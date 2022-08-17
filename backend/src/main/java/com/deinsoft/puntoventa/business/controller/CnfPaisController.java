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

import com.deinsoft.puntoventa.business.model.CnfPais;
import com.deinsoft.puntoventa.business.service.CnfPaisService;

@RestController
public class CnfPaisController extends CommonController<CnfPais, CnfPaisService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfPaisController.class);
	
	@Autowired 
	CnfPaisService cnfPaisService;
	
	
	@GetMapping(value="/get-all-cnf-pais")
	public List<CnfPais> getAllCnfPais(CnfPais cnfPais) {
		logger.info("getAllCnfPais received: "+cnfPais.toString());
		List<CnfPais> cnfPaisList = cnfPaisService.getAllCnfPais(cnfPais);
		return cnfPaisList;
	}
	@GetMapping(value="/get-cnf-pais")
	public CnfPais getCnfPais(@Param("id") Long id) {
		logger.info("getCnfPais received: "+id);
		CnfPais cnfPais = cnfPaisService.getCnfPais(id);
		return cnfPais;
	}
	
	@PostMapping(value="/save-cnf-pais")
	public ResponseEntity<?> saveCnfPais(@Valid @RequestBody CnfPais cnfPais, BindingResult result) {
		return super.crear(cnfPais, result);
	}
	@GetMapping(value="/get-all-cnf-pais-combo")
	public List<CnfPais> getAllCnfPais() {
		List<CnfPais> cnfPaisList = cnfPaisService.getAllCnfPais();
		return cnfPaisList;
	}
	@DeleteMapping("/delete-cnf-pais")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfPaisService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
