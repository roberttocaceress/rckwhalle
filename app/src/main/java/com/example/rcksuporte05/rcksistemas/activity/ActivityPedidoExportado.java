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
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoExportadoAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaUsuarioAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPedidoExportado extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ListaPedidoExportadoAdapter listaPedidoExportadoAdapter;
    ListaPedidoExportadoAdapter.Listener listener;
    List<WebPedido> lista;

    Context context;
    DBHelper db = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_exportado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.recyclerView);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        ButterKnife.bind(this);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(view -> onBackPressed());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager( new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false));

        String razaoSocial = getIntent().getStringExtra("razaoSocial");
        String url = getIntent().getStringExtra("url");
        int porta = getIntent().getIntExtra("porta", 725);
        String versaoAPI = getIntent().getStringExtra("versaoAPI");
        String conexaoSegura = getIntent().getStringExtra("conexaoSegura");
        toolbar.setTitle(razaoSocial);



        listener = new ListaPedidoExportadoAdapter.Listener() {
            @Override
            public void onClickListener(int position) {
                if ( lista.get(position).getWebPedidoItens().size() > 0) {
                    PedidoHelper.setListaWebPedidoItens(lista.get(position).getWebPedidoItens());
                    startActivity(new Intent(ActivityPedidoExportado.this, ActivityPedidoItensExportado.class));
                }
                //Toast.makeText(getApplicationContext(), "Clicou para consultar os itens do pedido" + lista.get(position).getId_web_pedido_servidor() , Toast.LENGTH_SHORT).show();
                //updateUsuario();
            }
        };
        getListaPedidos(url, porta, conexaoSegura, versaoAPI);
    }

    private void getListaPedidos(String url, int porta, String conexaoSegura, String versaoAPI) {
        try {
            Api.urlTecnico = (conexaoSegura.equalsIgnoreCase("N")) ?
                    "http://" + url + ":" + Integer.toString(porta) + "/rckwhalleAPI" + versaoAPI.substring(0,5) + "/ws/" :
                    "https://" + url;
            Api.time_conn = "10";
            Api.time_read = "180";
            Api.time_write = "180";

            Rotas apiRotas = Api.buildRetrofitTecnico(true);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            //Deve se efetuar o login para a consulta aos pedidos
            String data_inicial = db.subtraiData("SELECT date('now','localtime', '-10 day');");
            String data_final = db.pegaDataAtual();
            Call<List<WebPedido>> call = apiRotas.consultarPedidos(data_inicial, data_final, cabecalho );
            call.enqueue(new Callback<List<WebPedido>>() {
                @Override
                public void onResponse(Call<List<WebPedido>> call, Response<List<WebPedido>> response) {
                    switch (response.code()) {
                        case 200:
                            lista = response.body();
                            setRecyclerView(lista);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Falha ao obter a lista de pedidos", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<WebPedido>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão", Toast.LENGTH_SHORT).show();
                }
            });
        } catch ( NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Parametros para conexão nao definidos corretamente!", Toast.LENGTH_SHORT).show();
        } catch ( Exception e) {
            Toast.makeText(getApplicationContext(), "Parametros para conexão nao definidos corretamente!!", Toast.LENGTH_SHORT).show();

        }
    }

    private void setRecyclerView(List<WebPedido> lista) {
        listaPedidoExportadoAdapter = new ListaPedidoExportadoAdapter( context, lista, listener);
        recyclerView.setAdapter(listaPedidoExportadoAdapter);
        listaPedidoExportadoAdapter.notifyDataSetChanged();
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