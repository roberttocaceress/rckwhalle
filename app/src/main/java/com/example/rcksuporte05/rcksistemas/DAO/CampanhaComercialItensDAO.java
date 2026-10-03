package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

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
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_ITENS WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + " AND ID_PRODUTO_VENDA = " + idProduto + ";");
        return getLista(cursor).get(0);
    }

    public CampanhaComercialItens getCampanhaComercialItens(CampanhaComercialCab campanhaComercialCab, int idLinha) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_ITENS WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + " AND ID_LINHA_PRODUTO = " + idLinha + ";");
        return getLista(cursor).get(0);
    }

    public List<CampanhaComercialItens> getLista(CampanhaComercialCab campanhaComercialCab) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_ITENS WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + ";");
        return getLista(cursor);
    }

    public int getTotalReg(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return 0;
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("TOTALREG"));
    }


    private List<CampanhaComercialItens> getLista(Cursor cursor) {
        List<CampanhaComercialItens> lista = new ArrayList<>();
        if( cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            CampanhaComercialItens campanhaComercialItens = new CampanhaComercialItens();
            try {
                campanhaComercialItens.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                campanhaComercialItens.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
                campanhaComercialItens.setIdTipoCampanha(cursor.getInt(cursor.getColumnIndex("ID_TIPO_CAMPANHA")));
                campanhaComercialItens.setIdBaseCampanha(cursor.getInt(cursor.getColumnIndex("ID_BASE_CAMPANHA")));
                campanhaComercialItens.setIdCampanha(cursor.getInt(cursor.getColumnIndex("ID_CAMPANHA")));
                campanhaComercialItens.setIdLinhaProduto(cursor.getInt(cursor.getColumnIndex("ID_LINHA_PRODUTO")));
                campanhaComercialItens.setIdProdutoVenda(cursor.getString(cursor.getColumnIndex("ID_PRODUTO_VENDA")));
                campanhaComercialItens.setNomeProdutoLinha(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO_LINHA")));
                campanhaComercialItens.setQuantidadeVenda(cursor.getFloat(cursor.getColumnIndex("QUANTIDADE_VENDA")));
                campanhaComercialItens.setIdProdutoBonus(cursor.getString(cursor.getColumnIndex("ID_PRODUTO_BONUS")));
                campanhaComercialItens.setQuantidadeBonus(cursor.getFloat(cursor.getColumnIndex("QUANTIDADE_BONUS")));
                campanhaComercialItens.setNomeProdutoBonus(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO_BONUS")));
                campanhaComercialItens.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
                campanhaComercialItens.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
                campanhaComercialItens.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                lista.add(campanhaComercialItens);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        return lista;
    }
}
