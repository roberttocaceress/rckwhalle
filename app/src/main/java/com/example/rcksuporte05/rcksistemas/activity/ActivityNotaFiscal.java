package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaNotaFiscalAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.NotaFiscal;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityNotaFiscal extends AppCompatActivity implements  View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    @BindView(R.id.tbl_resumo_nota_fiscal)
    Toolbar toolbar;

    @BindView(R.id.txvMes)
    TextView txvMes;

    @BindView(R.id.txvAno)
    TextView txvAno;

    @BindView(R.id.img_view_anterior)
    ImageView btnAnterior;
    @BindView(R.id.img_view_proximo)
    ImageView btnProximo;

    @BindView(R.id.recycle_nota_fiscal)
    RecyclerView recyclerView;

    @BindView(R.id.progress_splash)
    ProgressBar progress_bar;
    @BindView(R.id.progress_title)
    TextView progress_title;


    @BindView(R.id.card_view_menu)
    CardView card_view_menu;

    @BindView(R.id.img_view_menu)
    ImageView btn_view_menu;
    @BindView(R.id.id_opcao_mes)
    Switch chk_opcao_mes;

    @BindView(R.id.id_opcao_semana)
    Switch chk_opcao_semana;

    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    PreferenciaDAO  preferenciaDAO = new PreferenciaDAO(db);
    PackageInfo pInfo = null;
    private int indMes = 0;
    private int indSem = 0;
    private int pag = 1;
    Context context;

    String periodo = "";
    String data[];
    String pagData[] = {"", "", "", ""};

    private ListaNotaFiscalAdapter listaNotaFiscalAdapter;
    private ListaNotaFiscalAdapter.notaFiscalListener listenerNota;
    private final LinearLayout.LayoutParams params = null;
    private static List<NotaFiscal> listaNotas = new ArrayList<>();
    private boolean visivel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_fiscal);
        ButterKnife.bind(this);
        context = this;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        btn_view_menu.setTooltipText("Toque para visualizar/esconder menu para definir o tipo de relatório!");
        btnAnterior.setOnClickListener(this);
        btnProximo.setOnClickListener(this);
        btn_view_menu.setOnClickListener(this);

        chk_opcao_mes.setChecked(true);
        chk_opcao_semana.setChecked(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getMesAno(indMes);
        showHideProgressBar(true);
        sincronizaSinteticoPeriodoAPI(data[2], data[3]);
        listenerNota = new ListaNotaFiscalAdapter.notaFiscalListener() {
            @Override
            public void onClickListener(int position) {
                String tipo = listaNotas.get(position).getE_entrada_s_saida();
                if ( tipo.equalsIgnoreCase("C") || tipo.equalsIgnoreCase("V"))
                    return;
                int idOperacao = listaNotas.get(position).getId_operacao();
                if (pag == 1) {
                    pag = 2;
                    toolbar.setTitle("Resumo Venda/Compra");
                    toolbar.setSubtitle( listaNotas.get(position).getNome_operacao());
                    pagData[0] = data[2];
                    pagData[1] = data[3];
                    pagData[2] = "-";
                    pagData[3] = "-";
                    showHideProgressBar(true);
                    sincronizaAnaliticoAPI(data[2], data[3], idOperacao, tipo);
                } else if (pag == 2) {
                    pag = 3;
                    String dataEmissao = listaNotas.get(position).getData_emissao();
                    toolbar.setTitle("Resumo(" + MascaraUtil.formataData(listaNotas.get(position).getData_emissao(), "/") + ")");
                    pagData[0] = data[2];
                    pagData[1] = data[3];
                    pagData[2] = String.valueOf(idOperacao);
                    pagData[3] = tipo;
                    showHideProgressBar(true);
                    sincronizaAnaliticoAPI(dataEmissao, idOperacao, tipo);
                } else if (pag == 3) {
                    //pag = 1;
                }
            }
        };

        chk_opcao_semana.setOnCheckedChangeListener(this);
        chk_opcao_mes.setOnCheckedChangeListener(this);

    }

    @Override
    public void onBackPressed() {
        toolbar.setTitle("Resumo Venda/Compra");
        if (pag == 3) {
            showHideProgressBar(true);
            sincronizaAnaliticoAPI(data[2], data[3], Integer.parseInt(pagData[2]), pagData[3]);
            pagData[0] = data[2];
            pagData[1] = data[3];
            pagData[2] = "-";
            pagData[3] = "-";
            pag = 2;
        } else if (pag == 2) {
            showHideProgressBar(true);
            toolbar.setSubtitle("");
            sincronizaSinteticoPeriodoAPI(data[2], data[3]);
            pag = 1;
        } else if (pag == 1)
            super.onBackPressed();
    }

    private void showHideProgressBar(boolean visivel) {
        if (visivel)
            progress_bar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progress_bar.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
    }
    private void getMesAno( int ind) {
        String sql;
        if (ind < 0)
            sql = "SELECT " +
                    "strftime('%m', date('now', 'start of month', '" + ind + " month') ), " +
                    "strftime('%Y', date('now', 'start of month', '" + ind + " month') ), " +
                    "date('now', 'start of month', '" + ind + " month'), " +
                    "date('now', 'start of month', '" + (ind + 1) + " month', '-1 day');";
        else
            sql = "SELECT " +
                    "strftime('%m', date('now', 'start of month', '+" + ind + " month') ), " +
                    "strftime('%Y', date('now', 'start of month', '+" + ind + " month') ), " +
                    "date('now', 'start of month', '+" + ind + " month'), " +
                    "date('now', 'start of month', '+" + (ind + 1) + " month', '-1 day');";

        data = db.pegaData(sql);
        switch ( Integer.parseInt(data[0])) {
            case 1:
                periodo = " JAN ";
                break;
            case 2:
                periodo = " FEV ";
                break;
            case 3:
                periodo = " MAR ";
                break;
            case 4:
                periodo = " ABR ";
                break;
            case 5:
                periodo = " MAI ";
                break;
            case 6:
                periodo = " JUN ";
                break;
            case 7:
                periodo = " JUL ";
                break;
            case 8:
                periodo = " AGO ";
                break;
            case 9:
                periodo = " SET ";
                break;
            case 10:
                periodo = " OUT ";
                break;
            case 11:
                periodo = " NOV ";
                break;
            case 12:
                periodo = " DEZ ";
                break;
        }
        txvMes.setText( periodo );
        txvAno.setText( " " + data[1] + " " );

    }
    private void getSemMes(int ind) { //pega os dias de cada semana(SEG A DOM) dentro do mes
        //int dIni = ( ( -(ind)) * 7) ;
        //int dFin = ( ( 1 + ( -(ind))) * 7) -1;
        String sql;
        if ( ind < 0)
            sql = "SELECT strftime('%m', date('now', 'weekday 0', '-0 days') ), " +
                    "strftime('%Y', date('now', 'weekday 0', '-7 days') ), " +
                    "date('now', 'weekday 0', '-" + (( ( 1 + ( -(ind))) * 7) -1) + " days')," +
                    "date('now', 'weekday 0', '-" + ( ( -(ind)) * 7) + " days')";
        else
            sql = "SELECT strftime('%m', date('now', 'weekday 0', '-0 days') ), " +
                    "strftime('%Y', date('now', 'weekday 0', '-7 days') ), " +
                    "date('now', 'weekday 0', '-6 days')," +
                    "date('now', 'weekday 0', '-0 days')";

        data = db.pegaData(sql);
        txvMes.setText(MascaraUtil.formataData(data[2], "/"));
        txvAno.setText(MascaraUtil.formataData(data[3], "/"));
    }


    private void sincronizaSinteticoPeriodoAPI(String dataInicial, String dataFinal) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        String movEstoque = "S", movFinanceiro = "S", pagaComissao = "S";
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<NotaFiscal>> call = apiRotas.getNotaSinteticoPeriodo( dataInicial, dataFinal, movEstoque, movFinanceiro, pagaComissao, Integer.parseInt( UsuarioHelper.getUsuario().getId_usuario() ), cabecalho);

            call.enqueue(new Callback<List<NotaFiscal>>() {
                @Override
                public void onResponse(Call<List<NotaFiscal>> call, Response<List<NotaFiscal>> response) {
                    if (response.code() == 200) {
                        listaNotas = response.body();
                        setRecyclerView(listaNotas);
                    } else {
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NotaFiscal>> call, Throwable t) {
                    showHideProgressBar(false);
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            showHideProgressBar(false);
            Toast.makeText( context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }

    private void sincronizaAnaliticoAPI(String dataInicial, String dataFinal, int idOperacao, String tipo) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<NotaFiscal>> call = apiRotas.getNotaAnaliticoPeriodo(dataInicial, dataFinal, idOperacao, tipo, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);

            call.enqueue(new Callback<List<NotaFiscal>>() {
                @Override
                public void onResponse(Call<List<NotaFiscal>> call, Response<List<NotaFiscal>> response) {
                    if (response.code() == 200) {
                        listaNotas = response.body();
                        setRecyclerView(listaNotas);
                    } else {
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NotaFiscal>> call, Throwable t) {
                    showHideProgressBar(false);
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            showHideProgressBar(false);
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }

    private void sincronizaAnaliticoAPI(String dataEmissao, int idOperacao, String tipo) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<NotaFiscal>> call = apiRotas.getNotaAnaliticoData(dataEmissao, idOperacao, tipo, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);

            call.enqueue(new Callback<List<NotaFiscal>>() {
                @Override
                public void onResponse(Call<List<NotaFiscal>> call, Response<List<NotaFiscal>> response) {
                    if (response.code() == 200) {
                        listaNotas = response.body();
                        setRecyclerView(listaNotas);
                    } else {
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NotaFiscal>> call, Throwable t) {
                    showHideProgressBar(false);
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            showHideProgressBar(false);
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }

    private void setRecyclerView(List<NotaFiscal> lista) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listaNotaFiscalAdapter = new ListaNotaFiscalAdapter(lista, listenerNota);
        recyclerView.setAdapter(listaNotaFiscalAdapter);
        listaNotaFiscalAdapter.notifyDataSetChanged();
        showHideProgressBar(false);
    }

    @Override
    public void onClick(View view) {
        if ( view == btnAnterior) {
            if (chk_opcao_mes.isChecked()) {
                indMes -= 1;
                getMesAno(indMes);
            }  else if (chk_opcao_semana.isChecked()) {
                indSem -= 1;
                getSemMes(indSem);
            }
            pag = 1;
            showHideProgressBar(true);
            toolbar.setTitle("Resumo Venda/Compra");
            toolbar.setSubtitle("");
            sincronizaSinteticoPeriodoAPI(data[2], data[3]);
        } else if( view == btnProximo) {
            if (chk_opcao_mes.isChecked()) {
                if (indMes == 0)
                    return;
                indMes += 1;
                getMesAno(indMes);
            } else if (chk_opcao_semana.isChecked()) {
                if (indSem == 0)
                    return;
                indSem += 1;
                getSemMes(indSem);
            }
            pag = 1;
            showHideProgressBar(true);
            toolbar.setTitle("Resumo Venda/Compra");
            toolbar.setSubtitle("");
            sincronizaSinteticoPeriodoAPI(data[2], data[3]);
        } else if ( view == btn_view_menu) {
            ViewGroup.LayoutParams params = card_view_menu.getLayoutParams();
            if ( !visivel) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                visivel = !visivel;
                btn_view_menu.setImageResource( R.drawable.ic_action_arrow_down_white);
            } else {
                params.height = 0;
                visivel = !visivel;
                btn_view_menu.setImageResource( R.drawable.ic_action_arrow_up_white);
            }
            card_view_menu.setLayoutParams(params);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if ( compoundButton == chk_opcao_semana) {
            chk_opcao_mes.setChecked(!isChecked);
            if (isChecked)
                getSemMes(indSem);
            else {
                indMes = -18;
                getMesAno(indMes);
            }
            showHideProgressBar(true);
            toolbar.setTitle("Resumo Venda/Compra");
            toolbar.setSubtitle("");
            sincronizaSinteticoPeriodoAPI(data[2], data[3]);
            pag = 1;
        } else if(compoundButton == chk_opcao_mes) {
            chk_opcao_semana.setChecked(!isChecked);
            if (isChecked) {
                indMes = -18;
                getMesAno(indMes);
            } else
                getSemMes(indSem);
            showHideProgressBar(true);
            toolbar.setTitle("Resumo Venda/Compra");
            toolbar.setSubtitle("");
            sincronizaSinteticoPeriodoAPI(data[2], data[3]);
            pag = 1;
        }
    }
}
