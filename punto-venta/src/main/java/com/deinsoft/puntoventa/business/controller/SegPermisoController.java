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

import com.deinsoft.puntoventa.business.model.SegPermiso;
import com.deinsoft.puntoventa.business.service.SegPermisoService;

@RestController
@RequestMapping("/api/business/seg-permiso")
public class SegPermisoController extends CommonController<SegPermiso, SegPermisoService> {

    private static final Logger logger = LoggerFactory.getLogger(SegPermisoController.class);

    @Autowired
    SegPermisoService segPermisoService;

    @GetMapping(value = "/get-all-seg-permiso")
    public List<SegPermiso> getAllSegPermiso(SegPermiso segPermiso) {
        logger.info("getAllSegPermiso received: " + segPermiso.toString());
        List<SegPermiso> segPermisoList = segPermisoService.getAllSegPermiso(segPermiso);
        return segPermisoList;
    }

    @GetMapping(value = "/get-seg-permiso")
    public SegPermiso getSegPermiso(@Param("id") Long id) {
        logger.info("getSegPermiso received: " + id);
        SegPermiso segPermiso = segPermisoService.getSegPermiso(id);
        return segPermiso;
    }

    @PostMapping(value = "/save-seg-permiso")
    public ResponseEntity<?> saveSegPermiso(@Valid @RequestBody SegPermiso segPermiso, BindingResult result) {
        return super.crear(segPermiso, result);
    }

    @GetMapping(value = "/get-all-seg-permiso-combo")
    public List<SegPermiso> getAllSegPermiso() {
        List<SegPermiso> segPermisoList = segPermisoService.getAllSegPermiso();
        return segPermisoList;
    }

    @DeleteMapping("/delete-seg-permiso")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        segPermisoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-seg-permiso-by-seg-rol")
    public List<SegPermiso> getAllSegPermisoBySegRol(@Param("id") Long id) {
        List<SegPermiso> segPermisoList = segPermisoService.getAllSegPermisoBySegRol(id);
        return segPermisoList;
    }

    @GetMapping(value = "/get-all-seg-permiso-by-seg-menu")
    public List<SegPermiso> getAllSegPermisoBySegMenu(@Param("id") Long id) {
        List<SegPermiso> segPermisoList = segPermisoService.getAllSegPermisoBySegMenu(id);
        return segPermisoList;
    }

    @GetMapping(value = "/get-all-seg-permiso-by-seg-accion")
    public List<SegPermiso> getAllSegPermisoBySegAccion(@Param("id") Long id) {
        List<SegPermiso> segPermisoList = segPermisoService.getAllSegPermisoBySegAccion(id);
        return segPermisoList;
    }
    
    @GetMapping(value = "/get-all-seg-permiso-by-seg-rol-name")
    public List<SegPermiso> getAllSegPermisoBySegRol(@Param("nombre") String nombre) {
        List<SegPermiso> segPermisoList = segPermisoService.getAllSegPermisoBySegRolNombre(nombre);
        return segPermisoList;
    }
}
