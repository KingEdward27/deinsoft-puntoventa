package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.GeneratedFile;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActOrden;
import com.deinsoft.puntoventa.business.repository.ActOrdenRepository;
import com.deinsoft.puntoventa.business.service.ActOrdenService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.dto.ReporteContableDto;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.dto.TopProductosDto;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.model.ActOrdenDetalle;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.repository.ActOrdenDetalleRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoRepository;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import com.deinsoft.puntoventa.business.service.CnfImpuestoCondicionService;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;
import com.deinsoft.puntoventa.config.AppConfig;
import com.deinsoft.puntoventa.facturador.client.EnvioPSE2;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import com.deinsoft.puntoventa.framework.util.Formatos;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.util.Constantes;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class ActOrdenServiceImpl extends CommonServiceImpl<ActOrden,Long, ActOrdenRepository>
        implements ActOrdenService {

    @Autowired
    ActOrdenRepository actOrdenRepository;

    @Autowired
    ActOrdenDetalleRepository actOrdenDetalleRepository;

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;

    @Autowired
    InvAlmacenProductoRepository invAlmacenProductoRepository;

    @Autowired
    InvAlmacenProductoService invAlmacenProductoService;

    @Autowired
    InvMovimientoProductoRepository invMovimientoProductoRepository;

    @Autowired
    CnfFormaPagoDetalleRepository cnfFormaPagoDetalleRepository;

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;

    @Autowired
    ActCajaTurnoRepository actCajaTurnoRepository;

    @Autowired
    ActCajaOperacionRepository actCajaOperacionRepository;

    @Autowired
    BusinessService businessService;
    
    @Autowired
    CnfImpuestoCondicionService cnfImpuestoCondicionService;

    @Autowired
    ActPagoRepository actPagoRepository;
    
    @Autowired
    AppConfig appConfig;

    static DateTimeFormatter YYYYMM_FORMATER = DateTimeFormatter.ofPattern("yyyyMM");
    static DateTimeFormatter YYYYMMDD_FORMATER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<ActOrden> getAllActOrden(ActOrden actOrden) {
        SecurityFilterDto securityFilterDto = listRoles();
        List<ActOrden> actOrdenList = (List<ActOrden>) actOrdenRepository.getAllActOrden(
                actOrden.getSerie().toUpperCase(), actOrden.getNumero().toUpperCase(),
                actOrden.getObservacion().toUpperCase(),
                actOrden.getFlagEstado().toUpperCase(),
                actOrden.getFlagIsventa().toUpperCase(),
                securityFilterDto);
        return actOrdenList;
    }

    public ActOrden getActOrden(Long id) {
        ActOrden actOrden = null;
        Optional<ActOrden> actOrdenOptional = actOrdenRepository.findById(id);
        Set<ActOrdenDetalle> list;
        if (actOrdenOptional.isPresent()) {
            actOrden = actOrdenOptional.get();
            list = actOrden.getListActOrdenDetalle().stream().map(mapper -> {
                mapper.setCnfImpuestoCondicion(
                cnfImpuestoCondicionService.getCnfImpuestoCondicion(mapper.getCnfImpuestoCondicion().getId()));
                return mapper;
            }).collect(Collectors.toSet());
            
            actOrden.setListActOrdenDetalle(list);
            
            SecurityFilterDto f = listRoles();
            if (f.getEmpresaId() != actOrden.getCnfLocal().getCnfEmpresa().getId()) {
                throw new SecurityException(Constantes.MSG_NO_AUTHORIZED);
            }
        } else {
            throw new SecurityException(Constantes.MSG_NO_EXISTS_ITEM);
        }
        return actOrden;
    }

    @Transactional
    @Override
    public ActOrden saveActOrden(ActOrden actOrden) throws Exception {
        
//        businessService.verifyPlan(actOrden, null);
        
        ActOrden actOrdenResult = null;

        actOrden.setSegUsuario(auth.getLoggedUserdata());
        //tiene que obedecer un parametro de configuración caja turno, por ahora null
        List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(actOrden.getSegUsuario().getId(),listRoles());
        actCajaTurno = actCajaTurno.stream()
                .filter(item -> item.getEstado().equals("1"))
                .collect(Collectors.toList());
        
        
        if (actCajaTurno.isEmpty()) {
            throw new Exception("El usuario no tiene caja aperturada");
        }
        try {

//            if (numComprobante.isEmpty()) {
//                throw new Exception("No existe numeración para el tipo de comprobante y el local");
//            }
//            if (numComprobante.isEmpty()) {
//                throw new Exception("No existe numeración para el tipo de comprobante y el local");
//            }
            if (actOrden.getId() != null) {

                List<CnfNumComprobante> numComprobante = cnfNumComprobanteRepository.findByCnfTipoComprobanteIdAndCnfLocalId(
                        actOrden.getCnfTipoComprobante().getId(),
                        actOrden.getCnfLocal().getId());
                if (numComprobante.isEmpty()) {
                    throw new Exception("No existe numeración para el tipo de comprobante y el local");
                }

                //update num comprobante
                CnfNumComprobante cnfNumComprobante = numComprobante.get(0);
                cnfNumComprobante.setUltimoNro(numComprobante.get(0).getUltimoNro() + 1);

                cnfNumComprobanteRepository.save(cnfNumComprobante);

                actOrden.setFechaRegistro(LocalDateTime.now());
                actOrden.setNumero(String.valueOf(numComprobante.get(0).getUltimoNro()));
            }

            actOrden.getListActOrdenDetalle().forEach(data -> {
                actOrden.addActOrdenDetalle(data);
            });
            actOrdenResult = actOrdenRepository.save(actOrden);
            if (actOrden.getId() != null) {
                if (actOrdenResult.getCnfLocal().getCnfEmpresa().getFlagVentaRapida() == 1) {
//                    validate(actOrden.getId());

                    actOrdenResult.setFlagEstado("2");
                    actOrdenRepository.save(actOrden);
                }
            }

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
            throw e;
        }

        return actOrdenResult;
    }

    @Transactional
    @Override
    public ActOrden saveActOrdenCompra(ActOrden actOrden) throws Exception {
        ActOrden actOrdenResult = null;

        actOrden.setSegUsuario(auth.getLoggedUserdata());
        try {
            actOrden.setFechaRegistro(LocalDateTime.now());
            actOrden.getListActOrdenDetalle().forEach(data -> {
                actOrden.addActOrdenDetalle(data);
            });
            actOrdenResult = actOrdenRepository.save(actOrden);

            if (actOrdenResult.getCnfLocal().getCnfEmpresa().getFlagCompraRapida() == 1) {
                
                actOrdenResult.setFlagEstado("2");
                actOrdenRepository.save(actOrden);
            }

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
            throw e;
        }

        return actOrdenResult;
    }


    
    public List<ActOrden> getAllActOrden() {
        List<ActOrden> actOrdenList = (List<ActOrden>) actOrdenRepository.findAll();
        return actOrdenList;
    }

    public List<ActOrden> getAllActOrdenByCnfMaestro(long id) {
        List<ActOrden> ActOrdenList = (List<ActOrden>) actOrdenRepository.findByCnfMaestroId(id);
        return ActOrdenList;
    }

    public List<ActOrden> getAllActOrdenByCnfFormaPago(long id) {
        List<ActOrden> ActOrdenList = (List<ActOrden>) actOrdenRepository.findByCnfFormaPagoId(id);
        return ActOrdenList;
    }

    public List<ActOrden> getAllActOrdenByCnfMoneda(long id) {
        List<ActOrden> ActOrdenList = (List<ActOrden>) actOrdenRepository.findByCnfMonedaId(id);
        return ActOrdenList;
    }

    public List<ActOrden> getAllActOrdenByCnfLocal(long id) {
        List<ActOrden> ActOrdenList = (List<ActOrden>) actOrdenRepository.findByCnfLocalId(id);
        return ActOrdenList;
    }

    public List<ActOrden> getAllActOrdenByCnfTipoComprobante(long id) {
        List<ActOrden> ActOrdenList = (List<ActOrden>) actOrdenRepository.findByCnfTipoComprobanteId(id);
        return ActOrdenList;
    }

    public List<ActOrden> getAllActOrdenByInvAlmacen(long id) {
        List<ActOrden> ActOrdenList = (List<ActOrden>) actOrdenRepository.findByInvAlmacenId(id);
        return ActOrdenList;
    }

    @Override
    public void delete(long id) {
        ActOrden actOrden = null;
        Optional<ActOrden> actOrdenOptional = actOrdenRepository.findById(id);

        if (actOrdenOptional.isPresent()) {
            actOrden = actOrdenOptional.get();
            actOrdenRepository.delete(actOrden);
        }
    }

    @Override
    public List<ActOrden> getReportActOrden(ParamBean paramBean) {
        List<ActOrden> actOrdenList = (List<ActOrden>) actOrdenRepository.getReportActOrden(paramBean,listRoles());
        return actOrdenList;
    }

    @Transactional
    @Override
    public String invalidate(long id) throws Exception {
        String result = "";
        try {
            ActOrden actInvoice = getActOrden(id);
            if (actInvoice.getCnfLocal().getCnfEmpresa().getFlagVentaRapida() == 1) {
                if (!"2".equals(actInvoice.getFlagEstado())) {
                    result = "El documento no se encuentra en estado Validado";
                    throw new Exception(result);
                }
            } else {
                if (!"1".equals(actInvoice.getFlagEstado())) {
                    result = "El documento no se encuentra en estado Registrado";
                    throw new Exception(result);
                }
            }
            // clean in actOrder
            for (ActOrdenDetalle item : actInvoice.getListActOrdenDetalle()) {
//                List<ActOrderLine> listActOrderline = actOrderLineService.findByActInvoiceId(item.getId());
//                for (ActOrderLine actOrderLine : listActOrderline) {
//                    actOrderLine.setActInvoiceLine(null);
//                    actOrderLineService.save(actOrderLine);
//                }
                // update balance
                if (!item.getCnfProducto().getCnfUnidadMedida().getCodigoSunat().equals("ZZ")) {
                    InvAlmacenProducto invBalance
                            = invAlmacenProductoRepository.findByCnfProductoIdAndInvAlmacenId(
                                    item.getCnfProducto().getId(), actInvoice.getInvAlmacen().getId()).stream().findFirst().orElse(null);
                    if (invBalance != null) {
                        BigDecimal currentQty = invBalance.getCantidad();
                        if (actInvoice.getFlagIsventa().equals("1")) {
                            invBalance.setCantidad(currentQty.add(item.getCantidad()));
                        } else {
                            invBalance.setCantidad(currentQty.subtract(item.getCantidad()));
                        }
                        invAlmacenProductoRepository.save(invBalance);
                    }
                    

//                    invMovimientoProductoRepository.deleteByActOrden(actInvoice);
                }

            }
            actInvoice.setFlagEstado("0");
            actInvoice.setTotal(BigDecimal.ZERO);
            // update cab
            this.save(actInvoice);
            // delete detail
//            actInvoiceLineService.deleteByActInvoice(actInvoice);

            // delete payment(remains validate)
//            actPagoProgramacionRepository.deleteByActOrden(actInvoice);

            // delete invoice tax
//            actInvoiceTaxService.deleteByActInvoice(actInvoice);
            // delete movement
//            if (actInvoice.getIsinventory()) {
//                invMovimientoProductoRepository.deleteByActInvoice(actInvoice);
//            }
            // enviar a ebi
//            if (actInvoice.getCnfDoctype().getIsebi()) {
//                result = "No se pudo enviar la anulacion de documento a EBI, porque no era un documento electronico";
//                String documentSerial = actInvoice.getDocumentserial();
//                String documentNro = String.format("%08d", actInvoice.getDocumentno());
//                String docType = actInvoice.getCnfDoctype().getValue();
//                ObjectNode objectNode = ebiService.ebiDeleteDocument(docType, documentSerial, documentNro);
//                result = objectNode.get(RESPONSE).asText();
//                if (result.toLowerCase().startsWith("ok")) {
//                    result = "";
//                }
//            }
//        } catch (GenericBusinessException e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            result = e.getMessage();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = e.getMessage();
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    @Override
    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception {
        byte[] bytes = businessService.printOrden(appConfig.getStaticResourcesPath(), tipo, getActOrden(id), false);
        return bytes;
    }

    @Override
    public GeneratedFile generateSireTxt(ParamBean paramBean) throws Exception {

        ParamBean paramWorked = paramBean;
        int lastDay = LocalDate.parse(paramWorked.getPeriodo() + "01", YYYYMMDD_FORMATER).lengthOfMonth();
        paramWorked.setFechaDesde(LocalDate.parse(paramWorked.getPeriodo() + "01", YYYYMMDD_FORMATER));
        paramWorked.setFechaHasta(LocalDate.parse(paramWorked.getPeriodo() + String.valueOf(lastDay), YYYYMMDD_FORMATER));
        paramWorked.setFlagEstado("2");
        List<String> x = getReportActOrden(paramWorked)
                .stream()
                .filter(predicate -> predicate.getCnfTipoComprobante().getFlagElectronico().equals("1"))
                .map(mapper -> {
                    BigDecimal multiplicand = BigDecimal.ONE;
                    if (mapper.getCnfTipoComprobante().getCodigoSunat().equals(Constantes.TIPO_DOC_NOTA_CREDITO)) {
                        multiplicand = multiplicand.multiply(BigDecimal.valueOf(-1));
                    }

                    String line = mapper.getCnfLocal().getCnfEmpresa().getNroDocumento().concat("|")
                            .concat(mapper.getCnfLocal().getCnfEmpresa().getNombre()).concat("|")
                            .concat(mapper.getFecha().format(Constantes.YYYYMM_FORMATER)).concat("|")
                            .concat("|")
                            .concat(mapper.getFecha().format(Constantes.DDMMYYYY_FORMATER)).concat("|")
                            .concat("|")
                            .concat(mapper.getCnfTipoComprobante().getCodigoSunat()).concat("|")
                            .concat(mapper.getSerie())
                            .concat("|")
                            .concat(mapper.getNumero()).concat("|")
                            .concat("|")
                            .concat(mapper.getCnfMaestro().getCnfTipoDocumento().getCodigoSunat()).concat("|")
                            .concat(mapper.getCnfMaestro().getNroDoc()).concat("|")
                            .concat(mapper.getCnfMaestro().getRazonSocial()).concat("|")
                            .concat("|");
                    line = line.concat(Formatos.df.format(mapper.getSubtotal().multiply(multiplicand))).concat("|")
                            .concat("0.00").concat("|")//DESCUENTO
                            .concat(Formatos.df.format(mapper.getIgv().multiply(multiplicand))).concat("|")
                            .concat("0.00").concat("|");//DESCUENTO
                    line = line.concat("0.00").concat("|")//exonerada
                            .concat("0.00").concat("|")//inafecta
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|");
                    line = line.concat(Formatos.df.format(mapper.getTotal().multiply(multiplicand))).concat("|")
                            .concat(mapper.getCnfMoneda().getCodigo()).concat("|")
                            .concat("|")//tipo cambio
                            
                            .concat("|")//fecha emision comprobante modificado
                            .concat("|")//tipo comprobante modificado
                            .concat("|")//serie comprobante modificado
                            .concat("|")//numero comprobante modificado
                            .concat("|")
                            .concat("|").concat("\n");
                    return line;
                }).collect(Collectors.toList());
        if (x.isEmpty()) {
            throw new Exception("No hay datos para el periodo");
        }
        String joined = x.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
        String fileName = "LE"
                .concat(paramBean.getCnfLocal().getCnfEmpresa().getNroDocumento())
                .concat(paramBean.getFechaDesde().format(Constantes.YYYYMM_FORMATER))
                .concat("00")
                .concat("080400")//codigo libro
                .concat("02")//Código de oportunidad de presentación del RVIE/RCE: -> RCE Cuando realiza ajustes posteriores
                .concat("1")//Indicador de operaciones -> 0 Cierre o baja de RUC  / 1 Empresa operativa  / 2 Cierre de libro
                .concat("1")//Indicador del contenido del libro o registro -> 1 Con información / 0 Sin información
                .concat("1")//Indicador de la moneda utilizada -> 1 Soles  / 2 Dólares
                .concat("2")//Indicador de libro electrónico generado por el MIGE IGV/RVIE -> Generado por el SIRE ( Fijo) (2)
                //.concat("1")//Correlativo de los ajustes posteriores -> Puede ser 01, 02, 03, etc. Según el correlativo que corresponde.
                .concat(".txt");
        byte[] txtData = Util.generateFile(fileName, joined);
        byte[] zippedData = Util.comprimirArchivo(new ByteArrayInputStream(txtData), fileName);
//        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\user\\Documents\\test.zip")) {
//            fos.write(zippedData);
//        }
        ByteArrayInputStream stream = new ByteArrayInputStream(zippedData);
        InputStreamResource inputStreamResource = new InputStreamResource(stream);
        return new GeneratedFile(fileName, zippedData);
    }

    public List<ReporteContableDto> getListaReporteContable(Long cnfLocalId) {
        if (cnfLocalId == 0) {
            throw new RuntimeException("Dbe ingresar el local");
        }
        List<ActOrden> list = actOrdenRepository.findByCnfLocalId(cnfLocalId);
        List<ReporteContableDto> x = list.stream()
                .filter(predicate -> predicate.getCnfTipoComprobante().getFlagElectronico().equals("1"))
                .collect(
                Collectors.groupingBy(item -> grouping(item),
                        Collectors.summingDouble(mapper -> mapper.getTotal().floatValue())))
                .entrySet().stream()
                .map(mapper -> {
                    return new ReporteContableDto(mapper.getKey().split("-")[0], 
                            mapper.getValue(),mapper.getKey().split("-")[1],mapper.getKey().split("-")[2]);
                            })
                .sorted(Comparator.comparing(ReporteContableDto::getPeriodo, Collections.reverseOrder()))
                .collect(Collectors.toList());

        return x;
    }

    @Override
    public Map<String, Object> getDashboardActOrdens(ParamBean param) {
        
        param.setFlagIsventa("1");
        param.setFlagEstado("2");
        List<ActOrden> listVentas = getReportActOrden(param);
        Double sumVentas = listVentas.stream().mapToDouble(o -> o.getTotal().doubleValue()).sum();
        
        List<Long> listClientes = listVentas.stream().map(action -> {
            return action.getCnfMaestro().getId();
        }).distinct().collect(Collectors.toList());
        
        String moneda = "";
        if (!listVentas.isEmpty()) {
            moneda = listVentas.get(0).getCnfLocal().getCnfEmpresa().getCnfMoneda().getSimbolo();
        }
        
        List<ActOrdenDetalle> listActOrdenDetalle = new ArrayList<>();
        for (ActOrden listVenta : listVentas) {
            listActOrdenDetalle.addAll(listVenta.getListActOrdenDetalle());
        }
        List<TopProductosDto> listTopProducts = listActOrdenDetalle.stream()
                .collect(
                Collectors.groupingBy(item -> grouping(item),
                        Collectors.summingDouble(mapper -> mapper.getImporte().floatValue())))
                .entrySet().stream()
                .map(mapper -> {
                    return new TopProductosDto(mapper.getKey(), 
                            mapper.getValue());
                            })
                .sorted(Comparator.comparing(TopProductosDto::getPrecio, Collections.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());
        
        List<TopProductosDto> listTopClients = listVentas.stream()
                .collect(
                Collectors.groupingBy(item -> item.getCnfMaestro().getRazonSocial(),
                        Collectors.summingDouble(mapper -> mapper.getTotal().floatValue())))
                .entrySet().stream()
                .map(mapper -> {
                    return new TopProductosDto(mapper.getKey(), 
                            mapper.getValue());
                            })
                .sorted(Comparator.comparing(TopProductosDto::getPrecio, Collections.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());
        
        
        param.setFlagIsventa("2");
        List<ActOrden> listCompras = getReportActOrden(param);
        Double sumCompras = listCompras.stream().mapToDouble(o -> o.getTotal().doubleValue()).sum();
        
        
        
        Double totalGanancia = listVentas.stream().map(mapper -> {
            return mapper.getListActOrdenDetalle().stream()
                    .mapToDouble(o -> (o.getPrecio().subtract(o.getCnfProducto().getCosto())).doubleValue())
                    .sum();
        }).mapToDouble(o -> o).sum();
        Map<String, Object> map = new HashMap<>();
        map.put("totalClientes", listClientes.size());
        map.put("totalVentas", listVentas.size());
        map.put("totalMontoVentas", sumVentas);
        map.put("totalCompras", listCompras.size());
        map.put("totalMontoCompras", sumCompras);
        map.put("totalCaja", totalGanancia);
        map.put("moneda", moneda);
        map.put("listTopProducts", listTopProducts);
        map.put("listTopClients", listTopClients);
        return map;
    }
    
    String grouping(ActOrden item) {
        String fileNameCompras = "LE"
                    .concat(item.getCnfLocal().getCnfEmpresa().getNroDocumento())
                    .concat(item.getFecha().format(Constantes.YYYYMM_FORMATER))
                    .concat("00")
                    .concat("080100")//codigo libro
                    .concat("02")//Código de oportunidad de presentación del RVIE/RCE: -> RCE Cuando realiza ajustes posteriores
                    .concat("1")//Indicador de operaciones -> 0 Cierre o baja de RUC  / 1 Empresa operativa  / 2 Cierre de libro
                    .concat("1")//Indicador del contenido del libro o registro -> 1 Con información / 0 Sin información
                    .concat("1")//Indicador de la moneda utilizada -> 1 Soles  / 2 Dólares
                    .concat("2")//Indicador de libro electrónico generado por el MIGE IGV/RVIE -> Generado por el SIRE ( Fijo) (2)
                    //.concat("1")//Correlativo de los ajustes posteriores -> Puede ser 01, 02, 03, etc. Según el correlativo que corresponde.
                    .concat(".zip");
        String fileNameVentas = "LE"
                    .concat(item.getCnfLocal().getCnfEmpresa().getNroDocumento())
                    .concat(item.getFecha().format(Constantes.YYYYMM_FORMATER))
                    .concat("00")
                    .concat("140100")//codigo libro
                    .concat("02")//Código de oportunidad de presentación del RVIE/RCE: -> RCE Cuando realiza ajustes posteriores
                    .concat("1")//Indicador de operaciones -> 0 Cierre o baja de RUC  / 1 Empresa operativa  / 2 Cierre de libro
                    .concat("1")//Indicador del contenido del libro o registro -> 1 Con información / 0 Sin información
                    .concat("1")//Indicador de la moneda utilizada -> 1 Soles  / 2 Dólares
                    .concat("2")//Indicador de libro electrónico generado por el MIGE IGV/RVIE -> Generado por el SIRE ( Fijo) (2)
                    //.concat("1")//Correlativo de los ajustes posteriores -> Puede ser 01, 02, 03, etc. Según el correlativo que corresponde.
                    .concat(".zip");
        return item.getFecha().format(YYYYMM_FORMATER) + "-" +fileNameCompras + "-" +fileNameVentas;
    }
    
    String grouping(ActOrdenDetalle item) {
        return item.getCnfProducto().getNombre();
    }
    
}
