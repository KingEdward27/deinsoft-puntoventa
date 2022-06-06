/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.model;

/**
 *
 * @author EDWARD-PC
 */
public class ReferenceTable {
    private String tableName;
    private String referencedTableName;
    private String referencedColumnName;

    public ReferenceTable() {
    }

    public ReferenceTable(String tableName, String referencedTableName, String referencedColumnName) {
        this.tableName = tableName;
        this.referencedTableName = referencedTableName;
        this.referencedColumnName = referencedColumnName;
    }
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    public void setReferencedColumnName(String referencedColumnName) {
        this.referencedColumnName = referencedColumnName;
    }
    
    
}
