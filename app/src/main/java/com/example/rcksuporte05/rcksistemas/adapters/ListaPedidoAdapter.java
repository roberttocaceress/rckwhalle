package com.example.rcksuporte05.rcksistemas.adapters;

import static android.view.View.INVISIBLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.PedidoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class ListaPedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> {
    private List<WebPedido> pedidos;
    private PedidoAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private Activity activity;
    private String id_vendedor;
    private DBHelper db;
    int numCols;

    // --- CACHES DE PERFORMANCE ---
    private final Map<String, String> cacheOperacoes = new HashMap<>();
    private final Map<String, String> cachePagamentos = new HashMap<>();
    private final Map<String, String> cacheMoedas = new HashMap<>();
    private final Map<String, String> cacheEmpresas = new HashMap<>();
    private final SimpleDateFormat sdfSql = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdfBr = new SimpleDateFormat("dd/MM/yyyy");

    public ListaPedidoAdapter(List<WebPedido> pedidos, PedidoAdapterListener listener, Activity activity) {
        this.pedidos = pedidos;
        this.selectedItems = new SparseBooleanArray();
        this.listener = listener;
        this.activity = activity;
        this.db = new DBHelper(activity);
    }

    @SuppressLint("Range")
    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // NOVO: Usando o layout Premium 2026
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pedido_lista_2026, parent, false);

        if (id_vendedor == null) {
            Cursor cursor = db.listaDados("SELECT U.*, L.TOKEN FROM TBL_WEB_USUARIO U INNER JOIN TBL_LOGIN L ON U.LOGIN = L.LOGIN WHERE L.LOGADO = 'S'");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                id_vendedor = cursor.getString(cursor.getColumnIndex("ID_QUANDO_VENDEDOR"));
            }
            cursor.close();
        }
        return new PedidoViewHolder(itemView, db, parent.getContext());
    }
    @SuppressLint({"Range", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.abandonado.getLayoutParams();
        params.height = 0;//ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.abandonado.setLayoutParams(params);
        holder.btnLinhaTempo.setVisibility(View.GONE);
        holder.btnCopiaSegura.setVisibility(View.VISIBLE);
        if ( pedidos.get(position).getId_web_pedido_servidor() != null && !pedidos.get(position).getId_web_pedido_servidor().equals("")) {
            holder.txtIdPedido.setText("Nº " + MascaraUtil.numeroZeros(pedidos.get(position).getId_web_pedido_servidor(), 5));
            holder.btnExcluir.setVisibility(View.GONE);
            holder.btnEnviar.setVisibility(View.GONE);
            holder.btnCopiaSegura.setVisibility(View.GONE);
            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_pendente));
            holder.txtStatus.setText("");
            holder.btnLinhaTempo.setVisibility(View.VISIBLE);
            try {
                if ( ( pedidos.get(position).getAnalise_credito().equalsIgnoreCase("B")
                        || pedidos.get(position).getAnalise_credito().equalsIgnoreCase("S"))
                        || pedidos.get(position).getExcluido().equalsIgnoreCase("S")) {
                    holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_excluido_negado));
                    if ( pedidos.get(position).getAnalise_credito().equalsIgnoreCase("B"))
                        holder.txtStatus.setText("Crédito negado".toUpperCase());
                    else if ( pedidos.get(position).getAnalise_credito().equalsIgnoreCase("S")) {
                        holder.txtStatus.setText("Crédito autorizado".toUpperCase());
                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                        try {
                            if (Integer.parseInt(pedidos.get(position).getId_nota_fiscal()) > 0) {
                                try {
                                    if (pedidos.get(position).getFaturado().equalsIgnoreCase("S")) {
                                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_faturado)); //mudado para cor_pedido_autorizado-faturado
                                        holder.txtStatus.setText("Faturado".toUpperCase());
                                    } else {
                                        if ( pedidos.get(position).getAnalise_credito().equalsIgnoreCase("S")) {
                                            holder.txtStatus.setText("Crédito autorizado".toUpperCase());
                                            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                                        } else {
                                            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                                            holder.txtStatus.setText("Em ánalise".toUpperCase());
                                        }
                                    }
                                } catch (NullPointerException e) {
                                    if ( pedidos.get(position).getAnalise_credito().equalsIgnoreCase("S")) {
                                        holder.txtStatus.setText("Crédito autorizado".toUpperCase());
                                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                                    } else {
                                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                                        holder.txtStatus.setText("Em ánalise".toUpperCase());
                                    }
                                }
                            }
                        } catch ( NumberFormatException|NullPointerException e) {
                                 e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if( pedidos.get(position).getExcluido().equalsIgnoreCase("S")) {
                            holder.txtStatus.setText("Cancelado/Excluido".toUpperCase());
                            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_excluido_negado));
                        }
                    }else if( pedidos.get(position).getExcluido().equalsIgnoreCase("S")) {
                        holder.txtStatus.setText("Cancelado/Excluido".toUpperCase());
                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_excluido_negado));
                    }
                } else {
                    try {
                        try {
                            if (pedidos.get(position).getFaturado().equalsIgnoreCase("S")) {
                                holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_faturado)); //mudado para cor_pedido_autorizado-faturado
                                holder.txtStatus.setText("Faturado".toUpperCase());
                            } else {
                                holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                                holder.txtStatus.setText("Em ánalise".toUpperCase());
                            }
                        } catch (NullPointerException e) {
                            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_faturado)); //mudado para cor_pedido_autorizado-faturado
                            holder.txtStatus.setText("Faturado".toUpperCase());
                        }
                    } catch ( NullPointerException e ) {
                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                        holder.txtStatus.setText("Em ánalise".toUpperCase());
                    } catch ( Exception e) {
                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                        holder.txtStatus.setText("Em ánalise".toUpperCase());
                    }
                }

            } catch ( NullPointerException e ) {
                holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                holder.txtStatus.setText("Em ánalise".toUpperCase());
            } catch ( Exception e) {
                holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_analise));
                holder.txtStatus.setText("Em ánalise".toUpperCase());
            }
        } else {
            if ( pedidos.get(position).getExcluido().equalsIgnoreCase("S")) {
                holder.txtStatus.setVisibility(INVISIBLE);
                holder.btnLinhaTempo.setVisibility(INVISIBLE);
                holder.btnCopiaSegura.setVisibility(INVISIBLE);
                holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_excluido_negado));
                holder.txtIdPedido.setText("Nº " + MascaraUtil.numeroZeros(pedidos.get(position).getId_web_pedido(), 5));
                holder.txvAbandonado.setText("LIXEIRA");
                holder.abandonado.setBackgroundColor(Color.parseColor("#F34F4F"));
                holder.cor.setBackgroundColor(Color.parseColor("#F34F4F"));
                holder.abandonado.setVisibility(View.VISIBLE);
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.abandonado.setLayoutParams(params);
                holder.btnExcluir.setBackgroundResource(R.drawable.ic_action_remove_pedido_lixeira);


            } else {
                holder.txtStatus.setVisibility(INVISIBLE);
                holder.btnLinhaTempo.setVisibility(INVISIBLE);
                holder.btnCopiaSegura.setVisibility(View.VISIBLE);
                try {
                    if (pedidos.get(position).getPedido_enviado().equalsIgnoreCase("N")
                            && (pedidos.get(position).getId_web_pedido_servidor() != null
                            && !pedidos.get(position).getId_web_pedido_servidor().equals("")))
                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_atencao));
                    else
                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_pendente));
                    if (pedidos.get(position).getDescontoIndevido().equalsIgnoreCase("S"))
                        holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_atencao));
                } catch (NullPointerException e) {
                    holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_pendente));
                } catch (Exception e) {
                    holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_pendente));
                }
                holder.txtIdPedido.setText("Nº " + MascaraUtil.numeroZeros(pedidos.get(position).getId_web_pedido(), 5));
            }
        }
        if (pedidos.get(position).getFinalizado() != null && !pedidos.get(position).getFinalizado().equals("S")) {
            holder.btnEnviar.setVisibility(View.GONE);
            holder.btnCompartilhar.setVisibility(View.GONE);
        }
        if ( pedidos.get(position).getCadastro().getId_cadastro() == 0) {
            holder.txtNomeCliente.setText(pedidos.get(position).getNome_extenso().trim());
            holder.txtFantasiaCliente.setText("-");
            holder.btnEnviar.setVisibility(View.GONE);
            holder.btnDuplic.setVisibility(View.GONE);
            holder.btnCompartilhar.setVisibility(View.VISIBLE);
            holder.txtNomeCliente.setTextColor(Color.RED);
        } else {
            holder.txtNomeCliente.setText(pedidos.get(position).getCadastro().getNome_cadastro().trim());
            try {
                holder.txtFantasiaCliente.setText(pedidos.get(position).getCadastro().getNome_fantasia().trim());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if ( pedidos.get(position).getId_web_pedido_servidor() != null
                    && !pedidos.get(position).getId_web_pedido_servidor().equals("")) {
                holder.btnEnviar.setVisibility(INVISIBLE);
            } else {
                holder.btnEnviar.setVisibility(View.VISIBLE);
            }
            holder.btnDuplic.setVisibility(View.VISIBLE);
            holder.btnCompartilhar.setVisibility(View.VISIBLE);
            holder.txtNomeCliente.setTextColor(Color.parseColor("#000000"));
        }

        // Lógica de Empresa com Cache para Performance
        if (holder.txtEmpresaPedido != null) {
            String idEmpresa = pedidos.get(position).getId_empresa();
            if (idEmpresa != null && !idEmpresa.isEmpty() && !idEmpresa.equals("0")) {
                if (cacheEmpresas.containsKey(idEmpresa)) {
                    holder.txtEmpresaPedido.setText(cacheEmpresas.get(idEmpresa));
                } else {
                    try {
                        String razaoSocial = db.consultaDados("SELECT RAZAO_SOCIAL FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + idEmpresa + "';", "RAZAO_SOCIAL");
                        String nomeMostrar = (razaoSocial != null && !razaoSocial.isEmpty()) ? razaoSocial.trim() : "Empresa (" + idEmpresa + ")";
                        cacheEmpresas.put(idEmpresa, nomeMostrar);
                        holder.txtEmpresaPedido.setText(nomeMostrar);
                    } catch (Exception e) {
                        holder.txtEmpresaPedido.setText("Empresa: " + idEmpresa);
                    }
                }
                holder.txtEmpresaPedido.setVisibility(View.VISIBLE);
            } else {
                holder.txtEmpresaPedido.setText("Empresa padrão");
                holder.txtEmpresaPedido.setVisibility(View.VISIBLE);
            }
        }

        if (pedidos.get(position).getValor_total() != null)
            holder.txtPrecoPedido.setText(MascaraUtil.mascaraReal(pedidos.get(position).getValor_total()));
        else
            holder.txtPrecoPedido.setText("");

        // --- OTIMIZAÇÃO: REUSO DE DATE FORMAT ---
        try {
            if (pedidos.get(position).getData_emissao() != null) {
                holder.txtDataEmissaoPedido.setText(sdfBr.format(sdfSql.parse(pedidos.get(position).getData_emissao())));
            }
            if (pedidos.get(position).getData_prev_entrega() != null) {
                holder.txtDataEntrega.setText(sdfBr.format(sdfSql.parse(pedidos.get(position).getData_prev_entrega())));
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        try {
            holder.txtDataEmissaoPedido.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_emissao())));
            holder.txtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_prev_entrega())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        try {
            if (pedidos.get(position).getIsOnLine() != null && !pedidos.get(position).getIsOnLine().isEmpty()) {

            } else {
                if ((pedidos.get(position).getId_pedido_venda() == null || pedidos.get(position).getId_pedido_venda().isEmpty()) && pedidos.get(position).getId_nota_fiscal() == null || pedidos.get(position).getId_nota_fiscal().isEmpty()) {
                    holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_nao_importado));
                    holder.txtStatus.setText("Não importado no ERP".toUpperCase());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Float.parseFloat(pedidos.get(position).getValor_total()) <= 0.00f) {
                holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_atencao));
            }
        } catch ( NullPointerException|NumberFormatException e) {
            e.printStackTrace();
        }
        holder.itemView
               .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);


        // --- OTIMIZAÇÃO: CACHE DE OPERAÇÃO, PAGAMENTO E MOEDA (EVITA BANCO NO SCROLL) ---
        try {
            String idOp = pedidos.get(position).getId_operacao();
            if (idOp != null) {
                String nomeOp = cacheOperacoes.get(idOp);
                if (nomeOp == null) {
                    nomeOp = db.consultaDados("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + idOp + ";", "NOME_OPERACAO");
                    if (nomeOp != null) cacheOperacoes.put(idOp, nomeOp);
                }
                holder.txtOperacao.setText(nomeOp);
            }

            String idCond = pedidos.get(position).getId_condicao_pagamento();
            if (idCond != null && !idCond.equals("0") && !idCond.isEmpty()) {
                String nomeCond = cachePagamentos.get(idCond);
                if (nomeCond == null) {
                    nomeCond = db.consultaDados("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + idCond + ";", "NOME_CONDICAO");
                    if (nomeCond != null) cachePagamentos.put(idCond, nomeCond);
                }
                holder.txtCondicaoPagamento.setText(nomeCond);
            } else {
                holder.txtCondicaoPagamento.setText("");
            }

            String idMoeda = pedidos.get(position).getId_moeda_padrao();
            if (idMoeda != null && !idMoeda.equals("0") && !idMoeda.isEmpty()) {
                String nomeMoeda = cacheMoedas.get(idMoeda);
                if (nomeMoeda == null) {
                    nomeMoeda = db.consultaDados("SELECT * FROM TBL_MOEDA WHERE ID = '" + idMoeda + "';", "NOME_MOEDA");
                    if (nomeMoeda != null) cacheMoedas.put(idMoeda, nomeMoeda);
                }
                holder.txtMoeda.setText(nomeMoeda);
            } else {
                holder.txtMoeda.setText("");
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        try {
            holder.txtOperacao.setText(db.consultaDados("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + pedidos.get(position).getId_operacao() + ";", "NOME_OPERACAO"));
            if (!pedidos.get(position).getId_condicao_pagamento().equals("0")) {
                if (pedidos.get(position).getId_condicao_pagamento() == null || pedidos.get(position).getId_condicao_pagamento().isEmpty()) {
                    holder.txtCondicaoPagamento.setText("");
                } else{
                    holder.txtCondicaoPagamento.setText(db.consultaDados("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + pedidos.get(position).getId_condicao_pagamento() + ";", "NOME_CONDICAO"));
                }
            }else
                holder.txtCondicaoPagamento.setText("");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (!pedidos.get(position).getId_moeda_padrao().equals("0"))
                holder.txtMoeda.setText(db.consultaDados("SELECT * FROM TBL_MOEDA WHERE ID = " + pedidos.get(position).getId_moeda_padrao() + ";", "NOME_MOEDA"));
            else
                holder.txtMoeda.setText("");
        } catch ( NullPointerException e) {
            holder.txtMoeda.setText("");
        }catch (Exception e) {
            holder.txtMoeda.setText("");
        }
        */
        try {
            if ( pedidos.get(position).getExcluido().equalsIgnoreCase("S")) {
                holder.abandonado.setVisibility(View.VISIBLE);
                holder.txtPrecoPedido.setVisibility(INVISIBLE);
                holder.lyEntrega.setVisibility(INVISIBLE);
                holder.btnEnviar.setVisibility(INVISIBLE);
                holder.btnCompartilhar.setVisibility(INVISIBLE);
                holder.btnDuplic.setVisibility(INVISIBLE);
                holder.btnCopiaSegura.setVisibility(INVISIBLE);
                holder.txtPrecoPedido.setVisibility(View.VISIBLE);
                holder.lyEntrega.setVisibility(View.VISIBLE);
            } else {
                if (pedidos.get(position).getFinalizado().equals("N") || pedidos.get(position).getDescontoIndevido().equalsIgnoreCase("S")) {
                    if (pedidos.get(position).getDescontoIndevido().equalsIgnoreCase("S")) {
                        holder.txvAbandonado.setText("DESCONTO INDEVIDO");
                        holder.abandonado.setBackgroundColor(Color.parseColor("#FF8C00"));
                        holder.cor.setBackgroundColor(Color.parseColor("#FF8C00"));

                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        holder.abandonado.setLayoutParams(params);
                    } else {
                        holder.txvAbandonado.setText("ABANDONADO");
                        holder.abandonado.setBackgroundColor(Color.parseColor("#F34F4F"));
                        holder.cor.setBackgroundColor(Color.parseColor("#F34F4F"));
                       // holder.cor.setVisibility(View.INVISIBLE);
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        holder.abandonado.setLayoutParams(params);

                    }
                    holder.abandonado.setVisibility(View.VISIBLE);
                    holder.txtPrecoPedido.setVisibility(INVISIBLE);
                    holder.lyEntrega.setVisibility(INVISIBLE);
                    holder.btnEnviar.setVisibility(INVISIBLE);
                    holder.btnCompartilhar.setVisibility(INVISIBLE);
                    holder.btnDuplic.setVisibility(INVISIBLE);
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.abandonado.setLayoutParams(params);
                } else {
                    holder.abandonado.setVisibility(INVISIBLE);
                    holder.txtPrecoPedido.setVisibility(View.VISIBLE);
                    holder.lyEntrega.setVisibility(View.VISIBLE);
                }
            }
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        try {
            if ( !pedidos.get(position).getId_vendedor().equalsIgnoreCase(pedidos.get(position).getId_outro_vendedor()) ) {
                holder.cor.setBackground(activity.getDrawable(R.color.preto));
            }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        }
        //holder.itemView.setActivated(selectedItems.get(position, false));
        holder.btnExcluir.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                View.OnClickListener l = listener.onClickExcluir(pos);
                if (l != null) l.onClick(v);
            }
        });
        holder.btnEnviar.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                View.OnClickListener l = listener.onClickEnviar(pos);
                if (l != null) l.onClick(v);
            }
        });
        holder.btnDuplic.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                View.OnClickListener l = listener.onClickDuplic(pos);
                if (l != null) l.onClick(v);
            }
        });
        holder.btnCompartilhar.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                View.OnClickListener l = listener.onClickCompartilhar(pos);
                if (l != null) l.onClick(v);
            }
        });
        holder.btnRastreio.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                View.OnClickListener l = listener.onClickRastrear(pos);
                if (l != null) l.onClick(v);
            }
        });
        holder.btnLinhaTempo.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                View.OnClickListener l = listener.onClickLinhaTempo(pos);
                if (l != null) l.onClick(v);
            }
        });
        holder.btnCopiaSegura.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                View.OnClickListener l = listener.onClickCopiaSegura(pos);
                if (l != null) l.onClick(v);
            }
        });
        try {
            if (pedidos.get(position).getIsOnLine() == null || pedidos.get(position).getIsOnLine().isEmpty()) {
                applyClickEvents(holder, position);
            } else {
                if( pedidos.get(position).getIsOnLine().equalsIgnoreCase("S")) {
                    holder.btnExcluir.setVisibility(INVISIBLE);
                    holder.btnEnviar.setVisibility(INVISIBLE);
                    holder.btnDuplic.setVisibility(INVISIBLE);
                    holder.btnCompartilhar.setVisibility(INVISIBLE);
                    holder.btnRastreio.setVisibility(INVISIBLE);
                    holder.btnLinhaTempo.setVisibility(INVISIBLE);
                    holder.btnCopiaSegura.setVisibility(INVISIBLE);
                    holder.txtStatus.setText(  holder.txtStatus.getText().toString().trim() + "\n" + (pedidos.get(position).getUsuario_lancamento_nome().trim()));
                } else {
                    applyClickEvents(holder, position);
                }
            }
        } catch (NullPointerException e) {
            applyClickEvents(holder, position);
        }
        
        // SUAVIZAÇÃO DA LINHA DE STATUS COM TRANSPARÊNCIA AUMENTADA
        if (holder.cor != null) {
            holder.cor.setAlpha(0.25f);
        }
    }

    private void applyClickEvents(final PedidoViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    listener.onRowLongClicked(adapterPos);
                }
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    listener.onPedidoRowClicked(adapterPos);
                }
            }
        });
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

    public WebPedido getItem(int position) {
        return pedidos.get(position);
    }

    public List<WebPedido> getItensSelecionados() {
        List<WebPedido> pedidosSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++)
            pedidosSelecionados.add(pedidos.get(selectedItems.keyAt(i)));
        return pedidosSelecionados;
    }

    public void remove(WebPedido pedido) {
        pedidos.remove(pedido);
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public int setNumCols(int qtdCols) {
        return numCols = qtdCols;
    }

    @Override
    public int getItemViewType(int position) {
        if (pedidos.size() == 1)
            return 0;
        else {
            if (pedidos.size() % numCols == 0)
                return 1;
            else
                return (position > 1 && position == pedidos.size() - 1) ? 0 : 1;
        }
    }


    public interface PedidoAdapterListener {
        void onPedidoRowClicked(int position);
        void onRowLongClicked(int position);
        View.OnClickListener onClickExcluir(int position);
        View.OnClickListener onClickEnviar(int position);
        View.OnClickListener onClickDuplic(int position);
        View.OnClickListener onClickCompartilhar(int position);
        View.OnClickListener onClickRastrear(int position);
        View.OnClickListener onClickLinhaTempo(int position);

        View.OnClickListener onClickCopiaSegura(int position);
    }
}
