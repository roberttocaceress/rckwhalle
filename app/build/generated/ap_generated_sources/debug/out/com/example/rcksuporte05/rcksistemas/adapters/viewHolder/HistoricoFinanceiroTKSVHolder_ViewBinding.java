// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HistoricoFinanceiroTKSVHolder_ViewBinding implements Unbinder {
  private HistoricoFinanceiroTKSVHolder target;

  @UiThread
  public HistoricoFinanceiroTKSVHolder_ViewBinding(HistoricoFinanceiroTKSVHolder target,
      View source) {
    this.target = target;

    target.txvNumeroDocumento = Utils.findRequiredViewAsType(source, R.id.txvNumeroDocumento, "field 'txvNumeroDocumento'", TextView.class);
    target.txvNumeroParcela = Utils.findRequiredViewAsType(source, R.id.txvNumeroParcela, "field 'txvNumeroParcela'", TextView.class);
    target.txvDataEmissao = Utils.findRequiredViewAsType(source, R.id.txvDataEmissao, "field 'txvDataEmissao'", TextView.class);
    target.txvDataVencimento = Utils.findRequiredViewAsType(source, R.id.txvDataVencimento, "field 'txvDataVencimento'", TextView.class);
    target.txvPontualidade = Utils.findRequiredViewAsType(source, R.id.txvPontualidade, "field 'txvPontualidade'", TextView.class);
    target.txvValorTotal = Utils.findRequiredViewAsType(source, R.id.txvValorTotal, "field 'txvValorTotal'", TextView.class);
    target.corx = Utils.findRequiredView(source, R.id.corx, "field 'corx'");
  }

  @Override
  @CallSuper
  public void unbind() {
    HistoricoFinanceiroTKSVHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txvNumeroDocumento = null;
    target.txvNumeroParcela = null;
    target.txvDataEmissao = null;
    target.txvDataVencimento = null;
    target.txvPontualidade = null;
    target.txvValorTotal = null;
    target.corx = null;
  }
}
