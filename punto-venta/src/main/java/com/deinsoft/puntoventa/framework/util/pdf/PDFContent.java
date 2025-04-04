package com.deinsoft.puntoventa.framework.util.pdf;

import com.itextpdf.text.BaseColor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class PDFContent {
    private PDFContentType type;
    private String text;
    private List<Map<String, String>> tableData;
    private Integer fontSize;
    private String fontWeight;
    private String colorHex;
    private String alignment = "left";
    private Float spacingAfter = 5F;
    private boolean isSeparator;
    boolean hasBorders = false;

    public PDFContentType getType() { return type; }
    public String getTextOrDefault() { return text != null ? text : ""; }
    public int getFontSizeOrDefault() { return fontSize != null ? fontSize : 12; }
    public String getColorHexOrDefault() { return colorHex != null ? colorHex : "#000000"; }
    public String getAlignmentOrDefault() { return alignment != null ? alignment : "LEFT"; }
    public float getSpacingAfterOrDefault() { return spacingAfter != null ? spacingAfter : 5f; }
//    public List<String> getColumns() { return columns; }
//    public List<List<String>> getData() { return data; }
}
