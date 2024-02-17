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

import com.deinsoft.puntoventa.business.model.CnfMenuTipoSistema;
import com.deinsoft.puntoventa.business.service.CnfMenuTipoSistemaService;

@RestController
public class CnfMenuTipoSistemaController extends CommonController<CnfMenuTipoSistema, CnfMenuTipoSistemaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfMenuTipoSistemaController.class);
	
	@Autowired 
	CnfMenuTipoSistemaService cnfMenuTipoSistemaService;
	
	
	@GetMapping(value="/get-all-cnf-menu-tipo-sistema")
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistema(CnfMenuTipoSistema cnfMenuTipoSistema) {
		logger.info("getAllCnfMenuTipoSistema received: "+cnfMenuTipoSistema.toString());
		List<CnfMenuTipoSistema> cnfMenuTipoSistemaList = cnfMenuTipoSistemaService.getAllCnfMenuTipoSistema(cnfMenuTipoSistema);
		return cnfMenuTipoSistemaList;
	}
	@GetMapping(value="/get-cnf-menu-tipo-sistema")
	public CnfMenuTipoSistema getCnfMenuTipoSistema(@Param("id") Long id) {
		logger.info("getCnfMenuTipoSistema received: "+id);
		CnfMenuTipoSistema cnfMenuTipoSistema = cnfMenuTipoSistemaService.getCnfMenuTipoSistema(id);
		return cnfMenuTipoSistema;
	}
	
	@PostMapping(value="/save-cnf-menu-tipo-sistema")
	public ResponseEntity<?> saveCnfMenuTipoSistema(@Valid @RequestBody CnfMenuTipoSistema cnfMenuTipoSistema, BindingResult result) {
		return super.crear(cnfMenuTipoSistema, result);
	}
	@GetMapping(value="/get-all-cnf-menu-tipo-sistema-combo")
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistema() {
		List<CnfMenuTipoSistema> cnfMenuTipoSistemaList = cnfMenuTipoSistemaService.getAllCnfMenuTipoSistema();
		return cnfMenuTipoSistemaList;
	}
	@DeleteMapping("/delete-cnf-menu-tipo-sistema")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfMenuTipoSistemaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-menu-tipo-sistema-by-seg-menu")
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistemaBySegMenu(@Param("id") Long id) {
		List<CnfMenuTipoSistema> cnfMenuTipoSistemaList = cnfMenuTipoSistemaService.getAllCnfMenuTipoSistemaBySegMenu(id);
		return cnfMenuTipoSistemaList;
	}
}
