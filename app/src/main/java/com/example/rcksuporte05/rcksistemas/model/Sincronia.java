package com.example.rcksuporte05.rcksistemas.model;

import java.util.ArrayList;
import java.util.List;

public class Sincronia {
    private boolean cliente;
    private boolean produto;
    private boolean pedidosFinalizados;
    private boolean pedidosPendentes;
    private boolean prospectPendentes;
    private boolean prospectEnviados;
    private boolean visitasPendentes;
    private boolean produtoFoto;
    private boolean cobranca;
    private int maxProgress;
    private List<Cliente> listaCliente = new ArrayList<>();
    private List<CondicoesPagamento> listaCondicoesPagamento = new ArrayList<>();
    private List<CadastroCondicoesPagamento> listaCadastroCondicoesPagamento = new ArrayList<>();
    private List<Operacao> listaOperacao = new ArrayList<>();
    private List<Produto> listaProduto = new ArrayList<>();
    public  List<ProdutoFoto> listaProdutoFoto = new ArrayList<>() ;
    private List<TabelaPrecoCab> listaTabelaPrecoCab = new ArrayList<>();
    private List<TabelaPrecoItem> listaTabelaPrecoItem = new ArrayList<>();
    private List<Usuario> listaUsuario = new ArrayList<>();
    private List<WebPedido> listaWebPedidosPendentes = new ArrayList<>();
    private List<WebPedido> listaWebPedidosFinalizados = new ArrayList<>();
    private List<Pais> listaPais = new ArrayList<>();
    private List<MotivoNaoCadastramento> motivos = new ArrayList<>();
    private List<Prospect> listaProspectPendentes = new ArrayList<>();
    private List<Prospect> listaProspectEnviados = new ArrayList<>();



    private List<Prospect> listaProspectPositivados = new ArrayList<>();
    private List<VisitaProspect> visitas = new ArrayList<>();
    private List<Categoria> listaCategoria = new ArrayList<>();
    private List<Promocao> listaPromocao = new ArrayList<>();
    private List<CadastroFinanceiroResumo> listaCadastroFinanceiroResumo = new ArrayList<>();
    private List<ProdutoSubGrupo> listaProdutoSubGrupo = new ArrayList<>();
    private List<ProdutoGrupo> listaProdutoGrupo = new ArrayList<>();
    private List<Moeda> listaMoeda = new ArrayList<>();
    private List<EmpresaParametro> listaEmpresa = new ArrayList<>();
    private List<Segmento> listaSegmento = new ArrayList<>();
    private List<Deposito> listaDeposito = new ArrayList<>();
    private List<Generico> listaContaBanco = new ArrayList<>();
    private List<ClienteResumo> listaClienteResumo = new ArrayList<>();
    private List<FinanceiroResumo> listaCobranca;
    private List<Generico> listaBairro;
    private List<Generico> listaRegiao;
    private List<Municipio> listaMunicipio;

    private List<Generico> listaErro;


    //Implementando campanha
    private List<CampanhaComClientes> listaCampanhaComClientes = new ArrayList<>();
    private List<CampanhaComercialCab> listaCampanhaComercialCab = new ArrayList<>();
    private List<CampanhaComercialItens> listaCampanhaComercialItens = new ArrayList<>();
    private List<ProdutoLinhaColecao> listaProdutoLinhaColecao = new ArrayList<>();

    //Implementado SKU
    private List<ProdutoSku> listaProdutoSku;
    private List<ProdutoParametroSku> listaProdutoParametroSku;

    //Implementando verbas/bonus
    private List<VendedorBonusResumo> listaVendedorBonus;
    private List<WebPedido> listaPedidosBonus = new ArrayList<>();
    //Implementando Cobranças/
    private List<ReceberCab> listaRecebimentos = new ArrayList<>();
    private List<CobrancaStatus> listaCobrancaStatus = new ArrayList<>();

    private int dias_hist_venda;
    private int dias_hist_financeiro;
    private int dias_hist_pedido;

    private List<ProdutoFoto> listaProdutoFoto1;
    private List<ProdutoFoto> listaProdutoFoto2;
    private List<ProdutoFoto> listaProdutoFoto3;
    private List<ProdutoFoto> listaProdutoFoto4;
    private List<ProdutoFoto> listaProdutoFoto5;
    private List<ProdutoFoto> listaProdutoFoto6;

    private List<WebPedidoAnalise> listaParaAnalise;

    private List<WebVisita> listaVisita = new ArrayList<>();

