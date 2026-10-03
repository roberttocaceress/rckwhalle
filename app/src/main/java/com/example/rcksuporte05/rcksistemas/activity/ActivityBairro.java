package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.DAO.BairroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.BairroAdapter;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityBairro extends AppCompatActivity implements BairroAdapter.bairroListener {
    @BindView(R.id.listaBairro)
    RecyclerView recyclerView;
    @BindView(R.id.toolbarBairro)
    Toolbar toolbar;
    private BairroAdapter listaBairroAdapter;
    private SearchView searchView;
    private DBHelper db = new DBHelper(this);
    BairroDAO bairroDAO = new BairroDAO(db);
    List<Generico> listaBairro;
    Generico generico;
    String id_cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bairro);
        ButterKnife.bind(this);
        toolbar.setTitle("Pesquisar");
        setGenerico();
        try {
            id_cidade = getIntent().getStringExtra("id_cidade");
            getListaBairro("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_CIDADE = " + id_cidade + " ORDER BY NOME_BAIRRO");
        } catch ( NullPointerException e) {
            e.printStackTrace();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setGenerico() {
        generico = new Generico();
        generico.setId("0");
        generico.setNome("TODOS");
        generico.setAtivo("S");
    }

    private void getListaBairro(String sql) {
        listaBairro = new ArrayList<>();
        listaBairro.add(generico);
        listaBairro.addAll(bairroDAO.getLista(sql));
        setRecyclerView(listaBairro);
    }

    @Override
    public void onClick(int position) {
        listaBairroAdapter.toggleSelection(position);
        listaBairroAdapter.notifyDataSetChanged();
        setResult(RESULT_OK, new Intent().putExtra("result", listaBairro.get(position).getId()));
        finish();
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
                    if ( query.trim().equals("") || query.isEmpty() )
                        getListaBairro("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_CIDADE = " + id_cidade + " ORDER BY NOME_BAIRRO");
                    else
                        buscaBairro(query);
                    System.gc();
                } catch (Exception e) {
                }
                return false;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Bairro");
        return true;
    }
    private void setRecyclerView(List<Generico> lista) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        listaBairroAdapter = new BairroAdapter( lista, this);
        recyclerView.setAdapter(listaBairroAdapter);
        listaBairroAdapter.notifyDataSetChanged();
    }

    private void buscaBairro(String query) {
        listaBairro = new ArrayList<>();
        listaBairro.addAll(bairroDAO.getLista("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_CIDADE = " + id_cidade + " AND NOME_BAIRRO LIKE '%" + query.toUpperCase() + "%' ORDER BY NOME_BAIRRO"));
        setRecyclerView(listaBairro);
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