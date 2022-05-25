/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author EDWARD-PC
 */
public class Util {
    public static BigDecimal getBigDecimalValue(Map<String,Object> map, String key){
        if(map.get(key) == null) return BigDecimal.ZERO;
        return new BigDecimal(map.get(key).toString());
    }
    public Map<String, Object> toMap(Object object,String[] visibles) throws IllegalArgumentException, IllegalAccessException, IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field f : object.getClass().getDeclaredFields()) {
//            System.out.println(f.toString());
//                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getType());
//                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getModifiers()+ " = " + f.get(facturaElectronica));
            if (f.toString().contains("final") || f.toString().contains("list") || 
                    !Arrays.asList(visibles).contains(f.getName())) {
                continue;
            }

            map.put(f.getName(), f.get(object)); 

//                objectBuilder.add(f.getName(), f.get(facturaElectronica).toString());
        }
        return map;
    }
}
