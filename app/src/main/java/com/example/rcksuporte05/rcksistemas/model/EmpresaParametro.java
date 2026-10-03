package com.example.rcksuporte05.rcksistemas.model;

import android.database.SQLException;

public class EmpresaParametro {
    private int id_empresa;
    private String razao_social;
    private String nome_fantasia;
    private String empresa_logo;
    private String empresa_logo_mini;
    private String empresa_app_cor;
    private String utiliza_tabela_preco;
    private String tipo_tabela_preco;
    private String campanha_no_pedido_venda;
    private String dscto_valor_no_pedido_venda;
    private String tempo_auto_sincronia;
    private String estoque_auto_sincronia;
    private String utiliza_limite_credito;
    private String endereco_uf;
    private String id_pais;
    private String pre_pedido;
    private String ip_url;
    private int porta;
    private String conexao_segura;
    private String versaoAPI;
    private String utiliza_confirmacao_prazo;
    private String utiliza_unidade_manut_estoque;
    private String utiliza_paginacao_dados;
    private String prod_descricao_preco_um;
    private String layout_venda_erp;
    private String id_con_pgto_default_app;
    private String utiliza_cobranca_offline;
    private String id_cidade;
    private String utiliza_cobranca_app;
    private String utiliza_icms_app;
    private String utiliza_desct_auto_app;
    private String sincronia_automatica_app;
    private String utiliza_verba_pedido_app;
    private String cadastra_cliente_app;
    private String visual_hist_fin_pedido_app;
    private String visual_prazo_pgto_pedido_app;
    private String visual_estoque_produto_app;
    private String qtde_maior_saldo_ped_app;
    private String ordem_lista_cliente_app;
    private String atual_lista_ped_exportado;
    private String confirma_sincronia_dados_app;
    private String confirma_sincronia_checkin_app;

    private String cnpj_cpf;
    private String marca_produto_ja_comprado_app;
    private String pedido_enviado_on_line_app;
    private String visualiza_grafico_on_line_app;
    private String visualiza_ultimo_preco_venda_app;
    private String calcula_dscto_automatico_muda_prazo;

    public EmpresaParametro() {
    }

    public EmpresaParametro(String razao_social, String ip_url, int porta, String conexao_segura, String versaoAPI) {
        this.razao_social = razao_social;
        this.ip_url = ip_url;
        this.porta = porta;
        this.conexao_segura = conexao_segura;
        this.versaoAPI = versaoAPI;
    }

    //TEMPO_AUTO_SINCRONIA_APP  VARCHAR 4
    //TBL_EMPRESA_PARAMETROS
    //CAMPO A SER CRIADO NA BASE DE DADOS NO SERVIDOR

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getEmpresa_logo() {
        return empresa_logo;
    }

    public void setEmpresa_logo(String empresa_logo) {
        this.empresa_logo = empresa_logo;
    }

    public String getEmpresa_logo_mini() {
        return empresa_logo_mini;
    }

    public void setEmpresa_logo_mini(String empresa_logo_mini) {
        this.empresa_logo_mini = empresa_logo_mini;
    }

    public String getEmpresa_app_cor() {
        return empresa_app_cor;
    }

    public void setEmpresa_app_cor(String empresa_app_cor) {
        this.empresa_app_cor = empresa_app_cor;
    }

    public String getUtiliza_tabela_preco() {
        return utiliza_tabela_preco;
    }

    public void setUtiliza_tabela_preco(String utiliza_tabela_preco) {
        this.utiliza_tabela_preco = utiliza_tabela_preco;
    }

    public String getTipo_tabela_preco() {
        return tipo_tabela_preco;
    }

    public void setTipo_tabela_preco(String tipo_tabela_preco) {
        this.tipo_tabela_preco = tipo_tabela_preco;
    }

    public String getCampanha_no_pedido_venda() {
        return campanha_no_pedido_venda;
    }

    public void setCampanha_no_pedido_venda(String campanha_no_pedido_venda) {
        this.campanha_no_pedido_venda = campanha_no_pedido_venda;
    }

    public String getDscto_valor_no_pedido_venda() {
        return dscto_valor_no_pedido_venda;
    }

    public void setDscto_valor_no_pedido_venda(String dscto_valor_no_pedido_venda) {
        this.dscto_valor_no_pedido_venda = dscto_valor_no_pedido_venda;
    }

    public String getTempo_auto_sincronia() {
        return tempo_auto_sincronia;
    }

