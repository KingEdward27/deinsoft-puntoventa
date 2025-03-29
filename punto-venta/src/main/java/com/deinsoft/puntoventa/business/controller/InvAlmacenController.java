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

import com.deinsoft.puntoventa.business.model.InvAlmacen;
import com.deinsoft.puntoventa.business.service.InvAlmacenService;

@RestController
@RequestMapping("/api/business/inv-almacen")
public class InvAlmacenController extends CommonController<InvAlmacen, Long, InvAlmacenService>{
	
	private static final Logger logger = LoggerFactory.getLogger(InvAlmacenController.class);
	
	@Autowired 
	InvAlmacenService invAlmacenService;
	
	
	@GetMapping(value="/get-all-inv-almacen")
	public List<InvAlmacen> getAllInvAlmacen(InvAlmacen invAlmacen) {
		logger.info("getAllInvAlmacen received: "+invAlmacen.toString());
		List<InvAlmacen> invAlmacenList = invAlmacenService.getAllInvAlmacen(invAlmacen);
		return invAlmacenList;
	}
	@GetMapping(value="/get-inv-almacen")
	public InvAlmacen getInvAlmacen(@Param("id") Long id) {
		logger.info("getInvAlmacen received: "+id);
		InvAlmacen invAlmacen = invAlmacenService.getInvAlmacen(id);
		return invAlmacen;
	}
	
	@PostMapping(value="/save-inv-almacen")
	public ResponseEntity<?> saveInvAlmacen(@Valid @RequestBody InvAlmacen invAlmacen, BindingResult result) {
		return super.crear(invAlmacen, result);
	}
	@GetMapping(value="/get-all-inv-almacen-combo")
	public List<InvAlmacen> getAllInvAlmacen() {
		List<InvAlmacen> invAlmacenList = invAlmacenService.getAllInvAlmacen();
		return invAlmacenList;
	}
	@DeleteMapping("/delete-inv-almacen")
	public ResponseEntity<?> delete(@Param("id") Long id){
		invAlmacenService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping(value="/get-all-inv-almacen-by-cnf-local")
	public List<InvAlmacen> getAllInvAlmacenByCnfLocal(@Param("id") Long id) {
		List<InvAlmacen> invAlmacenList = invAlmacenService.getAllInvAlmacenByCnfLocal(id);
		return invAlmacenList;
	}
}
