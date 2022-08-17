package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;

@RestController
@RequestMapping("/api/business/inv-almacen-producto")
public class InvAlmacenProductoController extends CommonController<InvAlmacenProducto, InvAlmacenProductoService> {

    private static final Logger logger = LoggerFactory.getLogger(InvAlmacenProductoController.class);

    @Autowired
    InvAlmacenProductoService invAlmacenProductoService;

    @GetMapping(value = "/get-all-inv-almacen-producto")
    public List<InvAlmacenProducto> getAllInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto) {
        logger.info("getAllInvAlmacenProducto received: " + invAlmacenProducto.toString());
        List<InvAlmacenProducto> invAlmacenProductoList = invAlmacenProductoService.getAllInvAlmacenProducto(invAlmacenProducto);
        return invAlmacenProductoList;
    }

    @GetMapping(value = "/get-inv-almacen-producto")
    public InvAlmacenProducto getInvAlmacenProducto(@Param("id") Long id) {
        logger.info("getInvAlmacenProducto received: " + id);
        InvAlmacenProducto invAlmacenProducto = invAlmacenProductoService.getInvAlmacenProducto(id);
        return invAlmacenProducto;
    }

    @PostMapping(value = "/save-inv-almacen-producto")
    public ResponseEntity<?> saveInvAlmacenProducto(@Valid @RequestBody InvAlmacenProducto invAlmacenProducto, BindingResult result) {
        return super.crear(invAlmacenProducto, result);
    }

    @GetMapping(value = "/get-all-inv-almacen-producto-combo")
    public List<InvAlmacenProducto> getAllInvAlmacenProducto() {
        List<InvAlmacenProducto> invAlmacenProductoList = invAlmacenProductoService.getAllInvAlmacenProducto();
        return invAlmacenProductoList;
    }

    @DeleteMapping("/delete-inv-almacen-producto")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        invAlmacenProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-inv-almacen-producto-by-inv-almacen")
    public List<InvAlmacenProducto> getAllInvAlmacenProductoByInvAlmacen(@Param("id") Long id) {
        List<InvAlmacenProducto> invAlmacenProductoList = invAlmacenProductoService.getAllInvAlmacenProductoByInvAlmacen(id);
        return invAlmacenProductoList;
    }

    @GetMapping(value = "/get-all-inv-almacen-producto-by-cnf-producto")
    public List<InvAlmacenProducto> getAllInvAlmacenProductoByCnfProducto(@Param("id") Long id) {
        List<InvAlmacenProducto> invAlmacenProductoList = invAlmacenProductoService.getAllInvAlmacenProductoByCnfProducto(id);
        return invAlmacenProductoList;
    }
    
    @PostMapping(value = "/get-report-inv-almacen-producto")
    public List<InvAlmacenProducto> getReportActComprobante(@RequestBody ParamBean paramBean) {
        logger.info("getAllActComprobante received: " + paramBean.toString());
        List<InvAlmacenProducto> list = invAlmacenProductoService.getReportInvAlmacen(paramBean);
        return list;
    }
}
