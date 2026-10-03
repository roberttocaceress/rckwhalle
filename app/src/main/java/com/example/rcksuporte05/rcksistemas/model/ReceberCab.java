package com.example.rcksuporte05.rcksistemas.model;

public class   ReceberCab {
    private String id_web_receber;
    private String id_lote;
    private String id_empresa;
    private String id_vendedor;
    private String id_moeda;
    private String status;
    private String excluido;
    private String valor;
    private String usuario_id;
    private String usuario_nome;
    private String usuario_data;
    private String id_cadastro;
    private String data_vencimento;
    private String id_android;

    private String data_reagendamento;
    private String observacoes;

    private String chave_receber_app; //id_vendedor(000) + id_web_receber(00000000) + datahora(ddmmyy-hhmmss) + id_cadastro

    public String getId_web_receber() {
        return id_web_receber;
    }

    public void setId_web_receber(String id_web_receber) {
        this.id_web_receber = id_web_receber;
    }

    public String getId_lote() {
        return id_lote;
    }

    public void setId_lote(String id_lote) {
        this.id_lote = id_lote;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getId_moeda() {
        return id_moeda;
    }

    public void setId_moeda(String id_moeda) {
        this.id_moeda = id_moeda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_data() {
        return usuario_data;
    }

    public void setUsuario_data(String usuario_data) {
        this.usuario_data = usuario_data;
    }

    public String getId_cadastro() {
        return id_cadastro;
    }

    public void setId_cadastro(String id_cadastro) {
        this.id_cadastro = id_cadastro;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(String data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public String getId_android() {
        return id_android;
    }

    public void setId_android(String id_android) {
        this.id_android = id_android;
    }

    public String getData_reagendamento() {
        return data_reagendamento;
    }

    public void setData_reagendamento(String data_reagendamento) {
        this.data_reagendamento = data_reagendamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getChave_receber_app() {
        return chave_receber_app;
    }

    public void setChave_receber_app(String chave_receber_app) {
        this.chave_receber_app = chave_receber_app;
    }
}
