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

import com.deinsoft.puntoventa.business.model.CnfCategoria;
import com.deinsoft.puntoventa.business.service.CnfCategoriaService;

@RestController
public class CnfCategoriaController extends CommonController<CnfCategoria, CnfCategoriaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfCategoriaController.class);
	
	@Autowired 
	CnfCategoriaService cnfCategoriaService;
	
	
	@GetMapping(value="/get-all-cnf-categoria")
	public List<CnfCategoria> getAllCnfCategoria(CnfCategoria cnfCategoria) {
		logger.info("getAllCnfCategoria received: "+cnfCategoria.toString());
		List<CnfCategoria> cnfCategoriaList = cnfCategoriaService.getAllCnfCategoria(cnfCategoria);
		return cnfCategoriaList;
	}
	@GetMapping(value="/get-cnf-categoria")
	public CnfCategoria getCnfCategoria(@Param("id") Long id) {
		logger.info("getCnfCategoria received: "+id);
		CnfCategoria cnfCategoria = cnfCategoriaService.getCnfCategoria(id);
		return cnfCategoria;
	}
	
	@PostMapping(value="/save-cnf-categoria")
	public ResponseEntity<?> saveCnfCategoria(@Valid @RequestBody CnfCategoria cnfCategoria, BindingResult result) {
		return super.crear(cnfCategoria, result);
	}
	@GetMapping(value="/get-all-cnf-categoria-combo")
	public List<CnfCategoria> getAllCnfCategoria() {
		List<CnfCategoria> cnfCategoriaList = cnfCategoriaService.getAllCnfCategoria();
		return cnfCategoriaList;
	}
	@DeleteMapping("/delete-cnf-categoria")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfCategoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-categoria-by-cnf-empresa")
	public List<CnfCategoria> getAllCnfCategoriaByCnfEmpresa(@Param("id") Long id) {
		List<CnfCategoria> cnfCategoriaList = cnfCategoriaService.getAllCnfCategoriaByCnfEmpresa(id);
		return cnfCategoriaList;
	}
}
