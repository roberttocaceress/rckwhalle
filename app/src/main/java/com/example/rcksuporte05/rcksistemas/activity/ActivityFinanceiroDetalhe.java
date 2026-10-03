package com.example.rcksuporte05.rcksistemas.activity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterHistoricoFinanceiroPendentes;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterHistoricoFinanceiroQuitados;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityFinanceiroDetalhe extends AppCompatActivity {

    @BindView(R.id.tbFinanceiro)
    Toolbar toolbar;
    @BindView(R.id.trLinhaColunaQuitado)
    TableRow trLinhaColunaQuitado;
    @BindView(R.id.trLinhaColunaPendente)
    TableRow trLinhaColunaPendente;
    @BindView(R.id.lstHistoricoFinanceiro)
    ListView listaHistoricoFinanceiro;
    @BindView(R.id.edtTotalTitulos)
    EditText edtTotalTitulos;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private AlertDialog.Builder alert;
    private DBHelper db = new DBHelper(this);
    PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    Utilitaria util = new Utilitaria();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro_detalhe);
        ButterKnife.bind(this);
        toolbar.setSubtitle(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sincronizaHistoricoFinanceiroAPI(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onResume() {
        getLista();
        super.onResume();
    }

    public void getLista() {
        switch (getIntent().getIntExtra("financeiro", 0)) {
            case 1:
                ListaAdapterHistoricoFinanceiroPendentes listaAdapterHistoricoFinanceiroVencido = new ListaAdapterHistoricoFinanceiroPendentes(this, HistoricoFinanceiroHelper.getHistoricoFinanceiro().getListaVencida());
                showDados("Historico Financeiro - Vencidas", HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalVencida(), listaAdapterHistoricoFinanceiroVencido);
                break;
            case 2:
                ListaAdapterHistoricoFinanceiroPendentes listaAdapterHistoricoFinanceiroAvencer = new ListaAdapterHistoricoFinanceiroPendentes(this, HistoricoFinanceiroHelper.getHistoricoFinanceiro().getListaAvencer());
                showDados("Historico Financeiro - A vencer", HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalAvencer(), listaAdapterHistoricoFinanceiroAvencer);
                break;
            case 3:
                ListaAdapterHistoricoFinanceiroQuitados listaAdapterHistoricoFinanceiroQuitado = new ListaAdapterHistoricoFinanceiroQuitados(this, HistoricoFinanceiroHelper.getHistoricoFinanceiro().getListaQuitado());
                showDados("Historico Financeiro - Quitados", HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalQuitado(), listaAdapterHistoricoFinanceiroQuitado);
                break;
        }
    }

    private void showDados(String titulo, float totalTitulos, ArrayAdapter adapter) {
        toolbar.setTitle(titulo);
        trLinhaColunaQuitado.setVisibility(View.VISIBLE);
        trLinhaColunaPendente.setVisibility(View.GONE);
        edtTotalTitulos.setText(MascaraUtil.mascaraReal(totalTitulos));
        listaHistoricoFinanceiro.setAdapter(adapter);
    }


    public void sincronizaHistoricoFinanceiroAPI(int idCliente) {
        swipe.setRefreshing(true);
        int dias_hist_financeiro = 0;
        Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
        try {
            dias_hist_financeiro = preferencia.getDias_hist_financeiro();
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Rotas apiRotas = Api.buildRetrofit(false);
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<HistoricoFinanceiro> call = apiRotas.getHistoricoFinanceiro(idCliente, dias_hist_financeiro, cabecalho);

        call.enqueue(new Callback<HistoricoFinanceiro>() {
            @Override
            public void onResponse(Call<HistoricoFinanceiro> call, Response<HistoricoFinanceiro> response) {
                HistoricoFinanceiroHelper.setHistoricoFinanceiro(response.body());
                getLista();
                swipe.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<HistoricoFinanceiro> call, Throwable t) {
                swipe.setRefreshing(false);
                util.showMsgAlerta("Não foi possivel carregar relatorio!\n        Verifique sua conexão com a internet/rede!", ActivityFinanceiroDetalhe.this);
            }
        });
    }
 }
