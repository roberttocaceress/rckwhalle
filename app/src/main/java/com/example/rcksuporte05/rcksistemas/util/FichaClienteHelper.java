package com.example.rcksuporte05.rcksistemas.util;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.FichaProdutoItem;

import java.util.ArrayList;
import java.util.List;

public class FichaClienteHelper {

    public static double getLimiteCredito(Cliente cliente) {
        if (cliente == null || cliente.getLimite_credito() == null) return 0.0;
        try {
            return Double.parseDouble(cliente.getLimite_credito().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static double getLimiteUtilizado(Cliente cliente) {
        if (cliente == null) return 0.0;
        double vencido = 0.0;
        double vencendo = 0.0;
        try {
            if (cliente.getFinanceiro_vencido() != null) {
                vencido = Double.parseDouble(cliente.getFinanceiro_vencido().trim());
            }
            if (cliente.getFinanceiro_vencendo() != null) {
                vencendo = Double.parseDouble(cliente.getFinanceiro_vencendo().trim());
            }
        } catch (Exception ignored) {}
        return vencido + vencendo;
    }

    public static double getSaldoDisponivel(Cliente cliente) {
        return getLimiteCredito(cliente) - getLimiteUtilizado(cliente);
    }

    public static double getSaldoDevedor(Cliente cliente) {
        if (cliente == null || cliente.getFinanceiro_vencido() == null) return 0.0;
        try {
            return Double.parseDouble(cliente.getFinanceiro_vencido().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static double getSaldoVencendo(Cliente cliente) {
        if (cliente == null || cliente.getFinanceiro_vencendo() == null) return 0.0;
        try {
            return Double.parseDouble(cliente.getFinanceiro_vencendo().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static double getTicketMedio(DBHelper db, Cliente cliente) {
        if (db == null || cliente == null) return 0.0;
        int idCadSrv = cliente.getId_cadastro_servidor();
        int idCadLoc = cliente.getId_cadastro();

        StringBuilder whereClause = new StringBuilder("(ID_CADASTRO = ").append(idCadLoc);
        if (idCadSrv > 0) {
            whereClause.append(" OR ID_CADASTRO = ").append(idCadSrv);
        }
        whereClause.append(") AND EXCLUIDO = 'N'");

        String sqlSum = "SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO WHERE " + whereClause;
        String sqlCount = "SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE " + whereClause;

        double totalFaturado = db.soma(sqlSum);
        int totalPedidos = db.contagem(sqlCount);

        return totalPedidos > 0 ? (totalFaturado / totalPedidos) : 0.0;
    }

    public static String getDataUltimaCompra(DBHelper db, Cliente cliente) {
        if (cliente == null) return "SEM COMPRA";

        if (cliente.getData_ultima_compra() != null && !cliente.getData_ultima_compra().trim().isEmpty()) {
            try {
                String dtStr = cliente.getData_ultima_compra().trim();
                if (dtStr.length() >= 10) {
                    dtStr = dtStr.substring(0, 10);
                }
                if (!dtStr.startsWith("2000") && !dtStr.startsWith("2001") && !dtStr.startsWith("1900")) {
                    return MascaraUtil.formataData(dtStr, "/");
                }
            } catch (Exception ignored) {}
        }

        if (db != null) {
            int idCadSrv = cliente.getId_cadastro_servidor();
            int idCadLoc = cliente.getId_cadastro();

            StringBuilder whereClause = new StringBuilder("(ID_CADASTRO = ").append(idCadLoc);
            if (idCadSrv > 0) {
                whereClause.append(" OR ID_CADASTRO = ").append(idCadSrv);
            }
            whereClause.append(") AND EXCLUIDO = 'N'");

            String sqlMaxDate = "SELECT MAX(DATA_EMISSAO) FROM TBL_WEB_PEDIDO WHERE " + whereClause;
            String dataMax = db.consultaDados(sqlMaxDate, "MAX(DATA_EMISSAO)");

            if (dataMax != null && !dataMax.trim().isEmpty() && !dataMax.equalsIgnoreCase("null")) {
                try {
                    String dataApenas = dataMax.trim();
                    if (dataApenas.length() >= 10) {
                        dataApenas = dataApenas.substring(0, 10);
                    }
                    return MascaraUtil.formataData(dataApenas, "/");
                } catch (Exception e) {
                    return dataMax;
                }
            }
        }

        return "SEM COMPRA";
    }

    public static String getMensagemAlertaReposicao(Cliente cliente) {
        return "⚠️ ATENÇÃO COMERCIAL: O cliente costuma repor seu estoque a cada 15 dias. A última compra foi há 19 dias (Reposição Atrasada em 4 dias!).";
    }

    public static List<FichaProdutoItem> getProdutosTopGiroLocal(DBHelper db, Cliente cliente) {
        List<FichaProdutoItem> lista = new ArrayList<>();
        if (db == null || cliente == null) return getMockProdutosPorAba("MAIS_COMPRADOS");

        int idCadSrv = cliente.getId_cadastro_servidor();
        int idCadLoc = cliente.getId_cadastro();

        StringBuilder whereCliente = new StringBuilder("(P.ID_CADASTRO = ").append(idCadLoc);
        if (idCadSrv > 0) {
            whereCliente.append(" OR P.ID_CADASTRO = ").append(idCadSrv);
        }
        whereCliente.append(") AND P.EXCLUIDO = 'N'");

        // 1. Obtém o ID do último pedido do cliente no banco local
        String sqlUltimoPedido = "SELECT P.ID_WEB_PEDIDO FROM TBL_WEB_PEDIDO P WHERE " + whereCliente.toString() +
                " ORDER BY P.DATA_EMISSAO DESC, P.ID_WEB_PEDIDO DESC LIMIT 1";
        String idUltimoPedidoStr = db.consultaDados(sqlUltimoPedido, "ID_WEB_PEDIDO");
        int idUltimoPedido = 0;
        try {
            if (idUltimoPedidoStr != null && !idUltimoPedidoStr.trim().isEmpty()) {
                idUltimoPedido = Integer.parseInt(idUltimoPedidoStr.trim());
            }
        } catch (Exception ignored) {}

        // 2. Consulta os Top 10 Produtos por Giro Financeiro Ponderado (Score = Faturamento * Recorrência)
        String sqlTop = "SELECT I.ID_PRODUTO, PR.NOME_PRODUTO, " +
                "COUNT(DISTINCT P.ID_WEB_PEDIDO) AS QTD_PEDIDOS, " +
                "SUM(I.QUANTIDADE) AS TOTAL_QTD, " +
                "AVG(I.QUANTIDADE) AS MEDIA_QTD, " +
                "SUM(I.VALOR_TOTAL) AS TOTAL_VALOR, " +
                "(SUM(I.VALOR_TOTAL) * COUNT(DISTINCT P.ID_WEB_PEDIDO)) AS SCORE_COMERCIAL " +
                "FROM TBL_WEB_PEDIDO_ITENS I " +
                "INNER JOIN TBL_WEB_PEDIDO P ON P.ID_WEB_PEDIDO = I.ID_PEDIDO " +
                "LEFT JOIN TBL_PRODUTO PR ON PR.ID_PRODUTO = I.ID_PRODUTO " +
                "WHERE " + whereCliente.toString() + " " +
                "GROUP BY I.ID_PRODUTO, PR.NOME_PRODUTO " +
                "ORDER BY SCORE_COMERCIAL DESC, TOTAL_VALOR DESC LIMIT 10";

        android.database.Cursor cursor = db.listaDados(sqlTop);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idxProd = cursor.getColumnIndex("ID_PRODUTO");
                int idxNome = cursor.getColumnIndex("NOME_PRODUTO");
                int idxMediaQtd = cursor.getColumnIndex("MEDIA_QTD");

                String idProduto = idxProd != -1 ? cursor.getString(idxProd) : "";
                String nomeProduto = idxNome != -1 && cursor.getString(idxNome) != null ? cursor.getString(idxNome) : "PRODUTO #" + idProduto;
                double mediaQtd = idxMediaQtd != -1 ? cursor.getDouble(idxMediaQtd) : 0.0;

                // 3. Consulta as 2 últimas compras deste item específico
                String sqlComprasItem = "SELECT I.QUANTIDADE, I.VALOR_UNITARIO, P.ID_WEB_PEDIDO " +
                        "FROM TBL_WEB_PEDIDO_ITENS I " +
                        "INNER JOIN TBL_WEB_PEDIDO P ON P.ID_WEB_PEDIDO = I.ID_PEDIDO " +
                        "WHERE " + whereCliente.toString() + " AND I.ID_PRODUTO = '" + idProduto + "' " +
                        "ORDER BY P.DATA_EMISSAO DESC, P.ID_WEB_PEDIDO DESC LIMIT 2";

                double ultimaQtd = 0.0;
                double penultimaQtd = 0.0;
                double valorUltimoPreco = 0.0;
                boolean noUltimoPedidoGeral = false;

                android.database.Cursor cursorItem = db.listaDados(sqlComprasItem);
                if (cursorItem != null && cursorItem.moveToFirst()) {
                    int iCount = 0;
                    do {
                        int idxQtd = cursorItem.getColumnIndex("QUANTIDADE");
                        int idxVal = cursorItem.getColumnIndex("VALOR_UNITARIO");
                        int idxPedId = cursorItem.getColumnIndex("ID_WEB_PEDIDO");

                        double q = idxQtd != -1 ? cursorItem.getDouble(idxQtd) : 0.0;
                        double v = idxVal != -1 ? cursorItem.getDouble(idxVal) : 0.0;
                        int pId = idxPedId != -1 ? cursorItem.getInt(idxPedId) : 0;

                        if (iCount == 0) {
                            valorUltimoPreco = v;
                            if (pId == idUltimoPedido) {
                                ultimaQtd = q;
                                noUltimoPedidoGeral = true;
                            } else {
                                penultimaQtd = q;
                                ultimaQtd = 0.0;
                            }
                        } else if (iCount == 1) {
                            if (noUltimoPedidoGeral) {
                                penultimaQtd = q;
                            }
                        }
                        iCount++;
                    } while (cursorItem.moveToNext());
                    cursorItem.close();
                }

                // 4. Monta Strings e Diagnóstico
                String mediaStr = String.format(java.util.Locale.US, "%.0f un/mês", mediaQtd);
                String penultimaStr = String.format(java.util.Locale.US, "%.0f un", penultimaQtd);
                String ultimaStr = String.format(java.util.Locale.US, "%.0f un", ultimaQtd);

                String statusGiro = "ESTAVEL";
                String diagnostico = "";
                double qtdSugerida = mediaQtd > 0 ? mediaQtd : 1.0;

                if (ultimaQtd <= 0) {
                    statusGiro = "ALERTA_MAXIMO";
                    ultimaStr = "0 un 🚨";
                    diagnostico = "Diagnóstico: Produto não comprado no último pedido!";
                } else if (ultimaQtd < (mediaQtd * 0.7)) {
                    statusGiro = "QUEDA_VOLUME";
                    ultimaStr = String.format(java.util.Locale.US, "%.0f un 🔻", ultimaQtd);
                    double percQueda = Math.round((1.0 - (ultimaQtd / mediaQtd)) * 100.0);
                    diagnostico = "Diagnóstico: Compra " + (int) percQueda + "% abaixo da média habitual.";
                    qtdSugerida = Math.max(1.0, mediaQtd - ultimaQtd);
                } else {
                    ultimaStr = String.format(java.util.Locale.US, "%.0f un 🟢", ultimaQtd);
                }

                String textoBotao = "Adicionar " + String.format(java.util.Locale.US, "%.0f", qtdSugerida) + " un ao Pedido";

                lista.add(new FichaProdutoItem(
                        idProduto,
                        nomeProduto,
                        mediaStr,
                        penultimaStr,
                        ultimaStr,
                        diagnostico,
                        textoBotao,
                        statusGiro,
                        valorUltimoPreco,
                        "MAIS_COMPRADOS"
                ));

            } while (cursor.moveToNext());
            cursor.close();
        }

        // Retorna a lista real encontrada no banco local (vazia se não houver compras)
        return lista;
    }

    public static List<FichaProdutoItem> getMockProdutosPorAba(String tipoAba) {
        List<FichaProdutoItem> lista = new ArrayList<>();

        if ("MAIS_COMPRADOS".equalsIgnoreCase(tipoAba)) {
            lista.add(new FichaProdutoItem(
                    "101",
                    "Arroz Tipo 1 5kg",
                    "30 cx/mês",
                    "30 cx",
                    "0 cx 🚨",
                    "Diagnóstico: Produto não comprado no último pedido!",
                    "Adicionar 30 cx ao Pedido",
                    "ALERTA_MAXIMO",
                    32.50,
                    "MAIS_COMPRADOS"
            ));

            lista.add(new FichaProdutoItem(
                    "102",
                    "Óleo de Soja 900ml",
                    "50 cx/mês",
                    "50 cx",
                    "15 cx 🔻",
                    "Diagnóstico: Compra 70% abaixo da média habitual.",
                    "Adicionar 35 cx (Sugerido)",
                    "QUEDA_VOLUME",
                    18.90,
                    "MAIS_COMPRADOS"
            ));

            lista.add(new FichaProdutoItem(
                    "103",
                    "Feijão Carioca 1kg",
                    "20 cx/mês",
                    "20 cx",
                    "20 cx 🟢",
                    "",
                    "Adicionar 20 cx",
                    "ESTAVEL",
                    14.20,
                    "MAIS_COMPRADOS"
            ));

            lista.add(new FichaProdutoItem(
                    "104",
                    "Açúcar Refinado 1kg",
                    "15 cx/mês",
                    "15 cx",
                    "15 cx 🟢",
                    "",
                    "Adicionar 15 cx",
                    "ESTAVEL",
                    9.80,
                    "MAIS_COMPRADOS"
            ));
        } else if ("OPORTUNIDADES".equalsIgnoreCase(tipoAba)) {
            lista.add(new FichaProdutoItem(
                    "201",
                    "Macarrão Espaguete 500g",
                    "25 cx/mês",
                    "25 cx",
                    "0 cx 🚨",
                    "Diagnóstico: Não pede este item há mais de 45 dias!",
                    "Adicionar 25 cx",
                    "ALERTA_MAXIMO",
                    8.50,
                    "OPORTUNIDADES"
            ));
            lista.add(new FichaProdutoItem(
                    "202",
                    "Café Torrado e Moído 500g",
                    "40 cx/mês",
                    "40 cx",
                    "10 cx 🔻",
                    "Diagnóstico: Pediu apenas 25% da média no mês anterior.",
                    "Adicionar 30 cx (Sugerido)",
                    "QUEDA_VOLUME",
                    21.90,
                    "OPORTUNIDADES"
            ));
        } else if ("SUGERIDOS".equalsIgnoreCase(tipoAba)) {
            lista.add(new FichaProdutoItem(
                    "301",
                    "Azeite de Oliva Extra Virgem 500ml",
                    "12 cx/mês",
                    "--",
                    "0 cx 🔥",
                    "Diagnóstico: Item mais vendido no segmento que este cliente ainda não possui.",
                    "Adicionar 10 cx (Teste)",
                    "ESTAVEL",
                    38.00,
                    "SUGERIDOS"
            ));
            lista.add(new FichaProdutoItem(
                    "302",
                    "Molho de Tomate Sachê 300g",
                    "30 cx/mês",
                    "--",
                    "0 cx 🔥",
                    "Diagnóstico: Lançamento do mês com alta conversão na região.",
                    "➕ Adicionar 20 cx (Lançamento)",
                    "ESTAVEL",
                    4.50,
                    "SUGERIDOS"
            ));
        }

        return lista;
    }
}