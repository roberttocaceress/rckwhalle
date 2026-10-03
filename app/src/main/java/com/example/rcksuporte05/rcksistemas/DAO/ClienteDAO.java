package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.ClienteResumo;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private DBHelper db;

    public ClienteDAO(DBHelper db) {
        this.db = db;
    }

    public long add(Cliente cliente) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", cliente.getAtivo());
            content.put("ID_EMPRESA", cliente.getId_empresa());
            //content.put("ID_CADASTRO", cliente.getId_cadastro());
            content.put("ID_CADASTRO_SERVIDOR", cliente.getId_cadastro_servidor());
            content.put("ID_PROSPECT", cliente.getId_prospect());
            content.put("PESSOA_F_J", cliente.getPessoa_f_j());
            content.put("DATA_ANIVERSARIO", cliente.getData_aniversario());
            content.put("NOME_CADASTRO", cliente.getNome_cadastro());
            content.put("NOME_FANTASIA", cliente.getNome_fantasia());
            content.put("CPF_CNPJ", cliente.getCpf_cnpj());
            content.put("INSCRI_ESTADUAL", cliente.getInscri_estadual());

            content.put("ENDERECO", cliente.getEndereco());
            content.put("ENDERECO_BAIRRO", cliente.getEndereco_bairro());
            content.put("ENDERECO_NUMERO", cliente.getEndereco_numero());
            content.put("ENDERECO_COMPLEMENTO", cliente.getEndereco_complemento());
            content.put("ENDERECO_UF", cliente.getEndereco_uf());
            content.put("NOME_MUNICIPIO", cliente.getNome_municipio());
            content.put("ENDERECO_CEP", cliente.getEndereco_cep());
            content.put("USUARIO_ID", cliente.getUsuario_id());
            content.put("USUARIO_NOME", cliente.getUsuario_nome());
            content.put("USUARIO_DATA", cliente.getUsuario_data());
            content.put("F_CLIENTE", cliente.getF_cliente());
            content.put("F_FORNECEDOR", cliente.getF_fornecedor());
            content.put("F_FUNCIONARIO", cliente.getF_funcionario());
            content.put("F_VENDEDOR", cliente.getF_vendedor());
            content.put("F_TRANSPORTADOR", cliente.getF_transportador());
            content.put("DATA_ULTIMA_COMPRA", cliente.getData_ultima_compra());
            content.put("NOME_VENDEDOR", cliente.getNome_vendedor());
            content.put("F_ID_CLIENTE", cliente.getF_id_cliente());
            content.put("ID_ENTIDADE", cliente.getId_entidade());
            content.put("F_ID_FORNECEDOR", cliente.getF_id_fornecedor());
            content.put("F_ID_VENDEDOR", cliente.getF_id_vendedor());
            content.put("F_ID_TRANSPORTADOR", cliente.getF_id_transportador());
            content.put("TELEFONE_PRINCIPAL", cliente.getTelefone_principal());
            content.put("EMAIL_PRINCIPAL", cliente.getEmail_principal());
            content.put("ID_PAIS", cliente.getId_pais());
            content.put("F_ID_FUNCIONARIO", cliente.getF_id_funcionario());
            content.put("AVISAR_COM_DIAS", cliente.getAvisar_com_dias());
            content.put("OBSERVACOES", cliente.getObservacoes());
            content.put("PADRAO_ID_C_CUSTO", cliente.getPadrao_id_c_custo());
            content.put("PADRAO_ID_C_GERENCIADORA", cliente.getPadrao_id_c_gerenciadora());
            content.put("PADRAO_ID_C_ANALITICA", cliente.getPadrao_id_c_analitica());
            content.put("COB_ENDERECO", cliente.getCob_endereco());
            content.put("COB_ENDERECO_BAIRRO", cliente.getCob_endereco_bairro());
            content.put("COB_ENDERECO_NUMERO", cliente.getCob_endereco_numero());
            content.put("COB_ENDERECO_COMPLEMENTO", cliente.getCob_endereco_complemento());
            content.put("COB_ENDERECO_UF", cliente.getCob_endereco_uf());
            content.put("NOME_COB_MUNICIPIO", cliente.getNome_cob_municipio());
            content.put("COB_ENDERECO_CEP", cliente.getCob_endereco_cep());
            content.put("COB_ENDERECO_ID_PAIS", cliente.getCob_endereco_id_pais());
            content.put("LIMITE_CREDITO", cliente.getLimite_credito());
            content.put("LIMITE_DISPONIVEL", cliente.getLimite_disponivel());
            content.put("PESSOA_CONTATO_FINANCEIRO", cliente.getPessoa_contato_financeiro());
            content.put("EMAIL_FINANCEIRO", cliente.getEmail_financeiro());
            content.put("OBSERVACOES_FATURAMENTO", cliente.getObservacoes_faturamento());
            content.put("OBSERVACOES_FINANCEIRO", cliente.getObservacoes_financeiro());
            content.put("TELEFONE_DOIS", cliente.getTelefone_dois());
            content.put("TELEFONE_TRES", cliente.getTelefone_tres());
            content.put("PESSOA_CONTATO_PRINCIPAL", cliente.getPessoa_contato_principal());
            content.put("IND_DA_IE_DESTINATARIO", cliente.getInd_da_ie_destinatario());
            content.put("COMISSAO_PERCENTUAL", cliente.getComissao_percentual());
            content.put("ID_SETOR", cliente.getId_setor());
            content.put("NFE_EMAIL_ENVIAR", cliente.getNfe_email_enviar());
            content.put("NFE_EMAIL_UM", cliente.getNfe_email_um());
            content.put("NFE_EMAIL_DOIS", cliente.getNfe_email_dois());
            content.put("NFE_EMAIL_TRES", cliente.getNfe_email_tres());
            content.put("NFE_EMAIL_QUATRO", cliente.getNfe_email_quatro());
            content.put("NFE_EMAIL_CINCO", cliente.getNfe_email_cinco());
            content.put("ID_GRUPO_VENDEDOR", cliente.getId_grupo_vendedor());
            content.put("VENDEDOR_USA_PORTAL", cliente.getVendedor_usa_portal());
            content.put("VENDEDOR_ID_USER_PORTAL", cliente.getVendedor_id_user_portal());
            content.put("F_TARIFA", cliente.getF_tarifa());
            content.put("F_ID_TARIFA", cliente.getF_id_tarifa());
            content.put("F_PRODUTOR", cliente.getF_produtor());
            content.put("RG_NUMERO", cliente.getRg_numero());
            content.put("RG_SSP", cliente.getRg_ssp());
            content.put("CONTA_CONTABIL", cliente.getConta_contabil());
            content.put("MOTORISTA", cliente.getMotorista());
            content.put("F_ID_MOTORISTA", cliente.getF_id_motorista());
            content.put("HABILITACAO_NUMERO", cliente.getHabilitacao_numero());
            content.put("HABILITACAO_CATEGORIA", cliente.getHabilitacao_categoria());
            content.put("HABILITACAO_VENCIMENTO", cliente.getHabilitacao_vencimento());
            content.put("MOT_ID_TRANSPORTADORA", cliente.getMot_id_transportadora());
            content.put("LOCAL_CADASTRO", cliente.getLocal_cadastro());
            content.put("ID_CATEGORIA", cliente.getIdCategoria());
            content.put("ID_VENDEDOR", cliente.getId_vendedor());
            content.put("ALTERADO", "N");
            content.put("SITUACAO_PREDIO", cliente.getSituacaoPredio());
            content.put("FINALIZADO", cliente.getFinalizado());
            content.put("ID_TABELA_PRECO", cliente.getId_tabela_preco());
            content.put("NOME_TABELA_PRECO", cliente.getNome_tabela_preco());
            content.put("UTILIZA_TABELA_PRECO", cliente.getUtiliza_tabela_preco());
            content.put("TIPO_TABELA_PRECO", cliente.getTipo_tabela_preco());
            content.put("FINANCEIRO_VENCIDO", cliente.getFinanceiro_vencido());
            content.put("FINANCEIRO_VENCENDO", cliente.getFinanceiro_vencendo());
            content.put("PEDIDO_ANALISE", cliente.getPedido_analise());
            content.put("LATITUDE", cliente.getLatitude());
            content.put("LONGITUDE", cliente.getLongitude());
            content.put("ID_REGIAO", cliente.getId_regiao());
            content.put("ID_BAIRRO", cliente.getId_bairro());
            content.put("ROTA_SEQUENCIA", cliente.getRota_sequencia());
            System.gc();
            return db.addDados("TBL_CADASTRO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void addLista(List<Cliente> lista, DBHelper db) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT);

        try {
            database.beginTransaction(); // Trava o disco uma única vez

            for (Cliente c : lista) {
                stmt.clearBindings();

                // Vincular campos na ordem exata do SQL acima
                bindString(stmt, 1, c.getAtivo());
                bindLong(stmt, 2, c.getId_empresa());
                bindLong(stmt, 3, c.getId_cadastro_servidor());
                bindLong(stmt, 4, c.getId_prospect());
                bindString(stmt, 5, c.getPessoa_f_j());
                bindString(stmt, 6, c.getData_aniversario());
                bindString(stmt, 7, c.getNome_cadastro());
                bindString(stmt, 8, c.getNome_fantasia());
                bindString(stmt, 9, c.getCpf_cnpj());
                bindString(stmt, 10, c.getInscri_estadual());
                bindString(stmt, 11, c.getEndereco());
                bindString(stmt, 12, c.getEndereco_bairro());
                bindString(stmt, 13, c.getEndereco_numero());
                bindString(stmt, 14, c.getEndereco_complemento());
                bindString(stmt, 15, c.getEndereco_uf());
                bindString(stmt, 16, c.getNome_municipio());
                bindString(stmt, 17, c.getEndereco_cep());
                bindLong(stmt, 18, c.getUsuario_id());
                bindString(stmt, 19, c.getUsuario_nome());
                bindString(stmt, 20, c.getUsuario_data());
                bindString(stmt, 21, c.getF_cliente());
                bindString(stmt, 22, c.getF_fornecedor());
                bindString(stmt, 23, c.getF_funcionario());
                bindString(stmt, 24, c.getF_vendedor());
                bindString(stmt, 25, c.getF_transportador());
                bindString(stmt, 26, c.getData_ultima_compra());
                bindString(stmt, 27, c.getNome_vendedor());
                bindLong(stmt, 28, c.getF_id_cliente());
                bindLong(stmt, 29, c.getId_entidade());
                bindLong(stmt, 30, c.getF_id_fornecedor());
                bindLong(stmt, 31, c.getF_id_vendedor());
                bindLong(stmt, 32, c.getF_id_transportador());
                bindString(stmt, 33, c.getTelefone_principal());
                bindString(stmt, 34, c.getEmail_principal());
                bindLong(stmt, 35, c.getId_pais());
                bindLong(stmt, 36, c.getF_id_funcionario());
                bindLong(stmt, 37, c.getAvisar_com_dias());
                bindString(stmt, 38, c.getObservacoes());
                bindLong(stmt, 39, c.getPadrao_id_c_custo());
                bindLong(stmt, 40, c.getPadrao_id_c_gerenciadora());
                bindLong(stmt, 41, c.getPadrao_id_c_analitica());
                bindString(stmt, 42, c.getCob_endereco());
                bindString(stmt, 43, c.getCob_endereco_bairro());
                bindString(stmt, 44, c.getCob_endereco_numero());
                bindString(stmt, 45, c.getCob_endereco_complemento());
                bindString(stmt, 46, c.getCob_endereco_uf());
                bindString(stmt, 47, c.getNome_cob_municipio());
                bindString(stmt, 48, c.getCob_endereco_cep());
                bindLong(stmt, 49, c.getCob_endereco_id_pais());
                bindDouble(stmt, 50, c.getLimite_credito());
                bindDouble(stmt, 51, c.getLimite_disponivel());
                bindString(stmt, 52, c.getPessoa_contato_financeiro());
                bindString(stmt, 53, c.getEmail_financeiro());
                bindString(stmt, 54, c.getObservacoes_faturamento());
                bindString(stmt, 55, c.getObservacoes_financeiro());
                bindString(stmt, 56, c.getTelefone_dois());
                bindString(stmt, 57, c.getTelefone_tres());
                bindString(stmt, 58, c.getPessoa_contato_principal());
                bindLong(stmt, 59, c.getInd_da_ie_destinatario());
                bindDouble(stmt, 60, c.getComissao_percentual());
                bindLong(stmt, 61, c.getId_setor());
                bindString(stmt, 62, c.getNfe_email_enviar());
                bindString(stmt, 63, c.getNfe_email_um());
                bindString(stmt, 64, c.getNfe_email_dois());
                bindString(stmt, 65, c.getNfe_email_tres());
                bindString(stmt, 66, c.getNfe_email_quatro());
                bindString(stmt, 67, c.getNfe_email_cinco());
                bindLong(stmt, 68, c.getId_grupo_vendedor());
                bindString(stmt, 69, c.getVendedor_usa_portal());
                bindLong(stmt, 70, c.getVendedor_id_user_portal());
                bindString(stmt, 71, c.getF_tarifa());
                bindLong(stmt, 72, c.getF_id_tarifa());
                bindString(stmt, 73, c.getF_produtor());
                bindString(stmt, 74, c.getRg_numero());
                bindString(stmt, 75, c.getRg_ssp());
                bindString(stmt, 76, c.getConta_contabil());
                bindString(stmt, 77, c.getMotorista());
                bindLong(stmt, 78, c.getF_id_motorista());
                bindString(stmt, 79, c.getHabilitacao_numero());
                bindString(stmt, 80, c.getHabilitacao_categoria());
                bindString(stmt, 81, c.getHabilitacao_vencimento());
                bindLong(stmt, 82, c.getMot_id_transportadora());
                bindString(stmt, 83, c.getLocal_cadastro());
                bindLong(stmt, 84, c.getIdCategoria());
                bindLong(stmt, 85, c.getId_vendedor());
                bindString(stmt, 86, "N"); // ALTERADO
                bindString(stmt, 87, c.getSituacaoPredio());
                bindString(stmt, 88, c.getFinalizado());
                bindLong(stmt, 89, c.getId_tabela_preco());
                bindString(stmt, 90, c.getNome_tabela_preco());
                bindString(stmt, 91, c.getUtiliza_tabela_preco());
                bindString(stmt, 92, c.getTipo_tabela_preco());
                bindDouble(stmt, 93, c.getFinanceiro_vencido());
                bindDouble(stmt, 94, c.getFinanceiro_vencendo());
                bindString(stmt, 95, c.getPedido_analise());
                bindDouble(stmt, 96, c.getLatitude());
                bindDouble(stmt, 97, c.getLongitude());
                bindLong(stmt, 98, c.getId_regiao());
                bindLong(stmt, 99, c.getId_bairro());
                bindLong(stmt, 100, c.getRota_sequencia());

                stmt.executeInsert();
            }

            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    // Auxiliares para tratar nulos
    private void bindString(SQLiteStatement stmt, int i, String val) {
        if (val == null) stmt.bindNull(i); else stmt.bindString(i, val);
    }
    private void bindLong(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindLong(i, Long.parseLong(val.toString()));
    }
    private void bindDouble(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindDouble(i, Double.parseDouble(val.toString()));
    }
    private static final String SQL_INSERT = "INSERT INTO TBL_CADASTRO (" +
            "ATIVO, ID_EMPRESA, ID_CADASTRO_SERVIDOR, ID_PROSPECT, PESSOA_F_J, DATA_ANIVERSARIO, NOME_CADASTRO, NOME_FANTASIA, CPF_CNPJ, INSCRI_ESTADUAL, " +
            "ENDERECO, ENDERECO_BAIRRO, ENDERECO_NUMERO, ENDERECO_COMPLEMENTO, ENDERECO_UF, NOME_MUNICIPIO, ENDERECO_CEP, USUARIO_ID, USUARIO_NOME, USUARIO_DATA, " +
            "F_CLIENTE, F_FORNECEDOR, F_FUNCIONARIO, F_VENDEDOR, F_TRANSPORTADOR, DATA_ULTIMA_COMPRA, NOME_VENDEDOR, F_ID_CLIENTE, ID_ENTIDADE, F_ID_FORNECEDOR, " +
            "F_ID_VENDEDOR, F_ID_TRANSPORTADOR, TELEFONE_PRINCIPAL, EMAIL_PRINCIPAL, ID_PAIS, F_ID_FUNCIONARIO, AVISAR_COM_DIAS, OBSERVACOES, PADRAO_ID_C_CUSTO, PADRAO_ID_C_GERENCIADORA, " +
            "PADRAO_ID_C_ANALITICA, COB_ENDERECO, COB_ENDERECO_BAIRRO, COB_ENDERECO_NUMERO, COB_ENDERECO_COMPLEMENTO, COB_ENDERECO_UF, NOME_COB_MUNICIPIO, COB_ENDERECO_CEP, COB_ENDERECO_ID_PAIS, LIMITE_CREDITO, " +
            "LIMITE_DISPONIVEL, PESSOA_CONTATO_FINANCEIRO, EMAIL_FINANCEIRO, OBSERVACOES_FATURAMENTO, OBSERVACOES_FINANCEIRO, TELEFONE_DOIS, TELEFONE_TRES, PESSOA_CONTATO_PRINCIPAL, IND_DA_IE_DESTINATARIO, COMISSAO_PERCENTUAL, " +
            "ID_SETOR, NFE_EMAIL_ENVIAR, NFE_EMAIL_UM, NFE_EMAIL_DOIS, NFE_EMAIL_TRES, NFE_EMAIL_QUATRO, NFE_EMAIL_CINCO, ID_GRUPO_VENDEDOR, VENDEDOR_USA_PORTAL, VENDEDOR_ID_USER_PORTAL, " +
            "F_TARIFA, F_ID_TARIFA, F_PRODUTOR, RG_NUMERO, RG_SSP, CONTA_CONTABIL, MOTORISTA, F_ID_MOTORISTA, HABILITACAO_NUMERO, HABILITACAO_CATEGORIA, " +
            "HABILITACAO_VENCIMENTO, MOT_ID_TRANSPORTADORA, LOCAL_CADASTRO, ID_CATEGORIA, ID_VENDEDOR, ALTERADO, SITUACAO_PREDIO, FINALIZADO, ID_TABELA_PRECO, NOME_TABELA_PRECO, " +
            "UTILIZA_TABELA_PRECO, TIPO_TABELA_PRECO, FINANCEIRO_VENCIDO, FINANCEIRO_VENCENDO, PEDIDO_ANALISE, LATITUDE, LONGITUDE, ID_REGIAO, ID_BAIRRO, ROTA_SEQUENCIA" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public int updateIdFDB(Cliente cliente, String id_cadastro) {
        ContentValues content = new ContentValues();
        content.put("ID_CLIENTE_APP_FDB", cliente.getId_cliente_app_fdb());
        return db.updateDados("TBL_CADASTRO", content, "ID_CADASTRO = " + id_cadastro);
    }

    public int update(Cliente cliente) {
        ContentValues content = new ContentValues();
        try {
            content.put("ATIVO", cliente.getAtivo());
            content.put("ID_EMPRESA", cliente.getId_empresa());
            if ( cliente.getId_cadastro() > 0) {
                content.put("ID_CADASTRO", cliente.getId_cadastro());
            }
            content.put("ID_CADASTRO_SERVIDOR", cliente.getId_cadastro_servidor());
            content.put("ID_PROSPECT", cliente.getId_prospect());
            content.put("PESSOA_F_J", cliente.getPessoa_f_j());
            content.put("DATA_ANIVERSARIO", cliente.getData_aniversario());
            content.put("NOME_CADASTRO", cliente.getNome_cadastro());
            content.put("NOME_FANTASIA", cliente.getNome_fantasia());
            content.put("CPF_CNPJ", cliente.getCpf_cnpj());
            content.put("INSCRI_ESTADUAL", cliente.getInscri_estadual());
            content.put("ENDERECO", cliente.getEndereco());
            content.put("ENDERECO_BAIRRO", cliente.getEndereco_bairro());
            content.put("ENDERECO_NUMERO", cliente.getEndereco_numero());
            content.put("ENDERECO_COMPLEMENTO", cliente.getEndereco_complemento());
            content.put("ENDERECO_UF", cliente.getEndereco_uf());
            content.put("NOME_MUNICIPIO", cliente.getNome_municipio());
            content.put("ENDERECO_CEP", cliente.getEndereco_cep());
            content.put("USUARIO_ID", cliente.getUsuario_id());
            content.put("USUARIO_NOME", cliente.getUsuario_nome());
            content.put("USUARIO_DATA", cliente.getUsuario_data());
            content.put("F_CLIENTE", cliente.getF_cliente());
            content.put("F_FORNECEDOR", cliente.getF_fornecedor());
            content.put("F_FUNCIONARIO", cliente.getF_funcionario());
            content.put("F_VENDEDOR", cliente.getF_vendedor());
            content.put("F_TRANSPORTADOR", cliente.getF_transportador());
            content.put("DATA_ULTIMA_COMPRA", cliente.getData_ultima_compra());
            content.put("NOME_VENDEDOR", cliente.getNome_vendedor());
            content.put("F_ID_CLIENTE", cliente.getF_id_cliente());
            content.put("ID_ENTIDADE", cliente.getId_entidade());
            content.put("F_ID_FORNECEDOR", cliente.getF_id_fornecedor());
            content.put("F_ID_VENDEDOR", cliente.getF_id_vendedor());
            content.put("F_ID_TRANSPORTADOR", cliente.getF_id_transportador());
            content.put("TELEFONE_PRINCIPAL", cliente.getTelefone_principal());
            content.put("EMAIL_PRINCIPAL", cliente.getEmail_principal());
            content.put("ID_PAIS", cliente.getId_pais());
            content.put("F_ID_FUNCIONARIO", cliente.getF_id_funcionario());
            content.put("AVISAR_COM_DIAS", cliente.getAvisar_com_dias());
            content.put("OBSERVACOES", cliente.getObservacoes());
            content.put("PADRAO_ID_C_CUSTO", cliente.getPadrao_id_c_custo());
            content.put("PADRAO_ID_C_GERENCIADORA", cliente.getPadrao_id_c_gerenciadora());
            content.put("PADRAO_ID_C_ANALITICA", cliente.getPadrao_id_c_analitica());
            content.put("COB_ENDERECO", cliente.getCob_endereco());
            content.put("COB_ENDERECO_BAIRRO", cliente.getCob_endereco_bairro());
            content.put("COB_ENDERECO_NUMERO", cliente.getCob_endereco_numero());
            content.put("COB_ENDERECO_COMPLEMENTO", cliente.getCob_endereco_complemento());
            content.put("COB_ENDERECO_UF", cliente.getCob_endereco_uf());
            content.put("NOME_COB_MUNICIPIO", cliente.getNome_cob_municipio());
            content.put("COB_ENDERECO_CEP", cliente.getCob_endereco_cep());
            content.put("COB_ENDERECO_ID_PAIS", cliente.getCob_endereco_id_pais());
            content.put("LIMITE_CREDITO", cliente.getLimite_credito());
            content.put("LIMITE_DISPONIVEL", cliente.getLimite_disponivel());
            content.put("PESSOA_CONTATO_FINANCEIRO", cliente.getPessoa_contato_financeiro());
            content.put("EMAIL_FINANCEIRO", cliente.getEmail_financeiro());
            content.put("OBSERVACOES_FATURAMENTO", cliente.getObservacoes_faturamento());
            content.put("OBSERVACOES_FINANCEIRO", cliente.getObservacoes_financeiro());
            content.put("TELEFONE_DOIS", cliente.getTelefone_dois());
            content.put("TELEFONE_TRES", cliente.getTelefone_tres());
            content.put("PESSOA_CONTATO_PRINCIPAL", cliente.getPessoa_contato_principal());
            content.put("IND_DA_IE_DESTINATARIO", cliente.getInd_da_ie_destinatario());
            content.put("COMISSAO_PERCENTUAL", cliente.getComissao_percentual());
            content.put("ID_SETOR", cliente.getId_setor());
            content.put("NFE_EMAIL_ENVIAR", cliente.getNfe_email_enviar());
            content.put("NFE_EMAIL_UM", cliente.getNfe_email_um());
            content.put("NFE_EMAIL_DOIS", cliente.getNfe_email_dois());
            content.put("NFE_EMAIL_TRES", cliente.getNfe_email_tres());
            content.put("NFE_EMAIL_QUATRO", cliente.getNfe_email_quatro());
            content.put("NFE_EMAIL_CINCO", cliente.getNfe_email_cinco());
            content.put("ID_GRUPO_VENDEDOR", cliente.getId_grupo_vendedor());
            content.put("VENDEDOR_USA_PORTAL", cliente.getVendedor_usa_portal());
            content.put("VENDEDOR_ID_USER_PORTAL", cliente.getVendedor_id_user_portal());
            content.put("F_TARIFA", cliente.getF_tarifa());
            content.put("F_ID_TARIFA", cliente.getF_id_tarifa());
            content.put("F_PRODUTOR", cliente.getF_produtor());
            content.put("RG_NUMERO", cliente.getRg_numero());
            content.put("RG_SSP", cliente.getRg_ssp());
            content.put("CONTA_CONTABIL", cliente.getConta_contabil());
            content.put("MOTORISTA", cliente.getMotorista());
            content.put("F_ID_MOTORISTA", cliente.getF_id_motorista());
            content.put("HABILITACAO_NUMERO", cliente.getHabilitacao_numero());
            content.put("HABILITACAO_CATEGORIA", cliente.getHabilitacao_categoria());
            content.put("HABILITACAO_VENCIMENTO", cliente.getHabilitacao_vencimento());
            content.put("MOT_ID_TRANSPORTADORA", cliente.getMot_id_transportadora());
            content.put("LOCAL_CADASTRO", cliente.getLocal_cadastro());
            content.put("ID_CATEGORIA", cliente.getIdCategoria());
            content.put("ID_VENDEDOR", cliente.getId_vendedor());
            content.put("ALTERADO", cliente.getAlterado());
            content.put("SITUACAO_PREDIO", cliente.getSituacaoPredio());
            content.put("FINALIZADO", cliente.getFinalizado());
            content.put("ID_TABELA_PRECO", cliente.getId_tabela_preco());
            content.put("NOME_TABELA_PRECO", cliente.getNome_tabela_preco());
            content.put("UTILIZA_TABELA_PRECO", cliente.getUtiliza_tabela_preco());
            content.put("TIPO_TABELA_PRECO", cliente.getTipo_tabela_preco());
            content.put("FINANCEIRO_VENCIDO", cliente.getFinanceiro_vencido());
            content.put("FINANCEIRO_VENCENDO", cliente.getFinanceiro_vencendo());
            content.put("PEDIDO_ANALISE", cliente.getPedido_analise());
            content.put("LATITUDE", cliente.getLatitude());
            content.put("LONGITUDE", cliente.getLongitude());
            content.put("ID_REGIAO", cliente.getId_regiao());
            content.put("ID_BAIRRO", cliente.getId_bairro());
            content.put("ROTA_SEQUENCIA", cliente.getRota_sequencia());
            System.gc();
            if ( cliente.getId_cadastro() == 0) {
                if ( cliente.getCpf_cnpj() == null || cliente.getCpf_cnpj().isEmpty()) {
                    return db.updateDados("TBL_CADASTRO", content, "ID_CADASTRO_SERVIDOR = '" + cliente.getId_cadastro_servidor() + "'");
                } else {
                    return db.updateDados("TBL_CADASTRO", content, "CPF_CNPJ = '" + cliente.getCpf_cnpj() + "'");
                }
            } else {
                return db.updateDados("TBL_CADASTRO", content, "ID_CADASTRO = " + cliente.getId_cadastro());
            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Cliente cliente, String tipo) {
        //Alteração no codigo tratando excessao
        //Roberto caceres
        //SQLiteDatabase db = getWritableDatabase();
        try {
            if (tipo.equalsIgnoreCase("0"))
                db.deleteDados("TBL_CADASTRO_CONTATO", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro()), "1"});
            else
                db.deleteDados("TBL_CADASTRO_CONTATO", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro_servidor()), "1"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (tipo.equalsIgnoreCase("0"))
                db.deleteDados("TBL_REFERENCIA_BANCARIA", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro()), "1"});
            else
                db.deleteDados("TBL_REFERENCIA_BANCARIA", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro_servidor()), "1"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (tipo.equalsIgnoreCase("0"))
                db.deleteDados("TBL_REFERENCIA_COMERCIAL", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro()), "1"});

            else
                db.deleteDados("TBL_REFERENCIA_COMERCIAL", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro_servidor()), "1"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (tipo.equalsIgnoreCase("0"))
                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro()), "1"});
            else
                db.deleteDados("TBL_CADASTRO_ANEXOS", "ID_CADASTRO_SERVIDOR=? AND ID_ENTIDADE=?", new String[]{String.valueOf(cliente.getId_cadastro_servidor()), "1"});
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (tipo.equalsIgnoreCase("0"))
                return db.deleteDados("TBL_CADASTRO", "ID_CADASTRO=?", new String[]{String.valueOf(cliente.getId_cadastro())});
            else
                return db.deleteDados("TBL_CADASTRO", "ID_CADASTRO_SERVIDOR=?", new String[]{String.valueOf(cliente.getId_cadastro_servidor())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressLint("Range")
    public List<Cliente> getLista(String SQL) {
        List<Cliente> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        Cursor cursorPro = null;
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            Cliente cliente = new Cliente();
            try {
                cliente.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                cliente.setId_empresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
                cliente.setId_cadastro(cursor.getInt(cursor.getColumnIndex("ID_CADASTRO")));
                cliente.setId_prospect(cursor.getInt(cursor.getColumnIndex("ID_PROSPECT")));
                cliente.setId_cadastro_servidor(cursor.getInt(cursor.getColumnIndex("ID_CADASTRO_SERVIDOR")));
                cliente.setPessoa_f_j(cursor.getString(cursor.getColumnIndex("PESSOA_F_J")));
                cliente.setData_aniversario(cursor.getString(cursor.getColumnIndex("DATA_ANIVERSARIO")));
                cliente.setNome_cadastro(cursor.getString(cursor.getColumnIndex("NOME_CADASTRO")));
                cliente.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
                cliente.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
                cliente.setInscri_estadual(cursor.getString(cursor.getColumnIndex("INSCRI_ESTADUAL")));
                cliente.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
                cliente.setEndereco_bairro(cursor.getString(cursor.getColumnIndex("ENDERECO_BAIRRO")));
                cliente.setEndereco_numero(cursor.getString(cursor.getColumnIndex("ENDERECO_NUMERO")));
                cliente.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
                cliente.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
                cliente.setNome_municipio(cursor.getString(cursor.getColumnIndex("NOME_MUNICIPIO")));
                cliente.setEndereco_cep(cursor.getString(cursor.getColumnIndex("ENDERECO_CEP")));
                cliente.setUsuario_id(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
                cliente.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
                cliente.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                cliente.setF_cliente(cursor.getString(cursor.getColumnIndex("F_CLIENTE")));
                cliente.setF_fornecedor(cursor.getString(cursor.getColumnIndex("F_FORNECEDOR")));
                cliente.setF_funcionario(cursor.getString(cursor.getColumnIndex("F_FUNCIONARIO")));
                cliente.setF_vendedor(cursor.getString(cursor.getColumnIndex("F_VENDEDOR")));
                cliente.setF_transportador(cursor.getString(cursor.getColumnIndex("F_TRANSPORTADOR")));
                cliente.setData_ultima_compra(cursor.getString(cursor.getColumnIndex("DATA_ULTIMA_COMPRA")));
                cliente.setNome_vendedor(cursor.getString(cursor.getColumnIndex("NOME_VENDEDOR")));
                cliente.setF_id_cliente(cursor.getString(cursor.getColumnIndex("F_ID_CLIENTE")));
                cliente.setId_entidade(cursor.getString(cursor.getColumnIndex("ID_ENTIDADE")));
                cliente.setF_id_fornecedor(cursor.getString(cursor.getColumnIndex("F_ID_FORNECEDOR")));
                cliente.setF_id_vendedor(cursor.getString(cursor.getColumnIndex("F_ID_VENDEDOR")));
                cliente.setF_id_transportador(cursor.getString(cursor.getColumnIndex("F_ID_TRANSPORTADOR")));
                cliente.setTelefone_principal(cursor.getString(cursor.getColumnIndex("TELEFONE_PRINCIPAL")));
                cliente.setEmail_principal(cursor.getString(cursor.getColumnIndex("EMAIL_PRINCIPAL")));
                cliente.setId_pais(cursor.getInt(cursor.getColumnIndex("ID_PAIS")));
                cliente.setF_id_funcionario(cursor.getString(cursor.getColumnIndex("F_ID_FUNCIONARIO")));
                cliente.setAvisar_com_dias(cursor.getString(cursor.getColumnIndex("AVISAR_COM_DIAS")));
                cliente.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
                cliente.setPadrao_id_c_custo(cursor.getString(cursor.getColumnIndex("PADRAO_ID_C_CUSTO")));
                cliente.setPadrao_id_c_gerenciadora(cursor.getString(cursor.getColumnIndex("PADRAO_ID_C_GERENCIADORA")));
                cliente.setPadrao_id_c_analitica(cursor.getString(cursor.getColumnIndex("PADRAO_ID_C_ANALITICA")));
                cliente.setCob_endereco(cursor.getString(cursor.getColumnIndex("COB_ENDERECO")));
                cliente.setCob_endereco_bairro(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_BAIRRO")));
                cliente.setCob_endereco_numero(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_NUMERO")));
                cliente.setCob_endereco_complemento(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_COMPLEMENTO")));
                cliente.setCob_endereco_uf(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_UF")));
                cliente.setNome_cob_municipio(cursor.getString(cursor.getColumnIndex("NOME_COB_MUNICIPIO")));
                cliente.setCob_endereco_cep(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_CEP")));
                cliente.setCob_endereco_id_pais(cursor.getInt(cursor.getColumnIndex("COB_ENDERECO_ID_PAIS")));
                cliente.setLimite_credito(cursor.getString(cursor.getColumnIndex("LIMITE_CREDITO")));
                cliente.setLimite_disponivel(cursor.getString(cursor.getColumnIndex("LIMITE_DISPONIVEL")));
                cliente.setPessoa_contato_financeiro(cursor.getString(cursor.getColumnIndex("PESSOA_CONTATO_FINANCEIRO")));
                cliente.setEmail_financeiro(cursor.getString(cursor.getColumnIndex("EMAIL_FINANCEIRO")));
                cliente.setObservacoes_faturamento(cursor.getString(cursor.getColumnIndex("OBSERVACOES_FATURAMENTO")));
                cliente.setObservacoes_financeiro(cursor.getString(cursor.getColumnIndex("OBSERVACOES_FINANCEIRO")));
                cliente.setTelefone_dois(cursor.getString(cursor.getColumnIndex("TELEFONE_DOIS")));
                cliente.setTelefone_tres(cursor.getString(cursor.getColumnIndex("TELEFONE_TRES")));
                cliente.setPessoa_contato_principal(cursor.getString(cursor.getColumnIndex("PESSOA_CONTATO_PRINCIPAL")));
                cliente.setInd_da_ie_destinatario(cursor.getString(cursor.getColumnIndex("IND_DA_IE_DESTINATARIO")));
                cliente.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
                cliente.setId_setor(cursor.getString(cursor.getColumnIndex("ID_SETOR")));
                cliente.setNfe_email_enviar(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_ENVIAR")));
                cliente.setNfe_email_um(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_UM")));
                cliente.setNfe_email_dois(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_DOIS")));
                cliente.setNfe_email_tres(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_TRES")));
                cliente.setNfe_email_quatro(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_QUATRO")));
                cliente.setNfe_email_cinco(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_CINCO")));
                cliente.setId_grupo_vendedor(cursor.getString(cursor.getColumnIndex("ID_GRUPO_VENDEDOR")));
                cliente.setVendedor_usa_portal(cursor.getString(cursor.getColumnIndex("VENDEDOR_USA_PORTAL")));
                cliente.setVendedor_id_user_portal(cursor.getString(cursor.getColumnIndex("VENDEDOR_ID_USER_PORTAL")));
                cliente.setF_tarifa(cursor.getString(cursor.getColumnIndex("F_TARIFA")));
                cliente.setF_id_tarifa(cursor.getString(cursor.getColumnIndex("F_ID_TARIFA")));
                cliente.setF_produtor(cursor.getString(cursor.getColumnIndex("F_PRODUTOR")));
                cliente.setRg_numero(cursor.getString(cursor.getColumnIndex("RG_NUMERO")));
                cliente.setRg_ssp(cursor.getString(cursor.getColumnIndex("RG_SSP")));
                cliente.setConta_contabil(cursor.getString(cursor.getColumnIndex("CONTA_CONTABIL")));
                cliente.setMotorista(cursor.getString(cursor.getColumnIndex("MOTORISTA")));
                cliente.setF_id_motorista(cursor.getString(cursor.getColumnIndex("F_ID_MOTORISTA")));
                cliente.setHabilitacao_numero(cursor.getString(cursor.getColumnIndex("HABILITACAO_NUMERO")));
                cliente.setHabilitacao_categoria(cursor.getString(cursor.getColumnIndex("HABILITACAO_CATEGORIA")));
                cliente.setHabilitacao_vencimento(cursor.getString(cursor.getColumnIndex("HABILITACAO_VENCIMENTO")));
                cliente.setMot_id_transportadora(cursor.getString(cursor.getColumnIndex("MOT_ID_TRANSPORTADORA")));
                cliente.setLocal_cadastro(cursor.getString(cursor.getColumnIndex("LOCAL_CADASTRO")));
                cliente.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
                cliente.setId_vendedor(cursor.getInt(cursor.getColumnIndex("ID_VENDEDOR")));
                cliente.setAlterado(cursor.getString(cursor.getColumnIndex("ALTERADO")));
                cliente.setSituacaoPredio(cursor.getString(cursor.getColumnIndex("SITUACAO_PREDIO")));
                if ( cursor.getString(cursor.getColumnIndex("FINALIZADO")) == null) {
                    cliente.setFinalizado("N");
                } else {
                    cliente.setFinalizado(cursor.getString(cursor.getColumnIndex("FINALIZADO")));
                }
                cliente.setId_tabela_preco(cursor.getInt(cursor.getColumnIndex("ID_TABELA_PRECO")));
                cliente.setNome_tabela_preco(cursor.getString(cursor.getColumnIndex("NOME_TABELA_PRECO")));
                cliente.setUtiliza_tabela_preco(cursor.getString(cursor.getColumnIndex("UTILIZA_TABELA_PRECO")));
                cliente.setTipo_tabela_preco(cursor.getString(cursor.getColumnIndex("TIPO_TABELA_PRECO")));
                cliente.setFinanceiro_vencido(cursor.getString(cursor.getColumnIndex("FINANCEIRO_VENCIDO")));
                cliente.setFinanceiro_vencendo(cursor.getString(cursor.getColumnIndex("FINANCEIRO_VENCENDO")));
                cliente.setPedido_analise(cursor.getString(cursor.getColumnIndex("PEDIDO_ANALISE")));
                cliente.setLatitude(cursor.getString(cursor.getColumnIndex("LATITUDE")));
                cliente.setLongitude(cursor.getString(cursor.getColumnIndex("LONGITUDE")));
                cliente.setId_regiao(cursor.getInt(cursor.getColumnIndex("ID_REGIAO")));
                cliente.setId_bairro(cursor.getInt(cursor.getColumnIndex("ID_BAIRRO")));
                cliente.setRota_sequencia(cursor.getInt(cursor.getColumnIndex("ROTA_SEQUENCIA")));
                cliente.setId_cliente_app_fdb(cursor.getString(cursor.getColumnIndex("ID_CLIENTE_APP_FDB")));
                try {
                    SQL = "SELECT P.ID_PROMOCAO AS ID_PROMOCAO FROM TBL_PROMOCAO_CLIENTE AS P" +
                            " INNER JOIN TBL_PROMOCAO_CAB AS PC ON PC.ID_PROMOCAO = P.ID_PROMOCAO" +
                            " WHERE P.ID_CADASTRO = '" + cursor.getInt(cursor.getColumnIndex("ID_CADASTRO_SERVIDOR")) + "'" +
                            " AND PC.ATIVO = 'S' AND PC.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice();
                    cursorPro = db.listaDados2(SQL);
                    if (cursorPro.getCount() > 0) {
                        if (cursorPro.moveToNext()) {
                            cliente.setId_promocao(cursorPro.getString(cursorPro.getColumnIndex("ID_PROMOCAO")));
                        } else
                            cliente.setId_promocao("0");
                    } else
                        cliente.setId_promocao("0");
                } catch (Exception e) {
                    cliente.setId_promocao("0");
                }
                if (cursorPro != null)
                    cursorPro.close();
                lista.add(cliente);
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        //System.gc();
        return lista;
    }

    @SuppressLint("Range")
    public int getTotalReg(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return 0;
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("TOTALREG"));
    }


    public long add(ClienteResumo cliente) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        ContentValues content = new ContentValues();
        try {
            content.put("CPF_CNPJ", cliente.getCpf_cnpj());
            content.put("NOME", cliente.getNome());
            System.gc();
            return db.addDados("TBL_CADASTRO_RESUMO", content);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @SuppressLint("Range")
    public ClienteResumo getCliente(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        ClienteResumo cliente = null;
        if (cursor.getCount() <= 0)
            return cliente;
        cursor.moveToFirst();
        do {
            cliente = new ClienteResumo();
            try {
                cliente.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
                cliente.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return cliente;

    }

    public void addListaClienteResumo(List<ClienteResumo> lista, DBHelper db) {
        // Obtém a instância do banco de dados real
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT_RESUMO);
        try {
            database.beginTransaction(); // Inicia a transação para máxima velocidade
            for (ClienteResumo cliente : lista) {
                stmt.clearBindings();
                bindString(stmt, 1, cliente.getCpf_cnpj());
                bindString(stmt, 2, cliente.getNome());
                stmt.executeInsert();
            }
            database.setTransactionSuccessful(); // Confirma as alterações
        } catch (Exception e) {
        } finally {
            database.endTransaction(); // Finaliza e grava no disco
        }
    }
    private static final String SQL_INSERT_RESUMO = "INSERT INTO TBL_CADASTRO_RESUMO (" +
            "CPF_CNPJ, NOME" +
            ") VALUES (?, ?)";
}