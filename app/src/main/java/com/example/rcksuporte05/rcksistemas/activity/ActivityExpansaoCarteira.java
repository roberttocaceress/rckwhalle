package com.example.rcksuporte05.rcksistemas.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.ExpansaoCarteira;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityExpansaoCarteira extends AppCompatActivity {

    @BindView(R.id.tvKpiTotalNovos)
    TextView tvTotalNovos;

    @BindView(R.id.tvKpiMetaExpansao)
    TextView tvMetaExpansao;

    @BindView(R.id.tvKpiPercExpansao)
    TextView tvPercExpansao;

    @BindView(R.id.pbKpiExpansao)
    ProgressBar pbKpiExpansao;

    @BindView(R.id.cvKpiPercExpansao)
    View cvKpiPercExpansao;

    @BindView(R.id.cvGraficoExpansao)
    View cvGraficoExpansao;

    @BindView(R.id.chartExpansao)
    BarChart barChart;

    @BindView(R.id.rvExpansaoLista)
    RecyclerView rvLista;

    @BindView(R.id.nestedScrollViewExpansao)
    androidx.core.widget.NestedScrollView nestedScrollViewExpansao;

    @BindView(R.id.tvDataPesquisa)
    TextView tvDataPesquisa;

    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar toolbar;

    @BindView(R.id.lytGlobalLoading)
    View lytGlobalLoading;

    @BindView(R.id.tvGlobalLoadingTitle)
    TextView tvGlobalLoadingTitle;

    private DBHelper db;
    private Configuracao configuracao;
    private List<ExpansaoCarteira> dadosExpansao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansao_carteira_2026);
        ButterKnife.bind(this);
        db = new DBHelper(this);
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

        // Configura Barra de Status
        getWindow().setStatusBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));
        
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Expansão de Carteira");
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Esconde o gráfico e o último card de KPI conforme solicitado
        cvKpiPercExpansao.setVisibility(View.GONE);
        cvGraficoExpansao.setVisibility(View.GONE);

        // Ativa a busca real de dados via API
        try {
            tvDataPesquisa.setText("Processado em: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
        } catch (Exception e) {
            tvDataPesquisa.setText("");
        }
        carregarDados();
    }

    private void carregarDados() {
        if (UsuarioHelper.getUsuario() == null) return;

        int idUsuario = Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario());
        // Se for gestor/admin, usa o ID 999 para que a API retorne os dados da equipe
        if (!UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            idUsuario = 999;
        }

        // Período: Primeiro dia do mês atual (ex: 2026-09-01)
        String dataInicioPeriodo = getPrimeiroDiaMesAtual();
        String qtdeMeses = "4";

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        // Log e Toast para confirmação visual da chamada (Auxilia o desenvolvedor no teste)
        String fullUrl = Api.url + "pedido/somatotalrealizado/cliente/periodo/" + idUsuario + "/" + dataInicioPeriodo + "/" + qtdeMeses;
        android.util.Log.d("RCK_API_EXP", "Chamando: " + fullUrl);
        showLoading("Buscando dados de expansão...");

        // Chamada para a nova rota de expansão de carteira (Novos Clientes)
        Api.buildRetrofit(false).getSomaTotalRealizadoClientePeriodo(idUsuario, dataInicioPeriodo, qtdeMeses, cabecalho).enqueue(new Callback<List<com.example.rcksuporte05.rcksistemas.model.MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<com.example.rcksuporte05.rcksistemas.model.MesAnoMeta>> call, Response<List<com.example.rcksuporte05.rcksistemas.model.MesAnoMeta>> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().isEmpty()) {
                        processarDadosReais(response.body());
                    } else {
                        Toast.makeText(ActivityExpansaoCarteira.this, "Nenhum dado encontrado para o período.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityExpansaoCarteira.this, "Falha na comunicação com o servidor.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<com.example.rcksuporte05.rcksistemas.model.MesAnoMeta>> call, Throwable t) {
                hideLoading();
                Toast.makeText(ActivityExpansaoCarteira.this, "Erro de conexão.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processarDadosReais(List<com.example.rcksuporte05.rcksistemas.model.MesAnoMeta> apiData) {
        android.util.Log.d("RCK_API_EXP", "Processando " + apiData.size() + " itens.");
        Map<String, ExpansaoCarteira> mapVendedores = new HashMap<>();
        
        for (com.example.rcksuporte05.rcksistemas.model.MesAnoMeta ma : apiData) {
            String idV = ma.getId_vendedor() != null ? ma.getId_vendedor().trim() : null;
            String nomeC = ma.getNome_cadastro() != null ? ma.getNome_cadastro().trim() : null;

            android.util.Log.d("RCK_API_EXP", "Item -> VendID: " + idV + " | NomeCad: " + nomeC + " | Mes: " + ma.getNome_mes());

            // Filtra: se id_vendedor for igual a 0 ou null, não coloca na lista
            if (idV == null || idV.equals("0") || idV.isEmpty()) {
                continue;
            }

            // Filtra: se nome_cadastro for igual a 0 ou null ou empty, não coloca na lista
            if (nomeC == null || nomeC.equals("0") || nomeC.isEmpty()) {
                continue;
            }

            
            ExpansaoCarteira exp = mapVendedores.get(idV);
            if (exp == null) {
                exp = new ExpansaoCarteira();
                exp.setIdVendedor(idV);
                // Usa o nome do vendedor vindo da API
                exp.setNomeVendedor(ma.getNome_vendedor() != null ? ma.getNome_vendedor() : "Vendedor " + idV);
                exp.setMeses(new ArrayList<>());
                mapVendedores.put(idV, exp);
            }
            
            String mesNome = ma.getNome_mes() != null ? ma.getNome_mes() : "Sem Mês";

            // NOVO: Formata a data para Mês/Ano apenas quando for perfil Vendedor (V) para agrupar corretamente
            if (UsuarioHelper.getUsuario() != null && UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
                if (mesNome.contains("-") && mesNome.length() >= 10) {
                    try {
                        java.text.SimpleDateFormat sdfIn = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.text.SimpleDateFormat sdfOut = new java.text.SimpleDateFormat("MMMM/yyyy", new java.util.Locale("pt", "BR"));
                        java.util.Date d = sdfIn.parse(mesNome.substring(0, 10));
                        mesNome = sdfOut.format(d).toUpperCase();
                    } catch (Exception e) {
                        // Caso falte algum dado, mantém o valor original
                    }
                }
            }

            ExpansaoCarteira.ExpansaoMensal mesData = null;
            for (ExpansaoCarteira.ExpansaoMensal em : exp.getMeses()) {
                if (em.getMesNome().equalsIgnoreCase(mesNome)) {
                    mesData = em;
                    break;
                }
            }
            
            if (mesData == null) {
                mesData = new ExpansaoCarteira.ExpansaoMensal();
                mesData.setMesNome(mesNome);
                mesData.setClientes(new ArrayList<>());
                exp.getMeses().add(mesData);
            }
            
            ExpansaoCarteira.ClienteExpansao cli = new ExpansaoCarteira.ClienteExpansao();
            // NOVO: Usa o campo nome_cadastro para a Razão Social conforme informado pelo usuário
            cli.setRazaoSocial(ma.getNome_cadastro());
            cli.setNomeFantasia(""); // O campo fantasia pode ser preenchido se vier na API futuramente
            
            // Tenta formatar a data de inclusão de forma segura para evitar crashes
            String dataInc = "";
            try {
                if (ma.getIni_mes() != null && ma.getIni_mes().length() >= 10) {
                    dataInc = MascaraUtil.formataData( ma.getNome_mes(), "/", 10);
                } else if (ma.getNome_mes() != null && ma.getNome_mes().length() >= 10 && ma.getNome_mes().contains("-")) {
                    dataInc = MascaraUtil.formataData(ma.getNome_mes(), "/", 10);
                } else {
                    dataInc = ma.getNome_mes() != null ? ma.getNome_mes() : "";
                }
            } catch (Exception e) {
                dataInc = ma.getNome_mes() != null ? ma.getNome_mes() : "";
            }
            cli.setDataInclusao(dataInc);
            
            mesData.getClientes().add(cli);
            mesData.setTotalMes(mesData.getClientes().size());
            exp.setTotalGeral(exp.getTotalGeral() + 1);
        }
        
        dadosExpansao = new ArrayList<>(mapVendedores.values());
        android.util.Log.d("RCK_API", "Processamento concluído: " + dadosExpansao.size() + " vendedores agrupados.");
        configurarInterface();
    }

    private void carregarDadosOriginal() {
        if (UsuarioHelper.getUsuario() == null) return;
        showLoading("Buscando dados...");

        int idUsuario = Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario());
        String dataFim = getDataAtual();
        String dataInicio = getDataQuatroMesesAtras();

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Api.buildRetrofit(false).getExpansaoCarteira(idUsuario, dataInicio, dataFim, cabecalho).enqueue(new Callback<List<ExpansaoCarteira>>() {
            @Override
            public void onResponse(Call<List<ExpansaoCarteira>> call, Response<List<ExpansaoCarteira>> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    dadosExpansao = response.body();
                    configurarInterface();
                } else {
                    Toast.makeText(ActivityExpansaoCarteira.this, "Falha ao carregar dados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ExpansaoCarteira>> call, Throwable t) {
                hideLoading();
                Toast.makeText(ActivityExpansaoCarteira.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(String title) {
        if (tvGlobalLoadingTitle != null) tvGlobalLoadingTitle.setText(title);
        if (lytGlobalLoading != null) lytGlobalLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        if (lytGlobalLoading != null) lytGlobalLoading.setVisibility(View.GONE);
    }

    private void configurarInterface() {
        if (dadosExpansao == null || dadosExpansao.isEmpty()) return;

        // 1. ATUALIZA KPIs
        int totalNovos = 0;
        for (ExpansaoCarteira exp : dadosExpansao) {
            totalNovos += exp.getTotalGeral();
        }
        tvTotalNovos.setText(String.valueOf(totalNovos));

        int metaMensal = 10; // Exemplo de meta
        tvMetaExpansao.setText(String.valueOf(metaMensal));

        double perc = (double) totalNovos / (metaMensal * 4) * 100; // Média nos 4 meses
        tvPercExpansao.setText(String.format("%.1f%%", perc));
        pbKpiExpansao.setProgress((int) perc);

        // 2. CONFIGURA GRÁFICO
        configurarGrafico();

        // 3. CONFIGURA LISTA
        configurarLista();
    }

    private void configurarGrafico() {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        if (UsuarioHelper.getUsuario() != null && UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            // VISÃO VENDEDOR: Evolução por mês (pega primeiro vendedor da lista)
            ExpansaoCarteira meuDado = dadosExpansao.get(0);
            if (meuDado.getMeses() != null) {
                for (int i = 0; i < meuDado.getMeses().size(); i++) {
                    ExpansaoCarteira.ExpansaoMensal mes = meuDado.getMeses().get(i);
                    entries.add(new BarEntry(i, mes.getTotalMes()));
                    labels.add(mes.getMesNome());
                }
            }
        } else {
            // VISÃO GESTOR: Ranking por Vendedor
            for (int i = 0; i < dadosExpansao.size(); i++) {
                ExpansaoCarteira vended = dadosExpansao.get(i);
                entries.add(new BarEntry(i, vended.getTotalGeral()));
                labels.add(vended.getNomeVendedor());
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Novos Clientes");
        dataSet.setColor(Color.parseColor("#31595F"));
        dataSet.setValueTextSize(10f);

        barChart.setData(new BarData(dataSet));
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private void configurarLista() {
        List<Object> itensLista = new ArrayList<>();
        
        if (UsuarioHelper.getUsuario() != null && UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            // LISTA SIMPLES POR MÊS PARA VENDEDOR
            ExpansaoCarteira meuDado = dadosExpansao.get(0);
            if (meuDado.getMeses() != null) {
                for (ExpansaoCarteira.ExpansaoMensal mes : meuDado.getMeses()) {
                    itensLista.add(mes.getMesNome().toUpperCase() + " (" + mes.getTotalMes() + ")");
                    if (mes.getClientes() != null) itensLista.addAll(mes.getClientes());
                }
            }
        } else {
            // LISTA AGRUPADA POR VENDEDOR PARA GESTOR
            for (ExpansaoCarteira exp : dadosExpansao) {
                itensLista.add(exp); // Header do Vendedor
                if (exp.getMeses() != null) {
                    for (ExpansaoCarteira.ExpansaoMensal mes : exp.getMeses()) {
                        if (mes.getClientes() != null) itensLista.addAll(mes.getClientes());
                    }
                }
            }
        }

        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(new ExpansaoAdapter(itensLista));
    }

    private String getPrimeiroDiaMesAtual() {
        Calendar c = Calendar.getInstance();
        return String.format("%04d-%02d-01", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    private String getDataAtual() {
        Calendar c = Calendar.getInstance();
        return String.format("%04d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    }

    private String getDataQuatroMesesAtras() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -4);
        return String.format("%04d-%02d-01", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_painel_grafico_metas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            compartilharDashboard();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void compartilharDashboard() {
        try {
            android.graphics.Bitmap bitmap = getBitmapFromView(nestedScrollViewExpansao);
            java.io.File cachePath = new java.io.File(getCacheDir(), "images");
            cachePath.mkdirs();
            java.io.File file = new java.io.File(cachePath, "expansao_carteira.png");
            java.io.FileOutputStream stream = new java.io.FileOutputStream(file);
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            android.net.Uri contentUri = androidx.core.content.FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

            if (contentUri != null) {
                android.content.Intent shareIntent = new android.content.Intent();
                shareIntent.setAction(android.content.Intent.ACTION_SEND);
                shareIntent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("image/png");
                startActivity(android.content.Intent.createChooser(shareIntent, "Compartilhar via"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar imagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private android.graphics.Bitmap getBitmapFromView(View view) {
        int height = 0;
        if (view instanceof androidx.core.widget.NestedScrollView) {
            if (((androidx.core.widget.NestedScrollView) view).getChildAt(0) != null) {
                height = ((androidx.core.widget.NestedScrollView) view).getChildAt(0).getHeight();
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

    // --- ADAPTER ADAPTATIVO PARA A LISTA ---
    private class ExpansaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_CLIENTE = 1;
        private List<Object> itens;

        public ExpansaoAdapter(List<Object> itens) { this.itens = itens; }

        @Override
        public int getItemViewType(int position) {
            return (itens.get(position) instanceof ExpansaoCarteira || itens.get(position) instanceof String) ? TYPE_HEADER : TYPE_CLIENTE;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expansao_header_mes, parent, false));
            } else {
                return new ClienteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expansao_cliente, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Object item = itens.get(position);
            if (holder instanceof HeaderViewHolder) {
                HeaderViewHolder h = (HeaderViewHolder) holder;
                if (item instanceof ExpansaoCarteira) {
                    ExpansaoCarteira exp = (ExpansaoCarteira) item;
                    h.tvNome.setText(exp.getNomeVendedor().toUpperCase());
                    h.tvTotal.setText(exp.getTotalGeral() + " CLIENTES");
                    h.itemView.setBackgroundColor(Color.parseColor("#F1F5F9"));
                } else {
                    h.tvNome.setText(item.toString());
                    h.tvTotal.setText("");
                    h.itemView.setBackgroundColor(Color.parseColor("#F8FAFC"));
                }
            } else {
                ClienteViewHolder c = (ClienteViewHolder) holder;
                ExpansaoCarteira.ClienteExpansao cli = (ExpansaoCarteira.ClienteExpansao) item;
                c.tvData.setText(cli.getDataInclusao());
                c.tvRazao.setText(cli.getRazaoSocial());
                c.tvFantasia.setText(cli.getNomeFantasia());
            }
        }

        @Override
        public int getItemCount() { return itens.size(); }

        class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView tvNome, tvTotal;
            HeaderViewHolder(View v) {
                super(v);
                tvNome = v.findViewById(R.id.tvHeaderMesNome);
                tvTotal = v.findViewById(R.id.tvHeaderMesTotal);
            }
        }

        class ClienteViewHolder extends RecyclerView.ViewHolder {
            TextView tvData, tvRazao, tvFantasia;
            ClienteViewHolder(View v) {
                super(v);
                tvData = v.findViewById(R.id.tvItemExpansaoData);
                tvRazao = v.findViewById(R.id.tvItemExpansaoRazao);
                tvFantasia = v.findViewById(R.id.tvItemExpansaoFantasia);
            }
        }
    }
}
