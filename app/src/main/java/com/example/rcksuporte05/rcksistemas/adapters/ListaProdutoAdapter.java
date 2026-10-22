package com.example.rcksuporte05.rcksistemas.adapters;

import static android.view.View.GONE;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.util.Base64;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterOutputStream;


/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ListaProdutoAdapter extends RecyclerView.Adapter<ProdutoViewHolder> {
    private Activity context;
    private List<Produto> lista;
    private ProdutoAdapterListener listener;
    private ImagemAdapterLister listenerImagem;
    private ProdutoViewHolder holder;
    public SparseBooleanArray selectedItems;
    private Cursor cursor;
    private DBHelper db;// = new DBHelper(context);
    private int acao;
    int numCols;
    Configuracao configuracao;
    public ListaProdutoAdapter(List<Produto> lista, ProdutoAdapterListener listener, ImagemAdapterLister listenerImagem, Activity context, int acao, Configuracao configuracao) {
        this.lista = lista;
        this.listener = listener;
        this.listenerImagem = listenerImagem;
        this.acao = acao;
        this.context = context;
        this.configuracao = configuracao;
        this.selectedItems = new SparseBooleanArray();
        db = new DBHelper(context);
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        View itemView;
        if ( configuracao.getLayout_venda_erp().equalsIgnoreCase("1")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.produto_lista_sem_imagem_2026, parent, false);
        } else {
            // REATIVANDO O 2026 COM AJUSTES DE FLEXIBILIDADE
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.produto_lista_com_imagem_2026, parent, false);
        }
        return new ProdutoViewHolder(itemView, db, context);

    }

    @Override
    public void onBindViewHolder(final ProdutoViewHolder holder, final int position) {
        try {
            if (configuracao.getVisual_estoque_produto_app().equalsIgnoreCase("N")) {
                holder.txtSaldoEstoque.setVisibility(View.INVISIBLE);
                holder.textUN.setPadding(0, 0,0, 0);
                holder.textUN.setText(lista.get(position).getUnidade());
             }
        } catch ( NullPointerException e) {
        } catch ( Exception e) {

        }
        holder.imageCarrinhoVerde.setVisibility(View.INVISIBLE);
        try {
            if (!lista.get(position).getW_id_produto().equalsIgnoreCase("-") && acao >= 1)
                holder.imageCarrinhoVerde.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
        }
        holder.imageCampanhaVerde.setVisibility(View.INVISIBLE);
        try {
            if ( Integer.parseInt(lista.get(position).getId_campanha_produto()) > 0 && Integer.parseInt(lista.get(position).getId_campanha_cliente()) > 0) {
                if ( lista.get(position).getId_campanha_produto().equalsIgnoreCase(lista.get(position).getId_campanha_cliente()) && acao >= 1)
                    holder.imageCampanhaVerde.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException|NumberFormatException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        holder.imagePromocaoVerde.setVisibility(View.INVISIBLE);
        try {
            if ( Integer.parseInt(lista.get(position).getId_promocao()) > 0 ) {
                    holder.imagePromocaoVerde.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException|NumberFormatException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        // --- NOVO MÉTODO DE CARREGAMENTO COM GLIDE (ALTA PERFORMANCE) ---
        String base64Image = lista.get(position).getAnexo_1();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
                com.bumptech.glide.Glide.with(context)
                        .load(imageBytes)
                        .apply(new com.bumptech.glide.request.RequestOptions()
                                .placeholder(R.drawable.ic_image_gallery_newww)
                                .error(R.drawable.ic_image_gallery_newww)
                                .centerInside())
                        .into(holder.imageAnexo);
            } catch (Exception e) {
                holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
            }
        } else {
            com.bumptech.glide.Glide.with(context).clear(holder.imageAnexo);
            holder.imageAnexo.setImageResource(R.drawable.ic_image_gallery_newww);
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        if ( !lista.get(position).getAnexo_1().isEmpty() ){
            try {
                byte[] decodedString = Base64.decode( lista.get(position).getAnexo_1().getBytes(), 0);
                //byte[] decodedString = decompressB64(lista.get(position).getAnexo_1()).getBytes();
                holder.imageAnexo.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));//, 170, 180, false));

            } catch (OutOfMemoryError e) {
                holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
            } catch ( Exception e ) {
                holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
            }
        } else {
            holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
        }
        */

        //System.gc();
        /*
        try {
            String SQL = "SELECT FOTO_MINIATURA, FOTO_ARQUIVO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" +
                    lista.get(position).getId_produto().trim()
                    + "' AND ATIVO = 'S' AND FOTO_PRINCIPAL = 'S'";
            cursor = db.listaDados(SQL);
            if (cursor.getCount() > 0) {

                if (cursor.moveToNext()) {
                    lista.get(position).setAnexo_1(cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO")));
                    try {
                        byte[] decodedString = Base64.decode(lista.get(position).getAnexo_1().getBytes(), 0);
                        holder.imageAnexo.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));//, 170, 180, false));
                        System.gc();
                    } catch (OutOfMemoryError e) {
                        holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
                        e.printStackTrace();
                    } catch ( Exception e ) {
                        holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
                        e.printStackTrace();
                    }
                }else {
                    lista.get(position).setAnexo_1("");
                    holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
                }
            } else {
                lista.get(position).setAnexo_1("");
                holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
            }
        } catch (Exception e) {
            lista.get(position).setAnexo_1("");
            holder.imageAnexo.setImageResource( R.drawable.ic_image_gallery_newww);
        }

         */
        if (cursor != null)
            cursor.close();

        holder.idProduto.setText("ID: " + String.valueOf(lista.get(position).getId_produto()));
        if (lista.get(position).getCodigo_em_barras() != null && !lista.get(position).getCodigo_em_barras().equals(""))
            holder.txtCodigoBarra.setText(lista.get(position).getCodigo_em_barras());
        else {
            holder.txtCodigoBarra.setText("");
        }
        holder.nomeListaProduto.setText(lista.get(position).getNome_produto().trim());
        String preco = null;
        try {
            preco = String.format("%.2f", Float.parseFloat(lista.get(position).getVenda_preco())).replace(".", ",");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*String w = lista.get(position).getVenda_preco();
        String x = MascaraUtil.mascaraRealSimbolo(w);
        String y = MascaraUtil.mascaraVirgula(w);
        String z = MascaraUtil.mascaraReal(w);
        String t =  MascaraUtil.mascaraReal(Float.parseFloat(lista.get(position).getVenda_preco()));*/

        //String A = "A";
        try {
            if (!lista.get(position).getVenda_preco().trim().equalsIgnoreCase(""))
                holder.precoProduto.setText( MascaraUtil.mascaraReal(Float.parseFloat(lista.get(position).getVenda_preco())));
            else
                holder.precoProduto.setText(lista.get(position).getVenda_preco().replace(".", ","));
        } catch (NullPointerException|NumberFormatException e) {
            holder.precoProduto.setText("R$ 0,00" );
        }

        try {
            holder.txtNomeGrupo.setText("GRUPO: " +lista.get(position).getNome_grupo());
            holder.txtNomeSubGrupo.setText("S-GRUPO: " +lista.get(position).getNome_sub_grupo());
        } catch (NullPointerException e) {
            holder.txtNomeGrupo.setText("");
            holder.txtNomeSubGrupo.setText("");
        } catch ( Exception e) {
            holder.txtNomeGrupo.setText("");
            holder.txtNomeSubGrupo.setText("");
        }

        try {
            if ( !lista.get(position).getNome_tabela_preco().isEmpty())
                holder.nomeTabelaPreco.setText("TAB: " + lista.get(position).getNome_tabela_preco().replace("TABELA","").replace("Tabela","").replace("Tabela", ""));
        } catch (NullPointerException e) {
            holder.nomeTabelaPreco.setText("");
        } catch ( Exception e) {
            holder.nomeTabelaPreco.setText("");
        }

        if (lista.get(position).getSaldo_estoque() <= 0 && lista.get(position).getTravar_fat_sem_estoque().equalsIgnoreCase("S"))
            setCorTexto(holder, Color.RED);
        else
            setCorTexto(holder, Color.BLACK);

        if (configuracao.getVisual_estoque_produto_app().equalsIgnoreCase("S")) {
            if (lista.get(position).getSaldo_estoque().toString().contains("-"))
                //holder.txtSaldoEstoque.setText("SALDO: 0" + "  " + lista.get(position).getUnidade());
                holder.txtSaldoEstoque.setText(lista.get(position).getUnidade());
            else
                holder.txtSaldoEstoque.setText(MascaraUtil.mascaraVirgula(lista.get(position).getSaldo_estoque()).replace(",00", "") + "  " + lista.get(position).getUnidade());
                //holder.txtSaldoEstoque.setText("SALDO: " + MascaraUtil.mascaraVirgula(lista.get(position).getSaldo_estoque()).replace(",00", "") + "  " + lista.get(position).getUnidade());
        } else {
            holder.txtSaldoEstoque.setText( lista.get(position).getUnidade());
        }
        holder.itemView

                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        holder.precoICMS.setText("");

        try {
            if (lista.get(position).getProduto_suj_icms_st().equalsIgnoreCase("S")) {
                if ( lista.get(position).getProduto_st_aliquota() > 0.00) {
                    BigDecimal bValorIcmsST = new BigDecimal(lista.get(position).getProduto_st_aliquota() *  Float.parseFloat(lista.get(position).getVenda_preco()) / 100).setScale(4, RoundingMode.HALF_EVEN);
                    int tamanho = String.valueOf(bValorIcmsST).length();
                    if ( (String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho -1)).trim().equalsIgnoreCase("5")) {
                        if (((Integer.parseInt(String.valueOf(bValorIcmsST).substring(tamanho - 2, tamanho - 1))) % 2) == 0) {
                            bValorIcmsST = new BigDecimal(lista.get(position).getProduto_st_aliquota() *  Float.parseFloat(lista.get(position).getVenda_preco()) / 100).setScale(2, RoundingMode.UP);
                        } else {
                            bValorIcmsST = new BigDecimal( lista.get(position).getProduto_st_aliquota() *  Float.parseFloat(lista.get(position).getVenda_preco()) / 100).setScale(2, RoundingMode.DOWN /*RoundingMode.HALF_EVEN*/);
                        }
                    } else {
                        bValorIcmsST = new BigDecimal( lista.get(position).getProduto_st_aliquota() *  Float.parseFloat(lista.get(position).getVenda_preco()) / 100).setScale(2, RoundingMode.HALF_EVEN);
                    }
                    float totalICMS = Float.parseFloat(String.valueOf(bValorIcmsST));//(quantidade * Float.parseFloat(p
                    holder.precoICMS.setText("ICMS ST " + MascaraUtil.mascaraReal(totalICMS));
                }
            }
        } catch ( NullPointerException|NumberFormatException e) {
            e.printStackTrace();
        } catch ( Exception e){
            e.printStackTrace();
        }

        try {
            boolean temTabela = lista.get(position).getNome_tabela_preco() != null && !lista.get(position).getNome_tabela_preco().isEmpty();
            boolean temICMS = !holder.precoICMS.getText().toString().isEmpty();

            if (temTabela || temICMS) {
                holder.lytTabelaPrecoICMS.setVisibility(View.VISIBLE);
                // Garante que a altura seja wrap_content caso tenha sido alterada
                ViewGroup.LayoutParams lp = holder.lytTabelaPrecoICMS.getLayoutParams();
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.lytTabelaPrecoICMS.setLayoutParams(lp);
            } else {
                holder.lytTabelaPrecoICMS.setVisibility(GONE);
            }
        } catch (Exception e) {
            holder.lytTabelaPrecoICMS.setVisibility(GONE);
        }
        try {
            if (Float.parseFloat(lista.get(position).getVenda_preco()) > 999.99f) {
                holder.precoProduto.setTextSize(12);
            }
        } catch (NullPointerException|NumberFormatException e) {
            e.printStackTrace();
        }

        holder.imageCarrinhoAzul.setVisibility(View.INVISIBLE);
        try {
            if ( configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("S")) {
                if (lista.get(position).getUltima_compra_cliente() != null && !lista.get(position).getUltima_compra_cliente().isEmpty()) {
                    holder.imageCarrinhoAzul.setVisibility(View.VISIBLE);
                }
            }
        } catch (NullPointerException e) {
            if (lista.get(position).getUltima_compra_cliente() != null && !lista.get(position).getUltima_compra_cliente().isEmpty()) {
                holder.imageCarrinhoAzul.setVisibility(View.VISIBLE);
            }
        }
        if ( configuracao.getLayout_venda_erp().equalsIgnoreCase("1")) {
            holder.txtSaldoEstoque.setVisibility(GONE);
        }
        applyCLickEnvents(holder, position);
        //System.gc();
    }

    public Produto getItem(int position) {
        return lista.get(position);
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

    private void setCorTexto(ProdutoViewHolder holder, int color) {
        holder.txtCodigoBarra.setTextColor(color);
        holder.nomeListaProduto.setTextColor(color);
        holder.precoProduto.setTextColor(color);
        holder.textUN.setTextColor(color);
        holder.idProduto.setTextColor(color);
        holder.txtSaldoEstoque.setTextColor(color);
        holder.txtNomeSubGrupo.setTextColor(color);
        holder.txtNomeGrupo.setTextColor(color);
        holder.nomeTabelaPreco.setTextColor(color);
    }
    private void applyCLickEnvents(final ProdutoViewHolder holder, final int position) {
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

        holder.imageAnexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ( !lista.get(position).getAnexo_1().isEmpty())
                        listenerImagem.onClickListener(position);
                    else {
                        Toast.makeText(  v.getContext(), "Este  produto não contem nenhuma imagem vinculada!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false))
            selectedItems.delete(pos);
        else
            selectedItems.put(pos, true);
        notifyDataSetChanged();
    }

    public int getSelectedItensCount() {
        return selectedItems.size();
    }

    public List<Produto> getItensSelecionados() {
        List<Produto> produtosSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++)
            produtosSelecionados.add(lista.get(selectedItems.keyAt(i)));
        return produtosSelecionados;
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface ProdutoAdapterListener {
        void onClickListener(int position);
        void onLongClickListener(int position);
    }

    public interface ImagemAdapterLister {
        void onClickListener(int position);
    }

    public static String decompressB64(String b64Compressed) throws IOException {
        byte[] decompressedBArray = decompress(Base64.decode(b64Compressed, 0));
        return new String(decompressedBArray, StandardCharsets.UTF_8);
    }

    public static byte[] decompress(byte[] compressedTxt) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (OutputStream ios = new InflaterOutputStream(os)) {
            ios.write(compressedTxt);        }
        return os.toByteArray();
    }
}
