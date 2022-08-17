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

import com.deinsoft.puntoventa.business.model.CnfRegion;
import com.deinsoft.puntoventa.business.service.CnfRegionService;

@RestController
public class CnfRegionController extends CommonController<CnfRegion, CnfRegionService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfRegionController.class);
	
	@Autowired 
	CnfRegionService cnfRegionService;
	
	
	@GetMapping(value="/get-all-cnf-region")
	public List<CnfRegion> getAllCnfRegion(CnfRegion cnfRegion) {
		logger.info("getAllCnfRegion received: "+cnfRegion.toString());
		List<CnfRegion> cnfRegionList = cnfRegionService.getAllCnfRegion(cnfRegion);
		return cnfRegionList;
	}
	@GetMapping(value="/get-cnf-region")
	public CnfRegion getCnfRegion(@Param("id") Long id) {
		logger.info("getCnfRegion received: "+id);
		CnfRegion cnfRegion = cnfRegionService.getCnfRegion(id);
		return cnfRegion;
	}
	
	@PostMapping(value="/save-cnf-region")
	public ResponseEntity<?> saveCnfRegion(@Valid @RequestBody CnfRegion cnfRegion, BindingResult result) {
		return super.crear(cnfRegion, result);
	}
	@GetMapping(value="/get-all-cnf-region-combo")
	public List<CnfRegion> getAllCnfRegion() {
		List<CnfRegion> cnfRegionList = cnfRegionService.getAllCnfRegion();
		return cnfRegionList;
	}
	@DeleteMapping("/delete-cnf-region")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfRegionService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-region-by-cnf-pais")
	public List<CnfRegion> getAllCnfRegionByCnfPais(@Param("id") Long id) {
		List<CnfRegion> cnfRegionList = cnfRegionService.getAllCnfRegionByCnfPais(id);
		return cnfRegionList;
	}
}
