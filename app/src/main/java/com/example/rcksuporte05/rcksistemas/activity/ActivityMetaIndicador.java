package com.example.rcksuporte05.rcksistemas.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.icu.math.BigDecimal;
import android.icu.text.NumberFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoLinhaColecaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.MesAno;
import com.example.rcksuporte05.rcksistemas.model.MesAnoGrupoMarca;
import com.example.rcksuporte05.rcksistemas.model.MetaSemanal;
import com.example.rcksuporte05.rcksistemas.model.ProdutoGrupo;
import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.FirestoreConexao;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityMetaIndicador extends AppCompatActivity {
    private DBHelper db = new DBHelper(this);
    private Context context;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.imgView)
    ImageView imgView;

    @BindView(R.id.btnCompartilhar)
    ImageView btnCompartilhar;

    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    @BindView(R.id.btnCancelar)
    Button btnCancelar;

    @BindView(R.id.lytMesAno)
    CardView lytMesAno;
    @BindView(R.id.lytMesAnoGrupo)
    CardView lytMesAnoGrupo;

    @BindView(R.id.lytMesAnoMarca)
    CardView lytMesAnoMarca;

    @BindView(R.id.lytMesAnoSemana)
    CardView lytMesAnoSemana;

    @BindView(R.id.lytMesAnoProduto)
    CardView lytMesAnoProduto;

    @BindView(R.id.lytMesAnoEmpresa)
    CardView lytMesAnoEmpresa;

    @BindView(R.id.txvMesAno)
    TextView txvMesAno;

    @BindView(R.id.edtJan)
    EditText edtJan;

    @BindView(R.id.edtFev)
    EditText edtFev;
    @BindView(R.id.edtMar)
    EditText edtMar;
    @BindView(R.id.edtAbr)
    EditText edtAbr;
    @BindView(R.id.edtMai)
    EditText edtMai;
    @BindView(R.id.edtJun)
    EditText edtJun;
    @BindView(R.id.edtJul)
    EditText edtJul;
    @BindView(R.id.edtAgo)
    EditText edtAgo;
    @BindView(R.id.edtSet)
    EditText edtSet;
    @BindView(R.id.edtOut)
    EditText edtOut;
    @BindView(R.id.edtNov)
    EditText edtNov;
    @BindView(R.id.edtDez)
    EditText edtDez;

    @BindView(R.id.inputJan)
    TextInputLayout inputJan;
    @BindView(R.id.inputFev)
    TextInputLayout inputFev;
    @BindView(R.id.inputMar)
    TextInputLayout inputMar;
    @BindView(R.id.inputAbr)
    TextInputLayout inputAbr;
    @BindView(R.id.inputMai)
    TextInputLayout inputMai;
    @BindView(R.id.inputJun)
    TextInputLayout inputJun;
    @BindView(R.id.inputJul)
    TextInputLayout inputJul;
    @BindView(R.id.inputAgo)
    TextInputLayout inputAgo;
    @BindView(R.id.inputSet)
    TextInputLayout inputSet;
    @BindView(R.id.inputOut)
    TextInputLayout inputOut;
    @BindView(R.id.inputNov)
    TextInputLayout inputNov;
    @BindView(R.id.inputDez)
    TextInputLayout inputDez;

    @BindView(R.id.tvHelperJan) TextView tvHelperJan;
    @BindView(R.id.tvHelperFev) TextView tvHelperFev;

    @BindView(R.id.tvHelperMar) TextView tvHelperMar;

    @BindView(R.id.tvHelperAbr) TextView tvHelperAbr;
    @BindView(R.id.tvHelperMai) TextView tvHelperMai;
    @BindView(R.id.tvHelperJun) TextView tvHelperJun;
    @BindView(R.id.tvHelperJul) TextView tvHelperJul;
    @BindView(R.id.tvHelperAgo) TextView tvHelperAgo;
    @BindView(R.id.tvHelperSet) TextView tvHelperSet;
    @BindView(R.id.tvHelperOut) TextView tvHelperOut;
    @BindView(R.id.tvHelperNov) TextView tvHelperNov;
    @BindView(R.id.tvHelperDez) TextView tvHelperDez;


    @BindView(R.id.spAnos)
    Spinner spAnos;

    @BindView(R.id.spUsuarios)
    Spinner spUsuarios;

    @BindView(R.id.lytUsuarioFiltro)
    View lytUsuarioFiltro;

    @BindView(R.id.lytEmpresaFiltro)
    View lytEmpresaFiltro;

    @BindView(R.id.spEmpresas)
    Spinner spEmpresas;

    @BindView(R.id.lytSubOpcoes)
    LinearLayout lytSubOpcoes;

    @BindView(R.id.lytFiltros)
    LinearLayout lytFiltros;

    @BindView(R.id.scrollViewMeta)
    ScrollView scrollViewMeta;

    @BindView(R.id.lblSubOpcoes)
    TextView lblSubOpcoes;

    @BindView(R.id.spSubOpcoes)
    Spinner spSubOpcoes;

    @BindView(R.id.btnAbrirCalendario)
    ImageView btnAbrirCalendario;

    @BindView(R.id.lytGlobalLoading)
    View lytGlobalLoading;

    @BindView(R.id.tvGlobalLoadingTitle)
    TextView tvGlobalLoadingTitle;

    @BindView(R.id.tvTotalAnual)
    TextView tvTotalAnual;

    @BindView(R.id.tvTotalRealizadoAnterior)
    TextView tvTotalRealizadoAnterior;

    @BindView(R.id.tvVariacaoPercentual)
    TextView tvVariacaoPercentual;

    @BindView(R.id.lblTotalAcumulado)
    TextView lblTotalAcumulado;

    @BindView(R.id.lblTotalRealizadoAnterior)
    TextView lblTotalRealizadoAnterior;

    @BindView(R.id.cvTotalAnual)
    View cvTotalAnual;

    @BindView(R.id.lytRealizadoAnoAnterior)
    View lytRealizadoAnoAnterior;

    private int anoSelecionado;
    private String idUsuarioSelecionado;
    private String idVendedorSelecionado;
    private String idEmpresaSelecionada;
    private String idSubOpcaoSelecionada;
    private String tipoMeta = "M"; // M = Mensal, G = Grupo, C = Coleção/Marca, E = Empresa
    private boolean alterado = false;
    private double totalRealizadoAnterior;
    Configuracao configuracao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meta_indicador);

        // Força a barra de status e navegação a terem a mesma cor da Toolbar
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        // Configura para que os ícones da barra de status sejam brancos (fundo escuro)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            int flags = decor.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // Remove flag de ícones pretos
            decor.setSystemUiVisibility(flags);
        }

        context = this;
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.btnCancelar);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if ( UsuarioHelper.getUsuario() == null) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        }

        anoSelecionado = Calendar.getInstance().get(Calendar.YEAR);
        popularAnos();
        popularUsuarios();
        popularEmpresas();

        //toolbar.setTitle("Meta - " + anoSelecionado);

        //imgView.setOnClickListener(v -> showYearPickerDialog());

        btnConfirmar.setVisibility(GONE);
        btnCancelar.setVisibility(GONE);

        lytMesAno.setOnClickListener(view -> {
            esconderCardsMenu();
            showHideLayoutFiltros(true);
            lytSubOpcoes.setVisibility(GONE);
            lytUsuarioFiltro.setVisibility(VISIBLE);
            lytEmpresaFiltro.setVisibility(GONE);
            lblSubOpcoes.setText("Definir meta mensal");
            tipoMeta = "M";
            atualizarTituloToolbar();
        });

        lytMesAnoGrupo.setOnClickListener(view -> {
            esconderCardsMenu();
            showHideLayoutFiltros(true);
            lytUsuarioFiltro.setVisibility(VISIBLE);
            lytEmpresaFiltro.setVisibility(GONE);
            lblSubOpcoes.setText("Selecionar Grupo");
            tipoMeta = "G";
            popularGrupos();
            atualizarTituloToolbar();
        });

        lytMesAnoMarca.setOnClickListener(view -> {
            esconderCardsMenu();
            showHideLayoutFiltros(true);
            lytUsuarioFiltro.setVisibility(VISIBLE);
            lytEmpresaFiltro.setVisibility(GONE);
            lblSubOpcoes.setText("Selecionar Marca/Coleção");
            tipoMeta = "C";
            popularColecoes();
            atualizarTituloToolbar();
        });

        lytMesAnoSemana.setOnClickListener(view -> {
            esconderCardsMenu();
            showHideLayoutFiltros(true);
            lytUsuarioFiltro.setVisibility(VISIBLE);
            lytEmpresaFiltro.setVisibility(GONE);
            lblSubOpcoes.setText("Selecionar Mês");
            tipoMeta = "S";
            btnAbrirCalendario.setVisibility(VISIBLE);
            popularMesesSubOpcao();
            atualizarTituloToolbar();
        });

        lytMesAnoProduto.setOnClickListener(view -> {
            Toast.makeText(context, "Módulo de Metas por Produto em desenvolvimento", Toast.LENGTH_SHORT).show();
        });

        lytMesAnoEmpresa.setOnClickListener(view -> {
            esconderCardsMenu();
            showHideLayoutFiltros(true);
            lytSubOpcoes.setVisibility(GONE);
            lytUsuarioFiltro.setVisibility(GONE);
            lytEmpresaFiltro.setVisibility(VISIBLE);
            lblSubOpcoes.setText("Definir meta por empresa");
            tipoMeta = "E";
            atualizarTituloToolbar();
            
            // Dispara a busca inicial para a empresa já selecionada no Spinner
            dispararBuscaCompleta(true);
        });

        showHideLayoutFiltros(false);

        btnConfirmar.setOnClickListener(view -> salvarMeta());
        
        btnCompartilhar.setOnClickListener(v -> compartilharMeta());

        btnAbrirCalendario.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            cal.set(anoSelecionado, Integer.parseInt(idSubOpcaoSelecionada) - 1, 1);
            android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(this, null, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        inputJan.setEndIconOnClickListener(v -> replicarMetaJaneiro());

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alterado) {
                    new androidx.appcompat.app.AlertDialog.Builder(context)
                            .setTitle("Alterações não salvas")
                            .setMessage("Deseja realmente cancelar e descartar as alterações?")
                            .setPositiveButton("Sim, Cancelar", (dialog, which) -> {
                                voltarEstadoInicial();
                                alterado = false;
                            })
                            .setNegativeButton("Continuar Editando", null)
                            .show();
                } else {
                    voltarEstadoInicial();
                    alterado = false;
                }
            }
        });
        Locale mLocale = new Locale("pt", "BR");
        //fdb.collection(cnpj_final).document("TBL_WEB_PEDIDO").collection(webPedido.getId_vendedor()).document(webPedido.getId_pedido_app_fdb()).set(map).addOnSuccessListener(documentReference -> {
        edtJan.addTextChangedListener(new MoneyTextWatcher(edtJan, Locale.getDefault()));
        edtJan.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtJan.selectAll();

        edtFev.addTextChangedListener(new MoneyTextWatcher(edtFev, Locale.getDefault()));
        edtFev.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtFev.selectAll();

        edtMar.addTextChangedListener(new MoneyTextWatcher(edtMar, Locale.getDefault()));
        edtMar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtMar.selectAll();

        edtAbr.addTextChangedListener(new MoneyTextWatcher(edtAbr, Locale.getDefault()));
        edtAbr.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtAbr.selectAll();

        edtMai.addTextChangedListener(new MoneyTextWatcher(edtMai, Locale.getDefault()));
        edtMai.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtMai.selectAll();

        edtJun.addTextChangedListener(new MoneyTextWatcher(edtJun, Locale.getDefault()));
        edtJun.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtJun.selectAll();

        edtJul.addTextChangedListener(new MoneyTextWatcher(edtJul, Locale.getDefault()));
        edtJul.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtJul.selectAll();

        edtAgo.addTextChangedListener(new MoneyTextWatcher(edtAgo, Locale.getDefault()));
        edtAgo.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtAgo.selectAll();

        edtSet.addTextChangedListener(new MoneyTextWatcher(edtSet, Locale.getDefault()));
        edtSet.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtSet.selectAll();

        edtOut.addTextChangedListener(new MoneyTextWatcher(edtOut, Locale.getDefault()));
        edtOut.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtOut.selectAll();

        edtNov.addTextChangedListener(new MoneyTextWatcher(edtNov, Locale.getDefault()));
        edtNov.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtNov.selectAll();

        edtDez.addTextChangedListener(new MoneyTextWatcher(edtDez, Locale.getDefault()));
        edtDez.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { 
                alterado = true;
                atualizarTotalAnual(); 
            }
        });
        edtDez.selectAll();


        //addWebUsuarioFirebase(configuracao.getCnpj_empresa(), UsuarioHelper.getUsuario());

        //addMetaUsuarioFirebase(configuracao.getCnpj_empresa(), UsuarioHelper.getUsuario());
        
        destacarMesAtual();
        animarEntrada();
    }

    private void animarEntrada() {
        // Define o estado inicial (invisível e um pouco abaixo da posição real)
        lytMesAno.setAlpha(0f);
        lytMesAno.setTranslationY(40f);
        
        lytMesAnoGrupo.setAlpha(0f);
        lytMesAnoGrupo.setTranslationY(40f);
        
        lytMesAnoMarca.setAlpha(0f);
        lytMesAnoMarca.setTranslationY(40f);
        
        lytMesAnoSemana.setAlpha(0f);
        lytMesAnoSemana.setTranslationY(40f);

        lytMesAnoProduto.setAlpha(0f);
        lytMesAnoProduto.setTranslationY(40f);

        lytMesAnoEmpresa.setAlpha(0f);
        lytMesAnoEmpresa.setTranslationY(40f);

        // Anima de volta para a posição original (0) e opacidade total (1)
        lytMesAno.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay(100).start();
        lytMesAnoGrupo.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay(200).start();
        lytMesAnoMarca.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay(300).start();
        lytMesAnoSemana.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay(400).start();
        lytMesAnoProduto.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay(500).start();
        lytMesAnoEmpresa.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay(600).start();
    }

    private void destacarMesAtual() {
        int mesAtual = Calendar.getInstance().get(Calendar.MONTH); // 0 a 11
        TextInputLayout[] inputs = {
                inputJan, inputFev, inputMar, inputAbr, inputMai, inputJun,
                inputJul, inputAgo, inputSet, inputOut, inputNov, inputDez
        };

        if (mesAtual >= 0 && mesAtual < inputs.length) {
            // Aplica uma cor de fundo muito sutil para destacar (ex: 20% de opacidade da cor primária)
            inputs[mesAtual].setBoxBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryTransparente20));
            // Focar o campo do mês atual ao abrir a tela para facilitar
            inputs[mesAtual].getEditText().requestFocus();
        }
    }

    private void showHideLayoutFiltros(boolean showLayout) {
            lytFiltros.setVisibility(showLayout ? VISIBLE : GONE);
            lytSubOpcoes.setVisibility(showLayout ? VISIBLE : GONE);
            scrollViewMeta.setVisibility(showLayout ? VISIBLE : GONE);
            btnConfirmar.setVisibility(showLayout ? VISIBLE : GONE);
            btnCancelar.setVisibility(showLayout ? VISIBLE : GONE);
            cvTotalAnual.setVisibility(showLayout ? VISIBLE : GONE);
            btnCompartilhar.setVisibility(showLayout ? VISIBLE : GONE);

            if (showLayout) {
                animarEntradaEdicao();
            }
    }

    private void animarEntradaEdicao() {
        // Prepara o estado inicial das views de edição
        View[] views = {lytFiltros, lytSubOpcoes, scrollViewMeta, cvTotalAnual, btnConfirmar, btnCancelar};
        
        for (View v : views) {
            if (v != null) {
                v.setAlpha(0f);
                v.setTranslationY(30f);
                v.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(400)
                    .start();
            }
        }
        
        // Animação específica para o ícone de compartilhar na Toolbar
        btnCompartilhar.setAlpha(0f);
        btnCompartilhar.setScaleX(0.5f);
        btnCompartilhar.setScaleY(0.5f);
        btnCompartilhar.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(300).start();
    }

    private void popularAnos() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> listaAnos = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            listaAnos.add(anoAtual + i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaAnos);
        adapter.setDropDownViewResource(R.layout.simple_list_item_spinner_m);
        spAnos.setAdapter(adapter);
        
        spAnos.setSelection(0); // Seleciona o primeiro (ano atual)
        anoSelecionado = anoAtual;

        spAnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anoSelecionado = (int) parent.getItemAtPosition(position);
                if (lytFiltros.getVisibility() == VISIBLE) {
                    atualizarTituloToolbar();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void popularUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        List<Usuario> listaUsuarios = usuarioDAO.getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND NOME_USUARIO <> '' AND USUARIO_TIPO <> 'D'ORDER BY NOME_USUARIO");
        Usuario placeholder = new Usuario();
        placeholder.setId_usuario("0");
        placeholder.setNome_usuario("Selecione um representante...");
        listaUsuarios.add(0, placeholder);

        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaUsuarios);
        adapter.setDropDownViewResource(R.layout.simple_list_item_spinner_m);
        spUsuarios.setAdapter(adapter);
        spUsuarios.setSelection(0);
        idUsuarioSelecionado = "0";
        idVendedorSelecionado = "0";

        spUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Usuario selectedUser = (Usuario) parent.getItemAtPosition(position);
                    if (selectedUser.getId_usuario().equals("0")) {
                        idUsuarioSelecionado = null;
                        idVendedorSelecionado = null;
                        if ( tipoMeta.equalsIgnoreCase("M"))
                           limparCamposMeta();
                    } else {
                        idUsuarioSelecionado = selectedUser.getId_usuario();
                        idVendedorSelecionado = selectedUser.getId_quando_vendedor();
                        dispararBuscaCompleta(true);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void carregarMetasDoFirebase(boolean exibirProgresso) {
        if (idVendedorSelecionado == null || idVendedorSelecionado.equals("0")) {
            if (!tipoMeta.equals("E")) {
                cvTotalAnual.setVisibility(GONE);
                return;
            }
        }

        if (tipoMeta.equals("G") || tipoMeta.equals("C") || tipoMeta.equals("S")) {
            if (idSubOpcaoSelecionada == null || idSubOpcaoSelecionada.equals("0")) {
                cvTotalAnual.setVisibility(GONE);
                return;
            }
        }

        if (tipoMeta.equals("E")) {
            if (idEmpresaSelecionada == null || idEmpresaSelecionada.equals("0")) {
                cvTotalAnual.setVisibility(GONE);
                return;
            }
        }
        
        cvTotalAnual.setVisibility(VISIBLE);

        if (exibirProgresso) {
            showLoading("Carregando metas...");
        }

        if (tipoMeta.equals("S")) {
            new FirestoreConexao().getMetaSemanal(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idVendedorSelecionado, idSubOpcaoSelecionada, new FirestoreConexao.OnMetaSemanalRetorno() {
                @Override
                public void onSucesso(MetaSemanal metaSemanal) {
                    if (exibirProgresso) hideLoading();
                    if (metaSemanal != null) {
                        edtJan.setText(metaSemanal.getSemana1());
                        edtFev.setText(metaSemanal.getSemana2());
                        edtMar.setText(metaSemanal.getSemana3());
                        edtAbr.setText(metaSemanal.getSemana4());
                        edtMai.setText(metaSemanal.getSemana5());
                        edtJun.setText(metaSemanal.getSemana6());
                    } else {
                        limparCamposMeta();
                    }
                }

                @Override
                public void onFalha(Exception e) {
                    if (exibirProgresso) hideLoading();
                    DialogHelper.showFeedbackDialog(context, "Erro", "Falha ao carregar metas semanais: " + e.getMessage(), false, R.drawable.ic_baseline_error_outline_24, null, null);
                }
            });
        } else {
            FirestoreConexao.OnMetaRetorno callback = new FirestoreConexao.OnMetaRetorno() {
                @Override
                public void onSucesso(MesAno mesAno) {
                    if (exibirProgresso) hideLoading();

                    if (mesAno != null) {
                        edtJan.setText(mesAno.getJan());
                        edtFev.setText(mesAno.getFev());
                        edtMar.setText(mesAno.getMar());
                        edtAbr.setText(mesAno.getAbr());
                        edtMai.setText(mesAno.getMai());
                        edtJun.setText(mesAno.getJun());
                        edtJul.setText(mesAno.getJul());
                        edtAgo.setText(mesAno.getAgo());
                        edtSet.setText(mesAno.getSet());
                        edtOut.setText(mesAno.getOut());
                        edtNov.setText(mesAno.getNov());
                        edtDez.setText(mesAno.getDez());
                    } else {
                        limparCamposMeta();
                    }
                    //getRealizacaoVendas(Integer.parseInt(idUsuarioSelecionado), anoSelecionado, exibirProgresso);
                }

                @Override
                public void onFalha(Exception e) {
                    if (exibirProgresso) hideLoading();
                    DialogHelper.showFeedbackDialog(context, "Erro", "Falha ao carregar metas: " + e.getMessage(), false, R.drawable.ic_baseline_error_outline_24, null, null);
                }
            };

            if (tipoMeta.equals("M")) {
                new FirestoreConexao().getMetaMesAno(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idVendedorSelecionado, callback);
            } else if (tipoMeta.equals("E")) {
                new FirestoreConexao().getMetaEmpresa(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idEmpresaSelecionada, callback);
            } else {
                new FirestoreConexao().getMetaMesAnoSubOpcao(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idVendedorSelecionado, idSubOpcaoSelecionada, tipoMeta, callback);
            }
        }
    }

    private void dispararBuscaCompleta(boolean exibirProgresso) {
        alterado = false;
        
        if (tipoMeta.equals("E")) {
            if (idEmpresaSelecionada != null) {
                carregarMetasDoFirebase(exibirProgresso);
                // No futuro, buscar realizado da empresa aqui se necessário
            }
            return;
        }

        if (idUsuarioSelecionado == null || idUsuarioSelecionado.equals("0")) return;

        // Se for mensal, dispara direto
        if (tipoMeta.equals("M")) {
            carregarMetasDoFirebase(exibirProgresso);
            getRealizacaoVendas(Integer.parseInt(idUsuarioSelecionado), anoSelecionado, 0, exibirProgresso);
        } else {
            // Se for Grupo/Marca/Semana, precisa da sub-opção
            if (idSubOpcaoSelecionada != null && !idSubOpcaoSelecionada.equals("0")) {
                carregarMetasDoFirebase(exibirProgresso);
                if (tipoMeta.equals("G") || tipoMeta.equals("C")) {
                    getRealizacaoVendas(Integer.parseInt(idUsuarioSelecionado), anoSelecionado, Integer.parseInt(idSubOpcaoSelecionada), exibirProgresso);
                }
            }
        }
    }
    private void getRealizacaoVendas(int idUsuario, int anoSelecionado,  int idSubOpcao, boolean exibirProgresso) {

        Map<String, String> cabecalho = new HashMap<>();
        if (UsuarioHelper.getUsuario().getToken() == null || UsuarioHelper.getUsuario().getToken().isEmpty()) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        }
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<MesAno>> call = null;
        
        // CONGELA O TIPO ATUAL PARA EVITAR MISTURA NO CACHE
        final String tipoRequestAtual = this.tipoMeta;
        final String subOpcaoRequest = String.valueOf(idSubOpcao);

        if (!configuracao.getId().isEmpty()) {
            if (tipoRequestAtual.equalsIgnoreCase("M"))
               call = apiRotas.getSomaTotalRealizadoPeriodo(idUsuario, anoSelecionado, cabecalho);
            else if (tipoRequestAtual.equalsIgnoreCase("G"))
                call = apiRotas.getSomaTotalRealizadoPeriodoGrupo(idUsuario, anoSelecionado, idSubOpcao, cabecalho);
            else if (tipoRequestAtual.equalsIgnoreCase("C"))
                call = apiRotas.getSomaTotalRealizadoPeriodoMarca(idUsuario, anoSelecionado, idSubOpcao, cabecalho);
            
            if (call != null) {
                call.enqueue(new Callback<List<MesAno>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MesAno>> call, @NonNull Response<List<MesAno>> response) {
                        if (response.code() == 200 && response.body() != null && !response.body().isEmpty()) {
                            if (exibirProgresso) hideLoading();
                            MesAno realizado = response.body().get(0);
                            setTotalRealizados(realizado);

                            // Salva no Firestore usando o tipo que foi solicitado no início da busca
                            Object cacheObj;
                            if (tipoRequestAtual.equalsIgnoreCase("G") || tipoRequestAtual.equalsIgnoreCase("C")) {
                                MesAnoGrupoMarca wrapper = new MesAnoGrupoMarca();
                                wrapper.setCodigo(subOpcaoRequest);
                                wrapper.setMesAno(realizado);
                                cacheObj = wrapper;
                            } else {
                                cacheObj = realizado;
                            }
                            new FirestoreConexao().salvarRealizado(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idVendedorSelecionado, subOpcaoRequest, tipoRequestAtual, cacheObj, null);
                        } else {
                            carregarRealizadoDoCache(exibirProgresso);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<MesAno>> call, Throwable t) {
                        carregarRealizadoDoCache(exibirProgresso);
                    }
                });
            } else {
                if (exibirProgresso) hideLoading();
            }

        } else {
            if (exibirProgresso) hideLoading();
            Toast.makeText(context, "Parâmetros de acesso ao servidor não definido!", Toast.LENGTH_SHORT).show();
        }
    }

    private void carregarRealizadoDoCache(boolean exibirProgresso) {
        if (!tipoMeta.equalsIgnoreCase("M") && !tipoMeta.equalsIgnoreCase("G") && !tipoMeta.equalsIgnoreCase("C")) {
            if (exibirProgresso) hideLoading();
            Toast.makeText(context, "Falha ao obter dados da API.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirestoreConexao.OnMetaRetorno callback = new FirestoreConexao.OnMetaRetorno() {
            @Override
            public void onSucesso(MesAno mesAno) {
                if (exibirProgresso) hideLoading();
                if (mesAno != null) {
                    setTotalRealizados(mesAno);
                    Toast.makeText(context, "Dados carregados do cache (Firestore)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Falha na API e nenhum cache encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFalha(Exception e) {
                if (exibirProgresso) hideLoading();
                Toast.makeText(context, "Erro ao buscar cache: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        if (tipoMeta.equalsIgnoreCase("M")) {
            new FirestoreConexao().getRealizadoMesAno(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idVendedorSelecionado, callback);
        } else {
            new FirestoreConexao().getRealizadoMesAnoSubOpcao(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idVendedorSelecionado, idSubOpcaoSelecionada, tipoMeta, callback);
        }
    }

   /*
    private void getRealizacaoVendasMarcaGrupo(int idUsuario, int anoSelecionado, int idMarcaGrupo) {
        Map<String, String> cabecalho = new HashMap<>();
        if (UsuarioHelper.getUsuario().getToken() == null || UsuarioHelper.getUsuario().getToken().isEmpty()) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        }
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<MesAno>> call;
        if (!configuracao.getId().isEmpty()) {
            if ( tipoMeta.equalsIgnoreCase("G"))
               call = apiRotas.getSomaTotalRealizadoPeriodoGrupo(idUsuario, anoSelecionado, idMarcaGrupo, cabecalho);
            else
                call = apiRotas.getSomaTotalRealizadoPeriodoMarca(idUsuario, anoSelecionado, idMarcaGrupo, cabecalho);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<List<MesAno>> call, @NonNull Response<List<MesAno>> response) {
                    if (response.code() == 200) {
                        List<MesAno> lista = response.body();
                        assert lista != null;
                        if (!lista.isEmpty())
                            setTotalRealizados(lista.get(0));
                    } else {
                        Toast.makeText(context, "Falha ao obter a soma total de vendas realizadas no periodo", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<MesAno>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context, "Parametros de acesso ao servidor não definido!", Toast.LENGTH_SHORT).show();
        }
    }
*/
    private void setTotalRealizados(MesAno mesAno) {
        double total = 0;
        double jan = Double.parseDouble(mesAno.getJan() != null ? mesAno.getJan() : "0");
        double fev = Double.parseDouble(mesAno.getFev() != null ? mesAno.getFev() : "0");
        double mar = Double.parseDouble(mesAno.getMar() != null ? mesAno.getMar() : "0");
        double abr = Double.parseDouble(mesAno.getAbr() != null ? mesAno.getAbr() : "0");
        double mai = Double.parseDouble(mesAno.getMai() != null ? mesAno.getMai() : "0");
        double jun = Double.parseDouble(mesAno.getJun() != null ? mesAno.getJun() : "0");
        double jul = Double.parseDouble(mesAno.getJul() != null ? mesAno.getJul() : "0");
        double ago = Double.parseDouble(mesAno.getAgo() != null ? mesAno.getAgo() : "0");
        double set = Double.parseDouble(mesAno.getSet() != null ? mesAno.getSet() : "0");
        double out = Double.parseDouble(mesAno.getOut() != null ? mesAno.getOut() : "0");
        double nov = Double.parseDouble(mesAno.getNov() != null ? mesAno.getNov() : "0");
        double dez = Double.parseDouble(mesAno.getDez() != null ? mesAno.getDez() : "0");
        total = jan + fev + mar + abr + mai + jun + jul + ago + set + out + nov + dez;

        String anoAnt = String.valueOf(anoSelecionado - 1).substring(2);
        tvHelperJan.setText(formatarTextoRealizado(anoAnt, "Jan", jan, total));
        tvHelperFev.setText(formatarTextoRealizado(anoAnt, "Fev", fev, total));
        tvHelperMar.setText(formatarTextoRealizado(anoAnt, "Mar", mar, total));
        tvHelperAbr.setText(formatarTextoRealizado(anoAnt, "Abr", abr, total));
        tvHelperMai.setText(formatarTextoRealizado(anoAnt, "Mai", mai, total));
        tvHelperJun.setText(formatarTextoRealizado(anoAnt, "Jun", jun, total));
        tvHelperJul.setText(formatarTextoRealizado(anoAnt, "Jul", jul, total));
        tvHelperAgo.setText(formatarTextoRealizado(anoAnt, "Ago", ago, total));
        tvHelperSet.setText(formatarTextoRealizado(anoAnt, "Set", set, total));
        tvHelperOut.setText(formatarTextoRealizado(anoAnt, "Out", out, total));
        tvHelperNov.setText(formatarTextoRealizado(anoAnt, "Nov", nov, total));
        tvHelperDez.setText(formatarTextoRealizado(anoAnt, "Dez", dez, total));

        tvTotalRealizadoAnterior.setText(MascaraUtil.mascaraRealSimbolo(String.valueOf(total)));
        lblTotalRealizadoAnterior.setText("REALIZADO " + (anoSelecionado - 1) + ":");
        totalRealizadoAnterior = total;
        atualizarTotalAnual();
    }

    private String formatarTextoRealizado(String ano, String mes, double valor, double total) {
        String percentual = "";
        if (total > 0) {
            percentual = " (" + String.format(Locale.getDefault(), "%.1f%%", (valor / total) * 100) + ")";
        }
        return mes + "/" + ano + ": " + MascaraUtil.mascaraRealSimbolo(String.valueOf(valor)) + percentual;
    }

    private void limparCamposMeta() {
        edtJan.setText("0.00");
        edtFev.setText("0.00");
        edtMar.setText("0.00");
        edtAbr.setText("0.00");
        edtMai.setText("0.00");
        edtJun.setText("0.00");
        edtJul.setText("0.00");
        edtAgo.setText("0.00");
        edtSet.setText("0.00");
        edtOut.setText("0.00");
        edtNov.setText("0.00");
        edtDez.setText("0.00");
        atualizarTotalAnual();
    }

    private void showLoading(String title) {
        if (lytGlobalLoading != null) {
            tvGlobalLoadingTitle.setText(title);
            lytGlobalLoading.setVisibility(VISIBLE);
        }
    }

    private void hideLoading() {
        if (lytGlobalLoading != null) {
            lytGlobalLoading.setVisibility(GONE);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void esconderCardsMenu() {
        lytMesAno.setVisibility(GONE);
        lytMesAnoGrupo.setVisibility(GONE);
        lytMesAnoMarca.setVisibility(GONE);
        lytMesAnoSemana.setVisibility(GONE);
        lytMesAnoProduto.setVisibility(GONE);
        lytMesAnoEmpresa.setVisibility(GONE);
    }

    private void voltarEstadoInicial() {
        lytMesAno.setVisibility(VISIBLE);
        lytMesAnoGrupo.setVisibility(VISIBLE);
        lytMesAnoMarca.setVisibility(VISIBLE);
        lytMesAnoSemana.setVisibility(VISIBLE);
        lytMesAnoProduto.setVisibility(VISIBLE);
        lytMesAnoEmpresa.setVisibility(VISIBLE);
        
        animarEntrada();

        hideKeyboard();
        resetInputsMeta();
        limparCamposMeta();
        showHideLayoutFiltros(false);
        //lytMenuPrincipal.setVisibility(VISIBLE);
        btnAbrirCalendario.setVisibility(GONE);
        spUsuarios.setSelection(0);
        spEmpresas.setSelection(0);
        lytUsuarioFiltro.setVisibility(VISIBLE);
        lytEmpresaFiltro.setVisibility(GONE);
        spAnos.setSelection(0);
        idSubOpcaoSelecionada = null;
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(" ");
    }

    private void atualizarTituloToolbar() {
        if (getSupportActionBar() != null) {
            String prefixo = "Metas de Vendas";
            if (tipoMeta.equals("M")) prefixo = "Projeção de Vendas";
            else if (tipoMeta.equals("G")) prefixo = "Projeção por Grupo";
            else if (tipoMeta.equals("C")) prefixo = "Projeção por Marca";
            else if (tipoMeta.equals("S")) prefixo = "Projeção Semanal";
            else if (tipoMeta.equals("E")) prefixo = "Projeção por Empresa";

            getSupportActionBar().setTitle(prefixo + " " + anoSelecionado);
        }
    }

    private void replicarMetaJaneiro() {
        hideKeyboard();
        String valorJaneiro = edtJan.getText().toString();
        if (valorJaneiro.isEmpty() || valorJaneiro.equals("R$ 0,00")) {
            Toast.makeText(this, "Informe um valor em Janeiro para replicar!", Toast.LENGTH_SHORT).show();
            return;
        }

        String mensagem = "Deseja replicar o valor de Janeiro para os demais meses?";
        if (tipoMeta.equals("S")) {
            mensagem = "Deseja replicar o valor da Semana 1 para as demais semanas?";
        }

        DialogHelper.showActionFeedbackDialog(context, "Confirmação", mensagem, false, "SIM", "NÃO", null, new DialogHelper.OnActionSelectedListener() {
            @Override
            public void onPositive() {
                if (tipoMeta.equals("S")) {
                    // No modo semanal, replica apenas para as semanas que existem no mês selecionado
                    if (inputFev.getVisibility() == VISIBLE) edtFev.setText(valorJaneiro);
                    if (inputMar.getVisibility() == VISIBLE) edtMar.setText(valorJaneiro);
                    if (inputAbr.getVisibility() == VISIBLE) edtAbr.setText(valorJaneiro);
                    if (inputMai.getVisibility() == VISIBLE) edtMai.setText(valorJaneiro);
                    if (inputJun.getVisibility() == VISIBLE) edtJun.setText(valorJaneiro);
                } else {
                    edtFev.setText(valorJaneiro);
                    edtMar.setText(valorJaneiro);
                    edtAbr.setText(valorJaneiro);
                    edtMai.setText(valorJaneiro);
                    edtJun.setText(valorJaneiro);
                    edtJul.setText(valorJaneiro);
                    edtAgo.setText(valorJaneiro);
                    edtSet.setText(valorJaneiro);
                    edtOut.setText(valorJaneiro);
                    edtNov.setText(valorJaneiro);
                    edtDez.setText(valorJaneiro);
                }

                Toast.makeText(ActivityMetaIndicador.this, "Valor replicado!", Toast.LENGTH_SHORT).show();
                atualizarTotalAnual();
            }

            @Override
            public void onNegative() {
            }

            @Override
            public void onNeutral() {
            }
        });
    }

    private void compartilharMeta() {
        if (idVendedorSelecionado == null || idVendedorSelecionado.equals("0")) {
            Toast.makeText(this, "Selecione um representante antes de compartilhar!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((tipoMeta.equals("G") || tipoMeta.equals("C") || tipoMeta.equals("S")) && (idSubOpcaoSelecionada == null || idSubOpcaoSelecionada.equals("0"))) {
            String msg = "Selecione a sub-opção antes de compartilhar!";
            if (tipoMeta.equals("G")) msg = "Selecione um grupo!";
            else if (tipoMeta.equals("C")) msg = "Selecione uma marca!";
            else if (tipoMeta.equals("S")) msg = "Selecione um mês!";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        DialogHelper.showActionFeedbackDialog(context, "Compartilhar", "Escolha o formato da projeção para enviar:", false, "DETALHADO", "RESUMO", null, new DialogHelper.OnActionSelectedListener() {
            @Override
            public void onPositive() {
                gerarTextoCompartilhamento(true);
            }

            @Override
            public void onNegative() {
                gerarTextoCompartilhamento(false);
            }

            @Override
            public void onNeutral() {
            }
        });
    }

    private void gerarTextoCompartilhamento(boolean detalhado) {
        String representante = "Não selecionado";
        if (spUsuarios.getSelectedItem() != null) {
            representante = spUsuarios.getSelectedItem().toString();
        }

        String subOpcao = "";
        if (spSubOpcoes.getSelectedItem() != null) {
            subOpcao = spSubOpcoes.getSelectedItem().toString();
        }

        String tituloMsg = (detalhado ? "📊 DETALHAMENTO DE METAS" : "📊 PROJEÇÃO DE VENDAS");
        if (tipoMeta.equals("S")) tituloMsg = (detalhado ? "📊 DETALHAMENTO SEMANAL " : "📊 RESUMO SEMANAL ") ;//+ subOpcao.toUpperCase();

        StringBuilder sb = new StringBuilder();
        sb.append(tituloMsg).append("\n");
        sb.append("👤 Representante: ").append(representante).append("\n");
        sb.append("📅 Ano: ").append(anoSelecionado).append("\n");

        if (!subOpcao.isEmpty()) {
            if (tipoMeta.equals("G")) {
                sb.append("📁 Grupo: ").append(subOpcao).append("\n");
            } else if (tipoMeta.equals("C")) {
                sb.append("🏷️ Marca: ").append(subOpcao).append("\n");
            } else if (tipoMeta.equals("S")) {
                sb.append("📁 Mês: ").append(subOpcao.toUpperCase()).append("\n");
            }
        }
        sb.append("\n");

        if (detalhado) {
            TextInputLayout[] inputs = {inputJan, inputFev, inputMar, inputAbr, inputMai, inputJun, inputJul, inputAgo, inputSet, inputOut, inputNov, inputDez};
            EditText[] edts = {edtJan, edtFev, edtMar, edtAbr, edtMai, edtJun, edtJul, edtAgo, edtSet, edtOut, edtNov, edtDez};
            int limite = tipoMeta.equals("S") ? 6 : 12;

            for (int i = 0; i < limite; i++) {
                if (inputs[i].getVisibility() == VISIBLE) {
                    String labelLine = inputs[i].getHint().toString();
                    String prefixoIcone = "🔹 ";
                    
                    if (tipoMeta.equals("S")) {
                        labelLine = labelLine.toUpperCase();
                    }
                    
                    sb.append(prefixoIcone).append(labelLine).append(": ").append(edts[i].getText().toString()).append("\n");
                }
            }
            sb.append("───────────────\n");
        }

        sb.append("💰 Meta Total: ").append(tvTotalAnual.getText().toString()).append("\n");

        if (tvVariacaoPercentual.getVisibility() == VISIBLE) {
            sb.append("📈 Evolução vs ").append(anoSelecionado - 1).append(": ").append(tvVariacaoPercentual.getText().toString()).append("\n");
        }

        if (!detalhado && lytRealizadoAnoAnterior.getVisibility() == VISIBLE) {
            sb.append("📉 Realizado ").append(anoSelecionado - 1).append(": ").append(tvTotalRealizadoAnterior.getText().toString()).append("\n");
        }

        sb.append("\n*Gerado via RCK Sistemas*");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Compartilhar Projeção");
        startActivity(shareIntent);
    }

    private void salvarMeta() {
        hideKeyboard();
        
        if (tipoMeta.equals("E")) {
            if (idEmpresaSelecionada == null || idEmpresaSelecionada.equals("0")) {
                Toast.makeText(this, "Selecione uma empresa!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (idVendedorSelecionado == null || idVendedorSelecionado.equals("0")) {
                Toast.makeText(this, "Selecione um representante!", Toast.LENGTH_SHORT).show();
                return;
            }

            if ((tipoMeta.equals("G") || tipoMeta.equals("C") || tipoMeta.equals("S")) && (idSubOpcaoSelecionada == null || idSubOpcaoSelecionada.equals("0"))) {
                Toast.makeText(this, "Selecione a sub-opção!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String mensagem = "Deseja realmente salvar a projeção de vendas selecionada?";
        DialogHelper.showActionFeedbackDialog(context, "Confirmação", mensagem, false, "SIM", "NÃO", null, new DialogHelper.OnActionSelectedListener() {
            @Override
            public void onPositive() {
                executarSalvarMeta();
            }

            @Override
            public void onNegative() {
            }

            @Override
            public void onNeutral() {
            }
        });
    }

    private void executarSalvarMeta() {
        alterado = false;
        showLoading("Gravando metas...");

        Object meta;
        if (tipoMeta.equals("S")) {
            MetaSemanal semanal = new MetaSemanal();
            semanal.setSemana1(getValorLimpo(edtJan));
            semanal.setSemana2(getValorLimpo(edtFev));
            semanal.setSemana3(getValorLimpo(edtMar));
            semanal.setSemana4(getValorLimpo(edtAbr));
            semanal.setSemana5(getValorLimpo(edtMai));
            semanal.setSemana6(getValorLimpo(edtJun));
            meta = semanal;
        } else {
            MesAno mensal = new MesAno();
            mensal.setJan(getValorLimpo(edtJan));
            mensal.setFev(getValorLimpo(edtFev));
            mensal.setMar(getValorLimpo(edtMar));
            mensal.setAbr(getValorLimpo(edtAbr));
            mensal.setMai(getValorLimpo(edtMai));
            mensal.setJun(getValorLimpo(edtJun));
            mensal.setJul(getValorLimpo(edtJul));
            mensal.setAgo(getValorLimpo(edtAgo));
            mensal.setSet(getValorLimpo(edtSet));
            mensal.setOut(getValorLimpo(edtOut));
            mensal.setNov(getValorLimpo(edtNov));
            mensal.setDez(getValorLimpo(edtDez));
            
            if (tipoMeta.equals("G") || tipoMeta.equals("C")) {
                MesAnoGrupoMarca metaWrapper = new MesAnoGrupoMarca();
                metaWrapper.setCodigo(idSubOpcaoSelecionada);
                metaWrapper.setNome(spSubOpcoes.getSelectedItem() != null ? spSubOpcoes.getSelectedItem().toString() : "");
                metaWrapper.setMesAno(mensal);
                meta = metaWrapper;
            } else {
                meta = mensal;
            }
        }
        
        String idParaSalvar = idVendedorSelecionado;
        String subOpcaoParaSalvar = idSubOpcaoSelecionada;
        
        if (tipoMeta.equals("E")) {
            idParaSalvar = "EMPRESA"; // Placeholder pois idSubOpcao será usado para a empresa no salvarMeta do FirestoreConexao
            subOpcaoParaSalvar = idEmpresaSelecionada;
        }
        
        new FirestoreConexao().salvarMeta(configuracao.getCnpj_empresa(), String.valueOf(anoSelecionado), idParaSalvar, subOpcaoParaSalvar, tipoMeta, meta, new FirestoreConexao.OnSucessoFalha() {
            @Override
            public void onSucesso() {
                hideLoading();
                if (tipoMeta.equals("M") || tipoMeta.equals("S") || tipoMeta.equals("E")) {
                    String termo = tipoMeta.equals("E") ? "empresa" : "representante";
                    DialogHelper.showActionFeedbackDialog(context, "Sucesso", "Metas gravadas com sucesso! Deseja definir a meta para outra " + termo + "?", true, "SIM", "NÃO", null, new DialogHelper.OnActionSelectedListener() {
                        @Override
                        public void onPositive() {
                            if (tipoMeta.equals("E")) {
                                spEmpresas.setSelection(0);
                                limparCamposMeta();
                                spEmpresas.requestFocus();
                                spEmpresas.performClick();
                            } else {
                                spUsuarios.setSelection(0);
                                limparCamposMeta();
                                spUsuarios.requestFocus();
                                spUsuarios.performClick();
                            }
                        }

                        @Override
                        public void onNegative() {
                            voltarEstadoInicial();
                        }

                        @Override
                        public void onNeutral() {
                        }
                    });
                } else if (tipoMeta.equals("G") || tipoMeta.equals("C")) {
                    String subTipo = tipoMeta.equals("G") ? "grupo" : "marca/coleção";
                    DialogHelper.showActionFeedbackDialog(context, "Sucesso", "Metas gravadas com sucesso! Deseja definir a meta para outro " + subTipo + "?", true, "SIM", "NÃO", null, new DialogHelper.OnActionSelectedListener() {
                        @Override
                        public void onPositive() {
                            spSubOpcoes.setSelection(0);
                            limparCamposMeta();
                            spSubOpcoes.requestFocus();
                            spSubOpcoes.performClick();
                        }

                        @Override
                        public void onNegative() {
                            voltarEstadoInicial();
                        }

                        @Override
                        public void onNeutral() {
                        }
                    });
                } else {
                    DialogHelper.showFeedbackDialog(context, "Sucesso", "Metas gravadas com sucesso!", true, R.drawable.ic_check_circle, null, () -> voltarEstadoInicial());
                }
            }

            @Override
            public void onFalha(Exception e) {
                hideLoading();
                DialogHelper.showFeedbackDialog(context, "Erro", "Falha ao gravar metas: " + e.getMessage(), false, R.drawable.ic_baseline_error_outline_24, null, null);
            }
        });
    }

    private String getValorLimpo(EditText edt) {
        String text = edt.getText().toString();
        // Remove R$, pontos, vírgulas e espaços
        String clean = text.replaceAll("[R$\\s.,]", "");
        if (clean.isEmpty()) return "0";
        return clean;
    }

    private void atualizarTotalAnual() {
        double jan = Double.parseDouble(getValorLimpo(edtJan));
        double fev = Double.parseDouble(getValorLimpo(edtFev));
        double mar = Double.parseDouble(getValorLimpo(edtMar));
        double abr = Double.parseDouble(getValorLimpo(edtAbr));
        double mai = Double.parseDouble(getValorLimpo(edtMai));
        double jun = Double.parseDouble(getValorLimpo(edtJun));
        double jul = Double.parseDouble(getValorLimpo(edtJul));
        double ago = Double.parseDouble(getValorLimpo(edtAgo));
        double set = Double.parseDouble(getValorLimpo(edtSet));
        double out = Double.parseDouble(getValorLimpo(edtOut));
        double nov = Double.parseDouble(getValorLimpo(edtNov));
        double dez = Double.parseDouble(getValorLimpo(edtDez));

        double total = 0;

        if (tipoMeta.equals("S")) {
            // Soma apenas os campos de semanas visíveis no mês
            if (inputJan.getVisibility() == VISIBLE) total += jan;
            if (inputFev.getVisibility() == VISIBLE) total += fev;
            if (inputMar.getVisibility() == VISIBLE) total += mar;
            if (inputAbr.getVisibility() == VISIBLE) total += abr;
            if (inputMai.getVisibility() == VISIBLE) total += mai;
            if (inputJun.getVisibility() == VISIBLE) total += jun;
            lblTotalAcumulado.setText("META TOTAL MENSAL:");
        } else {
            total = jan + fev + mar + abr + mai + jun + jul + ago + set + out + nov + dez;
            lblTotalAcumulado.setText("META TOTAL ANUAL:");
        }

        tvTotalAnual.setText(MascaraUtil.mascaraRealSimbolo(String.valueOf(total / 100)));

        // Atualiza os Hints com a porcentagem apenas para meta anual/grupo/marca
        if (total > 0 && !tipoMeta.equals("S")) {
            inputJan.setHint("Janeiro (" + String.format(Locale.getDefault(), "%.1f%%", (jan / total) * 100) + ")");
            inputFev.setHint("Fevereiro (" + String.format(Locale.getDefault(), "%.1f%%", (fev / total) * 100) + ")");
            inputMar.setHint("Março (" + String.format(Locale.getDefault(), "%.1f%%", (mar / total) * 100) + ")");
            inputAbr.setHint("Abril (" + String.format(Locale.getDefault(), "%.1f%%", (abr / total) * 100) + ")");
            inputMai.setHint("Maio (" + String.format(Locale.getDefault(), "%.1f%%", (mai / total) * 100) + ")");
            inputJun.setHint("Junho (" + String.format(Locale.getDefault(), "%.1f%%", (jun / total) * 100) + ")");
            inputJul.setHint("Julho (" + String.format(Locale.getDefault(), "%.1f%%", (jul / total) * 100) + ")");
            inputAgo.setHint("Agosto (" + String.format(Locale.getDefault(), "%.1f%%", (ago / total) * 100) + ")");
            inputSet.setHint("Setembro (" + String.format(Locale.getDefault(), "%.1f%%", (set / total) * 100) + ")");
            inputOut.setHint("Outubro (" + String.format(Locale.getDefault(), "%.1f%%", (out / total) * 100) + ")");
            inputNov.setHint("Novembro (" + String.format(Locale.getDefault(), "%.1f%%", (nov / total) * 100) + ")");
            inputDez.setHint("Dezembro (" + String.format(Locale.getDefault(), "%.1f%%", (dez / total) * 100) + ")");
        }

        if (totalRealizadoAnterior > 0 && !tipoMeta.equals("S")) {
            double metaTotal = total / 100;
            double variacao = ((metaTotal - totalRealizadoAnterior) / totalRealizadoAnterior) * 100;
            
            tvVariacaoPercentual.setVisibility(VISIBLE);
            tvVariacaoPercentual.setText(String.format(Locale.getDefault(), "(%.2f%%)", variacao));
            
            if (variacao > 0) {
                tvVariacaoPercentual.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.green));
            } else if (variacao < 0) {
                tvVariacaoPercentual.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.red));
            } else {
                tvVariacaoPercentual.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorText));
            }
        } else {
            tvVariacaoPercentual.setVisibility(GONE);
        }
    }

    private void popularEmpresas() {
        EmpresaParametroDAO empresaDAO = new EmpresaParametroDAO(db);
        List<EmpresaParametro> listaEmpresas = empresaDAO.getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO ORDER BY NOME_FANTASIA");
        
        if (listaEmpresas.isEmpty()) {
            EmpresaParametro emp = new EmpresaParametro();
            emp.setId_empresa(1);
            emp.setNome_fantasia("Empresa Principal");
            emp.setCnpj_cpf(configuracao.getCnpj_empresa());
            listaEmpresas.add(emp);
        }

        ArrayAdapter<EmpresaParametro> adapter = new ArrayAdapter<EmpresaParametro>(this, android.R.layout.simple_spinner_item, listaEmpresas) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setText(listaEmpresas.get(position).getNome_fantasia());
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                tv.setText(listaEmpresas.get(position).getNome_fantasia());
                return tv;
            }
        };
        
        adapter.setDropDownViewResource(R.layout.simple_list_item_spinner_m);
        spEmpresas.setAdapter(adapter);
        spEmpresas.setSelection(0);
        idEmpresaSelecionada = String.valueOf(listaEmpresas.get(0).getId_empresa());

        spEmpresas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EmpresaParametro selected = (EmpresaParametro) parent.getItemAtPosition(position);
                idEmpresaSelecionada = String.valueOf(selected.getId_empresa());
                if (tipoMeta.equals("E")) {
                    dispararBuscaCompleta(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void popularGrupos() {
        ProdutoDAO produtoDAO = new ProdutoDAO(db);
        List<ProdutoGrupo> listaGrupos = produtoDAO.getListaGrupo("SELECT * FROM TBL_PRODUTO_GRUPO WHERE ATIVO = 'S' ORDER BY NOME_GRUPO");

        // Item placeholder
        ProdutoGrupo placeholder = new ProdutoGrupo();
        placeholder.setId_grupo("0");
        placeholder.setNome_grupo("Selecione um grupo...");
        listaGrupos.add(0, placeholder);

        ArrayAdapter<ProdutoGrupo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaGrupos);
        adapter.setDropDownViewResource(R.layout.simple_list_item_spinner_m);
        spSubOpcoes.setAdapter(adapter);
        spSubOpcoes.setSelection(0);

        spSubOpcoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProdutoGrupo selected = (ProdutoGrupo) parent.getItemAtPosition(position);
                if (selected.getId_grupo().equals("0")) {
                    idSubOpcaoSelecionada = null;
                    limparCamposMeta();
                } else {
                    idSubOpcaoSelecionada = selected.getId_grupo();
                    dispararBuscaCompleta(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void popularColecoes() {
        ProdutoLinhaColecaoDAO linhaDAO = new ProdutoLinhaColecaoDAO(db);
        List<ProdutoLinhaColecao> listaLinhas = linhaDAO.getLista("SELECT * FROM TBL_PRODUTO_LINHA_COLECAO WHERE ATIVO = 'S' ORDER BY NOME_DESCRICAO_LINHA");

        // Item placeholder
        ProdutoLinhaColecao placeholder = new ProdutoLinhaColecao();
        placeholder.setIdLinhaColecao(0);
        placeholder.setNomeDescricaoLinha("Selecione uma marca...");
        listaLinhas.add(0, placeholder);

        ArrayAdapter<ProdutoLinhaColecao> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaLinhas);
        adapter.setDropDownViewResource(R.layout.simple_list_item_spinner_m);
        spSubOpcoes.setAdapter(adapter);
        spSubOpcoes.setSelection(0);

        spSubOpcoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProdutoLinhaColecao selected = (ProdutoLinhaColecao) parent.getItemAtPosition(position);
                if (selected.getIdLinhaColecao() == 0) {
                    idSubOpcaoSelecionada = null;
                    limparCamposMeta();
                } else {
                    idSubOpcaoSelecionada = String.valueOf(selected.getIdLinhaColecao());
                    dispararBuscaCompleta(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void popularMesesSubOpcao() {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meses);
        adapter.setDropDownViewResource(R.layout.simple_list_item_spinner_m);
        spSubOpcoes.setAdapter(adapter);
        int mesAtual = Calendar.getInstance().get(Calendar.MONTH);
        spSubOpcoes.setSelection(mesAtual);

        spSubOpcoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSubOpcaoSelecionada = String.valueOf(position + 1);
                atualizarCamposParaSemanas(anoSelecionado, position + 1);
                dispararBuscaCompleta(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void atualizarCamposParaSemanas(int ano, int mes) {
        // Reseta todos para visíveis e com nomes padrão
        resetInputsMeta();
        
        // Desabilita helper texts em modo semanal
        toggleHelperTexts(false);
        
        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes - 1, 1);
        int ultimoDia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        TextInputLayout[] inputs = {inputJan, inputFev, inputMar, inputAbr, inputMai, inputJun};
        int semanaIdx = 0;
        int diaInicio = 1;

        while (diaInicio <= ultimoDia && semanaIdx < 6) {
            cal.set(ano, mes - 1, diaInicio);
            int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
            
            // Encontra o fim da semana (próximo sábado ou último dia do mês)
            int diasAteSabado = Calendar.SATURDAY - diaSemana;
            int diaFim = Math.min(diaInicio + diasAteSabado, ultimoDia);
            
            String label = (semanaIdx + 1) + "ª SEM. (" +
                    String.format("%02d", diaInicio) + "/" + String.format("%02d", mes) + " a " + 
                    String.format("%02d", diaFim) + "/" + String.format("%02d", mes) + ")";
            
            inputs[semanaIdx].setHint(label);
            inputs[semanaIdx].setVisibility(VISIBLE);
            
            diaInicio = diaFim + 1;
            semanaIdx++;
        }

        // Esconde os campos que não serão usados (7 a 12) e limpa seus valores
        inputJul.setVisibility(GONE); edtJul.setText("0.00");
        inputAgo.setVisibility(GONE); edtAgo.setText("0.00");
        inputSet.setVisibility(GONE); edtSet.setText("0.00");
        inputOut.setVisibility(GONE); edtOut.setText("0.00");
        inputNov.setVisibility(GONE); edtNov.setText("0.00");
        inputDez.setVisibility(GONE); edtDez.setText("0.00");
        
        // Esconde também os que sobraram do 1 ao 6 e limpa seus valores
        EditText[] edts = {edtJan, edtFev, edtMar, edtAbr, edtMai, edtJun};
        for (int i = semanaIdx; i < 6; i++) {
            inputs[i].setVisibility(GONE);
            edts[i].setText("0.00");
        }
    }

    private void resetInputsMeta() {
        // Habilita helper texts em modo mensal/grupo/marca
        toggleHelperTexts(true);
        inputJan.setHint("Janeiro"); inputJan.setVisibility(VISIBLE);
        inputFev.setHint("Fevereiro"); inputFev.setVisibility(VISIBLE);
        inputMar.setHint("Março"); inputMar.setVisibility(VISIBLE);
        inputAbr.setHint("Abril"); inputAbr.setVisibility(VISIBLE);
        inputMai.setHint("Maio"); inputMai.setVisibility(VISIBLE);
        inputJun.setHint("Junho"); inputJun.setVisibility(VISIBLE);
        inputJul.setHint("Julho"); inputJul.setVisibility(VISIBLE);
        inputAgo.setHint("Agosto"); inputAgo.setVisibility(VISIBLE);
        inputSet.setHint("Setembro"); inputSet.setVisibility(VISIBLE);
        inputOut.setHint("Outubro"); inputOut.setVisibility(VISIBLE);
        inputNov.setHint("Novembro"); inputNov.setVisibility(VISIBLE);
        inputDez.setHint("Dezembro"); inputDez.setVisibility(VISIBLE);
    }

    private void resetVisibilidadeBotoesMeta() {
        lytMesAno.setVisibility(VISIBLE);
        lytMesAnoGrupo.setVisibility(VISIBLE);
        lytMesAnoMarca.setVisibility(VISIBLE);
        lytMesAnoSemana.setVisibility(VISIBLE);
    }


    private void toggleHelperTexts(boolean visible) {
        tvHelperJan.setVisibility(visible ? VISIBLE : GONE);
        tvHelperFev.setVisibility(visible ? VISIBLE : GONE);
        tvHelperMar.setVisibility(visible ? VISIBLE : GONE);
        tvHelperAbr.setVisibility(visible ? VISIBLE : GONE);
        tvHelperMai.setVisibility(visible ? VISIBLE : GONE);
        tvHelperJun.setVisibility(visible ? VISIBLE : GONE);
        tvHelperJul.setVisibility(visible ? VISIBLE : GONE);
        tvHelperAgo.setVisibility(visible ? VISIBLE : GONE);
        tvHelperSet.setVisibility(visible ? VISIBLE : GONE);
        tvHelperOut.setVisibility(visible ? VISIBLE : GONE);
        tvHelperNov.setVisibility(visible ? VISIBLE : GONE);
        tvHelperDez.setVisibility(visible ? VISIBLE : GONE);
        lytRealizadoAnoAnterior.setVisibility(visible ? VISIBLE : GONE);
    }

    private void addWebUsuarioFirebase(String cnpj, Usuario webUsuario) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected()) {
            showFeedbackDialog("Você está offline. A copia de segurança não sera efetuada!.", false);
            return; // Interrompe o método
        }
        if ( cnpj == null || cnpj.isEmpty()) {
                showFeedbackDialog("Parametros inválidos para efetuar a cópia de segurança!", false);
                return;
        }
        /*
        progress = new ProgressDialog(PedidoHelper.getActivityPedidoMain());
        progress.setMessage("Efetuando cópia de segurança!Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();

         */

        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        for (Field field : webUsuario.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(webUsuario));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            fdb.collection(cnpj).document("TBL_WEB_USUARIO").collection(webUsuario.getId_quando_vendedor()).add(map).addOnSuccessListener(documentReference -> {
                        showFeedbackDialog("Cadastro efetuado com sucesso!");
                        //showMsgAlerta("Copia de segurança efetuada com sucesso!");
                    }
            ).addOnFailureListener(e -> {
                DialogHelper.showFeedbackDialog(context, "Atenção", "Falha ao efetuar o cadastro!!\n" + e.getMessage(), true, R.drawable.ic_baseline_error_outline_24, null, null );
                //showMsgAlerta("Falha ao efetuar a copia de segurança!!\n" + e.getMessage());
            });
        }, 500);

    }
    private void addMetaUsuarioFirebase(String cnpj, Usuario webUsuario) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected()) {
            showFeedbackDialog("Você está offline. A copia de segurança não sera efetuada!.", false);
            return; // Interrompe o método
        }
        if ( cnpj == null || cnpj.isEmpty()) {
            showFeedbackDialog("Parametros inválidos para efetuar a cópia de segurança!", false);
            return;
        }
        /*
        progress = new ProgressDialog(PedidoHelper.getActivityPedidoMain());
        progress.setMessage("Efetuando cópia de segurança!Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();

         */

        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        for (Field field : webUsuario.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(webUsuario));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            fdb.collection(cnpj).document("TBL_WEB_USARIO_META")
                    .collection(webUsuario.getId_quando_vendedor())
                    .document("META-MES-ANO-2026").set(mapMesAno()).addOnSuccessListener(documentReference -> {
                        showFeedbackDialog("Cadastro efetuado com sucesso!");
                        //showMsgAlerta("Copia de segurança efetuada com sucesso!");
                    }
            ).addOnFailureListener(e -> {
                DialogHelper.showFeedbackDialog(context, "Atenção", "Falha ao efetuar o cadastro!!\n" + e.getMessage(), true, R.drawable.ic_baseline_error_outline_24, null, null );
                //showMsgAlerta("Falha ao efetuar a copia de segurança!!\n" + e.getMessage());
            });
            // Estrutura sem o documento intermediário META-MES-ANO-2026
            fdb.collection(cnpj)
                    .document("TBL_WEB_USARIO_META")
                    .collection(webUsuario.getId_quando_vendedor())
                    .document("META-MES-ANO-2026-GRUPO")
                    .collection("02")
                    .document("02")
                    .set(mapMesAno())
                    .addOnSuccessListener(aVoid -> {
                        showFeedbackDialog("Cadastro de Grupo efetuado com sucesso!");
                    }
            ).addOnFailureListener(e -> {
                DialogHelper.showFeedbackDialog(context, "Atenção", "Falha ao gravar grupo!!\n" + e.getMessage(), true, R.drawable.ic_baseline_error_outline_24, null, null );
            });
/*
            fdb.collection(cnpj).document("TBL_WEB_USARIO_META").collection(webUsuario.getId_quando_vendedor()).document("META-MES-ANO-2026-MARCA").set(map).addOnSuccessListener(documentReference -> {
                        showFeedbackDialog("Cadastro efetuado com sucesso!");
                    }
            ).addOnFailureListener(e -> {
                DialogHelper.showFeedbackDialog(context, "Atenção", "Falha ao efetuar o cadastro!!\n" + e.getMessage(), true, R.drawable.ic_baseline_error_outline_24, null, null );
            });
            fdb.collection(cnpj).document("TBL_WEB_USARIO_META").collection(webUsuario.getId_quando_vendedor()).document("META-MES-ANO-2026-SEMANA").set(map).addOnSuccessListener(documentReference -> {
                        showFeedbackDialog("Cadastro efetuado com sucesso!");
                        //showMsgAlerta("Copia de segurança efetuada com sucesso!");
                    }
            ).addOnFailureListener(e -> {
                DialogHelper.showFeedbackDialog(context, "Atenção", "Falha ao efetuar o cadastro!!\n" + e.getMessage(), true, R.drawable.ic_baseline_error_outline_24, null, null );
            });

 */
        }, 500);

    }

    private Map mapMesAno() {
        Map<String, Object> map = new HashMap<>();
        MesAno mesAno = new MesAno();
        mesAno.setJan("925000");
        mesAno.setFev("135000");
        mesAno.setMar("145000");
        mesAno.setAbr("155000");
        mesAno.setMai("165000");
        mesAno.setJun("175000");
        mesAno.setJul("185000");
        mesAno.setAgo("195000");
        mesAno.setSet("205000");
        mesAno.setOut("215000");
        mesAno.setNov("225000");
        mesAno.setDez("235000");
        for (Field field : mesAno.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(mesAno));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  map;
    }
    private Map mapMesAnoGrupo(ProdutoGrupo produtoGrupo){
        Map<String, Object> map = new HashMap<>();
        MesAnoGrupoMarca mesAnoGrupoMarca = new MesAnoGrupoMarca();
        mesAnoGrupoMarca.setCodigo(produtoGrupo.getId_grupo());
        mesAnoGrupoMarca.setNome(produtoGrupo.getNome_grupo());
        MesAno mesAno = new MesAno();
        mesAno.setJan("125000");
        mesAno.setFev("135000");
        mesAno.setMar("145000");
        mesAno.setAbr("155000");
        mesAno.setMai("165000");
        mesAno.setJun("175000");
        mesAno.setJul("185000");
        mesAno.setAgo("195000");
        mesAno.setSet("205000");
        mesAno.setOut("215000");
        mesAno.setNov("225000");
        mesAno.setDez("235000");
        mesAnoGrupoMarca.setMesAno(mesAno);
        for (Field field : mesAnoGrupoMarca.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(mesAnoGrupoMarca));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  map;
    }

    @Override
    public void onBackPressed() {
        if (alterado) {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Alterações não salvas")
                    .setMessage("Deseja realmente sair sem salvar as metas?")
                    .setPositiveButton("Sair", (dialog, which) -> {
                        alterado = false;
                        super.onBackPressed();
                    })
                    .setNegativeButton("Continuar Editando", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso) {

        DialogHelper.showFeedbackDialog(
                context,
                "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_check_circle : R.drawable.ic_baseline_error_outline_24,
                null, // Passando a cor personalizada
                () -> {
                }
        );
    }

    public void showFeedbackDialog(String mensagem) {

        DialogHelper.showFeedbackDialog(
                context,
                "Atenção", // Título dinâmico
                mensagem,
                true,
                R.drawable.ic_baseline_check_circle_outline_24_color_primary,
                null, // Passando a cor personalizada
                () -> {

                }
        );
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


}
