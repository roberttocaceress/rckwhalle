package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.CadastroAnexoBO;
import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.BairroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroMotivoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComClientesDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialItensDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CobrancaStatusDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CondicoesPagamentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ContasBancoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MoedaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MunicipioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.OperacaoEstoqueDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PaisesDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoLinhaColecaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoSkuDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProspectDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ReceberDAO;
import com.example.rcksuporte05.rcksistemas.DAO.RegiaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.SegmentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.TabelaPrecoCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.TabelaPrecoItemDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VendedorBonusResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VisitaProspectDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.CadastroCondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComClientes;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.ClienteResumo;
import com.example.rcksuporte05.rcksistemas.model.CobrancaStatus;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Deposito;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.Pais;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoGrupo;
import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;
import com.example.rcksuporte05.rcksistemas.model.ProdutoParametroSku;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSku;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.PromocaoCliente;
import com.example.rcksuporte05.rcksistemas.model.PromocaoProduto;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.ReceberCab;
import com.example.rcksuporte05.rcksistemas.model.Segmento;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoCab;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ActivitySincronia extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, LocationListener{
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 50.00f;
    private static final long MIN_TIME_BW_UPDATES = 10000;
    public native void beginTransactionNative();
    public native void commitTransactionNative();
    public native void rollbackTransactionNative();
    @BindView(R.id.id_opcao_pedidos)
    Switch id_opcao_pedidos;

    @BindView(R.id.id_opcao_cliente)
    Switch id_opcao_cliente;

    @BindView(R.id.id_opcao_produto)
    Switch id_opcao_produto;

    @BindView(R.id.id_opcao_pedidos_pendentes)
    Switch id_opcao_pedidos_pendentes;

    @BindView(R.id.id_opcao_prospect)
    Switch id_opcao_prospect;

    @BindView(R.id.id_opcao_prospect_enviados)
    Switch id_opcao_prospect_enviados;

    @BindView(R.id.id_opcao_visitas_pendentes)
    Switch id_opcao_visitas_pendentes;

    @BindView(R.id.id_opcao_imagem_produto)
    Switch id_opcao_imagem_produto;

    @BindView(R.id.id_opcao_cobrancas)
    Switch id_opcao_cobrancas;

    @BindView(R.id.id_opcao_fazer_checkin)
    Switch id_opcao_fazer_checkin;

    @BindView(R.id.id_opcao_restaurar_pendentes)
    Switch id_opcao_restaurar_pendentes;

    @BindView(R.id.id_opcao_restaurar_clientes)
    Switch id_opcao_restaurar_clientes;

    //@BindView(R.id.progress_bar)
    //ProgressBar progress_bar_2;

    //@BindView(R.id.progress_title)
    //TextView progress_title;

    @BindView(R.id.lytEncerrarRepetir)
    LinearLayout lytEncerrarRepetir;

    @BindView(R.id.linearLayout6)
    LinearLayout linearLayout6;

    @BindView(R.id.txvMensagem)
    TextView txvMensagem;

    @BindView(R.id.btnEncerrar)
    Button btnEncerrar;

    @BindView(R.id.btnRepetir)
    Button btnRepetir;

    @BindView(R.id.btnCancelar)
    Button btnCancelar;

    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    @BindView(R.id.btnNovoApp)
    Button btnNovoApp;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DBHelper db = new DBHelper(this);
    private CategoriaDAO categoriaDAO;
    private PromocaoDAO promocaoDAO;
    private PromocaoClienteDAO promocaoClienteDAO;
    private PromocaoProdutoDAO promocaoProdutoDAO;
    private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;
    private CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO;
    private CadastroAnexoDAO cadastroAnexoDAO;
    private CampanhaComClientesDAO campanhaComClientesDAO;
    private CampanhaComercialCabDAO campanhaComercialCabDAO;
    private CampanhaComercialItensDAO campanhaComercialItensDAO;
    private ProdutoLinhaColecaoDAO produtoLinhaColecaoDAO;

    private ClienteDAO clienteDAO = new ClienteDAO(db);
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    private UsuarioDAO usuarioDAO = new UsuarioDAO(db);
    private TabelaPrecoItemDAO tabelaPrecoItemDAO = new TabelaPrecoItemDAO(db);
    private TabelaPrecoCabDAO tabelaPrecoCabDAO = new TabelaPrecoCabDAO(db);
    private CondicoesPagamentoDAO condicoesPagamentoDAO = new CondicoesPagamentoDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private PaisesDAO paisesDAO = new PaisesDAO(db);
    private MoedaDAO moedaDAO = new MoedaDAO(db);
    private SegmentoDAO segmentoDAO = new SegmentoDAO(db);
    private OperacaoEstoqueDAO operacaoEstoqueDAO = new OperacaoEstoqueDAO(db);
    private ProspectDAO prospectDAO = new ProspectDAO(db);
    private VisitaProspectDAO visitaProspectDAO = new VisitaProspectDAO(db);
    private CadastroMotivoDAO cadastroMotivoDAO = new CadastroMotivoDAO(db);
    private ProdutoSkuDAO produtoSkuDAO = new ProdutoSkuDAO(db);
    private ContasBancoDAO contasBancoDAO = new ContasBancoDAO(db);
    private FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
    private RegiaoDAO regiaoDAO = new RegiaoDAO(db);
    private BairroDAO bairroDAO = new BairroDAO(db);
    private MunicipioDAO municipioDAO = new MunicipioDAO(db);
    private VendedorBonusResumoDAO vendedorBonusResumoDAO = new VendedorBonusResumoDAO(db);
    private ReceberDAO webReceberCabDAO = new ReceberDAO(db);
    private CobrancaStatusDAO cobrancaStatusDAO = new CobrancaStatusDAO(db);
    Rotas apiRotas;
    ProgressDialog progress;
    
    // NOVOS CAMPOS PARA DIÁLOGO CUSTOMIZADO
    private androidx.appcompat.app.AlertDialog progressDialogCustom;
    private TextView tvProgressMessage;

    boolean isSincronizando = false;
    boolean isAutomatico;
    private static final String TAG = "MyActivityRCK APP TAG LOG ";
    private static Activity activity;


    PackageInfo pInfo = null;
    Context context;

    private Usuario usuario ;
   private Configuracao configuracaoApp;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_sincronia);
          ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
              Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
              //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
              View toolbar = findViewById(R.id.toolbar);
              toolbar.setPadding(0, insets.top, 0, 0);

              return WindowInsetsCompat.CONSUMED;
          });
          ButterKnife.bind(this);
          setSupportActionBar(toolbar);
          Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
          getSupportActionBar().setDisplayShowHomeEnabled(true);
          toolbar.setNavigationOnClickListener(v -> onBackPressed());
          Drawable upArrow = ContextCompat.getDrawable(this, androidx.appcompat.R.drawable.abc_ic_ab_back_material);
          if (upArrow != null) {
              upArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);
              getSupportActionBar().setHomeAsUpIndicator(upArrow);
          }
          context = this;
          activity = this;
          showHideLAyoutEncerrar(true, true, false, "");
          btnRepetir.setText("CONFIRMAR SINCRONIA");
          txvMensagem.setText("");
          id_opcao_pedidos_pendentes.setOnCheckedChangeListener(this);
           id_opcao_pedidos_pendentes.setEnabled(false);
          id_opcao_restaurar_pendentes.setChecked(false);
          id_opcao_restaurar_pendentes.setOnCheckedChangeListener(this);
          id_opcao_restaurar_clientes.setChecked(false);
          id_opcao_restaurar_clientes.setOnCheckedChangeListener(this);
          id_opcao_imagem_produto.setOnCheckedChangeListener(this);
          id_opcao_cliente.setOnCheckedChangeListener(this);
          id_opcao_produto.setOnCheckedChangeListener(this);
          id_opcao_cobrancas.setOnCheckedChangeListener(this);
          id_opcao_pedidos.setOnCheckedChangeListener(this);

          ///id_opcao_pedidos_pendentes.setEnabled(false);


          EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(new DBHelper(this));
          EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
          try {
              if (empresaParametro != null && empresaParametro.getUtiliza_cobranca_offline() != null && 
                  (empresaParametro.getUtiliza_cobranca_offline().equalsIgnoreCase("S") || empresaParametro.getUtiliza_cobranca_offline().equalsIgnoreCase("V")))
                  id_opcao_cobrancas.setChecked(true);
          } catch (Exception e) {
              e.printStackTrace();
          }
          ConfiguracaoDAO configuracaoDAOApp = new ConfiguracaoDAO(new DBHelper(this));
          configuracaoApp = configuracaoDAOApp.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
          try {
              if (configuracaoApp != null && configuracaoApp.getTrabalha_com_cobranca() != null && 
                  configuracaoApp.getTrabalha_com_cobranca().equalsIgnoreCase("N")) {
                  id_opcao_cobrancas.setEnabled(false);
              }
          } catch (Exception e) {
          }
          id_opcao_fazer_checkin.setVisibility(View.GONE);
          id_opcao_prospect.setVisibility(View.GONE);
          id_opcao_prospect_enviados.setVisibility(View.GONE);
          try {
              if (empresaParametro != null && empresaParametro.getConfirma_sincronia_checkin_app() != null && 
                  empresaParametro.getConfirma_sincronia_checkin_app().equalsIgnoreCase("S")) {
                  id_opcao_fazer_checkin.setChecked(true);
                  id_opcao_fazer_checkin.setVisibility(View.VISIBLE);
              } else
                  id_opcao_fazer_checkin.setChecked(false);
          } catch (Exception e) {
              id_opcao_fazer_checkin.setChecked(false);
              e.printStackTrace();
          }

          int tExtraFonte = 0;
          try {
              tExtraFonte = db.pegaTamanhoExtraFonte();
          } catch (NullPointerException ignored) {
          }
          if ( tExtraFonte > 0) {
              id_opcao_cliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_produto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_cobrancas.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);

              id_opcao_fazer_checkin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_pedidos_pendentes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_pedidos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_imagem_produto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_restaurar_pendentes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_visitas_pendentes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
              id_opcao_restaurar_clientes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + tExtraFonte);
          }
          btnConfirmar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (isSincronizando)
                      return;
                  if (id_opcao_restaurar_pendentes.isChecked()) {
                      isSincronizando = !isSincronizando;
                      progress = new ProgressDialog(context);
                      progress.setTitle("RCK SISTEMAS ESPECIFICOS!");
                      progress.setIcon(R.mipmap.ic_rck_logo_dialog_progressbar_2025);

                      progress.setMessage("Analisando pedidos que foram efetuados backup para possivel restauração!");
                      progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                      progress.setIndeterminate(true);
                      progress.setCancelable(false);
                      progress.setProgressNumberFormat(null);
                      progress.setProgressPercentFormat(null);
                      try {
                          progress.show();
                      } catch (IllegalArgumentException e) {
                      } catch (Exception e) {
                      }
                      if (configuracaoApp.getCnpj_empresa() == null || configuracaoApp.getCnpj_empresa().isEmpty()) {
                          DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Parametro não definido para restauração de pedidos pendentes", R.drawable.ic_alerta_mensagem);
                          return;
                      }
                      showProgressDialogCustom("Analisando pedidos que foram efetuados backup para possivel restauração!");
                      getPedidoFirebase(configuracaoApp.getCnpj_empresa());
                      return;
                  }
                  if (id_opcao_restaurar_clientes.isChecked()) {
                      isSincronizando = !isSincronizando;
                      if (configuracaoApp.getCnpj_empresa() == null || configuracaoApp.getCnpj_empresa().isEmpty()) {
                          DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Parametro não definido para restauração de cadastro de clientes", R.drawable.ic_alerta_mensagem);
                          return;
                      }
                      showProgressDialogCustom("Analisando novos cadastros que foram efetuados backup para possivel restauração!");
                      getClienteFirebase(configuracaoApp.getCnpj_empresa());
                      return;
                  }
                  isSincronizando = !isSincronizando;
                  String versaoApp = "-";
                  try {
                      pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                      versaoApp = pInfo.versionName.substring(0, 7);
                  } catch (PackageManager.NameNotFoundException e) {
                      e.printStackTrace();
                  }
                  //showHideProgressBar(true);
                  String mensagem = "";
                  if (id_opcao_fazer_checkin.isChecked()) {
                      mensagem = "Efetuando checkin! Aguarde...";
                  } else {
                      mensagem = "Sincronizando a base de dados! Aguarde...";
                  }

                  showProgressDialogCustom(mensagem);

                  /*
                  if (id_opcao_fazer_checkin.isChecked()) {
                      Location location = getLocationWithCheckNetworkAndGPS();
                      if (location != null) {
                          try {
                              sincronizaApi(sincronia, versaoApp, String.valueOf(location.getLatitude()).trim(), String.valueOf(location.getLongitude()));
                              return;
                          } catch (NullPointerException e) {
                              e.printStackTrace();
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                          sincronizaApi(sincronia, versaoApp, "-", "-");
                          return;
                      }
                      sincronizaApi(sincronia, versaoApp, "-", "-");
                  } else
                   */
                  try {
                      Bundle extras = getIntent().getExtras();
                      assert extras != null;
                      int id = extras.getInt("idcliente");
                      if (extras.getInt("idcliente") > 0 && extras.getString("pedidosenviados").equalsIgnoreCase("S")) {
                          getListaWebPedidoCliente(String.valueOf(id));
                          return;
                      }
                  }catch (NullPointerException ex) {
                      ex.printStackTrace();
                  }
                  Sincronia sincronia = new Sincronia(id_opcao_cliente.isChecked(),
                          id_opcao_produto.isChecked(),
                          id_opcao_pedidos.isChecked(),
                          id_opcao_pedidos_pendentes.isChecked(),
                          id_opcao_prospect.isChecked(),
                          id_opcao_prospect_enviados.isChecked(),
                          id_opcao_visitas_pendentes.isChecked(),
                          id_opcao_imagem_produto.isChecked(),
                          id_opcao_cobrancas.isChecked());
                  sincronizaApi(sincronia, versaoApp, "-", "-");
              }
          });

          btnCancelar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (isSincronizando)
                      return;
                  //startActivity(new Intent(ActivitySincronia.this, ActivityPrincipal.class).putExtra("alterado", 0));
                  finish();
              }
          });
          btnEncerrar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  finish();
              }
          });
          btnRepetir.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  isSincronizando = false;
                  showHideLAyoutEncerrar(false, false, false, "");
                  btnConfirmar.callOnClick();
              }
          });
          btnNovoApp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (UsuarioHelper.getUsuario().getAparelho_versao_app() == null || UsuarioHelper.getUsuario().getAparelho_versao_app().isEmpty())
                      return;
                  //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UsuarioHelper.getUsuario().getAparelho_link_app())));
                  startActivity(new Intent(ActivitySincronia.this, UpdateAppWhalleActivity.class)
                          .putExtra("versaoApp", UsuarioHelper.getUsuario().getAparelho_versao_app())
                          .putExtra("linkApp", "-"));
              }
          });

          try {
              Bundle extras = getIntent().getExtras();
              if (extras.getBoolean("autoSincroniza")) {
                  try {
                      if (extras.getInt("idcliente") > 0 && extras.getString("pedidosenviados").equalsIgnoreCase("S")) {
                          id_opcao_cliente.setChecked(false);
                          id_opcao_produto.setChecked(false);
                          id_opcao_cobrancas.setChecked(false);

                          id_opcao_imagem_produto.setChecked(false);
                          id_opcao_restaurar_clientes.setChecked(false);
                          id_opcao_restaurar_pendentes.setChecked(false);
                          id_opcao_pedidos_pendentes.setChecked(false);
                          id_opcao_pedidos.setChecked(true);
                          btnConfirmar.callOnClick();
                          return;

                      }
                  }catch (NullPointerException ex) {
                      ex.printStackTrace();
                  }
                  Configuracao configuracao = new Configuracao();
                  ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(new DBHelper(this));
                  try {
                      configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                      if (configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("S") || configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("V")) {
                          id_opcao_cobrancas.setChecked(true);
                      }
                  } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
                  } catch (Exception e) {
                  }
                  btnConfirmar.callOnClick();
              }
          } catch (NullPointerException e) {
              e.printStackTrace();
          } catch (Exception e) {
              e.printStackTrace();
          }

      }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
         /*
          try {
              progress.dismiss();
          }catch (Exception e) {
              e.printStackTrace();
          }

          */
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        try {
            if (progress != null) progress.dismiss();
            dismissProgressDialogCustom();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if ( isSincronizando)
            return;;
        //startActivity(new Intent(ActivitySincronia.this, ActivityPrincipal.class).putExtra("alterado", 0));
        //finish();
        super.onBackPressed();
    }

    private void showHideLAyoutEncerrar(boolean visivel, boolean sucesso, boolean novoApp, String mensagem) {
        lytEncerrarRepetir.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
        if (visivel == true) {

            if (novoApp) {
                btnNovoApp.setVisibility(View.VISIBLE);
            } else {
                btnNovoApp.setVisibility(View.INVISIBLE);
            }
            btnConfirmar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryTransparente80));
            btnCancelar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryTransparente80));
            if (sucesso) {
                txvMensagem.setTextColor(Color.BLACK);
                txvMensagem.setText("SINCRONIA EFETUADA COM SUCESSO");
                btnRepetir.setText("REPETIR SINCRONIA");
            } else {
                txvMensagem.setTextColor(Color.RED);
                txvMensagem.setText(mensagem);
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.id_opcao_cliente:
            case R.id.id_opcao_produto:
            case R.id.id_opcao_cobrancas:
            case R.id.id_opcao_pedidos:
                if (b) {
                    id_opcao_restaurar_clientes.setChecked(false);
                    id_opcao_restaurar_pendentes.setChecked(false);
                    id_opcao_imagem_produto.setChecked(false);
                }
                break;
            case R.id.id_opcao_pedidos_pendentes:
                if (b) {
                    DBHelper db = new DBHelper(ActivitySincronia.this);
                    WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
                    try {
                        List<WebPedido> listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
                        if (listaPedido.size() == 1) {
                            showActionDialog(listaPedido, "Deseja enviar " + listaPedido.size() + " pedido pendente para faturamento?");
                        } else if (listaPedido.size() > 1) {
                            showActionDialog(listaPedido, "Deseja enviar " + listaPedido.size() + " pedidos pendentes para faturamento?");
                        } else {
                            showFeedbackDialog("Nenhum pedido pendente encontrado", false, null);
                            id_opcao_pedidos_pendentes.setChecked(false);
                        }
                    } catch (NumberFormatException | CursorIndexOutOfBoundsException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.id_opcao_restaurar_pendentes:
                if (b) {
                    id_opcao_cliente.setChecked(false);
                    id_opcao_produto.setChecked(false);
                    id_opcao_cobrancas.setChecked(false);
                    id_opcao_pedidos_pendentes.setChecked(false);
                    id_opcao_pedidos.setChecked(false);
                    id_opcao_imagem_produto.setChecked(false);
                    id_opcao_prospect.setChecked(false);
                    id_opcao_prospect_enviados.setChecked(false);
                    id_opcao_restaurar_clientes.setChecked(false);
                    showHideLAyoutEncerrar(true, true, false, "");
                    btnRepetir.setText("CONFIRMAR SINCRONIA");
                    showFeedbackDialog("Atenção! A restauração de pedidos pendentes, sera executada apenas para os  pedidos que foram feitos a cópia de segurança," +
                            "após a restauração é aconselhavel a verificação do pedido, quanto aos itens e cliente informado no aplicativo! Em caso de duvidas entre em contato com o suporte técnico!", false, R.drawable.ic_alerta_mensagem_color_primary);
                }
                break;
            case R.id.id_opcao_restaurar_clientes:
                if (b) {
                    id_opcao_cliente.setChecked(false);
                    id_opcao_produto.setChecked(false);
                    id_opcao_cobrancas.setChecked(false);
                    id_opcao_pedidos_pendentes.setChecked(false);
                    id_opcao_pedidos.setChecked(false);
                    id_opcao_imagem_produto.setChecked(false);
                    id_opcao_prospect.setChecked(false);
                    id_opcao_prospect_enviados.setChecked(false);
                    id_opcao_restaurar_pendentes.setChecked(false);
                    showHideLAyoutEncerrar(true, true, false, "");
                    btnRepetir.setText("CONFIRMAR SINCRONIA");
                    showFeedbackDialog("Atenção! A restauração de cadastro de novos clientes, sera executada apenas para os clientes cadastrados no aplicativo e que foram feitos a cópia de segurança," +
                            "após a restauração é aconselhavel a verificação do cadastro e dos dados informados! Em caso de duvidas entre em contato com o suporte técnico!", false, R.drawable.ic_alerta_mensagem_color_primary);
                }
                break;
            case R.id.id_opcao_imagem_produto:
                if ( b) {
                    id_opcao_cliente.setChecked(false);
                    id_opcao_produto.setChecked(false);
                    id_opcao_cobrancas.setChecked(false);
                    id_opcao_pedidos_pendentes.setChecked(false);
                    id_opcao_pedidos.setChecked(false);
                    id_opcao_restaurar_pendentes.setChecked(false);
                    id_opcao_prospect.setChecked(false);
                    id_opcao_prospect_enviados.setChecked(false);
                    id_opcao_restaurar_pendentes.setChecked(false);
                    id_opcao_restaurar_clientes.setChecked(false);
                    showHideLAyoutEncerrar(true, true, false, "");
                    btnRepetir.setText("CONFIRMAR SINCRONIA");
                }
                break;
        }
    }

    private void showProgressDialogCustom(String mensagem) {
        try {
            if (progressDialogCustom != null && progressDialogCustom.isShowing()) {
                updateProgressMessage(mensagem);
                return;
            }

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_dialog_sincronia_progress, null);
            tvProgressMessage = view.findViewById(R.id.tvProgressMessage);
            tvProgressMessage.setText(mensagem);

            progressDialogCustom = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            
            progressDialogCustom.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProgressMessage(String mensagem) {
        runOnUiThread(() -> {
            if (tvProgressMessage != null && progressDialogCustom != null && progressDialogCustom.isShowing()) {
                tvProgressMessage.setText(mensagem);
            }
        });
    }

    private void dismissProgressDialogCustom() {
        runOnUiThread(() -> {
            try {
                if (progressDialogCustom != null && progressDialogCustom.isShowing()) {
                    progressDialogCustom.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void sincronizaBanco(final Sincronia sincronia, final String versaoApp) {
        final Thread a = new Thread(() -> {
            updateProgressMessage("Analisando arquivos recebidos da base de dados! Aguarde...");
            if (sincronia.isCliente()) {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nCadastro de clientes em analise..." );
                addClientes(sincronia);
            }
            //System.gc();
            if (sincronia.isProduto()) {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nCadastro de produtos em analise..." );
                addProdutos(sincronia);
            }
            //System.gc();
            if (sincronia.isPedidosPendentes())
                updatePedidosEnviadoss(sincronia.getListaWebPedidosPendentes());
            //System.gc();
            if (sincronia.isPedidosFinalizados()) {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nCadastro de pedidos enviados...");
                addPedidosFinalizados(sincronia);
            }
            //System.gc();
            // ... (prospects)
            if (sincronia.isProdutoFoto()) {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nCadastro de fotos dos produtos em analise...");
                addProdutoFotos(sincronia);
            }else {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nAtualização do cadastro de fotos dos produtos em analise...");
                updateProdutoFotos(sincronia);
            }
            //System.gc();
            if (sincronia.isCobranca()) {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nCobranças em analise...");
                addCobrancas(sincronia);
                System.gc();
            }
            updateProgressMessage("Analisando arquivos recebidos da base de dados!\nCadastro(s) de configurações em analise...");
            addUsuarios(sincronia);
            updateParametros(sincronia);
            if ( UsuarioHelper.getUsuario() == null ) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivitySincronia.this));
            }
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( ActivitySincronia.this));
            }
            updateConfiguracao(sincronia, UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());

            if (!(!sincronia.isCliente() && !sincronia.isProduto() && !sincronia.isPedidosPendentes() && !sincronia.isPedidosFinalizados()
                    && !sincronia.isProspectPendentes() && !sincronia.isPedidosFinalizados() && !sincronia.isVisitasPendentes() && sincronia.isProdutoFoto())) {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nTabelas auxiliares em analise");
                // ... (restante do processamento das tabelas auxiliares)
                if (sincronia.getListaOperacao().size() > 0) {
                    db.deleteDados("TBL_OPERACAO_ESTOQUE", "1", null);
                    for (Operacao operacao : sincronia.getListaOperacao())
                        operacaoEstoqueDAO.add(operacao);
                } else
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Lista de operação de estoque vazia!!", R.drawable.ic_alerta_mensagem);
                // System.gc();
                db.deleteDados("TBL_TABELA_PRECO_CAB", "1", null);
                tabelaPrecoCabDAO.addListaTabelaPrecoCab(sincronia.getListaTabelaPrecoCab(), db);
                // System.gc();

                // QUESTÃO 4: MELHORIA NA SINCRONIZAÇÃO DA TABELA DE PREÇO ITENS
                // APAGA A TABELA E CRIA NOVAMENTE PARA GARANTIR OS TIPOS DE DADOS E ÍNDICES
                try {
                    db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS TBL_TABELA_PRECO_ITENS;");
                    db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS TBL_TABELA_PRECO_ITENS (ID_ITEM INTEGER PRIMARY KEY," +
                            " ID_TABELA INTEGER NOT NULL," +
                            " PERC_DESC_INICIAL DECIMAL(12, 4)," +
                            " PERC_DESC_FINAL DECIMAL(12, 4)," +
                            " PERC_COM_INTERNO DECIMAL(12, 4)," +
                            " PERC_COM_EXTERNO DECIMAL(12, 4)," +
                            " PERC_COM_EXPORTACAO DECIMAL(12, 4)," +
                            " PONTOS_PREMIACAO DECIMAL(12, 4)," +
                            " COR_PAINEL VARCHAR(15)," +
                            " COR_FONTE VARCHAR(15)," +
                            " VERBA_PERC DECIMAL(12, 4)," +
                            " UTILIZA_VERBA CHAR(1)," +
                            " DESCONTO_VERBA_MAX DECIMAL(12, 4)," +
                            " ID_USUARIO INTEGER NOT NULL," +
                            " USUARIO VARCHAR(30)," +
                            " USUARIO_DATA TIMESTAMP," +
                            " COR_WEB VARCHAR(20), " +
                            " ID_CATEGORIA INTEGER, " +
                            " VALOR_UNITARIO DECIMAL(12, 4)," +
                            " ID_PRODUTO VARCHAR(20));");

                    db.getWritableDatabase().execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_TABELA ON TBL_TABELA_PRECO_ITENS(ID_TABELA)");
                    db.getWritableDatabase().execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_PRODUTO ON TBL_TABELA_PRECO_ITENS(ID_PRODUTO)");
                    db.getWritableDatabase().execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_ID_CATEGORIA ON TBL_TABELA_PRECO_ITENS(ID_CATEGORIA)");
                    db.getWritableDatabase().execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_PROD_TABELA ON TBL_TABELA_PRECO_ITENS(ID_PRODUTO, ID_TABELA)");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //
                Log.w("RCK ", "sincronizaAPI 22");
                tabelaPrecoItemDAO.addListaTabelaPrecoItens(sincronia.getListaTabelaPrecoItem(), db);
                // System.gc();
                if (sincronia.getListaCondicoesPagamento().size() > 0) {
                    db.deleteDados("TBL_CONDICOES_PAG_CAB", "1", null);
                    Log.w("RCK ", "sincronizaAPI 24");
                    for (CondicoesPagamento condicoesPagamento : sincronia.getListaCondicoesPagamento())
                        condicoesPagamentoDAO.add(condicoesPagamento);
                } else
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Lista de condições de pagamento/prazo vazia!!", R.drawable.ic_alerta_mensagem);
                //System.gc();
                db.deleteDados("TBL_PAISES", "1", null);
                for (Pais pais : sincronia.getListaPais())
                    paisesDAO.add(pais);
                System.gc();
                for (MotivoNaoCadastramento motivo : sincronia.getMotivos())
                    cadastroMotivoDAO.addUpdate(motivo);
                //System.gc();
                db.deleteDados("TBL_CADASTRO_CATEGORIA", "1", null);
                for (Categoria categoria : sincronia.getListaCategoria())
                    categoriaDAO.addUpdate(categoria);
                //System.gc();
                db.deleteDados("TBL_CADASTRO_FINANCEIRO_RESUMO", "1", null);
                for (CadastroFinanceiroResumo cadastroFinanceiroResumo : sincronia.getListaCadastroFinanceiroResumo())
                    cadastroFinanceiroResumoDAO.addUpdate(cadastroFinanceiroResumo);
                //System.gc();
                db.deleteDados("TBL_PROMOCAO_CAB", "1", null);
                String args[] = {};
                for (Promocao promocao : sincronia.getListaPromocao()) {
                    promocaoDAO.addUpdate(promocao);
                    try {
                        db.deleteDados("TBL_PROMOCAO_CLIENTE", "ID_PROMOCAO = ?", new String[]{String.valueOf(promocao.getIdPromocao())});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //System.gc();
                    for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente())
                        promocaoClienteDAO.addUpdate(promocaoCliente);
                    //System.gc();
                    try {
                        db.deleteDados("TBL_PROMOCAO_PRODUTO", "ID_PROMOCAO = ?", new String[]{String.valueOf(promocao.getIdPromocao())});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                        promocaoProdutoDAO.addUpdate(promocaoProduto);
                    //System.gc();
                }
                db.deleteDados("TBL_PRODUTO_SUB_GRUPO", "1", null);
                for (ProdutoSubGrupo produtoSubGrupo : sincronia.getListaProdutoSubGrupo())
                    produtoDAO.add(produtoSubGrupo);
                //System.gc();
                db.deleteDados("TBL_PRODUTO_GRUPO", "1", null);
                for (ProdutoGrupo produtoGrupo : sincronia.getListaProdutoGrupo())
                    produtoDAO.add(produtoGrupo);
                //System.gc();
                db.deleteDados("TBL_MOEDA", "1", null);
                for (Moeda moeda : sincronia.getListaMoeda())
                    moedaDAO.add(moeda);
                //System.gc();
                db.deleteDados("TBL_CADASTRO_CONDICOES_PAG", "1", null);
                try {
                    for (CadastroCondicoesPagamento cadastroCondicoesPagamento : sincronia.getListaCadastroCondicoesPagamento())
                        condicoesPagamentoDAO.add(cadastroCondicoesPagamento);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //System.gc();
                db.deleteDados("TBL_CADASTRO_SETOR", "1", null);
                for (Segmento segmento : sincronia.getListaSegmento())
                    segmentoDAO.add(segmento);
                //System.gc();
                db.deleteDados("TBL_DEPOSITO", "1", null);
                for (Deposito deposito : sincronia.getListaDeposito())
                    produtoDAO.add(deposito);
                //System.gc();
                db.deleteDados("TBL_CONTAS_BANCO", "1", null);
                for (Generico conta : sincronia.getListaContaBanco())
                    contasBancoDAO.add(conta);
                //System.gc();
                try {
                    db.deleteDados("TBL_CADASTRO_BAIRRO", "1", null);
                    for (Generico bairro : sincronia.getListaBairro())
                        bairroDAO.add(bairro);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                //System.gc();
                try {
                    db.deleteDados("TBL_CADASTRO_REGIAO", "1", null);
                    for (Generico regiao : sincronia.getListaRegiao())
                        regiaoDAO.add(regiao);
                } catch (NullPointerException e) {
                }
                //System.gc();
            }
            //System.gc();
            updateProgressMessage("Analisando arquivos recebidos da base de dados!\n Cadastro(s) de campanhas em analise...");
            db.deleteDados("TBL_CAMPANHA_COM_CLIENTES", "1", null);
            for (CampanhaComClientes campanhaComClientes : sincronia.getListaCampanhaComClientes())
                try {
                    campanhaComClientesDAO.add(campanhaComClientes);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //System.gc();
            db.deleteDados("TBL_CAMPANHA_COMERCIAL_CAB", "1", null);
            for (CampanhaComercialCab campanhaComercialCab : sincronia.getListaCampanhaComercialCab())
                try {
                    campanhaComercialCabDAO.add(campanhaComercialCab);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            //System.gc();
            db.deleteDados("TBL_CAMPANHA_COMERCIAL_ITENS", "1", null);
            for (CampanhaComercialItens campanhaComercialItens : sincronia.getListaCampanhaComercialItens())
                try {
                    campanhaComercialItensDAO.add(campanhaComercialItens);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            //System.gc();
            updateProgressMessage("Analisando arquivos recebidos da base de dados!\nTabelas auxiliares em analise...");
            db.deleteDados("TBL_PRODUTO_LINHA_COLECAO", "1", null);
            for (ProdutoLinhaColecao produtoLinhaColecao : sincronia.getListaProdutoLinhaColecao())
                try {
                    produtoLinhaColecaoDAO.add(produtoLinhaColecao);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            //System.gc();
            try {
                db.deleteDados("TBL_VENDEDOR_BONUS_RESUMO", "1", null);
                for (VendedorBonusResumo vendedorBonusResumo : sincronia.getListaVendedorBonus())
                    try {
                        vendedorBonusResumoDAO.add(vendedorBonusResumo);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.gc();
            if ( db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO_MUNICIPIO") == 0) {
                updateProgressMessage("Analisando arquivos recebidos da base de dados!\nCadastro de cidades...");
                municipioDAO.addListaMunicipios(sincronia.getListaMunicipio(), db);
            }
            //System.gc();
            // ... (versão app e finalização)
            String btnOK = "   OK   ";
            String version = versaoApp;
            String versao = UsuarioHelper.getUsuario().getAparelho_versao_app();
            String url = "";
            UsuarioHelper.getUsuario().getAparelho_link_app();
            boolean novoApp = false;
            try {
                url = UsuarioHelper.getUsuario().getAparelho_link_app();
                if (!versao.isEmpty()) {
                    if (!version.equalsIgnoreCase(UsuarioHelper.getUsuario().getAparelho_versao_app())) {
                        novoApp = true;
                        this.runOnUiThread(() -> DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Nova versão do aplicativo disponivel para download", R.drawable.ic_alerta_mensagem));
                    }else
                        url = "";
                } else {
                    url = "";
                }
            } catch (NullPointerException e) {
                url = "";
                e.printStackTrace();
            } catch (Exception e) {
                url = "";
                e.printStackTrace();
            }
            //System.gc();
            if (sincronia.isProduto())
                db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_PRODUTO = '" + db.pegaDataHoraAtual() + "';");
            if (sincronia.isCliente())
                db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_CLIENTE = '" + db.pegaDataHoraAtual() + "';");
            if (sincronia.isCobranca())
                db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_COBRANCA = '" + db.pegaDataHoraAtual() + "';");
            if (sincronia.isProdutoFoto())
                db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_IMAGEN = '" + db.pegaDataHoraAtual() + "';");
            db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA = '" + db.pegaDataHoraAtual() + "';");
            
            try {
                dismissProgressDialogCustom();
                boolean finalNovoApp = novoApp;
                this.runOnUiThread(() -> showHideLAyoutEncerrar(true, true, finalNovoApp, "SINCRONIA EFETUADA COM SUCESSO"));
            } catch (NullPointerException ignored) {
            }
            System.gc();
        });
        a.start();
    }

    private void updatePedidosEnviadoss(List<WebPedido> webPedidosEnviados) {
        ClienteDAO clienteDAO = new ClienteDAO(db);
        for (WebPedido pedido : webPedidosEnviados) {
            if (pedido.getPedido_enviado().equalsIgnoreCase("S")) {

                final CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                pedido.getCadastro().setAlterado("N");
                clienteDAO.update(pedido.getCadastro());
                try {
                    db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(pedido.getCadastro().getId_cadastro())});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (pedido.getCadastro().getListaCadastroAnexo().size() > 0)
                    for (CadastroAnexo cadastroAnexo : pedido.getCadastro().getListaCadastroAnexo()) {
                        if (cadastroAnexo.getExcluido().equals("N"))
                            cadastroAnexoDAO.addUpdate(cadastroAnexo);
                        else if (cadastroAnexo.getExcluido().equals("S"))
                            try {
                                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_ANEXO = ?", new String[]{String.valueOf(cadastroAnexo.getIdAnexo())});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                else
                    try {
                        db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(pedido.getCadastro().getId_cadastro())});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (webPedidoDAO.update(pedido) > 0) {
                    for (WebPedidoItens pedidoIten : pedido.getWebPedidoItens())
                        webPedidoItensDAO.update(pedidoIten);
                    //excluirPedidoSincronizado(false);
                }
            }
        }

    }

    private void updateParametros( Sincronia sincronia) {
        //progress_bar.setTitle("Sincronizando parametros da empresa...aguarde");
        //if ( sincronia.getListaEmpresa().size() > 0) {
            try {
                // APAGA A TABELA E CRIA NOVAMENTE PARA ATUALIZAR A ESTRUTURA (ID_CIDADE)
                db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS TBL_EMPRESA_PARAMETRO;");
                db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS TBL_EMPRESA_PARAMETRO (ID INTEGER PRIMARY KEY, RAZAO_SOCIAL VARCHAR(50)," +
                        "NOME_FANTASIA VARCHAR(50), EMPRESA_LOGO BLOB, EMPRESA_LOGO_MINI BLOB, EMPRESA_APP_COR VARCHAR(10 )," +
                        "TIPO_TABELA_PRECO VARCHAR(1), UTILIZA_TABELA_PRECO VARCHAR(1), TEMPO_AUTO_SINCRONIA VARCHAR(4)," +
                        "ESTOQUE_AUTO_SINCRONIA VARCHAR(1), UTILIZA_LIMITE_CREDITO VARCHAR(1), ENDERECO_UF VARCHAR(1)," +
                        "PROD_DESCRICAO_PRECO_UM VARCHAR(20), UTILIZA_COBRANCA_OFFLINE VARCHAR(1), " +
                        "UTILIZA_ICMS_ST VARCHAR(1), QTDE_MAIOR_SALDO_PED_APP VARCHAR(1), CONFIRMA_SINCRONIA_CHECKIN_APP VARCHAR(1), ID_CIDADE INTEGER );");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (EmpresaParametro empresaParametro : sincronia.getListaEmpresa())
                    empresaParametroDAO.add(empresaParametro);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
   // }

    private void updateConfiguracao( Sincronia sincronia, String idEmpresa) {
        //progress_bar.setTitle("Sincronizando configuração de acesso...aguarde");
        try {
            if ( sincronia.getListaEmpresa().size() > 0) {
                ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
                for (EmpresaParametro empresa : sincronia.getListaEmpresa()) {
                    if (idEmpresa.equalsIgnoreCase(String.valueOf(empresa.getId_empresa()))) {
                        String campanha_no_pedido = empresa.getCampanha_no_pedido_venda();
                        String dscto_valor_no_pedido = empresa.getDscto_valor_no_pedido_venda();
                        String limite_credito = empresa.getUtiliza_limite_credito();
                        String confirma_prazo = empresa.getUtiliza_confirmacao_prazo();
                        String pre_pedido = "N";
                        String usa_unid_estoque = empresa.getUtiliza_unidade_manut_estoque();
                        String usa_paginacao = empresa.getUtiliza_paginacao_dados();
                        String layout_venda_erp = empresa.getLayout_venda_erp();
                        String id_con_pgto_default_app = empresa.getId_con_pgto_default_app();
                        String usa_cobranca_app = empresa.getUtiliza_cobranca_offline();
                        String usa_icms_app = empresa.getUtiliza_icms_app();
                        String usa_desc_auto_app = empresa.getUtiliza_desct_auto_app();
                        String sincronia_automatica = empresa.getSincronia_automatica_app();
                        String usa_verba_pedido_app = empresa.getUtiliza_verba_pedido_app();
                        String cadastra_cliente_app = empresa.getCadastra_cliente_app();
                        String visual_hist_fin_pedido_app = empresa.getVisual_hist_fin_pedido_app();
                        String visual_prazo_pgto_pedido_app = empresa.getVisual_prazo_pgto_pedido_app();
                        String visual_estoque_produto_app = empresa.getVisual_estoque_produto_app();
                        String ordem_lista_cliente_app = empresa.getOrdem_lista_cliente_app();
                        String atual_lista_ped_exportado = empresa.getAtual_lista_ped_exportado();
                        String confirma_sincronia_dados = empresa.getConfirma_sincronia_dados_app();
                        String cnpj_empresa = empresa.getCnpj_cpf();
                        String marca_produto_ja_comprado = empresa.getMarca_produto_ja_comprado_app();
                        try {
                            if (empresa.getSincronia_automatica_app().isEmpty() || empresa.getSincronia_automatica_app() == null)
                                sincronia_automatica = "S";
                        } catch (NullPointerException e) {
                            sincronia_automatica = "S";
                        } catch (Exception e) {
                            sincronia_automatica = "S";
                        }
                        try {
                            if (empresa.getUtiliza_verba_pedido_app().isEmpty() || empresa.getUtiliza_verba_pedido_app() == null)
                                usa_verba_pedido_app = "N";
                        } catch (NullPointerException e) {
                            usa_verba_pedido_app = "N";
                        } catch (Exception e) {
                            usa_verba_pedido_app = "N";
                        }

                        try {
                            pre_pedido = empresa.getPre_pedido();
                            if (pre_pedido.isEmpty() || pre_pedido == null)
                                pre_pedido = "N";
                        } catch (NullPointerException e) {
                            pre_pedido = "N";
                            e.printStackTrace();
                        } catch (Exception e) {
                            pre_pedido = "N";
                            e.printStackTrace();
                        }
                        String pedido_enviado_on_line_app = empresa.getPedido_enviado_on_line_app() == null || empresa.getPedido_enviado_on_line_app().isEmpty() ? "N" : empresa.getPedido_enviado_on_line_app();
                        String visualiza_grafico_on_line_app = empresa.getVisualiza_grafico_on_line_app() == null || empresa.getVisualiza_grafico_on_line_app().isEmpty() ? "N" : empresa.getVisualiza_grafico_on_line_app();
                        String visualiza_ultimo_preco_venda_app = empresa.getVisualiza_ultimo_preco_venda_app() == null || empresa.getVisualiza_ultimo_preco_venda_app().isEmpty() ? "N" : empresa.getVisualiza_ultimo_preco_venda_app();
                        String calcula_dscto_automatico_muda_prazo = empresa.getCalcula_dscto_automatico_muda_prazo() == null || empresa.getCalcula_dscto_automatico_muda_prazo().isEmpty() ? "N" : empresa.getCalcula_dscto_automatico_muda_prazo();
                        configuracaoDAO.update(campanha_no_pedido, dscto_valor_no_pedido, limite_credito, pre_pedido, confirma_prazo, usa_unid_estoque,
                                "S"/*usa_paginacao*/, layout_venda_erp, id_con_pgto_default_app, usa_cobranca_app,
                                usa_icms_app, usa_desc_auto_app, sincronia_automatica, usa_verba_pedido_app,
                                cadastra_cliente_app, visual_hist_fin_pedido_app, visual_prazo_pgto_pedido_app,
                                visual_estoque_produto_app, ordem_lista_cliente_app, atual_lista_ped_exportado,
                                confirma_sincronia_dados, cnpj_empresa, marca_produto_ja_comprado,
                                pedido_enviado_on_line_app, visualiza_grafico_on_line_app, visualiza_ultimo_preco_venda_app, calcula_dscto_automatico_muda_prazo);
                    }
                }
            }
        } catch ( NullPointerException e ){
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }



    private void addUsuarios( Sincronia sincronia) {
        //progress_bar.setTitle("Sincronizando tabela de usuários...aguarde");
        try {
            db.deleteDados("TBL_WEB_USUARIO", "1", null);
            for (Usuario usuario : sincronia.getListaUsuario()) {
                usuarioDAO.add(usuario);
                try {
                    if (usuario.getId_usuario().equalsIgnoreCase(UsuarioHelper.getUsuario().getId_usuario()))
                        UsuarioHelper.setUsuario(usuario);
                } catch (NullPointerException e) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Falha ao atualizar o usuário logado no aplicativo!\n" + e.getMessage(), R.drawable.ic_baseline_error_outline_24);
                } catch (Exception e) {
                    DialogHelper.showSnackInfo(findViewById(android.R.id.content),  "Falha ao atualizar o usuário logado no aplicativo!\n" + e.getMessage(), R.drawable.ic_baseline_error_outline_24);
                }
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        //System.gc();
    }

    private void addClientes(Sincronia sincronia) {
        db.deleteDados("TBL_CADASTRO","ID_CADASTRO_SERVIDOR > ?", new String[]{"0"});
            clienteDAO.addLista(sincronia.getListaCliente(), db);
            clienteDAO.addListaClienteResumo(sincronia.getListaClienteResumo(), db);
            /*
            for (Cliente cliente : sincronia.getListaCliente()) {
                clienteDAO.add(cliente);
                clienteDAO.delete(cliente, "1");  //0 == local 1 = Servidor
                int idCadastro = (int) clienteDAO.add(cliente);
                if (idCadastro > 0 && cliente.getListaCadastroAnexo().size() > 0) {
                    for (CadastroAnexo cadastroAnexo : cliente.getListaCadastroAnexo()) {
                        cadastroAnexo.setIdCadastro(idCadastro);
                        cadastroAnexoDAO.addUpdate(cadastroAnexo);
                    }
                }
            }
            db.deleteDados("TBL_CADASTRO_RESUMO", "1", null);
            for (ClienteResumo cliente : sincronia.getListaClienteResumo()) {
                clienteDAO.add(cliente);
            }
            */

    }

    private void addProdutos( Sincronia sincronia) {
        db.deleteDados("TBL_PRODUTO", "1", null);
        produtoDAO.addListaProdutos(sincronia.getListaProduto(),  db);
        /*
        for (Produto produto : sincronia.getListaProduto())
             produtoDAO.add(produto);
         */
        db.deleteDados("TBL_PRODUTO_SKU", "1", null);
        //System.gc();
        for (ProdutoSku produto : sincronia.getListaProdutoSku())
           produtoSkuDAO.add(produto);
        db.deleteDados("TBL_PRODUTO_PARAMETRO_SKU", "1", null);
        //System.gc();
        for (ProdutoParametroSku produto : sincronia.getListaProdutoParametroSku())
            produtoSkuDAO.add(produto);
    }
    /*
    private void addPedidosPendentes( Sincronia sincronia) {
        for (WebPedido webPedido : sincronia.getListaWebPedidosPendentes()) {
            final CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
            if ( webPedidoDAO.update( webPedido) > 0) {
                //totalUpdatePedido++;
                for (WebPedidoItens pedidoIten : webPedido.getWebPedidoItens())
                    webPedidoItensDAO.update( pedidoIten);
            }
            try {
                clienteDAO.update(webPedido.getCadastro());
                try {
                    db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(webPedido.getCadastro().getId_cadastro())});
                } catch ( Exception e){
                    e.printStackTrace();
                }
                if (webPedido.getCadastro().getListaCadastroAnexo().size() > 0)
                    for (CadastroAnexo cadastroAnexo : webPedido.getCadastro().getListaCadastroAnexo())
                        if (cadastroAnexo.getExcluido().equals("N"))
                            cadastroAnexoDAO.addUpdate(cadastroAnexo);
                        else if (cadastroAnexo.getExcluido().equals("S"))
                            try {
                                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_ANEXO = ?", new String[]{String.valueOf(cadastroAnexo.getIdAnexo())});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        else
                            try {
                                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(webPedido.getCadastro().getId_cadastro())});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
            } catch ( NullPointerException|ArrayIndexOutOfBoundsException e ) {
            } catch ( Exception e) {
            }
        }
        //excluirPedidoSincronizado(false);
    }
*/
    private void addPedidosFinalizados(Sincronia sincronia) {
        try {
            excluirPedidoSincronizado();
            for (WebPedido webPedido : sincronia.getListaWebPedidosFinalizados()) {
                if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor()) > 0)
                    try {
                        db.deleteDados("TBL_WEB_PEDIDO", "ID_WEB_PEDIDO_SERVIDOR = ?", new String[]{webPedido.getId_web_pedido_servidor()});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            webPedidoDAO.addListaWebPedidos(sincronia.getListaWebPedidosFinalizados(), db);
            /*
                int idPedido = (int) webPedidoDAO.add(webPedido);
                if (idPedido > 0) {
                    try {
                        for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                            webPedidoItens.setId_pedido(String.valueOf(idPedido));
                            if (!webPedidoItensDAO.update(webPedidoItens))
                                if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItens.getId_produto().trim() + "') OR " +
                                        "(ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItens.getId_produto() + "');") <= 0) {
                                    webPedidoItensDAO.add(webPedidoItens);
                                }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    idPedido = 0;
                }
            }


             */
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void addProdutoFotos(Sincronia sincronia) {
        db.deleteDados("TBL_PRODUTO_FOTO", "1", null);
        //System.gc();
        try {
            if (!sincronia.getListaProdutoFoto1().isEmpty())
                produtoDAO.addListaProdutoFoto(sincronia.getListaProdutoFoto1(), db);
                //for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto1())
               //produtoDAO.add(produtoFoto);

            if (!sincronia.getListaProdutoFoto2().isEmpty())
                produtoDAO.addListaProdutoFoto(sincronia.getListaProdutoFoto2(), db);
                //for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto2())
                //    produtoDAO.add(produtoFoto);

            if (!sincronia.getListaProdutoFoto3().isEmpty())
                produtoDAO.addListaProdutoFoto(sincronia.getListaProdutoFoto3(), db);
                //for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto3())
                //    produtoDAO.add(produtoFoto);

            if (!sincronia.getListaProdutoFoto4().isEmpty())
                produtoDAO.addListaProdutoFoto(sincronia.getListaProdutoFoto4(), db);
                //for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto4())
                //    produtoDAO.add(produtoFoto);

            if (!sincronia.getListaProdutoFoto5().isEmpty())
                produtoDAO.addListaProdutoFoto(sincronia.getListaProdutoFoto5(), db);
                //for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto5())
                //    produtoDAO.add(produtoFoto);

            if (!sincronia.getListaProdutoFoto6().isEmpty())
                produtoDAO.addListaProdutoFoto(sincronia.getListaProdutoFoto6(), db);
                //for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto6())
                //    produtoDAO.add(produtoFoto);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProdutoFotos(Sincronia sincronia) {
        //System.gc();
        //progress_bar.setTitle("Sincronizando tabela de imagens de produto...aguarde");
        try {
            for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto())
                if ( produtoFoto.getId_usuario().equalsIgnoreCase("0"))
                    produtoDAO.updateFoto(produtoFoto);
                else
                    produtoDAO.add(produtoFoto);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCobrancas(Sincronia sincronia) {
        db.deleteDados("TBL_FINANCEIRO_RESUMO", "1", null);
        db.deleteDados("TBL_COBRANCA_STATUS", "1", null);
        for (FinanceiroResumo financeiro : sincronia.getListaCobranca())
            financeiroDAO.add(financeiro);
        for (CobrancaStatus cobrancaStatus : sincronia.getListaCobrancaStatus())
            cobrancaStatusDAO.add(cobrancaStatus);
        //System.gc();
        String id_android = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        db.deleteDados("TBL_WEB_RECEBER_CAB", "STATUS = ? AND EXCLUIDO = ? AND ID_ANDROID != ? AND ID_LOTE IS NOT NULL", new String[]{"A", "N", id_android});
        if (!sincronia.getListaRecebimentos().isEmpty()) {
            for (ReceberCab receber : sincronia.getListaRecebimentos()) {
                if (receber.getStatus().equalsIgnoreCase("A") && receber.getExcluido().equalsIgnoreCase("N")) {
                     if (receber.getId_android().equalsIgnoreCase(id_android))
                         webReceberCabDAO.update(receber);
                     else
                         webReceberCabDAO.add(receber);
                     } else if (receber.getStatus().equalsIgnoreCase("R") || receber.getExcluido().equalsIgnoreCase("S")) {
                         webReceberCabDAO.deleteRecebimento(receber, new String[]{receber.getId_web_receber(), "A", receber.getId_android()});
                         webReceberCabDAO.deleteRecebimento(receber, new String[]{receber.getId_web_receber(), "N", receber.getId_android()});
                     }
            }
        }

    }

    @SuppressLint("LongLogTag")
    public void sincronizaApi(final Sincronia sincronia, final String versaoApp, final String latitude, final String longitude) {
        //final ImageView ivInternet = (ImageView) activity.findViewById(R.id.ivInternet);
        categoriaDAO = new CategoriaDAO(db);
        promocaoDAO = new PromocaoDAO(db);
        promocaoClienteDAO = new PromocaoClienteDAO(db);
        promocaoProdutoDAO = new PromocaoProdutoDAO(db);
        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
        cadastroAnexoDAO = new CadastroAnexoDAO(db);
        campanhaComClientesDAO = new CampanhaComClientesDAO(db);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        produtoLinhaColecaoDAO = new ProdutoLinhaColecaoDAO(db);

        final Configuracao configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + pInfo.versionName.substring(0, 5) + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            //Api.time_conn = configuracao.getTime_conn();
            //Api.time_read = configuracao.getTime_read();
            //Api.time_write = configuracao.getTime_write();
            Api.context = context;
            apiRotas = Api.buildRetrofit(true);
            Log.w("RCK ", "sincronizaAPI 1");

        }
        @SuppressLint("HardwareIds") final Thread a = new Thread(() -> {
            Map<String, String> cabecalho = new HashMap<>();
            try {
                if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                    UsuarioBO usuarioBO = new UsuarioBO();
                    UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
                }
            } catch (NullPointerException e) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
            } catch (Exception e) {
            }

            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

            if (sincronia.isPedidosPendentes()) {
                final List<WebPedido> listaParaEnvio = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
                for (int i = 0; i < listaParaEnvio.size(); i++) {
                    if (listaParaEnvio.get(i).getCadastro().getId_cadastro_servidor() <= -1) {
                        listaParaEnvio.get(i).getCadastro().setId_cadastro_servidor(0);
                    }
                    listaParaEnvio.get(i).setTipo_envio("4");
                    try {
                        listaParaEnvio.get(i).setId_android(Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                    } catch (Exception e) {
                        listaParaEnvio.get(i).setId_android("EXCEPTION");
                    }
                }
                sincronia.setListaWebPedidosPendentes(prepararItensPedidos(listaParaEnvio));
            }

            if (sincronia.isProspectPendentes())
                try {
                    final List<Prospect> lista = prospectDAO.getLista(Prospect.PROSPECT_SALVO);
                    sincronia.setListaProspectPendentes(lista);
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            if (sincronia.isVisitasPendentes())
                sincronia.setVisitas(visitaProspectDAO.getListaPendentes());//db.listaProspectsPendentes());
            if (sincronia.isCobranca()) {
                String id_android = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                sincronia.setListaRecebimentos(webReceberCabDAO.getLista("SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_ANDROID = '" + id_android + "'"));  //N nao sincronizado
            }

            String key_firebase = "00000001";

            Call<Sincronia> call = null;
            try {
                if( latitude.equalsIgnoreCase("-") || longitude.equalsIgnoreCase("-"))
                    call = apiRotas.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase, versaoApp, cabecalho, sincronia);
                else
                    call = apiRotas.sincroniaApiChekin(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase,
                            versaoApp, latitude, longitude,cabecalho, sincronia);
            } catch (NullPointerException e) {
                apiRotas = Api.buildRetrofit(true);
                try {
                    if( latitude.equalsIgnoreCase("-") || longitude.equalsIgnoreCase("-"))
                        call = apiRotas.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase, versaoApp, cabecalho, sincronia);
                    else
                        call = apiRotas.sincroniaApiChekin(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase,
                                versaoApp, latitude, longitude, cabecalho, sincronia);
                } catch (NullPointerException ex) {
                    //Toast.makeText(getApplicationContext(), "Falha ao criar a rotina para sincronização, feche o aplicativo e tente novamente!", Toast.LENGTH_SHORT).show();
                    isSincronizando = !isSincronizando;
                    finish();
                    return;
                } catch (Exception ex) {
                    //Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    isSincronizando = !isSincronizando;
                    finish();
                    return;
                }
            }
            this.runOnUiThread(() -> updateProgressMessage("Conectado a base de dados! Aguarde..."));

            call.enqueue(new Callback<Sincronia>() {
                @Override
                public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                    switch (response.code()) {
                        case 200:
                            Log.w("RCK ", "sincronizaAPI 3");
                            if (response.body() == null) {
                                isSincronizando = !isSincronizando;
                                showHideLAyoutEncerrar(true, false, false, "FALHA NA SINCRONIA!\n" + response.code());
                                return;
                            }
                            Sincronia sincronia1 = response.body();
                            //System.gc();
                            sincronizaBanco(sincronia1, versaoApp);
                            break;
                        case 500:
                            runOnUiThread(() -> {
                                if (progress != null) progress.dismiss();
                                dismissProgressDialogCustom();
                            });
                            isSincronizando = !isSincronizando;
                            //showMsgAlerta("Falha ao sincronizar com a base de dados!!");
                            showHideLAyoutEncerrar(true, false, false, "FALHA NA SINCRONIA !\n" + response.code());
                            break;
                        default:
                            runOnUiThread(() -> {
                                if (progress != null) progress.dismiss();
                                dismissProgressDialogCustom();
                            });
                            isSincronizando = !isSincronizando;
                            //showMsgAlerta("Falha ao sincronizar com a base de dados!!!");
                            showHideLAyoutEncerrar(true, false, false, "FALHA NA SINCRONIA !!");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Sincronia> call, Throwable t) {
                    runOnUiThread(() -> {
                        if (progress != null) progress.dismiss();
                        dismissProgressDialogCustom();
                    });
                    isSincronizando = !isSincronizando;
                    showHideLAyoutEncerrar(true, false, false, "FALHA NA SINCRONIA !!!");
                    t.printStackTrace();
                }
            });
        });
        a.start();

    }

    public void getListaWebPedidoCliente(String idCadastro) {
        final Configuracao configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            String url = configuracao.getUrl_ip_1().trim() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + pInfo.versionName.substring(0, 5) + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.context = context;
            apiRotas = Api.buildRetrofit(true);

        }
        @SuppressLint("HardwareIds") final Thread a = new Thread(() -> {
            Map<String, String> cabecalho = new HashMap<>();
            try {
                if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                    UsuarioBO usuarioBO = new UsuarioBO();
                    UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
                }
            } catch (NullPointerException e) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
            } catch (Exception e) {
            }

            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<WebPedido>> call = apiRotas.listaEnviadosCliente( UsuarioHelper.getUsuario().getId_usuario(),
                    idCadastro, cabecalho);
            call.enqueue(new Callback<List<WebPedido>>() {
                @Override
                public void onResponse(Call<List<WebPedido>> call, Response<List<WebPedido>> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            List<WebPedido> lista = response.body();
                            if (!lista.isEmpty()) {
                                DBHelper db = new DBHelper(ActivitySincronia.this);
                                db.deleteDados("TBL_WEB_PEDIDO", "ID_CADASTRO = ? AND ID_WEB_PEDIDO_SERVIDOR > 0", new String[]{idCadastro});
                                new WebPedidoDAO(db).addListaWebPedidos(lista, db);
                                    runOnUiThread(() -> {
                                        dismissProgressDialogCustom();
                                        Toast.makeText(context,  "Histórico do cliente sincronizado com sucesso!Aguarde...", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(context, ActivityPedidoEnviado.class)
                                                .putExtra("idcliente", idCadastro));
                                        finish();
                                    });

                            } else {
                                runOnUiThread(this::dismissProgressDialogCustom);
                                Toast.makeText(context,  "Sem registros no histórico do cliente informado!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            runOnUiThread(this::dismissProgressDialogCustom);
                            Toast.makeText(context,  "Falha ao obter os dados do relatorio!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        runOnUiThread(this::dismissProgressDialogCustom);
                        Toast.makeText(context, "Falha ao obter os dados do relatorio!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<List<WebPedido>> call, Throwable t) {
                    runOnUiThread(this::dismissProgressDialogCustom);
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }

                private void dismissProgressDialogCustom() {
                    ActivitySincronia.this.dismissProgressDialogCustom();
                }
            });

        });
        a.start();


    }

    /*
    public void sincronizaApi2(final Sincronia sincronia, final String versaoApp, ProgressDialog progress) {
        final ImageView ivInternet = (ImageView) activity.findViewById(R.id.ivInternet);
        categoriaDAO = new CategoriaDAO(db);
        promocaoDAO = new PromocaoDAO(db);
        promocaoClienteDAO = new PromocaoClienteDAO(db);
        promocaoProdutoDAO = new PromocaoProdutoDAO(db);
        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
        cadastroAnexoDAO = new CadastroAnexoDAO(db);

        campanhaComClientesDAO = new CampanhaComClientesDAO(db);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        produtoLinhaColecaoDAO = new ProdutoLinhaColecaoDAO(db);

        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.mipmap.ic_sincroniza_main)
                .setContentTitle("Sincronia em andamento...")
                .setContentText("Aguarde")
                .setPriority(2)
                .setProgress(0, 0, true);
        final NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notificacao.build());

        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
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
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivInternet.setVisibility(View.VISIBLE);
                        progress.dismiss();
                    }
                });
                notificacao.setContentText("Erro de comunicação")
                        .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                        .setProgress(0, 0, false)
                        .setSmallIcon(R.mipmap.ic_sem_internet)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(2)
                        .setAutoCancel(true);
                mNotificationManager.notify(0, notificacao.build());
                ActivityPrincipal.setSincronizando();
                return;
            }
        } catch ( NullPointerException e) {
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
            return;
        }
        final Rotas apiRotas2 = Api.buildRetrofit2(true);


        Map<String, String> cabecalho = new HashMap<>();
        try {
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        } catch (Exception e) {
        }

        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        sincronia.setDias_hist_venda(30);
        sincronia.setDias_hist_financeiro(30);
        Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
        try {
            sincronia.setDias_hist_venda(preferencia.getDias_hist_venda());
            sincronia.setDias_hist_financeiro(preferencia.getDias_hist_financeiro());
            sincronia.setDias_hist_pedido(preferencia.getDias_hist_pedido());
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sincronia.isPedidosPendentes()) {
            final List<WebPedido> listaParaEnvio = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
            for (int i = 0; i < listaParaEnvio.size(); i++) {
                if (listaParaEnvio.get(i).getCadastro().getId_cadastro_servidor() <= -1) {
                    listaParaEnvio.get(i).getCadastro().setId_cadastro_servidor(0);
                }
            }
            sincronia.setListaWebPedidosPendentes(prepararItensPedidos(listaParaEnvio));
        }

        if (sincronia.isProspectPendentes())
            try {
                final List<Prospect> lista = prospectDAO.getLista(Prospect.PROSPECT_SALVO);
                sincronia.setListaProspectPendentes(lista);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        if (sincronia.isVisitasPendentes())
            sincronia.setVisitas(visitaProspectDAO.getListaPendentes());

        String key_firebase = "00000001";
        Call<Sincronia> call = apiRotas2.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase, versaoApp, cabecalho, sincronia);
        try {
            Response<Sincronia> response = call.execute();
            if (response.code() == 200) {
                Sincronia sincronia2 = response.body();
                activity.runOnUiThread(() -> ivInternet.setVisibility(View.GONE));
                sincronizaBanco(sincronia2, versaoApp, notificacao, mNotificationManager, progress);
            } else {
                activity.runOnUiThread(() -> {
                    ivInternet.setVisibility(View.VISIBLE);
                    progress.dismiss();
                });
                notificacao.setContentText("Erro de comunicação")
                        .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                        .setProgress(0, 0, false)
                        .setSmallIcon(R.mipmap.ic_sem_internet)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(2)
                        .setAutoCancel(true);
                mNotificationManager.notify(0, notificacao.build());
                ActivityPrincipal.setSincronizando();
            }
        } catch (ConnectException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Verifique sua conexão e tente novamente")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Verifique sua conexão e tente novamente")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Verifique sua conexão e tente novamente")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
        }
        activity = null;
    }
    */
    private List<WebPedido> prepararItensPedidos(List<WebPedido> listaPedido) {
        for (WebPedido pedido : listaPedido) {
            if (pedido.getCadastro().getId_cadastro_servidor() <= 0)
                if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + pedido.getCadastro().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                    final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                    List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(context, pedido.getCadastro().getId_cadastro());
                    pedido.getCadastro().setListaCadastroAnexo(listaCadastroAnexo);
                }
            List<WebPedidoItens> webPedidoItenses;
            webPedidoItenses = webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM");
            pedido.setWebPedidoItens(webPedidoItenses);
        }
        return listaPedido;
    }

    private void excluirPedidoSincronizado() {
        List<WebPedido> listaPedido;
        try {
            Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
            if (!preferencia.getId().isEmpty()) {
                if (preferencia.getDias_hist_pedido() > 0) {
                    String data = preferencia.getDias_hist_pedido() == 1 ? db.pegaDataExclusao("  SELECT date('now')") : db.pegaDataExclusao("  SELECT date('now',  '-" + ( preferencia.getDias_hist_pedido() + 1 ) + " days')");
                    if (!data.isEmpty())
                        try {
                            listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND DATA_EMISSAO <= '" + data + "' ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC");
                            for (int i = 0; i < listaPedido.size(); i++)
                                webPedidoDAO.delete(listaPedido.get(i));
                                    //totalDeletePedido++;
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                } else {
                    try {
                        listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC");
                        for (int i = 0; i < listaPedido.size(); i++)
                            webPedidoDAO.delete(listaPedido.get(i));
                                //totalDeletePedido++;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                    try {
                        listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC");
                        for (int i = 0; i < listaPedido.size(); i++)
                            webPedidoDAO.delete(listaPedido.get(i));
                        //totalDeletePedido++;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void showMsgAlerta(String mensagem, boolean closeActivity) {
        ViewGroup viewGroup = ActivitySincronia.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( ActivitySincronia.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySincronia.this);
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
                if ( closeActivity) {
                    startActivity(new Intent(ActivitySincronia.this, ActivityPrincipal.class).putExtra("alterado", 0));
                    finish();
                }
                //PedidoHelper.getActivityPedidoMain().finish();
            }
        });
        alertDialog.show();
    }

    public  void showMsgDialog(String mensagem, List<WebPedido> listaPedido) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button buttonSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        buttonSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                for (WebPedido pedido : listaPedido) {
                    if (pedido.getDescontoIndevido().equalsIgnoreCase("S")) {
                        showFeedbackDialog("O pedido " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + " esta com desconto indevido!\nÉ aconselhavel a correção do mesmo, antes do envio/sincronização ao servidor!", false);
                        id_opcao_pedidos_pendentes.setChecked(false);
                        break;
                    }
                }
                for (WebPedido pedido : listaPedido) {
                   try {
                       if (Integer.parseInt(pedido.getId_web_pedido_servidor()) > 0) {
                           showFeedbackDialog("O pedido " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + " ja foi sincronizado!\nÉ aconselhavel a confirmação junto a empresa, antes do envio/sincronização ao servidor!", false);
                           id_opcao_pedidos_pendentes.setChecked(false);
                           break;
                       }
                   } catch ( NullPointerException e){

                   } catch ( NumberFormatException e) {

                   }
                }

            }
        });

        Button buttonNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        buttonNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                id_opcao_pedidos_pendentes.setChecked(false);
            }
        });
        alertDialog.show();
    }
*/
    public Location getLocationWithCheckNetworkAndGPS() {
        LocationManager lm = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        boolean isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkLocationEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location networkLoacation = null, gpsLocation = null, finalLoc = null;
        if (isGpsEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if ( context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }
            }
        } else {
            Toast.makeText( context,
                    "GPS não habilitado!!",
                    Toast.LENGTH_SHORT).show();
            return finalLoc;
        }

        lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        Log.d("GPS Enabled", "GPS Enabled");



        gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (isNetworkLocationEnabled)
            networkLoacation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation != null && networkLoacation != null) {
            if (gpsLocation.getAccuracy() > networkLoacation.getAccuracy()) {
                return finalLoc = networkLoacation;
            } else {
                return finalLoc = gpsLocation;
            }
        } else {
            try {
                if (gpsLocation != null) {
                    return finalLoc = gpsLocation;
                } else if (networkLoacation != null) {
                    return finalLoc = networkLoacation;
                }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
        return finalLoc;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    private void getPedidoFirebase(String cnpj) {
        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        /*
        fdb.collection(cnpj)
                .whereEqualTo("usuario_lancamento_id", idUsuario)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int qtdePedRest = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                WebPedido webPedido = document.toObject(WebPedido.class);
                                if( db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE ID_PEDIDO_APP = '" + webPedido.getId_pedido_app() + "'") == 0) {
                                    long  ultId = webPedidoDAO.add(webPedido);
                                    if ( ultId > 0) {
                                        qtdePedRest++;
                                        for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                                            webPedidoItens.setId_pedido(String.valueOf(ultId));
                                            webPedidoItensDAO.add(webPedidoItens);
                                        }
                                    }
                                }
                            }
                            if (qtdePedRest >= 1)
                                showMsgAlerta(qtdePedRest == 1 ? "O total de 1 pedido foi restaurado com sucesso"
                                        : "O total de " + qtdePedRest + " foram restaurados com sucesso", false);
                            else
                                showMsgAlerta("Nenhum pedido foi restaurado, em caso de duvidas entre em contato com o suporte técnico!", false);
                            isSincronizando = !isSincronizando;
                            try {
                                progress.dismiss();
                            } catch ( NullPointerException e) {
                                e.printStackTrace();
                            }

                        } else {
                            isSincronizando = !isSincronizando;
                            showMsgAlerta("Falha ao verificar pedidos para serem restaurados!" , false);
                            try {
                                progress.dismiss();
                            } catch ( NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        */
        fdb.collection(cnpj).document("TBL_WEB_PEDIDO").collection(UsuarioHelper.getUsuario().getId_quando_vendedor()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int qtdePedRest = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                WebPedido webPedido = document.toObject(WebPedido.class);
                                if( db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE ID_PEDIDO_APP = '" + webPedido.getId_pedido_app() + "'") == 0) {
                                    long  ultId = webPedidoDAO.add(webPedido);
                                    if ( ultId > 0) {
                                        webPedidoDAO.updateIdFDB(webPedido, String.valueOf(ultId), webPedido.getData_prev_entrega(), webPedido.getFinalizado());
                                        qtdePedRest++;
                                        for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                                            webPedidoItens.setId_pedido(String.valueOf(ultId));
                                            webPedidoItensDAO.add(webPedidoItens);
                                        }
                                    }
                                }
                            }
                            if (qtdePedRest >= 1)
                                showFeedbackDialog(qtdePedRest == 1 ? "O total de 1 pedido foi restaurado com sucesso"
                                        : "O total de " + qtdePedRest + " foram restaurados com sucesso", false, null);
                            else
                                showFeedbackDialog("Nenhum pedido foi restaurado, em caso de duvidas entre em contato com o suporte técnico!", false, R.drawable.ic_baseline_error_outline_24);
                            isSincronizando = !isSincronizando;
                            try {
                                progress.dismiss();
                            } catch ( NullPointerException e) {
                                e.printStackTrace();
                            }
                            showHideLAyoutEncerrar(true, true, false, "");
                        } else {
                            isSincronizando = !isSincronizando;
                            showFeedbackDialog("Falha ao verificar pedidos para serem restaurados!" , false, R.drawable.ic_baseline_error_outline_24);
                            try {
                                progress.dismiss();
                            } catch ( NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                        showHideLAyoutEncerrar(true, false, false, "");
                    }
                });
        /*
        db.collection(cnpj)
                .document(idPedidoApp)
                .collection("itens")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                WebPedidoItens webPedidoItens = document.toObject(WebPedidoItens.class);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        */
    }

    private void getClienteFirebase(String cnpj) {
        clienteDAO = new ClienteDAO(db);
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        fdb.collection(cnpj).document("TBL_WEB_CLIENTE").collection(UsuarioHelper.getUsuario().getId_quando_vendedor()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int qtdeCliRest = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cliente webCliente = document.toObject(Cliente.class);
                                if( db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE CPF_CNPJ = '" + webCliente.getCpf_cnpj()
                                        + "' OR ID_CLIENTE_APP_FDB = '" + webCliente.getId_cliente_app_fdb() + "'") == 0) {
                                    long  ultId = clienteDAO.add(webCliente);
                                    if ( ultId > 0) {
                                        clienteDAO.updateIdFDB(webCliente, String.valueOf(ultId));
                                        qtdeCliRest++;
                                    }
                                }
                            }
                            if (qtdeCliRest >= 1)
                                showFeedbackDialog( qtdeCliRest == 1 ? "O total de 1 cliente foi restaurado com sucesso"
                                        : "O total de " + qtdeCliRest + " foram restaurados com sucesso", false, null);
                            else
                                showFeedbackDialog("Nenhum cadastro de cliente foi restaurado, em caso de duvidas entre em contato com o suporte técnico!", false, R.drawable.ic_baseline_error_outline_24);
                            isSincronizando = !isSincronizando;
                            try {
                                progress.dismiss();
                            } catch ( NullPointerException e) {
                                e.printStackTrace();
                            }
                            showHideLAyoutEncerrar(true, true, false, "");
                        } else {
                            isSincronizando = !isSincronizando;
                            showFeedbackDialog("Falha ao verificar pedidos para serem restaurados!" , false, null);
                            try {
                                progress.dismiss();
                            } catch ( NullPointerException e) {
                                e.printStackTrace();
                            }
                            showHideLAyoutEncerrar(true, false, false, "");
                        }

                    }
                });
        /*
        db.collection(cnpj)
                .document(idPedidoApp)
                .collection("itens")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                WebPedidoItens webPedidoItens = document.toObject(WebPedidoItens.class);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        */
    }

    public void showFeedbackDialog(String mensagem, boolean fimActivity, Integer iconId) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                this,
                 "Atenção", // Título dinâmico
                mensagem,
                true,
                iconId,
                null, // Passando a cor personalizada
                () -> {
                    if ( fimActivity) {
                        startActivity(new Intent(ActivitySincronia.this, ActivityPrincipal.class).putExtra("alterado", 0));
                        finish();
                    }
                }
        );
    }

    public void showActionDialog( List<WebPedido> listaPedido, String mensagem) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {

                    }
                    @Override
                    public void onNegative() {
                        for (WebPedido pedido : listaPedido) {
                            if (pedido.getDescontoIndevido().equalsIgnoreCase("S")) {
                                showFeedbackDialog("O pedido " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + " esta com desconto indevido!\nÉ aconselhavel a correção do mesmo, antes do envio/sincronização ao servidor!", false, R.drawable.ic_alerta_mensagem_color_primary);
                                id_opcao_pedidos_pendentes.setChecked(false);
                                break;
                            }
                        }
                        for (WebPedido pedido : listaPedido) {
                            try {
                                if (Integer.parseInt(pedido.getId_web_pedido_servidor()) > 0) {
                                    showFeedbackDialog("O pedido " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + " ja foi sincronizado!\nÉ aconselhavel a confirmação junto a empresa, antes do envio/sincronização ao servidor!", false, R.drawable.ic_alerta_mensagem_color_primary);
                                    id_opcao_pedidos_pendentes.setChecked(false);
                                    break;
                                }
                            } catch ( NullPointerException e){

                            } catch ( NumberFormatException e) {

                            }
                        }
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }

}