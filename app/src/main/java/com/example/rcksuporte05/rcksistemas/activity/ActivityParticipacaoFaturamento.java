package com.example.rcksuporte05.rcksistemas.activity;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
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
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFListaGraficoVendasUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityParticipacaoFaturamento extends AppCompatActivity  {

    @BindView(R.id.pieChart)
    PieChart pieChart;

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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    PackageInfo pInfo = null;
    Context context;
    private EmpresaParametro empresaParametro;
    private Configuracao configuracao = new Configuracao();
    private DBHelper db = new DBHelper(this);
    private String dataInicial, dataFinal, tipoRelatorio;  //Grupo ou marca
    private boolean isMaximized = false;
    private  Legend l;
    protected Typeface tfLight;

    private List<ParticipacaoFaturamento> lista;
    PDFListaGraficoVendasUtil pdfListaGraficoVendasUtil;

    private boolean listaVisivel, graficoVivel;
    //String idVendedor = "999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participacao_faturamento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            //View rodape = findViewById(R.id.btnFlipCard);
            //rodape.setPadding(0, 0, 0, insets.bottom);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) btnFlipCard.getLayoutParams();
            // Somamos o espaço da barra (insets.bottom) + um respiro de 16dp (aprox. 48 pixels)
            int respiro = (int) (0 * getResources().getDisplayMetrics().density);
            params.bottomMargin = insets.bottom + respiro;
            btnFlipCard.setLayoutParams(params);
            return  windowInsets;
        });
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pieChart.setNoDataText("Aguarde...");
        pieChart.setNoDataTextColor(Color.BLACK);
        dataInicial   = getIntent().getStringExtra("dataInicial");
        dataFinal     = getIntent().getStringExtra("dataFinal");
        tipoRelatorio = getIntent().getStringExtra("tipoRelatorio");
        btnFlipCard.setText("Aguarde...");
        btnFlipCard.setEnabled(false);
        //toolbar.setSubtitle("Periodo de " + MascaraUtil.formataData(dataInicial, "/", 8) + " A " + MascaraUtil.formataData(dataFinal, "/", 8) );
        graficoVivel = false;
        listaVisivel = true;
        toolbar.setSubtitle("Periodo de " + MascaraUtil.formataData(dataInicial, "/", 8) + " A " + MascaraUtil.formataData(dataFinal, "/", 8) );
        createPieChart();
        getListaParticipacaoFaturamento(getIntent().getStringExtra("idVendedor"));

        //createPieChart();
        btnFlipCard.setOnClickListener(v -> {
            if ( !isMaximized) {
                caixa_layout_grafico.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryTransparente05));
                flipCard(context, caixa_layout_grafico, caixa_layout_resumo, isMaximized);
                isMaximized = !isMaximized;
                if ( tipoRelatorio.equalsIgnoreCase("G"))
                    btnFlipCard.setText("Visualizar resumo por grupo");
                if ( tipoRelatorio.equalsIgnoreCase("M"))
                    btnFlipCard.setText("Visualizar resumo por marca");

                btnFlipCard.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0/*R.drawable.ic_view_list_24_24*/);
                graficoVivel = true;
                listaVisivel = false;
            } else {
                caixa_layout_resumo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryTransparente05));
                flipCard(context,  caixa_layout_resumo, caixa_layout_grafico, isMaximized );
                isMaximized = !isMaximized;
                if ( tipoRelatorio.equalsIgnoreCase("G"))
                    btnFlipCard.setText("Visualizar gráfico por grupo");
                if ( tipoRelatorio.equalsIgnoreCase("M"))
                    btnFlipCard.setText("Visualizar gráfico por marca");
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
                        pdfListaGraficoVendasUtil.PDFGraficoVendasUtil(pieChart, empresa, ActivityParticipacaoFaturamento.this, db,
                                ("Periodo de " + MascaraUtil.formataData(dataInicial, "/", 8) + " A " + MascaraUtil.formataData(dataFinal, "/", 8)));

                        filePDF = pdfListaGraficoVendasUtil.criandoGraficoPdf();

                    } else {
                        pdfListaGraficoVendasUtil.PDFListaVendasUtil(lista, empresa, ActivityParticipacaoFaturamento.this, db,
                                ("Periodo de " + MascaraUtil.formataData(dataInicial, "/", 8) + " A " + MascaraUtil.formataData(dataFinal, "/", 8)));

                        filePDF = pdfListaGraficoVendasUtil.criandoListaPdf();
                    }
                    if (filePDF == null) {
                        return;
                    }
                    try {
                        startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                .setType("text/plain")
                                .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(ActivityParticipacaoFaturamento.this, getApplicationContext().getPackageName() + ".provider", filePDF))
                                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION), "Compartilhar text"));
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

    public void getListaParticipacaoFaturamento(String idUsuario) {
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        Map<String, String> cabecalho = new HashMap<>();
        if (UsuarioHelper.getUsuario().getToken() == null || UsuarioHelper.getUsuario().getToken().isEmpty()) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        }
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        String versaoApp = pInfo.versionName.substring(0, 5);
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<ParticipacaoFaturamento>> call;
        if (!configuracao.getId().isEmpty()) {
            try {
                if (tipoRelatorio.equalsIgnoreCase("G"))
                    call = apiRotas.getTotalVendaPorGrupo(Integer.parseInt(idUsuario), dataInicial, dataFinal, cabecalho);
                else if (tipoRelatorio.equalsIgnoreCase("M"))
                    call = apiRotas.getTotalVendaPorMarca(Integer.parseInt(idUsuario), dataInicial, dataFinal, cabecalho);
                else
                    call = null;
                if (call == null) {
                    Toast.makeText(context, "Tipo de relatorio não definido", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NullPointerException e) {
               call = null;
            } catch (Exception e) {
                call = null;
            }
             if ( call == null) {
                 onBackPressed();
             }
            try {
                call.enqueue(new Callback<List<ParticipacaoFaturamento>>() {
                    @Override
                    public void onResponse(Call<List<ParticipacaoFaturamento>> call, Response<List<ParticipacaoFaturamento>> response) {
                        if (response.code() == 200) {
                            lista = response.body();
                            if (lista.size() > 0)
                                setData(lista);
                                //setReciclerView(lista);
                            else {
                                Toast.makeText(context, "Sem registros para analise do relatorio", Toast.LENGTH_SHORT).show();
                                pieChart.setNoDataText("Falha ao obter os dados do relatorio!");
                                setData(new ArrayList<>());
                            }
                        } else {
                            pieChart.setNoDataText("Falha ao obter os dados do relatorio");
                            Toast.makeText(context, "Falha ao obter os dados do relatorio", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ParticipacaoFaturamento>> call, Throwable t) {
                        pieChart.setNoDataText("Falha ao obter os dados do relatorio!");
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (NullPointerException e) {
                onBackPressed();
            }
        } else {
            pieChart.setNoDataText("");
            Toast.makeText(context, "Parametros de acesso ao servidor não definido!", Toast.LENGTH_SHORT).show();
        }
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
    }

    private void setData(List<ParticipacaoFaturamento> lista) {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<LegendEntry> legends = new ArrayList<>();
        Float total = 0.00f;
        for (int i = 0; i < lista.size(); i++)
            try {
                total += Float.parseFloat(lista.get(i).getValor_liquido());
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
            }
        int index = 0;
        for (int i = 0; i < lista.size(); i++) {
            try {
                entries.add(new PieEntry((Float.parseFloat(lista.get(i).getValor_liquido()) / total) * 100,
                        MascaraUtil.duasCasaDecimal((Float.parseFloat(lista.get(i).getValor_liquido()) / total) * 100) + " %",
                        null));
                legends.add(new LegendEntry(lista.get(i).getNome() + " - " + MascaraUtil.duasCasaDecimal((Float.parseFloat(lista.get(i).getValor_liquido()) / total) * 100) + " %", Legend.LegendForm.DEFAULT, 10f, 2f, null, colors.get(i)));
            } catch (IndexOutOfBoundsException e) {
                entries.add(new PieEntry((Float.parseFloat(lista.get(i).getValor_liquido()) / total) * 100,
                        MascaraUtil.duasCasaDecimal((Float.parseFloat(lista.get(i).getValor_liquido()) / total) * 100) + " %",
                        null));
                legends.add(new LegendEntry(lista.get(i).getNome() + " - " + MascaraUtil.duasCasaDecimal((Float.parseFloat(lista.get(i).getValor_liquido()) / total) * 100) + " %", Legend.LegendForm.DEFAULT, 10f, 2f, null, colors.get(index)));
                index++;
                if ( index == 30) {
                    index = 0;
                }
            }
        }
        pieChart.setCenterText( total > 0.00f ? MascaraUtil.mascaraRealSimbolo(total)  :  "R$ 0,00" );
        pieChart.setCenterTextSize(10f);
        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.TRANSPARENT);
        l.setCustom(legends);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.setDrawEntryLabels(true);
        pieChart.highlightValues(null);
        pieChart.invalidate();
        setReciclerView(lista, colors);
    }

    private void setReciclerView(List<ParticipacaoFaturamento> lista, ArrayList<Integer> colors) {
       float valor_bruto  = 0.00f;
       float valor_liquido = 0.00f;

       for ( int i = 0; i < lista.size(); i++) {
           try {
               valor_bruto += Float.parseFloat(lista.get(i).getValor_bruto());
               valor_liquido += Float.parseFloat(lista.get(i).getValor_liquido());
           } catch ( NullPointerException|NumberFormatException e) {
               e.printStackTrace();
           }
       }
       if ( lista.size() > 0) {
           ParticipacaoFaturamento participacaoFaturamento = new ParticipacaoFaturamento();
           if ( tipoRelatorio.equalsIgnoreCase("G"))
               participacaoFaturamento.setNome("Total geral(grupo)");
           if ( tipoRelatorio.equalsIgnoreCase("M"))
               participacaoFaturamento.setNome("Total geral(marca)");

           participacaoFaturamento.setValor_bruto(String.valueOf(valor_bruto));
           participacaoFaturamento.setValor_liquido(String.valueOf(valor_liquido));
           lista.add(participacaoFaturamento);
       }


       ParticipacaoFaturamentoAdapter adapter = new ParticipacaoFaturamentoAdapter(lista, "GP");
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
            inVisibleView.setVisibility(View.INVISIBLE);
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
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                pieChart.animateXY(1500, 1500);
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
}