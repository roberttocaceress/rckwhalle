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
        //Alteração no codigo tratando excessao
        //Roberto caceres
        //SQLiteDatabase db = getWritableDatabase();
        PedidoAnexo x = pedidoAnexo;
        try {
             return db.deleteDados("TBL_PEDIDO_ANEXOS", "ID_ANEXO = ? AND ID_PEDIDO = ? ", new String[]{String.valueOf(pedidoAnexo.getIdAnexo()), String.valueOf(pedidoAnexo.getIdPedido())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<PedidoAnexo> getLista(int idPedido, int idPedidoServidor) {
        String SQL = "";
        if ( idPedidoServidor <= 0) {
            SQL = "SELECT * FROM TBL_PEDIDO_ANEXOS WHERE ID_PEDIDO = ";
            return getLista(db.listaDados(SQL + idPedido + " ORDER BY ID_ANEXO;"));
        } else {
            SQL = "SELECT * FROM TBL_PEDIDO_ANEXOS WHERE ID_PEDIDO_SERVIDOR = ";
            return getLista(db.listaDados(SQL + idPedidoServidor + " ORDER BY ID_ANEXO;"));
        }

    }

    private List<PedidoAnexo> getLista(Cursor cursor) {
        List<PedidoAnexo> lista = new ArrayList<>();
        if (cursor.getCount() > 0)
            if (cursor.moveToFirst())
                do {
                    PedidoAnexo pedidoAnexo = new PedidoAnexo();
                    byte[] decodedString = Base64.decode( cursor.getString(cursor.getColumnIndex("ANEXO")).getBytes(), 0);
                    //byte[] decodedString = decompressB64(lista.get(position).getAnexo_1()).getBytes();
                    //holder.imageAnexo.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));//, 170, 180, false));
                    try {
                        pedidoAnexo.setIdAnexo(cursor.getInt(cursor.getColumnIndex("ID_ANEXO")));
                        pedidoAnexo.setIdAnexoServidor(cursor.getInt(cursor.getColumnIndex("ID_ANEXO_SERVIDOR")));
                        pedidoAnexo.setIdPedido(cursor.getInt(cursor.getColumnIndex("ID_PEDIDO")));
                        pedidoAnexo.setIdPedidoServidor(cursor.getInt(cursor.getColumnIndex("ID_PEDIDO_SERVIDOR")));
                        pedidoAnexo.setNomeAnexo(cursor.getString(cursor.getColumnIndex("NOME_ANEXO")));
                        pedidoAnexo.setAnexo(cursor.getString(cursor.getColumnIndex("ANEXO")));

                        pedidoAnexo.setMiniatura(Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length), 220, 230, false)
                                /*BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length)*/);

                        pedidoAnexo.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
                        pedidoAnexo.setPrincipal(cursor.getString(cursor.getColumnIndex("PRINCIPAL")));
                    } catch (Exception e) {
                        return lista;
                    }
                    lista.add(pedidoAnexo);
                } while (cursor.moveToNext());


        return lista;
    }
}
