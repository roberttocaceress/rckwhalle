package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ProdutoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.txtCodigoBarra)
    public TextView txtCodigoBarra;

    @BindView(R.id.nomeListaProduto)
    public TextView nomeListaProduto;

    @BindView(R.id.precoProduto)
    public TextView precoProduto;

    @BindView((R.id.precoICMS))
    public TextView precoICMS;

    @BindView(R.id.txtUnidadeListaProduto)
    public TextView textUN;

    @BindView(R.id.idProduto)
    public TextView idProduto;

    @BindView(R.id.txtSaldoEstoque)
    public TextView txtSaldoEstoque;

    @BindView(R.id.txtNomeSubGrupo)
    public TextView txtNomeSubGrupo;

    @BindView(R.id.txtVazio)
    public TextView txtVazio;





    @BindView(R.id.txtNomeGrupo)
    public TextView txtNomeGrupo;

    @BindView(R.id.nomeTabelaPreco)
    public TextView nomeTabelaPreco;
    //nomeTabelaPreco

    @BindView(R.id.imageAnexo)
    public ImageView imageAnexo;

    //imagePromocaoVerde
    @BindView(R.id.imagePromocaoVerde)
    public ImageView imagePromocaoVerde;

    @BindView(R.id.imageCampanhaVerde)
    public ImageView imageCampanhaVerde;

    @BindView(R.id.imageCarrinhoVerde)
    public ImageView imageCarrinhoVerde;

    @BindView(R.id.imageCarrinhoAzul)
    public ImageView imageCarrinhoAzul;


    @BindView(R.id.itemView)
    public LinearLayout itemView;

    @BindView(R.id.lytTabelaPrecoICMS)
    public LinearLayout lytTabelaPrecoICMS;

    public ProdutoViewHolder(View itemView, DBHelper db, Context ctx) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        int tExtraFonte = 0;
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
        } catch (NullPointerException ignored){
        }
        if (tExtraFonte > 0) {
            nomeListaProduto.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewNomeLista1 : R.style.TextViewNomeLista2);
            idProduto.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            txtCodigoBarra.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            txtVazio.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            txtSaldoEstoque.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            textUN.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            txtNomeGrupo.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            txtNomeSubGrupo.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            nomeTabelaPreco.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
            precoICMS.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosLista1 : R.style.TextViewOutrosLista2);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
