package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.BairroViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ParticipacaoFaturamentoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ParticipacaoFaturamentoAdapter extends RecyclerView.Adapter<ParticipacaoFaturamentoViewHolder> {
    private List<ParticipacaoFaturamento> lista;
    private String tipoRelatorio;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ParticipacaoFaturamento item);
    }

    public ParticipacaoFaturamentoAdapter(List<ParticipacaoFaturamento> lista, String tipoRelatorio) {
        this.lista = lista;
        this.tipoRelatorio = tipoRelatorio;
    }

    public ParticipacaoFaturamentoAdapter(List<ParticipacaoFaturamento> lista, String tipoRelatorio, OnItemClickListener listener) {
        this.lista = lista;
        this.tipoRelatorio = tipoRelatorio;
        this.listener = listener;
    }
    @Override
    public ParticipacaoFaturamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if ( tipoRelatorio.equalsIgnoreCase("MR") || tipoRelatorio.equalsIgnoreCase("GP")) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_participacao_faturamento_lista, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_evolucao_faturamento_lista, parent, false);
        }
        return new ParticipacaoFaturamentoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParticipacaoFaturamentoViewHolder holder, int position) {
        ParticipacaoFaturamento pf = lista.get(position);
        holder.txvNome.setTextColor(Color.BLACK);
        holder.txvValorLiquido.setTextColor(Color.BLACK);
        
        // Reset visual (importante para o reuso do ViewHolder)
        holder.itemView.setPadding(0, 0, 0, 0);
        holder.txvNome.setTypeface(null, android.graphics.Typeface.NORMAL);
        holder.txvValorLiquido.setTypeface(null, android.graphics.Typeface.NORMAL);
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        try {
            String nome = pf.getNome();
            
            // LÓGICA VISUAL DE AGRUPAMENTO (Destaque para Totais e Subtotais)
            if (nome != null && (nome.startsWith("TOTAL") || nome.startsWith("SUBTOTAL"))) {
                holder.txvNome.setTypeface(null, android.graphics.Typeface.BOLD);
                holder.txvValorLiquido.setTypeface(null, android.graphics.Typeface.BOLD);
                
                if (nome.contains("GERAL")) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#E2E8F0")); // Cinza mais forte p/ Total Geral
                } else {
                    holder.itemView.setBackgroundColor(Color.parseColor("#F1F5F9")); // Cinza suave p/ Totais/Subtotais Mensais
                }
                
                holder.txvNome.setText(nome);
            } else {
                // Linha de dado (Grupo ou Cliente): Recuo e Bullet
                holder.txvNome.setText("  • " + nome);
            }

            if (pf.getNome().equalsIgnoreCase("SEM VENDAS") || pf.getNome().contains("(DEV)") || pf.getNome().contains("DEVOLUÇÕES"))
                holder.txvNome.setTextColor(Color.RED);
            
            if (Float.parseFloat(pf.getValor_liquido()) < 0.00f || (pf.getNome().contains("DEVOLUÇÕES") && !pf.getNome().contains("LÍQUIDO")))
                holder.txvValorLiquido.setTextColor(Color.RED);

            if (tipoRelatorio.equalsIgnoreCase("MR") || tipoRelatorio.equalsIgnoreCase("GP") || tipoRelatorio.equalsIgnoreCase("C")) {
                holder.txvValorBruto.setText(MascaraUtil.mascaraRealSimbolo(pf.getValor_bruto()));
            }
            holder.txvValorLiquido.setText(MascaraUtil.mascaraRealSimboloD(Double.parseDouble(pf.getValor_liquido())));

            if (listener != null) {
                holder.itemView.setOnClickListener(v -> {
                    if (nome != null && !nome.startsWith("TOTAL") && !nome.startsWith("SUBTOTAL")) {
                        listener.onItemClick(pf);
                    }
                });
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (lista != null )
            return lista.size();
        return 0;

    }
}
