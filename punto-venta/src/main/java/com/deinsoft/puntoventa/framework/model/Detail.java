/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author EDWARD-PC
 */
public class Detail {

    private String tableName;
    private String relatedBy;
    private List<Map<String, Object>> filters;
    private UpdateParam updateParam;
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRelatedBy() {
        return relatedBy;
    }

    public void setRelatedBy(String relatedBy) {
        this.relatedBy = relatedBy;
    }

    public List<Map<String, Object>> getFilters() {
        return filters;
    }

    public void setFilters(List<Map<String, Object>> filters) {
        this.filters = filters;
    }

    public UpdateParam getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(UpdateParam updateParam) {
        this.updateParam = updateParam;
    }

    


}
