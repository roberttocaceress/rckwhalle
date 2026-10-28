// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityAnaliseCredito_ViewBinding implements Unbinder {
  private ActivityAnaliseCredito target;

  private View view7f0a00fd;

  @UiThread
  public ActivityAnaliseCredito_ViewBinding(ActivityAnaliseCredito target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityAnaliseCredito_ViewBinding(final ActivityAnaliseCredito target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.txtNomeCliente = Utils.findRequiredViewAsType(source, R.id.txtNomeCliente, "field 'txtNomeCliente'", TextView.class);
    target.txtStatus = Utils.findRequiredViewAsType(source, R.id.txtStatus, "field 'txtStatus'", TextView.class);
    target.txtPendenciaFinanceira = Utils.findRequiredViewAsType(source, R.id.txtPendenciaFinanceira, "field 'txtPendenciaFinanceira'", TextView.class);
    target.txtLimiteCredito = Utils.findRequiredViewAsType(source, R.id.txtLimiteCredito, "field 'txtLimiteCredito'", TextView.class);
    target.txtValorPedido = Utils.findRequiredViewAsType(source, R.id.txtValorPedido, "field 'txtValorPedido'", TextView.class);
    target.txtLimiteUtilizado = Utils.findRequiredViewAsType(source, R.id.txtLimiteUtilizado, "field 'txtLimiteUtilizado'", TextView.class);
    target.txtSaldoRestante = Utils.findRequiredViewAsType(source, R.id.txtSaldoRestante, "field 'txtSaldoRestante'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnOk, "method 'onClick'");
    view7f0a00fd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityAnaliseCredito target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtNomeCliente = null;
    target.txtStatus = null;
    target.txtPendenciaFinanceira = null;
    target.txtLimiteCredito = null;
    target.txtValorPedido = null;
    target.txtLimiteUtilizado = null;
    target.txtSaldoRestante = null;

    view7f0a00fd.setOnClickListener(null);
    view7f0a00fd = null;
  }
}
