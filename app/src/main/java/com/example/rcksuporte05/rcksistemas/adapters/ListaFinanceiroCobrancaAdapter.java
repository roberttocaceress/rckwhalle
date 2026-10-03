package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinanceiroCobrancaVHolder;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaFinanceiroCobrancaAdapter  extends RecyclerView.Adapter<FinanceiroCobrancaVHolder> {
    private Context context;
    private List<FinanceiroResumo> listaFinancas;
    private ListaFinanceiroCobrancaAdapter.financaListener listener;
    private SparseBooleanArray selectedItems;
    private String statusFinanceiro;

    public ListaFinanceiroCobrancaAdapter(List<FinanceiroResumo> listaFinancas, ListaFinanceiroCobrancaAdapter.financaListener listener, Context context, String statusFinanceiro) {
        this.listaFinancas = listaFinancas;
        this.listener = listener;
        this.context = context;
        this.statusFinanceiro = statusFinanceiro;
        this.selectedItems = new SparseBooleanArray();
    }
    @NonNull
    @Override
    public FinanceiroCobrancaVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_financeiro_cobranca, parent, false);
        return new FinanceiroCobrancaVHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceiroCobrancaVHolder holder, int position) {
        if ( statusFinanceiro.equalsIgnoreCase("R")) {
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryAzul));
            holder.inputNomeFavorecido.setVisibility(View.INVISIBLE);
            holder.inputValorTotal.setVisibility(View.INVISIBLE);
            holder.inputDataBaixa.setVisibility(View.INVISIBLE);

            holder.inputNumeroDocumento.setHint("Total Vencido");
            holder.numeroDocumento.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getTotal_vencido()));
            holder.numeroDocumento.setTextColor(Color.BLACK);

            holder.inputNumeroParcela.setHint("Total a Vencer");
            holder.numeroParcela.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getTotal_vencer()));
            holder.numeroParcela.setTextColor(Color.BLACK);


            holder.inputDataEmissao.setHint("Titulos vencidos");
            holder.inputDataVencimento.setHint("Titulos a vencer");
            holder.dataEmissao.setText(listaFinancas.get(position).getTotal_titulos_vencidos());
            holder.dataEmissao.setTextColor(Color.BLACK);
            holder.dataVencimento.setText(listaFinancas.get(position).getTotal_titulos_avencer());
            holder.dataVencimento.setTextColor(Color.BLACK);

            //holder.inputDataEmissao.setHint("Total Quitado");
            //holder.dataEmissao.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getTotal_quitado()));
            //holder.dataEmissao.setTextColor(Color.BLACK);
        } else if (statusFinanceiro.equalsIgnoreCase("RONN")) {
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryAzul));
            holder.inputNomeFavorecido.setVisibility(View.INVISIBLE);

            holder.inputNumeroDocumento.setHint("Total Vencido");
            holder.numeroDocumento.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getTotal_vencido()));
            holder.numeroDocumento.setTextColor(Color.BLACK);

            holder.inputNumeroParcela.setHint("Total a Vencer");
            holder.numeroParcela.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getTotal_vencer()));
            holder.numeroParcela.setTextColor(Color.BLACK);

            holder.inputDataBaixa.setHint("Total quitado");
            holder.dataBaixa.setText("R$" + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getTotal_quitado()));
            holder.dataBaixa.setTextColor(Color.BLACK);


            holder.inputDataEmissao.setHint("Titulos vencidos");
            holder.inputDataVencimento.setHint("Titulos a vencer");
            holder.inputValorTotal.setHint("Total quitados");

            holder.dataEmissao.setText(listaFinancas.get(position).getTotal_titulos_vencidos());
            holder.dataEmissao.setTextColor(Color.BLACK);
            holder.dataVencimento.setText(listaFinancas.get(position).getTotal_titulos_avencer());
            holder.dataVencimento.setTextColor(Color.BLACK);
            holder.valorTotal.setText(listaFinancas.get(position).getTotal_titulos_quitados());
            holder.valorTotal.setTextColor(Color.BLACK);


            //holder.inputValorTotal.setHint("Total Quitado");
            //holder.dataEmissao.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getTotal_quitado()));
            //holder.dataEmissao.setTextColor(Color.BLACK);


        } else {
            holder.numeroDocumento.setText(listaFinancas.get(position).getDocumento());
            holder.nomeFavorecido.setText(listaFinancas.get(position).getNome_favorecido());
            holder.numeroParcela.setText(listaFinancas.get(position).getParcela() == null || listaFinancas.get(position).getParcela().isEmpty() ? " " : listaFinancas.get(position).getParcela());
            holder.dataEmissao.setText(MascaraUtil.formataData(listaFinancas.get(position).getData_emissao(), "/", 8));
            holder.dataVencimento.setText(MascaraUtil.formataData(listaFinancas.get(position).getData_vencimento(), "/", 8));
            if (!statusFinanceiro.isEmpty()) {
                holder.dataBaixa.setText(" ");
                holder.dataBaixa.setVisibility(View.INVISIBLE);
                holder.inputDataBaixa.setVisibility(View.INVISIBLE);
                if (statusFinanceiro.equalsIgnoreCase("T")) {
                    if (listaFinancas.get(position).getStatus().equalsIgnoreCase("V"))
                        holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    else if (listaFinancas.get(position).getStatus().equalsIgnoreCase("AV"))
                        holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                    else if (listaFinancas.get(position).getStatus().equalsIgnoreCase("Q")) {
                        holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                        holder.dataBaixa.setText(MascaraUtil.formataData(listaFinancas.get(position).getData_baixa(), "/", 8));
                        holder.dataBaixa.setVisibility(View.VISIBLE);
                        holder.inputDataBaixa.setVisibility(View.VISIBLE);
                        holder.dataBaixa.setTextColor(Color.BLACK);
                    }

                } else {
                    if (statusFinanceiro.equalsIgnoreCase("V"))
                        holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    else if (statusFinanceiro.equalsIgnoreCase("AV"))
                        holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                    else if (statusFinanceiro.equalsIgnoreCase("Q")) {
                        holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                        holder.dataBaixa.setText(MascaraUtil.formataData(listaFinancas.get(position).getData_baixa(), "/", 8));
                        holder.dataBaixa.setVisibility(View.VISIBLE);
                        holder.inputDataBaixa.setVisibility(View.VISIBLE);
                    }
                }
            }
            holder.valorTotal.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getValor_total()));
        }
        applyCLickEnvents(holder, position);
        System.gc();


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

    private void applyCLickEnvents(final FinanceiroCobrancaVHolder holder, final int position) {

    }
}
