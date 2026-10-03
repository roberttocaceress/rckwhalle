package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

public class LoginDAO {
    private DBHelper db;
    public LoginDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Usuario usuario) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_LOGIN", 1);
            content.put("LOGIN", usuario.getLogin());
            content.put("SENHA", usuario.getSenha());
            content.put("LOGADO", usuario.getLogado());
            content.put("APARELHO_ID", usuario.getAparelho_id());
            content.put("TOKEN", usuario.getToken());
            content.put("USUARIO_ID_CIDADE", usuario.getUsuario_id_cidade());
            content.put("USUARIO_UF", usuario.getUsuario_uf());
            content.put("USUARIO_LEMBRETE_SINCRONIA", usuario.getUsuario_lembrete_sincronia());
            content.put("USUARIO_LEMBRETE_SINCRONIA_DATA", usuario.getUsuario_lembrete_sincronia_data());
            System.gc();
            return db.addDados("TBL_LOGIN", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Usuario usuario) {
        ContentValues content = new ContentValues();
        try {
            content.put("LOGIN", usuario.getLogin());
            content.put("SENHA", usuario.getSenha());
            content.put("LOGADO", usuario.getLogado());
            content.put("APARELHO_ID", usuario.getAparelho_id());
            content.put("TOKEN", usuario.getToken());
            content.put("USUARIO_ID_CIDADE", usuario.getUsuario_id_cidade());
            content.put("USUARIO_UF", usuario.getUsuario_uf());
            content.put("USUARIO_LEMBRETE_SINCRONIA", usuario.getUsuario_lembrete_sincronia());
            content.put("USUARIO_LEMBRETE_SINCRONIA_DATA", usuario.getUsuario_lembrete_sincronia_data());
            System.gc();
            return db.updateDados("TBL_LOGIN", content, "ID_LOGIN = " + 1);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Usuario getUsuarioLocal(String SQL) {
        Usuario usuario = new Usuario();

        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return null;
        cursor.moveToFirst();
        do {
            try {
                usuario.setUsuario_id_cidade(cursor.getInt(cursor.getColumnIndex("USUARIO_ID_CIDADE")));
                usuario.setUsuario_uf(cursor.getString(cursor.getColumnIndex("USUARIO_UF")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return usuario;

    }
}
