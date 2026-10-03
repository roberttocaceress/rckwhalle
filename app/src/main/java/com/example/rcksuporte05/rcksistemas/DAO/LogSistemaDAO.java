package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.model.LogSistema;

import java.util.ArrayList;
import java.util.List;

public class LogSistemaDAO {
    private DBHelper db;

    public LogSistemaDAO(DBHelper db) {
        this.db = db;
    }

    public long add(LogSistema log) {
        SQLiteDatabase banco = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put("ID_PEDIDO", log.getIdPedido());
            values.put("ID_PEDIDO_APP", log.getIdPedidoApp());
            values.put("ID_WEB_PEDIDO_SERVIDOR", log.getIdWebPedidoServidor());
            values.put("ID_VENDEDOR", log.getIdVendedor());
            // DATA_HORA tem default no SQL, mas podemos passar se preenchido
            if (log.getDataHora() != null && !log.getDataHora().isEmpty()) {
                values.put("DATA_HORA", log.getDataHora());
            }
            values.put("TIPO_LOG", log.getTipoLog());
            values.put("EVENTO", log.getEvento());
            values.put("CLASSE", log.getClasse());
            values.put("METODO", log.getMetodo());
            values.put("ID_PRODUTO", log.getIdProduto());
            values.put("VALOR_ANTERIOR", log.getValorAnterior());
            values.put("VALOR_NOVO", log.getValorNovo());
            values.put("ERRO_DETALHE", log.getErroDetalhe());
            values.put("ST_ENVIADO", log.getStEnviado() != null ? log.getStEnviado() : "N");

            long id = banco.insert("TBL_LOG_SISTEMA", null, values);
            if (id == -1) {
                android.util.Log.e("LOG_SISTEMA", "FALHA AO INSERIR REGISTRO DE LOG NO SQLITE");
            }
            return id;
        } catch (SQLException e) {
            android.util.Log.e("LOG_SISTEMA", "ERRO SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            android.util.Log.e("LOG_SISTEMA", "ERRO GERAL: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public void updateServerId(String idPedidoLocal, String idServer) {
        SQLiteDatabase banco = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_WEB_PEDIDO_SERVIDOR", idServer);
        banco.update("TBL_LOG_SISTEMA", values, "ID_PEDIDO = ?", new String[]{idPedidoLocal});
    }

    public void markAsSent(int idLog) {
        SQLiteDatabase banco = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ST_ENVIADO", "S");
        banco.update("TBL_LOG_SISTEMA", values, "ID_LOG = ?", new String[]{String.valueOf(idLog)});
    }

    @SuppressLint("Range")
    public List<LogSistema> getAllUnsentLogs() {
        List<LogSistema> logs = new ArrayList<>();
        SQLiteDatabase banco = db.getReadableDatabase();
        Cursor cursor = banco.rawQuery("SELECT * FROM TBL_LOG_SISTEMA WHERE ST_ENVIADO = 'N'", null);

        if (cursor.moveToFirst()) {
            do {
                LogSistema log = new LogSistema();
                log.setIdLog(cursor.getInt(cursor.getColumnIndex("ID_LOG")));
                log.setIdPedido(cursor.getString(cursor.getColumnIndex("ID_PEDIDO")));
                log.setIdPedidoApp(cursor.getString(cursor.getColumnIndex("ID_PEDIDO_APP")));
                log.setIdWebPedidoServidor(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO_SERVIDOR")));
                log.setIdVendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                log.setDataHora(cursor.getString(cursor.getColumnIndex("DATA_HORA")));
                log.setTipoLog(cursor.getString(cursor.getColumnIndex("TIPO_LOG")));
                log.setEvento(cursor.getString(cursor.getColumnIndex("EVENTO")));
                log.setClasse(cursor.getString(cursor.getColumnIndex("CLASSE")));
                log.setMetodo(cursor.getString(cursor.getColumnIndex("METODO")));
                log.setIdProduto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
                log.setValorAnterior(cursor.getString(cursor.getColumnIndex("VALOR_ANTERIOR")));
                log.setValorNovo(cursor.getString(cursor.getColumnIndex("VALOR_NOVO")));
                log.setErroDetalhe(cursor.getString(cursor.getColumnIndex("ERRO_DETALHE")));
                log.setStEnviado(cursor.getString(cursor.getColumnIndex("ST_ENVIADO")));
                logs.add(log);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return logs;
    }
}
