package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import java.time.LocalDate;

@Service
@Transactional
public interface ActPagoProgramacionService extends CommonService<ActPagoProgramacion> {

    public List<ActPagoProgramacion> getAllActPagoProgramacion(ActPagoProgramacion actPagoProgramacion);

    public ActPagoProgramacion getActPagoProgramacion(Long id);

    public ActPagoProgramacion saveActPagoProgramacion(ActPagoProgramacion actPagoProgramacion);

    public List<ActPagoProgramacion> getAllActPagoProgramacion();

    public List<ActPagoProgramacion> getAllActPagoProgramacionByActComprobante(long id);

    public void delete(long id);
    
    public List<ActPagoProgramacion> getAllActPagoProgramacionByCnfMaestro(long id, LocalDate fechaVencimiento, long cnfLocalId);
    
    public List<ActPagoProgramacion> getAllActPagoProgramacionCompraByCnfMaestro(long id, LocalDate fechaVencimiento);
}
