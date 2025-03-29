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

import com.deinsoft.puntoventa.business.model.InvMovAlmacenDet;
import com.deinsoft.puntoventa.business.service.InvMovAlmacenDetService;

@RestController
@RequestMapping("/api/business/inv-mov-almacen-det")
public class InvMovAlmacenDetController extends CommonController<InvMovAlmacenDet, Long, InvMovAlmacenDetService> {

    private static final Logger logger = LoggerFactory.getLogger(InvMovAlmacenDetController.class);

    @Autowired
    InvMovAlmacenDetService invMovAlmacenDetService;

    @GetMapping(value = "/get-all-inv-mov-almacen-det")
    public List<InvMovAlmacenDet> getAllInvMovAlmacenDet(InvMovAlmacenDet invMovAlmacenDet) {
        logger.info("getAllInvMovAlmacenDet received: " + invMovAlmacenDet.toString());
        List<InvMovAlmacenDet> invMovAlmacenDetList = invMovAlmacenDetService.getAllInvMovAlmacenDet(invMovAlmacenDet);
        return invMovAlmacenDetList;
    }

    @GetMapping(value = "/get-inv-mov-almacen-det")
    public InvMovAlmacenDet getInvMovAlmacenDet(@Param("id") Long id) {
        logger.info("getInvMovAlmacenDet received: " + id);
        InvMovAlmacenDet invMovAlmacenDet = invMovAlmacenDetService.getInvMovAlmacenDet(id);
        return invMovAlmacenDet;
    }

    @PostMapping(value = "/save-inv-mov-almacen-det")
    public ResponseEntity<?> saveInvMovAlmacenDet(@Valid @RequestBody InvMovAlmacenDet invMovAlmacenDet, BindingResult result) {
        return super.crear(invMovAlmacenDet, result);
    }

    @GetMapping(value = "/get-all-inv-mov-almacen-det-combo")
    public List<InvMovAlmacenDet> getAllInvMovAlmacenDet() {
        List<InvMovAlmacenDet> invMovAlmacenDetList = invMovAlmacenDetService.getAllInvMovAlmacenDet();
        return invMovAlmacenDetList;
    }

    @DeleteMapping("/delete-inv-mov-almacen-det")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        invMovAlmacenDetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-inv-mov-almacen-det-by-inv-mov-almacen")
    public List<InvMovAlmacenDet> getAllInvMovAlmacenDetByInvMovAlmacen(@Param("id") Long id) {
        List<InvMovAlmacenDet> invMovAlmacenDetList = invMovAlmacenDetService.getAllInvMovAlmacenDetByInvMovAlmacen(id);
        return invMovAlmacenDetList;
    }

    @GetMapping(value = "/get-all-inv-mov-almacen-det-by-cnf-producto")
    public List<InvMovAlmacenDet> getAllInvMovAlmacenDetByCnfProducto(@Param("id") Long id) {
        List<InvMovAlmacenDet> invMovAlmacenDetList = invMovAlmacenDetService.getAllInvMovAlmacenDetByCnfProducto(id);
        return invMovAlmacenDetList;
    }
}
