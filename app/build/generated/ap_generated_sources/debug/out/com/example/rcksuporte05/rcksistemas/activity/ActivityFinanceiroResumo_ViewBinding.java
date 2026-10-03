// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityFinanceiroResumo_ViewBinding implements Unbinder {
  private ActivityFinanceiroResumo target;

  @UiThread
  public ActivityFinanceiroResumo_ViewBinding(ActivityFinanceiroResumo target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityFinanceiroResumo_ViewBinding(ActivityFinanceiroResumo target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.idToolbarFinanceiro, "field 'toolbar'", Toolbar.class);
    target.txtVencida = Utils.findRequiredViewAsType(source, R.id.txtVencida, "field 'txtVencida'", TextView.class);
    target.txtAvencer = Utils.findRequiredViewAsType(source, R.id.txtAvencer, "field 'txtAvencer'", TextView.class);
    target.txtQuitada = Utils.findRequiredViewAsType(source, R.id.txtQuitada, "field 'txtQuitada'", TextView.class);
    target.lyVencida = Utils.findRequiredViewAsType(source, R.id.lyVencida, "field 'lyVencida'", LinearLayout.class);
    target.lyVencer = Utils.findRequiredViewAsType(source, R.id.lyVencer, "field 'lyVencer'", LinearLayout.class);
    target.lyQuitada = Utils.findRequiredViewAsType(source, R.id.lyQuitada, "field 'lyQuitada'", LinearLayout.class);
    target.txtLimiteCredito = Utils.findRequiredViewAsType(source, R.id.txtLimiteCredito, "field 'txtLimiteCredito'", TextView.class);
    target.lySincronia = Utils.findRequiredViewAsType(source, R.id.lySincronia, "field 'lySincronia'", LinearLayout.class);
    target.txtDataHoraSincroniza = Utils.findRequiredViewAsType(source, R.id.txtDataHoraSincroniza, "field 'txtDataHoraSincroniza'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityFinanceiroResumo target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtVencida = null;
    target.txtAvencer = null;
    target.txtQuitada = null;
    target.lyVencida = null;
    target.lyVencer = null;
    target.lyQuitada = null;
    target.txtLimiteCredito = null;
    target.lySincronia = null;
    target.txtDataHoraSincroniza = null;
  }
}
