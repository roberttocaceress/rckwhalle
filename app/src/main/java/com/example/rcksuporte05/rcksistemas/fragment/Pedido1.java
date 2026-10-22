package com.example.rcksuporte05.rcksistemas.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rcksuporte05.rcksistemas.BO.PedidoBO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProdutoDrawer;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProdutoPedido;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutoPedido;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.PromocaoRetorno;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Pedido1 extends Fragment implements ListaAdapterProdutoPedido.ProdutoPedidoAdapterListener {

    public static List<WebPedidoItens> listaProdutoRemovido = new ArrayList<>();
    private static ListaAdapterProdutoPedido adapter;
    private static List<WebPedidoItens> listaProdutoPedido = new ArrayList<>();
    private static RecyclerView recyclerView;
    private DBHelper db = new DBHelper(PedidoHelper.getActivityPedidoMain());
    private WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
    private TabelaPrecoItem tabelaPrecoItem;
    private Button btnInserirProduto, btnMenuProduto;
    private Bundle bundle;
    private int idCliente, idOrdemPoduto;
    private CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO;
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private Context context;
    private Configuracao configuracao = new Configuracao();
    private boolean exportado = false;

    public static ListaAdapterProdutoPedido getAdapter() {
        return adapter;
    }

    public static boolean removeProdutos(final List<WebPedidoItens> webPedidoItens) {
        DBHelper db1 = new DBHelper(PedidoHelper.getActivityPedidoMain());
        WebPedidoItensDAO dao = new WebPedidoItensDAO(db1);
        Context ctx = PedidoHelper.getActivityPedidoMain();
        boolean aoMenosUmRemovido = false;

        try {
            // Cria uma cópia da lista de exclusão para iterar com segurança
            List<WebPedidoItens> itensParaRemover = new ArrayList<>(webPedidoItens);
            
            for (WebPedidoItens item : itensParaRemover) {
                boolean deletadoNoBanco = true;
                
                // Só tenta deletar do banco se o item já possuir ID gravado
                if (item.getId_web_item() != null && !item.getId_web_item().isEmpty()) {
                    deletadoNoBanco = dao.delete(ctx, item);
                }

                if (deletadoNoBanco) {
                    listaProdutoPedido.remove(item);
                    if (listaProdutoRemovido == null) listaProdutoRemovido = new ArrayList<>();
                    listaProdutoRemovido.add(item);
                    aoMenosUmRemovido = true;
                    PedidoHelper.setMudouItens(true); // Sinaliza mudança para recalcular totais
                }
            }

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            
            PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
            return aoMenosUmRemovido;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido1_2025, container, false);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        
        // Garante que a lista comece vazia mas instanciada para evitar o "trava" do cache no primeiro recálculo
        if (listaProdutoPedido == null) {
            listaProdutoPedido = new ArrayList<>();
        }

        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        idOrdemPoduto = 1;

        final PedidoHelper pedidoHelper = new PedidoHelper(this);
        bundle = getArguments();
        context = this.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.lstProdutosPedido);
        btnInserirProduto = (Button) view.findViewById(R.id.btnInserirProduto);
        btnMenuProduto = (Button) view.findViewById(R.id.btnMenuProduto);

        btnMenuProduto.setOnClickListener(v -> showMenu(v, R.menu.menu_produto_pedido));

        if (bundle.getInt("vizualizacao") == 1) {
            btnInserirProduto.setVisibility(View.INVISIBLE);
            btnInserirProduto.setEnabled(false);
            exportado = true;
        }

        btnInserirProduto.setOnClickListener(v -> {
            if (PedidoHelper.getActivityPedidoMain().verificaCliente()) {
                Intent intent = new Intent(getActivity(), ActivityProdutoDrawer.class);
                intent.putExtra("acao", 1);
                intent.putExtra("idPedido", PedidoHelper.getIdPedido());
                intent.putExtra("idCliente", ClienteHelper.getCliente().getId_cadastro());
                startActivity(intent);
            } else {
                Toast.makeText(context, "Informe um cliente antes de prosseguir.!", Toast.LENGTH_SHORT).show();
                PedidoHelper.getActivityPedidoMain().btnBuscaCliente.callOnClick();
            }
        });

        if (PedidoHelper.getIdPedido() > 0) {
            if (bundle.getInt("vizualizacao") == 1) {
                btnInserirProduto.setEnabled(false);
                btnInserirProduto.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparente50)));
                Configuracao config = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                try {
                    PedidoHelper.getWebPedido().setTrabalha_com_campanha(config.getTrabalha_com_campanhas());
                } catch (NullPointerException ignored) {}
            }
            try {
                if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S")) {
                    listaProdutoPedido = webPedidoItensDAO.getLista("SELECT WI.*, CP.NOME_CAMPANHA AS NOME_CAMPANHA FROM TBL_WEB_PEDIDO_ITENS AS WI " +
                            "LEFT JOIN TBL_CAMPANHA_COMERCIAL_CAB AS CP ON CP.ID_CAMPANHA = WI.ID_CAMPANHA " +
                            "WHERE WI.ID_PEDIDO = " + PedidoHelper.getIdPedido() + " GROUP BY WI.ID_PRODUTO ORDER BY WI.ID_WEB_ITEM");
                } else {
                    listaProdutoPedido = webPedidoItensDAO.getListaOtimizada(getSqlOtimizado("WI.ID_WEB_ITEM"));
                }
                setRecyclerView(listaProdutoPedido, idOrdemPoduto);
            } catch (Exception e) {
                showFeedbackDialog("O pedido encontra-se sem produto!!", false);
            }
        } else if (PedidoHelper.getWebPedido() != null) {
            listaProdutoPedido = PedidoHelper.getListaWebPedidoItens();
            setRecyclerView(listaProdutoPedido, idOrdemPoduto);
        }

        return view;
    }

    public boolean inserirProduto(WebPedidoItens webPedidoItem) {
        if (webPedidoItem == null) return false;
        if (PedidoHelper.getIdPedido() > 0) {
            if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + PedidoHelper.getIdPedido() +
                    " AND ID_PRODUTO = '" + webPedidoItem.getId_produto().trim() + "') " +
                    " OR (ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND ID_PRODUTO = '" + webPedidoItem.getId_produto() + "');") <= 0) {
                webPedidoItem.setId_pedido(String.valueOf(PedidoHelper.getIdPedido()));
                webPedidoItem.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                webPedidoItem.setUsuario_lancamento_id(String.valueOf(bundle.getInt("idUsuario")));
                if (listaProdutoPedido == null) listaProdutoPedido = new ArrayList<>();
                int id_web_item = (int) webPedidoItensDAO.add(webPedidoItem);
                if (id_web_item > 0) {
                    webPedidoItem.setId_web_item(Integer.toString(id_web_item));
                    listaProdutoPedido.add(webPedidoItem);
                    setRecyclerView(listaProdutoPedido, 1);
                    return true;
                } else {
                    showFeedbackDialog("Houve um erro ao obter os dados do produto!", false);
                    return false;
                }
            }
        }
        return false;
    }

    public boolean alterarProduto(WebPedidoItens webPedidoItem, int position) {
        webPedidoItem.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        webPedidoItem.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
        if (webPedidoItensDAO.update(webPedidoItem)) {
            listaProdutoPedido.set(position, webPedidoItem);
            setRecyclerView(listaProdutoPedido, 1);
            return true;
        }
        return false;
    }

    public List<WebPedidoItens> salvaPedidos() {
        for (WebPedidoItens webPedidoItens : listaProdutoPedido) {
            if (PedidoHelper.getCondicoesPagamento() == null) {
                webPedidoItens.setDescontoIndevido(false);
            } else {
                if (PedidoHelper.getCondicoesPagamento().getAceita_desconto() != null) {
                    if ((PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("N") || (webPedidoItens.getProduto_materia_prima() != null && webPedidoItens.getProduto_materia_prima().equals("S")) || (webPedidoItens.getProduto_tercerizacao() != null && webPedidoItens.getProduto_tercerizacao().equals("S")))
                            && Float.parseFloat(webPedidoItens.getValor_desconto_real()) > 0.f) {
                        if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S") && ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                            webPedidoItens.setDescontoIndevido(false);
                        } else {
                            webPedidoItens.setDescontoIndevido(true);
                        }
                    }
                } else {
                    webPedidoItens.setDescontoIndevido(false);
                }
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
        PedidoHelper.setListaWebPedidoItens(listaProdutoPedido);
        return listaProdutoPedido;
    }

    public static float calcularDesconto() {
        float resultado = 0;
        for (WebPedidoItens item : listaProdutoPedido) {
            try { resultado += Float.parseFloat(item.getValor_desconto_real()); } catch (Exception ignored) {}
        }
        return resultado;
    }

    public static int totalProdutos() {
        return listaProdutoPedido.size();
    }

    public static void zerarDesconto() {
        DBHelper dbHelper = new DBHelper(PedidoHelper.getActivityPedidoMain());
        WebPedidoItensDAO dao = new WebPedidoItensDAO(dbHelper);
        for (WebPedidoItens item : listaProdutoPedido) {
            item.setValor_desconto_per("0.00");
            item.setValor_desconto_real("0.00");
            item.setDescontoIndevido(false);
            float bruto = 0; try { bruto = Float.parseFloat(item.getValor_bruto()); } catch (Exception ignored) {}
            item.setValor_total(String.valueOf(bruto));
            dao.update(item);
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
    }

    public static void verificaDescontoPrazo(Float desconto) {
        for (WebPedidoItens item : listaProdutoPedido) {
            try {
                if (Float.parseFloat(item.getValor_desconto_per()) > (desconto != null ? desconto : 0.0f)) {
                    item.setDescontoIndevido(true);
                } else {
                    item.setDescontoIndevido(false);
                }
            } catch (Exception ignored) {}
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public static void colocarDescontoPrazo(float desconto, DBHelper db) {
        WebPedidoItensDAO dao = new WebPedidoItensDAO(db);
        for (WebPedidoItens item : listaProdutoPedido) {
            try {
                item.setValor_desconto_per(MascaraUtil.duasCasaDecimal(desconto));
                float bruto = Float.parseFloat(item.getValor_bruto());
                float valorDesconto = (desconto * bruto) / 100;
                item.setValor_desconto_real(String.valueOf(valorDesconto));
                item.setValor_total(String.valueOf(bruto - valorDesconto));
                dao.update(item);
            } catch (Exception ignored) {}
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
    }

    private void getConfiguracao() {
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    public void setRecyclerView(List<WebPedidoItens> webPedidoItens, int idOrdem) {
        getConfiguracao();
        context = PedidoHelper.getActivityPedidoMain();
        if (context == null) context = getContext();
        if (context == null) return;

        boolean trabalhaCampanha = false;
        try { trabalhaCampanha = PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"); } catch (Exception ignored) {}
        adapter = new ListaAdapterProdutoPedido(webPedidoItens, this, trabalhaCampanha, exportado, idOrdem);

        int numColsList = 1;
        GridLayoutManager layoutManager;
        if (configuracao.getGira_tela_pedido().equalsIgnoreCase("S")) {
            int orientation = context.getResources().getConfiguration().orientation;
            int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                numColsList = screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ? 2 : 1;
            } else {
                numColsList = screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ? 3 : 2;
            }
            layoutManager = new GridLayoutManager(context, numColsList);
        } else {
            layoutManager = new GridLayoutManager(context, 1);
        }
        
        adapter.setNumCols(numColsList);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        if (listaProdutoPedido != null)
            PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
        try {
            idCliente = ClienteHelper.getCliente().getId_cadastro();
        } catch (NullPointerException e) {
        }
        setTabelaPrecoItem();
        PedidoBO pedidoBO = new PedidoBO();
        if (bundle.getInt("vizualizacao") != 1)
            verificaDescontoIndevido(pedidoBO);
        if (adapter != null) adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onRowClicked(int position) {
        if (position < 0 || listaProdutoPedido == null || position >= listaProdutoPedido.size()) {
            return;
        }
        if (adapter.getSelectedItemCount() > 0) {
            toggleSelection(position);
        } else {
            Intent intent = new Intent(getActivity(), ActivityProdutoPedido.class);
            PedidoHelper.setWebPedidoItem(listaProdutoPedido.get(position));
            intent.putExtra("pedido", 1);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    public void onLongRowClicked(int position) {
        if (position < 0 || listaProdutoPedido == null || position >= listaProdutoPedido.size()) {
            return;
        }
        PedidoHelper.getActivityPedidoMain().enableActionMode(position);
    }

    @Override
    public void onButtonExcClicked(final int position) {
        if (position < 0 || listaProdutoPedido == null || position >= listaProdutoPedido.size()) {
            return;
        }
        if (adapter.getSelectedItemCount() <= 0) {
            final WebPedidoItens item = listaProdutoPedido.get(position);

            DialogHelper.showActionDialog(
                    requireContext(),
                    "Tem certeza que deseja excluir o produto selecionado?",
                    "NÃO", null,
                    "SIM", R.drawable.ic_delete,
                    null, null,
                    new DialogHelper.OnActionSelectedListener() {
                        @Override
                        public void onPositive() {
                            // "NÃO" - Não faz nada
                        }

                        @Override
                        public void onNegative() {
                            // "SIM" - Executa a exclusão
                            List<WebPedidoItens> listaExclusao = new ArrayList<>();
                            listaExclusao.add(item);
                            PedidoHelper.getActivityPedidoMain().excluiProdutos(null, listaExclusao);
                        }

                        @Override
                        public void onNeutral() {
                        }
                    },
                    getPopulatedProductExclusionView(item)
            );
        }
    }

    private View getPopulatedProductExclusionView(WebPedidoItens item) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.layout_dialog_resumo_produto_exclusao, null);

        ImageView ivProduto = view.findViewById(R.id.ivProdutoExclusao);
        TextView tvNome = view.findViewById(R.id.tvNomeProdutoExclusao);
        TextView tvDetalhe = view.findViewById(R.id.tvDetalheProdutoExclusao);

        if (tvNome != null) tvNome.setText(item.getNome_produto());
        if (tvDetalhe != null) {
            tvDetalhe.setText("Quantidade: " + item.getQuantidade() + " | Total: " + MascaraUtil.mascaraRealSimbolo(item.getValor_total()));
        }

        if (ivProduto != null) {
            try {
                Cursor cursor = db.listaDados("SELECT FOTO_ARQUIVO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" +
                        item.getId_produto() + "' AND ATIVO = 'S' AND FOTO_PRINCIPAL = 'S'");
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        @SuppressLint("Range") String fotoBase64 = cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO"));
                        if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                            byte[] imageBytes = Base64.decode(fotoBase64, Base64.DEFAULT);
                            Glide.with(requireContext())
                                    .load(imageBytes)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.ic_image_gallery_newww)
                                            .error(R.drawable.ic_image_gallery_newww)
                                            .centerInside())
                                    .into(ivProduto);
                        }
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                ivProduto.setImageResource(R.drawable.ic_image_gallery_newww);
            }
        }

        return view;
    }

    @Override
    public void onButtonAnlClicked(int position) {
        try {
            if (bundle.getInt("vizualizacao") != 1) {
                Toast toast = Toast.makeText(context, "Data da analise do estoque\n"
                        + MascaraUtil.formataData(PedidoHelper.getWebPedido().getAnalise_estoque_data_hora().substring(0, 10), "/")
                        + PedidoHelper.getWebPedido().getAnalise_estoque_data_hora().substring(10), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();
        if (count == 0) ActivityPedidoMain.actionMode.finish();
        else ActivityPedidoMain.actionMode.setTitle(String.valueOf(count));
    }

    private String getSqlOtimizado(String orderBy) {
        return "SELECT WI.*, P.NOME_PRODUTO AS P_NOME_PRODUTO, P.ID_LINHA_COLECAO AS ID_LINHA_COLECAO_P, " +
                "P.UNIDADE AS UNIDADE_P, P.VENDA_PRECO AS VENDA_PRECO_P, P.ATIVO AS ATIVO_P, " +
                "P.NOME_SUB_GRUPO AS NOME_SUB_GRUPO_P, P.NOME_GRUPO AS NOME_GRUPO_P, " +
                "P.PRODUTO_EDITA_PRECO_APP AS PRODUTO_EDITA_PRECO_APP_P, " +
                "TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA " +
                "FROM TBL_WEB_PEDIDO_ITENS AS WI " +
                "LEFT JOIN TBL_PRODUTO AS P ON P.ID_PRODUTO = WI.ID_PRODUTO " +
                "LEFT JOIN TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() + " " +
                "LEFT JOIN TBL_TABELA_PRECO_CAB AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                "WHERE WI.ID_PEDIDO = '" + PedidoHelper.getIdPedido() + "' " +
                "ORDER BY " + orderBy;
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(v.getContext(), v, Gravity.START, 0, R.style.PopupProdutoMenu);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        popup.show();
    }

    @SuppressLint("Range")
    private void verificaDescontoIndevido(PedidoBO pedidoBO) {
        if (listaProdutoPedido == null || listaProdutoPedido.isEmpty()) return;
        boolean algumDescontoIndevido = false;
        float descontoTabelaBase = 0.00f;
        float descontoPrazoBase = 0.00f;

        try {
            List<TabelaPrecoItem> listaTab = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria() + " LIMIT 1");
            if (listaTab != null && !listaTab.isEmpty()) {
                tabelaPrecoItem = listaTab.get(0);
                descontoTabelaBase = Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());
            }
        } catch (Exception ignored) {}

        try {
            Cursor cursor = db.listaDados("SELECT DESCONTO_PERC FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = '" + PedidoHelper.getWebPedido().getId_condicao_pagamento() + "' AND ACEITA_DESCONTO = 'S'");
            if (cursor != null && cursor.moveToNext()) {
                descontoPrazoBase = Float.parseFloat(cursor.getString(cursor.getColumnIndex("DESCONTO_PERC")));
                cursor.close();
            }
        } catch (Exception ignored) {}

        for (WebPedidoItens item : listaProdutoPedido) {
            float promoDesc = 0f;
            try { promoDesc = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro_servidor(), item.getId_produto(), getActivity()).getValorDesconto(); } catch (Exception ignored) {}
            float maxDesc = Math.max(descontoTabelaBase, promoDesc);
            if (maxDesc <= 0) maxDesc = descontoPrazoBase;
            float itemDesc = 0f;
            try { itemDesc = Float.parseFloat(item.getValor_desconto_per()); } catch (Exception ignored) {}
            if (itemDesc > maxDesc) {
                item.setDescontoIndevido(true);
                algumDescontoIndevido = true;
            } else {
                item.setDescontoIndevido(false);
            }
        }
        PedidoHelper.getWebPedido().setDescontoIndevido(algumDescontoIndevido ? "S" : "N");
        PedidoHelper.getActivityPedidoMain().updatePedido();
    }

    private void setTabelaPrecoItem() {
        try {
            if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                String sql = ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4") ?
                        "SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria() + " LIMIT 1" :
                        "SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() + " LIMIT 1";
                List<TabelaPrecoItem> lista = db.listaTabelaPrecoItem(sql);
                if (!lista.isEmpty()) tabelaPrecoItem = lista.get(0);
            }
        } catch (Exception ignored) {}
    }

    public void limpaListaProdutosExcluidos() {
        if (listaProdutoRemovido != null) {
            listaProdutoRemovido.clear();
        }
    }

    private void showFeedbackDialog(String message, boolean success) {
        if (getActivity() == null) return;
        DialogHelper.showFeedbackDialog(getActivity(), "Atenção", message, success, success ? R.drawable.ic_check_circle : R.drawable.ic_baseline_error_outline_24, null, null);
    }
}
