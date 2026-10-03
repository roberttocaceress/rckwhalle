package com.example.rcksuporte05.rcksistemas.model;

public class Configuracao {
    private String id;
    private String usuario;
    private String senha;
    private String url_ip_1;
    private int porta_1;
    private String url_ip_desc_1;
    private String url_ip_2;
    private int porta_2;
    private String url_ip_desc_2;
    private String conexao_segura;
    private String trabalha_com_campanhas;
    private String trabalha_com_dscto_reais;
    private String trabalha_com_limite_cred;
    private String trabalha_com_pre_pedido;
    private String pesquisa_produto_por_linha_grupo; //G Grupo L Linha
    private String trabalha_com_confirmacao_prazo;
    private String trabalha_com_unidade_manut_estoque;
    private String trabalha_com_paginacao_dados;
    private String layout_venda_erp;
    private String id_con_pgto_default_app;
    private String trabalha_com_cobranca;
    private String trabalha_com_icms_st;
    private String trabalha_com_desc_auto_app; //Desconto automatico ao incluir item no app
    private String sincronia_automatica_app;
    private String trabalha_com_verba_pedido;
    private String lista_todos_pedidos;
    private String gira_tela_cliente;
    private String gira_tela_produto;
    private String gira_tela_cobranca;
    private String gira_tela_ped_pendente;
    private String gira_tela_ped_enviado;
    private String gira_tela_pedido;
    private String cadastra_cliente_app;
    private String visual_hist_fin_pedido_app;
    private String visual_prazo_pgto_pedido_app;
    private String visual_estoque_produto_app;
    private String time_conn;
    private String time_read;
    private String time_write;
    private String ordem_lista_cliente;
    private String atual_lista_ped_exportado;
    private String confirma_sincronia_dados;

    private String cnpj_empresa;

    private String marca_produto_ja_comprado;
    private String visualiza_legenda_cliente;
    private String visualiza_legenda_produto;
    private String visualiza_legenda_pedido;
    private String visualiza_legenda_sem_uso;
    private String pedido_enviado_on_line_app;
    private String visualiza_grafico_on_line_app;
    private String visualiza_ultimo_preco_venda_app;
    private String calcula_dscto_automatico_muda_prazo;
    //PEDIDO_ENVIADO_ON_LINE_APP



    public Configuracao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUrl_ip_1() {
        return url_ip_1;
    }

    public void setUrl_ip_1(String url_ip_1) {
        this.url_ip_1 = url_ip_1;
    }

    public String getUrl_ip_desc_1() {
        return url_ip_desc_1;
    }

    public void setUrl_ip_desc_1(String url_ip_desc_1) {
        this.url_ip_desc_1 = url_ip_desc_1;
    }

    public String getUrl_ip_2() {
        return url_ip_2;
    }

    public void setUrl_ip_2(String url_ip_2) {
        this.url_ip_2 = url_ip_2;
    }

    public String getUrl_ip_desc_2() {
        return url_ip_desc_2;
    }

    public void setUrl_ip_desc_2(String url_ip_desc_2) {
        this.url_ip_desc_2 = url_ip_desc_2;
    }

    public int getPorta_1() {
        return porta_1;
    }

    public void setPorta_1(int porta_1) {
        this.porta_1 = porta_1;
    }

    public int getPorta_2() {
        return porta_2;
    }

    public void setPorta_2(int porta_2) {
        this.porta_2 = porta_2;
    }

    public String getConexao_segura() {
        return conexao_segura;
    }

    public void setConexao_segura(String conexao_segura) {
        this.conexao_segura = conexao_segura;
    }

    public String getTrabalha_com_campanhas() {
        return trabalha_com_campanhas;
    }

    public void setTrabalha_com_campanhas(String trabalha_com_campanhas) {
        this.trabalha_com_campanhas = trabalha_com_campanhas;
    }

