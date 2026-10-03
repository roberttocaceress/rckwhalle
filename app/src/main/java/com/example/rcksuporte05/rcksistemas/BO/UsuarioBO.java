package com.example.rcksuporte05.rcksistemas.BO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.TokenUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by RCK 03 on 06/10/2017.
 */

public class UsuarioBO {
    DBHelper db;

    public boolean add(List<Usuario> usuarioList, Context context) {
        db = new DBHelper(context);
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(db);
            db.deleteDados("TBL_WEB_USUARIO", "1", null);
            for (Usuario usuario : usuarioList)
                usuarioDAO.add(usuario);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addUsuario(List<String> usuarioList, Context context) {
        db = new DBHelper(context);
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(db);
            db.deleteDados("TBL_WEB_USUARIO", "1", null);
            for (String usuarioJson : usuarioList) {
                Gson gson = new Gson();
                TokenUtils.descryptografaUsuario(usuarioJson);
                Usuario usuario = gson.fromJson (TokenUtils.descryptografaUsuario(usuarioJson), Usuario.class);
                usuarioDAO.add(usuario);
            }

            //usuarioDAO.add(usuario);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /*
      foi criado para poder executar um inner join para montar um usuario com token no login
      automatico.
    */
    public Usuario getUsuarioLogin(Context context) {
        db = new DBHelper(context);
        SQLiteDatabase con = db.getReadableDatabase();
        Cursor cursor;
        cursor = con.rawQuery("SELECT U.*, L.TOKEN, L.USUARIO_LEMBRETE_SINCRONIA, L.USUARIO_LEMBRETE_SINCRONIA_DATA " +
                "FROM TBL_WEB_USUARIO U INNER JOIN TBL_LOGIN L ON U.LOGIN = L.LOGIN WHERE L.LOGADO = 'S'", null);
        if ( cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
            usuario.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            usuario.setNome_usuario(cursor.getString(cursor.getColumnIndex("NOME_USUARIO")));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex("LOGIN")));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
            usuario.setSenha_confirma(cursor.getString(cursor.getColumnIndex("SENHA_CONFIRMA")));
            usuario.setData_cadastro(cursor.getString(cursor.getColumnIndex("DATA_CADASTRO")));
            usuario.setUsuario_cadatro(cursor.getString(cursor.getColumnIndex("USUARIO_CADATRO")));
            usuario.setData_alterado(cursor.getString(cursor.getColumnIndex("DATA_ALTERADO")));
            usuario.setUsuario_alterou(cursor.getString(cursor.getColumnIndex("USUARIO_ALTEROU")));
            usuario.setAparece_cad_usuario(cursor.getString(cursor.getColumnIndex("APARECE_CAD_USUARIO")));
            usuario.setCliente_lista_todos(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_TODOS")));
            usuario.setCliente_lista_setor(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_SETOR")));
            usuario.setCliente_lista_representante(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_REPRESENTANTE")));
            usuario.setPedido_lista_todos(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_TODOS")));
            usuario.setPedido_lista_setor(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_SETOR")));
            usuario.setPedido_lista_representante(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_REPRESENTANTE")));
            usuario.setMensagem_lista_financeiro(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_FINANCEIRO")));
            usuario.setMensagem_lista_todos(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_TODOS")));
            usuario.setMensagem_lista_setor(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_SETOR")));
            usuario.setMensagem_lista_representante(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_REPRESENTANTE")));
            usuario.setOrcamento_lista_todos(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_TODOS")));
            usuario.setOrcamento_lista_setor(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_SETOR")));
            usuario.setOrcamento_lista_representante(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_REPRESENTANTE")));
            usuario.setUsuario_lista_todos(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_TODOS")));
            usuario.setUsuario_lista_setor(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_SETOR")));
            usuario.setUsuario_lista_representante(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_REPRESENTANTE")));
            usuario.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
            usuario.setId_setor(cursor.getString(cursor.getColumnIndex("ID_SETOR")));
            usuario.setId_quando_vendedor(cursor.getString(cursor.getColumnIndex("ID_QUANDO_VENDEDOR")));
            usuario.setAparelho_id(cursor.getString(cursor.getColumnIndex("APARELHO_ID")));
            usuario.setToken(cursor.getString(cursor.getColumnIndex("TOKEN")));
            usuario.setIdEmpresaMultiDevice(cursor.getString(cursor.getColumnIndex("ID_EMPRESA_MULTI_DEVICE")));
            //Novos campos
            usuario.setEmail_vendedor(cursor.getString(cursor.getColumnIndex("EMAIL_VENDEDOR")));
            usuario.setAparelho_key_firebase(cursor.getString(cursor.getColumnIndex("APARELHO_KEY_FIREBASE")));
            usuario.setAparelho_versao_app(cursor.getString(cursor.getColumnIndex("APARELHO_VERSAO_APP")));
            usuario.setAparelho_notifica_update_app(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_UPDATE_APP")));
            usuario.setAparelho_notifica_status_ped(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_STATUS_PED")));
            usuario.setAparelho_notifica_vencto_fin(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_VENCTO_FIN")));
            usuario.setAparelho_link_app(cursor.getString(cursor.getColumnIndex("APARELHO_LINK_APP")));
            usuario.setUsuario_tipo(cursor.getString(cursor.getColumnIndex("USUARIO_TIPO")));
            usuario.setUsuario_permite_n_logins(cursor.getString(cursor.getColumnIndex("USUARIO_PERMITE_N_LOGINS")));
            usuario.setUsuario_permite_biometria(cursor.getString(cursor.getColumnIndex("USUARIO_PERMITE_BIOMETRIA")));
            usuario.setUsuario_avaliacao_app( cursor.getInt(cursor.getColumnIndex("USUARIO_AVALIACAO_APP")));
            try {
                usuario.setUsuario_edita_preco_app(cursor.getString(cursor.getColumnIndex("USUARIO_EDITA_PRECO_APP")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_edita_preco_app("N");
            }
            try {
                usuario.setUsuario_lembrete_sincronia(cursor.getString(cursor.getColumnIndex("USUARIO_LEMBRETE_SINCRONIA")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_lembrete_sincronia("N");
            }
            try {
                usuario.setUsuario_lembrete_sincronia_data(cursor.getString(cursor.getColumnIndex("USUARIO_LEMBRETE_SINCRONIA_DATA")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_lembrete_sincronia_data("");
            }
            try{
                usuario.setUsuario_altera_cliente_ped(cursor.getString(cursor.getColumnIndex("USUARIO_ALTERA_CLIENTE_PED")));
            } catch (NullPointerException e) {
                usuario.setUsuario_altera_cliente_ped("S");
            }
            if ( usuario.getUsuario_altera_cliente_ped() == null || usuario.getUsuario_altera_cliente_ped().isEmpty()) {
                usuario.setUsuario_altera_cliente_ped("S");
            }
            return usuario;
        }
        return null;
    }

    public Usuario getUsuarioLogin(DBHelper db) {
        SQLiteDatabase con = db.getReadableDatabase();
        Cursor cursor;
        cursor = con.rawQuery("SELECT U.*, L.TOKEN, L.USUARIO_LEMBRETE_SINCRONIA, L.USUARIO_LEMBRETE_SINCRONIA_DATA " +
                "FROM TBL_WEB_USUARIO U INNER JOIN TBL_LOGIN L ON U.LOGIN = L.LOGIN WHERE L.LOGADO = 'S'", null);
        if ( cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
            usuario.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            usuario.setNome_usuario(cursor.getString(cursor.getColumnIndex("NOME_USUARIO")));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex("LOGIN")));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
            usuario.setSenha_confirma(cursor.getString(cursor.getColumnIndex("SENHA_CONFIRMA")));
            usuario.setData_cadastro(cursor.getString(cursor.getColumnIndex("DATA_CADASTRO")));
            usuario.setUsuario_cadatro(cursor.getString(cursor.getColumnIndex("USUARIO_CADATRO")));
            usuario.setData_alterado(cursor.getString(cursor.getColumnIndex("DATA_ALTERADO")));
            usuario.setUsuario_alterou(cursor.getString(cursor.getColumnIndex("USUARIO_ALTEROU")));
            usuario.setAparece_cad_usuario(cursor.getString(cursor.getColumnIndex("APARECE_CAD_USUARIO")));
            usuario.setCliente_lista_todos(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_TODOS")));
            usuario.setCliente_lista_setor(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_SETOR")));
            usuario.setCliente_lista_representante(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_REPRESENTANTE")));
            usuario.setPedido_lista_todos(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_TODOS")));
            usuario.setPedido_lista_setor(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_SETOR")));
            usuario.setPedido_lista_representante(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_REPRESENTANTE")));
            usuario.setMensagem_lista_financeiro(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_FINANCEIRO")));
            usuario.setMensagem_lista_todos(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_TODOS")));
            usuario.setMensagem_lista_setor(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_SETOR")));
            usuario.setMensagem_lista_representante(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_REPRESENTANTE")));
            usuario.setOrcamento_lista_todos(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_TODOS")));
            usuario.setOrcamento_lista_setor(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_SETOR")));
            usuario.setOrcamento_lista_representante(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_REPRESENTANTE")));
            usuario.setUsuario_lista_todos(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_TODOS")));
            usuario.setUsuario_lista_setor(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_SETOR")));
            usuario.setUsuario_lista_representante(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_REPRESENTANTE")));
            usuario.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
            usuario.setId_setor(cursor.getString(cursor.getColumnIndex("ID_SETOR")));
            usuario.setId_quando_vendedor(cursor.getString(cursor.getColumnIndex("ID_QUANDO_VENDEDOR")));
            usuario.setAparelho_id(cursor.getString(cursor.getColumnIndex("APARELHO_ID")));
            usuario.setToken(cursor.getString(cursor.getColumnIndex("TOKEN")));
            usuario.setIdEmpresaMultiDevice(cursor.getString(cursor.getColumnIndex("ID_EMPRESA_MULTI_DEVICE")));
            //Novos campos
            usuario.setEmail_vendedor(cursor.getString(cursor.getColumnIndex("EMAIL_VENDEDOR")));
            usuario.setAparelho_key_firebase(cursor.getString(cursor.getColumnIndex("APARELHO_KEY_FIREBASE")));
            usuario.setAparelho_versao_app(cursor.getString(cursor.getColumnIndex("APARELHO_VERSAO_APP")));
            usuario.setAparelho_notifica_update_app(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_UPDATE_APP")));
            usuario.setAparelho_notifica_status_ped(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_STATUS_PED")));
            usuario.setAparelho_notifica_vencto_fin(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_VENCTO_FIN")));
            usuario.setAparelho_link_app(cursor.getString(cursor.getColumnIndex("APARELHO_LINK_APP")));
            usuario.setUsuario_tipo(cursor.getString(cursor.getColumnIndex("USUARIO_TIPO")));
            usuario.setUsuario_permite_n_logins(cursor.getString(cursor.getColumnIndex("USUARIO_PERMITE_N_LOGINS")));
            usuario.setUsuario_permite_biometria(cursor.getString(cursor.getColumnIndex("USUARIO_PERMITE_BIOMETRIA")));
            usuario.setUsuario_avaliacao_app( cursor.getInt(cursor.getColumnIndex("USUARIO_AVALIACAO_APP")));
            try {
                usuario.setUsuario_edita_preco_app(cursor.getString(cursor.getColumnIndex("USUARIO_EDITA_PRECO_APP")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_edita_preco_app("N");
            }
            try {
                usuario.setUsuario_lembrete_sincronia(cursor.getString(cursor.getColumnIndex("USUARIO_LEMBRETE_SINCRONIA")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_lembrete_sincronia("N");
            }
            try {
                usuario.setUsuario_lembrete_sincronia_data(cursor.getString(cursor.getColumnIndex("USUARIO_LEMBRETE_SINCRONIA_DATA")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_lembrete_sincronia_data("");
            }
            try{
                usuario.setUsuario_altera_cliente_ped(cursor.getString(cursor.getColumnIndex("USUARIO_ALTERA_CLIENTE_PED")));
            } catch (NullPointerException e) {
                usuario.setUsuario_altera_cliente_ped("S");
            }
            if ( usuario.getUsuario_altera_cliente_ped() == null || usuario.getUsuario_altera_cliente_ped().isEmpty()) {
                usuario.setUsuario_altera_cliente_ped("S");
            }
            return usuario;
        }
        return null;
    }

    public Usuario getUsuarioLoginBiometria(DBHelper db) {
        SQLiteDatabase con = db.getReadableDatabase();
        Cursor cursor;
        cursor = con.rawQuery("SELECT U.*, L.TOKEN, L.USUARIO_LEMBRETE_SINCRONIA, L.USUARIO_LEMBRETE_SINCRONIA_DATA " +
                " FROM TBL_WEB_USUARIO U INNER JOIN TBL_LOGIN L ON U.LOGIN = L.LOGIN WHERE L.LOGADO = 'S'", null);
        if ( cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
            usuario.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            usuario.setNome_usuario(cursor.getString(cursor.getColumnIndex("NOME_USUARIO")));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex("LOGIN")));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
            usuario.setSenha_confirma(cursor.getString(cursor.getColumnIndex("SENHA_CONFIRMA")));
            usuario.setData_cadastro(cursor.getString(cursor.getColumnIndex("DATA_CADASTRO")));
            usuario.setUsuario_cadatro(cursor.getString(cursor.getColumnIndex("USUARIO_CADATRO")));
            usuario.setData_alterado(cursor.getString(cursor.getColumnIndex("DATA_ALTERADO")));
            usuario.setUsuario_alterou(cursor.getString(cursor.getColumnIndex("USUARIO_ALTEROU")));
            usuario.setAparece_cad_usuario(cursor.getString(cursor.getColumnIndex("APARECE_CAD_USUARIO")));
            usuario.setCliente_lista_todos(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_TODOS")));
            usuario.setCliente_lista_setor(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_SETOR")));
            usuario.setCliente_lista_representante(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_REPRESENTANTE")));
            usuario.setPedido_lista_todos(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_TODOS")));
            usuario.setPedido_lista_setor(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_SETOR")));
            usuario.setPedido_lista_representante(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_REPRESENTANTE")));
            usuario.setMensagem_lista_financeiro(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_FINANCEIRO")));
            usuario.setMensagem_lista_todos(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_TODOS")));
            usuario.setMensagem_lista_setor(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_SETOR")));
            usuario.setMensagem_lista_representante(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_REPRESENTANTE")));
            usuario.setOrcamento_lista_todos(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_TODOS")));
            usuario.setOrcamento_lista_setor(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_SETOR")));
            usuario.setOrcamento_lista_representante(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_REPRESENTANTE")));
            usuario.setUsuario_lista_todos(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_TODOS")));
            usuario.setUsuario_lista_setor(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_SETOR")));
            usuario.setUsuario_lista_representante(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_REPRESENTANTE")));
            usuario.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
            usuario.setId_setor(cursor.getString(cursor.getColumnIndex("ID_SETOR")));
            usuario.setId_quando_vendedor(cursor.getString(cursor.getColumnIndex("ID_QUANDO_VENDEDOR")));
            usuario.setAparelho_id(cursor.getString(cursor.getColumnIndex("APARELHO_ID")));
            usuario.setToken(cursor.getString(cursor.getColumnIndex("TOKEN")));
            usuario.setIdEmpresaMultiDevice(cursor.getString(cursor.getColumnIndex("ID_EMPRESA_MULTI_DEVICE")));
            //Novos campos
            usuario.setEmail_vendedor(cursor.getString(cursor.getColumnIndex("EMAIL_VENDEDOR")));
            usuario.setAparelho_key_firebase(cursor.getString(cursor.getColumnIndex("APARELHO_KEY_FIREBASE")));
            usuario.setAparelho_versao_app(cursor.getString(cursor.getColumnIndex("APARELHO_VERSAO_APP")));
            usuario.setAparelho_notifica_update_app(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_UPDATE_APP")));
            usuario.setAparelho_notifica_status_ped(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_STATUS_PED")));
            usuario.setAparelho_notifica_vencto_fin(cursor.getString(cursor.getColumnIndex("APARELHO_NOTIFICA_VENCTO_FIN")));
            usuario.setAparelho_link_app(cursor.getString(cursor.getColumnIndex("APARELHO_LINK_APP")));
            usuario.setUsuario_tipo(cursor.getString(cursor.getColumnIndex("USUARIO_TIPO")));
            usuario.setUsuario_permite_n_logins(cursor.getString(cursor.getColumnIndex("USUARIO_PERMITE_N_LOGINS")));
            usuario.setUsuario_permite_biometria(cursor.getString(cursor.getColumnIndex("USUARIO_PERMITE_BIOMETRIA")));
            usuario.setUsuario_avaliacao_app( cursor.getInt(cursor.getColumnIndex("USUARIO_AVALIACAO_APP")));
            try {
                usuario.setUsuario_edita_preco_app(cursor.getString(cursor.getColumnIndex("USUARIO_EDITA_PRECO_APP")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_edita_preco_app("N");
            }
            try {
                usuario.setUsuario_lembrete_sincronia(cursor.getString(cursor.getColumnIndex("USUARIO_LEMBRETE_SINCRONIA")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_lembrete_sincronia("N");
            }
            try {
                usuario.setUsuario_lembrete_sincronia_data(cursor.getString(cursor.getColumnIndex("USUARIO_LEMBRETE_SINCRONIA_DATA")));
            } catch ( NullPointerException e) {
                usuario.setUsuario_lembrete_sincronia_data("");
            }
            try{
                usuario.setUsuario_altera_cliente_ped(cursor.getString(cursor.getColumnIndex("USUARIO_ALTERA_CLIENTE_PED")));
            } catch (NullPointerException e) {
                usuario.setUsuario_altera_cliente_ped("S");
            }
            if ( usuario.getUsuario_altera_cliente_ped() == null || usuario.getUsuario_altera_cliente_ped().isEmpty()) {
                usuario.setUsuario_altera_cliente_ped("S");
            }
            return usuario;
        }
        return null;
    }
}
