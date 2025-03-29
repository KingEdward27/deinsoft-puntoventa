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

import com.deinsoft.puntoventa.business.model.CnfTipoDocumento;
import com.deinsoft.puntoventa.business.service.CnfTipoDocumentoService;

@RestController
@RequestMapping("/api/business/cnf-tipo-documento")
public class CnfTipoDocumentoController extends CommonController<CnfTipoDocumento, Long, CnfTipoDocumentoService>{
	
	private static final Logger logger = LoggerFactory.getLogger(CnfTipoDocumentoController.class);
	
	@Autowired 
	CnfTipoDocumentoService cnfTipoDocumentoService;
	
	
	@GetMapping(value="/get-all-cnf-tipo-documento")
	public List<CnfTipoDocumento> getAllCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento) {
		logger.info("getAllCnfTipoDocumento received: "+cnfTipoDocumento.toString());
		List<CnfTipoDocumento> cnfTipoDocumentoList = cnfTipoDocumentoService.getAllCnfTipoDocumento(cnfTipoDocumento);
		return cnfTipoDocumentoList;
	}
	@GetMapping(value="/get-cnf-tipo-documento")
	public CnfTipoDocumento getCnfTipoDocumento(@Param("id") Long id) {
		logger.info("getCnfTipoDocumento received: "+id);
		CnfTipoDocumento cnfTipoDocumento = cnfTipoDocumentoService.getCnfTipoDocumento(id);
		return cnfTipoDocumento;
	}
	
	@PostMapping(value="/save-cnf-tipo-documento")
	public ResponseEntity<?> saveCnfTipoDocumento(@Valid @RequestBody CnfTipoDocumento cnfTipoDocumento, BindingResult result) {
		return super.crear(cnfTipoDocumento, result);
	}
	@GetMapping(value="/get-all-cnf-tipo-documento-combo")
	public List<CnfTipoDocumento> getAllCnfTipoDocumento() {
		List<CnfTipoDocumento> cnfTipoDocumentoList = cnfTipoDocumentoService.getAllCnfTipoDocumento();
		return cnfTipoDocumentoList;
	}
	@DeleteMapping("/delete-cnf-tipo-documento")
	public ResponseEntity<?> delete(@Param("id") Long id){
		cnfTipoDocumentoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
