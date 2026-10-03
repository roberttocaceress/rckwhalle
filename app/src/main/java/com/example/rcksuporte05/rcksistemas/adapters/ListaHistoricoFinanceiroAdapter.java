package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinanceiroClienteViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinanceiroViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.DataFormatException;

public class ListaHistoricoFinanceiroAdapter extends RecyclerView.Adapter<FinanceiroClienteViewHolder> {

    private Activity context;
    private List<Financeiro> listaFinanceiros;
    //private FinanceiroAdapterListener listener;
    private FinanceiroClienteViewHolder holder;
    private SparseBooleanArray selectedItems;

    // --- OTIMIZAÇÃO: FORMATADORES FIXOS ---
    private final SimpleDateFormat sdfSql = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdfBr = new SimpleDateFormat("dd/MM/yyyy");

    public ListaHistoricoFinanceiroAdapter(List<Financeiro> listaFinanceiros){//, FinanceiroAdapterListener listener/*) {
        this.listaFinanceiros = listaFinanceiros;
        this.context = context;
        this.selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public FinanceiroClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_financeiro_cliente_2025, parent, false);
        return new FinanceiroClienteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceiroClienteViewHolder holder, int position) {
        holder.numeroDocumento.setText( listaFinanceiros.get(position).getDocumento() );
        holder.numeroParcela.setText( listaFinanceiros.get(position).getParcela() );

        // --- OTIMIZAÇÃO: REUSO DE DATE FORMAT ---
        try {
            if (listaFinanceiros.get(position).getData_emissao() != null)
                holder.dataEmissao.setText(sdfBr.format(sdfSql.parse(listaFinanceiros.get(position).getData_emissao())));
            if (listaFinanceiros.get(position).getData_vencimento() != null)
                holder.dataVencimento.setText(sdfBr.format(sdfSql.parse(listaFinanceiros.get(position).getData_vencimento())));
            if (listaFinanceiros.get(position).getData_pagamento() != null)
                holder.dataBaixa.setText(sdfBr.format(sdfSql.parse(listaFinanceiros.get(position).getData_pagamento())));
        } catch (Exception e) {
            // e.printStackTrace();
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
          try {
            holder.dataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(listaFinanceiros.get(position).getData_emissao())));
            holder.dataVencimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(listaFinanceiros.get(position).getData_vencimento())));
            holder.dataBaixa.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(listaFinanceiros.get(position).getData_pagamento())));
        } catch ( NullPointerException e) {
        } catch (Exception e) {
        }
        */

        holder.especieDocumento.setText( listaFinanceiros.get(position).getEspecie() );
        holder.valorTotal.setText( /*MascaraUtil.duasCasaDecimal*/MascaraUtil.mascaraVirgula(listaFinanceiros.get(position).getValor_documento()) );
        if ( !listaFinanceiros.get(position).getValor_juros().isEmpty() )
            holder.valorJuros.setText( /*MascaraUtil.duasCasaDecimal*/MascaraUtil.mascaraVirgula(listaFinanceiros.get(position).getValor_juros()) );
        else
            holder.valorJuros.setText("0,00");

        if ( !listaFinanceiros.get(position).getValor_pago().isEmpty() ) {
            holder.valorPago.setText(/*MascaraUtil.duasCasaDecimal*/MascaraUtil.mascaraVirgula(listaFinanceiros.get(position).getValor_pago()));
            try {
                if (listaFinanceiros.get(position).getData_pagamento().isEmpty())
                    holder.dataBaixa.setText("  /  /    ");
            } catch ( NullPointerException e) {
                holder.dataBaixa.setText("  /  /    ");
            }

        }else {
            holder.valorPago.setText("0,00");
            //holder.valorPago.setText("");
        }

        if ( !listaFinanceiros.get(position).getValor_saldo().isEmpty() )
            holder.valorSaldo.setText( /*MascaraUtil.duasCasaDecimal*/MascaraUtil.mascaraVirgula(listaFinanceiros.get(position).getValor_saldo()) );
        else
            holder.valorSaldo.setText("0,00");

        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);


        //applyCLickEnvents(holder, position);
        // System.gc();
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
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }
}
