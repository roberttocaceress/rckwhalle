package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.Helper.GraficoFaturamentoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ParticipacaoFaturamentoAdapter;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.MesAnoMeta;
import com.example.rcksuporte05.rcksistemas.model.MesSemana;
import com.example.rcksuporte05.rcksistemas.model.ParticipacaoFaturamento;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 06/10/2017.
 */

public class ListaFaturamentoCarrosel extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<EvolucaoFaturamento> lista;
    private List<MesAnoMeta> listaMesAnoMeta;
    private List<MesSemana> listaMesSemana;
    private List<ParticipacaoFaturamento> listaParticipacao;
    private List<Financeiro> listaFinanceiro;
    private String tipoRelatorio;
    private Context context;

    public static ListaFaturamentoCarrosel newInstance(int position, String tipoRelatorio) {
        ListaFaturamentoCarrosel fragment = new ListaFaturamentoCarrosel();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("tipoRelatorio", tipoRelatorio);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lista = (List<EvolucaoFaturamento>) getArguments().getSerializable("lista");
            tipoRelatorio = getArguments().getString("tipoRelatorio");
        }

        // Recupera do Helper caso os argumentos estejam vazios (padrão do Adapter)
        if (lista == null) {
            lista = GraficoFaturamentoHelper.getListaEvolucaoFaturamento();
        }
        if (listaMesAnoMeta == null) {
            listaMesAnoMeta = GraficoFaturamentoHelper.getListaMesAnoMeta();
        }
        if (listaMesSemana == null) {
            listaMesSemana = GraficoFaturamentoHelper.getListaMesSemana();
        }
        if (listaParticipacao == null) {
            listaParticipacao = GraficoFaturamentoHelper.getListaParticipacaoFaturamento();
        }
        if (listaFinanceiro == null) {
            listaFinanceiro = GraficoFaturamentoHelper.getListaFinanceiro();
        }
        if (tipoRelatorio == null) {
            tipoRelatorio = GraficoFaturamentoHelper.getTipoRelatorio();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_faturamento_carrosel_fragment, container, false);
        ButterKnife.bind(this, view);
        context = getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setReciclerView(lista);


        return view;
    }

    private void setReciclerView(List<EvolucaoFaturamento> listaRefOriginal) {
        float valor_bruto = 0.00f;
        double valor_liquido = 0.00;
        float total_saida = 0.00f;
        float total_entrada = 0.00f;

        List<ParticipacaoFaturamento> listaFaturamento = new ArrayList<>();

        if (tipoRelatorio != null && tipoRelatorio.equalsIgnoreCase("M") && listaMesAnoMeta != null) {
            for (MesAnoMeta item : listaMesAnoMeta) {
                try {
                    float vLiq = Float.parseFloat(item.getVenda_liquida().replace(".", "").replace(",", "."));
                    valor_liquido += vLiq;
                    ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
                    pf.setNome(formatarMesAno(item.getNome_mes(), null));
                    pf.setValor_liquido(String.valueOf(vLiq));
                    listaFaturamento.add(pf);
                } catch (Exception e) { e.printStackTrace(); }
            }
        } else if (tipoRelatorio != null && tipoRelatorio.equalsIgnoreCase("S") && listaMesSemana != null) {
            // Agrupamento por mês para subtotais (similar à lógica do gráfico)
            Map<String, List<MesSemana>> mapaMeses = new LinkedHashMap<>();
            for (MesSemana ms : listaMesSemana) {
                String data = ms.getIni_semama();
                if (data == null || data.length() < 7) continue;
                String chaveMes = data.substring(0, 7); 
                if (!mapaMeses.containsKey(chaveMes)) mapaMeses.put(chaveMes, new ArrayList<>());
                mapaMeses.get(chaveMes).add(ms);
            }

            for (String mesKey : mapaMeses.keySet()) {
                double subtotalMes = 0;
                List<MesSemana> semanas = mapaMeses.get(mesKey);
                for (MesSemana ms : semanas) {
                    try {
                        float vLiq = Float.parseFloat(ms.getVenda_liquida().replace(".", "").replace(",", "."));
                        subtotalMes += vLiq;
                        valor_liquido += vLiq;
                        ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
                        pf.setNome(ms.getNome_semana() + " (" + formatarMesAno(mesKey, null) + ")");
                        pf.setValor_liquido(String.valueOf(vLiq));
                        listaFaturamento.add(pf);
                    } catch (Exception e) { e.printStackTrace(); }
                }
                ParticipacaoFaturamento pfTotal = new ParticipacaoFaturamento();
                pfTotal.setNome("TOTAL " + formatarMesAno(mesKey, null));
                pfTotal.setValor_liquido(String.valueOf(subtotalMes));
                listaFaturamento.add(pfTotal);
            }
        } else if (tipoRelatorio != null && tipoRelatorio.startsWith("FIN")) {
            if (tipoRelatorio.equals("FIN_RK")) {
                // LÓGICA DE RANKING FINANCEIRO (Agregado por Cliente)
                Map<String, ParticipacaoFaturamento> mapaRanking = new LinkedHashMap<>();
                for (Financeiro f : (listaFinanceiro != null ? listaFinanceiro : new ArrayList<Financeiro>())) {
                    String idCli = f.getId_favorecido() != null ? f.getId_favorecido().trim() : "0";
                    double valor = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());

                    if (!mapaRanking.containsKey(idCli)) {
                        ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
                        pf.setId(idCli);
                        pf.setNome(f.getNome_favorecido());
                        pf.setValor_liquido(String.valueOf(valor));
                        mapaRanking.put(idCli, pf);
                    } else {
                        ParticipacaoFaturamento pf = mapaRanking.get(idCli);
                        double total = Double.parseDouble(pf.getValor_liquido()) + valor;
                        pf.setValor_liquido(String.valueOf(total));
                    }
                }
                List<ParticipacaoFaturamento> listaRank = new ArrayList<>(mapaRanking.values());
                Collections.sort(listaRank, (o1, o2) -> Double.compare(Double.parseDouble(o2.getValor_liquido()), Double.parseDouble(o1.getValor_liquido())));
                
                int rankingPos = 1;
                for (ParticipacaoFaturamento pf : listaRank) {
                    ParticipacaoFaturamento pfFinal = new ParticipacaoFaturamento();
                    pfFinal.setNome(rankingPos + "º " + pf.getNome());
                    pfFinal.setValor_liquido(pf.getValor_liquido());
                    listaFaturamento.add(pfFinal);
                    valor_liquido += Double.parseDouble(pf.getValor_liquido());
                    rankingPos++;
                }

            } else {
                // LÓGICA FINANCEIRA (A Vencer / Vencidos)
                Map<String, List<Financeiro>> mapaFinanceiro = new LinkedHashMap<>();
                int diasRef = GraficoFaturamentoHelper.getDiasReferenciaFinanceiro();

                if (tipoRelatorio.equals("FIN_AV")) {
                    mapaFinanceiro.put("Hoje", new ArrayList<>());
                    mapaFinanceiro.put("7/D", new ArrayList<>());
                    mapaFinanceiro.put("15/D", new ArrayList<>());
                    mapaFinanceiro.put("30/D", new ArrayList<>());
                    if (diasRef >= 60) mapaFinanceiro.put("60/D", new ArrayList<>());
                    if (diasRef >= 90) mapaFinanceiro.put("90/D", new ArrayList<>());

                    for (Financeiro f : (listaFinanceiro != null ? listaFinanceiro : new ArrayList<Financeiro>())) {
                        int diasAtraso = 0;
                        try { diasAtraso = Math.abs(Integer.parseInt(f.getDias_atraso())); } catch (Exception e) {}
                        if (diasAtraso == 0) mapaFinanceiro.get("Hoje").add(f);
                        else if (diasAtraso <= 7) mapaFinanceiro.get("7/D").add(f);
                        else if (diasAtraso <= 15) mapaFinanceiro.get("15/D").add(f);
                        else if (diasAtraso <= 30) mapaFinanceiro.get("30/D").add(f);
                        else if (diasAtraso <= 60 && diasRef >= 60) mapaFinanceiro.get("60/D").add(f);
                        else if (diasAtraso <= 90 && diasRef >= 90) mapaFinanceiro.get("90/D").add(f);
                    }
                } else {
                    mapaFinanceiro.put("1-7/D", new ArrayList<>());
                    mapaFinanceiro.put("8-15/D", new ArrayList<>());
                    mapaFinanceiro.put("16-30/D", new ArrayList<>());
                    if (diasRef >= 60) mapaFinanceiro.put("31-60/D", new ArrayList<>());
                    if (diasRef >= 90) mapaFinanceiro.put("61-90/D", new ArrayList<>());

                    for (Financeiro f : (listaFinanceiro != null ? listaFinanceiro : new ArrayList<Financeiro>())) {
                        int diasAtraso = 0;
                        try { diasAtraso = Integer.parseInt(f.getDias_atraso()); } catch (Exception e) {}
                        if (diasAtraso > 0) {
                            if (diasAtraso <= 7) mapaFinanceiro.get("1-7/D").add(f);
                            else if (diasAtraso <= 15) mapaFinanceiro.get("8-15/D").add(f);
                            else if (diasAtraso <= 30) mapaFinanceiro.get("16-30/D").add(f);
                            else if (diasAtraso <= 60 && diasRef >= 60) mapaFinanceiro.get("31-60/D").add(f);
                            else if (diasAtraso <= 90 && diasRef >= 90) mapaFinanceiro.get("61-90/D").add(f);
                        }
                    }
                }

                for (String faixa : mapaFinanceiro.keySet()) {
                    double subtotalFaixa = 0;
                    List<Financeiro> titulos = mapaFinanceiro.get(faixa);
                    if (titulos == null || titulos.isEmpty()) continue;

                    ParticipacaoFaturamento pfHeader = new ParticipacaoFaturamento();
                    pfHeader.setNome("SUBTOTAL " + faixa);
                    pfHeader.setValor_liquido("0");
                    listaFaturamento.add(pfHeader);
                    int headerIdx = listaFaturamento.size() - 1;

                    for (Financeiro f : titulos) {
                        double v = Double.parseDouble(f.getValor_saldo() == null || f.getValor_saldo().isEmpty() ? "0" : f.getValor_saldo());
                        subtotalFaixa += v;
                        valor_liquido += v;

                        ParticipacaoFaturamento item = new ParticipacaoFaturamento();
                        String nomeCli = f.getNome_favorecido() != null ? f.getNome_favorecido() : "CLIENTE NÃO IDENTIFICADO";
                        String dataVenc = f.getData_vencimento() != null ? MascaraUtil.formataData(f.getData_vencimento(), "/", 8) : "--/--/--";
                        item.setNome(nomeCli + " (Venc: " + dataVenc + ")");
                        item.setValor_liquido(String.valueOf(v));
                        listaFaturamento.add(item);
                    }
                    listaFaturamento.get(headerIdx).setValor_liquido(String.valueOf(subtotalFaixa));
                }
            }
        } else if (tipoRelatorio != null && (tipoRelatorio.equalsIgnoreCase("G") || tipoRelatorio.equalsIgnoreCase("C"))) {
            java.util.Map<String, List<ParticipacaoFaturamento>> mapaPorMes = new java.util.LinkedHashMap<>();
            for (ParticipacaoFaturamento item : (listaParticipacao != null ? listaParticipacao : new ArrayList<ParticipacaoFaturamento>())) {
                String mesAno = formatarMesAno(item.getNome_mes(), item.getAno());
                if (!mapaPorMes.containsKey(mesAno)) {
                    mapaPorMes.put(mesAno, new ArrayList<>());
                }
                mapaPorMes.get(mesAno).add(item);
            }

            for (String mesAno : mapaPorMes.keySet()) {
                float subtotalMes = 0;
                int rankingPosicao = 1; 
                
                for (ParticipacaoFaturamento item : mapaPorMes.get(mesAno)) {
                    try {
                        float vLiq = Float.parseFloat(item.getValor_liquido() == null || item.getValor_liquido().isEmpty() ? "0.00" : item.getValor_liquido());
                        boolean isDevolucao = item.getE_entrada_s_saida() != null && item.getE_entrada_s_saida().equalsIgnoreCase("E");

                        float valorFinalItem = isDevolucao ? -Math.abs(vLiq) : Math.abs(vLiq);
                        subtotalMes += valorFinalItem;
                        
                        if (isDevolucao) total_entrada += Math.abs(vLiq); else total_saida += Math.abs(vLiq);
                        valor_liquido += valorFinalItem;

                        ParticipacaoFaturamento pf = new ParticipacaoFaturamento();
                        String prefixoRanking = "";
                        if ((tipoRelatorio.equalsIgnoreCase("C") || tipoRelatorio.equalsIgnoreCase("G")) && !isDevolucao) {
                            prefixoRanking = rankingPosicao + "º ";
                        }
                        pf.setNome(prefixoRanking + item.getNome() + (isDevolucao ? " (DEV)" : ""));
                        pf.setValor_liquido(String.valueOf(valorFinalItem));
                        listaFaturamento.add(pf);
                        if (!isDevolucao) rankingPosicao++;
                    } catch (Exception e) {}
                }
                ParticipacaoFaturamento pfSubtotal = new ParticipacaoFaturamento();
                pfSubtotal.setNome("SUBTOTAL " + mesAno);
                pfSubtotal.setValor_liquido(String.valueOf(subtotalMes));
                listaFaturamento.add(pfSubtotal);
            }
        } else if (tipoRelatorio != null && tipoRelatorio.equalsIgnoreCase("S")) {
            List<EvolucaoFaturamento> listaRef = (lista != null) ? lista : new ArrayList<>();
            for (int i = 0; i < listaRef.size(); i++) {
                EvolucaoFaturamento mesRef = listaRef.get(i);
                float subtotalLiquidoMes = 0.00f;
                String nomeMesAno = formatarMesAno(mesRef.getMes(), mesRef.getAno());

                if (mesRef.getTotal_por_grupo() != null) {
                    for (EvolucaoFaturamento semana : mesRef.getTotal_por_grupo()) {
                        try {
                            float vLiq = Float.parseFloat(semana.getTotal_liquido());
                            subtotalLiquidoMes += vLiq;
                            valor_liquido += vLiq;
                            valor_bruto += Float.parseFloat(semana.getTotal_bruto());

                            ParticipacaoFaturamento pfSemana = new ParticipacaoFaturamento();
                            pfSemana.setNome(semana.getMes() + "ª SEMANA " + nomeMesAno);
                            pfSemana.setValor_bruto("0.00");
                            pfSemana.setValor_liquido(semana.getTotal_liquido());
                            listaFaturamento.add(pfSemana);
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                }
                ParticipacaoFaturamento pfSubtotal = new ParticipacaoFaturamento();
                pfSubtotal.setNome("TOTAL " + nomeMesAno);
                pfSubtotal.setValor_bruto("0.00");
                pfSubtotal.setValor_liquido(String.valueOf(subtotalLiquidoMes));
                listaFaturamento.add(pfSubtotal);
            }
        } else if (tipoRelatorio != null && (tipoRelatorio.equalsIgnoreCase("M") || tipoRelatorio.equalsIgnoreCase("MM") || tipoRelatorio.equalsIgnoreCase("MG"))) {
            List<EvolucaoFaturamento> listaRef = (lista != null) ? lista : new ArrayList<>();
            for (int i = 0; i < listaRef.size(); i++) {
                try {
                    valor_bruto += Float.parseFloat(listaRef.get(i).getTotal_bruto());
                    valor_liquido += Float.parseFloat(listaRef.get(i).getTotal_liquido());
                    ParticipacaoFaturamento participacaoFaturamento = new ParticipacaoFaturamento();
                    participacaoFaturamento.setNome(formatarMesAno(listaRef.get(i).getMes(), listaRef.get(i).getAno()));
                    participacaoFaturamento.setValor_bruto("0.00");
                    participacaoFaturamento.setValor_liquido(listaRef.get(i).getTotal_liquido());
                    listaFaturamento.add(participacaoFaturamento);
                } catch (NullPointerException | NumberFormatException e) { e.printStackTrace(); }
            }
        } else {
            List<EvolucaoFaturamento> listaRef = (lista != null) ? lista : new ArrayList<>();
            if (listaRef != null) {
                for (int i = listaRef.size() - 1; i >= 0; i--) {
                    try {
                        valor_bruto += Float.parseFloat(listaRef.get(i).getTotal_bruto());
                        valor_liquido += Float.parseFloat(listaRef.get(i).getTotal_liquido());
                        ParticipacaoFaturamento participacaoFaturamento = new ParticipacaoFaturamento();
                        participacaoFaturamento.setNome(formatarMesAno(listaRef.get(i).getMes(), listaRef.get(i).getAno()));
                        participacaoFaturamento.setValor_bruto("0.00");
                        participacaoFaturamento.setValor_liquido(listaRef.get(i).getTotal_liquido());
                        listaFaturamento.add(participacaoFaturamento);
                    } catch (NullPointerException | NumberFormatException e) { e.printStackTrace(); }
                }
            }
        }

        if (listaFaturamento.size() > 0) {
            if (tipoRelatorio != null && tipoRelatorio.equalsIgnoreCase("C") && total_entrada > 0) {
                ParticipacaoFaturamento pfSaida = new ParticipacaoFaturamento();
                pfSaida.setNome("TOTAL VENDAS (SAÍDAS)");
                pfSaida.setValor_liquido(String.valueOf(total_saida));
                listaFaturamento.add(pfSaida);

                ParticipacaoFaturamento pfEntrada = new ParticipacaoFaturamento();
                pfEntrada.setNome("TOTAL DEVOLUÇÕES (ENTRADAS)");
                pfEntrada.setValor_liquido(String.valueOf(total_entrada));
                listaFaturamento.add(pfEntrada);

                ParticipacaoFaturamento pfGeral = new ParticipacaoFaturamento();
                pfGeral.setNome("TOTAL GERAL LÍQUIDO");
                pfGeral.setValor_liquido(String.valueOf(total_saida - total_entrada));
                listaFaturamento.add(pfGeral);
            } else {
                ParticipacaoFaturamento pfGeral = new ParticipacaoFaturamento();
                pfGeral.setNome("TOTAL GERAL NO PERÍODO");
                pfGeral.setValor_liquido(String.valueOf(valor_liquido));
                listaFaturamento.add(pfGeral);
            }
        }

        ParticipacaoFaturamentoAdapter adapter = new ParticipacaoFaturamentoAdapter(listaFaturamento, (tipoRelatorio != null && tipoRelatorio.equalsIgnoreCase("MM")) ? "MM" : "SS");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String formatarMesAno(String mes, String ano) {
        String mesRef = mes;
        String anoRef = ano;
        if (mes != null && mes.contains("-")) {
            String[] partes = mes.split("-");
            if (partes.length >= 2) {
                anoRef = partes[0];
                mesRef = partes[1];
            }
        }
        String nomeMes = setMesAno(mesRef);
        String sufixoAno = "";
        if (anoRef != null && !anoRef.isEmpty()) {
            sufixoAno = "/" + (anoRef.length() >= 4 ? anoRef.substring(anoRef.length() - 2) : anoRef);
        }
        return nomeMes + sufixoAno;
    }

    private String setMesAno(String mes) {
        if (mes == null) return "";
        String m = mes;
        if (m.startsWith("0") && m.length() > 1) m = m.substring(1);
        switch (m) {
            case "1": return "JANEIRO";
            case "2": return "FEVEREIRO";
            case "3": return "MARÇO";
            case "4": return "ABRIL";
            case "5": return "MAIO";
            case "6": return "JUNHO";
            case "7": return "JULHO";
            case "8": return "AGOSTO";
            case "9": return "SETEMBRO";
            case "10": return "OUTUBRO";
            case "11": return "NOVEMBRO";
            case "12": return "DEZEMBRO";
            default: return mes;
        }
    }
}
