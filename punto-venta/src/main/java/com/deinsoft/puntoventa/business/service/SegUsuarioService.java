package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.SegUsuario;

@Service
@Transactional
public interface SegUsuarioService extends CommonService<SegUsuario> {

    public List<SegUsuario> getAllSegUsuario(SegUsuario segUsuario);

    public SegUsuario getSegUsuario(Long id);

    public SegUsuario saveSegUsuario(SegUsuario segUsuario);

    public List<SegUsuario> getAllSegUsuario();

    public void delete(long id);

    SegUsuario registerNewUser(SegUsuario segUsuario) throws Exception;
    
    public SegUsuario changePassword(SegUsuario segUsuario) throws Exception;
    
    public SegUsuario getRecoverPassword(SegUsuario segUsuario) throws Exception ;
    
    public SegUsuario recoverPassword(SegUsuario segUsuario) throws Exception ;
}
