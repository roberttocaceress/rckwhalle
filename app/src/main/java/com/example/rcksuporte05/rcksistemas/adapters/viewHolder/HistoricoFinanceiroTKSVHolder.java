package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoricoFinanceiroTKSVHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.txvNumeroDocumento)
    public TextView txvNumeroDocumento;

    @BindView(R.id.txvNumeroParcela)
    public TextView txvNumeroParcela;

    @BindView(R.id.txvDataEmissao)
    public TextView txvDataEmissao;

    @BindView(R.id.txvDataVencimento)
    public TextView txvDataVencimento;

    @BindView(R.id.txvPontualidade)
    public TextView txvPontualidade;


    @BindView(R.id.txvValorTotal)
    public TextView txvValorTotal;


    @BindView(R.id.corx)
    public View corx;

    public HistoricoFinanceiroTKSVHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
