package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MainAdapter;
import com.example.rcksuporte05.rcksistemas.fragment.FinanceiroGenericoFragment;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFinanceiroCobranca extends AppCompatActivity implements View.OnClickListener, TabLayoutMediator.TabConfigurationStrategy {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btnFiltrar)
    Button btnFiltrar;

    @BindView(R.id.btnFiltrar2)
    Button btnFiltrar2;

    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    private ArrayList<String> titles;
    private Context context;
    private String debitoCredito;
    private String idCliente, dataIni, dataFin, periodoStatus, tipoRelatorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_financeiro_cobranca);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.viewPager);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitle("");
        titles = new ArrayList<>();
        titles.add("TITULOS");
        titles.add("RESUMO");
        //titles.add("QUITADO");
        //titles.add("TODOS TESTE");
        btnFiltrar.setOnClickListener(this);
        btnFiltrar2.setOnClickListener(this);
        viewPager.setPageTransformer( new ZoomOutPageTransformer());
        debitoCredito = getIntent().getStringExtra("debitoCredito");
        try {
            dataIni = getIntent().getStringExtra("dataInicial");
            dataFin = getIntent().getStringExtra("dataFinal");
            periodoStatus = getIntent().getStringExtra("periodoStatus");
            if (dataIni.equalsIgnoreCase(dataFin)) {
                titles.clear();
                if ( dataIni.equalsIgnoreCase("-") && dataFin.equalsIgnoreCase("-"))
                    titles.add("TITULOS " + getIntent().getStringExtra("nomeStatus"));
                else
                    titles.add("TITULOS DE " + MascaraUtil.formataData(dataIni, "/", 8));
                titles.add("RESUMO");
            }else {
                titles.clear();
                titles.add("TITULOS DE " + MascaraUtil.formataData(dataIni, "/", 8) + " A " + MascaraUtil.formataData(dataFin, "/", 8) );
                titles.add("RESUMO");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            idCliente = getIntent().getStringExtra("idCliente");
        } catch (NullPointerException e){
            idCliente = null;
        } catch ( Exception e) {
            idCliente = null;
        }
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (idCliente == null) {
            try {
                dataIni = getIntent().getStringExtra("dataInicial");
                dataFin = getIntent().getStringExtra("dataFinal");
                periodoStatus = getIntent().getStringExtra("periodoStatus");
            } catch (NullPointerException ex) {
                finish();
            } catch (Exception e) {
                finish();
            }

            setViewPagerAdapter(dataIni, dataFin, periodoStatus, debitoCredito, "OFF");
            new TabLayoutMediator(tabLayout, viewPager, ActivityFinanceiroCobranca.this).attach();
            toolbar.setTitle(" ");

        } else {
            tipoRelatorio = getIntent().getStringExtra("tipoRelatorio");
            btnFiltrar.setVisibility(View.INVISIBLE);
            btnFiltrar2.setVisibility(View.INVISIBLE);
            toolbar.setTitle("Voltar");
            setViewPagerAdapter("-", "-", "E", debitoCredito, tipoRelatorio);
            new TabLayoutMediator(tabLayout, viewPager, ActivityFinanceiroCobranca.this).attach();
        }

    }
    @Override
    public void onClick(View v) {
        switch ( v.getId()){
            case R.id.btnFiltrar:
            case R.id.btnFiltrar2:
                onBackPressed();
                break;
        }
    }

    public void setViewPagerAdapter(String dataIni, String dataFin, String periodoStatus, String debito_credito, String tipoRelatorio) {
        MainAdapter adapter = new MainAdapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FinanceiroGenericoFragment(dataIni, dataFin, periodoStatus, debito_credito, idCliente == null ? "-" : idCliente, "T", tipoRelatorio));
        fragmentList.add(new FinanceiroGenericoFragment(dataIni, dataFin, periodoStatus, debito_credito, idCliente == null ? "-" : idCliente, "R", tipoRelatorio));
        adapter.setData(fragmentList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }
}