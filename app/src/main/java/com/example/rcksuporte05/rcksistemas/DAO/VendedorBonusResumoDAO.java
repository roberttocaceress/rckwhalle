package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.os.Build;

import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.VendedorBonusResumo;

import java.util.ArrayList;
import java.util.List;

public class VendedorBonusResumoDAO {
    private DBHelper db;

    public VendedorBonusResumoDAO(DBHelper db) {
        this.db = db;
    }

    public long add(VendedorBonusResumo vendedorBonusResumo) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_VENDEDOR", vendedorBonusResumo.getId_vendedor());
            content.put("ID_EMPRESA", vendedorBonusResumo.getId_empresa());
            content.put("VALOR_CREDITO", vendedorBonusResumo.getValor_credito());
            content.put("VALOR_DEBITO", vendedorBonusResumo.getValor_debito());
            content.put("VALOR_BONUS_CANCELADOS", vendedorBonusResumo.getValor_bonus_cancelados());
            content.put("VALOR_SALDO", vendedorBonusResumo.getValor_saldo());
            content.put("DATA_ULTIMA_ATUALIZACAO", vendedorBonusResumo.getData_ultima_atualizacao());
            System.gc();
            return db.addDados("TBL_VENDEDOR_BONUS_RESUMO", content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<VendedorBonusResumo> getLista(String SQL) {
        List<VendedorBonusResumo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            VendedorBonusResumo vendedorBonusResumo = new VendedorBonusResumo();
            try {
                vendedorBonusResumo.setId_vendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                vendedorBonusResumo.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                vendedorBonusResumo.setValor_credito(cursor.getString(cursor.getColumnIndex("VALOR_CREDITO")));
                vendedorBonusResumo.setValor_debito(cursor.getString(cursor.getColumnIndex("VALOR_DEBITO")));
                vendedorBonusResumo.setValor_bonus_cancelados(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CANCELADOS")));
                vendedorBonusResumo.setValor_saldo(cursor.getString(cursor.getColumnIndex("VALOR_SALDO")));
                vendedorBonusResumo.setData_ultima_atualizacao(cursor.getString(cursor.getColumnIndex("DATA_ULTIMA_ATUALIZACAO")));
                lista.add(vendedorBonusResumo);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public VendedorBonusResumo getVendedor(String SQL) {
        VendedorBonusResumo vendedorBonusResumo = new VendedorBonusResumo();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return vendedorBonusResumo;
        cursor.moveToFirst();
        try {
            vendedorBonusResumo.setId_vendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
            vendedorBonusResumo.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            vendedorBonusResumo.setValor_credito(cursor.getString(cursor.getColumnIndex("VALOR_CREDITO")));
            vendedorBonusResumo.setValor_debito(cursor.getString(cursor.getColumnIndex("VALOR_DEBITO")));
            vendedorBonusResumo.setValor_bonus_cancelados(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CANCELADOS")));
            vendedorBonusResumo.setValor_saldo(cursor.getString(cursor.getColumnIndex("VALOR_SALDO")));
            vendedorBonusResumo.setData_ultima_atualizacao(cursor.getString(cursor.getColumnIndex("DATA_ULTIMA_ATUALIZACAO")));
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        cursor.close();
        System.gc();
        return vendedorBonusResumo;
    }
}
