package com.example.rcksuporte05.rcksistemas.model;

public class MesAnoGrupoMarca {
    private String codigo;
    private String nome;
    private MesAno mesAno;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MesAno getMesAno() {
        return mesAno;
    }

    public void setMesAno(MesAno mesAno) {
        this.mesAno = mesAno;
    }
}
