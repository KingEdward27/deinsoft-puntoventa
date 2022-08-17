package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import com.deinsoft.puntoventa.business.service.InvMovimientoProductoService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class InvMovimientoProductoServiceImpl extends CommonServiceImpl<InvMovimientoProducto,InvMovimientoProductoRepository> implements InvMovimientoProductoService  {
	@Autowired 
	InvMovimientoProductoRepository invMovimientoProductoRepository;
	
	public List<InvMovimientoProducto> getAllInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto) {
		List<InvMovimientoProducto> invMovimientoProductoList = (List<InvMovimientoProducto>)invMovimientoProductoRepository.getAllInvMovimientoProducto();
		return invMovimientoProductoList;
	}
	public InvMovimientoProducto getInvMovimientoProducto(Long id) {
		InvMovimientoProducto invMovimientoProducto = null; 
		Optional<InvMovimientoProducto> invMovimientoProductoOptional = invMovimientoProductoRepository.findById(id);
		if (invMovimientoProductoOptional.isPresent()) {
			invMovimientoProducto = invMovimientoProductoOptional.get();
		}
		return invMovimientoProducto;
	}
	
	public InvMovimientoProducto saveInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto) {
		InvMovimientoProducto invMovimientoProductoResult = invMovimientoProductoRepository.save(invMovimientoProducto);
		return invMovimientoProductoResult;
	}
	public List<InvMovimientoProducto> getAllInvMovimientoProducto() {
		List<InvMovimientoProducto> invMovimientoProductoList = (List<InvMovimientoProducto>)invMovimientoProductoRepository.findAll();
		return invMovimientoProductoList;
	}
	public List<InvMovimientoProducto> getAllInvMovimientoProductoByInvAlmacen(long id) {
		List<InvMovimientoProducto> InvMovimientoProductoList = (List<InvMovimientoProducto>)invMovimientoProductoRepository.findByInvAlmacenId(id);
		return InvMovimientoProductoList;
	}
	public List<InvMovimientoProducto> getAllInvMovimientoProductoByCnfProducto(long id) {
		List<InvMovimientoProducto> InvMovimientoProductoList = (List<InvMovimientoProducto>)invMovimientoProductoRepository.findByCnfProductoId(id);
		return InvMovimientoProductoList;
	}
	public List<InvMovimientoProducto> getAllInvMovimientoProductoByActComprobante(long id) {
		List<InvMovimientoProducto> InvMovimientoProductoList = (List<InvMovimientoProducto>)invMovimientoProductoRepository.findByActComprobanteId(id);
		return InvMovimientoProductoList;
	}
	@Override
	public void delete(long id) {
		InvMovimientoProducto invMovimientoProducto =null;
		Optional<InvMovimientoProducto> invMovimientoProductoOptional = invMovimientoProductoRepository.findById(id);
		
		if (invMovimientoProductoOptional.isPresent()) {
			invMovimientoProducto = invMovimientoProductoOptional.get();
				invMovimientoProductoRepository.delete(invMovimientoProducto);
		}
	}
}
