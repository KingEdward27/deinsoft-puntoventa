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

import com.deinsoft.puntoventa.business.model.InvMovAlmacen;
import com.deinsoft.puntoventa.business.service.InvMovAlmacenService;

@RestController
public class InvMovAlmacenController extends CommonController<InvMovAlmacen, InvMovAlmacenService>{
	
	private static final Logger logger = LoggerFactory.getLogger(InvMovAlmacenController.class);
	
	@Autowired 
	InvMovAlmacenService invMovAlmacenService;
	
	
	@GetMapping(value="/get-all-inv-mov-almacen")
	public List<InvMovAlmacen> getAllInvMovAlmacen(InvMovAlmacen invMovAlmacen) {
		logger.info("getAllInvMovAlmacen received: "+invMovAlmacen.toString());
		List<InvMovAlmacen> invMovAlmacenList = invMovAlmacenService.getAllInvMovAlmacen(invMovAlmacen);
		return invMovAlmacenList;
	}
	@GetMapping(value="/get-inv-mov-almacen")
	public InvMovAlmacen getInvMovAlmacen(@Param("id") Long id) {
		logger.info("getInvMovAlmacen received: "+id);
		InvMovAlmacen invMovAlmacen = invMovAlmacenService.getInvMovAlmacen(id);
		return invMovAlmacen;
	}
	
	@PostMapping(value="/save-inv-mov-almacen")
	public ResponseEntity<?> saveInvMovAlmacen(@Valid @RequestBody InvMovAlmacen invMovAlmacen, BindingResult result) {
		return super.crear(invMovAlmacen, result);
	}
	@GetMapping(value="/get-all-inv-mov-almacen-combo")
	public List<InvMovAlmacen> getAllInvMovAlmacen() {
		List<InvMovAlmacen> invMovAlmacenList = invMovAlmacenService.getAllInvMovAlmacen();
		return invMovAlmacenList;
	}
	@DeleteMapping("/delete-inv-mov-almacen")
	public ResponseEntity<?> delete(@Param("id") Long id){
		invMovAlmacenService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-inv-mov-almacen-by-inv-tipo-mov-almacen")
	public List<InvMovAlmacen> getAllInvMovAlmacenByInvTipoMovAlmacen(@Param("id") Long id) {
		List<InvMovAlmacen> invMovAlmacenList = invMovAlmacenService.getAllInvMovAlmacenByInvTipoMovAlmacen(id);
		return invMovAlmacenList;
	}
	@GetMapping(value="/get-all-inv-mov-almacen-by-cnf-maestro")
	public List<InvMovAlmacen> getAllInvMovAlmacenByCnfMaestro(@Param("id") Long id) {
		List<InvMovAlmacen> invMovAlmacenList = invMovAlmacenService.getAllInvMovAlmacenByCnfMaestro(id);
		return invMovAlmacenList;
	}
	@GetMapping(value="/get-all-inv-mov-almacen-by-cnf-local")
	public List<InvMovAlmacen> getAllInvMovAlmacenByCnfLocal(@Param("id") Long id) {
		List<InvMovAlmacen> invMovAlmacenList = invMovAlmacenService.getAllInvMovAlmacenByCnfLocal(id);
		return invMovAlmacenList;
	}
	@GetMapping(value="/get-all-inv-mov-almacen-by-cnf-tipo-comprobante")
	public List<InvMovAlmacen> getAllInvMovAlmacenByCnfTipoComprobante(@Param("id") Long id) {
		List<InvMovAlmacen> invMovAlmacenList = invMovAlmacenService.getAllInvMovAlmacenByCnfTipoComprobante(id);
		return invMovAlmacenList;
	}
	@GetMapping(value="/get-all-inv-mov-almacen-by-inv-almacen")
	public List<InvMovAlmacen> getAllInvMovAlmacenByInvAlmacen(@Param("id") Long id) {
		List<InvMovAlmacen> invMovAlmacenList = invMovAlmacenService.getAllInvMovAlmacenByInvAlmacen(id);
		return invMovAlmacenList;
	}
}
