package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutoPedido;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ProdutoPedidoViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener, View.OnLongClickListener*/ {

    @BindView(R.id.imageAnexo)
    public ImageView imageAnexo;
    @BindView(R.id.idPosition)
    public TextView idPosition;

    @BindView(R.id.idProduto)
    public TextView idProduto;

    @BindView(R.id.txtCodigoBarra)
    public TextView txtCodigoBarra;
    //@BindView(R.id.idProduto)
    //public TextView idProduto;

    @BindView(R.id.nomeListaProduto)
    public TextView nomeListaProduto;

    @BindView(R.id.precoProduto)
    public TextView precoProduto;

    @BindView(R.id.textViewUnidadeMedida)
    public TextView textViewUnidadeMedida;

    @BindView(R.id.textViewCampanha)
    public TextView textViewCampanha;

    @BindView(R.id.nomeCampanha)
    public TextView nomeCampanha;

    @BindView(R.id.txtDesconto)
    public TextView txtDesconto;

    @BindView(R.id.itemView)
    public LinearLayout itemView;

    @BindView(R.id.btnExcluirProduto)
    public Button btnExcluirProduto;

    @BindView(R.id.btnAnaliseEstoque)
    public Button btnAnaliseEstoque;

    @BindView(R.id.lytDesxctoPreco)
    public LinearLayout lytDesxctoPreco;

    @BindView(R.id.lytCampanha)
    public LinearLayout lytCampanha;

    ListaAdapterProdutoPedido.ProdutoPedidoAdapterListener listener;

    public ProdutoPedidoViewHolder(View itemView, DBHelper db, Context ctx) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        int tExtraFonte = 0;
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
        } catch (NullPointerException ignored){
        }
        if (tExtraFonte > 0) {
            idPosition.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10 + tExtraFonte);
            nomeListaProduto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            textViewUnidadeMedida.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 + tExtraFonte);
            precoProduto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 + tExtraFonte);
            txtDesconto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 + tExtraFonte);
            textViewCampanha.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 + tExtraFonte);
            nomeCampanha.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 + tExtraFonte);
            btnAnaliseEstoque.getLayoutParams().width  = btnAnaliseEstoque.getLayoutParams().width + (tExtraFonte  * 4);
            btnAnaliseEstoque.getLayoutParams().height  = btnAnaliseEstoque.getLayoutParams().height + (tExtraFonte  * 4);
            btnExcluirProduto.getLayoutParams().width  = btnExcluirProduto.getLayoutParams().width + (tExtraFonte  * 4);
            btnExcluirProduto.getLayoutParams().height = btnExcluirProduto.getLayoutParams().height + (tExtraFonte * 4);

        }
    }
}
