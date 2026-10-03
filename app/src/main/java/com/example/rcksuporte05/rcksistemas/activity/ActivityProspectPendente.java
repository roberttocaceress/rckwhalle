package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroMotivoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProspectDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
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


public class ActivityProspectPendente extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleProspect)
    RecyclerView recycleProspect;

    @BindView(R.id.edtTotalProspect)
    EditText edtTotalProspect;

    @BindView(R.id.rgFiltaProspect)
    RadioGroup rgFiltaProspect;

    private ListaProspectAdapter listaProspectAdapter;
    private ActionMode actionMode;
    private List<Prospect> listaProspect;
    private DBHelper db;
    private ProgressDialog progress;
    private SearchView searchView;
    private MenuItem viraCliente;
    private CadastroMotivoDAO cadastroMotivoDAO;
    private ProspectDAO prospectDAO;
    @OnClick(R.id.btnAddProspect)
    public void novoProspect() {
        /*Prospect prospect = new Prospect();
        prospect.setProspectSalvo("N");
        ProspectHelper.setProspect(prospect);
        Intent intent = new Intent(ActivityListaProspect.this, ActivityCadastroProspect.class);
        intent.putExtra("novo", 1);
        startActivity(intent);*/

        //Intent intent = new Intent(ActivityListaProspect.this, ActivityValidaProspect.class);
        startActivity(new Intent(ActivityProspectPendente.this, ActivityProspectValida.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prospect);
        ButterKnife.bind(this);
        db = new DBHelper(this);
        prospectDAO = new ProspectDAO(db);

        rgFiltaProspect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    if (actionMode != null)
                        actionMode.finish();

                    switch (checkedId) {
                        case R.id.filtraProspectAmbos:
                            listaProspect = prospectDAO.getLista(Prospect.PROSPECT_PENDENTE_SALVO);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty())
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                        case R.id.filtraProspectPendentes:
                            //listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE);
                            listaProspect = prospectDAO.getLista(Prospect.PROSPECT_PENDENTE);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty())
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                        case R.id.filtraProspectSalvos:
                            //listaProspect = db.listaProspect(Prospect.PROSPECT_SALVO);
                            listaProspect = prospectDAO.getLista(Prospect.PROSPECT_SALVO);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty())
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                        default:
                            //listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
                            listaProspect = prospectDAO.getLista(Prospect.PROSPECT_PENDENTE_SALVO);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty())
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                    }
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    recycleProspect.setVisibility(View.INVISIBLE);
                    edtTotalProspect.setText("0: Prospects Listados");
                }
            }
        });

        recycleProspect.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recycleProspect.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        final MenuItem item = menu.findItem(R.id.buscaCliente);
        searchView = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? searchView = (SearchView) item.getActionView() : (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                try {
                    if (query.trim().equals("")) {
                        if (listaProspect.size() > 0)
                            preencheLista(listaProspect);
                    } else
                        preencheLista(buscaProspect(listaProspect, query));

                } catch (NullPointerException e) {
                    Toast.makeText(ActivityProspectPendente.this, "Não existem Prospects para consulta", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        searchView.setQueryHint("Nome cadastro/nome fantasia/CPF-CNPJ/codigo prospect");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void preencheLista(final List<Prospect> lista) {
        listaProspectAdapter = new ListaProspectAdapter(lista, new ListaProspectAdapter.ProspectAdapterListener() {
            @Override
            public void onProspectRowClicked(int position) {
                if (listaProspectAdapter.getItensSelecionadosCount() > 0)
                    enableActionMode(position);
                else {
                    ProspectHelper.setProspect(lista.get(position));
                    Intent intent = new Intent(ActivityProspectPendente.this, ActivityProspectCadastro.class);
                    if (!lista.get(position).getProspectSalvo().equals("S"))
                        intent.putExtra("novo", 1);
                    startActivity(intent);
                }
            }

            @Override
            public void onProspectLongClicked(int position) {
                enableActionMode(position);
            }
        });
        recycleProspect.setVisibility(View.VISIBLE);
        recycleProspect.setAdapter(listaProspectAdapter);
    }

    public List<Prospect> buscaProspect(List<Prospect> listaProspect, String query) {
        final String upperCaseQuery = query.toUpperCase();
        final List<Prospect> lista = new ArrayList<>();
        for (Prospect prospect : listaProspect) {
            boolean entrou = false;

            try {
                final String idProspect = prospect.getId_prospect().toUpperCase();
                if (idProspect.equals(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                final String nomeCadastro = prospect.getNome_cadastro().toUpperCase();
                if (nomeCadastro.contains(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                final String nomeFantasia = prospect.getNome_fantasia().toUpperCase();
                if (nomeFantasia.contains(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                final String cpfCnpj = prospect.getCpf_cnpj().toUpperCase();
                if (cpfCnpj.contains(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        edtTotalProspect.setText(lista.size() + ": Prospects Encontrados");
        return lista;
    }

    @Override
    public void onResume() {
        atualizaTela();
        super.onResume();
    }

    private void enableActionMode(final int position) {
        if (listaProspectAdapter.getItensSelecionadosCount() == 0 || listaProspectAdapter.getItensSelecionados().get(listaProspectAdapter.getItensSelecionadosCount() - 1).getProspectSalvo().equals(listaProspectAdapter.getItem(position).getProspectSalvo())) {
            if (actionMode == null) {
                actionMode = startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        if (listaProspectAdapter.getItem(position).getProspectSalvo().equals("S"))
                            mode.getMenuInflater().inflate(R.menu.subir_vira_cliente, menu);
                        else
                            mode.getMenuInflater().inflate(R.menu.excluir_vira_cliente, menu);
                        viraCliente = menu.findItem(R.id.viraCliente);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                if (listaProspectAdapter.getItensSelecionadosCount() > 1)
                                    showMsgSimNao(mode, item.getItemId(), "Deseja realmente excluir" +
                                            " esses " + listaProspectAdapter.getItensSelecionadosCount() + "itens?");
                                else
                                    showMsgSimNao(mode, item.getItemId(), "Deseja realmente excluir" +
                                            " o prospect " + listaProspectAdapter.getItensSelecionados().get(0).getId_prospect() + " ?");
                                break;
                            case R.id.uploadFoto:
                                if (listaProspectAdapter.getItensSelecionadosCount() > 1)
                                    showMsgSimNao(mode, item.getItemId(), "Tem certeza que deseja enviar esses " + listaProspectAdapter.getItensSelecionadosCount()
                                            + " prospects para o servidor?");
                                else
                                    showMsgSimNao(mode, item.getItemId(), "Tem certeza que deseja enviar o prospect " + listaProspectAdapter.getItensSelecionados().get(0).getId_prospect()
                                            + " para o servidor?");
                                break;
                            case R.id.viraCliente:
                                if (listaProspectAdapter.getItensSelecionadosCount() == 1)
                                    showMsgSimNao( mode, item.getItemId() , "Deseja transformar " + listaProspectAdapter.getItensSelecionados().get(0).getNome_cadastro() + " em cliente?");

                        }
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        actionMode.finish();
                        listaProspectAdapter.clearSelections();
                        actionMode = null;
                    }
                });
            }
            toggleSelection(position);
        } else
            Toast.makeText(this, "O item " + listaProspectAdapter.getItem(position).getId_prospect() + " da lista não faz parte do grupo de seleção ativado", Toast.LENGTH_SHORT).show();
    }

    public void toggleSelection(int position) {
        listaProspectAdapter.toggleSelection(position);
        if (listaProspectAdapter.getItensSelecionadosCount() == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            if (viraCliente != null) {
                if (listaProspectAdapter.getItensSelecionadosCount() > 1)
                    viraCliente.setVisible(false);
                else
                    viraCliente.setVisible(true);
            }
            actionMode.setTitle(String.valueOf(listaProspectAdapter.getItensSelecionadosCount()));
            actionMode.invalidate();
        }
    }

    public void enviarProspects(List<Prospect> prospectsEnvio) {
        Rotas apiRetrofit = Api.buildRetrofit(false);

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        for (Prospect prospect : prospectsEnvio)
            try {
                if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + prospect.getId_prospect() + " AND ID_ENTIDADE = 10;") > 0) {
                    CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                    List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoDAO.getLista(Integer.parseInt(prospect.getId_prospect()), 10);

                    for (CadastroAnexo cadastroAnexo : listaCadastroAnexo)
                        if (cadastroAnexo.getPrincipal().equals("S"))
                            prospect.setFotoPrincipalBase64(cadastroAnexo);
                        else
                            prospect.setFotoSecundariaBase64(cadastroAnexo);

                }
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        Call<List<Prospect>> call = apiRetrofit.salvarProspect(cabecalho, prospectsEnvio);
        call.enqueue(new Callback<List<Prospect>>() {
            @Override
            public void onResponse(Call<List<Prospect>> call, Response<List<Prospect>> response) {
                switch (response.code()) {
                    case 200:
                        List<Prospect> prospectsRetorno = response.body();
                        if (prospectsRetorno != null && prospectsRetorno.size() > 0) {
                            for (Prospect prospect : prospectsRetorno)
                                prospectDAO.addUpdate(prospect);
                                //db.atualizarTBL_PROSPECT(prospect);
                            listaProspectAdapter.clearSelections();
                            actionMode.finish();
                            actionMode = null;
                            progress.dismiss();
                            Toast.makeText(ActivityProspectPendente.this, "Enviado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    case 500:
                        progress.dismiss();
                        Toast.makeText(ActivityProspectPendente.this, "Erro, não foi possivel enviar o prospect", Toast.LENGTH_SHORT).show();
                        listaProspectAdapter.clearSelections();
                        actionMode.finish();
                        actionMode = null;
                        break;
                    default:
                        progress.dismiss();
                        Toast.makeText(ActivityProspectPendente.this, "Erro não catalogado: " + response.code(), Toast.LENGTH_SHORT).show();
                        listaProspectAdapter.clearSelections();
                        actionMode.finish();
                        actionMode = null;
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Prospect>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ActivityProspectPendente.this, "Falhou, tente novamente, ou entre em contato com o suporte", Toast.LENGTH_SHORT).show();
                listaProspectAdapter.clearSelections();
                actionMode.finish();
                actionMode = null;
            }
        });

    }


    public void atualizaTela() {
        try {
            DBHelper db = new DBHelper(this);
            switch (rgFiltaProspect.getCheckedRadioButtonId()) {
                case R.id.filtraProspectAmbos:
                    //listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
                    listaProspect = prospectDAO.getLista(Prospect.PROSPECT_PENDENTE_SALVO);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
                case R.id.filtraProspectPendentes:
                    //listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE);
                    listaProspect = prospectDAO.getLista(Prospect.PROSPECT_PENDENTE);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
                case R.id.filtraProspectSalvos:
                    //listaProspect = db.listaProspect(Prospect.PROSPECT_SALVO);
                    listaProspect = prospectDAO.getLista(Prospect.PROSPECT_SALVO);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
                default:
                    //listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
                    listaProspect = prospectDAO.getLista(Prospect.PROSPECT_PENDENTE_SALVO);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
            }

            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty())
                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
            else
                preencheLista(listaProspect);

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            recycleProspect.setVisibility(View.INVISIBLE);
            edtTotalProspect.setText("0: Prospects Listados");
        }
    }

    public void showMsgSimNao( ActionMode mode,  final int id , String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityProspectPendente.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProspectPendente.this);
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
                if ( id == R.id.action_delete) {
                    DBHelper db = new DBHelper(ActivityProspectPendente.this);
                    for (Prospect prospect : listaProspectAdapter.getItensSelecionados()) {
                        listaProspectAdapter.remove(prospect);
                        prospectDAO.delete(prospect, "0");
                        //db.excluiProspect(prospect);
                    }
                    listaProspectAdapter.notifyDataSetChanged();
                    actionMode.finish();
                    listaProspectAdapter.clearSelections();
                    actionMode = null;
                } else if ( id == R.id.uploadFoto) {
                    progress = new ProgressDialog(ActivityProspectPendente.this);
                    progress.setMessage("Enviando Prospects");
                    progress.setTitle("Aguarde");
                    progress.show();
                    enviarProspects(listaProspectAdapter.getItensSelecionados());
                } else if( id == R.id.viraCliente) {
                    try {
                        if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + listaProspectAdapter.getItensSelecionados().get(0).getId_prospect() + " AND ID_ENTIDADE = 10;") > 0) {
                            CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                            List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoDAO.getLista(Integer.parseInt(listaProspectAdapter.getItensSelecionados().get(0).getId_prospect()), 10);

                            for (CadastroAnexo cadastroAnexo : listaCadastroAnexo)
                                if (cadastroAnexo.getPrincipal().equals("S"))
                                    listaProspectAdapter.getItensSelecionados().get(0).setFotoPrincipalBase64(cadastroAnexo);
                                else
                                    listaProspectAdapter.getItensSelecionados().get(0).setFotoSecundariaBase64(cadastroAnexo);

                        }
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    Cliente cliente = new Cliente(listaProspectAdapter.getItensSelecionados().get(0));
                    Intent intent = new Intent(ActivityProspectPendente.this, CadastroClienteMain.class);
                    intent.putExtra("prospect", Integer.parseInt(listaProspectAdapter.getItensSelecionados().get(0).getId_prospect()));
                    intent.putExtra("novo", 1);
                    ClienteHelper.setCliente(cliente);
                    startActivity(intent);
                    actionMode.finish();
                    listaProspectAdapter.clearSelections();
                    actionMode = null;
                }
            }

        });
        alertDialog.show();
    }
}
