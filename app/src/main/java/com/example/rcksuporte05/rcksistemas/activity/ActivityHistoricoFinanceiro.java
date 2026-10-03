package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterFinanceiro;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHistoricoFinanceiro extends AppCompatActivity {
    @BindView(R.id.vp_tabs)
    public ViewPager mViewPager;
    @BindView(R.id.toolbarFrags)
    Toolbar toolbar;
    @BindView(R.id.stl_tabs)
    SlidingTabLayout mSlidingTabLayout;
    private TabsAdapterFinanceiro tabsAdapterFinanceiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_financeiro_2025);
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
        toolbar.setTitle("Histórico financeiro");
        toolbar.setSubtitle(ClienteHelper.getCliente().getNome_cadastro());
        tabsAdapterFinanceiro = new TabsAdapterFinanceiro(getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapterFinanceiro);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.RED);
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mSlidingTabLayout.setSelectedIndicatorColors(Color.RED);
                        break;
                    case 1:
                        mSlidingTabLayout.setSelectedIndicatorColors(Color.YELLOW);
                        break;
                    case 2:
                        mSlidingTabLayout.setSelectedIndicatorColors(Color.GREEN);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
