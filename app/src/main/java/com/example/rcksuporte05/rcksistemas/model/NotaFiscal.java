package com.example.rcksuporte05.rcksistemas.model;

public class NotaFiscal {
    private int id_nota_fiscal;
    private int id_empresa;
    private int id_emissor;
    private String origem;
    private String numero_nota;

    private String vr_contabil;
    private String vr_produtos;
    private String valor_financeiro;

    private int id_operacao;
    private String nome_operacao;
    private String e_entrada_s_saida;
    private int finalidade_emissao;
    private String data_emissao;

    private int id_pedido;
    private int id_entidade;

    private int transp_id;
    private String transp_nome;

    private String mov_estoque;
    private String mov_financeiro;
    private String nf_cancelada;

    private int id_deposito;
    private int id_vendedor;

    private String comissao_perc;
    private String comissao_valor;
    private int total_docs;

    private int id_dest_rem;
    private String dest_rem_nome;
    //ARQUIVO_XML                 BLOB SUB_TYPE 0 SEGMENT SIZE 80,


    public NotaFiscal() {
    }

    public int getId_nota_fiscal() {
        return id_nota_fiscal;
    }

    public void setId_nota_fiscal(int id_nota_fiscal) {
        this.id_nota_fiscal = id_nota_fiscal;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_emissor() {
        return id_emissor;
    }

    public void setId_emissor(int id_emissor) {
        this.id_emissor = id_emissor;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getNumero_nota() {
        return numero_nota;
    }

    public void setNumero_nota(String numero_nota) {
        this.numero_nota = numero_nota;
    }

    public String getVr_contabil() {
        return vr_contabil;
    }

    public void setVr_contabil(String vr_contabil) {
        this.vr_contabil = vr_contabil;
    }

    public String getVr_produtos() {
        return vr_produtos;
    }

    public void setVr_produtos(String vr_produtos) {
        this.vr_produtos = vr_produtos;
    }

    public String getValor_financeiro() {
        return valor_financeiro;
    }

    public void setValor_financeiro(String valor_financeiro) {
        this.valor_financeiro = valor_financeiro;
    }

    public int getId_operacao() {
        return id_operacao;
    }

    public void setId_operacao(int id_operacao) {
        this.id_operacao = id_operacao;
    }

    public String getNome_operacao() {
        return nome_operacao;
    }

    public void setNome_operacao(String nome_operacao) {
        this.nome_operacao = nome_operacao;
    }

    public String getE_entrada_s_saida() {
        return e_entrada_s_saida;
    }

    public void setE_entrada_s_saida(String e_entrada_s_saida) {
        this.e_entrada_s_saida = e_entrada_s_saida;
    }

    public int getFinalidade_emissao() {
        return finalidade_emissao;
    }

    public void setFinalidade_emissao(int finalidade_emissao) {
        this.finalidade_emissao = finalidade_emissao;
    }

    public String getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(String data_emissao) {
        this.data_emissao = data_emissao;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_entidade() {
        return id_entidade;
    }

    public void setId_entidade(int id_entidade) {
        this.id_entidade = id_entidade;
    }

    public int getTransp_id() {
        return transp_id;
    }

    public void setTransp_id(int transp_id) {
        this.transp_id = transp_id;
    }

    public String getTransp_nome() {
        return transp_nome;
    }

    public void setTransp_nome(String transp_nome) {
        this.transp_nome = transp_nome;
    }

    public String getMov_estoque() {
        return mov_estoque;
    }

    public void setMov_estoque(String mov_estoque) {
        this.mov_estoque = mov_estoque;
    }

    public String getMov_financeiro() {
        return mov_financeiro;
    }

    public void setMov_financeiro(String mov_financeiro) {
        this.mov_financeiro = mov_financeiro;
    }

    public String getNf_cancelada() {
        return nf_cancelada;
    }

    public void setNf_cancelada(String nf_cancelada) {
        this.nf_cancelada = nf_cancelada;
    }

    public int getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(int id_deposito) {
        this.id_deposito = id_deposito;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getComissao_perc() {
        return comissao_perc;
    }

    public void setComissao_perc(String comissao_perc) {
        this.comissao_perc = comissao_perc;
    }

    public String getComissao_valor() {
        return comissao_valor;
    }

    public void setComissao_valor(String comissao_valor) {
        this.comissao_valor = comissao_valor;
    }

    public int getTotal_docs() {
        return total_docs;
    }

    public void setTotal_docs(int total_docs) {
        this.total_docs = total_docs;
    }

    public int getId_dest_rem() {
        return id_dest_rem;
    }

    public void setId_dest_rem(int id_dest_rem) {
        this.id_dest_rem = id_dest_rem;
    }

    public String getDest_rem_nome() {
        return dest_rem_nome;
    }

    public void setDest_rem_nome(String dest_rem_nome) {
        this.dest_rem_nome = dest_rem_nome;
    }
}
