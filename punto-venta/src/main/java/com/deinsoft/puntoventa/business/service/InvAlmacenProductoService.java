package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.model.ActComprobante;

import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.model.InvMovAlmacen;

@Service
@Transactional
public interface InvAlmacenProductoService extends CommonService<InvAlmacenProducto,Long> {

    public List<InvAlmacenProducto> getAllInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto);

    public InvAlmacenProducto getInvAlmacenProducto(Long id);

    public InvAlmacenProducto saveInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto);

    public List<InvAlmacenProducto> getAllInvAlmacenProducto();

    public List<InvAlmacenProducto> getAllInvAlmacenProductoByInvAlmacen(long id);

    public List<InvAlmacenProducto> getAllInvAlmacenProductoByCnfProducto(long id);

    public void delete(long id);
    
    public List<InvAlmacenProducto> getReportInvAlmacen(ParamBean paramBean);
    
    void registerProductMovementAndUpdateStock(ActComprobante actComprobante, InvMovAlmacen invMovAlmacen);
}
