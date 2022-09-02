package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.SegAccion;

@Service
@Transactional
public interface SegAccionService extends CommonService<SegAccion>{
	
	public List<SegAccion> getAllSegAccion(SegAccion segAccion);
	public SegAccion getSegAccion(Long id);
	public SegAccion saveSegAccion(SegAccion segAccion);
	public List<SegAccion> getAllSegAccion();
	public void delete(long id);
}
