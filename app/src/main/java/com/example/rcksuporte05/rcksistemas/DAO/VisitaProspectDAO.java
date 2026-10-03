package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;

import java.util.ArrayList;
import java.util.List;

public class VisitaProspectDAO {
    private DBHelper db;
    public VisitaProspectDAO(DBHelper db) {
        this.db = db;
    }
    public VisitaProspect addUpdate(VisitaProspect visita) {
        ContentValues content = new ContentValues();
        try {
            content.put("DESCRICAO_VISTA", visita.getDescricaoVisita());
            content.put("DATA_VISITA", visita.getDataVisita());
            content.put("USUARIO_ID", visita.getUsuario_id());
            content.put("DATA_PROXIMA_VISITA", visita.getDataRetorno());
            content.put("TIPO_CONTATO", visita.getTipoContato());
            content.put("LATITUDE", visita.getLatitude());
            content.put("LONGITUDE", visita.getLongitude());
            content.put("ID_CADASTRO", visita.getProspect().getId_prospect());
            content.put("ID_CADASTRO_SERVIDOR", visita.getProspect().getId_cadastro());
            content.put("ID_VISITA_SERVIDOR", visita.getIdVisitaServidor());
            content.put("TITULO", visita.getTitulo());
            if (visita.getDataRetorno() != null && !visita.getDataRetorno().trim().equals("")) {
                ProspectDAO prospectDAO = new ProspectDAO(db);
                prospectDAO.updateDataProspect(visita.getDataRetorno(), visita.getProspect().getId_prospect());
                //atualizarDataVisitaProspect(visita.getDataRetorno(), visita.getProspect().getId_prospect());
            }
            CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
            if (visita.getFotoPrincipalBase64() != null) {
                cadastroAnexoDAO.addUpdate(visita.getFotoPrincipalBase64());
                //cadastroAnexoDAO.atualizarCadastroAnexo(visita.getFotoPrincipalBase64());
            }
            if (visita.getFotoSecundariaBase64() != null) {
                cadastroAnexoDAO.addUpdate(visita.getFotoSecundariaBase64());
                //cadastroAnexoDAO.atualizarCadastroAnexo(visita.getFotoSecundariaBase64());
            }
            if ((visita.getIdVisita() != null && db.contagem("SELECT COUNT(ID_VISITA) FROM TBL_VISITA_PROSPECT WHERE ID_VISITA = " + visita.getIdVisita()) > 0)
                    || (visita.getIdVisitaServidor() != null && db.contagem("SELECT COUNT(ID_VISITA_SERVIDOR) FROM TBL_VISITA_PROSPECT WHERE ID_VISITA_SERVIDOR = " + visita.getIdVisitaServidor()) > 0)) {
                content.put("ID_VISITA", visita.getIdVisita());
                db.updateDados("TBL_VISITA_PROSPECT", content, "ID_VISITA = " + visita.getIdVisita());
            } else {
                int id = (int) db.addDados("TBL_VISITA_PROSPECT", content);
                if ( id > 0 )
                    visita.setIdVisita(Integer.toString(id));
            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return visita;
    }

    public List<VisitaProspect> getLista(Prospect prospect) {
        List<VisitaProspect> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_VISITA_PROSPECT WHERE ID_CADASTRO = " + prospect.getId_prospect() + " ORDER BY ID_CADASTRO DESC;");
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            VisitaProspect visita = new VisitaProspect();
            try {
                visita.setIdVisita(cursor.getString(cursor.getColumnIndex("ID_VISITA")));
                visita.setDataVisita(cursor.getString(cursor.getColumnIndex("DATA_VISITA")));
                visita.setDataRetorno(cursor.getString(cursor.getColumnIndex("DATA_PROXIMA_VISITA")));
                visita.setTipoContato(cursor.getString(cursor.getColumnIndex("TIPO_CONTATO")));
                visita.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
                visita.setLatitude(cursor.getString(cursor.getColumnIndex("LATITUDE")));
                visita.setLongitude(cursor.getString(cursor.getColumnIndex("LONGITUDE")));
                visita.setDescricaoVisita(cursor.getString(cursor.getColumnIndex("DESCRICAO_VISTA")));
                visita.setIdVisitaServidor(cursor.getString(cursor.getColumnIndex("ID_VISITA_SERVIDOR")));
                visita.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                visita.setProspect(prospect);
                lista.add(visita);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public VisitaProspect getVisitaProspect(Prospect prospect) {
        VisitaProspect visita = new VisitaProspect();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_VISITA_PROSPECT WHERE ID_VISITA = " + prospect.getIdPrimeiraVisita() + ";");
        if (cursor.getCount() <= 0)
            return visita;
        cursor.moveToFirst();
        try {
            visita.setIdVisita(cursor.getString(cursor.getColumnIndex("ID_VISITA")));
            visita.setDataVisita(cursor.getString(cursor.getColumnIndex("DATA_VISITA")));
            visita.setDataRetorno(cursor.getString(cursor.getColumnIndex("DATA_PROXIMA_VISITA")));
            visita.setTipoContato(cursor.getString(cursor.getColumnIndex("TIPO_CONTATO")));
            visita.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            visita.setLatitude(cursor.getString(cursor.getColumnIndex("LATITUDE")));
            visita.setLongitude(cursor.getString(cursor.getColumnIndex("LONGITUDE")));
            visita.setDescricaoVisita(cursor.getString(cursor.getColumnIndex("DESCRICAO_VISTA")));
            visita.setIdVisitaServidor(cursor.getString(cursor.getColumnIndex("ID_VISITA_SERVIDOR")));
            visita.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
            visita.setProspect(prospect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        cursor.close();
        System.gc();
        return visita;
    }

    public List<VisitaProspect> getListaPendentes() {
        List<VisitaProspect> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM TBL_VISITA_PROSPECT WHERE ID_VISITA_SERVIDOR IS NULL");
        if (cursor.getCount() <= 0)
            return lista;
        ProspectDAO prospectDAO = new ProspectDAO(db);
        cursor.moveToFirst();
        do {
            VisitaProspect visita = new VisitaProspect();
            visita.setIdVisita(cursor.getString(cursor.getColumnIndex("ID_VISITA")));
            try {
                visita.setDataVisita(cursor.getString(cursor.getColumnIndex("DATA_VISITA")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setDataRetorno(cursor.getString(cursor.getColumnIndex("DATA_PROXIMA_VISITA")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setTipoContato(cursor.getString(cursor.getColumnIndex("TIPO_CONTATO")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setLatitude(cursor.getString(cursor.getColumnIndex("LATITUDE")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setLongitude(cursor.getString(cursor.getColumnIndex("LONGITUDE")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setDescricaoVisita(cursor.getString(cursor.getColumnIndex("DESCRICAO_VISTA")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                Prospect prospect = prospectDAO.getProspect( "SELECT * FROM TBL_PROSPECT WHERE ID_PROSPECT = " + cursor.getString(cursor.getColumnIndex("ID_CADASTRO")));
                visita.setProspect(prospect);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setIdVisitaServidor(cursor.getString(cursor.getColumnIndex("ID_VISITA_SERVIDOR")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                visita.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            lista.add(visita);
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }
}
