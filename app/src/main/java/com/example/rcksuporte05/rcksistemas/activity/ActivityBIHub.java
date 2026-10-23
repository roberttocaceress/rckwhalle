package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.rcksuporte05.rcksistemas.BO.DashboardBO;
import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;

import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.DashboardData;
import com.example.rcksuporte05.rcksistemas.model.MesAno;

import com.example.rcksuporte05.rcksistemas.model.Usuario;

import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityBIHub extends AppCompatActivity implements DashboardBO.OnDashboardListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cardAnalitico)
    MaterialCardView cardAnalitico;

    @BindView(R.id.cardFinanceiro)
    MaterialCardView cardFinanceiro;

    @BindView(R.id.cardPerformance)
    MaterialCardView cardPerformance;

    @BindView(R.id.cardGestaoMetas)
    MaterialCardView cardGestaoMetas;

    @BindView(R.id.cardGestaoVisitas)
    MaterialCardView cardGestaoVisitas;

    @BindView(R.id.cardRankingEquipe)
    MaterialCardView cardRankingEquipe;

    @BindView(R.id.cardExpansao)
    MaterialCardView cardExpansao;

    @BindView(R.id.tvSaudacao)
    TextView tvSaudacao;

    @BindView(R.id.tvValorMesHub)
    TextView tvValorMesHub;

    @BindView(R.id.tvPercPerformanceHub)
    TextView tvPercPerformanceHub;

    @BindView(R.id.tvNovosClientesHub)
    TextView tvNovosClientesHub;

    private DBHelper db;
    private Configuracao configuracao;
    private double realizadoMensal = 0;
    private DashboardBO dashboardBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_hub);
        ButterKnife.bind(this);
        db = new DBHelper(this);
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

        // Garante que o sistema permita desenhar o fundo das barras
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // Força a barra de status e navegação a terem a mesma cor da Toolbar
        getWindow().setStatusBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));

        // Configura para que os ícones da barra de status sejam brancos (fundo escuro)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            int flags = decor.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // Remove flag de ícones pretos
            decor.setSystemUiVisibility(flags);
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Business Intelligence");
        }
        
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        configurarSaudacao();
        configurarAcessos();
        configurarCliques();
        // carregarResumoFaturamento(); // Comentado para usar DashboardBO
        animarEntrada();
        initDashboardBO();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDadosDashboard();
    }

    private void initDashboardBO() {
        dashboardBO = new DashboardBO(this, this);
    }

    private void carregarDadosDashboard() {
        if (UsuarioHelper.getUsuario() == null) {
            Toast.makeText(this, "Erro: Usuário não identificado.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dashboardBO == null) initDashboardBO();

        String idVendedorFirestore = "";
        try {
            List<Usuario> users = new UsuarioDAO(db).getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario());
            if (users != null && !users.isEmpty() && users.get(0).getId_quando_vendedor() != null) {
                idVendedorFirestore = users.get(0).getId_quando_vendedor().trim();
            } else {
                idVendedorFirestore = UsuarioHelper.getUsuario().getId_usuario();
            }
        } catch (Exception e) {
            idVendedorFirestore = UsuarioHelper.getUsuario().getId_usuario();
        }

        dashboardBO.carregarDados(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), idVendedorFirestore, configuracao);
    }

    @Override
    public void onDashboardLoaded(DashboardData data) {
        if (data != null) {
            if (tvValorMesHub != null) tvValorMesHub.setText(MascaraUtil.mascaraRealSimboloD(data.getRealizadoMes()));
            if (tvPercPerformanceHub != null) tvPercPerformanceHub.setText(data.getPorcentagemMes() + "% da meta");
            if (tvNovosClientesHub != null) tvNovosClientesHub.setText(String.valueOf(data.getQtdNovosClientes()));
        }
    }

    @Override
    public void onDashboardError(String mensagem) {
        // Silencioso
    }

    private void configurarSaudacao() {
        if ( UsuarioHelper.getUsuario() == null || UsuarioHelper.getUsuario().getToken() == null) {
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        }

        if (UsuarioHelper.getUsuario() != null) {
            String nome = UsuarioHelper.getUsuario().getNome_usuario();
            if (nome != null && !nome.isEmpty()) {
                if (nome.contains(" ")) {
                    nome = nome.split(" ")[0]; // Pega apenas o primeiro nome
                }
                tvSaudacao.setText("Olá, " + nome);
            }
        }
    }

    /* ANTIGO MÉTODO DE CARREGAMENTO - MANTIDO COMENTADO CONFORME SOLICITADO
    private void carregarResumoFaturamento() {
        if (UsuarioHelper.getUsuario() == null) return;
        
        // Reset inicial para evitar exibição de lixo de memória ou de outro usuário
        realizadoMensal = 0;
        tvValorMesHub.setText("R$ 0,00");
        tvPercPerformanceHub.setText("0% da meta");
        tvNovosClientesHub.setText("0");
        if ( UsuarioHelper.getUsuario() == null || UsuarioHelper.getUsuario().getToken() == null) {
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        }
        int idUsuario = Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario());
        
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        
        // Busca o realizado mensal de forma silenciosa para o Hub
        Api.buildRetrofit(false).getSomaTotalRealizadoMesPeriodo(idUsuario, getPrimeiroDiaMesAtual(), "0", cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Pega o último item da lista (mês atual/mais recente) para exibir no KPI do Hub
                    MesAnoMeta mesData = response.body().get(response.body().size() - 1);
                    realizadoMensal = cleanMoney(mesData.getVenda_liquida());
                    tvValorMesHub.setText(MascaraUtil.mascaraRealSimboloD(realizadoMensal));
                }
                // Sempre busca a meta, mesmo que o realizado seja zero
                buscarMetaParaComparativo();
            }
            @Override
            public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) {
                // Mesmo em falha na API de faturamento, tenta mostrar a meta para o vendedor saber o objetivo
                buscarMetaParaComparativo();
            }
        });
    }

    private void buscarMetaParaComparativo() {
        if (configuracao == null) return;
        
        int ano = Calendar.getInstance().get(Calendar.YEAR);
        int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String idVendedor = UsuarioHelper.getUsuario().getId_quando_vendedor();
        
        new FirestoreConexao().getMetaMesAno(configuracao.getCnpj_empresa(), String.valueOf(ano), idVendedor, new FirestoreConexao.OnMetaRetorno() {
            @Override
            public void onSucesso(MesAno mesAno) {
                if (mesAno != null) {
                    double meta = getMetaDoMes(mesAno, mes);
                    if (meta > 0) {
                        int perc = (int) ((realizadoMensal / meta) * 100);
                        tvPercPerformanceHub.setText(perc + "% da meta");
                    }
                }
            }
            @Override
            public void onFalha(Exception e) {}
        });
    }
    */

    private double getMetaDoMes(MesAno m, int mesIdx) {
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
        return cleanMoney(val);
    }

    private double cleanMoney(String valor) {
        if (valor == null || valor.isEmpty()) return 0;
        try {
            String v = valor.trim();
            // Padronização com a lógica utilizada nos dashboards de gráfico
            if (v.contains(",")) {
                return Double.parseDouble(v.replace(".", "").replace(",", "."));
            }
            return Double.parseDouble(v);
        } catch (Exception e) {
            return 0;
        }
    }

    private String getPrimeiroDiaMesAtual() {
        Calendar c = Calendar.getInstance();
        // Garante o formato YYYY-MM-DD para compatibilidade com API e SQLite
        return String.format(java.util.Locale.US, "%04d-%02d-01", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    private void animarEntrada() {
        cardAnalitico.setAlpha(0f);
        cardFinanceiro.setAlpha(0f);
        cardPerformance.setAlpha(0f);
        cardGestaoVisitas.setAlpha(0f);
        cardExpansao.setAlpha(0f);
        cardRankingEquipe.setAlpha(0f);
        cardGestaoMetas.setAlpha(0f);
        findViewById(R.id.lytResumoGlobal).setAlpha(0f);

        findViewById(R.id.lytResumoGlobal).animate().alpha(1f).translationYBy(-10f).setDuration(400).start();
        cardAnalitico.animate().alpha(1f).translationYBy(-20f).setDuration(400).setStartDelay(100).start();
        cardFinanceiro.animate().alpha(1f).translationYBy(-20f).setDuration(400).setStartDelay(150).start();
        cardPerformance.animate().alpha(1f).translationYBy(-20f).setDuration(400).setStartDelay(200).start();
        cardGestaoVisitas.animate().alpha(1f).translationYBy(-20f).setDuration(400).setStartDelay(250).start();
        cardExpansao.animate().alpha(1f).translationYBy(-20f).setDuration(400).setStartDelay(300).start();
        cardRankingEquipe.animate().alpha(1f).translationYBy(-20f).setDuration(400).setStartDelay(400).start();
        cardGestaoMetas.animate().alpha(1f).translationYBy(-20f).setDuration(400).setStartDelay(500).start();
    }

    private void configurarAcessos() {
        if (UsuarioHelper.getUsuario() != null && 
            UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            cardGestaoMetas.setVisibility(View.GONE);
            cardRankingEquipe.setVisibility(View.GONE);
        }
    }

    private void configurarCliques() {
        cardAnalitico.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityPainelGraficoFaturamento.class));
        });

        cardFinanceiro.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(), "Em desenvolvimento", Toast.LENGTH_SHORT).show();
            //return;
            startActivity(new Intent(this, ActivityPainelFinanceiro.class));
        });

        cardPerformance.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityPainelGraficoMetas.class));
        });

        cardExpansao.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityExpansaoCarteira.class));
        });
        cardGestaoVisitas.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityRelatorioVisitas.class));
        });

        cardGestaoMetas.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityMetaIndicador.class));
        });

        cardRankingEquipe.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityPainelGraficoEquipe.class));
        });
    }
}
