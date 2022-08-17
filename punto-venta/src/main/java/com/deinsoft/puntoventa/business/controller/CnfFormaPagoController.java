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

import com.deinsoft.puntoventa.business.model.CnfFormaPago;
import com.deinsoft.puntoventa.business.service.CnfFormaPagoService;

@RestController
@RequestMapping("/api/business/cnf-forma-pago")
public class CnfFormaPagoController extends CommonController<CnfFormaPago, CnfFormaPagoService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfFormaPagoController.class);
	
	@Autowired 
	CnfFormaPagoService cnfFormaPagoService;
	
	
	@GetMapping(value="/get-all-cnf-forma-pago")
	public List<CnfFormaPago> getAllCnfFormaPago(CnfFormaPago cnfFormaPago) {
		logger.info("getAllCnfFormaPago received: "+cnfFormaPago.toString());
		List<CnfFormaPago> cnfFormaPagoList = cnfFormaPagoService.getAllCnfFormaPago(cnfFormaPago);
		return cnfFormaPagoList;
	}
	@GetMapping(value="/get-cnf-forma-pago")
	public CnfFormaPago getCnfFormaPago(@Param("id") Long id) {
		logger.info("getCnfFormaPago received: "+id);
		CnfFormaPago cnfFormaPago = cnfFormaPagoService.getCnfFormaPago(id);
		return cnfFormaPago;
	}
	
	@PostMapping(value="/save-cnf-forma-pago")
	public ResponseEntity<?> saveCnfFormaPago(@Valid @RequestBody CnfFormaPago cnfFormaPago, BindingResult result) {
		return super.crear(cnfFormaPago, result);
	}
	@GetMapping(value="/get-all-cnf-forma-pago-combo")
	public List<CnfFormaPago> getAllCnfFormaPago() {
		List<CnfFormaPago> cnfFormaPagoList = cnfFormaPagoService.getAllCnfFormaPago();
		return cnfFormaPagoList;
	}
	@DeleteMapping("/delete-cnf-forma-pago")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfFormaPagoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-forma-pago-by-cnf-empresa")
	public List<CnfFormaPago> getAllCnfFormaPagoByCnfEmpresa(@Param("id") Long id) {
		List<CnfFormaPago> cnfFormaPagoList = cnfFormaPagoService.getAllCnfFormaPagoByCnfEmpresa(id);
		return cnfFormaPagoList;
	}
}
