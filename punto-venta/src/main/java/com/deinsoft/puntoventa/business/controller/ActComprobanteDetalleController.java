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

import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;
import com.deinsoft.puntoventa.business.service.ActComprobanteDetalleService;

@RestController
@RequestMapping("/api/business/act-comprobante-detalle")
public class ActComprobanteDetalleController extends CommonController<ActComprobanteDetalle, Long, ActComprobanteDetalleService>{
	
	private static final Logger logger = LoggerFactory.getLogger(ActComprobanteDetalleController.class);
	
	@Autowired 
	ActComprobanteDetalleService actComprobanteDetalleService;
	
	
	@GetMapping(value="/get-all-act-comprobante-detalle")
	public List<ActComprobanteDetalle> getAllActComprobanteDetalle(ActComprobanteDetalle actComprobanteDetalle) {
		logger.info("getAllActComprobanteDetalle received: "+actComprobanteDetalle.toString());
		List<ActComprobanteDetalle> actComprobanteDetalleList = actComprobanteDetalleService.getAllActComprobanteDetalle(actComprobanteDetalle);
		return actComprobanteDetalleList;
	}
	@GetMapping(value="/get-act-comprobante-detalle")
	public ActComprobanteDetalle getActComprobanteDetalle(@Param("id") Long id) {
		logger.info("getActComprobanteDetalle received: "+id);
		ActComprobanteDetalle actComprobanteDetalle = actComprobanteDetalleService.getActComprobanteDetalle(id);
		return actComprobanteDetalle;
	}
	
	@PostMapping(value="/save-act-comprobante-detalle")
	public ResponseEntity<?> saveActComprobanteDetalle(@Valid @RequestBody ActComprobanteDetalle actComprobanteDetalle, BindingResult result) {
		return super.crear(actComprobanteDetalle, result);
	}
	@GetMapping(value="/get-all-act-comprobante-detalle-combo")
	public List<ActComprobanteDetalle> getAllActComprobanteDetalle() {
		List<ActComprobanteDetalle> actComprobanteDetalleList = actComprobanteDetalleService.getAllActComprobanteDetalle();
		return actComprobanteDetalleList;
	}
	@DeleteMapping("/delete-act-comprobante-detalle")
	public ResponseEntity<?> delete(@Param("id") Long id){
		actComprobanteDetalleService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-act-comprobante-detalle-by-act-comprobante")
	public List<ActComprobanteDetalle> getAllActComprobanteDetalleByActComprobante(@Param("id") Long id) {
		List<ActComprobanteDetalle> actComprobanteDetalleList = actComprobanteDetalleService.getAllActComprobanteDetalleByActComprobante(id);
		return actComprobanteDetalleList;
	}
	@GetMapping(value="/get-all-act-comprobante-detalle-by-cnf-producto")
	public List<ActComprobanteDetalle> getAllActComprobanteDetalleByCnfProducto(@Param("id") Long id) {
		List<ActComprobanteDetalle> actComprobanteDetalleList = actComprobanteDetalleService.getAllActComprobanteDetalleByCnfProducto(id);
		return actComprobanteDetalleList;
	}
	@GetMapping(value="/get-all-act-comprobante-detalle-by-cnf-impuesto-condicion")
	public List<ActComprobanteDetalle> getAllActComprobanteDetalleByCnfImpuestoCondicion(@Param("id") Long id) {
		List<ActComprobanteDetalle> actComprobanteDetalleList = actComprobanteDetalleService.getAllActComprobanteDetalleByCnfImpuestoCondicion(id);
		return actComprobanteDetalleList;
	}
}
