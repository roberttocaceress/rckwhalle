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
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.navigation.NavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPainelGraficoFaturamento_ViewBinding implements Unbinder {
  private ActivityPainelGraficoFaturamento target;

  private View view7f0a0107;

  private View view7f0a0104;

  private View view7f0a0102;

  private View view7f0a00af;

  private View view7f0a0105;

  private View view7f0a00ae;

  @UiThread
  public ActivityPainelGraficoFaturamento_ViewBinding(ActivityPainelGraficoFaturamento target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPainelGraficoFaturamento_ViewBinding(final ActivityPainelGraficoFaturamento target,
      View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.barChart1 = Utils.findRequiredViewAsType(source, R.id.barChart1, "field 'barChart1'", BarChart.class);
    target.barChart3 = Utils.findRequiredViewAsType(source, R.id.barChart3, "field 'barChart3'", BarChart.class);
    target.pieChart = Utils.findRequiredViewAsType(source, R.id.pieChart, "field 'pieChart'", PieChart.class);
    target.horChart = Utils.findRequiredViewAsType(source, R.id.horChart, "field 'horChart'", HorizontalBarChart.class);
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
    target.lyt10 = Utils.findRequiredViewAsType(source, R.id.lyt10, "field 'lyt10'", LinearLayout.class);
    target.lyt11 = Utils.findRequiredViewAsType(source, R.id.lyt11, "field 'lyt11'", LinearLayout.class);
    target.progressBar10 = Utils.findRequiredViewAsType(source, R.id.progressBar10, "field 'progressBar10'", ProgressBar.class);
    target.progressTitle10 = Utils.findRequiredViewAsType(source, R.id.progressTitle10, "field 'progressTitle10'", TextView.class);
    target.vtotalVendasPeriodo = Utils.findRequiredViewAsType(source, R.id.vtotalVendasPeriodo, "field 'vtotalVendasPeriodo'", TextView.class);
    target.vmedioVendasPeriodo = Utils.findRequiredViewAsType(source, R.id.vmedioVendasPeriodo, "field 'vmedioVendasPeriodo'", TextView.class);
    target.qtotalVendasPeriodo = Utils.findRequiredViewAsType(source, R.id.qtotalVendasPeriodo, "field 'qtotalVendasPeriodo'", TextView.class);
    target.vtotalDevolucaoPeriodo = Utils.findRequiredViewAsType(source, R.id.vtotalDevolucaoPeriodo, "field 'vtotalDevolucaoPeriodo'", TextView.class);
    target.lblDevolucoesPeriodo = Utils.findRequiredViewAsType(source, R.id.lblDevolucoesPeriodo, "field 'lblDevolucoesPeriodo'", TextView.class);
    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.drawerLayout, "field 'drawerLayout'", DrawerLayout.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.navView, "field 'navigationView'", NavigationView.class);
    target.lytGlobalLoading = Utils.findRequiredView(source, R.id.lytGlobalLoading, "field 'lytGlobalLoading'");
    target.tvGlobalLoadingTitle = Utils.findRequiredViewAsType(source, R.id.tvGlobalLoadingTitle, "field 'tvGlobalLoadingTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnRefreshSemanal, "field 'btnRefreshSemanal' and method 'onRefreshClick'");
    target.btnRefreshSemanal = Utils.castView(view, R.id.btnRefreshSemanal, "field 'btnRefreshSemanal'", ImageView.class);
    view7f0a0107 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshMensal, "field 'btnRefreshMensal' and method 'onRefreshClick'");
    target.btnRefreshMensal = Utils.castView(view, R.id.btnRefreshMensal, "field 'btnRefreshMensal'", ImageView.class);
    view7f0a0104 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshGrupo, "field 'btnRefreshGrupo' and method 'onRefreshClick'");
    target.btnRefreshGrupo = Utils.castView(view, R.id.btnRefreshGrupo, "field 'btnRefreshGrupo'", ImageView.class);
    view7f0a0102 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAnaliseRankingGrupo, "field 'btnAnaliseRankingGrupo' and method 'onRefreshClick'");
    target.btnAnaliseRankingGrupo = Utils.castView(view, R.id.btnAnaliseRankingGrupo, "field 'btnAnaliseRankingGrupo'", ImageView.class);
    view7f0a00af = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshRanking, "field 'btnRefreshRanking' and method 'onRefreshClick'");
    target.btnRefreshRanking = Utils.castView(view, R.id.btnRefreshRanking, "field 'btnRefreshRanking'", ImageView.class);
    view7f0a0105 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAnaliseRanking, "field 'btnAnaliseRanking' and method 'onRefreshClick'");
    target.btnAnaliseRanking = Utils.castView(view, R.id.btnAnaliseRanking, "field 'btnAnaliseRanking'", ImageView.class);
    view7f0a00ae = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRefreshClick(p0);
      }
    });
    target.cardGrafico1 = Utils.findRequiredViewAsType(source, R.id.cardGrafico1, "field 'cardGrafico1'", CardView.class);
    target.cardGrafico2 = Utils.findRequiredViewAsType(source, R.id.cardGrafico2, "field 'cardGrafico2'", CardView.class);
    target.cardGrafico3 = Utils.findRequiredViewAsType(source, R.id.cardGrafico3, "field 'cardGrafico3'", CardView.class);
    target.cardGrafico4 = Utils.findRequiredViewAsType(source, R.id.cardGrafico4, "field 'cardGrafico4'", CardView.class);
    target.tvDataPesquisa = Utils.findRequiredViewAsType(source, R.id.tvDataPesquisa, "field 'tvDataPesquisa'", TextView.class);
    target.scrollViewFaturamento = Utils.findRequiredViewAsType(source, R.id.scrollViewFaturamento, "field 'scrollViewFaturamento'", ScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPainelGraficoFaturamento target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.barChart1 = null;
    target.barChart3 = null;
    target.pieChart = null;
    target.horChart = null;
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
    target.lyt10 = null;
    target.lyt11 = null;
    target.progressBar10 = null;
    target.progressTitle10 = null;
    target.vtotalVendasPeriodo = null;
    target.vmedioVendasPeriodo = null;
    target.qtotalVendasPeriodo = null;
    target.vtotalDevolucaoPeriodo = null;
    target.lblDevolucoesPeriodo = null;
    target.drawerLayout = null;
    target.navigationView = null;
    target.lytGlobalLoading = null;
    target.tvGlobalLoadingTitle = null;
    target.btnRefreshSemanal = null;
    target.btnRefreshMensal = null;
    target.btnRefreshGrupo = null;
    target.btnAnaliseRankingGrupo = null;
    target.btnRefreshRanking = null;
    target.btnAnaliseRanking = null;
    target.cardGrafico1 = null;
    target.cardGrafico2 = null;
    target.cardGrafico3 = null;
    target.cardGrafico4 = null;
    target.tvDataPesquisa = null;
    target.scrollViewFaturamento = null;

    view7f0a0107.setOnClickListener(null);
    view7f0a0107 = null;
    view7f0a0104.setOnClickListener(null);
    view7f0a0104 = null;
    view7f0a0102.setOnClickListener(null);
    view7f0a0102 = null;
    view7f0a00af.setOnClickListener(null);
    view7f0a00af = null;
    view7f0a0105.setOnClickListener(null);
    view7f0a0105 = null;
    view7f0a00ae.setOnClickListener(null);
    view7f0a00ae = null;
  }
}
