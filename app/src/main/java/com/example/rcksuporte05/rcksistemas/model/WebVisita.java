package com.example.rcksuporte05.rcksistemas.model;

public class WebVisita {
    private int idVisita;
    private String idVisitaApp;
    private int idVendedor;
    private int idPedido;
    private String idPedidoApp;
    private int idPedidoServidor;
    private int idClienteServidor;
    private String dataHoraCheckin;
    private String latitudeCheckin;
    private String longitudeCheckin;
    private String altitudeCheckin;
    private String precisaoCheckin;
    private String dataHoraCheckout;
    private String latitudeCheckout;
    private String longitudeCheckout;
    private byte[] fotoCheckin1;
    private byte[] fotoCheckin2;
    private String statusVisita;
    private String motivoNaoVenda;
    private int idMotivoNaoVenda;
    private String versaoApp;
    private String observacao;
    private String excluido;

    public WebVisita() {
        this.excluido = "N";
        this.statusVisita = "N";
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdVisitaApp() {
        return idVisitaApp;
    }

    public void setIdVisitaApp(String idVisitaApp) {
        this.idVisitaApp = idVisitaApp;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdPedidoApp() {
        return idPedidoApp;
    }

    public void setIdPedidoApp(String idPedidoApp) {
        this.idPedidoApp = idPedidoApp;
    }

    public int getIdPedidoServidor() {
        return idPedidoServidor;
    }

    public void setIdPedidoServidor(int idPedidoServidor) {
        this.idPedidoServidor = idPedidoServidor;
    }

    public int getIdClienteServidor() {
        return idClienteServidor;
    }

    public void setIdClienteServidor(int idClienteServidor) {
        this.idClienteServidor = idClienteServidor;
    }

    public String getDataHoraCheckin() {
        return dataHoraCheckin;
    }

    public void setDataHoraCheckin(String dataHoraCheckin) {
        this.dataHoraCheckin = dataHoraCheckin;
    }

    public String getLatitudeCheckin() {
        return latitudeCheckin;
    }

    public void setLatitudeCheckin(String latitudeCheckin) {
        this.latitudeCheckin = latitudeCheckin;
    }

    public String getLongitudeCheckin() {
        return longitudeCheckin;
    }

    public void setLongitudeCheckin(String longitudeCheckin) {
        this.longitudeCheckin = longitudeCheckin;
    }

    public String getAltitudeCheckin() {
        return altitudeCheckin;
    }

    public void setAltitudeCheckin(String altitudeCheckin) {
        this.altitudeCheckin = altitudeCheckin;
    }

    public String getPrecisaoCheckin() {
        return precisaoCheckin;
    }

    public void setPrecisaoCheckin(String precisaoCheckin) {
        this.precisaoCheckin = precisaoCheckin;
    }

    public String getDataHoraCheckout() {
        return dataHoraCheckout;
    }

    public void setDataHoraCheckout(String dataHoraCheckout) {
        this.dataHoraCheckout = dataHoraCheckout;
    }

    public String getLatitudeCheckout() {
        return latitudeCheckout;
    }

    public void setLatitudeCheckout(String latitudeCheckout) {
        this.latitudeCheckout = latitudeCheckout;
    }

    public String getLongitudeCheckout() {
        return longitudeCheckout;
    }

    public void setLongitudeCheckout(String longitudeCheckout) {
        this.longitudeCheckout = longitudeCheckout;
    }

    public byte[] getFotoCheckin1() {
        return fotoCheckin1;
    }

    public void setFotoCheckin1(byte[] fotoCheckin1) {
        this.fotoCheckin1 = fotoCheckin1;
    }

    public byte[] getFotoCheckin2() {
        return fotoCheckin2;
    }

    public void setFotoCheckin2(byte[] fotoCheckin2) {
        this.fotoCheckin2 = fotoCheckin2;
    }

    public String getStatusVisita() {
        return statusVisita;
    }

    public void setStatusVisita(String statusVisita) {
        this.statusVisita = statusVisita;
    }

    public String getMotivoNaoVenda() {
        return motivoNaoVenda;
    }

    public void setMotivoNaoVenda(String motivoNaoVenda) {
        this.motivoNaoVenda = motivoNaoVenda;
    }

    public int getIdMotivoNaoVenda() {
        return idMotivoNaoVenda;
    }

    public void setIdMotivoNaoVenda(int idMotivoNaoVenda) {
        this.idMotivoNaoVenda = idMotivoNaoVenda;
    }

    public String getVersaoApp() {
        return versaoApp;
    }

    public void setVersaoApp(String versaoApp) {
        this.versaoApp = versaoApp;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }
}
