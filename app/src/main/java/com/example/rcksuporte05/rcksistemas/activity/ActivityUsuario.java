package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaUsuarioAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUsuario extends AppCompatActivity {
    TextView txtView;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ListaUsuarioAdapter listaUsuarioAdapter;
    ListaUsuarioAdapter.Listener listenerUsuarioAdapter;
    List<Usuario> lista;
    Context context;
    DBHelper db = new DBHelper(this);
    UsuarioDAO usuarioDAO = new UsuarioDAO(db);
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
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
        toolbar.setTitle("Voltar");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String razaoSocial = getIntent().getStringExtra("razaoSocial");
        String url = getIntent().getStringExtra("url");
        int porta = getIntent().getIntExtra("porta", 725);
        String versaoAPI = getIntent().getStringExtra("versaoAPI");
        String conexaoSegura = getIntent().getStringExtra("conexaoSegura");
        String acao = getIntent().getStringExtra("acao");
        assert razaoSocial != null;
        if (!razaoSocial.isEmpty())
            toolbar.setTitle(razaoSocial);

        txtView = findViewById(R.id.txtView);
        if (acao.equalsIgnoreCase("lista")) {
            txtView.setText("Lista de usuário(s)");
            getListaUsuarios(url, porta, conexaoSegura, versaoAPI);

        }else {
            txtView.setText("Usuário");
            getUsuario();
        }
        listenerUsuarioAdapter = new ListaUsuarioAdapter.Listener() {
            @Override
            public void onClickListener(int position) {
                //Toast.makeText(getApplicationContext(), "Clicou em update para " + lista.get(position).getNome_usuario(), Toast.LENGTH_SHORT).show();
                //updateUsuario();
            }
        };
    }

    private void getUsuario(){
        try {
            String id_usuario = UsuarioHelper.getUsuario().getId_usuario();

            usuario = new Usuario();
            List<Usuario> lista = new ArrayList<>();
            lista = usuarioDAO.getLista("SELECT WU.*, BU.VALOR_SALDO AS VALOR_SALDO_VERBA, BU.DATA_ULTIMA_ATUALIZACAO AS DATA_ATUALIZA_VERBA, " +
                    "LU.DATA_SINCRONIA_CLIENTE, LU.DATA_SINCRONIA_PRODUTO, LU.DATA_SINCRONIA_IMAGEN, LU.DATA_SINCRONIA_COBRANCA, LU.SENHA AS SENHA_LOGADO " +
                    "FROM TBL_WEB_USUARIO AS WU " +
                    "LEFT JOIN TBL_VENDEDOR_BONUS_RESUMO AS BU ON BU.ID_VENDEDOR =  WU.ID_QUANDO_VENDEDOR " +
                    "LEFT JOIN TBL_LOGIN AS LU ON LU.LOGIN = WU.LOGIN " +
                    "WHERE WU.ID_USUARIO = '" + id_usuario + "'");
            setRecyclerView(lista);
        } catch ( NullPointerException e) {
            Toast.makeText(ActivityUsuario.this, "Falha ao obter dados do usuario ativo", Toast.LENGTH_LONG).show();
            finish();
        } catch ( Exception e) {
            Toast.makeText(ActivityUsuario.this, "Falha ao obter dados do usuario ativo", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    private void getListaUsuarios(String url, int porta, String conexaoSegura, String versaoAPI){

        try {
            Api.urlTecnico = (conexaoSegura.equalsIgnoreCase("N")) ?
                    "http://" + url + ":" + Integer.toString(porta) + "/rckwhalleAPI" + versaoAPI.substring(0,5) + "/ws/" :
                    "https://" + url;
            Api.time_conn = "10";
            Api.time_read = "180";
            Api.time_write = "180";
            Rotas apiRotas = Api.buildRetrofitTecnico(true);

            Call<List<Usuario>> call = apiRotas.getLista();
            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    switch (response.code()) {
                        case 200:
                            lista = response.body();
                            setRecyclerView(lista);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Falha ao obter a lista de usuários", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão", Toast.LENGTH_SHORT).show();
                }
            });
        } catch ( NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Parametros para conexão nao definidos corretamente!", Toast.LENGTH_SHORT).show();
        } catch ( Exception e) {
            Toast.makeText(getApplicationContext(), "Parametros para conexão nao definidos corretamente!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUsuario() {
    }


    private void setRecyclerView(List<Usuario> lista) {
        listaUsuarioAdapter = new ListaUsuarioAdapter( context, lista, listenerUsuarioAdapter);
        recyclerView.setAdapter(listaUsuarioAdapter);
        listaUsuarioAdapter.notifyDataSetChanged();
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