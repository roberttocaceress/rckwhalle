package com.example.rcksuporte05.rcksistemas.model;

public class ItemMenu {
    private int id;
    private int codigo;
    private String nome;
    private int imagem;

    public ItemMenu(int imagem) {
        this.imagem = imagem;
    }


    public ItemMenu(String nome, int imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
