package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

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
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (showSpinner) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(0);
                categoria.setIdEmpresa(1);
                categoria.setAtivo("S");
                categoria.setNomeCategoria("Selecione");
                categoria.setUsuarioId("0");
                categoria.setMdvAceitaNovo("S");
                lista.add(categoria);
            }
            if (cursor.moveToFirst()) {
                do {
                    lista.add(setCategoria(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public List<Categoria> getListaNovo(boolean showSpinner) {
        List<Categoria> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA WHERE MULTI_DEVICE_ACEITA_NOVO = 'S' ORDER BY ID_CATEGORIA;");
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (showSpinner) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(0);
                categoria.setIdEmpresa(1);
                categoria.setAtivo("S");
                categoria.setNomeCategoria("Selecione");
                categoria.setUsuarioId("0");
                categoria.setMdvAceitaNovo("S");
                lista.add(categoria);
            }
            if (cursor.moveToFirst()) {
                do {
                    lista.add(setCategoria(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public HashMap<Integer, Categoria> listaHashCategoria() {
        HashMap<Integer, Categoria> lista = new HashMap<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA ORDER BY ID_CATEGORIA;");
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    Categoria categoria = setCategoria(cursor);
                    lista.put(categoria.getIdCategoria(), categoria);
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public Categoria getCategoria(int idCategoria) {
        Categoria categoria = new Categoria();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA WHERE ID_CATEGORIA = " + idCategoria + ";");
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return categoria;
        }
        try {
            if (cursor.moveToFirst())
                categoria = setCategoria(cursor);
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return categoria;
    }

    public Categoria getCategoriaPDF(int idCategoria) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA WHERE ID_CATEGORIA = " + idCategoria + ";");
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return null;
        }
        try {
            if (cursor.moveToFirst())
                return setCategoria(cursor);
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    private Categoria setCategoria(Cursor cursor) {
        Categoria categoria = new Categoria();
        try {
            categoria.setIdCategoria(getInt(cursor, "ID_CATEGORIA"));
            categoria.setIdEmpresa(getInt(cursor, "ID_EMPRESA"));
            categoria.setAtivo(getString(cursor, "ATIVO"));
            categoria.setNomeCategoria(getString(cursor, "NOME_MULTI_DISPOSITIVO"));
            categoria.setUsuarioId(getString(cursor, "USUARIO_ID"));
            categoria.setMdvAceitaNovo(getString(cursor, "MULTI_DEVICE_ACEITA_NOVO"));
        } catch (Exception ignored) {
        }
        return categoria;
    }

    // =========================================================================
    // AUXILIARES NECESSÁRIOS (CURSOR NULL-SAFE)
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
