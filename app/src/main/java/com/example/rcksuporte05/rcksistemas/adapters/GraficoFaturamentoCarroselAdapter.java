package com.example.rcksuporte05.rcksistemas.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rcksuporte05.rcksistemas.Helper.GraficoFaturamentoHelper;
import com.example.rcksuporte05.rcksistemas.fragment.GraficoFaturamentoCarrosel;
import com.example.rcksuporte05.rcksistemas.fragment.ListaFaturamentoCarrosel;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;

import java.util.List;

public class GraficoFaturamentoCarroselAdapter extends FragmentStateAdapter {
    List<EvolucaoFaturamento> listaEvolucaoFaturamento;
    List<MesAnoMeta> listaMesAnoMeta;
    List<MesSemana> listaMesSemana;
    List<ParticipacaoFaturamento> listaParticipacaoFaturamento;
    List<Financeiro> listaFinanceiro;
    private String tipoRelatorio;

    public GraficoFaturamentoCarroselAdapter(@NonNull FragmentActivity fragmentActivity,
                                             List<EvolucaoFaturamento> listaEvolucaoFaturamento,
                                             List<MesAnoMeta> listaMesAnoMeta,
                                             List<MesSemana> listaMesSemana,
                                             List<ParticipacaoFaturamento> listaParticipacaoFaturamento,
                                             List<Financeiro> listaFinanceiro,
                                             String tipoRelatorio) {
        super(fragmentActivity);
        this.listaEvolucaoFaturamento = listaEvolucaoFaturamento;
        this.listaMesAnoMeta = listaMesAnoMeta;
        this.listaMesSemana = listaMesSemana;
        this.listaParticipacaoFaturamento = listaParticipacaoFaturamento;
        this.listaFinanceiro = listaFinanceiro;
        this.tipoRelatorio = tipoRelatorio;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Sincroniza o Helper com as listas atuais antes de criar o fragmento
        GraficoFaturamentoHelper.setTipoRelatorio(tipoRelatorio);
        GraficoFaturamentoHelper.setIdxRelatorio(position);
        GraficoFaturamentoHelper.setListaEvolucaoFaturamento(listaEvolucaoFaturamento);
        GraficoFaturamentoHelper.setListaMesAnoMeta(listaMesAnoMeta);
        GraficoFaturamentoHelper.setListaMesSemana(listaMesSemana);
        GraficoFaturamentoHelper.setListaParticipacaoFaturamento(listaParticipacaoFaturamento);
        GraficoFaturamentoHelper.setListaFinanceiro(listaFinanceiro);

        switch (position) {
            case 1:
                return ListaFaturamentoCarrosel.newInstance(position, tipoRelatorio);
            default:
                return GraficoFaturamentoCarrosel.newInstance(position, tipoRelatorio);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
