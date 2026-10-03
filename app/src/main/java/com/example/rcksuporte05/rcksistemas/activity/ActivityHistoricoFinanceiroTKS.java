package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MainAdapter;
import com.example.rcksuporte05.rcksistemas.fragment.FinanceiroGenericoFragment;
import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiroTKSFragment;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityHistoricoFinanceiroTKS extends AppCompatActivity implements View.OnClickListener, TabLayoutMediator.TabConfigurationStrategy {
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

    //@BindView(R.id.txvPontualidade)
    //TextView txvPontualidade;

    private ArrayList<String> titles;
    private Context context;
    private String idCliente, nomeCliente ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_financeiro_tks);
        context = this;
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitle("");

        titles = new ArrayList<>();
        titles.add("VENCIDO");
        titles.add("A VENCER");
        titles.add("QUITADO");
        btnFiltrar.setOnClickListener(this);
        btnFiltrar2.setOnClickListener(this);
        viewPager.setPageTransformer( new ZoomOutPageTransformer());

        try {
            idCliente = getIntent().getStringExtra("idCliente");
            toolbar.setSubtitle(getIntent().getStringExtra("nomeCliente"));
        } catch (NullPointerException e){
            idCliente = null;
        } catch ( Exception e) {
            idCliente = null;
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        btnFiltrar.setVisibility(View.INVISIBLE);
        btnFiltrar2.setVisibility(View.INVISIBLE);
        toolbar.setTitle("Voltar");
        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, viewPager, ActivityHistoricoFinanceiroTKS.this).attach();
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

    public void setViewPagerAdapter() {
        MainAdapter adapter = new MainAdapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HistoricoFinanceiroTKSFragment(idCliente == null ? "-" : idCliente, "V"));
        fragmentList.add(new HistoricoFinanceiroTKSFragment(idCliente == null ? "-" : idCliente, "AV"));
        fragmentList.add(new HistoricoFinanceiroTKSFragment(idCliente == null ? "-" : idCliente, "Q"));
        adapter.setData(fragmentList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));

    }
}