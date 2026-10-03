package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
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
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.example.rcksuporte05.rcksistemas.util.ColorTemplateApp;
import com.example.rcksuporte05.rcksistemas.Helper.GraficoFaturamentoHelper;
import com.google.android.material.navigation.NavigationView;

import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.utils.ViewPortHandler;
import android.graphics.Paint;
import com.github.mikephil.charting.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import android.content.Intent;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPainelFinanceiro extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvDataPesquisa)
    TextView tvDataPesquisa;

    @BindView(R.id.tvKpiVencidos)
    TextView tvKpiVencidos;

    @BindView(R.id.tvKpiAVencer)
    TextView tvKpiAVencer;

    @BindView(R.id.tvKpiRecebidos)
    TextView tvKpiRecebidos;

    @BindView(R.id.tvKpiTotalCarteira)
    TextView tvKpiTotalCarteira;

    @BindView(R.id.tvPeriodoReferencia)
    TextView tvPeriodoReferencia;

    @BindView(R.id.btnSelecionarPeriodoFinanceiro)
    View btnSelecionarPeriodoFinanceiro;

    @BindView(R.id.btnRefreshFluxo)
    ImageView btnRefreshFluxo;

    @BindView(R.id.btnRefreshReceber)
    ImageView btnRefreshReceber;

    @BindView(R.id.btnRefreshInad)
    ImageView btnRefreshInad;

    @BindView(R.id.btnAnaliseFluxo)
    ImageView btnAnaliseFluxo;

    @BindView(R.id.btnAnaliseReceber)
    ImageView btnAnaliseReceber;

    @BindView(R.id.btnAnaliseInad)
    ImageView btnAnaliseInad;

    @BindView(R.id.chartFluxo)
    BarChart chartFluxo;

    @BindView(R.id.chartReceber)
    BarChart chartReceber;

    @BindView(R.id.chartInadimplentes)
    HorizontalBarChart chartInadimplentes;

    @BindView(R.id.scrollViewDashboard)
    View scrollViewDashboard;

    @BindView(R.id.lytGlobalLoading)
    View lytGlobalLoading;

    @BindView(R.id.tvGlobalLoadingTitle)
    TextView tvGlobalLoadingTitle;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navView)
    NavigationView navigationView;

    private Context context;
    private DBHelper db;
    private Configuracao configuracao;
    private int diasReferencia = 30;
    private int idUsuarioSelecionado = 999;

    private List<Financeiro> listaVencidos = new ArrayList<>();
    private List<Financeiro> listaAVencer = new ArrayList<>();
    private List<Financeiro> listaQuitados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_financeiro);
        ButterKnife.bind(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        context = this;
        db = new DBHelper(this);
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //if (UsuarioHelper.getUsuario() != null) {
            //    getSupportActionBar().setTitle(UsuarioHelper.getUsuario().getNome_usuario());
            //} else {
            getSupportActionBar().setTitle("Painel Financeiro");
            //}
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Configuração do Drawer Toggle (Ícone de abrir/fechar menu)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Inicializa o Filtro de Usuário no Drawer
        configurarDrawerFiltro();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        atualizarDataProcessamento();
        carregarDadosDash(30);

        chartFluxo.setOnClickListener(v -> {
            if (listaAVencer != null && !listaAVencer.isEmpty()) {
                GraficoFaturamentoHelper.setDiasReferenciaFinanceiro(diasReferencia);
                GraficoFaturamentoHelper.setListaFinanceiro(listaAVencer); // NOVO: Salva no Helper para evitar TransactionTooLarge
                Intent intent = new Intent(context, ActivityGraficoFaturamentoCarrosel.class);
                intent.putExtra("tipoRelatorio", "FIN_AV");
                // intent.putExtra("lista", (Serializable) listaAVencer); // COMENTADO: Evita erro de limite de 1MB
                startActivity(intent);
            }
        });

        chartReceber.setOnClickListener(v -> {
            if (listaVencidos != null && !listaVencidos.isEmpty()) {
                GraficoFaturamentoHelper.setDiasReferenciaFinanceiro(diasReferencia);
                GraficoFaturamentoHelper.setListaFinanceiro(listaVencidos); // NOVO: Salva no Helper
                Intent intent = new Intent(context, ActivityGraficoFaturamentoCarrosel.class);
                intent.putExtra("tipoRelatorio", "FIN_VC");
                // intent.putExtra("lista", (Serializable) listaVencidos);
                startActivity(intent);
            }
        });

        chartInadimplentes.setOnClickListener(v -> {
            if (listaVencidos != null && !listaVencidos.isEmpty()) {
                GraficoFaturamentoHelper.setDiasReferenciaFinanceiro(diasReferencia);
                GraficoFaturamentoHelper.setListaFinanceiro(listaVencidos); // NOVO: Salva no Helper
                Intent intent = new Intent(context, ActivityGraficoFaturamentoCarrosel.class);
                intent.putExtra("tipoRelatorio", "FIN_RK");
                // intent.putExtra("lista", (Serializable) listaVencidos);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btnSelecionarPeriodoFinanceiro)
    public void onSelecionarPeriodoClick() {
        exibirSeletorPeriodo();
    }

    private void exibirSeletorPeriodo() {
        String[] opcoes = {"Ciclo ± 30 dias (Vencidos/A Vencer)", "Ciclo ± 60 dias (Vencidos/A Vencer)", "Ciclo ± 90 dias (Vencidos/A Vencer)"};
        int[] valores = {30, 60, 90};

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Selecione o período de análise")
                .setItems(opcoes, (dialog, which) -> {
                    tvPeriodoReferencia.setText(opcoes[which]);
                    diasReferencia = valores[which];
                    carregarDadosDash(diasReferencia);
                })
                .show();
    }

    private void atualizarDataProcessamento() {
        String dataHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(new java.util.Date());
        tvDataPesquisa.setText("Processado em: " + dataHora);
    }

    @OnClick({R.id.btnRefreshFluxo, R.id.btnRefreshReceber, R.id.btnRefreshInad, 
              R.id.btnAnaliseFluxo, R.id.btnAnaliseReceber, R.id.btnAnaliseInad})
    public void onIconClick(View v) {
        int id = v.getId();
        if (id == R.id.btnRefreshFluxo || id == R.id.btnRefreshReceber || id == R.id.btnRefreshInad) {
            carregarDadosDash(diasReferencia);
        } else if (id == R.id.btnAnaliseFluxo) {
            exibirDialogAnaliseFinanceira(listaAVencer, "ANALISE: TÍTULOS A VENCER", "Clique p/ ver os títulos detalhados");
        } else if (id == R.id.btnAnaliseReceber || id == R.id.btnAnaliseInad) {
            exibirDialogAnaliseFinanceira(listaVencidos, "ANALISE: TÍTULOS VENCIDOS", "Clique p/ ver os títulos detalhados");
        } else {
            Toast.makeText(context, "Análise detalhada em desenvolvimento...", Toast.LENGTH_SHORT).show();
        }
    }

    private void exibirDialogAnaliseFinanceira(List<Financeiro> lista, String titulo, String subtitulo) {
        if (lista == null || lista.isEmpty()) {
            Toast.makeText(context, "Dados indisponíveis no momento.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Agrupa por Cliente (Evita duplicidade conforme solicitado)
        Map<String, ParticipacaoFaturamento> mapaAgregado = new java.util.LinkedHashMap<>();
        for (Financeiro f : lista) {
            String idCli = f.getId_favorecido() != null ? f.getId_favorecido().trim() : "0";
            double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

            if (!mapaAgregado.containsKey(idCli)) {
                ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
                pf.setId(idCli);
                pf.setNome(f.getNome_favorecido());
                pf.setValor_liquido(String.valueOf(valor));
                mapaAgregado.put(idCli, pf);
            } else {
                ParticipacaoFaturamento pf = mapaAgregado.get(idCli);
                double total = Double.parseDouble(pf.getValor_liquido()) + valor;
                pf.setValor_liquido(String.valueOf(total));
            }
        }

        List<ParticipacaoFaturamento> listaExibicao = new ArrayList<>(mapaAgregado.values());
        // Ordena por maior saldo devedor/a vencer
        java.util.Collections.sort(listaExibicao, (o1, o2) -> Double.compare(Double.parseDouble(o2.getValor_liquido()), Double.parseDouble(o1.getValor_liquido())));

        // 2. Monta o Diálogo
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View headerView = inflater.inflate(R.layout.layout_dialog_menu_cliente, findViewById(android.R.id.content), false);

        TextView tvTitulo = headerView.findViewById(R.id.tvResumoClienteNome);
        TextView tvSubtitulo = headerView.findViewById(R.id.tvResumoClienteDoc);
        headerView.findViewById(R.id.tvResumoClienteCidade).setVisibility(View.GONE);
        ImageView ivIcon = headerView.findViewById(R.id.ivMenuClienteIcon);

        if (tvTitulo != null) tvTitulo.setText(titulo);
        if (tvSubtitulo != null) {
            tvSubtitulo.setText(subtitulo);
            tvSubtitulo.setTextSize(11);
            tvSubtitulo.setTextColor(Color.parseColor("#475569"));
        }
        if (ivIcon != null) {
            ivIcon.setImageResource(R.drawable.baseline_analytics_24);
            ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            ivIcon.setBackgroundColor(Color.WHITE); // Garante fundo branco para evitar contraste
        }

        RecyclerView rv = new RecyclerView(context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setPadding(0, 16, 0, 16);

        rv.setAdapter(new com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter(listaExibicao, "C", item -> {
            // Abre o detalhamento de títulos do cliente selecionado
            exibirDialogTitulosCliente(item.getId(), lista, item.getNome());
        }));

        builder.setCustomTitle(headerView)
                .setView(rv)
                .setPositiveButton("FECHAR", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_painel_grafico_metas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            String dataHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(new java.util.Date());
            String resumo = "📊 *DASHBOARD FINANCEIRO RCK*\n" +
                            "📅 *Processado em:* " + dataHora + "\n" +
                            "─────────────────────\n" +
                            "📌 *Resumo de Carteira:*\n" +
                            "• Vencidos: " + tvKpiVencidos.getText().toString() + "\n" +
                            "• A Vencer: " + tvKpiAVencer.getText().toString() + "\n" +
                            "• Total: " + tvKpiTotalCarteira.getText().toString() + "\n" +
                            "─────────────────────\n" +
                            "_Gerado via RCK Sistemas_";
            
            compartilharView(scrollViewDashboard, "financeiro_dashboard.png", resumo);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exibirDialogTitulosCliente(String idCliente, List<Financeiro> listaOriginal, String nomeCliente) {
        // 1. Filtra os títulos apenas do cliente selecionado
        List<Financeiro> titulosFiltrados = new ArrayList<>();
        for (Financeiro f : listaOriginal) {
            if (f.getId_favorecido() != null && f.getId_favorecido().trim().equals(idCliente.trim())) {
                titulosFiltrados.add(f);
            }
        }

        // 2. Ordena por Data de Vencimento
        java.util.Collections.sort(titulosFiltrados, (o1, o2) -> {
            if (o1.getData_vencimento() == null || o2.getData_vencimento() == null) return 0;
            return o1.getData_vencimento().compareTo(o2.getData_vencimento());
        });

        // 3. Converte para ParticipacaoFaturamento para usar o Adapter existente e monta o texto formatado
        List<ParticipacaoFaturamento> listaExibicao = new ArrayList<>();
        double totalGeral = 0;
        
        StringBuilder sbWhats = new StringBuilder();
        sb_header_whats(sbWhats, nomeCliente, idCliente);

        for (Financeiro f : titulosFiltrados) {
            double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());
            totalGeral += valor;

            ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
            String dataEmissao = MascaraUtil.formataData(f.getData_emissao(), "/", 8);
            String dataVenc = MascaraUtil.formataData(f.getData_vencimento(), "/", 8);
            
            String label = "Doc: " + f.getDocumento() + " EMS: " + dataEmissao + " PARC: " + f.getParcela() + " VCTO: " + dataVenc;
            pf.setNome(label);
            pf.setValor_liquido(String.valueOf(valor));
            listaExibicao.add(pf);
            
            // Adiciona ao resumo de texto
            sbWhats.append("📄 Doc: ").append(f.getDocumento())
                    .append(" | Parc: ").append(f.getParcela())
                    .append(" | 📅 Vcto: ").append(dataVenc)
                    .append(" | 💰 *").append(MascaraUtil.mascaraRealSimboloD(valor)).append("*\n");
        }

        // Adiciona o Totalizador no final
        ParticipacaoFaturamento pfTotal = new ParticipacaoFaturamento();
        pfTotal.setNome("TOTAL DO CLIENTE");
        pfTotal.setValor_liquido(String.valueOf(totalGeral));
        listaExibicao.add(pfTotal);
        
        sb_footer_whats(sbWhats, totalGeral);

        // 4. Monta o Diálogo de Detalhes
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();

        // Layout container que será capturado para compartilhamento
        LinearLayout lytContainer = new LinearLayout(context);
        lytContainer.setOrientation(LinearLayout.VERTICAL);
        lytContainer.setBackgroundColor(Color.WHITE);
        lytContainer.setPadding(16, 16, 16, 16);

        View headerView = inflater.inflate(R.layout.layout_dialog_menu_cliente, null, false);
        ((TextView) headerView.findViewById(R.id.tvResumoClienteNome)).setText(nomeCliente);
        TextView tvSub = headerView.findViewById(R.id.tvResumoClienteDoc);
        tvSub.setText("Títulos Detalhados (ID: " + idCliente + ")");
        tvSub.setTextSize(11);
        headerView.findViewById(R.id.tvResumoClienteCidade).setVisibility(View.GONE);
        
        // Ícone de Compartilhamento no Painel de Ações
        ImageView ivShareAction = headerView.findViewById(R.id.ivMenuClienteIcon);
        if (ivShareAction != null) {
            ivShareAction.setImageResource(R.drawable.ic_compartlihar_color_primary_24_24);
            ivShareAction.setClickable(true);
            ivShareAction.setFocusable(true);
            ivShareAction.setBackgroundColor(Color.WHITE); // Fundo totalmente branco conforme solicitado
            ivShareAction.setPadding(8, 8, 8, 8);
        }
        
        lytContainer.addView(headerView);

        RecyclerView rv = new RecyclerView(context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setPadding(0, 16, 0, 16);
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(new com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter(listaExibicao, "C"));
        
        lytContainer.addView(rv);

        NestedScrollView nsv = new NestedScrollView(context);
        nsv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nsv.addView(lytContainer);

        builder.setView(nsv)
                .setPositiveButton("VOLTAR", null);

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        // Clique no ícone de compartilhamento dentro do layout
        if (ivShareAction != null) {
            ivShareAction.setOnClickListener(v -> {
                compartilharView(lytContainer, "titulos_cliente_" + idCliente + ".png", sbWhats.toString());
            });
        }
    }

    private void sb_header_whats(StringBuilder sb, String nome, String id) {
        sb.append("📋 *EXTRATO FINANCEIRO RCK*\n");
        sb.append("👤 *Cliente:* ").append(nome).append("\n");
        sb.append("🆔 *ID:* ").append(id).append("\n");
        sb.append("─────────────────────\n");
    }

    private void sb_footer_whats(StringBuilder sb, double total) {
        sb.append("─────────────────────\n");
        sb.append("✅ *TOTAL GERAL:* *").append(MascaraUtil.mascaraRealSimboloD(total)).append("*\n\n");
        sb.append("_Gerado via RCK Sistemas_");
    }

    private void compartilharView(View view, String fileName, String text) {
        try {
            Bitmap bitmap = getBitmapFromView(view);

            java.io.File cachePath = new java.io.File(context.getCacheDir(), "images");
            cachePath.mkdirs();
            java.io.File file = new java.io.File(cachePath, fileName);
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
                if (text != null && !text.isEmpty()) {
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                }
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao compartilhar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        int height = view.getHeight();
        if (view instanceof android.widget.ScrollView) {
            if (((android.widget.ScrollView) view).getChildAt(0) != null) {
                height = ((android.widget.ScrollView) view).getChildAt(0).getHeight();
            }
        } else if (view instanceof NestedScrollView) {
            if (((NestedScrollView) view).getChildAt(0) != null) {
                height = ((NestedScrollView) view).getChildAt(0).getHeight();
            }
        }

        if (height <= 0) height = 1000;
        int width = view.getWidth() > 0 ? view.getWidth() : 1000;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    private void carregarDadosDash(int dias) {
        if (UsuarioHelper.getUsuario() == null) return;
        showLoading("Sincronizando indicadores financeiros...");
        if ( UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V") )
            idUsuarioSelecionado = Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario());

        Map<String, String> cabecalho = new java.util.HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            api = Api.buildRetrofit(true);
            if (api == null) {
                hideLoading();
                Toast.makeText(context, "Configuração de API inválida.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        api.getSomaTotalFinanceiroPeriodo(idUsuarioSelecionado, "0", dias, cabecalho).enqueue(new Callback<List<List<Financeiro>>>() {
            @Override
            public void onResponse(Call<List<List<Financeiro>>> call, Response<List<List<Financeiro>>> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null && response.body().size() >= 3) {
                    listaVencidos = response.body().get(0);
                    listaAVencer = response.body().get(1);
                    listaQuitados = response.body().get(2);
                    processarIndicadores(dias);
                } else {
                    Toast.makeText(context, "Erro ao carregar dados financeiros. Verifique o formato do retorno da API.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<List<Financeiro>>> call, Throwable t) {
                hideLoading();
                Toast.makeText(context, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processarIndicadores(int dias) {
        double totalVencido = 0;
        for (Financeiro f : listaVencidos) totalVencido += Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

        double totalAVencer = 0;
        for (Financeiro f : listaAVencer) totalAVencer += Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

        double totalQuitado = 0;
        for (Financeiro f : listaQuitados) totalQuitado += Double.parseDouble(f.getValor_pago() == null || f.getValor_pago().isEmpty() ? "0" : f.getValor_pago());

        tvKpiVencidos.setText(MascaraUtil.mascaraRealSimboloD(totalVencido));
        tvKpiAVencer.setText(MascaraUtil.mascaraRealSimboloD(totalAVencer));
        tvKpiRecebidos.setText(MascaraUtil.mascaraRealSimboloD(totalQuitado));
        tvKpiTotalCarteira.setText(MascaraUtil.mascaraRealSimboloD(totalVencido + totalAVencer));

        setupChartAVencer(dias);
        setupChartVencidos(dias);
        setupChartInadimplentes(); 
    }

    private void setupChartAVencer(int dias) {
        Map<String, Double> somaFaixas = new java.util.LinkedHashMap<>();
        somaFaixas.put("Hoje", 0.0);
        somaFaixas.put("7/D", 0.0);
        somaFaixas.put("15/D", 0.0);
        somaFaixas.put("30/D", 0.0);
        if (dias >= 60) somaFaixas.put("60/D", 0.0);
        if (dias >= 90) somaFaixas.put("90/D", 0.0);

        for (Financeiro f : listaAVencer) {
            int diasAtraso = 0;
            try { diasAtraso = Math.abs(Integer.parseInt(f.getDias_atraso())); } catch (Exception e) {}
            double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

            if (diasAtraso == 0) somaFaixas.put("Hoje", somaFaixas.get("Hoje") + valor);
            else if (diasAtraso <= 7) somaFaixas.put("7/D", somaFaixas.get("7/D") + valor);
            else if (diasAtraso <= 15) somaFaixas.put("15/D", somaFaixas.get("15/D") + valor);
            else if (diasAtraso <= 30) somaFaixas.put("30/D", somaFaixas.get("30/D") + valor);
            else if (diasAtraso <= 60 && dias >= 60) somaFaixas.put("60/D", somaFaixas.get("60/D") + valor);
            else if (diasAtraso <= 90 && dias >= 90) somaFaixas.put("90/D", somaFaixas.get("90/D") + valor);
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>(somaFaixas.keySet());
        int i = 0;
        for (Double valor : somaFaixas.values()) {
            entries.add(new BarEntry(i++, valor.floatValue()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "A Vencer");
        List<GradientColor> gradientColors = new ArrayList<>();
        for (int j = 0; j < labels.size(); j++) {
            gradientColors.add(new GradientColor(ColorTemplateApp.GRADIENT_START_6[j % 6], ColorTemplateApp.GRADIENT_END_6[j % 6]));
        }
        dataSet.setGradientColors(gradientColors);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(8f);
        dataSet.setValueFormatter(new CustomValueFormatter());

        chartFluxo.setData(new BarData(dataSet));
        XAxis xAxis = chartFluxo.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        chartFluxo.setFitBars(true);

        chartFluxo.getAxisLeft().setDrawLabels(false);
        chartFluxo.getAxisLeft().setDrawGridLines(false);
        chartFluxo.getAxisLeft().setDrawAxisLine(false);
        chartFluxo.getAxisRight().setEnabled(false);
        chartFluxo.getDescription().setEnabled(false);
        chartFluxo.getLegend().setEnabled(false);
        chartFluxo.animateY(1000);
        chartFluxo.invalidate();
    }

    private void setupChartVencidos(int dias) {
        Map<String, Double> somaFaixas = new java.util.LinkedHashMap<>();
        somaFaixas.put("1-7/D", 0.0);
        somaFaixas.put("8-15/D", 0.0);
        somaFaixas.put("16-30/D", 0.0);
        if (dias >= 60) somaFaixas.put("31-60/D", 0.0);
        if (dias >= 90) somaFaixas.put("61-90/D", 0.0);

        for (Financeiro f : listaVencidos) {
            int diasAtraso = 0;
            try { diasAtraso = Integer.parseInt(f.getDias_atraso()); } catch (Exception e) {}
            double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

            if (diasAtraso > 0) {
                if (diasAtraso <= 7) somaFaixas.put("1-7/D", somaFaixas.get("1-7/D") + valor);
                else if (diasAtraso <= 15) somaFaixas.put("8-15/D", somaFaixas.get("8-15/D") + valor);
                else if (diasAtraso <= 30) somaFaixas.put("16-30/D", somaFaixas.get("16-30/D") + valor);
                else if (diasAtraso <= 60 && dias >= 60) somaFaixas.put("31-60/D", somaFaixas.get("31-60/D") + valor);
                else if (diasAtraso <= 90 && dias >= 90) somaFaixas.put("61-90/D", somaFaixas.get("61-90/D") + valor);
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>(somaFaixas.keySet());
        int i = 0;
        for (Double valor : somaFaixas.values()) {
            entries.add(new BarEntry(i++, valor.floatValue()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Vencidos");
        List<GradientColor> gradientColors = new ArrayList<>();
        for (int j = 0; j < labels.size(); j++) {
            int colorIdx = (j + 4) % 6; // Cores mais quentes
            gradientColors.add(new GradientColor(ColorTemplateApp.GRADIENT_START_6[colorIdx], ColorTemplateApp.GRADIENT_END_6[colorIdx]));
        }
        dataSet.setGradientColors(gradientColors);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(8f);
        dataSet.setValueFormatter(new CustomValueFormatter());

        chartReceber.setData(new BarData(dataSet));
        XAxis xAxisRec = chartReceber.getXAxis();
        xAxisRec.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxisRec.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisRec.setDrawGridLines(false);
        xAxisRec.setGranularity(1f);
        xAxisRec.setGranularityEnabled(true);
        chartReceber.setFitBars(true);

        chartReceber.getAxisLeft().setDrawLabels(false);
        chartReceber.getAxisLeft().setDrawGridLines(false);
        chartReceber.getAxisLeft().setDrawAxisLine(false);
        chartReceber.getAxisRight().setEnabled(false);
        chartReceber.getDescription().setEnabled(false);
        chartReceber.getLegend().setEnabled(false);
        chartReceber.animateY(1000);
        chartReceber.invalidate();
    }

    private void setupChartInadimplentes() {
        if (listaVencidos == null || listaVencidos.isEmpty()) {
            chartInadimplentes.clear();
            chartInadimplentes.setNoDataText("Nenhum devedor encontrado.");
            return;
        }

        // 1. Agrega por Cliente (Volume Financeiro Vencido)
        Map<String, ParticipacaoFaturamento> mapaAgregado = new java.util.LinkedHashMap<>();
        double totalVencidoGeral = 0;

        for (Financeiro f : listaVencidos) {
            String idCli = f.getId_favorecido() != null ? f.getId_favorecido().trim() : "0";
            double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());
            totalVencidoGeral += valor;

            if (!mapaAgregado.containsKey(idCli)) {
                ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
                pf.setId(idCli);
                pf.setNome(f.getNome_favorecido());
                pf.setValor_liquido(String.valueOf(valor));
                mapaAgregado.put(idCli, pf);
            } else {
                ParticipacaoFaturamento pf = mapaAgregado.get(idCli);
                double total = Double.parseDouble(pf.getValor_liquido()) + valor;
                pf.setValor_liquido(String.valueOf(total));
            }
        }

        List<ParticipacaoFaturamento> listaRanking = new ArrayList<>(mapaAgregado.values());
        // Ordena por maior saldo devedor
        java.util.Collections.sort(listaRanking, (o1, o2) -> Double.compare(Double.parseDouble(o2.getValor_liquido()), Double.parseDouble(o1.getValor_liquido())));

        // 2. Determina limite Top (Celular 5 / Tablet 10)
        int limiteTop = 5;
        int screenSize = getResources().getConfiguration().screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSize == android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE) {
            limiteTop = 10;
        }

        List<BarEntry> entries = new ArrayList<>();
        int entryIndex = 0;

        // 1. Calcula o montante de "OUTROS" (Clientes fora do Top 5/10)
        double valorOutros = 0;
        if (listaRanking.size() > limiteTop) {
            for (int i = limiteTop; i < listaRanking.size(); i++) {
                valorOutros += Double.parseDouble(listaRanking.get(i).getValor_liquido());
            }
        }

        // 2. Adiciona "OUTROS" na base do gráfico se houver
        if (valorOutros > 0) {
            double percentualOutros = totalVencidoGeral > 0 ? (valorOutros / totalVencidoGeral) * 100 : 0;
            String labelOutros = "OUTROS###" + MascaraUtil.duasCasaDecimal(String.valueOf(percentualOutros));
            entries.add(new BarEntry(entryIndex++, (float) valorOutros, labelOutros));
        }

        // 3. Adiciona o Top Clientes (do menor para o maior para que o maior fique no topo do HorizontalBarChart)
        int numTop = Math.min(listaRanking.size(), limiteTop);
        for (int i = numTop - 1; i >= 0; i--) {
            ParticipacaoFaturamento pf = listaRanking.get(i);
            float valor = Float.parseFloat(pf.getValor_liquido());
            double percentual = totalVencidoGeral > 0 ? (valor / totalVencidoGeral) * 100 : 0;

            String labelData = pf.getNome() + "###" + MascaraUtil.duasCasaDecimal(String.valueOf(percentual));
            entries.add(new BarEntry(entryIndex++, valor, labelData));
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(ColorTemplateApp.PIE_COLORS_6_RANK);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(9f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ""; 
            }

            @Override
            public String getBarLabel(BarEntry barEntry) {
                try {
                    String dataStr = (String) barEntry.getData();
                    if (dataStr != null && dataStr.contains("###")) {
                        String[] parts = dataStr.split("###");
                        String nomeParaExibir = parts[0];
                        if (nomeParaExibir.length() > 20) nomeParaExibir = nomeParaExibir.substring(0, 18) + "..";
                        return nomeParaExibir + " - " + MascaraUtil.mascaraRealSimbolo(barEntry.getY());
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return super.getBarLabel(barEntry);
            }
        });

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);
        
        // --- AJUSTE DE SEGURANÇA: Seta os dados PRIMEIRO, depois o renderer ---
        chartInadimplentes.setData(data);
        chartInadimplentes.setRenderer(new CustomHorizontalBarRenderer(chartInadimplentes, chartInadimplentes.getAnimator(), chartInadimplentes.getViewPortHandler()));
        chartInadimplentes.notifyDataSetChanged();

        XAxis xAxis = chartInadimplentes.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chartInadimplentes.getAxisLeft().setEnabled(false);
        chartInadimplentes.getAxisLeft().setAxisMinimum(0f);
        chartInadimplentes.getAxisLeft().setSpaceMax(40f);
        chartInadimplentes.getAxisRight().setEnabled(false);

        chartInadimplentes.getDescription().setEnabled(false);
        chartInadimplentes.getLegend().setEnabled(false);
        chartInadimplentes.setDrawGridBackground(false);
        
        // --- AJUSTE VISUAL: Texto alinhado à esquerda (dentro/fora da barra) ---
        chartInadimplentes.setDrawValueAboveBar(true); 
        chartInadimplentes.setExtraOffsets(5, 5, 20, 5); // Reduzimos o offset da direita já que o texto não depende mais dele
        
        chartInadimplentes.setFitBars(true);

        chartInadimplentes.animateY(1500);
        chartInadimplentes.invalidate();
    }

    private void showLoading(String title) {
        tvGlobalLoadingTitle.setText(title);
        lytGlobalLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        lytGlobalLoading.setVisibility(View.GONE);
    }

    public class CustomValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
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
            // Alinha o texto sempre no início do gráfico (margem esquerda + pequena folga)
            float xPos = mViewPortHandler.contentLeft() + Utils.convertDpToPixel(5f);
            mValuePaint.setTextAlign(Paint.Align.LEFT);
            mValuePaint.setColor(color);
            c.drawText(valueText, xPos, y, mValuePaint);
        }
    }

    private void configurarDrawerFiltro() {
        View drawerContent = getLayoutInflater().inflate(R.layout.layout_drawer_usuarios, navigationView, false);
        navigationView.addView(drawerContent);

        RecyclerView rvUsuarios = drawerContent.findViewById(R.id.rvUsuariosDrawer);

        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        List<Usuario> lista = new ArrayList<>();

        // Lógica de acesso: Vendedor só vê ele mesmo, outros perfis vêem todos (exceto tipo D)
        boolean isVendedor = UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V");
        
        if (!isVendedor) {
            Usuario todos = new Usuario();
            todos.setId_usuario("999");
            todos.setNome_usuario("TODOS");
            lista.add(todos);
        }

        String query = isVendedor ?
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario() :
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND (NOME_USUARIO IS NOT NULL AND NOME_USUARIO <> '') AND USUARIO_TIPO <> 'D' ORDER BY NOME_USUARIO";
        
        lista.addAll(usuarioDAO.getLista(query));

        UsuarioDrawerAdapter adapter = new UsuarioDrawerAdapter(lista, user -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(user.getNome_usuario());
            }
            this.idUsuarioSelecionado = Integer.parseInt(user.getId_usuario());
            carregarDadosDash(diasReferencia);
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        rvUsuarios.setLayoutManager(new LinearLayoutManager(this));
        rvUsuarios.setAdapter(adapter);
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
}
