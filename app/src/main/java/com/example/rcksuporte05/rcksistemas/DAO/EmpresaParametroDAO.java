package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoPendente;
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
            if (empresa.getQtde_maior_saldo_ped_app() == null || empresa.getQtde_maior_saldo_ped_app().isEmpty()) {
                content.put("QTDE_MAIOR_SALDO_PED_APP", "S");
            } else{
                content.put("QTDE_MAIOR_SALDO_PED_APP", empresa.getQtde_maior_saldo_ped_app());
            }
            System.gc();
            return db.addDados("TBL_EMPRESA_PARAMETRO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressLint("Range")
    public List<EmpresaParametro> getLista(String SQL) {
        List<EmpresaParametro> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.moveToFirst()) {
            do {
                EmpresaParametro empresa = new EmpresaParametro();
                try {
                    empresa.setId_empresa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID"))));
                    empresa.setRazao_social(cursor.getString(cursor.getColumnIndex("RAZAO_SOCIAL")));
                    empresa.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
                    empresa.setEmpresa_logo(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO")));
                    empresa.setEmpresa_logo_mini(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO_MINI")));
                    empresa.setEmpresa_app_cor(cursor.getString(cursor.getColumnIndex("EMPRESA_APP_COR")));
                    empresa.setTipo_tabela_preco(cursor.getString(cursor.getColumnIndex("TIPO_TABELA_PRECO")));
                    empresa.setUtiliza_tabela_preco(cursor.getString(cursor.getColumnIndex("UTILIZA_TABELA_PRECO")));
                    empresa.setUtiliza_limite_credito(cursor.getString(cursor.getColumnIndex("UTILIZA_LIMITE_CREDITO")));
                    empresa.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
                    empresa.setProd_descricao_preco_um(cursor.getString(cursor.getColumnIndex("PROD_DESCRICAO_PRECO_UM")));
                    empresa.setUtiliza_cobranca_offline(cursor.getString(cursor.getColumnIndex("UTILIZA_COBRANCA_OFFLINE")));
                    empresa.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                    empresa.setUtiliza_icms_app(cursor.getString(cursor.getColumnIndex("UTILIZA_ICMS_ST")));
                    empresa.setQtde_maior_saldo_ped_app(cursor.getString(cursor.getColumnIndex("QTDE_MAIOR_SALDO_PED_APP")));
                    empresa.setConfirma_sincronia_checkin_app(cursor.getString(cursor.getColumnIndex("CONFIRMA_SINCRONIA_CHECKIN_APP")));
                    lista.add(empresa);
                    System.gc();
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
            System.gc();
        }
        return lista;
    }

    @SuppressLint("Range")
    public EmpresaParametro getEmpresa(String SQL) {
        EmpresaParametro empresa = new EmpresaParametro();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.moveToFirst()) {
            try {
                empresa.setId_empresa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID"))));
                empresa.setRazao_social(cursor.getString(cursor.getColumnIndex("RAZAO_SOCIAL")));
                empresa.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
                empresa.setEmpresa_logo(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO")));
                empresa.setEmpresa_logo_mini(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO_MINI")));
                empresa.setEmpresa_app_cor(cursor.getString(cursor.getColumnIndex("EMPRESA_APP_COR")));
                empresa.setTipo_tabela_preco(cursor.getString(cursor.getColumnIndex("TIPO_TABELA_PRECO")));
                empresa.setUtiliza_tabela_preco(cursor.getString(cursor.getColumnIndex("UTILIZA_TABELA_PRECO")));
                empresa.setUtiliza_limite_credito(cursor.getString(cursor.getColumnIndex("UTILIZA_LIMITE_CREDITO")));
                empresa.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
                empresa.setProd_descricao_preco_um(cursor.getString(cursor.getColumnIndex("PROD_DESCRICAO_PRECO_UM")));
                empresa.setUtiliza_cobranca_offline(cursor.getString(cursor.getColumnIndex("UTILIZA_COBRANCA_OFFLINE")));
                empresa.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                empresa.setUtiliza_icms_app(cursor.getString(cursor.getColumnIndex("UTILIZA_ICMS_ST")));
                empresa.setQtde_maior_saldo_ped_app(cursor.getString(cursor.getColumnIndex("QTDE_MAIOR_SALDO_PED_APP")));
                empresa.setConfirma_sincronia_checkin_app(cursor.getString(cursor.getColumnIndex("CONFIRMA_SINCRONIA_CHECKIN_APP")));
                System.gc();
            } catch (CursorIndexOutOfBoundsException e) {
                return  null;
            }
            cursor.close();
            System.gc();
            return empresa;
        }
        return null;
    }
    @SuppressLint("Range")
    public EmpresaParametro getEmpresaPDF(String SQL) {
        EmpresaParametro empresa = new EmpresaParametro();
        Cursor cursor = db.listaDados(SQL);
        if ( cursor.moveToFirst()) {
            try {
                empresa.setId_empresa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID"))));
                empresa.setRazao_social(cursor.getString(cursor.getColumnIndex("RAZAO_SOCIAL")));
                empresa.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
                empresa.setEmpresa_logo(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO")));
                empresa.setEmpresa_logo_mini(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO_MINI")));
                empresa.setEmpresa_app_cor(cursor.getString(cursor.getColumnIndex("EMPRESA_APP_COR")));
                empresa.setTipo_tabela_preco(cursor.getString(cursor.getColumnIndex("TIPO_TABELA_PRECO")));
                empresa.setUtiliza_tabela_preco(cursor.getString(cursor.getColumnIndex("UTILIZA_TABELA_PRECO")));
                empresa.setUtiliza_limite_credito(cursor.getString(cursor.getColumnIndex("UTILIZA_LIMITE_CREDITO")));
                empresa.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
                empresa.setProd_descricao_preco_um(cursor.getString(cursor.getColumnIndex("PROD_DESCRICAO_PRECO_UM")));
                empresa.setUtiliza_cobranca_offline(cursor.getString(cursor.getColumnIndex("UTILIZA_COBRANCA_OFFLINE")));
                empresa.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                empresa.setUtiliza_icms_app(cursor.getString(cursor.getColumnIndex("UTILIZA_ICMS_ST")));
                System.gc();
            } catch (CursorIndexOutOfBoundsException e) {
                return  null;
            }
            cursor.close();
            System.gc();
            return empresa;
        }
        return null;

    }
    @SuppressLint("Range")
    public EmpresaParametro getEmpresaPDF(Context context, String SQL) {
        EmpresaParametro empresa = new EmpresaParametro();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        try {
            empresa.setId_empresa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID"))));
            empresa.setRazao_social(cursor.getString(cursor.getColumnIndex("RAZAO_SOCIAL")));
            empresa.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
            empresa.setEmpresa_logo(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO")));
            empresa.setEmpresa_logo_mini(cursor.getString(cursor.getColumnIndex("EMPRESA_LOGO_MINI")));
            empresa.setEmpresa_app_cor(cursor.getString(cursor.getColumnIndex("EMPRESA_APP_COR")));
            empresa.setTipo_tabela_preco(cursor.getString(cursor.getColumnIndex("TIPO_TABELA_PRECO")));
            empresa.setUtiliza_tabela_preco(cursor.getString(cursor.getColumnIndex("UTILIZA_TABELA_PRECO")));
            empresa.setUtiliza_limite_credito(cursor.getString(cursor.getColumnIndex("UTILIZA_LIMITE_CREDITO")));
            empresa.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
            empresa.setProd_descricao_preco_um(cursor.getString(cursor.getColumnIndex("PROD_DESCRICAO_PRECO_UM")));
            empresa.setUtiliza_cobranca_offline(cursor.getString(cursor.getColumnIndex("UTILIZA_COBRANCA_OFFLINE")));
            empresa.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
            empresa.setUtiliza_icms_app(cursor.getString(cursor.getColumnIndex("UTILIZA_ICMS_ST")));
            System.gc();
        } catch (CursorIndexOutOfBoundsException|IllegalStateException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            return empresa;
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            return  empresa;
        }
        cursor.close();
        System.gc();
        return empresa;
    }
}
