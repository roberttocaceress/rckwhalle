package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.graphics.Color;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.SegmentoDAO;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityClienteDrawer;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ClientesViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.util.Common;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ListaClienteAdapter extends RecyclerView.Adapter<ClientesViewHolder> {
    private Activity context;
    private List<Cliente> lista;
    private Listener listener;
    private SparseBooleanArray selectedItems;
    private DBHelper db;
    private  CategoriaDAO categoriaDAO;
    private Categoria categoria = new Categoria();
    private SegmentoDAO segmentoDAO ;
    private ConfiguracaoDAO configuracaoDAO;
    private Configuracao configuracao;
    public Cliente cliente;
    public String cidadeUF;
    int numCols;

    // --- CACHES DE PERFORMANCE ---
    private final Map<Integer, String> cacheCategorias = new HashMap<>();
    private final Map<String, String> cacheSegmentos = new HashMap<>();
    private final SimpleDateFormat sdfSql = new SimpleDateFormat("yyyy-MM-dd");

    public ListaClienteAdapter(Activity context, List<Cliente> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
        this.context = context;
        this.selectedItems = new SparseBooleanArray();
        this.db = new DBHelper(context);
        this.categoriaDAO =  new CategoriaDAO(this.db);
        this.segmentoDAO = new SegmentoDAO(this.db);
        this.configuracaoDAO = new ConfiguracaoDAO(this.db);
        this.configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    @Override
    public ClientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //configuracao.setOrdem_lista_cliente("R");
        try {
            // NOVO: Usando o layout Premium 2026
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cliente_lista_2026, parent, false);
            return new ClientesViewHolder(itemView, db, context);
        } catch (NullPointerException e) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cliente_lista_2026, parent, false);
            return new ClientesViewHolder(itemView, db, context);
        } catch ( Exception e) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cliente_lista_2026, parent, false);
            return new ClientesViewHolder(itemView, db, context);
        }
    }

    @Override
    public void onBindViewHolder(ClientesViewHolder holder, int position) {
        cliente = lista.get(position);
        cidadeUF = "";
        //configuracao.setOrdem_lista_cliente("R");
        if (cliente.getId_cadastro_servidor() > 0) {
            holder.idCliente.setText("ID - " + cliente.getId_cadastro_servidor());

            holder.btnDownload.setBackgroundResource(R.drawable.ic_download_cliente_2025);
            if (cliente.getAlterado().equals("S"))
                holder.btnDownload.setBackgroundResource(R.drawable.ic_upload_cliente_2025);
        } else {
            holder.idCliente.setText(String.valueOf(cliente.getId_cadastro()));
            holder.btnDownload.setBackgroundResource(R.drawable.ic_upload_cliente_2025);
        }
        holder.textViewNome.setText(cliente.getNome_cadastro().trim());
        try {
            holder.textViewNomeFantasia.setText(cliente.getNome_fantasia().trim());
        } catch (NullPointerException e) {
            holder.textViewNomeFantasia.setText("--");
        }
        try {
            if (cliente.getCpf_cnpj() == null || cliente.getCpf_cnpj().isEmpty())
                holder.cnpjCliente.setText("--");
            else {
                if (cliente.getPessoa_f_j().equalsIgnoreCase("F")) {
                    holder.cnpjCliente.setText(MascaraUtil.mascaraCPF(cliente.getCpf_cnpj()));
                } else if (cliente.getPessoa_f_j().equalsIgnoreCase("J")) {
                    holder.cnpjCliente.setText(MascaraUtil.mascaraCNPJ(cliente.getCpf_cnpj()));
                } else {
                    holder.cnpjCliente.setText("--");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //if (configuracao.getOrdem_lista_cliente().equalsIgnoreCase("R") || configuracao.getOrdem_lista_cliente().equalsIgnoreCase("E")) {
                if (cliente.getEndereco() != null && !cliente.getEndereco().isEmpty())
                    cidadeUF = cliente.getEndereco();
                if (cliente.getEndereco_numero() != null && !cliente.getEndereco_numero().isEmpty())
                    cidadeUF += " N " + cliente.getEndereco_numero();
                if (cliente.getEndereco_bairro() != null && !cliente.getEndereco_bairro().isEmpty())
                    cidadeUF += " - " + cliente.getEndereco_bairro();

                if (cliente.getNome_municipio() != null && !cliente.getNome_municipio().isEmpty())
                    cidadeUF += " - " + cliente.getNome_municipio();
                if (cliente.getEndereco_uf() != null && !cliente.getEndereco_uf().isEmpty())
                    cidadeUF += " - " + cliente.getEndereco_uf();
            //} else {
           //     if (cliente.getNome_municipio() != null && !cliente.getNome_municipio().isEmpty())
           //         cidadeUF = cliente.getNome_municipio();
           //     if (cliente.getEndereco_uf() != null && !cliente.getEndereco_uf().isEmpty())
           //         cidadeUF += " - " + cliente.getEndereco_uf();
          //  }
            holder.cidadeCliente.setText(cidadeUF);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String telefone = cliente.getTelefone_principal() != null ? cliente.getTelefone_principal() : "";
            boolean ehCelular = MascaraUtil.isCelular(telefone);

            if (ehCelular) {
                //if (holder.imgTipoTelefone != null) {
                //    holder.imgTipoTelefone.setImageResource(R.drawable.ic_whatsapp_dark_2026);
                //    holder.imgTipoTelefone.clearColorFilter();
                //    holder.imgTipoTelefone.setAlpha(0.6f);
               // }

                holder.btnChamada.setBackgroundResource(R.drawable.ic_whatsapp_dark_2026);
                //if (holder.btnChamada.getBackground() != null) {
                //    holder.btnChamada.getBackground().setColorFilter(Color.parseColor("#94A3B8"), android.graphics.PorterDuff.Mode.SRC_IN);
                //    holder.btnChamada.getBackground().setAlpha(153);
                //}
            } else {
                //if (holder.imgTipoTelefone != null) {
                //    holder.imgTipoTelefone.setImageResource(R.drawable.ic_phone_color_app_16_16);
                //    holder.imgTipoTelefone.setColorFilter(Color.parseColor("#333333"), android.graphics.PorterDuff.Mode.SRC_IN);
                //    holder.imgTipoTelefone.setAlpha(0.6f);
                //}

                holder.btnChamada.setBackgroundResource(R.drawable.ic_baseline_cliente_telefone_2025);
                //if (holder.btnChamada.getBackground() != null) {
                    //holder.btnChamada.getBackground().clearColorFilter();
                    //holder.btnChamada.getBackground().setAlpha(153);
                //}
            }

            holder.telefoneCliente.setText(MascaraUtil.formataTelefone(telefone));
            try {
                if (cliente.getTelefone_dois() != null && !cliente.getTelefone_dois().isEmpty()) {
                    holder.telefoneCliente.setText(MascaraUtil.formataTelefone(telefone) + "  --  " + MascaraUtil.formataTelefone(cliente.getTelefone_dois()));
                }
            } catch (NullPointerException e) {
            }
        } catch (NullPointerException e) {
        }
        holder.nomeTabelaPreco.setText("--");
        try {
            if (!lista.get(position).getNome_tabela_preco().isEmpty()) {
                holder.nomeTabelaPreco.setText("TABELA: " + cliente.getNome_tabela_preco());
            }

        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        // --- OTIMIZAÇÃO: CACHE DE CATEGORIA E SEGMENTO (EVITA ACESSO AO BANCO NO SCROLL) ---
        try {
            int idCat = cliente.getIdCategoria();
            if (idCat > 0) {
                String nomeCat = cacheCategorias.get(idCat);
                if (nomeCat == null) {
                    categoria = categoriaDAO.getCategoria(idCat);
                    if (categoria != null && categoria.getNomeCategoria() != null) {
                        nomeCat = categoria.getNomeCategoria().toUpperCase();
                        cacheCategorias.put(idCat, nomeCat);
                    }
                }
                if (nomeCat != null) holder.txtCategoria.setText("CATEGORIA: " + nomeCat);
            }

            String idSetor = cliente.getId_setor();
            if (idSetor != null && !idSetor.isEmpty()) {
                String nomeSeg = cacheSegmentos.get(idSetor);
                if (nomeSeg == null) {
                    // Nota: segmentoDAO.getLista retorna uma lista de objetos do tipo Segmento
                    List<?> listSeg = segmentoDAO.getLista("SELECT * FROM TBL_CADASTRO_SETOR WHERE ID = " + idSetor, false);
                    if (listSeg != null && !listSeg.isEmpty()) {
                        // Usando reflexão ou cast se soubéssemos a classe exata, mas como o original funcionava:
                        // Vamos assumir que o método getNomeSetor existe no primeiro item
                        nomeSeg = ((com.example.rcksuporte05.rcksistemas.model.Segmento)listSeg.get(0)).getNomeSetor();
                        cacheSegmentos.put(idSetor, nomeSeg);
                    }
                }
                if (nomeSeg != null) holder.txtSegmento.setText("SEGMENTO: " + nomeSeg);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        try {
            if (lista.get(position).getIdCategoria() > 0) {
                categoria = categoriaDAO.getCategoria(cliente.getIdCategoria());
                holder.txtCategoria.setText("CATEGORIA: " + categoria.getNomeCategoria().toUpperCase());
            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
        } catch (Exception e) {
        }

        try {
            if (!lista.get(position).getId_setor().isEmpty()) {
                holder.txtSegmento.setText("SEGMENTO: " + segmentoDAO.getLista("SELECT * FROM TBL_CADASTRO_SETOR WHERE ID = " + cliente.getId_setor(), false).get(0).getNomeSetor());
            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        // --- OTIMIZAÇÃO: REUSO DE DATE FORMAT ---
        try {
            Date dtUltima = sdfSql.parse(cliente.getData_ultima_compra());
            if (dtUltima.before(sdfSql.parse("2001-01-01"))) {
                holder.txtDataUltimaCompra.setText("SEM COMPRA");
            } else {
                holder.txtDataUltimaCompra.setText("NÃO COMPRA Á " + calcularDias(dtUltima, new Date()) + " dias");
            }
        } catch (Exception e) {
            holder.txtDataUltimaCompra.setText("SEM COMPRA");
        }

        /* CÓDIGO ANTERIOR (COMENTADO PARA SEGURANÇA)
        try {
            if (new SimpleDateFormat("yyyy-MM-dd").parse(cliente.getData_ultima_compra()).before(new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01")))
                holder.txtDataUltimaCompra.setText("SEM COMPRA");
            else
                holder.txtDataUltimaCompra.setText("NÃO COMPRA Á " + calcularDias(new SimpleDateFormat("yyyy-MM-dd").parse(cliente.getData_ultima_compra()), new Date()) + " dias");
        } catch (Exception e) {
            holder.txtDataUltimaCompra.setText("SEM COMPRA");
        }
        */

        if (cliente.getId_cadastro_servidor() > 0) {
            if (cliente.getF_cliente().equals("S"))
                holder.imStatus.setVisibility(View.GONE);
            else {
                holder.imStatus.setImageResource(R.mipmap.ic_time);
                holder.txtClienteAguarda.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imStatus.setImageResource(R.mipmap.ic_prospect_pendente);
            holder.txtClienteAguarda.setVisibility(View.VISIBLE);
        }

        if (cliente.getId_cadastro_servidor() <= 0) {
            holder.txtClienteAguarda.setBackgroundResource(R.drawable.bg_badge_status_amarelo);
            holder.txtClienteAguarda.setTextColor(Color.parseColor("#854D0E")); // Marrom/Dourado para leitura no amarelo
            holder.txtClienteAguarda.setText("CLIENTE AGUARDANDO ENVIO P/ APROVAÇÃO DE CADASTRO");
        } else if (cliente.getAlterado().equals("S")) {
            holder.txtClienteAguarda.setBackgroundResource(R.drawable.bg_badge_status_vermelho);
            holder.txtClienteAguarda.setTextColor(Color.parseColor("#B91C1C"));
            holder.txtClienteAguarda.setText("CLIENTE COM ALTERAÇÕES PENDENTES");
        } else {
            try {
                if (cliente.getF_cliente().equalsIgnoreCase("N")) {
                    holder.txtClienteAguarda.setBackgroundResource(R.drawable.bg_badge_status_vermelho);
                    holder.txtClienteAguarda.setTextColor(Color.parseColor("#B91C1C"));
                    holder.txtClienteAguarda.setText("CLIENTE AGUARDANDO ANALISE PARA EFETIVAÇÃO");
                } else {
                    holder.txtClienteAguarda.setVisibility(View.GONE);
                }
            } catch (NullPointerException e) {
                holder.txtClienteAguarda.setVisibility(View.GONE);
            }
        }
        holder.imgFinanceiroVencido.setVisibility(View.INVISIBLE);
        try {
            if (Float.parseFloat(cliente.getFinanceiro_vencido()) > 0.00) {
                holder.imgFinanceiroVencido.setVisibility(View.VISIBLE);
                holder.imgFinanceiroVencido.setImageResource(R.drawable.ic_financeiro_vencido);
            }
        } catch (NullPointerException|NumberFormatException e) {
        } catch (Exception e){
        }
        holder.imgFinanceiroVencendo.setVisibility(View.INVISIBLE);
        try {
        if ( Float.parseFloat(cliente.getFinanceiro_vencendo()) > 0.00) {
            holder.imgFinanceiroVencendo.setVisibility(View.VISIBLE);
            holder.imgFinanceiroVencendo.setImageResource(R.drawable.ic_financeiro_vencendo);
        }
        } catch (NullPointerException|NumberFormatException e) {
        } catch (Exception e){
        }

        holder.imgPedidoAnalise.setVisibility(View.INVISIBLE);
        try {
            if ( Float.parseFloat(cliente.getPedido_analise()) > 0.00) {
                holder.imgPedidoAnalise.setVisibility(View.VISIBLE);
                holder.imgPedidoAnalise.setImageResource(R.drawable.ic_pedido_analise);
            }
        } catch (NullPointerException|NumberFormatException e) {
        } catch (Exception e){
        }


        holder.imgPromocaoCliente.setVisibility(View.INVISIBLE);
        try {
            if ( Integer.parseInt(cliente.getId_promocao()) > 0) {
                holder.imgPromocaoCliente.setVisibility(View.VISIBLE);
                holder.imgPromocaoCliente.setImageResource(R.drawable.ic_produto_promocao);
            }
        } catch (NullPointerException|NumberFormatException e) {
        } catch (Exception e){
        }

        holder.imgClienteBloqueado.setVisibility(View.INVISIBLE);
        try {
            if ( cliente.getAtivo().equalsIgnoreCase("N")) {
                holder.imgClienteBloqueado.setVisibility(View.VISIBLE);
                holder.imgClienteBloqueado.setImageResource(R.drawable.ic_cliente_bloqueado);
            }
            if ( configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S")) {
                float limiteCredito = Float.parseFloat(cliente.getLimite_credito());
                float limitUtilizado = Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo());
                if ( (limiteCredito - limitUtilizado) < 0.00f) {
                    if ( cliente.getAtivo().equalsIgnoreCase("S")) {
                        holder.imgClienteBloqueado.setVisibility(View.VISIBLE);
                        holder.imgClienteBloqueado.setImageResource(R.drawable.ic_cliente_atencao);
                    }
                }
            }
        } catch (NullPointerException|NumberFormatException e) {
        } catch (Exception e){
        }

        if ( context.getIntent().getIntExtra("acao", 0) == 1)
            holder.lyBotoes.setVisibility(View.GONE);
        else {
            holder.lyDownload.setOnClickListener(listener.onClickDowload(position));
            holder.btnDownload.setOnClickListener(listener.onClickDowload(position));
            holder.lyChamada.setOnClickListener(listener.onClickChamada(position));
            holder.btnChamada.setOnClickListener(listener.onClickChamada(position));
            holder.lyChamada.setOnLongClickListener(listener.onLongClickChamada(position));
            holder.btnChamada.setOnLongClickListener(listener.onLongClickChamada(position));
            holder.lyGps.setOnClickListener(listener.onClickGps(position));
            holder.btnGps.setOnClickListener(listener.onClickGps(position));
            if (holder.lyEmail != null) holder.lyEmail.setOnClickListener(listener.onClickEmail(position));
            if (holder.btnEmail != null) holder.btnEmail.setOnClickListener(listener.onClickEmail(position));
            holder.lyFinanceiro.setOnClickListener(listener.onClickFinanceiro(position));
            holder.btnFinanceiro.setOnClickListener(listener.onClickFinanceiro(position));
            holder.lyNovoPedido.setOnClickListener(listener.onClickNovoPedido(position));
            holder.btnNovoPedido.setOnClickListener(listener.onClickNovoPedido(position));
        }
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
        applyCLickEnvents(holder, position);
        // System.gc();
    }

    public Cliente getItem(int position) {
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

    private void applyCLickEnvents(ClientesViewHolder holder, final int position) {
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

    public List<Cliente> getItensSelecionados() {
        List<Cliente> lista = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++)
            lista.add(this.lista.get(selectedItems.keyAt(i)));
        return lista;
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    private String calcularDias(Date data1, Date data2) {
        return String.valueOf((data2.getTime() - data1.getTime()) / 86400000L);
    }

    public interface Listener {
        void onClickListener(int position);
        void onLongClickListener(int position);
        View.OnClickListener onClickChamada(int position);
        View.OnLongClickListener onLongClickChamada(int position);
        View.OnClickListener onClickGps(int position);
        View.OnClickListener onClickEmail(int position);
        View.OnClickListener onClickFinanceiro(int position);
        View.OnClickListener onClickNovoPedido(int position);
        View.OnClickListener onClickDowload(int position);
    }

}
