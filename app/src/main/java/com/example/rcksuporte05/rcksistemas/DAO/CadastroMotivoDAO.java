package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import java.util.ArrayList;
import java.util.List;

public class CadastroMotivoDAO {
    private DBHelper db;

    public CadastroMotivoDAO(DBHelper db) {
        this.db = db;
    }

    public long addUpdate(MotivoNaoCadastramento motivo) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_ITEM", motivo.getIdItem());
            content.put("MOTIVO", motivo.getMotivo());
            content.put("DESCRICAO_OUTROS", motivo.getDescricaoOutros());
            System.gc();
            if (motivo.getIdItem() != null && db.contagem("SELECT COUNT(ID_ITEM) FROM TBL_CADASTRO_MOTIVO_NAO_CAD WHERE ID_ITEM = " + motivo.getIdItem()) > 0)
                return db.updateDados("TBL_CADASTRO_MOTIVO_NAO_CAD", content, "ID_ITEM = " + motivo.getIdItem());
            else
                return db.addDados("TBL_CADASTRO_MOTIVO_NAO_CAD", content);
        } catch (NullPointerException | SQLException ignored) {
        }
        return 0;
    }

    public List<MotivoNaoCadastramento> getLista() {
        List<MotivoNaoCadastramento> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_MOTIVO_NAO_CAD ORDER BY MOTIVO");
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (cursor.moveToFirst())
                do {
                    lista.add(setCadastroMotivo(cursor));
                } while (cursor.moveToNext());
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public MotivoNaoCadastramento getMotivo(String idItem) {
        MotivoNaoCadastramento motivo = new MotivoNaoCadastramento();
        if (idItem == null || idItem.trim().isEmpty()) return motivo;
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_MOTIVO_NAO_CAD WHERE ID_ITEM = " + idItem + " ORDER BY MOTIVO");
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return motivo;
        }
        try {
            if (cursor.moveToFirst()) {
                motivo = setCadastroMotivo(cursor);
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();

        }
        return motivo;
    }

    private MotivoNaoCadastramento setCadastroMotivo(Cursor cursor) {
        MotivoNaoCadastramento motivo = new MotivoNaoCadastramento();
        try {
            motivo.setIdItem(getString(cursor, "ID_ITEM"));
            motivo.setMotivo(getString(cursor, "MOTIVO"));
            motivo.setDescricaoOutros(getString(cursor, "DESCRICAO_OUTROS"));
        } catch (Exception ignored) {
        }
        return motivo;
    }

    // =========================================================================
    // AUXILIAR NECESSÁRIO (CURSOR NULL-SAFE)
    // =========================================================================
    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }
}
