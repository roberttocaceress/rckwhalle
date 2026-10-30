package com.example.rcksuporte05.rcksistemas.Helper;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.graphics.Color;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProdutoPedido;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido0;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.PedidoAnexo;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.BiFunction;

public class PedidoHelper {

    public static boolean mudouItens = true; // NOVO: Flag de sujeira para evitar cálculos redundantes
    private static int cacheExtraFonte = -1; // NOVO: Cache para o tamanho da fonte

    private static Float valorVenda;
    private static ActivityPedidoMain activityPedidoMain;
    private static ActivityProdutoPedido produtoPedidoActivity;
    private static Pedido0 pedido0;
    private static Pedido1 pedido1;
    private static Pedido2 pedido2;

    private static WebPedido webPedido;
    private static WebPedidoItens webPedidoItem;
    private static List<WebPedidoItens> listaWebPedidoItens;
    private static List<Produto> listaProdutos;
    private static List<PedidoAnexo> listaPedidoAnexo = new ArrayList<>();
    private static Produto produto;
    private static int idPedido;
    private static String buscaProduto;
    private static CondicoesPagamento condicoesPagamento;
    private Float valorProdutos = 0.0f;
    private Float descontoReal = 0.0f;
    private Float mediaDesconto = 0.0f;
    public TextView edtTotalVenda;
    public TextView edtTotalDescontos;
    public TextView edtTotalProdutos;

    public TextView totalVenda;
    public TextView totalDescontos;
    public TextView totalProdutos;

    private ViewPager mViewPager;
    private DBHelper db;
    private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;
    private ConfiguracaoDAO configuracaoDAO;

    public PedidoHelper() {
    }

    public PedidoHelper(ActivityPedidoMain activityPedidoMain) {
        this.activityPedidoMain = activityPedidoMain;
        edtTotalVenda = activityPedidoMain.findViewById(R.id.edtTotalVenda);
        edtTotalDescontos = activityPedidoMain.findViewById(R.id.edtTotalDescontos);
        edtTotalProdutos = activityPedidoMain.findViewById(R.id.edtTotalProdutos);
        db = new DBHelper(activityPedidoMain);
        //System.gc();
    }

    public PedidoHelper(ActivityProdutoPedido produtoPedidoActivity) {
        this.produtoPedidoActivity = produtoPedidoActivity;
        //System.gc();
    }

    public PedidoHelper(Pedido0 pedido0) {
        this.pedido0 = pedido0;
        //activityPedidoMain.findViewById(R.id.edtTotalVenda).setVisibility(View.VISIBLE);
        //System.gc();
    }
    public PedidoHelper(Pedido1 pedido1) {
        this.pedido1 = pedido1;
        //activityPedidoMain.findViewById(R.id.edtTotalVenda).setVisibility(View.VISIBLE);
        //System.gc();
    }

    public PedidoHelper(Pedido2 pedido2) {
        this.pedido2 = pedido2;
        //activityPedidoMain.findViewById(R.id.edtTotalVenda).setVisibility(View.INVISIBLE);
        db = new DBHelper(activityPedidoMain);
        //System.gc();
    }

    public static ActivityPedidoMain getActivityPedidoMain() {
        //System.gc();
        return activityPedidoMain;
    }

    public static ActivityProdutoPedido getProdutoPedidoActivity() {
        //System.gc();
        return produtoPedidoActivity;
    }

    public static boolean isPedido1Valido() {
        return pedido1 != null && pedido1.isAdded();
    }

    public static void setMudouItens(boolean mudouItens) {
        PedidoHelper.mudouItens = mudouItens;
    }

    public static List<WebPedidoItens> getListaWebPedidoItens() {
        return listaWebPedidoItens;
    }

    public static void setListaWebPedidoItens(List<WebPedidoItens> listaWebPedidoItens) {
        PedidoHelper.listaWebPedidoItens = listaWebPedidoItens;
        mudouItens = true; // NOVO: Marca mudança para recalcular totais
    }

    public List<WebPedidoItens> getListaWebPedidoItensSalvo() {
        return PedidoHelper.listaWebPedidoItens;
    }


    public static List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public static void setListaProdutos(List<Produto> listaProdutos) {
        PedidoHelper.listaProdutos = listaProdutos;
    }

    public static WebPedido getWebPedido() {
        return webPedido;
    }

    public static void setWebPedido(WebPedido webPedido) {
        PedidoHelper.webPedido = webPedido;
    }

    public static int getIdPedido() {
        return idPedido;
    }

    public static void setIdPedido(int idPedido) {
        PedidoHelper.idPedido = idPedido;
    }

    public static WebPedidoItens getWebPedidoItem() {
        return webPedidoItem;
    }

    public static void setWebPedidoItem(WebPedidoItens webPedidoItem) {
        PedidoHelper.webPedidoItem = webPedidoItem;
        //System.gc();
    }

    public static Produto getProduto() {
        return produto;
    }

    public static void setProduto(Produto produto) {
        PedidoHelper.produto = produto;
    }

    public static String getBuscaProduto() {
        return buscaProduto;
    }

    public static void setBuscaProduto(String buscaProduto) {
        PedidoHelper.buscaProduto = buscaProduto;
    }

    public static CondicoesPagamento getCondicoesPagamento() {
        return condicoesPagamento;
    }

    public static void setCondicoesPagamento(CondicoesPagamento condicoesPagamento) {
        PedidoHelper.condicoesPagamento = condicoesPagamento;
    }

    public static List<PedidoAnexo> getListaPedidoAnexo() {
        return listaPedidoAnexo;
    }

    public static void setListaPedidoAnexo(List<PedidoAnexo> listaPedidoAnexo) {
        PedidoHelper.listaPedidoAnexo = listaPedidoAnexo;
    }

    public static void pintaTxtNomeCliente() {
        TextView txtNomeCliente = (TextView) activityPedidoMain.findViewById(R.id.txtNomeCliente);
        txtNomeCliente.setTextColor(Color.RED);
    }

    public static EditText editTextDataEntrega() {
        return activityPedidoMain.findViewById(R.id.edtDataEntrega);
    }

