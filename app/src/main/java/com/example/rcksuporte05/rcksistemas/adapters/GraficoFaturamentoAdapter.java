package com.example.rcksuporte05.rcksistemas.adapters;

import static android.content.Context.ACTIVITY_SERVICE;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProdutoImagemZoom;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class GraficoFaturamentoAdapter extends PagerAdapter implements View.OnClickListener {

    private Context context;
    public List<List<EvolucaoFaturamento>> lista;
    private boolean isMaximized = false;
    private boolean listaVisivel, graficoVivel;
    public int positionselected;
    Button btnFlipCard;
    FrameLayout caixa_layout_grafico, caixa_layout_resumo;
    ShimmerFrameLayout shimmerLayout;
    RecyclerView recyclerView;
    public GraficoFaturamentoAdapter(List<List<EvolucaoFaturamento>> lista,Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject( View view,  Object object) {
        return view == (ConstraintLayout)object;
    }
    public void setImagemListada(int position) {
        positionselected = position;
        btnFlipCard.callOnClick();
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_view_grafico_faturamento, container, false);
        container.addView(view);

        caixa_layout_grafico =  view.findViewById(R.id.caixa_layout_grafico);
        caixa_layout_resumo  =  view.findViewById(R.id.caixa_layout_resumo);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView =  view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);


        btnFlipCard =  view.findViewById(R.id.btnFlipCard);
        btnFlipCard.setText("Aguarde...");
        btnFlipCard.setEnabled(false);
        btnFlipCard.setOnClickListener(this);

        graficoVivel = false;
        listaVisivel = true;

        isMaximized = true;
        btnFlipCard.setEnabled(true);
        //btnFlipCard.callOnClick();

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnFlipCard) {
            //Toast.makeText(context, positionselected, Toast.LENGTH_SHORT).show();
            if (!isMaximized) {
                caixa_layout_grafico.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryTransparente05));
                flipCard(context, caixa_layout_grafico, caixa_layout_resumo, isMaximized);
                isMaximized = !isMaximized;
                if (!graficoVivel)
                    btnFlipCard.setText("Visualizar resumo");
                else
                    btnFlipCard.setText("Visualizar gráfico");
                btnFlipCard.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0/*R.drawable.ic_view_list_24_24*/);
                graficoVivel = true;
                listaVisivel = false;
            } else {
                caixa_layout_resumo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryTransparente05));
                flipCard(context, caixa_layout_resumo, caixa_layout_grafico, isMaximized);
                isMaximized = !isMaximized;
                if ( !graficoVivel)
                    btnFlipCard.setText("Visualizar resumo");
                else
                    btnFlipCard.setText("Visualizar gráfico");
                btnFlipCard.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0/* R.drawable.ic_pie_chart_24_24*/);
                graficoVivel = false;
                listaVisivel = true;
            }
        }
    }
    @SuppressLint("ResourceType")
    private void  flipCard(Context context, View visibleView, View inVisibleView, boolean isMaximized) {
        try {
            inVisibleView.setVisibility(View.INVISIBLE);
//            visibleView.setVisibility(View.VISIBLE);
            @SuppressLint("ResourceType") AnimatorSet flipOutAnimatorSet =  (AnimatorSet)  AnimatorInflater.loadAnimator(
                    context,
                    R.anim.flip_out
            );
            @SuppressLint("ResourceType") AnimatorSet flipInAnimationSet;

            flipOutAnimatorSet.setTarget(inVisibleView);
            //if ( isMaximized) {
            flipInAnimationSet = (AnimatorSet) AnimatorInflater.loadAnimator(
                    context,
                    R.anim.flip_in_right
            );

            flipInAnimationSet.setTarget(visibleView);
            flipOutAnimatorSet.start();
            flipInAnimationSet.start();
            visibleView.setVisibility(View.VISIBLE);
            if ( visibleView.getId() == caixa_layout_grafico.getId()) {
                //barChart.notifyDataSetChanged();
                //barChart.invalidate();
                //barChart.animateXY(1500, 1500);
            }
            Handler handler = new Handler();
            Runnable runnable = () -> {
                caixa_layout_resumo.setBackgroundColor(0);
                caixa_layout_grafico.setBackgroundColor(0);
            };
            handler.postDelayed(runnable, 1000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
