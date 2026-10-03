package com.example.rcksuporte05.rcksistemas.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.CampanhaViewHolder;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaCampanhaAdapter extends RecyclerView.Adapter<CampanhaViewHolder> {
    private List<CampanhaComercialCab> lista;
    private Listener listener;
    public CampanhaComercialCab campanhaComercialCab;

    public ListaCampanhaAdapter(List<CampanhaComercialCab> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CampanhaViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_campanha_2025, parent, false);
        return new CampanhaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampanhaViewHolder holder, int position) {
        campanhaComercialCab = lista.get(position);
        holder.txtIdCampanha.setText(String.valueOf(campanhaComercialCab.getIdCampanha()));
        holder.txtNomeCampanha.setText(campanhaComercialCab.getNomeCampanha());
        String periodo = "Periodo de  ";
        try {
            periodo += new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(campanhaComercialCab.getDataInicio()));
         } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            periodo += " A " + new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(campanhaComercialCab.getDataFim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtPeriodo.setText(periodo);
        holder.txtDescricao.setText( campanhaComercialCab.getDescricaoCampanha());
        applyClickEvents(holder, position);
    }

    public CampanhaComercialCab getItem(int position) {
        return lista.get(position);
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        return 0;
    }

    private void applyClickEvents(CampanhaViewHolder holder, final int position) {
        holder.layoutCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListener(position);
            }
        });
        holder.txtDescricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListener(position);
            }
        });
    }

    public interface Listener {
        void onClickListener(int position);
        //View.OnClickListener onClickDescCampanha(int position);
    }
}
