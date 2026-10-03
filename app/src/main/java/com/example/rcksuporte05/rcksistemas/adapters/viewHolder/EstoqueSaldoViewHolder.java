package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstoqueSaldoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.idProduto)
    public TextView idProduto;

    @BindView(R.id.txtCodigoBarra)
    public TextView txtCodigoBarra;

    @BindView(R.id.nomeListaProduto)
    public TextView nomeListaProduto;


    @BindView(R.id.txtSaldoEstoque)
    public TextView txtSaldoEstoque;

    @BindView(R.id.txtSaldoReservado)
    public TextView txtSaldoReservado;

    @BindView(R.id.txtUnidadeListaProduto)
    public TextView textUN;

    @BindView(R.id.imgProdutoAtivoBloqueado)
    public ImageView imgProdutoAtivoBloqueado;

    public EstoqueSaldoViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
