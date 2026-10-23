package com.example.rcksuporte05.rcksistemas.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

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
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebVisita;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    private String statusFiltroAtual = null; // null = Todas, 'V' = Positivadas, 'N' = Não Venda
    private int idVendedorSelecionado = 0;
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

        // Inicializa idVendedorSelecionado se o usuário logado for vendedor
        if (UsuarioHelper.getUsuario() != null && "V".equalsIgnoreCase(UsuarioHelper.getUsuario().getUsuario_tipo())) {
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
            btnFiltroTodas.setOnClickListener(v -> carregarVisitas(null));
        }
        if (btnFiltroPositivadas != null) {
            btnFiltroPositivadas.setOnClickListener(v -> carregarVisitas("V"));
        }
        if (btnFiltroNaoVenda != null) {
            btnFiltroNaoVenda.setOnClickListener(v -> carregarVisitas("N"));
        }

        configurarDrawerFiltro();
        atualizarTextoReferencia();
        carregarResumoKpi();
        carregarVisitas(null);
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
            usuarioTodos.setId_usuario("0");
            usuarioTodos.setId_quando_vendedor("0");
            usuarioTodos.setNome_usuario("TODOS OS REPRESENTANTES");
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
            if ("0".equals(user.getId_usuario())) {
                idVendedorSelecionado = 0;
            } else {
                try {
                    idVendedorSelecionado = Integer.parseInt(user.getId_quando_vendedor() != null ? user.getId_quando_vendedor() : user.getId_usuario());
                } catch (Exception e) {
                    idVendedorSelecionado = 0;
                }
            }
            carregarResumoKpi();
            carregarVisitas(statusFiltroAtual);
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
                    carregarResumoKpi();
                    carregarVisitas(statusFiltroAtual);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void carregarResumoKpi() {
        int total = visitaDAO.getCountVisitas(null, idVendedorSelecionado, anoAtual, mesAtual);
        int positivadas = visitaDAO.getCountVisitas("V", idVendedorSelecionado, anoAtual, mesAtual);
        int naoVenda = visitaDAO.getCountVisitas("N", idVendedorSelecionado, anoAtual, mesAtual);

        if (tvResumoTotalVisitas != null) tvResumoTotalVisitas.setText(String.valueOf(total));
        if (tvResumoPositivadas != null) tvResumoPositivadas.setText(String.valueOf(positivadas));
        if (tvResumoNaoVenda != null) tvResumoNaoVenda.setText(String.valueOf(naoVenda));
    }

    private void carregarVisitas(String statusFilter) {
        statusFiltroAtual = statusFilter;
        List<WebVisita> lista = visitaDAO.getListaVisitas(statusFilter, idVendedorSelecionado, anoAtual, mesAtual);

        adapter = new RelatorioVisitaAdapter(this, lista, visita -> {
            // Ação ao clicar no item de visita
        });

        if (rvVisitas != null) {
            rvVisitas.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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
