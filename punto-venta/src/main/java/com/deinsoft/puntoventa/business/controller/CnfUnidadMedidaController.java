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

import com.deinsoft.puntoventa.business.model.CnfUnidadMedida;
import com.deinsoft.puntoventa.business.service.CnfUnidadMedidaService;

@RestController
@RequestMapping("/api/business/cnf-unidad-medida")
public class CnfUnidadMedidaController extends CommonController<CnfUnidadMedida, CnfUnidadMedidaService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfUnidadMedidaController.class);
	
	@Autowired 
	CnfUnidadMedidaService cnfUnidadMedidaService;
	
	
	@GetMapping(value="/get-all-cnf-unidad-medida")
	public List<CnfUnidadMedida> getAllCnfUnidadMedida(CnfUnidadMedida cnfUnidadMedida) {
		logger.info("getAllCnfUnidadMedida received: "+cnfUnidadMedida.toString());
		List<CnfUnidadMedida> cnfUnidadMedidaList = cnfUnidadMedidaService.getAllCnfUnidadMedida(cnfUnidadMedida);
		return cnfUnidadMedidaList;
	}
	@GetMapping(value="/get-cnf-unidad-medida")
	public CnfUnidadMedida getCnfUnidadMedida(@Param("id") Long id) {
		logger.info("getCnfUnidadMedida received: "+id);
		CnfUnidadMedida cnfUnidadMedida = cnfUnidadMedidaService.getCnfUnidadMedida(id);
		return cnfUnidadMedida;
	}
	
	@PostMapping(value="/save-cnf-unidad-medida")
	public ResponseEntity<?> saveCnfUnidadMedida(@Valid @RequestBody CnfUnidadMedida cnfUnidadMedida, BindingResult result) {
		return super.crear(cnfUnidadMedida, result);
	}
	@GetMapping(value="/get-all-cnf-unidad-medida-combo")
	public List<CnfUnidadMedida> getAllCnfUnidadMedida() {
		List<CnfUnidadMedida> cnfUnidadMedidaList = cnfUnidadMedidaService.getAllCnfUnidadMedida();
		return cnfUnidadMedidaList;
	}
	@DeleteMapping("/delete-cnf-unidad-medida")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfUnidadMedidaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