    public void setTempo_auto_sincronia(String tempo_auto_sincronia) {
        this.tempo_auto_sincronia = tempo_auto_sincronia;
    }

    public String getEstoque_auto_sincronia() {
        return estoque_auto_sincronia;
    }

    public void setEstoque_auto_sincronia(String estoque_auto_sincronia) {
        this.estoque_auto_sincronia = estoque_auto_sincronia;
    }

    public String getUtiliza_limite_credito() {
        return utiliza_limite_credito;
    }

    public void setUtiliza_limite_credito(String utiliza_limite_credito) {
        this.utiliza_limite_credito = utiliza_limite_credito;
    }

    public String getEndereco_uf() {
        return endereco_uf;
    }

    public void setEndereco_uf(String endereco_uf) {
        this.endereco_uf = endereco_uf;
    }

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public String getPre_pedido() {
        return pre_pedido;
    }

    public void setPre_pedido(String pre_pedido) {
        this.pre_pedido = pre_pedido;
    }

    public String getIp_url() {
        return ip_url;
    }

    public void setIp_url(String ip_url) {
        this.ip_url = ip_url;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getConexao_segura() {
        return conexao_segura;
    }

    public void setConexao_segura(String conexao_segura) {
        this.conexao_segura = conexao_segura;
    }

    public String getVersaoAPI() {
        return versaoAPI;
    }

    public void setVersaoAPI(String versaoAPI) {
        this.versaoAPI = versaoAPI;
    }

    public String getUtiliza_confirmacao_prazo() {
        return utiliza_confirmacao_prazo;
    }

    public void setUtiliza_confirmacao_prazo(String utiliza_confirmacao_prazo) {
        this.utiliza_confirmacao_prazo = utiliza_confirmacao_prazo;
    }

    public String getUtiliza_unidade_manut_estoque() {
        return utiliza_unidade_manut_estoque;
    }

    public void setUtiliza_unidade_manut_estoque(String utiliza_unidade_manut_estoque) {
        this.utiliza_unidade_manut_estoque = utiliza_unidade_manut_estoque;
    }

    public String getUtiliza_paginacao_dados() {
        return utiliza_paginacao_dados;
    }

    public void setUtiliza_paginacao_dados(String utiliza_paginacao_dados) {
        this.utiliza_paginacao_dados = utiliza_paginacao_dados;
    }

    public String getProd_descricao_preco_um() {
        return prod_descricao_preco_um;
    }

    public void setProd_descricao_preco_um(String prod_descricao_preco_um) {
        this.prod_descricao_preco_um = prod_descricao_preco_um;
    }

    public String getLayout_venda_erp() {
        return layout_venda_erp;
    }

    public void setLayout_venda_erp(String layout_venda_erp) {
        this.layout_venda_erp = layout_venda_erp;
    }

    public String getId_con_pgto_default_app() {
        return id_con_pgto_default_app;
    }

    public void setId_con_pgto_default_app(String id_con_pgto_default_app) {
        this.id_con_pgto_default_app = id_con_pgto_default_app;
    }

    public String getUtiliza_cobranca_offline() {
        return utiliza_cobranca_offline;
    }

    public void setUtiliza_cobranca_offline(String utiliza_cobranca_offline) {
        this.utiliza_cobranca_offline = utiliza_cobranca_offline;
    }

    public String getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(String id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getUtiliza_cobranca_app() {
        return utiliza_cobranca_app;
    }

    public void setUtiliza_cobranca_app(String utiliza_cobranca_app) {
        this.utiliza_cobranca_app = utiliza_cobranca_app;
    }

    public String getUtiliza_icms_app() {
        return utiliza_icms_app;
    }

    public void setUtiliza_icms_app(String utiliza_icms_app) {
        this.utiliza_icms_app = utiliza_icms_app;
    }

    public String getUtiliza_desct_auto_app() {
        return utiliza_desct_auto_app;
    }

    public void setUtiliza_desct_auto_app(String utiliza_desct_auto_app) {
        this.utiliza_desct_auto_app = utiliza_desct_auto_app;
    }

    public String getSincronia_automatica_app() {
        return sincronia_automatica_app;
    }

    public void setSincronia_automatica_app(String sincronia_automatica_app) {
        this.sincronia_automatica_app = sincronia_automatica_app;
    }

    public String getUtiliza_verba_pedido_app() {
        return utiliza_verba_pedido_app;
    }

    public void setUtiliza_verba_pedido_app(String utiliza_verba_pedido_app) {
        this.utiliza_verba_pedido_app = utiliza_verba_pedido_app;
    }

    public String getCadastra_cliente_app() {
        return cadastra_cliente_app;
    }

    public void setCadastra_cliente_app(String cadastra_cliente_app) {
        this.cadastra_cliente_app = cadastra_cliente_app;
    }

    public String getVisual_hist_fin_pedido_app() {
        return visual_hist_fin_pedido_app;
    }

    public void setVisual_hist_fin_pedido_app(String visual_hist_fin_pedido_app) {
        this.visual_hist_fin_pedido_app = visual_hist_fin_pedido_app;
    }

    public String getVisual_prazo_pgto_pedido_app() {
        return visual_prazo_pgto_pedido_app;
    }

    public void setVisual_prazo_pgto_pedido_app(String visual_prazo_pgto_pedido_app) {
        this.visual_prazo_pgto_pedido_app = visual_prazo_pgto_pedido_app;
    }

    public String getVisual_estoque_produto_app() {
        return visual_estoque_produto_app;
    }

    public void setVisual_estoque_produto_app(String visual_estoque_produto_app) {
        this.visual_estoque_produto_app = visual_estoque_produto_app;
    }

    public String getQtde_maior_saldo_ped_app() {
        return qtde_maior_saldo_ped_app;
    }

    public void setQtde_maior_saldo_ped_app(String qtde_maior_saldo_ped_app) {
        this.qtde_maior_saldo_ped_app = qtde_maior_saldo_ped_app;
    }

    public String getOrdem_lista_cliente_app() {
        return ordem_lista_cliente_app;
    }

    public void setOrdem_lista_cliente_app(String ordem_lista_cliente_app) {
        this.ordem_lista_cliente_app = ordem_lista_cliente_app;
    }

    public String getAtual_lista_ped_exportado() {
        return atual_lista_ped_exportado;
    }

    public void setAtual_lista_ped_exportado(String atual_lista_ped_exportado) {
        this.atual_lista_ped_exportado = atual_lista_ped_exportado;
    }

    public String getConfirma_sincronia_dados_app() {
        return confirma_sincronia_dados_app;
    }

    public void setConfirma_sincronia_dados_app(String confirma_sincronia_dados_app) {
        this.confirma_sincronia_dados_app = confirma_sincronia_dados_app;
    }

    public String getConfirma_sincronia_checkin_app() {
        return confirma_sincronia_checkin_app;
    }

    public void setConfirma_sincronia_checkin_app(String confirma_sincronia_checkin_app) {
        this.confirma_sincronia_checkin_app = confirma_sincronia_checkin_app;
    }

    public String getCnpj_cpf() {
        return cnpj_cpf;
    }

    public void setCnpj_cpf(String cnpj) {
        this.cnpj_cpf = cnpj;
    }

    public String getMarca_produto_ja_comprado_app() {
        return marca_produto_ja_comprado_app;
    }

    public void setMarca_produto_ja_comprado_app(String marca_produto_ja_comprado_app) {
        this.marca_produto_ja_comprado_app = marca_produto_ja_comprado_app;
    }

    public String getPedido_enviado_on_line_app() {
        return pedido_enviado_on_line_app;
    }

    public void setPedido_enviado_on_line_app(String pedido_enviado_on_line_app) {
        this.pedido_enviado_on_line_app = pedido_enviado_on_line_app;
    }

    public String getVisualiza_grafico_on_line_app() {
        return visualiza_grafico_on_line_app;
    }

    public void setVisualiza_grafico_on_line_app(String visualiza_grafico_on_line_app) {
        this.visualiza_grafico_on_line_app = visualiza_grafico_on_line_app;
    }

    public String getVisualiza_ultimo_preco_venda_app() {
        return visualiza_ultimo_preco_venda_app;
    }

    public void setVisualiza_ultimo_preco_venda_app(String visualiza_ultimo_preco_venda_app) {
        this.visualiza_ultimo_preco_venda_app = visualiza_ultimo_preco_venda_app;
    }

    public String getCalcula_dscto_automatico_muda_prazo() {
        return calcula_dscto_automatico_muda_prazo;
    }

    public void setCalcula_dscto_automatico_muda_prazo(String calcula_dscto_automatico_muda_prazo) {
        this.calcula_dscto_automatico_muda_prazo = calcula_dscto_automatico_muda_prazo;
    }
}
