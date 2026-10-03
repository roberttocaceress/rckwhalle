package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinanceiroCobrancaVHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.inputNomeFavorecido)
    public TextInputLayout inputNomeFavorecido;
    @BindView(R.id.nomeFavorecido)
    public TextView nomeFavorecido;


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

    @BindView(R.id.corx)
    public View corx;

    @BindView(R.id.inputValorTotal)
    public TextInputLayout inputValorTotal;
    @BindView(R.id.valorTotal)
    public TextView valorTotal;



    public FinanceiroCobrancaVHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
