package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;

import java.util.List;
import java.util.Optional;

import com.deinsoft.puntoventa.business.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.repository.CnfProductoRepository;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@Transactional
public class InvAlmacenProductoServiceImpl
        extends CommonServiceImpl<InvAlmacenProducto, Long, InvAlmacenProductoRepository> implements InvAlmacenProductoService {

    @Autowired
    InvMovimientoProductoRepository invMovimientoProductoRepository;

    @Autowired
    InvAlmacenProductoRepository invAlmacenProductoRepository;

    @Autowired
    CnfProductoRepository cnfProductoRepository;

    public List<InvAlmacenProducto> getAllInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto) {
        List<InvAlmacenProducto> invAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.getAllInvAlmacenProducto(listRoles());
        return invAlmacenProductoList;
    }

    public InvAlmacenProducto getInvAlmacenProducto(Long id) {
        InvAlmacenProducto invAlmacenProducto = null;
        Optional<InvAlmacenProducto> invAlmacenProductoOptional = invAlmacenProductoRepository.findById(id);
        if (invAlmacenProductoOptional.isPresent()) {
            invAlmacenProducto = invAlmacenProductoOptional.get();
        }
        return invAlmacenProducto;
    }

    public InvAlmacenProducto saveInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto) {
        InvAlmacenProducto invAlmacenProductoResult = invAlmacenProductoRepository.save(invAlmacenProducto);
        return invAlmacenProductoResult;
    }

    public List<InvAlmacenProducto> getAllInvAlmacenProducto() {
        List<InvAlmacenProducto> invAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.findAll();
        return invAlmacenProductoList;
    }

    public List<InvAlmacenProducto> getAllInvAlmacenProductoByInvAlmacen(long id) {
        List<InvAlmacenProducto> InvAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.findByInvAlmacenId(id);
        return InvAlmacenProductoList;
    }

    public List<InvAlmacenProducto> getAllInvAlmacenProductoByCnfProducto(long id) {
        List<InvAlmacenProducto> InvAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.findByCnfProductoId(id);
        return InvAlmacenProductoList;
    }

    @Override
    public void delete(long id) {
        InvAlmacenProducto invAlmacenProducto = null;
        Optional<InvAlmacenProducto> invAlmacenProductoOptional = invAlmacenProductoRepository.findById(id);

        if (invAlmacenProductoOptional.isPresent()) {
            invAlmacenProducto = invAlmacenProductoOptional.get();
            invAlmacenProductoRepository.delete(invAlmacenProducto);
        }
    }

    @Override
    public List<InvAlmacenProducto> getReportInvAlmacen(ParamBean paramBean) {
        List<InvAlmacenProducto> list = (List<InvAlmacenProducto>) invAlmacenProductoRepository.getReportInvAlmacen(paramBean, listRoles());
        return list;
    }

    @Override
    public void registerProductMovementAndUpdateStock(ActComprobante actComprobante, InvMovAlmacen invMovAlmacen) {
        if (actComprobante != null) {
            invMovimientoProductoRepository.deleteByActComprobante(actComprobante);
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                if (!data.getCnfProducto().getCnfUnidadMedida().getCodigoSunat().equals("ZZ")) {
//                    if (data.getPrecioVenta() == null) {
//                        data.setPrecioVenta(data.getPrecio());
//                    }


                    //actualizar stock almacen
                    InvAlmacenProducto stock;
//                    boolean isForAddStock = false;
//                    if (actComprobante.getFlagIsventa().equals("1") && actComprobante.getCnfTipoComprobante().getCodigoSunat().equals("07")) {
//                        isForAddStock = true;
//                    }
//                    if (!actComprobante.getFlagIsventa().equals("1") && !actComprobante.getCnfTipoComprobante().getCodigoSunat().equals("07")) {
//                        isForAddStock = true;
//                    }
                    boolean isForAddStock = (actComprobante.getFlagIsventa().equals("1") && actComprobante.getCnfTipoComprobante().getCodigoSunat().equals("07"))
                            || (!actComprobante.getFlagIsventa().equals("1") && !actComprobante.getCnfTipoComprobante().getCodigoSunat().equals("07"));

                    CnfProducto cnfProducto = cnfProductoRepository.getById(data.getCnfProducto().getId());

                    updateStock(actComprobante, null, data.getCantidad(),data.getPrecioVenta(),data.getPrecio(), isForAddStock,
                            cnfProducto, BigDecimal.ONE);

                    if (!cnfProducto.getListCnfPaqueteDet().isEmpty()) {
                        cnfProducto.getListCnfPaqueteDet().forEach(paqueteProducto -> {
                            updateStock(actComprobante, null, data.getCantidad(),data.getPrecioVenta(),data.getPrecio(), isForAddStock,
                                    paqueteProducto.getCnfProductoContenido(), paqueteProducto.getCantidad());

                        });

                    }


                }

            });
        }
        if (invMovAlmacen != null) {
            invMovimientoProductoRepository.deleteByInvMovAlmacen(invMovAlmacen);
            invMovAlmacen.getListInvMovAlmacenDet().forEach(data -> {
                if (!data.getCnfProducto().getCnfUnidadMedida().getCodigoSunat().equals("ZZ")) {


                    if (data.getInvMovAlmacen().getInvTipoMovAlmacen().getNaturaleza().equals("2")) {
                        //SALIDA
                        callUpdateStockAndPackageContent(actComprobante, invMovAlmacen, false,data);
                        //ENTRADA
                        callUpdateStockAndPackageContent(actComprobante, invMovAlmacen, true, data);
//                        updateStock(null,invMovAlmacen, data.getCantidad(),null,
//                                data.getPrecio(), true, data.getCnfProducto(), BigDecimal.ONE);
//
//                        if (!data.getCnfProducto().getListCnfPaqueteDet().isEmpty()) {
//                            data.getCnfProducto().getListCnfPaqueteDet().forEach(paqueteProducto -> {
//                                updateStock(actComprobante, null, data.getCantidad(), null,data.getPrecio(), true,
//                                        paqueteProducto.getCnfProductoContenido(), paqueteProducto.getCantidad());
//
//                            });
//
//                        }

                    }
                    else {

                        boolean isForAddStock = !data.getInvMovAlmacen().getInvTipoMovAlmacen().getNaturaleza().equals("-1");
                        callUpdateStockAndPackageContent(actComprobante, invMovAlmacen, isForAddStock, data);
                    }

//                    List<InvAlmacenProducto> list
//                            = invAlmacenProductoRepository.findByCnfProductoId(data.getCnfProducto().getId());
//                    if (list.size() > 1) {
//                        throw new RuntimeException("No existe un único registro para el almacen y producto seleccionados");
//                    }
//                    if (list.isEmpty() && data.getInvMovAlmacen().getInvTipoMovAlmacen().getNaturaleza().equals("-1")) {
//                        throw new RuntimeException("No existe stock para el almacen y producto seleccionados");
//                    }
//
//
//                    InvAlmacenProducto invAlmacenProducto = list.stream().findFirst().orElse(null);
//                    if (invAlmacenProducto == null) {
//                        invAlmacenProducto = new InvAlmacenProducto();
//                        invAlmacenProducto.setCantidad(BigDecimal.ZERO);
//                    }
//                    if (isForAddStock) {
//                        invAlmacenProducto.setCantidad(invAlmacenProducto.getCantidad()
//                                .add(data.getCantidad().multiply(BigDecimal.ONE)));
//
//                    } else {
//                        invAlmacenProducto.setCantidad(invAlmacenProducto.getCantidad().multiply(BigDecimal.ONE)
//                                .subtract(data.getCantidad().multiply(BigDecimal.ONE)));
//                    }
//
//                    invAlmacenProducto.setCnfProducto(data.getCnfProducto());
//                    invAlmacenProducto.setInvAlmacen(invMovAlmacen.getInvAlmacen());
//                    invAlmacenProductoRepository.save(invAlmacenProducto);
//
//                    InvMovimientoProducto mov = new InvMovimientoProducto();
//                    mov.setCnfProducto(data.getCnfProducto());
//                    mov.setInvAlmacen(invMovAlmacen.getInvAlmacen());
//                    mov.setFecha(invMovAlmacen.getFecha());
//                    mov.setFechaRegistro(LocalDateTime.now());
//                    mov.setCantidad(data.getCantidad().multiply(!isForAddStock ? BigDecimal.valueOf(-1) : BigDecimal.ZERO));
//                    mov.setValor(data.getPrecio());
//                    invMovimientoProductoRepository.save(mov);
                }

            });
        }

    }

    private void callUpdateStockAndPackageContent(ActComprobante actComprobante, InvMovAlmacen invMovAlmacen, boolean isForAddStock, InvMovAlmacenDet data) {
        updateStock(null, invMovAlmacen, data.getCantidad(),null,
                data.getPrecio(), isForAddStock, data.getCnfProducto(), BigDecimal.ONE);

        if (!data.getCnfProducto().getListCnfPaqueteDet().isEmpty()) {
            data.getCnfProducto().getListCnfPaqueteDet().forEach(paqueteProducto -> {
                updateStock(actComprobante, null, data.getCantidad(), null, data.getPrecio(), isForAddStock,
                        paqueteProducto.getCnfProductoContenido(), paqueteProducto.getCantidad());

            });

        }
    }

