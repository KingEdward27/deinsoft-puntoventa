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
import com.deinsoft.puntoventa.business.model.InvMovAlmacen;
import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class InvAlmacenProductoServiceImpl
        extends CommonServiceImpl<InvAlmacenProducto, InvAlmacenProductoRepository> implements InvAlmacenProductoService {

    @Autowired
    InvMovimientoProductoRepository invMovimientoProductoRepository;

    @Autowired
    InvAlmacenProductoRepository invAlmacenProductoRepository;

    public List<InvAlmacenProducto> getAllInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto) {
        List<InvAlmacenProducto> invAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.getAllInvAlmacenProducto();
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
        List<InvAlmacenProducto> list = (List<InvAlmacenProducto>) invAlmacenProductoRepository.getReportInvAlmacen(paramBean);
        return list;
    }

    @Override
    public void registerProductMovementAndUpdateStock(ActComprobante actComprobante, InvMovAlmacen invMovAlmacen) {
        if (actComprobante != null) {
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                if (!data.getCnfProducto().getCnfUnidadMedida().getCodigoSunat().equals("ZZ")) {
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
                    mov.setValor(data.getPrecio());
                    invMovimientoProductoRepository.save(mov);
                }

            });
        }
        if (invMovAlmacen != null) {
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
