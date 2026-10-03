package com.example.rcksuporte05.rcksistemas.model;

public class Periodo {

    private String periodo;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Periodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return periodo;
    }
}
