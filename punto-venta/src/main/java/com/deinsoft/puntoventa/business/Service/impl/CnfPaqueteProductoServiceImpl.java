package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.model.CnfPaqueteProducto;
import com.deinsoft.puntoventa.business.model.CnfPaqueteProducto;
import com.deinsoft.puntoventa.business.repository.CnfPaqueteProductoRepository;
import com.deinsoft.puntoventa.business.repository.CnfPaqueteProductoRepository;
import com.deinsoft.puntoventa.business.service.CnfPaqueteProductoService;
import com.deinsoft.puntoventa.business.service.StorageService;
import com.deinsoft.puntoventa.config.AppConfig;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import com.deinsoft.puntoventa.framework.util.CodigoQR;
import com.deinsoft.puntoventa.framework.util.GenerateItextPdf;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.util.Constantes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CnfPaqueteProductoServiceImpl extends CommonServiceImpl<CnfPaqueteProducto, String, CnfPaqueteProductoRepository>
        implements CnfPaqueteProductoService {

    @Autowired
    CnfPaqueteProductoRepository cnfProductoRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    AppConfig appConfig;

    @Autowired
    AuthenticationHelper auth;

    static DateTimeFormatter YYYYMMDDHHMMSS_FORMATER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    public CnfPaqueteProducto getCnfPaqueteProducto(String id) throws Exception {
        CnfPaqueteProducto cnfProducto = null;
        Optional<CnfPaqueteProducto> cnfProductoOptional = cnfProductoRepository.findById(id);
        if (cnfProductoOptional.isPresent()) {
            cnfProducto = cnfProductoOptional.get();
            SecurityFilterDto f = listRoles();
//            if (f.getEmpresaId() != cnfProducto.getCnfEmpresa().getId()) {
//                throw new SecurityException(Constantes.MSG_NO_AUTHORIZED);
//            }
        } else {
            throw new SecurityException(Constantes.MSG_NO_EXISTS_ITEM);
        }
        return cnfProducto;
    }

    @Transactional
    public CnfPaqueteProducto saveCnfPaqueteProducto(CnfPaqueteProducto cnfProducto, MultipartFile file) {

        cnfProductoRepository.save(cnfProducto);
        return cnfProducto;
    }

    public List<CnfPaqueteProducto> getAllCnfPaqueteProducto() {
        List<CnfPaqueteProducto> cnfProductoList = (List<CnfPaqueteProducto>) cnfProductoRepository.findAll();
        return cnfProductoList;
    }


    @Override
    public void delete(String id) {
        CnfPaqueteProducto cnfProducto = null;
        Optional<CnfPaqueteProducto> cnfProductoOptional = cnfProductoRepository.findById(id);

        if (cnfProductoOptional.isPresent()) {
            cnfProducto = cnfProductoOptional.get();
            cnfProductoRepository.delete(cnfProducto);
        }
    }

}
