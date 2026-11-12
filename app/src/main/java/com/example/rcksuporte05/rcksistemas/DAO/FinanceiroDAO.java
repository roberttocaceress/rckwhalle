package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;

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
            content.put("DATA_BAIXA", financeiro.getData_baixa());
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

            System.gc();
            return db.addDados("TBL_FINANCEIRO_RESUMO", content);
        } catch (SQLException ignored) {
        }
        return 0;
    }

    public List<FinanceiroResumo> getLista(String SQL, boolean recebido, boolean reagendado) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    FinanceiroResumo financeiro = setFinanceiroResumo(cursor);

                    financeiro.setDocs(getString(cursor, "DOCS"));

                    String dataPgto = getString(cursor, "DATA_PGTO");
                    if (dataPgto != null)
                        financeiro.setData_baixa(dataPgto);

                    String ultVcto = getString(cursor, "ULT_VCTO");
                    if (ultVcto != null)
                        financeiro.setData_vencimento(ultVcto);

                    String valRec = getString(cursor, "VALOR_RECEBIMENTO");
                    if (valRec != null) {
                        financeiro.setValor_recebimento(valRec);
                        try {
                            float valTot = Float.parseFloat(financeiro.getValor_total() != null ? financeiro.getValor_total() : "0");
                            float valReceb = Float.parseFloat(valRec);
                            financeiro.setValor_debito(String.valueOf(valTot - valReceb));
                        } catch (Exception ignored) {
                        }
                    } else {
                        financeiro.setValor_recebimento("0.00");
                    }

                    financeiro.setTotal_docs(getInt(cursor, "TDOCS"));
                    financeiro.setStatus(getString(cursor, "STATUS"));

                    if ((recebido && Float.parseFloat(financeiro.getValor_recebimento()) > 0.0f) || (reagendado && financeiro.getData_reagendamento() != null)) {
                        lista.add(financeiro);
                    } else if (!recebido && !reagendado) {
                        lista.add(financeiro);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        //System.gc();
        return lista;
    }

    public List<FinanceiroResumo> getLista(String SQL) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    FinanceiroResumo financeiro = setFinanceiroResumo(cursor);
                    lista.add(financeiro);
                } while (cursor.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        System.gc();
        return lista;
    }

    public List<FinanceiroResumo> getLista2(String SQL, String status) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                do {
                    FinanceiroResumo financeiro = setFinanceiroResumo(cursor);
                    financeiro.setStatus(status);
                    lista.add(financeiro);
                } while (cursor.moveToNext());
        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        //System.gc();
        return lista;
    }

    public List<FinanceiroResumo> getLista3(String SQL, String status) {
        List<FinanceiroResumo> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                do {
                    FinanceiroResumo financeiro = new FinanceiroResumo();
                    financeiro.setValor_total(String.valueOf(getFloat(cursor, "VALOR_TOTAL")));
                    financeiro.setTotal_titulos(getString(cursor, "TOTAL_TITULOS"));
                    lista.add(financeiro);
                } while (cursor.moveToNext());
        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        //System.gc();
        return lista;
    }

    public FinanceiroResumo getFinanceiro(String SQL) {
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                return setFinanceiroResumo(cursor);
        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        //System.gc();
        return new FinanceiroResumo();
    }

    public FinanceiroResumo getTotalAberto(String SQL) {
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst()) {
                int totalDocs = getInt(cursor, "TOTAL_DOCS");
                if (totalDocs <= 0)
                    return null;
                FinanceiroResumo financeiro = new FinanceiroResumo();
                financeiro.setValor_total(getString(cursor, "VALOR_TOTAL"));
                financeiro.setTotal_docs(totalDocs);
                financeiro.setValor_recebimento(getString(cursor, "VALOR_RECEBIMENTO"));
                return financeiro;
            }
        } catch (Exception ignored) {
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        System.gc();
        return null;
    }

    public boolean delete(FinanceiroResumo financeiro) {
        try {
            return db.deleteDados("TBL_FINANCEIRO_RESUMO", "ID_FAVORECIDO = ?", new String[]{financeiro.getId_favorecido()}) >= 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(FinanceiroResumo financeiro) {
        try {
            return db.deleteDados("TBL_FINANCEIRO_RESUMO", "ID_FAVORECIDO = ?", new String[]{financeiro.getId_favorecido()}) >= 1;
        } catch (Exception e) {
            return false;
        }
    }

    private FinanceiroResumo setFinanceiroResumo(Cursor cursor) {
        FinanceiroResumo financeiro = new FinanceiroResumo();
        try {
            financeiro.setDevedor(getString(cursor, "DEVEDOR"));
            financeiro.setData_emissao(getString(cursor, "DATA_EMISSAO"));
            financeiro.setData_baixa(getString(cursor, "DATA_BAIXA"));
            financeiro.setAtivo(getString(cursor, "ATIVO"));
            financeiro.setData_vencimento(getString(cursor, "DATA_VENCIMENTO"));
            financeiro.setData_competencia(getString(cursor, "DATA_COMPETENCIA"));
            financeiro.setValor_bruto(getString(cursor, "VALOR_BRUTO"));
            financeiro.setValor_total(getString(cursor, "VALOR_TOTAL"));
            financeiro.setValor_credito(getString(cursor, "VALOR_CREDITO"));
            financeiro.setValor_debito(getString(cursor, "VALOR_DEBITO"));
            financeiro.setValor_juros(getString(cursor, "VALOR_JUROS"));
            financeiro.setValor_descontos(getString(cursor, "VALOR_DESCONTOS"));
            financeiro.setId_conta(getInt(cursor, "ID_CONTA"));
            financeiro.setNome_conta(getString(cursor, "NOME_CONTA"));
            financeiro.setDebito_credito(getString(cursor, "DEBITO_CREDITO"));
            financeiro.setDocumento(getString(cursor, "DOCUMENTO"));
            financeiro.setParcela(getString(cursor, "PARCELA"));
            financeiro.setNome_favorecido(getString(cursor, "NOME_FAVORECIDO"));
            financeiro.setId_bairro(getString(cursor, "ID_BAIRRO"));
            financeiro.setId_cidade(getString(cursor, "ID_CIDADE"));
            financeiro.setId_regiao(getString(cursor, "ID_REGIAO"));
            financeiro.setId_favorecido(getString(cursor, "ID_FAVORECIDO"));
            financeiro.setCpf_cnpj(getString(cursor, "CPF_CNPJ"));
            financeiro.setOrdem(getString(cursor, "ORDEM"));

            financeiro.setNome_fantasia(getString(cursor, "NOME_FANTASIA"));
            financeiro.setEndereco(getString(cursor, "ENDERECO"));
            financeiro.setEndereco_bairro(getString(cursor, "ENDERECO_BAIRRO"));
            financeiro.setEndereco_numero(getString(cursor, "ENDERECO_NUMERO"));
            String comp = getString(cursor, "ENDERECO_COMPLEMENTO");
            financeiro.setEndereco_complemento(comp != null ? comp : "");

            financeiro.setEndereco_uf(getString(cursor, "ENDERECO_UF"));
            financeiro.setNome_municipio(getString(cursor, "NOME_MUNICIPIO"));
            financeiro.setTelefone_principal(getString(cursor, "TELEFONE_PRINCIPAL"));
            financeiro.setTelefone_dois(getString(cursor, "TELEFONE_DOIS"));
            financeiro.setEndereco_cep(getString(cursor, "ENDERECO_CEP"));

            financeiro.setCobranca_id_status(getString(cursor, "COBRANCA_ID_STATUS"));
            financeiro.setCobranca_data_status(getString(cursor, "COBRANCA_DATA_STATUS"));
            financeiro.setCobranca_descricao_status(getString(cursor, "COBRANCA_DESCRICAO_STATUS"));

            financeiro.setTotal_compras(getString(cursor, "TOTAL_COMPRAS"));
            financeiro.setData_ult_compra(getString(cursor, "DATA_ULT_COMPRA"));
            financeiro.setData_reagendamento(getString(cursor, "DATA_REAGENDAMENTO"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return financeiro;
    }

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

    private float getFloat(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0.0f;
        try {
            return c.getFloat(idx);
        } catch (Exception e) {
            return 0.0f;
        }
    }
}
