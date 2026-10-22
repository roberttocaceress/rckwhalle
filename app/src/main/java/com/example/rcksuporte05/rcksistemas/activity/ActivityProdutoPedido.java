package com.example.rcksuporte05.rcksistemas.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.rcksuporte05.rcksistemas.BO.PedidoBO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CondicoesPagamentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoSkuDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VendedorBonusResumoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ImagemProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoParametroSku;
import com.example.rcksuporte05.rcksistemas.model.PromocaoRetorno;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.itextpdf.text.pdf.PdfName;


public class ActivityProdutoPedido extends AppCompatActivity {

    //@BindView(R.id.rgRealPorc)
    //RadioGroup rgRealPorc;
    @BindView(R.id.activity_produto_pedido)  //root
    LinearLayout activity_produto_pedido;

    @BindView(R.id.lytProduto)
    RelativeLayout lyProduto;

    @BindView(R.id.porcentagem)
    RadioButton rbPorcentagem;
    @BindView(R.id.real)
    RadioButton rbReal;
    @BindView(R.id.edtNomeProduto)
    TextView edtNomeProduto;
    @BindView(R.id.edtTabelaPreco)
    EditText edtTabelaPreco;
    @BindView(R.id.edtQuantidade)
    EditText edtQuantidade;
    @BindView(R.id.edtValorProdutos)
    EditText edtValorProdutos;

    @BindView(R.id.edtValorPrecoPago)
    EditText edtValorPrecoPago;
    @BindView(R.id.edtDesconto)
    EditText edtDesconto;
    @BindView(R.id.edtDescontoReais)
    EditText edtDescontoReais;
    @BindView(R.id.edtDescontoVerba)
    EditText edtDescontoVerba;
    @BindView(R.id.edtvalorIcmsST)
    EditText edtvalorIcmsST;
    @BindView(R.id.edtTotal)
    EditText edtTotal;
    @BindView(R.id.tb_produto_pedido)
    Toolbar toolbar;
    @BindView(R.id.btnBuscaProduto)
    Button btnBuscaProduto;
    @BindView(R.id.cdPromocao)
    CardView cdPromocao;
    @BindView(R.id.txtPromocao)
    TextView txtPromocao;
    @BindView(R.id.btnSubtraiQuantidade)
    Button btnSubtraiQuantidade;
    @BindView(R.id.btnSomaQuantidade)
    Button btnSomaQuantidade;

    @BindView(R.id.btnCancelar)
    Button btnCancelar;

    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    //Implementando campanha
    @BindView(R.id.lymainCampanha)
    LinearLayout lymainCampanha;
    @BindView(R.id.lyCampanha)
    LinearLayout lyCampanha;
    //Aplicabdo regra p visualizar desconto em reais
    @BindView(R.id.lydsctoReais)
    LinearLayout lydsctoReais;

    @BindView(R.id.lydsctoVerba)
    LinearLayout lydsctoVerba;

    @BindView(R.id.lyvalorIcmsST)
    LinearLayout lyvalorIcmsST;

    @BindView(R.id.edtNomeCampanha)
    EditText edtNomeCampanha;
    @BindView(R.id.btnBuscaCampanha)
    Button btnBuscaCampanha;
    @BindView(R.id.btnInfoCampanha)
    Button btnInfoCampanha;

    //Implementando sku
    @BindView(R.id.lymainSku)
    LinearLayout lymainSku;
    @BindView(R.id.lySku)
    LinearLayout lySku;

    @BindView(R.id.spnSku)
    Spinner spnSku;

    @BindView(R.id.view_pager_produto_img)
    ViewPager mViewPager;

    @BindView(R.id.circle_view_produto_img)
    CircleIndicator indicator;

    @BindView(R.id.txtPesquisaProduto)
    TextView txtPesquisaProduto;

    private WebPedidoItens webPedidoItem;
    private MenuItem salvar_produto;
    private DBHelper db = new DBHelper(this);

    private TabelaPrecoItem tabelaPrecoItem;
    private PedidoBO pedidoBO = new PedidoBO();
    private PromocaoRetorno promocaoRetorno;
    private Float quantidade;
    private Float totalProdutoBruto;
    private float totalProdutoUnitario;
    private float totalICMS;
    private Float desconto;
    private float descontoVerba;
    private Float desconto_perc;
    private Float desconto_tabela;
    private Float descontoUnitario;
    private Float valorIcmsST;
    private Boolean prazo_permite_desconto;

    private ImagemProdutoAdapter mViewPagerAdapter;
    private ArrayList<ProdutoFoto> mContents;
    private List<ProdutoFoto> listaProdutoFoto;
    private List<ProdutoParametroSku> listaSku;
    private ArrayAdapter<ProdutoParametroSku> adapterSku;

    private static int currentPage = 0;
    private static int numPage = 0;
    private ActionMode actionMode;
    private Handler handler = new Handler();
    private Runnable runnable;
    private Timer timer;

    private CampanhaComercialCabDAO campanhaComercialCabDAO;
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private ProdutoSkuDAO produtoSkuDAO = new ProdutoSkuDAO(db);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private EmpresaParametro empresaParametro;
    private Configuracao configuracao ;
    private Context context;
    private boolean verificaCampanha;

    private boolean enterClicado = false;
    TextWatcher textWatcherDesconto;// = new TextWatcher();
    TextWatcher textWatcherPreco;   // = new TextWatcher();
    @OnClick(R.id.btnCancelar)
    public void cancelar() {
        PedidoHelper.setWebPedidoItem(null);
        PedidoHelper.setProduto(null);
        PedidoHelper.setListaProdutos(null);
        onBackPressed();
    }

    @OnClick(R.id.btnConfirmar)
    public void confirmar() {
        if (!PedidoHelper.isPedido1Valido()) {
            showFeedbackDialog("A sessão de venda expirou devido à inatividade. Por favor, retorne a tela principal do pedido e tente novamente.", false, this::onBackPressed);
            return;
        }
        salvarProduto();
    }

    @OnClick(R.id.btnSomaQuantidade)
    public void somaQuantidade() {
        try {
            Float valor = Float.parseFloat(edtQuantidade.getText().toString());
            valor++;
            if (valor <= 0) {
                btnSubtraiQuantidade.setClickable(false);
                edtQuantidade.setText("0");
            } else {
                btnSubtraiQuantidade.setClickable(true);
                edtQuantidade.setText(String.valueOf(valor).replace(".0", ""));
            }
        } catch (Exception e) {
            edtQuantidade.setText("1");
            btnSubtraiQuantidade.setClickable(false);
        }
    }

