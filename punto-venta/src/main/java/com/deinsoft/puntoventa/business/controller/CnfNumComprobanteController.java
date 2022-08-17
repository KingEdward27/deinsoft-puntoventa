package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.service.CnfNumComprobanteService;

@RestController
@RequestMapping("/api/business/cnf-num-comprobante")
public class CnfNumComprobanteController extends CommonController<CnfNumComprobante, CnfNumComprobanteService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfNumComprobanteController.class);

    @Autowired
    CnfNumComprobanteService cnfNumComprobanteService;

    @GetMapping(value = "/get-all-cnf-num-comprobante")
    public List<CnfNumComprobante> getAllCnfNumComprobante(CnfNumComprobante cnfNumComprobante) {
        logger.info("getAllCnfNumComprobante received: " + cnfNumComprobante.toString());
        List<CnfNumComprobante> cnfNumComprobanteList = cnfNumComprobanteService.getAllCnfNumComprobante(cnfNumComprobante);
        return cnfNumComprobanteList;
    }

    @GetMapping(value = "/get-cnf-num-comprobante")
    public CnfNumComprobante getCnfNumComprobante(@Param("id") Long id) {
        logger.info("getCnfNumComprobante received: " + id);
        CnfNumComprobante cnfNumComprobante = cnfNumComprobanteService.getCnfNumComprobante(id);
        return cnfNumComprobante;
    }

    @PostMapping(value = "/save-cnf-num-comprobante")
    public ResponseEntity<?> saveCnfNumComprobante(@Valid @RequestBody CnfNumComprobante cnfNumComprobante, BindingResult result) {
        return super.crear(cnfNumComprobante, result);
    }

    @GetMapping(value = "/get-all-cnf-num-comprobante-combo")
    public List<CnfNumComprobante> getAllCnfNumComprobante() {
        List<CnfNumComprobante> cnfNumComprobanteList = cnfNumComprobanteService.getAllCnfNumComprobante();
        return cnfNumComprobanteList;
    }

    @DeleteMapping("/delete-cnf-num-comprobante")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        cnfNumComprobanteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-cnf-num-comprobante-by-cnf-tipo-comprobante")
    public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfTipoComprobante(@Param("id") Long id) {
        List<CnfNumComprobante> cnfNumComprobanteList = cnfNumComprobanteService.getAllCnfNumComprobanteByCnfTipoComprobante(id);
        return cnfNumComprobanteList;
    }

    @GetMapping(value = "/get-all-cnf-num-comprobante-by-cnf-local")
    public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfLocal(@Param("id") Long id) {
        List<CnfNumComprobante> cnfNumComprobanteList = cnfNumComprobanteService.getAllCnfNumComprobanteByCnfLocal(id);
        return cnfNumComprobanteList;
    }
    @GetMapping(value = "/get-cnf-num-comprobante-by-cnf-tipo-comprobante-and-cnf-local")
    public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfTipoComprobante(@Param("id") Long id,@Param("idLocal") Long idLocal) {
        List<CnfNumComprobante> cnfNumComprobanteList
                = cnfNumComprobanteService.getCnfNumComprobanteByCnfTipoComprobanteAndCnfLocal(id,idLocal);
        return cnfNumComprobanteList;
    }
}
