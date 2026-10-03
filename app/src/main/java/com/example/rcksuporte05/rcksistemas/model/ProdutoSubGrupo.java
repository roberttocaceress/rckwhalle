package com.example.rcksuporte05.rcksistemas.model;

public class ProdutoSubGrupo {
    private String id_grupo;
    private String nome_sub_grupo;
    private String ativo;
    private String total_produto;

    public String getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(String id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNome_sub_grupo() {
        return nome_sub_grupo;
    }

    public void setNome_sub_grupo(String nome_sub_grupo) {
        this.nome_sub_grupo = nome_sub_grupo;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getTotal_produto() {
        return total_produto;
    }

    public void setTotal_produto(String total_produto) {
        this.total_produto = total_produto;
    }

    @Override
    public String toString() {
        return nome_sub_grupo;
    }
}
