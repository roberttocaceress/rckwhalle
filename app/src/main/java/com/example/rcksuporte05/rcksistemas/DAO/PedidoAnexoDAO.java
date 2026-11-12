package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.PedidoAnexo;

import java.util.ArrayList;
import java.util.List;

public class PedidoAnexoDAO {
    private DBHelper db;

    public PedidoAnexoDAO(DBHelper db) {
        this.db = db;
    }

    public void add(PedidoAnexo pedidoAnexo) {
        ContentValues content = new ContentValues();
        content.put("ID_ANEXO_SERVIDOR", pedidoAnexo.getIdAnexoServidor());
        content.put("ID_PEDIDO", pedidoAnexo.getIdPedido());
        content.put("ID_PEDIDO_SERVIDOR", pedidoAnexo.getIdPedidoServidor());
        content.put("NOME_ANEXO", pedidoAnexo.getNomeAnexo());
        content.put("ANEXO", pedidoAnexo.getAnexo());
        content.put("EXCLUIDO", pedidoAnexo.getExcluido());
        content.put("PRINCIPAL", pedidoAnexo.getPrincipal());
        db.addDados("TBL_PEDIDO_ANEXOS", content);
    }

    public boolean update(PedidoAnexo pedidoAnexo) {
        ContentValues content = new ContentValues();
        content.put("ID_ANEXO_SERVIDOR", pedidoAnexo.getIdAnexoServidor());
        content.put("ID_PEDIDO_SERVIDOR", pedidoAnexo.getIdPedidoServidor());
        return db.updateDados("TBL_PEDIDO_ANEXOS", content, "ID_ANEXO = " + pedidoAnexo.getIdAnexo()) >= 1;
    }

    public int delete(PedidoAnexo pedidoAnexo) {
        try {
             return db.deleteDados("TBL_PEDIDO_ANEXOS", "ID_ANEXO = ? AND ID_PEDIDO = ? ", new String[]{String.valueOf(pedidoAnexo.getIdAnexo()), String.valueOf(pedidoAnexo.getIdPedido())});
        } catch (Exception ignored) {
        }
        return 0;
    }

    public List<PedidoAnexo> getLista(int idPedido, int idPedidoServidor) {
        if (idPedidoServidor <= 0)
           return getLista(db.listaDados("SELECT * FROM TBL_PEDIDO_ANEXOS WHERE ID_PEDIDO = " + idPedido + " ORDER BY ID_ANEXO;"));
        else
           return getLista(db.listaDados("SELECT * FROM TBL_PEDIDO_ANEXOS WHERE ID_PEDIDO_SERVIDOR = " + idPedidoServidor + " ORDER BY ID_ANEXO;"));
    }

    private List<PedidoAnexo> getLista(Cursor cursor) {
        List<PedidoAnexo> lista = new ArrayList<>();
        if (cursor == null) return lista;
        try {
            if (cursor.moveToFirst()) {
                do
                    lista.add(setPedidoAnexo(cursor));
                while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
            //e.printStackTrace();
        } finally {
            if (!cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    private PedidoAnexo setPedidoAnexo(Cursor cursor) {
        PedidoAnexo pedidoAnexo = new PedidoAnexo();
        try {
            pedidoAnexo.setIdAnexo(getInt(cursor, "ID_ANEXO"));
            pedidoAnexo.setIdAnexoServidor(getInt(cursor, "ID_ANEXO_SERVIDOR"));
            pedidoAnexo.setIdPedido(getInt(cursor, "ID_PEDIDO"));
            pedidoAnexo.setIdPedidoServidor(getInt(cursor, "ID_PEDIDO_SERVIDOR"));
            pedidoAnexo.setNomeAnexo(getString(cursor, "NOME_ANEXO"));
            pedidoAnexo.setAnexo(getString(cursor, "ANEXO"));
            if (pedidoAnexo.getAnexo() != null && !pedidoAnexo.getAnexo().trim().isEmpty())
                try {
                    byte[] decodedString = Base64.decode(pedidoAnexo.getAnexo().trim(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    if (bitmap != null) {
                        pedidoAnexo.setMiniatura(Bitmap.createScaledBitmap(bitmap, 220, 230, false));
                    }
                } catch (Exception ignored) {}
            pedidoAnexo.setExcluido(getString(cursor, "EXCLUIDO"));
            pedidoAnexo.setPrincipal(getString(cursor, "PRINCIPAL"));
        } catch (Exception ignored) {
        }
        return pedidoAnexo;
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

    private long getLong(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0L;
        try {
            return Long.parseLong(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private double getDouble(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0.0;
        try {
            return Double.parseDouble(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
