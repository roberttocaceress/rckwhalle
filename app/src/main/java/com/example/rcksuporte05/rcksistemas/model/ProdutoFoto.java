package com.example.rcksuporte05.rcksistemas.model;

public class ProdutoFoto {
    private String images;
    private String ativo;
    private String id_produto;
    private String id_foto;
    private String id_usuario;
    private String usuario_data;
    private String foto_arquivo;  //  BLOB
    private String foto_miniatura; // BLOB
    public String foto_principal;
    private String fbase64;
    private String nome_produto;

    public String getFbase64() {
        return fbase64;
    }

    public void setFbase64(String fbase64) {
        this.fbase64 = fbase64;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getId_produto() {
        return id_produto;
    }

    public void setId_produto(String id_produto) {
        this.id_produto = id_produto;
    }

    public String getId_foto() {
        return id_foto;
    }

    public void setId_foto(String id_foto) {
        this.id_foto = id_foto;
    }

    public String getUsuario_data() {
        return usuario_data;
    }

    public void setUsuario_data(String usuario_data) {
        this.usuario_data = usuario_data;
    }

    public String getFoto_arquivo() {
        return foto_arquivo;
    }

    public void setFoto_arquivo(String foto_arquivo) {
        this.foto_arquivo = foto_arquivo;
    }

    public String getFoto_miniatura() {
        return foto_miniatura;
    }

    public void setFoto_miniatura(String foto_miniatura) {
        this.foto_miniatura = foto_miniatura;
    }

    public String getFoto_principal() {
        return foto_principal;
    }

    public void setFoto_principal(String foto_principal) {
        this.foto_principal = foto_principal;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public ProdutoFoto(String id_foto, String fbase64) {
        this.id_foto = id_foto;
        this.fbase64 = fbase64;
    }

    public ProdutoFoto() {
    }
/*
    private String id_produto;
    public String nome_produto;

    private String anexo;
    private String miniatura;
    public int images;

    public String getId_produto() {
        return id_produto;
    }

    public void setId_produto(String id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public int getImages() {
        return images;
    }
    */
}
