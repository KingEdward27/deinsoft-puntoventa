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

import com.deinsoft.puntoventa.business.model.CnfProvincia;
import com.deinsoft.puntoventa.business.service.CnfProvinciaService;

@RestController
public class CnfProvinciaController extends CommonController<CnfProvincia, CnfProvinciaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfProvinciaController.class);
	
	@Autowired 
	CnfProvinciaService cnfProvinciaService;
	
	
	@GetMapping(value="/get-all-cnf-provincia")
	public List<CnfProvincia> getAllCnfProvincia(CnfProvincia cnfProvincia) {
		logger.info("getAllCnfProvincia received: "+cnfProvincia.toString());
		List<CnfProvincia> cnfProvinciaList = cnfProvinciaService.getAllCnfProvincia(cnfProvincia);
		return cnfProvinciaList;
	}
	@GetMapping(value="/get-cnf-provincia")
	public CnfProvincia getCnfProvincia(@Param("id") Long id) {
		logger.info("getCnfProvincia received: "+id);
		CnfProvincia cnfProvincia = cnfProvinciaService.getCnfProvincia(id);
		return cnfProvincia;
	}
	
	@PostMapping(value="/save-cnf-provincia")
	public ResponseEntity<?> saveCnfProvincia(@Valid @RequestBody CnfProvincia cnfProvincia, BindingResult result) {
		return super.crear(cnfProvincia, result);
	}
	@GetMapping(value="/get-all-cnf-provincia-combo")
	public List<CnfProvincia> getAllCnfProvincia() {
		List<CnfProvincia> cnfProvinciaList = cnfProvinciaService.getAllCnfProvincia();
		return cnfProvinciaList;
	}
	@DeleteMapping("/delete-cnf-provincia")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfProvinciaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-provincia-by-cnf-region")
	public List<CnfProvincia> getAllCnfProvinciaByCnfRegion(@Param("id") Long id) {
		List<CnfProvincia> cnfProvinciaList = cnfProvinciaService.getAllCnfProvinciaByCnfRegion(id);
		return cnfProvinciaList;
	}
}
