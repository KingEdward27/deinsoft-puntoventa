package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.ActCaja;

@Service
@Transactional
public interface ActCajaService extends CommonService<ActCaja,Long>{
	
	public List<ActCaja> getAllActCaja(ActCaja actCaja);
	public ActCaja getActCaja(Long id);
	public ActCaja saveActCaja(ActCaja actCaja);
	public List<ActCaja> getAllActCaja();
	public void delete(long id);
        
        public List<ActCaja> getAllActCajaByCnfEmpresa(long id);

	public List<ActCaja> getAllActCajaByCnfLocal(long id, long localId);
}
