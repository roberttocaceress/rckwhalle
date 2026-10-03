package com.example.rcksuporte05.rcksistemas.adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.BairroCheckViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Municipio;

import java.util.List;

public class ListaBairroCheckAdapter extends RecyclerView.Adapter<BairroCheckViewHolder> {
    private bairroListener listener;
    private String[] municipio;
    private List<Generico> lista;

    public ListaBairroCheckAdapter(List<Generico> lista, bairroListener listener) {
        this.listener = listener;
        this.lista = lista;
    }
    @Override
    public BairroCheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_bairro_checkbox, parent, false);
        return new BairroCheckViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(BairroCheckViewHolder holder, int position) {
        holder.txtBairro.setText(lista.get(position).getNome());
        holder.chkBairro.setChecked(lista.get(position).isChecked());
        applyClickEvents(holder, position);
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        return 0;

    }
    private void applyClickEvents(BairroCheckViewHolder holder, final int position) {
        holder.lytBairro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });
        holder.chkBairro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });


    }

    public interface bairroListener {
        void onClick(int position);
    }
}
