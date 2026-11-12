package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Operacao;

import java.util.ArrayList;
import java.util.List;

public class OperacaoEstoqueDAO {
    private DBHelper db;

    public OperacaoEstoqueDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Operacao operacao) {
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", operacao.getAtivo());
            content.put("ID_OPERACAO", operacao.getId_operacao());
            content.put("NOME_OPERACAO", operacao.getNome_operacao());
            content.put("NATUREZA_OPERACAO", operacao.getNatureza_operacao());
            System.gc();
            return db.addDados("TBL_OPERACAO_ESTOQUE", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Operacao> getLista(String SQL) {
        List<Operacao> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                do {
                    lista.add(setOperacao(cursor));
                } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public Operacao getOperacao(String SQL) {
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                return setOperacao(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    private Operacao setOperacao(Cursor cursor) {
        Operacao operacao = new Operacao();
        try {
            operacao.setId_operacao(getString(cursor, "ID_OPERACAO"));
            operacao.setAtivo(getString(cursor, "ATIVO"));
            operacao.setNome_operacao(getString(cursor, "NOME_OPERACAO"));
            operacao.setNatureza_operacao(getString(cursor, "NATUREZA_OPERACAO"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operacao;
    }

    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }
}
