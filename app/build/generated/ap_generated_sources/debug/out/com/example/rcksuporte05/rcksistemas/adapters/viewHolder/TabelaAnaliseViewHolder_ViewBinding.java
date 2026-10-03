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

public class TabelaAnaliseViewHolder_ViewBinding implements Unbinder {
  private TabelaAnaliseViewHolder target;

  @UiThread
  public TabelaAnaliseViewHolder_ViewBinding(TabelaAnaliseViewHolder target, View source) {
    this.target = target;

    target.txtIdPedido = Utils.findRequiredViewAsType(source, R.id.edtNumeroPedido, "field 'txtIdPedido'", TextView.class);
    target.txtNomeCliente = Utils.findRequiredViewAsType(source, R.id.txtNomeCliente, "field 'txtNomeCliente'", TextView.class);
    target.txtFantasiaCliente = Utils.findRequiredViewAsType(source, R.id.txtFantasiaCliente, "field 'txtFantasiaCliente'", TextView.class);
    target.txtDataEmissaoPedido = Utils.findRequiredViewAsType(source, R.id.txtDataEmissaoPedido, "field 'txtDataEmissaoPedido'", TextView.class);
    target.txtDataEntrega = Utils.findRequiredViewAsType(source, R.id.txtDataEntrega, "field 'txtDataEntrega'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TabelaAnaliseViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtIdPedido = null;
    target.txtNomeCliente = null;
    target.txtFantasiaCliente = null;
    target.txtDataEmissaoPedido = null;
    target.txtDataEntrega = null;
  }
}
