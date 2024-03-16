package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.SegUsuarioRepository;
import com.deinsoft.puntoventa.business.service.SegUsuarioService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.CnfEmpresa;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.model.CnfTipoDocumento;
import com.deinsoft.puntoventa.business.model.SegRol;
import com.deinsoft.puntoventa.business.model.SegRolUsuario;
import com.deinsoft.puntoventa.business.repository.SegRolUsuarioRepository;
import com.deinsoft.puntoventa.business.service.CnfEmpresaService;
import com.deinsoft.puntoventa.business.service.CnfLocalService;
import com.deinsoft.puntoventa.business.service.CnfTipoDocumentoService;
import com.deinsoft.puntoventa.business.service.SegRolService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@Transactional
public class SegUsuarioServiceImpl extends CommonServiceImpl<SegUsuario, SegUsuarioRepository> implements SegUsuarioService {

    @Autowired
    SegUsuarioRepository segUsuarioRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    CnfEmpresaService cnfEmpresaService;
    
    @Autowired
    CnfLocalService cnfLocalService;
    
    @Autowired
    CnfTipoDocumentoService cnfTipoDocumentoService;
    
    @Autowired
    SegRolUsuarioRepository segRolUsuarioRepository;
    
    @Autowired
    SegRolService segRolService;
    
    public List<SegUsuario> getAllSegUsuario(SegUsuario segUsuario) {
        List<SegUsuario> segUsuarioList = (List<SegUsuario>) segUsuarioRepository.getAllSegUsuario(segUsuario.getNombre().toUpperCase(), segUsuario.getEmail().toUpperCase(), segUsuario.getPassword().toUpperCase());
        return segUsuarioList;
    }

    public SegUsuario getSegUsuario(Long id) {
        SegUsuario segUsuario = null;
        Optional<SegUsuario> segUsuarioOptional = segUsuarioRepository.findById(id);
        if (segUsuarioOptional.isPresent()) {
            segUsuario = segUsuarioOptional.get();
        }
        return segUsuario;
    }

    public SegUsuario saveSegUsuario(SegUsuario segUsuario) {
        SegUsuario segUsuarioResult = segUsuarioRepository.save(segUsuario);
        return segUsuarioResult;
    }

    public List<SegUsuario> getAllSegUsuario() {
        List<SegUsuario> segUsuarioList = (List<SegUsuario>) segUsuarioRepository.findAll();
        return segUsuarioList;
    }

    @Override
    public void delete(long id) {
        SegUsuario segUsuario = null;
        Optional<SegUsuario> segUsuarioOptional = segUsuarioRepository.findById(id);

        if (segUsuarioOptional.isPresent()) {
            segUsuario = segUsuarioOptional.get();
            segUsuarioRepository.delete(segUsuario);
        }
    }
    
    @Override
    @Transactional
    public SegUsuario registerNewUser(SegUsuario segUsuario) throws Exception {
        //validar empresa existente
        CnfEmpresa cnfEmpresa = cnfEmpresaService.getAllCnfEmpresa().stream()
                .filter(predicate-> predicate.getNroDocumento().equals(segUsuario.getRucEmpresa()))
                .findFirst().orElse(null);
        if (cnfEmpresa != null) {
            throw  new Exception("La empresa ya se encuentra registrada");
        }
        
        //validar email existente
        SegUsuario segUsuarioFromDb = getAllSegUsuario().stream()
                .filter(predicate -> predicate.getEmail().equals(segUsuario.getEmail()))
                .findFirst().orElse(null);
        if (segUsuarioFromDb != null) {
            throw  new Exception("El email del usuario ya se encuentra registrado");
        }
        //grabar empresa
        CnfTipoDocumento tipoDoc = cnfTipoDocumentoService.getAllCnfTipoDocumento().stream()
                .filter(predicate -> predicate.getCodigoSunat().equals("6"))
                .findFirst().orElse(null);
        
        CnfEmpresa empresa = new CnfEmpresa();
        empresa.setCnfTipoDocumento(tipoDoc);
        empresa.setNroDocumento(segUsuario.getRucEmpresa());
        empresa.setNombre(segUsuario.getNombreEmpresa());
        CnfEmpresa empresaResult = cnfEmpresaService.save(empresa);
        
        CnfLocal local = new CnfLocal();
        local.setCnfEmpresa(empresaResult);
        local.setNombre("LOCAL PRINCIPAL");
        cnfLocalService.save(local);
        
        SegUsuario newSegUsuario = new SegUsuario();
        newSegUsuario.setCnfEmpresa(empresaResult);
        newSegUsuario.setEmail(segUsuario.getEmail());
        newSegUsuario.setNombre(segUsuario.getEmail().split("@")[0]);
        newSegUsuario.setPassword(passwordEncoder.encode(segUsuario.getPassword()));
        SegUsuario segUsuarioResult = save(newSegUsuario);
        
        //grabar rousuario
        SegRol rolUser = segRolService.getAllSegRol().stream()
                .filter(predicate -> predicate.getNombre().equals("ROLE_USER"))
                .findFirst().orElse(null);
        
        SegRolUsuario segRolUsuario= new SegRolUsuario();
        segRolUsuario.setSegRol(rolUser);
        segRolUsuario.setSegUsuario(segUsuarioResult);
        segRolUsuario.setEmpresa(empresaResult);
        segRolUsuarioRepository.save(segRolUsuario);
        
        return segUsuarioResult;
    }
}
