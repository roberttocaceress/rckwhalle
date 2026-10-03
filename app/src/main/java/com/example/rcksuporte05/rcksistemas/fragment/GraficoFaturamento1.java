package com.example.rcksuporte05.rcksistemas.fragment;

import static android.view.View.INVISIBLE;
import static com.example.rcksuporte05.rcksistemas.api.Api.context;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityGraficoFaturamento;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.util.ColorTemplateApp;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraficoFaturamento1 extends Fragment {
    private View view;
    @BindView(R.id.barChart)
    BarChart barChart;

    @BindView(R.id.lyt1)
    LinearLayout lyt1;

    @BindView(R.id.lyt2)
    LinearLayout lyt2;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.progressTitle)
    TextView progressTitle;

    private DBHelper db;
    PackageInfo pInfo = null;
    int idx;

    Configuracao configuracao;

    public GraficoFaturamento1(int idx) {
        this.idx = idx;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate o layout para este fragmento
        view = inflater.inflate(R.layout.grafico_faturamento_fragment, container, false);
        ButterKnife.bind(this, view);
        db = new DBHelper(getActivity());
        barChart.setNoDataText("Aguarde...");
        barChart.setNoDataTextColor(Color.BLACK);
        flipCard(context,  lyt2,  lyt1, progressBar ,idx );
        getEvolucaoVendas(idx);
        return view;

        //return inflater.inflate(R.layout.grafico_faturamento_fragment, container, false);
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    private void getEvolucaoVendas( int idxItemMenu) {

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<EvolucaoFaturamento>> call = null;
        if ( idxItemMenu == 0)
            call = apiRotas.getTotalVendaMes(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
        if ( idxItemMenu == 1)
            call = apiRotas.getTotalVendaMesMes(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
        if ( idxItemMenu == 2)
            call = apiRotas.getTotalVendaSemanaSemana(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
        if ( idxItemMenu == 3)
            call = apiRotas.getTotalVendaMesMesGrupo(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
        if (call != null)
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<EvolucaoFaturamento>> call, Response<List<EvolucaoFaturamento>> response) {
                    if (response.code() == 200) {
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(INVISIBLE);
                        List<EvolucaoFaturamento> lista = response.body();
                        //setReciclerView(lista);
                        if ( idxItemMenu == 0) {
                            //setReciclerView(lista);
                            createbarChart1(lista);
                        }
                        if ( idxItemMenu == 1)
                            createbarChart2(lista);
                        if ( idxItemMenu == 2)
                            createbarChart3(lista);
                        if ( idxItemMenu == 3)
                            createbarChart4(lista);
                    } else {
                        //Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.\n" + response.code(), Toast.LENGTH_SHORT).show();
                        if ( idxItemMenu == 0) {
                            progressBar.setIndeterminate(false);
                            progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                            barChart.setNoDataText("Erro..");
                        }
                        if ( idxItemMenu == 1) {
                            progressBar.setIndeterminate(false);
                            progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                            barChart.setNoDataText("Erro..");
                        }
                        if ( idxItemMenu == 2) {
                            progressBar.setIndeterminate(false);
                            progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                            barChart.setNoDataText("Erro..");
                        }
                        if ( idxItemMenu == 3) {
                            progressBar.setIndeterminate(false);
                            progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                            barChart.setNoDataText("Erro..");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<EvolucaoFaturamento>> call, Throwable t) {
                    if ( idxItemMenu == 0) {
                        progressBar.setIndeterminate(false);
                        progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                        barChart.setNoDataText("Erro..");
                    }
                    if ( idxItemMenu == 1) {
                        progressBar.setIndeterminate(false);
                        progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                        barChart.setNoDataText("Erro..");
                    }
                    if ( idxItemMenu == 2) {
                        progressBar.setIndeterminate(false);
                        progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                        barChart.setNoDataText("Erro..");
                    }
                    if ( idxItemMenu == 3) {
                        progressBar.setIndeterminate(false);
                        progressTitle.setText("Não foi possivel sincronizar com o servidor!");
                        barChart.setNoDataText("Erro..");
                    }
                }
            });
        else {
            //hideShimmer();
            Toast.makeText(context, "Falha ao obter os parametros", Toast.LENGTH_LONG).show();
        }

    }

    private void createbarChart1(List<EvolucaoFaturamento> lista) {
        flipCard(context,  lyt1,  lyt1, progressBar, 0 );
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        barChart.setNoDataText("");
        //barChart.setDrawingCacheBackgroundColor(Color.parseColor("#1031595F"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);

        //leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1.5f);
        //leftAxis.set

        //xAxis.setSpaceTop(0f);
        //xAxis.setAxisMaximum(0f);


        String[] labels = new String[lista.size() + 2];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size(); i++) {
            z--;
            labels[i] = setMes(lista.get(z).getMes());

            barOne.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z).getTotal_liquido())));
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch ( ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");
        ArrayList<Integer> colors = new ArrayList<>();
        //colors.add(Color.parseColor("#EABF96"));
        colors.add(Color.parseColor("#31595F"));
        set1.setColors(colors);
        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        //set1.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(8f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        float barWidth = 0.55f; // x4 DataSet
        //barChart.getLegend().setTextSize(6f);
        data.setBarWidth(barWidth);
        barChart.getLegend().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "M"));
            }
        });
        //xAxis.setDrawLabels(false);
        leftAxis.setDrawLabels(false);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChart2(List<EvolucaoFaturamento> lista) {
        flipCard(context,  lyt1,  lyt2, null, 0 );
        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        barChart.setNoDataText("");
        //barChart.setDrawingCacheBackgroundColor(Color.parseColor("#1031595F"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);

        //leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1.5f);

        //xAxis.setSpaceTop(0f);
        //xAxis.setAxisMaximum(0f);


        String[] labels = new String[ ( lista.size()  ) ];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size() / 2; i++) {
            z--;
            labels[i] = setMesAnoAno(  lista.get(z ).getMes() , lista.get(z -1).getMes());
            barOne.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z).getTotal_liquido())));
            barTwo.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z-1).getTotal_liquido())));
            z--;
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch ( ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");
        BarDataSet set2 = null;
        //if ( tipoRelatorio.equalsIgnoreCase("M")) {
        set2 = new BarDataSet(barTwo, "");
        //}


        ArrayList<Integer> colors1 = new ArrayList<>();
        colors1.add(Color.parseColor("#EABF96"));
        // colors1.add(Color.parseColor("#31595F"));
        ArrayList<Integer> colors2 = new ArrayList<>();
        colors2.add(Color.parseColor("#31595F"));
        //colors2.add(Color.parseColor("#EABF96"));

        set1.setColors(colors1);
        set1.setStackLabels(labels);

        set1.setHighlightEnabled(false);
        set1.setVisible(true);

        //if ( tipoRelatorio.equalsIgnoreCase("M")) {
        set2.setColors(colors2);
        //set2.setColor(Color.RED);
        set2.setHighlightEnabled(false);
        set2.setVisible(true);
        //dataSets.add(set2);
        //}
       /* float groupSpace = 0.2f; // Space between groups
        float barSpace = 0.01f; // Space between individual bars within a group
        float barWidth = 0.46f;
        barWidth = (1 - groupSpace) / 2 - (barSpace );*/


        float groupSpace = 0.10f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.39f; // x4 DataSet




        BarData data;
        //if ( tipoRelatorio.equalsIgnoreCase("M")) {
        data =new BarData( set1, set2);
        //} else {
        // data = new BarData(set1);
        //}

        data.setBarWidth(barWidth);
        data.setValueTextSize(8f);


        barChart.getLegend().setEnabled(false);
        barChart.getLegend().setEnabled(false);



        // restrict the x-axis range
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(6);

        //barChart.setDragEnabled(true);
        //barChart.groupBars(0, groupSpace, barSpace);
        //barChart.setVisibleXRangeMinimum(7f);
        barChart.setScaleEnabled(true);
        barChart.setDragEnabled(true);

        data.setValueTextSize(8f);
        data.getDataSetByIndex(0);

        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "MM"));
            }
        });
        //xAxis.setAxisMaximum(data.getGroupWidth(groupSpace, barSpace) * lista.size() );
        barChart.getLegend().setEnabled(false);
        barChart.setDragEnabled(true);
        leftAxis.setDrawLabels(false);
        barChart.setData(data);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChart3(List<EvolucaoFaturamento> lista) {
        flipCard(context,  lyt1,  lyt2, null, 0 );//Stacked
        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        barChart.setDrawingCacheBackgroundColor(Color.parseColor("#F8F8FF"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);

        //leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1.5f);
        //leftAxis.set

        //xAxis.setSpaceTop(0f);
        //xAxis.setAxisMaximum(0f);


        String[] labels = new String[(lista.size())];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        //ArrayList<BarEntry> barTwo = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size(); i++) {
            z--;
            labels[i] = setMes(lista.get(z).getMes());
            float[] lista1 = new float[lista.get(z).getTotal_por_grupo() == null ? 0 : lista.get(z).getTotal_por_grupo().size()];
            if (lista.get(z).getTotal_por_grupo() != null) {
                for (int x = 0; x < lista.get(z).getTotal_por_grupo().size(); x++) {
                    lista1[x] = Float.parseFloat(lista.get(z).getTotal_por_grupo().get(x).getTotal_liquido());
                }
            }
            barOne.add(new BarEntry(i, lista1));
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");

        set1.setHighlightEnabled(false);
        set1.setVisible(true);

        set1.setDrawIcons(false);
        //set1.setColors(getColors(4));


        int qtdeSemanas = 0;
        int idxSemanas = 0;
        for (int i = 0; i < lista.size(); i++) {
            try {
                if (qtdeSemanas < lista.get(i).getTotal_por_grupo().size()) {
                    qtdeSemanas = lista.get(i).getTotal_por_grupo().size();
                    idxSemanas = i;
                }
            } catch (NullPointerException e) {

            }
        }
        String[] stackLabes = new String[lista.get(idxSemanas).getTotal_por_grupo().size()];
        for (int i = 0; i < lista.get(idxSemanas).getTotal_por_grupo().size(); i++) {
            stackLabes[i] = lista.get(idxSemanas).getTotal_por_grupo().get(i).getMes() + "º SEM.";
        }
        set1.setStackLabels(stackLabes);
        if (stackLabes.length == 1)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_1);
        if (stackLabes.length == 2)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_2);
        if (stackLabes.length == 3)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_3);
        if (stackLabes.length == 4)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_4);
        if (stackLabes.length == 5)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_5);
        if (stackLabes.length == 6)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_6);
        if (stackLabes.length == 7)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_7);

        //Toast.makeText(context, qtdeSemanas, Toast.LENGTH_LONG).show();

        //set1.setDrawValues(false);
        //ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        //dataSets.add(set1);
