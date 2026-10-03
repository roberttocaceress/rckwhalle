package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinanceiroClienteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /*
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

     */



    @BindView(R.id.inputNumeroDocumento)
    public TextInputLayout inputNumeroDocumento;
    @BindView(R.id.numeroDocumento)
    public TextView numeroDocumento;


    @BindView(R.id.inputNumeroParcela)
    public TextInputLayout inputNumeroParcela;
    @BindView(R.id.numeroParcela)
    public TextView numeroParcela;

    @BindView(R.id.inputDataEmissao)
    public TextInputLayout inputDataEmissao;
    @BindView(R.id.dataEmissao)
    public TextView dataEmissao;

    @BindView(R.id.inputDataVencimento)
    public TextInputLayout inputDataVencimento;
    @BindView(R.id.dataVencimento)
    public TextView dataVencimento;

    @BindView(R.id.inputDataBaixa)
    public TextInputLayout inputDataBaixa;
    @BindView(R.id.dataBaixa)
    public TextView dataBaixa;



    @BindView(R.id.inputValorTotal)
    public TextInputLayout inputValorTotal;
    @BindView(R.id.valorTotal)
    public TextView valorTotal;

    @BindView(R.id.valorJuros)
    public TextView valorJuros;

    @BindView(R.id.valorPago)
    public TextView valorPago;

    @BindView(R.id.valorSaldo)
    public TextView valorSaldo;


    @BindView(R.id.especieDocumento)
    public TextView especieDocumento;

    public FinanceiroClienteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View v) {

    }
}
