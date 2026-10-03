package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.model.CadastroCondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;

import java.util.ArrayList;
import java.util.List;

public class CondicoesPagamentoDAO {
    private DBHelper db;
    public CondicoesPagamentoDAO(DBHelper db) {
        this.db = db;
    }
    public long add(CondicoesPagamento condicoesPagamento) {
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", condicoesPagamento.getAtivo());
            content.put("ID_CONDICAO", condicoesPagamento.getId_condicao());
            content.put("NOME_CONDICAO", condicoesPagamento.getNome_condicao());
            content.put("NUMERO_PARCELAS", condicoesPagamento.getNumero_parcelas());
            content.put("INTERVALO_DIAS", condicoesPagamento.getIntervalo_dias());
            content.put("TIPO_CONDICAO", condicoesPagamento.getTipo_condicao());
            content.put("NFE_TIPO_FINANCEIRO", condicoesPagamento.getNfe_tipo_financeiro());
            content.put("NFE_MOSTRAR_PARCELAS", condicoesPagamento.getNfe_mostrar_parcelas());
            content.put("USUARIO_ID", condicoesPagamento.getUsuario_id());
            content.put("USUARIO_NOME", condicoesPagamento.getUsuario_nome());
            content.put("USUARIO_DATA", condicoesPagamento.getUsuario_data());
            content.put("PUBLICAR_NA_WEB", condicoesPagamento.getPublicar_na_web());
            content.put("ACEITA_DESCONTO", condicoesPagamento.getAceita_desconto());
            content.put("DESCONTO_PERC", condicoesPagamento.getDesconto_perc());
            //System.gc();
            return db.addDados("TBL_CONDICOES_PAG_CAB", content);
        } catch (NullPointerException | SQLException e) {
        } catch (Exception e) {

        }
        return 0;
    }

    public List<CondicoesPagamento> getLista(String SQL) {
        List<CondicoesPagamento> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
            try {
                condicoesPagamento.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                condicoesPagamento.setId_condicao(cursor.getString(cursor.getColumnIndex("ID_CONDICAO")));
                condicoesPagamento.setNome_condicao(cursor.getString(cursor.getColumnIndex("NOME_CONDICAO")));
                condicoesPagamento.setNumero_parcelas(cursor.getString(cursor.getColumnIndex("NUMERO_PARCELAS")));
                condicoesPagamento.setIntervalo_dias(cursor.getString(cursor.getColumnIndex("INTERVALO_DIAS")));
                condicoesPagamento.setTipo_condicao(cursor.getString(cursor.getColumnIndex("TIPO_CONDICAO")));
                condicoesPagamento.setNfe_tipo_financeiro(cursor.getString(cursor.getColumnIndex("NFE_TIPO_FINANCEIRO")));
                condicoesPagamento.setNfe_mostrar_parcelas(cursor.getString(cursor.getColumnIndex("NFE_MOSTRAR_PARCELAS")));
                condicoesPagamento.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
                condicoesPagamento.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
                condicoesPagamento.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                condicoesPagamento.setPublicar_na_web(cursor.getString(cursor.getColumnIndex("PUBLICAR_NA_WEB")));
                condicoesPagamento.setAceita_desconto(cursor.getString(cursor.getColumnIndex("ACEITA_DESCONTO")));
                condicoesPagamento.setDesconto_perc(cursor.getString(cursor.getColumnIndex("DESCONTO_PERC")));
                lista.add(condicoesPagamento);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        //System.gc();
        return lista;
    }


    public CondicoesPagamento getCondicao(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return null;
        cursor.moveToFirst();
            CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
            try {
                condicoesPagamento.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                condicoesPagamento.setId_condicao(cursor.getString(cursor.getColumnIndex("ID_CONDICAO")));
                condicoesPagamento.setNome_condicao(cursor.getString(cursor.getColumnIndex("NOME_CONDICAO")));
                condicoesPagamento.setNumero_parcelas(cursor.getString(cursor.getColumnIndex("NUMERO_PARCELAS")));
                condicoesPagamento.setIntervalo_dias(cursor.getString(cursor.getColumnIndex("INTERVALO_DIAS")));
                condicoesPagamento.setTipo_condicao(cursor.getString(cursor.getColumnIndex("TIPO_CONDICAO")));
                condicoesPagamento.setNfe_tipo_financeiro(cursor.getString(cursor.getColumnIndex("NFE_TIPO_FINANCEIRO")));
                condicoesPagamento.setNfe_mostrar_parcelas(cursor.getString(cursor.getColumnIndex("NFE_MOSTRAR_PARCELAS")));
                condicoesPagamento.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
                condicoesPagamento.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
                condicoesPagamento.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                condicoesPagamento.setPublicar_na_web(cursor.getString(cursor.getColumnIndex("PUBLICAR_NA_WEB")));
                condicoesPagamento.setAceita_desconto(cursor.getString(cursor.getColumnIndex("ACEITA_DESCONTO")));
                condicoesPagamento.setDesconto_perc(cursor.getString(cursor.getColumnIndex("DESCONTO_PERC")));
                return condicoesPagamento;
            } catch (CursorIndexOutOfBoundsException e) {
                return null;
            }

    }

    public long add(CadastroCondicoesPagamento cadastroCondicoesPagamento) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_CONDICAO", cadastroCondicoesPagamento.getId_condicao());
            content.put("ID_CADASTRO", cadastroCondicoesPagamento.getId_cadastro());
            return db.addDados("TBL_CADASTRO_CONDICOES_PAG", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
