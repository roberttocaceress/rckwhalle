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
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListaAdapterHistoricoFinanceiroQuitados extends ArrayAdapter<HistoricoFinanceiroQuitado> {

    private final Context context;
    private final List<HistoricoFinanceiroQuitado> lista;

    // --- OTIMIZAÇÃO: FORMATADORES FIXOS ---
    private final SimpleDateFormat sdfSql = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdfBr = new SimpleDateFormat("dd/MM/yy");

    public ListaAdapterHistoricoFinanceiroQuitados(Context context, List<HistoricoFinanceiroQuitado> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        HistoricoFinanceiroQuitado itemPosicao = this.lista.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.lista_financeiro_quitado, parent, false);
            holder = new ViewHolder();
            holder.txtDocumento = convertView.findViewById(R.id.txtDocumento);
            holder.txtParcela = convertView.findViewById(R.id.txtParcela);
            holder.txtVencimento = convertView.findViewById(R.id.txtVencimento);
            holder.txtValor = convertView.findViewById(R.id.txtValor);
            holder.txtPagamento = convertView.findViewById(R.id.txtPagamento);
            holder.txtPontualidade = convertView.findViewById(R.id.txtPontualidade);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        else
            convertView.setBackgroundColor(Color.TRANSPARENT);

        try {
            if (itemPosicao.getData_emissao() != null)
                holder.txtVencimento.setText(sdfBr.format(sdfSql.parse(itemPosicao.getData_emissao())));
            if (itemPosicao.getData_baixa() != null)
                holder.txtPagamento.setText(sdfBr.format(sdfSql.parse(itemPosicao.getData_baixa())));
            holder.txtPontualidade.setText(itemPosicao.getPontualidade());
        } catch (Exception e) {
            // e.printStackTrace();
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        try {
            txtVencimento.setText(new SimpleDateFormat("dd/mm/yy").format(new SimpleDateFormat("yyyy-mm-dd").parse(itemPosicao.getData_emissao())));
            txtPagamento.setText(new SimpleDateFormat("dd/mm/yy").format(new SimpleDateFormat("yyyy-mm-dd").parse(itemPosicao.getData_baixa())));
            txtPontualidade.setText(itemPosicao.getPontualidade());
        } catch (ParseException e) {
        }
        */

        holder.txtDocumento.setText(itemPosicao.getDocumento());
        holder.txtParcela.setText(itemPosicao.getParcela());
        holder.txtValor.setText(MascaraUtil.mascaraReal(itemPosicao.getValor_total()));

        if (itemPosicao.getPontualidade_status() != null && itemPosicao.getPontualidade_status().trim().equals("A"))
            holder.txtPontualidade.setTextColor(Color.RED);
        else
            holder.txtPontualidade.setTextColor(Color.parseColor("#4caf50"));

        holder.txtValor.setTextColor(Color.parseColor("#4caf50"));

        return convertView;
    }

    private static class ViewHolder {
        TextView txtDocumento;
        TextView txtParcela;
        TextView txtVencimento;
        TextView txtValor;
        TextView txtPagamento;
        TextView txtPontualidade;
    }
}
