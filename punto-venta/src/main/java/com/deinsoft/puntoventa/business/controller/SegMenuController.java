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

import com.deinsoft.puntoventa.business.model.SegMenu;
import com.deinsoft.puntoventa.business.service.SegMenuService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/business/seg-menu")
public class SegMenuController extends CommonController<SegMenu, SegMenuService> {

    private static final Logger logger = LoggerFactory.getLogger(SegMenuController.class);

    @Autowired
    SegMenuService segMenuService;

    @GetMapping(value = "/get-all-seg-menu")
    public List<SegMenu> getAllSegMenu(SegMenu segMenu) {
        logger.info("getAllSegMenu received: " + segMenu.toString());
        List<SegMenu> segMenuList = segMenuService.getAllSegMenu(segMenu);
        return segMenuList;
    }

    @GetMapping(value = "/get-seg-menu")
    public SegMenu getSegMenu(@Param("id") Long id) {
        logger.info("getSegMenu received: " + id);
        SegMenu segMenu = segMenuService.getSegMenu(id);
        return segMenu;
    }

    @PostMapping(value = "/save-seg-menu")
    public ResponseEntity<?> saveSegMenu(@Valid @RequestBody SegMenu segMenu, BindingResult result) {
        if (segMenu.getId() != 0 && segMenu.getParent().getId() == segMenu.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede relacionar con la misma opción de menú");
        }
        return super.crear(segMenu, result);
    }

    @GetMapping(value = "/get-all-seg-menu-combo")
    public List<SegMenu> getAllSegMenu() {
        List<SegMenu> segMenuList = segMenuService.getAllSegMenu();
        return segMenuList;
    }

    @DeleteMapping("/delete-seg-menu")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        segMenuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-seg-menu-by-seg-menu")
    public List<SegMenu> getAllSegMenuBySegMenu(@Param("id") Long id) {
        List<SegMenu> segMenuList = segMenuService.getAllSegMenuBySegMenu(id);
        return segMenuList;
    }
}
