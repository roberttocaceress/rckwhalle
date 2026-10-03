package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Moeda;

import java.util.ArrayList;
import java.util.List;

public class BairroDAO {
    private DBHelper db;

    public BairroDAO(DBHelper db) {
        this.db = db;
    }


    public long add(Generico bairro) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID", bairro.getId());
            content.put("ID_CIDADE", bairro.getId_generico());  //id DA CIDADE
            content.put("NOME_BAIRRO", bairro.getNome());
            content.put("ORDEM", bairro.getOrdem());
            content.put("ID_REGIAO", bairro.getId_regiao());
            System.gc();
            return db.addDados("TBL_CADASTRO_BAIRRO", content);
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
            Generico bairro = new Generico();
            try {
                bairro.setId(cursor.getString(cursor.getColumnIndex("ID")));
                bairro.setId_generico(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                bairro.setNome(cursor.getString(cursor.getColumnIndex("NOME_BAIRRO")));
                bairro.setOrdem(cursor.getInt(cursor.getColumnIndex("ORDEM")));
                bairro.setId_regiao(cursor.getInt(cursor.getColumnIndex("ID_REGIAO")));
                lista.add(bairro);
            } catch (CursorIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }
    public List<Generico> getLista(String SQL, boolean addTodos) {
        List<Generico> lista = new ArrayList<>();
        Generico bairro = new Generico();
        bairro.setId("0");
        bairro.setId_regiao(0);
        bairro.setNome("Todos(toque para selecionar)");
        lista.add(bairro);
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            bairro = new Generico();
            try {
                bairro.setId(cursor.getString(cursor.getColumnIndex("ID")));
                bairro.setId_generico(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                bairro.setNome(cursor.getString(cursor.getColumnIndex("NOME_BAIRRO")));
                bairro.setOrdem(cursor.getInt(cursor.getColumnIndex("ORDEM")));
                bairro.setId_regiao(cursor.getInt(cursor.getColumnIndex("ID_REGIAO")));
                bairro.setChecked(false);
                lista.add(bairro);
            } catch (CursorIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }
}
