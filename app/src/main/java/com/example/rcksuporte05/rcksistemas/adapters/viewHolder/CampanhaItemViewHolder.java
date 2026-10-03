package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtIdProdutoLinha)
    public TextView txtIdProdutoLinha;

    @BindView(R.id.edtNomeProdutoLinha)
    public TextView edtNomeProdutoLinha;

    @BindView(R.id.edtQuantidadeLinha)
    public TextView edtQuantidadeLinha;

    @BindView(R.id.txtIdProdutoBrinde)
    public TextView txtIdProdutoBrinde;

    @BindView(R.id.edtNomeProdutoBrinde)
    public TextView edtNomeProdutoBrinde;

    @BindView(R.id.edtQuantidadeBrinde)
    public TextView edtQuantidadeBrinde;

    @BindView(R.id.txtTipoCampanha1)
    public TextView txtTipoCampanha1;

    public CampanhaItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
