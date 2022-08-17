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

import com.deinsoft.puntoventa.business.model.CnfMarca;
import com.deinsoft.puntoventa.business.service.CnfMarcaService;

@RestController
public class CnfMarcaController extends CommonController<CnfMarca, CnfMarcaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfMarcaController.class);
	
	@Autowired 
	CnfMarcaService cnfMarcaService;
	
	
	@GetMapping(value="/get-all-cnf-marca")
	public List<CnfMarca> getAllCnfMarca(CnfMarca cnfMarca) {
		logger.info("getAllCnfMarca received: "+cnfMarca.toString());
		List<CnfMarca> cnfMarcaList = cnfMarcaService.getAllCnfMarca(cnfMarca);
		return cnfMarcaList;
	}
	@GetMapping(value="/get-cnf-marca")
	public CnfMarca getCnfMarca(@Param("id") Long id) {
		logger.info("getCnfMarca received: "+id);
		CnfMarca cnfMarca = cnfMarcaService.getCnfMarca(id);
		return cnfMarca;
	}
	
	@PostMapping(value="/save-cnf-marca")
	public ResponseEntity<?> saveCnfMarca(@Valid @RequestBody CnfMarca cnfMarca, BindingResult result) {
		return super.crear(cnfMarca, result);
	}
	@GetMapping(value="/get-all-cnf-marca-combo")
	public List<CnfMarca> getAllCnfMarca() {
		List<CnfMarca> cnfMarcaList = cnfMarcaService.getAllCnfMarca();
		return cnfMarcaList;
	}
	@DeleteMapping("/delete-cnf-marca")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfMarcaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-marca-by-cnf-empresa")
	public List<CnfMarca> getAllCnfMarcaByCnfEmpresa(@Param("id") Long id) {
		List<CnfMarca> cnfMarcaList = cnfMarcaService.getAllCnfMarcaByCnfEmpresa(id);
		return cnfMarcaList;
	}
}
