package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.service.CnfFormaPagoDetalleService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfFormaPagoDetalleServiceImpl extends CommonServiceImpl<CnfFormaPagoDetalle, CnfFormaPagoDetalleRepository> implements CnfFormaPagoDetalleService {

    @Autowired
    CnfFormaPagoDetalleRepository cnfFormaPagoDetalleRepository;

    public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalle(CnfFormaPagoDetalle cnfFormaPagoDetalle) {
        List<CnfFormaPagoDetalle> cnfFormaPagoDetalleList = (List<CnfFormaPagoDetalle>) cnfFormaPagoDetalleRepository.getAllCnfFormaPagoDetalle();
        return cnfFormaPagoDetalleList;
    }

    public CnfFormaPagoDetalle getCnfFormaPagoDetalle(Long id) {
        CnfFormaPagoDetalle cnfFormaPagoDetalle = null;
        Optional<CnfFormaPagoDetalle> cnfFormaPagoDetalleOptional = cnfFormaPagoDetalleRepository.findById(id);
        if (cnfFormaPagoDetalleOptional.isPresent()) {
            cnfFormaPagoDetalle = cnfFormaPagoDetalleOptional.get();
        }
        return cnfFormaPagoDetalle;
    }

    public CnfFormaPagoDetalle saveCnfFormaPagoDetalle(CnfFormaPagoDetalle cnfFormaPagoDetalle) {
        CnfFormaPagoDetalle cnfFormaPagoDetalleResult = cnfFormaPagoDetalleRepository.save(cnfFormaPagoDetalle);
        return cnfFormaPagoDetalleResult;
    }

    public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalle() {
        List<CnfFormaPagoDetalle> cnfFormaPagoDetalleList = (List<CnfFormaPagoDetalle>) cnfFormaPagoDetalleRepository.findAll();
        return cnfFormaPagoDetalleList;
    }

    public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalleByCnfFormaPago(long id) {
        List<CnfFormaPagoDetalle> CnfFormaPagoDetalleList = (List<CnfFormaPagoDetalle>) cnfFormaPagoDetalleRepository.findByCnfFormaPagoId(id);
        return CnfFormaPagoDetalleList;
    }

    @Override
    public void delete(long id) {
        CnfFormaPagoDetalle cnfFormaPagoDetalle = null;
        Optional<CnfFormaPagoDetalle> cnfFormaPagoDetalleOptional = cnfFormaPagoDetalleRepository.findById(id);

        if (cnfFormaPagoDetalleOptional.isPresent()) {
            cnfFormaPagoDetalle = cnfFormaPagoDetalleOptional.get();
            cnfFormaPagoDetalleRepository.delete(cnfFormaPagoDetalle);
        }
    }
}
