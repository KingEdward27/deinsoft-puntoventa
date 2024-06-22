package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfZona;

@Service
@Transactional
public interface CnfZonaService extends CommonService<CnfZona> {

    public List<CnfZona> getAllCnfZona(CnfZona cnfZona);

    public CnfZona getCnfZona(Long id);

    public CnfZona saveCnfZona(CnfZona cnfZona);

    public List<CnfZona> getAllCnfZona();

    public List<CnfZona> getAllCnfZonaByCnfEmpresa(long id);

    public void delete(long id);
    
    public List<CnfZona> saveAll(List<CnfZona> cnfZona) ;
}
