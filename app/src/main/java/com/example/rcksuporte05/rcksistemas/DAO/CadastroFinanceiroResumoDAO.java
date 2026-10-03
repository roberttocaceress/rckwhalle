package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

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
        if ( cursor.getCount() <= 0)
            return cadastro;
        cursor.moveToFirst();
        do {
            try {
                cadastro.setIdCadastro(cursor.getInt(cursor.getColumnIndex("ID_CADASTRO")));
                cadastro.setLimiteCredito(cursor.getFloat(cursor.getColumnIndex("LIMITE_CREDITO")));
                cadastro.setFinanceiroVencido(cursor.getFloat(cursor.getColumnIndex("FINANCEIRO_VENCIDO")));
                cadastro.setFinanceiroVencer(cursor.getFloat(cursor.getColumnIndex("FINANCEIRO_VENCER")));
                cadastro.setFinanceiroQuitado(cursor.getFloat(cursor.getColumnIndex("FINANCEIRO_QUITADO")));
                cadastro.setPedidosLiberados(cursor.getFloat(cursor.getColumnIndex("PEDIDOS_LIBERADOS")));
                cadastro.setLimiteUtilizado(cursor.getFloat(cursor.getColumnIndex("LIMITE_UTILIZADO")));
                cadastro.setLimiteDisponivel(cursor.getFloat(cursor.getColumnIndex("LIMITE_DISPONIVEL")));
                cadastro.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
                cadastro.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
                cadastro.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                cadastro.setDataUltimaAtualizacao(cursor.getString(cursor.getColumnIndex("DATA_ULTIMA_ATUALIZACAO")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        return cadastro;
    }
}
