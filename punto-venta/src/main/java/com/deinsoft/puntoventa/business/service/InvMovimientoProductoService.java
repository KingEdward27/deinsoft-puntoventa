package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;

import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import java.math.BigDecimal;

@Service
@Transactional
public interface InvMovimientoProductoService extends CommonService<InvMovimientoProducto> {

    public List<InvMovimientoProducto> getAllInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto);

    public InvMovimientoProducto getInvMovimientoProducto(Long id);

    public InvMovimientoProducto saveInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto);

    public List<InvMovimientoProducto> getAllInvMovimientoProducto();

    public List<InvMovimientoProducto> getAllInvMovimientoProductoByInvAlmacen(long id);

    public List<InvMovimientoProducto> getAllInvMovimientoProductoByCnfProducto(long id,long idAlmacen);

    public List<InvMovimientoProducto> getAllInvMovimientoProductoByActComprobante(long id);

    public void delete(long id);
    
    public List<InvMovimientoProducto> getReportInvMovimientoProducto(ParamBean paramBean);
    
    public BigDecimal getSaldoReportInvMovimientoProducto(ParamBean paramBean);
}
