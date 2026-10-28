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

    public static double getTicketMedio(DBHelper db, Cliente cliente) {
        if (db == null || cliente == null) return 0.0;
        int idCadSrv = cliente.getId_cadastro_servidor();
        int idCadLoc = cliente.getId_cadastro();

        StringBuilder whereClause = new StringBuilder("(ID_CADASTRO = ").append(idCadLoc);
        if (idCadSrv > 0) {
            whereClause.append(" OR ID_CADASTRO = ").append(idCadSrv);
        }
        whereClause.append(") AND EXCLUIDO = 'N'");

        String sqlSum = "SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO WHERE " + whereClause.toString();
        String sqlCount = "SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE " + whereClause.toString();

        double totalFaturado = db.soma(sqlSum);
        int totalPedidos = db.contagem(sqlCount);

        return totalPedidos > 0 ? (totalFaturado / totalPedidos) : 0.0;
    }

    public static String getDataUltimaCompra(DBHelper db, Cliente cliente) {
        if (db == null || cliente == null) return "Não informada";
        int idCadSrv = cliente.getId_cadastro_servidor();
        int idCadLoc = cliente.getId_cadastro();

        StringBuilder whereClause = new StringBuilder("(ID_CADASTRO = ").append(idCadLoc);
        if (idCadSrv > 0) {
            whereClause.append(" OR ID_CADASTRO = ").append(idCadSrv);
        }
        whereClause.append(") AND EXCLUIDO = 'N'");

        String sqlMaxDate = "SELECT MAX(DATA_EMISSAO) FROM TBL_WEB_PEDIDO WHERE " + whereClause.toString();
        String dataMax = db.consultaDados(sqlMaxDate, "MAX(DATA_EMISSAO)");

        if (dataMax == null || dataMax.trim().isEmpty() || dataMax.equalsIgnoreCase("null")) {
            return "Não informada";
        }

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

    public static String getMensagemAlertaReposicao(Cliente cliente) {
        return "⚠️ ATENÇÃO COMERCIAL: O cliente costuma repor seu estoque a cada 15 dias. A última compra foi há 19 dias (Reposição Atrasada em 4 dias!).";
    }

    public static List<FichaProdutoItem> getMockProdutosPorAba(String tipoAba) {
        List<FichaProdutoItem> lista = new ArrayList<>();

        if ("MAIS_COMPRADOS".equalsIgnoreCase(tipoAba)) {
            lista.add(new FichaProdutoItem("101", "ÓLEO LUBRIFICANTE 20W50 1L", 28.90, 140.0, "🏆 Comprado 48x", "MAIS_COMPRADOS"));
            lista.add(new FichaProdutoItem("102", "FILTRO DE SANGUE DIESEL HL", 18.50, 85.0, "🏆 Comprado 32x", "MAIS_COMPRADOS"));
            lista.add(new FichaProdutoItem("103", "ADITIVO PARA RADIADOR 1L RED", 14.20, 210.0, "🏆 Comprado 29x", "MAIS_COMPRADOS"));
            lista.add(new FichaProdutoItem("104", "GRAXA MULTIUSO CASTANHA 1KG", 34.00, 45.0, "🏆 Comprado 21x", "MAIS_COMPRADOS"));
        } else if ("OPORTUNIDADES".equalsIgnoreCase(tipoAba)) {
            lista.add(new FichaProdutoItem("201", "PASTA PARA LIMPEZA DE MÃOS 500G", 12.80, 60.0, "⚠️ Não compra há 38 dias", "OPORTUNIDADES"));
            lista.add(new FichaProdutoItem("202", "DESENGRIPANTE SPRAY 300ML", 16.90, 110.0, "⚠️ Não compra há 42 dias", "OPORTUNIDADES"));
            lista.add(new FichaProdutoItem("203", "PALHETA LIMPADOR PARA-BRISA 22\"", 22.50, 30.0, "⚠️ Não compra há 55 dias", "OPORTUNIDADES"));
        } else if ("SUGERIDOS".equalsIgnoreCase(tipoAba)) {
            lista.add(new FichaProdutoItem("301", "CERA AUTOMOTIVA CRISTALIZADORA 500ML", 38.00, 50.0, "🔥 Top Vendas na Região", "SUGERIDOS"));
            lista.add(new FichaProdutoItem("302", "SHAMPOO AUTOMOTIVO COM CERA 2L", 24.90, 95.0, "🔥 Lançamento do Mês", "SUGERIDOS"));
            lista.add(new FichaProdutoItem("303", "SILICONE SPRAY PERFUMADO 400ML", 19.90, 120.0, "🔥 Destaque do Segmento", "SUGERIDOS"));
        }

        return lista;
    }
}