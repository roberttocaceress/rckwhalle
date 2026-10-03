package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
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
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_ITEM", motivo.getIdItem());
            content.put("MOTIVO", motivo.getMotivo());
            content.put("DESCRICAO_OUTROS", motivo.getDescricaoOutros());
            System.gc();
            if (motivo.getIdItem() != null && db.contagem("SELECT COUNT(ID_ITEM) FROM TBL_CADASTRO_MOTIVO_NAO_CAD WHERE ID_ITEM = " + motivo.getIdItem()) > 0)
                return (long) db.updateDados("TBL_CADASTRO_MOTIVO_NAO_CAD", content, "ID_ITEM = " + motivo.getIdItem());
            else
                return db.addDados("TBL_CADASTRO_MOTIVO_NAO_CAD", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<MotivoNaoCadastramento> getLista() {
        List<MotivoNaoCadastramento> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_MOTIVO_NAO_CAD ORDER BY MOTIVO");
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToNext();
        do {
            MotivoNaoCadastramento motivo = new MotivoNaoCadastramento();
            try {
                motivo.setIdItem(cursor.getString(cursor.getColumnIndex("ID_ITEM")));
                motivo.setMotivo(cursor.getString(cursor.getColumnIndex("MOTIVO")));
                motivo.setDescricaoOutros(cursor.getString(cursor.getColumnIndex("DESCRICAO_OUTROS")));
                lista.add(motivo);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public MotivoNaoCadastramento getMotivo(String idItem) {
        MotivoNaoCadastramento motivo = new MotivoNaoCadastramento();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_MOTIVO_NAO_CAD WHERE ID_ITEM = " + idItem + " ORDER BY MOTIVO");
        if (cursor.getCount() <= 0)
            return motivo;
        cursor.moveToNext();
        try {
            motivo.setIdItem(cursor.getString(cursor.getColumnIndex("ID_ITEM")));
            motivo.setMotivo(cursor.getString(cursor.getColumnIndex("MOTIVO")));
            motivo.setDescricaoOutros(cursor.getString(cursor.getColumnIndex("DESCRICAO_OUTROS")));
        } catch (CursorIndexOutOfBoundsException e) {
        }
        cursor.close();
        System.gc();
        return motivo;
    }
}
