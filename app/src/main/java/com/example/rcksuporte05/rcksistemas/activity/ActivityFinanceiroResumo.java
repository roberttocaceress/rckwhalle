package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFinanceiroResumo extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.idToolbarFinanceiro)
    Toolbar toolbar;
    @BindView(R.id.txtVencida)
    TextView txtVencida;
    @BindView(R.id.txtAvencer)
    TextView txtAvencer;
    @BindView(R.id.txtQuitada)
    TextView txtQuitada;
    @BindView(R.id.lyVencida)
    LinearLayout lyVencida;
    @BindView(R.id.lyVencer)
    LinearLayout lyVencer;
    @BindView(R.id.lyQuitada)
    LinearLayout lyQuitada;
    @BindView(R.id.txtLimiteCredito)
    TextView txtLimiteCredito;
    @BindView(R.id.lySincronia)
    LinearLayout lySincronia;
    @BindView(R.id.txtDataHoraSincroniza)
    TextView txtDataHoraSincroniza;
    private ProgressDialog progress;
    private AlertDialog.Builder alert;
    private MenuItem sincroniza;
    private CadastroFinanceiroResumo cadastroFinanceiroResumo;

    private DBHelper db = new DBHelper(this);
    PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    Utilitaria util = new Utilitaria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro_resumo);
        ButterKnife.bind(this);
        try {
            toolbar.setSubtitle(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());
            cadastroFinanceiroResumo = HistoricoFinanceiroHelper.getCadastroFinanceiroResumo();
        } catch (NullPointerException e) {
            showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lyVencida.setOnClickListener(this);
        lyVencer.setOnClickListener(this);
        lyQuitada.setOnClickListener(this);
        /*lyVencida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null)
                    startActivity(new Intent(ActivityFinanceiroResumo.this, ActivityFinanceiroDetalhe.class).putExtra("financeiro", 1));
                else
                    showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
            }
        });
        lyVencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null)
                    startActivity(new Intent(ActivityFinanceiroResumo.this, ActivityFinanceiroDetalhe.class).putExtra("financeiro", 2));
                else
                    showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
            }
        });
        lyQuitada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null)
                      startActivity(new Intent(ActivityFinanceiroResumo.this, ActivityFinanceiroDetalhe.class).putExtra("financeiro", 3));
                else
                     showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
            }
        });
        */
        try {
            txtVencida.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroVencido()));
            txtAvencer.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroVencer()));
            txtQuitada.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroQuitado()));

            if (HistoricoFinanceiroHelper.getCliente().getLimite_credito() != null && !HistoricoFinanceiroHelper.getCliente().getLimite_credito().trim().isEmpty())
                txtLimiteCredito.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCliente().getLimite_credito()));
            else
                txtLimiteCredito.setText("< Não cadastrado >");
            try {
                txtDataHoraSincroniza.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(cadastroFinanceiroResumo.getDataUltimaAtualizacao())));
            } catch (ParseException e) {
            }
        } catch (NullPointerException e) {
        }
        sincronizaHistoricoFinanceiroAPI(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
    }

    @Override
    public void onClick(View view) {
        if (view == lyVencida) {
            if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null)
                startActivity(new Intent(ActivityFinanceiroResumo.this, ActivityFinanceiroDetalhe.class).putExtra("financeiro", 1));
            else
                showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
        } else if (view == lyVencer) {
            if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null)
                startActivity(new Intent(ActivityFinanceiroResumo.this, ActivityFinanceiroDetalhe.class).putExtra("financeiro", 2));
            else
                showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
        } else if( view == lyQuitada) {
            if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null)
                startActivity(new Intent(ActivityFinanceiroResumo.this, ActivityFinanceiroDetalhe.class).putExtra("financeiro", 3));
            else
                showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
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
                showMsgSimNao("Deseja carregar historico financeiro detalhado desse cliente?");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sincronizaHistoricoFinanceiroAPI(int idCliente) {
        int dias_hist_financeiro = 0;
        Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
        try {
            dias_hist_financeiro = preferencia.getDias_hist_financeiro();
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progress = new ProgressDialog(ActivityFinanceiroResumo.this);
        progress.setMessage("Carregando historico financeiro!");
        if (dias_hist_financeiro > 0)
            progress.setMessage("Carregando historico financeiro ultimos " + dias_hist_financeiro + " dias!");
        progress.setCancelable(false);
        progress.show();

        Rotas apiRotas = Api.buildRetrofit(false);
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<HistoricoFinanceiro> call = apiRotas.getHistoricoFinanceiro(idCliente, dias_hist_financeiro, cabecalho);

        call.enqueue(new Callback<HistoricoFinanceiro>() {
            @Override
            public void onResponse(Call<HistoricoFinanceiro> call, Response<HistoricoFinanceiro> response) {
                HistoricoFinanceiroHelper.setHistoricoFinanceiro(response.body());
                txtVencida.setText(MascaraUtil.mascaraReal(response.body().getTotalVencida()));
                txtAvencer.setText(MascaraUtil.mascaraReal(response.body().getTotalAvencer()));
                txtQuitada.setText(MascaraUtil.mascaraReal(response.body().getTotalQuitado()));
                if (HistoricoFinanceiroHelper.getCliente().getLimite_credito() != null && !HistoricoFinanceiroHelper.getCliente().getLimite_credito().trim().isEmpty())
                    txtLimiteCredito.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCliente().getLimite_credito()));
                else
                    txtLimiteCredito.setText("< Não cadastrado >");
                lySincronia.setVisibility(View.GONE);
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<HistoricoFinanceiro> call, Throwable t) {
                progress.dismiss();
                util.showMsgAlerta("Não foi possivel carregar relatorio!\n        Verifique sua conexão com a internet/rede!", ActivityFinanceiroResumo.this);
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            txtVencida.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalVencida()));
            txtAvencer.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalAvencer()));
            txtQuitada.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalQuitado()));
            if (HistoricoFinanceiroHelper.getCliente().getLimite_credito() != null && !HistoricoFinanceiroHelper.getCliente().getLimite_credito().trim().isEmpty())
                txtLimiteCredito.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCliente().getLimite_credito()));
            else
                txtLimiteCredito.setText("< Não cadastrado >");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        HistoricoFinanceiroHelper.limparDados();
        super.onDestroy();
    }

    public void showMsgSimNao( String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityFinanceiroResumo.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityFinanceiroResumo.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                sincronizaHistoricoFinanceiroAPI( HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor() );
            }

        });
        alertDialog.show();
    }
}