/*
        if ( tipoRelatorio.equalsIgnoreCase("M")) {
            set2.setColors(colors2);
            //set2.setColor(Color.RED);
            set2.setHighlightEnabled(false);
            set2.setVisible(true);
        }
*/
       /* float groupSpace = 0.2f; // Space between groups
        float barSpace = 0.01f; // Space between individual bars within a group
        float barWidth = 0.46f;
        barWidth = (1 - groupSpace) / 2 - (barSpace );*/


        float groupSpace = 0.10f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.80f; // x4 DataSet


        BarData data;
        //if ( tipoRelatorio.equalsIgnoreCase("M")) {
        //    data =new BarData( set1, set2);
        //} else {
        data = new BarData(set1);
        //}

        data.setBarWidth(barWidth);
        data.setValueTextSize(8f);


        //data.setBarWidth(barWidth);

        // restrict the x-axis range
        //barChart.getXAxis().setAxisMinimum(0);
        //barChart.getXAxis().setAxisMaximum(6);

        //barChart.setDragEnabled(true);
        //barChart.groupBars(0, groupSpace, barSpace);
        //barChart.setVisibleXRangeMinimum(5f);
        //barChart.setScaleEnabled(true);
        //barChart.setDragEnabled(true);
        //barChart.getLegend().setEnabled(false);
        barChart.getLegend().setTextSize(6f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(value);
            }
        });

        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "S"));
            }
        });

        leftAxis.setDrawLabels(false);
        barChart.setData(data);

        barChart.setFitBars(true);

        barChart.getLegend().setWordWrapEnabled(true);
        //barChart.getLegend().setEnabled(false);
        //barChart.getLegend().setEnabled(false);//Faz a quebra das linhas na legenda
        barChart.setExtraBottomOffset(8f);

        //Mostra a legenda a diretita
        barChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        barChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        barChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        barChart.getLegend().setDrawInside(false);
        barChart.getLegend().setTextSize(7f);

        //barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChart4(List<EvolucaoFaturamento> lista) {
        flipCard(context,  lyt1,  lyt2, null , 0);
        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        //barChart.setDrawingCacheBackgroundColor(Color.parseColor("#1031595F"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);

        //leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1.5f);
        //leftAxis.set

        //xAxis.setSpaceTop(0f);
        //xAxis.setAxisMaximum(0f);


        String[] labels = new String[ ( lista.size()  ) ];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        //ArrayList<BarEntry> barTwo = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size() ; i++) {
            z--;
            labels[i] = setMes(  lista.get(z ).getMes() );
            float[] lista1 = new float[lista.get(z).getTotal_por_grupo() == null ? 0 : lista.get(z).getTotal_por_grupo().size()];
            if ( lista.get(z).getTotal_por_grupo() != null) {
                for (int x = 0; x < lista.get(z).getTotal_por_grupo().size(); x++) {
                    lista1[x] = Float.parseFloat(lista.get(z).getTotal_por_grupo().get(x).getTotal_liquido());
                }
            }
            barOne.add(new BarEntry(i, lista1));
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch ( ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");

        set1.setHighlightEnabled(false);
        set1.setVisible(true);

        set1.setDrawIcons(false);
        //set1.setColors(getColors(4));


        String[] stackLabes = new String[lista.get(0).getLista_grupo().size()];
        for ( int i = 0; i < lista.get(0).getLista_grupo().size(); i++) {
            stackLabes[i] = lista.get(0).getLista_grupo().get(i);
        }
        set1.setStackLabels(stackLabes);

        if (stackLabes.length == 1)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_1);
        if (stackLabes.length == 2)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_2);
        if (stackLabes.length == 3)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_3);
        if (stackLabes.length == 4)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_4);
        if (stackLabes.length == 5)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_5);
        if (stackLabes.length == 6)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_6);
        if (stackLabes.length == 7)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_7);
        if (stackLabes.length == 8)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_8);
        //set1.setColors(gerarCoresPastel(stackLabes.length));

        float groupSpace = 0.10f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.65f; // x4 DataSet



        BarData data;
        //if ( tipoRelatorio.equalsIgnoreCase("M")) {
        //    data =new BarData( set1, set2);
        //} else {
        data = new BarData(set1);
        //}

        data.setBarWidth(barWidth);


        //data.setBarWidth(barWidth);

        // restrict the x-axis range
        //barChart.getXAxis().setAxisMinimum(0);
        //barChart.getXAxis().setAxisMaximum(6);

        //barChart.setDragEnabled(true);
        //barChart.groupBars(0, groupSpace, barSpace);
        //barChart.setVisibleXRangeMinimum(5f);
        //barChart.setScaleEnabled(true);
        //barChart.setDragEnabled(true);
        //barChart.getLegend().setEnabled(false);
        data.setValueTextSize(8f);
        data.getDataSetByIndex(0);

        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });

        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "MG"));
            }
        });
        leftAxis.setDrawLabels(false);
        barChart.setData(data);

        barChart.setFitBars(true);

        barChart.getLegend().setWordWrapEnabled(true);  //Faz a quebra das linhas na legenda
        barChart.setExtraBottomOffset(8f);



        //barChart.getLegend().setEnabled(false);
        //mostra a legenda a direita
        barChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        barChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        barChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        barChart.getLegend().setDrawInside(false);
        barChart.getLegend().setTextSize(7f);

        barChart.setDragEnabled(true);

        //barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    public class CustomValueFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            // Hide values that are less than a certain threshold (e.g., 5.0)
            return MascaraUtil.mascaraRealSemSimbolo(value);
        }
    }

    public String setMes(String mes) {

        if (mes.substring(5).equalsIgnoreCase("1") || mes.substring(5).equalsIgnoreCase("01"))
            return "JAN " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("2") || mes.substring(5).equalsIgnoreCase("02"))
            return "FEV " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("3") || mes.substring(5).equalsIgnoreCase("03"))
            return "MAR " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("4") || mes.substring(5).equalsIgnoreCase("04"))
            return "ABR " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("5") || mes.substring(5).equalsIgnoreCase("05"))
            return "MAI " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("6") || mes.substring(5).equalsIgnoreCase("06"))
            return "JUN " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("7") || mes.substring(5).equalsIgnoreCase("07"))
            return "JUL " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("8") || mes.substring(5).equalsIgnoreCase("08"))
            return "AGO " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("9") || mes.substring(5).equalsIgnoreCase("09"))
            return "SET " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("10"))
            return "OUT " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("11"))
            return "NOV " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("12"))
            return "DEZ " + mes.substring(2,4) ;
        else
            return "";
    }
    public String setMesAnoAno(String mes1, String mes2) {

        if (mes1.substring(5).equalsIgnoreCase("1") || mes1.substring(5).equalsIgnoreCase("01"))
            return "JAN " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("2") || mes1.substring(5).equalsIgnoreCase("02"))
            return "FEV " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("3") || mes1.substring(5).equalsIgnoreCase("03"))
            return "MAR " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("4") || mes1.substring(5).equalsIgnoreCase("04"))
            return "ABR " + mes1.substring(2,4)  + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("5") || mes1.substring(5).equalsIgnoreCase("05"))
            return "MAI " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("6") || mes1.substring(5).equalsIgnoreCase("06"))
            return "JUN " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("7") || mes1.substring(5).equalsIgnoreCase("07"))
            return "JUL " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("8") || mes1.substring(5).equalsIgnoreCase("08"))
            return "AGO " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("9") || mes1.substring(5).equalsIgnoreCase("09"))
            return "SET " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("10"))
            return "OUT " + mes1.substring(2,4)  + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("11"))
            return "NOV " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("12"))
            return "DEZ " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        else
            return "";
    }

    @SuppressLint("ResourceType")
    private void  flipCard(Context context, View visibleView, View inVisibleView, ProgressBar progressBar, int idColor) {
        if ( progressBar != null) {
            if ( idColor == 0 || idColor == 2)
                progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            if ( idColor == 1 || idColor == 3)
                progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(context, R.color.colorButton), PorterDuff.Mode.SRC_IN);
        }
        try {
            inVisibleView.setVisibility(View.INVISIBLE);
            @SuppressLint("ResourceType") AnimatorSet flipOutAnimatorSet =  (AnimatorSet)  AnimatorInflater.loadAnimator(
                    context,
                    R.anim.flip_out
            );
            @SuppressLint("ResourceType") AnimatorSet flipInAnimationSet;

            flipOutAnimatorSet.setTarget(inVisibleView);
            //if ( isMaximized) {
            flipInAnimationSet = (AnimatorSet) AnimatorInflater.loadAnimator(
                    context,
                    R.anim.flip_in_right
            );
            flipInAnimationSet.setTarget(visibleView);
            flipOutAnimatorSet.start();
            flipInAnimationSet.start();
            visibleView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
