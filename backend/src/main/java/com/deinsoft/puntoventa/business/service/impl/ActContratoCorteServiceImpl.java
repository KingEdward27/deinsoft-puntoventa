package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActContratoCorte;
import com.deinsoft.puntoventa.business.repository.ActContratoCorteRepository;
import com.deinsoft.puntoventa.business.service.ActContratoCorteService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class ActContratoCorteServiceImpl extends CommonServiceImpl<ActContratoCorte,ActContratoCorteRepository> implements ActContratoCorteService  {
	@Autowired 
	ActContratoCorteRepository actContratoCorteRepository;
	
	public List<ActContratoCorte> getAllActContratoCorte(ActContratoCorte actContratoCorte) {
		List<ActContratoCorte> actContratoCorteList = (List<ActContratoCorte>)actContratoCorteRepository.getAllActContratoCorte(actContratoCorte.getFlgEstado().toUpperCase());
		return actContratoCorteList;
	}
	public ActContratoCorte getActContratoCorte(Long id) {
		ActContratoCorte actContratoCorte = null; 
		Optional<ActContratoCorte> actContratoCorteOptional = actContratoCorteRepository.findById(id);
		if (actContratoCorteOptional.isPresent()) {
			actContratoCorte = actContratoCorteOptional.get();
		}
		return actContratoCorte;
	}
	
	public ActContratoCorte saveActContratoCorte(ActContratoCorte actContratoCorte) {
		ActContratoCorte actContratoCorteResult = actContratoCorteRepository.save(actContratoCorte);
		return actContratoCorteResult;
	}
	public List<ActContratoCorte> getAllActContratoCorte() {
		List<ActContratoCorte> actContratoCorteList = (List<ActContratoCorte>)actContratoCorteRepository.findAll();
		return actContratoCorteList;
	}
	public List<ActContratoCorte> getAllActContratoCorteByActContrato(long id) {
		List<ActContratoCorte> ActContratoCorteList = (List<ActContratoCorte>)actContratoCorteRepository.findByActContratoId(id);
		return ActContratoCorteList;
	}
	public List<ActContratoCorte> getAllActContratoCorteBySegUsuario(long id) {
		List<ActContratoCorte> ActContratoCorteList = (List<ActContratoCorte>)actContratoCorteRepository.findBySegUsuarioId(id);
		return ActContratoCorteList;
	}
	@Override
	public void delete(long id) {
		ActContratoCorte actContratoCorte =null;
		Optional<ActContratoCorte> actContratoCorteOptional = actContratoCorteRepository.findById(id);
		
		if (actContratoCorteOptional.isPresent()) {
			actContratoCorte = actContratoCorteOptional.get();
				actContratoCorteRepository.delete(actContratoCorte);
		}
	}
}