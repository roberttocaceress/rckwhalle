package com.example.rcksuporte05.rcksistemas.adapters;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.util.Base64;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoPedidoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaAdapterProdutoPedido extends RecyclerView.Adapter<ProdutoPedidoViewHolder> {
    private static int currentSelectedIndex = -1;
    private List<WebPedidoItens> lista;
    private ProdutoPedidoAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private ProdutoPedidoViewHolder holder;
    private boolean campanha;
    private boolean exportado;
    int numCols;

    private Configuracao configuracao;

    private DBHelper db;

    private int idOrdemPoduto;

    // --- CACHES DE PERFORMANCE ---
    private final Map<String, String> cacheFotos = new HashMap<>();

    //private Context context;

    public ListaAdapterProdutoPedido(List<WebPedidoItens> lista, ProdutoPedidoAdapterListener listener, boolean campanha, boolean exportado/*, Context context*/, int idOrdemPoduto) {
        this.listener = listener;
        this.lista = lista;
        this.selectedItems = new SparseBooleanArray();
        this.campanha = campanha;
        this.exportado= exportado;
        this.idOrdemPoduto = idOrdemPoduto;
        //this.context = context;
    }

    @Override
    public ProdutoPedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        db = new DBHelper(parent.getContext());
        getConfiguracao();
        if ( configuracao.getLayout_venda_erp().equalsIgnoreCase("1")) {
            //Analisar aqui p criar lista sem imagem ou a esconder
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto_pedido_lista_2025, parent, false);
        return new ProdutoPedidoViewHolder(view, db, parent.getContext());
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(ProdutoPedidoViewHolder holder, int position) {
        ViewGroup.LayoutParams params1 = holder.lytDesxctoPreco.getLayoutParams();
        ViewGroup.LayoutParams params2 = holder.lytCampanha.getLayoutParams();
        params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.lytDesxctoPreco.setLayoutParams(params1);
        // --- OTIMIZAÇÃO: CACHE DE FOTOS E CARREGAMENTO ASSÍNCRONO COM GLIDE ---
        try {
            if (!configuracao.getLayout_venda_erp().equalsIgnoreCase("1")) {
                String idProd = lista.get(position).getId_produto();
                String fotoBase64 = cacheFotos.get(idProd);

                if (fotoBase64 == null) {
                    Cursor cursor = db.listaDados("SELECT FOTO_ARQUIVO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" +
                            idProd + "' AND ATIVO = 'S' AND FOTO_PRINCIPAL = 'S'");
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            @SuppressLint("Range") String foto = cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO"));
                            if (foto != null) {
                                fotoBase64 = foto;
                                cacheFotos.put(idProd, fotoBase64);
                            }
                        }
                        cursor.close();
                    }
                }

                if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                    byte[] imageBytes = Base64.decode(fotoBase64, Base64.DEFAULT);
                    com.bumptech.glide.Glide.with(holder.itemView.getContext())
                            .load(imageBytes)
                            .apply(new com.bumptech.glide.request.RequestOptions()
                                    .placeholder(R.drawable.ic_image_gallery_newww)
                                    .error(R.drawable.ic_image_gallery_newww)
                                    .centerInside())
                            .into(holder.imageAnexo);
                } else {
                    holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
                }
            }
        } catch (Exception e) {
            holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        try {
            if (!configuracao.getLayout_venda_erp().equalsIgnoreCase("1")) {
                Cursor cursor = db.listaDados("SELECT FOTO_ARQUIVO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" +
                        lista.get(position).getId_produto() + "' AND ATIVO = 'S' AND FOTO_PRINCIPAL = 'S'");
                if (cursor.getCount() > 0) {
                    if (cursor.moveToNext()) {
                        if (!cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO")).isEmpty()) {
                            try {
                                @SuppressLint("Range") byte[] decodedString = Base64.decode(cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO")).getBytes(), 0);
                                holder.imageAnexo.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));//, 170, 180, false));
                            } catch (OutOfMemoryError e) {
                                holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
                            } catch (Exception e) {
                                holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
                            }
                        } else {
                            holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
                        }
                    }
                    //Trabalha com imagens
                } else {
                    holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
                }
            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        holder.idProduto.setText("ID: " + String.valueOf(lista.get(position).getId_produto()));
        if (lista.get(position).getCodigo_em_barras() != null && !lista.get(position).getCodigo_em_barras().equals(""))
            holder.txtCodigoBarra.setText("EAN: " + lista.get(position).getCodigo_em_barras());
        if ( !lista.get(position).getProdutoAtivo())
            holder.nomeListaProduto.setTextColor(Color.parseColor("#FF0000"));
        else
            holder.nomeListaProduto.setTextColor(Color.parseColor("#000000"));
        holder.btnAnaliseEstoque.setVisibility(View.INVISIBLE);
        try {
            if ( lista.get(position).getAnalise_estoque_resultado().equalsIgnoreCase("A")) {
                holder.btnAnaliseEstoque.setVisibility(View.VISIBLE);
                holder.btnAnaliseEstoque.setBackgroundResource(R.drawable.ic_check);
            } else if (lista.get(position).getAnalise_estoque_resultado().equalsIgnoreCase("N")) {
                holder.btnAnaliseEstoque.setVisibility(View.VISIBLE);
                holder.btnAnaliseEstoque.setBackgroundResource(R.drawable.ic_check_alert_red);
            } else {
                holder.btnAnaliseEstoque.setVisibility(View.INVISIBLE);
            }
        } catch (NullPointerException e) {

        }
        holder.nomeListaProduto.setText(lista.get(position).getNome_produto().trim());
        ///holder.textViewUnidadeMedida.setText("  --  " + lista.get(position).getId_produto());
        Float valorProduto;
        if (lista.get(position).getValor_desconto_per() != null && !lista.get(position).getValor_desconto_per().trim().isEmpty() && Float.parseFloat(lista.get(position).getValor_desconto_per()) > 0) {
            valorProduto = lista.get(position).getValor_unitario() - (Float.parseFloat(lista.get(position).getValor_desconto_real()) / Float.parseFloat(lista.get(position).getQuantidade()));
            holder.txtDesconto.setText("DESCONTO: " + MascaraUtil.duasCasaDecimal(lista.get(position).getValor_desconto_per()) + "%");
            try {
                if (Float.parseFloat(lista.get(position).getPerc_desconto_adic()) > 0.00f) {
                    holder.txtDesconto.setText("DESCONTO(s): " + MascaraUtil.duasCasaDecimal(lista.get(position).getValor_desconto_per()) + "% + " + MascaraUtil.duasCasaDecimal(lista.get(position).getPerc_desconto_adic()) + "%");
                    valorProduto = lista.get(position).getValor_unitario() - ( Float.parseFloat(lista.get(position).getValor_desconto_real()) + Float.parseFloat(lista.get(position).getValor_desconto_adic()))  / Float.parseFloat(lista.get(position).getQuantidade());

                }
            } catch ( NullPointerException|NumberFormatException e) {
            } catch ( Exception e) {
            }
        } else {
            params1.height = 0;
            valorProduto = lista.get(position).getValor_unitario();
            holder.txtDesconto.setText("");
            holder.lytDesxctoPreco.setLayoutParams(params1);
        }
        holder.precoProduto.setText(MascaraUtil.duasCasaDecimal(lista.get(position).getQuantidade()) + " x " + MascaraUtil.mascaraReal(valorProduto) + " = " + MascaraUtil.mascaraReal(lista.get(position).getValor_total()));
        try {
            if ( Float.parseFloat( lista.get(position).getIcms_st_valor()) > 0.00 ) {
                holder.precoProduto.setText(MascaraUtil.duasCasaDecimal(lista.get(position).getQuantidade()) + " x ("
                        + MascaraUtil.mascaraReal(valorProduto) +" + "  +MascaraUtil.mascaraReal(lista.get(position).getIcms_st_valor() )+ ") = " + MascaraUtil.mascaraReal(lista.get(position).getValor_total()));
            }
        } catch ( NullPointerException|NumberFormatException e) {
        } catch ( Exception e) {
        }
        if ( !lista.get(position).getProdutoAtivo()) {
            holder.textViewUnidadeMedida.setText("*****");
            holder.textViewUnidadeMedida.setTextColor(Color.parseColor("#FF0000"));
            if ( !PedidoHelper.getActivityPedidoMain().isPedidoEnviado())
                Toast.makeText( PedidoHelper.getActivityPedidoMain(), "O produto + " + lista.get(position).getNome_produto().trim() + "  não foi localizado/e ou foi inativado, favor verificar antes de prosseguir!", Toast.LENGTH_LONG).show();
        } else {
            try {
                if ( lista.get(position).getId_sku() > 0) {
                    holder.textViewUnidadeMedida.setText(lista.get(position).getNome_sku());
                    holder.textViewUnidadeMedida.setTextColor(Color.parseColor("#000000"));
                } else {
                    holder.textViewUnidadeMedida.setText(lista.get(position).getDescricao());
                    holder.textViewUnidadeMedida.setTextColor(Color.parseColor("#000000"));
                }
            } catch ( NullPointerException e) {
                holder.textViewUnidadeMedida.setText(lista.get(position).getDescricao());
                holder.textViewUnidadeMedida.setTextColor(Color.parseColor("#000000"));
            } catch ( Exception e) {
                holder.textViewUnidadeMedida.setText(lista.get(position).getDescricao());
                holder.textViewUnidadeMedida.setTextColor(Color.parseColor("#000000"));
            }
        }
         if ( idOrdemPoduto == 1 || idOrdemPoduto == 2) {
             holder.idPosition.setText(String.valueOf(position + 1));
         } else {
             if ( idOrdemPoduto == 3)
                holder.idPosition.setText("GRUPO : " + lista.get(position).getNome_grupo() == null || lista.get(position).getNome_grupo().isEmpty() ? "-" : lista.get(position).getNome_grupo() );
             if ( idOrdemPoduto == 4)
                 holder.idPosition.setText("S-GRUPO : " + lista.get(position).getNome_sub_grupo() == null ||  lista.get(position).getNome_sub_grupo().isEmpty() ? "--" : lista.get(position).getNome_sub_grupo());
         }
        if (campanha)
            try {
                if (lista.get(position).getIdCampanha() > 0)
                    holder.nomeCampanha.setText(lista.get(position).getNomeCampanha());
                else {
                    holder.nomeCampanha.setText(" ");
                    holder.textViewCampanha.setVisibility(View.INVISIBLE);
                    params2.height = 0;
                    holder.lytCampanha.setLayoutParams(params2);
                }
            } catch (NullPointerException e) {
            } catch (Exception e) {
            }
        else {
            holder.nomeCampanha.setText(" ");
            holder.textViewCampanha.setVisibility(View.INVISIBLE);
            params2.height = 0;
            holder.lytCampanha.setLayoutParams(params2);
        }
        if ( exportado)
            holder.btnExcluirProduto.setVisibility(View.INVISIBLE);
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        if (lista.get(position).getDescontoIndevido())
            holder.txtDesconto.setTextColor(Color.parseColor("#FF0000"));
        else
            holder.txtDesconto.setTextColor(Color.parseColor("#000000"));
        this.holder = holder;
        applyClickEvents(holder, position);
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();
        return 0;
    }

    public int setNumCols(int qtdCols) {
        return numCols = qtdCols;
    }

    @Override
    public int getItemViewType(int position) {
        if (lista.size() == 1)
            return 0;
        else {
            if (lista.size() % numCols == 0)
                return 1;
            else
                return (position > 1 && position == lista.size() - 1) ? 0 : 1;
        }
    }

    private void applyClickEvents(final ProdutoPedidoViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRowClicked(holder.getAdapterPosition());
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongRowClicked(holder.getAdapterPosition());
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return false;
            }
        });
        holder.btnExcluirProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonExcClicked(holder.getAdapterPosition());
            }
        });
        holder.btnAnaliseEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonAnlClicked(holder.getAdapterPosition());
            }
        });
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false))
            selectedItems.delete(pos);
        else
            selectedItems.put(pos, true);
        notifyItemChanged(pos);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public WebPedidoItens getItem(int position) {
        return lista.get(position);
    }

    public List<WebPedidoItens> getItensSelecionados() {
        List<WebPedidoItens> webPedidoItensSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++)
            webPedidoItensSelecionados.add(lista.get(selectedItems.keyAt(i)));
        return webPedidoItensSelecionados;
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void getList(List<WebPedidoItens> webPedidoItens) {
        lista = webPedidoItens;
    }

    public interface ProdutoPedidoAdapterListener {

        void onRowClicked(int position);

        void onLongRowClicked(int position);

        void onButtonExcClicked(int position);
        void onButtonAnlClicked(int position);
    }

    private void getConfiguracao() {
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
}
