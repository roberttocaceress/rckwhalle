package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class TabelaAnaliseViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    //@BindView(R.id.edtNomeProduto)
    @BindView(R.id.edtNumeroPedido)
    public TextView txtIdPedido;

    @BindView(R.id.txtNomeCliente)
    public TextView txtNomeCliente;

    @BindView(R.id.txtFantasiaCliente)
    public TextView txtFantasiaCliente;

    @BindView(R.id.txtDataEmissaoPedido)
    public TextView txtDataEmissaoPedido;

    @BindView(R.id.txtDataEntrega)
    public TextView txtDataEntrega;




    ListaPedidoAdapter.PedidoAdapterListener listener;

    public TabelaAnaliseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }
}
