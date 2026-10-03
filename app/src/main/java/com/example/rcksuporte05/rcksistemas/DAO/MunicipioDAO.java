package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.model.Pais;

import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {

    private DBHelper db;
    public MunicipioDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Municipio municipio) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID", municipio.getId());
            content.put("NOME_MUNICIPIO", municipio.getNome_municipio());
            content.put("UF", municipio.getUf());
            System.gc();
            return db.addDados("TBL_CADASTRO_MUNICIPIO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Municipio> getLista(String SQL)  {
        List<Municipio> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Municipio municipio = new Municipio();
            try {
                municipio.setId(cursor.getString(cursor.getColumnIndex("ID")));
                municipio.setNome_municipio(cursor.getString(cursor.getColumnIndex("NOME_MUNICIPIO")));
                municipio.setUf(cursor.getString(cursor.getColumnIndex("UF")));
                lista.add(municipio);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public void addListaMunicipios(List<Municipio> lista, DBHelper db) {
        // Obtém a instância do banco (verifique se 'db' no seu código já é o banco ou o Helper)
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT_MUNICIPIO);
        try {
            database.beginTransaction(); // Inicia a transação (Essencial para Android 15)
            for (Municipio municipio : lista) {
                stmt.clearBindings();
                stmt.bindString(1, municipio.getId());
                stmt.bindString(2, municipio.getNome_municipio() == null ? "NOME NAO CADASTRADO" : municipio.getNome_municipio());
                stmt.bindString( 3, municipio.getUf() == null ? "UF" : municipio.getUf());
                stmt.executeInsert();
            }
            database.setTransactionSuccessful(); // Marca como sucesso
        } catch (Exception e) {
        } finally {
            database.endTransaction(); // Grava tudo no disco de uma vez só
        }
    }

    private static final String SQL_INSERT_MUNICIPIO = "INSERT INTO TBL_CADASTRO_MUNICIPIO (" +
            "ID, NOME_MUNICIPIO, UF" +
            ") VALUES (?, ?, ?)";
}
