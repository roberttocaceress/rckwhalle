package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NovidadeViewHolder extends RecyclerView.ViewHolder {




    @BindView(R.id.txvTitulo)
    public TextView txvTitulo;

    @BindView(R.id.btnShow)
    public ImageButton btnShow;


    public NovidadeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
