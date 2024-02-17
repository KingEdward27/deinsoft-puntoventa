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

import com.deinsoft.puntoventa.business.model.CnfTipoSistema;
import com.deinsoft.puntoventa.business.service.CnfTipoSistemaService;

@RestController
public class CnfTipoSistemaController extends CommonController<CnfTipoSistema, CnfTipoSistemaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfTipoSistemaController.class);
	
	@Autowired 
	CnfTipoSistemaService cnfTipoSistemaService;
	
	
	@GetMapping(value="/get-all-cnf-tipo-sistema")
	public List<CnfTipoSistema> getAllCnfTipoSistema(CnfTipoSistema cnfTipoSistema) {
		logger.info("getAllCnfTipoSistema received: "+cnfTipoSistema.toString());
		List<CnfTipoSistema> cnfTipoSistemaList = cnfTipoSistemaService.getAllCnfTipoSistema(cnfTipoSistema);
		return cnfTipoSistemaList;
	}
	@GetMapping(value="/get-cnf-tipo-sistema")
	public CnfTipoSistema getCnfTipoSistema(@Param("id") Long id) {
		logger.info("getCnfTipoSistema received: "+id);
		CnfTipoSistema cnfTipoSistema = cnfTipoSistemaService.getCnfTipoSistema(id);
		return cnfTipoSistema;
	}
	
	@PostMapping(value="/save-cnf-tipo-sistema")
	public ResponseEntity<?> saveCnfTipoSistema(@Valid @RequestBody CnfTipoSistema cnfTipoSistema, BindingResult result) {
		return super.crear(cnfTipoSistema, result);
	}
	@GetMapping(value="/get-all-cnf-tipo-sistema-combo")
	public List<CnfTipoSistema> getAllCnfTipoSistema() {
		List<CnfTipoSistema> cnfTipoSistemaList = cnfTipoSistemaService.getAllCnfTipoSistema();
		return cnfTipoSistemaList;
	}
	@DeleteMapping("/delete-cnf-tipo-sistema")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfTipoSistemaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
