package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.Pais;

import java.util.ArrayList;
import java.util.List;

public class MoedaDAO {
    private DBHelper db;

    public MoedaDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Moeda moeda) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID", moeda.getId_moeda());
            content.put("NOME_MOEDA", moeda.getNome_moeda());
            return db.addDados("TBL_MOEDA", content);
        } catch (SQLException ignored) {
        }
        return 0;
    }
    @SuppressLint("Range")
    public List<Moeda> getLista(String SQL) {
        List<Moeda> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Moeda moeda = new Moeda();
            try {
                moeda.setId_moeda(cursor.getString(cursor.getColumnIndex("ID")));
                moeda.setNome_moeda(cursor.getString(cursor.getColumnIndex("NOME_MOEDA")));
                lista.add(moeda);
            } catch (CursorIndexOutOfBoundsException ignored) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        return lista;
    }
}
