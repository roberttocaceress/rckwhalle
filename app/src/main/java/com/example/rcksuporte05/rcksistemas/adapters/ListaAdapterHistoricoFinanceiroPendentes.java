package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiroPendente;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListaAdapterHistoricoFinanceiroPendentes extends ArrayAdapter<HistoricoFinanceiroPendente> {

    private final Context context;
    private final List<HistoricoFinanceiroPendente> lista;

    // --- OTIMIZAÇÃO: FORMATADORES FIXOS ---
    private final SimpleDateFormat sdfSql = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdfBr = new SimpleDateFormat("dd/MM/yy");

    public ListaAdapterHistoricoFinanceiroPendentes(Context context, List<HistoricoFinanceiroPendente> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        HistoricoFinanceiroPendente itemPosicao = this.lista.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.lista_financeiro_pendente, parent, false);
            holder = new ViewHolder();
            holder.txtDocumento = convertView.findViewById(R.id.txtDocumento);
            holder.txtParcela = convertView.findViewById(R.id.txtParcela);
            holder.txtEmissao = convertView.findViewById(R.id.txtEmissao);
            holder.txtVencimento = convertView.findViewById(R.id.txtVencimento);
            holder.txtDiasVenc = convertView.findViewById(R.id.txtDiasVenc);
            holder.txtValor = convertView.findViewById(R.id.txtValor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        else
            convertView.setBackgroundColor(Color.TRANSPARENT);

        try {
            holder.txtDocumento.setHeight(50);
            if (itemPosicao.getData_emissao() != null)
                holder.txtEmissao.setText(sdfBr.format(sdfSql.parse(itemPosicao.getData_emissao())));
            if (itemPosicao.getData_vencimento() != null)
                holder.txtVencimento.setText(sdfBr.format(sdfSql.parse(itemPosicao.getData_vencimento())));
        } catch (Exception e) {
            // e.printStackTrace();
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        try {
            txtDocumento.setHeight(50);
            txtEmissao.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(itemPosicao.getData_emissao())));
            txtVencimento.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(itemPosicao.getData_vencimento())));
        } catch (ParseException e) {
        }
        */

        holder.txtDocumento.setText(itemPosicao.getDocumento());
        holder.txtParcela.setText(itemPosicao.getParcela());
        holder.txtValor.setText(MascaraUtil.mascaraReal(itemPosicao.getValor_total()));

        try {
            if (Integer.parseInt(itemPosicao.getDias_atrazo()) > 0) {
                holder.txtDiasVenc.setText("-" + itemPosicao.getDias_atrazo());
                holder.txtValor.setTextColor(Color.RED);
                holder.txtDiasVenc.setTextColor(Color.RED);
            } else {
                holder.txtDiasVenc.setText(itemPosicao.getDias_atrazo());
                holder.txtValor.setTextColor(Color.parseColor("#4caf50"));
                holder.txtDiasVenc.setTextColor(Color.parseColor("#4caf50"));
            }
        } catch (Exception e) {
            // Erro no parse do atraso
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView txtDocumento;
        TextView txtParcela;
        TextView txtEmissao;
        TextView txtVencimento;
        TextView txtDiasVenc;
        TextView txtValor;
    }
}
