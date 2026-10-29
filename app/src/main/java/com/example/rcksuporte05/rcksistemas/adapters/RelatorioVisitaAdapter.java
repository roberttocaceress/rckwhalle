package com.example.rcksuporte05.rcksistemas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.WebVisita;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RelatorioVisitaAdapter extends RecyclerView.Adapter<RelatorioVisitaAdapter.VisitaViewHolder> {

    public interface OnVisitaClickListener {
        void onVisitaClick(WebVisita visita);
    }

    private Context context;
    private List<WebVisita> listaVisitas;
    private OnVisitaClickListener listener;

    public RelatorioVisitaAdapter(Context context, List<WebVisita> listaVisitas, OnVisitaClickListener listener) {
        this.context = context;
        this.listaVisitas = listaVisitas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VisitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_relatorio_visita, parent, false);
        return new VisitaViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VisitaViewHolder holder, int position) {
        WebVisita visita = listaVisitas.get(position);
        if (visita == null) return;

        String razao = visita.getRazaoSocial() != null && !visita.getRazaoSocial().isEmpty()
                ? visita.getRazaoSocial() : "Cliente #" + visita.getIdClienteServidor();
        holder.tvRazaoSocial.setText(razao);

        if (visita.getNomeFantasia() != null && !visita.getNomeFantasia().isEmpty()) {
            holder.tvNomeFantasia.setVisibility(View.VISIBLE);
            holder.tvNomeFantasia.setText(visita.getNomeFantasia());
        } else {
            holder.tvNomeFantasia.setVisibility(View.GONE);
        }

        holder.tvDataHora.setText("Check-in: " + formatarDataHora(visita.getDataHoraCheckin()));

        boolean isVenda = "V".equalsIgnoreCase(visita.getStatusVisita());

        if (isVenda) {
            holder.vStatusCirculo.setBackgroundResource(R.drawable.bg_circulo_verde);
            holder.lyDetalhesNaoVenda.setVisibility(View.GONE);
            holder.lyDetalhesVenda.setVisibility(View.VISIBLE);

            String numPed = visita.getIdPedido() > 0 ? "" +  visita.getIdPedido() : (visita.getIdPedidoApp() != null ? visita.getIdPedidoApp() : "-");
            String numSrv = visita.getIdPedidoServidor() > 0 ? "" +  visita.getIdPedidoServidor() :  "";
            holder.tvPedidoVisita.setText("• Pedido: " + numPed + " -- " + numSrv);
            holder.tvValorPedidoVisita.setText("• Valor: " + MascaraUtil.mascaraRealSimboloD(visita.getValorPedido()));
        } else {
            holder.vStatusCirculo.setBackgroundResource(R.drawable.bg_circulo_vermelho);
            holder.lyDetalhesNaoVenda.setVisibility(View.VISIBLE);
            holder.lyDetalhesVenda.setVisibility(View.GONE);

            String motivo = visita.getMotivoNaoVenda() != null && !visita.getMotivoNaoVenda().isEmpty()
                    ? visita.getMotivoNaoVenda() : "Motivo não informado";
            holder.tvMotivos.setText("• Motivo(s): " + motivo);

            String obs = visita.getObservacao() != null && !visita.getObservacao().isEmpty()
                    ? visita.getObservacao() : "Sem observação";
            holder.tvObservacao.setText("• Obs: " + obs);
        }

        if (visita.getLatitudeCheckin() != null && !visita.getLatitudeCheckin().trim().isEmpty() &&
                visita.getLongitudeCheckin() != null && !visita.getLongitudeCheckin().trim().isEmpty()) {

            String gpsStr = "GPS: " + visita.getLatitudeCheckin().trim() + ", " + visita.getLongitudeCheckin().trim() +
                    (visita.getPrecisaoCheckin() != null ? " (Precisão: " + visita.getPrecisaoCheckin() + ")" : "") +
                    "  🗺️ (Toque p/ Mapa)";
            holder.tvCoordenadas.setText(gpsStr);

            holder.tvCoordenadas.setOnClickListener(v -> abrirGoogleMaps(visita));
        } else {
            holder.tvCoordenadas.setText("Check-in não executado (Parâmetro desativado)");
            holder.tvCoordenadas.setOnClickListener(null);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVisitaClick(visita);
            }
        });
    }

    private void abrirGoogleMaps(WebVisita visita) {
        if (visita == null || visita.getLatitudeCheckin() == null || visita.getLongitudeCheckin() == null) return;
        try {
            String lat = visita.getLatitudeCheckin().trim();
            String lng = visita.getLongitudeCheckin().trim();
            String label = visita.getRazaoSocial() != null && !visita.getRazaoSocial().isEmpty()
                    ? visita.getRazaoSocial() : "Check-in Visita";

            android.net.Uri gmmIntentUri = android.net.Uri.parse("geo:" + lat + "," + lng + "?q=" + lat + "," + lng + "(" + android.net.Uri.encode(label) + ")");
            android.content.Intent mapIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            } else {
                android.content.Intent genericIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://maps.google.com/?q=" + lat + "," + lng));
                context.startActivity(genericIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                String lat = visita.getLatitudeCheckin().trim();
                String lng = visita.getLongitudeCheckin().trim();
                android.content.Intent genericIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://maps.google.com/?q=" + lat + "," + lng));
                context.startActivity(genericIntent);
            } catch (Exception ignored) {}
        }
    }

    private String formatarDataHora(String dataHoraRaw) {
        if (dataHoraRaw == null || dataHoraRaw.trim().isEmpty()) {
            return "--/--/----";
        }
        try {
            SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date date = sdfIn.parse(dataHoraRaw);
            if (date != null) {
                SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                return sdfOut.format(date);
            }
        } catch (Exception e1) {
            try {
                SimpleDateFormat sdfIn2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date2 = sdfIn2.parse(dataHoraRaw);
                if (date2 != null) {
                    SimpleDateFormat sdfOut2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    return sdfOut2.format(date2);
                }
            } catch (Exception ignored) {}
        }
        return dataHoraRaw;
    }

    @Override
    public int getItemCount() {
        return listaVisitas != null ? listaVisitas.size() : 0;
    }

    public static class VisitaViewHolder extends RecyclerView.ViewHolder {
        TextView tvRazaoSocial, tvNomeFantasia, tvDataHora, tvPedidoVisita, tvValorPedidoVisita, tvMotivos, tvObservacao, tvCoordenadas;
        View vStatusCirculo;
        LinearLayout lyDetalhesVenda, lyDetalhesNaoVenda;

        public VisitaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRazaoSocial = itemView.findViewById(R.id.tvRazaoSocialVisita);
            tvNomeFantasia = itemView.findViewById(R.id.tvNomeFantasiaVisita);
            tvDataHora = itemView.findViewById(R.id.tvDataHoraVisita);
            vStatusCirculo = itemView.findViewById(R.id.vStatusCirculoVisita);
            tvPedidoVisita = itemView.findViewById(R.id.tvPedidoVisita);
            tvValorPedidoVisita = itemView.findViewById(R.id.tvValorPedidoVisita);
            tvMotivos = itemView.findViewById(R.id.tvMotivosVisita);
            tvObservacao = itemView.findViewById(R.id.tvObservacaoVisita);
            tvCoordenadas = itemView.findViewById(R.id.tvCoordenadasVisita);
            lyDetalhesVenda = itemView.findViewById(R.id.lyDetalhesVenda);
            lyDetalhesNaoVenda = itemView.findViewById(R.id.lyDetalhesNaoVenda);
        }
    }
}
