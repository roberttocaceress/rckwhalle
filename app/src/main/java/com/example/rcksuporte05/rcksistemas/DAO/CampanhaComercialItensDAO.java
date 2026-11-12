package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;

import java.util.ArrayList;
import java.util.List;

public class CampanhaComercialItensDAO {
    private DBHelper db;

    public CampanhaComercialItensDAO(DBHelper db) {
        this.db = db;
    }

    public void add(CampanhaComercialItens campanhaComercialItens) {
        ContentValues content = new ContentValues();
        content.put("ATIVO", campanhaComercialItens.getAtivo());
        content.put("ID_EMPRESA", campanhaComercialItens.getIdEmpresa());
        content.put("ID_TIPO_CAMPANHA", campanhaComercialItens.getIdTipoCampanha());
        content.put("ID_BASE_CAMPANHA", campanhaComercialItens.getIdBaseCampanha());
        content.put("ID_CAMPANHA", campanhaComercialItens.getIdCampanha());
        content.put("ID_LINHA_PRODUTO", campanhaComercialItens.getIdLinhaProduto());
        if (campanhaComercialItens.getIdLinhaProduto() == 0)
            content.put("ID_LINHA_PRODUTO", -1);
        content.put("ID_PRODUTO_VENDA", campanhaComercialItens.getIdProdutoVenda());
        content.put("NOME_PRODUTO_LINHA", campanhaComercialItens.getNomeProdutoLinha());
        content.put("QUANTIDADE_VENDA", campanhaComercialItens.getQuantidadeVenda());
        content.put("ID_PRODUTO_BONUS", campanhaComercialItens.getIdProdutoBonus());
        content.put("NOME_PRODUTO_BONUS", campanhaComercialItens.getNomeProdutoBonus());
        content.put("QUANTIDADE_BONUS", campanhaComercialItens.getQuantidadeBonus());
        content.put("USUARIO_ID", campanhaComercialItens.getUsuarioId());
        content.put("USUARIO_NOME", campanhaComercialItens.getUsuarioNome());
        content.put("USUARIO_DATA", campanhaComercialItens.getUsuarioData());
        db.addDados("TBL_CAMPANHA_COMERCIAL_ITENS", content);
    }

    public CampanhaComercialItens getCampanhaComercialItens(CampanhaComercialCab campanhaComercialCab, String idProduto) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_ITENS WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + " AND ID_PRODUTO_VENDA = '" + idProduto + "';");
        List<CampanhaComercialItens> lista = getLista(cursor);
        return !lista.isEmpty() ? lista.get(0) : null;
    }

    public CampanhaComercialItens getCampanhaComercialItens(CampanhaComercialCab campanhaComercialCab, int idLinha) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_ITENS WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + " AND ID_LINHA_PRODUTO = " + idLinha + ";");
        List<CampanhaComercialItens> lista = getLista(cursor);
        return !lista.isEmpty() ? lista.get(0) : null;
    }

    public List<CampanhaComercialItens> getLista(CampanhaComercialCab campanhaComercialCab) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_ITENS WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + ";");
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

    private List<CampanhaComercialItens> getLista(Cursor cursor) {
        List<CampanhaComercialItens> lista = new ArrayList<>();
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    lista.add(setCampanhaComercialItens(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    private CampanhaComercialItens setCampanhaComercialItens(Cursor cursor) {
        CampanhaComercialItens item = new CampanhaComercialItens();
        try {
            item.setAtivo(getString(cursor, "ATIVO"));
            item.setIdEmpresa(getInt(cursor, "ID_EMPRESA"));
            item.setIdTipoCampanha(getInt(cursor, "ID_TIPO_CAMPANHA"));
            item.setIdBaseCampanha(getInt(cursor, "ID_BASE_CAMPANHA"));
            item.setIdCampanha(getInt(cursor, "ID_CAMPANHA"));
            item.setIdLinhaProduto(getInt(cursor, "ID_LINHA_PRODUTO"));
            item.setIdProdutoVenda(getString(cursor, "ID_PRODUTO_VENDA"));
            item.setNomeProdutoLinha(getString(cursor, "NOME_PRODUTO_LINHA"));
            item.setQuantidadeVenda(getFloat(cursor, "QUANTIDADE_VENDA"));
            item.setIdProdutoBonus(getString(cursor, "ID_PRODUTO_BONUS"));
            item.setQuantidadeBonus(getFloat(cursor, "QUANTIDADE_BONUS"));
            item.setNomeProdutoBonus(getString(cursor, "NOME_PRODUTO_BONUS"));
            item.setUsuarioId(getInt(cursor, "USUARIO_ID"));
            item.setUsuarioNome(getString(cursor, "USUARIO_NOME"));
            item.setUsuarioData(getString(cursor, "USUARIO_DATA"));
        } catch (Exception ignored) {
        }
        return item;
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

    private float getFloat(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0.0f;
        try {
            return Float.parseFloat(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }
}
