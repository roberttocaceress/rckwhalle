package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Deposito;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoGrupo;
import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private DBHelper db;

    public ProdutoDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Produto produto) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", produto.getAtivo());
            content.put("ID_PRODUTO", produto.getId_produto());
            content.put("ID_LINHA_COLECAO", produto.getIdLinhaColecao());
            content.put("NOME_PRODUTO", produto.getNome_produto());
            content.put("UNIDADE", produto.getUnidade());
            content.put("TIPO_CADASTRO", produto.getTipo_cadastro());
            content.put("ID_ENTIDADE", produto.getId_entidade());
            content.put("NCM", produto.getNcm());
            content.put("ID_GRUPO", produto.getId_grupo());
            content.put("ID_SUB_GRUPO", produto.getId_sub_grupo());
            content.put("PESO_BRUTO", produto.getPeso_bruto());
            content.put("PESO_LIQUIDO", produto.getPeso_liquido());
            content.put("CODIGO_EM_BARRAS", produto.getCodigo_em_barras());
            content.put("MOVIMENTA_ESTOQUE", produto.getMovimenta_estoque());
            content.put("NOME_DA_MARCA", produto.getNome_da_marca());
            content.put("ID_EMPRESA", produto.getId_empresa());
            content.put("ID_ORIGEM", produto.getId_origem());
            content.put("CUSTO_PRODUTO", produto.getCusto_produto());
            content.put("CUSTO_PER_IPI", produto.getCusto_per_ipi());
            content.put("CUSTO_IPI", produto.getCusto_ipi());
            content.put("CUSTO_PER_FRETE", produto.getCusto_per_frete());
            content.put("CUSTO_FRETE", produto.getCusto_frete());
            content.put("CUSTO_PER_ICMS", produto.getCusto_per_icms());
            content.put("CUSTO_ICMS", produto.getCusto_icms());
            content.put("CUSTO_PER_FIN", produto.getCusto_per_fin());
            content.put("CUSTO_FIN", produto.getCusto_fin());
            content.put("CUSTO_PER_SUBST", produto.getCusto_per_subst());
            content.put("CUSTO_SUBT", produto.getCusto_subt());
            content.put("CUSTO_PER_OUTROS", produto.getCusto_per_outros());
            content.put("CUSTO_OUTROS", produto.getCusto_outros());
            content.put("VALOR_CUSTO", produto.getValor_custo());
            content.put("EXCLUIDO", produto.getExcluido());
            content.put("EXCLUIDO_POR", produto.getExcluido_por());
            content.put("EXCLUIDO_POR_DATA", produto.getExcluido_por_data());
            content.put("EXCLUIDO_CODIGO_NOVO", produto.getExcluido_codigo_novo());
            content.put("AJUSTE_PRECO_DATA", produto.getAjuste_preco_data());
            content.put("AJUSTE_PRECO_NFE", produto.getAjuste_preco_nfe());
            content.put("AJUSTE_PRECO_USUARIO", produto.getAjuste_preco_usuario());
            content.put("TOTAL_CUSTO", produto.getTotal_custo());
            content.put("TOTAL_CREDITO", produto.getTotal_credito());
            content.put("VALOR_CUSTO_ESTOQUE", produto.getValor_custo_estoque());
            content.put("CUSTO_DATA_INICIAL", produto.getCusto_data_inicial());
            content.put("CUSTO_VALOR_INICIAL", produto.getCusto_valor_inicial());
            content.put("PRODUTO_VENDA", produto.getProduto_venda());
            content.put("PRODUTO_INSUMO", produto.getProduto_insumo());
            content.put("PRODUTO_CONSUMO", produto.getProduto_consumo());
            content.put("PRODUTO_PRODUCAO", produto.getProduto_producao());
            content.put("VENDA_PERC_COMISSAO", produto.getVenda_perc_comissao());
            content.put("VENDA_PRECO", produto.getVenda_preco());
            content.put("VENDA_PERC_COMISSAO_DOIS", produto.getVenda_perc_comissao_dois());
            content.put("DESCRICAO", produto.getDescricao());
            content.put("NOME_SUB_GRUPO", produto.getNome_sub_grupo());
            content.put("NOME_GRUPO", produto.getNome_grupo());
            content.put("PRODUTO_MATERIA_PRIMA", produto.getProduto_materia_prima());
            content.put("PRODUTO_TERCERIZACAO", produto.getProduto_tercerizacao());
            content.put("SALDO_ESTOQUE", produto.getSaldo_estoque());
            content.put("TRAVAR_FAT_SEM_ESTOQUE", produto.getTravar_fat_sem_estoque());
            content.put("UTILIZA_TABELA_PRECO", produto.getUtiliza_tabela_preco());
            content.put("PRODUTO_EDITA_PRECO_APP", produto.getProduto_edita_valor());
            content.put("PRODUTO_ST_ALIQUOTA", produto.getProduto_st_aliquota());
            content.put("PRODUTO_SUJ_ICMS_ST", produto.getProduto_suj_icms_st());
            System.gc();
            return db.addDados("TBL_PRODUTO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long updateSaldoEstoque(Produto produto) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("SALDO_ESTOQUE", produto.getSaldo_estoque());
            System.gc();
            return db.updateDados("TBL_PRODUTO", content, "ID_PRODUTO = '" + produto.getId_produto().trim() + "'" );
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<Produto> getLista(String SQL, String idCliente) {
        Configuracao configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        List<Produto> lista = new ArrayList<>();
        Cursor cursorAnx = null;
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        Produto produto;
        do {
            produto = new Produto();
            try {
                produto.setId_produto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
                produto.setIdLinhaColecao(cursor.getInt(cursor.getColumnIndex("ID_LINHA_COLECAO")));
                produto.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                produto.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
                produto.setUnidade(cursor.getString(cursor.getColumnIndex("UNIDADE")));
                produto.setVenda_preco(cursor.getString(cursor.getColumnIndex("VENDA_PRECO")));
                produto.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                produto.setCodigo_em_barras(cursor.getString(cursor.getColumnIndex("CODIGO_EM_BARRAS")));
                produto.setNome_sub_grupo(cursor.getString(cursor.getColumnIndex("NOME_SUB_GRUPO")));
                produto.setNome_grupo(cursor.getString(cursor.getColumnIndex("NOME_GRUPO")));
                produto.setId_sub_grupo(cursor.getString(cursor.getColumnIndex("ID_SUB_GRUPO")));
                produto.setId_grupo(cursor.getString(cursor.getColumnIndex("ID_GRUPO")));
                produto.setProduto_tercerizacao(cursor.getString(cursor.getColumnIndex("PRODUTO_TERCERIZACAO")));
                produto.setProduto_materia_prima(cursor.getString(cursor.getColumnIndex("PRODUTO_MATERIA_PRIMA")));
                produto.setSaldo_estoque(cursor.getFloat(cursor.getColumnIndex("SALDO_ESTOQUE")));
                produto.setTravar_fat_sem_estoque(cursor.getString(cursor.getColumnIndex("TRAVAR_FAT_SEM_ESTOQUE")));
                produto.setUtiliza_tabela_preco(cursor.getString(cursor.getColumnIndex("UTILIZA_TABELA_PRECO")));
                produto.setProduto_edita_valor(cursor.getString(cursor.getColumnIndex("PRODUTO_EDITA_PRECO_APP")));
                produto.setProduto_st_aliquota(cursor.getFloat(cursor.getColumnIndex("PRODUTO_ST_ALIQUOTA")));
                produto.setProduto_suj_icms_st(cursor.getString(cursor.getColumnIndex("PRODUTO_SUJ_ICMS_ST")));
                produto.setUltima_compra_cliente("");
                try {
                    produto.setUltima_compra_cliente( cursor.getString(cursor.getColumnIndex("OUTRAS_COMPRAS")) != null ? cursor.getString(cursor.getColumnIndex("OUTRAS_COMPRAS")) : "");
                } catch (NullPointerException|SQLException e) {
                } catch (Exception e) {
                }
                try {
                    produto.setW_id_produto( cursor.getString(cursor.getColumnIndex("W_ID_PRODUTO")) );
                    produto.setId_campanha_produto(cursor.getString(cursor.getColumnIndex("ID_CAMPANHA")));
                    produto.setId_campanha_cliente(cursor.getString(cursor.getColumnIndex("ID_CAMPANHA_C")));
                } catch ( NullPointerException e) {
                    produto.setW_id_produto("-");
                } catch ( Exception e) {
                    produto.setW_id_produto("-");
                }
                if (produto.getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                    try {
                        if (Float.parseFloat(cursor.getString(cursor.getColumnIndex("PRECO_TABELA_CLIENTE"))) > 0.0f) {
                            produto.setVenda_preco(cursor.getString(cursor.getColumnIndex("PRECO_TABELA_CLIENTE")));
                            produto.setNome_tabela_preco(cursor.getString(cursor.getColumnIndex("NOME_TABELA")));
                        }
                    } catch (NullPointerException e) {
                    } catch (Exception e) {
                    }
                }
                produto.setAnexo_1("");
                if ( !configuracao.getLayout_venda_erp().equalsIgnoreCase("1")) {
                    try {
                        SQL = "SELECT FOTO_MINIATURA, FOTO_ARQUIVO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" +
                                cursor.getString(cursor.getColumnIndex("ID_PRODUTO")).trim()
                                + "' AND ATIVO = 'S' AND FOTO_PRINCIPAL = 'S'";
                        cursorAnx = db.listaFotos(SQL);
                        if (cursor.getCount() > 0) {
                            if (cursorAnx.moveToNext()) {
                                produto.setAnexo_1(cursorAnx.getString(cursorAnx.getColumnIndex("FOTO_ARQUIVO")));
                            }
                        }
                        cursorAnx.close();
                    } catch (NullPointerException e) {
                    }
                }
                //produto.setAnexo_1("");
                try{
                    boolean produtoPromocao = false;
                    produto.setId_promocao("0");
                    int id_promocao = 0;
                    while ( produtoPromocao == false ) {
                        id_promocao = getPromocao1(produto, idCliente);
                        if (id_promocao > 0) {
                            produtoPromocao = true;
                            produto.setId_promocao(String.valueOf(id_promocao));
                            break;
                        }
                        id_promocao = getPromocao2(produto);
                        if (id_promocao > 0) {
                            produtoPromocao = true;
                            produto.setId_promocao(String.valueOf(id_promocao));
                            break;
                        }
                        id_promocao = getPromocao3(produto);
                        if (id_promocao > 0) {
                            produtoPromocao = true;
                            produto.setId_promocao(String.valueOf(id_promocao));
                            break;
                        }
                        id_promocao = getPromocao4(produto);
                        if (id_promocao > 0) {
                            produtoPromocao = true;
                            produto.setId_promocao(String.valueOf(id_promocao));
                            break;
                        }
                        produtoPromocao = true;
                    }
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                lista.add(produto);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            //System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        //System.gc();
        return lista;
    }

    private int getPromocao1(Produto produto, String idCliente) {
        String SQL = "SELECT PC.*, PP.* FROM TBL_PROMOCAO_CAB AS PC " +
                "INNER JOIN TBL_PROMOCAO_PRODUTO AS PP  ON ( PP.ID_PROMOCAO = PC.ID_PROMOCAO AND PP.ID_PRODUTO = '" + produto.getId_produto() + "') " +
                "INNER JOIN TBL_PROMOCAO_CLIENTE AS PCL ON ( PCL.ID_PROMOCAO = PC.ID_PROMOCAO AND PCL.ID_CADASTRO = '" + idCliente + "')" +
                "WHERE PC.APLICACAO_CLIENTE = 1 AND PC.APLICACAO_PRODUTO = 1 AND PC.ATIVO = 'S' AND PC.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice();
        Cursor cursor = db.listaPromocao(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return 0;
        }
        try {
            cursor.moveToFirst();
            int id_promocao = cursor.getInt(cursor.getColumnIndex("ID_PROMOCAO"));
            cursor.close();
            return  id_promocao;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            cursor.close();
        } catch ( NullPointerException e) {

        }
        System.gc();
        return 0;
    }
    private int getPromocao2(Produto produto){
        String SQL = "SELECT PC.*, PP.* FROM TBL_PROMOCAO_CAB AS PC INNER JOIN TBL_PROMOCAO_PRODUTO AS PP " +
                "ON ( PP.ID_PROMOCAO = PC.ID_PROMOCAO AND PP.ID_PRODUTO = '" + produto.getId_produto() + "') " +
                "WHERE PC.APLICACAO_CLIENTE = 0 AND PC.APLICACAO_PRODUTO = 1 AND PC.ATIVO = 'S' AND PC.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice();
        Cursor cursor = db.listaPromocao(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return 0;
        }
        try {
            cursor.moveToFirst();
            int id_promocao = cursor.getInt(cursor.getColumnIndex("ID_PROMOCAO"));
            cursor.close();
            return  id_promocao;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            cursor.close();
        } catch ( NullPointerException e) {

        }
        System.gc();
        return 0;
    }

    private int getPromocao3(Produto produto){
        String SQL = "SELECT PC.*, PP.* FROM TBL_PROMOCAO_CAB AS PC INNER JOIN TBL_PROMOCAO_PRODUTO AS PP " +
                "ON ( PP.ID_PROMOCAO = PC.ID_PROMOCAO AND PP.ID_PRODUTO = '" + produto.getId_produto() + "') " +
                "WHERE PC.APLICACAO_CLIENTE = 1 AND PC.APLICACAO_PRODUTO = 0 AND PC.ATIVO = 'S' AND PC.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice();
        Cursor cursor = db.listaPromocao(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return 0;
        }
        try {
            cursor.moveToFirst();
            int id_promocao = cursor.getInt(cursor.getColumnIndex("ID_PROMOCAO"));
            cursor.close();
            return  id_promocao;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            cursor.close();
        } catch ( NullPointerException e) {

        }
        System.gc();
        return  0;
    }

    private int getPromocao4(Produto produto){
        String SQL = "SELECT PC.*, PP.* FROM TBL_PROMOCAO_CAB AS PC INNER JOIN TBL_PROMOCAO_PRODUTO AS PP " +
                "ON ( PP.ID_PROMOCAO = PC.ID_PROMOCAO AND PP.ID_PRODUTO = '" + produto.getId_produto() + "') " +
                "WHERE PC.APLICACAO_CLIENTE = 0 AND PC.APLICACAO_PRODUTO = 0 AND PC.ATIVO = 'S' AND PC.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice();
        Cursor cursor = db.listaPromocao(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return 0;
        }
        try {
            cursor.moveToFirst();
            int id_promocao = cursor.getInt(cursor.getColumnIndex("ID_PROMOCAO"));
            cursor.close();
            return  id_promocao;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            cursor.close();
        } catch ( NullPointerException e) {

        }
        System.gc();
        return 0;
    }

    public int getTotalReg(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();
        int totalreg = cursor.getInt(cursor.getColumnIndex("TOTALREG"));
        cursor.close();
        return totalreg;
    }

    public long add(ProdutoFoto produtoFoto) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", produtoFoto.getAtivo());
            content.put("ID_PRODUTO", produtoFoto.getId_produto());
            content.put("ID_FOTO", produtoFoto.getId_foto());
            content.put("USUARIO_DATA", produtoFoto.getUsuario_data());
            content.put("FOTO_ARQUIVO", produtoFoto.getFoto_arquivo());
            content.put("FOTO_MINIATURA", produtoFoto.getFoto_miniatura());
            content.put("FOTO_PRINCIPAL", produtoFoto.getFoto_principal());
            System.gc();
            return db.addDados("TBL_PRODUTO_FOTO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long updateFoto(ProdutoFoto produtoFoto) {
            ContentValues content = new ContentValues();
            try {
                content.put("FOTO_ARQUIVO"   , produtoFoto.getFoto_arquivo());
                content.put("FOTO_MINIATURA" , produtoFoto.getFoto_miniatura());
                content.put("FOTO_PRINCIPAL" , produtoFoto.getFoto_principal());
                System.gc();
                return db.updateDados("TBL_PRODUTO_FOTO", content, "ID_FOTO = '" + produtoFoto.getId_foto().trim() + "'");
            } catch (NullPointerException | SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
    }

    public List<ProdutoFoto> getListaFotos(String SQL) {
        List<ProdutoFoto> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        do {
            ProdutoFoto produtoFoto = new ProdutoFoto();
            try {
                produtoFoto.setImages(cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO")));
                produtoFoto.setId_foto(cursor.getString(cursor.getColumnIndex("ID_FOTO")));
                lista.add(produtoFoto);
            } catch (CursorIndexOutOfBoundsException|OutOfMemoryError e) {
                e.printStackTrace();
                produtoFoto.setImages("");
                produtoFoto.setId_foto("");
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public List<ProdutoFoto> getListaAnexos(String SQL) {
        List<ProdutoFoto> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        do {
            ProdutoFoto produtoFoto = new ProdutoFoto();
            try {
                produtoFoto.setAtivo("S");
                produtoFoto.setId_produto( cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
                produtoFoto.setFoto_arquivo(cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO")));
                produtoFoto.setFoto_miniatura(cursor.getString(cursor.getColumnIndex("FOTO_MINIATURA")));
                produtoFoto.setFoto_principal(cursor.getString(cursor.getColumnIndex("FOTO_PRINCIPAL")));
                produtoFoto.setFbase64("");
                produtoFoto.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")).replace("'\'"," "));
                lista.add(produtoFoto);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public Cursor getListaFotoID(String SQL) {
        return db.listaDados(SQL);
    }

    public boolean add(ProdutoSubGrupo produtoSubGrupo) {
        ContentValues content = new ContentValues();
        content.put("ID", produtoSubGrupo.getId_grupo());
        content.put("NOME_SUB_GRUPO", produtoSubGrupo.getNome_sub_grupo());
        content.put("ATIVO", produtoSubGrupo.getAtivo());
        try {
            if ( db.addDados("TBL_PRODUTO_SUB_GRUPO",  content) > 0)
                return true;
            return  false;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean add(ProdutoGrupo produtoGrupo) {
        ContentValues content = new ContentValues();
        content.put("ID", produtoGrupo.getId_grupo());
        content.put("NOME_GRUPO", produtoGrupo.getNome_grupo());
        content.put("ATIVO", produtoGrupo.getAtivo());
        try {
            if ( db.addDados("TBL_PRODUTO_GRUPO",  content) > 0)
                return true;
            return  false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean add(Deposito deposito) {
        ContentValues content = new ContentValues();
        content.put("ID_DEPOSITO", deposito.getId_deposito());
        content.put("NOME_DEPOSITO", deposito.getNome_deposito());
        content.put("ID_EMPRESA", deposito.getId_empresa());
        try {
            if ( db.addDados("TBL_DEPOSITO",  content) > 0)
                return true;
            return  false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<ProdutoSubGrupo> getListaSubGrupo(String SQL) {
        List<ProdutoSubGrupo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        do {
            ProdutoSubGrupo produtoSubGrupo = new ProdutoSubGrupo();
            try {
                produtoSubGrupo.setId_grupo(cursor.getString(cursor.getColumnIndex("ID")));
                produtoSubGrupo.setNome_sub_grupo(cursor.getString(cursor.getColumnIndex("NOME_SUB_GRUPO")));
                produtoSubGrupo.setTotal_produto(cursor.getString(cursor.getColumnIndex("TOTAL_PRODUTO_SUB_GRUPO")));
                lista.add(produtoSubGrupo);
            } catch ( ArrayIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public List<ProdutoSubGrupo> getListaGrupos(String SQL) {
        List<ProdutoSubGrupo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        do {
            ProdutoSubGrupo produtoGrupo = new ProdutoSubGrupo();
            try {
                produtoGrupo.setId_grupo(cursor.getString(cursor.getColumnIndex("ID")));
                produtoGrupo.setNome_sub_grupo(cursor.getString(cursor.getColumnIndex("NOME_GRUPO")));
                produtoGrupo.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                lista.add(produtoGrupo);
            } catch ( ArrayIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public List<ProdutoGrupo> getListaGrupo(String SQL) {
        List<ProdutoGrupo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        do {
            ProdutoGrupo produtoGrupo = new ProdutoGrupo();
            try {
                produtoGrupo.setId_grupo(cursor.getString(cursor.getColumnIndex("ID")));
                produtoGrupo.setNome_grupo(cursor.getString(cursor.getColumnIndex("NOME_GRUPO")));
                produtoGrupo.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                lista.add(produtoGrupo);
            } catch ( ArrayIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public List<ProdutoLinhaColecao> getListaLinha(String SQL) {
        List<ProdutoLinhaColecao> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        do {
            ProdutoLinhaColecao produtoLinha = new ProdutoLinhaColecao();
            try {
                produtoLinha.setIdLinhaColecao(cursor.getInt(cursor.getColumnIndex("ID_LINHA_COLECAO")));
                produtoLinha.setNomeDescricaoLinha(cursor.getString(cursor.getColumnIndex("NOME_DESCRICAO_LINHA")));
                produtoLinha.setTotal_produto(cursor.getString(cursor.getColumnIndex("TOTAL_PRODUTO_LINHA")));
                lista.add(produtoLinha);
            } catch ( ArrayIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public List<Deposito> getListaDeposito(String SQL) {
        List<Deposito> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0) {
            cursor.close();
            return lista;
        }
        cursor.moveToFirst();
        do {
            Deposito deposito = new Deposito();
            try {
                deposito.setId_deposito(cursor.getString(cursor.getColumnIndex("ID_DEPOSITO")));
                deposito.setNome_deposito(cursor.getString(cursor.getColumnIndex("NOME_DEPOSITO")));
                deposito.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                lista.add(deposito);
            } catch ( ArrayIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public ProdutoGrupo getProdutoGrupo(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.getCount() <= 0) {
            cursor.close();
            return null;
        }
        if (cursor.moveToFirst()) {
            ProdutoGrupo produtoGrupo = new ProdutoGrupo();
            try {
                produtoGrupo.setId_grupo(cursor.getString(cursor.getColumnIndex("ID")));
                produtoGrupo.setNome_grupo(cursor.getString(cursor.getColumnIndex("NOME_GRUPO")));
                return  produtoGrupo;
            } catch ( ArrayIndexOutOfBoundsException e) {
            }
        }
        cursor.close();
        return null;
    }


    public Cursor getCursor(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        return  cursor;
    }

    public void addListaProdutos(List<Produto> lista,  DBHelper db) {
        // Certifique-se de pegar o banco corretamente.
        // Se 'db' no seu código original for o helper, use db.getWritableDatabase()
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT_PRODUTO);

        try {
            database.beginTransaction(); // INICIO DA TRANSAÇÃO

            for (Produto p : lista) {
                stmt.clearBindings();

                bindString(stmt, 1, p.getAtivo());
                bindString(stmt, 2, p.getId_produto());
                bindLong(stmt, 3, p.getIdLinhaColecao());
                bindString(stmt, 4, p.getNome_produto());
                bindString(stmt, 5, p.getUnidade());
                bindString(stmt, 6, p.getTipo_cadastro());
                bindLong(stmt, 7, p.getId_entidade());
                bindString(stmt, 8, p.getNcm());
                bindLong(stmt, 9, p.getId_grupo());
                bindLong(stmt, 10, p.getId_sub_grupo());

                bindDouble(stmt, 11, p.getPeso_bruto());
                bindDouble(stmt, 12, p.getPeso_liquido());
                bindString(stmt, 13, p.getCodigo_em_barras());
                bindString(stmt, 14, p.getMovimenta_estoque());
                bindString(stmt, 15, p.getNome_da_marca());
                bindLong(stmt, 16, p.getId_empresa());
                bindLong(stmt, 17, p.getId_origem());
                bindDouble(stmt, 18, p.getCusto_produto());
                bindDouble(stmt, 19, p.getCusto_per_ipi());
                bindDouble(stmt, 20, p.getCusto_ipi());

                bindDouble(stmt, 21, p.getCusto_per_frete());
                bindDouble(stmt, 22, p.getCusto_frete());
                bindDouble(stmt, 23, p.getCusto_per_icms());
                bindDouble(stmt, 24, p.getCusto_icms());
                bindDouble(stmt, 25, p.getCusto_per_fin());
                bindDouble(stmt, 26, p.getCusto_fin());
                bindDouble(stmt, 27, p.getCusto_per_subst());
                bindDouble(stmt, 28, p.getCusto_subt());
                bindDouble(stmt, 29, p.getCusto_per_outros());
                bindDouble(stmt, 30, p.getCusto_outros());

                bindDouble(stmt, 31, p.getValor_custo());
                bindString(stmt, 32, p.getExcluido());
                bindString(stmt, 33, p.getExcluido_por());
                bindString(stmt, 34, p.getExcluido_por_data());
                bindString(stmt, 35, p.getExcluido_codigo_novo());
                bindString(stmt, 36, p.getAjuste_preco_data());
                bindString(stmt, 37, p.getAjuste_preco_nfe());
                bindString(stmt, 38, p.getAjuste_preco_usuario());
                bindDouble(stmt, 39, p.getTotal_custo());
                bindDouble(stmt, 40, p.getTotal_credito());

                bindDouble(stmt, 41, p.getValor_custo_estoque());
                bindString(stmt, 42, p.getCusto_data_inicial());
                bindDouble(stmt, 43, p.getCusto_valor_inicial());
                bindString(stmt, 44, p.getProduto_venda());
                bindString(stmt, 45, p.getProduto_insumo());
                bindString(stmt, 46, p.getProduto_consumo());
                bindString(stmt, 47, p.getProduto_producao());
                bindDouble(stmt, 48, p.getVenda_perc_comissao());
                bindDouble(stmt, 49, p.getVenda_preco());
                bindDouble(stmt, 50, p.getVenda_perc_comissao_dois());

                bindString(stmt, 51, p.getDescricao());
                bindString(stmt, 52, p.getNome_sub_grupo());
                bindString(stmt, 53, p.getNome_grupo());
                bindString(stmt, 54, p.getProduto_materia_prima());
                bindString(stmt, 55, p.getProduto_tercerizacao());
                bindDouble(stmt, 56, p.getSaldo_estoque());
                bindString(stmt, 57, p.getTravar_fat_sem_estoque());
                bindString(stmt, 58, p.getUtiliza_tabela_preco());
                bindString(stmt, 59, p.getProduto_edita_valor());
                bindDouble(stmt, 60, p.getProduto_st_aliquota());
                bindString(stmt, 61, p.getProduto_suj_icms_st());

                stmt.executeInsert();
            }

            database.setTransactionSuccessful(); // COMMIT
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction(); // FINALIZA TRANSAÇÃO
        }
    }

    public void addListaProdutoFoto(List<ProdutoFoto> lista, DBHelper db) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT_FOTO);

        try {
            database.beginTransaction(); // Modo turbo: abre o arquivo uma única vez

            for (ProdutoFoto pf : lista) {
                stmt.clearBindings(); // Limpa os dados da foto anterior no loop

                // Vincular os 7 campos seguindo a ordem do SQL acima
                bindString(stmt, 1, pf.getAtivo());
                bindLong(stmt, 2, pf.getId_produto());
                bindLong(stmt, 3, pf.getId_foto());
                bindString(stmt, 4, pf.getUsuario_data() == null ? "" : pf.getUsuario_data());
                bindString(stmt, 5, pf.getFoto_arquivo() == null ? "" : pf.getFoto_arquivo());
                bindString(stmt, 6, pf.getFoto_miniatura() == null ? "" : pf.getFoto_miniatura());
                bindString(stmt, 7, pf.getFoto_principal() == null ? "N" : pf.getFoto_principal());

                stmt.executeInsert();
            }

            database.setTransactionSuccessful(); // Confirma todas as fotos de uma vez
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction(); // Fecha a transação e grava no disco
        }
    }

    public void updateSaldoEstoque(List<Produto> lista, DBHelper db) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_UPDATE_ESTOQ);

        try {
            database.beginTransaction(); // Modo turbo: abre o arquivo uma única vez
            for (Produto p : lista) {
                stmt.clearBindings(); // Limpa os dados da foto anterior no loop
                bindDouble(stmt, 1, p.getSaldo_estoque());
                bindString(stmt, 2, p.getId_produto());
                stmt.execute();
            }
            database.setTransactionSuccessful(); // Confirma todas as fotos de uma vez
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction(); // Fecha a transação e grava no disco
        }
    }

    // Métodos auxiliares para segurança de tipos
    private void bindString(SQLiteStatement stmt, int i, String val) {
        if (val == null) stmt.bindNull(i); else stmt.bindString(i, val);
    }
    private void bindLong(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindLong(i, Long.parseLong(val.toString()));
    }
    private void bindDouble(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindDouble(i, Double.parseDouble(val.toString()));
    }
    private static final String SQL_INSERT_PRODUTO = "INSERT INTO TBL_PRODUTO (" +
            "ATIVO, ID_PRODUTO, ID_LINHA_COLECAO, NOME_PRODUTO, UNIDADE, TIPO_CADASTRO, ID_ENTIDADE, NCM, ID_GRUPO, ID_SUB_GRUPO, " + // 1-10
            "PESO_BRUTO, PESO_LIQUIDO, CODIGO_EM_BARRAS, MOVIMENTA_ESTOQUE, NOME_DA_MARCA, ID_EMPRESA, ID_ORIGEM, CUSTO_PRODUTO, CUSTO_PER_IPI, CUSTO_IPI, " + // 11-20
            "CUSTO_PER_FRETE, CUSTO_FRETE, CUSTO_PER_ICMS, CUSTO_ICMS, CUSTO_PER_FIN, CUSTO_FIN, CUSTO_PER_SUBST, CUSTO_SUBT, CUSTO_PER_OUTROS, CUSTO_OUTROS, " + // 21-30
            "VALOR_CUSTO, EXCLUIDO, EXCLUIDO_POR, EXCLUIDO_POR_DATA, EXCLUIDO_CODIGO_NOVO, AJUSTE_PRECO_DATA, AJUSTE_PRECO_NFE, AJUSTE_PRECO_USUARIO, TOTAL_CUSTO, TOTAL_CREDITO, " + // 31-40
            "VALOR_CUSTO_ESTOQUE, CUSTO_DATA_INICIAL, CUSTO_VALOR_INICIAL, PRODUTO_VENDA, PRODUTO_INSUMO, PRODUTO_CONSUMO, PRODUTO_PRODUCAO, VENDA_PERC_COMISSAO, VENDA_PRECO, VENDA_PERC_COMISSAO_DOIS, " + // 41-50
            "DESCRICAO, NOME_SUB_GRUPO, NOME_GRUPO, PRODUTO_MATERIA_PRIMA, PRODUTO_TERCERIZACAO, SALDO_ESTOQUE, TRAVAR_FAT_SEM_ESTOQUE, UTILIZA_TABELA_PRECO, PRODUTO_EDITA_PRECO_APP, PRODUTO_ST_ALIQUOTA, " + // 51-60
            "PRODUTO_SUJ_ICMS_ST" + // 61
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private static final String SQL_INSERT_FOTO = "INSERT INTO TBL_PRODUTO_FOTO (" +
            "ATIVO, ID_PRODUTO, ID_FOTO, USUARIO_DATA, FOTO_ARQUIVO, " +
            "FOTO_MINIATURA, FOTO_PRINCIPAL" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_ESTOQ = "UPDATE TBL_PRODUTO SET SALDO_ESTOQUE = ? WHERE ID_PRODUTO = ?";
}

