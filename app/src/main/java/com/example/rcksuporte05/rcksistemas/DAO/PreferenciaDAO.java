package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Preferencia;

public class PreferenciaDAO {
    private DBHelper db;
    public PreferenciaDAO(DBHelper db) {
        this.db = db;
    }

    public boolean add(Preferencia preferencia) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        try {
            db.deleteDados("TBL_PREFERENCIA", "1", null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues content = new ContentValues();
        content.put("DIAS_HIST_VENDA", preferencia.getDias_hist_venda());
        content.put("DIAS_HIST_FINANCEIRO", preferencia.getDias_hist_financeiro());
        content.put("DIAS_HIST_PEDIDO", preferencia.getDias_hist_pedido());
        try {
            if ( db.addDados("TBL_PREFERENCIA", content) > 0){
                return  true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Preferencia getPreferencia(String SQL) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        Cursor cursor = db.listaDados(SQL);
        Preferencia preferencia = new Preferencia();
        if (cursor.getCount() <= 0) {
            preferencia.setId("");
            return preferencia;
        }
        try {
            if (cursor.moveToNext()) {
                preferencia.setId(cursor.getString(cursor.getColumnIndex("ID")));
                preferencia.setDias_hist_venda(cursor.getInt(cursor.getColumnIndex("DIAS_HIST_VENDA")));
                preferencia.setDias_hist_financeiro(cursor.getInt(cursor.getColumnIndex("DIAS_HIST_FINANCEIRO")));
                preferencia.setDias_hist_pedido(cursor.getInt(cursor.getColumnIndex("DIAS_HIST_PEDIDO")));
            } else
                preferencia.setId("");
        } catch (SQLException e) {
            db.onUpgrade( db.getWritableDatabase(), 27, 27);
            cursor = db.listaDados(SQL);
            if (cursor.moveToNext()) {
                preferencia.setId(cursor.getString(cursor.getColumnIndex("ID")));
                preferencia.setDias_hist_venda(cursor.getInt(cursor.getColumnIndex("DIAS_HIST_VENDA")));
                preferencia.setDias_hist_financeiro(cursor.getInt(cursor.getColumnIndex("DIAS_HIST_FINANCEIRO")));
                preferencia.setDias_hist_pedido(cursor.getInt(cursor.getColumnIndex("DIAS_HIST_PEDIDO")));
            } else
                preferencia.setId("");
        }
        return preferencia;
    }

}
