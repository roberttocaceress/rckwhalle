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
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinanceiroViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.NotaFiscalViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.NotaFiscal;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListaNotaFiscalAdapter extends RecyclerView.Adapter<NotaFiscalViewHolder> {

    private Activity context;
    private List<NotaFiscal> listaNotas;
    private notaFiscalListener listener;
    private NotaFiscalViewHolder holder;
    private SparseBooleanArray selectedItems;

    public ListaNotaFiscalAdapter(List<NotaFiscal> listaNotas, notaFiscalListener listener) {
        this.listaNotas = listaNotas;
        this.listener = listener;
        this.context = context;
        this.selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public NotaFiscalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_nota_fiscal, parent, false);
        return new NotaFiscalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaFiscalViewHolder holder, int position) {
        Locale mLocale = new Locale("pt", "BR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        //DecimalFormat format = new DecimalFormat("#########,##");
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        holder.txvNomeOperacao.setText( listaNotas.get(position).getNome_operacao());
        try {
            holder.txvValorOperacao.setText(  numberFormat.format( Float.parseFloat(listaNotas.get(position).getValor_financeiro()) ));
            if ( Float.parseFloat(listaNotas.get(position).getValor_financeiro()) <= 0.0f)
                holder.txvValorOperacao.setText( "R$ 0,00");
            holder.txvTotalDocs.setText( listaNotas.get(position).getTotal_docs() + "  titulo(s)");
        } catch ( NullPointerException|NumberFormatException e) {
            holder.txvValorOperacao.setText( "R$ 0,00");
        } catch ( Exception e) {
            holder.txvValorOperacao.setText( "R$ 0,00");
        }

        try {
            if (!listaNotas.get(position).getDest_rem_nome().isEmpty())
                holder.txvTotalDocs.setText(listaNotas.get(position).getDest_rem_nome().toUpperCase());
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        /*
        if ( listaNotas.get(position).getE_entrada_s_saida().equalsIgnoreCase("E") || listaNotas.get(position).getE_entrada_s_saida().equalsIgnoreCase("C")  ) {
            //#c0c0c0
            holder.txvNomeOperacao.setTextColor(Color.parseColor("#4F4F4F"));
            holder.txvValorOperacao.setTextColor(Color.parseColor("#4F4F4F"));
            holder.txvTotalDocs.setTextColor(Color.parseColor("#4F4F4F"));
        } else {
            holder.txvNomeOperacao.setTextColor(Color.parseColor("#000000"));
            holder.txvValorOperacao.setTextColor(Color.parseColor("#000000"));
            holder.txvTotalDocs.setTextColor(Color.parseColor("#000000"));
        }
        /*
         */
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        applyCLickEnvents(holder, position);
        System.gc();
    }

    @Override
    public int getItemCount() {
        if (listaNotas != null)
            return listaNotas.size();
        return 0;
    }

    public interface notaFiscalListener {
        void onClickListener(int position);
    }

    private void applyCLickEnvents(final NotaFiscalViewHolder holder, final int position) {
        holder.txvNomeOperacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.txvValorOperacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.txvTotalDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
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
    }
}
