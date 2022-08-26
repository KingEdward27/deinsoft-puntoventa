package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.SegUsuarioRepository;
import com.deinsoft.puntoventa.business.service.SegUsuarioService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class SegUsuarioServiceImpl extends CommonServiceImpl<SegUsuario,SegUsuarioRepository> implements SegUsuarioService  {
	@Autowired 
	SegUsuarioRepository segUsuarioRepository;
	
	public List<SegUsuario> getAllSegUsuario(SegUsuario segUsuario) {
		List<SegUsuario> segUsuarioList = (List<SegUsuario>)segUsuarioRepository.getAllSegUsuario(segUsuario.getNombre().toUpperCase(),segUsuario.getEmail().toUpperCase(),segUsuario.getPassword().toUpperCase());
		return segUsuarioList;
	}
	public SegUsuario getSegUsuario(Long id) {
		SegUsuario segUsuario = null; 
		Optional<SegUsuario> segUsuarioOptional = segUsuarioRepository.findById(id);
		if (segUsuarioOptional.isPresent()) {
			segUsuario = segUsuarioOptional.get();
		}
		return segUsuario;
	}
	
	public SegUsuario saveSegUsuario(SegUsuario segUsuario) {
		SegUsuario segUsuarioResult = segUsuarioRepository.save(segUsuario);
		return segUsuarioResult;
	}
	public List<SegUsuario> getAllSegUsuario() {
		List<SegUsuario> segUsuarioList = (List<SegUsuario>)segUsuarioRepository.findAll();
		return segUsuarioList;
	}
	@Override
	public void delete(long id) {
		SegUsuario segUsuario =null;
		Optional<SegUsuario> segUsuarioOptional = segUsuarioRepository.findById(id);
		
		if (segUsuarioOptional.isPresent()) {
			segUsuario = segUsuarioOptional.get();
				segUsuarioRepository.delete(segUsuario);
		}
	}
}
