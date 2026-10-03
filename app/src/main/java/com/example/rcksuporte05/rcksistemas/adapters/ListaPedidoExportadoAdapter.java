package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ListaEmprasaClienteViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ListaPedidoExportadoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaPedidoExportadoAdapter extends RecyclerView.Adapter<ListaPedidoExportadoViewHolder> {

    private Context context;
    private List<WebPedido> lista;
    private ListaPedidoExportadoAdapter.Listener listener;

    public ListaPedidoExportadoAdapter(Context context, List<WebPedido> lista, ListaPedidoExportadoAdapter.Listener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListaPedidoExportadoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_pedido_exportado, viewGroup, false);
        return new ListaPedidoExportadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPedidoExportadoViewHolder holder, int position) {
        holder.edtRazaoSocial.setText( lista.get(position).getNome_extenso());
        holder.edtNomeFantasia.setText("--");
        holder.edtNumeroPedido.setText( lista.get(position).getId_web_pedido());
        holder.edtNomeVendedor.setText( lista.get(position).getUsuario_lancamento_nome());
        holder.edtValorDesconto.setText( "R$ 0.00");
        try {
            holder.edtValorProduto.setText("R$ " + MascaraUtil.duasCasaDecimal( lista.get(position).getValor_produtos()));
            holder.edtValorTotal.setText("R$ " + MascaraUtil.duasCasaDecimal( lista.get(position).getValor_total()));
            holder.edtValorDesconto.setText( "R$ " +MascaraUtil.duasCasaDecimal(lista.get(position).getValor_desconto()));
        } catch ( NullPointerException e) {

        }
        try {
            holder.edtDataEmissao.setText(MascaraUtil.formataData( lista.get(position).getData_emissao(), "/", 8));
            holder.edtDataEntrega.setText(MascaraUtil.formataData( lista.get(position).getData_prev_entrega(), "/", 8));
        } catch ( NullPointerException e) {
        }
        applyCLickEnvents(holder, position);
        System.gc();
    }

    @Override
    public int getItemCount() {
        if ( lista.size() > 0)
            return lista.size();
        return 0;
    }

    private void applyCLickEnvents(final ListaPedidoExportadoViewHolder holder, final int position) {

        holder.btnConfirmar.setOnClickListener(new View.OnClickListener() {
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

    public interface Listener {
        void onClickListener(int position);
    }
}
