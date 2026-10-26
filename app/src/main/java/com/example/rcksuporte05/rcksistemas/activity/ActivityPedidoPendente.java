package com.example.rcksuporte05.rcksistemas.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.CadastroAnexoBO;
import com.example.rcksuporte05.rcksistemas.BO.PedidoBO;
import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PedidoAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.LogSistemaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VendedorBonusResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.PedidoAnexo;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.model.LogSistema;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.model.WebVisita;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoICMSST;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoUtil2026;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.MesAno;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.MetaSemanal;
import com.example.rcksuporte05.rcksistemas.util.FirestoreConexao;
import java.util.Calendar;
import java.lang.reflect.Field;
import java.util.Locale;

public class ActivityPedidoPendente extends AppCompatActivity implements  ListaPedidoAdapter.PedidoAdapterListener, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    ActionModeCallback actionModeCallback;
    @BindView(R.id.listaPedidosPendentes)
    RecyclerView recyclerView;

    //@BindView(R.id.swipe_container)
    //SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.buscaPedido)
    SearchView buscapedido;

    @BindView((R.id.helpPedido))
    ImageView helpPedido;

    private List<WebPedido> listaPedido = new ArrayList();
    private EditText edtTotalPedidos;
    private DBHelper db = new DBHelper(this);
    private WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
    private WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);

    private WebVisitaDAO webVisitaDAO = new WebVisitaDAO(db);
    private PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private Configuracao configuracao = new Configuracao();
    private Usuario usuario;
    private ProgressDialog progress;
    // NOVOS CAMPOS PARA DIÁLOGO CUSTOMIZADO
    private androidx.appcompat.app.AlertDialog progressDialogCustom;
    private TextView tvProgressMessage;

    private PedidoBO pedidoBO = new PedidoBO();
    private ListaPedidoAdapter listaPedidoAdapter;
    private MenuItem geraPDF;
    private MenuItem emailPDF;
    private MenuItem duplicaPedido;
    private MenuItem enviaPedido;

    private MenuItem analisePedido;

    private MenuItem selecionarTodos;
    private ActionMode actionMode;
    private Context context;

    private int offSet     = 0;
    private int totalReg   = 0;
    private int limitReg   = 100;
    private int indSel     = -1;
    private int indAnalise = -1;
    private int indAcao    =  0; //1 cria e mostra pdf | 2 cria e envia por email | 3 Compartilha

    private String SQL;
    PackageInfo pInfo = null;
    FirebaseFirestore fdb;

    private ItemTouchHelper itemTouchhelper;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private int indItem;

    private boolean alerta = true;
    private boolean cliqueAtivo = true;

    Utilitaria util = new Utilitaria();
    @BindView(R.id.btnNovoPedido)
    Button btnNovoPedido;

    @BindView(R.id.lytInserirPedido)
    LinearLayout lytInserirPedido;
    /*
    @OnClick(R.id.btnNovoPedido)
    public void btnNovoPedido() {
        try {
            listaPedidoAdapter.clearSelections();
            actionMode = null;
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        indItem = 0;
        startActivity(new Intent(this, ActivityPedidoMain.class));
    }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_drawer);

        // 1. Carregamento Crítico de Sessão (Antes de qualquer restauração de UI)
        db = new DBHelper(this);
        getConfiguracao();
        try {
            if (configuracao.getVisualiza_legenda_pedido() == null || configuracao.getVisualiza_legenda_pedido().equalsIgnoreCase("S")) {
                startActivity(new Intent(ActivityPedidoPendente.this, ActivityDialogPedido.class));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (configuracao.getGira_tela_ped_pendente().isEmpty() || configuracao.getGira_tela_ped_pendente().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        
        try {
            if (UsuarioHelper.getUsuario() == null || UsuarioHelper.getUsuario().getId_usuario() == null || UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
            }
        } catch (Exception e) {
        }
        
        usuario = UsuarioHelper.getUsuario();
        // Se o usuário não for recuperado, redireciona para login ou impede crash
        if (usuario == null) {
            Toast.makeText(this, "Sessão expirada. Por favor, faça login novamente.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        context = this;
        ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        fdb = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbarPedidoPendente);
        toolbar.setTitle("Pedidos Pendentes");

        buscapedido.setOnQueryTextListener(new ActivityPedidoPendente.QueryTextListener());
        buscapedido.setIconifiedByDefault(true);
        indItem = 0;

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ActivityPedidoPendente.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        addMenuDrawerItem();

        edtTotalPedidos = findViewById(R.id.edtTotalPedidos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ActivityPedidoPendente.ScrollListener());

        actionModeCallback = new ActionModeCallback();

        helpPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityPedidoPendente.this, ActivityDialogPedido.class));
            }
        });
        btnNovoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escolherEmpresaPedido();
            }
        });
        //System.gc();
    }

    private void escolherEmpresaPedido() {
        List<EmpresaParametro> listaEmpresas = new EmpresaParametroDAO(db).getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        if (listaEmpresas != null && listaEmpresas.size() > 1) {
            String[] nomesEmpresas = new String[listaEmpresas.size()];
            for (int i = 0; i < listaEmpresas.size(); i++) {
                nomesEmpresas[i] = listaEmpresas.get(i).getRazao_social() != null ? listaEmpresas.get(i).getRazao_social() : listaEmpresas.get(i).getRazao_social();
            }

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Selecione a Empresa")
                    .setItems(nomesEmpresas, (dialog, which) -> {
                        startActivityPedido(String.valueOf(listaEmpresas.get(which).getId_empresa()));
                    })
                    .setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            startActivityPedido("");
        }
    }

    private void startActivityPedido(String idEmpresa) {
        new PedidoHelper().limparDados();

        // 2. Garante que o cliente selecionado esteja ativo no Helper
        ClienteHelper.setCliente(null);
        try {
            if (listaPedidoAdapter != null) {
                listaPedidoAdapter.clearSelections();
            }
            actionMode = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        indItem = 0;
        startActivity(new Intent(context, ActivityPedidoMain.class).putExtra("id_empresa", idEmpresa));
    }

    @SuppressLint("HardwareIds")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    listaPedidoAdapter.clearSelections();
                    actionMode = null;
                } catch (NullPointerException e) {
                } catch ( Exception e) {
                }
                finish();
                break;
            case R.id.menu_pedido_pendente:
                List<WebPedido> pedidosPendentes = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
                for ( int i = 0; i < pedidosPendentes.size(); i++) {
                    pedidosPendentes.get(i).setTipo_envio("3");
                    try {
                        pedidosPendentes.get(i).setId_android(Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                    } catch (Exception e) {
                        pedidosPendentes.get(i).setId_android("EXCEPTION");
                    }
                }
                if (pedidosPendentes.size() > 0)
                    if (pedidosPendentes.size() == 1) {
                        showProgressDialogCustom("Analisando integridade do pedido...");
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            if (analisaPedido(pedidosPendentes)) {
                                dismissProgressDialogCustom();
                                showOrderSummaryDialog(pedidosPendentes.get(0), pedidosPendentes);
                            } else {
                                dismissProgressDialogCustom();
                                showActionDialog(pedidosPendentes.get(0), "Foi encontrada divergencia no pedido referente aos itens gravados! Deseja cancelar o envio para analisar o pedido n: " + pedidosPendentes.get(0).getId_web_pedido() + " de " + pedidosPendentes.get(0).getNome_extenso() + "?");
                            }
                        }, 200);
                    } else
                        showActionDialog(null, item.getItemId(), pedidosPendentes, "Deseja enviar todos os pedidos pendentes não sincronizados para serem faturados?");
                else {
                    cliqueAtivo = true;
                    showFeedbackDialog("Não há pedidos pendentes para enviar!", true, false, null);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void addMenuDrawerItem() {
        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.addSubMenu(" Grupos ");
        subMenu.add(0, 0, 0, "PEDIDOS PENDENTES").setIcon(R.drawable.ic_lista_todos_drawner);
        subMenu.add(0, 1, 1, "PEDIDOS EXCLUIDOS/LIXEIRA").setIcon(R.drawable.ic_lista_todos_drawner);
        subMenu.add(0, 2, 2, "TODOS").setIcon(R.drawable.ic_lista_todos_drawner);
        subMenu.add(0, 3, 3, "NOVO CADASTRO DE PEDIDO").setIcon(R.drawable.ic_add_novo_registro);
        navigationView.invalidate();
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0: {
                toolbar.setTitle("Pedidos Pendentes");
                indItem = 0;
                getPedidos();
                drawerLayout.closeDrawers();
                break;
            }
            case 1: {
                toolbar.setTitle("Pedidos excluidos/lixeira");
                indItem = 1;
                getPedidos();
                drawerLayout.closeDrawers();
                break;
            }
            case 2: {
                toolbar.setTitle("Todos pedidos");
                indItem = 2;
                drawerLayout.closeDrawers();
                getPedidos();
                break;
            }
            case 3: {
                drawerLayout.closeDrawers();
                btnNovoPedido.callOnClick();
                break;
            }
            default:
                drawerLayout.closeDrawers();
                break;

        }
        return true;
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            try {
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("N")) {
                    edtTotalPedidos.setText("(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
                    return;
                }
            } catch (NullPointerException e) {
                return;
            }
            if (!recyclerView.canScrollVertically(1)) {
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S")) {
                    offSet += limitReg;
                    listaPedido.addAll( webPedidoDAO.getLista(SQL + " LIMIT " + limitReg + " OFFSET " + offSet));
                    listaPedidoAdapter.notifyDataSetChanged();
                }
            }
            edtTotalPedidos.setText( "(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
        }
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
               btnNovoPedido.setVisibility(View.INVISIBLE);
                lytInserirPedido.setVisibility(View.INVISIBLE);
            }else if (dy < 0) {
                btnNovoPedido.setVisibility(View.VISIBLE);
                lytInserirPedido.setVisibility(View.VISIBLE);
            }
        }

    }

    private int getFirstItem(){
        return ((LinearLayoutManager)recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private int getLastItem() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastVisibleItemPosition();
    }

    private void showProgressDialogCustom(String mensagem) {
        try {
            if (progressDialogCustom != null && progressDialogCustom.isShowing()) {
                updateProgressMessage(mensagem);
                return;
            }

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_dialog_sincronia_progress, null);
            tvProgressMessage = view.findViewById(R.id.tvProgressMessage);
            tvProgressMessage.setText(mensagem);

            progressDialogCustom = new com.google.android.material.dialog.MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
                    .setView(view)
                    .setCancelable(false)
                    .create();

            progressDialogCustom.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProgressMessage(String mensagem) {
        runOnUiThread(() -> {
            if (tvProgressMessage != null && progressDialogCustom != null && progressDialogCustom.isShowing()) {
                tvProgressMessage.setText(mensagem);
            }
        });
    }

    private void dismissProgressDialogCustom() {
        runOnUiThread(() -> {
            try {
                if (progressDialogCustom != null && progressDialogCustom.isShowing()) {
                    progressDialogCustom.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void sincronizaPedidos(final List<WebPedido> listaParaEnvio) {
        showProgressDialogCustom("Analisando o pedido! Aguarde...");
        
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if ( !usuarioIsLogado()) {
                cliqueAtivo = true;
                dismissProgressDialogCustom();
                return;
            }
            final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(ActivityPedidoPendente.this)
                    .setSmallIcon(R.mipmap.ic_enviar_pedidos)
                    .setContentTitle("Enviando pedidos")
                    .setContentText("Estabelecendo Conexão")
                    .setProgress(0, 0, true)
                    .setPriority(2);
            final NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificacao.build());

            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String versaoApp = pInfo.versionName.substring(0, 5);
            prepararItensPedidos(listaParaEnvio);
            prepararAnexosPedidos(listaParaEnvio);

            for ( WebPedido webPedido : listaParaEnvio) {
                if (webPedido.getWebPedidoItens().isEmpty()) {
                    dismissProgressDialogCustom();
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), listaParaEnvio.size() == 1 ? "O pedido não contem produtos!" : "Um ou mais pedidos não contem produtos!", R.drawable.ic_alerta_mensagem);
                    return;
                }
                for ( WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                    if ( !webPedidoItens.getProdutoAtivo() ) {
                        dismissProgressDialogCustom();
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "O pedido " + webPedido.getId_web_pedido() +
                                " contem produto(s) inativo/e ou que não podem ser vendidos pelo aplicativo!");
                        cliqueAtivo = true;
                        return;
                    }
                    if ( webPedidoItens.getValor_unitario() <= 0.00f ) {
                        dismissProgressDialogCustom();
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "O produto " + webPedidoItens.getNome_produto() +
                                " está com valor unitário igual a zero, o que não é permitido para envio!");
                        cliqueAtivo = true;
                        return;
                    }
                }
            }
            updateProgressMessage("Analisando o pedido! Aguarde...");
            getConfiguracao();
            if (!configuracao.getId().isEmpty()) {
                Call<Sincronia> call = getRotasAPI(1, listaParaEnvio, configuracao, versaoApp);
                if (call == null) {
                    cliqueAtivo = true;
                    dismissProgressDialogCustom();
                    return;
                }

                call.enqueue(new Callback<Sincronia>() {

                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                        cliqueAtivo = true;
                        dismissProgressDialogCustom();
                        if (response.code() == 200) {
                            final Sincronia sincronia1 = response.body();
                            updateEstoque(sincronia1.getListaProduto(), false, null, null);
                            updatePedidosEnviados(sincronia1.getListaWebPedidosPendentes());

                            PendingIntent pendingIntent;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                                pendingIntent = PendingIntent.getActivity(ActivityPedidoPendente.this, 0, new Intent(ActivityPedidoPendente.this, ActivityPedidoEnviado.class), PendingIntent.FLAG_MUTABLE);
                            } else {
                                pendingIntent = PendingIntent.getActivity(ActivityPedidoPendente.this, 0, new Intent(ActivityPedidoPendente.this, ActivityPedidoEnviado.class), PendingIntent.FLAG_IMMUTABLE);
                            }
                        } else {
                            boolean produtoAtivo = true;
                            String produtosInativo = "";
                            for (int x = 0; x < listaParaEnvio.size(); x++) {
                                if (listaParaEnvio.get(x).getWebPedidoItens().size() > 0) {
                                    for (int i = 0; i < listaParaEnvio.get(x).getWebPedidoItens().size(); i++) {
                                        if (!listaParaEnvio.get(x).getWebPedidoItens().get(i).getProdutoAtivo()) {
                                            produtoAtivo = false;
                                            if (!produtosInativo.contains(listaParaEnvio.get(x).getWebPedidoItens().get(i).getNome_produto()))
                                                produtosInativo += listaParaEnvio.get(x).getWebPedidoItens().get(i).getNome_produto() + "\n";
                                        }
                                    }
                                }
                            }
                            if (!produtoAtivo)
                                showFeedbackDialog("O(s) produto(s) " + produtosInativo + " foi/foram inativado(s)/excluido(s) da base de dados\nFavor verificar/corrigir o(s) pedido(s) antes de exportar!", true, false, null);
                            else
                                showFeedbackDialog("Não foi possivel enviar o pedido! Favor verificar o pedido e seus produtos", true, false, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Sincronia> call, Throwable t) {
                        cliqueAtivo = true;
                        dismissProgressDialogCustom();
                        try {
                            if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty()) {
                                sincronizaPedidos2(listaParaEnvio);
                            } else {
                                setNotificacaoFalha(notificacao, notificationManager, "Problema de conexão!", "Não foi possivel enviar os pedidos!!",
                                        "Não foi possivel enviar o(s) pedido(s)! Verifique sua conexão a rede/internet e tente novamente!\n"+ t.getMessage());
                            }
                        } catch (NullPointerException e) {
                            setNotificacaoFalha(notificacao, notificationManager, "Problema de conexão!", "Não foi possivel enviar os pedidos!!",
                                    "Não foi possivel enviar o(s) pedido(s)! Verifique sua conexão a rede/internet e tente novamente!\n"+ t.getMessage() + "\n" +e.getMessage());
                        }
                    }
                });
            }else {
                cliqueAtivo = true;
                dismissProgressDialogCustom();
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Parametros para conexão não cadastrados!", R.drawable.ic_alerta_mensagem);
            }
        }, 200);
    }

    public void sincronizaEstoque(final List<WebPedido> listaParaEnvio) {
        showProgressDialogCustom("Analisando o pedido! Aguarde...");
        /*
        progress = new ProgressDialog(ActivityPedidoPendente.this);
        progress.setMessage("Analisando o pedido! Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();
         */
        if ( !usuarioIsLogado()) {
            cliqueAtivo = true;
            dismissProgressDialogCustom();
            return;
        }
        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(ActivityPedidoPendente.this)
                .setSmallIcon(R.mipmap.ic_enviar_pedidos)
                .setContentTitle("Analisando pedido")
                .setContentText("Estabelecendo Conexão")
                .setProgress(0, 0, true)
                .setPriority(2);
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificacao.build());

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String versaoApp = pInfo.versionName.substring(0, 5);
        prepararItensPedidos(listaParaEnvio);
        List<WebPedido> listaTemp = new ArrayList<>();
        getConfiguracao();
        cliqueAtivo = true;
        if (!configuracao.getId().isEmpty()) {
            Call<Sincronia> call = getRotasAPI(1, listaTemp, configuracao, versaoApp);
            if (call == null)
                return;

            call.enqueue(new Callback<Sincronia>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    if (response.code() == 200) {
                        final Sincronia sincronia1 = response.body();
                        updateEstoque(sincronia1.getListaProduto(), true, listaParaEnvio, sincronia1.getListaWebPedidosPendentes());
                        dismissProgressDialogCustom();

                    } else {
                        showFeedbackDialog("Não foi possivel efetuar a analise do pedido em relação ao estoque!", true, false, R.drawable.ic_alerta_mensagem );
                        dismissProgressDialogCustom();
                    }
                }

                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    dismissProgressDialogCustom();
                    showFeedbackDialog("Não foi possivel efetuar a analise do pedido em relação ao estoque!\n" + t.getMessage(), true, false, R.drawable.ic_alerta_mensagem);
                }
            });
        }else {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Parametros para conexão não cadastrados!", R.drawable.ic_alerta_mensagem);
            dismissProgressDialogCustom();
        }

    }

    private void updatePedidosEnviados(List<WebPedido> webPedidosEnviados) {
        int totalSincronizado = webPedidosEnviados.size();
        ClienteDAO clienteDAO = new ClienteDAO(db);
        for (WebPedido pedido : webPedidosEnviados) {
            if (pedido.getPedido_enviado().equalsIgnoreCase("S")) {

                final CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                final PedidoAnexoDAO pedidoAnexoDAO = new PedidoAnexoDAO(db);
                pedido.getCadastro().setAlterado("N");
                clienteDAO.update(pedido.getCadastro());
                WebVisita webVisita = pedido.getWebVisita();
                if ( webVisita != null)
                   webVisitaDAO.atualiarVisitaAPI(webVisita.getIdVisitaApp(), webVisita.getIdVisitaServidor(), webVisita.getIdPedidoServidor());
                try {
                    db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(pedido.getCadastro().getId_cadastro())});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (pedido.getCadastro().getListaCadastroAnexo().size() > 0)
                    for (CadastroAnexo cadastroAnexo : pedido.getCadastro().getListaCadastroAnexo()) {
                        if (cadastroAnexo.getExcluido().equals("N"))
                            cadastroAnexoDAO.addUpdate(cadastroAnexo);
                        else if (cadastroAnexo.getExcluido().equals("S"))
                            try {
                                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_ANEXO = ?", new String[]{String.valueOf(cadastroAnexo.getIdAnexo())});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                else
                    try {
                        db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(pedido.getCadastro().getId_cadastro())});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (webPedidoDAO.update(pedido) > 0) {
                    for (WebPedidoItens pedidoIten : pedido.getWebPedidoItens()) {
                        webPedidoItensDAO.update(pedidoIten);
                    }
                    for (PedidoAnexo pedidoAnexo : pedido.getWebPedidoAnexos()) {
                        pedidoAnexoDAO.update(pedidoAnexo);
                    }

                    // ATUALIZA E SINCRONIZA LOGS DO SISTEMA
                    try {
                        LogSistemaDAO logDAO = new LogSistemaDAO(db);
                        logDAO.updateServerId(pedido.getId_web_pedido(), pedido.getId_web_pedido_servidor());
                        syncLogsToFirestore(pedido);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    delPedidoFirebase(configuracao.getCnpj_empresa(), pedido);
                    
                    // Remove da lista e atualiza a tela
                    for (int i = 0; i < listaPedido.size(); i++) {
                        if (listaPedido.get(i).getId_web_pedido().equals(pedido.getId_web_pedido())) {
                            listaPedido.remove(i);
                            listaPedidoAdapter.notifyItemRemoved(i);
                            break;
                        }
                    }
                } else
                    totalSincronizado--;
            } else {
                totalSincronizado--;
            }
        }
        excluirPedidoSincronizado();
        if (totalSincronizado == 0 && webPedidosEnviados.size() == 1) {
            try {
                for (WebPedido webPedido : webPedidosEnviados) {
                    if (!webPedido.getListaErro().isEmpty()){
                        showFeedbackDialog("Falha na sincronização do pedido!\nAnalise do erro!\n"  + webPedido.getListaErro().get(0).getMensagem(), false, false, null);
                        //showMsgAlertErroPedido("Falha na sincronização do pedido!\nAnalise do erro!\n"  + webPedido.getListaErro().get(0).getMensagem());
                        return;

                    }
                }
            } catch (NullPointerException e) {

            }
            showFeedbackDialog("Falha na sincronização do pedido!\nFavor enviar novamente e caso o erro persista, favor entrar em contato com o suporte técnico!!", false, false, null);
            //showMsgAlertErroPedido("Falha na sincronização do pedido!\nFavor enviar novamente e caso o erro persista, favor entrar em contato com o suporte técnico!!");
            return;
        }
        if (totalSincronizado == webPedidosEnviados.size()) {
            if (webPedidosEnviados.size() == 1) {
                final WebPedido pedido = webPedidosEnviados.get(0);
                if ( configuracao.getVisualiza_grafico_on_line_app().equalsIgnoreCase("S")) {
                    DialogHelper.showActionDialog(
                            ActivityPedidoPendente.this,
                            "O pedido do cliente " + pedido.getCadastro().getNome_cadastro() + " foi enviado.\n\nDeseja analisar seu progresso de metas e posição no ranking?",
                            "SIM, ANALISAR", null,
                            "AGORA NÃO", null,
                            null, null,
                            new DialogHelper.OnActionSelectedListener() {
                                @Override
                                public void onPositive() {
                                    showPostSyncFeedback(pedido);
                                }

                                @Override
                                public void onNegative() {
                                    showFeedbackDialog("O pedido do cliente " + pedido.getCadastro().getNome_cadastro()
                                            + " no valor de " + MascaraUtil.mascaraReal(pedido.getValor_total())
                                            + " foi sincronizado com sucesso!!", true, false, null);
                                }

                                @Override
                                public void onNeutral() {

                                }
                            }
                    );
                } else {
                    showFeedbackDialog("O pedido do cliente " + pedido.getCadastro().getNome_cadastro()
                            + " no valor de " + MascaraUtil.mascaraReal(pedido.getValor_total())
                            + " foi sincronizado com sucesso!!", true, false, null);
                }

            } else {
                String nomeCadastro = "";
                for ( int i = 0; i < webPedidosEnviados.size(); i++) {
                    nomeCadastro +=  webPedidosEnviados.get(i).getCadastro().getNome_cadastro() + "\n";
                }
                 if ( totalSincronizado >= 4) {
                     showFeedbackDialog( totalSincronizado + " pedidos foram sincronizados com sucesso!!", true, false, null);
                 } else {
                     showFeedbackDialog("Os " + totalSincronizado + " pedidos de\n" + nomeCadastro + "foram sincronizados com sucesso!!", true, false, null);
                 }
           }
        }else {
            showFeedbackDialog(String.valueOf(totalSincronizado) + " pedido(s) sincronizado(s) com sucesso, de um total de " + webPedidosEnviados.size() + " enviado(s)!!", true, false, null);
        }

    }

    private String qtdeItensPedido(List<WebPedidoItens> lista){
        int qtdeItens = lista.size();
        for (int i = 0; i < lista.size(); i++) {
            if ( lista.get(i).getId_web_item_servidor() == null) {
                qtdeItens--;
            }
        }
        if ( lista.size() == 1 ) {
            if ( lista.size() ==  qtdeItens ) {
                return "1 produto";
            } else {
                showFeedbackDialog("Houve falha na gravação dos itens do pedido, favor verificar junto a empresa se o pedido chegou em sua total integridade a empresa!", false, false, null);
                //showMsgAlertErroPedido("Houve falha na gravação dos itens do pedido, favor verificar junto a empresa se o pedido chegou em sua total integridade a empresa!");
                return "1 produto";
            }
        } else if ( lista.size() > 1) {
            if ( lista.size() == qtdeItens){
                return lista.size() + " produtos";
            } else {
                showFeedbackDialog("Houve falha na gravação dos itens do pedido, favor verificar junto a empresa se o pedido chegou em sua total integridade a empresa!", false, false, null);
                //showMsgAlertErroPedido("Houve falha na gravação dos itens do pedido, favor verificar junto a empresa se o pedido chegou em sua total integridade a empresa!", false, false, null);
                return lista.size() + " produtos";
            }
        } else {
            return "pedido sem produto";
        }

    }

    private void updateEstoque(List<Produto> listaProduto, boolean analisePedidoEstoque, List<WebPedido> listaParaEnvio, List<WebPedido> listaAnalise) {
        try {
            if (!listaProduto.isEmpty()) {
                ProdutoDAO produtoDAO = new ProdutoDAO(db);
                produtoDAO.updateSaldoEstoque(listaProduto, db);
                //for (Produto produto :listaProduto)
                //    produtoDAO.updateSaldoEstoque(produto);
                    //if (produtoDAO.updateSaldoEstoque(produto) > 0)
                    //    System.gc();


            }
            System.gc();
            if ( analisePedidoEstoque) {
               try {
                   WebPedido webPedido = listaParaEnvio.get(0);
                   webPedido.setAnalise_estoq_efetuada("S");
                   webPedido.setAnalise_estoque_data_hora(listaAnalise.get(0).getAnalise_estoque_data_hora());
                   new WebPedidoDAO(db).updateaAnaliseEstoque(webPedido);
               } catch ( NullPointerException e) {
                   e.printStackTrace();
               }
               if ( analisePedidoEstoque(listaParaEnvio)) {
                   showFeedbackDialog("Analise efetuada com sucesso!!\nSaldo em estoque no momento é suficiente para atender ao pedido selecionado!\n" +
                           "Data/Hora\n" + MascaraUtil.formataData(listaAnalise.get(0).getAnalise_estoque_data_hora().substring(0,10) , "/") +  "  -  " + listaAnalise.get(0).getAnalise_estoque_data_hora().substring(11) + " ss", true, false, null);
               } else {
                   showFeedbackDialog("Verificação pode ser necessária!\nNo momento existem produtos com saldo/estoque insuficiente para atender ao pedido selecionado!\n" +
                           "Data/Hora\n" + MascaraUtil.formataData(listaAnalise.get(0).getAnalise_estoque_data_hora().substring(0,10) , "/") +  "  -  " + listaAnalise.get(0).getAnalise_estoque_data_hora().substring(11) + " ss", true, false, R.drawable.ic_alerta_mensagem_color_primary);
               }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean analisePedidoEstoque(List<WebPedido> listaParaEnvio) {
        boolean res = true;
        WebPedidoItensDAO webPedidoItensDAO1 = new WebPedidoItensDAO(db);
        for ( WebPedido webPedido : listaParaEnvio) {
            for ( WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                try {
                    if ( Float.parseFloat(webPedidoItens.getQuantidade()) > db.saldo("SELECT SALDO_ESTOQUE FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'")) {
                        webPedidoItens.setAnalise_estoque_resultado("N");
                        res = false;
                    }    else {
                        webPedidoItens.setAnalise_estoque_resultado("A");
                    }
                    webPedidoItensDAO1.updateAnaliseEstoque(webPedidoItens);
                } catch (NullPointerException|NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public void sincronizaPedidos2(final List<WebPedido> listaParaEnvio) {
        if ( !usuarioIsLogado())
            return;
        updateProgressMessage("Estabelecendo Conexão(2)");
        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(ActivityPedidoPendente.this)
                .setSmallIcon(R.mipmap.ic_enviar_pedidos)
                .setContentTitle("Enviando pedidos")
                .setContentText("Estabelecendo Conexão(2)")
                .setProgress(0, 0, true)
                .setPriority(2);
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificacao.build());
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        prepararItensPedidos(listaParaEnvio);

        String versaoApp = pInfo.versionName.substring(0, 5);
        getConfiguracao();
        if (!configuracao.getId().isEmpty()) {
            Call<Sincronia> call = getRotasAPI(2, listaParaEnvio, configuracao, versaoApp);
            if (call == null)
                return;
            call.enqueue(new Callback<Sincronia>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    if (response.code() == 200) {
                        final Sincronia sincronia2 = response.body();
                        //final List<WebPedido> webPedidosEnviados = sincronia2.getListaWebPedidosPendentes();
                        updateEstoque(sincronia2.getListaProduto(), false, null, null);
                        updatePedidosEnviados(sincronia2.getListaWebPedidosPendentes());
                        PendingIntent pendingIntent;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                            pendingIntent = PendingIntent.getActivity(ActivityPedidoPendente.this, 0, new Intent(ActivityPedidoPendente.this, ActivityPedidoEnviado.class), PendingIntent.FLAG_MUTABLE);
                        } else {
                            pendingIntent = PendingIntent.getActivity(ActivityPedidoPendente.this, 0, new Intent(ActivityPedidoPendente.this, ActivityPedidoEnviado.class), PendingIntent.FLAG_IMMUTABLE);
                        }
                        setNotificacaoSucesso(notificacao, notificationManager, pendingIntent, "Pedidos enviados com sucesso!",
                                sincronia2.getListaWebPedidosPendentes().size() + " pedidos enviados.");

                    } else {
                        boolean produtoAtivo = true;
                        String produtosInativo = "";
                        for (int x = 0; x < listaParaEnvio.size(); x++) {
                            if (listaParaEnvio.get(x).getWebPedidoItens().size() > 0) {
                                for (int i = 0; i < listaParaEnvio.get(x).getWebPedidoItens().size(); i++) {
                                    if (!listaParaEnvio.get(x).getWebPedidoItens().get(i).getProdutoAtivo()) {
                                        produtoAtivo = false;
                                        if (!produtosInativo.contains(listaParaEnvio.get(x).getWebPedidoItens().get(i).getNome_produto()))
                                            produtosInativo += listaParaEnvio.get(x).getWebPedidoItens().get(i).getNome_produto() + "\n";
                                    }
                                }
                            }
                        }
                        if (!produtoAtivo)
                            showFeedbackDialog("O(s) produto(s) " + produtosInativo + " foi/foram inativado(s)/excluido(s) da base de dados\nFavor verificar/corrigir o(s) pedido(s) antes de exportar!!", true, false, null);
                        else
                            showFeedbackDialog("Não foi possivel enviar o pedido! Favor verificar o pedido e seus produtos, e tente novamente!", true, false, null);
                        dismissProgressDialogCustom();
                    }
                }

                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    setNotificacaoFalha(notificacao, notificationManager, "Problema de conexão!", "Não foi possivel enviar os pedidos!!",
                            "Não foi possivel enviar o(s) pedido(s)! Verifique sua conexão a rede/internet e tente novamente!\n"+ t.getMessage());
                }
            });
        }else {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Parametros para conexão não cadastrados!!", R.drawable.ic_alerta_mensagem);
        }

    }

    private void setNotificacaoFalha(NotificationCompat.Builder notificacao, NotificationManager notificationManager, String titulo, String texto, String textoAlerta) {
        getPedidos();
        dismissProgressDialogCustom();

        notificacao.setContentTitle(titulo)
                .setContentText(texto)
                .setProgress(0, 0, false)
                .setSmallIcon(R.mipmap.ic_sem_internet)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setPriority(2);
        notificationManager.notify(0, notificacao.build());
        showFeedbackDialog(textoAlerta, false, false, null);
    }

    private void setNotificacaoSucesso(NotificationCompat.Builder notificacao, NotificationManager notificationManager, PendingIntent pendingIntent,
                                       String titulo, String texto) {
        notificacao.setContentTitle(titulo)
                .setContentText(texto)
                .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                .setPriority(2)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(0, "PEDIDOS ENVIADOS", pendingIntent)
                .setProgress(0, 0, false)
                .setAutoCancel(true);
         notificationManager.notify(0, notificacao.build());
         getPedidos();
         dismissProgressDialogCustom();
    }

    private Call<Sincronia> getRotasAPI(int idRota, List<WebPedido> listaParaEnvio, Configuracao configuracao, String versaoApp) {
        Map<String, String> cabecalho = new HashMap<>();
        if ( UsuarioHelper.getUsuario() == null || UsuarioHelper.getUsuario().getToken() == null) {
            UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
        }
        Usuario usuario1 = UsuarioHelper.getUsuario();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Sincronia sincronia = new Sincronia(true,
                true);
        sincronia.setListaWebPedidosPendentes( listaParaEnvio );
        if ( idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                return apiRotas.sincronizaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + versaoApp + "/ws/";
                    Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();
                    Api.context = context;

                    apiRotas = Api.buildRetrofit2(true);
                    return apiRotas.sincronizaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
                } catch (NullPointerException ex) {
                    progress.dismiss();
                }
            }
        } else if (idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                return apiRotas2.sincronizaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();
                    Api.context = context;
                    apiRotas2 = Api.buildRetrofit2(true);
                    return apiRotas2.sincronizaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
                } catch (NullPointerException ex) {
                    progress.dismiss();
                }
            }
        }
        return  null;

    }

    public boolean usuarioIsLogado() {
        try {
            if (UsuarioHelper.getUsuario() == null || (UsuarioHelper.getUsuario().getId_usuario() == null || UsuarioHelper.getUsuario().getId_usuario().isEmpty())) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPedidoPendente.this));
                if (UsuarioHelper.getUsuario() == null) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!", R.drawable.ic_alerta_mensagem);
                    dismissProgressDialogCustom();
                    return false;
                }
            }
        } catch ( NullPointerException e) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content),"Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!\n" + e.getMessage(), R.drawable.ic_alerta_mensagem);
            dismissProgressDialogCustom();
            return false;
        } catch ( Exception e) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!!\n" + e.getMessage(), R.drawable.ic_alerta_mensagem);
            dismissProgressDialogCustom();
            return false;
        }
        return true;
    }

    private void excluirPedidoSincronizado() {
        try {
            Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
            if (!preferencia.getId().isEmpty()) {
                if (preferencia.getDias_hist_pedido() > 0) {
                    String data;
                    data = preferencia.getDias_hist_pedido() == 1 ? db.pegaDataExclusao("  SELECT date('now')") : db.pegaDataExclusao("  SELECT date('now',  '-" + ( preferencia.getDias_hist_pedido() + 1 ) + " days')");
                    List<WebPedido> listaPedido = new ArrayList<>();
                     if ( !data.isEmpty())
                         try {
                             listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND DATA_EMISSAO <= '" + data + "' ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC");
                             for (int i = 0; i < listaPedido.size(); i++)
                                 webPedidoDAO.delete(listaPedido.get(i));
                         } catch (NullPointerException e) {
                             e.printStackTrace();
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     else
                         showFeedbackDialog("falha ao obter o periodo para exclusão do(s) pedido(s) sincronizado(s)! Pedido(s) não excluido(s)", true, false, R.drawable.ic_alerta_mensagem);
                }
            }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean prepararItensPedidos(List<WebPedido> listaPedido) {
        for (WebPedido pedido : listaPedido) {
            try {
                if (pedido.getCadastro().getId_cadastro_servidor() <= 0) {
                    if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + pedido.getCadastro().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                        final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                        List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(ActivityPedidoPendente.this, pedido.getCadastro().getId_cadastro());
                        pedido.getCadastro().setListaCadastroAnexo(listaCadastroAnexo);
                    }
                }
            } catch (NullPointerException i) {  //i = ignored
                i.printStackTrace();
            }
            pedido.setWebPedidoItens(webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM"));
            pedido.setWebVisita(webVisitaDAO.getWebVisita(pedido.getId_web_pedido(), pedido.getCadastro().getId_cadastro_servidor()));
            try {
                if ( (Integer.parseInt(pedido.getQtde_itens()) != pedido.getWebPedidoItens().size()) || Integer.parseInt(pedido.getQtde_itens()) == 0) {
                    return false;
                }
            } catch (NumberFormatException | NullPointerException e){
                return false;
            }
        }
        return true;
    }

    public Boolean prepararAnexosPedidos(List<WebPedido> listaPedido) {
        PedidoAnexoDAO pedidoAnexoDAO = new PedidoAnexoDAO(db);
        for (WebPedido pedido : listaPedido) {
           if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_PEDIDO_ANEXOS WHERE ID_PEDIDO = " + pedido.getId_web_pedido()) > 0) {
              pedido.setWebPedidoAnexos(pedidoAnexoDAO.getLista( Integer.parseInt(pedido.getId_web_pedido()), 0));
           }
        }
        return true;
    }

    public Boolean analisaPedido(List<WebPedido> listaPedido) {
        for (WebPedido pedido : listaPedido) {
            List<WebPedidoItens> webPedidoItens;
            webPedidoItens = webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM");
            try {
                if (pedido.getQtde_itens() != null && !pedido.getQtde_itens().isEmpty()) {
                    if (Integer.parseInt(pedido.getQtde_itens()) != webPedidoItens.size()) {
                        return false;
                    }
                }
                if ( Integer.parseInt(pedido.getQtde_itens()) == 0) {
                    return false;
                }
            } catch (NumberFormatException | NullPointerException e) {
                if ( Integer.parseInt(pedido.getQtde_itens()) == 0) {
                    return false;
                }
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Boolean analisaPedido(WebPedido pedido) {
            List<WebPedidoItens> webPedidoItens;
            webPedidoItens = webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM");
            try {
                if (pedido.getQtde_itens() != null && !pedido.getQtde_itens().isEmpty()) {
                    if (Integer.parseInt(pedido.getQtde_itens()) != webPedidoItens.size()) {
                        return false;
                    }
                }
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        return true;
    }

    public List<WebPedido> analisaPedidoPendentes(List<WebPedido> listaPedido) {
        List<WebPedido> listaAnalise = new ArrayList<>();
        for (WebPedido pedido : listaPedido) {
            List<WebPedidoItens> webPedidoItens;
            webPedidoItens = webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM");
            try {
                if (pedido.getQtde_itens() != null && !pedido.getQtde_itens().isEmpty()) {
                    if (Integer.parseInt(pedido.getQtde_itens()) != webPedidoItens.size()) {
                        listaAnalise.add(pedido);
                    }
                }
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaAnalise;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pedido_pendente, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        final MenuItem item = menu.findItem(R.id.busca_pedido_pendente);
        searchView = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? (SearchView) item.getActionView() : (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(final String query) {
                if (query.trim().equals("")) {
                    offSet= 0;
                    if ( indItem == 0) {
                        SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                        totalReg = webPedidoDAO.getTotalReg(SQL);
                        SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                    } else if(indItem == 1) {
                        SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                        totalReg = webPedidoDAO.getTotalReg(SQL);
                        SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                    } else if(indItem == 2) {
                        SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                        totalReg = webPedidoDAO.getTotalReg(SQL);
                        SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                    }
                    if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                        listaPedido = webPedidoDAO.getLista(SQL + " LIMIT " + limitReg + " OFFSET " + offSet);
                    else
                        listaPedido = webPedidoDAO.getLista(SQL);
                    setRecyclerView(listaPedido);
                    //edtTotalPedidos.setText(listaPedido.size() + ": Pedidos Pendentes");
                } else {
                    //Analisar aqui quano o cliente é novo e inserir a pesquisa pelo nome fantasia
                    /*
                    offSet = 0;
                    //SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' )";
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    //SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                    SQL = "SELECT  WP.* FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR  = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' ) ORDER BY WP.ID_WEB_PEDIDO_SERVIDOR DESC ";

                    if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                        listaPedido = webPedidoDAO.getLista(SQL + " LIMIT " + limitReg + " OFFSET " + offSet);
                    else
                        listaPedido = webPedidoDAO.getLista(SQL);
                    setRecyclerView(listaPedido);
                     */
                    List<WebPedido> listaBusca = buscaPedidoPendente(listaPedido, query);
                    try {
                        totalReg = listaBusca.size();
                    } catch ( NumberFormatException|NullPointerException e ){
                        totalReg = 0;
                    } catch ( Exception e) {
                        totalReg = 0;
                    }
                    setRecyclerView(listaBusca);
                }
                //System.gc();
                return false;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Razão social/Nº pedido");
        return true;
    }

    private class QueryTextListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @SuppressLint("SuspiciousIndentation")
        @Override
        public boolean onQueryTextChange(final String query) {
             usuario = UsuarioHelper.getUsuario();
             if ( usuario == null) {
                onBackPressed();
                return false;
             }
            if (query.trim().equals("")) {
                offSet= 0;
                if ( indItem == 0) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                } else if(indItem == 1) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                } else if(indItem == 2) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                }
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                    listaPedido = webPedidoDAO.getLista(SQL + " LIMIT " + limitReg + " OFFSET " + offSet);
                else
                    listaPedido = webPedidoDAO.getLista(SQL);
                setRecyclerView(listaPedido);
                //edtTotalPedidos.setText(listaPedido.size() + ": Pedidos Pendentes");
            } else {
                //Analisar aqui quano o cliente é novo e inserir a pesquisa pelo nome fantasia
                /*
                    offSet = 0;
                    //SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' )";
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    //SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                    SQL = "SELECT  WP.* FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR  = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' ) ORDER BY WP.ID_WEB_PEDIDO_SERVIDOR DESC ";

                    if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                        listaPedido = webPedidoDAO.getLista(SQL + " LIMIT " + limitReg + " OFFSET " + offSet);
                    else
                        listaPedido = webPedidoDAO.getLista(SQL);
                    setRecyclerView(listaPedido);
                     */
                List<WebPedido> listaBusca = buscaPedidoPendente(listaPedido, query);
                try {
                    totalReg = listaBusca.size();
                } catch ( NumberFormatException|NullPointerException e ){
                    totalReg = 0;
                } catch ( Exception e) {
                    totalReg = 0;
                }
                setRecyclerView(listaBusca);
            }
            System.gc();
            return false;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }


    }

    public List<WebPedido> buscaPedidoPendente(List<WebPedido> webPedidos, String query) {
        final String upperCaseQuery = query.toUpperCase();
        final List<WebPedido> lista = new ArrayList<>();
        for (WebPedido webPedido : webPedidos) {
            try {
                final String nomeCliente = webPedido.getNome_extenso().toUpperCase();
                final String numeroPedido = webPedido.getId_web_pedido().toUpperCase();
                if (nomeCliente.contains(upperCaseQuery) || numeroPedido.equals(upperCaseQuery))
                    lista.add(webPedido);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public void setRecyclerView(List<WebPedido> lista) {
        int numColsList = 1;
        GridLayoutManager layoutManager;
        int orientation = this.getResources().getConfiguration().orientation;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        listaPedidoAdapter = new ListaPedidoAdapter(lista, this, this);
        if (configuracao.getGira_tela_ped_pendente().equalsIgnoreCase("S")) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        layoutManager = new GridLayoutManager(this, 2);
                        numColsList = 2;
                        break;
                    default:
                        layoutManager = new GridLayoutManager(this, 1);
                }
            } else {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                        layoutManager = new GridLayoutManager(this, 3);
                        numColsList = 3;
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        layoutManager = new GridLayoutManager(this, 1);
                        break;
                    default:
                        layoutManager = new GridLayoutManager(this, 2);
                        numColsList = 2;
                }
            }
        } else {
            switch (screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    layoutManager = new GridLayoutManager(this, 2);
                    numColsList = 2;
                    break;
                default:
                    layoutManager = new GridLayoutManager(this, 1);
                    break;
            }
        }
        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        int finalNumColsList = numColsList;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                if (listaPedidoAdapter != null) {
                    listaPedidoAdapter.setNumCols(finalNumColsList);
                    switch (listaPedidoAdapter.getItemViewType(i)) {
                        case 1:
                            return 1;
                        case 0:
                            return finalNumColsList;
                        default:
                            return -1;
                    }
                }else
                    return -1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        while (recyclerView.getItemDecorationCount() > 0)
            recyclerView.removeItemDecorationAt(0);
        recyclerView.setAdapter(listaPedidoAdapter);
        listaPedidoAdapter.notifyDataSetChanged();

        if (lista.size() > 0)
            edtTotalPedidos.setText("1/" + totalReg);
        else
            edtTotalPedidos.setText("0/" + totalReg);

    }

    @Override
    protected void onResume() {
        dismissProgressDialogCustom();
        getPedidos();
        super.onResume();
    }

    private void getPedidos() {
        getConfiguracao();
        try {
            if (configuracao.getGira_tela_ped_pendente().isEmpty() || configuracao.getGira_tela_ped_pendente().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        offSet = 0;
        try {
            if ( !configuracao.getLista_todos_pedidos().isEmpty()  && configuracao.getLista_todos_pedidos().equalsIgnoreCase("S")) {
                if(indItem == 0) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' ";
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' ORDER BY ID_WEB_PEDIDO DESC ";
                } else if(indItem == 1) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' ";
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' ORDER BY ID_WEB_PEDIDO DESC ";
                } else if(indItem == 2) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' ";
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' ORDER BY ID_WEB_PEDIDO DESC ";
                }
            } else {
                if(indItem == 0) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                }  else if(indItem == 1) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                } else if(indItem == 2) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                }
            }
            if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                listaPedido = webPedidoDAO.getLista(SQL + " LIMIT " + limitReg + " OFFSET " + offSet);
            else
                listaPedido = webPedidoDAO.getLista(SQL);
            if ( !configuracao.getLista_todos_pedidos().isEmpty()  && configuracao.getLista_todos_pedidos().equalsIgnoreCase("S")) {
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND (ID_PEDIDO_APP_FDB IS NULL OR ID_PEDIDO_APP_FDB = '')";
            }else {
                 SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND (ID_PEDIDO_APP_FDB IS NULL OR ID_PEDIDO_APP_FDB = '') AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() ;
            }
            int qtde = db.contagem(SQL) ;
            if( qtde == 1 && alerta) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Atenção, existe pedido pendente sem cópia de segurança, caso deseje efetue a cópia clicando no icone correspondente na lista de pedidos!", R.drawable.ic_alerta_mensagem);
                alerta = !alerta;
            } else if(qtde > 1 && alerta) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Atenção, existem pedidos pendentes sem cópia de segurança, caso deseje efetue as cópias clicando no icone correspondente em cada pedido da lista!", R.drawable.ic_alerta_mensagem);
                alerta = !alerta;
            }
            setRecyclerView(listaPedido);
            if (actionMode != null)
                actionMode.finish();
            listaPedidoAdapter.clearSelections();
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            listaPedido.clear();
            edtTotalPedidos.setText("0/" + totalReg);
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Nenhum pedido pendente!\n" + e.getMessage(), R.drawable.ic_alerta_mensagem);
        } catch ( Exception e) {
            listaPedido.clear();
            edtTotalPedidos.setText("0/" + totalReg);
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Nenhum pedido pendente!\n" + e.getMessage(), R.drawable.ic_alerta_mensagem);
        }
        ClienteHelper.clear();
    }

    @Override
    public void onPedidoRowClicked(int position) {
        if (position < 0 || position >= listaPedido.size()) return;

        if (listaPedidoAdapter.getSelectedItemCount() > 0) {
            indSel = position;
            enableActionMode(position);
        } else {
            WebPedido pedido = listaPedidoAdapter.getItem(position);
            
            // NOVO: Se o pedido estiver excluído (lixeira), abre direto a confirmação de restauração
            if (pedido.getExcluido().equalsIgnoreCase("S")) {
                showActionDialog(R.id.btnExcluir, pedido, "Deseja restaurar este pedido da lixeira?", null, position);
                return;
            }

            // NOVO: Chama o Hub de Ações para pedidos pendentes
            exibirDialogAcoesPedido(position);
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        if (position < 0 || position >= listaPedido.size()) return;

        if ( listaPedidoAdapter.getItem(position).getExcluido().equalsIgnoreCase("S")) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content),"Pedido na lixeira, a unica opção disponivel é a restauração, caso deseje restaurar , clique no icone/imagem correspondente", R.drawable.ic_alerta_mensagem);
            return;
        }
        enableActionMode(position);
        if (listaPedidoAdapter.getSelectedItemCount() > 0)
            indSel = position;
    }

    @Override
    public View.OnClickListener onClickExcluir(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( listaPedidoAdapter.getItem(position).getExcluido().equalsIgnoreCase("S")) {
                    showActionDialog(view.getId(), listaPedidoAdapter.getItem(position), "Deseja realmente restaurar o pedido " /*+ listaPedidoAdapter.getItem(position).getId_web_pedido()*/ + "da lixeira?", "", position);
                } else {
                    showActionDialog(view.getId(), listaPedidoAdapter.getItem(position), "Deseja realmente enviar o pedido " /*+ listaPedidoAdapter.getItem(position).getId_web_pedido()*/ + "para a lixeira?", "", position);
                }
            }
        };
    }

    @Override
    public View.OnClickListener onClickEnviar(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebPedido webPedido = listaPedidoAdapter.getItem(position);
                webPedido.setTipo_envio("1");
                try {
                    webPedido.setId_android(Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                } catch (Exception e) {
                    webPedido.setId_android("EXCEPTION");
                }
                List<WebPedido> listaPedidos = new ArrayList<>();
                listaPedidos.add(webPedido);
                
                showProgressDialogCustom("Analisando integridade do pedido...");
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (analisaPedido(listaPedidos)) {
                        dismissProgressDialogCustom();
                        showOrderSummaryDialog(webPedido, listaPedidos);
                    } else {
                        dismissProgressDialogCustom();
                        showActionDialog(webPedido, "Foi encontrada divergencia no pedido referente aos itens gravados! Deseja cancelar o envio para analisar o pedido n: " + webPedido.getId_web_pedido() + " de " + webPedido.getNome_extenso() + "?");
                    }
                }, 200);
            }
        };
    }

    @Override
    public View.OnClickListener onClickDuplic(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( listaPedidoAdapter.getItem(position).getCadastro().getId_cadastro() == 0){
                    showFeedbackDialog("Pré-pedido não pode ser duplicado!", true, false, R.drawable.ic_alerta_mensagem);
                    return;
                }
                if ( listaPedidoAdapter.getItem(position).getId_web_pedido() == null) {
                    showFeedbackDialog("Pedido não pode ser duplicado!", true, false, R.drawable.ic_alerta_mensagem);
                }
                showActionDialog(view.getId(), listaPedidoAdapter.getItem(position), "Deseja duplicar o pedido " /*+ listaPedidoAdapter.getItem(position).getId_web_pedido() + " de " + listaPedidoAdapter.getItem(position).getCadastro().getNome_cadastro()*/ + " para poder faturá-lo novamente?\n"/* +
                        "Atenção!!  Todos os produtos/descontos/promoções serão revisados, caso algum desconto/promoção não exista/e ou não sejam mais permitidos, os mesmos serão removidos de cada item, onde não sejam mais aplicáveis!"*/, "", position );
            }
        };
    }

    @Override
    public View.OnClickListener onClickCompartilhar(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indSel = position;
                //indAcao = 1;
                showActionDialog(view.getId(), listaPedidoAdapter.getItem(position), "Deseja criar um arquivo PDF do pedido " /*+ listaPedidoAdapter.getItem(position).getId_web_pedido()*/ + " para ser compartilhado?", "" , position);
            }
        };
    }

    @Override
    public View.OnClickListener onClickCopiaSegura(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showMsgBackupPedido(configuracao.getCnpj_empresa(), listaPedidoAdapter.getItem(position), position);
                showActionDialog(view.getId(), listaPedidoAdapter.getItem(position), "Gostaria de criar uma cópia de segurança do pedido? " /*+ listaPedidoAdapter.getItem(position).getCadastro().getNome_cadastro() + "?\n" +  listaPedidoAdapter.getItem(position).getId_pedido_app()*/, "", position );
                //showMsgSimNao( view.getId(), listaPedidoAdapter.getItem(position), "Deseja criar um arquivo PDF do pedido " + listaPedidoAdapter.getItem(position).getId_web_pedido() + " para ser compartilhado?", "" );
            }
        };
    }

    public void showMsgBackupPedido( String cnpj, WebPedido webPedido, int position) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoPendente.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoPendente.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText("Gostaria de criar uma cópia de segurança do pedido? " /*+ webPedido.getCadastro().getNome_cadastro() + "?\n" + webPedido.getId_pedido_app()*/);
        Button btnNao = dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(v -> alertDialog.dismiss());
        Button btnSim = dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(v -> {
            alertDialog.dismiss();
            addPedidoFirebase(cnpj, webPedido, position);
        });
        alertDialog.show();
    }
    private void addPedidoFirebase(String cnpj, WebPedido webPedido, int position) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected()) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content),"Você está offline. A copia de segurança não sera efetuada!.", R.drawable.ic_alerta_mensagem);
            return; // Interrompe o método
        }
        if ( cnpj == null || cnpj.isEmpty()) {
            configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            cnpj = configuracao.getCnpj_empresa().trim();
            if ( cnpj == null || cnpj.isEmpty()) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Parametros inválidos para efetuar a cópia de segurança!", R.drawable.ic_alerta_mensagem);
                return;
            }
        }
        showProgressDialogCustom("Efetuando cópia de segurança!Aguarde...");
        /*
        progress = new ProgressDialog(ActivityPedidoPendente.this);
        progress.setMessage("Efetuando cópia de segurança!Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();
         */
        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
        webPedido.setWebPedidoItens(webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM"));
        try {
            if (webPedido.getId_pedido_app_fdb() == null || webPedido.getId_pedido_app_fdb().isEmpty()) {
                //Toast.makeText(context, "Vai iniciar gravação do valor da chave", Toast.LENGTH_SHORT).show();
                webPedido.setId_pedido_app_fdb(
                        webPedido.getId_pedido_app().substring(0, 3)
                                + String.format("%08d", Integer.parseInt(webPedido.getId_web_pedido()))
                                + "DBFSTORE" + webPedido.getId_pedido_app().substring(19));
            }
        } catch (NullPointerException|NumberFormatException e) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao efetuar a copia de segurança!\n" + e.toString(), R.drawable.ic_alerta_mensagem);
            return;
        }
        if (webPedido.getId_pedido_app_fdb() == null || webPedido.getId_pedido_app_fdb().isEmpty()) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao efetuar a copia de segurança!!", R.drawable.ic_alerta_mensagem);
            return;
        }
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        for (Field field : webPedido.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(webPedido));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        String finalCnpj = cnpj;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            fdb.collection(finalCnpj).document("TBL_WEB_PEDIDO").collection(webPedido.getId_vendedor()).document(webPedido.getId_pedido_app_fdb()).set(map).addOnSuccessListener(documentReference -> {
                        dismissProgressDialogCustom();
                webPedidoDAO.updateIdFDB(webPedido, webPedido.getId_web_pedido(), "", "");
                listaPedido.get(position).setId_pedido_app_fdb(webPedido.getId_pedido_app_fdb());
                        showFeedbackDialog("Copia de segurança efetuada com sucesso!", true, false, null );
                    }
            ).addOnFailureListener(e -> {
                dismissProgressDialogCustom();
                showFeedbackDialog("Falha ao efetuar a copia de segurança!!\n" + e, true, false, null);
            });

        }, 500);


    }

    @Override
    public View.OnClickListener onClickRastrear(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityPedidoPendente.this, "Em desenvolvimento!", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public View.OnClickListener onClickLinhaTempo(int position) {
        return null;
    }

    private void enableActionMode(int position) {
        if (actionMode == null)
            actionMode = startSupportActionMode(actionModeCallback);
        toggleSelection(position);
    }

    private void reiniciarActivityPorErro() {
        Toast.makeText(this, "A seleção de pedidos apresentou uma inconsistência. A tela foi reiniciada.", Toast.LENGTH_LONG).show();
        if (actionMode != null) {
            try { actionMode.finish(); } catch (Exception ignored) {}
        }
        if (listaPedidoAdapter != null) {
            try { listaPedidoAdapter.clearSelections(); } catch (Exception ignored) {}
        }
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void toggleSelection(int position) {
        try {
            listaPedidoAdapter.toggleSelection(position);
            //int count = listaPedidoAdapter.getSelectedItemCount();
            if (listaPedidoAdapter.getSelectedItemCount() == 0) {
                actionMode.finish();
            } else {
                if (geraPDF != null)
                    if (listaPedidoAdapter.getSelectedItemCount() > 1)
                        geraPDF.setVisible(false);
                    else
                        geraPDF.setVisible(true);
               // if (emailPDF != null)
               //     if (listaPedidoAdapter.getSelectedItemCount() > 1)
               //         emailPDF.setVisible(false);
               //     else
               //         emailPDF.setVisible(false);
                if (duplicaPedido != null)
                    if (listaPedidoAdapter.getSelectedItemCount() > 1)
                        duplicaPedido.setVisible(false);
                    else
                        duplicaPedido.setVisible(true);
                if (analisePedido != null)
                    if (listaPedidoAdapter.getSelectedItemCount() > 1)
                        analisePedido.setVisible(false);
                    else
                        analisePedido.setVisible(true);
                boolean finalizado = false;
                boolean pre_pedido = false;
                boolean descto_indevido = false;
                if (enviaPedido != null) {
                    for (WebPedido webPedido : listaPedidoAdapter.getItensSelecionados()) {
                        if (webPedido.getFinalizado().equals("N"))
                            finalizado = true;
                        if (webPedido.getCadastro().getId_cadastro() == 0)
                            pre_pedido = true;
                        if ( webPedido.getDescontoIndevido().equalsIgnoreCase("S"))
                            descto_indevido = true;
                    }
                    for ( int i = 0; i < listaPedidoAdapter.getItensSelecionados().size(); i++) {
                        listaPedidoAdapter.getItensSelecionados().get(i).setTipo_envio("2");
                        try {
                            listaPedidoAdapter.getItensSelecionados().get(i).setId_android(Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                        } catch (Exception e) {
                            listaPedidoAdapter.getItensSelecionados().get(i).setId_android("EXCEPTION");
                        }
                    }
                    if (finalizado) {
                        enviaPedido.setVisible(false);
                        geraPDF.setVisible(false);
                        //emailPDF.setVisible(false);
                        duplicaPedido.setVisible(false);
                        selecionarTodos.setVisible(false);
                        analisePedido.setVisible(false);
                    } else {
                        enviaPedido.setVisible(true);
                        geraPDF.setVisible(true);
                       // emailPDF.setVisible(false);
                        duplicaPedido.setVisible(true);
                        selecionarTodos.setVisible(true);
                        analisePedido.setVisible(true);
                    }
                    if ( pre_pedido){
                        enviaPedido.setVisible(false);
                        duplicaPedido.setVisible(false);
                    }
                    if ( descto_indevido)
                        duplicaPedido.setVisible(false);
                }
                actionMode.setTitle(String.valueOf(listaPedidoAdapter.getSelectedItemCount()));
                actionMode.invalidate();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            reiniciarActivityPorErro();
        } catch (Exception e) {
            e.printStackTrace();
            reiniciarActivityPorErro();
        }
    }

    private void toggleSelectionAll() {
        listaPedidoAdapter.clearSelections();

        if ( listaPedido.size() > 0) {
            for (int i = 0; i < listaPedido.size(); i++) {
                listaPedidoAdapter.toggleSelection(i);
            }
        }
        actionMode.setTitle(String.valueOf(listaPedido.size()));
        //listaPedidoAdapter.toggleSelection(position);
        actionMode.invalidate();
    }

    private class RefreshListener implements  SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            //mSwipeRefreshLayout.setRefreshing(false);
            getPedidos();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            geraPDF = menu.findItem(R.id.action_pdf_pedido);
            //emailPDF = menu.findItem(R.id.action_pdf_pedido_email);
            duplicaPedido = menu.findItem(R.id.action_duplica_pedido);
            enviaPedido = menu.findItem(R.id.action_mode_menu_pedido_pendente);
            selecionarTodos = menu.findItem(R.id.action_select_all);
            analisePedido = menu.findItem(R.id.action_pedido_analise);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            final List<WebPedido> pedidosSelecionados = listaPedidoAdapter.getItensSelecionados();
            switch (item.getItemId()) {
                case R.id.action_delete:
                    if (pedidosSelecionados.size() == 1)
                        showActionDialog(mode, item.getItemId() , pedidosSelecionados, "Deseja realmente enviar o pedido " + pedidosSelecionados.get(0).getId_web_pedido() + " para a lixeira?");
                    else
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja realmente enviar para a lixeira os " + pedidosSelecionados.size() + " pedidos selecionados?");
                    return true;
                case R.id.action_mode_menu_pedido_pendente:
                    if (pedidosSelecionados.size() == 1) {
                        // NOVO: Chama o resumo direto
                        showOrderSummaryDialog(pedidosSelecionados.get(0), pedidosSelecionados);
                        
                        // ANTERIOR:
                        // showActionDialog(mode, item.getItemId() , pedidosSelecionados, "Deseja enviar o pedido n° " + pedidosSelecionados.get(0).getId_web_pedido() + " de "  + pedidosSelecionados.get(0).getCadastro().getNome_cadastro() + " para ser faturado?");
                    } else
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja enviar os " + pedidosSelecionados.size() + " pedidos selecionados para serem faturados?");
                    return true;
                case R.id.action_pdf_pedido:
                    if (pedidosSelecionados.size() == 1)
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja gerar um arquivo PDF do pedido? "/* + pedidosSelecionados.get(0).getId_web_pedido() + "?"*/);
                    else
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),"Somente é permitido a seleção/criação de um pedido por vez", R.drawable.ic_alerta_mensagem);
                    return true;
                /*
                    case R.id.action_pdf_pedido_email:
                    if (pedidosSelecionados.size() == 1)
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja enviar um arquivo PDF do pedido " + pedidosSelecionados.get(0).getId_web_pedido() + " para o email do cliente ?");
                    else
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Somente é permitido a seleção/envio de um pedido por vez", R.drawable.ic_alerta_mensagem);
                        //showMsgSimNao(mode, item.getItemId(), pedidosSelecionados, "Deseja enviar os " + pedidosSelecionados.size() + " pedidos selecionados para os emails de seus clientes?");
                    return true;

                 */
                case R.id.action_duplica_pedido:
                    if (pedidosSelecionados.size() == 1)
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja duplicar o pedido " /*+ pedidosSelecionados.get(0).getId_web_pedido() + " de " +  pedidosSelecionados.get(0).getCadastro().getNome_cadastro()*/ + " para poder faturá-lo novamente?\n"/* +
                            "Atenção!!\n" +
                            "Todos os produtos/descontos/promoções serão revisados, caso algum desconto/promoção não exista/e ou não sejam mais permitidos, os mesmos serão removidos de cada item, onde não sejam mais aplicáveis!"*/ );
                    else
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Somente é permitido a seleção/duplicação de um pedido por vez", R.drawable.ic_alerta_mensagem);
                    return true;
                case R.id.action_select_all:
                    // NOVO: Chama o diálogo antigo (padrão do sistema) para selecionar todos
                    new AlertDialog.Builder(ActivityPedidoPendente.this)
                            .setTitle("Atenção!")
                            .setMessage("Deseja selecionar todos os pedidos pendentes?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    toggleSelectionAll();
                                }
                            })
                            .setNegativeButton("NÃO", null)
                            .show();
                    return true;
                case R.id.action_pedido_analise:
                    if ( pedidosSelecionados.size() > 1) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Selecione apenas um pedido para efetuar a analise de estoque", R.drawable.ic_alerta_mensagem);
                        return false;
                    }
                    sincronizaEstoque(pedidosSelecionados);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            listaPedidoAdapter.clearSelections();
            actionMode = null;
            getPedidos();
        }
    }


