package com.deinsoft.puntoventa.framework.util.pdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
public class PDFGenerator {
    public static void generatePDF(OutputStream outputStream, List<PDFContent> contentData, PDFConfig config) throws Exception {
//        Document document = new Document(getPageSize(config.getPageSize()), 20, 20, 40, 40);
//        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//        writer.setPageEvent(new HeaderFooter(config));


//        boolean isTicket = "ticket".equalsIgnoreCase(config.getPageSize());
//
//        float ticketHeight = isTicket ? calculateTicketHeight(contentData) : 800;
//        Document document = new Document(new Rectangle(226, ticketHeight), 10, 10, 10, 10);

        Document document;
        boolean isTicket = "ticket".equalsIgnoreCase(config.getPageSize());
        if (isTicket) {
            float ticketWidth = 226;
            float ticketHeight = calculateTicketHeight(contentData);
            document = new Document(new Rectangle(ticketWidth, ticketHeight), config.getMarginLeft(), config.getMarginRight(), config.getMarginTop(), config.getMarginBottom());
        } else {
            Rectangle pageSize = getPageSize(config.getPageSize());
            document = new Document(pageSize, config.getMarginLeft(), config.getMarginRight(), config.getMarginTop(), config.getMarginBottom());
        }

        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        if (!isTicket) {
            writer.setPageEvent(new HeaderFooter(config));
        }
        document.open();

        // Agregar logo si está configurado
        if (config.getLogoPath() != null) {
            Image logo = Image.getInstance(config.getLogoPath());
            float logoWidth = logo.getWidth();
            float logoHeight = logo.getHeight();
            float maxWidth = document.getPageSize().getWidth() / 4; // Máximo ancho del logo
            float maxHeight = document.getPageSize().getHeight() / 8; // Máximo alto del logo

            float scaleFactor = Math.min(maxWidth / logoWidth, maxHeight / logoHeight);
            if (scaleFactor < 1) {
                logo.scaleToFit(logoWidth * scaleFactor, logoHeight * scaleFactor);
            }

            logo.setAbsolutePosition(document.leftMargin(), document.getPageSize().getHeight() - logo.getScaledHeight() - document.topMargin());
            document.add(logo);
        }

        // Procesar contenido dinámico
        for (PDFContent section : contentData) {

            switch (section.getType()) {
                case TEXT:
                    String text = section.getText();
                    int fontSize = section.getFontSize() == null? config.getFontSize() : section.getFontSize();
                    String alignment = section.getAlignment();
                    float spacingAfter = section.getSpacingAfterOrDefault();

                    Font textFont = new Font(Font.FontFamily.HELVETICA, fontSize, getFont(section.getFontWeight()),
                            hexToBaseColor(section.getColorHexOrDefault()));
                    Paragraph textParagraph = new Paragraph(text, textFont);
                    textParagraph.setAlignment(getAlignment(alignment));
                    textParagraph.setSpacingAfter(spacingAfter);
                    document.add(textParagraph);
                    break;
                case TABLE:
                    List<Map<String, String>> data = section.getTableData();
                    PdfPTable table = new PdfPTable(data.get(0).size());
                    table.setWidthPercentage(100);

                    boolean hasBorders = section.isHasBorders();
                    if (!hasBorders) {
                        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    }

                    // Encabezados
                    Font headerFont = new Font(Font.FontFamily.HELVETICA, config.getFontSize(), Font.BOLD, hexToBaseColor(section.getColorHexOrDefault()));
                    for (String key : data.get(0).keySet()) {
                        PdfPCell headerCell = new PdfPCell(new Phrase(key, headerFont));
                        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        if (!hasBorders) {
                            headerCell.setBorder(Rectangle.NO_BORDER);
                        }
                        table.addCell(headerCell);
                    }

                    // Datos
                    Font dataFont = new Font(Font.FontFamily.HELVETICA, config.getFontSize());
                    for (Map<String, String> row : data) {
                        for (String value : row.values()) {
                            PdfPCell cell = new PdfPCell(new Phrase(value, dataFont));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            if (!hasBorders) {
                                cell.setBorder(Rectangle.NO_BORDER);
                            }
                            table.addCell(cell);
                        }
                    }
                    document.add(table);
                    break;
                case SEPARATOR:
                    addDashedSeparator(document, config);
                    break;
            }
//            if (section.getText() != null) {
//                String text = section.getText();
//                int fontSize = section.getFontSize() == null? config.getFontSize() : section.getFontSize();
//                int color = section.getColor();
//                String alignment = section.getAlignment();
//                float spacingAfter = section.getSpacingAfter();
//
//                Font textFont = new Font(Font.FontFamily.HELVETICA, fontSize, Font.NORMAL, new BaseColor(color));
//                Paragraph textParagraph = new Paragraph(text, textFont);
//                textParagraph.setAlignment(getAlignment(alignment));
//                textParagraph.setSpacingAfter(spacingAfter);
//                document.add(textParagraph);
//            }
//
//            if (section.isSeparator()) {
//                addDashedSeparator(document, config);
//            }

//            if (section.get("data") != null && !((List<Map<String, String>>) section.get("data")).isEmpty()) {
//                List<Map<String, String>> data = (List<Map<String, String>>) section.get("data");
//                PdfPTable table = new PdfPTable(data.get(0).size());
//                table.setWidthPercentage(100);
//
//                boolean hasBorders = (boolean) section.getOrDefault("hasBorders", true);
//                if (!hasBorders) {
//                    table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//                }
//
//                // Encabezados
//                Font headerFont = new Font(Font.FontFamily.HELVETICA, config.getFontSize(), Font.BOLD, new BaseColor(config.getHeaderColor()));
//                for (String key : data.get(0).keySet()) {
//                    PdfPCell headerCell = new PdfPCell(new Phrase(key, headerFont));
//                    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    if (!hasBorders) {
//                        headerCell.setBorder(Rectangle.NO_BORDER);
//                    }
//                    table.addCell(headerCell);
//                }
//
//                // Datos
//                Font dataFont = new Font(Font.FontFamily.HELVETICA, config.getFontSize());
//                for (Map<String, String> row : data) {
//                    for (String value : row.values()) {
//                        PdfPCell cell = new PdfPCell(new Phrase(value, dataFont));
//                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        if (!hasBorders) {
//                            cell.setBorder(Rectangle.NO_BORDER);
//                        }
//                        table.addCell(cell);
//                    }
//                }
//                document.add(table);
//            }
        }


        // Agregar comando de corte para impresoras térmicas
        addCutCommand(outputStream);
        document.close();
    }

