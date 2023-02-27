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

import com.deinsoft.puntoventa.business.model.ActPagoDetalle;
import com.deinsoft.puntoventa.business.service.ActPagoDetalleService;

@RestController
public class ActPagoDetalleController extends CommonController<ActPagoDetalle, ActPagoDetalleService>{
	
	private static final Logger logger = LoggerFactory.getLogger(ActPagoDetalleController.class);
	
	@Autowired 
	ActPagoDetalleService actPagoDetalleService;
	
	
	@GetMapping(value="/get-all-act-pago-detalle")
	public List<ActPagoDetalle> getAllActPagoDetalle(ActPagoDetalle actPagoDetalle) {
		logger.info("getAllActPagoDetalle received: "+actPagoDetalle.toString());
		List<ActPagoDetalle> actPagoDetalleList = actPagoDetalleService.getAllActPagoDetalle(actPagoDetalle);
		return actPagoDetalleList;
	}
	@GetMapping(value="/get-act-pago-detalle")
	public ActPagoDetalle getActPagoDetalle(@Param("id") Long id) {
		logger.info("getActPagoDetalle received: "+id);
		ActPagoDetalle actPagoDetalle = actPagoDetalleService.getActPagoDetalle(id);
		return actPagoDetalle;
	}
	
	@PostMapping(value="/save-act-pago-detalle")
	public ResponseEntity<?> saveActPagoDetalle(@Valid @RequestBody ActPagoDetalle actPagoDetalle, BindingResult result) {
		return super.crear(actPagoDetalle, result);
	}
	@GetMapping(value="/get-all-act-pago-detalle-combo")
	public List<ActPagoDetalle> getAllActPagoDetalle() {
		List<ActPagoDetalle> actPagoDetalleList = actPagoDetalleService.getAllActPagoDetalle();
		return actPagoDetalleList;
	}
	@DeleteMapping("/delete-act-pago-detalle")
	public ResponseEntity<?> delete(@Param("id") Long id){
		actPagoDetalleService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-act-pago-detalle-by-act-pago")
	public List<ActPagoDetalle> getAllActPagoDetalleByActPago(@Param("id") Long id) {
		List<ActPagoDetalle> actPagoDetalleList = actPagoDetalleService.getAllActPagoDetalleByActPago(id);
		return actPagoDetalleList;
	}
	@GetMapping(value="/get-all-act-pago-detalle-by-act-pago-programacion")
	public List<ActPagoDetalle> getAllActPagoDetalleByActPagoProgramacion(@Param("id") Long id) {
		List<ActPagoDetalle> actPagoDetalleList = actPagoDetalleService.getAllActPagoDetalleByActPagoProgramacion(id);
		return actPagoDetalleList;
	}
}
