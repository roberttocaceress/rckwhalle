package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.BairroViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.MunicipioViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Municipio;

import java.util.List;

public class BairroAdapter extends RecyclerView.Adapter<BairroViewHolder> {
    private bairroListener listener;
    private String[] municipio;
    private List<Generico> lista;
    private SparseBooleanArray selectedItems;
    private int posAnterior;

    public BairroAdapter( List<Generico> lista, bairroListener listener) {
        this.listener = listener;
        this.lista = lista;
        this.selectedItems = new SparseBooleanArray();
        posAnterior = -1;
    }
    @Override
    public BairroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_bairro, parent, false);
        return new BairroViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(BairroViewHolder holder, int position) {
            holder.txtBairro.setText(lista.get(position).getNome());
            //holder.rlItemBairros.setActivated(selectedItems.get(position, false));
            if (selectedItems.get(position))
                holder.txtBairro.setTextColor(Color.parseColor("#ffffff"));
            else
                holder.txtBairro.setTextColor(Color.parseColor("#86869e"));

        applyClickEvents(holder, position);
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        return 0;

    }
    private void applyClickEvents(BairroViewHolder holder, final int position) {
        holder.rlItemBairros.setOnClickListener(new View.OnClickListener() {
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

        if (posAnterior >= 0)
            selectedItems.delete(posAnterior);

        if (pos == posAnterior) {
            selectedItems.clear();
            posAnterior = -1;
        } else
            posAnterior = pos;
    }

    public void selecionado(String bairroSelecionado) {
        /*if ( lista == null) {
            for (int i = 0; municipio.length > i; i++) {
                if (municipio[i].equals(municipioSelecionado)) {
                    toggleSelection(i);
                    break;
                }
            }
        }

         */
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public interface bairroListener {
        void onClick(int position);
    }
}
