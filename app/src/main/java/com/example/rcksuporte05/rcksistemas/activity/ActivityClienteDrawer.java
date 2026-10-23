package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.ActionMode;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.CadastroAnexoBO;
import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.BairroDAO;
import com.example.rcksuporte05.rcksistemas.util.CameraPhotoHelper;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.LoginDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MunicipioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.LogSistemaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.RegiaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.SegmentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaBairroCheckAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.LogSistema;
import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.model.Segmento;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

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

public class ActivityClienteDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.listaRecycler)
    RecyclerView recyclerView;
    @BindView(R.id.edtTotalClientes)
    EditText edtTotalClientes;
    @BindView(R.id.tb_cliente)
    Toolbar toolbar;
    @BindView(R.id.buscaCliente)
    SearchView buscaCliente;
    @BindView((R.id.helpCliente))
    ImageView helpCliente;
    @BindView(R.id.btnInserirCliente)
    Button btnInserirCliente;

    @BindView(R.id.lytInserirCliente)
    LinearLayout lytInserirCliente;


    @BindView(R.id.cardFinalizarInclusao)
    LinearLayout cardFinalizarInclusao;
    @BindView(R.id.btnFinalizarInclusao)
    Button btnFinalizarInclusao;


    private DBHelper db = new DBHelper(this);
    private ClienteDAO clienteDAO = new ClienteDAO(db);
    private ListaClienteAdapter adapter;
    private ListaClienteAdapter.Listener listener;
    private List<Cliente> lista;
    private ActionMode actionMode;
    Utilitaria util = new Utilitaria();
    private ProgressDialog progress;
    private boolean addNovo = false;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Context context;
    private Configuracao configuracao = new Configuracao();
    PackageInfo pInfo = null;

    private int offSet = 0;
    private int totalReg = 0;
    private int limitReg = 50;
    private String SQL;
    String ufSelecionada;
    String municipioSelecionado;
    String categoriaSelecionada;
    String segmentoSelecionada;
    String regiaoSelecionada;
    String bairroSelecionado;

    String statusSelecionado;

    private ArrayAdapter ufAdapter;
    private ArrayAdapter ativoInativoAdapter;
    private ArrayAdapter municipioAdapter;
    private ArrayAdapter categoriaAdapter;
    private ArrayAdapter segmentoAdapter;
    private ArrayAdapter regiaoAdapter;
    private ArrayAdapter bairroAdapter;

    private ListaBairroCheckAdapter adapterBairro;
    private RecyclerView       bairroRecyclerView;

    List<Categoria> listaCategoria;
    List<Segmento> listaSegmento;
    List<Generico> listaRegiao;
    List<Generico> listaBairro;
    List<Municipio> listaMunicipio;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private static final long DELAY_BUSCA = 500; // 400 milissegundos de delay
    private int[] listaUf = {R.array.AC,
            R.array.AL,
            R.array.AM,
            R.array.AP,
            R.array.BA,
            R.array.CE,
            R.array.DF,
            R.array.ES,
            R.array.EX,
            R.array.GO,
            R.array.MA,
            R.array.MG,
            R.array.MS,
            R.array.MT,
            R.array.PA,
            R.array.PB,
            R.array.PE,
            R.array.PI,
            R.array.PR,
            R.array.RJ,
            R.array.RN,
            R.array.RO,
            R.array.RR,
            R.array.RS,
            R.array.SC,
            R.array.SE,
            R.array.SP,
            R.array.TO};



    @OnClick(R.id.btnInserirCliente)
    public void inserirCliente() {
        Cliente cliente = new Cliente();
        ClienteHelper.setCliente(cliente);
        if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE FINALIZADO = 'N'") >= 1) {
            androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(ActivityClienteDrawer.this);
            alert.setTitle("Atenção!");
            alert.setMessage("Foi detectado um cadastro de cliente em andamento, deseja continua-lo?");
            alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    addNovo = true;
                    novoCadastro();
                }
            });
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        continuaCadastro();
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });
            alert.show();
        } else {
            androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(ActivityClienteDrawer.this);
            alert.setTitle("Atenção!");
            alert.setMessage("Após a conclusão do novo cadastro, consulte a lista de clientes não efetivados, para efetuar a sincronização do mesmo!");
            alert.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    addNovo = true;
                    startActivity(new Intent(ActivityClienteDrawer.this, ActivityCpfCnpjCliente.class).putExtra("novo", 1));
                }
            });
            alert.show();
        }
    }

    @OnClick(R.id.btnFinalizarInclusao)
    public void btnFinalizarInclusao() {

        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_cliente_drawer);
        navigationView = (NavigationView) findViewById(R.id.navView);
        ViewCompat.setOnApplyWindowInsetsListener(navigationView, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, insets.top, 0, insets.bottom);
            return windowInsets;
        });

        ButterKnife.bind(this);
        toolbar.setTitle("Lista de Clientes");
        getConfiguracao();
        try {
            if (getIntent().getIntExtra("acao", 0) != 1) {
                if (configuracao.getVisualiza_legenda_cliente() == null || configuracao.getVisualiza_legenda_cliente().equalsIgnoreCase("S")) {
                    startActivity(new Intent(ActivityClienteDrawer.this, ActivityDialogCliente.class));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (getIntent().getIntExtra("acao", 0) == 1) {
                btnInserirCliente.setVisibility(View.GONE);
                lytInserirCliente.setVisibility(View.GONE);

                if (configuracao.getGira_tela_pedido().isEmpty() || configuracao.getGira_tela_pedido().equalsIgnoreCase("N"))
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                ViewGroup.LayoutParams layoutParams =  (ViewGroup.LayoutParams)  cardFinalizarInclusao.getLayoutParams();
                ViewGroup.LayoutParams layoutParams1 =  (ViewGroup.LayoutParams)  btnFinalizarInclusao.getLayoutParams();
                layoutParams1.height = 0;
                btnFinalizarInclusao.setLayoutParams(layoutParams1);
                layoutParams.height = 0;
                cardFinalizarInclusao.setLayoutParams(layoutParams);
                if (configuracao.getGira_tela_cliente().isEmpty() || configuracao.getGira_tela_cliente().equalsIgnoreCase("N"))
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {
            if (configuracao.getCadastra_cliente_app().equalsIgnoreCase("N")) {
                btnInserirCliente.setVisibility(View.INVISIBLE);
                lytInserirCliente.setVisibility(View.INVISIBLE);
            }
        } catch ( NullPointerException e) {
        } catch ( Exception e){
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ActivityClienteDrawer.ScrollListener());
        buscaCliente.setOnQueryTextListener(new QueryTextListener());
        buscaCliente.setIconifiedByDefault(true);

        // BLOQUEIO DE ASPAS SIMPLES (') NO SEARCHVIEW PARA EVITAR CONFLITOS DE SINTAXE E SQL
        try {
            EditText searchEditText = buscaCliente.findViewById(androidx.appcompat.R.id.search_src_text);
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
        setListenerCliente();
        setRecyclerView(getListaClientes(0, "", "", "", "", ""), 2);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ActivityClienteDrawer.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        addMenuDrawerItem();
        helpCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ActivityClienteDrawer.this, ActivityDialogCliente.class));
            }
        });
        System.gc();
    }

    @Override
    protected void onResume() {
        try {
            if (buscaCliente != null && !buscaCliente.getQuery().toString().trim().isEmpty())
                System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (addNovo) {
            setRecyclerView(getListaClientes(2, "","","","", ""), 2);
            addNovo = false;
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.gc();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            try {
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("N")) {
                    edtTotalClientes.setText("(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
                    return;
                }
            } catch (NullPointerException e) {
                return;
            }
            if (!recyclerView.canScrollVertically(1)) {
                if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S")) {
                    offSet += limitReg;
                    lista.addAll(clienteDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet));;
                    adapter.notifyDataSetChanged();
                }
            }
            edtTotalClientes.setText(  "(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            try {
                if (getIntent().getIntExtra("acao", 0) == 1) {
                    return;
                }
            } catch (NullPointerException  ignored){
            }
            if (configuracao.getCadastra_cliente_app().equalsIgnoreCase("N")) {
                return;
            }
            if (dy > 0) {
                lytInserirCliente.setVisibility(View.INVISIBLE);
                btnInserirCliente.setVisibility(View.INVISIBLE);
            }else if (dy < 0) {
                lytInserirCliente.setVisibility(View.VISIBLE);
                btnInserirCliente.setVisibility(View.VISIBLE);
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

    private void getConfiguracao() {
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }


    private class QueryTextListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
        @Override
        public boolean onQueryTextChange(String query) {
            try {
                if (query.trim().isEmpty())
                    setRecyclerView(getListaClientes(0, "", "", "", "", ""), 2);
                else {
                    if (runnable != null) {
                        handler.removeCallbacks(runnable);
                    }
                    runnable = () -> {
                        setRecyclerView(getListaClientes(query), 2);
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

    private void addMenuDrawerItem() {
        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.addSubMenu(" Status/Pendência(s)/Ativo/Inativo ");
        subMenu.add(0, 0, 0, "TODOS OS CLIENTES").setIcon(R.drawable.ic_lista_todos_drawner);
        subMenu.add(0, 1, 1, "T. EFETIVADOS").setIcon(R.drawable.ic_lista_drawner);
        subMenu.add(0, 2, 2, "T. NÃO EFETIVADOS").setIcon(R.drawable.ic_lista_drawner);
        subMenu.add(0, 3, 3, "T. COM PENDÊNCIA(S) FINANCEIRA").setIcon(R.drawable.ic_lista_drawner);
        subMenu.add(0, 4, 4, "T. COM PEDIDO(S) EM ANALISE").setIcon(R.drawable.ic_lista_drawner);
        subMenu.add(0, 5, 5, "SOMENTE CLIENTES ATIVOS").setIcon(R.drawable.ic_lista_drawner);
        subMenu.add(0, 6, 6, "SOMENTE CLIENTES INATIVOS/BLOQUEADOS").setIcon(R.drawable.ic_lista_drawner);
        subMenu.add(0, 7, 7, "FILTROS").setIcon(R.drawable.ic_lista_drawner);
        subMenu.add(0, 8, 8, "NOVO CADASTRO DE CLIENTE").setIcon(R.drawable.ic_add_novo_registro);
        subMenu.add(0, 9, 9, "VOLTAR").setIcon(R.drawable.ic_action_up_arrow_back_white);
        //subMenu.add(0, 6, 6, "ROTAS").setIcon(R.drawable.ic_lista_drawner);
        navigationView.invalidate();
    }

    public List<Cliente> getListaClientes(final String query) {
        try {
            offSet = 0;
            SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' "/*AND ATIVO = 'S'*/ + "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%')";
            totalReg = clienteDAO.getTotalReg(SQL);
            SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY NOME_CADASTRO ";
            if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                if ( configuracao.getOrdem_lista_cliente() == null ||
                        configuracao.getOrdem_lista_cliente().isEmpty() ||
                        configuracao.getOrdem_lista_cliente().equalsIgnoreCase("N") ||
                        configuracao.getOrdem_lista_cliente().equalsIgnoreCase("N"))
                    lista = clienteDAO.getLista( SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                else {
                    /*
                    if ( configuracao.getOrdem_lista_cliente().equalsIgnoreCase("R")) {
                        SQL = "SELECT DISTINCT CD.* FROM TBL_CADASTRO AS CD " +
                                "LEFT JOIN TBL_CADASTRO_REGIAO AS RG ON RG.ID = CD.ID_REGIAO " +
                                "LEFT JOIN TBL_CADASTRO_BAIRRO BR ON BR.ID = RG.ID " +
                                "WHERE CD.FINALIZADO <> 'N' AND (CD.NOME_CADASTRO LIKE '%" + query + "%' OR CD.NOME_FANTASIA LIKE '%"
                                    + query + "%' OR CD.CPF_CNPJ LIKE '"
                                    + query + "%' OR CD.TELEFONE_PRINCIPAL LIKE '%"
                                    + query + "%' OR CD.ID_CADASTRO_SERVIDOR LIKE '%"
                                    + query + "%') " +
                                "ORDER BY RG.NOME_REGIAO, BR.ORDEM, CD.ENDERECO_BAIRRO, CD.ROTA_SEQUENCIA, CD.ENDERECO, CD.ENDERECO_NUMERO  ";
                        if ( configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S")) {
                            lista = clienteDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
                        } else {
                            lista = clienteDAO.getLista(SQL);
                        }
                    } else {
                     */
                        SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND FINANCEIRO_VENCIDO > 0.00 " + "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY NOME_CADASTRO ";
                        lista = clienteDAO.getLista(SQL);
                        SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND (FINANCEIRO_VENCIDO  <= 0.00 OR FINANCEIRO_VENCIDO IS NULL) " + "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY NOME_CADASTRO ";
                        lista.addAll(clienteDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet));
                    //}
                }
            else {
                if ( configuracao.getOrdem_lista_cliente() == null || configuracao.getOrdem_lista_cliente().isEmpty() || configuracao.getOrdem_lista_cliente().equalsIgnoreCase("N"))
                    lista = clienteDAO.getLista(SQL);
                else {
                    SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' " /*AND ATIVO = 'S'*/ + "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') AND FINANCEIRO_VENCIDO > 0.00 ORDER BY NOME_CADASTRO ";
                    lista = clienteDAO.getLista(SQL);
                    SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' " /*AND ATIVO = 'S'*/ + "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') AND (FINANCEIRO_VENCIDO <= 0.00 OR FINANCEIRO_VENCIDO IS NULL) ORDER BY NOME_CADASTRO ";
                    lista.addAll(clienteDAO.getLista(SQL));
                }
            }
            edtTotalClientes.setText(lista.size() + "/" + totalReg);
            return lista;
        } catch (CursorIndexOutOfBoundsException e) {
            edtTotalClientes.setText("0/0");
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public List<Cliente> getListaClientes(int idStatus, String uf, String cidade, String categoriaRegiao, String segmentoBairro, String statusCadastro) {

        try {
            offSet = 0;
            if ( idStatus == 0 || idStatus == 5 || idStatus == 6) {
                if ( UsuarioHelper.getUsuario() != null) {
                    if ( UsuarioHelper.getUsuario().getCliente_lista_todos().equalsIgnoreCase("S")) {
                        SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N'";
                    }else {
                        SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND ID_VENDEDOR = '" + UsuarioHelper.getUsuario().getId_quando_vendedor() + "' ";/*AND ATIVO = 'S'"*/
                    }
                    if ( idStatus == 5)
                        SQL += "AND ATIVO = 'S'";
                    if ( idStatus == 6)
                        SQL += "AND ATIVO = 'N'";
                    totalReg =clienteDAO.getTotalReg(SQL);
                    if ( configuracao.getOrdem_lista_cliente() == null ||
                            configuracao.getOrdem_lista_cliente().isEmpty() ||
                            configuracao.getOrdem_lista_cliente().equalsIgnoreCase("N") ||
                            configuracao.getOrdem_lista_cliente().equalsIgnoreCase("R")) {
                        if ( UsuarioHelper.getUsuario().getCliente_lista_todos().equalsIgnoreCase("S")) {
                            SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N'";
                        } else {
                            SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND ID_VENDEDOR = '" + UsuarioHelper.getUsuario().getId_quando_vendedor() + "'";// /*AND ATIVO = 'S'*/ + " ORDER BY NOME_CADASTRO ";
                        }
                        if ( idStatus == 5)
                            SQL += "AND ATIVO = 'S' ";
                        if ( idStatus == 6)
                            SQL += "AND ATIVO = 'N' ";
                        SQL += "ORDER BY NOME_CADASTRO ";

                    }else {
                        SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND FINANCEIRO_VENCIDO > 0.00" /*AND ATIVO = 'S'*/ + " ORDER BY NOME_CADASTRO ";
                        lista = clienteDAO.getLista(SQL);
                        SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND (FINANCEIRO_VENCIDO  <= 0.00 OR FINANCEIRO_VENCIDO IS NULL) " ;
                        if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S")) {
                            lista.addAll(clienteDAO.getLista(SQL + " ORDER BY NOME_CADASTRO " + "LIMIT " + limitReg + " OFFSET " + offSet  ));
                        } else {
                            lista.addAll(clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND (FINANCEIRO_VENCIDO  <= 0.00 OR FINANCEIRO_VENCIDO IS NULL)" + " ORDER BY NOME_CADASTRO"));
                        }
                        if (lista.size() > 0)
                            edtTotalClientes.setText(lista.size() + "/" + totalReg);
                        else {
                            edtTotalClientes.setText("0/0");
                            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
                        }
                        return lista;
                    }
                } else {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' ";/*AND ATIVO = 'S'"*/;
                    if ( idStatus == 5)
                        SQL += "AND ATIVO = 'S'";
                    if ( idStatus == 6)
                        SQL += "AND ATIVO = 'N'";
                    totalReg =clienteDAO.getTotalReg(SQL);
                    if ( configuracao.getOrdem_lista_cliente() == null ||
                            configuracao.getOrdem_lista_cliente().isEmpty() ||
                            configuracao.getOrdem_lista_cliente().equalsIgnoreCase("N") ||
                            configuracao.getOrdem_lista_cliente().equalsIgnoreCase("R")) {
                        SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' ";// /*AND ATIVO = 'S'*/ + " ORDER BY NOME_CADASTRO ";
                        if ( idStatus == 5)
                            SQL += "AND ATIVO = 'S' ";
                        if ( idStatus == 6)
                            SQL += "AND ATIVO = 'N' ";
                        SQL += "ORDER BY NOME_CADASTRO ";

                    }else {
                        SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND FINANCEIRO_VENCIDO > 0.00" /*AND ATIVO = 'S'*/ + " ORDER BY NOME_CADASTRO ";
                        lista = clienteDAO.getLista(SQL);
                        SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND (FINANCEIRO_VENCIDO  <= 0.00 OR FINANCEIRO_VENCIDO IS NULL) " ;
                        if (configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S")) {
                            lista.addAll(clienteDAO.getLista(SQL + " ORDER BY NOME_CADASTRO " + "LIMIT " + limitReg + " OFFSET " + offSet  ));
                        } else {
                            lista.addAll(clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND (FINANCEIRO_VENCIDO  <= 0.00 OR FINANCEIRO_VENCIDO IS NULL)" + " ORDER BY NOME_CADASTRO"));
                        }
                        if (lista.size() > 0)
                            edtTotalClientes.setText(lista.size() + "/" + totalReg);
                        else {
                            edtTotalClientes.setText("0/0");
                            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
                        }
                        return lista;
                    }
                }

            }else if ( idStatus == 1) {//Efetivados
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0";
                totalReg =clienteDAO.getTotalReg(SQL);
                SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0 ORDER BY NOME_CADASTRO ";
            } else if ( idStatus == 2) {//Não efetivados
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE (FINALIZADO <> 'N' OR FINALIZADO IS NULL) AND F_CLIENTE <> 'S' " /*AND ATIVO = 'S'*/ + "AND F_VENDEDOR = 'N'";
                totalReg =clienteDAO.getTotalReg(SQL);
                SQL = "SELECT * FROM TBL_CADASTRO WHERE  (FINALIZADO <> 'N' OR FINALIZADO IS NULL) AND F_CLIENTE <> 'S' " /*AND ATIVO = 'S'*/ + "AND F_VENDEDOR = 'N' ORDER BY ID_PROSPECT, ID_CADASTRO DESC, NOME_CADASTRO ";
            } else if (idStatus == 3) {//Efetivado com pendencia financeira
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0 AND ( FINANCEIRO_VENCIDO <> '0.0' OR FINANCEIRO_VENCENDO <> '0.0' )";
                totalReg =clienteDAO.getTotalReg(SQL);
                SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0 AND ( FINANCEIRO_VENCIDO <> '0.0' OR FINANCEIRO_VENCENDO <> '0.0' ) ORDER BY NOME_CADASTRO ";
            } else if ( idStatus == 4) {//Efetivado com pedido em analise
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0 AND  PEDIDO_ANALISE <> '0.0'";
                totalReg =clienteDAO.getTotalReg(SQL);
                SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0 AND  PEDIDO_ANALISE <> '0.0' ORDER BY NOME_CADASTRO ";
            } else if ( idStatus == 7) {//Outros filtros
                if ( categoriaRegiao.isEmpty() && segmentoBairro.isEmpty() ) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    totalReg = clienteDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' " ;//
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    SQL += " ORDER BY NOME_CADASTRO ";

                }  else if ( categoriaRegiao.equalsIgnoreCase("0") && segmentoBairro.equalsIgnoreCase("0")) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    totalReg = clienteDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' " ;
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    SQL += " ORDER BY NOME_CADASTRO ";
                }  else if ( !categoriaRegiao.equalsIgnoreCase("0") && segmentoBairro.equalsIgnoreCase("0")) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' AND ID_CATEGORIA = '" + categoriaRegiao + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    totalReg = clienteDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' AND ID_CATEGORIA = '" + categoriaRegiao + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    SQL += " ORDER BY NOME_CADASTRO ";
                }  else if ( categoriaRegiao.equalsIgnoreCase("0") && !segmentoBairro.equalsIgnoreCase("0")) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' AND ID_SETOR = '" + segmentoBairro + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    totalReg = clienteDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' AND ID_SETOR = '" + segmentoBairro + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    SQL += " ORDER BY NOME_CADASTRO ";
                }  else if ( !categoriaRegiao.equalsIgnoreCase("0") && !segmentoBairro.equalsIgnoreCase("0")) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' AND ID_CATEGORIA = '" + categoriaRegiao + "' AND ID_SETOR = '" + segmentoBairro + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    totalReg = clienteDAO.getTotalReg(SQL);
                    SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " + "AND ID_CADASTRO_SERVIDOR > 0 AND ENDERECO_UF = '" + uf + "' AND NOME_MUNICIPIO = '" + cidade + "' AND ID_CATEGORIA = '" + categoriaRegiao + "' AND ID_SETOR = '" + segmentoBairro + "' ";
                    if ( statusCadastro.equalsIgnoreCase("A"))
                        SQL += "AND ATIVO = 'S' ";
                    if ( statusCadastro.equalsIgnoreCase("I"))
                        SQL += "AND ATIVO = 'N' ";
                    SQL += " ORDER BY NOME_CADASTRO ";
                }
            } else if ( idStatus == 8) {//Rotas filtros
                if (!categoriaRegiao.equalsIgnoreCase("0") && segmentoBairro.equalsIgnoreCase("0")) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ +
                            "AND ID_CADASTRO_SERVIDOR > 0 AND ID_REGIAO = '" + categoriaRegiao + "' AND NOME_MUNICIPIO = '" + municipioSelecionado + "'";
                    totalReg = clienteDAO.getTotalReg(SQL);
                    SQL = "SELECT DISTINCT CD.* FROM TBL_CADASTRO AS CD " +
                            "LEFT JOIN TBL_CADASTRO_REGIAO AS RG ON RG.ID = CD.ID_REGIAO " +
                            "LEFT JOIN TBL_CADASTRO_BAIRRO BR ON BR.ID = CD.ID_BAIRRO " +
                            "WHERE CD.FINALIZADO <> 'N' AND CD.F_CLIENTE = 'S' " +
                            "AND CD.ID_CADASTRO_SERVIDOR > 0 " +
                            "AND CD.ID_REGIAO = '" + categoriaRegiao + "' AND CD.NOME_MUNICIPIO = '" + municipioSelecionado + "' " +
                            "ORDER BY RG.NOME_REGIAO, BR.ORDEM, CD.ENDERECO_BAIRRO, CD.ROTA_SEQUENCIA, CD.ENDERECO, CD.ENDERECO_NUMERO  ";
                } else if (!categoriaRegiao.equalsIgnoreCase("0") && !segmentoBairro.equalsIgnoreCase("0")) {
                    SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ +
                            "AND ID_CADASTRO_SERVIDOR > 0 AND ID_REGIAO = '" + categoriaRegiao.trim() + "' AND NOME_MUNICIPIO = '" + municipioSelecionado.trim() + "' " +
                            //"AND ENDERECO_BAIRRO = '" + bairroSelecionado.trim() + "'";
                            "AND ID_BAIRRO IN " + bairroSelecionado;
                    totalReg = clienteDAO.getTotalReg(SQL);
                    SQL = "SELECT DISTINCT CD.* FROM TBL_CADASTRO AS CD " +
                            "LEFT JOIN TBL_CADASTRO_REGIAO AS RG ON RG.ID = CD.ID_REGIAO " +
                            "LEFT JOIN TBL_CADASTRO_BAIRRO BR ON BR.ID = CD.ID_BAIRRO " +
                            "WHERE CD.FINALIZADO <> 'N' AND CD.F_CLIENTE = 'S' " +
                            "AND CD.ID_CADASTRO_SERVIDOR > 0 " +
                            "AND CD.ID_REGIAO = '" + categoriaRegiao.trim() + "' AND CD.NOME_MUNICIPIO = '" + municipioSelecionado.trim() + "' " +
                            //"AND CD.ENDERECO_BAIRRO = '" + bairroSelecionado.trim() + "' " +
                            "AND CD.ID_BAIRRO IN " + bairroSelecionado.trim() + " " +
                            "ORDER BY RG.NOME_REGIAO, BR.ORDEM, CD.ENDERECO_BAIRRO, CD.ROTA_SEQUENCIA, CD.ENDERECO, CD.ENDERECO_NUMERO  ";
                } else {

                }
            } else if(idStatus == 9) {
                onBackPressed();
            } else {
                SQL = "SELECT COUNT(*) AS TOTALREG FROM TBL_CADASTRO WHERE FINALIZADO <> 'N'";// AND ATIVO = 'S'";
                totalReg =clienteDAO.getTotalReg(SQL);
                SQL = "SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' " /*AND ATIVO = 'S'*/ + " ORDER BY NOME_CADASTRO ";
            }
            if ( configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S"))
                lista = clienteDAO.getLista(SQL + "LIMIT " + limitReg + " OFFSET " + offSet);
            else
               lista = clienteDAO.getLista(SQL);

            if (lista.size() > 0)
                edtTotalClientes.setText( lista.size() + "/" + totalReg);
            else {
                edtTotalClientes.setText("0/0");
                Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
            }
            return lista;
        } catch (CursorIndexOutOfBoundsException e) {
            edtTotalClientes.setText("0/0");
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

/*
    public void showMsgSimNao( Cliente webCliente, int position) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityClienteDrawer.this).inflate(R.layout.dialog_sim_nao_altera_cliente_pedido, viewGroup, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ActivityClienteDrawer.this);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText("Confirma a alteração do cliente " + webCliente.getNome_cadastro() + " para -->  "  + adapter.getItem(position).getNome_cadastro() + "?");

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
                ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                activityPedidoMain.pegaCliente(adapter.getItem(position));
                Pedido2 pedido2 = new Pedido2();
                pedido2.pegaCliente(adapter.getItem(position));
                System.gc();
                finish();
                return;
            }
        });
        alertDialog.show();
    }

 */
    private void setListenerCliente() {
        if (getIntent().getIntExtra("acao", 0) == 1) {
            float limiteCredito  = 0.00f;
            float limitUtilizado = 0.00f;
            listener = new ListaClienteAdapter.Listener() {
                @Override
                public void onClickListener(int position) {
                    /* MODIFICAÇÃO EM 22/05/2024 - 16:30 - TRATAMENTO DE PROCESS DEATH
                       SE O USUÁRIO TENTAR SELECIONAR UM CLIENTE PARA O PEDIDO MAS O OBJETO DO PEDIDO FOI LIMPO PELO SISTEMA (INATIVIDADE),
                       EXIBIMOS O ALERTA ANIMADO E RETORNAMOS AO MENU PRINCIPAL PARA EVITAR O CRASH POR NULLPOINTER NO GETWEBPEDIDO. */
                    if (PedidoHelper.getWebPedido() == null) {
                        showAnimationFeedbackDialog("A sessão de venda foi interrompida pelo sistema Android para liberar memória (geralmente devido à inatividade prolongada ou ao excesso de aplicativos abertos simultaneamente). Para sua segurança, você será redirecionado ao menu principal. Caso este pedido já possua itens lançados, você poderá continuar a edição através da opção 'Pedidos Pendentes'.", false, () -> {
                            Intent intent = new Intent(ActivityClienteDrawer.this, ActivityPrincipal.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        });
                        return;
                    }
                    /* FIM DA MODIFICAÇÃO */

                    Cliente cliente = adapter.getItem(position);
                    boolean abrePedido = true;
                    if (cliente.getIdCategoria() <= 0) {
                        abrePedido = false;
                        util.showFeedbackDialog("Este cliente não tem categoria definida!", true, false, R.drawable.ic_alerta_mensagem_color_primary, ActivityClienteDrawer.this);
                    } else if (cliente.getAtivo().equalsIgnoreCase("N")) {
                        abrePedido = false;
                        util.showFeedbackDialog("Este cliente esta inátivo/bloqueado!", true, false, R.drawable.ic_alerta_mensagem_color_primary, ActivityClienteDrawer.this);
                    } else if (configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                        try {
                            float limiteCredito = Float.parseFloat(cliente.getLimite_credito());
                            float limitUtilizado = Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo());
                            if ((limiteCredito - limitUtilizado) < 0.00f) {
                                abrePedido = false;
                                showActionDialog("Cliente sem limite disponivel para compra a prazo! Pedido só podera ser feito para pagamento à vista!\nDeseja continuar?", position, true, "");
                            }
                        } catch (NullPointerException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    if (abrePedido) {
                        // NOVO: Verificação de Débito com Informativo em Destaque (Ação 1)
                        if (verificarDebitoCliente(cliente, () -> selecionarClienteParaPedido(position))) {
                            return;
                        }
                        selecionarClienteParaPedido(position);
                    }
                }

                @Override
                public void onLongClickListener(int position) {
                }

                @Override
                public View.OnClickListener onClickDowload(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showActionDialog(view.getId(),position, "Deseja efetuar a sincronização/atualização dos dados do cliente?");
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickChamada(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (!adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty())
                                    if (adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11)
                                        showActionDialog(view.getId(), position, "Deseja ligar para o número " + adapter.getItem(position).getTelefone_principal() + " ?");
                                    else
                                        util.showFeedbackDialog("Este numero de telefone não é válido!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                                else
                                    util.showFeedbackDialog("Nenhum numero de telefone informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            } catch (Exception e) {
                                util.showFeedbackDialog("Nenhum numero de telefone informado/ou número inválido!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            }
                        }
                    };
                }

                @Override
                public View.OnLongClickListener onLongClickChamada(int position) {
                    return view -> {
                        try {
                            String tel = adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").trim();
                            if (!tel.isEmpty()) {
                                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + tel)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    };
                }

                @Override
                public View.OnClickListener onClickGps(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (adapter.getItem(position).getEndereco().equals("Nenhum endereço informado!"))
                                util.showFeedbackDialog("Nenhum endereço informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            else
                                showActionDialog(view.getId(), position, "Deseja navegar para a localização para " + adapter.getItem(position).getNome_cadastro() + " no GPS?");
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickEmail(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (adapter.getItem(position).getEmail_principal().isEmpty())
                                    util.showFeedbackDialog("Nenhum e-mail informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                                else
                                    showActionDialog(view.getId(), position, "Deseja enviar um email a " + adapter.getItem(position).getNome_cadastro() + "?");
                            } catch (NullPointerException e) {
                                util.showFeedbackDialog("Nenhum e-mail informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            }
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickFinanceiro(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showActionDialog(view.getId(), position, "Deseja efetuar consulta ao historico financeiro?");
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickNovoPedido(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Cliente cliente = adapter.getItem(position);
                            boolean abrePedido = true;
                            if (cliente.getIdCategoria() <= 0) {
                                abrePedido = false;
                                util.showFeedbackDialog("Este cliente não tem uma categoria definida", true, false, R.drawable.ic_alerta_mensagem_color_primary, ActivityClienteDrawer.this);
                            } else if (cliente.getAtivo().equalsIgnoreCase("N")) {
                                abrePedido = false;
                                util.showFeedbackDialog("Este cliente esta inátivo/bloqueado!", true, false, R.drawable.ic_alerta_mensagem_color_primary, ActivityClienteDrawer.this);
                            } else if (configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                                float limiteCredito = Float.parseFloat(cliente.getLimite_credito());
                                float limitUtilizado = Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo());
                                if ((limiteCredito - limitUtilizado) < 0.00f) {
                                    abrePedido = false;
                                    showActionDialog("Cliente sem limite disponivel para compra a prazo! Pedido só podera ser feito para pagamento à vista!\nDeseja continuar?", position, false, "");
                                }
                            }

                            if (abrePedido) {
                                // NOVO: Verificação de Débito com Informativo em Destaque (Ação 2)
                                if (verificarDebitoCliente(cliente, () -> escolherEmpresaPedido(position))) {
                                    return;
                                }
                                escolherEmpresaPedido(position);
                            }
                        }
                    };
                }
            };
        } else {
            listener = new ListaClienteAdapter.Listener() {
                @Override
                public void onClickListener(int position) {
                    if (adapter.getSelectedItensCount() > 0)
                        enableActionMode(position);
                    else {
                        // NOVO: Chama o Hub de Ações moderno
                        exibirDialogAcoesCliente(position);

                        // ANTERIOR: Chamava a lista de texto
                        // exibirDialogLista("Clique em uma das opções para o cliente", position);
                    }
                }

                @Override
                public void onLongClickListener(int position) {
                    enableActionMode(position);
                }

                @Override
                public View.OnClickListener onClickDowload(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showActionDialog(view.getId(),position, "Deseja efetuar a sincronização/atualização dos dados do cliente?");
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickChamada(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (!adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty())
                                    if (adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11)
                                        showActionDialog(view.getId(), position, "Deseja ligar para o número " + adapter.getItem(position).getTelefone_principal() + " ?");
                                    else
                                        util.showFeedbackDialog("Este numero de telefone não é válido!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                                else
                                    util.showFeedbackDialog("Nenhum numero de telefone informado!", true, false,R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            } catch (Exception e) {
                                util.showFeedbackDialog("Nenhum numero de telefone informado/ou número inválido!", true, false,R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            }
                        }
                    };
                }
                @Override
                public View.OnLongClickListener onLongClickChamada(int position) {
                    return view -> {
                        try {
                            String tel = adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").trim();
                            if (!tel.isEmpty()) {
                                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + tel)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    };
                }

                @Override
                public View.OnClickListener onClickGps(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (adapter.getItem(position).getEndereco().equals("Nenhum endereço informado!"))
                                util.showFeedbackDialog("Nenhum endereço informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            else
                                showActionDialog(view.getId(), position, "Deseja navegar para a localização para " + adapter.getItem(position).getNome_cadastro() + " no GPS?");
                        }
                    };
                }
                @Override
                public View.OnClickListener onClickEmail(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (adapter.getItem(position).getEmail_principal().isEmpty())
                                    util.showFeedbackDialog("Nenhum e-mail informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                                else
                                    showActionDialog(view.getId(), position, "Deseja enviar um email a " + adapter.getItem(position).getNome_cadastro() + "?");
                            } catch (NullPointerException e) {
                                util.showFeedbackDialog("Nenhum e-mail informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            }
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickFinanceiro(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showActionDialog(view.getId(), position, "Deseja efetuar consulta ao historico financeiro?");
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickNovoPedido(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean abrePedido = true;
                            if (adapter.getItem(position).getIdCategoria() <= 0) {
                                abrePedido = false;
                                util.showFeedbackDialog("Este cliente não tem uma categoria definida", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            } else if (adapter.getItem(position).getAtivo().equalsIgnoreCase("N")) {
                                abrePedido = false;
                                util.showFeedbackDialog("Este cliente esta inátivo/bloqueado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityClienteDrawer.this);
                            } else if (configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                                float limiteCredito = Float.parseFloat(adapter.getItem(position).getLimite_credito());
                                float limitUtilizado = Float.parseFloat(adapter.getItem(position).getFinanceiro_vencido()) + Float.parseFloat(adapter.getItem(position).getFinanceiro_vencendo());
                                if ((limiteCredito - limitUtilizado) < 0.00f) {
                                    abrePedido = false;
                                    showActionDialog("Cliente sem limite disponivel para compra a prazo! Pedido só podera ser feito para pagamento à vista!\nDeseja continuar?", position, false, "");
                                } else {
                                    showActionDialog(view.getId(), position, "Deseja iniciar um novo pedido para " + adapter.getItem(position).getNome_cadastro() + "?");
                                }
                            } else {
                                if (abrePedido)
                                    showActionDialog(view.getId(), position, "Deseja iniciar um novo pedido para " + adapter.getItem(position).getNome_cadastro() + "?");

                            }
                        }
                    };
                }
            };
        }
    };

    public void setRecyclerView(List<Cliente> lista) {
        adapter = new ListaClienteAdapter(ActivityClienteDrawer.this, this.lista, listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (!lista.isEmpty())
             edtTotalClientes.setText(   "1/" + totalReg);
        else
            edtTotalClientes.setText( "0/0");
    }

    @SuppressLint("SuspiciousIndentation")
    public void setRecyclerView(List<Cliente> lista, final int numCols) {  //V
        int numColsList = 1;
        GridLayoutManager layoutManager;
        int orientation = this.getResources().getConfiguration().orientation;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        adapter = new ListaClienteAdapter(ActivityClienteDrawer.this, this.lista, listener);
        if (configuracao.getGira_tela_cliente().equalsIgnoreCase("S")) {
            if (getIntent().getIntExtra("acao", 0) == 1) {
                if (configuracao.getGira_tela_pedido().equalsIgnoreCase("S")) {
                    if (orientation == Configuration.ORIENTATION_PORTRAIT)
                        switch (screenSize) {
                            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                                layoutManager = new GridLayoutManager(this, 2);
                                numColsList = 2;
                                break;
                            default:
                                layoutManager = new GridLayoutManager(this, 1);
                        }
                    else
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
                } else {
                    switch (screenSize) {
                        case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                            layoutManager = new GridLayoutManager(this, 2);
                            numColsList = 2;
                            break;
                        default:
                            layoutManager = new GridLayoutManager(this, 1);
                    }
                }
            } else {
                if (orientation == Configuration.ORIENTATION_PORTRAIT)
                    switch (screenSize) {
                        case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                        case Configuration.SCREENLAYOUT_SIZE_LARGE:
                            layoutManager = new GridLayoutManager(this, 2);
                            numColsList = 2;
                            break;
                        default:
                            layoutManager = new GridLayoutManager(this, 1);
                    }
                else
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
            if (getIntent().getIntExtra("acao", 0) == 1) {
                if (configuracao.getGira_tela_pedido().equalsIgnoreCase("S")) {
                    if (orientation == Configuration.ORIENTATION_PORTRAIT)
                        switch (screenSize) {
                            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                                layoutManager = new GridLayoutManager(this, 2);
                                numColsList = 2;
                                break;
                            default:
                                layoutManager = new GridLayoutManager(this, 1);
                        }
                    else
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
                } else {
                    switch (screenSize) {
                        case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                            layoutManager = new GridLayoutManager(this, 2);
                            numColsList = 2;
                            break;
                        default:
                            layoutManager = new GridLayoutManager(this, 1);
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
                }
            }
        }
        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        int finalNumColsList = numColsList;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                    if (adapter != null) {
                        adapter.setNumCols(finalNumColsList);
                        switch (adapter.getItemViewType(i)) {
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
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void enableActionMode(final int position) {
        if (adapter.getItem(position).getId_cadastro_servidor() <= 0 || (adapter.getItem(position).getId_cadastro_servidor() > 0 && adapter.getItem(position).getAlterado().equals("S"))) {
            if (adapter.getSelectedItensCount() == 0 || (adapter.getItensSelecionados().get(adapter.getSelectedItensCount() - 1).getAlterado().equals(adapter.getItem(position).getAlterado()))) {
                if (actionMode == null) {
                    actionMode = startActionMode(new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            mode.getMenuInflater().inflate(R.menu.menu_cadastro_cliente, menu);
                            if (adapter.getItem(position).getId_cadastro_servidor() > 0)
                                menu.findItem(R.id.excluir_cliente).setVisible(false);
                            return true;
                        }
                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }
                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.subir_cliente:
                                    if (adapter.getSelectedItensCount() > 1)
                                        showActionDialog(item.getItemId(), 0, "Deseja enviar os " + adapter.getSelectedItensCount() + " cadastros de clientes selecionandos ao servidor?");
                                    else
                                        showActionDialog(item.getItemId(), 0, "Deseja enviar o cliente " + adapter.getItensSelecionados().get(0).getNome_cadastro() + " ao servidor?");
                                    break;
                                case R.id.excluir_cliente:
                                    if (adapter.getSelectedItensCount() > 1)
                                        showActionDialog(item.getItemId(), 0, "Deseja excluir esses " + adapter.getSelectedItensCount() + " cadastros?");
                                    else
                                        showActionDialog(item.getItemId(), 0, "Deseja excluir o cadastro de " + adapter.getItensSelecionados().get(0).getNome_cadastro() + " ?");
                                    break;
                            }
                            return true;
                        }
                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            actionMode.finish();
                            adapter.clearSelection();
                            actionMode = null;
                        }
                    });
                }
                toggleSelection(position);
            } else {
                Toast.makeText(ActivityClienteDrawer.this, "O cadastro " + adapter.getItem(position).getNome_cadastro() + " não faz parte do grupo de seleção ativado", Toast.LENGTH_SHORT).show();
            }
        } else if (adapter.getItem(position).getF_cliente().equals("S")) {
            Toast.makeText(ActivityClienteDrawer.this, "Esse cliente já está efetivado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityClienteDrawer.this, "Cliente foi enviado e esta aguardando análise para efetivação", Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleSelection(int position) {
        adapter.toggleSelection(position);
        if (adapter.getSelectedItensCount() == 0) {
            actionMode.finish();
            actionMode = null;
            adapter.clearSelection();
        } else {
            actionMode.setTitle(String.valueOf(adapter.getSelectedItensCount()));
            actionMode.invalidate();
        }
    }

    private void novoCadastro() {
        db.deleteDados("TBL_CADASTRO", "FINALIZADO = ?", new String[]{"N"});
        ClienteHelper.getCliente().setNome_municipio("");
        ClienteHelper.setPosicaoMunicipio(0);
        startActivity(new Intent(ActivityClienteDrawer.this, ActivityCpfCnpjCliente.class).putExtra("novo", 1));
    }

    private void continuaCadastro() {
        ClienteHelper.setCliente(clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO = 'N'").get(0));
        if (ClienteHelper.getCliente().getId_cadastro() > 0) {
            if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                final ProgressDialog progress = new ProgressDialog(ActivityClienteDrawer.this);
                progress.setTitle("Aguarde");
                progress.setMessage("Carregando anexos do cliente");
                progress.setCancelable(false);
                progress.show();
                final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                Thread a = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(ActivityClienteDrawer.this, ClienteHelper.getCliente().getId_cadastro());
                        ClienteHelper.setListaCadastroAnexo(listaCadastroAnexo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.dismiss();
                            }
                        });
                    }
                });
                a.start();
            }
        } else {
            if (ClienteHelper.getCliente().getListaCadastroAnexo().size() > 0)
                ClienteHelper.setListaCadastroAnexo(ClienteHelper.getCliente().getListaCadastroAnexo());
        }
        startActivity(new Intent(ActivityClienteDrawer.this, CadastroClienteMain.class).putExtra("novo", 1));
    }

    public void addUpdateClienteAPI(List<Cliente> listaClientes) {
        if ( !usuarioIsLogado() )
            return;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Falha ao definir a versão do aplicativo para acesso a conexão!!", Toast.LENGTH_LONG).show();
            return;
        }
        String versaoApp = pInfo.versionName.substring(0, 5);
        final CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
        try {
            for (Cliente cliente : listaClientes)
                try {
                    cliente.setListaCadastroAnexo(cadastroAnexoDAO.getLista(cliente.getId_cadastro(), 0));
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        getConfiguracao();
        Call<List<Cliente>> call = getRotasAPI(1, configuracao, versaoApp, listaClientes );
        if ( call == null)
            return;
        showHideProgressDialog(true);
        //Call<List<Cliente>> call = apiRetrofit.salvarClientes(cabecalho, listaClientes);
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                switch (response.code()) {
                    case 200:
                        List<Cliente> clientesRetorno = response.body();
                        if (clientesRetorno != null && clientesRetorno.size() > 0) {
                            for (Cliente cliente : clientesRetorno) {
                                if (cliente.getId_cadastro_servidor() <= 0)
                                    util.showFeedbackDialog("Erro ao sincronizar o cadastro do cliente ", true, false, R.drawable.ic_baseline_error_outline_24 , ActivityClienteDrawer.this);
                            }
                            updateCliente(clientesRetorno, listaClientes);
                        }else
                            limpaListaSelecionados();
                        showHideProgressDialog(false);
                        break;
                    case 500:
                        limpaListaSelecionados();
                        showHideProgressDialog(false);
                        util.showFeedbackDialog("Erro ao sincronizar o(s) cadastro(s)!", true, false, R.drawable.ic_baseline_error_outline_24 , ActivityClienteDrawer.this);
                        break;
                    default:
                        showHideProgressDialog(false);
                        util.showFeedbackDialog("Erro ao sincronizar o(s) cadastro(s)!\n(" + response.code() + ")", true, false, R.drawable.ic_baseline_error_outline_24 , ActivityClienteDrawer.this);
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                showHideProgressDialog(false);
                try {
                    if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty()) {
                         addUpdateClienteAPI2(listaClientes, versaoApp);
                    } else {
                        showHideProgressDialog(false);
                        limpaListaSelecionados();
                        Toast.makeText(context, "Falha ao sincronizar o(s) cadastro(s)!\n" + t.getMessage() + "\n" , Toast.LENGTH_SHORT).show();
                    }
                } catch ( NullPointerException e) {
                    showHideProgressDialog(false);
                    limpaListaSelecionados();
                    Toast.makeText(context, "Falha ao sincronizar o(s) cadastro(s)!\n" + t.getMessage() + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addUpdateClienteAPI2(List<Cliente> listaClientes, String versaoApp) {
        showHideProgressDialog(true);
        Call<List<Cliente>> call = getRotasAPI(2, configuracao, versaoApp, listaClientes );
        if ( call == null)
            return;
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                switch (response.code()) {
                    case 200:
                        List<Cliente> clientesRetorno = response.body();
                        if (clientesRetorno != null && clientesRetorno.size() > 0)
                            updateCliente(clientesRetorno, listaClientes);
                        else
                            limpaListaSelecionados();
                        showHideProgressDialog(false);
                        break;
                    case 500:
                        limpaListaSelecionados();
                        showHideProgressDialog(false);
                        util.showFeedbackDialog("Erro ao sincronizar o(s) cadastro(s)!!", true, false, R.drawable.ic_baseline_error_outline_24 , ActivityClienteDrawer.this);
                        break;
                    default:
                        showHideProgressDialog(false);
                        util.showFeedbackDialog("Erro ao sincronizar o(s) cadastro(s)!!\n(" + response.code() + ")", true, false, R.drawable.ic_baseline_error_outline_24 , ActivityClienteDrawer.this);
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                showHideProgressDialog(false);
                limpaListaSelecionados();
                util.showFeedbackDialog("Falha ao sincronizar o(s) cadastro(s)!!\n" + t.getMessage(), true, false, R.drawable.ic_baseline_error_outline_24 , ActivityClienteDrawer.this);
            }
        });
    }

    public boolean usuarioIsLogado() {
        try {
            if (UsuarioHelper.getUsuario() == null || (UsuarioHelper.getUsuario().getId_usuario() == null || UsuarioHelper.getUsuario().getId_usuario().isEmpty())) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityClienteDrawer.this));
                if (UsuarioHelper.getUsuario() == null) {
                    Toast.makeText(this, "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } catch ( NullPointerException e) {
            Toast.makeText(this, "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        } catch ( Exception e) {
            Toast.makeText(this, "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Call<List<Cliente>> getRotasAPI(int idRota, Configuracao configuracao, String versaoApp, List<Cliente> listaClientes) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                //if (versaoApp.substring(0, 3).equalsIgnoreCase("1.0"))
                //    return apiRotas.addClientes(cabecalho, listaClientes);
                //else
                return apiRotas.salvarClientes(cabecalho, listaClientes);
            } catch ( NullPointerException e) {
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
                    return apiRotas.salvarClientes(cabecalho, listaClientes);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch ( Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if (idRota == 2){
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                return apiRotas2.salvarClientes(cabecalho, listaClientes);
            } catch ( NullPointerException e) {
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
                    return apiRotas2.salvarClientes(cabecalho, listaClientes);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }

            } catch ( Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return  null;
    }

    /*public void downLoadClienteAPI(int id_cadastro_servidor, int position) {
        if ( !usuarioIsLogado() )
            return;
        showHideProgressDialog(true);
        Rotas apiRetrofit = Api.buildRetrofit(false);
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<List<Cliente>> call = apiRetrofit.buscarCliente( UsuarioHelper.getUsuario().getId_usuario(), id_cadastro_servidor, cabecalho );
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                switch (response.code()) {
                    case 200:
                        List<Cliente> lista1 = response.body();
                        if (lista1 != null && lista1.size() > 0) {
                            db.deleteDados("TBL_CADASTRO", "ID_CADASTRO_SERVIDOR = ?", new String[]{String.valueOf(lista1.get(0).getId_cadastro_servidor())});
                            lista1.get(0).setAlterado("N");
                            clienteDAO.add(lista1.get(0));
                            lista.set(position, lista1.get(0));
                            Toast.makeText(ActivityClienteDrawer.this, "Cadastro atualizado com sucesso!!", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        } else
                            util.showMsgAlerta("Erro ao sincronizar o cadastro!", ActivityClienteDrawer.this);
                        showHideProgressDialog(false);
                        break;
                    case 500:
                        showHideProgressDialog(false);
                        util.showMsgAlerta("Erro ao sincronizar o cadastro!", ActivityClienteDrawer.this);
                        break;
                    default:
                        showHideProgressDialog(false);
                        util.showMsgAlerta("Erro ao sincronizar o cadastro!\n(" + response.code() + ")", ActivityClienteDrawer.this);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                showHideProgressDialog(false);
                util.showMsgAlerta("Falha ao sincronizar o cadastro!", ActivityClienteDrawer.this);
            }
        });
    }
    */
    /*
    public void showMsgSimNao(final int id, int position, String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityClienteDrawer.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityClienteDrawer.this);
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
                alertDialog.dismiss();
                final Intent intent;
                switch (id) {
                    case R.id.lyDownload:
                    case R.id.btnDownload:
                        if (adapter.getItem(position).getId_cadastro_servidor() > 0) {
                            Toast.makeText(context, "Efetua a sincronização de dados, selecionando a opção 'Clientes' para efetuar a atualização dos dados", Toast.LENGTH_SHORT).show();
                         } else {
                            List<Cliente> lista1 = new ArrayList<>();
                            lista1.add( adapter.getItem(position));
                            addUpdateClienteAPI( lista1 );
                        }
                        break;
                    case R.id.lyChamada:
                    case R.id.btnChamada:
                        showTelefoneApp(position);
                        break;
                    case R.id.lyGps:
                    case R.id.btnGps:
                        showGpsApp(position);
                        break;
                    case R.id.lyEmail:
                    case R.id.btnEmail:
                        startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: " + adapter.getItem(position).getEmail_principal())));
                        break;
                    case R.id.lyFinanceiro:
                    case R.id.btnFinanceiro:
                        showActivityHistoricoFinanceiro(position);
                        break;
                    case R.id.lyNovoPedido:
                    case R.id.btnNovoPedido:
                        escolherEmpresaPedido(position);
                        break;
                    case R.id.subir_cliente:
                        addUpdateClienteAPI(adapter.getItensSelecionados());
                        break;
                    case R.id.excluir_cliente:
                        deleteCliente();
                        break;
                }
            }
        });
        alertDialog.show();
    }
    */
    public void exibirDialogListaOld(String mensagem, int position) {
        String[] opcoes = {"INICIAR NOVO PEDIDO", "CONSULTAR DADOS DO CADASTRO",
                "CONSULTAR HISTÓRICO FINANCEIRO", "CONSULTAR HISTÓRICO DE PEDIDOS", "CANCELAR"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(mensagem);
        builder.setItems(opcoes, (dialog, which) -> {
            // o parâmetro 'which' é o índice (0, 1, 2, 3...)
            switch (which) {
                case 0:
                    dialog.dismiss(); //<-- change it with ur code
                    escolherEmpresaPedido(position);/* Ação 1 */ break;
                case 1:
                    dialog.dismiss();
                    Intent intent;
                    if (adapter.getItem(position).getF_cliente().equals("S")) {
                        intent = new Intent(ActivityClienteDrawer.this, ActivityContato.class);
                        ClienteHelper.setCliente(adapter.getItem(position));
                        if (adapter.getItem(position).getId_cadastro_servidor() > 0)
                            intent.putExtra("vizualizacao", 1);
                    } else {
                        intent = new Intent(ActivityClienteDrawer.this, CadastroClienteMain.class);
                        intent.putExtra("novo", 1);
                        ClienteHelper.setCliente(adapter.getItem(position));
                    }
                    System.gc();
                    startActivity(intent);
                    /* Ação 2 */ break;
                case 2: /* Ação 3 */ break;
                case 3: /* Ação 4 */ break;
                case 4:
                    dialog.dismiss();/* Ação 5 */ break;
            }
        });

        builder.show();
    }



    public void exibirDialogAcoesCliente(final int position) {
        if (isFinishing() || isDestroyed()) return;

        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View headerView = inflater.inflate(R.layout.layout_dialog_menu_cliente, null);
        Cliente cliente = adapter.getItem(position);

        // Preenche o ícone no cabeçalho
        ImageView ivIcon = headerView.findViewById(R.id.ivMenuClienteIcon);
        if (ivIcon != null) {
            ivIcon.setColorFilter(androidx.core.content.ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        // Resumo do cliente no painel de ações
        TextView tvNome = headerView.findViewById(R.id.tvResumoClienteNome);
        TextView tvDoc = headerView.findViewById(R.id.tvResumoClienteDoc);
        TextView tvCidade = headerView.findViewById(R.id.tvResumoClienteCidade);
        if (tvNome != null) tvNome.setText(cliente.getNome_cadastro() != null ? cliente.getNome_cadastro().trim() : "");
        if (tvDoc != null) {
            String doc = cliente.getCpf_cnpj() != null ? cliente.getCpf_cnpj() : "";
            if (cliente.getPessoa_f_j() != null && cliente.getPessoa_f_j().equalsIgnoreCase("F")) {
                tvDoc.setText(MascaraUtil.mascaraCPF(doc));
            } else {
                tvDoc.setText(MascaraUtil.mascaraCNPJ(doc));
            }
        }
        if (tvCidade != null) tvCidade.setText((cliente.getNome_municipio() != null ? cliente.getNome_municipio().trim() : "") + " - " + (cliente.getEndereco_uf() != null ? cliente.getEndereco_uf() : ""));

        String[] opcoes = {"INICIAR NOVO PEDIDO", "CONSULTAR DADOS DO CADASTRO", "CONSULTAR HISTÓRICO FINANCEIRO", "CONSULTAR HISTÓRICO DE PEDIDOS", "FECHAR"};

        // Uso de androidx.appcompat.app.AlertDialog para evitar problemas de tema com MaterialAlertDialogBuilder
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setCustomTitle(headerView)
                .setItems(opcoes, (dialog, which) -> {
                    switch (which) {
                        case 0: escolherEmpresaPedido(position); break;
                        case 1:
                            Intent intent;
                            if (cliente.getF_cliente().equals("S")) {
                                intent = new Intent(ActivityClienteDrawer.this, ActivityContato.class);
                                ClienteHelper.setCliente(cliente);
                                if (cliente.getId_cadastro_servidor() > 0) intent.putExtra("vizualizacao", 1);
                            } else {
                                intent = new Intent(ActivityClienteDrawer.this, CadastroClienteMain.class);
                                intent.putExtra("novo", 1);
                                ClienteHelper.setCliente(cliente);
                            }
                            startActivity(intent);
                            break;
                        case 2: showActivityHistoricoFinanceiro(position); break;
                        case 3:
                            startActivity(new Intent(context, ActivitySincronia.class).putExtra("autoSincroniza", true)
                                    .putExtra("idcliente", cliente.getId_cadastro_servidor())
                                    .putExtra("pedidosenviados", "S"));
                            break;
                        case 4: dialog.dismiss(); break;
                    }
                })
                .show();
    }


    public void showMsgDialog(String mensagem, int position) {
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("RCK SISTEMAS ESPECIFICOS");
        alertDialog.setMessage(mensagem);
        alertDialog.setIcon(R.mipmap.ic_rck_logo_dialog_progressbar_2025);
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Novo pedido",
                (dialog, which) -> {
                    dialog.dismiss(); //<-- change it with ur code
                    alertDialog.dismiss();
                    escolherEmpresaPedido(position);

                });
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "Visualizar cadastro",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent;
                    if (adapter.getItem(position).getF_cliente().equals("S")) {
                        intent = new Intent(ActivityClienteDrawer.this, ActivityContato.class);
                        ClienteHelper.setCliente(adapter.getItem(position));
                        if (adapter.getItem(position).getId_cadastro_servidor() > 0)
                            intent.putExtra("vizualizacao", 1);
                    } else {
                        intent = new Intent(ActivityClienteDrawer.this, CadastroClienteMain.class);
                        intent.putExtra("novo", 1);
                        ClienteHelper.setCliente(adapter.getItem(position));
                    }
                    System.gc();
                    startActivity(intent);


                });
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Visualizar histórico de pedidos",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showSincDialog();
                        /*
                        startActivity(new Intent(ActivityClienteDrawer.this, ActivityPedidoEnviado.class)
                                .putExtra("autoSincroniza", true)
                                .putExtra("idcliente", adapter.getItem(position).getId_cadastro_servidor())
                                .putExtra("pedidospendentes", "S"));
                        dialog.dismiss();

                         */
                    }
                } );
        alertDialog.show();
    }
    public void showMsgDialog2(String mensagem, int position) {
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("RCK SISTEMAS ESPECIFICOS");
        alertDialog.setMessage(mensagem);
        alertDialog.setIcon(R.mipmap.ic_rck_logo_dialog_progressbar_2025);
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Novo pedido",
                (dialog, which) -> {
                    dialog.dismiss(); //<-- change it with ur code
                    alertDialog.dismiss();
                    escolherEmpresaPedido(position);

                });
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "Visualizar cadastro",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent;
                    if (adapter.getItem(position).getF_cliente().equals("S")) {
                        intent = new Intent(ActivityClienteDrawer.this, ActivityContato.class);
                        ClienteHelper.setCliente(adapter.getItem(position));
                        if (adapter.getItem(position).getId_cadastro_servidor() > 0)
                            intent.putExtra("vizualizacao", 1);
                    } else {
                        intent = new Intent(ActivityClienteDrawer.this, CadastroClienteMain.class);
                        intent.putExtra("novo", 1);
                        ClienteHelper.setCliente(adapter.getItem(position));
                    }
                    System.gc();
                    startActivity(intent);


                });
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Consultar histórico de pedidos",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showSincDialog();
                        /*
                        startActivity(new Intent(ActivityClienteDrawer.this, ActivityPedidoEnviado.class)
                                .putExtra("autoSincroniza", true)
                                .putExtra("idcliente", adapter.getItem(position).getId_cadastro_servidor())
                                .putExtra("pedidospendentes", "S"));
                        dialog.dismiss();

                         */
                    }
                } );
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } );
        alertDialog.show();
    }


    public void showSincDialog() {
        ViewGroup viewGroup = ((Activity) context).findViewById(R.id.rootx);
        View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.dialog_alert_atualiza_hist_pedido, viewGroup, true);
        new androidx.appcompat.app.AlertDialog.Builder(ActivityClienteDrawer.this)
                .setTitle("RCK SISTEMAS ESPECIFICOS")
                .setMessage("Atualizando a o histórico de pedidos!")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();

    }
    private void showTelefoneApp(int position) {
        try {
            String telefone = adapter.getItem(position).getTelefone_principal() != null ? adapter.getItem(position).getTelefone_principal() : "";
            if (MascaraUtil.isCelular(telefone)) {
                // LÓGICA WHATSAPP
                String telLimpo = telefone.replaceAll("[^0-9]", "");
                if (telLimpo.length() == 11) {
                    telLimpo = "55" + telLimpo;
                }
                String saudacao = MascaraUtil.getSaudacaoHorario();
                String url = "https://api.whatsapp.com/send?phone=" + telLimpo + "&text=" + URLEncoder.encode(saudacao + "!", "UTF-8");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setPackage("com.whatsapp").setData(Uri.parse(url));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                } else {
                    // Se não tiver WhatsApp, tenta dialer
                    startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + telLimpo)));
                }
            } else {
                // LÓGICA TELEFONE FIXO (ORIGINAL)
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String telLimpo = telefone.replaceAll("[^0-9]", "");
                if (telLimpo.length() == 10 || telLimpo.length() == 11)
                    intent.setData(Uri.parse("tel:" + "0" + telLimpo));
                else
                    intent.setData(Uri.parse("tel:" + telLimpo));
                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Sem suporte para efetuar ligação!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGpsApp(int position) {
        String endereco = adapter.getItem(position).getEndereco().replace(",", "")
                + ", " + adapter.getItem(position).getEndereco_numero()
                + setCepGPS(adapter.getItem(position).getEndereco_cep())
                + setCidadeGPS(adapter.getItem(position).getNome_municipio())
                + setUfGPS(adapter.getItem(position).getEndereco_uf());
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + endereco + "&waypoints=AVENIDA DUQUE DE CAXIAS, 5000, CAMPO GRANDE MS" );
        startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri).setPackage("com.google.android.apps.maps"));


    }

    private String  setCepGPS(String cep) {
        if ( cep == null || cep.isEmpty()) {
            return "";
        } else
            return  " - " + cep;

    }
    private String  setCidadeGPS(String cidade) {
        if ( cidade == null || cidade.isEmpty()) {
            return "";
        } else
            return  " ," + cidade;

    }
    private String  setUfGPS(String uf) {
        if ( uf == null || uf.isEmpty()) {
            return "";
        } else
            return  " " + uf;

    }

    private void showActivityHistoricoFinanceiro(int position) {
        ClienteHelper.setCliente(adapter.getItem(position));
        HistoricoFinanceiroHelper.setCliente(ClienteHelper.getCliente());
        System.gc();
        startActivity(new Intent(ActivityClienteDrawer.this, ActivityHistoricoFinanceiro.class));
    }

    private void escolherEmpresaPedido(final int position) {
        List<EmpresaParametro> listaEmpresas = new EmpresaParametroDAO(db).getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        if (listaEmpresas != null && listaEmpresas.size() > 1) {
            String[] nomesEmpresas = new String[listaEmpresas.size()];
            for (int i = 0; i < listaEmpresas.size(); i++) {
                nomesEmpresas[i] = listaEmpresas.get(i).getRazao_social() != null ? listaEmpresas.get(i).getRazao_social() : listaEmpresas.get(i).getRazao_social();
            }

            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Selecione a Empresa")
                    .setItems(nomesEmpresas, (dialog, which) -> {
                        showActivityPedido(position, String.valueOf(listaEmpresas.get(which).getId_empresa()));
                    })
                    .setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            showActivityPedido(position, "");
        }
    }

    private void showActivityPedido(int position, String idEmpresa) {
        Cliente cliente = adapter.getItem(position);
        ClienteHelper.setCliente(cliente);
        try {
            if (cliente.getAtivo().equalsIgnoreCase("N")) {
                Toast.makeText(this, "Este cliente esta inativado/bloqueado!", Toast.LENGTH_SHORT).show();
                return;
            } else if (cliente.getIdCategoria() <= 0) {
                util.showFeedbackDialog("Este cliente não tem uma categoria definida", true, false, R.drawable.ic_alerta_mensagem_color_primary, ActivityClienteDrawer.this);
                return;
            }

            // NOVO: Verificação de Débito com Informativo em Destaque (Ação 3 - Pelo Hub de Opções)
            if (verificarDebitoCliente(cliente, () -> continuarParaPedido(idEmpresa))) {
                return;
            }

            if (configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                float limiteCredito = Float.parseFloat(cliente.getLimite_credito());
                float limitUtilizado = Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo());
                if ((limiteCredito - limitUtilizado) < 0.00f) {
                    showActionDialog("Cliente sem limite disponivel para compra a prazo! Pedido só podera ser feito para pagamento à vista!\nDeseja continuar?", position, false, idEmpresa);
                    return;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        continuarParaPedido(idEmpresa);
        //exibirDialogRaioXCliente(cliente, () -> continuarParaPedido(idEmpresa));
    }

    /**
     * PROTÓTIPO TEMPORÁRIO DE TESTE: Diálogo Raio-X de Compras do Cliente (Pré-Análise)
     */
    private void exibirDialogRaioXCliente(Cliente cliente, Runnable onContinuar) {
        if (cliente == null) {
            onContinuar.run();
            return;
        }

        int idCad = cliente.getId_cadastro_servidor() > 0 ? cliente.getId_cadastro_servidor() : cliente.getId_cadastro();

        // 1. Consulta Indicadores do Cliente no SQLite local
        int totalPedidos = db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE (ID_CADASTRO = " + idCad + " OR ID_CADASTRO = " + cliente.getId_cadastro() + ") AND EXCLUIDO = 'N'");
        double totalFaturado = db.soma("SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO WHERE (ID_CADASTRO = " + idCad + " OR ID_CADASTRO = " + cliente.getId_cadastro() + ") AND EXCLUIDO = 'N'");
        double ticketMedio = totalPedidos > 0 ? (totalFaturado / totalPedidos) : 0;

        String dataUltimaStr = db.consultaDados("SELECT MAX(DATA_EMISSAO) FROM TBL_WEB_PEDIDO WHERE (ID_CADASTRO = " + idCad + " OR ID_CADASTRO = " + cliente.getId_cadastro() + ") AND EXCLUIDO = 'N'", "MAX(DATA_EMISSAO)");
        String dataPrimeiraStr = db.consultaDados("SELECT MIN(DATA_EMISSAO) FROM TBL_WEB_PEDIDO WHERE (ID_CADASTRO = " + idCad + " OR ID_CADASTRO = " + cliente.getId_cadastro() + ") AND EXCLUIDO = 'N'", "MIN(DATA_EMISSAO)");

        String dataUltimaFormatada = "Não informada";
        String freqMediaStr = "Sem histórico suficiente";
        long diasSemComprar = -1;

        SimpleDateFormat sdfSql = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
        SimpleDateFormat sdfBr = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.US);

        try {
            if (dataUltimaStr != null && !dataUltimaStr.trim().isEmpty()) {
                java.util.Date dUltima = sdfSql.parse(dataUltimaStr.substring(0, 10));
                dataUltimaFormatada = sdfBr.format(dUltima);

                long diffMs = new java.util.Date().getTime() - dUltima.getTime();
                diasSemComprar = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(diffMs);
                if (diasSemComprar >= 0) {
                    dataUltimaFormatada += " (há " + diasSemComprar + " dias)";
                }

                if (totalPedidos >= 2 && dataPrimeiraStr != null && !dataPrimeiraStr.trim().isEmpty()) {
                    java.util.Date dPrimeira = sdfSql.parse(dataPrimeiraStr.substring(0, 10));
                    long diffTotalMs = dUltima.getTime() - dPrimeira.getTime();
                    long totalDiasPeriodo = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(diffTotalMs);
                    long mediaDias = totalDiasPeriodo / (totalPedidos - 1);
                    if (mediaDias > 0) {
                        freqMediaStr = "A cada " + mediaDias + " dia(s)";
                    } else {
                        freqMediaStr = "Recorrente / Diária";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Consulta Top 5 Produtos Comprados (Curva A Financeira)
        StringBuilder sbTopProdutos = new StringBuilder();
        String sqlTop = "SELECT I.NOME_PRODUTO, SUM(I.QUANTIDADE) AS QTD, SUM(I.VALOR_TOTAL) AS VALOR " +
                "FROM TBL_WEB_PEDIDO_ITENS AS I INNER JOIN TBL_WEB_PEDIDO AS P ON (P.ID_WEB_PEDIDO = I.ID_PEDIDO OR P.ID_PEDIDO = I.ID_PEDIDO) " +
                "WHERE (P.ID_CADASTRO = " + idCad + " OR P.ID_CADASTRO = " + cliente.getId_cadastro() + ") AND P.EXCLUIDO = 'N' " +
                "GROUP BY I.ID_PRODUTO, I.NOME_PRODUTO ORDER BY VALOR DESC LIMIT 5";

        Cursor cursor = db.listaDados(sqlTop);
        int rank = 1;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nomeProd = cursor.getString(cursor.getColumnIndex("NOME_PRODUTO"));
                @SuppressLint("Range") double qtd = cursor.getDouble(cursor.getColumnIndex("QTD"));
                @SuppressLint("Range") double valor = cursor.getDouble(cursor.getColumnIndex("VALOR"));

                sbTopProdutos.append(rank).append(". ").append(nomeProd).append("\n")
                        .append("   • Volume: ").append(String.format(java.util.Locale.US, "%.0f", qtd)).append(" un | ")
                        .append("Total: ").append(MascaraUtil.mascaraRealSimboloD(valor)).append("\n\n");
                rank++;
            } while (cursor.moveToNext());
            cursor.close();
        }

        if (sbTopProdutos.length() == 0) {
            sbTopProdutos.append("Nenhum histórico de produtos faturados para este cliente.");
        }

        // 3. Exibe usando o padrão padronizado do DialogHelper
        DialogHelper.showRaioXClienteDialog(
                this,
                cliente.getNome_cadastro(),
                totalFaturado,
                totalPedidos,
                ticketMedio,
                dataUltimaFormatada,
                freqMediaStr,
                sbTopProdutos.toString().trim(),
                onContinuar
        );
    }

    /**
     * NOVO: Método centralizado para verificar o débito do cliente e exibir o alerta específico.
     */
    private boolean verificarDebitoCliente(Cliente cliente, Runnable onConfirm) {
        float vencido = 0.00f;
        float vencendo = 0.00f;
        try {
            vencido = Float.parseFloat(cliente.getFinanceiro_vencido() == null ? "0.00" : cliente.getFinanceiro_vencido());
            vencendo = Float.parseFloat(cliente.getFinanceiro_vencendo() == null ? "0.00" : cliente.getFinanceiro_vencendo());
        } catch (Exception ignored) {}

        // Se o cliente tem qualquer pendência (vencida ou a vencer), mostra o diálogo adaptado
        if (vencido > 0 || vencendo > 0) {
            DialogHelper.showDebtorAlertaDialog(context, vencido, vencendo, 
                    "O cliente possui pendências financeiras registradas. Deseja prosseguir com a abertura do novo pedido mesmo assim?", onConfirm);
            return true;
        }
        return false;
    }

    /**
     * NOVO: Método auxiliar para selecionar cliente em pedidos já iniciados.
     */
    private void selecionarClienteParaPedido(int position) {
        try {
            WebPedido webPedido = PedidoHelper.getWebPedido();
            Cliente novoCliente = adapter.getItem(position);

            if (webPedido.getCadastro() == null) {
                ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                activityPedidoMain.pegaCliente(novoCliente);
                activityPedidoMain.showProduto(true);
                Pedido2 pedido2 = new Pedido2();
                pedido2.pegaCliente(novoCliente);
                System.gc();
                finish();
            } else {
                if (webPedido.getCadastro().getCpf_cnpj().equalsIgnoreCase(novoCliente.getCpf_cnpj())) {
                    ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                    activityPedidoMain.pegaCliente(novoCliente);
                    Pedido2 pedido2 = new Pedido2();
                    pedido2.pegaCliente(novoCliente);
                    System.gc();
                    finish();
                } else {
                    showActionDialog(webPedido.getCadastro(), position);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void continuarParaPedido(String idEmpresa) {
        Cliente cliente = ClienteHelper.getCliente();
        String razao = cliente != null ? cliente.getNome_cadastro() : "";
        String fantasia = cliente != null ? cliente.getNome_fantasia() : "";
        String end = cliente != null ? (cliente.getEndereco() + ", " + cliente.getEndereco_numero() + " - " + cliente.getEndereco_bairro() + " - " + cliente.getNome_municipio() + "/" + cliente.getEndereco_uf()) : "";
        if ( configuracao.getTrab_com_checkin_app().equalsIgnoreCase("N")) {
            new PedidoHelper().limparDados();
            if (cliente != null) {
                ClienteHelper.setCliente(cliente);
            }
            Intent intent = new Intent(ActivityClienteDrawer.this, ActivityPedidoMain.class);
            intent.putExtra("id_empresa", idEmpresa);
            intent.putExtra("id_visita_app", 0);
            startActivity(intent);
            return;
        }

        // GERA UUID ÚNICO DO CHECK-IN PARA VINCULAR AO PEDIDO OU À NÃO-VENDA
        String idVisitaApp = java.util.UUID.randomUUID().toString();

        DialogHelper.showCheckInClienteDialog(
                this,
                razao,
                fantasia,
                end,
                (locationData, fotoBytes) -> {
                    // Após confirmar Check-In, abre o diálogo de intenção de atendimento
                    DialogHelper.showOpcoesPosCheckInDialog(
                            this,
                            () -> {
                                // Opção 1: CONTINUAR PARA O PEDIDO (Grava a visita inicial e vincula id_visita_app)
                                try {
                                    com.example.rcksuporte05.rcksistemas.model.WebVisita visita = new com.example.rcksuporte05.rcksistemas.model.WebVisita();
                                    visita.setIdVisitaApp(idVisitaApp);
                                    if (com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper.getUsuario() != null && com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper.getUsuario().getId_quando_vendedor() != null) {
                                        try {
                                            visita.setIdVendedor(Integer.parseInt(com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper.getUsuario().getId_quando_vendedor()));
                                        } catch (Exception ignored) {}
                                    }
                                    if (cliente != null) {
                                        visita.setIdClienteServidor(cliente.getId_cadastro_servidor());
                                    }
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

                                    visita.setStatusVisita("A"); // 'A' = Atendimento em andamento
                                    visita.setVersaoApp(BuildConfig.VERSION_NAME);

                                    com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO visitaDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db);
                                    visitaDAO.salvarVisita(visita);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                new PedidoHelper().limparDados();
                                if (cliente != null) {
                                    ClienteHelper.setCliente(cliente);
                                }
                                Intent intent = new Intent(ActivityClienteDrawer.this, ActivityPedidoMain.class);
                                intent.putExtra("id_empresa", idEmpresa);
                                intent.putExtra("id_visita_app", idVisitaApp);
                                startActivity(intent);
                            },
                            () -> {
                                // Opção 2: REGISTRAR NÃO VENDA
                                DialogHelper.showRegistroNaoVendaDialog(
                                        this,
                                        db,
                                        razao,
                                        fantasia,
                                        end,
                                        (motivosSelecionados, observacao) -> {
                                            StringBuilder sbMotivos = new StringBuilder();
                                            if (motivosSelecionados != null && !motivosSelecionados.isEmpty()) {
                                                for (int i = 0; i < motivosSelecionados.size(); i++) {
                                                    if (i > 0) sbMotivos.append(", ");
                                                    sbMotivos.append(motivosSelecionados.get(i));
                                                }
                                            } else {
                                                sbMotivos.append("Nenhum motivo marcado");
                                            }

                                            // GRAVAÇÃO COMPLETA DA NÃO VENDA NA TABELA TBL_WEB_VISITA
                                            try {
                                                com.example.rcksuporte05.rcksistemas.model.WebVisita visita = new com.example.rcksuporte05.rcksistemas.model.WebVisita();
                                                visita.setIdVisitaApp(idVisitaApp);
                                                if (com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper.getUsuario() != null && com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper.getUsuario().getId_quando_vendedor() != null) {
                                                    try {
                                                        visita.setIdVendedor(Integer.parseInt(com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper.getUsuario().getId_quando_vendedor()));
                                                    } catch (Exception ignored) {}
                                                }
                                                if (cliente != null) {
                                                    visita.setIdClienteServidor(cliente.getId_cadastro_servidor());
                                                }
                                                visita.setDataHoraCheckin(db.pegaDataHoraAtual());

                                                // GRAVA COORDENADAS GPS DO CHECK-IN
                                                if (locationData != null && locationData.hasLocation) {
                                                    visita.setLatitudeCheckin(String.valueOf(locationData.latitude));
                                                    visita.setLongitudeCheckin(String.valueOf(locationData.longitude));
                                                    visita.setAltitudeCheckin(String.valueOf(locationData.altitude));
                                                    visita.setPrecisaoCheckin(String.valueOf(locationData.accuracy));
                                                }

                                                // GRAVA FOTO CAPTURADA DO CHECK-IN (SE HOUVER)
                                                if (fotoBytes != null && fotoBytes.length > 0) {
                                                    visita.setFotoCheckin1(fotoBytes);
                                                }

                                                visita.setStatusVisita("N"); // 'N' = Não Venda
                                                visita.setMotivoNaoVenda(sbMotivos.toString());
                                                visita.setObservacao(observacao);
                                                visita.setVersaoApp(BuildConfig.VERSION_NAME);

                                                com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO visitaDAO = new com.example.rcksuporte05.rcksistemas.DAO.WebVisitaDAO(db);
                                                visitaDAO.salvarVisita(visita);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            String msgFeedback = "Check-in e Não Venda registrados com sucesso!\n\n" +
                                                    "• Cliente: " + razao + "\n" +
                                                    "• Motivo(s): " + sbMotivos.toString() +
                                                    (!observacao.isEmpty() ? "\n• Obs: " + observacao : "");

                                            DialogHelper.showFeedbackDialog(
                                                    this,
                                                    "VISITA REGISTRADA",
                                                    msgFeedback,
                                                    true,
                                                    R.drawable.ic_baseline_check_circle_outline_24_color_primary,
                                                    null,
                                                    null
                                            );
                                        }
                                );
                            }
                    );
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraPhotoHelper.REQUEST_CAMERA_CODE) {
            CameraPhotoHelper.handleActivityResult(requestCode, resultCode);
        }
    }

    private void delClienteFirebase(String cnpj, Cliente webCliente) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        try {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                db.collection(cnpj).document("TBL_WEB_CLIENTE").collection(String.valueOf(webCliente.getId_vendedor())).document(webCliente.getId_cliente_app_fdb()).delete().addOnSuccessListener(documentReference -> {
                        }
                ).addOnFailureListener(e -> {
                });

            }, 500);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCliente() {
        ClienteDAO clienteDAO = new ClienteDAO(db);
        int qtde = 0;
        for (Cliente cliente : adapter.getItensSelecionados())
            if (clienteDAO.delete(cliente, "0") <= 0) {
                util.showFeedbackDialog("Falha ao excluir o cadastro do cliente\n" + cliente.getNome_cadastro() + "\nExclusão cancelada! ", true, false, R.drawable.ic_baseline_error_outline_24 , ActivityClienteDrawer.this);
                break;
            }else {
                qtde++;
                delClienteFirebase(configuracao.getCnpj_empresa(), cliente);
            }
        if ( qtde == 1) {
            showFeedbackDialog(qtde + " cadastro excluido com sucesso!", true);
        } else if (qtde > 1) {
            showFeedbackDialog(qtde + " cadastros excluidos com sucesso!", true);
        } else {
            showFeedbackDialog("Falha ao excluir cadastro(s)!", false);
        }
        actionMode.finish();
        adapter.clearSelection();
        setRecyclerView(getListaClientes(2, "","","","", ""), 2);
        actionMode = null;
        onResume();
    }

    private void updateCliente(List<Cliente> clientesRetorno, List<Cliente> clientesAntigo) {
        ClienteDAO clienteDAO = new ClienteDAO(db);
        WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
        CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
        for (Cliente cliente : clientesRetorno) {
            cliente.setAlterado("N");
            if (clienteDAO.update(cliente) > 0) {
                for (Cliente clienteAntigo : clientesAntigo)
                    if (clienteAntigo.getCpf_cnpj().equalsIgnoreCase(cliente.getCpf_cnpj())) {
                        webPedidoDAO.updateClientePedido(clienteAntigo.getId_cadastro(), cliente.getId_cadastro_servidor(), cliente.getNome_cadastro());
                        break;
                    }
                delClienteFirebase(configuracao.getCnpj_empresa(), cliente);

            }
            try {
                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(cliente.getId_cadastro())});
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cliente.getListaCadastroAnexo().size() > 0) {
                for (CadastroAnexo cadastroAnexo : cliente.getListaCadastroAnexo()) {
                    if (cadastroAnexo.getExcluido().equals("N"))
                        cadastroAnexoDAO.addUpdate(cadastroAnexo);
                    if (cadastroAnexo.getExcluido().equals("S"))
                        try {
                            db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_ANEXO = ?", new String[]{String.valueOf(cadastroAnexo.getIdAnexo())});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            } else
                try {
                    db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(cliente.getId_cadastro())});
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
        try {
            limpaListaSelecionados();
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        setRecyclerView(getListaClientes(2, "", "", "", "",""), 2);
        Toast.makeText(ActivityClienteDrawer.this, "Cadastro(s) sincronizado(s) com sucesso!", Toast.LENGTH_SHORT).show();
        onResume();
    }

    private void limpaListaSelecionados() {
        try {
            adapter.clearSelection();
            actionMode.finish();
            actionMode = null;
        } catch (NullPointerException ignored) {
        }
    }

    private void showHideProgressDialog(boolean visivel) {
        if (!visivel) {
            progress.dismiss();
            return;
        }
        progress = new ProgressDialog(ActivityClienteDrawer.this);
        progress.setMessage("Aguarde...!");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == 7) {
            drawerLayout.closeDrawers();
            showDialogFiltros();
            return true;
        } else if (item.getItemId() == 8) {
            drawerLayout.closeDrawers();
            inserirCliente();//;showRotaFiltros();
            return true;
        }

        setRecyclerView(getListaClientes(item.getItemId(),"","","","", ""), 2);
        drawerLayout.closeDrawers();
        return true;
    }

    /*
    public void showMsgSimNao(  String mensagem, int position, boolean pedidoIniciado) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityClienteDrawer.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ActivityClienteDrawer.this);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
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
                alertDialog.dismiss();
                ClienteHelper.setCliente(adapter.getItem(position));
                if (!pedidoIniciado) {
                    ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                    activityPedidoMain.pegaCliente(adapter.getItem(position));
                    Pedido2 pedido2 = new Pedido2();
                    pedido2.pegaCliente(adapter.getItem(position));
                    startActivity(new Intent(ActivityClienteDrawer.this, ActivityPedidoMain.class));
                } else {
                    ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                    activityPedidoMain.pegaCliente(adapter.getItem(position));
                    Pedido2 pedido2 = new Pedido2();
                    pedido2.pegaCliente(adapter.getItem(position));
                    System.gc();
                    finish();
                }
            }
        });
        alertDialog.show();
    }
*/
    public void showDialogFiltros( ) {
        ViewGroup viewGroup = this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( this).inflate(R.layout.dialog_outros_filtro_lista_cliente, null, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        //((ViewGroup) dialogView.getParent()).removeView(dialogView);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Spinner spnUF = (Spinner) dialogView.findViewById(R.id.spnUf);
        Spinner spnMunicipio = (Spinner) dialogView.findViewById(R.id.spnMunicipio);
        Spinner spnCategoria = (Spinner) dialogView.findViewById(R.id.spnCategoria);
        Spinner spnSegmento = (Spinner) dialogView.findViewById(R.id.spnSegmento);
        Spinner spnAtivoInativo = (Spinner) dialogView.findViewById(R.id.spnAtivoInativo);
        EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
        EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        ufAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
        spnUF.setAdapter(ufAdapter);

        spnUF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ufSelecionada = ufAdapter.getItem(i).toString();
                municipioAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                spnMunicipio.setAdapter(municipioAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for ( int i = 0; i < listaUf.length; i++) {
            if ( ufAdapter.getItem(i).toString().equalsIgnoreCase(empresaParametro.getEndereco_uf())) {
                spnUF.setSelection(i, false);
            }
        }
        spnMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                municipioSelecionado = municipioAdapter.getItem(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO(db);
            listaCategoria = categoriaDAO.getListaNovo(true);
            categoriaAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, listaCategoria);
            spnCategoria.setAdapter(categoriaAdapter);
            if (listaCategoria.size() == 2) {
                spnCategoria.setSelection(1);
            }
        } catch (CursorIndexOutOfBoundsException| NullPointerException e) {
        }

        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoriaSelecionada = String.valueOf(listaCategoria.get(spnCategoria.getSelectedItemPosition()).getIdCategoria());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        try {
            SegmentoDAO segmentoDAO = new SegmentoDAO(db);
            listaSegmento = segmentoDAO.getLista("SELECT * FROM TBL_CADASTRO_SETOR", true);
            segmentoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, listaSegmento);
            spnSegmento.setAdapter(segmentoAdapter);
            if (listaSegmento.size() == 2) {
                spnSegmento.setSelection(1);
            }
        } catch (CursorIndexOutOfBoundsException| NullPointerException e) {
        }

        spnSegmento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //segmentoSelecionada = segmentoAdapter.getItem(i).toString();
                segmentoSelecionada = String.valueOf(listaSegmento.get(spnSegmento.getSelectedItemPosition()).getIdSetor());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        List<Generico> listaAtivoInativo = new ArrayList<>();
        try {

            Generico generico = new Generico();
            generico.setId("T");
            generico.setNome("TODOS");
            listaAtivoInativo.add(generico);
            generico = new Generico();
            generico.setId("A");
            generico.setNome("ATIVO");
            listaAtivoInativo.add(generico);
            generico = new Generico();
            generico.setId("I");
            generico.setNome("INATIVOS/BLOQUEADOS");
            listaAtivoInativo.add(generico);

            //listaAtivoInativo.add("ATIVOS");
            //listaAtivoInativo.add("INATIVOS/BLOQUEADOS");
            ativoInativoAdapter = segmentoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, listaAtivoInativo);
            spnAtivoInativo.setAdapter(ativoInativoAdapter);
            spnAtivoInativo.setSelection(0);
        } catch (CursorIndexOutOfBoundsException| NullPointerException e) {
        }

        spnAtivoInativo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //segmentoSelecionada = segmentoAdapter.getItem(i).toString();
                statusSelecionado = String.valueOf(listaAtivoInativo.get(spnAtivoInativo.getSelectedItemPosition()).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Button btnConfirmar = (Button) dialogView.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ufSelecionada.isEmpty()) {
                    Toast.makeText(context, "Selecione um estado!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( municipioSelecionado.isEmpty()) {
                    Toast.makeText(context, "Selecione uma cidade!", Toast.LENGTH_SHORT).show();
                    return;
                }
               alertDialog.dismiss();
               setRecyclerView(getListaClientes(7, ufSelecionada, municipioSelecionado, categoriaSelecionada, segmentoSelecionada, statusSelecionado), 2);

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

    public void showRotaFiltros( ) {
        ViewGroup viewGroup = this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( this).inflate(R.layout.dialog_rotas_filtro_lista_cliente, viewGroup, true);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Spinner spnUF = (Spinner) dialogView.findViewById(R.id.spnUf);
        Spinner spnMunicipio = (Spinner) dialogView.findViewById(R.id.spnMunicipio);
        Spinner spnRegiao = (Spinner) dialogView.findViewById(R.id.spnRegiao);
        //Spinner spnBairro = (Spinner) dialogView.findViewById(R.id.spnBairro);
        EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
        EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        LoginDAO loginDAO = new LoginDAO(db);
        Usuario login = loginDAO.getUsuarioLocal("SELECT * FROM TBL_LOGIN");
        ufAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
        spnUF.setAdapter(ufAdapter);

        spnUF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ufSelecionada = ufAdapter.getItem(i).toString();
                listaMunicipio = new MunicipioDAO(db).getLista("SELECT * FROM TBL_CADASTRO_MUNICIPIO WHERE UF = '" +  ufSelecionada + "'");
                municipioAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_activated_1, listaMunicipio);
                spnMunicipio.setAdapter(municipioAdapter);
                for ( int x = 0; x < listaMunicipio.size(); x++) {
                    try {
                        if (listaMunicipio.get(x).getId().equalsIgnoreCase(String.valueOf(login.getUsuario_id_cidade()))) {
                            spnMunicipio.setSelection(x, false);
                            return;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for ( int i = 0; i < listaUf.length; i++) {
            try {
                if (ufAdapter.getItem(i).toString().equalsIgnoreCase(login.getUsuario_uf())) {
                    spnUF.setSelection(i, false);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        spnMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                municipioSelecionado = municipioAdapter.getItem(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*
        try {
            listaBairro = new ArrayList<>();
            bairroAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, listaBairro);
            spnBairro.setAdapter(bairroAdapter);
        } catch (CursorIndexOutOfBoundsException| NullPointerException e) {
        }


        spnBairro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //segmentoSelecionada = segmentoAdapter.getItem(i).toString();
                bairroSelecionado = String.valueOf(listaBairro.get(spnBairro.getSelectedItemPosition()).getNome());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        */

        try {
            RegiaoDAO regiaoDAO = new RegiaoDAO(db);
            listaRegiao = regiaoDAO.getLista("SELECT * FROM TBL_CADASTRO_REGIAO WHERE ID_VENDEDOR = " + UsuarioHelper.getUsuario().getId_quando_vendedor());
            regiaoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, listaRegiao);
            spnRegiao.setAdapter(regiaoAdapter);
            if (listaRegiao.size() > 0) {
                spnRegiao.setSelection(0);
            }
        } catch (CursorIndexOutOfBoundsException| NullPointerException e) {
        }



        spnRegiao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                regiaoSelecionada = String.valueOf(listaRegiao.get(spnRegiao.getSelectedItemPosition()).getId());
                BairroDAO bairroDAO = new BairroDAO(db);
                listaBairro = bairroDAO.getLista("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_REGIAO = '" + regiaoSelecionada + "' ORDER BY ORDEM", true);
                //bairroAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_activated_1, listaBairro);
                //spnBairro.setAdapter(bairroAdapter);
                //bairroAdapter.notifyDataSetChanged();

                adapterBairro = new ListaBairroCheckAdapter(listaBairro, new ListaBairroCheckAdapter.bairroListener() {
                    @Override
                    public void onClick(int position) {
                        listaBairro.get(position).setChecked(!listaBairro.get(position).isChecked());
                        if ( position == 0) {
                            if ( listaBairro.get(position).isChecked()) {
                                for ( int i = 1; i < listaBairro.size(); i++) {
                                    listaBairro.get(i).setChecked(true);
                                }
                            } else {
                                for ( int i = 1; i < listaBairro.size(); i++) {
                                    listaBairro.get(i).setChecked(false);
                                }
                            }
                            adapterBairro.notifyDataSetChanged();
                        } else {
                            listaBairro.get(0).setChecked(false);
                            adapterBairro.notifyItemChanged(position);
                        }
                    }
                });
                bairroRecyclerView.setAdapter(adapterBairro);
                adapterBairro.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        bairroRecyclerView = (RecyclerView) dialogView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bairroRecyclerView.setLayoutManager(layoutManager);





        Button btnConfirmar = (Button) dialogView.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( regiaoSelecionada.isEmpty()) {
                    Toast.makeText(context, "Selecione uma regiao!", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                for ( int i = 0; i < listaBairro.size(); i++) {
                    if ( listaBairro.get(0).isChecked() || listaBairro.size() == 1) {
                        setRecyclerView(getListaClientes(8, "", municipioSelecionado , regiaoSelecionada, "0", ""), 2);
                        return;
                    } else {
                        if ( i == 0) {
                            bairroSelecionado = "(";
                        }
                        if ( i >= 1) {
                            if ( listaBairro.get(i).isChecked())
                                bairroSelecionado += "'" + listaBairro.get(i).getId() + "',";
                        }
                    }

                }
                bairroSelecionado = bairroSelecionado.substring(0, bairroSelecionado.length()-1) + ")";
                setRecyclerView(getListaClientes(8, "", municipioSelecionado , regiaoSelecionada, bairroSelecionado, ""), 2);

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

    public void showActionDialog(final int id ,  int position, String mensagem ) {
        final Cliente cliente = adapter.getItem(position);
        Integer iconConfirm = null;
        String textConfirm = "SIM";

        // Define o ícone com base no botão clicado usando recursos existentes
        if (id == R.id.lyDownload || id == R.id.btnDownload || id == R.id.subir_cliente) {
            iconConfirm = R.mipmap.ic_enviar_pedidos;
        } else if (id == R.id.lyChamada || id == R.id.btnChamada) {
            if (MascaraUtil.isCelular(cliente.getTelefone_principal())) {
                iconConfirm = R.drawable.ic_whatsapp_;
                textConfirm = "ABRIR WHATSAPP";
            } else {
                iconConfirm = R.drawable.ic_phone_color_app_24_24;
                textConfirm = "LIGAR AGORA";
            }
        } else if (id == R.id.lyGps || id == R.id.btnGps) {
            iconConfirm = R.drawable.ic_menu_gps_2025_black_24_24;
        } else if (id == R.id.lyEmail || id == R.id.btnEmail) {
            iconConfirm = R.drawable.ic_baseline_cliente_email_2025;
        } else if (id == R.id.lyFinanceiro || id == R.id.btnFinanceiro) {
            iconConfirm = R.mipmap.ic_financeiro;
        } else if (id == R.id.lyNovoPedido || id == R.id.btnNovoPedido) {
            iconConfirm = R.drawable.ic_add_novo_registro;
            textConfirm = "INICIAR AGORA";
        } else if (id == R.id.excluir_cliente) {
            iconConfirm = R.drawable.ic_delete_white_24dp;
        }

        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                textConfirm, iconConfirm,
                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {
                    }
                    @Override
                    public void onNegative() {
                        switch (id) {
                            case R.id.lyDownload:
                            case R.id.btnDownload:
                                if (cliente.getId_cadastro_servidor() > 0) {
                                    Toast.makeText(context, "Efetua a sincronização de dados, selecionando a opção 'Clientes' para efetuar a atualização dos dados", Toast.LENGTH_SHORT).show();
                                } else {
                                    List<Cliente> lista1 = new ArrayList<>();
                                    lista1.add(cliente);
                                    addUpdateClienteAPI( lista1 );
                                }
                                break;
                            case R.id.lyChamada:
                            case R.id.btnChamada:
                                showTelefoneApp(position);
                                break;
                            case R.id.lyGps:
                            case R.id.btnGps:
                                showGpsApp(position);
                                break;
                            case R.id.lyEmail:
                            case R.id.btnEmail:
                                startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: " + cliente.getEmail_principal())));
                                break;
                            case R.id.lyFinanceiro:
                            case R.id.btnFinanceiro:
                                showActivityHistoricoFinanceiro(position);
                                break;
                            case R.id.lyNovoPedido:
                            case R.id.btnNovoPedido:
                                escolherEmpresaPedido(position);
                                break;
                            case R.id.subir_cliente:
                                addUpdateClienteAPI(adapter.getItensSelecionados());
                                break;
                            case R.id.excluir_cliente:
                                deleteCliente();
                                break;
                        }
                    }
                    @Override
                    public void onNeutral() {
                    }
                },
                getPopulatedClientSummaryView(cliente)
        );

        /*
        // ANTERIOR:
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
                        switch (id) {
                            case R.id.lyDownload:
                            case R.id.btnDownload:
                                if (adapter.getItem(position).getId_cadastro_servidor() > 0) {
                                    Toast.makeText(context, "Efetua a sincronização de dados, selecionando a opção 'Clientes' para efetuar a atualização dos dados", Toast.LENGTH_SHORT).show();
                                } else {
                                    List<Cliente> lista1 = new ArrayList<>();
                                    lista1.add( adapter.getItem(position));
                                    addUpdateClienteAPI( lista1 );
                                }
                                break;
                            case R.id.lyChamada:
                            case R.id.btnChamada:
                                showTelefoneApp(position);
                                break;
                            case R.id.lyGps:
                            case R.id.btnGps:
                                showGpsApp(position);
                                break;
                            case R.id.lyEmail:
                            case R.id.btnEmail:
                                startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: " + adapter.getItem(position).getEmail_principal())));
                                break;
                            case R.id.lyFinanceiro:
                            case R.id.btnFinanceiro:
                                showActivityHistoricoFinanceiro(position);
                                break;
                            case R.id.lyNovoPedido:
                            case R.id.btnNovoPedido:
                                escolherEmpresaPedido(position);
                                break;
                            case R.id.subir_cliente:
                                addUpdateClienteAPI(adapter.getItensSelecionados());
                                break;
                            case R.id.excluir_cliente:
                                deleteCliente();
                                break;
                        }
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
        */
    }

    private View getPopulatedClientSummaryView(Cliente cliente) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewResumo = inflater.inflate(R.layout.layout_dialog_resumo_cliente, null);

        TextView tvNome = viewResumo.findViewById(R.id.tvResumoClienteNome);
        TextView tvDoc = viewResumo.findViewById(R.id.tvResumoClienteDoc);
        TextView tvCidade = viewResumo.findViewById(R.id.tvResumoClienteCidade);

        if (tvNome != null) tvNome.setText(cliente.getNome_cadastro() != null ? cliente.getNome_cadastro().trim() : "");
        if (tvDoc != null) {
            String doc = cliente.getCpf_cnpj() != null ? cliente.getCpf_cnpj() : "";
            if (cliente.getPessoa_f_j() != null && cliente.getPessoa_f_j().equalsIgnoreCase("F")) {
                tvDoc.setText(MascaraUtil.mascaraCPF(doc));
            } else {
                tvDoc.setText(MascaraUtil.mascaraCNPJ(doc));
            }
        }
        if (tvCidade != null) {
            String cidade = cliente.getNome_municipio() != null ? cliente.getNome_municipio().trim() : "";
            String uf = cliente.getEndereco_uf() != null ? cliente.getEndereco_uf().trim() : "";
            tvCidade.setText(cidade + " - " + uf);
        }

        return viewResumo;
    }

    public void showActionDialog(String mensagem, int position, boolean pedidoIniciado, String idEmpresa ) {
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
                        ClienteHelper.setCliente(adapter.getItem(position));
                        if (!pedidoIniciado) {
                            ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                            activityPedidoMain.pegaCliente(adapter.getItem(position));
                            Pedido2 pedido2 = new Pedido2();
                            pedido2.pegaCliente(adapter.getItem(position));
                            startActivity(new Intent(ActivityClienteDrawer.this, ActivityPedidoMain.class).putExtra("id_empresa", idEmpresa));
                        } else {
                            ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                            activityPedidoMain.pegaCliente(adapter.getItem(position));
                            Pedido2 pedido2 = new Pedido2();
                            pedido2.pegaCliente(adapter.getItem(position));
                            System.gc();
                            finish();
                        }
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }

    public void showActionDialog(Cliente webCliente, int position ) {
        DialogHelper.showActionDialog(
                context,
                "Confirma a alteração do cliente " + webCliente.getNome_cadastro() + " para -->  "  + adapter.getItem(position).getNome_cadastro() + "?",
                "NÂO", null,
                "SIM", null,

                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {

                    }
                    @Override
                    public void onNegative() {
                        Cliente novoCliente = adapter.getItem(position);

                        // GRAVAÇÃO DO LOG DE ALTERAÇÃO DE CLIENTE NO PEDIDO
                        try {
                            LogSistema log = new LogSistema();
                            WebPedido pedidoAtual = PedidoHelper.getWebPedido();
                            if (pedidoAtual != null) {
                                log.setIdPedido(pedidoAtual.getId_web_pedido());
                                log.setIdPedidoApp(pedidoAtual.getId_pedido_app());
                                log.setIdWebPedidoServidor(pedidoAtual.getId_web_pedido_servidor());
                                log.setIdVendedor(pedidoAtual.getId_vendedor());
                            }
                            log.setTipoLog("AUDITORIA");
                            log.setEvento("ALTERAR_CLIENTE_PEDIDO");
                            log.setClasse("ActivityClienteDrawer");
                            log.setMetodo("showActionDialog.onNegative");
                            log.setValorAnterior("ID: " + webCliente.getId_cadastro() + " - " + webCliente.getNome_cadastro());
                            log.setValorNovo("ID: " + novoCliente.getId_cadastro() + " - " + novoCliente.getNome_cadastro());
                            new LogSistemaDAO(db).add(log);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                        activityPedidoMain.pegaCliente(novoCliente);
                        Pedido2 pedido2 = new Pedido2();
                        pedido2.pegaCliente(adapter.getItem(position));
                        System.gc();
                        finish();
                        return;
                    }
                    @Override
                    public void onNeutral() {
                    }
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
                    System.gc();
                }
        );
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                this,
                sucesso ? "Sucesso" : "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_check_circle : R.drawable.ic_baseline_error_outline_24,
                null, // Passando a cor personalizada
                () -> {
                    System.gc();
                }
        );
    }

}