package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ListaUsuarioViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.ProdutoGrupo;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaUsuarioAdapter extends RecyclerView.Adapter<ListaUsuarioViewHolder>{

    private Context context;
    private List<Usuario> lista;
    private ListaUsuarioAdapter.Listener listener;

    public ListaUsuarioAdapter(Context context, List<Usuario> lista, ListaUsuarioAdapter.Listener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ListaUsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_usuarios, viewGroup, false);
        return new ListaUsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaUsuarioViewHolder holder, int position) {
        //holder.txvUsuario.setText( lista.get(position).getNome_usuario().toUpperCase());
        holder.edtLogin.setText( lista.get(position).getLogin());
        holder.edtSenha.setText( lista.get(position).getSenha());
        try {
            holder.txvSincCliente.setText(MascaraUtil.formataData( lista.get(position).getData_sincronia_cliente(), "/", 8));
            holder.txvSincProduto.setText(MascaraUtil.formataData( lista.get(position).getData_sincronia_produto(), "/", 8));
            holder.txvSincImagem.setText(MascaraUtil.formataData( lista.get(position).getData_sincronia_imagem(), "/", 8));
            holder.txvSincCobranca.setText(MascaraUtil.formataData( lista.get(position).getData_sincronia_cobranca(), "/", 8));
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        holder.edtEmail.setText(" -- " );
        try {
            if (!lista.get(position).getEmail_vendedor().isEmpty())
                holder.edtEmail.setText( lista.get(position).getEmail_vendedor().toLowerCase());
        } catch ( NullPointerException e) {
        }

        holder.txvVersaoApp.setText("Versão do app:  -- " );
        try {
            if (!lista.get(position).getAparelho_versao_app().isEmpty())
                holder.txvVersaoApp.setText("Versão do app:  " + lista.get(position).getAparelho_versao_app());
        } catch ( NullPointerException e) {
        }

        try {
            holder.swtAtivo.setChecked(!lista.get(position).getAtivo().isEmpty() && !lista.get(position).getAtivo().equalsIgnoreCase("N") ? true : false);
        } catch ( NullPointerException e) {
            holder.swtAtivo.setChecked(false);
        }
        try {
            holder.swtBiometria.setChecked( !lista.get(position).getUsuario_permite_biometria().isEmpty() && !lista.get(position).getUsuario_permite_biometria().equalsIgnoreCase("N") ? true : false);
        } catch ( NullPointerException e) {
            holder.swtBiometria.setChecked(false);
        }

        try {
            holder.edtSaldoVerba.setText(MascaraUtil.mascaraReal(lista.get(position).getSaldo_verba()));
            if ( Float.parseFloat(lista.get(position).getSaldo_verba()) < 0.00f) {
               holder.edtSaldoVerba.setTextColor(Color.RED);
            }
            if ( !lista.get(position).getData_atualizao_verba().isEmpty()) {
                holder.edtDataVerba.setText(MascaraUtil.formataData(lista.get(position).getData_atualizao_verba(), "/", 8));
            }
        } catch ( NullPointerException | NumberFormatException e) {
            holder.edtSaldoVerba.setText("RS 0,00");
        } catch ( Exception e) {
            holder.edtSaldoVerba.setText("RS 0,00");
        }
        setAvalicaoApp(holder, lista.get(position).getUsuario_avaliacao_app(), false);
        applyCLickEnvents(holder, position);
        System.gc();
    }

    @Override
    public int getItemCount() {
        if ( lista.size() > 0)
            return lista.size();
        return 0;
    }

    private void setAvalicaoApp( ListaUsuarioViewHolder holder, int avalicao, boolean isClick) {
        try {
            if ( avalicao > 0 ) {
                if (avalicao == 1) {
                    holder.img_avalia_1.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_1.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_2.setTag(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_3.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_3.setTag(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_4.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_4.setTag(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_5.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_5.setTag(R.drawable.ic_avaliacao_negativa);
                } else if (avalicao == 2) {
                    holder.img_avalia_1.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_1.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_3.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_3.setTag(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_4.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_4.setTag(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_5.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_5.setTag(R.drawable.ic_avaliacao_negativa);
                } else if (avalicao == 3) {
                    holder.img_avalia_1.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_1.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_3.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_3.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_4.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_4.setTag(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_5.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_5.setTag(R.drawable.ic_avaliacao_negativa);
                } else if ( avalicao == 4) {
                    holder.img_avalia_1.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_1.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_3.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_3.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_4.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_4.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_5.setImageResource(R.drawable.ic_avaliacao_negativa);
                    holder.img_avalia_5.setTag(R.drawable.ic_avaliacao_negativa);
                } else if ( avalicao == 5) {
                    holder.img_avalia_1.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_1.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_2.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_3.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_3.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_4.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_4.setTag(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_5.setImageResource(R.drawable.ic_avaliacao_positiva);
                    holder.img_avalia_5.setTag(R.drawable.ic_avaliacao_positiva);

                }

            } else {
                holder.img_avalia_1.setImageResource(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_1.setTag(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_2.setImageResource(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_2.setTag(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_3.setImageResource(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_3.setTag(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_4.setImageResource(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_4.setTag(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_5.setImageResource(R.drawable.ic_avaliacao_negativa);
                holder.img_avalia_5.setTag(R.drawable.ic_avaliacao_negativa);
            }
        }catch ( NullPointerException e) {
            holder.img_avalia_1.setImageResource(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_1.setTag(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_2.setImageResource(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_2.setTag(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_3.setImageResource(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_3.setTag(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_4.setImageResource(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_4.setTag(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_5.setImageResource(R.drawable.ic_avaliacao_negativa);
            holder.img_avalia_5.setTag(R.drawable.ic_avaliacao_negativa);
        }
    }


    private void applyCLickEnvents(final ListaUsuarioViewHolder holder, final int position) {
        /*
        holder.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

         */

    }
    public interface Listener {
        void onClickListener(int position);
    }
}
