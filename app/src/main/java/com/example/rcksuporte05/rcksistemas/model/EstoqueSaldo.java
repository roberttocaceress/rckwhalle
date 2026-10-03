package com.example.rcksuporte05.rcksistemas.model;

public class EstoqueSaldo {
    private String id_produto;
    private String id_empresa;
    private String nome_produto;
    private String entradas;
    private String saidas;
    private String saldo;
    private String reservado;
    private String unidade;
    private String cod_em_barras;
    private  String ativo;

    public String getId_produto() {
        return id_produto;
    }

    public void setId_produto(String id_produto) {
        this.id_produto = id_produto;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getEntradas() {
        return entradas;
    }

    public void setEntradas(String entradas) {
        this.entradas = entradas;
    }

    public String getSaidas() {
        return saidas;
    }

    public void setSaidas(String saidas) {
        this.saidas = saidas;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getReservado() {
        return reservado;
    }

    public void setReservado(String reservado) {
        this.reservado = reservado;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getCod_em_barras() {
        return cod_em_barras;
    }

    public void setCod_em_barras(String cod_em_barras) {
        this.cod_em_barras = cod_em_barras;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}
