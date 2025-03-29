package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.model.CnfPaqueteProducto;
import com.deinsoft.puntoventa.business.model.CnfPaqueteProducto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public interface CnfPaqueteProductoService extends CommonService<CnfPaqueteProducto, UUID> {


    public CnfPaqueteProducto getCnfPaqueteProducto(String id) throws Exception;

    public CnfPaqueteProducto saveCnfPaqueteProducto(CnfPaqueteProducto cnfProducto,MultipartFile file);

    public List<CnfPaqueteProducto> getAllCnfPaqueteProducto();


    public void delete(String id);
}
