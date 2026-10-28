package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Configuracao;

public class ConfiguracaoDAO {
    private DBHelper db;

    public ConfiguracaoDAO(DBHelper db) {
        this.db = db;
    }

    public boolean add(Configuracao configuracao) {
        Configuracao configuracao1 = getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        String v_l_cliente = null;
        String v_l_produto = null;
        String v_l_pedido  = null;
        if(!configuracao1.getId().isEmpty()) {
            v_l_cliente = configuracao1.getVisualiza_legenda_cliente();
            v_l_produto = configuracao1.getVisualiza_legenda_produto();
            v_l_pedido  = configuracao1.getVisualiza_legenda_pedido();

        }
        try {
            db.deleteDados("TBL_CONFIGURACAO", "1", null);
        } catch (SQLException e) {
        }
        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CNPJ_EMPRESA VARCHAR(14);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_VERBA_PEDIDO VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try{
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_ICMS_ST VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN LISTA_TODOS_PEDIDOS VARCHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }


        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CADASTRA_CLIENTE_APP CHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUAL_HIST_FIN_PEDIDO_APP CHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUAL_PRAZO_PGTO_PEDIDO_APP CHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN VISUAL_ESTOQUE_PRODUTO_APP CHAR(1);");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        try {
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PEDIDO CHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_CLIENTE CHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PRODUTO CHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_COBRANCA CHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PED_PENDENTE CHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN GIRA_TELA_PED_ENVIADO CHAR(1);");
        }catch ( SQLException e) {
        }catch ( Exception e) {
        }
        try {
            //TRABALHA_COM_CHECKIN_APP
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_CHECKOUT_APP CHAR(1)");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_CHECKIN_APP CHAR(1)");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO CHAR(1)");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN MARCA_PRODUTO_JA_COMPRADO CHAR(1)");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN CONFIRMA_SINCRONIA_DADOS CHAR(1)");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN ATUAL_LISTA_PED_EXPORTADO CHAR(1)");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN ORDEM_LISTA_CLIENTE CHAR(1)");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN ID_CON_PGTO_DEFAULT_APP INTEGER ");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN LAYOUT_VENDA_ERP INTEGER ");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_PAGINACAO_DADOS VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_UNIDADE_MANUT_ESTOQUE VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_CONFIRMACAO_PRAZO VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN PESQUISA_PRODUTO_POR_LINHA_GRUPO VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_PRE_PEDIDO VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_LIMITE_CREDITO VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_DSCTO_REAIS VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_CAMPANHAS VARCHAR(1);");
            db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TRABALHA_COM_COBRANCA VARCHAR(1);");

        } catch (SQLException e) {
        } catch (Exception e) {
        }
        try {
                db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TIME_CONN INTEGER;");
                db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TIME_READ INTEGER;");
                db.alterar("ALTER TABLE TBL_CONFIGURACAO ADD COLUMN TIME_WRITE INTEGER;");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContentValues content = new ContentValues();
        content.put("URL_IP_1", configuracao.getUrl_ip_1());
        content.put("PORTA_1", configuracao.getPorta_1());
        content.put("URL_IP_DESC_1", configuracao.getUrl_ip_desc_1());
        content.put("URL_IP_2", configuracao.getUrl_ip_2());
        content.put("PORTA_2", configuracao.getPorta_2());
        content.put("URL_IP_DESC_2", configuracao.getUrl_ip_desc_2());
        content.put("CONEXAO_SEGURA", configuracao.getConexao_segura());
        content.put("TRABALHA_COM_CAMPANHAS", configuracao.getTrabalha_com_campanhas());
        content.put("TRABALHA_COM_DSCTO_REAIS", configuracao.getTrabalha_com_dscto_reais());
        content.put("TRABALHA_COM_LIMITE_CREDITO", configuracao.getTrabalha_com_limite_cred());
        content.put("TRABALHA_COM_PRE_PEDIDO", configuracao.getTrabalha_com_pre_pedido());
        content.put("PESQUISA_PRODUTO_POR_LINHA_GRUPO", configuracao.getPesquisa_produto_por_linha_grupo());
        content.put("TRABALHA_COM_CONFIRMACAO_PRAZO", configuracao.getTrabalha_com_confirmacao_prazo());
        content.put("TRABALHA_COM_UNIDADE_MANUT_ESTOQUE", configuracao.getTrabalha_com_unidade_manut_estoque());
        content.put("TRABALHA_COM_PAGINACAO_DADOS", configuracao.getTrabalha_com_paginacao_dados());
        content.put("ID_CON_PGTO_DEFAULT_APP", configuracao.getId_con_pgto_default_app());
        content.put("TRABALHA_COM_COBRANCA", configuracao.getTrabalha_com_cobranca());
        content.put("TRABALHA_COM_ICMS_ST", configuracao.getTrabalha_com_icms_st());
        content.put("PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP", configuracao.getTrabalha_com_icms_st());
        content.put("SINCRONIA_AUTOMATICA_APP", configuracao.getSincronia_automatica_app());
        content.put("TRABALHA_COM_VERBA_PEDIDO", configuracao.getTrabalha_com_verba_pedido());
        content.put("LISTA_TODOS_PEDIDOS", configuracao.getLista_todos_pedidos());
        content.put("GIRA_TELA_CLIENTE", configuracao.getGira_tela_cliente() );
        content.put("GIRA_TELA_PRODUTO", configuracao.getGira_tela_produto() );
        content.put("GIRA_TELA_COBRANCA", configuracao.getGira_tela_cobranca() );
        content.put("GIRA_TELA_PED_PENDENTE", configuracao.getGira_tela_ped_pendente() );
        content.put("GIRA_TELA_PED_ENVIADO", configuracao.getGira_tela_ped_enviado() );
        content.put("GIRA_TELA_PEDIDO", configuracao.getGira_tela_pedido() );
        content.put("LAYOUT_VENDA_ERP", configuracao.getLayout_venda_erp());
        content.put("CADASTRA_CLIENTE_APP", configuracao.getCadastra_cliente_app());
        content.put("VISUAL_HIST_FIN_PEDIDO_APP", configuracao.getVisual_hist_fin_pedido_app());
        content.put("VISUAL_PRAZO_PGTO_PEDIDO_APP", configuracao.getVisual_prazo_pgto_pedido_app());
        content.put("VISUAL_ESTOQUE_PRODUTO_APP", configuracao.getVisual_estoque_produto_app());
        if (  configuracao.getTime_conn() != null && !configuracao.getTime_conn().isEmpty()) {
            content.put("TIME_CONN", configuracao.getTime_conn());
        } else {
            content.put("TIME_CONN", 10);
        }
        if ( configuracao.getTime_read() != null && !configuracao.getTime_read().isEmpty() ) {
            content.put("TIME_READ", configuracao.getTime_read());
        } else {
            content.put("TIME_READ", 180);
        }
        if ( configuracao.getTime_write() != null && !configuracao.getTime_write().isEmpty() ) {
            content.put("TIME_WRITE", configuracao.getTime_write());
        } else {
            content.put("TIME_WRITE", 180);
        }
        if( configuracao.getOrdem_lista_cliente() == null || configuracao.getOrdem_lista_cliente().isEmpty()) {
            content.put("ORDEM_LISTA_CLIENTE", "N");
        } else {
            content.put("ORDEM_LISTA_CLIENTE", configuracao.getOrdem_lista_cliente());
        }

        if( configuracao.getAtual_lista_ped_exportado() == null || configuracao.getAtual_lista_ped_exportado().isEmpty()) {
            content.put("ATUAL_LISTA_PED_EXPORTADO", "N");
        } else {
            content.put("ATUAL_LISTA_PED_EXPORTADO", configuracao.getAtual_lista_ped_exportado());
        }

        content.put("CNPJ_EMPRESA"                    , configuracao.getCnpj_empresa());
        content.put("MARCA_PRODUTO_JA_COMPRADO"       , configuracao.getMarca_produto_ja_comprado());
        content.put("PEDIDO_ENVIADO_ON_LINE_APP"      , configuracao.getPedido_enviado_on_line_app());
        content.put("VISUALIZA_GRAFICO_ON_LINE_APP"   , configuracao.getVisualiza_grafico_on_line_app());
        content.put("VISUALIZA_ULTIMO_PRECO_VENDA_APP", configuracao.getVisualiza_ultimo_preco_venda_app());
        content.put("CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO", configuracao.getCalcula_dscto_automatico_muda_prazo() == null || configuracao.getCalcula_dscto_automatico_muda_prazo().isEmpty() ? "N" : configuracao.getCalcula_dscto_automatico_muda_prazo());
        content.put("VISUALIZA_LEGENDA_CLIENTE", v_l_cliente == null || v_l_cliente.isEmpty() ? "S" : v_l_cliente );
        content.put("VISUALIZA_LEGENDA_PRODUTO", v_l_produto == null || v_l_produto.isEmpty() ? "S" : v_l_produto );
        content.put("VISUALIZA_LEGENDA_PEDIDO" , v_l_pedido  == null || v_l_pedido.isEmpty()  ? "S" : v_l_pedido );
        content.put("TRABALHA_COM_CHECKIN_APP" , configuracao.getTrab_com_checkin_app()  == null || configuracao.getTrab_com_checkin_app().isEmpty()  ? "N" : configuracao.getTrab_com_checkin_app() );
        content.put("TRABALHA_COM_CHECKOUT_APP" , configuracao.getTrab_com_checkout_app()  == null || configuracao.getTrab_com_checkout_app().isEmpty()  ? "N" : configuracao.getTrab_com_checkout_app() );

        try {
            if ( db.addDados("TBL_CONFIGURACAO",  content) > 0)
                return true;
        } catch (SQLException e) {
        }
        return false;
    }

    public int update(String campanha, String desconto, String limite, String pre_pedido, String conf_prazo, String usa_unid_estoque,
                      String usa_paginacao, String layout_venda_erp, String id_con_pgto_default_app, String usa_cobranca_app,
                      String usa_icms_app, String desc_automatico_app, String sincronia_automatica, String usa_verba_pedido,
                      String cadastra_cliente_app, String visual_hist_fin_pedido_app, String visual_prazo_pgto_pedido_app,
                      String visual_estoque_produto_app, String ordem_lista_cliente, String atual_lista_ped_exportado,
                      String confirma_sincronia_dados, String cnpj_empresa, String marca_produto_ja_comprado_app,
                      String pedido_enviado_on_line_app, String visualiza_grafico_on_line_app, String visualiza_ultimo_preco_venda_app,
                      String calcula_dscto_automatico_muda_prazo, String trab_com_checkin_app, String trab_com_checkout_app) {



        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("TRABALHA_COM_CAMPANHAS", campanha);
            content.put("TRABALHA_COM_DSCTO_REAIS", desconto);
            content.put("TRABALHA_COM_LIMITE_CREDITO", limite);
            content.put("TRABALHA_COM_PRE_PEDIDO", pre_pedido);
            content.put("TRABALHA_COM_CONFIRMACAO_PRAZO", conf_prazo);
            content.put("TRABALHA_COM_UNIDADE_MANUT_ESTOQUE", usa_unid_estoque);
            content.put("TRABALHA_COM_PAGINACAO_DADOS", "S"/*usa_paginacao*/);
            content.put("LAYOUT_VENDA_ERP", layout_venda_erp);
            content.put("ID_CON_PGTO_DEFAULT_APP", id_con_pgto_default_app);
            content.put("TRABALHA_COM_COBRANCA", "N");
            try {
                if (usa_cobranca_app != null || !usa_cobranca_app.isEmpty())
                    content.put("TRABALHA_COM_COBRANCA", usa_cobranca_app);
            } catch ( NullPointerException e) {
            } catch ( Exception e) {
            }
            content.put("TRABALHA_COM_ICMS_ST", usa_icms_app);
            content.put("PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP", desc_automatico_app);
            content.put("SINCRONIA_AUTOMATICA_APP", sincronia_automatica);
            content.put("TRABALHA_COM_VERBA_PEDIDO", usa_verba_pedido);
            content.put("CADASTRA_CLIENTE_APP", cadastra_cliente_app);
            content.put("VISUAL_HIST_FIN_PEDIDO_APP", visual_hist_fin_pedido_app);
            content.put("VISUAL_PRAZO_PGTO_PEDIDO_APP", visual_prazo_pgto_pedido_app);
            content.put("VISUAL_ESTOQUE_PRODUTO_APP", visual_estoque_produto_app);
            if( ordem_lista_cliente == null || ordem_lista_cliente.isEmpty()) {
                content.put("ORDEM_LISTA_CLIENTE", "N");
            } else {
                content.put("ORDEM_LISTA_CLIENTE", ordem_lista_cliente.toUpperCase());
            }

            if( atual_lista_ped_exportado == null || atual_lista_ped_exportado.isEmpty()) {
                content.put("ATUAL_LISTA_PED_EXPORTADO", "N");
            } else {
                content.put("ATUAL_LISTA_PED_EXPORTADO", atual_lista_ped_exportado);
            }
            content.put("CONFIRMA_SINCRONIA_DADOS", confirma_sincronia_dados);
            content.put("CNPJ_EMPRESA", cnpj_empresa);
            if ( marca_produto_ja_comprado_app == null || marca_produto_ja_comprado_app.isEmpty()) {
                content.put("MARCA_PRODUTO_JA_COMPRADO", "N");
            } else {
                content.put("MARCA_PRODUTO_JA_COMPRADO", marca_produto_ja_comprado_app);
            }
            content.put("PEDIDO_ENVIADO_ON_LINE_APP"      , pedido_enviado_on_line_app);
            content.put("VISUALIZA_GRAFICO_ON_LINE_APP"   , visualiza_grafico_on_line_app);
            content.put("VISUALIZA_ULTIMO_PRECO_VENDA_APP", visualiza_ultimo_preco_venda_app);
            content.put("CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO", calcula_dscto_automatico_muda_prazo);
            content.put("TRABALHA_COM_CHECKIN_APP", trab_com_checkin_app);
            content.put("TRABALHA_COM_CHECKOUT_APP", trab_com_checkout_app);
            System.gc();
            return db.updateDados("TBL_CONFIGURACAO", content, null);
        } catch ( NullPointerException|SQLException e){
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Configuracao configuracao) {  //Trabalha com campanha/Trabalha com desconto em valor
        ContentValues content = new ContentValues();
        try {
            content.put("URL_IP_1", configuracao.getUrl_ip_1());
            content.put("PORTA_1", configuracao.getPorta_1());
            content.put("URL_IP_DESC_1", configuracao.getUrl_ip_desc_1());
            content.put("CONEXAO_SEGURA", configuracao.getConexao_segura());
            return db.updateDados("TBL_CONFIGURACAO", content, null);

        } catch ( NullPointerException|SQLException e){
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @SuppressLint("Range")
    public Configuracao getConfiguracao(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        Configuracao configuracao = new Configuracao();

        if (cursor.moveToNext()) {
            configuracao.setId(cursor.getString(cursor.getColumnIndex("ID")));
            configuracao.setUsuario(cursor.getString(cursor.getColumnIndex("USUARIO")));
            configuracao.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));

            configuracao.setUrl_ip_1(cursor.getString(cursor.getColumnIndex("URL_IP_1")));
            configuracao.setPorta_1(cursor.getInt(cursor.getColumnIndex("PORTA_1")));
            configuracao.setUrl_ip_desc_1(cursor.getString(cursor.getColumnIndex("URL_IP_DESC_1")));

            configuracao.setUrl_ip_2(cursor.getString(cursor.getColumnIndex("URL_IP_2")));
            configuracao.setPorta_2(cursor.getInt(cursor.getColumnIndex("PORTA_2")));
            configuracao.setUrl_ip_desc_2(cursor.getString(cursor.getColumnIndex("URL_IP_DESC_2")));
            configuracao.setConexao_segura(cursor.getString(cursor.getColumnIndex("CONEXAO_SEGURA")));
            try {
                configuracao.setTrabalha_com_campanhas(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_CAMPANHAS")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setTrabalha_com_campanhas("N");
            } catch (Exception e) {
                configuracao.setTrabalha_com_campanhas("N");
            }
            try {
                configuracao.setTrabalha_com_dscto_reais(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_DSCTO_REAIS")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setTrabalha_com_dscto_reais("N");
            } catch (Exception e) {
                configuracao.setTrabalha_com_dscto_reais("N");
            }
            try {
                configuracao.setTrabalha_com_limite_cred(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_LIMITE_CREDITO")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setTrabalha_com_limite_cred("N");
            } catch (Exception e) {
                configuracao.setTrabalha_com_limite_cred("N");
            }
            try {
                configuracao.setTrabalha_com_pre_pedido(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_PRE_PEDIDO")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setTrabalha_com_pre_pedido("N");
            } catch (Exception e) {
                configuracao.setTrabalha_com_pre_pedido("N");
            }
            try {
                configuracao.setPesquisa_produto_por_linha_grupo(cursor.getString(cursor.getColumnIndex("PESQUISA_PRODUTO_POR_LINHA_GRUPO")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setPesquisa_produto_por_linha_grupo("G");
            } catch (Exception e) {
                configuracao.setPesquisa_produto_por_linha_grupo("G");
            }

            try {
                configuracao.setTrabalha_com_confirmacao_prazo(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_CONFIRMACAO_PRAZO")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setTrabalha_com_confirmacao_prazo("N");
            } catch (Exception e) {
                configuracao.setTrabalha_com_confirmacao_prazo("N");
            }

            try {
                configuracao.setTrabalha_com_unidade_manut_estoque(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_UNIDADE_MANUT_ESTOQUE")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setTrabalha_com_unidade_manut_estoque("N");
            } catch (Exception e) {
                configuracao.setTrabalha_com_unidade_manut_estoque("N");
            }

            try {
                configuracao.setTrabalha_com_paginacao_dados(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_PAGINACAO_DADOS")));
            } catch (IllegalStateException | NullPointerException e) {
                configuracao.setTrabalha_com_paginacao_dados("N");
            } catch (Exception e) {
                configuracao.setTrabalha_com_paginacao_dados("N");
            }
            configuracao.setTrabalha_com_paginacao_dados("S");
            try {
                if ( cursor.getString(cursor.getColumnIndex("LAYOUT_VENDA_ERP")) != null  && !cursor.getString(cursor.getColumnIndex("LAYOUT_VENDA_ERP")).isEmpty() ) {
                    configuracao.setLayout_venda_erp(cursor.getString(cursor.getColumnIndex("LAYOUT_VENDA_ERP")));
                } else {
                    configuracao.setLayout_venda_erp("0");
                }
            } catch ( NullPointerException e) {
                configuracao.setLayout_venda_erp("0");
            } catch ( Exception e) {
                configuracao.setLayout_venda_erp("0");
            }

            try {
                if (cursor.getString(cursor.getColumnIndex("ID_CON_PGTO_DEFAULT_APP")) != null & !cursor.getString(cursor.getColumnIndex("ID_CON_PGTO_DEFAULT_APP")).isEmpty())
                    configuracao.setId_con_pgto_default_app(cursor.getString(cursor.getColumnIndex("ID_CON_PGTO_DEFAULT_APP")));
                else
                    configuracao.setId_con_pgto_default_app("0");
            } catch ( NullPointerException e) {
                configuracao.setId_con_pgto_default_app("0");
            } catch ( Exception e) {
                configuracao.setId_con_pgto_default_app("0");
            }

            try {
                if ( cursor.getString(cursor.getColumnIndex("TRABALHA_COM_COBRANCA")) != null && !cursor.getString(cursor.getColumnIndex("TRABALHA_COM_COBRANCA")).isEmpty())
                    configuracao.setTrabalha_com_cobranca(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_COBRANCA")));
                else
                    configuracao.setTrabalha_com_cobranca("N");
            } catch ( NullPointerException e) {
                configuracao.setTrabalha_com_cobranca("N");
            } catch ( Exception e) {
                configuracao.setTrabalha_com_cobranca("N");
            }

            try {
                if (cursor.getString(cursor.getColumnIndex("TRABALHA_COM_ICMS_ST")) != null && !cursor.getString(cursor.getColumnIndex("TRABALHA_COM_ICMS_ST")).isEmpty())
                    configuracao.setTrabalha_com_icms_st(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_ICMS_ST")));
                else
                    configuracao.setTrabalha_com_icms_st("N");
            } catch ( NullPointerException e) {
                configuracao.setTrabalha_com_icms_st("N");
            } catch ( Exception e) {
                configuracao.setTrabalha_com_icms_st("N");
            }

            try {
                if (cursor.getString(cursor.getColumnIndex("PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP")) != null && !cursor.getString(cursor.getColumnIndex("PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP")).isEmpty())
                    configuracao.setTrabalha_com_desc_auto_app(cursor.getString(cursor.getColumnIndex("PEDIDO_ITEM_PUXAR_DESC_PADRAO_APP")));
                else
                    configuracao.setTrabalha_com_desc_auto_app("N");
            } catch ( NullPointerException e) {
                configuracao.setTrabalha_com_desc_auto_app("N");
            } catch ( Exception e) {
                configuracao.setTrabalha_com_desc_auto_app("N");
            }

            try {
                configuracao.setSincronia_automatica_app(cursor.getString(cursor.getColumnIndex("SINCRONIA_AUTOMATICA_APP")));
            } catch ( NullPointerException e) {
                configuracao.setSincronia_automatica_app("N");
            } catch ( Exception e) {
                configuracao.setSincronia_automatica_app("N");
            }

            try {
                if (cursor.getString(cursor.getColumnIndex("TRABALHA_COM_VERBA_PEDIDO")) != null && !cursor.getString(cursor.getColumnIndex("TRABALHA_COM_VERBA_PEDIDO")).isEmpty())
                    configuracao.setTrabalha_com_verba_pedido(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_VERBA_PEDIDO")));
                else
                    configuracao.setTrabalha_com_verba_pedido("N");
            } catch ( NullPointerException e) {
                configuracao.setTrabalha_com_verba_pedido("N");
            } catch ( Exception e) {
                configuracao.setTrabalha_com_verba_pedido("N");
            }
            try {
                if (cursor.getString(cursor.getColumnIndex("LISTA_TODOS_PEDIDOS")) != null && !cursor.getString(cursor.getColumnIndex("LISTA_TODOS_PEDIDOS")).isEmpty())
                    configuracao.setLista_todos_pedidos(cursor.getString(cursor.getColumnIndex("LISTA_TODOS_PEDIDOS")));
                else
                    configuracao.setLista_todos_pedidos("N");
            } catch ( NullPointerException e) {
                configuracao.setLista_todos_pedidos("N");
            } catch ( Exception e) {
                configuracao.setLista_todos_pedidos("N");
            }
            configuracao.setGira_tela_cliente(cursor.getString(cursor.getColumnIndex("GIRA_TELA_CLIENTE")));
            configuracao.setGira_tela_produto(cursor.getString(cursor.getColumnIndex("GIRA_TELA_PRODUTO")));
            configuracao.setGira_tela_cobranca(cursor.getString(cursor.getColumnIndex("GIRA_TELA_COBRANCA")));
            configuracao.setGira_tela_ped_pendente(cursor.getString(cursor.getColumnIndex("GIRA_TELA_PED_PENDENTE")));
            configuracao.setGira_tela_ped_enviado(cursor.getString(cursor.getColumnIndex("GIRA_TELA_PED_ENVIADO")));
            configuracao.setGira_tela_pedido(cursor.getString(cursor.getColumnIndex("GIRA_TELA_PEDIDO")));
            if (cursor.getString(cursor.getColumnIndex("GIRA_TELA_CLIENTE")) == null || cursor.getString(cursor.getColumnIndex("GIRA_TELA_CLIENTE")).isEmpty()) {
                configuracao.setGira_tela_cliente("N");
                configuracao.setGira_tela_produto("N");
                configuracao.setGira_tela_cobranca("N");
                configuracao.setGira_tela_ped_pendente("N");
                configuracao.setGira_tela_ped_enviado("N");
                configuracao.setGira_tela_pedido("N");
            }
            try {
                configuracao.setCadastra_cliente_app(cursor.getString(cursor.getColumnIndex("CADASTRA_CLIENTE_APP")));
            } catch ( NullPointerException e) {
                configuracao.setCadastra_cliente_app("S");
            } catch ( Exception e) {
                configuracao.setCadastra_cliente_app("S");
            }
            try {
                configuracao.setVisual_hist_fin_pedido_app(cursor.getString(cursor.getColumnIndex("VISUAL_HIST_FIN_PEDIDO_APP")));
                configuracao.setVisual_prazo_pgto_pedido_app(cursor.getString(cursor.getColumnIndex("VISUAL_PRAZO_PGTO_PEDIDO_APP")));
                configuracao.setVisual_estoque_produto_app(cursor.getString(cursor.getColumnIndex("VISUAL_ESTOQUE_PRODUTO_APP")));
                //VISUAL_PRAZO_PGTO_PEDIDO_APP CHAR(1), VISUAL_ESTOQUE_PRODUTO_APP CHAR(1)
            } catch ( NullPointerException e) {
                configuracao.setVisual_hist_fin_pedido_app("N");
                configuracao.setVisual_prazo_pgto_pedido_app("S");
                configuracao.setVisual_estoque_produto_app("S");
            } catch ( Exception e) {
                configuracao.setVisual_hist_fin_pedido_app("N");
                configuracao.setVisual_prazo_pgto_pedido_app("S");
                configuracao.setVisual_estoque_produto_app("S");
            }
            try {
                if (!cursor.getString(cursor.getColumnIndex("TIME_CONN")).isEmpty())
                    configuracao.setTime_conn(cursor.getString(cursor.getColumnIndex("TIME_CONN")));
                else
                    configuracao.setTime_conn("10");

                if ( !cursor.getString(cursor.getColumnIndex("TIME_READ")).isEmpty())
                   configuracao.setTime_read(cursor.getString(cursor.getColumnIndex("TIME_READ")));
                else
                    configuracao.setTime_read("180");

                if ( !cursor.getString(cursor.getColumnIndex("TIME_WRITE")).isEmpty())
                   configuracao.setTime_write(cursor.getString(cursor.getColumnIndex("TIME_WRITE")));
                else
                    configuracao.setTime_write("180");

            } catch (NullPointerException e) {
                configuracao.setTime_conn("10");
                configuracao.setTime_read("180");
                configuracao.setTime_write("180");
            } catch (Exception e) {
                configuracao.setTime_conn("10");
                configuracao.setTime_read("180");
                configuracao.setTime_write("180");

            }
            try {
                if (cursor.getString(cursor.getColumnIndex("ORDEM_LISTA_CLIENTE")) == null || cursor.getString(cursor.getColumnIndex("ORDEM_LISTA_CLIENTE")).isEmpty())
                    configuracao.setOrdem_lista_cliente("N");
                else
                    configuracao.setOrdem_lista_cliente(cursor.getString(cursor.getColumnIndex("ORDEM_LISTA_CLIENTE")));
            } catch ( NullPointerException e) {
                configuracao.setOrdem_lista_cliente("N");
            } catch ( Exception e) {
                configuracao.setOrdem_lista_cliente("N");
            }
            try {
                if (cursor.getString(cursor.getColumnIndex("ATUAL_LISTA_PED_EXPORTADO")) == null || cursor.getString(cursor.getColumnIndex("ATUAL_LISTA_PED_EXPORTADO")).isEmpty())
                    configuracao.setAtual_lista_ped_exportado("N");
                else
                    configuracao.setAtual_lista_ped_exportado(cursor.getString(cursor.getColumnIndex("ATUAL_LISTA_PED_EXPORTADO")));
            } catch ( NullPointerException e) {
                configuracao.setAtual_lista_ped_exportado("N");
            } catch ( Exception e) {
                configuracao.setAtual_lista_ped_exportado("N");
            }

            try {
                if (cursor.getString(cursor.getColumnIndex("CONFIRMA_SINCRONIA_DADOS")) == null || cursor.getString(cursor.getColumnIndex("CONFIRMA_SINCRONIA_DADOS")).isEmpty())
                    configuracao.setConfirma_sincronia_dados("N");
                else
                    configuracao.setConfirma_sincronia_dados(cursor.getString(cursor.getColumnIndex("CONFIRMA_SINCRONIA_DADOS")));
            } catch ( NullPointerException e) {
                configuracao.setConfirma_sincronia_dados("N");
            } catch ( Exception e) {
                configuracao.setConfirma_sincronia_dados("N");
            }
            configuracao.setCnpj_empresa(cursor.getString(cursor.getColumnIndex("CNPJ_EMPRESA")));
            try {
                configuracao.setMarca_produto_ja_comprado(cursor.getString(cursor.getColumnIndex("MARCA_PRODUTO_JA_COMPRADO")));
            } catch (NullPointerException e) {
                configuracao.setMarca_produto_ja_comprado("N");
            }
            try {
                configuracao.setPedido_enviado_on_line_app(cursor.getString(cursor.getColumnIndex("PEDIDO_ENVIADO_ON_LINE_APP")));
            } catch (NullPointerException e) {
                configuracao.setPedido_enviado_on_line_app("N");
            }
            try {
                configuracao.setVisualiza_grafico_on_line_app(cursor.getString(cursor.getColumnIndex("VISUALIZA_GRAFICO_ON_LINE_APP")));
            } catch (NullPointerException e) {
                configuracao.setVisualiza_grafico_on_line_app("N");
            }
            try {
                configuracao.setVisualiza_ultimo_preco_venda_app(cursor.getString(cursor.getColumnIndex("VISUALIZA_ULTIMO_PRECO_VENDA_APP")));
            } catch (NullPointerException e) {
                configuracao.setVisualiza_ultimo_preco_venda_app("N");
            }

            try {
                configuracao.setCalcula_dscto_automatico_muda_prazo(
                        cursor.getString(cursor.getColumnIndex("CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO")) == null ||
                                cursor.getString(cursor.getColumnIndex("CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO")).isEmpty() ? "N" :
                                cursor.getString(cursor.getColumnIndex("CALCULA_DSCTO_AUTOMATICO_MUDA_PRAZO")));
            } catch (NullPointerException | IllegalStateException e) {
                configuracao.setCalcula_dscto_automatico_muda_prazo("N");
            }
            //VISUALIZA_ULTIMO_PRECO_VENDA_APP
            //PEDIDO_ENVIADO_ON_LINE_APP
            configuracao.setVisualiza_legenda_cliente(cursor.getString(cursor.getColumnIndex("VISUALIZA_LEGENDA_CLIENTE")));
            configuracao.setVisualiza_legenda_produto(cursor.getString(cursor.getColumnIndex("VISUALIZA_LEGENDA_PRODUTO")));
            configuracao.setVisualiza_legenda_pedido(cursor.getString(cursor.getColumnIndex("VISUALIZA_LEGENDA_PEDIDO")));
            try {
                configuracao.setTrab_com_checkin_app(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_CHECKIN_APP")));
            } catch (NullPointerException |  IllegalStateException e) {
                configuracao.setTrab_com_checkin_app("N");
            }
            try {
                configuracao.setTrab_com_checkout_app(cursor.getString(cursor.getColumnIndex("TRABALHA_COM_CHECKOUT_APP")));
            } catch (NullPointerException |  IllegalStateException e) {
                configuracao.setTrab_com_checkout_app("N");
            }

        } else {
            configuracao.setId("");
            cursor.close();
        }
        return configuracao;
    }
}
