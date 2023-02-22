package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.repository.CnfProductoRepository;
import com.deinsoft.puntoventa.business.service.CnfProductoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.framework.util.CodigoQR;
import com.deinsoft.puntoventa.framework.util.GenerateItextPdf;
import com.deinsoft.puntoventa.framework.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;

@Service
@Transactional
public class CnfProductoServiceImpl extends CommonServiceImpl<CnfProducto, CnfProductoRepository> 
        implements CnfProductoService {

    @Autowired
    CnfProductoRepository cnfProductoRepository;

    public List<CnfProducto> getAllCnfProducto(CnfProducto cnfProducto) {
        List<CnfProducto> cnfProductoList = (List<CnfProducto>) cnfProductoRepository.getAllCnfProducto(
                cnfProducto.getCodigo().toUpperCase(), 
                cnfProducto.getNombre().toUpperCase(), 
                cnfProducto.getRutaImagen().toUpperCase(), 
                cnfProducto.getFlagEstado().toUpperCase(), 
                cnfProducto.getBarcode().toUpperCase());
        return cnfProductoList;
    }

    public CnfProducto getCnfProducto(Long id) {
        CnfProducto cnfProducto = null;
        Optional<CnfProducto> cnfProductoOptional = cnfProductoRepository.findById(id);
        if (cnfProductoOptional.isPresent()) {
            cnfProducto = cnfProductoOptional.get();
        }
        return cnfProducto;
    }

    public CnfProducto saveCnfProducto(CnfProducto cnfProducto) {
        if(cnfProducto.getCnfSubCategoria().getId() == 0){
            cnfProducto.setCnfSubCategoria(null);
        }
        if(cnfProducto.getCnfMarca() != null && cnfProducto.getCnfMarca().getId() == 0){
            cnfProducto.setCnfMarca(null);
        }
        if(cnfProducto.getCnfUnidadMedida().getId() == 0){
            cnfProducto.setCnfUnidadMedida(null);
        }
        cnfProducto.setFechaRegistro(LocalDateTime.now());
        CnfProducto cnfProductoResult = cnfProductoRepository.save(cnfProducto);
        return cnfProductoResult;
    }

    public List<CnfProducto> getAllCnfProducto() {
        List<CnfProducto> cnfProductoList = (List<CnfProducto>) cnfProductoRepository.findAll();
        return cnfProductoList;
    }

    public List<CnfProducto> getAllCnfProductoByCnfUnidadMedida(long id) {
        List<CnfProducto> CnfProductoList = (List<CnfProducto>) cnfProductoRepository.findByCnfUnidadMedidaId(id);
        return CnfProductoList;
    }

    public List<CnfProducto> getAllCnfProductoByCnfEmpresa(long id) {
        List<CnfProducto> CnfProductoList = (List<CnfProducto>) cnfProductoRepository.findByCnfEmpresaId(id);
        return CnfProductoList;
    }

    public List<CnfProducto> getAllCnfProductoByCnfSubCategoria(long id) {
        List<CnfProducto> CnfProductoList = (List<CnfProducto>) cnfProductoRepository.findByCnfSubCategoriaId(id);
        return CnfProductoList;
    }

    public List<CnfProducto> getAllCnfProductoByCnfMarca(long id) {
        List<CnfProducto> CnfProductoList = (List<CnfProducto>) cnfProductoRepository.findByCnfMarcaId(id);
        return CnfProductoList;
    }

    @Override
    public void delete(long id) {
        CnfProducto cnfProducto = null;
        Optional<CnfProducto> cnfProductoOptional = cnfProductoRepository.findById(id);

        if (cnfProductoOptional.isPresent()) {
            cnfProducto = cnfProductoOptional.get();
            cnfProductoRepository.delete(cnfProducto);
        }
    }

    @Override
    public List<CnfProducto> getAllCnfProductTypeHead(String nameOrValue, long cnfEmpresaId) {
        List<CnfProducto> cnfProductList = (List<CnfProducto>) cnfProductoRepository.getAllCnfProductTypeHead
        (nameOrValue.toUpperCase(),cnfEmpresaId);
        
        return cnfProductList;
    }
    @Override
    public List<CnfProducto> getAllCnfProductTypeHeadNoServicios(String nameOrValue, long cnfEmpresaId) {
        List<CnfProducto> cnfProductList = (List<CnfProducto>) cnfProductoRepository.getAllCnfProductTypeHead
        (nameOrValue.toUpperCase(),cnfEmpresaId).stream()
                .filter(predicate -> !predicate.getCnfUnidadMedida().getCodigoSunat().equals("ZZ"))
                .collect(Collectors.toList());
        
        return cnfProductList;
    }
    @Override
    public List<CnfProducto> getAllCnfProductoCodeBarsPre(ParamBean param) {
        List<CnfProducto> cnfProductoList 
                = (List<CnfProducto>) cnfProductoRepository
                        .findByCnfEmpresaId(param.getCnfEmpresa().getId())
                        .stream()
                .filter(predicate -> !predicate.getCnfUnidadMedida().getCodigoSunat().equals("ZZ"))
                .filter(predicate -> param.getCnfCategoria().getId() == 0 || 
                        predicate.getCnfSubCategoria().getCnfCategoria().getId() == param.getCnfCategoria().getId())
                .collect(Collectors.toList());
        return cnfProductoList;
    }
    
    @Override
    public byte[] getPdfcodeBars(ParamBean param) throws ParseException, Exception {
        GenerateItextPdf generateItextPdf = new GenerateItextPdf();
        List<CnfProducto> cnfProductoList = (List<CnfProducto>) cnfProductoRepository.findByCnfEmpresaId(
                param.getCnfEmpresa().getId())
                .stream()
                .filter(predicate -> !predicate.getCnfUnidadMedida().getCodigoSunat().equals("ZZ"))
                .filter(predicate -> param.getCnfCategoria().getId() == 0 || predicate.getCnfSubCategoria().getCnfCategoria().getId() == param.getCnfCategoria().getId())
                .collect(Collectors.toList());
        List<byte[]> list = new ArrayList<>();
        List<String> listCodes = new ArrayList<>();
        for (CnfProducto cnfProducto : cnfProductoList) {
            String code = StringUtils.leftPad(String.valueOf(cnfProducto.getCnfEmpresa().getId()),3,"0")
                + StringUtils.leftPad(String.valueOf(cnfProducto.getCnfMarca().getId()),3,"0")
                + StringUtils.leftPad(String.valueOf(cnfProducto.getCnfSubCategoria().getCnfCategoria().getId()),3,"0")
                + StringUtils.leftPad(String.valueOf(cnfProducto.getId()),3,"0");
            
            int[] i = {1,3,1,3,1,3,1,3,1,3,1,3};
            int sum = 0;
            for (int j = 0; j < code.length(); j++) {
                sum = sum + Integer.valueOf(String.valueOf(code.charAt(j))) * i[j];
            }
            int checkSum = Math.round(Util.round((sum + 9) / 10, 0)) * 10 - sum;

            listCodes.add(code + checkSum);
            list.add(CodigoQR.generateEAN13BarcodeImage(code + checkSum)); 
        }
        int[] anchoColumnas = {100,100,100,100,100};
        generateItextPdf.setTitle("Códigos de Barras");
        generateItextPdf.setSubTitle(" ");
        generateItextPdf.setAnchoColumnas(anchoColumnas);
        byte[] array = generateItextPdf.generateFromLinearDataImages(list,listCodes);
//        byte[] array = new byte[ius.available()];
//        ius.read(array);

        return array;
    }    
}
