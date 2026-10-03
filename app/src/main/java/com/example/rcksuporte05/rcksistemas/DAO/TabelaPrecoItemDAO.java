package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;

import java.util.ArrayList;
import java.util.List;

public class TabelaPrecoItemDAO {
    private DBHelper db;

    public TabelaPrecoItemDAO(DBHelper db) {
        this.db = db;
    }

    public long add(TabelaPrecoItem tabelaPrecoItem) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_ITEM", tabelaPrecoItem.getId_item());
            content.put("ID_TABELA", tabelaPrecoItem.getId_tabela());
            content.put("PERC_DESC_INICIAL", tabelaPrecoItem.getPerc_desc_inicial());
            content.put("PERC_DESC_FINAL", tabelaPrecoItem.getPerc_desc_final());
            content.put("PERC_COM_INTERNO", tabelaPrecoItem.getPerc_com_interno());
            content.put("PERC_COM_EXTERNO", tabelaPrecoItem.getPerc_com_externo());
            content.put("PERC_COM_EXPORTACAO", tabelaPrecoItem.getPerc_com_exportacao());
            content.put("PONTOS_PREMIACAO", tabelaPrecoItem.getPontos_premiacao());
            content.put("COR_PAINEL", tabelaPrecoItem.getCor_painel());
            content.put("COR_FONTE", tabelaPrecoItem.getCor_fonte());
            content.put("VERBA_PERC", tabelaPrecoItem.getVerba_perc());
            content.put("UTILIZA_VERBA", tabelaPrecoItem.getUtiliza_verba());
            content.put("DESCONTO_VERBA_MAX", tabelaPrecoItem.getDesconto_verba_max());
            content.put("ID_USUARIO", tabelaPrecoItem.getId_usuario());
            content.put("USUARIO", tabelaPrecoItem.getUsuario());
            content.put("USUARIO_DATA", tabelaPrecoItem.getUsuario_data());
            content.put("COR_WEB", tabelaPrecoItem.getCor_web());
            content.put("ID_CATEGORIA", tabelaPrecoItem.getIdCategoria());
            content.put("VALOR_UNITARIO", tabelaPrecoItem.getValor_unitario());
            content.put("ID_PRODUTO", tabelaPrecoItem.getId_produto());
            System.gc();
            return db.addDados("TBL_TABELA_PRECO_ITENS", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressLint("Range")
    public List<TabelaPrecoItem> getLista(String SQL) {
        List<TabelaPrecoItem> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0) {
            TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();
            tabelaPrecoItem.setPerc_desc_final("0.00");
            lista.add(tabelaPrecoItem);
            return lista;
        }
        cursor.moveToFirst();
        do {
            TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();
            try {
                tabelaPrecoItem.setId_item(cursor.getString(cursor.getColumnIndex("ID_ITEM")));
                tabelaPrecoItem.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
                tabelaPrecoItem.setPerc_desc_inicial(cursor.getString(cursor.getColumnIndex("PERC_DESC_INICIAL")));
                tabelaPrecoItem.setPerc_desc_final(cursor.getString(cursor.getColumnIndex("PERC_DESC_FINAL")));
                tabelaPrecoItem.setPerc_com_interno(cursor.getString(cursor.getColumnIndex("PERC_COM_INTERNO")));
                tabelaPrecoItem.setPerc_com_externo(cursor.getString(cursor.getColumnIndex("PERC_COM_EXTERNO")));
                tabelaPrecoItem.setPerc_com_exportacao(cursor.getString(cursor.getColumnIndex("PERC_COM_EXPORTACAO")));
                tabelaPrecoItem.setPontos_premiacao(cursor.getString(cursor.getColumnIndex("PONTOS_PREMIACAO")));
                tabelaPrecoItem.setCor_painel(cursor.getString(cursor.getColumnIndex("COR_PAINEL")));
                tabelaPrecoItem.setCor_fonte(cursor.getString(cursor.getColumnIndex("COR_FONTE")));
                tabelaPrecoItem.setVerba_perc(cursor.getString(cursor.getColumnIndex("VERBA_PERC")));
                tabelaPrecoItem.setUtiliza_verba(cursor.getString(cursor.getColumnIndex("UTILIZA_VERBA")));
                tabelaPrecoItem.setDesconto_verba_max(cursor.getString(cursor.getColumnIndex("DESCONTO_VERBA_MAX")));
                tabelaPrecoItem.setId_usuario(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
                tabelaPrecoItem.setUsuario(cursor.getString(cursor.getColumnIndex("USUARIO")));
                tabelaPrecoItem.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                tabelaPrecoItem.setCor_web(cursor.getString(cursor.getColumnIndex("COR_WEB")));
                tabelaPrecoItem.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
                lista.add(tabelaPrecoItem);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public void addListaTabelaPrecoItens(List<TabelaPrecoItem> lista, DBHelper db) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT_TABELA_PRECO_ITEM);

        try {
            database.beginTransaction();

            for (TabelaPrecoItem item : lista) {
                stmt.clearBindings();

                bindString(stmt, 1, item.getId_item());
                bindString(stmt, 2, item.getId_tabela());
                bindString(stmt, 3, item.getPerc_desc_inicial());
                bindString(stmt, 4, item.getPerc_desc_final());
                bindString(stmt, 5, item.getPerc_com_interno());
                bindString(stmt, 6, item.getPerc_com_externo());
                bindString(stmt, 7, item.getPerc_com_exportacao());
                bindString(stmt, 8, item.getPontos_premiacao());
                bindString(stmt, 9, item.getCor_painel());
                bindString(stmt, 10, item.getCor_fonte());

                bindString(stmt, 11, item.getVerba_perc());
                bindString(stmt, 12, item.getUtiliza_verba());
                bindString(stmt, 13, item.getDesconto_verba_max());
                bindString(stmt, 14, item.getId_usuario());
                bindString(stmt, 15, item.getUsuario());
                bindString(stmt, 16, item.getUsuario_data());
                bindString(stmt, 17, item.getCor_web());
                bindLong(stmt, 18, item.getIdCategoria());
                bindString(stmt, 19, item.getValor_unitario());
                bindString(stmt, 20, item.getId_produto());

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

    private void bindLong(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindLong(i, Long.parseLong(val.toString()));
    }

    private static final String SQL_INSERT_TABELA_PRECO_ITEM = "INSERT INTO TBL_TABELA_PRECO_ITENS (" +
            "ID_ITEM, ID_TABELA, PERC_DESC_INICIAL, PERC_DESC_FINAL, PERC_COM_INTERNO, " + // 1-5
            "PERC_COM_EXTERNO, PERC_COM_EXPORTACAO, PONTOS_PREMIACAO, COR_PAINEL, COR_FONTE, " + // 6-10
            "VERBA_PERC, UTILIZA_VERBA, DESCONTO_VERBA_MAX, ID_USUARIO, USUARIO, " + // 11-15
            "USUARIO_DATA, COR_WEB, ID_CATEGORIA, VALOR_UNITARIO, ID_PRODUTO" + // 16-20
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}
