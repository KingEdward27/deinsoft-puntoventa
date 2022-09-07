package com.deinsoft.puntoventa.business.commons.service;

import com.deinsoft.puntoventa.business.model.SegRolUsuario;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.SegRolUsuarioRepository;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements CommonService<E> {

    @Autowired
    protected R repository;

    @Autowired
    AuthenticationHelper auth;
    
    @Autowired
    SegRolUsuarioRepository segRolUsuarioRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    protected final List<SegRolUsuario> listRoles(){
        SegUsuario segUsuario = auth.getLoggedUserdata();
        return segRolUsuarioRepository.findBySegUsuarioId(segUsuario.getId());
    }
}
