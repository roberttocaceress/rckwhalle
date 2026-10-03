package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinancaViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.NotaFiscalViewHolder;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.NotaFiscal;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListaFinancaAdapter extends RecyclerView.Adapter<FinancaViewHolder> {

    private Activity context;
    private List<FinanceiroResumo> listaFinancas;
    private financaListener listener;
    private NotaFiscalViewHolder holder;
    private SparseBooleanArray selectedItems;
    private int pag;

    public ListaFinancaAdapter(List<FinanceiroResumo> listaFinancas, financaListener listener, int pag) {
        this.listaFinancas = listaFinancas;
        this.listener = listener;
        this.pag = pag;
        this.context = context;
        this.selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public FinancaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_financa, parent, false);
        return new FinancaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FinancaViewHolder holder, int position) {
        Locale mLocale = new Locale("pt", "BR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        //DecimalFormat format = new DecimalFormat("#########,##");
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        holder.txvNomeOperacao.setText( listaFinancas.get(position).getNome_conta() );
        try {
            holder.txvValorOperacao.setText(  numberFormat.format( Float.parseFloat(listaFinancas.get(position).getValor_total() )));
            if ( Float.parseFloat( listaFinancas.get(position).getValor_bruto()) <= 0.0f)
                holder.txvValorOperacao.setText( "R$ 0,00");

         holder.txvTotalDocs.setText( listaFinancas.get(position).getTotal_docs() + "  titulo(s)");
        } catch ( NullPointerException|NumberFormatException e) {
            holder.txvValorOperacao.setText( "R$ 0,00");
        } catch ( Exception e) {
            holder.txvValorOperacao.setText( "R$ 0,00");
        }
        try {
            if (!listaFinancas.get(position).getNome_favorecido().isEmpty())
                holder.txvTotalDocs.setText(listaFinancas.get(position).getNome_favorecido().toUpperCase());

        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        if ( pag == 3) {
            if ( !listaFinancas.get(position).getDebito_credito().equalsIgnoreCase("R") && !listaFinancas.get(position).getDebito_credito().equalsIgnoreCase("P")) {
                ViewGroup.LayoutParams params = holder.swtStatus.getLayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.swtStatus.setLayoutParams(params);
                try {
                    if (!listaFinancas.get(position).getData_baixa().isEmpty() && listaFinancas.get(position).getData_baixa() != null)
                        holder.swtStatus.setChecked(true);
                    else
                        holder.swtStatus.setChecked(false);
                } catch (NullPointerException e) {
                    holder.swtStatus.setChecked(false);
                } catch ( Exception e) {
                    holder.swtStatus.setChecked(false);
                }
            } else {
                hideStatus(holder);
            }
        } else {
            hideStatus(holder);
        }
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        applyCLickEnvents(holder, position);
        System.gc();
    }

    private void hideStatus(FinancaViewHolder holder) {
        ViewGroup.LayoutParams params = holder.swtStatus.getLayoutParams();
        params.width = 0;
        holder.swtStatus.setLayoutParams(params);
        holder.swtStatus.setVisibility(View.INVISIBLE);
    }


    @Override
    public int getItemCount() {
        if (listaFinancas != null)
            return listaFinancas.size();
        return 0;
    }

    public interface financaListener {
        void onClickListener(int position);
    }

    private void applyCLickEnvents(final FinancaViewHolder holder, final int position) {
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
        holder.swtStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                /*
                try {
                    if (!listaFinancas.get(position).getData_baixa().isEmpty() && listaFinancas.get(position).getData_baixa() != null) {
                        compoundButton.setChecked(true);
                    } else {
                        compoundButton.setChecked(false);
                    }
                } catch ( NullPointerException e) {
                    compoundButton.setChecked(false);
                } catch ( Exception e) {
                    compoundButton.setChecked(false);
                }

                 */
            }
        });
    }
}