//    private void extracted(ActComprobante actComprobante, ActComprobanteDetalle data, boolean isForAddStock) {
//        CnfProducto cnfProducto = cnfProductoRepository.getById(data.getCnfProducto().getId());
//
//        updateStock(actComprobante, data, isForAddStock, cnfProducto, BigDecimal.ONE);
//
//        if (!cnfProducto.getListCnfPaqueteDet().isEmpty()) {
//            cnfProducto.getListCnfPaqueteDet().forEach(paqueteProducto -> {
//                updateStock(actComprobante, data, isForAddStock,
//                        paqueteProducto.getCnfProductoContenido(), paqueteProducto.getCantidad());
//
//            });
//
//        }
//
//    }

    private void updateStock(ActComprobante actComprobante,
                             InvMovAlmacen invMovAlmacen,
                             BigDecimal cantidadMov,
                             BigDecimal precioVentaMov,
                             BigDecimal precioMov,
                             boolean isForAddStock,
                             CnfProducto producto,
                             BigDecimal cantidadToMultiply) {

        Long almacenId;
        if (actComprobante != null) {
            almacenId = actComprobante.getInvAlmacen().getId();
        } else {
            almacenId = invMovAlmacen.getInvAlmacen().getId();
        }
        CnfProducto cnfProducto = cnfProductoRepository.getById(producto.getId());
        List<InvAlmacenProducto> list
                = invAlmacenProductoRepository.findByCnfProductoIdAndInvAlmacenId(cnfProducto.getId(),almacenId);

        InvAlmacenProducto invAlmacenProducto = list.stream().findFirst().orElse(null);
        if (list.size() > 1) {
            throw new RuntimeException("No existe un único registro para el almacen y producto seleccionados");
        }
        if (!isForAddStock && (list.size() == 0 || invAlmacenProducto.getCantidad().compareTo(cantidadMov) <= 0)) {
            throw new RuntimeException("No existe stock para el almacen y producto seleccionados");
        }
        if (invAlmacenProducto == null) {
            invAlmacenProducto = new InvAlmacenProducto();
            invAlmacenProducto.setCantidad(BigDecimal.ZERO);
        }
        if (isForAddStock) {
            invAlmacenProducto.setCantidad(invAlmacenProducto.getCantidad()
                    .add(cantidadMov.multiply(cantidadToMultiply)));

        } else {
            invAlmacenProducto.setCantidad(invAlmacenProducto.getCantidad().multiply(cantidadToMultiply)
                    .subtract(cantidadMov.multiply(cantidadToMultiply)));
        }
        invAlmacenProducto.setCnfProducto(cnfProducto);

        InvMovimientoProducto mov = new InvMovimientoProducto();
        if (actComprobante != null) {
            invAlmacenProducto.setInvAlmacen(actComprobante.getInvAlmacen());
            mov.setInvAlmacen(actComprobante.getInvAlmacen());
            mov.setFecha(actComprobante.getFecha());
        } else {
            invAlmacenProducto.setInvAlmacen(invMovAlmacen.getInvAlmacen());
            mov.setInvAlmacen(invMovAlmacen.getInvAlmacen());
            mov.setFecha(invMovAlmacen.getFecha());
        }

        invAlmacenProductoRepository.save(invAlmacenProducto);

        mov.setCnfProducto(cnfProducto);
        mov.setActComprobante(actComprobante);
        mov.setInvMovAlmacen(invMovAlmacen);
        mov.setFechaRegistro(LocalDateTime.now());
        mov.setCantidad(cantidadMov.multiply(cantidadToMultiply).multiply(!isForAddStock ?
                BigDecimal.valueOf(-1) : BigDecimal.ONE));
        mov.setValor(!isForAddStock ? cnfProducto.getCosto() : precioMov);
        invMovimientoProductoRepository.save(mov);

        if (isForAddStock & actComprobante != null) {
            List<InvMovimientoProducto> listMovs
                    = (List<InvMovimientoProducto>) invMovimientoProductoRepository.findByCnfProductoId(
                    producto.getId(), actComprobante.getInvAlmacen().getId());
            BigDecimal costo = BigDecimal.ZERO;
            BigDecimal costoTotal = BigDecimal.ZERO;
            BigDecimal cantidad = BigDecimal.ZERO;
            for (InvMovimientoProducto invMovimientoProducto : listMovs) {
                //solo compras
                if (invMovimientoProducto.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
                    cantidad = cantidad.add(invMovimientoProducto.getCantidad());
                    costoTotal = costoTotal.add(invMovimientoProducto.getCantidad().multiply(invMovimientoProducto.getValor()));
                }
            }
            if (cnfProducto.getCnfEmpresa().getTipoCostoInventario() == 1) {
                costo = costoTotal.divide(cantidad, 2, RoundingMode.HALF_UP);
                //actualizar precio, metodo promedio ponerado, proxima configuracion de empresa
                cnfProducto.setCosto(costo);
                cnfProducto.setPrecio(precioVentaMov);
            } else if (cnfProducto.getCnfEmpresa().getTipoCostoInventario() == 2) {
                costo = precioMov;
                cnfProducto.setCosto(precioMov);
            }
            if (cnfProducto.getCnfEmpresa().getTipoCostoInventario() == 1
                    || cnfProducto.getCnfEmpresa().getTipoCostoInventario() == 2) {
                cnfProducto.setPrecio(precioVentaMov);
                BigDecimal gananciaPorcentaje
                        = precioVentaMov.divide(costo, 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100.00")).subtract(new BigDecimal("100.00"));
                cnfProducto.setPorcentajeGanancia(gananciaPorcentaje.floatValue());
                cnfProductoRepository.save(cnfProducto);
            }

        }
    }
}
