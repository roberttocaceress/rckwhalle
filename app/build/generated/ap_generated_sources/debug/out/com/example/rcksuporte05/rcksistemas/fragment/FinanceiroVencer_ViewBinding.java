// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FinanceiroVencer_ViewBinding implements Unbinder {
  private FinanceiroVencer target;

  @UiThread
  public FinanceiroVencer_ViewBinding(FinanceiroVencer target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaFinanceiro, "field 'recyclerView'", RecyclerView.class);
    target.edtTotalTitulos = Utils.findRequiredViewAsType(source, R.id.edtTotalTitulos, "field 'edtTotalTitulos'", TextView.class);
    target.edtTotalJuros = Utils.findRequiredViewAsType(source, R.id.edtTotalJuros, "field 'edtTotalJuros'", TextView.class);
    target.edtTotalPago = Utils.findRequiredViewAsType(source, R.id.edtTotalPago, "field 'edtTotalPago'", TextView.class);
    target.edtTotalSaldo = Utils.findRequiredViewAsType(source, R.id.edtTotalSaldo, "field 'edtTotalSaldo'", TextView.class);
    target.layout_progress = Utils.findRequiredViewAsType(source, R.id.layout_progress, "field 'layout_progress'", LinearLayout.class);
    target.progress_ajuda = Utils.findRequiredViewAsType(source, R.id.progress_ajuda, "field 'progress_ajuda'", ProgressBar.class);
    target.shimmerLayout = Utils.findRequiredViewAsType(source, R.id.shimmerLayout, "field 'shimmerLayout'", ShimmerFrameLayout.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
    target.floatingActionButton = Utils.findRequiredViewAsType(source, R.id.floatingActionButton, "field 'floatingActionButton'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FinanceiroVencer target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.edtTotalTitulos = null;
    target.edtTotalJuros = null;
    target.edtTotalPago = null;
    target.edtTotalSaldo = null;
    target.layout_progress = null;
    target.progress_ajuda = null;
    target.shimmerLayout = null;
    target.progress_title = null;
    target.floatingActionButton = null;
  }
}
