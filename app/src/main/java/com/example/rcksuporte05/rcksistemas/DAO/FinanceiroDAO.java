package com.example.rcksuporte05.rcksistemas.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Moeda;
import com.example.rcksuporte05.rcksistemas.model.ReceberCab;

import java.util.ArrayList;
import java.util.List;

public class FinanceiroDAO {

    private DBHelper db;

    public FinanceiroDAO(DBHelper db) {
        this.db = db;
    }

    public long add(FinanceiroResumo financeiro) {
        ContentValues content = new ContentValues();
        try {
            content.put("DATA_EMISSAO", financeiro.getData_emissao());
            content.put("DATA_BAIXA",  financeiro.getData_baixa());
            content.put("DATA_VENCIMENTO", financeiro.getData_vencimento());
            content.put("DATA_COMPETENCIA", financeiro.getData_competencia());
            content.put("VALOR_BRUTO", financeiro.getValor_bruto());
            content.put("VALOR_TOTAL", financeiro.getValor_total());
            content.put("VALOR_CREDITO", financeiro.getValor_credito());
            content.put("VALOR_DEBITO", financeiro.getValor_debito());
            content.put("VALOR_JUROS", financeiro.getValor_juros());
            content.put("VALOR_DESCONTOS", financeiro.getValor_descontos());
            content.put("ID_CONTA", financeiro.getId_conta());
            content.put("NOME_CONTA", financeiro.getNome_conta());
            content.put("DEBITO_CREDITO", financeiro.getDebito_credito());
            content.put("DOCUMENTO", financeiro.getDocumento());
            content.put("PARCELA", financeiro.getParcela());
            content.put("NOME_FAVORECIDO", financeiro.getNome_favorecido());
            content.put("ID_BAIRRO", financeiro.getId_bairro());
            content.put("ID_CIDADE", financeiro.getId_cidade());
            content.put("ID_REGIAO", financeiro.getId_regiao());
            content.put("ID_FAVORECIDO", financeiro.getId_favorecido());
            content.put("CPF_CNPJ", financeiro.getCpf_cnpj());

            content.put("NOME_FANTASIA", financeiro.getNome_fantasia());
            content.put("ENDERECO", financeiro.getEndereco());
            content.put("ENDERECO_BAIRRO", financeiro.getEndereco_bairro());
            content.put("ENDERECO_NUMERO", financeiro.getEndereco_numero());
            content.put("ENDERECO_COMPLEMENTO", financeiro.getEndereco_complemento());
            content.put("ENDERECO_UF", financeiro.getEndereco_uf());
            content.put("NOME_MUNICIPIO", financeiro.getNome_municipio());
            content.put("TELEFONE_PRINCIPAL", financeiro.getTelefone_principal());
            content.put("TELEFONE_DOIS", financeiro.getTelefone_dois());
            content.put("ENDERECO_CEP", financeiro.getEndereco_cep());
            content.put("ATIVO", financeiro.getAtivo());
            content.put("ORDEM", financeiro.getOrdem());
            content.put("DEVEDOR", financeiro.getDevedor());

            content.put("COBRANCA_ID_STATUS", financeiro.getCobranca_id_status());
            content.put("COBRANCA_DATA_STATUS", financeiro.getCobranca_data_status());
            content.put("COBRANCA_DESCRICAO_STATUS", financeiro.getCobranca_descricao_status());

            content.put("TOTAL_COMPRAS", financeiro.getTotal_compras());
            content.put("DATA_ULT_COMPRA", financeiro.getData_ult_compra());

            //TOTAL_COMPRAS INTEGER, DATA_ULT_COMPRA DATE

            System.gc();
            return db.addDados("TBL_FINANCEIRO_RESUMO", content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<FinanceiroResumo> getLista(String SQL, boolean recebido, boolean reagendado) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
        do {
            FinanceiroResumo financeiro = new FinanceiroResumo();
            try {
                financeiro.setDevedor(cursor.getString(cursor.getColumnIndex("DEVEDOR")));
                financeiro.setData_emissao(cursor.getString(cursor.getColumnIndex("DATA_EMISSAO")));
                financeiro.setData_baixa(cursor.getString(cursor.getColumnIndex("DATA_BAIXA")));
                financeiro.setData_baixa(cursor.getString(cursor.getColumnIndex("DATA_PGTO")));
                financeiro.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                financeiro.setData_vencimento(cursor.getString(cursor.getColumnIndex("DATA_VENCIMENTO")));
                financeiro.setData_vencimento(cursor.getString(cursor.getColumnIndex("ULT_VCTO")));
                financeiro.setData_competencia(cursor.getString(cursor.getColumnIndex("DATA_COMPETENCIA")));
                financeiro.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
                financeiro.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                financeiro.setValor_credito(cursor.getString(cursor.getColumnIndex("VALOR_CREDITO")));
                financeiro.setValor_debito(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                financeiro.setValor_juros(cursor.getString(cursor.getColumnIndex("VALOR_JUROS")));
                financeiro.setValor_descontos(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTOS")));
                financeiro.setId_conta(cursor.getInt(cursor.getColumnIndex("ID_CONTA")));
                financeiro.setNome_conta(cursor.getString(cursor.getColumnIndex("NOME_CONTA")));
                financeiro.setDebito_credito(cursor.getString(cursor.getColumnIndex("DEBITO_CREDITO")));
                financeiro.setDocumento(cursor.getString(cursor.getColumnIndex("DOCUMENTO")));
                financeiro.setParcela(cursor.getString(cursor.getColumnIndex("PARCELA")));
                financeiro.setNome_favorecido(cursor.getString(cursor.getColumnIndex("NOME_FAVORECIDO")));
                financeiro.setId_bairro(cursor.getString(cursor.getColumnIndex("ID_BAIRRO")));
                financeiro.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                financeiro.setId_regiao(cursor.getString(cursor.getColumnIndex("ID_REGIAO")));
                financeiro.setId_favorecido(cursor.getString(cursor.getColumnIndex("ID_FAVORECIDO")));
                financeiro.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
                financeiro.setDocs( cursor.getString( cursor.getColumnIndex("DOCS")));
                financeiro.setOrdem( cursor.getString(cursor.getColumnIndex("ORDEM")));

                financeiro.setNome_fantasia(cursor.getString( cursor.getColumnIndex("NOME_FANTASIA")));
                financeiro.setEndereco(cursor.getString( cursor.getColumnIndex("ENDERECO")));
                financeiro.setEndereco_bairro(cursor.getString( cursor.getColumnIndex("ENDERECO_BAIRRO")));
                financeiro.setEndereco_numero(cursor.getString( cursor.getColumnIndex("ENDERECO_NUMERO")));
                try {
                    if ( !cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")).isEmpty())
                        financeiro.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
                    else {
                        financeiro.setEndereco_complemento("");
                    }
                } catch ( NullPointerException e) {
                    financeiro.setEndereco_complemento("");
                }

                financeiro.setEndereco_uf(cursor.getString( cursor.getColumnIndex("ENDERECO_UF")));
                financeiro.setNome_municipio(cursor.getString( cursor.getColumnIndex("NOME_MUNICIPIO")));
                financeiro.setTelefone_principal(cursor.getString( cursor.getColumnIndex("TELEFONE_PRINCIPAL")));
                financeiro.setTelefone_dois(cursor.getString( cursor.getColumnIndex("TELEFONE_DOIS")));
                financeiro.setEndereco_cep(cursor.getString( cursor.getColumnIndex("ENDERECO_CEP")));
                try {
                    financeiro.setValor_recebimento(cursor.getString(cursor.getColumnIndex("VALOR_RECEBIMENTO")));
                    financeiro.setValor_debito( String.valueOf(Float.parseFloat(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL"))) - Float.parseFloat(cursor.getString(cursor.getColumnIndex("VALOR_RECEBIMENTO")))));
                } catch ( NullPointerException|NumberFormatException e) {
                    financeiro.setValor_recebimento("0.00");
                }
                financeiro.setTotal_docs(cursor.getInt(cursor.getColumnIndex("TDOCS")));
                financeiro.setStatus(cursor.getString(cursor.getColumnIndex("STATUS")));  //STATUS DA BAIXA
                financeiro.setData_reagendamento(cursor.getString(cursor.getColumnIndex("DATA_REAGENDAMENTO")));

                financeiro.setTotal_compras(cursor.getString(cursor.getColumnIndex("TOTAL_COMPRAS")));
                financeiro.setData_ult_compra(cursor.getString(cursor.getColumnIndex("DATA_ULT_COMPRA")));

                if ( recebido && Float.parseFloat(financeiro.getValor_recebimento()) > 0.0f || reagendado &&  financeiro.getData_reagendamento() != null ) {
                    lista.add(financeiro);
                }else if ( !recebido && !reagendado) {
                    lista.add(financeiro);
                }
            } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    @SuppressLint("Range")
    public List<FinanceiroResumo> getLista(String SQL) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
        do {
            FinanceiroResumo financeiro = new FinanceiroResumo();
            try {
                financeiro.setDevedor(cursor.getString(cursor.getColumnIndex("DEVEDOR")));
                financeiro.setData_emissao(cursor.getString(cursor.getColumnIndex("DATA_EMISSAO")));
                financeiro.setData_baixa(cursor.getString(cursor.getColumnIndex("DATA_BAIXA")));
                //financeiro.setData_baixa(cursor.getString(cursor.getColumnIndex("DATA_PGTO")));
                financeiro.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                financeiro.setData_vencimento(cursor.getString(cursor.getColumnIndex("DATA_VENCIMENTO")));
                //financeiro.setData_vencimento(cursor.getString(cursor.getColumnIndex("ULT_VCTO")));
                financeiro.setData_competencia(cursor.getString(cursor.getColumnIndex("DATA_COMPETENCIA")));
                financeiro.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
                financeiro.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                financeiro.setValor_credito(cursor.getString(cursor.getColumnIndex("VALOR_CREDITO")));
                financeiro.setValor_debito(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                financeiro.setValor_juros(cursor.getString(cursor.getColumnIndex("VALOR_JUROS")));
                financeiro.setValor_descontos(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTOS")));
                financeiro.setId_conta(cursor.getInt(cursor.getColumnIndex("ID_CONTA")));
                financeiro.setNome_conta(cursor.getString(cursor.getColumnIndex("NOME_CONTA")));
                financeiro.setDebito_credito(cursor.getString(cursor.getColumnIndex("DEBITO_CREDITO")));
                financeiro.setDocumento(cursor.getString(cursor.getColumnIndex("DOCUMENTO")));
                financeiro.setParcela(cursor.getString(cursor.getColumnIndex("PARCELA")));
                financeiro.setNome_favorecido(cursor.getString(cursor.getColumnIndex("NOME_FAVORECIDO")));
                financeiro.setId_bairro(cursor.getString(cursor.getColumnIndex("ID_BAIRRO")));
                financeiro.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                financeiro.setId_regiao(cursor.getString(cursor.getColumnIndex("ID_REGIAO")));
                financeiro.setId_favorecido(cursor.getString(cursor.getColumnIndex("ID_FAVORECIDO")));
                financeiro.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
                //financeiro.setDocs( cursor.getString( cursor.getColumnIndex("DOCS")));
                financeiro.setOrdem( cursor.getString(cursor.getColumnIndex("ORDEM")));

                financeiro.setNome_fantasia(cursor.getString( cursor.getColumnIndex("NOME_FANTASIA")));
                financeiro.setEndereco(cursor.getString( cursor.getColumnIndex("ENDERECO")));
                financeiro.setEndereco_bairro(cursor.getString( cursor.getColumnIndex("ENDERECO_BAIRRO")));
                financeiro.setEndereco_numero(cursor.getString( cursor.getColumnIndex("ENDERECO_NUMERO")));
                try {
                    if ( !cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")).isEmpty())
                        financeiro.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
                    else {
                        financeiro.setEndereco_complemento("");
                    }
                } catch ( NullPointerException e) {
                    financeiro.setEndereco_complemento("");
                }

                financeiro.setEndereco_uf(cursor.getString( cursor.getColumnIndex("ENDERECO_UF")));
                financeiro.setNome_municipio(cursor.getString( cursor.getColumnIndex("NOME_MUNICIPIO")));
                financeiro.setTelefone_principal(cursor.getString( cursor.getColumnIndex("TELEFONE_PRINCIPAL")));
                financeiro.setTelefone_dois(cursor.getString( cursor.getColumnIndex("TELEFONE_DOIS")));
                financeiro.setEndereco_cep(cursor.getString( cursor.getColumnIndex("ENDERECO_CEP")));

                financeiro.setCobranca_id_status(cursor.getString( cursor.getColumnIndex("COBRANCA_ID_STATUS")));
                financeiro.setCobranca_data_status(cursor.getString( cursor.getColumnIndex("COBRANCA_DATA_STATUS")));
                financeiro.setCobranca_descricao_status(cursor.getString( cursor.getColumnIndex("COBRANCA_DESCRICAO_STATUS")));

                try {
                    //financeiro.setValor_recebimento(cursor.getString(cursor.getColumnIndex("VALOR_RECEBIMENTO")));
                    //financeiro.setValor_debito( String.valueOf(Float.parseFloat(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL"))) - Float.parseFloat(cursor.getString(cursor.getColumnIndex("VALOR_RECEBIMENTO")))));
                } catch ( NullPointerException|NumberFormatException e) {
                    financeiro.setValor_recebimento("0.00");
                }
                //financeiro.setTotal_docs(cursor.getInt(cursor.getColumnIndex("TDOCS")));
                //financeiro.setStatus(cursor.getString(cursor.getColumnIndex("STATUS")));  //STATUS DA BAIXA
                //financeiro.setData_reagendamento(cursor.getString(cursor.getColumnIndex("DATA_REAGENDAMENTO")));
               lista.add(financeiro);
            } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    @SuppressLint("Range")
    public List<FinanceiroResumo> getLista2(String SQL, String status) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
        do {
            FinanceiroResumo financeiro = new FinanceiroResumo();
            try {
                financeiro.setDevedor(cursor.getString(cursor.getColumnIndex("DEVEDOR")));
                financeiro.setData_emissao(cursor.getString(cursor.getColumnIndex("DATA_EMISSAO")));
                financeiro.setData_baixa(cursor.getString(cursor.getColumnIndex("DATA_BAIXA")));
                //financeiro.setData_baixa(cursor.getString(cursor.getColumnIndex("DATA_PGTO")));
                financeiro.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                financeiro.setData_vencimento(cursor.getString(cursor.getColumnIndex("DATA_VENCIMENTO")));
                //financeiro.setData_vencimento(cursor.getString(cursor.getColumnIndex("ULT_VCTO")));
                financeiro.setData_competencia(cursor.getString(cursor.getColumnIndex("DATA_COMPETENCIA")));
                financeiro.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
                financeiro.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                financeiro.setValor_credito(cursor.getString(cursor.getColumnIndex("VALOR_CREDITO")));
                financeiro.setValor_debito(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                financeiro.setValor_juros(cursor.getString(cursor.getColumnIndex("VALOR_JUROS")));
                financeiro.setValor_descontos(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTOS")));
                financeiro.setId_conta(cursor.getInt(cursor.getColumnIndex("ID_CONTA")));
                financeiro.setNome_conta(cursor.getString(cursor.getColumnIndex("NOME_CONTA")));
                financeiro.setDebito_credito(cursor.getString(cursor.getColumnIndex("DEBITO_CREDITO")));
                financeiro.setDocumento(cursor.getString(cursor.getColumnIndex("DOCUMENTO")));
                financeiro.setParcela(cursor.getString(cursor.getColumnIndex("PARCELA")));
                financeiro.setNome_favorecido(cursor.getString(cursor.getColumnIndex("NOME_FAVORECIDO")));
                financeiro.setId_bairro(cursor.getString(cursor.getColumnIndex("ID_BAIRRO")));
                financeiro.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                financeiro.setId_regiao(cursor.getString(cursor.getColumnIndex("ID_REGIAO")));
                financeiro.setId_favorecido(cursor.getString(cursor.getColumnIndex("ID_FAVORECIDO")));
                financeiro.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
                //financeiro.setDocs( cursor.getString( cursor.getColumnIndex("DOCS")));
                financeiro.setOrdem( cursor.getString(cursor.getColumnIndex("ORDEM")));

                financeiro.setNome_fantasia(cursor.getString( cursor.getColumnIndex("NOME_FANTASIA")));
                financeiro.setEndereco(cursor.getString( cursor.getColumnIndex("ENDERECO")));
                financeiro.setEndereco_bairro(cursor.getString( cursor.getColumnIndex("ENDERECO_BAIRRO")));
                financeiro.setEndereco_numero(cursor.getString( cursor.getColumnIndex("ENDERECO_NUMERO")));
                try {
                    if ( !cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")).isEmpty())
                        financeiro.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
                    else {
                        financeiro.setEndereco_complemento("");
                    }
                } catch ( NullPointerException e) {
                    financeiro.setEndereco_complemento("");
                }

                financeiro.setEndereco_uf(cursor.getString( cursor.getColumnIndex("ENDERECO_UF")));
                financeiro.setNome_municipio(cursor.getString( cursor.getColumnIndex("NOME_MUNICIPIO")));
                financeiro.setTelefone_principal(cursor.getString( cursor.getColumnIndex("TELEFONE_PRINCIPAL")));
                financeiro.setTelefone_dois(cursor.getString( cursor.getColumnIndex("TELEFONE_DOIS")));
                financeiro.setEndereco_cep(cursor.getString( cursor.getColumnIndex("ENDERECO_CEP")));
                financeiro.setCobranca_id_status(cursor.getString( cursor.getColumnIndex("COBRANCA_ID_STATUS")));
                financeiro.setCobranca_data_status(cursor.getString( cursor.getColumnIndex("COBRANCA_DATA_STATUS")));
                financeiro.setCobranca_descricao_status(cursor.getString( cursor.getColumnIndex("COBRANCA_DESCRICAO_STATUS")));

                try {
                    //financeiro.setValor_recebimento(cursor.getString(cursor.getColumnIndex("VALOR_RECEBIMENTO")));
                    //financeiro.setValor_debito( String.valueOf(Float.parseFloat(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL"))) - Float.parseFloat(cursor.getString(cursor.getColumnIndex("VALOR_RECEBIMENTO")))));
                } catch ( NullPointerException|NumberFormatException e) {
                    financeiro.setValor_recebimento("0.00");
                }
                //financeiro.setTotal_docs(cursor.getInt(cursor.getColumnIndex("TDOCS")));
                financeiro.setStatus(status);  //STATUS DA BAIXA
                //financeiro.setData_reagendamento(cursor.getString(cursor.getColumnIndex("DATA_REAGENDAMENTO")));
                lista.add(financeiro);
            } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    @SuppressLint("Range")
    public List<FinanceiroResumo> getLista3(String SQL, String status) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
        do {
            FinanceiroResumo financeiro = new FinanceiroResumo();
            try {
                financeiro.setValor_total(String.valueOf(cursor.getFloat(cursor.getColumnIndex("VALOR_TOTAL"))));
                financeiro.setTotal_titulos(cursor.getString(cursor.getColumnIndex("TOTAL_TITULOS")));
                lista.add(financeiro);
            } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }


    @SuppressLint("Range")
    public FinanceiroResumo getFinanceiro(String SQL) {
        FinanceiroResumo financeiro = new FinanceiroResumo();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return financeiro;
        cursor.moveToFirst();
            try {
                financeiro.setDevedor(cursor.getString(cursor.getColumnIndex("DEVEDOR")));
                financeiro.setData_emissao(cursor.getString(cursor.getColumnIndex("DATA_EMISSAO")));
                financeiro.setData_baixa(cursor.getString(cursor.getColumnIndex("DATA_BAIXA")));
                financeiro.setData_vencimento(cursor.getString(cursor.getColumnIndex("DATA_VENCIMENTO")));
                financeiro.setData_competencia(cursor.getString(cursor.getColumnIndex("DATA_COMPETENCIA")));
                financeiro.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
                financeiro.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                financeiro.setValor_credito(cursor.getString(cursor.getColumnIndex("VALOR_CREDITO")));
                financeiro.setValor_debito(cursor.getString(cursor.getColumnIndex("VALOR_DEBITO")));
                financeiro.setValor_juros(cursor.getString(cursor.getColumnIndex("VALOR_JUROS")));
                financeiro.setValor_descontos(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTOS")));
                financeiro.setId_conta(cursor.getInt(cursor.getColumnIndex("ID_CONTA")));
                financeiro.setNome_conta(cursor.getString(cursor.getColumnIndex("NOME_CONTA")));
                financeiro.setDebito_credito(cursor.getString(cursor.getColumnIndex("DEBITO_CREDITO")));
                financeiro.setDocumento(cursor.getString(cursor.getColumnIndex("DOCUMENTO")));
                financeiro.setParcela(cursor.getString(cursor.getColumnIndex("PARCELA")));
                financeiro.setNome_favorecido(cursor.getString(cursor.getColumnIndex("NOME_FAVORECIDO")));
                financeiro.setId_bairro(cursor.getString(cursor.getColumnIndex("ID_BAIRRO")));
                financeiro.setId_cidade(cursor.getString(cursor.getColumnIndex("ID_CIDADE")));
                financeiro.setId_regiao(cursor.getString(cursor.getColumnIndex("ID_REGIAO")));
                financeiro.setId_favorecido(cursor.getString(cursor.getColumnIndex("ID_FAVORECIDO")));
                financeiro.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
                financeiro.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                financeiro.setNome_fantasia(cursor.getString( cursor.getColumnIndex("NOME_FANTASIA")));
                financeiro.setEndereco(cursor.getString( cursor.getColumnIndex("ENDERECO")));
                financeiro.setEndereco_bairro(cursor.getString( cursor.getColumnIndex("ENDERECO_BAIRRO")));
                financeiro.setEndereco_numero(cursor.getString( cursor.getColumnIndex("ENDERECO_NUMERO")));
                financeiro.setOrdem( cursor.getString(cursor.getColumnIndex("ORDEM")));

                try {
                    if ( !cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")).isEmpty())
                        financeiro.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
                    else {
                        financeiro.setEndereco_complemento("");
                    }
                } catch ( NullPointerException e) {
                    financeiro.setEndereco_complemento("");
                }
                financeiro.setEndereco_uf(cursor.getString( cursor.getColumnIndex("ENDERECO_UF")));
                financeiro.setNome_municipio(cursor.getString( cursor.getColumnIndex("NOME_MUNICIPIO")));
                financeiro.setTelefone_principal(cursor.getString( cursor.getColumnIndex("TELEFONE_PRINCIPAL")));
                financeiro.setTelefone_dois(cursor.getString( cursor.getColumnIndex("TELEFONE_DOIS")));
                financeiro.setEndereco_cep(cursor.getString( cursor.getColumnIndex("ENDERECO_CEP")));
                financeiro.setData_reagendamento(cursor.getString(cursor.getColumnIndex("DATA_REAGENDAMENTO")));
                financeiro.setCobranca_id_status(cursor.getString( cursor.getColumnIndex("COBRANCA_ID_STATUS")));
                financeiro.setCobranca_data_status(cursor.getString( cursor.getColumnIndex("COBRANCA_DATA_STATUS")));
                financeiro.setCobranca_descricao_status(cursor.getString( cursor.getColumnIndex("COBRANCA_DESCRICAO_STATUS")));

                return  financeiro;
            } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }

        cursor.close();
        System.gc();
        return financeiro;
    }

    public FinanceiroResumo getTotalAberto(String SQL) {
        FinanceiroResumo financeiro = new FinanceiroResumo();
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return null;
        cursor.moveToFirst();
        if ( cursor.getInt(cursor.getColumnIndex("TOTAL_DOCS")) <= 0 )
            return null;
        try {
            financeiro.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
            financeiro.setTotal_docs(cursor.getInt(cursor.getColumnIndex("TOTAL_DOCS")));
            financeiro.setValor_recebimento(cursor.getString(cursor.getColumnIndex("VALOR_RECEBIMENTO")));
            return  financeiro;
        } catch (CursorIndexOutOfBoundsException|NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        cursor.close();
        System.gc();
        return null;
    }

    public boolean delete( FinanceiroResumo financeiro) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        try {
            if( db.deleteDados( "TBL_FINANCEIRO_RESUMO", "ID_FAVORECIDO = ?", new String[]{ financeiro.getId_favorecido() } ) >= 1)
                return true;
            else
                return false;
        } catch ( Exception e) {
            return false;
        }

    }

    public boolean update( FinanceiroResumo financeiro) {
        //Alteração no codigo tratando excessao e retorno
        //Roberto caceres
        //Atualiza o cliente como nao devedor
        try {
            if( db.deleteDados( "TBL_FINANCEIRO_RESUMO", "ID_FAVORECIDO = ?", new String[]{ financeiro.getId_favorecido() } ) >= 1)
                return true;
            else
                return false;
        } catch ( Exception e) {
            return false;
        }

    }



    /*
    public FinanceiroResumo getFinanceiro(String SQL) {
        Cursor cursor = db.listaDados(SQL);
        if (cursor.getCount() <= 0)
            return new String{"0","00"};
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            return Float.parseFloat(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
        } catch ( NullPointerException|NumberFormatException|CursorIndexOutOfBoundsException e) {

        } catch ( Exception e) {

        }
        return  0.00f;
    }

     */
}
