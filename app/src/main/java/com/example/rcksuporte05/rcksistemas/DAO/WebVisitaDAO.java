package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.SQLException;
import com.example.rcksuporte05.rcksistemas.model.WebVisita;

public class WebVisitaDAO {
    private DBHelper db;

    public WebVisitaDAO(DBHelper db) {
        this.db = db;
        verificarECriarTabela();
    }

    private void verificarECriarTabela() {
        try {
            if (db != null) {
                db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_VISITA (" +
                        "ID_VISITA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ID_VISITA_APP VARCHAR(38), " +
                        "ID_VENDEDOR INTEGER, " +
                        "ID_PEDIDO INTEGER, " +
                        "ID_PEDIDO_APP VARCHAR(38), " +
                        "ID_PEDIDO_SERVIDOR INTEGER, " +
                        "ID_CLIENTE_SERVIDOR INTEGER, " +
                        "DATA_HORA_CHECKIN DATETIME, " +
                        "LATITUDE_CHECKIN VARCHAR(60), " +
                        "LONGITUDE_CHECKIN VARCHAR(60), " +
                        "ALTITUDE_CHECKIN VARCHAR(60), " +
                        "PRECISAO_CHECKIN VARCHAR(60), " +
                        "DATA_HORA_CHECKOUT DATETIME, " +
                        "LATITUDE_CHECKOUT VARCHAR(60), " +
                        "LONGITUDE_CHECKOUT VARCHAR(60), " +
                        "FOTO_CHECKIN_1 BLOB, " +
                        "FOTO_CHECKIN_2 BLOB, " +
                        "STATUS_VISITA VARCHAR(1), " +
                        "MOTIVO_NAO_VENDA VARCHAR(50), " +
                        "ID_MOTIVO_NAO_VENDA INTEGER, " +
                        "VERSAO_APP VARCHAR(10), " +
                        "OBSERVACAO TEXT, " +
                        "EXCLUIDO VARCHAR(1) DEFAULT 'N');");

                // Verificação individual para tabelas que foram criadas em versões anteriores sem os novos campos
                try {
                    db.getWritableDatabase().execSQL("ALTER TABLE TBL_WEB_VISITA ADD COLUMN ALTITUDE_CHECKIN VARCHAR(60);");
                } catch (Exception ignored) {}
                try {
                    db.getWritableDatabase().execSQL("ALTER TABLE TBL_WEB_VISITA ADD COLUMN PRECISAO_CHECKIN VARCHAR(60);");
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long salvarVisita(WebVisita visita) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_VISITA_APP", visita.getIdVisitaApp());
            content.put("ID_VENDEDOR", visita.getIdVendedor());
            content.put("ID_PEDIDO", visita.getIdPedido());
            content.put("ID_PEDIDO_APP", visita.getIdPedidoApp() == null ? "" : visita.getIdPedidoApp());
            content.put("ID_PEDIDO_SERVIDOR", visita.getIdPedidoServidor());
            content.put("ID_CLIENTE_SERVIDOR", visita.getIdClienteServidor());
            content.put("DATA_HORA_CHECKIN", visita.getDataHoraCheckin());
            content.put("LATITUDE_CHECKIN", visita.getLatitudeCheckin());
            content.put("LONGITUDE_CHECKIN", visita.getLongitudeCheckin());
            content.put("ALTITUDE_CHECKIN", visita.getAltitudeCheckin());
            content.put("PRECISAO_CHECKIN", visita.getPrecisaoCheckin());
            content.put("DATA_HORA_CHECKOUT", visita.getDataHoraCheckout());
            content.put("LATITUDE_CHECKOUT", visita.getLatitudeCheckout());
            content.put("LONGITUDE_CHECKOUT", visita.getLongitudeCheckout());
            content.put("FOTO_CHECKIN_1", visita.getFotoCheckin1());
            content.put("FOTO_CHECKIN_2", visita.getFotoCheckin2());
            content.put("STATUS_VISITA", visita.getStatusVisita());
            content.put("MOTIVO_NAO_VENDA", visita.getMotivoNaoVenda());
            content.put("ID_MOTIVO_NAO_VENDA", visita.getIdMotivoNaoVenda());
            content.put("VERSAO_APP", visita.getVersaoApp());
            content.put("OBSERVACAO", visita.getObservacao());
            content.put("EXCLUIDO", visita.getExcluido() == null ? "N" : visita.getExcluido());

            return db.addDados("TBL_WEB_VISITA", content);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int vincularPedidoAVisita(String idVisitaApp, int idPedido, String idPedidoApp) {
        if (idVisitaApp == null || idVisitaApp.trim().isEmpty()) return 0;
        ContentValues content = new ContentValues();
        content.put("ID_PEDIDO", idPedido);
        content.put("ID_PEDIDO_APP", idPedidoApp == null ? "" : idPedidoApp);
        content.put("STATUS_VISITA", "V"); // 'V' = Venda Efetuada
        try {
            return db.updateDados("TBL_WEB_VISITA", content, "ID_VISITA_APP = '" + idVisitaApp + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
