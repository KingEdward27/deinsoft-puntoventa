/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.Service;

import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import java.text.ParseException;

/**
 *
 * @author EDWARD-PC
 */
public interface BusinessService {
     public RespuestaPSE sendApi(String tableName, long id) throws ParseException;
     public byte[] getPDF(long id, int tipo) throws ParseException;
}
