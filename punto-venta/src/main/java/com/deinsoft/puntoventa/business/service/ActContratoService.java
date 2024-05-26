package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.bean.UploadResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public interface ActContratoService extends CommonService<ActContrato> {

    public List<ActContrato> getAllActContrato(ActContrato actContrato);

    public ActContrato getActContrato(Long id);

    public ActContrato saveActContrato(ActContrato actContrato) throws Exception;

    public List<ActContrato> getAllActContrato();

    public List<ActContrato> getAllActContratoByCnfMaestro(long id);

    public List<ActContrato> getAllActContratoByCnfLocal(long id);

    public List<ActContrato> getAllActContratoByCnfTipoComprobante(long id);

    public List<ActContrato> getAllActContratoByCnfFormaPago(long id);

    public List<ActContrato> getAllActContratoByCnfPlanContrato(long id);

    public void delete(long id);
    
    public List<ActContrato> getReportActContratos(ParamBean paramBean);
    
    public Map<String,Object> getDashboardActContratos(long empresaId);
    
    public List<UploadResponse> importExcel(MultipartFile reapExcelDataFile,CnfLocal cnfLocal) throws IOException, Exception ;
}
