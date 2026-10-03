package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ClientesViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ContatoViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.NovidadeViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Contato;
import com.example.rcksuporte05.rcksistemas.model.Novidade;

import java.util.List;

public class ListaNovidadeAdapter extends RecyclerView.Adapter<NovidadeViewHolder>{
    private Context context;
    private ListaNovidadeAdapter.ListaNovidadeListener listener;
    private List<Novidade> list;


    public ListaNovidadeAdapter(Context context, List<Novidade> list, ListaNovidadeAdapter.ListaNovidadeListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }


    @NonNull
    @Override
    public NovidadeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_novidades, parent, false);
        return new NovidadeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovidadeViewHolder holder, int position) {
        //holder.txvArquivo.setText( list.get(position).getArquivo());

        holder.txvTitulo.setText(list.get(position).getTitulo());
        applyCLickEnvents(holder, position);

    }

    @Override
    public int getItemCount() {
        if ( list.size() > 0)
            return  list.size();
        return 0;
    }
    private void applyCLickEnvents( NovidadeViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
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


    public interface ListaNovidadeListener{
        void onClickListener(int position);
    }
}
