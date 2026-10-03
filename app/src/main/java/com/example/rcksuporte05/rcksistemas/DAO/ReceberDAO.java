package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.ReceberCab;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;

import java.util.ArrayList;
import java.util.List;

public class ReceberDAO {
    private DBHelper db;
    public ReceberDAO(DBHelper db) {
        this.db = db;
    }

    public long add(ReceberCab receber) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_EMPRESA", receber.getId_empresa());
            content.put("ID_VENDEDOR", receber.getId_vendedor());
            content.put("ID_MOEDA", receber.getId_moeda());
            content.put("STATUS", receber.getStatus());
            content.put("EXCLUIDO", receber.getExcluido());
            content.put("VALOR", receber.getValor());
            content.put("USUARIO_ID", receber.getUsuario_id());
            content.put("USUARIO_NOME", receber.getUsuario_nome());
            content.put("USUARIO_DATA", receber.getUsuario_data());
            content.put("ID_CADASTRO", receber.getId_cadastro());
            content.put("DATA_VENCIMENTO", receber.getData_vencimento());
            content.put("ID_ANDROID", receber.getId_android());
            content.put("DATA_REAGENDAMENTO", receber.getData_reagendamento());
            content.put("OBSERVACOES", receber.getObservacoes());
            content.put("CHAVE_RECEBER_APP", receber.getChave_receber_app());
            try {
                if (Integer.parseInt(receber.getId_lote()) > 0) {
                    content.put("ID_LOTE", receber.getId_lote());
                }
            } catch ( NullPointerException|NumberFormatException e) {
            } catch ( Exception e) {
            }
            System.gc();
            return db.addDados("TBL_WEB_RECEBER_CAB", content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(ReceberCab receber, String id_web_receber, String id_android) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_EMPRESA", receber.getId_empresa());
            content.put("ID_VENDEDOR", receber.getId_vendedor());
            content.put("ID_MOEDA", receber.getId_moeda());
            content.put("STATUS", receber.getStatus());
            content.put("EXCLUIDO", receber.getExcluido());
            content.put("VALOR", receber.getValor());
            content.put("USUARIO_ID", receber.getUsuario_id());
            content.put("USUARIO_NOME", receber.getUsuario_nome());
            content.put("USUARIO_DATA", receber.getUsuario_data());
            content.put("ID_CADASTRO", receber.getId_cadastro());
            content.put("DATA_VENCIMENTO", receber.getData_vencimento());
            content.put("ID_ANDROID", receber.getId_android());
            content.put("DATA_REAGENDAMENTO", receber.getData_reagendamento());
            content.put("OBSERVACOES", receber.getObservacoes());
            System.gc();
            return db.updateDados("TBL_WEB_RECEBER_CAB", content, "ID_WEB_RECEBER = '" + id_web_receber + "' AND ID_ANDROID = '" + id_android + "' AND STATUS = 'N'" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(ReceberCab receber){
        ContentValues content = new ContentValues();
        try {
            content.put("ID_LOTE", receber.getId_lote());
            content.put("STATUS", receber.getStatus());
            System.gc();
            return db.updateDados("TBL_WEB_RECEBER_CAB", content, "ID_WEB_RECEBER = " + receber.getId_web_receber());
        } catch ( NullPointerException|SQLException e){
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean delete( ReceberCab receber, String params[]) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        try {
            if( db.deleteDados( "TBL_WEB_RECEBER_CAB ", "ID_WEB_RECEBER = ?  AND ID_ANDROID = ?", params ) >= 1)
                return true;
            else
                return false;
        } catch ( Exception e) {
            return false;
        }

    }


    public boolean deleteRecebimento( ReceberCab receber, String params[]) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        try {
            if( db.deleteDados( "TBL_WEB_RECEBER_CAB ", "ID_WEB_RECEBER = ?  AND STATUS = ? AND ID_ANDROID = ?", params ) >= 1)
                return true;
            else
                return false;
        } catch ( Exception e) {
            return false;
        }

    }

    public List<ReceberCab> getLista(String SQL) {
        List<ReceberCab> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            ReceberCab cobranca = new ReceberCab();
            cobranca.setId_web_receber(cursor.getString(cursor.getColumnIndex("ID_WEB_RECEBER")));
            cobranca.setId_empresa( cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            cobranca.setId_vendedor( cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
            cobranca.setId_moeda( cursor.getString(cursor.getColumnIndex("ID_MOEDA")));
            cobranca.setStatus( cursor.getString(cursor.getColumnIndex("STATUS")));
            cobranca.setExcluido( cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
            cobranca.setValor( cursor.getString(cursor.getColumnIndex("VALOR")));
            cobranca.setUsuario_id( cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            cobranca.setUsuario_nome( cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            cobranca.setUsuario_data( cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            cobranca.setId_cadastro( cursor.getString(cursor.getColumnIndex("ID_CADASTRO")));
            cobranca.setData_vencimento( cursor.getString(cursor.getColumnIndex("DATA_VENCIMENTO")));
            cobranca.setId_lote( cursor.getString(cursor.getColumnIndex("ID_LOTE")));
            cobranca.setData_reagendamento(cursor.getString(cursor.getColumnIndex("DATA_REAGENDAMENTO")));
            cobranca.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
            if ( cursor.getString(cursor.getColumnIndex("STATUS")).equalsIgnoreCase("N")) {
                cobranca.setId_lote( "0");
            }
            cobranca.setId_android(cursor.getString(cursor.getColumnIndex("ID_ANDROID")));
            cobranca.setChave_receber_app(cursor.getString(cursor.getColumnIndex("CHAVE_RECEBER_APP")));
            lista.add(cobranca);

        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;

    }

}
