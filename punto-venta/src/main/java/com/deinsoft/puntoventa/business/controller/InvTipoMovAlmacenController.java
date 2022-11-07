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

import com.deinsoft.puntoventa.business.model.InvTipoMovAlmacen;
import com.deinsoft.puntoventa.business.service.InvTipoMovAlmacenService;

@RestController
@RequestMapping("/api/business/inv-tipo-mov-almacen")
public class InvTipoMovAlmacenController extends CommonController<InvTipoMovAlmacen, InvTipoMovAlmacenService> {

    private static final Logger logger = LoggerFactory.getLogger(InvTipoMovAlmacenController.class);

    @Autowired
    InvTipoMovAlmacenService invTipoMovAlmacenService;

    @GetMapping(value = "/get-all-inv-tipo-mov-almacen")
    public List<InvTipoMovAlmacen> getAllInvTipoMovAlmacen(InvTipoMovAlmacen invTipoMovAlmacen) {
        logger.info("getAllInvTipoMovAlmacen received: " + invTipoMovAlmacen.toString());
        List<InvTipoMovAlmacen> invTipoMovAlmacenList = invTipoMovAlmacenService.getAllInvTipoMovAlmacen(invTipoMovAlmacen);
        return invTipoMovAlmacenList;
    }

    @GetMapping(value = "/get-inv-tipo-mov-almacen")
    public InvTipoMovAlmacen getInvTipoMovAlmacen(@Param("id") Long id) {
        logger.info("getInvTipoMovAlmacen received: " + id);
        InvTipoMovAlmacen invTipoMovAlmacen = invTipoMovAlmacenService.getInvTipoMovAlmacen(id);
        return invTipoMovAlmacen;
    }

    @PostMapping(value = "/save-inv-tipo-mov-almacen")
    public ResponseEntity<?> saveInvTipoMovAlmacen(@Valid @RequestBody InvTipoMovAlmacen invTipoMovAlmacen, BindingResult result) {
        return super.crear(invTipoMovAlmacen, result);
    }

    @GetMapping(value = "/get-all-inv-tipo-mov-almacen-combo")
    public List<InvTipoMovAlmacen> getAllInvTipoMovAlmacen() {
        List<InvTipoMovAlmacen> invTipoMovAlmacenList = invTipoMovAlmacenService.getAllInvTipoMovAlmacen();
        return invTipoMovAlmacenList;
    }

    @DeleteMapping("/delete-inv-tipo-mov-almacen")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        invTipoMovAlmacenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
