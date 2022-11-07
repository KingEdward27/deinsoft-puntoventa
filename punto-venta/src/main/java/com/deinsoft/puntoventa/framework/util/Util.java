/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public static BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        if (map.get(key) == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(map.get(key).toString());
    }

    public static String getStringValue(Map<String, Object> map, String key) {
        if (map.get(key) == null) {
            return null;
        }
        return map.get(key).toString();
    }

    public static Map<String, Object> toMap(Object object, String[] visibles) {
        Map<String, Object> map = new HashMap<>();
        try {

            for (Field f : object.getClass().getDeclaredFields()) {
//            System.out.println(f.toString());
//                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getType());
                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getModifiers());
                if (visibles == null) {
                    if (f.toString().contains("final") || f.toString().contains("list")) {
                        continue;
                    }
                } else {
                    if (f.toString().contains("final") || f.toString().contains("list")
                            || !Arrays.asList(visibles).contains(f.getName())) {
                        continue;
                    }
                }

                map.put(f.getName(), f.get(object));

//                objectBuilder.add(f.getName(), f.get(facturaElectronica).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    public static byte[] OutputStreamToByteArray(OutputStream myOutputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        baos.writeTo(myOutputStream); 
        byte[] x = baos.toByteArray();
        return x;
    }
}
