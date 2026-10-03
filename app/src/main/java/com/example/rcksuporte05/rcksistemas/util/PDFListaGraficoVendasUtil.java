package com.example.rcksuporte05.rcksistemas.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.github.mikephil.charting.charts.PieChart;
import com.google.common.collect.Table;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PDFListaGraficoVendasUtil extends PdfPageEventHelper {
    private static Font titFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 15,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    private static Font normalBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static Font cabFont = new Font(Font.FontFamily.TIMES_ROMAN, 6,
            Font.BOLD);
    private static Font operacaoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);

    private static Font smallFontRed = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL, BaseColor.RED);

    private static Font smallFontPequena = new Font(Font.FontFamily.TIMES_ROMAN, 6,
            Font.NORMAL);

    Font calibril = FontFactory.getFont("/assets/fonts/calibril.ttf", BaseFont.IDENTITY_H, 8);
    Font calibrin = FontFactory.getFont("/assets/fonts/calibri.ttf", BaseFont.IDENTITY_H, 8);
    Font calibrib = FontFactory.getFont("/assets/fonts/calibrib.ttf", BaseFont.IDENTITY_H, 8);

    Font calibrilt= FontFactory.getFont("/assets/fonts/calibrib.ttf", BaseFont.IDENTITY_H, 10);

    Font calibrilr = FontFactory.getFont("/assets/fonts/calibril.ttf", BaseFont.IDENTITY_H, 8, Color.RED);

    private PieChart pieChart;

    private List<ParticipacaoFaturamento> lista;
    private EmpresaParametro empresa;
    private Activity activity;
    private  DBHelper db;
    private String idAndroit;
    private String periodo;


    public PDFListaGraficoVendasUtil() {

    }

    public void PDFGraficoVendasUtil(PieChart pieChart, EmpresaParametro empresa, Activity activity, DBHelper db, String periodo) {
        this.pieChart = pieChart;
        this.empresa = empresa;
        this.activity = activity;
        this.db = db;
        this.periodo = periodo;
    }




    public void PDFListaVendasUtil(List<ParticipacaoFaturamento> lista, EmpresaParametro empresa, Activity activity, DBHelper db, String periodo) {
        this.lista = lista;
        this.empresa = empresa;
        this.activity = activity;
        this.db = db;
        this.periodo = periodo;
    }


    public File criandoGraficoPdf() {
        idAndroit = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            String filename;
            filename = "graficoVendasGrupoMarca" + ".pdf";
            Document document = new Document(PageSize.A4);
            document.setMargins(15, 15, 15, 15);
            String path ;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Pedidos";

            }else {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Pedidos";
            }
            /*
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";// + activity.getString(R.string.app_name) + "/Graficos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";// + activity.getString(R.string.app_name) + "/Graficos";

            }else {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/";// + activity.getString(R.string.app_name) + "/Graficos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";// + activity.getString(R.string.app_name) + "/Graficos";
            }

             */
            File dir = new File(path, filename);
            if (!dir.exists()) {
                try {
                    dir.getParentFile().mkdirs();
                } catch ( Exception e) {
                    Toast.makeText(activity, "Falha ao criar o diretorio/pasta do arquivo!\n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            FileOutputStream fOut = new FileOutputStream(dir);
            fOut.flush();
            PdfWriter writer = PdfWriter.getInstance(document, fOut);

            //HeaderFooterPdfPageEvent event = new HeaderFooterPdfPageEvent();
            //writer.setPageEvent(event);


            document.open();

            Paragraph titulo = new Paragraph( periodo, titFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("           "));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = pieChart.getChartBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image myImg = Image.getInstance(stream.toByteArray());

            myImg.setAlignment(Image.ALIGN_JUSTIFIED_ALL);
            //myImg.setAlignment(Image.ALIGN_RIGHT);
            myImg.setAbsolutePosition(50f, 10f);
            myImg.scalePercent(48, 48);
            document.add(myImg);

            document.close();
            return dir;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Falha ao criar o arquivo PDF, favor entrar em contato com o suporte técnico!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        } catch ( FileNotFoundException e) {
            showAlertInfoApp();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Falha ao criar o arquivo PDF, favor entrar em contato com o suporte técnico!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public File criandoListaPdf() {
        calibrilr.setColor(BaseColor.RED);
        idAndroit = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            String filename;
            filename = "listaVendasGrupoMarca" + ".pdf";
            Document document = new Document(PageSize.A4);
            document.setMargins(15, 15, 15, 15);
            String path ;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Pedidos";

            }else {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Pedidos";
            }
            /*
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";// + activity.getString(R.string.app_name) + "/Graficos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";// + activity.getString(R.string.app_name) + "/Graficos";

            }else {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/";// + activity.getString(R.string.app_name) + "/Graficos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";// + activity.getString(R.string.app_name) + "/Graficos";
            }

             */
            File dir = new File(path, filename);
            if (!dir.exists()) {
                try {
                    dir.getParentFile().mkdirs();
                } catch ( Exception e) {
                    Toast.makeText(activity, "Falha ao criar o diretorio/pasta do arquivo!\n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            FileOutputStream fOut = new FileOutputStream(dir);
            fOut.flush();
            PdfWriter writer = PdfWriter.getInstance(document, fOut);


            HeaderFooterPdfPageEvent event = new HeaderFooterPdfPageEvent();
            writer.setPageEvent(event);




            document.open();
            Paragraph titulo = new Paragraph( periodo, calibrilt);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            montandoRelatorio(document, writer);
            document.close();
            return dir;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Falha ao criar o arquivo PDF, favor entrar em contato com o suporte técnico!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        } catch ( FileNotFoundException e) {
            showAlertInfoApp();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Falha ao criar o arquivo PDF, favor entrar em contato com o suporte técnico!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void showAlertInfoApp() {
        new AlertDialog.Builder(activity)
                .setTitle("RCK SISTEMAS ESPECIFICOS!")
                .setMessage("Aplicativo sem permissão para criação/armazenamento de arquivos!\n Gostaria de analisar/conceder permissão para de armazenamento para este aplicativo? ")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        activity.startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)), 400);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(activity, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    private boolean montandoRelatorio(Document document, PdfWriter writer) throws DocumentException {
        Paragraph titulo;

        document.add(new Paragraph("           "));
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100);
        PdfPCell c1 = new PdfPCell(new Phrase("Grupo/Marca", calibril));
        PdfPCell c2 = new PdfPCell(new Phrase("Valor total bruto", calibril));
        PdfPCell c3 = new PdfPCell(new Phrase("Valor total liquido", calibril));
        c1.setBorder(Rectangle.NO_BORDER);
        c2.setBorder(Rectangle.NO_BORDER);
        c3.setBorder(Rectangle.NO_BORDER);
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        PdfPCell cellSpace =new PdfPCell(new Phrase(""));
        cellSpace.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellSpace);
        table.addCell(cellSpace);
        table.addCell(cellSpace);
        for (int i = 0; i < 10; i++) {
            for (ParticipacaoFaturamento participacaoFaturamento : lista) {
                c1 = new PdfPCell(new Phrase(participacaoFaturamento.getNome(), calibril));
                if (participacaoFaturamento.getNome().trim().equalsIgnoreCase("SEM MARCA") || participacaoFaturamento.getNome().trim().equalsIgnoreCase("SEM GRUPO"))
                    c1 = new PdfPCell(new Phrase(participacaoFaturamento.getNome(), calibrilr));
                c2 = new PdfPCell(new Phrase(MascaraUtil.mascaraReal(participacaoFaturamento.getValor_bruto()), calibrib));
                c3 = new PdfPCell(new Phrase(MascaraUtil.mascaraReal(participacaoFaturamento.getValor_liquido()), calibrib));
                c1.setBorder(Rectangle.NO_BORDER);
                c2.setBorder(Rectangle.NO_BORDER);
                c3.setBorder(Rectangle.NO_BORDER);
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c2.setPaddingRight(20);
                table.addCell(c1);
                table.addCell(c2);
                table.addCell(c3);
                //cellSpace.setBackgroundColor(new BaseColor(243,243,242));
                //cellSpace.setBackgroundColor(new BaseColor(253,253,253));
                cellSpace.setBackgroundColor(new BaseColor(249, 249, 248));
                cellSpace.setFixedHeight(4.5f);
                table.addCell(cellSpace);
                table.addCell(cellSpace);
                table.addCell(cellSpace);
            }
        }
        table.setHeaderRows(1);
        document.add(table);


        return true;
    }


    private void addColumn(PdfWriter writer, Rectangle rect, boolean useAscender, int align, Boolean borda, Paragraph p) throws DocumentException {
        rect.setBorder(Rectangle.BOX);
        if (borda) {
            rect.setBorderWidth(0.5f);
            rect.setBorderColor(BaseColor.BLACK);
        }
        PdfContentByte cb = writer.getDirectContent();
        cb.rectangle(rect);
        ColumnText ct = new ColumnText(cb);
        ct.setSimpleColumn(rect);
        ct.setUseAscender(useAscender);
        ct.addText(p);
        ct.setAlignment(align);
        ct.go();
    }
}
