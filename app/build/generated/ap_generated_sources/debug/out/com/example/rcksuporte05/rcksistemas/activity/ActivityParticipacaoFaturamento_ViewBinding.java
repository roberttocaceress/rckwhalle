// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityParticipacaoFaturamento_ViewBinding implements Unbinder {
  private ActivityParticipacaoFaturamento target;

  @UiThread
  public ActivityParticipacaoFaturamento_ViewBinding(ActivityParticipacaoFaturamento target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityParticipacaoFaturamento_ViewBinding(ActivityParticipacaoFaturamento target,
      View source) {
    this.target = target;

    target.pieChart = Utils.findRequiredViewAsType(source, R.id.pieChart, "field 'pieChart'", PieChart.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.btnFlipCard = Utils.findRequiredViewAsType(source, R.id.btnFlipCard, "field 'btnFlipCard'", Button.class);
    target.caixa_layout_grafico = Utils.findRequiredViewAsType(source, R.id.caixa_layout_grafico, "field 'caixa_layout_grafico'", FrameLayout.class);
    target.caixa_layout_resumo = Utils.findRequiredViewAsType(source, R.id.caixa_layout_resumo, "field 'caixa_layout_resumo'", FrameLayout.class);
    target.shimmerLayout = Utils.findRequiredViewAsType(source, R.id.shimmerLayout, "field 'shimmerLayout'", ShimmerFrameLayout.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.floatingActionButton = Utils.findRequiredViewAsType(source, R.id.floatingActionButton, "field 'floatingActionButton'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityParticipacaoFaturamento target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pieChart = null;
    target.recyclerView = null;
    target.btnFlipCard = null;
    target.caixa_layout_grafico = null;
    target.caixa_layout_resumo = null;
    target.shimmerLayout = null;
    target.toolbar = null;
    target.floatingActionButton = null;
  }
}
