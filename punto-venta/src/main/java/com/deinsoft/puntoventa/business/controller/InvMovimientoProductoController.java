package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.service.InvMovimientoProductoService;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/business/inv-movimiento-producto")
public class InvMovimientoProductoController extends CommonController<InvMovimientoProducto, InvMovimientoProductoService> {

    private static final Logger logger = LoggerFactory.getLogger(InvMovimientoProductoController.class);

    @Autowired
    InvMovimientoProductoService invMovimientoProductoService;

    @GetMapping(value = "/get-all-inv-movimiento-producto")
    public List<InvMovimientoProducto> getAllInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto) {
        logger.info("getAllInvMovimientoProducto received: " + invMovimientoProducto.toString());
        List<InvMovimientoProducto> invMovimientoProductoList = invMovimientoProductoService.getAllInvMovimientoProducto(invMovimientoProducto);
        return invMovimientoProductoList;
    }

    @GetMapping(value = "/get-inv-movimiento-producto")
    public InvMovimientoProducto getInvMovimientoProducto(@Param("id") Long id) {
        logger.info("getInvMovimientoProducto received: " + id);
        InvMovimientoProducto invMovimientoProducto = invMovimientoProductoService.getInvMovimientoProducto(id);
        return invMovimientoProducto;
    }

    @PostMapping(value = "/save-inv-movimiento-producto")
    public ResponseEntity<?> saveInvMovimientoProducto(@Valid @RequestBody InvMovimientoProducto invMovimientoProducto, BindingResult result) {
        return super.crear(invMovimientoProducto, result);
    }

    @GetMapping(value = "/get-all-inv-movimiento-producto-combo")
    public List<InvMovimientoProducto> getAllInvMovimientoProducto() {
        List<InvMovimientoProducto> invMovimientoProductoList = invMovimientoProductoService.getAllInvMovimientoProducto();
        return invMovimientoProductoList;
    }

    @DeleteMapping("/delete-inv-movimiento-producto")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        invMovimientoProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-inv-movimiento-producto-by-inv-almacen")
    public List<InvMovimientoProducto> getAllInvMovimientoProductoByInvAlmacen(@Param("id") Long id) {
        List<InvMovimientoProducto> invMovimientoProductoList = invMovimientoProductoService.getAllInvMovimientoProductoByInvAlmacen(id);
        return invMovimientoProductoList;
    }

    @GetMapping(value = "/get-all-inv-movimiento-producto-by-cnf-producto")
    public List<InvMovimientoProducto> getAllInvMovimientoProductoByCnfProducto(@Param("id") Long id) {
        List<InvMovimientoProducto> invMovimientoProductoList = invMovimientoProductoService.getAllInvMovimientoProductoByCnfProducto(id);
        return invMovimientoProductoList;
    }

    @GetMapping(value = "/get-all-inv-movimiento-producto-by-act-comprobante")
    public List<InvMovimientoProducto> getAllInvMovimientoProductoByActComprobante(@Param("id") Long id) {
        List<InvMovimientoProducto> invMovimientoProductoList = invMovimientoProductoService.getAllInvMovimientoProductoByActComprobante(id);
        return invMovimientoProductoList;
    }
    @PostMapping(value = "/get-report-inv-movimiento-producto")
    public List<InvMovimientoProducto> getReportActComprobante(@RequestBody ParamBean paramBean) {
        logger.info("getAllActComprobante received: " + paramBean.toString());
        List<InvMovimientoProducto> list = invMovimientoProductoService.getReportInvMovimientoProducto(paramBean);
        return list;
    }
    @PostMapping(value = "/get-saldo-inv-movimiento-producto")
    public BigDecimal getSaldoReportActComprobante(@RequestBody ParamBean paramBean) {
        logger.info("getAllActComprobante received: " + paramBean.toString());
        return invMovimientoProductoService.getSaldoReportInvMovimientoProducto(paramBean);
    }
}
