package com.deinsoft.puntoventa.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.CnfMoneda;
import com.deinsoft.puntoventa.business.service.CnfMonedaService;

@RestController
@RequestMapping("/api/business/cnf-moneda")
public class CnfMonedaController extends CommonController<CnfMoneda, Long, CnfMonedaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfMonedaController.class);
	
	@Autowired 
	CnfMonedaService cnfMonedaService;
	
	
	@GetMapping(value="/get-all-cnf-moneda")
	public List<CnfMoneda> getAllCnfMoneda(CnfMoneda cnfMoneda) {
		logger.info("getAllCnfMoneda received: "+cnfMoneda.toString());
		List<CnfMoneda> cnfMonedaList = cnfMonedaService.getAllCnfMoneda(cnfMoneda);
		return cnfMonedaList;
	}
	@GetMapping(value="/get-cnf-moneda")
	public CnfMoneda getCnfMoneda(@Param("id") Long id) {
		logger.info("getCnfMoneda received: "+id);
		CnfMoneda cnfMoneda = cnfMonedaService.getCnfMoneda(id);
		return cnfMoneda;
	}
	
	@PostMapping(value="/save-cnf-moneda")
	public ResponseEntity<?> saveCnfMoneda(@Valid @RequestBody CnfMoneda cnfMoneda, BindingResult result) {
		return super.crear(cnfMoneda, result);
	}
	@GetMapping(value="/get-all-cnf-moneda-combo")
	public List<CnfMoneda> getAllCnfMoneda() {
		List<CnfMoneda> cnfMonedaList = cnfMonedaService.getAllCnfMoneda();
		return cnfMonedaList;
	}
	@DeleteMapping("/delete-cnf-moneda")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfMonedaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
