package com.example.rcksuporte05.rcksistemas.model;

import androidx.annotation.NonNull;

public class Deposito {
    private String id_deposito;
    private String id_empresa;
    private String nome_deposito;

    public String getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(String id_deposito) {
        this.id_deposito = id_deposito;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNome_deposito() {
        return nome_deposito;
    }

    public void setNome_deposito(String nome_deposito) {
        this.nome_deposito = nome_deposito;
    }

    @NonNull
    @Override
    public String toString() {
        return nome_deposito;
    }
}
