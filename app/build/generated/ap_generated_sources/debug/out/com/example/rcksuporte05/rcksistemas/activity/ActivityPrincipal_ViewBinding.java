// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPrincipal_ViewBinding implements Unbinder {
  private ActivityPrincipal target;

  @UiThread
  public ActivityPrincipal_ViewBinding(ActivityPrincipal target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPrincipal_ViewBinding(ActivityPrincipal target, View source) {
    this.target = target;

    target.btnCliente = Utils.findRequiredViewAsType(source, R.id.btnCliente, "field 'btnCliente'", Button.class);
    target.btnProduto = Utils.findRequiredViewAsType(source, R.id.btnProduto, "field 'btnProduto'", Button.class);
    target.btnPedidos = Utils.findRequiredViewAsType(source, R.id.btnPedido, "field 'btnPedidos'", Button.class);
    target.btnSincroniza = Utils.findRequiredViewAsType(source, R.id.btnSincroniza, "field 'btnSincroniza'", Button.class);
    target.btnPedidoFinalizado = Utils.findRequiredViewAsType(source, R.id.btnPedidoFinalizado, "field 'btnPedidoFinalizado'", Button.class);
    target.btnPedidoPendente = Utils.findRequiredViewAsType(source, R.id.btnPedidoPendente, "field 'btnPedidoPendente'", Button.class);
    target.btnProspectNovo = Utils.findRequiredViewAsType(source, R.id.btnProspectNovo, "field 'btnProspectNovo'", Button.class);
    target.btnCampanha = Utils.findRequiredViewAsType(source, R.id.btnCampanha, "field 'btnCampanha'", Button.class);
    target.btnOutrosIndicadores = Utils.findRequiredViewAsType(source, R.id.btnOutrosIndicadores, "field 'btnOutrosIndicadores'", Button.class);
    target.btnOcultarBI = Utils.findRequiredViewAsType(source, R.id.btnOcultarBI, "field 'btnOcultarBI'", ImageButton.class);
    target.lytConteudoBI = Utils.findRequiredViewAsType(source, R.id.lytConteudoBI, "field 'lytConteudoBI'", LinearLayout.class);
    target.tvPorcentagemSemana = Utils.findRequiredViewAsType(source, R.id.tvPorcentagemSemana, "field 'tvPorcentagemSemana'", TextView.class);
    target.tvTituloMetaSemana = Utils.findRequiredViewAsType(source, R.id.tvTituloMetaSemana, "field 'tvTituloMetaSemana'", TextView.class);
    target.tvValoresSemana = Utils.findRequiredViewAsType(source, R.id.tvValoresSemana, "field 'tvValoresSemana'", TextView.class);
    target.tvRankingSemana = Utils.findRequiredViewAsType(source, R.id.tvRankingSemana, "field 'tvRankingSemana'", TextView.class);
    target.tvPorcentagemMes = Utils.findRequiredViewAsType(source, R.id.tvPorcentagemMes, "field 'tvPorcentagemMes'", TextView.class);
    target.tvTituloMetaMes = Utils.findRequiredViewAsType(source, R.id.tvTituloMetaMes, "field 'tvTituloMetaMes'", TextView.class);
    target.tvValoresMes = Utils.findRequiredViewAsType(source, R.id.tvValoresMes, "field 'tvValoresMes'", TextView.class);
    target.tvRankingMes = Utils.findRequiredViewAsType(source, R.id.tvRankingMes, "field 'tvRankingMes'", TextView.class);
    target.tvFaltaMes = Utils.findRequiredViewAsType(source, R.id.tvFaltaMes, "field 'tvFaltaMes'", TextView.class);
    target.tvQtdeVisitas = Utils.findRequiredViewAsType(source, R.id.tvQtdeVisitas, "field 'tvQtdeVisitas'", TextView.class);
    target.tvPedidosFaturados = Utils.findRequiredViewAsType(source, R.id.tvPedidosFaturados, "field 'tvPedidosFaturados'", TextView.class);
    target.tvPedidosDevolvidos = Utils.findRequiredViewAsType(source, R.id.tvPedidosDevolvidos, "field 'tvPedidosDevolvidos'", TextView.class);
    target.tvPedidosCancelados = Utils.findRequiredViewAsType(source, R.id.tvPedidosCancelados, "field 'tvPedidosCancelados'", TextView.class);
    target.tvNovosClientes = Utils.findRequiredViewAsType(source, R.id.tvNovosClientes, "field 'tvNovosClientes'", TextView.class);
    target.progressSemana = Utils.findRequiredViewAsType(source, R.id.progressSemana, "field 'progressSemana'", ProgressBar.class);
    target.progressMes = Utils.findRequiredViewAsType(source, R.id.progressMes, "field 'progressMes'", ProgressBar.class);
    target.tvPositivacao = Utils.findRequiredViewAsType(source, R.id.tvPositivacao, "field 'tvPositivacao'", TextView.class);
    target.tvTicketMedio = Utils.findRequiredViewAsType(source, R.id.tvTicketMedio, "field 'tvTicketMedio'", TextView.class);
    target.tvPedidosATransmitir = Utils.findRequiredViewAsType(source, R.id.tvPedidosATransmitir, "field 'tvPedidosATransmitir'", TextView.class);
    target.tvUltimaAtualizacao = Utils.findRequiredViewAsType(source, R.id.tvUltimaAtualizacao, "field 'tvUltimaAtualizacao'", TextView.class);
    target.txvProspectNovo = Utils.findRequiredViewAsType(source, R.id.txvProspectNovo, "field 'txvProspectNovo'", TextView.class);
    target.ivInternet = Utils.findRequiredViewAsType(source, R.id.ivInternet, "field 'ivInternet'", ImageView.class);
    target.lytCampanhas = Utils.findRequiredViewAsType(source, R.id.lytCampanhas, "field 'lytCampanhas'", LinearLayout.class);
    target.shimmerLayout = Utils.findRequiredViewAsType(source, R.id.shimmerLayout, "field 'shimmerLayout'", ShimmerFrameLayout.class);
    target.txvAguarde = Utils.findRequiredViewAsType(source, R.id.txvAguarde, "field 'txvAguarde'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPrincipal target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnCliente = null;
    target.btnProduto = null;
    target.btnPedidos = null;
    target.btnSincroniza = null;
    target.btnPedidoFinalizado = null;
    target.btnPedidoPendente = null;
    target.btnProspectNovo = null;
    target.btnCampanha = null;
    target.btnOutrosIndicadores = null;
    target.btnOcultarBI = null;
    target.lytConteudoBI = null;
    target.tvPorcentagemSemana = null;
    target.tvTituloMetaSemana = null;
    target.tvValoresSemana = null;
    target.tvRankingSemana = null;
    target.tvPorcentagemMes = null;
    target.tvTituloMetaMes = null;
    target.tvValoresMes = null;
    target.tvRankingMes = null;
    target.tvFaltaMes = null;
    target.tvQtdeVisitas = null;
    target.tvPedidosFaturados = null;
    target.tvPedidosDevolvidos = null;
    target.tvPedidosCancelados = null;
    target.tvNovosClientes = null;
    target.progressSemana = null;
    target.progressMes = null;
    target.tvPositivacao = null;
    target.tvTicketMedio = null;
    target.tvPedidosATransmitir = null;
    target.tvUltimaAtualizacao = null;
    target.txvProspectNovo = null;
    target.ivInternet = null;
    target.lytCampanhas = null;
    target.shimmerLayout = null;
    target.txvAguarde = null;
  }
}
