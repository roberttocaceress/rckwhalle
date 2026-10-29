package com.example.rcksuporte05.rcksistemas.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.LogSistemaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterPedido;
import com.example.rcksuporte05.rcksistemas.adapters_.TabsAdapterPedido_;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido0;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.LogSistema;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityPedidoMain extends AppCompatActivity {

    public static ActionMode actionMode;
    private static Cliente objetoCliente = null;
    @BindView(R.id.toolbarFragsPedido)
    public Toolbar toolbar;
    @BindView(R.id.txtNomeCliente)
    public TextView txtNomeCliente;
    @BindView(R.id.txtNomeFantasia)
    public TextView txtNomeFantasia;
    @BindView(R.id.stl_tabsPedido)
    SlidingTabLayout stl_tabsPedido;
    @BindView(R.id.vp_tabsPedido)

    ViewPager mViewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnBuscaCliente)
    public Button btnBuscaCliente;
    @BindView(R.id.ifCliente)
    LinearLayout ifCliente;

    @BindView(R.id.lyHeaderCliente)
    RelativeLayout lyHeaderCliente;


    @BindView(R.id.btnBuscaHistoricoFinanceiro)
    Button btnBuscaHistoricoFinanceiro;
    @BindView(R.id.ifHistoricoFinanceiro)
    LinearLayout ifHistoricoFinanceiro;

    @BindView(R.id.lyhistoricoFinanceiro)
    RelativeLayout lyhistoricoFinanceiro;

    @BindView(R.id.lyvalorIcmsST)
    LinearLayout lyvalorIcmsST;

    //@BindView(R.id.btnMenuProduto)
    //Button btnMenuProduto;






    ActionModeCallback actionModeCallback;
    private PedidoHelper pedidoHelper;
    private TabsAdapterPedido_ tabsAdapterPedido;
    private int vizualizacao;
    private DBHelper db = new DBHelper(this);
    private Bundle bundle = new Bundle();
    private WebPedido webPedido;
    private Pedido1 pedido1 = new Pedido1();
    private Pedido0 pedido0 = new Pedido0();
    private HashMap<Integer, Categoria> listaCategoria;
    private CategoriaDAO categoriaDAO;
    private WebPedidoDAO webPedidoDAO;
    public static boolean pedidoEnviado = false;

    public boolean showListaProduto = false;
    private Configuracao configuracao = new Configuracao();

    //private ConfiguracaoDAO configuracaoDAO;
    Utilitaria util = new Utilitaria();
    private boolean alerta = true;


    @OnClick(R.id.btnBuscaCliente)
    public void buscaCliente() {
        if (vizualizacao != 1) {
            showCliente();
        }
    }

    @OnClick(R.id.ifCliente)
    public void nomeCliente() {
        if (vizualizacao != 1) {
            showCliente();
        }
    }


    @OnClick(R.id.btnBuscaHistoricoFinanceiro)
    public void buscaHistoricoFinanceiro() {
        if (vizualizacao != 1)
            showHistoricoFinanceiro();
    }

    @OnClick(R.id.ifHistoricoFinanceiro)
    public void financeiroCliente() {
        if (vizualizacao != 1)
            showHistoricoFinanceiro();
    }



    private void showHistoricoFinanceiro() {
        try {
            int id = webPedido.getCadastro().getId_cadastro_servidor();
            String nome = webPedido.getCadastro().getNome_cadastro();
            try {
                if (id > 0)
                    startActivity(
                            new Intent(ActivityPedidoMain.this, ActivityHistoricoFinanceiroTKS.class)
                                    .putExtra("idCliente", String.valueOf(id))
                                    .putExtra("nomeCliente", nome)
                    );
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch ( NullPointerException e){
        } catch ( Exception e) {
        }
    }

    private void showCliente() {
        //Toast.makeText(getApplication().getApplicationContext(),  UsuarioHelper.getUsuario().getUsuario_altera_cliente_ped(), Toast.LENGTH_SHORT).show();
        try {
            if (webPedido.getCadastro() == null || UsuarioHelper.getUsuario().getUsuario_altera_cliente_ped().equalsIgnoreCase("S")) {
                showProduto(false);
                bundle = new Bundle();
                bundle.putInt("acao", 1);
                Intent intent = new Intent(this, ActivityClienteDrawer.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            } else {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content),"Alteração do cliente não é permitida!", R.drawable.ic_alerta_mensagem);
                return;
            }
        } catch (NullPointerException e) {
        } catch ( Exception e) {
        }
        showProduto(false);
        bundle = new Bundle();
        bundle.putInt("acao", 1);
        Intent intent = new Intent(this, ActivityClienteDrawer.class);
        intent.putExtras(bundle);
        startActivity(intent);
        //startActivityForResult(intent, 100);

    }
    private void showHideLayoutIcmsST(){
        //ViewGroup.LayoutParams params = lyvalorIcmsST.getLayoutParams();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyvalorIcmsST.getLayoutParams();
        params.weight = 0;
        params.height = 0;
        params.width = 0;
        lyvalorIcmsST.setLayoutParams(params);
        /* LÓGICA ANTERIOR (CONSULTA REDUNDANTE) COMENTADA:
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        */
        try {
            if ( configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S")){
                params.height =  LinearLayout.LayoutParams.WRAP_CONTENT;
                params.width  =  LinearLayout.LayoutParams.WRAP_CONTENT;
                params.weight = 1;
                lyvalorIcmsST.setLayoutParams(params);
            }
        } catch ( NullPointerException e){
        } catch ( Exception e) {
        }
    }

    private void showHideLayoutFinanceiro(){
        if (lyhistoricoFinanceiro != null) {
            lyhistoricoFinanceiro.setVisibility(View.GONE);
        }
        stl_tabsPedido.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // AJUSTE DE SEGURANÇA EM 22/05/2024 - TRATAMENTO DE PROCESS DEATH (MORTE DO PROCESSO)
        // SE O SISTEMA TENTAR RESTAURAR A ACTIVITY MAS AS VARIÁVEIS ESTÁTICAS FOREM LIMPAS, 
        // ABORTAMOS A RESTAURAÇÃO PARA EVITAR CRASH NOS FRAGMENTOS (PEDIDO0, 1, 2)
        if (savedInstanceState != null && PedidoHelper.getActivityPedidoMain() == null) {
            super.onCreate(null);
            showAnimationFeedbackDialog("A sessão de venda foi interrompida pelo sistema Android para liberar memória (geralmente devido à inatividade prolongada ou ao excesso de aplicativos abertos simultaneamente). Para sua segurança, você será redirecionado ao menu principal. Caso este pedido já possua itens lançados, você poderá continuar a edição através da opção 'Pedidos Pendentes'.", false, () -> {
                Intent intent = new Intent(this, ActivityPrincipal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= 35) {
            androidx.activity.EdgeToEdge.enable(this);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pedido_main_2025);
        // 2. AJUSTE DINÂMICO DE BARRAS (Funciona em TODAS as versões)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_cadastro_cliente_main), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.toolbarFragsPedido);
            toolbar.setPadding(0, insets.top, 0, 0);

            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.layout_totais_rodape);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        getConfiguracao();
        try {
            if (configuracao.getGira_tela_pedido().isEmpty() || configuracao.getGira_tela_pedido().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //db = new DBHelper(this);
        showHideLayoutFinanceiro();
        showHideLayoutIcmsST();
        pedidoHelper = new PedidoHelper(this);
        vizualizacao = getIntent().getIntExtra("vizualizacao", 0);
        pedidoEnviado = false;
        try {
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain.this));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain.this));
        } catch ( Exception e) {
        }

        tabsAdapterPedido = new TabsAdapterPedido_(getSupportFragmentManager(), ActivityPedidoMain.this, UsuarioHelper.getUsuario(), vizualizacao);
        mViewPager.setAdapter(tabsAdapterPedido);

        stl_tabsPedido.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        stl_tabsPedido.setSelectedIndicatorColors(Color.YELLOW);
        stl_tabsPedido.setViewPager(mViewPager);

        // MANTÉM OS CABEÇALHOS PERMANENTEMENTE OCULTOS (GONE) CONFORME SOLICITADO
        if (lyHeaderCliente != null) lyHeaderCliente.setVisibility(View.GONE);
        if (lyhistoricoFinanceiro != null) lyhistoricoFinanceiro.setVisibility(View.GONE);

        if (vizualizacao == 1) {
            toolbar.setTitle("Vizualização de Pedido");
            txtNomeCliente.setFocusable(false);
            this.setTheme(R.style.Theme_MeuTemaPedido);
            pedidoEnviado = true;
        } else if (pedidoHelper.getIdPedido() > 0) {
            toolbar.setTitle("Alteração do pedido");
        } else {
            toolbar.setTitle("Lançamento de Pedido");
            if (ClienteHelper.getCliente() != null) {
                if (ClienteHelper.getCliente().getId_cadastro() == 0)
                    toolbar.setTitle("Pré-pedido");

                if (PedidoHelper.getListaWebPedidoItens() != null && PedidoHelper.getListaWebPedidoItens().size() > 0) {
                    System.out.println("pedido duplicado!");
                } else {
                    //pedidoHelper.salvaPedidoParcial();
                    //objetoCliente = ClienteHelper.getCliente();
                    //mViewPager.setCurrentItem(1);
                    //mViewPager.setCurrentItem(2);
                    //mViewPager.setCurrentItem(0);
                    objetoCliente = ClienteHelper.getCliente();

                    Intent intent = new Intent(this, ActivityProdutoDrawer.class);
                    intent.putExtra("acao", 1);
                    intent.putExtra("idPedido", PedidoHelper.getIdPedido());
                    intent.putExtra("idCliente", ClienteHelper.getCliente().getId_cadastro());
                    startActivity(intent);


                }
            } else {
                objetoCliente = null;
                btnBuscaCliente.callOnClick();
            }

        }

        categoriaDAO = new CategoriaDAO(db);
        webPedidoDAO = new WebPedidoDAO(db);
        try {
            listaCategoria = categoriaDAO.listaHashCategoria();
        } catch (CursorIndexOutOfBoundsException e) {
        }

        if (PedidoHelper.getIdPedido() > 0) {

            List<WebPedido> lista = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido() + ";");
            if (lista.isEmpty()) {
                showFeedbackDialog("Os dados do pedido não foram localizados. A operação foi encerrada para garantir a integridade das informações.", false, () -> {
                    PedidoHelper.setIdPedido(0);
                    finish();
                });
                return;
            }
            webPedido = lista.get(0);
            objetoCliente = webPedido.getCadastro();
            ClienteHelper.setCliente(objetoCliente);
            if (webPedido.getId_web_pedido_servidor() != null)
                toolbar.setSubtitle("Pedido: " + webPedido.getId_web_pedido_servidor());
            else {
                if (ClienteHelper.getCliente().getId_cadastro() == 0)
                    toolbar.setSubtitle("Pré-pedido: " + webPedido.getId_web_pedido());
                else
                    toolbar.setSubtitle("Pedido: " + webPedido.getId_web_pedido());
            }
        } else if (PedidoHelper.getWebPedido() != null) {
            webPedido = PedidoHelper.getWebPedido();
            objetoCliente = webPedido.getCadastro();
            if ( objetoCliente == null) {
                //Mudei aqui 01/04/2021
                objetoCliente = ClienteHelper.getCliente();
            }
            ClienteHelper.setCliente(objetoCliente);
        } else {
            webPedido = new WebPedido();
            webPedido.setPrazo_confirmado("N");
        }

        if (objetoCliente != null)
            try {
                txtNomeCliente.setText(objetoCliente.getNome_cadastro().trim());
                txtNomeFantasia.setText(objetoCliente.getNome_fantasia().trim());
                txtNomeCliente.setTextColor(Color.WHITE);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        actionModeCallback = new ActionModeCallback();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mViewPager.getCurrentItem() == 0) {
            if (objetoCliente == null ) {
                if (vizualizacao != 1) {
                    pedidoHelper.salvaPedidoParcial();
                    salvaPedido();

                }
            }
        }


    }

    @Override
    protected void onResume() {
        try {
            //List<WebPedidoItens> lista = PedidoHelper.getListaWebPedidoItens();
            //boolean isCliente = verificaCliente();
            //String textox = txtNomeCliente.getText().toString();
            if ( (PedidoHelper.getListaWebPedidoItens() == null || PedidoHelper.getListaWebPedidoItens().size() <= 0) && verificaCliente() && txtNomeCliente.getText().toString().equals("Toque aqui para selecionar o seu cliente")) {
                Intent intent = new Intent(this, ActivityProdutoDrawer.class);
                intent.putExtra("acao", 1);
                intent.putExtra("idPedido", PedidoHelper.getIdPedido());
                intent.putExtra("idCliente", ClienteHelper.getCliente().getId_cadastro());
                startActivity(intent);
            }
            txtNomeCliente.setTextColor(Color.WHITE);
            txtNomeCliente.setText(objetoCliente.getNome_cadastro().trim());
            txtNomeFantasia.setText(objetoCliente.getNome_fantasia().trim());
            txtNomeFantasia.setVisibility(View.VISIBLE);
            ifCliente.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
        } catch (NullPointerException e) {
            if ( objetoCliente != null) {
                if (verificaCliente()) {
                    if (vizualizacao != 1) {


                    }
                }
                e.printStackTrace();
            }
        }
        if (verificaCliente()) {
            if (vizualizacao != 1) {
                pedidoHelper.salvaPedidoParcial();
                objetoCliente = ClienteHelper.getCliente();
                if (ClienteHelper.getCliente() != null) {
                    if (ClienteHelper.getCliente().getId_cadastro() == 0)
                        toolbar.setTitle("Pré-pedido");
                } else {
                    objetoCliente = null;
                    btnBuscaCliente.callOnClick();
                }
            }
        }
        super.onResume();
    }
    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    public WebPedido salvaPedido() {
        if (vizualizacao != 1) {
            if ( webPedido.getCadastro() != null) {
                try {
                    if (webPedido.getCadastro().getId_cadastro_servidor() > 0 && webPedido.getCadastro().getId_cadastro_servidor() != objetoCliente.getId_cadastro_servidor()) {
                        showFeedbackDialog("Cliente alterado com sucesso para\n" + objetoCliente.getNome_cadastro() + "!", true);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            webPedido.setCadastro(objetoCliente);
            /*
            if ( webPedido.getCadastro().getId_cadastro_servidor() > 0 && webPedido.getCadastro().getId_cadastro_servidor() == objetoCliente.getId_cadastro_servidor())  {
                webPedido.setCadastro(objetoCliente);
            } else if (webPedido.getCadastro().getId_cadastro_servidor() == -1 && objetoCliente.getId_cadastro_servidor() == 1){
                webPedido.setCadastro(objetoCliente);
            } else if (webPedido.getCadastro() == null){
                webPedido.setCadastro(objetoCliente);
            } else {
                webPedido.setCadastro(objetoCliente);
                showMsgClienteAlterado("Cliente alterado com sucesso!");
            }

             */
            try {
                if (UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                    UsuarioBO usuarioBO = new UsuarioBO();
                    UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain.this));
                }
                setDadosVendedor();
            } catch (NullPointerException e) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain.this));
                setDadosVendedor();
            } catch ( Exception e) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain.this));
                setDadosVendedor();
            }
            webPedido.setExcluido("N");
            webPedido.setStatus("A");
            webPedido.setPedido_enviado("N");
        }
        return webPedido;
    }

    private void setDadosVendedor() {
        try {
            if (getIntent().hasExtra("id_empresa")) {
                String idEmpresa = getIntent().getStringExtra("id_empresa");
                if (idEmpresa != null && !idEmpresa.isEmpty()) {
                    webPedido.setId_empresa(idEmpresa);
                }
            }
            if (getIntent().hasExtra("id_visita_app")) {
                Object extraVisita = getIntent().getExtras() != null ? getIntent().getExtras().get("id_visita_app") : null;
                if (extraVisita != null) {
                    String idVisitaApp = String.valueOf(extraVisita);
                    if (!idVisitaApp.trim().isEmpty() && !"0".equals(idVisitaApp.trim())) {
                        webPedido.setId_visita_app(idVisitaApp);
                    }
                }
            }
            if (getIntent().hasExtra("id_analise_app")) {
                String idAnaliseApp = getIntent().getStringExtra("id_analise_app");
                if (idAnaliseApp != null && !idAnaliseApp.isEmpty()) {
                    webPedido.setId_analise_app(idAnaliseApp);
                }
            }

            if (webPedido.getId_empresa() == null || webPedido.getId_empresa().isEmpty()) {
                webPedido.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
            }
            webPedido.setId_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
            webPedido.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
            webPedido.setUsuario_lancamento_nome(UsuarioHelper.getUsuario().getNome_usuario());
            webPedido.setId_outro_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (vizualizacao != 1) {
            if (mViewPager.getCurrentItem() != 0)
                mViewPager.setCurrentItem(0);
            else {
                try {
                    showActionDialog("Tem certeza que deseja sair do pedido? "/* + webPedido.getCadastro().getNome_cadastro() + "?"*/ + "As informações não salvas serão perdidas!!");
                } catch ( NullPointerException e) {
                    showActionDialog("Tem certeza que deseja do pedido? As informações não salvas serão perdidas!!");
                }
            }
        }else {
            pedidoHelper.limparDados();
            objetoCliente = null;
            HistoricoFinanceiroHelper.limparDados();
            //System.gc();
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //if (getIntent().getIntExtra("vizualizacao", 0) != 1)
        //    getMenuInflater().inflate(R.menu.menu_produto_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar_produto:

                /*if (mViewPager.getCurrentItem() == 0)
                    mViewPager.setCurrentItem(1);
                else {
                    String nomeCadastro = "";
                    try {
                        nomeCadastro = objetoCliente.getNome_cadastro();
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(),"Informe um cliente antes de salvar o pedido", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    ProgressDialog dialog = new ProgressDialog(PedidoHelper.getActivityPedidoMain());
                    dialog.setTitle("Atenção!");
                    dialog.setMessage("Salvando o Pedido");
                    dialog.setCancelable(false);
                    dialog.show();

                    if (pedidoHelper.salvaPedido()) {
                        pedidoHelper.limparDados();
                        objetoCliente = null;
                        HistoricoFinanceiroHelper.limparDados();
                        util.showMsgSucesso("Pedido de " + nomeCadastro + "\nsalvo com sucesso!", ActivityPedidoMain.this);
                    }
                    dialog.dismiss();
                }*/
                break;
            case android.R.id.home:
                if (vizualizacao != 1) {
                    if (mViewPager.getCurrentItem() != 0)
                        mViewPager.setCurrentItem(0);
                    else {
                        try {
                            showActionDialog("Tem certeza que deseja sair do pedido?" /*+ webPedido.getCadastro().getNome_cadastro() + "?"*/ + " As informações não salvas serão perdidas!!");
                        } catch ( NullPointerException e) {
                            showActionDialog("Tem certeza que deseja do pedido? As informações não salvas serão perdidas!!");
                        }
                    }
                }else {
                    pedidoHelper.limparDados();
                    objetoCliente = null;
                    HistoricoFinanceiroHelper.limparDados();
                    //System.gc();
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pegaCliente(Cliente cliente) {
        objetoCliente = cliente;
        ClienteHelper.setCliente(cliente);
    }

    public void showProduto(boolean showLista) {
        showListaProduto = showLista;

    }

    public void showProduto(Cliente cliente) {
        onResume();
        Intent intent = new Intent(this, ActivityProdutoDrawer.class);
        intent.putExtra("acao", 1);
        intent.putExtra("idPedido", PedidoHelper.getIdPedido());
        intent.putExtra("idCliente", cliente.getId_cadastro());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        /*
        pedidoHelper.limparDados();
        objetoCliente = null;
        HistoricoFinanceiroHelper.limparDados();

         */
        //System.gc();
        super.onDestroy();
    }

    public boolean verificaCliente() {
        return objetoCliente != null;
    }

    public boolean isPedidoEnviado() {
        return  pedidoEnviado;
    }

    public void updatePedido() {
        if (vizualizacao != 1)
            pedidoHelper.salvaPedidoParcial();
    }



    public void enableActionMode(int position) {
        if (actionMode == null)
            actionMode = startSupportActionMode(actionModeCallback);
        pedido1.toggleSelection(position);
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode_produtos, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(ActivityPedidoMain.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoMain.this);
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                    textView.setText("Tem certeza que deseja excluir o(s) produto(s) selecionado(s)");
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
                            alertDialog.dismiss();
                            excluiProdutos( mode, pedido1.getAdapter().getItensSelecionados());
                        }
                    });
                    alertDialog.show();
                    return true;
            }
            return false;
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            Pedido1.getAdapter().clearSelections();
            actionMode = null;
        }
    }

    public void excluiProdutos(ActionMode mode, List<WebPedidoItens> lista) {
        if ( !pedido1.removeProdutos(lista) )
            DialogHelper.showSnackInfo(findViewById(android.R.id.content),"Falha ao excluir o(s) produto(s)! Tente novamente", R.drawable.ic_baseline_error_outline_24);
        //Toast.makeText( ActivityPedidoMain.this, "Falha ao excluir o(s) produto(s)! Tente novamente", Toast.LENGTH_SHORT).show();
        pedidoHelper.salvaPedidoParcial();
        try {
            mode.finish();
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onResume();
    }

    /*
        public void showMsgSimNao( String mensagem) {
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(ActivityPedidoMain.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoMain.this);
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
                    pedidoHelper.limparDados();
                    objetoCliente = null;
                    HistoricoFinanceiroHelper.limparDados();

                    System.gc();
                    finish();
                }

            });

            alertDialog.show();
        }
    */
    public void showMsgClienteAlterado( String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoMain.this).inflate(R.layout.dialog_sucesso_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoMain.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }

        });

        alertDialog.show();
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso) {
        showFeedbackDialog(mensagem, sucesso, null);
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, Runnable onConfirm) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                this,
                "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_check_circle : R.drawable.ic_baseline_error_outline_24,
                null, // Passando a cor personalizada
                () -> {
                    if (onConfirm != null) onConfirm.run();
                    System.gc();
                }
        );
    }


    public void showAnimationFeedbackDialog(String mensagem, boolean sucesso, Runnable onConfirm) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showAnimationFeedbackDialog(
                this,
                "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_check_circle : R.drawable.ic_memory_alert,
                null, // Passando a cor personalizada
                () -> {
                    if (onConfirm != null) onConfirm.run();
                    //System.gc();
                }
        );
    }

    public void showActionDialog(String mensagem ) {
        // NOVO: Diálogo com Resumo Detalhado ao tentar sair
        DialogHelper.showActionDialog(
                this,
                mensagem,
                "NÂO", null,
                "SIM", null,
                null, R.drawable.ic_help_new,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {
                    }
                    @Override
                    public void onNegative() {
                        // GRAVAÇÃO DO LOG DE ABANDONO DE PEDIDO
                        try {
                            LogSistema log = new LogSistema();
                            if (webPedido != null) {
                                log.setIdPedido(webPedido.getId_web_pedido());
                                log.setIdPedidoApp(webPedido.getId_pedido_app());
                                log.setIdWebPedidoServidor(webPedido.getId_web_pedido_servidor());
                                log.setIdVendedor(webPedido.getId_vendedor());
                                log.setValorAnterior("Total Itens: " + webPedido.getQtde_itens() + " | Valor: " + webPedido.getValor_total());
                            }
                            log.setTipoLog("AUDITORIA");
                            log.setEvento("ABANDONAR_PEDIDO");
                            log.setClasse("ActivityPedidoMain");
                            log.setMetodo("showActionDialog.onNegative");
                            log.setValorNovo("PEDIDO CANCELADO PELO USUÁRIO AO VOLTAR");
                            new LogSistemaDAO(db).add(log);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        pedidoHelper.limparDados();
                        objetoCliente = null;
                        HistoricoFinanceiroHelper.limparDados();
                        //System.gc();
                        finish();
                    }
                    @Override
                    public void onNeutral() {
                    }
                },
                getPopulatedSummaryView(webPedido, false)
        );

        /*
        // ANTERIOR:
        DialogHelper.showActionDialog(
                this,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,  R.drawable.ic_help_new,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {
                    }
                    @Override
                    public void onNegative() {
                        // GRAVAÇÃO DO LOG DE ABANDONO DE PEDIDO
                        try {
                            LogSistema log = new LogSistema();
                            if (webPedido != null) {
                                log.setIdPedido(webPedido.getId_web_pedido());
                                log.setIdPedidoApp(webPedido.getId_pedido_app());
                                log.setIdWebPedidoServidor(webPedido.getId_web_pedido_servidor());
                                log.setIdVendedor(webPedido.getId_vendedor());
                                log.setValorAnterior("Total Itens: " + webPedido.getQtde_itens() + " | Valor: " + webPedido.getValor_total());
                            }
                            log.setTipoLog("AUDITORIA");
                            log.setEvento("ABANDONAR_PEDIDO");
                            log.setClasse("ActivityPedidoMain");
                            log.setMetodo("showActionDialog.onNegative");
                            log.setValorNovo("PEDIDO CANCELADO PELO USUÁRIO AO VOLTAR");
                            new LogSistemaDAO(db).add(log);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        pedidoHelper.limparDados();
                        objetoCliente = null;
                        HistoricoFinanceiroHelper.limparDados();
                        System.gc();
                        finish();
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
        */
    }

    private View getPopulatedSummaryView(WebPedido pedido, boolean mostrarTotal) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewResumo = inflater.inflate(R.layout.layout_dialog_resumo_pedido, null);

        TextView tvPedidoNum = viewResumo.findViewById(R.id.tvResumoPedidoNum);
        TextView tvCliente = viewResumo.findViewById(R.id.tvResumoCliente);
        TextView tvVendedor = viewResumo.findViewById(R.id.tvResumoVendedor);
        TextView tvEmpresa = viewResumo.findViewById(R.id.tvResumoEmpresa);
        TextView tvData = viewResumo.findViewById(R.id.tvResumoData);
        TextView tvItens = viewResumo.findViewById(R.id.tvResumoItens);
        TextView tvPrazo = viewResumo.findViewById(R.id.tvResumoPrazo);
        TextView tvOperacao = viewResumo.findViewById(R.id.tvResumoOperacao);
        TextView tvTotal = viewResumo.findViewById(R.id.tvResumoTotal);
        View lyTotal = viewResumo.findViewById(R.id.lyResumoTotal);

        if (pedido == null) return viewResumo;

        if (tvPedidoNum != null) {
            String id = (pedido.getId_web_pedido() != null && !pedido.getId_web_pedido().isEmpty()) ? pedido.getId_web_pedido() : "0";
            tvPedidoNum.setText("PEDIDO Nº " + MascaraUtil.numeroZeros(id, 5));
        }

        if (tvCliente != null) {
            if (pedido.getCadastro() != null && pedido.getCadastro().getNome_cadastro() != null)
                tvCliente.setText(pedido.getCadastro().getNome_cadastro().trim());
            else
                tvCliente.setText(pedido.getNome_extenso() != null ? pedido.getNome_extenso().trim() : "Cliente não identificado");
        }

        if (tvVendedor != null) {
            String nomeVend = (pedido.getUsuario_lancamento_nome() != null && !pedido.getUsuario_lancamento_nome().isEmpty()) ? pedido.getUsuario_lancamento_nome() : (UsuarioHelper.getUsuario() != null ? UsuarioHelper.getUsuario().getNome_usuario() : "");
            tvVendedor.setText("Vendedor: " + nomeVend.trim());
        }

        if (tvEmpresa != null) {
            try {
                String idEmpresa = pedido.getId_empresa();
                if (idEmpresa != null && !idEmpresa.isEmpty()) {
                    String razaoSocial = db.consultaDados("SELECT RAZAO_SOCIAL FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + idEmpresa + "';", "RAZAO_SOCIAL");
                    tvEmpresa.setText("Empresa: " + (razaoSocial != null && !razaoSocial.isEmpty() ? razaoSocial.trim() : "Não identificada"));
                } else {
                    tvEmpresa.setText("Empresa: Padrão");
                }
            } catch (Exception e) {
                tvEmpresa.setText("Empresa: Não identificada");
            }
        }

        if (tvData != null) {
            try { tvData.setText(MascaraUtil.formataData(pedido.getData_emissao(), "/")); } catch (Exception e) { tvData.setText(pedido.getData_emissao() != null ? pedido.getData_emissao() : ""); }
        }

        if (tvItens != null) tvItens.setText(pedido.getQtde_itens() != null ? pedido.getQtde_itens() : "0");

        if (tvPrazo != null) {
            try {
                String idCond = pedido.getId_condicao_pagamento();
                String prazo = (idCond != null && !idCond.equals("0")) ? db.consultaDados("SELECT NOME_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + idCond + ";", "NOME_CONDICAO") : "Não informado";
                tvPrazo.setText(prazo != null && !prazo.isEmpty() ? prazo : "Não informado");
            } catch (Exception e) { tvPrazo.setText("Não informado"); }
        }

        if (tvOperacao != null) {
            try {
                String idOp = pedido.getId_operacao();
                String operacao = (idOp != null && !idOp.equals("0")) ? db.consultaDados("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + idOp + ";", "NOME_OPERACAO") : "Não informado";
                tvOperacao.setText(operacao != null && !operacao.isEmpty() ? operacao : "Não informado");
            } catch (Exception e) { tvOperacao.setText("Não informado"); }
        }

        if (tvTotal != null) tvTotal.setText(MascaraUtil.mascaraRealSimbolo(pedido.getValor_total() != null ? pedido.getValor_total() : "0.00"));

        if (lyTotal != null) {
            lyTotal.setVisibility(mostrarTotal ? View.VISIBLE : View.GONE);
        }

        return viewResumo;
    }

    private double cleanMoney(String valor) {
        if (valor == null || valor.isEmpty()) return 0;
        try {
            String v = valor.trim();
            if (v.contains(",")) {
                return Double.parseDouble(v.replace(".", "").replace(",", "."));
            }
            if (v.contains(".") && v.indexOf(".") == v.lastIndexOf(".")) {
                String decimalPart = v.substring(v.lastIndexOf(".") + 1);
                if (decimalPart.equals("0") || decimalPart.equals("00")) {
                    return Double.parseDouble(v.split("\\.")[0]) / 100.0;
                }
                return Double.parseDouble(v);
            }
            String apenasNumeros = v.replace(".", "");
            return Double.parseDouble(apenasNumeros) / 100.0;
        } catch (Exception e) {
            return 0;
        }
    }
    /*
    public void showMsgSucesso(String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoMain.this).inflate(R.layout.dialog_sucesso_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoMain.this);
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
                PedidoHelper.getActivityPedidoMain().finish();
            }
        });
        alertDialog.show();
    }

     */
}
