package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.icu.util.GregorianCalendar;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ReceberDAO;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.ReceberCab;
import com.example.rcksuporte05.rcksistemas.util.DatePickerDialogWithMaxMinRange;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Mask;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCobrancaRecebimento extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txvRazaoSocial)
    TextView txvRazaoSocial;

    //@BindView(R.id.edtTotalTitulos)
    //EditText edtTotalTitulos;

    @BindView(R.id.edtTotalPendente)
    EditText edtTotalPendente;

    @BindView(R.id.edtTotalRecebido)
    EditText edtTotalRecebido;


    @BindView(R.id.edtDataReagendamento)
    EditText edtDataReagendamento;

    @BindView(R.id.edtObservacao)
    EditText edtObservacao;

    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    @BindView(R.id.btnCancelar)
    Button btnCancelar;



   /*
    private int intCurrentYear;
    private int intCurrentMonth;
    private int intCurrentDay;
    private int intMaxYear;
    private int intMaxMonth;
    private int intMaxDay;
    private int intMinYear;
    private int intMinDay;
    private int intMinMonth;
    */
    DatePickerDialogWithMaxMinRange datePickerDialog= null;
    DatePickerDialog.OnDateSetListener datePickerOnDateSetListener;
    Calendar myCalendar;

    DBHelper db = new DBHelper(this);
    ReceberDAO receberDAO = new ReceberDAO(db);
    ReceberCab receberCab = new ReceberCab();
    FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
    private Configuracao configuracao = new Configuracao();
    //FinanceiroResumo financeiroResumo = new FinanceiroResumo();
    private String id_web_receber = "";
    String  id_favorecido;
    String nome_favorecido;
    String valor_pendente;
    String total_docs;
    String cpf_cnpj;
    String devedor;
    String position;
    Locale mLocale;
    String id_android;
    private Context context;

    private boolean cliqueAtivo = false;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranca_recebimento);
        ButterKnife.bind(this);
        getConfiguracao();
        try {
            if (configuracao.getGira_tela_cobranca().isEmpty() || configuracao.getGira_tela_cobranca().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        context = this;

        id_android = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
       // edtTotalTitulos.setFocusable(false);
        edtTotalPendente.setFocusable(false);
        edtTotalRecebido.setFocusable(true);
        //edtTotalRecebido.addTextChangedListener( amount()));
        mLocale = new Locale("pt", "BR");
        edtTotalRecebido.addTextChangedListener(new MoneyTextWatcher(edtTotalRecebido, mLocale));
        //edtTotalRecebido.addTextChangedListener(new MoneyTextWatcher(edtTotalRecebido));

        edtTotalRecebido.selectAll();
        try {
            id_favorecido = getIntent().getStringExtra("id_favorecido");
            nome_favorecido = getIntent().getStringExtra("nome_favorecido");
            position = getIntent().getStringExtra("position");
            valor_pendente = getIntent().getStringExtra("valor_pendente");
            total_docs  = getIntent().getStringExtra("total_docs");
            cpf_cnpj    = getIntent().getStringExtra("cpf_cnpj");
            devedor     =  getIntent().getStringExtra("devedor");
            if ( devedor.equalsIgnoreCase("N"))
                edtTotalRecebido.setEnabled(false);
            txvRazaoSocial.setText( nome_favorecido + "\n" + (MascaraUtil.isValidCNPJ( cpf_cnpj ) ?  "CNPJ: " + MascaraUtil.mascaraCNPJ( cpf_cnpj) : "CPF: " + MascaraUtil.mascaraCPF( cpf_cnpj)) );
            edtTotalPendente.setText( MascaraUtil.mascaraReal(Float.parseFloat(valor_pendente)));
            //edtTotalTitulos.setText( total_docs);
            if (!id_favorecido.isEmpty() ) {
                String SQL = "SELECT COUNT(*) AS TOTAL_DOCS, '0.00' AS VALOR_TOTAL, SUM(WRES.VALOR) AS VALOR_RECEBIMENTO " +
                        "FROM TBL_WEB_RECEBER_CAB AS WRES WHERE WRES.ID_CADASTRO = " + id_favorecido;
                //financeiroResumo = financeiroDAO.getTotalAberto(SQL);
                //if (financeiroResumo != null) {
                    if ( Float.parseFloat(valor_pendente) > 0.00f ) {
                        edtTotalPendente.setText( MascaraUtil.mascaraReal(valor_pendente));
                    } else {
                        if ( devedor.equalsIgnoreCase("S")) {
                            Toast.makeText(ActivityCobrancaRecebimento.this, "Cliente não tem valores pendentes!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                //}

                try {
                    receberCab = receberDAO.getLista("SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = " + id_favorecido + " AND ID_ANDROID = '" + id_android + "' AND STATUS = 'N'").get(0);
                    try {
                        id_web_receber = receberCab.getId_web_receber();
                        if (Float.parseFloat(receberCab.getValor()) > 0.00f) {
                            edtTotalRecebido.setText(MascaraUtil.duasCasaDecimal(receberCab.getValor()));
                            valor_pendente = String.valueOf(Float.parseFloat(valor_pendente) + Float.parseFloat(receberCab.getValor()));
                            edtTotalPendente.setText( MascaraUtil.mascaraReal(valor_pendente));
                        }
                    } catch ( NullPointerException | NumberFormatException e){
                        e.printStackTrace();
                    }
                    try {
                        if (receberCab.getData_reagendamento() != null) {
                            edtDataReagendamento.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(receberCab.getData_reagendamento())));
                        }
                    } catch ( NullPointerException| java.text.ParseException e) {
                        e.printStackTrace();
                    }
                } catch ( NullPointerException | CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch ( Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(ActivityCobrancaRecebimento.this, "Atenção!\nCodigo do cliente não localizado!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch ( NumberFormatException e) {
            Toast.makeText( ActivityCobrancaRecebimento.this, "Atenção!\nValor total do(s) titulo(s) é inválido!", Toast.LENGTH_SHORT).show();
            finish();
        } catch ( NullPointerException e) {
            Toast.makeText( ActivityCobrancaRecebimento.this, "Atenção!\nCodigo do cliente não localizado!!", Toast.LENGTH_SHORT).show();
            finish();
        } catch ( Exception e) {
            Toast.makeText( ActivityCobrancaRecebimento.this, "Atenção!\nCodigo do cliente não localizado!!!", Toast.LENGTH_SHORT).show();
            finish();
        }



        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        edtDataReagendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setDate();
                mostraDatePickerDialog(ActivityCobrancaRecebimento.this,  edtDataReagendamento);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cliqueAtivo = true;
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( cliqueAtivo ) {
                    String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    Float valorRecebido = 0.00f;
                    try {
                        if (!edtTotalRecebido.getText().toString().isEmpty()) {
                            valorRecebido = Float.parseFloat(((edtTotalRecebido.getText().toString().substring(2)
                                    .replaceAll("\\s+", "")).replace(".", "")).replace(",", "."));
                        }
                    } catch (NullPointerException | NumberFormatException |
                             IndexOutOfBoundsException e) {
                        valorRecebido = 0.00f;
                    } catch (Exception e) {
                        valorRecebido = 0.00f;
                        e.printStackTrace();
                    }


                    String dataLimiteMax = MascaraUtil.formataData(db.pegaDataAtual("SELECT date('now','localtime', '+30 day')"), "/");
                    String dataLimiteMin = MascaraUtil.formataData(db.pegaDataAtual("SELECT date('now','localtime', '+1 day')"), "/");
                    Date dataReagendamento = null;
                    Date dataLimiteMaximo = null;
                    Date dataLimiteMinimo = null;
                    try {
                        dataReagendamento = new SimpleDateFormat("dd/MM/yyyy").parse(edtDataReagendamento.getText().toString());
                    } catch (java.text.ParseException | NullPointerException e) {
                        e.printStackTrace();
                    }
                    try {
                        dataLimiteMaximo = new SimpleDateFormat("dd/MM/yyyy").parse(dataLimiteMax);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        dataLimiteMinimo = new SimpleDateFormat("dd/MM/yyyy").parse(dataLimiteMin);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        //if (valorRecebido >= 0.00f || !edtDataReagendamento.getText().toString().replaceAll("/","").trim().isEmpty()) {
                        if (valorRecebido >= 0.01f || dataReagendamento != null) {
                            String x = "//";

                            x = edtDataReagendamento.getText().toString().replaceAll("/", "").trim();
                            x = "//";
                            if (valorRecebido > Float.parseFloat(valor_pendente)) {
                                cliqueAtivo = !cliqueAtivo;
                                Toast.makeText(ActivityCobrancaRecebimento.this, "Valor do recebimento é maior que o saldo devedor!", Toast.LENGTH_SHORT).show();
                                return;

                            }
                            if (valorRecebido == Float.parseFloat(valor_pendente)) {

                                if (!edtDataReagendamento.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                                    if (devedor.equalsIgnoreCase("S")) {
                                        cliqueAtivo = !cliqueAtivo;
                                        Toast.makeText(ActivityCobrancaRecebimento.this, "Valor do recebimento é igual ao valor do débito atual\n Reagendamento do titulo não poder ser executado!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }

                            }
                            if (!edtDataReagendamento.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                                if (dataReagendamento.before(dataLimiteMinimo)) {
                                    cliqueAtivo = !cliqueAtivo;
                                    Toast.makeText(ActivityCobrancaRecebimento.this, "Data do reagendamento não pode ser menor/igual a data atual!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (dataReagendamento.after(dataLimiteMaximo)) {
                                    cliqueAtivo = !cliqueAtivo;
                                    Toast.makeText(ActivityCobrancaRecebimento.this, "Data do reagendamento não pode ser superior a 30 dias!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }


                            String dataHoraAtual = db.pegaDataHoraAtual();
                            receberCab = new ReceberCab();
                            receberCab.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                            receberCab.setId_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());
                            receberCab.setId_moeda("1");
                            receberCab.setStatus("N");
                            receberCab.setExcluido("N");
                            receberCab.setValor(String.valueOf(valorRecebido));
                            receberCab.setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
                            receberCab.setUsuario_nome(UsuarioHelper.getUsuario().getNome_usuario());
                            receberCab.setUsuario_data(db.pegaDataHoraAtual());
                            receberCab.setId_cadastro(id_favorecido);

                            receberCab.setData_vencimento(db.pegaDataAtual());
                            receberCab.setId_android(id_android);
                            receberCab.setChave_receber_app(String.format("%03d", Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor())) + "90000009" + String.format("%08d", Integer.parseInt(receberCab.getId_cadastro())) + dataHoraAtual);
                            //webPedido.setId_pedido_app(String.format("%03d", Integer.parseInt(webPedido.getId_vendedor().trim())) + "90000009" + String.format("%08d", webPedido.getCadastro().getId_cadastro()) + dataHoraAtual);
                            try {
                                receberCab.setObservacoes(edtObservacao.getText().toString().toUpperCase());
                            } catch (NullPointerException e) {

                            }
                            try {
                                receberCab.setData_reagendamento(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(edtDataReagendamento.getText().toString())));
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            if (id_web_receber.isEmpty()) {


                                try {
                                    receberCab = receberDAO.getLista("SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = " + id_favorecido + " AND ID_ANDROID = '" + id_android + "' AND STATUS = 'N'").get(0);
                                    try {
                                        id_web_receber = receberCab.getId_web_receber();
                                        if (Float.parseFloat(receberCab.getValor()) > 0.00f) {
                                            edtTotalRecebido.setText(MascaraUtil.duasCasaDecimal(receberCab.getValor()));
                                            valor_pendente = String.valueOf(Float.parseFloat(valor_pendente) + Float.parseFloat(receberCab.getValor()) );
                                            edtTotalPendente.setText( MascaraUtil.mascaraReal(valor_pendente));
                                        }
                                    } catch ( NullPointerException | NumberFormatException e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (receberCab.getData_reagendamento() != null) {
                                            edtDataReagendamento.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(receberCab.getData_reagendamento())));
                                        }
                                    } catch ( NullPointerException| java.text.ParseException e) {
                                        e.printStackTrace();
                                    }
                                } catch ( NullPointerException | CursorIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                } catch ( Exception e) {
                                    e.printStackTrace();
                                }
                                float valor_debito_atual = getValorDebitoAtual(id_favorecido);
                                float valor_pagto_atual  = getValorPagamentoTotal(id_favorecido);

                                if ( valor_pagto_atual  >= valor_debito_atual ) {
                                    showMsgAlerta("Atenção!!\nSem valor pendente para recebimento!");
                                    return;
                                } else {
                                    if ( (valor_pagto_atual + valorRecebido) > valor_debito_atual ) {
                                        showMsgAlerta("Atenção!!\nValor maior que o saldo devedor atual!");
                                        return;
                                    }
                                }

                                if (receberDAO.add(receberCab) > 0) {
                                    Toast.makeText(ActivityCobrancaRecebimento.this, "Registro gravado com sucesso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("result", true);
                                    intent.putExtra("position", position);
                                    intent.putExtra("valor_recebido", String.valueOf(valorRecebido));
                                    intent.putExtra("data_reagendamento", receberCab.getData_reagendamento());
                                    intent.putExtra("acao", "0");//add
                                    setResult(RESULT_OK, intent);
                                    cliqueAtivo = !cliqueAtivo;
                                    finish();
                                } else {
                                    showMsgAlerta("Atenção!!\nFalha ao gravar o valor do recebimento/agendamento, favor tentar novamente, caso o problema persista favor informar ao suporte tecnico!");
                                }
                            } else {
                                if (receberDAO.update(receberCab, id_web_receber, id_android) > 0) {
                                    Toast.makeText(ActivityCobrancaRecebimento.this, "Registro atualizado com sucesso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("result", true);
                                    intent.putExtra("position", position);
                                    intent.putExtra("valor_recebido", String.valueOf(valorRecebido));
                                    intent.putExtra("data_reagendamento", receberCab.getData_reagendamento());
                                    intent.putExtra("acao", "1");//update
                                    setResult(RESULT_OK, intent);
                                    cliqueAtivo = !cliqueAtivo;
                                    finish();
                                } else {
                                    showMsgAlerta("Atenção!!\nFalha ao atualizar o valor do recebimento/agendamento, favor tentar novamente, caso o problema persista favor informar ao suporte tecnico!");
                                }
                            }

                        } else {
                            Toast.makeText(ActivityCobrancaRecebimento.this, "Valor é inválido!\n", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException | NumberFormatException e) {
                        Toast.makeText(ActivityCobrancaRecebimento.this, "Valor é inválido!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(ActivityCobrancaRecebimento.this, "Valor é inválido!!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                if ( !cliqueAtivo) {
                    cliqueAtivo = true;
                }
            }

        });
    }
    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            edtTotalRecebido.addTextChangedListener(new MoneyTextWatcher(edtTotalRecebido, mLocale));
        //if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        // }


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

    private float getValorDebitoAtual(String id_favorecido) {
        String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  MAX(FRES.DATA_EMISSAO) AS ULT_COMPRA, " +
                "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                "( SELECT MAX(USUARIO_DATA) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                "( SELECT MAX(DATA_REAGENDAMENTO) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                "WHERE FRES.ID_FAVORECIDO = " + id_favorecido+ " ";
        SQL += "AND FRES.DEVEDOR = 'S'  GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
        List<FinanceiroResumo> lista = new ArrayList<>();
        lista = financeiroDAO.getLista(SQL, false, false);
        if ( lista.size() > 0) {
            try {
                return Float.parseFloat(lista.get(0).getValor_debito());
            } catch ( NumberFormatException|NullPointerException e) {
                e.printStackTrace();
            }
        }
        return  0.00f;
        //listaFinanceiro.set(position, lista.get(0));
    }

    private float getValorPagamentoTotal(String id_favorecido) {
        String SQL = "SELECT SUM(VALOR) AS VALOR_PAGO FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = " + id_favorecido;
        Cursor cursor = db.listaDados("SELECT SUM(VALOR) AS VALOR_PAGO FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = " + id_favorecido);
        if ( cursor.moveToNext()) {
            return  cursor.getFloat(cursor.getColumnIndex("VALOR_PAGO"));
        }
        //        select sum(valor) as valor_pago from tbl_web_receber_cab where id_cadastro = 8500
        return  0.00f;
    }

    public void mostraDatePickerDialog(Context context, final EditText campoTexto) {
        final Calendar calendar;
        //Prepara data anterior caso ja tenha sido selecionada
        calendar = campoTexto.getTag() != null ? ((Calendar) campoTexto.getTag()) : Calendar.getInstance();
        //if (campoTexto.getTag() != null)
        //calendar = ((Calendar) campoTexto.getTag());
        //else
        // calendar = Calendar.getInstance();

        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                campoTexto.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
                campoTexto.setTag(newDate);
                //PedidoHelper.editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = ActivityCobrancaRecebimento.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityCobrancaRecebimento.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ActivityCobrancaRecebimento.this);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }

    public class NumberTextWatcher implements TextWatcher {

        private final DecimalFormat df;
        private final DecimalFormat dfnd;
        private final EditText et;
        private boolean hasFractionalPart;
        private int trailingZeroCount;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public NumberTextWatcher(EditText editText, String pattern) {
            df = new DecimalFormat(pattern);
            df.setDecimalSeparatorAlwaysShown(true);
            dfnd = new DecimalFormat("####.00");
            this.et = editText;
            hasFractionalPart = false;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void afterTextChanged(Editable s) {
            et.removeTextChangedListener(this);

            if (s != null && !s.toString().isEmpty()) {
                try {
                    int inilen, endlen;
                    inilen = et.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "").replace("","");
                    Number n = null;
                    try {
                        n = df.parse(v);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    int cp = et.getSelectionStart();
                    if (hasFractionalPart) {
                        StringBuilder trailingZeros = new StringBuilder();
                        while (trailingZeroCount-- > 0)
                            trailingZeros.append('0');
                        et.setText(df.format(n) + trailingZeros.toString());
                    } else {
                        et.setText(dfnd.format(n));
                    }
                    et.setText("".concat(et.getText().toString()));
                    endlen = et.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel < et.getText().length()) {
                        et.setSelection(sel);
                    } else if (trailingZeroCount > -1) {
                        et.setSelection(et.getText().length() - 3);
                    } else {
                        et.setSelection(et.getText().length());
                    }
                } catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();
                }
            }

            et.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int index = s.toString().indexOf(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));
            trailingZeroCount = 0;
            if (index > -1) {
                for (index++; index < s.length(); index++) {
                    if (s.charAt(index) == '0')
                        trailingZeroCount++;
                    else {
                        trailingZeroCount = 0;
                    }
                }
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }
        }
    }

    public class MoneyTextWatcher implements TextWatcher {
        private final WeakReference<EditText> editTextWeakReference;
        private final Locale locale;

        public MoneyTextWatcher(EditText editText, Locale locale) {
            this.editTextWeakReference = new WeakReference<EditText>(editText);
            this.locale = locale != null ? locale : Locale.getDefault();
        }

        public MoneyTextWatcher(EditText editText) {
            this.editTextWeakReference = new WeakReference<EditText>(editText);
            this.locale = Locale.getDefault();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void afterTextChanged(Editable editable) {
            EditText editText = editTextWeakReference.get();
            if (editText == null) return;
            editText.removeTextChangedListener(this);

            BigDecimal parsed = parseToBigDecimal(editable.toString(), locale);
            String formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);
            // NumberFormat.getNumberInstance(locale).format(parsed); // sem o simbolo de moeda

            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private BigDecimal parseToBigDecimal(String value, Locale locale) {
            String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());

            String cleanString = value.replaceAll(replaceable, "");
            try {
                return new BigDecimal(cleanString).setScale(
                        2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
                );
            } catch ( NullPointerException|NumberFormatException e) {
                return  new BigDecimal(0.00);
            }
        }
    }

    /*
    public void setDate() {


        datePickerOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                edtDataReagendamento.setText(new StringBuilder().append(year)
                        .append("-").append(monthOfYear + 1).append("-")
                        .append(dayOfMonth));
            }
        };


        myCalendar = Calendar.getInstance();

        intCurrentYear = myCalendar.get(Calendar.YEAR);
        intCurrentMonth = myCalendar.get(Calendar.MONTH);
        intCurrentDay = myCalendar.get(Calendar.DAY_OF_MONTH);

        intMaxYear =  intCurrentYear ;
        intMaxMonth = intCurrentMonth ;
        intMaxDay =  intCurrentDay + 30;

        intMinYear =  intCurrentYear;
        intMinMonth = intCurrentMonth;
        intMinDay =  intCurrentDay;

        datePickerDialog = new DatePickerDialogWithMaxMinRange(
                context, datePickerOnDateSetListener,intMinYear,intMinMonth,intMinDay,intMaxYear,intMaxMonth,intMaxDay);
        datePickerDialog.show();
    }

     */
}