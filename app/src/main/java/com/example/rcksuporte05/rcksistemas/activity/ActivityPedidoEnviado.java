package com.example.rcksuporte05.rcksistemas.activity;

import static com.example.rcksuporte05.rcksistemas.api.Api.context;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.StrictMode;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.SegmentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VendedorBonusResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoAnalise;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoICMSST;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoUtil2026;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPedidoEnviado extends AppCompatActivity implements ListaPedidoAdapter.PedidoAdapterListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.listaPedidoEnviados)
    RecyclerView recyclerView;
   // @BindView(R.id.edtNumeroPedidoEnviado)
   // TextView edtTotalPedidos;
   // @BindView(R.id.txvTotalPedidoDia)
    //TextView txvTotalPedidoDia;

    //@BindView(R.id.txvTotalTitulo)
    //TextView txvTotalTitulo;

    //@BindView(R.id.img_view_retornar)
    //ImageView img_view_retornar;

    //@BindView(R.id.img_view_avancar)
    //ImageView img_view_avancar;

    //@BindView(R.id.toolbarPedidoEnviado)
    //Toolbar toolbar;
    //@BindView(R.id.swipe_container)
    //SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.buscaPedido)
    SearchView buscapedido;
    @OnClick(R.id.btnNovoPedido)
    public void btnNovoPedido() {
        Toast.makeText(context, "Opção em desenvolvimento!", Toast.LENGTH_SHORT).show();
        return;
        /*
        try {
            listaPedidoAdapter.clearSelections();
            actionMode = null;
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        indItem = 0;
        startActivity(new Intent(this, ActivityPedidoMain.class));*/

    }
    private EditText edtTotalPedidos;
    private TextView txvTotalTitulo;
    private TextView txvTotalPedidoDia;

    private TextView txvTotalFaturado, txvTotalCancelado, txvTotalOutros;

    private ImageView img_view_retornar;
    private ImageView img_view_avancar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar, toolbar2;

    private LinearLayout lytTotalPedidoDia;
    private int indItem;
    @BindView((R.id.helpPedido))
    ImageView helpPedido;
    ActionModeCallback actionModeCallback;
    private List<WebPedido> listaPedido;

    private DBHelper db = new DBHelper(this);
    private WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private Configuracao configuracao = new Configuracao();
    private Usuario usuario;
    private ListaPedidoAdapter listaPedidoAdapter;
    private ActionMode actionMode;
    Utilitaria util = new Utilitaria();
    private String SQL;
    PackageInfo pInfo = null;
    private ProgressDialog progress;
    // NOVOS CAMPOS PARA DIÁLOGO CUSTOMIZADO
    private androidx.appcompat.app.AlertDialog progressDialogCustom;
    private TextView tvProgressMessage;

    private Context context;

    private int offSet = 0;
    private int totalReg = 0;
    private int limitReg = 50;
    private int indSel = -1;
    private int indAcao = 0; //1 cria e mostra pdf | 2 cria e envia por email | 3 Compartilha

    private int indData = 0;

    private String dataAtiva;

    private String dataIni, dataFin;

    private TextView txvDiaInicial,txvDiaFinal, txvAnoInicial,txvAnoFinal,txvMesInicial,txvMesFinal;
    private  Spinner spnColaborador;

    private ArrayAdapter<Usuario> colaboradorAdapter;
    List<Usuario> listaUsuario; //Colaborador
    private boolean cliqueAtivo = true;
    private boolean isSincroniaInicial = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_enviado_drawer);
        getConfiguracao();

        try {
            if (configuracao.getVisualiza_legenda_pedido() == null || configuracao.getVisualiza_legenda_pedido().equalsIgnoreCase("S")) {
                startActivity(new Intent(ActivityPedidoEnviado.this, ActivityDialogPedido.class));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (configuracao.getGira_tela_ped_enviado().isEmpty() || configuracao.getGira_tela_ped_enviado().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        ButterKnife.bind(this);
        context = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        webPedidoDAO = new WebPedidoDAO(db);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        toolbar = findViewById(R.id.toolbarPedidoEnviado);
        toolbar.setTitle("Pedidos Enviados");
        //setSupportActionBar(toolbar);


        buscapedido.setOnQueryTextListener(new ActivityPedidoEnviado.QueryTextListener());
        buscapedido.setIconifiedByDefault(true);
        indItem = 0;

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ActivityPedidoEnviado.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        addMenuDrawerItem();

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(db);
            usuario = usuarioDAO.getLista("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consultaDados("SELECT * FROM TBL_LOGIN;", "LOGIN") + "';").get(0);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            util.showMsgAlerta("Usuario logado não localizado! Por gentileza refaça seu login!", ActivityPedidoEnviado.this);
            return;
        } catch (Exception e) {
            util.showMsgAlerta("Usuario logado não localizado! Por gentileza refaça seu login!!", ActivityPedidoEnviado.this);
            return;
        }
        if ( configuracao.getPedido_enviado_on_line_app().equalsIgnoreCase("N")) {
            toolbar2              = findViewById(R.id.toolbar2);
            lytTotalPedidoDia     = findViewById(R.id.lytTotalPedidoDia);
            showHideLytTotalPedidoDia();

        }
        edtTotalPedidos   = findViewById(R.id.edtTotalPedidos);
        txvTotalTitulo    = findViewById(R.id.txvTotalTitulo);
        txvTotalPedidoDia = findViewById(R.id.txvTotalPedidoDia);
        txvTotalFaturado  = findViewById(R.id.txvTotalFaturado);
        txvTotalCancelado = findViewById(R.id.txvTotalCancelado);
        txvTotalOutros    = findViewById(R.id.txvTotalOutros);

        img_view_retornar  = findViewById(R.id.img_view_retornar);
        img_view_avancar   = findViewById(R.id.img_view_avancar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ActivityPedidoEnviado.ScrollListener());
        //mSwipeRefreshLayout.setOnRefreshListener(new ActivityPedidoEnviado.RefreshListener());
        //if ( configuracao.getPedido_enviado_on_line_app().equalsIgnoreCase("S"))
           isSincroniaInicial = true;
           analisaPedidos();
        //else
          //analisaPedidos();
        //progress = new ProgressDialog(ActivityPedidoEnviado.this);
        //progress.setMessage("Efetuando pesquisa dos pedidos! Aguarde...");
        //progress.setTitle("Atenção!");
        //progress.setCancelable(false);
        //progress.show();
        dataAtiva = db.pegaDataAtual();
        // showDados();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionModeCallback = new ActivityPedidoEnviado.ActionModeCallback();
        helpPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityPedidoEnviado.this, ActivityDialogPedido.class));
            }
        });
        img_view_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( indData == 0) {
                    dataAtiva = db.pegaDataAtual("SELECT date('now','localtime', '" + indData +" day')");
                    showDados(dataAtiva, null, null);
                    return;
                }
                indData++;
                dataAtiva = db.pegaDataAtual("SELECT date('now','localtime', '" + indData +" day')");
                showDados(dataAtiva, null, null);
            }
        });
        img_view_retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indData--;// Code to execute when the image is clicked
                dataAtiva = db.pegaDataAtual("SELECT date('now','localtime', '" + indData + " day')");
                showDados(dataAtiva, null, null);
            }
        });

    }

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
        }
        return super.onOptionsItemSelected(item);
    }
    private void showHideLytTotalPedidoDia(){
        ViewGroup.LayoutParams params = toolbar2.getLayoutParams();

        // 2. Converter DP para Pixels para manter a consistência em diferentes telas

        // 3. Definir a nova altura
        // Remove o limite mínimo que o Android impõe
        toolbar2.setMinimumHeight(0);
        toolbar2.setPadding(0, 0, 0, 0); // O padding também pode ocupar espaço

        params.height = 0;
        toolbar2.setLayoutParams(params);
        toolbar2.setVisibility(View.GONE);

        //binding.toolbar2.setLayoutParams(par

        // O segredo está aqui: Toolbar.LayoutParams

        // se estiver usando a Toolbar da biblioteca appcompat (padrão atual).
        /*
        androidx.appcompat.widget.Toolbar.LayoutParams params =
                (androidx.appcompat.widget.Toolbar.LayoutParams) lytTotalPedidoDia.getLayoutParams();

        if (params == null) {
            // Caso os parâmetros estejam nulos por algum motivo, criamos novos
            params = new androidx.appcompat.widget.Toolbar.LayoutParams(
                    androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT,
                    androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT
            );
        }
        params.height = 0;

        // 3. Define a posição dentro da Toolbar (Gravity)
        // Isso garante que o layout não fique "escondido" atrás do título
        params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        // 5. Aplica os parâmetros de volta ao layout
        lytTotalPedidoDia.setLayoutParams(params);
        lytTotalPedidoDia.requestLayout();
         */
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pedido_enviados, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.busca_pedido_enviados);
        searchView = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? (SearchView) item.getActionView() : (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                if (query.trim().equals("")) {
                    offSet = 0;
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC ";
                    if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                        listaPedido = webPedidoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                    else
                        listaPedido = webPedidoDAO.getLista(SQL);
                    setRecyclerView(listaPedido);

                } else {
                    offSet = 0;
                    //SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' )";
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    //SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                    SQL = "SELECT  WP.* FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR  = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' ) ORDER BY WP.ID_WEB_PEDIDO_SERVIDOR DESC ";

                    if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                        listaPedido = webPedidoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                    else
                        listaPedido = webPedidoDAO.getLista(SQL);
                    setRecyclerView(listaPedido);
                    *//*
                    List<WebPedido> listaBusca = buscaPedidoEnviado(listaPedido, query);
                    try {
                        totalReg = listaBusca.size();
                    } catch (NumberFormatException | NullPointerException e) {
                        totalReg = 0;
                    } catch (Exception e) {
                        totalReg = 0;
                    }
                    setRecyclerView(listaBusca);

                     *//*
                }
                System.gc();
                return false;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Razão social/Nº pedido");
        return true;
    }*/

    private void showDados(String dataEmissao, String dataInicial, String dataFinal) {
        showProgressDialogCustom("Efetuando pesquisa dos pedidos! Aguarde...");
        /*
        progress = new ProgressDialog(ActivityPedidoEnviado.this);
        progress.setMessage("Efetuando pesquisa dos pedidos! Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();
         */
        try {
            if ( configuracao.getPedido_enviado_on_line_app().equalsIgnoreCase("S")) {
               //if ( configuracao.getOrdem_lista_cliente().equalsIgnoreCase("E")) {
                  SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() +
                        " AND DATA_EMISSAO = '" + dataEmissao + "' ";
                  totalReg = webPedidoDAO.getTotalReg(SQL);
                  SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() +
                        " AND DATA_EMISSAO = '" + dataEmissao + "' " +
                        " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC ";
                  if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                      listaPedido = webPedidoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                  else
                      listaPedido = webPedidoDAO.getLista(SQL);
                  SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() +
                        " AND DATA_EMISSAO = '" + dataEmissao + "' " +
                        " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC ";
                  List<WebPedido> listaTotalDia = new ArrayList<>();
                  listaTotalDia = webPedidoDAO.getLista(SQL);
                  float totalVendaDia = 0.00f;
                  for (WebPedido webPedido : listaTotalDia) {
                     totalVendaDia += Float.parseFloat(webPedido.getValor_total());
                  }
                  txvTotalPedidoDia.setText(MascaraUtil.mascaraRealSimbolo(totalVendaDia));
                  txvTotalTitulo.setTextSize(18);
                  txvTotalTitulo.setText(MascaraUtil.formataData(dataAtiva, "/", 10));
               //}
            }  else {
                int idCadastro = 0;
                try {
                    Bundle extras = getIntent().getExtras();
                    if (extras != null && extras.containsKey("idcliente")) {
                        idCadastro = Integer.parseInt(Objects.requireNonNull(extras.getString("idcliente")));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if ( dataInicial == null && dataFinal == null) {
                    if ( idCadastro > 0) {
                        SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND ID_CADASTRO = " + idCadastro;
                        totalReg = webPedidoDAO.getTotalReg(SQL);
                        SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND ID_CADASTRO = " + idCadastro + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC ";
                    } else{
                        SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                        totalReg = webPedidoDAO.getTotalReg(SQL);
                        SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC ";
                    }
                } else {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() +
                            " AND (DATA_EMISSAO >= '" + dataInicial + "' AND DATA_EMISSAO <= '" + dataFinal + "')";
                    //;
                    totalReg = webPedidoDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() +
                            " AND (DATA_EMISSAO >= '" + dataInicial + "' AND DATA_EMISSAO <= '" + dataFinal + "')" +
                            " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC, DATA_EMISSAO ";
                }
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                    listaPedido = webPedidoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                else
                    listaPedido = webPedidoDAO.getLista(SQL);
            }
            setRecyclerView(listaPedido);
            if (actionMode == null)
                listaPedidoAdapter.clearSelections();
            else
                actionMode.finish();
            dismissProgressDialogCustom();
            
            // NOVO: Exibe o informativo de totais após carregar a lista
            showResumoInformativo();

        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            dismissProgressDialogCustom();
            listaPedido.clear();
            edtTotalPedidos.setText("0/" + totalReg);
        } catch (Exception e) {
            dismissProgressDialogCustom();
            listaPedido.clear();
            edtTotalPedidos.setText("0/" + totalReg);
        }
        try {
            dismissProgressDialogCustom();
        }catch (NullPointerException e) {

        }
        try {
            if (configuracao.getVisualiza_legenda_pedido() == null || configuracao.getVisualiza_legenda_pedido().equalsIgnoreCase("S")) {
                if (!listaPedido.isEmpty())
                    startActivity(new Intent(ActivityPedidoEnviado.this, ActivityDialogPedido.class));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if ( dataInicial != null && dataFinal != null && totalReg == 0 )
            DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Sem registros de pedidos no periodo informado!", R.drawable.ic_alerta_mensagem);

    }

    private void showDados2(List<WebPedido> listaPedido) {
        try {
                float totalVendaDia = 0.00f;
                for ( WebPedido webPedido : listaPedido) {
                    if ( !webPedido.getExcluido().equalsIgnoreCase("S"))
                       totalVendaDia += Float.parseFloat(webPedido.getValor_total());
                }
                txvTotalPedidoDia.setText(MascaraUtil.mascaraRealSimbolo(totalVendaDia));
                indData = 0;
                txvTotalTitulo.setTextSize(12);
                txvTotalTitulo.setText("(P) " +MascaraUtil.formataData(dataIni,"/", 8) + " A " + MascaraUtil.formataData(dataFin,"/", 8));

                setRecyclerView(listaPedido);
                totalReg = listaPedido.size();
                edtTotalPedidos.setText("0/" + totalReg);
            if (actionMode == null)
                listaPedidoAdapter.clearSelections();
            else
                actionMode.finish();
            dismissProgressDialogCustom();
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            listaPedido.clear();
            edtTotalPedidos.setText("0/" + totalReg);
        } catch (Exception e) {
            listaPedido.clear();
            edtTotalPedidos.setText("0/" + totalReg);
        }
        try {
            dismissProgressDialogCustom();
        }catch (NullPointerException e) {

        }
        try {
            // Desativado abertura automática de legenda para não conflitar com o resumo de totais
            /*if (configuracao.getVisualiza_legenda_pedido() == null || configuracao.getVisualiza_legenda_pedido().equalsIgnoreCase("S")) {
                if (!listaPedido.isEmpty())
                    startActivity(new Intent(ActivityPedidoEnviado.this, ActivityDialogPedido.class));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addMenuDrawerItem() {
        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.addSubMenu(" Filtros ");
        subMenu.add(0, 0, 0,  "FILTRO").setIcon(R.drawable.ic_lista_todos_drawner);
        subMenu.add(0, 1, 1, "NOVO CADASTRO DE PEDIDO").setIcon(R.drawable.ic_add_novo_registro);
        navigationView.invalidate();
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == 0) {
            drawerLayout.closeDrawers();
            showDialogFiltros();
        } else if (item.getItemId() == 1) {
            drawerLayout.closeDrawers();
            btnNovoPedido();
        }
        return true;
    }

    public void showDialogFiltros( ) {
        int tExtraFonte  = 0;
        View dialogView = LayoutInflater.from( this).inflate(R.layout.dialog_outros_filtro_lista_pedido, null, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        spnColaborador = (Spinner) dialogView.findViewById(R.id.spnColaborador);
        EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
        EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        try {
            if (UsuarioHelper.getUsuario().getUsuario_tipo() != null &&
                    !UsuarioHelper.getUsuario().getUsuario_tipo().isEmpty()
                    && UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("G")
                    && configuracao.getPedido_enviado_on_line_app().equalsIgnoreCase("S")) {
                List<Usuario> lista = new UsuarioDAO(db).getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND ID_QUANDO_VENDEDOR IS NOT NULL " +
                        "AND USUARIO_TIPO <> 'D' AND NOME_USUARIO <> '' ORDER BY NOME_USUARIO");
                List<Usuario> listaAdapter = new ArrayList<>();
                for (Usuario usuario1 : lista) {
                    listaAdapter.add(usuario1);
                }
                colaboradorAdapter = new ArrayAdapter<>(context,
                        tExtraFonte == 0 ? R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g,
                        listaAdapter);
                spnColaborador.setAdapter(colaboradorAdapter);

            } else {
                List<Usuario> lista = new ArrayList<>();
                lista.add(UsuarioHelper.getUsuario());
                colaboradorAdapter = new ArrayAdapter<>(context,
                        tExtraFonte == 0 ? R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g,
                        lista);
                spnColaborador.setAdapter(colaboradorAdapter);
            }
        } catch (NullPointerException ignored) {
        }
        txvDiaInicial = dialogView.findViewById(R.id.txvDiaInicial);
        txvDiaFinal   = dialogView.findViewById(R.id.txvDiaFinal);
        txvAnoInicial = dialogView.findViewById(R.id.txvAnoInicial);
        txvAnoFinal   = dialogView.findViewById(R.id.txvAnoFinal);
        txvMesInicial = dialogView.findViewById(R.id.txvMesInicial);
        txvMesFinal   = dialogView.findViewById(R.id.txvMesFinal);
        Button btnDiaAnterior = dialogView.findViewById(R.id.btnDiaAnterior);
        Button btnDiaAtual    = dialogView.findViewById(R.id.btnDiaAtual);
        Button btnSemanaAnterior = dialogView.findViewById(R.id.btnSemanaAnterior);
        Button btnSemanaAtual    = dialogView.findViewById(R.id.btnSemanaAtual);
        Button btnMesAnterior    = dialogView.findViewById(R.id.btnMesAnterior);
        Button btnMesAtual       = dialogView.findViewById(R.id.btnMesAtual);
        Button btnCalendario     = dialogView.findViewById(R.id.btnCalendario);

        btnDiaAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_button);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);

                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);

                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                db.pegaDataAtual("SELECT date('now','localtime', '" + indData +" day')");
                setData( db.pegaDataAtual("SELECT date('now','localtime', '-1 day')"), db.pegaDataAtual("SELECT date('now','localtime', '-1 day')"));
            }
        });
        btnDiaAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_button);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);

                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);

                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                setData( db.pegaDataFinal("SELECT date('now', 'localtime');"), db.pegaDataFinal("SELECT date('now', 'localtime');"));
            }
        });
        btnDiaAtual.callOnClick();
        btnSemanaAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_button);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                setData( db.pegaDataInicial("SELECT date('now','weekday 0', '-14 days');"), db.pegaDataFinal("SELECT date('now','weekday 0', '-8 days');"));
            }
        });
        btnSemanaAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_button);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                setData( db.pegaDataInicial("SELECT date('now','weekday 0', '-7 days');"),  db.pegaDataFinal("SELECT date('now','weekday 0', '-1 days');"));
            }
        });

        btnMesAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_button);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                setData( db.pegaDataInicial("SELECT date('now','start of month', '-1 month');"),  db.pegaDataFinal("SELECT date('now','start of month', '-1 day');"));
            }
        });

        btnMesAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_button);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                setData( db.pegaDataInicial("SELECT date('now','start of month');"), db.pegaDataFinal("SELECT date('now','start of month', '+1 month', '-1 days');"));
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_button);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_tamanho_fonte_color_transparent);
                startActivityForResult(new Intent(getApplicationContext(),  CalendarioActivity.class), 200);
            }
        });

        Button btnConfirmar = (Button) dialogView.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if ( configuracao.getPedido_enviado_on_line_app().equalsIgnoreCase("S"))
                   getListaWebPedido(dataIni, dataFin, Objects.requireNonNull(colaboradorAdapter.getItem(spnColaborador.getSelectedItemPosition())).getId_quando_vendedor());
                else
                    showDados(null, dataIni, dataFin);
            }
        });

        Button btnCancelar = (Button) dialogView.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void getListaWebPedido(String dataIni, String dataFin, String idVendedor) {
        listaPedido.clear();
        progress.show();
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
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<WebPedido>> call = apiRotas.visualizarPedidos(dataIni, dataFin, idVendedor, cabecalho);
        call.enqueue(new Callback<List<WebPedido>>() {
                    @Override
                    public void onResponse(Call<List<WebPedido>> call, Response<List<WebPedido>> response) {
                        if ( response != null) {
                            if (response.code() == 200) {
                                if ( response.body() != null) {
                                    listaPedido = response.body();
                                    if (!listaPedido.isEmpty()) {
                                        for (int i = 0; i < listaPedido.size(); i++) {
                                            listaPedido.get(i).setIsOnLine("S");
                                        }
                                        showDados2(listaPedido);
                                    } else {
                                        showDados2(listaPedido);
                                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Sem registros de pedidos no periodo informado!", R.drawable.ic_alerta_mensagem);
                                    }
                                } else {
                                    dismissProgressDialogCustom();
                                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Falha ao obter os dados do relatorio", R.drawable.ic_baseline_error_outline_24);
                                }
                            } else {
                                dismissProgressDialogCustom();
                                DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Falha ao obter os dados do relatorio", R.drawable.ic_baseline_error_outline_24);
                            }
                        } else {
                            dismissProgressDialogCustom();
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Falha ao obter os dados do relatorio", R.drawable.ic_baseline_error_outline_24);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebPedido>> call, Throwable t) {
                        dismissProgressDialogCustom();
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  t.getMessage(), R.drawable.ic_baseline_error_outline_24);
                    }
                });


    }

    private void setData(String dataInicial, String dataFinal  ) {
        dataIni = dataInicial;
        dataFin = dataFinal;
        txvDiaInicial.setText( dataInicial.substring(8));
        txvDiaFinal.setText( dataFinal.substring(8));
        txvAnoInicial.setText( dataInicial.substring(0,4));
        txvAnoFinal.setText( dataFinal.substring(0,4));
        txvMesInicial.setText(dataInicial.substring(5,7));
        txvMesFinal.setText(dataFinal.substring(5,7));
        if ( dataInicial.substring(5,7).equalsIgnoreCase("01"))
            txvMesInicial.setText("JAN");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("02"))
            txvMesInicial.setText("FEV");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("03"))
            txvMesInicial.setText("MAR");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("04"))
            txvMesInicial.setText("ABR");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("05"))
            txvMesInicial.setText("MAI");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("06"))
            txvMesInicial.setText("JUN");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("07"))
            txvMesInicial.setText("JUL");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("08"))
            txvMesInicial.setText("AGO");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("09"))
            txvMesInicial.setText("SET");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("10"))
            txvMesInicial.setText("OUT");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("11"))
            txvMesInicial.setText("NOV");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("12"))
            txvMesInicial.setText("DEZ");

        if ( dataFinal.substring(5,7).equalsIgnoreCase("01"))
            txvMesFinal.setText("JAN");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("02"))
            txvMesFinal.setText("FEV");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("03"))
            txvMesFinal.setText("MAR");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("04"))
            txvMesFinal.setText("ABR");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("05"))
            txvMesFinal.setText("MAI");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("06"))
            txvMesFinal.setText("JUN");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("07"))
            txvMesFinal.setText("JUL");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("08"))
            txvMesFinal.setText("AGO");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("09"))
            txvMesFinal.setText("SET");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("10"))
            txvMesFinal.setText("OUT");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("11"))
            txvMesFinal.setText("NOV");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("12"))
            txvMesFinal.setText("DEZ");

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
                    listaPedido.addAll(webPedidoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet));
                    listaPedidoAdapter.notifyDataSetChanged();
                }
            }
            edtTotalPedidos.setText("(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
        }

    }

    private int getFirstItem() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private int getLastItem() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastVisibleItemPosition();
    }

    public List<WebPedido> buscaPedidoEnviado(List<WebPedido> webPedidos, String query) {
        final String upperCaseQuery = query.toUpperCase();

        final List<WebPedido> lista = new ArrayList<>();
        for (WebPedido webPedido : webPedidos) {
            try {
                final String nomeCliente = webPedido.getNome_extenso().toUpperCase();
                final String numeroPedido = webPedido.getId_web_pedido_servidor().toUpperCase();
                if (nomeCliente.contains(upperCaseQuery) || numeroPedido.equals(upperCaseQuery))
                    lista.add(webPedido);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
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
        if (configuracao.getGira_tela_ped_enviado().equalsIgnoreCase("S")) {
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
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
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
                } else
                    return -1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        while (recyclerView.getItemDecorationCount() > 0)
            recyclerView.removeItemDecorationAt(0);
        recyclerView.setAdapter(listaPedidoAdapter);
        listaPedidoAdapter.notifyDataSetChanged();
        if (!lista.isEmpty()) {
            edtTotalPedidos.setText("1/" + totalReg);
            float totalCancelado = 0.00f;
            float totalFaturado  = 0.00f;
            float totalOutros    = 0.00f;
            for ( WebPedido webPedido : lista)
                try {

                   if ((webPedido.getId_pedido_venda() == null || webPedido.getId_pedido_venda().isEmpty()) && webPedido.getId_nota_fiscal() == null || webPedido.getId_nota_fiscal().isEmpty()) {
                      totalOutros += Float.parseFloat(webPedido.getValor_total() == null ? "0.00" : webPedido.getValor_total() );
                   } else {
                      if (webPedido.getExcluido().equalsIgnoreCase("S") || webPedido.getAnalise_credito().equalsIgnoreCase("B"))
                         totalCancelado += Float.parseFloat(webPedido.getValor_total() == null ? "0.00" : webPedido.getValor_total());
                      else if (webPedido.getFaturado().equalsIgnoreCase("S"))
                         totalFaturado += Float.parseFloat(webPedido.getValor_total() == null ? "0.00" : webPedido.getValor_total());
                      else
                         totalOutros += Float.parseFloat(webPedido.getValor_total() == null ? "0.00" : webPedido.getValor_total());

                   }
                } catch ( NullPointerException | NumberFormatException e) {
                    totalOutros += Float.parseFloat(webPedido.getValor_total() == null ? "0.00" : webPedido.getValor_total() );
                    e.printStackTrace();
                }

            try {
                txvTotalCancelado.setText(MascaraUtil.mascaraRealSemSimbolo(totalCancelado));
                txvTotalFaturado.setText(MascaraUtil.mascaraRealSemSimbolo(totalFaturado));
                txvTotalOutros.setText(MascaraUtil.mascaraRealSemSimbolo(totalOutros));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }else {
            txvTotalCancelado.setText("0,0");
            txvTotalFaturado.setText("0,00");
            txvTotalOutros.setText("0,00");
            edtTotalPedidos.setText("0/" + totalReg);
        }
    }

    @Override
    protected void onResume() {
        if (!isSincroniaInicial) {
            dismissProgressDialogCustom();
        }
        isSincroniaInicial = false;
        getConfiguracao();
        try {
            if (configuracao.getGira_tela_ped_enviado().isEmpty() || configuracao.getGira_tela_ped_enviado().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }


    @Override
    public void onPedidoRowClicked(int position) {
        if (position < 0 || position >= listaPedido.size()) return;

        if (listaPedidoAdapter.getSelectedItemCount() > 0)
            enableActionMode(position);
        else {
            // NOVO: Chama o Hub de Ações moderno para o pedido enviado
            exibirDialogAcoesPedido(position);

            /*
            // ANTERIOR: Abria direto a visualização
            if ( cliqueAtivo) {
                cliqueAtivo = false;
                PedidoHelper.setIdPedido(Integer.parseInt(listaPedidoAdapter.getItem(position).getId_web_pedido()));
                PedidoHelper.setWebPedido(listaPedidoAdapter.getItem(position));
                startActivity(new Intent(ActivityPedidoEnviado.this, ActivityPedidoMain.class)
                        .putExtra("vizualizacao", 1));
                cliqueAtivo = true;
            }
            */
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    @Override
    public View.OnClickListener onClickExcluir(int position) {
        return null;
    }

    @Override
    public View.OnClickListener onClickEnviar(int position) {
        return null;
    }

    @Override
    public View.OnClickListener onClickDuplic(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if ( !listaPedidoAdapter.getItem(position).getId_vendedor().equalsIgnoreCase(listaPedidoAdapter.getItem(position).getId_outro_vendedor()) ) {
                //    Toast.makeText(context, "Atenção, pedido feito por outro representante, a duplicação não é permitida, apenas a consulta!", Toast.LENGTH_SHORT).show();
                //    return;
                //}
                if ( cliqueAtivo) {
                    cliqueAtivo = false;
                    if (listaPedidoAdapter.getItem(position).getId_web_pedido() == null || listaPedidoAdapter.getItem(position).getId_web_pedido_servidor() == null) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Atenção, pedido sem numero de pedido servidor/web, a duplicação não é permitida, apenas a consulta!", R.drawable.ic_alerta_mensagem);
                        cliqueAtivo = true;
                        return;
                    }
                    showActionDialog(R.id.btnDuplic, listaPedidoAdapter.getItem(position), "Deseja duplicar o pedido " /*+ listaPedidoAdapter.getItem(position).getId_web_pedido() + "/" + listaPedidoAdapter.getItem(position).getId_web_pedido_servidor() + ") de (" + listaPedidoAdapter.getItem(position).getCadastro().getId_cadastro() + "/" + listaPedidoAdapter.getItem(position).getCadastro().getId_cadastro_servidor() + ") " + listaPedidoAdapter.getItem(position).getCadastro().getNome_cadastro()*/ + " para poder faturá-lo novamente?\n"
                            /*"Atenção!!\n" +
                            "Todos os produtos/descontos/promoções serão revisados, caso algum desconto/promoção não exista/e ou não sejam mais permitidos, os mesmos serão removidos de cada item, onde não sejam mais aplicáveis!"*/);
                }
            }
        };
    }

    @Override
    public View.OnClickListener onClickCompartilhar(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if ( !listaPedidoAdapter.getItem(position).getId_vendedor().equalsIgnoreCase(listaPedidoAdapter.getItem(position).getId_outro_vendedor()) ) {
                //    Toast.makeText(context, "Atenção, pedido feito por outro representante, a geração de um arquivo em PDF não é permitida, apenas a consulta!", Toast.LENGTH_SHORT).show();
                //    return;
                //}
                indSel = position;
                showActionDialog(R.id.btnCompartilhar, listaPedidoAdapter.getItem(position), "Deseja criar um arquivo PDF do pedido " /*+ listaPedidoAdapter.getItem(position).getId_web_pedido() */+ " para ser compartilhado?");
            }
        };
    }

    public View.OnClickListener onClickCopiaSegura(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Copia de segurança nao pode ser feita para pedido já enviado para faturamento", R.drawable.ic_alerta_mensagem);
            }
        };
    }

    @Override
    public View.OnClickListener onClickRastrear(final int position) {
        return null;
    }

    @Override
    public View.OnClickListener onClickLinhaTempo(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // NOVO: Chama o Hub de Ações para pedidos enviados
                exibirDialogAcoesPedido(position);
                
                // ANTERIOR: 
                // showMsgDialog(position, view.getId());
            }
        };
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100) {
                final List<WebPedido> pedidosSelecionados = listaPedidoAdapter.getItensSelecionados();
                criaPdf(pedidosSelecionados, indAcao);
            }
            if (requestCode == 200) {
                String dataInicial = data.getStringExtra("dataInicial");
                String dataFinal = data.getStringExtra("dataFinal");
                setData(dataInicial, dataFinal);
            } else {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao obter o periodo para p relatorio", R.drawable.ic_baseline_error_outline_24);
            }
            if ( requestCode == 300) {
                try {
                    SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = "
                            + usuario.getId_usuario() + " AND ID_WEB_PEDIDO = '" + listaPedido.get(indSel).getId_web_pedido() + "'";
                    listaPedido.set(indSel, webPedidoDAO.getLista(SQL).get(0));
                    listaPedidoAdapter.notifyItemChanged(indSel);
                } catch (IndexOutOfBoundsException|NullPointerException e) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  e.toString(), R.drawable.ic_alerta_mensagem);
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableActionMode(int position) {
        if (actionMode == null)
            actionMode = startSupportActionMode(actionModeCallback);
        if (listaPedidoAdapter.getSelectedItemCount() <= 0 || listaPedidoAdapter.getItem(position).getId_web_pedido_servidor().equals(listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido_servidor()))
            toggleSelection(position);
        else
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Somente é permitido a seleção de um pedido por vez", R.drawable.ic_alerta_mensagem);
    }
    private void toggleSelection(int position) {
        indSel = position;
        listaPedidoAdapter.toggleSelection(position);
        if (listaPedidoAdapter.getSelectedItemCount() == 0)
            actionMode.finish();
        else {
            actionMode.setTitle(String.valueOf(listaPedidoAdapter.getSelectedItemCount()));
            actionMode.invalidate();
        }
    }


    private class RefreshListener implements  SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            //mSwipeRefreshLayout.setRefreshing(false);
            //onResume();
        }
    }
    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode_pdf, menu);
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
                case R.id.action_pdf_pedido:
                    showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja gerar um arquivo PDF do pedido?"/* + pedidosSelecionados.get(0).getId_web_pedido() + "?"*/);
                    return true;
                case R.id.action_pdf_pedido_email:
                    if (pedidosSelecionados.size() == 1)
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja enviar um arquivo PDF do pedido " /*+ pedidosSelecionados.get(0).getId_web_pedido() */+ " para o email do cliente ?");
                    else
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja enviar os " + pedidosSelecionados.size() + " pedidos selecionados para os emails de seus clientes?");
                    return true;
                case R.id.action_duplica_pedido:
                    if ( cliqueAtivo) {
                        cliqueAtivo = false;
                        if (pedidosSelecionados.get(0).getId_web_pedido() == null || pedidosSelecionados.get(0).getId_web_pedido_servidor() == null) {
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Atenção, pedido sem numero de pedido servidor/web, a duplicação não é permitida, apenas a consulta!", R.drawable.ic_alerta_mensagem);
                            cliqueAtivo = true;
                            return false;
                        }
                        showActionDialog(mode, item.getItemId(), pedidosSelecionados, "Deseja duplicar o pedido "/* + pedidosSelecionados.get(0).getId_web_pedido() + "/" + pedidosSelecionados.get(0).getId_web_pedido_servidor() + ") de (" + pedidosSelecionados.get(0).getCadastro().getId_cadastro() + "/" + pedidosSelecionados.get(0).getCadastro().getId_cadastro_servidor() + ") " + pedidosSelecionados.get(0).getCadastro().getNome_cadastro()*/ + " para poder faturá-lo novamente?\n"
                                /*"Atenção!!\n" +
                                "Todos os produtos/descontos/promoções serão revisados, caso algum desconto/promoção não exista/e ou não sejam mais permitidos, os mesmos serão removidos de cada item, onde não sejam mais aplicáveis!"*/);
                        return true;
                    }
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            listaPedidoAdapter.clearSelections();
            actionMode = null;
            cliqueAtivo = true;
        }
    }



    private void criaPdf(List<WebPedido> pedidosSelecionados, int indAcao) {  //1 cria e mostra PDF  2 cria e envia por email
        EmpresaParametro empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + pedidosSelecionados.get(0).getId_empresa() + "'");
        if (empresa == null) {
            empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO");
            empresa = null;
            if ( empresa == null) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao obter as informações da empresa!!!\nFavor entrar em contato com o suporte técnico!!!\n", R.drawable.ic_baseline_error_outline_24);
                //return;
            }
        }
        File filePDF;
        if (empresa == null) {
            PDFPedidoUtil2026 pdfPedidoUtil = new PDFPedidoUtil2026(pedidosSelecionados.get(0), empresa, ActivityPedidoEnviado.this, indAcao);
            filePDF = pdfPedidoUtil.criandoPdf("PE");
            if (filePDF == null) {
                dismissProgressDialogCustom();
                cliqueAtivo = true;
                return;
            }
        } else {
            try {
                if (empresa.getUtiliza_icms_app().equalsIgnoreCase("S")) {
                    PDFPedidoICMSST pdfPedidoUtil = new PDFPedidoICMSST(pedidosSelecionados.get(0), empresa, ActivityPedidoEnviado.this);
                    filePDF = pdfPedidoUtil.criandoPdf("PE");
                } else {
                    PDFPedidoUtil2026 pdfPedidoUtil = new PDFPedidoUtil2026(pedidosSelecionados.get(0), empresa, ActivityPedidoEnviado.this, indAcao);
                    filePDF = pdfPedidoUtil.criandoPdf("PE");
                }
            } catch (NullPointerException e) {
                PDFPedidoUtil2026 pdfPedidoUtil = new PDFPedidoUtil2026(pedidosSelecionados.get(0), empresa, ActivityPedidoEnviado.this, indAcao);
                filePDF = pdfPedidoUtil.criandoPdf("PE");
            }
            if (filePDF == null) {
                dismissProgressDialogCustom();
                cliqueAtivo = true;
                return;
            }
        }
        if (Build.VERSION.SDK_INT > +Build.VERSION_CODES.N) {
            if (indAcao == 1) {
                try {
                    Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", filePDF);
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
            } else if (indAcao == 3) {
                try {
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND)
                            .setType("pdf/*")
                            .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(ActivityPedidoEnviado.this, getApplicationContext().getPackageName() + ".provider", filePDF))
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
                cliqueAtivo = true;
                return;
            }

        } else {
            if (indAcao == 1) {
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
            } else if (indAcao == 3) {
                try {
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND)
                            .setType("pdf/*")
                            .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(ActivityPedidoEnviado.this, getApplicationContext().getPackageName() + ".provider", filePDF))
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
            } else {
                cliqueAtivo = true;
                return;
            }
        }
        cliqueAtivo = true;
    }

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
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPedidoEnviado.this));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPedidoEnviado.this));
        }
        String SQL = "SELECT * FROM TBL_CONFIGURACAO";
        db = new DBHelper(context);
        configuracao = new ConfiguracaoDAO(db).getConfiguracao(SQL);
        SQL = "SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + "'";
        EmpresaParametro empresaParametro = new EmpresaParametroDAO(db).getEmpresa(SQL);
        Float saldoVerba = 0.00f;
        try {
            if (configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S")) {
                VendedorBonusResumo vendedorBonusResumo = new VendedorBonusResumoDAO(db).getVendedor("SELECT * FROM TBL_VENDEDOR_BONUS_RESUMO WHERE ID_VENDEDOR = '" + UsuarioHelper.getUsuario().getId_quando_vendedor() + "'");
                saldoVerba = Float.parseFloat(vendedorBonusResumo.getValor_saldo());
            }
        } catch (NullPointerException | CursorIndexOutOfBoundsException | NumberFormatException ignored) {
        }
        CampanhaComercialCabDAO campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);

        WebPedido webPedidoDuplicado = webPedido;
        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
        SQL = "SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedidoDuplicado.getId_web_pedido() + " ORDER BY ID_WEB_ITEM";
        webPedidoDuplicado.setWebPedidoItens(webPedidoItensDAO.getListaDuplicacao(SQL, configuracao, empresaParametro, saldoVerba, webPedidoDuplicado.getCadastro(), campanhaComercialCabDAO));
        webPedidoDuplicado.setId_web_pedido(null);
        webPedidoDuplicado.setId_pedido_app(null);
        webPedidoDuplicado.setId_web_pedido_servidor(null);
        webPedidoDuplicado.setPedido_enviado("N");
        webPedidoDuplicado.setFinalizado("N");
        webPedidoDuplicado.setFaturado(null);
        webPedidoDuplicado.setAnalise_credito(null);
        webPedidoDuplicado.setData_emissao(db.pegaDataAtual());
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
            webPedidoDuplicado.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
            webPedidoDuplicado.setId_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
            webPedidoDuplicado.setId_outro_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
            webPedidoDuplicado.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
            webPedidoDuplicado.setUsuario_lancamento_nome(UsuarioHelper.getUsuario().getNome_usuario());
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        try {
            ClienteHelper.setCliente(webPedidoDuplicado.getCadastro());
        } catch (NullPointerException e) {
            showFeedbackDialog("Cliente não localizado!", true, false, null);
        } catch (Exception e) {
            showFeedbackDialog("Cadastro do cliente não localizado!", true, false, null);
        }
        for (WebPedidoItens webPedidoItens : webPedidoDuplicado.getWebPedidoItens()) {
            webPedidoItens.setId_web_item_servidor(null);
            webPedidoItens.setId_pedido("");
            webPedidoItens.setId_web_item(null);
        }
        PedidoHelper.setWebPedido(webPedidoDuplicado);
        PedidoHelper.setListaWebPedidoItens(webPedidoDuplicado.getWebPedidoItens());

        getConfiguracao();
        Cliente cliente = webPedidoDuplicado.getCadastro();
        if (configuracao != null && configuracao.getTrab_com_checkin_app() != null && configuracao.getTrab_com_checkin_app().equalsIgnoreCase("S") && cliente != null) {
            dismissProgressDialogCustom();
            cliqueAtivo = true;

            String razao = cliente.getNome_cadastro() != null ? cliente.getNome_cadastro() : "";
            String fantasia = cliente.getNome_fantasia() != null ? cliente.getNome_fantasia() : "";
            String end = cliente.getEndereco() + ", " + cliente.getEndereco_numero() + " - " + cliente.getEndereco_bairro() + " - " + cliente.getNome_municipio() + "/" + cliente.getEndereco_uf();

            String dataHoraAtual = db.pegaDataHoraAtual().replaceAll("[^0-9]", "");
            int idVendedorNum = 0;
            try {
                if (UsuarioHelper.getUsuario() != null && UsuarioHelper.getUsuario().getId_quando_vendedor() != null) {
                    idVendedorNum = Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor().trim());
                }
            } catch (Exception ignored) {}

            int idClienteNum = cliente.getId_cadastro_servidor() > 0 ? cliente.getId_cadastro_servidor() : cliente.getId_cadastro();
            String idVisitaApp = String.format("%03d", idVendedorNum) + "90000008" + String.format("%08d", idClienteNum) + dataHoraAtual;

            DialogHelper.showCheckInClienteDialog(
                    this,
                    razao,
                    fantasia,
                    end,
                    (locationData, fotoBytes) -> {
                        try {
                            com.example.rcksuporte05.rcksistemas.model.WebVisita visita = new com.example.rcksuporte05.rcksistemas.model.WebVisita();
                            visita.setIdVisitaApp(idVisitaApp);
                            if (UsuarioHelper.getUsuario() != null && UsuarioHelper.getUsuario().getId_quando_vendedor() != null) {
                                try {
                                    visita.setIdVendedor(Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor()));
                                } catch (Exception ignored) {}
                            }
                            visita.setIdClienteServidor(idClienteNum);
                            visita.setDataHoraCheckin(db.pegaDataHoraAtual());

                            if (locationData != null && locationData.hasLocation) {
                                visita.setLatitudeCheckin(String.valueOf(locationData.latitude));
                                visita.setLongitudeCheckin(String.valueOf(locationData.longitude));
                                visita.setAltitudeCheckin(String.valueOf(locationData.altitude));
                                visita.setPrecisaoCheckin(String.valueOf(locationData.accuracy));
                            }

                            if (fotoBytes != null && fotoBytes.length > 0) {
                                visita.setFotoCheckin1(fotoBytes);
                            }

                            visita.setStatusVisita("A");
                            visita.setVersaoApp(BuildConfig.VERSION_NAME);

                            com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO visitaDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db);
                            visitaDAO.salvarVisita(visita);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        webPedidoDuplicado.setId_visita_app(idVisitaApp);
                        PedidoHelper.setWebPedido(webPedidoDuplicado);
                        PedidoHelper.setListaWebPedidoItens(webPedidoDuplicado.getWebPedidoItens());

                        finish();
                        Intent intent = new Intent(ActivityPedidoEnviado.this, ActivityPedidoMain.class);
                        intent.putExtra("id_visita_app", idVisitaApp);
                        startActivity(intent);
                        cliqueAtivo = true;
                    },
                    () -> {
                        // Se cancelar o checkin, fecha o progresso e libera cliques
                        dismissProgressDialogCustom();
                        cliqueAtivo = true;
                    }
            );
            return;
        }

        dismissProgressDialogCustom();
        cliqueAtivo = true;
        finish();
        startActivity(new Intent(ActivityPedidoEnviado.this, ActivityPedidoMain.class));
        cliqueAtivo = true;
    }


    private boolean usuarioIsLogado() {
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(context));
        try {
            if (UsuarioHelper.getUsuario() == null) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
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

    public void analisaPedidos() {  //Analise pedidos nao faturados e em analise
        showProgressDialogCustom("Sincronizando/Analisando pedidos! Aguarde...");
        /*
        progress = new ProgressDialog(ActivityPedidoEnviado.this);
        progress.setMessage("Sincronizando/Analisando pedidos! Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();
         */

        if ( !usuarioIsLogado()) {
            dismissProgressDialogCustom();
            return;
        }
        List<WebPedidoAnalise> listaParaAnalise = new ArrayList<>();
        SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = "
                + usuario.getId_usuario() + " AND EXCLUIDO <> 'S' AND ID_VENDEDOR = ID_OUTRO_VENDEDOR " +
                " AND (FATURADO <> 'S'  OR FATURADO IS NULL)  ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC ";
        listaParaAnalise = webPedidoDAO.getListaAnalise(SQL );

        if ( listaParaAnalise.size() <= 0) {
           //progress.cancel();
            //progress.dismiss();
            updateProgressMessage("Analisando pedidos! Aguarde...");
            showDados(null, null, null);
            return;
        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String versaoApp = pInfo.versionName.substring(0, 5);

        getConfiguracao();
        if (!configuracao.getId().isEmpty()) {
            Call<Sincronia> call = getRotasAPI(1, listaParaAnalise, configuracao, versaoApp);
            if (call == null) {
                dismissProgressDialogCustom();
                dismissProgressDialogCustom();
                return;
            }

            call.enqueue(new Callback<Sincronia>() {
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    if (response.code() == 200) {
                        final Sincronia sincronia1 = response.body();
                        updatePedidosAnalisados(sincronia1.getListaParaAnalise());
                        //progress.cancel();
                        //progress.dismiss();
                        showDados(null, null, null);
                    }  else {
                        //progress.cancel();
                        //progress.dismiss();
                        showDados(null, null, null);
                    }
                }

                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    //progress.cancel();
                    //progress.dismiss();
                    showDados(null, null, null);
                }
            });
        }else {
            //progress.cancel();
            showDados(null, null, null);
            DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Parametros para conexão não cadastrados!", R.drawable.ic_alerta_mensagem);
        }

    }

    private void updatePedidosAnalisados(List<WebPedidoAnalise> listaParaAnalise) {
        if (listaParaAnalise == null || listaParaAnalise.isEmpty()) return;
        WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);

        for (int i = 0; i < listaParaAnalise.size(); i++) {
            WebPedidoAnalise analise = listaParaAnalise.get(i);
            if (analise == null) continue;
            try {
                 if( analise.getExpedicao_status() == null && "S".equalsIgnoreCase(analise.getAnalise_credito())) {
                    try {
                        if ( "N".equalsIgnoreCase(analise.getExcluido())) {
                            if ( analise.getFaturado() == null && "S".equalsIgnoreCase(analise.getAnalise_credito())){
                                webPedidoDAO.updatePedidoAnaliseCredito(analise);
                            } else if("S".equalsIgnoreCase(analise.getAnalise_credito()) &&  "S".equalsIgnoreCase(analise.getFaturado()) ) {
                                webPedidoDAO.updatePedidoFaturado(analise);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 } else if( analise.getExpedicao_status() == null  && "N".equalsIgnoreCase(analise.getAnalise_credito())) {
                     if ( analise.getFaturado() == null && "N".equalsIgnoreCase(analise.getAnalise_credito())){
                         webPedidoDAO.updatePedidoAnaliseCredito(analise);
                     } else if("N".equalsIgnoreCase(analise.getAnalise_credito()) &&  "S".equalsIgnoreCase(analise.getFaturado()) ) {
                         webPedidoDAO.updatePedidoFaturado(analise);
                     }
                 } else if( analise.getExpedicao_status() != null  && "S".equalsIgnoreCase(analise.getAnalise_credito())) {
                    try {
                        if ( "N".equalsIgnoreCase(analise.getExcluido())) {
                            if ( analise.getFaturado() == null && "S".equalsIgnoreCase(analise.getAnalise_credito())){
                                webPedidoDAO.updatePedidoAnaliseCredito(analise);
                            } else if("S".equalsIgnoreCase(analise.getAnalise_credito()) &&  "S".equalsIgnoreCase(analise.getFaturado()) ) {
                                webPedidoDAO.updatePedidoFaturado(analise);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch ( Exception e) {
                e.printStackTrace();
                if ( "S".equalsIgnoreCase(analise.getExcluido())) {
                    webPedidoDAO.updatePedidCanceladoExcluido(analise);
                }
                try {
                    if ("N".equalsIgnoreCase(analise.getExcluido())) {
                        if ( analise.getFaturado() == null && "S".equalsIgnoreCase(analise.getAnalise_credito())){
                            webPedidoDAO.updatePedidoAnaliseCredito(analise);
                        } else if("S".equalsIgnoreCase(analise.getAnalise_credito()) &&  "S".equalsIgnoreCase(analise.getFaturado()) ) {
                            webPedidoDAO.updatePedidoFaturado(analise);
                        }  else if("S".equalsIgnoreCase(analise.getAnalise_credito()) &&  "N".equalsIgnoreCase(analise.getFaturado()) ) {
                            webPedidoDAO.updatePedidoAnaliseCredito(analise);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if ( "S".equalsIgnoreCase(analise.getExcluido())) {
                webPedidoDAO.updatePedidCanceladoExcluido(analise);
            } else if  ("B".equalsIgnoreCase(analise.getAnalise_credito()))  {
                webPedidoDAO.updatePedidCanceladoExcluido(analise);
            } else {
                try {
                    if ( analise.getId_nota_fiscal() != null && Integer.parseInt(analise.getId_nota_fiscal()) > 0)  {
                        // Faturado
                    }
                } catch ( Exception e ) {
                    webPedidoDAO.updatePedidoImportado(analise);
                }
            }

        }
    }

    private void showResumoInformativo() {
        if (listaPedido == null || listaPedido.isEmpty()) return;

        float totalAtivo = 0.00f;
        float totalCancelado = 0.00f;

        for (WebPedido pedido : listaPedido) {
            float valor = 0.00f;
            try {
                valor = Float.parseFloat(pedido.getValor_total() == null ? "0.00" : pedido.getValor_total());
            } catch (Exception ignored) {}

            // REGRA DA COR VERMELHA (CANCELADO OU BLOQUEADO)
            boolean isVermelho = "S".equalsIgnoreCase(pedido.getExcluido()) ||
                                "B".equalsIgnoreCase(pedido.getAnalise_credito());

            if (isVermelho) {
                totalCancelado += valor;
            } else {
                totalAtivo += valor;
            }
        }

        View viewResumo = getLayoutInflater().inflate(R.layout.layout_dialog_resumo_vendas_periodo, null);

        TextView tvAtivo = viewResumo.findViewById(R.id.tvResumoAtivo);
        TextView tvCancelado = viewResumo.findViewById(R.id.tvResumoCancelado);
        TextView tvGeral = viewResumo.findViewById(R.id.tvResumoGeral);

        if (tvAtivo != null) tvAtivo.setText(MascaraUtil.mascaraRealSimbolo(totalAtivo));
        if (tvCancelado != null) tvCancelado.setText(MascaraUtil.mascaraRealSimbolo(totalCancelado));
        if (tvGeral != null) tvGeral.setText(MascaraUtil.mascaraRealSimbolo(totalAtivo + totalCancelado));

        DialogHelper.showVendasResumoDialog(this, viewResumo);
    }

    /*
    if (pedidos.get(position).getAnalise_credito().equalsIgnoreCase("B") || pedidos.get(position).getExcluido().equalsIgnoreCase("S")) {
        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_excluido_negado));
        if ( pedidos.get(position).getAnalise_credito().equalsIgnoreCase("B"))
            holder.txtStatus.setText("Crédito negado".toUpperCase());
        else if( pedidos.get(position).getExcluido().equalsIgnoreCase("S"))
            holder.txtStatus.setText("Cancelado/Excluido".toUpperCase());
    }

     */

    private Call<Sincronia> getRotasAPI(int idRota, List<WebPedidoAnalise> listaParaAnalise, Configuracao configuracao, String versaoApp) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Sincronia sincronia = new Sincronia(false,
                false);
        sincronia.setListaParaAnalise( listaParaAnalise );
        if ( idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                return apiRotas.analisaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
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
                    return apiRotas.analisaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
                } catch (NullPointerException ex) {
                    //progress.dismiss();
                }
            }
        } else if (idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                return apiRotas2.analisaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
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
                    return apiRotas2.analisaPedido(UsuarioHelper.getUsuario().getId_usuario(), sincronia, cabecalho);
                } catch (NullPointerException ex) {
                    //progress.dismiss();
                }
            }
        }
        return  null;

    }

    private class QueryTextListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(final String query) {

            if (query.trim().equals("")) {
                offSet = 0;
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                totalReg = webPedidoDAO.getTotalReg(SQL);
                SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC ";
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                    listaPedido = webPedidoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                else
                    listaPedido = webPedidoDAO.getLista(SQL);
                setRecyclerView(listaPedido);

            } else {
                offSet = 0;
                //SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario();
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' )";
                totalReg = webPedidoDAO.getTotalReg(SQL);
                //SQL = "SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC ";
                SQL = "SELECT  WP.* FROM TBL_WEB_PEDIDO AS WP INNER JOIN TBL_CADASTRO AS CD ON CD.ID_CADASTRO_SERVIDOR  = WP.ID_CADASTRO WHERE WP.PEDIDO_ENVIADO = 'S' AND WP.USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND ( CD.NOME_CADASTRO LIKE '%" + query + "%' OR WP.ID_WEB_PEDIDO_SERVIDOR LIKE '%" + query  + "%' ) ORDER BY WP.ID_WEB_PEDIDO_SERVIDOR DESC ";

                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                    listaPedido = webPedidoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                else
                    listaPedido = webPedidoDAO.getLista(SQL);
                setRecyclerView(listaPedido);
                    /*
                    List<WebPedido> listaBusca = buscaPedidoEnviado(listaPedido, query);
                    try {
                        totalReg = listaBusca.size();
                    } catch (NumberFormatException | NullPointerException e) {
                        totalReg = 0;
                    } catch (Exception e) {
                        totalReg = 0;
                    }
                    setRecyclerView(listaBusca);

                     */
            }
            System.gc();
            return false;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }


    }

    public void exibirDialogAcoesPedido(final int position) {
        if (isFinishing() || isDestroyed()) return;

        LayoutInflater inflater = getLayoutInflater();
        View headerView = inflater.inflate(R.layout.layout_dialog_menu_pedido, null);
        WebPedido pedido = listaPedidoAdapter.getItem(position);

        // Preenche o ícone no cabeçalho moderno
        ImageView ivIcon = headerView.findViewById(R.id.ivMenuPedidoIcon);
        if (ivIcon != null) {
            ivIcon.setColorFilter(androidx.core.content.ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        }

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

        String[] opcoes = {"VISUALIZAR PEDIDO", "DUPLICAR PEDIDO", "COMPARTILHAR PEDIDO", "GERAR PDF", "RASTREIO / STATUS", "SINCRONIZAR DADOS", "FECHAR"};

        new com.google.android.material.dialog.MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded)
                .setCustomTitle(headerView)
                .setItems(opcoes, (dialog, which) -> {
                    List<WebPedido> lista = new ArrayList<>();
                    lista.add(pedido);
                    
                    switch (which) {
                        case 0: // Visualizar
                            showProgressDialogCustom("Preparando visualização do pedido...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                PedidoHelper.setIdPedido(Integer.parseInt(pedido.getId_web_pedido()));
                                PedidoHelper.setWebPedido(pedido);
                                startActivity(new Intent(ActivityPedidoEnviado.this, ActivityPedidoMain.class).putExtra("vizualizacao", 1));
                            }, 200);
                            break;
                        case 1: // Duplicar
                            showActionDialog(-400, pedido, "Deseja duplicar o pedido para poder faturá-lo novamente?");
                            break;
                        case 2: // Compartilhar
                            showActionDialog(-500, pedido, "Deseja criar um arquivo PDF do pedido para ser compartilhado?");
                            break;
                        case 3: // Gerar PDF
                            showActionDialog(-600, pedido, "Deseja gerar o arquivo PDF deste pedido?");
                            break;
                        case 4: // Rastreio / Status (Activity que você mencionou)
                            PedidoHelper.setWebPedido(pedido);
                            startActivityForResult(new Intent(ActivityPedidoEnviado.this, com.example.rcksuporte05.rcksistemas.activity.ActivityAnalisePedido.class), 300);
                            break;
                        case 5: // Sincronizar Dados
                            showActionDialog(-700, pedido, "Deseja sincronizar e atualizar o status deste pedido?");
                            break;
                        case 6: // Fechar
                            dialog.dismiss();
                            break;
                    }
                })
                .show();
    }

    /*
    public void showMsgSimNao(ActionMode mode, final int id, List<WebPedido> pedidosSelecionados, String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoEnviado.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoEnviado.this);
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
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
                cliqueAtivo = true;
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == R.id.action_pdf_pedido) {
                    try {
                        alertDialog.dismiss();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    indAcao = 1;
                    criaPdf(pedidosSelecionados, indAcao); // acao 1 cria e mostra PDF
                    cliqueAtivo = true;

                } else if (id == R.id.action_pdf_pedido_email) {
                    try {
                        alertDialog.dismiss();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    indAcao = 2;
                    criaPdf(pedidosSelecionados, indAcao);
                    cliqueAtivo = true;

                } else if (id == R.id.action_duplica_pedido) {
                    try {
                        alertDialog.dismiss();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        mode.finish();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    duplicaPedido(pedidosSelecionados.get(0));
                }
            }
        });
        alertDialog.show();
    }
*/
    /*
    public void showMsgSimNao(final int id, WebPedido webPedido, String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoEnviado.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoEnviado.this);
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
                cliqueAtivo = true;
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == R.id.btnDuplic || id == -400) {
                    try {
                        alertDialog.dismiss();
                    } catch (NullPointerException ignored) {
                    }
                    duplicaPedido(webPedido);
                } else if (id == R.id.btnCompartilhar || id == -500) {
                    try {
                        alertDialog.dismiss();
                    } catch (NullPointerException ignored) {
                    }
                    indAcao = 3;
                    List<WebPedido> lista = new ArrayList<>();
                    listaPedidoAdapter.toggleSelection(indSel);
                    lista.add(webPedido);
                    criaPdf(lista, indAcao);  // 3 Compartilha PDF
                }
            }

        });
        alertDialog.show();
    }
*/

    /*
    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoEnviado.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoEnviado.this);
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

    /**
     * NOVO: Métodos auxiliares para isolar a lógica de duplicação com progress dialog.
     */
    private void executarDuplicacao(WebPedido webPedido) {
        showProgressDialogCustom("Duplicando pedido, aguarde...");
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            duplicaPedido(webPedido);
            cliqueAtivo = true;
        }, 200);
    }

    private void executarDuplicacao(ActionMode mode, WebPedido webPedido) {
        showProgressDialogCustom("Duplicando pedido, aguarde...");
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try { if (mode != null) mode.finish(); } catch (NullPointerException e) { e.printStackTrace(); }
            duplicaPedido(webPedido);
            cliqueAtivo = true;
        }, 200);
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
            try { String prazo = db.consultaDados("SELECT NOME_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + pedido.getId_condicao_pagamento() + ";", "NOME_CONDICAO"); tvPrazo.setText(prazo != null && !prazo.isEmpty() ? prazo : "Não informado"); } catch (Exception e) { tvPrazo.setText("Não informado"); }
        }
        if (tvOperacao != null) {
            try { String operacao = db.consultaDados("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + pedido.getId_operacao() + ";", "NOME_OPERACAO"); tvOperacao.setText(operacao != null && !operacao.isEmpty() ? operacao : "Não informado"); } catch (Exception e) { tvOperacao.setText("Não informado"); }
        }
        if (tvTotal != null) tvTotal.setText(MascaraUtil.mascaraRealSimbolo(pedido.getValor_total()));

        if (lyTotal != null) {
            lyTotal.setVisibility(mostrarTotal ? View.VISIBLE : View.GONE);
        }

        return viewResumo;
    }

    public void showActionDialog(final int id ,WebPedido webPedido, String mensagem) {
        Integer iconConfirm = null;
        if (id == R.id.btnDuplic || id == -400) {
            iconConfirm = R.drawable.ic_duplica_pedido;
        } else if (id == R.id.btnCompartilhar || id == -500) {
            iconConfirm = R.drawable.ic_baseline_compartilhar_pedido_2025;
        } else if (id == -600) {
            iconConfirm = R.drawable.ic_pdf_pedido;
        } else if (id == -700) {
            iconConfirm = R.mipmap.ic_enviar_pedidos;
        }

        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", iconConfirm,
                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override public void onPositive() { cliqueAtivo = true; }
                    @Override public void onNegative() {
                        if (id == R.id.btnDuplic || id == -400) {
                            // QUESTÃO: VERIFICAÇÃO DE DÉBITO NA DUPLICAÇÃO (ENVIADOS)
                            float vencido = 0.00f;
                            float vencendo = 0.00f;
                            try {
                                vencido = Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencido() == null ? "0.00" : webPedido.getCadastro().getFinanceiro_vencido());
                                vencendo = Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencendo() == null ? "0.00" : webPedido.getCadastro().getFinanceiro_vencendo());
                            } catch (Exception ignored) {}

                            if (vencido > 0 || vencendo > 0) {
                                DialogHelper.showDebtorAlertaDialog(ActivityPedidoEnviado.this, vencido, vencendo, 
                                        "O cliente possui pendências financeiras registradas. Deseja prosseguir com a DUPLICAÇÃO do pedido mesmo assim?", () -> {
                                    executarDuplicacao(webPedido);
                                });
                            } else {
                                executarDuplicacao(webPedido);
                            }
                        } else if (id == R.id.btnCompartilhar || id == -500) {
                            showProgressDialogCustom("Preparando compartilhamento...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                indAcao = 3;
                                List<WebPedido> lista = new ArrayList<>();
                                lista.add(webPedido);
                                criaPdf(lista, indAcao);
                            }, 200);
                        } else if (id == -600) {
                            showProgressDialogCustom("Gerando arquivo PDF, aguarde...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                indAcao = 1;
                                List<WebPedido> lista = new ArrayList<>();
                                lista.add(webPedido);
                                criaPdf(lista, indAcao);
                            }, 200);
                        } else if (id == -700) {
                            analisaPedidos();
                        }
                    }
                    @Override public void onNeutral() {}
                },
                getPopulatedSummaryView(webPedido, false)
        );
    }

    public void showActionDialog(ActionMode mode, final int id ,List<WebPedido> pedidosSelecionados, String mensagem) {
        cliqueAtivo = false;
        View bodyView = (pedidosSelecionados.size() == 1) ? getPopulatedSummaryView(pedidosSelecionados.get(0), false) : null;
        
        Integer iconConfirm = null;
        if (id == R.id.action_pdf_pedido) {
            iconConfirm = R.drawable.ic_pdf_pedido;
        } else if (id == R.id.action_duplica_pedido) {
            iconConfirm = R.drawable.ic_duplica_pedido;
        }

        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", iconConfirm,
                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override public void onPositive() {
                        try { if (mode != null) mode.finish(); } catch (NullPointerException e) { e.printStackTrace(); }
                        cliqueAtivo = true;
                    }
                    @Override public void onNegative() {
                        if (id == R.id.action_pdf_pedido) {
                            showProgressDialogCustom("Gerando arquivo PDF, aguarde...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                indAcao = 1;
                                criaPdf(pedidosSelecionados, indAcao);
                                cliqueAtivo = true;
                            }, 200);
                        } else if (id == R.id.action_pdf_pedido_email) {
                            showProgressDialogCustom("Preparando e-mail com PDF...");
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                indAcao = 2;
                                criaPdf(pedidosSelecionados, indAcao);
                                cliqueAtivo = true;
                            }, 200);
                        } else if (id == R.id.action_duplica_pedido) {
                            // QUESTÃO: VERIFICAÇÃO DE DÉBITO NA DUPLICAÇÃO (ENVIADOS - ACTION MODE)
                            WebPedido pedidoParaDuplicar = pedidosSelecionados.get(0);
                            float vencido = 0.00f;
                            float vencendo = 0.00f;
                            try {
                                vencido = Float.parseFloat(pedidoParaDuplicar.getCadastro().getFinanceiro_vencido() == null ? "0.00" : pedidoParaDuplicar.getCadastro().getFinanceiro_vencido());
                                vencendo = Float.parseFloat(pedidoParaDuplicar.getCadastro().getFinanceiro_vencendo() == null ? "0.00" : pedidoParaDuplicar.getCadastro().getFinanceiro_vencendo());
                            } catch (Exception ignored) {}

                            if (vencido > 0 || vencendo > 0) {
                                DialogHelper.showDebtorAlertaDialog(ActivityPedidoEnviado.this, vencido, vencendo, 
                                        "O cliente possui pendências financeiras registradas. Deseja prosseguir com a DUPLICAÇÃO do pedido mesmo assim?", () -> {
                                    executarDuplicacao(mode, pedidoParaDuplicar);
                                });
                            } else {
                                executarDuplicacao(mode, pedidoParaDuplicar);
                            }
                        }
                    }
                    @Override public void onNeutral() {}
                },
                bodyView
        );
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, boolean fimActivity, Integer iconId) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                this,
                sucesso ? "Sucesso" : "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                iconId,
                null, // Passando a cor personalizada
                () -> {
                    System.gc();
                }
        );
    }

    public void showActionDialog( String mensagem, String erro) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {

                    }
                    @Override
                    public void onNegative() {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }
}


