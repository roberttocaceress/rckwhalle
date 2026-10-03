package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.BairroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ContasBancoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MoedaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MunicipioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ReceberDAO;
import com.example.rcksuporte05.rcksistemas.DAO.RegiaoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCobrancaAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaSemCobrancaAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.model.ReceberCab;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.PDFReciboUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

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

public class ActivitySemCobranca extends AppCompatActivity  implements CompoundButton.OnCheckedChangeListener{
    ActivitySemCobranca.ActionModeCallback actionModeCallback;

    @BindView(R.id.tbl_cobranca)
    Toolbar toolbar;

    @BindView(R.id.buscaCobranca)
    SearchView buscaCobranca;


    @BindView(R.id.recycle_cobranca)
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

    /*
    @BindView(R.id.id_opcao_emissao)
    CheckBox id_opcao_emissao;

    @BindView(R.id.id_opcao_vencimento)
    CheckBox id_opcao_vencimento;

    @BindView(R.id.id_opcao_recebido)
    CheckBox id_opcao_recebido;

    @BindView(R.id.id_opcao_reagendado)
    CheckBox id_opcao_reagendado;
    */


    //@BindView(R.id.edt_nome_favorecido)
    //EditText edt_nome_favorecido;

    //@BindView(R.id.edt_numero_documento)
    //EditText edt_numero_documento;

    //@BindView(R.id.edt_vencido_de)
    //EditText edt_vencido_de;

    //@BindView(R.id.edt_vencido_a)
    //EditText edt_vencido_a;

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


