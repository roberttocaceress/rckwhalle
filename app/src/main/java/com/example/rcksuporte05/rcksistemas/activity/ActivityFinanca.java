package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ContasBancoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaFinancaAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFinanca extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    @BindView(R.id.tbl_resumo_financeiro)
    Toolbar toolbar;

    //@BindView(R.id.txvMes)
    //TextView txvMes;

    //@BindView(R.id.txvAno)
   // TextView txvAno;

   //@BindView(R.id.img_view_anterior)
   // ImageView btnAnterior;
   // @BindView(R.id.img_view_proximo)
   // ImageView btnProximo;

    @BindView(R.id.recycle_financa)
    RecyclerView recyclerView;

    @BindView(R.id.progress_splash)
    ProgressBar progress_bar;
    @BindView(R.id.progress_title)
    TextView progress_title;

    @BindView(R.id.card_view_menu)
    CardView card_view_menu;

    @BindView(R.id.img_view_avancar)
    ImageView btn_view_avancar;

    @BindView(R.id.img_view_menu)
    ImageView btn_view_menu;

    //@BindView(R.id.id_opcao_mes)
    //Switch chk_opcao_mes;

    //@BindView(R.id.id_opcao_semana)
    //Switch chk_opcao_semana;

    @BindView(R.id.id_opcao_emissao)
    CheckBox id_opcao_emissao;

    @BindView(R.id.id_opcao_competencia)
    CheckBox id_opcao_competencia;

    @BindView(R.id.id_opcao_vencimento)
    CheckBox id_opcao_vencimento;

    @BindView(R.id.id_opcao_quitacao)
    CheckBox id_opcao_quitacao;

    @BindView(R.id.id_opcao_pendentes)
    CheckBox id_opcao_pendentes;

    @BindView(R.id.id_opcao_quitadas)
    CheckBox id_opcao_quitadas;

    @BindView(R.id.id_opcao_vencidas)
    CheckBox id_opcao_vencidas;

    @BindView(R.id.id_opcao_avencer)
    CheckBox id_opcao_avencer;

    @BindView(R.id.edt_data_final)
    EditText edt_data_final;

    @BindView(R.id.edt_data_inicial)
    EditText edt_data_inicial;

    @BindView(R.id.sp_conta_bancaria)
    Spinner sp_conta_bancaria;

    @BindView(R.id.sp_especie)
    Spinner sp_especie;

    @BindView(R.id.edt_nome_favorecido)
    EditText edt_nome_favorecido;

    @BindView(R.id.edt_numero_documento)
    EditText edt_numero_documento;

    @BindView(R.id.edt_numero_cheque)
    EditText edt_numero_cheque;


    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db) ;
    private PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    PackageInfo pInfo = null;
    //private int indMes = -18;
    private int indMes = 0;
    private int indSem = 0;
    private int pag = 1;
    Context context;

    String periodo = "";
    String situacao = "";
    int idConta = 0;
    String tipo = "";
    String data[];
    String pagData[] = {"", "", "", ""};

    private ListaFinancaAdapter listaFinancaAdapter;
    private ListaFinancaAdapter.financaListener listenerFinanca;
    private final LinearLayout.LayoutParams params = null;
    private static List<FinanceiroResumo> listaFinanceiro = new ArrayList<>();
    private boolean visivel;
    private String url;

    List<Generico> listaConta;
    List<Generico> listaEspecie;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financa);
        ButterKnife.bind(this);
        context = this;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        btn_view_menu.setTooltipText("Toque para visualizar/esconder menu para definir o tipo de relatório!");
        showHideFiltros(true);

        try {
            ContasBancoDAO contasBancoDAO = new ContasBancoDAO(db);
            listaConta = new ArrayList<>();
            Generico generico = new Generico();
            generico.setId("0");
            generico.setNome("TODAS");
            generico.setAtivo("S");
            listaConta.add(generico);
            listaConta.addAll(contasBancoDAO.getLista("SELECT * FROM TBL_CONTAS_BANCO;"));
            sp_conta_bancaria.setAdapter( new ArrayAdapter<>(ActivityFinanca.this, android.R.layout.simple_list_item_activated_1, listaConta) );

        } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
        } catch ( Exception ex) {
        }

        try {
            //ContasBancoDAO contasBancoDAO = new ContasBancoDAO(db);
            String[] especie = {"TODAS", "Anuidade", "Boleto", "Carnê", "Carteira", "Cheque", "Cartão de Crédito",
                    "Cartão de Débito", "Contrato", "Cupom Fiscal", "Dinheiro", "Débito Automático",
                    "Deposito", "Duplicata", "Fatura", "Holerite", "Guia", "Mensalidade", "Nota Físcal",
                    "Pedido", "Previsao", "Promissoria", "Recibo", "Relatorio", "Renegociação", "Saque", "Transferencia"};
            listaEspecie = new ArrayList<>();
            Generico generico ;

            for (int i = 0; i < especie.length; i++) {
                generico = new Generico();
                generico.setId( String.valueOf(i) );
                generico.setNome( especie[i]);
                generico.setAtivo("S");
                listaEspecie.add(generico);
            }
            //listaConta.addAll(contasBancoDAO.getLista("SELECT * FROM TBL_CONTAS_BANCO;"));
            sp_especie.setAdapter( new ArrayAdapter<>(ActivityFinanca.this, android.R.layout.simple_list_item_activated_1, listaEspecie) );

        } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
        } catch ( Exception ex) {
        }

        btn_view_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( pag == 1) {
                    if (!id_opcao_emissao.isChecked() && !id_opcao_competencia.isChecked() && !id_opcao_vencimento.isChecked() && !id_opcao_quitacao.isChecked()) {
                        Toast.makeText(ActivityFinanca.this, "Atenção!!\nFavor selecione um periodo(Emissão/Competencia/Vencimento/Quitação)", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dataInicial = formatter.format(dataIni);
                        Date dataFin = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_final.getText().toString());
                        String dataFinal = formatter.format(dataFin);
                        if (dataIni.after(dataFin)) {
                            Toast.makeText(ActivityFinanca.this, "A data final não pode ser anterior a data inicial", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String razaoSocial = edt_nome_favorecido.getText().toString().isEmpty() ? "-" : edt_nome_favorecido.getText().toString();
                        String documento   = edt_numero_documento.getText().toString().isEmpty() ? "-" : edt_numero_documento.getText().toString();
                        String cheque      = edt_numero_cheque.getText().toString().isEmpty() ? "-" : edt_numero_cheque.getText().toString();
                        String idConta     = listaConta.get(sp_conta_bancaria.getSelectedItemPosition()).getId();
                        String especie     = listaEspecie.get(sp_especie.getSelectedItemPosition()).getNome().toString();
                        situacao           = situacao.isEmpty() ? "T" : situacao;
                        showHideFiltros(false);
                        sincronizaPeriodoAPI(dataInicial, dataFinal, periodo, situacao, razaoSocial, documento, cheque, idConta, especie);
                    } catch (ParseException e) {
                        Toast.makeText(ActivityFinanca.this, "Atenção!!\nA data inicial/final não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
        btn_view_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showHideFiltros(true);
            }
        });
        id_opcao_emissao.setOnCheckedChangeListener(this);
        id_opcao_emissao.setChecked(false);
        id_opcao_competencia.setOnCheckedChangeListener(this);
        id_opcao_vencimento.setOnCheckedChangeListener(this);
        id_opcao_quitacao.setOnCheckedChangeListener(this);
        id_opcao_emissao.setChecked(true);

        id_opcao_pendentes.setChecked(false);
        id_opcao_quitadas.setChecked(false);
        id_opcao_vencidas.setChecked(false);
        id_opcao_avencer.setChecked(false);

        id_opcao_pendentes.setOnCheckedChangeListener(this);
        id_opcao_quitadas.setOnCheckedChangeListener(this);
        id_opcao_vencidas.setOnCheckedChangeListener(this);
        id_opcao_avencer.setOnCheckedChangeListener(this);
        edt_data_inicial.setText("01/09/2020");
        edt_data_final.setText("31/10/2020");

        //chk_opcao_mes.setChecked(true);
        //chk_opcao_semana.setChecked(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getMesAno(indMes);
        //showHideProgressBar(true);
        listenerFinanca = new ListaFinancaAdapter.financaListener() {
            @Override
            public void onClickListener(int position) {
                tipo = listaFinanceiro.get(position).getDebito_credito();
                if ( tipo.equalsIgnoreCase("R") || tipo.equalsIgnoreCase("P"))
                    return;

                idConta = listaFinanceiro.get(position).getId_conta();
                if (pag == 1) {
                    toolbar.setTitle("Resumo Receita/Despesa");
                    toolbar.setSubtitle( listaFinanceiro.get(position).getNome_conta() );
                    //pagData[0] = data[2];
                    //pagData[1] = data[3];
                    //pagData[2] = "-";
                    //pagData[3] = "-";
                    showHideProgressBar(true);
                    try {
                        Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dataInicial = formatter.format(dataIni);
                        Date dataFin = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_final.getText().toString());
                        String dataFinal = formatter.format(dataFin);
                        if (dataIni.after(dataFin)) {
                            Toast.makeText(ActivityFinanca.this, "A data final não pode ser anterior a data inicial", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pag = 2;
                        String razaoSocial = edt_nome_favorecido.getText().toString().isEmpty() ? "-" : edt_nome_favorecido.getText().toString();
                        String documento   = edt_numero_documento.getText().toString().isEmpty() ? "-" : edt_numero_documento.getText().toString();
                        String cheque      = edt_numero_cheque.getText().toString().isEmpty() ? "-" : edt_numero_cheque.getText().toString();
                        String especie     = listaEspecie.get(sp_especie.getSelectedItemPosition()).getNome().toString();
                        situacao           = situacao.isEmpty() ? "T" : situacao;
                        sincronizaAnaliticoAPI( dataInicial, dataFinal, periodo, situacao, razaoSocial, documento, cheque, idConta, tipo, especie);
                        btn_view_avancar.setVisibility(View.INVISIBLE);
                        btn_view_menu.setVisibility(View.INVISIBLE);
                    } catch (ParseException e) {
                        Toast.makeText( ActivityFinanca.this, "Atenção!!\nA data inicial/final não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else if (pag == 2) {
                    pag = 3;
                    String data = MascaraUtil.formataData(listaFinanceiro.get(position).getData(), "-");
                    toolbar.setTitle("Financeiro(" + listaFinanceiro.get(position).getData() + ")");
                    //pagData[0] = data[2];
                    //pagData[1] = data[3];
                    //pagData[2] = String.valueOf(idConta);
                    //pagData[3] = tipo;
                    showHideProgressBar(true);
                    String razaoSocial = edt_nome_favorecido.getText().toString().isEmpty() ? "-" : edt_nome_favorecido.getText().toString();
                    String documento   = edt_numero_documento.getText().toString().isEmpty() ? "-" : edt_numero_documento.getText().toString();
                    String cheque      = edt_numero_cheque.getText().toString().isEmpty() ? "-" : edt_numero_cheque.getText().toString();
                    String especie     = listaEspecie.get(sp_especie.getSelectedItemPosition()).getNome().toString();
                    situacao           = situacao.isEmpty() ? "T" : situacao;
                    sincronizaAnaliticoAPI(data, periodo, situacao, razaoSocial, documento, cheque, idConta, tipo, especie);
                    btn_view_menu.setVisibility(View.INVISIBLE);
                } else if (pag == 3) {
                    //pag = 1;
                }
            }
        };
        edt_data_final.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if ( !id_opcao_emissao.isChecked() && !id_opcao_competencia.isChecked() && !id_opcao_vencimento.isChecked() && !id_opcao_quitacao.isChecked()) {
                        Toast.makeText( ActivityFinanca.this, "Atenção!!\nFavor selecione um periodo(Emissão/Competencia/Vencimento/Quitação)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Calendar dataInicial = new GregorianCalendar();
                    //Calendar dataFinal = new GregorianCalendar();
                    //Date date = new Date();
                    try {
                        dataInicial.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString()));
                    } catch (NullPointerException e) {
                        Toast.makeText( ActivityFinanca.this, "Atenção!!\nA data inicial não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
                        return false;
                    } catch (ParseException e) {
                        Toast.makeText( ActivityFinanca.this, "Atenção!!\nA data inicial não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    mostraDatePickerDialog(ActivityFinanca.this, edt_data_final);
                }
                return false;
            }
        });
        edt_data_inicial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if ( !id_opcao_emissao.isChecked() && !id_opcao_competencia.isChecked() && !id_opcao_vencimento.isChecked() && !id_opcao_quitacao.isChecked()) {
                        Toast.makeText( ActivityFinanca.this, "Atenção!!\nFavor selecione um periodo(Emissão/Competencia/Vencimento/Quitação)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    mostraDatePickerDialog(ActivityFinanca.this, edt_data_inicial);
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        toolbar.setTitle("Resumo Receita/Despesa");
        if (pag == 3) {
            try {
                Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dataInicial = formatter.format(dataIni);
                Date dataFin = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_final.getText().toString());
                String dataFinal = formatter.format(dataFin);
                if (dataIni.after(dataFin)) {
                    Toast.makeText(ActivityFinanca.this, "A data final não pode ser anterior a data inicial", Toast.LENGTH_SHORT).show();
                    return;
                }
                pag = 2;
                String razaoSocial = edt_nome_favorecido.getText().toString().isEmpty() ? "-" : edt_nome_favorecido.getText().toString();
                String documento   = edt_numero_documento.getText().toString().isEmpty() ? "-" : edt_numero_documento.getText().toString();
                String cheque      = edt_numero_cheque.getText().toString().isEmpty() ? "-" : edt_numero_cheque.getText().toString();
                String especie     = listaEspecie.get(sp_especie.getSelectedItemPosition()).getNome().toString();
                situacao           = situacao.isEmpty() ? "T" : situacao;
                sincronizaAnaliticoAPI( dataInicial, dataFinal, periodo, situacao, razaoSocial, documento, cheque, idConta, tipo, especie);
            } catch (ParseException e) {
                Toast.makeText( ActivityFinanca.this, "Atenção!!\nA data inicial/final não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
                return;
            }
            //pagData[0] = data[2];
            //pagData[1] = data[3];
            //pagData[2] = "-";
            //pagData[3] = "-";
            //pag = 2;
        } else if (pag == 2) {
            toolbar.setSubtitle("");
            pag = 1;
            btn_view_menu.setVisibility(View.VISIBLE);
            btn_view_avancar.setVisibility(View.VISIBLE);
            btn_view_avancar.performClick();
            showHideFiltros(false);
            //btn_view_avancar.setVisibility(View.VISIBLE);
            //sincronizaPeriodoAPI(, data[3]);

        } else if (pag == 1)
            super.onBackPressed();
    }

    public void mostraDatePickerDialog(Context context, final EditText campoTexto) {
        final Calendar calendar;
        calendar = campoTexto.getTag() != null ? ((Calendar) campoTexto.getTag()) : Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                try {
                    campoTexto.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()).toString());
                    campoTexto.setTag(newDate);
                    campoTexto.setBackgroundResource(R.drawable.borda_edittext);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showHideProgressBar(boolean visivel) {
        if (visivel)
            progress_bar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progress_bar.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
    }

    private void showHideFiltros(boolean visivel) {
        ViewGroup.LayoutParams params = card_view_menu.getLayoutParams();
        if (visivel) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_down_white);
        } else {
            params.height = 0;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_up_white);
        }
        card_view_menu.setLayoutParams(params);
    }

    /*
    private void getMesAno(int ind) {
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
        //txvMes.setText(periodo);
        //txvAno.setText(" " + data[1] + " ");
    }
    */
    /*
    private void getSemMes(int ind) { //pega os dias de cada semana(SEG A DOM) dentro do mes
        //int dIni = ( ( -(ind)) * 7) ;
        //int dFin = ( ( 1 + ( -(ind))) * 7) -1;
        String sql;
        if ( ind < 0)
            sql = "SELECT strftime('%m', date('now', 'weekday 0', '-0 days') ), " +
                    "strftime('%Y', date('now', 'weekday 0', '-7 days') ), " +
                    "date('now', 'weekday 0', '-" + (  ( ( 1 + ( -(ind))) * 7) -1 ) + " days')," +
                    "date('now', 'weekday 0', '-" + ( ( -(ind)) * 7) + " days')";
         else
            sql = "SELECT strftime('%m', date('now', 'weekday 0', '-0 days') ), " +
                    "strftime('%Y', date('now', 'weekday 0', '-7 days') ), " +
                    "date('now', 'weekday 0', '-6 days')," +
                    "date('now', 'weekday 0', '-0 days')";
        data = db.pegaData(sql);
        //txvMes.setText(MascaraUtil.formataData(data[2], "/"));
        //txvAno.setText(MascaraUtil.formataData(data[3], "/"));
    }
     */

    //Este evento executa ao abrir
    //Pagina 1
    private void sincronizaPeriodoAPI(String dataInicial, String dataFinal, String periodo, String situacao, String razaoSocial, String documento, String cheque, String idConta, String especie) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        showHideProgressBar(true);
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityFinanca.this));
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<FinanceiroResumo>> call = apiRotas.getSinteticoPeriodo(dataInicial, dataFinal, periodo, situacao, razaoSocial, documento, cheque, idConta, especie, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);

            call.enqueue(new Callback<List<FinanceiroResumo>>() {
                @Override
                public void onResponse(Call<List<FinanceiroResumo>> call, Response<List<FinanceiroResumo>> response) {
                    if (response.code() == 200) {
                        listaFinanceiro = response.body();
                        setRecyclerView(response.body());
                    }else {
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<FinanceiroResumo>> call, Throwable t) {
                    showHideProgressBar(false);
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor!! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            showHideProgressBar(false);
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }

    //Pagina 2
    private void sincronizaAnaliticoAPI(String dataInicial, String dataFinal, String periodo, String situacao, String razaoSocial,
                                        String documento, String cheque, int idConta, String tipo, String especie) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        showHideProgressBar(true);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<FinanceiroResumo>> call = apiRotas.getAnaliticoPeriodo(dataInicial, dataFinal, periodo, situacao, razaoSocial, documento, cheque, idConta, tipo, especie, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
            call.enqueue(new Callback<List<FinanceiroResumo>>() {
                @Override
                public void onResponse(Call<List<FinanceiroResumo>> call, Response<List<FinanceiroResumo>> response) {
                    if (response.code() == 200) {
                        listaFinanceiro = response.body();
                        setRecyclerView(response.body());
                    }else {
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<FinanceiroResumo>> call, Throwable t) {
                    showHideProgressBar(false);
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            showHideProgressBar(false);
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }

    //Pagina 3
    private void sincronizaAnaliticoAPI(String dataBaixa, String periodo, String situacao, String razaoSocial, String documento, String cheque,int idConta, String tipo, String especie) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        showHideProgressBar(true);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<FinanceiroResumo>> call = apiRotas.getAnaliticoData(dataBaixa, periodo, situacao, razaoSocial, documento, cheque, idConta, tipo, especie, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
            call.enqueue(new Callback<List<FinanceiroResumo>>() {
                @Override
                public void onResponse(Call<List<FinanceiroResumo>> call, Response<List<FinanceiroResumo>> response) {
                    if (response.code() == 200) {
                        listaFinanceiro = response.body();
                        setRecyclerView(listaFinanceiro);
                    } else {
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<FinanceiroResumo>> call, Throwable t) {
                    showHideProgressBar(false);
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            showHideProgressBar(false);
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }

    private void setRecyclerView(List<FinanceiroResumo> lista) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listaFinancaAdapter = new ListaFinancaAdapter(lista, listenerFinanca, pag);
        recyclerView.setAdapter(listaFinancaAdapter);
        listaFinancaAdapter.notifyDataSetChanged();
        showHideProgressBar(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.id_opcao_emissao:
                if ( isChecked ) {
                    periodo = "E";
                    id_opcao_competencia.setChecked(!isChecked);
                    id_opcao_vencimento.setChecked(!isChecked);
                    id_opcao_quitacao.setChecked(!isChecked);
                }
                break;

            case R.id.id_opcao_competencia:
                if ( isChecked ) {
                    periodo = "C";
                    id_opcao_emissao.setChecked(!isChecked);
                    id_opcao_vencimento.setChecked(!isChecked);
                    id_opcao_quitacao.setChecked(!isChecked);
                }
                break;
            case R.id.id_opcao_vencimento:
                if ( isChecked ) {
                    periodo = "V";
                    id_opcao_emissao.setChecked(!isChecked);
                    id_opcao_competencia.setChecked(!isChecked);
                    id_opcao_quitacao.setChecked(!isChecked);
                }
                break;
            case R.id.id_opcao_quitacao:
                if ( isChecked ) {
                    periodo = "Q";
                    id_opcao_emissao.setChecked(!isChecked);
                    id_opcao_competencia.setChecked(!isChecked);
                    id_opcao_vencimento.setChecked(!isChecked);

                    id_opcao_pendentes.setChecked(!isChecked);
                    id_opcao_quitadas.setChecked(!isChecked);
                    id_opcao_vencidas.setChecked(!isChecked);
                    id_opcao_avencer.setChecked(!isChecked);
                }
                break;


            case R.id.id_opcao_pendentes:
                if ( isChecked ) {
                    if (id_opcao_quitacao.isChecked()) {
                        id_opcao_pendentes.setChecked(false);
                        return;
                    }
                    situacao = "P";
                    id_opcao_quitadas.setChecked(!isChecked);
                    id_opcao_vencidas.setChecked(!isChecked);
                    id_opcao_avencer.setChecked(!isChecked);
                }
                break;
            case R.id.id_opcao_quitadas:
                if ( isChecked ) {
                    if (id_opcao_quitacao.isChecked()) {
                        id_opcao_quitadas.setChecked(false);
                        return;
                    }
                    situacao = "Q";
                    id_opcao_pendentes.setChecked(!isChecked);
                    id_opcao_vencidas.setChecked(!isChecked);
                    id_opcao_avencer.setChecked(!isChecked);
                }
                break;
            case R.id.id_opcao_vencidas:
                if ( isChecked ) {
                    if (id_opcao_quitacao.isChecked()) {
                        id_opcao_vencidas.setChecked(false);
                        return;
                    }
                    situacao = "V";
                    id_opcao_pendentes.setChecked(!isChecked);
                    id_opcao_quitadas.setChecked(!isChecked);
                    id_opcao_avencer.setChecked(!isChecked);
                }
                break;
            case R.id.id_opcao_avencer:
                if ( isChecked ) {
                    if (id_opcao_quitacao.isChecked()) {
                        id_opcao_avencer.setChecked(false);
                        return;
                    }
                    situacao = "AV";
                    id_opcao_pendentes.setChecked(!isChecked);
                    id_opcao_quitadas.setChecked(!isChecked);
                    id_opcao_vencidas.setChecked(!isChecked);
                }
                break;
        }
    }
}

