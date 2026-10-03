package com.example.rcksuporte05.rcksistemas.model;

import android.graphics.Bitmap;

public class PedidoAnexo {
    private int idAnexo;
    private int idAnexoServidor;
    private int idPedido;
    private int idPedidoServidor;
    private String nomeAnexo;
    private String anexo;
    transient private Bitmap miniatura;
    private String excluido;
    private String principal;

    public int getIdAnexo() {
        return idAnexo;
    }

    public void setIdAnexo(int idAnexo) {
        this.idAnexo = idAnexo;
    }

    public int getIdAnexoServidor() {
        return idAnexoServidor;
    }

    public void setIdAnexoServidor(int idAnexoServidor) {
        this.idAnexoServidor = idAnexoServidor;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdPedidoServidor() {
        return idPedidoServidor;
    }

    public void setIdPedidoServidor(int idPedidoServidor) {
        this.idPedidoServidor = idPedidoServidor;
    }

    public String getNomeAnexo() {
        return nomeAnexo;
    }

    public void setNomeAnexo(String nomeAnexo) {
        this.nomeAnexo = nomeAnexo;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public Bitmap getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(Bitmap miniatura) {
        this.miniatura = miniatura;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
