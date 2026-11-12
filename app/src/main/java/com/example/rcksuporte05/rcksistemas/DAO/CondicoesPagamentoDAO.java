package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.CadastroCondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;

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
            return db.addDados("TBL_CONDICOES_PAG_CAB", content);
        } catch (NullPointerException | SQLException ignored) {
        }
        return 0;
    }

    public List<CondicoesPagamento> getLista(String SQL) {
        List<CondicoesPagamento> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return lista;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    lista.add(setCondicoesPagamento(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public CondicoesPagamento getCondicao(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return null;
        }
        try {
            if (cursor.moveToFirst())
                return setCondicoesPagamento(cursor);
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    public long add(CadastroCondicoesPagamento cadastroCondicoesPagamento) {
        ContentValues content = new ContentValues();
        try {
            content.put("ID_CONDICAO", cadastroCondicoesPagamento.getId_condicao());
            content.put("ID_CADASTRO", cadastroCondicoesPagamento.getId_cadastro());
            return db.addDados("TBL_CADASTRO_CONDICOES_PAG", content);
        } catch (NullPointerException | SQLException ignored) {
        }
        return 0;
    }

    private CondicoesPagamento setCondicoesPagamento(Cursor cursor) {
        CondicoesPagamento condicoesPagamento = new CondicoesPagamento();
        try {
            condicoesPagamento.setAtivo(getString(cursor, "ATIVO"));
            condicoesPagamento.setId_condicao(getString(cursor, "ID_CONDICAO"));
            condicoesPagamento.setNome_condicao(getString(cursor, "NOME_CONDICAO"));
            condicoesPagamento.setNumero_parcelas(getString(cursor, "NUMERO_PARCELAS"));
            condicoesPagamento.setIntervalo_dias(getString(cursor, "INTERVALO_DIAS"));
            condicoesPagamento.setTipo_condicao(getString(cursor, "TIPO_CONDICAO"));
            condicoesPagamento.setNfe_tipo_financeiro(getString(cursor, "NFE_TIPO_FINANCEIRO"));
            condicoesPagamento.setNfe_mostrar_parcelas(getString(cursor, "NFE_MOSTRAR_PARCELAS"));
            condicoesPagamento.setUsuario_id(getString(cursor, "USUARIO_ID"));
            condicoesPagamento.setUsuario_nome(getString(cursor, "USUARIO_NOME"));
            condicoesPagamento.setUsuario_data(getString(cursor, "USUARIO_DATA"));
            condicoesPagamento.setPublicar_na_web(getString(cursor, "PUBLICAR_NA_WEB"));
            condicoesPagamento.setAceita_desconto(getString(cursor, "ACEITA_DESCONTO"));
            condicoesPagamento.setDesconto_perc(getString(cursor, "DESCONTO_PERC"));
        } catch (Exception ignored) {
        }
        return condicoesPagamento;
    }

    // =========================================================================
    // AUXILIAR NECESSÁRIO (CURSOR NULL-SAFE)
    // =========================================================================
    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }
}
