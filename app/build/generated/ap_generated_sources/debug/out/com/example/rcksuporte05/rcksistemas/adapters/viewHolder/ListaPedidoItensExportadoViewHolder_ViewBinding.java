// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListaPedidoItensExportadoViewHolder_ViewBinding implements Unbinder {
  private ListaPedidoItensExportadoViewHolder target;

  @UiThread
  public ListaPedidoItensExportadoViewHolder_ViewBinding(ListaPedidoItensExportadoViewHolder target,
      View source) {
    this.target = target;

    target.edtNomeProduto = Utils.findRequiredViewAsType(source, R.id.edtNomeProduto, "field 'edtNomeProduto'", TextInputEditText.class);
    target.edtQtdeProduto = Utils.findRequiredViewAsType(source, R.id.edtQtdeProduto, "field 'edtQtdeProduto'", TextInputEditText.class);
    target.edtValorProduto = Utils.findRequiredViewAsType(source, R.id.edtValorProduto, "field 'edtValorProduto'", TextInputEditText.class);
    target.edtDescontoProduto = Utils.findRequiredViewAsType(source, R.id.edtDescontoProduto, "field 'edtDescontoProduto'", TextInputEditText.class);
    target.edtTotalProduto = Utils.findRequiredViewAsType(source, R.id.edtTotalProduto, "field 'edtTotalProduto'", TextInputEditText.class);
    target.edtCodigoProduto = Utils.findRequiredViewAsType(source, R.id.edtCodigoProduto, "field 'edtCodigoProduto'", TextInputEditText.class);
    target.edtEANProduto = Utils.findRequiredViewAsType(source, R.id.edtEANProduto, "field 'edtEANProduto'", TextInputEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListaPedidoItensExportadoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtNomeProduto = null;
    target.edtQtdeProduto = null;
    target.edtValorProduto = null;
    target.edtDescontoProduto = null;
    target.edtTotalProduto = null;
    target.edtCodigoProduto = null;
    target.edtEANProduto = null;
  }
}
