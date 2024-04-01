package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.model.InvMovAlmacen;
import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.repository.CnfProductoRepository;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@Transactional
public class InvAlmacenProductoServiceImpl
        extends CommonServiceImpl<InvAlmacenProducto, InvAlmacenProductoRepository> implements InvAlmacenProductoService {

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
        List<InvAlmacenProducto> list = (List<InvAlmacenProducto>) invAlmacenProductoRepository.getReportInvAlmacen(paramBean,listRoles());
        return list;
    }

    @Override
    public void registerProductMovementAndUpdateStock(ActComprobante actComprobante, InvMovAlmacen invMovAlmacen) {
        if (actComprobante != null) {
            invMovimientoProductoRepository.deleteByActComprobante(actComprobante);
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                if (!data.getCnfProducto().getCnfUnidadMedida().getCodigoSunat().equals("ZZ")) {
                    
                    CnfProducto cnfProducto = cnfProductoRepository.getById(data.getCnfProducto().getId());
                    List<InvAlmacenProducto> list
                            = invAlmacenProductoRepository.findByCnfProductoId(data.getCnfProducto().getId());

                    if (list.size() > 1) {
                        throw new RuntimeException("No existe un único registro para el almacen y producto seleccionados");
                    }
                    if (list.size() == 0 && actComprobante.getFlagIsventa().equals("1")) {
                        throw new RuntimeException("No existe stock para el almacen y producto seleccionados");
                    }
                    //actualizar stock almacen
                    InvAlmacenProducto stock = new InvAlmacenProducto();
                    if (!actComprobante.getFlagIsventa().equals("1")) {
                        if (list.size() == 0) {
                            stock = new InvAlmacenProducto();
                            stock.setCantidad(data.getCantidad());

                        } else {
                            stock = list.get(0);
                            stock.setCantidad(stock.getCantidad().add(data.getCantidad()));
                        }
                    } else {
                        if (list.size() == 0) {
                            stock = new InvAlmacenProducto();
                            stock.setCantidad(data.getCantidad());

                        } else {
                            stock = list.get(0);
                            stock.setCantidad(stock.getCantidad().subtract(data.getCantidad()));
                        }
                    }
                    stock.setCnfProducto(data.getCnfProducto());
                    stock.setInvAlmacen(actComprobante.getInvAlmacen());
                    invAlmacenProductoRepository.save(stock);

                    InvMovimientoProducto mov = new InvMovimientoProducto();
                    mov.setCnfProducto(data.getCnfProducto());
                    mov.setInvAlmacen(actComprobante.getInvAlmacen());
                    mov.setActComprobante(actComprobante);
                    mov.setFecha(actComprobante.getFecha());
                    mov.setFechaRegistro(LocalDateTime.now());
                    mov.setCantidad(data.getCantidad().multiply(
                            actComprobante.getFlagIsventa().equals("1") ? BigDecimal.valueOf(-1) : BigDecimal.ONE));
                    mov.setValor(actComprobante.getFlagIsventa().equals("1") ? cnfProducto.getCosto() : data.getPrecio());
                    invMovimientoProductoRepository.save(mov);
                    
                    if (!actComprobante.getFlagIsventa().equals("1")) {
                        List<InvMovimientoProducto> listMovs = (List<InvMovimientoProducto>) 
                            invMovimientoProductoRepository.findByCnfProductoId(data.getCnfProducto().getId(), actComprobante.getInvAlmacen().getId());
                        BigDecimal costo = BigDecimal.ZERO;
                        BigDecimal costoTotal = BigDecimal.ZERO;
                        BigDecimal cantidad = BigDecimal.ZERO;
                        for (InvMovimientoProducto invMovimientoProducto : listMovs) {
                            //solo compras
                            if (invMovimientoProducto.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
                                cantidad = cantidad.add(invMovimientoProducto.getCantidad());
                                costoTotal = costoTotal.add(invMovimientoProducto.getCantidad().multiply(invMovimientoProducto.getValor()));
                            }
    //                        invMovimientoProducto.setCant(cantidad);
    //                        invMovimientoProducto.setCostoTotal(costoTotal);
    //                        invMovimientoProducto.setCosto(costo);
                        }
                        
                        costo = costoTotal.divide(cantidad, 2, RoundingMode.HALF_UP);
                        //actualizar precio, metodo promedio ponerado, proxima configuracion de empresa
                        cnfProducto.setCosto(costo);
                        cnfProducto.setPrecio(data.getPrecioVenta());
                        BigDecimal gananciaPorcentaje = 
                                data.getPrecioVenta().divide(costo, 2, RoundingMode.HALF_UP)
                                        .multiply(new BigDecimal("100.00")).subtract(new BigDecimal("100.00"));
                        cnfProducto.setPorcentajeGanancia(gananciaPorcentaje.floatValue());
                        cnfProductoRepository.save(cnfProducto);
                    }
                    
                }

            });
        }
        if (invMovAlmacen != null) {
            invMovimientoProductoRepository.deleteByInvMovAlmacen(invMovAlmacen);
            invMovAlmacen.getListInvMovAlmacenDet().forEach(data -> {
                if (!data.getCnfProducto().getCnfUnidadMedida().getCodigoSunat().equals("ZZ")) {
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
                    stock.setInvAlmacen(invMovAlmacen.getInvAlmacen());
                    stock.setCantidad(stock.getCantidad().subtract(data.getCantidad()));
                    invAlmacenProductoRepository.save(stock);

                    InvMovimientoProducto mov = new InvMovimientoProducto();
                    mov.setCnfProducto(data.getCnfProducto());
                    mov.setInvAlmacen(invMovAlmacen.getInvAlmacen());
                    mov.setFecha(invMovAlmacen.getFecha());
                    mov.setFechaRegistro(LocalDateTime.now());
                    mov.setCantidad(data.getCantidad().multiply(
                            invMovAlmacen.getInvTipoMovAlmacen().getNaturaleza().equals("-1") ? BigDecimal.valueOf(-1) : BigDecimal.ZERO));
                    mov.setValor(data.getPrecio());
                    invMovimientoProductoRepository.save(mov);
                }

            });
        }

    }
}
