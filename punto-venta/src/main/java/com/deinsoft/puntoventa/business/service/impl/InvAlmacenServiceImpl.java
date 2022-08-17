package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvAlmacen;
import com.deinsoft.puntoventa.business.repository.InvAlmacenRepository;
import com.deinsoft.puntoventa.business.service.InvAlmacenService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class InvAlmacenServiceImpl extends CommonServiceImpl<InvAlmacen,InvAlmacenRepository> implements InvAlmacenService  {
	@Autowired 
	InvAlmacenRepository invAlmacenRepository;
	
	public List<InvAlmacen> getAllInvAlmacen(InvAlmacen invAlmacen) {
		List<InvAlmacen> invAlmacenList = (List<InvAlmacen>)invAlmacenRepository.getAllInvAlmacen(invAlmacen.getNombre().toUpperCase(),invAlmacen.getFlagEstado().toUpperCase());
		return invAlmacenList;
	}
	public InvAlmacen getInvAlmacen(Long id) {
		InvAlmacen invAlmacen = null; 
		Optional<InvAlmacen> invAlmacenOptional = invAlmacenRepository.findById(id);
		if (invAlmacenOptional.isPresent()) {
			invAlmacen = invAlmacenOptional.get();
		}
		return invAlmacen;
	}
	
	public InvAlmacen saveInvAlmacen(InvAlmacen invAlmacen) {
		InvAlmacen invAlmacenResult = invAlmacenRepository.save(invAlmacen);
		return invAlmacenResult;
	}
	public List<InvAlmacen> getAllInvAlmacen() {
		List<InvAlmacen> invAlmacenList = (List<InvAlmacen>)invAlmacenRepository.findAll();
		return invAlmacenList;
	}
	public List<InvAlmacen> getAllInvAlmacenByCnfLocal(long id) {
		List<InvAlmacen> InvAlmacenList = (List<InvAlmacen>)invAlmacenRepository.findByCnfLocalId(id);
		return InvAlmacenList;
	}
	@Override
	public void delete(long id) {
		InvAlmacen invAlmacen =null;
		Optional<InvAlmacen> invAlmacenOptional = invAlmacenRepository.findById(id);
		
		if (invAlmacenOptional.isPresent()) {
			invAlmacen = invAlmacenOptional.get();
				invAlmacenRepository.delete(invAlmacen);
		}
	}
}
