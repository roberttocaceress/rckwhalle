package com.example.rcksuporte05.rcksistemas.activity;

import static android.view.View.INVISIBLE;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.util.ColorTemplateApp;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFListaGraficoVendasUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityGraficoFaturamento extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.barChart)
    BarChart barChart;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btnFlipCard)
    Button btnFlipCard;

    @BindView(R.id.caixa_layout_grafico)
    FrameLayout caixa_layout_grafico;

    @BindView(R.id.caixa_layout_resumo)
    FrameLayout caixa_layout_resumo;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerLayout;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;



    PackageInfo pInfo = null;
    Context context;
    private Configuracao configuracao = new Configuracao();
    private DBHelper db = new DBHelper(this);
    private boolean isMaximized = false;
    private Legend l;
    protected Typeface tfLight;
    private List<ParticipacaoFaturamento> lista;
    PDFListaGraficoVendasUtil pdfListaGraficoVendasUtil;
    private String tipoRelatorio;
    private boolean listaVisivel, graficoVivel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_faturamento);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(v -> onBackPressed());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        barChart.setNoDataText("");
        barChart.setNoDataTextColor(Color.BLACK);
        //dataInicial  = getIntent().getStringExtra("dataInicial");
        //dataFinal    = getIntent().getStringExtra("dataFinal");
        tipoRelatorio  = getIntent().getStringExtra("tipoRelatorio");
        btnFlipCard.setText("Aguarde...");
        btnFlipCard.setEnabled(false);
        if ( tipoRelatorio.equalsIgnoreCase("M") || tipoRelatorio.equalsIgnoreCase("MG")) {
            toolbar.setSubtitle("Periodo dos 6 ultimos meses.");
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorButton), PorterDuff.Mode.SRC_IN);
        }
        if ( tipoRelatorio.equalsIgnoreCase("S")) {
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            toolbar.setSubtitle("Periodo dos 6 ultimos meses.");
        }
        if ( tipoRelatorio.equalsIgnoreCase("MM")) {
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            toolbar.setSubtitle("Periodo dos 6 ultimos meses.");
        }
        graficoVivel = false;
        listaVisivel = true;
        getEvolucaoVendas(1,1);
        //createBarChart();
        btnFlipCard.setOnClickListener(v -> {
            if ( !isMaximized) {
                caixa_layout_grafico.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryTransparente05));
                flipCard(context,  caixa_layout_resumo, caixa_layout_grafico,isMaximized);
                isMaximized = !isMaximized;
                if ( tipoRelatorio.equalsIgnoreCase("S"))
                    btnFlipCard.setText("Visualizar resumo por semana");
                if ( tipoRelatorio.equalsIgnoreCase("M") || tipoRelatorio.equalsIgnoreCase("MG") || tipoRelatorio.equalsIgnoreCase("MM"))
                    btnFlipCard.setText("Visualizar resumo por mes");

                btnFlipCard.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0/*R.drawable.ic_view_list_24_24*/);
                graficoVivel = true;
                listaVisivel = false;
            } else {
                caixa_layout_resumo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryTransparente05));
                flipCard(context,  caixa_layout_grafico,  caixa_layout_resumo, isMaximized );
                isMaximized = !isMaximized;
                if ( tipoRelatorio.equalsIgnoreCase("S")) {
                    Toast.makeText(context,"Melhor visualizado no modo paisagem!", Toast.LENGTH_LONG).show();
                    btnFlipCard.setText("Visualizar gráfico por semana");
                }
                if ( tipoRelatorio.equalsIgnoreCase("M") || tipoRelatorio.equalsIgnoreCase("MG") || tipoRelatorio.equalsIgnoreCase("MM"))
                    btnFlipCard.setText("Visualizar gráfico por mes");
                btnFlipCard.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0/* R.drawable.ic_pie_chart_24_24*/);
                graficoVivel =false;
                listaVisivel = true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmpresaParametro empresa = new EmpresaParametroDAO(db).getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + UsuarioHelper.getUsuario().getId_usuario() + "'");
                File filePDF;
                pdfListaGraficoVendasUtil = new PDFListaGraficoVendasUtil();
                if (empresa == null) {
                    if ( graficoVivel) {
                        //pdfListaGraficoVendasUtil.PDFGraficoVendasUtil(barChart, empresa, ActivityParticipacaoFaturamento.this, db,
                         //       ("Periodo de " + MascaraUtil.formataData(dataInicial, "/", 8) + " A " + MascaraUtil.formataData(dataFinal, "/", 8)));

                        filePDF = pdfListaGraficoVendasUtil.criandoGraficoPdf();

                    } else {
                        //pdfListaGraficoVendasUtil.PDFListaVendasUtil(lista, empresa, ActivityParticipacaoFaturamento.this, db,
                        //        ("Periodo de " + MascaraUtil.formataData(dataInicial, "/", 8) + " A " + MascaraUtil.formataData(dataFinal, "/", 8)));

                        filePDF = pdfListaGraficoVendasUtil.criandoListaPdf();
                    }
                    if (filePDF == null) {
                        return;
                    }
                    try {
                        startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                .setType("pdf/*")
                                .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(ActivityGraficoFaturamento.this, getApplicationContext().getPackageName() + ".provider", filePDF))
                                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION), "Compartilhar arquivo"));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    private void getEvolucaoVendas(int periodo, int idxItemMenu) {
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        String versaoApp = pInfo.versionName.substring(0, 5);
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
            Call<List<EvolucaoFaturamento>> call = null;
            /*if (idxItemMenu == 0)
                call = rotasApi.listaParticipacaoVendasHora(usuario.getId_usuario(), dataIni, dataFin, cabecalho);
            if (idxItemMenu == 1)
                call = rotasApi.listaParticipacaoVendasDiaDia(usuario.getId_usuario(), dataIni, dataFin, cabecalho);
            if (idxItemMenu == 2)
                call = rotasApi.listaParticipacaoVendasDiaSemana("LJ", usuario.getId_usuario(), dataIni, dataFin, cabecalho);
            if (idxItemMenu == 3)*/
            if ( tipoRelatorio.equalsIgnoreCase("M"))
               call = apiRotas.getTotalVendaMes(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
            if ( tipoRelatorio.equalsIgnoreCase("MM"))
                call = apiRotas.getTotalVendaMesMes(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
            if ( tipoRelatorio.equalsIgnoreCase("MG"))
               call = apiRotas.getTotalVendaMesMesGrupo(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
            if ( tipoRelatorio.equalsIgnoreCase("S"))
                call = apiRotas.getTotalVendaSemanaSemana(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);

            if (call != null)
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<List<EvolucaoFaturamento>> call, Response<List<EvolucaoFaturamento>> response) {
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(INVISIBLE);
                        if (response.code() == 200) {

                            List<EvolucaoFaturamento> lista = response.body();
                            //setReciclerView(lista);
                            if ( tipoRelatorio.equalsIgnoreCase("M")) {
                                setReciclerView(lista);
                                createbarChart(lista);
                            }
                            if ( tipoRelatorio.equalsIgnoreCase("MM")) {
                                setReciclerView(lista);
                                createbarChartGroup(lista);
                            }
                            if (tipoRelatorio.equalsIgnoreCase("MG")) {
                                setReciclerView(lista);
                                createbarChartstacked(lista);
                            }
                            if (tipoRelatorio.equalsIgnoreCase("S")) {
                                setReciclerView(lista);
                                createbarChartstacked(lista);
                            }
                                //return;
                                //createbarChart(lista);
                        } else {
                            hideShimmer();
                            Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.\n" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EvolucaoFaturamento>> call, Throwable t) {
                        progressBar.setIndeterminate(false);
                        hideShimmer();
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor!! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                    }
                });
            else {
                progressBar.setIndeterminate(false);
                hideShimmer();
                Toast.makeText(context, "Falha ao obter os parametros", Toast.LENGTH_LONG).show();
            }

    }
    /*private void createBarChart() {
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
        pieChart.setDrawEntryLabels(true); // To remove labels from piece of pie
        pieChart.getDescription().setEnabled(false); // To remove description of pie
        pieChart.animateXY(1500, 1500);
        l = pieChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setYOffset(10f);
        l.setYEntrySpace(10f);
        l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
    }*/

    private void createbarChartOld(List<EvolucaoFaturamento> lista) {
        barChart.setDrawBarShadow(false);
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
        xAxis.setTextSize(10);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
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


        String[] labels = new String[lista.size() + 2];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size(); i++) {
            z--;
            if ( tipoRelatorio.equalsIgnoreCase("M")) {
                labels[i] = setMes(lista.get(z).getMes());
            } else {
                labels[i] = MascaraUtil.formataData(lista.get(z).getMes(), "/", 8) + "-" + MascaraUtil.formataData(lista.get(z).getAno(), "/", 8);
            }
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
        colors.add(Color.parseColor("#EABF96"));
        colors.add(Color.parseColor("#31595F"));
        set1.setColors(colors);
        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        //set1.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        hideShimmer();
        //barChart.getLegend().setTextSize(6f);
        barChart.getLegend().setEnabled(false);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(3500, 3500);
    }

    private void createbarChart(List<EvolucaoFaturamento> lista) {
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
        colors.add(Color.parseColor("#EABF96"));
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
        data.setValueFormatter(new ActivityGraficoFaturamento.CustomValueFormatter() {
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
        leftAxis.setDrawLabels(false);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

    private void createbarChartGroup(List<EvolucaoFaturamento> lista) {
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
        hideShimmer();
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
                stackLabes[i] = lista.get(idxSemanas).getTotal_por_grupo().get(i).getMes() + "º SEM.";
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
            if (stackLabes.length == 9)
                set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_9);
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
        data.setValueTextSize(10f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        hideShimmer();
        //xAxis.setAxisMaximum(data.getGroupWidth(groupSpace, barSpace) * lista.size() );


        barChart.setData(data);

        barChart.setFitBars(true);

        barChart.getLegend().setWordWrapEnabled(true);  //Faz a quebra das linhas na legenda
        barChart.setExtraBottomOffset(8f);
        //barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
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

    public String setMesAno(String mes) {

        if (mes.substring(5).equalsIgnoreCase("1") || mes.substring(5).equalsIgnoreCase("01"))
            return "JANEIRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("2") || mes.substring(5).equalsIgnoreCase("02"))
            return "FEVEREIRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("3") || mes.substring(5).equalsIgnoreCase("03"))
            return "MARÇO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("4") || mes.substring(5).equalsIgnoreCase("04"))
            return "ABRIL DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("5") || mes.substring(5).equalsIgnoreCase("05"))
            return "MAIO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("6") || mes.substring(5).equalsIgnoreCase("06"))
            return "JUNHO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("7") || mes.substring(5).equalsIgnoreCase("07"))
            return "JULHO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("8") || mes.substring(5).equalsIgnoreCase("08"))
            return "AGOSTO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("9") || mes.substring(5).equalsIgnoreCase("09"))
            return "SETEMBRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("10"))
            return "OUTUBRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("11"))
            return "NOVEMBRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("12"))
            return "DEZEMBRO DE " + mes.substring(0,4) ;
        else
            return "";
    }

    private void setReciclerView(List<EvolucaoFaturamento> lista) {
        float valor_bruto  = 0.00f;
        float valor_liquido = 0.00f;
        List<ParticipacaoFaturamento> listaFaturamento = new ArrayList<>();
        if ( tipoRelatorio.equalsIgnoreCase("MM") ||  tipoRelatorio.equalsIgnoreCase("MG") || tipoRelatorio.equalsIgnoreCase("S") ) {
            for (int i = 0; i < lista.size(); i++) {
                try {
                    valor_bruto += Float.parseFloat(lista.get(i).getTotal_bruto());
                    valor_liquido += Float.parseFloat(lista.get(i).getTotal_liquido());
                    ParticipacaoFaturamento participacaoFaturamento = new ParticipacaoFaturamento();
                    //if (tipoRelatorio.equalsIgnoreCase("M"))
                        participacaoFaturamento.setNome(setMesAno(lista.get(i).getMes()));
                    //else {
                    //    participacaoFaturamento.setNome(MascaraUtil.formataData(lista.get(i).getMes(), "/", 8) + " A " + MascaraUtil.formataData(lista.get(i).getAno(), "/", 8));
                    //}
                    participacaoFaturamento.setValor_bruto("0.00");
                    participacaoFaturamento.setValor_liquido(lista.get(i).getTotal_liquido());
                    listaFaturamento.add(participacaoFaturamento);
                } catch (NullPointerException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = lista.size() - 1 ; i >= 0 ; i--) {
                try {
                    valor_bruto += Float.parseFloat(lista.get(i).getTotal_bruto());
                    valor_liquido += Float.parseFloat(lista.get(i).getTotal_liquido());
                    ParticipacaoFaturamento participacaoFaturamento = new ParticipacaoFaturamento();
                    if (tipoRelatorio.equalsIgnoreCase("M"))
                       participacaoFaturamento.setNome(setMesAno(lista.get(i).getMes()));
                    else {
                        participacaoFaturamento.setNome(MascaraUtil.formataData(lista.get(i).getMes(), "/", 8) + " A " + MascaraUtil.formataData(lista.get(i).getAno(), "/", 8));
                    }
                    participacaoFaturamento.setValor_bruto("0.00");
                    participacaoFaturamento.setValor_liquido(lista.get(i).getTotal_liquido());
                    listaFaturamento.add(participacaoFaturamento);
                } catch (NullPointerException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        if ( lista.size() > 0) {
            ParticipacaoFaturamento participacaoFaturamento = new ParticipacaoFaturamento();
            participacaoFaturamento.setNome("TOTAL GERAL NO PERIODO");

            participacaoFaturamento.setValor_bruto(String.valueOf(valor_bruto));
            participacaoFaturamento.setValor_liquido(String.valueOf(valor_liquido));
            listaFaturamento.add(participacaoFaturamento);
        }


        ParticipacaoFaturamentoAdapter adapter = new ParticipacaoFaturamentoAdapter(listaFaturamento, tipoRelatorio.equalsIgnoreCase("MM") ? "MM" : "SS");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        isMaximized = true;
        btnFlipCard.setEnabled(true);
        btnFlipCard.callOnClick();
        hideShimmer();
    }
    private void hideShimmer() {
        shimmerLayout.stopShimmer();
        shimmerLayout.hideShimmer();
    }

    @SuppressLint("ResourceType")
    private void  flipCard(Context context, View visibleView, View inVisibleView, boolean isMaximized) {
        try {
            inVisibleView.setVisibility(INVISIBLE);
//            visibleView.setVisibility(View.VISIBLE);
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
            /*
            }else {
                    flipInAnimationSet = (AnimatorSet) AnimatorInflater.loadAnimator(
                            context,
                            R.anim.flip_in_left
                    );
                }
             */
            flipInAnimationSet.setTarget(visibleView);
            flipOutAnimatorSet.start();
            flipInAnimationSet.start();
            //inVisibleView.setVisibility(View.INVISIBLE);
            visibleView.setVisibility(View.VISIBLE);
            if ( visibleView.getId() == caixa_layout_grafico.getId()) {
                barChart.notifyDataSetChanged();
                barChart.invalidate();
                barChart.animateXY(1500, 1500);
            }
            Handler handler = new Handler();
            Runnable runnable = () -> {
                caixa_layout_resumo.setBackgroundColor(0);
                caixa_layout_grafico.setBackgroundColor(0);
            };
            handler.postDelayed(runnable, 1000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class CustomValueFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            // Hide values that are less than a certain threshold (e.g., 5.0)
          return MascaraUtil.mascaraRealSemSimbolo(value);
        }
    }
}

