// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.github.mikephil.charting.charts.BarChart;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentExpansaoGrafico_ViewBinding implements Unbinder {
  private FragmentExpansaoGrafico target;

  @UiThread
  public FragmentExpansaoGrafico_ViewBinding(FragmentExpansaoGrafico target, View source) {
    this.target = target;

    target.barChart = Utils.findRequiredViewAsType(source, R.id.chartExpansao, "field 'barChart'", BarChart.class);
    target.tvTitulo = Utils.findRequiredViewAsType(source, R.id.tvTituloGraficoExpansao, "field 'tvTitulo'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentExpansaoGrafico target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.barChart = null;
    target.tvTitulo = null;
  }
}
