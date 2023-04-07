package com.deinsoft.puntoventa.framework;

import com.deinsoft.puntoventa.framework.controller.*;
import com.deinsoft.puntoventa.framework.model.Detail;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deinsoft.puntoventa.framework.model.JsonData;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import com.deinsoft.puntoventa.framework.service.TransactionService;
import com.deinsoft.puntoventa.framework.util.ExportUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    JdbcRepository jdbcRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping(value = "/")
    public ResponseEntity<?> selectByProperties(@RequestBody JsonData jsonData, HttpServletRequest request,
            HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Apis status: working");
    }

}
