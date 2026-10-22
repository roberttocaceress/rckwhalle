package com.example.rcksuporte05.rcksistemas.BO;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.DashboardData;
import com.example.rcksuporte05.rcksistemas.model.MesAno;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.MetaSemanal;
import com.example.rcksuporte05.rcksistemas.util.FirestoreConexao;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardBO {

    public interface OnDashboardListener {
        void onDashboardLoaded(DashboardData data);
        void onDashboardError(String mensagem);
    }

    private Context context;
    private DBHelper db;
    private OnDashboardListener listener;
    private DashboardData data;
    private int idUsuario;
    private int anoAtual, mesAtual;
    private String idVendedorFirestore = "";
    private int activeRequests = 0;
    private boolean requestFailed = false;

    public DashboardBO(Context context, OnDashboardListener listener) {
        this.context = context;
        this.db = new DBHelper(context);
        this.listener = listener;
        this.data = new DashboardData();

        Calendar cal = Calendar.getInstance();
        this.anoAtual = cal.get(Calendar.YEAR);
        this.mesAtual = cal.get(Calendar.MONTH) + 1;
        
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        this.data.setNomeMesExtenso(meses[mesAtual - 1].toUpperCase());

        // Carrega o cache imediatamente para evitar o "piscar" de dados vazios/asteriscos
        DashboardData cachedData = loadCache();
        if (cachedData != null) {
            this.data = cachedData;
            // Notifica a Activity logo de início com os dados antigos enquanto busca os novos
            new Handler(Looper.getMainLooper()).post(() -> listener.onDashboardLoaded(this.data));
        }
    }

    public void carregarDados(int idUsuario, String idVendedorFirestore, Configuracao configuracao) {
        this.idUsuario = idUsuario;
        this.idVendedorFirestore = idVendedorFirestore;
        this.activeRequests = 4; // Semana, Mês, Operacional (F), Operacional (C)
        this.requestFailed = false;

        // 1. Dados Locais (Síncronos)
        carregarDadosLocais();

        // 2. Dados Online (Cadeia de chamadas)
        buscarDadosSemanais(configuracao);
        buscarDadosMensais(configuracao);
        buscarDadosOperacionais(configuracao, "F"); // Faturados
        buscarDadosOperacionais(configuracao, "C"); // Cancelados
    }

    private void carregarDadosLocais() {
        try {
            String where = " WHERE PEDIDO_ENVIADO = 'N' AND EXCLUIDO <> 'S' AND FINALIZADO = 'S' AND USUARIO_LANCAMENTO_ID = " + idUsuario;
            data.setQtdPendentes(db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO" + where));
            data.setValorPendentes(db.soma("SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO" + where));
            data.setQtdVisitas(db.contagem("SELECT COUNT(*) FROM TBL_WEB_VISITA WHERE STRFTIME('%Y-%m', DATA_HORA_CHECKIN) = STRFTIME('%Y-%m', 'now');"));
            // Qtd Clientes Ativos
            data.setQtdClientesAtivos(db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE ATIVO = 'S' AND F_CLIENTE = 'S'"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buscarDadosSemanais(Configuracao configuracao) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        String dataInicio = String.format("%04d-%02d-01", anoAtual, mesAtual);

        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            checkFinished();
            return;
        }

        api.getSomaTotalRealizadoSemanaPeriodo(idUsuario, dataInicio, cabecalho).enqueue(new Callback<List<MesSemana>>() {
            @Override
            public void onResponse(Call<List<MesSemana>> call, Response<List<MesSemana>> response) {
                if (response.code() == 200 && response.body() != null && !response.body().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String hoje = sdf.format(new Date());

                    double totalSemanaLiquida = 0;
                    String iniSemanaAtual = "";
                    int numeroSemana = 0;
                    int contadorSemanasUnicas = 0;
                    String ultimaDataInicioProcessada = "";

                    // 1. Identifica o intervalo da semana de hoje
                    for (MesSemana ms : response.body()) {
                        if (isDataNoPeriodo(hoje, ms.getIni_semama(), ms.getFin_semana())) {
                            iniSemanaAtual = ms.getIni_semama();
                            break;
                        }
                    }

                    // 2. Fallback: Se não encontrou hoje (ex: virada de dia), pega a última da lista
                    if (iniSemanaAtual.isEmpty()) {
                        iniSemanaAtual = response.body().get(response.body().size() - 1).getIni_semama();
                    }

                    // 3. Calcula o total líquido e descobre o número da semana (posição do grupo)
                    double totalDevolucaoSemana = 0;
                    for (MesSemana ms : response.body()) {
                        // Incrementa contador de semanas apenas quando muda o intervalo
                        if (!ms.getIni_semama().equals(ultimaDataInicioProcessada)) {
                            contadorSemanasUnicas++;
                            ultimaDataInicioProcessada = ms.getIni_semama();
                        }

                        if (ms.getIni_semama().equals(iniSemanaAtual)) {
                            if (numeroSemana == 0) {
                                numeroSemana = contadorSemanasUnicas;
                            }
                            double valor = cleanMoney(ms.getVenda_liquida());
                            if (ms.getE_entrada_s_saida() != null && ms.getE_entrada_s_saida().equalsIgnoreCase("E")) {
                                totalDevolucaoSemana += valor;
                                if (ms.getFigura_rel_comercial() != null && ms.getFigura_rel_comercial().equalsIgnoreCase("S")) {
                                    totalSemanaLiquida -= valor;
                                }
                            } else {
                                totalSemanaLiquida += valor;
                            }
                        }
                    }

                    data.setRealizadoSemana(totalSemanaLiquida);
                    data.setValorDevolucaoSemana(totalDevolucaoSemana);
                    data.setSemanaAtual(numeroSemana);
                    final int finalNumSemana = numeroSemana;

                    // Busca Meta no Firestore (Verifica se CNPJ existe antes)
                    if (configuracao != null && configuracao.getCnpj_empresa() != null) {
                        new FirestoreConexao().getMetaSemanal(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), idVendedorFirestore, String.valueOf(mesAtual), new FirestoreConexao.OnMetaSemanalRetorno() {
                            @Override
                            public void onSucesso(MetaSemanal m) {
                                if (m != null) {
                                    double meta = 0;
                                    switch(finalNumSemana) {
                                        case 1: meta = cleanMoney(m.getSemana1()); break;
                                        case 2: meta = cleanMoney(m.getSemana2()); break;
                                        case 3: meta = cleanMoney(m.getSemana3()); break;
                                        case 4: meta = cleanMoney(m.getSemana4()); break;
                                        case 5: meta = cleanMoney(m.getSemana5()); break;
                                        case 6: meta = cleanMoney(m.getSemana6()); break;
                                        default: meta = cleanMoney(m.getSemana1());
                                    }
                                    data.setMetaSemana(meta);
                                    if (meta > 0) data.setPorcentagemSemana((int)((data.getRealizadoSemana() / meta) * 100));
                                }
                                checkFinished();
                            }
                            @Override public void onFalha(Exception e) { 
                                requestFailed = true;
                                checkFinished(); 
                            }
                        });
                    } else {
                        checkFinished();
                    }
                } else {
                    requestFailed = true;
                    checkFinished();
                }
            }
            @Override public void onFailure(Call<List<MesSemana>> call, Throwable t) { 
                requestFailed = true;
                checkFinished(); 
            }
        });
    }

    private void buscarDadosMensais(Configuracao configuracao) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        String dataInicio = String.format("%04d-%02d-01", anoAtual, mesAtual);

        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            checkFinished();
            return;
        }

        api.getSomaTotalRealizadoMesPeriodo(idUsuario, dataInicio,"0", cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (response.code() == 200 && response.body() != null && !response.body().isEmpty()) {
                    double totalRealizado = 0;
                    double totalDevolucaoMes = 0;
                    int qtdDevolucoes = 0;
                    for (MesAnoMeta mam : response.body()) {
                        double valor = cleanMoney(mam.getVenda_liquida());
                        if (mam.getE_entrada_s_saida() != null && mam.getE_entrada_s_saida().equalsIgnoreCase("E")) {
                            totalDevolucaoMes += valor;
                            try {
                                if (mam.getQtde_pedidos() != null && !mam.getQtde_pedidos().isEmpty()) {
                                    qtdDevolucoes += Integer.parseInt(mam.getQtde_pedidos());
                                }
                            } catch (Exception e) {
                                qtdDevolucoes++; // Fallback caso o parse falhe
                            }
                            if ( mam.getFigura_rel_comercial() != null && mam.getFigura_rel_comercial().equalsIgnoreCase("S")) {
                                totalRealizado -= valor;
                                // NOVO: Pega a quantidade real de títulos devolvidos do campo qtde_pedidos
                            } //else {
                              //  totalRealizado += valor;
                            //}
                        } else {
                            totalRealizado += valor;
                        }
                    }
                    data.setRealizadoMes(totalRealizado);
                    data.setValorDevolucaoMes(totalDevolucaoMes);
                    data.setQtdDevolucoesMes(qtdDevolucoes);

                    // Busca Meta Mensal no Firestore (Verifica se CNPJ existe antes)
                    if (configuracao != null && configuracao.getCnpj_empresa() != null) {
                        new FirestoreConexao().getMetaMesAno(configuracao.getCnpj_empresa(), String.valueOf(anoAtual), idVendedorFirestore, new FirestoreConexao.OnMetaRetorno() {
                            @Override
                            public void onSucesso(MesAno m) {
                                if (m != null) {
                                    double meta = getMetaDoMes(m, mesAtual);
                                    data.setMetaMes(meta);
                                    if (meta > 0) data.setPorcentagemMes((int)((data.getRealizadoMes() / meta) * 100));
                                }
                                checkFinished();
                            }
                            @Override public void onFalha(Exception e) { 
                                requestFailed = true;
                                checkFinished(); 
                            }
                        });
                    } else {
                        checkFinished();
                    }
                } else {
                    requestFailed = true;
                    checkFinished();
                }
            }
            @Override public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) { 
                requestFailed = true;
                checkFinished(); 
            }
        });
    }

    private void buscarDadosOperacionais(Configuracao configuracao, String status) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        String dataInicio = String.format("%04d-%02d-01", anoAtual, mesAtual);

        Rotas api = Api.buildRetrofit(false);
        if (api == null) {
            checkFinished();
            return;
        }

        api.getSomaTotalRealizadoCanceladoMesPeriodo(idUsuario, dataInicio, "0", status, cabecalho).enqueue(new Callback<List<MesAnoMeta>>() {
            @Override
            public void onResponse(Call<List<MesAnoMeta>> call, Response<List<MesAnoMeta>> response) {
                if (response.code() == 200 && response.body() != null && !response.body().isEmpty()) {
                    MesAnoMeta result = response.body().get(0);
                    int qtd = 0;
                    try { qtd = Integer.parseInt(result.getQtde_pedidos()); } catch (Exception e) {}

                    if ("F".equalsIgnoreCase(status)) {
                        data.setQtdFaturados(qtd);
                        //data.setRealizadoMes(cleanMoney(result.getVenda_liquida())); // Atualiza com o valor oficial do BI
                        
                        // O cálculo do Ticket Médio foi movido para o checkFinished para garantir precisão com as devoluções

                        // Calcula Positivação baseada no novo campo qtde_clientes
                        int qtdPos = 0;
                        try { qtdPos = Integer.parseInt(result.getQtde_clientes()); } catch (Exception e) {}
                        data.setQtdPositivados(qtdPos);

                        // Novo: Captura Novos Clientes (Prospects convertidos) do BI
                        int qtdNewCli = 0;
                        try { qtdNewCli = Integer.parseInt(result.getQtde_prospect()); } catch (Exception e) {}
                        data.setQtdNovosClientes(qtdNewCli);
                        
                        if (data.getQtdClientesAtivos() > 0) {
                            data.setPorcentagemPositivacao((int) (((double) qtdPos / data.getQtdClientesAtivos()) * 100));
                        }
                    } else if ("C".equalsIgnoreCase(status)) {
                        data.setQtdCancelados(qtd);
                        data.setValorCancelados(cleanMoney(result.getVenda_liquida()));
                    }
                }
                checkFinished();
            }
            @Override public void onFailure(Call<List<MesAnoMeta>> call, Throwable t) { 
                requestFailed = true;
                checkFinished(); 
            }
        });
    }

    private synchronized void checkFinished() {
        activeRequests--;
        if (activeRequests <= 0) {
            if (requestFailed) {
                // Tenta carregar do cache se houver falha em alguma requisição
                DashboardData cachedData = loadCache();
                if (cachedData != null) {
                    // Mantém os dados locais frescos
                    cachedData.setQtdPendentes(data.getQtdPendentes());
                    cachedData.setValorPendentes(data.getValorPendentes());
                    cachedData.setQtdClientesAtivos(data.getQtdClientesAtivos());
                    
                    data = cachedData;
                    data.setOffline(true);
                } else {
                    data.setDataHoraAtualizacao("Falha na atualização");
                    data.setOffline(true);
                }
            } else {
                // Sucesso Total - Atualiza timestamp e salva cache
                data.setOffline(false);
                data.setDataHoraAtualizacao(new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(new Date()));

                // AJUSTE TICKET MÉDIO: (Venda Líquida do Mês) / (Qtd Pedidos Faturados - Qtd Títulos Devolvidos)
                int divisor = data.getQtdFaturados();// - data.getQtdDevolucoesMes();
                if (divisor > 0) {
                    data.setTicketMedio(data.getRealizadoMes() / divisor);
                } else if (data.getQtdFaturados() > 0) {
                    // Fallback para evitar divisão por zero se devoluções forem maiores ou iguais a faturados
                    data.setTicketMedio(data.getRealizadoMes() / data.getQtdFaturados());
                } else {
                    data.setTicketMedio(0.0);
                }

                saveCache(data);
            }
            new Handler(Looper.getMainLooper()).post(() -> listener.onDashboardLoaded(data));
        }
    }

    private void saveCache(DashboardData dashboardData) {
        try {
            String json = new Gson().toJson(dashboardData);
            context.getSharedPreferences("DASHBOARD_CACHE", Context.MODE_PRIVATE)
                    .edit()
                    .putString("last_data", json)
                    .apply();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private DashboardData loadCache() {
        try {
            String json = context.getSharedPreferences("DASHBOARD_CACHE", Context.MODE_PRIVATE)
                    .getString("last_data", null);
            if (json != null) {
                return new Gson().fromJson(json, DashboardData.class);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private double cleanMoney(String valor) {
        if (valor == null || valor.isEmpty()) return 0.0;
        try {
            String v = valor.trim();
            if (v.contains(",")) return Double.parseDouble(v.replace(".", "").replace(",", "."));
            if (v.contains(".") && v.indexOf(".") == v.lastIndexOf(".")) return Double.parseDouble(v);
            return Double.parseDouble(v.replace(".", "")) / 100.0;
        } catch (Exception e) { return 0.0; }
    }

    private double getMetaDoMes(MesAno m, int mes) {
        if (m == null) return 0.0;
        switch (mes) {
            case 1: return cleanMoney(m.getJan());
            case 2: return cleanMoney(m.getFev());
            case 3: return cleanMoney(m.getMar());
            case 4: return cleanMoney(m.getAbr());
            case 5: return cleanMoney(m.getMai());
            case 6: return cleanMoney(m.getJun());
            case 7: return cleanMoney(m.getJul());
            case 8: return cleanMoney(m.getAgo());
            case 9: return cleanMoney(m.getSet());
            case 10: return cleanMoney(m.getOut());
            case 11: return cleanMoney(m.getNov());
            case 12: return cleanMoney(m.getDez());
            default: return 0.0;
        }
    }

    private boolean isDataNoPeriodo(String dataCheck, String dataInicio, String dataFim) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date check = sdf.parse(dataCheck);
            Date inicio = sdf.parse(dataInicio);
            Date fim = sdf.parse(dataFim);
            return (check.after(inicio) || check.equals(inicio)) && (check.before(fim) || check.equals(fim));
        } catch (Exception e) { return false; }
    }
}
