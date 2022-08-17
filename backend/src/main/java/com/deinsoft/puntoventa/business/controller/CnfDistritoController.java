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

import com.deinsoft.puntoventa.business.model.CnfDistrito;
import com.deinsoft.puntoventa.business.service.CnfDistritoService;

@RestController
public class CnfDistritoController extends CommonController<CnfDistrito, CnfDistritoService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfDistritoController.class);
	
	@Autowired 
	CnfDistritoService cnfDistritoService;
	
	
	@GetMapping(value="/get-all-cnf-distrito")
	public List<CnfDistrito> getAllCnfDistrito(CnfDistrito cnfDistrito) {
		logger.info("getAllCnfDistrito received: "+cnfDistrito.toString());
		List<CnfDistrito> cnfDistritoList = cnfDistritoService.getAllCnfDistrito(cnfDistrito);
		return cnfDistritoList;
	}
	@GetMapping(value="/get-cnf-distrito")
	public CnfDistrito getCnfDistrito(@Param("id") Long id) {
		logger.info("getCnfDistrito received: "+id);
		CnfDistrito cnfDistrito = cnfDistritoService.getCnfDistrito(id);
		return cnfDistrito;
	}
	
	@PostMapping(value="/save-cnf-distrito")
	public ResponseEntity<?> saveCnfDistrito(@Valid @RequestBody CnfDistrito cnfDistrito, BindingResult result) {
		return super.crear(cnfDistrito, result);
	}
	@GetMapping(value="/get-all-cnf-distrito-combo")
	public List<CnfDistrito> getAllCnfDistrito() {
		List<CnfDistrito> cnfDistritoList = cnfDistritoService.getAllCnfDistrito();
		return cnfDistritoList;
	}
	@DeleteMapping("/delete-cnf-distrito")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfDistritoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-distrito-by-cnf-provincia")
	public List<CnfDistrito> getAllCnfDistritoByCnfProvincia(@Param("id") Long id) {
		List<CnfDistrito> cnfDistritoList = cnfDistritoService.getAllCnfDistritoByCnfProvincia(id);
		return cnfDistritoList;
	}
}
