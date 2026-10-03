package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.content.Context;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    //@BindView(R.id.edtNomeProduto)
    @BindView(R.id.edtNumeroPedido)
    public TextView txtIdPedido;

    //@BindView(R.id.edtQtdeItens)
    //public TextView txvQtdeItens;



    @BindView(R.id.txtNomeCliente)
    public TextView txtNomeCliente;

    @BindView(R.id.txtEmpresaPedido)
    public TextView txtEmpresaPedido;

    @BindView(R.id.txtFantasiaCliente)
    public TextView txtFantasiaCliente;

    @BindView(R.id.txtPrecoPedido)
    public TextView txtPrecoPedido;

    @BindView(R.id.txtDataEntrega)
    public TextView txtDataEntrega;

    @BindView(R.id.txtDataEmissaoPedido)
    public TextView txtDataEmissaoPedido;



    @BindView(R.id.item_lista_pedido)
    public LinearLayout itemListaPedido;

    @BindView(R.id.cor)
    public View cor;

    //@BindView(R.id.lyExcluir)
   // public LinearLayout lyExcluir;

    //@BindView(R.id.lyEnvia)
    //public LinearLayout lyEnvia;

    //@BindView(R.id.lyCompartilhar)
   // public LinearLayout lyCompartilhar;

    @BindView(R.id.btnExcluir)
    public Button btnExcluir;

    @BindView(R.id.btnEnviar)
    public Button btnEnviar;

    @BindView(R.id.btnDuplic)
    public Button btnDuplic;

    @BindView(R.id.btnCompartilhar)
    public Button btnCompartilhar;

    @BindView(R.id.btnRastreio)
    public Button btnRastreio;

    @BindView(R.id.btnCopiaSegura)
    public Button btnCopiaSegura;

    @BindView(R.id.txtCondicaoPagamento)
    public TextView txtCondicaoPagamento;

    @BindView(R.id.txtOperacao)
    public TextView txtOperacao;

    @BindView(R.id.txtMoeda)
    public TextView txtMoeda;

    @BindView(R.id.txtStatus)
    public TextView txtStatus;

    @BindView(R.id.img_data_emissao)
    public ImageView img_data_emissao;

    @BindView(R.id.img_data_entrega)
    public ImageView img_data_entrega;


    @BindView(R.id.abandonado)
    public LinearLayout abandonado;

    @BindView(R.id.txvAbandonado)
    public TextView txvAbandonado;

    @BindView(R.id.lyEntrega)
    public LinearLayout lyEntrega;

    //@BindView(R.id.lyLinhaTempo)
    //public LinearLayout lyLinhaTempo;

    @BindView(R.id.btnLinhaTempo)
    public Button btnLinhaTempo;

    @BindView(R.id.itemView)
    public LinearLayout itemView;

    ListaPedidoAdapter.PedidoAdapterListener listener;

    public PedidoViewHolder(View itemView, DBHelper db, Context ctx) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        int tExtraFonte = 0;
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
        } catch (NullPointerException ignored){
        }
        if (tExtraFonte > 0) {
            txtNomeCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewNomeLista1 : R.style.TextViewNomeLista2);
            txtFantasiaCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtDataEntrega.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtDataEmissaoPedido.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtIdPedido.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtPrecoPedido.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18 + tExtraFonte);
            txtStatus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10 + tExtraFonte);
            img_data_emissao.getLayoutParams().width  = img_data_emissao.getLayoutParams().width + (tExtraFonte  * 4);
            img_data_emissao.getLayoutParams().height = img_data_emissao.getLayoutParams().height + (tExtraFonte * 4);
            img_data_entrega.getLayoutParams().width  = img_data_entrega.getLayoutParams().width + (tExtraFonte  * 4);
            img_data_entrega.getLayoutParams().height = img_data_entrega.getLayoutParams().height + (tExtraFonte * 4);

            /*txtDataUltimaCompra.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            telefoneCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            cnpjCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            cidadeCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtClienteAguarda.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            idCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            nomeTabelaPreco.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtCategoria.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtSegmento.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);*/
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }
}
