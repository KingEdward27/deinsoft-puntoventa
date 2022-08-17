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

import com.deinsoft.puntoventa.business.model.CnfImpuestoCondicion;
import com.deinsoft.puntoventa.business.service.CnfImpuestoCondicionService;

@RestController
public class CnfImpuestoCondicionController extends CommonController<CnfImpuestoCondicion, CnfImpuestoCondicionService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfImpuestoCondicionController.class);
	
	@Autowired 
	CnfImpuestoCondicionService cnfImpuestoCondicionService;
	
	
	@GetMapping(value="/get-all-cnf-impuesto-condicion")
	public List<CnfImpuestoCondicion> getAllCnfImpuestoCondicion(CnfImpuestoCondicion cnfImpuestoCondicion) {
		logger.info("getAllCnfImpuestoCondicion received: "+cnfImpuestoCondicion.toString());
		List<CnfImpuestoCondicion> cnfImpuestoCondicionList = cnfImpuestoCondicionService.getAllCnfImpuestoCondicion(cnfImpuestoCondicion);
		return cnfImpuestoCondicionList;
	}
	@GetMapping(value="/get-cnf-impuesto-condicion")
	public CnfImpuestoCondicion getCnfImpuestoCondicion(@Param("id") Long id) {
		logger.info("getCnfImpuestoCondicion received: "+id);
		CnfImpuestoCondicion cnfImpuestoCondicion = cnfImpuestoCondicionService.getCnfImpuestoCondicion(id);
		return cnfImpuestoCondicion;
	}
	
	@PostMapping(value="/save-cnf-impuesto-condicion")
	public ResponseEntity<?> saveCnfImpuestoCondicion(@Valid @RequestBody CnfImpuestoCondicion cnfImpuestoCondicion, BindingResult result) {
		return super.crear(cnfImpuestoCondicion, result);
	}
	@GetMapping(value="/get-all-cnf-impuesto-condicion-combo")
	public List<CnfImpuestoCondicion> getAllCnfImpuestoCondicion() {
		List<CnfImpuestoCondicion> cnfImpuestoCondicionList = cnfImpuestoCondicionService.getAllCnfImpuestoCondicion();
		return cnfImpuestoCondicionList;
	}
	@DeleteMapping("/delete-cnf-impuesto-condicion")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfImpuestoCondicionService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
