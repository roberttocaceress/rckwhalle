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
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDFPedidoUtil2026 extends PdfPageEventHelper {
    
    // CORES PREMIUM 2026
    private static final BaseColor COLOR_PRIMARY = new BaseColor(49, 89, 95); // #31595F
    private static final BaseColor COLOR_LIGHT_GREY = new BaseColor(241, 245, 249);
    private static final BaseColor COLOR_TEXT_SOFT = new BaseColor(100, 116, 139);
    
    private static Font titFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, COLOR_PRIMARY);
    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, COLOR_PRIMARY);
    private static Font redFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.RED);
    private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    private static Font normalBold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static Font cabFont = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, COLOR_TEXT_SOFT);
    private static Font operacaoFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

    private WebPedido webPedido;
    private EmpresaParametro empresa;
    private int indAcao;
    private Activity activity;
    private WebPedidoItensDAO webPedidoItensDAO;
    private boolean exibirDebitoNoPdf = false;

    public PDFPedidoUtil2026(WebPedido webPedido, EmpresaParametro empresa, Activity activity, int indAcao) {
        this.webPedido = webPedido;
        this.empresa = empresa;
        this.activity = activity;
        this.indAcao = indAcao;
    }

    public void setExibirDebitoNoPdf(boolean exibirDebitoNoPdf) {
        this.exibirDebitoNoPdf = exibirDebitoNoPdf;
    }

    public File criandoPdf(String tipoPedido) {
        try {
            DBHelper db = new DBHelper(activity);
            String filename = tipoPedido + db.pegaDataHoraAtual().replaceAll("[^0-9]", "") + "_2026.pdf";
            
            Document document = new Document(PageSize.A4);
            document.setMargins(20, 20, 20, 20);
            
            String path;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
            } else {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
            }

            File dir = new File(path, filename);
            if (!dir.exists()) dir.getParentFile().mkdirs();

            FileOutputStream fOut = new FileOutputStream(dir);
            PdfWriter writer = PdfWriter.getInstance(document, fOut);
            writer.setPageEvent(this);
            
            document.open();
            if (!montandoRelatorio(document, writer)) return null;
            document.close();
            fOut.close();
            
            return dir;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        
        // Linha divisória discreta no rodapé
        cb.setLineWidth(0.5f);
        cb.setColorStroke(COLOR_TEXT_SOFT);
        cb.moveTo(20, 25);
        cb.lineTo(575, 25);
        cb.stroke();

        // Texto de desenvolvimento à esquerda
        Phrase footerDev = new Phrase("DESENVOLVIDO POR RCK SISTEMAS ESPECIFICOS", cabFont);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footerDev, 20, 15, 0);

        // Número da página à direita
        Phrase footerPage = new Phrase(String.format("Página %d", writer.getPageNumber()), cabFont);
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footerPage, 575, 15, 0);
    }

    private boolean montandoRelatorio(Document document, PdfWriter writer) throws DocumentException {
        try {
            // TÍTULO MODERNO COM LINHA INFERIOR
            String txtTitulo = (indAcao == 4) ? "ANÁLISE DE ESTOQUE - PEDIDO " : "PEDIDO ";
            String idPed = (webPedido.getId_web_pedido_servidor() != null) ? webPedido.getId_web_pedido_servidor() : webPedido.getId_web_pedido();
            
            Paragraph titulo = new Paragraph(txtTitulo + idPed, catFont);
            titulo.setAlignment(Element.ALIGN_LEFT);
            titulo.setSpacingAfter(10f);
            document.add(titulo);

            // LINHA DIVISORA COLORIDA
            PdfContentByte cb = writer.getDirectContent();
            cb.setLineWidth(2f);
            cb.setColorStroke(COLOR_PRIMARY);
            cb.moveTo(20, 795);
            cb.lineTo(575, 795);
            cb.stroke();

            // BLOCO DE DADOS DO CLIENTE (ESQUERDA)
            Paragraph pCli = new Paragraph();
            pCli.add(new Chunk("CLIENTE\n", cabFont));
            String nomeCli = (webPedido.getCadastro().getNome_cadastro().isEmpty()) ? "PRÉ-PEDIDO" : webPedido.getCadastro().getNome_cadastro().toUpperCase();
            pCli.add(new Chunk(nomeCli + "\n", normalBold));
            
            String end = (webPedido.getCadastro().getEndereco() != null) ? webPedido.getCadastro().getEndereco() : "-";
            pCli.add(new Chunk(end + ", " + webPedido.getCadastro().getEndereco_bairro() + "\n", smallFont));
            pCli.add(new Chunk(webPedido.getCadastro().getNome_municipio() + " - " + webPedido.getCadastro().getEndereco_uf(), smallFont));

            // BLOCO DE DOCUMENTOS (DIREITA)
            Paragraph pDoc = new Paragraph();
            pDoc.setAlignment(Element.ALIGN_RIGHT);
            pDoc.add(new Chunk("CNPJ/CPF: ", cabFont));
            String doc = "-";
            try {
                if (webPedido.getCadastro().getPessoa_f_j().equalsIgnoreCase("J")) doc = MascaraUtil.mascaraCNPJ(webPedido.getCadastro().getCpf_cnpj());
                else doc = MascaraUtil.mascaraCPF(webPedido.getCadastro().getCpf_cnpj());
            } catch (Exception ignored) {}
            pDoc.add(new Chunk(doc + "\n", smallFont));
            
            pDoc.add(new Chunk("IE: ", cabFont));
            pDoc.add(new Chunk((webPedido.getCadastro().getInscri_estadual() != null ? webPedido.getCadastro().getInscri_estadual() : "-") + "\n", smallFont));
            
            pDoc.add(new Chunk("CONTATO: ", cabFont));
            pDoc.add(new Chunk(validaTelefone(webPedido.getCadastro()), smallFont));

            // ADICIONA CABEÇALHO EM TABELA INVISÍVEL PARA MELHOR ALINHAMENTO
            PdfPTable tableHeader = new PdfPTable(2);
            tableHeader.setWidthPercentage(100);
            tableHeader.setSpacingBefore(15f);
            
            PdfPCell cellLeft = new PdfPCell(pCli);
            cellLeft.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cellLeft);
            
            PdfPCell cellRight = new PdfPCell(pDoc);
            cellRight.setBorder(Rectangle.NO_BORDER);
            cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableHeader.addCell(cellRight);
            
            document.add(tableHeader);

            // BLOCO DE DEBITO VENCIDO (CASO SOLICITADO)
            if (exibirDebitoNoPdf && webPedido.getCadastro() != null) {
                float vencido = 0.00f;
                try {
                    vencido = Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencido() == null ? "0.00" : webPedido.getCadastro().getFinanceiro_vencido());
                } catch (Exception ignored) {}

                if (vencido > 0) {
                    PdfPTable tableDebito = new PdfPTable(1);
                    tableDebito.setWidthPercentage(100);
                    tableDebito.setSpacingBefore(10f);

                    PdfPCell cellDebito = new PdfPCell();
                    cellDebito.setBackgroundColor(new BaseColor(255, 235, 238)); // Vermelho bem clarinho
                    cellDebito.setPadding(8f);
                    cellDebito.setBorderColor(new BaseColor(198, 40, 40)); // Vermelho forte (C62828)

                    Paragraph pDeb = new Paragraph();
                    pDeb.add(new Chunk("INFORMATIVO DE DÉBITO VENCIDO: ", smallBold));
                    pDeb.add(new Chunk(MascaraUtil.mascaraRealSimbolo(vencido), redFont));
                    pDeb.add(new Chunk("\nRessalva: Favor desconsiderar este valor caso o pagamento já tenha sido efetuado recentemente.", smallFont));
                    
                    cellDebito.addElement(pDeb);
                    tableDebito.addCell(cellDebito);
                    document.add(tableDebito);
                }
            }

            // BOX DE METADADOS (Vendedor, Emissão, Entrega)
            PdfPTable tableMeta = new PdfPTable(5);
            tableMeta.setWidthPercentage(100);
            tableMeta.setSpacingBefore(15f);
            tableMeta.setSpacingAfter(20f);
            
            addMetaCell(tableMeta, "REPRESENTANTE", (UsuarioHelper.getUsuario() != null ? UsuarioHelper.getUsuario().getNome_usuario() : "-"));
            addMetaCell(tableMeta, "TIPO DE OPERAÇÃO", (buscaOperacao() != null ? buscaOperacao().getNatureza_operacao() : "-"));
            addMetaCell(tableMeta, "PRAZO DE PAGAMENTO", (buscaCondPag() != null ? buscaCondPag().getNome_condicao() : "-"));
            addMetaCell(tableMeta, "DATA EMISSÃO", MascaraUtil.formataData(webPedido.getData_emissao()));
            addMetaCell(tableMeta, "PREVISÃO ENTREGA", MascaraUtil.formataData(webPedido.getData_prev_entrega()));
            
            document.add(tableMeta);

            // TABELA DE PRODUTOS
            PdfPTable tableProd;
            if (indAcao == 4) {
                tableProd = new PdfPTable(new float[]{40f, 140f, 30f, 56f, 40f, 40f, 40f});
            } else {
                tableProd = new PdfPTable(new float[]{40f, 140f, 30f, 56f, 40f, 40f, 40f});
            }
            tableProd.setWidthPercentage(100);
            
            // Header da Tabela
            addColHeader(tableProd, "CÓDIGO PRODUTO", Element.ALIGN_LEFT);
            addColHeader(tableProd, "DESCRIÇÃO DO PRODUTO", Element.ALIGN_LEFT);
            addColHeader(tableProd, "UNID.", Element.ALIGN_LEFT);
            addColHeader(tableProd, "CÓDIGO BARRAS", Element.ALIGN_LEFT);
            addColHeader(tableProd, "QUANTIDADE", Element.ALIGN_LEFT);
            
            if (indAcao == 4) {
                addColHeader(tableProd, "ESTOQUE", Element.ALIGN_LEFT);
                addColHeader(tableProd, "SALDO", Element.ALIGN_LEFT);
            } else {
                addColHeader(tableProd, "VALOR UNITÁRIO", Element.ALIGN_LEFT);
                addColHeader(tableProd, "VALOR TOTAL", Element.ALIGN_LEFT);
            }

            if (webPedido.getWebPedidoItens() == null || webPedido.getWebPedidoItens().isEmpty()) {
                webPedido.setWebPedidoItens(listaPedidoItens());
            }

            int count = 0;
            for (WebPedidoItens item : webPedido.getWebPedidoItens()) {
                BaseColor rowColor = (count % 2 == 0) ? BaseColor.WHITE : COLOR_LIGHT_GREY;
                Font rowFont = item.getProdutoAtivo() ? smallFont : redFont;

                addColCell(tableProd, item.getId_produto(), rowFont, rowColor, Element.ALIGN_LEFT);
                addColCell(tableProd, item.getNome_produto(), rowFont, rowColor, Element.ALIGN_LEFT);
                addColCell(tableProd, (item.getId_sku() > 0 ? item.getNome_sku() : item.getUnidade()), rowFont, rowColor, Element.ALIGN_LEFT);
                addColCell(tableProd, item.getCodigo_em_barras(), rowFont, rowColor, Element.ALIGN_LEFT);
                addColCell(tableProd, item.getQuantidade(), rowFont, rowColor, Element.ALIGN_RIGHT);

                if (indAcao == 4) {
                    int saldo = saldoEstoque(item.getId_produto());
                    int saldoFinal = saldo - Integer.parseInt(item.getQuantidade());
                    addColCell(tableProd, String.valueOf(saldo), rowFont, rowColor, Element.ALIGN_RIGHT);
                    addColCell(tableProd, String.valueOf(saldoFinal), (saldoFinal < 0 ? redFont : rowFont), rowColor, Element.ALIGN_RIGHT);
                } else {
                    addColCell(tableProd, MascaraUtil.duasCasaDecimal(item.getValor_unitario()), rowFont, rowColor, Element.ALIGN_RIGHT);
                    addColCell(tableProd, MascaraUtil.duasCasaDecimal(item.getValor_total()), rowFont, rowColor, Element.ALIGN_RIGHT);
                }
                count++;
            }

            document.add(tableProd);

            // TOTAIS E OBSERVAÇÕES
            if (indAcao != 4) {
                PdfPTable tableTotal = new PdfPTable(2);
                tableTotal.setWidthPercentage(100);
                tableTotal.setSpacingBefore(20f);
                tableTotal.setWidths(new float[]{3f, 1f});

                // Observações
                PdfPCell cellObs = new PdfPCell(new Phrase("OBSERVAÇÕES:\n" + webPedido.getObservacoes(), smallFont));
                cellObs.setBorder(Rectangle.NO_BORDER);
                cellObs.setPadding(10f);
                cellObs.setBackgroundColor(COLOR_LIGHT_GREY);
                tableTotal.addCell(cellObs);

                // Valores
                Paragraph pVal = new Paragraph();
                pVal.setAlignment(Element.ALIGN_RIGHT);
                pVal.add(new Chunk("PRODUTOS: " + MascaraUtil.mascaraReal(webPedido.getValor_produtos()) + "\n", smallFont));
                pVal.add(new Chunk("DESCONTOS: " + MascaraUtil.mascaraReal(webPedido.getValor_desconto()) + "\n", smallFont));
                pVal.add(new Chunk("TOTAL: " + MascaraUtil.mascaraReal(webPedido.getValor_total()), normalBold));

                PdfPCell cellVal = new PdfPCell(pVal);
                cellVal.setBorder(Rectangle.NO_BORDER);
                cellVal.setVerticalAlignment(Element.ALIGN_BOTTOM);
                tableTotal.addCell(cellVal);

                document.add(tableTotal);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addMetaCell(PdfPTable table, String label, String value) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(COLOR_LIGHT_GREY);
        cell.setPadding(6f);
        
        // Stacking label above value using Paragraph for consistency
        Paragraph p = new Paragraph();
        p.add(new Chunk(label + "\n", cabFont));
        p.add(new Chunk(value != null ? value : "-", operacaoFont));
        
        cell.addElement(p);
        table.addCell(cell);
    }

    private void addColHeader(PdfPTable table, String text, int align) {
        Font whiteBold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(text, whiteBold));
        cell.setBackgroundColor(COLOR_PRIMARY);
        cell.setPadding(5f);
        cell.setHorizontalAlignment(align);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private void addColCell(PdfPTable table, String text, Font font, BaseColor bg, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "-", font));
        cell.setBackgroundColor(bg);
        cell.setPadding(6f);
        if (align == Element.ALIGN_RIGHT) {
            cell.setPaddingRight(8f); // Pequeno espaço extra à direita para não colar na borda
        }
        cell.setHorizontalAlignment(align);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }

    private Integer saldoEstoque(String id_produto) {
        DBHelper db = new DBHelper(activity);
        return db.saldoPDF("SELECT SALDO_ESTOQUE FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + id_produto + "'");
    }

    private Operacao buscaOperacao() {
        DBHelper db = new DBHelper(activity);
        OperacaoEstoqueDAO operacaoEstoqueDAO = new OperacaoEstoqueDAO(db);
        try {
            return operacaoEstoqueDAO.getOperacao("SELECT * FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + webPedido.getId_operacao() + ";");
        } catch (Exception e) { return null; }
    }

    private CondicoesPagamento buscaCondPag() {
        DBHelper db = new DBHelper(activity);
        CondicoesPagamentoDAO condicoesPagamentoCabDAO = new CondicoesPagamentoDAO(db);
        try {
            return condicoesPagamentoCabDAO.getCondicao("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + webPedido.getId_condicao_pagamento() + ";");
        } catch (Exception e) { return null; }
    }

    private List<WebPedidoItens> listaPedidoItens() {
        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(new DBHelper(activity));
        return webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM");
    }

    private String validaTelefone(Cliente cliente) {
        if (cliente.getTelefone_principal() != null && !cliente.getTelefone_principal().isEmpty()) return cliente.getTelefone_principal();
        if (cliente.getTelefone_dois() != null && !cliente.getTelefone_dois().isEmpty()) return cliente.getTelefone_dois();
        return "-";
    }
}
