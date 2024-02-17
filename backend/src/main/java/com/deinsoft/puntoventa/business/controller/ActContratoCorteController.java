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

import com.deinsoft.puntoventa.business.model.ActContratoCorte;
import com.deinsoft.puntoventa.business.service.ActContratoCorteService;

@RestController
public class ActContratoCorteController extends CommonController<ActContratoCorte, ActContratoCorteService>{
	
	private static final Logger logger = LoggerFactory.getLogger(ActContratoCorteController.class);
	
	@Autowired 
	ActContratoCorteService actContratoCorteService;
	
	
	@GetMapping(value="/get-all-act-contrato-corte")
	public List<ActContratoCorte> getAllActContratoCorte(ActContratoCorte actContratoCorte) {
		logger.info("getAllActContratoCorte received: "+actContratoCorte.toString());
		List<ActContratoCorte> actContratoCorteList = actContratoCorteService.getAllActContratoCorte(actContratoCorte);
		return actContratoCorteList;
	}
	@GetMapping(value="/get-act-contrato-corte")
	public ActContratoCorte getActContratoCorte(@Param("id") Long id) {
		logger.info("getActContratoCorte received: "+id);
		ActContratoCorte actContratoCorte = actContratoCorteService.getActContratoCorte(id);
		return actContratoCorte;
	}
	
	@PostMapping(value="/save-act-contrato-corte")
	public ResponseEntity<?> saveActContratoCorte(@Valid @RequestBody ActContratoCorte actContratoCorte, BindingResult result) {
		return super.crear(actContratoCorte, result);
	}
	@GetMapping(value="/get-all-act-contrato-corte-combo")
	public List<ActContratoCorte> getAllActContratoCorte() {
		List<ActContratoCorte> actContratoCorteList = actContratoCorteService.getAllActContratoCorte();
		return actContratoCorteList;
	}
	@DeleteMapping("/delete-act-contrato-corte")
	public ResponseEntity<?> delete(@Param("id") Long id){
		actContratoCorteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-act-contrato-corte-by-act-contrato")
	public List<ActContratoCorte> getAllActContratoCorteByActContrato(@Param("id") Long id) {
		List<ActContratoCorte> actContratoCorteList = actContratoCorteService.getAllActContratoCorteByActContrato(id);
		return actContratoCorteList;
	}
	@GetMapping(value="/get-all-act-contrato-corte-by-seg-usuario")
	public List<ActContratoCorte> getAllActContratoCorteBySegUsuario(@Param("id") Long id) {
		List<ActContratoCorte> actContratoCorteList = actContratoCorteService.getAllActContratoCorteBySegUsuario(id);
		return actContratoCorteList;
	}
}
