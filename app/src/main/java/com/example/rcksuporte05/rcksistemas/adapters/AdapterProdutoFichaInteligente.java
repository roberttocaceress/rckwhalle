package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.FichaProdutoItem;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class AdapterProdutoFichaInteligente extends RecyclerView.Adapter<AdapterProdutoFichaInteligente.ProdutoViewHolder> {

    public interface OnProdutoAdicionadoListener {
        void onAdicionarProduto(FichaProdutoItem item);
    }

    private Context context;
    private List<FichaProdutoItem> listaProdutos;
    private OnProdutoAdicionadoListener listener;

    public AdapterProdutoFichaInteligente(Context context, List<FichaProdutoItem> listaProdutos, OnProdutoAdicionadoListener listener) {
        this.context = context;
        this.listaProdutos = listaProdutos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produto_ficha_inteligente, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        FichaProdutoItem item = listaProdutos.get(position);
        if (item == null) return;

        holder.tvNomeProduto.setText(item.getNomeProduto());
        holder.tvTagDestaque.setText(item.getTagDestaque());

        String valoresStr = "Último Preço: " + MascaraUtil.mascaraRealSimboloD(item.getValorUltimoPreco()) +
                " | Estq: " + String.format(java.util.Locale.US, "%.0f", item.getEstoqueAtual()) + " un";
        holder.tvValores.setText(valoresStr);

        holder.btnAdicionar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAdicionarProduto(item);
            } else {
                Toast.makeText(context, "Produto " + item.getNomeProduto() + " selecionado (+1)!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos != null ? listaProdutos.size() : 0;
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTagDestaque, tvNomeProduto, tvValores;
        Button btnAdicionar;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTagDestaque = itemView.findViewById(R.id.tvTagDestaqueProduto);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeFichaProduto);
            tvValores = itemView.findViewById(R.id.tvValoresFichaProduto);
            btnAdicionar = itemView.findViewById(R.id.btnAdicionarProdutoFicha);
        }
    }
}