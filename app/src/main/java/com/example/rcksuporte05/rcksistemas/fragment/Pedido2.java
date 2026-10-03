package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.icu.math.BigDecimal;
import android.icu.text.NumberFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CondicoesPagamentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.LogSistemaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MoedaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.OperacaoEstoqueDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityAnexoCadastro;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoAnexo;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoPendente;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.LogSistema;
import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Pedido2 extends Fragment {

    private static Cliente objetoCliente = null;
    @BindView(R.id.spPagamento)
    Spinner spPagamento;

    @BindView(R.id.spOperacao)
    Spinner spOperacao;

    @BindView(R.id.lytMoeda)
    LinearLayout lytMoeda;
    @BindView(R.id.spMoeda)
    Spinner spMoeda;

    @BindView(R.id.txtDataEmissao)
    TextView edtDataEmissao;

    @BindView(R.id.txtDaDataDeEmissao)
    TextView txtDataEmissao;

    @BindView(R.id.txtDataEntrega)
    TextView txtDataEntrega;

    @BindView(R.id.edtDataEntrega)
    EditText edtDataEntrega;

    @BindView(R.id.edtValorRecibo)
    EditText edtValorRecibo;

    @BindView(R.id.edtSaldoDevedor)
    EditText edtSaldoDevedor;

    @BindView(R.id.edtObservacao)
    EditText edtObservacao;

    @BindView(R.id.btnSalvarPedido)
    Button btnSalvarPedido;

    @BindView(R.id.btnPedidoAnexo)
    Button btnPedidoAnexo;


    @BindView(R.id.lyPrazoPagamento)
    LinearLayout lyPrazoPagamento;

    @BindView(R.id.lyOperacao)
    LinearLayout lyOperacao;

    @BindView(R.id.lySpinner)
    LinearLayout lySpinner;

    public Boolean control = false;
    private ArrayAdapter<Operacao> adapterOperacao;
    private ArrayAdapter<CondicoesPagamento> adapterPagamento;
    private ArrayAdapter<Moeda> adapterMoeda;
    private DBHelper db = new DBHelper( PedidoHelper.getActivityPedidoMain());
    private WebPedidoDAO webPedidoDAO;
    private WebPedido webPedido = new WebPedido();
    private EmpresaParametroDAO empresaParametroDAO  = new EmpresaParametroDAO(db);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private Configuracao configuracao;
    private Bundle bundle;
    private PedidoHelper pedidoHelper;
    private Context context;
    private String idCondPag = "";

    private ProgressDialog progress;
    //String position;
    Locale mLocale;
    private int tExtraFonte  = 0;
    private String idOperacaoAnterior = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido2_2025, container, false);
        ButterKnife.bind(this, view);
        context = this.getContext();
        ViewGroup.LayoutParams params = lytMoeda.getLayoutParams();
        params.height = 0;//ViewGroup.LayoutParams.WRAP_CONTENT;

        webPedidoDAO = new WebPedidoDAO(db);
        try {
            configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            edtDataEmissao.setText(db.pegaDataAtual());
        } catch ( NullPointerException e) {
            e.printStackTrace();
        }
        pedidoHelper = new PedidoHelper(this);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        edtSaldoDevedor.setFocusable(false);

        bundle = getArguments();
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
            if ( tExtraFonte > 0) {
                edtObservacao.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
            }
        } catch (NullPointerException ignored) {
        }
        try {
            float limiteCredito  = 0.00f;
            float limitUtilizado = 0.00f;
            CondicoesPagamentoDAO condicoesPagamentoCabDAO = new CondicoesPagamentoDAO(db);
            OperacaoEstoqueDAO operacaoEstoqueDAO = new OperacaoEstoqueDAO(db);
            List<CondicoesPagamento> listaCondicoesPagamentos = new ArrayList<>();
            String sql;
            try {
                if ( PedidoHelper.getWebPedido().getCadastro() != null) {
                    sql = "SELECT CP.* FROM TBL_CONDICOES_PAG_CAB AS CP INNER JOIN TBL_CADASTRO_CONDICOES_PAG AS CCP ON ( CCP.ID_CONDICAO = CP.ID_CONDICAO AND CCP.ID_CADASTRO = ";
                    sql += PedidoHelper.getWebPedido().getCadastro().getId_cadastro_servidor() + ") WHERE CP.ATIVO = 'S' AND  ( CP.NOME_CONDICAO != '' AND CP.NOME_CONDICAO IS NOT NULL) ORDER BY CP.ID_CONDICAO";
                    if ( configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                        try {
                            limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                            limitUtilizado = Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencido()) + Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencendo());

                        } catch (NullPointerException|NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if ((limiteCredito - limitUtilizado) < 0.00f) {
                            sql = "SELECT CP.* FROM TBL_CONDICOES_PAG_CAB AS CP INNER JOIN TBL_CADASTRO_CONDICOES_PAG AS CCP ON ( CCP.ID_CONDICAO = CP.ID_CONDICAO AND CCP.ID_CADASTRO = ";
                            sql += PedidoHelper.getWebPedido().getCadastro().getId_cadastro_servidor() + ") WHERE CP.ATIVO = 'S' AND  ( CP.NOME_CONDICAO != '' AND CP.NOME_CONDICAO IS NOT NULL) AND CP.TIPO_CONDICAO = '1' ORDER BY CP.ID_CONDICAO";
                        }
                    }
                    listaCondicoesPagamentos = condicoesPagamentoCabDAO.getLista(sql);
                }
            } catch ( NullPointerException|CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                if (listaCondicoesPagamentos.size() <= 0) {
                    sql = "SELECT * FROM TBL_CONDICOES_PAG_CAB  WHERE ATIVO = 'S' AND  (NOME_CONDICAO != '' AND NOME_CONDICAO IS NOT NULL) ORDER BY ID_CONDICAO;";
                    if (configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                        try {
                            limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                            limitUtilizado = Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencido()) + Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencendo());
                        } catch ( NullPointerException|NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if ((limiteCredito - limitUtilizado) < 0.00f) {
                            sql = "SELECT * FROM TBL_CONDICOES_PAG_CAB  WHERE ATIVO = 'S' AND  (NOME_CONDICAO != '' AND NOME_CONDICAO IS NOT NULL) AND TIPO_CONDICAO = '1' ORDER BY ID_CONDICAO;";
                        }

                    }
                    listaCondicoesPagamentos = condicoesPagamentoCabDAO.getLista(sql);
                }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
            adapterPagamento = new ArrayAdapter<>(getContext(),
                    tExtraFonte == 0 ?  R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g,
                    listaCondicoesPagamentos);
            WebPedido webPedidox = PedidoHelper.getWebPedido();
            spPagamento.setAdapter(adapterPagamento);
            try {
                if (!configuracao.getId_con_pgto_default_app().equalsIgnoreCase("0")) {
                    for (int i = 0; i < listaCondicoesPagamentos.size(); i++)
                        if ( PedidoHelper.getWebPedido().getId_condicao_pagamento() == null || PedidoHelper.getWebPedido().getId_condicao_pagamento().isEmpty()) {
                            if (listaCondicoesPagamentos.get(i).getId_condicao().equalsIgnoreCase(configuracao.getId_con_pgto_default_app())) {
                                spPagamento.setSelection(i);
                                break;
                            }
                        } else {
                            //for ( int i = 0; i < listaCondicoesPagamentos.size(); i++) {
                            if ( listaCondicoesPagamentos.get(i).getId_condicao().equalsIgnoreCase(PedidoHelper.getWebPedido().getId_condicao_pagamento())) {
                                spPagamento.setSelection(i);
                                break;
                            }

                            //}
                        }
                } else {
                    for (int i = 0; i < listaCondicoesPagamentos.size(); i++)
                        if ( PedidoHelper.getWebPedido().getId_condicao_pagamento() == null || PedidoHelper.getWebPedido().getId_condicao_pagamento().isEmpty()) {
                            if (listaCondicoesPagamentos.get(i).getId_condicao().equalsIgnoreCase(configuracao.getId_con_pgto_default_app())) {
                                spPagamento.setSelection(i);
                                break;
                            }
                        } else {
                            //for ( int i = 0; i < listaCondicoesPagamentos.size(); i++) {
                            if ( listaCondicoesPagamentos.get(i).getId_condicao().equalsIgnoreCase(PedidoHelper.getWebPedido().getId_condicao_pagamento())) {
                                spPagamento.setSelection(i);
                                break;
                            }

                            //}
                        }
                    //spPagamento.setSelection(0);
                }
            } catch ( NullPointerException e) {
                try {
                    if ( PedidoHelper.getWebPedido().getId_condicao_pagamento() !=null || !PedidoHelper.getWebPedido().getId_condicao_pagamento().isEmpty()) {
                        for ( int i = 0; i < listaCondicoesPagamentos.size(); i++) {
                            if ( listaCondicoesPagamentos.get(i).getId_condicao().equalsIgnoreCase(PedidoHelper.getWebPedido().getId_condicao_pagamento())) {
                                spPagamento.setSelection(i);
                                break;
                            }

                        }
                    }
                } catch ( NullPointerException ex) {
                    spPagamento.setSelection(0);
                }

            }
            //idCondPag = adapterPagamento.getItem(0).getId_condicao();

            adapterOperacao = new ArrayAdapter<>(getContext(),
                    /*android.R.layout.simple_list_item_activated_1*/
                    tExtraFonte == 0 ?  R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g,
                    operacaoEstoqueDAO.getLista("SELECT * FROM TBL_OPERACAO_ESTOQUE;"));
            spOperacao.setAdapter(adapterOperacao);

            try {
                MoedaDAO moedaDAO = new MoedaDAO(db);
                List<Moeda> lista = moedaDAO.getLista("SELECT * FROM TBL_MOEDA;");
                adapterMoeda = new ArrayAdapter<>(getContext(),
                        /*android.R.layout.simple_list_item_activated_1*/
                        tExtraFonte == 0 ?  R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g, lista);
                spMoeda.setAdapter(adapterMoeda);
                if (lista.size() <= 0) {
                    lytMoeda.setVisibility(View.INVISIBLE);
                    spMoeda.setVisibility(View.INVISIBLE);
                    lytMoeda.setLayoutParams(params);

                } else {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    lytMoeda.setLayoutParams(params);
                }
            } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
                lytMoeda.setVisibility(View.INVISIBLE);
                spMoeda.setVisibility(View.INVISIBLE);
                lytMoeda.setLayoutParams(params);
            } catch ( Exception ex) {
                lytMoeda.setVisibility(View.INVISIBLE);
                spMoeda.setVisibility(View.INVISIBLE);
                lytMoeda.setLayoutParams(params);
            }

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if (PedidoHelper.getIdPedido() > 0) {
            webPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido()).get(0);
            objetoCliente = webPedido.getCadastro();
            idCondPag = webPedido.getId_condicao_pagamento();
            idOperacaoAnterior = webPedido.getId_operacao();

            //Seleciona Condição de pagamento correta dentro do Spinner spPagamento
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_condicao_pagamento().equals(adapterPagamento.getItem(i).getId_condicao()));
                spPagamento.setSelection(i);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Seleciona operação correta dentro do Spinner spOperacao
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_operacao().equals(adapterOperacao.getItem(i).getId_operacao()));
                spOperacao.setSelection(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Seleciona operação correta dentro do Spinner spMoeda
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_moeda_padrao().equals(adapterMoeda.getItem(i).getId_moeda()));
                spMoeda.setSelection(i);
            } catch ( NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            edtObservacao.setText(webPedido.getObservacoes());
            try {
                edtValorRecibo.setText( MascaraUtil.duasCasaDecimal(webPedido.getValor_recibo()));
            } catch ( NullPointerException|NumberFormatException e) {
                edtValorRecibo.setText(MascaraUtil.mascaraReal("0.00"));
            }
            try {
                edtSaldoDevedor.setText( MascaraUtil.mascaraReal(Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencido())
                        + Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencendo()) )) ;
            } catch ( NullPointerException|NumberFormatException e) {
                edtSaldoDevedor.setText(MascaraUtil.mascaraReal("0.00"));
            }
            try {
                edtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_emissao())));
                edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!configuracao.getLayout_venda_erp().equalsIgnoreCase("25")) {
                    txtDataEmissao.setText("DATA DE EMISSÃO");
                    txtDataEntrega.setText("DATA DE FATURAMENTO");
                    hideEditValorRecibo();
                    showEditDataEntrega();
                } else {
                    txtDataEmissao.setText("SALDO DEVEDOR");
                    txtDataEntrega.setText("VALOR RECIBO");
                    hideEditDataEntrega();
                    showEditValorRecibo();
                }
            } catch (NullPointerException e) {
                txtDataEmissao.setText("DATA DE EMISSÃO");
                txtDataEntrega.setText("DATA DE FATURAMENTO");
                hideEditValorRecibo();
                showEditDataEntrega();
            }
        } else if (PedidoHelper.getWebPedido() != null) {
            webPedido = PedidoHelper.getWebPedido();
            objetoCliente = webPedido.getCadastro();
            //Seleciona Condição de pagamento correta dentro do Spinner spPagamento
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_condicao_pagamento().equals(adapterPagamento.getItem(i).getId_condicao()));
                spPagamento.setSelection(i);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Seleciona operação correta dentro do Spinner spOperacao
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_operacao().equals(adapterOperacao.getItem(i).getId_operacao()));
                spOperacao.setSelection(i);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Seleciona operação correta dentro do Spinner spMoeda
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_moeda_padrao().equals(adapterMoeda.getItem(i).getId_moeda()));
                spMoeda.setSelection(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            edtObservacao.setText(webPedido.getObservacoes());
            try {
                edtValorRecibo.setText( MascaraUtil.duasCasaDecimal(webPedido.getValor_recibo()));
            } catch ( NullPointerException|NumberFormatException e) {
                edtValorRecibo.setText(MascaraUtil.duasCasaDecimal("0.00"));
            }
            try {
                edtSaldoDevedor.setText(MascaraUtil.mascaraReal(Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencido())
                        + Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencendo()) ));
            } catch ( NullPointerException|NumberFormatException e) {
                edtSaldoDevedor.setText(MascaraUtil.mascaraReal("0.00"));
            }
            try {
                edtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_emissao())));
                if ( !configuracao.getLayout_venda_erp().equalsIgnoreCase("25") ) {
                    txtDataEmissao.setText("DATA DE EMISSÃO");
                    txtDataEntrega.setText("DATA DE FATURAMENTO");
                    edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega())));
                    hideEditValorRecibo();
                    showEditDataEntrega();
                } else {
                    txtDataEmissao.setText("SALDO DEVEDOR");
                    txtDataEntrega.setText("VALOR RECIBO ");
                    edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(db.pegaDataAtual())));
                    hideEditDataEntrega();
                    showEditValorRecibo();
                }
            } catch (Exception e) {
                if ( !configuracao.getLayout_venda_erp().equalsIgnoreCase("25") ) {
                    txtDataEmissao.setText("DATA DE EMISSÃO");
                    txtDataEntrega.setText("DATA DE FATURAMENTO");
                    hideEditValorRecibo();
                    showEditDataEntrega();
                } else{
                    txtDataEmissao.setText("SALDO DEVEDOR");
                    txtDataEntrega.setText("VALOR RECIBO ");
                    hideEditDataEntrega();
                    showEditValorRecibo();
                    try {
                        edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(db.pegaDataAtual())));
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }
                try {
                    edtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy")
                            .format(new SimpleDateFormat("yyyy-MM-dd")
                                    .parse(db.pegaDataAtual())));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                edtValorRecibo.setText( MascaraUtil.duasCasaDecimal(webPedido.getValor_recibo()));
            } catch ( NullPointerException|NumberFormatException e) {
                edtValorRecibo.setText(MascaraUtil.duasCasaDecimal("0.00"));
            }
            try {
                edtSaldoDevedor.setText(MascaraUtil.mascaraReal(Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencido())
                        + Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencendo()) ));
            } catch ( NullPointerException|NumberFormatException e) {
                edtSaldoDevedor.setText(MascaraUtil.mascaraReal("0.00"));
            }
            try {
                if (!configuracao.getLayout_venda_erp().equalsIgnoreCase("25")) {
                    txtDataEmissao.setText("DATA DE EMISSÃO");
                    txtDataEntrega.setText("DATA DE FATURAMENTO");
                    hideEditValorRecibo();
                    showEditDataEntrega();
                } else {
                    txtDataEmissao.setText("SALDO DEVEDOR");
                    txtDataEntrega.setText("VALOR RECIBO ");
                    hideEditDataEntrega();
                    showEditValorRecibo();
                    try {
                        edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(db.pegaDataAtual())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch ( NullPointerException e) {
                txtDataEmissao.setText("DATA DE EMISSÃO");
                txtDataEntrega.setText("DATA DE FATURAMENTO");
                hideEditValorRecibo();
                showEditDataEntrega();
            }
            try {
                edtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy")
                        .format(new SimpleDateFormat("yyyy-MM-dd")
                                .parse(db.pegaDataAtual())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        /* AUTO-RECOLHIMENTO DO lySpinner EM SMARTPHONES QUANDO O TECLADO VIRTUAL É EXIBIDO (DESATIVADO TEMPORARIAMENTE)
        try {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {
                private boolean tecladoAbertoAnterior = false;

                @Override
                public void onGlobalLayout() {
                    boolean isTablet = (getResources().getConfiguration().screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) >= android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
                    if (isTablet || lySpinner == null) return;

                    android.graphics.Rect r = new android.graphics.Rect();
                    view.getWindowVisibleDisplayFrame(r);
                    int screenHeight = view.getRootView().getHeight();
                    int heightDiff = screenHeight - (r.bottom - r.top);

                    boolean tecladoAberto = heightDiff > screenHeight * 0.15; // Teclado ocupa mais de 15% da tela

                    if (tecladoAberto != tecladoAbertoAnterior) {
                        tecladoAbertoAnterior = tecladoAberto;
                        if (tecladoAberto) {
                            if (lySpinner.getVisibility() == View.VISIBLE) {
                                lySpinner.setVisibility(View.GONE);
                            }
                        } else {
                            if (lySpinner.getVisibility() == View.GONE) {
                                lySpinner.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        btnSalvarPedido.setOnClickListener(v -> {
            if (bundle.getInt("vizualizacao") == 1) {
                pedidoHelper.limparDados();
                objetoCliente = null;
                HistoricoFinanceiroHelper.limparDados();
                PedidoHelper.getActivityPedidoMain().finish();
                return;
            }
            ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
            Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            try {
                if (configuracao.getTrabalha_com_confirmacao_prazo().equalsIgnoreCase("S"))
                    if ( PedidoHelper.getWebPedido().getPrazo_confirmado().equalsIgnoreCase("N") || PedidoHelper.getWebPedido().getPrazo_confirmado().isEmpty() ) {
                        DialogHelper.showSnackInfo(getView(), "Obrigátoria a confirmação do prazo/condição de pagamento, favor clicar na lista de prazo(s)/condição de pagamento e efetivar a confirmação!!", R.drawable.ic_alerta_mensagem);
                        //Toast.makeText(context, "Obrigátoria a confirmação do prazo/condição de pagamento, favor clicar na lista de prazo(s)/condição de pagamento e efetivar a confirmação!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
            try {
                if (configuracao.getLayout_venda_erp().equalsIgnoreCase("25")) {
                    webPedido.setData_prev_entrega(db.pegaDataAtual());
                } else{
                    if ( edtDataEntrega.getText().toString() == null || edtDataEntrega.getText().toString() .isEmpty() || edtDataEntrega.getText().toString().replace("/", "").replace(" ", "").isEmpty()) {
                        DialogHelper.showSnackInfo(getView(), "Obrigatório informar a data de faturamento/entrega do pedido!", R.drawable.ic_alerta_mensagem);
                        //Toast.makeText( context, "Obrigatório informar a data de faturamento/entrega do pedido!", Toast.LENGTH_SHORT).show();
                        edtDataEntrega.setTextColor(Color.RED);
                        edtDataEntrega.setFocusable(true);
                        return;
                    }
                }
            } catch ( NullPointerException e){
                webPedido.setData_prev_entrega(db.pegaDataAtual());
            }
            try {
                if ( webPedido.getCadastro() == null) {
                    DialogHelper.showSnackInfo(getView(), "Informe um cliente antes de salvar o pedido!", R.drawable.ic_alerta_mensagem);
                    //Toast.makeText( context, "Informe um cliente antes de salvar o pedido!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch ( NullPointerException e){
                e.printStackTrace();
            }

            // AJUSTE DE SEGURANÇA EM 22/05/2024 - VERIFICA SE A ABA DE PRODUTOS (PEDIDO1) AINDA ESTÁ NA MEMÓRIA
            if (!PedidoHelper.isPedido1Valido()) {
                showFeedbackDialog("A sessão de venda expirou devido à inatividade prolongada. Por favor, retorne à lista de pedidos e abra este pedido novamente para atualizar os dados com segurança.", false, () -> {
                    pedidoHelper.limparDados();
                    objetoCliente = null;
                    HistoricoFinanceiroHelper.limparDados();
                    PedidoHelper.getActivityPedidoMain().finish();
                });
                return;
            }

            ProgressDialog dialog = new ProgressDialog(PedidoHelper.getActivityPedidoMain());
            dialog.setTitle("Atenção!");
            dialog.setMessage("Salvando o Pedido");
            dialog.setCancelable(false);
            dialog.show();
            if (pedidoHelper.salvaPedido()) {
                dialog.setMessage("Aguarde...Efetuando analise no pedido");
                if ( pedidoHelper.getListaWebPedidoItensSalvo().size() == db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + PedidoHelper.getIdPedido() )){
                    try {
                        dialog.dismiss();
                    } catch ( NullPointerException e){
                    } catch ( Exception e) {
                    }
                    //showMsgSucesso("Pedido de " + webPedido.getCadastro().getNome_cadastro() + "\nsalvo com sucesso!");
                    webPedido.setFinalizado("S");

                    // GRAVAÇÃO DO LOG DE SUCESSO NO SALVAMENTO
                    try {
                        LogSistema log = new LogSistema();
                        log.setIdPedido(webPedido.getId_web_pedido());
                        log.setIdPedidoApp(webPedido.getId_pedido_app());
                        log.setIdWebPedidoServidor(webPedido.getId_web_pedido_servidor());
                        log.setIdVendedor(webPedido.getId_vendedor());
                        log.setTipoLog("AUDITORIA");
                        log.setEvento("SALVAR_PEDIDO_SUCESSO");
                        log.setClasse("Pedido2");
                        log.setMetodo("btnSalvarPedido.onClick");
                        log.setValorNovo("TOTAL: " + webPedido.getValor_total() + " | ITENS: " + webPedido.getQtde_itens());
                        new LogSistemaDAO(db).add(log);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    showActionDialog(configuracao.getCnpj_empresa(), webPedido);
                } else {
                    try {
                        dialog.dismiss();
                    } catch ( NullPointerException e){
                    } catch ( Exception e) {
                    }
                    showFeedbackDialog("Pedido salvo com divergencias de produtos e valores!! Favor verificar o mesmo e confirmar as informações gravadas!\nEm caso de duvidas entre em contato com o suporte técnico", true);
                }
            } else {
                dialog.dismiss();
                //showMsgAlerta("Favor confirmar as informações gravadas!\nEm caso de duvidas entre em contato com o suporte técnico");
            }
        });

        btnPedidoAnexo.setOnClickListener(v -> {
            //ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
            //Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            Intent intent = new Intent(getActivity(), ActivityPedidoAnexo.class);
            startActivity(intent.putExtra("visualizacao", bundle.getInt("vizualizacao")));
        });


        if (bundle.getInt("vizualizacao") == 1) {
            // btnSalvarPedido.setVisibility(View.INVISIBLE);
            //btnSalvarPedido.setEnabled(false);
            //btnSalvarPedido.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparente50)));
            btnSalvarPedido.setText("FECHAR");


            edtObservacao.setFocusable(false);
            spPagamento.setEnabled(false);
            edtDataEntrega.setFocusable(false);
            edtValorRecibo.setFocusable(false);
            spOperacao.setEnabled(false);
            spMoeda.setEnabled(false);
            btnPedidoAnexo.setText("VISUALIZAR ANEXO");

        } else {
            edtDataEntrega.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraDatePickerDialog(PedidoHelper.getActivityPedidoMain(), edtDataEntrega);
                }
            });
        }

        spOperacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!PedidoHelper.isPedido1Valido()) {
                    return;
                }
                String idNova = adapterOperacao.getItem(i).getId_operacao();
                if (!idOperacaoAnterior.equals(idNova) && !idOperacaoAnterior.isEmpty()) {
                    try {
                        String opAnterior = db.consultaDados("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + idOperacaoAnterior, "NOME_OPERACAO");
                        String opNova = adapterOperacao.getItem(i).getNome_operacao();

                        LogSistema log = new LogSistema();
                        log.setIdPedido(webPedido.getId_web_pedido());
                        log.setIdPedidoApp(webPedido.getId_pedido_app());
                        log.setIdWebPedidoServidor(webPedido.getId_web_pedido_servidor());
                        log.setIdVendedor(webPedido.getId_vendedor());
                        log.setTipoLog("AUDITORIA");
                        log.setEvento("ALTERAR_OPERACAO");
                        log.setClasse("Pedido2");
                        log.setMetodo("spOperacao.onItemSelected");
                        log.setValorAnterior(opAnterior);
                        log.setValorNovo(opNova);
                        new LogSistemaDAO(db).add(log);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                idOperacaoAnterior = idNova;
                pedidoHelper.salvaPedidoParcial();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spMoeda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!PedidoHelper.isPedido1Valido()) {
                    return;
                }
                if ( !(bundle.getInt("vizualizacao") == 1))
                    pedidoHelper.salvaPedidoParcial();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spPagamento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                webPedido.setPrazo_confirmado("S");
                PedidoHelper.getWebPedido().setPrazo_confirmado("S");

                return false;
            }
        });
        spPagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!PedidoHelper.isPedido1Valido()) {
                    return;
                }
                if ( adapterPagamento.getItem(position).getTipo_condicao().equalsIgnoreCase("1")) {
                    edtValorRecibo.setEnabled(false);
                    edtValorRecibo.setText("0.00");
                }else
                    edtValorRecibo.setEnabled(true);

                if ( idCondPag.equalsIgnoreCase(adapterPagamento.getItem(position).getId_condicao())) {
                    PedidoHelper.setCondicoesPagamento(adapterPagamento.getItem(position));
                    return;
                }

                // GRAVAÇÃO DO LOG DE ALTERAÇÃO DE PRAZO
                if (!idCondPag.isEmpty()) {
                    try {
                        String prazoAnterior = db.consultaDados("SELECT NOME_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + idCondPag, "NOME_CONDICAO");
                        String prazoNovo = Objects.requireNonNull(adapterPagamento.getItem(position)).getNome_condicao();

                        LogSistema log = new LogSistema();
                        log.setIdPedido(webPedido.getId_web_pedido());
                        log.setIdPedidoApp(webPedido.getId_pedido_app());
                        log.setIdWebPedidoServidor(webPedido.getId_web_pedido_servidor());
                        log.setIdVendedor(webPedido.getId_vendedor());
                        log.setTipoLog("AUDITORIA");
                        log.setEvento("ALTERAR_PRAZO");
                        log.setClasse("Pedido2");
                        log.setMetodo("spPagamento.onItemSelected");
                        log.setValorAnterior(prazoAnterior);
                        log.setValorNovo(prazoNovo);
                        new LogSistemaDAO(db).add(log);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                idCondPag = adapterPagamento.getItem(position).getId_condicao();
                spPagamento.setSelection(position);
                PedidoHelper.setCondicoesPagamento(adapterPagamento.getItem(position));
                pedidoHelper.salvaPedidoParcial();
                if ( !(bundle.getInt("vizualizacao") == 1)) {
                    //pedidoHelper.salvaPedidoParcial();
                    //Alteração feita em 18/04/2019   RRC
                    try {
                        if ( ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6")) {
                                if (!PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S")) {
                                    if (PedidoHelper.verificaDesconto()) {
                                        if (Pedido1.calcularDesconto() > 0.00)
                                            showActionDialog(0.0f, 0.0f, "A condição de pagamento informada não permite desconto(s), e foi detectado desconto(s) no(s) produto(s). Deseja limpar todo(s) o(s) desconto(s) permitido(s)?\n" +
                                                    "(O total da venda será de " + MascaraUtil.mascaraReal(PedidoHelper.calculaValorSemDesconto()) + ")");
                                        else
                                            Toast.makeText(getActivity(), "A condição de pagamento informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", Toast.LENGTH_LONG).show();
                                            //DialogHelper.showSnackInfo(getView(), "A condição de pagamento informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", R.drawable.ic_alerta_mensagem);

                                    } else {
                                        //DialogHelper.showSnackInfo(getView(), "A condição de pagamento informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!!", R.drawable.ic_alerta_mensagem);
                                        Toast.makeText(getActivity(), "A condição de pagamento informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", Toast.LENGTH_LONG).show();
                                    }

                                } else /*if (position == 1 || position ==2 )*/ {
                                    if (PedidoHelper.getIdPedido() > 0) {
                                        if (Pedido1.totalProdutos() > 0) {
                                            Float desconto_perc = 0.0f;
                                            try {
                                                String desconto = adapterPagamento.getItem(position).getDesconto_perc();
                                                desconto_perc = Float.parseFloat(adapterPagamento.getItem(position).getDesconto_perc());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            if (desconto_perc > 0.00) {
                                                Float valorDesconto = PedidoHelper.calculaValorComDescontoPrazo(desconto_perc);
                                                Float valorTotal = 0.0f;
                                                try {
                                                    valorTotal = Float.parseFloat(PedidoHelper.getWebPedido().getValor_total());
                                                } catch (NullPointerException e) {

                                                } catch (Exception e) {

                                                }
                                                DecimalFormat decimal = new DecimalFormat("0.00");
                                                //Double vl_conv = Double.parseDouble(resultado);
                                                String total = (decimal.format(valorTotal));
                                                total = total.replaceAll(",", ".");
                                                final Float desconto_prazo = desconto_perc;
                                                String total_com_desconto = (decimal.format(PedidoHelper.calculaValorComDescontoPrazo(desconto_perc)));
                                                total_com_desconto = total_com_desconto.replaceAll(",", ".");
                                                if (Float.parseFloat(total) != Float.parseFloat(total_com_desconto)) {
                                                    Toast.makeText(context, "Desconto de " +  desconto_perc + "% permitido no prazo de pagamento selecionado", Toast.LENGTH_SHORT).show();
                                                    if ( configuracao.getCalcula_dscto_automatico_muda_prazo().equalsIgnoreCase("S")) {
                                                        //Codigo foi comentado nesta parte para posterior criação de parametro para verificar se recalcula ou nao
                                                        //DialogHelper.showSnackInfo(getView(), "Desconto de " +  desconto_perc + "% permitido no prazo de pagamento selecionado!", R.drawable.ic_alerta_mensagem);
                                                        showMsgSimNao(desconto_prazo, desconto_prazo, "A condição de pagamento informada permite desconto(s) de " + desconto_perc + "%\n" +
                                                                "(O total da venda com desconto será de " + MascaraUtil.mascaraReal(PedidoHelper.calculaValorComDescontoPrazo(desconto_perc)) + ")\n Deseja reprocessar os dados com o desconto de " + desconto_perc + "% para o(s) produto(s) permitido(s)?");
                                                    }

                                                } else {
                                                    Pedido1.verificaDescontoPrazo(desconto_perc);
                                                }
                                            } else {
                                                float valor_desconto = 0.0f;
                                                try {
                                                    valor_desconto = Float.parseFloat(PedidoHelper.getWebPedido().getValor_desconto());
                                                } catch (NullPointerException | NumberFormatException e) {
                                                    e.printStackTrace();
                                                    valor_desconto = 0.1f;
                                                } catch (Exception e) {
                                                    valor_desconto = 0.1f;
                                                    e.printStackTrace();
                                                }

                                                if (valor_desconto > 0.00) {
                                                    showActionDialog(0.0f, 0.0f, "Desconto não autorizado ou percentual de desconto não informado !\nDeseja reprocessar os dados?\n Atenção!! Todos os descontos aplicados serão removidos!");
                                                }
                                            }
                                        } else {
                                            //Toast.makeText(getActivity(), "A condição de pagamento informada permite desconto(s)! Nenhum produto cadastrado!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                                return;
                            }
                            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4")) {
                                if (!PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S")) {
                                    if (PedidoHelper.verificaDesconto()) {
                                        if (Pedido1.calcularDesconto() > 0.00) {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                            alert.setTitle("Atenção");
                                            alert.setMessage("A condição de pagamento/categoria informada não permite desconto(s), e foi detectado desconto(s) no(s) produto(s). Deseja limpar todo(s) o(s) desconto(s)?\n" +
                                                    "(O total da venda será de " + MascaraUtil.mascaraReal(PedidoHelper.calculaValorSemDesconto()) + ")");
                                            alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    webPedidoDAO.updateDescontoIndevido(PedidoHelper.getWebPedido(), "S");
                                                }
                                            });
                                            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Pedido1.zerarDesconto();
                                                }
                                            });
                                            alert.show();
                                        } else {
                                            //DialogHelper.showSnackInfo(getView(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", R.drawable.ic_alerta_mensagem);
                                            Toast.makeText(getActivity(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        //DialogHelper.showSnackInfo(getView(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!!", R.drawable.ic_alerta_mensagem);
                                        Toast.makeText(getActivity(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                return;
                            }
                            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                                return;
                            }
                        } else {
                            if (!PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S")) {
                                if (PedidoHelper.verificaDesconto()) {
                                    if (Pedido1.calcularDesconto() > 0.00) {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                        alert.setTitle("Atenção");
                                        alert.setMessage("A condição de pagamento/categoria informada não permite desconto(s), e foi detectado desconto(s) no(s) produto(s). Deseja limpar todo(s) o(s) desconto(s)?\n" +
                                                "(O total da venda será de " + MascaraUtil.mascaraReal(PedidoHelper.calculaValorSemDesconto()) + ")");
                                        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                webPedidoDAO.updateDescontoIndevido(PedidoHelper.getWebPedido(), "S");
                                            }
                                        });
                                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Pedido1.zerarDesconto();
                                            }
                                        });
                                        alert.show();
                                    } else {
                                        //DialogHelper.showSnackInfo(getView(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", R.drawable.ic_alerta_mensagem);
                                        Toast.makeText(getActivity(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    //DialogHelper.showSnackInfo(getView(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!!", R.drawable.ic_alerta_mensagem);
                                    Toast.makeText(getActivity(), "A condição de pagamento/categoria informada não permite desconto(s)! Nenhum produto/desconto cadastrado/informado!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                if (PedidoHelper.getIdPedido() > 0) {
                                    if (Pedido1.totalProdutos() > 0) {
                                        Float desconto_perc = 0.0f;
                                        try {
                                            String desconto = adapterPagamento.getItem(position).getDesconto_perc();
                                            desconto_perc = Float.parseFloat(adapterPagamento.getItem(position).getDesconto_perc());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (desconto_perc > 0.00) {
                                            Float valorDesconto = PedidoHelper.calculaValorComDescontoPrazo(desconto_perc);
                                            Float valorTotal = 0.0f;
                                            try {
                                                valorTotal = Float.parseFloat(PedidoHelper.getWebPedido().getValor_total());
                                            } catch (NullPointerException e) {

                                            } catch (Exception e) {

                                            }
                                            DecimalFormat decimal = new DecimalFormat("0.00");
                                            //Double vl_conv = Double.parseDouble(resultado);
                                            String total = (decimal.format(valorTotal));
                                            total = total.replaceAll(",", ".");
                                            final Float desconto_prazo = desconto_perc;
                                            String total_com_desconto = (decimal.format(PedidoHelper.calculaValorComDescontoPrazo(desconto_perc)));
                                            total_com_desconto = total_com_desconto.replaceAll(",", ".");
                                            if (Float.parseFloat(total) != Float.parseFloat(total_com_desconto)) {
                                                //DialogHelper.showSnackInfo(getView(), "Desconto de " +  desconto_perc + "% permitido no prazo de pagamento selecionado!", R.drawable.ic_alerta_mensagem);
                                                Toast.makeText(context, "Desconto de " +  desconto_perc + "% permitido no prazo de pagamento selecionado", Toast.LENGTH_SHORT).show();
                                                //Codigo foi comentado nesta parte para posterior criação de parametro para verificar se recalcula ou nao
                                                if ( configuracao.getCalcula_dscto_automatico_muda_prazo().equalsIgnoreCase("S")) {
                                                    showMsgSimNao(desconto_prazo, desconto_prazo, "A condição de pagamento informada permite desconto(s) de " + desconto_perc + "%\n" +
                                                            "(O total da venda com desconto será de " + MascaraUtil.mascaraReal(PedidoHelper.calculaValorComDescontoPrazo(desconto_perc)) + ")\n Deseja reprocessar os dados com o desconto de " + desconto_perc + "% para o(s) produto(s) permitido(s)?");
                                                }
                                            } else {
                                                Pedido1.verificaDescontoPrazo(desconto_perc);
                                            }
                                        } else {
                                            float valor_desconto = 0.0f;
                                            try {
                                                valor_desconto = Float.parseFloat(PedidoHelper.getWebPedido().getValor_desconto());
                                            } catch (NullPointerException | NumberFormatException e) {
                                                e.printStackTrace();
                                                valor_desconto = 0.1f;
                                            } catch (Exception e) {
                                                valor_desconto = 0.1f;
                                                e.printStackTrace();
                                            }
                                            if (valor_desconto > 0.00) {
                                                showActionDialog(0.0f, 0.0f, "Desconto não autorizado ou percentual de desconto não informado !\nDeseja reprocessar os dados?\n Atenção!! Todos os descontos aplicados serão removidos!");
                                            }
                                        }
                                    } else {
                                        //DialogHelper.showSnackInfo(getView(), "A condição de pagamento informada permite desconto(s)! Nenhum produto cadastrado!", R.drawable.ic_alerta_mensagem);
                                        Toast.makeText(getActivity(), "A condição de pagamento informada permite desconto(s)! Nenhum produto cadastrado!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                //webPedidoDAO.updateDescontoIndevido(PedidoHelper.getWebPedido(), "N");
                            }
                        }


                    } catch ( NullPointerException e) {
                    } catch ( Exception e) {
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*
        edtValorRecibo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!edtValorRecibo.getText().toString().trim().isEmpty()) {
                        webPedido.setValor_recibo(edtValorRecibo.getText().toString().toString().replaceAll("\\s+", "").replace(",", "."));
                    } else {
                        webPedido.setValor_recibo("0.00");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        */
        //System.gc();
        mLocale = new Locale("pt", "BR");

        edtValorRecibo.addTextChangedListener(new MoneyTextWatcher(edtValorRecibo, mLocale));
        try {
            if (!configuracao.getVisual_prazo_pgto_pedido_app().equalsIgnoreCase("S"))
                showHideLayoutPrazoPagamento();
        } catch ( NullPointerException  e) {
        } catch ( Exception e) {
        }
        return (view);
    }

    private void showHideLayoutPrazoPagamento(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyPrazoPagamento.getLayoutParams();
        lyPrazoPagamento.setLayoutParams(params);
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        try {
            if ( configuracao.getVisual_hist_fin_pedido_app().equalsIgnoreCase("S")){
                params.height =  0;
                params.width =  0;
                lyPrazoPagamento.setLayoutParams(params);
                txtDataEntrega.setText("DATA DE ENTREGA");

            }
        } catch ( NullPointerException e){
        } catch ( Exception e) {
        }
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
                PedidoHelper.editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void pegaCliente(Cliente cliente) {
        objetoCliente = cliente;

    }

    public WebPedido salvaPedido() {
        webPedido.setId_condicao_pagamento(adapterPagamento.getItem(spPagamento.getSelectedItemPosition()).getId_condicao());
        webPedido.setCadastro(objetoCliente);
        webPedido.setObservacoes(edtObservacao.getText().toString());
        try {
            if (configuracao.getLayout_venda_erp().equalsIgnoreCase("25"))
                webPedido.setData_prev_entrega(db.pegaDataAtual());
            else
                webPedido.setData_prev_entrega(edtDataEntrega.getText().toString().trim());
        } catch ( NullPointerException e) {
            webPedido.setData_prev_entrega(db.pegaDataAtual());
        }
        webPedido.setPedido_enviado("N");
        webPedido.setId_operacao(adapterOperacao.getItem(spOperacao.getSelectedItemPosition()).getId_operacao());
        try {
            webPedido.setId_moeda_padrao(adapterMoeda.getItem(spMoeda.getSelectedItemPosition()).getId_moeda());
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            webPedido.setValor_recibo( ( (edtValorRecibo.getText().toString().substring(2)
                    .replaceAll("\\s+", "")).replace(".","") ).replace(",", ".") );
        } catch ( NullPointerException|NumberFormatException|IndexOutOfBoundsException e) {
            webPedido.setValor_recibo("0.00");
        } catch ( Exception e) {
            webPedido.setValor_recibo("0.00");
        }
        try {
            if ( !edtValorRecibo.getText().toString().isEmpty()) {
                webPedido.setValor_recibo( ( (edtValorRecibo.getText().toString().substring(2)
                        .replaceAll("\\s+", "")).replace(".","") ).replace(",", ".") );
            }
        } catch ( NullPointerException|NumberFormatException|IndexOutOfBoundsException e) {
            webPedido.setValor_recibo("0.00");
        } catch ( Exception e) {
            webPedido.setValor_recibo("0.00");
            e.printStackTrace();
        }

        return webPedido;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        CondicoesPagamentoDAO condicoesPagamentoCabDAO = new CondicoesPagamentoDAO(db);
        List<CondicoesPagamento> listaCondicoesPagamentos = new ArrayList<>();
        String sql;
        try {

            try {
                if ( PedidoHelper.getWebPedido().getCadastro() != null) {
                    sql = "SELECT CP.* FROM TBL_CONDICOES_PAG_CAB AS CP INNER JOIN TBL_CADASTRO_CONDICOES_PAG AS CCP ON ( CCP.ID_CONDICAO = CP.ID_CONDICAO AND CCP.ID_CADASTRO = ";
                    sql += PedidoHelper.getWebPedido().getCadastro().getId_cadastro_servidor() + ") WHERE CP.ATIVO = 'S' AND  ( CP.NOME_CONDICAO != '' AND CP.NOME_CONDICAO IS NOT NULL) ORDER BY CP.ID_CONDICAO";
                    if ( configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                        float limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                        float limitUtilizado = Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencido()) + Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencendo());
                        if ( (limiteCredito - limitUtilizado) < 0.00f) {
                            sql = "SELECT CP.* FROM TBL_CONDICOES_PAG_CAB AS CP INNER JOIN TBL_CADASTRO_CONDICOES_PAG AS CCP ON ( CCP.ID_CONDICAO = CP.ID_CONDICAO AND CCP.ID_CADASTRO = ";
                            sql += PedidoHelper.getWebPedido().getCadastro().getId_cadastro_servidor() + ") WHERE CP.ATIVO = 'S' AND  ( CP.NOME_CONDICAO != '' AND CP.NOME_CONDICAO IS NOT NULL) AND CP.TIPO_CONDICAO = '1' ORDER BY CP.ID_CONDICAO";
                        }
                    }
                    listaCondicoesPagamentos = condicoesPagamentoCabDAO.getLista(sql);
                }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
            try {
                if (listaCondicoesPagamentos.size() <= 0) {
                    sql = "SELECT * FROM TBL_CONDICOES_PAG_CAB  WHERE ATIVO = 'S' AND  (NOME_CONDICAO != '' AND NOME_CONDICAO IS NOT NULL) ORDER BY ID_CONDICAO;";
                    if (configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                        float limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                        float limitUtilizado = Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencido()) + Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencendo());
                        if ((limiteCredito - limitUtilizado) < 0.00f) {
                            sql = "SELECT * FROM TBL_CONDICOES_PAG_CAB  WHERE ATIVO = 'S' AND  (NOME_CONDICAO != '' AND NOME_CONDICAO IS NOT NULL) AND TIPO_CONDICAO = '1' ORDER BY ID_CONDICAO;";
                        }
                    }
                    listaCondicoesPagamentos = condicoesPagamentoCabDAO.getLista(sql);
                }
                adapterPagamento = new ArrayAdapter<>(getContext(),
                        tExtraFonte == 0 ?  R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g,
                        listaCondicoesPagamentos);
                //adapterPagamento = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, listaCondicoesPagamentos);
                spPagamento.setAdapter(adapterPagamento);
                if (!configuracao.getId_con_pgto_default_app().equalsIgnoreCase("0")) {
                    for (int i = 0; i < listaCondicoesPagamentos.size(); i++)
                        if (listaCondicoesPagamentos.get(i).getId_condicao().equalsIgnoreCase(configuracao.getId_con_pgto_default_app())) {
                            spPagamento.setSelection(i);
                            webPedido.setId_condicao_pagamento( listaCondicoesPagamentos.get(i).getId_condicao());
                        }
                } else {
                    spPagamento.setSelection(0);
                }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
        } catch (CursorIndexOutOfBoundsException e) {
            sql =  "SELECT * FROM TBL_CONDICOES_PAG_CAB  WHERE ATIVO = 'S' AND  (NOME_CONDICAO != '' AND NOME_CONDICAO IS NOT NULL)  ORDER BY ID_CONDICAO;";
            if ( configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                float limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                float limitUtilizado = Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencido()) + Float.parseFloat(ClienteHelper.getCliente().getFinanceiro_vencendo());
                if ( (limiteCredito - limitUtilizado) < 0.00f) {
                    sql =  "SELECT * FROM TBL_CONDICOES_PAG_CAB  WHERE ATIVO = 'S' AND  (NOME_CONDICAO != '' AND NOME_CONDICAO IS NOT NULL) AND TIPO_CONDICAO = '1' ORDER BY ID_CONDICAO;";
                }
            }
            listaCondicoesPagamentos = condicoesPagamentoCabDAO.getLista(sql);
            adapterPagamento = new ArrayAdapter<>(getContext(), tExtraFonte == 0 ?  R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g, listaCondicoesPagamentos);
            spPagamento.setAdapter(adapterPagamento);
            if (!configuracao.getId_con_pgto_default_app().equalsIgnoreCase("0")) {
                for (int i = 0; i < listaCondicoesPagamentos.size(); i++)
                    if (listaCondicoesPagamentos.get(i).getId_condicao().equalsIgnoreCase(configuracao.getId_con_pgto_default_app())) {
                        spPagamento.setSelection(i);
                        webPedido.setId_condicao_pagamento( listaCondicoesPagamentos.get(i).getId_condicao());
                    }
            } else {
                spPagamento.setSelection(0);
            }
        }
        if (PedidoHelper.getIdPedido() > 0) {
            webPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido()).get(0);
            objetoCliente = webPedido.getCadastro();
            //Seleciona Condição de pagamento correta dentro do Spinner spPagamento
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_condicao_pagamento().equals(adapterPagamento.getItem(i).getId_condicao()));
                spPagamento.setSelection(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            edtValorRecibo.setText( MascaraUtil.duasCasaDecimal(webPedido.getValor_recibo()));
        } catch ( NullPointerException|NumberFormatException e) {
            edtValorRecibo.setText(MascaraUtil.duasCasaDecimal("0.00"));
        } catch (Exception e) {
            edtValorRecibo.setText(MascaraUtil.duasCasaDecimal("0.00"));
        }
        try {
            edtSaldoDevedor.setText( MascaraUtil.mascaraReal(Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencido())
                    + Float.parseFloat(webPedido.getCadastro().getFinanceiro_vencendo()) ));
        } catch ( NullPointerException|NumberFormatException e) {
            edtSaldoDevedor.setText(MascaraUtil.mascaraReal("0.00"));
        } catch ( Exception e) {
            edtSaldoDevedor.setText(MascaraUtil.mascaraReal("0.00"));
        }
        super.onResume();
    }


    @Override
    public void onDestroy() {
        spPagamento.setSelection(spPagamento.getSelectedItemPosition());
        objetoCliente = null;
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        int x = spPagamento.getSelectedItemPosition();
        spPagamento.setSelection(spPagamento.getSelectedItemPosition());
        super.onPause();
    }
    @Override
    public void onStop() {
        int x = spPagamento.getSelectedItemPosition();
        spPagamento.setSelection(spPagamento.getSelectedItemPosition());
        super.onStop();
    }

    /*
    public void showMsgSucesso(String mensagem) {
        ViewGroup viewGroup = PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sucesso_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                pedidoHelper.limparDados();
                objetoCliente = null;
                HistoricoFinanceiroHelper.limparDados();
                PedidoHelper.getActivityPedidoMain().finish();

            }
        });
        if ( alertDialog != null) {
            alertDialog.show();
        }
    }
    */

    /*
    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                pedidoHelper.limparDados();
                objetoCliente = null;
                HistoricoFinanceiroHelper.limparDados();
                PedidoHelper.getActivityPedidoMain().finish();

            }
        });
        if ( alertDialog != null) {
            alertDialog.show();
        }
    }
*/

    public void showMsgSimNao( float verificaDescontoPrazo , float colocaDescontoPrazo, String mensagem) {
        ViewGroup viewGroup = PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido1.verificaDescontoPrazo(verificaDescontoPrazo);
                alertDialog.dismiss();

            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido1.zerarDesconto();
                Pedido1.colocarDescontoPrazo(colocaDescontoPrazo, db);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }



    private void addPedidoFirebase(String cnpj, WebPedido webPedido) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected()) {
            showFeedbackDialog("Você está offline. A copia de segurança não sera efetuada!.", false);
            return; // Interrompe o método
        }
        String cnpj_final = cnpj;
        if ( cnpj == null || cnpj.isEmpty()) {
            configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            cnpj = configuracao.getCnpj_empresa().trim();
            if ( cnpj == null || cnpj.isEmpty()) {
                showFeedbackDialog("Parametros inválidos para efetuar a cópia de segurança!", false);
                return;
            }
        }
        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
        try {
            if (webPedido.getId_pedido_app_fdb() == null || webPedido.getId_pedido_app_fdb().isEmpty()) {
                webPedido.setId_pedido_app_fdb(
                        webPedido.getId_pedido_app().substring(0, 3)
                                + String.format("%08d", Integer.parseInt(webPedido.getId_web_pedido()))
                                + "DBFSTORE" + webPedido.getId_pedido_app().substring(19));
            }
        } catch (NullPointerException|NumberFormatException e) {
            DialogHelper.showSnackInfo(getView(),  "Falha ao efetuar a cópia de segurança!\n" + e.toString(), R.drawable.ic_baseline_error_outline_24);
            //Toast.makeText(context, "Falha ao efetuar a cópia de segurança!\n" + e.toString(), Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            DialogHelper.showSnackInfo(getView(),  "Falha ao efetuar a cópia de segurança!!\n" + e.toString(), R.drawable.ic_baseline_error_outline_24);
            //Toast.makeText(context, "Falha ao efetuar a cópia de segurança!\n" + e.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (webPedido.getId_pedido_app_fdb() == null || webPedido.getId_pedido_app_fdb().isEmpty()) {
            DialogHelper.showSnackInfo(getView(),  "Falha ao efetuar a cópia de segurança!!!" , R.drawable.ic_baseline_error_outline_24);
            //Toast.makeText(context, "Falha ao efetuar a copia de segurança!!", Toast.LENGTH_SHORT).show();
            return;
        }
        //       Toast.makeText(context, webPedido.getId_pedido_app_fdb(), Toast.LENGTH_SHORT).show();

        progress = new ProgressDialog(PedidoHelper.getActivityPedidoMain());
        progress.setMessage("Efetuando cópia de segurança!Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();

        webPedido.setWebPedidoItens(webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM"));
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        for (Field field : webPedido.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(webPedido));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            fdb.collection(cnpj_final).document("TBL_WEB_PEDIDO").collection(webPedido.getId_vendedor()).document(webPedido.getId_pedido_app_fdb()).set(map).addOnSuccessListener(documentReference -> {
                        progress.dismiss();
                        webPedidoDAO.updateIdFDB(webPedido, webPedido.getId_web_pedido(), "", "");
                showFeedbackDialog("Copia de segurança efetuada com sucesso!");
                        //showMsgAlerta("Copia de segurança efetuada com sucesso!");
                    }
            ).addOnFailureListener(e -> {
                progress.dismiss();
                DialogHelper.showFeedbackDialog(requireContext(), "Atenção", "Falha ao efetuar a copia de segurança!!\n" + e.getMessage(), true, R.drawable.ic_baseline_error_outline_24, null, null );
                //showMsgAlerta("Falha ao efetuar a copia de segurança!!\n" + e.getMessage());
            });
        }, 500);

    }

    private void hideEditDataEntrega() {
        ViewGroup.LayoutParams params = edtDataEntrega.getLayoutParams();
        params.height = 0;
        edtDataEntrega.setLayoutParams(params);
        edtDataEmissao.setLayoutParams(params);
    }
    private void showEditDataEntrega() {
        ViewGroup.LayoutParams params = edtDataEntrega.getLayoutParams();
        params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;
        edtDataEntrega.setLayoutParams(params);
        edtDataEmissao.setLayoutParams(params);
    }

    private void hideEditValorRecibo() {
        ViewGroup.LayoutParams params = edtValorRecibo.getLayoutParams();
        params.height = 0;
        edtValorRecibo.setLayoutParams(params);
        edtSaldoDevedor.setLayoutParams(params);
    }
    private void showEditValorRecibo() {
        ViewGroup.LayoutParams params = edtValorRecibo.getLayoutParams();
        params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;
        edtValorRecibo.setLayoutParams(params);
        edtSaldoDevedor.setLayoutParams(params);
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
            try {
                BigDecimal parsed = parseToBigDecimal(editable.toString(), locale);
                String formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);
                // NumberFormat.getNumberInstance(locale).format(parsed); // sem o simbolo de moeda

                editText.setText(formatted);
                editText.setSelection(formatted.length());
                editText.addTextChangedListener(this);
            } catch (NullPointerException | NumberFormatException | NoClassDefFoundError e) {

            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private BigDecimal parseToBigDecimal(String value, Locale locale) {
            if (Build.VERSION.SDK_INT >= +Build.VERSION_CODES.N) {
                try {
                    String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());
                    String cleanString = value.replaceAll(replaceable, "");
                    webPedido.setValor_recibo(String.valueOf(new BigDecimal(cleanString).setScale(
                            2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
                    )));
                    return new BigDecimal(cleanString).setScale(
                            2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
                    );
                } catch (NullPointerException | NumberFormatException | NoClassDefFoundError e) {
                    webPedido.setValor_recibo("0.00");
                    return new BigDecimal(0.00);
                }
            } else {
                webPedido.setValor_recibo("0.00");
                return null;
            }
        }
    }

    /*public void showMsgBackupPedido( String cnpj, WebPedido webPedido)
    {
        ViewGroup viewGroup = PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText("Pedido de " + webPedido.getCadastro().getNome_cadastro() +" salvo com sucesso! Gostaria de criar uma cópia de segurança?");
        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                pedidoHelper.limparDados();
                objetoCliente = null;
                HistoricoFinanceiroHelper.limparDados();
                PedidoHelper.getActivityPedidoMain().finish();

            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                addPedidoFirebase(cnpj, webPedido);
            }
        });
        alertDialog.show();
    }

     */

    public void showActionDialog(float verificaDescontoPrazo , float colocaDescontoPrazo, String mensagem) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,  R.drawable.ic_help_new,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {
                        Pedido1.verificaDescontoPrazo(verificaDescontoPrazo);
                    }
                    @Override
                    public void onNegative() {
                        Pedido1.zerarDesconto();
                        Pedido1.colocarDescontoPrazo(colocaDescontoPrazo, db);
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }

    public void showActionDialog(String cnpj, WebPedido webPedido ) {
        WebPedido webPedido1 = PedidoHelper.getWebPedido();
        // NOVO: Diálogo com Resumo Detalhado igual ao de envio
        DialogHelper.showActionDialog(
                context,
                "Pedido salvo com sucesso! Gostaria de criar uma cópia de segurança?",
                "NÂO", null,
                "SIM", R.drawable.ic_baseline_check_circle_outline_24_color_primary,
                null, null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {
                        pedidoHelper.limparDados();
                        objetoCliente = null;
                        HistoricoFinanceiroHelper.limparDados();
                        PedidoHelper.getActivityPedidoMain().finish();
                    }
                    @Override
                    public void onNegative() {
                        addPedidoFirebase(cnpj, PedidoHelper.getWebPedido());
                    }
                    @Override
                    public void onNeutral() {
                    }
                },
                getPopulatedSummaryView(webPedido1, true)
        );

        /*
        // ANTERIOR:
        DialogHelper.showActionDialog(
                context,
                "Pedido de " + webPedido.getCadastro().getNome_cadastro() +" salvo com sucesso! Gostaria de criar uma cópia de segurança?",
                "NÂO", null,
                "SIM", null,

                null,  R.drawable.ic_help_new,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {
                        pedidoHelper.limparDados();
                        objetoCliente = null;
                        HistoricoFinanceiroHelper.limparDados();
                        PedidoHelper.getActivityPedidoMain().finish();
                    }
                    @Override
                    public void onNegative() {
                        addPedidoFirebase(cnpj, webPedido);
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
        */
    }

    private View getPopulatedSummaryView(WebPedido pedido, boolean mostrarTotal) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewResumo = inflater.inflate(R.layout.layout_dialog_resumo_pedido, null);

        TextView tvPedidoNum = viewResumo.findViewById(R.id.tvResumoPedidoNum);
        TextView tvCliente = viewResumo.findViewById(R.id.tvResumoCliente);
        TextView tvVendedor = viewResumo.findViewById(R.id.tvResumoVendedor);
        TextView tvEmpresa = viewResumo.findViewById(R.id.tvResumoEmpresa);
        TextView tvData = viewResumo.findViewById(R.id.tvResumoData);
        TextView tvItens = viewResumo.findViewById(R.id.tvResumoItens);
        TextView tvPrazo = viewResumo.findViewById(R.id.tvResumoPrazo);
        TextView tvOperacao = viewResumo.findViewById(R.id.tvResumoOperacao);
        TextView tvTotal = viewResumo.findViewById(R.id.tvResumoTotal);
        TextView tvLabelTotal = viewResumo.findViewById(R.id.tvLabelTotal);
        View lyTotal = viewResumo.findViewById(R.id.lyResumoTotal);
        
        // NOVO: Bloco de Informativo de Débito (Discreto)
        View lyDebito = viewResumo.findViewById(R.id.lyResumoDebito);
        TextView tvDebito = viewResumo.findViewById(R.id.tvResumoDebito);

        if (tvPedidoNum != null) tvPedidoNum.setText("PEDIDO Nº " + MascaraUtil.numeroZeros(pedido.getId_web_pedido(), 5));
        if (tvCliente != null) {
            if (pedido.getCadastro() != null && pedido.getCadastro().getNome_cadastro() != null)
                tvCliente.setText(pedido.getCadastro().getNome_cadastro().trim());
            else
                tvCliente.setText(pedido.getNome_extenso() != null ? pedido.getNome_extenso().trim() : "Cliente não identificado");
        }
        if (tvVendedor != null) {
            String nomeVend = (pedido.getUsuario_lancamento_nome() != null && !pedido.getUsuario_lancamento_nome().isEmpty()) ? pedido.getUsuario_lancamento_nome() : (UsuarioHelper.getUsuario() != null ? UsuarioHelper.getUsuario().getNome_usuario() : "");
            tvVendedor.setText("Vendedor: " + nomeVend.trim());
        }
        if (tvEmpresa != null) {
            try {
                String razaoSocial = db.consultaDados("SELECT RAZAO_SOCIAL FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + pedido.getId_empresa() + "';", "RAZAO_SOCIAL");
                tvEmpresa.setText("Empresa: " + (razaoSocial != null ? razaoSocial.trim() : "Não identificada"));
            } catch (Exception e) {
                tvEmpresa.setText("Empresa: Não identificada");
            }
        }
        if (tvData != null) {
            try { tvData.setText(MascaraUtil.formataData(pedido.getData_emissao(), "/")); } catch (Exception e) { tvData.setText(pedido.getData_emissao()); }
        }
        if (tvItens != null) tvItens.setText(pedido.getQtde_itens());
        if (tvPrazo != null) {
            try { String prazo = db.consultaDados("SELECT NOME_CONDICAO FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + pedido.getId_condicao_pagamento() + ";", "NOME_CONDICAO"); tvPrazo.setText(prazo != null && !prazo.isEmpty() ? prazo : "Não informado"); } catch (Exception e) { tvPrazo.setText("Não informado"); }
        }
        if (tvOperacao != null) {
            try { String operacao = db.consultaDados("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + pedido.getId_operacao() + ";", "NOME_OPERACAO"); tvOperacao.setText(operacao != null && !operacao.isEmpty() ? operacao : "Não informado"); } catch (Exception e) { tvOperacao.setText("Não informado"); }
        }

        if (tvLabelTotal != null) tvLabelTotal.setText("TOTAL DO PEDIDO");
        if (tvTotal != null) tvTotal.setText(MascaraUtil.mascaraRealSimbolo(pedido.getValor_total()));

        // NOVO: Exibe o informativo discreto de débito se houver valor vencido
        if (lyDebito != null && tvDebito != null && pedido.getCadastro() != null) {
            float vencido = 0.00f;
            try {
                vencido = Float.parseFloat(pedido.getCadastro().getFinanceiro_vencido() == null ? "0.00" : pedido.getCadastro().getFinanceiro_vencido());
            } catch (Exception ignored) {}

            if (vencido > 0) {
                lyDebito.setVisibility(View.VISIBLE);
                tvDebito.setText("Informativo: Cliente possui débito vencido de " + MascaraUtil.mascaraRealSimbolo(vencido));
            } else {
                lyDebito.setVisibility(View.GONE);
            }
        }

        if (lyTotal != null) {
            lyTotal.setVisibility(mostrarTotal ? View.VISIBLE : View.GONE);
        }

        return viewResumo;
    }

    private double cleanMoney(String valor) {
        if (valor == null || valor.isEmpty()) return 0;
        try {
            String v = valor.trim();
            if (v.contains(",")) {
                return Double.parseDouble(v.replace(".", "").replace(",", "."));
            }
            if (v.contains(".") && v.indexOf(".") == v.lastIndexOf(".")) {
                String decimalPart = v.substring(v.lastIndexOf(".") + 1);
                if (decimalPart.equals("0") || decimalPart.equals("00")) {
                    return Double.parseDouble(v.split("\\.")[0]) / 100.0;
                }
                return Double.parseDouble(v);
            }
            String apenasNumeros = v.replace(".", "");
            return Double.parseDouble(apenasNumeros) / 100.0;
        } catch (Exception e) {
            return 0;
        }
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso) {
        showFeedbackDialog(mensagem, sucesso, () -> {
            pedidoHelper.limparDados();
            objetoCliente = null;
            HistoricoFinanceiroHelper.limparDados();
            PedidoHelper.getActivityPedidoMain().finish();
            //System.gc();
        });
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, Runnable onConfirm) {
        DialogHelper.showFeedbackDialog(
                requireContext(),
                "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_check_circle : R.drawable.ic_baseline_error_outline_24,
                null, // Passando a cor personalizada
                () -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                    //System.gc();
                }
        );
    }

    public void showFeedbackDialog(String mensagem) {

        DialogHelper.showFeedbackDialog(
                requireContext(),
                "Atenção", // Título dinâmico
                mensagem,
                true,
                R.drawable.ic_baseline_check_circle_outline_24_color_primary,
                null, // Passando a cor personalizada
                () -> {
                    pedidoHelper.limparDados();
                    objetoCliente = null;
                    HistoricoFinanceiroHelper.limparDados();
                    PedidoHelper.getActivityPedidoMain().finish();
                }
        );
    }



}
