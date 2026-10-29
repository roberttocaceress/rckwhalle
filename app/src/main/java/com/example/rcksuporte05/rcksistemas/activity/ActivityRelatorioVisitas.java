package com.example.rcksuporte05.rcksistemas.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.RelatorioVisitaAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebVisita;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRelatorioVisitas extends AppCompatActivity {

    private DBHelper db;
    private WebVisitaDAO visitaDAO;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView tvMesAnoReferencia;
    private View btnSelecionarMesAno;

    private TextView tvResumoTotalVisitas, tvResumoPositivadas, tvResumoNaoVenda;
    private Button btnFiltroTodas, btnFiltroPositivadas, btnFiltroNaoVenda;
    private RecyclerView rvVisitas;
    private RelatorioVisitaAdapter adapter;

    private List<WebVisita> listaVisitasAtual = new ArrayList<>();
    private String statusFiltroAtual = null; // null = Todas, 'V' = Positivadas, 'N' = Não Venda
    private int idVendedorSelecionado = 0;
    private int idUsuarioSelecionado = 0;
    private String usuarioTipoSelecionado = "V"; // 'V' = Vendedor, 'G'/'A' = Gerente/Admin
    private boolean buscouViaDrawer = false; // Flag para controlar se houve seleção explícita no Drawer
    private int anoAtual, mesAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_visitas);

        db = new DBHelper(this);
        visitaDAO = new WebVisitaDAO(db);

        Calendar cal = Calendar.getInstance();
        anoAtual = cal.get(Calendar.YEAR);
        mesAtual = cal.get(Calendar.MONTH) + 1; // 1-indexed

        // Inicializa idVendedorSelecionado e idUsuarioSelecionado se o usuário logado for vendedor
        if (UsuarioHelper.getUsuario() != null) {
            usuarioTipoSelecionado = UsuarioHelper.getUsuario().getUsuario_tipo();
            try {
                idUsuarioSelecionado = Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario());
            } catch (Exception ignored) {}
            try {
                idVendedorSelecionado = Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor());
            } catch (Exception ignored) {}
        }

        // Garante que o sistema permita desenhar o fundo das barras
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // Força a barra de status e navegação a terem a mesma cor da Toolbar
        getWindow().setStatusBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            int flags = decor.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decor.setSystemUiVisibility(flags);
        }

        drawerLayout = findViewById(R.id.drawerLayoutRelatorio);
        navigationView = findViewById(R.id.navViewRelatorio);
        toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            if (UsuarioHelper.getUsuario() != null) {
                toolbar.setTitle(UsuarioHelper.getUsuario().getNome_usuario());
            }
        }

        if (drawerLayout != null && toolbar != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvMesAnoReferencia = findViewById(R.id.tvMesAnoReferenciaRelatorio);
        btnSelecionarMesAno = findViewById(R.id.btnSelecionarMesAnoRelatorio);

        if (btnSelecionarMesAno != null) {
            btnSelecionarMesAno.setOnClickListener(v -> exibirSeletorMesAno());
        }

        tvResumoTotalVisitas = findViewById(R.id.tvResumoTotalVisitas);
        tvResumoPositivadas = findViewById(R.id.tvResumoPositivadas);
        tvResumoNaoVenda = findViewById(R.id.tvResumoNaoVenda);

        btnFiltroTodas = findViewById(R.id.btnFiltroTodasVisitas);
        btnFiltroPositivadas = findViewById(R.id.btnFiltroPositivadas);
        btnFiltroNaoVenda = findViewById(R.id.btnFiltroNaoVenda);

        rvVisitas = findViewById(R.id.rvRelatorioVisitas);
        if (rvVisitas != null) {
            rvVisitas.setLayoutManager(new LinearLayoutManager(this));
        }

        if (btnFiltroTodas != null) {
            btnFiltroTodas.setOnClickListener(v -> carregarVisitasGeral(null));
        }
        if (btnFiltroPositivadas != null) {
            btnFiltroPositivadas.setOnClickListener(v -> carregarVisitasGeral("V"));
        }
        if (btnFiltroNaoVenda != null) {
            btnFiltroNaoVenda.setOnClickListener(v -> carregarVisitasGeral("N"));
        }

        configurarDrawerFiltro();
        atualizarTextoReferencia();
        carregarVisitasLocal(null);
        //carregarVisitasGeral(null);
    }

    private void configurarDrawerFiltro() {
        if (UsuarioHelper.getUsuario() == null || navigationView == null) return;

        View drawerContent = getLayoutInflater().inflate(R.layout.layout_drawer_usuarios, navigationView, false);
        navigationView.addView(drawerContent);
        RecyclerView rvUsuarios = drawerContent.findViewById(R.id.rvUsuariosDrawer);
        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        List<Usuario> lista = new ArrayList<>();

        if (!UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
            Usuario usuarioTodos = new Usuario();
            usuarioTodos.setId_usuario("999");
            usuarioTodos.setId_quando_vendedor("999");
            usuarioTodos.setNome_usuario("TODOS OS REPRESENTANTES");
            usuarioTodos.setUsuario_tipo("G");
            lista.add(usuarioTodos);
        }

        List<Usuario> listaUsuarios = usuarioDAO.getLista(UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V") ?
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario() :
                "SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND (NOME_USUARIO <> NULL OR NOME_USUARIO <> '') AND USUARIO_TIPO <> 'D' ORDER BY NOME_USUARIO");
        lista.addAll(listaUsuarios);

        UsuarioDrawerAdapter adapterUsers = new UsuarioDrawerAdapter(lista, user -> {
            if (toolbar != null) {
                toolbar.setTitle(user.getNome_usuario());
            }

            buscouViaDrawer = true; // Marca que o usuário fez uma seleção explícita no Drawer
            usuarioTipoSelecionado = user.getUsuario_tipo();

            if ("0".equals(user.getId_usuario()) || "999".equals(user.getId_usuario())) {
                idUsuarioSelecionado = 999;
                idVendedorSelecionado = 0;
            } else {
                try {
                    idUsuarioSelecionado = Integer.parseInt(user.getId_usuario());
                } catch (Exception e) {
                    idUsuarioSelecionado = 999;
                }
                try {
                    idVendedorSelecionado = Integer.parseInt(user.getId_quando_vendedor() != null ? user.getId_quando_vendedor() : user.getId_usuario());
                } catch (Exception e) {
                    idVendedorSelecionado = 0;
                }
            }

            carregarVisitasGeral(statusFiltroAtual);

            if (drawerLayout != null) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        if (rvUsuarios != null) {
            rvUsuarios.setLayoutManager(new LinearLayoutManager(this));
            rvUsuarios.setAdapter(adapterUsers);
        }
    }

    private void atualizarTextoReferencia() {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        if (tvMesAnoReferencia != null) {
            tvMesAnoReferencia.setText(meses[mesAtual - 1] + " / " + anoAtual);
        }
    }

    private void exibirSeletorMesAno() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final NumberPicker monthPicker = new NumberPicker(this);
        final NumberPicker yearPicker = new NumberPicker(this);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(mesAtual);
        monthPicker.setDisplayedValues(new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"});
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        yearPicker.setMinValue(2020);
        yearPicker.setMaxValue(2030);
        yearPicker.setValue(anoAtual);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        LinearLayout layout = new LinearLayout(this);
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

        builder.setTitle("Selecionar Período")
                .setView(layout)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    mesAtual = monthPicker.getValue();
                    anoAtual = yearPicker.getValue();
                    atualizarTextoReferencia();
                    carregarVisitasGeral(statusFiltroAtual);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    /**
     * Método mestre para decidir se carrega visitas do banco SQLite local ou via API Retrofit
     */
    private void carregarVisitasGeral(String statusFilter) {
        statusFiltroAtual = statusFilter;

        /* === CÓDIGO ANTERIOR DA CHAMADA DIRETA DA API COMENTADO CONFORME SOLICITADO ===
        // carregarVisitasAPI(idUsuarioSelecionado, statusFilter);
        ================================================================================ */

        // REGRA SOLICITADA:
        // Ao abrir a activity, os dados filtrados devem ser SEMPRE do banco LOCAL (SQLite) e não da API.
        // A consulta na API só deve ser executada quando o usuário selecionar explicitamente um representante no Drawer (e se for diferente de 'V').
        //if (buscouViaDrawer && !"V".equalsIgnoreCase(usuarioTipoSelecionado)) {
            carregarVisitasAPI(idUsuarioSelecionado, statusFilter);
        //} else {
        //    carregarVisitasLocal(statusFilter);
        //}
    }

    /* === CÓDIGO ANTERIOR DE BUSCA EXCLUSIVAMENTE LOCAL COMENTADO CONFORME SOLICITADO ===
    private void carregarResumoKpiLocalAntigo() {
        int total = visitaDAO.getCountVisitas(null, idVendedorSelecionado, anoAtual, mesAtual);
        int positivadas = visitaDAO.getCountVisitas("V", idVendedorSelecionado, anoAtual, mesAtual);
        int naoVenda = visitaDAO.getCountVisitas("N", idVendedorSelecionado, anoAtual, mesAtual);

        if (tvResumoTotalVisitas != null) tvResumoTotalVisitas.setText(String.valueOf(total));
        if (tvResumoPositivadas != null) tvResumoPositivadas.setText(String.valueOf(positivadas));
        if (tvResumoNaoVenda != null) tvResumoNaoVenda.setText(String.valueOf(naoVenda));
    }
    ===================================================================================== */

    private void carregarVisitasLocal(String statusFilter) {
        int total = visitaDAO.getCountVisitas(null, idVendedorSelecionado, anoAtual, mesAtual);
        int positivadas = visitaDAO.getCountVisitas("V", idVendedorSelecionado, anoAtual, mesAtual);
        int naoVenda = visitaDAO.getCountVisitas("N", idVendedorSelecionado, anoAtual, mesAtual);

        if (tvResumoTotalVisitas != null) tvResumoTotalVisitas.setText(String.valueOf(total));
        if (tvResumoPositivadas != null) tvResumoPositivadas.setText(String.valueOf(positivadas));
        if (tvResumoNaoVenda != null) tvResumoNaoVenda.setText(String.valueOf(naoVenda));

        List<WebVisita> lista = visitaDAO.getListaVisitas(statusFilter, idVendedorSelecionado, anoAtual, mesAtual);
        this.listaVisitasAtual = lista;

        adapter = new RelatorioVisitaAdapter(this, lista, visita -> {
            exibirHistoricoAnaliseVisita(visita);
        });

        if (rvVisitas != null) {
            rvVisitas.setAdapter(adapter);
        }
    }

    private void carregarVisitasAPI(int idUsuario, String statusFilter) {
        Map<String, String> cabecalho = new HashMap<>();
        if (UsuarioHelper.getUsuario() != null && UsuarioHelper.getUsuario().getToken() != null) {
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        }

        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            tratarFalhaAPI();
            return;
        }

        String periodoSelecionado = String.format(Locale.US, "%04d-%02d-01", anoAtual, mesAtual);

        api.getWebVisitasPeriodo(idUsuario, periodoSelecionado, cabecalho).enqueue(new Callback<List<WebVisita>>() {
            @Override
            public void onResponse(@NonNull Call<List<WebVisita>> call, @NonNull Response<List<WebVisita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<WebVisita> listaApi = response.body();

                    int total = listaApi.size();
                    int positivadas = 0;
                    int naoVenda = 0;

                    List<WebVisita> listaFiltrada = new ArrayList<>();

                    for (WebVisita v : listaApi) {
                        if ("V".equalsIgnoreCase(v.getStatusVisita())) {
                            positivadas++;
                        } else if ("N".equalsIgnoreCase(v.getStatusVisita())) {
                            naoVenda++;
                        }

                        if (statusFilter == null || statusFilter.equalsIgnoreCase(v.getStatusVisita())) {
                            listaFiltrada.add(v);
                        }
                    }

                    listaVisitasAtual = listaFiltrada;

                    if (tvResumoTotalVisitas != null) tvResumoTotalVisitas.setText(String.valueOf(total));
                    if (tvResumoPositivadas != null) tvResumoPositivadas.setText(String.valueOf(positivadas));
                    if (tvResumoNaoVenda != null) tvResumoNaoVenda.setText(String.valueOf(naoVenda));

                    adapter = new RelatorioVisitaAdapter(ActivityRelatorioVisitas.this, listaFiltrada, visita -> {
                        exibirHistoricoAnaliseVisita(visita);
                    });

                    if (rvVisitas != null) {
                        rvVisitas.setAdapter(adapter);
                    }
                } else {
                    tratarFalhaAPI();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<WebVisita>> call, @NonNull Throwable t) {
                t.printStackTrace();
                tratarFalhaAPI();
            }
        });
    }

    private void exibirHistoricoAnaliseVisita(WebVisita visita) {
        if (visita == null) return;

        com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO analiseDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebAnaliseDAO(db);
        com.example.rcksuporte05.rcksistemas.model.WebAnalise analise = analiseDAO.getWebAnalisePorVisitaOuCliente(visita.getIdVisitaApp(), visita.getIdClienteServidor());

        com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO clienteDAO = new com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO(db);
        List<com.example.rcksuporte05.rcksistemas.model.Cliente> listaCli = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO_SERVIDOR = " + visita.getIdClienteServidor() + " OR ID_CADASTRO = " + visita.getIdClienteServidor());
        com.example.rcksuporte05.rcksistemas.model.Cliente cliente = (listaCli != null && !listaCli.isEmpty()) ? listaCli.get(0) : null;

        if (analise != null) {
            DialogHelper.showFichaClienteHistoricoDialog(this, analise, cliente);
        } else {
            Toast.makeText(this, "Nenhuma análise histórica registrada para este atendimento.", Toast.LENGTH_SHORT).show();
        }
    }

    private void tratarFalhaAPI() {
        /* === CÓDIGO ANTERIOR DO FALLBACK LOCAL COMENTADO CONFORME SOLICITADO ===
        // carregarVisitasLocal(statusFilter);
        ======================================================================== */

        listaVisitasAtual.clear();

        if (tvResumoTotalVisitas != null) tvResumoTotalVisitas.setText("0");
        if (tvResumoPositivadas != null) tvResumoPositivadas.setText("0");
        if (tvResumoNaoVenda != null) tvResumoNaoVenda.setText("0");

        adapter = new RelatorioVisitaAdapter(ActivityRelatorioVisitas.this, new ArrayList<>(), visita -> {
            // Ação ao clicar no item de visita
        });

        if (rvVisitas != null) {
            rvVisitas.setAdapter(adapter);
        }

        Toast.makeText(this, "Falha ao consultar visitas no servidor! Verifique a conexão.", Toast.LENGTH_LONG).show();
    }

    private void compartilharRelatorioVisitas() {
        if (listaVisitasAtual == null || listaVisitasAtual.isEmpty()) {
            Toast.makeText(this, "Nenhuma visita disponível para compartilhar!", Toast.LENGTH_SHORT).show();
            return;
        }

        String representante = (toolbar != null && toolbar.getTitle() != null) ? toolbar.getTitle().toString() : "Não selecionado";
        String periodo = (tvMesAnoReferencia != null) ? tvMesAnoReferencia.getText().toString() : "--/----";

        String filtroTexto = "TODAS";
        if ("V".equalsIgnoreCase(statusFiltroAtual)) {
            filtroTexto = "POSITIVADAS (VENDA)";
        } else if ("N".equalsIgnoreCase(statusFiltroAtual)) {
            filtroTexto = "NÃO VENDA";
        }

        String total = (tvResumoTotalVisitas != null) ? tvResumoTotalVisitas.getText().toString() : "0";
        String positivadas = (tvResumoPositivadas != null) ? tvResumoPositivadas.getText().toString() : "0";
        String naoVenda = (tvResumoNaoVenda != null) ? tvResumoNaoVenda.getText().toString() : "0";

        StringBuilder sb = new StringBuilder();
        sb.append("📊 RELATÓRIO DE VISITAS E ATENDIMENTOS\n");
        sb.append("👤 Representante: ").append(representante).append("\n");
        sb.append("📅 Período: ").append(periodo).append("\n");
        sb.append("🏷️ Filtro Exibido: ").append(filtroTexto).append("\n");
        sb.append("📈 Resumo: Total: ").append(total)
                .append(" | 🟢 Positivadas: ").append(positivadas)
                .append(" | 🔴 Não Venda: ").append(naoVenda).append("\n");
        sb.append("───────────────────────────────\n\n");

        for (int i = 0; i < listaVisitasAtual.size(); i++) {
            WebVisita v = listaVisitasAtual.get(i);
            boolean isVenda = "V".equalsIgnoreCase(v.getStatusVisita());

            String iconeStatus = isVenda ? "🟢 POSITIVADO" : "🔴 NÃO VENDA";
            sb.append(iconeStatus).append("\n");

            String razao = v.getRazaoSocial() != null && !v.getRazaoSocial().isEmpty()
                    ? v.getRazaoSocial() : "Cliente #" + v.getIdClienteServidor();
            sb.append("🏢 ").append(razao);
            if (v.getNomeFantasia() != null && !v.getNomeFantasia().trim().isEmpty()) {
                sb.append(" (").append(v.getNomeFantasia().trim()).append(")");
            }
            sb.append("\n");

            sb.append("🕒 Check-in: ").append(formatarDataHoraCheckin(v.getDataHoraCheckin())).append("\n");

            if (isVenda) {
                String numPed = v.getIdPedido() > 0 ? "#" + v.getIdPedido() : (v.getIdPedidoApp() != null ? v.getIdPedidoApp() : "-");
                sb.append("💰 Pedido Gerado: ").append(numPed).append("\n");
                sb.append("💵 Valor Faturado: ").append(MascaraUtil.mascaraRealSimboloD(v.getValorPedido())).append("\n");
            } else {
                String motivo = v.getMotivoNaoVenda() != null && !v.getMotivoNaoVenda().isEmpty()
                        ? v.getMotivoNaoVenda() : "Motivo não informado";
                sb.append("⚠️ Motivo(s): ").append(motivo).append("\n");
                if (v.getObservacao() != null && !v.getObservacao().trim().isEmpty()) {
                    sb.append("📝 Obs: ").append(v.getObservacao().trim()).append("\n");
                }
            }

            if (v.getLatitudeCheckin() != null && !v.getLatitudeCheckin().trim().isEmpty() &&
                    v.getLongitudeCheckin() != null && !v.getLongitudeCheckin().trim().isEmpty()) {
                String lat = v.getLatitudeCheckin().trim();
                String lng = v.getLongitudeCheckin().trim();
                sb.append("📍 GPS: ").append(lat).append(", ").append(lng);
                if (v.getPrecisaoCheckin() != null && !v.getPrecisaoCheckin().trim().isEmpty()) {
                    sb.append(" (Precisão: ").append(v.getPrecisaoCheckin().trim()).append("m)");
                }
                sb.append("\n");
                sb.append("🗺️ Mapa: https://maps.google.com/?q=").append(lat).append(",").append(lng).append("\n");
            }

            if (i < listaVisitasAtual.size() - 1) {
                sb.append("───────────────────────────────\n");
            }
        }

        sb.append("\n*Gerado via RCK Sistemas*");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Compartilhar Relatório de Visitas");
        startActivity(shareIntent);
    }

    private String formatarDataHoraCheckin(String dataHoraRaw) {
        if (dataHoraRaw == null || dataHoraRaw.trim().isEmpty()) {
            return "--/--/----";
        }
        try {
            SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date date = sdfIn.parse(dataHoraRaw);
            if (date != null) {
                SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                return sdfOut.format(date);
            }
        } catch (Exception e1) {
            try {
                if (dataHoraRaw.length() >= 10) {
                    String dataFormatada = MascaraUtil.formataData(dataHoraRaw.substring(0, 10), "/");
                    if (dataHoraRaw.length() > 10) {
                        return dataFormatada + dataHoraRaw.substring(10);
                    }
                    return dataFormatada;
                }
            } catch (Exception ignored) {}
        }
        return MascaraUtil.formataData(dataHoraRaw);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_relatorio_visitas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_compartilhar_visitas) {
            compartilharRelatorioVisitas();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnUsuarioClickListener {
        void onUsuarioClick(Usuario usuario);
    }

    public static class UsuarioDrawerAdapter extends RecyclerView.Adapter<UsuarioDrawerAdapter.ViewHolder> {
        private List<Usuario> usuarios;
        private OnUsuarioClickListener listener;

        public UsuarioDrawerAdapter(List<Usuario> usuarios, OnUsuarioClickListener listener) {
            this.usuarios = usuarios;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario_drawer, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Usuario u = usuarios.get(position);
            if (u != null) {
                holder.tv.setText(u.getNome_usuario());
                holder.itemView.setOnClickListener(v -> {
                    if (listener != null) listener.onUsuarioClick(u);
                });
            }
        }

        @Override
        public int getItemCount() {
            return usuarios != null ? usuarios.size() : 0;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            public ViewHolder(View v) {
                super(v);
                tv = v.findViewById(R.id.tvNomeUsuario);
            }
        }
    }
}
