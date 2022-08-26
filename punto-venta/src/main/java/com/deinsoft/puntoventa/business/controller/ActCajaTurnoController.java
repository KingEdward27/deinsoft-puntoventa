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

import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.service.ActCajaTurnoService;

@RestController
public class ActCajaTurnoController extends CommonController<ActCajaTurno, ActCajaTurnoService>{
	
	private static final Logger logger = LoggerFactory.getLogger(ActCajaTurnoController.class);
	
	@Autowired 
	ActCajaTurnoService actCajaTurnoService;
	
	
	@GetMapping(value="/get-all-act-caja-turno")
	public List<ActCajaTurno> getAllActCajaTurno(ActCajaTurno actCajaTurno) {
		logger.info("getAllActCajaTurno received: "+actCajaTurno.toString());
		List<ActCajaTurno> actCajaTurnoList = actCajaTurnoService.getAllActCajaTurno(actCajaTurno);
		return actCajaTurnoList;
	}
	@GetMapping(value="/get-act-caja-turno")
	public ActCajaTurno getActCajaTurno(@Param("id") Long id) {
		logger.info("getActCajaTurno received: "+id);
		ActCajaTurno actCajaTurno = actCajaTurnoService.getActCajaTurno(id);
		return actCajaTurno;
	}
	
	@PostMapping(value="/save-act-caja-turno")
	public ResponseEntity<?> saveActCajaTurno(@Valid @RequestBody ActCajaTurno actCajaTurno, BindingResult result) {
		return super.crear(actCajaTurno, result);
	}
	@GetMapping(value="/get-all-act-caja-turno-combo")
	public List<ActCajaTurno> getAllActCajaTurno() {
		List<ActCajaTurno> actCajaTurnoList = actCajaTurnoService.getAllActCajaTurno();
		return actCajaTurnoList;
	}
	@DeleteMapping("/delete-act-caja-turno")
	public ResponseEntity<?> delete(@Param("id") Long id){
		actCajaTurnoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-act-caja-turno-by-seg-usuario")
	public List<ActCajaTurno> getAllActCajaTurnoBySegUsuario(@Param("id") Long id) {
		List<ActCajaTurno> actCajaTurnoList = actCajaTurnoService.getAllActCajaTurnoBySegUsuario(id);
		return actCajaTurnoList;
	}
}
