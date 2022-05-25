/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.service;

import com.deinsoft.puntoventa.framework.model.JsonData;
import java.util.Map;

/**
 *
 * @author EDWARD-PC
 */
public interface TransactionService {
    Map<String,Object> saveTransaction(JsonData jsonData);
}
