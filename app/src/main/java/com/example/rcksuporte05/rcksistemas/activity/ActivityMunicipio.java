package com.example.rcksuporte05.rcksistemas.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


import com.example.rcksuporte05.rcksistemas.DAO.BairroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MunicipioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MunicipioAdapter;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityMunicipio extends AppCompatActivity implements MunicipioAdapter.municipioListener {

    @BindView(R.id.listaMunicipio)
    RecyclerView recyclerView;
    @BindView(R.id.toolbarMunicipio)
    Toolbar toolbarMunicipio;
    private MunicipioAdapter adapter;
    private SearchView searchView;
    private String[] municipio;
    private String[] bairro;
    private String municipioSelecionado;
    List<Municipio> lista;
    MunicipioDAO municipioDAO;
    String uf = "";
    private int[] listaUf = {R.array.AC_LIST,
            R.array.AL_LIST,
            R.array.AM_LIST,
            R.array.AP_LIST,
            R.array.BA_LIST,
            R.array.CE_LIST,
            R.array.DF_LIST,
            R.array.ES_LIST,
            R.array.EX_LIST,
            R.array.GO_LIST,
            R.array.MA_LIST,
            R.array.MG_LIST,
            R.array.MS_LIST,
            R.array.MT_LIST,
            R.array.PA_LIST,
            R.array.PB_LIST,
            R.array.PE_LIST,
            R.array.PI_LIST,
            R.array.PR_LIST,
            R.array.RJ_LIST,
            R.array.RN_LIST,
            R.array.RO_LIST,
            R.array.RR_LIST,
            R.array.RS_LIST,
            R.array.SC_LIST,
            R.array.SE_LIST,
            R.array.SP_LIST,
            R.array.TO_LIST};

    private DBHelper db = new DBHelper(this);
    Municipio todos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_municipio);
        ButterKnife.bind(this);
        toolbarMunicipio.setTitle("Pesquisar");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        if (getIntent().getIntExtra("cliente", 0) >= 1) {
            municipio = getResources().getStringArray(listaUf[ClienteHelper.getPosicaoUf()]);
            if (ClienteHelper.getPosicaoMunicipio() > 0)
                municipioSelecionado = municipio[ClienteHelper.getPosicaoMunicipio() - 1];
            setRecyclerView(municipio);
        } else if (getIntent().getIntExtra("prospect", 0) >= 1) {
            municipio = getResources().getStringArray(listaUf[ProspectHelper.getPosicaoUf()]);
            if (ProspectHelper.getPosicaoMunicipio() > 0)
                municipioSelecionado = municipio[ProspectHelper.getPosicaoMunicipio() - 1];
            setRecyclerView(municipio);
        } else {
            try {
                uf = getIntent().getStringExtra("uf");
            } catch ( NullPointerException e) {
                EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
                EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
                uf = empresaParametro.getEndereco_uf();
            }
            municipioDAO = new MunicipioDAO(db);
            //Mudei daqui
            todos = new Municipio();
            todos.setId("0");
            todos.setNome_municipio("TODOS");
            todos.setUf(uf.toUpperCase());
            lista = new ArrayList<>();
            lista.add(todos);
            //Ate aqui
            lista.addAll(municipioDAO.getLista("SELECT * FROM TBL_CADASTRO_MUNICIPIO WHERE UF = '" + uf.toUpperCase() + "' ORDER BY NOME_MUNICIPIO"));
            try {
                municipioSelecionado = getIntent().getStringExtra("pos_selecionado");
            } catch ( NullPointerException e) {
            }
            setRecyclerView(lista);

        }
        setSupportActionBar(toolbarMunicipio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(int position) {
        adapter.toggleSelection(position);
        adapter.notifyDataSetChanged();
        if (getIntent().getIntExtra("cliente", 0) >= 1) {
            if (ClienteHelper.getPosicaoMunicipio() > 0 && position != ClienteHelper.getPosicaoUf())
                ClienteHelper.setPosicaoMunicipio(1);
            for (int i = 0; municipio.length > i; i++)
                if (municipio[i].equals(adapter.getItem(position))) {
                    ClienteHelper.setPosicaoMunicipio(i+ 1);
                    break;
                }

            finish();
        } else if (getIntent().getIntExtra("prospect", 0) >= 1) {
            if (ProspectHelper.getPosicaoMunicipio() > 0 && position != ProspectHelper.getPosicaoUf())
                ProspectHelper.setPosicaoMunicipio(0);
            for (int i = 0; municipio.length > i; i++)
                if (municipio[i].equals(adapter.getItem(position))) {
                    ProspectHelper.setPosicaoMunicipio(i + 1);
                    break;
                }
            finish();
        } else if (getIntent().getIntExtra("cidade", 0) >= 0) {
            /*
            Intent intent = getIntent();
            intent.putExtra("position", position);
            intent.putExtra("id_cidade", lista.get(position).getId());
            setResult(RESULT_OK, intent);

             */
            Intent intent = new Intent();
            intent.putExtra("result", lista.get(position).getId());
            setResult(RESULT_OK, intent);
            finish();
            //finish();


        }

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
                    if (query.trim().equals("")) {
                        if (lista == null)
                            setRecyclerView(municipio);
                        else {
                            if (getIntent().getIntExtra("cliente", 0) >= 1 || getIntent().getIntExtra("prospect", 0) >= 1) {
                                lista = municipioDAO.getLista("SELECT * FROM TBL_CADASTRO_MUNICIPIO WHERE UF = '" + uf.toUpperCase() + "' ORDER BY NOME_MUNICIPIO");
                            } else {
                                lista = new ArrayList<>();
                                lista.add(todos);
                                lista.addAll(municipioDAO.getLista("SELECT * FROM TBL_CADASTRO_MUNICIPIO WHERE UF = '" + uf.toUpperCase() + "' ORDER BY NOME_MUNICIPIO"));
                                setRecyclerView(lista);
                            }
                        }
                    } else {
                        if (lista == null)
                            setRecyclerView(buscaMunicipio(query));
                        else
                            setRecyclerView(busca(query));
                    }

                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Municipio");
        return true;
    }

    private void setRecyclerView(String[] municipio) {
        adapter = new MunicipioAdapter(municipio, null, this);
        recyclerView.setAdapter(adapter);
        adapter.selecionado(municipioSelecionado);
        adapter.notifyDataSetChanged();
    }


    private void setRecyclerView(List<Municipio> lista) {
        adapter = new MunicipioAdapter(null, lista, this);
        recyclerView.setAdapter(adapter);
        adapter.selecionado(municipioSelecionado);
        adapter.notifyDataSetChanged();
    }

    private String[] buscaMunicipio(String query) {
        query = query.toUpperCase();
        List<String> busca = new ArrayList<>();
        for (int i = 0; municipio.length > i; i++)
            if (municipio[i].toUpperCase().contains(query))
                busca.add(municipio[i]);
        final String[] resultado = new String[busca.size()];
        for (int i = 0; busca.size() > i; i++)
            resultado[i] = busca.get(i);
        return resultado;
    }

    public List<Municipio> busca(String query) {
        query = query.toUpperCase();
        lista = municipioDAO.getLista("SELECT * FROM TBL_CADASTRO_MUNICIPIO WHERE UF = '" + uf.toUpperCase() + "' AND NOME_MUNICIPIO LIKE '%" + query + "%' ORDER BY NOME_MUNICIPIO");
        return lista;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
