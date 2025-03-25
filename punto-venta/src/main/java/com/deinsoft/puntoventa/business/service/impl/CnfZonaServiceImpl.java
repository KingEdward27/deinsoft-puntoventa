package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfZona;
import com.deinsoft.puntoventa.business.repository.CnfZonaRepository;
import com.deinsoft.puntoventa.business.service.CnfZonaService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfZonaServiceImpl extends CommonServiceImpl<CnfZona,Long, CnfZonaRepository> implements CnfZonaService {

    @Autowired
    CnfZonaRepository cnfZonaRepository;

    public List<CnfZona> getAllCnfZona(CnfZona cnfZona) {
        List<CnfZona> cnfZonaList = (List<CnfZona>) cnfZonaRepository.getAllCnfZona(
                cnfZona.getNombre().toUpperCase());
        return cnfZonaList;
    }

    public CnfZona getCnfZona(Long id) {
        CnfZona cnfZona = null;
        Optional<CnfZona> cnfZonaOptional = cnfZonaRepository.findById(id);
        if (cnfZonaOptional.isPresent()) {
            cnfZona = cnfZonaOptional.get();
        }
        return cnfZona;
    }

    public CnfZona saveCnfZona(CnfZona cnfZona) {
        CnfZona cnfZonaResult = cnfZonaRepository.save(cnfZona);
        return cnfZonaResult;
    }

    public List<CnfZona> saveAll(List<CnfZona> cnfZona) {
        List<CnfZona> cnfZonaResult = cnfZonaRepository.saveAll(cnfZona);
        return cnfZonaResult;
    }
    
    public List<CnfZona> getAllCnfZona() {
        List<CnfZona> cnfZonaList = (List<CnfZona>) cnfZonaRepository.findAll();
        return cnfZonaList;
    }

    public List<CnfZona> getAllCnfZonaByCnfEmpresa(long id) {
        List<CnfZona> CnfZonaList = (List<CnfZona>) cnfZonaRepository.findByCnfEmpresaId(id);
        return CnfZonaList;
    }

    @Override
    public void delete(long id) {
        CnfZona cnfZona = null;
        Optional<CnfZona> cnfZonaOptional = cnfZonaRepository.findById(id);

        if (cnfZonaOptional.isPresent()) {
            cnfZona = cnfZonaOptional.get();
            cnfZonaRepository.delete(cnfZona);
        }
    }
}
