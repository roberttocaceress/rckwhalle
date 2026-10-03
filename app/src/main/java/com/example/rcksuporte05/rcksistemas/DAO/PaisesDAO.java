package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Pais;

import java.util.ArrayList;
import java.util.List;

public class PaisesDAO {
    private DBHelper db;
    public PaisesDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Pais pais) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_PAIS", pais.getId_pais());
            content.put("NOME_PAIS", pais.getNome_pais());
            System.gc();
            return db.addDados("TBL_PAISES", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Pais> getLista()  {
        List<Pais> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_PAISES");
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Pais pais = new Pais();
            try {
                pais.setId_pais(cursor.getString(cursor.getColumnIndex("ID_PAIS")));
                pais.setNome_pais(cursor.getString(cursor.getColumnIndex("NOME_PAIS")));
                lista.add(pais);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }
}
