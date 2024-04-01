/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import java.io.OutputStream;
import java.util.List;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author EDWARD-PC
 */
public class GenerateItextPdf {

    private String title;

    private String subTitle;
    private String baseText;
    private int[] anchoColumnas = new int[]{100, 100, 100, 100, 100};

    public GenerateItextPdf() {
        this.title = null;
        this.subTitle = null;
        this.baseText = null;
        this.anchoColumnas = new int[]{100, 100, 100, 100, 100};
    }

    Document doc;
    Paragraph encabezado;
    Paragraph subtitulo;
    Paragraph textoBase;
    OutputStream archivo;
    ByteArrayOutputStream out;
    PdfPTable tablaCab;
    PdfPTable tablaDetail;
    void initialize() throws Exception {
        //creamos un documento
        doc = new Document(PageSize.A4, 30f, 20f, 50f, 20f);
//        archivo = new FileOutputStream("document.pdf");
        out = new ByteArrayOutputStream();
        PdfWriter.getInstance(doc, out);

        //abrimos el archivo
        doc.open();
        doc.add(new Chunk(""));
        encabezado = new Paragraph(title, FontFactory.getFont("arial", 20, Font.BOLD));
        encabezado.setAlignment(encabezado.ALIGN_CENTER);

        subtitulo = new Paragraph(subTitle, FontFactory.getFont("arial", 12, Font.NORMAL));
        subtitulo.setAlignment(subtitulo.ALIGN_CENTER);

        BaseFont tipo = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, false);
        Font fuente = new Font(tipo, 13, Font.BOLD, BaseColor.BLUE);
        textoBase = new Paragraph(baseText, fuente);
    }
    void addElementsAndCloseDoc(String[] cabecera) throws DocumentException, IOException{
        //le añadimos los elementos al documento
        if (title != null) {
            doc.add(encabezado);
        }
        if (subTitle != null) {
            doc.add(subtitulo);
        }
        if (subTitle != null) {
            doc.add(subtitulo);
        }
        if (cabecera != null) {
            doc.add(tablaCab);
        }
        if (tablaDetail != null) {
            doc.add(tablaDetail);
        }
        if (baseText != null) {
            doc.add(textoBase);
        }
        doc.close();
        out.close();
//        archivo.close();
    }
    public OutputStream generateFromTableData(String[] cabecera, List<String> datos) throws DocumentException, IOException {
        //creamos la tabla para las filas del jtable
        tablaCab = new PdfPTable(cabecera.length);
        tablaCab.getDefaultCell().setBorder(0);
        tablaCab.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablaCab.setWidths(anchoColumnas);
        tablaCab.setSpacingBefore(50);
        tablaCab.setWidthPercentage(100);
        for (String string : cabecera) {
            tablaCab.addCell(string);
            tablaCab.addCell(string);
            tablaCab.addCell(string);
            tablaCab.addCell(string);
            tablaCab.addCell(string);
        }

        tablaDetail = new PdfPTable(cabecera.length);
        tablaDetail.getDefaultCell().setBorder(0);

        tablaDetail.setWidths(anchoColumnas);
        tablaDetail.setWidthPercentage(100);

        for (String dato : datos) {
            tablaDetail.addCell(dato);
            tablaDetail.addCell(dato);
            tablaDetail.addCell(dato);
            tablaDetail.addCell(dato);
            tablaDetail.addCell(dato);
        }
        addElementsAndCloseDoc(cabecera);
        return archivo;
    }

    public byte[] generateFromLinearDataImages(List<byte[]> data, List<String> dataCodes) throws Exception {
        initialize();
        tablaDetail = new PdfPTable(anchoColumnas.length);
        tablaDetail.getDefaultCell().setBorder(0);

        tablaDetail.setWidths(anchoColumnas);
        tablaDetail.setWidthPercentage(100);
        for (int i = 0; i < data.size(); i++) {
            PdfPCell cell = new PdfPCell(new Phrase(""));
            
//            BarcodeEAN barcode = new BarcodeEAN();
//            barcode.setCodeType(BarcodeEAN.EAN8);
//            barcode.setCode("0010010012");
//
//            // Create barcode object to put it to the cell as image
//            Imag barcodeObject = barcode.create(Color.darkGray, Color.darkGray);
            
            Image img = Image.getInstance(data.get(i));
            //cell.setCalculatedHeight(50);
            cell.addElement(img);
            Chunk chunk = new Chunk(dataCodes.get(i), FontFactory.getFont("arial", 9, Font.NORMAL));
            chunk.setCharacterSpacing(1);
            
//            Phrase phrase = new Phrase();
//            phrase.add(chunk);

            Paragraph para = new Paragraph();
            para.add(new Phrase(chunk));
            para.setAlignment(Element.ALIGN_CENTER);
            
            cell.addElement(para);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(10);
            cell.setBorderWidth(0);
            tablaDetail.addCell(cell);
        }
        int restanteFila = data.size() > anchoColumnas.length ? 
                data.size() % anchoColumnas.length : anchoColumnas.length - data.size();
        for (int i = 0; i < restanteFila; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(""));
            cell.setBorderWidth(0);
            tablaDetail.addCell(cell);
        }
        //le añadimos los elementos al documento
        addElementsAndCloseDoc(null);
        return out.toByteArray();
    }
    public OutputStream generateFromLinearDataText(List<String> data) throws Exception {
        initialize();
        tablaDetail = new PdfPTable(data.size());
        tablaDetail.getDefaultCell().setBorder(0);
        
        tablaDetail.setWidths(anchoColumnas);
        tablaDetail.setWidthPercentage(100);
        for (int i = 0; i < data.size(); i++) {
            tablaDetail.addCell(data.get(i));
        }
        //le añadimos los elementos al documento
        addElementsAndCloseDoc(null);
        return archivo;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getBaseText() {
        return baseText;
    }

    public void setBaseText(String baseText) {
        this.baseText = baseText;
    }

    public int[] getAnchoColumnas() {
        return anchoColumnas;
    }

    public void setAnchoColumnas(int[] anchoColumnas) {
        this.anchoColumnas = anchoColumnas;
    }

}
