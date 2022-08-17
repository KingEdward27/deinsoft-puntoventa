package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.commons.service.CommonService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;

@Service
@Transactional
public interface CnfNumComprobanteService extends CommonService<CnfNumComprobante> {

    public List<CnfNumComprobante> getAllCnfNumComprobante(CnfNumComprobante cnfNumComprobante);

    public CnfNumComprobante getCnfNumComprobante(Long id);

    public CnfNumComprobante saveCnfNumComprobante(CnfNumComprobante cnfNumComprobante);

    public List<CnfNumComprobante> getAllCnfNumComprobante();

    public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfTipoComprobante(long id);

    public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfLocal(long id);

    public List<CnfNumComprobante> getCnfNumComprobanteByCnfTipoComprobanteAndCnfLocal(long id,long idLocal);
            
    public void delete(long id);
}
