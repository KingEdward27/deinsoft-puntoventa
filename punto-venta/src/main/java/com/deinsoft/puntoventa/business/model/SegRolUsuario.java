package com.deinsoft.puntoventa.business.model;

import com.deinsoft.puntoventa.framework.security.model.*;
import com.deinsoft.puntoventa.business.model.CnfEmpresa;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity(name = "segRolUser")
@Table(name = "seg_rol_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SegRolUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_rol_usuario_id", nullable = false, unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "seg_rol_id")
    private SegRol segRol;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "cnf_empresa_id") 
    private com.deinsoft.puntoventa.business.model.CnfEmpresa empresa;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "cnf_local_id") 
    private CnfLocal local;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    public SegRol getSegRol() {
        return segRol;
    }

    public void setSegRol(SegRol segRol) {
        this.segRol = segRol;
    }

    public CnfEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(CnfEmpresa empresa) {
        this.empresa = empresa;
    }

    public CnfLocal getLocal() {
        return local;
    }

    public void setLocal(CnfLocal local) {
        this.local = local;
    }



}