package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfMenuTipoSistema")
@Table(name="cnf_menu_tipo_sistema")
public class CnfMenuTipoSistema implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_menu_tipo_sistema_id")
	@SequenceGenerator(name="seq_cnf_menu_tipo_sistema_id", sequenceName = "seq_cnf_menu_tipo_sistema_id",allocationSize=1)
	@Column(name="cnf_menu_tipo_sistema_id",nullable = false, unique = true)
	private long id;
	
	@OneToOne
    @JoinColumn(name="seg_menu_id")
	private SegMenu segMenu;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SegMenu getSegMenu() {
		return segMenu;
	}

	public void setSegMenu(SegMenu segMenu) {
		this.segMenu = segMenu;
	}
	@Override
	public String toString() {
		return "cnfMenuTipoSistema [id=" + id + ", segMenu=" + (segMenu!=null?segMenu:"") + "]";	}

	
	
	
}
