package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Prospect;

import java.util.ArrayList;
import java.util.List;

public class ProspectDAO {
    private DBHelper db;

    public ProspectDAO(DBHelper db) {
        this.db = db;
    }

    public Prospect addUpdate(Prospect prospect) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_PROSPECT_SERVIDOR", prospect.getId_prospect_servidor());
            content.put("ID_CADASTRO", prospect.getId_cadastro());
            try {
                content.put("ID_MOTIVO_NAO_CADASTRAMENTO", prospect.getMotivoNaoCadastramento().getIdItem());
            } catch (NullPointerException e) {
                content.put("ID_MOTIVO_NAO_CADASTRAMENTO", 0);
                e.printStackTrace();
            }
            content.put("NOME_CADASTRO", prospect.getNome_cadastro());
            content.put("NOME_FANTASIA", prospect.getNome_fantasia());
            content.put("PESSOA_F_J", prospect.getPessoa_f_j());
            content.put("CPF_CNPJ", prospect.getCpf_cnpj());
            content.put("INSCRI_ESTADUAL", prospect.getInscri_estadual());
            content.put("ENDERECO", prospect.getEndereco());
            content.put("ENDERECO_BAIRRO", prospect.getEndereco_bairro());
            content.put("ENDERECO_NUMERO", prospect.getEndereco_numero());
            content.put("ENDERECO_COMPLEMENTO", prospect.getEndereco_complemento());
            content.put("ENDERECO_UF", prospect.getEndereco_uf());
            content.put("NOME_MUNICIPIO", prospect.getNome_municipio());
            content.put("ENDERECO_CEP", prospect.getEndereco_cep());
            content.put("ID_PAIS", prospect.getId_pais());
            content.put("USUARIO_ID", prospect.getUsuario_id());
            content.put("USUARIO_NOME", prospect.getUsuario_nome());
            content.put("SITUACAO_PREDIO", prospect.getSituacaoPredio());
            content.put("LIMITE_CREDITO_SUGERIDO", prospect.getLimiteDeCreditoSugerido());
            content.put("LIMITE_PRAZO_SUGERIDO", prospect.getLimiteDePrazoSugerido());
            content.put("ID_EMPRESA", prospect.getIdEmpresa());
            content.put("DATA_RETORNO", prospect.getDataRetorno());
            content.put("PROSPECT_SALVO", prospect.getProspectSalvo());
            content.put("IND_DA_IE_DESTINATARIO_PROSPECT", prospect.getInd_da_ie_destinatario_prospect());
            content.put("LATITUDE", prospect.getLatitude());
            content.put("LONGITUDE", prospect.getLongitude());
            content.put("USUARIO_DATA", prospect.getUsuario_data());
            content.put("ID_VENDEDOR", prospect.getIdVendedor());
            content.put("ID_CATEGORIA", prospect.getIdCategoria());
            content.put("OBSERVACOES_COMERCIAIS", prospect.getObservacoesComerciais());
            content.put("ID_PRIMEIRA_VISITA", prospect.getIdPrimeiraVisita());
            content.put("TELEFONE", prospect.getTelefone());
            try {
                content.put("DESCRICAO_MOTIVO_NAO_CAD", prospect.getMotivoNaoCadastramento().getDescricaoOutros());
            } catch (NullPointerException e) {
                content.put("DESCRICAO_MOTIVO_NAO_CAD", 0);
                e.printStackTrace();
            }
            CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
            if (prospect.getFotoPrincipalBase64() != null) {
                cadastroAnexoDAO.addUpdate(prospect.getFotoPrincipalBase64());
            }

            if (prospect.getFotoSecundariaBase64() != null)
                cadastroAnexoDAO.addUpdate(prospect.getFotoSecundariaBase64());

            if (prospect.getId_prospect() != null && db.contagem("SELECT COUNT(ID_PROSPECT) FROM TBL_PROSPECT WHERE ID_PROSPECT = " + prospect.getId_prospect()) > 0) {
                content.put("ID_PROSPECT", prospect.getId_prospect());
                db.updateDados("TBL_PROSPECT", content, "ID_PROSPECT = " + prospect.getId_prospect());
            } else {
                content.put("ID_PROSPECT", prospect.getId_prospect());
                int id = (int) db.addDados("TBL_PROSPECT", content);
                if ( id > 0)
                    prospect.setId_prospect(Integer.toString(id));

            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prospect;
    }

    public void updateDataProspect(String novaData, String idProspect) {
        ContentValues content = new ContentValues();
        content.put("DATA_RETORNO", novaData);
        db.updateDados("TBL_PROSPECT", content, "ID_PROSPECT = " + idProspect);
    }

