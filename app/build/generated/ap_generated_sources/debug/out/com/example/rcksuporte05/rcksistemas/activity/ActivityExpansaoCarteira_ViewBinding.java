// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.github.mikephil.charting.charts.BarChart;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityExpansaoCarteira_ViewBinding implements Unbinder {
  private ActivityExpansaoCarteira target;

  @UiThread
  public ActivityExpansaoCarteira_ViewBinding(ActivityExpansaoCarteira target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityExpansaoCarteira_ViewBinding(ActivityExpansaoCarteira target, View source) {
    this.target = target;

    target.tvTotalNovos = Utils.findRequiredViewAsType(source, R.id.tvKpiTotalNovos, "field 'tvTotalNovos'", TextView.class);
    target.tvMetaExpansao = Utils.findRequiredViewAsType(source, R.id.tvKpiMetaExpansao, "field 'tvMetaExpansao'", TextView.class);
    target.tvPercExpansao = Utils.findRequiredViewAsType(source, R.id.tvKpiPercExpansao, "field 'tvPercExpansao'", TextView.class);
    target.pbKpiExpansao = Utils.findRequiredViewAsType(source, R.id.pbKpiExpansao, "field 'pbKpiExpansao'", ProgressBar.class);
    target.cvKpiPercExpansao = Utils.findRequiredView(source, R.id.cvKpiPercExpansao, "field 'cvKpiPercExpansao'");
    target.cvGraficoExpansao = Utils.findRequiredView(source, R.id.cvGraficoExpansao, "field 'cvGraficoExpansao'");
    target.barChart = Utils.findRequiredViewAsType(source, R.id.chartExpansao, "field 'barChart'", BarChart.class);
    target.rvLista = Utils.findRequiredViewAsType(source, R.id.rvExpansaoLista, "field 'rvLista'", RecyclerView.class);
    target.nestedScrollViewExpansao = Utils.findRequiredViewAsType(source, R.id.nestedScrollViewExpansao, "field 'nestedScrollViewExpansao'", NestedScrollView.class);
    target.tvDataPesquisa = Utils.findRequiredViewAsType(source, R.id.tvDataPesquisa, "field 'tvDataPesquisa'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.lytGlobalLoading = Utils.findRequiredView(source, R.id.lytGlobalLoading, "field 'lytGlobalLoading'");
    target.tvGlobalLoadingTitle = Utils.findRequiredViewAsType(source, R.id.tvGlobalLoadingTitle, "field 'tvGlobalLoadingTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityExpansaoCarteira target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTotalNovos = null;
    target.tvMetaExpansao = null;
    target.tvPercExpansao = null;
    target.pbKpiExpansao = null;
    target.cvKpiPercExpansao = null;
    target.cvGraficoExpansao = null;
    target.barChart = null;
    target.rvLista = null;
    target.nestedScrollViewExpansao = null;
    target.tvDataPesquisa = null;
    target.toolbar = null;
    target.lytGlobalLoading = null;
    target.tvGlobalLoadingTitle = null;
  }
}
