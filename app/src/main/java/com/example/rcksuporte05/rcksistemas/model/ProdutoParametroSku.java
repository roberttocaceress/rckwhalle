package com.example.rcksuporte05.rcksistemas.model;

import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

public class ProdutoParametroSku {

    private int id_empresa;
    private int id_sku;
    private String id_produto;
    private String preco;
    private String fator;
    private String preco_venda_dois;
    private String nome;

    /*
    EAN               VARCHAR(30),
    USUARIO_ID        INTEGER NOT NULL,
    USUARIO_NOME      VARCHAR(60),
    USUARIO_DATE      TIMESTAMP,
    preco_venda_dois  DECIMAL(12,8)

    */

    public ProdutoParametroSku() {
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

    public String getId_produto() {
        return id_produto;
    }

    public void setId_produto(String id_produto) {
        this.id_produto = id_produto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getFator() {
        return fator;
    }

    public void setFator(String fator) {
        this.fator = fator;
    }

    public String getPreco_venda_dois() {
        return preco_venda_dois;
    }

    public void setPreco_venda_dois(String preco_venda_dois) {
        this.preco_venda_dois = preco_venda_dois;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {

        return  nome + "  -  " + MascaraUtil.mascaraReal(preco);
    }
}
