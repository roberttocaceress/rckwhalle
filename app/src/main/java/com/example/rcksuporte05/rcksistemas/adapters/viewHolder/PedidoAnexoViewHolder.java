package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PedidoAnexoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemView)
    public RelativeLayout itemView;

    /*
    @BindView(R.id.txtPosition)
    public TextView txtPosition;
    */
    @BindView(R.id.imAnexo)
    public ImageView imAnexo;

    /*
    @BindView(R.id.txtNomeAnexo)
    public TextView txtNomeAnexo;

     */

    public PedidoAnexoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
