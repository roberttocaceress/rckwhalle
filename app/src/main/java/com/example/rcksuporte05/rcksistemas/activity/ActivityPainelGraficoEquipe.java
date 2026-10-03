package com.example.rcksuporte05.rcksistemas.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.MesAno;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesGrupoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.MetaSemanal;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.ColorTemplateApp;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.FirestoreConexao;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.github.mikephil.charting.utils.Utils;
import android.graphics.Paint;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;

import android.os.Handler;
import android.os.Looper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.github.mikephil.charting.highlight.Highlight;
import android.content.Intent;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class ActivityPainelGraficoEquipe extends AppCompatActivity implements OnChartValueSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.barChart1)
    BarChart barChartSemanal;

    @BindView(R.id.barChart2)
    BarChart barChartMensal;

    @BindView(R.id.barChart3)
    HorizontalBarChart barChartRanking;

    @BindView(R.id.lyt1)
    LinearLayout lyt1;
    @BindView(R.id.lyt2)
    LinearLayout lyt2;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    @BindView(R.id.lyt3)
    LinearLayout lyt3;
    @BindView(R.id.lyt4)
    LinearLayout lyt4;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;

    @BindView(R.id.lyt5)
    LinearLayout lyt5;
    @BindView(R.id.lyt6)
    LinearLayout lyt6;
    @BindView(R.id.progressBar3)
    ProgressBar progressBar3;

    @BindView(R.id.scrollViewDashboard)
    ScrollView scrollViewDashboard;

    @BindView(R.id.lytGlobalLoading)
    View lytGlobalLoading;

    @BindView(R.id.tvGlobalLoadingTitle)
    TextView tvGlobalLoadingTitle;

    @BindView(R.id.tvTituloSemanal)
    TextView tvTituloSemanal;

    @BindView(R.id.tvKpiReal1)
    TextView tvKpiReal1;
    @BindView(R.id.tvKpiPerc1)
    TextView tvKpiPerc1;
    @BindView(R.id.tvKpiMeta1)
    TextView tvKpiMeta1;
    @BindView(R.id.pbKpi1)
    ProgressBar pbKpi1;

    @BindView(R.id.tvKpiReal2)
    TextView tvKpiReal2;
    @BindView(R.id.tvKpiPerc2)
    TextView tvKpiPerc2;
    @BindView(R.id.tvKpiMeta2)
    TextView tvKpiMeta2;
    @BindView(R.id.pbKpi2)
    ProgressBar pbKpi2;

    @BindView(R.id.tvKpiReal3)
    TextView tvKpiReal3;
    @BindView(R.id.tvKpiPerc3)
    TextView tvKpiPerc3;
    @BindView(R.id.tvKpiMeta3)
    TextView tvKpiMeta3;
    @BindView(R.id.pbKpi3)
    ProgressBar pbKpi3;

    @BindView(R.id.tvKpiTituloDevolucao)
    TextView tvKpiTituloDevolucao;
    @BindView(R.id.tvKpiRealDevolucao)
    TextView tvKpiRealDevolucao;
    @BindView(R.id.tvKpiPercDevolucao)
    TextView tvKpiPercDevolucao;
    @BindView(R.id.pbKpiDevolucao)
    ProgressBar pbKpiDevolucao;

    @BindView(R.id.tvScrollSemanal)
    TextView tvScrollSemanal;
    @BindView(R.id.tvScrollMensal)
    TextView tvScrollMensal;
    @BindView(R.id.tvScrollRanking)
    TextView tvScrollRanking;

    @BindView(R.id.cardGrafico1)
    androidx.cardview.widget.CardView cardGrafico1;

    @BindView(R.id.cardGrafico2)
    androidx.cardview.widget.CardView cardGrafico2;

    @BindView(R.id.cardGrafico3)
    androidx.cardview.widget.CardView cardGrafico3;

    @BindView(R.id.tvDataPesquisa)
    TextView tvDataPesquisa;

    @BindView(R.id.tvMesAnoReferencia)
    TextView tvMesAnoReferencia;

    @BindView(R.id.btnSelecionarMesAnoGlobal)
    View btnSelecionarMesAnoGlobal;

    private Context context;
    private DBHelper db;
    private Configuracao configuracao;
    private List<Usuario> listaVendedores;
    private int anoAtual, mesAtual, semanaAtual;
    private int anoSelecionado, mesSelecionado;
    private AtomicInteger activeRequests = new AtomicInteger(0);
    private Handler handler = new Handler(Looper.getMainLooper());
    private Map<String, MetaSemanal> mapaMetasSemanais = new HashMap<>();
    private Map<String, MesAno> mapaMetasMensais = new HashMap<>();
    private double metaEmpresaGlobal = 3650000.00; // Valor dinâmico carregado do Firestore

    // NOVO: Listas para exportação detalhada
    private List<ItemPerformanceVendedor> listaParaExportacao = new ArrayList<>();
    private List<MesGrupoMeta> listaVendaDetalhada = new ArrayList<>();
    private List<MesGrupoMeta> listaVendaSegmento = new ArrayList<>();
    private List<MesAnoMeta> listaVendaMensalHistorico = new ArrayList<>();

    public static class ItemPerformanceVendedor {
        public String nome;
        public float meta;
        public float real;
        public int idUsuario;
        public String idQuandoVendedor; // NOVO: ID usado na API
        public ItemPerformanceVendedor(String n, float m, float r, int id, String idQuando) { 
            this.nome = n; this.meta = m; this.real = r; this.idUsuario = id;
            this.idQuandoVendedor = idQuando;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_grafico_equipe);
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

        context = this;
        db = new DBHelper(this);
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

        Calendar cal = Calendar.getInstance();
        anoAtual = cal.get(Calendar.YEAR);
        mesAtual = cal.get(Calendar.MONTH) + 1;
        semanaAtual = cal.get(Calendar.WEEK_OF_MONTH);
        
        anoSelecionado = anoAtual;
        mesSelecionado = mesAtual;

        tvTituloSemanal.setText("Meta vs Realizado"/* (Semana " + semanaAtual + ")"*/);
        atualizarTextoReferencia();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Performance da Equipe");
            atualizarSubtitulo(0);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        flipCard(context, lyt2, lyt1, progressBar1, 1);
        flipCard(context, lyt4, lyt3, progressBar2, 1);
        flipCard(context, lyt6, lyt5, progressBar3, 1);

        // carregarTodosOsGraficos(); // REMOVIDO: Agora perguntamos o período primeiro
        animarEntrada();

        // NOVO: Pergunta o período antes de carregar os dados pela primeira vez
        try {
            tvDataPesquisa.setText("Processado em: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
        } catch (Exception e) {
            tvDataPesquisa.setText("");
        }
        handler.postDelayed(() -> exibirSeletorMesAno(true), 500);
    }

    private void atualizarTextoReferencia() {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        if (tvMesAnoReferencia != null) {
            tvMesAnoReferencia.setText(meses[mesSelecionado - 1] + " / " + anoSelecionado);
        }
    }

    @OnClick(R.id.btnSelecionarMesAnoGlobal)
    public void onSelecionarPeriodoClick() {
        exibirSeletorMesAno(false);
    }

    private void exibirSeletorMesAno(boolean isInicial) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final NumberPicker monthPicker = new NumberPicker(context);
        final NumberPicker yearPicker = new NumberPicker(context);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(mesSelecionado);
        monthPicker.setDisplayedValues(new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"});
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        yearPicker.setMinValue(2020);
        yearPicker.setMaxValue(2030);
        yearPicker.setValue(anoSelecionado);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(android.view.Gravity.CENTER);
        layout.setPadding(20, 20, 20, 20);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        monthPicker.setLayoutParams(params);
        yearPicker.setLayoutParams(params);

        layout.addView(monthPicker);
        layout.addView(yearPicker);

        builder.setTitle(isInicial ? "Selecione o Período de Início" : "Selecionar Período")
                .setView(layout)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    mesSelecionado = monthPicker.getValue();
                    anoSelecionado = yearPicker.getValue();
                    atualizarTextoReferencia();
                    carregarTodosOsGraficos();
                })
                .setNegativeButton(isInicial ? "Mês Atual" : "Cancelar", (dialog, which) -> {
                    if (isInicial) {
                        // Se cancelar no início, processa o mês atual por padrão
                        atualizarTextoReferencia();
                        carregarTodosOsGraficos();
                    }
                });
        
        if (isInicial) builder.setCancelable(false); // Força uma escolha inicial

        builder.show();
    }

    private String getDataReferencia(int ano, int mes) {
        return String.format("%04d-%02d-01", ano, mes);
    }

    private void animarEntrada() {
        androidx.cardview.widget.CardView[] cards = {cardGrafico1, cardGrafico2, cardGrafico3};
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

    private void carregarTodosOsGraficos() {
        showLoading("Sincronizando painel da equipe...");
        
        tvKpiReal1.setText("R$ 0,00");
        tvKpiMeta1.setText("Meta Equipe: R$ 0,00");
        tvKpiPerc1.setText("0% atingido");
        pbKpi1.setProgress(0);

        tvKpiReal2.setText("R$ 0,00");
        tvKpiMeta2.setText("Meta Empresa: R$ 3.650.000,00");
        tvKpiPerc2.setText("0.00% da empresa");
        pbKpi2.setProgress(0);

        tvKpiReal3.setText("R$ 0,00");
        tvKpiMeta3.setText("Meta: R$ 0,00");
        tvKpiPerc3.setText("0% atingido");
        pbKpi3.setProgress(0);

        activeRequests.set(3);
        showLoading("Processando indicadores...");
        
        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        listaVendedores = usuarioDAO.getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND USUARIO_TIPO <> 'D' ORDER BY NOME_USUARIO");
        
        if (listaVendedores == null || listaVendedores.isEmpty()) {
            hideLoading();
            Toast.makeText(context, "Nenhum vendedor encontrado.", Toast.LENGTH_LONG).show();
            return;
        }

        atualizarSubtitulo(listaVendedores.size());

        activeRequests.set(3);
        getMetaEmpresaFirestore();
        getPerformanceSemanal();
        getPerformanceMensal();
        getRankingVolume();
    }

    private void getMetaEmpresaFirestore() {
        if (configuracao == null || configuracao.getCnpj_empresa() == null || configuracao.getCnpj_empresa().trim().isEmpty()) {
            return;
        }

        new FirestoreConexao().getMetaEmpresa(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), "1", new FirestoreConexao.OnMetaRetorno() {
            @Override
            public void onSucesso(MesAno m) {
                if (m != null) {
                    double val = getMetaMesVal(m, mesSelecionado);
                    if (val > 0) {
                        metaEmpresaGlobal = val;
                        runOnUiThread(() -> {
                            if (tvKpiReal2 != null) {
                                tvKpiReal2.setText(MascaraUtil.mascaraRealSimboloD(metaEmpresaGlobal));
                            }
                        });
                    }
                }
            }

            @Override
            public void onFalha(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private double getMetaMesVal(MesAno m, int mesIdx) {
        if (m == null) return 0;
        String val = "";
        switch (mesIdx) {
            case 1: val = m.getJan(); break;
            case 2: val = m.getFev(); break;
            case 3: val = m.getMar(); break;
            case 4: val = m.getAbr(); break;
            case 5: val = m.getMai(); break;
            case 6: val = m.getJun(); break;
            case 7: val = m.getJul(); break;
            case 8: val = m.getAgo(); break;
            case 9: val = m.getSet(); break;
            case 10: val = m.getOut(); break;
            case 11: val = m.getNov(); break;
            case 12: val = m.getDez(); break;
        }
        return cleanMoney(val); // Trata a string de meta do Firestore dividindo os centavos por 100
    }

    private void getVendaDetalhadaGrupos() {
        if (UsuarioHelper.getUsuario() == null) return;
        
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        
        String dataReferencia = getDataReferencia(anoSelecionado, mesSelecionado);
        
        if (Api.url == null || Api.url.isEmpty()) {
            checkRequestFinished();
            return;
        }

        // ID 999 para trazer todos os vendedores agrupados por grupo
        Api.buildRetrofit(false).getSomaTotalRealizadoGrupoPeriodo(999, dataReferencia, cabecalho).enqueue(new Callback<List<MesGrupoMeta>>() {
            @Override
            public void onResponse(Call<List<MesGrupoMeta>> call, Response<List<MesGrupoMeta>> response) {
                if (response.code() == 200 && response.body() != null) {
                    listaVendaDetalhada = response.body();
                }
                checkRequestFinished();
            }

            @Override
            public void onFailure(Call<List<MesGrupoMeta>> call, Throwable t) {
                checkRequestFinished();
            }
        });
    }

    @OnClick({R.id.btnRefreshSemanal, R.id.btnRefreshMensal, R.id.btnRefreshRanking, R.id.btnCalendarioSemanal})
    public void onRefreshClick(View view) {
        int id = view.getId();
        if (id == R.id.btnRefreshSemanal) {
            activeRequests.set(1);
            flipCard(context, lyt2, lyt1, progressBar1, 1);
            getPerformanceSemanal();
        } else if (id == R.id.btnRefreshMensal) {
            activeRequests.set(1);
            flipCard(context, lyt4, lyt3, progressBar2, 1);
            getPerformanceMensal();
        } else if (id == R.id.btnRefreshRanking) {
            activeRequests.set(1);
            flipCard(context, lyt6, lyt5, progressBar3, 1);
            getRankingVolume();
        } else if (id == R.id.btnCalendarioSemanal) {
            exibirCalendario();
        }
    }

    private void exibirCalendario() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            // Apenas para visualização, como na tela de metas
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle("Calendário de Referência");
        dialog.show();
    }

    private void getPerformanceSemanal() {
        if (listaVendedores == null || listaVendedores.isEmpty()) return;
        
        mapaMetasSemanais.clear();

        /* CÓDIGO ANTERIOR (Busca individual com loop for em cada vendedor - N requisições lentas):
        AtomicInteger processados = new AtomicInteger(0);
        for (Usuario usuario : listaVendedores) {
            new FirestoreConexao().getMetaSemanal(
                    configuracao.getCnpj_empresa(),
                    // String.valueOf(anoAtual), // Comentado conforme solicitado
                    String.valueOf(anoSelecionado),
                    usuario.getId_quando_vendedor(),
                    // String.valueOf(mesAtual), // Comentado conforme solicitado
                    String.valueOf(mesSelecionado),
                    new FirestoreConexao.OnMetaSemanalRetorno() {
                        @Override
                        public void onSucesso(MetaSemanal metaSemanal) {
                            if (metaSemanal != null) mapaMetasSemanais.put(usuario.getId_quando_vendedor(), metaSemanal);
                            if (processados.incrementAndGet() == listaVendedores.size()) buscarRealizadoSemanalAPI();
                        }

                        @Override
                        public void onFalha(Exception e) {
                            if (processados.incrementAndGet() == listaVendedores.size()) buscarRealizadoSemanalAPI();
                        }
                    }
            );
        }
        */

        // NOVO: Busca ultra rápida em lote via CollectionGroup Query (1 única requisição para todos os vendedores)
        com.google.firebase.firestore.FirebaseFirestore fdb = com.google.firebase.firestore.FirebaseFirestore.getInstance();
        String colecaoAlvo = "META-MES-ANO-" + anoSelecionado + "-SEMANA";
        String idMesStr = String.valueOf(mesSelecionado);

        fdb.collectionGroup(idMesStr)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        for (com.google.firebase.firestore.QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            try {
                                String path = doc.getReference().getPath();
                                // Filtra para garantir que pertence ao CNPJ da empresa e à coleção do ano correto
                                if (configuracao != null && configuracao.getCnpj_empresa() != null
                                        && path.contains(configuracao.getCnpj_empresa())
                                        && path.contains(colecaoAlvo)) {

                                    // Estrutura: {cnpj}/TBL_WEB_USARIO_META/{idVendedor}/META-MES-ANO-{ano}-SEMANA/{idMes}/{idMes}
                                    com.google.firebase.firestore.CollectionReference colVendedor = doc.getReference().getParent().getParent().getParent();
                                    if (colVendedor != null) {
                                        String idVend = colVendedor.getId();
                                        MetaSemanal metaSemanal = doc.toObject(MetaSemanal.class);
                                        if (metaSemanal != null && idVend != null && !idVend.trim().isEmpty()) {
                                            mapaMetasSemanais.put(idVend.trim(), metaSemanal);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    buscarRealizadoSemanalAPI();
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    buscarRealizadoSemanalAPI();
                });
    }

    private void buscarRealizadoSemanalAPI() {
        if (UsuarioHelper.getUsuario() == null) return;
        if (Api.url == null || Api.url.isEmpty()) {
            checkRequestFinished();
            barChartSemanal.setNoDataText("Configuração de API pendente.");
            barChartSemanal.invalidate();
            return;
        }

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        
        // String dataInicio = getPrimeiroDiaMesAtual(); // Comentado conforme solicitado
        String dataInicio = getDataReferencia(anoSelecionado, mesSelecionado);
        
        Api.buildRetrofit(false).getSomaTotalRealizadoSemanaPeriodo(999, dataInicio, cabecalho).enqueue(new Callback<List<MesSemana>>() {
            @Override
            public void onResponse(Call<List<MesSemana>> call, Response<List<MesSemana>> response) {
                if (response.code() == 200 && response.body() != null) {
                    processarResultadoSemanal(response.body());
                } else {
                    checkRequestFinished();
                    barChartSemanal.setNoDataText("Sem dados na API (999).");
                    barChartSemanal.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<MesSemana>> call, Throwable t) {
                checkRequestFinished();
                barChartSemanal.setNoDataText("Falha na conexão.");
                barChartSemanal.invalidate();
            }
        });
    }

    private void processarResultadoSemanal(List<MesSemana> apiData) {
        /* LÓGICA ANTERIOR (RANKING POR VENDEDOR NA SEMANA ATUAL)
        class ItemPerformance {
            String nome;
            float meta;
            float real;
            int idUsuario;
            ItemPerformance(String n, float m, float r, int id) { 
                this.nome = n; this.meta = m; this.real = r; this.idUsuario = id;
            }
        }

        List<ItemPerformance> listaRanking = new ArrayList<>();
        double totalRealEq = 0;
        double totalMetaEq = 0;
        
        for (Usuario v : listaVendedores) {
            String idQuandoVendedor = v.getId_quando_vendedor() != null ? v.getId_quando_vendedor().trim() : "";
            
            if (mapaMetasSemanais.containsKey(idQuandoVendedor)) {
                MetaSemanal metaObj = mapaMetasSemanais.get(idQuandoVendedor);
                float valorMeta = getMetaPorSemana(metaObj, semanaAtual);
                
                if (valorMeta <= 0) continue;
                
                float valorReal = 0;
                for (MesSemana ms : apiData) {
                    String idApi = ms.getId_vendedor() != null ? ms.getId_vendedor().trim() : "";
                    String semanaApiLimpa = ms.getNome_semana() != null ? ms.getNome_semana().replaceAll("[^0-9]", "") : "";

                    if (idQuandoVendedor.equals(idApi) && String.valueOf(semanaAtual).equals(semanaApiLimpa)) {
                        valorReal = cleanMoney(ms.getVenda_liquida());
                        break;
                    }
                }
                
                totalRealEq += valorReal;
                totalMetaEq += valorMeta;
                listaRanking.add(new ItemPerformance(v.getNome_usuario(), valorMeta, valorReal, Integer.parseInt(v.getId_usuario())));
            }
        }
        ... FIM DA LÓGICA COMENTADA */

        // NOVA LÓGICA: EVOLUÇÃO DA EQUIPE (SOMA DE TODOS POR SEMANA)
        double[] metasEquipeSemanas = new double[6];
        double[] realizadoEquipeSemanas = new double[6];
        double totalDevolucaoMes = 0;
        int qtdDevolucoesMes = 0;

        // 1. Somar as metas de todos os vendedores para cada semana
        for (MetaSemanal metaObj : mapaMetasSemanais.values()) {
            metasEquipeSemanas[0] += cleanMoney(metaObj.getSemana1());
            metasEquipeSemanas[1] += cleanMoney(metaObj.getSemana2());
            metasEquipeSemanas[2] += cleanMoney(metaObj.getSemana3());
            metasEquipeSemanas[3] += cleanMoney(metaObj.getSemana4());
            metasEquipeSemanas[4] += cleanMoney(metaObj.getSemana5());
            metasEquipeSemanas[5] += cleanMoney(metaObj.getSemana6());
        }

        // 2. Somar o realizado da API agrupado por semana (Abatendo Devoluções "E")
        for (MesSemana ms : apiData) {
            String semLimpa = ms.getNome_semana() != null ? ms.getNome_semana().replaceAll("[^0-9]", "") : "0";
            try {
                int numSemana = Integer.parseInt(semLimpa);
                if (numSemana >= 1 && numSemana <= 6) {
                    // Parse robusto para evitar divisão por 100 (regra do Firestore)
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
                    float valorFinal = (float) Math.abs(valorNum);

                    /* CÓDIGO ANTERIOR:
                    if (isDevolucao) {
                        realizadoEquipeSemanas[numSemana - 1] -= valorFinal;
                        totalDevolucaoMes += valorFinal;
                        
                        try {
                            qtdDevolucoesMes += Integer.parseInt(ms.getQtde_pedidos().trim());
                        } catch (NumberFormatException e) {
                        } catch (Exception e) {}
                    } else {
                        realizadoEquipeSemanas[numSemana - 1] += valorFinal;
                    }
                    */

                    if (isDevolucao) {
                        totalDevolucaoMes += valorFinal;

                        // Abate do saldo semanal Apenas se a relação comercial for 'S'
                        if (relComercialSim) {
                            realizadoEquipeSemanas[numSemana - 1] -= valorFinal;
                        }

                        try {
                            if (ms.getQtde_pedidos() != null && !ms.getQtde_pedidos().trim().isEmpty()) {
                                qtdDevolucoesMes += Integer.parseInt(ms.getQtde_pedidos().trim());
                            }
                        } catch (Exception ignored) {}
                    } else {
                        realizadoEquipeSemanas[numSemana - 1] += valorFinal;
                    }
                    
                    android.util.Log.d("RCK_EQUIPE", "Semana: " + numSemana + " | Op: " + (isDevolucao ? "E" : "S") + " | Val: " + valorFinal + " | Acumulado: " + realizadoEquipeSemanas[numSemana - 1]);
                }
            } catch (Exception ignored) {}
        }

        ArrayList<BarEntry> entriesMeta = new ArrayList<>();
        ArrayList<BarEntry> entriesReal = new ArrayList<>();
        List<GradientColor> colorsReal = new ArrayList<>();
        List<GradientColor> colorsMeta = new ArrayList<>();
        String[] labels = {"1ª Sem.", "2ª Sem.", "3ª Sem.", "4ª Sem.", "5ª Sem.", "6ª Sem."};

        double totalRealMes = 0;
        double totalMetaMes = 0;

        for (int i = 0; i < 6; i++) {
            float vM = (float) metasEquipeSemanas[i];
            float vR = (float) realizadoEquipeSemanas[i];
            
            if (vM <= 0 && vR <= 0) continue;

            // Lógica de Transparência
            int alphaReal = (vR <= 0 && vM > 0) ? 35 : 255;
            int alphaMeta = (vM <= 0 && vR > 0) ? 35 : 255;

            colorsReal.add(getSemanticGradient(vR, vM, alphaReal));
            colorsMeta.add(new GradientColor(
                    adjustAlpha(ColorTemplateApp.GRADIENT_START_6[2], alphaMeta), 
                    adjustAlpha(ColorTemplateApp.GRADIENT_END_6[2], alphaMeta)
            ));

            entriesReal.add(new BarEntry(i, vR <= 0 && vM > 0 ? vM : vR, vR <= 0 ? "HIDELABEL" : null));
            entriesMeta.add(new BarEntry(i, vM <= 0 && vR > 0 ? vR : vM, vM <= 0 ? "HIDELABEL" : null));
            
            totalRealMes += vR;
            totalMetaMes += vM;
        }

        final double fTotalRealEqMes = totalRealMes;
        final double fTotalMetaEqMes = totalMetaMes;
        final double fTotalDevolucaoMes = totalDevolucaoMes;
        final int fQtdDevolucoesMes = qtdDevolucoesMes;

        runOnUiThread(() -> {
            checkRequestFinished();
            
            // CARD 1: CONSOLIDADO EQUIPE (MÊS)
            tvKpiReal1.setText(MascaraUtil.mascaraRealSimboloD(fTotalRealEqMes));
            tvKpiMeta1.setText("Meta Equipe: " + MascaraUtil.mascaraRealSimboloD(fTotalMetaEqMes));
            if (fTotalMetaEqMes > 0) {
                int perc = (int) ((fTotalRealEqMes / fTotalMetaEqMes) * 100);
                tvKpiPerc1.setText(perc + "% atingido");
                pbKpi1.setProgress(perc);
            }

            // CARD 3: META EMPRESA (REALIZADO EQUIPE VS META DINÂMICA FIRESTORE)
            /* CÓDIGO ANTERIOR:
            double metaEmpresa = 3450000.00;
            */
            double metaEmpresa = metaEmpresaGlobal;
            tvKpiReal2.setText(MascaraUtil.mascaraRealSimboloD(metaEmpresa));
            tvKpiMeta2.setText("Realizado Equipe: " + MascaraUtil.mascaraRealSimboloD(fTotalRealEqMes));
            if (metaEmpresa > 0) {
                double percPart = (fTotalRealEqMes / metaEmpresa) * 100;
                tvKpiPerc2.setText(String.format(java.util.Locale.US, "%.2f%% da empresa", percPart));
                pbKpi2.setProgress((int) Math.min(percPart, 100));
            }

            // CARD DEVOLUÇÕES (NOVO)
            tvKpiTituloDevolucao.setText("DEVOLUÇÕES (" + fQtdDevolucoesMes + ")");
            tvKpiRealDevolucao.setText(MascaraUtil.mascaraRealSimboloD(fTotalDevolucaoMes));
            if (fTotalRealEqMes > 0) {
                double percDev = (fTotalDevolucaoMes / (fTotalRealEqMes + fTotalDevolucaoMes)) * 100;
                tvKpiPercDevolucao.setText(String.format(java.util.Locale.US, "%.2f%% do faturamento", percDev));
                pbKpiDevolucao.setProgress((int) Math.min(percDev, 100));
            } else {
                tvKpiPercDevolucao.setText("0.00% do faturamento");
                pbKpiDevolucao.setProgress(0);
            }

            // CARD 2: GRUPOS (FICTÍCIO CONFORME SOLICITADO)
            tvKpiReal3.setText(MascaraUtil.mascaraRealSimboloD(fTotalRealEqMes * 0.45)); // Exemplo: 45% do faturamento é um grupo
            tvKpiMeta3.setText("Meta: R$ 1.500.000,00");
            tvKpiPerc3.setText("42% atingido");
            pbKpi3.setProgress(42);

            flipCard(context, lyt1, lyt2, progressBar1, 1);
            if (entriesReal.isEmpty()) {
                barChartSemanal.setNoDataText("Sem faturamento para este período.");
                barChartSemanal.setData(null);
            } else {
                desenharGraficoSemanal(entriesReal, entriesMeta, colorsReal, colorsMeta, labels);
            }
            barChartSemanal.invalidate();
        });
    }

    private void desenharGraficoSemanal(ArrayList<BarEntry> entriesReal, ArrayList<BarEntry> entriesMeta, List<GradientColor> colorsReal, List<GradientColor> colorsMeta, String[] labels) {
        BarDataSet setReal = new BarDataSet(entriesReal, "Real");
        BarDataSet setMeta = new BarDataSet(entriesMeta, "Meta");

        setReal.setGradientColors(colorsReal);
        setMeta.setGradientColors(colorsMeta);
        
        // ATIVADO: Necessário para o Drill-down funcionar
        setReal.setHighlightEnabled(true);
        setMeta.setHighlightEnabled(true);
        // Remove o efeito cinza de seleção para manter o visual limpo
        setReal.setHighLightColor(Color.TRANSPARENT);
        setMeta.setHighLightColor(Color.TRANSPARENT);

        BarData data = new BarData(setReal, setMeta);
        data.setValueTextSize(9f);
        data.setValueFormatter(new CustomValueFormatter());
        data.setBarWidth(0.35f);

        barChartSemanal.setData(data);
        barChartSemanal.setOnChartValueSelectedListener(this);
        configureChart(barChartSemanal, labels, -45f);
        barChartSemanal.groupBars(0f, 0.20f, 0.05f);
        barChartSemanal.invalidate();
        barChartSemanal.animateXY(1200, 1200);
    }

    private void configureChart(BarChart chart, String[] labels, float rotation) {
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false); 
        chart.setDoubleTapToZoomEnabled(false); // DESATIVADO: Evita conflito com o Drill-down
        chart.setDrawGridBackground(false);
        chart.setDragEnabled(true); 
        chart.setScaleYEnabled(false);
        
        XAxis xAxis = chart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true); // Mantém a linha horizontal do eixo X
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelRotationAngle(rotation);
        xAxis.setLabelCount(labels.length);
        
        chart.setExtraBottomOffset(0f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                int idx = (int) v;
                return (idx >= 0 && idx < labels.length) ? labels[idx] : "";
            }
        });

        // LÓGICA DE ROLAGEM RESPONSIVA (Smartphone vs Tablet)
        int smallestWidth = getResources().getConfiguration().smallestScreenWidthDp;
        float maxVisivel;
        
        if (smallestWidth >= 600) {
            // TABLET: Se tiver mais de 10, permite scroll
            maxVisivel = 10f;
        } else {
            // CELULAR: Se tiver mais de 5, permite scroll
            maxVisivel = 5f;
        }

        if (labels.length > maxVisivel) {
            chart.setVisibleXRangeMaximum(maxVisivel);
            // Sinalização de scroll
            if (chart.getId() == R.id.barChart1) tvScrollSemanal.setVisibility(View.VISIBLE);
            else if (chart.getId() == R.id.barChart2) tvScrollMensal.setVisibility(View.VISIBLE);
            else if (chart.getId() == R.id.barChart3) tvScrollRanking.setVisibility(View.VISIBLE);
        } else {
            if (chart.getId() == R.id.barChart1) tvScrollSemanal.setVisibility(View.GONE);
            else if (chart.getId() == R.id.barChart2) tvScrollMensal.setVisibility(View.GONE);
            else if (chart.getId() == R.id.barChart3) tvScrollRanking.setVisibility(View.GONE);
        }

        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false);
        left.setDrawGridLines(false);
        left.setDrawAxisLine(false); // Remove a linha vertical do lado esquerdo (Eixo Y)
        left.setAxisMinimum(0f);
        left.setSpaceTop(15f);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        
        chart.getXAxis().setAxisMinimum(0f);
        chart.getXAxis().setAxisMaximum(labels.length);
    }

    private float getMetaPorSemana(MetaSemanal m, int semana) {
        if (m == null) return 0f;
        switch (semana) {
            case 1: return cleanMoney(m.getSemana1());
            case 2: return cleanMoney(m.getSemana2());
            case 3: return cleanMoney(m.getSemana3());
            case 4: return cleanMoney(m.getSemana4());
            case 5: return cleanMoney(m.getSemana5());
            case 6: return cleanMoney(m.getSemana6());
            default: return 0f;
        }
    }

    private String getPrimeiroDiaMesAtual() {
        String data = "";
        try (Cursor cursor = db.getReadableDatabase().rawQuery("SELECT date('now', 'start of month')", null)) {
            if (cursor.moveToFirst()) data = cursor.getString(0);
        } catch (Exception e) {
            data = String.format("%04d-%02d-01", Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
        }
        return data;
    }

    private float cleanMoney(String valor) {
        if (valor == null || valor.isEmpty()) return 0f;
        try {
            String v = valor.trim();

            // Caso 1: Formato Brasileiro da API (8.295,95)
            if (v.contains(",")) {
                return Float.parseFloat(v.replace(".", "").replace(",", "."));
            }

            // Caso 2: Já é um float válido (ex: 8295.95 vindo da API)
            // Se houver APENAS UM ponto e ele estiver separando as casas decimais (ex: .95 ou .5)
            if (v.contains(".") && v.indexOf(".") == v.lastIndexOf(".")) {
                String decimalPart = v.substring(v.lastIndexOf(".") + 1);
                // Se for .0 ou .00, costuma ser o Firestore salvando inteiro como float
                if (decimalPart.equals("0") || decimalPart.equals("00")) {
                    return (float) (Double.parseDouble(v.split("\\.")[0]) / 100.0);
                }
                // Se tiver casas decimais reais (como .95), retorna o valor como está
                return Float.parseFloat(v);
            }

            // Caso 3: Valor puro (centavos) ou com múltiplos pontos (ex: 1.900.000 do Firestore)
            String apenasNumeros = v.replace(".", "");
            return (float) (Double.parseDouble(apenasNumeros) / 100.0);

        } catch (Exception e) {
            return 0f;
        }
    }

    private void showLoading(String title) {
        tvGlobalLoadingTitle.setText(title);
        lytGlobalLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        lytGlobalLoading.setVisibility(View.GONE);
    }

    private void atualizarSubtitulo(int count) {
        if (getSupportActionBar() != null) {
            String[] meses = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO",
                    "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
            
            String nomeMes = (mesAtual >= 1 && mesAtual <= 12) ? meses[mesAtual - 1] : "";
            String sub = nomeMes + " DE " + anoAtual;
            
            if (count > 0) {
                sub = /*" - " + */count + " Representantes Analisados";
            }
            getSupportActionBar().setSubtitle(sub);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e.getData() != null && e.getData() instanceof String) {
            String data = (String) e.getData();
            // Remove prefixo de label oculta se existir
            if (data.startsWith("HIDELABEL")) data = data.replace("HIDELABEL", "");
            
            // Separa ID e Nome (ID|NOME ou ID|NOME###...)
            String drillDownStr = data.contains("###") ? data.split("###")[0] : data;
            
            if (drillDownStr.contains("|")) {
                String[] parts = drillDownStr.split("\\|");
                try {
                    int idUsuario = Integer.parseInt(parts[0]);
                    String nomeUsuario = parts[1];

                    if (idUsuario > 0) {
                        Intent intent = new Intent(context, ActivityPainelGraficoFaturamento.class);
                        intent.putExtra("idUsuarioSelecionado", idUsuario);
                        intent.putExtra("nomeUsuarioSelecionado", nomeUsuario);
                        startActivity(intent);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onNothingSelected() {}

    private void checkRequestFinished() {
        if (activeRequests.decrementAndGet() <= 0) {
            runOnUiThread(this::hideLoading);
        }
    }

    public class CustomValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float v) {
            return MascaraUtil.mascaraRealSemSimbolo(v);
        }

        @Override
        public String getBarLabel(BarEntry barEntry) {
            if (barEntry.getData() != null && barEntry.getData() instanceof String) {
                String data = (String) barEntry.getData();
                if (data.startsWith("HIDELABEL")) {
                    return "";
                }
            }
            return super.getBarLabel(barEntry);
        }
    }

    private int adjustAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0x00ffffff);
    }

    private GradientColor getSemanticGradient(float real, float meta, int alpha) {
        float perc = meta > 0 ? (real / meta) * 100 : 0;
        int startColor;
        int endColor;

        if (perc >= 100) {
            // Sucesso (Teal)
            startColor = ColorTemplateApp.GRADIENT_START_6[0];
            endColor = ColorTemplateApp.GRADIENT_END_6[0];
        } else if (perc >= 80) {
            // Atenção (Laranja)
            startColor = ColorTemplateApp.GRADIENT_START_6[1];
            endColor = ColorTemplateApp.GRADIENT_END_6[1];
        } else {
            // Perigo (Vermelho/Rosa Forte)
            startColor = ColorTemplateApp.GRADIENT_START_6[5];
            endColor = ColorTemplateApp.GRADIENT_END_6[5];
        }

        return new GradientColor(adjustAlpha(startColor, alpha), adjustAlpha(endColor, alpha));
    }

    private String abreviarNome(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.isEmpty()) return "";
        String[] partes = nomeCompleto.trim().split("\\s+");
        if (partes.length > 1) {
            return partes[0] + " " + partes[1].substring(0, 1).toUpperCase() + ".";
        }
        return partes[0];
    }

    private void getPerformanceMensal() {
        if (listaVendedores == null || listaVendedores.isEmpty()) return;

        AtomicInteger processados = new AtomicInteger(0);
        mapaMetasMensais.clear();

        for (Usuario usuario : listaVendedores) {
            new FirestoreConexao().getMetaMesAno(
                    configuracao.getCnpj_empresa(),
                    // String.valueOf(anoAtual), // Comentado conforme solicitado
                    String.valueOf(anoSelecionado),
                    usuario.getId_quando_vendedor(),
                    new FirestoreConexao.OnMetaRetorno() {
                        @Override
                        public void onSucesso(MesAno mesAno) {
                            if (mesAno != null) mapaMetasMensais.put(usuario.getId_quando_vendedor(), mesAno);
                            if (processados.incrementAndGet() == listaVendedores.size()) {
                                // CODIGO ANTERIOR: buscarRealizadoMensalAPI();
                                buscarRealizadoMensalAPI("999", "1", null); // Busca apenas o mês atual para os gráficos
                            }
                        }

                        @Override
                        public void onFalha(Exception e) {
                            if (processados.incrementAndGet() == listaVendedores.size()) {
                                // CODIGO ANTERIOR: buscarRealizadoMensalAPI();
                                buscarRealizadoMensalAPI("999", "1", null);
                            }
                        }
                    }
            );
        }
    }

    private void buscarRealizadoMensalAPI(String idUsario, String qtdePeriodo, Callback<List<MesAnoMeta>> externalCallback) {
        if (UsuarioHelper.getUsuario() == null) return;
        if (Api.url == null || Api.url.isEmpty()) {
            if (externalCallback == null) {
                checkRequestFinished();
                barChartMensal.setNoDataText("Configuração de API pendente.");
                barChartMensal.invalidate();
            }
            return;
        }

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        String dataInicio = getDataReferencia(anoSelecionado, mesSelecionado);

        Api.buildRetrofit(false).getSomaTotalRealizadoMesPeriodo(Integer.parseInt(idUsario), dataInicio, qtdePeriodo, cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (externalCallback != null) {
                    externalCallback.onResponse(call, response);
                } else {
                    // Comportamento padrão para os gráficos da tela
                    if (response.code() == 200 && response.body() != null) {
                        processarResultadoMensal(response.body());
                    } else {
                        checkRequestFinished();
                        barChartMensal.setNoDataText("Sem dados mensais na API.");
                        barChartMensal.invalidate();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) {
                if (externalCallback != null) {
                    externalCallback.onFailure(call, t);
                } else {
                    checkRequestFinished();
                    barChartMensal.setNoDataText("Falha na conexão.");
                    barChartMensal.invalidate();
                }
            }
        });
    }

    private void processarResultadoMensal(List<MesAnoMeta> apiData) {
        List<ItemPerformanceVendedor> listaRanking = new ArrayList<>();
        double totalRealEq = 0;
        double totalMetaEq = 0;

        // Agrupamento numérico por Vendedor para evitar erros de re-parsing e duplicidade
        Map<String, MesAnoMeta> mapaAgrupado = new java.util.LinkedHashMap<>();
        Map<String, Double> mapaValoresLiquidos = new java.util.HashMap<>();

        for (MesAnoMeta ma : apiData) {
            String idV = ma.getId_vendedor() != null ? ma.getId_vendedor().trim() : null;
            if (idV == null) continue;

            double valorItem = parseMoneyAPI(ma.getVenda_liquida());
            boolean isDevolucao = ma.getE_entrada_s_saida() != null && ma.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
            boolean relComercialSim = ma.getFigura_rel_comercial() != null && ma.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

            /* CÓDIGO ANTERIOR:
            double valorFinalItem = isDevolucao ? -Math.abs(valorItem) : Math.abs(valorItem);
            */

            double valorFinalItem = 0;
            if (isDevolucao) {
                // Abate do saldo mensal do vendedor Apenas se a relação comercial for 'S'
                if (relComercialSim) {
                    valorFinalItem = -Math.abs(valorItem);
                } else {
                    valorFinalItem = 0;
                }
            } else {
                valorFinalItem = Math.abs(valorItem);
            }

            if (!mapaValoresLiquidos.containsKey(idV)) {
                mapaValoresLiquidos.put(idV, valorFinalItem);
                mapaAgrupado.put(idV, ma);
            } else {
                mapaValoresLiquidos.put(idV, mapaValoresLiquidos.get(idV) + valorFinalItem);
            }
        }

        // Processa a lista já consolidada numericamente
        for (String idVendedorApi : mapaAgrupado.keySet()) {
            MesAnoMeta ma = mapaAgrupado.get(idVendedorApi);
            double valorReal = mapaValoresLiquidos.get(idVendedorApi);

            Usuario vendedorLocal = null;
            for (Usuario v : listaVendedores) {
                String idLocal = v.getId_quando_vendedor() != null ? v.getId_quando_vendedor().trim() : "";
                if (idVendedorApi.equals(idLocal)) {
                    vendedorLocal = v;
                    break;
                }
            }

            float valorMeta = 0;
            if (mapaMetasMensais.containsKey(idVendedorApi)) {
                valorMeta = getMetaDoMes(mapaMetasMensais.get(idVendedorApi), mesSelecionado);
            }

            totalRealEq += valorReal;
            totalMetaEq += valorMeta;

            // PRIORIDADE DE NOME: Local App > Nome Vendedor API > Nome Cadastro API
            String nomeParaRelatorio = ma.getNome_vendedor();
            if (vendedorLocal != null) {
                nomeParaRelatorio = vendedorLocal.getNome_usuario();
            } else if (nomeParaRelatorio == null || nomeParaRelatorio.isEmpty()) {
                nomeParaRelatorio = ma.getNome_cadastro();
            }
            
            if (nomeParaRelatorio == null || nomeParaRelatorio.isEmpty()) {
                nomeParaRelatorio = "VENDEDOR " + idVendedorApi;
            }

            int idInternoApp = vendedorLocal != null ? Integer.parseInt(vendedorLocal.getId_usuario()) : 0;

            listaRanking.add(new ItemPerformanceVendedor(nomeParaRelatorio, valorMeta, (float) valorReal, idInternoApp, idVendedorApi));
        }

        // 2. ADICIONA VENDEDORES QUE TÊM META MAS NÃO TIVERAM VENDA (FICAM NO FINAL)
        for (Usuario v : listaVendedores) {
            String idV = v.getId_quando_vendedor() != null ? v.getId_quando_vendedor().trim() : "";
            if (idV.isEmpty()) continue;
            
            boolean jaEstaNaLista = false;
            for (ItemPerformanceVendedor item : listaRanking) {
                if (idV.equals(item.idQuandoVendedor)) {
                    jaEstaNaLista = true;
                    break;
                }
            }

            if (!jaEstaNaLista && mapaMetasMensais.containsKey(idV)) {
                float valorMeta = getMetaDoMes(mapaMetasMensais.get(idV), mesSelecionado);
                if (valorMeta > 0) {
                    totalMetaEq += valorMeta;
                    listaRanking.add(new ItemPerformanceVendedor(
                            v.getNome_usuario(), 
                            valorMeta, 
                            0f, 
                            Integer.parseInt(v.getId_usuario()), 
                            idV
                    ));
                }
            }
        }

        // REMOVIDO: Collections.sort manual para obedecer a ordem vinda da API
        
        // Salva para exportação Excel respeitando a ordem processada
        this.listaParaExportacao = new ArrayList<>(listaRanking);

        ArrayList<BarEntry> entriesMeta = new ArrayList<>();
        ArrayList<BarEntry> entriesReal = new ArrayList<>();
        List<GradientColor> colorsReal = new ArrayList<>();
        List<GradientColor> colorsMeta = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < listaRanking.size(); i++) {
            ItemPerformanceVendedor item = listaRanking.get(i);
            labels.add(abreviarNome(item.nome));

            float vR = item.real;
            float vM = item.meta;

            // Lógica de Transparência
            int alphaReal = (vR <= 0 && vM > 0) ? 35 : 255;
            int alphaMeta = (vM <= 0 && vR > 0) ? 35 : 255;

            // Cores Semafóricas para o Realizado
            colorsReal.add(getSemanticGradient(vR, vM, alphaReal));

            // Meta com cor fixa azulada
            colorsMeta.add(new GradientColor(
                    adjustAlpha(ColorTemplateApp.GRADIENT_START_6[2], alphaMeta), 
                    adjustAlpha(ColorTemplateApp.GRADIENT_END_6[2], alphaMeta)
            ));

            String drillDownInfo = item.idUsuario + "|" + item.nome;

            // CORREÇÃO: Não troca o valor vR por vM. Mantém o real líquido para que o abatimento seja visível.
            entriesReal.add(new BarEntry(i, vR, vR <= 0 ? "HIDELABEL" + drillDownInfo : drillDownInfo));
            entriesMeta.add(new BarEntry(i, vM, vM <= 0 ? "HIDELABEL" + drillDownInfo : drillDownInfo));
        }

        final double fTotalReal = totalRealEq;
        final double fTotalMeta = totalMetaEq;

        runOnUiThread(() -> {
            checkRequestFinished();
            tvKpiReal2.setText(MascaraUtil.mascaraRealSimboloD(fTotalReal));
            if (fTotalMeta > 0) {
                int perc = (int) ((fTotalReal / fTotalMeta) * 100);
                tvKpiPerc2.setText(perc + "% da meta equipe");
            } else {
                tvKpiPerc2.setText("0% da meta equipe");
            }

            // Sempre executa o flip para esconder o progresso, com ou sem dados
            flipCard(context, lyt3, lyt4, progressBar2, 1);

            if (entriesReal.isEmpty()) {
                barChartMensal.setNoDataText("Sem dados mensais para esta equipe.");
                barChartMensal.setData(null);
            } else {
                desenharGraficoMensal(entriesReal, entriesMeta, colorsReal, colorsMeta, labels.toArray(new String[0]));
            }
            barChartMensal.invalidate();
        });
    }

    private void desenharGraficoMensal(ArrayList<BarEntry> entriesReal, ArrayList<BarEntry> entriesMeta, List<GradientColor> colorsReal, List<GradientColor> colorsMeta, String[] labels) {
        BarDataSet setReal = new BarDataSet(entriesReal, "Real");
        BarDataSet setMeta = new BarDataSet(entriesMeta, "Meta");

        setReal.setGradientColors(colorsReal);
        setMeta.setGradientColors(colorsMeta);
        
        // ATIVADO: Necessário para o Drill-down funcionar
        setReal.setHighlightEnabled(true);
        setMeta.setHighlightEnabled(true);
        // Remove o efeito cinza de seleção
        setReal.setHighLightColor(Color.TRANSPARENT);
        setMeta.setHighLightColor(Color.TRANSPARENT);

        BarData data = new BarData(setReal, setMeta);
        data.setValueTextSize(9f);
        data.setValueFormatter(new CustomValueFormatter());
        data.setBarWidth(0.35f);

        barChartMensal.setData(data);
        barChartMensal.setOnChartValueSelectedListener(this);
        configureChart(barChartMensal, labels, -45f);
        barChartMensal.groupBars(0f, 0.20f, 0.05f);
        barChartMensal.invalidate();
        barChartMensal.animateXY(1200, 1200);
    }

    private float getMetaDoMes(MesAno m, int mesIdx) {
        if (m == null) return 0f;
        switch (mesIdx) {
            case 1: return cleanMoney(m.getJan());
            case 2: return cleanMoney(m.getFev());
            case 3: return cleanMoney(m.getMar());
            case 4: return cleanMoney(m.getAbr());
            case 5: return cleanMoney(m.getMai());
            case 6: return cleanMoney(m.getJun());
            case 7: return cleanMoney(m.getJul());
            case 8: return cleanMoney(m.getAgo());
            case 9: return cleanMoney(m.getSet());
            case 10: return cleanMoney(m.getOut());
            case 11: return cleanMoney(m.getNov());
            case 12: return cleanMoney(m.getDez());
            default: return 0f;
        }
    }

    private void getRankingVolume() {
        if (listaVendedores == null || listaVendedores.isEmpty()) return;

        AtomicInteger processados = new AtomicInteger(0);
        for (Usuario usuario : listaVendedores) {
            new FirestoreConexao().getMetaMesAno(
                    configuracao.getCnpj_empresa(),
                    // String.valueOf(anoAtual),
                    String.valueOf(anoSelecionado),
                    usuario.getId_quando_vendedor(),
                    new FirestoreConexao.OnMetaRetorno() {
                        @Override
                        public void onSucesso(MesAno mesAno) {
                            if (mesAno != null) mapaMetasMensais.put(usuario.getId_quando_vendedor(), mesAno);
                            if (processados.incrementAndGet() == listaVendedores.size()) buscarRankingAPI();
                        }

                        @Override
                        public void onFalha(Exception e) {
                            if (processados.incrementAndGet() == listaVendedores.size()) buscarRankingAPI();
                        }
                    }
            );
        }
    }

    private void buscarRankingAPI() {
        if (UsuarioHelper.getUsuario() == null) return;
        if (Api.url == null || Api.url.isEmpty()) {
            checkRequestFinished();
            barChartRanking.setNoDataText("Configuração de API pendente.");
            barChartRanking.invalidate();
            return;
        }

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        // String dataInicio = getPrimeiroDiaMesAtual();
        String dataInicio = getDataReferencia(anoSelecionado, mesSelecionado);
        Api.buildRetrofit(false).getSomaTotalRealizadoMesPeriodo(999, dataInicio, "1", cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (response.code() == 200 && response.body() != null) {
                    processarRankingVolume(response.body());
                } else {
                    checkRequestFinished();
                    barChartRanking.setNoDataText("Sem dados de ranking na API.");
                    barChartRanking.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) {
                checkRequestFinished();
                barChartRanking.setNoDataText("Falha na conexão.");
                barChartRanking.invalidate();
            }
        });
    }

    private void processarRankingVolume(List<MesAnoMeta> apiData) {
        class ItemRanking {
            String nome;
            float real;
            int idUsuario;
            ItemRanking(String n, float r, int id) { 
                this.nome = n; this.real = r; this.idUsuario = id;
            }
        }

        // Agrupamento numérico robusto para abater devoluções ("E") no ranking
        Map<String, ItemRanking> mapaConsolidado = new java.util.HashMap<>();
        for (MesAnoMeta ma : apiData) {
            String idVendedorApi = ma.getId_vendedor();
            if (idVendedorApi == null) continue;

            double valorItem = 0;
            try {
                String v = ma.getVenda_liquida().trim();
                if (v.contains(",")) {
                    valorItem = Double.parseDouble(v.replace(".", "").replace(",", "."));
                } else {
                    valorItem = Double.parseDouble(v);
                }
            } catch (Exception e) { valorItem = 0; }

            boolean isDevolucao = ma.getE_entrada_s_saida() != null && ma.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
            boolean relComercialSim = ma.getFigura_rel_comercial() != null && ma.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

            /* CÓDIGO ANTERIOR:
            float valorFinalItem = (float) (isDevolucao ? -Math.abs(valorItem) : Math.abs(valorItem));
            */

            float valorFinalItem = 0;
            if (isDevolucao) {
                // Abate do saldo do vendedor no ranking Apenas se a relação comercial for 'S'
                if (relComercialSim) {
                    valorFinalItem = (float) -Math.abs(valorItem);
                } else {
                    valorFinalItem = 0f;
                }
            } else {
                valorFinalItem = (float) Math.abs(valorItem);
            }

            if (!mapaConsolidado.containsKey(idVendedorApi)) {
                String nomeVendedor = ma.getNome_cadastro();
                int idUsuarioVendedor = 0;
                for (Usuario v : listaVendedores) {
                    if (idVendedorApi.trim().equals(v.getId_quando_vendedor())) {
                        nomeVendedor = v.getNome_usuario();
                        idUsuarioVendedor = Integer.parseInt(v.getId_usuario());
                        break;
                    }
                }
                mapaConsolidado.put(idVendedorApi, new ItemRanking(nomeVendedor, valorFinalItem, idUsuarioVendedor));
            } else {
                mapaConsolidado.get(idVendedorApi).real += valorFinalItem;
            }
        }

        List<ItemRanking> listaTotal = new ArrayList<>();
        double totalGeral = 0;

        for (ItemRanking item : mapaConsolidado.values()) {
            if (item.real != 0) { // Agora permite valores negativos se devoluções superarem vendas
                totalGeral += item.real;
                listaTotal.add(item);
            }
        }

        Collections.sort(listaTotal, (o1, o2) -> Float.compare(o2.real, o1.real));

        int smallestWidth = getResources().getConfiguration().smallestScreenWidthDp;
        int limiteTop = smallestWidth >= 600 ? 10 : 5;
        
        List<ItemRanking> listaFinal = new ArrayList<>();
        float valorOutros = 0f;
        
        for (int i = 0; i < listaTotal.size(); i++) {
            if (i < limiteTop) {
                listaFinal.add(listaTotal.get(i));
            } else {
                valorOutros += listaTotal.get(i).real;
            }
        }

        if (valorOutros > 0) {
            listaFinal.add(new ItemRanking("OUTROS", valorOutros, 0));
        }

        Collections.reverse(listaFinal);

        ArrayList<BarEntry> entries = new ArrayList<>();
        final double fTotalGeral = totalGeral;

        for (int i = 0; i < listaFinal.size(); i++) {
            ItemRanking item = listaFinal.get(i);
            String nomeLimpo = item.nome.replace("#", " ").replace("|", " "); 
            double percentual = fTotalGeral > 0 ? (item.real / fTotalGeral) * 100 : 0;
            
            String drillDownInfo = item.idUsuario + "|" + item.nome;
            entries.add(new BarEntry(i, item.real, drillDownInfo + "###" + nomeLimpo + "###" + MascaraUtil.duasCasaDecimal(String.valueOf(percentual))));
        }

        runOnUiThread(() -> {
            checkRequestFinished();
            
            // Sempre executa o flip para esconder o progresso, com ou sem dados
            flipCard(context, lyt5, lyt6, progressBar3, 1);

            if (entries.isEmpty()) {
                barChartRanking.setNoDataText("Sem faturamento para o ranking.");
                barChartRanking.setData(null);
            } else {
                BarDataSet dataSet = new BarDataSet(entries, "");
                dataSet.setColors(limiteTop == 5 ? ColorTemplateApp.PIE_COLORS_6_RANK : ColorTemplateApp.PIE_COLORS_11_RANK);
                dataSet.setDrawValues(true);
                dataSet.setValueTextSize(9f);
                dataSet.setValueTextColor(Color.BLACK);
                
                // Formatador robusto para garantir a exibição do Nome e Valor
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
                                // O novo formato é ID|NOME###NOME_LIMPO###PERCENTUAL
                                // Então parts[1] tem o nome para exibir
                                String nomeParaExibir = parts[1];
                                if (nomeParaExibir.length() > 20) nomeParaExibir = nomeParaExibir.substring(0, 18) + "..";
                                return nomeParaExibir + " - " + MascaraUtil.mascaraRealSimbolo(barEntry.getY());
                            }
                        } catch (Exception e) { e.printStackTrace(); }
                        return super.getBarLabel(barEntry);
                    }
                });

                BarData data = new BarData(dataSet);
                data.setBarWidth(0.5f);
                barChartRanking.setData(data);
                barChartRanking.setOnChartValueSelectedListener(this);
                
                barChartRanking.getDescription().setEnabled(false);
                barChartRanking.getLegend().setEnabled(false);
                barChartRanking.setDrawGridBackground(false);
                barChartRanking.setDragEnabled(true);
                barChartRanking.setPinchZoom(false);
                barChartRanking.setScaleYEnabled(false);
                
                // --- AJUSTE DE SEGURANÇA: Seta os dados PRIMEIRO, depois o renderer ---
                barChartRanking.setData(data);
                barChartRanking.setRenderer(new CustomHorizontalBarRenderer(barChartRanking, barChartRanking.getAnimator(), barChartRanking.getViewPortHandler()));
                barChartRanking.notifyDataSetChanged();

                XAxis xAxis = barChartRanking.getXAxis();
                xAxis.setEnabled(true);
                xAxis.setDrawLabels(false); // DESATIVADO: Remove os nomes que apareciam antes do gráfico (no eixo)
                xAxis.setDrawAxisLine(false);
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextColor(Color.BLACK);
                xAxis.setTextSize(9f);

                barChartRanking.getAxisLeft().setEnabled(false);
                barChartRanking.getAxisLeft().setAxisMinimum(0f);
                barChartRanking.getAxisLeft().setSpaceMax(40f); // Aumentado para dar mais espaço ao texto
                barChartRanking.getAxisRight().setEnabled(false);

                // Offset lateral reduzido (Texto agora alinhado à esquerda)
                barChartRanking.setExtraOffsets(5, 5, 20, 5);
                barChartRanking.setDrawValueAboveBar(true);

                barChartRanking.setFitBars(true);
                barChartRanking.invalidate();
                barChartRanking.animateY(1200);
            }
            barChartRanking.invalidate();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_painel_grafico_equipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            // NOVO: Dialog para escolher formato de compartilhamento
            DialogHelper.showActionDialog(
                    context,
                    "Como você deseja compartilhar o demonstrativo?",
                    "EXCEL", R.drawable.ic_copia_segura,
                    "GRAFICOS", R.drawable.ic_image_gallery,
                    "CANCELAR", null,
                    new DialogHelper.OnActionSelectedListener() {
                        @Override
                        public void onPositive() {
                            exportarExcel();
                        }

                        @Override
                        public void onNegative() {
                            compartilharDashboard();
                        }

                        @Override
                        public void onNeutral() {
                        }
                    }
            );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportarExcel() {
        if (UsuarioHelper.getUsuario() == null) return;
        
        if (listaParaExportacao == null || listaParaExportacao.isEmpty()) {
            Toast.makeText(context, "Não há dados para exportar.", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading("Processando informações...");

        // Limpa as listas para garantir dados novos
        listaVendaDetalhada.clear();
        listaVendaSegmento.clear();
        listaVendaMensalHistorico.clear();

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        String dataReferencia = getDataReferencia(anoSelecionado, mesSelecionado);

        if (Api.url == null || Api.url.isEmpty()) {
            hideLoading();
            Toast.makeText(context, "Configuração de API inválida.", Toast.LENGTH_SHORT).show();
            return;
        }

        AtomicInteger requestsPending = new AtomicInteger(3); // Aumentado para 3 requisições

        // 1. Busca faturamento por GRUPO (Aba Venda Detalhada)
        Api.buildRetrofit(false).getSomaTotalRealizadoGrupoPeriodo(999, dataReferencia, cabecalho).enqueue(new Callback<List<MesGrupoMeta>>() {
            @Override
            public void onResponse(Call<List<MesGrupoMeta>> call, Response<List<MesGrupoMeta>> response) {
                if (response.code() == 200 && response.body() != null) listaVendaDetalhada = response.body();
                if (requestsPending.decrementAndGet() == 0) buildExcelAndShare();
            }
            @Override
            public void onFailure(Call<List<MesGrupoMeta>> call, Throwable t) {
                if (requestsPending.decrementAndGet() == 0) buildExcelAndShare();
            }
        });

        // 2. Busca faturamento por SEGMENTO (Linha Coleção) para abas individuais
        Api.buildRetrofit(false).getSomaTotalRealizadoLinhaColecaoPeriodo(999, dataReferencia, cabecalho).enqueue(new Callback<List<MesGrupoMeta>>() {
            @Override
            public void onResponse(Call<List<MesGrupoMeta>> call, Response<List<MesGrupoMeta>> response) {
                if (response.code() == 200 && response.body() != null) listaVendaSegmento = response.body();
                if (requestsPending.decrementAndGet() == 0) buildExcelAndShare();
            }
            @Override
            public void onFailure(Call<List<MesGrupoMeta>> call, Throwable t) {
                if (requestsPending.decrementAndGet() == 0) buildExcelAndShare();
            }
        });

        // 3. Busca HISTÓRICO MENSAL (Jan até Mês Selecionado) para as abas de cada vendedor
        buscarRealizadoMensalAPI("0", String.valueOf(mesSelecionado), new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (response.code() == 200 && response.body() != null) listaVendaMensalHistorico = response.body();
                if (requestsPending.decrementAndGet() == 0) buildExcelAndShare();
            }

            @Override
            public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) {
                if (requestsPending.decrementAndGet() == 0) buildExcelAndShare();
            }
        });
    }

    private float parseMoneyAPI(String v) {
        if (v == null || v.isEmpty()) return 0f;
        try {
            v = v.trim();
            if (v.contains(",")) return Float.parseFloat(v.replace(".", "").replace(",", "."));
            return Float.parseFloat(v);
        } catch (Exception e) { return 0f; }
    }

    private void buildExcelAndShare() {
        // 1. CONSOLIDAÇÃO INICIAL: Garante que todos os vendedores da API detalhada existam na lista de exportação
        if (listaVendaDetalhada != null) {
            for (MesGrupoMeta item : listaVendaDetalhada) {
                String idV = item.getId_vendedor() != null ? item.getId_vendedor().trim() : null;
                if (idV == null) continue;

                boolean jaNaLista = false;
                for (ItemPerformanceVendedor ipv : listaParaExportacao) {
                    if (idV.equals(ipv.idQuandoVendedor != null ? ipv.idQuandoVendedor.trim() : "")) {
                        jaNaLista = true;
                        break;
                    }
                }

                if (!jaNaLista) {
                    String nomeV = (item.getNome_vendedor() != null && !item.getNome_vendedor().isEmpty()) ? item.getNome_vendedor() : "";
                    if (nomeV.isEmpty()) nomeV = "VENDEDOR " + idV;
                    listaParaExportacao.add(new ItemPerformanceVendedor(nomeV, 0f, 0f, 0, idV));
                }
            }
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Demonstrativo Vendas");

        // NOVO: Agregação por prefixo de mês (ex: "SET") para suportar formatos como "SET/2026"
        Map<String, Map<String, Float>> mapaHistoricoAgregado = new HashMap<>();
        if (listaVendaMensalHistorico != null) {
            for (MesAnoMeta ma : listaVendaMensalHistorico) {
                String idV = ma.getId_vendedor() != null ? ma.getId_vendedor().trim() : null;
                String mesNomeRaw = ma.getNome_mes();
                if (idV == null || mesNomeRaw == null) continue;
                
                String mesChave = mesNomeRaw.substring(0, Math.min(mesNomeRaw.length(), 3)).toUpperCase();
                float valor = parseMoneyAPI(ma.getVenda_liquida());
                boolean isDev = ma.getE_entrada_s_saida() != null && ma.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                boolean relComercialSim = ma.getFigura_rel_comercial() != null && ma.getFigura_rel_comercial().trim().equalsIgnoreCase("S");
                
                if (!mapaHistoricoAgregado.containsKey(idV)) mapaHistoricoAgregado.put(idV, new HashMap<>());
                float current = mapaHistoricoAgregado.get(idV).getOrDefault(mesChave, 0f);

                /* CÓDIGO ANTERIOR:
                mapaHistoricoAgregado.get(idV).put(mesChave, isDev ? (current - Math.abs(valor)) : (current + Math.abs(valor)));
                */

                if (isDev) {
                    if (relComercialSim) {
                        mapaHistoricoAgregado.get(idV).put(mesChave, current - Math.abs(valor));
                    } else {
                        mapaHistoricoAgregado.get(idV).put(mesChave, current);
                    }
                } else {
                    mapaHistoricoAgregado.get(idV).put(mesChave, current + Math.abs(valor));
                }
            }
        }

        Map<String, Map<String, Float>> mapaSegmentoAgregado = new HashMap<>();
        Map<String, String> mapaNomesSegmentos = new HashMap<>();
        if (listaVendaSegmento != null) {
            for (MesGrupoMeta item : listaVendaSegmento) {
                String idV = item.getId_vendedor() != null ? item.getId_vendedor().trim() : null;
                String idG = item.getId_grupo() != null ? item.getId_grupo().trim() : null;
                if (idV == null || idG == null) continue;
                float valor = parseMoneyAPI(item.getVenda_liquida());
                boolean isDev = item.getE_entrada_s_saida() != null && item.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                boolean relComercialSim = item.getFigura_rel_comercial() != null && item.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

                if (!mapaSegmentoAgregado.containsKey(idV)) mapaSegmentoAgregado.put(idV, new HashMap<>());
                float current = mapaSegmentoAgregado.get(idV).getOrDefault(idG, 0f);

                /* CÓDIGO ANTERIOR:
                mapaSegmentoAgregado.get(idV).put(idG, isDev ? (current - Math.abs(valor)) : (current + Math.abs(valor)));
                */

                if (isDev) {
                    if (relComercialSim) {
                        mapaSegmentoAgregado.get(idV).put(idG, current - Math.abs(valor));
                    } else {
                        mapaSegmentoAgregado.get(idV).put(idG, current);
                    }
                } else {
                    mapaSegmentoAgregado.get(idV).put(idG, current + Math.abs(valor));
                }

                mapaNomesSegmentos.put(idG, item.getNome_grupo());
            }
        }

        try {
            // ... resto do código de estilos ...
            // ESTILOS
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle tableHeaderStyle = workbook.createCellStyle();
            Font tableFont = workbook.createFont();
            tableFont.setBold(true);
            tableHeaderStyle.setFont(tableFont);
            tableHeaderStyle.setBorderBottom(BorderStyle.THIN);
            tableHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            tableHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            tableHeaderStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle totalStyle = workbook.createCellStyle();
            totalStyle.setFont(tableFont);
            totalStyle.setBorderTop(BorderStyle.MEDIUM);
            totalStyle.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle totalLabelStyle = workbook.createCellStyle();
            totalLabelStyle.setFont(tableFont);
            totalLabelStyle.setBorderTop(BorderStyle.MEDIUM);
            totalLabelStyle.setAlignment(HorizontalAlignment.LEFT);

            // ESTILO PARA DESTAQUE DE FUNDO (Igual cabeçalho)
            CellStyle highlightedStyle = workbook.createCellStyle();
            highlightedStyle.setFont(tableFont);
            highlightedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            highlightedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            highlightedStyle.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle highlightedLabelStyle = workbook.createCellStyle();
            highlightedLabelStyle.setFont(tableFont);
            highlightedLabelStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            highlightedLabelStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            highlightedLabelStyle.setAlignment(HorizontalAlignment.LEFT);

            // ESTILO PARA MOEDA (R$)
            DataFormat df = workbook.createDataFormat();
            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat(df.getFormat("R$ #,##0.00"));
            currencyStyle.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle totalCurrencyStyle = workbook.createCellStyle();
            totalCurrencyStyle.setFont(tableFont);
            totalCurrencyStyle.setBorderTop(BorderStyle.MEDIUM);
            totalCurrencyStyle.setDataFormat(df.getFormat("R$ #,##0.00"));
            totalCurrencyStyle.setAlignment(HorizontalAlignment.RIGHT);

            // ESTILO PARA VALOR ZERO (Destaque na cor da fonte)
            Font zeroFont = workbook.createFont();
            zeroFont.setColor(IndexedColors.RED.getIndex());

            CellStyle zeroValueStyle = workbook.createCellStyle();
            zeroValueStyle.setDataFormat(df.getFormat("R$ #,##0.00"));
            zeroValueStyle.setAlignment(HorizontalAlignment.RIGHT);
            zeroValueStyle.setFont(zeroFont);

            // ESTILO PARA PERCENTUAL
            CellStyle percentStyle = workbook.createCellStyle();
            percentStyle.setDataFormat(df.getFormat("0.00%"));
            percentStyle.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle totalPercentStyle = workbook.createCellStyle();
            totalPercentStyle.setFont(tableFont);
            totalPercentStyle.setBorderTop(BorderStyle.MEDIUM);
            totalPercentStyle.setDataFormat(df.getFormat("0.00%"));
            totalPercentStyle.setAlignment(HorizontalAlignment.RIGHT);

            // ESTILO PARA PERCENTUAL BAIXO (Apenas fonte amarela suave)
            Font softYellowFont = workbook.createFont();
            softYellowFont.setColor(IndexedColors.GOLD.getIndex());
            softYellowFont.setBold(true);

            CellStyle lowAchievementStyle = workbook.createCellStyle();
            lowAchievementStyle.setDataFormat(df.getFormat("0.00%"));
            lowAchievementStyle.setAlignment(HorizontalAlignment.RIGHT);
            lowAchievementStyle.setFont(softYellowFont);

            CellStyle rightAlignedStyle = workbook.createCellStyle();
            rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);

            // ESTILOS PARA EFEITO ZEBRA (Linhas Alternadas)
            short zebraColor = IndexedColors.GREY_25_PERCENT.getIndex();
            
            CellStyle zebraCurrencyStyle = workbook.createCellStyle();
            zebraCurrencyStyle.setDataFormat(df.getFormat("R$ #,##0.00"));
            zebraCurrencyStyle.setAlignment(HorizontalAlignment.RIGHT);
            zebraCurrencyStyle.setFillForegroundColor(zebraColor);
            zebraCurrencyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle zebraZeroValueStyle = workbook.createCellStyle();
            zebraZeroValueStyle.setDataFormat(df.getFormat("R$ #,##0.00"));
            zebraZeroValueStyle.setAlignment(HorizontalAlignment.RIGHT);
            zebraZeroValueStyle.setFillForegroundColor(zebraColor);
            zebraZeroValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            zebraZeroValueStyle.setFont(zeroFont);

            CellStyle zebraPercentStyle = workbook.createCellStyle();
            zebraPercentStyle.setDataFormat(df.getFormat("0.00%"));
            zebraPercentStyle.setAlignment(HorizontalAlignment.RIGHT);
            zebraPercentStyle.setFillForegroundColor(zebraColor);
            zebraPercentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle zebraPlainStyle = workbook.createCellStyle();
            zebraPlainStyle.setFillForegroundColor(zebraColor);
            zebraPlainStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // TEXTO DE RODAPÉ (Data/Hora Emissão)
            String dataHoraEmissao = "Relatório gerado pelo App Whalle em " + 
                    new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss", Locale.getDefault()).format(new Date());
            CellStyle footerStyle = workbook.createCellStyle();
            Font footerFont = workbook.createFont();
            footerFont.setItalic(true);
            footerFont.setFontHeightInPoints((short) 9);
            footerStyle.setFont(footerFont);

        // 1. Rastreio de devoluções apenas do MÊS ATUAL para a coluna de Devolução na Aba 1
        Map<String, Float> mapaDevolucoesMesAtualVendedor = new HashMap<>();
        if (listaVendaDetalhada != null) {
            for (MesGrupoMeta item : listaVendaDetalhada) {
                String idV = item.getId_vendedor() != null ? item.getId_vendedor().trim() : null;
                if (idV == null) continue;

                boolean isDevolucao = item.getE_entrada_s_saida() != null && item.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                if (isDevolucao) {
                    float valor = Math.abs(parseMoneyAPI(item.getVenda_liquida()));
                    float currentDev = mapaDevolucoesMesAtualVendedor.getOrDefault(idV, 0f);
                    mapaDevolucoesMesAtualVendedor.put(idV, currentDev + valor);
                }
            }
        }

        // 2. Rastreio de todas as devoluções ACUMULADAS do ano (Jan a Mês Selecionado) para as abas dos vendedores
        Map<String, Float> mapaTodasDevolucoesVendedor = new HashMap<>();
        if (listaVendaMensalHistorico != null) {
            for (MesAnoMeta ma : listaVendaMensalHistorico) {
                String idV = ma.getId_vendedor() != null ? ma.getId_vendedor().trim() : null;
                if (idV == null) continue;

                boolean isDev = ma.getE_entrada_s_saida() != null && ma.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                if (isDev) {
                    float valor = Math.abs(parseMoneyAPI(ma.getVenda_liquida()));
                    float currentDev = mapaTodasDevolucoesVendedor.getOrDefault(idV, 0f);
                    mapaTodasDevolucoesVendedor.put(idV, currentDev + valor);
                }
            }
        }

        // LINHA 1: TITULO
        Row rowTitle = sheet.createRow(0);
        Cell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue("DEMONSTRATIVO DE VENDAS GERAL");
        cellTitle.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); // Mescla A até F

        // LINHA 2: MES E ANO
        Row rowDate = sheet.createRow(1);
        Cell cellDate = rowDate.createCell(0);
        String[] meses = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
        cellDate.setCellValue(meses[mesSelecionado - 1] + " / " + anoSelecionado);
        cellDate.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5)); // Mescla A até F

        // LINHA 3: CABEÇALHO DA TABELA
        Row rowHeader = sheet.createRow(2);
        String[] headers = {"VENDEDORES", "META", "VENDA", "% META", "% EMPRESA", "DEVOLUÇÃO"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(tableHeaderStyle);
        }

        // CORPO DA TABELA
        int firstDataRow = 4; // Index Excel para o início dos dados
        int rowNum = 3;
        /* CÓDIGO ANTERIOR:
        double metaEmpresaGlobal = 3650000.00;
        */
        // Usa a variavel de classe metaEmpresaGlobal carregada dinamicamente do Firestore

        for (int i = 0; i < listaParaExportacao.size(); i++) {
            ItemPerformanceVendedor item = listaParaExportacao.get(i);
            Row row = sheet.createRow(rowNum++);
            int currentExcelRow = rowNum;
            boolean isZebra = i % 2 != 0;

            Cell cellNome = row.createCell(0);
            cellNome.setCellValue(item.nome);
            if (isZebra) cellNome.setCellStyle(zebraPlainStyle);
            
            Cell cellMeta = row.createCell(1);
            cellMeta.setCellValue(item.meta);
            cellMeta.setCellStyle(isZebra ? zebraCurrencyStyle : currencyStyle);

            Cell cellVenda = row.createCell(2);
            cellVenda.setCellValue(item.real);
            cellVenda.setCellStyle(isZebra ? zebraCurrencyStyle : currencyStyle);
            
            // % META (Fórmula: Venda / Meta)
            Cell cellPerc = row.createCell(3);
            cellPerc.setCellFormula("IF(B" + currentExcelRow + ">0, C" + currentExcelRow + "/B" + currentExcelRow + ", 0)");
            cellPerc.setCellStyle(isZebra ? zebraPercentStyle : percentStyle);

            // % EMPRESA (Fórmula: Venda / Meta Global)
            Cell cellPercEmp = row.createCell(4);
            cellPercEmp.setCellFormula("C" + currentExcelRow + "/" + metaEmpresaGlobal);
            cellPercEmp.setCellStyle(isZebra ? zebraPercentStyle : percentStyle);

            // DEVOLUÇÃO (Total de devoluções do vendedor no MÊS ATUAL, independente da condição)
            Cell cellDev = row.createCell(5);
            float totalDevVendMes = mapaDevolucoesMesAtualVendedor.getOrDefault(item.idQuandoVendedor != null ? item.idQuandoVendedor.trim() : "", 0f);
            cellDev.setCellValue(totalDevVendMes);
            if (isZebra) {
                cellDev.setCellStyle(totalDevVendMes > 0 ? zebraZeroValueStyle : zebraCurrencyStyle);
            } else {
                cellDev.setCellStyle(totalDevVendMes > 0 ? zeroValueStyle : currencyStyle);
            }
        }

        int lastDataRow = rowNum; // Última linha de dados no Excel

        // LINHA EM BRANCO (Separação visual)
        rowNum++;

        // ÚLTIMA LINHA: TOTALIZADORES
        Row rowTotal = sheet.createRow(rowNum++);
        int totalExcelRow = rowNum;

        Cell cellTotalLabel = rowTotal.createCell(0);
        cellTotalLabel.setCellValue("TOTALIZADORES:");
        cellTotalLabel.setCellStyle(totalLabelStyle);

        Cell cellTotalMeta = rowTotal.createCell(1);
        cellTotalMeta.setCellFormula("SUM(B" + firstDataRow + ":B" + lastDataRow + ")");
        cellTotalMeta.setCellStyle(totalCurrencyStyle);

        Cell cellTotalReal = rowTotal.createCell(2);
        cellTotalReal.setCellFormula("SUM(C" + firstDataRow + ":C" + lastDataRow + ")");
        cellTotalReal.setCellStyle(totalCurrencyStyle);

        Cell cellTotalPerc = rowTotal.createCell(3);
        cellTotalPerc.setCellFormula("IF(B" + totalExcelRow + ">0, C" + totalExcelRow + "/B" + totalExcelRow + ", 0)");
        cellTotalPerc.setCellStyle(totalPercentStyle);
        
        // Coluna E (Totalizador % Empresa) - Em branco conforme solicitado
        Cell cellTotalEmp = rowTotal.createCell(4);
        cellTotalEmp.setCellValue(""); 
        cellTotalEmp.setCellStyle(totalStyle);

        // Coluna F (Totalizador Devoluções)
        Cell cellTotalDev = rowTotal.createCell(5);
        cellTotalDev.setCellFormula("SUM(F" + firstDataRow + ":F" + lastDataRow + ")");
        cellTotalDev.setCellStyle(totalCurrencyStyle);

        // LINHA DE RODAPÉ (Data/Hora)
        rowNum++;
        Row rowFooter1 = sheet.createRow(rowNum);
        Cell cellFooter1 = rowFooter1.createCell(0);
        cellFooter1.setCellValue(dataHoraEmissao);
        cellFooter1.setCellStyle(footerStyle);

        // NOVO: Ajusta largura das colunas manualmente
        sheet.setColumnWidth(0, 10000); // Vendedores
        sheet.setColumnWidth(1, 7000); // Meta
        sheet.setColumnWidth(2, 7000); // Venda
        sheet.setColumnWidth(3, 5000); // % Meta
        sheet.setColumnWidth(4, 5000); // % Empresa
        sheet.setColumnWidth(5, 7000); // Devolução

            // =====================================================================================
            // ABA 2: VENDA DETALHADA
            // =====================================================================================
            Sheet sheet2 = workbook.createSheet("Venda detalhada");
            int numVendedores = listaParaExportacao.size();
            int colVazia = numVendedores + 1;
            int colTotalGeral = numVendedores + 2;

            // LINHA 1: TITULO DINÂMICO
            Row rowTitle2 = sheet2.createRow(0);
            Cell cellTitle2 = rowTitle2.createCell(0);
            String tituloAba2 = "VENDA DETALHADA " + meses[mesSelecionado - 1] + " / " + anoSelecionado;
            cellTitle2.setCellValue(tituloAba2);
            cellTitle2.setCellStyle(titleStyle);
            sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, colTotalGeral));

            // LINHA 2: SUBTITULO
            Row rowSub2 = sheet2.createRow(1);
            Cell cellSub2 = rowSub2.createCell(0);
            cellSub2.setCellValue("DEMONSTRATIVO POR VENDEDORES");
            cellSub2.setCellStyle(headerStyle);
            sheet2.addMergedRegion(new CellRangeAddress(1, 1, 0, colTotalGeral));

            // LINHA 3: CABEÇALHO (GRUPOS + NOMES VENDEDORES + TOTAL)
            Row rowHeader2 = sheet2.createRow(2);
            Cell cellGrp = rowHeader2.createCell(0);
            cellGrp.setCellValue("LINHA/GRUPO");
            cellGrp.setCellStyle(tableHeaderStyle);

            for (int i = 0; i < numVendedores; i++) {
                Cell cellName = rowHeader2.createCell(i + 1);
                cellName.setCellValue(listaParaExportacao.get(i).nome);
                cellName.setCellStyle(tableHeaderStyle);
            }
            
            // Coluna em branco
            rowHeader2.createCell(colVazia).setCellStyle(tableHeaderStyle);
            
            // Coluna TOTAIS
            Cell cellTotalH = rowHeader2.createCell(colTotalGeral);
            cellTotalH.setCellValue("TOTAIS");
            cellTotalH.setCellStyle(tableHeaderStyle);

            // CORPO: DADOS REAIS POR GRUPO/VENDEDOR
            // 1. Mapeia os dados por Grupo -> Vendedor (ID_QUANDO_VENDEDOR)
            Map<String, Map<String, Float>> pivotData = new HashMap<>();
            List<String> idsGruposComVenda = new ArrayList<>();
            List<String> idsGruposSemVenda = new ArrayList<>();
            List<String> idsGruposComDevolucao = new ArrayList<>(); // NOVO
            Map<String, String> mapaIdNomeGrupo = new HashMap<>();
            Map<String, Float> mapaDevolucoesVendedor = new HashMap<>();

            for (MesGrupoMeta item : listaVendaDetalhada) {
                String idV = item.getId_vendedor() != null ? item.getId_vendedor().trim() : null;
                if (idV == null) continue;

                float valor = Math.abs(parseMoneyAPI(item.getVenda_liquida()));
                boolean isDevolucao = item.getE_entrada_s_saida() != null && item.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                boolean relComercialSim = item.getFigura_rel_comercial() != null && item.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

                String idG = item.getId_grupo() != null && !item.getId_grupo().isEmpty() ? item.getId_grupo().trim() : "SEM_GRP";
                if (isDevolucao) {
                    if (relComercialSim) {
                        float currentDev = mapaDevolucoesVendedor.getOrDefault(idV, 0f);
                        mapaDevolucoesVendedor.put(idV, currentDev + valor);
                        
                        // Abate da venda do grupo do vendedor
                        if (!pivotData.containsKey(idG)) {
                            pivotData.put(idG, new HashMap<>());
                            if (!mapaIdNomeGrupo.containsKey(idG)) {
                                mapaIdNomeGrupo.put(idG, (item.getNome_grupo() != null && !item.getNome_grupo().isEmpty()) ? item.getNome_grupo() : "GERAL");
                            }
                        }
                        float currentVal = pivotData.get(idG).getOrDefault(idV, 0f);
                        pivotData.get(idG).put(idV, currentVal - valor);

                        // Rastreia grupos que tiveram devoluções (apenas se o valor for maior que zero)
                        if (valor > 0 && !idsGruposComDevolucao.contains(idG)) {
                            idsGruposComDevolucao.add(idG);
                        }
                    }
                } else {
                    if (!pivotData.containsKey(idG)) {
                        pivotData.put(idG, new HashMap<>());
                        if (!mapaIdNomeGrupo.containsKey(idG)) {
                            mapaIdNomeGrupo.put(idG, (item.getNome_grupo() != null && !item.getNome_grupo().isEmpty()) ? item.getNome_grupo() : "GERAL");
                        }
                    }
                    float currentVal = pivotData.get(idG).getOrDefault(idV, 0f);
                    pivotData.get(idG).put(idV, currentVal + valor);
                }
            }

            // 2. Separa grupos com venda e sem venda (Soma total da linha)
            for (String idG : mapaIdNomeGrupo.keySet()) {
                float totalVendaGrupo = 0;
                if (pivotData.containsKey(idG)) {
                    for (float val : pivotData.get(idG).values()) totalVendaGrupo += val;
                }
                
                if (totalVendaGrupo > 0) idsGruposComVenda.add(idG);
                else idsGruposSemVenda.add(idG);
            }
            
            // Ordena grupos por nome
            Collections.sort(idsGruposComVenda, (id1, id2) -> mapaIdNomeGrupo.get(id1).compareToIgnoreCase(mapaIdNomeGrupo.get(id2)));
            Collections.sort(idsGruposSemVenda, (id1, id2) -> mapaIdNomeGrupo.get(id1).compareToIgnoreCase(mapaIdNomeGrupo.get(id2)));

            /* LÓGICA ANTERIOR (MOSTRAVA TODOS OS GRUPOS)
            List<String> idsGruposEncontrados = new ArrayList<>(pivotData.keySet());
            Collections.sort(idsGruposEncontrados, (id1, id2) -> mapaIdNomeGrupo.get(id1).compareToIgnoreCase(mapaIdNomeGrupo.get(id2)));
            for (String idGrp : idsGruposEncontrados) { ... }
            */

            int rowNum2 = 3;
            for (int i = 0; i < idsGruposComVenda.size(); i++) {
                String idGrp = idsGruposComVenda.get(i);
                Row row = sheet2.createRow(rowNum2++);
                int excelRow = rowNum2;
                boolean isZebra = i % 2 != 0;
                
                Cell cellGrpName = row.createCell(0);
                cellGrpName.setCellValue(mapaIdNomeGrupo.get(idGrp));
                if (isZebra) cellGrpName.setCellStyle(zebraPlainStyle);
                
                Map<String, Float> vendasPorVendedor = pivotData.get(idGrp);
                
                for (int j = 0; j < numVendedores; j++) {
                    ItemPerformanceVendedor ipv = listaParaExportacao.get(j);
                    String idVend = (ipv.idQuandoVendedor != null) ? ipv.idQuandoVendedor.trim() : "";
                    
                    float valor = 0f;
                    if (vendasPorVendedor != null && !idVend.isEmpty()) {
                        if (vendasPorVendedor.containsKey(idVend)) {
                            valor = vendasPorVendedor.get(idVend);
                        }
                    }

                    // LOG DE AUDITORIA: Verifica por que está zerado

                    
                    Cell cellVal = row.createCell(j + 1);
                    cellVal.setCellValue(valor); 
                    
                    if (valor == 0) {
                        cellVal.setCellStyle(isZebra ? zebraZeroValueStyle : zeroValueStyle);
                    } else {
                        cellVal.setCellStyle(isZebra ? zebraCurrencyStyle : currencyStyle);
                    }
                }
                
                Cell cellEmpty = row.createCell(colVazia);
                if (isZebra) cellEmpty.setCellStyle(zebraPlainStyle);
                
                Cell cellRowTotal = row.createCell(colTotalGeral);
                String lastSellerColLetter = getExcelColumnName(numVendedores + 1);
                cellRowTotal.setCellFormula("SUM(B" + excelRow + ":" + lastSellerColLetter + excelRow + ")");
                cellRowTotal.setCellStyle(totalCurrencyStyle);
            }

            /* CÓDIGO ANTERIOR (Linha separada de Devoluções):
            Row rowDev = sheet2.createRow(rowNum2++);
            Cell cellDevLabel = rowDev.createCell(0);
            cellDevLabel.setCellValue("DEVOLUÇÕES:");
            cellDevLabel.setCellStyle(highlightedLabelStyle);
            
            float totalGeralDevolucao = 0;
            for (int j = 0; j < numVendedores; j++) {
                ItemPerformanceVendedor ipv = listaParaExportacao.get(j);
                String idVendCol = ipv.idQuandoVendedor != null ? ipv.idQuandoVendedor.trim() : "";
                float valDev = mapaDevolucoesVendedor.getOrDefault(idVendCol, 0f);
                
                Cell cellValDev = rowDev.createCell(j + 1);
                cellValDev.setCellValue(-valDev);
                cellValDev.setCellStyle(zeroValueStyle);
                totalGeralDevolucao += valDev;
            }
            rowDev.createCell(colVazia).setCellStyle(zebraPlainStyle);
            Cell cellRowTotalDev = rowDev.createCell(colTotalGeral);
            cellRowTotalDev.setCellValue(-totalGeralDevolucao);
            cellRowTotalDev.setCellStyle(zeroValueStyle);
            rowNum2++;
            */

            // LINHA EM BRANCO
            rowNum2++;

            // ÚLTIMA LINHA: TOTAIS (RODAPÉ)
            Row rowTotal2 = sheet2.createRow(rowNum2);
            Cell cellTotalLabel2 = rowTotal2.createCell(0);
            cellTotalLabel2.setCellValue("TOTAIS:");
            cellTotalLabel2.setCellStyle(totalLabelStyle);

            for (int j = 0; j < numVendedores; j++) {
                Cell cellSum = rowTotal2.createCell(j + 1);
                // O vendedor 'j' está na coluna index 'j+1', que no Excel é o número 'j+2'
                String colLetter = getExcelColumnName(j + 2);
                cellSum.setCellFormula("SUM(" + colLetter + "4:" + colLetter + (rowNum2 - 1) + ")");
                cellSum.setCellStyle(totalCurrencyStyle);
            }
            
            // Total Geral da Aba (Soma da coluna TOTAIS)
            Cell cellGrandTotal = rowTotal2.createCell(colTotalGeral);
            String totalColLetter = getExcelColumnName(colTotalGeral + 1);
            cellGrandTotal.setCellFormula("SUM(" + totalColLetter + "4:" + totalColLetter + (rowNum2 - 1) + ")");
            cellGrandTotal.setCellStyle(totalCurrencyStyle);

            // LINHA DE RANKING (Pós-Total)
            rowNum2++;
            Row rowRanking = sheet2.createRow(rowNum2);
            Cell cellRankLabel = rowRanking.createCell(0);
            cellRankLabel.setCellValue("RANKING:");
            cellRankLabel.setCellStyle(highlightedLabelStyle);

            for (int j = 0; j < numVendedores; j++) {
                Cell cellRank = rowRanking.createCell(j + 1);
                cellRank.setCellValue((j + 1) + "º");
                cellRank.setCellStyle(highlightedStyle); 
            }
            
            // Célula vazia e total na linha de ranking também com destaque
            rowRanking.createCell(colVazia).setCellStyle(highlightedStyle);
            rowRanking.createCell(colTotalGeral).setCellStyle(highlightedStyle);

            // LINHA DE RODAPÉ (Data/Hora)
            rowNum2++;
            rowNum2++;
            Row rowFooter2 = sheet2.createRow(rowNum2);
            Cell cellFooter2 = rowFooter2.createCell(0);
            cellFooter2.setCellValue(dataHoraEmissao);
            cellFooter2.setCellStyle(footerStyle);

            // Ajusta largura das colunas
            sheet2.setColumnWidth(0, 9000); // Grupos
            for (int i = 1; i <= numVendedores; i++) sheet2.setColumnWidth(i, 6000);
            sheet2.setColumnWidth(colVazia, 1000); // Coluna de separação
            sheet2.setColumnWidth(colTotalGeral, 7000); // Coluna de Totais

            // =====================================================================================
            // ABA 3: GRUPOS SEM FATURAMENTO
            // =====================================================================================
            Sheet sheet3 = workbook.createSheet("Grupos sem faturamento");

            // LINHA 1: TITULO
            Row rowTitle3 = sheet3.createRow(0);
            Cell cellTitle3 = rowTitle3.createCell(0);
            cellTitle3.setCellValue("LINHAS/GRUPOS SEM FATURAMENTO NO PERÍODO");
            cellTitle3.setCellStyle(titleStyle);
            sheet3.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

            // LINHA 2: CABEÇALHO
            Row rowHeader3 = sheet3.createRow(1);
            Cell h1 = rowHeader3.createCell(0); h1.setCellValue("CÓDIGO"); h1.setCellStyle(tableHeaderStyle);
            Cell h2 = rowHeader3.createCell(1); h2.setCellValue("DESCRIÇÃO DO GRUPO"); h2.setCellStyle(tableHeaderStyle);

            int rowNum3 = 2;
            for (int i = 0; i < idsGruposSemVenda.size(); i++) {
                String idGrp = idsGruposSemVenda.get(i);
                Row row = sheet3.createRow(rowNum3++);
                boolean isZebra = i % 2 != 0;
                
                Cell c1 = row.createCell(0);
                c1.setCellValue(idGrp);
                if (isZebra) c1.setCellStyle(zebraPlainStyle);
                
                Cell c2 = row.createCell(1);
                c2.setCellValue(mapaIdNomeGrupo.get(idGrp));
                if (isZebra) c2.setCellStyle(zebraPlainStyle);
            }

            // LINHA EM BRANCO DE SEPARAÇÃO
            rowNum3++;

            // NOVA SEÇÃO: GRUPOS COM DEVOLUÇÃO
            Row rowTitleDev = sheet3.createRow(rowNum3++);
            Cell cellTitleDev = rowTitleDev.createCell(0);
            cellTitleDev.setCellValue("LINHAS/GRUPOS COM DEVOLUÇÕES NO PERÍODO");
            cellTitleDev.setCellStyle(titleStyle);
            sheet3.addMergedRegion(new CellRangeAddress(rowNum3 - 1, rowNum3 - 1, 0, 1));

            // CABEÇALHO DA SEÇÃO DE DEVOLUÇÕES
            Row rowHeaderDev = sheet3.createRow(rowNum3++);
            Cell hd1 = rowHeaderDev.createCell(0); hd1.setCellValue("CÓDIGO"); hd1.setCellStyle(tableHeaderStyle);
            Cell hd2 = rowHeaderDev.createCell(1); hd2.setCellValue("DESCRIÇÃO DO GRUPO"); hd2.setCellStyle(tableHeaderStyle);

            // Ordena grupos com devolução por nome
            Collections.sort(idsGruposComDevolucao, (id1, id2) -> {
                String n1 = mapaIdNomeGrupo.get(id1) != null ? mapaIdNomeGrupo.get(id1) : "";
                String n2 = mapaIdNomeGrupo.get(id2) != null ? mapaIdNomeGrupo.get(id2) : "";
                return n1.compareToIgnoreCase(n2);
            });

            for (int i = 0; i < idsGruposComDevolucao.size(); i++) {
                String idGrp = idsGruposComDevolucao.get(i);
                Row row = sheet3.createRow(rowNum3++);
                boolean isZebra = i % 2 != 0;
                
                Cell c1 = row.createCell(0);
                c1.setCellValue(idGrp);
                if (isZebra) c1.setCellStyle(zebraPlainStyle);
                
                Cell c2 = row.createCell(1);
                c2.setCellValue(mapaIdNomeGrupo.get(idGrp));
                if (isZebra) c2.setCellStyle(zebraPlainStyle);
            }

            // LINHA DE RODAPÉ (Data/Hora)
            rowNum3++;
            Row rowFooter3 = sheet3.createRow(rowNum3);
            Cell cellFooter3 = rowFooter3.createCell(0);
            cellFooter3.setCellValue(dataHoraEmissao);
            cellFooter3.setCellStyle(footerStyle);

            sheet3.setColumnWidth(0, 4000);
            sheet3.setColumnWidth(1, 14000);

            // =====================================================================================
            // NOVAS ABAS: EVOLUÇÃO INDIVIDUAL POR VENDEDOR (Exemplo com Dados Fictícios)
            // =====================================================================================
            String[] mesesAbreviados = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
            
            for (ItemPerformanceVendedor vendedor : listaParaExportacao) {
                // Cria aba com nome do vendedor (limitado a 31 caracteres, regra do Excel)
                String nomeAba = vendedor.nome.length() > 30 ? vendedor.nome.substring(0, 27) + "..." : vendedor.nome;
                Sheet vSheet = workbook.createSheet(nomeAba);

                // LINHA 1: TITULO
                Row vRow1 = vSheet.createRow(0);
                Cell vCellTitle = vRow1.createCell(0);
                vCellTitle.setCellValue("DEMONSTRATIVO DE VENDAS - ANO " + anoSelecionado);
                vCellTitle.setCellStyle(titleStyle);
                // Mescla conforme a quantidade de meses exibidos (mínimo 2 colunas para o cabeçalho vendedor)
                vSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, Math.max(1, mesSelecionado - 1)));

                // LINHA 2: NOME VENDEDOR
                Row vRow2 = vSheet.createRow(1);
                vRow2.createCell(0).setCellValue("VENDEDOR:");
                vRow2.createCell(1).setCellValue(vendedor.nome);

                // LINHA 3: TÍTULO ROTAS
                Row vRow3 = vSheet.createRow(2);
                Cell vCellRotas = vRow3.createCell(0);
                vCellRotas.setCellValue("ROTAS:");
                vCellRotas.setCellStyle(headerStyle);

                // LINHA 4: ESPAÇO PARA PREENCHER ROTAS (MESCLADO)
                Row vRow4 = vSheet.createRow(3);
                vRow4.createCell(0).setCellValue("(Gerente: informe as rotas aqui)");
                vSheet.addMergedRegion(new CellRangeAddress(3, 3, 0, Math.max(1, mesSelecionado - 1)));

                // LINHA 5: RESUMO ACUMULADO (YTD)
                Row vRow5 = vSheet.createRow(4);
                vRow5.createCell(0).setCellValue("TOTAL ACUMULADO:");
                Cell cAccVal = vRow5.createCell(1);
                String lastMonthCol = getExcelColumnName(mesSelecionado);
                cAccVal.setCellFormula("SUM(A8:" + lastMonthCol + "8)"); // Soma a Linha 8 (Vendas)
                cAccVal.setCellStyle(totalCurrencyStyle);

                vRow5.createCell(2).setCellValue("PERFORMANCE GERAL:");
                Cell cAccPerc = vRow5.createCell(3);
                // Fórmula: SomaVendas / SomaMetas (Linha 8 / Linha 10)
                cAccPerc.setCellFormula("IF(SUM(A10:" + lastMonthCol + "10)>0, SUM(A8:" + lastMonthCol + "8)/SUM(A10:" + lastMonthCol + "10), 0)");
                cAccPerc.setCellStyle(totalPercentStyle);

                // MÉDIA MENSAL
                vRow5.createCell(4).setCellValue("MÉDIA MENSAL:");
                Cell cAvgVal = vRow5.createCell(5);
                cAvgVal.setCellFormula("B5 / " + mesSelecionado); // Divide o TOTAL ACUMULADO (B5) pelos meses
                cAvgVal.setCellStyle(totalCurrencyStyle);

                // TOTAL DEVOLUÇÕES (Colunas H e I)
                vRow5.createCell(7).setCellValue("TOTAL DEVOLUÇÕES:");
                Cell cDevVal = vRow5.createCell(8);
                float totalDevIndividual = mapaTodasDevolucoesVendedor.getOrDefault(vendedor.idQuandoVendedor != null ? vendedor.idQuandoVendedor.trim() : "", 0f);
                cDevVal.setCellValue(totalDevIndividual);
                cDevVal.setCellStyle(totalDevIndividual > 0 ? zeroValueStyle : totalCurrencyStyle);

                // LINHA 7: CABEÇALHO DOS MESES (Index 6)
                Row vRow6 = vSheet.createRow(6);
                for (int m = 0; m < mesSelecionado; m++) {
                    Cell mCell = vRow6.createCell(m);
                    mCell.setCellValue(mesesAbreviados[m]);
                    mCell.setCellStyle(tableHeaderStyle);
                }

                // LINHA 8: VALORES DE VENDA (Index 7)
                Row vRow7 = vSheet.createRow(7);
                for (int m = 0; m < mesSelecionado; m++) {
                    Cell vCell = vRow7.createCell(m);
                    
                    if (m == mesSelecionado - 1) {
                        // DADO REAL: Valor exato liquidado para o mês atual
                        vCell.setCellValue(vendedor.real); 
                    } else {
                        // BUSCA HISTÓRICO AGREGADO: Garante consistência com devoluções dos meses anteriores
                        float valorHist = 0f;
                        String prefixoBusca = meses[m].substring(0, 3).toUpperCase();
                        
                        Map<String, Float> historicoVend = mapaHistoricoAgregado.get(vendedor.idQuandoVendedor != null ? vendedor.idQuandoVendedor.trim() : "");
                        if (historicoVend != null) {
                            valorHist = historicoVend.getOrDefault(prefixoBusca, 0f);
                        }
                        vCell.setCellValue(valorHist);
                    }
                    vCell.setCellStyle(currencyStyle);
                }

                // LINHA 9: TÍTULO METAS (Index 8)
                Row vRow8 = vSheet.createRow(8);
                Cell vCellMetaTitle = vRow8.createCell(0);
                vCellMetaTitle.setCellValue("METAS");
                vCellMetaTitle.setCellStyle(headerStyle);
                vSheet.addMergedRegion(new CellRangeAddress(8, 8, 0, Math.max(1, mesSelecionado - 1)));

                // LINHA 10: VALORES DE META (Index 9)
                Row vRow9 = vSheet.createRow(9);
                for (int m = 0; m < mesSelecionado; m++) {
                    Cell vCell = vRow9.createCell(m);
                    
                    float valorMetaReal = 0;
                    if (mapaMetasMensais.containsKey(vendedor.idQuandoVendedor)) {
                        valorMetaReal = getMetaDoMes(mapaMetasMensais.get(vendedor.idQuandoVendedor), m + 1);
                    }

                    vCell.setCellValue(valorMetaReal);
                    vCell.setCellStyle(currencyStyle);
                }

                // LINHA 11: % ATINGIDO (Index 10)
                Row vRow10 = vSheet.createRow(10);
                for (int m = 0; m < mesSelecionado; m++) {
                    Cell vCell = vRow10.createCell(m);
                    String colL = getExcelColumnName(m + 1);
                    vCell.setCellFormula("IF(" + colL + "10>0, " + colL + "8/" + colL + "10, 0)");
                    
                    // NOVO: Calcula valor real para aplicar o estilo de destaque se <= 98%
                    float vMeta = 0;
                    if (mapaMetasMensais.containsKey(vendedor.idQuandoVendedor)) {
                        vMeta = getMetaDoMes(mapaMetasMensais.get(vendedor.idQuandoVendedor), m + 1);
                    }
                    
                    float vVenda = 0;
                    if (m == mesSelecionado - 1) {
                        vVenda = vendedor.real;
                    } else {
                        String pMes = meses[m].substring(0, 3).toUpperCase();
                        for (MesAnoMeta ma : listaVendaMensalHistorico) {
                            String idV = ma.getId_vendedor() != null ? ma.getId_vendedor().trim() : "";
                            String idB = vendedor.idQuandoVendedor != null ? vendedor.idQuandoVendedor.trim() : "";
                            if (idB.equals(idV) && ma.getNome_mes() != null && ma.getNome_mes().toUpperCase().contains(pMes)) {
                                vVenda = cleanMoney(ma.getVenda_liquida());
                                break;
                            }
                        }
                    }
                    
                    double pAt = vMeta > 0 ? (vVenda / vMeta) : 0;
                    if (pAt <= 0.98) {
                        vCell.setCellStyle(lowAchievementStyle);
                    } else {
                        vCell.setCellStyle(percentStyle);
                    }
                }

                // NOVO: Reserva espaço para o gráfico (Linha 14 até 20)
                vSheet.addMergedRegion(new CellRangeAddress(13, 19, 0, Math.max(1, mesSelecionado - 1)));
                
                // Adiciona a instrução para o gestor
                Row vRowInstr = vSheet.createRow(13);
                Cell vCellInstr = vRowInstr.createCell(0);
                vCellInstr.setCellValue("(Espaço para o gráfico: selecione os meses e valores acima e use a opção 'Inserir Gráfico')");
                vCellInstr.setCellStyle(headerStyle);

                // =================================================================================
                // NOVA SEÇÃO: VENDA POR SEGMENTO (ABAIXO DO GRÁFICO)
                // =================================================================================
                // Filtra segmentos agregados do vendedor atual
                Map<String, Float> segmentosVend = mapaSegmentoAgregado.get(vendedor.idQuandoVendedor);

                // LINHA 21: TITULO SEGMENTO (Index 20)
                Row vRowSegTitle = vSheet.createRow(20);
                Cell cSegT = vRowSegTitle.createCell(0);
                cSegT.setCellValue("VENDA POR SEGMENTO (LÍQUIDA) - " + meses[mesSelecionado - 1]);
                cSegT.setCellStyle(headerStyle);
                vSheet.addMergedRegion(new CellRangeAddress(20, 20, 0, Math.max(1, mesSelecionado - 1)));

                // LINHA 22: NOMES DOS SEGMENTOS (Index 21)
                Row vRowSegNames = vSheet.createRow(21);
                // LINHA 23: VALORES DOS SEGMENTOS (Index 22)
                Row vRowSegVals = vSheet.createRow(22);
                
                double totalSeg = 0;
                int colSeg = 0;

                if (segmentosVend != null)
                    for (String idG : segmentosVend.keySet()) {
                        // Nome do Segmento
                        Cell cS = vRowSegNames.createCell(colSeg);
                        cS.setCellValue(mapaNomesSegmentos.getOrDefault(idG, "SEGMENTO " + idG));
                        cS.setCellStyle(tableHeaderStyle);

                        // Valor do Segmento (Líquido)
                        float valSeg = segmentosVend.get(idG);
                        Cell cV = vRowSegVals.createCell(colSeg);
                        cV.setCellValue(valSeg);
                        cV.setCellStyle(currencyStyle);
                        
                        totalSeg += valSeg;
                        colSeg++;
                    }

                // Coluna do Total de Segmentos
                Cell cSegTotalLabel = vRowSegNames.createCell(colSeg);
                cSegTotalLabel.setCellValue("TOTAL SEG.");
                cSegTotalLabel.setCellStyle(tableHeaderStyle);

                Cell cSegTotalVal = vRowSegVals.createCell(colSeg);
                cSegTotalVal.setCellValue(totalSeg);
                cSegTotalVal.setCellStyle(totalCurrencyStyle);

                // LINHA 25: TÍTULO OBSERVAÇÕES (Index 24)
                Row vRowObsTitle = vSheet.createRow(24);
                Cell vCellObsTitle = vRowObsTitle.createCell(0);
                vCellObsTitle.setCellValue("OBSERVAÇÕES / FEEDBACK DO GESTOR:");
                vCellObsTitle.setCellStyle(headerStyle);
                vSheet.addMergedRegion(new CellRangeAddress(24, 24, 0, Math.max(1, mesSelecionado - 1)));

                // LINHA 26-28: ESPAÇO PARA FEEDBACK (Index 25-27)
                vSheet.addMergedRegion(new CellRangeAddress(25, 27, 0, Math.max(1, mesSelecionado - 1)));
                Row vRowFeedback = vSheet.createRow(25);
                vRowFeedback.createCell(0).setCellValue("(Espaço para anotações do gestor)");

                // LINHA DE RODAPÉ (Data/Hora) - Index 29
                Row vRowFooter = vSheet.createRow(29);
                Cell cFooter = vRowFooter.createCell(0);
                cFooter.setCellValue(dataHoraEmissao);
                cFooter.setCellStyle(footerStyle);

                // Ajusta larguras
                vSheet.setColumnWidth(0, 5000);
                for (int i = 1; i < 12; i++) vSheet.setColumnWidth(i, 5000);
            }

            // SALVAR ARQUIVO
            File cachePath = new File(context.getCacheDir(), "documents");
            cachePath.mkdirs();
            File file = new File(cachePath, "demonstrativo_vendas.xlsx");
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            workbook.close();
            hideLoading();

            // COMPARTILHAR
            Uri uri = FileProvider.getUriForFile(context, getPackageName() + ".provider", file);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Compartilhar Relatório Excel"));
        } catch (Exception e) {
            Toast.makeText(context, "Erro ao gerar Excel: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void compartilharDashboard() {
        try {
            Bitmap bitmap = getBitmapFromView(scrollViewDashboard);
            // Salva na pasta de cache para não precisar de permissão de escrita externa em Androids novos
            java.io.File cachePath = new java.io.File(context.getCacheDir(), "images");
            cachePath.mkdirs();
            java.io.File file = new java.io.File(cachePath, "dashboard_equipe.png");
            java.io.FileOutputStream stream = new java.io.FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            Uri contentUri = FileProvider.getUriForFile(context, getPackageName() + ".provider", file);
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
            Toast.makeText(context, "Erro ao gerar imagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        // Captura a altura total do conteúdo dentro do ScrollView
        int height = 0;
        if (view instanceof ScrollView) {
            if (((ScrollView) view).getChildAt(0) != null)
                height = ((ScrollView) view).getChildAt(0).getHeight();
             else
                height = view.getHeight();
        } else
            height = view.getHeight();

        // Evita erro de bitmap com altura zero
        if (height <= 0) height = 1000; 
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Se for um ScrollView, precisamos desenhar o fundo branco antes, 
        // senão as partes não visíveis podem ficar pretas ou transparentes
        canvas.drawColor(Color.WHITE); 
        view.draw(canvas);
        return bitmap;
    }

    private void flipCard(Context context, View vV, View iV, ProgressBar pB, int idC) {
        if (pB != null)
            pB.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, idC == 1 ? R.color.colorPrimary : R.color.colorButton), PorterDuff.Mode.SRC_IN);
        try {
            iV.setVisibility(View.INVISIBLE);
            @SuppressLint("ResourceType") AnimatorSet fOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.flip_out);
            @SuppressLint("ResourceType") AnimatorSet fIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.flip_in_right);
            fOut.setTarget(iV);
            fIn.setTarget(vV);
            fOut.start();
            fIn.start();
            vV.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    private String getExcelColumnName(int columnNumber) {
        StringBuilder columnName = new StringBuilder();
        while (columnNumber > 0) {
            int modulo = (columnNumber - 1) % 26;
            columnName.insert(0, (char) ('A' + modulo));
            columnNumber = (columnNumber - modulo) / 26;
        }
        return columnName.toString();
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
