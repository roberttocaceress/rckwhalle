package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.CursorIndexOutOfBoundsException;

import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;

public class ProdutoLinhaColecaoDAO {
    private DBHelper db;

    public ProdutoLinhaColecaoDAO(DBHelper db) {
        this.db = db;
    }

    public void add(ProdutoLinhaColecao produtoLinhaColecao) {
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", produtoLinhaColecao.getAtivo());
            content.put("ID_LINHA_COLECAO", produtoLinhaColecao.getIdLinhaColecao());
            content.put("NOME_DESCRICAO_LINHA", produtoLinhaColecao.getNomeDescricaoLinha());
            content.put("USUARIO_ID", produtoLinhaColecao.getUsuarioId());
            content.put("USUARIO_NOME", produtoLinhaColecao.getUsuarioNome());
            content.put("USUARIO_DATA", produtoLinhaColecao.getUsuarioData());
            db.addDados("TBL_PRODUTO_LINHA_COLECAO", content);
        } catch ( CursorIndexOutOfBoundsException e) {
        }
    }

    public java.util.List<ProdutoLinhaColecao> getLista(String SQL) {
        java.util.List<ProdutoLinhaColecao> lista = new java.util.ArrayList<>();
        android.database.Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        if (cursor.moveToFirst()) {
            int idLinha = cursor.getColumnIndex("ID_LINHA_COLECAO");
            int nomeLinha = cursor.getColumnIndex("NOME_DESCRICAO_LINHA");
            int ativo = cursor.getColumnIndex("ATIVO");
            do {
                ProdutoLinhaColecao item = new ProdutoLinhaColecao();
                try {
                    if (idLinha != -1) item.setIdLinhaColecao(cursor.getInt(idLinha));
                    if (nomeLinha != -1) item.setNomeDescricaoLinha(cursor.getString(nomeLinha));
                    if (ativo != -1) item.setAtivo(cursor.getString(ativo));
                    lista.add(item);
                } catch (Exception e) {
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}
