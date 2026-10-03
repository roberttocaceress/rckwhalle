package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

import java.util.List;

/**
 * Created by RCK 03 on 06/10/2017.
 */

public class GraficoFaturamentoHelper {
    private static EvolucaoFaturamento evolucaoFaturamento;

    private static  String tipoRelatorio;
    private static  int idxRelatorio;

    private static List<EvolucaoFaturamento> listaEvolucaoFaturamento;

    private static List<ParticipacaoFaturamento> listaParticipacaoFaturamento;
    private static List<EvolucaoFaturamento> listaEvolucaoFaturamentoMensal;
    private static List<EvolucaoFaturamento> listaEvolucaoFaturamentoMesMes;
    private static List<EvolucaoFaturamento> listaEvolucaoFaturamentoSemanaSemana;

    private static List<EvolucaoFaturamento> listaEvolucaoFaturamentoMesAno;
    private static List<MesAnoMeta> listaMesAnoMeta;
    private static List<MesSemana> listaMesSemana;
    private static List<com.example.rcksuporte05.rcksistemas.model.Financeiro> listaFinanceiro;
    private static int diasReferenciaFinanceiro;

    public static EvolucaoFaturamento getEvolucaoFaturamento() {
        return evolucaoFaturamento;
    }

    public static void setEvolucaoFaturamento(EvolucaoFaturamento evolucaoFaturamento) {
        GraficoFaturamentoHelper.evolucaoFaturamento = evolucaoFaturamento;
    }

    public static List<EvolucaoFaturamento> getListaEvolucaoFaturamento() {
        return listaEvolucaoFaturamento;
    }

    public static void setListaEvolucaoFaturamento(List<EvolucaoFaturamento> listaEvolucaoFaturamento) {
        GraficoFaturamentoHelper.listaEvolucaoFaturamento = listaEvolucaoFaturamento;
    }

    public static String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public static void setTipoRelatorio(String tipoRelatorio) {
        GraficoFaturamentoHelper.tipoRelatorio = tipoRelatorio;
    }

    public static int getIdxRelatorio() {
        return idxRelatorio;
    }

    public static void setIdxRelatorio(int idxRelatorio) {
        GraficoFaturamentoHelper.idxRelatorio = idxRelatorio;
    }

    public static List<ParticipacaoFaturamento> getListaParticipacaoFaturamento() {
        return listaParticipacaoFaturamento;
    }

    public static void setListaParticipacaoFaturamento(List<ParticipacaoFaturamento> listaParticipacaoFaturamento) {
        GraficoFaturamentoHelper.listaParticipacaoFaturamento = listaParticipacaoFaturamento;
    }

    public static List<com.example.rcksuporte05.rcksistemas.model.Financeiro> getListaFinanceiro() {
        return listaFinanceiro;
    }

    public static void setListaFinanceiro(List<com.example.rcksuporte05.rcksistemas.model.Financeiro> listaFinanceiro) {
        GraficoFaturamentoHelper.listaFinanceiro = listaFinanceiro;
    }

    public static int getDiasReferenciaFinanceiro() {
        return diasReferenciaFinanceiro;
    }

    public static void setDiasReferenciaFinanceiro(int diasReferenciaFinanceiro) {
        GraficoFaturamentoHelper.diasReferenciaFinanceiro = diasReferenciaFinanceiro;
    }

    public static List<MesAnoMeta> getListaMesAnoMeta() {
        return listaMesAnoMeta;
    }

    public static void setListaMesAnoMeta(List<MesAnoMeta> listaMesAnoMeta) {
        GraficoFaturamentoHelper.listaMesAnoMeta = listaMesAnoMeta;
    }

    public static List<MesSemana> getListaMesSemana() {
        return listaMesSemana;
    }

    public static void setListaMesSemana(List<MesSemana> listaMesSemana) {
        GraficoFaturamentoHelper.listaMesSemana = listaMesSemana;
    }
}
