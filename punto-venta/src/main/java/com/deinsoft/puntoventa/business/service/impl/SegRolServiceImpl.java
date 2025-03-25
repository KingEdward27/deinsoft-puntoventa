package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.SegRol;
import com.deinsoft.puntoventa.business.repository.SegRolRepository;
import com.deinsoft.puntoventa.business.service.SegRolService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class SegRolServiceImpl extends CommonServiceImpl<SegRol,Long, SegRolRepository> implements SegRolService {

    @Autowired
    SegRolRepository segRolRepository;

    public List<SegRol> getAllSegRol(SegRol segRol) {
        List<SegRol> segRolList = (List<SegRol>) segRolRepository.getAllSegRol(segRol.getNombre().toUpperCase());
        return segRolList;
    }

    public SegRol getSegRol(Long id) {
        SegRol segRol = null;
        Optional<SegRol> segRolOptional = segRolRepository.findById(id);
        if (segRolOptional.isPresent()) {
            segRol = segRolOptional.get();
        }
        return segRol;
    }

    public SegRol saveSegRol(SegRol segRol) {
        SegRol segRolResult = segRolRepository.save(segRol);
        return segRolResult;
    }

    public List<SegRol> getAllSegRol() {
        List<SegRol> segRolList = (List<SegRol>) segRolRepository.findAll();
        return segRolList;
    }

    @Override
    public void delete(long id) {
        SegRol segRol = null;
        Optional<SegRol> segRolOptional = segRolRepository.findById(id);

        if (segRolOptional.isPresent()) {
            segRol = segRolOptional.get();
            segRolRepository.delete(segRol);
        }
    }
}