    @OnClick(R.id.btnSubtraiQuantidade)
    public void subtraiQuantidade() {
        try {
            Float valor = Float.parseFloat(edtQuantidade.getText().toString());
            valor--;
            if (valor <= 0) {
                btnSubtraiQuantidade.setClickable(false);
                edtQuantidade.setText("0");
            } else {
                btnSubtraiQuantidade.setClickable(true);
                edtQuantidade.setText(String.valueOf(valor).replace(".0", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            edtQuantidade.setText("0");
            btnSubtraiQuantidade.setClickable(false);
        }
    }

    @OnClick(R.id.btnInfoCampanha)
    public void infoCampanha() {
        CampanhaComercialItensDAO campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        Intent intent = new Intent(ActivityProdutoPedido.this, ActivityCampanhaDetalhe.class);
        try {
            if (CampanhaHelper.getCampanhaComercialCab().getIdBaseCampanha() == 1)
                CampanhaHelper.setItemCampanhaDetalhe(campanhaComercialItensDAO.getCampanhaComercialItens(CampanhaHelper.getCampanhaComercialCab(), webPedidoItem.getIdLinhaColecao()));
            else if (CampanhaHelper.getCampanhaComercialCab().getIdBaseCampanha() == 2)
                CampanhaHelper.setItemCampanhaDetalhe(campanhaComercialItensDAO.getCampanhaComercialItens(CampanhaHelper.getCampanhaComercialCab(), webPedidoItem.getId_produto()));
            else
                showFeedbackDialog("Falha ao carregar informações da campanha", true);
                //Toast.makeText(this, "Falha ao carregar informações da campanha", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            showFeedbackDialog("Falha ao carregar informações da campanha", true);
            //Toast.makeText(this, "Falha ao carregar informações da campanha", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.gc();
        /*
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup.LayoutParams default_layout_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view1 = inflater.inflate(R.layout.activity_produto_pedido_layout_1, null);
        addContentView(view1, default_layout_params);
        View view2 = inflater.inflate(R.layout.activity_produto_pedido_layout_2, null);
        addContentView(view2, default_layout_params);
        view2.setVisibility(View.VISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view1.bringToFront();
        */
        context = this;
        getConfiguracaoEmpresa();
        try {
            if (configuracao.getGira_tela_pedido().isEmpty() || configuracao.getGira_tela_pedido().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if ( configuracao.getLayout_venda_erp().equalsIgnoreCase("1"))
            setContentView(R.layout.activity_produto_pedido_layout_sem_tabela_preco);
        else {
            setContentView(R.layout.activity_produto_pedido_layout_tabela_preco_2025);
            // 2. AJUSTE DINÂMICO DE BARRAS (Funciona em TODAS as versões)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_produto_pedido), (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

                // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
                View toolbar = findViewById(R.id.tb_produto_pedido);
                toolbar.setPadding(0, insets.top, 0, 0);

                // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
                View rodape = findViewById(R.id.linearLayout3);
                rodape.setPadding(0, 0, 0, insets.bottom);

                return WindowInsetsCompat.CONSUMED;
            });
        }
        ButterKnife.bind(this);
        int orientation = this.getResources().getConfiguration().orientation;
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        activity_produto_pedido.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if ( orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    int heightDiff = activity_produto_pedido.getRootView().getHeight() - activity_produto_pedido.getHeight();
                    //switch (screenSize) {
                    //    case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    //        break;
                    //    case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    //        break;
                    //    default:
                            if (heightDiff > 100)
                                showHideLayout(false);
                            else
                                showHideLayout(true);
                    //        break;
                    //}
                }
            }
        });


        PedidoHelper pedidoHelper = new PedidoHelper(this);

        desconto_perc = 0.0f;
        desconto_tabela = 0.0f;
        verificaCampanha = true;
        try {
            if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"))
                campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        prazo_permite_desconto = false;
        try {
            Cursor cursor = db.listaDados("SELECT DESCONTO_PERC FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = '" + PedidoHelper.getWebPedido().getId_condicao_pagamento() + "' AND ACEITA_DESCONTO = 'S'");
            if (cursor.moveToNext()) {
                desconto_perc = Float.parseFloat(cursor.getString(cursor.getColumnIndex("DESCONTO_PERC")));
                prazo_permite_desconto = true;
            }

        } catch (SQLException | NumberFormatException | NullPointerException e) {
        } catch (Exception e) {
        }

        try {
            webPedidoItem = pedidoHelper.getWebPedidoItem();
            try {
                if ( webPedidoItem.getProduto_edita_valor().equalsIgnoreCase("S") && webPedidoItem.getProduto_preco_editado() > 0.00f)
                    webPedidoItem.setVenda_preco( String.valueOf( webPedidoItem.getProduto_preco_editado()));
            } catch ( NullPointerException|NumberFormatException e) {
                e.printStackTrace();
            }
            if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                if ( ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                    if (ClienteHelper.getCliente().getIdCategoria() >= 1) {
                        tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria()).get(0);
                        try {
                            desconto_perc = Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());
                        } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
                            desconto_perc = 0.0f;
                        } catch (Exception e) {

                        }
                    }
                } else if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4") ) {
                    if (ClienteHelper.getCliente().getIdCategoria() >= 1) {
                        tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria()).get(0);
                        if (PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S")) {
                            try {
                                desconto_perc = Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());
                            } catch ( NullPointerException|NumberFormatException e) {
                                desconto_perc = 0.0f;
                            }
                        } else {
                            desconto_perc = 0.0f;
                        }
                    }
                } else if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6")) {
                    if (ClienteHelper.getCliente().getId_tabela_preco() >= 1) {
                        try {
                            if (PedidoHelper.getListaProdutos().size() >= 1) {
                                int x = PedidoHelper.getListaProdutos().size();
                                for (int i = 0; i < PedidoHelper.getListaProdutos().size(); i++) {
                                    tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_PRODUTO = " + PedidoHelper.getListaProdutos().get(i).getId_produto() + " AND ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco()).get(0);
                                    if (!tabelaPrecoItem.getId_item().isEmpty()) {
                                        tabelaPrecoItem.setPerc_desc_final("0");
                                        desconto_perc = 0.0f;
                                    }

                                }
                            }
                        } catch (NullPointerException e) {
                            String id_produto = webPedidoItem != null ? webPedidoItem.getId_produto() : PedidoHelper.getProduto().getId_produto();
                            tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_PRODUTO = " + id_produto + " AND ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco()).get(0);
                            if (!tabelaPrecoItem.getId_item().isEmpty()) {
                                tabelaPrecoItem.setPerc_desc_final("0");
                                desconto_perc = 0.0f;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            rbPorcentagem.setText("Desconto %(max " + desconto_perc + "%)");
            try {
                if (configuracao.getTrabalha_com_desc_auto_app().equalsIgnoreCase("N")) {

                    if ( webPedidoItem.getValor_desconto_per() == null || webPedidoItem.getValor_desconto_per().isEmpty()) {
                        edtDesconto.setText("0.00");
                    } else {
                        edtDesconto.setText(String.valueOf(webPedidoItem.getValor_desconto_per()));
                    }
                } else {
                    edtDesconto.setText(String.valueOf(desconto_perc));
                }
            }catch ( NullPointerException e) {
                edtDesconto.setText(String.valueOf(desconto_perc));
            }
        } catch (Exception e) {
            rbPorcentagem.setText("Desconto %(max " + String.valueOf(desconto_perc) + "%)");
        }


        edtQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Float valor = Float.parseFloat(edtQuantidade.getText().toString());
                    if (valor <= 0)
                        btnSubtraiQuantidade.setClickable(false);
                    else
                        btnSubtraiQuantidade.setClickable(true);
                } catch (Exception e) {
                    btnSubtraiQuantidade.setClickable(false);
                }
                calculaDesconto();
            }
        });
        edtTabelaPreco.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!enterClicado) {
                        enterClicado = true;
                        confirmar();
                    } else {
                        enterClicado = false;
                        return false;
                    }

                } else {
                    return false;
                }
                return true;
            }
        });

        edtTabelaPreco.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        textWatcherDesconto  = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (rbPorcentagem.isChecked())
                    //edtTabelaPreco.setText(MascaraUtil.mascaraVirgula(webPedidoItem.getVenda_preco()));
                    calculaDesconto();
            }
        };

        edtDesconto.addTextChangedListener(textWatcherDesconto);
        edtDesconto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Ação quando o EditText RECEBE foco
                    //edtTabelaPreco.removeTextChangedListener(textWatcherPreco);
                    //edtTabelaPreco.setText(MascaraUtil.duasCasaDecimal(webPedidoItem.getVenda_preco()));
                } else {
                    //edtTabelaPreco.addTextChangedListener(textWatcherPreco);
                }
            }
        });


        edtDescontoReais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (rbReal.isChecked())
                    calculaDesconto();
            }
        });

        //edtDescontoVerba.setText("0.00");
        //edtDescontoVerba.setEnabled(false);
        edtDescontoVerba.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!configuracao.getTrabalha_com_verba_pedido().isEmpty() || configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S")) {
                    try {
                        if (!edtDesconto.getText().toString().isEmpty()) {
                            edtDescontoVerba.setBackgroundResource(R.drawable.borda_edittext);
                            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                                if (Float.parseFloat(edtDescontoVerba.getText().toString()) > Float.parseFloat("5.00")) {
                                    edtDescontoVerba.setBackgroundResource(R.drawable.borda_edittext_erro);
                                    edtDescontoVerba.selectAll();
                                    showFeedbackDialog("Percentual do desconto informado acima do máximo permitido conforme definido na tabela do cliente!", true);
                                    //Toast.makeText(ActivityProdutoPedido.this, "Percentual do desconto informado acima do máximo permitido conforme definido na tabela do cliente!", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    calculaDesconto();
                                }
                            } else {
                                calculaDesconto();
                            }
                        }
                    } catch (NullPointerException e) {
                    } catch (Exception e) {
                    }
                }
            }
        });
        //edtDescontoVerba.setEnabled(false);
        textWatcherPreco = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    try {
                        if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                            String precoEdit = edtTabelaPreco.getText().toString();
                            //Ajustei aqui p verificar se o preço atualiza para o valor editado 15-05-2026
                            //if ( configuracao.getLayout_venda_erp().equalsIgnoreCase("1"))
                               webPedidoItem.setVenda_preco(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", "."));

                            Float preco_produto  = Float.parseFloat(webPedidoItem.getVenda_preco());
                            Float preco_alterado = Float.parseFloat(edtTabelaPreco.getText().toString().toString().replaceAll("\\s+", "").replace(",", "."));
                            Float percentual_desconto = 0.00f;
                            BigDecimal bdesconto = null;
                            if ( preco_alterado <= preco_produto) {
                                percentual_desconto = (preco_alterado / preco_produto * 100) - 100;
                                bdesconto = new BigDecimal(-percentual_desconto).setScale(4, RoundingMode.HALF_EVEN);
                            } else {

                                edtDesconto.setText("0.00");
                                //return;
                            }
                            //desconto += Float.parseFloat(String.valueOf(bdesconto));
                            edtDesconto.setText(String.valueOf( Float.parseFloat(String.valueOf(bdesconto))));
                            return;

                        }
                    } catch (NullPointerException e) {
                       e.printStackTrace();
                    }
                    if (webPedidoItem.getProduto_edita_valor().equalsIgnoreCase("S")) {
                        if (!edtTabelaPreco.getText().toString().trim().isEmpty()) {
                            //Ajustei aqui em 18-05-2026
                            if (webPedidoItem.getProduto_edita_valor().equalsIgnoreCase("S") && webPedidoItem.getProduto_preco_editado() > 0.00f)
                                webPedidoItem.setVenda_preco(webPedidoItem.getProduto_edita_valor().toString().replaceAll("\\s+", "").replace(",", "."));
                            else
                               webPedidoItem.setVenda_preco(edtTabelaPreco.getText().toString().toString().replaceAll("\\s+", "").replace(",", "."));

                            //webPedidoItem.setProduto_preco_editado(Float.parseFloat(edtTabelaPreco.getText().toString().toString().replaceAll("\\s+", "").replace(",", ".")));
                            calculaDesconto();
                        } else {
                            webPedidoItem.setProduto_preco_editado(0.0f);
                        }
                        return;
                    }


                } catch ( NumberFormatException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
        //edtTabelaPreco.addTextChangedListener(textWatcherPreco);
        toolbar.setTitle("Item de venda(inclusão)");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getIntExtra("vizualizacao", 0) == 1) {
            edtNomeProduto.setFocusable(false);
            edtTabelaPreco.setFocusable(false);
            edtValorProdutos.setFocusable(false);
            edtQuantidade.setFocusable(false);
            edtDesconto.setFocusable(false);
            edtDescontoVerba.setFocusable(false);
            edtDescontoReais.setFocusable(false);
            edtValorPrecoPago.setFocusable(false);
            edtTotal.setFocusable(false);
            edtQuantidade.setFocusable(false);
            btnBuscaProduto.setEnabled(false);
            rbReal.setClickable(false);
            rbPorcentagem.setClickable(false);
            btnSubtraiQuantidade.setVisibility(View.INVISIBLE);
            btnSomaQuantidade.setVisibility(View.INVISIBLE);
            btnConfirmar.setVisibility(View.INVISIBLE);
            btnCancelar.setVisibility(View.INVISIBLE);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            this.setTheme(R.style.Theme_MeuTemaPedido);
        } else {
             try {
                 if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                     rbPorcentagem.setChecked(false);
                     rbReal.setChecked(false);
                 }
             } catch (NullPointerException e) {

             }
            btnBuscaProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity(new Intent(ActivityProdutoPedido.this, ActivityProdutoDrawer.class).putExtra("acao", 2));
                    PedidoHelper.setWebPedidoItem(null);
                    PedidoHelper.setProduto(null);
                    PedidoHelper.setListaProdutos(null);
                    finish();
                }
            });

            edtNomeProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity(new Intent(ActivityProdutoPedido.this, ActivityProdutoDrawer.class).putExtra("acao", 2));
                    finish();
                }
            });
            //if (rbPorcentagem.isChecked())
            //    edtDescontoReais.setEnabled(false);
            rbPorcentagem.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        edtDesconto.setEnabled(true);
                        edtDescontoReais.setEnabled(false);
                        rbReal.setChecked(false);
                    }
                }
            });
            /*
            rbPorcentagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( rbPorcentagem.isChecked()) {
                        rbPorcentagem.setChecked(false);
                    } else {
                        rbPorcentagem.setChecked(true);
                    }
                }
            });

             */
            rbReal.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        edtDesconto.setEnabled(false);
                        edtDescontoReais.setEnabled(true);
                        rbPorcentagem.setChecked(false);
                    }
                }
            });
            /*
            rbReal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rbReal.isChecked()) {
                        rbReal.setChecked(false);
                    } else {
                        rbReal.setChecked(true);
                    }
                }
            });

             */
        }
        mContents = new ArrayList<>();
        if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0) {
            for (int i = 0; i < PedidoHelper.getListaProdutos().size(); i++) {
                listaProdutoFoto = produtoDAO.getListaFotos("SELECT FOTO_MINIATURA, FOTO_ARQUIVO, ID_FOTO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '"
                        + PedidoHelper.getListaProdutos().get(i).getId_produto() + "' AND ATIVO = 'S' ORDER BY FOTO_PRINCIPAL DESC  LIMIT 1");
                for (int x = 0; x < listaProdutoFoto.size(); x++) {
                    ProdutoFoto produtoFoto = new ProdutoFoto();
                    produtoFoto.setFbase64(listaProdutoFoto.get(x).getImages());
                    produtoFoto.setId_foto(listaProdutoFoto.get(x).getId_foto());
                    mContents.add(produtoFoto);
                }
            }
            if (mContents.size() <= 0) {
                ProdutoFoto produtoFoto = new ProdutoFoto();
                mContents.add(produtoFoto);
            }
        } else {
            try {
                listaProdutoFoto = produtoDAO.getListaFotos("SELECT FOTO_MINIATURA, FOTO_ARQUIVO, ID_FOTO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" + PedidoHelper.getProduto().getId_produto() + "' AND ATIVO = 'S' ORDER BY FOTO_PRINCIPAL DESC");
            } catch (NullPointerException e) {
                e.printStackTrace();
                try {
                    listaProdutoFoto = produtoDAO.getListaFotos("SELECT FOTO_MINIATURA, FOTO_ARQUIVO, ID_FOTO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" + webPedidoItem.getId_produto() + "' AND ATIVO = 'S' ORDER BY FOTO_PRINCIPAL DESC");
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    e.printStackTrace();
                }
            }
            try {
                if (listaProdutoFoto.size() <= 0) {
                    ProdutoFoto produtoFoto = new ProdutoFoto();
                    mContents.add(produtoFoto);
                } else {
                    for (int i = 0; i < listaProdutoFoto.size(); i++) {
                        ProdutoFoto produtoFoto = new ProdutoFoto();
                        produtoFoto.setFbase64(listaProdutoFoto.get(i).getImages());
                        produtoFoto.setId_foto(listaProdutoFoto.get(i).getId_foto());
                        mContents.add(produtoFoto);
                    }
                }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }

        mViewPagerAdapter = new ImagemProdutoAdapter(mContents, this);
        mViewPager.setPageTransformer(true, new ViewPagerStack());
        if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0)
            mViewPager.setOffscreenPageLimit(listaProdutoFoto.size());
        else
            mViewPager.setOffscreenPageLimit(4);

        mViewPager.setAdapter(mViewPagerAdapter);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ActivityProdutoPedido.slideTimer(), 4000, 6000);

        indicator.setViewPager(mViewPager);

        try {
            if (mContents.get(0).getId_foto().isEmpty()) {
                mViewPager.setVisibility(View.INVISIBLE);
                indicator.setVisibility(View.INVISIBLE);
                txtPesquisaProduto.setVisibility(View.INVISIBLE);
            }
        } catch (NullPointerException e) {
            mViewPager.setVisibility(View.INVISIBLE);
            indicator.setVisibility(View.INVISIBLE);
            txtPesquisaProduto.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            mViewPager.setVisibility(View.INVISIBLE);
            indicator.setVisibility(View.INVISIBLE);
            txtPesquisaProduto.setVisibility(View.INVISIBLE);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int PageScrolled = i1;
            }

            @Override
            public void onPageSelected(int i) {
                int PageSelec = i;
                mViewPagerAdapter.setImagemListada(i);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int PageScroll = state;
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = mContents.size();//;images.length;
                }
            }
        });

        btnBuscaCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PedidoHelper.getProduto() != null) {
                    if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getProduto(), true)) {
                        showFeedbackDialog("Nenhuma campanha encontrada!", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Nenhuma campanha encontrada", Toast.LENGTH_SHORT).show();
                        CampanhaHelper.setCampanhaComercialCab(null);
                        edtNomeCampanha.setText("");
                        webPedidoItem.setIdCampanha(0);
                        webPedidoItem.setNomeCampanha("");
                        btnInfoCampanha.setVisibility(View.GONE);
                    }
                } else if (PedidoHelper.getWebPedidoItem() != null) {
                    if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem(), true)) {
                        showFeedbackDialog("Nenhuma campanha encontrada!!", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Nenhuma campanha encontrada", Toast.LENGTH_SHORT).show();
                        CampanhaHelper.setCampanhaComercialCab(null);
                        edtNomeCampanha.setText("");
                        btnInfoCampanha.setVisibility(View.GONE);
                        webPedidoItem.setIdCampanha(0);
                        webPedidoItem.setNomeCampanha("");
                    }
                } else {
                    if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0)
                        showFeedbackDialog("Não é possivel lançar campanhas na multi-seleção de produtos", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Não é possivel lançar campanhas na multi-seleção de produtos", Toast.LENGTH_SHORT).show();
                    else
                        showFeedbackDialog("Nenhum produto localizado", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Nenhum produto localizado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtNomeCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PedidoHelper.getProduto() != null)
                    if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getProduto(), true)) {
                        showFeedbackDialog("Nenhuma campanha encontrada!!!", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Nenhuma campanha encontrada", Toast.LENGTH_SHORT).show();
                        CampanhaHelper.setCampanhaComercialCab(null);
                        edtNomeCampanha.setText("");
                        btnInfoCampanha.setVisibility(View.GONE);
                        webPedidoItem.setIdCampanha(0);
                        webPedidoItem.setNomeCampanha("");
                    }
                else if (PedidoHelper.getWebPedidoItem() != null)
                    if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem(), true)) {
                        showFeedbackDialog("Nenhuma campanha encontrada!!!!", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Nenhuma campanha encontrada", Toast.LENGTH_SHORT).show();
                        CampanhaHelper.setCampanhaComercialCab(null);
                        edtNomeCampanha.setText("");
                        btnInfoCampanha.setVisibility(View.GONE);
                        webPedidoItem.setIdCampanha(0);
                        webPedidoItem.setNomeCampanha("");
                    }
                else
                    if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0)
                        showFeedbackDialog("Não é possivel lançar campanhas na multi-seleção de produtos!!", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Não é possivel lançar campanhas na multi-seleção de produtos", Toast.LENGTH_SHORT).show();
                    else
                        showFeedbackDialog("Nenhum produto localizado", true);
                        //Toast.makeText(ActivityProdutoPedido.this, "Nenhum produto localizado", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S")) {
                if (PedidoHelper.getWebPedidoItem() != null && PedidoHelper.getWebPedidoItem().getIdCampanha() > 0)
                    CampanhaHelper.setCampanhaComercialCab(campanhaComercialCabDAO.getLista(webPedidoItem.getIdCampanha()));
            } else {
                ViewGroup.LayoutParams params = lymainCampanha.getLayoutParams();
                params.height = 0;
                lymainCampanha.setLayoutParams(params);
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }

        listaSku = new ArrayList<>();
        String sql;

    }

    private void getConfiguracaoEmpresa() {
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
    }

    private void showHideLayout(boolean b) {
        ViewGroup.LayoutParams paramslyProduto  = lyProduto.getLayoutParams();
        ViewGroup.LayoutParams paramslyCampanha = lymainCampanha.getLayoutParams();
        ViewGroup.LayoutParams paramslySKU      = lymainSku.getLayoutParams();
        ViewGroup.LayoutParams paramslyPromocao = cdPromocao.getLayoutParams();
        if (b) {
            paramslyProduto.height  = ViewGroup.LayoutParams.WRAP_CONTENT;
            paramslyCampanha.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            paramslySKU.height      = ViewGroup.LayoutParams.WRAP_CONTENT;
            paramslyPromocao.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            paramslyProduto.height  = 0;
            paramslyCampanha.height = 0;
            paramslySKU.height      = 0;
            paramslyPromocao.height = 0;
        }
        if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("N")) {
            paramslyCampanha.height = 0;
        }
        cdPromocao.setLayoutParams(paramslyPromocao);
        lyProduto.setLayoutParams(paramslyProduto);
        lymainCampanha.setLayoutParams(paramslyCampanha);
        lymainSku.setLayoutParams(paramslyProduto);
    }

    @Override
    protected void onResume() {
        try {
            if (PedidoHelper.getWebPedido().getTrabalha_com_dscto_reais().equalsIgnoreCase("N"))
                hideLayoutDescontoReais();
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
        hideLayoutIcmsST();
        try {
            if( configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                showLayoutIcmsST();
            } else {
                hideLayoutIcmsST();
            }

        } catch ( NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("N")) {
                hideLayoutDescontoVerba();
            }
        } catch ( NullPointerException e) {
            configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            try {
                if (configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("N")) {
                    hideLayoutDescontoVerba();
                }
            } catch ( NullPointerException ex) {

            } catch ( Exception ex) {

            }
        } catch ( Exception e){

        }
        if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0) {
            hideLayoutSku();
            hideLayoutDescontoVerba();
            List<CampanhaComercialCab> listaCampanha = new ArrayList<>();
            Float valorUnitario = 0.f;
            for (Produto produto : PedidoHelper.getListaProdutos())
                valorUnitario += Float.parseFloat(produto.getVenda_preco());

            BigDecimal bValorUnitario = new BigDecimal(valorUnitario).setScale(2, RoundingMode.HALF_EVEN);
            webPedidoItem = new WebPedidoItens();
            webPedidoItem.setNome_produto("VARIOS PRODUTOS SELECIONADOS");
            edtNomeProduto.setText("VARIOS PRODUTOS SELECIONADOS");
            webPedidoItem.setVenda_preco(valorUnitario.toString());
            edtTabelaPreco.setText(bValorUnitario.toString());
            cdPromocao.setVisibility(View.VISIBLE);
            txtPromocao.setText(PedidoHelper.getListaProdutos().size() + " PRODUTO(S) SELECIONADO(S)");
            rbReal.setEnabled(false);
            edtDescontoReais.setEnabled(false);
            WebPedido w = PedidoHelper.getWebPedido();
            CondicoesPagamento p = PedidoHelper.getCondicoesPagamento();
            Cliente c = ClienteHelper.getCliente();
            if ( PedidoHelper.getCondicoesPagamento() == null) {
                CondicoesPagamentoDAO condicoesPagamentoDAO = new CondicoesPagamentoDAO(db);
                PedidoHelper.setCondicoesPagamento(condicoesPagamentoDAO.getCondicao("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + PedidoHelper.getWebPedido().getId_condicao_pagamento()));
            }
             p = PedidoHelper.getCondicoesPagamento();

            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6") && PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("N")) {
                rbPorcentagem.setText("D. max(" + desconto_perc + "%)");
                edtDesconto.setText(String.valueOf(desconto_perc));
                if (desconto_perc <= 0.f)
                    //Sem desconto
                    showMultiSelecao(0.0f);

            } else if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4") && PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("N")) {
                rbPorcentagem.setText("D. max(" + desconto_perc + "%)");
                edtDesconto.setText(String.valueOf(desconto_perc));
                if (desconto_perc <= 0.f)
                    showMultiSelecao(0.0f);
            } else {
                int qtde_desconto_permitido = 0;
                int qtde_desconto_bloqueado = 0;
                int qtde_promocao = 0;
                int qtde_campanha = 0;
                String campanhas = "";
                for (Produto produto : PedidoHelper.getListaProdutos()) {
                    valorUnitario += Float.parseFloat(produto.getVenda_preco());
                    if (!produto.getProduto_tercerizacao().equals("S") && !produto.getProduto_materia_prima().equals("S")) {
                        promocaoRetorno = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro_servidor(), produto.getId_produto(), ActivityProdutoPedido.this);
                        try {
                            if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                                qtde_promocao++;
                                qtde_desconto_bloqueado++;
                            } else {
                                qtde_desconto_permitido++;
                            }
                        } catch ( NullPointerException|NumberFormatException e) {
                            if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f) {
                                qtde_promocao++;
                                qtde_desconto_bloqueado++;
                            } else {
                                qtde_desconto_permitido++;
                            }
                        }
                    } else {
                        qtde_desconto_bloqueado++;
                    }
                    if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S")) {
                        if (verificaCampanhaLista(ClienteHelper.getCliente(), produto)) {
                            listaCampanha = getCampanhaLista(ClienteHelper.getCliente(), produto);
                            for (int i = 0; i < listaCampanha.size(); i++)
                                campanhas += listaCampanha.get(i).getNomeCampanha();

                            qtde_campanha++;
                            qtde_desconto_permitido--;
                        }
                    }
                }
                if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S") && !campanhas.isEmpty())
                    showLayoutCampanha();
                else
                    hideLayoutCampanha();
                if ( ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2") ){
                    rbPorcentagem.setText("Desc. tabela(" + String.valueOf(desconto_perc) + "%)");
                } else {
                   rbPorcentagem.setText("Desc. max(" + String.valueOf(desconto_perc) + "%)");
                }
                try {
                    //if ( ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2") ){
                    if (configuracao.getTrabalha_com_desc_auto_app().equalsIgnoreCase("N")) {

                        if ( webPedidoItem.getValor_desconto_per() == null || webPedidoItem.getValor_desconto_per().isEmpty()) {
                            edtDesconto.setText("0.00");
                        } else {
                            edtDesconto.setText(String.valueOf(webPedidoItem.getValor_desconto_per()));
                        }
                    } else {
                        edtDesconto.setText(tabelaPrecoItem.getPerc_desc_final());
                    }
                } catch (NullPointerException e) {
                    edtDesconto.setText(String.valueOf(desconto_perc));
                } catch (Exception e) {
                    edtDesconto.setText(String.valueOf(desconto_perc));
                }
                showMultiSelecao(qtde_desconto_bloqueado, qtde_promocao, qtde_desconto_permitido, qtde_campanha, campanhas);
            }
        } else {
            setWebPedidoItem();
            if (configuracao.getTrabalha_com_unidade_manut_estoque().equalsIgnoreCase("S")) {
                try {
                    listaSku = produtoSkuDAO.getLista(webPedidoItem.getId_produto());
                } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                if (listaSku.size() > 0) {
                    showLayoutSku();
                    adapterSku = new ArrayAdapter<>(ActivityProdutoPedido.this, android.R.layout.simple_list_item_activated_1, listaSku);
                    spnSku.setAdapter(adapterSku);
                    try {
                        if (webPedidoItem.getId_sku() > 0) {
                            for (int z = 0; z <= listaSku.size(); z++) {
                                if (listaSku.get(z).getId_sku() == webPedidoItem.getId_sku()) {
                                    spnSku.setSelection(z);
                                    break;
                                }
                            }

                        } else {
                            if (listaSku.size() == 1) {
                                spnSku.setSelection(0);
                            }
                        }
                    } catch (NullPointerException | NumberFormatException e) {
                        spnSku.setSelection(0);
                    }
                    //spnSku.setSelection(0);
                    spnSku.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            edtTabelaPreco.setText(MascaraUtil.mascaraVirgula(listaSku.get(i).getPreco()));
                            try {
                                webPedidoItem.setVenda_preco(listaSku.get(i).getPreco());
                                webPedidoItem.setId_sku(listaSku.get(i).getId_sku());
                                webPedidoItem.setNome_sku(listaSku.get(i).getNome());
                                try {
                                    if (webPedidoItem.getProduto_preco_editado() > 0.0f) {
                                        if (webPedidoItem.getProduto_preco_editado() != Float.parseFloat(listaSku.get(i).getPreco())) {
                                            webPedidoItem.setVenda_preco(String.valueOf(webPedidoItem.getProduto_preco_editado()));
                                            edtTabelaPreco.setText(MascaraUtil.mascaraVirgula(webPedidoItem.getProduto_preco_editado()));
                                        }
                                    }
                                } catch (NumberFormatException | NullPointerException e) {
                                    e.printStackTrace();
                                }

                                if (!edtQuantidade.getText().toString().isEmpty()) {
                                    calculaDesconto();
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            edtTabelaPreco.selectAll();
                            edtTabelaPreco.requestFocus();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else {
                    hideLayoutSku();
                }
            } else {
                hideLayoutSku();
            }
            try {
                if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S")) {
                    if ((getIntent().getIntExtra("pedido", 0) != 1 && verificaCampanha) ||
                            (getIntent().getIntExtra("pedido", 0) == 1 && getIntent().getIntExtra("vizualizacao", 0) != 1 && verificaCampanha)) {
                        if (getIntent().getIntExtra("pedido", 0) != 1 && verificaCampanha) {
                            verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem(), true);
                            //if (verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem(), true)) {
                                //Toast.makeText(ProdutoPedidoActivity.this, "Campanha(s) ativa(s) para este item encontrada(s)", Toast.LENGTH_SHORT).show();
                            //}
                        } else if (getIntent().getIntExtra("pedido", 0) == 1 && getIntent().getIntExtra("vizualizacao", 0) != 1 && verificaCampanha) {
                            verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem(), false);
                            //if (verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem(), false)) {
                                //Toast.makeText(ProdutoPedidoActivity.this, "Campanha(s) ativa(s) para este item encontrada(s)", Toast.LENGTH_SHORT).show();
                            //}
                        }
                        verificaCampanha = false;
                        if (PedidoHelper.getWebPedidoItem() != null && PedidoHelper.getWebPedidoItem().getIdCampanha() > 0)
                            CampanhaHelper.setCampanhaComercialCab(campanhaComercialCabDAO.getLista(webPedidoItem.getIdCampanha()));

                    }
                    if (CampanhaHelper.getCampanhaComercialCab() != null) {
                        showLayoutCampanha();
                        webPedidoItem.setIdCampanha(CampanhaHelper.getCampanhaComercialCab().getIdCampanha());
                        webPedidoItem.setNomeCampanha(CampanhaHelper.getCampanhaComercialCab().getNomeCampanha());
                        //if (getIntent().getIntExtra("vizualizacao", 0) != 1) {
                        //}
                    } else {
                        hideLayoutCampanha();
                        webPedidoItem.setIdCampanha(0);
                        webPedidoItem.setNomeCampanha("");
                    }
                }
            } catch (NullPointerException e) {
            } catch (Exception e) {
            }
            try {
                if (PedidoHelper.getCondicoesPagamento() == null) {
                    PedidoHelper.setCondicoesPagamento(new CondicoesPagamentoDAO(db).getCondicao("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + PedidoHelper.getWebPedido().getId_condicao_pagamento()));
                }
            } catch (NullPointerException ignored) {
            }
            CondicoesPagamento p = PedidoHelper.getCondicoesPagamento();
            //if ( (webPedidoItem.getProduto_tercerizacao() != null && !webPedidoItem.getProduto_tercerizacao().equals("S") ) && ( webPedidoItem.getProduto_materia_prima() != null && !webPedidoItem.getProduto_materia_prima().equals("S") ) /*&& PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S") */) {
                try {
                    if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6") && PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("N")) {
                        hideLayoutDescontoVerba();
                        showPrazoSemDesconto();
                    } else if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4") && PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("N")) {
                        hideLayoutDescontoVerba();
                        showPrazoSemDesconto();
                    } else {
                        if ( ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")
                            && ClienteHelper.getCliente().getId_cadastro() > 0 /*&& ClienteHelper.getCliente().getId_cadastro_servidor() > 0*/){
                            //&& PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S")
                            promocaoRetorno = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro_servidor(), webPedidoItem.getId_produto(), ActivityProdutoPedido.this);
                            try {
                                if (Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()) >= 0.0f) {
                                    if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                                        showProdutoPromocao();
                                        hideLayoutDescontoVerba();
                                    } else {
                                        if (Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()) >= 0.0f) {
                                            if (configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S"))
                                                showClienteCategoriaEspecial();
                                        } else {
                                            hideLayoutDescontoVerba();
                                            showProdutoSempromocao();
                                        }
                                    }
                                } else {
                                    hideLayoutDescontoVerba();
                                    if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f)
                                        showProdutoPromocao();
                                    else
                                        showProdutoSempromocao();
                                }
                            } catch (NullPointerException | NumberFormatException e) {
                                hideLayoutDescontoVerba();
                                if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f)
                                    showProdutoPromocao();
                                else
                                    showProdutoSempromocao();
                            }

                        } else {
                            hideLayoutDescontoVerba();
                            promocaoRetorno = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro_servidor(), webPedidoItem.getId_produto(), ActivityProdutoPedido.this);
                            try {
                                if (Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()) >= 0.0f) {
                                    if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()))
                                        showProdutoPromocao();
                                    else
                                        showProdutoSempromocao();
                                } else {
                                    if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f)
                                        showProdutoPromocao();
                                    else
                                        showProdutoSempromocao();
                                }
                            } catch (NullPointerException | NumberFormatException e) {
                                if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f)
                                    showProdutoPromocao();
                                else
                                    showProdutoSempromocao();
                            }
                        }
                    }
                } catch ( NullPointerException e ) {
                    hideLayoutDescontoVerba();
                    showPrazoSemDesconto();
                }
            //}else {
            //    hideLayoutDescontoVerba();
            //    showProdutoSemDesconto();
            //}


            edtNomeProduto.setText(webPedidoItem.getNome_produto());

            edtTabelaPreco.setText(MascaraUtil.mascaraVirgula(webPedidoItem.getVenda_preco()));
            try {
                if (!configuracao.getTrabalha_com_verba_pedido().isEmpty() && configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S")) {
                    edtDescontoVerba.setText(webPedidoItem.getPerc_desconto_adic());
                }
            } catch ( NullPointerException e ) {
            } catch ( Exception e) {
            }

            try {
                if (edtTabelaPreco.getText().toString().isEmpty())
                    edtNomeProduto.setTextColor(Color.RED);
                else if (Float.parseFloat(webPedidoItem.getVenda_preco()) <= 0.00)
                    edtNomeProduto.setTextColor(Color.RED);
                else
                    edtNomeProduto.setTextColor(Color.parseColor("#ffffff"));

                if ( configuracao.getLayout_venda_erp().equalsIgnoreCase("1"))
                    edtNomeProduto.setTextColor(Color.parseColor("#ffffff"));

            } catch (NullPointerException | NumberFormatException e) {
                if (configuracao.getLayout_venda_erp().equalsIgnoreCase("1"))
                    edtNomeProduto.setTextColor(Color.parseColor("#ffffff"));
                else
                    edtNomeProduto.setTextColor(Color.RED);

            }
            edtValorPrecoPago.setFocusable(false);
            //edtValorPrecoPago.setEnabled(true);
            try {
                if (webPedidoItem.getProduto_edita_valor().equalsIgnoreCase("S")) {
                    edtTabelaPreco.setFocusable(true);
                    edtTabelaPreco.setEnabled(true);
                    edtTabelaPreco.requestFocus();
                    //Adicionei aqui p ver se volta a funcionar
                    rbPorcentagem.setChecked(true);
                    //edtDesconto.setFocusable(false);
                    edtTabelaPreco.addTextChangedListener(textWatcherPreco);
                    rbReal.setChecked(false);
                    rbReal.setEnabled(false);
                    edtDescontoReais.setEnabled(true);
                    edtDescontoReais.setFocusable(false);
                } else {
                    //Era assim
                    //edtTabelaPreco.setFocusable(false);
                    //edtTabelaPreco.setEnabled(true);
                    //Agora fiocu assim  23/03/2026
                    if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
                        edtTabelaPreco.setFocusable(true);
                        edtTabelaPreco.setEnabled(true);
                        edtTabelaPreco.requestFocus();
                        rbPorcentagem.setChecked(true);
                        //edtDesconto.setFocusable(false);
                        edtTabelaPreco.addTextChangedListener(textWatcherPreco);
                        rbReal.setChecked(false);
                        rbReal.setEnabled(false);
                        edtDescontoReais.setEnabled(true);
                        edtDescontoReais.setFocusable(false);
                    } else {
                        edtTabelaPreco.setFocusable(false);
                        edtTabelaPreco.setEnabled(true);
                    }
                }
            } catch ( NullPointerException e) {
                edtTabelaPreco.setFocusable(false);
                edtTabelaPreco.setEnabled(true);
            } catch ( Exception e) {
                edtTabelaPreco.setFocusable(false);
                edtTabelaPreco.setEnabled(true);


            }

            calculaDesconto();
        }
        //edtQuantidade.requestFocus();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("vizualizacao", 0) != 1) {
            getMenuInflater().inflate(R.menu.menu_produto_pedido, menu);
            salvar_produto = menu.findItem(R.id.menu_salvar_produto);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PedidoHelper.setWebPedidoItem(null);
                PedidoHelper.setProduto(null);
                PedidoHelper.setListaProdutos(null);
                finish();
                break;
            case R.id.menu_salvar_produto:
                confirmar();
                //salvarProduto();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarProduto() {
        if (!PedidoHelper.isPedido1Valido()) {
            showAnimationFeedbackDialog("A sessão de venda foi interrompida pelo sistema Android para liberar memória (geralmente devido à inatividade prolongada ou ao excesso de aplicativos abertos simultaneamente). Para sua segurança, você será redirecionado ao menu principal. Caso este pedido já possua itens lançados, você poderá continuar a edição através da opção 'Pedidos Pendentes'.", false, () -> {
                Intent intent = new Intent(this, ActivityPrincipal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
            return;
        }
        Float descontoPedido;
        try {
            if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()))
                descontoPedido = promocaoRetorno.getValorDesconto();
            else
                try {
                    descontoPedido = desconto_perc;
                } catch (NullPointerException e) {
                    descontoPedido = 0.0f;
                }
        } catch (NullPointerException e) {
            if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > desconto_perc)
                descontoPedido = promocaoRetorno.getValorDesconto();
            else
                try {
                    //descontoPedido = Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());
                    descontoPedido = desconto_perc;
                } catch (NullPointerException ex) {
                    descontoPedido = 0.0f;
                }

        }
        if (edtDesconto.getText().toString().trim().isEmpty())
            edtDesconto.setText("0.00");
        if (edtDescontoReais.getText().toString().trim().isEmpty())
            edtDescontoReais.setText("0.00");
        try {
            if (!configuracao.getTrabalha_com_verba_pedido().isEmpty() || configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S")) {
                if (edtDescontoVerba.getText().toString().isEmpty()) {
                    edtDescontoVerba.setText("0.00");
                } else {
                    if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                        if (Float.parseFloat(edtDescontoVerba.getText().toString()) > Float.parseFloat("5.00")) {
                            edtDescontoVerba.setBackgroundResource(R.drawable.borda_edittext_erro);
                            edtDescontoVerba.selectAll();
                            showFeedbackDialog("Percentual do desconto informado acima do máximo permitido conforme definido na tabela do cliente!", true);
                            //Toast.makeText(ActivityProdutoPedido.this, "Percentual do desconto informado acima do máximo permitido conforme definido na tabela do cliente!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
            }
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        Produto prod = PedidoHelper.getProduto();
        /*
        if (UsuarioHelper.getUsuario().getUsuario_edita_preco_app().equalsIgnoreCase("S")) {
            if ( !permiteDesconto()) {
                showFeedbackDialog("Valor do produto nao pode ser informado abaixo do preço de tabela!", false);
                return;
            }
        }
        
         */

        if (webPedidoItem != null) {
            if (edtQuantidade.getText().toString().trim().isEmpty()) {
                showFeedbackDialog("Informe a quantidade", true);
                //Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Float.parseFloat(edtQuantidade.getText().toString()) <= 0) {
                showFeedbackDialog("A quantidade não pode ser zero!", true);
                //Toast.makeText(this, "A quantidade não pode ser zero!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!edtTabelaPreco.getText().toString().isEmpty()) {
                if (Float.parseFloat(webPedidoItem.getVenda_preco()) > 0.00 ) {
                    if (Float.parseFloat(edtDesconto.getText().toString()) <= descontoPedido) {
                        if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0) {
                             BigDecimal bdesconto = null;
                            BigDecimal btotalBruto = null;
                            BigDecimal bValorIcmsST = null;
                            PedidoHelper pedidoHelper = new PedidoHelper();
                            Intent intent = new Intent();
                            for (Produto produto : PedidoHelper.getListaProdutos()) {
                                try {
                                    if (empresaParametro.getQtde_maior_saldo_ped_app().equalsIgnoreCase("N")) {
                                        if (Float.parseFloat(edtQuantidade.getText().toString()) > produto.getSaldo_estoque()) {
                                            edtQuantidade.requestFocus();
                                            showFeedbackDialog("Gravação dos itens selecionados cancelada!\nA quantidade não pode ser maior que o saldo em estoque disponivel para o produto " + produto.getNome_produto(), true);
                                            //Toast.makeText(this, "Gravação dos itens selecionados cancelada!\nA quantidade não pode ser maior que o saldo em estoque disponivel para o produto " + produto.getNome_produto(), Toast.LENGTH_LONG).show();
                                            onBackPressed();
                                            return;
                                        }
                                    }
                                } catch (NullPointerException e) {
                                }
                            }
                            for (Produto produto : PedidoHelper.getListaProdutos()) {
                                valorIcmsST = 0.00f;
                                WebPedidoItens produtoSelecionado = new WebPedidoItens(produto);
                                produtoSelecionado.setProdutoAtivo(true);
                                if (!produtoSelecionado.getProduto_tercerizacao().equalsIgnoreCase("S") && !produtoSelecionado.getProduto_materia_prima().equalsIgnoreCase("S")) {
                                    produtoSelecionado.setValor_unitario(Float.valueOf(produto.getVenda_preco()));
                                    produtoSelecionado.setQuantidade(quantidade.toString());
                                    produtoSelecionado.setValor_total(String.valueOf((Float.parseFloat(produtoSelecionado.getVenda_preco()) * quantidade) - ((Float.parseFloat(edtDesconto.getText().toString()) / 100) * (Float.parseFloat(produto.getVenda_preco()) * quantidade))));
                                    produtoSelecionado.setValor_bruto(String.valueOf(Float.valueOf(produtoSelecionado.getVenda_preco()) * Float.parseFloat(quantidade.toString())));
                                    produtoSelecionado.setValor_desconto_per(edtDesconto.getText().toString());
                                    produtoSelecionado.setValor_desconto_real("0.00");
                                    bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produtoSelecionado.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                    desconto = Float.parseFloat(String.valueOf(bdesconto));
                                    produtoSelecionado.setValor_desconto_real(String.valueOf(desconto));
                                    btotalBruto = new BigDecimal((quantidade * Float.parseFloat(produtoSelecionado.getVenda_preco()))).setScale(2, RoundingMode.HALF_EVEN);
                                    totalProdutoBruto = Float.parseFloat(String.valueOf(btotalBruto));//(quantidade * Float.parseFloat(produto.getVenda_preco()));
                                    produtoSelecionado.setValor_bruto(String.valueOf(totalProdutoBruto));
                                    if (!edtDesconto.getText().toString().isEmpty()) {
                                        bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(3, RoundingMode.HALF_EVEN);
                                        int tamanho = String.valueOf(bdesconto).length();
                                        if (String.valueOf(bdesconto).substring(tamanho - 1).equalsIgnoreCase("5")) {
                                            if (((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.DOWN);
                                            } else {
                                                bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.UP);
                                            }
                                        } else {
                                            bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                        }
                                        //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                        desconto = Float.parseFloat(String.valueOf(bdesconto));
                                        produtoSelecionado.setValor_desconto_real(String.valueOf(bdesconto));
                                        try {
                                            if (configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                                                if (produto.getProduto_suj_icms_st().equalsIgnoreCase("S")) {
                                                    if (produto.getProduto_st_aliquota() > 0.00f) {
                                                        bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (Float.parseFloat(produto.getVenda_preco()) - desconto)) / 100).setScale(4, RoundingMode.HALF_EVEN);
                                                        tamanho = String.valueOf(bValorIcmsST).length();
                                                        if ((String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1)).trim().equalsIgnoreCase("5")) {
                                                            if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                                bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.UP);
                                                            } else {
                                                                bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.DOWN /*RoundingMode.HALF_EVEN*/);
                                                            }
                                                        } else {
                                                            bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                        }

                                                        valorIcmsST = Float.parseFloat(String.valueOf(bValorIcmsST));
                                                        produtoSelecionado.setIcms_st_percentual(produto.getProduto_st_aliquota());
                                                        produtoSelecionado.setIcms_st_valor(String.valueOf(bValorIcmsST));
                                                        produtoSelecionado.setIcms_st_calcula("S");
                                                        produtoSelecionado.setValor_total(String.valueOf((totalProdutoBruto + valorIcmsST) - desconto));
                                                        produtoSelecionado.setValor_bruto(String.valueOf((totalProdutoBruto + valorIcmsST)));
                                                        totalICMS += Float.parseFloat(String.valueOf(bValorIcmsST));
                                                    }
                                                }
                                            }
                                        } catch (NullPointerException | NumberFormatException e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        try {
                                            if (configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                                                if (produto.getProduto_suj_icms_st().equalsIgnoreCase("S")) {
                                                    if (produto.getProduto_st_aliquota() > 0.00f) {
                                                        bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoBruto)) / 100).setScale(4, RoundingMode.HALF_EVEN);
                                                        int tamanho = String.valueOf(bValorIcmsST).length();
                                                        if (String.valueOf(bValorIcmsST).substring(tamanho - 1).equalsIgnoreCase("5")) {
                                                            if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                                bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.UP);
                                                            } else {
                                                                bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.DOWN);
                                                            }
                                                        } else {
                                                            bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * ((totalProdutoBruto))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                        }
                                                        valorIcmsST = Float.parseFloat(String.valueOf(bValorIcmsST));
                                                        produtoSelecionado.setIcms_st_percentual(produto.getProduto_st_aliquota());
                                                        produtoSelecionado.setIcms_st_valor(String.valueOf(bValorIcmsST));
                                                        produtoSelecionado.setIcms_st_calcula("S");
                                                        produtoSelecionado.setValor_bruto(String.valueOf((totalProdutoBruto + valorIcmsST)));
                                                        totalICMS += Float.parseFloat(String.valueOf(bValorIcmsST));
                                                    }
                                                }
                                            }
                                        } catch (NullPointerException | NumberFormatException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } else {
                                    //float zeradesconto = 0.0f;
                                    produtoSelecionado.setValor_unitario(Float.valueOf(produto.getVenda_preco()));
                                    produtoSelecionado.setQuantidade(quantidade.toString());
                                    produtoSelecionado.setValor_total(String.valueOf((Float.parseFloat(produtoSelecionado.getVenda_preco()) * quantidade)));
                                    produtoSelecionado.setValor_bruto(String.valueOf(Float.valueOf(produtoSelecionado.getVenda_preco()) * Float.parseFloat(quantidade.toString())));
                                    produtoSelecionado.setValor_desconto_per("0.00");
                                    produtoSelecionado.setValor_desconto_real("0.00");
                                    //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produtoSelecionado.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                    //desconto = Float.parseFloat(String.valueOf(bdesconto));
                                    //produtoSelecionado.setValor_desconto_real(String.valueOf(desconto));
                                    btotalBruto = new BigDecimal((quantidade * Float.parseFloat(produtoSelecionado.getVenda_preco()))).setScale(2, RoundingMode.HALF_EVEN);
                                    totalProdutoBruto = Float.parseFloat(String.valueOf(btotalBruto));//(quantidade * Float.parseFloat(produto.getVenda_preco()));
                                }
                                produtoSelecionado.setValor_total(String.valueOf(totalProdutoBruto - desconto));
                                produtoSelecionado.setValor_total(String.valueOf((totalProdutoBruto + valorIcmsST) - desconto));
                                produtoSelecionado.setId_grupo(produto.getId_grupo() == null || produto.getId_grupo().isEmpty() ? "0" : produto.getId_grupo());
                                produtoSelecionado.setId_sub_grupo(produto.getId_sub_grupo() == null || produto.getId_sub_grupo() .isEmpty() ? "0" : produto.getId_sub_grupo());
                                if (rbPorcentagem.isChecked())
                                    produtoSelecionado.setTipoDesconto("P");
                                else if (rbReal.isChecked())
                                    produtoSelecionado.setTipoDesconto("R");
                                if (!existeProduto(produtoSelecionado, true))
                                    if (!pedidoHelper.inserirProduto(produtoSelecionado)) {
                                        //showMsgAlerta("Falha ao gravar o produto " + produtoSelecionado.getNome_produto());
                                        showFeedbackDialog("Falha ao gravar o produto ", false);
                                        //Toast.makeText(this, "Falha ao gravar o produto " + produtoSelecionado.getNome_produto(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                //pedidoHelper.salvaPedidoParcial();
                            }
                            if (Float.parseFloat(String.valueOf(btotalBruto)) <= 0.00) {
                                showFeedbackDialog("O total do produto não pode ser zero!", true);
                                //Toast.makeText(this, "O total do produto não pode ser zero!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if ( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) > Float.parseFloat(webPedidoItem.getVenda_preco())) {
                                webPedidoItem.setVenda_preco(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", "."));
                            }
                            pedidoHelper.salvaPedidoParcial();
                            //intent.putStringArrayListExtra("listaSelecionados", PedidoHelper.getListaProdutos());
                            setResult(RESULT_OK, intent);
                            PedidoHelper.setListaProdutos(null);
                            //listaProdutosSelecionados.clear();
                        } else {
                            if (totalProdutoBruto <= 0.00) {
                                showFeedbackDialog("O total do produto não pode ser igual a zero!", true);
                                //Toast.makeText(this, "O total do produto não pode ser igual a zero!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                if (empresaParametro.getQtde_maior_saldo_ped_app().equalsIgnoreCase("N")) {
                                    try {
                                        if (Float.parseFloat(edtQuantidade.getText().toString()) > PedidoHelper.getProduto().getSaldo_estoque()) {
                                            edtQuantidade.requestFocus();
                                            showFeedbackDialog("A quantidade não pode ser maior que o saldo em estoque disponivel!!\n", true);
                                            //showMsgAlerta("A quantidade não pode ser maior que o saldo em estoque disponivel!!\n");
                                            //Toast.makeText(this, "A quantidade não pode ser maior que o saldo em estoque disponivel!!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (NullPointerException e) {
                                        if (Float.parseFloat(edtQuantidade.getText().toString()) > webPedidoItem.getSaldo_estoque()) {
                                            edtQuantidade.requestFocus();
                                            showFeedbackDialog("A quantidade não pode ser maior que o saldo em estoque disponivel!!\n", true);
                                            //showMsgAlerta("A quantidade não pode ser maior que o saldo em estoque disponivel!!\n");
                                            return;
                                        }
                                    }
                                }
                            } catch (NullPointerException e) {

                            }
                            //Produto produtox = PedidoHelper.getProduto();
                            Intent intent = new Intent();
                            webPedidoItem.setProdutoAtivo(true);
                            webPedidoItem.setValor_unitario(Float.parseFloat(webPedidoItem.getVenda_preco()));
                            if ( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) > Float.parseFloat(webPedidoItem.getVenda_preco())) {
                                webPedidoItem.setValor_unitario(Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")));
                            }

                            webPedidoItem.setQuantidade(quantidade.toString());
                            webPedidoItem.setValor_total(String.valueOf((totalProdutoBruto + valorIcmsST) - (Float.parseFloat(edtDescontoReais.getText().toString()) + descontoVerba)));
                            webPedidoItem.setValor_bruto(String.valueOf((totalProdutoBruto + valorIcmsST)));
                            webPedidoItem.setValor_desconto_per(edtDesconto.getText().toString());
                            webPedidoItem.setValor_desconto_real(edtDescontoReais.getText().toString());
                            webPedidoItem.setPerc_desconto_adic(edtDescontoVerba.getText().toString());
                            webPedidoItem.setValor_desconto_adic(Float.toString(descontoVerba));
                            webPedidoItem.setIcms_st_valor(String.valueOf(valorIcmsST));
                            webPedidoItem.setSaldo_estoque(null);
                            if (rbPorcentagem.isChecked())
                                webPedidoItem.setTipoDesconto("P");
                            else if (rbReal.isChecked())
                                webPedidoItem.setTipoDesconto("R");
                            try {
                                if (webPedidoItem.getProduto_edita_valor().equalsIgnoreCase("S")) {
                                    if (Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) >= Float.parseFloat(webPedidoItem.getVenda_preco())) {
                                        webPedidoItem.setProduto_preco_editado(Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")));
                                    } else {
                                        webPedidoItem.setProduto_preco_editado(Float.parseFloat(webPedidoItem.getVenda_preco()));
                                    }
                                }
                            } catch (NullPointerException e) {
                            }
                            if (getIntent().getIntExtra("pedido", 0) != 1) {
                                PedidoHelper pedidoHelper = new PedidoHelper();
                                if (!existeProduto(webPedidoItem, false)) {
                                    try {
                                        webPedidoItem.setId_grupo(PedidoHelper.getProduto().getId_grupo() == null || PedidoHelper.getProduto().getId_grupo().isEmpty() ? "0" : PedidoHelper.getProduto().getId_grupo());
                                        webPedidoItem.setId_sub_grupo(PedidoHelper.getProduto().getId_sub_grupo() == null || PedidoHelper.getProduto().getId_sub_grupo().isEmpty() ? "0" : PedidoHelper.getProduto().getId_sub_grupo());
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (pedidoHelper.inserirProduto(webPedidoItem)) {
                                            pedidoHelper.salvaPedidoParcial();
                                            setResult(RESULT_OK, intent);
                                        } else {
                                            showFeedbackDialog("Falha ao gravar o produto " + webPedidoItem.getNome_produto() + "!", true);
                                            //showMsgAlerta("Falha ao gravar o produto " + webPedidoItem.getNome_produto());
                                            return;
                                        }
                                    } catch (NullPointerException e) {
                                        showFeedbackDialog("Falha ao gravar o produto " + webPedidoItem.getNome_produto() + "!!", true);
                                        //showMsgAlerta("Falha ao gravar o produto " + webPedidoItem.getNome_produto());
                                        return;
                                    }
                                } else {
                                    if (pedidoHelper.alterarProduto(webPedidoItem, getIntent().getIntExtra("position", 0))) {
                                        pedidoHelper.salvaPedidoParcial();
                                        setResult(RESULT_OK, intent);
                                    } else {
                                        showFeedbackDialog("Falha ao gravar o produto " + webPedidoItem.getNome_produto() + "!!!", true);
                                        //showMsgAlerta("Falha ao atualizar o produto " + webPedidoItem.getNome_produto());
                                        return;
                                    }
                                }
                            } else {
                                PedidoHelper pedidoHelper = new PedidoHelper();
                                if (pedidoHelper.alterarProduto(webPedidoItem, getIntent().getIntExtra("position", 0))) {
                                    pedidoHelper.salvaPedidoParcial();
                                    setResult(RESULT_OK, intent);
                                } else {
                                    //showMsgAlerta("Falha ao atualizar o produto " + webPedidoItem.getNome_produto());
                                    showFeedbackDialog("Falha ao atualizar o produto " + webPedidoItem.getNome_produto(), false);
                                    //Toast.makeText(this, "Falha ao gravar/atualizar o produto " + webPedidoItem.getNome_produto(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                        }
                        //Alterado em 22/10/2021
                        verificaCampanha = true;
                        PedidoHelper.setWebPedidoItem(null);
                        PedidoHelper.setProduto(null);
                        PedidoHelper.setListaProdutos(null);
                        finish();
                    } else {
                        if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S"))
                            if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4"))
                                showFeedbackDialog("Desconto ultrapassou o limite autorizado!", true);
                                //showMsgAlerta("Desconto ultrapassou o limite autorizado!");
                            else if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6"))
                                //showMsgAlerta("Desconto ultrapassou o limite autorizado!!");
                                showFeedbackDialog("Desconto ultrapassou o limite autorizado!!", true);
                                //Toast.makeText(this, "Desconto ultrapassou o limite da tabela!", Toast.LENGTH_SHORT).show();
                            else
                                showFeedbackDialog("Desconto ultrapassou o limite autorizado!!!", true);
                                //showMsgAlerta("Desconto ultrapassou o limite autorizado!!!");
                            //Toast.makeText(this, "Desconto ultrapassou o limite da condição de pagamento!!", Toast.LENGTH_SHORT).show();
                        else
                            showFeedbackDialog("Desconto ultrapassou o limite autorizado!!!!", true);
                            //showMsgAlerta("Desconto ultrapassou o limite autorizado!!!!");
                        //Toast.makeText(this, "Desconto ultrapassou o limite da condição de pagamento!", Toast.LENGTH_SHORT).show();
                    }
                } else
                    showFeedbackDialog("O preço não pode ser zero!", true);
                    //Toast.makeText(this, "O preço não pode ser zero!", Toast.LENGTH_SHORT).show();
            } else
                showFeedbackDialog("Informe o preço do produto!", true);
                //Toast.makeText(this, "Informe o preço do produto!", Toast.LENGTH_SHORT).show();
        } else
            showFeedbackDialog("Informe um produto", false);
            //Toast.makeText(this, "Informe um produto", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        verificaCampanha = true;
        CampanhaHelper.setCampanhaComercialCab(null);
        //System.gc();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        verificaCampanha = true;
        CampanhaHelper.setCampanhaComercialCab(null);
        super.onBackPressed();
    }

    private void calculaDesconto() {
        try {
            totalProdutoBruto = 0.0f;
            totalProdutoUnitario = 0.0f;
            totalICMS         = 0.0f;
            desconto = 0.0f;
            descontoUnitario = 0.0f;
            descontoVerba = 0.0f;
            valorIcmsST  = 0.0f;
            BigDecimal bdesconto = null;
            BigDecimal bdescontoUnitario = null;
            BigDecimal btotalBruto = null;
            BigDecimal bDescontoVerba = null;
            BigDecimal bValorIcmsST = null;


            if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0) {
                //Float valorUnitario = 0.f;
                if (!edtQuantidade.getText().toString().trim().isEmpty()) {
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    for (Produto produto : PedidoHelper.getListaProdutos()) {
                        btotalBruto = new BigDecimal((quantidade * Float.parseFloat(produto.getVenda_preco()))).setScale(2, RoundingMode.HALF_EVEN);
                        totalProdutoBruto += Float.parseFloat(String.valueOf(btotalBruto));//(quantidade * Float.parseFloat(produto.getVenda_preco()));
                        totalProdutoUnitario = Float.parseFloat(String.valueOf(btotalBruto));//(quantidade * Float.parseFloat(produto.getVenda_preco()));
                        if (!produto.getProduto_tercerizacao().equals("S") && !produto.getProduto_materia_prima().equals("S")) {
                            promocaoRetorno = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro_servidor(), produto.getId_produto(), ActivityProdutoPedido.this);
                            try {
                                if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                                    if (!edtDesconto.getText().toString().trim().isEmpty()) {
                                        if (Float.parseFloat(edtDesconto.getText().toString()) > promocaoRetorno.getValorDesconto()) {
                                            edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                            edtDesconto.selectAll();
                                            showFeedbackDialog("Percentual do desconto informado acima do máximo permitido na promoção!", true);
                                            //Toast.makeText(ActivityProdutoPedido.this, "Percentual do desconto informado acima do máximo permitido na promoção!", Toast.LENGTH_LONG).show();
                                        } else {
                                            edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                                        }
                                    } else{
                                        edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                                    }
                                } else if (Float.parseFloat(edtDesconto.getText().toString()) > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                                    showFeedbackDialog("Percentual do desconto informado acima do máximo permitido!", true);
                                    //Toast.makeText(ActivityProdutoPedido.this, "Percentual do desconto informado acima do máximo permitido!", Toast.LENGTH_LONG).show();
                                    edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                    edtDesconto.selectAll();
                                } else {
                                    edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                                }
                            } catch (NullPointerException|NumberFormatException e) {
                                if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f) {
                                    if (!edtDesconto.getText().toString().trim().isEmpty()) {
                                        if (Float.parseFloat(edtDesconto.getText().toString()) > promocaoRetorno.getValorDesconto()) {
                                            edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                            edtDesconto.selectAll();
                                            showFeedbackDialog("Percentual do desconto informado acima do máximo permitido na promoção!", true);
                                            //Toast.makeText(ActivityProdutoPedido.this, "Percentual do desconto informado acima do máximo permitido na promoção!", Toast.LENGTH_LONG).show();
                                        } else {
                                            edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                                        }
                                    } else{
                                        edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                                    }
                                }
                                //Alterado dia 26/03/2021 RRC
                                /*else if (Float.parseFloat(edtDesconto.getText().toString()) > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                                    Toast.makeText(ActivityProdutoPedido.this, "Percentual do desconto informado acima do máximo permitido!", Toast.LENGTH_LONG).show();
                                    edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                    edtDesconto.selectAll();
                                }*/ else {
                                    edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (rbPorcentagem.isChecked()) {
                                if (!edtDesconto.getText().toString().trim().isEmpty()) {
                                    if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S")) {
                                        if (verificaCampanhaLista(ClienteHelper.getCliente(), produto)) {
                                            //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                            bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(3, RoundingMode.HALF_EVEN);
                                            int tamanho = String.valueOf(bdesconto).length();
                                            if ( String.valueOf(bdesconto).substring( tamanho-1 ).equalsIgnoreCase("5")) {
                                                if ( ((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho-2,tamanho-1))) % 2) == 0){
                                                    bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.DOWN);
                                                } else{
                                                    bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.UP);
                                                }
                                            } else {
                                                bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                            }
                                            desconto += Float.parseFloat(String.valueOf(bdesconto));
                                            descontoUnitario = Float.parseFloat(String.valueOf(bdesconto));


                                        } else {
                                            try {
                                                if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                                                    //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    desconto += Float.parseFloat(String.valueOf(0.0f));

                                                } else {
                                                    //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(3, RoundingMode.HALF_EVEN);
                                                    int tamanho = String.valueOf(bdesconto).length();
                                                    if (String.valueOf(bdesconto).substring(tamanho - 1).equalsIgnoreCase("5")) {
                                                        if (((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                            bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.DOWN);
                                                        } else {
                                                            bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.UP);
                                                        }
                                                    } else {
                                                        bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    }
                                                    desconto += Float.parseFloat(String.valueOf(bdesconto));
                                                    descontoUnitario = Float.parseFloat(String.valueOf(bdesconto));
                                                }
                                            } catch ( NullPointerException|NumberFormatException e) {
                                                if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f ) {
                                                    //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    desconto += Float.parseFloat(String.valueOf(0.0f));

                                                } else {
                                                    //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(3, RoundingMode.HALF_EVEN);
                                                    int tamanho = String.valueOf(bdesconto).length();
                                                    if (String.valueOf(bdesconto).substring(tamanho - 1).equalsIgnoreCase("5")) {
                                                        if (((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                            bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.DOWN);
                                                        } else {
                                                            bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.UP);
                                                        }
                                                    } else {
                                                        bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    }
                                                    desconto += Float.parseFloat(String.valueOf(bdesconto));
                                                    descontoUnitario = Float.parseFloat(String.valueOf(bdesconto));
                                                }
                                            }
                                        }
                                    } else {
                                        try {
                                            if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                                                //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                desconto += Float.parseFloat(String.valueOf(0.0f));
                                                descontoUnitario = Float.parseFloat(String.valueOf(0.0f));

                                            } else {
                                                //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(3, RoundingMode.HALF_EVEN);
                                                int tamanho = String.valueOf(bdesconto).length();
                                                if (String.valueOf(bdesconto).substring(tamanho - 1).equalsIgnoreCase("5")) {
                                                    if (((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                        bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.DOWN);
                                                    } else {
                                                        bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.UP);
                                                    }
                                                } else {
                                                    bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                }
                                                desconto += Float.parseFloat(String.valueOf(bdesconto));
                                                descontoUnitario = Float.parseFloat(String.valueOf(bdesconto));

                                            }
                                        } catch ( NullPointerException|NumberFormatException e) {
                                            if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f) {
                                                //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                desconto += Float.parseFloat(String.valueOf(0.0f));

                                            } else {
                                                //bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(3, RoundingMode.HALF_EVEN);
                                                int tamanho = String.valueOf(bdesconto).length();
                                                if (String.valueOf(bdesconto).substring(tamanho - 1).equalsIgnoreCase("5")) {
                                                    if (((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                        bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.DOWN);
                                                    } else {
                                                        bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.UP);
                                                    }
                                                } else {
                                                    bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (quantidade * Float.parseFloat(produto.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                }
                                                desconto += Float.parseFloat(String.valueOf(bdesconto));
                                                descontoUnitario = Float.parseFloat(String.valueOf(bdesconto));
                                            }
                                        }
                                    }
                                    try {
                                        if( configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                                            if (produto.getProduto_suj_icms_st().equalsIgnoreCase("S")) {
                                                if (produto.getProduto_st_aliquota() > 0.00f) {
                                                    bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(4, RoundingMode.HALF_EVEN);
                                                    int tamanho = String.valueOf(bValorIcmsST).length();
                                                    if ( (String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho -1)).trim().equalsIgnoreCase("5")) {
                                                        if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                            bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(2, RoundingMode.UP);
                                                        } else {
                                                            bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(2, RoundingMode.DOWN /*RoundingMode.HALF_EVEN*/);
                                                        }
                                                    } else {
                                                        bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    }
                                                    totalICMS += Float.parseFloat(String.valueOf(bValorIcmsST));//(quantidade * Float.parseFloat(p

                                                }
                                            }
                                        }
                                    } catch ( NullPointerException|NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        if( configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                                            if (produto.getProduto_suj_icms_st().equalsIgnoreCase("S")) {
                                                if (produto.getProduto_st_aliquota() > 0.00f) {
                                                    bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(4, RoundingMode.HALF_EVEN);
                                                    int tamanho = String.valueOf(bValorIcmsST).length();
                                                    if ( (String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho -1)).trim().equalsIgnoreCase("5")) {
                                                        if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                            bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(2, RoundingMode.UP);
                                                        } else {
                                                            bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(2, RoundingMode.DOWN /*RoundingMode.HALF_EVEN*/);
                                                        }
                                                    } else {
                                                        bValorIcmsST = new BigDecimal((produto.getProduto_st_aliquota() * (totalProdutoUnitario - (descontoUnitario + descontoVerba))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                                    }
                                                    totalICMS += Float.parseFloat(String.valueOf(bValorIcmsST));//(quantidade * Float.parseFloat(p


                                                }
                                            }
                                        }
                                    } catch ( NullPointerException|NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    edtDescontoReais.setText("0.00");
                                }
                                //float valorIcmsTmp = Float.parseFloat(String.valueOf(bValorIcmsST));
                                try {
                                    valorIcmsST += Float.parseFloat(String.valueOf(bValorIcmsST));
                                    //webPedidoItem.setIcms_st_valor(String.valueOf(valorIcmsST));
                                } catch ( NullPointerException|NumberFormatException e) {
                                    e.printStackTrace();
                                } catch ( Exception e) {
                                   e.printStackTrace();
                                }
                            } else if (rbReal.isChecked()) {
                                if (!edtDescontoReais.getText().toString().trim().isEmpty()) {
                                    bdesconto = new BigDecimal((Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / (quantidade * Float.parseFloat(produto.getVenda_preco()))).setScale(2, RoundingMode.HALF_EVEN);
                                    desconto += Float.parseFloat(String.valueOf(bdesconto));
                                } else {
                                    edtDesconto.setText("0.00");
                                }

                            }
                        }
                    }
                    try {
                        //valorIcmsST = Float.parseFloat(String.valueOf(bValorIcmsST));
                        //edtvalorIcmsST.setText(String.valueOf(valorIcmsST));
                        edtvalorIcmsST.setText(MascaraUtil.mascaraVirgula(totalICMS));
                    } catch ( NullPointerException|NumberFormatException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                    //edtValorProdutos.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto ));
                    edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto + totalICMS));
                    if (rbPorcentagem.isChecked()) {
                        if (!edtDesconto.getText().toString().trim().isEmpty()) {
                            edtDescontoReais.setText(MascaraUtil.duasCasaDecimal(desconto));
                            //edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoUnitario));
                            edtTotal.setText(MascaraUtil.mascaraVirgula(( totalProdutoBruto + totalICMS) - desconto));
                        } else {
                            edtDescontoReais.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto + totalICMS));
                            //edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoUnitario));
                            edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto - descontoUnitario));
                        }

                    } else if (rbReal.isChecked()) {
                        if (!edtDescontoReais.getText().toString().trim().isEmpty()) {
                            bdesconto = new BigDecimal((Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / totalProdutoBruto).setScale(2, RoundingMode.HALF_EVEN);
                            desconto = Float.parseFloat(String.valueOf(bdesconto));
                            edtDesconto.setText(MascaraUtil.duasCasaDecimal(desconto));
                            //edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoUnitario));
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto - Float.parseFloat(edtDescontoReais.getText().toString())));
                        } else {
                            edtDesconto.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto));
                            //edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoUnitario));

                        }

                    }
                }

            } else if (webPedidoItem != null) {//|| (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0)) {
                if (!edtQuantidade.getText().toString().trim().isEmpty()) {
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    if ( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) <= Float.parseFloat(webPedidoItem.getVenda_preco())) {
                        btotalBruto = new BigDecimal(quantidade * Float.parseFloat(webPedidoItem.getVenda_preco())).setScale(2, RoundingMode.HALF_EVEN);
                    } else {
                        btotalBruto = new BigDecimal(quantidade * Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", "."))).setScale(2, RoundingMode.HALF_EVEN);
                    }
                    totalProdutoBruto += Float.parseFloat(String.valueOf(btotalBruto));//quantidade * Float.parseFloat(webPedidoItem.getVenda_preco());
                    edtValorProdutos.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto));
                    edtValorPrecoPago.setText(MascaraUtil.duasCasaDecimal(Float.parseFloat(webPedidoItem.getVenda_preco())));
                    if (rbPorcentagem.isChecked()) {

                        if (!edtDesconto.getText().toString().trim().isEmpty()) {
                            bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(3, RoundingMode.HALF_EVEN);
                            int tamanho = String.valueOf(bdesconto).length();
                            if ( String.valueOf(bdesconto).substring( tamanho-1 ).equalsIgnoreCase("5")) {
                               if ( ((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho-2,tamanho-1))) % 2) == 0){
                                   bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.DOWN);
                                   if ( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) <= Float.parseFloat(webPedidoItem.getVenda_preco())) {
                                       bdescontoUnitario = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (Float.parseFloat(webPedidoItem.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                   } else {
                                       bdescontoUnitario = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                   }
                               } else{
                                   bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.UP);
                                   if ( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) <= Float.parseFloat(webPedidoItem.getVenda_preco())) {
                                      bdescontoUnitario = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (Float.parseFloat(webPedidoItem.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                   }else {
                                       bdescontoUnitario = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                   }
                                }
                            } else {
                                bdesconto = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                if ( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) <= Float.parseFloat(webPedidoItem.getVenda_preco())) {
                                    bdescontoUnitario = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (Float.parseFloat(webPedidoItem.getVenda_preco()))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                } else {
                                    bdescontoUnitario = new BigDecimal((Float.parseFloat(edtDesconto.getText().toString()) * (Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                }
                            }
                            desconto = Float.parseFloat(String.valueOf(bdesconto));
                            descontoUnitario = Float.parseFloat(String.valueOf(bdescontoUnitario));
                            edtDescontoReais.setText(MascaraUtil.duasCasaDecimal(desconto));
                            if ( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) <= Float.parseFloat(webPedidoItem.getVenda_preco())) {
                                edtValorPrecoPago.setText(MascaraUtil.duasCasaDecimal(Float.parseFloat(webPedidoItem.getVenda_preco()) - descontoUnitario));
                            } else {
                                edtValorPrecoPago.setText(MascaraUtil.duasCasaDecimal( Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", ".")) - descontoUnitario));
                            }

                            if (  configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S")  ) {
                                if (!edtDescontoVerba.getText().toString().isEmpty()) {
                                    try {
                                        if (Float.parseFloat(edtDescontoVerba.getText().toString()) > 0) {
                                            bDescontoVerba = new BigDecimal((Float.parseFloat(edtDescontoVerba.getText().toString()) * (totalProdutoBruto - desconto)) / 100).setScale(3, RoundingMode.HALF_EVEN);
                                            tamanho = String.valueOf(bDescontoVerba).length();
                                            if (String.valueOf(bDescontoVerba).substring(tamanho - 1).equalsIgnoreCase("5")) {
                                                if (((Integer.parseInt(String.valueOf(bDescontoVerba).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                    bDescontoVerba = new BigDecimal((Float.parseFloat(edtDescontoVerba.getText().toString()) * (totalProdutoBruto - desconto)) / 100).setScale(2, RoundingMode.DOWN);
                                                } else {
                                                    bDescontoVerba = new BigDecimal((Float.parseFloat(edtDescontoVerba.getText().toString()) * (totalProdutoBruto - desconto)) / 100).setScale(2, RoundingMode.UP);
                                                }
                                            } else {
                                                bDescontoVerba = new BigDecimal((Float.parseFloat(edtDescontoVerba.getText().toString()) * (totalProdutoBruto - desconto)) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                            }
                                        }
                                    } catch (NullPointerException|NumberFormatException e) {
                                    } catch (Exception e) {
                                    }
                                }
                                try {
                                    descontoVerba = Float.parseFloat(String.valueOf(bDescontoVerba));
                                } catch (NullPointerException | NumberFormatException e) {

                                } catch (Exception e) {

                                }

                            } else {
                                descontoVerba = 0;
                            }
                            try {
                                if( configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                                    if (webPedidoItem.getIcms_st_calcula().equalsIgnoreCase("S")) {
                                        if (webPedidoItem.getIcms_st_percentual() > 0.00f) {
                                            bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(4, RoundingMode.HALF_EVEN);
                                            tamanho = String.valueOf(bValorIcmsST).length();
                                            if ( (String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho -1)).trim().equalsIgnoreCase("5")) {
                                                if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                    bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.UP);
                                                } else {
                                                    bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.DOWN /*RoundingMode.HALF_EVEN*/);
                                                }
                                            } else {
                                                bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                            }
                                            //bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(3, RoundingMode.DOWN);
                                        }
                                    }
                                }
                            } catch ( NullPointerException|NumberFormatException e) {
                                e.printStackTrace();
                            }
                            try {
                                valorIcmsST = Float.parseFloat(String.valueOf(bValorIcmsST));
                                edtvalorIcmsST.setText(String.valueOf(valorIcmsST));
                                webPedidoItem.setIcms_st_valor(String.valueOf(valorIcmsST));
                            } catch ( NullPointerException|NumberFormatException e) {
                                 e.printStackTrace();
                            } catch ( Exception e) {
                                 e.printStackTrace();
                            }
                            if ( !edtDescontoVerba.getText().toString().isEmpty()) {
                                edtTotal.setText(MascaraUtil.mascaraVirgula((totalProdutoBruto + valorIcmsST) - (desconto + descontoVerba)));
                            } else {
                                edtTotal.setText(MascaraUtil.mascaraVirgula((totalProdutoBruto + valorIcmsST) - desconto));
                            }
                            //edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoUnitario));

                        } else {
                            try {
                                if( configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") && empresaParametro.getEndereco_uf().equalsIgnoreCase(ClienteHelper.getCliente().getEndereco_uf())) {
                                    if (webPedidoItem.getIcms_st_calcula().equalsIgnoreCase("S")) {
                                        if (webPedidoItem.getIcms_st_percentual() > 0.00f) {
                                            bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(4, RoundingMode.HALF_EVEN);
                                            int tamanho = String.valueOf(bValorIcmsST).length();
                                            if ( (String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho -1)).trim().equalsIgnoreCase("5")) {
                                                if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                                                    bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.UP);
                                                } else {
                                                    bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.DOWN /*RoundingMode.HALF_EVEN*/);
                                                }
                                            } else {
                                                bValorIcmsST = new BigDecimal((webPedidoItem.getIcms_st_percentual() * (totalProdutoBruto - (desconto + descontoVerba))) / 100).setScale(2, RoundingMode.HALF_EVEN);
                                            }

                                        }
                                    }
                                }
                            } catch ( NullPointerException|NumberFormatException e) {
                                e.printStackTrace();
                            }
                            try {
                                valorIcmsST = Float.parseFloat(String.valueOf(bValorIcmsST));
                                edtvalorIcmsST.setText(String.valueOf(valorIcmsST));
                                webPedidoItem.setIcms_st_valor(String.valueOf(valorIcmsST));
                            } catch ( NullPointerException|NumberFormatException e) {

                            } catch ( Exception e) {

                            }
                            edtDescontoReais.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto + valorIcmsST));
                            //edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoUnitario));
                        }
                    } else if (rbReal.isChecked()) {
                        if (!edtDescontoReais.getText().toString().trim().isEmpty()) {
                            bdesconto = new BigDecimal((Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / totalProdutoBruto).setScale(3, RoundingMode.HALF_EVEN);
                            int tamanho = String.valueOf(bdesconto).length();
                            if ( String.valueOf(bdesconto).substring( tamanho-1 ).equalsIgnoreCase("5")) {
                                if ( ((Integer.parseInt(String.valueOf(bdesconto).substring(tamanho-2,tamanho-1))) % 2) == 0){
                                    bdesconto = new BigDecimal((Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / totalProdutoBruto).setScale(2, RoundingMode.DOWN);
                                    bdescontoUnitario = new BigDecimal(Float.parseFloat(edtDescontoReais.getText().toString()) ).setScale(2, RoundingMode.HALF_EVEN);
                                } else{
                                    bdesconto = new BigDecimal((Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / totalProdutoBruto).setScale(2, RoundingMode.UP);
                                    bdescontoUnitario = new BigDecimal(Float.parseFloat(edtDescontoReais.getText().toString()) ).setScale(2, RoundingMode.HALF_EVEN);
                                }
                            } else {
                                bdesconto = new BigDecimal((Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / totalProdutoBruto).setScale(2, RoundingMode.HALF_EVEN);
                                bdescontoUnitario = new BigDecimal(Float.parseFloat(edtDescontoReais.getText().toString()) ).setScale(2, RoundingMode.HALF_EVEN);
                            }

                            desconto = Float.parseFloat(String.valueOf(bdesconto));
                            descontoUnitario = Float.parseFloat(String.valueOf(bdescontoUnitario));
                            edtDesconto.setText(MascaraUtil.duasCasaDecimal(desconto));
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto - Float.parseFloat(edtDescontoReais.getText().toString())));
                            edtValorPrecoPago.setText(MascaraUtil.duasCasaDecimal(Float.parseFloat(webPedidoItem.getVenda_preco()) - descontoUnitario));
                            //edtValorPrecoPago.setText(MascaraUtil.duasCasaDecimal(Float.parseFloat(webPedidoItem.getVenda_preco()) - descontoUnitario));

                        } else {
                            edtDesconto.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto));
                            //edtValorPrecoPago.setText(MascaraUtil.mascaraVirgula(totalProdutoUnitario));
                        }
                    }
                    try {
                        if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                            if (Float.parseFloat(edtDesconto.getText().toString()) > promocaoRetorno.getValorDesconto()) {
                                edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                edtDesconto.selectAll();
                            }else {
                                edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                            }
                        } else if (Float.parseFloat(edtDesconto.getText().toString()) > desconto_perc) {
                            if ( ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S") && ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                                if (Float.parseFloat(edtDesconto.getText().toString()) > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()) ) {
                                    edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                    edtDesconto.selectAll();
                                } else {
                                    edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                                }
                            } else {
                                edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                edtDesconto.selectAll();
                            }
                        } else {
                            edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                        }
                    } catch ( NumberFormatException|NullPointerException e) {
                          if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0.0f ) {
                            if (Float.parseFloat(edtDesconto.getText().toString()) > promocaoRetorno.getValorDesconto()) {
                                edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                                edtDesconto.selectAll();
                            }else
                                edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                        } else if (Float.parseFloat(edtDesconto.getText().toString()) > desconto_perc) {
                            edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                              edtDesconto.selectAll();
                        } else {
                            edtDesconto.setBackgroundResource(R.drawable.border_shadow_2025_outline);
                        }
                    } catch ( Exception e) {

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   //Analisa se o usuario pode editar o preço e se ele pode vender abaixo do preço da tabela
    private boolean permiteDesconto() {
       BigDecimal bValorProduto = null;
       BigDecimal bValorEditado = null;
       bValorProduto = new BigDecimal(Float.parseFloat(PedidoHelper.getProduto().getVenda_preco())).setScale(2, RoundingMode.HALF_EVEN);
       bValorEditado = new BigDecimal(Float.parseFloat(edtTabelaPreco.getText().toString().replaceAll("\\s+", "").replace(",", "."))).setScale(2, RoundingMode.HALF_EVEN);
       try {
          if ( Float.parseFloat(String.valueOf(bValorEditado)) >= Float.parseFloat(String.valueOf(bValorProduto)) ){
             return true;
          }
          return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private class ViewPagerStack implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position >= 0) {
                page.setScaleX(0.7f - 0.05f * position);
                page.setScaleY(0.7f);
                page.setTranslationX(-page.getWidth() * position);
                page.setTranslationY(-48 * position);
            }
        }
    }

    class slideTimer extends TimerTask {
        @Override
        public void run() {
            ActivityProdutoPedido.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mViewPager.getCurrentItem() < mContents.size() - 1)
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    else
                        mViewPager.setCurrentItem(0);
                }
            });
        }
    }

    private boolean verificaCampanha(Cliente cliente, Produto produto, boolean visualizaCampanha) {
        //edtNomeCampanha.setBackgroundResource(R.drawable.borda_edittext);
        try {
            List<CampanhaComercialCab> listaCampanha = campanhaComercialCabDAO.getLista(cliente, produto);
            if (listaCampanha.size() > 0) {
                CampanhaHelper.setListaCampanha(listaCampanha);
                try {
                    if (webPedidoItem.getIdCampanha() <= 0) {
                        webPedidoItem.setIdCampanha(listaCampanha.get(0).getIdCampanha());
                        webPedidoItem.setNomeCampanha(listaCampanha.get(0).getNomeCampanha());
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (visualizaCampanha) {
                    Intent intent = new Intent(ActivityProdutoPedido.this, ActivityCampanha.class);
                    intent.putExtra("pedido", 1);
                    intent.putExtra("produto", produto.getNome_produto());
                    startActivity(intent);
                }

                return true;
            }
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean verificaCampanhaLista(Cliente cliente, Produto produto) {
        edtNomeCampanha.setBackgroundResource(R.drawable.borda_edittext);
        try {
            List<CampanhaComercialCab> listaCampanha = campanhaComercialCabDAO.getLista(cliente, produto);
            if (listaCampanha.size() > 0)
                return true;

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<CampanhaComercialCab> getCampanhaLista(Cliente cliente, Produto produto) {
        edtNomeCampanha.setBackgroundResource(R.drawable.borda_edittext);
        List<CampanhaComercialCab> listaCampanha = new ArrayList<>();
        try {
            listaCampanha = campanhaComercialCabDAO.getLista(cliente, produto);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return listaCampanha;
    }

    private void showLayoutCampanha() {
        String campanhas = "";
        ViewGroup.LayoutParams params = lymainCampanha.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lymainCampanha.setLayoutParams(params);
        try {
            if (webPedidoItem.getIdCampanha() > 0) {
                for (CampanhaComercialCab campanha : CampanhaHelper.getListaCampanha()) {
                    if (campanha.getIdCampanha() == webPedidoItem.getIdCampanha()) {
                        edtNomeCampanha.setText("*  " + campanha.getNomeCampanha());
                        webPedidoItem.setNomeCampanha(campanha.getNomeCampanha());
                        //break;
                    } else {
                        campanhas = "-  " + campanha.getNomeCampanha() + "\n";
                    }
                }
                edtNomeCampanha.setText(edtNomeCampanha.getText().toString() + "\n" + campanhas);
            } else {
                edtNomeCampanha.setText(CampanhaHelper.getCampanhaComercialCab().getNomeCampanha());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

            //edtNomeCampanha.setText("Campanha (ativa(s)");
        }
    }

    private void hideLayoutCampanha() {
        ViewGroup.LayoutParams params = lymainCampanha.getLayoutParams();
        params.height = 0;
        lymainCampanha.setLayoutParams(params);
        edtNomeCampanha.setText("");
        //btnInfoCampanha.setVisibility(View.GONE);
    }

    private void showLayoutSku() {
        //String campanhas = "";
        ViewGroup.LayoutParams params = lymainSku.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lymainSku.setLayoutParams(params);
        try {
            if (listaSku.size() > 0) {
                //Verificar aqui
            } else {
                //edtNomeCampanha.setText(CampanhaHelper.getCampanhaComercialCab().getNomeCampanha());
            }
        } catch (NullPointerException e) {
            //edtNomeCampanha.setText("Campanha (ativa(s)");
        }
    }

    private void showLayoutIcmsST(){
        ViewGroup.LayoutParams params = lyvalorIcmsST.getLayoutParams();
        params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;
        lyvalorIcmsST.setLayoutParams(params);
    }

    private void hideLayoutSku() {
        ViewGroup.LayoutParams params = lymainSku.getLayoutParams();
        params.height = 0;
        lymainSku.setLayoutParams(params);
    }

    private void hideLayoutDescontoReais() {
        ViewGroup.LayoutParams params = lydsctoReais.getLayoutParams();
        params.height = 0;
        lydsctoReais.setLayoutParams(params);
    }

    private void hideLayoutDescontoVerba() {
        ViewGroup.LayoutParams params = lydsctoVerba.getLayoutParams();
        params.height = 0;
        lydsctoVerba.setLayoutParams(params);
    }

    private void hideLayoutIcmsST() {
        ViewGroup.LayoutParams params = lyvalorIcmsST.getLayoutParams();
        params.height = 0;
        lyvalorIcmsST.setLayoutParams(params);
    }

    private void showMultiSelecao(int qtde_desconto_bloqueado, int qtde_promocao, int qtde_desconto_permitido, int qtde_campanha, String campanhas) {
        if (qtde_campanha > 1) {
            edtNomeCampanha.setText("VARIAS CAMPANHAS ATIVAS!\nINCLUA OS PRODUTOS EM CAMPANHA INDIVIDUALMENTE PARA LANÇAR AS MESMAS");
        } else {
            edtNomeCampanha.setText(campanhas);
        }
        if (qtde_desconto_bloqueado > 0 || qtde_promocao > 0) {
            int total_bloqueado = qtde_desconto_bloqueado + qtde_promocao;
            if (total_bloqueado == PedidoHelper.getListaProdutos().size())
                edtDesconto.setText("0.00");
        }
        cdPromocao.setVisibility(View.VISIBLE);
        if (qtde_desconto_bloqueado > 0) {
            if (qtde_desconto_bloqueado > 1)
                if (prazo_permite_desconto)
                    txtPromocao.setText(txtPromocao.getText().toString() + "\n" + qtde_desconto_bloqueado + " COM DESCONTO(S) NÃO AUTORIZADO(S)" + "\n" + " VINCULADO(S) A PREÇO FINAL/TABELA/OUTROS");
                else
                    txtPromocao.setText(txtPromocao.getText().toString() + "\n" + qtde_desconto_bloqueado + " COM DESCONTO(S) NÃO AUTORIZADO(S)" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO"  + "\n" + " VINCULADO(S) A PREÇO FINAL/TABELA/OUTROS");
            else
                txtPromocao.setText(txtPromocao.getText().toString() + "\n" + qtde_desconto_bloqueado + " COM DESCONTO NÃO AUTORIZADO" + "\n" + " T VINCULADO A PREÇO FINAL/TABELA/OUTROS");
            if (qtde_desconto_bloqueado == PedidoHelper.getListaProdutos().size()) {
                rbPorcentagem.setEnabled(false);
                edtDesconto.setEnabled(false);
            }
        }
        if (qtde_desconto_permitido > 0)
            txtPromocao.setText(txtPromocao.getText().toString() + "\n" + qtde_desconto_permitido + " PRODUTO(S) DESCONTO(S) PERMITIDO(S)");
        if (qtde_promocao > 0)
            txtPromocao.setText(txtPromocao.getText().toString() + "\n" + qtde_promocao + " PRODUTO(S) EM PROMOÇÃO");
        if (qtde_campanha > 0)
            txtPromocao.setText(txtPromocao.getText().toString() + "\n" + qtde_campanha + " PRODUTO(S) EM CAMPANHA");
    }

    private void showMultiSelecao(float desconto) {
        try {
            if (!tabelaPrecoItem.getId_item().isEmpty()) {
                if (prazo_permite_desconto)
                    txtPromocao.setText(PedidoHelper.getListaProdutos().size() + " PRODUTO(S) SELECIONADO(S)\n" + "DESCONTO NÃO AUTORIZADO" + "\n" + "PRODUTO(S) VINCULADO A PREÇO FINAL/TABELA");
                else
                    txtPromocao.setText(PedidoHelper.getListaProdutos().size() + " PRODUTO(S) SELECIONADO(S)\n" + "DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO" + "\n" + "PRODUTO(S) VINCULADO A PREÇO FINAL/TABELA");

            }else {
                txtPromocao.setText(PedidoHelper.getListaProdutos().size() + " PRODUTO(S) SELECIONADO(S)\n" + "DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
            }
        } catch (NullPointerException e) {
            txtPromocao.setText(PedidoHelper.getListaProdutos().size() + " PRODUTO(S) SELECIONADO(S)\n" + "DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
        } catch (Exception e) {
            e.printStackTrace();
        }
        rbPorcentagem.setEnabled(false);
        //rbReal.setEnabled(false);
        edtDesconto.setEnabled(false);
        edtDesconto.setText("00");
        edtDescontoReais.setEnabled(false);
        edtDescontoReais.setText("0.00");
        cdPromocao.setVisibility(View.VISIBLE);
        rbPorcentagem.setText("D. max(0%)");

    }

    private Float getSaldoEstoque(String idProduto) {
        try {
            Cursor cursor = db.listaDados("SELECT SALDO_ESTOQUE  FROM TBL_PRODUTO WHERE ID_PRODUTO = '" +idProduto + "'");
            if (cursor.moveToNext()) {
                @SuppressLint("Range")
                Float saldoEsqoque = Float.parseFloat(cursor.getString(cursor.getColumnIndex("SALDO_ESTOQUE"))) ;
                try {
                    if (saldoEsqoque > 0.0f) {
                        return saldoEsqoque;
                    } else {
                        return 0.00f;
                    }
                } catch (NullPointerException|NumberFormatException e) {
                    return 0.00f;
                }

            }
        } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
        } catch (Exception e) {
        }
        return 0.0f;
    }
    private void setWebPedidoItem() {
        try {
            if (PedidoHelper.getProduto() != null && !PedidoHelper.getProduto().getId_produto().equals(PedidoHelper.getWebPedidoItem().getId_produto())) {
                webPedidoItem = new WebPedidoItens(PedidoHelper.getProduto());
                PedidoHelper.setProduto(null);
                PedidoHelper.setWebPedidoItem(webPedidoItem);
                PedidoHelper.getWebPedidoItem().setSaldo_estoque(getSaldoEstoque(webPedidoItem.getId_produto()));
                webPedidoItem.setSaldo_estoque(getSaldoEstoque(webPedidoItem.getId_produto()));
                if (Float.parseFloat(edtDesconto.getText().toString()) <= 0)
                    edtDesconto.setText("0.00");
            } else {
                webPedidoItem = PedidoHelper.getWebPedidoItem();
                webPedidoItem.setSaldo_estoque(getSaldoEstoque(webPedidoItem.getId_produto()));
                try {
                    btnBuscaProduto.setVisibility(View.INVISIBLE);
                    btnBuscaProduto.setEnabled(false);
                    btnBuscaProduto.setClickable(false);
                    edtNomeProduto.setClickable(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                toolbar.setTitle("Item de venda(alteração)");
                try {
                    if (webPedidoItem.getProduto_edita_valor().equalsIgnoreCase("S") && webPedidoItem.getProduto_preco_editado() > 0.00f) {
                        webPedidoItem.setVenda_preco(String.valueOf(webPedidoItem.getProduto_preco_editado()));
                    }
                } catch ( NumberFormatException|NullPointerException e ) {
                    e.printStackTrace();
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                try {
                    edtQuantidade.setText(webPedidoItem.getQuantidade().toString().replace(".0", ""));
                } catch ( NullPointerException|NumberFormatException e) {
                    e.printStackTrace();
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                edtDesconto.setText(webPedidoItem.getValor_desconto_per());

                edtDescontoReais.setText(webPedidoItem.getValor_desconto_real());
                try {
                    edtvalorIcmsST.setText( webPedidoItem.getIcms_st_valor());
                } catch ( NullPointerException|NumberFormatException e) {
                }
                if (webPedidoItem.getTipoDesconto().equals("R"))
                    rbReal.setChecked(true);
            }

        } catch (NumberFormatException | NullPointerException e) {
            try {
                webPedidoItem = new WebPedidoItens(PedidoHelper.getProduto());
                webPedidoItem.setSaldo_estoque(getSaldoEstoque(webPedidoItem.getId_produto()));
                PedidoHelper.setWebPedidoItem(webPedidoItem);
                if (Float.parseFloat(edtDesconto.getText().toString()) <= 0)
                    edtDesconto.setText("0.00");
                try {
                    edtDesconto.setText(String.valueOf(desconto_perc));
                } catch (NullPointerException ex) {
                    edtDesconto.setText(String.valueOf(desconto_perc));
                } catch (Exception ex) {
                    edtDesconto.setText(String.valueOf(desconto_perc));
                }
                try {
                    edtvalorIcmsST.setText( webPedidoItem.getIcms_st_valor());
                } catch ( NullPointerException|NumberFormatException ex) {
                }
            } catch (Exception nullPointer) {
                //if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                if (configuracao.getTrabalha_com_desc_auto_app().equalsIgnoreCase("N")) {
                    try {
                        if (webPedidoItem.getValor_desconto_per() == null || webPedidoItem.getValor_desconto_per().isEmpty()) {
                            edtDesconto.setText("0.00");
                        } else {
                            edtDesconto.setText(String.valueOf(webPedidoItem.getValor_desconto_per()));
                        }
                    } catch (NullPointerException ex) {
                        edtDesconto.setText("0.00");
                    }
                } else {
                    edtDesconto.setText(String.valueOf(desconto_perc));
                }
                //edtDesconto.setText(String.valueOf(desconto_perc));
            }
        }
        if ( listaSku.size() > 0) {
            webPedidoItem.setVenda_preco(listaSku.get(spnSku.getSelectedItemPosition()).getPreco());
            webPedidoItem.setId_sku(listaSku.get(spnSku.getSelectedItemPosition()).getId_sku());
        }

    }

    private void showPrazoSemDesconto() {
        rbPorcentagem.setEnabled(false);
        rbReal.setEnabled(false);
        edtDesconto.setEnabled(false);
        edtDesconto.setText("00");
        edtDescontoReais.setEnabled(false);
        edtDescontoReais.setText("0.00");
        cdPromocao.setVisibility(View.VISIBLE);
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            try {
                if (!tabelaPrecoItem.getId_item().isEmpty()) {
                    if (prazo_permite_desconto)
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                    else
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                } else {
                    txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                }
            } catch (Exception e) {
                txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
            }
        else
            try {
                if (!tabelaPrecoItem.getId_item().isEmpty()) {
                    if (prazo_permite_desconto)
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                    else
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                } else {
                    txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                }
            } catch (Exception e) {
                txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "|" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
            }

        rbPorcentagem.setText("D. max(0%)");
    }

    private void showProdutoPromocao() {
        rbPorcentagem.setText("Desc max(" + promocaoRetorno.getValorDesconto().toString().replace(".0", "") + "%)");
        cdPromocao.setVisibility(View.VISIBLE);
        txtPromocao.setText("**PRODUTO EM PROMOÇÃO**\n" + promocaoRetorno.getNomePromocao());
        edtDesconto.setEnabled(true);
        edtDescontoReais.setEnabled(true);
        rbPorcentagem.setEnabled(true);
        rbReal.setEnabled(true);
    }

    private void showClienteCategoriaEspecial() {
        CategoriaDAO categoriaDAO = new CategoriaDAO(db);
        VendedorBonusResumoDAO vendedorBonusResumoDAO = new VendedorBonusResumoDAO(db);
        rbPorcentagem.setText("Desc. tabela(" + tabelaPrecoItem.getPerc_desc_final().toString().replace(".0", "") + "%)");
        cdPromocao.setVisibility(View.VISIBLE);
        float verbaUsadaPedido = 0.00f;
        float verbaDisponivelPedido = 0.00f;
        String id_vendedor =  PedidoHelper.getWebPedido().getId_vendedor();
        try {

            try {
                try {
                    verbaUsadaPedido = Float.parseFloat(PedidoHelper.getWebPedido().getValor_desconto_adic());
                } catch ( NullPointerException|NumberFormatException ex ) {

                } catch ( Exception ex) {

                }
                VendedorBonusResumo vendedorBonusResumo = vendedorBonusResumoDAO.getVendedor("SELECT * FROM TBL_VENDEDOR_BONUS_RESUMO WHERE ID_VENDEDOR = '"
                        + id_vendedor +"'");
                verbaDisponivelPedido  = Float.parseFloat(vendedorBonusResumo.getValor_saldo());
            } catch ( NullPointerException|NumberFormatException|CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
            //float saldoVerba =  Float.parseFloat(vendedorBonusResumoDAO.getVendedor("SELECT * FROM TBL_VENDEDOR_BONUS_RESUMO WHERE ID_VENDEDOR = "
            //        + PedidoHelper.getWebPedido().getId_vendedor()).getValor_saldo() );
            if ( verbaUsadaPedido > 0.00f) {
                try {
                    txtPromocao.setText("** SALDO DE VERBA (" +
                            MascaraUtil.duasCasaDecimal(verbaDisponivelPedido) + " - " + MascaraUtil.duasCasaDecimal(verbaUsadaPedido) + ") = R$ " + MascaraUtil.duasCasaDecimal(verbaDisponivelPedido - verbaUsadaPedido) + " **\n"
                            + "** PRODUTO COM DESCONTO EM TABELA **\n" + "** CATEGORIA " + categoriaDAO.getCategoria(PedidoHelper.getWebPedido().getCadastro().getIdCategoria()).getNomeCategoria().toUpperCase() + " **");
                } catch ( NullPointerException e) {
                    txtPromocao.setText("** SALDO DE VERBA (" +
                            MascaraUtil.duasCasaDecimal(verbaDisponivelPedido) + " - " + MascaraUtil.duasCasaDecimal(verbaUsadaPedido) + ") = R$ " + MascaraUtil.duasCasaDecimal(verbaDisponivelPedido - verbaUsadaPedido) + " **\n"
                            + "** PRODUTO COM DESCONTO EM TABELA **\n" + "** CATEGORIA "  + " **");
                } catch ( Exception e) {
                    txtPromocao.setText("** SALDO DE VERBA (" +
                            MascaraUtil.duasCasaDecimal(verbaDisponivelPedido) + " - " + MascaraUtil.duasCasaDecimal(verbaUsadaPedido) + ") = R$ " + MascaraUtil.duasCasaDecimal(verbaDisponivelPedido - verbaUsadaPedido) + " **\n"
                            + "** PRODUTO COM DESCONTO EM TABELA **\n" + "** CATEGORIA "  + " **");
                }
            } else {
                try {
                    txtPromocao.setText("** SALDO DE VERBA R$ " +
                            MascaraUtil.duasCasaDecimal(verbaDisponivelPedido) + " **\n"
                            + "** PRODUTO COM DESCONTO EM TABELA **\n" + "** CATEGORIA " + categoriaDAO.getCategoria(PedidoHelper.getWebPedido().getCadastro().getIdCategoria()).getNomeCategoria().toUpperCase() + " **");
                } catch ( NullPointerException e) {
                    txtPromocao.setText("** SALDO DE VERBA R$ " +
                            MascaraUtil.duasCasaDecimal(verbaDisponivelPedido) + " **\n"
                            + "** PRODUTO COM DESCONTO EM TABELA **\n" + "** CATEGORIA " + " **");
                } catch ( Exception e) {
                    txtPromocao.setText("** SALDO DE VERBA R$ " +
                            MascaraUtil.duasCasaDecimal(verbaDisponivelPedido) + " **\n"
                            + "** PRODUTO COM DESCONTO EM TABELA **\n" + "** CATEGORIA " + " **");
                }
            }
            /*txtPromocao.setText("**SALDO DE VERBA R$ " +
                    MascaraUtil.duasCasaDecimal(verbaDisponivelPedido) + "**\n" +
                    "**SALDO UTILIZADO R$ " + MascaraUtil.duasCasaDecimal( verbaUsadaPedido) + "**\n" +
                    "**SALDO DISPONIVEL R$ " + MascaraUtil.duasCasaDecimal( verbaDisponivelPedido - verbaUsadaPedido ) + "**\n"
                    + "**PRODUTO COM DESCONTO EM TABELA**\n" + "**CLIENTE CATEGORIA**\n"
                    + categoriaDAO.getCategoria(PedidoHelper.getWebPedido().getCadastro().getIdCategoria()).getNomeCategoria());
             */
        } catch ( NullPointerException | NumberFormatException e) {
            txtPromocao.setText("** SALDO DE VERBA R$ 0.00 **"
                    + "\n** PRODUTO COM DESCONTO EM TABELA **\n"
                    + "** CLIENTE CATEGORIA " + categoriaDAO.getCategoria(PedidoHelper.getWebPedido().getCadastro().getIdCategoria()).getNomeCategoria() + " **");
        } catch ( Exception e) {
            txtPromocao.setText("** SALDO DE VERBA R$ 0.00 **"
                    + "\n** PRODUTO COM DESCONTO EM TABELA **\n"
                    + "** CLIENTE CATEGORIA " + categoriaDAO.getCategoria(PedidoHelper.getWebPedido().getCadastro().getIdCategoria()).getNomeCategoria() + " **");
        }
        edtDesconto.setEnabled(true);
        edtDescontoReais.setEnabled(true);
        rbPorcentagem.setEnabled(true);
        rbReal.setEnabled(true);
        if ( verbaDisponivelPedido <= 0.00f || configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("N")) {
            edtDescontoVerba.setText("0.00");
            edtDescontoVerba.setEnabled(false);
        }
    }

    private void showProdutoSempromocao() {
        cdPromocao.setVisibility(View.GONE);
        try {
            try {
                rbPorcentagem.setText("D. max(" + String.valueOf(desconto_perc) + "%)");
                //Verificação posterior necessaria aqui, p analisar caso do desconto utilizado pela Bertola
                if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4")) {
                    if (ClienteHelper.getCliente().getIdCategoria() >= 1) {
                        if ( !webPedidoItem.getId_web_item().isEmpty())
                            edtDesconto.setText(String.valueOf(webPedidoItem.getValor_desconto_per()));
                        else {
                            if (configuracao.getTrabalha_com_desc_auto_app().equalsIgnoreCase("N")) {
                                if ( webPedidoItem.getValor_desconto_per() == null || webPedidoItem.getValor_desconto_per().isEmpty()) {
                                    edtDesconto.setText("");
                                } else {
                                    edtDesconto.setText(String.valueOf(webPedidoItem.getValor_desconto_per()));
                                }
                            } else  {
                                edtDesconto.setText(String.valueOf(desconto_perc));
                            }
                        }
                    }
                }else {
                    if (configuracao.getTrabalha_com_desc_auto_app().equalsIgnoreCase("N")) {
                        if ( webPedidoItem.getValor_desconto_per() == null || webPedidoItem.getValor_desconto_per().isEmpty()) {
                            edtDesconto.setText("");
                        } else {
                            edtDesconto.setText(String.valueOf(webPedidoItem.getValor_desconto_per()));
                        }
                    } else  {
                        edtDesconto.setText(String.valueOf(desconto_perc));
                    }
                }
            } catch (NullPointerException e) {
            }
        } catch (NullPointerException e) {
            edtDesconto.setText(String.valueOf(0));
        }
        if (desconto_perc <= 0.00) {
            rbPorcentagem.setEnabled(false);
            rbReal.setEnabled(false);
            edtDesconto.setEnabled(false);
            edtDesconto.setText("00");
            edtDescontoReais.setEnabled(false);
            edtDescontoReais.setText("0.00");
            cdPromocao.setVisibility(View.VISIBLE);
            int orientation = this.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                try {
                    if (!tabelaPrecoItem.getId_item().isEmpty()) {
                        if (prazo_permite_desconto)
                            txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                        else
                            txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                    } else {
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                    }
                } catch (Exception e) {
                    txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                }
            else
                try {
                    if (!tabelaPrecoItem.getId_item().isEmpty()) {
                        if (prazo_permite_desconto)
                            txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                        else
                            txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                    } else {
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                    }
                } catch (Exception e) {
                    txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                }

            rbPorcentagem.setText("Desconto (max 0%)");
        }
    }

    private void showProdutoSemDesconto() {
        rbPorcentagem.setEnabled(false);
        rbReal.setEnabled(false);
        edtDesconto.setEnabled(false);
        edtDesconto.setText("00");
        edtDescontoReais.setEnabled(false);
        edtDescontoReais.setText("0.00");
        cdPromocao.setVisibility(View.VISIBLE);
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            try {
                if (!tabelaPrecoItem.getId_item().isEmpty()) {
                    if (prazo_permite_desconto)
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                    else
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                } else {
                    txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                }
            } catch (Exception e) {
                txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + "\n" + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
            }
        else
            try {
                if (!tabelaPrecoItem.getId_item().isEmpty()) {
                    if (prazo_permite_desconto)
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                    else
                        txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO" + "\n" + "PRODUTO VINCULADO A PREÇO FINAL/TABELA");
                } else {
                    txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
                }
            } catch (Exception e) {
                txtPromocao.setText("DESCONTO NÃO AUTORIZADO" + " | " + "PRAZO INFORMADO NÃO PERMITE DESCONTO");
            }

        rbPorcentagem.setText("D. max(0%)");
    }

    private boolean existeProduto(WebPedidoItens webPedidoItem, boolean showMsg) {
        try {
            String SQL = "SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PRODUTO = '" + webPedidoItem.getId_produto().trim() + "' AND ID_PEDIDO = " + PedidoHelper.getIdPedido();
            Cursor cursor = db.listaDados(SQL);
            if (cursor.moveToFirst()) {
                if (showMsg)
                    showFeedbackDialog("O produto " + webPedidoItem.getNome_produto() + " já existe neste pedido!!", true);
                    //showMsgAlerta("O produto " + webPedidoItem.getNome_produto() + " já existe neste pedido!!");
                return true;
            }
        } catch (SQLException | IndexOutOfBoundsException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void showMsgSucesso(String mensagem) {
        ViewGroup viewGroup = ActivityProdutoPedido.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sucesso_mensagem, viewGroup, false);
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
                alertDialog.hide();
                //PedidoHelper.getActivityPedidoMain().finish();
            }
        });
        alertDialog.show();
    }
/*
    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = ActivityProdutoPedido.this.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityProdutoPedido.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ActivityProdutoPedido.this);
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
*/
    public void showAnimationFeedbackDialog(String mensagem, boolean sucesso, Runnable onConfirm) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showAnimationFeedbackDialog(
                this,
                "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_baseline_check_circle_outline_24_color_primary : R.drawable.ic_memory_alert,
                null, // Passando a cor personalizada
                () -> {
                    if (onConfirm != null) onConfirm.run();
                    //System.gc();
                }
        );
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso) {
        showFeedbackDialog(mensagem, sucesso, null);
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, Runnable onConfirm) {
        if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                this,
                "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                sucesso ? R.drawable.ic_alerta_mensagem_color_primary : R.drawable.ic_baseline_error_outline_24,
                null, // Passando a cor personalizada
                () -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                    //System.gc();
                }
        );
    }

}
