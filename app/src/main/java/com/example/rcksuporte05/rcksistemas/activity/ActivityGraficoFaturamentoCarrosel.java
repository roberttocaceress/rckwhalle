package com.example.rcksuporte05.rcksistemas.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rcksuporte05.rcksistemas.Helper.GraficoFaturamentoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.GraficoFaturamentoAdapter2;
import com.example.rcksuporte05.rcksistemas.adapters.GraficoFaturamentoCarroselAdapter;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator3;

public class ActivityGraficoFaturamentoCarrosel extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context context;
    private ViewPager2 mViewPager;
    private GraficoFaturamentoCarroselAdapter pagerAdapter;

    private List<EvolucaoFaturamento> listaEvolucaoFaturamento;
    private List<MesAnoMeta> listaMesAnoMeta;
    private List<MesSemana> listaMesSemana;
    private List<ParticipacaoFaturamento> listaParticipacaoFaturamento;
    private List<Financeiro> listaFinanceiro;
    private String tipoRelatorio;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_faturamento_carrosel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.circle_view_grafico);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rodape.getLayoutParams();
            params.bottomMargin = insets.bottom; // Aplica a altura da barra como margem
            rodape.setLayoutParams(params);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        context = this;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Voltar");

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        mViewPager =  findViewById(R.id.view_pager_grafico);

        tipoRelatorio = getIntent().getStringExtra("tipoRelatorio");
        if (tipoRelatorio != null) {
            GraficoFaturamentoHelper.setTipoRelatorio(tipoRelatorio);
        } else {
            tipoRelatorio = GraficoFaturamentoHelper.getTipoRelatorio();
        }

        if ( tipoRelatorio.equalsIgnoreCase("M") ) {
            listaMesAnoMeta = (List<MesAnoMeta>) getIntent().getSerializableExtra("lista");
            if (listaMesAnoMeta != null) {
                GraficoFaturamentoHelper.setListaMesAnoMeta(listaMesAnoMeta);
            } else {
                listaMesAnoMeta = GraficoFaturamentoHelper.getListaMesAnoMeta();
            }
            pagerAdapter = new GraficoFaturamentoCarroselAdapter(this, null, listaMesAnoMeta, null, null, null, tipoRelatorio);
        } else if ( tipoRelatorio.equalsIgnoreCase("S") ) {
            listaMesSemana = (List<MesSemana>) getIntent().getSerializableExtra("lista");
            if (listaMesSemana != null) {
                GraficoFaturamentoHelper.setListaMesSemana(listaMesSemana);
            } else {
                listaMesSemana = GraficoFaturamentoHelper.getListaMesSemana();
            }
            pagerAdapter = new GraficoFaturamentoCarroselAdapter(this, null, null, listaMesSemana, null, null, tipoRelatorio);
        } else if ( tipoRelatorio.equalsIgnoreCase("MM") || tipoRelatorio.equalsIgnoreCase("MG") ) {
            listaEvolucaoFaturamento = (List<EvolucaoFaturamento>) getIntent().getSerializableExtra("lista");
            if (listaEvolucaoFaturamento != null) {
                GraficoFaturamentoHelper.setListaEvolucaoFaturamento(listaEvolucaoFaturamento);
            } else {
                listaEvolucaoFaturamento = GraficoFaturamentoHelper.getListaEvolucaoFaturamento();
            }
            pagerAdapter = new GraficoFaturamentoCarroselAdapter(this, listaEvolucaoFaturamento, null, null, null, null, tipoRelatorio);
        } else if (tipoRelatorio.startsWith("FIN")) {
            listaFinanceiro = (List<Financeiro>) getIntent().getSerializableExtra("lista");
            if (listaFinanceiro != null) {
                GraficoFaturamentoHelper.setListaFinanceiro(listaFinanceiro);
            } else {
                listaFinanceiro = GraficoFaturamentoHelper.getListaFinanceiro();
            }
            pagerAdapter = new GraficoFaturamentoCarroselAdapter(this, null, null, null, null, listaFinanceiro, tipoRelatorio);
        } else {
            listaParticipacaoFaturamento = (List<ParticipacaoFaturamento>) getIntent().getSerializableExtra("lista");
            if (listaParticipacaoFaturamento != null) {
                GraficoFaturamentoHelper.setListaParticipacaoFaturamento(listaParticipacaoFaturamento);
            } else {
                listaParticipacaoFaturamento = GraficoFaturamentoHelper.getListaParticipacaoFaturamento();
            }
            pagerAdapter = new GraficoFaturamentoCarroselAdapter(this, null, null, null, listaParticipacaoFaturamento, null, tipoRelatorio);
        }
        mViewPager.setAdapter(pagerAdapter);

        //mViewPagerAdapter = new GraficoFaturamentoAdapter( mContents, this);
        //mViewPager.setPageTransformer(new ViewPagerStack());

        mViewPager.setOffscreenPageLimit(2);
        //mViewPager.setAdapter(mViewPagerAdapter);
        CircleIndicator3 indicator = findViewById(R.id.circle_view_grafico);
        indicator.setViewPager(mViewPager);

        executarAutoDemonstracaoCarrosel();
    }

    private android.os.Handler demoHandler = new android.os.Handler(android.os.Looper.getMainLooper());

    private void executarAutoDemonstracaoCarrosel() {
        boolean isDemo = getIntent().getBooleanExtra("isDemo", false);
        if (!isDemo || mViewPager == null) return;

        // Passo 1 (3.0s): Rola para a segunda página (Lista Detalhada)
        demoHandler.postDelayed(() -> {
            if (!isFinishing() && mViewPager != null && pagerAdapter != null && pagerAdapter.getItemCount() > 1) {
                mViewPager.setCurrentItem(1, true);
            }
        }, 3000);

        // Passo 2 (6.0s): Rola de volta para a primeira página (Gráfico)
        demoHandler.postDelayed(() -> {
            if (!isFinishing() && mViewPager != null && pagerAdapter != null && pagerAdapter.getItemCount() > 0) {
                mViewPager.setCurrentItem(0, true);
            }
        }, 6000);

        // Passo 3 (8.5s): Fecha automaticamente o carrossel e retorna ao painel principal
        demoHandler.postDelayed(() -> {
            if (!isFinishing()) {
                onBackPressed();
            }
        }, 8500);
    }

    @Override
    public void onBackPressed() {
        if (demoHandler != null) demoHandler.removeCallbacksAndMessages(null);
        ((Activity)context).finish();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        if (demoHandler != null) demoHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}