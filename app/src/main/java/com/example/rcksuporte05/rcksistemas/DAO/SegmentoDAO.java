package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.model.Segmento;

import java.util.ArrayList;
import java.util.List;

public class SegmentoDAO {
    private DBHelper db;
    public SegmentoDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Segmento segmento){
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID", segmento.getIdSetor());
            content.put("ATIVO", segmento.getAtivo());
            content.put("NOME_SETOR", segmento.getNomeSetor());
            System.gc();
            return db.addDados("TBL_CADASTRO_SETOR", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Segmento> getLista(String SQL, boolean showSpinner) {
        List<Segmento> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        Segmento segmento = new Segmento();
        if ( showSpinner) {
            segmento.setIdSetor("0");
            segmento.setAtivo("S");
            segmento.setNomeSetor("Selecione");
            lista.add(segmento);
        }
        do {
            segmento = new Segmento();
            try {
                segmento.setIdSetor(cursor.getString(cursor.getColumnIndex("ID")));
                segmento.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                segmento.setNomeSetor(cursor.getString(cursor.getColumnIndex("NOME_SETOR")));
                lista.add(segmento);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }
}
