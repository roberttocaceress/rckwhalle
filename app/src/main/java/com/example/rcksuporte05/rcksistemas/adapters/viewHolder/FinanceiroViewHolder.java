package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinanceiroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    @BindView(R.id.finDocumento)
    public TextView finDocumento;

    @BindView(R.id.finParcela)
    public TextView finParcela;

    @BindView(R.id.finEmissao)
    public TextView finEmissao;

    @BindView(R.id.finVencimento)
    public TextView finVencimento;

    @BindView(R.id.finPagamento)
    public TextView finPagamento;

    @BindView(R.id.finEspecie)
    public TextView finEspecie;

    @BindView(R.id.finValorDoc)
    public TextView finValorDoc;

    @BindView(R.id.finJuroDesconto)
    public TextView finJuroDesconto;


    @BindView(R.id.txvPagamento)
    public  TextView txvPagamento;

    @BindView(R.id.finValorPago)
    public TextView finValorPago;


    @BindView(R.id.finValorSaldo)
    public TextView finValorSaldo;

    @BindView(R.id.itemView)
    public LinearLayout itemView;

    public FinanceiroViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View v) {

    }
}