/*
    public  void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoPendente.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoPendente.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
*/
    /*
    public  void showMsgAlerta2(String mensagem, List<WebPedido> listaPedido) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoPendente.this).inflate(R.layout.dialog_alerta_mensagem_2, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoPendente.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaPdf(listaPedido, 4);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
*/
    /*
    public  void showMsgAlertErroPedido(String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoPendente.this).inflate(R.layout.dialog_alerta_mensagem_erro_pedido, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoPendente.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
*/
    /*
    public void showMsgSimNao(ActionMode mode, final int id,  List<WebPedido> pedidosSelecionados, String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoPendente.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoPendente.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mode.finish();
                } catch ( NullPointerException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( id == R.id.action_delete) {
                    try {
                        mode.finish();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    try {
                        int qtde = 0;
                        for (int i = 0; i < pedidosSelecionados.size(); i++) {
                            WebPedido webPedidoL = pedidosSelecionados.get(i);
                            webPedidoL.setExcluido("S");
                            if (webPedidoDAO.addLixeira(webPedidoL)) {
                                try {
                                    int idPed = Integer.parseInt(webPedidoL.getId_web_pedido());
                                    new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, webPedidoL.getId_pedido_app(), "S");
                                } catch (Exception ignored) {}
                                delPedidoFirebase(configuracao.getCnpj_empresa(), pedidosSelecionados.get(i));
                                qtde++;
                            } else {
                                showFeedbackDialog("Falha ao enviar  o(s) pedido(s) selecionado(s) para a lixeira", false, false, null);
                                getPedidos();
                            }
                        }
                        if (pedidosSelecionados.size() == 1)
                            showFeedbackDialog("O Pedido selecionado foi enviado para a lixeira!", true, false, null);
                        else
                            showFeedbackDialog("Os Pedidos selecionados foram enviados para a lixeira!", true, false, null);
                        listaPedidoAdapter.remove(pedidosSelecionados.get(0));
                        alertDialog.dismiss();
                        getPedidos();
                    } catch (Exception e) {
                        alertDialog.dismiss();
                        Toast.makeText(ActivityPedidoPendente.this, "Não foi possivel enviar o(s) pedido(s) para a lixeira! Tente novamente!", Toast.LENGTH_LONG).show();
                    }
                } else if (id == R.id.action_pedido_analise) {
                    List<WebPedido> lista = analisaPedidoPendentes(pedidosSelecionados);
                    sincronizaEstoque(lista);
                } else if ( id == R.id.action_mode_menu_pedido_pendente || id == R.id.menu_pedido_pendente) {
                    try {
                        mode.finish();
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                    List<WebPedido> lista = analisaPedidoPendentes(pedidosSelecionados);

                    if ( lista.size() > 0) {
                        String pedidos = "";
                        for (int i = 0; i < lista.size(); i++) {
                            pedidos += "Pedido n: " + lista.get(i).getId_web_pedido() + " de " + lista.get(i).getNome_extenso() + "\n";
                        }
                        showFeedbackDialog("Foram encontradas divergencias! Favor verificar o(s) itens gravados no(s) pedido(s) informado(s) na mensagem\n" + pedidos, true, false, R.drawable.ic_alerta_mensagem);
                    } else {
                        for(WebPedido webPedido : pedidosSelecionados) {
                            if ( webPedido.getData_prev_entrega() == null || webPedido.getData_prev_entrega().isEmpty() || webPedido.getData_prev_entrega().equalsIgnoreCase("/  /")){
                                showFeedbackDialog("Data de entrega/faturamento inválida/não informada  em um ou mais pedidos selecionados!Favor verificar antes de enviar os pedidos!", true, false, R.drawable.ic_alerta_mensagem);
                                return;
                            }
                        }
                        sincronizaPedidos(pedidosSelecionados);
                    }
                } else if ( id == R.id.action_pdf_pedido) {
                    try {
                        alertDialog.dismiss();
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                    indAcao = 1;
                    geraArquivoPDF(pedidosSelecionados, indAcao);
                } else if( id == R.id.action_pdf_pedido_email ) {
                    try {
                        alertDialog.dismiss();
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                    indAcao = 2;
                    geraArquivoPDF(pedidosSelecionados, indAcao);
                } else if ( id == R.id.action_duplica_pedido) {
                    try {
                        mode.finish();
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                    duplicaPedido( pedidosSelecionados.get(0));
               } else if ( id == R.id.action_select_all) {
                    toggleSelectionAll();
                    try {
                        alertDialog.dismiss();
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        alertDialog.show();
    }
*/
    private void geraArquivoPDF(List<WebPedido> pedidosSelecionados, int indAcao) {
        try {
            if (pedidosSelecionados.get(0).getCadastro() == null) {
                pedidosSelecionados.get(0).setCadastro(new Cliente());
                pedidosSelecionados.get(0).getCadastro().setId_cadastro(0);
            }
        } catch (NullPointerException e) {
            pedidosSelecionados.get(0).setCadastro(new Cliente());
            pedidosSelecionados.get(0).getCadastro().setId_cadastro(0);
        } catch (Exception e) {
            pedidosSelecionados.get(0).setCadastro(new Cliente());
            pedidosSelecionados.get(0).getCadastro().setId_cadastro(0);
        }
        if (pedidosSelecionados.get(0).getCadastro().getId_cadastro() == 0) {
            final EditText txtNomeCliente = new EditText(ActivityPedidoPendente.this);
            new AlertDialog.Builder(ActivityPedidoPendente.this)
                    .setTitle("RCK SISTEMAS ESPECIFICOS")
                    .setMessage("Informe o nome/razão social do cliente ")
                    .setView(txtNomeCliente)
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            pedidosSelecionados.get(0).getCadastro().setNome_cadastro(txtNomeCliente.getText().toString());
                            analisarDebitoParaPdf(pedidosSelecionados, indAcao);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Ação cancelada!", R.drawable.ic_alerta_mensagem);
                        }
                    })
                    .show();
        } else {
            analisarDebitoParaPdf(pedidosSelecionados, indAcao);
        }

    }

    /**
     * NOVO: Verifica se o cliente possui apenas débitos vencidos e solicita permissão para destacar no PDF.
     */
    private void analisarDebitoParaPdf(List<WebPedido> pedidos, int indAcao) {
        if (pedidos == null || pedidos.isEmpty() || pedidos.get(0).getCadastro() == null) {
            criaPdf(pedidos, indAcao, false);
            return;
        }

        Cliente cli = pedidos.get(0).getCadastro();
        float vencido = 0.00f;
        float vencendo = 0.00f;
        try {
            vencido = Float.parseFloat(cli.getFinanceiro_vencido() == null ? "0.00" : cli.getFinanceiro_vencido());
            vencendo = Float.parseFloat(cli.getFinanceiro_vencendo() == null ? "0.00" : cli.getFinanceiro_vencendo());
        } catch (Exception ignored) {}

        // Regra: Caso tenha título SOMENTE VENCIDO
        if (vencido > 0 && vencendo <= 0) {
            DialogHelper.showDebtorAlertaDialog(ActivityPedidoPendente.this, vencido, 0, 
                    "Deseja que o valor do débito vencido seja DESTACADO no PDF do mesmo?", 
                    "SIM, DESTACAR", "NÃO", () -> {
                // Se clicar em SIM
                criaPdf(pedidos, indAcao, true);
            }, () -> {
                // Se clicar em NÃO (Ainda gera o PDF, mas sem destaque)
                criaPdf(pedidos, indAcao, false);
            });
        } else {
            criaPdf(pedidos, indAcao, false);
        }
    }

    private void criaPdf(List<WebPedido> pedidosSelecionados, int indAcao, boolean destacarDebito) {
        cliqueAtivo = true; // Libera cliques após iniciar o processo de PDF
        EmpresaParametro empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + pedidosSelecionados.get(0).getId_empresa() + "'");
        if (empresa == null) {
            empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO");
            empresa = null;
            if ( empresa == null) {
                dismissProgressDialogCustom();
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao obter as informações da empresa!!!\nFavor entrar em contato com o suporte técnico!!!\n", R.drawable.ic_alerta_mensagem);
                //return;
            }
        }
        File filePDF;
        if (empresa == null) {
            PDFPedidoUtil2026 pdfPedidoUtil = new PDFPedidoUtil2026(pedidosSelecionados.get(0), empresa, ActivityPedidoPendente.this, indAcao);
            pdfPedidoUtil.setExibirDebitoNoPdf(destacarDebito);
            if ( indAcao == 4) {
                filePDF = pdfPedidoUtil.criandoPdf("AN");
            } else {
                filePDF = pdfPedidoUtil.criandoPdf("PO");
            }
            if (filePDF == null) {
                dismissProgressDialogCustom();
                return;
            }
        } else {
            try {
                if (empresa.getUtiliza_icms_app().equalsIgnoreCase("S") ) {
                    PDFPedidoICMSST pdfPedidoUtil = new PDFPedidoICMSST(pedidosSelecionados.get(0), empresa, ActivityPedidoPendente.this);
                    pdfPedidoUtil.setExibirDebitoNoPdf(destacarDebito);
                    if ( indAcao == 4) {
                        filePDF = pdfPedidoUtil.criandoPdf("AN");
                    } else {
                        filePDF = pdfPedidoUtil.criandoPdf("PO");
                    }
                } else {
                    PDFPedidoUtil2026 pdfPedidoUtil = new PDFPedidoUtil2026(pedidosSelecionados.get(0), empresa, ActivityPedidoPendente.this, indAcao);
                    pdfPedidoUtil.setExibirDebitoNoPdf(destacarDebito);
                    if ( indAcao == 4) {
                        filePDF = pdfPedidoUtil.criandoPdf("AN");
                    } else {
                        filePDF = pdfPedidoUtil.criandoPdf("PO");
                    }
                }
            } catch (NullPointerException e) {
                PDFPedidoUtil2026 pdfPedidoUtil = new PDFPedidoUtil2026(pedidosSelecionados.get(0), empresa, ActivityPedidoPendente.this, indAcao);
                pdfPedidoUtil.setExibirDebitoNoPdf(destacarDebito);
                if ( indAcao == 4) {
                    filePDF = pdfPedidoUtil.criandoPdf("AN");
                } else {
                    filePDF = pdfPedidoUtil.criandoPdf("PO");
                }
            } catch (Exception e) {
                PDFPedidoUtil2026 pdfPedidoUtil = new PDFPedidoUtil2026(pedidosSelecionados.get(0), empresa, ActivityPedidoPendente.this, indAcao);
                pdfPedidoUtil.setExibirDebitoNoPdf(destacarDebito);
                if ( indAcao == 4) {
                    filePDF = pdfPedidoUtil.criandoPdf("AN");
                } else {
                    filePDF = pdfPedidoUtil.criandoPdf("PO");
                }
            }
            if (filePDF == null) {
                dismissProgressDialogCustom();
                return;
            }
        }
        if ( Build.VERSION.SDK_INT >+ Build.VERSION_CODES.N) {
            if ( indAcao == 1 || indAcao == 4) {
                try {
                    Uri contentUri = FileProvider.getUriForFile(ActivityPedidoPendente.this, getApplicationContext().getPackageName() + ".provider", filePDF);
                    startActivity(new Intent()
                            .setAction(Intent.ACTION_VIEW)
                            .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            .setDataAndType(contentUri, "application/pdf"));
                    try {
                        listaPedidoAdapter.clearSelections();
                        actionMode = null;
                        dismissProgressDialogCustom();
                    } catch (NullPointerException e) {
                        dismissProgressDialogCustom();
                        showActionDialog("Aplicativo não pode criar/armazenar arquivos!\nFavor verifique as permissões concedidas!" , String.valueOf(indAcao) + "-" + e.toString()  );
                    }
                } catch (NullPointerException e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo não pode criar/armazenar arquivos!!\nFavor verifique as permissões concedidas!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                } catch (Exception e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo não pode criar/armazenar arquivos!!!\nFavor verifique as permissões concedidas!!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                }
            }else if(indAcao == 2) {
                try {
                    final Intent intent = new Intent(Intent.ACTION_SENDTO);
                    if (pedidosSelecionados.get(0).getCadastro().getEmail_principal() != null && !pedidosSelecionados.get(0).getCadastro().getEmail_principal().trim().equals(""))
                        intent.setData(Uri.parse("mailto: " + pedidosSelecionados.get(0).getCadastro().getEmail_principal()));
                    else if (pedidosSelecionados.get(0).getCadastro().getEmail_financeiro() != null && !pedidosSelecionados.get(0).getCadastro().getEmail_financeiro().trim().equals(""))
                        intent.setData(Uri.parse("mailto: " + listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro()));
                    else
                        intent.setData(Uri.parse("mailto: Informe o email do cliente"));
                    startActivity(intent
                            .putExtra(Intent.EXTRA_SUBJECT, "Espelho do pedido " + pedidosSelecionados.get(0).getId_web_pedido())
                            .putExtra(Intent.EXTRA_TEXT, "Segue em anexo o espelho do pedido")
                            .putExtra(Intent.EXTRA_STREAM, Uri.fromFile(filePDF)));
                    try {
                        listaPedidoAdapter.clearSelections();
                        actionMode = null;
                        dismissProgressDialogCustom();
                    } catch (NullPointerException e) {
                        dismissProgressDialogCustom();
                        showActionDialog("Aplicativo não pode criar/armazenar/email arquivos!\nFavor verifique as permissões concedidas!" , String.valueOf(indAcao) + "-" + e.toString()  );
                    }
                } catch (NullPointerException e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo não pode criar/armazenar/email arquivos!!\nFavor verifique as permissões concedidas!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                } catch (Exception e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo não pode criar/armazenar/email arquivos!!!\nFavor verifique as permissões concedidas!!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                }
            } else if ( indAcao == 3) {
                try {
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND)
                            .setType("pdf/*")
                            .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(ActivityPedidoPendente.this, getApplicationContext().getPackageName() + ".provider", filePDF))
                            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION), "Compartilhar arquivo do pedido " + pedidosSelecionados.get(0).getId_web_pedido()));
                    try {
                        listaPedidoAdapter.clearSelections();
                        actionMode = null;
                        dismissProgressDialogCustom();
                    } catch (NullPointerException e) {
                        dismissProgressDialogCustom();
                        showActionDialog("Aplicativo não pode criar/armazenar/compartilhar arquivos!\nFavor verifique as permissões concedidas!" , String.valueOf(indAcao) + "-" + e.toString()  );
                    }
                } catch (NullPointerException e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo não pode criar/armazenar/compartilhar arquivos!!\nFavor verifique as permissões concedidas!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                } catch (Exception e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo não pode criar/armazenar/compartilhar arquivos!!!\nFavor verifique as permissões concedidas!!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                }

            } else {
                dismissProgressDialogCustom();
                return;
            }

        } else {
            if ( indAcao == 1 || indAcao == 4) {
                try {
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW)
                            .setDataAndType(Uri.fromFile(filePDF), "application/pdf"), "Abrir arquivo"));
                    try {
                        listaPedidoAdapter.clearSelections();
                        actionMode = null;
                        dismissProgressDialogCustom();
                    } catch ( NullPointerException e) {
                        dismissProgressDialogCustom();
                        showActionDialog("Aplicativo sem permissão para criação/armazenamento de arquivos!\nFavor verifique as permissões concedidas!" , String.valueOf(indAcao) + "-" + e.toString()  );
                    }
                } catch (NullPointerException e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo sem permissão para criação/armazenamento de arquivos!!\nFavor verifique as permissões concedidas!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                } catch (Exception e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo sem permissão para criação/armazenamento de arquivos!!!\nFavor verifique as permissões concedidas!!!" , String.valueOf(indAcao) + "-" + e.toString()  );

                }
            } else if (indAcao == 2) {
                try {
                    final Intent intent = new Intent(Intent.ACTION_SENDTO);
                    if (pedidosSelecionados.get(0).getCadastro().getEmail_principal() != null && !pedidosSelecionados.get(0).getCadastro().getEmail_principal().trim().equals(""))
                        intent.setData(Uri.parse("mailto: " + pedidosSelecionados.get(0).getCadastro().getEmail_principal()));
                    else if (pedidosSelecionados.get(0).getCadastro().getEmail_financeiro() != null && !pedidosSelecionados.get(0).getCadastro().getEmail_financeiro().trim().equals(""))
                        intent.setData(Uri.parse("mailto: " + listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro()));
                    else
                        intent.setData(Uri.parse("mailto: Informe o email do cliente"));
                    startActivity(intent
                            .putExtra(Intent.EXTRA_SUBJECT, "Espelho do pedido " + pedidosSelecionados.get(0).getId_web_pedido())
                            .putExtra(Intent.EXTRA_TEXT, "Segue em anexo o espelho do pedido")
                            .putExtra(Intent.EXTRA_STREAM, Uri.fromFile(filePDF)));
                    try {
                        listaPedidoAdapter.clearSelections();
                        actionMode = null;
                        dismissProgressDialogCustom();
                    } catch ( NullPointerException e) {
                        dismissProgressDialogCustom();
                        showActionDialog("Aplicativo sem permissão para criação/armazenamento/email de arquivos!\nFavor verifique as permissões concedidas!" , String.valueOf(indAcao) + "-" + e.toString()  );
                    }
                } catch (NullPointerException e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo sem permissão para criação/armazenamento/email de arquivos!!\nFavor verifique as permissões concedidas!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                } catch (Exception e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo sem permissão para criação/armazenamento/email de arquivos!!!\nFavor verifique as permissões concedidas!!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                }
            }else if ( indAcao == 3) {
                try {
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND)
                            .setType("pdf/*")
                            .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(ActivityPedidoPendente.this, getApplicationContext().getPackageName() + ".provider", filePDF))
                            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION), "Compartilhar arquivo do pedido " + pedidosSelecionados.get(0).getId_web_pedido()));
                    try {
                        listaPedidoAdapter.clearSelections();
                        actionMode = null;
                        dismissProgressDialogCustom();
                    } catch (NullPointerException e) {
                        dismissProgressDialogCustom();
                        showActionDialog("Aplicativo sem permissão para criação/armazenamento/compartilhar arquivos!\nFavor verifique as permissões concedidas!" , String.valueOf(indAcao) + "-" + e.toString()  );
                        e.printStackTrace();
                    }
                } catch (NullPointerException e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo sem permissão para criação/armazenamento/compartilhar arquivos!!\nFavor verifique as permissões concedidas!!" , String.valueOf(indAcao) + "-" + e.toString()  );
                } catch (Exception e) {
                    dismissProgressDialogCustom();
                    showActionDialog("Aplicativo sem permissão para criação/armazenamento/compartilhar arquivos!!!\nFavor verifique as permissões concedidas!!!" , String.valueOf(indAcao) + "-" + e.toString()  );

                }
            } else{
                dismissProgressDialogCustom();
                return;
            }
        }
    }

    /*
    private void showAlertInfoApp(String mensagem, String erro) {
        new AlertDialog.Builder(ActivityPedidoPendente.this)
                .setTitle("RCK SISTEMAS ESPECIFICOS!")
                .setMessage(mensagem + "\n" + erro)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(ActivityPedidoPendente.this, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
*/
    /*
    private void showAlertVerificaPedido(WebPedido pedido) {
        new AlertDialog.Builder(ActivityPedidoPendente.this)
                .setTitle("RCK SISTEMAS ESPECIFICOS!")
                .setMessage("Foi encontrada divergencia no pedido referente aos itens gravados! Deseja cancelar o envio para analisar o pedido n: " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + "?")
                .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(ActivityPedidoPendente.this, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        List<WebPedido> lista = new ArrayList<>();
                        lista.add(pedido);
                        sincronizaPedidos(lista);
                    }
                })
                .show();
    }
*/
    /*
    public void showMsgSimNao( final int id ,WebPedido webPedido, String mensagem, String tipoEnvio) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoPendente.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoPendente.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == R.id.lyExcluir || id == R.id.btnExcluir) {
                    if (webPedido.getExcluido().equalsIgnoreCase("S")) {
                        try {
                            webPedido.setExcluido("N");
                            if (webPedidoDAO.removeLixeira(webPedido)) {
                                try {
                                    int idPed = Integer.parseInt(webPedido.getId_web_pedido());
                                    new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, webPedido.getId_pedido_app(), "N");
                                } catch (Exception ignored) {}
                                //delPedidoFirebase(configuracao.getCnpj_empresa(), webPedido);
                                listaPedidoAdapter.remove(webPedido);
                                alertDialog.dismiss();
                                showFeedbackDialog("O Pedido foi restaurado da lixeira!", true, false, null);
                                getPedidos();
                            } else {
                                showFeedbackDialog("Falha ao restaurar o pedido " + webPedido.getId_web_pedido() + " de " + webPedido.getNome_extenso() + " da lixeira", false, false, null);
                                getPedidos();
                            }
                        } catch (Exception e) {
                            alertDialog.dismiss();
                            getPedidos();
                            e.printStackTrace();
                            Toast.makeText(ActivityPedidoPendente.this, "Não foi possivel resraurar o pedido da lixeira! Tente novamente!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            webPedido.setExcluido("S");
                            if (webPedidoDAO.addLixeira(webPedido)) {
                                try {
                                    int idPed = Integer.parseInt(webPedido.getId_web_pedido());
                                    new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, webPedido.getId_pedido_app(), "S");
                                } catch (Exception ignored) {}
                                delPedidoFirebase(configuracao.getCnpj_empresa(), webPedido);
                                listaPedidoAdapter.remove(webPedido);
                                alertDialog.dismiss();
                                showFeedbackDialog("O Pedido foi enviado para a lixeira!", true, false, null);
                                getPedidos();
                            } else {
                                showFeedbackDialog("Falha ao enviar o pedido " + webPedido.getId_web_pedido() + " de " + webPedido.getNome_extenso() + " para a lixeira", false, false, null);
                                getPedidos();
                            }
                        } catch (Exception e) {
                            alertDialog.dismiss();
                            getPedidos();
                            e.printStackTrace();
                            Toast.makeText(ActivityPedidoPendente.this, "Não foi possivel enviar o(s) pedido(s) para a lixeira! Tente novamente!", Toast.LENGTH_LONG).show();
                        }
                    }
                } else if ( id == R.id.action_pedido_analise) {
                    List<WebPedido> listaPedidos = new ArrayList<>();
                    listaPedidos.add(webPedido);
                    sincronizaEstoque(listaPedido);
                } else if ( id == R.id.lyEnvia || id == R.id.btnEnviar) {
                    try {
                        alertDialog.dismiss();
                    } catch ( NullPointerException e) {
                    } catch ( Exception e) {
                    }
                    webPedido.setTipo_envio("1");
                    try {
                        webPedido.setId_android(Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                    } catch (Exception e) {
                        webPedido.setId_android("EXCEPTION");
                    }
                    List<WebPedido> listaPedidos = new ArrayList<>();
                    listaPedidos.add(webPedido);
                    if ( webPedido.getData_prev_entrega() == null || webPedido.getData_prev_entrega().isEmpty() || webPedido.getData_prev_entrega().equalsIgnoreCase("/  /")){
                        showFeedbackDialog("Data de entrega/faturamento inválida/não informada|Favor verificar antes de enviar o pedido!", true, false, R.drawable.ic_alerta_mensagem);
                        return;
                    }
                    if ( analisaPedido(listaPedidos)){
                        //sincronizaEstoque(listaPedidos);
                        sincronizaPedidos(listaPedidos);
                    } else {
                        showAlertVerificaPedido(webPedido);
                    }

                } else if ( id == R.id.btnDuplic) {
                    if ( webPedido.getCadastro().getId_cadastro() == 0) {
                        showFeedbackDialog("Pré-pedido não pode ser duplicado!!", true, false, R.drawable.ic_alerta_mensagem);
                        return;
                    }
                    try {
                        alertDialog.dismiss();
                    } catch ( NullPointerException e) {
                    } catch ( Exception e) {
                    }
                    duplicaPedido(webPedido);
                } else if ( id == R.id.btnCompartilhar) {
                    try {
                        alertDialog.dismiss();
                    } catch ( NullPointerException e) {
                    } catch ( Exception e) {

                    }
                    indAcao = 3;
                    List<WebPedido> lista = new ArrayList<>();
                    listaPedidoAdapter.toggleSelection(indSel);
                    lista.add(webPedido);
                    geraArquivoPDF( lista,indAcao);  // 3 Compartilha PDF
                }
            }
        });
        alertDialog.show();
    }
*/
    private void duplicaPedido(WebPedido webPedido) {
        new PedidoHelper().limparDados();

        // 2. Garante que o cliente selecionado esteja ativo no Helper
        ClienteHelper.setCliente(null);
        try {
            if (listaPedidoAdapter != null) {
                listaPedidoAdapter.clearSelections();
            }
            actionMode = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPedidoPendente.this));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPedidoPendente.this));
        } catch (Exception e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPedidoPendente.this));
        }
        String SQL = "SELECT * FROM TBL_CONFIGURACAO";
        configuracao = new ConfiguracaoDAO(db).getConfiguracao(SQL);
        SQL = "SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + "'";
        EmpresaParametro empresaParametro = new EmpresaParametroDAO(db).getEmpresa(SQL);
        Float saldoVerba = 0.00f;
        try {
            if (configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S")) {
                VendedorBonusResumo vendedorBonusResumo = new VendedorBonusResumoDAO(db).getVendedor("SELECT * FROM TBL_VENDEDOR_BONUS_RESUMO WHERE ID_VENDEDOR = '" + UsuarioHelper.getUsuario().getId_quando_vendedor() + "'");
                saldoVerba = Float.parseFloat(vendedorBonusResumo.getValor_saldo());
            }
        } catch ( NullPointerException|CursorIndexOutOfBoundsException|NumberFormatException e) {
        } catch ( Exception e) {
        }

        WebPedido webPedidoDuplicado = webPedido;
        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
        CampanhaComercialCabDAO campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        SQL = "SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedidoDuplicado.getId_web_pedido() + " ORDER BY ID_WEB_ITEM";
        webPedidoDuplicado.setWebPedidoItens(webPedidoItensDAO.getListaDuplicacao(SQL, configuracao, empresaParametro, saldoVerba, webPedidoDuplicado.getCadastro(), campanhaComercialCabDAO));
        webPedidoDuplicado.setId_web_pedido(null);
        webPedidoDuplicado.setId_web_pedido_servidor(null);
        webPedidoDuplicado.setPedido_enviado("N");
        webPedidoDuplicado.setFinalizado("N");
        webPedidoDuplicado.setData_emissao(db.pegaDataAtual() );
        webPedidoDuplicado.setData_prev_entrega("");
        webPedidoDuplicado.setId_pedido_app_fdb(null);
        webPedidoDuplicado.setWeb_importado_data(null);
        webPedidoDuplicado.setAnalise_usuario_id(null);
        webPedidoDuplicado.setAnalise_usuario_nome(null);
        webPedidoDuplicado.setId_ordem_carregamento(null);
        webPedidoDuplicado.setOrdem_carregamento(null);
        webPedidoDuplicado.setNumero_nota_fiscal(null);
        webPedidoDuplicado.setData_nota_fiscal(null);
        webPedidoDuplicado.setData_nota_fiscal(null);
        webPedidoDuplicado.setEspecie(null);
        webPedidoDuplicado.setVolumes(null);
        webPedidoDuplicado.setWeb_valor_total(null);
        webPedidoDuplicado.setId_pedido_venda(null);
        webPedidoDuplicado.setId_nota_fiscal(null);
        webPedidoDuplicado.setId_pedido(null);
        webPedidoDuplicado.setAnalise_usuario_data(null);
        webPedidoDuplicado.setValor_faturado(null);
        try {
            webPedido.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
            webPedido.setId_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
            webPedido.setId_outro_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
            webPedido.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
            webPedido.setUsuario_lancamento_nome(UsuarioHelper.getUsuario().getNome_usuario());
        } catch (NullPointerException e) {
        } catch ( Exception e) {
        }
        try {
            ClienteHelper.setCliente(webPedidoDuplicado.getCadastro());
        } catch (NullPointerException e) {
            showFeedbackDialog("Cliente não localizado!", false, false, null);
        } catch (Exception e) {
            showFeedbackDialog("Cadastro do cliente não localizado!", false, false, null);
        }
        for (WebPedidoItens webPedidoItens : webPedidoDuplicado.getWebPedidoItens()) {
            webPedidoItens.setId_web_item_servidor(null);
            webPedidoItens.setId_pedido("");
            webPedidoItens.setId_web_item(null);
        }
        cliqueAtivo = true;
        PedidoHelper.setWebPedido(webPedidoDuplicado);
        PedidoHelper.setListaWebPedidoItens(webPedidoDuplicado.getWebPedidoItens());
        startActivity(new Intent(ActivityPedidoPendente.this, ActivityPedidoMain.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100) {
                final List<WebPedido> pedidosSelecionados = listaPedidoAdapter.getItensSelecionados();
                analisarDebitoParaPdf(pedidosSelecionados, indAcao);
            }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void delPedidoFirebase(String cnpj, WebPedido webPedido) {
        if ( (webPedido.getId_pedido_app_fdb() == null || webPedido.getId_pedido_app_fdb().isEmpty()) || ( cnpj == null || cnpj.isEmpty() ) ){
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();

        try {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                db.collection(cnpj).document("TBL_WEB_PEDIDO").collection(webPedido.getId_vendedor()).document(webPedido.getId_pedido_app_fdb()).delete().addOnSuccessListener(documentReference -> {
                            //showMsgAlerta("Copia de segurança apagada com sucesso!" );
                        }
                ).addOnFailureListener(e -> {
                    //showMsgAlerta("Falha ao criar o cadastro!!\n" + e);
                });

            }, 1000);
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
    }


    public void showActionDialog(final int id ,WebPedido webPedido, String mensagem, String tipoEnvio, int position) {
        String textConfirm = (id == R.id.lyEnvia || id == R.id.btnEnviar || id == R.id.menu_pedido_pendente) ? "ENVIAR AGORA" : "SIM";
        Integer iconConfirm = null;

        if (id == R.id.lyExcluir || id == R.id.btnExcluir) {
            iconConfirm = R.drawable.ic_delete_white_24dp;
        } else if (id == R.id.btnDuplic) {
            iconConfirm = R.drawable.ic_duplica_pedido;
        } else if (id == R.id.btnCompartilhar) {
            iconConfirm = R.drawable.ic_baseline_compartilhar_pedido_2025;
        } else if (id == R.id.lyEnvia || id == R.id.btnEnviar || id == R.id.menu_pedido_pendente) {
            iconConfirm = R.mipmap.ic_enviar_pedidos;
        } else if (id == R.id.btnCopiaSegura) {
            iconConfirm = R.drawable.ic_check_list_all;
        } else if (id == -600) {
            iconConfirm = R.drawable.ic_pdf_pedido;
        } else if (id == -700) {
            iconConfirm = R.drawable.ic_info_pedido_estoque;
        }

        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                textConfirm, iconConfirm,
                null, null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override public void onPositive() {}
                    @Override public void onNegative() {
                        if (id == R.id.lyExcluir || id == R.id.btnExcluir) {
                            // ... (resto do código)
                            if (webPedido.getExcluido().equalsIgnoreCase("S")) {
                                try {
                                    webPedido.setExcluido("N");
                                    if (webPedidoDAO.removeLixeira(webPedido)) {
                                        try {
                                            int idPed = Integer.parseInt(webPedido.getId_web_pedido());
                                            new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, webPedido.getId_pedido_app(), "N");
                                        } catch (Exception ignored) {}
                                        listaPedidoAdapter.remove(webPedido);
                                        showFeedbackDialog("O Pedido foi restaurado da lixeira!", true, false, null);
                                        getPedidos();
                                    } else {
                                        showFeedbackDialog("Falha ao restaurar o pedido " + webPedido.getId_web_pedido() + " de " + webPedido.getNome_extenso() + " da lixeira", false, false, R.drawable.ic_alerta_mensagem);
                                        getPedidos();
                                    }
                                } catch (Exception e) {
                                    getPedidos();
                                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Não foi possivel resraurar o pedido da lixeira! Tente novamente!", R.drawable.ic_alerta_mensagem);
                                }
                            } else {
                                try {
                                    webPedido.setExcluido("S");
                                    if (webPedidoDAO.addLixeira(webPedido)) {
                                        try {
                                            int idPed = Integer.parseInt(webPedido.getId_web_pedido());
                                            new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, webPedido.getId_pedido_app(), "S");
                                        } catch (Exception ignored) {}
                                        delPedidoFirebase(configuracao.getCnpj_empresa(), webPedido);
                                        listaPedidoAdapter.remove(webPedido);
                                        showFeedbackDialog("O Pedido foi enviado para a lixeira!", true, false, null);
                                        getPedidos();
                                    } else {
                                        showFeedbackDialog("Falha ao enviar o pedido " + webPedido.getId_web_pedido() + " de " + webPedido.getNome_extenso() + " para a lixeira", false, false, R.drawable.ic_alerta_mensagem);
                                        getPedidos();
                                    }
                                } catch (Exception e) {
                                    getPedidos();
                                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Não foi possivel enviar o(s) pedido(s) para a lixeira! Tente novamente!", R.drawable.ic_alerta_mensagem);
                                }
                            }
                        } else if ( id == R.id.action_pedido_analise) {
                            List<WebPedido> listaPedidos = new ArrayList<>();
                            listaPedidos.add(webPedido);
                            sincronizaEstoque(listaPedido);
                        } else if ( id == R.id.lyEnvia || id == R.id.btnEnviar || id == R.id.menu_pedido_pendente) {
                            webPedido.setTipo_envio("1");
                            try { webPedido.setId_android(Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID)); } catch (Exception e) { webPedido.setId_android("EXCEPTION"); }
                            List<WebPedido> listaPedidos = new ArrayList<>();
                            listaPedidos.add(webPedido);
                            if ( webPedido.getData_prev_entrega() == null || webPedido.getData_prev_entrega().isEmpty() || webPedido.getData_prev_entrega().equalsIgnoreCase("/  /")){
                                showFeedbackDialog("Data de entrega/faturamento inválida/não informada|Favor verificar antes de enviar o pedido!", true, false, R.drawable.ic_alerta_mensagem);
                                return;
                            }
                            
                            showProgressDialogCustom("Analisando integridade do pedido...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                if ( analisaPedido(listaPedidos)){
                                    dismissProgressDialogCustom();
                                    showOrderSummaryDialog(webPedido, listaPedidos);
                                } else {
                                    dismissProgressDialogCustom();
                                    showActionDialog(webPedido, "Foi encontrada divergencia no pedido referente aos itens gravados! Deseja cancelar o envio para analisar o pedido n: " + webPedido.getId_web_pedido() + " de " + webPedido.getNome_extenso() + "?");
                                }
                            }, 200);
                        } else if (id == R.id.btnDuplic) {
                            if (webPedido.getCadastro().getId_cadastro() == 0) {
                                showFeedbackDialog("Pré-pedido não pode ser duplicado!!", false, false, null);
                                return;
                            }

                            // QUESTÃO: VERIFICAÇÃO DE DÉBITO NA DUPLICAÇÃO
                            // Se o cliente tiver débitos, mostra o alerta antes de prosseguir com a duplicação
                            float vencido = 0.00f;
                            float vencendo = 0.00f;
                            try {
                                vencido = Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencido() == null ? "0.00" : webPedido.getCadastro().getFinanceiro_vencido());
                                vencendo = Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencendo() == null ? "0.00" : webPedido.getCadastro().getFinanceiro_vencendo());
                            } catch (Exception ignored) {}

                            if (vencido > 0 || vencendo > 0) {
                                DialogHelper.showDebtorAlertaDialog(ActivityPedidoPendente.this, vencido, vencendo, 
                                        "O cliente possui pendências financeiras registradas. Deseja prosseguir com a DUPLICAÇÃO do pedido mesmo assim?", () -> {
                                    // Se confirmar, duplica
                                    executarDuplicacao(webPedido);
                                });
                            } else {
                                executarDuplicacao(webPedido);
                            }

                        } else if (id == R.id.btnCompartilhar) {
                            showProgressDialogCustom("Preparando compartilhamento...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                indAcao = 3;
                                List<WebPedido> lista = new ArrayList<>();
                                lista.add(webPedido);
                                geraArquivoPDF( lista,indAcao);
                            }, 200);
                        } else if (id == -600) {
                            showProgressDialogCustom("Gerando arquivo PDF, aguarde...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                indAcao = 1;
                                List<WebPedido> lista = new ArrayList<>();
                                lista.add(webPedido);
                                geraArquivoPDF(lista, indAcao);
                            }, 200);
                        } else if (id == -700) {
                            showProgressDialogCustom("Analisando estoque...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                List<WebPedido> listaPedidos = new ArrayList<>();
                                listaPedidos.add(webPedido);
                                sincronizaEstoque(listaPedidos);
                            }, 200);
                        } else if(id == R.id.btnCopiaSegura) {
                            addPedidoFirebase(configuracao.getCnpj_empresa(), webPedido, position);
                        }
                    }
                    @Override public void onNeutral() {}
                },
                getPopulatedSummaryView(webPedido, (id == R.id.lyEnvia || id == R.id.btnEnviar || id == R.id.menu_pedido_pendente)) // NOVO: Mostra total apenas no envio
        );
    }
    public void showActionDialog(ActionMode mode, final int id ,List<WebPedido> pedidosSelecionados, String mensagem) {
        cliqueAtivo = false;
        
        // NOVO: Prepara a View de resumo se for apenas 1 pedido
        boolean isEnvio = (id == R.id.action_mode_menu_pedido_pendente || id == R.id.menu_pedido_pendente);
        View bodyView = (pedidosSelecionados.size() == 1) ? getPopulatedSummaryView(pedidosSelecionados.get(0), isEnvio) : null;

        String textConfirm = isEnvio ? "ENVIAR AGORA" : "SIM";
        Integer iconConfirm = null;

        if (id == R.id.action_delete) {
            iconConfirm = R.drawable.ic_delete_white_24dp;
        } else if (id == R.id.action_duplica_pedido) {
            iconConfirm = R.drawable.ic_duplica_pedido;
        } else if (id == R.id.action_pdf_pedido) {
            iconConfirm = R.drawable.ic_pdf_pedido;
        } else if (id == R.id.action_mode_menu_pedido_pendente || id == R.id.menu_pedido_pendente) {
            iconConfirm = R.mipmap.ic_enviar_pedidos_selecionados;
        } else if (id == R.id.action_pedido_analise) {
            iconConfirm = R.drawable.ic_info_pedido_estoque;
        }

        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                textConfirm, iconConfirm,
                null, null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override public void onPositive() { cliqueAtivo = true; }
                    @Override public void onNegative() {
                        if ( id == R.id.action_delete) {
                            // ... (resto do código)
                            try { if (mode != null) mode.finish(); } catch (NullPointerException e) { e.printStackTrace(); }
                            try {
                                int qtde = 0;
                                for (int i = 0; i < pedidosSelecionados.size(); i++) {
                                    WebPedido webPedidoL = pedidosSelecionados.get(i);
                                    webPedidoL.setExcluido("S");
                                    if (webPedidoDAO.addLixeira(webPedidoL)) {
                                        try {
                                            int idPed = Integer.parseInt(webPedidoL.getId_web_pedido());
                                            new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, webPedidoL.getId_pedido_app(), "S");
                                        } catch (Exception ignored) {}
                                        delPedidoFirebase(configuracao.getCnpj_empresa(), pedidosSelecionados.get(i));
                                        qtde++;
                                    } else {
                                        showFeedbackDialog("Falha ao enviar  o(s) pedido(s) selecionado(s) para a lixeira", false, false, null);
                                        getPedidos();
                                    }
                                }
                                if (pedidosSelecionados.size() == 1)
                                    showFeedbackDialog("O Pedido selecionado foi enviado para a lixeira!", true, false, null);
                                else
                                    showFeedbackDialog("Os Pedidos selecionados foram enviados para a lixeira!", true, false, null);
                                listaPedidoAdapter.remove(pedidosSelecionados.get(0));
                                getPedidos();
                                cliqueAtivo = true;
                            } catch (Exception e) {
                                cliqueAtivo = true;
                                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Não foi possivel enviar o(s) pedido(s) para a lixeira! Tente novamente!", R.drawable.ic_alerta_mensagem);
                            }
                        } else if (id == R.id.action_pedido_analise) {
                            List<WebPedido> lista = analisaPedidoPendentes(pedidosSelecionados);
                            sincronizaEstoque(lista);
                        } else if ( id == R.id.action_mode_menu_pedido_pendente || id == R.id.menu_pedido_pendente) {
                            try { if (mode != null) mode.finish(); } catch ( NullPointerException e) { e.printStackTrace(); }
                            
                            showProgressDialogCustom("Analisando integridade do pedido...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                List<WebPedido> lista = analisaPedidoPendentes(pedidosSelecionados);
                                if ( lista.size() > 0) {
                                    dismissProgressDialogCustom();
                                    String pedidos = "";
                                    for (int i = 0; i < lista.size(); i++) { pedidos += "Pedido n: " + lista.get(i).getId_web_pedido() + " de " + lista.get(i).getNome_extenso() + "\n"; }
                                    cliqueAtivo = true;
                                    showFeedbackDialog("Foram encontradas divergencias! Favor verificar o(s) itens gravados no(s) pedido(s) informado(s) na mensagem\n" + pedidos, true, false, R.drawable.ic_alerta_mensagem);
                                } else {
                                    for(WebPedido webPedido : pedidosSelecionados) {
                                        if ( webPedido.getData_prev_entrega() == null || webPedido.getData_prev_entrega().isEmpty() || webPedido.getData_prev_entrega().equalsIgnoreCase("/  /")){
                                            dismissProgressDialogCustom();
                                            showFeedbackDialog("Data de entrega/faturamento inválida/não informada  em um ou mais pedidos selecionados!Favor verificar antes de enviar os pedidos!", true, false, R.drawable.ic_alerta_mensagem);
                                            return;
                                        }
                                    }
                                    dismissProgressDialogCustom();
                                    if (pedidosSelecionados.size() == 1) {
                                        showOrderSummaryDialog(pedidosSelecionados.get(0), pedidosSelecionados);
                                    } else {
                                        sincronizaPedidos(pedidosSelecionados);
                                    }
                                }
                            }, 200);
                        } else if ( id == R.id.action_pdf_pedido) {
                            showProgressDialogCustom("Gerando arquivo PDF, aguarde...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                indAcao = 1;
                                geraArquivoPDF(pedidosSelecionados, indAcao);
                                cliqueAtivo = true;
                            }, 200);
                        } else if (id == R.id.action_duplica_pedido) {
                            // QUESTÃO: VERIFICAÇÃO DE DÉBITO NA DUPLICAÇÃO (ACTION MODE)
                            WebPedido pedidoParaDuplicar = pedidosSelecionados.get(0);
                            float vencido = 0.00f;
                            float vencendo = 0.00f;
                            try {
                                vencido = Float.parseFloat(pedidoParaDuplicar.getCadastro().getFinanceiro_vencido() == null ? "0.00" : pedidoParaDuplicar.getCadastro().getFinanceiro_vencido());
                                vencendo = Float.parseFloat(pedidoParaDuplicar.getCadastro().getFinanceiro_vencendo() == null ? "0.00" : pedidoParaDuplicar.getCadastro().getFinanceiro_vencendo());
                            } catch (Exception ignored) {}

                            if (vencido > 0 || vencendo > 0) {
                                DialogHelper.showDebtorAlertaDialog(ActivityPedidoPendente.this, vencido, vencendo, 
                                        "O cliente possui pendências financeiras registradas. Deseja prosseguir com a DUPLICAÇÃO do pedido mesmo assim?", () -> {
                                    executarDuplicacao(mode, pedidoParaDuplicar);
                                });
                            } else {
                                executarDuplicacao(mode, pedidoParaDuplicar);
                            }

                        } else if (id == R.id.action_select_all) {
                            toggleSelectionAll();
                            cliqueAtivo = true;
                        }
                    }
                    @Override public void onNeutral() {}
                },
                bodyView // NOVO: Adiciona o resumo visual se existir
        );
    }

    public void showActionDialog(WebPedido webPedido, String mensagem) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "CANCELAR", null,
                "CONTINUAR", null,
                null, null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override public void onPositive() {}
                    @Override public void onNegative() {
                        List<WebPedido> lista = new ArrayList<>();
                        lista.add(webPedido);
                        sincronizaPedidos(lista);
                    }
                    @Override public void onNeutral() {}
                }
        );
    }

    public void showActionDialog(String mensagem, String erro) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", null,
                null, null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override public void onPositive() {}
                    @Override public void onNegative() {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                    @Override public void onNeutral() {}
                }
        );
    }

    private View getPopulatedSummaryView(WebPedido pedido, boolean mostrarTotal) {
        LayoutInflater inflater = getLayoutInflater();
        View viewResumo = inflater.inflate(R.layout.layout_dialog_resumo_pedido, null);

        TextView tvPedidoNum = viewResumo.findViewById(R.id.tvResumoPedidoNum);
        TextView tvCliente = viewResumo.findViewById(R.id.tvResumoCliente);
        TextView tvVendedor = viewResumo.findViewById(R.id.tvResumoVendedor);
        TextView tvData = viewResumo.findViewById(R.id.tvResumoData);
        TextView tvItens = viewResumo.findViewById(R.id.tvResumoItens);
        TextView tvPrazo = viewResumo.findViewById(R.id.tvResumoPrazo);
        TextView tvOperacao = viewResumo.findViewById(R.id.tvResumoOperacao);
        TextView tvTotal = viewResumo.findViewById(R.id.tvResumoTotal);
        TextView tvLabelTotal = viewResumo.findViewById(R.id.tvLabelTotal);
        View lyTotal = viewResumo.findViewById(R.id.lyResumoTotal);

        if (tvPedidoNum != null) tvPedidoNum.setText("PEDIDO Nº " + MascaraUtil.numeroZeros(pedido.getId_web_pedido(), 5));
        if (tvCliente != null) {
            if (pedido.getCadastro() != null && pedido.getCadastro().getNome_cadastro() != null)
                tvCliente.setText(pedido.getCadastro().getNome_cadastro().trim());
            else
                tvCliente.setText(pedido.getNome_extenso() != null ? pedido.getNome_extenso().trim() : "Cliente não identificado");
        }
        if (tvVendedor != null) {
            String nomeVend = (pedido.getUsuario_lancamento_nome() != null && !pedido.getUsuario_lancamento_nome().isEmpty()) ? pedido.getUsuario_lancamento_nome() : (usuario != null ? usuario.getNome_usuario() : "");
            tvVendedor.setText("Vendedor: " + nomeVend.trim());
        }
        if (tvData != null) {
            try { tvData.setText(MascaraUtil.formataData(pedido.getData_emissao(), "/")); } catch (Exception e) { tvData.setText(pedido.getData_emissao()); }
        }
        if (tvItens != null) tvItens.setText(pedido.getQtde_itens());
        if (tvPrazo != null) {
            try { String idCond = pedido.getId_condicao_pagamento(); String prazo = (idCond != null && !idCond.equals("0")) ? db.consultaDados("SELECT NOME_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + idCond + ";", "NOME_CONDICAO") : "Não informado"; tvPrazo.setText(prazo != null && !prazo.isEmpty() ? prazo : "Não informado"); } catch (Exception e) { tvPrazo.setText("Não informado"); }
        }
        if (tvOperacao != null) {
            try { String idOp = pedido.getId_operacao(); String operacao = (idOp != null && !idOp.equals("0")) ? db.consultaDados("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + idOp + ";", "NOME_OPERACAO") : "Não informado"; tvOperacao.setText(operacao != null && !operacao.isEmpty() ? operacao : "Não informado"); } catch (Exception e) { tvOperacao.setText("Não informado"); }
        }

        if (tvLabelTotal != null) tvLabelTotal.setText("TOTAL DO PEDIDO");
        if (tvTotal != null) tvTotal.setText(MascaraUtil.mascaraRealSimbolo(pedido.getValor_total()));

        if (lyTotal != null) {
            lyTotal.setVisibility(mostrarTotal ? View.VISIBLE : View.GONE);
        }

        return viewResumo;
    }

    public void showOrderSummaryDialog(final WebPedido pedido, final List<WebPedido> listaParaEnvio) {
        try {
            if (isFinishing() || isDestroyed() || pedido == null) return;

            // QUESTÃO 2: VERIFICAÇÃO DE DÉBITO DO CLIENTE (MÁXIMO 1 PEDIDO SELECIONADO)
            // Se houver apenas um pedido sendo enviado, verificamos a saúde financeira do cliente
            if (listaParaEnvio != null && listaParaEnvio.size() == 1 && pedido.getCadastro() != null) {
                float vencido = 0.00f;
                float vencendo = 0.00f;
                try {
                    vencido = Float.parseFloat(pedido.getCadastro().getFinanceiro_vencido() == null ? "0.00" : pedido.getCadastro().getFinanceiro_vencido());
                    vencendo = Float.parseFloat(pedido.getCadastro().getFinanceiro_vencendo() == null ? "0.00" : pedido.getCadastro().getFinanceiro_vencendo());
                } catch (Exception ignored) {}

                if (vencido > 0 || vencendo > 0) {
                    DialogHelper.showDebtorAlertaDialog(ActivityPedidoPendente.this, vencido, vencendo, 
                            "O cliente possui pendências financeiras registradas. Deseja prosseguir com o ENVIO do pedido mesmo assim?", () -> {
                        // Se o vendedor confirmar no alerta de débito, aí sim mostramos o resumo do pedido
                        exibirResumoEnvioPedido(pedido, listaParaEnvio);
                    });
                    return;
                }
            }

            // Se não houver débito ou forem vários pedidos, segue o fluxo normal
            exibirResumoEnvioPedido(pedido, listaParaEnvio);

        } catch (Exception e) {
            e.printStackTrace();
            sincronizaPedidos(listaParaEnvio);
        }
    }

    /**
     * NOVO: Métodos auxiliares para isolar a lógica de duplicação com progress dialog.
     */
    private void executarDuplicacao(WebPedido webPedido) {
        showProgressDialogCustom("Duplicando pedido, aguarde...");
        new Handler(Looper.getMainLooper()).postDelayed(() -> duplicaPedido(webPedido), 200);
    }

    private void executarDuplicacao(ActionMode mode, WebPedido webPedido) {
        showProgressDialogCustom("Duplicando pedido, aguarde...");
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try { if (mode != null) mode.finish(); } catch (NullPointerException e) { e.printStackTrace(); }
            duplicaPedido(webPedido);
        }, 200);
    }

    /**
     * NOVO: Método auxiliar para isolar a exibição do diálogo de resumo de envio.
     */
    private void exibirResumoEnvioPedido(WebPedido pedido, List<WebPedido> listaParaEnvio) {
        View viewResumo = getPopulatedSummaryView(pedido, true);
        DialogHelper.showActionDialog(
                ActivityPedidoPendente.this,
                null,
                "CANCELAR", null,
                "ENVIAR AGORA", R.mipmap.ic_enviar_pedidos,
                null, null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override public void onPositive() {}
                    @Override public void onNegative() { sincronizaPedidos(listaParaEnvio); }
                    @Override public void onNeutral() {}
                },
                viewResumo
        );
    }

    public void exibirDialogAcoesPedido(final int position) {
        if (isFinishing() || isDestroyed()) return;
        LayoutInflater inflater = getLayoutInflater();
        View headerView = inflater.inflate(R.layout.layout_dialog_menu_pedido, null);
        WebPedido pedido = listaPedidoAdapter.getItem(position);
        
        ImageView ivIcon = headerView.findViewById(R.id.ivMenuPedidoIcon);
        if (ivIcon != null) { ivIcon.setColorFilter(androidx.core.content.ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN); }

        // Popula o resumo de identidade do pedido
        TextView tvPedidoNum = headerView.findViewById(R.id.tvIdentidadePedidoNum);
        TextView tvCliente = headerView.findViewById(R.id.tvIdentidadePedidoCliente);
        TextView tvDetalhes = headerView.findViewById(R.id.tvIdentidadePedidoDetalhes);

        if (tvPedidoNum != null) tvPedidoNum.setText("PEDIDO Nº " + MascaraUtil.numeroZeros(pedido.getId_web_pedido(), 5));
        if (tvCliente != null) tvCliente.setText(pedido.getCadastro() != null ? pedido.getCadastro().getNome_cadastro().trim() : (pedido.getNome_extenso() != null ? pedido.getNome_extenso().trim() : ""));
        if (tvDetalhes != null) {
            String data = "";
            try { data = MascaraUtil.formataData(pedido.getData_emissao(), "/"); } catch (Exception e) { data = pedido.getData_emissao(); }
            String valor = MascaraUtil.mascaraRealSimbolo(pedido.getValor_total());
            tvDetalhes.setText(data + "  |  " + valor);
        }

        // NOVO: Determina se o pedido está abandonado
        boolean isAbandonado = (pedido.getFinalizado() != null && pedido.getFinalizado().equalsIgnoreCase("N"));

        final String[] opcoes;
        if (isAbandonado) {
            opcoes = new String[]{"EDITAR PEDIDO", "EXCLUIR PEDIDO", "FECHAR"};
        } else {
            opcoes = new String[]{"EDITAR PEDIDO", "ENVIAR PEDIDO", "DUPLICAR PEDIDO", "COMPARTILHAR PEDIDO", "GERAR PDF", "ANÁLISE DE ESTOQUE", "CRIAR CÓPIA DE SEGURANÇA", "EXCLUIR PEDIDO", "FECHAR"};
        }

        new com.google.android.material.dialog.MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded)
                .setCustomTitle(headerView)
                .setItems(opcoes, (dialog, which) -> {
                    if (isAbandonado) {
                        switch (which) {
                            case 0: // Editar
                                showProgressDialogCustom("Preparando edição do pedido...");
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    new PedidoHelper().limparDados();

                                    // 2. Garante que o cliente selecionado esteja ativo no Helper
                                    ClienteHelper.setCliente(null);
                                    try {
                                        if (listaPedidoAdapter != null) {
                                            listaPedidoAdapter.clearSelections();
                                        }
                                        actionMode = null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    PedidoHelper.setIdPedido(Integer.parseInt(pedido.getId_web_pedido()));
                                    startActivity(new Intent(ActivityPedidoPendente.this, ActivityPedidoMain.class));
                                }, 200);
                                break;
                            case 1: // Excluir
                                showActionDialog(R.id.btnExcluir, pedido, "Deseja realmente excluir este pedido abandonado?", null, position);
                                break;
                            case 2: // Fechar
                                dialog.dismiss();
                                break;
                        }
                    } else {
                        switch (which) {
                            case 0: // Editar
                                showProgressDialogCustom("Preparando edição do pedido...");
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    new PedidoHelper().limparDados();

                                    // 2. Garante que o cliente selecionado esteja ativo no Helper
                                    ClienteHelper.setCliente(null);
                                    try {
                                        if (listaPedidoAdapter != null) {
                                            listaPedidoAdapter.clearSelections();
                                        }
                                        actionMode = null;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    PedidoHelper.setIdPedido(Integer.parseInt(pedido.getId_web_pedido()));
                                    startActivity(new Intent(ActivityPedidoPendente.this, ActivityPedidoMain.class));
                                }, 200);
                                break;
                            case 1: // Enviar
                                showProgressDialogCustom("Analisando integridade do pedido...");
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    List<WebPedido> listaEnvia = java.util.Collections.singletonList(pedido);
                                    if (analisaPedido(listaEnvia)) {
                                        dismissProgressDialogCustom();
                                        showOrderSummaryDialog(pedido, listaEnvia);
                                    } else {
                                        dismissProgressDialogCustom();
                                        showActionDialog(pedido, "Foi encontrada divergencia no pedido referente aos itens gravados! Deseja cancelar o envio para analisar o pedido n: " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + "?");
                                    }
                                }, 200);
                                break;
                            case 2: // Duplicar
                                showActionDialog(R.id.btnDuplic, pedido, "Deseja duplicar o pedido selecionado?", null, position);
                                break;
                            case 3: // Compartilhar
                                showActionDialog(R.id.btnCompartilhar, pedido, "Deseja criar um arquivo PDF do pedido para ser compartilhado?", null, position);
                                break;
                            case 4: // Gerar PDF
                                showActionDialog(-600, pedido, "Deseja gerar o arquivo PDF deste pedido?", null, position);
                                break;
                            case 5: // Análise estoque
                                showActionDialog(-700, pedido, "Deseja realizar a análise de estoque deste pedido?", null, position);
                                break;
                            case 6: // Cópia de Segurança
                                showActionDialog(R.id.btnCopiaSegura, pedido, "Deseja criar uma cópia de segurança deste pedido?", null, position);
                                break;
                            case 7: // Excluir
                                showActionDialog(R.id.btnExcluir, pedido, "Deseja realmente enviar o pedido para a lixeira?", null, position);
                                break;
                            case 8: // Fechar
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .show();
    }

    private void syncLogsToFirestore(WebPedido pedido) {
        try {
            LogSistemaDAO logDAO = new LogSistemaDAO(db);
            // Busca TODOS os logs pendentes do dispositivo (incluindo crashes antigos)
            List<LogSistema> logs = logDAO.getAllUnsentLogs();

            if (logs.isEmpty()) return;

            String cnpj = configuracao.getCnpj_empresa().trim();
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            for (LogSistema log : logs) {
                if ("S".equalsIgnoreCase(log.getStEnviado())) continue;

                Map<String, Object> logMap = new HashMap<>();
                for (Field field : log.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        logMap.put(field.getName(), field.get(log));
                    } catch (Exception ignored) {}
                }

                String idVendedor = log.getIdVendedor() != null ? log.getIdVendedor() : (pedido.getId_vendedor() != null ? pedido.getId_vendedor() : "SEM_VENDEDOR");

                if ("CRASH".equalsIgnoreCase(log.getTipoLog()) || "ERRO".equalsIgnoreCase(log.getTipoLog())) {
                    // LOG TÉCNICO: Centralizado para fácil localização do suporte
                    firestore.collection(cnpj)
                            .document("TBL_LOG_ERROS_TECNICOS")
                            .collection(idVendedor)
                            .document(log.getIdLog() + "_" + (log.getDataHora() != null ? log.getDataHora().replaceAll("[^0-9]", "") : ""))
                            .set(logMap)
                            .addOnSuccessListener(aVoid -> logDAO.markAsSent(log.getIdLog()));
                } else {
                    // LOG DE AUDITORIA: Vinculado ao pedido se houver ID de servidor
                    String idPedidoServidor = log.getIdWebPedidoServidor();
                    // Se o log for deste pedido que acabou de sincronizar, o ID pode vir do objeto pedido
                    if ((idPedidoServidor == null || idPedidoServidor.isEmpty() || idPedidoServidor.equals("0")) && log.getIdPedido().equals(pedido.getId_web_pedido())) {
                        idPedidoServidor = pedido.getId_web_pedido_servidor();
                    }

                    if (idPedidoServidor != null && !idPedidoServidor.isEmpty() && !idPedidoServidor.equals("0")) {
                        String idSrvPadded = MascaraUtil.numeroZeros(idPedidoServidor, 8);
                        firestore.collection(cnpj)
                                .document("TBL_LOG_SISTEMA")
                                .collection("PEDIDOS")
                                .document(idSrvPadded)
                                .collection("EVENTOS")
                                .document(String.valueOf(log.getIdLog()))
                                .set(logMap)
                                .addOnSuccessListener(aVoid -> logDAO.markAsSent(log.getIdLog()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPostSyncFeedback(final WebPedido pedidoSincronizado) {
        try {
            if (isFinishing() || isDestroyed() || pedidoSincronizado == null || usuario == null) return;
            if (progress != null && progress.isShowing()) progress.dismiss();
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_dialog_feedback_carousel, null);
            final AlertDialog dialog = new AlertDialog.Builder(ActivityPedidoPendente.this).create();
            dialog.setView(dialogView);
            if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            androidx.viewpager2.widget.ViewPager2 vpFeedback = dialogView.findViewById(R.id.vpFeedback);
            Button btnFechar = dialogView.findViewById(R.id.btnFecharFeedback);
            Calendar cal = Calendar.getInstance();
            final int anoAtual = cal.get(Calendar.YEAR);
            final int mesAtual = cal.get(Calendar.MONTH) + 1;
            final int semanaAtual = cal.get(Calendar.WEEK_OF_MONTH);
            final String idVendedor = usuario.getId_quando_vendedor();
            final int idUsuario = Integer.parseInt(usuario.getId_usuario());
            final String msgSucessoPadrao = "O pedido do cliente " + pedidoSincronizado.getCadastro().getNome_cadastro() + " no valor de " + MascaraUtil.mascaraReal(pedidoSincronizado.getValor_total()) + " foi sincronizado com sucesso!!";

            vpFeedback.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    int layoutId;
                    switch (viewType) {
                        case 0: layoutId = R.layout.layout_card_feedback_meta; break;
                        case 1: layoutId = R.layout.layout_card_feedback_meta_mensal; break;
                        case 2: layoutId = R.layout.layout_card_feedback_ranking; break;
                        case 3: layoutId = R.layout.layout_card_feedback_cliente_share; break;
                        default: layoutId = R.layout.layout_card_feedback_meta; break;
                    }
                    View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                    return new RecyclerView.ViewHolder(view) {};
                }
                @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressWarnings("RecyclerView") int position) {
                    int adapterPos = holder.getAdapterPosition();
                    if (adapterPos == 0) {
                        final TextView tvMetaTotal = holder.itemView.findViewById(R.id.tvMetaTotal);
                        final TextView tvValorAtual = holder.itemView.findViewById(R.id.tvValorAtual);
                        final TextView tvQuantoFalta = holder.itemView.findViewById(R.id.tvQuantoFalta);
                        final ProgressBar pbMeta = holder.itemView.findViewById(R.id.pbMeta);
                        final TextView tvPorcentagem = holder.itemView.findViewById(R.id.tvPorcentagem);
                        final View celeb = holder.itemView.findViewById(R.id.lytCelebracaoSemanal);
                        TextView tvTituloCard = holder.itemView.findViewById(R.id.tvTituloCard);
                        if (tvTituloCard != null) tvTituloCard.setText("META SEMANA " + semanaAtual);
                        if (tvMetaTotal != null) tvMetaTotal.setText("R$ 0,00");
                        if (tvValorAtual != null) tvValorAtual.setText("R$ 0,00");
                        if (tvQuantoFalta != null) tvQuantoFalta.setText("R$ 0,00");
                        if (pbMeta != null) pbMeta.setProgress(0);
                        if (tvPorcentagem != null) tvPorcentagem.setText("Buscando meta...");
                        if (celeb != null) celeb.setVisibility(View.GONE);

                        new FirestoreConexao().getMetaSemanal(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), idVendedor, String.valueOf(mesAtual), new FirestoreConexao.OnMetaSemanalRetorno() {
                            @Override public void onSucesso(MetaSemanal m) {
                                if (m != null && tvMetaTotal != null) {
                                    final double vM = getMetaPorSemana(m, semanaAtual);
                                    tvMetaTotal.setText(MascaraUtil.mascaraRealSimboloD(vM));
                                    Map<String, String> cabecalho = new HashMap<>();
                                    cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
                                    Api.buildRetrofit(false).getSomaTotalRealizadoSemanaPeriodo(idUsuario, getPrimeiroDiaMesAtual(), cabecalho).enqueue(new Callback<List<MesSemana>>() {
                                        @Override public void onResponse(Call<List<MesSemana>> call, Response<List<MesSemana>> response) {
                                            double vR = 0;
                                            if (response.isSuccessful() && response.body() != null) {
                                                for (MesSemana ms : response.body()) {
                                                    String semApi = ms.getNome_semana() != null ? ms.getNome_semana().replaceAll("[^0-9]", "") : "";
                                                    if (String.valueOf(semanaAtual).equals(semApi)) { vR = cleanMoney(ms.getVenda_liquida()); break; }
                                                }
                                            }
                                            vR += cleanMoney(pedidoSincronizado.getValor_total());
                                            tvValorAtual.setText(MascaraUtil.mascaraRealSimboloD(vR));
                                            double vFalta = Math.max(0, vM - vR);
                                            if (tvQuantoFalta != null) tvQuantoFalta.setText(MascaraUtil.mascaraRealSimboloD(vFalta));
                                            if (vM > 0) {
                                                int perc = (int) ((vR / vM) * 100);
                                                pbMeta.setProgress(Math.min(perc, 100));
                                                tvPorcentagem.setText(perc + "% da meta semanal alcançada");
                                                if (perc >= 100 && celeb != null) { celeb.setVisibility(View.VISIBLE); celeb.setScaleX(0.5f); celeb.setScaleY(0.5f); celeb.animate().scaleX(1f).scaleY(1f).setDuration(500).start(); }
                                            } else { if (tvPorcentagem != null) tvPorcentagem.setText("Meta não definida"); }
                                        }
                                        @Override public void onFailure(Call<List<MesSemana>> call, Throwable t) {
                                            tvValorAtual.setText(MascaraUtil.mascaraRealSimboloD(0.0));
                                            if (tvQuantoFalta != null) tvQuantoFalta.setText(MascaraUtil.mascaraRealSimboloD(vM));
                                            if (tvPorcentagem != null) tvPorcentagem.setText("Erro ao buscar realizado");
                                        }
                                    });
                                } else { if (tvPorcentagem != null) tvPorcentagem.setText("Meta não encontrada"); }
                            }
                            @Override public void onFalha(Exception e) { if (tvPorcentagem != null) tvPorcentagem.setText("Falha na conexão"); }
                        });
                    } else if (position == 1) {
                        final TextView tvMetaTotalMensal = holder.itemView.findViewById(R.id.tvMetaTotalMensal);
                        final TextView tvValorAtualMensal = holder.itemView.findViewById(R.id.tvValorAtualMensal);
                        final TextView tvQuantoFaltaMensal = holder.itemView.findViewById(R.id.tvQuantoFaltaMensal);
                        final ProgressBar pbMetaMensal = holder.itemView.findViewById(R.id.pbMetaMensal);
                        final TextView tvPorcentagemMensal = holder.itemView.findViewById(R.id.tvPorcentagemMensal);
                        final View celebMensal = holder.itemView.findViewById(R.id.lytCelebracaoMensal);
                        if (tvMetaTotalMensal != null) tvMetaTotalMensal.setText("R$ 0,00");
                        if (tvValorAtualMensal != null) tvValorAtualMensal.setText("R$ 0,00");
                        if (tvQuantoFaltaMensal != null) tvQuantoFaltaMensal.setText("R$ 0,00");
                        if (pbMetaMensal != null) pbMetaMensal.setProgress(0);
                        if (tvPorcentagemMensal != null) tvPorcentagemMensal.setText("Buscando meta...");
                        if (celebMensal != null) celebMensal.setVisibility(View.GONE);

                        new FirestoreConexao().getMetaMesAno(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), idVendedor, new FirestoreConexao.OnMetaRetorno() {
                            @Override public void onSucesso(MesAno m) {
                                if (m != null && tvMetaTotalMensal != null) {
                                    final double vM = getMetaDoMes(m, mesAtual);
                                    tvMetaTotalMensal.setText(MascaraUtil.mascaraRealSimboloD(vM));
                                    Map<String, String> cabecalho = new HashMap<>();
                                    cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
                                    Api.buildRetrofit(false).getSomaTotalRealizadoMesPeriodo(idUsuario, getPrimeiroDiaMesAtual(), "4", cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
                                        @Override public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                                            double vR = 0;
                                            if (response.isSuccessful() && response.body() != null) {
                                                String[] mesesNomes = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
                                                String nomeMesAtual = mesesNomes[mesAtual - 1];
                                                for (MesAnoMeta ma : response.body()) { if (ma.getNome_mes() != null && (ma.getNome_mes().equalsIgnoreCase(nomeMesAtual) || ma.getNome_mes().toUpperCase().contains(nomeMesAtual.substring(0, 3)))) { vR = cleanMoney(ma.getVenda_liquida()); break; } }
                                            }
                                            vR += cleanMoney(pedidoSincronizado.getValor_total());
                                            tvValorAtualMensal.setText(MascaraUtil.mascaraRealSimboloD(vR));
                                            double vFalta = Math.max(0, vM - vR);
                                            if (tvQuantoFaltaMensal != null) tvQuantoFaltaMensal.setText(MascaraUtil.mascaraRealSimboloD(vFalta));
                                            if (vM > 0) {
                                                int perc = (int) ((vR / vM) * 100);
                                                pbMetaMensal.setProgress(Math.min(perc, 100));
                                                tvPorcentagemMensal.setText(perc + "% do objetivo mensal concluído");
                                                if (perc >= 100 && celebMensal != null) { celebMensal.setVisibility(View.VISIBLE); celebMensal.setScaleX(0.5f); celebMensal.setScaleY(0.5f); celebAnimate(celebMensal); }
                                            } else { if (tvPorcentagemMensal != null) tvPorcentagemMensal.setText("Meta não definida"); }
                                        }
                                        @Override public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) {
                                            tvValorAtualMensal.setText(MascaraUtil.mascaraRealSimboloD(0.0));
                                            if (tvQuantoFaltaMensal != null) tvQuantoFaltaMensal.setText(MascaraUtil.mascaraRealSimboloD(vM));
                                            if (tvPorcentagemMensal != null) tvPorcentagemMensal.setText("Erro ao buscar realizado");
                                        }
                                    });
                                } else { if (tvPorcentagemMensal != null) tvPorcentagemMensal.setText("Meta não encontrada"); }
                            }
                            @Override public void onFalha(Exception e) { if (tvPorcentagemMensal != null) tvPorcentagemMensal.setText("Falha na conexão"); }
                        });
                    } else if (position == 2) {
                        final TextView tvPos = holder.itemView.findViewById(R.id.tvPosicaoRanking);
                        final TextView tvEvol = holder.itemView.findViewById(R.id.tvRankingEvolucao);
                        final TextView tvRankingSubtexto = holder.itemView.findViewById(R.id.tvRankingSubtexto);
                        if (tvPos != null) tvPos.setText("-");
                        if (tvEvol != null) tvEvol.setText("Buscando ranking...");
                        if (tvRankingSubtexto != null) tvRankingSubtexto.setText("Analisando equipe...");
                        final Map<String, String> cabecalho = new HashMap<>();
                        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
                        Api.buildRetrofit(false).getSomaTotalRealizadoMesPeriodo(999, getPrimeiroDiaMesAtual(), "4", cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
                            @Override public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    List<MesAnoMeta> rankingData = response.body();
                                    String[] mesesNomes = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
                                    String nomeMesAtual = mesesNomes[mesAtual - 1];
                                    class ItemRank { int idUsuario; double valor; ItemRank(int id, double v) { this.idUsuario = id; this.valor = v; } }
                                    List<ItemRank> listaRanking = new ArrayList<>();
                                    boolean vendedorEncontrado = false;
                                    for (MesAnoMeta ma : rankingData) {
                                        if (ma.getNome_mes() != null && (ma.getNome_mes().equalsIgnoreCase(nomeMesAtual) || ma.getNome_mes().toUpperCase().contains(nomeMesAtual.substring(0, 3)))) {
                                            double valor = cleanMoney(ma.getVenda_liquida());
                                            int idVend = 0; try { idVend = Integer.parseInt(ma.getId_vendedor()); } catch (Exception e) {}
                                            if (idVend == idUsuario) { valor += cleanMoney(pedidoSincronizado.getValor_total()); vendedorEncontrado = true; }
                                            listaRanking.add(new ItemRank(idVend, valor));
                                        }
                                    }
                                    if (!vendedorEncontrado) listaRanking.add(new ItemRank(idUsuario, cleanMoney(pedidoSincronizado.getValor_total())));
                                    Collections.sort(listaRanking, (o1, o2) -> Double.compare(o2.valor, o1.valor));
                                    int posicao = 0; for (int i = 0; i < listaRanking.size(); i++) { if (listaRanking.get(i).idUsuario == idUsuario) { posicao = i + 1; break; } }
                                    if (posicao > 0) {
                                        if (tvPos != null) tvPos.setText(posicao + "º LUGAR");
                                        if (tvRankingSubtexto != null) tvRankingSubtexto.setText("Entre " + listaRanking.size() + " representantes");
                                        if (tvEvol != null) {
                                            if (posicao == 1) tvEvol.setText("Você está no topo da equipe! 🚀");
                                            else if (posicao <= 3) tvEvol.setText("Parabéns, você está no TOP 3!");
                                            else tvEvol.setText("Continue assim para subir no ranking!");
                                        }
                                    } else { if (tvEvol != null) tvEvol.setText("Sem dados de ranking."); }
                                }
                            }
                            @Override public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) { if (tvEvol != null) tvEvol.setText("Falha ao carregar ranking."); }
                        });
                    } else if (position == 3) {
                        final TextView tvNome = holder.itemView.findViewById(R.id.tvNomeClienteShare);
                        final TextView tvValorCli = holder.itemView.findViewById(R.id.tvValorTotalCliente);
                        final TextView tvPercShare = holder.itemView.findViewById(R.id.tvSharePorcentagem);
                        final ProgressBar pbShare = holder.itemView.findViewById(R.id.pbShareCliente);
                        if (tvNome != null) tvNome.setText(pedidoSincronizado.getCadastro().getNome_cadastro());
                        if (tvValorCli != null) tvValorCli.setText("Buscando dados...");
                        if (tvPercShare != null) tvPercShare.setText("0%");
                        if (pbShare != null) pbShare.setProgress(0);
                        final Map<String, String> cabecalho = new HashMap<>();
                        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
                        final int idCliente = pedidoSincronizado.getCadastro().getId_cadastro_servidor();
                        final double valorPedidoAtual = cleanMoney(pedidoSincronizado.getValor_total());
                        Api.buildRetrofit(false).getSomaTotalVendaPorCliente(idUsuario, idCliente, getPrimeiroDiaMesAtual(), getUltimoDiaMesAtual(), cabecalho).enqueue(new Callback<List<ParticipacaoFaturamento>>() {
                            @Override public void onResponse(Call<List<ParticipacaoFaturamento>> call, Response<List<ParticipacaoFaturamento>> response) {
                                if (response.isSuccessful() && response.body() != null && response.body().size() >= 3) {
                                    double vRClienteMes = cleanMoney(response.body().get(0).getValor_liquido());
                                    double vRVendedor6Meses = cleanMoney(response.body().get(1).getValor_liquido());
                                    double vRCliente6Meses = cleanMoney(response.body().get(2).getValor_liquido());
                                    if (tvValorCli != null) tvValorCli.setText("Total no mês: " + MascaraUtil.mascaraRealSimboloD(vRClienteMes + valorPedidoAtual));
                                    double totalVendedor6 = vRVendedor6Meses + valorPedidoAtual;
                                    if (totalVendedor6 > 0) {
                                        double share = ((vRCliente6Meses + valorPedidoAtual) / totalVendedor6) * 100;
                                        if (tvPercShare != null) tvPercShare.setText(String.format(Locale.US, "%.1f%%", share));
                                        if (pbShare != null) pbShare.setProgress((int) share);
                                    }
                                }
                            }
                            @Override public void onFailure(Call<List<ParticipacaoFaturamento>> call, Throwable t) {}
                        });
                    }
                }
                @Override public int getItemCount() { return 4; }
                @Override public int getItemViewType(int position) { return position; }
            });
            btnFechar.setOnClickListener(v -> { dialog.dismiss(); getPedidos(); });
            dialog.show();
        } catch (Exception e) { e.printStackTrace(); showFeedbackDialog("Pedido enviado com sucesso!", true, false, null); }
    }

    private void celebAnimate(View v) {
        v.setVisibility(View.VISIBLE); v.setScaleX(0.5f); v.setScaleY(0.5f);
        v.animate().scaleX(1f).scaleY(1f).setDuration(500).start();
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, boolean fimActivity, Integer iconId) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(this, sucesso ? "Sucesso" : "Atenção", mensagem, sucesso, iconId, null, () -> { if (fimActivity) finish(); else System.gc(); });
    }

    public void showFeedbackDialog(String mensagem, List<WebPedido> listaPedido) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(this, "Sucesso", mensagem, true, null, null, () -> {
            for (WebPedido pedido : listaPedido) { delPedidoFirebase(configuracao.getCnpj_empresa(), pedido); }
            getPedidos();
        });
    }

    private double cleanMoney(String valor) {
        if (valor == null || valor.isEmpty()) return 0;
        try {
            String v = valor.trim();
            if (v.contains(",")) return Double.parseDouble(v.replace(".", "").replace(",", "."));
            if (v.contains(".") && v.indexOf(".") == v.lastIndexOf(".")) {
                String dec = v.substring(v.lastIndexOf(".") + 1);
                if (dec.equals("0") || dec.equals("00")) return Double.parseDouble(v.split("\\.")[0]) / 100.0;
                return Double.parseDouble(v);
            }
            return Double.parseDouble(v.replace(".", "")) / 100.0;
        } catch (Exception e) { return 0; }
    }

    private double getMetaDoMes(MesAno m, int mesIdx) {
        if (m == null) return 0;
        String val = "";
        switch (mesIdx) {
            case 1: val = m.getJan(); break; case 2: val = m.getFev(); break; case 3: val = m.getMar(); break;
            case 4: val = m.getAbr(); break; case 5: val = m.getMai(); break; case 6: val = m.getJun(); break;
            case 7: val = m.getJul(); break; case 8: val = m.getAgo(); break; case 9: val = m.getSet(); break;
            case 10: val = m.getOut(); break; case 11: val = m.getNov(); break; case 12: val = m.getDez(); break;
        }
        return cleanMoney(val);
    }

    private double getMetaPorSemana(MetaSemanal m, int sem) {
        if (m == null) return 0;
        switch (sem) {
            case 1: return cleanMoney(m.getSemana1()); case 2: return cleanMoney(m.getSemana2());
            case 3: return cleanMoney(m.getSemana3()); case 4: return cleanMoney(m.getSemana4());
            case 5: return cleanMoney(m.getSemana5()); case 6: return cleanMoney(m.getSemana6());
            default: return 0;
        }
    }

    private String getUltimoDiaMesAtual() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return String.format(java.util.Locale.US, "%04d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    }

    private String getPrimeiroDiaMesAtual() {
        Calendar c = Calendar.getInstance();
        return String.format(java.util.Locale.US, "%04d-%02d-01", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }
}
