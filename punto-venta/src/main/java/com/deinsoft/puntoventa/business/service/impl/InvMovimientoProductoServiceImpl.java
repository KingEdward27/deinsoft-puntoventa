package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import com.deinsoft.puntoventa.business.service.InvMovimientoProductoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;
import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
@Transactional
public class InvMovimientoProductoServiceImpl 
        extends CommonServiceImpl<InvMovimientoProducto, InvMovimientoProductoRepository> implements InvMovimientoProductoService {

    @Autowired
    InvMovimientoProductoRepository invMovimientoProductoRepository;

    public List<InvMovimientoProducto> getAllInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto) {
        List<InvMovimientoProducto> invMovimientoProductoList = (List<InvMovimientoProducto>) invMovimientoProductoRepository.getAllInvMovimientoProducto();
        return invMovimientoProductoList;
    }

    public InvMovimientoProducto getInvMovimientoProducto(Long id) {
        InvMovimientoProducto invMovimientoProducto = null;
        Optional<InvMovimientoProducto> invMovimientoProductoOptional = invMovimientoProductoRepository.findById(id);
        if (invMovimientoProductoOptional.isPresent()) {
            invMovimientoProducto = invMovimientoProductoOptional.get();
        }
        return invMovimientoProducto;
    }

    public InvMovimientoProducto saveInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto) {
        InvMovimientoProducto invMovimientoProductoResult = invMovimientoProductoRepository.save(invMovimientoProducto);
        return invMovimientoProductoResult;
    }

    public List<InvMovimientoProducto> getAllInvMovimientoProducto() {
        List<InvMovimientoProducto> invMovimientoProductoList = (List<InvMovimientoProducto>) invMovimientoProductoRepository.findAll();
        return invMovimientoProductoList;
    }

    public List<InvMovimientoProducto> getAllInvMovimientoProductoByInvAlmacen(long id) {
        List<InvMovimientoProducto> InvMovimientoProductoList = (List<InvMovimientoProducto>) invMovimientoProductoRepository.findByInvAlmacenId(id);
        return InvMovimientoProductoList;
    }

    public List<InvMovimientoProducto> getAllInvMovimientoProductoByCnfProducto(long id,long idAlmacen) {
        List<InvMovimientoProducto> InvMovimientoProductoList = (List<InvMovimientoProducto>) invMovimientoProductoRepository.findByCnfProductoId(id,idAlmacen);
        return InvMovimientoProductoList;
    }

    public List<InvMovimientoProducto> getAllInvMovimientoProductoByActComprobante(long id) {
        List<InvMovimientoProducto> InvMovimientoProductoList = (List<InvMovimientoProducto>) invMovimientoProductoRepository.findByActComprobanteId(id);
        return InvMovimientoProductoList;
    }

    @Override
    public void delete(long id) {
        InvMovimientoProducto invMovimientoProducto = null;
        Optional<InvMovimientoProducto> invMovimientoProductoOptional = invMovimientoProductoRepository.findById(id);

        if (invMovimientoProductoOptional.isPresent()) {
            invMovimientoProducto = invMovimientoProductoOptional.get();
            invMovimientoProductoRepository.delete(invMovimientoProducto);
        }
    }
    @Override
    @Transactional
    public List<InvMovimientoProducto> getReportInvMovimientoProducto(ParamBean paramBean) {
        
        List<InvMovimientoProducto> list = (List<InvMovimientoProducto>) 
                invMovimientoProductoRepository.getReportInvMovimientoProducto(paramBean);
        BigDecimal costo = BigDecimal.ZERO;
        BigDecimal costoTotal = BigDecimal.ZERO;
        BigDecimal cantidad = BigDecimal.ZERO;
        for (InvMovimientoProducto invMovimientoProducto : list) {
            
            cantidad = cantidad.add(invMovimientoProducto.getCantidad());
            costoTotal = costoTotal.add(invMovimientoProducto.getCantidad().multiply(invMovimientoProducto.getValor()));
            costo = costoTotal.divide(cantidad, 2, RoundingMode.HALF_UP);
//            costo = invMovimientoProducto.getActComprobante()
//                    .getListActComprobanteDetalle().stream()
//                    .filter(predicate -> predicate.getCnfProducto().getId() == invMovimientoProducto.getCnfProducto().getId())
//                    .findFirst().orElse(new ActComprobanteDetalle()).getCnfProducto().getCosto();
//            costoTotal = costoTotal.add(invMovimientoProducto.getCantidad().multiply(costo));
            invMovimientoProducto.setCant(cantidad);
            invMovimientoProducto.setCostoTotal(costoTotal);
            invMovimientoProducto.setCosto(costo);
        }
        return list;
    }
    @Override
    public BigDecimal getSaldoReportInvMovimientoProducto(ParamBean paramBean) {
        List<InvMovimientoProducto> list = (List<InvMovimientoProducto>) 
                invMovimientoProductoRepository.getSaldoReportInvMovimientoProducto(paramBean);
        BigDecimal saldoInicial = BigDecimal.ZERO;
        
        for (InvMovimientoProducto invMovimientoProducto : list) {
            saldoInicial = saldoInicial.add(invMovimientoProducto.getCantidad());
        }
        return saldoInicial;
    }
}
