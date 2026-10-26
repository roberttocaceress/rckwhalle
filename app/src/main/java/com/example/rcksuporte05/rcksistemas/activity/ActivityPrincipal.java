  package com.example.rcksuporte05.rcksistemas.activity;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.SincroniaBO;
import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity_.ActivityLogin_;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.BO.DashboardBO;
import com.example.rcksuporte05.rcksistemas.model.DashboardData;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.Biometria;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.FirestoreConexao;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, NavigationBarView.OnItemSelectedListener {

    @BindView(R.id.btnCliente)
    Button btnCliente;

    @BindView(R.id.btnProduto)
    Button btnProduto;

    @BindView(R.id.btnPedido)
    Button btnPedidos;

    @BindView(R.id.btnSincroniza)
    Button btnSincroniza;

    @BindView(R.id.btnPedidoFinalizado)
    Button btnPedidoFinalizado;

    @BindView(R.id.btnPedidoPendente)
    Button btnPedidoPendente;

    @BindView(R.id.btnProspectNovo)
    Button btnProspectNovo;

    @BindView(R.id.btnCampanha)
    Button btnCampanha;

    @BindView(R.id.btnOutrosIndicadores)
    Button btnOutrosIndicadores;

    @BindView(R.id.btnOcultarBI)
    ImageButton btnOcultarBI;

    @BindView(R.id.lytConteudoBI)
    LinearLayout lytConteudoBI;

    @BindView(R.id.tvPorcentagemSemana)
    TextView tvPorcentagemSemana;

    @BindView(R.id.tvTituloMetaSemana)
    TextView tvTituloMetaSemana;

    @BindView(R.id.tvValoresSemana)
    TextView tvValoresSemana;

    @BindView(R.id.tvRankingSemana)
    TextView tvRankingSemana;

    @BindView(R.id.tvPorcentagemMes)
    TextView tvPorcentagemMes;

    @BindView(R.id.tvTituloMetaMes)
    TextView tvTituloMetaMes;

    @BindView(R.id.tvValoresMes)
    TextView tvValoresMes;

    @BindView(R.id.tvRankingMes)
    TextView tvRankingMes;

    @BindView(R.id.tvFaltaMes)
    TextView tvFaltaMes;

    @BindView(R.id.tvQtdeVisitas)
    TextView tvQtdeVisitas;
    @BindView(R.id.tvPedidosFaturados)
    TextView tvPedidosFaturados;



    @BindView(R.id.tvPedidosDevolvidos)
    TextView tvPedidosDevolvidos;

    @BindView(R.id.tvPedidosCancelados)
    TextView tvPedidosCancelados;

    @BindView(R.id.tvNovosClientes)
    TextView tvNovosClientes;

    @BindView(R.id.progressSemana)
    ProgressBar progressSemana;

    @BindView(R.id.progressMes)
    ProgressBar progressMes;

    @BindView(R.id.tvPositivacao)
    TextView tvPositivacao;

    @BindView(R.id.tvTicketMedio)
    TextView tvTicketMedio;

    @BindView(R.id.tvPedidosATransmitir)
    TextView tvPedidosATransmitir;

    @BindView(R.id.tvUltimaAtualizacao)
    TextView tvUltimaAtualizacao;

    private int realQtdPendentes = 0;
    private double realValorPendentes = 0.0;
    private DashboardBO dashboardBO;
    private DashboardData currentDashboardData;

    @BindView(R.id.txvProspectNovo)
    TextView txvProspectNovo;

    @BindView(R.id.ivInternet)
    ImageView ivInternet;

    @BindView(R.id.lytCampanhas)
    LinearLayout lytCampanhas;

    //@BindView(R.id.lytGrafico)
    //LinearLayout lytGrafico;

    //@BindView(R.id.barChartPrincipal)
    //BarChart barChart;

    //@BindView((R.id.txvIndicadores))
    //TextView txvIndicadores;

    @BindView((R.id.shimmerLayout))
    ShimmerFrameLayout shimmerLayout;

    @BindView((R.id.txvAguarde))
    TextView txvAguarde;

    //@BindView(R.id.barChartSecundario)
    //BarChart barChartSecundario;

    //@BindView(R.id.edtDataSincronia)
    static TextView edtDataSincronia;

    private int aperta = 0;
    private SincroniaBO sincroniaBO = new SincroniaBO();
    private Sincronia sincronia;
    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioBO usuarioBO = new UsuarioBO();
    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private Configuracao configuracao;
    private Uri uri;
    public Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Context context;
    private static  Context scontext ;
    PackageInfo pInfo = null;
    public static boolean sincronizando = false;
    private Intent itSincronia = null;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private int tExtraFonte = 0;
    private BottomNavigationView navigationBottom;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private static final long DELAY_OPEN_ACTIVITY = 800; // 400 milissegundos de delay
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_drawer);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // Força a barra de status e navegação a terem a mesma cor da Toolbar
        getWindow().setStatusBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));

        /*
        View mainContainer = findViewById(R.id.drawerLayout);
        ViewCompat.setOnApplyWindowInsetsListener(mainContainer, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });
         */
        // 3. Ajuste para o Menu Lateral (Drawer)
        navigationView = (NavigationView) findViewById(R.id.navView);
        ViewCompat.setOnApplyWindowInsetsListener(navigationView, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, insets.top, 0, insets.bottom);
            return windowInsets;
        });
        ButterKnife.bind(this);
        /*
        barChart.setVisibility(VISIBLE);
        lytGrafico.setVisibility(VISIBLE);
        barChart.setNoDataText("Aguarde...");
         */
        EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        if ( !usuarioIsLogado())
             DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao obter dados sobre o usuário logado! Refaça seu login!",  R.drawable.ic_alerta_mensagem);
        getConfiguracao();
        
        if (configuracao != null && configuracao.getTrabalha_com_campanhas() != null && configuracao.getTrabalha_com_campanhas().equalsIgnoreCase("N")){
            lytCampanhas.setVisibility(INVISIBLE);
        }
        
        try {
            if (configuracao != null && configuracao.getTrabalha_com_cobranca() != null && (configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("S") || configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("V"))) {
                btnProspectNovo.setBackgroundResource(R.mipmap.ic_conta_receber);
                btnProspectNovo.refreshDrawableState();
            } else {
                btnProspectNovo.setBackgroundResource(R.mipmap.ic_add_prospect);
                btnProspectNovo.setVisibility(INVISIBLE);
                txvProspectNovo.setVisibility(INVISIBLE);
            }
        } catch (Exception e) {
            btnProspectNovo.setBackgroundResource(R.mipmap.ic_add_prospect);
            btnProspectNovo.setVisibility(INVISIBLE);
            txvProspectNovo.setVisibility(INVISIBLE);
        }

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        scontext = this;
        /*
        try {
            if (configuracao.getVisualiza_grafico_on_line_app().equalsIgnoreCase("S"))
                getEvolucaoVendas();
            else {
                btnOutrosIndicadores.setClickable(false);
                btnOutrosIndicadores.setEnabled(false);
                showHideLytGrafico();
            }
        } catch (NullPointerException e) {
            btnOutrosIndicadores.setClickable(false);
            btnOutrosIndicadores.setEnabled(false);
            showHideLytGrafico();
        }
        */
        edtDataSincronia = (TextView) findViewById(R.id.edtDataSincronia);
        try {
            String dataSincStr = db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA");
            if (dataSincStr != null && !dataSincStr.isEmpty()) {
                edtDataSincronia.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(dataSincStr)));
                edtDataSincronia.setText(edtDataSincronia.getText().toString() + "\n " + new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(dataSincStr)));
            } else {
                edtDataSincronia.setText("00/00/00" + "\n " + "00:00:00");
            }
        } catch (ParseException| NullPointerException e) {
            edtDataSincronia.setText("00/00/00" + "\n " + "00:00:00");
            e.printStackTrace();
        }
        btnPedidos.setOnClickListener(this);
        btnProduto.setOnClickListener(this);
        btnCliente.setOnClickListener(this);
        btnSincroniza.setOnClickListener(this);
        btnPedidoPendente.setOnClickListener(this);
        btnPedidoFinalizado.setOnClickListener(this);
        btnProspectNovo.setOnClickListener(this);
        btnCampanha.setOnClickListener(this);
        btnOutrosIndicadores.setOnClickListener(this);
        btnOcultarBI.setOnClickListener(this);
        //btnOutrosIndicadores.setClickable(false);
        //btnOutrosIndicadores.setEnabled(false);
        /*
        lytGrafico.setClickable(false);
        lytGrafico.setEnabled(false);
        barChart.setClickable(false);
        barChart.setEnabled(false);
        */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationBottom = (BottomNavigationView) findViewById(R.id.navigationBottom);
        navigationBottom.setOnItemSelectedListener(this);

        //navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        //navigationView.getMenu().clear();
        //navigationView.inflateMenu(R.menu.nav_menu);
        /*
        try {
            if (configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("N") || configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("V"))
                navigationView.getMenu().findItem(R.id.nav_principal_cliente).setVisible(false);
        } catch ( NullPointerException e) {
            navigationView.getMenu().findItem(R.id.nav_principal_cliente).setVisible(false);
        }
        */
        try {
            if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("V")) {
                //navigationView.getMenu().findItem(R.id.nav_principal_tecnico).setVisible(false);
                //navigationView.getMenu().findItem(R.id.nav_principal_financeiro).setVisible(false);
                //navigationView.getMenu().findItem(R.id.nav_principal_estoque).setVisible(false);

            }
        } catch ( NullPointerException e) {
            navigationView.getMenu().findItem(R.id.nav_principal_tecnico).setVisible(false);
            //navigationView.getMenu().findItem(R.id.nav_principal_financeiro).setVisible(false);
            //navigationView.getMenu().findItem(R.id.nav_principal_estoque).setVisible(false);

        }

        try {
            if (configuracao.getSincronia_automatica_app().equalsIgnoreCase("S")) {
                if (configuracao.getConfirma_sincronia_dados().equalsIgnoreCase("S"))
                    usuarioIsLogado2();
                verificaDataHoraSincronia();
            }
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
            int screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;
            if (tExtraFonte == 0) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("Tamanho fonte(P)");
                        break;
                    default:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("T. fonte(P)");
                }
                navigationView.setItemTextAppearance(R.style.Base_Theme_MeuTemaDrawnerPadrao);

            } else if (tExtraFonte == 2) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("Tamanho fonte(M)");
                        break;
                    default:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("T. fonte(M)");
                }
                navigationView.setItemTextAppearance(R.style.Base_Theme_MeuTemaDrawnerExtra1);

            } else if (tExtraFonte == 4) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("Tamanho fonte(G)");
                        break;
                    default:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("T. fonte(G)");
                }
                navigationView.setItemTextAppearance(R.style.Base_Theme_MeuTemaDrawnerExtra2);
            }
        } catch (NullPointerException e) {
        }
        getUsuarios();
        shimmerLayout.stopShimmer();
        shimmerLayout.hideShimmer();
        txvAguarde.setVisibility(GONE);
    }


    @Override
    protected void onResume() {
        try {
            try {
                ClienteHelper.clear();
            } catch (NullPointerException e) {

            } catch ( Exception e) {

            }
            try {
                String dataSincStr = db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA");
                if (dataSincStr != null && !dataSincStr.isEmpty()) {
                    edtDataSincronia.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(dataSincStr)));
                    edtDataSincronia.setText(edtDataSincronia.getText().toString() + "\n " + new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(dataSincStr)));
                } else {
                    edtDataSincronia.setText("00/00/00" + "\n " + "00:00:00");
                }
            } catch (ParseException | NullPointerException e) {
                edtDataSincronia.setText("00/00/00" + "\n " + "00:00:00");
                e.printStackTrace();
            }
            if (getIntent().getIntExtra("alterado", 0) == 1) {
                String versaoApp = "-";
                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    versaoApp = pInfo.versionName.substring(0, 5);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                boolean cobranca = false;
                try {
                    EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(new DBHelper(this));
                    EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
                    if (empresaParametro.getUtiliza_cobranca_offline().equalsIgnoreCase("S"))
                        cobranca = true;
                } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
                } catch (Exception e) {
                }
                //sincroniaBO.sincronizaApi(new Sincronia(true, true, true, false, false, false, false, false, cobranca), versaoApp);
                //getIntent().putExtra("alterado", 0);
                startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                finish();
            }
            setTituloToolBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
            int screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;
            if (tExtraFonte == 0) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("Tamanho fonte(P)");
                        break;
                    default:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("T. fonte(P)");
                }
                navigationView.setItemTextAppearance(R.style.Base_Theme_MeuTemaDrawnerPadrao);

            } else if (tExtraFonte == 2) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("Tamanho fonte(M)");
                        break;
                    default:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("T. fonte(M)");
                }
                navigationView.setItemTextAppearance(R.style.Base_Theme_MeuTemaDrawnerExtra1);

            } else if (tExtraFonte == 4) {
                switch (screenSize) {
                    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("Tamanho fonte(G)");
                        break;
                    default:
                        navigationBottom.getMenu().findItem(R.id.acessibilidaApp).setTitle("T. fonte(G)");
                }
                navigationView.setItemTextAppearance(R.style.Base_Theme_MeuTemaDrawnerExtra2);
            }
        } catch (NullPointerException e) {
        }
        getConfiguracao(); // Garante dados atualizados para o BI antes de carregar o dashboard
        carregarDadosDashboard();
        setupVisibilidadeBI();
        super.onResume();
    }
    @Override
    protected void onRestart() {
        /*
        try {
            if (configuracao.getSincronia_automatica_app().equalsIgnoreCase("S")) {
                if (configuracao.getConfirma_sincronia_dados().equalsIgnoreCase("S"))
                    usuarioIsLogado2();
                verificaDataHoraSincronia();
            }
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
         */
        super.onRestart();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /*
    private void showHideLytGrafico(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lytGrafico.getLayoutParams();
        lytGrafico.setMinimumHeight(0);
        lytGrafico.setPadding(0, 0, 0, 0); // O padding também pode ocupar espaço
        params.height = 0;
        lytGrafico.setLayoutParams(params);
        lytGrafico.requestLayout();
        lytGrafico.setVisibility(GONE);
        txvIndicadores.setVisibility(GONE);

    }

     */
    public boolean usuarioIsLogado() {
        try {
            if (UsuarioHelper.getUsuario() == null || (UsuarioHelper.getUsuario().getId_usuario() == null || UsuarioHelper.getUsuario().getId_usuario().isEmpty())) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPrincipal.this));
                if (UsuarioHelper.getUsuario() == null) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!",  R.drawable.ic_baseline_error_outline_24);
                    return false;
                }
            }
        } catch ( NullPointerException e) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!\n" + e.getMessage(),  R.drawable.ic_baseline_error_outline_24);
            return false;
        } catch ( Exception e) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!!\n" + e.getMessage(),  R.drawable.ic_baseline_error_outline_24);
            return false;
        }
        return true;
    }

    public boolean usuarioIsLogado2() {
        try {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPrincipal.this));
                if (UsuarioHelper.getUsuario() == null) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!",  R.drawable.ic_baseline_error_outline_24);
                    return false;
                }
        } catch ( NullPointerException e) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!\n" + e.getMessage(),  R.drawable.ic_baseline_error_outline_24);
            return false;
        } catch ( Exception e) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Erro ao obter dados do usuario logado, por gentileza refaça seu login no aplicativo!!!\n" + e.getMessage(),  R.drawable.ic_baseline_error_outline_24);
            return false;
        }
        return true;
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    public static void atualizaDataSincronia(String dataSincronia) {
        DBHelper dbs = new DBHelper(scontext);
        try {
            edtDataSincronia.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(dbs.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA"))));
            edtDataSincronia.setText(edtDataSincronia.getText().toString() + "\n " + new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(dbs.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA"))));
        } catch (ParseException| NullPointerException e) {
            edtDataSincronia.setText("00/00/00" + "\n " + "00:00:00");
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        //    edtDataSincronia.setText(dataSincronia);
    }


    public static  void updateApp(String url){
        try {
            if (!url.isEmpty())
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                scontext.startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }catch ( NullPointerException e ) {
            Toast.makeText(scontext, "A url/link para update do aplicativo é inválida!", Toast.LENGTH_SHORT).show();
        } catch ( ActivityNotFoundException e) {
            Toast.makeText(scontext, "A url/link para update do aplicativo é inválida!", Toast.LENGTH_SHORT).show();
        } catch ( Exception e) {
            Toast.makeText(scontext, "A url/link para update do aplicativo é inválida!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showMsgConfirmaSincronia() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPrincipal.this).inflate(R.layout.dialog_alert_sincronia, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrincipal.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        CheckBox check = (CheckBox) dialogView.findViewById(R.id.checkLembrarDepois);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked) {
                    String sql = "UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'S', USUARIO_LEMBRETE_SINCRONIA_DATA = '" + db.pegaDataAtual();
                    db.update("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'S', USUARIO_LEMBRETE_SINCRONIA_DATA = '" + db.pegaDataAtual() + "'" );
                } else {
                    db.update("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'N', USUARIO_LEMBRETE_SINCRONIA_DATA = ''");
                }
            }
        });

        Button btnNao = (Button) dialogView.findViewById(R.id.btnCancelar);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                return;
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnConfirmar);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                //setSincronizando();
                //executaSincronia();
            }
        });
        alertDialog.show();
    }

    public void verificaDataHoraSincronia() {
        String dataSincronia = "";
        String tempoSincronia = "0";

        try {
            String dataSincStr = db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA");
            if (dataSincStr != null && !dataSincStr.isEmpty()) {
                dataSincronia = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dataSincStr));
            }
            
            tempoSincronia = db.consultaDados("SELECT TEMPO_AUTO_SINCRONIA FROM TBL_EMPRESA_PARAMETRO", "TEMPO_AUTO_SINCRONIA");
            if (tempoSincronia == null || tempoSincronia.isEmpty())
                tempoSincronia = "0";
        } catch (ParseException | NullPointerException | CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        if (dataSincronia != null && !dataSincronia.equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
            if (!getSincronizando()) {
                // Se a data está vazia (primeiro acesso), vai direto para a sincronia
                if (dataSincronia.isEmpty()) {
                    startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                    return;
                }

                if (configuracao != null && configuracao.getConfirma_sincronia_dados() != null && configuracao.getConfirma_sincronia_dados().equalsIgnoreCase("S")) {
                    if (UsuarioHelper.getUsuario() != null && UsuarioHelper.getUsuario().getUsuario_lembrete_sincronia() != null && UsuarioHelper.getUsuario().getUsuario_lembrete_sincronia().equalsIgnoreCase("N")) {
                       try {
                            String dataSincStr = db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA");
                            if (dataSincStr == null || dataSincStr.isEmpty()) return;

                            Date dataSinc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dataSincStr);
                            if (dataSinc == null) return;

                            Date dataAtual = new Date();
                            long difference = dataAtual.getTime() - dataSinc.getTime();
                            int days = (int) (difference / (1000 * 60 * 60 * 24));
                            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                            if (tempoSincronia.equalsIgnoreCase("null")) {
                                if (!getSincronizando()) {
                                    //setSincronizando();
                                    //executaSincronia();
                                    startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                                }
                            } else {
                                if (!tempoSincronia.equalsIgnoreCase("0")) {
                                    if (hours >= Integer.parseInt(tempoSincronia.trim()) || days >= 1) {
                                        if ( days >= 3) {
                                            if (!getSincronizando()) {
                                                //setSincronizando();
                                                //executaSincronia();
                                                startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                                            }
                                            return;
                                        }
                                        if (!getSincronizando()) {
                                            if( configuracao.getConfirma_sincronia_dados().equalsIgnoreCase("S")) {
                                                showMsgConfirmaSincronia();
                                            }else {
                                                //setSincronizando();
                                                //executaSincronia();
                                                startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (ParseException | NullPointerException | NumberFormatException e) {
                        } catch (Exception e) {
                        }
                    } else {
                        if (!tempoSincronia.equalsIgnoreCase("0")) {
                            Date dataSinc = null;
                            try {
                                dataSinc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA"));
                                Date dataAtual = new Date();
                                long difference = dataAtual.getTime() - dataSinc.getTime();
                                long differenced = dataAtual.getDate() - dataSinc.getDate();
                                int days = (int) (difference / (1000 * 60 * 60 * 24));
                                int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                                if (differenced >= 3) {
                                    db.update("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'N', USUARIO_LEMBRETE_SINCRONIA_DATA = ''");
                                    //setSincronizando();
                                    //executaSincronia();
                                    startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                                    return;
                                }
                                if (differenced >= 1 && differenced <= 2) {
                                    Date dataLemb = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(db.consultaDados("SELECT USUARIO_LEMBRETE_SINCRONIA_DATA FROM TBL_LOGIN", "USUARIO_LEMBRETE_SINCRONIA_DATA"));
                                    differenced = dataAtual.getDate() - dataLemb.getDate();
                                    if ( differenced <= 0)
                                        return;
                                    db.update("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'N', USUARIO_LEMBRETE_SINCRONIA_DATA = ''");
                                    showMsgConfirmaSincronia();
                                    return;
                                }
                                if (differenced == 0 ) {

                                }
                                return;

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showMsgConfirmaSincronia();
                        }
                    }
                }else {
                    //setSincronizando();
                    //executaSincronia();
                    startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                }
            } else {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Sincronização em andamento",  R.drawable.ic_alerta_mensagem);
            }

        } else {
            if (configuracao.getConfirma_sincronia_dados().equalsIgnoreCase("S")) {
                if (UsuarioHelper.getUsuario().getUsuario_lembrete_sincronia().equalsIgnoreCase("N")) {
                    try {
                        Date dataSinc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA"));
                        Date dataAtual = new Date();
                        long difference = dataAtual.getTime() - dataSinc.getTime();
                        int days = (int) (difference / (1000 * 60 * 60 * 24));
                        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                        if (tempoSincronia.equalsIgnoreCase("null")) {
                            if (!getSincronizando()) {
                                //setSincronizando();
                                //executaSincronia();
                                startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                            }
                        } else {
                            if (!tempoSincronia.equalsIgnoreCase("0")) {
                                if (hours >= Integer.parseInt(tempoSincronia.trim())) {
                                    if (!getSincronizando()) {
                                        if( configuracao.getConfirma_sincronia_dados().equalsIgnoreCase("S")) {
                                            showMsgConfirmaSincronia();
                                        }else {
                                            //setSincronizando();
                                            //executaSincronia();
                                            startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ParseException | NullPointerException | NumberFormatException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                    }

                } else {
                    if (!tempoSincronia.equalsIgnoreCase("0")) {
                        Date dataSinc = null;
                        try {
                            dataSinc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA"));
                            Date dataAtual = new Date();
                            long difference = dataAtual.getTime() - dataSinc.getTime();
                            long differenced = dataAtual.getDate() - dataSinc.getDate();
                            int days = (int) (difference / (1000 * 60 * 60 * 24));
                            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                            if (differenced >= 3) {
                                db.update("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'N', USUARIO_LEMBRETE_SINCRONIA_DATA = ''");
                                //setSincronizando();
                                //executaSincronia();
                                startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                                return;
                            }
                            if (differenced >= 1 && differenced <= 2) {
                                Date dataLemb = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(db.consultaDados("SELECT USUARIO_LEMBRETE_SINCRONIA_DATA FROM TBL_LOGIN", "USUARIO_LEMBRETE_SINCRONIA_DATA"));
                                differenced = dataAtual.getDate() - dataLemb.getDate();
                                if ( differenced <= 0)
                                    return;
                                db.update("UPDATE TBL_LOGIN SET USUARIO_LEMBRETE_SINCRONIA = 'N', USUARIO_LEMBRETE_SINCRONIA_DATA = ''");
                                showMsgConfirmaSincronia();
                                return;
                            }
                            if (differenced == 0 ) {

                            }
                            return;

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showMsgConfirmaSincronia();
                    }
                }
            }else {
                try {
                    Date dataSinc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA"));
                    Date dataAtual = new Date();
                    long difference = dataAtual.getTime() - dataSinc.getTime();
                    int days = (int) (difference / (1000 * 60 * 60 * 24));
                    int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                    int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                    if (tempoSincronia.equalsIgnoreCase("null")) {
                        if (!getSincronizando()) {
                            //setSincronizando();
                            //executaSincronia();
                            startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                        }
                    } else {
                        if (!tempoSincronia.equalsIgnoreCase("0")) {
                            if (hours >= Integer.parseInt(tempoSincronia.trim())) {
                                if (!getSincronizando()) {
                                    if( configuracao.getConfirma_sincronia_dados().equalsIgnoreCase("S")) {
                                        showMsgConfirmaSincronia();
                                    }else {
                                        //setSincronizando();
                                        //executaSincronia();
                                        startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", true));
                                    }
                                }
                            }
                        }
                    }
                } catch (ParseException | NullPointerException | NumberFormatException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static boolean getSincronizando(){
        return sincronizando;
    }

    public static  boolean setSincronizando() {
        sincronizando = !sincronizando;
        return sincronizando;
    }





    /*
    public  void executaSincronia() {
        Toast.makeText(ActivityPrincipal.this, "A sincronização automatica de dados sera iniciada! Favor aguarde a finalização!", Toast.LENGTH_SHORT).show();
        SincroniaBO.setActivity(ActivityPrincipal.this);
        startActivity(new Intent(ActivityPrincipal.this, ActivityDialogSincronia.class).putExtra("autoSincroniza", true));
    }
     */

    public void setTituloToolBar(){
        toolbar.setTitle(getString(R.string.app_name));
        try {
            EmpresaParametro empresaParametro = empresaParametroDAO.getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO").get(0);
            if ( !empresaParametro.getNome_fantasia().isEmpty())
                toolbar.setTitle( empresaParametro.getNome_fantasia().toUpperCase());
        } catch ( NullPointerException|IndexOutOfBoundsException e) {
        } catch ( Exception e){
        }
        try {
            toolbar.setSubtitle("Olá: " + UsuarioHelper.getUsuario().getNome_usuario());
        } catch ( Exception e) {
            toolbar.setSubtitle("Olá: ");
        }
    }

    public void updateURLAPI() { //Utiliza a conexão padrao(1)
        ViewGroup viewGroup = ((Activity) context).findViewById(R.id.rootx);
        View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.dialog_alert_permissao, viewGroup, true);
        TextInputEditText edtCodigoAcesso = dialogView.findViewById(R.id.edtPwd);
        new AlertDialog.Builder(ActivityPrincipal.this)
                .setTitle("RCK WHALLE")
                .setMessage("Informe o código para a opção selecionada!")
                .setView(dialogView)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (edtCodigoAcesso.getText().toString().isEmpty()) {
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Código de acesso inválido!",  R.drawable.ic_alerta_mensagem);
                            return;
                        }
                        if (edtCodigoAcesso.getText().toString().trim().length() < 4) {
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Código de acesso inválido!", R.drawable.ic_alerta_mensagem);
                            return;
                        }
                        getDadosFirebase(FirebaseFirestore.getInstance(), edtCodigoAcesso.getText().toString());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Ação cancelada!",  R.drawable.ic_alerta_mensagem);
                    }
                })
                .show();
        edtCodigoAcesso.requestFocus();
    }
    private void getDadosFirebase(FirebaseFirestore fdb, String codigo) {
        try {
            fdb.collection("clientes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            boolean isValidCnpjCod = false;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if ( document.get("codigo").toString().equalsIgnoreCase(codigo)) {
                                        isValidCnpjCod = !isValidCnpjCod;
                                        updateConfiguracao(document);
                                        break;
                                    }
                                }
                                if (!isValidCnpjCod) {
                                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), " Código de segurança não localizado(s), favor confirmar os dados informados!",  R.drawable.ic_alerta_mensagem);
                                }
                            } else {
                                DialogHelper.showSnackInfo(findViewById(android.R.id.content), " Falha ao localizar os dados.\nFavor tentar novamente, caso o problema persista, entre em contato com o suporte técnico!",  R.drawable.ic_alerta_mensagem);
                            }
                        }
                    });
        }catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void updateConfiguracao(QueryDocumentSnapshot document) {
        Configuracao configuracao = new Configuracao();
        configuracao.setUrl_ip_1(document.get("ddns").toString().toLowerCase());
        configuracao.setPorta_1(Integer.parseInt(document.get("porta").toString()));
        configuracao.setUrl_ip_desc_1(document.get("descricao").toString().toUpperCase());
        configuracao.setConexao_segura(document.get("segura").toString());
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        if (configuracaoDAO.update(configuracao) > 0) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), " Dados atualizados com sucesso!!",  R.drawable.ic_alerta_mensagem);
            //finish();
        } else {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), " Falha ao atualizar os dados\nTente novamente, caso o problema persista, entre em contato  com o suporte técnico!",  R.drawable.ic_alerta_mensagem);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //int id = item.getItemId();
        //final Intent intent;
        final UsuarioBO usuarioBO = new UsuarioBO();
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_principal_logoff:
            case R.id.logoffApp:
                showActionDialog("Tem certeza que deseja sair/efetuar logoff?\n(O login só é possível novamente com acesso a internet/rede local)", true);
                //showMsgSimNao("Tem certeza que deseja sair?\n(O login só é possível com acesso a internet/rede local)");
                break;
            case R.id.nav_principal_configuracao:
                startActivity(new Intent(ActivityPrincipal.this, ActivityConfiguracao.class));
                break;
            //case R.id.nav_principal_metas_indicadores:
            //case R.id.metasApp:
                //Toast.makeText(context, "Opão em desenvolvimento", Toast.LENGTH_SHORT).show();
            //    startActivity(new Intent(ActivityPrincipal.this, ActivityBIHub.class));
            //    break;
            case R.id.nav_principal_preferencia:
                startActivity(new Intent(ActivityPrincipal.this, ActivityPreferencia.class));
                break;
             /*
            case R.id.nav_principal_cliente:
                configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                try {
                    if (configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("S"))
                        startActivity(new Intent(ActivityPrincipal.this, ActivitySemCobranca.class));

                } catch ( NullPointerException e) {
                } catch ( Exception e) {
                }
                break;
            */
            case R.id.nav_principal_vendas_por_grupo:
                startActivity(new Intent(ActivityPrincipal.this, ActivityDataFiltro.class).putExtra("tipoRelatorio", "G"));
                break;
            case R.id.nav_principal_vendas_por_marca:
               startActivity(new Intent(ActivityPrincipal.this, ActivityDataFiltro.class).putExtra("tipoRelatorio", "M"));
               break;
           /* case R.id.nav_principal_vendas_mes_a_mes_valor:
                startActivity(new Intent(ActivityPrincipal.this, ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "M"));
                break;
            case R.id.nav_principal_vendas_semana_a_semana_valor:
                startActivity(new Intent(ActivityPrincipal.this, ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "S"));
                break;

            case R.id.nav_principal_vendas_mes_a_mes_grupo:
                startActivity(new Intent(ActivityPrincipal.this, ActivityGraficoFaturamento.class).putExtra("tipoRelatorio", "MG"));
                break;*/


            //case R.id.nav_principal_cobranca:
            //     startActivity(new Intent(ActivityPrincipal.this, ActivityCobranca.class));
            //break;
                /*
            case R.id.nav_principal_alerta: {
                UsuarioHelper.setUsuario( usuarioBO.buscarUsuarioLogin( ActivityPrincipalDrawer.this));
                if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D"))
                    startActivity(new Intent(ActivityPrincipalDrawer.this, ActivityAlerta.class));
                else
                    Toast.makeText(ActivityPrincipalDrawer.this, "Usuario não tem permissão para acessar este módulo do aplicátivo!", Toast.LENGTH_LONG).show();
                break;
            }
            */
            /*
            case R.id.nav_principal_update:
                try {

                    loginNaApi(UsuarioHelper.getUsuario(), true);
                    break;
                } catch (NullPointerException e) {
                    Toast.makeText(ActivityPrincipal.this, "Aparelho não configurado para atualização!", Toast.LENGTH_SHORT).show();
                    break;
                } catch (Exception e) {
                    Toast.makeText(ActivityPrincipal.this, "Aparelho não configurado para atualização!", Toast.LENGTH_SHORT).show();
                    break;
                }
            */
            case R.id.nav_principal_usuario:
                startActivity(new Intent(ActivityPrincipal.this, ActivityUsuario.class)
                        .putExtra("razaoSocial", "")
                        .putExtra("url", "")
                        .putExtra("porta", "")
                        .putExtra("versaoAPI", "")
                        .putExtra("conexaoSegura", "")
                        .putExtra("acao", "usuario"));
                break;


            //case R.id.nav_principal_biometria:
            //    Biometria biometria = new Biometria();
            //    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&  biometria.isBiometricPromptEnabled() )
            //        startActivity(new Intent(ActivityPrincipal.this, ActivityBiometriaCadastro.class));
            //    else
           //         Toast.makeText( ActivityPrincipal.this, "Aparelho não dispoe de leitor de biometria!", Toast.LENGTH_SHORT).show();
            //    break;
            case R.id.nav_principal_update:
                try {
                    loginNaApi(UsuarioHelper.getUsuario(), true);
                } catch (NullPointerException e) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Aparelho não configurado para atualização!",  R.drawable.ic_alerta_mensagem);
                } catch (Exception e) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Aparelho não configurado para atualização!",  R.drawable.ic_alerta_mensagem);
                }
                break;
            case R.id.nav_principal_update_url:
                //Intent intentx = new Intent(context, ActivityFoto.class);
                //startActivity(intentx);
                updateURLAPI();
                break;
            //case R.id.nav_principal_video_aula:
            //Toast.makeText(ActivityPrincipal.this, "Opção em desenvolvimento!", Toast.LENGTH_LONG).show();
            //startActivity(new Intent(ActivityPrincipal.this, ActivityVideoAula.class));
            //break;
            //Relatorios a partir daqui   Cobrança -   Gerencial contas a pagar/receber - saldo de etoque
            /*
            case R.id.nav_principal_financeiro:
                try {
                    UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPrincipal.this));
                    if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("G") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("A") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D"))
                        startActivity(new Intent(ActivityPrincipal.this, ActivityFinanca.class));
                    else
                        Toast.makeText(ActivityPrincipal.this, "Usuario não tem permissão para acessar este módulo do aplicátivo!", Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    Toast.makeText(ActivityPrincipal.this, "Usuario não tem permissão para acessar este módulo do aplicátivo!", Toast.LENGTH_LONG).show();
                }
                break;
                case R.id.nav_principal_estoque:
                try {
                    UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivityPrincipal.this));
                    if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("G") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("A") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D"))
                        startActivity(new Intent(ActivityPrincipal.this, ActivityEstoqueSaldo.class));
                    else
                        Toast.makeText(ActivityPrincipal.this, "Usuario não tem permissão para acessar este módulo do aplicátivo!", Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    Toast.makeText(ActivityPrincipal.this, "Usuario não tem permissão para acessar este módulo do aplicátivo!", Toast.LENGTH_LONG).show();
                }
                break;
             */

            /*
            case R.id.nav_principal_nota_fiscal: {
                //Intent it = new Intent(ActivityPrincipalDrawer.this, ActivityNotaFiscal.class);
                startActivity(new Intent(ActivityPrincipalDrawer.this, ActivityNotaFiscal.class));
                break;
            }
            */
            //Menu suporte tecnico/video aulas
            case R.id.nav_principal_video_aula:
                //startActivity(new Intent(ActivityPrincipal.this, ActivityVideoAula.class));
                break;
            case R.id.nav_principal_whats:
            case R.id.suporteApp:
                PackageManager packageManager = context.getPackageManager();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String message = "Ola! Tudo bem? ";
                String phone = "5567993235558";
                try {
                    String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(message, "UTF-8");
                    intent.setPackage("com.whatsapp");
                    intent.setData(Uri.parse(url));
                    if (intent.resolveActivity(packageManager) != null) {
                        context.startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_principal_telefone:
                //intent = new Intent(Intent.ACTION_DIAL);
                //intent.setData(Uri.parse("tel:06733833878"));
                try {
                    startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:06733833878")));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Sem suporte para efetuar ligação!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.nav_principal_email:
                startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: rcksistemassuporte@rcksistemas.com.br")));
                break;

            case R.id.nav_principal_tecnico:
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPrincipal.this));
                //if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D"))

                showMsgDialog(0);
                    //else
                //    Toast.makeText(ActivityPrincipal.this, "Usuario não tem permissão para acessar este módulo do aplicátivo!", Toast.LENGTH_LONG).show();
                break;

            //Menu sobre
            case R.id.nav_principal_sobre:
                //startActivity(new Intent(ActivityPrincipal.this, ActivityAcessibilidade.class));
                startActivity(new Intent(ActivityPrincipal.this, ActivitySobre.class));
                break;

            case R.id.acessibilidaApp:
                startActivity(new Intent(context, ActivityAcessibilidade.class));
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showMsgDialog(int idxMenu) {
        ViewGroup viewGroup = ((Activity) context).findViewById(R.id.rootx);
        View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.dialog_alert_permissao, viewGroup, true);
        TextInputEditText edtCodigoAcesso = dialogView.findViewById(R.id.edtPwd);
        new AlertDialog.Builder(ActivityPrincipal.this)
                .setTitle("RCK SISTEMAS ESPECIFICOS")
                .setMessage("Informe o código de acesso para a opção selecionada!")
                .setView(dialogView)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (edtCodigoAcesso.getText().toString().trim().equalsIgnoreCase("102030")) {
                                startActivity(new Intent(context, ActivityTecnico.class));
                        }else {
                            showMsgDialog(idxMenu);
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Código de acesso inválido!",  R.drawable.ic_alerta_mensagem);
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Ação cancelada!",  R.drawable.ic_alerta_mensagem);
                    }
                })
                .show();
        edtCodigoAcesso.requestFocus();
    }

    public void DownloadFiles(String url){
        try {
            URL u = new URL(url);
            InputStream is = u.openStream();
            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "data/rck-whalle.apk"));
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        } catch ( Exception e) {
            Log.e("SYNC getUpdate", "security error", e);
        }
    }
    @Override
    public void onClick(View view) {
        if (view == btnCliente) {
            //getUsuarios();
            shimmerLayout.showShimmer(true);
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runnable = () -> {
                startActivity(new Intent(ActivityPrincipal.this, ActivityClienteDrawer.class));
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
            };
            handler.postDelayed(runnable, DELAY_OPEN_ACTIVITY);

        } else if (view == btnProduto) {
            //getUsuarios();
            shimmerLayout.showShimmer(true);
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runnable = () -> {
                startActivity(new Intent(ActivityPrincipal.this, ActivityProdutoDrawer.class));
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
            };
            handler.postDelayed(runnable, DELAY_OPEN_ACTIVITY);

        } else if (view == btnPedidos) {
            escolherEmpresaPedido();
        } else if (view == btnSincroniza) {
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runnable = () -> {
                startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", false));
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
            };
            handler.postDelayed(runnable, DELAY_OPEN_ACTIVITY);
            //startActivity(new Intent(ActivityPrincipal.this, ActivitySincronia.class).putExtra("autoSincroniza", false));
        } else if (view == btnPedidoFinalizado) {
            //getUsuarios();
            shimmerLayout.showShimmer(true);
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runnable = () -> {
                startActivity(new Intent(ActivityPrincipal.this, ActivityPedidoEnviado.class));
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
            };
            handler.postDelayed(runnable, DELAY_OPEN_ACTIVITY);
            //startActivity(new Intent(ActivityPrincipal.this, ActivityPedidoEnviado.class));
        } else if (view == btnPedidoPendente) {
            //getUsuarios();
            shimmerLayout.showShimmer(true);
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runnable = () -> {
                startActivity(new Intent(ActivityPrincipal.this, ActivityPedidoPendente.class));
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
            };
            handler.postDelayed(runnable, DELAY_OPEN_ACTIVITY);
            //startActivity(new Intent(ActivityPrincipal.this, ActivityPedidoPendente.class));
        } else if (view == btnProspectNovo) {
            //getUsuarios();
            configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            try {
                if (configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("S"))
                    startActivity(new Intent(ActivityPrincipal.this, ActivityCobranca.class));
                if (configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("V"))
                    startActivity(new Intent(ActivityPrincipal.this, ActivityDataFiltroCobranca.class));
            } catch (NullPointerException e) {
                startActivity( new Intent(ActivityPrincipal.this, ActivityProspectPendente.class));
            } catch (Exception e) {
                startActivity( new Intent(ActivityPrincipal.this, ActivityProspectPendente.class));
            }
        } else if (view == btnCampanha) {
            //getUsuarios();
            configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            try {
                if (configuracao.getTrabalha_com_campanhas().equalsIgnoreCase("S"))
                    startActivity(new Intent(ActivityPrincipal.this, ActivityCampanha.class));
                else
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Aplicativo não esta parametrizado para a utilização de campanha comercial!",  R.drawable.ic_alerta_mensagem);
            } catch (NullPointerException e) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao ler o arquivo de configuração!",  R.drawable.ic_baseline_error_outline_24);
            } catch (Exception e) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Falha ao ler o arquivo de configuração!!",  R.drawable.ic_baseline_error_outline_24);
            }
        }  else if(view == btnOutrosIndicadores) {
            getConfiguracao(); // Atualiza a configuração lendo da base local (importante para o primeiro acesso pós-sincronia)
            boolean isVendedor = "V".equalsIgnoreCase(UsuarioHelper.getUsuario().getUsuario_tipo());
            if (isVendedor && (configuracao == null || !"S".equalsIgnoreCase(configuracao.getVisualiza_grafico_on_line_app()))) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Sua configuração de sistema não permite a visualização destes indicadores!", R.drawable.ic_alerta_mensagem);
                return;
            }
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runnable = () -> {
                //startActivity(new Intent(ActivityPrincipal.this, ActivityPainelGraficoFaturamento.class));
                startActivity(new Intent(ActivityPrincipal.this, ActivityBIHub.class));
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
            };
            handler.postDelayed(runnable, DELAY_OPEN_ACTIVITY);
            //startActivity(new Intent(ActivityPrincipal.this, ActivityPainelGraficoFaturamento.class));
            //Toast.makeText(this, "Opção em desenvolvimento!!", Toast.LENGTH_SHORT).show();

        }
        else if (view == btnOcultarBI) {
            toggleVisibilidadeBI();
        }
        /*else if ( view == img_whatss) {
            drawerLayout.closeDrawers();
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String message = "Ola! Tudo bem? ";
            String phone = "5567993235558";
            try {
                String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(message, "UTF-8");
                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse(url));
                if (intent.resolveActivity(packageManager) != null) {
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ( view == img_telefone) {
            drawerLayout.closeDrawers();
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:06733833878")));
        } else if ( view == img_email) {
            drawerLayout.closeDrawers();
            startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: rcksistemassuporte@rcksistemas.com.br")));
        } else if ( view == img_tecnico) {
            drawerLayout.closeDrawers();
            UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivityPrincipal.this));
            //if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("A"))
                startActivity(new Intent(ActivityPrincipal.this, ActivityTecnico.class));
            //else
            //    Toast.makeText(ActivityPrincipal.this, "Usuario não tem permissão para acessar este módulo do aplicátivo!", Toast.LENGTH_LONG).show();
        } else if ( view == img_sobre) {
            drawerLayout.closeDrawers();
            startActivity(new Intent(ActivityPrincipal.this, ActivitySobre.class));
        } else if( view == img_avalia_1) {
                updateAvaliacaoAPP(1);
        } else if( view == img_avalia_2) {
                updateAvaliacaoAPP(2);
        } else if( view == img_avalia_3) {
                updateAvaliacaoAPP(3);
        } else if( view == img_avalia_4) {
                updateAvaliacaoAPP(4);
        } else if( view == img_avalia_5) {
                updateAvaliacaoAPP(5);
        }
        */

    }

    private void escolherEmpresaPedido() {
        List<EmpresaParametro> listaEmpresas = new EmpresaParametroDAO(db).getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        if (listaEmpresas != null && listaEmpresas.size() > 1) {
            String[] nomesEmpresas = new String[listaEmpresas.size()];
            for (int i = 0; i < listaEmpresas.size(); i++) {
                nomesEmpresas[i] = listaEmpresas.get(i).getRazao_social() != null ? listaEmpresas.get(i).getRazao_social() : listaEmpresas.get(i).getRazao_social();
            }

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Selecione a Empresa")
                    .setItems(nomesEmpresas, (dialog, which) -> {
                        showActivityPedido(String.valueOf(listaEmpresas.get(which).getId_empresa()));
                    })
                    .setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            showActivityPedido("");
        }
    }

    private void showActivityPedido(String idEmpresa) {
        shimmerLayout.showShimmer(true);
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        runnable = () -> {
            new PedidoHelper().limparDados();
            ClienteHelper.setCliente(null);
            startActivity(new Intent(ActivityPrincipal.this, ActivityPedidoMain.class).putExtra("id_empresa", idEmpresa));
            shimmerLayout.stopShimmer();
            shimmerLayout.hideShimmer();
        };
        handler.postDelayed(runnable, DELAY_OPEN_ACTIVITY);
    }

    @Override
    public void onBackPressed() {
        aperta++;
        if (aperta >= 2) {
            showActionDialog("Deseja realmente sair do aplicativo?", false);
            //finish();
        }else
            Toast.makeText(this, "Toque novamente para sair!", Toast.LENGTH_SHORT).show();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                aperta = 0;
                System.gc();
            }
        }, 2000, 5000);
    }

    public  void verificaDataSincroniaCobranca() {
        String dataSincronia = "";

        try {
            dataSincronia = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(db.consultaDados("SELECT DATA_SINCRONIA_COBRANCA FROM TBL_LOGIN", "DATA_SINCRONIA_COBRANCA")));
        } catch (ParseException|NullPointerException|CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (!dataSincronia.equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
                //montar a rotina para pergutar se queratualizar
            //if ( !getSincronizando()) {
            //    setSincronizando();
                 //executaSincronia();
            //}

        } else {
            startActivity(new Intent(ActivityPrincipal.this, ActivityDataFiltroFinanceiro.class));
        }
    }

    public void getUsuarios() {
        String version = pInfo.versionName.substring(0, 5);
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<Usuario>> call;
        try {
            call = apiRotas.getListaUsuarios();
        } catch ( NullPointerException e) {
            try {
                String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + version + "/ws/";
                Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                        "http://" + url :
                        "https://" + url;
                Api.time_conn = configuracao.getTime_conn();
                Api.time_read = configuracao.getTime_read();
                Api.time_write = configuracao.getTime_write();
                Api.context = context;
                apiRotas = Api.buildRetrofit(true);
                call = apiRotas.getListaUsuarios();
            } catch (NullPointerException ex) {
                return;
            }
        }
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                usuarioList = response.body();
                //ivInternet.setVisibility(View.GONE);
                String dataSincronia = "";
                try {
                    dataSincronia = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA")));
                } catch (ParseException|NullPointerException e) {
                    e.printStackTrace();
                }
                if (!dataSincronia.equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
                    try {
                        if (configuracao.getSincronia_automatica_app().equalsIgnoreCase("S"))
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Sincronia atrasada, por favor faça a sincronia!",  R.drawable.ic_alerta_mensagem);
                    } catch ( NullPointerException e) {
                    } catch ( Exception e) {
                    }

                if (!usuarioBO.add(usuarioList, ActivityPrincipal.this)) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Houve um erro ao salvar os usuarios",  R.drawable.ic_alerta_mensagem);
                } else {
                    String aparelhoId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    try {
                        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
                        Usuario usuarioLogin = usuarioDAO.getLista("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consultaDados("SELECT * FROM TBL_LOGIN;", "LOGIN") + "';").get(0);
                        if (aparelhoId.equals(usuarioLogin.getAparelho_id()) || usuarioLogin.getUsuario_permite_n_logins().equalsIgnoreCase("S")) {
                            loginNaApi(usuarioLogin, false);
                        } else {
                            try {
                                if (!UsuarioHelper.getUsuario().getUsuario_permite_n_logins().equalsIgnoreCase("S")) {
                                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Por favor, refaça o seu login",  R.drawable.ic_alerta_mensagem);
                                    db.update("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                                    startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                                    finish();
                                }
                            } catch ( NullPointerException e) {
                                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Por favor, refaça o seu login",  R.drawable.ic_alerta_mensagem);
                                startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                                finish();
                            }
                            System.gc();
                        }
                    } catch (CursorIndexOutOfBoundsException e) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Por favor, refaça o seu login",  R.drawable.ic_alerta_mensagem);
                        startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                        finish();
                    } catch (IndexOutOfBoundsException e) {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Por favor, refaça o seu login",  R.drawable.ic_alerta_mensagem);
                        startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loginNaApi(final Usuario usuario, boolean verificaVersaoApp) {
        Rotas apiRotas = Api.buildRetrofit(false);
        String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Call<Usuario> call;
        try {
            call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario(), db.consultaDados("SELECT * FROM TBL_LOGIN", "LOGIN"), db.consultaDados("SELECT * FROM TBL_LOGIN", "SENHA"), "-");
        } catch ( NullPointerException e) {
            apiRotas = Api.buildRetrofit(true);
            call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario(), db.consultaDados("SELECT * FROM TBL_LOGIN", "LOGIN"), db.consultaDados("SELECT * FROM TBL_LOGIN", "SENHA"), "-");
        }
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario1 = response.body();
                switch (response.code()) {
                    case 200:
                        if (usuario1.getIdEmpresaMultiDevice() != null && Integer.parseInt(usuario1.getIdEmpresaMultiDevice()) > 0) {
                            System.gc();
                            UsuarioHelper.setUsuario(usuario1);
                            if ( verificaVersaoApp) {
                                String version = pInfo.versionName.substring(0, 7);
                                String versao = UsuarioHelper.getUsuario().getAparelho_versao_app();
                                try {
                                    if (!versao.isEmpty()) {
                                        if (!version.equalsIgnoreCase(UsuarioHelper.getUsuario().getAparelho_versao_app())) {
                                            try {
                                                startActivity(new Intent( ActivityPrincipal.this, UpdateAppWhalleActivity.class)
                                                        .putExtra("versaoApp", usuario1.getAparelho_versao_app())
                                                        .putExtra("linkApp", "-"));
                                            } catch (NullPointerException e) {
                                                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "A url/link para update do aplicativo é inválida!", R.drawable.ic_alerta_mensagem);
                                            } catch (ActivityNotFoundException e) {
                                                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "A url/link para update do aplicativo é inválida!!",R.drawable.ic_alerta_mensagem);
                                            } catch (Exception e) {
                                                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "A url/link para update do aplicativo é inválida!!!", R.drawable.ic_alerta_mensagem);
                                            }
                                        } else {
                                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Versão do aplicativo já atualizada", R.drawable.ic_alerta_mensagem);
                                        }
                                        break;
                                    }
                                } catch ( NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            showFeedbackDialog("O usuario em questão não tem o codigo da empresa informado em seu cadastro, é necessário a correção no cadastro deste usuário!\n    " +
                                    "Em caso de duvida, favor entrar em contato com a RCK sistemas!");
                            //showMsgAlerta("O usuario em questão não tem o codigo da empresa informado em seu cadastro, é necessário a correção no cadastro deste usuário!\n    " +
                            //        "Em caso de duvida, favor entrar em contato com a RCK sistemas!");
                        }
                        break;
                    case 500:
                        if (!UsuarioHelper.getUsuario().getLogin().equals("DC")) {
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Foi encontrada uma divergencia em seu cadastro! Por favor, refaça o seu login.", R.drawable.ic_alerta_mensagem);
                            //db.update("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                            startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                            finish();
                        }
                        System.gc();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Não foi possivel conectar com o servidor! Verifique sua conexão a rede/internet!\n(" + t.getMessage() + ")", R.drawable.ic_alerta_mensagem);
            }
        });
    }



    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
