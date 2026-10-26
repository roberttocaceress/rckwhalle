package com.example.rcksuporte05.rcksistemas.model;

import java.util.ArrayList;
import java.util.List;

public class WebPedido {

    private String id_web_pedido;
    private String id_empresa;
    private Cliente cadastro;
    private String id_vendedor;
    private String id_outro_vendedor;
    private String id_condicao_pagamento;
    private String id_operacao;
    private String id_tabela;
    private String nome_extenso;
    private String data_emissao;
    private String valor_produtos;
    private String valor_desconto;
    private String valor_desconto_add;
    private String desconto_per;
    private String desconto_per_add;
    private String valor_total;
    private String excluido;
    private String excluido_usuario_id;
    private String excluido_usuario_nome;
    private String excluido_usuario_data;
    private String justificativa_exclusao;
    private String usuario_lancamento_id;
    private String usuario_lancamento_nome;
    private String usuario_lancamento_data;
    private String observacoes;
    private String status;
    private String id_pedido_venda;
    private String id_nota_fiscal;
    private String id_tabela_preco_faixa;
    private String pontos_total;
    private String pontos_coeficiente;
    private String pontos_cor;
    private String comissao_percentual;
    private String comissao_valor;
    private String id_faixa_final;
    private String valor_bonus_credor;
    private String perc_bonus_credor;
    private String origem;
    private String id_web_pedido_servidor;
    private String data_prev_entrega;
    private List<WebPedidoItens> webPedidoItens;
    private WebVisita webVisita;
    private String pedido_enviado;
    private String finalizado;
    private String id_visita_app;

    private String web_importado_data;
    private String analise_credito;
    private String analise_usuario_id;
    private String analise_usuario_nome;
    private String analise_usuario_data;
    private String ordem_carregamento;
    private String id_ordem_carregamento;
    private String id_transportadora;
    private String nome_transportadora;
    private String numero_nota_fiscal;
    private String data_nota_fiscal;

    private String trabalha_com_campanha;
    private String trabalha_com_dscto_reais;

    private String analise_estoq_efetuada;
    private String analise_estoque_data_hora;

    /*private String id_nota_fiscal;
      private String excluido;
      private String excluido_usuario_id;
      private String EXCLUIDO_USUARIO_NOME;
      private String EXCLUIDO_USUARIO_DATA;
      private String JUSTIFICATIVA_EXCLUSAO;
    */
    private String especie;
    private String volumes;
    private String web_valor_total;
    private String expedicao_status;
    private String id_pedido;
    private String valor_faturado;

    //V 2.5.6
    private String id_moeda_padrao;
    //V 2.5.7
    private String descontoIndevido;
    private String id_pedido_app;

    private String id_pedido_app_fdb; //id do firebase;
    private String nome_empresa;
    private String prazo_confirmado;
    private String valor_recibo;
    private String valor_desconto_adic;
    private String perc_desconto_adic;

    private String valor_icms_st;

    private String cliente_novo;
    private String qtde_itens;
    private String faturado;

    private String id_android;
    private String tipo_envio; // 1 lista de pedidos pendentes envio individual
                               // 2 lista de pedidos pendentes envio seleção
                               // 3 lista de pedidos pendentes envio todos pendentes
                               // 4 todos os pedidos atraves da sincronia de dados
                               private String isOnLine;

    private List<PedidoAnexo> webPedidoAnexos = new ArrayList<>();

    private List<Generico> listaErro;

    public WebPedido() {
    }

    public WebPedido(Cliente cadastro) {
        this.cadastro = cadastro;
    }

    public Cliente getCadastro() {
        return cadastro;
    }

    public void setCadastro(Cliente cadastro) {
        this.cadastro = cadastro;
    }

    public String getId_web_pedido() {
        return id_web_pedido;
    }

