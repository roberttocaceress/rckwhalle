package com.example.rcksuporte05.rcksistemas.DAO;

import static com.example.rcksuporte05.rcksistemas.api.Api.context;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CampanhaComercialCabDAO {
    private DBHelper db;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String dataAtual;

    public CampanhaComercialCabDAO(DBHelper db) {
        this.db = db;
    }

    public void add(CampanhaComercialCab campanhaComercialCab) {
        ContentValues content = new ContentValues();
        try {
           content.put("ATIVO", campanhaComercialCab.getAtivo());
           content.put("ID_CAMPANHA", campanhaComercialCab.getIdCampanha());
           content.put("ID_TIPO_CAMPANHA", campanhaComercialCab.getIdTipoCampanha());
           content.put("ID_BASE_CAMPANHA", campanhaComercialCab.getIdBaseCampanha());
           content.put("ID_EMPRESA", campanhaComercialCab.getIdEmpresa());
           content.put("DATA_INICIO", campanhaComercialCab.getDataInicio());
           content.put("DATA_FIM", campanhaComercialCab.getDataFim());
           content.put("NOME_CAMPANHA", campanhaComercialCab.getNomeCampanha());
           content.put("DESCRICAO_CAMPANHA", campanhaComercialCab.getDescricaoCampanha());
           content.put("USUARIO_ID", campanhaComercialCab.getUsuarioId());
           content.put("USUARIO_NOME", campanhaComercialCab.getUsuarioNome());
           content.put("USUARIO_DATA", campanhaComercialCab.getUsuarioData());
           db.addDados("TBL_CAMPANHA_COMERCIAL_CAB", content);
        } catch (NullPointerException | SQLException ignored) {
        }

    }

    public CampanhaComercialCab getLista(int idCampanha) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_CAB WHERE ID_CAMPANHA = " + idCampanha + ";");
        List<CampanhaComercialCab> lista = getLista(cursor);
        return !lista.isEmpty() ? lista.get(0) : null;
    }

    public List<CampanhaComercialCab> getLista() {
        dataAtual  = sdf.format(new Date());
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_CAB WHERE (DATA_INICIO <= '" + dataAtual + "' AND  DATA_FIM >= '" + dataAtual + "') ORDER BY NOME_CAMPANHA;");
        return getLista(cursor);
    }

    @SuppressLint("Range")
    public int getTotalReg(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return 0;
        }
        try {
            if (cursor.moveToFirst())
                return getInt(cursor, "TOTALREG");
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return 0;
    }

    public List<CampanhaComercialCab> getLista(Cliente cliente, Produto produto) {
        dataAtual  = sdf.format(new Date());
        String SQL = "SELECT CAB.* FROM TBL_CAMPANHA_COMERCIAL_CAB CAB " +
                "INNER JOIN TBL_CAMPANHA_COM_CLIENTES CLIENTE ON CAB.ID_CAMPANHA = CLIENTE.ID_CAMPANHA " +
                "INNER JOIN TBL_CAMPANHA_COMERCIAL_ITENS ITENS ON ITENS.ID_CAMPANHA = CAB.ID_CAMPANHA " +
                "WHERE (CAB.DATA_INICIO <= '" + dataAtual + "' AND  CAB.DATA_FIM >= '" + dataAtual + "') " +
                "AND  CLIENTE.ID_CLIENTE = " +  cliente.getId_cadastro_servidor() + " " +
                "AND (ITENS.ID_LINHA_PRODUTO = " + produto.getIdLinhaColecao() + " OR ITENS.ID_PRODUTO_VENDA = '" + produto.getId_produto() + "');";
        Cursor cursor = db.listaDados(SQL);
        return getLista(cursor);
    }

    public List<CampanhaComercialCab> getLista(String idCliente, String idProduto, String idLinhaColecao, int idCampanha) {
        dataAtual  = sdf.format(new Date());
        String SQL = "SELECT CAB.* FROM TBL_CAMPANHA_COMERCIAL_CAB CAB " +
                "INNER JOIN TBL_CAMPANHA_COM_CLIENTES CLIENTE ON CAB.ID_CAMPANHA = CLIENTE.ID_CAMPANHA " +
                "INNER JOIN TBL_CAMPANHA_COMERCIAL_ITENS ITENS ON ITENS.ID_CAMPANHA = CAB.ID_CAMPANHA " +
                "WHERE (CAB.DATA_INICIO <= '" + dataAtual + "' AND  CAB.DATA_FIM >= '" + dataAtual + "') " +
                "AND  CLIENTE.ID_CLIENTE = " +  idCliente + " " +
                "AND (ITENS.ID_LINHA_PRODUTO = " + idLinhaColecao + " OR ITENS.ID_PRODUTO_VENDA = '" + idProduto + "') " +
                "AND CAB.ID_CAMPANHA = '" + idCampanha + "';";
        Cursor cursor = db.listaDados(SQL);
        return getLista(cursor);
    }

    public List<CampanhaComercialCab> getLista(int idcliente, int idProduto, int idLinhaColecao) {
        dataAtual  = sdf.format(new Date());
        String SQL = "SELECT CAB.* FROM TBL_CAMPANHA_COMERCIAL_CAB CAB " +
                "INNER JOIN TBL_CAMPANHA_COM_CLIENTES CLIENTE ON CAB.ID_CAMPANHA = CLIENTE.ID_CAMPANHA " +
                "INNER JOIN TBL_CAMPANHA_COMERCIAL_ITENS ITENS ON ITENS.ID_CAMPANHA = CAB.ID_CAMPANHA " +
                "WHERE (CAB.DATA_INICIO <= '" + dataAtual + "' AND  CAB.DATA_FIM >= '" + dataAtual + "') " +
                "AND  CLIENTE.ID_CLIENTE = " +  idcliente + " " +
                "AND (ITENS.ID_LINHA_PRODUTO = " + idLinhaColecao + " OR ITENS.ID_PRODUTO_VENDA = '" + idProduto + "');";
        Cursor cursor = db.listaDados(SQL);
        return getLista(cursor);
    }

    public boolean verificaCampanha(Cliente cliente, WebPedidoItens webPedidoItem) {
        dataAtual  = sdf.format(new Date());
        try {
            if (webPedidoItem.getIdCampanha() > 0) {
                String SQL = "SELECT CAB.* FROM TBL_CAMPANHA_COMERCIAL_CAB CAB " +
                     " INNER JOIN TBL_CAMPANHA_COM_CLIENTES CLIENTE ON CAB.ID_CAMPANHA = CLIENTE.ID_CAMPANHA" +
                     " INNER JOIN TBL_CAMPANHA_COMERCIAL_ITENS ITENS ON ITENS.ID_CAMPANHA = CAB.ID_CAMPANHA" +
                     " WHERE (CAB.DATA_INICIO <= '" + dataAtual + "' AND  CAB.DATA_FIM >= '" + dataAtual + "') " +
                     "AND CLIENTE.ID_CLIENTE = " + cliente.getId_cadastro_servidor() + " " +
                     "AND (ITENS.ID_LINHA_PRODUTO = " + webPedidoItem.getIdLinhaColecao() + " OR ITENS.ID_PRODUTO_VENDA = '" + webPedidoItem.getId_produto() + "') " +
                     "AND CAB.ID_CAMPANHA = " + webPedidoItem.getIdCampanha() + ";";
                Cursor cursor = db.listaDados(SQL);
                List<CampanhaComercialCab> lista = getLista(cursor);
                return !lista.isEmpty();
            }
        } catch (CursorIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private List<CampanhaComercialCab> getLista(Cursor cursor) {
        List<CampanhaComercialCab> lista = new ArrayList<>();
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    CampanhaComercialCab campanhaComercialCab = setCampanhaComercialCab(cursor);
                    if (usuarioIsLogado()) {
                        if (db.contagem("SELECT COUNT(*) FROM TBL_CAMPANHA_COM_CLIENTES CAMP INNER JOIN TBL_CADASTRO CAD ON CAMP.ID_CLIENTE = CAD.ID_CADASTRO_SERVIDOR WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + " AND CAMP.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " ORDER BY CAD.NOME_CADASTRO;") > 0) {
                            boolean insere = true;
                            for (int i = 0; lista.size() > i; i++)
                                if (campanhaComercialCab.getIdCampanha() == lista.get(i).getIdCampanha())
                                    insere = false;
                            if (insere)
                                lista.add(campanhaComercialCab);
                        }
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    private CampanhaComercialCab setCampanhaComercialCab(Cursor cursor) {
        CampanhaComercialCab campanhaComercialCab = new CampanhaComercialCab();
        try {
            campanhaComercialCab.setAtivo(getString(cursor, "ATIVO"));
            campanhaComercialCab.setIdCampanha(getInt(cursor, "ID_CAMPANHA"));
            campanhaComercialCab.setIdTipoCampanha(getInt(cursor, "ID_TIPO_CAMPANHA"));
            campanhaComercialCab.setIdBaseCampanha(getInt(cursor, "ID_BASE_CAMPANHA"));
            campanhaComercialCab.setIdEmpresa(getInt(cursor, "ID_EMPRESA"));
            campanhaComercialCab.setDataInicio(getString(cursor, "DATA_INICIO"));
            campanhaComercialCab.setDataFim(getString(cursor, "DATA_FIM"));
            campanhaComercialCab.setNomeCampanha(getString(cursor, "NOME_CAMPANHA"));
            campanhaComercialCab.setDescricaoCampanha(getString(cursor, "DESCRICAO_CAMPANHA"));
            campanhaComercialCab.setUsuarioId(getInt(cursor, "USUARIO_ID"));
            campanhaComercialCab.setUsuarioNome(getString(cursor, "USUARIO_NOME"));
            campanhaComercialCab.setUsuarioData(getString(cursor, "USUARIO_DATA"));
        } catch (Exception ignored) {
        }
        return campanhaComercialCab;
    }

    // =========================================================================
    // AUXILIARES NECESSÁRIOS (CURSOR NULL-SAFE)
    // =========================================================================
    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }

    private int getInt(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0;
        try {
            return Integer.parseInt(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean usuarioIsLogado() {
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(context));
        try {
            if (UsuarioHelper.getUsuario() == null) 
                return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
