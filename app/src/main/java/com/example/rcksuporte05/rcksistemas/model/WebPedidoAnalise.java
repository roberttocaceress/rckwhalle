package com.example.rcksuporte05.rcksistemas.model;

public class WebPedidoAnalise {
    private String id_pedido_app;
    private String id_pedido;
    private String id_pedido_venda;
    private String data_importacao;


    private String evento_descricao;
    private String analise_credito;
    private String analise_usuario_id;
    private String analise_usuario_nome;
    private String analise_usuario_data;



    private String id_ordem_carregamento;
    private String data_ordem_carregamento;
    private String id_transportadora;
    private String nome_transportadora;

    private String numero_nota_fiscal;
    private String data_nota_fiscal;
    private String id_nota_fiscal;


    private String especie;
    private String volumes;

    private String valor_produto;
    private String valor_frete;
    private String valor_total;

    private String expedicao_status;
    private String boleto_emitido;

    private String faturado;
    private String tipo_faturamento;
    private String entregue;

    private String excluido;
    private String excluido_usuario_id;
    private String excluido_usuario_nome;
    private String excluido_usuario_data;
    private String justificativa_exclusao;

    public WebPedidoAnalise() {
    }


    public String getId_pedido_app() {
        return id_pedido_app;
    }

    public void setId_pedido_app(String id_pedido_app) {
        this.id_pedido_app = id_pedido_app;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getData_importacao() {
        return data_importacao;
    }

    public void setData_importacao(String data_importacao) {
        this.data_importacao = data_importacao;
    }

    public String getEvento_descricao() {
        return evento_descricao;
    }

    public void setEvento_descricao(String evento_descricao) {
        this.evento_descricao = evento_descricao;
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



    public String getId_ordem_carregamento() {
        return id_ordem_carregamento;
    }

    public void setId_ordem_carregamento(String id_ordem_carregamento) {
        this.id_ordem_carregamento = id_ordem_carregamento;
    }

    public String getData_ordem_carregamento() {
        return data_ordem_carregamento;
    }

    public void setData_ordem_carregamento(String data_ordem_carregamento) {
        this.data_ordem_carregamento = data_ordem_carregamento;
    }

    public String getId_transportadora() {
        return id_transportadora;
    }

    public void setId_transportadora(String id_transportadora) {
        this.id_transportadora = id_transportadora;
    }

    public String getNome_transportadora() {
        return nome_transportadora;
    }

    public void setNome_transportadora(String nome_transportadora) {
        this.nome_transportadora = nome_transportadora;
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

    public String getValor_produto() {
        return valor_produto;
    }

    public void setValor_produto(String valor_produto) {
        this.valor_produto = valor_produto;
    }

    public String getValor_frete() {
        return valor_frete;
    }

    public void setValor_frete(String valor_frete) {
        this.valor_frete = valor_frete;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getExpedicao_status() {
        return expedicao_status;
    }

    public void setExpedicao_status(String expedicao_status) {
        this.expedicao_status = expedicao_status;
    }

    public String getBoleto_emitido() {
        return boleto_emitido;
    }

    public void setBoleto_emitido(String boleto_emitido) {
        this.boleto_emitido = boleto_emitido;
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

    public String getId_nota_fiscal() {
        return id_nota_fiscal;
    }

    public void setId_nota_fiscal(String id_nota_fiscal) {
        this.id_nota_fiscal = id_nota_fiscal;
    }

    public String getFaturado() {
        return faturado;
    }

    public void setFaturado(String faturado) {
        this.faturado = faturado;
    }

    public String getTipo_faturamento() {
        return tipo_faturamento;
    }

    public void setTipo_faturamento(String tipo_faturamento) {
        this.tipo_faturamento = tipo_faturamento;
    }

    public String getEntregue() {
        return entregue;
    }

    public void setEntregue(String entregue) {
        this.entregue = entregue;
    }

    public String getId_pedido_venda() {
        return id_pedido_venda;
    }

    public void setId_pedido_venda(String id_pedido_venda) {
        this.id_pedido_venda = id_pedido_venda;
    }
}
