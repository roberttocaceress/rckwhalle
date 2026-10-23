package com.example.rcksuporte05.rcksistemas.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.MesAno;
import com.example.rcksuporte05.rcksistemas.model.MesAnoGrupoMarca;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesGrupoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.MetaMensal;
import com.example.rcksuporte05.rcksistemas.model.MetaSemanal;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.ColorTemplateApp;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.FirestoreConexao;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPainelGraficoMetas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener, OnChartGestureListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.barChart1)
    BarChart barChart1;

    @BindView(R.id.barChart2)
    BarChart barChart2;

    @BindView(R.id.barChart3)
    BarChart barChart3;

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

    @BindView(R.id.tvTituloSemanal)
    TextView tvTituloSemanal;

    @BindView(R.id.tvTituloGrupo)
    TextView tvTituloGrupo;

    @BindView(R.id.scrollViewDashboard)
    ScrollView scrollViewDashboard;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navView)
    NavigationView navigationView;

    @BindView(R.id.lytGlobalLoading)
    View lytGlobalLoading;

    @BindView(R.id.tvGlobalLoadingTitle)
    TextView tvGlobalLoadingTitle;

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

    @BindView(R.id.btnRefreshSemanal)
    ImageView btnRefreshSemanal;

    @BindView(R.id.btnCalendarioSemanal)
    ImageView btnCalendarioSemanal;

    @BindView(R.id.btnRefreshGrupo)
    ImageView btnRefreshGrupo;

    @BindView(R.id.btnRefreshMensal)
    ImageView btnRefreshMensal;

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
    private DBHelper db = new DBHelper(this);
    PackageInfo pInfo = null;
    protected Typeface tfLight;
    private Legend l;
    private Configuracao configuracao;
    private int activeRequests = 0;
    private String totalGeralGrupo = "R$ 0,00";
    private List<ParticipacaoFaturamento> listaGraficoPizza = new ArrayList<>();
    private long lastTapTime = 0;
    private int idUsuarioSelecionado = 0;
    private int anoAtual, mesAtual;
    private String idVendedorFirestore = "";
    
    private double valorRealizadoMesAtual = 0;
    private double valorMetaMesAtual = 0;
    private double valorRealizadoMesAnterior = 0;
    private double valorMetaEmpresaGlobal = 0; // Armazena a meta real da empresa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (UsuarioHelper.getUsuario() == null) {
            Toast.makeText(this, "Erro: Usuário não identificado. Por favor, refaça o login.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_painel_grafico_metas);
        ButterKnife.bind(this);

        // Força a barra de status e navegação a terem a mesma cor da Toolbar
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        Calendar cal = Calendar.getInstance();
        anoAtual = cal.get(Calendar.YEAR);
        mesAtual = cal.get(Calendar.MONTH) + 1;

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
        
        if (UsuarioHelper.getUsuario() != null) {
            toolbar.setTitle(UsuarioHelper.getUsuario().getNome_usuario());
        } else {
            toolbar.setTitle("RCK Sistemas");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        configurarDrawerFiltro();

        carregarKpiVisitas();

        flipCard(context, lyt2, lyt1, progressBar1, 1);
        flipCard(context, lyt4, lyt3, progressBar2, 1);
        flipCard(context, lyt6, lyt5, progressBar3, 1);

        carregarTodosOsGraficos(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()));
        try {
            tvDataPesquisa.setText("Processado em: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
        } catch (Exception e) {
            tvDataPesquisa.setText("");
        }
        atualizarTextoReferencia();
        animarEntrada();
    }

    private void carregarKpiVisitas() {
        try {
            com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO visitaDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db);
            int totalVisitas = visitaDAO.getCountVisitas(null);
            int positivadas = visitaDAO.getCountVisitas("V");
            int naoVenda = visitaDAO.getCountVisitas("N");
            int percPosit = totalVisitas > 0 ? (positivadas * 100 / totalVisitas) : 0;

            TextView tvTotal = findViewById(R.id.tvKpiVisitasTotal);
            TextView tvSub = findViewById(R.id.tvKpiVisitasSubtexto);
            TextView tvPerc = findViewById(R.id.tvKpiVisitasPerc);
            ProgressBar pb = findViewById(R.id.pbKpiVisitas);
            View cardVisitas = findViewById(R.id.cardKpiVisitas);

            if (tvTotal != null) tvTotal.setText(totalVisitas + " Visita(s)");
            if (tvSub != null) tvSub.setText(positivadas + " Positivadas | " + naoVenda + " Não Venda");
            if (tvPerc != null) tvPerc.setText(percPosit + "% Positivação >");
            if (pb != null) pb.setProgress(Math.min(percPosit, 100));

            if (cardVisitas != null) {
                cardVisitas.setOnClickListener(v -> {
                    startActivity(new Intent(ActivityPainelGraficoMetas.this, ActivityRelatorioVisitas.class));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizarTextoReferencia() {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        if (tvMesAnoReferencia != null) {
            tvMesAnoReferencia.setText(meses[mesAtual - 1] + " / " + anoAtual);
        }
    }

    @butterknife.OnClick(R.id.btnSelecionarMesAnoGlobal)
    public void onSelecionarPeriodoClick() {
        exibirSeletorMesAno();
    }

    private void exibirSeletorMesAno() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        
        final NumberPicker monthPicker = new NumberPicker(context);
        final NumberPicker yearPicker = new NumberPicker(context);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(mesAtual);
        monthPicker.setDisplayedValues(new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"});
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // Evita duplo clique

        yearPicker.setMinValue(2020);
        yearPicker.setMaxValue(2030);
        yearPicker.setValue(anoAtual);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // Evita duplo clique

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(android.view.Gravity.CENTER);
        layout.setPadding(20, 20, 20, 20);
        
        // Ajuste de layout para os pickers
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        monthPicker.setLayoutParams(params);
        yearPicker.setLayoutParams(params);

        layout.addView(monthPicker);
        layout.addView(yearPicker);

        builder.setTitle("Selecionar Período")
                .setView(layout)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    mesAtual = monthPicker.getValue();
                    anoAtual = yearPicker.getValue();
                    atualizarTextoReferencia();
                    carregarTodosOsGraficos(idUsuarioSelecionado);
                })
                .setNegativeButton("Cancelar", null)
                .show();
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

    private void carregarTodosOsGraficos(int idUsuario) {
        this.idUsuarioSelecionado = idUsuario;
        getConfiguracao();

        // Busca o id_quando_vendedor para o Firestore usando getLista
        List<Usuario> users = new UsuarioDAO(db).getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " + idUsuario);
        if (users != null && !users.isEmpty() && users.get(0).getId_quando_vendedor() != null) {
            this.idVendedorFirestore = users.get(0).getId_quando_vendedor().trim();
        } else {
            this.idVendedorFirestore = String.valueOf(idUsuario); // Fallback
        }

        showLoading("Processando indicadores...");
        tvKpiReal1.setText("R$ 0,00");
        tvKpiMeta1.setText("Meta: R$ 0,00");
        tvKpiPerc1.setText("0% atingido");
        pbKpi1.setProgress(0);

        tvKpiReal2.setText("R$ 0,00");
        tvKpiMeta2.setText("Meta Empresa: R$ 0,00");
        tvKpiPerc2.setText("0.00% da empresa");
        pbKpi2.setProgress(0);

        tvKpiReal3.setText("R$ 0,00");
        tvKpiMeta3.setText("Meta: R$ 0,00");
        tvKpiPerc3.setText("0% atingido");
        pbKpi3.setProgress(0);

        // String dataInicio = getPrimeiroDiaMesAtual(); // Comentado para usar seleção global
        String dataInicio = getDataReferencia(anoAtual, mesAtual);

        this.valorRealizadoMesAtual = 0;
        this.valorMetaMesAtual = 0;
        this.valorRealizadoMesAnterior = 0;
        this.valorMetaEmpresaGlobal = 0;

        getMetaEmpresaFirestore(); // Nova busca da meta real da empresa
        getEvolucaoVendasSemana(idUsuario, 0, dataInicio);
        getEvolucaoVendasGrupo(idUsuario, 0, dataInicio);
        getEvolucaoVendasMes(idUsuario, 0, dataInicio);

        try {
            String[] partes = dataInicio.split("-");
            String anoCurto = partes[0].substring(2);
            String mesNome = "";
            switch (partes[1]) {
                case "01": mesNome = "JAN"; break;
                case "02": mesNome = "FEV"; break;
                case "03": mesNome = "MAR"; break;
                case "04": mesNome = "ABR"; break;
                case "05": mesNome = "MAI"; break;
                case "06": mesNome = "JUN"; break;
                case "07": mesNome = "JUL"; break;
                case "08": mesNome = "AGO"; break;
                case "09": mesNome = "SET"; break;
                case "10": mesNome = "OUT"; break;
                case "11": mesNome = "NOV"; break;
                case "12": mesNome = "DEZ"; break;
            }
            String descMesAno = "(" + mesNome + "/" + anoCurto + ")";
            tvTituloSemanal.setText("Performance Semanal"/* + descMesAno*/);
            tvTituloGrupo.setText("Performance por Grupo"/* + descMesAno*/);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPrimeiroDiaMesAtual() {
        String data = "";
        try {
            Cursor cursor = db.getReadableDatabase().rawQuery("SELECT date('now', 'start of month')", null);
            if (cursor.moveToFirst()) {
                data = cursor.getString(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Calendar c = Calendar.getInstance();
            data = String.format("%04d-%02d-01", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
        }
        return data;
    }

    private void getMetaEmpresaFirestore() {
        // Busca a meta da empresa logada (ID 1 por padrão ou configurável)
        getConfiguracao();
        if (configuracao == null || configuracao.getCnpj_empresa() == null) {
            tvKpiMeta2.setText("Meta Empresa: R$ 0,00");
            return;
        }

        new FirestoreConexao().getMetaEmpresa(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), "1", new FirestoreConexao.OnMetaRetorno() {
            @Override
            public void onSucesso(MesAno m) {
                if (m != null) {
                    valorMetaEmpresaGlobal = getMetaDoMes(m, mesesNomesFull[mesAtual - 1]);
                    atualizarCardMetaEmpresa();
                }
            }
            @Override public void onFalha(Exception e) {
                e.printStackTrace(); }
        });
    }

    private String[] mesesNomesFull = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};

    private void atualizarCardMetaEmpresa() {
        if (valorMetaEmpresaGlobal > 0) {
            tvKpiReal2.setText(MascaraUtil.mascaraRealSimboloD(valorMetaEmpresaGlobal));
            // O realizado do vendedor será atualizado no createbarChart2
        } else {
            tvKpiMeta2.setText("Meta Empresa: R$ 0,00");
        }
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

    private void configurarDrawerFiltro() {
        if (UsuarioHelper.getUsuario() == null) return;

        View drawerContent = getLayoutInflater().inflate(R.layout.layout_drawer_usuarios, navigationView, false);
        navigationView.addView(drawerContent);
        RecyclerView rvUsuarios = drawerContent.findViewById(R.id.rvUsuariosDrawer);
        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        List<Usuario> lista = new ArrayList<>();
        /*
        if (!UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario("999");
            usuario.setNome_usuario("TODOS");
            lista.add(usuario);
        }

         */
        List<Usuario> listaUsuarios = usuarioDAO.getLista( UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V") ?
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario() :
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND (NOME_USUARIO <> NULL OR NOME_USUARIO <> '') AND USUARIO_TIPO <> 'D' ORDER BY NOME_USUARIO");
        lista.addAll(listaUsuarios);
        ActivityPainelGraficoMetas.UsuarioDrawerAdapter adapter = new ActivityPainelGraficoMetas.UsuarioDrawerAdapter(lista, user -> {
            toolbar.setTitle(user.getNome_usuario());
            this.idUsuarioSelecionado = Integer.parseInt(user.getId_usuario());
            carregarTodosOsGraficos(idUsuarioSelecionado);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        rvUsuarios.setLayoutManager(new LinearLayoutManager(this));
        rvUsuarios.setAdapter(adapter);
    }

    @butterknife.OnClick({R.id.btnRefreshSemanal, R.id.btnRefreshGrupo, R.id.btnRefreshMensal, R.id.btnCalendarioSemanal})
    public void onRefreshClick(View view) {
        int id = view.getId();
        // String dataInicio = getPrimeiroDiaMesAtual(); // Comentado para usar seleção global
        String dataInicio = getDataReferencia(anoAtual, mesAtual);
        if (id == R.id.btnRefreshSemanal) {
            flipCard(context, lyt2, lyt1, progressBar1, 1);
            getEvolucaoVendasSemana(idUsuarioSelecionado, 0, dataInicio);
        } else if (id == R.id.btnRefreshGrupo) {
            flipCard(context, lyt6, lyt5, progressBar3, 1);
            getEvolucaoVendasGrupo(idUsuarioSelecionado, 0, dataInicio);
        } else if (id == R.id.btnRefreshMensal) {
            flipCard(context, lyt4, lyt3, progressBar2, 1);
            getEvolucaoVendasMes(idUsuarioSelecionado, 0, dataInicio);
        } else if (id == R.id.btnCalendarioSemanal) {
            exibirCalendario();
        }
    }

    private void exibirCalendario() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            // Apenas para visualização, não fazemos nada com a data selecionada
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle("Calendário de Referência");
        dialog.show();
    }

    private void showLoading(String title) {
        activeRequests = 3;
        tvGlobalLoadingTitle.setText(title);
        lytGlobalLoading.setVisibility(View.VISIBLE);
    }

    private void checkRequestFinished() {
        if (activeRequests > 0) {
            activeRequests--;
        }
        if (activeRequests <= 0) {
            lytGlobalLoading.setVisibility(View.GONE);
        }
    }

    private void getConfiguracao() {
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
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

        api.getSomaTotalRealizadoSemanaPeriodo(idUsuario, dataInicio, cabecalho).enqueue(new Callback<List<MesSemana>>() {
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
                        
                        // Parse robusto: Identifica o formato (BR 1.000,00 ou US 1000.00)
                        double valorNum = 0;
                        try {
                            String v = ms.getVenda_liquida().trim();
                            if (v.contains(",")) {
                                // Formato Brasileiro: 6.653,60 -> 6653.60
                                valorNum = Double.parseDouble(v.replace(".", "").replace(",", "."));
                            } else {
                                // Formato Standard/JSON: 6653.60 -> 6653.60
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
                            // Abate do saldo semanal Apenas se a relação comercial for 'S'
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
                        // IMPORTANTE: Salva com vírgula para enganar o cleanMoney e evitar divisão por 100
                        ms.setVenda_liquida(String.format(java.util.Locale.US, "%.2f", totalLiquido).replace(".", ","));
                        listaFinal.add(ms);
                    }
                    carregarMetasDoFirebase1(true, "S", 2026, idVendedor, listaFinal);
                } else { 
                    checkRequestFinished(); 
                    flipCard(context, lyt1, lyt2, progressBar1, 1);
                    barChart1.setNoDataText("Sem dados de faturamento.");
                    barChart1.setData(null);
                    barChart1.invalidate();
                }
            }
            @Override public void onFailure(Call<List<MesSemana>> call, Throwable t) { 
                checkRequestFinished(); 
                flipCard(context, lyt1, lyt2, progressBar1, 1);
                barChart1.setNoDataText("Falha na conexão.");
                barChart1.invalidate();
            }
        });
    }

    private void getEvolucaoVendasMes(int idUsuario, int idx, String dataInicio) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        getConfiguracao();
        
        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            checkRequestFinished();
            flipCard(context, lyt3, lyt4, progressBar2, 1);
            barChart2.setNoDataText("Configuração de API inválida.");
            barChart2.invalidate();
            return;
        }

        api.getSomaTotalRealizadoMesPeriodo(idUsuario, dataInicio, "4", cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<MesAnoMeta> listaOriginal = response.body();
                    Map<String, MesAnoMeta> mapaMeses = new java.util.LinkedHashMap<>();
                    Map<String, Double> valoresMeses = new java.util.HashMap<>();

                    for (MesAnoMeta mam : listaOriginal) {
                        String chave = (mam.getNome_mes() != null ? mam.getNome_mes().toUpperCase().trim() : "SEM_MES");
                        
                        // Parse robusto: Identifica o formato (BR 1.000,00 ou US 1000.00)
                        double valorNum = 0;
                        try {
                            String v = mam.getVenda_liquida().trim();
                            if (v.contains(",")) {
                                valorNum = Double.parseDouble(v.replace(".", "").replace(",", "."));
                            } else {
                                valorNum = Double.parseDouble(v);
                            }
                        } catch (Exception e) { valorNum = 0; }

                        boolean isDevolucao = mam.getE_entrada_s_saida() != null && mam.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                        boolean relComercialSim = mam.getFigura_rel_comercial() != null && mam.getFigura_rel_comercial().trim().equalsIgnoreCase("S");
                        
                        double acumulado = valoresMeses.containsKey(chave) ? valoresMeses.get(chave) : 0.0;

                        /* CÓDIGO ANTERIOR:
                        double novoSaldo = isDevolucao ? (acumulado - Math.abs(valorNum)) : (acumulado + Math.abs(valorNum));
                        valoresMeses.put(chave, novoSaldo);
                        */

                        if (isDevolucao) {
                            // Abate do saldo mensal Apenas se a relação comercial for 'S'
                            if (relComercialSim) {
                                valoresMeses.put(chave, acumulado - Math.abs(valorNum));
                            } else {
                                valoresMeses.put(chave, acumulado);
                            }
                        } else {
                            valoresMeses.put(chave, acumulado + Math.abs(valorNum));
                        }
                        
                        if (!mapaMeses.containsKey(chave)) {
                            mapaMeses.put(chave, mam);
                        }
                    }

                    List<MesAnoMeta> listaFinal = new ArrayList<>();
                    for (String chave : mapaMeses.keySet()) {
                        MesAnoMeta mam = mapaMeses.get(chave);
                        double totalLiquido = valoresMeses.get(chave);
                        // IMPORTANTE: Salva com vírgula para enganar o cleanMoney e evitar divisão por 100
                        mam.setVenda_liquida(String.format(java.util.Locale.US, "%.2f", totalLiquido).replace(".", ","));
                        listaFinal.add(mam);
                    }
                    carregarMetasDoFirebase2(true, "M", 2026, 13, listaFinal);
                } else { 
                    checkRequestFinished(); 
                    flipCard(context, lyt3, lyt4, progressBar2, 1);
                    barChart2.setNoDataText("Sem dados mensais.");
                    barChart2.setData(null);
                    barChart2.invalidate();
                }
            }
            @Override public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) { 
                checkRequestFinished(); 
                flipCard(context, lyt3, lyt4, progressBar2, 1);
                barChart2.setNoDataText("Falha na conexão.");
                barChart2.invalidate();
            }
        });
    }

    private float parseMoney(String valor) {
        if (valor == null || valor.trim().isEmpty()) return 0f;
        try {
            String v = valor.trim();
            if (v.contains(",")) {
                v = v.replace(".", "").replace(",", ".");
            } else if (v.contains(".")) {
                int lastDot = v.lastIndexOf(".");
                String integerPart = v.substring(0, lastDot).replace(".", "");
                String decimalPart = v.substring(lastDot + 1);
                v = integerPart + "." + decimalPart;
            }
            return Float.parseFloat(v);
        } catch (Exception e) {
            return 0f;
        }
    }

    private void getEvolucaoVendasGrupo(int idUsuario, int idx, String dataInicio) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        getConfiguracao();
        
        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            checkRequestFinished();
            flipCard(context, lyt5, lyt6, progressBar3, 1);
            barChart3.setNoDataText("Configuração de API inválida.");
            barChart3.invalidate();
            return;
        }

        api.getSomaTotalRealizadoGrupoPeriodo(idUsuario, dataInicio, cabecalho).enqueue(new Callback<List<MesGrupoMeta>>() {
            @Override
            public void onResponse(Call<List<MesGrupoMeta>> call, Response<List<MesGrupoMeta>> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<MesGrupoMeta> listaOriginal = response.body();
                    Map<String, MesGrupoMeta> mapaGrupos = new java.util.LinkedHashMap<>();
                    Map<String, Float> valoresGrupos = new java.util.HashMap<>();

                    /* CÓDIGO ANTERIOR (Duas etapas com linha comentada):
                    for (MesGrupoMeta mgm : listaOriginal) {
                        boolean isDevolucao = mgm.getE_entrada_s_saida() != null && mgm.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                        if (!isDevolucao) {
                            String chave = (mgm.getId_grupo() != null ? mgm.getId_grupo().trim() : "SEM_ID");
                            float valorItem = parseMoney(mgm.getVenda_liquida());
                            float acumulado = valoresGrupos.containsKey(chave) ? valoresGrupos.get(chave) : 0f;
                            valoresGrupos.put(chave, acumulado + valorItem);
                            if (!mapaGrupos.containsKey(chave)) mapaGrupos.put(chave, mgm);
                        }
                    }
                    for (MesGrupoMeta mgm : listaOriginal) {
                        boolean isDevolucao = mgm.getE_entrada_s_saida() != null && mgm.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                        boolean relComercialSim = mgm.getFigura_rel_comercial() != null && mgm.getFigura_rel_comercial().trim().equalsIgnoreCase("S");
                        if (isDevolucao && relComercialSim) {
                            String chave = (mgm.getId_grupo() != null ? mgm.getId_grupo().trim() : "SEM_ID");
                            float valorItem = parseMoney(mgm.getVenda_liquida());
                            if (valoresGrupos.containsKey(chave)) {
                                float acumulado = valoresGrupos.get(chave);
                                valoresGrupos.put(chave, (acumulado - valorItem));
                            }
                        }
                    }
                    */

                    // NOVO: Loop padronizado acumulando saídas e abatendo entradas (com figura_rel_comercial == "S")
                    for (MesGrupoMeta mgm : listaOriginal) {
                        String chave = (mgm.getId_grupo() != null ? mgm.getId_grupo().trim() : "SEM_ID");
                        float valorItem = parseMoney(mgm.getVenda_liquida());
                        boolean isDevolucao = mgm.getE_entrada_s_saida() != null && mgm.getE_entrada_s_saida().trim().equalsIgnoreCase("E");
                        boolean relComercialSim = mgm.getFigura_rel_comercial() != null && mgm.getFigura_rel_comercial().trim().equalsIgnoreCase("S");

                        float acumulado = valoresGrupos.containsKey(chave) ? valoresGrupos.get(chave) : 0f;

                        if (isDevolucao) {
                            // Abate do saldo do grupo Apenas se a relação comercial for 'S'
                            if (relComercialSim) {
                                valoresGrupos.put(chave, acumulado - Math.abs(valorItem));
                            } else {
                                valoresGrupos.put(chave, acumulado);
                            }
                        } else {
                            valoresGrupos.put(chave, acumulado + Math.abs(valorItem));
                        }

                        if (!mapaGrupos.containsKey(chave)) {
                            mapaGrupos.put(chave, mgm);
                        }
                    }

                    List<MesGrupoMeta> listaFinal = new ArrayList<>();
                    for (String chave : mapaGrupos.keySet()) {
                        MesGrupoMeta mgm = mapaGrupos.get(chave);
                        float totalLiquido = valoresGrupos.get(chave);
                        // Atualiza a venda líquida no objeto com vírgula para leitura compatível com o gráfico
                        mgm.setVenda_liquida(String.format(java.util.Locale.US, "%.2f", totalLiquido).replace(".", ","));
                        listaFinal.add(mgm);
                        android.util.Log.d("RCK_METAS_GRUPO", "Processado API -> ID Grupo: " + mgm.getId_grupo() + " | Nome: " + mgm.getNome_grupo() + " | Total Líquido: " + mgm.getVenda_liquida());
                    }
                    carregarMetasDoFirebase3(true, "G", 2026, 13, listaFinal);
                } else {
                    checkRequestFinished(); 
                    flipCard(context, lyt5, lyt6, progressBar3, 1);
                    barChart3.setNoDataText("Sem dados por grupo.");
                    barChart3.setData(null);
                    barChart3.invalidate();
                }
            }
            @Override public void onFailure(Call<List<MesGrupoMeta>> call, Throwable t) { 
                checkRequestFinished(); 
                flipCard(context, lyt5, lyt6, progressBar3, 1);
                barChart3.setNoDataText("Falha na conexão.");
                barChart3.invalidate();
            }
        });
    }

    private void carregarMetasDoFirebase1(boolean p, String t, int ano, int idV, List<MesSemana> lista) {
        new FirestoreConexao().getMetaSemanal(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), idVendedorFirestore, String.valueOf(mesAtual), new FirestoreConexao.OnMetaSemanalRetorno() {
            @Override public void onSucesso(MetaSemanal m) { 
                checkRequestFinished(); 
                if (m != null) {
                    createbarChart1(lista, m); 
                } else {
                    flipCard(context, lyt1, lyt2, progressBar1, 1);
                    barChart1.setNoDataText("Metas não definidas");
                    barChart1.setData(null);
                    barChart1.invalidate();
                }
            }
            @Override public void onFalha(Exception e) { 
                checkRequestFinished(); 
                flipCard(context, lyt1, lyt2, progressBar1, 1);
                barChart1.setNoDataText("Erro ao carregar meta semanal.");
                barChart1.invalidate();
            }
        });
    }

    private void carregarMetasDoFirebase2(boolean p, String t, int ano, int idV, List<MesAnoMeta> lista) {
        new FirestoreConexao().getMetaMesAno(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), idVendedorFirestore, new FirestoreConexao.OnMetaRetorno() {
            @Override public void onSucesso(MesAno m) { 
                checkRequestFinished(); 
                if (m != null) {
                    createbarChart2(lista, m); 
                } else {
                    flipCard(context, lyt3, lyt4, progressBar2, 1);
                    barChart2.setNoDataText("Metas não definidas");
                    barChart2.setData(null);
                    barChart2.invalidate();
                }
            }
            @Override public void onFalha(Exception e) { 
                checkRequestFinished(); 
                flipCard(context, lyt3, lyt4, progressBar2, 1);
                barChart2.setNoDataText("Erro ao carregar meta mensal.");
                barChart2.invalidate();
            }
        });
    }

    private void carregarMetasDoFirebase3(boolean p, String t, int ano, int idV, List<MesGrupoMeta> lista) {
        new FirestoreConexao().getTodasMetasSubOpcao(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), idVendedorFirestore, t, new FirestoreConexao.OnListaMetasRetorno() {
            @Override public void onSucesso(List<MesAnoGrupoMarca> m) { 
                checkRequestFinished(); 
                if (m != null && !m.isEmpty()) {
                    createbarChart3(lista, m); 
                } else {
                    flipCard(context, lyt5, lyt6, progressBar3, 1);
                    barChart3.setNoDataText("Metas não definidas");
                    barChart3.setData(null);
                    barChart3.invalidate();
                }
            }
            @Override public void onFalha(Exception e) { 
                checkRequestFinished(); 
                flipCard(context, lyt5, lyt6, progressBar3, 1);
                barChart3.setNoDataText("Erro ao carregar metas de grupos.");
                barChart3.invalidate();
            }
        });
    }

    private float getMetaDoMes(MesAno m, String nomeMes) {
        if (m == null) return 0f;
        if (nomeMes != null && !nomeMes.trim().isEmpty()) {
            String mes = nomeMes.toUpperCase();
            if (mes.contains("JANEIRO") || mes.contains("JAN")) return cleanMoney(m.getJan());
            if (mes.contains("FEVEREIRO") || mes.contains("FEV")) return cleanMoney(m.getFev());
            if (mes.contains("MARÇO") || mes.contains("MAR")) return cleanMoney(m.getMar());
            if (mes.contains("ABRIL") || mes.contains("ABR")) return cleanMoney(m.getAbr());
            if (mes.contains("MAIO") || mes.contains("MAI")) return cleanMoney(m.getMai());
            if (mes.contains("JUNHO") || mes.contains("JUN")) return cleanMoney(m.getJun());
            if (mes.contains("JULHO") || mes.contains("JUL")) return cleanMoney(m.getJul());
            if (mes.contains("AGOSTO") || mes.contains("AGO")) return cleanMoney(m.getAgo());
            if (mes.contains("SETEMBRO") || mes.contains("SET")) return cleanMoney(m.getSet());
            if (mes.contains("OUTUBRO") || mes.contains("OUT")) return cleanMoney(m.getOut());
            if (mes.contains("NOVEMBRO") || mes.contains("NOV")) return cleanMoney(m.getNov());
            if (mes.contains("DEZEMBRO") || mes.contains("DEZ")) return cleanMoney(m.getDez());
        }
        // Fallback para o mês selecionado/atual
        return getMetaDoMes(m, mesAtual);
    }

    private float getMetaDoMes(MesAno m, int mes) {
        if (m == null) return 0f;
        switch (mes) {
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

    private void createbarChart3(List<MesGrupoMeta> listaAPI, List<MesAnoGrupoMarca> listaMetasFirebase) {
        flipCard(context, lyt5, lyt6, progressBar3, 1);
        if (listaAPI == null || listaAPI.isEmpty()) {
            barChart3.setNoDataText("Sem faturamento por grupo.");
            barChart3.setData(null);
            barChart3.invalidate();
            return;
        }
        double totalR = 0, totalM = 0;
        Collections.sort(listaAPI, (o1, o2) -> Float.compare(parseMoney(o2.getVenda_liquida()), parseMoney(o1.getVenda_liquida())));
        /* CÓDIGO ANTERIOR (Limitação rígida que cortava a lista de grupos):
        int limit = getResources().getConfiguration().smallestScreenWidthDp >= 600 ? 15 : 10;
        if (listaAPI.size() > limit) listaAPI = listaAPI.subList(0, limit);
        */

        String[] labels = new String[listaAPI.size()];
        ArrayList<BarEntry> b1 = new ArrayList<>(), b2 = new ArrayList<>();
        List<GradientColor> c1 = new ArrayList<>(), c2 = new ArrayList<>();
        for (int i = 0; i < listaAPI.size(); i++) {
            MesGrupoMeta item = listaAPI.get(i);
            labels[i] = item.getNome_grupo().length() > 8 ? item.getNome_grupo().substring(0, 8) + "." : item.getNome_grupo();
            float vR = parseMoney(item.getVenda_liquida()), vM = 0f;

            for (MesAnoGrupoMarca meta : listaMetasFirebase) {
                boolean matchCodigo = false;
                if (meta.getCodigo() != null && item.getId_grupo() != null) {
                    if (meta.getCodigo().equalsIgnoreCase(item.getId_grupo().trim())) {
                        matchCodigo = true;
                    } else {
                        try {
                            if (Integer.parseInt(meta.getCodigo().trim()) == Integer.parseInt(item.getId_grupo().trim())) {
                                matchCodigo = true;
                            }
                        } catch (Exception ignored) {}
                    }
                }

                if (matchCodigo) {
                    vM = getMetaDoMes(meta.getMesAno(), item.getNome_mes());
                    break;
                }
            }

            //android.util.Log.d("RCK_METAS_GRUPO", "Grafico Bar " + i + " | Grupo: " + item.getNome_grupo() + " (ID: " + item.getId_grupo() + ") | Realizado (vR): " + vR + " | Meta (vM): " + vM);
            
            // Lógica de Transparência
            int alphaReal = (vR <= 0 && vM > 0) ? 35 : 255;
            int alphaMeta = (vM <= 0 && vR > 0) ? 35 : 255;

            // Cores Semafóricas para o Realizado
            c1.add(getSemanticGradient(vR, vM, alphaReal));
            
            // Meta com cor fixa azulada para contraste
            c2.add(new GradientColor(
                    adjustAlpha(ColorTemplateApp.GRADIENT_START_6[2], alphaMeta), 
                    adjustAlpha(ColorTemplateApp.GRADIENT_END_6[2], alphaMeta)
            ));

            /* CÓDIGO ANTERIOR:
            b1.add(new BarEntry(i, vR <= 0 && vM > 0 ? vM : vR, vR <= 0 ? "HIDELABEL" : null));
            b2.add(new BarEntry(i, vM <= 0 && vR > 0 ? vR : vM, vM <= 0 ? "HIDELABEL" : null));
            */

            b1.add(new BarEntry(i, vR, vR <= 0 ? "HIDELABEL" : null));
            b2.add(new BarEntry(i, vM, vM <= 0 ? "HIDELABEL" : null));
            totalR += vR; totalM += vM;
        }
        tvKpiReal3.setText(MascaraUtil.mascaraRealSimboloD(totalR));
        tvKpiMeta3.setText("Meta: " + MascaraUtil.mascaraRealSimboloD(totalM));
        
        if (totalM > 0) {
            int perc = (int)((totalR/totalM)*100);
            tvKpiPerc3.setText(perc + "% atingido");
            pbKpi3.setProgress(perc);
        } else {
            tvKpiPerc3.setText("0% atingido");
            pbKpi3.setProgress(0);
        }

        barChart3.setData(new BarData(configureSet(b1, c1, "Real"), configureSet(b2, c2, "Meta")));
        configureChart(barChart3, labels, -45f);

        // Habilita a rolagem horizontal caso existam mais grupos do que o limite de exibição visível inicial
        int visibleLimit = getResources().getConfiguration().smallestScreenWidthDp >= 600 ? 8 : 5;
        if (labels.length > visibleLimit) {
            barChart3.setDragEnabled(true);
            barChart3.setScaleEnabled(false);
            barChart3.setVisibleXRangeMaximum((float) visibleLimit);
            barChart3.moveViewToX(0f);
        }
    }

    private void createbarChart1(List<MesSemana> lista, MetaSemanal metaSemanal) {
        flipCard(context, lyt1, lyt2, progressBar1, 1);
        if (lista == null || lista.isEmpty()) {
            barChart1.setNoDataText("Sem faturamento para esta semana.");
            barChart1.setData(null);
            barChart1.invalidate();
            return;
        }
        double totalR = 0, totalM = cleanMoney(metaSemanal.getSemana1()) + cleanMoney(metaSemanal.getSemana2()) + cleanMoney(metaSemanal.getSemana3()) + cleanMoney(metaSemanal.getSemana4()) + cleanMoney(metaSemanal.getSemana5()) + cleanMoney(metaSemanal.getSemana6());
        String[] labels = new String[lista.size()];
        ArrayList<BarEntry> b1 = new ArrayList<>(), b2 = new ArrayList<>();
        List<GradientColor> colorsSet1 = new ArrayList<>();
        List<GradientColor> colorsSet2 = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            labels[i] = (i + 1) + "º SEM.";
            float vR = cleanMoney(lista.get(i).getVenda_liquida()), vM = 0f;
            if (i == 0) vM = cleanMoney(metaSemanal.getSemana1());
            else if (i == 1) vM = cleanMoney(metaSemanal.getSemana2());
            else if (i == 2) vM = cleanMoney(metaSemanal.getSemana3());
            else if (i == 3) vM = cleanMoney(metaSemanal.getSemana4());
            else if (i == 4) vM = cleanMoney(metaSemanal.getSemana5());
            else if (i == 5) vM = cleanMoney(metaSemanal.getSemana6());

            // Lógica de Transparência
            int alphaReal = (vR <= 0 && vM > 0) ? 35 : 255;
            int alphaMeta = (vM <= 0 && vR > 0) ? 35 : 255;

            // Cores Semafóricas para o Realizado
            colorsSet1.add(getSemanticGradient(vR, vM, alphaReal));
            
            // Meta com cor fixa azulada
            colorsSet2.add(new GradientColor(
                    adjustAlpha(ColorTemplateApp.GRADIENT_START_6[2], alphaMeta), 
                    adjustAlpha(ColorTemplateApp.GRADIENT_END_6[2], alphaMeta)
            ));

            // CORREÇÃO: Não troca o valor vR por vM se for zero. Mantém o valor real para o gráfico.
            b1.add(new BarEntry(i, vR, vR <= 0 ? "HIDELABEL" : null));
            b2.add(new BarEntry(i, vM, vM <= 0 ? "HIDELABEL" : null));
            totalR += vR;
        }
        this.valorRealizadoMesAtual = totalR;
        this.valorMetaMesAtual = totalM;
        
        atualizarLabelsCardConsolidado();

        barChart1.setData(new BarData(configureSet(b1, colorsSet1, "Real"), configureSet(b2, colorsSet2, "Meta")));
        configureChart(barChart1, labels, 0f);
        barChart1.invalidate();
    }

    private void atualizarLabelsCardConsolidado() {
        tvKpiReal1.setText(MascaraUtil.mascaraRealSimboloD(valorRealizadoMesAtual));
        
        String info = "Meta: " + MascaraUtil.mascaraRealSimboloD(valorMetaMesAtual);
        if (valorRealizadoMesAnterior > 0) {
            info += " | Ant: " + MascaraUtil.mascaraRealSimboloD(valorRealizadoMesAnterior);
        }
        tvKpiMeta1.setText(info);

        if (valorRealizadoMesAnterior > 0) {
            int percCrescimento = (int) ((valorRealizadoMesAtual / valorRealizadoMesAnterior) * 100);
            tvKpiPerc1.setText(percCrescimento + "% do mês anterior");
            pbKpi1.setProgress(Math.min(percCrescimento, 100));
        } else if (valorMetaMesAtual > 0) {
            // Fallback para meta se não houver mês anterior carregado ainda
            int percMeta = (int) ((valorRealizadoMesAtual / valorMetaMesAtual) * 100);
            tvKpiPerc1.setText(percMeta + "% atingido");
            pbKpi1.setProgress(percMeta);
        }
    }

    private void createbarChart2(List<MesAnoMeta> lista, MesAno metaMensal) {
        flipCard(context, lyt3, lyt4, progressBar2, 1);
        if (lista == null || lista.isEmpty()) {
            barChart2.setNoDataText("Sem faturamento para o mês.");
            barChart2.setData(null);
            barChart2.invalidate();
            return;
        }
        
        double totalR = 0; 
        float vendedorMesAtual = 0;
        String[] mesesNomes = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
        String nomeMesProcurado = mesesNomes[mesAtual - 1];

        String[] labels = new String[lista.size()];
        ArrayList<BarEntry> b1 = new ArrayList<>(), b2 = new ArrayList<>();
        List<GradientColor> colorsSet1 = new ArrayList<>();
        List<GradientColor> colorsSet2 = new ArrayList<>();
        
        for (int i = 0; i < lista.size(); i++) {
            MesAnoMeta item = lista.get(i);
            labels[i] = item.getNome_mes();
            float vR = cleanMoney(item.getVenda_liquida()), vM = getMetaDoMes(metaMensal, item.getNome_mes());

            // Identifica o realizado do vendedor especificamente para o mês de referência
            if (item.getNome_mes() != null && item.getNome_mes().toUpperCase().contains(nomeMesProcurado)) {
                vendedorMesAtual = vR;
            }

            // Lógica de Transparência
            int alphaReal = (vR <= 0 && vM > 0) ? 35 : 255;
            int alphaMeta = (vM <= 0 && vR > 0) ? 35 : 255;

            // Cores Semafóricas para o Realizado
            colorsSet1.add(getSemanticGradient(vR, vM, alphaReal));
            
            // Meta com cor fixa azulada
            colorsSet2.add(new GradientColor(
                    adjustAlpha(ColorTemplateApp.GRADIENT_START_6[2], alphaMeta), 
                    adjustAlpha(ColorTemplateApp.GRADIENT_END_6[2], alphaMeta)
            ));

            // CORREÇÃO: Não troca o valor vR por vM se for zero. Mantém o valor real para o gráfico.
            b1.add(new BarEntry(i, vR, vR <= 0 ? "HIDELABEL" : null));
            b2.add(new BarEntry(i, vM, vM <= 0 ? "HIDELABEL" : null));
            totalR += vR;
        }

        // Se não encontrou o mês pelo nome (API pode retornar diferente), usa o último da lista como fallback
        if (vendedorMesAtual == 0 && !lista.isEmpty()) {
            vendedorMesAtual = cleanMoney(lista.get(lista.size() - 1).getVenda_liquida());
        }

        // AJUSTE CARD 2: META EMPRESA EM DESTAQUE (COM DADOS REAIS DO FIRESTORE)
        double metaEmpresa = valorMetaEmpresaGlobal; 
        
        // Se a meta global ainda não carregou ou for zero, mantém o texto informativo
        if (metaEmpresa > 0) {
            tvKpiReal2.setText(MascaraUtil.mascaraRealSimboloD(metaEmpresa));
        }
        
        tvKpiMeta2.setText("Seu Realizado: " + MascaraUtil.mascaraRealSimboloD((double) vendedorMesAtual));
        
        if (metaEmpresa > 0) {
            double percPart = (vendedorMesAtual / metaEmpresa) * 100;
            // Formata com 2 casas decimais para maior precisão em valores altos
            tvKpiPerc2.setText(String.format(java.util.Locale.US, "%.2f%% da empresa", percPart));
            pbKpi2.setProgress((int) Math.min(percPart, 100));
        } else {
            tvKpiPerc2.setText("0.00% da empresa");
            pbKpi2.setProgress(0);
        }

        // NOVO: Captura mês anterior para o Card 1
        String[] mesesNomesLongos = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
        String nomeMesAnterior = mesesNomesLongos[mesAtual == 1 ? 11 : mesAtual - 2];
        for (MesAnoMeta item : lista) {
            if (item.getNome_mes() != null && item.getNome_mes().toUpperCase().contains(nomeMesAnterior)) {
                this.valorRealizadoMesAnterior = cleanMoney(item.getVenda_liquida());
                atualizarLabelsCardConsolidado(); // Atualiza o Card 1 com a nova informação do mês anterior
                break;
            }
        }

        barChart2.setData(new BarData(configureSet(b1, colorsSet1, "Real"), configureSet(b2, colorsSet2, "Meta")));
        configureChart(barChart2, labels, 0f);
    }

    private BarDataSet configureSet(ArrayList<BarEntry> entries, List<GradientColor> colors, String label) {
        BarDataSet set = new BarDataSet(entries, label);
        set.setGradientColors(colors);
        set.setHighlightEnabled(false);
        return set;
    }

    private void configureChart(BarChart chart, String[] labels, float rotation) {
        chart.setDrawBarShadow(false); chart.getDescription().setEnabled(false); chart.setPinchZoom(true); chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis(); xAxis.setCenterAxisLabels(true); xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false); xAxis.setGranularity(1f); xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelRotationAngle(rotation);
        xAxis.setLabelCount(labels.length); 
        if (rotation != 0) chart.setExtraBottomOffset(15f);
        xAxis.setValueFormatter(new ValueFormatter() { @Override public String getFormattedValue(float v) { try { int idx = (int) v; return (idx >= 0 && idx < labels.length) ? labels[idx] : ""; } catch (Exception e) { return ""; } } });
        
        YAxis left = chart.getAxisLeft(); 
        left.setDrawLabels(false); 
        left.setDrawGridLines(false); 
        left.setDrawAxisLine(false);
        left.setAxisMinimum(0f); // GARANTE QUE O GRÁFICO COMECE NO ZERO REAL
        left.setSpaceBottom(0f); // REMOVE QUALQUER MARGEM NO FUNDO
        left.setSpaceTop(15f);   // RESERVA ESPAÇO APENAS NO TOPO PARA O TEXTO DO VALOR

        chart.getAxisRight().setEnabled(false); chart.getLegend().setEnabled(false);
        chart.getData().setBarWidth(0.35f); chart.getData().setValueTextSize(9f); chart.getData().setValueFormatter(new CustomValueFormatter());
        chart.getXAxis().setAxisMinimum(0f); chart.getXAxis().setAxisMaximum(labels.length);
        chart.groupBars(0f, 0.20f, 0.05f); chart.invalidate(); chart.animateXY(1500, 1500);
    }

    public class CustomValueFormatter extends ValueFormatter {
        @Override public String getFormattedValue(float v) { return MascaraUtil.mascaraRealSemSimbolo(v); }
        @Override public String getBarLabel(BarEntry e) { return "HIDELABEL".equals(e.getData()) ? "" : super.getBarLabel(e); }
    }

    private int adjustAlpha(int color, int alpha) { return (alpha << 24) | (color & 0x00ffffff); }

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

    private void flipCard(Context context, View vV, View iV, ProgressBar pB, int idC) {
        if (pB != null) { pB.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, idC == 1 ? R.color.colorPrimary : R.color.colorButton), PorterDuff.Mode.SRC_IN); }
        try {
            iV.setVisibility(View.INVISIBLE);
            @SuppressLint("ResourceType") AnimatorSet fOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.flip_out);
            @SuppressLint("ResourceType") AnimatorSet fIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.flip_in_right);
            fOut.setTarget(iV); fIn.setTarget(vV); fOut.start(); fIn.start(); vV.setVisibility(View.VISIBLE);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {}
    @Override public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {}
    @Override public void onChartLongPressed(MotionEvent me) {}
    @Override public void onChartDoubleTapped(MotionEvent me) {}
    @Override public void onChartSingleTapped(MotionEvent me) {}
    @Override public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {}
    @Override public void onChartScale(MotionEvent me, float scaleX, float scaleY) {}
    @Override public void onChartTranslate(MotionEvent me, float dX, float dY) {}
    @Override public void onValueSelected(Entry e, Highlight h) {}
    @Override public void onNothingSelected() {}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_painel_grafico_metas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            compartilharDashboard();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void compartilharDashboard() {
        try {
            Bitmap bitmap = getBitmapFromView(scrollViewDashboard);
            // Salva na pasta de cache para não precisar de permissão de escrita externa em Androids novos
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "dashboard_metas.png");
            FileOutputStream stream = new FileOutputStream(file);
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
            e.printStackTrace();
            Toast.makeText(context, "Erro ao gerar imagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        // Captura a altura total do conteúdo dentro do ScrollView
        int height = 0;
        if (view instanceof ScrollView) {
            if (((ScrollView) view).getChildAt(0) != null) {
                height = ((ScrollView) view).getChildAt(0).getHeight();
            } else {
                height = view.getHeight();
            }
        } else {
            height = view.getHeight();
        }

        // Evita erro de bitmap com altura/largura zero
        if (height <= 0) height = 1000;
        int width = view.getWidth() > 0 ? view.getWidth() : 1000;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Se for um ScrollView, precisamos desenhar o fundo branco antes, 
        // senão as partes não visíveis podem ficar pretas ou transparentes
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) { return false; }

    public class UsuarioDrawerAdapter extends RecyclerView.Adapter<UsuarioDrawerAdapter.ViewHolder> {
        private List<Usuario> usuarios;
        private ActivityPainelGraficoFaturamento.OnUsuarioClickListener listener;
        public UsuarioDrawerAdapter(List<Usuario> usuarios, ActivityPainelGraficoFaturamento.OnUsuarioClickListener listener) { this.usuarios = usuarios; this.listener = listener; }
        @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario_drawer, parent, false)); }
        @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { Usuario u = usuarios.get(position); holder.tv.setText(u.getNome_usuario()); holder.itemView.setOnClickListener(v -> { if (listener != null) listener.onUsuarioClick(u); }); }
        @Override public int getItemCount() { return usuarios.size(); }
        public class ViewHolder extends RecyclerView.ViewHolder { TextView tv; public ViewHolder(View v) { super(v); tv = v.findViewById(R.id.tvNomeUsuario); } }
    }
}
