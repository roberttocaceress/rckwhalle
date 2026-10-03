package com.example.rcksuporte05.rcksistemas.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ContatoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Contato;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

/**
 * Created by RCK 03 on 03/02/2018.
 */

public class ListaContatoAdapter extends RecyclerView.Adapter<ContatoViewHolder>{

    private Listener listener;
    private List<Contato> lista;
    public Contato contato;

    public ListaContatoAdapter(List<Contato> lista, Listener listener) {
        this.listener = listener;
        this.lista = lista;
    }

    @Override
    public ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contato_itens,parent,false);
        return new ContatoViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ContatoViewHolder holder, int position) {
        contato = lista.get(position);
        holder.txtNomeResponsavelProspectlist.setText(contato.getPessoa_contato());
        holder.txtFuncaoResponsalveProspectList.setText(contato.getFuncao());
        if (contato.getNumero_telefone() != null && !contato.getNumero_telefone().replaceAll("[^0-9]", "").trim().isEmpty() && contato.getNumero_telefone().replaceAll("[^0-9]", "").length() >= 8 && contato.getNumero_telefone().replaceAll("[^0-9]", "").length() <= 11)
            holder.txtCelular1ProspectList.setText(MascaraUtil.formataTelefone(contato.getNumero_telefone()));
        else
            holder.txtCelular1ProspectList.setText("Nenhum telefone válido informado!");
        if(contato.getEmail() != null && !contato.getEmail().trim().isEmpty())
            holder.txtEmailProspectLista.setText(contato.getEmail());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public interface Listener {
        void onClick(int position);
    }
}
