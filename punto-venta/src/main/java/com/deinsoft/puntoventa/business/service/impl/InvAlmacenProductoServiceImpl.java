package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.ActComprobante;

@Service
@Transactional
public class InvAlmacenProductoServiceImpl extends CommonServiceImpl<InvAlmacenProducto, 
        InvAlmacenProductoRepository> implements InvAlmacenProductoService {

    @Autowired
    InvAlmacenProductoRepository invAlmacenProductoRepository;

    public List<InvAlmacenProducto> getAllInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto) {
        List<InvAlmacenProducto> invAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.getAllInvAlmacenProducto();
        return invAlmacenProductoList;
    }

    public InvAlmacenProducto getInvAlmacenProducto(Long id) {
        InvAlmacenProducto invAlmacenProducto = null;
        Optional<InvAlmacenProducto> invAlmacenProductoOptional = invAlmacenProductoRepository.findById(id);
        if (invAlmacenProductoOptional.isPresent()) {
            invAlmacenProducto = invAlmacenProductoOptional.get();
        }
        return invAlmacenProducto;
    }

    public InvAlmacenProducto saveInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto) {
        InvAlmacenProducto invAlmacenProductoResult = invAlmacenProductoRepository.save(invAlmacenProducto);
        return invAlmacenProductoResult;
    }

    public List<InvAlmacenProducto> getAllInvAlmacenProducto() {
        List<InvAlmacenProducto> invAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.findAll();
        return invAlmacenProductoList;
    }

    public List<InvAlmacenProducto> getAllInvAlmacenProductoByInvAlmacen(long id) {
        List<InvAlmacenProducto> InvAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.findByInvAlmacenId(id);
        return InvAlmacenProductoList;
    }

    public List<InvAlmacenProducto> getAllInvAlmacenProductoByCnfProducto(long id) {
        List<InvAlmacenProducto> InvAlmacenProductoList = (List<InvAlmacenProducto>) invAlmacenProductoRepository.findByCnfProductoId(id);
        return InvAlmacenProductoList;
    }

    @Override
    public void delete(long id) {
        InvAlmacenProducto invAlmacenProducto = null;
        Optional<InvAlmacenProducto> invAlmacenProductoOptional = invAlmacenProductoRepository.findById(id);

        if (invAlmacenProductoOptional.isPresent()) {
            invAlmacenProducto = invAlmacenProductoOptional.get();
            invAlmacenProductoRepository.delete(invAlmacenProducto);
        }
    }
    @Override
    public List<InvAlmacenProducto> getReportInvAlmacen(ParamBean paramBean) {
        List<InvAlmacenProducto> list = (List<InvAlmacenProducto>) 
                invAlmacenProductoRepository.getReportInvAlmacen(paramBean);
        return list;
    }
}
