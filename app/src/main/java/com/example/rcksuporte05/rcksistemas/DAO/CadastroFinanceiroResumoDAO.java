package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;

public class CadastroFinanceiroResumoDAO {
    private DBHelper db;

    public CadastroFinanceiroResumoDAO(DBHelper db) {
        this.db = db;
    }

    public void addUpdate(CadastroFinanceiroResumo cadastroFinanceiroResumo) {
        ContentValues content = new ContentValues();
        content.put("ID_CADASTRO", cadastroFinanceiroResumo.getIdCadastro());
        content.put("LIMITE_CREDITO", cadastroFinanceiroResumo.getLimiteCredito());
        content.put("FINANCEIRO_VENCIDO", cadastroFinanceiroResumo.getFinanceiroVencido());
        content.put("FINANCEIRO_VENCER", cadastroFinanceiroResumo.getFinanceiroVencer());
        content.put("FINANCEIRO_QUITADO", cadastroFinanceiroResumo.getFinanceiroQuitado());
        content.put("PEDIDOS_LIBERADOS", cadastroFinanceiroResumo.getPedidosLiberados());
        content.put("LIMITE_UTILIZADO", cadastroFinanceiroResumo.getLimiteUtilizado());
        content.put("LIMITE_DISPONIVEL", cadastroFinanceiroResumo.getLimiteDisponivel());
        content.put("USUARIO_ID", cadastroFinanceiroResumo.getUsuarioId());
        content.put("USUARIO_NOME", cadastroFinanceiroResumo.getUsuarioNome());
        content.put("USUARIO_DATA", cadastroFinanceiroResumo.getUsuarioData());
        content.put("DATA_ULTIMA_ATUALIZACAO", db.pegaDataHoraAtual());
        if (cadastroFinanceiroResumo.getIdCadastro() != 0 && db.contagem("SELECT COUNT(ID_CADASTRO) FROM TBL_CADASTRO_FINANCEIRO_RESUMO WHERE ID_CADASTRO = " + cadastroFinanceiroResumo.getIdCadastro() + ";") > 0)
            db.updateDados("TBL_CADASTRO_FINANCEIRO_RESUMO", content, "ID_CADASTRO = " + cadastroFinanceiroResumo.getIdCadastro());
        else
            db.addDados("TBL_CADASTRO_FINANCEIRO_RESUMO", content);
    }

    public CadastroFinanceiroResumo getCadastro(int idCadastro) {
        CadastroFinanceiroResumo cadastro = new CadastroFinanceiroResumo();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_FINANCEIRO_RESUMO WHERE ID_CADASTRO = " + idCadastro);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) cursor.close();
            return cadastro;
        }
        try {
            if (cursor.moveToFirst()) {
                cadastro = setCadastroFinanceiroResumo(cursor);
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return cadastro;
    }

    private CadastroFinanceiroResumo setCadastroFinanceiroResumo(Cursor cursor) {
        CadastroFinanceiroResumo cadastro = new CadastroFinanceiroResumo();
        try {
            cadastro.setIdCadastro(getInt(cursor, "ID_CADASTRO"));
            cadastro.setLimiteCredito(getFloat(cursor, "LIMITE_CREDITO"));
            cadastro.setFinanceiroVencido(getFloat(cursor, "FINANCEIRO_VENCIDO"));
            cadastro.setFinanceiroVencer(getFloat(cursor, "FINANCEIRO_VENCER"));
            cadastro.setFinanceiroQuitado(getFloat(cursor, "FINANCEIRO_QUITADO"));
            cadastro.setPedidosLiberados(getFloat(cursor, "PEDIDOS_LIBERADOS"));
            cadastro.setLimiteUtilizado(getFloat(cursor, "LIMITE_UTILIZADO"));
            cadastro.setLimiteDisponivel(getFloat(cursor, "LIMITE_DISPONIVEL"));
            cadastro.setUsuarioId(getInt(cursor, "USUARIO_ID"));
            cadastro.setUsuarioNome(getString(cursor, "USUARIO_NOME"));
            cadastro.setUsuarioData(getString(cursor, "USUARIO_DATA"));
            cadastro.setDataUltimaAtualizacao(getString(cursor, "DATA_ULTIMA_ATUALIZACAO"));
        } catch (Exception ignored) {
        }
        return cadastro;
    }

    // =========================================================================
    // AUXILIARES NECESSÁRIOS (CURSOR NULL-SAFE)
    // =========================================================================
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

    private float getFloat(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0.0f;
        try {
            return Float.parseFloat(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }
}
