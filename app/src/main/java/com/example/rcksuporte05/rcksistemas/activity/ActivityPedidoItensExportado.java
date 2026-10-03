package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoExportadoAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoItensExportadoAdapter;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.util.List;

import butterknife.ButterKnife;

public class ActivityPedidoItensExportado extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    Context context;
    ListaPedidoItensExportadoAdapter listaPedidoItensExportadoAdapter;
    DBHelper db = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_itens_exportado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.recyclerView);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        List<WebPedidoItens> lista = PedidoHelper.getListaWebPedidoItens();
        ButterKnife.bind(this);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(view -> onBackPressed());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager( new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false));
        toolbar.setTitle("Pedido " + lista.get(0).getId_pedido());
        setRecyclerView(lista);
    }

    private void setRecyclerView(List<WebPedidoItens> lista) {
        listaPedidoItensExportadoAdapter = new ListaPedidoItensExportadoAdapter(context, lista);
        recyclerView.setAdapter(listaPedidoItensExportadoAdapter);
        listaPedidoItensExportadoAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        PedidoHelper.setListaWebPedidoItens(null);
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.gc();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}