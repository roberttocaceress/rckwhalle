package com.example.rcksuporte05.rcksistemas.model;

public class FichaProdutoItem {
    private String idProduto;
    private String nomeProduto;
    private String mediaMensal;
    private String penultimaCompra;
    private String ultimaCompra;
    private String diagnostico;
    private String textoBotao;
    private String statusGiro; // "ALERTA_MAXIMO", "QUEDA_VOLUME", "ESTAVEL"
    private double valorUltimoPreco;
    private String tipoAba;

    public FichaProdutoItem(String idProduto, String nomeProduto, String mediaMensal, String penultimaCompra,
                            String ultimaCompra, String diagnostico, String textoBotao, String statusGiro,
                            double valorUltimoPreco, String tipoAba) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.mediaMensal = mediaMensal;
        this.penultimaCompra = penultimaCompra;
        this.ultimaCompra = ultimaCompra;
        this.diagnostico = diagnostico;
        this.textoBotao = textoBotao;
        this.statusGiro = statusGiro;
        this.valorUltimoPreco = valorUltimoPreco;
        this.tipoAba = tipoAba;
    }

    public String getIdProduto() { return idProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public String getMediaMensal() { return mediaMensal; }
    public String getPenultimaCompra() { return penultimaCompra; }
    public String getUltimaCompra() { return ultimaCompra; }
    public String getDiagnostico() { return diagnostico; }
    public String getTextoBotao() { return textoBotao; }
    public String getStatusGiro() { return statusGiro; }
    public double getValorUltimoPreco() { return valorUltimoPreco; }
    public String getTipoAba() { return tipoAba; }
}