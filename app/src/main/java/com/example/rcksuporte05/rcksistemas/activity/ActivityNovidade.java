package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaNovidadeAdapter;
import com.example.rcksuporte05.rcksistemas.model.Novidade;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityNovidade extends AppCompatActivity {
    RecyclerView recyclerView;
    private ListaNovidadeAdapter listaNovidadeAdapter;
    private String arquivo;
    @BindView(R.id.toolbarNovidade)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novidade);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewYoutube);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));
        setRecyclerView(getListaNovidades());
    }
    private void setRecyclerView(List<Novidade> lista) {
        listaNovidadeAdapter = new ListaNovidadeAdapter(ActivityNovidade.this, lista, new ListaNovidadeAdapter.ListaNovidadeListener() {
            @Override
            public void onClickListener(int position) {
                startActivity(new Intent(ActivityNovidade.this, ActivityNovidadeVideo.class)
                .putExtra("url", lista.get(position).getArquivo())
                .putExtra("position", position));
            }
        });
        recyclerView.setAdapter(listaNovidadeAdapter);
        listaNovidadeAdapter.notifyDataSetChanged();
    }
    private List<Novidade> getListaNovidades() {
        Novidade novidade;
        List<Novidade> lista = new ArrayList<>();
        novidade = new Novidade("1", "Versao 2.*.*-*", "Cadastro de novo cliente no aplicativo", "mjln5bYagno");
        lista.add(novidade);
        novidade = new Novidade("2", "Versao 2.*.*-*", "Sincronização/exportação do novo cliente no aplicativo", "3-3vRBonKZI");
        lista.add(novidade);
        return  lista;
    }


}
