package com.example.rcksuporte05.rcksistemas.model;

import androidx.annotation.NonNull;

public class Municipio {

    private String id;
    private String nome_municipio;
    private String uf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome_municipio() {
        return nome_municipio;
    }

    public void setNome_municipio(String nome_municipio) {
        this.nome_municipio = nome_municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @NonNull
    @Override
    public String toString() {
        return nome_municipio.toString();
    }
}
