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