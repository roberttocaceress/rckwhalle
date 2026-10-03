package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.CobrancaStatus;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;

import java.util.ArrayList;
import java.util.List;

public class CobrancaStatusDAO {
    private DBHelper db;

    public CobrancaStatusDAO(DBHelper db) {
        this.db = db;
    }

    public long add(CobrancaStatus cobrancaStatus) {
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", cobrancaStatus.getAtivo());
            content.put("ID_STATUS",  cobrancaStatus.getId_status());
            content.put("NOME_STATUS", cobrancaStatus.getNome_status());
            content.put("EXCLUIDO", cobrancaStatus.getExcluido());
            System.gc();
            return db.addDados("TBL_COBRANCA_STATUS", content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressLint("Range")
    public List<CobrancaStatus> getLista(String SQL) {
        List<CobrancaStatus> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            CobrancaStatus cobrancaStatus = new CobrancaStatus();
            try {
                cobrancaStatus.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                cobrancaStatus.setId_status(cursor.getInt(cursor.getColumnIndex("ID_STATUS")));
                cobrancaStatus.setNome_status(cursor.getString(cursor.getColumnIndex("NOME_STATUS")));
                cobrancaStatus.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
                lista.add(cobrancaStatus);
            } catch (CursorIndexOutOfBoundsException |NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }


}
