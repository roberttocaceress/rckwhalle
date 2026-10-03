package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
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
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Operacao operacao = new Operacao();
            try {
                operacao.setId_operacao(cursor.getString(cursor.getColumnIndex("ID_OPERACAO")));
                operacao.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                operacao.setNome_operacao(cursor.getString(cursor.getColumnIndex("NOME_OPERACAO")));
                operacao.setNatureza_operacao(cursor.getString(cursor.getColumnIndex("NATUREZA_OPERACAO")));
                lista.add(operacao);
                System.gc();
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public Operacao getOperacao(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return null;
        cursor.moveToFirst();
        Operacao operacao = new Operacao();
        try {
            operacao.setId_operacao(cursor.getString(cursor.getColumnIndex("ID_OPERACAO")));
            operacao.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            operacao.setNome_operacao(cursor.getString(cursor.getColumnIndex("NOME_OPERACAO")));
            operacao.setNatureza_operacao(cursor.getString(cursor.getColumnIndex("NATUREZA_OPERACAO")));
            System.gc();
            return operacao;
        } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

    }
}
