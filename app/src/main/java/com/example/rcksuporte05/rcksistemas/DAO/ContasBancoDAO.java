package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Moeda;

import java.util.ArrayList;
import java.util.List;

public class ContasBancoDAO {
    private DBHelper db;

    public ContasBancoDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Generico contas) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_CONTA", contas.getId());
            content.put("ID_EMPRESA", "1");
            content.put( "NOME_CONTA", contas.getNome() );
            System.gc();
            return db.addDados("TBL_CONTAS_BANCO", content);
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
            Generico conta = new Generico();
            try {
                conta.setId(cursor.getString(cursor.getColumnIndex("ID_CONTA")));
                conta.setNome(cursor.getString(cursor.getColumnIndex("NOME_CONTA")));
                lista.add(conta);
            } catch (CursorIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }


    public long addEspecie(Generico especie) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_ESPECIE", especie.getId());
            content.put("ID_EMPRESA", "1");
            content.put( "NOME_ESPECIE", especie.getNome() );
            System.gc();
            return db.addDados("TBL_ESPECIE", content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<Generico> getListaEspecie(String SQL) {
        List<Generico> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Generico conta = new Generico();
            try {
                conta.setId(cursor.getString(cursor.getColumnIndex("ID_ESPECIE")));
                conta.setNome(cursor.getString(cursor.getColumnIndex("NOME_ESPECIE")));
                lista.add(conta);
            } catch (CursorIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }
}
