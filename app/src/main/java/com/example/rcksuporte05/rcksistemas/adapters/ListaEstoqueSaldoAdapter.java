package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.EstoqueSaldoViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.EstoqueSaldo;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaEstoqueSaldoAdapter extends RecyclerView.Adapter<EstoqueSaldoViewHolder> {
    private Activity context;
    private List<EstoqueSaldo> lista;
    private Listener listener;

    public ListaEstoqueSaldoAdapter(List<EstoqueSaldo> lista, Listener listener, Activity context){
        this.lista = lista;
        this.listener = listener;
        this.context = context;

    }
    @NonNull
    @Override
    public EstoqueSaldoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.estoque_saldo_lista, parent, false);
        return new EstoqueSaldoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EstoqueSaldoViewHolder holder, int position) {
        if ( !lista.get(position).getAtivo().equalsIgnoreCase("S")) {
            holder.idProduto.setTextColor(Color.RED);
            holder.txtCodigoBarra.setTextColor(Color.RED);
            holder.nomeListaProduto.setTextColor(Color.RED);
            holder.textUN.setTextColor(Color.RED);
            holder.txtSaldoEstoque.setTextColor(Color.RED);
            holder.txtSaldoReservado.setTextColor(Color.RED);
            holder.imgProdutoAtivoBloqueado.setImageResource(R.drawable.ic_cliente_bloqueado);
        } else {
            holder.idProduto.setTextColor(Color.parseColor("#000000"));
            holder.txtCodigoBarra.setTextColor(Color.parseColor("#000000"));
            holder.nomeListaProduto.setTextColor(Color.parseColor("#000000"));
            holder.textUN.setTextColor(Color.parseColor("#000000"));
            holder.txtSaldoEstoque.setTextColor(Color.parseColor("#000000"));
            holder.txtSaldoReservado.setTextColor(Color.parseColor("#000000"));
            holder.imgProdutoAtivoBloqueado.setImageResource(R.drawable.ic_cliente_ativo);
        }

        try {
            if ( Float.parseFloat(lista.get(position).getReservado()) > Float.parseFloat( lista.get(position).getSaldo()) && lista.get(position).getAtivo().equalsIgnoreCase("S") )
                //holder.nomeListaProduto.setTextColor(Color.RED);
                holder.imgProdutoAtivoBloqueado.setImageResource(R.drawable.ic_cliente_atencao);
        } catch ( NullPointerException|NumberFormatException e) {

        }


        try {
            holder.txtSaldoReservado.setText("" + MascaraUtil.mascaraVirgula(lista.get(position).getReservado()).replace(",00", ""));
        } catch ( NullPointerException e) {
            holder.txtSaldoReservado.setText("" + MascaraUtil.mascaraVirgula("0").replace(",00", ""));
        }
        holder.idProduto.setText(lista.get(position).getId_produto());
        if (lista.get(position).getCod_em_barras() != null && !lista.get(position).getCod_em_barras().equals(""))
            holder.txtCodigoBarra.setText("EAN: " +  lista.get(position).getCod_em_barras());

        holder.nomeListaProduto.setText(lista.get(position).getNome_produto());
        holder.textUN.setText(lista.get(position).getUnidade());
        if (lista.get(position).getSaldo().toString().contains("-"))
            holder.txtSaldoEstoque.setText(" 0");
        else
            holder.txtSaldoEstoque.setText("" + MascaraUtil.mascaraVirgula(lista.get(position).getSaldo()).replace(",00", ""));

        applyCLickEnvents(holder, position);
        System.gc();
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        return 0;
    }

    private void applyCLickEnvents(final EstoqueSaldoViewHolder holder, final int position) {
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
    }

    public interface Listener {
        void onClickListener(int position);
        void onLongClickListener(int position);
    }

}
