// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FinanceiroViewHolder_ViewBinding implements Unbinder {
  private FinanceiroViewHolder target;

  @UiThread
  public FinanceiroViewHolder_ViewBinding(FinanceiroViewHolder target, View source) {
    this.target = target;

    target.finDocumento = Utils.findRequiredViewAsType(source, R.id.finDocumento, "field 'finDocumento'", TextView.class);
    target.finParcela = Utils.findRequiredViewAsType(source, R.id.finParcela, "field 'finParcela'", TextView.class);
    target.finEmissao = Utils.findRequiredViewAsType(source, R.id.finEmissao, "field 'finEmissao'", TextView.class);
    target.finVencimento = Utils.findRequiredViewAsType(source, R.id.finVencimento, "field 'finVencimento'", TextView.class);
    target.finPagamento = Utils.findRequiredViewAsType(source, R.id.finPagamento, "field 'finPagamento'", TextView.class);
    target.finEspecie = Utils.findRequiredViewAsType(source, R.id.finEspecie, "field 'finEspecie'", TextView.class);
    target.finValorDoc = Utils.findRequiredViewAsType(source, R.id.finValorDoc, "field 'finValorDoc'", TextView.class);
    target.finJuroDesconto = Utils.findRequiredViewAsType(source, R.id.finJuroDesconto, "field 'finJuroDesconto'", TextView.class);
    target.txvPagamento = Utils.findRequiredViewAsType(source, R.id.txvPagamento, "field 'txvPagamento'", TextView.class);
    target.finValorPago = Utils.findRequiredViewAsType(source, R.id.finValorPago, "field 'finValorPago'", TextView.class);
    target.finValorSaldo = Utils.findRequiredViewAsType(source, R.id.finValorSaldo, "field 'finValorSaldo'", TextView.class);
    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FinanceiroViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.finDocumento = null;
    target.finParcela = null;
    target.finEmissao = null;
    target.finVencimento = null;
    target.finPagamento = null;
    target.finEspecie = null;
    target.finValorDoc = null;
    target.finJuroDesconto = null;
    target.txvPagamento = null;
    target.finValorPago = null;
    target.finValorSaldo = null;
    target.itemView = null;
  }
}
