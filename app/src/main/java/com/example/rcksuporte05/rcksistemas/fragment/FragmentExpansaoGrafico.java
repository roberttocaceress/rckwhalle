package com.example.rcksuporte05.rcksistemas.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.ExpansaoCarteira;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentExpansaoGrafico extends Fragment {

    @BindView(R.id.chartExpansao)
    BarChart barChart;

    @BindView(R.id.tvTituloGraficoExpansao)
    TextView tvTitulo;

    private static List<ExpansaoCarteira> dados;

    public static FragmentExpansaoGrafico newInstance(List<ExpansaoCarteira> lista) {
        dados = lista;
        return new FragmentExpansaoGrafico();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expansao_grafico_2026, container, false);
        ButterKnife.bind(this, view);
        configurarGrafico();
        return view;
    }

    private void configurarGrafico() {
        if (dados == null || dados.isEmpty()) return;

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            // VISÃO VENDEDOR: Barras por Mês
            tvTitulo.setText("Crescimento Mensal");
            ExpansaoCarteira meuDado = dados.get(0);
            for (int i = 0; i < meuDado.getMeses().size(); i++) {
                ExpansaoCarteira.ExpansaoMensal mes = meuDado.getMeses().get(i);
                entries.add(new BarEntry(i, mes.getTotalMes()));
                labels.add(mes.getMesNome());
            }
        } else {
            // VISÃO GESTOR: Barras por Vendedor
            tvTitulo.setText("Ranking de Expansão (Total Equipe)");
            for (int i = 0; i < dados.size(); i++) {
                ExpansaoCarteira vended = dados.get(i);
                entries.add(new BarEntry(i, vended.getTotalGeral()));
                labels.add(vended.getNomeVendedor());
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Novos Clientes");
        dataSet.setColor(Color.parseColor("#31595F")); // Cor RCK
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        
        barChart.animateY(1000);
        barChart.invalidate();
    }
}
