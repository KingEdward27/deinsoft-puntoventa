package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.ActCajaOperacion;

@Service
@Transactional
public interface ActCajaOperacionService extends CommonService<ActCajaOperacion> {

    public List<ActCajaOperacion> getAllActCajaOperacion(ActCajaOperacion actCajaOperacion);

    public ActCajaOperacion getActCajaOperacion(Long id);

    public ActCajaOperacion saveActCajaOperacion(ActCajaOperacion actCajaOperacion);

    public List<ActCajaOperacion> getAllActCajaOperacion();

    public List<ActCajaOperacion> getAllActCajaOperacionByActCajaTurno(long id);

    public List<ActCajaOperacion> getAllActCajaOperacionByActComprobante(long id);

    public List<ActCajaOperacion> getAllActCajaOperacionByActPago(long id);

    public void delete(long id);
    
    public List<ActCajaOperacion> getReportActCajaOperacion(ParamBean paramBean);
}
