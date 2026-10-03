package com.example.rcksuporte05.rcksistemas.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Financeiro implements Serializable {
    private String documento;
    private String parcela;
    private String especie;
    private String data_emissao;
    private String data_vencimento;
    private String data_pagamento;
    private String valor_documento;
    private String valor_juros;
    private String valor_pago;
    private String valor_saldo;

    private String nome_pdf;

    private String id_favorecido;
    @SerializedName(value = "nome_favorecido", alternate = {"NOME_FAVORECIDO", "razao_social", "RAZAO_SOCIAL"})
    private String nome_favorecido;
    private String id_vendedor;
    private String nome_vendedor;

    private String dias_atraso;

    //private String cobranca_descricao_status;
    //private String historico;
    //private String pontualidade;
    //private String pontualidade_status;


    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(String data_emissao) {
        this.data_emissao = data_emissao;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(String data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public String getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public String getValor_documento() {
        return valor_documento;
    }

    public void setValor_documento(String valor_documento) {
        this.valor_documento = valor_documento;
    }

    public String getValor_juros() {
        return valor_juros;
    }

    public void setValor_juros(String valor_juros) {
        this.valor_juros = valor_juros;
    }

    public String getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(String valor_pago) {
        this.valor_pago = valor_pago;
    }

    public String getValor_saldo() {
        return valor_saldo;
    }

    public void setValor_saldo(String valor_saldo) {
        this.valor_saldo = valor_saldo;
    }

    public String getNome_pdf() {
        return nome_pdf;
    }

    public void setNome_pdf(String nome_pdf) {
        this.nome_pdf = nome_pdf;
    }

    public String getId_favorecido() {
        return id_favorecido;
    }

    public void setId_favorecido(String id_favorecido) {
        this.id_favorecido = id_favorecido;
    }

    public String getNome_favorecido() {
        return nome_favorecido;
    }

    public void setNome_favorecido(String nome_favorecido) {
        this.nome_favorecido = nome_favorecido;
    }

    public String getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getNome_vendedor() {
        return nome_vendedor;
    }

    public void setNome_vendedor(String nome_vendedor) {
        this.nome_vendedor = nome_vendedor;
    }

    public String getDias_atraso() {
        return dias_atraso;
    }

    public void setDias_atraso(String dias_atraso) {
        this.dias_atraso = dias_atraso;
    }
}
