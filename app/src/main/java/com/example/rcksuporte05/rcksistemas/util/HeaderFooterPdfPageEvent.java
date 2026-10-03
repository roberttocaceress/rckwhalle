package com.example.rcksuporte05.rcksistemas.util;

import android.content.res.Resources;
import android.text.Spannable;

import androidx.core.content.res.FontResourcesParserCompat;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HeaderFooterPdfPageEvent extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;

    private static Font titFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private static Font smallFontPequena = new Font(Font.FontFamily.TIMES_ROMAN, 6,
            Font.NORMAL);

    Font calibril = FontFactory.getFont("/assets/fonts/calibril.ttf", BaseFont.IDENTITY_H, 4);
    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(8, 12.5f);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    private void addHeader(PdfWriter writer){

        //final String CALIBRI = "C:\\ImagensAPI\\calibri\\calibri.ttf";
        //final String CALIBRIB = "C:\\ImagensAPI\\calibri\\calibrib.ttf";

        PdfPTable header = new PdfPTable(2);
        try {
            // set defaults
            header.setWidths(new int[]{2, 24});
            header.setTotalWidth(570);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add image
            //Image logo = Image.getInstance(HeaderFooterPdfPageEvent.class.getResource("C:\\ImagensAPI\\ic_splash-web.png"));
            //header.addCell(logo);

            // add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.BOTTOM);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase("iText PDF Header Footer Example", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("https://memorynotfound.com", new Font(Font.FontFamily.HELVETICA, 8)));
            header.addCell(text);

            // write content
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    private void addFooter(PdfWriter writer){

        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = sdf.format(data);
        PdfPTable footer = new PdfPTable(3);
        try {
            footer.setWidths(new int[]{14, 2, 1});
            footer.setTotalWidth(570);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            //footer.addCell(new Phrase("\u00A9 rcksistemas.com.br", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            footer.addCell(new Phrase(dataFormatada, calibril));

            // add copyright
            //footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            //footer.addCell(new Phrase("\u00A9 rcksistemas.com.br", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            //footer.addCell(new Phrase("rcksistemas.com.br", fcalibri));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Pag. %d de", writer.getPageNumber()), calibril) );

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 14, 16, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber()), calibril),
                totalWidth, 6, 0);
    }
}
