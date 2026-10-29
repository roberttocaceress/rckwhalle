package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.FichaProdutoItem;

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

        if (holder.imageAnexo != null) {
            holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
            try {
                com.example.rcksuporte05.rcksistemas.DAO.DBHelper db = new com.example.rcksuporte05.rcksistemas.DAO.DBHelper(context);
                android.database.Cursor cursor = db.listaDados("SELECT FOTO_ARQUIVO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" + item.getIdProduto() + "' AND FOTO_ARQUIVO IS NOT NULL AND FOTO_ARQUIVO <> '' LIMIT 1");
                if (cursor != null && cursor.moveToFirst()) {
                    int idxFoto = cursor.getColumnIndex("FOTO_ARQUIVO");
                    if (idxFoto != -1 && cursor.getString(idxFoto) != null) {
                        String fotoBase64 = cursor.getString(idxFoto);
                        byte[] decodedString = android.util.Base64.decode(fotoBase64, android.util.Base64.DEFAULT);
                        holder.imageAnexo.setImageBitmap(android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                    }
                    cursor.close();
                }
            } catch (Exception ignored) {}
        }

        String metricasStr = "Média: " + item.getMediaMensal() +
                " | Penúltima: " + item.getPenultimaCompra() +
                " | Última: " + item.getUltimaCompra();
        holder.tvMetricasGiro.setText(metricasStr);

        holder.btnAdicionar.setText(item.getTextoBotao());

        // MANTÉM O CARD E O BOTÃO COM O LAYOUT PADRÃO UNIFORME
        holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        holder.btnAdicionar.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.colorPrimary)));

        // EXIBE APENAS OS ÍCONES E O CAMPO DO DIAGNÓSTICO COMO DESTAQUE VISUAL
        if (item.getDiagnostico() != null && !item.getDiagnostico().trim().isEmpty()) {
            holder.tvDiagnostico.setVisibility(View.VISIBLE);
            holder.tvDiagnostico.setText(item.getDiagnostico());
            if ("ALERTA_MAXIMO".equalsIgnoreCase(item.getStatusGiro())) {
                holder.tvDiagnostico.setTextColor(Color.parseColor("#DC2626"));
            } else if ("QUEDA_VOLUME".equalsIgnoreCase(item.getStatusGiro())) {
                holder.tvDiagnostico.setTextColor(Color.parseColor("#B45309"));
            } else {
                holder.tvDiagnostico.setTextColor(Color.parseColor("#1E293B"));
            }
        } else {
            holder.tvDiagnostico.setVisibility(View.GONE);
        }

        holder.btnAdicionar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAdicionarProduto(item);
            } else {
                Toast.makeText(context, "Item adicionado: " + item.getNomeProduto(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos != null ? listaProdutos.size() : 0;
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageAnexo;
        TextView tvNomeProduto, tvMetricasGiro, tvDiagnostico;
        Button btnAdicionar;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardProdutoFicha);
            imageAnexo = itemView.findViewById(R.id.imageAnexo);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeFichaProduto);
            tvMetricasGiro = itemView.findViewById(R.id.tvMetricasGiroProduto);
            tvDiagnostico = itemView.findViewById(R.id.tvDiagnosticoProduto);
            btnAdicionar = itemView.findViewById(R.id.btnAdicionarProdutoFicha);
        }
    }
}