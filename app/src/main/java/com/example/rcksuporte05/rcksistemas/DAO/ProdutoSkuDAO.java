package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoParametroSku;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSku;

import java.util.ArrayList;
import java.util.List;

public class ProdutoSkuDAO {
    private DBHelper db;

    public ProdutoSkuDAO(DBHelper db) {
        this.db = db;
    }

    public long add(ProdutoSku produto) {
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", produto.getAtivo());
            content.put("ID_EMPRESA", produto.getId_empresa());
            content.put("ID_SKU", produto.getId_sku());
            content.put("NOME_SKU", produto.getNome_sku());
            System.gc();
            return db.addDados("TBL_PRODUTO_SKU", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long add(ProdutoParametroSku produto) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_EMPRESA", produto.getId_empresa());
            content.put("ID_SKU", produto.getId_sku());
            content.put("ID_PRODUTO", produto.getId_produto());
            content.put("PRECO", produto.getPreco());
            content.put("FATOR", produto.getFator());
            content.put("PRECO_VENDA_DOIS", produto.getPreco_venda_dois());

            System.gc();
            return db.addDados("TBL_PRODUTO_PARAMETRO_SKU", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ProdutoParametroSku> getLista(String idProduto) {
        String sql;
        List<ProdutoParametroSku> lista = new ArrayList<>();
        try {
            sql = "SELECT 1 AS ID_SKU, ID_PRODUTO, UNIDADE || ' '  || EP.PROD_DESCRICAO_PRECO_UM AS NOME_SKU, VENDA_PRECO AS PRECO, 1 AS FATOR "
                    + "FROM TBL_PRODUTO P LEFT JOIN TBL_EMPRESA_PARAMETRO AS EP ON EP.ID = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " "
                    + "WHERE P.ID_PRODUTO = '" + idProduto + "' AND NOT P.ID_PRODUTO IS NULL AND P.ATIVO = 'S' "
                    + "UNION ALL "
                    + "SELECT PMSKU.ID_SKU, PMSKU.ID_PRODUTO, PSKU.NOME_SKU, PMSKU.PRECO, PMSKU.FATOR "
                    + "FROM TBL_PRODUTO_PARAMETRO_SKU PMSKU "
                    + "INNER JOIN TBL_PRODUTO_SKU PSKU ON PMSKU.ID_SKU = PSKU.ID_SKU "
                    + "WHERE PMSKU.ID_PRODUTO = '" + idProduto + "' "
                    + "AND PMSKU.ID_PRODUTO IS NOT NULL AND PSKU.ATIVO = 'S' ";
        } catch ( NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin(db ));
            sql = "SELECT 1 AS ID_SKU, ID_PRODUTO, UNIDADE || ' '  || EP.PROD_DESCRICAO_PRECO_UM AS NOME_SKU, VENDA_PRECO AS PRECO, 1 AS FATOR "
                    + "FROM TBL_PRODUTO P LEFT JOIN TBL_EMPRESA_PARAMETRO AS EP ON EP.ID = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " "
                    + "WHERE P.ID_PRODUTO = '" + idProduto + "' AND NOT P.ID_PRODUTO IS NULL AND P.ATIVO = 'S' "
                    + "UNION ALL "
                    + "SELECT PMSKU.ID_SKU, PMSKU.ID_PRODUTO, PSKU.NOME_SKU, PMSKU.PRECO, PMSKU.FATOR "
                    + "FROM TBL_PRODUTO_PARAMETRO_SKU PMSKU "
                    + "INNER JOIN TBL_PRODUTO_SKU PSKU ON PMSKU.ID_SKU = PSKU.ID_SKU "
                    + "WHERE PMSKU.ID_PRODUTO = '" + idProduto + "' "
                    + "AND PMSKU.ID_PRODUTO IS NOT NULL AND PSKU.ATIVO = 'S' ";
        } catch ( Exception e) {
            return lista;
        }
        sql+= "ORDER BY PRECO";

        Cursor cursor = db.listaDados(sql);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            ProdutoParametroSku produto = new ProdutoParametroSku();
            try {
                //produto.setId_empresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
                produto.setId_sku(cursor.getInt(cursor.getColumnIndex("ID_SKU")));
                produto.setId_produto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
                produto.setPreco(cursor.getString(cursor.getColumnIndex("PRECO")));
                produto.setFator(cursor.getString(cursor.getColumnIndex("FATOR")));
                //produto.setPreco_venda_dois(cursor.getString(cursor.getColumnIndex("PRECO_VENDA_DOIS")));
                produto.setNome(cursor.getString(cursor.getColumnIndex("NOME_SKU")));
                lista.add(produto);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            //System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    //        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO_PARAMETRO_SKU(ID_EMPRESA INTEGER, ID_SKU INTEGER, ID_PRODUTO VARCHAR(20), PRECO DECIMAL(12, 8), " +
    //            "FATOR DECIMAL(12, 8), PRECO_VENDA_DOIS DECIMAL(12, 8))");


}