    public Sincronia(boolean cliente,
                     boolean produto,
                     boolean pedidosFinalizados,
                     boolean pedidosPendentes,
                     boolean prospect,
                     boolean prospectEnviados,
                     boolean visitasPendentes,
                     boolean produtoFoto,
                     boolean cobranca) {
        this.cliente = cliente;
        this.produto = produto;
        this.pedidosFinalizados = pedidosFinalizados;
        this.pedidosPendentes = pedidosPendentes;
        this.prospectPendentes = prospect;
        this.prospectEnviados = prospectEnviados;
        this.visitasPendentes = visitasPendentes;
        this.produtoFoto = produtoFoto;
        this.cobranca = cobranca;
    }

    public Sincronia(boolean pedidosPendentes,
                     boolean produto) {
        this.pedidosPendentes = pedidosPendentes;
        this.produto = produto;

    }

    public Sincronia ( boolean cobranca) {
        this.cobranca = cobranca;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isProduto() {
        return produto;
    }

    public void setProduto(boolean produto) {
        this.produto = produto;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }



    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }



    public List<CondicoesPagamento> getListaCondicoesPagamento() {
        return listaCondicoesPagamento;
    }

    public void setListaCondicoesPagamento(List<CondicoesPagamento> listaCondicoesPagamento) {
        this.listaCondicoesPagamento = listaCondicoesPagamento;
    }



    public List<Operacao> getListaOperacao() {
        return listaOperacao;
    }

    public void setListaOperacao(List<Operacao> listaOperacao) {
        this.listaOperacao = listaOperacao;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public List<TabelaPrecoCab> getListaTabelaPrecoCab() {
        return listaTabelaPrecoCab;
    }

    public void setListaTabelaPreco(List<TabelaPrecoCab> listaTabelaPrecoCab) {
        this.listaTabelaPrecoCab = listaTabelaPrecoCab;
    }

    public List<TabelaPrecoItem> getListaTabelaPrecoItem() {
        return listaTabelaPrecoItem;
    }

    public void setListaTabelaPrecoItem(List<TabelaPrecoItem> listaTabelaPrecoItem) {
        this.listaTabelaPrecoItem = listaTabelaPrecoItem;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public List<WebPedido> getListaWebPedidosPendentes() {
        return listaWebPedidosPendentes;
    }

    public void setListaWebPedidosPendentes(List<WebPedido> listaWebPedidosPendentes) {
        this.listaWebPedidosPendentes = listaWebPedidosPendentes;
    }

    public boolean isPedidosFinalizados() {
        return pedidosFinalizados;
    }

    public void setPedidosFinalizados(boolean pedidosFinalizados) {
        this.pedidosFinalizados = pedidosFinalizados;
    }

    public boolean isPedidosPendentes() {
        return pedidosPendentes;
    }

    public void setPedidosPendentes(boolean pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }

    public List<WebPedido> getListaWebPedidosFinalizados() {
        return listaWebPedidosFinalizados;
    }

    public void setListaWebPedidosFinalizados(List<WebPedido> listaWebPedidosFinalizados) {
        this.listaWebPedidosFinalizados = listaWebPedidosFinalizados;
    }

    public List<Pais> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<Pais> listaPais) {
        this.listaPais = listaPais;
    }

    public List<MotivoNaoCadastramento> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoNaoCadastramento> motivos) {
        this.motivos = motivos;
    }

    public boolean isProspectPendentes() {
        return prospectPendentes;
    }

    public void setProspectPendentes(boolean prospectPendentes) {
        this.prospectPendentes = prospectPendentes;
    }

    public List<Prospect> getListaProspectPendentes() {
        return listaProspectPendentes;
    }

    public void setListaProspectPendentes(List<Prospect> listaProspectPendentes) {
        this.listaProspectPendentes = listaProspectPendentes;
    }

    public List<Prospect> getListaProspectEnviados() {
        return listaProspectEnviados;
    }

    public void setListaProspectEnviados(List<Prospect> listaProspectEnviados) {
        this.listaProspectEnviados = listaProspectEnviados;
    }

    public boolean isProspectEnviados() {
        return prospectEnviados;
    }

    public void setProspectEnviados(boolean prospectEnviados) {
        this.prospectEnviados = prospectEnviados;
    }

    public boolean isVisitasPendentes() {
        return visitasPendentes;
    }

    public void setVisitasPendentes(boolean visitasPendentes) {
        this.visitasPendentes = visitasPendentes;
    }

