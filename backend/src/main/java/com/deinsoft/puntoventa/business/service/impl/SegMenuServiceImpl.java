package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.SegMenu;
import com.deinsoft.puntoventa.business.repository.SegMenuRepository;
import com.deinsoft.puntoventa.business.service.SegMenuService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class SegMenuServiceImpl extends CommonServiceImpl<SegMenu,SegMenuRepository> implements SegMenuService  {
	@Autowired 
	SegMenuRepository segMenuRepository;
	
	public List<SegMenu> getAllSegMenu(SegMenu segMenu) {
		List<SegMenu> segMenuList = (List<SegMenu>)segMenuRepository.getAllSegMenu(segMenu.getNombre().toUpperCase(),segMenu.getIcon().toUpperCase(),segMenu.getPath().toUpperCase());
		return segMenuList;
	}
	public SegMenu getSegMenu(Long id) {
		SegMenu segMenu = null; 
		Optional<SegMenu> segMenuOptional = segMenuRepository.findById(id);
		if (segMenuOptional.isPresent()) {
			segMenu = segMenuOptional.get();
		}
		return segMenu;
	}
	
	public SegMenu saveSegMenu(SegMenu segMenu) {
		SegMenu segMenuResult = segMenuRepository.save(segMenu);
		return segMenuResult;
	}
	public List<SegMenu> getAllSegMenu() {
		List<SegMenu> segMenuList = (List<SegMenu>)segMenuRepository.findAll();
		return segMenuList;
	}
	public List<SegMenu> getAllSegMenuBySegMenu(long id) {
		List<SegMenu> SegMenuList = (List<SegMenu>)segMenuRepository.findBySegMenuId(id);
		return SegMenuList;
	}
	@Override
	public void delete(long id) {
		SegMenu segMenu =null;
		Optional<SegMenu> segMenuOptional = segMenuRepository.findById(id);
		
		if (segMenuOptional.isPresent()) {
			segMenu = segMenuOptional.get();
				segMenuRepository.delete(segMenu);
		}
	}
}