    public String getTrabalha_com_dscto_reais() {
        return trabalha_com_dscto_reais;
    }

    public void setTrabalha_com_dscto_reais(String trabalha_com_dscto_reais) {
        this.trabalha_com_dscto_reais = trabalha_com_dscto_reais;
    }

    public String getTrabalha_com_limite_cred() {
        return trabalha_com_limite_cred;
    }

    public void setTrabalha_com_limite_cred(String trabalha_com_limite_cred) {
        this.trabalha_com_limite_cred = trabalha_com_limite_cred;
    }

    public String getTrabalha_com_pre_pedido() {
        return trabalha_com_pre_pedido;
    }

    public void setTrabalha_com_pre_pedido(String trabalha_com_pre_pedido) {
        this.trabalha_com_pre_pedido = trabalha_com_pre_pedido;
    }

    public String getPesquisa_produto_por_linha_grupo() {
        return pesquisa_produto_por_linha_grupo;
    }

    public void setPesquisa_produto_por_linha_grupo(String pesquisa_produto_por_linha_grupo) {
        this.pesquisa_produto_por_linha_grupo = pesquisa_produto_por_linha_grupo;
    }

    public String getTrabalha_com_confirmacao_prazo() {
        return trabalha_com_confirmacao_prazo;
    }

    public void setTrabalha_com_confirmacao_prazo(String trabalha_com_confirmacao_prazo) {
        this.trabalha_com_confirmacao_prazo = trabalha_com_confirmacao_prazo;
    }

    public String getTrabalha_com_unidade_manut_estoque() {
        return trabalha_com_unidade_manut_estoque;
    }

    public void setTrabalha_com_unidade_manut_estoque(String trabalha_com_unidade_manut_estoque) {
        this.trabalha_com_unidade_manut_estoque = trabalha_com_unidade_manut_estoque;
    }

    public String getTrabalha_com_paginacao_dados() {
        return trabalha_com_paginacao_dados;
    }

