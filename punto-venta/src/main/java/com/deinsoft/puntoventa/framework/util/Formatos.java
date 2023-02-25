/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.framework.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author pablojc
 */
public class Formatos {

    public static SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat sdfFecha3 = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat sdfFecha2 = new SimpleDateFormat("MM/dd/yyyy");
    public static DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
    public static DecimalFormat df2 = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.US));
    public static java.sql.Date FechaSQL(String fecha)throws ParseException 
    {
        java.util.Date f = Formatos.sdfFecha.parse(fecha);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(f.getTime());
        java.sql.Date f2 = new java.sql.Date(gc.getTimeInMillis());
        return f2;
    }
}
