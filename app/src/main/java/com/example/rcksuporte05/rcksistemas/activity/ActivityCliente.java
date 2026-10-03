package com.example.rcksuporte05.rcksistemas.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.CadastroAnexoBO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCliente extends AppCompatActivity {
    @BindView(R.id.listaRecycler)
    RecyclerView recyclerView;

    @BindView(R.id.edtTotalClientes)
    EditText edtTotalClientes;

    @BindView(R.id.tb_cliente)
    Toolbar toolbar;

    @BindView(R.id.rgFiltraCliente)
    RadioGroup rgFiltraCliente;

    @BindView(R.id.filtraTodosClientes)
    RadioButton filtraTodosClientes;

    @BindView(R.id.filtraClientesEfetivados)
    RadioButton filtraClientesEfetivados;

    @BindView(R.id.filtraClientesNaoEfetivados)
    RadioButton filtraClientesNaoEfetivados;

    @BindView(R.id.btnInserirCliente)
    Button btnInserirCliente;

    private DBHelper db = new DBHelper(this);
    private ClienteDAO clienteDAO  = new ClienteDAO(db);

    private ListaClienteAdapter adapter;
    private SearchView searchView;
    private ActionMode actionMode;
    Utilitaria util = new Utilitaria();
    private ProgressDialog progress;
    private boolean addNovo = false;


    @OnClick(R.id.btnInserirCliente)
    public void inserirCliente() {
        Cliente cliente = new Cliente();
        ClienteHelper.setCliente(cliente);

        if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE FINALIZADO = 'N'") >= 1) {
            androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(ActivityCliente.this);
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
            addNovo = true;
            startActivity(new Intent(ActivityCliente.this, ActivityCpfCnpjCliente.class).putExtra("novo", 1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        ButterKnife.bind(this);
        toolbar.setTitle("Lista de Clientes");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        if (getIntent().getIntExtra("acao", 0) == 1)
            btnInserirCliente.setVisibility(View.GONE);

        rgFiltraCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (searchView.getQuery() != null && searchView.getQuery().toString().trim().isEmpty())
                    setRecyclerView(getListaClientes());
                else
                    setRecyclerView(getListaClientes(searchView.getQuery().toString()));
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRecyclerView(getListaClientes());
        System.gc();
    }

    @Override
    protected void onResume() {
        try {
            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty())
                setRecyclerView(getListaClientes(searchView.getQuery().toString()));
            else
                setRecyclerView(getListaClientes());
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (addNovo) {
            if ( !filtraClientesNaoEfetivados.isChecked())
                filtraClientesNaoEfetivados.setChecked(true);
            else
                setRecyclerView(clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE <> 'S' " /*AND ATIVO = 'S' */+" AND F_VENDEDOR = 'N' ORDER BY NOME_CADASTRO;"));
            addNovo = false;
            //List<Cliente> listaCliente;
            //listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE <> 'S' AND ATIVO = 'S' AND F_VENDEDOR = 'N' ORDER BY ATIVO DESC, NOME_CADASTRO;");
        }

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.buscaCliente);
        searchView = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? (SearchView) item.getActionView() : (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                try {
                    if (query.trim().equals(""))
                        setRecyclerView(getListaClientes());
                    else
                        setRecyclerView(getListaClientes(query));
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Razão Social / Nome Fantasia / CNPJ-CPF / Telefone");
        return true;
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

    public List<Cliente> getListaClientes(final String query) {
        try {
            List<Cliente> listaCliente;
            switch (rgFiltraCliente.getCheckedRadioButtonId()) {
                case R.id.filtraTodosClientes:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' " /* AND ATIVO = 'S'*/ + "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY NOME_CADASTRO");
                    break;
                case R.id.filtraClientesEfetivados:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0 AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '?%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesNaoEfetivados:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE <> 'S' " /*AND ATIVO = 'S'*/ + "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '?%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY NOME_CADASTRO;");
                    break;
                default:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' " /* AND ATIVO = 'S'*/ +  "AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY NOME_CADASTRO");
                    break;
            }
            recyclerView.setVisibility(View.VISIBLE);
            edtTotalClientes.setText("Clientes listados: " + listaCliente.size() + "   ");
            return listaCliente;
        } catch (CursorIndexOutOfBoundsException e) {
            recyclerView.setVisibility(View.INVISIBLE);
            edtTotalClientes.setText("Não há clientes a serem exibidos!   ");
        }
        return null;
    }

    public List<Cliente> getListaClientes() {
        try {
            List<Cliente> listaCliente;
            switch (rgFiltraCliente.getCheckedRadioButtonId()) {
                case R.id.filtraTodosClientes:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' " /*AND ATIVO = 'S'*/ + "ORDER BY NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesEfetivados:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' " /*AND ATIVO = 'S'*/ + "AND ID_CADASTRO_SERVIDOR > 0 ORDER BY NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesNaoEfetivados:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE <> 'S' " /*AND ATIVO = 'S'*/ + "AND F_VENDEDOR = 'N' ORDER BY NOME_CADASTRO;");
                    break;
                default:
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' " /*AND ATIVO = 'S'*/ +"ORDER BY NOME_CADASTRO;");
                    break;
            }
            if (listaCliente.size() > 0) {
                if (listaCliente.get(0).getId_cadastro() <= 0)
                    return null;
            } else
                return null;

            recyclerView.setVisibility(View.VISIBLE);
            edtTotalClientes.setText("Clientes listados: " + listaCliente.size() + "   ");
            return listaCliente;
        } catch (CursorIndexOutOfBoundsException e) {
            recyclerView.setVisibility(View.INVISIBLE);
            edtTotalClientes.setText("Não há clientes a serem exibidos!   ");
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void setRecyclerView(List<Cliente> clientes) {
        if (getIntent().getIntExtra("acao", 0) == 1) {

            adapter = new ListaClienteAdapter( ActivityCliente.this, clientes, new ListaClienteAdapter.Listener() {
                @Override
                public void onClickListener(int position) {
                    if (adapter.getItem(position).getIdCategoria() <= 0)
                        util.showMsgAlerta("Este cliente não tem categoria definida", ActivityCliente.this);
                    else {
                        try {
                            ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                            activityPedidoMain.pegaCliente(adapter.getItem(position));
                            Pedido2 pedido2 = new Pedido2();
                            pedido2.pegaCliente(adapter.getItem(position));
                            System.gc();
                            finish();
                        } catch (CursorIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            Toast.makeText(ActivityCliente.this, "Financeiro não encontrado, por favor faça a sincronia e tente novamente!", Toast.LENGTH_LONG).show();
                        }
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
                            showMsgSimNao(view.getId(),position, "Deseja efetuar a sincronização/atualização dos dados do cliente?");
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickChamada(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (!adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty()) {
                                    if (adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11) {
                                        if (MascaraUtil.isCelular(adapter.getItem(position).getTelefone_principal()))
                                            showMsgSimNao(view.getId(), position, "Deseja enviar um WhatsApp para " + adapter.getItem(position).getTelefone_principal() + " ?");
                                        else
                                            showMsgSimNao(view.getId(), position, "Deseja ligar para o número " + adapter.getItem(position).getTelefone_principal() + " ?");
                                    } else
                                        util.showMsgAlerta("Este numero de telefone não é válido!", ActivityCliente.this);
                                } else
                                    util.showMsgAlerta("Nenhum numero de telefone informado!", ActivityCliente.this);
                            } catch (Exception e) {
                                util.showMsgAlerta("Nenhum numero de telefone informado/ou número inválido!", ActivityCliente.this);
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
                            if ( adapter.getItem(position).getEndereco().equals("Nenhum endereço informado!"))
                                util.showMsgAlerta("Nenhum endereço informado!", ActivityCliente.this);
                            else
                                showMsgSimNao( view.getId(), position, "Deseja navegar para a localização para " + adapter.getItem(position).getNome_cadastro() + " no GPS?");
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
                                    util.showMsgAlerta("Nenhum e-mail informado!", ActivityCliente.this);
                                else
                                    showMsgSimNao( view.getId(), position, "Deseja enviar um email a " + adapter.getItem(position).getNome_cadastro() + "?");
                            } catch ( NullPointerException e) {
                                util.showMsgAlerta("Nenhum e-mail informado!", ActivityCliente.this);
                            }
                        }
                    };
                }
                @Override
                public View.OnClickListener onClickFinanceiro(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showMsgSimNao(view.getId(), position, "Deseja efetuar consulta ao historico financeiro?");
                        }
                    };
                }
                @Override
                public View.OnClickListener onClickNovoPedido(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (adapter.getItem(position).getIdCategoria() <= 0)
                                util.showMsgAlerta("Este cliente não tem uma categoria definida", ActivityCliente.this);
                            else
                                showMsgSimNao(view.getId(), position, "Deseja iniciar um novo pedido para " + adapter.getItem(position).getNome_cadastro() + "?");
                        }
                    };
                }
            });

        } else {
            adapter = new ListaClienteAdapter(ActivityCliente.this, clientes, new ListaClienteAdapter.Listener() {
                @Override
                public void onClickListener(int position) {
                    if (adapter.getSelectedItensCount() > 0)
                        enableActionMode(position);
                    else {
                        Intent intent;
                        if (adapter.getItem(position).getF_cliente().equals("S")) {
                            intent = new Intent(ActivityCliente.this, ActivityContato.class);
                            ClienteHelper.setCliente(adapter.getItem(position));
                            if (adapter.getItem(position).getId_cadastro_servidor() > 0)
                                intent.putExtra("vizualizacao", 1);
                        } else {
                            intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
                            intent.putExtra("novo", 1);
                            ClienteHelper.setCliente(adapter.getItem(position));
                        }
                        System.gc();
                        startActivity(intent);
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
                            showMsgSimNao(view.getId(),position, "Deseja efetuar a sincronização/atualização dos dados do cliente?");
                        }
                    };
                }

                @Override
                public View.OnClickListener onClickChamada(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (!adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty()) {
                                    if (adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && adapter.getItem(position).getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11) {
                                        if (MascaraUtil.isCelular(adapter.getItem(position).getTelefone_principal()))
                                            showMsgSimNao(view.getId(), position, "Deseja enviar um WhatsApp para " + adapter.getItem(position).getTelefone_principal() + " ?");
                                        else
                                            showMsgSimNao(view.getId(), position, "Deseja ligar para o número " + adapter.getItem(position).getTelefone_principal() + " ?");
                                    } else
                                        util.showMsgAlerta("Este numero de telefone não é válido!", ActivityCliente.this);
                                } else
                                    util.showMsgAlerta("Nenhum numero de telefone informado!", ActivityCliente.this);
                            } catch (Exception e) {
                                util.showMsgAlerta("Nenhum numero de telefone informado/ou número inválido!", ActivityCliente.this);
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
                            if ( adapter.getItem(position).getEndereco().equals("Nenhum endereço informado!"))
                                util.showMsgAlerta("Nenhum endereço informado!", ActivityCliente.this);
                            else
                                showMsgSimNao( view.getId(), position, "Deseja navegar para a localização para " + adapter.getItem(position).getNome_cadastro() + " no GPS?");

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
                                    util.showMsgAlerta("Nenhum e-mail informado!", ActivityCliente.this);
                                else
                                    showMsgSimNao( view.getId(), position, "Deseja enviar um email a " + adapter.getItem(position).getNome_cadastro() + "?");
                            } catch ( NullPointerException e) {
                                util.showMsgAlerta("Nenhum e-mail informado!",ActivityCliente.this);
                            }
                        }
                    };
                }
                @Override
                public View.OnClickListener onClickFinanceiro(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           showMsgSimNao(view.getId(), position, "Deseja efetuar consulta ao historico financeiro?");
                        }
                    };
                }
                @Override
                public View.OnClickListener onClickNovoPedido(int position) {
                    return new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (adapter.getItem(position).getIdCategoria() <= 0)
                                util.showMsgAlerta("Este cliente não tem uma categoria definida", ActivityCliente.this);
                            else
                                showMsgSimNao(view.getId(), position, "Deseja iniciar um novo pedido para " + adapter.getItem(position).getNome_cadastro() + "?");
                        }
                    };
                }
            });
        }
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
                                    //AlertDialog.Builder alertEnviar = new AlertDialog.Builder(ActivityCliente.this);
                                    //alertEnviar.setTitle("Atenção");
                                    if (adapter.getSelectedItensCount() > 1)
                                        showMsgSimNao( item.getItemId(), 0, "Deseja enviar os " + adapter.getSelectedItensCount() + " cadastros de clientes selecionandos ao servidor?");
                                    else
                                        showMsgSimNao( item.getItemId(), 0, "Deseja enviar o cliente " + adapter.getItensSelecionados().get(0).getNome_cadastro() + " ao servidor?");
                                    break;
                                case R.id.excluir_cliente:
                                    if (adapter.getSelectedItensCount() > 1)
                                        showMsgSimNao( item.getItemId(), 0, "Deseja excluir esses " + adapter.getSelectedItensCount() + " cadastros?");
                                    else
                                        showMsgSimNao( item.getItemId(), 0, "Deseja excluir o cadastro de " + adapter.getItensSelecionados().get(0).getNome_cadastro() + " ?");
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
                Toast.makeText(ActivityCliente.this, "O cadastro " + adapter.getItem(position).getNome_cadastro() + " não faz parte do grupo de seleção ativado", Toast.LENGTH_SHORT).show();
            }
        } else if (adapter.getItem(position).getF_cliente().equals("S")) {
            Toast.makeText(ActivityCliente.this, "Esse cliente já está efetivado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityCliente.this, "Cliente foi enviado e esta aguardando análise para efetivação", Toast.LENGTH_SHORT).show();
        }
    }


    public void toggleSelection(int position) {
        adapter.toggleSelection(position);
        if (adapter.getSelectedItensCount() == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(adapter.getSelectedItensCount()));
            actionMode.invalidate();
        }
    }


    private void novoCadastro(){
        db.deleteDados("TBL_CADASTRO","FINALIZADO = ?", new String[]{"N"});
        ClienteHelper.getCliente().setNome_municipio("");
        ClienteHelper.setPosicaoMunicipio(0);
        startActivity(new Intent(ActivityCliente.this, ActivityCpfCnpjCliente.class).putExtra("novo", 1));
    }
    private void continuaCadastro() {
        ClienteHelper.setCliente(clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO = 'N'").get(0));
        if (ClienteHelper.getCliente().getId_cadastro() > 0) {
            if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                final ProgressDialog progress = new ProgressDialog(ActivityCliente.this);
                progress.setTitle("Aguarde");
                progress.setMessage("Carregando anexos do cliente");
                progress.setCancelable(false);
                progress.show();
                final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                Thread a = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(ActivityCliente.this, ClienteHelper.getCliente().getId_cadastro());
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
        startActivity(new Intent(ActivityCliente.this, CadastroClienteMain.class).putExtra("novo", 1));

    }

    public void sincronizarClienteAPI(List<Cliente> listaClientes) {
        showHideProgressDialog(true);
        Rotas apiRetrofit = Api.buildRetrofit(false);
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
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
        Call<List<Cliente>> call = apiRetrofit.salvarClientes(cabecalho, listaClientes);
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                switch (response.code()) {
                    case 200:
                        List<Cliente> clientesRetorno = response.body();
                        if (clientesRetorno != null && clientesRetorno.size() > 0)
                            updateCliente(clientesRetorno);
                        else
                            limpaListaSelecionados();
                        showHideProgressDialog(false);
                        break;
                    case 500:
                        limpaListaSelecionados();
                        showHideProgressDialog(false);
                        util.showMsgAlerta("Erro ao sincronizar o(s) cadastro(s)!", ActivityCliente.this);
                        break;
                    default:
                        showHideProgressDialog(false);
                        util.showMsgAlerta("Erro ao sincronizar o(s) cadastro(s)!\n(" + response.code() + ")", ActivityCliente.this);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                limpaListaSelecionados();
                showHideProgressDialog(false);
                util.showMsgAlerta("Falha ao sincronizar o(s) cadastro(s)!", ActivityCliente.this);

            }
        });
    }

    public void showMsgSimNao( final int id , int position, String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityCliente.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCliente.this);
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
                switch (id){
                    case R.id.lyChamada: case R.id.btnChamada:
                        showTelefoneApp(position);
                        break;
                    case R.id.lyGps: case R.id.btnGps:
                        showGpsApp(position);
                        break;
                    case R.id.lyEmail: case R.id.btnEmail:
                        startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: " + adapter.getItem(position).getEmail_principal())));
                        break;
                    case R.id.lyFinanceiro: case R.id.btnFinanceiro:
                        showActivityHistoricoFinanceiro(position);
                        break;
                    case R.id.lyNovoPedido: case R.id.btnNovoPedido:
                        showActivityPedido(position);//Inicia um novo pedido
                        break;
                    case R.id.subir_cliente:
                        sincronizarClienteAPI(adapter.getItensSelecionados());
                        break;
                    case R.id.excluir_cliente:
                        deleteCliente();
                        break;
                }
            }
        });
        alertDialog.show();
    }

    private void showTelefoneApp(int position){
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
                // LÓGICA TELEFONE FIXO
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String telLimpo = telefone.replaceAll("[^0-9]", "");
                if (telLimpo.length() == 10 || telLimpo.length() == 11)
                    intent.setData(Uri.parse("tel:" + "0" + telLimpo));
                else
                    intent.setData(Uri.parse("tel:" + telLimpo));
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGpsApp(int position){
        String endereco = adapter.getItem(position).getEndereco().replace(",", "")
                + ", " + adapter.getItem(position).getEndereco_numero() + " - "
                + adapter.getItem(position).getEndereco_cep();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + endereco );
        //Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        //intent.setPackage("com.google.android.apps.maps");
        startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri).setPackage("com.google.android.apps.maps"));
    }

    private void showActivityHistoricoFinanceiro(int position){
        ClienteHelper.setCliente( adapter.getItem(position));
        HistoricoFinanceiroHelper.setCliente( ClienteHelper.getCliente() );
        System.gc();
        startActivity(new Intent(ActivityCliente.this, ActivityHistoricoFinanceiro.class));
    }

    private void showActivityPedido(int position){
        ClienteHelper.setCliente( adapter.getItem(position));
        ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
        Pedido2 pedido2 = new Pedido2();
        activityPedidoMain.pegaCliente(ClienteHelper.getCliente());
        pedido2.pegaCliente(ClienteHelper.getCliente());
        startActivity(new Intent(ActivityCliente.this, ActivityPedidoMain.class));
    }

    private void deleteCliente() {
        ClienteDAO clienteDAO = new ClienteDAO(db);
        for (Cliente cliente : adapter.getItensSelecionados())
            if (clienteDAO.delete(cliente, "0") <= 0) {
                util.showMsgAlerta("Falha ao excluir o cadastro do cliente\n" + cliente.getNome_cadastro() + "\n nExclusão cancelada! ", ActivityCliente.this);
                break;
                //0 = local 1 = servidor
            }
        actionMode.finish();
        adapter.clearSelection();
        setRecyclerView(clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE <> 'S' " /*AND ATIVO = 'S'*/ + "AND F_VENDEDOR = 'N' ORDER BY NOME_CADASTRO;"));

        //filtraClientesNaoEfetivados.setChecked(true);
        actionMode = null;
        onResume();
    }

    private void updateCliente( List<Cliente> clientesRetorno){
        ClienteDAO clienteDAO = new ClienteDAO(db);
        CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
        for (Cliente cliente : clientesRetorno) {
            cliente.setAlterado("N");
            clienteDAO.update(cliente);
            try {
                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(cliente.getId_cadastro())});
            } catch ( Exception e) {
                e.printStackTrace();
            }
            if (cliente.getListaCadastroAnexo().size() > 0) {
                for (CadastroAnexo cadastroAnexo : cliente.getListaCadastroAnexo()) {
                    if (cadastroAnexo.getExcluido().equals("N"))
                        cadastroAnexoDAO.addUpdate(cadastroAnexo);
                    else if (cadastroAnexo.getExcluido().equals("S"))
                        try {
                            db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_ANEXO = ?", new String[]{String.valueOf(cadastroAnexo.getIdAnexo())});
                        } catch ( Exception e) {
                            e.printStackTrace();
                        }
                }
            } else {
                try {
                    db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(cliente.getId_cadastro())});
                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }
        limpaListaSelecionados();
        Toast.makeText(ActivityCliente.this, "Cadastro(s) sincronizado(s) com sucesso!", Toast.LENGTH_SHORT).show();
        onResume();
    }

    private void limpaListaSelecionados(){
        adapter.clearSelection();
        actionMode.finish();
        actionMode = null;
    }

    private void showHideProgressDialog(boolean visivel){
        if ( !visivel) {
            progress.dismiss();
            return;
        }
        progress = new ProgressDialog(ActivityCliente.this);
        progress.setMessage("Aguarde...!");
        progress.setCancelable(false);
        progress.show();
    }
}
