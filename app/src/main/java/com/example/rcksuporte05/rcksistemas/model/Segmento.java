package com.example.rcksuporte05.rcksistemas.model;

/**
 * Created by RCK 03 on 30/01/2018.
 */

public class Segmento {
    /*
    CREATE TABLE TBL_CADASTRO_SETOR
           ( ID_SETOR INTEGER NOT NULL, ATIVO VARCHAR(1), NOME_SETOR VARCHAR(60));

     */

    private String ativo;
    private String idSetor;
    private String nomeSetor;
    private String descricaoOutros;


    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(String idSetor) {
        this.idSetor = idSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public String getDescricaoOutros() {
        return descricaoOutros;
    }

    public void setDescricaoOutros(String descricaoOutros) {
        this.descricaoOutros = descricaoOutros;
    }

    @Override
    public String toString() {
        return nomeSetor;
    }
}
