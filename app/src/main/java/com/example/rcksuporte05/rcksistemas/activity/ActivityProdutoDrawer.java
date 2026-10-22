package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoSkuDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;
import com.example.rcksuporte05.rcksistemas.model.ProdutoParametroSku;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProdutoDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.listaProdutoRecycler)
    RecyclerView recyclerView;

    @BindView(R.id.tb_produto)
    Toolbar toolbar;

    @BindView(R.id.edtTotalProdutos)
    TextView edtTotalProdutos;

    @BindView(R.id.buscaProduto)
    SearchView buscaProduto;

    @BindView(R.id.compradoProduto)
    ImageView compradoProduto;


    @BindView(R.id.helpProduto)
    ImageView helpProduto;

    @BindView(R.id.btnNovoPedido)
    Button btnNovoPedido;

    @BindView(R.id.lytNovoPedido)
    LinearLayout lytNovoPedido;

    //@BindView(R.id.swipe_container)
    //SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.cardFinalizarInclusao)
    LinearLayout cardFinalizarInclusao;
    @BindView(R.id.btnFinalizarInclusao)
    Button btnFinalizarInclusao;
    //btnFinalizarInclusao

    ImageView imageAnexo;

    private Activity context;
    private List<Produto> listaProduto;
    private List<ProdutoSubGrupo> listaProdutoSubGrupo;
    private List<ProdutoLinhaColecao> listaProdutoLinha;
    private DBHelper db = new DBHelper(this);
    private ListaProdutoAdapter listaProdutoAdapter;
    private ActionMode actionMode;
    private ListaProdutoAdapter.ProdutoAdapterListener listenerProduto;
    private ListaProdutoAdapter.ImagemAdapterLister listenerImagem;
    private final LinearLayout.LayoutParams params = null;
    Animation animZoomIn, animZoomOut;
    private int acao = 0;
    private int idPedido = 0;
    private int posRecycleview = 0;
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private PromocaoDAO promocaoDAO = new PromocaoDAO(db);
    private boolean visivel;
    private int idCliente = -1;
    private EmpresaParametro empresaParametro = new EmpresaParametro();
    private Configuracao configuracao = new Configuracao();
    private ItemTouchHelper itemTouchhelper;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int offSet = 0;
    private int totalReg = 0;
    private int limitReg = 50;
    private String SQL;
    PackageInfo pInfo = null;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private static final long DELAY_BUSCA = 500; // 400 milissegundos de delay

    @OnClick(R.id.lytNovoPedido)
    public void novoPedido() {
        try {
            EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
            empresaParametro = empresaParametroDAO.getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO").get(0);
            ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
            Pedido2 pedido2 = new Pedido2();
            Cliente cliente = new Cliente();
            cliente.setIdCategoria(1);
            cliente.setId_cadastro_servidor(0);
            cliente.setId_cadastro(0);
            cliente.setNome_cadastro("CLIENTE NÃO INFORMADO");
            cliente.setNome_fantasia("-");
            cliente.setEndereco_uf(empresaParametro.getEndereco_uf());
            cliente.setTipo_tabela_preco(empresaParametro.getTipo_tabela_preco());
            cliente.setUtiliza_tabela_preco(empresaParametro.getUtiliza_tabela_preco());
            ClienteHelper.setCliente(cliente);
            activityPedidoMain.pegaCliente(ClienteHelper.getCliente());
            pedido2.pegaCliente(ClienteHelper.getCliente());
            startActivity(new Intent(ActivityProdutoDrawer.this, ActivityPedidoMain.class));
            finish();
        } catch (NullPointerException | IndexOutOfBoundsException e) {

        } catch (Exception e) {

        }
    }

    @OnClick(R.id.btnNovoPedido)
    public void btnNovoPedido() {
        novoPedido();
    }

    @OnClick(R.id.btnFinalizarInclusao)
    public void btnFinalizarInclusao() {
        onBackPressed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_produto_drawer);
        navigationView = (NavigationView) findViewById(R.id.navView);
        ViewCompat.setOnApplyWindowInsetsListener(navigationView, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, insets.top, 0, insets.bottom);
            return windowInsets;
        });

        ButterKnife.bind(this);
        toolbar.setTitle("Lista de Produtos");
        getConfiguracao();
        try {
            if (configuracao.getVisualiza_legenda_produto() == null || configuracao.getVisualiza_legenda_produto().equalsIgnoreCase("S")) {
                startActivity(new Intent(ActivityProdutoDrawer.this, ActivityDialogProduto.class));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (configuracao.getGira_tela_produto().isEmpty() || configuracao.getGira_tela_produto().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        try {
            acao = getIntent().getIntExtra("acao", 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null) {
            compradoProduto.setVisibility(View.VISIBLE);
            btnFinalizarInclusao.setVisibility(View.VISIBLE);
        } else {
            compradoProduto.setVisibility(View.INVISIBLE);
            btnFinalizarInclusao.setVisibility(View.INVISIBLE);
            btnFinalizarInclusao.setEnabled(false);
            ViewGroup.LayoutParams layoutParams =  (ViewGroup.LayoutParams)  cardFinalizarInclusao.getLayoutParams();
            ViewGroup.LayoutParams layoutParams1 =  (ViewGroup.LayoutParams)  btnFinalizarInclusao.getLayoutParams();
            layoutParams1.height = 0;
            btnFinalizarInclusao.setLayoutParams(layoutParams1);
            layoutParams.height = 0;
            cardFinalizarInclusao.setLayoutParams(layoutParams);
        }
        if (acao == 1 || acao == 2)
            showHidePrePedido(false);

        setIdPedidoCliente();
        RecyclerView.LayoutManager layoutManagerProduto = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerProduto);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.addOnScrollListener(new ActivityProdutoDrawer.ScrollListener());

        //mSwipeRefreshLayout.setOnRefreshListener(new ActivityProdutoDrawer.RefreshListener());
        setListenerProduto();

        listenerImagem = position -> showResultActivityProdutoImagem(position);
        buscaProduto.setOnQueryTextListener(new QueryTextListener());
        buscaProduto.setIconifiedByDefault(true);

        // BLOQUEIO DE ASPAS SIMPLES (') NO SEARCHVIEW PARA EVITAR CONFLITOS DE SINTAXE E SQL
        try {
            EditText searchEditText = buscaProduto.findViewById(androidx.appcompat.R.id.search_src_text);
            if (searchEditText != null) {
                searchEditText.setFilters(new android.text.InputFilter[]{
                    (source, start, end, dest, dstart, dend) -> {
                        for (int i = start; i < end; i++) {
                            if (source.charAt(i) == '\'') {
                                return ""; // Bloqueia a digitação ou colagem de aspas simples (')
                            }
                        }
                        return null; // Permite os demais caracteres
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'";
            totalReg = produtoDAO.getTotalReg(SQL);
            if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null)
                if ( configuracao.getMarca_produto_ja_comprado() == null || configuracao.getMarca_produto_ja_comprado().isEmpty() || configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("N")) {
                    SQL = "SELECT P.*, TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA,  WP.ID_PRODUTO AS W_ID_PRODUTO," +
                            " CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C, '' AS OUTRAS_COMPRAS" +
                            " FROM TBL_PRODUTO AS P LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA" +
                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO)" +
                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO ";
                } else {
                    SQL = "SELECT P.*, TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA,  WP.ID_PRODUTO AS W_ID_PRODUTO, CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C," +
                            " (SELECT  MAX( WEBP.ID_WEB_PEDIDO_SERVIDOR ) FROM TBL_WEB_PEDIDO AS WEBP" +
                            " INNER JOIN TBL_WEB_PEDIDO_ITENS WEBPI ON WEBPI.ID_PEDIDO = WEBP.ID_WEB_PEDIDO" +
                            " WHERE WEBP.ID_CADASTRO = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "' AND WEBPI.ID_PRODUTO = P.ID_PRODUTO) AS OUTRAS_COMPRAS" +
                            " FROM TBL_PRODUTO AS P LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA" +
                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO)" +
                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO ";
                }
            else
                SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C," +
                        " '' AS OUTRAS_COMPRAS FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' " +
                        "ORDER BY P.NOME_PRODUTO ";
            if ( configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                listaProduto = produtoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet, (acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "");
            else
                listaProduto = produtoDAO.getLista(SQL,(acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "");
            setRecyclerView(listaProduto, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ActivityProdutoDrawer.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));

        if (PedidoHelper.getBuscaProduto() != null && !PedidoHelper.getBuscaProduto().trim().isEmpty()) {
            buscaProduto.setIconified(false);
            buscaProduto.setQuery(PedidoHelper.getBuscaProduto(), true);
        }
        //setSupportActionBar(toolbar);
        if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("G"))
            listaProdutoSubGrupo = produtoDAO.getListaGrupos("SELECT PG.*, ( SELECT COUNT() FROM TBL_PRODUTO WHERE ID_GRUPO = PG.ID AND ( ATIVO = 'S' AND EXCLUIDO <> 'S' ) ) AS TOTAL_PRODUTO_SUB_GRUPO" +
                    " FROM TBL_PRODUTO_GRUPO AS PG WHERE PG.ATIVO = 'S' ORDER BY PG.NOME_GRUPO");
        else if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("S"))
            listaProdutoSubGrupo = produtoDAO.getListaSubGrupo("SELECT PG.*, ( SELECT COUNT() FROM TBL_PRODUTO WHERE ID_SUB_GRUPO = PG.ID AND ( ATIVO = 'S' AND EXCLUIDO <> 'S' ) ) AS TOTAL_PRODUTO_SUB_GRUPO" +
                    " FROM TBL_PRODUTO_SUB_GRUPO AS PG WHERE PG.ATIVO = 'S' ORDER BY PG.NOME_SUB_GRUPO");
        else
            listaProdutoLinha = produtoDAO.getListaLinha("SELECT PL.*, ( SELECT COUNT() FROM TBL_PRODUTO WHERE ID_LINHA_COLECAO = PL.ID_LINHA_COLECAO AND ( ATIVO = 'S' AND EXCLUIDO <> 'S' ) ) AS TOTAL_PRODUTO_LINHA" +
                " FROM TBL_PRODUTO_LINHA_COLECAO AS PL WHERE PL.ATIVO = 'S' ORDER BY PL.NOME_DESCRICAO_LINHA");
        addMenuDrawerItem();
        helpProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ActivityProdutoDrawer.this, ActivityDialogProduto.class));
            }
        });

        compradoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( configuracao.getMarca_produto_ja_comprado() == null || configuracao.getMarca_produto_ja_comprado().isEmpty() || configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("N")) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Aplicativo não configurado para pesquisa baseado nas ultimas compras!", R.drawable.ic_alerta_mensagem);
                } else {
                    if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null) {
                        Toast.makeText(context, "Pesquisa baseado nas últimas compras do cliente sendo executada!", Toast.LENGTH_SHORT).show();
                        SQL = "SELECT P.*, TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA,  WP.ID_PRODUTO AS W_ID_PRODUTO, CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C," +
                                " (SELECT  MAX( WEBP.ID_WEB_PEDIDO_SERVIDOR ) FROM TBL_WEB_PEDIDO AS WEBP" +
                                " INNER JOIN TBL_WEB_PEDIDO_ITENS WEBPI ON WEBPI.ID_PEDIDO = WEBP.ID_WEB_PEDIDO" +
                                " WHERE WEBP.ID_CADASTRO = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "' AND WEBPI.ID_PRODUTO = P.ID_PRODUTO) AS OUTRAS_COMPRAS" +
                                " FROM TBL_PRODUTO AS P LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA" +
                                " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO)" +
                                " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND OUTRAS_COMPRAS  <> '' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO ";
                        if ( configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                            listaProduto = produtoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet, (acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "");
                        else
                            listaProduto = produtoDAO.getLista(SQL,(acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "");
                        totalReg = listaProduto.size();
                        setRecyclerView(listaProduto, 1);
                    } else {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Pesquisa baseado nas últimas compras do cliente ativada apenas na inclusão/edição do pedido!", R.drawable.ic_alerta_mensagem);
                    }
                }

            }
        });

    }

    private void showHidePrePedido(boolean visivel) {
        lytNovoPedido.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
        btnNovoPedido.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
    }

    private void setIdPedidoCliente() { //seta o id do pedido e do cliente
        try {
            idPedido = getIntent().getIntExtra("idPedido", 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            idCliente = getIntent().getIntExtra("idCliente", -1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        try {
            if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("N") || configuracao.getTrabalha_com_pre_pedido().isEmpty())
                showHidePrePedido(false);
        } catch (NullPointerException e) {
            showHidePrePedido(false);
        }
    }

    private class RefreshListener implements  SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            // Refresh items//
            //preencheListaRecycler(controladorList = new ArrayList<>());
            if (acao == 1 || acao == 2){
                //mSwipeRefreshLayout.setRefreshing(false);
                return;
            }
            try {
                if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("N") || configuracao.getTrabalha_com_pre_pedido().isEmpty()) {
                   // mSwipeRefreshLayout.setRefreshing(false);
                    //lytNovoPedido.setVisibility(View.INVISIBLE);
                    return;
                }
            } catch ( NullPointerException e) {
                //mSwipeRefreshLayout.setRefreshing(false);
                return;
            }
            //mSwipeRefreshLayout.setRefreshing(false);
            if (listaProdutoAdapter.getSelectedItensCount() <= 0)
                showHidePrePedido(true);
        }
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //if (acao == 1 || acao == 2)
            //    return;
            try {
                if ((configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("N") || configuracao.getTrabalha_com_pre_pedido().isEmpty()) && configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("N")) {
                    edtTotalProdutos.setText("(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
                    return;
                }
            } catch (NullPointerException e) {
                return;
            }

            if (!recyclerView.canScrollVertically(1)) {
                if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("S"))
                    showHidePrePedido(false);
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S")) {
                    offSet += limitReg;
                    listaProduto.addAll(produtoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet,""));
                    listaProdutoAdapter.notifyDataSetChanged();
                }
            } else {
                if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("S"))
                    if (listaProdutoAdapter.getSelectedItensCount() <= 0) {
                        if (acao == 1 || acao == 2)
                            showHidePrePedido(false);
                        else
                            showHidePrePedido(true);
                    }
            }
            try {
                if (idCliente == 0)
                    edtTotalProdutos.setText( "(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg + "-(Pré-pedido ativo)");
                else
                    edtTotalProdutos.setText( "(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
            } catch (NullPointerException e) {
                edtTotalProdutos.setText( "(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
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

    private void setListenerProduto() {
        if (getIntent().getIntExtra("acao", 0) == 1) {
            listenerProduto = new ListaProdutoAdapter.ProdutoAdapterListener() {
                @Override
                public void onClickListener(int position) {
                    if (!PedidoHelper.isPedido1Valido()) {
                        showAnimationFeedbackDialog("A sessão de venda foi interrompida pelo sistema Android para liberar memória (geralmente devido à inatividade prolongada ou ao excesso de aplicativos abertos simultaneamente). Para sua segurança, você será redirecionado ao menu principal. Caso este pedido já possua itens lançados, você poderá continuar a edição através da opção 'Pedidos Pendentes'.", false, () -> {
                            Intent intent = new Intent(ActivityProdutoDrawer.this, ActivityPrincipal.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        });
                        return;
                    }
                    if (listaProdutoAdapter.getSelectedItensCount() > 0) {
                        Boolean produtoRepetido = false;
                        if (PedidoHelper.getListaWebPedidoItens() != null)
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!", true, false, null);
                                    break;
                                }

                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido)
                                    enableActionMode(position);
                            } else
                                showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido!", true, false, null);
                        } else {
                            if (!produtoRepetido)
                                enableActionMode(position);
                        }
                    } else {
                        final Intent intent = new Intent(ActivityProdutoDrawer.this, ActivityProdutoPedido.class);
                        if (buscaProduto != null) {
                            PedidoHelper.setBuscaProduto(buscaProduto.getQuery().toString());
                            buscaProduto.clearFocus();
                        }
                        Boolean produtoRepetido = false;
                        List<WebPedidoItens> listaTemp = PedidoHelper.getListaWebPedidoItens();
                        if (PedidoHelper.getListaWebPedidoItens() != null) {
                            int i = 0;
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens()) {
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    final int posicao = i;
                                    showActionDialog(posicao, position, webPedidoItens, "O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido, deseja alterá-lo?", intent);
                                    break;
                                    /*
                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(ActivityProdutoDrawer.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProdutoDrawer.this);
                                    builder.setView(dialogView);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCancelable(false);
                                    TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                                    textView.setText("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido, deseja alterá-lo?");

                                    Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
                                    btnNao.setOnClickListener(v -> alertDialog.dismiss());
                                    Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
                                    btnSim.setOnClickListener(v -> {
                                        alertDialog.dismiss();
                                        PedidoHelper.setProduto(null);
                                        webPedidoItens.setSaldo_estoque(listaProdutoAdapter.getItem(position).getSaldo_estoque());
                                        PedidoHelper.setWebPedidoItem(webPedidoItens);
                                        intent.putExtra("pedido", 1);
                                        intent.putExtra("position", posicao);
                                        startActivity(intent);
                                    });
                                    alertDialog.show();
                                    break;

                                     */
                                }
                                i++;
                            }
                        }
                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido) {
                                    if ( configuracao.getVisualiza_ultimo_preco_venda_app().equalsIgnoreCase("S")) {
                                        showUltimoPreco(listaProdutoAdapter.getItem(position),
                                                ClienteHelper.getCliente().getId_cadastro_servidor(), intent, position);
                                    } else {
                                        //Definir aqui  tela onde sera mostrada a data e valor da ultima compra
                                        PedidoHelper.setProduto(listaProdutoAdapter.getItem(position));
                                        startActivityForResult(intent, position);
                                    }
                                }
                            } else
                                showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido", true, false, null);
                        } else {
                            if (!produtoRepetido) {
                                if ( configuracao.getVisualiza_ultimo_preco_venda_app().equalsIgnoreCase("S")) {
                                    showUltimoPreco(listaProdutoAdapter.getItem(position),
                                            ClienteHelper.getCliente().getId_cadastro_servidor(), intent, position);
                                } else {
                                    PedidoHelper.setProduto(listaProdutoAdapter.getItem(position));
                                    startActivityForResult(intent, position);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onLongClickListener(int position) {
                    Boolean produtoRepetido = false;
                    if (PedidoHelper.getListaWebPedidoItens() != null) {
                        for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                            if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                produtoRepetido = true;
                                showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!", true, false, R.drawable.ic_alerta_mensagem);
                                break;
                            }
                    }
                    if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                        if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                            if (!produtoRepetido)
                                enableActionMode(position);
                        } else
                            showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido!!", true, false, null);
                    } else if (!produtoRepetido)
                        enableActionMode(position);
                }
            };
        } else if (getIntent().getIntExtra("acao", 0) == 2) {
            listenerProduto = new ListaProdutoAdapter.ProdutoAdapterListener() {
                @Override
                public void onClickListener(int position) {
                    if (!PedidoHelper.isPedido1Valido()) {
                        showAnimationFeedbackDialog("A sessão de venda foi interrompida pelo sistema Android para liberar memória (geralmente devido à inatividade prolongada ou ao excesso de aplicativos abertos simultaneamente). Para sua segurança, você será redirecionado ao menu principal. Caso este pedido já possua itens lançados, você poderá continuar a edição através da opção 'Pedidos Pendentes'.", false, () -> {
                            Intent intent = new Intent(ActivityProdutoDrawer.this, ActivityPrincipal.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        });
                        return;
                    }
                    if (listaProdutoAdapter.getSelectedItensCount() > 0) {
                        Boolean produtoRepetido = false;
                        if (PedidoHelper.getListaWebPedidoItens() != null)
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!", true, false, null);
                                    break;
                                }

                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido)
                                    enableActionMode(position);
                            } else
                                showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido", true, false, null);
                        } else {
                            if (!produtoRepetido)
                                enableActionMode(position);
                        }
                    } else {
                        if (buscaProduto != null) {
                            PedidoHelper.setBuscaProduto(buscaProduto.getQuery().toString());
                            buscaProduto.clearFocus();
                        }
                        Boolean produtoRepetido = false;
                        if (PedidoHelper.getListaWebPedidoItens() != null)
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!", true, false, null);
                                    break;
                                }
                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido) {
                                    PedidoHelper.setProduto(listaProdutoAdapter.getItem(position));
                                    finish();
                                }
                            } else
                                showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido", true, false, null);
                        } else {
                            if (!produtoRepetido) {
                                PedidoHelper.setProduto(listaProdutoAdapter.getItem(position));
                                finish();
                            }
                        }
                    }
                }

                @Override
                public void onLongClickListener(int position) {
                    Boolean produtoRepetido = false;
                    if (PedidoHelper.getListaWebPedidoItens() != null)
                        for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                            if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                produtoRepetido = true;
                                showFeedbackDialog("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!", true, false, null);
                                break;
                            }
                    if (!produtoRepetido)
                        enableActionMode(position);
                }
            };
        } else {
            listenerProduto = new ListaProdutoAdapter.ProdutoAdapterListener() {
                @Override
                public void onClickListener(int position) {
                    try {
                        Produto produto = listaProdutoAdapter.getItem(position);
                        if (configuracao.getTrabalha_com_unidade_manut_estoque().equalsIgnoreCase("S")) {
                            List<ProdutoParametroSku> lista = new ArrayList<>();
                            ProdutoSkuDAO produtoSkuDAO = new ProdutoSkuDAO(db);
                            lista = produtoSkuDAO.getLista(produto.getId_produto());
                            Promocao promocao = promocaoDAO.getPromocao( produto.getId_promocao());

                            if (lista.size() > 1) {
                                if ( promocao != null) {
                                    try {
                                        showDadosSQKU(lista, produto.getNome_produto().toUpperCase().trim() + "\nPROMOÇÂO\n("
                                                + MascaraUtil.duasCasaDecimal(promocao.getDescontoPerc()) + "%) " + promocao.getNomePromocao().toUpperCase() +
                                                "\n\nCONDIÇÃO DE VENDA(SKU)");
                                    } catch ( NullPointerException|NumberFormatException e){
                                    } catch ( Exception e) {
                                    }
                                } else{
                                    showDadosSQKU(lista, produto.getNome_produto().toUpperCase().trim() + "\nCONDIÇÃO DE VENDA(SKU)");
                                }
                                return;
                            }
                            if ( promocao != null) {
                                showDadosPromocao( promocao, produto.getNome_produto());
                            }
                        }
                        if ( Integer.parseInt(produto.getId_promocao()) > 0) {
                            Promocao promocao = promocaoDAO.getPromocao( produto.getId_promocao());
                            if ( promocao != null) {
                                showDadosPromocao( promocao, produto.getNome_produto());
                            }
                        }
                    } catch (NullPointerException|NumberFormatException e) {
                        e.printStackTrace();
                    }catch ( Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLongClickListener(int position) {
                    enableActionMode(position);
                }
            };
        }
    }

    public void showUltimoPreco(Produto produto, int idCliente, Intent intent, int position) {
        // 1. Validação inicial (Mantida sua lógica)
        if (String.valueOf(idCliente) == null) {
            PedidoHelper.setProduto(produto);
            startActivityForResult(intent, position);
            return;
        }

        String SQL = "SELECT MAX(ITENS.ID_WEB_ITEM_SERVIDOR), PEDIDO.DATA_EMISSAO, ITENS.ID_PEDIDO, " +
                "ITENS.NOME_PRODUTO, ITENS.VALOR_UNITARIO, ITENS.VALOR_DESCONTO_PER, ITENS.QUANTIDADE FROM TBL_WEB_PEDIDO_ITENS AS ITENS INNER JOIN " +
                "TBL_WEB_PEDIDO AS PEDIDO ON (PEDIDO.ID_CADASTRO = " + idCliente + " AND PEDIDO.ID_WEB_PEDIDO = ITENS.ID_PEDIDO) " +
                "WHERE ITENS.ID_PRODUTO = '" + produto.getId_produto() + "'";

        Generico generico = db.pegaUltimoPreco(SQL);

        if (generico == null || generico.getId().equalsIgnoreCase("0")) {
            PedidoHelper.setProduto(produto);
            startActivityForResult(intent, position);
            return;
        }
        DialogHelper.showActionDialogUltPreco(
                context,
                "Ultima compra!",
                "CANCELAR", null,
                "CONTINUAR", null,

                null,  R.drawable.ic_alerta_mensagem_color_primary,
                generico,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {

                    }
                    @Override
                    public void onNegative() {
                        PedidoHelper.setProduto(produto);
                        startActivityForResult(intent, position);
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }

    /**
     * Método auxiliar para aplicar a mesma estética do DialogHelper
     */
    private void formatarBotoesDialogo(AlertDialog dialog) {
        Button btnPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        // Pegamos o container dos botões
        if (btnPos != null && btnPos.getParent() instanceof LinearLayout) {
            LinearLayout container = (LinearLayout) btnPos.getParent();

            // 1. FORÇAR A ORIENTAÇÃO VERTICAL NO CONTAINER
            container.setOrientation(LinearLayout.VERTICAL);
            container.setGravity(Gravity.CENTER_HORIZONTAL);

            // 2. REMOVER O PADDING DO CONTAINER (evita que os botões fiquem "esmagados")
            container.setPadding(0, 0, 0, 0);

            // 3. CONFIGURAR CADA BOTÃO
            // Criamos uma lista com os botões para facilitar o loop
            Button[] botoes = {btnPos, btnNeg};

            for (Button btn : botoes) {
                if (btn != null) {
                    // Definir visibilidade explicitamente
                    btn.setVisibility(View.VISIBLE);

                    // FORÇAR LARGURA TOTAL (MATCH_PARENT)
                    // Isso é o que obriga o empilhamento vertical
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    // Margens (convertendo DP para PX)
                    int marginH = (int) (24 * getResources().getDisplayMetrics().density); // margem lateral
                    int marginV = (int) (6 * getResources().getDisplayMetrics().density);  // margem entre botões
                    lp.setMargins(marginH, marginV, marginH, marginV);

                    btn.setLayoutParams(lp);

                    // Estilo visual igual ao DialogHelper
                    btn.setAllCaps(false); // Texto mais moderno (não tudo maiúsculo)
                }
            }

            // 4. REMOVER ESPAÇOS EM BRANCO (VITAL PARA ANDROID 15)
            // O Android insere Views invisíveis entre os botões, vamos removê-las
            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);
                if (!(child instanceof Button)) {
                    child.setVisibility(View.GONE);
                }
            }

            // 5. AJUSTE DE COR (Opcional, para seguir seu padrão)
            btnPos.setTextColor(getColor(R.color.colorButton));
            btnNeg.setTextColor(Color.GRAY);
        }
    }

    private void showResultActivityProdutoImagem(int position) {
        if (acao > 0 && !PedidoHelper.isPedido1Valido()) {
            showAnimationFeedbackDialog("A sessão de venda foi interrompida pelo sistema Android para liberar memória (geralmente devido à inatividade prolongada ou ao excesso de aplicativos abertos simultaneamente). Para sua segurança, você será redirecionado ao menu principal. Caso este pedido já possua itens lançados, você poderá continuar a edição através da opção 'Pedidos Pendentes'.", false, () -> {
                Intent intent = new Intent(ActivityProdutoDrawer.this, ActivityPrincipal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
            return;
        }
        Intent intent = new Intent(ActivityProdutoDrawer.this, ActivityProdutoImagem.class);
        intent.putExtra("id_produto", listaProdutoAdapter.getItem(position).getId_produto());
        intent.putExtra("nome_produto", listaProdutoAdapter.getItem(position).getNome_produto());
        intent.putExtra("saldo_estoque", listaProdutoAdapter.getItem(position).getSaldo_estoque());
        intent.putExtra("acao", getIntent().getIntExtra("acao", 0));
        intent.putExtra("position", position);
        startActivityForResult(intent, position);
    }

    private void showDadosSQKU(List<ProdutoParametroSku> lista, String nomeProduto){
        String nome[] = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            nome[i] = lista.get(i).getNome() + "  -  " + MascaraUtil.mascaraReal(lista.get(i).getPreco());
        }
        ViewGroup viewGroup = ActivityProdutoDrawer.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityProdutoDrawer.this).inflate(R.layout.dialog_produto_sku, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProdutoDrawer.this);
        TextView title = new TextView(ActivityProdutoDrawer.this);
        title.setText(nomeProduto);
        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(17);
        builder.setCustomTitle(title);

        builder.setItems(nome, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ListView listView = ((AlertDialog) dialogInterface).getListView();
                final ListAdapter originalAdapter = listView.getAdapter();
                listView.setAdapter(new ListAdapter() {

                    @Override
                    public int getCount() {
                        return originalAdapter.getCount();
                    }

                    @Override
                    public Object getItem(int id) {
                        return originalAdapter.getItem(id);
                    }

                    @Override
                    public long getItemId(int id) {
                        return originalAdapter.getItemId(id);
                    }

                    @Override
                    public int getItemViewType(int id) {
                        return originalAdapter.getItemViewType(id);
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = originalAdapter.getView(position, convertView, parent);
                        //view.setBackgroundResource(R.drawable.borda_dialog_view);
                        TextView textView = (TextView) view;
                        //textView.setTextSize(16); set text size programmatically if needed
                        textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 80 /* this is item height */));
                        return view;
                    }

                    @Override
                    public int getViewTypeCount() {
                        return originalAdapter.getViewTypeCount();
                    }

                    @Override
                    public boolean hasStableIds() {
                        return originalAdapter.hasStableIds();
                    }

                    @Override
                    public boolean isEmpty() {
                        return originalAdapter.isEmpty();
                    }

                    @Override
                    public void registerDataSetObserver(DataSetObserver observer) {
                        originalAdapter.registerDataSetObserver(observer);

                    }

                    @Override
                    public void unregisterDataSetObserver(DataSetObserver observer) {
                        originalAdapter.unregisterDataSetObserver(observer);

                    }

                    @Override
                    public boolean areAllItemsEnabled() {
                        return originalAdapter.areAllItemsEnabled();
                    }

                    @Override
                    public boolean isEnabled(int position) {
                        return originalAdapter.isEnabled(position);
                    }

                });
            }
        });
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void showDadosPromocao( Promocao promocao, String nomeProduto){
        String nome[] = new String[1];
        nome[0] =  "(" + MascaraUtil.duasCasaDecimal(promocao.getDescontoPerc()) + "%) " + promocao.getNomePromocao().toUpperCase() ;
        ViewGroup viewGroup = ActivityProdutoDrawer.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityProdutoDrawer.this).inflate(R.layout.dialog_produto_sku, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProdutoDrawer.this);
        TextView title = new TextView(ActivityProdutoDrawer.this);
        title.setText(nomeProduto.toUpperCase()+"\nPROMOÇÃO");
        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(17);
        builder.setCustomTitle(title);
        builder.setItems( nome, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!buscaProduto.isIconified())
                    buscaProduto.setIconified(true);
                else
                    finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class QueryTextListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            try {
                if (query.trim().isEmpty()) {
                    offSet = 0;
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'";
                    totalReg = produtoDAO.getTotalReg(SQL);
                    if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null)
                        if ( configuracao.getMarca_produto_ja_comprado() == null || configuracao.getMarca_produto_ja_comprado().isEmpty() || configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("N")) {
                            SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO," +
                                    " CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C, '' AS OUTRAS_COMPRAS" +
                                      " FROM TBL_PRODUTO AS P " +
                                    " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                    " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                    " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                    " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                    " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                    " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO ";
                        } else {
                            SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO, CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C," +
                                    " (SELECT  MAX( WEBP.ID_WEB_PEDIDO_SERVIDOR ) FROM TBL_WEB_PEDIDO AS WEBP" +
                                    " INNER JOIN TBL_WEB_PEDIDO_ITENS WEBPI ON WEBPI.ID_PEDIDO = WEBP.ID_WEB_PEDIDO" +
                                    " WHERE WEBP.ID_CADASTRO = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "' AND WEBPI.ID_PRODUTO = P.ID_PRODUTO) AS OUTRAS_COMPRAS" +
                                    " FROM TBL_PRODUTO AS P " +
                                    " LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                    " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                    " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                    " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                    " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                    " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO ";
                        }
                        //listaProduto = produtoDAO.getLista(SQL + "LIMIT 30 OFFSET " + offSet );
                    else
                        SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ORDER BY P.NOME_PRODUTO ";
                    if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                        listaProduto = produtoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet,(acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "", configuracao);
                    else
                        listaProduto = produtoDAO.getLista(SQL,(acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "", configuracao);
                    setRecyclerView(listaProduto, 1);
                } else {
                    if (runnable != null) {
                        handler.removeCallbacks(runnable);
                    }
                    runnable = () -> {
                        buscarProdutos(query);
                    };
                    handler.postDelayed(runnable, DELAY_BUSCA);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (buscaProduto != null) {
            buscaProduto.clearFocus();
        }
        try {
            if (requestCode >= 0 && resultCode == RESULT_OK && acao >= 1) {
                if (listaProdutoAdapter.getSelectedItensCount() >= 1) {
                    for (int i = 0; i < listaProdutoAdapter.getSelectedItensCount(); i++)
                        if (listaProdutoAdapter.selectedItems.keyAt(i) >= 0) {
                            listaProdutoAdapter.getItem(listaProdutoAdapter.selectedItems.keyAt(i)).setW_id_produto(listaProdutoAdapter.getItem(listaProdutoAdapter.selectedItems.keyAt(i)).getId_produto());
                            listaProdutoAdapter.notifyDataSetChanged();
                        }
                    actionMode.finish();
                    listaProdutoAdapter.clearSelection();
                    actionMode = null;
                } else {
                    if (db.listaDados("SELECT ID_PRODUTO FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PRODUTO = '" + listaProdutoAdapter.getItem(requestCode).getId_produto() + "' AND ID_PEDIDO = " + PedidoHelper.getIdPedido()).getCount() > 0)
                        listaProdutoAdapter.getItem(requestCode).setW_id_produto(listaProdutoAdapter.getItem(requestCode).getId_produto());
                    listaProdutoAdapter.notifyDataSetChanged();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarProdutos(String query) {
        System.gc();
        //List<Produto> lista = new ArrayList<>();
        if (!query.trim().equals("")) {
            try {
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE (P.NOME_PRODUTO LIKE '%" + query + "%' OR P.ID_PRODUTO LIKE '%" + query + "%' OR P.CODIGO_EM_BARRAS LIKE '%" + query + "%') AND ( P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' )";
                totalReg = produtoDAO.getTotalReg(SQL);
                offSet = 0;
                if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null) {
                    //Cliente webCliente = ClienteHelper.getCliente();
                    if (configuracao.getMarca_produto_ja_comprado() == null || configuracao.getMarca_produto_ja_comprado().isEmpty() || configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("N")) {
                        SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO," +
                                " CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C, '' AS OUTRAS_COMPRAS" +
                                " FROM TBL_PRODUTO AS P " +
                                " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                " WHERE (P.NOME_PRODUTO LIKE '%" + query + "%' OR P.ID_PRODUTO LIKE '%" + query + "%' OR P.CODIGO_EM_BARRAS LIKE '%" + query + "%') AND ( P.ATIVO = 'S' AND EXCLUIDO <> 'S' ) " +
                                " GROUP BY P.ID_PRODUTO ORDER BY P.ATIVO DESC, P.NOME_PRODUTO ";
                    } else {
                        SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO, CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C," +
                                " (SELECT  MAX( WEBP.ID_WEB_PEDIDO_SERVIDOR ) FROM TBL_WEB_PEDIDO AS WEBP" +
                                " INNER JOIN TBL_WEB_PEDIDO_ITENS WEBPI ON WEBPI.ID_PEDIDO = WEBP.ID_WEB_PEDIDO" +
                                " WHERE WEBP.ID_CADASTRO = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "' AND WEBPI.ID_PRODUTO = P.ID_PRODUTO) AS OUTRAS_COMPRAS" +
                                " FROM TBL_PRODUTO AS P " +
                                " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                " WHERE (P.NOME_PRODUTO LIKE '%" + query + "%' OR P.ID_PRODUTO LIKE '%" + query + "%' OR P.CODIGO_EM_BARRAS LIKE '%" + query + "%') AND ( P.ATIVO = 'S' AND EXCLUIDO <> 'S' ) " +
                                " GROUP BY P.ID_PRODUTO ORDER BY P.ATIVO DESC, P.NOME_PRODUTO ";
                    }
                }else {
                    SQL = "SELECT P.*, '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE (P.NOME_PRODUTO LIKE '%" + query + "%' OR P.ID_PRODUTO LIKE '%" + query + "%' OR P.CODIGO_EM_BARRAS LIKE '%" + query + "%') AND ( P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ) ORDER BY P.ATIVO DESC, P.NOME_PRODUTO ";
                }if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S")) {
                    listaProduto = produtoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet, (acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "", configuracao);
                }else {
                    listaProduto = produtoDAO.getLista(SQL, (acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "", configuracao);
                }
                setRecyclerView(listaProduto, 1);
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
                edtTotalProdutos.setText("Nenhum produto encontrado");
            }
        }
        System.gc();
    }

    /*public void setRecyclerView(List<Produto> lista) {
        listaProdutoAdapter = new ListaProdutoAdapter(lista, listenerProduto, listenerImagem, ActivityProdutoDrawer.this, getIntent().getIntExtra("acao", 0));
        recyclerView.setAdapter(listaProdutoAdapter);
        listaProdutoAdapter.notifyDataSetChanged();
        if (idCliente == 0) {
            if (lista.size() > 0)
                edtTotalProdutos.setText("1/" + totalReg + "-(Pré-pedido ativo)");
            else
                edtTotalProdutos.setText("0/" + totalReg + "-(Pré-pedido ativo)");
        }else {
            if (lista.size() > 0)
                edtTotalProdutos.setText("1/" + totalReg);
            else
                edtTotalProdutos.setText("0/" + totalReg);
        }
    }

     */

    public void setRecyclerView(List<Produto> lista, final int numCols) {  //V
        //System.gc();
        int numColsList = 1;
        GridLayoutManager layoutManager;
        int orientation = this.getResources().getConfiguration().orientation;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        listaProdutoAdapter = new ListaProdutoAdapter(lista, listenerProduto, listenerImagem, ActivityProdutoDrawer.this, getIntent().getIntExtra("acao", 0), configuracao);

        if ( configuracao.getGira_tela_produto().equalsIgnoreCase("S")) {
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
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    layoutManager = new GridLayoutManager(this, 2);
                    numColsList = 2;
                    break;
                default:
                    layoutManager = new GridLayoutManager(this, 1);
            }
        }
        // vencida
        //adapter = new ListaClienteAdapter(ActivityClienteDrawer.this, this.lista, listener, 1);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new GridLayoutManager( this, Common.NUM_OF_COL_PORT);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        int finalNumColsList = numColsList;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (listaProdutoAdapter != null) {
                    listaProdutoAdapter.setNumCols(finalNumColsList);
                    switch (listaProdutoAdapter.getItemViewType(i)) {
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

        //recyclerView.addItemDecoration(new SpaceItemDecoration(Common.NUM_OF_SPACE_MESA));
        recyclerView.setAdapter(listaProdutoAdapter);
        listaProdutoAdapter.notifyDataSetChanged();
        if (idCliente == 0) {
            if (!lista.isEmpty())
                edtTotalProdutos.setText("1/" + totalReg + "-(Pré-pedido ativo)");
            else
                edtTotalProdutos.setText("0/" + totalReg + "-(Pré-pedido ativo)");
        }else {
            if (!lista.isEmpty())
                edtTotalProdutos.setText("1/" + totalReg);
            else
                edtTotalProdutos.setText("0/" + totalReg);
        }
        //System.gc();
    }

    public void enableActionMode(final int position) {
        if (actionMode == null) {
            actionMode = startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.menu_action_mode_produtos_pedido, menu);
                    showHidePrePedido(false);
                    if (getIntent().getIntExtra("acao", 0) != 1 && getIntent().getIntExtra("acao", 0) != 2)
                        menu.findItem(R.id.action_continua_pedido).setVisible(false);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_continua_pedido:
                            try {
                                if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Evento bloqueado! ", R.drawable.ic_alerta_mensagem);
                                    actionMode.finish();
                                    listaProdutoAdapter.clearSelection();
                                    listaProdutoAdapter.notifyDataSetChanged();
                                    break;
                                }
                                showActionDialog("Deseja enviar o(s) produtos(s) selecionado(s) para o pedido?", actionMode);
                            } catch (NullPointerException ignored) {
                            }
                            //onDestroyActionMode(actionMode);
                            break;
                            /*
                            ViewGroup viewGroup = ActivityProdutoDrawer.this.findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(ActivityProdutoDrawer.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProdutoDrawer.this);
                            builder.setView(dialogView);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.setCancelable(false);
                            TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                            textView.setText("Deseja enviar o(s) produtos(s) selecionado(s) para o pedido?");
                            Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
                            btnNao.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onDestroyActionMode(actionMode);
                                    alertDialog.dismiss();
                                }
                            });
                            Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
                            btnSim.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    enviarSelecionados();
                                    //onDestroyActionMode(actionMode);
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();
                            //
                            break;

                             */
                        case R.id.action_produto_imagem:
                            if (listaProdutoAdapter.getSelectedItensCount() == 1)
                                sinconizaProdutoFotoId(listaProdutoAdapter.getItem(position).getId_produto(), position);
                            else
                                DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Atenção!! Deve ser selecionado apenas um produto! ", R.drawable.ic_alerta_mensagem);
                                //Toast.makeText(ActivityProdutoDrawer.this, "Atenção!! Deve ser selecionado apenas um produto! ", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    actionMode.finish();
                    listaProdutoAdapter.clearSelection();
                    listaProdutoAdapter.notifyDataSetChanged();
                    try {
                        PedidoHelper.getListaProdutos().clear();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    actionMode = null;
                    if (acao != 1 && acao != 2)
                        try {
                            if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("S"))
                                showHidePrePedido(true);
                            else
                                showHidePrePedido(false);
                        } catch ( NullPointerException e) {
                            showHidePrePedido(false);

                        }

                }
            });
        }
        toggleSelection(position);
    }

    public void toggleSelection(int position) {

        listaProdutoAdapter.toggleSelection(position);
        if (listaProdutoAdapter.getSelectedItensCount() == 0) {
            if (acao != 1 && acao != 2) {
                try {
                    if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("S"))
                        showHidePrePedido(true);
                    else
                        showHidePrePedido(false);
                } catch ( NullPointerException e) {
                    showHidePrePedido(false);
                }
            }
            actionMode.finish();
            actionMode = null;
            listaProdutoAdapter.clearSelection();
            listaProdutoAdapter.notifyDataSetChanged();
            try {
                PedidoHelper.getListaProdutos().clear();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            actionMode.setTitle(String.valueOf(listaProdutoAdapter.getSelectedItensCount()));
            actionMode.invalidate();
        }
    }

    public void sinconizaProdutoFotoId(String idCadastro, final int position) {
        if ( !usuarioIsLogado() )
            return;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Falha ao definir a versão do aplicativo para acesso a conexão!!", Toast.LENGTH_LONG).show();
            return;
        }
        String versaoApp = pInfo.versionName.substring(0, 5);
        getConfiguracao();
        Call<List<ProdutoFoto>> call = getRotasAPI(1, idCadastro, configuracao, versaoApp);
        if ( call == null)
            return;
        Toast toast = Toast.makeText(context, "Aguarde.... \nSincronizando com o servidor!.\nV-APP-API" + versaoApp, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        call.enqueue(new Callback<List<ProdutoFoto>>() {
            @Override
            public void onResponse(Call<List<ProdutoFoto>> call, Response<List<ProdutoFoto>> response) {
                switch (response.code()) {
                    case 200:
                        List<ProdutoFoto> produtoFoto = response.body();
                        if (produtoFoto.size() > 0) {
                            //db.alterar("DELETE FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = " + produtoFoto.get(0).getId_produto());
                            try {
                                db.deleteDados("TBL_PRODUTO_FOTO", "ID_PRODUTO = ?", new String[]{produtoFoto.get(0).getId_produto()});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (ProdutoFoto produto : produtoFoto) {
                                if (produtoDAO.add(produto) <= 0)  //grava foto no registro
                                    Toast.makeText(ActivityProdutoDrawer.this, "Sincronia efetuada com sucesso!! : " + produtoFoto.size()
                                            + " porem não foi possivel atualizar a base de dados no aplicativo! ", Toast.LENGTH_SHORT).show();
                                else if (produto.foto_principal.equalsIgnoreCase("S"))
                                    listaProdutoAdapter.getItem(position).setAnexo_1(produto.getFoto_arquivo());
                                toast.cancel();
                            }
                            Toast.makeText(ActivityProdutoDrawer.this, "Sincronia efetuada com sucesso!! : " + produtoFoto.size()
                                    + " foto(s)/imagem(s) sincronizada(s)  ", Toast.LENGTH_SHORT).show();

                        } else {
                            toast.cancel();
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Nenhuma imagem/foto vinculada ao produto foi localizado!", R.drawable.ic_alerta_mensagem);
                            //Toast.makeText(ActivityProdutoDrawer.this, "Nenhuma imagem/foto vinculada ao produto foi localizado!", Toast.LENGTH_SHORT).show();
                        }
                        actionMode.finish();
                        actionMode = null;
                        break;
                    default:
                        actionMode.finish();
                        actionMode = null;
                        toast.cancel();
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Erro não catalogado: " + response.code(), R.drawable.ic_baseline_error_outline_24);
                        break;
                }
            }


            @Override
            public void onFailure(Call<List<ProdutoFoto>> call, Throwable t) {
                toast.cancel();
                try {
                    if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty())
                        sinconizaProdutoFotoId2(idCadastro, position, versaoApp);
                    else
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.\n" + t.getMessage(), R.drawable.ic_baseline_error_outline_24);
                } catch ( NullPointerException e) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!.\n" + t.getMessage(), R.drawable.ic_baseline_error_outline_24);
                }

            }
        });
    }

    public void sinconizaProdutoFotoId2(String idCadastro, final int position, String versaoApp) {
        Call<List<ProdutoFoto>> call = getRotasAPI(2, idCadastro, configuracao, versaoApp);
        if ( call == null)
            return;
        Toast toast = Toast.makeText(context, "Aguarde.... \nSincronizando com o servidor!!.\nV-APP-API" + versaoApp, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        call.enqueue(new Callback<List<ProdutoFoto>>() {
            @Override
            public void onResponse(Call<List<ProdutoFoto>> call, Response<List<ProdutoFoto>> response) {
                switch (response.code()) {
                    case 200:
                        List<ProdutoFoto> produtoFoto = response.body();
                        if (produtoFoto.size() > 0) {
                            try {
                                db.deleteDados("TBL_PRODUTO_FOTO", "ID_PRODUTO = ?", new String[]{produtoFoto.get(0).getId_produto()});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (ProdutoFoto produto : produtoFoto)
                                if (produtoDAO.add(produto) <= 0)  //grava foto no registro
                                    Toast.makeText(ActivityProdutoDrawer.this, "Sincronia efetuada com sucesso!! : " + produtoFoto.size()
                                            + " porem não foi possivel atualizar a base de dados no aplicativo! ", Toast.LENGTH_SHORT).show();
                                else if (produto.foto_principal.equalsIgnoreCase("S"))
                                    listaProdutoAdapter.getItem(position).setAnexo_1(produto.getFoto_arquivo());
                            toast.cancel();
                            Toast.makeText(ActivityProdutoDrawer.this, "Sincronia efetuada com sucesso!! : " + produtoFoto.size()
                                    + " foto(s)/imagem(s) sincronizada(s)  ", Toast.LENGTH_SHORT).show();

                        } else {
                            toast.cancel();
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Nenhuma imagem/foto vinculada ao produto foi localizado!", R.drawable.ic_alerta_mensagem);
                            //Toast.makeText(ActivityProdutoDrawer.this, "Nenhuma imagem/foto vinculada ao produto foi localizado!", Toast.LENGTH_SHORT).show();
                        }
                        actionMode.finish();
                        actionMode = null;
                        break;
                    default:
                        actionMode.finish();
                        actionMode = null;
                        toast.cancel();
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro não catalogado: " + response.code(), R.drawable.ic_baseline_error_outline_24);
                        break;
                }
            }


            @Override
            public void onFailure(Call<List<ProdutoFoto>> call, Throwable t) {
                toast.cancel();
                DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!.\n" + t.getMessage(), R.drawable.ic_baseline_error_outline_24);
            }
        });
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

    private Call<List<ProdutoFoto>> getRotasAPI(int idRota, String idCadastro, Configuracao configuracao, String versaoApp) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                if ( versaoApp.substring(0,3).equalsIgnoreCase("1.0"))
                    return apiRotas.produtoAnexo(idCadastro, cabecalho);
                else
                    return apiRotas.produtoImagemID(idCadastro, cabecalho);
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
                    apiRotas = Api.buildRetrofit(true);
                    return apiRotas.produtoImagemID(idCadastro, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if(idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                return apiRotas2.produtoImagemID(idCadastro, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp.substring(0, 5) + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();
                    apiRotas2 = Api.buildRetrofit2(true);
                    return apiRotas2.produtoImagemID(idCadastro, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!!\nSelecione o registro e tente novamente!!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!!\nSelecione o registro e tente novamente!!\n"+ e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    /*
    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = ActivityProdutoDrawer.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityProdutoDrawer.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProdutoDrawer.this);
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
    private void enviarSelecionados() {
        if (!PedidoHelper.isPedido1Valido()) {
            showAnimationFeedbackDialog("A sessão de venda foi interrompida pelo sistema Android para liberar memória (geralmente devido à inatividade prolongada ou ao excesso de aplicativos abertos simultaneamente). Para sua segurança, você será redirecionado ao menu principal. Caso este pedido já possua itens lançados, você poderá continuar a edição através da opção 'Pedidos Pendentes'.", false, () -> {
                Intent intent = new Intent(ActivityProdutoDrawer.this, ActivityPrincipal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
            return;
        }
        Intent intent = new Intent(ActivityProdutoDrawer.this, ActivityProdutoPedido.class);
        try {
            if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6")) {
                    int produto_tabela = 0;
                    int produto_sem_tabela = 0;
                    boolean permite_desconto = true;
                    try {
                        Cursor cursor = db.listaDados("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = '" + PedidoHelper.getWebPedido().getId_condicao_pagamento() + "' AND ACEITA_DESCONTO = 'N'");
                        if (cursor.moveToNext())
                            permite_desconto = false;
                    } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
                    } catch (Exception e) {
                    }
                    List<Produto> listaSelecionados = listaProdutoAdapter.getItensSelecionados();
                    for (int i = 0; i < listaSelecionados.size(); i++)
                        try {
                            if (!db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE" +
                                    " ID_PRODUTO = " + listaSelecionados.get(i).getId_produto() + " AND ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco()).get(0).getId_item().isEmpty()) {
                                produto_tabela++;
                            } else {
                                if (!permite_desconto) {
                                    produto_tabela++;
                                }
                                produto_sem_tabela++;
                            }
                        } catch (Exception e) {
                            produto_sem_tabela++;
                            if (!permite_desconto) {
                                produto_tabela++;
                            }
                        }

                    if (!(produto_sem_tabela == listaProdutoAdapter.getSelectedItensCount() || produto_tabela == listaProdutoAdapter.getSelectedItensCount())) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Operação não autorizada!!", R.drawable.ic_alerta_mensagem);
                        return;
                    }
                }
                PedidoHelper.setListaProdutos(listaProdutoAdapter.getItensSelecionados());
                if (buscaProduto != null)
                    PedidoHelper.setBuscaProduto(buscaProduto.getQuery().toString());

            } else {
                boolean permite_desconto = true;
                try {
                    Cursor cursor = db.listaDados("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = '" + PedidoHelper.getWebPedido().getId_condicao_pagamento() + "' AND ACEITA_DESCONTO = 'N'");
                    if (cursor.moveToNext())
                        permite_desconto = false;
                } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<Produto> listaSelecionados = listaProdutoAdapter.getItensSelecionados();
                for (int i = 0; i < listaSelecionados.size(); i++) {
                    if (listaSelecionados.get(i).getProduto_materia_prima().equalsIgnoreCase("S") && !permite_desconto) {
                        //Toast.makeText(ActivityProdutoDrawer.this, "Operação não autorizada!! Produto cadastrado como materia prima, a inclusão so é permitida individualmente!", Toast.LENGTH_SHORT).show();
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Operação não autorizada!! Produto cadastrado como materia prima, a inclusão so é permitida individualmente!", R.drawable.ic_alerta_mensagem);
                        return;
                    }
                    if (listaSelecionados.get(i).getProduto_tercerizacao().equalsIgnoreCase("S") && !permite_desconto) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Operação não autorizada!! Produto cadastrado como terceirizado, a inclusão so é permitida individualmente!", R.drawable.ic_alerta_mensagem);
                        //Toast.makeText(ActivityProdutoDrawer.this, "Operação não autorizada!! Produto cadastrado como terceirizado, a inclusão so é permitida individualmente!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        //float preco = Float.parseFloat(listaSelecionados.get(i).getVenda_preco());
                        if (Float.parseFloat(listaSelecionados.get(i).getVenda_preco()) <= 0.00f) {
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Operação não autorizada!! Produto sem preço pré definido, a inclusão so é permitida individualmente!", R.drawable.ic_alerta_mensagem);
                            //Toast.makeText(ActivityProdutoDrawer.this, "Operação não autorizada!! Produto sem preço pré definido, a inclusão so é permitida individualmente!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch ( NumberFormatException| NullPointerException e) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Operação não autorizada!! Produto sem preço pré definido, a inclusão so é permitida individualmente!", R.drawable.ic_alerta_mensagem);
                        return;
                    }

                }
                PedidoHelper.setListaProdutos(listaProdutoAdapter.getItensSelecionados());
            }
        } catch (Exception e) {
            PedidoHelper.setListaProdutos(listaProdutoAdapter.getItensSelecionados());
            if (buscaProduto != null)
                PedidoHelper.setBuscaProduto(buscaProduto.getQuery().toString());

        }
        startActivityForResult(intent, listaProdutoAdapter.getSelectedItensCount());
    }


    private void addMenuDrawerItem() {
        Menu menu = navigationView.getMenu();
        if ( configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("G")) {
            SubMenu subMenu = menu.addSubMenu(" Grupos ");
            subMenu.add(0, 0, 0, "TODOS OS GRUPOS").setIcon(R.drawable.ic_lista_todos_drawner);
            for (int i = 1; i <= listaProdutoSubGrupo.size(); i++)
                subMenu.add(0, i, i, listaProdutoSubGrupo.get(i - 1).getNome_sub_grupo().toUpperCase()).setIcon(R.drawable.ic_lista_drawner);
            //subMenu.add(0, listaProdutoSubGrupo.size() + 1, listaProdutoSubGrupo.size() + 1, "VOLTAR").setIcon(R.drawable.ic_action_up_arrow_back_white);

        }else if ( configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("S")) {
                SubMenu subMenu = menu.addSubMenu(" Sub-Grupos ");
                subMenu.add(0, 0, 0, "TODOS OS SUB-GRUPOS").setIcon(R.drawable.ic_lista_todos_drawner);
                for (int i = 1; i <= listaProdutoSubGrupo.size(); i++)
                    subMenu.add(0, i, i, listaProdutoSubGrupo.get(i-1).getNome_sub_grupo().toUpperCase()).setIcon(R.drawable.ic_lista_drawner);
           // subMenu.add(0, listaProdutoSubGrupo.size() + 1, listaProdutoSubGrupo.size() + 1, "VOLTAR").setIcon(R.drawable.ic_action_up_arrow_back_white);
        } else {
            SubMenu subMenu = menu.addSubMenu(" Linhas ");
            subMenu.add(0, 0, 0, "TODAS AS LINHAS").setIcon(R.drawable.ic_lista_todos_drawner);
            for (int i = 1; i <= listaProdutoLinha.size(); i++)
                subMenu.add(0, i, i, listaProdutoLinha.get(i-1).getNomeDescricaoLinha().toUpperCase()).setIcon(R.drawable.ic_lista_drawner);
            //subMenu.add(0, listaProdutoLinha.size() + 1, listaProdutoLinha.size() + 1, "VOLTAR").setIcon(R.drawable.ic_action_up_arrow_back_white);
        }
        navigationView.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_produto_itens: {
                onBackPressed();
                break;
            }
            default:
                try {
                    if (item.getItemId() >= 0) {
                        /*
                        Menu drawerMenu = navigationView.getMenu();
                        if ( drawerMenu.size() == item.getItemId() + 1) {
                            drawerLayout.closeDrawers();
                            onBackPressed();
                            return true;
                        }

                         */
                        drawerLayout.closeDrawers();
                        List<Produto> lista = new ArrayList<>();
                        offSet = 0;
                        buscaProduto.setQuery("", false);
                        if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null) {
                            offSet = 0;
                            if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("G")) {
                                if (configuracao.getMarca_produto_ja_comprado() == null || configuracao.getMarca_produto_ja_comprado().isEmpty() || configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("N")) {
                                    SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO," +
                                            " CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C, '' AS OUTRAS_COMPRAS" +
                                            " FROM TBL_PRODUTO AS P " +
                                            " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA  = TI.ID_TABELA " +
                                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS   AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ";
                                } else {
                                    SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO, CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C," +
                                            " (SELECT  MAX( WEBP.ID_WEB_PEDIDO_SERVIDOR ) FROM TBL_WEB_PEDIDO AS WEBP" +
                                            " INNER JOIN TBL_WEB_PEDIDO_ITENS WEBPI ON WEBPI.ID_PEDIDO = WEBP.ID_WEB_PEDIDO" +
                                            " WHERE WEBP.ID_CADASTRO = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "' AND WEBPI.ID_PRODUTO = P.ID_PRODUTO) AS OUTRAS_COMPRAS" +
                                            " FROM TBL_PRODUTO AS P " +
                                            " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA  = TI.ID_TABELA " +
                                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS   AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ";
                                }
                                if (item.getItemId() > 0) {
                                    totalReg = produtoDAO.getTotalReg("SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND ID_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "'");
                                    SQL += "AND P.ID_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO ";
                                }else {
                                    totalReg = produtoDAO.getTotalReg("SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'");
                                    SQL += " GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO";
                                }
                            } else if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("S")) {
                                if (configuracao.getMarca_produto_ja_comprado() == null || configuracao.getMarca_produto_ja_comprado().isEmpty() || configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("N")) {
                                    SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO," +
                                            " CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C, '' AS OUTRAS_COMPRAS" +
                                            " FROM TBL_PRODUTO AS P " +
                                            " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA  = TI.ID_TABELA " +
                                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ";
                                } else {
                                    SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO, CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C," +
                                            " (SELECT  MAX( WEBP.ID_WEB_PEDIDO_SERVIDOR ) FROM TBL_WEB_PEDIDO AS WEBP" +
                                            " INNER JOIN TBL_WEB_PEDIDO_ITENS WEBPI ON WEBPI.ID_PEDIDO = WEBP.ID_WEB_PEDIDO" +
                                            " WHERE WEBP.ID_CADASTRO = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "' AND WEBPI.ID_PRODUTO = P.ID_PRODUTO) AS OUTRAS_COMPRAS" +
                                            " FROM TBL_PRODUTO AS P " +
                                            " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA  = TI.ID_TABELA " +
                                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ";
                                }
                                if (item.getItemId() > 0) {
                                    totalReg = produtoDAO.getTotalReg("SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND ID_SUB_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "'");
                                    SQL += "AND P.ID_SUB_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO " ;
                                }else {
                                    totalReg = produtoDAO.getTotalReg("SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'");
                                    SQL += " GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO";
                                }
                            }else {
                                if (configuracao.getMarca_produto_ja_comprado() == null || configuracao.getMarca_produto_ja_comprado().isEmpty() || configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("N")) {
                                    SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO," +
                                            " CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C, '' AS OUTRAS_COMPRAS" +
                                            " FROM TBL_PRODUTO AS P " +
                                            " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA  = TI.ID_TABELA " +
                                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ";
                                } else {
                                    SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO, CP.ID_CAMPANHA AS ID_CAMPANHA, CC.ID_CAMPANHA AS ID_CAMPANHA_C," +
                                            " (SELECT  MAX( WEBP.ID_WEB_PEDIDO_SERVIDOR ) FROM TBL_WEB_PEDIDO AS WEBP" +
                                            " INNER JOIN TBL_WEB_PEDIDO_ITENS WEBPI ON WEBPI.ID_PEDIDO = WEBP.ID_WEB_PEDIDO" +
                                            " WHERE WEBP.ID_CADASTRO = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "' AND WEBPI.ID_PRODUTO = P.ID_PRODUTO) AS OUTRAS_COMPRAS" +
                                            " FROM TBL_PRODUTO AS P " +
                                            " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA  = TI.ID_TABELA " +
                                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                            " LEFT JOIN TBL_CAMPANHA_COMERCIAL_ITENS AS CP ON ID_PRODUTO_VENDA = P.ID_PRODUTO" +
                                            " LEFT JOIN TBL_CAMPANHA_COM_CLIENTES AS CC ON CC.ID_CLIENTE = '" + ClienteHelper.getCliente().getId_cadastro_servidor() + "'" +
                                            " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ";
                                }
                                if (item.getItemId() > 0) {
                                    totalReg = produtoDAO.getTotalReg("SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND ID_LINHA_COLECAO  = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "'");
                                    SQL += "AND P.ID_LINHA_COLECAO = '" + listaProdutoLinha.get(item.getItemId() - 1).getIdLinhaColecao() + "' GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO ";
                                }else {
                                    totalReg = produtoDAO.getTotalReg("SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'");
                                    SQL += " GROUP BY P.ID_PRODUTO ORDER BY P.NOME_PRODUTO";
                                }
                            }
                        } else {
                            offSet = 0;
                            if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("G")) {
                                if (item.getItemId() > 0) {
                                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND  ID_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "'";
                                    totalReg = produtoDAO.getTotalReg(SQL);
                                    SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND  ID_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "' ORDER BY P.NOME_PRODUTO ";
                                } else {
                                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'";
                                    totalReg = produtoDAO.getTotalReg(SQL);
                                    SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ORDER BY P.NOME_PRODUTO ";
                                }
                            } else if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("S")) {
                                if (item.getItemId() > 0) {
                                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND ID_SUB_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "'";
                                    totalReg = produtoDAO.getTotalReg(SQL);
                                    SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND  ID_SUB_GRUPO = '" + listaProdutoSubGrupo.get(item.getItemId() - 1).getId_grupo() + "' ORDER BY P.NOME_PRODUTO ";
                                }else {
                                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'";
                                    totalReg = produtoDAO.getTotalReg(SQL);
                                    SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ORDER BY P.NOME_PRODUTO ";
                                }
                            } else {
                                if (item.getItemId() > 0) {
                                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND ID_LINHA_COLECAO = '" + listaProdutoLinha.get(item.getItemId() - 1).getIdLinhaColecao() + "'";
                                    totalReg = produtoDAO.getTotalReg(SQL);
                                    SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND ID_LINHA_COLECAO = '" + listaProdutoLinha.get(item.getItemId() - 1).getIdLinhaColecao() + "' ORDER BY P.NOME_PRODUTO ";
                                }else {
                                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S'";
                                    totalReg = produtoDAO.getTotalReg(SQL);
                                    SQL = "SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO, '' AS ID_CAMPANHA, '' AS ID_CAMPANHA_C FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ORDER BY P.NOME_PRODUTO ";
                                }
                            }
                        }
                        if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                            listaProduto = produtoDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet, (acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "");
                        else
                            listaProduto = produtoDAO.getLista(SQL, (acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ? String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor()) : "");
                        setRecyclerView(listaProduto, 1);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }

    public void showAnimationFeedbackDialog(String mensagem, boolean sucesso, Runnable onConfirm) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showAnimationFeedbackDialog(
                this,
                "Atenção",
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_baseline_check_circle_outline_24_color_primary : R.drawable.ic_memory_alert,
                null,
                () -> {
                    if (onConfirm != null) onConfirm.run();
                    System.gc();
                }
        );
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, boolean fimActivity, Integer iconId) {
        showFeedbackDialog(mensagem, sucesso, fimActivity, iconId, null);
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, boolean fimActivity, Integer iconId, Runnable onConfirm) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                this,
                sucesso ? "Sucesso" : "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                iconId != null ? iconId : R.drawable.ic_alerta_mensagem_color_primary,
                null, // Passando a cor personalizada
                () -> {
                    if (onConfirm != null) onConfirm.run();
                    if (fimActivity) finish();
                    System.gc();
                }
        );
    }

    public void showActionDialog(final int posicao , int position, WebPedidoItens webPedidoItens, String mensagem, Intent intent) {
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
                        PedidoHelper.setProduto(null);
                        webPedidoItens.setSaldo_estoque(listaProdutoAdapter.getItem(position).getSaldo_estoque());
                        PedidoHelper.setWebPedidoItem(webPedidoItens);
                        intent.putExtra("pedido", 1);
                        intent.putExtra("position", posicao);
                        startActivity(intent);
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }

    public void showActionDialog(String mensagem,final  ActionMode actionMode) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {
                        actionMode.finish();
                        listaProdutoAdapter.clearSelection();
                        listaProdutoAdapter.notifyDataSetChanged();
                        try {
                            PedidoHelper.getListaProdutos().clear();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        //actionMode = null;
                        if (acao != 1 && acao != 2)
                            try {
                                if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("S"))
                                    showHidePrePedido(true);
                                else
                                    showHidePrePedido(false);
                            } catch ( NullPointerException e) {
                                showHidePrePedido(false);

                            }
                    }
                    @Override
                    public void onNegative() {
                        enviarSelecionados();
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }
}
