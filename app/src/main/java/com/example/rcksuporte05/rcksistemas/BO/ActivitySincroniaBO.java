package com.example.rcksuporte05.rcksistemas.BO;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.example.rcksuporte05.rcksistemas.DAO.BairroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroMotivoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComClientesDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialItensDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CobrancaStatusDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CondicoesPagamentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ContasBancoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MoedaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.MunicipioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.OperacaoEstoqueDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PaisesDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoLinhaColecaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoSkuDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProspectDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ReceberDAO;
import com.example.rcksuporte05.rcksistemas.DAO.RegiaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.SegmentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.TabelaPrecoCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.TabelaPrecoItemDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VendedorBonusResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VisitaProspectDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPrincipal;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.CadastroCondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComClientes;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.ClienteResumo;
import com.example.rcksuporte05.rcksistemas.model.CobrancaStatus;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Deposito;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.model.Municipio;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.Pais;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoGrupo;
import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;
import com.example.rcksuporte05.rcksistemas.model.ProdutoParametroSku;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSku;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.PromocaoCliente;
import com.example.rcksuporte05.rcksistemas.model.PromocaoProduto;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.ReceberCab;
import com.example.rcksuporte05.rcksistemas.model.Segmento;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoCab;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by RCK 03 on 06/10/2017.
 */

public class ActivitySincroniaBO {
    private static Activity activity;
    private DBHelper db = new DBHelper(activity);
    private CategoriaDAO categoriaDAO;
    private PromocaoDAO promocaoDAO;
    private PromocaoClienteDAO promocaoClienteDAO;
    private PromocaoProdutoDAO promocaoProdutoDAO;
    private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;
    private CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO;
    private CadastroAnexoDAO cadastroAnexoDAO;
    private CampanhaComClientesDAO campanhaComClientesDAO;
    private CampanhaComercialCabDAO campanhaComercialCabDAO;
    private CampanhaComercialItensDAO campanhaComercialItensDAO;
    private ProdutoLinhaColecaoDAO produtoLinhaColecaoDAO;

    private ClienteDAO clienteDAO = new ClienteDAO(db);
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    private UsuarioDAO usuarioDAO = new UsuarioDAO(db);
    private TabelaPrecoItemDAO tabelaPrecoItemDAO = new TabelaPrecoItemDAO(db);
    private TabelaPrecoCabDAO tabelaPrecoCabDAO = new TabelaPrecoCabDAO(db);
    private CondicoesPagamentoDAO condicoesPagamentoDAO = new CondicoesPagamentoDAO(db);
    private EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
    private PaisesDAO paisesDAO = new PaisesDAO(db);
    private MoedaDAO moedaDAO = new MoedaDAO(db);
    private SegmentoDAO segmentoDAO = new SegmentoDAO(db);
    private OperacaoEstoqueDAO operacaoEstoqueDAO = new OperacaoEstoqueDAO(db);
    private ProspectDAO prospectDAO = new ProspectDAO(db);
    private VisitaProspectDAO visitaProspectDAO = new VisitaProspectDAO(db);
    private CadastroMotivoDAO cadastroMotivoDAO = new CadastroMotivoDAO(db);
    private ProdutoSkuDAO produtoSkuDAO = new ProdutoSkuDAO(db);
    private ContasBancoDAO contasBancoDAO = new ContasBancoDAO(db);
    private FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
    private RegiaoDAO regiaoDAO = new RegiaoDAO(db);
    private BairroDAO bairroDAO = new BairroDAO(db);
    private MunicipioDAO municipioDAO = new MunicipioDAO(db);
    private VendedorBonusResumoDAO vendedorBonusResumoDAO = new VendedorBonusResumoDAO(db);
    private ReceberDAO webReceberCabDAO = new ReceberDAO(db);
    private CobrancaStatusDAO cobrancaStatusDAO = new CobrancaStatusDAO(db);
    Rotas apiRotas;
    //private CadastroCondicoesPagDAO cadastroCondicoesPagDAO;

