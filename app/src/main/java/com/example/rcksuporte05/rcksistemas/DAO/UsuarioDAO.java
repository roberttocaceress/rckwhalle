package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.rcksuporte05.rcksistemas.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private DBHelper db;
    public UsuarioDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Usuario usuario) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ID_USUARIO", usuario.getId_usuario());
            content.put("ATIVO", usuario.getAtivo());
            content.put("NOME_USUARIO", usuario.getNome_usuario());
            content.put("LOGIN", usuario.getLogin());
            content.put("SENHA", usuario.getSenha());
            content.put("SENHA_CONFIRMA", usuario.getSenha_confirma());
            content.put("DATA_CADASTRO", usuario.getData_cadastro());
            content.put("USUARIO_CADATRO", usuario.getUsuario_cadatro());
            content.put("DATA_ALTERADO", usuario.getData_alterado());
            content.put("USUARIO_ALTEROU", usuario.getUsuario_alterou());
            content.put("APARECE_CAD_USUARIO", usuario.getAparece_cad_usuario());
            content.put("CLIENTE_LISTA_TODOS", usuario.getCliente_lista_todos());
            content.put("CLIENTE_LISTA_SETOR", usuario.getCliente_lista_setor());
            content.put("CLIENTE_LISTA_REPRESENTANTE", usuario.getCliente_lista_representante());
            content.put("PEDIDO_LISTA_TODOS", usuario.getPedido_lista_todos());
            content.put("PEDIDO_LISTA_SETOR", usuario.getPedido_lista_setor());
            content.put("PEDIDO_LISTA_REPRESENTANTE", usuario.getPedido_lista_representante());
            content.put("MENSAGEM_LISTA_FINANCEIRO", usuario.getMensagem_lista_financeiro());
            content.put("MENSAGEM_LISTA_TODOS", usuario.getMensagem_lista_todos());
            content.put("MENSAGEM_LISTA_SETOR", usuario.getMensagem_lista_setor());
            content.put("MENSAGEM_LISTA_REPRESENTANTE", usuario.getMensagem_lista_representante());
            content.put("ORCAMENTO_LISTA_TODOS", usuario.getOrcamento_lista_todos());
            content.put("ORCAMENTO_LISTA_SETOR", usuario.getOrcamento_lista_setor());
            content.put("ORCAMENTO_LISTA_REPRESENTANTE", usuario.getOrcamento_lista_representante());
            content.put("USUARIO_LISTA_TODOS", usuario.getUsuario_lista_todos());
            content.put("USUARIO_LISTA_SETOR", usuario.getUsuario_lista_setor());
            content.put("USUARIO_LISTA_REPRESENTANTE", usuario.getUsuario_lista_representante());
            content.put("EXCLUIDO", usuario.getExcluido());
            content.put("ID_SETOR", usuario.getId_setor());
            content.put("ID_QUANDO_VENDEDOR", usuario.getId_quando_vendedor());
            content.put("APARELHO_ID", usuario.getAparelho_id());
            content.put("ID_EMPRESA_MULTI_DEVICE", usuario.getIdEmpresaMultiDevice());
            content.put("EMAIL_VENDEDOR", usuario.getEmail_vendedor());
            content.put("APARELHO_KEY_FIREBASE", usuario.getAparelho_key_firebase());
            content.put(" APARELHO_VERSAO_APP", usuario.getAparelho_versao_app());
            content.put("APARELHO_NOTIFICA_UPDATE_APP", usuario.getAparelho_notifica_update_app());
            content.put("APARELHO_NOTIFICA_STATUS_PED", usuario.getAparelho_notifica_status_ped());
            content.put("APARELHO_NOTIFICA_VENCTO_FIN", usuario.getAparelho_notifica_vencto_fin());
            content.put("APARELHO_LINK_APP", usuario.getAparelho_link_app());
            content.put("USUARIO_TIPO", usuario.getUsuario_tipo());
            if (usuario.getUsuario_tipo().equalsIgnoreCase("D"))
                content.put("USUARIO_PERMITE_N_LOGINS", "S");
            else
                content.put("USUARIO_PERMITE_N_LOGINS", usuario.getUsuario_permite_n_logins());
            content.put("USUARIO_PERMITE_BIOMETRIA", usuario.getUsuario_permite_biometria());
            content.put("USUARIO_AVALIACAO_APP", usuario.getUsuario_avaliacao_app());
            content.put("USUARIO_EDITA_PRECO_APP", usuario.getUsuario_edita_preco_app());
            content.put("USUARIO_ID_CIDADE", usuario.getUsuario_id_cidade());
            content.put("USUARIO_UF", usuario.getUsuario_uf());
            content.put("USUARIO_ALTERA_CLIENTE_PED", usuario.getUsuario_altera_cliente_ped() == null || usuario.getUsuario_altera_cliente_ped().isEmpty() ? "S" : usuario.getUsuario_altera_cliente_ped() );
            System.gc();
            return db.addDados("TBL_WEB_USUARIO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Usuario> getLista(String SQL) {
        List<Usuario> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Usuario usuario = new Usuario();
            try {
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
                usuario.setIdEmpresaMultiDevice(cursor.getString(cursor.getColumnIndex("ID_EMPRESA_MULTI_DEVICE")));
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
                try {
                    usuario.setUsuario_avaliacao_app(Integer.parseInt(cursor.getString(cursor.getColumnIndex("USUARIO_AVALIACAO_APP"))));
                } catch ( NullPointerException|NumberFormatException e) {
                    usuario.setUsuario_avaliacao_app(0);
                }
                try {
                    usuario.setUsuario_edita_preco_app(cursor.getString(cursor.getColumnIndex("USUARIO_EDITA_PRECO_APP")));
                } catch ( NullPointerException e) {
                    usuario.setUsuario_edita_preco_app("N");
                }


                try {
                    int idxSincCliente = cursor.getColumnIndex("DATA_SINCRONIA_CLIENTE");
                    if (idxSincCliente >= 0) usuario.setData_sincronia_cliente(cursor.getString(idxSincCliente));
                    
                    int idxSincProd = cursor.getColumnIndex("DATA_SINCRONIA_PRODUTO");
                    if (idxSincProd >= 0) usuario.setData_sincronia_produto(cursor.getString(idxSincProd));
                    
                    int idxSincImg = cursor.getColumnIndex("DATA_SINCRONIA_IMAGEN");
                    if (idxSincImg >= 0) usuario.setData_sincronia_imagem(cursor.getString(idxSincImg));
                    
                    int idxSincCob = cursor.getColumnIndex("DATA_SINCRONIA_COBRANCA");
                    if (idxSincCob >= 0) usuario.setData_sincronia_cobranca(cursor.getString(idxSincCob));
                    
                    int idxSaldoVerba = cursor.getColumnIndex("VALOR_SALDO_VERBA");
                    if (idxSaldoVerba >= 0) usuario.setSaldo_verba(cursor.getString(idxSaldoVerba));
                    
                    int idxDataVerba = cursor.getColumnIndex("DATA_ATUALIZA_VERBA");
                    if (idxDataVerba >= 0) usuario.setData_atualizao_verba(cursor.getString(idxDataVerba));
                    
                    int idxSenhaLogado = cursor.getColumnIndex("SENHA_LOGADO");
                    if (idxSenhaLogado >= 0) usuario.setSenha(cursor.getString(idxSenhaLogado));
                } catch (NullPointerException|NumberFormatException e) {
                    usuario.setSaldo_verba("0.00");
                    usuario.setData_atualizao_verba("");
                } catch (Exception e) {
                    usuario.setSaldo_verba("0.00");
                    usuario.setData_atualizao_verba("");
                    e.printStackTrace();
                }
                usuario.setUsuario_id_cidade(cursor.getInt(cursor.getColumnIndex("USUARIO_ID_CIDADE")));
                usuario.setUsuario_uf(cursor.getString(cursor.getColumnIndex("USUARIO_UF")));
                usuario.setUsuario_altera_cliente_ped(cursor.getString(cursor.getColumnIndex("USUARIO_ALTERA_CLIENTE_PED")));

                lista.add(usuario);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public int updateAvalicao(int id_usuario, int avalicao) {
        ContentValues content = new ContentValues();
        try {
            content.put("USUARIO_AVALIACAO_APP", avalicao);
            System.gc();
            return db.updateDados("TBL_WEB_USUARIO", content, "ID_USUARIO = " + id_usuario);
        } catch ( NullPointerException|SQLException e){
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
