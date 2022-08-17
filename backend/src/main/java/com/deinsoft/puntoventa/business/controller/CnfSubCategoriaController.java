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

import com.deinsoft.puntoventa.business.model.CnfSubCategoria;
import com.deinsoft.puntoventa.business.service.CnfSubCategoriaService;

@RestController
public class CnfSubCategoriaController extends CommonController<CnfSubCategoria, CnfSubCategoriaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfSubCategoriaController.class);
	
	@Autowired 
	CnfSubCategoriaService cnfSubCategoriaService;
	
	
	@GetMapping(value="/get-all-cnf-sub-categoria")
	public List<CnfSubCategoria> getAllCnfSubCategoria(CnfSubCategoria cnfSubCategoria) {
		logger.info("getAllCnfSubCategoria received: "+cnfSubCategoria.toString());
		List<CnfSubCategoria> cnfSubCategoriaList = cnfSubCategoriaService.getAllCnfSubCategoria(cnfSubCategoria);
		return cnfSubCategoriaList;
	}
	@GetMapping(value="/get-cnf-sub-categoria")
	public CnfSubCategoria getCnfSubCategoria(@Param("id") Long id) {
		logger.info("getCnfSubCategoria received: "+id);
		CnfSubCategoria cnfSubCategoria = cnfSubCategoriaService.getCnfSubCategoria(id);
		return cnfSubCategoria;
	}
	
	@PostMapping(value="/save-cnf-sub-categoria")
	public ResponseEntity<?> saveCnfSubCategoria(@Valid @RequestBody CnfSubCategoria cnfSubCategoria, BindingResult result) {
		return super.crear(cnfSubCategoria, result);
	}
	@GetMapping(value="/get-all-cnf-sub-categoria-combo")
	public List<CnfSubCategoria> getAllCnfSubCategoria() {
		List<CnfSubCategoria> cnfSubCategoriaList = cnfSubCategoriaService.getAllCnfSubCategoria();
		return cnfSubCategoriaList;
	}
	@DeleteMapping("/delete-cnf-sub-categoria")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfSubCategoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-sub-categoria-by-cnf-categoria")
	public List<CnfSubCategoria> getAllCnfSubCategoriaByCnfCategoria(@Param("id") Long id) {
		List<CnfSubCategoria> cnfSubCategoriaList = cnfSubCategoriaService.getAllCnfSubCategoriaByCnfCategoria(id);
		return cnfSubCategoriaList;
	}
	@GetMapping(value="/get-all-cnf-sub-categoria-by-cnf-empresa")
	public List<CnfSubCategoria> getAllCnfSubCategoriaByCnfEmpresa(@Param("id") Long id) {
		List<CnfSubCategoria> cnfSubCategoriaList = cnfSubCategoriaService.getAllCnfSubCategoriaByCnfEmpresa(id);
		return cnfSubCategoriaList;
	}
}
