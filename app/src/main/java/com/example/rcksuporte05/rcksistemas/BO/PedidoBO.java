package com.example.rcksuporte05.rcksistemas.BO;

import android.content.Context;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.PromocaoCliente;
import com.example.rcksuporte05.rcksistemas.model.PromocaoProduto;
import com.example.rcksuporte05.rcksistemas.model.PromocaoRetorno;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;

import java.util.List;

/**
 * Created by RCK 03 on 07/12/2017.
 */

public class PedidoBO {
    DBHelper db;

    /* QUESTÃO 4: CACHE DE PROMOÇÕES PARA EVITAR N+1 QUERIES NO LOOP DE ITENS */
    private List<Promocao> cachePromo_C1_P1;
    private List<Promocao> cachePromo_C0_P1;
    private List<Promocao> cachePromo_C1_P0;
    private List<Promocao> cachePromo_Geral;

    public PromocaoRetorno calculaDesconto(int idCliente, String idProduto, Context context) {
        if (db == null) {
            db = new DBHelper(context);
        }
        PromocaoDAO promocaoDAO = new PromocaoDAO(db);
        PromocaoRetorno promocaoRetorno = new PromocaoRetorno(0.f);

        // 1. CLIENTE E PRODUTO (1,1)
        if (cachePromo_C1_P1 == null) {
            cachePromo_C1_P1 = promocaoDAO.getLista("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 1 AND APLICACAO_PRODUTO = 1 AND ATIVO = 'S' AND ID_EMPRESA = ");
        }

        if (cachePromo_C1_P1.size() > 0)
            for (Promocao promocao : cachePromo_C1_P1)
                for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente())
                    if (promocaoCliente.getIdCadastro() == idCliente)
                        //if (promocao.getAplicacaoProduto() > 0) {
                        for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                            if (promocaoProduto.getIdProduto().equals(idProduto))
                                if (promocaoRetorno.getValorDesconto() > promocaoProduto.getDescontoPerc())
                                    return promocaoRetorno;
                                else {
                                    promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                                    promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                                    return promocaoRetorno;
                                }

        // 2. TODOS OS CLIENTE E INFORMANDO O PRODUTO (0,1)
        if (cachePromo_C0_P1 == null) {
            cachePromo_C0_P1 = promocaoDAO.getLista("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 0 AND APLICACAO_PRODUTO = 1 AND ATIVO = 'S' AND ID_EMPRESA = ");
        }

        if (cachePromo_C0_P1.size() > 0)
            for (Promocao promocao : cachePromo_C0_P1)
                for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                    if (promocaoProduto.getIdProduto().equals(idProduto))
                        if (promocaoRetorno.getValorDesconto() > promocaoProduto.getDescontoPerc())
                            return promocaoRetorno;
                        else {
                            promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                            promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                            return promocaoRetorno;
                        }

        // 3. CLIENTE E TODOS OS PRODUTOS (1,0)
        if (cachePromo_C1_P0 == null) {
            cachePromo_C1_P0 = promocaoDAO.getLista("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 1 AND APLICACAO_PRODUTO = 0 AND ATIVO = 'S' AND ID_EMPRESA = ");
        }

