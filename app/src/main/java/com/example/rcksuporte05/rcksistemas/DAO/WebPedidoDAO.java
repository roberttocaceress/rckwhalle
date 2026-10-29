package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoAnalise;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.util.ArrayList;
import java.util.List;

public class WebPedidoDAO {
    private DBHelper db;

    public WebPedidoDAO(DBHelper db) {
        this.db = db;
    }

    public long add(WebPedido webPedido) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_EMPRESA", webPedido.getId_empresa());
            if (webPedido.getCadastro().getId_cadastro_servidor() <= 0) {
                content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro());
                content.put("CLIENTE_NOVO", "S");
            } else {
                content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro_servidor());
                content.put("CLIENTE_NOVO", "N");
            }
            content.put("ID_VENDEDOR", webPedido.getId_vendedor());
            content.put("ID_CONDICAO_PAGAMENTO", webPedido.getId_condicao_pagamento());
            content.put("ID_OPERACAO", webPedido.getId_operacao());
            content.put("ID_TABELA", webPedido.getId_tabela());
            content.put("NOME_EXTENSO", webPedido.getCadastro().getNome_cadastro());
            content.put("DATA_EMISSAO", webPedido.getData_emissao());
            //content.put("DATA_EMISSAO", db.pegaDataAtual());
            content.put("VALOR_PRODUTOS", webPedido.getValor_produtos());
            content.put("VALOR_DESCONTO", webPedido.getValor_desconto());
            content.put("VALOR_DESCONTO_ADD", webPedido.getValor_desconto_add());
            content.put("DESCONTO_PER", webPedido.getDesconto_per());
            content.put("DESCONTO_PER_ADD", webPedido.getDesconto_per_add());
            content.put("VALOR_TOTAL", webPedido.getValor_total());
            content.put("EXCLUIDO", webPedido.getExcluido());
            content.put("EXCLUIDO_USUARIO_ID", webPedido.getExcluido_usuario_id());
            content.put("EXCLUIDO_USUARIO_NOME", webPedido.getExcluido_usuario_nome());
            content.put("EXCLUIDO_USUARIO_DATA", webPedido.getExcluido_usuario_data());
            content.put("JUSTIFICATIVA_EXCLUSAO", webPedido.getJustificativa_exclusao());
            content.put("USUARIO_LANCAMENTO_ID", webPedido.getUsuario_lancamento_id());
            content.put("USUARIO_LANCAMENTO_NOME", webPedido.getUsuario_lancamento_nome());
            content.put("USUARIO_LANCAMENTO_DATA", webPedido.getUsuario_lancamento_data());
            content.put("OBSERVACOES", webPedido.getObservacoes());
            content.put("STATUS", webPedido.getStatus());
            content.put("ID_PEDIDO_VENDA", webPedido.getId_pedido_venda());
            content.put("ID_NOTA_FISCAL", webPedido.getId_nota_fiscal());
            content.put("ID_TABELA_PRECO_FAIXA", webPedido.getId_tabela_preco_faixa());
            content.put("PONTOS_TOTAL", webPedido.getPontos_total());
            content.put("PONTOS_COEFICIENTE", webPedido.getPontos_coeficiente());
            content.put("PONTOS_COR", webPedido.getPontos_cor());
            content.put("COMISSAO_PERCENTUAL", webPedido.getComissao_percentual());
            content.put("COMISSAO_VALOR", webPedido.getComissao_valor());
            content.put("ID_FAIXA_FINAL", webPedido.getId_faixa_final());
            content.put("VALOR_BONUS_CREDOR", webPedido.getValor_bonus_credor());
            content.put("PERC_BONUS_CREDOR", webPedido.getPerc_bonus_credor());
            content.put("DATA_PREV_ENTREGA", webPedido.getData_prev_entrega());
            content.put("ID_WEB_PEDIDO_SERVIDOR", webPedido.getId_web_pedido_servidor());
            content.put("PEDIDO_ENVIADO", webPedido.getPedido_enviado());
            content.put("FINALIZADO", webPedido.getFinalizado());
            //NOVOS CAMPOS   29/01/2020
            content.put("ID_PEDIDO", webPedido.getId_pedido());
            content.put("WEB_IMPORTADO_DATA", webPedido.getWeb_importado_data());
            content.put("ANALISE_CREDITO", webPedido.getAnalise_credito());
            content.put("ANALISE_USUARIO_ID", webPedido.getAnalise_usuario_id());
            content.put("ANALISE_USUARIO_NOME", webPedido.getAnalise_usuario_nome());
            content.put("ANALISE_USUARIO_DATA", webPedido.getAnalise_usuario_data());
            content.put("ORDEM_CARREGAMENTO", webPedido.getOrdem_carregamento());
            content.put("ID_ORDEM_CARREGAMENTO", webPedido.getId_ordem_carregamento());
            content.put("ID_TRANSPORTADORA", webPedido.getId_transportadora());
            content.put("NOME_TRANSPORTADORA", webPedido.getNome_transportadora());
            content.put("NUMERO_NOTA_FISCAL", webPedido.getNumero_nota_fiscal());
            content.put("ID_NOTA_FISCAL", webPedido.getId_nota_fiscal());  //ANALISAR P PEGAR DA TABELA PEDIDO_VENDA
            content.put("DATA_NOTA_FISCAL", webPedido.getData_nota_fiscal());
            content.put("ESPECIE", webPedido.getEspecie());
            content.put("VOLUMES", webPedido.getVolumes());
            content.put("WEB_VALOR_TOTAL", webPedido.getWeb_valor_total());
            content.put("EXPEDICAO_STATUS", webPedido.getExpedicao_status());
            content.put("VALOR_FATURADO", webPedido.getValor_faturado());
            content.put("VALOR_ICMS_ST", webPedido.getValor_icms_st());
            content.put("FATURADO", webPedido.getFaturado());
            content.put("ID_OUTRO_VENDEDOR", webPedido.getId_outro_vendedor());
            try {
                content.put("VALOR_RECIBO", webPedido.getValor_recibo().replaceAll("\\s+", "").replace(",", "."));
            } catch (NullPointerException | NumberFormatException e) {
                content.put("VALOR_RECIBO", webPedido.getValor_recibo());
            } catch (Exception e) {
                content.put("VALOR_RECIBO", webPedido.getValor_recibo());
            }
            try {
                if (!webPedido.getId_moeda_padrao().isEmpty() || webPedido.getId_moeda_padrao() != null)
                    content.put("ID_MOEDA_PADRAO", webPedido.getId_moeda_padrao());
                else
                    content.put("ID_MOEDA_PADRAO", "1");
            } catch (NullPointerException e) {
                content.put("ID_MOEDA_PADRAO", "1");
            }
            content.put("DESCONTO_INDEVIDO", "N");
            content.put("ID_PEDIDO_APP", webPedido.getId_pedido_app());
            try {
                content.put("PRAZO_CONFIRMADO", webPedido.getPrazo_confirmado());
            } catch (NullPointerException e) {
                content.put("PRAZO_CONFIRMADO", "N");
            } catch (Exception e) {
                content.put("PRAZO_CONFIRMADO", "N");
            }
            try {
                content.put("VALOR_DESCONTO_ADIC", webPedido.getValor_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch (NullPointerException | NumberFormatException e) {
                content.put("VALOR_DESCONTO_ADIC", webPedido.getValor_desconto_adic());
            } catch (Exception e) {
                content.put("VALOR_DESCONTO_ADIC", webPedido.getValor_desconto_adic());
            }
            try {
                content.put("PERC_DESCONTO_ADIC", webPedido.getPerc_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch (NullPointerException | NumberFormatException e) {
                content.put("PERC_DESCONTO_ADIC", webPedido.getPerc_desconto_adic());
            } catch (Exception e) {
                content.put("PERC_DESCONTO_ADIC", webPedido.getPerc_desconto_adic());
            }
            //content.put("ID_PEDIDO_APP_FDB", webPedido.getId_pedido_app_fdb());
            content.put("QTDE_ITENS", webPedido.getQtde_itens());
            content.put("ANALISE_ESTOQ_EFETUADA", webPedido.getAnalise_estoq_efetuada() == null ? "N" : webPedido.getAnalise_estoq_efetuada());
            content.put("ANALISE_ESTOQ_DATA_HORA", webPedido.getAnalise_estoque_data_hora());
            content.put("ID_VISITA_APP", webPedido.getId_visita_app());
            content.put("ID_ANALISE_APP", webPedido.getId_analise_app() == null ? "" : webPedido.getId_analise_app());

            //System.gc();
            return db.addDados("TBL_WEB_PEDIDO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int updateIdFDB(WebPedido webPedido, String idPedido, String dataFaturamento, String finalizado ) {
        ContentValues content = new ContentValues();
        content.put("ID_PEDIDO_APP_FDB", webPedido.getId_pedido_app_fdb());
        if ( !dataFaturamento.isEmpty())
           content.put("DATA_PREV_ENTREGA", dataFaturamento);
        if ( !finalizado.isEmpty()) {
            content.put("FINALIZADO", finalizado);
        }
        return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + idPedido);
    }
    public int update(WebPedido webPedido) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();

        try {
            content.put("ID_WEB_PEDIDO", webPedido.getId_web_pedido());
            content.put("ID_EMPRESA", webPedido.getId_empresa());
            if (webPedido.getCadastro().getId_cadastro_servidor() <= 0) {
                content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro());
                content.put("ID_PEDIDO_APP", webPedido.getId_pedido_app().substring(0, 3) + String.format("%08d", Integer.parseInt(webPedido.getId_web_pedido())) + String.format("%08d", webPedido.getCadastro().getId_cadastro()) + webPedido.getId_pedido_app().substring(19));
                content.put("CLIENTE_NOVO", "S");
            } else {
                content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro_servidor());
                content.put("ID_PEDIDO_APP", webPedido.getId_pedido_app().substring(0, 3) + String.format("%08d", Integer.parseInt(webPedido.getId_web_pedido())) + String.format("%08d", webPedido.getCadastro().getId_cadastro_servidor()) + webPedido.getId_pedido_app().substring(19));
                content.put("CLIENTE_NOVO", "N");
            }
            content.put("ID_VENDEDOR", webPedido.getId_vendedor());
            content.put("ID_CONDICAO_PAGAMENTO", webPedido.getId_condicao_pagamento());
            content.put("ID_OPERACAO", webPedido.getId_operacao());
            content.put("ID_TABELA", webPedido.getId_tabela());
            content.put("NOME_EXTENSO", webPedido.getCadastro().getNome_cadastro());
            content.put("DATA_EMISSAO", webPedido.getData_emissao());
            content.put("VALOR_PRODUTOS", webPedido.getValor_produtos());
            content.put("VALOR_DESCONTO", webPedido.getValor_desconto());
            content.put("VALOR_DESCONTO_ADD", webPedido.getValor_desconto_add());
            content.put("DESCONTO_PER", webPedido.getDesconto_per());
            content.put("DESCONTO_PER_ADD", webPedido.getDesconto_per_add());
            content.put("VALOR_TOTAL", webPedido.getValor_total());
            content.put("EXCLUIDO", webPedido.getExcluido());
            content.put("EXCLUIDO_USUARIO_ID", webPedido.getExcluido_usuario_id());
            content.put("EXCLUIDO_USUARIO_NOME", webPedido.getExcluido_usuario_nome());
            content.put("EXCLUIDO_USUARIO_DATA", webPedido.getExcluido_usuario_data());
            content.put("JUSTIFICATIVA_EXCLUSAO", webPedido.getJustificativa_exclusao());
            content.put("USUARIO_LANCAMENTO_ID", webPedido.getUsuario_lancamento_id());
            content.put("USUARIO_LANCAMENTO_NOME", webPedido.getUsuario_lancamento_nome());
            content.put("USUARIO_LANCAMENTO_DATA", webPedido.getUsuario_lancamento_data());
            content.put("OBSERVACOES", webPedido.getObservacoes());
            content.put("STATUS", webPedido.getStatus());
            content.put("ID_PEDIDO_VENDA", webPedido.getId_pedido_venda());
            content.put("ID_NOTA_FISCAL", webPedido.getId_nota_fiscal());
            content.put("ID_TABELA_PRECO_FAIXA", webPedido.getId_tabela_preco_faixa());
            content.put("PONTOS_TOTAL", webPedido.getPontos_total());
            content.put("PONTOS_COEFICIENTE", webPedido.getPontos_coeficiente());
            content.put("PONTOS_COR", webPedido.getPontos_cor());
            content.put("COMISSAO_PERCENTUAL", webPedido.getComissao_percentual());
            content.put("COMISSAO_VALOR", webPedido.getComissao_valor());
            content.put("ID_FAIXA_FINAL", webPedido.getId_faixa_final());
            content.put("VALOR_BONUS_CREDOR", webPedido.getValor_bonus_credor());
            content.put("PERC_BONUS_CREDOR", webPedido.getPerc_bonus_credor());
            content.put("DATA_PREV_ENTREGA", webPedido.getData_prev_entrega());
            content.put("PEDIDO_ENVIADO", webPedido.getPedido_enviado());
            content.put("ID_WEB_PEDIDO_SERVIDOR", webPedido.getId_web_pedido_servidor());
            content.put("FINALIZADO", webPedido.getFinalizado());
            content.put("ID_MOEDA_PADRAO", webPedido.getId_moeda_padrao());
            content.put("DESCONTO_INDEVIDO", webPedido.getDescontoIndevido());
            content.put("PRAZO_CONFIRMADO", webPedido.getPrazo_confirmado());
            content.put("VALOR_ICMS_ST", webPedido.getValor_icms_st());
            content.put("VALOR_ICMS_ST", webPedido.getValor_icms_st());
            try {
                content.put("VALOR_RECIBO", webPedido.getValor_recibo().replaceAll("\\s+", "").replace(",", "."));
            } catch (NullPointerException | NumberFormatException e) {
                content.put("VALOR_RECIBO", webPedido.getValor_recibo());
            } catch (Exception e) {
                content.put("VALOR_RECIBO", webPedido.getValor_recibo());
            }
            try {
                content.put("VALOR_DESCONTO_ADIC", webPedido.getValor_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch (NullPointerException | NumberFormatException e) {
                content.put("VALOR_DESCONTO_ADIC", webPedido.getValor_desconto_adic());
            } catch (Exception e) {
                content.put("VALOR_DESCONTO_ADIC", webPedido.getValor_desconto_adic());
            }
            try {
                content.put("PERC_DESCONTO_ADIC", webPedido.getPerc_desconto_adic().replaceAll("\\s+", "").replace(",", "."));
            } catch (NullPointerException | NumberFormatException e) {
                content.put("PERC_DESCONTO_ADIC", webPedido.getPerc_desconto_adic());
            } catch (Exception e) {
                content.put("PERC_DESCONTO_ADIC", webPedido.getPerc_desconto_adic());
            }
            //System.gc();
            try {
                content.put("QTDE_ITENS", webPedido.getQtde_itens());
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
            content.put("ID_ANALISE_APP", webPedido.getId_analise_app() == null ? "" : webPedido.getId_analise_app());
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedido.getId_web_pedido());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateDescontoIndevido(WebPedido webPedido, String desconto_indevido) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_WEB_PEDIDO", webPedido.getId_web_pedido());
            content.put("DESCONTO_INDEVIDO", desconto_indevido);
            //System.gc();
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedido.getId_web_pedido());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int updateaAnaliseEstoque(WebPedido webPedido) {
        ContentValues content = new ContentValues();
        try {
            content.put("ANALISE_ESTOQ_EFETUADA", webPedido.getAnalise_estoq_efetuada());
            content.put("ANALISE_ESTOQ_DATA_HORA", webPedido.getAnalise_estoque_data_hora());
            //System.gc();
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedido.getId_web_pedido());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int updateClientePedido(int id_cadastro_antigo, int id_cadastro_servidor, String nomeCadastro) {

        ContentValues content = new ContentValues();
        try {
            content.put("ID_CADASTRO", id_cadastro_servidor);
            content.put("CLIENTE_NOVO", "N");
            content.put("NOME_EXTENSO", nomeCadastro);
            //System.gc();
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_CADASTRO = '" + id_cadastro_antigo + "' AND PEDIDO_ENVIADO = 'N'");
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateEmpresaPedido(int idPedido, String idEmpresa) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_EMPRESA", idEmpresa);
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = '" + idPedido + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public boolean delete(WebPedido pedido) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        try {
            if (db.deleteDados("TBL_WEB_PEDIDO", "ID_WEB_PEDIDO=?", new String[]{pedido.getId_web_pedido()}) >= 1)
                db.deleteDados("TBL_WEB_PEDIDO_ITENS", "ID_PEDIDO =?", new String[]{pedido.getId_web_pedido()});
            else
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean addLixeira(WebPedido pedido) {
        try {
            ContentValues content = new ContentValues();
            content.put("EXCLUIDO", pedido.getExcluido());
            //System.gc();
            if (db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = '" + pedido.getId_web_pedido() + "'") >= 1) {
                try {
                    int idPed = Integer.parseInt(pedido.getId_web_pedido());
                    new WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, pedido.getId_pedido_app(), "S");
                    new WebAnaliseDAO(db).atualizarExcluidoPorPedido(idPed, pedido.getId_pedido_app(), "S");
                } catch (Exception ignored) {}
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    public boolean removeLixeira(WebPedido pedido) {
        try {
            ContentValues content = new ContentValues();
            content.put("EXCLUIDO", pedido.getExcluido());
            //System.gc();
            if (db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = '" + pedido.getId_web_pedido() + "'") >= 1) {
                try {
                    int idPed = Integer.parseInt(pedido.getId_web_pedido());
                    new WebVisitaDAO(db).atualizarExcluidoPorPedido(idPed, pedido.getId_pedido_app(), "N");
                    new WebAnaliseDAO(db).atualizarExcluidoPorPedido(idPed, pedido.getId_pedido_app(), "N");
                } catch (Exception ignored) {}
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public List<WebPedido> getLista(String SQL) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        List<WebPedido> lista = new ArrayList<>();
        List<Cliente> listaCliente = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        ClienteDAO clienteDAO = new ClienteDAO(db);
        do {
            WebPedido webPedido = new WebPedido();
            try {
                webPedido.setId_web_pedido(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO")));
                webPedido.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                webPedido.setCliente_novo(cursor.getString(cursor.getColumnIndex("CLIENTE_NOVO")));
                try {
                    if (webPedido.getCliente_novo().equalsIgnoreCase("S")) {
                        listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO = " + cursor.getString(cursor.getColumnIndex("ID_CADASTRO"))
                                + " AND (ID_CADASTRO_SERVIDOR <= 0 OR ID_CADASTRO_SERVIDOR IS NULL) ");
                    } else {
                        listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO_SERVIDOR = " + cursor.getString(cursor.getColumnIndex("ID_CADASTRO")));
                    }
                } catch (NullPointerException e) {
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE (ID_CADASTRO_SERVIDOR = "
                            + cursor.getString(cursor.getColumnIndex("ID_CADASTRO")) + " AND FINALIZADO <> 'N' AND F_CLIENTE <> 'S' " /* AND ATIVO = 'S'*/ + "AND F_VENDEDOR = 'N') OR (ID_CADASTRO = "
                            + cursor.getString(cursor.getColumnIndex("ID_CADASTRO")) + " AND (ID_CADASTRO_SERVIDOR <= 0 OR ID_CADASTRO_SERVIDOR IS NULL) )");
                }
                if (listaCliente.size() > 0) {
                    webPedido.setCadastro(listaCliente.get(0));
                } else {
                    listaCliente = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO_SERVIDOR = " + cursor.getString(cursor.getColumnIndex("ID_CADASTRO")));
                    if (listaCliente.size() > 0) {
                        webPedido.setCadastro(listaCliente.get(0));
                    } else {
                        if (cursor.getString(cursor.getColumnIndex("ID_CADASTRO")).equalsIgnoreCase("0")) {
                            Cliente cliente = new Cliente();
                            cliente.setIdCategoria(1);
                            cliente.setId_cadastro_servidor(0);
                            cliente.setId_cadastro(0);
                            cliente.setNome_cadastro("CLIENTE NÃO INFORMADO");

                            cliente.setNome_fantasia("-");
                            EmpresaParametro empresaParametro = new EmpresaParametro();
                            EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
                            empresaParametro = empresaParametroDAO.getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO").get(0);
                            cliente.setTipo_tabela_preco(empresaParametro.getTipo_tabela_preco());
                            cliente.setUtiliza_tabela_preco(empresaParametro.getUtiliza_tabela_preco());
                            webPedido.setCadastro(cliente);
                        } else {
                            Cliente cliente = new Cliente();
                            cliente.setIdCategoria(1);
                            cliente.setId_cadastro_servidor(0);
                            cliente.setId_cadastro(0);
                            cliente.setNome_cadastro("CLIENTE NÃO LOCALIZADO");

                            cliente.setNome_fantasia("-");
                            EmpresaParametro empresaParametro = new EmpresaParametro();
                            EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
                            empresaParametro = empresaParametroDAO.getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO").get(0);
                            cliente.setTipo_tabela_preco(empresaParametro.getTipo_tabela_preco());
                            cliente.setUtiliza_tabela_preco(empresaParametro.getUtiliza_tabela_preco());
                            webPedido.setCadastro(cliente);
                        }
                    }
                }
                webPedido.setId_vendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                webPedido.setId_condicao_pagamento(cursor.getString(cursor.getColumnIndex("ID_CONDICAO_PAGAMENTO")));
                webPedido.setId_operacao(cursor.getString(cursor.getColumnIndex("ID_OPERACAO")));
                webPedido.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
                webPedido.setNome_extenso(cursor.getString(cursor.getColumnIndex("NOME_EXTENSO")));
                webPedido.setData_emissao(cursor.getString(cursor.getColumnIndex("DATA_EMISSAO")));
                webPedido.setValor_produtos(cursor.getString(cursor.getColumnIndex("VALOR_PRODUTOS")));
                webPedido.setValor_desconto(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO")));
                webPedido.setValor_desconto_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_ADD")));
                webPedido.setDesconto_per(cursor.getString(cursor.getColumnIndex("DESCONTO_PER")));
                webPedido.setDesconto_per_add(cursor.getString(cursor.getColumnIndex("DESCONTO_PER_ADD")));
                webPedido.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                webPedido.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
                webPedido.setExcluido_usuario_id(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_ID")));
                webPedido.setExcluido_usuario_nome(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_NOME")));
                webPedido.setExcluido_usuario_data(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_DATA")));
                webPedido.setJustificativa_exclusao(cursor.getString(cursor.getColumnIndex("JUSTIFICATIVA_EXCLUSAO")));
                webPedido.setUsuario_lancamento_id(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_ID")));
                webPedido.setUsuario_lancamento_nome(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_NOME")));
                webPedido.setUsuario_lancamento_data(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA")));
                webPedido.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
                webPedido.setStatus(cursor.getString(cursor.getColumnIndex("STATUS")));
                webPedido.setId_pedido_venda(cursor.getString(cursor.getColumnIndex("ID_PEDIDO_VENDA")));
                webPedido.setId_nota_fiscal(cursor.getString(cursor.getColumnIndex("ID_NOTA_FISCAL")));
                webPedido.setId_tabela_preco_faixa(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA")));
                webPedido.setPontos_total(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL")));
                webPedido.setPontos_coeficiente(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE")));
                webPedido.setPontos_cor(cursor.getString(cursor.getColumnIndex("PONTOS_COR")));
                webPedido.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
                webPedido.setComissao_valor(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR")));
                webPedido.setId_faixa_final(cursor.getString(cursor.getColumnIndex("ID_FAIXA_FINAL")));
                webPedido.setValor_bonus_credor(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR")));
                webPedido.setPerc_bonus_credor(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR")));
                webPedido.setId_web_pedido_servidor(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO_SERVIDOR")));
                webPedido.setData_prev_entrega(cursor.getString(cursor.getColumnIndex("DATA_PREV_ENTREGA")));
                webPedido.setPedido_enviado(cursor.getString(cursor.getColumnIndex("PEDIDO_ENVIADO")));
                webPedido.setFinalizado(cursor.getString(cursor.getColumnIndex("FINALIZADO")));
                //Novos campos
                webPedido.setId_pedido(cursor.getString(cursor.getColumnIndex("ID_PEDIDO")));
                webPedido.setWeb_importado_data(cursor.getString(cursor.getColumnIndex("WEB_IMPORTADO_DATA")));
                webPedido.setAnalise_credito(cursor.getString(cursor.getColumnIndex("ANALISE_CREDITO")));
                webPedido.setAnalise_usuario_id(cursor.getString(cursor.getColumnIndex("ANALISE_USUARIO_ID")));
                webPedido.setAnalise_usuario_nome(cursor.getString(cursor.getColumnIndex("ANALISE_USUARIO_NOME")));
                webPedido.setAnalise_usuario_data(cursor.getString(cursor.getColumnIndex("ANALISE_USUARIO_DATA")));
                webPedido.setOrdem_carregamento(cursor.getString(cursor.getColumnIndex("ORDEM_CARREGAMENTO")));
                webPedido.setId_ordem_carregamento(cursor.getString(cursor.getColumnIndex("ID_ORDEM_CARREGAMENTO")));
                webPedido.setId_transportadora(cursor.getString(cursor.getColumnIndex("ID_TRANSPORTADORA")));
                webPedido.setNome_transportadora(cursor.getString(cursor.getColumnIndex("NOME_TRANSPORTADORA")));
                webPedido.setNumero_nota_fiscal(cursor.getString(cursor.getColumnIndex("NUMERO_NOTA_FISCAL")));
                webPedido.setData_nota_fiscal(cursor.getString(cursor.getColumnIndex("DATA_NOTA_FISCAL")));
                webPedido.setEspecie(cursor.getString(cursor.getColumnIndex("ESPECIE")));
                webPedido.setVolumes(cursor.getString(cursor.getColumnIndex("VOLUMES")));
                webPedido.setWeb_valor_total(cursor.getString(cursor.getColumnIndex("WEB_VALOR_TOTAL")));
                webPedido.setExpedicao_status(cursor.getString(cursor.getColumnIndex("EXPEDICAO_STATUS")));
                webPedido.setValor_faturado(cursor.getString(cursor.getColumnIndex("VALOR_FATURADO")));
                webPedido.setId_moeda_padrao(cursor.getString(cursor.getColumnIndex("ID_MOEDA_PADRAO")));
                webPedido.setId_outro_vendedor(cursor.getString(cursor.getColumnIndex("ID_OUTRO_VENDEDOR")));
                webPedido.setId_pedido_app_fdb(cursor.getString(cursor.getColumnIndex("ID_PEDIDO_APP_FDB")));
                webPedido.setAnalise_estoq_efetuada(cursor.getString(cursor.getColumnIndex("ANALISE_ESTOQ_EFETUADA")));
                webPedido.setAnalise_estoque_data_hora(cursor.getString(cursor.getColumnIndex("ANALISE_ESTOQ_DATA_HORA")));
                try {
                    webPedido.setValor_recibo(cursor.getString(cursor.getColumnIndex("VALOR_RECIBO")));
                } catch (NullPointerException e) {
                    webPedido.setValor_recibo("0.00");
                    e.printStackTrace();
                } catch (Exception e) {
                    webPedido.setValor_recibo("0.00");
                    e.printStackTrace();
                }
                try {
                    webPedido.setValor_icms_st(cursor.getString(cursor.getColumnIndex("VALOR_ICMS_ST")));
                } catch (NullPointerException e) {
                    webPedido.setValor_icms_st("0.00");
                } catch (Exception e) {
                    webPedido.setValor_icms_st("0.00");
                }
                try {
                    if (cursor.getString(cursor.getColumnIndex("DESCONTO_INDEVIDO")).isEmpty() || cursor.getString(cursor.getColumnIndex("DESCONTO_INDEVIDO")) == null)
                        webPedido.setDescontoIndevido("N");
                    else
                        webPedido.setDescontoIndevido(cursor.getString(cursor.getColumnIndex("DESCONTO_INDEVIDO")));
                } catch (NullPointerException e) {
                    webPedido.setDescontoIndevido("N");
                } catch (Exception e) {
                    webPedido.setDescontoIndevido("N");
                }

                try {
                    if (cursor.getString(cursor.getColumnIndex("ID_PEDIDO_APP")).isEmpty() || cursor.getString(cursor.getColumnIndex("ID_PEDIDO_APP")) == null)
                        webPedido.setId_pedido_app("");
                    else
                        webPedido.setId_pedido_app(cursor.getString(cursor.getColumnIndex("ID_PEDIDO_APP")));
                } catch (NullPointerException e) {
                    webPedido.setId_pedido_app("");
                } catch (Exception e) {
                    webPedido.setId_pedido_app("");
                }

                try {
                    if (cursor.getString(cursor.getColumnIndex("PRAZO_CONFIRMADO")).isEmpty() || cursor.getString(cursor.getColumnIndex("PRAZO_CONFIRMADO")) == null)
                        webPedido.setPrazo_confirmado("N");
                    else
                        webPedido.setPrazo_confirmado(cursor.getString(cursor.getColumnIndex("PRAZO_CONFIRMADO")));
                } catch (NullPointerException e) {
                    webPedido.setPrazo_confirmado("N");
                } catch (Exception e) {
                    webPedido.setPrazo_confirmado("N");
                }

                try {
                    webPedido.setValor_desconto_adic(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_ADIC")));
                    webPedido.setPerc_desconto_adic(cursor.getString(cursor.getColumnIndex("PERC_DESCONTO_ADIC")));
                } catch (NullPointerException e) {
                    webPedido.setValor_desconto_adic("0.00");
                    webPedido.setPerc_desconto_adic("0.00");
                } catch (Exception e) {
                    webPedido.setValor_desconto_adic("0.00");
                    webPedido.setPerc_desconto_adic("0.00");
                }
                try {
                    webPedido.setQtde_itens(cursor.getString(cursor.getColumnIndex("QTDE_ITENS")));
                } catch ( NullPointerException e) {

                }
                try {
                    int idxAnalise = cursor.getColumnIndex("ID_ANALISE_APP");
                    if (idxAnalise != -1 && cursor.getString(idxAnalise) != null) {
                        webPedido.setId_analise_app(cursor.getString(idxAnalise));
                    }
                } catch (Exception ignored) {}
                webPedido.setFaturado(cursor.getString(cursor.getColumnIndex("FATURADO")));
                lista.add(webPedido);
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }
        } while (cursor.moveToNext());
        cursor.close();
        //System.gc();
        return lista;
    }

    public int getTotalReg(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return 0;
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("TOTALREG"));
    }

    public boolean deleteAll() {
        List<WebPedido> lista = getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S'");
        for (int i = 0; i < lista.size(); i++) {
            try {
                db.deleteDados("TBL_WEB_PEDIDO", "ID_WEB_PEDIDO = ?", new String[]{lista.get(i).getId_web_pedido()});
                db.deleteDados("TBL_WEB_PEDIDO_ITENS", "ID_PEDIDO = ?", new String[]{lista.get(i).getId_web_pedido()});
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("Range")
    public List<WebPedidoAnalise> getListaAnalise(String SQL) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        List<WebPedidoAnalise> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            WebPedidoAnalise webPedido = new WebPedidoAnalise();
            try {
                webPedido.setId_pedido(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO_SERVIDOR")));
                webPedido.setId_pedido_app(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO")));

                lista.add(webPedido);
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }
        } while (cursor.moveToNext());
        cursor.close();
        //System.gc();
        return lista;
    }

    public int updatePedidCanceladoExcluido(WebPedidoAnalise webPedidoAnalise) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_PEDIDO_VENDA", webPedidoAnalise.getId_pedido_venda());
            content.put("ANALISE_CREDITO", webPedidoAnalise.getAnalise_credito());
            content.put("EXCLUIDO", webPedidoAnalise.getExcluido());
            content.put("EXCLUIDO_USUARIO_ID", webPedidoAnalise.getExcluido_usuario_id());
            content.put("EXCLUIDO_USUARIO_NOME", webPedidoAnalise.getExcluido_usuario_nome());
            content.put("EXCLUIDO_USUARIO_DATA", webPedidoAnalise.getExcluido_usuario_data());
            content.put("JUSTIFICATIVA_EXCLUSAO", webPedidoAnalise.getJustificativa_exclusao());
            //System.gc();
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedidoAnalise.getId_pedido_app());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updatePedidoFaturado(WebPedidoAnalise webPedidoAnalise) {
        ContentValues content = new ContentValues();
        try {
            content.put("ANALISE_CREDITO", webPedidoAnalise.getAnalise_credito());
            content.put("FATURADO", webPedidoAnalise.getFaturado());
            content.put("ID_NOTA_FISCAL", webPedidoAnalise.getId_nota_fiscal());
            content.put("ID_PEDIDO_VENDA", webPedidoAnalise.getId_pedido_venda());
            content.put("NUMERO_NOTA_FISCAL", webPedidoAnalise.getNumero_nota_fiscal());
            //System.gc();
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedidoAnalise.getId_pedido_app());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int  updatePedidoAnaliseCredito(WebPedidoAnalise webPedidoAnalise) {
        ContentValues content = new ContentValues();
        try {
            content.put("ANALISE_CREDITO", webPedidoAnalise.getAnalise_credito());
            content.put("FATURADO", webPedidoAnalise.getFaturado());
            content.put("ID_PEDIDO_VENDA", webPedidoAnalise.getId_pedido_venda());
            //System.gc();
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedidoAnalise.getId_pedido_app());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int  updatePedidoImportado(WebPedidoAnalise webPedidoAnalise) {  
        if ( webPedidoAnalise.getId_pedido_venda() == null)
            return 0;
        ContentValues content = new ContentValues();
        try {
            content.put("ID_PEDIDO_VENDA", webPedidoAnalise.getId_pedido_venda());
            //System.gc();
            return db.updateDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedidoAnalise.getId_pedido_app());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addListaWebPedidos(List<WebPedido> lista, DBHelper db) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT_WEB_PEDIDO);
        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
            try {
                database.beginTransaction();
                for (WebPedido webPedido : lista) {
                    stmt.clearBindings();

                    // 1 a 5
                    stmt.bindString(1, webPedido.getId_empresa());
                    if (webPedido.getCadastro().getId_cadastro_servidor() <= 0) {
                        stmt.bindLong(2, webPedido.getCadastro().getId_cadastro());
                        stmt.bindString(3, "S"); // CLIENTE_NOVO
                    } else {
                        stmt.bindLong(2, webPedido.getCadastro().getId_cadastro_servidor());
                        stmt.bindString(3, "N"); // CLIENTE_NOVO
                    }
                    stmt.bindString(4, webPedido.getId_vendedor());
                    stmt.bindString(5, webPedido.getId_condicao_pagamento());

                    // 6 a 10
                    stmt.bindString(6, webPedido.getId_operacao());
                    stmt.bindString(7, webPedido.getId_tabela());
                    stmt.bindString(8, webPedido.getCadastro().getNome_cadastro() == null ? "CADASTRO NAO LOCALIZADO" : webPedido.getCadastro().getNome_cadastro());
                    stmt.bindString(9, webPedido.getData_emissao());
                    stmt.bindString(10, webPedido.getValor_produtos());

                    // 11 a 15
                    stmt.bindString(11, webPedido.getValor_desconto());
                    stmt.bindString(12, webPedido.getValor_desconto_add() == null ? "0.00" : webPedido.getValor_desconto_add());
                    stmt.bindString(13, webPedido.getDesconto_per() == null ? "0.00" : webPedido.getDesconto_per());
                    stmt.bindString(14, webPedido.getDesconto_per_add() == null ? "0.00" : webPedido.getDesconto_per_add());
                    stmt.bindString(15, webPedido.getValor_total() == null ? "0.00" : webPedido.getValor_total());

                    // 16 a 20
                    stmt.bindString(16, webPedido.getExcluido());
                    stmt.bindString(17, webPedido.getExcluido_usuario_id() == null ? "0" : webPedido.getExcluido_usuario_id());
                    stmt.bindString(18, webPedido.getExcluido_usuario_nome() == null ? "" : webPedido.getExcluido_usuario_nome());
                    stmt.bindString(19, webPedido.getExcluido_usuario_data() == null ? "" : webPedido.getExcluido_usuario_data());
                    stmt.bindString(20, webPedido.getJustificativa_exclusao() == null ? "" :webPedido.getJustificativa_exclusao()) ;

                    // 21 a 25
                    stmt.bindString(21, webPedido.getUsuario_lancamento_id());
                    stmt.bindString(22, webPedido.getUsuario_lancamento_nome());
                    stmt.bindString(23, webPedido.getUsuario_lancamento_data());
                    stmt.bindString(24, webPedido.getObservacoes() == null ? "" : webPedido.getObservacoes());
                    stmt.bindString(25, webPedido.getStatus() == null ? "" : webPedido.getStatus());

                    // 26 a 30
                    stmt.bindString(26, webPedido.getId_pedido_venda() == null ? "0" : webPedido.getId_pedido_venda());
                    stmt.bindString(27, webPedido.getId_nota_fiscal() == null ? "0" : webPedido.getId_nota_fiscal());
                    stmt.bindString(28, webPedido.getId_tabela_preco_faixa() == null ? "0" : webPedido.getId_tabela_preco_faixa());
                    stmt.bindString(29, webPedido.getPontos_total() ==  null ? "0" : webPedido.getPontos_total());
                    stmt.bindString(30, webPedido.getPontos_coeficiente() == null ? "0" : webPedido.getPontos_coeficiente());

                    // 31 a 35
                    stmt.bindString(31, webPedido.getPontos_cor()== null ? "0" : webPedido.getPontos_cor());
                    stmt.bindString(32, webPedido.getComissao_percentual() == null ? "0" : webPedido.getComissao_percentual());
                    stmt.bindString(33, webPedido.getComissao_valor() == null ? "0" : webPedido.getComissao_valor());
                    stmt.bindString(34, webPedido.getId_faixa_final() == null ? "0" : webPedido.getId_faixa_final());
                    stmt.bindString(35, webPedido.getValor_bonus_credor() == null ? "0" : webPedido.getValor_bonus_credor());

                    // 36 a 40
                    stmt.bindString(36, webPedido.getPerc_bonus_credor() == null ? "0" : webPedido.getPerc_bonus_credor());
                    stmt.bindString(37, webPedido.getData_prev_entrega() == null ? "" : webPedido.getData_prev_entrega());
                    stmt.bindString(38, webPedido.getId_web_pedido_servidor());
                    stmt.bindString(39, webPedido.getPedido_enviado());
                    stmt.bindString(40, webPedido.getFinalizado() );

                    // 41 a 45
                    stmt.bindString(41, webPedido.getId_pedido() == null ? "" : webPedido.getId_pedido());
                    stmt.bindString(42, webPedido.getWeb_importado_data() == null ? "" : webPedido.getWeb_importado_data());
                    stmt.bindString(43, webPedido.getAnalise_credito() == null ? "" : webPedido.getAnalise_credito());
                    stmt.bindString(44, webPedido.getAnalise_usuario_id() == null ? "0" : webPedido.getAnalise_usuario_id());
                    stmt.bindString(45, webPedido.getAnalise_usuario_nome() == null ? "" : webPedido.getAnalise_usuario_nome());

                    // 46 a 50
                    stmt.bindString(46, webPedido.getAnalise_usuario_data() == null ? "" : webPedido.getAnalise_usuario_data());
                    stmt.bindString(47, webPedido.getOrdem_carregamento() == null ? "" : webPedido.getOrdem_carregamento());
                    stmt.bindString(48, webPedido.getId_ordem_carregamento() == null ? "0" : webPedido.getId_ordem_carregamento());
                    stmt.bindString(49, webPedido.getId_transportadora() == null ? "0" : webPedido.getId_transportadora());
                    stmt.bindString(50, webPedido.getNome_transportadora() == null ? "" : webPedido.getNome_transportadora());

                    // 51 a 55
                    stmt.bindString(51, webPedido.getNumero_nota_fiscal() == null ? "" : webPedido.getNumero_nota_fiscal());
                    stmt.bindString(52, webPedido.getData_nota_fiscal() == null ? "" : webPedido.getData_nota_fiscal());
                    stmt.bindString(53, webPedido.getEspecie() == null ? "" : webPedido.getEspecie());
                    stmt.bindString(54, webPedido.getVolumes() == null ? "0" : webPedido.getVolumes());
                    stmt.bindString(55, webPedido.getWeb_valor_total() == null ? "0" : webPedido.getWeb_valor_total());

                    // 56 a 60
                    stmt.bindString(56, webPedido.getExpedicao_status() ==  null ? "0" : webPedido.getExpedicao_status());
                    stmt.bindString(57, webPedido.getValor_faturado() == null ? "0" : webPedido.getValor_faturado());
                    stmt.bindString(58, webPedido.getValor_icms_st() == null ? "0" : webPedido.getValor_icms_st());
                    stmt.bindString(59, webPedido.getFaturado() == null ? "" : webPedido.getFaturado());
                    stmt.bindString(60, webPedido.getId_outro_vendedor() == null ? webPedido.getId_vendedor() : webPedido.getId_outro_vendedor());

                    // 61: VALOR_RECIBO (Tratamento de String/Double)
                    String vRecibo = webPedido.getValor_recibo();
                    try {
                        if (vRecibo != null) vRecibo = vRecibo.replaceAll("\\s+", "").replace(",", ".");
                    } catch (Exception e) {}
                    if (vRecibo == null) stmt.bindNull(61); else stmt.bindString(61, vRecibo);

                    // 62: ID_MOEDA_PADRAO
                    String moeda = "1";
                    try {
                        if (webPedido.getId_moeda_padrao() != null && !webPedido.getId_moeda_padrao().isEmpty()) {
                            moeda = webPedido.getId_moeda_padrao();
                        }
                    } catch (Exception e) {}
                    stmt.bindString(62, moeda);

                    // 63 a 65
                    stmt.bindString(63, "N"); // DESCONTO_INDEVIDO
                    stmt.bindString(64, webPedido.getId_pedido_app() == null ? "" : webPedido.getId_pedido_app());

                    String prazo = "N";
                    if (webPedido.getPrazo_confirmado() != null) prazo = webPedido.getPrazo_confirmado();
                    stmt.bindString(65, prazo);

                    // 66: VALOR_DESCONTO_ADIC
                    String vDescAdic = webPedido.getValor_desconto_adic();
                    try {
                        if (vDescAdic != null) vDescAdic = vDescAdic.replaceAll("\\s+", "").replace(",", ".");
                    } catch (Exception e) {}
                    if (vDescAdic == null) stmt.bindNull(66); else stmt.bindString(66, vDescAdic);

                    // 67: PERC_DESCONTO_ADIC
                    String pDescAdic = webPedido.getPerc_desconto_adic();
                    try {
                        if (pDescAdic != null) pDescAdic = pDescAdic.replaceAll("\\s+", "").replace(",", ".");
                    } catch (Exception e) {}
                    if (pDescAdic == null) stmt.bindNull(67); else stmt.bindString(67, pDescAdic);

                    // 68 a 70
                    stmt.bindString(68, webPedido.getQtde_itens() == null ? "0" : webPedido.getQtde_itens());
                    stmt.bindString(69, webPedido.getAnalise_estoq_efetuada() == null ? "N" : webPedido.getAnalise_estoq_efetuada());
                    if (webPedido.getAnalise_estoque_data_hora() == null) stmt.bindNull(70); else stmt.bindString(70, webPedido.getAnalise_estoque_data_hora());
                    //int idPedido = (int) webPedidoDAO.add(webPedido);
                    long idPedido = stmt.executeInsert();
                    try {
                        webPedidoItensDAO.addListaWebPedidoItens(webPedido.getWebPedidoItens(), String.valueOf(idPedido), database);
                        /*
                        for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                            webPedidoItens.setId_pedido(String.valueOf(idPedido));
                            if (!webPedidoItensDAO.update(webPedidoItens))
                                if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE (ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItens.getId_produto().trim() + "') OR " +
                                        "(ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = '" + webPedidoItens.getId_produto() + "');") <= 0) {

                                    webPedidoItensDAO.add(webPedidoItens);
                                }
                        }

                         */
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                database.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.endTransaction();
            }
        }

        private static final String SQL_INSERT_WEB_PEDIDO = "INSERT INTO TBL_WEB_PEDIDO (" +
            "ID_EMPRESA, ID_CADASTRO, CLIENTE_NOVO, ID_VENDEDOR, ID_CONDICAO_PAGAMENTO, " + // 1-5
            "ID_OPERACAO, ID_TABELA, NOME_EXTENSO, DATA_EMISSAO, VALOR_PRODUTOS, " +         // 6-10
            "VALOR_DESCONTO, VALOR_DESCONTO_ADD, DESCONTO_PER, DESCONTO_PER_ADD, VALOR_TOTAL, " + // 11-15
            "EXCLUIDO, EXCLUIDO_USUARIO_ID, EXCLUIDO_USUARIO_NOME, EXCLUIDO_USUARIO_DATA, JUSTIFICATIVA_EXCLUSAO, " + // 16-20
            "USUARIO_LANCAMENTO_ID, USUARIO_LANCAMENTO_NOME, USUARIO_LANCAMENTO_DATA, OBSERVACOES, STATUS, " + // 21-25
            "ID_PEDIDO_VENDA, ID_NOTA_FISCAL, ID_TABELA_PRECO_FAIXA, PONTOS_TOTAL, PONTOS_COEFICIENTE, " + // 26-30
            "PONTOS_COR, COMISSAO_PERCENTUAL, COMISSAO_VALOR, ID_FAIXA_FINAL, VALOR_BONUS_CREDOR, " + // 31-35
            "PERC_BONUS_CREDOR, DATA_PREV_ENTREGA, ID_WEB_PEDIDO_SERVIDOR, PEDIDO_ENVIADO, FINALIZADO, " + // 36-40
            "ID_PEDIDO, WEB_IMPORTADO_DATA, ANALISE_CREDITO, ANALISE_USUARIO_ID, ANALISE_USUARIO_NOME, " + // 41-45
            "ANALISE_USUARIO_DATA, ORDEM_CARREGAMENTO, ID_ORDEM_CARREGAMENTO, ID_TRANSPORTADORA, NOME_TRANSPORTADORA, " + // 46-50
            "NUMERO_NOTA_FISCAL, DATA_NOTA_FISCAL, ESPECIE, VOLUMES, WEB_VALOR_TOTAL, " + // 51-55
            "EXPEDICAO_STATUS, VALOR_FATURADO, VALOR_ICMS_ST, FATURADO, ID_OUTRO_VENDEDOR, " + // 56-60
            "VALOR_RECIBO, ID_MOEDA_PADRAO, DESCONTO_INDEVIDO, ID_PEDIDO_APP, PRAZO_CONFIRMADO, " + // 61-65
            "VALOR_DESCONTO_ADIC, PERC_DESCONTO_ADIC, QTDE_ITENS, ANALISE_ESTOQ_EFETUADA, ANALISE_ESTOQ_DATA_HORA" + // 66-70
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}
