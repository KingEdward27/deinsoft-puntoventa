package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import com.deinsoft.puntoventa.business.dto.InvMovimientoProductoDTO;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class InvMovimientoProductoServiceImpl 
        extends CommonServiceImpl<InvMovimientoProducto,Long, InvMovimientoProductoRepository>
        implements InvMovimientoProductoService {

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
        List<InvMovimientoProducto> InvMovimientoProductoList = (List<InvMovimientoProducto>) invMovimientoProductoRepository
                .findByCnfProductoIdAndInvAlmacenId(id,idAlmacen);
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
    public List<InvMovimientoProductoDTO> getReportInvMovimientoProducto(ParamBean paramBean) {
        
        List<InvMovimientoProductoDTO> list = invMovimientoProductoRepository.getReportInvMovimientoProducto(paramBean)
                        .stream()
                        .filter(predicate -> predicate.getCnfProducto().getListCnfPaqueteDet().isEmpty())
                        .map(data -> {
                            String tipo;
                            if (data.getActComprobante() != null) {
                                tipo = data.getActComprobante().getFlagIsventa().equals("1") ? "Venta" : "Compra";
                            } else if (data.getInvMovAlmacen() != null) {
                                tipo = data.getInvMovAlmacen().getInvTipoMovAlmacen().getNaturaleza().equals("1") ? "Movimiento de ingreso" : "Movimiento de salida";
                            } else {
                                tipo = "";
                            }
                            return InvMovimientoProductoDTO.builder()
                                    .local(data.getInvAlmacen().getCnfLocal().getNombre())
                                    .almacen(data.getInvAlmacen().getNombre())
                                    .producto(data.getCnfProducto().getNombre())
                                    .cantidad(data.getCantidad())
                                    .tipoMovimiento(tipo)
                                    .valor(data.getValor())
                                    .simboloMoneda(data.getInvAlmacen()
                                        .getCnfLocal().getCnfEmpresa().getCnfMoneda().getSimbolo())
                                    .forDiscount((data.getActComprobante() != null
                                                    && data.getActComprobante().getFlagIsventa().equals("1"))
                                            || (data.getInvMovAlmacen() != null
                                                    && data.getInvMovAlmacen().getInvTipoMovAlmacen().getNaturaleza().equals("-1")))
                                    .build();
                        })
                        .collect(Collectors.toList());;
        BigDecimal costo = BigDecimal.ZERO;
        BigDecimal costoTotal = BigDecimal.ZERO;
        BigDecimal cantidadTotal = BigDecimal.ZERO;
        for (InvMovimientoProductoDTO invMovimientoProducto : list) {
            
            cantidadTotal = cantidadTotal.add(invMovimientoProducto.getCantidad());
            costoTotal = costoTotal.add(invMovimientoProducto.getCantidad().multiply(invMovimientoProducto.getValor()));
            if (cantidadTotal.compareTo(BigDecimal.ZERO) == 0) {
                costoTotal = BigDecimal.ZERO;
            } else {
                costo = costoTotal.divide(cantidadTotal, 2, RoundingMode.HALF_UP);
            }
            
//            costo = invMovimientoProducto.getActComprobante()
//                    .getListActComprobanteDetalle().stream()
//                    .filter(predicate -> predicate.getCnfProducto().getId() == invMovimientoProducto.getCnfProducto().getId())
//                    .findFirst().orElse(new ActComprobanteDetalle()).getCnfProducto().getCosto();
//            costoTotal = costoTotal.add(invMovimientoProducto.getCantidad().multiply(costo));

//            if (invMovimientoProducto.getSimboloMoneda() == null) {
//                invMovimientoProducto.setSimboloMoneda(invMovimientoProducto.getInvAlmacen()
//                        .getCnfLocal().getCnfEmpresa().getCnfMoneda().getSimbolo());
//            }
            if (invMovimientoProducto.isForDiscount()) {
                invMovimientoProducto.setCantidadDescuento(invMovimientoProducto.getCantidad());
                invMovimientoProducto.setValorDescuento(invMovimientoProducto.getValor());

                invMovimientoProducto.setCantidad(BigDecimal.ZERO);
                invMovimientoProducto.setValor(BigDecimal.ZERO);
            } else {
                invMovimientoProducto.setCantidadDescuento(BigDecimal.ZERO);
                invMovimientoProducto.setValorDescuento(BigDecimal.ZERO);

                invMovimientoProducto.setCantidad(invMovimientoProducto.getCantidad());
            }
            invMovimientoProducto.setCantidadTotal(cantidadTotal);
            invMovimientoProducto.setCostoTotal(costoTotal);
            invMovimientoProducto.setCosto(costo);
        }
        return list;
    }
    @Override
    public BigDecimal getSaldoReportInvMovimientoProducto(ParamBean paramBean) {
        List<InvMovimientoProducto> list = (List<InvMovimientoProducto>) 
                invMovimientoProductoRepository.getSaldoReportInvMovimientoProducto(paramBean)
                        .stream().filter(predicate -> predicate.getCnfProducto().getListCnfPaqueteDet().isEmpty())
                        .collect(Collectors.toList());
        BigDecimal saldoInicial = BigDecimal.ZERO;
        
        for (InvMovimientoProducto invMovimientoProducto : list) {
            saldoInicial = saldoInicial.add(invMovimientoProducto.getCantidad());
        }
        return saldoInicial;
    }
}