    public void setId_web_pedido(String id_web_pedido) {
        this.id_web_pedido = id_web_pedido;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getId_condicao_pagamento() {
        return id_condicao_pagamento;
    }

    public void setId_condicao_pagamento(String id_condicao_pagamento) {
        this.id_condicao_pagamento = id_condicao_pagamento;
    }

    public String getId_operacao() {
        return id_operacao;
    }

    public void setId_operacao(String id_operacao) {
        this.id_operacao = id_operacao;
    }

    public String getId_tabela() {
        return id_tabela;
    }

    public void setId_tabela(String id_tabela) {
        this.id_tabela = id_tabela;
    }

    public String getNome_extenso() {
        return nome_extenso;
    }

    public void setNome_extenso(String nome_extenso) {
        this.nome_extenso = nome_extenso;
    }

    public String getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(String data_emissao) {
        this.data_emissao = data_emissao;
    }

    public String getValor_produtos() {
        return valor_produtos;
    }

    public void setValor_produtos(String valor_produtos) {
        this.valor_produtos = valor_produtos;
    }

    public String getValor_desconto() {
        return valor_desconto;
    }

    public void setValor_desconto(String valor_desconto) {
        this.valor_desconto = valor_desconto;
    }

    public String getValor_desconto_add() {
        return valor_desconto_add;
    }

    public void setValor_desconto_add(String valor_desconto_add) {
        this.valor_desconto_add = valor_desconto_add;
    }

    public String getDesconto_per() {
        return desconto_per;
    }

    public void setDesconto_per(String desconto_per) {
        this.desconto_per = desconto_per;
    }

    public String getDesconto_per_add() {
        return desconto_per_add;
    }

    public void setDesconto_per_add(String desconto_per_add) {
        this.desconto_per_add = desconto_per_add;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    public String getExcluido_usuario_id() {
        return excluido_usuario_id;
    }

    public void setExcluido_usuario_id(String excluido_usuario_id) {
        this.excluido_usuario_id = excluido_usuario_id;
    }

    public String getExcluido_usuario_nome() {
        return excluido_usuario_nome;
    }

    public void setExcluido_usuario_nome(String excluido_usuario_nome) {
        this.excluido_usuario_nome = excluido_usuario_nome;
    }

    public String getExcluido_usuario_data() {
        return excluido_usuario_data;
    }

    public void setExcluido_usuario_data(String excluido_usuario_data) {
        this.excluido_usuario_data = excluido_usuario_data;
    }

    public String getJustificativa_exclusao() {
        return justificativa_exclusao;
    }

    public void setJustificativa_exclusao(String justificativa_exclusao) {
        this.justificativa_exclusao = justificativa_exclusao;
    }

    public String getUsuario_lancamento_id() {
        return usuario_lancamento_id;
    }

    public void setUsuario_lancamento_id(String usuario_lancamento_id) {
        this.usuario_lancamento_id = usuario_lancamento_id;
    }

    public String getUsuario_lancamento_nome() {
        return usuario_lancamento_nome;
    }

    public void setUsuario_lancamento_nome(String usuario_lancamento_nome) {
        this.usuario_lancamento_nome = usuario_lancamento_nome;
    }

    public String getUsuario_lancamento_data() {
        return usuario_lancamento_data;
    }

    public void setUsuario_lancamento_data(String usuario_lancamento_data) {
        this.usuario_lancamento_data = usuario_lancamento_data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_pedido_venda() {
        return id_pedido_venda;
    }

    public void setId_pedido_venda(String id_pedido_venda) {
        this.id_pedido_venda = id_pedido_venda;
    }

    public String getId_nota_fiscal() {
        return id_nota_fiscal;
    }

    public void setId_nota_fiscal(String id_nota_fiscal) {
        this.id_nota_fiscal = id_nota_fiscal;
    }

    public String getId_tabela_preco_faixa() {
        return id_tabela_preco_faixa;
    }

    public void setId_tabela_preco_faixa(String id_tabela_preco_faixa) {
        this.id_tabela_preco_faixa = id_tabela_preco_faixa;
    }

    public String getPontos_total() {
        return pontos_total;
    }

    public void setPontos_total(String pontos_total) {
        this.pontos_total = pontos_total;
    }

    public String getPontos_coeficiente() {
        return pontos_coeficiente;
    }

    public void setPontos_coeficiente(String pontos_coeficiente) {
        this.pontos_coeficiente = pontos_coeficiente;
    }

    public String getPontos_cor() {
        return pontos_cor;
    }

    public void setPontos_cor(String pontos_cor) {
        this.pontos_cor = pontos_cor;
    }

    public String getComissao_percentual() {
        return comissao_percentual;
    }

    public void setComissao_percentual(String comissao_percentual) {
        this.comissao_percentual = comissao_percentual;
    }

    public String getComissao_valor() {
        return comissao_valor;
    }

    public void setComissao_valor(String comissao_valor) {
        this.comissao_valor = comissao_valor;
    }

    public String getId_faixa_final() {
        return id_faixa_final;
    }

    public void setId_faixa_final(String id_faixa_final) {
        this.id_faixa_final = id_faixa_final;
    }

    public String getValor_bonus_credor() {
        return valor_bonus_credor;
    }

    public void setValor_bonus_credor(String valor_bonus_credor) {
        this.valor_bonus_credor = valor_bonus_credor;
    }

    public String getPerc_bonus_credor() {
        return perc_bonus_credor;
    }

    public void setPerc_bonus_credor(String perc_bonus_credor) {
        this.perc_bonus_credor = perc_bonus_credor;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getId_web_pedido_servidor() {
        return id_web_pedido_servidor;
    }

    public void setId_web_pedido_servidor(String id_web_pedido_servidor) {
        this.id_web_pedido_servidor = id_web_pedido_servidor;
    }

    public String getData_prev_entrega() {
        return data_prev_entrega;
    }

    public void setData_prev_entrega(String data_prev_entrega) {
        this.data_prev_entrega = data_prev_entrega;
    }

    public List<WebPedidoItens> getWebPedidoItens() {
        return webPedidoItens;
    }

    public void setWebPedidoItens(List<WebPedidoItens> webPedidoItens) {
        this.webPedidoItens = webPedidoItens;
    }

    public String getPedido_enviado() {
        return pedido_enviado;
    }

    public void setPedido_enviado(String pedido_enviado) {
        this.pedido_enviado = pedido_enviado;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public String getWeb_importado_data() {
        return web_importado_data;
    }

    public void setWeb_importado_data(String web_importado_data) {
        this.web_importado_data = web_importado_data;
    }

    public String getAnalise_credito() {
        return analise_credito;
    }

    public void setAnalise_credito(String analise_credito) {
        this.analise_credito = analise_credito;
    }

    public String getAnalise_usuario_id() {
        return analise_usuario_id;
    }

    public void setAnalise_usuario_id(String analise_usuario_id) {
        this.analise_usuario_id = analise_usuario_id;
    }

    public String getAnalise_usuario_nome() {
        return analise_usuario_nome;
    }

    public void setAnalise_usuario_nome(String analise_usuario_nome) {
        this.analise_usuario_nome = analise_usuario_nome;
    }

    public String getAnalise_usuario_data() {
        return analise_usuario_data;
    }

    public void setAnalise_usuario_data(String analise_usuario_data) {
        this.analise_usuario_data = analise_usuario_data;
    }

    public String getOrdem_carregamento() {
        return ordem_carregamento;
    }

    public void setOrdem_carregamento(String ordem_carregamento) {
        this.ordem_carregamento = ordem_carregamento;
    }

    public String getId_ordem_carregamento() {
        return id_ordem_carregamento;
    }

    public void setId_ordem_carregamento(String id_ordem_carregamento) {
        this.id_ordem_carregamento = id_ordem_carregamento;
    }

    public String getId_transportadora() {
        return id_transportadora;
    }

    public String getNome_transportadora() {
        return nome_transportadora;
    }

    public void setNome_transportadora(String nome_transportadora) {
        this.nome_transportadora = nome_transportadora;
    }

    public void setId_transportadora(String id_transportadora) {
        this.id_transportadora = id_transportadora;
    }

    public String getNumero_nota_fiscal() {
        return numero_nota_fiscal;
    }

    public void setNumero_nota_fiscal(String numero_nota_fiscal) {
        this.numero_nota_fiscal = numero_nota_fiscal;
    }

    public String getData_nota_fiscal() {
        return data_nota_fiscal;
    }

    public void setData_nota_fiscal(String data_nota_fiscal) {
        this.data_nota_fiscal = data_nota_fiscal;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getVolumes() {
        return volumes;
    }

    public void setVolumes(String volumes) {
        this.volumes = volumes;
    }

    public String getWeb_valor_total() {
        return web_valor_total;
    }

    public void setWeb_valor_total(String web_valor_total) {
        this.web_valor_total = web_valor_total;
    }

    public String getExpedicao_status() {
        return expedicao_status;
    }

    public void setExpedicao_status(String expedicao_status) {
        this.expedicao_status = expedicao_status;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getValor_faturado() {
        return valor_faturado;
    }

    public void setValor_faturado(String valor_faturado) {
        this.valor_faturado = valor_faturado;
    }

    public String getId_moeda_padrao() {
        return id_moeda_padrao;
    }

    public void setId_moeda_padrao(String id_moeda_padrao) {
        this.id_moeda_padrao = id_moeda_padrao;
    }

    public String getTrabalha_com_campanha() {
        return trabalha_com_campanha;
    }

    public void setTrabalha_com_campanha(String trabalha_com_campanha) {
        this.trabalha_com_campanha = trabalha_com_campanha;
    }

    public String getTrabalha_com_dscto_reais() {
        return trabalha_com_dscto_reais;
    }

    public void setTrabalha_com_dscto_reais(String trabalha_com_dscto_reais) {
        this.trabalha_com_dscto_reais = trabalha_com_dscto_reais;
    }

    public String getDescontoIndevido() {
        return descontoIndevido;
    }

    public void setDescontoIndevido(String descontoIndevido) {
        this.descontoIndevido = descontoIndevido;
    }

    public String getId_pedido_app() {
        return id_pedido_app;
    }

    public void setId_pedido_app(String id_pedido_app) {
        this.id_pedido_app = id_pedido_app;
    }

    public String getPrazo_confirmado() {
        return prazo_confirmado;
    }

    public void setPrazo_confirmado(String prazo_confirmado) {
        this.prazo_confirmado = prazo_confirmado;
    }

    public String getValor_recibo() {
        return valor_recibo;
    }

    public void setValor_recibo(String valor_recibo) {
        this.valor_recibo = valor_recibo;
    }

    public String getValor_desconto_adic() {
        return valor_desconto_adic;
    }

    public void setValor_desconto_adic(String valor_desconto_adic) {
        this.valor_desconto_adic = valor_desconto_adic;
    }

    public String getPerc_desconto_adic() {
        return perc_desconto_adic;
    }

    public void setPerc_desconto_adic(String perc_desconto_adic) {
        this.perc_desconto_adic = perc_desconto_adic;
    }

    public String getCliente_novo() {
        return cliente_novo;
    }

    public void setCliente_novo(String cliente_novo) {
        this.cliente_novo = cliente_novo;
    }

    public String getValor_icms_st() {
        return valor_icms_st;
    }

    public void setValor_icms_st(String valor_icms_st) {
        this.valor_icms_st = valor_icms_st;
    }

    public String getQtde_itens() {
        return qtde_itens;
    }

    public void setQtde_itens(String qtde_itens) {
        this.qtde_itens = qtde_itens;
    }

    public String getNome_empresa() {
        return nome_empresa;
    }

    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }

    public String getFaturado() {
        return faturado;
    }

    public void setFaturado(String faturado) {
        this.faturado = faturado;
    }

    public String getId_outro_vendedor() {
        return id_outro_vendedor;
    }

    public void setId_outro_vendedor(String id_outro_vendedor) {
        this.id_outro_vendedor = id_outro_vendedor;
    }

    public String getId_android() {
        return id_android;
    }

    public void setId_android(String id_android) {
        this.id_android = id_android;
    }

    public String getTipo_envio() {
        return tipo_envio;
    }

    public void setTipo_envio(String tipo_envio) {
        this.tipo_envio = tipo_envio;
    }

    public String getId_pedido_app_fdb() {
        return id_pedido_app_fdb;
    }

    public void setId_pedido_app_fdb(String id_pedido_app_fdb) {
        this.id_pedido_app_fdb = id_pedido_app_fdb;
    }

    public String getAnalise_estoq_efetuada() {
        return analise_estoq_efetuada;
    }

    public void setAnalise_estoq_efetuada(String analise_estoq_efetuada) {
        this.analise_estoq_efetuada = analise_estoq_efetuada;
    }

    public String getAnalise_estoque_data_hora() {
        return analise_estoque_data_hora;
    }

    public void setAnalise_estoque_data_hora(String analise_estoque_data_hora) {
        this.analise_estoque_data_hora = analise_estoque_data_hora;
    }

    public List<PedidoAnexo> getWebPedidoAnexos() {
        return webPedidoAnexos;
    }

    public void setWebPedidoAnexos(List<PedidoAnexo> webPedidoAnexos) {
        this.webPedidoAnexos = webPedidoAnexos;
    }

    public String getIsOnLine() {
        return isOnLine;
    }

    public void setIsOnLine(String isOnLine) {
        this.isOnLine = isOnLine;
    }

    public List<Generico> getListaErro() {
        return listaErro;
    }

    public void setListaErro(List<Generico> listaErro) {
        this.listaErro = listaErro;
    }

    public String getId_visita_app() {
        return id_visita_app;
    }

    public void setId_visita_app(String id_visita_app) {
        this.id_visita_app = id_visita_app;
    }

    public WebVisita getWebVisita() {
        return webVisita;
    }

    public void setWebVisita(WebVisita webVisita) {
        this.webVisita = webVisita;
    }
}
