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

import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.service.CnfProductoService;

@RestController
public class CnfProductoController extends CommonController<CnfProducto, CnfProductoService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfProductoController.class);
	
	@Autowired 
	CnfProductoService cnfProductoService;
	
	
	@GetMapping(value="/get-all-cnf-producto")
	public List<CnfProducto> getAllCnfProducto(CnfProducto cnfProducto) {
		logger.info("getAllCnfProducto received: "+cnfProducto.toString());
		List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProducto(cnfProducto);
		return cnfProductoList;
	}
	@GetMapping(value="/get-cnf-producto")
	public CnfProducto getCnfProducto(@Param("id") Long id) {
		logger.info("getCnfProducto received: "+id);
		CnfProducto cnfProducto = cnfProductoService.getCnfProducto(id);
		return cnfProducto;
	}
	
	@PostMapping(value="/save-cnf-producto")
	public ResponseEntity<?> saveCnfProducto(@Valid @RequestBody CnfProducto cnfProducto, BindingResult result) {
		return super.crear(cnfProducto, result);
	}
	@GetMapping(value="/get-all-cnf-producto-combo")
	public List<CnfProducto> getAllCnfProducto() {
		List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProducto();
		return cnfProductoList;
	}
	@DeleteMapping("/delete-cnf-producto")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfProductoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-cnf-producto-by-cnf-unidad-medida")
	public List<CnfProducto> getAllCnfProductoByCnfUnidadMedida(@Param("id") Long id) {
		List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfUnidadMedida(id);
		return cnfProductoList;
	}
	@GetMapping(value="/get-all-cnf-producto-by-cnf-empresa")
	public List<CnfProducto> getAllCnfProductoByCnfEmpresa(@Param("id") Long id) {
		List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfEmpresa(id);
		return cnfProductoList;
	}
	@GetMapping(value="/get-all-cnf-producto-by-cnf-sub-categoria")
	public List<CnfProducto> getAllCnfProductoByCnfSubCategoria(@Param("id") Long id) {
		List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfSubCategoria(id);
		return cnfProductoList;
	}
	@GetMapping(value="/get-all-cnf-producto-by-cnf-marca")
	public List<CnfProducto> getAllCnfProductoByCnfMarca(@Param("id") Long id) {
		List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfMarca(id);
		return cnfProductoList;
	}
}
