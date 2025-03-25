package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfMaestro;

@Service
@Transactional
public interface CnfMaestroService extends CommonService<CnfMaestro,Long> {

    public List<CnfMaestro> getAllCnfMaestro(CnfMaestro cnfMaestro);

    public CnfMaestro getCnfMaestro(Long id);

    public CnfMaestro saveCnfMaestro(CnfMaestro cnfMaestro)  throws Exception;

    public List<CnfMaestro> getAllCnfMaestro();

    public List<CnfMaestro> getAllCnfMaestroByCnfTipoDocumento(long id);

    public List<CnfMaestro> getAllCnfMaestroByCnfEmpresa(long id);

    public List<CnfMaestro> getAllCnfMaestroByCnfDistrito(long id);

    public void delete(long id);

    public List<CnfMaestro> getAllCnfMaestroTypeHead(String nameOrValue);
}
