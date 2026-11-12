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

                try {
                    db.getWritableDatabase().execSQL("ALTER TABLE TBL_WEB_VISITA ADD COLUMN ALTITUDE_CHECKIN VARCHAR(60);");
                } catch (Exception ignored) {}
                try {
                    db.getWritableDatabase().execSQL("ALTER TABLE TBL_WEB_VISITA ADD COLUMN PRECISAO_CHECKIN VARCHAR(60);");
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {
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
            content.put("VERSAO_APP", visita.getVersaoApp().substring(0,7));
            content.put("OBSERVACAO", visita.getObservacao());
            content.put("EXCLUIDO", visita.getExcluido() == null ? "N" : visita.getExcluido());

            return db.addDados("TBL_WEB_VISITA", content);
        } catch (SQLException ignored) {
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
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int atualiarVisitaAPI(String idVisitaApp, String idVisitaServidor, int idPedidoServidor ) {
        ContentValues content = new ContentValues();
        content.put("ID_VISITA_SERVIDOR", idVisitaServidor);
        content.put("ID_PEDIDO_SERVIDOR", idPedidoServidor);
        try {
            return db.updateDados("TBL_WEB_VISITA", content, "ID_VISITA_APP = '" + idVisitaApp + "'");
        } catch (Exception ignored) {
        }
        return 0;
    }
    public int atualizarExcluidoPorPedido(int idPedido, String idPedidoApp, String statusExcluido) {
        ContentValues content = new ContentValues();
        content.put("EXCLUIDO", statusExcluido == null ? "N" : statusExcluido);
        try {
            String where = "ID_PEDIDO = " + idPedido;
            if (idPedidoApp != null && !idPedidoApp.trim().isEmpty())
                where += " OR ID_PEDIDO_APP = '" + idPedidoApp + "'";
            return db.updateDados("TBL_WEB_VISITA", content, where);
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int atualizarClienteVisita(String idVisitaApp, int idNovoClienteServidor) {
        if (idVisitaApp == null || idVisitaApp.trim().isEmpty()) return 0;
        ContentValues content = new ContentValues();
        content.put("ID_CLIENTE_SERVIDOR", idNovoClienteServidor);
        try {
            return db.updateDados("TBL_WEB_VISITA", content, "ID_VISITA_APP = '" + idVisitaApp + "'");
        } catch (Exception ignored) {
        }
        return 0;
    }

    public List<WebVisita> getListaVisitas(String filtroStatus, int idVendedor, int ano, int mes) {
        List<WebVisita> lista = new java.util.ArrayList<>();
        String sql = "SELECT V.*, C.NOME_CADASTRO, C.NOME_FANTASIA, P.VALOR_TOTAL AS VALOR_PEDIDO_FAT " +
                "FROM TBL_WEB_VISITA AS V " +
                "LEFT JOIN TBL_CADASTRO AS C ON (C.ID_CADASTRO_SERVIDOR = V.ID_CLIENTE_SERVIDOR OR C.ID_CADASTRO = V.ID_CLIENTE_SERVIDOR) " +
                "LEFT JOIN TBL_WEB_PEDIDO AS P ON (P.ID_PEDIDO_APP = V.ID_PEDIDO_APP AND P.ID_PEDIDO_APP <> '') " +
                "WHERE V.EXCLUIDO = 'N'";

        if (idVendedor > 0)
            sql += " AND V.ID_VENDEDOR = " + idVendedor;

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
    public List<WebVisita> getListaVisitasSincronia() {
        List<WebVisita> lista = new java.util.ArrayList<>();
        if ( UsuarioHelper.getUsuario() == null)
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
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
                WebVisita webVisita = setVisita(cursor);
                webVisita.setWebAnalise(new WebAnaliseDAO(db).getWebAnalisePorVisita(webVisita.getIdVisitaApp()));
                lista.add(webVisita);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lista;
    }

    public WebVisita getWebVisita(String idPedidoApp) {
        if ( UsuarioHelper.getUsuario() ==null)
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        String sql = "";
        sql = "SELECT V.* " +
                    "FROM TBL_WEB_VISITA AS V WHERE V.STATUS_VISITA = 'V' AND V.EXCLUIDO = 'N' " +
                    "AND V.ID_VISITA_SERVIDOR IS NULL AND V.ID_VENDEDOR =  " + UsuarioHelper.getUsuario().getId_quando_vendedor() + " " +
                    "AND V.ID_PEDIDO_APP = '" + idPedidoApp + "'";

        android.database.Cursor cursor = db.listaDados(sql);
        if (cursor == null || cursor.getCount() <= 0)
            if (cursor != null) cursor.close();

        assert cursor != null;
        if (cursor.moveToFirst()) {
           WebVisita webVisita = setVisita(cursor);
           webVisita.setWebAnalise(new WebAnaliseDAO(db).getWebAnalisePorPedido(webVisita.getIdPedidoApp()));
           return webVisita;
        }
        return null;
    }

    public WebVisita getWebVisita(String idPedidoApp, int idCliente) {
        //WebVisita webVisita = new WebVisita();
        if ( UsuarioHelper.getUsuario() ==null)
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        String sql = "";
        if ( idCliente > 0)
           sql = "SELECT V.* " +
                "FROM TBL_WEB_VISITA AS V WHERE V.STATUS_VISITA = 'V' AND V.EXCLUIDO = 'N' " +
                "AND V.ID_VISITA_SERVIDOR IS NULL AND V.ID_VENDEDOR =  " + UsuarioHelper.getUsuario().getId_quando_vendedor() + " " +
                "AND V.ID_PEDIDO_APP = '" + idPedidoApp + "' AND ID_CLIENTE_SERVIDOR = " + idCliente;
        else
            sql = "SELECT V.* " +
                    "FROM TBL_WEB_VISITA AS V WHERE V.STATUS_VISITA = 'V' AND V.EXCLUIDO = 'N' " +
                    "AND V.ID_VISITA_SERVIDOR IS NULL AND V.ID_VENDEDOR =  " + UsuarioHelper.getUsuario().getId_quando_vendedor() + " " +
                    "AND V.ID_PEDIDO_APP = '" + idPedidoApp + "'";

        android.database.Cursor cursor = db.listaDados(sql);
        if (cursor == null || cursor.getCount() <= 0)
            if (cursor != null) cursor.close();

        assert cursor != null;
        if (cursor.moveToFirst())
           return setVisita(cursor);
        return null;
    }

    private WebVisita setVisita(Cursor cursor) {
        WebVisita webVisita = new WebVisita();
        try {
            webVisita.setIdVisita(getInt(cursor, "ID_VISITA"));
            webVisita.setIdVisitaApp(getString(cursor, "ID_VISITA_APP"));
            webVisita.setIdVendedor(getInt(cursor, "ID_VENDEDOR"));
            webVisita.setIdPedido(getInt(cursor, "ID_PEDIDO"));
            webVisita.setIdPedidoApp(getString(cursor, "ID_PEDIDO_APP"));
            webVisita.setIdPedidoServidor(getInt(cursor, "ID_PEDIDO_SERVIDOR"));
            webVisita.setIdClienteServidor(getInt(cursor, "ID_CLIENTE_SERVIDOR"));
            webVisita.setDataHoraCheckin(getString(cursor, "DATA_HORA_CHECKIN"));
            webVisita.setLatitudeCheckin(getString(cursor, "LATITUDE_CHECKIN"));
            webVisita.setLongitudeCheckin(getString(cursor, "LONGITUDE_CHECKIN"));
            webVisita.setAltitudeCheckin(getString(cursor, "ALTITUDE_CHECKIN"));
            webVisita.setPrecisaoCheckin(getString(cursor, "PRECISAO_CHECKIN"));
            webVisita.setStatusVisita(getString(cursor, "STATUS_VISITA"));
            webVisita.setMotivoNaoVenda(getString(cursor, "MOTIVO_NAO_VENDA"));
            webVisita.setObservacao(getString(cursor, "OBSERVACAO"));

            String razao = getString(cursor, "NOME_CADASTRO");
            if (razao != null) webVisita.setRazaoSocial(razao);

            String fantasia = getString(cursor, "NOME_FANTASIA");
            if (fantasia != null) webVisita.setNomeFantasia(fantasia);

            String valPedStr = getString(cursor, "VALOR_PEDIDO_FAT");
            if (valPedStr != null) {
                try {
                    String vStr = valPedStr.replaceAll("[^0-9,.-]", "").trim();
                    if (vStr.contains(",") && vStr.contains(".")) {
                        vStr = vStr.replace(".", "").replace(",", ".");
                    } else if (vStr.contains(",")) {
                        vStr = vStr.replace(",", ".");
                    }
                    webVisita.setValorPedido(Double.parseDouble(vStr));
                } catch (Exception ignored) {}
            }

            String visSrv = getString(cursor, "ID_VISITA_SERVIDOR");
            if (visSrv != null) webVisita.setIdVisitaServidor(visSrv);

            String versao = getString(cursor, "VERSAO_APP");
            if (versao != null) webVisita.setVersaoApp(versao);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return webVisita;
    }

    // =========================================================================
    // AUXILIARES OBRIGATÓRIOS (CURSOR NULL-SAFE)
    // =========================================================================
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

    private long getLong(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0L;
        try {
            return Long.parseLong(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private double getDouble(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0.0;
        try {
            return Double.parseDouble(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0.0;
        }
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
