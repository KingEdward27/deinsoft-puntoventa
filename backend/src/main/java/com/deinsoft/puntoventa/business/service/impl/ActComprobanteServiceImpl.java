package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.repository.ActComprobanteRepository;
import com.deinsoft.puntoventa.business.service.ActComprobanteService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class ActComprobanteServiceImpl extends CommonServiceImpl<ActComprobante,ActComprobanteRepository> implements ActComprobanteService  {
	@Autowired 
	ActComprobanteRepository actComprobanteRepository;
	
	public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante) {
		List<ActComprobante> actComprobanteList = (List<ActComprobante>)actComprobanteRepository.getAllActComprobante(actComprobante.getSerie().toUpperCase(),actComprobante.getNumero().toUpperCase(),actComprobante.getObservacion().toUpperCase(),actComprobante.getFlagEstado().toUpperCase(),actComprobante.getFlagIsventa().toUpperCase(),actComprobante.getEnvioPseFlag().toUpperCase(),actComprobante.getEnvioPseMensaje().toUpperCase(),actComprobante.getXmlhash().toUpperCase(),actComprobante.getCodigoqr().toUpperCase(),actComprobante.getNumTicket().toUpperCase());
		return actComprobanteList;
	}
	public ActComprobante getActComprobante(Long id) {
		ActComprobante actComprobante = null; 
		Optional<ActComprobante> actComprobanteOptional = actComprobanteRepository.findById(id);
		if (actComprobanteOptional.isPresent()) {
			actComprobante = actComprobanteOptional.get();
		}
		return actComprobante;
	}
	
	public ActComprobante saveActComprobante(ActComprobante actComprobante) {
		ActComprobante actComprobanteResult = actComprobanteRepository.save(actComprobante);
		return actComprobanteResult;
	}
	public List<ActComprobante> getAllActComprobante() {
		List<ActComprobante> actComprobanteList = (List<ActComprobante>)actComprobanteRepository.findAll();
		return actComprobanteList;
	}
	public List<ActComprobante> getAllActComprobanteByActComprobante(long id) {
		List<ActComprobante> ActComprobanteList = (List<ActComprobante>)actComprobanteRepository.findByActComprobanteId(id);
		return ActComprobanteList;
	}
	public List<ActComprobante> getAllActComprobanteByCnfMaestro(long id) {
		List<ActComprobante> ActComprobanteList = (List<ActComprobante>)actComprobanteRepository.findByCnfMaestroId(id);
		return ActComprobanteList;
	}
	public List<ActComprobante> getAllActComprobanteByCnfFormaPago(long id) {
		List<ActComprobante> ActComprobanteList = (List<ActComprobante>)actComprobanteRepository.findByCnfFormaPagoId(id);
		return ActComprobanteList;
	}
	public List<ActComprobante> getAllActComprobanteByCnfMoneda(long id) {
		List<ActComprobante> ActComprobanteList = (List<ActComprobante>)actComprobanteRepository.findByCnfMonedaId(id);
		return ActComprobanteList;
	}
	public List<ActComprobante> getAllActComprobanteByCnfLocal(long id) {
		List<ActComprobante> ActComprobanteList = (List<ActComprobante>)actComprobanteRepository.findByCnfLocalId(id);
		return ActComprobanteList;
	}
	public List<ActComprobante> getAllActComprobanteByCnfTipoComprobante(long id) {
		List<ActComprobante> ActComprobanteList = (List<ActComprobante>)actComprobanteRepository.findByCnfTipoComprobanteId(id);
		return ActComprobanteList;
	}
	public List<ActComprobante> getAllActComprobanteByInvAlmacen(long id) {
		List<ActComprobante> ActComprobanteList = (List<ActComprobante>)actComprobanteRepository.findByInvAlmacenId(id);
		return ActComprobanteList;
	}
	@Override
	public void delete(long id) {
		ActComprobante actComprobante =null;
		Optional<ActComprobante> actComprobanteOptional = actComprobanteRepository.findById(id);
		
		if (actComprobanteOptional.isPresent()) {
			actComprobante = actComprobanteOptional.get();
				actComprobanteRepository.delete(actComprobante);
		}
	}
}
