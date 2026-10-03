package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoSubGrupoViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;

import java.util.List;

public class ListaSubGrupoAdapter  extends RecyclerView.Adapter<ProdutoSubGrupoViewHolder> {
    private Activity context;
    private List<ProdutoSubGrupo> produtoSubGrupo;
    private SubGrupoAdapterListener listener;
    private ProdutoSubGrupoViewHolder holder;


    public ListaSubGrupoAdapter( List<ProdutoSubGrupo> produtoSubGrupo, SubGrupoAdapterListener listener) {
        this.produtoSubGrupo = produtoSubGrupo;
        this.listener = listener;
        this.context = context;

    }

    @Override
    public ProdutoSubGrupoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto_sub_grupo, parent, false);
        return new ProdutoSubGrupoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProdutoSubGrupoViewHolder holder, final int position) {
        if (!produtoSubGrupo.get(position).getNome_sub_grupo().isEmpty()) {
            holder.txv_id_sub_grupo.setText(produtoSubGrupo.get(position).getId_grupo());
            holder.txt_nome_sub_grupo.setText(produtoSubGrupo.get(position).getNome_sub_grupo());
            holder.txt_total_produto.setText(produtoSubGrupo.get(position).getTotal_produto() + " Produto(s)");
        }
        applyCLickEnvents(holder, position);
        System.gc();
    }

    public ProdutoSubGrupo getItem(int position) {
        return produtoSubGrupo.get(position);
    }

    @Override
    public int getItemCount() {
        if (produtoSubGrupo != null)
            return produtoSubGrupo.size();
        return 0;
    }

    private void applyCLickEnvents(final ProdutoSubGrupoViewHolder holder, final int position) {
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    listener.onLongClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

    }
    public interface SubGrupoAdapterListener {
        void onClickListener(int position);
        void onLongClickListener(int position);
    }
}