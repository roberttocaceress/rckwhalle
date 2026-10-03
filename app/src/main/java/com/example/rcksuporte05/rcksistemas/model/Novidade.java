package com.example.rcksuporte05.rcksistemas.model;

public class Novidade {

    private String id;
    private String versaoApp;
    private String titulo;
    private String arquivo;


    public Novidade(String id, String versaoApp, String titulo, String arquivo) {
        this.id = id;
        this.versaoApp = versaoApp;
        this.titulo = titulo;
        this.arquivo = arquivo;
    }

    public Novidade() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersaoApp() {
        return versaoApp;
    }

    public void setVersaoApp(String versaoApp) {
        this.versaoApp = versaoApp;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
}
