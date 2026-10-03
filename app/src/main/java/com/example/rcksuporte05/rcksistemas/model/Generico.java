package com.example.rcksuporte05.rcksistemas.model;

import androidx.annotation.NonNull;

public class Generico {

    private String id;
    private String nome;
    private String ativo;
    private String id_generico;
    private int ordem;
    private int id_regiao;
    private boolean isChecked;

    private String valor;
    private String datae;
    private String datap;

    private String valor_desconto;
    private String perc_desconto;

    private String valor_liquido;

    private String qtde_produto;


    private String mensagem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getId_generico() {
        return id_generico;
    }

    public void setId_generico(String id_generico) {
        this.id_generico = id_generico;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    public int getId_regiao() {
        return id_regiao;
    }

    public void setId_regiao(int id_regiao) {
        this.id_regiao = id_regiao;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDatae() {
        return datae;
    }

    public void setDatae(String datae) {
        this.datae = datae;
    }

    public String getDatap() {
        return datap;
    }

    public void setDatap(String datap) {
        this.datap = datap;
    }

    public String getValor_desconto() {
        return valor_desconto;
    }

    public void setValor_desconto(String valor_desconto) {
        this.valor_desconto = valor_desconto;
    }

    public String getPerc_desconto() {
        return perc_desconto;
    }

    public void setPerc_desconto(String perc_desconto) {
        this.perc_desconto = perc_desconto;
    }

    public String getValor_liquido() {
        return valor_liquido;
    }

    public void setValor_liquido(String valor_liquido) {
        this.valor_liquido = valor_liquido;
    }

    public String getQtde_produto() {
        return qtde_produto;
    }

    public void setQtde_produto(String qtde_produto) {
        this.qtde_produto = qtde_produto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
