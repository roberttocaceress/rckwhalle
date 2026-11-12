package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;

import java.util.ArrayList;
import java.util.List;

public class EmpresaParametroDAO {
    private DBHelper db;

    public EmpresaParametroDAO(DBHelper db) {
        this.db = db;
    }

    public long add(EmpresaParametro empresa) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID", empresa.getId_empresa());
            content.put("RAZAO_SOCIAL", empresa.getRazao_social());
            content.put("NOME_FANTASIA", empresa.getNome_fantasia());
            content.put("EMPRESA_LOGO", empresa.getEmpresa_logo());
            content.put("EMPRESA_LOGO_MINI", empresa.getEmpresa_logo_mini());
            content.put("EMPRESA_APP_COR", empresa.getEmpresa_app_cor());
            content.put("TIPO_TABELA_PRECO", empresa.getTipo_tabela_preco());
            content.put("UTILIZA_TABELA_PRECO", empresa.getUtiliza_tabela_preco());
            content.put("TEMPO_AUTO_SINCRONIA", empresa.getTempo_auto_sincronia());
            content.put("ESTOQUE_AUTO_SINCRONIA", empresa.getEstoque_auto_sincronia());
            content.put("UTILIZA_LIMITE_CREDITO", empresa.getUtiliza_limite_credito());
            content.put("ENDERECO_UF", empresa.getEndereco_uf());
            content.put("PROD_DESCRICAO_PRECO_UM", empresa.getProd_descricao_preco_um());
            content.put("UTILIZA_COBRANCA_OFFLINE", empresa.getUtiliza_cobranca_offline());
            content.put("ID_CIDADE", empresa.getId_cidade());
            content.put("UTILIZA_ICMS_ST", empresa.getUtiliza_icms_app());
            content.put("CONFIRMA_SINCRONIA_CHECKIN_APP", empresa.getConfirma_sincronia_checkin_app());
            content.put("CONFIRMA_SINCRONIA_CHECKOUT_APP", empresa.getConfirma_sincronia_checkout_app());
            if (empresa.getQtde_maior_saldo_ped_app() == null || empresa.getQtde_maior_saldo_ped_app().isEmpty()) {
                content.put("QTDE_MAIOR_SALDO_PED_APP", "S");
            } else {
                content.put("QTDE_MAIOR_SALDO_PED_APP", empresa.getQtde_maior_saldo_ped_app());
            }
            System.gc();
            return db.addDados("TBL_EMPRESA_PARAMETRO", content);
        } catch (NullPointerException | SQLException ignored) {
        }
        return 0;
    }

    public List<EmpresaParametro> getLista(String SQL) {
        List<EmpresaParametro> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                do {
                    lista.add(setEmpresaParametro(cursor));
                } while (cursor.moveToNext());

        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public EmpresaParametro getEmpresa(String SQL) {
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                return setEmpresaParametro(cursor);

        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    public EmpresaParametro getEmpresaPDF(String SQL) {
        return getEmpresa(SQL);
    }

    public EmpresaParametro getEmpresaPDF(Context context, String SQL) {
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                return setEmpresaParametro(cursor);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    private EmpresaParametro setEmpresaParametro(Cursor cursor) {
        EmpresaParametro empresa = new EmpresaParametro();
        try {
            empresa.setId_empresa(getInt(cursor, "ID"));
            empresa.setRazao_social(getString(cursor, "RAZAO_SOCIAL"));
            empresa.setNome_fantasia(getString(cursor, "NOME_FANTASIA"));
            empresa.setEmpresa_logo(getString(cursor, "EMPRESA_LOGO"));
            empresa.setEmpresa_logo_mini(getString(cursor, "EMPRESA_LOGO_MINI"));
            empresa.setEmpresa_app_cor(getString(cursor, "EMPRESA_APP_COR"));
            empresa.setTipo_tabela_preco(getString(cursor, "TIPO_TABELA_PRECO"));
            empresa.setUtiliza_tabela_preco(getString(cursor, "UTILIZA_TABELA_PRECO"));
            empresa.setTempo_auto_sincronia(getString(cursor, "TEMPO_AUTO_SINCRONIA"));
            empresa.setEstoque_auto_sincronia(getString(cursor, "ESTOQUE_AUTO_SINCRONIA"));
            empresa.setUtiliza_limite_credito(getString(cursor, "UTILIZA_LIMITE_CREDITO"));
            empresa.setEndereco_uf(getString(cursor, "ENDERECO_UF"));
            empresa.setProd_descricao_preco_um(getString(cursor, "PROD_DESCRICAO_PRECO_UM"));
            empresa.setUtiliza_cobranca_offline(getString(cursor, "UTILIZA_COBRANCA_OFFLINE"));
            empresa.setId_cidade(getString(cursor, "ID_CIDADE"));
            empresa.setUtiliza_icms_app(getString(cursor, "UTILIZA_ICMS_ST"));
            empresa.setQtde_maior_saldo_ped_app(getString(cursor, "QTDE_MAIOR_SALDO_PED_APP"));
            empresa.setConfirma_sincronia_checkin_app(getString(cursor, "CONFIRMA_SINCRONIA_CHECKIN_APP"));
            empresa.setConfirma_sincronia_checkout_app(getString(cursor, "CONFIRMA_SINCRONIA_CHECKOUT_APP"));
        } catch (Exception ignored) {
        }
        return empresa;
    }

    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }

    private int getInt(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0;
        try {
            return Integer.parseInt(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
