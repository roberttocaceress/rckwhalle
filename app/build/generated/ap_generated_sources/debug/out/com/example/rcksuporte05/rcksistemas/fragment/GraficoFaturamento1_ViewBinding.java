// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.github.mikephil.charting.charts.BarChart;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GraficoFaturamento1_ViewBinding implements Unbinder {
  private GraficoFaturamento1 target;

  @UiThread
  public GraficoFaturamento1_ViewBinding(GraficoFaturamento1 target, View source) {
    this.target = target;

    target.barChart = Utils.findRequiredViewAsType(source, R.id.barChart, "field 'barChart'", BarChart.class);
    target.lyt1 = Utils.findRequiredViewAsType(source, R.id.lyt1, "field 'lyt1'", LinearLayout.class);
    target.lyt2 = Utils.findRequiredViewAsType(source, R.id.lyt2, "field 'lyt2'", LinearLayout.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.progressTitle = Utils.findRequiredViewAsType(source, R.id.progressTitle, "field 'progressTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GraficoFaturamento1 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.barChart = null;
    target.lyt1 = null;
    target.lyt2 = null;
    target.progressBar = null;
    target.progressTitle = null;
  }
}