    /*
    @BindView(R.id.edt_data_final)
    EditText edt_data_final;

    @BindView(R.id.edt_data_inicial)
    EditText edt_data_inicial;
    */
    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db) ;
    private PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private MunicipioDAO municipioDAO = new MunicipioDAO(db);
    private ReceberDAO webReceberCabDAO = new ReceberDAO(db);
    FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);

    private EmpresaParametro empresaParametro;

    PackageInfo pInfo = null;
    Context context;
    String periodo = "";
    boolean visivel = true;
    boolean recebido = false;
    boolean reagendado = false;

    private ListaSemCobrancaAdapter listaCobrancaAdapter;
    private ListaSemCobrancaAdapter.CobrancaAdapterListener listenerCobranca;
    private static List<FinanceiroResumo> listaFinanceiro = new ArrayList<>();
    private List<Municipio> listaMunicipio = new ArrayList<>();
    private Utilitaria util = new Utilitaria();


    List<Generico> listaConta;
    List<Generico> listaBairro;
    List<Generico> listaRegiao;
    List<Moeda> listaMoeda;

    private MenuItem geraPDFRecibo;
    private MenuItem geraPDFEmail;
    private MenuItem sincroniza;
    private MenuItem delete;
    private ActionMode actionMode;
    String id_android;
    int positionSelected = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_cobranca);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.tbl_cobranca);
        toolbar.setTitle("Lista de cliente");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buscaCobranca.setOnQueryTextListener(new ActivitySemCobranca.QueryTextListener());
        buscaCobranca.setIconifiedByDefault(true);
        context = this;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            btn_view_menu.setTooltipText("Toque para visualizar/esconder menu para definir o tipo de relatório!");
        }
        id_android = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        showHideFiltros(visivel);


        sp_estado.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf)));
        empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        for (int i = 0; i < sp_estado.getAdapter().getCount(); i++) {
            if ( sp_estado.getAdapter().getItem(i).toString().equalsIgnoreCase(empresaParametro.getEndereco_uf()))
                sp_estado.setSelection(i);
        }

        sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                    sp_cidade.setSelection(0);
                    for ( int i = 0; i < listaMunicipio.size(); i++) {
                        //if ( listaMunicipio.get(i).getId().equalsIgnoreCase(empresaParametro.getId_cidade()) /*id-municipo tbl empresa parametro   */)
                        //    sp_cidade.setSelection(i);
                        //
                    }
                } catch (CursorIndexOutOfBoundsException | NullPointerException ex) {
                } catch (Exception ex) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                    sp_bairro.setAdapter( new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaBairro) );

                } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
                } catch ( Exception ex) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        //5002704
        /*try {
            BairroDAO bairroDAO = new BairroDAO(db);
            listaBairro = new ArrayList<>();
            Generico generico = new Generico();
            generico.setId("0");
            generico.setNome("TODOS/TOQUE P SELECIONAR");
            generico.setAtivo("S");
            listaBairro.add(generico);
            listaBairro.addAll(bairroDAO.getLista("SELECT * FROM TBL_CADASTRO_BAIRRO WHERE ID_CIDADE = " + listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId() + " ORDER BY NOME_BAIRRO"));
            sp_bairro.setAdapter( new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaBairro) );

        } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
        } catch ( Exception ex) {
        }
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
            sp_regiao.setAdapter( new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaRegiao) );
            if ( listaRegiao.size() > 1) {
                for ( int i = 0; i < listaRegiao.size();i++) {
                    if ( listaRegiao.get(i).getId_generico().equalsIgnoreCase(UsuarioHelper.getUsuario().getId_quando_vendedor())) {
                        sp_regiao.setSelection(i);
                        break;
                    }
                }
            }
        } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
            ex.printStackTrace();
        } catch ( Exception ex) {
            ex.printStackTrace();
        }

        try {
            ContasBancoDAO contasBancoDAO = new ContasBancoDAO(db);
            listaConta = new ArrayList<>();
            Generico generico = new Generico();
            generico.setId("0");
            generico.setNome("TODAS");
            generico.setAtivo("S");
            listaConta.add(generico);
            listaConta.addAll(contasBancoDAO.getLista("SELECT * FROM TBL_CONTAS_BANCO;"));
            sp_conta_bancaria.setAdapter( new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaConta) );

        } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
        } catch ( Exception ex) {
        }

        try {
            MoedaDAO moedaDAO = new MoedaDAO(db);
            listaMoeda = new ArrayList<>();
            Moeda moeda = new Moeda();
            moeda.setId_moeda("0");
            moeda.setNome_moeda("TODAS");
            listaMoeda.add(moeda);
            try {
                listaMoeda.addAll(moedaDAO.getLista("SELECT * FROM TBL_MOEDA;"));
            } catch ( NullPointerException|ArrayIndexOutOfBoundsException e) {

            }
            sp_moeda.setAdapter( new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaMoeda) );

        } catch ( CursorIndexOutOfBoundsException | NullPointerException ex) {
        } catch ( Exception ex) {
        }
        /*
        edt_data_final.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if ( !id_opcao_emissao.isChecked() && !id_opcao_vencimento.isChecked()) {
                        Toast.makeText( context, "Atenção!!\nFavor selecione um periodo(Emissão/Vencimento)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Calendar dataInicial = new GregorianCalendar();
                    if ( !edt_data_inicial.getText().toString().replaceAll("/", "").trim().isEmpty()) {
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
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if ( !id_opcao_emissao.isChecked() &&  !id_opcao_vencimento.isChecked() ) {
                        Toast.makeText( context, "Atenção!!\nFavor selecione um periodo(Emissão/Vencimento)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    mostraDatePickerDialog(context, edt_data_inicial);
                }
                return false;
            }
        });
        */

        sp_cidade.setClickable(false);
        sp_cidade.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Intent intent = new Intent( getApplicationContext(), ActivityMunicipio.class);
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
                    Intent intent = new Intent( getApplicationContext(), ActivityBairro.class);
                    intent.putExtra("id_bairro", listaBairro.get(sp_bairro.getSelectedItemPosition()).getId());
                    intent.putExtra("id_cidade", listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId());
                    startActivityForResult(intent, 200);
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

        /*
        id_opcao_emissao.setOnCheckedChangeListener(this);
        id_opcao_vencimento.setOnCheckedChangeListener(this);
        id_opcao_recebido.setOnCheckedChangeListener(this);
        id_opcao_reagendado.setOnCheckedChangeListener(this);
        id_opcao_vencimento.setChecked(false);
        id_opcao_vencimento.setChecked(true);
        id_opcao_recebido.setChecked(false);
        id_opcao_reagendado.setChecked(false);

        //edt_data_inicial.setText();
        edt_data_final.setText( MascaraUtil.formataData(db.pegaDataAtual("SELECT date('now','localtime', '+1 day')")));
        */
        btn_view_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAvancarOnClick(buscaCobranca.getQuery().toString());
            }
        });



        setListenerCobranca();
        actionModeCallback = new ActivitySemCobranca.ActionModeCallback();
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
                else {
                    btnAvancarOnClick(query.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }

    private void btnAvancarOnClick(String razaoSocial) {
        /*
        if (!id_opcao_emissao.isChecked() && !id_opcao_vencimento.isChecked() ) {
            Toast.makeText(context, "Atenção!!\nFavor selecione um periodo(Emissão/Vencimento)", Toast.LENGTH_SHORT).show();
            return;
        }
        */
        try {
            /*
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataIni = null;
            String dataInicial = "-";
            String dataFinal = "-";
            if ( !edt_data_inicial.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString());
                dataInicial = formatter.format(dataIni);
            }
            //Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_inicial.getText().toString());
            Date dataFin = null;
            if ( !edt_data_final.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                dataFin = new SimpleDateFormat("dd/MM/yyyy").parse(edt_data_final.getText().toString());
                dataFinal = formatter.format(dataFin);
            }
            if ( !edt_data_inicial.getText().toString().replaceAll("/", "").trim().isEmpty() &&
                    !edt_data_final.getText().toString().replaceAll("/", "").trim().isEmpty()) {
                if (dataIni.after(dataFin)) {
                    Toast.makeText(context, "A data final não pode ser anterior a data inicial", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            */
            String nome = razaoSocial.toString().isEmpty() ? "-" : razaoSocial;
            String documento = "-";//edt_numero_documento.getText().toString().isEmpty() ? "-" : edt_numero_documento.getText().toString();
            String idConta = listaConta.get(sp_conta_bancaria.getSelectedItemPosition()).getId();
            String idMoeda = listaMoeda.get(sp_moeda.getSelectedItemPosition()).getId_moeda().toString();
            String idBairro = listaBairro.get(sp_bairro.getSelectedItemPosition()).getId().toString();
            String idCidade = listaMunicipio.get(sp_cidade.getSelectedItemPosition()).getId().toString();
            String idRegiao = listaRegiao.get(sp_regiao.getSelectedItemPosition()).getId().toString();
            visivel = false;
            showHideFiltros( visivel );
            if (empresaParametro.getUtiliza_cobranca_offline().equalsIgnoreCase("S"))
                buscaDados( nome, documento, idConta, idMoeda, idBairro, idCidade, idRegiao, recebido, reagendado);
            //else
            //    sincronizaPeriodoAPI( nome, documento, idConta, idMoeda, idBairro, idCidade, idRegiao);
        } catch ( NullPointerException e) {
            Toast.makeText(context, "Atenção!!\nParametro inválido!", Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception e) {
            Toast.makeText(context, "Atenção!!\nParametro inválido!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void buscaDados(String razaoSocial, String documento, String idConta, String idMoeda,
                            String idBairro, String idCidade, String idRegiao, boolean recebido, boolean reagendado) {
        showHideProgressBar(true);
        String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO, FRES.DATA_EMISSAO AS ULT_COMPRA, SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, FRES.PARCELA AS TDOCS, " +
                "GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                "( SELECT MAX(USUARIO_DATA) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                "( SELECT MAX(DATA_REAGENDAMENTO) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " ;
        SQL += "WHERE FRES.DEVEDOR = 'N' " ;
        if ( !razaoSocial.equalsIgnoreCase("-"))
            SQL += "AND FRES.NOME_FAVORECIDO LIKE '%" + razaoSocial + "%' ";
        if ( !documento.equalsIgnoreCase("-"))
            SQL += "AND FRES.DOCUMENTO LIKE '%" + documento + "%' ";
        if ( !idBairro.equalsIgnoreCase("0")) {
            if (!idCidade.equalsIgnoreCase("0")) {
                SQL += "AND (FRES.ID_CIDADE = " + idCidade + " AND FRES.ID_BAIRRO = " + idBairro + ") ";
            } else {
                SQL += "AND FRES.ID_BAIRRO = " + idBairro + " ";
            }
        }else {
            if ( !idCidade.equalsIgnoreCase("0"))
                SQL += "AND FRES.ID_CIDADE = " + idCidade;
        }
        if ( !idRegiao.equalsIgnoreCase("0"))
            SQL += " AND FRES.ID_REGIAO = " + idRegiao;

        SQL += " GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
        //ORDER BY TBL_REGIAO.NOME_REGIAO, TBL_CADASTRO_BAIRRO.ORDEM, TBL_CADASTRO. ENDERECO,  TBL_CADASTRO.ENDERECO_NUMERO;
        //ORDER BY TBL_REGIAO.NOME_REGIAO, TBL_CADASTRO_BAIRRO.ORDEM, TBL_CADASTRO. ENDERECO, TBL_CADASTRO. ENDERECO_BAIRRO,  TBL_CADASTRO.ENDERECO_NUMERO;
        listaFinanceiro =  financeiroDAO.getLista(SQL, recebido, reagendado);
        if ( listaFinanceiro.size() <= 0)
            Toast.makeText(ActivitySemCobranca.this, "Atenção!\n Nemhum registro localizado!!", Toast.LENGTH_SHORT).show();
        setRecyclerView(listaFinanceiro);
    }

    @Override
    public void onResume() {
        id_android = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode >= 0 && resultCode == RESULT_OK ) {
                if ( requestCode == 100) {
                    String id_cidade = data.getStringExtra("result");
                    for (int i = 0; i <= listaMunicipio.size(); i++) {
                        if (listaMunicipio.get(i).getId().equalsIgnoreCase(id_cidade)) {
                            sp_cidade.setSelection(i);
                            break;
                        }

                    }
                } else if ( requestCode == 200) {
                    String id_bairro = data.getStringExtra("result");
                    for (int i = 0; i <= listaBairro.size(); i++) {
                        if (listaBairro.get(i).getId().equalsIgnoreCase(id_bairro)) {
                            sp_bairro.setSelection(i);
                            break;
                        }
                    }
                } else if ( requestCode == 300) {
                    if( data.getBooleanExtra("result", true)){
                        int position = Integer.parseInt(data.getStringExtra("position"));
                        try {/*
                            float valor_recebido = Float.parseFloat(data.getStringExtra("valor_recebido"));
                            String data_reagendamento = data.getStringExtra("data_reagendamento");
                            try {
                                if ( data_reagendamento == null || data_reagendamento.isEmpty()) {
                                    data_reagendamento = "";
                                }
                            } catch ( NullPointerException e) {
                                data_reagendamento = "";
                            }
                            float valor = 0.00f;
                            try {
                                valor = Float.parseFloat(listaFinanceiro.get(position).getValor_recebimento());
                                listaFinanceiro.get(position).setData_baixa(db.pegaDataAtual());
                            } catch ( NullPointerException|NumberFormatException ex) {
                                ex.printStackTrace();
                            }
                            listaFinanceiro.get(position).setValor_recebimento(String.valueOf(valor + valor_recebido));
                            listaFinanceiro.get(position).setValor_debito(String.valueOf( Float.parseFloat(listaFinanceiro.get(position).getValor_debito())  -  valor_recebido) );
                            listaFinanceiro.get(position).setData_reagendamento(data_reagendamento);
                            */
                            String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  FRES.DATA_EMISSAO AS ULT_COMPRA, " +
                                    "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, FRES.PARCELA AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                                    "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                                    "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                                    "( SELECT MAX(USUARIO_DATA) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                                    "( SELECT MAX(DATA_REAGENDAMENTO) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                                    "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                                    "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                                    "WHERE FRES.ID_FAVORECIDO = " + listaFinanceiro.get(position).getId_favorecido() + " " ;
                            SQL += "AND FRES.DEVEDOR = 'N'  GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
                            List<FinanceiroResumo> lista = new ArrayList<>();
                            lista = financeiroDAO.getLista(SQL, false, false);
                            listaFinanceiro.set(position, lista.get(0));
                            listaCobrancaAdapter.notifyDataSetChanged();
                        } catch ( NumberFormatException|NullPointerException|CursorIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    hideKeyboard(ActivitySemCobranca.this);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showHideProgressBar(boolean b) {
        if (b)
            progress_bar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progress_bar.setVisibility( b ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility( b ? View.VISIBLE : View.INVISIBLE);
    }

    private void showHideFiltros(boolean b) {
        ViewGroup.LayoutParams params = card_view_menu.getLayoutParams();
        ViewGroup.LayoutParams paramsP = progress_bar.getLayoutParams();
        ViewGroup.LayoutParams paramsT = progress_title.getLayoutParams();
        if (visivel) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            paramsP.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            paramsT.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_down_white);
            visivel = !visivel;
            //btn_view_avancar.setVisibility(View.VISIBLE);
        } else {
            params.height = 0;
            paramsP.height = 0;
            paramsT.height = 0;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_up_white);
            visivel = !visivel;
            //btn_view_avancar.setVisibility(View.INVISIBLE);
        }
        card_view_menu.setLayoutParams(params);
        progress_bar.setLayoutParams(paramsP);
        progress_title.setLayoutParams(paramsT);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            /*
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
                if ( isChecked)
                    recebido = true;
                else
                    recebido = false;
                break;
            case R.id.id_opcao_reagendado:
                if ( isChecked)
                    reagendado = true;
                else
                    reagendado= false;
                break;

             */
        }
    }

    /*
    private void sincronizaPeriodoAPI(  String razaoSocial, String documento,  String idConta, String idMoeda,
                                      String idBairro, String idCidade, String IdRegiao) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        showHideProgressBar(true);
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( context));
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<FinanceiroResumo>> call = apiRotas.getCobrancaPeriodo(dataInicial, dataFinal, periodo, razaoSocial, documento, idConta, idMoeda, idBairro,  Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
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

     */

    private void limpaSelecao(String mensagem){
        actionMode.finish();
        actionMode = null;
        positionSelected = -1;
        listaCobrancaAdapter.clearSelections();
        showHideProgressBar(false);
        if ( !mensagem.isEmpty())
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();

    }
    private void sincronizaClienteAPI( String idCadastro, int position) {  //
        if ( position < 0) {
            Toast.makeText(ActivitySemCobranca.this, "Falha ao definir o registro para ser sincronizado!\nSelecione o registro e tente novamente!", Toast.LENGTH_LONG).show();
            actionMode = null;
            positionSelected = -1;
            return;
        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            limpaSelecao("Falha ao definir a versão do aplicativo para acesso a conexão!!");
            Toast.makeText(ActivitySemCobranca.this, "Falha ao definir a versão do aplicativo para acesso a conexão!!", Toast.LENGTH_LONG).show();
            return;
        }
        String versaoApp = pInfo.versionName.substring(0, 5);
        if ( !usuarioIsLogado() )
            return;
        Toast toast = Toast.makeText(context, "Aguarde.... \ntitulo/cliente em sincronização!!.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        //Toast.makeText(context, "Aguarde.... \ntitulo em sincronização;;!.", Toast.LENGTH_SHORT).show();
        showHideProgressBar(true);
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( context));
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {

            Call<Sincronia> call = getRotasAPI(1, idCadastro, configuracao, versaoApp);
            if ( call == null)
                return;
            call.enqueue(new Callback<Sincronia>() {
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    if (response.code() == 200) {
                        final Sincronia sincronia1 = response.body();
                        List<FinanceiroResumo> listaCobranca = sincronia1.getListaCobranca();
                        List<ReceberCab> listaRecebimentos = sincronia1.getListaRecebimentos();
                        db.deleteDados("TBL_WEB_RECEBER_CAB", "STATUS = ? AND EXCLUIDO = ? AND ID_ANDROID != ? AND ID_CADASTRO = ? AND ID_LOTE IS NOT NULL", new String[]{"A", "N", id_android, idCadastro});
                        if (listaRecebimentos.size() > 0) {
                            for (ReceberCab receber : listaRecebimentos) {
                                try {
                                    if (receber.getStatus().equalsIgnoreCase("A") && receber.getExcluido().equalsIgnoreCase("N")) {
                                        if (receber.getId_android().equalsIgnoreCase(id_android))
                                            webReceberCabDAO.update(receber);
                                        else
                                            webReceberCabDAO.add(receber);

                                    } else if (receber.getStatus().equalsIgnoreCase("R") || receber.getExcluido().equalsIgnoreCase("S")) {
                                        webReceberCabDAO.delete(receber, new String[]{ receber.getId_web_receber(), "A", receber.getId_android()});
                                        webReceberCabDAO.delete(receber, new String[]{ receber.getId_web_receber(), "N", receber.getId_android()});
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(context, "Visita enviada/sincronizada com o servidor!.", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            if (listaCobranca.size() > 0) {
                                financeiroDAO.delete(listaCobranca.get(0));
                                for (FinanceiroResumo financeiro : listaCobranca)
                                    financeiroDAO.add(financeiro);
                                String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  FRES.DATA_EMISSAO AS ULT_COMPRA, " +
                                        "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, FRES.PARCELA AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                                        "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                                        "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                                        "( SELECT MAX(USUARIO_DATA) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                                        "( SELECT MAX(DATA_REAGENDAMENTO) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                                        "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                                        "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                                        "WHERE FRES.ID_FAVORECIDO = " + listaCobranca.get(0).getId_favorecido() + " " ;
                                SQL += "AND FRES.DEVEDOR = 'N' GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
                                List<FinanceiroResumo> lista1 = new ArrayList<>();
                                lista1 = financeiroDAO.getLista(SQL, false, false);
                                if ( (recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && !reagendado /*&&  lista1.get(0).getData_reagendamento() != null*/ ) {
                                    listaFinanceiro.remove(positionSelected);
                                } else if ( ( reagendado &&  lista1.get(0).getData_reagendamento() == null) && !recebido) {
                                    listaFinanceiro.remove(positionSelected);
                                } else if ( (recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && ( reagendado && lista1.get(0).getData_reagendamento() == null ) ) {
                                    listaFinanceiro.remove(positionSelected);
                                }else {
                                    listaFinanceiro.set(positionSelected, lista1.get(0));
                                }

                                listaCobrancaAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "Titulo/cliente sincronizado com o servidor!.", Toast.LENGTH_SHORT).show();
                            }
                            actionMode.finish();
                            actionMode = null;
                            positionSelected = -1;
                            listaCobrancaAdapter.clearSelections();
                        } catch ( NullPointerException|CursorIndexOutOfBoundsException e) {
                            actionMode.finish();
                            actionMode = null;
                            positionSelected = -1;
                            listaCobrancaAdapter.clearSelections();
                            Toast.makeText(context, "Falha ao sincronizar com o servidor!!.\n" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        showHideProgressBar(false);
                        System.gc();

                    }else {
                        actionMode.finish();
                        actionMode = null;
                        positionSelected = -1;
                        listaCobrancaAdapter.clearSelections();
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    try {
                        toast.cancel();
                    } catch ( NullPointerException e){

                    }
                    sincronizaClienteAPI2(idCadastro, position);
                }
            });
        } else {
            actionMode.finish();
            actionMode = null;
            positionSelected = -1;
            listaCobrancaAdapter.clearSelections();
            showHideProgressBar(false);
            Toast.makeText(context, "Parametros para conexão não cadastrados!", Toast.LENGTH_LONG).show();
        }
    }



    private void sincronizaClienteAPI2( String idCadastro, int position) {  //
        if ( position < 0) {
            Toast.makeText(ActivitySemCobranca.this, "Falha ao definir o registro para ser sincronizado!\nSelecione o registro e tente novamente!", Toast.LENGTH_LONG).show();
            actionMode = null;
            positionSelected = -1;
            return;
        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(context, "Aguarde.... \ntitulo/cliente em sincronização!!.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        String versaoApp = pInfo.versionName.substring(0, 5);
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( context));
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            Sincronia sincronia = new Sincronia(true);
            String id_android = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            sincronia.setListaRecebimentos(webReceberCabDAO.getLista("SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = '" + idCadastro + "' AND ID_ANDROID = '" + id_android + "'"));  //N nao sincronizado
            sincronia.setListaCadastroFinanceiroResumo(new ArrayList<>());
            ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);

            try {
                if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty()) {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp.substring(0, 5) + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();
                } else {
                    actionMode.finish();
                    actionMode = null;
                    positionSelected = -1;
                    listaCobrancaAdapter.clearSelections();
                    showHideProgressBar(false);
                    Toast.makeText(context, "Parametro p conexao ao servidor não definido!.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch ( NullPointerException e) {
                actionMode.finish();
                actionMode = null;
                positionSelected = -1;
                listaCobrancaAdapter.clearSelections();
                showHideProgressBar(false);
                Toast.makeText(context, "Parametro p conexao ao servidor não definido!!.", Toast.LENGTH_SHORT).show();
                return;
            }
            final Rotas apiRotas2 = Api.buildRetrofit2(true);

            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<Sincronia> call =  apiRotas2.sincronizaSemCobranca( idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia ,cabecalho);
            call.enqueue(new Callback<Sincronia>() {
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    if (response.code() == 200) {
                        final Sincronia sincronia1 = response.body();
                        List<FinanceiroResumo> listaCobranca = sincronia1.getListaCobranca();
                        List<ReceberCab> listaRecebimentos = sincronia1.getListaRecebimentos();
                        db.deleteDados("TBL_WEB_RECEBER_CAB", "STATUS = ? AND EXCLUIDO = ? AND ID_ANDROID != ? AND ID_CADASTRO = ? AND ID_LOTE IS NOT NULL", new String[]{"A", "N", id_android, idCadastro});
                        if (listaRecebimentos.size() > 0) {
                            for (ReceberCab receber : listaRecebimentos) {
                                try {
                                    if (receber.getStatus().equalsIgnoreCase("A") && receber.getExcluido().equalsIgnoreCase("N")) {
                                        if (receber.getId_android().equalsIgnoreCase(id_android))
                                            webReceberCabDAO.update(receber);
                                        else
                                            webReceberCabDAO.add(receber);

                                    } else if (receber.getStatus().equalsIgnoreCase("R") || receber.getExcluido().equalsIgnoreCase("S")) {
                                        webReceberCabDAO.delete(receber, new String[]{receber.getId_web_receber(), "A", receber.getId_android()});
                                        webReceberCabDAO.delete(receber, new String[]{ receber.getId_web_receber(), "N", receber.getId_android()});
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(context, "Visita enviada/sincronizada com o servidor!.", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            if (listaCobranca.size() > 0) {
                                financeiroDAO.delete(listaCobranca.get(0));
                                for (FinanceiroResumo financeiro : listaCobranca)
                                    financeiroDAO.add(financeiro);
                                String SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  FRES.DATA_EMISSAO AS ULT_COMPRA, " +
                                        "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, FRES.PARCELA AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                                        "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                                        "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                                        "( SELECT USUARIO_DATA FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                                        "( SELECT DATA_REAGENDAMENTO FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                                        "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                                        "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                                        "WHERE FRES.ID_FAVORECIDO = " + listaCobranca.get(0).getId_favorecido() + " ";
                                SQL += "AND FRES.DEVEDOR = 'N'  GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
                                List<FinanceiroResumo> lista1 = new ArrayList<>();
                                lista1 = financeiroDAO.getLista(SQL, false, false);

                                if ( (recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && !reagendado /*&&  lista1.get(0).getData_reagendamento() != null*/ ) {
                                    listaFinanceiro.remove(positionSelected);
                                } else if ( ( reagendado &&  lista1.get(0).getData_reagendamento() == null) && !recebido) {
                                    listaFinanceiro.remove(positionSelected);
                                } else if ( (recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && ( reagendado && lista1.get(0).getData_reagendamento() == null ) ) {
                                    listaFinanceiro.remove(positionSelected);
                                }else {
                                    listaFinanceiro.set(positionSelected, lista1.get(0));
                                }

                                //listaFinanceiro.set(positionSelected, lista.get(0));
                                listaCobrancaAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "Titulo/cliente sincronizado com o servidor!.", Toast.LENGTH_SHORT).show();
                            } /*else {
                                financeiroDAO.delete(listaFinanceiro.get(positionSelected));
                                util.showMsgAlerta("Não foram localizados titulos pendentes para o cliente \n" + listaFinanceiro.get(positionSelected).getNome_favorecido() + "!", ActivitySemCobranca.this);
                                listaFinanceiro.remove(positionSelected);
                                listaCobrancaAdapter.notifyDataSetChanged();
                            }
                            */
                            actionMode.finish();
                            actionMode = null;
                            positionSelected = -1;
                            listaCobrancaAdapter.clearSelections();
                        } catch ( NullPointerException|CursorIndexOutOfBoundsException e) {
                            actionMode.finish();
                            actionMode = null;
                            positionSelected = -1;
                            listaCobrancaAdapter.clearSelections();
                            Toast.makeText(context, "Falha ao sincronizar com o servidor!!.\n" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        showHideProgressBar(false);
                        System.gc();

                    }else {
                        actionMode.finish();
                        actionMode = null;
                        positionSelected = -1;
                        listaCobrancaAdapter.clearSelections();
                        showHideProgressBar(false);
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    actionMode.finish();
                    actionMode = null;
                    positionSelected = -1;
                    listaCobrancaAdapter.clearSelections();
                    showHideProgressBar(false);
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor!! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            actionMode.finish();
            actionMode = null;
            positionSelected = -1;
            listaCobrancaAdapter.clearSelections();
            showHideProgressBar(false);
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }
    private void setRecyclerView(List<FinanceiroResumo> lista) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listaCobrancaAdapter = new ListaSemCobrancaAdapter(ActivitySemCobranca.this, lista, listenerCobranca);
        recyclerView.setAdapter(listaCobrancaAdapter);
        listaCobrancaAdapter.notifyDataSetChanged();
        //setListenerCobranca();
        showHideProgressBar(false);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setListenerCobranca() {
        listenerCobranca = new ListaSemCobrancaAdapter.CobrancaAdapterListener() {
            @Override
            public void onClickListener(int position) {
                /*
                startActivityForResult(new Intent(ActivitySemCobranca.this, ActivityCobrancaRecebimento.class)
                                .putExtra("position", String.valueOf(position))
                                .putExtra("id_favorecido", listaFinanceiro.get(position).getId_favorecido())
                                .putExtra("nome_favorecido", listaFinanceiro.get(position).getNome_favorecido())
                                .putExtra("valor_pendente", "0.00")
                                .putExtra("total_docs", "0")
                                .putExtra("cpf_cnpj", String.valueOf(listaFinanceiro.get(position).getCpf_cnpj()))
                                .putExtra("devedor", "N")
                        , 300);
                */
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
                        util.showMsgAlerta("Nenhum endereço informado!", ActivitySemCobranca.this);
                    else
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + listaFinanceiro.get(position).getEndereco())).setPackage("com.google.android.apps.maps"));
                } catch ( NullPointerException e) {
                }catch ( Exception e) {
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
            /*else if (configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                if (Float.parseFloat(cliente.getLimite_credito()) - (Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo())) < 0.0f) {
                    showMsgSimNao(R.id.novoPedido, "Cliente sem limite disponivel para compra a prazo! Pedido só podera ser feito para pagamento à vista!\nDeseja continuar?");
                    return;
                }
            }*/
            Cliente cliente = new ClienteDAO(db).getLista("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO_SERVIDOR = " + listaFinanceiro.get(position).getId_favorecido()).get(0);
            ClienteHelper.setCliente( cliente );
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
            startActivity(new Intent(ActivitySemCobranca.this, ActivityPedidoMain.class));

        } catch (NullPointerException | NumberFormatException | CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
    public void fazerChamada(String telefone, String nome) {
        try {
            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty())
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11) {
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(ActivitySemCobranca.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySemCobranca.this);
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
                    util.showMsgAlerta("Este numero de Telefone não é valido!", ActivitySemCobranca.this);
            else
                util.showMsgAlerta("Nenhum numero de telefone informado!", ActivitySemCobranca.this);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }
    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            geraPDFRecibo = menu.findItem(R.id.action_pdf_pedido);
            geraPDFEmail  = menu.findItem(R.id.action_pdf_pedido_email);
            sincroniza    = menu.findItem(R.id.action_mode_menu_pedido_pendente);
            delete        = menu.findItem(R.id.action_delete);
            //menu.findItem(R.id.action_delete).setVisible(false);
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
                    try {
                        mode.finish();
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                //case R.id.action_dados_cliente:
                //    startActivity( new Intent( ActivitySemCobranca.this, ActivityCobrancaContato.class)
                //            .putExtra("id_favorecido", titulosSelecionados.get(0).getId_favorecido()));
                //    return true;
                case R.id.action_mode_menu_pedido_pendente:
                    sincronizaClienteAPI( titulosSelecionados.get(0).getId_favorecido(), positionSelected);
                    return true;
                case R.id.action_delete:
                    deleteRecebimento( titulosSelecionados.get(0), positionSelected);
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
                if ( Float.parseFloat( listaFinanceiro.get(position).getValor_recebimento()) > 0.00f) {
                    sincroniza.setVisible(true);
                    geraPDFRecibo.setVisible(true);
                    sincroniza.setVisible(true);
                    String sql = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = '" + listaFinanceiro.get(position).getId_favorecido() + "' AND STATUS = 'N' AND ID_ANDROID = '" + id_android + "'";
                    List<ReceberCab> listaRecebimentos = webReceberCabDAO.getLista(sql);
                    if ( listaRecebimentos.size() > 0)
                        delete.setVisible(true);
                    else
                        delete.setVisible(false);
                    /*
                    if (listaFinanceiro.get(position).getStatus().equalsIgnoreCase("N"))
                        delete.setVisible(true);
                    else {
                        delete.setVisible(false);
                    }
                    */
                    return;
                } else if ( listaFinanceiro.get(position).getData_reagendamento() != null){
                    geraPDFRecibo.setVisible(false);
                    sincroniza.setVisible(true);
                    String sql = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = '" + listaFinanceiro.get(position).getId_favorecido() + "' AND STATUS = 'N' AND ID_ANDROID = '" + id_android + "'";
                    List<ReceberCab> listaRecebimentos = webReceberCabDAO.getLista(sql);
                    if ( listaRecebimentos.size() > 0)
                        delete.setVisible(true);
                    else
                        delete.setVisible(false);
                    /*if (listaFinanceiro.get(position).getStatus().equalsIgnoreCase("N"))
                         delete.setVisible(true);
                    else
                        delete.setVisible(false);
                     */
                    return;
                } else {
                    geraPDFRecibo.setVisible(false);
                    sincroniza.setVisible(true);
                    delete.setVisible(false);
                }

                actionMode.setTitle(String.valueOf(listaCobrancaAdapter.getSelectedItemCount()));
                actionMode.invalidate();
            } catch ( NullPointerException|NumberFormatException e) {
                geraPDFRecibo.setVisible(false);
                geraPDFEmail.setVisible(false);

            }

        }
    }

    private void geraArquivoPDF(FinanceiroResumo financeiroResumo) {

        if ( Build.VERSION.SDK_INT >+ Build.VERSION_CODES.N) {
            try {
                EmpresaParametro empresa = getEmpresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                if (empresa == null) {
                    Toast.makeText(ActivitySemCobranca.this, "Falha ao obter as informações da empresa , favor entrar em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                PDFReciboUtil pdfReciboUtil = new PDFReciboUtil(financeiroResumo, empresa, ActivitySemCobranca.this, db);
                File filePDF = pdfReciboUtil.criandoPdf();
                if ( filePDF == null) {
                    return;
                }
                Uri contentUri = FileProvider.getUriForFile(ActivitySemCobranca.this, getApplicationContext().getPackageName() + "..provider", filePDF/*pdfPedidoUtil.criandoPdf()*/);
                startActivity(new Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        .setDataAndType(contentUri, "application/pdf"));
            } catch (NullPointerException e) {
                showAlertInfoApp();
            } catch (Exception e) {
                Toast.makeText(ActivitySemCobranca.this, "Aplicativo sem permissão para criar o arquivo! De permissão de acesso manualmente,ou entre em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
            }
        } else{

            try {
                EmpresaParametro empresa = getEmpresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                if (empresa == null) {
                    Toast.makeText(ActivitySemCobranca.this, "Falha ao obter as informações da empresa , favor entrar em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                PDFReciboUtil pdfReciboUtil = new PDFReciboUtil(financeiroResumo, empresa,ActivitySemCobranca.this, db);
                File filePDF = pdfReciboUtil.criandoPdf();
                if ( filePDF == null) {
                    return;
                }
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW)
                        .setDataAndType(Uri.fromFile(filePDF), "application/pdf"), "Abrir arquivo"));
            } catch (NullPointerException e) {
                showAlertInfoApp();
            } catch (Exception e) {
                Toast.makeText(ActivitySemCobranca.this, "Aplicativo sem permissão para criar o arquivo! De permissão de acesso manualmente,ou entre em contato com o suporte técnico!!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void deleteRecebimento(FinanceiroResumo financeiro, int position) {
        if ( position < 0) {
            Toast.makeText(ActivitySemCobranca.this, "Falha ao definir o registro para ser excluido!!", Toast.LENGTH_SHORT).show();
            return;
        }
        int positionSelected = position;
        ReceberDAO receberDAO = new ReceberDAO(db);
        String SQL = "SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = " + financeiro.getId_favorecido() + " AND ID_ANDROID = '" + id_android + "' AND STATUS = 'N'";
        List<ReceberCab> lista = new ArrayList<>();
        lista = receberDAO.getLista(SQL);
        if (lista.size() > 0) {
            for ( int i = 0; i < lista.size(); i++) {
                if (receberDAO.delete(lista.get(i), new String[]{lista.get(i).getId_web_receber(), lista.get(i).getId_android()})) {
                    Toast.makeText(ActivitySemCobranca.this, "Registro(s) excluido(s) com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            //SQL = "SELECT FRES.*, SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
            SQL = "SELECT FRES.*, REG.NOME_REGIAO, MAX(FRES.DATA_VENCIMENTO) AS ULT_VCTO,  FRES.DATA_EMISSAO AS ULT_COMPRA, " +
                    "SUM(FRES.VALOR_TOTAL) AS VALOR_TOTAL, FRES.PARCELA AS TDOCS, GROUP_CONCAT(FRES.DOCUMENTO) AS DOCS, " +
                    "( SELECT SUM(VALOR) FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS VALOR_RECEBIMENTO, " +
                    "( SELECT STATUS FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS STATUS, " +
                    "( SELECT USUARIO_DATA FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_PGTO, " +
                    "( SELECT DATA_REAGENDAMENTO FROM TBL_WEB_RECEBER_CAB WHERE ID_CADASTRO = FRES.ID_FAVORECIDO) AS DATA_REAGENDAMENTO " +
                    "FROM TBL_FINANCEIRO_RESUMO AS FRES " +
                    "LEFT JOIN TBL_CADASTRO_REGIAO AS REG ON REG.ID = FRES.ID_REGIAO " +
                    "WHERE FRES.ID_FAVORECIDO = " + financeiro.getId_favorecido() + " " ;
            SQL += "AND FRES.DEVEDOR = 'N'  GROUP BY FRES.ID_FAVORECIDO ORDER BY REG.NOME_REGIAO, FRES.ORDEM, FRES.ENDERECO_BAIRRO, FRES.ENDERECO, FRES.ENDERECO_NUMERO";
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
                    if ( (recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && !reagendado /*&&  lista1.get(0).getData_reagendamento() != null*/ ) {
                        listaFinanceiro.remove(positionSelected);
                    } else if ( ( reagendado &&  lista1.get(0).getData_reagendamento() == null) && !recebido) {
                        listaFinanceiro.remove(positionSelected);
                    } else if ( (recebido && Float.parseFloat(lista1.get(0).getValor_recebimento()) <= 0.0f) && ( reagendado && lista1.get(0).getData_reagendamento() == null ) ) {
                        listaFinanceiro.remove(positionSelected);
                    }else {
                        listaFinanceiro.set(positionSelected, lista1.get(0));
                    }

                    //listaCobrancaAdapter.notifyDataSetChanged();

                }
            } catch ( NullPointerException|CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            /*
            btnAvancarOnClick(buscaCobranca.getQuery().toString());
            actionMode.finish();
            actionMode = null;
            positionSelected = -1;
            listaCobrancaAdapter.clearSelections();
            recyclerView.scrollToPosition(position);

             */
        } else {
            Toast.makeText(ActivitySemCobranca.this, "Nenhum registro foi excluido!!", Toast.LENGTH_SHORT).show();
        }
        actionMode.finish();
        actionMode = null;
        positionSelected = -1;
        listaCobrancaAdapter.clearSelections();
    }


    private EmpresaParametro getEmpresa(String id_empresa) {
        EmpresaParametro empresa = null;
        try {
            empresa = empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + id_empresa + "'");
            if (empresa == null) {
                empresaParametroDAO.getEmpresaPDF("SELECT * FROM TBL_EMPRESA_PARAMETRO");
                if (empresa == null) {
                    Toast.makeText(ActivitySemCobranca.this, "Falha ao obter as informações da empresa , favor entrar em contato com o suporte técnico!", Toast.LENGTH_SHORT).show();
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

    private void showAlertInfoApp() {
        new AlertDialog.Builder(ActivitySemCobranca.this)
                .setTitle("RCK SISTEMAS ESPECIFICOS!")
                .setMessage("Aplicativo sem permissão para criação/armazenamento de arquivos!\n Gostaria de analisar/conceder permissão para de armazenamento para este aplicativo? ")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(ActivitySemCobranca.this, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
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
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas.getSemCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                else
                    return   apiRotas.sincronizaSemCobranca( idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia ,cabecalho);
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
                        return apiRotas.getSemCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                    else
                        return   apiRotas.sincronizaSemCobranca( idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia ,cabecalho);
                } catch (NullPointerException ex) {
                    showHideProgressBar(false);
                    Toast.makeText(ActivitySemCobranca.this, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                showHideProgressBar(false);
                Toast.makeText(ActivitySemCobranca.this, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n" + e.toString(), Toast.LENGTH_LONG).show();
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
                } catch (NullPointerException ex) {
                    showHideProgressBar(false);
                    Toast.makeText(ActivitySemCobranca.this, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                showHideProgressBar(false);
                Toast.makeText(ActivitySemCobranca.this, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n"+ e.toString(), Toast.LENGTH_LONG).show();
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


    /*private void criarUrlGoogleDirections(){

         Uri urlGoogleDirections = "http://maps.googleapis.com/maps/api/directions/json?origin=" +
                latLngPontoPartida.latitude + "," + latLngPontoPartida.longitude +
                "&destination=" + latLngPontoFinal.latitude + "," + latLngPontoFinal.longitude +
                "&waypoints=";

        for (int i = 0; i < objListaLatLngWaypoints.size(); i++) {

            urlGoogleDirections += objListaLatLngWaypoints.get(i).latitude
                    + "," + objListaLatLngWaypoints.get(i).longitude;

            if ((i+1) < objListaLatLngWaypoints.size()) {

                urlGoogleDirections += "|";
            }
        }

        urlGoogleDirections += "&sensor=false";
        urlGoogleDirections += "&mode=bicycling";

        Log.i("URL", urlGoogleDirections);

    }

     */
}