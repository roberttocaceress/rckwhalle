package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class WebPedidoItensDAO {
    private DBHelper db;

    public WebPedidoItensDAO(DBHelper db) {
        this.db = db;
    }

    public long add(WebPedidoItens webPedidoItem) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_WEB_ITEM_SERVIDOR", webPedidoItem.getId_web_item_servidor());
            content.put("ID_PEDIDO", webPedidoItem.getId_pedido());
            content.put("ID_PRODUTO", webPedidoItem.getId_produto());
            content.put("ID_EMPRESA", webPedidoItem.getId_empresa());
            content.put("QUANTIDADE", webPedidoItem.getQuantidade());
            if ( webPedidoItem.getVenda_preco() != null && !webPedidoItem.getVenda_preco().isEmpty() ) {
                content.put("VALOR_UNITARIO", Float.parseFloat(webPedidoItem.getVenda_preco()) > 0.00f ? Float.parseFloat(webPedidoItem.getVenda_preco()) : webPedidoItem.getValor_unitario());
            } else {
                content.put("VALOR_UNITARIO",  webPedidoItem.getValor_unitario());
            }
            //content.put("VALOR_UNITARIO", Float.parseFloat(webPedidoItem.getVenda_preco()) > 0.00f ? Float.parseFloat(webPedidoItem.getVenda_preco()) : webPedidoItem.getValor_unitario());
            content.put("VALOR_BRUTO", webPedidoItem.getValor_bruto());
            content.put("VALOR_DESCONTO_REAL", webPedidoItem.getValor_desconto_real());
            content.put("VALOR_DESCONTO_PER_ADD", webPedidoItem.getValor_desconto_per_add());
            content.put("VALOR_DESCONTO_REAL_ADD", webPedidoItem.getValor_desconto_real_add());
            content.put("VALOR_TOTAL", webPedidoItem.getValor_total());
            content.put("DATA_MOVIMENTACAO", db.pegaDataAtual());
            content.put("USUARIO_LANCAMENTO_ID", webPedidoItem.getUsuario_lancamento_id());
            content.put("USUARIO_LANCAMENTO_DATA", db.pegaDataHoraAtual());
            content.put("ID_ITEM_DESCONTO", webPedidoItem.getId_item_desconto());
            content.put("PONTOS_UNITARIO", webPedidoItem.getPontos_unitario());
            content.put("PONTOS_TOTAL", webPedidoItem.getPontos_total());
            content.put("PONTOS_COEFICIENTE", webPedidoItem.getPontos_coeficiente());
            content.put("COMISSAO_PERCENTUAL", webPedidoItem.getComissao_percentual());
            content.put("COMISSAO_VALOR", webPedidoItem.getComissao_valor());
            content.put("VALOR_BONUS_CREDOR", webPedidoItem.getValor_bonus_credor());
            content.put("PERC_BONUS_CREDOR", webPedidoItem.getPerc_bonus_credor());
            content.put("VALOR_DESCONTO_PER_ORIG", webPedidoItem.getValor_desconto_per_orig());
            content.put("VALOR_DESCONTO_REAL_ORIG", webPedidoItem.getValor_desconto_real_orig());
            content.put("VALOR_DESCONTO_PER_ADD_ORIG", webPedidoItem.getValor_desconto_per_add_orig());
            content.put("VALOR_DESCONTO_REAL_ADD_ORIG", webPedidoItem.getValor_desconto_real_add_orig());
            content.put("ID_TABELA_PRECO_FAIXA_ORIG", webPedidoItem.getId_tabela_preco_faixa_orig());
            content.put("VALOR_TOTAL_ORIG", webPedidoItem.getValor_total_orig());
            content.put("PONTOS_UNITARIO_ORIG", webPedidoItem.getPontos_unitario_orig());
            content.put("PONTOS_COEFICIENTE_ORIG", webPedidoItem.getPontos_coeficiente_orig());
            content.put("COMISSAO_PERCENTUAL_ORIG", webPedidoItem.getComissao_percentual_orig());
            content.put("VALOR_BONUS_CREDOR_ORIG", webPedidoItem.getValor_bonus_credor_orig());
            content.put("PERC_BONUS_CREDOR_ORIG", webPedidoItem.getPerc_bonus_credor_orig());
            content.put("COMISSAO_VALOR_ORIG", webPedidoItem.getComissao_valor_orig());
            content.put("PONTOS_TOTAL_ORIG", webPedidoItem.getPontos_total_orig());
            content.put("PONTOS_COR_ORIG", webPedidoItem.getPontos_cor_orig());
            content.put("VALOR_PRECO_PAGO", webPedidoItem.getValor_preco_pago());
            content.put("TIPO_DESCONTO", webPedidoItem.getTipoDesconto());
            content.put("VALOR_DESCONTO_PER", webPedidoItem.getValor_desconto_per());
            content.put("NOME_PRODUTO", webPedidoItem.getNome_produto());
            content.put("PRODUTO_MATERIA_PRIMA", webPedidoItem.getProduto_materia_prima());
            content.put("PRODUTO_TERCERIZACAO", webPedidoItem.getProduto_tercerizacao());
 //           content.put("ID_LINHA_COLECAO", webPedidoItem.getIdLinhaColecao());
            content.put("ID_CAMPANHA", webPedidoItem.getIdCampanha());
            content.put("PRODUTO_PRECO_EDITADO", webPedidoItem.getProduto_preco_editado());
            content.put("ID_SKU", webPedidoItem.getId_sku());
            content.put("NOME_SKU", webPedidoItem.getNome_sku());
            content.put("ICMS_ST_VALOR", webPedidoItem.getIcms_st_valor());
            content.put("ICMS_ST_PERCENTUAL", webPedidoItem.getIcms_st_percentual());
            content.put("ICMS_ST_CALCULA", webPedidoItem.getIcms_st_calcula());
            content.put("ID_GRUPO", webPedidoItem.getId_grupo() == null || webPedidoItem.getId_grupo().isEmpty() ? "0" : webPedidoItem.getId_grupo()  );
            content.put("ID_SUB_GRUPO", webPedidoItem.getId_sub_grupo() == null || webPedidoItem.getId_sub_grupo().isEmpty() ? "0" : webPedidoItem.getId_sub_grupo());
            try {
                content.put("VALOR_DESCONTO_ADIC", webPedidoItem.getValor_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch ( NullPointerException|NumberFormatException e) {
                content.put("VALOR_DESCONTO_ADIC", webPedidoItem.getValor_desconto_adic());
            } catch ( Exception e) {
                content.put("VALOR_DESCONTO_ADIC", webPedidoItem.getValor_desconto_adic());
            }
            try {
                content.put("PERC_DESCONTO_ADIC", webPedidoItem.getPerc_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch ( NullPointerException|NumberFormatException e) {
                content.put("PERC_DESCONTO_ADIC", webPedidoItem.getPerc_desconto_adic());
            } catch ( Exception e) {
                content.put("PERC_DESCONTO_ADIC", webPedidoItem.getPerc_desconto_adic());
            }

            //Campos do grupo

            System.gc();
            return db.addDados("TBL_WEB_PEDIDO_ITENS", content);
             //   return true;
            //return false;
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean update(WebPedidoItens webPedidoItem) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_WEB_ITEM", webPedidoItem.getId_web_item());
            content.put("ID_WEB_ITEM_SERVIDOR", webPedidoItem.getId_web_item_servidor());
            content.put("ID_PEDIDO", webPedidoItem.getId_pedido());
            content.put("ID_PRODUTO", webPedidoItem.getId_produto());
            content.put("ID_EMPRESA", webPedidoItem.getId_empresa());
            content.put("QUANTIDADE", webPedidoItem.getQuantidade());
            //content.put("VALOR_UNITARIO", webPedidoItem.getValor_unitario());
            if ( webPedidoItem.getVenda_preco() != null && !webPedidoItem.getVenda_preco().isEmpty() ) {
                content.put("VALOR_UNITARIO", Float.parseFloat(webPedidoItem.getVenda_preco()) > 0.00f ? Float.parseFloat(webPedidoItem.getVenda_preco()) : webPedidoItem.getValor_unitario());
            } else {
                content.put("VALOR_UNITARIO",  webPedidoItem.getValor_unitario());
            }
            content.put("VALOR_BRUTO", webPedidoItem.getValor_bruto());
            content.put("VALOR_DESCONTO_REAL", webPedidoItem.getValor_desconto_real());
            content.put("VALOR_DESCONTO_PER_ADD", webPedidoItem.getValor_desconto_per_add());
            content.put("VALOR_DESCONTO_REAL_ADD", webPedidoItem.getValor_desconto_real_add());
            content.put("VALOR_TOTAL", webPedidoItem.getValor_total());
            content.put("DATA_MOVIMENTACAO", webPedidoItem.getData_movimentacao());
            content.put("USUARIO_LANCAMENTO_ID", webPedidoItem.getUsuario_lancamento_id());
            content.put("USUARIO_LANCAMENTO_DATA", webPedidoItem.getUsuario_lancamento_data());
            content.put("ID_ITEM_DESCONTO", webPedidoItem.getId_item_desconto());
            content.put("PONTOS_UNITARIO", webPedidoItem.getPontos_unitario());
            content.put("PONTOS_TOTAL", webPedidoItem.getPontos_total());
            content.put("PONTOS_COEFICIENTE", webPedidoItem.getPontos_coeficiente());
            content.put("PONTOS_COR", webPedidoItem.getPontos_cor());
            content.put("COMISSAO_PERCENTUAL", webPedidoItem.getComissao_percentual());
            content.put("COMISSAO_VALOR", webPedidoItem.getComissao_valor());
            content.put("VALOR_BONUS_CREDOR", webPedidoItem.getValor_bonus_credor());
            content.put("PERC_BONUS_CREDOR", webPedidoItem.getPerc_bonus_credor());
            content.put("ID_TABELA_PRECO", webPedidoItem.getId_tabela_preco());
            content.put("VALOR_DESCONTO_PER_ORIG", webPedidoItem.getValor_desconto_per_orig());
            content.put("VALOR_DESCONTO_REAL_ORIG", webPedidoItem.getValor_desconto_real_orig());
            content.put("VALOR_DESCONTO_PER_ADD_ORIG", webPedidoItem.getValor_desconto_per_add_orig());
            content.put("VALOR_DESCONTO_REAL_ADD_ORIG", webPedidoItem.getValor_desconto_real_add_orig());
            content.put("ID_TABELA_PRECO_FAIXA_ORIG", webPedidoItem.getId_tabela_preco_faixa_orig());
            content.put("VALOR_TOTAL_ORIG", webPedidoItem.getValor_total_orig());
            content.put("PONTOS_UNITARIO_ORIG", webPedidoItem.getPontos_unitario_orig());
            content.put("PONTOS_COEFICIENTE_ORIG", webPedidoItem.getPontos_coeficiente_orig());
            content.put("COMISSAO_PERCENTUAL_ORIG", webPedidoItem.getComissao_percentual_orig());
            content.put("VALOR_BONUS_CREDOR_ORIG", webPedidoItem.getValor_bonus_credor_orig());
            content.put("PERC_BONUS_CREDOR_ORIG", webPedidoItem.getPerc_bonus_credor_orig());
            content.put("COMISSAO_VALOR_ORIG", webPedidoItem.getComissao_valor_orig());
            content.put("PONTOS_TOTAL_ORIG", webPedidoItem.getPontos_total_orig());
            content.put("PONTOS_COR_ORIG", webPedidoItem.getPontos_cor_orig());
            content.put("VALOR_PRECO_PAGO", webPedidoItem.getValor_preco_pago());
            content.put("TIPO_DESCONTO", webPedidoItem.getTipoDesconto());
            content.put("VALOR_DESCONTO_PER", webPedidoItem.getValor_desconto_per());
            content.put("NOME_PRODUTO", webPedidoItem.getNome_produto());
            content.put("PRODUTO_MATERIA_PRIMA", webPedidoItem.getProduto_materia_prima());
            content.put("PRODUTO_TERCERIZACAO", webPedidoItem.getProduto_tercerizacao());
//            content.put("ID_LINHA_COLECAO", webPedidoItem.getIdLinhaColecao());
            content.put("ID_CAMPANHA", webPedidoItem.getIdCampanha());
            content.put("PRODUTO_PRECO_EDITADO", webPedidoItem.getProduto_preco_editado());
            content.put("ID_SKU", webPedidoItem.getId_sku());
            content.put("NOME_SKU", webPedidoItem.getNome_sku());
            content.put("ICMS_ST_VALOR", webPedidoItem.getIcms_st_valor());
            content.put("ICMS_ST_PERCENTUAL", webPedidoItem.getIcms_st_percentual());
            content.put("ICMS_ST_CALCULA", webPedidoItem.getIcms_st_calcula());
            try {
                content.put("VALOR_DESCONTO_ADIC", webPedidoItem.getValor_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch ( NullPointerException|NumberFormatException e) {
                content.put("VALOR_DESCONTO_ADIC", webPedidoItem.getValor_desconto_adic());
            } catch ( Exception e) {
                content.put("VALOR_DESCONTO_ADIC", webPedidoItem.getValor_desconto_adic());
            }
            try {
                content.put("PERC_DESCONTO_ADIC", webPedidoItem.getPerc_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch ( NullPointerException|NumberFormatException e) {
                content.put("PERC_DESCONTO_ADIC", webPedidoItem.getPerc_desconto_adic());
            } catch ( Exception e) {
                content.put("PERC_DESCONTO_ADIC", webPedidoItem.getPerc_desconto_adic());
            }
            if (db.updateDados("TBL_WEB_PEDIDO_ITENS", content, "ID_WEB_ITEM = " + webPedidoItem.getId_web_item()) >= 1)
                return true;
            return false;
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAnaliseEstoque(WebPedidoItens webPedidoItem) {
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ANALISE_ESTOQ_RESULTADO", webPedidoItem.getAnalise_estoque_resultado());
            if (db.updateDados("TBL_WEB_PEDIDO_ITENS", content, "ID_WEB_ITEM = " + webPedidoItem.getId_web_item()) >= 1)
                return true;
            return false;
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("Range")
    public List<WebPedidoItens> getLista(String SQL) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        List<WebPedidoItens> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        ProdutoDAO produtoDAO = new ProdutoDAO(db);
        do {
            WebPedidoItens webPedidoItens = new WebPedidoItens();
            try {
                webPedidoItens.setId_web_item(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM")));
                webPedidoItens.setId_web_item_servidor(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM_SERVIDOR")));
                webPedidoItens.setValor_preco_pago(cursor.getString(cursor.getColumnIndex("VALOR_PRECO_PAGO")));
                webPedidoItens.setId_pedido(cursor.getString(cursor.getColumnIndex("ID_PEDIDO")));
                webPedidoItens.setId_produto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
                webPedidoItens.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                webPedidoItens.setQuantidade(cursor.getString(cursor.getColumnIndex("QUANTIDADE")));
                webPedidoItens.setValor_unitario(cursor.getFloat(cursor.getColumnIndex("VALOR_UNITARIO")));
                webPedidoItens.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
                webPedidoItens.setValor_desconto_per(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER")));
                webPedidoItens.setValor_desconto_real(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL")));
                webPedidoItens.setValor_desconto_per_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD")));
                webPedidoItens.setValor_desconto_real_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD")));
                webPedidoItens.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                webPedidoItens.setData_movimentacao(cursor.getString(cursor.getColumnIndex("DATA_MOVIMENTACAO")));
                webPedidoItens.setUsuario_lancamento_id(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_ID")));
                webPedidoItens.setUsuario_lancamento_data(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA")));
                webPedidoItens.setId_item_desconto(cursor.getString(cursor.getColumnIndex("ID_ITEM_DESCONTO")));
                webPedidoItens.setPontos_unitario(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO")));
                webPedidoItens.setPontos_total(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL")));
                webPedidoItens.setPontos_coeficiente(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE")));
                webPedidoItens.setPontos_cor(cursor.getString(cursor.getColumnIndex("PONTOS_COR")));
                webPedidoItens.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
                webPedidoItens.setComissao_valor(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR")));
                webPedidoItens.setValor_bonus_credor(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR")));
                webPedidoItens.setPerc_bonus_credor(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR")));
                webPedidoItens.setId_tabela_preco(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO")));
                webPedidoItens.setValor_desconto_per_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ORIG")));
                webPedidoItens.setValor_desconto_real_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ORIG")));
                webPedidoItens.setValor_desconto_per_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD_ORIG")));
                webPedidoItens.setValor_desconto_real_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD_ORIG")));
                webPedidoItens.setId_tabela_preco_faixa_orig(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA_ORIG")));
                webPedidoItens.setValor_total_orig(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL_ORIG")));
                webPedidoItens.setPontos_unitario_orig(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO_ORIG")));
                webPedidoItens.setPontos_coeficiente_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE_ORIG")));
                webPedidoItens.setComissao_percentual_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL_ORIG")));
                webPedidoItens.setValor_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR_ORIG")));
                webPedidoItens.setPerc_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR_ORIG")));
                webPedidoItens.setComissao_valor_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR_ORIG")));
                webPedidoItens.setPontos_total_orig(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL_ORIG")));
                webPedidoItens.setPontos_cor_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COR_ORIG")));
                webPedidoItens.setTipoDesconto(cursor.getString(cursor.getColumnIndex("TIPO_DESCONTO")));
                webPedidoItens.setProduto_materia_prima(cursor.getString(cursor.getColumnIndex("PRODUTO_MATERIA_PRIMA")));
                webPedidoItens.setProduto_tercerizacao(cursor.getString(cursor.getColumnIndex("PRODUTO_TERCERIZACAO")));
                //webPedidoItens.setIdLinhaColecao( cursor.getInt(cursor.getColumnIndex("ID_LINHA_COLECAO")) );
                webPedidoItens.setIdCampanha(cursor.getInt(cursor.getColumnIndex("ID_CAMPANHA")));
                webPedidoItens.setProduto_edita_valor("N");
                webPedidoItens.setIcms_st_valor(cursor.getString(cursor.getColumnIndex("ICMS_ST_VALOR")));
                webPedidoItens.setIcms_st_calcula(cursor.getString(cursor.getColumnIndex("ICMS_ST_CALCULA")));
                webPedidoItens.setIcms_st_percentual(cursor.getFloat(cursor.getColumnIndex("ICMS_ST_PERCENTUAL")));
                webPedidoItens.setId_grupo(cursor.getString(cursor.getColumnIndex("ID_GRUPO")));
                webPedidoItens.setId_sub_grupo(cursor.getString(cursor.getColumnIndex("ID_SUB_GRUPO")));
                try {
                    webPedidoItens.setProduto_preco_editado(cursor.getFloat(cursor.getColumnIndex("PRODUTO_PRECO_EDITADO")));

                } catch ( NumberFormatException|NullPointerException e) {
                    webPedidoItens.setProduto_preco_editado(0.00f);
                } catch (Exception e) {
                    webPedidoItens.setProduto_preco_editado(0.00f);
                }

                webPedidoItens.setProdutoAtivo(true);
                try {
                    webPedidoItens.setId_sku(cursor.getInt(cursor.getColumnIndex("ID_SKU")));
                    webPedidoItens.setNome_sku(cursor.getString(cursor.getColumnIndex("NOME_SKU")));
                } catch ( NullPointerException e) {
                    webPedidoItens.setId_sku(0);
                    webPedidoItens.setNome_sku("");
                } catch ( Exception e) {
                    webPedidoItens.setId_sku(0);
                    webPedidoItens.setNome_sku("");
                }
                try {  //Caso nao exista no SQL
                    webPedidoItens.setNomeCampanha(cursor.getString(cursor.getColumnIndex("NOME_CAMPANHA")));
                } catch (NullPointerException e) {
                    webPedidoItens.setNomeCampanha("");
                } catch (Exception e) {
                    webPedidoItens.setNomeCampanha("");
                }

                try {
                    webPedidoItens.setValor_desconto_adic(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_ADIC")));
                    webPedidoItens.setPerc_desconto_adic(cursor.getString(cursor.getColumnIndex("PERC_DESCONTO_ADIC")));
                } catch ( NullPointerException e) {
                    webPedidoItens.setValor_desconto_adic("0.00");
                    webPedidoItens.setPerc_desconto_adic("0.00");
                }catch ( Exception e) {
                    webPedidoItens.setValor_desconto_adic("0.00");
                    webPedidoItens.setPerc_desconto_adic("0.00");
                }
                //Cliente clientex = ClienteHelper.getCliente();
                try {
                    try {
                        if ( ClienteHelper.getCliente() == null ) {
                            String SQLP = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA FROM TBL_PRODUTO AS P " +
                                    " LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = 0"   +
                                    " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                    " WHERE P.ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'";
                            Produto produto = produtoDAO.getLista(SQLP, "").get(0);
                            webPedidoItens.setProduto(produto);
                            webPedidoItens.setProdutoBase(true);
                            webPedidoItens.setNome_grupo(produto.getNome_grupo());
                            webPedidoItens.setNome_sub_grupo(produto.getNome_sub_grupo());
                            if ( produto.getProduto_edita_valor().equalsIgnoreCase("S")) {
                                webPedidoItens.setProduto_edita_valor("S");
                                webPedidoItens.setVenda_preco(String.valueOf(cursor.getFloat(cursor.getColumnIndex("PRODUTO_PRECO_EDITADO"))));
                            }

                        } else {
                            String SQLP = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA FROM TBL_PRODUTO AS P " +
                                    " LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                    " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                    " WHERE P.ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'";
                            Produto produto = produtoDAO.getLista(SQLP, "").get(0);
                            webPedidoItens.setProduto(produto);
                            webPedidoItens.setProdutoBase(true);
                            webPedidoItens.setNome_grupo(produto.getNome_grupo());
                            webPedidoItens.setNome_sub_grupo(produto.getNome_sub_grupo());
                            if ( produto.getProduto_edita_valor().equalsIgnoreCase("S")) {
                                webPedidoItens.setProduto_edita_valor("S");
                                webPedidoItens.setVenda_preco(String.valueOf(cursor.getFloat(cursor.getColumnIndex("PRODUTO_PRECO_EDITADO"))));
                            }
                        }
                    } catch ( CursorIndexOutOfBoundsException e) {
                        webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        try {
                            Produto produto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'", "").get(0);
                            webPedidoItens.setProduto(produto);
                            webPedidoItens.setNome_grupo(produto.getNome_grupo());
                            webPedidoItens.setNome_sub_grupo(produto.getNome_sub_grupo());
                            if (produto.getProduto_edita_valor().equalsIgnoreCase("S")) {
                                webPedidoItens.setProduto_edita_valor("S");
                                webPedidoItens.setVenda_preco(String.valueOf(cursor.getFloat(cursor.getColumnIndex("PRODUTO_PRECO_EDITADO"))));
                            }
                        } catch (CursorIndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        } catch (IndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        }
                        webPedidoItens.setProdutoBase(true);
                    }  catch ( IndexOutOfBoundsException e){
                        webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        try {
                            Produto produto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'","").get(0);
                            webPedidoItens.setProduto(produto);
                            webPedidoItens.setNome_grupo(produto.getNome_grupo());
                            webPedidoItens.setNome_sub_grupo(produto.getNome_sub_grupo());
                            if ( produto.getProduto_edita_valor().equalsIgnoreCase("S")) {
                                webPedidoItens.setProduto_edita_valor("S");
                                webPedidoItens.setVenda_preco(String.valueOf(cursor.getFloat(cursor.getColumnIndex("PRODUTO_PRECO_EDITADO"))));
                            }
                        } catch (CursorIndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        } catch (IndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        }
                        webPedidoItens.setProdutoBase(true);
                    } catch (NullPointerException e) {
                        webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        try {
                            Produto produto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'","").get(0);
                            webPedidoItens.setProduto(produto);
                            webPedidoItens.setNome_grupo(produto.getNome_grupo());
                            webPedidoItens.setNome_sub_grupo(produto.getNome_sub_grupo());
                            if ( produto.getProduto_edita_valor().equalsIgnoreCase("S")) {
                                webPedidoItens.setProduto_edita_valor("S");
                                webPedidoItens.setVenda_preco(String.valueOf(cursor.getFloat(cursor.getColumnIndex("PRODUTO_PRECO_EDITADO"))));
                            }
                        } catch ( CursorIndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        } catch ( IndexOutOfBoundsException ex){
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        }
                        webPedidoItens.setProdutoBase(true);
                    }
                } catch (CursorIndexOutOfBoundsException e) {
                    webPedidoItens.setProdutoBase(false);
                    webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                    webPedidoItens.setProdutoAtivo(false);
                    e.printStackTrace();
                }
                try{
                webPedidoItens.setAnalise_estoque_resultado(cursor.getString(cursor.getColumnIndex("ANALISE_ESTOQ_RESULTADO")));
                } catch (NullPointerException|CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                lista.add(webPedidoItens);
            }catch ( CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            //System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        //System.gc();
        return lista;
    }

    @SuppressLint("Range")
    public List<WebPedidoItens> getListaOtimizada(String SQL) {
        List<WebPedidoItens> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }

        // Cache de índices para máxima performance no loop (evita getColumnIndex repetitivo)
        int idxIdWebItem = cursor.getColumnIndex("ID_WEB_ITEM");
        int idxIdWebItemServidor = cursor.getColumnIndex("ID_WEB_ITEM_SERVIDOR");
        int idxValorPrecoPago = cursor.getColumnIndex("VALOR_PRECO_PAGO");
        int idxIdPedido = cursor.getColumnIndex("ID_PEDIDO");
        int idxIdProduto = cursor.getColumnIndex("ID_PRODUTO");
        int idxIdEmpresa = cursor.getColumnIndex("ID_EMPRESA");
        int idxQuantidade = cursor.getColumnIndex("QUANTIDADE");
        int idxValorUnitario = cursor.getColumnIndex("VALOR_UNITARIO");
        int idxValorBruto = cursor.getColumnIndex("VALOR_BRUTO");
        int idxValorDescontoPer = cursor.getColumnIndex("VALOR_DESCONTO_PER");
        int idxValorDescontoReal = cursor.getColumnIndex("VALOR_DESCONTO_REAL");
        int idxValorDescontoPerAdd = cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD");
        int idxValorDescontoRealAdd = cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD");
        int idxValorTotal = cursor.getColumnIndex("VALOR_TOTAL");
        int idxDataMovimentacao = cursor.getColumnIndex("DATA_MOVIMENTACAO");
        int idxUsuarioLancamentoId = cursor.getColumnIndex("USUARIO_LANCAMENTO_ID");
        int idxUsuarioLancamentoData = cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA");
        int idxIdItemDesconto = cursor.getColumnIndex("ID_ITEM_DESCONTO");
        int idxPontosUnitario = cursor.getColumnIndex("PONTOS_UNITARIO");
        int idxPontosTotal = cursor.getColumnIndex("PONTOS_TOTAL");
        int idxPontosCoeficiente = cursor.getColumnIndex("PONTOS_COEFICIENTE");
        int idxPontosCor = cursor.getColumnIndex("PONTOS_COR");
        int idxComissaoPercentual = cursor.getColumnIndex("COMISSAO_PERCENTUAL");
        int idxComissaoValor = cursor.getColumnIndex("COMISSAO_VALOR");
        int idxValorBonusCredor = cursor.getColumnIndex("VALOR_BONUS_CREDOR");
        int idxPercBonusCredor = cursor.getColumnIndex("PERC_BONUS_CREDOR");
        int idxIdTabelaPreco = cursor.getColumnIndex("ID_TABELA_PRECO");
        int idxValorDescontoPerOrig = cursor.getColumnIndex("VALOR_DESCONTO_PER_ORIG");
        int idxValorDescontoRealOrig = cursor.getColumnIndex("VALOR_DESCONTO_REAL_ORIG");
        int idxValorDescontoPerAddOrig = cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD_ORIG");
        int idxValorDescontoRealAddOrig = cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD_ORIG");
        int idxIdTabelaPrecoFaixaOrig = cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA_ORIG");
        int idxValorTotalOrig = cursor.getColumnIndex("VALOR_TOTAL_ORIG");
        int idxPontosUnitarioOrig = cursor.getColumnIndex("PONTOS_UNITARIO_ORIG");
        int idxPontosCoeficienteOrig = cursor.getColumnIndex("PONTOS_COEFICIENTE_ORIG");
        int idxComissaoPercentualOrig = cursor.getColumnIndex("COMISSAO_PERCENTUAL_ORIG");
        int idxValorBonusCredorOrig = cursor.getColumnIndex("VALOR_BONUS_CREDOR_ORIG");
        int idxPercBonusCredorOrig = cursor.getColumnIndex("PERC_BONUS_CREDOR_ORIG");
        int idxComissaoValorOrig = cursor.getColumnIndex("COMISSAO_VALOR_ORIG");
        int idxPontosTotalOrig = cursor.getColumnIndex("PONTOS_TOTAL_ORIG");
        int idxPontosCorOrig = cursor.getColumnIndex("PONTOS_COR_ORIG");
        int idxTipoDesconto = cursor.getColumnIndex("TIPO_DESCONTO");
        int idxProdMateriaPrima = cursor.getColumnIndex("PRODUTO_MATERIA_PRIMA");
        int idxProdTercerizacao = cursor.getColumnIndex("PRODUTO_TERCERIZACAO");
        int idxIdCampanha = cursor.getColumnIndex("ID_CAMPANHA");
        int idxIcmsStValor = cursor.getColumnIndex("ICMS_ST_VALOR");
        int idxIcmsStCalcula = cursor.getColumnIndex("ICMS_ST_CALCULA");
        int idxIcmsStPercentual = cursor.getColumnIndex("ICMS_ST_PERCENTUAL");
        int idxIdGrupo = cursor.getColumnIndex("ID_GRUPO");
        int idxIdSubGrupo = cursor.getColumnIndex("ID_SUB_GRUPO");
        int idxProdPrecoEditado = cursor.getColumnIndex("PRODUTO_PRECO_EDITADO");
        int idxIdSku = cursor.getColumnIndex("ID_SKU");
        int idxNomeSku = cursor.getColumnIndex("NOME_SKU");
        int idxNomeCampanha = cursor.getColumnIndex("NOME_CAMPANHA");
        int idxValorDescontoAdic = cursor.getColumnIndex("VALOR_DESCONTO_ADIC");
        int idxPercDescontoAdic = cursor.getColumnIndex("PERC_DESCONTO_ADIC");
        int idxAnaliseEstoque = cursor.getColumnIndex("ANALISE_ESTOQ_RESULTADO");
        int idxNomeProduto = cursor.getColumnIndex("NOME_PRODUTO");

        // Índices do Produto (Esperados do JOIN na query principal)
        int idxP_NomeProduto = cursor.getColumnIndex("P_NOME_PRODUTO"); // Alias sugerido para evitar colisão
        int idxP_IdLinhaColecao = cursor.getColumnIndex("ID_LINHA_COLECAO_P");
        int idxP_Unidade = cursor.getColumnIndex("UNIDADE_P");
        int idxP_VendaPreco = cursor.getColumnIndex("VENDA_PRECO_P");
        int idxP_Ativo = cursor.getColumnIndex("ATIVO_P");
        int idxP_NomeSubGrupo = cursor.getColumnIndex("NOME_SUB_GRUPO_P");
        int idxP_NomeGrupo = cursor.getColumnIndex("NOME_GRUPO_P");
        int idxP_ProdEditaPreco = cursor.getColumnIndex("PRODUTO_EDITA_PRECO_APP_P");
        int idxP_StAliquota = cursor.getColumnIndex("PRODUTO_ST_ALIQUOTA_P");
        int idxP_SujIcmsSt = cursor.getColumnIndex("PRODUTO_SUJ_ICMS_ST_P");
        int idxP_PrecoTabela = cursor.getColumnIndex("PRECO_TABELA_CLIENTE");
        int idxP_NomeTabela = cursor.getColumnIndex("NOME_TABELA");
        int idxP_CodigoEmBarras = cursor.getColumnIndex("CODIGO_EM_BARRAS");

        cursor.moveToFirst();
        do {
            WebPedidoItens webPedidoItens = new WebPedidoItens();
            try {
                if (idxIdWebItem != -1) webPedidoItens.setId_web_item(cursor.getString(idxIdWebItem));
                if (idxIdWebItemServidor != -1) webPedidoItens.setId_web_item_servidor(cursor.getString(idxIdWebItemServidor));
                if (idxValorPrecoPago != -1) webPedidoItens.setValor_preco_pago(cursor.getString(idxValorPrecoPago));
                if (idxIdPedido != -1) webPedidoItens.setId_pedido(cursor.getString(idxIdPedido));
                if (idxIdProduto != -1) webPedidoItens.setId_produto(cursor.getString(idxIdProduto));
                if (idxIdEmpresa != -1) webPedidoItens.setId_empresa(cursor.getString(idxIdEmpresa));
                if (idxQuantidade != -1) webPedidoItens.setQuantidade(cursor.getString(idxQuantidade));
                if (idxValorUnitario != -1) webPedidoItens.setValor_unitario(cursor.getFloat(idxValorUnitario));
                if (idxValorBruto != -1) webPedidoItens.setValor_bruto(cursor.getString(idxValorBruto));
                if (idxValorDescontoPer != -1) webPedidoItens.setValor_desconto_per(cursor.getString(idxValorDescontoPer));
                if (idxValorDescontoReal != -1) webPedidoItens.setValor_desconto_real(cursor.getString(idxValorDescontoReal));
                if (idxValorDescontoPerAdd != -1) webPedidoItens.setValor_desconto_per_add(cursor.getString(idxValorDescontoPerAdd));
                if (idxValorDescontoRealAdd != -1) webPedidoItens.setValor_desconto_real_add(cursor.getString(idxValorDescontoRealAdd));
                if (idxValorTotal != -1) webPedidoItens.setValor_total(cursor.getString(idxValorTotal));
                if (idxDataMovimentacao != -1) webPedidoItens.setData_movimentacao(cursor.getString(idxDataMovimentacao));
                if (idxUsuarioLancamentoId != -1) webPedidoItens.setUsuario_lancamento_id(cursor.getString(idxUsuarioLancamentoId));
                if (idxUsuarioLancamentoData != -1) webPedidoItens.setUsuario_lancamento_data(cursor.getString(idxUsuarioLancamentoData));
                if (idxIdItemDesconto != -1) webPedidoItens.setId_item_desconto(cursor.getString(idxIdItemDesconto));
                if (idxPontosUnitario != -1) webPedidoItens.setPontos_unitario(cursor.getString(idxPontosUnitario));
                if (idxPontosTotal != -1) webPedidoItens.setPontos_total(cursor.getString(idxPontosTotal));
                if (idxPontosCoeficiente != -1) webPedidoItens.setPontos_coeficiente(cursor.getString(idxPontosCoeficiente));
                if (idxPontosCor != -1) webPedidoItens.setPontos_cor(cursor.getString(idxPontosCor));
                if (idxComissaoPercentual != -1) webPedidoItens.setComissao_percentual(cursor.getString(idxComissaoPercentual));
                if (idxComissaoValor != -1) webPedidoItens.setComissao_valor(cursor.getString(idxComissaoValor));
                if (idxValorBonusCredor != -1) webPedidoItens.setValor_bonus_credor(cursor.getString(idxValorBonusCredor));
                if (idxPercBonusCredor != -1) webPedidoItens.setPerc_bonus_credor(cursor.getString(idxPercBonusCredor));
                if (idxIdTabelaPreco != -1) webPedidoItens.setId_tabela_preco(cursor.getString(idxIdTabelaPreco));
                if (idxValorDescontoPerOrig != -1) webPedidoItens.setValor_desconto_per_orig(cursor.getString(idxValorDescontoPerOrig));
                if (idxValorDescontoRealOrig != -1) webPedidoItens.setValor_desconto_real_orig(cursor.getString(idxValorDescontoRealOrig));
                if (idxValorDescontoPerAddOrig != -1) webPedidoItens.setValor_desconto_per_add_orig(cursor.getString(idxValorDescontoPerAddOrig));
                if (idxValorDescontoRealAddOrig != -1) webPedidoItens.setValor_desconto_real_add_orig(cursor.getString(idxValorDescontoRealAddOrig));
                if (idxIdTabelaPrecoFaixaOrig != -1) webPedidoItens.setId_tabela_preco_faixa_orig(cursor.getString(idxIdTabelaPrecoFaixaOrig));
                if (idxValorTotalOrig != -1) webPedidoItens.setValor_total_orig(cursor.getString(idxValorTotalOrig));
                if (idxPontosUnitarioOrig != -1) webPedidoItens.setPontos_unitario_orig(cursor.getString(idxPontosUnitarioOrig));
                if (idxPontosCoeficienteOrig != -1) webPedidoItens.setPontos_coeficiente_orig(cursor.getString(idxPontosCoeficienteOrig));
                if (idxComissaoPercentualOrig != -1) webPedidoItens.setComissao_percentual_orig(cursor.getString(idxComissaoPercentualOrig));
                if (idxValorBonusCredorOrig != -1) webPedidoItens.setValor_bonus_credor_orig(cursor.getString(idxValorBonusCredorOrig));
                if (idxPercBonusCredorOrig != -1) webPedidoItens.setPerc_bonus_credor_orig(cursor.getString(idxPercBonusCredorOrig));
                if (idxComissaoValorOrig != -1) webPedidoItens.setComissao_valor_orig(cursor.getString(idxComissaoValorOrig));
                if (idxPontosTotalOrig != -1) webPedidoItens.setPontos_total_orig(cursor.getString(idxPontosTotalOrig));
                if (idxPontosCorOrig != -1) webPedidoItens.setPontos_cor_orig(cursor.getString(idxPontosCorOrig));
                if (idxTipoDesconto != -1) webPedidoItens.setTipoDesconto(cursor.getString(idxTipoDesconto));
                if (idxP_CodigoEmBarras != -1) webPedidoItens.setCodigo_em_barras(cursor.getString(idxP_CodigoEmBarras));
                //if (idxProdMateriaPrima != -1) {
                    webPedidoItens.setProduto_materia_prima(cursor.getString(idxProdMateriaPrima) == null ? "N" : cursor.getString(idxProdMateriaPrima));
                //}
                //if (idxProdTercerizacao != -1) {
                    webPedidoItens.setProduto_tercerizacao(cursor.getString(idxProdTercerizacao) == null ? "N" : cursor.getString(idxProdTercerizacao));
                //
                // }
                if ( webPedidoItens.getProduto_materia_prima() == null) {
                    webPedidoItens.setProduto_materia_prima("N");
                }
                if ( webPedidoItens.getProduto_tercerizacao() == null) {
                    webPedidoItens.setProduto_tercerizacao("N");
                }
                if (idxIdCampanha != -1) webPedidoItens.setIdCampanha(cursor.getInt(idxIdCampanha));
                if (idxIcmsStValor != -1) webPedidoItens.setIcms_st_valor(cursor.getString(idxIcmsStValor));
                if (idxIcmsStCalcula != -1) webPedidoItens.setIcms_st_calcula(cursor.getString(idxIcmsStCalcula));
                if (idxIcmsStPercentual != -1) webPedidoItens.setIcms_st_percentual(cursor.getFloat(idxIcmsStPercentual));
                if (idxIdGrupo != -1) webPedidoItens.setId_grupo(cursor.getString(idxIdGrupo));
                if (idxIdSubGrupo != -1) webPedidoItens.setId_sub_grupo(cursor.getString(idxIdSubGrupo));
                if (idxProdPrecoEditado != -1) webPedidoItens.setProduto_preco_editado(cursor.getFloat(idxProdPrecoEditado));
                if (idxIdSku != -1) webPedidoItens.setId_sku(cursor.getInt(idxIdSku));
                if (idxNomeSku != -1) webPedidoItens.setNome_sku(cursor.getString(idxNomeSku));
                if (idxNomeCampanha != -1) webPedidoItens.setNomeCampanha(cursor.getString(idxNomeCampanha));
                if (idxValorDescontoAdic != -1) webPedidoItens.setValor_desconto_adic(cursor.getString(idxValorDescontoAdic));
                if (idxPercDescontoAdic != -1) webPedidoItens.setPerc_desconto_adic(cursor.getString(idxPercDescontoAdic));
                if (idxAnaliseEstoque != -1) webPedidoItens.setAnalise_estoque_resultado(cursor.getString(idxAnaliseEstoque));
                
                webPedidoItens.setProduto_edita_valor("N");
                webPedidoItens.setProdutoAtivo(true);
                webPedidoItens.setProdutoBase(true);

                // Preenchimento Otimizado do Produto (Evita nova consulta ao banco por item)
                if (idxP_NomeProduto != -1 && !cursor.isNull(idxP_NomeProduto)) {
                    Produto produto = new Produto();
                    produto.setId_produto(webPedidoItens.getId_produto());
                    produto.setNome_produto(cursor.getString(idxP_NomeProduto));
                    if (idxP_IdLinhaColecao != -1) produto.setIdLinhaColecao(cursor.getInt(idxP_IdLinhaColecao));
                    if (idxP_Unidade != -1) produto.setUnidade(cursor.getString(idxP_Unidade));
                    if (idxP_VendaPreco != -1) produto.setVenda_preco(cursor.getString(idxP_VendaPreco));
                    if (idxP_Ativo != -1) produto.setAtivo(cursor.getString(idxP_Ativo));
                    if (idxP_NomeSubGrupo != -1) produto.setNome_sub_grupo(cursor.getString(idxP_NomeSubGrupo));
                    if (idxP_NomeGrupo != -1) produto.setNome_grupo(cursor.getString(idxP_NomeGrupo));
                    if (idxP_ProdEditaPreco != -1) produto.setProduto_edita_valor(cursor.getString(idxP_ProdEditaPreco));
                    if (idxP_StAliquota != -1) produto.setProduto_st_aliquota(cursor.getFloat(idxP_StAliquota));
                    if (idxP_SujIcmsSt != -1) produto.setProduto_suj_icms_st(cursor.getString(idxP_SujIcmsSt));
                    if (idxP_CodigoEmBarras != -1) produto.setCodigo_em_barras(cursor.getString(idxP_CodigoEmBarras));

                    // Lógica de Tabela de Preço integrada
                    if (idxP_PrecoTabela != -1 && !cursor.isNull(idxP_PrecoTabela) && cursor.getFloat(idxP_PrecoTabela) > 0.0f) {
                        produto.setVenda_preco(cursor.getString(idxP_PrecoTabela));
                        if (idxP_NomeTabela != -1) produto.setNome_tabela_preco(cursor.getString(idxP_NomeTabela));
                    }

                    webPedidoItens.setProduto(produto);
                    webPedidoItens.setNome_grupo(produto.getNome_grupo());
                    webPedidoItens.setNome_sub_grupo(produto.getNome_sub_grupo());
                    
                    if ("S".equalsIgnoreCase(produto.getProduto_edita_valor())) {
                        webPedidoItens.setProduto_edita_valor("S");
                        webPedidoItens.setVenda_preco(String.valueOf(webPedidoItens.getProduto_preco_editado()));
                    }
                } else {
                    // Fallback para quando o produto não foi encontrado via JOIN
                    if (idxNomeProduto != -1) webPedidoItens.setNome_produto(cursor.getString(idxNomeProduto));
                    webPedidoItens.setProdutoBase(false);
                    webPedidoItens.setProdutoAtivo(false);
                }

                lista.add(webPedidoItens);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        
        cursor.close();
        return lista;
    }

    public boolean delete(Context context, List<WebPedidoItens> webPedidoItens) {
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

    public boolean delete(Context context, WebPedidoItens webPedidoIten) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        db = new DBHelper(context);
        try {
            if( db.deleteDados( "TBL_WEB_PEDIDO_ITENS", "ID_PEDIDO =? AND ID_WEB_ITEM=?", new String[]{webPedidoIten.getId_pedido(), webPedidoIten.getId_web_item().trim()} ) >= 1)
                return true;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<WebPedidoItens> getListaDuplicacao(String SQL, Configuracao configuracao, EmpresaParametro empresaParametro, Float saldoVerba,
                                                   Cliente cliente, CampanhaComercialCabDAO campanhaComercialCabDAO) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        List<WebPedidoItens> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        ProdutoDAO produtoDAO = new ProdutoDAO(db);
        Produto produto = new Produto();
        do {
            WebPedidoItens webPedidoItens = new WebPedidoItens();

            try {
                webPedidoItens.setId_web_item(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM")));
                webPedidoItens.setId_web_item_servidor(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM_SERVIDOR")));
                webPedidoItens.setValor_preco_pago(cursor.getString(cursor.getColumnIndex("VALOR_PRECO_PAGO")));
                webPedidoItens.setId_pedido(cursor.getString(cursor.getColumnIndex("ID_PEDIDO")));
                webPedidoItens.setId_produto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
                webPedidoItens.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                webPedidoItens.setQuantidade(cursor.getString(cursor.getColumnIndex("QUANTIDADE")));
                webPedidoItens.setValor_unitario(cursor.getFloat(cursor.getColumnIndex("VALOR_UNITARIO")));
                webPedidoItens.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
                webPedidoItens.setValor_desconto_per(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER")));
                webPedidoItens.setValor_desconto_real(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL")));
                webPedidoItens.setValor_desconto_per_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD")));
                webPedidoItens.setValor_desconto_real_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD")));
                webPedidoItens.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                webPedidoItens.setData_movimentacao(cursor.getString(cursor.getColumnIndex("DATA_MOVIMENTACAO")));
                webPedidoItens.setUsuario_lancamento_id(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_ID")));
                webPedidoItens.setUsuario_lancamento_data(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA")));
                webPedidoItens.setId_item_desconto(cursor.getString(cursor.getColumnIndex("ID_ITEM_DESCONTO")));
                webPedidoItens.setPontos_unitario(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO")));
                webPedidoItens.setPontos_total(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL")));
                webPedidoItens.setPontos_coeficiente(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE")));
                webPedidoItens.setPontos_cor(cursor.getString(cursor.getColumnIndex("PONTOS_COR")));
                webPedidoItens.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
                webPedidoItens.setComissao_valor(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR")));
                webPedidoItens.setValor_bonus_credor(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR")));
                webPedidoItens.setPerc_bonus_credor(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR")));
                webPedidoItens.setId_tabela_preco(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO")));
                webPedidoItens.setValor_desconto_per_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ORIG")));
                webPedidoItens.setValor_desconto_real_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ORIG")));
                webPedidoItens.setValor_desconto_per_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD_ORIG")));
                webPedidoItens.setValor_desconto_real_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD_ORIG")));
                webPedidoItens.setId_tabela_preco_faixa_orig(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA_ORIG")));
                webPedidoItens.setValor_total_orig(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL_ORIG")));
                webPedidoItens.setPontos_unitario_orig(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO_ORIG")));
                webPedidoItens.setPontos_coeficiente_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE_ORIG")));
                webPedidoItens.setComissao_percentual_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL_ORIG")));
                webPedidoItens.setValor_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR_ORIG")));
                webPedidoItens.setPerc_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR_ORIG")));
                webPedidoItens.setComissao_valor_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR_ORIG")));
                webPedidoItens.setPontos_total_orig(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL_ORIG")));
                webPedidoItens.setPontos_cor_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COR_ORIG")));
                webPedidoItens.setTipoDesconto(cursor.getString(cursor.getColumnIndex("TIPO_DESCONTO")));
                webPedidoItens.setProduto_materia_prima(cursor.getString(cursor.getColumnIndex("PRODUTO_MATERIA_PRIMA")));
                webPedidoItens.setProduto_tercerizacao(cursor.getString(cursor.getColumnIndex("PRODUTO_TERCERIZACAO")));
                //webPedidoItens.setIdLinhaColecao( cursor.getInt(cursor.getColumnIndex("ID_LINHA_COLECAO")) );
                webPedidoItens.setIdCampanha(cursor.getInt(cursor.getColumnIndex("ID_CAMPANHA")));
                //webPedidoItens.setNomeCampanha(cursor.getString(cursor.getColumnIndex("NOME_CAMPANHA")));
                webPedidoItens.setProduto_edita_valor("N");
                webPedidoItens.setIcms_st_valor(cursor.getString(cursor.getColumnIndex("ICMS_ST_VALOR")));
                webPedidoItens.setIcms_st_calcula(cursor.getString(cursor.getColumnIndex("ICMS_ST_CALCULA")));
                webPedidoItens.setIcms_st_percentual(cursor.getFloat(cursor.getColumnIndex("ICMS_ST_PERCENTUAL")));
                try {
                    webPedidoItens.setProduto_preco_editado(cursor.getFloat(cursor.getColumnIndex("PRODUTO_PRECO_EDITADO")));
                } catch ( NumberFormatException|NullPointerException e) {
                    webPedidoItens.setProduto_preco_editado(0.00f);
                } catch (Exception e) {
                    webPedidoItens.setProduto_preco_editado(0.00f);
                }

                webPedidoItens.setProdutoAtivo(true);
                try {
                    webPedidoItens.setId_sku(cursor.getInt(cursor.getColumnIndex("ID_SKU")));
                    webPedidoItens.setNome_sku(cursor.getString(cursor.getColumnIndex("NOME_SKU")));
                } catch ( NullPointerException e) {
                    webPedidoItens.setId_sku(0);
                    webPedidoItens.setNome_sku("");
                } catch ( Exception e) {
                    webPedidoItens.setId_sku(0);
                    webPedidoItens.setNome_sku("");
                }


                try {
                    webPedidoItens.setValor_desconto_adic(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_ADIC")));
                    webPedidoItens.setPerc_desconto_adic(cursor.getString(cursor.getColumnIndex("PERC_DESCONTO_ADIC")));
                } catch ( NullPointerException e) {
                    webPedidoItens.setValor_desconto_adic("0.00");
                    webPedidoItens.setPerc_desconto_adic("0.00");
                }catch ( Exception e) {
                    webPedidoItens.setValor_desconto_adic("0.00");
                    webPedidoItens.setPerc_desconto_adic("0.00");
                }

                try {
                    try {
                        String SQLP = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA FROM TBL_PRODUTO AS P " +
                                " LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                " WHERE P.ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'";
                        produto = produtoDAO.getLista(SQLP,"").get(0);
                        webPedidoItens.setProduto(produto);
                        webPedidoItens.setProdutoBase(true);

                        if ( produto.getProduto_edita_valor().equalsIgnoreCase("S")) {
                            webPedidoItens.setProduto_edita_valor("S");
                            //novo ajuste 18-05-2026
                            if ( webPedidoItens.getProduto_preco_editado() != 0.00f) {
                                try {
                                    webPedidoItens.setVenda_preco(webPedidoItens.getProduto_preco_editado().toString());
                                } catch (NullPointerException|NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //if ( webPedidoItens.getValor_unitario() != Float.parseFloat(produto.getVenda_preco())){
                            webPedidoItens = calculaValorItem(produto, webPedidoItens, configuracao, empresaParametro, saldoVerba);
                        //}
                    } catch ( CursorIndexOutOfBoundsException e) {
                        try {
                            produto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'","").get(0);
                            webPedidoItens.setProduto(produto);
                            if ( webPedidoItens.getValor_unitario() != Float.parseFloat(produto.getVenda_preco())){
                                webPedidoItens = calculaValorItem(produto, webPedidoItens, configuracao, empresaParametro, saldoVerba);
                            }
                            if ( produto.getProduto_edita_valor().equalsIgnoreCase("S"))
                                webPedidoItens.setProduto_edita_valor("S");
                        } catch (CursorIndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        } catch (IndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        }
                        webPedidoItens.setProdutoBase(true);
                    }  catch ( IndexOutOfBoundsException e){
                        try {
                            produto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'","").get(0);
                            webPedidoItens.setProduto(produto);

                            webPedidoItens = calculaValorItem(produto, webPedidoItens, configuracao, empresaParametro, saldoVerba);

                            if ( produto.getProduto_edita_valor().equalsIgnoreCase("S"))
                                webPedidoItens.setProduto_edita_valor("S");
                        } catch (CursorIndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        } catch (IndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        }
                        webPedidoItens.setProdutoBase(true);
                    } catch (NullPointerException e) {
                        try {
                            produto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'", "").get(0);
                            webPedidoItens.setProduto(produto);

                            webPedidoItens = calculaValorItem(produto, webPedidoItens, configuracao, empresaParametro, saldoVerba);

                            if (produto.getProduto_edita_valor().equalsIgnoreCase("S"))
                                webPedidoItens.setProduto_edita_valor("S");
                        } catch (CursorIndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        } catch (IndexOutOfBoundsException ex) {
                            webPedidoItens.setProdutoAtivo(false);
                            webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                        }
                        webPedidoItens.setProdutoBase(true);
                    }
                } catch (CursorIndexOutOfBoundsException e) {
                    webPedidoItens.setProdutoBase(false);
                    webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                    webPedidoItens.setProdutoAtivo(false);
                    e.printStackTrace();
                }

                try {
                    if (configuracao.getTrabalha_com_campanhas().equalsIgnoreCase("S")) {
                        try {
                            List<CampanhaComercialCab> listaCampanha = campanhaComercialCabDAO.getLista( String.valueOf(cliente.getId_cadastro_servidor()), produto.getId_produto(), String.valueOf(produto.getIdLinhaColecao()), webPedidoItens.getIdCampanha());
                            if (listaCampanha.size() > 0) {
                                webPedidoItens.setIdCampanha(listaCampanha.get(0).getIdCampanha());
                                webPedidoItens.setNomeCampanha(listaCampanha.get(0).getNomeCampanha());
                            } else {
                                webPedidoItens.setNomeCampanha("");
                                webPedidoItens.setIdCampanha(0);
                            }
                        } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
                            webPedidoItens.setNomeCampanha("");
                            webPedidoItens.setIdCampanha(0);
                        } catch ( Exception e) {
                           e.printStackTrace();
                        }
                    } else {
                        webPedidoItens.setNomeCampanha("");
                        webPedidoItens.setIdCampanha(0);
                    }
                } catch ( NullPointerException ex) {
                } catch ( Exception ex) {
                }



                lista.add(webPedidoItens);
            }catch ( CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            //System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        //System.gc();
        return lista;
    }

    private WebPedidoItens calculaValorItem(Produto produto, WebPedidoItens webPedidoItens, Configuracao configuracao, EmpresaParametro empresaParametro, Float saldoVerba) {

        Float totalProdutoBruto = 0.0f;
        Float desconto = 0.0f;
        Float descontoVerba = 0.0f;
        Float valorIcmsST = 0.0f;
        BigDecimal bdesconto = null;
        BigDecimal bDescontoVerba = null;
        BigDecimal btotalBruto = null;
        BigDecimal bValorIcmsST = null;
        try {
            btotalBruto = new BigDecimal(Float.parseFloat(webPedidoItens.getQuantidade()) * Float.parseFloat(produto.getVenda_preco())).setScale(2, RoundingMode.HALF_EVEN);
        } catch ( NullPointerException e) {
            e.printStackTrace();
            try {
                if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                    btotalBruto = new BigDecimal(Float.parseFloat(webPedidoItens.getQuantidade()) * webPedidoItens.getProduto_preco_editado()).setScale(2, RoundingMode.HALF_EVEN);
                }
            } catch ( NullPointerException ex) {
               ex.printStackTrace();
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (Float.parseFloat(produto.getVenda_preco()) == 0.00f) {
                try {
                    if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                        try {
                            if ( webPedidoItens.getProduto_preco_editado() <= 0.0f){
                                webPedidoItens.setProduto_preco_editado(webPedidoItens.getValor_unitario());
                            }
                        } catch ( NumberFormatException|NullPointerException e) {
                        }
                        btotalBruto = new BigDecimal(Float.parseFloat(webPedidoItens.getQuantidade()) * webPedidoItens.getProduto_preco_editado()).setScale(2, RoundingMode.HALF_EVEN);
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        } catch ( NullPointerException e) {

        }
        totalProdutoBruto = Float.parseFloat(String.valueOf(btotalBruto));
        if ( webPedidoItens.getValor_desconto_per() == null ){
            webPedidoItens.setValor_desconto_per("0");
        }
        if ( webPedidoItens.getPerc_desconto_adic() == null) {
            webPedidoItens.setPerc_desconto_adic("0");
        }
        if ( !webPedidoItens.getValor_desconto_per().equalsIgnoreCase("0")) {
            bdesconto = new BigDecimal((Float.parseFloat(webPedidoItens.getValor_desconto_per()) * (totalProdutoBruto)) / 100).setScale(3, RoundingMode.HALF_EVEN);
            int tamanho = String.valueOf(bdesconto).length();

            if (String.valueOf(bdesconto).substring(tamanho - 1).equalsIgnoreCase("5")) {
                if (((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                    bdesconto = new BigDecimal((Float.parseFloat(webPedidoItens.getValor_desconto_per()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.DOWN);
                } else {
                    bdesconto = new BigDecimal((Float.parseFloat(webPedidoItens.getValor_desconto_per()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.UP);
                }
            } else {
                bdesconto = new BigDecimal((Float.parseFloat(webPedidoItens.getValor_desconto_per()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.HALF_EVEN);
            }

            //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(3, RoundingMode.DOWN);
            try {
                desconto = Float.parseFloat(String.valueOf(bdesconto));
            } catch (NullPointerException | NumberFormatException e) {

            } catch (Exception e) {

            }
            //edtDescontoReais.setText(MascaraUtil.duasCasaDecimal(desconto));
            if ( saldoVerba > 0.00f) {
                if (!webPedidoItens.getPerc_desconto_adic().equalsIgnoreCase("0")) {
                    bDescontoVerba = new BigDecimal((Float.parseFloat(webPedidoItens.getPerc_desconto_adic()) * (totalProdutoBruto - desconto)) / 100).setScale(3, RoundingMode.HALF_EVEN);
                    tamanho = String.valueOf(bDescontoVerba).length();
                    if (String.valueOf(bDescontoVerba).substring(tamanho - 1).equalsIgnoreCase("5")) {
                        if (((Integer.parseInt(String.valueOf(bDescontoVerba).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                            bDescontoVerba = new BigDecimal((Float.parseFloat(webPedidoItens.getPerc_desconto_adic()) * (totalProdutoBruto - desconto)) / 100).setScale(2, RoundingMode.DOWN);
                        } else {
                            bDescontoVerba = new BigDecimal((Float.parseFloat(webPedidoItens.getPerc_desconto_adic()) * (totalProdutoBruto - desconto)) / 100).setScale(2, RoundingMode.UP);
                        }
                    } else {
                        bDescontoVerba = new BigDecimal((Float.parseFloat(webPedidoItens.getPerc_desconto_adic()) * (totalProdutoBruto - desconto) ) / 100).setScale(2, RoundingMode.HALF_EVEN);
                    }
                    //bDescontoVerba = new BigDecimal((Float.parseFloat(edtDescontoVerba.getText().toString()) * (totalProdutoBruto - desconto)) / 100).setScale(3, RoundingMode.DOWN);
                }
                try {
                    descontoVerba = Float.parseFloat(String.valueOf(bDescontoVerba));
                } catch (NullPointerException | NumberFormatException e) {

                } catch (Exception e) {

                }
            } else {
                webPedidoItens.setPerc_desconto_adic("0");
            }
            try {
                if (configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                    if (webPedidoItens.getIcms_st_calcula().equalsIgnoreCase("S")) {
                        if (webPedidoItens.getIcms_st_percentual() > 0.00f) {
                            bValorIcmsST = new BigDecimal((webPedidoItens.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(4, RoundingMode.HALF_EVEN);
                            tamanho = String.valueOf(bValorIcmsST).length();
                            if ((String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1)).trim().equalsIgnoreCase("5")) {
                                if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                    bValorIcmsST = new BigDecimal((webPedidoItens.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.UP);
                                } else {
                                    bValorIcmsST = new BigDecimal((webPedidoItens.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.DOWN /*RoundingMode.HALF_EVEN*/);
                                }
                            } else {
                                bValorIcmsST = new BigDecimal((webPedidoItens.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                            }
                            //bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(3, RoundingMode.DOWN);
                        }
                    }
                }
            } catch (NullPointerException | NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                valorIcmsST = Float.parseFloat(String.valueOf(bValorIcmsST));
                //edtvalorIcmsST.setText(String.valueOf(valorIcmsST));
                webPedidoItens.setIcms_st_valor(String.valueOf(valorIcmsST));
            } catch (NullPointerException | NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
            if ( !edtDescontoVerba.getText().toString().isEmpty()) {
                edtTotal.setText(MascaraUtil.mascaraVirgula((totalProdutoBruto + valorIcmsST) - (desconto + descontoVerba)));
            } else {
                edtTotal.setText(MascaraUtil.mascaraVirgula((totalProdutoBruto + valorIcmsST) - desconto));
            }

             */
        }
        try {
            webPedidoItens.setValor_unitario(Float.parseFloat(produto.getVenda_preco()));
        } catch ( NullPointerException e) {
            try {
                if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                    webPedidoItens.setValor_unitario(webPedidoItens.getProduto_preco_editado());
                }
            } catch ( NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        try {
            if (Float.parseFloat(produto.getVenda_preco()) == 0.00f || Float.parseFloat(produto.getVenda_preco()) == 0.0f) {
                try {
                    if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                        webPedidoItens.setValor_unitario(webPedidoItens.getProduto_preco_editado());
                    }
                } catch (NullPointerException ex) {
                     ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        }

        webPedidoItens.setIcms_st_percentual(produto.getProduto_st_aliquota());
        webPedidoItens.setIcms_st_valor(String.valueOf(valorIcmsST));
        webPedidoItens.setIcms_st_calcula(produto.getProduto_suj_icms_st());
        webPedidoItens.setValor_total(String.valueOf((totalProdutoBruto + valorIcmsST) - (desconto + descontoVerba)));
        webPedidoItens.setValor_bruto(String.valueOf((totalProdutoBruto + valorIcmsST)));
        webPedidoItens.setValor_desconto_real(String.valueOf(desconto));
        webPedidoItens.setValor_desconto_adic(String.valueOf(descontoVerba));
        return  webPedidoItens;
    }

    public void addListaWebPedidoItens(List<WebPedidoItens> lista, String idPedido, SQLiteDatabase database) {
//        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_ITENS);


            try {
                database.beginTransaction();

                for (WebPedidoItens webPedidoItem : lista) {
                    if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItem.getId_produto().trim() + "') OR " +
                            "(ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItem.getId_produto() + "');") <= 0) {
                        stmt.clearBindings();

                        // 1 a 5
                        stmt.bindString(1, webPedidoItem.getId_web_item_servidor() == null ? "" : webPedidoItem.getId_web_item_servidor());
                        stmt.bindString(2, idPedido);
                        stmt.bindString(3, webPedidoItem.getId_produto());
                        stmt.bindString(4, webPedidoItem.getId_empresa());
                        stmt.bindString(5, webPedidoItem.getQuantidade());

                        // 6: VALOR_UNITARIO (Lógica fiel ao seu IF/ELSE)
                        if (webPedidoItem.getVenda_preco() != null && !webPedidoItem.getVenda_preco().isEmpty()) {
                            float precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                            stmt.bindDouble(6, (precoVenda > 0.00f) ? precoVenda : webPedidoItem.getValor_unitario());
                        } else {
                            stmt.bindDouble(6, webPedidoItem.getValor_unitario());
                        }

                        // 7 a 11
                        stmt.bindString(7, webPedidoItem.getValor_bruto() == null ? "0.00" : webPedidoItem.getValor_bruto());
                        stmt.bindString(8, webPedidoItem.getValor_desconto_real() == null ? "0.00" : webPedidoItem.getValor_desconto_real());
                        stmt.bindString(9, webPedidoItem.getValor_desconto_per_add() == null ? "0.00" : webPedidoItem.getValor_desconto_per_add());
                        stmt.bindString(10, webPedidoItem.getValor_desconto_real_add() == null ? "0.00" : webPedidoItem.getValor_desconto_real_add());
                        stmt.bindString(11, webPedidoItem.getValor_total());

                        // 12 a 15 (Mantendo chamadas ao dbHelper se necessário)
                        stmt.bindString(12, db.pegaDataAtual()); // DATA_MOVIMENTACAO
                        stmt.bindString(13, webPedidoItem.getUsuario_lancamento_id());
                        stmt.bindString(14, db.pegaDataHoraAtual()); // USUARIO_LANCAMENTO_DATA
                        stmt.bindString(15, webPedidoItem.getId_item_desconto() == null ? "" : webPedidoItem.getId_item_desconto());

                        // 16 a 20
                        stmt.bindString(16, webPedidoItem.getPontos_unitario() == null ? "0" : webPedidoItem.getPontos_unitario());
                        stmt.bindString(17, webPedidoItem.getPontos_total() == null ? "0" : webPedidoItem.getPontos_total());
                        stmt.bindString(18, webPedidoItem.getPontos_coeficiente() == null ? "0" : webPedidoItem.getPontos_coeficiente());
                        stmt.bindString(19, webPedidoItem.getComissao_percentual() == null ? "0.00" : webPedidoItem.getComissao_percentual());
                        stmt.bindString(20, webPedidoItem.getComissao_valor() == null ? "0.00" : webPedidoItem.getComissao_valor());

                        // 21 a 25
                        stmt.bindString(21, webPedidoItem.getValor_bonus_credor() == null ? "0.00" : webPedidoItem.getValor_bonus_credor());
                        stmt.bindString(22, webPedidoItem.getPerc_bonus_credor() == null ? "0.00" : webPedidoItem.getPerc_bonus_credor());
                        stmt.bindString(23, webPedidoItem.getValor_desconto_per_orig() == null ? "0.00" : webPedidoItem.getValor_desconto_per_orig());
                        stmt.bindString(24, webPedidoItem.getValor_desconto_real_orig() == null ? "0.00" : webPedidoItem.getValor_desconto_real_orig());
                        stmt.bindString(25, webPedidoItem.getValor_desconto_per_add_orig() == null ? "0.00" : webPedidoItem.getValor_desconto_per_add_orig());

                        // 26 a 30
                        stmt.bindString(26, webPedidoItem.getValor_desconto_real_add_orig() == null ? "0.00" : webPedidoItem.getValor_desconto_real_add_orig());
                        stmt.bindString(27, webPedidoItem.getId_tabela_preco_faixa_orig() == null ? "" : webPedidoItem.getId_tabela_preco_faixa_orig());
                        stmt.bindString(28, webPedidoItem.getValor_total_orig()  == null ? "0.00" : webPedidoItem.getValor_total_orig());
                        stmt.bindString(29, webPedidoItem.getPontos_unitario_orig() == null ? "0" : webPedidoItem.getPontos_unitario_orig());
                        stmt.bindString(30, webPedidoItem.getPontos_coeficiente_orig() == null ? "0" : webPedidoItem.getPontos_coeficiente_orig());

                        // 31 a 35
                        stmt.bindString(31, webPedidoItem.getComissao_percentual_orig() == null ? "0.00" : webPedidoItem.getComissao_percentual_orig());
                        stmt.bindString(32, webPedidoItem.getValor_bonus_credor_orig() == null ? "0.00" : webPedidoItem.getValor_bonus_credor_orig());
                        stmt.bindString(33, webPedidoItem.getPerc_bonus_credor_orig() == null ? "0.00" : webPedidoItem.getPerc_bonus_credor_orig());
                        stmt.bindString(34, webPedidoItem.getComissao_valor_orig() == null ? "0.00" : webPedidoItem.getComissao_valor_orig());
                        stmt.bindString(35, webPedidoItem.getPontos_total_orig() == null ? "0" : webPedidoItem.getPontos_total_orig());

                        // 36 a 40
                        stmt.bindString(36, webPedidoItem.getPontos_cor_orig() == null ? "0" : webPedidoItem.getPontos_cor_orig());
                        stmt.bindString(37, webPedidoItem.getValor_preco_pago() == null ? "0.00" : webPedidoItem.getValor_preco_pago());
                        stmt.bindString(38, webPedidoItem.getTipoDesconto() == null ? "" : webPedidoItem.getTipoDesconto());
                        stmt.bindString(39, webPedidoItem.getValor_desconto_per() == null ? "0.00" : webPedidoItem.getValor_desconto_per());
                        stmt.bindString(40, webPedidoItem.getNome_produto());

                        // 41 a 45
                        stmt.bindString(41, webPedidoItem.getProduto_materia_prima() == null ? "N" : webPedidoItem.getProduto_materia_prima());
                        stmt.bindString(42, webPedidoItem.getProduto_tercerizacao() == null ? "N" : webPedidoItem.getProduto_tercerizacao());
                        stmt.bindLong(43, webPedidoItem.getIdCampanha());
                        stmt.bindDouble(44, webPedidoItem.getProduto_preco_editado() == null ? 0.00f : webPedidoItem.getProduto_preco_editado());
                        stmt.bindLong(45, webPedidoItem.getId_sku());

                        // 46 a 50
                        stmt.bindString(46, webPedidoItem.getNome_sku() == null ? "" : webPedidoItem.getNome_sku());
                        stmt.bindString(47, webPedidoItem.getIcms_st_valor() == null ? "0.00" : webPedidoItem.getIcms_st_valor());
                        stmt.bindDouble(48, webPedidoItem.getIcms_st_percentual() == null ? 0.00f : webPedidoItem.getIcms_st_percentual());
                        stmt.bindString(49, webPedidoItem.getIcms_st_calcula() == null ? "N" : webPedidoItem.getIcms_st_calcula());
                        // Lógica ID_GRUPO fiel: null/empty -> "0"
                        stmt.bindString(50, (webPedidoItem.getId_grupo() == null || webPedidoItem.getId_grupo().isEmpty()) ? "0" : webPedidoItem.getId_grupo());

                        // 51 a 53
                        // Lógica ID_SUB_GRUPO fiel: null/empty -> "0"
                        stmt.bindString(51, (webPedidoItem.getId_sub_grupo() == null || webPedidoItem.getId_sub_grupo().isEmpty()) ? "0" : webPedidoItem.getId_sub_grupo());

                        // 52: VALOR_DESCONTO_ADIC (Com try-catch original)
                        String vDescAdic = webPedidoItem.getValor_desconto_adic();
                        try {
                            if (vDescAdic != null)
                                vDescAdic = vDescAdic.replaceAll("\\s+", "").replace(",", ".");
                        } catch (Exception e) { /* Mantém original */ }
                        if (vDescAdic == null) stmt.bindNull(52);
                        else stmt.bindString(52, vDescAdic);

                        // 53: PERC_DESCONTO_ADIC (Com try-catch original)
                        String pDescAdic = webPedidoItem.getPerc_desconto_adic();
                        try {
                            if (pDescAdic != null)
                                pDescAdic = pDescAdic.replaceAll("\\s+", "").replace(",", ".");
                        } catch (Exception e) { /* Mantém original */ }
                        if (pDescAdic == null) stmt.bindNull(53);
                        else stmt.bindString(53, pDescAdic);

                        stmt.executeInsert();
                    }
                }
                database.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.endTransaction();
            }
        }

    public boolean addListaItensOtimizada(List<WebPedidoItens> lista, int idPedido) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_ITENS);
        String dataAtual = db.pegaDataAtual();
        String dataHoraAtual = db.pegaDataHoraAtual();
        boolean sucesso = true;

        database.beginTransaction();
        try {
            for (WebPedidoItens webPedidoItem : lista) {
                // Verificação de existência (Otimizada: apenas se id_web_item estiver vazio)
                if (webPedidoItem.getId_web_item() == null || webPedidoItem.getId_web_item().isEmpty() || webPedidoItem.getId_web_item().equals("0")) {
                    // Check if already in DB to avoid duplicates
                    if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItem.getId_produto().trim() + "'") <= 0) {
                        stmt.clearBindings();

                        // 1 a 5
                        if (webPedidoItem.getId_web_item_servidor() == null) stmt.bindNull(1); else stmt.bindString(1, webPedidoItem.getId_web_item_servidor());
                        stmt.bindString(2, String.valueOf(idPedido));
                        stmt.bindString(3, webPedidoItem.getId_produto());
                        stmt.bindString(4, webPedidoItem.getId_empresa());
                        stmt.bindString(5, webPedidoItem.getQuantidade());

                        // 6: VALOR_UNITARIO (Lógica idêntica ao add original)
                        if (webPedidoItem.getVenda_preco() != null && !webPedidoItem.getVenda_preco().isEmpty()) {
                            float precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                            stmt.bindDouble(6, (precoVenda > 0.00f) ? precoVenda : webPedidoItem.getValor_unitario());
                        } else {
                            stmt.bindDouble(6, webPedidoItem.getValor_unitario());
                        }

                        // 7 a 11
                        if (webPedidoItem.getValor_bruto() == null) stmt.bindString(7, "0.00"); else stmt.bindString(7, webPedidoItem.getValor_bruto());
                        if (webPedidoItem.getValor_desconto_real() == null) stmt.bindString(8, "0.00"); else stmt.bindString(8, webPedidoItem.getValor_desconto_real());
                        if (webPedidoItem.getValor_desconto_per_add() == null) stmt.bindString(9, "0.00"); else stmt.bindString(9, webPedidoItem.getValor_desconto_per_add());
                        if (webPedidoItem.getValor_desconto_real_add() == null) stmt.bindString(10, "0.00"); else stmt.bindString(10, webPedidoItem.getValor_desconto_real_add());
                        stmt.bindString(11, webPedidoItem.getValor_total());

                        // 12 a 15
                        stmt.bindString(12, dataAtual);
                        stmt.bindString(13, webPedidoItem.getUsuario_lancamento_id());
                        stmt.bindString(14, dataHoraAtual);
                        if (webPedidoItem.getId_item_desconto() == null) stmt.bindNull(15); else stmt.bindString(15, webPedidoItem.getId_item_desconto());

                        // 16 a 20
                        if (webPedidoItem.getPontos_unitario() == null) stmt.bindString(16, "0"); else stmt.bindString(16, webPedidoItem.getPontos_unitario());
                        if (webPedidoItem.getPontos_total() == null) stmt.bindString(17, "0"); else stmt.bindString(17, webPedidoItem.getPontos_total());
                        if (webPedidoItem.getPontos_coeficiente() == null) stmt.bindString(18, "0"); else stmt.bindString(18, webPedidoItem.getPontos_coeficiente());
                        if (webPedidoItem.getComissao_percentual() == null) stmt.bindString(19, "0.00"); else stmt.bindString(19, webPedidoItem.getComissao_percentual());
                        if (webPedidoItem.getComissao_valor() == null) stmt.bindString(20, "0.00"); else stmt.bindString(20, webPedidoItem.getComissao_valor());

                        // 21 a 25
                        if (webPedidoItem.getValor_bonus_credor() == null) stmt.bindString(21, "0.00"); else stmt.bindString(21, webPedidoItem.getValor_bonus_credor());
                        if (webPedidoItem.getPerc_bonus_credor() == null) stmt.bindString(22, "0.00"); else stmt.bindString(22, webPedidoItem.getPerc_bonus_credor());
                        if (webPedidoItem.getValor_desconto_per_orig() == null) stmt.bindString(23, "0.00"); else stmt.bindString(23, webPedidoItem.getValor_desconto_per_orig());
                        if (webPedidoItem.getValor_desconto_real_orig() == null) stmt.bindString(24, "0.00"); else stmt.bindString(24, webPedidoItem.getValor_desconto_real_orig());
                        if (webPedidoItem.getValor_desconto_per_add_orig() == null) stmt.bindString(25, "0.00"); else stmt.bindString(25, webPedidoItem.getValor_desconto_per_add_orig());

                        // 26 a 30
                        if (webPedidoItem.getValor_desconto_real_add_orig() == null) stmt.bindString(26, "0.00"); else stmt.bindString(26, webPedidoItem.getValor_desconto_real_add_orig());
                        if (webPedidoItem.getId_tabela_preco_faixa_orig() == null) stmt.bindNull(27); else stmt.bindString(27, webPedidoItem.getId_tabela_preco_faixa_orig());
                        if (webPedidoItem.getValor_total_orig() == null) stmt.bindString(28, "0.00"); else stmt.bindString(28, webPedidoItem.getValor_total_orig());
                        if (webPedidoItem.getPontos_unitario_orig() == null) stmt.bindString(29, "0"); else stmt.bindString(29, webPedidoItem.getPontos_unitario_orig());
                        if (webPedidoItem.getPontos_coeficiente_orig() == null) stmt.bindString(30, "0"); else stmt.bindString(30, webPedidoItem.getPontos_coeficiente_orig());

                        // 31 a 35
                        if (webPedidoItem.getComissao_percentual_orig() == null) stmt.bindString(31, "0.00"); else stmt.bindString(31, webPedidoItem.getComissao_percentual_orig());
                        if (webPedidoItem.getValor_bonus_credor_orig() == null) stmt.bindString(32, "0.00"); else stmt.bindString(32, webPedidoItem.getValor_bonus_credor_orig());
                        if (webPedidoItem.getPerc_bonus_credor_orig() == null) stmt.bindString(33, "0.00"); else stmt.bindString(33, webPedidoItem.getPerc_bonus_credor_orig());
                        if (webPedidoItem.getComissao_valor_orig() == null) stmt.bindString(34, "0.00"); else stmt.bindString(34, webPedidoItem.getComissao_valor_orig());
                        if (webPedidoItem.getPontos_total_orig() == null) stmt.bindString(35, "0"); else stmt.bindString(35, webPedidoItem.getPontos_total_orig());

                        // 36 a 40
                        if (webPedidoItem.getPontos_cor_orig() == null) stmt.bindString(36, "0"); else stmt.bindString(36, webPedidoItem.getPontos_cor_orig());
                        if (webPedidoItem.getValor_preco_pago() == null) stmt.bindString(37, "0.00"); else stmt.bindString(37, webPedidoItem.getValor_preco_pago());
                        if (webPedidoItem.getTipoDesconto() == null) stmt.bindNull(38); else stmt.bindString(38, webPedidoItem.getTipoDesconto());
                        if (webPedidoItem.getValor_desconto_per() == null) stmt.bindString(39, "0.00"); else stmt.bindString(39, webPedidoItem.getValor_desconto_per());
                        stmt.bindString(40, webPedidoItem.getNome_produto());

                        // 41 a 45
                        stmt.bindString(41, webPedidoItem.getProduto_materia_prima() == null ? "N" : webPedidoItem.getProduto_materia_prima());
                        stmt.bindString(42, webPedidoItem.getProduto_tercerizacao() == null ? "N" : webPedidoItem.getProduto_tercerizacao());
                        stmt.bindLong(43, webPedidoItem.getIdCampanha());
                        stmt.bindDouble(44, webPedidoItem.getProduto_preco_editado() == null ? 0.00f : webPedidoItem.getProduto_preco_editado());
                        stmt.bindLong(45, webPedidoItem.getId_sku());

                        // 46 a 50
                        if (webPedidoItem.getNome_sku() == null) stmt.bindNull(46); else stmt.bindString(46, webPedidoItem.getNome_sku());
                        if (webPedidoItem.getIcms_st_valor() == null) stmt.bindString(47, "0.00"); else stmt.bindString(47, webPedidoItem.getIcms_st_valor());
                        stmt.bindDouble(48, webPedidoItem.getIcms_st_percentual() == null ? 0.00f : webPedidoItem.getIcms_st_percentual());
                        stmt.bindString(49, webPedidoItem.getIcms_st_calcula() == null ? "N" : webPedidoItem.getIcms_st_calcula());
                        stmt.bindString(50, (webPedidoItem.getId_grupo() == null || webPedidoItem.getId_grupo().isEmpty()) ? "0" : webPedidoItem.getId_grupo());

                        // 51 a 53
                        stmt.bindString(51, (webPedidoItem.getId_sub_grupo() == null || webPedidoItem.getId_sub_grupo().isEmpty()) ? "0" : webPedidoItem.getId_sub_grupo());

                        String vDescAdic = webPedidoItem.getValor_desconto_adic();
                        try { if (vDescAdic != null) vDescAdic = vDescAdic.replaceAll("\\s+", "").replace(",", "."); } catch (Exception ignored) {}
                        if (vDescAdic == null) stmt.bindNull(52); else stmt.bindString(52, vDescAdic);

                        String pDescAdic = webPedidoItem.getPerc_desconto_adic();
                        try { if (pDescAdic != null) pDescAdic = pDescAdic.replaceAll("\\s+", "").replace(",", "."); } catch (Exception ignored) {}
                        if (pDescAdic == null) stmt.bindNull(53); else stmt.bindString(53, pDescAdic);

                        long id = stmt.executeInsert();
                        if (id > 0) {
                            webPedidoItem.setId_web_item(String.valueOf(id));
                        } else {
                            sucesso = false;
                        }
                    }
                }
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            sucesso = false;
        } finally {
            database.endTransaction();
        }
        return sucesso;
    }

    private static final String SQL_ITENS = "INSERT INTO TBL_WEB_PEDIDO_ITENS (" +
            "ID_WEB_ITEM_SERVIDOR, ID_PEDIDO, ID_PRODUTO, ID_EMPRESA, QUANTIDADE, " + // 1-5
            "VALOR_UNITARIO, VALOR_BRUTO, VALOR_DESCONTO_REAL, VALOR_DESCONTO_PER_ADD, VALOR_DESCONTO_REAL_ADD, " + // 6-10
            "VALOR_TOTAL, DATA_MOVIMENTACAO, USUARIO_LANCAMENTO_ID, USUARIO_LANCAMENTO_DATA, ID_ITEM_DESCONTO, " + // 11-15
            "PONTOS_UNITARIO, PONTOS_TOTAL, PONTOS_COEFICIENTE, COMISSAO_PERCENTUAL, COMISSAO_VALOR, " + // 16-20
            "VALOR_BONUS_CREDOR, PERC_BONUS_CREDOR, VALOR_DESCONTO_PER_ORIG, VALOR_DESCONTO_REAL_ORIG, VALOR_DESCONTO_PER_ADD_ORIG, " + // 21-25
            "VALOR_DESCONTO_REAL_ADD_ORIG, ID_TABELA_PRECO_FAIXA_ORIG, VALOR_TOTAL_ORIG, PONTOS_UNITARIO_ORIG, PONTOS_COEFICIENTE_ORIG, " + // 26-30
            "COMISSAO_PERCENTUAL_ORIG, VALOR_BONUS_CREDOR_ORIG, PERC_BONUS_CREDOR_ORIG, COMISSAO_VALOR_ORIG, PONTOS_TOTAL_ORIG, " + // 31-35
            "PONTOS_COR_ORIG, VALOR_PRECO_PAGO, TIPO_DESCONTO, VALOR_DESCONTO_PER, NOME_PRODUTO, " + // 36-40
            "PRODUTO_MATERIA_PRIMA, PRODUTO_TERCERIZACAO, ID_CAMPANHA, PRODUTO_PRECO_EDITADO, ID_SKU, " + // 41-45
            "NOME_SKU, ICMS_ST_VALOR, ICMS_ST_PERCENTUAL, ICMS_ST_CALCULA, ID_GRUPO, " + // 46-50
            "ID_SUB_GRUPO, VALOR_DESCONTO_ADIC, PERC_DESCONTO_ADIC" + // 51-53
            ") VALUES (" +
            "?,?,?,?,?," + // 1-5
            "?,?,?,?,?," + // 6-10
            "?,?,?,?,?," + // 11-15
            "?,?,?,?,?," + // 16-20
            "?,?,?,?,?," + // 21-25
            "?,?,?,?,?," + // 26-30
            "?,?,?,?,?," + // 31-35
            "?,?,?,?,?," + // 36-40
            "?,?,?,?,?," + // 41-45
            "?,?,?,?,?," + // 46-50
            "?,?,?" +       // 51-53
            ")";
}
