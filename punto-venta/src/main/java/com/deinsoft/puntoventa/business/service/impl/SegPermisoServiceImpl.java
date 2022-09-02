package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.SegPermiso;
import com.deinsoft.puntoventa.business.repository.SegPermisoRepository;
import com.deinsoft.puntoventa.business.service.SegPermisoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class SegPermisoServiceImpl extends CommonServiceImpl<SegPermiso, SegPermisoRepository> implements SegPermisoService {

    @Autowired
    SegPermisoRepository segPermisoRepository;

    public List<SegPermiso> getAllSegPermiso(SegPermiso segPermiso) {
        List<SegPermiso> segPermisoList = (List<SegPermiso>) segPermisoRepository.getAllSegPermiso();
        return segPermisoList;
    }

    public SegPermiso getSegPermiso(Long id) {
        SegPermiso segPermiso = null;
        Optional<SegPermiso> segPermisoOptional = segPermisoRepository.findById(id);
        if (segPermisoOptional.isPresent()) {
            segPermiso = segPermisoOptional.get();
        }
        return segPermiso;
    }

    public SegPermiso saveSegPermiso(SegPermiso segPermiso) {
        SegPermiso segPermisoResult = segPermisoRepository.save(segPermiso);
        return segPermisoResult;
    }

    public List<SegPermiso> getAllSegPermiso() {
        List<SegPermiso> segPermisoList = (List<SegPermiso>) segPermisoRepository.findAll();
        return segPermisoList;
    }

    public List<SegPermiso> getAllSegPermisoBySegRol(long id) {
        List<SegPermiso> SegPermisoList = (List<SegPermiso>) segPermisoRepository.findBySegRolId(id);
        return SegPermisoList;
    }

    public List<SegPermiso> getAllSegPermisoBySegMenu(long id) {
        List<SegPermiso> SegPermisoList = (List<SegPermiso>) segPermisoRepository.findBySegMenuId(id);
        return SegPermisoList;
    }

    public List<SegPermiso> getAllSegPermisoBySegAccion(long id) {
        List<SegPermiso> SegPermisoList = (List<SegPermiso>) segPermisoRepository.findBySegAccionId(id);
        return SegPermisoList;
    }

    @Override
    public void delete(long id) {
        SegPermiso segPermiso = null;
        Optional<SegPermiso> segPermisoOptional = segPermisoRepository.findById(id);

        if (segPermisoOptional.isPresent()) {
            segPermiso = segPermisoOptional.get();
            segPermisoRepository.delete(segPermiso);
        }
    }
    
    public List<SegPermiso> getAllSegPermisoBySegRolNombre(String nombre) {
        List<SegPermiso> SegPermisoList = (List<SegPermiso>) segPermisoRepository.findBySegRolNonmbre(nombre);
        return SegPermisoList;
    }
}
