package com.example.rcksuporte05.rcksistemas.model;

public class FichaProdutoItem {
    private String idProduto;
    private String nomeProduto;
    private double valorUltimoPreco;
    private double estoqueAtual;
    private String tagDestaque;
    private String tipoAba;

    public FichaProdutoItem(String idProduto, String nomeProduto, double valorUltimoPreco, double estoqueAtual, String tagDestaque, String tipoAba) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.valorUltimoPreco = valorUltimoPreco;
        this.estoqueAtual = estoqueAtual;
        this.tagDestaque = tagDestaque;
        this.tipoAba = tipoAba;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getValorUltimoPreco() {
        return valorUltimoPreco;
    }

    public double getEstoqueAtual() {
        return estoqueAtual;
    }

    public String getTagDestaque() {
        return tagDestaque;
    }

    public String getTipoAba() {
        return tipoAba;
    }
}