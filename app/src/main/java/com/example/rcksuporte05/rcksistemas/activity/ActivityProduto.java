package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoSkuDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaSubGrupoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoParametroSku;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ActivityProduto extends AppCompatActivity {

    @BindView(R.id.listaProdutoRecycler)
    RecyclerView recyclerView;

    @BindView(R.id.tb_produto)
    Toolbar toolbar;

    @BindView(R.id.edtTotalProdutos)
    TextView edtTotalProdutos;


    @BindView(R.id.buscaProduto)
    SearchView buscaProduto;

    @BindView(R.id.buscaGrupo)
    Button buscaGrupo;

    @BindView(R.id.btnNovoPedido)
    Button btnNovoPedido;

    @BindView(R.id.lytNovoPedido)
    LinearLayout lytNovoPedido;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    ImageView imageAnexo;

    private Activity context;
    private List<Produto> listaProduto;
    private List<ProdutoSubGrupo> listaProdutoSubGrupo;
    private DBHelper db = new DBHelper(this);
    private ListaProdutoAdapter listaProdutoAdapter;
    private ListaSubGrupoAdapter listaSubGrupoAdater;
    private ActionMode actionMode;
    private ListaProdutoAdapter.ProdutoAdapterListener listenerProduto;
    private ListaSubGrupoAdapter.SubGrupoAdapterListener listenerSubGrupo;
    private ListaProdutoAdapter.ImagemAdapterLister listenerImagem;
    private final LinearLayout.LayoutParams params = null;
    Animation animZoomIn, animZoomOut;
    private int acao = 0;
    private int idPedido = 0;
    private int posRecycleview = 0;
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private boolean visivel;
    private int idCliente = -1 ;
    private EmpresaParametro  empresaParametro = new EmpresaParametro();
    private Configuracao configuracao = new Configuracao();

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
            cliente.setTipo_tabela_preco(empresaParametro.getTipo_tabela_preco());
            cliente.setUtiliza_tabela_preco(empresaParametro.getUtiliza_tabela_preco());
            ClienteHelper.setCliente(cliente);
            activityPedidoMain.pegaCliente(ClienteHelper.getCliente());
            pedido2.pegaCliente(ClienteHelper.getCliente());
            startActivity(new Intent(ActivityProduto.this, ActivityPedidoMain.class));
            finish();
        } catch ( NullPointerException|IndexOutOfBoundsException e) {

        } catch ( Exception e) {

        }
    }

    @OnClick(R.id.btnNovoPedido)
    public void btnNovoPedido() {
        novoPedido();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_produto);
        ButterKnife.bind(this);
        buscaGrupo.setVisibility(View.INVISIBLE);

        try {
            acao = getIntent().getIntExtra("acao", 0);
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        if (acao == 1 || acao == 2)
            showHidePrePedido(false);

        getConfiguracao();
        setIdPedidoCliente();
        //Cliente cliente = ClienteHelper.getCliente();
        toolbar.setTitle("Lista de Produtos");
        animZoomIn= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in_lista);
        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out_default);
        RecyclerView.LayoutManager layoutManagerProduto = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerProduto);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.addOnScrollListener(new ScrollListener());

        mSwipeRefreshLayout.setOnRefreshListener( new RefreshListener());

        setListenerProduto();

        listenerImagem = new ListaProdutoAdapter.ImagemAdapterLister() {
            @Override
            public void onClickListener(int position) {
                showResultActivityProdutoImagem(position);
            }
        };

        buscaProduto.setOnQueryTextListener(new QueryTextListener());
          try {
            if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null) {
                String SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO FROM TBL_PRODUTO AS P " +
                        " LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                        " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                        " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                        " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ORDER BY P.NOME_PRODUTO";

                listaProduto = produtoDAO.getLista(SQL,"");
            } else
                listaProduto = produtoDAO.getLista("SELECT P.* , '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO  FROM TBL_PRODUTO AS P WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ORDER BY P.NOME_PRODUTO", "");

            if (idCliente == 0)
                edtTotalProdutos.setText("Pré-pedido ativo");
            else
                edtTotalProdutos.setText(listaProduto.size() + " Produtos listados");
           setRecyclerView(listaProduto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (PedidoHelper.getBuscaProduto() != null && !PedidoHelper.getBuscaProduto().trim().isEmpty()) {
            buscaProduto.setIconified(false);
            buscaProduto.setQuery(PedidoHelper.getBuscaProduto(), true);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void showHidePrePedido(boolean visivel) {
        lytNovoPedido.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
        btnNovoPedido.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
    }

    private void setIdPedidoCliente() { //seta o id do pedido e do cliente
        try {
            idPedido = getIntent().getIntExtra("idPedido", 0);
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            idCliente = getIntent().getIntExtra("idCliente", -1);
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        try {
            if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("N") || configuracao.getTrabalha_com_pre_pedido().isEmpty())
                showHidePrePedido(false);
        } catch ( NullPointerException e) {
            showHidePrePedido(false);
        }
    }

    private class RefreshListener implements  SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            // Refresh items//
            //preencheListaRecycler(controladorList = new ArrayList<>());
            if (acao == 1 || acao == 2){
                mSwipeRefreshLayout.setRefreshing(false);
                return;
            }
            try {
                if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("N") || configuracao.getTrabalha_com_pre_pedido().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    lytNovoPedido.setVisibility(View.INVISIBLE);
                    return;
                }
            } catch ( NullPointerException e) {
                mSwipeRefreshLayout.setRefreshing(false);
                return;
            }
            mSwipeRefreshLayout.setRefreshing(false);
            showHidePrePedido(true);


        }
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState){
            super.onScrollStateChanged(recyclerView, newState);
            if (acao == 1 || acao == 2)
                return;

            try {
                if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("N") || configuracao.getTrabalha_com_pre_pedido().isEmpty()) {
                    lytNovoPedido.setVisibility(View.INVISIBLE);
                    return;
                }
            } catch ( NullPointerException e) {
                return;
            }

            if (!recyclerView.canScrollVertically(1))
                showHidePrePedido(false);
            else
                showHidePrePedido(true);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // your code there
        }
    }

    private class QueryTextListener implements SearchView.OnQueryTextListener{

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            try {
                if (query.trim().equals(""))
                    setRecyclerView(listaProduto);
                    //listaProdutoAdapter = new ListaProdutoAdapter(listaProduto, listenerProduto, listenerImagem, getIntent().getIntExtra("acao", 0));
                    //edtTotalProdutos.setText(listaProduto.size() + " Produto(s) listado(s)");
                else {
                    //listaProduto = buscarProdutos(query);
                    setRecyclerView(buscarProdutos(query));
                }
                //listaProdutoAdapter = new ListaProdutoAdapter(buscarProdutos(query), listenerProduto, listenerImagem, getIntent().getIntExtra("acao", 0));
                ///listaProdutoAdapter.notifyDataSetChanged();
                //recyclerView.setAdapter(listaProdutoAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private void setListenerProduto() {
        if ( getIntent().getIntExtra("acao", 0) == 1) {
            listenerProduto = new ListaProdutoAdapter.ProdutoAdapterListener() {
                @Override
                public void onClickListener(int position) {
                    if (listaProdutoAdapter.getSelectedItensCount() > 0) {
                        Boolean produtoRepetido = false;
                        if (PedidoHelper.getListaWebPedidoItens() != null)
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!");
                                    break;
                                }

                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido)
                                    enableActionMode(position);
                            } else
                                showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido!");
                        } else {
                            if (!produtoRepetido)
                                enableActionMode(position);
                        }
                    } else {
                        final Intent intent = new Intent(ActivityProduto.this, ActivityProdutoPedido.class);
                        if (buscaProduto != null)
                            PedidoHelper.setBuscaProduto(buscaProduto.getQuery().toString());
                        Boolean produtoRepetido = false;
                        if (PedidoHelper.getListaWebPedidoItens() != null) {
                            int i = 0;
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens()) {
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    final int posicao = i;
                                    ViewGroup viewGroup = findViewById(android.R.id.content);
                                    View dialogView = LayoutInflater.from(ActivityProduto.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProduto.this);
                                    builder.setView(dialogView);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCancelable(false);
                                    TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                                    textView.setText("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido, deseja alterá-lo?");

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
                                            PedidoHelper.setProduto(null);
                                            PedidoHelper.setWebPedidoItem(webPedidoItens);
                                            intent.putExtra("pedido", 1);
                                            intent.putExtra("position", posicao);
                                            startActivity(intent);
                                        }
                                    });
                                    alertDialog.show();
                                    break;
                                }
                                i++;
                            }
                        }
                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido) {
                                    PedidoHelper.setProduto(listaProdutoAdapter.getItem(position));
                                    startActivityForResult(intent, position);
                                }
                            } else
                                showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido");
                        } else {
                            if (!produtoRepetido) {
                                PedidoHelper.setProduto(listaProdutoAdapter.getItem(position));
                                startActivityForResult(intent, position);
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
                                showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!");
                                break;
                            }
                    }
                    if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                        if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                            if (!produtoRepetido)
                                enableActionMode(position);
                        } else
                            showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido!!");
                    } else if (!produtoRepetido)
                        enableActionMode(position);
                }
            };

        } else if (getIntent().getIntExtra("acao", 0) == 2) {

            listenerProduto = new ListaProdutoAdapter.ProdutoAdapterListener() {
                @Override
                public void onClickListener(int position) {
                    if (listaProdutoAdapter.getSelectedItensCount() > 0) {
                        Boolean produtoRepetido = false;
                        if (PedidoHelper.getListaWebPedidoItens() != null)
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!");
                                    break;
                                }

                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido)
                                    enableActionMode(position);
                            } else
                                showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido");
                        } else {
                            if (!produtoRepetido)
                                enableActionMode(position);
                        }
                    } else {
                        if (buscaProduto != null)
                            PedidoHelper.setBuscaProduto(buscaProduto.getQuery().toString());
                        Boolean produtoRepetido = false;
                        if (PedidoHelper.getListaWebPedidoItens() != null)
                            for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                                if (webPedidoItens.getId_produto().trim().equals(listaProdutoAdapter.getItem(position).getId_produto().trim())) {
                                    produtoRepetido = true;
                                    showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!");
                                    break;
                                }
                        if (listaProdutoAdapter.getItem(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                            if (listaProdutoAdapter.getItem(position).getSaldo_estoque() > 0) {
                                if (!produtoRepetido) {
                                    PedidoHelper.setProduto(listaProdutoAdapter.getItem(position));
                                    finish();
                                }
                            } else
                                showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido");
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
                                showMsgAlerta("O produto " + listaProdutoAdapter.getItem(position).getNome_produto() + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!");
                                break;
                            }
                    if (!produtoRepetido)
                        enableActionMode(position);
                }
            };
        } else {

        }
    }

    private void showResultActivityProdutoImagem(int position) {
        Intent intent = new Intent(ActivityProduto.this, ActivityProdutoImagem.class);
        intent.putExtra("id_produto", listaProdutoAdapter.getItem(position).getId_produto());
        intent.putExtra("nome_produto", listaProdutoAdapter.getItem(position).getNome_produto());
        intent.putExtra("saldo_estoque", listaProdutoAdapter.getItem(position).getSaldo_estoque());
        intent.putExtra("acao", getIntent().getIntExtra("acao", 0));
        intent.putExtra("position", position);
        startActivityForResult(intent, position);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        } catch ( NullPointerException e) {
            e.printStackTrace();
        }catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public List<Produto> buscarProdutos(String query) {
        List<Produto> lista = new ArrayList<>();

        if (!query.trim().equals("")) {
            try {
                if ((acao == 1 || acao == 2) && ClienteHelper.getCliente() != null) {
                    String SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO FROM TBL_PRODUTO AS P " +
                            " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                            " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                            " WHERE P.NOME_PRODUTO LIKE '%" + query + "%' OR P.ID_PRODUTO LIKE '%" + query + "%' OR P.CODIGO_EM_BARRAS LIKE '%" + query + "%' AND ( P.ATIVO = 'S' AND EXCLUIDO <> 'S' ) ORDER BY P.ATIVO DESC, P.NOME_PRODUTO;";

                    lista = produtoDAO.getLista(SQL,"");
                    // String SQL = "SELECT CAB.* FROM TBL_CAMPANHA_COMERCIAL_CAB CAB " +
                    //       "INNER JOIN TBL_CAMPANHA_COM_CLIENTES CLIENTE ON CAB.ID_CAMPANHA = CLIENTE.ID_CAMPANHA " +
                    //      "INNER JOIN TBL_CAMPANHA_COMERCIAL_ITENS ITENS ON ITENS.ID_CAMPANHA = CAB.ID_CAMPANHA " +
                    //     "WHERE (CAB.DATA_INICIO <= '" + dataAtual + "' AND  CAB.DATA_FIM >= '" + dataAtual + "') AND  CLIENTE.ID_CLIENTE = " +
                    //   cliente.getId_cadastro_servidor() + " AND (ITENS.ID_LINHA_PRODUTO = " + produto.getIdLinhaColecao() + " OR ITENS.ID_PRODUTO_VENDA = '" + produto.getId_produto() + "');";
                } else
                    lista = produtoDAO.getLista("SELECT P.*, '' AS PRECO_TABELA_CLIENTE, '' AS NOME_TABELA, '' AS W_ID_PRODUTO FROM TBL_PRODUTO AS P WHERE P.NOME_PRODUTO LIKE '%" + query + "%' OR P.ID_PRODUTO LIKE '%" + query + "%' OR P.CODIGO_EM_BARRAS LIKE '%" + query + "%' AND ( P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' ) ORDER BY P.ATIVO DESC, P.NOME_PRODUTO;","");

                if (idCliente == 0)
                    edtTotalProdutos.setText("Pré-pedido ativo");
                else
                    edtTotalProdutos.setText(listaProduto.size() + " Produtos listados");
                //edtTotalProdutos.setText(lista.size() + " Produto(s) encontrado(s)");
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
                edtTotalProdutos.setText("Nenhum produto encontrado");
            }
        }
        System.gc();
        return lista;
    }

    public void setRecyclerView(List<Produto> produtos) {
        listaProdutoAdapter = new ListaProdutoAdapter(produtos, listenerProduto, listenerImagem,  ActivityProduto.this, getIntent().getIntExtra("acao", 0), configuracao );
        recyclerView.setAdapter(listaProdutoAdapter);
        listaProdutoAdapter.notifyDataSetChanged();
        if (idCliente == 0)
            edtTotalProdutos.setText("Pré-pedido ativo");
        else
            edtTotalProdutos.setText(listaProduto.size() + " Produtos listados");
    }

    public void enableActionMode(final int position) {
        if (actionMode == null) {
            actionMode = startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.menu_action_mode_produtos_pedido, menu);
                    if ( getIntent().getIntExtra("acao", 0) != 1 && getIntent().getIntExtra("acao", 0) != 2)
                        menu.findItem(R.id.action_continua_pedido).setVisible(false);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_continua_pedido:
                            ViewGroup viewGroup =  ActivityProduto.this.findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from( ActivityProduto.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                            AlertDialog.Builder builder = new AlertDialog.Builder( ActivityProduto.this);
                            builder.setView(dialogView);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.setCancelable(false);

                            TextView textView = (TextView)  dialogView.findViewById(R.id.txvMensagemOk);
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
                        case R.id.action_produto_imagem:
                            if ( listaProdutoAdapter.getSelectedItensCount() == 1)
                                sinconizaProdutoFotoId(listaProdutoAdapter.getItem(position).getId_produto(), position);
                            else
                                Toast.makeText(ActivityProduto.this, "Atenção!! Deve ser selecionado apenas um produto ", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    actionMode.finish();
                    listaProdutoAdapter.clearSelection();
                    actionMode = null;
                }
            });
        }
        toggleSelection(position);
    }

    public void toggleSelection(int position) {
        listaProdutoAdapter.toggleSelection(position);
        if (listaProdutoAdapter.getSelectedItensCount() == 0) {
            actionMode.finish();
            actionMode = null;
            listaProdutoAdapter.clearSelection();
        } else {
            actionMode.setTitle(String.valueOf(listaProdutoAdapter.getSelectedItensCount()));
            actionMode.invalidate();
        }
    }

    public void sinconizaProdutoFotoId(String id, final int position ) {
        final ProgressDialog progress = new ProgressDialog(ActivityProduto.this);
        progress.setTitle("Aguarde");
        progress.setMessage("Consultando a base");
        progress.setCancelable(false);
        progress.show();
        Rotas apiRetrofit = Api.buildRetrofit(false);

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Call<List<ProdutoFoto>> call = apiRetrofit.produtoImagemID(  id, cabecalho);
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
                            for (ProdutoFoto produto : produtoFoto)
                                if (produtoDAO.add(produto) <= 0)  //grava foto no registro
                                    Toast.makeText(ActivityProduto.this, "Sincronia efetuada com sucesso!! : " + produtoFoto.size()
                                            + " porem não foi possivel atualizar a base de dados no aplicativo! ", Toast.LENGTH_SHORT).show();
                                else if (produto.foto_principal.equalsIgnoreCase("S"))
                                    listaProdutoAdapter.getItem(position).setAnexo_1(produto.getFoto_arquivo());


                            Toast.makeText(ActivityProduto.this, "Sincronia efetuada com sucesso!! : " + produtoFoto.size()
                                    + " foto(s)/imagem(s) sincronizada(s)  ", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        } else {
                            Toast.makeText(ActivityProduto.this, "Nenhuma imagem/foto vinculada ao produto foi localizado" , Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                        actionMode.finish();
                        actionMode = null;
                        break;
                    default:
                        actionMode.finish();
                        actionMode = null;
                        Toast.makeText(ActivityProduto.this, "Erro não catalogado: " + response.code(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        break;
                }
            }


            @Override
            public void onFailure(Call<List<ProdutoFoto>> call, Throwable t) {
                progress.dismiss();
                showMsgAlerta("Sem conexão com o servidor.");
                t.printStackTrace();
            }
        });
    }

    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = ActivityProduto.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( ActivityProduto.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProduto.this);
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

    private void enviarSelecionados(){
        Intent intent = new Intent(ActivityProduto.this, ActivityProdutoPedido.class);
        try {
            if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6")) {
                    int  produto_tabela = 0;
                    int  produto_sem_tabela = 0;
                    boolean permite_desconto = true;
                    try {
                        Cursor cursor = db.listaDados("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = '" + PedidoHelper.getWebPedido().getId_condicao_pagamento() + "' AND ACEITA_DESCONTO = 'N'");
                        if (cursor.moveToNext())
                            permite_desconto = false;
                    } catch ( NullPointerException| CursorIndexOutOfBoundsException e ) {
                    } catch ( Exception e){
                    }
                    List<Produto> listaSelecionados = listaProdutoAdapter.getItensSelecionados();
                    for ( int i = 0; i < listaSelecionados.size(); i++)
                        try {
                            if (!db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE" +
                                    " ID_PRODUTO = " +  listaSelecionados.get(i).getId_produto() + " AND ID_TABELA = " +ClienteHelper.getCliente().getId_tabela_preco() ).get(0).getId_item().isEmpty() ) {
                                produto_tabela++;
                            } else {
                                if (!permite_desconto) {
                                    produto_tabela++;
                                }
                                produto_sem_tabela++;
                            }
                        } catch ( Exception e) {
                            produto_sem_tabela++;
                            if (!permite_desconto) {
                                produto_tabela++;
                            }
                        }

                    if ( !(produto_sem_tabela == listaProdutoAdapter.getSelectedItensCount() || produto_tabela == listaProdutoAdapter.getSelectedItensCount()) ) {
                        Toast.makeText( ActivityProduto.this, "Operação não autorizada!!", Toast.LENGTH_SHORT).show();
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
                } catch ( NullPointerException| CursorIndexOutOfBoundsException e ) {
                    e.printStackTrace();
                } catch ( Exception e){
                    e.printStackTrace();
                }
                List<Produto> listaSelecionados = listaProdutoAdapter.getItensSelecionados();
                for ( int i = 0; i < listaSelecionados.size(); i++) {
                    if ( listaSelecionados.get(i).getProduto_materia_prima().equalsIgnoreCase("S") && !permite_desconto){
                        Toast.makeText( ActivityProduto.this, "Operação não autorizada!! Produto cadastrado como materia prima, a inclusão so é permitida individualmente!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ( listaSelecionados.get(i).getProduto_tercerizacao().equalsIgnoreCase("S") && !permite_desconto){
                        Toast.makeText( ActivityProduto.this, "Operação não autorizada!! Produto cadastrado como terceirizado,  a inclusão so é permitida individualmente!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                PedidoHelper.setListaProdutos(listaProdutoAdapter.getItensSelecionados());
            }
        } catch ( Exception e ) {
            PedidoHelper.setListaProdutos(listaProdutoAdapter.getItensSelecionados());
            if (buscaProduto != null)
                PedidoHelper.setBuscaProduto(buscaProduto.getQuery().toString());

        }
        startActivityForResult(intent, listaProdutoAdapter.getSelectedItensCount());
    }
}

 /*
    public void preecheRecyclerProdutoSubGrupo(List<ProdutoSubGrupo> produtoSubGrupos) {
        listaSubGrupoAdater = new ListaSubGrupoAdapter(produtoSubGrupos, listenerSubGrupo);
        listaSubGrupoRecyclerView.setAdapter(listaSubGrupoAdater);
        listaSubGrupoAdater.notifyDataSetChanged();
    }


     */
// RecyclerView.LayoutManager layoutManagerSubGrupo = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
// listaSubGrupoRecyclerView.setLayoutManager(layoutManagerSubGrupo);
// LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
//         listaSubGrupoLinear.getLayoutParams();
//params.weight = 0.0f;
//  params.height = 0;
// listaSubGrupoLinear.setLayoutParams(params);
         /*
        listaSubGrupoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int pCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int uCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int pcCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    int ucCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                    int visibleItemCount = listaSubGrupoRecyclerView.getChildCount();
                    int totalItemCount = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).getItemCount();
                    int x = pCurrenePosition;
                    int y = uCurrenePosition;

                    if ( pCurrenePosition > 0 && pCurrenePosition < ( totalItemCount - visibleItemCount) ) {
                        img_view_proximo.setAlpha(1.0f);
                        if (pCurrenePosition > 0 && (pCurrenePosition <= (totalItemCount - visibleItemCount)))
                            img_view_anterior.setAlpha(1.0f);
                        else if (pCurrenePosition == 0)
                            if (ucCurrenePosition > 0)
                                img_view_anterior.setAlpha(1.0f);
                            else
                                img_view_anterior.setAlpha(0.5f);


                    } else if( pCurrenePosition == ( totalItemCount - visibleItemCount)) {
                        img_view_proximo.setAlpha(0.5f);
                        img_view_anterior.setAlpha(1.0f);
                    } else if ( pCurrenePosition == 0 ) {
                        img_view_anterior.setAlpha(0.5f);
                        if( pCurrenePosition < (totalItemCount - visibleItemCount))
                            img_view_proximo.setAlpha(1.0f);

                    }
                    if ( (totalItemCount - visibleItemCount) == 0 ) {
                        img_view_anterior.setAlpha(0.5f);
                        img_view_proximo.setAlpha(0.5f);
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

          */


/*
        btn_view_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( idCliente == 0 || idCliente >= 1) {
                    return;
                }
                ViewGroup.LayoutParams params = card_view_menu.getLayoutParams();
                if ( !visivel) {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    visivel = !visivel;
                    btn_view_menu.setImageResource( R.drawable.ic_action_arrow_down_white);
                } else {
                    params.height = 0;
                    visivel = !visivel;
                    btn_view_menu.setImageResource( R.drawable.ic_action_arrow_up_white);
                }
                card_view_menu.setLayoutParams(params);
            }
        });
        */
//params = (LinearLayout.LayoutParams)
//         listaProdutoRecyclerView.getLayoutParams();
// params.weight = 1.0f;
//listaProdutoRecyclerView.setLayoutParams(params);

        /*
        listenerSubGrupo = new ListaSubGrupoAdapter.SubGrupoAdapterListener() {
            @Override
            public void onClickListener(int position) {
                String id = listaProdutoSubGrupo.get(position).getId_grupo();
                try {
                    if( ( acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ) {
                        String SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO FROM TBL_PRODUTO AS P " +
                                " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND P.ID_SUB_GRUPO = " + id + " ORDER BY P.NOME_PRODUTO;";
                        listaProduto = produtoDAO.getLista(SQL);
                    } else
                        listaProduto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' AND EXCLUIDO <> 'S' AND ID_SUB_GRUPO = '" + id + "' ORDER BY NOME_PRODUTO");
                    if ( idCliente == 0) {
                        edtTotalProdutos.setText("Pré-pedido ativo");
                    } else {
                        edtTotalProdutos.setText(listaProduto.size() + " Produtos listados");
                    }
                    preecheRecyclerProduto(listaProduto);

                    listaSubGrupoLinear.startAnimation(animZoomOut);
                    new Handler().postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                    listaSubGrupoLinear.getLayoutParams();
                            params.weight = 0.0f;
                            params.height = 0;
                            listaSubGrupoLinear.setLayoutParams(params);

                            params = (LinearLayout.LayoutParams)
                                    listaProdutoRecyclerView.getLayoutParams();
                            params.weight = 1.0f;

                            listaProdutoRecyclerView.setLayoutParams(params);
                            listaProdutoRecyclerView.startAnimation(animZoomIn);
                            txvVisualizaSubGrupo.setText("Visualizar grupo(s)");
                            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
                                btnVisualizaSubGrupo.setImageResource(getResources().getIdentifier("ic_visualiza_dados_white", "mipmap", getPackageName()));
                            else
                                btnVisualizaSubGrupo.setImageResource(getResources().getIdentifier("ic_visualiza_dados_white", "mipmap", getPackageName()));

                        }
                    }, 310);
                } catch (Exception e) {
                    Toast.makeText(ActivityProduto.this, "Nenhum produto localizado!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onLongClickListener(int position) {
                if ( acao == 1  || acao == 2 ) {
                    String id = listaProdutoSubGrupo.get(position).getId_grupo();
                    try {
                        if( ( acao == 1 || acao == 2) && ClienteHelper.getCliente() != null ) {
                            String SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA, WP.ID_PRODUTO AS W_ID_PRODUTO FROM TBL_PRODUTO AS P " +
                                    " LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                    " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                    " LEFT JOIN TBL_WEB_PEDIDO_ITENS AS WP ON (WP.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND WP.ID_PRODUTO = P.ID_PRODUTO) " +
                                    " WHERE P.ATIVO = 'S' AND P.EXCLUIDO <> 'S' AND P.ID_SUB_GRUPO = " + id + " ORDER BY P.NOME_PRODUTO;";
                            listaProduto = produtoDAO.getLista(SQL);
                        } else
                            listaProduto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' AND EXCLUIDO <> 'S' AND ID_SUB_GRUPO = '" + id + "' ORDER BY NOME_PRODUTO");

                        //listaProduto = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' AND ID_SUB_GRUPO = '" + id + "' ORDER BY NOME_PRODUTO");
                        if ( idCliente == 0) {
                            edtTotalProdutos.setText("Pré-pedido ativo");
                        } else {
                            edtTotalProdutos.setText(listaProduto.size() + " Produtos listados");
                        }
                        //edtTotalProdutos.setText(listaProduto.size() + " Produtos listados");
                        preecheRecyclerProduto(listaProduto);


                        listaSubGrupoLinear.startAnimation(animZoomOut);

                        new Handler().postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                        listaSubGrupoLinear.getLayoutParams();
                                params.weight = 0.0f;
                                params.height = 0;
                                listaSubGrupoLinear.setLayoutParams(params);
                                params = (LinearLayout.LayoutParams)
                                        listaProdutoRecyclerView.getLayoutParams();
                                params.weight = 1.0f;
                                listaProdutoRecyclerView.setLayoutParams(params);
                                listaProdutoRecyclerView.startAnimation(animZoomIn);
                            }
                        }, 310);

                        int totalRepetido = 0;
                        int totalSelecionado = 0;

                        for (int i = 0; i < listaProduto.size(); i++) {
                            if (PedidoHelper.getListaWebPedidoItens() != null) {
                                Boolean produtoRepetido = false;
                                for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens()) {
                                    if (webPedidoItens.getId_produto().trim().equals(listaProduto.get(i).getId_produto().trim())) {
                                        produtoRepetido = true;
                                        totalRepetido++;
                                    }
                                }
                                if (!produtoRepetido) {
                                    if ( listaProduto.get(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S")) {
                                        if (listaProduto.get(position).getSaldo_estoque() > 0) {
                                            enableActionMode(i);
                                            totalSelecionado++;
                                        }  else
                                            totalRepetido++;
                                    } else {
                                        enableActionMode(i);
                                        totalSelecionado++;
                                    }
                                }
                            } else {
                                enableActionMode(i);
                                totalSelecionado++;
                            }
                        }
                        Toast.makeText(ActivityProduto.this, totalSelecionado + " Produto(s) selecionado(s)!\n" + totalRepetido + " Produto(s) já cadastrado(s)/                                                                          Sem estoque disponivel!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(ActivityProduto.this, "Nenhum produto localizado!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    String id = listaProdutoSubGrupo.get(position).getId_grupo();
                    try {
                        listaProduto = produtoDAO.getLista("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' AND EXCLUIDO <> 'S' AND ID_SUB_GRUPO = '" + id + "' ORDER BY NOME_PRODUTO");
                        if ( idCliente == 0) {
                            edtTotalProdutos.setText("Pré-pedido ativo");
                        } else {
                            edtTotalProdutos.setText(listaProduto.size() + " Produtos listados");
                        }
                        preecheRecyclerProduto(listaProduto);
                        listaSubGrupoLinear.startAnimation(animZoomOut);

                        new Handler().postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                        listaSubGrupoLinear.getLayoutParams();
                                params.weight = 0.0f;
                                params.height = 0;
                                listaSubGrupoLinear.setLayoutParams(params);

                                params = (LinearLayout.LayoutParams)
                                        listaProdutoRecyclerView.getLayoutParams();
                                params.weight = 1.0f;

                                listaProdutoRecyclerView.setLayoutParams(params);
                                listaProdutoRecyclerView.startAnimation(animZoomIn);
                            }
                        }, 310);

                    } catch (Exception e) {
                        Toast.makeText(ActivityProduto.this, "Nenhum produto localizado!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        } ;

         */
  /*buscaGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showHideSubGrupo();
            }
        });
        */
//visualizaSubGrupoLinear.setOnClickListener(new View.OnClickListener() {
//@Override
// public void onClick(View view) {

//}
//});
//btnVisualizaSubGrupo.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {

//}
// });
 /*listaProdutoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (acao == 1 || acao == 2)
                    return;

                try {
                    if (configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("N") || configuracao.getTrabalha_com_pre_pedido().isEmpty()) {
                        lytNovoPedido.setVisibility(View.INVISIBLE);
                        return;
                    }
                } catch ( NullPointerException e) {
                    return;
                }

                if (!recyclerView.canScrollVertically(1))
                    showHidePrePedido(false);
                else
                    showHidePrePedido(true);
            }
        });
        */

/*
        img_view_anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicaoAtual  = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                listaSubGrupoRecyclerView.getLayoutManager().scrollToPosition(((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() - 1);
                int posicaoUpdate  = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int pCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstVisibleItemPosition()- 1;
                int pcCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                int visibleItemCount = listaSubGrupoRecyclerView.getChildCount();
                int totalItemCount = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).getItemCount();
                if (pCurrenePosition < (totalItemCount - visibleItemCount)) {
                    img_view_proximo.setAlpha(1.0f);
                    if (pCurrenePosition > 0 && (pCurrenePosition <= (totalItemCount - visibleItemCount)))
                        img_view_anterior.setAlpha(1.0f);
                    else if (pCurrenePosition == 0)
                        img_view_anterior.setAlpha(0.5f);
                    if (pCurrenePosition < (totalItemCount - visibleItemCount))
                        img_view_proximo.setAlpha(1.0f);
                }
                if( pcCurrenePosition == (totalItemCount - visibleItemCount)) {
                    img_view_proximo.setAlpha(0.5f);
                    img_view_anterior.setAlpha(1.0f);
                }
            }
        });

        img_view_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaSubGrupoRecyclerView.getLayoutManager().scrollToPosition(((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findLastVisibleItemPosition() + 1);
                int pCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() + 1;
                int pcCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition() +1;
                int ucCurrenePosition = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                int visibleItemCount = listaSubGrupoRecyclerView.getChildCount();
                int totalItemCount = ((LinearLayoutManager)listaSubGrupoRecyclerView.getLayoutManager()).getItemCount();
                if ( pCurrenePosition < ( totalItemCount - visibleItemCount) ){
                    img_view_proximo.setAlpha(1.0f);
                    if ( pCurrenePosition > 0 && ( pCurrenePosition <= (totalItemCount - visibleItemCount)))
                        img_view_anterior.setAlpha(1.0f);
                    else if ( pCurrenePosition == 0)
                        if ( ucCurrenePosition > 0)
                            img_view_anterior.setAlpha(1.0f);
                        else
                            img_view_anterior.setAlpha(0.5f);
                } else if( pcCurrenePosition >= (totalItemCount - visibleItemCount)) {
                    img_view_proximo.setAlpha(0.5f);
                    img_view_anterior.setAlpha(1.0f);
                }


            }
        });

         */
    /*
    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {

    }

     */

    /*
    private void showHideSubGrupo() {
        buscaProduto.setQuery("", false);
        buscaProduto.clearFocus();
        buscaProduto.setIconified(true);
        //listaProdutoRecyclerView.startAnimation(animZoomOut);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                listaSubGrupoLinear.getLayoutParams();
        int pheight = params.height;
        int pwidth = params.width;
        Float pweight = params.weight;
        if (params.weight >= 1.0f) {

            listaSubGrupoLinear.startAnimation(animZoomOut);
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                            listaSubGrupoLinear.getLayoutParams();
                    params.weight = 0.0f;
                    params.height = 0;
                    listaSubGrupoLinear.setLayoutParams(params);

                    params = (LinearLayout.LayoutParams)
                            listaProdutoRecyclerView.getLayoutParams();
                    params.weight = 1.0f;

                    listaProdutoRecyclerView.setLayoutParams(params);
                    listaProdutoRecyclerView.startAnimation(animZoomIn);
                }
            }, 310);
            try {
                edtTotalProdutos.setText(listaProduto.size() + " Produto(s) listado(s)");
            } catch (NullPointerException e) {
            } catch (Exception e) {
            }
            txvVisualizaSubGrupo.setText("Visualizar grupo(s)");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) //@mipmap/ic_visualiza_dados_white
                btnVisualizaSubGrupo.setImageResource(getResources().getIdentifier("ic_visualiza_dados_white", "mipmap", getPackageName()));
            else
                btnVisualizaSubGrupo.setImageResource(getResources().getIdentifier("ic_visualiza_dados_white", "mipmap", getPackageName()));
            return;
        }
        try {
            listaProdutoSubGrupo = produtoDAO.getListaSubGrupo("SELECT PG.*, ( SELECT COUNT() FROM TBL_PRODUTO WHERE ID_SUB_GRUPO = PG.ID AND ( ATIVO = 'S' AND EXCLUIDO <> 'S' ) ) AS TOTAL_PRODUTO_SUB_GRUPO" +
                    " FROM TBL_PRODUTO_SUB_GRUPO AS PG WHERE PG.ATIVO = 'S' ORDER BY PG.NOME_SUB_GRUPO");
            //edtTotalProdutos.setText(listaProdutoSubGrupo.size() + " Produtos listados");
            //edtTotalProdutos.setTextColor(Color.BLACK);
            preecheRecyclerProdutoSubGrupo(listaProdutoSubGrupo);
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                            listaProdutoRecyclerView.getLayoutParams();
                    params.weight = 6.0f;
                    listaProdutoRecyclerView.setLayoutParams(params);

                    params = (LinearLayout.LayoutParams)
                            listaSubGrupoLinear.getLayoutParams();
                    params.weight = 1.0f;
                    params.height = 0;

                    listaSubGrupoLinear.setLayoutParams(params);
                    listaSubGrupoLinear.startAnimation(animZoomIn);
                    try {
                        edtTotalProdutos.setText(listaProdutoSubGrupo.size() + " Grupo(s) encontrado(s)");
                    } catch (NullPointerException e) {
                        e.printStackTrace();

                    }
                    txvVisualizaSubGrupo.setText("Remover grupo(s)");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        //@mipmap/ic_visualiza_dados_white
                        btnVisualizaSubGrupo.setImageResource(getResources().getIdentifier("ic_esconde_dados_white", "mipmap", getPackageName()));
                    else
                        btnVisualizaSubGrupo.setImageResource(getResources().getIdentifier("ic_esconde_dados_white", "mipmap", getPackageName()));
                }
            }, 310);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     */
