package com.example.rcksuporte05.rcksistemas.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CondicoesPagamentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.OperacaoEstoqueDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoSkuDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDFReciboUtil extends PdfPageEventHelper {
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
    private FinanceiroResumo financeiroResumo;
    private EmpresaParametro empresa;
    private Activity activity;
    private  DBHelper db;
    private String idAndroit;

    public PDFReciboUtil(FinanceiroResumo financeiroResumo, EmpresaParametro empresa, Activity activity, DBHelper db) {
        this.financeiroResumo = financeiroResumo;
        this.empresa = empresa;
        this.activity = activity;
        this.db = db;
    }

    public File criandoPdf() {
        idAndroit = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            String filename;
            filename = "recibo" + financeiroResumo.getCpf_cnpj() + ".pdf";
            Document document = new Document(PageSize.A4);
            document.setMargins(15, 15, 15, 15);
            String path ;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + activity.getString(R.string.app_name) + "/Recibos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Recibos";

            }else {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + activity.getString(R.string.app_name) + "/Recibos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Recibos";
            }
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
            document.open();
            if ( !montandoRelatorio(document, writer) ) {
                Toast.makeText(activity, "Falha ao montar o arquivo PDF, favor entrar em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
                return null;
            }
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
        //+ financeiroResumo.getCpf_cnpj()
        //titulo = new Paragraph("   DATA/HORA ___/___/______   ____:____         Assinatura:  _______________________________"  + "\n\n", operacaoFont);
        titulo = new Paragraph("RECIBO"  + "\n", titFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));

        //Rectangle rect = new Rectangle(23, 685, 63, 645);
        //addColumn(writer, rect, false, Element.ALIGN_LEFT, true, new Paragraph(new Chunk( " " + financeiroResumo.getNome_favorecido(), normalFont)));

        Paragraph p1 = new Paragraph("", cabFont);
        p1.setIndentationLeft(4);
        p1.setIndentationLeft(4);
        Chunk c1;
        if ( financeiroResumo.getTotal_docs() > 1) {
            c1 = new Chunk("  RECIBO REFERENTE AOS PEDIDOS DE COMPRA:(" + financeiroResumo.getDocs().toUpperCase() + ")" + "\n", subFont);
        } else {
            c1 = new Chunk("  RECIBO REFERENTE AO PEDIDO DE COMPRA:(" + financeiroResumo.getDocs().toUpperCase() + ")" + "\n", subFont);
        }
        p1.add(c1);

        Chunk c2 = new Chunk(  "  No dia " + MascaraUtil.formataData(db.pegaDataAtual()) + " a empresa " +  empresa.getRazao_social().toUpperCase() + " recebeu de: ", operacaoFont);
        p1.add(c2);
        Chunk c3 = new Chunk(financeiroResumo.getNome_favorecido().toUpperCase(), operacaoFont) ;
        p1.add(c3);
        Chunk c4 = new Chunk(  " a importancia de " + MascaraUtil.mascaraReal(financeiroResumo.getValor_recebimento())   , operacaoFont);
        p1.add(c4);
        Chunk c5;
        if ( financeiroResumo.getTotal_docs() > 1) {
            c5 = new Chunk(  " referente aos pedidos de compra(" + financeiroResumo.getDocs().toUpperCase() + ").  \n"   , operacaoFont);
        } else {
            c5 = new Chunk(  " referente ao pedido de compra(" + financeiroResumo.getDocs().toUpperCase() + ").  \n"   , operacaoFont);
        }
        p1.add(c5);
        try {
            if (Float.parseFloat(financeiroResumo.getValor_debito()) > 0.00) {
                Chunk c6 = new Chunk(" RESTANDO SALDO DEVEDOR DE  " + MascaraUtil.mascaraReal(financeiroResumo.getValor_debito()) + ". COM VENCIMENTO PARA O DIA " + MascaraUtil.formataData(financeiroResumo.getData_vencimento()) + "\n", subFont);
                p1.add(c6);
            } else {
                Chunk c6 = new Chunk("\n", operacaoFont);
                p1.add(c6);
            }
        } catch ( NullPointerException|NumberFormatException e) {
            Chunk c6 = new Chunk("\n", operacaoFont);
            p1.add(c6);
        }
        Chunk c7 = new Chunk(  "  ***AMIGO CLIENTE, GUARDE ESTE RECIBO, POIS ELE É O COMPROVANTE DE SEU PAGAMENTO***\n"   , operacaoFont);
        p1.add(c7);

        Chunk c8 = new Chunk(  "   " +financeiroResumo.getId_favorecido() + " | " +  MascaraUtil.formataData(financeiroResumo.getData_vencimento()) + " | " + UsuarioHelper.getUsuario().getId_quando_vendedor() + " | " + String.valueOf(idAndroit)  , smallFontPequena);
        p1.add(c8);

        Rectangle rect = new Rectangle(23, 800, 575, 690);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true,  p1);


        /*
        rect = new Rectangle(23, 145, 575, 23);
        try {
            addColumn(writer, rect, false, Element.ALIGN_TOP, true, new Paragraph(new Chunk( " " + financeiroResumo.getNome_favorecido(), normalFont)));
        } catch (NullPointerException e) {
            e.printStackTrace();
            addColumn(writer, rect, false, Element.ALIGN_TOP, true, new Paragraph(new Chunk("-", normalFont)));
        } catch ( Exception e) {
            e.printStackTrace();
            addColumn(writer, rect, false, Element.ALIGN_TOP, true, new Paragraph(new Chunk("-", normalFont)));
        }

         */
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