    public List<VisitaProspect> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<VisitaProspect> visitas) {
        this.visitas = visitas;
    }

    public List<Categoria> getListaCategoria() {
        return listaCategoria;
    }

    public void setListaCategoria(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    public List<Promocao> getListaPromocao() {
        return listaPromocao;
    }

    public void setListaPromocao(List<Promocao> listaPromocao) {
        this.listaPromocao = listaPromocao;
    }

    public List<Prospect> getListaProspectPositivados() {
        return listaProspectPositivados;
    }

    public void setListaProspectPositivados(List<Prospect> listaProspectPositivados) {
        this.listaProspectPositivados = listaProspectPositivados;
    }

    public List<CadastroFinanceiroResumo> getListaCadastroFinanceiroResumo() {
        return listaCadastroFinanceiroResumo;
    }

    public void setListaCadastroFinanceiroResumo(List<CadastroFinanceiroResumo> listaCadastroFinanceiroResumo) {
        this.listaCadastroFinanceiroResumo = listaCadastroFinanceiroResumo;
    }


    public boolean isProdutoFoto() {
        return produtoFoto;
    }

    public void setProdutoFoto(boolean produtofoto) {
        this.produtoFoto = produtoFoto;
    }

    public boolean isCobranca() {
        return cobranca;
    }

    public void setCobranca(boolean cobranca) {
        this.cobranca = cobranca;
    }

    public List<ProdutoFoto> getListaProdutoFoto() {
        return listaProdutoFoto;
    }

    public void setListaProdutoFoto(List<ProdutoFoto> listaProdutoFoto) {
        this.listaProdutoFoto = listaProdutoFoto;
    }

    public int getDias_hist_venda() {
        return dias_hist_venda;
    }

    public void setDias_hist_venda(int dias_hist_venda) {
        this.dias_hist_venda = dias_hist_venda;
    }

    public int getDias_hist_financeiro() {
        return dias_hist_financeiro;
    }

    public void setDias_hist_financeiro(int dias_hist_financeiro) {
        this.dias_hist_financeiro = dias_hist_financeiro;
    }

    public List<ProdutoSubGrupo> getListaProdutoSubGrupo() {
        return listaProdutoSubGrupo;
    }

    public void setListaProdutoSubGrupo(List<ProdutoSubGrupo> listaProdutoSubGrupo) {
        this.listaProdutoSubGrupo = listaProdutoSubGrupo;
    }

    public List<ProdutoGrupo> getListaProdutoGrupo() {
        return listaProdutoGrupo;
    }

    public void setListaProdutoGrupo(List<ProdutoGrupo> listaProdutoGrupo) {
        this.listaProdutoGrupo = listaProdutoGrupo;
    }

    public List<Moeda> getListaMoeda() {
        return listaMoeda;
    }

    public void setListaMoeda(List<Moeda> listaMoeda) {
        this.listaMoeda = listaMoeda;
    }

    public List<EmpresaParametro> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setListaEmpresa(List<EmpresaParametro> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }

    //Implementando campanhas
    public List<CampanhaComClientes> getListaCampanhaComClientes() {
        return listaCampanhaComClientes;
    }

    public void setListaCampanhaComClientes(List<CampanhaComClientes> listaCampanhaComClientes) {
        this.listaCampanhaComClientes = listaCampanhaComClientes;
    }

    public List<CampanhaComercialCab> getListaCampanhaComercialCab() {
        return listaCampanhaComercialCab;
    }

    public void setListaCampanhaComercialCab(List<CampanhaComercialCab> listaCampanhaComercialCab) {
        this.listaCampanhaComercialCab = listaCampanhaComercialCab;
    }

    public List<CampanhaComercialItens> getListaCampanhaComercialItens() {
        return listaCampanhaComercialItens;
    }

    public void setListaCampanhaComercialItens(List<CampanhaComercialItens> listaCampanhaComercialItens) {
        this.listaCampanhaComercialItens = listaCampanhaComercialItens;
    }

    public List<ProdutoLinhaColecao> getListaProdutoLinhaColecao() {
        return listaProdutoLinhaColecao;
    }

    public void setListaProdutoLinhaColecao(List<ProdutoLinhaColecao> listaProdutoLinhaColecao) {
        this.listaProdutoLinhaColecao = listaProdutoLinhaColecao;
    }



    public int getDias_hist_pedido() {
        return dias_hist_pedido;
    }

    public void setDias_hist_pedido(int dias_hist_pedido) {
        this.dias_hist_pedido = dias_hist_pedido;
    }

    public List<CadastroCondicoesPagamento> getListaCadastroCondicoesPagamento() {
        return listaCadastroCondicoesPagamento;
    }

    public void setListaCadastroCondicoesPagamento(List<CadastroCondicoesPagamento> listaCadastroCondicoesPagamento) {
        this.listaCadastroCondicoesPagamento = listaCadastroCondicoesPagamento;
    }

    public List<Segmento> getListaSegmento() {
        return listaSegmento;
    }

    public void setListaSegmento(List<Segmento> listaSegmento) {
        this.listaSegmento = listaSegmento;
    }

    public List<ProdutoSku> getListaProdutoSku() {
        return listaProdutoSku;
    }

    public void setListaProdutoSku(List<ProdutoSku> listaProdutoSku) {
        this.listaProdutoSku = listaProdutoSku;
    }

    public List<ProdutoParametroSku> getListaProdutoParametroSku() {
        return listaProdutoParametroSku;
    }

    public void setListaProdutoParametroSku(List<ProdutoParametroSku> listaProdutoParametroSku) {
        this.listaProdutoParametroSku = listaProdutoParametroSku;
    }

    public List<Deposito> getListaDeposito() {
        return listaDeposito;
    }

    public void setListaDeposito(List<Deposito> listaDeposito) {
        this.listaDeposito = listaDeposito;
    }

    public List<Generico> getListaContaBanco() {
        return listaContaBanco;
    }

    public void setListaContaBanco(List<Generico> listaContaBanco) {
        this.listaContaBanco = listaContaBanco;
    }

    public List<ClienteResumo> getListaClienteResumo() {
        return listaClienteResumo;
    }

    public void setListaClienteResumo(List<ClienteResumo> listaClienteResumo) {
        this.listaClienteResumo = listaClienteResumo;
    }

    public List<FinanceiroResumo> getListaCobranca() {
        return listaCobranca;
    }

    public void setListaCobranca(List<FinanceiroResumo> listaCobranca) {
        this.listaCobranca = listaCobranca;
    }

    public List<Generico> getListaBairro() {
        return listaBairro;
    }

    public void setListaBairro(List<Generico> listaBairro) {
        this.listaBairro = listaBairro;
    }

    public List<Generico> getListaRegiao() {
        return listaRegiao;
    }

    public void setListaRegiao(List<Generico> listaRegiao) {
        this.listaRegiao = listaRegiao;
    }

    public List<Municipio> getListaMunicipio() {
        return listaMunicipio;
    }

    public void setListaMunicipio(List<Municipio> listaMunicipio) {
        this.listaMunicipio = listaMunicipio;
    }

    public List<ProdutoFoto> getListaProdutoFoto1() {
        return listaProdutoFoto1;
    }

    public void setListaProdutoFoto1(List<ProdutoFoto> listaProdutoFoto1) {
        this.listaProdutoFoto1 = listaProdutoFoto1;
    }

    public List<ProdutoFoto> getListaProdutoFoto2() {
        return listaProdutoFoto2;
    }

    public void setListaProdutoFoto2(List<ProdutoFoto> listaProdutoFoto2) {
        this.listaProdutoFoto2 = listaProdutoFoto2;
    }

    public List<ProdutoFoto> getListaProdutoFoto3() {
        return listaProdutoFoto3;
    }

    public void setListaProdutoFoto3(List<ProdutoFoto> listaProdutoFoto3) {
        this.listaProdutoFoto3 = listaProdutoFoto3;
    }

    public List<ProdutoFoto> getListaProdutoFoto4() {
        return listaProdutoFoto4;
    }

    public void setListaProdutoFoto4(List<ProdutoFoto> listaProdutoFoto4) {
        this.listaProdutoFoto4 = listaProdutoFoto4;
    }

    public List<ProdutoFoto> getListaProdutoFoto5() {
        return listaProdutoFoto5;
    }

    public void setListaProdutoFoto5(List<ProdutoFoto> listaProdutoFoto5) {
        this.listaProdutoFoto5 = listaProdutoFoto5;
    }

    public List<ProdutoFoto> getListaProdutoFoto6() {
        return listaProdutoFoto6;
    }

    public void setListaProdutoFoto6(List<ProdutoFoto> listaProdutoFoto6) {
        this.listaProdutoFoto6 = listaProdutoFoto6;
    }

    public List<VendedorBonusResumo> getListaVendedorBonus() {
        return listaVendedorBonus;
    }

    public void setListaVendedorBonus(List<VendedorBonusResumo> listaVendedorBonus) {
        this.listaVendedorBonus = listaVendedorBonus;
    }

    public List<ReceberCab> getListaRecebimentos() {
        return listaRecebimentos;
    }

    public void setListaRecebimentos(List<ReceberCab> listaRecebimentos) {
        this.listaRecebimentos = listaRecebimentos;
    }

    public List<CobrancaStatus> getListaCobrancaStatus() {
        return listaCobrancaStatus;
    }

    public void setListaCobrancaStatus(List<CobrancaStatus> listaCobrancaStatus) {
        this.listaCobrancaStatus = listaCobrancaStatus;
    }

    public List<WebPedidoAnalise> getListaParaAnalise() {
        return listaParaAnalise;
    }

    public void setListaParaAnalise(List<WebPedidoAnalise> listaParaAnalise) {
        this.listaParaAnalise = listaParaAnalise;
    }

    public List<WebVisita> getListaVisita() {
        return listaVisita;
    }

    public void setListaVisita(List<WebVisita> listaVisita) {
        this.listaVisita = listaVisita;
    }
}
