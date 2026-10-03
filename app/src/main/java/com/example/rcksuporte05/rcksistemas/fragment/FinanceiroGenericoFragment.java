package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaFinanceiroCobrancaAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinanceiroGenericoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinanceiroGenericoFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String dataIni;
    private String dataFin;
    private String periodoStatus;
    private String debitoCredito;
    private String idCliente;
    private String situacao;
    private String tipoRelatorio;
    private Usuario usuario;
    private Configuracao configuracao;
    private Utilitaria util = new Utilitaria();
    private DBHelper db;
    private ListaFinanceiroCobrancaAdapter adapter;
    private ListaFinanceiroCobrancaAdapter.financaListener listener;
    private View view;

    public FinanceiroGenericoFragment(String dataInicial, String dataFinal, String periodoStatus, String debitoCredito, String idCliente, String situacao, String tipoRelatorio) {
        this.dataIni = dataInicial;
        this.dataFin = dataFinal;
        this.periodoStatus = periodoStatus;
        this.debitoCredito = debitoCredito;
        this.idCliente     = idCliente;
        this.situacao      = situacao;
        this.tipoRelatorio = tipoRelatorio;
    }

    public static FinanceiroGenericoFragment newInstance(String dataInicial, String dataFinal, String periodo, String debitoCredito, String idCliente, String situacao, String tipoRelatorio) {
        FinanceiroGenericoFragment fragment = new FinanceiroGenericoFragment(dataInicial, dataFinal, periodo, debitoCredito, idCliente, situacao, tipoRelatorio );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_financeiro_generico, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        db = new DBHelper(getContext());
        if ( tipoRelatorio.equalsIgnoreCase("OFF"))
            getListaTitulos(dataIni, dataFin, periodoStatus, situacao, idCliente);
        if ( tipoRelatorio.equalsIgnoreCase("ON"))
            getListaTitulos(Integer.parseInt(idCliente), situacao);
        return view;
    }

    private void getListaTitulos(int idCliente, String situacao) {

        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");


        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<FinanceiroResumo>> call = apiRotas.gethistoricoFinanceiroCliente(String.valueOf(UsuarioHelper.getUsuario().getId_usuario()), idCliente, situacao, cabecalho);
            call.enqueue(new Callback<List<FinanceiroResumo>>() {
                @Override
                public void onResponse(Call<List<FinanceiroResumo>> call, Response<List<FinanceiroResumo>> response) {
                    if (response.code() == 200) {
                        List<FinanceiroResumo> lista = response.body();
                        if ( situacao.equalsIgnoreCase("T"))
                             setRecyclerView2(lista);
                        if ( situacao.equalsIgnoreCase("R"))
                            setRecyclerView4(lista);;
                    }
                }

                @Override
                public void onFailure(Call<List<FinanceiroResumo>> call, Throwable t) {
                    Toast.makeText(getContext(), "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }


    }


    private void getListaTitulos(String dataInicial, String dataFinal, String periodoStatus, String situacao, String idCliente) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
        String SQL = "";
        String dataAtual = db.pegaDataAtual();
        SQL = "SELECT * FROM TBL_FINANCEIRO_RESUMO WHERE ";
        if ( situacao.equalsIgnoreCase("V") || situacao.equalsIgnoreCase("AV") || situacao.equalsIgnoreCase("Q")) {
            if (periodoStatus.equalsIgnoreCase("E"))
                SQL += "(DATA_EMISSAO >= '" + dataInicial + "' AND DATA_EMISSAO <= '" + dataFinal + "') ";
            else if (periodoStatus.equalsIgnoreCase("C"))
                SQL += "(DATA_COMPETENCIA >= '" + dataInicial + "' AND DATA_COMPETENCIA <= '" + dataFinal + "') ";
            else if (periodoStatus.equalsIgnoreCase("V"))
                SQL += "(DATA_VENCIMENTO >= '" + dataInicial + "' AND DATA_VENCIMENTO <= '" + dataFinal + "') ";
            else if (periodoStatus.equalsIgnoreCase("Q"))
                SQL += "(F.DATA_BAIXA >= '" + dataInicial + "' AND DATA_BAIXA <= '" + dataFinal + "') ";

            try {
                if (Integer.parseInt(periodoStatus) > 0) {
                    SQL += "(DATA_VENCIMENTO >= '" + dataInicial + "' AND DATA_VENCIMENTO <= '" + dataFinal + "') AND COBRANCA_ID_STATUS = " +periodoStatus;
                    setRecyclerView(financeiroDAO.getLista(SQL));
                    return;
                }
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (situacao == "V")
                SQL += "AND DATA_VENCIMENTO < '" + dataAtual + "' AND DATA_BAIXA IS NULL";
            if (situacao == "AV")
                SQL += "AND DATA_VENCIMENTO >= '" + dataAtual + "' AND DATA_BAIXA IS NULL";
            if (situacao == "Q")
                SQL += "AND ( DATA_BAIXA IS NOT NULL OR DATA_BAIXA != '')";

           setRecyclerView(financeiroDAO.getLista(SQL));
           return;

        } else if ( situacao.equalsIgnoreCase("T")) {
            List<FinanceiroResumo> lista = new ArrayList<>();
            if (idCliente.equalsIgnoreCase("-")) {
                if (periodoStatus.equalsIgnoreCase("E"))
                    SQL += "(DATA_EMISSAO >= '" + dataInicial + "' AND DATA_EMISSAO <= '" + dataFinal + "') ";
                else if (periodoStatus.equalsIgnoreCase("C"))
                    SQL += "(DATA_COMPETENCIA >= '" + dataInicial + "' AND DATA_COMPETENCIA <= '" + dataFinal + "') ";
                else if (periodoStatus.equalsIgnoreCase("V"))
                    SQL += "(DATA_VENCIMENTO >= '" + dataInicial + "' AND DATA_VENCIMENTO <= '" + dataFinal + "') ";

                try {
                    if (Integer.parseInt(periodoStatus) > 0) {
                        lista.addAll( financeiroDAO.getLista2(SQL + "DATA_BAIXA IS NULL AND COBRANCA_ID_STATUS = " + periodoStatus, "V"));
                        setRecyclerView2(lista);
                        return;
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!idCliente.equalsIgnoreCase("-")) {
                SQL += " ID_FAVORECIDO = '" + idCliente + "' ";
            } else if (idCliente.equalsIgnoreCase("0")) {
                return;
            }
            lista.addAll( financeiroDAO.getLista2(SQL + "AND DATA_VENCIMENTO < '" +  dataAtual + "' AND DATA_BAIXA IS NULL ORDER BY DATA_VENCIMENTO ASC, NOME_FAVORECIDO", "V"));
            lista.addAll( financeiroDAO.getLista2(SQL + "AND DATA_VENCIMENTO >= '" + dataAtual + "' AND DATA_BAIXA IS NULL ORDER BY DATA_VENCIMENTO ASC, NOME_FAVORECIDO", "AV"));
            setRecyclerView2(lista);
            return;
        } else if (situacao.equalsIgnoreCase("R")) {
            SQL = "SELECT SUM(VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TOTAL_TITULOS FROM TBL_FINANCEIRO_RESUMO WHERE ";
            String total_titulos_vencidos = "0";
            String total_titulos_avencer  = "0";
            String total_titulos_quitados = "0";
            if (idCliente.equalsIgnoreCase("-")) {
                if (periodoStatus.equalsIgnoreCase("E"))
                    SQL += "(DATA_EMISSAO >= '" + dataInicial + "' AND DATA_EMISSAO <= '" + dataFinal + "') ";
                else if (periodoStatus.equalsIgnoreCase("C"))
                    SQL += "(DATA_COMPETENCIA >= '" + dataInicial + "' AND DATA_COMPETENCIA <= '" + dataFinal + "') ";
                else if (periodoStatus.equalsIgnoreCase("V"))
                    SQL += "(DATA_VENCIMENTO >= '" + dataInicial + "' AND DATA_VENCIMENTO <= '" + dataFinal + "') ";

                try {
                    if (Integer.parseInt(periodoStatus) > 0) {
                        SQL += "DATA_BAIXA IS NULL AND COBRANCA_ID_STATUS = " + periodoStatus;
                        float total_vencido  = 0.0f;
                        total_vencido = Float.parseFloat(financeiroDAO.getLista3(SQL,"V").get(0).getValor_total());
                        total_titulos_vencidos = financeiroDAO.getLista3(SQL,"V").get(0).getTotal_titulos();
                        List<FinanceiroResumo> lista = new ArrayList<>();
                        FinanceiroResumo financeiroResumo = new FinanceiroResumo();
                        financeiroResumo.setTotal_vencido(MascaraUtil.duasCasaDecimal(total_vencido));
                        financeiroResumo.setTotal_vencer("0.00");
                        financeiroResumo.setTotal_quitado("0.00");
                        financeiroResumo.setTotal_titulos_vencidos(total_titulos_vencidos);
                        lista.add(financeiroResumo);
                        setRecyclerView3(lista);
                        return;
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!idCliente.equalsIgnoreCase("-")) {
                SQL += " ID_FAVORECIDO = '" + idCliente + "' ";
            } else if (idCliente.equalsIgnoreCase("0")) {
                return;
            }
            float total_vencido = 0.0f;
            try {
                total_vencido = Float.parseFloat(financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO < '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "V").get(0).getValor_total());
                total_titulos_vencidos = financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO < '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "V").get(0).getTotal_titulos();
            } catch (NumberFormatException | NullPointerException e) {
            } catch (Exception e) {
            }
            float total_vencer = 0.0f;
            try {
                total_vencer = Float.parseFloat(financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO >= '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "AV").get(0).getValor_total());
                total_titulos_avencer = financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO >= '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "AV").get(0).getTotal_titulos();
            } catch (NumberFormatException | NullPointerException e) {
            } catch (Exception e) {
            }
            float total_quitado = 0.0f;
            List<FinanceiroResumo> lista = new ArrayList<>();
            FinanceiroResumo financeiroResumo = new FinanceiroResumo();
            financeiroResumo.setTotal_vencido(MascaraUtil.duasCasaDecimal(total_vencido));
            financeiroResumo.setTotal_vencer(MascaraUtil.duasCasaDecimal(total_vencer));
            financeiroResumo.setTotal_quitado(MascaraUtil.duasCasaDecimal(total_quitado));
            financeiroResumo.setTotal_titulos_vencidos(total_titulos_vencidos);
            financeiroResumo.setTotal_titulos_avencer(total_titulos_avencer);
            financeiroResumo.setTotal_titulos_quitados(total_titulos_quitados);
            lista.add(financeiroResumo);
            setRecyclerView3(lista);
            return;
        } else if (situacao.equalsIgnoreCase("S")) {  //Soma pelo status selecionado
            SQL = "SELECT SUM(VALOR_TOTAL) AS VALOR_TOTAL, COUNT(*) AS TOTAL_TITULOS FROM TBL_FINANCEIRO_RESUMO WHERE ";
            String total_titulos_vencidos = "0";
            String total_titulos_avencer  = "0";
            String total_titulos_quitados = "0";
            if (idCliente.equalsIgnoreCase("-")) {
                if (periodoStatus.equalsIgnoreCase("E"))
                    SQL += "(DATA_EMISSAO >= '" + dataInicial + "' AND DATA_EMISSAO <= '" + dataFinal + "') ";
                else if (periodoStatus.equalsIgnoreCase("C"))
                    SQL += "(DATA_COMPETENCIA >= '" + dataInicial + "' AND DATA_COMPETENCIA <= '" + dataFinal + "') ";
                else if (periodoStatus.equalsIgnoreCase("V"))
                    SQL += "(DATA_VENCIMENTO >= '" + dataInicial + "' AND DATA_VENCIMENTO <= '" + dataFinal + "') ";
            } else if (!idCliente.equalsIgnoreCase("-")) {
                SQL += " ID_FAVORECIDO = '" + idCliente + "' ";
            } else if (idCliente.equalsIgnoreCase("0")) {
                return;
            }
            float total_vencido = 0.0f;
            try {
                total_vencido = Float.parseFloat(financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO < '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "V").get(0).getValor_total());
                total_titulos_vencidos = financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO < '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "V").get(0).getTotal_titulos();
            } catch (NumberFormatException | NullPointerException e) {
            } catch (Exception e) {
            }
            float total_vencer = 0.0f;
            try {
                total_vencer = Float.parseFloat(financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO >= '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "AV").get(0).getValor_total());
                total_titulos_avencer = financeiroDAO.getLista3(SQL + "AND DATA_VENCIMENTO >= '" + dataAtual + "' AND DATA_BAIXA IS NULL ", "AV").get(0).getTotal_titulos();
            } catch (NumberFormatException | NullPointerException e) {
            } catch (Exception e) {
            }
            float total_quitado = 0.0f;
            List<FinanceiroResumo> lista = new ArrayList<>();
            FinanceiroResumo financeiroResumo = new FinanceiroResumo();
            financeiroResumo.setTotal_vencido(MascaraUtil.duasCasaDecimal(total_vencido));
            financeiroResumo.setTotal_vencer(MascaraUtil.duasCasaDecimal(total_vencer));
            financeiroResumo.setTotal_quitado(MascaraUtil.duasCasaDecimal(total_quitado));
            financeiroResumo.setTotal_titulos_vencidos(total_titulos_vencidos);
            financeiroResumo.setTotal_titulos_avencer(total_titulos_avencer);
            financeiroResumo.setTotal_titulos_quitados("0");
            lista.add(financeiroResumo);
            setRecyclerView3(lista);
            return;

        }
    }

    private void setRecyclerView(List<FinanceiroResumo> lista) {
        adapter = new ListaFinanceiroCobrancaAdapter(lista, listener, getContext(), situacao);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setRecyclerView2(List<FinanceiroResumo> lista) {
        adapter = new ListaFinanceiroCobrancaAdapter(lista, listener, getContext(), "T");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setRecyclerView3(List<FinanceiroResumo> lista) {
        adapter = new ListaFinanceiroCobrancaAdapter(lista, listener, getContext(), "R");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setRecyclerView4(List<FinanceiroResumo> lista) {
        adapter = new ListaFinanceiroCobrancaAdapter(lista, listener, getContext(), "RONN");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}