package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.WebVisita;
import java.util.ArrayList;
import java.util.List;

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

    public List<WebVisita> getListaVisitas(String filtroStatus, int idVendedor, int ano, int mes) {
        List<WebVisita> lista = new java.util.ArrayList<>();
        String sql = "SELECT V.*, C.NOME_CADASTRO, C.NOME_FANTASIA, P.VALOR_TOTAL AS VALOR_PEDIDO_FAT " +
                "FROM TBL_WEB_VISITA AS V " +
                "LEFT JOIN TBL_CADASTRO AS C ON (C.ID_CADASTRO_SERVIDOR = V.ID_CLIENTE_SERVIDOR OR C.ID_CADASTRO = V.ID_CLIENTE_SERVIDOR) " +
                "LEFT JOIN TBL_WEB_PEDIDO AS P ON (P.ID_WEB_PEDIDO = V.ID_PEDIDO OR (P.ID_PEDIDO_APP = V.ID_PEDIDO_APP AND P.ID_PEDIDO_APP <> '')) " +
                "WHERE V.EXCLUIDO = 'N'";

        if (idVendedor > 0) {
            sql += " AND V.ID_VENDEDOR = " + idVendedor;
        }

        if (ano > 0 && mes > 0) {
            String mesAnoStr = String.format(java.util.Locale.US, "%04d-%02d", ano, mes);
            sql += " AND (V.DATA_HORA_CHECKIN LIKE '" + mesAnoStr + "%' OR V.DATA_HORA_CHECKIN LIKE '%/" + String.format(java.util.Locale.US, "%02d/%04d", mes, ano) + "%')";
        }

        if ("V".equalsIgnoreCase(filtroStatus)) {
            sql += " AND V.STATUS_VISITA = 'V'";
        } else if ("N".equalsIgnoreCase(filtroStatus)) {
            sql += " AND V.STATUS_VISITA = 'N'";
        }
        sql += " ORDER BY V.ID_VISITA DESC";

        android.database.Cursor cursor = db.listaDados(sql);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }

        if (cursor.moveToFirst()) {
            do {
               lista.add(setVisita(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lista;
    }
    public List<WebVisita> getListaVisitas() {
        List<WebVisita> lista = new java.util.ArrayList<>();
        if ( UsuarioHelper.getUsuario() ==null) {
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        }
        String sql = "SELECT V.* " +
                "FROM TBL_WEB_VISITA AS V WHERE V.STATUS_VISITA = 'N' AND V.EXCLUIDO = 'N' AND V.ID_VISITA_SERVIDOR IS NULL AND V.ID_VENDEDOR =  " + UsuarioHelper.getUsuario().getId_quando_vendedor();
        sql += " ORDER BY V.ID_VISITA DESC";

        android.database.Cursor cursor = db.listaDados(sql);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }

        if (cursor.moveToFirst()) {
            do {
               lista.add(setVisita(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lista;
    }

    private WebVisita setVisita(Cursor cursor) {
        WebVisita webVisita = new WebVisita();
        try {
            int idxIdVisita = cursor.getColumnIndex("ID_VISITA");
            if (idxIdVisita != -1) webVisita.setIdVisita(cursor.getInt(idxIdVisita));

            int idxVisitaApp = cursor.getColumnIndex("ID_VISITA_APP");
            if (idxVisitaApp != -1) webVisita.setIdVisitaApp(cursor.getString(idxVisitaApp));

            int idxVend = cursor.getColumnIndex("ID_VENDEDOR");
            if (idxVend != -1) webVisita.setIdVendedor(cursor.getInt(idxVend));

            int idxPed = cursor.getColumnIndex("ID_PEDIDO");
            if (idxPed != -1) webVisita.setIdPedido(cursor.getInt(idxPed));

            int idxPedApp = cursor.getColumnIndex("ID_PEDIDO_APP");
            if (idxPedApp != -1) webVisita.setIdPedidoApp(cursor.getString(idxPedApp));

            int idxCli = cursor.getColumnIndex("ID_CLIENTE_SERVIDOR");
            if (idxCli != -1) webVisita.setIdClienteServidor(cursor.getInt(idxCli));

            int idxData = cursor.getColumnIndex("DATA_HORA_CHECKIN");
            if (idxData != -1) webVisita.setDataHoraCheckin(cursor.getString(idxData));

            int idxLat = cursor.getColumnIndex("LATITUDE_CHECKIN");
            if (idxLat != -1) webVisita.setLatitudeCheckin(cursor.getString(idxLat));

            int idxLng = cursor.getColumnIndex("LONGITUDE_CHECKIN");
            if (idxLng != -1) webVisita.setLongitudeCheckin(cursor.getString(idxLng));

            int idxAlt = cursor.getColumnIndex("ALTITUDE_CHECKIN");
            if (idxAlt != -1) webVisita.setAltitudeCheckin(cursor.getString(idxAlt));

            int idxPrec = cursor.getColumnIndex("PRECISAO_CHECKIN");
            if (idxPrec != -1) webVisita.setPrecisaoCheckin(cursor.getString(idxPrec));

            int idxStatus = cursor.getColumnIndex("STATUS_VISITA");
            if (idxStatus != -1) webVisita.setStatusVisita(cursor.getString(idxStatus));

            int idxMotivo = cursor.getColumnIndex("MOTIVO_NAO_VENDA");
            if (idxMotivo != -1) webVisita.setMotivoNaoVenda(cursor.getString(idxMotivo));

            int idxObs = cursor.getColumnIndex("OBSERVACAO");
            if (idxObs != -1) webVisita.setObservacao(cursor.getString(idxObs));

            int idxRazao = cursor.getColumnIndex("NOME_CADASTRO");
            if (idxRazao != -1 && cursor.getString(idxRazao) != null) {
                webVisita.setRazaoSocial(cursor.getString(idxRazao));
            }

            int idxFantasia = cursor.getColumnIndex("NOME_FANTASIA");
            if (idxFantasia != -1 && cursor.getString(idxFantasia) != null) {
                webVisita.setNomeFantasia(cursor.getString(idxFantasia));
            }

            int idxValPed = cursor.getColumnIndex("VALOR_PEDIDO_FAT");
            if (idxValPed != -1 && cursor.getString(idxValPed) != null) {
                try {
                    webVisita.setValorPedido(Double.parseDouble(cursor.getString(idxValPed)));
                } catch (Exception ignored) {
                }
            }
            int idxValIdVisitaServidor = cursor.getColumnIndex("ID_VISITA_SERVIDOR");
            if (idxValIdVisitaServidor != -1 && cursor.getString(idxValIdVisitaServidor) != null) {
                try {
                    webVisita.setValorPedido(Double.parseDouble(cursor.getString(idxValIdVisitaServidor)));
                } catch (Exception ignored) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return webVisita;
    }

    public int getCountVisitas(String statusFilter, int idVendedor, int ano, int mes) {
        String sql = "SELECT COUNT(*) FROM TBL_WEB_VISITA WHERE EXCLUIDO = 'N'";
        if (idVendedor > 0) {
            sql += " AND ID_VENDEDOR = " + idVendedor;
        }
        if (ano > 0 && mes > 0) {
            String mesAnoStr = String.format(java.util.Locale.US, "%04d-%02d", ano, mes);
            sql += " AND (DATA_HORA_CHECKIN LIKE '" + mesAnoStr + "%' OR DATA_HORA_CHECKIN LIKE '%/" + String.format(java.util.Locale.US, "%02d/%04d", mes, ano) + "%')";
        }
        if ("V".equalsIgnoreCase(statusFilter)) {
            sql += " AND STATUS_VISITA = 'V'";
        } else if ("N".equalsIgnoreCase(statusFilter)) {
            sql += " AND STATUS_VISITA = 'N'";
        }
        return db.contagem(sql);
    }

    public int getCountVisitas(String statusFilter, int idVendedor) {
        return getCountVisitas(statusFilter, idVendedor, 0, 0);
    }

    public int getCountVisitas(String statusFilter) {
        return getCountVisitas(statusFilter, 0, 0, 0);
    }
}
