package com.example.rcksuporte05.rcksistemas.model;

import androidx.annotation.NonNull;

public class Moeda {

    private String id_moeda;
    private String nome_moeda;

    public String getId_moeda() {
        return id_moeda;
    }

    public void setId_moeda(String id_moeda) {
        this.id_moeda = id_moeda;
    }

    public String getNome_moeda() {
        return nome_moeda;
    }

    public void setNome_moeda(String nome_moeda) {
        this.nome_moeda = nome_moeda;
    }

    @NonNull
    @Override
    public String toString() {
        return nome_moeda;
    }
}
