package com.example.rcksuporte05.rcksistemas.model;

public class LogSistema {
    private int idLog;
    private String idPedido;
    private String idPedidoApp;
    private String idWebPedidoServidor;
    private String idVendedor;
    private String dataHora;
    private String tipoLog;
    private String evento;
    private String classe;
    private String metodo;
    private String idProduto;
    private String valorAnterior;
    private String valorNovo;
    private String erroDetalhe;
    private String stEnviado;

    public LogSistema() {
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdPedidoApp() {
        return idPedidoApp;
    }

    public void setIdPedidoApp(String idPedidoApp) {
        this.idPedidoApp = idPedidoApp;
    }

    public String getIdWebPedidoServidor() {
        return idWebPedidoServidor;
    }

    public void setIdWebPedidoServidor(String idWebPedidoServidor) {
        this.idWebPedidoServidor = idWebPedidoServidor;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipoLog() {
        return tipoLog;
    }

    public void setTipoLog(String tipoLog) {
        this.tipoLog = tipoLog;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNovo() {
        return valorNovo;
    }

    public void setValorNovo(String valorNovo) {
        this.valorNovo = valorNovo;
    }

    public String getErroDetalhe() {
        return erroDetalhe;
    }

    public void setErroDetalhe(String erroDetalhe) {
        this.erroDetalhe = erroDetalhe;
    }

    public String getStEnviado() {
        return stEnviado;
    }

    public void setStEnviado(String stEnviado) {
        this.stEnviado = stEnviado;
    }
}
