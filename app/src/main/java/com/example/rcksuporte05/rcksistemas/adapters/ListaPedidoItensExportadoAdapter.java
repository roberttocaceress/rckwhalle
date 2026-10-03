package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ListaPedidoExportadoViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ListaPedidoItensExportadoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaPedidoItensExportadoAdapter  extends RecyclerView.Adapter<ListaPedidoItensExportadoViewHolder> {
    private Context context;
    private List<WebPedidoItens> lista;


    public ListaPedidoItensExportadoAdapter(Context context, List<WebPedidoItens> lista) {
        this.context = context;
        this.lista = lista;

    }
    @NonNull
    @Override
    public ListaPedidoItensExportadoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_pedido_itens_exportado, viewGroup, false);
        return new ListaPedidoItensExportadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPedidoItensExportadoViewHolder holder, int position) {
        holder.edtNomeProduto.setText( lista.get(position).getNome_produto().toUpperCase());
        holder.edtQtdeProduto.setText( MascaraUtil.duasCasaDecimal(lista.get(position).getQuantidade()));
        holder.edtValorProduto.setText( "R$ " + MascaraUtil.duasCasaDecimal(lista.get(position).getVenda_preco()));
        holder.edtDescontoProduto.setText( "R$ " + MascaraUtil.duasCasaDecimal(lista.get(position).getValor_desconto_real()));
        holder.edtTotalProduto.setText( "R$ " + MascaraUtil.duasCasaDecimal(lista.get(position).getValor_total()));
        holder.edtCodigoProduto.setText( lista.get(position).getId_produto());
        holder.edtEANProduto.setText( lista.get(position).getCodigo_em_barras());
        //applyCLickEnvents(holder, position);
        System.gc();
    }

    @Override
    public int getItemCount() {
        if ( lista.size() > 0)
            return lista.size();
        return 0;
    }
}
