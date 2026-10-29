// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.navigation.NavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPainelGraficoMetas_ViewBinding implements Unbinder {
  private ActivityPainelGraficoMetas target;

  private View view7f0a010c;

  private View view7f0a00ba;

  private View view7f0a0107;

  private View view7f0a0109;

  private View view7f0a0112;

  @UiThread
  public ActivityPainelGraficoMetas_ViewBinding(ActivityPainelGraficoMetas target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPainelGraficoMetas_ViewBinding(final ActivityPainelGraficoMetas target,
      View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.barChart1 = Utils.findRequiredViewAsType(source, R.id.barChart1, "field 'barChart1'", BarChart.class);
    target.barChart2 = Utils.findRequiredViewAsType(source, R.id.barChart2, "field 'barChart2'", BarChart.class);
    target.barChart3 = Utils.findRequiredViewAsType(source, R.id.barChart3, "field 'barChart3'", BarChart.class);
    target.lyt1 = Utils.findRequiredViewAsType(source, R.id.lyt1, "field 'lyt1'", LinearLayout.class);
    target.lyt2 = Utils.findRequiredViewAsType(source, R.id.lyt2, "field 'lyt2'", LinearLayout.class);
    target.progressBar1 = Utils.findRequiredViewAsType(source, R.id.progressBar1, "field 'progressBar1'", ProgressBar.class);
    target.progressTitle1 = Utils.findRequiredViewAsType(source, R.id.progressTitle1, "field 'progressTitle1'", TextView.class);
    target.lyt3 = Utils.findRequiredViewAsType(source, R.id.lyt3, "field 'lyt3'", LinearLayout.class);
    target.lyt4 = Utils.findRequiredViewAsType(source, R.id.lyt4, "field 'lyt4'", LinearLayout.class);
    target.progressBar2 = Utils.findRequiredViewAsType(source, R.id.progressBar2, "field 'progressBar2'", ProgressBar.class);
    target.progressTitle2 = Utils.findRequiredViewAsType(source, R.id.progressTitle2, "field 'progressTitle2'", TextView.class);
    target.lyt5 = Utils.findRequiredViewAsType(source, R.id.lyt5, "field 'lyt5'", LinearLayout.class);
    target.lyt6 = Utils.findRequiredViewAsType(source, R.id.lyt6, "field 'lyt6'", LinearLayout.class);
    target.progressBar3 = Utils.findRequiredViewAsType(source, R.id.progressBar3, "field 'progressBar3'", ProgressBar.class);
    target.progressTitle3 = Utils.findRequiredViewAsType(source, R.id.progressTitle3, "field 'progressTitle3'", TextView.class);
    target.tvTituloSemanal = Utils.findRequiredViewAsType(source, R.id.tvTituloSemanal, "field 'tvTituloSemanal'", TextView.class);
    target.tvTituloGrupo = Utils.findRequiredViewAsType(source, R.id.tvTituloGrupo, "field 'tvTituloGrupo'", TextView.class);
    target.scrollViewDashboard = Utils.findRequiredViewAsType(source, R.id.scrollViewDashboard, "field 'scrollViewDashboard'", ScrollView.class);
    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.drawerLayout, "field 'drawerLayout'", DrawerLayout.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.navView, "field 'navigationView'", NavigationView.class);
    target.lytGlobalLoading = Utils.findRequiredView(source, R.id.lytGlobalLoading, "field 'lytGlobalLoading'");
    target.tvGlobalLoadingTitle = Utils.findRequiredViewAsType(source, R.id.tvGlobalLoadingTitle, "field 'tvGlobalLoadingTitle'", TextView.class);
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
    view = Utils.findRequiredView(source, R.id.btnRefreshSemanal, "field 'btnRefreshSemanal' and method 'onRefreshClick'");
    target.btnRefreshSemanal = Utils.castView(view, R.id.btnRefreshSemanal, "field 'btnRefreshSemanal'", ImageView.class);
    view7f0a010c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCalendarioSemanal, "field 'btnCalendarioSemanal' and method 'onRefreshClick'");
    target.btnCalendarioSemanal = Utils.castView(view, R.id.btnCalendarioSemanal, "field 'btnCalendarioSemanal'", ImageView.class);
    view7f0a00ba = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshGrupo, "field 'btnRefreshGrupo' and method 'onRefreshClick'");
    target.btnRefreshGrupo = Utils.castView(view, R.id.btnRefreshGrupo, "field 'btnRefreshGrupo'", ImageView.class);
    view7f0a0107 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshMensal, "field 'btnRefreshMensal' and method 'onRefreshClick'");
    target.btnRefreshMensal = Utils.castView(view, R.id.btnRefreshMensal, "field 'btnRefreshMensal'", ImageView.class);
    view7f0a0109 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    target.cardGrafico1 = Utils.findRequiredViewAsType(source, R.id.cardGrafico1, "field 'cardGrafico1'", CardView.class);
    target.cardGrafico2 = Utils.findRequiredViewAsType(source, R.id.cardGrafico2, "field 'cardGrafico2'", CardView.class);
    target.cardGrafico3 = Utils.findRequiredViewAsType(source, R.id.cardGrafico3, "field 'cardGrafico3'", CardView.class);
    target.tvDataPesquisa = Utils.findRequiredViewAsType(source, R.id.tvDataPesquisa, "field 'tvDataPesquisa'", TextView.class);
    target.tvMesAnoReferencia = Utils.findRequiredViewAsType(source, R.id.tvMesAnoReferencia, "field 'tvMesAnoReferencia'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnSelecionarMesAnoGlobal, "field 'btnSelecionarMesAnoGlobal' and method 'onSelecionarPeriodoClick'");
    target.btnSelecionarMesAnoGlobal = view;
    view7f0a0112 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelecionarPeriodoClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPainelGraficoMetas target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.barChart1 = null;
    target.barChart2 = null;
    target.barChart3 = null;
    target.lyt1 = null;
    target.lyt2 = null;
    target.progressBar1 = null;
    target.progressTitle1 = null;
    target.lyt3 = null;
    target.lyt4 = null;
    target.progressBar2 = null;
    target.progressTitle2 = null;
    target.lyt5 = null;
    target.lyt6 = null;
    target.progressBar3 = null;
    target.progressTitle3 = null;
    target.tvTituloSemanal = null;
    target.tvTituloGrupo = null;
    target.scrollViewDashboard = null;
    target.drawerLayout = null;
    target.navigationView = null;
    target.lytGlobalLoading = null;
    target.tvGlobalLoadingTitle = null;
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
    target.btnRefreshSemanal = null;
    target.btnCalendarioSemanal = null;
    target.btnRefreshGrupo = null;
    target.btnRefreshMensal = null;
    target.cardGrafico1 = null;
    target.cardGrafico2 = null;
    target.cardGrafico3 = null;
    target.tvDataPesquisa = null;
    target.tvMesAnoReferencia = null;
    target.btnSelecionarMesAnoGlobal = null;

    view7f0a010c.setOnClickListener(null);
    view7f0a010c = null;
    view7f0a00ba.setOnClickListener(null);
    view7f0a00ba = null;
    view7f0a0107.setOnClickListener(null);
    view7f0a0107 = null;
    view7f0a0109.setOnClickListener(null);
    view7f0a0109 = null;
    view7f0a0112.setOnClickListener(null);
    view7f0a0112 = null;
  }
}
