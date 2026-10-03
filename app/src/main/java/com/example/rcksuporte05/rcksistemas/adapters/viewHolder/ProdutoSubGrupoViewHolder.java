package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdutoSubGrupoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.cdv_produto_sub_grupo)
    public CardView cdv_produto_sub_grupo;

    @BindView(R.id.img_produto_sub_grupo)
    public ImageView img_produto_sub_grupo;

    @BindView(R.id.lyt_produto_sub_grupo)
    public LinearLayout lyt_produto_sub_grupo;

    @BindView(R.id.txv_id_sub_grupo)
    public TextView txv_id_sub_grupo;

    @BindView(R.id.txt_nome_sub_grupo)
    public TextView txt_nome_sub_grupo;

    @BindView(R.id.txt_total_produto)
    public TextView txt_total_produto;

    public ProdutoSubGrupoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    @Override
    public void onClick(View view) {

    }
}