    public static void calculaValorPedido(List<WebPedidoItens> produtoPedido, ActivityPedidoMain activityPedidoMain) {

        /* AJUSTE DE PERFORMANCE E PRECISÃO:
           Só usamos o cache do banco se a lista for estritamente NULA (estado de pré-carregamento).
           Se a lista existir (mesmo vazia), forçamos o cálculo real para garantir que, 
           ao excluir todos os itens, os totais voltem para ZERO imediatamente. */
        
        if (produtoPedido == null && !mudouItens && PedidoHelper.getWebPedido() != null && activityPedidoMain != null) {
            try {
                WebPedido p = PedidoHelper.getWebPedido();
                float vVenda = Float.parseFloat(p.getValor_total());
                float vProd = Float.parseFloat(p.getValor_produtos());
                float vDesc = Float.parseFloat(p.getValor_desconto());
                float vDescAdic = Float.parseFloat(p.getValor_desconto_adic());
                float vIcms = Float.parseFloat(p.getValor_icms_st());

                atualizaUiCamposPedido(activityPedidoMain, vVenda, vProd, vDesc, vDescAdic, vIcms);
                return;
            } catch (Exception e) {
                // Em caso de erro, segue para o cálculo real
            }
        }

        Float valorVenda = 0.f;
        Float valorDesconto = 0.f;
        Float valorDescontoVerba = 0.f;
        Float valorProduto = 0.f;
        Float valorIcmsST = 0.f;
        BigDecimal bvalorVenda = null;
        BigDecimal bvalorDesconto = null;
        BigDecimal bvalorDescontoVerba = null;
        BigDecimal bvalorProduto = null;
        BigDecimal bvalorIcmsST = null;
        if ( produtoPedido != null) {
            for (int i = 0; produtoPedido.size() > i; i++) {
                try {
                    bvalorVenda = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_total())).setScale(2, RoundingMode.HALF_EVEN);
                    valorVenda += Float.parseFloat(String.valueOf(bvalorVenda));  //Float.valueOf(produtoPedido.get(i).getValor_total());

                    bvalorDesconto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_desconto_real())).setScale(2, RoundingMode.HALF_EVEN);
                    valorDesconto += Float.parseFloat(String.valueOf(bvalorDesconto));//Float.valueOf(produtoPedido.get(i).getValor_desconto_real());

