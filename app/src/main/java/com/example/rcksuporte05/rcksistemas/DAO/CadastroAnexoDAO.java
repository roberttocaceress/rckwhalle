package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;

import java.util.ArrayList;
import java.util.List;

public class CadastroAnexoDAO {

    private static final String TAG = "CADASTRO_ANEXO_DAO";
    private DBHelper db;

    public CadastroAnexoDAO(DBHelper db) {
        this.db = db;
    }

    public void addUpdate(CadastroAnexo cadastroAnexo) {
        ContentValues content = new ContentValues();
        content.put("ID_ANEXO_SERVIDOR", cadastroAnexo.getIdAnexoServidor());
        content.put("ID_ENTIDADE", cadastroAnexo.getIdEntidade());
        content.put("ID_CADASTRO", cadastroAnexo.getIdCadastro());
        content.put("ID_CADASTRO_SERVIDOR", cadastroAnexo.getIdCadastroServidor());
        content.put("NOME_ANEXO", cadastroAnexo.getNomeAnexo());
        content.put("ANEXO", cadastroAnexo.getAnexo());
        content.put("EXCLUIDO", cadastroAnexo.getExcluido());
        content.put("PRINCIPAL", cadastroAnexo.getPrincipal());
        if (cadastroAnexo.getIdAnexo() != 0 && db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_ANEXO = " + cadastroAnexo.getIdAnexo() + ";") > 0)
            db.updateDados("TBL_CADASTRO_ANEXOS", content, "ID_ANEXO = " + cadastroAnexo.getIdAnexo() + ";");
        else
            db.addDados("TBL_CADASTRO_ANEXOS", content);
    }

    public List<CadastroAnexo> getLista(int idCadastro, int id_entidade) {
        String SQL = "SELECT * FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = ";
        if (id_entidade == 0)
            return getLista(db.listaDados(SQL + idCadastro + " ORDER BY ID_ANEXO;"));
        else if (id_entidade == 1)
            return getLista(db.listaDados(SQL + idCadastro + " AND ID_ENTIDADE = 1 AND EXCLUIDO = 'N' ORDER BY ID_ANEXO;"));
        else if (id_entidade == 10)
            return getLista(db.listaDados(SQL + idCadastro + " AND ID_ENTIDADE = 10 AND EXCLUIDO = 'N' ORDER BY ID_ANEXO;"));
        else if (id_entidade == 11)
            return getLista(db.listaDados(SQL + idCadastro + " AND ID_ENTIDADE = 11 AND EXCLUIDO = 'N' ORDER BY ID_ANEXO;"));
        else
            return null;
    }

    private List<CadastroAnexo> getLista(Cursor cursor) {
        List<CadastroAnexo> lista = new ArrayList<>();
        if (cursor == null) return lista;
        try {
            if (cursor.moveToFirst()) {
                do {
                    lista.add(setCadastroAnexo(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    private CadastroAnexo setCadastroAnexo(Cursor cursor) {
        CadastroAnexo item = new CadastroAnexo();
        try {
            item.setIdAnexo(getInt(cursor, "ID_ANEXO"));
            item.setIdAnexoServidor(getInt(cursor, "ID_ANEXO_SERVIDOR"));
            item.setIdEntidade(getInt(cursor, "ID_ENTIDADE"));
            item.setIdCadastro(getInt(cursor, "ID_CADASTRO"));
            item.setIdCadastroServidor(getInt(cursor, "ID_CADASTRO_SERVIDOR"));
            item.setNomeAnexo(getString(cursor, "NOME_ANEXO"));
            item.setAnexo(getString(cursor, "ANEXO"));
            item.setExcluido(getString(cursor, "EXCLUIDO"));
            item.setPrincipal(getString(cursor, "PRINCIPAL"));
        } catch (Exception ignored) {
        }
        return item;
    }

    // =========================================================================
    // AUXILIARES OBRIGATÓRIOS (CURSOR NULL-SAFE)
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


}
