package com.example.rcksuporte05.rcksistemas.model;

public class CobrancaStatus {
    private String ativo;
    private int id_status;
    private String nome_status;
    private String excluido;

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public String getNome_status() {
        return nome_status;
    }

    public void setNome_status(String nome_status) {
        this.nome_status = nome_status;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    @Override
    public String toString() {
        return nome_status;
    }

   /*
    ATIVO                    CHAR(1),
    ID_STATUS                INTEGER NOT NULL,
    NOME_STATUS              VARCHAR(60),
    USUARIO_LANCAMENTO       VARCHAR(40),
    USUARIO_LANCAMENTO_DATA  TIMESTAMP,
    EXCLUIDO                 CHAR(1)

     */
}
