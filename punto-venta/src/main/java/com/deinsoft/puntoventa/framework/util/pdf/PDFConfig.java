package com.deinsoft.puntoventa.framework.util.pdf;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class PDFConfig {
    private String title;
    private int fontSize;
    private int headerColor;
    private String pageSize;
    private String headerText;
    private String footerText;
    private String logoPath;
    private String printedBy;

    private Float marginLeft = 10f;
    private Float marginRight = 10f;
    private Float marginTop = 10f;
    private Float marginBottom = 10f;

    public float getMarginLeft() {
        return marginLeft != null ? marginLeft : 5f;
    }

    public float getMarginRight() {
        return marginRight != null ? marginRight : 5f;
    }

    public float getMarginTop() {
        return marginTop != null ? marginTop : 5f;
    }

    public float getMarginBottom() {
        return marginBottom != null ? marginBottom : 5f;
    }
}
