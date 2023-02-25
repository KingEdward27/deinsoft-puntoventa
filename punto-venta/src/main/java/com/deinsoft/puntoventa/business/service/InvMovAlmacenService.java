package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.InvMovAlmacen;

@Service
@Transactional
public interface InvMovAlmacenService extends CommonService<InvMovAlmacen> {

    public List<InvMovAlmacen> getAllInvMovAlmacen(InvMovAlmacen invMovAlmacen);

    public InvMovAlmacen getInvMovAlmacen(Long id);

    public InvMovAlmacen saveInvMovAlmacen(InvMovAlmacen invMovAlmacen) throws Exception;

    public List<InvMovAlmacen> getAllInvMovAlmacen();

    public List<InvMovAlmacen> getAllInvMovAlmacenByInvTipoMovAlmacen(long id);

    public List<InvMovAlmacen> getAllInvMovAlmacenByCnfMaestro(long id);

    public List<InvMovAlmacen> getAllInvMovAlmacenByCnfLocal(long id);

    public List<InvMovAlmacen> getAllInvMovAlmacenByCnfTipoComprobante(long id);

    public List<InvMovAlmacen> getAllInvMovAlmacenByInvAlmacen(long id);

    public void delete(long id);
    
    public void validate (InvMovAlmacen invMovAlmacen);
}
