package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.repository.CnfProductoRepository;
import com.deinsoft.puntoventa.business.service.CnfProductoService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfProductoServiceImpl extends CommonServiceImpl<CnfProducto,CnfProductoRepository> implements CnfProductoService  {
	@Autowired 
	CnfProductoRepository cnfProductoRepository;
	
	public List<CnfProducto> getAllCnfProducto(CnfProducto cnfProducto) {
		List<CnfProducto> cnfProductoList = (List<CnfProducto>)cnfProductoRepository.getAllCnfProducto(cnfProducto.getCodigo().toUpperCase(),cnfProducto.getNombre().toUpperCase(),cnfProducto.getRutaImagen().toUpperCase(),cnfProducto.getFlagEstado().toUpperCase(),cnfProducto.getBarcode().toUpperCase());
		return cnfProductoList;
	}
	public CnfProducto getCnfProducto(Long id) {
		CnfProducto cnfProducto = null; 
		Optional<CnfProducto> cnfProductoOptional = cnfProductoRepository.findById(id);
		if (cnfProductoOptional.isPresent()) {
			cnfProducto = cnfProductoOptional.get();
		}
		return cnfProducto;
	}
	
	public CnfProducto saveCnfProducto(CnfProducto cnfProducto) {
		CnfProducto cnfProductoResult = cnfProductoRepository.save(cnfProducto);
		return cnfProductoResult;
	}
	public List<CnfProducto> getAllCnfProducto() {
		List<CnfProducto> cnfProductoList = (List<CnfProducto>)cnfProductoRepository.findAll();
		return cnfProductoList;
	}
	public List<CnfProducto> getAllCnfProductoByCnfUnidadMedida(long id) {
		List<CnfProducto> CnfProductoList = (List<CnfProducto>)cnfProductoRepository.findByCnfUnidadMedidaId(id);
		return CnfProductoList;
	}
	public List<CnfProducto> getAllCnfProductoByCnfEmpresa(long id) {
		List<CnfProducto> CnfProductoList = (List<CnfProducto>)cnfProductoRepository.findByCnfEmpresaId(id);
		return CnfProductoList;
	}
	public List<CnfProducto> getAllCnfProductoByCnfSubCategoria(long id) {
		List<CnfProducto> CnfProductoList = (List<CnfProducto>)cnfProductoRepository.findByCnfSubCategoriaId(id);
		return CnfProductoList;
	}
	public List<CnfProducto> getAllCnfProductoByCnfMarca(long id) {
		List<CnfProducto> CnfProductoList = (List<CnfProducto>)cnfProductoRepository.findByCnfMarcaId(id);
		return CnfProductoList;
	}
	@Override
	public void delete(long id) {
		CnfProducto cnfProducto =null;
		Optional<CnfProducto> cnfProductoOptional = cnfProductoRepository.findById(id);
		
		if (cnfProductoOptional.isPresent()) {
			cnfProducto = cnfProductoOptional.get();
				cnfProductoRepository.delete(cnfProducto);
		}
	}
}