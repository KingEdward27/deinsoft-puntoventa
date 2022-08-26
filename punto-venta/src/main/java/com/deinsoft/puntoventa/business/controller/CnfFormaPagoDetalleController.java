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

import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.service.CnfFormaPagoDetalleService;

@RestController
public class CnfFormaPagoDetalleController extends CommonController<CnfFormaPagoDetalle, CnfFormaPagoDetalleService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfFormaPagoDetalleController.class);
	
	@Autowired 
	CnfFormaPagoDetalleService cnfFormaPagoDetalleService;
	
	
	@GetMapping(value="/get-all-cnf-forma-pago-detalle")
	public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalle(CnfFormaPagoDetalle cnfFormaPagoDetalle) {
		logger.info("getAllCnfFormaPagoDetalle received: "+cnfFormaPagoDetalle.toString());
		List<CnfFormaPagoDetalle> cnfFormaPagoDetalleList = cnfFormaPagoDetalleService.getAllCnfFormaPagoDetalle(cnfFormaPagoDetalle);
		return cnfFormaPagoDetalleList;
	}
	@GetMapping(value="/get-cnf-forma-pago-detalle")
	public CnfFormaPagoDetalle getCnfFormaPagoDetalle(@Param("id") Long id) {
		logger.info("getCnfFormaPagoDetalle received: "+id);
		CnfFormaPagoDetalle cnfFormaPagoDetalle = cnfFormaPagoDetalleService.getCnfFormaPagoDetalle(id);
		return cnfFormaPagoDetalle;
	}
	
	@PostMapping(value="/save-cnf-forma-pago-detalle")
	public ResponseEntity<?> saveCnfFormaPagoDetalle(@Valid @RequestBody CnfFormaPagoDetalle cnfFormaPagoDetalle, BindingResult result) {
		return super.crear(cnfFormaPagoDetalle, result);
	}
	@GetMapping(value="/get-all-cnf-forma-pago-detalle-combo")
	public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalle() {
		List<CnfFormaPagoDetalle> cnfFormaPagoDetalleList = cnfFormaPagoDetalleService.getAllCnfFormaPagoDetalle();
		return cnfFormaPagoDetalleList;
	}
	@DeleteMapping("/delete-cnf-forma-pago-detalle")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfFormaPagoDetalleService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-forma-pago-detalle-by-cnf-forma-pago")
	public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalleByCnfFormaPago(@Param("id") Long id) {
		List<CnfFormaPagoDetalle> cnfFormaPagoDetalleList = cnfFormaPagoDetalleService.getAllCnfFormaPagoDetalleByCnfFormaPago(id);
		return cnfFormaPagoDetalleList;
	}
}
