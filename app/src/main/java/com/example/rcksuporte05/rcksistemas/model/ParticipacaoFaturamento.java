package com.example.rcksuporte05.rcksistemas.model;

import java.io.Serializable;

public class ParticipacaoFaturamento implements Serializable {

    private String id;
    private String nome;
    private String valor_bruto;
    private String valor_liquido;

    private String qtde_vendas; //Usado no relatorio BI
    private String ticket_med_bruto;
    private String ticket_med_liquido;

    private String valor_bruto_dev;  //Usado no relatorio BI devolucao
    private String valor_liquido_dev;//Usado no relatorio BI devolucao

    private String qtde_vendas_dev; //Usado no relatorio BI

    private String e_entrada_s_saida;
    private String figura_rel_comercial;

    private String nome_mes; // NOVO: Para suporte a agrupamento mensal
    private String ano;      // NOVO: Para suporte a agrupamento mensal

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

    public String getValor_bruto() {
        return valor_bruto;
    }

    public void setValor_bruto(String valor_bruto) {
        this.valor_bruto = valor_bruto;
    }

    public String getValor_liquido() {
        return valor_liquido;
    }

    public void setValor_liquido(String valor_liquido) {
        this.valor_liquido = valor_liquido;
    }

    public String getQtde_vendas() {
        return qtde_vendas;
    }

    public void setQtde_vendas(String qtde_vendas) {
        this.qtde_vendas = qtde_vendas;
    }

    public String getTicket_med_bruto() {
        return ticket_med_bruto;
    }

    public void setTicket_med_bruto(String ticket_med_bruto) {
        this.ticket_med_bruto = ticket_med_bruto;
    }

    public String getTicket_med_liquido() {
        return ticket_med_liquido;
    }

    public void setTicket_med_liquido(String ticket_med_liquido) {
        this.ticket_med_liquido = ticket_med_liquido;
    }

    public String getValor_bruto_dev() {
        return valor_bruto_dev;
    }

    public void setValor_bruto_dev(String valor_bruto_dev) {
        this.valor_bruto_dev = valor_bruto_dev;
    }

    public String getValor_liquido_dev() {
        return valor_liquido_dev;
    }

    public void setValor_liquido_dev(String valor_liquido_dev) {
        this.valor_liquido_dev = valor_liquido_dev;
    }

    public String getQtde_vendas_dev() {
        return qtde_vendas_dev;
    }

    public void setQtde_vendas_dev(String qtde_vendas_dev) {
        this.qtde_vendas_dev = qtde_vendas_dev;
    }

    public String getE_entrada_s_saida() {
        return e_entrada_s_saida;
    }

    public void setE_entrada_s_saida(String e_entrada_s_saida) {
        this.e_entrada_s_saida = e_entrada_s_saida;
    }

    public String getNome_mes() {
        return nome_mes;
    }

    public void setNome_mes(String nome_mes) {
        this.nome_mes = nome_mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getFigura_rel_comercial() {
        return figura_rel_comercial;
    }

    public void setFigura_rel_comercial(String figura_rel_comercial) {
        this.figura_rel_comercial = figura_rel_comercial;
    }
}
