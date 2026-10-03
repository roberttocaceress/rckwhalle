package com.example.rcksuporte05.rcksistemas.model;

import java.io.Serializable;

public class DashboardData implements Serializable {
    // SEMANA
    private double realizadoSemana = 0.0;
    private double metaSemana = 0.0;
    private double valorDevolucaoSemana = 0.0;
    private int porcentagemSemana = 0;
    private String rankingSemana = "-";
    private int semanaAtual = 0;

    // MÊS
    private double realizadoMes = 0.0;
    private double metaMes = 0.0;
    private double valorDevolucaoMes = 0.0;
    private int porcentagemMes = 0;
    private String rankingMes = "-";
    private String nomeMesExtenso = "";

    // OPERACIONAL (DADOS REAIS SQLITE)
    private int qtdPendentes = 0;
    private double valorPendentes = 0.0;
    private int qtdClientesAtivos = 0;
    private int qtdPositivados = 0;

    // OPERACIONAL (DADOS BI - FUTURO)
    private int qtdFaturados = 0;
    private int qtdCancelados = 0;
    private int qtdDevolucoesMes = 0;
    private double valorCancelados = 0.0;
    private int qtdNovosClientes = 0;
    private double ticketMedio = 0.0;
    private int porcentagemPositivacao = 0;

    // METADADOS DE SINCRONIA
    private String dataHoraAtualizacao = "";
    private boolean isOffline = false;

    public DashboardData() {}

    // GETTERS E SETTERS
    public String getDataHoraAtualizacao() { return dataHoraAtualizacao; }
    public void setDataHoraAtualizacao(String dataHoraAtualizacao) { this.dataHoraAtualizacao = dataHoraAtualizacao; }

    public boolean isOffline() { return isOffline; }
    public void setOffline(boolean offline) { isOffline = offline; }

    public double getRealizadoSemana() { return realizadoSemana; }
    public void setRealizadoSemana(double realizadoSemana) { this.realizadoSemana = realizadoSemana; }

    public double getMetaSemana() { return metaSemana; }
    public void setMetaSemana(double metaSemana) { this.metaSemana = metaSemana; }

    public double getValorDevolucaoSemana() { return valorDevolucaoSemana; }
    public void setValorDevolucaoSemana(double valorDevolucaoSemana) { this.valorDevolucaoSemana = valorDevolucaoSemana; }

    public int getPorcentagemSemana() { return porcentagemSemana; }
    public void setPorcentagemSemana(int porcentagemSemana) { this.porcentagemSemana = porcentagemSemana; }

    public String getRankingSemana() { return rankingSemana; }
    public void setRankingSemana(String rankingSemana) { this.rankingSemana = rankingSemana; }

    public int getSemanaAtual() { return semanaAtual; }
    public void setSemanaAtual(int semanaAtual) { this.semanaAtual = semanaAtual; }

    public double getRealizadoMes() { return realizadoMes; }
    public void setRealizadoMes(double realizadoMes) { this.realizadoMes = realizadoMes; }

    public double getMetaMes() { return metaMes; }
    public void setMetaMes(double metaMes) { this.metaMes = metaMes; }

    public double getValorDevolucaoMes() { return valorDevolucaoMes; }
    public void setValorDevolucaoMes(double valorDevolucaoMes) { this.valorDevolucaoMes = valorDevolucaoMes; }

    public int getPorcentagemMes() { return porcentagemMes; }
    public void setPorcentagemMes(int porcentagemMes) { this.porcentagemMes = porcentagemMes; }

    public String getRankingMes() { return rankingMes; }
    public void setRankingMes(String rankingMes) { this.rankingMes = rankingMes; }

    public String getNomeMesExtenso() { return nomeMesExtenso; }
    public void setNomeMesExtenso(String nomeMesExtenso) { this.nomeMesExtenso = nomeMesExtenso; }

    public int getQtdPendentes() { return qtdPendentes; }
    public void setQtdPendentes(int qtdPendentes) { this.qtdPendentes = qtdPendentes; }

    public double getValorPendentes() { return valorPendentes; }
    public void setValorPendentes(double valorPendentes) { this.valorPendentes = valorPendentes; }

    public int getQtdClientesAtivos() { return qtdClientesAtivos; }
    public void setQtdClientesAtivos(int qtdClientesAtivos) { this.qtdClientesAtivos = qtdClientesAtivos; }

    public int getQtdPositivados() { return qtdPositivados; }
    public void setQtdPositivados(int qtdPositivados) { this.qtdPositivados = qtdPositivados; }

    public int getQtdFaturados() { return qtdFaturados; }
    public void setQtdFaturados(int qtdFaturados) { this.qtdFaturados = qtdFaturados; }

    public int getQtdCancelados() { return qtdCancelados; }
    public void setQtdCancelados(int qtdCancelados) { this.qtdCancelados = qtdCancelados; }

    public int getQtdDevolucoesMes() { return qtdDevolucoesMes; }
    public void setQtdDevolucoesMes(int qtdDevolucoesMes) { this.qtdDevolucoesMes = qtdDevolucoesMes; }

    public double getValorCancelados() { return valorCancelados; }
    public void setValorCancelados(double valorCancelados) { this.valorCancelados = valorCancelados; }

    public int getQtdNovosClientes() { return qtdNovosClientes; }
    public void setQtdNovosClientes(int qtdNovosClientes) { this.qtdNovosClientes = qtdNovosClientes; }

    public double getTicketMedio() { return ticketMedio; }
    public void setTicketMedio(double ticketMedio) { this.ticketMedio = ticketMedio; }

    public int getPorcentagemPositivacao() { return porcentagemPositivacao; }
    public void setPorcentagemPositivacao(int porcentagemPositivacao) { this.porcentagemPositivacao = porcentagemPositivacao; }
}
