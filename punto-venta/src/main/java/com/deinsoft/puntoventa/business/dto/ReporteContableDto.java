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
    String fileName;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ReporteContableDto() {
    }

    public ReporteContableDto(String periodo, Double total, String fileName) {
        this.periodo = periodo;
        this.total = total;
        this.fileName = fileName;
    }
    
    
}
