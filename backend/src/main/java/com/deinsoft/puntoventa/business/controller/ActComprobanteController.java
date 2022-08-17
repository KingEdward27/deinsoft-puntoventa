package com.deinsoft.puntoventa.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.controller.commons.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.service.ActComprobanteService;

@RestController
public class ActComprobanteController extends CommonController<ActComprobante, ActComprobanteService>{
	
	private static final Logger logger = LoggerFactory.getLogger(ActComprobanteController.class);
	
	@Autowired 
	ActComprobanteService actComprobanteService;
	
	
	@GetMapping(value="/get-all-act-comprobante")
	public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante) {
		logger.info("getAllActComprobante received: "+actComprobante.toString());
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobante(actComprobante);
		return actComprobanteList;
	}
	@GetMapping(value="/get-act-comprobante")
	public ActComprobante getActComprobante(@Param("id") Long id) {
		logger.info("getActComprobante received: "+id);
		ActComprobante actComprobante = actComprobanteService.getActComprobante(id);
		return actComprobante;
	}
	
	@PostMapping(value="/save-act-comprobante")
	public ResponseEntity<?> saveActComprobante(@Valid @RequestBody ActComprobante actComprobante, BindingResult result) {
		return super.crear(actComprobante, result);
	}
	@GetMapping(value="/get-all-act-comprobante-combo")
	public List<ActComprobante> getAllActComprobante() {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobante();
		return actComprobanteList;
	}
	@DeleteMapping("/delete-act-comprobante")
	public ResponseEntity<?> delete(@Param("id") Long id){
		actComprobanteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-act-comprobante-by-act-comprobante")
	public List<ActComprobante> getAllActComprobanteByActComprobante(@Param("id") Long id) {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByActComprobante(id);
		return actComprobanteList;
	}
	@GetMapping(value="/get-all-act-comprobante-by-cnf-maestro")
	public List<ActComprobante> getAllActComprobanteByCnfMaestro(@Param("id") Long id) {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfMaestro(id);
		return actComprobanteList;
	}
	@GetMapping(value="/get-all-act-comprobante-by-cnf-forma-pago")
	public List<ActComprobante> getAllActComprobanteByCnfFormaPago(@Param("id") Long id) {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfFormaPago(id);
		return actComprobanteList;
	}
	@GetMapping(value="/get-all-act-comprobante-by-cnf-moneda")
	public List<ActComprobante> getAllActComprobanteByCnfMoneda(@Param("id") Long id) {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfMoneda(id);
		return actComprobanteList;
	}
	@GetMapping(value="/get-all-act-comprobante-by-cnf-local")
	public List<ActComprobante> getAllActComprobanteByCnfLocal(@Param("id") Long id) {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfLocal(id);
		return actComprobanteList;
	}
	@GetMapping(value="/get-all-act-comprobante-by-cnf-tipo-comprobante")
	public List<ActComprobante> getAllActComprobanteByCnfTipoComprobante(@Param("id") Long id) {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfTipoComprobante(id);
		return actComprobanteList;
	}
	@GetMapping(value="/get-all-act-comprobante-by-inv-almacen")
	public List<ActComprobante> getAllActComprobanteByInvAlmacen(@Param("id") Long id) {
		List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByInvAlmacen(id);
		return actComprobanteList;
	}
}
