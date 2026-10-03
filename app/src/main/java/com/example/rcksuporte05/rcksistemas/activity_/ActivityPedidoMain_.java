package com.example.rcksuporte05.rcksistemas.activity_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityClienteDrawer;
import com.example.rcksuporte05.rcksistemas.activity.ActivityHistoricoFinanceiroTKS;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProdutoDrawer;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterPedido;
import com.example.rcksuporte05.rcksistemas.adapters_.TabsAdapterPedido_;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityPedidoMain_ extends AppCompatActivity {

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
    Button btnBuscaCliente;
    @BindView(R.id.ifCliente)
    LinearLayout ifCliente;


    @BindView(R.id.btnBuscaHistoricoFinanceiro)
    Button btnBuscaHistoricoFinanceiro;
    @BindView(R.id.ifHistoricoFinanceiro)
    LinearLayout ifHistoricoFinanceiro;

    @BindView(R.id.lyhistoricoFinanceiro)
    RelativeLayout lyhistoricoFinanceiro;

    @BindView(R.id.lyvalorIcmsST)
    LinearLayout lyvalorIcmsST;






    ActivityPedidoMain_.ActionModeCallback actionModeCallback;
    private PedidoHelper pedidoHelper;
    private TabsAdapterPedido_ tabsAdapterPedido;
    private int vizualizacao;
    private DBHelper db = new DBHelper(this);
    private Bundle bundle = new Bundle();
    private WebPedido webPedido;
    private Pedido1 pedido1 = new Pedido1();
    private HashMap<Integer, Categoria> listaCategoria;
    private CategoriaDAO categoriaDAO;
    private WebPedidoDAO webPedidoDAO;
    public static boolean pedidoEnviado = false;
    private Configuracao configuracao = new Configuracao();

    //private ConfiguracaoDAO configuracaoDAO;
    Utilitaria util = new Utilitaria();

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
                            new Intent(ActivityPedidoMain_.this, ActivityHistoricoFinanceiroTKS.class)
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
                bundle = new Bundle();
                bundle.putInt("acao", 1);
                Intent intent = new Intent(this, ActivityClienteDrawer.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            } else {
                Toast.makeText(getApplication().getApplicationContext(), "Alteração do cliente não é permitida!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NullPointerException e) {
        } catch ( Exception e) {
        }
        bundle = new Bundle();
        bundle.putInt("acao", 1);
        Intent intent = new Intent(this, ActivityClienteDrawer.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private void showHideLayoutIcmsST(){
        //ViewGroup.LayoutParams params = lyvalorIcmsST.getLayoutParams();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyvalorIcmsST.getLayoutParams();
        params.weight = 0;
        params.height = 0;
        params.width = 0;
        lyvalorIcmsST.setLayoutParams(params);
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyhistoricoFinanceiro.getLayoutParams();
        lyhistoricoFinanceiro.setLayoutParams(params);
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        try {
            if ( configuracao.getVisual_hist_fin_pedido_app().equalsIgnoreCase("S")){
                params.height =  LinearLayout.LayoutParams.WRAP_CONTENT;
                lyhistoricoFinanceiro.setLayoutParams(params);
                stl_tabsPedido.setVisibility(View.VISIBLE);
            }
        } catch ( NullPointerException e){
        } catch ( Exception e) {
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_main_2025);
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
        //pedidoHelper = new PedidoHelper(this);
        //Codigo comentado para fazer o build
        vizualizacao = getIntent().getIntExtra("vizualizacao", 0);
        pedidoEnviado = false;
        try {
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain_.this));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain_.this));
        } catch ( Exception e) {
        }

        tabsAdapterPedido = new TabsAdapterPedido_(getSupportFragmentManager(), ActivityPedidoMain_.this, UsuarioHelper.getUsuario(), vizualizacao);
        mViewPager.setAdapter(tabsAdapterPedido);

        stl_tabsPedido.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        stl_tabsPedido.setSelectedIndicatorColors(Color.RED);
        stl_tabsPedido.setViewPager(mViewPager);

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
                    //System.out.println("pedido duplicado!");
                } else {
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
            webPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido() + ";").get(0);
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

        actionModeCallback = new ActivityPedidoMain_.ActionModeCallback();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mViewPager.getCurrentItem() == 0) {

        }

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
                        showMsgClienteAlterado("Cliente alterado com sucesso para\n" + objetoCliente.getNome_cadastro() + "!");
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
                    UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain_.this));
                }
                setDadosVendedor();
            } catch (NullPointerException e) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain_.this));
                setDadosVendedor();
            } catch ( Exception e) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPedidoMain_.this));
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
            webPedido.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
            webPedido.setId_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
            webPedido.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
            webPedido.setUsuario_lancamento_nome(UsuarioHelper.getUsuario().getNome_usuario());
            webPedido.setId_outro_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        if (vizualizacao != 1) {
            if (mViewPager.getCurrentItem() != 0)
                mViewPager.setCurrentItem(0);
            else {
                try {
                    showMsgSimNao("Tem certeza que deseja sair do pedido de " + webPedido.getCadastro().getNome_cadastro() + "?" + "\nAs informações não salvas serão perdidas!!");
                } catch ( NullPointerException e) {
                    showMsgSimNao("Tem certeza que deseja do pedido? As informações não salvas serão perdidas!!");
                }
            }
        }else {
            pedidoHelper.limparDados();
            objetoCliente = null;
            HistoricoFinanceiroHelper.limparDados();
            System.gc();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("vizualizacao", 0) != 1)
            getMenuInflater().inflate(R.menu.menu_produto_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar_produto:
                if (mViewPager.getCurrentItem() == 0)
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
                        util.showMsgSucesso("Pedido de " + nomeCadastro + "\nsalvo com sucesso!", ActivityPedidoMain_.this);
                    }
                    dialog.dismiss();
                }
                break;
            case android.R.id.home:
                if (vizualizacao != 1) {
                    if (mViewPager.getCurrentItem() != 0)
                        mViewPager.setCurrentItem(0);
                    else {
                        try {
                            showMsgSimNao("Tem certeza que deseja sair do pedido de " + webPedido.getCadastro().getNome_cadastro() + "?" + "\nAs informações não salvas serão perdidas!!");
                        } catch ( NullPointerException e) {
                            showMsgSimNao("Tem certeza que deseja do pedido? As informações não salvas serão perdidas!!");
                        }
                    }
                }else {
                    pedidoHelper.limparDados();
                    objetoCliente = null;
                    HistoricoFinanceiroHelper.limparDados();
                    System.gc();
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

    @Override
    protected void onDestroy() {
        /*
        pedidoHelper.limparDados();
        objetoCliente = null;
        HistoricoFinanceiroHelper.limparDados();

         */
        System.gc();
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

    @Override
    protected void onResume() {
        try {
            if (PedidoHelper.getListaWebPedidoItens().size() <= 0 && verificaCliente() && txtNomeCliente.getText().toString().equals("Toque aqui para selecionar o seu cliente")) {
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

            }
            e.printStackTrace();
        }
        if (verificaCliente()) {
            /*objetoCliente = webPedido.getCadastro();
            if (objetoCliente != null)
                try {
                    txtNomeCliente.setText(objetoCliente.getNome_cadastro());
                    txtNomeFantasia.setText(objetoCliente.getNome_fantasia());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

             */
            if (vizualizacao != 1) {
                pedidoHelper.salvaPedidoParcial();
            }
        }
        super.onResume();
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
                    View dialogView = LayoutInflater.from(ActivityPedidoMain_.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoMain_.this);
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
            Toast.makeText( ActivityPedidoMain_.this, "Falha ao excluir o(s) produto(s)! Tente novamente", Toast.LENGTH_SHORT).show();
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


    public void showMsgSimNao( String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoMain_.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoMain_.this);
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

    public void showMsgClienteAlterado( String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPedidoMain_.this).inflate(R.layout.dialog_sucesso_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPedidoMain_.this);
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
