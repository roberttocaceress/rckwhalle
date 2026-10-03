package com.example.rcksuporte05.rcksistemas.model;

public class Alerta {
    private String id;
    private String alerta_emissao_doc;
    private String alerta_vencimento_doc;
    private String alerta_novo_app;

    public Alerta() {
    }
    public Alerta(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAlerta_emissao_doc() {
        return alerta_emissao_doc;
    }
    public void setAlerta_emissao_doc(String alerta_emissao_doc) {
        this.alerta_emissao_doc = alerta_emissao_doc;
    }

    public String getAlerta_vencimento_doc() {
        return alerta_vencimento_doc;
    }

    public void setAlerta_vencimento_doc(String alerta_vencimento_doc) {
        this.alerta_vencimento_doc = alerta_vencimento_doc;
    }

    public String getAlerta_novo_app() {
        return alerta_novo_app;
    }
    public void setAlerta_novo_app(String alerta_novo_app) {
        this.alerta_novo_app = alerta_novo_app;
    }


}
