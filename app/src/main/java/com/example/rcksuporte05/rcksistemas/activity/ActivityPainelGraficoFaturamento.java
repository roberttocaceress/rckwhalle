package com.example.rcksuporte05.rcksistemas.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.GraficoFaturamentoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.data.PieData;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.Button;
import android.view.Gravity;
import androidx.core.view.GravityCompat;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
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

public class ActivityPainelGraficoFaturamento extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener, OnChartGestureListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.barChart1)
    BarChart barChart1;

    //@BindView(R.id.barChart2)
    //BarChart barChart2;

    @BindView(R.id.barChart3)
    BarChart barChart3;

    @BindView(R.id.pieChart)
    PieChart pieChart;

    @BindView(R.id.horChart)
    HorizontalBarChart horChart;
    //@BindView(R.id.barChart4)
    //BarChart barChart4;

    @BindView(R.id.lyt1)
    LinearLayout lyt1;

    @BindView(R.id.lyt2)
    LinearLayout lyt2;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.progressTitle1)
    TextView progressTitle1;

    @BindView(R.id.lyt3)
    LinearLayout lyt3;

    @BindView(R.id.lyt4)
    LinearLayout lyt4;

    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.progressTitle2)
    TextView progressTitle2;


    @BindView(R.id.lyt5)
    LinearLayout lyt5;

    @BindView(R.id.lyt6)
    LinearLayout lyt6;

    @BindView(R.id.progressBar3)
    ProgressBar progressBar3;
    @BindView(R.id.progressTitle3)
    TextView progressTitle3;

    @BindView(R.id.lyt10)
    LinearLayout lyt10;

    @BindView(R.id.lyt11)
    LinearLayout lyt11;
    @BindView(R.id.progressBar10)
    ProgressBar progressBar10;
    @BindView(R.id.progressTitle10)
    TextView progressTitle10;

    @BindView(R.id.vtotalVendasPeriodo)
    TextView vtotalVendasPeriodo;

    @BindView(R.id.vmedioVendasPeriodo)
    TextView vmedioVendasPeriodo;

    @BindView(R.id.qtotalVendasPeriodo)
    TextView qtotalVendasPeriodo;

    @BindView(R.id.vtotalDevolucaoPeriodo)
    TextView vtotalDevolucaoPeriodo;

    @BindView(R.id.lblDevolucoesPeriodo)
    TextView lblDevolucoesPeriodo;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navView)
    NavigationView navigationView;

    @BindView(R.id.lytGlobalLoading)
    View lytGlobalLoading;

    @BindView(R.id.tvGlobalLoadingTitle)
    TextView tvGlobalLoadingTitle;

    //@BindView(R.id.lyt5)
    //LinearLayout lyt5;

    //@BindView(R.id.lyt6)
    //LinearLayout lyt6;

    //@BindView(R.id.progressBar3)
    //ProgressBar progressBar3;

    //@BindView(R.id.progressTitle3)
    //TextView progressTitle3;


    //@BindView(R.id.lyt7)
    //LinearLayout lyt7;

    //@BindView(R.id.lyt8)
    //LinearLayout lyt8;

    //@BindView(R.id.progressBar4)
    //ProgressBar progressBar4;

   // @BindView(R.id.progressTitle4)
    //TextView progressTitle4;



    //@BindView(R.id.recyclerView)
    //RecyclerView recyclerView;

    @BindView(R.id.btnRefreshSemanal)
    ImageView btnRefreshSemanal;

    @BindView(R.id.btnRefreshMensal)
    ImageView btnRefreshMensal;

    @BindView(R.id.btnRefreshGrupo)
    ImageView btnRefreshGrupo;

    @BindView(R.id.btnAnaliseRankingGrupo)
    ImageView btnAnaliseRankingGrupo;

    @BindView(R.id.btnRefreshRanking)
    ImageView btnRefreshRanking;

    @BindView(R.id.btnAnaliseRanking)
    ImageView btnAnaliseRanking;

    @BindView(R.id.cardGrafico1)
    androidx.cardview.widget.CardView cardGrafico1;

    @BindView(R.id.cardGrafico2)
    androidx.cardview.widget.CardView cardGrafico2;

    @BindView(R.id.cardGrafico3)
    androidx.cardview.widget.CardView cardGrafico3;

    @BindView(R.id.cardGrafico4)
    androidx.cardview.widget.CardView cardGrafico4;

    @BindView(R.id.tvDataPesquisa)
    TextView tvDataPesquisa;

    @BindView(R.id.scrollViewFaturamento)
    android.widget.ScrollView scrollViewFaturamento;

    private Context context;
    private DBHelper db = new DBHelper(this);
    PackageInfo pInfo = null;
    protected Typeface tfLight;
    private  Legend l;
    private Configuracao configuracao;
    private int activeRequests = 0;
    private String totalGeralGrupo = "R$ 0,00";
    private List<ParticipacaoFaturamento> listaGraficoPizza = new ArrayList<>();
    private long lastTapTime = 0;
    private int idUsuarioSelecionado = 0;
    private List<ParticipacaoFaturamento> listaRankingAgregada = new ArrayList<>();
    private List<ParticipacaoFaturamento> listaGrupoAgregada = new ArrayList<>();
    private List<ParticipacaoFaturamento> listaRankingOriginal = new ArrayList<>();

    private List<ParticipacaoFaturamento> listaGrupoOriginal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_grafico_faturamento_drawer);
        ButterKnife.bind(this);

        // Força a barra de status e navegação a terem a mesma cor da Toolbar
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        // Configura para que os ícones da barra de status sejam brancos (fundo escuro)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            int flags = decor.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // Remove flag de ícones pretos
            decor.setSystemUiVisibility(flags);
        }

        View layoutMain = findViewById(R.id.layoutMain);
        if (layoutMain != null) {
            ViewCompat.setOnApplyWindowInsetsListener(layoutMain, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Ajuste para o NavigationView (Drawer) não sobrescrever a parte superior e inferior
        if (navigationView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(navigationView, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, insets.top, 0, insets.bottom);
                return windowInsets;
            });
        }

        context = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> {
                onBackPressed();
        });

        int idUsuarioFinal = Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario());
        String nomeUsuarioFinal = UsuarioHelper.getUsuario().getNome_usuario();

        if (getIntent().hasExtra("idUsuarioSelecionado")) {
            idUsuarioFinal = getIntent().getIntExtra("idUsuarioSelecionado", idUsuarioFinal);
            nomeUsuarioFinal = getIntent().getStringExtra("nomeUsuarioSelecionado");
        }

        getSupportActionBar().setTitle(nomeUsuarioFinal);

        // Configura o Toggle (Ícone de abrir/fechar)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Chame o método para carregar os usuários no menu com Checkboxes
        getConfiguracao();
        configurarDrawerFiltro();


        //toolbar.setOnClickListener(v -> onBackPressed());
        //toolbar.setSubtitle("Periodo dos 6 ultimos meses.");
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        flipCard(context,  lyt2,  lyt1, progressBar1 ,1 );
        flipCard(context,  lyt4,  lyt3, progressBar2, 2 );
        flipCard(context,  lyt6,  lyt5, progressBar3, 1 );
        flipCard(context,  lyt11,  lyt10, progressBar10, 2 );


        //flipCard(context,  lyt6,  lyt5, progressBar3, 1 );
        //flipCard(context,  lyt8,  lyt7, progressBar4, 2 );
        barChart1.setNoDataText("Aguarde...");
        barChart1.setNoDataTextColor(Color.BLACK);
        //barChart2.setNoDataText("Aguarde...");
        //barChart2.setNoDataTextColor(Color.BLACK);
        barChart3.setNoDataText("Aguarde...");
        barChart3.setNoDataTextColor(Color.BLACK);

        pieChart.setNoDataText("Aguarde");
        pieChart.setNoDataTextColor(Color.BLACK);


        horChart.setNoDataText("Aguarde");
        horChart.setNoDataTextColor(Color.BLACK);
        
        carregarTodosOsGraficos(idUsuarioFinal);
        animarEntrada();
    }

    private void animarEntrada() {
        androidx.cardview.widget.CardView[] cards = {cardGrafico1, cardGrafico2, cardGrafico3, cardGrafico4};
        long delay = 100;
        for (androidx.cardview.widget.CardView card : cards) {
            if (card != null) {
                card.setAlpha(0f);
                card.setTranslationY(40f);
                card.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(450)
                        .setStartDelay(delay)
                        .start();
                delay += 150;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((Activity)context).finish();
        System.gc();
    }
    private void configurarDrawerFiltro() {
        View drawerContent = getLayoutInflater().inflate(R.layout.layout_drawer_usuarios, navigationView, false);
        navigationView.addView(drawerContent);

        // Oculta o botão de aplicar, já que o filtro será por clique direto

        RecyclerView rvUsuarios = drawerContent.findViewById(R.id.rvUsuariosDrawer);

        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        List<Usuario> lista = new ArrayList<>();
        /*
        if ( !UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            Usuario usuario = new Usuario();
            usuario.setId_quando_vendedor("999");
            usuario.setId_usuario("999");
            usuario.setNome_usuario("TODOS");
            lista.add(usuario);
        }
*/
        List<Usuario> listaUsuarios = usuarioDAO.getLista( UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V") ?
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario() :
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND (NOME_USUARIO <> NULL OR NOME_USUARIO <> '') AND USUARIO_TIPO <> 'D' ORDER BY NOME_USUARIO");
        for (Usuario usuario1 : listaUsuarios) {
            lista.add(usuario1);
        }
        UsuarioDrawerAdapter adapter = new UsuarioDrawerAdapter(lista, user -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(user.getNome_usuario());
            }
            this.idUsuarioSelecionado = Integer.parseInt(user.getId_usuario());
            carregarTodosOsGraficos(idUsuarioSelecionado);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        
        rvUsuarios.setLayoutManager(new LinearLayoutManager(this));
        rvUsuarios.setAdapter(adapter);
    }

    private void carregarTodosOsGraficos(int idUsuario) {
        this.idUsuarioSelecionado = idUsuario;
        showLoading("Consolidando indicadores...");
        
        // Atualiza a data e hora do processamento
        if (tvDataPesquisa != null) {
            String dataHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(new java.util.Date());
            tvDataPesquisa.setText("Processado em: " + dataHora);
        }

        // --- NOVA LÓGICA DE PERÍODO (Baseada na ActivityPainelGraficoMetas) ---
        java.util.Calendar cal = java.util.Calendar.getInstance();
        String dataInicio = String.format(java.util.Locale.US, "%04d-%02d-01", cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH) + 1);

         //getEvolucaoVendas(idUsuario); // AJUSTE: Cards agora alimentados via getEvolucaoVendasMes
        
        /* AJUSTE ANTERIOR (MANTIDO PARA REVERSÃO)
        getEvolucaoVendas(idUsuario, 0);  //Mes
        getEvolucaoVendas(idUsuario, 2);  //Semana
        */
        getEvolucaoVendasMes(idUsuario, 0, dataInicio); // Nova chamada com abatimento de devoluções
        getEvolucaoVendasSemana(idUsuario, idUsuario, dataInicio); // Nova chamada semanal
        
        getEvolucaoVendasGrupo(idUsuario);
        getEvolucaoVendasCliente(idUsuario);
    }
    private void getEvolucaoVendasMes(int idUsuario, int idx, String dataInicio) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        getConfiguracao();

        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            checkRequestFinished();
            flipCard(context, lyt3, lyt4, progressBar2, 1);
            barChart1.setNoDataText("Configuração de API inválida.");
            barChart1.invalidate();
            return;
        }

        api.getSomaTotalRealizadoMesPeriodo(idUsuario, dataInicio, "6", cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<MesAnoMeta> listaOriginal = response.body();
                    Map<String, MesAnoMeta> mapaMeses = new java.util.LinkedHashMap<>();
                    Map<String, Double> valoresMeses = new java.util.HashMap<>();

                    double sumSaida = 0;
                    double sumEntrada = 0;
                    int countSaida = 0;
                    int countEntrada = 0;

                    for (MesAnoMeta mam : listaOriginal) {
                        if (mam == null) continue;

                        String chave = (mam.getNome_mes() != null ? mam.getNome_mes().toUpperCase().trim() : "SEM_MES");

                        // Parse robusto de valor
                        double valorNum = 0;
                        String vStr = mam.getVenda_liquida();
                        if (vStr != null && !vStr.isEmpty()) {
                            try {
                                String v = vStr.trim();
                                if (v.contains(",")) {
                                    valorNum = Double.parseDouble(v.replace(".", "").replace(",", "."));
                                } else {
                                    valorNum = Double.parseDouble(v);
                                }
                            } catch (Exception e) { valorNum = 0; }
                        }

                        // Parse robusto de quantidade
                        int qtde = 0;
                        String qStr = mam.getQtde_pedidos();
                        if (qStr != null && !qStr.isEmpty()) {
                            try {
                                qtde = (int) Double.parseDouble(qStr.trim());
                            } catch (Exception e) { qtde = 0; }
                        }

                        boolean isDevolucao = mam.getE_entrada_s_saida() != null && mam.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                        boolean relComercialSim = mam.getFigura_rel_comercial() != null && mam.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

                        /* CÓDIGO ANTERIOR:
                        if (isDevolucao) {
                            sumEntrada += Math.abs(valorNum);
                            countEntrada += qtde;
                        } else {
                            sumSaida += Math.abs(valorNum);
                            countSaida += qtde;
                        }

                        double acumulado = valoresMeses.containsKey(chave) ? valoresMeses.get(chave) : 0.0;
                        double novoSaldo = isDevolucao ? (acumulado - Math.abs(valorNum)) : (acumulado + Math.abs(valorNum));
                        valoresMeses.put(chave, novoSaldo);
                        */

                        double acumulado = valoresMeses.containsKey(chave) ? valoresMeses.get(chave) : 0.0;

                        if (isDevolucao) {
                            sumEntrada += Math.abs(valorNum);
                            countEntrada += qtde;

                            // Abate do total do mês Apenas se a relação comercial for 'S'
                            if (relComercialSim) {
                                valoresMeses.put(chave, acumulado - Math.abs(valorNum));
                            } else {
                                valoresMeses.put(chave, acumulado);
                            }
                        } else {
                            sumSaida += Math.abs(valorNum);
                            countSaida += qtde;
                            valoresMeses.put(chave, acumulado + Math.abs(valorNum));
                        }

                        if (!mapaMeses.containsKey(chave)) {
                            mapaMeses.put(chave, mam);
                        }
                    }

                    // --- ATUALIZAÇÃO DOS CARDS KPIs (Totalizadores) ---
                    /* CÓDIGO ANTERIOR:
                    double valorFinalPeriodo = sumSaida - sumEntrada;
                    */
                    double valorFinalPeriodo = 0;
                    for (double v : valoresMeses.values()) {
                        valorFinalPeriodo += v;
                    }
                    if (vtotalVendasPeriodo != null) vtotalVendasPeriodo.setText(MascaraUtil.mascaraRealSimboloD(valorFinalPeriodo));

                    double ticketMedio = 0;
                    if (countSaida > 0) {
                        ticketMedio = valorFinalPeriodo / countSaida;
                    }
                    if (vmedioVendasPeriodo != null) vmedioVendasPeriodo.setText(MascaraUtil.mascaraRealSimboloD(ticketMedio));
                    if (qtotalVendasPeriodo != null) qtotalVendasPeriodo.setText(String.valueOf(countSaida));

                    if (lblDevolucoesPeriodo != null) lblDevolucoesPeriodo.setText("DEVOLUÇÕES (" + countEntrada + ")");
                    if (vtotalDevolucaoPeriodo != null) vtotalDevolucaoPeriodo.setText(MascaraUtil.mascaraRealSimboloD(sumEntrada));

                    List<MesAnoMeta> listaFinal = new ArrayList<>();
                    for (String chave : mapaMeses.keySet()) {
                        MesAnoMeta mam = mapaMeses.get(chave);
                        double totalLiquido = valoresMeses.get(chave);
                        // IMPORTANTE: Salva com vírgula para enganar o cleanMoney e evitar divisão por 100
                        mam.setVenda_liquida(String.format(java.util.Locale.US, "%.2f", totalLiquido).replace(".", ","));
                        listaFinal.add(mam);
                    }
                    
                    // --- ATUALIZAÇÃO DO GRÁFICO ---
                    checkRequestFinished();
                    if (!listaFinal.isEmpty()) {
                        createbarChart1MesAnoMeta(listaFinal);
                    } else {
                        flipCard(context, lyt3, lyt4, progressBar2, 1);
                        barChart1.setNoDataText("Sem dados mensais.");
                        barChart1.invalidate();
                    }
                    //carregarMetasDoFirebase2(true, "M", 2026, 13, listaFinal);
                } else {
                    checkRequestFinished();
                    flipCard(context, lyt3, lyt4, progressBar2, 1);
                    //barChart2.setNoDataText("Sem dados mensais.");
                    //barChart2.setData(null);
                    //barChart2.invalidate();
                }
            }
            @Override public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) {
                checkRequestFinished();
                flipCard(context, lyt3, lyt4, progressBar2, 1);
                //barChart2.setNoDataText("Falha na conexão.");
                //barChart2.invalidate();
            }
        });
    }

    private void getEvolucaoVendasSemana(int idUsuario, int idVendedor, String dataInicio) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        getConfiguracao();
        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            checkRequestFinished();
            flipCard(context, lyt1, lyt2, progressBar1, 1);
            barChart1.setNoDataText("Configuração de API inválida.");
            barChart1.invalidate();
            return;
        }
        api.getSomaTotalRealizadoV2SemanaPeriodo(idUsuario, dataInicio, "6", cabecalho).enqueue(new Callback<List<MesSemana>>() {
            @Override
            public void onResponse(Call<List<MesSemana>> call, Response<List<MesSemana>> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<MesSemana> listaOriginal = response.body();
                    Map<String, MesSemana> mapaSemanas = new java.util.LinkedHashMap<>();
                    Map<String, Double> valoresSemanas = new java.util.HashMap<>();

                    for (MesSemana ms : listaOriginal) {
                        String ini = (ms.getIni_semama() != null) ? ms.getIni_semama().trim() : "";
                        if (ini.length() > 10) ini = ini.substring(0, 10);
                        String nomeSem = (ms.getNome_semana() != null) ? ms.getNome_semana().trim().toUpperCase() : "";
                        String chave = !ini.isEmpty() ? ini : (!nomeSem.isEmpty() ? nomeSem : "SEM");
                        double valorNum = 0;
                        try {
                            String v = ms.getVenda_liquida().trim();
                            if (v.contains(",")) {
                                valorNum = Double.parseDouble(v.replace(".", "").replace(",", "."));
                            } else {
                                valorNum = Double.parseDouble(v);
                            }
                        } catch (Exception e) { valorNum = 0; }
                        boolean isDevolucao = ms.getE_entrada_s_saida() != null && ms.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                        boolean relComercialSim = ms.getFigura_rel_comercial() != null && ms.getFigura_rel_comercial().trim().equalsIgnoreCase("S");
                        
                        double acumulado = valoresSemanas.containsKey(chave) ? valoresSemanas.get(chave) : 0.0;

                        /* CÓDIGO ANTERIOR:
                        double novoSaldo = isDevolucao ? (acumulado - Math.abs(valorNum)) : (acumulado + Math.abs(valorNum));
                        valoresSemanas.put(chave, novoSaldo);
                        */

                        if (isDevolucao) {
                            // Abate do saldo da semana Apenas se a relação comercial for 'S'
                            if (relComercialSim) {
                                valoresSemanas.put(chave, acumulado - Math.abs(valorNum));
                            } else {
                                valoresSemanas.put(chave, acumulado);
                            }
                        } else {
                            valoresSemanas.put(chave, acumulado + Math.abs(valorNum));
                        }
                        if (!mapaSemanas.containsKey(chave)) {
                            mapaSemanas.put(chave, ms);
                        }
                    }
                    List<MesSemana> listaFinal = new ArrayList<>();
                    for (String chave : mapaSemanas.keySet()) {
                        MesSemana ms = mapaSemanas.get(chave);
                        double totalLiquido = valoresSemanas.get(chave);
                        ms.setVenda_liquida(String.format(java.util.Locale.US, "%.2f", totalLiquido).replace(".", ","));
                        listaFinal.add(ms);
                    }
                    checkRequestFinished();
                    if (!listaFinal.isEmpty()) {
                        createbarChart3MesSemana(listaFinal);
                    } else {
                        flipCard(context, lyt1, lyt2, progressBar1, 1);
                        barChart3.setNoDataText("Sem dados semanais.");
                        barChart3.invalidate();
                    }
                } else {
                    checkRequestFinished();
                    flipCard(context, lyt1, lyt2, progressBar1, 1);
                }
            }
            @Override public void onFailure(Call<List<MesSemana>> call, Throwable t) {
                checkRequestFinished();
                flipCard(context, lyt1, lyt2, progressBar1, 1);
            }
        });
    }

    private void getEvolucaoVendasGrupo(int idUsuario) {

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
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
        if (apiRotas == null) {
            checkRequestFinished();
            horChart.setNoDataText("Configuração de API inválida.");
            horChart.invalidate();
            return;
        }
        Call<List<ParticipacaoFaturamento>> call;
        if (!configuracao.getId().isEmpty()) {
            call = apiRotas.getSomaTotalVendaPorGrupo(idUsuario,"-", "-", cabecalho);
            call.enqueue(new Callback<List<ParticipacaoFaturamento>>() {
                @Override
                public void onResponse(Call<List<ParticipacaoFaturamento>> call, Response<List<ParticipacaoFaturamento>> response) {
                    checkRequestFinished();
                    if (response.code() == 200) {
                        listaGraficoPizza = response.body();
                        listaGrupoOriginal = listaGraficoPizza != null ? new ArrayList<>(listaGraficoPizza) : new ArrayList<>();
                        if (listaGraficoPizza != null && !listaGraficoPizza.isEmpty()) {
                            setData(listaGraficoPizza);
                        } else {
                            flipCard(context, lyt5, lyt6, progressBar3, 1);
                            pieChart.setNoDataText("Sem dados de faturamento por grupo.");
                            pieChart.setData(null);
                            pieChart.invalidate();
                        }
                    } else {
                        flipCard(context, lyt5, lyt6, progressBar3, 1);
                        pieChart.setNoDataText("Erro ao obter dados do relatório.");
                        pieChart.invalidate();
                    }
                }

                @Override
                public void onFailure(Call<List<ParticipacaoFaturamento>> call, Throwable t) {
                    checkRequestFinished();
                    flipCard(context, lyt5, lyt6, progressBar3, 1);
                    pieChart.setNoDataText("Falha na conexão.");
                    pieChart.invalidate();
                }
            });

        } else {
            checkRequestFinished();
            pieChart.setNoDataText("");
            Toast.makeText(context, "Parametros de acesso ao servidor não definido!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEvolucaoVendasCliente(int idUsuario) {
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
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
        if (apiRotas == null) {
            checkRequestFinished();
            horChart.setNoDataText("Configuração de API inválida.");
            horChart.invalidate();
            return;
        }
        Call<List<ParticipacaoFaturamento>> call;
        if (!configuracao.getId().isEmpty()) {
            call = apiRotas.getSomaTotalVendaPorCliente(idUsuario,"-", "-", cabecalho);
            call.enqueue(new Callback<List<ParticipacaoFaturamento>>() {
                @Override
                public void onResponse(Call<List<ParticipacaoFaturamento>> call, Response<List<ParticipacaoFaturamento>> response) {
                    checkRequestFinished();
                    if (response.code() == 200) {
                        List<ParticipacaoFaturamento> lista = response.body();
                        listaRankingOriginal = lista != null ? new ArrayList<>(lista) : new ArrayList<>();
                        if (lista != null && !lista.isEmpty()) {
                            createHorChart4(lista);
                        } else {
                            flipCard(context, lyt10, lyt11, progressBar10, 1);
                            horChart.setNoDataText("Sem dados para o ranking.");
                            horChart.setData(null);
                            horChart.invalidate();
                        }
                    } else {
                        flipCard(context, lyt10, lyt11, progressBar10, 1);
                        horChart.setNoDataText("Erro ao obter dados do relatório.");
                        horChart.invalidate();
                    }
                }

                @Override
                public void onFailure(Call<List<ParticipacaoFaturamento>> call, Throwable t) {
                    checkRequestFinished();
                    flipCard(context, lyt10, lyt11, progressBar10, 1);
                    horChart.setNoDataText("Falha na conexão.");
                    horChart.invalidate();
                }
            });

        } else {
            checkRequestFinished();
            horChart.setNoDataText("");
            Toast.makeText(context, "Parametros de acesso ao servidor não definido!", Toast.LENGTH_SHORT).show();
        }
    }


    @butterknife.OnClick({R.id.btnRefreshSemanal, R.id.btnRefreshMensal, R.id.btnRefreshGrupo, R.id.btnRefreshRanking, R.id.btnAnaliseRanking, R.id.btnAnaliseRankingGrupo})
    public void onRefreshClick(View view) {
        int id = view.getId();
        if (id == R.id.btnAnaliseRanking) {
            exibirDialogAnaliseRanking();
            return;
        }
        if (id == R.id.btnAnaliseRankingGrupo) {
            exibirDialogAnaliseRankingGrupo();
            return;
        }
        activeRequests = 1; // Define que estamos aguardando apenas 1 retorno para o card
        if (id == R.id.btnRefreshSemanal) {
            flipCard(context, lyt2, lyt1, progressBar1, 1);
            // AJUSTE ANTERIOR: getEvolucaoVendas(idUsuarioSelecionado, 2);
            java.util.Calendar cal = java.util.Calendar.getInstance();
            String dataInicio = String.format(java.util.Locale.US, "%04d-%02d-01", cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH) + 1);
            getEvolucaoVendasSemana(idUsuarioSelecionado, idUsuarioSelecionado, dataInicio);
        } else if (id == R.id.btnRefreshMensal) {
            flipCard(context, lyt4, lyt3, progressBar2, 1);
            // AJUSTE ANTERIOR: getEvolucaoVendas(idUsuarioSelecionado, 0);
            java.util.Calendar cal = java.util.Calendar.getInstance();
            String dataInicio = String.format(java.util.Locale.US, "%04d-%02d-01", cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH) + 1);
            getEvolucaoVendasMes(idUsuarioSelecionado, 0, dataInicio);
        } else if (id == R.id.btnRefreshGrupo) {
            flipCard(context, lyt6, lyt5, progressBar3, 1);
            getEvolucaoVendasGrupo(idUsuarioSelecionado);
        } else if (id == R.id.btnRefreshRanking) {
            flipCard(context, lyt11, lyt10, progressBar10, 1);
            getEvolucaoVendasCliente(idUsuarioSelecionado);
        }
    }

    private void showLoading(String title) {
        activeRequests = 4; // Total de chamadas disparadas em carregarTodosOsGraficos (Mes, Semana, Grupo, Cliente)
        tvGlobalLoadingTitle.setText(title);
        lytGlobalLoading.setVisibility(View.VISIBLE);
    }

    private void checkRequestFinished() {
        activeRequests--;
        if (activeRequests <= 0) {
            lytGlobalLoading.setVisibility(View.GONE);
        }
    }

    private void popularDrawerComUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        List<Usuario> listaUsuarios = usuarioDAO.getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S'");

        Menu menu = navigationView.getMenu();
        menu.clear(); // Limpa itens estáticos se houver

        // Adiciona um sub-menu ou itens diretos
        SubMenu subMenu = menu.addSubMenu("Representantes");
        

        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario user = listaUsuarios.get(i);
            // O ID do item de menu será o índice ou ID do usuário para identificar no clique
            subMenu.add(Menu.NONE, Integer.parseInt(user.getId_usuario()), Menu.NONE, user.getNome_usuario())
                    .setIcon(R.drawable.ic_person_color_white_12_12); // Use um ícone apropriado
        }
    }
    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    private void getEvolucaoVendas(int idUsuario, int idxItemMenu) {
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        Map<String, String> cabecalho = new HashMap<>();
        if ( UsuarioHelper.getUsuario().getToken() == null) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        }
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<EvolucaoFaturamento>> call = null;
        if ( idxItemMenu == 0)
            call = apiRotas.getTotalVendaMes(idUsuario, cabecalho);
        if ( idxItemMenu == 1)
            call = apiRotas.getTotalVendaMesMes(idUsuario, cabecalho);
        if ( idxItemMenu == 2)
            call = apiRotas.getTotalVendaSemanaSemana(idUsuario, cabecalho);
        if ( idxItemMenu == 3)
            call = apiRotas.getTotalVendaMesMesGrupo(idUsuario, cabecalho);
        if (call != null)
            call.enqueue(new Callback<List<EvolucaoFaturamento>>() {
                @Override
                public void onResponse(Call<List<EvolucaoFaturamento>> call, Response<List<EvolucaoFaturamento>> response) {
                    checkRequestFinished();
                    if (response.code() == 200) {
                        List<EvolucaoFaturamento> lista = response.body();
                        if ( idxItemMenu == 0) {
                            createbarChart1(lista);
                        }
                        if ( idxItemMenu == 2)
                            createbarChart3(lista);
                    } else {
                        if ( idxItemMenu == 0) {
                            flipCard(context, lyt1, lyt2, progressBar1, 1);
                            barChart1.setNoDataText("Não foi possível sincronizar com o servidor.");
                            barChart1.setData(null);
                            barChart1.invalidate();
                        }
                        if ( idxItemMenu == 1) {
                            // lyt4? check layouts
                        }
                        if ( idxItemMenu == 2) {
                            flipCard(context, lyt3, lyt4, progressBar2, 1);
                            barChart3.setNoDataText("Não foi possível sincronizar com o servidor.");
                            barChart3.setData(null);
                            barChart3.invalidate();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<EvolucaoFaturamento>> call, Throwable t) {
                    checkRequestFinished();
                    if ( idxItemMenu == 0) {
                        flipCard(context, lyt1, lyt2, progressBar1, 1);
                        barChart1.setNoDataText("Falha na conexão.");
                        barChart1.setData(null);
                        barChart1.invalidate();
                    }
                    if ( idxItemMenu == 2) {
                        flipCard(context, lyt3, lyt4, progressBar2, 1);
                        barChart3.setNoDataText("Falha na conexão.");
                        barChart3.setData(null);
                        barChart3.invalidate();
                    }
                }
            });
        else {
            checkRequestFinished();
            Toast.makeText(context, "Falha ao obter os parametros", Toast.LENGTH_LONG).show();
        }

    }
    /* AJUSTE ANTERIOR: O faturamento total agora é consolidado via getEvolucaoVendasMes
    private void getEvolucaoVendas(int idUsuario) {
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
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
        if (apiRotas == null) {
            checkRequestFinished();
            horChart.setNoDataText("Configuração de API inválida.");
            horChart.invalidate();
            return;
        }
        Call<List<ParticipacaoFaturamento>> call;
        if (!configuracao.getId().isEmpty()) {
            call = apiRotas.getSomaTotalVendaPeriodo(idUsuario,"-", "-", cabecalho);
            call.enqueue(new Callback<List<ParticipacaoFaturamento>>() {
                @Override
                public void onResponse(Call<List<ParticipacaoFaturamento>> call, Response<List<ParticipacaoFaturamento>> response) {
                    checkRequestFinished();
                    if (response.code() == 200) {
                        List<ParticipacaoFaturamento> lista = response.body();
                        assert lista != null;
                        if (!lista.isEmpty()) {
                            ParticipacaoFaturamento pf = lista.get(0);

                            double valorLiquido = Double.parseDouble(pf.getValor_liquido() == null || pf.getValor_liquido().isEmpty() ? "0.00" : pf.getValor_liquido());
                            double valorLiquidoDev = Double.parseDouble(pf.getValor_liquido_dev() == null || pf.getValor_liquido_dev().isEmpty() ? "0.00" : pf.getValor_liquido_dev());
                            
                            // Se houver devolução, subtrai do total líquido conforme solicitado
                            double valorFinal = valorLiquido;
                            if (valorLiquidoDev > 0) {
                                valorFinal = valorLiquido - valorLiquidoDev;
                            }

                            vtotalVendasPeriodo.setText(MascaraUtil.mascaraRealSimboloD(valorFinal));

                            double ticketMedio = 0;
                            try {
                                int qtdVendas = Integer.parseInt(pf.getQtde_vendas() == null || pf.getQtde_vendas().isEmpty() ? "0" : pf.getQtde_vendas());
                                if (qtdVendas > 0) {
                                    ticketMedio = valorFinal / qtdVendas;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            vmedioVendasPeriodo.setText(MascaraUtil.mascaraRealSimboloD(ticketMedio));
                            qtotalVendasPeriodo.setText(pf.getQtde_vendas());

                            // Atualiza o novo card de devoluções com a quantidade no título
                            lblDevolucoesPeriodo.setText("DEVOLUÇÕES (" + pf.getQtde_vendas_dev() + ")");
                            vtotalDevolucaoPeriodo.setText(MascaraUtil.mascaraRealSimboloD(valorLiquidoDev));
                        }

                    } else {
                        Toast.makeText(context, "Falha ao obter a soma total de vendas no periodo", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<ParticipacaoFaturamento>> call, Throwable t) {
                    checkRequestFinished();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            checkRequestFinished();
            Toast.makeText(context, "Parametros de acesso ao servidor não definido!", Toast.LENGTH_SHORT).show();
        }
    }
    */

    private void createbarChart1MesAnoMeta(List<MesAnoMeta> lista) {
        float textSize = 8;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                textSize = 10;
                break;
        }
        
        flipCard(context, lyt3, lyt4, progressBar2, 0);
        barChart1.setDrawBarShadow(false);
        barChart1.getDescription().setEnabled(false);
        barChart1.setPinchZoom(true);
        barChart1.setDrawGridBackground(false); 
        barChart1.setNoDataText("");
        
        XAxis xAxis = barChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        
        YAxis leftAxis = barChart1.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceBottom(1.5f);
        barChart1.getAxisRight().setEnabled(false);

        String[] labels = new String[lista.size()];
        ArrayList<BarEntry> entries = new ArrayList<>();
        
        // Ordena por data (Nome do mês na verdade contém YYYY-MM na chave gerada ou similar)
        // No getEvolucaoVendasMes a lista final vem na ordem da API, vamos manter essa ordem
        for (int i = 0; i < lista.size(); i++) {
            MesAnoMeta item = lista.get(i);
            labels[i] = setMes(item.getNome_mes()); // setMes trata YYYY-MM
            
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
        
        barChart1.getLegend().setEnabled(false);
        barChart1.setData(data);

        barChart1.setOnClickListener(v -> {
            GraficoFaturamentoHelper.setListaMesAnoMeta(lista); // NOVO: Salva no Helper para evitar TransactionTooLarge
            Intent intent = new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class);
            intent.putExtra("tipoRelatorio", "M");
            if (demoOverlayView != null) {
                intent.putExtra("isDemo", true);
            }
            startActivity(intent);
        });

        barChart1.notifyDataSetChanged();
        barChart1.invalidate();
        barChart1.animateXY(1500, 1500);
    }

    private void createbarChart1(List<EvolucaoFaturamento> lista) {
        float textSize = 8;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                textSize = 10;
                break;
            default:
        }
        flipCard(context,  lyt3,  lyt4, progressBar2, 0 );
        barChart1.setDrawBarShadow(false);
        barChart1.getDescription().setEnabled(false);
        barChart1.setPinchZoom(true);
        barChart1.setDrawGridBackground(false); //Cor de fundo
        barChart1.setNoDataText("");
        XAxis xAxis = barChart1.getXAxis();
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        YAxis leftAxis = barChart1.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(textSize);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart1.getAxisRight();
        rigthAxis.setEnabled(false);
        leftAxis.setSpaceBottom(1.5f);
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
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(textSize);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new ActivityPainelGraficoFaturamento.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        float barWidth = 0.55f; // x4 DataSet
        data.setBarWidth(barWidth);
        barChart1.getLegend().setEnabled(false);
        barChart1.getLegend().setEnabled(false);
        barChart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraficoFaturamentoHelper.setListaEvolucaoFaturamento(lista); // NOVO: Salva no Helper para evitar TransactionTooLarge
                startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class)
                        .putExtra("tipoRelatorio", "M"));
                        // .putExtra("lista", (Serializable) lista));
            }
        });
        leftAxis.setDrawLabels(false);
        barChart1.setData(data);
        barChart1.notifyDataSetChanged();
        barChart1.invalidate();
        barChart1.animateXY(1500, 1500);
    }

    /*
    private void createbarChart2(List<EvolucaoFaturamento> lista) {
        flipCard(context,  lyt5,  lyt6, null, 0 );
        barChart2.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart2.getDescription().setEnabled(false);
        barChart2.setPinchZoom(true);
        barChart2.setDrawGridBackground(false); //Cor de fundo
        barChart2.setNoDataText("");
        //barChart.setDrawingCacheBackgroundColor(Color.parseColor("#1031595F"));
        XAxis xAxis = barChart2.getXAxis();
        xAxis.setCenterAxisLabels(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart2.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart2.getAxisRight();
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
        //float groupSpace = 0.2f; // Space between groups
        //float barSpace = 0.01f; // Space between individual bars within a group
        //float barWidth = 0.46f;
        //barWidth = (1 - groupSpace) / 2 - (barSpace );


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


        barChart2.getLegend().setEnabled(false);
        barChart2.getLegend().setEnabled(false);



        // restrict the x-axis range
        barChart2.getXAxis().setAxisMinimum(0);
        barChart2.getXAxis().setAxisMaximum(6);

        //barChart.setDragEnabled(true);
        //barChart.groupBars(0, groupSpace, barSpace);
        //barChart.setVisibleXRangeMinimum(7f);
        barChart2.setScaleEnabled(true);
        barChart2.setDragEnabled(true);

        data.setValueTextSize(8f);
        data.getDataSetByIndex(0);

        data.setValueFormatter(new ActivityPainelGraficoFaturamento.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        barChart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "MM"));
                startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class)
                        .putExtra("tipoRelatorio", "MM")
                        .putExtra("lista", (Serializable) lista));
            }
        });
        //xAxis.setAxisMaximum(data.getGroupWidth(groupSpace, barSpace) * lista.size() );
        barChart2.getLegend().setEnabled(false);
        barChart2.setDragEnabled(true);
        leftAxis.setDrawLabels(false);
        barChart2.setData(data);
        barChart2.groupBars(0, groupSpace, barSpace);
        barChart2.notifyDataSetChanged();
        barChart2.invalidate();
        barChart2.animateXY(1500, 1500);
    }
*/
    private void createbarChart3MesSemana(List<MesSemana> lista) {
        float textSize = 8;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                textSize = 10;
                break;
        }
        
        flipCard(context, lyt1, lyt2, null, 0);
        barChart3.setDrawBarShadow(false);
        barChart3.getDescription().setEnabled(false);
        barChart3.setPinchZoom(true);
        barChart3.setDrawGridBackground(false); 
        barChart3.setNoDataText("");
        
        XAxis xAxis = barChart3.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        
        YAxis leftAxis = barChart3.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceBottom(1.5f);
        barChart3.getAxisRight().setEnabled(false);

        // 1. Agrupa semanas por Mês para manter o visual Empilhado (Stacked)
        Map<String, List<MesSemana>> mapaMeses = new java.util.LinkedHashMap<>();
        int maxSemanas = 0;
        for (MesSemana ms : lista) {
            String data = ms.getIni_semama();
            if (data == null || data.length() < 7) continue;
            String chaveMes = data.substring(0, 7); // YYYY-MM
            
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
        
        // Cores para as semanas (Stacks)
        int[] cores = ColorTemplateApp.SEMANA_COLORS_APP_6; // Padrão 6 semanas
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
        
        barChart3.setData(data);
        barChart3.getLegend().setEnabled(true);
        barChart3.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        barChart3.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        barChart3.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        barChart3.getLegend().setDrawInside(false);
        barChart3.getLegend().setTextSize(textSize - 1);
        
        barChart3.notifyDataSetChanged();
        barChart3.invalidate();
        barChart3.animateXY(1500, 1500);
        
        barChart3.setOnClickListener(v -> {
            GraficoFaturamentoHelper.setListaMesSemana(lista); // NOVO: Salva no Helper
            Intent intent = new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class);
            intent.putExtra("tipoRelatorio", "S");
            if (demoOverlayView != null) {
                intent.putExtra("isDemo", true);
            }
            startActivity(intent);
        });
    }

    private void createbarChart3(List<EvolucaoFaturamento> lista) {
        float textSize = 8;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                textSize = 10;
                break;
            default:
        }
        flipCard(context,  lyt1,  lyt2, null, 0 );//Stacked
        barChart3.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart3.getDescription().setEnabled(false);
        barChart3.setPinchZoom(true);
        barChart3.setDrawGridBackground(false); //Cor de fundo
        barChart3.setDrawingCacheBackgroundColor(Color.TRANSPARENT/*Color.parseColor("#F8F8FF")*/);
        XAxis xAxis = barChart3.getXAxis();
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(textSize);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart3.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart3.getAxisRight();
        rigthAxis.setEnabled(false);
        leftAxis.setSpaceBottom(1.5f);
        String[] labels = new String[(lista.size())];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        int z = lista.size();
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
                        } catch ( ArrayIndexOutOfBoundsException e) {
                            s++;
                            lista1[w] = 0.00f;

                        } catch ( IndexOutOfBoundsException e) {
                            s++;
                            lista1[w] = 0.00f;
                        }
                         w++;
                    }
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
        qtdeSemanas = 0;
        idxSemanas = 0;
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
        float groupSpace = 0.10f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.80f; // x4 DataSet
        BarData data;
        data = new BarData(set1);
        data.setBarWidth(barWidth);
        data.setValueTextSize(textSize);
        barChart3.getLegend().setTextSize(6f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new ActivityPainelGraficoFaturamento.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(value);
            }
        });

        barChart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraficoFaturamentoHelper.setListaEvolucaoFaturamento(lista); // NOVO: Salva no Helper para evitar TransactionTooLarge
                startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class)
                        .putExtra("tipoRelatorio", "S"));
                        // .putExtra("lista", (Serializable) lista));
            }
        });

        leftAxis.setDrawLabels(false);
        barChart3.setData(data);
        barChart3.setFitBars(true);
        barChart3.getLegend().setWordWrapEnabled(true);
        barChart3.setExtraBottomOffset(8f);
        barChart3.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        barChart3.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        barChart3.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        barChart3.getLegend().setDrawInside(false);
        barChart3.getLegend().setTextSize(textSize - 1);
        barChart3.notifyDataSetChanged();
        barChart3.invalidate();
        barChart3.animateXY(1500, 1500);
    }

    private void createHorChart4(List<ParticipacaoFaturamento> lista) {
        flipCard(context, lyt10, lyt11, progressBar10, 0);

        // 1. Reset Total e Configurações de Escala
        horChart.clear();
        horChart.fitScreen(); 
        horChart.setNoDataText("Processando...");
        
        /* CÓDIGO ANTERIOR COMENTADO
        // Filtragem Local: Ignora Entradas "E" apenas para este gráfico
        List<ParticipacaoFaturamento> listaRanking = new ArrayList<>();
        for (ParticipacaoFaturamento p : lista) {
            if (p.getE_entrada_s_saida() == null || !p.getE_entrada_s_saida().equalsIgnoreCase("E")) {
                listaRanking.add(p);
            }
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();
        double valorOutros = 0f;
        double totalGeralClientes = 0f;
        int limiteTop = 5;
        float textSize = 8f;

        // Calcula total geral com segurança usando a lista filtrada
        for (ParticipacaoFaturamento p : listaRanking) {
            try {
                String valStr = p.getValor_liquido();
                if (valStr != null && !valStr.isEmpty()) {
                    totalGeralClientes += Double.parseDouble(valStr);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 10;
            textSize = 10;
        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            limiteTop = 5; // Mostra os 5 maiores e agrupa o resto
            textSize = 8;
        }
        // 2. Processamento da Lista para Ranking (usando listaRanking)
        int count = 0;
        for (int i = 0; i < listaRanking.size(); i++) {
            try {
                String valStr = listaRanking.get(i).getValor_liquido();
                double valor = (valStr != null && !valStr.isEmpty()) ? Double.parseDouble(valStr) : 0;
                
                if (count < limiteTop) {
                    String nome = listaRanking.get(i).getNome();
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
            } else if (listIdx < listaRanking.size()) {
                String valStr = listaRanking.get(listIdx).getValor_liquido();
                valor = (valStr != null && !valStr.isEmpty()) ? Double.parseDouble(valStr) : 0;
                nomeOriginal = listaRanking.get(listIdx).getNome();
            }

            if (nomeOriginal == null) nomeOriginal = "S/N";
            String nomeLimpo = nomeOriginal.replace("#", " ").replace("|", " "); 
            double percentual = totalGeralClientes > 0 ? (valor / totalGeralClientes) * 100 : 0;
            
            entries.add(new BarEntry(i, (float) valor, nomeLimpo + "###" + MascaraUtil.duasCasaDecimal(String.valueOf(percentual))));
        }
        */

        // --- NOVA LÓGICA DE AGREGAÇÃO POR CLIENTE ---
        Map<String, ParticipacaoFaturamento> mapaAgregado = new java.util.LinkedHashMap<>();
        for (ParticipacaoFaturamento pf : lista) {
            String idCli = pf.getId() != null ? pf.getId().trim() : "0";
            double valor = Double.parseDouble(pf.getValor_liquido() == null || pf.getValor_liquido().isEmpty() ? "0" : pf.getValor_liquido());

            boolean isDevolucao = pf.getE_entrada_s_saida() != null && pf.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
            boolean relComercialSim = pf.getFigura_rel_comercial() != null && pf.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

            /* CÓDIGO ANTERIOR:
            if (isDevolucao) {
                valor = -Math.abs(valor);
            }
            */

            if (isDevolucao) {
                // Abate do saldo do cliente Apenas se a relação comercial for 'S'
                if (relComercialSim) {
                    valor = -Math.abs(valor);
                } else {
                    valor = 0;
                }
            } else {
                valor = Math.abs(valor);
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

        this.listaRankingAgregada = new ArrayList<>(listaConsolidada);

        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();
        double valorOutros = 0f;
        double totalGeralClientes = 0f;
        int limiteTop = 5;
        float textSize = 8f;

        // Calcula total geral com segurança usando a lista consolidada
        for (ParticipacaoFaturamento p : listaConsolidada) {
            try {
                totalGeralClientes += Double.parseDouble(p.getValor_liquido());
            } catch (Exception e) { e.printStackTrace(); }
        }

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 10;
            textSize = 10;
        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            limiteTop = 5; // Mostra os 5 maiores e agrupa o resto
            textSize = 8;
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
        dataSet.setColors(limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6_RANK : ColorTemplateApp.PIE_COLORS_11_RANK);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(textSize);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ""; // Mantém vazio aqui para usar apenas o BarLabel customizado
            }

            @Override
            public String getBarLabel(BarEntry barEntry) {
                try {
                    String dataStr = (String) barEntry.getData();
                    if (dataStr != null && dataStr.contains("###")) {
                        String[] parts = dataStr.split("###");
                        String nomeParaExibir = parts[0];
                        // Truncamento seguro
                        if (nomeParaExibir.length() > 20) nomeParaExibir = nomeParaExibir.substring(0, 18) + "..";
                        return nomeParaExibir + " - " + MascaraUtil.mascaraRealSimbolo(barEntry.getY());
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return super.getBarLabel(barEntry);
            }
        });

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);

        horChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "M"));
                GraficoFaturamentoHelper.setListaParticipacaoFaturamento(lista); // NOVO: Salva no Helper para evitar TransactionTooLarge
                startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class)
                        .putExtra("tipoRelatorio", "C"));
                        // .putExtra("lista", (Serializable) lista));
            }
        });

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

        horChart.getAxisLeft().setEnabled(false);
        horChart.getAxisLeft().setAxisMinimum(0f);
        horChart.getAxisRight().setEnabled(false);

        // Offset lateral reduzido (Texto agora alinhado à esquerda pelo renderer)
        horChart.setExtraOffsets(5, 5, 20, 5);
        horChart.setDrawValueAboveBar(true);

        horChart.setFitBars(true);
        horChart.animateY(1500);
        horChart.invalidate();
    }

    private void createPieChart() {
        float textSize = 8f;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                textSize = 10;
                break;
            default:
                //limiteTop = 5;
        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            textSize = 8;
        }
        //flipCard(context,  lyt1,  lyt2, null, 0 );//Stacked
        flipCard(context,  lyt5,  lyt6, progressBar3, 0 );
        pieChart.setNoDataText("");
        pieChart.setNoDataTextColor(Color.BLACK);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        //pieChart.setExtraOffsets(5, 0, 5, 80);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterTextTypeface(tfLight);
        //chart.setCenterText(generateCenterSpannableText());
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(80f);
        pieChart.setCenterTextSize(textSize-1);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        //pieChart.setOnChartValueSelectedListener(this);
        //Tablet  pieChart.setExtraOffsets(20, 10, 110, 10);
        //pieChart.setExtraOffsets(20, 10, 60, 10);
        //int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // No modo paisagem, reduzimos as margens laterais e de topo ao máximo
            pieChart.setExtraOffsets(2, 2, 2, 2);
        } else {
            pieChart.setExtraOffsets(5, 5, 5, 5);
        }
        //pieChart.setExtraOffsets(5, 5, 5, 5);
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateXY(1500, 1500);
        pieChart.setUsePercentValues(false);
        pieChart.getLegend().setEnabled(true);
        pieChart.setOnChartValueSelectedListener(this);

        // Substitua o setOnClickListener por este:
        pieChart.setOnChartGestureListener(this);

    }

    private void setData(List<ParticipacaoFaturamento> lista) {
        /* CÓDIGO ANTERIOR COMENTADO
        createPieChart();
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<LegendEntry> legends = new ArrayList<>();
        double total = 0f;
        double valorOutros = 0f;
        int limiteTop = 5; // Mostra os 5 maiores e agrupa o resto
        float textSize = 8f;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                limiteTop = 10;
                textSize = 10;
                break;
            default:
                //limiteTop = 5;
        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            limiteTop = 5; // Mostra os 5 maiores e agrupa o resto
            textSize = 8;
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
                            limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6[i] : ColorTemplateApp.PIE_COLORS_11[i]
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
            //if ( limiteTop == 5)
               legends.add(new LegendEntry(
                    "OUTROS (" + MascaraUtil.mascaraRealSimboloD(valorOutros) + "%)",
                    Legend.LegendForm.DEFAULT, 8f, 2f, null,
                       limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6[5] : ColorTemplateApp.PIE_COLORS_11[10] // Cor cinza no índice 10
               ));
        }

        totalGeralGrupo = total > 0 ? MascaraUtil.mascaraRealSimboloD(total) : "R$ 0,00";
        pieChart.setCenterText(totalGeralGrupo);
        pieChart.setCenterTextSize(textSize);
        pieChart.setEntryLabelTextSize(textSize -1);

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(8f);
        dataSet.setColors(limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6 : ColorTemplateApp.PIE_COLORS_11);
        dataSet.setDrawValues(false);

        // Remove as linhas de chamada e valores externos
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setValueLineColor(Color.BLACK);
        dataSet.setValueLineWidth(0f);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setDrawEntryLabels(false);

        // Configuração da Legenda ultra compacta para maximizar o gráfico
        Legend l = pieChart.getLegend();
        l.setEnabled(true);
        l.setCustom(legends);
        //int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Em PAISAGEM: Legenda na direita, vertical
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setWordWrapEnabled(true);
            l.setXOffset(2f);
            l.setFormSize(2f);
            l.setYOffset(2f); // Espaço entre o gráfico e a legenda
            pieChart.setExtraOffsets(2, 0, 2, 2);
        } else {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setWordWrapEnabled(true);
            l.setTextSize(textSize);         // Fonte reduzida para ganhar espaço vertical
            l.setXEntrySpace(6f);
            l.setYEntrySpace(1f);        // Espaço entre linhas mínimo
            l.setFormToTextSpace(3f);
            l.setFormSize(5f);           // Ícones menores
            l.setYOffset(8f);
            pieChart.setExtraOffsets(2, 0, 2, 8); // Sobe a legenda levemente
        }
        pieChart.setMinOffset(0f);
        pieChart.highlightValues(null);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
        */

        // --- NOVA LÓGICA DE AGREGAÇÃO POR GRUPO ---
        Map<String, ParticipacaoFaturamento> mapaAgregado = new java.util.LinkedHashMap<>();
        for (ParticipacaoFaturamento pf : lista) {
            String idGrupo = pf.getId() != null ? pf.getId().trim() : "0";
            double valor = Double.parseDouble(pf.getValor_liquido() == null || pf.getValor_liquido().isEmpty() ? "0" : pf.getValor_liquido());

            boolean isDevolucao = pf.getE_entrada_s_saida() != null && pf.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
            boolean relComercialSim = pf.getFigura_rel_comercial() != null && pf.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

            /* CÓDIGO ANTERIOR:
            if (isDevolucao) {
                valor = -Math.abs(valor);
            }
            */

            if (isDevolucao) {
                // Abate do total do grupo Apenas se a relação comercial for 'S'
                if (relComercialSim) {
                    valor = -Math.abs(valor);
                } else {
                    valor = 0;
                }
            } else {
                valor = Math.abs(valor);
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

        this.listaGrupoAgregada = new ArrayList<>(listaConsolidada);

        // Renderização do Gráfico usando a lista consolidada
        createPieChart();
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<LegendEntry> legends = new ArrayList<>();
        double total = 0f;
        double valorOutros = 0f;
        int limiteTop = 5;
        float textSize = 8f;

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 10;
            textSize = 10;
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            limiteTop = 5;
            textSize = 8;
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
                            limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6[i] : ColorTemplateApp.PIE_COLORS_11[i]
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
                    limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6[5] : ColorTemplateApp.PIE_COLORS_11[10]
            ));
        }

        totalGeralGrupo = total > 0 ? MascaraUtil.mascaraRealSimboloD(total) : "R$ 0,00";
        pieChart.setCenterText(totalGeralGrupo);
        pieChart.setCenterTextSize(textSize);
        pieChart.setEntryLabelTextSize(textSize - 1);

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(8f);
        dataSet.setColors(limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6 : ColorTemplateApp.PIE_COLORS_11);
        dataSet.setDrawValues(false);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setDrawEntryLabels(false);

        Legend l = pieChart.getLegend();
        l.setEnabled(true);
        l.setCustom(legends);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setWordWrapEnabled(true);
            l.setYOffset(2f);
            pieChart.setExtraOffsets(2, 0, 2, 2);
        } else {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setWordWrapEnabled(true);
            l.setTextSize(textSize);
            l.setYOffset(8f);
            pieChart.setExtraOffsets(2, 0, 2, 8);
        }
        pieChart.setMinOffset(0f);
        pieChart.highlightValues(null);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_painel_grafico_faturamento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            compartilharTela();
            return true;
        } else if (id == R.id.action_demo_cards) {
            iniciarTourDemonstracao();
            return true;
        } else if (id == R.id.action_demo_semanal) {
            iniciarTourSemanal();
            return true;
        } else if (id == R.id.action_demo_mensal) {
            iniciarTourMensal();
            return true;
        } else if (id == R.id.action_demo_grupo) {
            iniciarTourGrupo();
            return true;
        } else if (id == R.id.action_demo_analise_grupo) {
            iniciarTourAnaliseGrupo();
            return true;
        } else if (id == R.id.action_demo_ranking) {
            iniciarTourRanking();
            return true;
        } else if (id == R.id.action_demo_analise_ranking) {
            iniciarTourAnaliseRanking();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void compartilharTela() {
        try {
            android.graphics.Bitmap bitmap = getBitmapFromView(scrollViewFaturamento);

            java.io.File cachePath = new java.io.File(context.getCacheDir(), "images");
            cachePath.mkdirs();
            java.io.File file = new java.io.File(cachePath, "faturamento_dashboard.png");
            java.io.FileOutputStream stream = new java.io.FileOutputStream(file);
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            Uri contentUri = androidx.core.content.FileProvider.getUriForFile(context, getPackageName() + ".provider", file);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao compartilhar tela: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private android.graphics.Bitmap getBitmapFromView(View view) {
        int height = 0;
        if (view instanceof android.widget.ScrollView) {
            if (((android.widget.ScrollView) view).getChildAt(0) != null) {
                height = ((android.widget.ScrollView) view).getChildAt(0).getHeight();
            } else {
                height = view.getHeight();
            }
        } else {
            height = view.getHeight();
        }

        if (height <= 0) height = 1000;
        int width = view.getWidth() > 0 ? view.getWidth() : 1000;

        android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(bitmap);
        canvas.drawColor(android.graphics.Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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
        pieChart.setCenterText(totalGeralGrupo);
        pieChart.setCenterTextSize(10);
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
        // O evento nativo pode ser instável em alguns dispositivos com rotação ativa
        irParaFaturamento();
    }

    private void irParaFaturamento() {
        if (listaGraficoPizza != null && !listaGraficoPizza.isEmpty()) {
            GraficoFaturamentoHelper.setListaParticipacaoFaturamento(listaGraficoPizza); // NOVO: Salva no Helper
            startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class)
                    .putExtra("tipoRelatorio", "G"));
                    // .putExtra("lista", (Serializable) listaGraficoPizza));

            //startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityParticipacaoFaturamento.class)
            //        .putExtra("dataInicial", "-")
            //        .putExtra("dataFinal", "-")
            //        .putExtra("tipoRelatorio", "G")
            //        .putExtra("lista", (Serializable) listaGraficoPizza));
        }
    }

    private void exibirDialogAnaliseRanking() {
        if (listaRankingAgregada == null || listaRankingAgregada.isEmpty()) {
            Toast.makeText(context, "Ranking indisponível no momento.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Determina o limite do Ranking baseado no tamanho da tela (Regra solicitada)
        int limiteTop = 5;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 10;
        }

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);

        LayoutInflater inflater = getLayoutInflater();
        View headerView = inflater.inflate(R.layout.layout_dialog_menu_cliente, findViewById(android.R.id.content), false);

        TextView tvTitulo = headerView.findViewById(R.id.tvResumoClienteNome);
        TextView tvSubtitulo = headerView.findViewById(R.id.tvResumoClienteDoc);
        headerView.findViewById(R.id.tvResumoClienteCidade).setVisibility(View.GONE);
        ImageView ivIcon = headerView.findViewById(R.id.ivMenuClienteIcon);

        if (tvTitulo != null) tvTitulo.setText("RANKING");
        if (tvSubtitulo != null) {
            tvSubtitulo.setText("Clique para visualizar a evolução mensal do cliente");
            tvSubtitulo.setTextSize(11);
            tvSubtitulo.setTextColor(Color.parseColor("#475569"));
        }
        if (ivIcon != null) {
            ivIcon.setImageResource(R.drawable.baseline_analytics_24);
            ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        RecyclerView rv = new RecyclerView(context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setPadding(0, 16, 0, 16);

        // 2. Filtra a lista agregada (Ajuste temporário: listando todos os clientes para teste)
        List<ParticipacaoFaturamento> listaExibicao = new ArrayList<>();
        for (ParticipacaoFaturamento p : listaRankingAgregada) {
            if (p.getNome() != null && !p.getNome().contains("TOTAL") && !p.getNome().contains("OUTROS")) {
                listaExibicao.add(p);
            }
        }

        rv.setAdapter(new com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter(listaExibicao, "C", item -> {
            // Ação de Clique: Abre o novo diálogo de evolução mensal
            exibirEvolucaoMensalCliente(item);
        }));

        dialogRankingAtivo = builder.setCustomTitle(headerView)
                .setView(rv)
                .setPositiveButton("FECHAR", null)
                .show();
    }

    private void exibirDialogAnaliseRankingGrupo() {
        if (listaRankingAgregada == null || listaRankingAgregada.isEmpty()) {
            Toast.makeText(context, "Ranking indisponível no momento.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Determina o limite do Ranking baseado no tamanho da tela (Regra solicitada)
        int limiteTop = 5;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 10;
        }

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);

        LayoutInflater inflater = getLayoutInflater();
        View headerView = inflater.inflate(R.layout.layout_dialog_menu_cliente, findViewById(android.R.id.content), false);

        TextView tvTitulo = headerView.findViewById(R.id.tvResumoClienteNome);
        TextView tvSubtitulo = headerView.findViewById(R.id.tvResumoClienteDoc);
        headerView.findViewById(R.id.tvResumoClienteCidade).setVisibility(View.GONE);
        ImageView ivIcon = headerView.findViewById(R.id.ivMenuClienteIcon);

        if (tvTitulo != null) tvTitulo.setText("RANKING");
        if (tvSubtitulo != null) {
            tvSubtitulo.setText("Clique para visualizar a evolução mensal do grupo");
            tvSubtitulo.setTextSize(11);
            tvSubtitulo.setTextColor(Color.parseColor("#475569"));
        }
        if (ivIcon != null) {
            ivIcon.setImageResource(R.drawable.baseline_analytics_24);
            ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        RecyclerView rv = new RecyclerView(context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setPadding(0, 16, 0, 16);

        // 2. Filtra a lista agregada (Ajuste temporário: listando todos os clientes para teste)
        List<ParticipacaoFaturamento> listaExibicao = new ArrayList<>();
        for (ParticipacaoFaturamento p : listaGrupoAgregada) {
            if (p.getNome() != null && !p.getNome().contains("TOTAL") && !p.getNome().contains("OUTROS")) {
                listaExibicao.add(p);
            }
        }

        rv.setAdapter(new com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter(listaExibicao, "G", item -> {
            // Ação de Clique: Abre o novo diálogo de evolução mensal
            exibirEvolucaoMensalGrupo(item);
        }));

        dialogRankingAtivo = builder.setCustomTitle(headerView)
                .setView(rv)
                .setPositiveButton("FECHAR", null)
                .show();
    }

    private void exibirEvolucaoMensalCliente(ParticipacaoFaturamento cliente) {
        if (listaRankingOriginal == null || listaRankingOriginal.isEmpty()) return;

        // 1. Filtra e Agrega por Mês/Ano (Tratando Devolução E)
        Map<String, ParticipacaoFaturamento> mapaMeses = new java.util.LinkedHashMap<>();
        for (ParticipacaoFaturamento pf : listaRankingOriginal) {
            if (pf.getId() != null && pf.getId().trim().equals(cliente.getId().trim())) {
                String chaveMes = formatarMesAno(pf.getNome_mes(), pf.getAno());
                double valor = Double.parseDouble(pf.getValor_liquido() == null || pf.getValor_liquido().isEmpty() ? "0" : pf.getValor_liquido());

                if (pf.getE_entrada_s_saida() != null && pf.getE_entrada_s_saida().trim().equalsIgnoreCase("E")) {
                    valor = -Math.abs(valor);
                }

                if (!mapaMeses.containsKey(chaveMes)) {
                    ParticipacaoFaturamento consolidado = new ParticipacaoFaturamento();
                    consolidado.setId(pf.getId());
                    consolidado.setNome(chaveMes);
                    consolidado.setValor_liquido(String.valueOf(valor));
                    mapaMeses.put(chaveMes, consolidado);
                } else {
                    ParticipacaoFaturamento existente = mapaMeses.get(chaveMes);
                    double valorExistente = Double.parseDouble(existente.getValor_liquido());
                    existente.setValor_liquido(String.valueOf(valorExistente + valor));
                }
            }
        }

        List<ParticipacaoFaturamento> listaEvolucao = new ArrayList<>(mapaMeses.values());

        // 2. Infla o Layout do Diálogo
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_evolucao_cliente, findViewById(android.R.id.content), false);
        
        // Cabeçalho Customizado (Usando o layout de menu de cliente como base)
        View headerView = getLayoutInflater().inflate(R.layout.layout_dialog_menu_cliente, findViewById(android.R.id.content), false);
        ((TextView) headerView.findViewById(R.id.tvResumoClienteNome)).setText(cliente.getNome());
        ((TextView) headerView.findViewById(R.id.tvResumoClienteDoc)).setText("ID: " + cliente.getId());
        headerView.findViewById(R.id.tvResumoClienteCidade).setVisibility(View.GONE);
        ((ImageView) headerView.findViewById(R.id.ivMenuClienteIcon)).setImageResource(R.drawable.baseline_analytics_24);

        BarChart chart = dialogView.findViewById(R.id.chartEvolucaoDialog);
        //RecyclerView rv = dialogView.findViewById(R.id.rvEvolucaoDialog);

        // 3. Configura o Gráfico
        ArrayList<BarEntry> entries = new ArrayList<>();
        final String[] labels = new String[listaEvolucao.size()];
        for (int i = 0; i < listaEvolucao.size(); i++) {
            float valor = Float.parseFloat(listaEvolucao.get(i).getValor_liquido());
            entries.add(new BarEntry(i, valor));
            labels[i] = listaEvolucao.get(i).getNome();
        }

        BarDataSet dataSet = new BarDataSet(entries, "Evolução Mensal");
        
        // Cores Gradientes p/ o gráfico do diálogo
        List<GradientColor> gradientColors = new ArrayList<>();
        for (int i = 0; i < Math.min(labels.length, ColorTemplateApp.GRADIENT_START_6.length); i++) {
            gradientColors.add(new GradientColor(ColorTemplateApp.GRADIENT_START_6[i], ColorTemplateApp.GRADIENT_END_6[i]));
        }
        dataSet.setGradientColors(gradientColors);
        dataSet.setValueTextSize(9f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueFormatter(new CustomValueFormatter());

        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        //xAxis.setAxisLineColor(Color.WHITE);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.animateY(1000);
        chart.invalidate();

        // 4. Configura a Lista
        //rv.setLayoutManager(new LinearLayoutManager(context));
        //rv.setAdapter(new com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter(listaEvolucao, "C"));

        dialogEvolucaoAtivo = builder.setCustomTitle(headerView)
               .setView(dialogView)
               .setPositiveButton("FECHAR", null)
               .show();
    }
    private void exibirEvolucaoMensalGrupo(ParticipacaoFaturamento grupo) {
        if (listaGrupoOriginal == null || listaGrupoOriginal.isEmpty()) return;

        // 1. Filtra e Agrega por Mês/Ano (Tratando Devolução E)
        Map<String, ParticipacaoFaturamento> mapaMeses = new java.util.LinkedHashMap<>();
        for (ParticipacaoFaturamento pf : listaGrupoOriginal) {
            if (pf.getId() != null && pf.getId().trim().equals(grupo.getId().trim())) {
                String chaveMes = formatarMesAno(pf.getNome_mes(), pf.getAno());
                double valor = Double.parseDouble(pf.getValor_liquido() == null || pf.getValor_liquido().isEmpty() ? "0" : pf.getValor_liquido());

                if (pf.getE_entrada_s_saida() != null && pf.getE_entrada_s_saida().trim().equalsIgnoreCase("E")) {
                    valor = -Math.abs(valor);
                }

                if (!mapaMeses.containsKey(chaveMes)) {
                    ParticipacaoFaturamento consolidado = new ParticipacaoFaturamento();
                    consolidado.setId(pf.getId());
                    consolidado.setNome(chaveMes);
                    consolidado.setValor_liquido(String.valueOf(valor));
                    mapaMeses.put(chaveMes, consolidado);
                } else {
                    ParticipacaoFaturamento existente = mapaMeses.get(chaveMes);
                    double valorExistente = Double.parseDouble(existente.getValor_liquido());
                    existente.setValor_liquido(String.valueOf(valorExistente + valor));
                }
            }
        }

        List<ParticipacaoFaturamento> listaEvolucao = new ArrayList<>(mapaMeses.values());

        // 2. Infla o Layout do Diálogo
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_evolucao_cliente, findViewById(android.R.id.content), false);

        // Cabeçalho Customizado (Usando o layout de menu de cliente como base)
        View headerView = getLayoutInflater().inflate(R.layout.layout_dialog_menu_cliente, findViewById(android.R.id.content), false);
        ((TextView) headerView.findViewById(R.id.tvResumoClienteNome)).setText(grupo.getNome());
        ((TextView) headerView.findViewById(R.id.tvResumoClienteDoc)).setText("ID: " + grupo.getId());
        headerView.findViewById(R.id.tvResumoClienteCidade).setVisibility(View.GONE);
        ((ImageView) headerView.findViewById(R.id.ivMenuClienteIcon)).setImageResource(R.drawable.baseline_analytics_24);

        BarChart chart = dialogView.findViewById(R.id.chartEvolucaoDialog);
        //RecyclerView rv = dialogView.findViewById(R.id.rvEvolucaoDialog);

        // 3. Configura o Gráfico
        ArrayList<BarEntry> entries = new ArrayList<>();
        final String[] labels = new String[listaEvolucao.size()];
        for (int i = 0; i < listaEvolucao.size(); i++) {
            float valor = Float.parseFloat(listaEvolucao.get(i).getValor_liquido());
            entries.add(new BarEntry(i, valor));
            labels[i] = listaEvolucao.get(i).getNome();
        }

        BarDataSet dataSet = new BarDataSet(entries, "Evolução Mensal");

        // Cores Gradientes p/ o gráfico do diálogo
        List<GradientColor> gradientColors = new ArrayList<>();
        for (int i = 0; i < Math.min(labels.length, ColorTemplateApp.GRADIENT_START_6.length); i++) {
            gradientColors.add(new GradientColor(ColorTemplateApp.GRADIENT_START_6[i], ColorTemplateApp.GRADIENT_END_6[i]));
        }
        dataSet.setGradientColors(gradientColors);
        dataSet.setValueTextSize(9f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueFormatter(new CustomValueFormatter());

        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        //xAxis.setAxisLineColor(Color.WHITE);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.animateY(1000);
        chart.invalidate();

        // 4. Configura a Lista
        //rv.setLayoutManager(new LinearLayoutManager(context));
        //rv.setAdapter(new com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter(listaEvolucao, "C"));

        dialogEvolucaoAtivo = builder.setCustomTitle(headerView)
                .setView(dialogView)
                .setPositiveButton("FECHAR", null)
                .show();
    }

    private String formatarMesAno(String mes, String ano) {
        try {
            int m = Integer.parseInt(mes);
            String[] meses = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
            String sMes = (m >= 1 && m <= 12) ? meses[m - 1] : mes;
            String sAno = (ano != null && ano.length() >= 2) ? ano.substring(ano.length() - 2) : ano;
            return sMes + "/" + sAno;
        } catch (Exception e) {
            return mes + "/" + ano;
        }
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        /* CÓDIGO ANTERIOR (APENAS DETECTOR MANUAL DE CLIQUE DUPLO):
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTapTime < 350) { // Detector manual de clique duplo
            irParaFaturamento();
        }
        lastTapTime = currentTime;
        */

        long currentTime = System.currentTimeMillis();
        boolean isDoubleTap = (currentTime - lastTapTime < 350);
        lastTapTime = currentTime;

        // Mantém o clique duplo funcionando em qualquer parte do gráfico
        if (isDoubleTap) {
            irParaFaturamento();
            return;
        }

        // NOVO: Clique simples no centro/fundo da pizza abre o carrossel (sem interferir no clique das fatias)
        if (pieChart != null && me != null) {
            Highlight highlight = pieChart.getHighlightByTouchPoint(me.getX(), me.getY());
            // Se highlight == null, significa que o toque foi no centro ou fora das fatias
            if (highlight == null) {
                irParaFaturamento();
            }
        }
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

    /*
    private void createbarChart4(List<EvolucaoFaturamento> lista) {
        flipCard(context,  lyt7,  lyt8, null , 0);
        barChart4.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart4.getDescription().setEnabled(false);
        barChart4.setPinchZoom(true);
        barChart4.setDrawGridBackground(false); //Cor de fundo
        //barChart.setDrawingCacheBackgroundColor(Color.parseColor("#1031595F"));
        XAxis xAxis = barChart4.getXAxis();
        xAxis.setCenterAxisLabels(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart4.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart4.getAxisRight();
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
        if (stackLabes.length == 9)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_9);
        if (stackLabes.length == 10)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_10);
        if (stackLabes.length == 11)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_11);
        if (stackLabes.length == 12)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_12);
        if (stackLabes.length == 13)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_13);
        if (stackLabes.length == 14)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_14);
        if (stackLabes.length == 15)
            set1.setColors(ColorTemplateApp.SEMANA_COLORS_APP_15);
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

        data.setValueFormatter(new ActivityPainelGraficoFaturamento.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });

        barChart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "MG"));
                GraficoFaturamentoHelper.setListaEvolucaoFaturamento(lista); // NOVO: Salva no Helper para evitar TransactionTooLarge
                startActivity(new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class)
                        .putExtra("tipoRelatorio", "MG"));
                        // .putExtra("lista", (Serializable) lista));
            }
        });
        leftAxis.setDrawLabels(false);
        barChart4.setData(data);

        barChart4.setFitBars(true);

        barChart4.getLegend().setWordWrapEnabled(true);  //Faz a quebra das linhas na legenda
        barChart4.setExtraBottomOffset(8f);



        //barChart4.getLegend().setEnabled(false);
        //mostra a legenda a direita
        barChart4.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        barChart4.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        barChart4.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        barChart4.getLegend().setDrawInside(false);
        barChart4.getLegend().setTextSize(7f);

        barChart4.setDragEnabled(true);

        //barChart.groupBars(0, groupSpace, barSpace);
        barChart4.notifyDataSetChanged();
        barChart4.invalidate();
        barChart4.animateXY(1500, 1500);
    }
*/
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
            // Garante que o texto comece no início da área de desenho (lado esquerdo)
            float xPos = mViewPortHandler.contentLeft() + Utils.convertDpToPixel(5f);
            
            // Força as propriedades do Paint para garantir visibilidade e alinhamento
            mValuePaint.setTextAlign(Paint.Align.LEFT);
            mValuePaint.setColor(color);
            
            // Desenha o texto na posição calculada, ignorando o 'x' original (que estaria no fim da barra)
            c.drawText(valueText, xPos, y, mValuePaint);
        }
    }

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

    @SuppressLint({"ResourceType", "SuspiciousIndentation"})
    private void  flipCard(Context context, View visibleView, View inVisibleView, ProgressBar progressBar, int idColor) {
        if ( progressBar != null) {
            if ( idColor == 1)
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            if ( idColor == 2)
                progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(context, R.color.colorButton), PorterDuff.Mode.SRC_IN);
        }
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
            //if ( visibleView.getId() == caixa_layout_grafico.getId()) {
                //barChart.notifyDataSetChanged();
                //barChart.invalidate();
                //barChart.animateXY(3500, 3500);
            //}
            //Handler handler = new Handler();
            //Runnable runnable = () -> {
                //caixa_layout_resumo.setBackgroundColor(0);
                //caixa_layout_grafico.setBackgroundColor(0);
            //};
            //handler.postDelayed(runnable, 1000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnUsuarioClickListener {
        void onUsuarioClick(Usuario user);
    }

    public class UsuarioDrawerAdapter extends RecyclerView.Adapter<UsuarioDrawerAdapter.ViewHolder> {
        private List<Usuario> usuarios;
        private OnUsuarioClickListener listener;

        public UsuarioDrawerAdapter(List<Usuario> usuarios, OnUsuarioClickListener listener) {
            this.usuarios = usuarios;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario_drawer, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Usuario user = usuarios.get(position);
            holder.tvNome.setText(user.getNome_usuario());
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onUsuarioClick(user);
            });
        }

        @Override
        public int getItemCount() {
            return usuarios.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNome;
            ImageView ivIcon;

            public ViewHolder(View itemView) {
                super(itemView);
                tvNome = itemView.findViewById(R.id.tvNomeUsuario);
                ivIcon = itemView.findViewById(R.id.ivUsuarioIcon);
            }
        }
    }

    // =====================================================================================
    // CÓDIGO TEMPORÁRIO DE DEMONSTRAÇÃO (TOUR GUIADO AUTOMÁTICO PARA VÍDEO - FOCO NOS CARDS)
    // =====================================================================================
    private android.os.Handler demoHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private View demoOverlayView = null;

    private void destacarCard(TextView tv, boolean destacar) {
        if (tv == null || !(tv.getParent() instanceof View) || !(((View) tv.getParent()).getParent() instanceof View)) {
            return;
        }
        View parentCard = (View) ((View) tv.getParent()).getParent();
        if (destacar) {
            parentCard.setBackgroundColor(android.graphics.Color.parseColor("#FEF08A")); // Destaque Amarelo Dourado
            if (parentCard instanceof com.google.android.material.card.MaterialCardView) {
                ((com.google.android.material.card.MaterialCardView) parentCard).setStrokeColor(android.graphics.Color.parseColor("#F59E0B"));
                ((com.google.android.material.card.MaterialCardView) parentCard).setStrokeWidth(6);
            }
            parentCard.animate().scaleX(1.06f).scaleY(1.06f).setDuration(300).start();

            // Rola a HorizontalScrollView para garantir que o card ativo fique visível
            if (parentCard.getParent() instanceof View && ((View) parentCard.getParent()).getParent() instanceof android.widget.HorizontalScrollView) {
                android.widget.HorizontalScrollView hsv = (android.widget.HorizontalScrollView) ((View) parentCard.getParent()).getParent();
                int scrollX = Math.max(0, parentCard.getLeft() - 32);
                hsv.smoothScrollTo(scrollX, 0);
            }
        } else {
            parentCard.setBackgroundColor(android.graphics.Color.WHITE);
            if (parentCard instanceof com.google.android.material.card.MaterialCardView) {
                ((com.google.android.material.card.MaterialCardView) parentCard).setStrokeColor(android.graphics.Color.parseColor("#E2E8F0"));
                ((com.google.android.material.card.MaterialCardView) parentCard).setStrokeWidth(2);
            }
            parentCard.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
        }
    }

    private void restaurarTodosCards() {
        destacarCard(vtotalVendasPeriodo, false);
        destacarCard(vmedioVendasPeriodo, false);
        destacarCard(qtotalVendasPeriodo, false);
        destacarCard(vtotalDevolucaoPeriodo, false);
    }

    private void iniciarTourDemonstracao() {
        if (demoOverlayView != null) return; // Já está rodando

        ViewGroup rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        // Infla um overlay visual simples por código
        android.widget.FrameLayout overlay = new android.widget.FrameLayout(this);
        overlay.setBackgroundColor(android.graphics.Color.parseColor("#40000000")); // Fundo semi-transparente suave
        overlay.setClickable(true);
        overlay.setFocusable(true);

        // Card de Legenda Flutuante
        androidx.cardview.widget.CardView cardLegenda = new androidx.cardview.widget.CardView(this);
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.view.Gravity.BOTTOM
        );
        cardParams.setMargins(32, 32, 32, 64);
        cardLegenda.setLayoutParams(cardParams);
        cardLegenda.setRadius(24f);
        cardLegenda.setCardElevation(16f);
        cardLegenda.setCardBackgroundColor(android.graphics.Color.parseColor("#1E293B")); // Dark Slate

        android.widget.LinearLayout layoutLegenda = new android.widget.LinearLayout(this);
        layoutLegenda.setOrientation(android.widget.LinearLayout.VERTICAL);
        layoutLegenda.setPadding(32, 24, 32, 24);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("DEMONSTRAÇÃO INTERATIVA - CARDS KPI");
        tvTitulo.setTextColor(android.graphics.Color.parseColor("#38BDF8")); // Azul claro
        tvTitulo.setTextSize(12f);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDescricao = new TextView(this);
        tvDescricao.setText("Iniciando apresentação dos cards indicadores...");
        tvDescricao.setTextColor(android.graphics.Color.WHITE);
        tvDescricao.setTextSize(14f);
        tvDescricao.setPadding(0, 8, 0, 16);

        Button btnFechar = new Button(this);
        btnFechar.setText("Clique para parar a demonstração");
        btnFechar.setTextColor(android.graphics.Color.WHITE);
        btnFechar.setBackgroundColor(android.graphics.Color.parseColor("#EF4444")); // Vermelho
        btnFechar.setOnClickListener(v -> encerrarTourDemonstracao());

        layoutLegenda.addView(tvTitulo);
        layoutLegenda.addView(tvDescricao);
        layoutLegenda.addView(btnFechar);
        cardLegenda.addView(layoutLegenda);
        overlay.addView(cardLegenda);

        rootView.addView(overlay, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        ));

        demoOverlayView = overlay;

        // ROTEIRO AUTOMÁTICO PASSO A PASSO (5 SEGUNDOS POR CARD)
        Runnable[] passos = new Runnable[]{
            () -> {
                // Card 1: Volume Total
                if (scrollViewFaturamento != null) scrollViewFaturamento.smoothScrollTo(0, 0);
                restaurarTodosCards();
                destacarCard(vtotalVendasPeriodo, true);
                tvDescricao.setText("CARD 1/4: VOLUME TOTAL DO PERÍODO\n" +
                        "Apresenta o faturamento líquido total acumulado no período em R$ (com abatimento das devoluções comerciais).");
            },
            () -> {
                // Card 2: Ticket Médio
                restaurarTodosCards();
                destacarCard(vmedioVendasPeriodo, true);
                tvDescricao.setText("CARD 2/4: TICKET MÉDIO\n" +
                        "Indica a média de valor por pedido realizado (Faturamento ÷ Quantidade de Pedidos).");
            },
            () -> {
                // Card 3: Qtd Pedidos
                restaurarTodosCards();
                destacarCard(qtotalVendasPeriodo, true);
                tvDescricao.setText("CARD 3/4: TICKETS E PEDIDOS\n" +
                        "Exibe a quantidade total de vendas e pedidos fechados no período analisado.");
            },
            () -> {
                // Card 4: Devoluções
                restaurarTodosCards();
                destacarCard(vtotalDevolucaoPeriodo, true);
                tvDescricao.setText("CARD 4/4: DEVOLUÇÕES DO PERÍODO\n" +
                        "Informa a quantidade de vendas devolvidas e o valor total em R$ abatido do faturamento.");
            },
            () -> {
                // Encerramento
                restaurarTodosCards();
                tvDescricao.setText("Apresentação dos Cards concluída com sucesso!");
                demoHandler.postDelayed(() -> encerrarTourDemonstracao(), 3000);
            }
        };

        // Agenda os passos (5 segundos por card)
        long delay = 500;
        for (Runnable p : passos) {
            demoHandler.postDelayed(p, delay);
            delay += 5000; // 5 segundos por passo
        }
    }

    private androidx.appcompat.app.AlertDialog dialogRankingAtivo = null;
    private androidx.appcompat.app.AlertDialog dialogEvolucaoAtivo = null;

    private void fecharDialogsAbertas() {
        if (dialogEvolucaoAtivo != null) {
            try { if (dialogEvolucaoAtivo.isShowing()) dialogEvolucaoAtivo.dismiss(); } catch (Exception ignored) {}
            dialogEvolucaoAtivo = null;
        }
        if (dialogRankingAtivo != null) {
            try { if (dialogRankingAtivo.isShowing()) dialogRankingAtivo.dismiss(); } catch (Exception ignored) {}
            dialogRankingAtivo = null;
        }
    }

    private void encerrarTourDemonstracao() {
        if (demoHandler != null) demoHandler.removeCallbacksAndMessages(null);
        restaurarTodosCards();
        fecharDialogsAbertas();
        if (demoOverlayView != null) {
            ViewGroup rootView = findViewById(android.R.id.content);
            if (rootView != null) rootView.removeView(demoOverlayView);
            demoOverlayView = null;
        }
    }

    private void iniciarTourSemanal() {
        if (demoOverlayView != null) encerrarTourDemonstracao();

        ViewGroup rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        // 1. Rola suavemente até o Card 1 (Gráfico Semanal - barChart3)
        if (scrollViewFaturamento != null && cardGrafico1 != null) {
            scrollViewFaturamento.smoothScrollTo(0, cardGrafico1.getTop());
        }

        if (barChart3 != null) {
            barChart3.animateXY(1200, 1200);
        }

        // Overlay de Legenda
        android.widget.FrameLayout overlay = new android.widget.FrameLayout(this);
        overlay.setBackgroundColor(android.graphics.Color.parseColor("#30000000")); // Fundo suave
        overlay.setClickable(true);
        overlay.setFocusable(true);

        androidx.cardview.widget.CardView cardLegenda = new androidx.cardview.widget.CardView(this);
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.view.Gravity.BOTTOM
        );
        cardParams.setMargins(32, 32, 32, 64);
        cardLegenda.setLayoutParams(cardParams);
        cardLegenda.setRadius(24f);
        cardLegenda.setCardElevation(16f);
        cardLegenda.setCardBackgroundColor(android.graphics.Color.parseColor("#1E293B"));

        android.widget.LinearLayout layoutLegenda = new android.widget.LinearLayout(this);
        layoutLegenda.setOrientation(android.widget.LinearLayout.VERTICAL);
        layoutLegenda.setPadding(32, 24, 32, 24);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("DEMONSTRAÇÃO - GRÁFICO SEMANAL");
        tvTitulo.setTextColor(android.graphics.Color.parseColor("#38BDF8"));
        tvTitulo.setTextSize(12f);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDescricao = new TextView(this);
        tvDescricao.setText("Acompanhe o ritmo de vendas em cada semana do mês. As cores empilhadas dividem o faturamento semana a semana.\n\nSimulando toque no gráfico para abrir o carrossel interativo...");
        tvDescricao.setTextColor(android.graphics.Color.WHITE);
        tvDescricao.setTextSize(14f);
        tvDescricao.setPadding(0, 8, 0, 16);

        Button btnFechar = new Button(this);
        btnFechar.setText("Clique para parar a demonstração");
        btnFechar.setTextColor(android.graphics.Color.WHITE);
        btnFechar.setBackgroundColor(android.graphics.Color.parseColor("#EF4444"));
        btnFechar.setOnClickListener(v -> encerrarTourDemonstracao());

        layoutLegenda.addView(tvTitulo);
        layoutLegenda.addView(tvDescricao);
        layoutLegenda.addView(btnFechar);
        cardLegenda.addView(layoutLegenda);
        overlay.addView(cardLegenda);

        rootView.addView(overlay, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        ));

        demoOverlayView = overlay;

        // Aguarda 4.5 segundos e simula o clique no gráfico semanal para carregar os dados e abrir o carrossel
        demoHandler.postDelayed(() -> {
            if (barChart3 != null) {
                barChart3.performClick();
            }
            encerrarTourDemonstracao();
        }, 4500);
    }

    private void iniciarTourMensal() {
        if (demoOverlayView != null) encerrarTourDemonstracao();

        ViewGroup rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        // 1. Rola suavemente até o Card 2 (Gráfico Mensal - barChart1 / cardGrafico2)
        if (scrollViewFaturamento != null && cardGrafico2 != null) {
            scrollViewFaturamento.smoothScrollTo(0, cardGrafico2.getTop());
        }

        if (barChart1 != null) {
            barChart1.animateXY(1200, 1200);
        }

        // Overlay de Legenda
        android.widget.FrameLayout overlay = new android.widget.FrameLayout(this);
        overlay.setBackgroundColor(android.graphics.Color.parseColor("#30000000")); // Fundo suave
        overlay.setClickable(true);
        overlay.setFocusable(true);

        androidx.cardview.widget.CardView cardLegenda = new androidx.cardview.widget.CardView(this);
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.view.Gravity.BOTTOM
        );
        cardParams.setMargins(32, 32, 32, 64);
        cardLegenda.setLayoutParams(cardParams);
        cardLegenda.setRadius(24f);
        cardLegenda.setCardElevation(16f);
        cardLegenda.setCardBackgroundColor(android.graphics.Color.parseColor("#1E293B"));

        android.widget.LinearLayout layoutLegenda = new android.widget.LinearLayout(this);
        layoutLegenda.setOrientation(android.widget.LinearLayout.VERTICAL);
        layoutLegenda.setPadding(32, 24, 32, 24);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("DEMONSTRAÇÃO - GRÁFICO MENSAL");
        tvTitulo.setTextColor(android.graphics.Color.parseColor("#38BDF8"));
        tvTitulo.setTextSize(12f);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDescricao = new TextView(this);
        tvDescricao.setText("Acompanhe o faturamento líquido acumulado mês a mês nos últimos 6 meses.\n\nSimulando toque no gráfico para abrir o carrossel interativo...");
        tvDescricao.setTextColor(android.graphics.Color.WHITE);
        tvDescricao.setTextSize(14f);
        tvDescricao.setPadding(0, 8, 0, 16);

        Button btnFechar = new Button(this);
        btnFechar.setText("Clique para parar a demonstração");
        btnFechar.setTextColor(android.graphics.Color.WHITE);
        btnFechar.setBackgroundColor(android.graphics.Color.parseColor("#EF4444"));
        btnFechar.setOnClickListener(v -> encerrarTourDemonstracao());

        layoutLegenda.addView(tvTitulo);
        layoutLegenda.addView(tvDescricao);
        layoutLegenda.addView(btnFechar);
        cardLegenda.addView(layoutLegenda);
        overlay.addView(cardLegenda);

        rootView.addView(overlay, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        ));

        demoOverlayView = overlay;

        // Aguarda 4.5 segundos e simula o clique no gráfico mensal para carregar os dados e abrir o carrossel
        demoHandler.postDelayed(() -> {
            if (barChart1 != null) {
                barChart1.performClick();
            }
            encerrarTourDemonstracao();
        }, 4500);
    }

    private void iniciarTourGrupo() {
        if (demoOverlayView != null) encerrarTourDemonstracao();

        ViewGroup rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        // 1. Rola suavemente até o Card do Gráfico de Pizza / Grupo (cardGrafico3)
        if (scrollViewFaturamento != null && cardGrafico3 != null) {
            scrollViewFaturamento.smoothScrollTo(0, cardGrafico3.getTop());
        }

        if (pieChart != null) {
            pieChart.animateXY(1200, 1200);
        }

        // Overlay de Legenda
        android.widget.FrameLayout overlay = new android.widget.FrameLayout(this);
        overlay.setBackgroundColor(android.graphics.Color.parseColor("#30000000")); // Fundo suave
        overlay.setClickable(true);
        overlay.setFocusable(true);

        androidx.cardview.widget.CardView cardLegenda = new androidx.cardview.widget.CardView(this);
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.view.Gravity.BOTTOM
        );
        cardParams.setMargins(32, 32, 32, 64);
        cardLegenda.setLayoutParams(cardParams);
        cardLegenda.setRadius(24f);
        cardLegenda.setCardElevation(16f);
        cardLegenda.setCardBackgroundColor(android.graphics.Color.parseColor("#1E293B"));

        android.widget.LinearLayout layoutLegenda = new android.widget.LinearLayout(this);
        layoutLegenda.setOrientation(android.widget.LinearLayout.VERTICAL);
        layoutLegenda.setPadding(32, 24, 32, 24);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("DEMONSTRAÇÃO - GRUPO DE PRODUTOS");
        tvTitulo.setTextColor(android.graphics.Color.parseColor("#38BDF8"));
        tvTitulo.setTextSize(12f);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDescricao = new TextView(this);
        tvDescricao.setText("Acompanhe a participação das vendas por grupo de produtos no gráfico de pizza.\n\nSimulando toque no gráfico para abrir o carrossel interativo por grupos...");
        tvDescricao.setTextColor(android.graphics.Color.WHITE);
        tvDescricao.setTextSize(14f);
        tvDescricao.setPadding(0, 8, 0, 16);

        Button btnFechar = new Button(this);
        btnFechar.setText("Clique para parar a demonstração");
        btnFechar.setTextColor(android.graphics.Color.WHITE);
        btnFechar.setBackgroundColor(android.graphics.Color.parseColor("#EF4444"));
        btnFechar.setOnClickListener(v -> encerrarTourDemonstracao());

        layoutLegenda.addView(tvTitulo);
        layoutLegenda.addView(tvDescricao);
        layoutLegenda.addView(btnFechar);
        cardLegenda.addView(layoutLegenda);
        overlay.addView(cardLegenda);

        rootView.addView(overlay, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        ));

        demoOverlayView = overlay;

        // Aguarda 4.5 segundos e abre o carrossel em modo demonstração por grupo
        demoHandler.postDelayed(() -> {
            encerrarTourDemonstracao();
            if (listaGraficoPizza != null && !listaGraficoPizza.isEmpty()) {
                GraficoFaturamentoHelper.setListaParticipacaoFaturamento(listaGraficoPizza);
                Intent intent = new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class);
                intent.putExtra("tipoRelatorio", "G");
                intent.putExtra("isDemo", true);
                startActivity(intent);
            }
        }, 4500);
    }

    private void iniciarTourRanking() {
        if (demoOverlayView != null) encerrarTourDemonstracao();

        ViewGroup rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        // 1. Rola suavemente até o Card do Ranking de Clientes (cardGrafico4 / horChart)
        if (scrollViewFaturamento != null && cardGrafico4 != null) {
            scrollViewFaturamento.smoothScrollTo(0, cardGrafico4.getTop());
        }

        if (horChart != null) {
            horChart.animateY(1200);
        }

        // Overlay de Legenda
        android.widget.FrameLayout overlay = new android.widget.FrameLayout(this);
        overlay.setBackgroundColor(android.graphics.Color.parseColor("#30000000")); // Fundo suave
        overlay.setClickable(true);
        overlay.setFocusable(true);

        androidx.cardview.widget.CardView cardLegenda = new androidx.cardview.widget.CardView(this);
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.view.Gravity.BOTTOM
        );
        cardParams.setMargins(32, 32, 32, 64);
        cardLegenda.setLayoutParams(cardParams);
        cardLegenda.setRadius(24f);
        cardLegenda.setCardElevation(16f);
        cardLegenda.setCardBackgroundColor(android.graphics.Color.parseColor("#1E293B"));

        android.widget.LinearLayout layoutLegenda = new android.widget.LinearLayout(this);
        layoutLegenda.setOrientation(android.widget.LinearLayout.VERTICAL);
        layoutLegenda.setPadding(32, 24, 32, 24);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("DEMONSTRAÇÃO - RANKING DE CLIENTES");
        tvTitulo.setTextColor(android.graphics.Color.parseColor("#38BDF8"));
        tvTitulo.setTextSize(12f);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDescricao = new TextView(this);
        tvDescricao.setText("Veja os principais clientes do período em barras horizontais roláveis.\n\nSimulando toque no gráfico para abrir o carrossel interativo de clientes...");
        tvDescricao.setTextColor(android.graphics.Color.WHITE);
        tvDescricao.setTextSize(14f);
        tvDescricao.setPadding(0, 8, 0, 16);

        Button btnFechar = new Button(this);
        btnFechar.setText("Clique para parar a demonstração");
        btnFechar.setTextColor(android.graphics.Color.WHITE);
        btnFechar.setBackgroundColor(android.graphics.Color.parseColor("#EF4444"));
        btnFechar.setOnClickListener(v -> encerrarTourDemonstracao());

        layoutLegenda.addView(tvTitulo);
        layoutLegenda.addView(tvDescricao);
        layoutLegenda.addView(btnFechar);
        cardLegenda.addView(layoutLegenda);
        overlay.addView(cardLegenda);

        rootView.addView(overlay, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        ));

        demoOverlayView = overlay;

        // Aguarda 4.5 segundos e abre o carrossel em modo demonstração ranking
        demoHandler.postDelayed(() -> {
            encerrarTourDemonstracao();
            if (listaRankingAgregada != null && !listaRankingAgregada.isEmpty()) {
                GraficoFaturamentoHelper.setListaParticipacaoFaturamento(listaRankingAgregada);
                Intent intent = new Intent(ActivityPainelGraficoFaturamento.this, ActivityGraficoFaturamentoCarrosel.class);
                intent.putExtra("tipoRelatorio", "C");
                intent.putExtra("isDemo", true);
                startActivity(intent);
            }
        }, 4500);
    }

    private void iniciarTourAnaliseGrupo() {
        if (demoOverlayView != null) encerrarTourDemonstracao();

        ViewGroup rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        if (scrollViewFaturamento != null && cardGrafico3 != null) {
            scrollViewFaturamento.smoothScrollTo(0, cardGrafico3.getTop());
        }

        android.widget.FrameLayout overlay = new android.widget.FrameLayout(this);
        overlay.setBackgroundColor(android.graphics.Color.parseColor("#30000000"));
        overlay.setClickable(true);
        overlay.setFocusable(true);

        androidx.cardview.widget.CardView cardLegenda = new androidx.cardview.widget.CardView(this);
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.view.Gravity.BOTTOM
        );
        cardParams.setMargins(32, 32, 32, 64);
        cardLegenda.setLayoutParams(cardParams);
        cardLegenda.setRadius(24f);
        cardLegenda.setCardElevation(16f);
        cardLegenda.setCardBackgroundColor(android.graphics.Color.parseColor("#1E293B"));

        android.widget.LinearLayout layoutLegenda = new android.widget.LinearLayout(this);
        layoutLegenda.setOrientation(android.widget.LinearLayout.VERTICAL);
        layoutLegenda.setPadding(32, 24, 32, 24);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("DEMONSTRAÇÃO - ANÁLISE POR GRUPO");
        tvTitulo.setTextColor(android.graphics.Color.parseColor("#38BDF8"));
        tvTitulo.setTextSize(12f);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDescricao = new TextView(this);
        tvDescricao.setText("Toque no ícone de análise ao lado do refresh para abrir a lista do ranking de grupos e consultar a evolução mensal.");
        tvDescricao.setTextColor(android.graphics.Color.WHITE);
        tvDescricao.setTextSize(14f);
        tvDescricao.setPadding(0, 8, 0, 16);

        Button btnFechar = new Button(this);
        btnFechar.setText("Clique para parar a demonstração");
        btnFechar.setTextColor(android.graphics.Color.WHITE);
        btnFechar.setBackgroundColor(android.graphics.Color.parseColor("#EF4444"));
        btnFechar.setOnClickListener(v -> encerrarTourDemonstracao());

        layoutLegenda.addView(tvTitulo);
        layoutLegenda.addView(tvDescricao);
        layoutLegenda.addView(btnFechar);
        cardLegenda.addView(layoutLegenda);
        overlay.addView(cardLegenda);

        rootView.addView(overlay, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        ));

        demoOverlayView = overlay;

        // Passo 1 (3.0s): Abre o diálogo com a lista do ranking de grupos
        demoHandler.postDelayed(() -> {
            exibirDialogAnaliseRankingGrupo();
        }, 3000);

        // Passo 2 (6.0s): Seleciona o primeiro grupo da lista e abre a evolução mensal
        demoHandler.postDelayed(() -> {
            if (listaGrupoAgregada != null && !listaGrupoAgregada.isEmpty()) {
                exibirEvolucaoMensalGrupo(listaGrupoAgregada.get(0));
            }
        }, 6000);

        // Passo 3 (9.5s): Encerra a demonstração
        demoHandler.postDelayed(() -> {
            encerrarTourDemonstracao();
        }, 9500);
    }

    private void iniciarTourAnaliseRanking() {
        if (demoOverlayView != null) encerrarTourDemonstracao();

        ViewGroup rootView = findViewById(android.R.id.content);
        if (rootView == null) return;

        if (scrollViewFaturamento != null && cardGrafico4 != null) {
            scrollViewFaturamento.smoothScrollTo(0, cardGrafico4.getTop());
        }

        android.widget.FrameLayout overlay = new android.widget.FrameLayout(this);
        overlay.setBackgroundColor(android.graphics.Color.parseColor("#30000000"));
        overlay.setClickable(true);
        overlay.setFocusable(true);

        androidx.cardview.widget.CardView cardLegenda = new androidx.cardview.widget.CardView(this);
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.view.Gravity.BOTTOM
        );
        cardParams.setMargins(32, 32, 32, 64);
        cardLegenda.setLayoutParams(cardParams);
        cardLegenda.setRadius(24f);
        cardLegenda.setCardElevation(16f);
        cardLegenda.setCardBackgroundColor(android.graphics.Color.parseColor("#1E293B"));

        android.widget.LinearLayout layoutLegenda = new android.widget.LinearLayout(this);
        layoutLegenda.setOrientation(android.widget.LinearLayout.VERTICAL);
        layoutLegenda.setPadding(32, 24, 32, 24);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("DEMONSTRAÇÃO - ANÁLISE POR CLIENTE");
        tvTitulo.setTextColor(android.graphics.Color.parseColor("#38BDF8"));
        tvTitulo.setTextSize(12f);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDescricao = new TextView(this);
        tvDescricao.setText("Toque no ícone de análise ao lado do refresh para abrir a lista do ranking de clientes e consultar a evolução mensal.");
        tvDescricao.setTextColor(android.graphics.Color.WHITE);
        tvDescricao.setTextSize(14f);
        tvDescricao.setPadding(0, 8, 0, 16);

        Button btnFechar = new Button(this);
        btnFechar.setText("Clique para parar a demonstração");
        btnFechar.setTextColor(android.graphics.Color.WHITE);
        btnFechar.setBackgroundColor(android.graphics.Color.parseColor("#EF4444"));
        btnFechar.setOnClickListener(v -> encerrarTourDemonstracao());

        layoutLegenda.addView(tvTitulo);
        layoutLegenda.addView(tvDescricao);
        layoutLegenda.addView(btnFechar);
        cardLegenda.addView(layoutLegenda);
        overlay.addView(cardLegenda);

        rootView.addView(overlay, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        ));

        demoOverlayView = overlay;

        // Passo 1 (3.0s): Abre o diálogo com a lista do ranking de clientes
        demoHandler.postDelayed(() -> {
            exibirDialogAnaliseRanking();
        }, 3000);

        // Passo 2 (6.0s): Seleciona o primeiro cliente da lista e abre a evolução mensal
        demoHandler.postDelayed(() -> {
            if (listaRankingAgregada != null && !listaRankingAgregada.isEmpty()) {
                exibirEvolucaoMensalCliente(listaRankingAgregada.get(0));
            }
        }, 6000);

        // Passo 3 (9.5s): Encerra a demonstração
        demoHandler.postDelayed(() -> {
            encerrarTourDemonstracao();
        }, 9500);
    }

}
