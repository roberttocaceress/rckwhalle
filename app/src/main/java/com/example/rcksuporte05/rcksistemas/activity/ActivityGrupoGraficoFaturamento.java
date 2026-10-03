package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.GraficoFaturamentoAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.GraficoFaturamentoAdapter2;
import com.example.rcksuporte05.rcksistemas.adapters.ImagemProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator2;
import me.relex.circleindicator.CircleIndicator3;

public class ActivityGrupoGraficoFaturamento extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DBHelper db = new DBHelper(this);
    private ViewPager2 mViewPager;
    private GraficoFaturamentoAdapter2 pagerAdapter;

    //private GraficoFaturamentoAdapter mViewPagerAdapter;
    private ArrayList<List<EvolucaoFaturamento>> mContents;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_grafico_faturamento);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Voltar");

        mViewPager =  findViewById(R.id.view_pager_grafico);
        mContents = new ArrayList<>();
        //List<EvolucaoFaturamento> listaEvolucaoFaturamento = new ArrayList<>();
        //mContents.add(listaEvolucaoFaturamento);
        //mContents.add(listaEvolucaoFaturamento);
        //mContents.add(listaEvolucaoFaturamento);

        pagerAdapter = new GraficoFaturamentoAdapter2(this);
        mViewPager.setAdapter(pagerAdapter);

        //mViewPagerAdapter = new GraficoFaturamentoAdapter( mContents, this);
        //mViewPager.setPageTransformer(new ViewPagerStack());

        mViewPager.setOffscreenPageLimit(4);
        //mViewPager.setAdapter(mViewPagerAdapter);
        CircleIndicator3 indicator = findViewById(R.id.circle_view_grafico);
        indicator.setViewPager(mViewPager);


        /*mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

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
                if ( state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = mContents.size();//;images.length;
                }
            }*/
        //});
    }


}