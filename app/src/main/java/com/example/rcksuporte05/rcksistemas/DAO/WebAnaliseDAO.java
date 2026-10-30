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
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int vincularPedidoAnalise(String idAnaliseApp,  String idPedidoApp) {
        ContentValues content = new ContentValues();
        content.put("ID_PEDIDO_APP", idPedidoApp == null ? "" : idPedidoApp);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int vincularVisitaAnalise(String idAnaliseApp,  String idVisitaApp) {
        ContentValues content = new ContentValues();
        content.put("ID_VISITA_APP", idVisitaApp == null ? "" : idVisitaApp);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int vincularPedidoPorVisita(String idVisitaApp, String idPedidoApp) {
        if (idVisitaApp == null || idVisitaApp.trim().isEmpty()) return 0;
        ContentValues content = new ContentValues();
        content.put("ID_PEDIDO_APP", idPedidoApp == null ? "" : idPedidoApp);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_VISITA_APP = '" + idVisitaApp + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int atualiaranaliseAPI(String idAnaliseApp, String idVisitaServidor, int idPedidoServidor ) {
        ContentValues content = new ContentValues();
        content.put("ID_VISITA_SERVIDOR", idVisitaServidor);
        content.put("ID_PEDIDO_SERVIDOR", idPedidoServidor);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int atualizarExcluidoPorPedido(int idPedido, String idPedidoApp, String statusExcluido) {
        ContentValues content = new ContentValues();
        content.put("EXCLUIDO", statusExcluido == null ? "N" : statusExcluido);
        try {
            String where = "ID_PEDIDO = " + idPedido;
            if (idPedidoApp != null && !idPedidoApp.trim().isEmpty()) {
                where += " OR ID_PEDIDO_APP = '" + idPedidoApp + "'";
            }
            return db.updateDados("TBL_WEB_ANALISE", content, where);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int atualizarClienteAnalise(String idAnaliseApp, int idNovoClienteServidor) {
        if (idAnaliseApp == null || idAnaliseApp.trim().isEmpty()) return 0;
        ContentValues content = new ContentValues();
        content.put("ID_CLIENTE_SERVIDOR", idNovoClienteServidor);
        try {
            return db.updateDados("TBL_WEB_ANALISE", content, "ID_ANALISE_APP = '" + idAnaliseApp + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<WebAnalise> getListaAnalise(String filtroStatus, int idVendedor, int ano, int mes) {
        List<WebAnalise> lista = new ArrayList<>();
        String sql = "SELECT A.*, C.NOME_CADASTRO, C.NOME_FANTASIA, P.VALOR_TOTAL AS VALOR_PEDIDO_FAT " +
                "FROM TBL_WEB_ANALISE AS A " +
                "LEFT JOIN TBL_CADASTRO AS C ON (C.ID_CADASTRO_SERVIDOR = A.ID_CLIENTE_SERVIDOR OR C.ID_CADASTRO = A.ID_CLIENTE_SERVIDOR) " +
                "LEFT JOIN TBL_WEB_PEDIDO AS P ON (P.ID_WEB_PEDIDO = A.ID_PEDIDO OR (P.ID_PEDIDO_APP = A.ID_PEDIDO_APP AND P.ID_PEDIDO_APP <> '')) " +
                "WHERE A.EXCLUIDO = 'N'";

        if (idVendedor > 0) {
            sql += " AND A.ID_VENDEDOR = " + idVendedor;
        }
       /*
        if (ano > 0 && mes > 0) {
            String mesAnoStr = String.format(java.util.Locale.US, "%04d-%02d", ano, mes);
            sql += " AND (A.DATA_HORA_CHECKIN LIKE '" + mesAnoStr + "%' OR A.DATA_HORA_CHECKIN LIKE '%/" + String.format(java.util.Locale.US, "%02d/%04d", mes, ano) + "%')";
        }

        if ("V".equalsIgnoreCase(filtroStatus)) {
            sql += " AND V.STATUS_VISITA = 'V'";
        } else if ("N".equalsIgnoreCase(filtroStatus)) {
            sql += " AND V.STATUS_VISITA = 'N'";
        }
        */
        sql += " ORDER BY A.ID_ANALISE DESC";


        Cursor cursor = db.listaDados(sql);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }

        if (cursor.moveToFirst()) {
            do {
               lista.add(setAnalise(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lista;
    }
    public List<WebAnalise> getListaAnalises() {
        List<WebAnalise> lista = new ArrayList<>();
        if ( UsuarioHelper.getUsuario() ==null) {
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        }
        String sql = "SELECT A.* " +
                "FROM TBL_WEB_ANALISE AS A WHERE A.EXCLUIDO = 'N' AND A.ID_VENDEDOR =  " + UsuarioHelper.getUsuario().getId_quando_vendedor();
        sql += " ORDER BY A.ID_ANALISE DESC";

        Cursor cursor = db.listaDados(sql);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }

        if (cursor.moveToFirst()) {
            do {
               lista.add(setAnalise(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lista;
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

    public WebAnalise getWebAnalise(String idPedidoApp, int idCliente) {
        //WebAnalise webAnalise = new WebAnalise();
        if ( UsuarioHelper.getUsuario() ==null) {
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        }
        String sql = "";
        if ( idCliente > 0)
           sql = "SELECT A.* " +
                "FROM TBL_WEB_ANALISE AS A WHERE A.EXCLUIDO = 'N' " +
                "AND A.ID_VENDEDOR =  " + UsuarioHelper.getUsuario().getId_quando_vendedor() + " " +
                "AND A.ID_PEDIDO_APP = '" + idPedidoApp + "' AND A.ID_CLIENTE_SERVIDOR = " + idCliente;
        else
            sql = "SELECT A.* " +
                    "FROM TBL_WEB_ANALISE  AS A WHERE A.EXCLUIDO = 'N' " +
                    "AND A.ID_ANALISE_SERVIDOR IS NULL AND A.ID_VENDEDOR =  " + UsuarioHelper.getUsuario().getId_quando_vendedor() + " " +
                    "AND A.ID_PEDIDO_APP = '" + idPedidoApp + "'";

        Cursor cursor = db.listaDados(sql);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
        }

        if (cursor.moveToFirst()) {
            do {
                return setAnalise(cursor);
            } while (cursor.moveToNext());
        }
        return null;
    }

    private WebAnalise setAnalise(Cursor cursor) {
        WebAnalise webAnalise = new WebAnalise();
        try {
            int idxIdAnalise = cursor.getColumnIndex("ID_ANALISE");
            if (idxIdAnalise != -1) webAnalise.setIdAnalise(cursor.getInt(idxIdAnalise));

            int idxAnaliseApp = cursor.getColumnIndex("ID_ANALISE_APP");
            if (idxAnaliseApp != -1) webAnalise.setIdAnaliseApp(cursor.getString(idxAnaliseApp));

            int idxAnaliseSrv = cursor.getColumnIndex("ID_ANALISE_SERVIDOR");
            if (idxAnaliseSrv != -1) webAnalise.setIdAnaliseServidor(cursor.getInt(idxAnaliseSrv));

            int idxVend = cursor.getColumnIndex("ID_VENDEDOR");
            if (idxVend != -1) webAnalise.setIdVendedor(cursor.getInt(idxVend));

            int idxPedApp = cursor.getColumnIndex("ID_PEDIDO_APP");
            if (idxPedApp != -1) webAnalise.setIdPedidoApp(cursor.getString(idxPedApp));

            int idxPedSrv = cursor.getColumnIndex("ID_PEDIDO_SERVIDOR");
            try {
                if (idxPedSrv != -1)
                    webAnalise.setIdPedidoServidor(Integer.parseInt(cursor.getString(idxPedSrv)));
            } catch (NumberFormatException | NullPointerException e) {
                webAnalise.setIdPedidoServidor(0);
            }

            int idxCli = cursor.getColumnIndex("ID_CLIENTE_SERVIDOR");
            if (idxCli != -1) webAnalise.setIdClienteServidor(cursor.getInt(idxCli));

            int dataHoraAnalise = cursor.getColumnIndex("DATA_HORA_ANALISE");
            if (dataHoraAnalise != -1 && cursor.getString(dataHoraAnalise) != null) {
                webAnalise.setDataHoraAnalise(cursor.getString(dataHoraAnalise));
            }
            int idxStatusCredito = cursor.getColumnIndex("STATUS_CREDITO");
            if (idxStatusCredito != -1 && cursor.getString(idxStatusCredito) != null) {
                webAnalise.setStatusCredito(cursor.getString(idxStatusCredito));
            }

            int idxLimiteCredito = cursor.getColumnIndex("LIMITE_CREDITO");
            if (idxLimiteCredito != -1 && cursor.getString(idxLimiteCredito) != null) {
                webAnalise.setLimiteCredito(cursor.getDouble(idxLimiteCredito));
            }

            int idxLimiteDisponivel = cursor.getColumnIndex("LIMITE_DISPONIVEL");
            if (idxLimiteDisponivel != -1 && cursor.getString(idxLimiteDisponivel) != null) {
                webAnalise.setLimiteDisponivel(cursor.getDouble(idxLimiteDisponivel));
            }
            int idxFaturamentoAnual = cursor.getColumnIndex("FATURAMENTO_ANUAL");
            if (idxFaturamentoAnual != -1 && cursor.getString(idxFaturamentoAnual) != null) {
                webAnalise.setFaturamentoAnual(cursor.getDouble(idxFaturamentoAnual));
            }
            int idxTicketMedio = cursor.getColumnIndex("TICKET_MEDIO");
            if (idxTicketMedio != -1 && cursor.getString(idxTicketMedio) != null) {
                webAnalise.setTicketMedio(cursor.getDouble(idxTicketMedio));
            }
            int idxDiasFrequencia = cursor.getColumnIndex("DIAS_FREQUENCIA_HABITUAL");
            if (idxDiasFrequencia != -1 && cursor.getString(idxDiasFrequencia) != null) {
                webAnalise.setDiasFrequenciaHabitual(cursor.getInt(idxDiasFrequencia));
            }
            int idxDiasUltimaCompra = cursor.getColumnIndex("DIAS_DESDE_ULTIMA_COMPRA");
            if (idxDiasUltimaCompra != -1 && cursor.getString(idxDiasUltimaCompra) != null) {
                webAnalise.setDiasDesdeUltimaCompra(cursor.getInt(idxDiasUltimaCompra));
            }
            int idxStatusReposicao = cursor.getColumnIndex("STATUS_REPOSICAO");
            if (idxStatusReposicao != -1 && cursor.getString(idxStatusReposicao) != null) {
                webAnalise.setStatusReposicao(cursor.getString(idxStatusReposicao));
            }
            int idxMensagemAlerta = cursor.getColumnIndex("MENSAGEM_ALERTA");
            if (idxMensagemAlerta != -1 && cursor.getString(idxMensagemAlerta) != null) {
                webAnalise.setMensagemAlerta(cursor.getString(idxMensagemAlerta));
            }
            int idxVersaoApp = cursor.getColumnIndex("VERSAO_APP");
            if (idxVersaoApp != -1 && cursor.getString(idxVersaoApp) != null) {
                try {
                    webAnalise.setVersaoApp(cursor.getString(idxVersaoApp));
                } catch (Exception ignored) {
                }
            }

            int idxStEnviado = cursor.getColumnIndex("ST_ENVIADO");
            if (idxStEnviado != -1 && cursor.getString(idxStEnviado) != null) {
                webAnalise.setStEnviado(cursor.getString(idxStEnviado));
            }
            int idxExcluido = cursor.getColumnIndex("EXCLUIDO");
            if (idxExcluido != -1 && cursor.getString(idxExcluido) != null) {
                webAnalise.setExcluido(cursor.getString(idxExcluido));
            }

            int idxRazao = cursor.getColumnIndex("NOME_CADASTRO");
            if (idxRazao != -1 && cursor.getString(idxRazao) != null) {
                webAnalise.setRazaoSocial(cursor.getString(idxRazao));
            }

            int idxFantasia = cursor.getColumnIndex("NOME_FANTASIA");
            if (idxFantasia != -1 && cursor.getString(idxFantasia) != null) {
                webAnalise.setNomeFantasia(cursor.getString(idxFantasia));
            }

            int idxValPed = cursor.getColumnIndex("VALOR_PEDIDO_FAT");
            if (idxValPed != -1 && cursor.getString(idxValPed) != null) {
                try {
                    webAnalise.setValorPedido(Double.parseDouble(cursor.getString(idxValPed)));
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webAnalise;
    }

    public int getCountAnalises(String statusFilter, int idVendedor, int ano, int mes) {
        String sql = "SELECT COUNT(*) FROM TBL_WEB_ANALISE WHERE EXCLUIDO = 'N'";
        if (idVendedor > 0) {
            sql += " AND ID_VENDEDOR = " + idVendedor;
        }
        return db.contagem(sql);
    }

    public int getCountAnalises(String statusFilter, int idVendedor) {
        return getCountAnalises(statusFilter, idVendedor, 0, 0);
    }

    public int getCountAnalises(String statusFilter) {
        return getCountAnalises(statusFilter, 0, 0, 0);
    }
}
