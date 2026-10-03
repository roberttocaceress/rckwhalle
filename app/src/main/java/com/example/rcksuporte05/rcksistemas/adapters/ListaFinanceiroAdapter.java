package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinanceiroViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;

import java.util.List;

public class ListaFinanceiroAdapter extends RecyclerView.Adapter<FinanceiroViewHolder> {

    private Activity context;
    private List<Financeiro> listaFinanceiros;
    private FinanceiroAdapterListener listener;
    private FinanceiroViewHolder holder;
    private SparseBooleanArray selectedItems;

    public ListaFinanceiroAdapter(List<Financeiro> listaFinanceiros, FinanceiroAdapterListener listener) {
        this.listaFinanceiros = listaFinanceiros;
        this.listener = listener;
        this.context = context;
        this.selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public FinanceiroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_financeiro, parent, false);
        return new FinanceiroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceiroViewHolder holder, int position) {
        holder.finDocumento.setText( listaFinanceiros.get(position).getDocumento() );
        holder.finParcela.setText( listaFinanceiros.get(position).getParcela() );
        holder.finEmissao.setText( listaFinanceiros.get(position).getData_emissao() );
        holder.finVencimento.setText( listaFinanceiros.get(position).getData_vencimento() );
        try {
            holder.finPagamento.setText( listaFinanceiros.get(position).getData_pagamento() );
        } catch ( NullPointerException e) {
            holder.finPagamento.setText( "00/00/00" );
        } catch ( Exception e) {
            holder.finPagamento.setText( "00/00/00" );
        }
        holder.finValorDoc.setText( listaFinanceiros.get(position).getValor_documento() );
        if ( !listaFinanceiros.get(position).getValor_juros().isEmpty() )
            holder.finJuroDesconto.setText( listaFinanceiros.get(position).getValor_juros() );
        else
            holder.finJuroDesconto.setText("0.00");

        if ( !listaFinanceiros.get(position).getValor_pago().isEmpty() )
            holder.finValorPago.setText( listaFinanceiros.get(position).getValor_pago() );
        else
            holder.finValorPago.setText("0.00");

        if ( !listaFinanceiros.get(position).getValor_saldo().isEmpty() )
            holder.finValorSaldo.setText( listaFinanceiros.get(position).getValor_saldo() );
        else
            holder.finValorSaldo.setText("0.00");
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        applyCLickEnvents(holder, position);
        System.gc();
    }

    @Override
    public int getItemCount() {
        if (listaFinanceiros != null)
            return listaFinanceiros.size();
        return 0;
    }

    public interface FinanceiroAdapterListener {
        void onClickListener(int position);
        void onLongClickListener(int position);
    }

    private void applyCLickEnvents(final FinanceiroViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    listener.onLongClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
}
