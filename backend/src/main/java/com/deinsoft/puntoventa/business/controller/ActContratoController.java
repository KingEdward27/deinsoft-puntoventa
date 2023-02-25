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

import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.service.ActContratoService;

@RestController
public class ActContratoController extends CommonController<ActContrato, ActContratoService>{
	
	private static final Logger logger = LoggerFactory.getLogger(ActContratoController.class);
	
	@Autowired 
	ActContratoService actContratoService;
	
	
	@GetMapping(value="/get-all-act-contrato")
	public List<ActContrato> getAllActContrato(ActContrato actContrato) {
		logger.info("getAllActContrato received: "+actContrato.toString());
		List<ActContrato> actContratoList = actContratoService.getAllActContrato(actContrato);
		return actContratoList;
	}
	@GetMapping(value="/get-act-contrato")
	public ActContrato getActContrato(@Param("id") Long id) {
		logger.info("getActContrato received: "+id);
		ActContrato actContrato = actContratoService.getActContrato(id);
		return actContrato;
	}
	
	@PostMapping(value="/save-act-contrato")
	public ResponseEntity<?> saveActContrato(@Valid @RequestBody ActContrato actContrato, BindingResult result) {
		return super.crear(actContrato, result);
	}
	@GetMapping(value="/get-all-act-contrato-combo")
	public List<ActContrato> getAllActContrato() {
		List<ActContrato> actContratoList = actContratoService.getAllActContrato();
		return actContratoList;
	}
	@DeleteMapping("/delete-act-contrato")
	public ResponseEntity<?> delete(@Param("id") Long id){
		actContratoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-act-contrato-by-cnf-maestro")
	public List<ActContrato> getAllActContratoByCnfMaestro(@Param("id") Long id) {
		List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfMaestro(id);
		return actContratoList;
	}
	@GetMapping(value="/get-all-act-contrato-by-cnf-local")
	public List<ActContrato> getAllActContratoByCnfLocal(@Param("id") Long id) {
		List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfLocal(id);
		return actContratoList;
	}
	@GetMapping(value="/get-all-act-contrato-by-cnf-tipo-comprobante")
	public List<ActContrato> getAllActContratoByCnfTipoComprobante(@Param("id") Long id) {
		List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfTipoComprobante(id);
		return actContratoList;
	}
	@GetMapping(value="/get-all-act-contrato-by-cnf-forma-pago")
	public List<ActContrato> getAllActContratoByCnfFormaPago(@Param("id") Long id) {
		List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfFormaPago(id);
		return actContratoList;
	}
	@GetMapping(value="/get-all-act-contrato-by-cnf-plan-contrato")
	public List<ActContrato> getAllActContratoByCnfPlanContrato(@Param("id") Long id) {
		List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfPlanContrato(id);
		return actContratoList;
	}
}
