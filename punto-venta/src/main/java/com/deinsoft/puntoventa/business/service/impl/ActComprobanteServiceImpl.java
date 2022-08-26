package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.Service.BusinessService;
import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.repository.ActComprobanteRepository;
import com.deinsoft.puntoventa.business.service.ActComprobanteService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.repository.ActComprobanteDetalleRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class ActComprobanteServiceImpl extends CommonServiceImpl<ActComprobante, ActComprobanteRepository>
        implements ActComprobanteService {

    @Autowired
    ActComprobanteRepository actComprobanteRepository;

    @Autowired
    ActComprobanteDetalleRepository actComprobanteDetalleRepository;

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;

    @Autowired
    InvAlmacenProductoRepository invAlmacenProductoRepository;

    @Autowired
    InvMovimientoProductoRepository invMovimientoProductoRepository;

    @Autowired
    CnfFormaPagoDetalleRepository cnfFormaPagoDetalleRepository;

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;

    public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante) {
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.getAllActComprobante(
                actComprobante.getSerie().toUpperCase(), actComprobante.getNumero().toUpperCase(),
                actComprobante.getObservacion().toUpperCase(),
                actComprobante.getFlagEstado().toUpperCase(),
                actComprobante.getFlagIsventa().toUpperCase(),
                actComprobante.getEnvioPseFlag().toUpperCase(),
                actComprobante.getEnvioPseMensaje().toUpperCase(),
                actComprobante.getXmlhash().toUpperCase(),
                actComprobante.getCodigoqr().toUpperCase(),
                actComprobante.getNumTicket().toUpperCase());
        return actComprobanteList;
    }

    public ActComprobante getActComprobante(Long id) {
        ActComprobante actComprobante = null;
        Optional<ActComprobante> actComprobanteOptional = actComprobanteRepository.findById(id);
        if (actComprobanteOptional.isPresent()) {
            actComprobante = actComprobanteOptional.get();
        }
        return actComprobante;
    }

    @Transactional
    @Override
    public ActComprobante saveActComprobante(ActComprobante actComprobante) throws Exception {
        ActComprobante actComprobanteResult = null;
        try {
            List<CnfNumComprobante> numComprobante = cnfNumComprobanteRepository.findByCnfTipoComprobanteIdAndCnfLocalId(
                    actComprobante.getCnfTipoComprobante().getId(),
                    actComprobante.getCnfLocal().getId());
            if (numComprobante.isEmpty()) {
                throw new Exception("No existe numeración para el tipo de comprobante y el local");
            }
            actComprobante.setFechaRegistro(LocalDateTime.now());
            actComprobante.setNumero(String.valueOf(numComprobante.get(0).getUltimoNro() + 1));
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                actComprobante.addActComprobanteDetalle(data);
            });
            actComprobanteResult = actComprobanteRepository.save(actComprobante);

            //update num comprobante
            CnfNumComprobante cnfNumComprobante = numComprobante.get(0);
            cnfNumComprobante.setUltimoNro(numComprobante.get(0).getUltimoNro() + 1);
            cnfNumComprobanteRepository.save(cnfNumComprobante);

            //update stock
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                List<InvAlmacenProducto> list
                        = invAlmacenProductoRepository.findByCnfProductoId(data.getCnfProducto().getId());

                if (list.size() > 1) {
                    throw new RuntimeException("No existe un único registro para el almacen y producto seleccionados");
                }
                if (list.size() == 0) {
                    throw new RuntimeException("No existe stock para el almacen y producto seleccionados");
                }
                InvAlmacenProducto stock = list.get(0);
                stock.setCnfProducto(data.getCnfProducto());
                stock.setInvAlmacen(actComprobante.getInvAlmacen());
                stock.setCantidad(stock.getCantidad().subtract(data.getCantidad()));
                invAlmacenProductoRepository.save(stock);

                InvMovimientoProducto mov = new InvMovimientoProducto();
                mov.setCnfProducto(data.getCnfProducto());
                mov.setInvAlmacen(actComprobante.getInvAlmacen());
                mov.setActComprobante(actComprobante);
                mov.setFecha(actComprobante.getFecha());
                mov.setFechaRegistro(LocalDateTime.now());
                mov.setCantidad(data.getCantidad().multiply(BigDecimal.valueOf(-1)));
                mov.setValor(data.getPrecio());
                invMovimientoProductoRepository.save(mov);
            });

            //save pagos programacion
            BigDecimal pending = actComprobante.getTotal();
            LocalDate dueDate = actComprobante.getFecha();
            for (CnfFormaPagoDetalle cnfFormaPagoDetalle
                    : cnfFormaPagoDetalleRepository.findByCnfFormaPagoId(actComprobante.getCnfFormaPago().getId())) {
                ActPagoProgramacion p = new ActPagoProgramacion();
                p.setActComprobante(actComprobante);

                if (cnfFormaPagoDetalle.getModoDiaVencimiento() != 0) {
                    p.setFecha(actComprobante.getFecha());
                    p.setFechaVencimiento(actComprobante.getFecha()
                            .plusDays(cnfFormaPagoDetalle.getModoDiaVencimiento()));
                    p.setMonto(cnfFormaPagoDetalle.getModoMonto());
                    p.setMontoPendiente(cnfFormaPagoDetalle.getModoMonto());
                } else if (cnfFormaPagoDetalle.getModoPorcentaje() != null) {
                    p.setFechaVencimiento(actComprobante.getFecha()
                            .plusDays(cnfFormaPagoDetalle.getModoDiasIntervalo()));
                    //actPayment.setTotalamt(actInvoice.getGrandtotal()
                    //				.multiply(cnfTendertypeSchedule.getPercent().divide(new BigDecimal(100))))
                    p.setMonto(actComprobante.getTotal().multiply(cnfFormaPagoDetalle.getModoPorcentaje().divide(new BigDecimal(100))));

                } else {
                    if (pending.compareTo(cnfFormaPagoDetalle.getModoMonto()) == -1) {
                        p.setMonto(pending);
                    }
                    p.setMonto(cnfFormaPagoDetalle.getModoMonto());
                }
                pending = pending.subtract(p.getMonto());
                p.setMontoPendiente(p.getMonto());
                actPagoProgramacionRepository.save(p);
            }
            if (pending.compareTo(BigDecimal.ZERO) == 1) {
                ActPagoProgramacion actPayment = new ActPagoProgramacion();
                actPayment.setActComprobante(actComprobante);
                actPayment.setFecha(actComprobante.getFecha());
                actPayment.setFechaVencimiento(dueDate.plusDays(30));
                actPayment.setMonto(pending);
                actPayment.setMontoPendiente(actPayment.getMonto());
                actPagoProgramacionRepository.save(actPayment);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
            throw e;
        }

        return actComprobanteResult;
    }

    @Transactional
    @Override
    public ActComprobante saveActComprobanteCompra(ActComprobante actComprobante) throws Exception {
        ActComprobante actComprobanteResult = null;
        try {
            actComprobante.setFechaRegistro(LocalDateTime.now());
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                actComprobante.addActComprobanteDetalle(data);
            });
            actComprobanteResult = actComprobanteRepository.save(actComprobante);

            //update stock
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                List<InvAlmacenProducto> list
                        = invAlmacenProductoRepository.findByCnfProductoId(data.getCnfProducto().getId());

                if (list.size() > 1) {
                    throw new RuntimeException("No existe un único registro para el almacen y producto seleccionados");
                }
                if (list.size() == 0) {
                    InvAlmacenProducto stock = new InvAlmacenProducto();
                    stock.setCnfProducto(data.getCnfProducto());
                    stock.setInvAlmacen(actComprobante.getInvAlmacen());
                    stock.setCantidad(data.getCantidad());
                    invAlmacenProductoRepository.save(stock);
                } else {
                    InvAlmacenProducto stock = list.get(0);
                    stock.setCnfProducto(data.getCnfProducto());
                    stock.setInvAlmacen(actComprobante.getInvAlmacen());
                    stock.setCantidad(stock.getCantidad().add(data.getCantidad()));
                    invAlmacenProductoRepository.save(stock);
                }
                InvMovimientoProducto mov = new InvMovimientoProducto();
                mov.setCnfProducto(data.getCnfProducto());
                mov.setInvAlmacen(actComprobante.getInvAlmacen());
                mov.setActComprobante(actComprobante);
                mov.setFecha(actComprobante.getFecha());
                mov.setFechaRegistro(LocalDateTime.now());
                mov.setCantidad(data.getCantidad());
                mov.setValor(data.getPrecio());
                invMovimientoProductoRepository.save(mov);
            });
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
            throw e;
        }

        return actComprobanteResult;
    }

    public List<ActComprobante> getAllActComprobante() {
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.findAll();
        return actComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfMaestro(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfMaestroId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfFormaPago(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfFormaPagoId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfMoneda(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfMonedaId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfLocal(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfLocalId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfTipoComprobante(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfTipoComprobanteId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByInvAlmacen(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByInvAlmacenId(id);
        return ActComprobanteList;
    }

    @Override
    public void delete(long id) {
        ActComprobante actComprobante = null;
        Optional<ActComprobante> actComprobanteOptional = actComprobanteRepository.findById(id);

        if (actComprobanteOptional.isPresent()) {
            actComprobante = actComprobanteOptional.get();
            actComprobanteRepository.delete(actComprobante);
        }
    }

    @Override
    public List<ActComprobante> getReportActComprobante(ParamBean paramBean) {
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.getReportActComprobante(paramBean);
        return actComprobanteList;
    }
}
