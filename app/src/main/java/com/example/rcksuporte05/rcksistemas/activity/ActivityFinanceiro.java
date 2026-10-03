package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaFinanceiroAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFinanceiro extends AppCompatActivity {

    @BindView(R.id.tbFinanceiro)
    Toolbar toolbar;

    @BindView(R.id.layout_vencido)
    LinearLayout layout_vencido;

    @BindView(R.id.layout_vencer)
    LinearLayout layout_vencer;

    @BindView(R.id.layout_quitado)
    LinearLayout layout_quitado;

    @BindView(R.id.listaVencido)
    RecyclerView recycle_vencido;

    private DBHelper db;
    private ConfiguracaoDAO configuracaoDAO;
    private ListaFinanceiroAdapter listaFinanceiroAdapter;
    private ListaFinanceiroAdapter.FinanceiroAdapterListener listenerFinanceiro;
    private final LinearLayout.LayoutParams params = null;
    PackageInfo pInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);
        ButterKnife.bind(this);
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        toolbar.setTitle("Histórico financeiro");
        toolbar.setSubtitle(ClienteHelper.getCliente().getNome_cadastro());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager layoutManagerVencido = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle_vencido.setLayoutManager(layoutManagerVencido);
          layout_vencido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Financeiro> listaFinanceiro = new ArrayList<Financeiro>();
                preencheRecycleFinanceiro(listaFinanceiro);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        layout_vencer.getLayoutParams();
                //float wh = params.weight;
                params.weight = 0.0f;
                layout_vencer.setLayoutParams(params);
                layout_quitado.setLayoutParams(params);
                params = (LinearLayout.LayoutParams)
                        layout_vencido.getLayoutParams();
                params.weight = 1;
                layout_vencido.setLayoutParams(params);
            }
        });
        layout_vencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Financeiro> listaFinanceiro = new ArrayList<Financeiro>();
                preencheRecycleFinanceiro(listaFinanceiro);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        layout_quitado.getLayoutParams();
                params.weight = 0.0f;
                layout_vencido.setLayoutParams(params);
                layout_quitado.setLayoutParams(params);
                params = (LinearLayout.LayoutParams)
                        layout_vencer.getLayoutParams();
                params.weight = 1;
                layout_vencer.setLayoutParams(params);
            }
        });
        layout_quitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Financeiro> listaFinanceiro = new ArrayList<Financeiro>();
                preencheRecycleFinanceiro(listaFinanceiro);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        layout_vencido.getLayoutParams();
                params.weight = 0.0f;
                layout_vencido.setLayoutParams(params);
                layout_vencer.setLayoutParams(params);
                params = (LinearLayout.LayoutParams)
                        layout_quitado.getLayoutParams();
                params.weight = 1;
                layout_quitado.setLayoutParams(params);
            }
        });
    }

    private void pesquisaTitulo(String tipo, int idCliente) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        String version = pInfo.versionName.substring(0,5);
        int dias_historico_financeiro = 0;
        db = new DBHelper(ActivityFinanceiro.this);
        configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

        if ( !configuracao.getId().isEmpty() ) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<Financeiro>> call = apiRotas.getFinanceiro(tipo, idCliente, dias_historico_financeiro, cabecalho);
            call.enqueue(new Callback<List<Financeiro>>() {
                @Override
                public void onResponse(Call<List<Financeiro>> call, Response<List<Financeiro>> response) {
                    if (response.code() == 200) {
                        List<Financeiro> listaFinanceiro = new ArrayList<>();
                        listaFinanceiro = response.body();
                        preencheRecycleFinanceiro(listaFinanceiro);
                    } else
                        Toast.makeText(ActivityFinanceiro.this, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<List<Financeiro>> call, Throwable t) {
                    Toast.makeText(ActivityFinanceiro.this, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText( ActivityFinanceiro.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar",Toast.LENGTH_LONG).show();
        }
    }


    private void preencheRecycleFinanceiro(List<Financeiro> listaFinanceiro) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycle_vencido.setLayoutManager(layoutManager);
        listaFinanceiroAdapter = new ListaFinanceiroAdapter(listaFinanceiro, listenerFinanceiro);
        recycle_vencido.setAdapter(listaFinanceiroAdapter);
        listaFinanceiroAdapter.notifyDataSetChanged();
    }
}
