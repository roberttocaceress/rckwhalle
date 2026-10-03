package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Generico;

import java.util.ArrayList;
import java.util.List;

public class RegiaoDAO {
    private DBHelper db;

    public RegiaoDAO(DBHelper db) {
        this.db = db;
    }


    public long add(Generico regiao) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID", regiao.getId());
            content.put("NOME_REGIAO", regiao.getNome());
            content.put("ID_VENDEDOR", regiao.getId_generico());
           // content.put("ID_CIDADE", regiao.getId_cidade());

            System.gc();
            return db.addDados("TBL_CADASTRO_REGIAO", content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<Generico> getLista(String SQL) {
        List<Generico> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Generico regiao = new Generico();
            try {
                regiao.setId(cursor.getString(cursor.getColumnIndex("ID")));
                regiao.setNome(cursor.getString(cursor.getColumnIndex("NOME_REGIAO")));
                regiao.setId_generico(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                //regiao.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                lista.add(regiao);
            } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }


}
