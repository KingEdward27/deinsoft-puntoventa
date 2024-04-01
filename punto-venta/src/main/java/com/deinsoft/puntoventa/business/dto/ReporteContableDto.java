/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.dto;

/**
 *
 * @author user
 */
public class ReporteContableDto {
    String periodo;
    Double total;
    String fileNameCompras;
    String fileNameVentas;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFileNameCompras() {
        return fileNameCompras;
    }

    public void setFileNameCompras(String fileNameCompras) {
        this.fileNameCompras = fileNameCompras;
    }

    public String getFileNameVentas() {
        return fileNameVentas;
    }

    public void setFileNameVentas(String fileNameVentas) {
        this.fileNameVentas = fileNameVentas;
    }

    
    public ReporteContableDto() {
    }

    public ReporteContableDto(String periodo, Double total, String fileNameCompras,String fileNameVentas) {
        this.periodo = periodo;
        this.total = total;
        this.fileNameCompras = fileNameCompras;
        this.fileNameVentas = fileNameVentas;
    }
    
    
}
