// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPainelGraficoEquipe_ViewBinding implements Unbinder {
  private ActivityPainelGraficoEquipe target;

  private View view7f0a0107;

  private View view7f0a0101;

  private View view7f0a00fe;

  private View view7f0a00ff;

  private View view7f0a00b7;

  @UiThread
  public ActivityPainelGraficoEquipe_ViewBinding(ActivityPainelGraficoEquipe target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPainelGraficoEquipe_ViewBinding(final ActivityPainelGraficoEquipe target,
      View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.barChartSemanal = Utils.findRequiredViewAsType(source, R.id.barChart1, "field 'barChartSemanal'", BarChart.class);
    target.barChartMensal = Utils.findRequiredViewAsType(source, R.id.barChart2, "field 'barChartMensal'", BarChart.class);
    target.barChartRanking = Utils.findRequiredViewAsType(source, R.id.barChart3, "field 'barChartRanking'", HorizontalBarChart.class);
    target.lyt1 = Utils.findRequiredViewAsType(source, R.id.lyt1, "field 'lyt1'", LinearLayout.class);
    target.lyt2 = Utils.findRequiredViewAsType(source, R.id.lyt2, "field 'lyt2'", LinearLayout.class);
    target.progressBar1 = Utils.findRequiredViewAsType(source, R.id.progressBar1, "field 'progressBar1'", ProgressBar.class);
    target.lyt3 = Utils.findRequiredViewAsType(source, R.id.lyt3, "field 'lyt3'", LinearLayout.class);
    target.lyt4 = Utils.findRequiredViewAsType(source, R.id.lyt4, "field 'lyt4'", LinearLayout.class);
    target.progressBar2 = Utils.findRequiredViewAsType(source, R.id.progressBar2, "field 'progressBar2'", ProgressBar.class);
    target.lyt5 = Utils.findRequiredViewAsType(source, R.id.lyt5, "field 'lyt5'", LinearLayout.class);
    target.lyt6 = Utils.findRequiredViewAsType(source, R.id.lyt6, "field 'lyt6'", LinearLayout.class);
    target.progressBar3 = Utils.findRequiredViewAsType(source, R.id.progressBar3, "field 'progressBar3'", ProgressBar.class);
    target.scrollViewDashboard = Utils.findRequiredViewAsType(source, R.id.scrollViewDashboard, "field 'scrollViewDashboard'", ScrollView.class);
    target.lytGlobalLoading = Utils.findRequiredView(source, R.id.lytGlobalLoading, "field 'lytGlobalLoading'");
    target.tvGlobalLoadingTitle = Utils.findRequiredViewAsType(source, R.id.tvGlobalLoadingTitle, "field 'tvGlobalLoadingTitle'", TextView.class);
    target.tvTituloSemanal = Utils.findRequiredViewAsType(source, R.id.tvTituloSemanal, "field 'tvTituloSemanal'", TextView.class);
    target.tvKpiReal1 = Utils.findRequiredViewAsType(source, R.id.tvKpiReal1, "field 'tvKpiReal1'", TextView.class);
    target.tvKpiPerc1 = Utils.findRequiredViewAsType(source, R.id.tvKpiPerc1, "field 'tvKpiPerc1'", TextView.class);
    target.tvKpiMeta1 = Utils.findRequiredViewAsType(source, R.id.tvKpiMeta1, "field 'tvKpiMeta1'", TextView.class);
    target.pbKpi1 = Utils.findRequiredViewAsType(source, R.id.pbKpi1, "field 'pbKpi1'", ProgressBar.class);
    target.tvKpiReal2 = Utils.findRequiredViewAsType(source, R.id.tvKpiReal2, "field 'tvKpiReal2'", TextView.class);
    target.tvKpiPerc2 = Utils.findRequiredViewAsType(source, R.id.tvKpiPerc2, "field 'tvKpiPerc2'", TextView.class);
    target.tvKpiMeta2 = Utils.findRequiredViewAsType(source, R.id.tvKpiMeta2, "field 'tvKpiMeta2'", TextView.class);
    target.pbKpi2 = Utils.findRequiredViewAsType(source, R.id.pbKpi2, "field 'pbKpi2'", ProgressBar.class);
    target.tvKpiReal3 = Utils.findRequiredViewAsType(source, R.id.tvKpiReal3, "field 'tvKpiReal3'", TextView.class);
    target.tvKpiPerc3 = Utils.findRequiredViewAsType(source, R.id.tvKpiPerc3, "field 'tvKpiPerc3'", TextView.class);
    target.tvKpiMeta3 = Utils.findRequiredViewAsType(source, R.id.tvKpiMeta3, "field 'tvKpiMeta3'", TextView.class);
    target.pbKpi3 = Utils.findRequiredViewAsType(source, R.id.pbKpi3, "field 'pbKpi3'", ProgressBar.class);
    target.tvKpiTituloDevolucao = Utils.findRequiredViewAsType(source, R.id.tvKpiTituloDevolucao, "field 'tvKpiTituloDevolucao'", TextView.class);
    target.tvKpiRealDevolucao = Utils.findRequiredViewAsType(source, R.id.tvKpiRealDevolucao, "field 'tvKpiRealDevolucao'", TextView.class);
    target.tvKpiPercDevolucao = Utils.findRequiredViewAsType(source, R.id.tvKpiPercDevolucao, "field 'tvKpiPercDevolucao'", TextView.class);
    target.pbKpiDevolucao = Utils.findRequiredViewAsType(source, R.id.pbKpiDevolucao, "field 'pbKpiDevolucao'", ProgressBar.class);
    target.tvScrollSemanal = Utils.findRequiredViewAsType(source, R.id.tvScrollSemanal, "field 'tvScrollSemanal'", TextView.class);
    target.tvScrollMensal = Utils.findRequiredViewAsType(source, R.id.tvScrollMensal, "field 'tvScrollMensal'", TextView.class);
    target.tvScrollRanking = Utils.findRequiredViewAsType(source, R.id.tvScrollRanking, "field 'tvScrollRanking'", TextView.class);
    target.cardGrafico1 = Utils.findRequiredViewAsType(source, R.id.cardGrafico1, "field 'cardGrafico1'", CardView.class);
    target.cardGrafico2 = Utils.findRequiredViewAsType(source, R.id.cardGrafico2, "field 'cardGrafico2'", CardView.class);
    target.cardGrafico3 = Utils.findRequiredViewAsType(source, R.id.cardGrafico3, "field 'cardGrafico3'", CardView.class);
    target.tvDataPesquisa = Utils.findRequiredViewAsType(source, R.id.tvDataPesquisa, "field 'tvDataPesquisa'", TextView.class);
    target.tvMesAnoReferencia = Utils.findRequiredViewAsType(source, R.id.tvMesAnoReferencia, "field 'tvMesAnoReferencia'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnSelecionarMesAnoGlobal, "field 'btnSelecionarMesAnoGlobal' and method 'onSelecionarPeriodoClick'");
    target.btnSelecionarMesAnoGlobal = view;
    view7f0a0107 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelecionarPeriodoClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshSemanal, "method 'onRefreshClick'");
    view7f0a0101 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshMensal, "method 'onRefreshClick'");
    view7f0a00fe = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshRanking, "method 'onRefreshClick'");
    view7f0a00ff = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCalendarioSemanal, "method 'onRefreshClick'");
    view7f0a00b7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPainelGraficoEquipe target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.barChartSemanal = null;
    target.barChartMensal = null;
    target.barChartRanking = null;
    target.lyt1 = null;
    target.lyt2 = null;
    target.progressBar1 = null;
    target.lyt3 = null;
    target.lyt4 = null;
    target.progressBar2 = null;
    target.lyt5 = null;
    target.lyt6 = null;
    target.progressBar3 = null;
    target.scrollViewDashboard = null;
    target.lytGlobalLoading = null;
    target.tvGlobalLoadingTitle = null;
    target.tvTituloSemanal = null;
    target.tvKpiReal1 = null;
    target.tvKpiPerc1 = null;
    target.tvKpiMeta1 = null;
    target.pbKpi1 = null;
    target.tvKpiReal2 = null;
    target.tvKpiPerc2 = null;
    target.tvKpiMeta2 = null;
    target.pbKpi2 = null;
    target.tvKpiReal3 = null;
    target.tvKpiPerc3 = null;
    target.tvKpiMeta3 = null;
    target.pbKpi3 = null;
    target.tvKpiTituloDevolucao = null;
    target.tvKpiRealDevolucao = null;
    target.tvKpiPercDevolucao = null;
    target.pbKpiDevolucao = null;
    target.tvScrollSemanal = null;
    target.tvScrollMensal = null;
    target.tvScrollRanking = null;
    target.cardGrafico1 = null;
    target.cardGrafico2 = null;
    target.cardGrafico3 = null;
    target.tvDataPesquisa = null;
    target.tvMesAnoReferencia = null;
    target.btnSelecionarMesAnoGlobal = null;

    view7f0a0107.setOnClickListener(null);
    view7f0a0107 = null;
    view7f0a0101.setOnClickListener(null);
    view7f0a0101 = null;
    view7f0a00fe.setOnClickListener(null);
    view7f0a00fe = null;
    view7f0a00ff.setOnClickListener(null);
    view7f0a00ff = null;
    view7f0a00b7.setOnClickListener(null);
    view7f0a00b7 = null;
  }
}