    public int delete(Prospect prospect, String tipo) {  //0 == local 1 = Servidor
        if (tipo.equalsIgnoreCase("0")) {
            db.deleteDados("TBL_REFERENCIA_BANCARIA", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{prospect.getId_prospect(),"10"});
            db.deleteDados("TBL_REFERENCIA_COMERCIAL", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{prospect.getId_prospect(),"10"});
            db.deleteDados("TBL_CADASTRO_CONTATO", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{prospect.getId_prospect(),"10"});
            db.deleteDados("TBL_PROSPECT", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{prospect.getId_prospect(),"10"});
       } else {
            db.deleteDados("TBL_REFERENCIA_BANCARIA", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{prospect.getId_cadastro(),"10"});
            db.deleteDados("TBL_REFERENCIA_COMERCIAL", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{prospect.getId_cadastro(),"10"});
            db.deleteDados("TBL_CADASTRO_CONTATO", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{prospect.getId_cadastro(),"10"});
            db.deleteDados("TBL_PROSPECT", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{prospect.getId_cadastro(),"10"});
        }
        return 0;
    }
    public List<Prospect> getLista(int parametro) {
        List<Prospect> lista = new ArrayList<>();
        CadastroMotivoDAO cadastroMotivoDAO = new CadastroMotivoDAO(db);
        Cursor cursor;
        switch (parametro) {
            case 0:
                cursor = db.listaDados("SELECT * FROM TBL_PROSPECT WHERE USUARIO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND ID_PROSPECT_SERVIDOR IS NULL ORDER BY ID_PROSPECT DESC");
                break;
            case 1:
                cursor = db.listaDados("SELECT * FROM TBL_PROSPECT WHERE USUARIO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND PROSPECT_SALVO = 'N' AND ID_PROSPECT_SERVIDOR IS NULL ORDER BY ID_PROSPECT DESC");
                break;
            case 2:
                cursor = db.listaDados("SELECT * FROM TBL_PROSPECT WHERE USUARIO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND PROSPECT_SALVO = 'S' AND ID_PROSPECT_SERVIDOR IS NULL ORDER BY ID_PROSPECT DESC");
                break;
            case 3:
                cursor = db.listaDados("SELECT * FROM TBL_PROSPECT WHERE USUARIO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND PROSPECT_SALVO = 'S' AND ID_PROSPECT_SERVIDOR IS NOT NULL ORDER BY DATA_RETORNO, ID_PROSPECT_SERVIDOR;");
                break;
            default:
                cursor = db.listaDados("SELECT * FROM TBL_PROSPECT WHERE USUARIO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " ORDER BY ID_PROSPECT DESC");
        }
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Prospect prospect = new Prospect();
            try {
                prospect.setId_prospect(cursor.getString(cursor.getColumnIndex("ID_PROSPECT")));
                prospect.setId_prospect_servidor(cursor.getString(cursor.getColumnIndex("ID_PROSPECT_SERVIDOR")));
                prospect.setId_cadastro(cursor.getString(cursor.getColumnIndex("ID_CADASTRO")));

            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                //verificar
                prospect.setMotivoNaoCadastramento(cadastroMotivoDAO.getMotivo(cursor.getString(cursor.getColumnIndex("ID_MOTIVO_NAO_CADASTRAMENTO"))));
                prospect.getMotivoNaoCadastramento().setDescricaoOutros(cursor.getString(cursor.getColumnIndex("DESCRICAO_MOTIVO_NAO_CAD")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            prospect.setNome_cadastro(cursor.getString(cursor.getColumnIndex("NOME_CADASTRO")));
            prospect.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
            prospect.setPessoa_f_j(cursor.getString(cursor.getColumnIndex("PESSOA_F_J")));
            prospect.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
            prospect.setInscri_estadual(cursor.getString(cursor.getColumnIndex("INSCRI_ESTADUAL")));
            prospect.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
            prospect.setEndereco_bairro(cursor.getString(cursor.getColumnIndex("ENDERECO_BAIRRO")));
            prospect.setEndereco_numero(cursor.getString(cursor.getColumnIndex("ENDERECO_NUMERO")));
            prospect.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
            prospect.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
            prospect.setNome_municipio(cursor.getString(cursor.getColumnIndex("NOME_MUNICIPIO")));
            prospect.setEndereco_cep(cursor.getString(cursor.getColumnIndex("ENDERECO_CEP")));
            prospect.setId_pais(cursor.getString(cursor.getColumnIndex("ID_PAIS")));
            prospect.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            prospect.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            prospect.setSituacaoPredio(cursor.getString(cursor.getColumnIndex("SITUACAO_PREDIO")));
            prospect.setLimiteDeCreditoSugerido(cursor.getString(cursor.getColumnIndex("LIMITE_CREDITO_SUGERIDO")));
            prospect.setLimiteDePrazoSugerido(cursor.getString(cursor.getColumnIndex("LIMITE_PRAZO_SUGERIDO")));
            prospect.setIdEmpresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            prospect.setDataRetorno(cursor.getString(cursor.getColumnIndex("DATA_RETORNO")));
            prospect.setObservacoesComerciais(cursor.getString(cursor.getColumnIndex("OBSERVACOES_COMERCIAIS")));
            prospect.setProspectSalvo(cursor.getString(cursor.getColumnIndex("PROSPECT_SALVO")));
            prospect.setInd_da_ie_destinatario_prospect(cursor.getString(cursor.getColumnIndex("IND_DA_IE_DESTINATARIO_PROSPECT")));
            prospect.setLongitude(cursor.getString(cursor.getColumnIndex("LONGITUDE")));
            prospect.setLatitude(cursor.getString(cursor.getColumnIndex("LATITUDE")));
            prospect.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            prospect.setIdVendedor(cursor.getInt(cursor.getColumnIndex("ID_VENDEDOR")));
            prospect.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
            prospect.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
            prospect.setEnderecoGps(cursor.getString(cursor.getColumnIndex("ENDERECO_GPS")));
            prospect.setIdPrimeiraVisita(cursor.getInt(cursor.getColumnIndex("ID_PRIMEIRA_VISITA")));
            lista.add(prospect);
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public Prospect getProspect(String sql) {
        Prospect prospect = new Prospect();
        Cursor cursor = db.listaDados(sql);
        if ( cursor.getCount() <= 0)
            return  prospect;
        CadastroMotivoDAO cadastroMotivoDAO = new CadastroMotivoDAO(db);
        cursor.moveToFirst();
        try {
            try {
                prospect.setId_prospect(cursor.getString(cursor.getColumnIndex("ID_PROSPECT")));
                prospect.setId_prospect_servidor(cursor.getString(cursor.getColumnIndex("ID_PROSPECT_SERVIDOR")));
                prospect.setId_cadastro(cursor.getString(cursor.getColumnIndex("ID_CADASTRO")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                //Analisar aqui
                prospect.setMotivoNaoCadastramento(cadastroMotivoDAO.getMotivo(cursor.getString(cursor.getColumnIndex("ID_MOTIVO_NAO_CADASTRAMENTO"))));
                prospect.getMotivoNaoCadastramento().setDescricaoOutros(cursor.getString(cursor.getColumnIndex("DESCRICAO_MOTIVO_NAO_CAD")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            prospect.setNome_cadastro(cursor.getString(cursor.getColumnIndex("NOME_CADASTRO")));
            prospect.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
            prospect.setPessoa_f_j(cursor.getString(cursor.getColumnIndex("PESSOA_F_J")));
            prospect.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
            prospect.setInscri_estadual(cursor.getString(cursor.getColumnIndex("INSCRI_ESTADUAL")));
            prospect.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
            prospect.setEndereco_bairro(cursor.getString(cursor.getColumnIndex("ENDERECO_BAIRRO")));
            prospect.setEndereco_numero(cursor.getString(cursor.getColumnIndex("ENDERECO_NUMERO")));
            prospect.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
            prospect.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
            prospect.setNome_municipio(cursor.getString(cursor.getColumnIndex("NOME_MUNICIPIO")));
            prospect.setEndereco_cep(cursor.getString(cursor.getColumnIndex("ENDERECO_CEP")));
            prospect.setId_pais(cursor.getString(cursor.getColumnIndex("ID_PAIS")));
            prospect.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            prospect.setSituacaoPredio(cursor.getString(cursor.getColumnIndex("SITUACAO_PREDIO")));
            prospect.setLimiteDeCreditoSugerido(cursor.getString(cursor.getColumnIndex("LIMITE_CREDITO_SUGERIDO")));
            prospect.setLimiteDePrazoSugerido(cursor.getString(cursor.getColumnIndex("LIMITE_PRAZO_SUGERIDO")));
            prospect.setIdEmpresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            prospect.setDataRetorno(cursor.getString(cursor.getColumnIndex("DATA_RETORNO")));
            prospect.setObservacoesComerciais(cursor.getString(cursor.getColumnIndex("OBSERVACOES_COMERCIAIS")));
            prospect.setProspectSalvo(cursor.getString(cursor.getColumnIndex("PROSPECT_SALVO")));
            prospect.setInd_da_ie_destinatario_prospect(cursor.getString(cursor.getColumnIndex("IND_DA_IE_DESTINATARIO_PROSPECT")));
            prospect.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            prospect.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
            prospect.setEnderecoGps(cursor.getString(cursor.getColumnIndex("ENDERECO_GPS")));
        } catch ( CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return  prospect;
    }
}
