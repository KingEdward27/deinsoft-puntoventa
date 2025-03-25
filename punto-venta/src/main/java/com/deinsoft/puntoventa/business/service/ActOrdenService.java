package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.GeneratedFile;
import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.dto.ReporteContableDto;

import com.deinsoft.puntoventa.business.model.ActOrden;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import java.text.ParseException;
import java.util.Map;

@Service
@Transactional
public interface ActOrdenService extends CommonService<ActOrden,Long> {

    public List<ActOrden> getAllActOrden(ActOrden actOrden);

    public ActOrden getActOrden(Long id);

    public ActOrden saveActOrden(ActOrden actOrden) throws Exception;

    public ActOrden saveActOrdenCompra(ActOrden actOrden) throws Exception;
            
    public List<ActOrden> getAllActOrden();

    public List<ActOrden> getAllActOrdenByCnfMaestro(long id);

    public List<ActOrden> getAllActOrdenByCnfFormaPago(long id);

    public List<ActOrden> getAllActOrdenByCnfMoneda(long id);

    public List<ActOrden> getAllActOrdenByCnfLocal(long id);

    public List<ActOrden> getAllActOrdenByCnfTipoComprobante(long id);

    public List<ActOrden> getAllActOrdenByInvAlmacen(long id);

    public void delete(long id);
    
    public List<ActOrden> getReportActOrden(ParamBean paramBean);
    
    public String invalidate(long id)  throws Exception;
    
    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception;
    
    public GeneratedFile generateSireTxt(ParamBean paramBean) throws Exception ;
    
    List<ReporteContableDto> getListaReporteContable (Long cnfLocalId);
    
    public Map<String, Object> getDashboardActOrdens(ParamBean param);
}
