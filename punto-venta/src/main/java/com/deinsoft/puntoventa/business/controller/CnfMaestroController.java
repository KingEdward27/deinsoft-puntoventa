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

import com.deinsoft.puntoventa.business.model.CnfMaestro;
import com.deinsoft.puntoventa.business.service.CnfMaestroService;

@RestController
@RequestMapping("/api/business/cnf-maestro")
public class CnfMaestroController extends CommonController<CnfMaestro, CnfMaestroService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfMaestroController.class);
	
	@Autowired 
	CnfMaestroService cnfMaestroService;
	
	
	@GetMapping(value="/get-all-cnf-maestro")
	public List<CnfMaestro> getAllCnfMaestro(CnfMaestro cnfMaestro) {
		logger.info("getAllCnfMaestro received: "+cnfMaestro.toString());
		List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestro(cnfMaestro);
		return cnfMaestroList;
	}
	@GetMapping(value="/get-cnf-maestro")
	public CnfMaestro getCnfMaestro(@Param("id") Long id) {
		logger.info("getCnfMaestro received: "+id);
		CnfMaestro cnfMaestro = cnfMaestroService.getCnfMaestro(id);
		return cnfMaestro;
	}
	
	@PostMapping(value="/save-cnf-maestro")
	public ResponseEntity<?> saveCnfMaestro(@Valid @RequestBody CnfMaestro cnfMaestro, BindingResult result) {
		return super.crear(cnfMaestro, result);
	}
	@GetMapping(value="/get-all-cnf-maestro-combo")
	public List<CnfMaestro> getAllCnfMaestro() {
		List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestro();
		return cnfMaestroList;
	}
	@DeleteMapping("/delete-cnf-maestro")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfMaestroService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-maestro-by-cnf-tipo-documento")
	public List<CnfMaestro> getAllCnfMaestroByCnfTipoDocumento(@Param("id") Long id) {
		List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestroByCnfTipoDocumento(id);
		return cnfMaestroList;
	}
	@GetMapping(value="/get-all-cnf-maestro-by-cnf-empresa")
	public List<CnfMaestro> getAllCnfMaestroByCnfEmpresa(@Param("id") Long id) {
		List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestroByCnfEmpresa(id);
		return cnfMaestroList;
	}
	@GetMapping(value="/get-all-cnf-maestro-by-cnf-distrito")
	public List<CnfMaestro> getAllCnfMaestroByCnfDistrito(@Param("id") Long id) {
		List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestroByCnfDistrito(id);
		return cnfMaestroList;
	}
}
