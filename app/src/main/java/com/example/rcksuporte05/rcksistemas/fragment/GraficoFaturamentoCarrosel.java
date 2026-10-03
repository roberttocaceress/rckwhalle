package com.example.rcksuporte05.rcksistemas.fragment;

import static android.view.View.GONE;
import static com.example.rcksuporte05.rcksistemas.api.Api.context;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.GraficoFaturamentoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityGraficoFaturamentoCarrosel;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPainelGraficoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.util.ColorTemplateApp;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.animation.ChartAnimator;
import android.graphics.Paint;
import android.graphics.Canvas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraficoFaturamentoCarrosel extends Fragment implements OnChartValueSelectedListener, OnChartGestureListener {
    private View view;


    BarChart barChart;
    PieChart pieChart;
    HorizontalBarChart horChart;


    private DBHelper db;
    PackageInfo pInfo = null;
    int idx;

    Configuracao configuracao;

    private List<EvolucaoFaturamento> listaEvolucaoFaturamento;
    private List<MesAnoMeta> listaMesAnoMeta;
    private List<MesSemana> listaMesSemana;
    private List<ParticipacaoFaturamento> listaParticipacaoFaturamento;
    private List<Financeiro> listaFinanceiro;
    private String tipoRelatorio;
    private  Legend l;
    protected Typeface tfLight;

    public GraficoFaturamentoCarrosel() {


    }

    public static GraficoFaturamentoCarrosel newInstance(int idx,String tipoRelatorio) {
        GraficoFaturamentoCarrosel fragment = new GraficoFaturamentoCarrosel();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    /*
    public GraficoFaturamentoCarrosel(int idx, List<EvolucaoFaturamento> lista, String tipoRelatorio) {
        this.idx = idx;
        this.lista = lista;
        this.tipoRelatorio = tipoRelatorio;


    }

     */

    /*public static  GraficoFaturamentoCarrosel newInstance(int idx, List<EvolucaoFaturamento> lista, String tipoRelatorio) {
        GraficoFaturamentoCarrosel fragment = new GraficoFaturamentoCarrosel();
        Bundle args = new Bundle();
        args.putAll(args);
        fragment.setArguments(args);
        return fragment;

   }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate o layout para este fragmento
        //lista = getArguments().get("0");
        db = new DBHelper(getActivity());
        
        // Recupera do Helper para evitar TransactionTooLarge
        tipoRelatorio = GraficoFaturamentoHelper.getTipoRelatorio();
        listaEvolucaoFaturamento = GraficoFaturamentoHelper.getListaEvolucaoFaturamento();
        listaMesAnoMeta = GraficoFaturamentoHelper.getListaMesAnoMeta();
        listaMesSemana = GraficoFaturamentoHelper.getListaMesSemana();
        listaParticipacaoFaturamento = GraficoFaturamentoHelper.getListaParticipacaoFaturamento();
        listaFinanceiro = GraficoFaturamentoHelper.getListaFinanceiro();

        // Se o Helper estiver vazio (raro, mas possível em recriação de fragmento), tenta os argumentos
        if (tipoRelatorio == null && getArguments() != null) {
            tipoRelatorio = getArguments().getString("tipoRelatorio");
        }

        db = new DBHelper(getActivity());
        if ( tipoRelatorio.equalsIgnoreCase("M") || tipoRelatorio.equalsIgnoreCase("S") || tipoRelatorio.equalsIgnoreCase("MM") || tipoRelatorio.equalsIgnoreCase("MG")) {
            view = inflater.inflate(R.layout.grafico_faturamento_carrosel_fragment, container, false);
            ButterKnife.bind(this, view); // MUDANÇA: Bind após inflar
            barChart = view.findViewById(R.id.barChart);
            barChart.setNoDataText("Aguarde...");
            barChart.setNoDataTextColor(Color.BLACK);

            if (tipoRelatorio.equalsIgnoreCase("M")) {
                if (listaMesAnoMeta != null) {
                    createbarChartMesAnoMeta(listaMesAnoMeta);
                } else if (listaEvolucaoFaturamento != null) {
                    createbarChart(listaEvolucaoFaturamento);
                }
            }
            if (tipoRelatorio.equalsIgnoreCase("MM")) {
                if (listaEvolucaoFaturamento != null) createbarChartGroup(listaEvolucaoFaturamento);
            }
            if (tipoRelatorio.equalsIgnoreCase("MG")) {
                if (listaEvolucaoFaturamento != null) createbarChartstacked(listaEvolucaoFaturamento);
            }
            if (tipoRelatorio.equalsIgnoreCase("S")) {
                if (listaMesSemana != null) {
                    createbarChartMesSemana(listaMesSemana);
                } else if (listaEvolucaoFaturamento != null) {
                    createbarChartstacked(listaEvolucaoFaturamento);
                }
            }
        } else if (tipoRelatorio.equalsIgnoreCase("FIN_RK")) {
            view = inflater.inflate(R.layout.grafico_participacao_faturamento_carrosel_fragment, container, false);
            ButterKnife.bind(this, view);
            pieChart = view.findViewById(R.id.pieChart);
            pieChart.setVisibility(GONE);
            horChart = view.findViewById(R.id.horChart);
            horChart.setVisibility(View.VISIBLE);
            createHorChart(convertFinanceiroToParticipacao(listaFinanceiro));
        } else if (tipoRelatorio.startsWith("FIN")) {
            view = inflater.inflate(R.layout.grafico_faturamento_carrosel_fragment, container, false);
            ButterKnife.bind(this, view);
            barChart = view.findViewById(R.id.barChart);
            barChart.setNoDataText("Aguarde...");
            barChart.setNoDataTextColor(Color.BLACK);
            createFinanceBarChart(listaFinanceiro, tipoRelatorio);
        } else if(tipoRelatorio.equalsIgnoreCase("C")) {   //Top five de clientes
            view = inflater.inflate(R.layout.grafico_participacao_faturamento_carrosel_fragment, container, false);
            ButterKnife.bind(this, view);
            pieChart = view.findViewById(R.id.pieChart);
            pieChart.setVisibility(GONE);
            horChart = view.findViewById(R.id.horChart);
            horChart.setVisibility(View.VISIBLE);
            createHorChart(listaParticipacaoFaturamento);
        } else {
            view = inflater.inflate(R.layout.grafico_participacao_faturamento_carrosel_fragment, container, false);
            ButterKnife.bind(this, view);
            horChart = view.findViewById(R.id.horChart);
            horChart.setVisibility(GONE);
            pieChart = view.findViewById(R.id.pieChart);
            pieChart.setVisibility(View.VISIBLE);
            createPieChart();
            setData(listaParticipacaoFaturamento);
        }


        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    private void createbarChartMesAnoMeta(List<MesAnoMeta> lista) {
        float textSize = 10;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            textSize = 12;
        }

        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); 
        barChart.setNoDataText("");
        
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceBottom(1.5f);
        barChart.getAxisRight().setEnabled(false);

        String[] labels = new String[lista.size()];
        ArrayList<BarEntry> entries = new ArrayList<>();
        
        for (int i = 0; i < lista.size(); i++) {
            MesAnoMeta item = lista.get(i);
            labels[i] = setMes(item.getNome_mes()); 
            
            float valor = 0;
            try {
                String v = item.getVenda_liquida().replace(".", "").replace(",", ".");
                valor = Float.parseFloat(v);
            } catch (Exception e) { valor = 0; }
            
            entries.add(new BarEntry(i, valor));
        }
        
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try { return labels[(int) value]; } catch (Exception e) { return ""; }
            }
        });

        BarDataSet set1 = new BarDataSet(entries, "");
        List<GradientColor> gradientColors = new ArrayList<>();
        for (int i = 0; i < ColorTemplateApp.GRADIENT_START_6.length; i++) {
            gradientColors.add(new GradientColor(ColorTemplateApp.GRADIENT_START_6[i], ColorTemplateApp.GRADIENT_END_6[i]));
        }
        set1.setGradientColors(gradientColors);
        set1.setHighlightEnabled(false);
        
        BarData data = new BarData(set1);
        data.setValueTextSize(textSize);
        data.setValueFormatter(new CustomValueFormatter());
        data.setBarWidth(0.55f);
        
        barChart.getLegend().setEnabled(false);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChartMesSemana(List<MesSemana> lista) {
        float textSize = 10;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            textSize = 12;
        }
        
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); 
        barChart.setNoDataText("");
        
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceBottom(1.5f);
        barChart.getAxisRight().setEnabled(false);

        Map<String, List<MesSemana>> mapaMeses = new LinkedHashMap<>();
        int maxSemanas = 0;
        for (MesSemana ms : lista) {
            String data = ms.getIni_semama();
            if (data == null || data.length() < 7) continue;
            String chaveMes = data.substring(0, 7); 
            
            if (!mapaMeses.containsKey(chaveMes)) {
                mapaMeses.put(chaveMes, new ArrayList<>());
            }
            mapaMeses.get(chaveMes).add(ms);
            if (mapaMeses.get(chaveMes).size() > maxSemanas) {
                maxSemanas = mapaMeses.get(chaveMes).size();
            }
        }

        List<String> mesesKeys = new ArrayList<>(mapaMeses.keySet());
        String[] labels = new String[mesesKeys.size()];
        ArrayList<BarEntry> entries = new ArrayList<>();
        
        for (int i = 0; i < mesesKeys.size(); i++) {
            String chave = mesesKeys.get(i);
            labels[i] = setMes(chave);
            
            List<MesSemana> semanasDoMes = mapaMeses.get(chave);
            float[] valoresSemanas = new float[maxSemanas];
            for (int j = 0; j < maxSemanas; j++) {
                if (j < semanasDoMes.size()) {
                    try {
                        String v = semanasDoMes.get(j).getVenda_liquida().replace(".", "").replace(",", ".");
                        valoresSemanas[j] = Float.parseFloat(v);
                    } catch (Exception e) { valoresSemanas[j] = 0; }
                } else {
                    valoresSemanas[j] = 0;
                }
            }
            entries.add(new BarEntry(i, valoresSemanas));
        }

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try { return labels[(int) value]; } catch (Exception e) { return ""; }
            }
        });

        BarDataSet dataSet = new BarDataSet(entries, "");
        int[] cores = ColorTemplateApp.SEMANA_COLORS_APP_6; 
        if (maxSemanas <= 1) cores = ColorTemplateApp.SEMANA_COLORS_APP_1;
        else if (maxSemanas == 2) cores = ColorTemplateApp.SEMANA_COLORS_APP_2;
        else if (maxSemanas == 3) cores = ColorTemplateApp.SEMANA_COLORS_APP_3;
        else if (maxSemanas == 4) cores = ColorTemplateApp.SEMANA_COLORS_APP_4;
        else if (maxSemanas == 5) cores = ColorTemplateApp.SEMANA_COLORS_APP_5;
        
        dataSet.setColors(cores);
        dataSet.setHighlightEnabled(false);
        
        String[] stackLabels = new String[maxSemanas];
        for (int i = 0; i < maxSemanas; i++) stackLabels[i] = (i + 1) + "ª SEM.";
        dataSet.setStackLabels(stackLabels);

        BarData data = new BarData(dataSet);
        data.setValueTextSize(textSize);
        data.setValueFormatter(new CustomValueFormatter());
        data.setBarWidth(0.80f);
        
        barChart.setData(data);
        barChart.getLegend().setEnabled(true);
        barChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        barChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        barChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        barChart.getLegend().setDrawInside(false);
        barChart.getLegend().setTextSize(textSize - 2);
        
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChart(List<EvolucaoFaturamento> lista) {
        float textSize = 10;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            textSize = 12;
        }

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
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(textSize);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
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
        //colors.add(Color.parseColor("#31595F"));
        //set1.setColors(colors);
        //set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_6);
        List<GradientColor> gradientColors = new ArrayList<>();

        for (int i = 0; i < ColorTemplateApp.GRADIENT_START_6.length; i++) {
            gradientColors.add(new GradientColor(
                    ColorTemplateApp.GRADIENT_START_6[i],
                    ColorTemplateApp.GRADIENT_END_6[i]
            ));
        }

        set1.setGradientColors(gradientColors);
        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        //set1.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(textSize);
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


        //xAxis.setDrawLabels(false);
        leftAxis.setDrawLabels(true);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChartGroup(List<EvolucaoFaturamento> lista) {
        if (lista == null || lista.isEmpty()) return;
        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        //barChart.setDrawingCacheBackgroundColor(Color.parseColor("#1031595F"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);

        //leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1.5f);
        //leftAxis.set

        //xAxis.setSpaceTop(0f);
        //xAxis.setAxisMaximum(0f);


        if (lista == null || lista.isEmpty()) return;
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
        if ( tipoRelatorio.equalsIgnoreCase("MM")) {
            set2 = new BarDataSet(barTwo, "");
        }


        ArrayList<Integer> colors1 = new ArrayList<>();
        colors1.add(Color.parseColor("#EABF96"));
        // colors1.add(Color.parseColor("#31595F"));
        ArrayList<Integer> colors2 = new ArrayList<>();
        colors2.add(Color.parseColor("#31595F"));
        //colors2.add(Color.parseColor("#EABF96"));

        set1.setColors(colors1);
        set1.setStackLabels(labels);
        //set1.setColor(colors1);

        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        //set1.setDrawValues(false);
        //ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        //dataSets.add(set1);
        if ( tipoRelatorio.equalsIgnoreCase("MM")) {
            set2.setColors(colors2);
            //set2.setColor(Color.RED);
            set2.setHighlightEnabled(false);
            set2.setVisible(true);
            //dataSets.add(set2);
        }
       /* float groupSpace = 0.2f; // Space between groups
        float barSpace = 0.01f; // Space between individual bars within a group
        float barWidth = 0.46f;
        barWidth = (1 - groupSpace) / 2 - (barSpace );*/


        float groupSpace = 0.10f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.39f; // x4 DataSet




        BarData data;
        if ( tipoRelatorio.equalsIgnoreCase("MM")) {
            data =new BarData( set1, set2);
        } else {
            data = new BarData(set1);
        }

        data.setBarWidth(barWidth);


        data.setBarWidth(barWidth);

        // restrict the x-axis range
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(6);

        //barChart.setDragEnabled(true);
        //barChart.groupBars(0, groupSpace, barSpace);
        //barChart.setVisibleXRangeMinimum(7f);
        barChart.setScaleEnabled(true);barChart.setDragEnabled(true);

        data.setValueTextSize(10f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        //xAxis.setAxisMaximum(data.getGroupWidth(groupSpace, barSpace) * lista.size() );
        barChart.getLegend().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.setData(data);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChartstacked(List<EvolucaoFaturamento> lista) {
        float textSize = 10;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            textSize = 12;
        }

        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        barChart.setDrawingCacheBackgroundColor(Color.TRANSPARENT/*Color.parseColor("#1031595F")*/);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(textSize);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);

        //leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1.5f);
        //leftAxis.set

        //xAxis.setSpaceTop(0f);
        //xAxis.setAxisMaximum(0f);


        if (lista == null || lista.isEmpty()) return;
        String[] labels = new String[ ( lista.size()  ) ];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        //ArrayList<BarEntry> barTwo = new ArrayList<>();
        int z = lista.size();
        if ( tipoRelatorio.equalsIgnoreCase("S")) {
             z = lista.size();
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
            for (int i = 0; i < lista.size(); i++) {
                z--;
                labels[i] = setMes(lista.get(z).getMes());
                //float[] lista1 = new float[lista.get(z).getTotal_por_grupo() == null ? 0 : lista.get(z).getTotal_por_grupo().size()];
                float[] lista1 = new float[lista.get(z).getTotal_por_grupo() == null ? 0 : qtdeSemanas];
                if (lista.get(z).getTotal_por_grupo() != null) {
                    if ( qtdeSemanas == lista.get(z).getTotal_por_grupo().size()) {
                        for (int x = 0; x < lista.get(z).getTotal_por_grupo().size(); x++) {
                            lista1[x] = Float.parseFloat(lista.get(z).getTotal_por_grupo().get(x).getTotal_liquido());
                        }
                    } else {
                        int w = 0;
                        int s = 1;
                        int t = 0;
                        for (int x = 1; x <= qtdeSemanas; x++) {
                            try {
                                if ( x == Integer.parseInt(lista.get(z).getTotal_por_grupo().get(t).getMes() )) {
                                    lista1[w] = Float.parseFloat(lista.get(z).getTotal_por_grupo().get(t).getTotal_liquido());
                                    t++;
                                } else {
                                    lista1[w] = 0.00f;//Float.parseFloat(String.valueOf(x));
                                }
                            /*
                            String strMes = lista.get(z).getTotal_por_grupo().get(t).getMes();
                            int  intMes = Integer.parseInt(lista.get(z).getTotal_por_grupo().get(t).getMes());
                            int  xinMes = x;
                            if ( (x+s) == intMes) {
                                lista1[w] = Float.parseFloat(lista.get(z).getTotal_por_grupo().get(t).getTotal_liquido());
                                t++;
                            } else {
                                s++;
                                lista1[w] = 0.00f;//Float.parseFloat(String.valueOf(x));
                            }

                             */
                            } catch ( ArrayIndexOutOfBoundsException e) {
                                s++;
                                lista1[w] = 0.00f;

                            } catch ( IndexOutOfBoundsException e) {
                                s++;
                                lista1[w] = 0.00f;
                            }
                            w++;
                        }
                        //    for (int x = 0; x < lista.get(z).getTotal_por_grupo().size(); x++) {
                        //        lista1[x] = Float.parseFloat(lista.get(z).getTotal_por_grupo().get(x).getTotal_liquido());
                        //    }

                    }
                }
                barOne.add(new BarEntry(i, lista1));
            }
        } else {
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
        if ( tipoRelatorio.equalsIgnoreCase("S")) {

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
                stackLabes[i] = lista.get(idxSemanas).getTotal_por_grupo().get(i).getMes() + "ª SEM.";
            }
            set1.setStackLabels(stackLabes);
            //set1.setColors(gerarCoresPastel(stackLabes.length));
            List<GradientColor> gradientColors = new ArrayList<>();
            if (stackLabes.length == 1) {
                for (int i = 0; i < ColorTemplateApp.GRADIENT_START_1.length; i++) {
                    gradientColors.add(new GradientColor(
                            ColorTemplateApp.GRADIENT_START_1[i],
                            ColorTemplateApp.GRADIENT_END_1[i]
                    ));
                }
                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_1);
            }
            if (stackLabes.length == 2) {
                for (int i = 0; i < ColorTemplateApp.GRADIENT_START_2.length; i++) {
                    gradientColors.add(new GradientColor(
                            ColorTemplateApp.GRADIENT_START_2[i],
                            ColorTemplateApp.GRADIENT_END_2[i]
                    ));
                }

                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_2);
            }
            if (stackLabes.length == 3) {
                for (int i = 0; i < ColorTemplateApp.GRADIENT_START_3.length; i++) {
                    gradientColors.add(new GradientColor(
                            ColorTemplateApp.GRADIENT_START_3[i],
                            ColorTemplateApp.GRADIENT_END_3[i]
                    ));
                }
                set1.setGradientColors(gradientColors);
                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_3);
            }
            if (stackLabes.length == 4) {
                for (int i = 0; i < ColorTemplateApp.GRADIENT_START_4.length; i++) {
                    gradientColors.add(new GradientColor(
                            ColorTemplateApp.GRADIENT_START_4[i],
                            ColorTemplateApp.GRADIENT_END_4[i]
                    ));
                }
                set1.setGradientColors(gradientColors);
                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_4);
            }
            if (stackLabes.length == 5) {
                for (int i = 0; i < ColorTemplateApp.GRADIENT_START_5.length; i++) {
                    gradientColors.add(new GradientColor(
                            ColorTemplateApp.GRADIENT_START_5[i],
                            ColorTemplateApp.GRADIENT_END_5[i]
                    ));
                }
                set1.setGradientColors(gradientColors);
                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_5);
            }
            if (stackLabes.length == 6) {
                for (int i = 0; i < ColorTemplateApp.GRADIENT_START_6.length; i++) {
                    gradientColors.add(new GradientColor(
                            ColorTemplateApp.GRADIENT_START_6[i],
                            ColorTemplateApp.GRADIENT_END_6[i]
                    ));
                }
                set1.setGradientColors(gradientColors);
                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_6);
            }
            if (stackLabes.length == 7)
                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_7);
        }
        if ( tipoRelatorio.equalsIgnoreCase("MG")) {
            String[] stackLabes = new String[lista.get(0).getLista_grupo().size()];
            for ( int i = 0; i < lista.get(0).getLista_grupo().size(); i++) {
                stackLabes[i] = lista.get(0).getLista_grupo().get(i);
            }
            set1.setStackLabels(stackLabes);

            //set1.setColors(gerarCoresPastel(stackLabes.length));
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
        }

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
        data.setValueTextSize(textSize);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });


        barChart.setData(data);

        barChart.setFitBars(true);

        barChart.getLegend().setWordWrapEnabled(true);  //Faz a quebra das linhas na legenda
        barChart.setExtraBottomOffset(8f);
        //barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createPieChart() {
        pieChart.setNoDataText("Aguarde...");
        pieChart.setNoDataTextColor(Color.BLACK);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 30, 5, 10);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterTextTypeface(tfLight);
        //chart.setCenterText(generateCenterSpannableText());
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        //pieChart.setOnChartValueSelectedListener(context);
        pieChart.setExtraOffsets(30, 10, 30, 0);
        pieChart.setDrawEntryLabels(false); // To remove labels from piece of pie
        pieChart.getDescription().setEnabled(false); // To remove description of pie
        pieChart.animateXY(1500, 1500);
        pieChart.setUsePercentValues(false);
        pieChart.getLegend().setEnabled(true);
        l = pieChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setYOffset(10f);
        l.setYEntrySpace(10f);
        //l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
    }

    private List<ParticipacaoFaturamento> convertFinanceiroToParticipacao(List<Financeiro> listaFin) {
        Map<String, ParticipacaoFaturamento> mapa = new LinkedHashMap<>();
        for (Financeiro f : (listaFin != null ? listaFin : new ArrayList<Financeiro>())) {
            String idCli = f.getId_favorecido() != null ? f.getId_favorecido().trim() : "0";
            double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

            if (!mapa.containsKey(idCli)) {
                ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
                pf.setId(idCli);
                pf.setNome(f.getNome_favorecido());
                pf.setValor_liquido(String.valueOf(valor));
                mapa.put(idCli, pf);
            } else {
                ParticipacaoFaturamento pf = mapa.get(idCli);
                double total = Double.parseDouble(pf.getValor_liquido()) + valor;
                pf.setValor_liquido(String.valueOf(total));
            }
        }
        List<ParticipacaoFaturamento> result = new ArrayList<>(mapa.values());
        Collections.sort(result, (o1, o2) -> Double.compare(Double.parseDouble(o2.getValor_liquido()), Double.parseDouble(o1.getValor_liquido())));
        return result;
    }

    private void createFinanceBarChart(List<Financeiro> lista, String tipo) {
        float textSize = 10;
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false);
        barChart.setNoDataText("Nenhum dado financeiro");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisLineColor(Color.WHITE);
        barChart.getAxisRight().setEnabled(false);

        Map<String, Double> somaFaixas = new LinkedHashMap<>();
        int diasRef = GraficoFaturamentoHelper.getDiasReferenciaFinanceiro();

        if (tipo.equals("FIN_AV")) {
            somaFaixas.put("Hoje", 0.0);
            somaFaixas.put("7/D", 0.0);
            somaFaixas.put("15/D", 0.0);
            somaFaixas.put("30/D", 0.0);
            if (diasRef >= 60) somaFaixas.put("60/D", 0.0);
            if (diasRef >= 90) somaFaixas.put("90/D", 0.0);

            for (Financeiro f : lista) {
                int diasAtraso = 0;
                try { diasAtraso = Math.abs(Integer.parseInt(f.getDias_atraso())); } catch (Exception e) {}
                double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

                if (diasAtraso == 0) somaFaixas.put("Hoje", somaFaixas.get("Hoje") + valor);
                else if (diasAtraso <= 7) somaFaixas.put("7/D", somaFaixas.get("7/D") + valor);
                else if (diasAtraso <= 15) somaFaixas.put("15/D", somaFaixas.get("15/D") + valor);
                else if (diasAtraso <= 30) somaFaixas.put("30/D", somaFaixas.get("30/D") + valor);
                else if (diasAtraso <= 60 && diasRef >= 60) somaFaixas.put("60/D", somaFaixas.get("60/D") + valor);
                else if (diasAtraso <= 90 && diasRef >= 90) somaFaixas.put("90/D", somaFaixas.get("90/D") + valor);
            }
        } else {
            somaFaixas.put("1-7/D", 0.0);
            somaFaixas.put("8-15/D", 0.0);
            somaFaixas.put("16-30/D", 0.0);
            if (diasRef >= 60) somaFaixas.put("31-60/D", 0.0);
            if (diasRef >= 90) somaFaixas.put("61-90/D", 0.0);

            for (Financeiro f : lista) {
                int diasAtraso = 0;
                try { diasAtraso = Integer.parseInt(f.getDias_atraso()); } catch (Exception e) {}
                double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

                if (diasAtraso > 0) {
                    if (diasAtraso <= 7) somaFaixas.put("1-7/D", somaFaixas.get("1-7/D") + valor);
                    else if (diasAtraso <= 15) somaFaixas.put("8-15/D", somaFaixas.get("8-15/D") + valor);
                    else if (diasAtraso <= 30) somaFaixas.put("16-30/D", somaFaixas.get("16-30/D") + valor);
                    else if (diasAtraso <= 60 && diasRef >= 60) somaFaixas.put("31-60/D", somaFaixas.get("31-60/D") + valor);
                    else if (diasAtraso <= 90 && diasRef >= 90) somaFaixas.put("61-90/D", somaFaixas.get("61-90/D") + valor);
                }
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>(somaFaixas.keySet());
        int i = 0;
        for (Double valor : somaFaixas.values()) {
            entries.add(new BarEntry(i++, valor.floatValue()));
        }

        BarDataSet set1 = new BarDataSet(entries, "");
        List<GradientColor> gradientColors = new ArrayList<>();
        for (int j = 0; j < labels.size(); j++) {
            int colorIdx = tipo.equals("FIN_VC") ? (j + 4) % 6 : j % 6;
            gradientColors.add(new GradientColor(ColorTemplateApp.GRADIENT_START_6[colorIdx], ColorTemplateApp.GRADIENT_END_6[colorIdx]));
        }
        set1.setGradientColors(gradientColors);
        set1.setDrawValues(true);
        set1.setValueTextSize(textSize);
        set1.setValueFormatter(new CustomValueFormatter());

        BarData data = new BarData(set1);
        data.setBarWidth(0.55f);
        barChart.setData(data);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getLegend().setEnabled(false);
        barChart.setFitBars(true);
        barChart.animateY(1500);
        barChart.invalidate();
    }

    private void setData(List<ParticipacaoFaturamento> lista) {
        /* CÓDIGO ANTERIOR COMENTADO
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<LegendEntry> legends = new ArrayList<>();
        double total = 0f;
        double valorOutros = 0f;
        int limiteTop = 15; // Mostra os 15 maiores e agrupa o resto
        float textSize = 10;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                limiteTop = 20;
                textSize = 12;
                break;
            default:
                //limiteTop = 5;
        }
        for (ParticipacaoFaturamento p : lista) {
            try {
                total += Float.parseFloat(p.getValor_liquido());
            } catch (Exception e) { e.printStackTrace(); }
        }

        for (int i = 0; i < lista.size(); i++) {
            try {
                double valor = Double.parseDouble(lista.get(i).getValor_liquido());
                double percentual = (valor / total) * 100;

                if (i < limiteTop) {
                    // Adiciona os itens individuais e salva o valor formatado no 'data'
                    entries.add(new PieEntry(Float.parseFloat(String.valueOf(percentual)), lista.get(i).getNome(), MascaraUtil.mascaraRealSimbolo(String.valueOf(valor))));
                    legends.add(new LegendEntry(
                            lista.get(i).getNome() + " (" + MascaraUtil.duasCasaDecimal(String.valueOf(percentual)) + "%)",
                            Legend.LegendForm.DEFAULT, 8f, 2f, null,
                            limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16[i] : ColorTemplateApp.PIE_COLORS_21[i]
                    ));
                } else {
                    // Soma o restante para o grupo "Outros"
                    valorOutros += percentual;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        // Adiciona a fatia "Outros" se houver valores agrupados
        if (valorOutros > 0) {
            entries.add(new PieEntry(Float.parseFloat(String.valueOf(valorOutros)), "OUTROS", MascaraUtil.mascaraRealSimbolo(String.valueOf(valorOutros / 100 * total))));
            if ( limiteTop == 15)
                legends.add(new LegendEntry(
                        "OUTROS (" + MascaraUtil.mascaraRealSimboloD(valorOutros) + "%)",
                        Legend.LegendForm.DEFAULT, 8f, 2f, null,
                        limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16[15] : ColorTemplateApp.PIE_COLORS_21[20] // Cor cinza
                ));
            else
                legends.add(new LegendEntry(
                        "OUTROS (" + MascaraUtil.mascaraRealSimboloD(valorOutros) + "%)",
                        Legend.LegendForm.DEFAULT, 8f, 2f, null,
                        limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16[15] : ColorTemplateApp.PIE_COLORS_21[20] // Cor cinza
                ));
        }

        String totalGeralGrupo = total > 0 ? MascaraUtil.mascaraRealSimboloD(total) : "R$ 0,00";
        pieChart.setCenterText(totalGeralGrupo);

        pieChart.setCenterTextSize(10f);
        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setColors(limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16 : ColorTemplateApp.PIE_COLORS_21);
        
        // Remove as linhas de chamada pretas
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setValueLineWidth(0f);
        dataSet.setValueLineColor(Color.TRANSPARENT);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(textSize);
        data.setValueTextColor(Color.TRANSPARENT);
        l.setCustom(legends);
        //pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.setData(data);
        pieChart.setDrawEntryLabels(false);
        pieChart.highlightValues(null);
        pieChart.invalidate();
        */

        // --- NOVA LÓGICA DE AGREGAÇÃO POR GRUPO PARA O CARROSSEL ---
        java.util.Map<String, ParticipacaoFaturamento> mapaAgregado = new java.util.LinkedHashMap<>();
        for (ParticipacaoFaturamento pf : lista) {
            String idGrupo = pf.getId() != null ? pf.getId().trim() : "0";
            double valor = Double.parseDouble(pf.getValor_liquido() == null || pf.getValor_liquido().isEmpty() ? "0" : pf.getValor_liquido());

            // Regra de Devolução: Se for entrada ("E"), abate do valor
            boolean isDevolucao = pf.getE_entrada_s_saida() != null && pf.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
            if (isDevolucao) {
                valor = -Math.abs(valor);
            }

            if (!mapaAgregado.containsKey(idGrupo)) {
                ParticipacaoFaturamento consolidado = new ParticipacaoFaturamento();
                consolidado.setId(idGrupo);
                consolidado.setNome(pf.getNome());
                consolidado.setValor_liquido(String.valueOf(valor));
                mapaAgregado.put(idGrupo, consolidado);
            } else {
                ParticipacaoFaturamento existente = mapaAgregado.get(idGrupo);
                double valorExistente = Double.parseDouble(existente.getValor_liquido());
                existente.setValor_liquido(String.valueOf(valorExistente + valor));
            }
        }

        List<ParticipacaoFaturamento> listaConsolidada = new ArrayList<>(mapaAgregado.values());
        // Re-ordena por valor líquido decrescente
        java.util.Collections.sort(listaConsolidada, (o1, o2) -> Double.compare(Double.parseDouble(o2.getValor_liquido()), Double.parseDouble(o1.getValor_liquido())));

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<LegendEntry> legends = new ArrayList<>();
        double total = 0f;
        double valorOutros = 0f;
        int limiteTop = 15;
        float textSize = 10;

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 20;
            textSize = 12;
        }

        for (ParticipacaoFaturamento p : listaConsolidada) {
            try {
                total += Double.parseDouble(p.getValor_liquido());
            } catch (Exception e) { e.printStackTrace(); }
        }

        for (int i = 0; i < listaConsolidada.size(); i++) {
            try {
                double valor = Double.parseDouble(listaConsolidada.get(i).getValor_liquido());
                double percentual = total > 0 ? (valor / total) * 100 : 0;

                if (i < limiteTop) {
                    entries.add(new PieEntry((float) percentual, listaConsolidada.get(i).getNome(), MascaraUtil.mascaraRealSimbolo(String.valueOf(valor))));
                    legends.add(new LegendEntry(
                            listaConsolidada.get(i).getNome() + " (" + MascaraUtil.duasCasaDecimal(String.valueOf(percentual)) + "%)",
                            Legend.LegendForm.DEFAULT, 8f, 2f, null,
                            limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16[i] : ColorTemplateApp.PIE_COLORS_21[i]
                    ));
                } else {
                    valorOutros += percentual;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        if (valorOutros > 0) {
            entries.add(new PieEntry((float) valorOutros, "OUTROS", MascaraUtil.mascaraRealSimbolo(String.valueOf(valorOutros / 100 * total))));
            legends.add(new LegendEntry(
                    "OUTROS (" + MascaraUtil.mascaraRealSimboloD(valorOutros) + "%)",
                    Legend.LegendForm.DEFAULT, 8f, 2f, null,
                    limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16[15] : ColorTemplateApp.PIE_COLORS_21[20]
            ));
        }

        String totalGeralGrupo = total > 0 ? MascaraUtil.mascaraRealSimboloD(total) : "R$ 0,00";
        pieChart.setCenterText(totalGeralGrupo);
        pieChart.setCenterTextSize(10f);

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setColors(limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16 : ColorTemplateApp.PIE_COLORS_21);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setValueLineWidth(0f);
        dataSet.setValueLineColor(Color.TRANSPARENT);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(textSize);
        data.setValueTextColor(Color.TRANSPARENT);
        l.setCustom(legends);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.setData(data);
        pieChart.setDrawEntryLabels(false);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    private void createHorChart(List<ParticipacaoFaturamento> lista) {
        // 1. Reset Total e Configurações de Escala
        horChart.clear();
        horChart.fitScreen();
        horChart.setNoDataText("Processando...");
        
        /* CÓDIGO ANTERIOR COMENTADO
        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();
        double valorOutros = 0f;
        double totalGeralClientes = 0f;
        int limiteTop = 15;
        float textSize = 10;

        // Calcula total geral com segurança
        for (ParticipacaoFaturamento p : lista) {
            try {
                String valStr = p.getValor_liquido();
                if (valStr != null && !valStr.isEmpty()) {
                    totalGeralClientes += Double.parseDouble(valStr);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 20;
            textSize = 12;
        }

        // 2. Processamento da Lista para Ranking
        int count = 0;
        for (int i = 0; i < lista.size(); i++) {
            try {
                String valStr = lista.get(i).getValor_liquido();
                double valor = (valStr != null && !valStr.isEmpty()) ? Double.parseDouble(valStr) : 0;

                if (count < limiteTop) {
                    String nome = lista.get(i).getNome();
                    if (nome == null || nome.isEmpty()) nome = "CLIENTE S/N";
                    labels.add(nome.substring(0,10));
                    count++;
                } else {
                    valorOutros += valor;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        if (valorOutros > 0) {
            labels.add("OUTROS..");
        }

        // 3. Criação das Entradas (Top 1 no Topo do gráfico)
        for (int i = 0; i < labels.size(); i++) {
            double valor = 0;
            String nomeOriginal = "";
            int listIdx = labels.size() - 1 - i;

            if (labels.get(listIdx).equals("OUTROS..") && valorOutros > 0) {
                valor = valorOutros;
                nomeOriginal = "OUTROS";
            } else if (listIdx < lista.size()) {
                String valStr = lista.get(listIdx).getValor_liquido();
                valor = (valStr != null && !valStr.isEmpty()) ? Double.parseDouble(valStr) : 0;
                nomeOriginal = lista.get(listIdx).getNome();
            }

            if (nomeOriginal == null) nomeOriginal = "S/N";
            String nomeLimpo = nomeOriginal.replace("#", " ").replace("|", " ");
            double percentual = totalGeralClientes > 0 ? (valor / totalGeralClientes) * 100 : 0;

            entries.add(new BarEntry(i, (float) valor, nomeLimpo + "###" + MascaraUtil.duasCasaDecimal(String.valueOf(percentual))));
        }
        */

        // --- NOVA LÓGICA DE AGREGAÇÃO POR CLIENTE PARA O CARROSSEL ---
        java.util.Map<String, ParticipacaoFaturamento> mapaAgregado = new java.util.LinkedHashMap<>();
        for (ParticipacaoFaturamento pf : (lista != null ? lista : new ArrayList<ParticipacaoFaturamento>())) {
            String idCli = pf.getId() != null ? pf.getId().trim() : "0";
            double valor = Double.parseDouble(pf.getValor_liquido() == null || pf.getValor_liquido().isEmpty() ? "0" : pf.getValor_liquido());

            // Regra de Devolução: Se for entrada ("E"), abate do valor do cliente
            boolean isDevolucao = pf.getE_entrada_s_saida() != null && pf.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
            if (isDevolucao) {
                valor = -Math.abs(valor);
            }

            if (!mapaAgregado.containsKey(idCli)) {
                ParticipacaoFaturamento consolidado = new ParticipacaoFaturamento();
                consolidado.setId(idCli);
                consolidado.setNome(pf.getNome());
                consolidado.setValor_liquido(String.valueOf(valor));
                mapaAgregado.put(idCli, consolidado);
            } else {
                ParticipacaoFaturamento existente = mapaAgregado.get(idCli);
                double valorExistente = Double.parseDouble(existente.getValor_liquido());
                existente.setValor_liquido(String.valueOf(valorExistente + valor));
            }
        }

        List<ParticipacaoFaturamento> listaConsolidada = new ArrayList<>(mapaAgregado.values());
        // Re-ordena por valor líquido decrescente (Ranking Real)
        java.util.Collections.sort(listaConsolidada, (o1, o2) -> Double.compare(Double.parseDouble(o2.getValor_liquido()), Double.parseDouble(o1.getValor_liquido())));

        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();
        double valorOutros = 0f;
        double totalGeralClientes = 0f;
        int limiteTop = 15;
        float textSize = 10;

        // Calcula total geral com segurança usando a lista consolidada
        for (ParticipacaoFaturamento p : listaConsolidada) {
            try {
                totalGeralClientes += Double.parseDouble(p.getValor_liquido());
            } catch (Exception e) { e.printStackTrace(); }
        }

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 20;
            textSize = 12;
        }

        // 2. Processamento da Lista para Ranking (usando listaConsolidada)
        int count = 0;
        for (int i = 0; i < listaConsolidada.size(); i++) {
            try {
                double valor = Double.parseDouble(listaConsolidada.get(i).getValor_liquido());
                if (count < limiteTop) {
                    String nome = listaConsolidada.get(i).getNome();
                    if (nome == null || nome.isEmpty()) nome = "CLIENTE S/N";
                    labels.add(nome.substring(0, Math.min(nome.length(), 10)));
                    count++;
                } else {
                    valorOutros += valor;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        if (valorOutros > 0) {
            labels.add("OUTROS..");
        }

        // 3. Criação das Entradas (Top 1 no Topo do gráfico)
        for (int i = 0; i < labels.size(); i++) {
            double valor = 0;
            String nomeOriginal = "";
            int listIdx = labels.size() - 1 - i;

            if (labels.get(listIdx).equals("OUTROS..") && valorOutros > 0) {
                valor = valorOutros;
                nomeOriginal = "OUTROS";
            } else if (listIdx < listaConsolidada.size()) {
                valor = Double.parseDouble(listaConsolidada.get(listIdx).getValor_liquido());
                nomeOriginal = listaConsolidada.get(listIdx).getNome();
            }

            if (nomeOriginal == null) nomeOriginal = "S/N";
            String nomeLimpo = nomeOriginal.replace("#", " ").replace("|", " ");
            double percentual = totalGeralClientes > 0 ? (valor / totalGeralClientes) * 100 : 0;

            entries.add(new BarEntry(i, (float) valor, nomeLimpo + "###" + MascaraUtil.duasCasaDecimal(String.valueOf(percentual))));
        }

        if (entries.isEmpty()) {
            horChart.setNoDataText("Nenhum dado encontrado.");
            horChart.invalidate();
            return;
        }

        // 4. Configuração Visual do DataSet
        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(limiteTop == 15 ? ColorTemplateApp.PIE_COLORS_16_RANK : ColorTemplateApp.PIE_COLORS_21_RANK);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(textSize);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                try {
                    String dataStr = (String) barEntry.getData();
                    if (dataStr != null && dataStr.contains("###")) {
                        String[] parts = dataStr.split("###");
                        String nomeParaExibir = parts[0];
                        // Truncamento seguro: garante visibilidade sem quebrar a renderização

                        if (nomeParaExibir.length() > 25) nomeParaExibir =
                                nomeParaExibir.substring(0, 23) + "..";
                        return nomeParaExibir + " - " + MascaraUtil.mascaraRealSimbolo(barEntry.getY());
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return "";
            }
        });

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);
        
        // --- AJUSTE DE SEGURANÇA: Seta os dados PRIMEIRO, depois o renderer ---
        horChart.setData(data);
        horChart.setRenderer(new CustomHorizontalBarRenderer(horChart, horChart.getAnimator(), horChart.getViewPortHandler()));
        horChart.notifyDataSetChanged();

        horChart.getDescription().setEnabled(false);
        horChart.getLegend().setEnabled(false);
        horChart.setDrawGridBackground(false);

        XAxis xAxis = horChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        // Ajuste fino de escala
        horChart.getAxisLeft().setEnabled(false);
        horChart.getAxisLeft().setAxisMinimum(0f);
        horChart.getAxisRight().setEnabled(false);

        // Offset lateral ajustado (Texto agora alinhado à esquerda pelo renderer)
        horChart.setExtraOffsets(5, 5, 20, 5);
        horChart.setDrawValueAboveBar(true);

        horChart.setFitBars(true);
        horChart.notifyDataSetChanged();
        horChart.animateY(1500);
        horChart.invalidate();
    }
    private static int[] gerarCoresPastel(int quantidade) {
        int[] cores = new int[quantidade];
        Random random = new Random();

        for (int i = 0; i < quantidade; i++) {
            // Gera cores pastel
            int r = (random.nextInt(256) + 255) / 2; // Red
            int g = (random.nextInt(256) + 255) / 2; // Green
            int b = (random.nextInt(256) + 255) / 2; // Blue

            // Gera cor sequencial
            //int r = (i * 255 / quantidade) % 256; // Red
            //int g = ((i + quantidade / 3) * 255 / quantidade) % 256; // Green
            //int b = ((i + 2 * quantidade / 3) * 255 / quantidade) % 256; // Blue

            // Gera cor sequencial com as cores mais claras primeiro
            //int r = (int) (255 * (i + 1) / (quantidade + 1)); // Red
            // int g = (int) (255 * (i + 1) / (quantidade + 1)); // Green
            //int b = (int) (255 * (i + 1) / (quantidade + 1)); // Blue

            // Gera cor sequencial com as cores mais claras primeiro detacando a cor verde
            //int r = (int) (255 * i / (quantidade - 1)); // Red
            //int g = 255; // Green
            //int b = (int) (255 * (quantidade - 1 - i) / (quantidade - 1)); // Blue

            cores[i] = Color.rgb(r, g, b);
        }
        return cores;
    }




    /**
     * AJUSTE: Nova lógica robusta para labels de meses (Compatível com MesAnoMeta e MesSemana)
     * Padronizado para formato MES/YY conforme solicitado.
     */
    public String setMes(String mes) {
        if (mes == null || mes.isEmpty()) return "";

        String mesRef = mes.toUpperCase().trim();
        String anoRef = "";

        // Se contiver hífen, assume formato YYYY-MM ou similar
        if (mesRef.contains("-")) {
            try {
                String[] partes = mesRef.split("-");
                if (partes.length >= 2) {
                    anoRef = partes[0].length() >= 4 ? partes[0].substring(2, 4) : partes[0];
                    mesRef = partes[1];
                }
            } catch (Exception e) {
                // Fallback para substring se o split falhar por algum motivo
                if (mesRef.length() >= 7) {
                    anoRef = mesRef.substring(2, 4);
                    mesRef = mesRef.substring(5);
                }
            }
        }

        // Trata meses com zero à esquerda (ex: "01" -> "1")
        if (mesRef.startsWith("0") && mesRef.length() > 1) {
            mesRef = mesRef.substring(1);
        }

        String labelMes = "";
        if (mesRef.equals("1") || mesRef.contains("JANEIRO") || mesRef.equals("JAN")) labelMes = "JAN";
        else if (mesRef.equals("2") || mesRef.contains("FEVEREIRO") || mesRef.equals("FEV")) labelMes = "FEV";
        else if (mesRef.equals("3") || mesRef.contains("MARÇO") || mesRef.equals("MAR")) labelMes = "MAR";
        else if (mesRef.equals("4") || mesRef.contains("ABRIL") || mesRef.equals("ABR")) labelMes = "ABR";
        else if (mesRef.equals("5") || mesRef.contains("MAIO") || mesRef.equals("MAI")) labelMes = "MAI";
        else if (mesRef.equals("6") || mesRef.contains("JUNHO") || mesRef.equals("JUN")) labelMes = "JUN";
        else if (mesRef.equals("7") || mesRef.contains("JULHO") || mesRef.equals("JUL")) labelMes = "JUL";
        else if (mesRef.equals("8") || mesRef.contains("AGOSTO") || mesRef.equals("AGO")) labelMes = "AGO";
        else if (mesRef.equals("9") || mesRef.contains("SETEMBRO") || mesRef.equals("SET")) labelMes = "SET";
        else if (mesRef.equals("10") || mesRef.contains("OUTUBRO") || mesRef.equals("OUT")) labelMes = "OUT";
        else if (mesRef.equals("11") || mesRef.contains("NOVEMBRO") || mesRef.equals("NOV")) labelMes = "NOV";
        else if (mesRef.equals("12") || mesRef.contains("DEZEMBRO") || mesRef.equals("DEZ")) labelMes = "DEZ";
        else labelMes = mesRef; // Caso não identifique, mostra o que veio

        return labelMes + (anoRef.isEmpty() ? "" : "/" + anoRef);
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

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null) return;

        if (e instanceof PieEntry) {
            PieEntry pe = (PieEntry) e;
            pieChart.setCenterText(pe.getLabel() + "\n" + pe.getData());
            pieChart.setCenterTextSize(10f);
        } else if (e instanceof BarEntry) {
            String data = (String) e.getData();
            if (data != null && data.contains("###")) {
                String[] parts = data.split("###");
                DialogHelper.showFeedbackDialog(
                        context,
                        "Participação de Faturamento",
                        parts[0] + ": " + parts[1] + "% do faturamento total do período.",
                        true,
                        R.drawable.ic_person_color_primary_24_24,
                        null,
                        null
                );
            }
        }
    }

    @Override
    public void onNothingSelected() {

    }

    public class CustomValueFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            // Hide values that are less than a certain threshold (e.g., 5.0)
            return MascaraUtil.mascaraRealSemSimbolo(value);
        }
    }

    /**
     * Renderer customizado para alinhar o texto à esquerda (início da barra)
     * e permitir que ele se expanda para fora caso a barra seja curta.
     */
    public class CustomHorizontalBarRenderer extends HorizontalBarChartRenderer {
        public CustomHorizontalBarRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
            super(chart, animator, viewPortHandler);
        }

        @Override
        public void drawValue(Canvas c, String valueText, float x, float y, int color) {
            float xPos = mViewPortHandler.contentLeft() + Utils.convertDpToPixel(5f);
            mValuePaint.setTextAlign(Paint.Align.LEFT);
            mValuePaint.setColor(color);
            c.drawText(valueText, xPos, y, mValuePaint);
        }
    }
}