   public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        ActivitySincroniaBO.activity = activity;
    }
    public int totalUpdatePedido = 0, totalDeletePedido = 0;
    public int totalClienteUpdate = 0, totalProdutoUpdate = 0;

    @TargetApi(Build.VERSION_CODES.N)
    public void sincronizaBanco(final Sincronia sincronia, final String versaoApp, final NotificationCompat.Builder notificacao, final NotificationManager mNotificationManager, final ProgressDialog progress) {
        if (sincronia.isCliente())
            addClientes(progress, sincronia);
        System.gc();

        if (sincronia.isProduto())
            addProdutos(progress, sincronia);
        System.gc();

        if (sincronia.isPedidosPendentes())
            addPedidosPendentes(progress, sincronia);
        System.gc();

        if (sincronia.isPedidosFinalizados())
            addPedidosFinalizados(progress, sincronia);
        System.gc();

        if (sincronia.isProspectPendentes()) {
            activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de prospects...aguarde"));
            try {
                if (sincronia.getListaProspectPendentes() != null && sincronia.getListaProspectPendentes().size() > 0)
                    for (Prospect prospect : sincronia.getListaProspectPendentes())
                        prospectDAO.addUpdate(prospect);
                        //db.atualizarTBL_PROSPECT(prospect);

            }catch ( NullPointerException e) {
                e.printStackTrace();
            }
        }
        System.gc();

        if (sincronia.isProspectEnviados()) {
            activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de prospects...aguarde"));
            if (sincronia.getListaProspectEnviados() != null)
                for (Prospect prospect : sincronia.getListaProspectEnviados()) {
                    if (db.contagem("SELECT COUNT(*) FROM TBL_PROSPECT WHERE ID_CADASTRO = " + prospect.getId_cadastro()) > 0) {
                        try {
                            prospectDAO.delete(prospect, "1");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    prospect.setProspectSalvo("S");
                    prospectDAO.addUpdate(prospect);
                    try {
                        if (prospect.getVisitas() != null && prospect.getVisitas().size() > 0) {
                            for (VisitaProspect visita : prospect.getVisitas()) {
                                visita.setProspect(prospect);
                                visitaProspectDAO.addUpdate(visita);
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
        }
        System.gc();

        if (sincronia.getListaProspectPositivados().size() > 0) {
            activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de prospects...aguarde"));
            for (Prospect prospectPositivado : sincronia.getListaProspectPositivados())
                prospectDAO.delete(prospectPositivado, "1");
        }
        System.gc();

        if (sincronia.isVisitasPendentes()) {
            activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de visitas...aguarde"));
            try {
                if (sincronia.getVisitas() != null && sincronia.getVisitas().size() > 0) {
                    for (VisitaProspect visita : sincronia.getVisitas())
                        visitaProspectDAO.addUpdate(visita);
                }
            }catch ( NullPointerException e) {
                e.printStackTrace();
            }
        }
        System.gc();
        if (sincronia.isProdutoFoto())
            addProdutoFotos(progress, sincronia );
        else
            updateProdutoFotos(progress, sincronia );
        System.gc();
        if ( sincronia.isCobranca()) {
            addCobrancas(progress, sincronia);
            System.gc();
        }

        addUsuarios(progress, sincronia);
        updateParametros(progress, sincronia);
        updateConfiguracao(progress,sincronia);

        if ( !( !sincronia.isCliente() && !sincronia.isProduto()  && !sincronia.isPedidosPendentes() && !sincronia.isPedidosFinalizados()
                && !sincronia.isProspectPendentes() && !sincronia.isPedidosFinalizados() && !sincronia.isVisitasPendentes() && sincronia.isProdutoFoto() )  ) {
            activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabelas auxiliares...aguarde"));

            if (sincronia.getListaOperacao().size() > 0) {
                db.deleteDados("TBL_OPERACAO_ESTOQUE", "1", null);
                for (Operacao operacao : sincronia.getListaOperacao())
                    operacaoEstoqueDAO.add(operacao);
            } else
                Toast.makeText(activity, "Lista de operação de estoque vazia!!", Toast.LENGTH_SHORT).show();
            System.gc();

            db.deleteDados("TBL_TABELA_PRECO_CAB", "1", null);
            for (TabelaPrecoCab tabelaPrecoCab : sincronia.getListaTabelaPrecoCab())
                tabelaPrecoCabDAO.add(tabelaPrecoCab);
             System.gc();

            db.deleteDados("TBL_TABELA_PRECO_ITENS", "1", null);
            for (TabelaPrecoItem tabelaPrecoItem : sincronia.getListaTabelaPrecoItem())
                tabelaPrecoItemDAO.add(tabelaPrecoItem);
            System.gc();

            if (sincronia.getListaCondicoesPagamento().size() > 0) {
                db.deleteDados("TBL_CONDICOES_PAG_CAB", "1", null);
                for (CondicoesPagamento condicoesPagamento : sincronia.getListaCondicoesPagamento())
                    condicoesPagamentoDAO.add(condicoesPagamento);
            } else
                Toast.makeText(activity, "Lista de condições de pagamento/prazo vazia!!", Toast.LENGTH_SHORT).show();
            System.gc();

            db.deleteDados("TBL_PAISES", "1", null);
            for (Pais pais : sincronia.getListaPais())
                paisesDAO.add(pais);
            System.gc();

            for (MotivoNaoCadastramento motivo : sincronia.getMotivos())
                cadastroMotivoDAO.addUpdate(motivo);
            System.gc();

            db.deleteDados("TBL_CADASTRO_CATEGORIA", "1", null);
            for (Categoria categoria : sincronia.getListaCategoria())
                categoriaDAO.addUpdate(categoria);
            System.gc();

            db.deleteDados("TBL_CADASTRO_FINANCEIRO_RESUMO", "1", null);
            for (CadastroFinanceiroResumo cadastroFinanceiroResumo : sincronia.getListaCadastroFinanceiroResumo())
                cadastroFinanceiroResumoDAO.addUpdate(cadastroFinanceiroResumo);
            System.gc();

            db.deleteDados("TBL_PROMOCAO_CAB", "1", null);
            String args[] = {};
            for (Promocao promocao : sincronia.getListaPromocao()) {
                promocaoDAO.addUpdate(promocao);
                try {
                    db.deleteDados("TBL_PROMOCAO_CLIENTE", "ID_PROMOCAO = ?", new String[]{String.valueOf(promocao.getIdPromocao())});
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                System.gc();

                for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente())
                    promocaoClienteDAO.addUpdate(promocaoCliente);
                System.gc();

                try {
                    db.deleteDados("TBL_PROMOCAO_PRODUTO", "ID_PROMOCAO = ?", new String[]{String.valueOf(promocao.getIdPromocao())});
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto())
                    promocaoProdutoDAO.addUpdate(promocaoProduto);
                System.gc();
            }
            db.deleteDados("TBL_PRODUTO_SUB_GRUPO", "1", null);
            for (ProdutoSubGrupo produtoSubGrupo : sincronia.getListaProdutoSubGrupo())
                produtoDAO.add(produtoSubGrupo);
            System.gc();

            db.deleteDados("TBL_PRODUTO_GRUPO", "1", null);
            for (ProdutoGrupo produtoGrupo : sincronia.getListaProdutoGrupo())
                produtoDAO.add(produtoGrupo);
            System.gc();

            db.deleteDados("TBL_MOEDA", "1", null);
            for (Moeda moeda : sincronia.getListaMoeda())
                moedaDAO.add(moeda);
            System.gc();

            db.deleteDados("TBL_CADASTRO_CONDICOES_PAG", "1", null);
            try {
                for (CadastroCondicoesPagamento cadastroCondicoesPagamento : sincronia.getListaCadastroCondicoesPagamento())
                    condicoesPagamentoDAO.add(cadastroCondicoesPagamento);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.gc();

            db.deleteDados("TBL_CADASTRO_SETOR", "1", null);
            for (Segmento segmento : sincronia.getListaSegmento())
                segmentoDAO.add(segmento);
            System.gc();

            db.deleteDados("TBL_DEPOSITO", "1", null);
            for (Deposito deposito : sincronia.getListaDeposito())
                produtoDAO.add(deposito);
            System.gc();

            db.deleteDados("TBL_CONTAS_BANCO", "1", null);
            for (Generico conta : sincronia.getListaContaBanco())
                contasBancoDAO.add(conta);
            System.gc();

            try {
                db.deleteDados("TBL_CADASTRO_BAIRRO", "1", null);
                for (Generico bairro : sincronia.getListaBairro())
                    bairroDAO.add(bairro);
            } catch ( NullPointerException e) {
               e.printStackTrace();
            }
            System.gc();

            try {
                db.deleteDados("TBL_CADASTRO_REGIAO", "1", null);
                for (Generico regiao : sincronia.getListaRegiao())
                    regiaoDAO.add(regiao);
            } catch ( NullPointerException e) {

            }
            System.gc();
        }
        System.gc();
        db.deleteDados("TBL_CAMPANHA_COM_CLIENTES", "1", null);
        for (CampanhaComClientes campanhaComClientes : sincronia.getListaCampanhaComClientes())
            try {
                campanhaComClientesDAO.add(campanhaComClientes);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
        }
        System.gc();

        db.deleteDados("TBL_CAMPANHA_COMERCIAL_CAB", "1", null);
        for (CampanhaComercialCab campanhaComercialCab : sincronia.getListaCampanhaComercialCab())
            try {
                campanhaComercialCabDAO.add(campanhaComercialCab);
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
        System.gc();

        db.deleteDados("TBL_CAMPANHA_COMERCIAL_ITENS", "1", null);
        for (CampanhaComercialItens campanhaComercialItens : sincronia.getListaCampanhaComercialItens())
            try {
                campanhaComercialItensDAO.add(campanhaComercialItens);
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
        System.gc();

        db.deleteDados("TBL_PRODUTO_LINHA_COLECAO", "1", null);
        for (ProdutoLinhaColecao produtoLinhaColecao : sincronia.getListaProdutoLinhaColecao())
            try {
                produtoLinhaColecaoDAO.add(produtoLinhaColecao);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        System.gc();

        try {
            db.deleteDados("TBL_VENDEDOR_BONUS_RESUMO", "1", null);
            for (VendedorBonusResumo vendedorBonusResumo : sincronia.getListaVendedorBonus())
                try {
                    vendedorBonusResumoDAO.add(vendedorBonusResumo);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        System.gc();
        try {
            db.deleteDados("TBL_CADASTRO_MUNICIPIO", "1", null);
            for (Municipio municipio : sincronia.getListaMunicipio()) {
               municipioDAO.add(municipio);
            }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        }

        /*
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
            notificacao.setContentText("Completo")
                    .setContentTitle("Sincronia concluída!!")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            mNotificationManager.notify(0, notificacao.build());
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

         */
        System.gc();
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        if (sincronia.isPedidosPendentes())
            alert.setMessage("Sincronia concluida com sucesso!!\n" + "Pedido(s) pendente(s) sincronizado(s) (" + totalUpdatePedido + ")\n");
        else
            alert.setMessage("Sincronia concluida com sucesso!!\n");
        alert.setTitle("Atenção");
        String btnOK = "   OK   ";
        String version = versaoApp;
        String versao = UsuarioHelper.getUsuario().getAparelho_versao_app();
        String url = "";UsuarioHelper.getUsuario().getAparelho_link_app();
        try {
            url = UsuarioHelper.getUsuario().getAparelho_link_app();
            if (!versao.isEmpty() && !url.isEmpty()) {
                if (!version.equalsIgnoreCase(UsuarioHelper.getUsuario().getAparelho_versao_app()))
                    alert.setTitle("Nova versão do aplicativo disponivel para atualização!!");
                else
                    url = "";

            } else {
                url = "";
            }
        } catch ( NullPointerException e) {
            url = "";
            e.printStackTrace();
        } catch ( Exception e) {
            url = "";
            e.printStackTrace();
        }
        String urlApi = url;
        alert.setNeutralButton(btnOK, (dialog, which) -> {
            mNotificationManager.cancel(0);
            ActivityPrincipal.updateApp(urlApi);
           System.gc();
        });

        if (sincronia.isProduto())
            db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_PRODUTO = '" + db.pegaDataHoraAtual() + "';");
        if ( sincronia.isCliente())
            db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_CLIENTE = '" + db.pegaDataHoraAtual() + "';");
        if ( sincronia.isCobranca())
            db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_COBRANCA = '" + db.pegaDataHoraAtual() + "';");
        if ( sincronia.isProdutoFoto())
            db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA_IMAGEN = '" + db.pegaDataHoraAtual() + "';");
        db.update("UPDATE TBL_LOGIN SET DATA_SINCRONIA = '" + db.pegaDataHoraAtual() + "';");
        if ( !( !sincronia.isCliente() && !sincronia.isProduto()  && !sincronia.isPedidosPendentes() && !sincronia.isPedidosFinalizados()
                && !sincronia.isProspectPendentes() && !sincronia.isPedidosFinalizados() && !sincronia.isVisitasPendentes() && sincronia.isProdutoFoto())   ) {
            mNotificationManager.cancel(0);
            System.gc();
            activity.runOnUiThread(() -> {
                progress.dismiss();
                String dataSincronia = "00/00/00" + "\n " + "00:00:00";
                try {
                    dataSincronia +=  "\n " + new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(db.consultaDados("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    ActivityPrincipal.atualizaDataSincronia(dataSincronia);
                } catch ( NullPointerException e) {
                    e.printStackTrace();
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                alert.show();
            });
        } else {
            activity.runOnUiThread(() -> {
                progress.dismiss();
                alert.show();
            });
        }
       //ActivityPrincipal.setSincronizando();
        System.gc();
    }

    private void updateParametros(ProgressDialog progress, Sincronia sincronia) {
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando parametros da empresa...aguarde"));
        if ( sincronia.getListaEmpresa().size() > 0) {
            db.deleteDados("TBL_EMPRESA_PARAMETRO", "1", null);
            try {
                for (EmpresaParametro empresaParametro : sincronia.getListaEmpresa())
                    empresaParametroDAO.add(empresaParametro);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateConfiguracao(ProgressDialog progress, Sincronia sincronia) {
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando configuração de acesso...aguarde"));
        try {
            if ( sincronia.getListaEmpresa().size() > 0) {
                ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
                for (EmpresaParametro empresa : sincronia.getListaEmpresa()) {
                    String campanha_no_pedido = empresa.getCampanha_no_pedido_venda();
                    String dscto_valor_no_pedido = empresa.getDscto_valor_no_pedido_venda();
                    String limite_credito = empresa.getUtiliza_limite_credito();
                    String confirma_prazo = empresa.getUtiliza_confirmacao_prazo();
                    String pre_pedido = "N";
                    String usa_unid_estoque = empresa.getUtiliza_unidade_manut_estoque();
                    String usa_paginacao = empresa.getUtiliza_paginacao_dados();
                    String layout_venda_erp = empresa.getLayout_venda_erp();
                    String id_con_pgto_default_app = empresa.getId_con_pgto_default_app();
                    String usa_cobranca_app = empresa.getUtiliza_cobranca_offline();
                    String usa_icms_app = empresa.getUtiliza_icms_app();
                    String usa_desc_auto_app = empresa.getUtiliza_desct_auto_app();
                    String sincronia_automatica = empresa.getSincronia_automatica_app();
                    String usa_verba_pedido_app = empresa.getUtiliza_verba_pedido_app();
                    String cadastra_cliente_app = empresa.getCadastra_cliente_app();
                    String visual_hist_fin_pedido_app = empresa.getVisual_hist_fin_pedido_app();
                    String visual_prazo_pgto_pedido_app = empresa.getVisual_prazo_pgto_pedido_app();
                    String visual_estoque_produto_app = empresa.getVisual_estoque_produto_app();
                    String ordem_lista_cliente_app    = empresa.getOrdem_lista_cliente_app();
                    String atual_lista_ped_exportado  = empresa.getAtual_lista_ped_exportado();
                    String confirma_sincronia_dados   = empresa.getConfirma_sincronia_dados_app();
                    String cnpj_empresa               = empresa.getCnpj_cpf();
                    String marca_produto_ja_comprado  = empresa.getMarca_produto_ja_comprado_app();
                    try {
                        if (empresa.getSincronia_automatica_app().isEmpty() || empresa.getSincronia_automatica_app() == null)
                            sincronia_automatica = "S";
                    } catch (NullPointerException e) {
                        sincronia_automatica = "S";
                    } catch (Exception e) {
                        sincronia_automatica = "S";
                    }
                    try {
                        if (empresa.getUtiliza_verba_pedido_app().isEmpty() || empresa.getUtiliza_verba_pedido_app() == null)
                            usa_verba_pedido_app = "N";
                    } catch (NullPointerException e) {
                        usa_verba_pedido_app = "N";
                    } catch (Exception e) {
                        usa_verba_pedido_app = "N";
                    }

                    try {
                        pre_pedido = empresa.getPre_pedido();
                        if (pre_pedido.isEmpty() || pre_pedido == null)
                            pre_pedido = "N";
                    } catch (NullPointerException e) {
                        pre_pedido = "N";
                        e.printStackTrace();
                    } catch (Exception e) {
                        pre_pedido = "N";
                        e.printStackTrace();
                    }
                    String pedido_enviado_on_line_app    = empresa.getPedido_enviado_on_line_app() == null || empresa.getPedido_enviado_on_line_app().isEmpty() ? "N" : empresa.getPedido_enviado_on_line_app();
                    String visualiza_grafico_on_line_app = empresa.getVisualiza_grafico_on_line_app() == null || empresa.getVisualiza_grafico_on_line_app().isEmpty() ? "N" : empresa.getVisualiza_grafico_on_line_app();
                    String visualiza_ultimo_preco_venda_app = empresa.getVisualiza_ultimo_preco_venda_app() == null || empresa.getVisualiza_ultimo_preco_venda_app().isEmpty() ? "N" : empresa.getVisualiza_ultimo_preco_venda_app();
                    String calcula_dscto_automatico_muda_prazo = empresa.getCalcula_dscto_automatico_muda_prazo() == null || empresa.getCalcula_dscto_automatico_muda_prazo().isEmpty() ? "N" : empresa.getCalcula_dscto_automatico_muda_prazo();
                    String trabalha_com_chekin = empresa.getConfirma_sincronia_checkin_app() == null || empresa.getConfirma_sincronia_checkin_app().isEmpty() ? "N" : empresa.getConfirma_sincronia_checkin_app();
                    configuracaoDAO.update(campanha_no_pedido, dscto_valor_no_pedido, limite_credito, pre_pedido, confirma_prazo, usa_unid_estoque,
                            usa_paginacao, layout_venda_erp, id_con_pgto_default_app, usa_cobranca_app,
                            usa_icms_app, usa_desc_auto_app, sincronia_automatica, usa_verba_pedido_app,
                            cadastra_cliente_app, visual_hist_fin_pedido_app, visual_prazo_pgto_pedido_app,
                            visual_estoque_produto_app, ordem_lista_cliente_app, atual_lista_ped_exportado,
                            confirma_sincronia_dados, cnpj_empresa, marca_produto_ja_comprado,
                            pedido_enviado_on_line_app, visualiza_grafico_on_line_app, visualiza_ultimo_preco_venda_app,
                            calcula_dscto_automatico_muda_prazo, trabalha_com_chekin);
                }
            }
        } catch ( NullPointerException e ){
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void addUsuarios(ProgressDialog progress, Sincronia sincronia) {
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de usuários...aguarde"));
        try {
            db.deleteDados("TBL_WEB_USUARIO", "1", null);
            for (Usuario usuario : sincronia.getListaUsuario()) {
                usuarioDAO.add(usuario);
                try {
                    if (usuario.getId_usuario().equalsIgnoreCase(UsuarioHelper.getUsuario().getId_usuario()))
                        UsuarioHelper.setUsuario(usuario);
                } catch (NullPointerException e) {
                    Toast.makeText(activity, "Falha ao atualizar o usuário logado no aplicativo!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(activity, "Falha ao atualizar o usuário logado no aplicativo!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        System.gc();
    }

    private void addClientes(ProgressDialog progress, Sincronia sincronia) {
        db.deleteDados("TBL_CADASTRO","ID_CADASTRO_SERVIDOR > ?", new String[]{"0"});
        System.gc();
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de cliente(s)...aguarde"));
        try {
            for (Cliente cliente : sincronia.getListaCliente()) {
                clienteDAO.delete(cliente, "1");  //0 == local 1 = Servidor
                int idCadastro = (int) clienteDAO.add(cliente);
                if (idCadastro > 0 && cliente.getListaCadastroAnexo().size() > 0) {
                    for (CadastroAnexo cadastroAnexo : cliente.getListaCadastroAnexo()) {
                        cadastroAnexo.setIdCadastro(idCadastro);
                        cadastroAnexoDAO.addUpdate(cadastroAnexo);
                    }
                    totalClienteUpdate++;
                }
            }
            db.deleteDados("TBL_CADASTRO_RESUMO", "1", null);
            for (ClienteResumo cliente : sincronia.getListaClienteResumo()) {
                clienteDAO.add(cliente);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void addProdutos(ProgressDialog progress, Sincronia sincronia) {
        db.deleteDados("TBL_PRODUTO", "1", null);
        System.gc();
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de produto(s)...aguarde"));
        try {
            for (Produto produto : sincronia.getListaProduto())
                if (produtoDAO.add(produto) > 0)
                    totalProdutoUpdate++;
        } catch ( NullPointerException e) {

        }
        db.deleteDados("TBL_PRODUTO_SKU", "1", null);
        System.gc();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setTitle("Sincronizando tabela de produto(s)/sku...aguarde");
            }
        });
        try {
            for (ProdutoSku produto : sincronia.getListaProdutoSku())
                if (produtoSkuDAO.add(produto) > 0)
                    totalProdutoUpdate++;
        } catch ( NullPointerException e) {
        } catch ( Exception e){
        }

        db.deleteDados("TBL_PRODUTO_PARAMETRO_SKU", "1", null);
        System.gc();
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de produto(s)/sku...aguarde"));
        try {
            for (ProdutoParametroSku produto : sincronia.getListaProdutoParametroSku())
                if (produtoSkuDAO.add(produto) > 0)
                    totalProdutoUpdate++;
        } catch ( NullPointerException e) {
        } catch ( Exception e){
        }
    }

    private void addPedidosPendentes(ProgressDialog progress, Sincronia sincronia) {
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de pedidos pendentes...aguarde"));
        for (WebPedido webPedido : sincronia.getListaWebPedidosPendentes()) {
            final CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
            if ( webPedidoDAO.update( webPedido) > 0) {
                totalUpdatePedido++;
                for (WebPedidoItens pedidoIten : webPedido.getWebPedidoItens())
                    webPedidoItensDAO.update( pedidoIten);
            } else
                totalUpdatePedido--;
            try {
                clienteDAO.update(webPedido.getCadastro());
                try {
                    db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(webPedido.getCadastro().getId_cadastro())});
                } catch ( Exception e){
                    e.printStackTrace();
                }
                if (webPedido.getCadastro().getListaCadastroAnexo().size() > 0)
                    for (CadastroAnexo cadastroAnexo : webPedido.getCadastro().getListaCadastroAnexo())
                        if (cadastroAnexo.getExcluido().equals("N"))
                            cadastroAnexoDAO.addUpdate(cadastroAnexo);
                        else if (cadastroAnexo.getExcluido().equals("S"))
                            try {
                                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_ANEXO = ?", new String[]{String.valueOf(cadastroAnexo.getIdAnexo())});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        else
                            try {
                                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO = ?", new String[]{String.valueOf(webPedido.getCadastro().getId_cadastro())});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
            } catch ( NullPointerException|ArrayIndexOutOfBoundsException e ) {
            } catch ( Exception e) {
            }
        }
        excluirPedidoSincronizado();
    }

    private void addPedidosFinalizados(ProgressDialog progress, Sincronia sincronia) {
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de pedido(s)...aguarde"));
        try {
            excluirPedidoSincronizado();
            for (WebPedido webPedido : sincronia.getListaWebPedidosFinalizados()) {
                if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor()) > 0)
                    try {
                        db.deleteDados("TBL_WEB_PEDIDO", "ID_WEB_PEDIDO_SERVIDOR = ?", new String[]{webPedido.getId_web_pedido_servidor()});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                int idPedido = (int) webPedidoDAO.add(webPedido);
                if (idPedido > 0) {
                    try {
                        for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                            webPedidoItens.setId_pedido(String.valueOf(idPedido));
                            if (!webPedidoItensDAO.update(webPedidoItens))
                                if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItens.getId_produto().trim() + "') OR " +
                                        "(ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItens.getId_produto() + "');") <= 0) {
                                    webPedidoItensDAO.add(webPedidoItens);
                                }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    idPedido = 0;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void addProdutoFotos(ProgressDialog progress, Sincronia sincronia) {
        db.deleteDados("TBL_PRODUTO_FOTO", "1", null);
        System.gc();
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de imagens de produto...aguarde"));
        try {
            if (sincronia.getListaProdutoFoto1().size() > 0)
                for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto1())
                    produtoDAO.add(produtoFoto);

            if (sincronia.getListaProdutoFoto2().size() > 0)
                for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto2())
                    produtoDAO.add(produtoFoto);

            if (sincronia.getListaProdutoFoto3().size() > 0)
                for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto3())
                    produtoDAO.add(produtoFoto);

            if (sincronia.getListaProdutoFoto4().size() > 0)
                for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto4())
                    produtoDAO.add(produtoFoto);

            if (sincronia.getListaProdutoFoto5().size() > 0)
                for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto5())
                    produtoDAO.add(produtoFoto);

            if (sincronia.getListaProdutoFoto6().size() > 0)
                for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto6())
                    produtoDAO.add(produtoFoto);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProdutoFotos(ProgressDialog progress, Sincronia sincronia) {
        System.gc();
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de imagens de produto...aguarde"));
        try {
            for (ProdutoFoto produtoFoto : sincronia.getListaProdutoFoto())
                if ( produtoFoto.getId_usuario().equalsIgnoreCase("0"))
                    produtoDAO.updateFoto(produtoFoto);
                else
                    produtoDAO.add(produtoFoto);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCobrancas(ProgressDialog progress, Sincronia sincronia) {
        System.gc();
        activity.runOnUiThread(() -> progress.setTitle("Sincronizando tabela de cobrança...aguarde"));
        try {
            db.deleteDados("TBL_FINANCEIRO_RESUMO", "1", null);
            db.deleteDados("TBL_COBRANCA_STATUS", "1", null);
            for (FinanceiroResumo financeiro : sincronia.getListaCobranca())
                financeiroDAO.add(financeiro);
            for (CobrancaStatus cobrancaStatus : sincronia.getListaCobrancaStatus())
                cobrancaStatusDAO.add(cobrancaStatus);
        } catch ( NullPointerException e) {
            e.printStackTrace();
        }
        System.gc();

        try {
            String id_android = Settings.Secure.getString(getActivity().getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
            db.deleteDados("TBL_WEB_RECEBER_CAB", "STATUS = ? AND EXCLUIDO = ? AND ID_ANDROID != ? AND ID_LOTE IS NOT NULL", new String[]{"A", "N", id_android});
            if (sincronia.getListaRecebimentos().size() > 0) {
                for (ReceberCab receber : sincronia.getListaRecebimentos()) {
                    try {
                        if (receber.getStatus().equalsIgnoreCase("A") && receber.getExcluido().equalsIgnoreCase("N")) {
                            if (receber.getId_android().equalsIgnoreCase(id_android))
                                webReceberCabDAO.update(receber);
                            else
                                webReceberCabDAO.add(receber);
                        } else if (receber.getStatus().equalsIgnoreCase("R") || receber.getExcluido().equalsIgnoreCase("S")) {
                            webReceberCabDAO.deleteRecebimento(receber, new String[]{receber.getId_web_receber(), "A", receber.getId_android()});
                            webReceberCabDAO.deleteRecebimento(receber, new String[]{receber.getId_web_receber(), "N", receber.getId_android()});
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch ( NullPointerException e){
            e.printStackTrace();
        } catch ( Exception e){
            e.printStackTrace();
        }
    }

    public void sincronizaApi(final Sincronia sincronia, final String versaoApp) {
        final ImageView ivInternet = (ImageView) activity.findViewById(R.id.ivInternet);
        categoriaDAO = new CategoriaDAO(db);
        promocaoDAO = new PromocaoDAO(db);
        promocaoClienteDAO = new PromocaoClienteDAO(db);
        promocaoProdutoDAO = new PromocaoProdutoDAO(db);
        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
        cadastroAnexoDAO = new CadastroAnexoDAO(db);
        campanhaComClientesDAO = new CampanhaComClientesDAO(db);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        produtoLinhaColecaoDAO = new ProdutoLinhaColecaoDAO(db);
        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.mipmap.ic_sincroniza_main)
                .setContentTitle("Sincronia em andamento")
                .setContentText("Aguarde")
                .setPriority(2)
                .setProgress(0, 0, true);
        final NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notificacao.build());

        final ProgressDialog progress = new ProgressDialog(activity);
        progress.setTitle("Sincronizando, aguarde...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        try {
            progress.show();
        } catch ( IllegalArgumentException e) {
        } catch (Exception e) {
        }
        apiRotas = Api.buildRetrofit(false);

        try {
            final Thread a = new Thread(() -> {
                Map<String, String> cabecalho = new HashMap<>();
                try {
                    if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                        UsuarioBO usuarioBO = new UsuarioBO();
                        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
                    }
                } catch (NullPointerException e) {
                    UsuarioBO usuarioBO = new UsuarioBO();
                    UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
                } catch (Exception e) {
                }

                cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

                if (sincronia.isPedidosPendentes()) {
                    final List<WebPedido> listaParaEnvio = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
                    for (int i = 0; i < listaParaEnvio.size(); i++) {
                        if (listaParaEnvio.get(i).getCadastro().getId_cadastro_servidor() <= -1) {
                            listaParaEnvio.get(i).getCadastro().setId_cadastro_servidor(0);
                        }
                    }
                    sincronia.setListaWebPedidosPendentes(prepararItensPedidos(listaParaEnvio));
                }

                if (sincronia.isProspectPendentes())
                    try {
                        final List<Prospect> lista = prospectDAO.getLista(Prospect.PROSPECT_SALVO);
                        sincronia.setListaProspectPendentes(lista);
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                if (sincronia.isVisitasPendentes())
                    sincronia.setVisitas(visitaProspectDAO.getListaPendentes());//db.listaProspectsPendentes());
                if (sincronia.isCobranca()) {
                    String id_android = Settings.Secure.getString(getActivity().getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
                    sincronia.setListaRecebimentos(webReceberCabDAO.getLista("SELECT * FROM TBL_WEB_RECEBER_CAB WHERE ID_ANDROID = '" + id_android + "'"));  //N nao sincronizado
                }

                String key_firebase = "00000001";

                Call<Sincronia> call = null;
                try {
                    call = apiRotas.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase, versaoApp, cabecalho, sincronia);
                } catch (NullPointerException e) {
                    apiRotas = Api.buildRetrofit(true);
                    try {
                        call = apiRotas.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase, versaoApp, cabecalho, sincronia);
                    } catch (NullPointerException ex) {
                        Toast.makeText(getActivity().getApplicationContext(), "Falha ao criar a rotina para sincronização, feche o aplicativo e tente novamente!", Toast.LENGTH_SHORT).show();
                        return;
                    } catch (Exception ex) {
                        Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                try {
                    Response<Sincronia> response = call.execute();
                    if (response.code() == 200) {
                        Sincronia sincronia1 = response.body();
                        activity.runOnUiThread(() -> ivInternet.setVisibility(View.GONE));
                        sincronizaBanco(sincronia1, versaoApp, notificacao, mNotificationManager, progress);
                    } else {
                        activity.runOnUiThread(() -> {
                            ivInternet.setVisibility(View.VISIBLE);
                            progress.dismiss();
                        });
                        notificacao.setContentText("Erro de comunicação")
                                .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                                .setProgress(0, 0, false)
                                .setSmallIcon(R.mipmap.ic_sem_internet)
                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setPriority(2)
                                .setAutoCancel(true);
                        mNotificationManager.notify(0, notificacao.build());
                    }
                    activity = null;
                    ActivityPrincipal.setSincronizando();
                } catch (ConnectException e) {
                    sincronizaApi2(sincronia, versaoApp, progress);
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(() -> {
                        try {
                            ivInternet.setVisibility(View.VISIBLE);
                            progress.dismiss();
                        } catch (Exception e1) {

                        }
                    });
                    notificacao.setContentText("Erro de comunicação")
                            .setContentTitle("Verifique sua conexão e tente novamente!!")
                            .setProgress(0, 0, false)
                            .setSmallIcon(R.mipmap.ic_sem_internet)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(2)
                            .setAutoCancel(true);
                    mNotificationManager.notify(0, notificacao.build());
                    activity = null;
                    ActivityPrincipal.setSincronizando();
                } catch (Exception e) {
                    e.printStackTrace();
                    activity.runOnUiThread(() -> {
                        try {
                            ivInternet.setVisibility(View.VISIBLE);
                            progress.dismiss();
                        } catch (Exception e1) {

                        }
                    });
                    notificacao.setContentText("Erro de comunicação")
                            .setContentTitle("Verifique sua conexão e tente novamente!!")
                            .setProgress(0, 0, false)
                            .setSmallIcon(R.mipmap.ic_sem_internet)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(2)
                            .setAutoCancel(true);
                    mNotificationManager.notify(0, notificacao.build());
                    activity = null;
                    ActivityPrincipal.setSincronizando();
                }
            });
            a.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sincronizaApi2(final Sincronia sincronia, final String versaoApp, ProgressDialog progress) {
        final ImageView ivInternet = (ImageView) activity.findViewById(R.id.ivInternet);
        categoriaDAO = new CategoriaDAO(db);
        promocaoDAO = new PromocaoDAO(db);
        promocaoClienteDAO = new PromocaoClienteDAO(db);
        promocaoProdutoDAO = new PromocaoProdutoDAO(db);
        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
        cadastroAnexoDAO = new CadastroAnexoDAO(db);

        campanhaComClientesDAO = new CampanhaComClientesDAO(db);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        produtoLinhaColecaoDAO = new ProdutoLinhaColecaoDAO(db);

        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.mipmap.ic_sincroniza_main)
                .setContentTitle("Sincronia em andamento...")
                .setContentText("Aguarde")
                .setPriority(2)
                .setProgress(0, 0, true);
        final NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notificacao.build());

        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        try {
            if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty()) {
                String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp.substring(0, 5) + "/ws/";
                Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                        "http://" + url :
                        "https://" + url;
                Api.time_conn = configuracao.getTime_conn();
                Api.time_read = configuracao.getTime_read();
                Api.time_write = configuracao.getTime_write();

            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivInternet.setVisibility(View.VISIBLE);
                        progress.dismiss();
                    }
                });
                notificacao.setContentText("Erro de comunicação")
                        .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                        .setProgress(0, 0, false)
                        .setSmallIcon(R.mipmap.ic_sem_internet)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(2)
                        .setAutoCancel(true);
                mNotificationManager.notify(0, notificacao.build());
                ActivityPrincipal.setSincronizando();
                return;
            }
        } catch ( NullPointerException e) {
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
            return;
        }
        final Rotas apiRotas2 = Api.buildRetrofit2(true);


        Map<String, String> cabecalho = new HashMap<>();
        try {
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        } catch (Exception e) {
        }

        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        sincronia.setDias_hist_venda(30);
        sincronia.setDias_hist_financeiro(30);
        Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
        try {
            sincronia.setDias_hist_venda(preferencia.getDias_hist_venda());
            sincronia.setDias_hist_financeiro(preferencia.getDias_hist_financeiro());
            sincronia.setDias_hist_pedido(preferencia.getDias_hist_pedido());
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sincronia.isPedidosPendentes()) {
            final List<WebPedido> listaParaEnvio = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
            for (int i = 0; i < listaParaEnvio.size(); i++) {
                if (listaParaEnvio.get(i).getCadastro().getId_cadastro_servidor() <= -1) {
                    listaParaEnvio.get(i).getCadastro().setId_cadastro_servidor(0);
                }
            }
            sincronia.setListaWebPedidosPendentes(prepararItensPedidos(listaParaEnvio));
        }

        if (sincronia.isProspectPendentes())
            try {
                final List<Prospect> lista = prospectDAO.getLista(Prospect.PROSPECT_SALVO);
                sincronia.setListaProspectPendentes(lista);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        if (sincronia.isVisitasPendentes())
            sincronia.setVisitas(visitaProspectDAO.getListaPendentes());

        String key_firebase = "00000001";
        Call<Sincronia> call = apiRotas2.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), key_firebase, versaoApp, cabecalho, sincronia);
        try {
            Response<Sincronia> response = call.execute();
            if (response.code() == 200) {
                Sincronia sincronia2 = response.body();
                activity.runOnUiThread(() -> ivInternet.setVisibility(View.GONE));
                sincronizaBanco(sincronia2, versaoApp, notificacao, mNotificationManager, progress);
            } else {
                activity.runOnUiThread(() -> {
                    ivInternet.setVisibility(View.VISIBLE);
                    progress.dismiss();
                });
                notificacao.setContentText("Erro de comunicação")
                        .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                        .setProgress(0, 0, false)
                        .setSmallIcon(R.mipmap.ic_sem_internet)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(2)
                        .setAutoCancel(true);
                mNotificationManager.notify(0, notificacao.build());
                ActivityPrincipal.setSincronizando();
            }
        } catch (ConnectException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Verifique sua conexão e tente novamente")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Verifique sua conexão e tente novamente")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                ivInternet.setVisibility(View.VISIBLE);
                progress.dismiss();
            });
            notificacao.setContentText("Erro de comunicação")
                    .setContentTitle("Verifique sua conexão e tente novamente")
                    .setProgress(0, 0, false)
                    .setSmallIcon(R.mipmap.ic_sem_internet)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(2)
                    .setAutoCancel(true);
            mNotificationManager.notify(0, notificacao.build());
            ActivityPrincipal.setSincronizando();
        }
        activity = null;
    }

    private List<WebPedido> prepararItensPedidos(List<WebPedido> listaPedido) {
        for (WebPedido pedido : listaPedido) {
            if (pedido.getCadastro().getId_cadastro_servidor() <= 0)
                if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + pedido.getCadastro().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                    final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                    List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(activity, pedido.getCadastro().getId_cadastro());
                    pedido.getCadastro().setListaCadastroAnexo(listaCadastroAnexo);
                }
            List<WebPedidoItens> webPedidoItenses;
            webPedidoItenses = webPedidoItensDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido() + " ORDER BY ID_WEB_ITEM");
            pedido.setWebPedidoItens(webPedidoItenses);
        }
        return listaPedido;
    }

    private void excluirPedidoSincronizado( ) {
        List<WebPedido> listaPedido;
        try {
            Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
            if (!preferencia.getId().isEmpty()) {
                if (preferencia.getDias_hist_pedido() > 0) {
                    String data = preferencia.getDias_hist_pedido() == 1 ? db.pegaDataExclusao("  SELECT date('now')") : db.pegaDataExclusao("  SELECT date('now',  '-" + ( preferencia.getDias_hist_pedido() + 1 ) + " days')");
                    if (!data.isEmpty())
                        try {
                            listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND DATA_EMISSAO <= '" + data + "' ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC");
                            for (int i = 0; i < listaPedido.size(); i++)
                                if (webPedidoDAO.delete(listaPedido.get(i)))
                                    totalDeletePedido++;
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                } else {
                    try {
                        listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC");
                        for (int i = 0; i < listaPedido.size(); i++)
                            if (webPedidoDAO.delete(listaPedido.get(i)))
                                totalDeletePedido++;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC");
                    for (int i = 0; i < listaPedido.size(); i++)
                        if (webPedidoDAO.delete(listaPedido.get(i)))
                            totalDeletePedido++;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}

