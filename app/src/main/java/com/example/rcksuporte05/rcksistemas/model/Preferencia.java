package com.example.rcksuporte05.rcksistemas.model;

public class Preferencia {

    private String id;

    private int dias_hist_venda;
    private int dias_hist_financeiro;
    private int dias_hist_pedido;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDias_hist_venda() {
        return dias_hist_venda;
    }

    public void setDias_hist_venda(int dias_hist_venda) {
        this.dias_hist_venda = dias_hist_venda;
    }

    public int getDias_hist_financeiro() {
        return dias_hist_financeiro;
    }

    public void setDias_hist_financeiro(int dias_hist_financeiro) {
        this.dias_hist_financeiro = dias_hist_financeiro;
    }

    public int getDias_hist_pedido() {
        return dias_hist_pedido;
    }

    public void setDias_hist_pedido(int dias_hist_pedido) {
        this.dias_hist_pedido = dias_hist_pedido;
    }
}