    public void setTrabalha_com_paginacao_dados(String trabalha_com_paginacao_dados) {
        this.trabalha_com_paginacao_dados = trabalha_com_paginacao_dados;
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

    public String getTrabalha_com_cobranca() {
        return trabalha_com_cobranca;
    }

    public void setTrabalha_com_cobranca(String trabalha_com_cobranca) {
        this.trabalha_com_cobranca = trabalha_com_cobranca;
    }

    public String getTrabalha_com_icms_st() {
        return trabalha_com_icms_st;
    }

    public void setTrabalha_com_icms_st(String trabalha_com_icms_st) {
        this.trabalha_com_icms_st = trabalha_com_icms_st;
    }

    public String getTrabalha_com_desc_auto_app() {
        return trabalha_com_desc_auto_app;
    }

    public void setTrabalha_com_desc_auto_app(String trabalha_com_desc_auto_app) {
        this.trabalha_com_desc_auto_app = trabalha_com_desc_auto_app;
    }

    public String getSincronia_automatica_app() {
        return sincronia_automatica_app;
    }

    public void setSincronia_automatica_app(String sincronia_automatica_app) {
        this.sincronia_automatica_app = sincronia_automatica_app;
    }

    public String getTrabalha_com_verba_pedido() {
        return trabalha_com_verba_pedido;
    }

    public void setTrabalha_com_verba_pedido(String trabalha_com_verba_pedido) {
        this.trabalha_com_verba_pedido = trabalha_com_verba_pedido;
    }

    public String getLista_todos_pedidos() {
        return lista_todos_pedidos;
    }

    public void setLista_todos_pedidos(String lista_todos_pedidos) {
        this.lista_todos_pedidos = lista_todos_pedidos;
    }

    public String getGira_tela_cliente() {
        return gira_tela_cliente;
    }

    public void setGira_tela_cliente(String gira_tela_cliente) {
        this.gira_tela_cliente = gira_tela_cliente;
    }

    public String getGira_tela_produto() {
        return gira_tela_produto;
    }

    public void setGira_tela_produto(String gira_tela_produto) {
        this.gira_tela_produto = gira_tela_produto;
    }

    public String getGira_tela_cobranca() {
        return gira_tela_cobranca;
    }

    public void setGira_tela_cobranca(String gira_tela_cobranca) {
        this.gira_tela_cobranca = gira_tela_cobranca;
    }

    public String getGira_tela_ped_pendente() {
        return gira_tela_ped_pendente;
    }

    public void setGira_tela_ped_pendente(String gira_tela_ped_pendente) {
        this.gira_tela_ped_pendente = gira_tela_ped_pendente;
    }

    public String getGira_tela_ped_enviado() {
        return gira_tela_ped_enviado;
    }

    public void setGira_tela_ped_enviado(String gira_tela_ped_enviado) {
        this.gira_tela_ped_enviado = gira_tela_ped_enviado;
    }

    public String getGira_tela_pedido() {
        return gira_tela_pedido;
    }

    public void setGira_tela_pedido(String gira_tela_pedido) {
        this.gira_tela_pedido = gira_tela_pedido;
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

    public String getTime_conn() {
        return time_conn;
    }

    public void setTime_conn(String time_conn) {
        this.time_conn = time_conn;
    }

    public String getTime_read() {
        return time_read;
    }

    public void setTime_read(String time_read) {
        this.time_read = time_read;
    }

    public String getTime_write() {
        return time_write;
    }

    public void setTime_write(String time_write) {
        this.time_write = time_write;
    }

    public String getOrdem_lista_cliente() {
        return ordem_lista_cliente;
    }

    public void setOrdem_lista_cliente(String ordem_lista_cliente) {
        this.ordem_lista_cliente = ordem_lista_cliente;
    }

    public String getAtual_lista_ped_exportado() {
        return atual_lista_ped_exportado;
    }

    public void setAtual_lista_ped_exportado(String atual_lista_ped_exportado) {
        this.atual_lista_ped_exportado = atual_lista_ped_exportado;
    }

    public String getConfirma_sincronia_dados() {
        return confirma_sincronia_dados;
    }

    public void setConfirma_sincronia_dados(String confirma_sincronia_dados) {
        this.confirma_sincronia_dados = confirma_sincronia_dados;
    }

    public String getCnpj_empresa() {
        return cnpj_empresa;
    }

    public void setCnpj_empresa(String cnpj_empresa) {
        this.cnpj_empresa = cnpj_empresa;
    }

    public String getMarca_produto_ja_comprado() {
        return marca_produto_ja_comprado;
    }

    public void setMarca_produto_ja_comprado(String marca_produto_ja_comprado) {
        this.marca_produto_ja_comprado = marca_produto_ja_comprado;
    }

    public String getVisualiza_legenda_cliente() {
        return visualiza_legenda_cliente;
    }

    public void setVisualiza_legenda_cliente(String visualiza_legenda_cliente) {
        this.visualiza_legenda_cliente = visualiza_legenda_cliente;
    }

    public String getVisualiza_legenda_produto() {
        return visualiza_legenda_produto;
    }

    public void setVisualiza_legenda_produto(String visualiza_legenda_produto) {
        this.visualiza_legenda_produto = visualiza_legenda_produto;
    }

    public String getVisualiza_legenda_pedido() {
        return visualiza_legenda_pedido;
    }

    public void setVisualiza_legenda_pedido(String visualiza_legenda_pedido) {
        this.visualiza_legenda_pedido = visualiza_legenda_pedido;
    }

    public String getVisualiza_legenda_sem_uso() {
        return visualiza_legenda_sem_uso;
    }

    public void setVisualiza_legenda_sem_uso(String visualiza_legenda_sem_uso) {
        this.visualiza_legenda_sem_uso = visualiza_legenda_sem_uso;
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
