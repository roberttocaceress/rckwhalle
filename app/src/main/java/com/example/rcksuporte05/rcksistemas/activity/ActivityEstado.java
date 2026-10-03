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


import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.UfAdapter;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityEstado extends AppCompatActivity implements UfAdapter.UfListener {

    @BindView(R.id.listaUF)
    RecyclerView recyclerView;
    @BindView(R.id.toolbarUf)
    Toolbar toolbarUf;
    private UfAdapter ufAdapter;
    private SearchView searchView;
    private String[] uf;
    private String ufSelecioando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_uf);
        ButterKnife.bind(this);
        toolbarUf.setTitle("Selecione a UF");
        uf = getResources().getStringArray(R.array.uf);
        if (getIntent().getIntExtra("cliente", 0) >= 1)
            if (ClienteHelper.getPosicaoUf() > -1)
                ufSelecioando = uf[ClienteHelper.getPosicaoUf()];
        else if (getIntent().getIntExtra("prospect", 0) >= 1)
            if (ProspectHelper.getPosicaoUf() > -1)
                ufSelecioando = uf[ProspectHelper.getPosicaoUf()];

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        setRecyclerView(uf);

        setSupportActionBar(toolbarUf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(int position) {
        ufAdapter.toggleSelection(position);
        ufAdapter.notifyDataSetChanged();
        Intent intent = new Intent(ActivityEstado.this, ActivityMunicipio.class).putExtra("uf", 1);
        if (getIntent().getIntExtra("cliente", 0) >= 1) {
            if (ClienteHelper.getPosicaoMunicipio() > -1 && position != ClienteHelper.getPosicaoUf())
                ClienteHelper.setPosicaoMunicipio(-1);
            for (int i = 0; uf.length > i; i++)
                if (uf[i].equals(ufAdapter.getItem(position))) {
                    ClienteHelper.setPosicaoUf(i);
                    break;
                }
            intent.putExtra("cliente", 1);
            startActivity(intent);
            finish();
        } else if (getIntent().getIntExtra("prospect", 0) >= 1) {
            if (ProspectHelper.getPosicaoMunicipio() > -1 && position != ProspectHelper.getPosicaoUf())
                ProspectHelper.setPosicaoMunicipio(-1);

            for (int i = 0; uf.length > i; i++)
                if (uf[i].equals(ufAdapter.getItem(position))) {
                    ProspectHelper.setPosicaoUf(i);
                    break;
                }
            intent.putExtra("prospect", 1);
            startActivity(intent);
            finish();
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
                    if (query.trim().equals(""))
                        setRecyclerView(uf);
                    else
                        setRecyclerView(buscaUF(query));
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("UF");
        return true;
    }

    private void setRecyclerView(String[] uf) {
        ufAdapter = new UfAdapter(uf, this);
        recyclerView.setAdapter(ufAdapter);
        ufAdapter.selecionado(ufSelecioando);
        ufAdapter.notifyDataSetChanged();
    }

    private String[] buscaUF(String query) {
        query = query.toUpperCase();
        List<String> busca = new ArrayList<>();
        for (int i = 0; uf.length > i; i++)
            if (uf[i].toUpperCase().contains(query))
                busca.add(uf[i]);
        final String[] resultado = new String[busca.size()];
        for (int i = 0; busca.size() > i; i++)
            resultado[i] = busca.get(i);
        return resultado;
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
