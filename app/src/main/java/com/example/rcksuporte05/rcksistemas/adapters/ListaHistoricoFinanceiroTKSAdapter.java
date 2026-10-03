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

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinanceiroCobrancaVHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.HistoricoFinanceiroTKSVHolder;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaHistoricoFinanceiroTKSAdapter extends RecyclerView.Adapter<HistoricoFinanceiroTKSVHolder> {
    private Context context;
    private List<FinanceiroResumo> listaFinancas;
    private SparseBooleanArray selectedItems;
    private String statusFinanceiro;
    private DBHelper db;

    public ListaHistoricoFinanceiroTKSAdapter(List<FinanceiroResumo> listaFinancas,  Context context, String statusFinanceiro) {
        this.listaFinancas = listaFinancas;
        this.context = context;
        this.statusFinanceiro = statusFinanceiro;
        this.selectedItems = new SparseBooleanArray();
    }
    @NonNull
    @Override
    public HistoricoFinanceiroTKSVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_historico_financeiro_tks, parent, false);
        if (db == null)
            db = new DBHelper(context);
        return new HistoricoFinanceiroTKSVHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoFinanceiroTKSVHolder holder, int position) {
        FinanceiroResumo financeiro = listaFinancas.get(position);
        
        if (financeiro.getStatus().equalsIgnoreCase("V"))
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        else if (financeiro.getStatus().equalsIgnoreCase("Q"))
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        else
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));

        holder.txvNumeroDocumento.setText(financeiro.getDocumento());
        holder.txvNumeroParcela.setText(financeiro.getParcela());
        holder.txvDataEmissao.setText(MascaraUtil.formataData(financeiro.getData_emissao(), "/", 8));
        
        if (financeiro.getStatus().equalsIgnoreCase("Q"))
            holder.txvDataVencimento.setText(MascaraUtil.formataData(financeiro.getData_baixa(), "/", 8));
        else
            holder.txvDataVencimento.setText(MascaraUtil.formataData(financeiro.getData_vencimento(), "/", 8));

        holder.txvPontualidade.setText(financeiro.getPontualidade() == null || financeiro.getPontualidade().isEmpty() ? " " : financeiro.getPontualidade());

        holder.txvValorTotal.setText("R$ " + MascaraUtil.duasCasaDecimal(financeiro.getValor_total()));

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        if ( listaFinancas.get(position).getStatus().equalsIgnoreCase("V"))
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        else if (listaFinancas.get(position).getStatus().equalsIgnoreCase("Q"))
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        else
            holder.corx.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));

        holder.txvNumeroDocumento.setText(listaFinancas.get(position).getDocumento());
        holder.txvNumeroParcela.setText(listaFinancas.get(position).getParcela());
        holder.txvDataEmissao.setText(MascaraUtil.formataData(listaFinancas.get(position).getData_emissao(), "/", 8));
        if ( listaFinancas.get(position).getStatus().equalsIgnoreCase("Q"))
            holder.txvDataVencimento.setText(MascaraUtil.formataData(listaFinancas.get(position).getData_baixa(), "/", 8));
        else
            holder.txvDataVencimento.setText(MascaraUtil.formataData(listaFinancas.get(position).getData_vencimento(), "/", 8));


        holder.txvPontualidade.setText( listaFinancas.get(position).getPontualidade() == null || listaFinancas.get(position).getPontualidade().isEmpty() ? " " :  listaFinancas.get(position).getPontualidade());

        holder.txvValorTotal.setText("R$ " + MascaraUtil.duasCasaDecimal(listaFinancas.get(position).getValor_total()));

        System.gc();
        */
        
        // System.gc();
    }


    @Override
    public int getItemCount() {
        if (listaFinancas != null)
            return listaFinancas.size();
        return 0;
    }


}
