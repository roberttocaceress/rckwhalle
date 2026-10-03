package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoCab;

import java.util.ArrayList;
import java.util.List;

public class TabelaPrecoCabDAO {
    private DBHelper db;
    public TabelaPrecoCabDAO(DBHelper db) {
        this.db = db;
    }
    public long add(TabelaPrecoCab tabelaPrecoCab) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_TABELA", tabelaPrecoCab.getId_tabela());
            content.put("ID_EMPRESA", tabelaPrecoCab.getId_empresa());
            content.put("ATIVO", tabelaPrecoCab.getAtivo());
            content.put("ID_TIPO_TABELA", tabelaPrecoCab.getId_tipo_tabela());
            content.put("NOME_TABELA", tabelaPrecoCab.getNome_tabela());
            content.put("DATA_INICIO", tabelaPrecoCab.getData_inicio());
            content.put("DATA_FIM", tabelaPrecoCab.getData_fim());
            content.put("DESCONTO_DE_PERC", tabelaPrecoCab.getDesconto_de_perc());
            content.put("DESCONTO_A_PERC", tabelaPrecoCab.getDesconto_a_perc());
            content.put("COMISSAO_PERC", tabelaPrecoCab.getComissao_perc());
            content.put("VERBA_PERC", tabelaPrecoCab.getVerba_perc());
            content.put("FAIXA_VALOR_DE", tabelaPrecoCab.getFaixa_valor_de());
            content.put("FAIXA_VALOR_A", tabelaPrecoCab.getFaixa_valor_a());
            content.put("USUARIO_ID", tabelaPrecoCab.getUsuario_id());
            content.put("USUARIO_NOME", tabelaPrecoCab.getUsuario_nome());
            content.put("USUARIO_DATA", tabelaPrecoCab.getUsuario_data());
            content.put("DESCONTO_VERBA_MAX", tabelaPrecoCab.getDesconto_verba_max());
            content.put("ID_GRUPO_VENDEDORES", tabelaPrecoCab.getId_grupo_vendedores());
            content.put("UTILIZA_VERBA", tabelaPrecoCab.getUtiliza_verba());
            content.put("FAIXA_VALOR_BRUTO_DE", tabelaPrecoCab.getFaixa_valor_bruto_de());
            content.put("FAIXA_VALOR_BRUTO_A", tabelaPrecoCab.getFaixa_valor_bruto_a());
            System.gc();
            return db.addDados("TBL_TABELA_PRECO_CAB", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<TabelaPrecoCab> getLista(String SQL) {
        List<TabelaPrecoCab> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return  lista;
        cursor.moveToFirst();
        do {
            TabelaPrecoCab tabelaPrecoCab = new TabelaPrecoCab();
            try {
                tabelaPrecoCab.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
                tabelaPrecoCab.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                tabelaPrecoCab.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                tabelaPrecoCab.setId_tipo_tabela(cursor.getString(cursor.getColumnIndex("ID_TIPO_TABELA")));
                tabelaPrecoCab.setNome_tabela(cursor.getString(cursor.getColumnIndex("NOME_TABELA")));
                tabelaPrecoCab.setData_inicio(cursor.getString(cursor.getColumnIndex("DATA_INICIO")));
                tabelaPrecoCab.setData_fim(cursor.getString(cursor.getColumnIndex("DATA_FIM")));
                tabelaPrecoCab.setDesconto_de_perc(cursor.getString(cursor.getColumnIndex("DESCONTO_DE_PERC")));
                tabelaPrecoCab.setDesconto_a_perc(cursor.getString(cursor.getColumnIndex("DESCONTO_A_PERC")));
                tabelaPrecoCab.setComissao_perc(cursor.getString(cursor.getColumnIndex("COMISSAO_PERC")));
                tabelaPrecoCab.setVerba_perc(cursor.getString(cursor.getColumnIndex("VERBA_PERC")));
                tabelaPrecoCab.setFaixa_valor_de(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_DE")));
                tabelaPrecoCab.setFaixa_valor_a(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_A")));
                tabelaPrecoCab.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
                tabelaPrecoCab.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
                tabelaPrecoCab.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                tabelaPrecoCab.setDesconto_verba_max(cursor.getString(cursor.getColumnIndex("DESCONTO_VERBA_MAX")));
                tabelaPrecoCab.setId_grupo_vendedores(cursor.getString(cursor.getColumnIndex("ID_GRUPO_VENDEDORES")));
                tabelaPrecoCab.setUtiliza_verba(cursor.getString(cursor.getColumnIndex("UTILIZA_VERBA")));
                tabelaPrecoCab.setFaixa_valor_bruto_de(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_BRUTO_DE")));
                tabelaPrecoCab.setFaixa_valor_bruto_a(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_BRUTO_A")));
                lista.add(tabelaPrecoCab);
                System.gc();
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public void addListaTabelaPrecoCab(List<TabelaPrecoCab> lista, DBHelper db) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT_TABELA_PRECO_CAB);

        try {
            database.beginTransaction();

            for (TabelaPrecoCab cab : lista) {
                stmt.clearBindings();

                bindString(stmt, 1, cab.getId_tabela());
                bindString(stmt, 2, cab.getId_empresa());
                bindString(stmt, 3, cab.getAtivo());
                bindString(stmt, 4, cab.getId_tipo_tabela());
                bindString(stmt, 5, cab.getNome_tabela());
                bindString(stmt, 6, cab.getData_inicio());
                bindString(stmt, 7, cab.getData_fim());
                bindString(stmt, 8, cab.getDesconto_de_perc());
                bindString(stmt, 9, cab.getDesconto_a_perc());
                bindString(stmt, 10, cab.getComissao_perc());

                bindString(stmt, 11, cab.getVerba_perc());
                bindString(stmt, 12, cab.getFaixa_valor_de());
                bindString(stmt, 13, cab.getFaixa_valor_a());
                bindString(stmt, 14, cab.getUsuario_id());
                bindString(stmt, 15, cab.getUsuario_nome());
                bindString(stmt, 16, cab.getUsuario_data());
                bindString(stmt, 17, cab.getDesconto_verba_max());
                bindString(stmt, 18, cab.getId_grupo_vendedores());
                bindString(stmt, 19, cab.getUtiliza_verba());
                bindString(stmt, 20, cab.getFaixa_valor_bruto_de());
                bindString(stmt, 21, cab.getFaixa_valor_bruto_a());

                stmt.executeInsert();
            }

            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    private void bindString(SQLiteStatement stmt, int i, String val) {
        if (val == null) stmt.bindNull(i); else stmt.bindString(i, val);
    }

    private static final String SQL_INSERT_TABELA_PRECO_CAB = "INSERT INTO TBL_TABELA_PRECO_CAB (" +
            "ID_TABELA, ID_EMPRESA, ATIVO, ID_TIPO_TABELA, NOME_TABELA, " + // 1-5
            "DATA_INICIO, DATA_FIM, DESCONTO_DE_PERC, DESCONTO_A_PERC, COMISSAO_PERC, " + // 6-10
            "VERBA_PERC, FAIXA_VALOR_DE, FAIXA_VALOR_A, USUARIO_ID, USUARIO_NOME, " + // 11-15
            "USUARIO_DATA, DESCONTO_VERBA_MAX, ID_GRUPO_VENDEDORES, UTILIZA_VERBA, FAIXA_VALOR_BRUTO_DE, " + // 16-20
            "FAIXA_VALOR_BRUTO_A" + // 21
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}
