// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.google.android.material.navigation.NavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPainelFinanceiro_ViewBinding implements Unbinder {
  private ActivityPainelFinanceiro target;

  private View view7f0a010f;

  private View view7f0a0101;

  private View view7f0a0106;

  private View view7f0a0103;

  private View view7f0a00ac;

  private View view7f0a00b0;

  private View view7f0a00ad;

  @UiThread
  public ActivityPainelFinanceiro_ViewBinding(ActivityPainelFinanceiro target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPainelFinanceiro_ViewBinding(final ActivityPainelFinanceiro target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.tvDataPesquisa = Utils.findRequiredViewAsType(source, R.id.tvDataPesquisa, "field 'tvDataPesquisa'", TextView.class);
    target.tvKpiVencidos = Utils.findRequiredViewAsType(source, R.id.tvKpiVencidos, "field 'tvKpiVencidos'", TextView.class);
    target.tvKpiAVencer = Utils.findRequiredViewAsType(source, R.id.tvKpiAVencer, "field 'tvKpiAVencer'", TextView.class);
    target.tvKpiRecebidos = Utils.findRequiredViewAsType(source, R.id.tvKpiRecebidos, "field 'tvKpiRecebidos'", TextView.class);
    target.tvKpiTotalCarteira = Utils.findRequiredViewAsType(source, R.id.tvKpiTotalCarteira, "field 'tvKpiTotalCarteira'", TextView.class);
    target.tvPeriodoReferencia = Utils.findRequiredViewAsType(source, R.id.tvPeriodoReferencia, "field 'tvPeriodoReferencia'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnSelecionarPeriodoFinanceiro, "field 'btnSelecionarPeriodoFinanceiro' and method 'onSelecionarPeriodoClick'");
    target.btnSelecionarPeriodoFinanceiro = view;
    view7f0a010f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelecionarPeriodoClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshFluxo, "field 'btnRefreshFluxo' and method 'onIconClick'");
    target.btnRefreshFluxo = Utils.castView(view, R.id.btnRefreshFluxo, "field 'btnRefreshFluxo'", ImageView.class);
    view7f0a0101 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onIconClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshReceber, "field 'btnRefreshReceber' and method 'onIconClick'");
    target.btnRefreshReceber = Utils.castView(view, R.id.btnRefreshReceber, "field 'btnRefreshReceber'", ImageView.class);
    view7f0a0106 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onIconClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRefreshInad, "field 'btnRefreshInad' and method 'onIconClick'");
    target.btnRefreshInad = Utils.castView(view, R.id.btnRefreshInad, "field 'btnRefreshInad'", ImageView.class);
    view7f0a0103 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onIconClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAnaliseFluxo, "field 'btnAnaliseFluxo' and method 'onIconClick'");
    target.btnAnaliseFluxo = Utils.castView(view, R.id.btnAnaliseFluxo, "field 'btnAnaliseFluxo'", ImageView.class);
    view7f0a00ac = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onIconClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAnaliseReceber, "field 'btnAnaliseReceber' and method 'onIconClick'");
    target.btnAnaliseReceber = Utils.castView(view, R.id.btnAnaliseReceber, "field 'btnAnaliseReceber'", ImageView.class);
    view7f0a00b0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onIconClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAnaliseInad, "field 'btnAnaliseInad' and method 'onIconClick'");
    target.btnAnaliseInad = Utils.castView(view, R.id.btnAnaliseInad, "field 'btnAnaliseInad'", ImageView.class);
    view7f0a00ad = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onIconClick(p0);
      }
    });
    target.chartFluxo = Utils.findRequiredViewAsType(source, R.id.chartFluxo, "field 'chartFluxo'", BarChart.class);
    target.chartReceber = Utils.findRequiredViewAsType(source, R.id.chartReceber, "field 'chartReceber'", BarChart.class);
    target.chartInadimplentes = Utils.findRequiredViewAsType(source, R.id.chartInadimplentes, "field 'chartInadimplentes'", HorizontalBarChart.class);
    target.scrollViewDashboard = Utils.findRequiredView(source, R.id.scrollViewDashboard, "field 'scrollViewDashboard'");
    target.lytGlobalLoading = Utils.findRequiredView(source, R.id.lytGlobalLoading, "field 'lytGlobalLoading'");
    target.tvGlobalLoadingTitle = Utils.findRequiredViewAsType(source, R.id.tvGlobalLoadingTitle, "field 'tvGlobalLoadingTitle'", TextView.class);
    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.drawerLayout, "field 'drawerLayout'", DrawerLayout.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.navView, "field 'navigationView'", NavigationView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPainelFinanceiro target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tvDataPesquisa = null;
    target.tvKpiVencidos = null;
    target.tvKpiAVencer = null;
    target.tvKpiRecebidos = null;
    target.tvKpiTotalCarteira = null;
    target.tvPeriodoReferencia = null;
    target.btnSelecionarPeriodoFinanceiro = null;
    target.btnRefreshFluxo = null;
    target.btnRefreshReceber = null;
    target.btnRefreshInad = null;
    target.btnAnaliseFluxo = null;
    target.btnAnaliseReceber = null;
    target.btnAnaliseInad = null;
    target.chartFluxo = null;
    target.chartReceber = null;
    target.chartInadimplentes = null;
    target.scrollViewDashboard = null;
    target.lytGlobalLoading = null;
    target.tvGlobalLoadingTitle = null;
    target.drawerLayout = null;
    target.navigationView = null;

    view7f0a010f.setOnClickListener(null);
    view7f0a010f = null;
    view7f0a0101.setOnClickListener(null);
    view7f0a0101 = null;
    view7f0a0106.setOnClickListener(null);
    view7f0a0106 = null;
    view7f0a0103.setOnClickListener(null);
    view7f0a0103 = null;
    view7f0a00ac.setOnClickListener(null);
    view7f0a00ac = null;
    view7f0a00b0.setOnClickListener(null);
    view7f0a00b0 = null;
    view7f0a00ad.setOnClickListener(null);
    view7f0a00ad = null;
  }
}
