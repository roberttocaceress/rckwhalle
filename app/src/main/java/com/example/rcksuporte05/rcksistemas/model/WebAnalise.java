package com.example.rcksuporte05.rcksistemas.model;

import java.util.List;

public class WebAnalise {
    private Integer idAnalise;
    private String idAnaliseApp;
    private Integer idAnaliseServidor;
    private String idVisitaApp;
    private Integer idVisitaServidor;
    private String idPedidoApp;
    private Integer idPedidoServidor;
    private Integer idVendedor;
    private Integer idClienteServidor;
    private String dataHoraAnalise;
    private String statusCredito;
    private Double limiteCredito;
    private Double limiteDisponivel;
    private Double faturamentoAnual;
    private Double ticketMedio;
    private Integer diasFrequenciaHabitual;
    private Integer diasDesdeUltimaCompra;
    private String statusReposicao;
    private String mensagemAlerta;
    private String versaoApp;
    private String stEnviado;
    private String excluido;
    private String razaoSocial;
    private String nomeFantasia;
    private double valorPedido;
    private List<WebAnaliseItens> itens;

    public Integer getIdAnalise() {
        return idAnalise;
    }

    public void setIdAnalise(Integer idAnalise) {
        this.idAnalise = idAnalise;
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

    public String getIdVisitaApp() {
        return idVisitaApp;
    }

    public void setIdVisitaApp(String idVisitaApp) {
        this.idVisitaApp = idVisitaApp;
    }

    public Integer getIdVisitaServidor() {
        return idVisitaServidor;
    }

    public void setIdVisitaServidor(Integer idVisitaServidor) {
        this.idVisitaServidor = idVisitaServidor;
    }

    public String getIdPedidoApp() {
        return idPedidoApp;
    }

    public void setIdPedidoApp(String idPedidoApp) {
        this.idPedidoApp = idPedidoApp;
    }

    public Integer getIdPedidoServidor() {
        return idPedidoServidor;
    }

    public void setIdPedidoServidor(Integer idPedidoServidor) {
        this.idPedidoServidor = idPedidoServidor;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getIdClienteServidor() {
        return idClienteServidor;
    }

    public void setIdClienteServidor(Integer idClienteServidor) {
        this.idClienteServidor = idClienteServidor;
    }

    public String getDataHoraAnalise() {
        return dataHoraAnalise;
    }

    public void setDataHoraAnalise(String dataHoraAnalise) {
        this.dataHoraAnalise = dataHoraAnalise;
    }

    public String getStatusCredito() {
        return statusCredito;
    }

    public void setStatusCredito(String statusCredito) {
        this.statusCredito = statusCredito;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(Double limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    public Double getFaturamentoAnual() {
        return faturamentoAnual;
    }

    public void setFaturamentoAnual(Double faturamentoAnual) {
        this.faturamentoAnual = faturamentoAnual;
    }

    public Double getTicketMedio() {
        return ticketMedio;
    }

    public void setTicketMedio(Double ticketMedio) {
        this.ticketMedio = ticketMedio;
    }

    public Integer getDiasFrequenciaHabitual() {
        return diasFrequenciaHabitual;
    }

    public void setDiasFrequenciaHabitual(Integer diasFrequenciaHabitual) {
        this.diasFrequenciaHabitual = diasFrequenciaHabitual;
    }

    public Integer getDiasDesdeUltimaCompra() {
        return diasDesdeUltimaCompra;
    }

    public void setDiasDesdeUltimaCompra(Integer diasDesdeUltimaCompra) {
        this.diasDesdeUltimaCompra = diasDesdeUltimaCompra;
    }

    public String getStatusReposicao() {
        return statusReposicao;
    }

    public void setStatusReposicao(String statusReposicao) {
        this.statusReposicao = statusReposicao;
    }

    public String getMensagemAlerta() {
        return mensagemAlerta;
    }

    public void setMensagemAlerta(String mensagemAlerta) {
        this.mensagemAlerta = mensagemAlerta;
    }

    public String getVersaoApp() {
        return versaoApp;
    }

    public void setVersaoApp(String versaoApp) {
        this.versaoApp = versaoApp;
    }

    public String getStEnviado() {
        return stEnviado;
    }

    public void setStEnviado(String stEnviado) {
        this.stEnviado = stEnviado;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    public List<WebAnaliseItens> getItens() {
        return itens;
    }

    public void setItens(List<WebAnaliseItens> itens) {
        this.itens = itens;
    }


    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public double getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(double valorPedido) {
        this.valorPedido = valorPedido;
    }
}