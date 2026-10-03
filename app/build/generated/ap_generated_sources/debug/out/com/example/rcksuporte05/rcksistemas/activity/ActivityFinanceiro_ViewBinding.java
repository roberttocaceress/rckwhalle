// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityFinanceiro_ViewBinding implements Unbinder {
  private ActivityFinanceiro target;

  @UiThread
  public ActivityFinanceiro_ViewBinding(ActivityFinanceiro target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityFinanceiro_ViewBinding(ActivityFinanceiro target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tbFinanceiro, "field 'toolbar'", Toolbar.class);
    target.layout_vencido = Utils.findRequiredViewAsType(source, R.id.layout_vencido, "field 'layout_vencido'", LinearLayout.class);
    target.layout_vencer = Utils.findRequiredViewAsType(source, R.id.layout_vencer, "field 'layout_vencer'", LinearLayout.class);
    target.layout_quitado = Utils.findRequiredViewAsType(source, R.id.layout_quitado, "field 'layout_quitado'", LinearLayout.class);
    target.recycle_vencido = Utils.findRequiredViewAsType(source, R.id.listaVencido, "field 'recycle_vencido'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityFinanceiro target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.layout_vencido = null;
    target.layout_vencer = null;
    target.layout_quitado = null;
    target.recycle_vencido = null;
  }
}
