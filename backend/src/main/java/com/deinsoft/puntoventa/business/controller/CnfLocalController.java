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

import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.service.CnfLocalService;

@RestController
public class CnfLocalController extends CommonController<CnfLocal, CnfLocalService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfLocalController.class);
	
	@Autowired 
	CnfLocalService cnfLocalService;
	
	
	@GetMapping(value="/get-all-cnf-local")
	public List<CnfLocal> getAllCnfLocal(CnfLocal cnfLocal) {
		logger.info("getAllCnfLocal received: "+cnfLocal.toString());
		List<CnfLocal> cnfLocalList = cnfLocalService.getAllCnfLocal(cnfLocal);
		return cnfLocalList;
	}
	@GetMapping(value="/get-cnf-local")
	public CnfLocal getCnfLocal(@Param("id") Long id) {
		logger.info("getCnfLocal received: "+id);
		CnfLocal cnfLocal = cnfLocalService.getCnfLocal(id);
		return cnfLocal;
	}
	
	@PostMapping(value="/save-cnf-local")
	public ResponseEntity<?> saveCnfLocal(@Valid @RequestBody CnfLocal cnfLocal, BindingResult result) {
		return super.crear(cnfLocal, result);
	}
	@GetMapping(value="/get-all-cnf-local-combo")
	public List<CnfLocal> getAllCnfLocal() {
		List<CnfLocal> cnfLocalList = cnfLocalService.getAllCnfLocal();
		return cnfLocalList;
	}
	@DeleteMapping("/delete-cnf-local")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfLocalService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-local-by-cnf-empresa")
	public List<CnfLocal> getAllCnfLocalByCnfEmpresa(@Param("id") Long id) {
		List<CnfLocal> cnfLocalList = cnfLocalService.getAllCnfLocalByCnfEmpresa(id);
		return cnfLocalList;
	}
}
