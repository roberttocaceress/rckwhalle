package com.example.rcksuporte05.rcksistemas.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.PedidoViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.TabelaAnaliseViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class ListaTabelaAnaliseAdapter extends RecyclerView.Adapter<TabelaAnaliseViewHolder> {
    private List<Generico> lista;
    private SparseBooleanArray selectedItems;
    private Listener listener;
    int numCols;

    public ListaTabelaAnaliseAdapter(List<Generico> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @SuppressLint("Range")
    @Override
    public TabelaAnaliseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tabela_analise_lista, parent, false);

        return new TabelaAnaliseViewHolder(itemView);
    }
    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(TabelaAnaliseViewHolder holder, int position) {
            //holder.txtIdPedido.setText("Nº " + MascaraUtil.numeroZeros(lista.get(position).getId(), 5));
            holder.txtIdPedido.setText("IDº   " + lista.get(position).getId() + "   --   CHV   " + lista.get(position).getId_generico());
            holder.txtNomeCliente.setText(lista.get(position).getNome());
            holder.txtFantasiaCliente.setText( MascaraUtil.mascaraReal(lista.get(position).getValor()));
            holder.txtDataEmissaoPedido.setText(lista.get(position).getDatae());
            holder.txtDataEntrega.setText(lista.get(position).getDatap());

            applyClickEvents(holder, position);
    }

    private void applyClickEvents(TabelaAnaliseViewHolder holder, final int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });

    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false))
            selectedItems.delete(pos);
        else
            selectedItems.put(pos, true);
        notifyItemChanged(pos);
    }



    @Override
    public int getItemCount() {
        return lista.size();
    }



    public interface Listener {
        void onClick(int position);
    }
}
