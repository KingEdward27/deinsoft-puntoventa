package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.SegPermiso;

@Service
@Transactional
public interface SegPermisoService extends CommonService<SegPermiso,Long> {

    public List<SegPermiso> getAllSegPermiso(SegPermiso segPermiso);

    public SegPermiso getSegPermiso(Long id);

    public SegPermiso saveSegPermiso(SegPermiso segPermiso);

    public List<SegPermiso> getAllSegPermiso();

    public List<SegPermiso> getAllSegPermisoBySegRol(long id);

    public List<SegPermiso> getAllSegPermisoBySegMenu(long id);

    public List<SegPermiso> getAllSegPermisoBySegAccion(long id);

    public void delete(long id);
    
    public List<SegPermiso> getAllSegPermisoBySegRolNombre(String nombre);
}
