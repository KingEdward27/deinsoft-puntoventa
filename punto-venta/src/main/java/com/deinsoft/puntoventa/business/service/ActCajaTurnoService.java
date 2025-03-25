package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.ActCajaTurno;

@Service
@Transactional
public interface ActCajaTurnoService extends CommonService<ActCajaTurno,Long> {

    public List<ActCajaTurno> getAllActCajaTurno(ActCajaTurno actCajaTurno);

    public ActCajaTurno getActCajaTurno(Long id);

    public ActCajaTurno saveActCajaTurno(ActCajaTurno actCajaTurno) throws Exception;

    public List<ActCajaTurno> getAllActCajaTurno();

    public List<ActCajaTurno> getAllActCajaTurnoBySegUsuario(long id);

    public void delete(long id);
    
    public List<ActCajaTurno> getReportActCajaTurno(ParamBean paramBean);

}
