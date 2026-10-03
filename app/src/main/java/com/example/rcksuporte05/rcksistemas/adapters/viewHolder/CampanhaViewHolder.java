package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtIdCampanha)
    public TextView txtIdCampanha;
    @BindView(R.id.txtNomeCampanha)
    public TextView txtNomeCampanha;
    @BindView(R.id.txtPeriodo)
    public TextView txtPeriodo;
    @BindView(R.id.txtDescricao)
    public TextView txtDescricao;

    @BindView(R.id.layoutCampanha)
    public LinearLayout layoutCampanha;

    public CampanhaViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
