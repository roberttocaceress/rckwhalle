package com.example.rcksuporte05.rcksistemas.model;

public class Categoria {

    private int idCategoria;
    private int idEmpresa;
    private String ativo;
    private String nomeCategoria;
    private String usuarioId;
    private String mdvAceitaNovo;// MULTI_DEVICE_ACEITA_NOVO(S/N)   permite ou não informar quando se cadastra um novo cliente
    private String nomeMultiDispositivo;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMdvAceitaNovo() {
        return mdvAceitaNovo;
    }

    public void setMdvAceitaNovo(String mdvAceitaNovo) {
        this.mdvAceitaNovo = mdvAceitaNovo;
    }

    public String getNomeMultiDispositivo() {
        return nomeMultiDispositivo;
    }

    public void setNomeMultiDispositivo(String nomeMultiDispositivo) {
        this.nomeMultiDispositivo = nomeMultiDispositivo;
    }

    @Override
    public String toString() {
        return nomeCategoria;
    }


}
