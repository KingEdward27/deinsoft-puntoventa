package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.InvTipoMovAlmacen;

@Service
@Transactional
public interface InvTipoMovAlmacenService extends CommonService<InvTipoMovAlmacen> {

    public List<InvTipoMovAlmacen> getAllInvTipoMovAlmacen(InvTipoMovAlmacen invTipoMovAlmacen);

    public InvTipoMovAlmacen getInvTipoMovAlmacen(Long id);

    public InvTipoMovAlmacen saveInvTipoMovAlmacen(InvTipoMovAlmacen invTipoMovAlmacen);

    public List<InvTipoMovAlmacen> getAllInvTipoMovAlmacen();

    public void delete(long id);
}
