package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 29/11/2017.
 */

public class ClientesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.nomeListaCliente)
    public TextView textViewNome;

    @BindView(R.id.txtDataUltimaCompra)
    public TextView txtDataUltimaCompra;

    @BindView(R.id.textViewNomeFantasia)
    public TextView textViewNomeFantasia;

    @BindView(R.id.telefoneCliente)
    public TextView telefoneCliente;

    @BindView(R.id.cnpjCliente)
    public TextView cnpjCliente;

    @BindView(R.id.cidadeCliente)
    public TextView cidadeCliente;

    @BindView(R.id.txtClienteAguarda)
    public TextView txtClienteAguarda;

    @BindView(R.id.idCliente)
    public TextView idCliente;

    @BindView(R.id.nomeTabelaPreco)
    public TextView nomeTabelaPreco;

    @BindView(R.id.txtCategoria)
    public TextView txtCategoria;

    @BindView(R.id.txtSegmento)
    public TextView txtSegmento;


    //Setor
    @BindView(R.id.imStatus)
    public ImageView imStatus;

    @BindView(R.id.imgFinanceiroVencendo)
    public ImageView imgFinanceiroVencendo;

    @BindView(R.id.imgFinanceiroVencido)
    public ImageView imgFinanceiroVencido;

    @BindView(R.id.imgPedidoAnalise)
    public ImageView imgPedidoAnalise;

    @BindView(R.id.imgPromocaoCliente)
    public ImageView imgPromocaoCliente;

    @BindView(R.id.imgClienteBloqueado)
    public ImageView imgClienteBloqueado;

    @BindView(R.id.lyCliente)
    public LinearLayout itemView;


    @BindView(R.id.botoes)
    public LinearLayout lyBotoes;

    @BindView(R.id.lyDownload)
    public LinearLayout lyDownload;

    @BindView(R.id.lyChamada)
    public LinearLayout lyChamada;

    @BindView(R.id.lyGps)
    public LinearLayout lyGps;

    @Nullable
    @BindView(R.id.lyEmail)
    public LinearLayout lyEmail;

    @Nullable
    @BindView(R.id.lyFinanceiro)
    public LinearLayout lyFinanceiro;

    @BindView(R.id.lyNovoPedido)
    public LinearLayout lyNovoPedido;

    @BindView(R.id.btnDownload)
    public Button btnDownload;

    @BindView(R.id.btnChamada)
    public Button btnChamada;

    @BindView(R.id.btnGps)
    public Button btnGps;

    @Nullable
    @BindView(R.id.btnEmail)
    public Button btnEmail;

    @BindView(R.id.btnFinanceiro)
    public Button btnFinanceiro;

    @BindView(R.id.btnNovoPedido)
    public Button btnNovoPedido;

    public ClientesViewHolder(View itemView, DBHelper db, Context ctx) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        int tExtraFonte = 0;
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
        } catch (NullPointerException ignored){
        }
        if (tExtraFonte > 0) {
            textViewNome.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewNomeLista1 : R.style.TextViewNomeLista2);
            textViewNomeFantasia.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtDataUltimaCompra.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            telefoneCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            cnpjCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            cidadeCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtClienteAguarda.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            idCliente.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            nomeTabelaPreco.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtCategoria.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
            txtSegmento.setTextAppearance(ctx, tExtraFonte == 2 ? R.style.TextViewOutrosListaSmall1 : R.style.TextViewOutrosListaSmall2);
        }
    }

}
