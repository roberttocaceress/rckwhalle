package com.example.rcksuporte05.rcksistemas.DAO;

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
            if (log.getDataHora() != null && !log.getDataHora().isEmpty()) 
                values.put("DATA_HORA", log.getDataHora());
            values.put("TIPO_LOG", log.getTipoLog());
            values.put("EVENTO", log.getEvento());
            values.put("CLASSE", log.getClasse());
            values.put("METODO", log.getMetodo());
            values.put("ID_PRODUTO", log.getIdProduto());
            values.put("VALOR_ANTERIOR", log.getValorAnterior());
            values.put("VALOR_NOVO", log.getValorNovo());
            values.put("ERRO_DETALHE", log.getErroDetalhe());
            values.put("ST_ENVIADO", log.getStEnviado() != null ? log.getStEnviado() : "N");

            return banco.insert("TBL_LOG_SISTEMA", null, values);
        } catch (SQLException e) {
            android.util.Log.e("LOG_SISTEMA", "ERRO SQL: " + e.getMessage());
        } catch (Exception e) {
            android.util.Log.e("LOG_SISTEMA", "ERRO GERAL: " + e.getMessage());
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

    public List<LogSistema> getAllUnsentLogs() {
        List<LogSistema> logs = new ArrayList<>();
        SQLiteDatabase banco = db.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = banco.rawQuery("SELECT * FROM TBL_LOG_SISTEMA WHERE ST_ENVIADO = 'N'", null);
            if (cursor.moveToFirst())
                do {
                    logs.add(setLogSistema(cursor));
                } while (cursor.moveToNext());
        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return logs;
    }

    private LogSistema setLogSistema(Cursor cursor) {
        LogSistema log = new LogSistema();
        try {
            log.setIdLog(getInt(cursor, "ID_LOG"));
            log.setIdPedido(getString(cursor, "ID_PEDIDO"));
            log.setIdPedidoApp(getString(cursor, "ID_PEDIDO_APP"));
            log.setIdWebPedidoServidor(getString(cursor, "ID_WEB_PEDIDO_SERVIDOR"));
            log.setIdVendedor(getString(cursor, "ID_VENDEDOR"));
            log.setDataHora(getString(cursor, "DATA_HORA"));
            log.setTipoLog(getString(cursor, "TIPO_LOG"));
            log.setEvento(getString(cursor, "EVENTO"));
            log.setClasse(getString(cursor, "CLASSE"));
            log.setMetodo(getString(cursor, "METODO"));
            log.setIdProduto(getString(cursor, "ID_PRODUTO"));
            log.setValorAnterior(getString(cursor, "VALOR_ANTERIOR"));
            log.setValorNovo(getString(cursor, "VALOR_NOVO"));
            log.setErroDetalhe(getString(cursor, "ERRO_DETALHE"));
            log.setStEnviado(getString(cursor, "ST_ENVIADO"));
        } catch (Exception ignored) {
        }
        return log;
    }

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
