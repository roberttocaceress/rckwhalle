package com.example.rcksuporte05.rcksistemas.model;

public class WebAnaliseItens {
    private Integer idAnaliseItem;
    private String idAnaliseApp;
    private Integer idAnaliseServidor;
    private String idProduto;
    private String nomeProduto;
    private String tipoAba;
    private String tagDestaque;
    private Double valorUltimoPreco;
    private Double estoqueAtual;
    private String foiAdicionado;
    private Double quantidadeAdicionada;
    private Integer ordemExibicao;

    public Integer getIdAnaliseItem() {
        return idAnaliseItem;
    }

    public void setIdAnaliseItem(Integer idAnaliseItem) {
        this.idAnaliseItem = idAnaliseItem;
    }

    public String getIdAnaliseApp() {
        return idAnaliseApp;
    }

    public void setIdAnaliseApp(String idAnaliseApp) {
        this.idAnaliseApp = idAnaliseApp;
    }

    public Integer getIdAnaliseServidor() {
        return idAnaliseServidor;
    }

    public void setIdAnaliseServidor(Integer idAnaliseServidor) {
        this.idAnaliseServidor = idAnaliseServidor;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getTipoAba() {
        return tipoAba;
    }

    public void setTipoAba(String tipoAba) {
        this.tipoAba = tipoAba;
    }

    public String getTagDestaque() {
        return tagDestaque;
    }

    public void setTagDestaque(String tagDestaque) {
        this.tagDestaque = tagDestaque;
    }

    public Double getValorUltimoPreco() {
        return valorUltimoPreco;
    }

    public void setValorUltimoPreco(Double valorUltimoPreco) {
        this.valorUltimoPreco = valorUltimoPreco;
    }

    public Double getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(Double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public String getFoiAdicionado() {
        return foiAdicionado;
    }

    public void setFoiAdicionado(String foiAdicionado) {
        this.foiAdicionado = foiAdicionado;
    }

    public Double getQuantidadeAdicionada() {
        return quantidadeAdicionada;
    }

    public void setQuantidadeAdicionada(Double quantidadeAdicionada) {
        this.quantidadeAdicionada = quantidadeAdicionada;
    }

    public Integer getOrdemExibicao() {
        return ordemExibicao;
    }

    public void setOrdemExibicao(Integer ordemExibicao) {
        this.ordemExibicao = ordemExibicao;
    }
}