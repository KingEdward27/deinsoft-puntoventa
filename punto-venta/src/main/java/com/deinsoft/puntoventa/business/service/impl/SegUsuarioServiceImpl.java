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
import com.deinsoft.puntoventa.business.model.InvAlmacen;
import com.deinsoft.puntoventa.business.model.SegRol;
import com.deinsoft.puntoventa.business.model.SegRolUsuario;
import com.deinsoft.puntoventa.business.repository.SegRolUsuarioRepository;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.business.service.CnfEmpresaService;
import com.deinsoft.puntoventa.business.service.CnfLocalService;
import com.deinsoft.puntoventa.business.service.CnfTipoDocumentoService;
import com.deinsoft.puntoventa.business.service.InvAlmacenService;
import com.deinsoft.puntoventa.business.service.SegRolService;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.util.MailBean;
import com.deinsoft.puntoventa.util.SendMail;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
    
    @Value("${app.config.url}")
    private String url;
    
    @Value("${app.config.mail.user}")
    private String mailUser;
    
    @Value("${app.config.mail.pass}")
    private String mailPass;
    
    @Autowired
    SendMail sendMail;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private InvAlmacenService invAlmacenService;
    
    @Autowired
    BusinessService businessService;
    
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
        //valida ruc
        if (segUsuario.getRucEmpresa().length() != 11) {
            throw  new RuntimeException("RUC inválido!");
        }
        Map<String,Object> mapRuc = null;
        try {
            mapRuc = businessService.searchSunat(segUsuario.getRucEmpresa());
        } catch (Exception e) {
            throw new RuntimeException("RUC no existe en la SUNAT");
        }
        
        if (mapRuc == null) {
            throw new RuntimeException("RUC no existe en la SUNAT!");
        }
        //validar empresa existente
        CnfEmpresa cnfEmpresa = cnfEmpresaService.getAllCnfEmpresa().stream()
                .filter(predicate-> predicate.getNroDocumento().equals(segUsuario.getRucEmpresa()))
                .findFirst().orElse(null);
        if (cnfEmpresa != null) {
            throw new RuntimeException("La empresa ya se encuentra registrada");
        }
        
        if (Util.validaCorreo(segUsuario.getEmail())) {
            throw  new RuntimeException("El correo tiene un formato inválido");
        }
        
        //validar email existente
        SegUsuario segUsuarioFromDb = getAllSegUsuario().stream()
                .filter(predicate -> predicate.getEmail().equals(segUsuario.getEmail()))
                .findFirst().orElse(null);
        if (segUsuarioFromDb != null) {
            throw  new RuntimeException("El email del usuario ya se encuentra registrado");
        }
        //grabar empresa
        CnfTipoDocumento tipoDoc = cnfTipoDocumentoService.getAllCnfTipoDocumento().stream()
                .filter(predicate -> predicate.getCodigoSunat().equals("6"))
                .findFirst().orElse(null);
        
        CnfEmpresa empresa = new CnfEmpresa();
        empresa.setCnfTipoDocumento(tipoDoc);
        empresa.setNroDocumento(segUsuario.getRucEmpresa());
        empresa.setNombre(mapRuc.get("nombre").toString());
        empresa.setDescripcion(mapRuc.get("nombre").toString());
        empresa.setPerfilEmpresa(segUsuario.getPerfilEmpresa());
        CnfEmpresa empresaResult = cnfEmpresaService.save(empresa);
        
        CnfLocal local = new CnfLocal();
        local.setCnfEmpresa(empresaResult);
        local.setNombre("LOCAL PRINCIPAL");
        CnfLocal localDb = cnfLocalService.save(local);
        
        InvAlmacen almacen = new InvAlmacen();
        almacen.setCnfLocal(localDb);
        almacen.setNombre("ALMACEN PRINCIPAL");
        almacen.setFlagEstado("1");
        invAlmacenService.save(almacen);
         
        SegUsuario newSegUsuario = new SegUsuario();
        newSegUsuario.setCnfEmpresa(empresaResult);
        newSegUsuario.setEmail(segUsuario.getEmail());
        newSegUsuario.setNombre(segUsuario.getEmail().split("@")[0]);
        newSegUsuario.setPassword(passwordEncoder.encode(segUsuario.getPassword()));
        SegUsuario segUsuarioResult = save(newSegUsuario);
        
        //grabar rousuario
        SegRol rolUser = segRolService.getAllSegRol().stream()
                .filter(predicate -> predicate.getNombre().equals("ROLE_ADMIN"))
                .findFirst().orElse(null);
        
        SegRolUsuario segRolUsuario= new SegRolUsuario();
        segRolUsuario.setSegRol(rolUser);
        segRolUsuario.setSegUsuario(segUsuarioResult);
        segRolUsuario.setEmpresa(empresaResult);
        segRolUsuarioRepository.save(segRolUsuario);
        
        return segUsuarioResult;
    }
    
    @Override
    public SegUsuario changePassword(SegUsuario segUsuario) throws Exception {
//        if (segUsuario.getNombre().equalsIgnoreCase("admin")) {
//            throw new Exception("No puede editar este registro");
//        }
        SegUsuario segUsuarioFromBd = new SegUsuario();
        
        if (segUsuario.getId() > 0) {
             segUsuarioFromBd = getSegUsuario(segUsuario.getId());
        }
        segUsuarioFromBd.setPassword(passwordEncoder.encode(segUsuario.getPassword()));
        SegUsuario segUsuarioResult = segUsuarioRepository.save(segUsuarioFromBd);
        return segUsuarioResult;
    }
    
    @Override
    @Transactional
    public SegUsuario getRecoverPassword(SegUsuario segUsuario) throws Exception {
        List<SegUsuario> listSegUsuarioFromBd = null;
        SegUsuario segUsuarioFromBd;
        
        if (!Util.validaCorreo(segUsuario.getEmail())) {
            throw new Exception("Formato de correo incorrecto");
        }
        if (segUsuario.getEmail() != null && !segUsuario.getEmail().isEmpty()) {
             listSegUsuarioFromBd = segUsuarioRepository.findByEmail(segUsuario.getEmail());
        }
        segUsuarioFromBd = Optional.ofNullable(
                listSegUsuarioFromBd).orElse(new ArrayList<>())
                    .stream().findFirst().orElse(new SegUsuario());
        
         if (listSegUsuarioFromBd.isEmpty()) {
            throw new Exception("Usuario no registrado");
        }
         
        segUsuarioFromBd.setFlagRecoverPassword(new Byte("1"));
        SegUsuario segUsuarioResult = segUsuarioRepository.save(segUsuarioFromBd);
        
        sendMailUsuario(segUsuarioResult);
        return segUsuarioResult;
    }
    @Override
    public SegUsuario recoverPassword(SegUsuario segUsuario) throws Exception {
        SegUsuario segUsuarioFromBd;
        segUsuarioFromBd =  getMatchUser(segUsuario.getTokenRecoverPassword());
        
        if (segUsuarioFromBd == null) {
            throw new Exception("Url no coincide con usuario solicitante");
        }
        if (segUsuarioFromBd.getId() == 0) {
            throw new Exception("Url no coincide con usuario solicitante");
        }
        if (segUsuarioFromBd.getFlagRecoverPassword() == new Byte("0")) {
            throw new Exception("Solicitud de cambio de contraseña expirada");
        }
        segUsuarioFromBd.setFlagRecoverPassword(new Byte("0"));
        segUsuarioFromBd.setPassword(passwordEncoder.encode(segUsuario.getPassword()));
        SegUsuario segUsuarioResult = segUsuarioRepository.save(segUsuarioFromBd);
        
        return segUsuarioResult;
    }
    public void sendMailUsuario(SegUsuario usuarioRepo) throws IOException, UnsupportedEncodingException {
        
        //before use -> clean and build
        InputStream is = SegUsuarioServiceImpl.class
                .getResourceAsStream("/mail/correo_recuperarcontraseña.email");

        String claveSeguridad = "";
        try {
            claveSeguridad = Util.encryptMessage(String.valueOf(usuarioRepo.getId()).getBytes("UTF-8")
                    ,"1234567890123456");
        } catch (Exception ex) {
            Logger.getLogger(SegUsuarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Map<String, String> inlineImages = new HashMap<String, String>();
        
        
        String url;
        if (this.environment.getActiveProfiles()[0].equalsIgnoreCase("local")) {
            
            //before use -> clean and build
            URL logotipo1 = SegUsuarioServiceImpl.class
                    .getClassLoader().getResource("mail/logo_deinsoft.png");
            inlineImages.put("image1", logotipo1.getPath());
            
            url = this.url + "#/recover-password;string=" + claveSeguridad;
        } else {
            String tomcatHome = System.getProperty( "catalina.base" );
            inlineImages.put("image1", tomcatHome + "/conf/recursos/videoteca/logowillax.png");
            
            url = this.url + "recover-password;string=" + claveSeguridad;
        }

        
        
        
        String cuerpo = Util.readFile(is, StandardCharsets.UTF_8);
        
        cuerpo = cuerpo.replace("{{titulo}}", "Recuperación de contraseña");
        cuerpo = cuerpo.replace("{{colorCode}}", "#3a55a6");
        cuerpo = cuerpo.replace("{{descripcionSoftware}}", "Software de Gestión de ventas - DEINSOFT");
        
        cuerpo = cuerpo.replace("{{descripcionLogotipo}}", "Deinsoft - Software de Gestión de ventas");
        cuerpo = cuerpo.replace("{{urlLogotipo}}", "cid:image1");
        cuerpo = cuerpo.replace("{{urlInicio}}", "Recuperación de contraseña");
        cuerpo = cuerpo.replace("{{descripcionRecuperacion}}", "Recuperación de contraseña");
        cuerpo = cuerpo.replace("{{linkRecuperaPass}}", url);
        cuerpo = cuerpo.replace("{{telefonoUsuario}}", "123456");
        cuerpo = cuerpo.replace("{{nombreUsuario}}", usuarioRepo.getNombre());
        
        sendMail.sendEmail(new MailBean("Recuperación de contraseña",
                cuerpo,
                mailUser,
                mailPass,
                usuarioRepo.getEmail(),
                null, inlineImages));
    }
    public SegUsuario getMatchUser(String param1) {
        List<SegUsuario> users = segUsuarioRepository.findAll();
        SegUsuario usuarioToUpdate = new SegUsuario();
        for (SegUsuario user : users) {
            String decode = "";
            try {
                decode = Util.decryptMessage(param1.getBytes("UTF-8"),"1234567890123456");
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
            if (decode.equalsIgnoreCase(String.valueOf(user.getId()))) {
                return user;
            }
            
        }
        return usuarioToUpdate;
    }
}
