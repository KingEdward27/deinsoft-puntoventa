package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.CnfMedioPago;
import com.deinsoft.puntoventa.business.service.CnfMedioPagoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/business/cnf-medio-pago")
public class CnfMedioPagoController extends CommonController<CnfMedioPago, Long, CnfMedioPagoService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfMedioPagoController.class);
	
	@Autowired 
	CnfMedioPagoService cnfMedioPagoService;
	
	
	@GetMapping(value="/get-all-cnf-forma-pago")
	public List<CnfMedioPago> getAllCnfMedioPago(CnfMedioPago cnfMedioPago) {
		logger.info("getAllCnfMedioPago received: "+cnfMedioPago.toString());
		List<CnfMedioPago> cnfMedioPagoList = cnfMedioPagoService.getAllCnfMedioPago(cnfMedioPago);
		return cnfMedioPagoList;
	}
	@GetMapping(value="/get-cnf-forma-pago")
	public CnfMedioPago getCnfMedioPago(@Param("id") Long id) {
		logger.info("getCnfMedioPago received: "+id);
		CnfMedioPago cnfMedioPago = cnfMedioPagoService.getCnfMedioPago(id);
		return cnfMedioPago;
	}
	
	@PostMapping(value="/save-cnf-forma-pago")
	public ResponseEntity<?> saveCnfMedioPago(@Valid @RequestBody CnfMedioPago cnfMedioPago, BindingResult result) {
		return super.crear(cnfMedioPago, result);
	}
	@GetMapping(value="/get-all-cnf-forma-pago-combo")
	public List<CnfMedioPago> getAllCnfMedioPago() {
		List<CnfMedioPago> cnfMedioPagoList = cnfMedioPagoService.getAllCnfMedioPago();
		return cnfMedioPagoList;
	}
	@DeleteMapping("/delete-cnf-forma-pago")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfMedioPagoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-medio-pago-by-cnf-empresa")
	public List<CnfMedioPago> getAllCnfMedioPagoByCnfEmpresa(@Param("id") Long id) {
		List<CnfMedioPago> cnfMedioPagoList = cnfMedioPagoService.getAllCnfMedioPagoByCnfEmpresa(id);
		return cnfMedioPagoList;
	}
}
