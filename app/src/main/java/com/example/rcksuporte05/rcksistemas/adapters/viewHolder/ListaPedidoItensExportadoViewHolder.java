package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaPedidoItensExportadoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.edtNomeProduto)
    public TextInputEditText edtNomeProduto;

    @BindView(R.id.edtQtdeProduto)
    public TextInputEditText edtQtdeProduto;

    @BindView(R.id.edtValorProduto)
    public TextInputEditText edtValorProduto;

    @BindView(R.id.edtDescontoProduto)
    public TextInputEditText edtDescontoProduto;

    @BindView(R.id.edtTotalProduto)
    public TextInputEditText edtTotalProduto;


    @BindView(R.id.edtCodigoProduto)
    public TextInputEditText edtCodigoProduto;

    @BindView(R.id.edtEANProduto)
    public TextInputEditText edtEANProduto;

    public ListaPedidoItensExportadoViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void onClick(View view) {

    }
}
