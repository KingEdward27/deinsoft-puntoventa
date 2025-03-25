package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.SegMenu;

@Service
@Transactional
public interface SegMenuService extends CommonService<SegMenu,Long>{
	
	public List<SegMenu> getAllSegMenu(SegMenu segMenu);
	public SegMenu getSegMenu(Long id);
	public SegMenu saveSegMenu(SegMenu segMenu);
	public List<SegMenu> getAllSegMenu();
  public List<SegMenu> getAllSegMenuBySegMenu(long id);
	public void delete(long id);
}
