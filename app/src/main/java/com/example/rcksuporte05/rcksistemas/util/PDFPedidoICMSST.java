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

import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CondicoesPagamentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.OperacaoEstoqueDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoPendente;
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
import com.itextpdf.text.pdf.PdfPTableFooter;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDFPedidoICMSST extends PdfPageEventHelper {
    private static Font titFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 15,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
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

    private WebPedido webPedido;
    private EmpresaParametro empresa;
    private Activity activity;
    private boolean exibirDebitoNoPdf = false;

    //private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;

    public PDFPedidoICMSST(WebPedido webPedido, EmpresaParametro empresa, Activity activity) {
        this.webPedido = webPedido;
        this.empresa = empresa;
        this.activity = activity;
    }

    public void setExibirDebitoNoPdf(boolean exibirDebitoNoPdf) {
        this.exibirDebitoNoPdf = exibirDebitoNoPdf;
    }

    public File criandoPdf(String tipoPedido) {
        try {
            DBHelper db = new DBHelper(activity);
            String filename = tipoPedido + db.pegaDataHoraAtual().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "") + ".pdf";
            if (filename.isEmpty()) {
                try {
                    if (webPedido.getId_web_pedido_servidor() != null)
                        filename = "PIDS" + webPedido.getId_web_pedido_servidor() + ".pdf";
                    else
                        filename = "PIDP" + webPedido.getId_web_pedido() + ".pdf";
                } catch (NullPointerException e) {
                    filename = "PNE" + db.pegaDataHoraAtual().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "") + ".pdf";
                } catch ( CursorIndexOutOfBoundsException e) {
                    filename = "PCE" + db.pegaDataHoraAtual().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "") + ".pdf";
                } catch (Exception e) {
                    filename = "PEE" + db.pegaDataHoraAtual().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "") + ".pdf";
                }
            }
            Document document = new Document(PageSize.A4);
            document.setMargins(15, 15, 15, 15);
            String path;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Pedidos";

            } else {
                if (Environment.getExternalStorageState() != null)
                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
                else
                    path = Environment.getDataDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + activity.getString(R.string.app_name) + "/Pedidos";
            }
            File dir = new File(path, filename);
            if (!dir.exists())
                dir.getParentFile().mkdirs();

            FileOutputStream fOut = new FileOutputStream(dir);
            fOut.flush();
            PdfWriter writer = PdfWriter.getInstance(document, fOut);
            document.open();
            montandoRelatorio(document, writer);
            document.close();

            return dir;
        } catch ( FileNotFoundException e) {
            showAlertInfoApp();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAlertInfoApp() {
        new AlertDialog.Builder(activity)
                .setTitle("RCK SISTEMAS ESPECIFICOS!")
                .setMessage("Aplicativo sem permissão para criação/armazenamento de arquivos!!!\n Gostaria de analisar/conceder permissão para armazenamento de arquivos, neste aplicativo? ")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        activity.startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)), 100);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(activity, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    private void montandoRelatorio(Document document, PdfWriter writer) throws DocumentException {
        //Paragraph titulo;
        /*if (webPedido.getId_web_pedido_servidor() != null)
            titulo = new Paragraph("PEDIDO " + webPedido.getId_web_pedido_servidor() + "\n\n", catFont);
        else
            if ( webPedido.getCadastro().getId_cadastro() == 0) {
                titulo = new Paragraph("PRÉ-PEDIDO " + webPedido.getId_web_pedido() + "\n\n", catFont);
            } else {
                titulo = new Paragraph("PEDIDO " + webPedido.getId_web_pedido() + "\n\n", catFont);
            }
        titulo.setAlignment(Element.ALIGN_CENTER);

         */
        //Para pedido onde se coloca a razao social da empresa
        /*
        titulo = new Paragraph(empresa.getRazao_social() + "\n" + empresa.getNome_fantasia() + "\n\n", titFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph("           "));
        Paragraph p = new Paragraph();
        p.setFont(normalBold);
        p.setAlignment(Element.ALIGN_LEFT);

        //p.add("Pedido:       ");
        Chunk numPedido;

        if (webPedido.getId_web_pedido_servidor() != null) {
            numPedido = new Chunk(webPedido.getId_web_pedido_servidor() + "\n");
            p.add("Pedido:       ");
        } else if (webPedido.getCadastro().getId_cadastro() == 0) {
            numPedido = new Chunk( webPedido.getId_web_pedido() + "\n");
            p.add("Pré-Pedido:       ");
        } else {
            numPedido = new Chunk(webPedido.getId_web_pedido() +  "\n");
            p.add("Pedido:       ");
        }

        numPedido.setFont(normalFont);
        p.add(numPedido);
        document.add(new Paragraph("           "));
         */

        Paragraph titulo;
        try {
            if (webPedido.getId_web_pedido_servidor() != null)
                titulo = new Paragraph("PEDIDO " + webPedido.getId_web_pedido_servidor() + "\n\n", catFont);
            else if (webPedido.getCadastro().getId_cadastro() == 0) {
                titulo = new Paragraph("PRÉ-PEDIDO " + webPedido.getId_web_pedido() + "\n\n", catFont);
            } else {
                titulo = new Paragraph("PEDIDO " + webPedido.getId_web_pedido() + "\n\n", catFont);
            }
        } catch ( NullPointerException e) {
            titulo = new Paragraph("-" + "\n\n", catFont);
        } catch ( Exception e) {
            titulo = new Paragraph("-" + "\n\n", catFont);
        }
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

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
                cellDebito.setBackgroundColor(new BaseColor(255, 235, 238));
                cellDebito.setPadding(8f);
                cellDebito.setBorderColor(new BaseColor(198, 40, 40));

                Paragraph pDeb = new Paragraph();
                pDeb.add(new Chunk("INFORMATIVO DE DÉBITO VENCIDO: ", smallBold));
                pDeb.add(new Chunk(MascaraUtil.mascaraRealSimbolo(vencido), redFont));
                pDeb.add(new Chunk("\nRessalva: Favor desconsiderar este valor caso o pagamento já tenha sido efetuado recentemente.", smallFont));

                cellDebito.addElement(pDeb);
                tableDebito.addCell(cellDebito);
                document.add(tableDebito);
            }
        }

        Paragraph p = new Paragraph();
        p.setFont(normalBold);
        p.setAlignment(Element.ALIGN_LEFT);

        p.add("Cliente:       ");
        Chunk cliente;
        if ( webPedido.getCadastro().getNome_cadastro().isEmpty())
            cliente = new Chunk("PRÉ-PEDIDO" + "\n");
        else
            cliente = new Chunk(webPedido.getCadastro().getNome_cadastro().toUpperCase() + "\n");
        cliente.setFont(normalFont);
        p.add(cliente);


        p.add("Endereço:   ");
        Chunk endereço = new Chunk("-" + "\n");
        try {
            if ( !webPedido.getCadastro().getEndereco().isEmpty() && !webPedido.getCadastro().getEndereco().equals(null))
                endereço = new Chunk(webPedido.getCadastro().getEndereco() + "\n");

        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        endereço.setFont(normalFont);
        p.add(endereço);
        p.add("Bairro:        ");
        Chunk bairro = new Chunk("-" + "\n");
        try {
            if (!webPedido.getCadastro().getEndereco_bairro().isEmpty() && !webPedido.getCadastro().getEndereco_bairro().equals(null))
                bairro = new Chunk(webPedido.getCadastro().getEndereco_bairro() + "\n");
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        bairro.setFont(normalFont);
        p.add(bairro);
        p.add("Munícipio: ");
        Chunk municipio = new Chunk("-" + "\n");
        try {
            if (!webPedido.getCadastro().getNome_municipio().isEmpty() && !webPedido.getCadastro().getNome_municipio().equals(null))
                municipio = new Chunk(webPedido.getCadastro().getNome_municipio() + "\n");
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        municipio.setFont(normalFont);

        p.add(municipio);
        p.add("Complemento: ");
        Chunk complemento = new Chunk( "-" + "\n");
        if (webPedido.getCadastro().getEndereco_complemento() != null && !webPedido.getCadastro().getEndereco_complemento().trim().isEmpty())
            complemento = new Chunk(webPedido.getCadastro().getEndereco_complemento() + "\n");
        p.add(complemento);
        /*
        p.add("Condição de Pagamento: ");
        Chunk condPag = new Chunk(buscaCondPag().getNome_condicao());
        p.add(condPag);
        */
        Paragraph p2 = new Paragraph();
        p2.setFont(normalBold);
        p2.setAlignment(Element.ALIGN_RIGHT);

        /*p2.add("Cond. de Pagamento: ");
        Chunk condPag = new Chunk(buscaCondPag().getNome_condicao() +"\n");
        condPag.setFont(normalFont);
        p2.add(condPag);
         */
        p2.add(""+"\n");
        p2.add("CNPJ/CPF: ");
        Chunk cpf = new Chunk("-" + "\n");
        try {
            switch (webPedido.getCadastro().getPessoa_f_j()) {
                case "J":
                    cpf = new Chunk(MascaraUtil.mascaraCNPJ(webPedido.getCadastro().getCpf_cnpj()) + "\n");
                    break;
                case "F":
                    cpf = new Chunk(MascaraUtil.mascaraCPF(webPedido.getCadastro().getCpf_cnpj()) + "\n");
                    break;
                case "":
                    cpf = new Chunk("-" + "\n");
                    break;
                default:
                    cpf = new Chunk(webPedido.getCadastro().getCpf_cnpj() + "\n");
                    break;
            }
        } catch ( NullPointerException e) {

        }catch ( Exception e) {

        }
        cpf.setFont(normalFont);
        p2.add(cpf);

        p2.add("IE: ");
        Chunk IE = new Chunk("-" + "\n");
        if (webPedido.getCadastro().getInscri_estadual() != null)
            IE = new Chunk(webPedido.getCadastro().getInscri_estadual() + "\n");
        IE.setFont(normalFont);
        p2.add(IE);
        p2.add("CEP: ");
        Chunk cep = new Chunk("-" + "\n");
        try {
            if (!webPedido.getCadastro().getEndereco_cep().isEmpty() && !webPedido.getCadastro().getEndereco_cep().equals(null))
                cep = new Chunk(MascaraUtil.mascaraCep(webPedido.getCadastro().getEndereco_cep()) + "\n");
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        cep.setFont(normalFont);
        p2.add(cep);
        p2.add("Telefone: ");
        Chunk telefone = new Chunk("-" + "\n");
        try {
            telefone = new Chunk(validaTelefone(webPedido.getCadastro()) + "\n");
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        telefone.setFont(normalFont);
        p2.add(telefone);
        p2.add("Categoria: ");
        Chunk categoria = new Chunk("");;
        try {
            if ( webPedido.getCadastro().getIdCategoria() >= 1)
                categoria = new Chunk(buscaCategoria(webPedido.getCadastro().getIdCategoria()).getNomeCategoria() + "\n");
            else
                categoria = new Chunk("");
        } catch (CursorIndexOutOfBoundsException|NullPointerException|NumberFormatException e) {
        } catch ( Exception e) {
        }
        p2.add(categoria);

        Rectangle rect = new Rectangle(23, 800, 400, 690);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, false, p);
        rect = new Rectangle(400, 800, 575, 690);
        addColumn(writer, rect, false, Element.ALIGN_RIGHT, false, p2);


        Paragraph condicao = new Paragraph("    Condição de pagamento \n                ", cabFont);
        Chunk nomeCondicao = new Chunk(buscaCondPag().getNome_condicao().trim()   , operacaoFont);
        condicao.add(nomeCondicao);

        rect = new Rectangle(23, 685, 163, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, condicao);


        Paragraph operacao = new Paragraph("    Operação \n                ", cabFont);
        Chunk nomeOperacao = new Chunk(buscaOperacao().getNatureza_operacao().trim()  , operacaoFont);
        operacao.add(nomeOperacao);

        rect = new Rectangle(163, 685, 303, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, operacao);

        Paragraph dadosVendedor = new Paragraph("   Vendedor \n                ", cabFont);
        Chunk infoVendedo = new Chunk(UsuarioHelper.getUsuario().getNome_usuario().trim()  , operacaoFont);

        dadosVendedor.add(infoVendedo);

        rect = new Rectangle(303, 685, 423, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, dadosVendedor);

        Paragraph dataEmissao = new Paragraph("   Emissão \n                ", cabFont);
        Chunk infoDataEmissao = null;
        try {
            infoDataEmissao = new Chunk(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_emissao())), operacaoFont);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dataEmissao.add(infoDataEmissao);

        rect = new Rectangle(423, 685, 498, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, dataEmissao);

        Paragraph dataEntrega = new Paragraph("   Entrega \n                ", cabFont);
        Chunk infoDataEntrega = null;
        try {
            infoDataEntrega = new Chunk(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega())), operacaoFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataEntrega.add(infoDataEntrega);

        rect = new Rectangle(498, 685, 573, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, dataEntrega);

        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        /*
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));//RRC 16-01-2020
        */

        Paragraph produtos = new Paragraph("   Dados dos produtos", smallBold);
        document.add(produtos);
        document.add(new Paragraph("           "));

        PdfPTable tabelaProdutos = new PdfPTable(new float[]{40f, 120f, 30f, 51f, 26f, 31f, 30f, 36f, 37f, 37f});
        tabelaProdutos.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.setTotalWidth(550);
        tabelaProdutos.setLockedWidth(true);

        PdfPCell c1 = new PdfPCell(new Phrase("Código\nProduto", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Descrição do Produto", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Unid.", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Código\nBarras", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Qtd.", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Valor\nUnitário", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Preço\nPago", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Desconto\nTotal", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("ICMS ST\nTotal", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Valor\nTotal", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        if (webPedido.getWebPedidoItens() == null || webPedido.getWebPedidoItens().size() <= 0)
            webPedido.setWebPedidoItens(listaPedidoItens());

        /*for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
            PdfPCell celulaProd = null;
            try {
                celulaProd = new PdfPCell(new Phrase(webPedidoItens.getId_produto(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                tabelaProdutos.addCell(new Phrase(webPedidoItens.getNome_produto(), smallFont));
            } catch (Exception e) {
                tabelaProdutos.addCell(new Phrase(""));
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(webPedidoItens.getUnidade(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("")));
                e.printStackTrace();
            }

            try {
                celulaProd = new PdfPCell(new Phrase(webPedidoItens.getCodigo_em_barras(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NullPointerException e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("")));
                e.printStackTrace();
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("")));
                e.printStackTrace();
            }

            try {
                celulaProd = new PdfPCell(new Phrase(webPedidoItens.getQuantidade(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("")));
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_unitario()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                        (webPedidoItens.getValor_unitario() + Float.parseFloat(webPedidoItens.getIcms_st_valor())) - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFont));
                //lista.get(position).getValor_unitario() - (Float.parseFloat(lista.get(position).getValor_desconto_real()) / Float.parseFloat(lista.get(position).getQuantidade()));

                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch ( NumberFormatException|NullPointerException e) {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                        webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFont));
                //lista.get(position).getValor_unitario() - (Float.parseFloat(lista.get(position).getValor_desconto_real()) / Float.parseFloat(lista.get(position).getQuantidade()));

                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);

            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_desconto_real()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }

            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getIcms_st_valor()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NumberFormatException|NullPointerException e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_total()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
        }*/
        for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
            PdfPCell celulaProd = null;
            try {
                if ( !webPedidoItens.getProdutoAtivo())
                    celulaProd = new PdfPCell(new Phrase(webPedidoItens.getId_produto(), smallFontRed));
                else
                    celulaProd = new PdfPCell(new Phrase(webPedidoItens.getId_produto(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                if ( !webPedidoItens.getProdutoAtivo())
                    tabelaProdutos.addCell(new Phrase(webPedidoItens.getNome_produto(), smallFontRed));
                else
                    tabelaProdutos.addCell(new Phrase(webPedidoItens.getNome_produto(), smallFont));
            } catch (Exception e) {
                tabelaProdutos.addCell(new Phrase("-", smallFont));
                e.printStackTrace();
            }
            try {
                if (!webPedidoItens.getProdutoAtivo())
                    celulaProd = new PdfPCell(new Phrase("*****", smallFontRed));
                else {
                    try {
                        if (webPedidoItens.getId_sku() > 0) {
                            celulaProd = new PdfPCell(new Phrase(webPedidoItens.getNome_sku(), smallFontPequena));
                        } else {
                            celulaProd = new PdfPCell(new Phrase(webPedidoItens.getUnidade(), smallFont));
                        }
                    } catch (NullPointerException | NumberFormatException e) {
                        celulaProd = new PdfPCell(new Phrase(webPedidoItens.getUnidade(), smallFont));
                    }
                }
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch ( NullPointerException e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("-", smallFont)));
                e.printStackTrace();
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("-", smallFont)));
                e.printStackTrace();
            }

            try {
                if ( !webPedidoItens.getProdutoAtivo())
                    celulaProd = new PdfPCell(new Phrase("*************", smallFontRed));
                else
                    celulaProd = new PdfPCell(new Phrase(webPedidoItens.getCodigo_em_barras(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NullPointerException e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("-", smallFont)));
                e.printStackTrace();
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("-", smallFont)));
                e.printStackTrace();
            }

            try {
                if ( !webPedidoItens.getProdutoAtivo())
                    celulaProd = new PdfPCell(new Phrase(webPedidoItens.getQuantidade(), smallFontRed));
                else
                    celulaProd = new PdfPCell(new Phrase(webPedidoItens.getQuantidade(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NullPointerException e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("-", smallFont)));
                e.printStackTrace();
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("-", smallFont)));
                e.printStackTrace();
            }
            try {
                if ( !webPedidoItens.getProdutoAtivo())
                    celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_unitario()), smallFontRed));
                else
                    celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_unitario()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NullPointerException|NumberFormatException e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                if ( !webPedidoItens.getProdutoAtivo()) {
                    try {
                        if ( Float.parseFloat(webPedidoItens.getValor_desconto_adic()) > 0.00f ) {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                    webPedidoItens.getValor_unitario() -  (
                                            (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade())) +
                                                    (Float.parseFloat(webPedidoItens.getValor_desconto_adic()) / Float.parseFloat(webPedidoItens.getQuantidade()))
                                    )
                            ), smallFontRed));
                        } else {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                    webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFontRed));
                        }

                    } catch (NumberFormatException | NullPointerException e) {
                        celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFontRed));
                    } catch (Exception e) {
                        celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFontRed));
                    }

                }else {
                    try {
                        if ( Float.parseFloat(webPedidoItens.getValor_desconto_adic()) > 0.00f ) {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                    webPedidoItens.getValor_unitario() - (
                                            (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade())) +
                                                    (Float.parseFloat(webPedidoItens.getValor_desconto_adic()) / Float.parseFloat(webPedidoItens.getQuantidade()))
                                    )
                            ), smallFont));
                        } else {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                    webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFont));
                        }
                    } catch (NumberFormatException|NullPointerException e) {
                        celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFont));
                    } catch ( Exception e) {
                        celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFont));
                    }
                    //lista.get(position).getValor_unitario() - (Float.parseFloat(lista.get(position).getValor_desconto_real()) / Float.parseFloat(lista.get(position).getQuantidade()));
                }
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NullPointerException|NumberFormatException e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                if ( !webPedidoItens.getProdutoAtivo()) {
                    try {
                        if (Float.parseFloat(webPedidoItens.getValor_desconto_adic()) > 0.00f) {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal( Float.parseFloat(webPedidoItens.getValor_desconto_real()) + Float.parseFloat(webPedidoItens.getValor_desconto_adic())), smallFontRed));
                        } else {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_desconto_real()), smallFontRed));
                        }
                    } catch ( NullPointerException|NumberFormatException e) {
                        celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_desconto_real()), smallFontRed));
                    } catch ( Exception e) {
                        celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_desconto_real()), smallFontRed));
                    }

                } else {
                    try {
                        if (Float.parseFloat(webPedidoItens.getValor_desconto_adic()) > 0.00f) {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(
                                    Float.parseFloat(webPedidoItens.getValor_desconto_real()) + Float.parseFloat( webPedidoItens.getValor_desconto_adic())
                            ), smallFont));
                        } else {
                            celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_desconto_real()), smallFont));
                        }
                    } catch ( NullPointerException|NumberFormatException e) {

                    } catch ( Exception e) {

                    }
                }
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NullPointerException|NumberFormatException e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getIcms_st_valor()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NumberFormatException|NullPointerException e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                if ( !webPedidoItens.getProdutoAtivo())
                    celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_total()), smallFontRed));
                else
                    celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_total()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (NullPointerException|NumberFormatException e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
        }

        tabelaProdutos.setHeaderRows(1);
        document.add(tabelaProdutos);

        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));//RRC 16-01-2020

        Paragraph totalProdutos = new Paragraph("    Valor total dos Produtos \n                                             ", cabFont);
        try {
            Chunk produto = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_produtos()), normalFont);
            totalProdutos.add(produto);
            rect = new Rectangle(425, 145, 575, 110);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalProdutos);
        } catch (Exception e) {
            Chunk produto = new Chunk("R$0,00", normalFont);
            totalProdutos.add(produto);
            rect = new Rectangle(425, 145, 575, 110);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalProdutos);
            e.printStackTrace();
        }


        /*Paragraph totalDescontos = new Paragraph("    Valor total dos Descontos \n                                             ", cabFont);
        try {
            Chunk desconto = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_desconto()), normalFont);
            totalDescontos.add(desconto);
            rect = new Rectangle(425, 110, 575, 75);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
        } catch (Exception e) {
            Chunk desconto = new Chunk("R$0,00", normalFont);
            totalDescontos.add(desconto);
            rect = new Rectangle(425, 110, 575, 75);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
            e.printStackTrace();
        }
*/

        Paragraph totalICMS = new Paragraph("    Valor total do ICMS ST \n                                             ", cabFont);
        try {
            Chunk icms = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_icms_st()), normalFont);
            totalICMS.add(icms);
            rect = new Rectangle(425, 75, 575, 40);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalICMS);
        } catch (NumberFormatException|NullPointerException e) {
            Chunk pedido = new Chunk("R$0,00", normalFont);
            totalICMS.add(pedido);
            rect = new Rectangle(425, 75, 575, 40);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalICMS);
            e.printStackTrace();

        } catch (Exception e) {
            Chunk pedido = new Chunk("R$0,00", normalFont);
            totalICMS.add(pedido);
            rect = new Rectangle(425, 75, 575, 40);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalICMS);
            e.printStackTrace();
        }

        Paragraph totalDescontos = new Paragraph("    Valor total dos Descontos \n                                             ", cabFont);
        try {
            try {
                if ( Float.parseFloat(webPedido.getValor_desconto_adic()) > 0.00f) {
                    Chunk desconto = new Chunk(MascaraUtil.mascaraReal(
                            Float.parseFloat(webPedido.getValor_desconto()) + Float.parseFloat( webPedido.getValor_desconto_adic())
                    ), normalFont);
                    totalDescontos.add(desconto);
                    rect = new Rectangle(425, 110, 575, 75);
                    addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);

                } else {
                    Chunk desconto = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_desconto()), normalFont);
                    totalDescontos.add(desconto);
                    rect = new Rectangle(425, 110, 575, 75);
                    addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);

                }
            } catch ( NullPointerException|NumberFormatException e) {
                Chunk desconto = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_desconto()), normalFont);
                totalDescontos.add(desconto);
                rect = new Rectangle(425, 110, 575, 75);
                addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
            } catch ( Exception e) {
                Chunk desconto = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_desconto()), normalFont);
                totalDescontos.add(desconto);
                rect = new Rectangle(425, 110, 575, 75);
                addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
            }

        } catch (NullPointerException|NumberFormatException e) {
            Chunk desconto = new Chunk("R$0,00", normalFont);
            totalDescontos.add(desconto);
            rect = new Rectangle(425, 110, 575, 75);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
            e.printStackTrace();
        } catch (Exception e) {
            Chunk desconto = new Chunk("R$0,00", normalFont);
            totalDescontos.add(desconto);
            rect = new Rectangle(425, 110, 575, 75);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
            e.printStackTrace();
        }



        Paragraph totalPedido = new Paragraph("    Valor total do Pedido \n                                             ", cabFont);
        try {
            Chunk pedido = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_total()), normalFont);
            totalPedido.add(pedido);
            rect = new Rectangle(425, 40, 575, 5);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalPedido);
        } catch (Exception e) {
            Chunk pedido = new Chunk("R$0,00", normalFont);
            totalPedido.add(pedido);
            rect = new Rectangle(425, 40, 575, 5);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalPedido);
            e.printStackTrace();
        }

        rect = new Rectangle(23, 145, 425, 5);
        try {
            addColumn(writer, rect, false, Element.ALIGN_BOTTOM, true, new Paragraph(new Chunk( " " + webPedido.getObservacoes(), normalFont)));
        } catch (NullPointerException e) {
            e.printStackTrace();
            addColumn(writer, rect, false, Element.ALIGN_BOTTOM, true, new Paragraph(new Chunk("", normalFont)));
        } catch ( Exception e) {
            e.printStackTrace();
            addColumn(writer, rect, false, Element.ALIGN_BOTTOM, true, new Paragraph(new Chunk("", normalFont)));
        }
    }

    private Operacao buscaOperacao() {
        DBHelper db = new DBHelper(activity);
        OperacaoEstoqueDAO operacaoEstoqueDAO = new OperacaoEstoqueDAO(db);

        return operacaoEstoqueDAO.getLista("SELECT * FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + webPedido.getId_operacao() + ";").get(0);
    }

    private Categoria buscaCategoria(int idCategoria) {
        DBHelper db = new DBHelper(activity);
        CategoriaDAO categoriaDAO = new CategoriaDAO(db);
        return categoriaDAO.getCategoria(idCategoria);
    }

    private CondicoesPagamento buscaCondPag() {
        DBHelper db = new DBHelper(activity);
        CondicoesPagamentoDAO condicoesPagamentoCabDAO = new CondicoesPagamentoDAO(db);
        return condicoesPagamentoCabDAO.getLista("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + webPedido.getId_condicao_pagamento() + ";").get(0);
    }

    private List<WebPedidoItens> listaPedidoItens() {
        webPedidoItensDAO = new WebPedidoItensDAO( new DBHelper(activity));
        return webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM");
    }

    private String validaTelefone(Cliente cliente) {
        if (cliente.getTelefone_principal() != null && !cliente.getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11) {
            return (formataTelefone(cliente.getTelefone_principal()));
        } else if (cliente.getTelefone_dois() != null && !cliente.getTelefone_dois().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() <= 11) {
            return (formataTelefone(cliente.getTelefone_dois()));
        } else if (cliente.getTelefone_tres() != null && !cliente.getTelefone_tres().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() <= 11) {
            return (formataTelefone(cliente.getTelefone_tres()));
        } else {
            return ("(  )     -    ");
        }
    }

    private String formataTelefone(String telefone) {
        telefone = telefone.trim().replaceAll("[^0-9]", "");
        switch (telefone.length()) {
            case 10:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
            case 11:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
            case 9:
                return telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
            case 8:
                return telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
            default:
                return telefone;
        }
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
