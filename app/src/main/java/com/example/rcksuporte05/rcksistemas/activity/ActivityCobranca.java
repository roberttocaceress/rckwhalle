package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.BairroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MunicipioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ReceberDAO;
import com.example.rcksuporte05.rcksistemas.DAO.RegiaoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCobrancaAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.model.ReceberCab;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFReciboUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
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

public class ActivityCobranca extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ActionModeCallback actionModeCallback;
    @BindView(R.id.tbl_cobranca)
    Toolbar toolbar;
    @BindView(R.id.buscaCobranca)
    SearchView buscaCobranca;
    @BindView(R.id.recycle_cobranca)
    RecyclerView recyclerView;
    //@BindView(R.id.progress_splash)
    //ProgressBar progress_bar;
    //@BindView(R.id.progress_title)
    //TextView progress_title;
    @BindView(R.id.card_view_menu)
    CardView card_view_menu;
    @BindView(R.id.img_view_avancar)
    ImageView btn_view_avancar;
    @BindView(R.id.img_view_menu)
    ImageView btn_view_menu;
    @BindView(R.id.id_opcao_emissao)
    CheckBox id_opcao_emissao;
    @BindView(R.id.id_opcao_vencimento)
    CheckBox id_opcao_vencimento;
    @BindView(R.id.id_opcao_recebido)
    CheckBox id_opcao_recebido;
    @BindView(R.id.id_opcao_reagendado)
    CheckBox id_opcao_reagendado;
    @BindView(R.id.sp_estado)
    Spinner sp_estado;
    @BindView(R.id.sp_cidade)
    Spinner sp_cidade;
    @BindView(R.id.sp_bairro)
    Spinner sp_bairro;
    @BindView(R.id.sp_regiao)
    Spinner sp_regiao;
    @BindView(R.id.sp_conta_bancaria)
    Spinner sp_conta_bancaria;
    @BindView(R.id.sp_moeda)
    Spinner sp_moeda;
    @BindView(R.id.edt_data_final)
    EditText edt_data_final;

    @BindView(R.id.edt_data_inicial)
    EditText edt_data_inicial;

    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private MunicipioDAO municipioDAO = new MunicipioDAO(db);
    private ReceberDAO webReceberCabDAO = new ReceberDAO(db);
    FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);

    private EmpresaParametro empresaParametro;
    private Configuracao configuracao = new Configuracao();

    PackageInfo pInfo = null;
    Context context;
    String periodo = "";
    boolean visivel = true;
    boolean recebido = false;
    boolean reagendado = false;

    private ListaCobrancaAdapter listaCobrancaAdapter;
    private ListaCobrancaAdapter.CobrancaAdapterListener listenerCobranca;
    private static List<FinanceiroResumo> listaFinanceiro = new ArrayList<>();
    private List<Municipio> listaMunicipio = new ArrayList<>();
    private Utilitaria util = new Utilitaria();



    List<Generico> listaBairro = new ArrayList<>();
    List<Generico> listaRegiao = new ArrayList<>();
    //List<Moeda> listaMoeda;

    private MenuItem geraPDFRecibo;
    private MenuItem geraPDFEmail;
    private MenuItem sincroniza;
    private MenuItem deleteRecebimento;


    private ActionMode actionMode;
    String id_android;
    String x;
    int positionSelected = -1;


    boolean cliqAtivo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_cobranca);
        ButterKnife.bind(this);
      
        toolbar.setTitle("Lista de cobrança");
        getConfiguracao();
        try {
            if (configuracao.getGira_tela_cobranca().isEmpty() || configuracao.getGira_tela_cobranca().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ActivityCobranca.ScrollListener());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buscaCobranca.setOnQueryTextListener(new QueryTextListener());
        buscaCobranca.setIconifiedByDefault(true);
        context = this;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            btn_view_menu.setTooltipText("Toque para visualizar/esconder menu para definir o tipo de relatório!");
        id_android = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        sp_estado.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf)));
        empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        for (int i = 0; i < sp_estado.getAdapter().getCount(); i++) {
            try {
                if (savedInstanceState != null) {
                    sp_estado.setSelection(savedInstanceState.getInt("indEstado"));
                    break;
                } else {
                    if (sp_estado.getAdapter().getItem(i).toString().equalsIgnoreCase(empresaParametro.getEndereco_uf()))
                        sp_estado.setSelection(i);
                }
            } catch (CursorIndexOutOfBoundsException | NullPointerException ex) {
            } catch (Exception e) {
            }
        }

        try {
            listaMunicipio = new ArrayList<>();
            Municipio municipio = new Municipio();
            municipio.setId("0");
            municipio.setNome_municipio("TODAS/TOQUE P SELECIONAR");
            municipio.setUf(sp_estado.getSelectedItem().toString());
            listaMunicipio.add(municipio);
            listaMunicipio.addAll(municipioDAO.getLista("SELECT * FROM TBL_CADASTRO_MUNICIPIO WHERE UF = '" + sp_estado.getSelectedItem().toString() + "' ORDER BY NOME_MUNICIPIO"));
            sp_cidade.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_activated_1, listaMunicipio));
            if (savedInstanceState != null) {
                sp_cidade.setSelection(savedInstanceState.getInt("indCidade"));
            }
        } catch (CursorIndexOutOfBoundsException | NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    try {
                        listaMunicipio = new ArrayList<>();
                        Municipio municipio = new Municipio();
                        municipio.setId("0");
                        municipio.setNome_municipio("TODAS/TOQUE P SELECIONAR");
                        municipio.setUf(sp_estado.getSelectedItem().toString());
                        listaMunicipio.add(municipio);
                        listaMunicipio.addAll(municipioDAO.getLista("SELECT * FROM TBL_CADASTRO_MUNICIPIO WHERE UF = '" + sp_estado.getSelectedItem().toString() + "' ORDER BY NOME_MUNICIPIO"));
                        sp_cidade.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_activated_1, listaMunicipio));
                        if (savedInstanceState != null)
                            sp_cidade.setSelection(savedInstanceState.getInt("indCidade"));
                        else
                            sp_cidade.setSelection(0);
                    } catch (CursorIndexOutOfBoundsException | NullPointerException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        */

        try {
            BairroDAO bairroDAO = new BairroDAO(db);
            listaBairro = new ArrayList<>();
            Generico generico = new Generico();
            generico.setId("0");
            generico.setNome("TODOS/TOQUE P SELECIONAR");
            generico.setAtivo("S");
            listaBairro.add(generico);
            if (savedInstanceState != null) {
                listaBairro.addAll(bairroDAO.getLista("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_CIDADE = " + listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId() + " ORDER BY NOME_BAIRRO"));
            }
            sp_bairro.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaBairro));
            if (savedInstanceState != null) {
                sp_bairro.setSelection(savedInstanceState.getInt("indBairro"));
            }
        } catch (CursorIndexOutOfBoundsException | NullPointerException ex) {
        } catch (Exception ex) {
        }
        /*
        sp_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    BairroDAO bairroDAO = new BairroDAO(db);
                    listaBairro = new ArrayList<>();
                    Generico generico = new Generico();
                    generico.setId("0");
                    generico.setNome("TODOS/TOQUE P SELECIONAR");
                    generico.setAtivo("S");
                    listaBairro.add(generico);
                    listaBairro.addAll(bairroDAO.getLista("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_CIDADE = " + listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId() + " ORDER BY NOME_BAIRRO"));
                    sp_bairro.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaBairro));
                    if (savedInstanceState != null) {
                        sp_bairro.setSelection(savedInstanceState.getInt("indBairro"));
                        if (!savedInstanceState.getString("strPesquisa").isEmpty() || savedInstanceState.getString("strPesquisa") != null) {
                            btnAvancarOnClick(savedInstanceState.getString("strPesquisa"));
                        } else{
                            btnAvancarOnClick("");
                        }
                    }else {
                        sp_bairro.setSelection(0);
                        //btnAvancarOnClick("");
                    }
                } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
                    ex.printStackTrace();
                } catch ( Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        */
        try {
            RegiaoDAO regiaoDAO = new RegiaoDAO(db);
            listaRegiao = new ArrayList<>();
            Generico generico = new Generico();
            generico.setId("0");
            generico.setNome("TODAS");
            generico.setAtivo("S");
            generico.setId_generico("0");
            listaRegiao.add(generico);
            listaRegiao.addAll(regiaoDAO.getLista("SELECT * FROM TBL_CADASTRO_REGIAO;"));
            sp_regiao.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaRegiao));
            if (listaRegiao.size() > 1) {
                if (savedInstanceState != null)
                    sp_regiao.setSelection(savedInstanceState.getInt("indRegiao"));
                else
                    for (int i = 0; i < listaRegiao.size(); i++) {
                        if (listaRegiao.get(i).getId_generico().equalsIgnoreCase(UsuarioHelper.getUsuario().getId_quando_vendedor())) {
                            sp_regiao.setSelection(i);
                            break;
                        }
                    }
            }
        } catch (CursorIndexOutOfBoundsException | NullPointerException ex) {
        } catch (Exception ex) {
        }

        edt_data_final.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!id_opcao_emissao.isChecked() && !id_opcao_vencimento.isChecked()) {
                        Toast.makeText(context, "Atenção!!\nFavor selecione um periodo(Emissão/Vencimento)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Calendar dataInicial = new GregorianCalendar();
                    if (!edt_data_inicial.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                        try {
                            dataInicial.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString()));
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Atenção!!\nA data inicial não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
                            return false;
                        } catch (ParseException e) {
                            Toast.makeText(context, "Atenção!!\nA data inicial não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    mostraDatePickerDialog(context, edt_data_final);
                }
                return false;
            }
        });

        edt_data_inicial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!id_opcao_emissao.isChecked() && !id_opcao_vencimento.isChecked()) {
                        Toast.makeText(context, "Atenção!!\nFavor selecione um periodo(Emissão/Vencimento)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    mostraDatePickerDialog(context, edt_data_inicial);
                }
                return false;
            }
        });

        sp_cidade.setClickable(false);
        sp_cidade.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Intent intent = new Intent(getApplicationContext(), ActivityMunicipio.class);
                    intent.putExtra("cidade", listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId());
                    intent.putExtra("uf", sp_estado.getAdapter().getItem(sp_estado.getSelectedItemPosition()).toString());
                    startActivityForResult(intent, 100);
                }
                return true;
            }
        });
        sp_bairro.setClickable(false);
        sp_bairro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    if (sp_cidade.getSelectedItemPosition() > 0) {
                        BairroDAO bairroDAO = new BairroDAO(db);
                        listaBairro = new ArrayList<>();
                        Generico generico = new Generico();
                        generico.setId("0");
                        generico.setNome("TODOS/TOQUE P SELECIONAR");
                        generico.setAtivo("S");
                        listaBairro.add(generico);
                        listaBairro.addAll(bairroDAO.getLista("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_CIDADE = " + listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId() + " ORDER BY NOME_BAIRRO"));
                        sp_bairro.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaBairro));
                        startActivityForResult(new Intent(getApplicationContext()
                                , ActivityBairro.class)
                                .putExtra("id_bairro", listaBairro.get(sp_bairro.getSelectedItemPosition()).getId())
                                .putExtra("id_cidade", listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId()), 200);
                    }
                }
                return true;
            }
        });
        btn_view_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideFiltros(visivel);
            }
        });
        id_opcao_emissao.setOnCheckedChangeListener(this);
        id_opcao_vencimento.setOnCheckedChangeListener(this);
        id_opcao_recebido.setOnCheckedChangeListener(this);
        id_opcao_reagendado.setOnCheckedChangeListener(this);
        id_opcao_vencimento.setChecked(false);
        id_opcao_vencimento.setChecked(true);
        id_opcao_recebido.setChecked(false);
        id_opcao_reagendado.setChecked(false);
        if (savedInstanceState != null) {
            id_opcao_emissao.setChecked(savedInstanceState.getString("strEmissao") == "0" ? true : false);
            id_opcao_vencimento.setChecked(savedInstanceState.getString("strVencimento") == "0" ? true : false);
            id_opcao_recebido.setChecked(savedInstanceState.getString("strRecebido") == "0" ? true : false);
            id_opcao_reagendado.setChecked(savedInstanceState.getString("strReagendado") == "0" ? true : false);
        }
        edt_data_final.setText(MascaraUtil.formataData(db.pegaDataAtual("SELECT date('now','localtime', '+1 day')")));
        btn_view_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAvancarOnClick(buscaCobranca.getQuery().toString());
            }
        });
        setListenerCobranca();
        actionModeCallback = new ActivityCobranca.ActionModeCallback();
        if (savedInstanceState != null) {
            edt_data_inicial.setText(savedInstanceState.getString("strDataIni"));
            edt_data_final.setText(savedInstanceState.getString("strDataFin"));
        } else {
            btnAvancarOnClick("");
        }
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            try {
            } catch (NullPointerException e) {
                return;
            }
            if (!recyclerView.canScrollVertically(1)) {
            }
        }
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indEstado", sp_estado.getSelectedItemPosition());
        outState.putInt("indRegiao", sp_regiao.getSelectedItemPosition());
        outState.putInt("indCidade", sp_cidade.getSelectedItemPosition());
        outState.putInt("indBairro", sp_bairro.getSelectedItemPosition());
        outState.putString("strPesquisa", buscaCobranca.getQuery().toString());
        outState.putString("strDataIni", edt_data_inicial.getText().toString());
        outState.putString("strDataFin", edt_data_final.getText().toString());
        outState.putString("strEmissao", id_opcao_emissao.isChecked() ? "0" : "1");
        outState.putString("strVencimento", id_opcao_vencimento.isChecked() ? "0" : "1");
        outState.putString("strRecebido", id_opcao_recebido.isChecked() ? "0" : "1");
        outState.putString("strReagendado", id_opcao_reagendado.isChecked() ? "0" : "1");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onResume() {
        id_android = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode >= 0 && resultCode == RESULT_OK) {
                if (requestCode == 100) {
                    String id_cidade = data.getStringExtra("result");
                    for (int i = 0; i <= listaMunicipio.size(); i++) {
                        if (listaMunicipio.get(i).getId().equalsIgnoreCase(id_cidade)) {
                            sp_cidade.setSelection(i);
                            if ( !cliqAtivo) {
                                cliqAtivo = true;
                            }
                            break;
                        }

                    }
                    if ( !cliqAtivo) {
                        cliqAtivo = true;
                    }
                    return;
                } else if (requestCode == 200) {
                    String id_bairro = data.getStringExtra("result");
                    for (int i = 0; i <= listaBairro.size(); i++) {
                        if (listaBairro.get(i).getId().equalsIgnoreCase(id_bairro)) {
                            sp_bairro.setSelection(i);
                            if ( !cliqAtivo ) {
                                cliqAtivo = true;
                            }
                            break;
                        }
                    }
                    if ( !cliqAtivo) {
                        cliqAtivo = true;
                    }
                    return;
                } else if (requestCode == 300) {
                    if (data.getBooleanExtra("result", true)) {
                        int position = Integer.parseInt(data.getStringExtra("position"));
                        try {
                            String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  MAX(FRES.DATA_EMISSAO) AS ULT_COMPRA, " +
                                    "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                                    "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                                    "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                                    "( SELECT MAX(USUARIO_DATA) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                                    "( SELECT MAX(DATA_REAGENDAMENTO) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                                    "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                                    "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                                    "WHERE FRES.ID_FAVORECIDO = " + listaFinanceiro.get(position).getId_favorecido() + " ";
                            SQL += "AND FRES.DEVEDOR = 'S'  GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
                            List<FinanceiroResumo> lista = new ArrayList<>();
                            lista = financeiroDAO.getLista(SQL, false, false);
                            listaFinanceiro.set(position, lista.get(0));
                            listaCobrancaAdapter.notifyDataSetChanged();
                        } catch (NumberFormatException | NullPointerException | CursorIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    setCliqAtivo(2000);
                    return;
                }

            }
            if (requestCode == 400) {
                setCliqAtivo(2000);
                final List<FinanceiroResumo> titulosSelecionados = listaCobrancaAdapter.getItensSelecionados();
                geraArquivoPDF(titulosSelecionados.get(0));
                return;
            }
            if( requestCode == 300 && resultCode == 0 ) {
                setCliqAtivo(200);
            }

        } catch (NullPointerException e) {
            setCliqAtivo(2000);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            setCliqAtivo(2000);
        }
    }

    private class QueryTextListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            try {
                if (query.trim().equals(""))
                    btnAvancarOnClick("");
                else
                    btnAvancarOnClick(query.toString());
            } catch (Exception e) {
            }
            return false;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }

    private void btnAvancarOnClick(String razaoSocial) {
        if (!id_opcao_emissao.isChecked() && !id_opcao_vencimento.isChecked()) {
            Toast.makeText(context, "Atenção!!\nFavor selecione um periodo(Emissão/Vencimento)", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataIni = null;
            String dataInicial = "-";
            String dataFinal = "-";
            if (!edt_data_inicial.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString());
                dataInicial = formatter.format(dataIni);
            }
            Date dataFin = null;
            if (!edt_data_final.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                dataFin = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_final.getText().toString());
                dataFinal = formatter.format(dataFin);
            }
            if (!edt_data_inicial.getText().toString().replaceAll("/", "").trim().isEmpty() &&
                    !edt_data_final.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                if (dataIni.after(dataFin)) {
                    Toast.makeText(context, "A data final não pode ser anterior a data inicial", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            String nome = razaoSocial.toString().isEmpty() ? "-" : razaoSocial;
            String documento = "-";
            String idConta = "0";
            String idMoeda = "0";
            String idBairro = "0";
            String idCidade = "0";
            String idRegiao = "0";
            try {
                idBairro = listaBairro.get(sp_bairro.getSelectedItemPosition()).getId().toString();
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                idCidade = listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId().toString();
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                idRegiao = listaRegiao.get(sp_regiao.getSelectedItemPosition()).getId().toString();
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            visivel = false;
            showHideFiltros(visivel);
            if (empresaParametro.getUtiliza_cobranca_offline().equalsIgnoreCase("S"))
                buscaDados(dataInicial, dataFinal, periodo, nome, documento, idConta, idMoeda, idBairro, idCidade, idRegiao, recebido, reagendado);
            //else
            //    sincronizaPeriodoAPI(dataInicial, dataFinal, periodo, nome, documento, idConta, idMoeda, idBairro, idCidade, idRegiao);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(context, "Atenção!!\nParametro inválido!", Toast.LENGTH_SHORT).show();
            return;
        } catch (ParseException e) {
            Toast.makeText(context, "Atenção!!\nA data inicial/final não foi informada, ou é inválida!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void buscaDados(String dataInicial, String dataFinal, String periodo, String razaoSocial, String documento, String idConta, String idMoeda,
                            String idBairro, String idCidade, String idRegiao, boolean recebido, boolean reagendado) {
        showHideProgressBar(true);
        String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  MAX(FRES.DATA_EMISSAO) AS ULT_COMPRA, " +
                "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TDOCS, " +
                "GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                "( SELECT MAX(USUARIO_DATA) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                "( SELECT MAX(DATA_REAGENDAMENTO) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO ";
        if (periodo.equalsIgnoreCase("E")) {
            if (!dataInicial.equalsIgnoreCase("-") || !dataFinal.equalsIgnoreCase("-")) {
                if (!dataInicial.equalsIgnoreCase("-") && !dataFinal.equalsIgnoreCase("-"))
                    SQL += "WHERE (FRES.DATA_EMISSAO >= '" + dataInicial + "' AND FRES.DATA_EMISSAO <= '" + dataFinal + "') ";
                else {
                    if (!dataInicial.equalsIgnoreCase("-"))
                        SQL += "WHERE FRES.DATA_EMISSAO >= '" + dataInicial + "' ";
                    if (!dataFinal.equalsIgnoreCase("-"))
                        SQL += "WHERE FRES.DATA_EMISSAO <= '" + dataFinal + "' ";
                }
            } else {
                SQL += "WHERE FRES.DATA_EMISSAO IS NOT NULL ";
            }
        } else {
            if (!dataInicial.equalsIgnoreCase("-") || !dataFinal.equalsIgnoreCase("-")) {
                if (!dataInicial.equalsIgnoreCase("-") && !dataFinal.equalsIgnoreCase("-"))
                    SQL += "WHERE (FRES.DATA_VENCIMENTO >= '" + dataInicial + "' AND FRES.DATA_VENCIMENTO <= '" + dataFinal + "') ";
                else {
                    if (!dataInicial.equalsIgnoreCase("-"))
                        SQL += "WHERE FRES.DATA_VENCIMENTO >= '" + dataInicial + "' ";
                    if (!dataFinal.equalsIgnoreCase("-"))
                        SQL += "WHERE FRES.DATA_VENCIMENTO <= '" + dataFinal + "' ";
                }
            } else {
                SQL += "WHERE FRES.DATA_VENCIMENTO IS NOT NULL ";
            }
        }
        if (!razaoSocial.equalsIgnoreCase("-"))
            SQL += "AND FRES.NOME_FAVORECIDO LIKE '%" + razaoSocial + "%' ";
        if (!documento.equalsIgnoreCase("-"))
            SQL += "AND FRES.DOCUMENTO LIKE '%" + documento + "%' ";
        if (!idBairro.equalsIgnoreCase("0")) {
            if (!idCidade.equalsIgnoreCase("0"))
                SQL += "AND (FRES.ID_CIDADE = " + idCidade + " AND FRES.ID_BAIRRO = " + idBairro + ") ";
            else
                SQL += "AND FRES.ID_BAIRRO = " + idBairro + " ";

        } else {
            if (!idCidade.equalsIgnoreCase("0"))
                SQL += "AND FRES.ID_CIDADE = " + idCidade;
        }
        if (!idRegiao.equalsIgnoreCase("0"))
            SQL += " AND FRES.ID_REGIAO = " + idRegiao;
        SQL += " AND FRES.DEVEDOR = 'S' GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
        listaFinanceiro = financeiroDAO.getLista(SQL, recebido, reagendado);
        if (listaFinanceiro.size() <= 0)
            Toast.makeText(ActivityCobranca.this, "Atenção!\n Nemhum registro localizado!!", Toast.LENGTH_SHORT).show();
        setRecyclerView(listaFinanceiro);
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
                    hideKeyboard(ActivityCobranca.this);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showHideProgressBar(boolean b) {
    }

    private void showHideFiltros(boolean b) {
        ViewGroup.LayoutParams params = card_view_menu.getLayoutParams();

        if (visivel) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_down_white);
            visivel = !visivel;
        } else {
            params.height = 0;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_up_white);
            visivel = !visivel;
        }
        card_view_menu.setLayoutParams(params);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.id_opcao_emissao:
                if (isChecked) {
                    periodo = "E";
                    id_opcao_vencimento.setChecked(!isChecked);
                }
                break;
            case R.id.id_opcao_vencimento:
                if (isChecked) {
                    periodo = "V";
                    id_opcao_emissao.setChecked(!isChecked);
                }
                break;
            case R.id.id_opcao_recebido:
                if (isChecked)
                    recebido = true;
                else
                    recebido = false;
                break;
            case R.id.id_opcao_reagendado:
                if (isChecked)
                    reagendado = true;
                else
                    reagendado = false;
                break;
        }
    }

    private void sincronizaClienteAPI(String idCadastro, int position) {  //
        if (position < 0) {
            limpaSelecao("Falha ao definir o registro para ser sincronizado!\nSelecione o registro e tente novamente!");
            setCliqAtivo(4000);
            return;
        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            limpaSelecao("Falha ao definir a versão do aplicativo para acesso a conexão!\n"+ e.toString() );
            setCliqAtivo(4000);
            return;
        }
        if ( !usuarioIsLogado() ) {
            setCliqAtivo(4000);
            return;
        }
        String versaoApp = pInfo.versionName.substring(0, 5);
        Toast toast = Toast.makeText(context, "Aguarde.... \ntitulo/cliente em sincronização!.\nV-APP-API" + versaoApp, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        showHideProgressBar(true);
        getConfiguracao();
        if (!configuracao.getId().isEmpty()) {
            Call<Sincronia> call = getRotasAPI(1,idCadastro, configuracao, versaoApp);
            if ( call == null) {
                setCliqAtivo(4000);
                return;
            }
            call.enqueue(new Callback<Sincronia>() {
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    if (response.code() == 200) {
                        final Sincronia sincronia1 = response.body();
                        db.deleteDados("TBL_WEB_RECEBER_CAB", "STATUS = ? AND EXCLUIDO = ? AND ID_ANDROID != ? AND ID_CADASTRO = ? AND ID_LOTE IS NOT NULL", new String[]{"A", "N", id_android, idCadastro});
                        addUpdateRecebimentos(sincronia1.getListaRecebimentos());
                        addUpdateCobrancas(sincronia1.getListaCobranca());
                        setCliqAtivo(4000);
                    } else {
                        setCliqAtivo(4000);
                        limpaSelecao("Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.");
                    }
                }

                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    try {
                        toast.cancel();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty())
                            sincronizaClienteAPI2(idCadastro, position);
                        else {
                            setCliqAtivo(4000);
                            limpaSelecao("Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.\n" + t.getMessage());
                        }
                    } catch ( NullPointerException e) {
                        setCliqAtivo(4000);
                        limpaSelecao("Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.\n" + t.getMessage() + "\n" + e.getMessage());
                    }
                }
            });
        } else {
            setCliqAtivo(4000);
            limpaSelecao("Parametros para conexão não cadastrados!");
        }
    }

    private void sincronizaClienteAPI2(String idCadastro, int position) {  //
        if (position < 0) {
            limpaSelecao("Falha ao definir o registro para ser sincronizado!!\nSelecione o registro e tente novamente!!");
            setCliqAtivo(4000);
            return;
        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            limpaSelecao("Falha ao definir a versão do aplicativo para acesso a conexão!!");
            Toast.makeText(ActivityCobranca.this, "Falha ao definir a versão do aplicativo para acesso a conexão!!", Toast.LENGTH_LONG).show();
            setCliqAtivo(4000);
            return;
        }
        if ( !usuarioIsLogado() ) {
            setCliqAtivo(4000);
            return;
        }
        String versaoApp = pInfo.versionName.substring(0, 5);
        Toast toast = Toast.makeText(context, "Aguarde.... \ntitulo/cliente em sincronização!!.\nV-APP-API!!" + versaoApp, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        getConfiguracao();
        if (!configuracao.getId().isEmpty()) {
            Call<Sincronia> call = getRotasAPI(2, idCadastro, configuracao, versaoApp);
            if ( call == null) {
                setCliqAtivo(4000);
                return;
            }
            call.enqueue(new Callback<Sincronia>() {
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    if (response.code() == 200) {
                        final Sincronia sincronia2 = response.body();
                        db.deleteDados("TBL_WEB_RECEBER_CAB", "STATUS = ? AND EXCLUIDO = ? AND ID_ANDROID != ? AND ID_CADASTRO = ? AND ID_LOTE IS NOT NULL", new String[]{"A", "N", id_android, idCadastro});
                        addUpdateRecebimentos( sincronia2.getListaRecebimentos());
                        addUpdateCobrancas(sincronia2.getListaCobranca());
                        setCliqAtivo(4000);
                    } else {
                        setCliqAtivo(4000);
                        limpaSelecao("Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!.");
                    }
                }
                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    setCliqAtivo(4000);
                    limpaSelecao("Não foi possivel sincronizar com o servidor!! Por favor verifique sua conexão e tente novamente!!" + t.getMessage());
                }
            });
        } else {
            setCliqAtivo(4000);
            limpaSelecao( "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!!");
        }
    }

    private void setCliqAtivo(long delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                cliqAtivo = !cliqAtivo;
                System.gc();
            }
        }, delay);
    }

    private Call<Sincronia> getRotasAPI(int idRota, String idCadastro, Configuracao configuracao, String versaoApp) {
        Sincronia sincronia = new Sincronia(true);
        String id_android = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        sincronia.setListaRecebimentos(webReceberCabDAO.getLista("SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = '" + idCadastro + "' AND ID_ANDROID = '" + id_android + "'"));  //N nao sincronizado
        sincronia.setListaCadastroFinanceiroResumo(new ArrayList<>());

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                //return apiRotas.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas.getAddCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                else
                    return apiRotas.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + versaoApp + "/ws/";
                    Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();

                    Api.context = context;
                    apiRotas = Api.buildRetrofit(true);
                    if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                        return apiRotas.getAddCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                    else
                        return apiRotas.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                } catch (NullPointerException ex) {
                    showHideProgressBar(false);
                    Toast.makeText(ActivityCobranca.this, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                showHideProgressBar(false);
                Toast.makeText(ActivityCobranca.this, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if(idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas2.getAddCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                else
                    return apiRotas2.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                //return apiRotas2.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp.substring(0, 5) + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();

                    apiRotas2 = Api.buildRetrofit2(true);
                    if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                        return apiRotas2.getAddCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                    else
                        return apiRotas2.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                    //return apiRotas2.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                } catch (NullPointerException ex) {
                    showHideProgressBar(false);
                    Toast.makeText(ActivityCobranca.this, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                showHideProgressBar(false);
                Toast.makeText(ActivityCobranca.this, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n"+ e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    private boolean usuarioIsLogado() {
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(context));
        try {
            if (UsuarioHelper.getUsuario() == null) {
                limpaSelecao("Falha ao definir o usuário logado no aplicativo!");
                return false;
            }
        } catch (NullPointerException e) {
            limpaSelecao( "Falha ao definir o usuário logado no aplicativo!\n"+ e.toString());
            return false;
        }
        return true;
    }

    private void limpaSelecao(String mensagem){
        try {
            actionMode.finish();
            actionMode = null;
            positionSelected = -1;
            listaCobrancaAdapter.clearSelections();
            showHideProgressBar(false);
            if (!mensagem.isEmpty())
                Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            actionMode = null;
            positionSelected = -1;
            listaCobrancaAdapter.clearSelections();
            showHideProgressBar(false);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void addUpdateCobrancas(List<FinanceiroResumo> listaCobrancas) {
        try {
            if (listaCobrancas.size() > 0) {
                if (listaCobrancas.size() == 1 && listaCobrancas.get(0).getDevedor().equalsIgnoreCase("N")) {
                    financeiroDAO.delete(listaCobrancas.get(0));
                    financeiroDAO.add(listaCobrancas.get(0));
                    listaFinanceiro.remove(positionSelected);
                    listaCobrancaAdapter.notifyDataSetChanged();
                    util.showMsgAlerta("Não foram localizados titulos pendentes para o cliente \n" + "O cadastro foi atualizado para a lista de cliente(s) sem débito!!", ActivityCobranca.this);
                } else {
                    financeiroDAO.delete(listaCobrancas.get(0));
                    for (FinanceiroResumo financeiro : listaCobrancas)
                        financeiroDAO.add(financeiro);
                    String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  MAX(FRES.DATA_EMISSAO) AS ULT_COMPRA, " +
                            "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                            "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                            "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                            "( SELECT USUARIO_DATA FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                            "( SELECT DATA_REAGENDAMENTO FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                            "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                            "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                            "WHERE FRES.ID_FAVORECIDO = " + listaCobrancas.get(0).getId_favorecido() + " ";
                    SQL += "AND FRES.DEVEDOR = 'S'  GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
                    List<FinanceiroResumo> lista1 = new ArrayList<>();
                    lista1 = financeiroDAO.getLista(SQL, false, false);

                    if ((recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && !reagendado /*&&  lista1.get(0).getData_reagendamento() != null*/) {
                        listaFinanceiro.remove(positionSelected);
                    } else if ((reagendado && lista1.get(0).getData_reagendamento() == null) && !recebido) {
                        listaFinanceiro.remove(positionSelected);
                    } else if ((recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && (reagendado && lista1.get(0).getData_reagendamento() == null)) {
                        listaFinanceiro.remove(positionSelected);
                    } else {
                        listaFinanceiro.set(positionSelected, lista1.get(0));
                    }

                    //listaFinanceiro.set(positionSelected, lista.get(0));
                    listaCobrancaAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Titulo/cliente sincronizado com o servidor!!.", Toast.LENGTH_SHORT).show();
                }
            } else {
                financeiroDAO.delete(listaFinanceiro.get(positionSelected));
                util.showMsgAlerta("Não foram localizados titulos para o cliente selecionado!!", ActivityCobranca.this);
                listaFinanceiro.remove(positionSelected);
                listaCobrancaAdapter.notifyDataSetChanged();
            }
            limpaSelecao("");
        } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
            limpaSelecao("Falha ao sincronizar com o servidor!!.\n" + e.toString());
        } catch ( Exception e) {
            limpaSelecao("Falha ao sincronizar com o servidor!!.\n" + e.toString());
        }

        System.gc();
    }

    private void addUpdateRecebimentos(List<ReceberCab> listaRecebimentos) {
        if (listaRecebimentos.size() > 0) {
            for (ReceberCab receber : listaRecebimentos) {
                try {
                    if (receber.getStatus().equalsIgnoreCase("A") && receber.getExcluido().equalsIgnoreCase("N")) {
                        if (receber.getId_android().equalsIgnoreCase(id_android)) {
                            webReceberCabDAO.update(receber);
                            Toast.makeText(context, "Cobrança enviada/sincronizada com sucesso\nLista atualizada no aplicativo!!.", Toast.LENGTH_SHORT).show();
                        }else {
                            webReceberCabDAO.add(receber);
                            Toast.makeText(context, "Cobrança sincronizada com sucesso\nLista atualizada no aplicativo!!.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (receber.getStatus().equalsIgnoreCase("R") || receber.getExcluido().equalsIgnoreCase("S")) {
                        webReceberCabDAO.deleteRecebimento(receber, new String[]{receber.getId_web_receber(), "A", receber.getId_android()});
                        webReceberCabDAO.deleteRecebimento(receber, new String[]{receber.getId_web_receber(), "N", receber.getId_android()});
                        Toast.makeText(context, "Cobrança sincronizada com sucesso\nLista atualizada/revisada no aplicativo!!.", Toast.LENGTH_SHORT).show();

                    } else if( receber.getStatus().equalsIgnoreCase("N")) {
                        Toast.makeText(context, "Falha na sincronização da cobrança\nFavor tentar novamente!!.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(context, "Falha na sincronização da cobrança\nFavor tentar novamente!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Falha na sincronização da cobrança\nFavor tentar novamente!!\n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void deleteRecebimento(FinanceiroResumo financeiro, int position) {
        if (position < 0) {
            Toast.makeText(ActivityCobranca.this, "Falha ao definir o registro para ser excluido!!", Toast.LENGTH_SHORT).show();
            return;
        }
        int positionSelected = position;
        ReceberDAO receberDAO = new ReceberDAO(db);
        String SQL = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = " + financeiro.getId_favorecido() + " AND ID_ANDROID = '" + id_android + "' AND (STATUS = 'N' OR STATUS = 'A')";
        List<ReceberCab> lista = new ArrayList<>();
        lista = receberDAO.getLista(SQL);
        if (lista.size() > 0) {
            for ( int i = 0; i < lista.size(); i++) {
                if (receberDAO.delete(lista.get(i), new String[]{lista.get(i).getId_web_receber(),  lista.get(i).getId_android()})) {
                    Toast.makeText(ActivityCobranca.this, "Registro(s) excluido(s) com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
            //SQL = "SELECT FRES.*, SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
            SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  MAX(FRES.DATA_EMISSAO) AS ULT_COMPRA, SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                    "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                    "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                    "( SELECT USUARIO_DATA FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                    "( SELECT DATA_REAGENDAMENTO FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                    "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                    "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                    "WHERE FRES.ID_FAVORECIDO = " + financeiro.getId_favorecido() + " ";
            SQL += "AND FRES.DEVEDOR = 'S'  GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
            List<FinanceiroResumo> lista1 = new ArrayList<>();
            lista1 = financeiroDAO.getLista(SQL, false, false);
            try {
                if (lista1.size() > 0) {
                    /*
                    if ( recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f || reagendado &&  lista1.get(0).getData_reagendamento() == null ) {
                        listaFinanceiro.remove(position);
                    }else {
                        listaFinanceiro.set(position, lista1.get(0));
                    }
                     */
                    if ((recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && !reagendado /*&&  lista1.get(0).getData_reagendamento() != null*/) {
                        listaFinanceiro.remove(positionSelected);
                    } else if ((reagendado && lista1.get(0).getData_reagendamento() == null) && !recebido) {
                        listaFinanceiro.remove(positionSelected);
                    } else if ((recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && (reagendado && lista1.get(0).getData_reagendamento() == null)) {
                        listaFinanceiro.remove(positionSelected);
                    } else {
                        listaFinanceiro.set(positionSelected, lista1.get(0));
                    }
                    //listaCobrancaAdapter.notifyDataSetChanged();
                }
            } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ActivityCobranca.this, "Nenhum registro foi excluido!!", Toast.LENGTH_SHORT).show();
        }
        actionMode.finish();
        actionMode = null;
        positionSelected = -1;
        listaCobrancaAdapter.clearSelections();
    }

    private void setRecyclerView(List<FinanceiroResumo> lista) {
        int numColsList = 1;
        GridLayoutManager layoutManager;
        int orientation = this.getResources().getConfiguration().orientation;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaCobrancaAdapter = new ListaCobrancaAdapter(ActivityCobranca.this, lista, listenerCobranca);
        if (configuracao.getGira_tela_cobranca().equalsIgnoreCase("S")) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        layoutManager = new GridLayoutManager(this, 2);
                        numColsList = 2;
                        break;
                    default:
                        layoutManager = new GridLayoutManager(this, 1);
                }
            } else {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        layoutManager = new GridLayoutManager(this, 3);
                        numColsList = 3;
                        break;
                    case Configuration.SCREENLAYOUT_SIZE_SMALL:
                        layoutManager = new GridLayoutManager(this, 1);
                        break;
                    default:
                        layoutManager = new GridLayoutManager(this, 2);
                        numColsList = 2;
                }
            }
        } else {
            switch (screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    layoutManager = new GridLayoutManager(this, 2);
                    numColsList = 2;
                    break;
                default:
                    layoutManager = new GridLayoutManager(this, 1);
                    break;
            }
        }
        recyclerView.setHasFixedSize(true);
        //layoutManager = new GridLayoutManager( this, Common.NUM_OF_COL_PORT);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        int finalNumColsList = numColsList;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                if (listaCobrancaAdapter != null) {
                    listaCobrancaAdapter.setNumCols(finalNumColsList);
                    switch (listaCobrancaAdapter.getItemViewType(i)) {
                        case 1:
                            return 1;
                        case 0:
                            return finalNumColsList;
                        default:
                            return -1;
                    }
                } else
                    return -1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        while (recyclerView.getItemDecorationCount() > 0)
            recyclerView.removeItemDecorationAt(0);
        recyclerView.setAdapter(listaCobrancaAdapter);
        listaCobrancaAdapter.notifyDataSetChanged();
        setListenerCobranca();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setListenerCobranca() {
        listenerCobranca = new ListaCobrancaAdapter.CobrancaAdapterListener() {
            @Override
            public void onClickListener(int position) {
                if ( cliqAtivo) {
                    cliqAtivo = !cliqAtivo;
                    try {
                        if (Float.parseFloat(listaFinanceiro.get(position).getValor_total()) - Float.parseFloat(listaFinanceiro.get(position).getValor_recebimento()) <= 0.00f) {
                            Toast.makeText(ActivityCobranca.this, "Cliente não tem valores/titulos pendentes!", Toast.LENGTH_SHORT).show();
                            setCliqAtivo(1000);
                            return;
                        }
                    } catch (NullPointerException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                    startActivityForResult(new Intent(ActivityCobranca.this, ActivityCobrancaRecebimento.class)
                                    .putExtra("position", String.valueOf(position))
                                    .putExtra("id_favorecido", listaFinanceiro.get(position).getId_favorecido())
                                    .putExtra("nome_favorecido", listaFinanceiro.get(position).getNome_favorecido())
                                    .putExtra("valor_pendente", listaFinanceiro.get(position).getValor_debito())
                                    .putExtra("total_docs", String.valueOf(listaFinanceiro.get(position).getTotal_docs()))
                                    .putExtra("cpf_cnpj", String.valueOf(listaFinanceiro.get(position).getCpf_cnpj()))
                                    .putExtra("devedor", "S")
                            , 300);
                }
            }

            @Override
            public void onClickNovoPedido(int position) {
                novoPedido(position);
            }


            @Override
            public void onLongClickListener(int position) {
                enableActionMode(position);
            }

            @Override
            public void onGPSClickListener(int position) {
                try {
                    if (listaFinanceiro.get(position).getEndereco().equalsIgnoreCase("Nenhum endereço informado!") || listaFinanceiro.get(position).getEndereco().isEmpty())
                        util.showMsgAlerta("Nenhum endereço informado!", ActivityCobranca.this);
                    else
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + listaFinanceiro.get(position).getEndereco())).setPackage("com.google.android.apps.maps"));
                } catch (NullPointerException e) {
                } catch (Exception e) {
                }
            }

            @Override
            public void onTelefoneClickListener(int position) {
                fazerChamada(listaFinanceiro.get(position).getTelefone_principal(), listaFinanceiro.get(position).getNome_favorecido().toUpperCase());
            }
        };
    }

    public void novoPedido(int position) {
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        try {
            Cliente cliente = new ClienteDAO(db).getLista("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO_SERVIDOR = " + listaFinanceiro.get(position).getId_favorecido()).get(0);
            ClienteHelper.setCliente(cliente);
            if (ClienteHelper.getCliente().getIdCategoria() <= 0) {
                Toast.makeText(this, "Este cliente não tem uma categoria definida", Toast.LENGTH_SHORT).show();
                return;
            } else if (ClienteHelper.getCliente().getAtivo().equalsIgnoreCase("N")) {
                Toast.makeText(this, "Este cliente esta inátivo/bloqueado!", Toast.LENGTH_SHORT).show();
                return;
            }
            ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
            Pedido2 pedido2 = new Pedido2();
            activityPedidoMain.pegaCliente(ClienteHelper.getCliente());
            pedido2.pegaCliente(ClienteHelper.getCliente());
            startActivity(new Intent(ActivityCobranca.this, ActivityPedidoMain.class));

        } catch (NullPointerException | NumberFormatException | CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fazerChamada(String telefone, String nome) {
        try {
            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty())
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11) {
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(ActivityCobranca.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCobranca.this);
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                    textView.setText("Deseja ligar para " + nome + " usando o número " + telefone + " ?");
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
                            final Intent intent = new Intent(Intent.ACTION_DIAL);
                            if (telefone.replaceAll("[^0-9]", "").length() == 10)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else if (telefone.replaceAll("[^0-9]", "").length() == 11)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else
                                intent.setData(Uri.parse("tel:" + telefone));
                            alertDialog.dismiss();
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                } else
                    util.showMsgAlerta("Este numero de Telefone não é valido!", ActivityCobranca.this);
            else
                util.showMsgAlerta("Nenhum numero de telefone informado!", ActivityCobranca.this);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            geraPDFRecibo = menu.findItem(R.id.action_pdf_pedido);
            geraPDFEmail = menu.findItem(R.id.action_pdf_pedido_email);
            sincroniza = menu.findItem(R.id.action_mode_menu_pedido_pendente);
            deleteRecebimento = menu.findItem(R.id.action_delete);
          //  deleteAgendamento = menu.findItem(R.id.action_delete);
            menu.findItem(R.id.action_mode_menu_pedido_pendente).setVisible(false);
            menu.findItem(R.id.action_duplica_pedido).setVisible(false);
            menu.findItem(R.id.action_pdf_pedido_email).setVisible(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            final List<FinanceiroResumo> titulosSelecionados = listaCobrancaAdapter.getItensSelecionados();
            switch (item.getItemId()) {
                case R.id.action_pdf_pedido:
                    geraArquivoPDF(titulosSelecionados.get(0));
                    return true;
                case R.id.action_mode_menu_pedido_pendente:
                    if ( cliqAtivo) {
                        cliqAtivo = !cliqAtivo;
                        sincronizaClienteAPI(titulosSelecionados.get(0).getId_favorecido(), positionSelected);
                    } else {
                        Toast.makeText(context, "Aguarde a finalização do evento", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.action_delete:
                    showAlertDelete(titulosSelecionados.get(0), positionSelected);
                    return true;
                default:
                    return true;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            listaCobrancaAdapter.clearSelections();
            actionMode = null;
            positionSelected = -1;
            //onRefresh();
        }
    }

    private void enableActionMode(int position) {

        if (actionMode == null)
            actionMode = startSupportActionMode(actionModeCallback);
        positionSelected = position;
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        listaCobrancaAdapter.toggleSelection(position);
        //int count = listaPedidoAdapter.getSelectedItemCount();
        if (listaCobrancaAdapter.getSelectedItemCount() != 1) {
            positionSelected = -1;
            actionMode.finish();
        } else {
            try {
                if (Float.parseFloat(listaFinanceiro.get(position).getValor_recebimento()) > 0.00f) {
                    geraPDFRecibo.setVisible(true);
                    sincroniza.setVisible(true);
                    String sql = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = '" + listaFinanceiro.get(position).getId_favorecido() + "' AND STATUS = 'N' AND ID_ANDROID = '" + id_android + "'";
                    List<ReceberCab> listaRecebimentos = webReceberCabDAO.getLista(sql);
                    if (listaRecebimentos.size() > 0)
                        deleteRecebimento.setVisible(true);
                    //else
                    //    delete.setVisible(false);
                    return;
                }
                if (listaFinanceiro.get(position).getData_reagendamento() != null) {
                    geraPDFRecibo.setVisible(false);
                    sincroniza.setVisible(true);
                    //String sql = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = '" + listaFinanceiro.get(position).getId_favorecido() + "' AND STATUS = 'N' AND ID_ANDROID = '" + id_android + "'";
                    String sql = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = '" + listaFinanceiro.get(position).getId_favorecido() + "' AND ID_ANDROID = '" + id_android + "'";
                    List<ReceberCab> listaRecebimentos = webReceberCabDAO.getLista(sql);
                    if (listaRecebimentos.size() > 0)
                        deleteRecebimento.setVisible(true);
                    else
                        deleteRecebimento.setVisible(false);
                    return;
                }
                 geraPDFRecibo.setVisible(false);
                 sincroniza.setVisible(true);
                 deleteRecebimento.setVisible(false);


                actionMode.setTitle(String.valueOf(listaCobrancaAdapter.getSelectedItemCount()));
                actionMode.invalidate();
            } catch (NullPointerException | NumberFormatException e) {
                geraPDFRecibo.setVisible(false);
                geraPDFEmail.setVisible(false);

            }

        }
    }

    private void geraArquivoPDF(FinanceiroResumo financeiroResumo) {
        EmpresaParametro empresa = getEmpresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        if (empresa == null) {
            Toast.makeText(ActivityCobranca.this, "Falha ao obter as informações da empresa , favor entrar em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
            return;
        }
        PDFReciboUtil pdfReciboUtil = new PDFReciboUtil(financeiroResumo, empresa, ActivityCobranca.this, db);
        File filePDF = pdfReciboUtil.criandoPdf();
        if (filePDF == null) {
            return;
        }
        if (Build.VERSION.SDK_INT > +Build.VERSION_CODES.N) {
            try {
                if (filePDF == null)
                    return;
                Uri contentUri = FileProvider.getUriForFile(ActivityCobranca.this, getApplicationContext().getPackageName() + ".provider", filePDF/*pdfPedidoUtil.criandoPdf()*/);
                startActivity(new Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        .setDataAndType(contentUri, "application/pdf"));
                try {
                    listaCobrancaAdapter.clearSelections();
                    actionMode = null;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                showAlertInfoApp();
            } catch (Exception e) {
                Toast.makeText(ActivityCobranca.this, "Aplicativo sem permissão para criar o arquivo! De permissão de acesso manualmente,ou entre em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                if (filePDF == null)
                    return;
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW)
                        .setDataAndType(Uri.fromFile(filePDF), "application/pdf"), "Abrir arquivo"));
                try {
                    listaCobrancaAdapter.clearSelections();
                    actionMode = null;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                showAlertInfoApp();
            } catch (Exception e) {
                Toast.makeText(ActivityCobranca.this, "Aplicativo sem permissão para criar o arquivo! De permissão de acesso manualmente,ou entre em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private EmpresaParametro getEmpresa(String id_empresa) {
        EmpresaParametro empresa = null;
        try {
            empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + id_empresa + "'");
            if (empresa == null) {
                empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO");
                if (empresa == null) {
                    Toast.makeText(ActivityCobranca.this, "Falha ao obter as informações da empresa , favor entrar em contato com o suporte técnico!", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        } catch (NullPointerException e) {
            empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        } catch (Exception e) {
            empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        }
        return empresa;
    }

    private void showAlertDelete(FinanceiroResumo financeiroResumo, int positionSelected) {
        String SQL = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = " + financeiroResumo.getId_favorecido();
        List<ReceberCab> lista = new ArrayList<>();
        lista = new ReceberDAO(db).getLista(SQL);
        if (lista.size() > 0) {
            String msg = "";
             String msgTitulos = "";
             msg = "Confirma a exclusao do(s) titulo(s)? Total de titulo(s) " + lista.size();
             for (int i = 0; i < lista.size(); i++)
                 msgTitulos += "\nLote " + lista.get(i).getId_lote() + "\nStatus " + lista.get(i).getStatus()
                       + "\nChave APP " + lista.get(i).getChave_receber_app()    + "\nID ANDROID " + lista.get(i).getId_android() + "\nID " + Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID) ;
            msg = msg + msgTitulos;
            new AlertDialog.Builder(ActivityCobranca.this)
                    .setTitle("Atenção!")
                    .setMessage(msg )
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            showMsgDialogDelete(financeiroResumo, positionSelected);
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(ActivityCobranca.this, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                            limpaSelecao("Ação cancelada!");
                        }
                    })
                    .show();
        } else
            limpaSelecao("Ação cancelada!");

    }

    public void showMsgDialogDelete(FinanceiroResumo financeiroResumo, int positionSelected) {
        ViewGroup viewGroup = ((Activity) context).findViewById(R.id.rootx);
        View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.dialog_alert_permissao, viewGroup, true);
        TextInputEditText edtCodigoAcesso = dialogView.findViewById(R.id.edtPwd);
        new AlertDialog.Builder(ActivityCobranca.this)
                .setTitle("RCK SISTEMAS ESPECIFICOS")
                .setMessage("Informe o código de acesso para a opção selecionada!")
                .setView(dialogView)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (edtCodigoAcesso.getText().toString().trim().equalsIgnoreCase("102030")) {
                            deleteRecebimento(financeiroResumo, positionSelected);
                        }else {
                            showMsgDialogDelete(financeiroResumo, positionSelected);
                            Toast.makeText(context, "Código de acesso inválido!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(context, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
        edtCodigoAcesso.requestFocus();
    }

    private void showAlertInfoApp() {
        new AlertDialog.Builder(ActivityCobranca.this)
                .setTitle("RCK SISTEMAS ESPECIFICOS!")
                .setMessage("Aplicativo sem permissão para criação/armazenamento de arquivos!\n Gostaria de analisar/conceder permissão para de armazenamento para este aplicativo? ")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)), 400);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(ActivityCobranca.this, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


}