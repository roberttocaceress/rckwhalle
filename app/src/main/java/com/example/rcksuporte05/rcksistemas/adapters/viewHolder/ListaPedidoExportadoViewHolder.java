package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaPedidoExportadoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.edtRazaoSocial)
    public TextInputEditText edtRazaoSocial;
    @BindView(R.id.edtNomeFantasia)
    public TextInputEditText edtNomeFantasia;
    @BindView(R.id.edtNumeroPedido)
    public TextInputEditText edtNumeroPedido;
    @BindView(R.id.edtDataEmissao)
    public TextInputEditText edtDataEmissao;
    @BindView(R.id.edtDataEntrega)
    public TextInputEditText edtDataEntrega;

    @BindView(R.id.edtValorProduto)
    public TextInputEditText edtValorProduto;
    @BindView(R.id.edtValorDesconto)
    public TextInputEditText edtValorDesconto;
    @BindView(R.id.edtValorTotal)
    public TextInputEditText edtValorTotal;

    @BindView(R.id.edtNomeVendedor)
    public TextInputEditText edtNomeVendedor;

    @BindView(R.id.btnConfirmar)
    public Button btnConfirmar;
    public ListaPedidoExportadoViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {
    }
}
