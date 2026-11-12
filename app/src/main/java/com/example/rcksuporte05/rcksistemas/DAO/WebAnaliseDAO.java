package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.WebAnalise;
import com.example.rcksuporte05.rcksistemas.model.WebVisita;
import java.util.ArrayList;
import java.util.List;

public class WebAnaliseDAO {
    private DBHelper db;

    public WebAnaliseDAO(DBHelper db) {
        this.db = db;
        verificarECriarTabela();
    }

    private void verificarECriarTabela() {
        try {
            if (db != null) {
                db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_ANALISE (" +
                        "ID_ANALISE INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ID_ANALISE_APP VARCHAR(38) NOT NULL, " +
                        "ID_ANALISE_SERVIDOR INTEGER, " +
                        "ID_VISITA_APP VARCHAR(38), " +
                        "ID_VISITA_SERVIDOR INTEGER, " +
                        "ID_PEDIDO_APP VARCHAR(38), " +
                        "ID_PEDIDO_SERVIDOR INTEGER, " +
                        "ID_VENDEDOR INTEGER NOT NULL, " +
                        "ID_CLIENTE_SERVIDOR INTEGER NOT NULL, " +
                        "DATA_HORA_ANALISE DATETIME DEFAULT (datetime('now', 'localtime')), " +
                        "STATUS_CREDITO VARCHAR(30), " +
                        "LIMITE_CREDITO DECIMAL(12, 2), " +
                        "LIMITE_DISPONIVEL DECIMAL(12, 2), " +
                        "FATURAMENTO_ANUAL DECIMAL(12, 2), " +
                        "TICKET_MEDIO DECIMAL(12, 2), " +
                        "DIAS_FREQUENCIA_HABITUAL INTEGER, " +
                        "DIAS_DESDE_ULTIMA_COMPRA INTEGER, " +
                        "STATUS_REPOSICAO VARCHAR(30), " +
                        "MENSAGEM_ALERTA VARCHAR(300), " +
                        "VERSAO_APP VARCHAR(10), " +
                        "ST_ENVIADO VARCHAR(1) DEFAULT 'N', " +
                        "EXCLUIDO VARCHAR(1) DEFAULT 'N');");

                db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_ANALISE_ITENS (" +
                        "ID_ANALISE_ITEM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ID_ANALISE_APP VARCHAR(38) NOT NULL, " +
                        "ID_ANALISE_SERVIDOR INTEGER, " +
                        "ID_PRODUTO VARCHAR(20) NOT NULL, " +
                        "NOME_PRODUTO VARCHAR(120), " +
                        "TIPO_ABA VARCHAR(30) NOT NULL, " +
                        "TAG_DESTAQUE VARCHAR(100), " +
                        "VALOR_ULTIMO_PRECO DECIMAL(12, 4), " +
                        "ESTOQUE_ATUAL DECIMAL(12, 4), " +
                        "FOI_ADICIONADO VARCHAR(1) DEFAULT 'N', " +
                        "QUANTIDADE_ADICIONADA DECIMAL(12, 4) DEFAULT 0, " +
                        "ORDEM_EXIBICAO INTEGER);");
                            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long salvarAnalise(WebAnalise analise) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_ANALISE_APP", analise.getIdAnaliseApp());
            content.put("ID_ANALISE_SERVIDOR", analise.getIdAnaliseServidor());
            content.put("ID_VISITA_APP", analise.getIdVisitaApp());
            content.put("ID_VISITA_SERVIDOR", analise.getIdVisitaServidor());
            content.put("ID_VENDEDOR", analise.getIdVendedor());
            content.put("ID_PEDIDO_APP", analise.getIdPedidoApp() == null ? "" : analise.getIdPedidoApp());
            content.put("ID_PEDIDO_SERVIDOR", analise.getIdPedidoServidor());
            content.put("ID_CLIENTE_SERVIDOR", analise.getIdClienteServidor());
            content.put("DATA_HORA_ANALISE", analise.getDataHoraAnalise());
            content.put("STATUS_CREDITO", analise.getStatusCredito() == null ? "" : analise.getStatusCredito());
            content.put("LIMITE_CREDITO", analise.getLimiteCredito());
            content.put("LIMITE_DISPONIVEL", analise.getLimiteDisponivel());
            content.put("FATURAMENTO_ANUAL", analise.getFaturamentoAnual());
            content.put("TICKET_MEDIO", analise.getTicketMedio());
            content.put("DIAS_FREQUENCIA_HABITUAL", analise.getDiasFrequenciaHabitual());
            content.put("DIAS_DESDE_ULTIMA_COMPRA", analise.getDiasDesdeUltimaCompra());
            content.put("STATUS_REPOSICAO", analise.getStatusReposicao() == null ? "" : analise.getStatusReposicao());
            content.put("MENSAGEM_ALERTA", analise.getMensagemAlerta() == null ? "" : analise.getMensagemAlerta());
            String versao = analise.getVersaoApp() != null ? analise.getVersaoApp() : "";
            if (versao.length() > 10) {
                versao = versao.substring(0, 10);
            }
            content.put("VERSAO_APP", versao);
            content.put("ST_ENVIADO", "N");
            content.put("EXCLUIDO", "N");
            return db.addDados("TBL_WEB_ANALISE", content);
        } catch (SQLException e) {
            android.util.Log.e("ANALISE_DEBUG", "Erro SQLException ao salvar analise: " + e.getMessage(), e);
        } catch (Exception e) {
            android.util.Log.e("ANALISE_DEBUG", "Erro Exception ao salvar analise: " + e.getMessage(), e);
        }
        return 0;
    }

    public int vincularPedidoAnalise(String idAnaliseApp,  String idPedidoApp) {
        ContentValues content = new ContentValues();
        content.put("ID_PEDIDO_APP", idPedidoApp == null ? "" : idPedidoApp);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp + "'");
        } catch (Exception ignored) {
        }
        return 0;
    }
    public int vincularVisitaAnalise(String idAnaliseApp,  String idVisitaApp) {
        ContentValues content = new ContentValues();
        content.put("ID_VISITA_APP", idVisitaApp == null ? "" : idVisitaApp);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp + "'");
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int atualizarClienteAnalise(String idAnaliseApp, int idNovoClienteServidor, String razaoSocial, String nomeFantasia) {
        if (idAnaliseApp == null || idAnaliseApp.trim().isEmpty()) return 0;
        ContentValues content = new ContentValues();
        content.put("ID_CLIENTE_SERVIDOR", idNovoClienteServidor);
        content.put("RAZAO_SOCIAL", razaoSocial == null ? "" : razaoSocial);
        content.put("NOME_FANTASIA", nomeFantasia == null ? "" : nomeFantasia);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp.trim() + "'");
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int vincularPedidoPorVisita(String idVisitaApp, String idPedidoApp) {
        if (idVisitaApp == null || idVisitaApp.trim().isEmpty()) return 0;
        ContentValues content = new ContentValues();
        content.put("ID_PEDIDO_APP", idPedidoApp == null ? "" : idPedidoApp);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_VISITA_APP = '" + idVisitaApp + "'");
        } catch (Exception ignored) {
        }
        return 0;
    }

    public int atualiaranaliseAPI(String idAnaliseApp, int idAnaliseServidor, String idVisitaServidor, int idPedidoServidor ) {
        ContentValues content = new ContentValues();
        content.put("ID_VISITA_SERVIDOR", idVisitaServidor);
        content.put("ID_PEDIDO_SERVIDOR", idPedidoServidor);
        content.put("ID_ANALISE_SERVIDOR", idAnaliseServidor);
        content.put("ST_ENVIADO", "S");
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp + "'");
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
            return db.updateDados("TBL_WEB_ANALISE", content, where);
        } catch (Exception ignored) {
        }
        return 0;
    }

    public WebAnalise getWebAnalisePorVisita(String idVisitaApp) {
        String sql = "";
            sql = "SELECT A.* FROM TBL_WEB_ANALISE AS A WHERE A.EXCLUIDO = 'N' AND A.ID_VISITA_APP = '" + idVisitaApp.trim() + "' ORDER BY A.ID_ANALISE DESC LIMIT 1";

        Cursor cursor = db.listaDados(sql);
        if (cursor != null && cursor.moveToFirst()) {
            WebAnalise analise = setAnalise(cursor);
            cursor.close();
            return analise;
        }
        if (cursor != null) cursor.close();
        return null;
    }
    public WebAnalise getWebAnalisePorPedido(String idPedidoApp) {
        String sql = "";
        sql = "SELECT A.* FROM TBL_WEB_ANALISE AS A WHERE A.EXCLUIDO = 'N' AND A.ID_PEDIDO_APP = '" + idPedidoApp.trim() + "' ORDER BY A.ID_ANALISE DESC LIMIT 1";

        Cursor cursor = db.listaDados(sql);
        if (cursor != null && cursor.moveToFirst()) {
            WebAnalise analise = setAnalise(cursor);
            cursor.close();
            return analise;
        }
        if (cursor != null) cursor.close();
        return null;
    }
    public WebAnalise getWebAnalisePorVisitaOuCliente(String idVisitaApp, int idClienteServidor) {
        String sql = "";
        if (idVisitaApp != null && !idVisitaApp.trim().isEmpty()) {
            sql = "SELECT A.* FROM TBL_WEB_ANALISE AS A WHERE A.EXCLUIDO = 'N' AND A.ID_VISITA_APP = '" + idVisitaApp.trim() + "' ORDER BY A.ID_ANALISE DESC LIMIT 1";
        } else if (idClienteServidor > 0) {
            sql = "SELECT A.* FROM TBL_WEB_ANALISE AS A WHERE A.EXCLUIDO = 'N' AND A.ID_CLIENTE_SERVIDOR = " + idClienteServidor + " ORDER BY A.ID_ANALISE DESC LIMIT 1";
        } else {
            return null;
        }

        Cursor cursor = db.listaDados(sql);
        if (cursor != null && cursor.moveToFirst()) {
            WebAnalise analise = setAnalise(cursor);
            cursor.close();
            return analise;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    // =========================================================================
    // MAPEAMENTO DE CURSOR (PADRÃO NULL-SAFE / GERADOR-DAO)
    // =========================================================================
    private WebAnalise setAnalise(Cursor cursor) {
        WebAnalise webAnalise = new WebAnalise();
        try {
            webAnalise.setIdAnalise(getInt(cursor, "ID_ANALISE"));
            webAnalise.setIdAnaliseApp(getString(cursor, "ID_ANALISE_APP"));
            webAnalise.setIdAnaliseServidor(getInt(cursor, "ID_ANALISE_SERVIDOR"));
            webAnalise.setIdVendedor(getInt(cursor, "ID_VENDEDOR"));
            webAnalise.setIdPedidoApp(getString(cursor, "ID_PEDIDO_APP"));
            webAnalise.setIdVisitaApp(getString(cursor, "ID_VISITA_APP"));
            webAnalise.setIdPedidoServidor(getInt(cursor, "ID_PEDIDO_SERVIDOR"));
            webAnalise.setIdClienteServidor(getInt(cursor, "ID_CLIENTE_SERVIDOR"));
            webAnalise.setDataHoraAnalise(getString(cursor, "DATA_HORA_ANALISE"));
            webAnalise.setStatusCredito(getString(cursor, "STATUS_CREDITO"));
            webAnalise.setLimiteCredito(getDouble(cursor, "LIMITE_CREDITO"));
            webAnalise.setLimiteDisponivel(getDouble(cursor, "LIMITE_DISPONIVEL"));
            webAnalise.setFaturamentoAnual(getDouble(cursor, "FATURAMENTO_ANUAL"));
            webAnalise.setTicketMedio(getDouble(cursor, "TICKET_MEDIO"));
            webAnalise.setDiasFrequenciaHabitual(getInt(cursor, "DIAS_FREQUENCIA_HABITUAL"));
            webAnalise.setDiasDesdeUltimaCompra(getInt(cursor, "DIAS_DESDE_ULTIMA_COMPRA"));
            webAnalise.setStatusReposicao(getString(cursor, "STATUS_REPOSICAO"));
            webAnalise.setMensagemAlerta(getString(cursor, "MENSAGEM_ALERTA"));
            webAnalise.setVersaoApp(getString(cursor, "VERSAO_APP"));
            webAnalise.setStEnviado(getString(cursor, "ST_ENVIADO"));
            webAnalise.setExcluido(getString(cursor, "EXCLUIDO"));
            webAnalise.setRazaoSocial(getString(cursor, "NOME_CADASTRO"));
            webAnalise.setNomeFantasia(getString(cursor, "NOME_FANTASIA"));
            webAnalise.setValorPedido(getDouble(cursor, "VALOR_PEDIDO_FAT"));
        } catch (Exception ignored) {
        }
        return webAnalise;
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

    public int getCountAnalises(String statusFilter, int idVendedor, int ano, int mes) {
        String sql = "SELECT COUNT(*) FROM TBL_WEB_ANALISE WHERE EXCLUIDO = 'N'";
        if (idVendedor > 0)
            sql += " AND ID_VENDEDOR = " + idVendedor;
        return db.contagem(sql);
    }

    public int getCountAnalises(String statusFilter, int idVendedor) {
        return getCountAnalises(statusFilter, idVendedor, 0, 0);
    }

    public int getCountAnalises(String statusFilter) {
        return getCountAnalises(statusFilter, 0, 0, 0);
    }
}
