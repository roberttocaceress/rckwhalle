package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Generico;

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
            content.put("ID_CIDADE", bairro.getId_generico()); // id DA CIDADE
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
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    lista.add(setBairro(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return lista;
    }

    public List<Generico> getLista(String SQL, boolean addTodos) {
        List<Generico> lista = new ArrayList<>();
        if (addTodos) {
            Generico bairroTodos = new Generico();
            bairroTodos.setId("0");
            bairroTodos.setId_regiao(0);
            bairroTodos.setNome("Todos(toque para selecionar)");
            lista.add(bairroTodos);
        }
        Cursor cursor = db.listaDados(SQL);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    Generico bairro = setBairro(cursor);
                    lista.add(bairro);
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    private Generico setBairro(Cursor cursor) {
        Generico bairro = new Generico();
        try {
            bairro.setId(getString(cursor, "ID"));
            bairro.setId_generico(getString(cursor, "ID_CIDADE"));
            bairro.setNome(getString(cursor, "NOME_BAIRRO"));
            bairro.setOrdem(getInt(cursor, "ORDEM"));
            bairro.setId_regiao(getInt(cursor, "ID_REGIAO"));
            bairro.setChecked(false);
        } catch (Exception ignored) {
        }
        return bairro;
    }

    // =========================================================================
    // AUXILIARES OBRIGATÓRIOS (CURSOR NULL-SAFE)
    // =========================================================================
    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }

    private int getInt(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0;
        try {
            return Integer.parseInt(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
