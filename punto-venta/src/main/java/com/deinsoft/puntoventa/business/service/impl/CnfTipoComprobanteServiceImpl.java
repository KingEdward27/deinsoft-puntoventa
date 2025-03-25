package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfTipoComprobante;
import com.deinsoft.puntoventa.business.repository.CnfTipoComprobanteRepository;
import com.deinsoft.puntoventa.business.service.CnfTipoComprobanteService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Transactional
public class CnfTipoComprobanteServiceImpl extends CommonServiceImpl<CnfTipoComprobante,Long, CnfTipoComprobanteRepository> implements CnfTipoComprobanteService {

    @Autowired
    CnfTipoComprobanteRepository cnfTipoComprobanteRepository;

    public List<CnfTipoComprobante> getAllCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante) {
        List<CnfTipoComprobante> cnfTipoComprobanteList = (List<CnfTipoComprobante>) cnfTipoComprobanteRepository.getAllCnfTipoComprobante(cnfTipoComprobante.getNombre().toUpperCase(), cnfTipoComprobante.getCodigoSunat().toUpperCase(), cnfTipoComprobante.getCodigo().toUpperCase(), cnfTipoComprobante.getFlagElectronico().toUpperCase());
        return cnfTipoComprobanteList;
    }

    public CnfTipoComprobante getCnfTipoComprobante(Long id) {
        CnfTipoComprobante cnfTipoComprobante = null;
        Optional<CnfTipoComprobante> cnfTipoComprobanteOptional = cnfTipoComprobanteRepository.findById(id);
        if (cnfTipoComprobanteOptional.isPresent()) {
            cnfTipoComprobante = cnfTipoComprobanteOptional.get();
        }
        return cnfTipoComprobante;
    }

    public CnfTipoComprobante saveCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante) {
        CnfTipoComprobante cnfTipoComprobanteResult = cnfTipoComprobanteRepository.save(cnfTipoComprobante);
        return cnfTipoComprobanteResult;
    }

    public List<CnfTipoComprobante> getAllCnfTipoComprobante() {
        List<CnfTipoComprobante> cnfTipoComprobanteList = (List<CnfTipoComprobante>) cnfTipoComprobanteRepository.findAll();
        return cnfTipoComprobanteList;
    }
    
    public List<CnfTipoComprobante> getAllCnfTipoComprobanteVentas() {
        final List<String> list = Arrays.asList("BOL","FAC","NOT","NC");
        List<CnfTipoComprobante> cnfTipoComprobanteList = (List<CnfTipoComprobante>) cnfTipoComprobanteRepository.findAll()
                .stream().filter(predicate -> list.stream().anyMatch(data -> 
                {
                    return data.equals(predicate.getCodigo());
                }))
                .collect(Collectors.toList());
        return cnfTipoComprobanteList;
    }
    
    public List<CnfTipoComprobante> getAllCnfTipoComprobanteMovAlmacen() {
        final List<String> list = Arrays.asList("NEA","NSA");
        List<CnfTipoComprobante> cnfTipoComprobanteList = (List<CnfTipoComprobante>) cnfTipoComprobanteRepository.findAll()
                .stream().filter(predicate -> list.stream().anyMatch(data -> data.equals(predicate.getCodigo())))
                .collect(Collectors.toList());
        return cnfTipoComprobanteList;
    }
    
    public List<CnfTipoComprobante> getAllCnfTipoComprobanteContrato() {
        final List<String> list = Arrays.asList("CNT");
        List<CnfTipoComprobante> cnfTipoComprobanteList = (List<CnfTipoComprobante>) cnfTipoComprobanteRepository.findAll()
                .stream().filter(predicate -> list.stream().anyMatch(data -> data.equals(predicate.getCodigo())))
                .collect(Collectors.toList());
        return cnfTipoComprobanteList;
    }

    @Override
    public void delete(long id) {
        CnfTipoComprobante cnfTipoComprobante = null;
        Optional<CnfTipoComprobante> cnfTipoComprobanteOptional = cnfTipoComprobanteRepository.findById(id);

        if (cnfTipoComprobanteOptional.isPresent()) {
            cnfTipoComprobante = cnfTipoComprobanteOptional.get();
            cnfTipoComprobanteRepository.delete(cnfTipoComprobante);
        }
    }
}
