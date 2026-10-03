package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAnaliseCredito extends AppCompatActivity { //Analisar esta aquitivity
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNomeCliente)
    TextView txtNomeCliente;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.txtPendenciaFinanceira)
    TextView txtPendenciaFinanceira;
    @BindView(R.id.txtLimiteCredito)
    TextView txtLimiteCredito;
    @BindView(R.id.txtValorPedido)
    TextView txtValorPedido;
    @BindView(R.id.txtLimiteUtilizado)
    TextView txtLimiteUtilizado;
    @BindView(R.id.txtSaldoRestante)
    TextView txtSaldoRestante;
    private CadastroFinanceiroResumo cadastroFinanceiroResumo;
    private MenuItem sincroniza;
    private ProgressDialog progress;
    private final DBHelper db = new DBHelper(this);
    Utilitaria util = new Utilitaria();
    private Context context;
    @OnClick(R.id.btnOk)
    public void onClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_de_credito);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showDados();
    }

    public void showDados() {
        cadastroFinanceiroResumo = HistoricoFinanceiroHelper.getCadastroFinanceiroResumo();
        txtNomeCliente.setText(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());
        txtValorPedido.setText("(-)" + MascaraUtil.mascaraReal(getIntent().getFloatExtra("valorPedido", 0)));

        txtLimiteCredito.setText(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito() != null && HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito() > 0 ?
                "(+)" + MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito()) :
                "(+)" + MascaraUtil.mascaraReal(0.f));

        txtPendenciaFinanceira.setText(cadastroFinanceiroResumo.getFinanceiroVencido() != null && cadastroFinanceiroResumo.getFinanceiroVencido() > 0 ?
                "(*)" + MascaraUtil.mascaraReal(cadastroFinanceiroResumo.getFinanceiroVencido()) :
                "(*)R$ NÃO TEM");

        Float limiteUltilizado = db.soma("SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO " +
                "WHERE PEDIDO_ENVIADO = 'N' AND ID_CONDICAO_PAGAMENTO <> 1 " +
                "AND ID_WEB_PEDIDO <> " + PedidoHelper.getIdPedido() + " " +
                "AND ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro_servidor() + ";") + cadastroFinanceiroResumo.getLimiteUtilizado();

        txtLimiteUtilizado.setText( limiteUltilizado != null && limiteUltilizado > 0 ?
                "(-)" + MascaraUtil.mascaraReal(limiteUltilizado) :
                "(-)" + MascaraUtil.mascaraReal(0.f) );

        Float saldoRestante = cadastroFinanceiroResumo.getLimiteCredito() - limiteUltilizado - getIntent().getFloatExtra("valorPedido", 0) - cadastroFinanceiroResumo.getFinanceiroVencido();
        txtSaldoRestante.setText("(=)" + MascaraUtil.mascaraReal(saldoRestante));
        if (saldoRestante < 0 || cadastroFinanceiroResumo.getFinanceiroVencido() > 0) {
            txtStatus.setText("<NEGADO>");
            txtStatus.setTextColor(Color.RED);
            txtSaldoRestante.setTextColor(Color.RED);
        } else {
            txtStatus.setText("<APROVADO>");
            txtStatus.setTextColor(Color.parseColor("#0277BD"));
            txtSaldoRestante.setTextColor(Color.parseColor("#0277BD"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        sincroniza = menu.findItem(R.id.menu_sincroniza);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_sincroniza:
                sincronizaFinanceiroAPI(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sincronizaFinanceiroAPI(int id) { //id do cliente no servidor
        showHideProgressDialog(true);
        Rotas apiRotas = Api.buildRetrofit(false);
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<CadastroFinanceiroResumo> call = apiRotas.sincronizaFinanceiro(id, cabecalho);
        call.enqueue(new Callback<CadastroFinanceiroResumo>() {
            @Override
            public void onResponse(@NonNull Call<CadastroFinanceiroResumo> call, @NonNull Response<CadastroFinanceiroResumo> response) {
                switch (response.code()) {
                    case 200:
                        CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
                        cadastroFinanceiroResumoDAO.addUpdate(response.body());
                        HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(response.body());
                        showHideProgressDialog(false);
                        showDados();
                        break;
                    default:
                        util.showMsgAlerta("Falha ao atualizar os dados! Tente novamente!\n("+ response.code() + ")", ActivityAnaliseCredito.this);
                        break;
                }
            }
            @Override
            public void onFailure(@NonNull Call<CadastroFinanceiroResumo> call, @NonNull Throwable t) {
                showHideProgressDialog(false);
                util.showMsgAlerta("Não foi possivel atualizar o financeiro deste cliente!\n" +
                        "Verifique sua conexão com a internet/servidor! " +
                        "\n" + t.getMessage(), ActivityAnaliseCredito.this);
            }
        });
    }
    private void showHideProgressDialog(boolean visivel){
        if ( !visivel) {
            progress.dismiss();
            return;
        }
        progress = new ProgressDialog(context);
        progress.setMessage("Aguarde...!");
        progress.setCancelable(false);
        progress.show();
    }
}
