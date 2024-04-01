/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.dto;

import java.util.List;

/**
 *
 * @author user
 */
public class SecurityFilterDto {
    Long empresaId;
    List<Long> localIds;

    public SecurityFilterDto() {
    }

    public SecurityFilterDto(Long empresaId, List<Long> localIds) {
        this.empresaId = empresaId;
        this.localIds = localIds;
    }

    
    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public List<Long> getLocalIds() {
        return localIds;
    }

    public void setLocalIds(List<Long> localIds) {
        this.localIds = localIds;
    }
    
    
}
