package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.GeneratedFile;
import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import java.text.ParseException;

@Service
@Transactional
public interface ActComprobanteService extends CommonService<ActComprobante> {

    public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante);

    public ActComprobante getActComprobante(Long id);

    public ActComprobante saveActComprobante(ActComprobante actComprobante) throws Exception;

    public ActComprobante saveActComprobanteCompra(ActComprobante actComprobante) throws Exception;
            
    public List<ActComprobante> getAllActComprobante();

    public List<ActComprobante> getAllActComprobanteByCnfMaestro(long id);

    public List<ActComprobante> getAllActComprobanteByCnfFormaPago(long id);

    public List<ActComprobante> getAllActComprobanteByCnfMoneda(long id);

    public List<ActComprobante> getAllActComprobanteByCnfLocal(long id);

    public List<ActComprobante> getAllActComprobanteByCnfTipoComprobante(long id);

    public List<ActComprobante> getAllActComprobanteByInvAlmacen(long id);

    public void delete(long id);
    
    public List<ActComprobante> getReportActComprobante(ParamBean paramBean);
    
    public String invalidate(long id)  throws Exception;
    
    void validate(long id) throws Exception;
    
    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception;
    
    public GeneratedFile generateSireTxt(ParamBean paramBean);
}
