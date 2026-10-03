package com.example.rcksuporte05.rcksistemas.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.CampanhaItemViewHolder;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaCampanhaltensAdapter extends RecyclerView.Adapter<CampanhaItemViewHolder> {
    private List<CampanhaComercialItens> lista;
    private Listener listener;
    public  CampanhaComercialItens campanhaComercialItens;
    public ListaCampanhaltensAdapter(List<CampanhaComercialItens> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @Override
    public CampanhaItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.campanha_item_lista_2025, parent, false);
        return new CampanhaItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampanhaItemViewHolder holder, int position) {
        campanhaComercialItens = lista.get(position);
        switch (campanhaComercialItens.getIdTipoCampanha()) {
            case 1:
                holder.txtTipoCampanha1.setText("Pague: ");
                 break;
            case 2:
                holder.txtTipoCampanha1.setText("Compre: ");
                break;
        }

        try {
            if ( !campanhaComercialItens.getIdProdutoVenda().isEmpty())
                holder.txtIdProdutoLinha.setText(campanhaComercialItens.getIdProdutoVenda());
            else
                holder.txtIdProdutoLinha.setText("Linha");
        }catch ( NullPointerException e) {
            holder.txtIdProdutoLinha.setText("Linha");
        }catch ( Exception e) {
            holder.txtIdProdutoLinha.setText("Linha");
        }
        holder.edtNomeProdutoLinha.setText(campanhaComercialItens.getNomeProdutoLinha());
        holder.edtQuantidadeLinha.setText(MascaraUtil.mascaraVirgula(campanhaComercialItens.getQuantidadeVenda()));
        holder.txtIdProdutoBrinde.setText( campanhaComercialItens.getIdProdutoBonus());
        holder.edtNomeProdutoBrinde.setText(campanhaComercialItens.getNomeProdutoBonus());
        holder.edtQuantidadeBrinde.setText(MascaraUtil.mascaraVirgula(campanhaComercialItens.getQuantidadeBonus()));
        //if (itensCampanha.get(position).getIdLinhaProduto() > 0) {
            //holder.btnInfoCampanha.setVisibility(View.VISIBLE);
            //holder.btnInfoCampanha.setOnClickListener(listener.onInfoClickListener(position));
        //}

    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        return 0;
    }

    public CampanhaComercialItens getItem(int position) {
        return lista.get(position);
    }

    public interface Listener {
        void onClickListener(int position);
        View.OnClickListener onInfoClickListener(int position);
    }
}