                    try {
                        bvalorDescontoVerba = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_desconto_adic())).setScale(2, RoundingMode.HALF_EVEN);
                        valorDescontoVerba += Float.parseFloat(String.valueOf(bvalorDescontoVerba));//Float.valueOf(produtoPedido.get(i).getValor_desconto_real());
                    } catch (NumberFormatException | NullPointerException e) {
                    } catch (Exception e) {
                    }

                    try {
                        bvalorIcmsST = new BigDecimal(Float.valueOf(produtoPedido.get(i).getIcms_st_valor())).setScale(2, RoundingMode.HALF_EVEN);
                        valorIcmsST += Float.parseFloat(String.valueOf(bvalorIcmsST));//Float.valueOf(produtoPedido.get(i).getValor_desconto_real());
                    } catch (NumberFormatException | NullPointerException e) {
                    } catch (Exception e) {
                    }

                    //bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getVenda_preco()) * Float.valueOf(produtoPedido.get(i).getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);
                    try {
                        bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_unitario()) * Float.valueOf(produtoPedido.get(i).getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);
                        if (Float.valueOf(produtoPedido.get(i).getValor_unitario()) <= 0.00f && produtoPedido.get(i).getProduto_preco_editado() > 0.00f) {
                            bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getProduto_preco_editado()) * Float.valueOf(produtoPedido.get(i).getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);
                            //Inclui este if para ver se calcula corretamente
                        } else if ((Float.valueOf(produtoPedido.get(i).getValor_unitario()) > 0.00f && produtoPedido.get(i).getProduto_preco_editado() > 0.00f) && produtoPedido.get(i).getProduto_preco_editado() != Float.valueOf(produtoPedido.get(i).getValor_unitario())) {
                            bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getProduto_preco_editado()) * Float.valueOf(produtoPedido.get(i).getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);
                            //bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_unitario()) * Float.valueOf(produtoPedido.get(i).getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);
                        }
                    } catch (NullPointerException ignored) {
                    }
                    //Ajustado aqui
                    if ( produtoPedido.get(i).getProdutoAtivo() == false &&  Float.valueOf(produtoPedido.get(i).getValor_unitario()) <= 0.00f) {
                        bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_total())).setScale(2, RoundingMode.HALF_EVEN);
                    }
                    valorProduto += Float.parseFloat(String.valueOf(bvalorProduto));//Float.valueOf(produtoPedido.get(i).getVenda_preco()) * Float.valueOf(produtoPedido.get(i).getQuantidade());
                } catch (NullPointerException | NumberFormatException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setValorVenda(valorVenda);
        listaWebPedidoItens = produtoPedido;

        /* ANTERIORMENTE O CÁLCULO E ATUALIZAÇÃO DE UI ERAM MISTURADOS. AGORA FOI ISOLADO EM UM MÉTODO AUXILIAR */
        atualizaUiCamposPedido(activityPedidoMain, valorVenda, valorProduto, valorDesconto, valorDescontoVerba, valorIcmsST);

        try {
            PedidoHelper.getWebPedido().setValor_total(Float.toString(valorVenda));
            PedidoHelper.getWebPedido().setValor_desconto(Float.toString(valorDesconto));
            PedidoHelper.getWebPedido().setValor_produtos(Float.toString(valorProduto));
            PedidoHelper.getWebPedido().setValor_desconto_adic(Float.toString(valorDescontoVerba));
            PedidoHelper.getWebPedido().setValor_icms_st(String.valueOf(valorIcmsST));
        } catch ( NullPointerException|NumberFormatException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        mudouItens = false; // Reseta a flag após o cálculo bem sucedido
    }

    /* NOVO: Método auxiliar para atualizar a interface do pedido evitando repetição de código e I/O de disco */
    private static void atualizaUiCamposPedido(ActivityPedidoMain activity, float valorVenda, float valorProduto, float valorDesconto, float valorDescontoVerba, float valorIcmsST) {
        if (activity == null) return;

        TextView edtTotalVenda = activity.findViewById(R.id.edtTotalVenda);
        TextView edtTotalProdutos = activity.findViewById(R.id.edtTotalProdutos);
        TextView edtTotalDescontos = activity.findViewById(R.id.edtTotalDescontos);
        TextView edtTotalIcmsST = activity.findViewById(R.id.edtTotalIcmsST);

        TextView totalVenda = activity.findViewById(R.id.totalVenda);
        TextView totalProdutos = activity.findViewById(R.id.totalProdutos);
        TextView totalDescontos = activity.findViewById(R.id.totalDescontos);
        TextView totalIcmsST = activity.findViewById(R.id.totalIcmsST);

        // NOVO: Cache da configuração de fonte para evitar leitura física do banco de dados em cada refresh de tela
        if (cacheExtraFonte == -1) {
            try {
                if (activity != null) {
                    cacheExtraFonte = new DBHelper(activity).pegaTamanhoExtraFonte();
                } else {
                    cacheExtraFonte = 0;
                }
            } catch (Exception e) {
                cacheExtraFonte = 0;
            }
        }
        int tExtraFonte = cacheExtraFonte;

        if ( tExtraFonte > 0) {
            if (totalVenda != null) totalVenda.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10 + tExtraFonte);
            if (totalProdutos != null) totalProdutos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10 + tExtraFonte);
            if (totalDescontos != null) totalDescontos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10 + tExtraFonte);
            if (totalIcmsST != null) totalIcmsST.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10 + tExtraFonte);

            if (edtTotalVenda != null) edtTotalVenda.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            if (edtTotalProdutos != null) edtTotalProdutos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            if (edtTotalDescontos != null) edtTotalDescontos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            if (edtTotalIcmsST != null) edtTotalIcmsST.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
        }

        if (edtTotalVenda != null) edtTotalVenda.setText(MascaraUtil.mascaraRealSemSimbolo(valorVenda));
        if (edtTotalProdutos != null) edtTotalProdutos.setText(MascaraUtil.mascaraRealSemSimbolo(valorProduto));
        if (edtTotalDescontos != null) edtTotalDescontos.setText(MascaraUtil.mascaraRealSemSimbolo(valorDesconto + valorDescontoVerba));
        if (edtTotalIcmsST != null) edtTotalIcmsST.setText(MascaraUtil.mascaraRealSemSimbolo(valorIcmsST));
    }

    public static void confirmaValorPedido(List<WebPedidoItens> produtoPedido, ActivityPedidoMain activityPedidoMain) {

        Float valorVenda = 0.f;
        Float valorDesconto = 0.f;
        Float valorDescontoVerba = 0.f;
        Float valorProduto = 0.f;
        Float valorIcmsST = 0.f;
        BigDecimal bvalorVenda = null;
        BigDecimal bvalorDesconto = null;
        BigDecimal bvalorDescontoVerba = null;
        BigDecimal bvalorProduto = null;
        BigDecimal bvalorIcmsST = null;
        for (int i = 0; produtoPedido.size() > i; i++) {
            if ( produtoPedido.get(i).getId_web_item() != null && !produtoPedido.get(i).getId_web_item().isEmpty()) {
                try {
                    bvalorVenda = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_total())).setScale(2, RoundingMode.HALF_EVEN);
                    valorVenda += Float.parseFloat(String.valueOf(bvalorVenda));  //Float.valueOf(produtoPedido.get(i).getValor_total());

                    bvalorDesconto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_desconto_real())).setScale(2, RoundingMode.HALF_EVEN);
                    valorDesconto += Float.parseFloat(String.valueOf(bvalorDesconto));//Float.valueOf(produtoPedido.get(i).getValor_desconto_real());

                    try {
                        bvalorDescontoVerba = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_desconto_adic())).setScale(2, RoundingMode.HALF_EVEN);
                        valorDescontoVerba += Float.parseFloat(String.valueOf(bvalorDescontoVerba));//Float.valueOf(produtoPedido.get(i).getValor_desconto_real());
                    } catch (NumberFormatException | NullPointerException e) {
                    } catch (Exception e) {
                    }

                    try {
                        bvalorIcmsST = new BigDecimal(Float.valueOf(produtoPedido.get(i).getIcms_st_valor())).setScale(2, RoundingMode.HALF_EVEN);
                        valorIcmsST += Float.parseFloat(String.valueOf(bvalorIcmsST));//Float.valueOf(produtoPedido.get(i).getValor_desconto_real());
                    } catch (NumberFormatException | NullPointerException e) {
                    } catch (Exception e) {
                    }

                    //bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getVenda_preco()) * Float.valueOf(produtoPedido.get(i).getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);
                    bvalorProduto = new BigDecimal(Float.valueOf(produtoPedido.get(i).getValor_unitario()) * Float.valueOf(produtoPedido.get(i).getQuantidade())).setScale(2, RoundingMode.HALF_EVEN);
                    valorProduto += Float.parseFloat(String.valueOf(bvalorProduto));//Float.valueOf(produtoPedido.get(i).getVenda_preco()) * Float.valueOf(produtoPedido.get(i).getQuantidade());
                } catch (NullPointerException | NumberFormatException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setValorVenda(valorVenda);
    }

    public static String confirmaQtdeItens(List<WebPedidoItens> produtoPedido) {
        int qtdeProdutos = 0;
        for (int i = 0; produtoPedido.size() > i; i++) {
            if ( produtoPedido.get(i).getId_web_item() != null && !produtoPedido.get(i).getId_web_item().isEmpty()) {
                qtdeProdutos+= 1;
            }
        }
        return String.valueOf(qtdeProdutos);
    }
        public static Float calculaValorSemDesconto() {

        Float resultado = 0.f;
        for (int i = 0; listaWebPedidoItens.size() > i; i++)
            try {
                resultado += Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto());
            } catch ( NullPointerException|NumberFormatException e) {
                e.printStackTrace();
            }
        return resultado;
    }

    public static Float calculaValorComDesconto() {

        Float resultado = 0.f;
        for (int i = 0; listaWebPedidoItens.size() > i; i++)
            try {
            resultado+= Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) - (Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto())*0.05f);
            } catch ( NullPointerException|NumberFormatException e) {
                e.printStackTrace();
            }
        //resultado += total;
        return resultado;
    }

    public static Float calculaValorComDescontoCategoria( float descontoCategoria) {
        BigDecimal bdesconto = null;
        BigDecimal btotalBruto = null;
        Float desconto = 0.0f;
        Float totalProdutoBruto = 0.0f;
        Float quantidade = null;
        Float resultado = 0.f;
        Float descontoReal = 0.00f;
        for (int i = 0; listaWebPedidoItens.size() > i; i++) {
            try {
                btotalBruto = new BigDecimal(Float.parseFloat(listaWebPedidoItens.get(i).getQuantidade()) * listaWebPedidoItens.get(i).getValor_unitario()).setScale(2, RoundingMode.HALF_EVEN);
                totalProdutoBruto = Float.parseFloat(String.valueOf(btotalBruto));
                bdesconto = new BigDecimal((descontoCategoria * totalProdutoBruto) / 100).setScale(3, RoundingMode.HALF_EVEN);
                int tamanho = String.valueOf(bdesconto).length();
                if (String.valueOf(bdesconto).substring(tamanho - 1).equalsIgnoreCase("5")) {
                    if (((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                        bdesconto = new BigDecimal((descontoCategoria * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.DOWN);
                    } else {
                        bdesconto = new BigDecimal((descontoCategoria * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.UP);
                    }
                } else {
                    bdesconto = new BigDecimal((descontoCategoria * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.HALF_EVEN);
                }
                descontoReal = Float.parseFloat(String.valueOf(bdesconto));

                resultado += Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) - descontoReal;
            } catch ( NullPointerException|NumberFormatException e) {
                e.printStackTrace();
            }
        }
            //resultado += total;
        return resultado;
    }

    public static Float calculaValorComDescontoPrazo(Float valorDesconto) {
        DBHelper dbh = new DBHelper(activityPedidoMain);
        Float resultado = 0.f;
        String tipo_tabela = "";
        Float total_parcial = 0.f;
        try {
            if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6"))
                    tipo_tabela = "6";
                if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4"))
                    tipo_tabela = "4";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BigDecimal bdesconto = null;
        BigDecimal btotalBruto = null;
        Float desconto = 0.0f;
        Float totalProdutoBruto = 0.0f;
        Float quantidade = null;

        for (int i = 0; listaWebPedidoItens.size() > i; i++) {
            try {
                quantidade = Float.valueOf(listaWebPedidoItens.get(i).getQuantidade());
                if (tipo_tabela.equalsIgnoreCase("6")) {
                    List<TabelaPrecoItem> listaTabelaPrecoItem = dbh.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_PRODUTO = " + listaWebPedidoItens.get(i).getId_produto() + " AND ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco());
                    if (dbh.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_PRODUTO = " + listaWebPedidoItens.get(i).getId_produto() + " AND ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco()).size() <= 0) {
                        //total_parcial =  ( Float.parseFloat( listaWebPedidoItens.get(i).getVenda_preco() ) * quantidade) - (((valorDesconto) / 100) * (Float.parseFloat(listaWebPedidoItens.get(i).getVenda_preco()) * quantidade));
                        //bdesconto = new BigDecimal((valorDesconto) * (quantidade * Float.parseFloat(listaWebPedidoItens.get(i).getVenda_preco())) / 100).setScale(2, RoundingMode.HALF_EVEN);
                        bdesconto = new BigDecimal((valorDesconto) * (quantidade * listaWebPedidoItens.get(i).getValor_unitario()) / 100).setScale(2, RoundingMode.HALF_EVEN);
                        desconto = Float.parseFloat(String.valueOf(bdesconto));
                        //btotalBruto = new BigDecimal((quantidade * Float.parseFloat(listaWebPedidoItens.get(i).getVenda_preco()))).setScale(2, RoundingMode.HALF_EVEN);
                        btotalBruto = new BigDecimal((quantidade * listaWebPedidoItens.get(i).getValor_unitario())).setScale(2, RoundingMode.HALF_EVEN);
                        totalProdutoBruto = Float.parseFloat(String.valueOf(btotalBruto));//(quantidade * Float.parseFloat(produto.getVenda_preco()));
                        if (desconto > 0.0f)
                            totalProdutoBruto = Float.parseFloat(MascaraUtil.duasCasaDecimal(totalProdutoBruto - desconto));
                        total_parcial = totalProdutoBruto;
                    } else {
                        //total_parcial = Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) - (Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) * (valorDesconto / 100));
                        //btotalBruto = new BigDecimal((quantidade * Float.parseFloat(listaWebPedidoItens.get(i).getVenda_preco()))).setScale(2, RoundingMode.HALF_EVEN);
                        btotalBruto = new BigDecimal((quantidade * listaWebPedidoItens.get(i).getValor_unitario())).setScale(2, RoundingMode.HALF_EVEN);
                        totalProdutoBruto = Float.parseFloat(String.valueOf(btotalBruto));//(quantidade * Float.parseFloat(produto.getVenda_preco()));
                        total_parcial = totalProdutoBruto;
                    }
                } else {
                    //total_parcial = Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) - (Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) * (valorDesconto / 100));
                    //bdesconto = new BigDecimal((valorDesconto) * (quantidade * Float.parseFloat(listaWebPedidoItens.get(i).getVenda_preco())) / 100).setScale(2, RoundingMode.HALF_EVEN);
                    bdesconto = new BigDecimal((valorDesconto) * (quantidade * listaWebPedidoItens.get(i).getValor_unitario()) / 100).setScale(2, RoundingMode.HALF_EVEN);
                    desconto = Float.parseFloat(String.valueOf(bdesconto));
                    //btotalBruto = new BigDecimal((quantidade * Float.parseFloat(listaWebPedidoItens.get(i).getVenda_preco()))).setScale(2, RoundingMode.HALF_EVEN);
                    btotalBruto = new BigDecimal((quantidade * listaWebPedidoItens.get(i).getValor_unitario())).setScale(2, RoundingMode.HALF_EVEN);
                    totalProdutoBruto = Float.parseFloat(String.valueOf(btotalBruto));//(quantidade * Float.parseFloat(produto.getVenda_preco()));
                    if (desconto > 0.0f)
                        totalProdutoBruto = Float.parseFloat(MascaraUtil.duasCasaDecimal(totalProdutoBruto - desconto));
                    total_parcial = totalProdutoBruto;
                }
                DecimalFormat decimal = new DecimalFormat("0.00");
                Double vl_conv = Double.parseDouble(String.valueOf(total_parcial));
                String total = (decimal.format(vl_conv));
                total = total.replaceAll(",", ".");
                resultado += Float.parseFloat(total);
            } catch (NullPointerException|NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public static Float calculDescontoPrazoTabela( Float valorDesconto) {

        Float resultado = 0.f;
        for (int i = 0; listaWebPedidoItens.size() > i; i++)
            resultado += Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) - (Float.valueOf(listaWebPedidoItens.get(i).getValor_bruto()) * (valorDesconto/100));
            //resultado += total;
        DecimalFormat decimal = new DecimalFormat( "0.00" );
        String total = (decimal.format(resultado));
        total = total.replaceAll(",",".");
        return Float.parseFloat(total);//resultado;
    }

    public static Float getValorVenda() {
        return valorVenda;
    }

    public static void setValorVenda(Float valorVenda) {
        PedidoHelper.valorVenda = valorVenda;
    }

    public Float getTotalVenda(){
        return PedidoHelper.valorVenda;
    }

    public boolean inserirProduto(WebPedidoItens webPedidoItem) {
        try {
            // AJUSTE DE SEGURANÇA EM 22/05/2024 - VERIFICA SE O FRAGMENTO EXISTE E ESTÁ ANEXADO
            // PARA EVITAR O ERRO 'NOT ATTACHED TO CONTEXT' APÓS INATIVIDADE
            if (pedido1 != null && pedido1.isAdded()) {
                if (pedido1.inserirProduto(webPedidoItem)) {
                    mudouItens = true; // NOVO: Marca mudança para forçar recálculo
                    return true;
                } else
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterarProduto(WebPedidoItens webPedidoIten, int position) {
        try {
            // AJUSTE DE SEGURANÇA EM 22/05/2024 - VERIFICA SE O FRAGMENTO EXISTE E ESTÁ ANEXADO
            // PARA EVITAR O ERRO 'NOT ATTACHED TO CONTEXT' APÓS INATIVIDADE
            if (pedido1 != null && pedido1.isAdded()) {
                if (pedido1.alterarProduto(webPedidoIten, position)) {
                    mudouItens = true; // NOVO: Marca mudança para forçar recálculo
                    return true;
                } else
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void moveTela(int position) {
        mViewPager = (ViewPager) activityPedidoMain.findViewById(R.id.vp_tabsPedido);
        mViewPager.setCurrentItem(position);
    }

    public boolean verificaCliente() {
        return activityPedidoMain.verificaCliente();
    }

    public boolean validaCredito() {
        CadastroFinanceiroResumo cadastroFinanceiroResumo = HistoricoFinanceiroHelper.getCadastroFinanceiroResumo();

        Float limiteUltilizado = db.soma("SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO " +
                "WHERE PEDIDO_ENVIADO = 'N' AND ID_CONDICAO_PAGAMENTO <> 1 " +
                "AND ID_WEB_PEDIDO <> " + PedidoHelper.getIdPedido() + " " +
                "AND ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro_servidor() + ";") + cadastroFinanceiroResumo.getLimiteUtilizado();
        Float saldoRestante = cadastroFinanceiroResumo.getLimiteCredito() - limiteUltilizado - getValorVenda() - cadastroFinanceiroResumo.getFinanceiroVencido();
        if (saldoRestante < 0 || cadastroFinanceiroResumo.getFinanceiroVencido() > 0) {
            Toast.makeText(activityPedidoMain, "Esse pedido não passou na análise de crédito!", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public static boolean verificaDesconto() {
        try {
            for (WebPedidoItens webPedidoItens : listaWebPedidoItens)
                if (Float.parseFloat(webPedidoItens.getValor_desconto_real()) > 0)
                    return true;
        } catch (Exception e) {
        }
        return false;
    }

    public void salvaPedidoParcial() {
        try {
            db = new DBHelper(activityPedidoMain);
            webPedidoDAO = new WebPedidoDAO(db);
            webPedidoItensDAO = new WebPedidoItensDAO(db);
            configuracaoDAO = new ConfiguracaoDAO(db);
            webPedido = activityPedidoMain.salvaPedido();
            Configuracao configuracao = new Configuracao();
            configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            webPedido.setTrabalha_com_campanha("N");
            webPedido.setTrabalha_com_dscto_reais("N");
            try {
                if (configuracao.getTrabalha_com_campanhas().equalsIgnoreCase("S"))
                    webPedido.setTrabalha_com_campanha("S");
            } catch (NullPointerException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (configuracao.getTrabalha_com_dscto_reais().equalsIgnoreCase("S"))
                    webPedido.setTrabalha_com_dscto_reais("S");
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!webPedido.getPedido_enviado().equalsIgnoreCase("S")) {
                if ( pedido2 == null ) {
                    try {
                        if (configuracao.getId_con_pgto_default_app() == null || configuracao.getId_con_pgto_default_app().equalsIgnoreCase("0")) {
                            if ( webPedido.getId_condicao_pagamento() == null || webPedido.getId_condicao_pagamento().isEmpty() || webPedido.getId_condicao_pagamento().equalsIgnoreCase("0")) {
                                webPedido.setId_condicao_pagamento(db.consultaDados("SELECT ID_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ATIVO = 'S' ORDER BY ID_CONDICAO LIMIT 1", "ID_CONDICAO"));
                            }
                        } else {
                            if ( webPedido.getId_condicao_pagamento() == null || webPedido.getId_condicao_pagamento().isEmpty() || webPedido.getId_condicao_pagamento().equalsIgnoreCase("0")) {
                                webPedido.setId_condicao_pagamento(db.consultaDados("SELECT ID_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ATIVO = 'S' AND ID_CONDICAO = '" + configuracao.getId_con_pgto_default_app() + "'", "ID_CONDICAO"));
                            }
                        }
                    } catch (NullPointerException ex) {
                        if ( webPedido.getId_condicao_pagamento() == null || webPedido.getId_condicao_pagamento().isEmpty() || webPedido.getId_condicao_pagamento().equalsIgnoreCase("0")) {
                            webPedido.setId_condicao_pagamento(db.consultaDados("SELECT ID_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ATIVO = 'S' ORDER BY ID_CONDICAO LIMIT 1", "ID_CONDICAO"));
                        }
                    }
                    if (webPedido.getId_operacao() == null || webPedido.getId_operacao().isEmpty() || webPedido.getId_operacao().equalsIgnoreCase("0")) {
                        webPedido.setId_operacao(db.consultaDados("SELECT ID_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ATIVO = 'S' ORDER BY ID_OPERACAO LIMIT 1", "ID_OPERACAO"));
                    }
                    if ( webPedido.getId_moeda_padrao() == null || webPedido.getId_moeda_padrao().isEmpty() || webPedido.getId_moeda_padrao().equalsIgnoreCase("0")) {
                        webPedido.setId_moeda_padrao(db.consultaDados("SELECT ID FROM TBL_MOEDA ORDER BY ID LIMIT 1", "ID"));
                    }
                } else {
                    try {
                        webPedido.setId_condicao_pagamento(pedido2.salvaPedido().getId_condicao_pagamento());
                        webPedido.setId_tabela(pedido2.salvaPedido().getId_tabela());
                        webPedido.setObservacoes(pedido2.salvaPedido().getObservacoes());
                        webPedido.setData_prev_entrega(pedido2.salvaPedido().getData_prev_entrega());
                        webPedido.setPedido_enviado(pedido2.salvaPedido().getPedido_enviado());
                        webPedido.setId_operacao(pedido2.salvaPedido().getId_operacao());
                        webPedido.setId_moeda_padrao(pedido2.salvaPedido().getId_moeda_padrao());
                        try {
                            webPedido.setData_prev_entrega(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(pedido2.salvaPedido().getData_prev_entrega())));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    listaWebPedidoItens = pedido1.salvaPedidos();
                    if ( listaWebPedidoItens != null) {
                        webPedido.setQtde_itens(String.valueOf(listaWebPedidoItens.size()));
                    }
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (PedidoHelper.getIdPedido() > 0) {
                    webPedido.setId_web_pedido(String.valueOf(PedidoHelper.getIdPedido()));
                    if ( Float.parseFloat(webPedido.getValor_total()) > 0.00f && Float.parseFloat(webPedido.getValor_produtos()) <= 0.00) {
                       if( !webPedido.getWebPedidoItens().isEmpty()) {
                            webPedido.setValor_produtos(webPedido.getValor_total());
                        }
                    }
                    webPedidoDAO.update(webPedido);
                } else {
                    if (!webPedido.getCadastro().equals(null)) {
                        String dataHoraAtual = db.pegaDataHoraAtual();
                        webPedido.setPedido_enviado("N");
                        webPedido.setFinalizado("N");
                        webPedido.setUsuario_lancamento_data(db.pegaDataHoraAtual());
                        webPedido.setData_emissao(db.pegaDataAtual());

                        int maxId = db.ultimoId("SELECT MAX(ID_WEB_PEDIDO) FROM TBL_WEB_PEDIDO") + 1;
                        try {
                            if (webPedido.getCadastro().getId_cadastro_servidor() <= 0) {
                                if ( webPedido.getId_pedido_app() == null || webPedido.getId_pedido_app().isEmpty()) {
                                    webPedido.setId_pedido_app(String.format("%03d", Integer.parseInt(webPedido.getId_vendedor().trim())) + String.format("%08d", maxId) + String.format("%08d", webPedido.getCadastro().getId_cadastro()) + dataHoraAtual);
                                }
                            }else {
                                if ( webPedido.getId_pedido_app() == null || webPedido.getId_pedido_app().isEmpty()) {
                                    webPedido.setId_pedido_app(String.format("%03d", Integer.parseInt(webPedido.getId_vendedor().trim())) + String.format("%08d", maxId)  + String.format("%08d", webPedido.getCadastro().getId_cadastro_servidor()) + dataHoraAtual);
                                }
                            }
                        } catch (NullPointerException | NumberFormatException e) {
                        } catch (Exception e) {
                        }
                        int idPedido = (int) webPedidoDAO.add(webPedido);
                        try {
                            if (idPedido > 0) {
                                if (webPedido.getId_visita_app() != null && !webPedido.getId_visita_app().isEmpty()) {
                                    try {
                                        com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO visitaDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db);
                                        visitaDAO.vincularPedidoAVisita(webPedido.getId_visita_app(), idPedido, webPedido.getId_pedido_app());
                                        com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO analiseDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO(db);
                                        analiseDAO.vincularPedidoPorVisita(webPedido.getId_visita_app(), webPedido.getId_pedido_app());
                                    } catch (Exception ignored) {}
                                }
                                PedidoHelper.setIdPedido(idPedido);
                                if ( webPedido.getCadastro().getId_cadastro() == 0) {
                                    getActivityPedidoMain().toolbar.setSubtitle("Pré-pedido: " + idPedido + " - " + MascaraUtil.formataData(webPedido.getData_emissao(), "/"));
                                } else {
                                    getActivityPedidoMain().toolbar.setSubtitle("Pedido: " + idPedido + " - " + MascaraUtil.formataData(webPedido.getData_emissao(), "/"));
                                }
                                if (webPedido.getWebPedidoItens() != null) {
                                    /* LÓGICA ANTERIOR (LENTA) COMENTADA:
                                    for (int i = 0; i < webPedido.getWebPedidoItens().size(); i++)
                                        if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND ID_PRODUTO = '" + webPedido.getWebPedidoItens().get(i).getId_produto().trim() + "') " +
                                                " OR (ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND ID_PRODUTO = '" + webPedido.getWebPedidoItens().get(i).getId_produto() + "');") <= 0) {
                                            if (webPedido.getWebPedidoItens().get(i).getId_pedido().isEmpty()) {
                                                webPedido.getWebPedidoItens().get(i).setId_pedido(String.valueOf(idPedido));
                                                webPedido.getWebPedidoItens().get(i).setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                                webPedido.getWebPedidoItens().get(i).setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
                                                //pedido1.inserirProduto(webPedido.getWebPedidoItens().get(i));
                                                int id_web_item = (int) webPedidoItensDAO.add(webPedido.getWebPedidoItens().get(i));
                                                if (id_web_item > 0) {
                                                    webPedido.getWebPedidoItens().get(i).setId_web_item(String.valueOf(id_web_item));
                                                } else {
                                                    showMsgAlerta("Ocorreu um erro ao obter os dados do produto inserido! Favor entrar em contato com o suporte técnico!");
                                                }
                                            }
                                        }
                                    */

                                    // NOVA LÓGICA OTIMIZADA:
                                    for (WebPedidoItens item : webPedido.getWebPedidoItens()) {
                                        if (item.getId_pedido() == null || item.getId_pedido().isEmpty()) {
                                            item.setId_pedido(String.valueOf(idPedido));
                                            item.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                            item.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
                                        }
                                    }
                                    if (!webPedidoItensDAO.addListaItensOtimizada(webPedido.getWebPedidoItens(), idPedido)) {
                                        showMsgAlerta("Ocorreu um erro ao obter os dados do produto inserido! Favor entrar em contato com o suporte técnico!");
                                    }

                                    try {
                                        listaWebPedidoItens = pedido1.salvaPedidos();
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else
                                showMsgAlerta("Ocorreu um erro ao obter os dados para iniciar o pedido! Favor entrar em contato com o suporte técnico!");

                        } catch (NullPointerException | NumberFormatException e) {
                            showMsgAlerta("Ocorreu um erro ao obter os dados para iniciar o pedido!! Favor entrar em contato com o suporte técnico!!");
                        } catch (Exception e) {
                            showMsgAlerta("Ocorreu um erro ao obter os dados para iniciar o pedido!!! Favor entrar em contato com o suporte técnico!!!");
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    public boolean salvaPedido() {
        String x = "0";
        try {
            if (UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( getActivityPedidoMain()));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( getActivityPedidoMain()));
        } catch ( Exception e) {
        }
        float desconto_perc = 0.0f;
        float desconto_item_perc = 0.0f;
        try {
            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6") && PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S")) {
                Cursor cursor = db.listaDados("SELECT DESCONTO_PERC FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = '" + PedidoHelper.getWebPedido().getId_condicao_pagamento() + "' AND ACEITA_DESCONTO = 'S'");
                if (cursor.moveToNext())
                    desconto_perc = Float.parseFloat(cursor.getString(cursor.getColumnIndex("DESCONTO_PERC")));
            }
        } catch (SQLException |NumberFormatException|NullPointerException| CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        try {
            webPedidoDAO = new WebPedidoDAO(db);
            webPedidoItensDAO = new WebPedidoItensDAO(db);
            webPedido = activityPedidoMain.salvaPedido();
            webPedido.setId_condicao_pagamento(pedido2.salvaPedido().getId_condicao_pagamento());
            webPedido.setId_tabela(pedido2.salvaPedido().getId_tabela());
            webPedido.setObservacoes(pedido2.salvaPedido().getObservacoes());
            webPedido.setData_prev_entrega(pedido2.salvaPedido().getData_prev_entrega());
            webPedido.setPedido_enviado(pedido2.salvaPedido().getPedido_enviado());
            webPedido.setId_operacao(pedido2.salvaPedido().getId_operacao());
            webPedido.setId_moeda_padrao(pedido2.salvaPedido().getId_moeda_padrao());
            webPedido.setValor_recibo(pedido2.salvaPedido().getValor_recibo());
            try {
                webPedido.setData_prev_entrega(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(pedido2.salvaPedido().getData_prev_entrega())));
            } catch (ParseException e) {
                webPedido.setData_prev_entrega(null);
                e.printStackTrace();
            }
            listaWebPedidoItens = pedido1.salvaPedidos();
            try {
                if (webPedido.getCadastro() != null) {
                    if (listaWebPedidoItens != null && listaWebPedidoItens.size() > 0) {
                            Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                            try {
                                if (configuracao.getLayout_venda_erp().trim().equalsIgnoreCase("25")) {
                                    webPedido.setData_prev_entrega(db.pegaDataAtual());
                                }
                            } catch ( NullPointerException e) {
                                webPedido.setData_prev_entrega(db.pegaDataAtual());
                            } catch (Exception e) {
                                webPedido.setData_prev_entrega(db.pegaDataAtual());
                            }
                        if (webPedido.getData_prev_entrega() != null) {
                            Calendar dataAtual = new GregorianCalendar();
                            Calendar dataPedido = new GregorianCalendar();
                            Date date = new Date();
                            dataAtual.setTime(date);
                            dataPedido.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega()));
                            String atual = new SimpleDateFormat("dd/MM/yyyy").format(dataAtual.getTime());
                            String pedido = new SimpleDateFormat("dd/MM/yyyy").format(dataPedido.getTime());
                            if (webPedido.getId_condicao_pagamento() != null && !webPedido.getId_condicao_pagamento().equals("0")) {
                                if ( atual.equals(pedido) || !dataAtual.getTime().after(dataPedido.getTime()))   {
                                    Boolean descontoIndevido = false;
                                    Cliente cliente = ClienteHelper.getCliente();
                                    for (WebPedidoItens webPedidoItens : listaWebPedidoItens) {
                                        try {
                                            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6")) {
                                                try {
                                                    desconto_item_perc = Float.parseFloat(webPedidoItens.getValor_desconto_per());
                                                } catch (Exception e) {
                                                    desconto_item_perc = 0.0f;
                                                }
                                                if (desconto_item_perc > desconto_perc) {
                                                    descontoIndevido = true;
                                                    break;
                                                }
                                            } else {
                                                if (webPedidoItens.getDescontoIndevido()) {
                                                    descontoIndevido = true;
                                                    break;
                                                }
                                            }
                                        } catch ( NullPointerException e) {
                                            if (webPedidoItens.getDescontoIndevido()) {
                                                descontoIndevido = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!descontoIndevido) {
                                        webPedido.setDescontoIndevido("N");
                                        webPedido.setValor_total(valorVenda.toString());
                                        if (listaWebPedidoItens != null) {
                                            for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                                valorProdutos += Float.parseFloat(listaWebPedidoItens.get(i).getValor_bruto());
                                                descontoReal += Float.parseFloat(listaWebPedidoItens.get(i).getValor_desconto_real());
                                            }
                                            if (listaWebPedidoItens.size() > 0) {
                                                mediaDesconto = (mediaDesconto / listaWebPedidoItens.size());
                                            }
                                        }
                                        webPedido.setDesconto_per(String.valueOf(mediaDesconto));
                                        webPedido.setValor_produtos(String.valueOf(valorProdutos));
                                        webPedido.setValor_desconto(String.valueOf(descontoReal));
                                        try {
                                            if (Pedido1.listaProdutoRemovido != null && Pedido1.listaProdutoRemovido.size() > 0 && PedidoHelper.getIdPedido() > 0)
                                                webPedidoItensDAO.delete(PedidoHelper.getActivityPedidoMain(), Pedido1.listaProdutoRemovido);
                                        } catch ( NullPointerException e) {
                                            e.printStackTrace();
                                        }
                                        calculaValorPedido(listaWebPedidoItens, activityPedidoMain);
                                        webPedido.setValor_total(String.valueOf(getValorVenda()));

                                        if (PedidoHelper.getIdPedido() > 0) {
                                            webPedido.setId_web_pedido(String.valueOf(PedidoHelper.getIdPedido()));
                                            webPedido.setFinalizado("S");
                                            webPedidoDAO.update(webPedido);
                                            if (webPedido.getId_visita_app() != null && !webPedido.getId_visita_app().isEmpty()) {
                                                try {
                                                    com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO visitaDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db);
                                                    visitaDAO.vincularPedidoAVisita(webPedido.getId_visita_app(), PedidoHelper.getIdPedido(), webPedido.getId_pedido_app());
                                                    com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO analiseDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO(db);
                                                    analiseDAO.vincularPedidoPorVisita(webPedido.getId_visita_app(), webPedido.getId_pedido_app());
                                                } catch (Exception ignored) {}
                                            }
                                            if (listaWebPedidoItens != null) {
                                                for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                                    listaWebPedidoItens.get(i).setId_pedido(webPedido.getId_web_pedido());
                                                    listaWebPedidoItens.get(i).setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                                    if (listaWebPedidoItens.get(i).getId_web_item() == null || listaWebPedidoItens.get(i).getId_web_item().isEmpty()) {
                                                        if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND ID_PRODUTO = '" + listaWebPedidoItens.get(i).getId_produto().trim() + "')" +
                                                                " OR (ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND ID_PRODUTO = '" + listaWebPedidoItens.get(i).getId_produto() + "');") <= 0) {
                                                            int id_web_item = (int) webPedidoItensDAO.add(listaWebPedidoItens.get(i));
                                                            if (id_web_item > 0) {
                                                                listaWebPedidoItens.get(i).setId_web_item(String.valueOf(id_web_item));
                                                            }
                                                        }
                                                    } else {
                                                        webPedidoItensDAO.update(listaWebPedidoItens.get(i));
                                                    }
                                                }
                                            }
                                        } else {
                                            webPedido.setPedido_enviado("N");
                                            webPedido.setUsuario_lancamento_data(db.pegaDataHoraAtual());
                                            webPedido.setData_emissao(db.pegaDataAtual());
                                            int idPedido = (int) webPedidoDAO.add(webPedido);
                                            if (idPedido > 0) {
                                                if (webPedido.getId_visita_app() != null && !webPedido.getId_visita_app().isEmpty()) {
                                                    try {
                                                        com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO visitaDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db);
                                                        visitaDAO.vincularPedidoAVisita(webPedido.getId_visita_app(), idPedido, webPedido.getId_pedido_app());
                                                        com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO analiseDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO(db);
                                                        analiseDAO.vincularPedidoPorVisita(webPedido.getId_visita_app(), webPedido.getId_pedido_app());
                                                    } catch (Exception ignored) {}
                                                }
                                                if (listaWebPedidoItens != null) {
                                                    for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                                        listaWebPedidoItens.get(i).setId_pedido(String.valueOf(idPedido));
                                                        listaWebPedidoItens.get(i).setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                                        if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + listaWebPedidoItens.get(i).getId_produto().trim() + "') " +
                                                                " OR (ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + listaWebPedidoItens.get(i).getId_produto() + "');") <= 0) {
                                                            int id_web_item = (int) webPedidoItensDAO.add(listaWebPedidoItens.get(i));
                                                            if (id_web_item > 0) {
                                                                listaWebPedidoItens.get(i).setId_web_item(String.valueOf(id_web_item));
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(activityPedidoMain, "Falha ao salvar o pedido", Toast.LENGTH_SHORT).show();
                                                return false;
                                            }

                                        }
                                        if (listaWebPedidoItens != null) {
                                            confirmaValorPedido(listaWebPedidoItens, activityPedidoMain);
                                            webPedido.setQtde_itens(confirmaQtdeItens(listaWebPedidoItens));
                                        }
                                        webPedido.setValor_total(String.valueOf(getValorVenda()));
                                        webPedidoDAO.update(webPedido);
                                        if (pedido1 != null) {
                                            pedido1.limpaListaProdutosExcluidos();
                                        }
                                        return true;
                                    } else {
                                        //webPedidoDAO.update(webPedido);
                                        //confirmaValorPedido(listaWebPedidoItens, activityPedidoMain);
                                        webPedido.setQtde_itens(confirmaQtdeItens(listaWebPedidoItens));
                                        webPedido.setDescontoIndevido("S");
                                        webPedidoDAO.updateDescontoIndevido(webPedido, "S");
                                        Toast.makeText(activityPedidoMain, "Há produtos fora da faixa de desconto permitida", Toast.LENGTH_SHORT).show();
                                        moveTela(1);
                                        return false;
                                    }
                                } else {
                                    Toast.makeText(activityPedidoMain, "A data de entrega não pode ser menor que a data atual/emissão da venda", Toast.LENGTH_SHORT).show();
                                    editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext_erro);
                                    moveTela(2);
                                    return false;
                                }
                            } else {
                                Toast.makeText(activityPedidoMain, "Você precisa selecionar uma condição de pagamento", Toast.LENGTH_SHORT).show();
                                moveTela(2);
                                return false;
                            }
                        } else {
                            Toast.makeText(activityPedidoMain, "ATENÇÃO - Você precisa informar a data de entrega", Toast.LENGTH_SHORT).show();
                            editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext_erro);
                            moveTela(2);
                            return false;
                        }
                    } else {
                        Toast.makeText(activityPedidoMain, "O pedido não pode ser salvo sem produtos!", Toast.LENGTH_SHORT).show();
                        moveTela(1);
                        return false;
                    }
                } else {
                    Toast.makeText(activityPedidoMain, "O pedido não pode ser salvo sem selecionar o cliente!", Toast.LENGTH_SHORT).show();
                    pintaTxtNomeCliente();
                    moveTela(0);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (NullPointerException e) {
            if (listaWebPedidoItens == null) {
                Toast.makeText(activityPedidoMain, "O pedido não pode ser salvo sem produtos!", Toast.LENGTH_SHORT).show();
                moveTela(1);
            }
            e.printStackTrace();
            return false;
        }
    }

    public void limparDados() {
        mudouItens = true; // NOVO: Garante recálculo no próximo pedido
        cacheExtraFonte = -1; // NOVO: Limpa cache de fonte
        valorVenda = null;
        listaWebPedidoItens = null;
        webPedido = null;
        webPedidoItem = null;
        //activityPedidoMain = null;
        //activityPedidoMain = null;
        produtoPedidoActivity = null;
        try {
            Pedido1.listaProdutoRemovido = null;
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        pedido0 = null;
        pedido1 = null;
        pedido2 = null;
        webPedido = null;
        idPedido = 0;
        try {
            buscaProduto = null;
            condicoesPagamento = null;
            listaProdutos = null;
        } catch (NullPointerException ignored) {
        }
        Log.i("PedidoHelper", "Memoria limpa com sucesso");
        //Toast.makeText(, "Memoria limpa com sucesso!", Toast.LENGTH_SHORT).show();
        //System.gc();
    }

    public  void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = activityPedidoMain.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activityPedidoMain).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activityPedidoMain);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public boolean analisePedido(WebPedido webPedido) {
        if ( getListaWebPedidoItensSalvo().size() == db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + PedidoHelper.getIdPedido() )) {
            return true;
        }
        return false;
    }


}
