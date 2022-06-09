package com.deinsoft.puntoventa.framework.security.model;

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

@Entity(name = "secRoleUser")
@Table(name = "deinsoft_seg_rol_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_rol_usuario_id", nullable = false, unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "sec_role_id")
    private SecRole secRole;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "empresa_id") 
    private CnfEmpresa empresa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SecUser getSecUser() {
        return secUser;
    }

    public void setSecUser(SecUser secUser) {
        this.secUser = secUser;
    }

    public SecRole getSecRole() {
        return secRole;
    }

    public void setSecRole(SecRole secRole) {
        this.secRole = secRole;
    }

    public CnfEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(CnfEmpresa empresa) {
        this.empresa = empresa;
    }



}