        if (cachePromo_C1_P0.size() > 0)
            for (Promocao promocao : cachePromo_C1_P0)
                for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente())
                    if (promocaoCliente.getIdCadastro() == idCliente)
                        if (promocaoRetorno.getValorDesconto() > promocao.getDescontoPerc())
                            return promocaoRetorno;
                        else {
                            promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                            promocaoRetorno.setValorDesconto(promocao.getDescontoPerc());
                            return promocaoRetorno;
                        }

        // 4. GERAL (0,0)
        if (cachePromo_Geral == null) {
            cachePromo_Geral = promocaoDAO.getLista();
        }

        if (cachePromo_Geral.size() > 0)
            for (Promocao promocao : cachePromo_Geral)
                if (promocao.getAplicacaoProduto() > 0) {
                    for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                        if (promocaoProduto.getIdProduto().equals(idProduto)) {
                            promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                            promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                            return promocaoRetorno;
                        }
                } else {
                    promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                    promocaoRetorno.setValorDesconto(promocao.getDescontoPerc());
                }
        return promocaoRetorno;
    }

    /* LÓGICA ANTERIOR (LENTA) COMENTADA:
    public PromocaoRetorno calculaDesconto(int idCliente, String idProduto, Context context) {
        db = new DBHelper(context);
        PromocaoDAO promocaoDAO = new PromocaoDAO(db);
        PromocaoRetorno promocaoRetorno = new PromocaoRetorno(0.f);
        List<Promocao> listaPromocaoCliente = promocaoDAO.getLista("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 1 AND APLICACAO_PRODUTO = 1 AND ATIVO = 'S' AND ID_EMPRESA = ");
        if (listaPromocaoCliente.size() > 0)
            for (Promocao promocao : listaPromocaoCliente)
                for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente())
                    if (promocaoCliente.getIdCadastro() == idCliente)
                        //if (promocao.getAplicacaoProduto() > 0) {
                        for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                            if (promocaoProduto.getIdProduto().equals(idProduto))
                                if (promocaoRetorno.getValorDesconto() > promocaoProduto.getDescontoPerc())
                                    return promocaoRetorno;
                                else {
                                    promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                                    promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                                    return promocaoRetorno;
                                }
        //CLIENTE E PRODUTO

        listaPromocaoCliente = promocaoDAO.getLista("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 0 AND APLICACAO_PRODUTO = 1 AND ATIVO = 'S' AND ID_EMPRESA = ");
        if (listaPromocaoCliente.size() > 0)
            for (Promocao promocao : listaPromocaoCliente)
                for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                    if (promocaoProduto.getIdProduto().equals(idProduto))
                        if (promocaoRetorno.getValorDesconto() > promocaoProduto.getDescontoPerc())
                            return promocaoRetorno;
                        else {
                            promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                            promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                            return promocaoRetorno;
                        }
         //TODOS OS CLIENTE E INFORMANDO O PRODUTO

        listaPromocaoCliente = promocaoDAO.getLista("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 1 AND APLICACAO_PRODUTO = 0 AND ATIVO = 'S' AND ID_EMPRESA = ");
        if (listaPromocaoCliente.size() > 0)
            for (Promocao promocao : listaPromocaoCliente)
                for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente())
                    if (promocaoCliente.getIdCadastro() == idCliente)
                        if (promocaoRetorno.getValorDesconto() > promocao.getDescontoPerc())
                            return promocaoRetorno;
                        else {
                            promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                            promocaoRetorno.setValorDesconto(promocao.getDescontoPerc());
                            return promocaoRetorno;
                        }
        //TODOS OS CLIENTE E INFORMANDO O PRODUTO

        List<Promocao> listaPromocao = promocaoDAO.getLista();
        if (listaPromocao.size() > 0)
            for (Promocao promocao : listaPromocao)
                if (promocao.getAplicacaoProduto() > 0) {
                    for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                        if (promocaoProduto.getIdProduto().equals(idProduto)) {
                            promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                            promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                            return promocaoRetorno;
                        }
                } else {
                    promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                    promocaoRetorno.setValorDesconto(promocao.getDescontoPerc());
                }
        return promocaoRetorno;
    }
    */

    public float calculaDesconto(Cliente cliente) {
        try {
            TabelaPrecoItem tabelaPrecoItem;
            tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria()).get(0);
            return Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());
        } catch ( NullPointerException|NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;

    }
}

/*
    public boolean excluirPedido(Context context, List<WebPedido> webPedidos) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        db = new DBHelper(context);
        for (WebPedido pedido : webPedidos) {
            try {
                if( db.deletar( "TBL_WEB_PEDIDO", "ID_WEB_PEDIDO=?", new String[]{pedido.getId_web_pedido()} ) >= 1) {
                    db.deletar( "TBL_WEB_PEDIDO_ITENS", "ID_PEDIDO =?", new String[]{pedido.getId_web_pedido()} ) ;
                } else {
                    return false;
                }

            } catch ( Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

     */
/*
    public boolean excluirPedido(Context context, WebPedido pedido) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        db = new DBHelper(context);
        try {
            if( db.deletar( "TBL_WEB_PEDIDO", "ID_WEB_PEDIDO=?", new String[]{pedido.getId_web_pedido()} ) >= 1) {
                db.deletar( "TBL_WEB_PEDIDO_ITENS", "ID_PEDIDO =?", new String[]{pedido.getId_web_pedido()} ) ;
            } else {
                return false;
            }
        } catch ( Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
     }

     */
/*
    public boolean excluiItensPedido(Context context, List<WebPedidoItens> webPedidoItens) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        db = new DBHelper(context);
        for (WebPedidoItens webPedidoIten : webPedidoItens) {
            try {
                if( db.deleteDados( "TBL_WEB_PEDIDO_ITENS", "ID_PEDIDO =? AND ID_WEB_ITEM=?", new String[]{webPedidoIten.getId_pedido(), webPedidoIten.getId_web_item()} ) <= 0) {
                    return false;
                }
            } catch ( Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public boolean excluiItenPedido(Context context, WebPedidoItens webPedidoIten) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        db = new DBHelper(context);
        try {
            if( db.deleteDados( "TBL_WEB_PEDIDO_ITENS", "ID_PEDIDO =? AND ID_WEB_ITEM=?", new String[]{webPedidoIten.getId_pedido(), webPedidoIten.getId_web_item()} ) >= 1) {
                return true;
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

     */
