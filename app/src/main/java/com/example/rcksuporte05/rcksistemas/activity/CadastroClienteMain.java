package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.rcksuporte05.rcksistemas.BO.CadastroAnexoBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterCliente;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroClienteMain extends AppCompatActivity {
    @BindView(R.id.vp_tabs)
    public ViewPager mViewPager;
    @BindView(R.id.toolbarFrags)
    Toolbar toolbar;
    @BindView(R.id.stl_tabs)
    SlidingTabLayout mSlidingTabLayout;
    private TabsAdapterCliente tabsAdapterCliente;

    private String[] titles = {"Geral", "Endereço", "Email NF-E", "Fotos", "Observações"};
    private Utilitaria util = new Utilitaria();
    DBHelper db = new DBHelper(this);
    Configuracao configuracao = new Configuracao();

    String razao_social = "";
    String nome_fantasia = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.toolbarFrags);
            toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.vp_tabs);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        toolbar.setTitle("Cadastro de Clientes");
        getConfiguracao();
        try {
            if (configuracao.getGira_tela_cliente().isEmpty() || configuracao.getGira_tela_cliente().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        tabsAdapterCliente = new TabsAdapterCliente(getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapterCliente);

        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener( new PageChangeListener());

        setCadastroAnexo();
        ClienteHelper.setCadastroClienteMain(this);

        if (getIntent().getIntExtra("novo", 0) >= 1) {
            mSlidingTabLayout.setVisibility(View.GONE);
            mViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            try {
                if ( getIntent().getStringExtra("razao_social") != null || !getIntent().getStringExtra("razao_social").isEmpty()) {
                    ClienteHelper.getCliente().setNome_cadastro(getIntent().getStringExtra("razao_social"));
                    ClienteHelper.getCliente().setNome_fantasia(getIntent().getStringExtra("nome_fantasia"));
                    ClienteHelper.getCliente().setEndereco(getIntent().getStringExtra("tipo_logradouro") + " " + getIntent().getStringExtra("logradouro"));
                    ClienteHelper.getCliente().setEndereco_numero(getIntent().getStringExtra("numero"));
                    ClienteHelper.getCliente().setEndereco_bairro(getIntent().getStringExtra("bairro"));
                    ClienteHelper.getCliente().setEndereco_complemento(getIntent().getStringExtra("complemento"));
                    ClienteHelper.getCliente().setTelefone_principal(getIntent().getStringExtra("ddd1") + getIntent().getStringExtra("telefone1"));
                    ClienteHelper.getCliente().setTelefone_dois( getIntent().getStringExtra("ddd2") == null ? "" : getIntent().getStringExtra("ddd2")  + getIntent().getStringExtra("telefone2") );
                    ClienteHelper.getCliente().setEmail_principal(getIntent().getStringExtra("email"));
                    ClienteHelper.getCliente().setId_pais(Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("codigo_pais"))));
                    ClienteHelper.getCliente().setEndereco_uf(getIntent().getStringExtra("sigla_estado"));
                    ClienteHelper.getCliente().setNome_municipio(getIntent().getStringExtra("nome_cidade"));
                    ClienteHelper.getCliente().setEndereco_cep(getIntent().getStringExtra("cep"));
                    if ( getIntent().getStringExtra("insc_estadual") != null && !Objects.requireNonNull(getIntent().getStringExtra("insc_estadual")).isEmpty()) {
                        ClienteHelper.getCliente().setInscri_estadual(getIntent().getStringExtra("insc_estadual"));
                    }


                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        try {
            if (!getIntent().getBooleanExtra("verificado", true))
                util.showMsgAlerta("Houve um erro ao consultar o servidor, se persistir, entre em contato com o pessoal responsável.", CadastroClienteMain.this);
        } catch ( NullPointerException e ) {
        } catch ( Exception e) {
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    private void setCadastroAnexo() {
        try {
            if (ClienteHelper.getCliente().getId_cadastro() > 0) {
                if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                    final ProgressDialog progress = new ProgressDialog(this);
                    progress.setTitle("Aguarde");
                    progress.setMessage("Carregando anexos do cliente");
                    progress.setCancelable(false);
                    progress.show();
                    final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                    Thread a = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(CadastroClienteMain.this, ClienteHelper.getCliente().getId_cadastro());
                            ClienteHelper.setListaCadastroAnexo(listaCadastroAnexo);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();
                                }
                            });
                        }
                    });
                    a.start();
                }
            } else {
                if (ClienteHelper.getCliente().getListaCadastroAnexo().size() > 0)
                    ClienteHelper.setListaCadastroAnexo(ClienteHelper.getCliente().getListaCadastroAnexo());
            }
        } catch (NullPointerException e) {
            finish();
        }

    }

    public class PageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (ClienteHelper.getCadastroCliente8() != null)
                ClienteHelper.getCadastroCliente8().finishActionMode();
            switch (position) {
                case 0:
                    if (ClienteHelper.getCadastroCliente1() != null) {
                        ClienteHelper.getCadastroCliente1().addDadosFrame();
                        if (getIntent().getIntExtra("novo", 0) >= 1)
                            toolbar.setTitle(titles[position]);
                    }
                    break;
                case 1:
                    if (ClienteHelper.getCadastroCliente2() != null) {
                        ClienteHelper.getCadastroCliente2().addDadosFrame();
                        if (getIntent().getIntExtra("novo", 0) >= 1)
                            toolbar.setTitle(titles[position]);
                    }
                    break;
                case 2:
                case 4:
                    if (getIntent().getIntExtra("novo", 0) >= 1)
                        toolbar.setTitle(titles[position]);
                    break;
                case 3:
                    if (ClienteHelper.getCadastroCliente7() != null) {
                        ClienteHelper.getCadastroCliente7().addDadosDaFrame();
                        if (getIntent().getIntExtra("novo", 0) >= 1)
                            toolbar.setTitle(titles[position]);
                    }
                    break;
                case 5:
                    if (ClienteHelper.getCadastroCliente9() != null) {
                        ClienteHelper.getCadastroCliente9().addDadosDaFrame();
                        if (getIntent().getIntExtra("novo", 0) >= 1)
                            toolbar.setTitle(titles[position]);
                    }
                    break;
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (ClienteHelper.getCadastroCliente8() != null)
                ClienteHelper.getCadastroCliente8().finishActionMode();
            switch (position) {
                case 0:
                    if (ClienteHelper.getCadastroCliente1() != null)
                        ClienteHelper.getCadastroCliente1().addDadosFrame();
                    break;
                case 1:
                    if (ClienteHelper.getCadastroCliente2() != null)
                        ClienteHelper.getCadastroCliente2().addDadosFrame();
                    break;
                case 3:
                    if (ClienteHelper.getCadastroCliente7() != null)
                        ClienteHelper.getCadastroCliente7().addDadosDaFrame();
                    break;
                case 5:
                    if (ClienteHelper.getCadastroCliente9() != null)
                        ClienteHelper.getCadastroCliente9().addDadosDaFrame();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() != 0)
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
        else {
            try {
                if (!ClienteHelper.getCliente().getF_cliente().equals("S"))
                    ClienteHelper.clear();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getIntent().getIntExtra("novo", 0) < 1) {
                    if (mViewPager.getCurrentItem() != 0)
                        mViewPager.setCurrentItem(0);
                    else
                        finish();
                } else {
                    if (mViewPager.getCurrentItem() != 0)
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                    else
                        finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
       super.onDestroy();
    }


}



/*mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (ClienteHelper.getCadastroCliente8() != null)
                    ClienteHelper.getCadastroCliente8().finishActionMode();
                switch (position) {
                    case 0:
                        if (ClienteHelper.getCadastroCliente1() != null) {
                            ClienteHelper.getCadastroCliente1().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1)
                                toolbar.setTitle(titles[position]);
                        }
                        break;
                    case 1:
                        if (ClienteHelper.getCadastroCliente2() != null) {
                            ClienteHelper.getCadastroCliente2().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1)
                                toolbar.setTitle(titles[position]);
                        }
                        break;
                    case 2:
                        if (getIntent().getIntExtra("novo", 0) >= 1)
                            toolbar.setTitle(titles[position]);
                        break;
                    case 3:
                        if (ClienteHelper.getCadastroCliente7() != null) {
                            ClienteHelper.getCadastroCliente7().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1)
                                toolbar.setTitle(titles[position]);

                        }
                        break;
                    case 4:
                        if (getIntent().getIntExtra("novo", 0) >= 1)
                            toolbar.setTitle(titles[position]);
                        break;
                    case 5:
                        if (ClienteHelper.getCadastroCliente9() != null) {
                            ClienteHelper.getCadastroCliente9().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1)
                                toolbar.setTitle(titles[position]);
                        }
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (ClienteHelper.getCadastroCliente8() != null)
                    ClienteHelper.getCadastroCliente8().finishActionMode();
                switch (position) {
                    case 0:
                        if (ClienteHelper.getCadastroCliente1() != null)
                            ClienteHelper.getCadastroCliente1().inserirDadosDaFrame();
                        break;
                    case 1:
                        if (ClienteHelper.getCadastroCliente2() != null)
                            ClienteHelper.getCadastroCliente2().inserirDadosDaFrame();
                        break;
                    case 3:
                        if (ClienteHelper.getCadastroCliente7() != null)
                            ClienteHelper.getCadastroCliente7().inserirDadosDaFrame();
                        break;
                    case 5:
                        if (ClienteHelper.getCadastroCliente9() != null)
                            ClienteHelper.getCadastroCliente9().inserirDadosDaFrame();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        */

