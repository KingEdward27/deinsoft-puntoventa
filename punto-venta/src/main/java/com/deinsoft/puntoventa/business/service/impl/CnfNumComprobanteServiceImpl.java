package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.service.CnfNumComprobanteService;

@Service
@Transactional
public class CnfNumComprobanteServiceImpl extends CommonServiceImpl<CnfNumComprobante, CnfNumComprobanteRepository> 
        implements CnfNumComprobanteService {

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;

    public List<CnfNumComprobante> getAllCnfNumComprobante(CnfNumComprobante cnfNumComprobante) {
        List<CnfNumComprobante> cnfNumComprobanteList = (List<CnfNumComprobante>) cnfNumComprobanteRepository.getAllCnfNumComprobante(cnfNumComprobante.getSerie().toUpperCase());
        return cnfNumComprobanteList;
    }

    public CnfNumComprobante getCnfNumComprobante(Long id) {
        CnfNumComprobante cnfNumComprobante = null;
        Optional<CnfNumComprobante> cnfNumComprobanteOptional = cnfNumComprobanteRepository.findById(id);
        if (cnfNumComprobanteOptional.isPresent()) {
            cnfNumComprobante = cnfNumComprobanteOptional.get();
        }
        return cnfNumComprobante;
    }

    public CnfNumComprobante saveCnfNumComprobante(CnfNumComprobante cnfNumComprobante) {
        CnfNumComprobante cnfNumComprobanteResult = cnfNumComprobanteRepository.save(cnfNumComprobante);
        return cnfNumComprobanteResult;
    }

    public List<CnfNumComprobante> getAllCnfNumComprobante() {
        List<CnfNumComprobante> cnfNumComprobanteList = (List<CnfNumComprobante>) cnfNumComprobanteRepository.findAll();
        return cnfNumComprobanteList;
    }

    public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfTipoComprobante(long id) {
        List<CnfNumComprobante> CnfNumComprobanteList = (List<CnfNumComprobante>) 
                cnfNumComprobanteRepository.findByCnfTipoComprobanteId(id);
        return CnfNumComprobanteList;
    }
    @Override
    public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfLocal(long id) {
        List<CnfNumComprobante> CnfNumComprobanteList = (List<CnfNumComprobante>) cnfNumComprobanteRepository.findByCnfLocalId(id);
        return CnfNumComprobanteList;
    }
    @Override
    public List<CnfNumComprobante> getCnfNumComprobanteByCnfTipoComprobanteAndCnfLocal(long id,long idLocal) {
        List<CnfNumComprobante> CnfNumComprobanteList 
                = (List<CnfNumComprobante>) cnfNumComprobanteRepository.findByCnfTipoComprobanteIdAndCnfLocalId(id,idLocal);
        return CnfNumComprobanteList;
    }
    @Override
    public void delete(long id) {
        CnfNumComprobante cnfNumComprobante = null;
        Optional<CnfNumComprobante> cnfNumComprobanteOptional = cnfNumComprobanteRepository.findById(id);

        if (cnfNumComprobanteOptional.isPresent()) {
            cnfNumComprobante = cnfNumComprobanteOptional.get();
            cnfNumComprobanteRepository.delete(cnfNumComprobante);
        }
    }
}
