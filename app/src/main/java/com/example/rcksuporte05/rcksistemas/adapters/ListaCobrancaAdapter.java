package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityCobranca;
import com.example.rcksuporte05.rcksistemas.activity.ActivityCobrancaRecebimento;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.CobrancaViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.FinanceiroViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaCobrancaAdapter extends RecyclerView.Adapter<CobrancaViewHolder> {

    private Activity context;
    private List<FinanceiroResumo> listaCobranca;
    private CobrancaAdapterListener listener;
    private FinanceiroViewHolder holder;
    private SparseBooleanArray selectedItems;
    int numCols;
    public ListaCobrancaAdapter(Activity context, List<FinanceiroResumo> listaCobranca, CobrancaAdapterListener listener) {
        this.listaCobranca = listaCobranca;
        this.listener = listener;
        this.context = context;
        this.selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public CobrancaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_cobranca_new, parent, false);
        return new CobrancaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CobrancaViewHolder holder, int position) {


        holder.imgStatus.setVisibility(View.INVISIBLE);
        holder.imgCheck.setVisibility(View.INVISIBLE);
        holder.imgReagendado.setVisibility(View.INVISIBLE);
        holder.imgStatusCliente.setVisibility(View.INVISIBLE);
        holder.txtRazaoSocial.setText( listaCobranca.get(position).getNome_favorecido() + " - " + listaCobranca.get(position).getId_favorecido());
        try {
            holder.txtBairro.setText(listaCobranca.get(position).getEndereco_bairro().toUpperCase());
        } catch ( NullPointerException e) {
            holder.txtBairro.setText("-");
        }
        if ( !listaCobranca.get(position).getEndereco_complemento().isEmpty())
              holder.txtEndereco.setText( listaCobranca.get(position).getEndereco() + " Nº " + listaCobranca.get(position).getEndereco_numero() + " - " +
               listaCobranca.get(position).getEndereco_complemento().toUpperCase());
        else{
            holder.txtEndereco.setText( listaCobranca.get(position).getEndereco() + " Nº " + listaCobranca.get(position).getEndereco_numero() );
        }
        //holder.txvBairro.setText( listaCobranca.get(position).getEndereco_bairro() );
        try {
            holder.txtTelefone.setText( MascaraUtil.formataTelefone(listaCobranca.get(position).getTelefone_principal()) );
            if ( !listaCobranca.get(position).getTelefone_dois().isEmpty()) {
                holder.txtTelefone.setText( MascaraUtil.formataTelefone(listaCobranca.get(position).getTelefone_principal()) + "  -  " + MascaraUtil.formataTelefone(listaCobranca.get(position).getTelefone_dois()));
            }
        } catch ( NullPointerException e) {
        }
        holder.txvPagamento.setText("");
        if ( listaCobranca.get(position).getDevedor().equalsIgnoreCase("S")) {
            holder.txtVencimento.setText(MascaraUtil.formataData(listaCobranca.get(position).getData_vencimento(), "/", 10));
            try {
                holder.txvReagendamento.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(listaCobranca.get(position).getData_reagendamento())));
                holder.imgReagendado.setVisibility(View.VISIBLE);
            } catch (NullPointerException | ParseException e) {
                holder.txvReagendamento.setText("");
            }
            try {
                holder.txvValorDebito.setText(MascaraUtil.mascaraReal(listaCobranca.get(position).getValor_total()));
                holder.txvValorSaldo.setText(MascaraUtil.mascaraReal(listaCobranca.get(position).getValor_debito()));
                holder.txvValorRecebido.setText(MascaraUtil.mascaraReal("R$ 0,00"));
                try {
                    holder.txvValorRecebido.setText(MascaraUtil.mascaraReal(listaCobranca.get(position).getValor_recebimento()));
                    if (Float.parseFloat(listaCobranca.get(position).getValor_recebimento()) > 0.00f) {
                        holder.txvPagamento.setText(MascaraUtil.formataData(listaCobranca.get(position).getData_baixa(), "/", 10));
                        holder.imgCheck.setVisibility(View.VISIBLE);
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    e.printStackTrace();
                    //holder.imgCheck.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    //holder.imgCheck.setVisibility(View.INVISIBLE);
                }
            } catch (NullPointerException | NumberFormatException e) {
                e.printStackTrace();
                //holder.txvValorOperacao.setText("R$ 0,00");
            } catch (Exception e) {
                e.printStackTrace();
                //holder.txvValorOperacao.setText("R$ 0,00");
            }
        }
        try {
            //if (!listaCobranca.get(position).getNome_favorecido().isEmpty())
            //    holder.txvTotalDocs.setText( listaCobranca.get(position).getNome_conta() +
            //            "\nEmissão: " + MascaraUtil.formataData(listaCobranca.get(position).getData_emissao())
            //            + "  |  Vencimento: " + MascaraUtil.formataData(listaCobranca.get(position).getData_vencimento()) );
            if (!listaCobranca.get(position).getNome_favorecido().isEmpty()) {
                //holder.txvUltimaCompra.setText("Compras(s): " + listaCobranca.get(position).getTotal_docs() + "  |    Ultima: " + MascaraUtil.formataData(listaCobranca.get(position).getData_emissao(), "/", 10));
                String ttotal_compras   =  listaCobranca.get(position).getTotal_compras() == null ? "0" : listaCobranca.get(position).getTotal_compras();
                String tdata_ult_compra = listaCobranca.get(position).getData_ult_compra() == null || listaCobranca.get(position).getData_ult_compra().isEmpty() ? "" : MascaraUtil.formataData(listaCobranca.get(position).getData_ult_compra(), "/", 10);
                String total_compras = "Nenhuma compra efetuada";
                if ( Integer.parseInt(ttotal_compras ) == 0) {
                    //total_compras   = ttotal_compras + "Nenhuma compra efetuada";
                } else if (Integer.parseInt(ttotal_compras) == 1) {
                    total_compras   = ttotal_compras + " compra feita em: " + tdata_ult_compra;
                } else if(Integer.parseInt(ttotal_compras) > 1) {
                    total_compras   = ttotal_compras + " compras ultima em: " + tdata_ult_compra;
                }
                holder.txvUltimaCompra.setText(total_compras);
                //}
            }


        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }

        try {
            if (listaCobranca.get(position).getStatus().equalsIgnoreCase("A")) {
                holder.imgStatus.setVisibility(View.VISIBLE);
            } else {

            }
        } catch ( NullPointerException e) {

        } catch ( Exception e) {

        }


        try {
            if (listaCobranca.get(position).getAtivo().equalsIgnoreCase("N")) {
                holder.imgStatusCliente.setVisibility(View.VISIBLE);
            } else {

            }
        } catch ( NullPointerException e) {

        } catch ( Exception e) {

        }
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        applyCLickEnvents(holder, position);
        System.gc();


    }

    @Override
    public int getItemCount() {
        if (listaCobranca != null)
            return listaCobranca.size();
        return 0;
    }


    public int setNumCols(int qtdCols) {
        return numCols = qtdCols;
    }
    @Override
    public int getItemViewType(int position) {
        if (listaCobranca.size() == 1)
            return 0;
        else {
            if (listaCobranca.size() % numCols == 0)
                return 1;
            else
                return (position > 1 && position == listaCobranca.size() - 1) ? 0 : 1;
        }
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false))
            selectedItems.delete(pos);
        else
            selectedItems.put(pos, true);
        notifyItemChanged(pos);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public int getSelectdItem(){
        return 0;
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface CobrancaAdapterListener {
        void onClickListener(int position);
        void onClickNovoPedido(int position);
        void onLongClickListener(int position);
        void onGPSClickListener(int position);
        void onTelefoneClickListener(int position);
    }

    public List<FinanceiroResumo> getItensSelecionados() {
        List<FinanceiroResumo> titulosSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++)
            titulosSelecionados.add(listaCobranca.get(selectedItems.keyAt(i)));
        return titulosSelecionados;
    }
    private void applyCLickEnvents(CobrancaViewHolder holder, final int position) {
        holder.txtRazaoSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });
        /*
        holder.txtCodigoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });

         */
        holder.descricaoTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });

        holder.txtTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });

        holder.descricaoBairro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });
        holder.txtBairro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });


        holder.descricaoEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });
        holder.txtEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });
        holder.lyDadosCobranca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  listener.onClickListener(position);
            }
        });


        holder.txtRazaoSocial.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        holder.descricaoTelefone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });


        holder.txtTelefone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        holder.descricaoEndereco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });


        holder.txtEndereco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        holder.descricaoEndereco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });


        holder.txtEndereco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        holder.lyDadosCobranca.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    listener.onLongClickListener(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        holder.lytNovoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNovoPedido(position);
            }
        });
        holder.txtNovoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNovoPedido(position);
            }
        });
        holder.btnNovoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNovoPedido(position);
            }
        });


        holder.ivGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGPSClickListener(position);
            }
        });

        holder.ivChamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTelefoneClickListener(position);
            }
        });
    }


}
