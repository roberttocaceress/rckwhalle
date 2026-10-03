package com.example.rcksuporte05.rcksistemas.model;

public class ProdutoSku {

    private String ativo;
    private int id_empresa;
    private int id_sku;
    private String nome_sku;
    //private int USUARIO_ID;
    //private String USUARIO_NOME;
    //USUARIO_DATE  TIMESTAMP


    public ProdutoSku() {
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_sku() {
        return id_sku;
    }

    public void setId_sku(int id_sku) {
        this.id_sku = id_sku;
    }

    public String getNome_sku() {
        return nome_sku;
    }

    public void setNome_sku(String nome_sku) {
        this.nome_sku = nome_sku;
    }
}
