package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="segMenu")
@Table(name="seg_menu")
public class SegMenu implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_seg_menu_id")
	@SequenceGenerator(name="seq_seg_menu_id", sequenceName = "seq_seg_menu_id",allocationSize=1)
	@Column(name="seg_menu_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 200)
	@Column(name="nombre", length = 200, nullable = true)
	private String nombre;
	
	@NotEmpty
	@Size(max = 10)
	@Column(name="seqorder", length = 10, nullable = false)
	private int seqorder;
	
	@Size(max = 64)
	@Column(name="icon", length = 64, nullable = true)
	private String icon;
	
	@Size(max = 45)
	@Column(name="path", length = 45, nullable = true)
	private String path;
	
	@OneToOne
    @JoinColumn(name="parent_id")
	private SegMenu segMenu;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getSeqorder() {
		return seqorder;
	}
	public void setSeqorder(int seqorder) {
		this.seqorder = seqorder;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public SegMenu getSegMenu() {
		return segMenu;
	}

	public void setSegMenu(SegMenu segMenu) {
		this.segMenu = segMenu;
	}
	@Override
	public String toString() {
		return "segMenu [id=" + id + ", segMenu=" + (segMenu!=null?segMenu:"") + ", nombre=" + nombre + ", seqorder=" + seqorder + ", icon=" + icon + ", path=" + path + "]";	}

	
	
	
}
