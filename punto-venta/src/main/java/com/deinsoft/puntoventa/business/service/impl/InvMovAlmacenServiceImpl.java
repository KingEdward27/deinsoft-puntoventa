package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.repository.InvMovAlmacenRepository;
import com.deinsoft.puntoventa.business.service.InvMovAlmacenService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.repository.InvMovAlmacenDetRepository;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;
import java.time.LocalDateTime;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class InvMovAlmacenServiceImpl extends CommonServiceImpl<InvMovAlmacen,Long, InvMovAlmacenRepository>
        implements InvMovAlmacenService {

    @Autowired
    InvMovAlmacenRepository invMovAlmacenRepository;

    @Autowired
    InvMovAlmacenDetRepository invMovAlmacenDetRepository;

    @Autowired
    InvAlmacenProductoService invAlmacenProductoService;

    @Autowired
    InvMovimientoProductoRepository invMovimientoProductoRepository;

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;
    
    @Autowired
    InvAlmacenProductoRepository invAlmacenProductoRepository;
    

    public List<InvMovAlmacen> getAllInvMovAlmacen(InvMovAlmacen invMovAlmacen) {
        List<InvMovAlmacen> invMovAlmacenList = (List<InvMovAlmacen>) invMovAlmacenRepository
                .getAllInvMovAlmacen(invMovAlmacen.getSerie(),
                        invMovAlmacen.getNumero().toUpperCase(),
                        invMovAlmacen.getNumeroRef().toUpperCase(), invMovAlmacen.getObservacion().toUpperCase(), invMovAlmacen.getFlagEstado().toUpperCase());
        return invMovAlmacenList;
    }

    public InvMovAlmacen getInvMovAlmacen(Long id) {
        InvMovAlmacen invMovAlmacen = null;
        Optional<InvMovAlmacen> invMovAlmacenOptional = invMovAlmacenRepository.findById(id);
        if (invMovAlmacenOptional.isPresent()) {
            invMovAlmacen = invMovAlmacenOptional.get();
        }
        return invMovAlmacen;
    }

    @Transactional
    @Override
    public InvMovAlmacen saveInvMovAlmacen(InvMovAlmacen invMovAlmacen) throws Exception {
        InvMovAlmacen invMovAlmacenResult = null;
        if (invMovAlmacen.getCnfMaestro().getId() == 0) invMovAlmacen.setCnfMaestro(null);
        try {
            List<CnfNumComprobante> numComprobante = cnfNumComprobanteRepository.findByCnfTipoComprobanteIdAndCnfLocalId(
                    invMovAlmacen.getCnfTipoComprobante().getId(),
                    invMovAlmacen.getCnfLocal().getId());
            if (numComprobante.isEmpty()) {
                throw new Exception("No existe numeración para el tipo de comprobante y el local");
            }

            invMovAlmacen.setFechareg(LocalDateTime.now());
            invMovAlmacen.setNumero(String.valueOf(numComprobante.get(0).getUltimoNro() + 1));
            invMovAlmacen.getListInvMovAlmacenDet().forEach(data -> {
                invMovAlmacen.addInvMovAlmacenDet(data);
            });

            invMovAlmacenResult = invMovAlmacenRepository.save(invMovAlmacen);

            //update num comprobante
            CnfNumComprobante cnfNumComprobante = numComprobante.get(0);
            cnfNumComprobante.setUltimoNro(numComprobante.get(0).getUltimoNro() + 1);
            cnfNumComprobanteRepository.save(cnfNumComprobante);

            //update stock and register product movement
            invAlmacenProductoService.registerProductMovementAndUpdateStock(null, invMovAlmacen);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
            throw e;
        }
        return invMovAlmacenResult;
    }

    @Override
    public void validate(InvMovAlmacen invMovAlmacen) {
        invAlmacenProductoService.registerProductMovementAndUpdateStock(null, invMovAlmacen);

        invMovAlmacen.setFlagEstado("2");
        invMovAlmacenRepository.save(invMovAlmacen);
    }

    public List<InvMovAlmacen> getAllInvMovAlmacen() {
        List<InvMovAlmacen> invMovAlmacenList = (List<InvMovAlmacen>) invMovAlmacenRepository.findAll();
        return invMovAlmacenList;
    }

    public List<InvMovAlmacen> getAllInvMovAlmacenByInvTipoMovAlmacen(long id) {
        List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>) invMovAlmacenRepository.findByInvTipoMovAlmacenId(id);
        return InvMovAlmacenList;
    }

    public List<InvMovAlmacen> getAllInvMovAlmacenByCnfMaestro(long id) {
        List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>) invMovAlmacenRepository.findByCnfMaestroId(id);
        return InvMovAlmacenList;
    }

    public List<InvMovAlmacen> getAllInvMovAlmacenByCnfLocal(long id) {
        List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>) invMovAlmacenRepository.findByCnfLocalId(id);
        return InvMovAlmacenList;
    }

    public List<InvMovAlmacen> getAllInvMovAlmacenByCnfTipoComprobante(long id) {
        List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>) invMovAlmacenRepository.findByCnfTipoComprobanteId(id);
        return InvMovAlmacenList;
    }

    public List<InvMovAlmacen> getAllInvMovAlmacenByInvAlmacen(long id) {
        List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>) invMovAlmacenRepository.findByInvAlmacenId(id);
        return InvMovAlmacenList;
    }

    @Override
    public void delete(long id) {
        InvMovAlmacen invMovAlmacen = null;
        Optional<InvMovAlmacen> invMovAlmacenOptional = invMovAlmacenRepository.findById(id);

        if (invMovAlmacenOptional.isPresent()) {

            invMovAlmacen = invMovAlmacenOptional.get();

            for (InvMovAlmacenDet data : invMovAlmacen.getListInvMovAlmacenDet()) {
                List<InvAlmacenProducto> list
                    = invAlmacenProductoRepository.findByCnfProductoId(data.getCnfProducto().getId());

                if (list.size() > 1) {
                    throw new RuntimeException("No existe un único registro para el almacen y producto seleccionados");
                }
                if (list.size() > 0) {
                    InvAlmacenProducto stock = list.get(0);
                    stock.setCnfProducto(data.getCnfProducto());
                    stock.setInvAlmacen(invMovAlmacen.getInvAlmacen());
                    stock.setCantidad(stock.getCantidad().subtract(data.getCantidad()));
                    invAlmacenProductoRepository.save(stock);
                }
                
            }
            invMovimientoProductoRepository.deleteByInvMovAlmacen(invMovAlmacen);
            invMovAlmacenDetRepository.deleteByInvMovAlmacen(invMovAlmacen);
            invMovAlmacenRepository.delete(invMovAlmacen);
        }
    }

    @Override
    public List<InvMovAlmacen> getReportInvMovAlmacen(ParamBean paramBean) {
        List<InvMovAlmacen> actComprobanteList = (List<InvMovAlmacen>) invMovAlmacenRepository.getReportInvMovAlmacen(paramBean, listRoles());
        return actComprobanteList;
    }
}
