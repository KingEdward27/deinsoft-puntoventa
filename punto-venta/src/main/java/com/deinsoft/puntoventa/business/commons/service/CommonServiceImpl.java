package com.deinsoft.puntoventa.business.commons.service;

import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.model.SegRolUsuario;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.CnfLocalRepository;
import com.deinsoft.puntoventa.business.repository.SegRolUsuarioRepository;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements CommonService<E> {

    @Autowired
    protected R repository;

    @Autowired
    public AuthenticationHelper auth;

    @Autowired
    SegRolUsuarioRepository segRolUsuarioRepository;

    @Autowired
    CnfLocalRepository cnfLocalRepository;

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

    protected final SecurityFilterDto listRoles() {
        List<SegRolUsuario> listRoles = segRolUsuarioRepository.findBySegUsuarioId(auth.getLoggedUserdata().getId());
        SecurityFilterDto securityFilterDto = null;
        List<Long> listLocales = new ArrayList<Long>();
        Long empresaId = null;
        for (SegRolUsuario role : listRoles) {
            if (role.getLocal() == null) {
                cnfLocalRepository.findByCnfEmpresaId(role.getEmpresa().getId());
                listLocales = cnfLocalRepository.findByCnfEmpresaId(role.getEmpresa().getId())
                        .stream().map(mapper -> mapper.getId()).collect(Collectors.toList());
                return new SecurityFilterDto(role.getEmpresa().getId(), listLocales);
            }
        }

        for (SegRolUsuario role : listRoles) {
            if (role.getLocal() != null) {
                listLocales.add(role.getLocal().getId());
                empresaId = role.getEmpresa().getId();
            }
        }
        securityFilterDto = new SecurityFilterDto(empresaId, listLocales);

        return securityFilterDto;
    }

}
