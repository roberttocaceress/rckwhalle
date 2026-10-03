package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import java.text.BreakIterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParticipacaoFaturamentoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txvNome)
    public TextView txvNome;

    @BindView(R.id.txvValorBruto)
    public TextView txvValorBruto;

    @BindView(R.id.txvValorLiquido)
    public TextView txvValorLiquido;


    public ParticipacaoFaturamentoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
