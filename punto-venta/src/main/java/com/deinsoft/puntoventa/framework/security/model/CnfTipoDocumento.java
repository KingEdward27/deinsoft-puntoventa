package com.deinsoft.puntoventa.framework.security.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;

@Entity(name = "secTipoDocumento")
@Table(name = "cnf_tipo_documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CnfTipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_tipo_documento_id", nullable = false, unique = true)
    private long id;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false) 
    private String name;

    @Size(max = 255)
    @Column(name = "value", length = 255, nullable = true)
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name; 
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

}