/*
    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = ActivityPrincipal.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( ActivityPrincipal.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrincipal.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                db.update("UPDATE TBL_LOGIN SET LOGADO = 'N'");
                startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                finish();
            }
        });
        alertDialog.show();
    }

    public void showMsgSimNao( String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityPrincipal.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrincipal.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button btnNao = dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button btnSim = dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                DBHelper db = new DBHelper(ActivityPrincipal.this);
                db.update("UPDATE TBL_LOGIN SET LOGADO = 'N'");
                startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                System.gc();
                finish();
            }
        });
        alertDialog.show();
    }
    */
    private void updateValiacaoAPI(int avaliacao) {
        db = new DBHelper(ActivityPrincipal.this);
        final com.example.rcksuporte05.rcksistemas.model.Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            Rotas apiRotas = Api.buildRetrofit(false);
            Call<List<Usuario>> call = apiRotas.updateAvalicaoAPP(UsuarioHelper.getUsuario().getId_usuario(), String.valueOf(avaliacao) );
            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    List<Usuario> usuarioList = response.body();
                    if (response.code() == 200) {
                        if (!usuarioBO.add(usuarioList, getApplicationContext()))
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Houve um erro atualizar a lista de usuários apos a avaliação!", R.drawable.ic_alerta_mensagem);
                        else
                            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Avaliação registrada com sucesso!", R.drawable.ic_alerta_mensagem);
                    } else {
                        DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Houve um erro atualizar a sua avaliação! Tente novamente", R.drawable.ic_baseline_error_outline_24);
                    }
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão", R.drawable.ic_baseline_error_outline_24);
                    //t.printStackTrace();
                }
            });
        } else
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", R.drawable.ic_alerta_mensagem);
    }

    protected void checkPermission() {
        List<String> permissionsNeeded = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
        }

        getConfiguracao();
        boolean usaCheckin = configuracao != null && configuracao.getTrab_com_checkin_app() != null && configuracao.getTrab_com_checkin_app().equalsIgnoreCase("S");

        if (usaCheckin) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }

        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                    ActivityPrincipal.this,
                    permissionsNeeded.toArray(new String[0]),
                    MY_PERMISSIONS_REQUEST_CODE
            );
        } else if (usaCheckin) {
            verificarGpsAtivo();
        }
    }

    private boolean verificarGpsAtivo() {
        try {
            getConfiguracao();
            if (configuracao != null && configuracao.getTrab_com_checkin_app() != null && configuracao.getTrab_com_checkin_app().equalsIgnoreCase("N")) {
                return true;
            }

            android.location.LocationManager locationManager = (android.location.LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean gpsEnabled = locationManager != null && (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER));

            if (!gpsEnabled) {
                new AlertDialog.Builder(this)
                        .setTitle("GPS Desativado")
                        .setMessage("O serviço de localização (GPS) do aparelho está desligado. É necessário ativá-lo para registrar o Check-In das visitas nos clientes.")
                        .setPositiveButton("ATIVAR GPS", (dialog, which) -> {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        })
                        .setNegativeButton("DEPOIS", null)
                        .show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
            getConfiguracao();
            if (configuracao != null && configuracao.getTrab_com_checkin_app() != null && configuracao.getTrab_com_checkin_app().equalsIgnoreCase("S")) {
                verificarGpsAtivo();
            }
        }
    }

    private void getEvolucaoVendas() {
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        Map<String, String> cabecalho = new HashMap<>();
        if ( UsuarioHelper.getUsuario().getToken() == null) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        }
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        getConfiguracao();
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<List<EvolucaoFaturamento>> call = null;
        try {
            call = apiRotas.getTotalVendaMes(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho);
            if (call != null) {
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<List<EvolucaoFaturamento>> call, Response<List<EvolucaoFaturamento>> response) {
                        if (response.code() == 200 && response.body() != null) {
                            List<EvolucaoFaturamento> lista = response.body();
                            //createbarChart(lista);
                            
                            // Salva no Firestore para cache
                            new FirestoreConexao().salvarEvolucaoVendas(configuracao.getCnpj_empresa(), UsuarioHelper.getUsuario().getId_usuario(), lista, null);
                        } else {
                            carregarEvolucaoDoCache();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<EvolucaoFaturamento>> call, Throwable t) {
                        carregarEvolucaoDoCache();
                    }
                });
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void carregarEvolucaoDoCache() {
        new FirestoreConexao().getEvolucaoVendas(configuracao.getCnpj_empresa(), UsuarioHelper.getUsuario().getId_usuario(), new FirestoreConexao.OnListaEvolucaoRetorno() {
            @Override
            public void onSucesso(List<EvolucaoFaturamento> lista) {
                if (lista != null && !lista.isEmpty()) {
                    //createbarChart(lista);
                } else {
                    txvAguarde.setVisibility(INVISIBLE);
                    //barChart.setNoDataText("Sem dados de evolução");
                    //barChart.invalidate();
                }
            }

            @Override
            public void onFalha(Exception e) {
                txvAguarde.setVisibility(INVISIBLE);
                //barChart.setNoDataText("");
                //barChart.invalidate();
            }
        });
    }
    /*
    private void createbarChart(List<EvolucaoFaturamento> lista) {
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        barChart.setNoDataText("");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);
        leftAxis.setSpaceBottom(1.5f);
        String[] labels = new String[lista.size() + 2];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size(); i++) {
            z--;
            labels[i] = setMes(lista.get(z).getMes());
            barOne.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z).getTotal_liquido())));
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch ( ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#EABF96"));
        colors.add(Color.parseColor("#31595F"));
        set1.setColors(colors);
        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(0f);
        data.getDataSetByIndex(0);
        data.setValueTextColor(R.color.branco);
        data.setValueFormatter(new ActivityPrincipal.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        float barWidth = 0.55f; // x4 DataSet
        data.setBarWidth(barWidth);
        barChart.getLegend().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityPrincipal.this, ActivityPainelGraficoFaturamento.class));
            }
        });
        leftAxis.setDrawLabels(false);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(1500, 1500);
    }

*/
/*
    private void createbarChartGroup(List<EvolucaoFaturamento> lista) {
        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        barChart.setBackgroundColor(Color.WHITE);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);
        leftAxis.setSpaceBottom(1.5f);
        String[] labels = new String[ ( lista.size()  ) ];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size() / 2; i++) {
            z--;
            labels[i] = setMesAnoAno(  lista.get(z ).getMes() , lista.get(z -1).getMes());
            barOne.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z).getTotal_liquido())));
            barTwo.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z-1).getTotal_liquido())));
            z--;
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch ( ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");
        BarDataSet set2 = null;
            set2 = new BarDataSet(barTwo, "");
        ArrayList<Integer> colors1 = new ArrayList<>();
        colors1.add(Color.parseColor("#EABF96"));
        ArrayList<Integer> colors2 = new ArrayList<>();
        colors2.add(Color.parseColor("#31595F"));
        set1.setColors(colors1);
        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        set2.setColors(colors2);
        set2.setHighlightEnabled(false);
        set2.setVisible(true);
        float groupSpace = 0.10f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.39f; // x4 DataSet
        BarData data;
        data =new BarData( set1, set2);
        data.setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(3);
        barChart.getLegend().setEnabled(false);
        leftAxis.setDrawLabels(false);
        barChart.setScaleEnabled(true);barChart.setDragEnabled(true);
        data.setValueTextSize(8f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new ActivityPrincipal.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        barChart.getLegend().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.setData(data);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(3500, 3500);
    }

*/
/*
    private void createbarChartstacked(List<EvolucaoFaturamento> lista) {
        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        barChart.setDrawingCacheBackgroundColor(Color.WHITE);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);
        leftAxis.setSpaceBottom(1.5f);
        String[] labels = new String[ ( lista.size()  ) ];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size() ; i++) {
            z--;
            labels[i] = setMes(  lista.get(z ).getMes() );
            float[] lista1 = new float[lista.get(z).getTotal_por_grupo() == null ? 0 : lista.get(z).getTotal_por_grupo().size()];
            if ( lista.get(z).getTotal_por_grupo() != null) {
                for (int x = 0; x < lista.get(z).getTotal_por_grupo().size(); x++) {
                    lista1[x] = Float.parseFloat(lista.get(z).getTotal_por_grupo().get(x).getTotal_liquido());
                }
            }
            barOne.add(new BarEntry(i, lista1));
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch ( ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");
        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        set1.setDrawIcons(false);
        int qtdeSemanas = 0;
        int idxSemanas = 0;
        for (int i = 0; i < lista.size(); i++) {
            try {
               if (qtdeSemanas < lista.get(i).getTotal_por_grupo().size()) {
                  qtdeSemanas = lista.get(i).getTotal_por_grupo().size();
                  idxSemanas = i;
               }
            } catch (NullPointerException e) {
            }
        }
        String[] stackLabes = new String[lista.get(idxSemanas).getTotal_por_grupo().size()];
        for (int i = 0; i < lista.get(idxSemanas).getTotal_por_grupo().size(); i++) {
           stackLabes[i] = lista.get(idxSemanas).getTotal_por_grupo().get(i).getMes() + "º SEM.";
        }
        set1.setStackLabels(stackLabes);
        set1.setColors(gerarCoresPastel(stackLabes.length));
        float barWidth = 0.80f; // x4 DataSet
        BarData data;
        data = new BarData(set1);
        data.setBarWidth(barWidth);
        data.setValueTextSize(10f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new ActivityPrincipal.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getLegend().setWordWrapEnabled(true);  //Faz a quebra das linhas na legenda
        barChart.setExtraBottomOffset(8f);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(3500, 3500);
    }


    private static int[] gerarCoresPastel(int quantidade) {
        int[] cores = new int[quantidade];
        Random random = new Random();
        for (int i = 0; i < quantidade; i++) {
            // Gera cores pastel
            int r = (random.nextInt(256) + 255) / 2; // Red
            int g = (random.nextInt(256) + 255) / 2; // Green
            int b = (random.nextInt(256) + 255) / 2; // Blue
            cores[i] = Color.rgb(r, g, b);
        }
        return cores;
    }
    public String setMes(String mes) {
        if (mes.substring(5).equalsIgnoreCase("1") || mes.substring(5).equalsIgnoreCase("01"))
            return "JAN " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("2") || mes.substring(5).equalsIgnoreCase("02"))
            return "FEV " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("3") || mes.substring(5).equalsIgnoreCase("03"))
            return "MAR " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("4") || mes.substring(5).equalsIgnoreCase("04"))
            return "ABR " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("5") || mes.substring(5).equalsIgnoreCase("05"))
            return "MAI " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("6") || mes.substring(5).equalsIgnoreCase("06"))
            return "JUN " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("7") || mes.substring(5).equalsIgnoreCase("07"))
            return "JUL " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("8") || mes.substring(5).equalsIgnoreCase("08"))
            return "AGO " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("9") || mes.substring(5).equalsIgnoreCase("09"))
            return "SET " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("10"))
            return "OUT " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("11"))
            return "NOV " + mes.substring(2,4) ;
        if (mes.substring(5).equalsIgnoreCase("12"))
            return "DEZ " + mes.substring(2,4) ;
        else
            return "";
    }
    public String setMesAnoAno(String mes1, String mes2) {
        if (mes1.substring(5).equalsIgnoreCase("1") || mes1.substring(5).equalsIgnoreCase("01"))
            return "JAN " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("2") || mes1.substring(5).equalsIgnoreCase("02"))
            return "FEV " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("3") || mes1.substring(5).equalsIgnoreCase("03"))
            return "MAR " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("4") || mes1.substring(5).equalsIgnoreCase("04"))
            return "ABR " + mes1.substring(2,4)  + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("5") || mes1.substring(5).equalsIgnoreCase("05"))
            return "MAI " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("6") || mes1.substring(5).equalsIgnoreCase("06"))
            return "JUN " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("7") || mes1.substring(5).equalsIgnoreCase("07"))
            return "JUL " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("8") || mes1.substring(5).equalsIgnoreCase("08"))
            return "AGO " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("9") || mes1.substring(5).equalsIgnoreCase("09"))
            return "SET " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("10"))
            return "OUT " + mes1.substring(2,4)  + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("11"))
            return "NOV " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        if (mes1.substring(5).equalsIgnoreCase("12"))
            return "DEZ " + mes1.substring(2,4) + "/" + mes2.substring(2,4) ;
        else
            return "";
    }
*/
    public String setMesAno(String mes) {
        if (mes.substring(5).equalsIgnoreCase("1") || mes.substring(5).equalsIgnoreCase("01"))
            return "JANEIRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("2") || mes.substring(5).equalsIgnoreCase("02"))
            return "FEVEREIRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("3") || mes.substring(5).equalsIgnoreCase("03"))
            return "MARÇO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("4") || mes.substring(5).equalsIgnoreCase("04"))
            return "ABRIL DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("5") || mes.substring(5).equalsIgnoreCase("05"))
            return "MAIO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("6") || mes.substring(5).equalsIgnoreCase("06"))
            return "JUNHO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("7") || mes.substring(5).equalsIgnoreCase("07"))
            return "JULHO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("8") || mes.substring(5).equalsIgnoreCase("08"))
            return "AGOSTO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("9") || mes.substring(5).equalsIgnoreCase("09"))
            return "SETEMBRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("10"))
            return "OUTUBRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("11"))
            return "NOVEMBRO DE " + mes.substring(0,4) ;
        if (mes.substring(5).equalsIgnoreCase("12"))
            return "DEZEMBRO DE " + mes.substring(0,4) ;
        else
            return "";
    }
    public class CustomValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return MascaraUtil.mascaraRealSemSimbolo(value);
        }
    }


    public void showFeedbackDialog(String mensagem) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                this,
                "Atenção", // Título dinâmico
                mensagem,
                true,
                R.drawable.ic_alerta_mensagem,
                null, // Passando a cor personalizada
                () -> {
                    db.update("UPDATE TBL_LOGIN SET LOGADO = 'N'");
                    startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                    finish();
                }
        );
    }

    public void showActionDialog(String mensagem, boolean isLogoff) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,
                isLogoff ? null : R.drawable.ic_help_new,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {

                    }
                    @Override
                    public void onNegative() {
                        if ( isLogoff) {
                           DBHelper db = new DBHelper(ActivityPrincipal.this);
                           db.update("UPDATE TBL_LOGIN SET LOGADO = 'N'");
                           startActivity(new Intent(ActivityPrincipal.this, ActivityLogin_.class));
                           System.gc();
                           finish();
                           return;
                        }
                        System.gc();
                        finish();
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }

    private void setupVisibilidadeBI() {
        // TRAVA DESATIVADA: Liberado para vendedores
        /*
        if (UsuarioHelper.getUsuario() != null && "V".equalsIgnoreCase(UsuarioHelper.getUsuario().getUsuario_tipo())) {
            btnOcultarBI.setImageResource(R.drawable.ic_senha_view_off_foreground);
            mascararDadosBI(true);
            return;
        }
        */

        boolean visivel = getSharedPreferences("DASHBOARD_PREFS", MODE_PRIVATE).getBoolean("BI_VISIVEL", true);
        if (visivel) {
            btnOcultarBI.setImageResource(R.drawable.ic_action_visibility_white);
            mascararDadosBI(false);
        } else {
            btnOcultarBI.setImageResource(R.drawable.ic_senha_view_off_foreground);
            mascararDadosBI(true);
        }
    }

    private void initDashboardBO() {
        dashboardBO = new DashboardBO(this, new DashboardBO.OnDashboardListener() {
            @Override
            public void onDashboardLoaded(DashboardData data) {
                currentDashboardData = data;
                realQtdPendentes = data.getQtdPendentes();
                realValorPendentes = data.getValorPendentes();
                atualizarInterfaceDashboard(data);
            }

            @Override
            public void onDashboardError(String mensagem) {
                // Silencioso por enquanto para não atrapalhar o uso principal
            }
        });
    }

    private void atualizarInterfaceDashboard(DashboardData data) {
        boolean estaMascarado = !getSharedPreferences("DASHBOARD_PREFS", MODE_PRIVATE).getBoolean("BI_VISIVEL", true);
        
        // TRAVA DESATIVADA: Liberado para vendedores
        /*
        if (UsuarioHelper.getUsuario() != null && "V".equalsIgnoreCase(UsuarioHelper.getUsuario().getUsuario_tipo())) {
            estaMascarado = true;
        }
        */

        // Atualiza o texto e cor do informativo de atualização
        if (data.isOffline()) {
            tvUltimaAtualizacao.setText("Offline - " + data.getDataHoraAtualizacao());
            tvUltimaAtualizacao.setTextColor(Color.parseColor("#E67E22")); // Laranja para alerta de offline
        } else {
            tvUltimaAtualizacao.setText("Atualizado: " + data.getDataHoraAtualizacao());
            tvUltimaAtualizacao.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorText));
        }
        
        mascararDadosBI(estaMascarado);
    }

    private void toggleVisibilidadeBI() {
        // TRAVA DESATIVADA: Liberado para vendedores
        /*
        if (UsuarioHelper.getUsuario() != null && "V".equalsIgnoreCase(UsuarioHelper.getUsuario().getUsuario_tipo())) {
            DialogHelper.showSnackInfo(findViewById(android.R.id.content), "Sua configuração de sistema não permite a visualização destes indicadores!", R.drawable.ic_alerta_mensagem);
            return;
        }
        */

        boolean estaMascarado = !getSharedPreferences("DASHBOARD_PREFS", MODE_PRIVATE).getBoolean("BI_VISIVEL", true);
        if (!estaMascarado) {
            btnOcultarBI.setImageResource(R.drawable.ic_senha_view_off_foreground);
            mascararDadosBI(true);
        } else {
            btnOcultarBI.setImageResource(R.drawable.ic_action_visibility_white);
            mascararDadosBI(false);
        }
        getSharedPreferences("DASHBOARD_PREFS", MODE_PRIVATE).edit().putBoolean("BI_VISIVEL", estaMascarado).apply();
    }

    private void mascararDadosBI(boolean mascarar) {
        if (currentDashboardData == null) {
            // Se ainda não carregou os dados reais, pode manter os fictícios ou zerar
            if (mascarar) {
                tvPorcentagemSemana.setText("**%");
                tvTituloMetaSemana.setText("META DA SEMANA");
                tvValoresSemana.setText("R$ ***** / *****");
                tvRankingSemana.setText("Devolução: R$ *****");
                tvRankingSemana.setTextColor(Color.RED);
                tvPorcentagemMes.setText("**%");
                tvTituloMetaMes.setText("OBJETIVO DO MÊS");
                tvValoresMes.setText("R$ ***** / *****");
                tvRankingMes.setText("Devolução: R$ *****");
                tvRankingMes.setTextColor(Color.RED);
                tvFaltaMes.setText("Faltam R$ *****");


                tvQtdeVisitas.setText("**");
                tvPedidosFaturados.setText("**");
                tvPedidosDevolvidos.setText("**");
                tvPedidosCancelados.setText("**");
                tvNovosClientes.setText("**");
                tvPositivacao.setText("Positivação: **% da carteira");
                tvTicketMedio.setText("Tkt Médio: R$ *****");
                tvPedidosATransmitir.setText("PEDIDOS PENDENTES (**): R$ *****");
            }
            return;
        }

        if (mascarar) {
            tvPorcentagemSemana.setText("**%");
            tvTituloMetaSemana.setText("VENDA/META DA SEMANA");
            tvValoresSemana.setText("R$ ***** / *****");
            tvRankingSemana.setText("Devolução: R$ *****");
            tvRankingSemana.setTextColor(Color.RED);
            
            tvPorcentagemMes.setText("**%");
            tvTituloMetaMes.setText("VENDA/META DO MÊS");
            tvValoresMes.setText("R$ ***** / *****");
            tvRankingMes.setText("Devolução: R$ *****");
            tvRankingMes.setTextColor(Color.RED);
            
            tvFaltaMes.setText("Faltam R$ *****");

            tvQtdeVisitas.setText("**");
            tvPedidosFaturados.setText("**");
            tvPedidosDevolvidos.setText("**");
            tvPedidosCancelados.setText("**");
            tvNovosClientes.setText("**");
            tvPositivacao.setText("Positivação: */* **% da carteira");
            tvTicketMedio.setText("Tkt Médio: R$ *****");
            tvPedidosATransmitir.setText("PEDIDOS PENDENTES (**): R$ *****");
        } else {
            // DADOS DA SEMANA
            tvPorcentagemSemana.setText(currentDashboardData.getPorcentagemSemana() + "%");
            tvTituloMetaSemana.setText("VENDA/META DA SEMANA (" + currentDashboardData.getSemanaAtual() + "ª)");
            tvValoresSemana.setText(MascaraUtil.mascaraRealSimboloD(currentDashboardData.getRealizadoSemana()) + " / " + MascaraUtil.mascaraRealSimboloD(currentDashboardData.getMetaSemana()));
            
            tvRankingSemana.setText("Devolução: " + MascaraUtil.mascaraRealSimboloD(currentDashboardData.getValorDevolucaoSemana()));
            tvRankingSemana.setTextColor(Color.RED);
            
            progressSemana.setProgress(currentDashboardData.getPorcentagemSemana());

            // DADOS DO MÊS
            tvPorcentagemMes.setText(currentDashboardData.getPorcentagemMes() + "%");
            tvTituloMetaMes.setText("VENDA/META DO MÊS (" + currentDashboardData.getNomeMesExtenso() + ")");
            tvValoresMes.setText(MascaraUtil.mascaraRealSimboloD(currentDashboardData.getRealizadoMes()) + " / " + MascaraUtil.mascaraRealSimboloD(currentDashboardData.getMetaMes()));
            
            tvRankingMes.setText("Devolução: " + MascaraUtil.mascaraRealSimboloD(currentDashboardData.getValorDevolucaoMes()));
            tvRankingMes.setTextColor(Color.RED);

            progressMes.setProgress(currentDashboardData.getPorcentagemMes());
            
            double faltaMes = currentDashboardData.getMetaMes() - currentDashboardData.getRealizadoMes();
            if (faltaMes > 0) tvFaltaMes.setText("Faltam " + MascaraUtil.mascaraRealSimboloD(faltaMes));
            else tvFaltaMes.setText("Meta atingida! Parabéns!");

            // OPERACIONAL
            tvQtdeVisitas.setText(String.valueOf(currentDashboardData.getQtdVisitas()));
            tvPedidosFaturados.setText(String.valueOf(currentDashboardData.getQtdFaturados()));
            tvPedidosDevolvidos.setText(String.valueOf(currentDashboardData.getQtdDevolucoesMes()));
            tvPedidosCancelados.setText(String.valueOf(currentDashboardData.getQtdCancelados()));
            tvNovosClientes.setText(String.valueOf(currentDashboardData.getQtdNovosClientes()));
            tvPositivacao.setText("Positivação: " + currentDashboardData.getQtdPositivados() + "/" + currentDashboardData.getQtdClientesAtivos() + " " + currentDashboardData.getPorcentagemPositivacao() + "% da carteira");
            tvTicketMedio.setText("Tkt Médio: " + MascaraUtil.mascaraRealSimboloD(currentDashboardData.getTicketMedio()));
            tvPedidosATransmitir.setText("PEDIDOS PENDENTES (" + currentDashboardData.getQtdPendentes() + "): " + MascaraUtil.mascaraRealSimboloD(currentDashboardData.getValorPendentes()));
        }
    }

    private void carregarDadosDashboard() {
        getConfiguracao(); // Atualiza CNPJ e parâmetros antes de iniciar a busca

        // TRAVA TEMPORÁRIA: Não executa a pesquisa/carregamento se for vendedor e estiver travado
        /*
        if (UsuarioHelper.getUsuario() != null && "V".equalsIgnoreCase(UsuarioHelper.getUsuario().getUsuario_tipo())) {
            tvUltimaAtualizacao.setText("Acesso restrito");
            return;
        }
        */

        if (dashboardBO == null) initDashboardBO();
        
        // Busca o idVendedorFirestore
        String idVendedorFirestore = "";
        List<Usuario> users = new UsuarioDAO(db).getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario());
        if (users != null && !users.isEmpty() && users.get(0).getId_quando_vendedor() != null) {
            idVendedorFirestore = users.get(0).getId_quando_vendedor().trim();
        } else {
            idVendedorFirestore = UsuarioHelper.getUsuario().getId_usuario();
        }
        
        dashboardBO.carregarDados(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), idVendedorFirestore, configuracao);
    }
    /*
    private void updateAvaliacaoAPP(int avalicao) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        try {
            setAvalicaoApp(avalicao, true);
            if ( usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), avalicao) >= 1) {
                if ( avalicao == 5 && img_avalia_5.getTag().equals(R.drawable.ic_avaliacao_positiva))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), avalicao);
                else if (avalicao == 5 && img_avalia_5.getTag().equals(R.drawable.ic_avaliacao_negativa))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), 4);
                else if (avalicao == 4 && img_avalia_4.getTag().equals(R.drawable.ic_avaliacao_positiva))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), avalicao);
                else if (avalicao == 4 && img_avalia_4.getTag().equals(R.drawable.ic_avaliacao_negativa))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), 3);
                else if (avalicao == 3 && img_avalia_3.getTag().equals(R.drawable.ic_avaliacao_positiva))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), avalicao);
                else if (avalicao == 3 && img_avalia_3.getTag().equals(R.drawable.ic_avaliacao_negativa))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), 2);
                else if (avalicao == 2 && img_avalia_2.getTag().equals(R.drawable.ic_avaliacao_positiva))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), avalicao);
                else if (avalicao == 2 && img_avalia_2.getTag().equals(R.drawable.ic_avaliacao_negativa))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), 1);

                else if (avalicao == 1 && img_avalia_1.getTag().equals(R.drawable.ic_avaliacao_positiva))
                    usuarioDAO.updateAvalicao(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), avalicao);
                //Toast.makeText(getApplicationContext(), "Avaliação registrada com sucesso!\n Grato", Toast.LENGTH_SHORT).show();
            }
        } catch ( NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Falha ao registrar a avaliação!\n Tente novamente", Toast.LENGTH_SHORT).show();
        }

    }

     */
}

