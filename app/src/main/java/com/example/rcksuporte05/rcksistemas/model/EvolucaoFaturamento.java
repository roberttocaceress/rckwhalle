package com.example.rcksuporte05.rcksistemas.model;

import java.io.Serializable;
import java.util.List;

public class EvolucaoFaturamento implements Serializable {
    private String hora;
    private String dia;
    private String diaSemana;
    private String mes;
    private String ano;
    private String total;
    private String total_liquido;
    private String total_bruto;

    private String nome_grupo;
    private String e_entrada_s_saida;

    List<EvolucaoFaturamento> total_por_grupo;

    List<String> lista_grupo;


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal_liquido() {
        return total_liquido;
    }

    public void setTotal_liquido(String total_liquido) {
        this.total_liquido = total_liquido;
    }

    public String getTotal_bruto() {
        return total_bruto;
    }

    public void setTotal_bruto(String total_bruto) {
        this.total_bruto = total_bruto;
    }

    public List<EvolucaoFaturamento> getTotal_por_grupo() {
        return total_por_grupo;
    }

    public void setTotal_por_grupo(List<EvolucaoFaturamento> total_por_grupo) {
        this.total_por_grupo = total_por_grupo;
    }

    public String getNome_grupo() {
        return nome_grupo;
    }

    public void setNome_grupo(String nome_grupo) {
        this.nome_grupo = nome_grupo;
    }

    public List<String> getLista_grupo() {
        return lista_grupo;
    }

    public void setLista_grupo(List<String> lista_grupo) {
        this.lista_grupo = lista_grupo;
    }

    public String getE_entrada_s_saida() {
        return e_entrada_s_saida;
    }

    public void setE_entrada_s_saida(String e_entrada_s_saida) {
        this.e_entrada_s_saida = e_entrada_s_saida;
    }
}
