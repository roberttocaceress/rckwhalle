package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Alerta;

import java.util.List;

public class AlertaDAO {
    private DBHelper db;
    public AlertaDAO(DBHelper db) {
        this.db = db;
    }

    public boolean add(Alerta alerta) {
        ContentValues content = new ContentValues();
        db.deleteDados("TBL_ALERTA", "1", null);
        long res = 0;
        try {
            content.put("ALERTA_EMISSAO_DOC", alerta.getAlerta_emissao_doc());
            content.put("ALERTA_VENCIMENTO_DOC", alerta.getAlerta_vencimento_doc());
            content.put("ALERTA_NOVO_APP", alerta.getAlerta_novo_app());
            db.addDados("TBL_ALERTA", content);
            UsuarioHelper.getUsuario().setAparelho_notifica_status_ped(alerta.getAlerta_emissao_doc());
            UsuarioHelper.getUsuario().setAparelho_notifica_vencto_fin(alerta.getAlerta_vencimento_doc());
            UsuarioHelper.getUsuario().setAparelho_notifica_update_app(alerta.getAlerta_novo_app());
            return true;
        } catch (NullPointerException | SQLException ignored) {
        } 
        return false;
    }

    public Alerta getAlerta(String SQL) {
        Alerta alerta = new Alerta("");
        Cursor cursor = db.listaDados(SQL);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return alerta;
        }
        try {
            if (cursor.moveToFirst())
                alerta = setAlerta(cursor);
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return alerta;
    }

    private Alerta setAlerta(Cursor cursor) {
        Alerta alerta = new Alerta("");
        try {
            alerta.setId(getString(cursor, "ID"));
            alerta.setAlerta_emissao_doc(getString(cursor, "ALERTA_EMISSAO_DOC"));
            alerta.setAlerta_vencimento_doc(getString(cursor, "ALERTA_VENCIMENTO_DOC"));
            alerta.setAlerta_novo_app(getString(cursor, "ALERTA_NOVO_APP"));
        } catch (Exception ignored) {
        }
        return alerta;
    }

    // =========================================================================
    // AUXILIARES OBRIGATÓRIOS (CURSOR NULL-SAFE)
    // =========================================================================
    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }
}
