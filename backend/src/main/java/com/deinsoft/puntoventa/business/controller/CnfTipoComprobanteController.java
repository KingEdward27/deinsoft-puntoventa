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

import com.deinsoft.puntoventa.business.model.CnfTipoComprobante;
import com.deinsoft.puntoventa.business.service.CnfTipoComprobanteService;

@RestController
public class CnfTipoComprobanteController extends CommonController<CnfTipoComprobante, CnfTipoComprobanteService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfTipoComprobanteController.class);
	
	@Autowired 
	CnfTipoComprobanteService cnfTipoComprobanteService;
	
	
	@GetMapping(value="/get-all-cnf-tipo-comprobante")
	public List<CnfTipoComprobante> getAllCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante) {
		logger.info("getAllCnfTipoComprobante received: "+cnfTipoComprobante.toString());
		List<CnfTipoComprobante> cnfTipoComprobanteList = cnfTipoComprobanteService.getAllCnfTipoComprobante(cnfTipoComprobante);
		return cnfTipoComprobanteList;
	}
	@GetMapping(value="/get-cnf-tipo-comprobante")
	public CnfTipoComprobante getCnfTipoComprobante(@Param("id") Long id) {
		logger.info("getCnfTipoComprobante received: "+id);
		CnfTipoComprobante cnfTipoComprobante = cnfTipoComprobanteService.getCnfTipoComprobante(id);
		return cnfTipoComprobante;
	}
	
	@PostMapping(value="/save-cnf-tipo-comprobante")
	public ResponseEntity<?> saveCnfTipoComprobante(@Valid @RequestBody CnfTipoComprobante cnfTipoComprobante, BindingResult result) {
		return super.crear(cnfTipoComprobante, result);
	}
	@GetMapping(value="/get-all-cnf-tipo-comprobante-combo")
	public List<CnfTipoComprobante> getAllCnfTipoComprobante() {
		List<CnfTipoComprobante> cnfTipoComprobanteList = cnfTipoComprobanteService.getAllCnfTipoComprobante();
		return cnfTipoComprobanteList;
	}
	@DeleteMapping("/delete-cnf-tipo-comprobante")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfTipoComprobanteService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
