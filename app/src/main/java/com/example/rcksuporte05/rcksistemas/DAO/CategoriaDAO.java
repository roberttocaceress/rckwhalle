package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import com.example.rcksuporte05.rcksistemas.model.Categoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoriaDAO {
    private DBHelper db;

    public CategoriaDAO(DBHelper db) {
        this.db = db;
    }

    public void addUpdate(Categoria categoria) {
        ContentValues content = new ContentValues();
        content.put("ID_EMPRESA", categoria.getIdEmpresa());
        content.put("ATIVO", categoria.getAtivo());
        content.put("NOME_CATEGORIA", categoria.getNomeCategoria());
        content.put("USUARIO_ID", categoria.getUsuarioId());
        content.put("MULTI_DEVICE_ACEITA_NOVO", categoria.getMdvAceitaNovo());
        content.put("NOME_MULTI_DISPOSITIVO", categoria.getNomeMultiDispositivo());
        if (categoria.getIdCategoria() != 0 && db.contagem("SELECT COUNT(ID_CATEGORIA) FROM TBL_CADASTRO_CATEGORIA WHERE ID_CATEGORIA = " + categoria.getIdCategoria()) > 0) {
            content.put("ID_CATEGORIA", categoria.getIdCategoria());
            db.updateDados("TBL_CADASTRO_CATEGORIA", content, "ID_CATEGORIA = " + categoria.getIdCategoria());
        } else {
            content.put("ID_CATEGORIA", categoria.getIdCategoria());
            db.addDados("TBL_CADASTRO_CATEGORIA", content);
        }
    }

    public List<Categoria> getLista(boolean showSpinner) {
        List<Categoria> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA ORDER BY ID_CATEGORIA;");
        if ( cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        Categoria categoria = new Categoria();
        if ( showSpinner) {
            categoria.setIdCategoria(0);
            categoria.setIdEmpresa(1);
            categoria.setAtivo("S");
            categoria.setNomeCategoria("Selecione");
            categoria.setUsuarioId("0");
            categoria.setMdvAceitaNovo("S");
            lista.add(categoria);
        }
        do {
            categoria = new Categoria();
            try {
                categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
                categoria.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
                categoria.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                categoria.setNomeCategoria(cursor.getString(cursor.getColumnIndex("NOME_MULTI_DISPOSITIVO")));
                categoria.setUsuarioId(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
                categoria.setMdvAceitaNovo(cursor.getString(cursor.getColumnIndex("MULTI_DEVICE_ACEITA_NOVO")));
                lista.add(categoria);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        return lista;
    }

    public List<Categoria> getListaNovo(boolean showSpinner) {
        List<Categoria> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA WHERE MULTI_DEVICE_ACEITA_NOVO = 'S' ORDER BY ID_CATEGORIA;");
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        Categoria categoria = new Categoria();
        if ( showSpinner) {
            categoria.setIdCategoria(0);
            categoria.setIdEmpresa(1);
            categoria.setAtivo("S");
            categoria.setNomeCategoria("Selecione");
            categoria.setUsuarioId("0");
            categoria.setMdvAceitaNovo("S");
            lista.add(categoria);
        }
        do {
           categoria = new Categoria();
            try {
                categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
                categoria.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
                categoria.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                categoria.setNomeCategoria(cursor.getString(cursor.getColumnIndex("NOME_MULTI_DISPOSITIVO")));
                categoria.setUsuarioId(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
                categoria.setMdvAceitaNovo(cursor.getString(cursor.getColumnIndex("MULTI_DEVICE_ACEITA_NOVO")));
                lista.add(categoria);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        return lista;
    }

    public HashMap<Integer, Categoria> listaHashCategoria() {
        HashMap<Integer, Categoria> lista = new HashMap<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA ORDER BY ID_CATEGORIA;");
        if ( cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Categoria categoria = new Categoria();
            try {
                categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
                categoria.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
                categoria.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                categoria.setNomeCategoria(cursor.getString(cursor.getColumnIndex("NOME_MULTI_DISPOSITIVO")));
                categoria.setUsuarioId(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
                categoria.setMdvAceitaNovo(cursor.getString(cursor.getColumnIndex("MULTI_DEVICE_ACEITA_NOVO")));
                lista.put(categoria.getIdCategoria(), categoria);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        return lista;
    }

    public Categoria getCategoria(int idCategoria) {
        Categoria categoria = new Categoria();
        //List<Categoria> listaCategoria = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA WHERE ID_CATEGORIA = " + idCategoria + ";");
        if (cursor.getCount() <= 0)
            return categoria;
        cursor.moveToFirst();
        //do {
        try {
            categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
            categoria.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            categoria.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            categoria.setNomeCategoria(cursor.getString(cursor.getColumnIndex("NOME_MULTI_DISPOSITIVO")));
            categoria.setUsuarioId(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            categoria.setMdvAceitaNovo(cursor.getString(cursor.getColumnIndex("MULTI_DEVICE_ACEITA_NOVO")));
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        //listaCategoria.add(categoria);
        //} while (cursor.moveToNext());
        return categoria;//listaCategoria.get(0);
    }
    public Categoria getCategoriaPDF(int idCategoria) {
        Categoria categoria = new Categoria();
        //List<Categoria> listaCategoria = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA WHERE ID_CATEGORIA = " + idCategoria + ";");
        if (cursor.getCount() <= 0)
            return null;
        cursor.moveToFirst();
        //do {
        try {
            categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
            categoria.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            categoria.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            categoria.setNomeCategoria(cursor.getString(cursor.getColumnIndex("NOME_MULTI_DISPOSITIVO")));
            categoria.setUsuarioId(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            categoria.setMdvAceitaNovo(cursor.getString(cursor.getColumnIndex("MULTI_DEVICE_ACEITA_NOVO")));
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
        //listaCategoria.add(categoria);
        //} while (cursor.moveToNext());
        return categoria;//listaCategoria.get(0);
    }
}
