/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.bean;

import java.util.List;

/**
 *
 * @author user
 */
public class UploadResponse {
    private int row;
    private List<String> message;

    public UploadResponse() {
    }

    public UploadResponse(int row, List<String> message) {
        this.row = row;
        this.message = message;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    
    
    
}
