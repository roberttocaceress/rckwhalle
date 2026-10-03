package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.PedidoAnexoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.PedidoAnexo;

import java.util.ArrayList;
import java.util.List;

public class PedidoAnexoAdapter extends RecyclerView.Adapter<PedidoAnexoViewHolder> {
    private List<PedidoAnexo> lista;
    private Listener listener;
    private SparseBooleanArray selectedItems;
    public PedidoAnexo pedidoAnexo;
    int numCols;
    public PedidoAnexoAdapter(List<PedidoAnexo> pedidoAnexo, Listener listener) {
        this.lista = pedidoAnexo;
        this.listener = listener;
        this.selectedItems = new SparseBooleanArray();
    }

    @Override
    public PedidoAnexoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_pedido_anexo, parent, false);
        return new PedidoAnexoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAnexoViewHolder holder, int position) {
        pedidoAnexo = lista.get(position);
        //holder.txtPosition.setText(String.valueOf(position + 1));
        //holder.txtNomeAnexo.setText(pedidoAnexo.getNomeAnexo().replace(".jpg", ""));
        holder.imAnexo.setImageBitmap(pedidoAnexo.getMiniatura());
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        applyClickEvents(holder, position);
    }

    public PedidoAnexo getItem(int position) {
        return lista.get(position);
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        return 0;
    }

    public int setNumCols(int qtdCols) {
        return numCols = qtdCols;
    }

    private void applyClickEvents(PedidoAnexoViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClickListener(position);
                return true;
            }
        });
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false))
            selectedItems.delete(pos);
        else
            selectedItems.put(pos, true);
        notifyDataSetChanged();
    }

    public int getSelectedItensCount() {
        return selectedItems.size();
    }

    public List<PedidoAnexo> getItensSelecionados() {
        List<PedidoAnexo> lista = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++)
            lista.add(this.lista.get(selectedItems.keyAt(i)));
        return lista;
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface Listener {
        void onClickListener(int position);

        void onLongClickListener(int position);
    }
}