    private static int getFont(String font) {
        if (font == null) return Font.NORMAL;
        switch (font.toLowerCase()) {
            case "bold": return Font.BOLD;
            case "normal": return Font.NORMAL;
            case "italic": return Font.ITALIC;
            default: return Font.NORMAL;
        }
    }

    private static int getAlignment(String alignment) {
        switch (alignment.toLowerCase()) {
            case "center": return Element.ALIGN_CENTER;
            case "right": return Element.ALIGN_RIGHT;
            case "left": return Element.ALIGN_LEFT;
            default: return Element.ALIGN_LEFT;
        }
    }
    private static BaseColor hexToBaseColor(String hex) {
        if (hex == null || !hex.matches("#?[0-9A-Fa-f]{6}")) {
            return BaseColor.BLACK; // Color por defecto
        }
        hex = hex.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new BaseColor(r, g, b);
    }
    private static void addDashedSeparator(Document document, PDFConfig config) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(PdfPCell.NO_BORDER);
//        cell.setBorderWidthBottom(1);
//        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setCellEvent(new DottedCellEvent());
        table.addCell(cell);
        document.add(table);
    }
    private static float calculateTicketHeight(List<PDFContent> contentData) {
        float baseHeight = 50; // Margen mínimo
        float lineHeight = 20; // Estimación de altura por línea
        float totalHeight = baseHeight;

        for (PDFContent section : contentData) {
            totalHeight += lineHeight;
            totalHeight += (float) section.getSpacingAfterOrDefault();
        }
        return totalHeight;
    }

    private static Rectangle getPageSize(String pageSize) {
        switch (pageSize.toLowerCase()) {
            case "a4": return PageSize.A4;
            case "ticket": return new Rectangle(200, 800);
            default: return PageSize.A4;
        }
    }

    static class HeaderFooter extends PdfPageEventHelper {
        private PDFConfig config;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        public HeaderFooter(PDFConfig config) {
            this.config = config;
        }

        @Override
//        public void onEndPage(PdfWriter writer, Document document) {
//            PdfContentByte canvas = writer.getDirectContent();
//            Font font = new Font(Font.FontFamily.HELVETICA, config.getFontSize(), Font.ITALIC);
//            Phrase header = new Phrase(config.getHeaderText(), font);
//            Phrase footer = new Phrase("Usuario: " + config.getPrintedBy() + " | Fecha: " + dateFormat.format(new Date()) + " | Página: " + writer.getPageNumber(), font);
//
//            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, header, document.left() + document.right() / 2, document.top() + 10, 0);
//            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, footer, document.left() + document.right() / 2, document.bottom() - 10, 0);
//        }

        public void onEndPage(PdfWriter writer, Document document) {
            if ("ticket".equalsIgnoreCase(config.getPageSize())) return; // No agregar pie de página en tickets

            PdfContentByte canvas = writer.getDirectContent();
            Font font = new Font(Font.FontFamily.HELVETICA, config.getFontSize(), Font.ITALIC);
            Phrase footer = new Phrase("Usuario: " + config.getPrintedBy() + " | Fecha: " + dateFormat.format(new Date()), font);
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, footer, document.left() + document.right() / 2, document.bottom() - 10, 0);
        }
    }
    private static void addCutCommand(OutputStream outputStream) throws Exception {
        outputStream.write(new byte[]{ 0x1B, 0x69 }); // Comando ESC/POS para corte total de papel
        outputStream.flush();
    }
    static class DottedCellEvent implements PdfPCellEvent {
        @Override
        public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            canvas.setLineDash(3, 3);
            canvas.moveTo(position.getLeft(), position.getBottom());
            canvas.lineTo(position.getRight(), position.getBottom());
            canvas.stroke();
        }
    }
}
