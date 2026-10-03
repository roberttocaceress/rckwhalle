package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCobrancaAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CobrancaViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener {


    @BindView(R.id.txtRazaoSocial)
    public TextView txtRazaoSocial;


    @BindView(R.id.txtTelefone)
    public TextView txtTelefone;

    @BindView(R.id.txtEndereco)
    public TextView txtEndereco;

    @BindView(R.id.txtVencimento)
    public TextView txtVencimento;

    @BindView(R.id.txvPagamento)
    public TextView txvPagamento;

    @BindView(R.id.txvValorDebito)
    public TextView txvValorDebito;


    @BindView(R.id.txvValorRecebido)
    public TextView txvValorRecebido;

    @BindView(R.id.txvValorSaldo)
    public  TextView txvValorSaldo;

    @BindView(R.id.txvReagendamento)
    public  TextView txvReagendamento;



    @BindView(R.id.lyDadosCobranca)
    public LinearLayout lyDadosCobranca;

    @BindView(R.id.itemView)
    public LinearLayout itemView;

    //@BindView(R.id.itemCard)
    //public CardView itemCard;


    @BindView(R.id.txvUltimaCompra)
    public TextView txvUltimaCompra;

    @BindView(R.id.imgStatusCliente)
    public ImageView imgStatusCliente;

    @BindView(R.id.imgStatus)
    public ImageView imgStatus;

    @BindView(R.id.imgCheck)
    public ImageView imgCheck;

    @BindView(R.id.imgReagendado)
    public ImageView imgReagendado;


    @BindView(R.id.ivGps)
    public ImageView ivGps;

    @BindView(R.id.ivChamada)
    public ImageView ivChamada;



    @BindView(R.id.descricaoEndereco)
    public TextView descricaoEndereco;


    @BindView(R.id.descricaoTelefone)
    public TextView descricaoTelefone;

    @BindView(R.id.descricaoBairro)
    public TextView descricaoBairro;

    @BindView(R.id.txtBairro)
    public TextView txtBairro;


    @BindView(R.id.btnNovoPedido)
    public Button btnNovoPedido;

    @BindView(R.id.txtNovoPedido)
    public TextView txtNovoPedido;

    @BindView(R.id.lytNovoPedido)
    public LinearLayout lytNovoPedido;

    ListaCobrancaAdapter.CobrancaAdapterListener listener;
    public CobrancaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public boolean onLongClick(View view) {
        return true;
    }
}
