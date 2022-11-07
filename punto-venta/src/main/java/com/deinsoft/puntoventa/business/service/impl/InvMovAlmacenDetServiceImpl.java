package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvMovAlmacenDet;
import com.deinsoft.puntoventa.business.repository.InvMovAlmacenDetRepository;
import com.deinsoft.puntoventa.business.service.InvMovAlmacenDetService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class InvMovAlmacenDetServiceImpl extends CommonServiceImpl<InvMovAlmacenDet, InvMovAlmacenDetRepository> implements InvMovAlmacenDetService {

    @Autowired
    InvMovAlmacenDetRepository invMovAlmacenDetRepository;

    public List<InvMovAlmacenDet> getAllInvMovAlmacenDet(InvMovAlmacenDet invMovAlmacenDet) {
        List<InvMovAlmacenDet> invMovAlmacenDetList = (List<InvMovAlmacenDet>) invMovAlmacenDetRepository.getAllInvMovAlmacenDet(invMovAlmacenDet.getNroserie().toUpperCase());
        return invMovAlmacenDetList;
    }

    public InvMovAlmacenDet getInvMovAlmacenDet(Long id) {
        InvMovAlmacenDet invMovAlmacenDet = null;
        Optional<InvMovAlmacenDet> invMovAlmacenDetOptional = invMovAlmacenDetRepository.findById(id);
        if (invMovAlmacenDetOptional.isPresent()) {
            invMovAlmacenDet = invMovAlmacenDetOptional.get();
        }
        return invMovAlmacenDet;
    }

    public InvMovAlmacenDet saveInvMovAlmacenDet(InvMovAlmacenDet invMovAlmacenDet) {
        InvMovAlmacenDet invMovAlmacenDetResult = invMovAlmacenDetRepository.save(invMovAlmacenDet);
        return invMovAlmacenDetResult;
    }

    public List<InvMovAlmacenDet> getAllInvMovAlmacenDet() {
        List<InvMovAlmacenDet> invMovAlmacenDetList = (List<InvMovAlmacenDet>) invMovAlmacenDetRepository.findAll();
        return invMovAlmacenDetList;
    }

    public List<InvMovAlmacenDet> getAllInvMovAlmacenDetByInvMovAlmacen(long id) {
        List<InvMovAlmacenDet> InvMovAlmacenDetList = (List<InvMovAlmacenDet>) invMovAlmacenDetRepository.findByInvMovAlmacenId(id);
        return InvMovAlmacenDetList;
    }

    public List<InvMovAlmacenDet> getAllInvMovAlmacenDetByCnfProducto(long id) {
        List<InvMovAlmacenDet> InvMovAlmacenDetList = (List<InvMovAlmacenDet>) invMovAlmacenDetRepository.findByCnfProductoId(id);
        return InvMovAlmacenDetList;
    }

    @Override
    public void delete(long id) {
        InvMovAlmacenDet invMovAlmacenDet = null;
        Optional<InvMovAlmacenDet> invMovAlmacenDetOptional = invMovAlmacenDetRepository.findById(id);

        if (invMovAlmacenDetOptional.isPresent()) {
            invMovAlmacenDet = invMovAlmacenDetOptional.get();
            invMovAlmacenDetRepository.delete(invMovAlmacenDet);
        }
    }
}
