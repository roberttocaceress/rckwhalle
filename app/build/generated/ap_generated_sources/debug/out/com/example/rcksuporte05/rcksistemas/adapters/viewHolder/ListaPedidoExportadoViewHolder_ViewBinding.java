// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListaPedidoExportadoViewHolder_ViewBinding implements Unbinder {
  private ListaPedidoExportadoViewHolder target;

  @UiThread
  public ListaPedidoExportadoViewHolder_ViewBinding(ListaPedidoExportadoViewHolder target,
      View source) {
    this.target = target;

    target.edtRazaoSocial = Utils.findRequiredViewAsType(source, R.id.edtRazaoSocial, "field 'edtRazaoSocial'", TextInputEditText.class);
    target.edtNomeFantasia = Utils.findRequiredViewAsType(source, R.id.edtNomeFantasia, "field 'edtNomeFantasia'", TextInputEditText.class);
    target.edtNumeroPedido = Utils.findRequiredViewAsType(source, R.id.edtNumeroPedido, "field 'edtNumeroPedido'", TextInputEditText.class);
    target.edtDataEmissao = Utils.findRequiredViewAsType(source, R.id.edtDataEmissao, "field 'edtDataEmissao'", TextInputEditText.class);
    target.edtDataEntrega = Utils.findRequiredViewAsType(source, R.id.edtDataEntrega, "field 'edtDataEntrega'", TextInputEditText.class);
    target.edtValorProduto = Utils.findRequiredViewAsType(source, R.id.edtValorProduto, "field 'edtValorProduto'", TextInputEditText.class);
    target.edtValorDesconto = Utils.findRequiredViewAsType(source, R.id.edtValorDesconto, "field 'edtValorDesconto'", TextInputEditText.class);
    target.edtValorTotal = Utils.findRequiredViewAsType(source, R.id.edtValorTotal, "field 'edtValorTotal'", TextInputEditText.class);
    target.edtNomeVendedor = Utils.findRequiredViewAsType(source, R.id.edtNomeVendedor, "field 'edtNomeVendedor'", TextInputEditText.class);
    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListaPedidoExportadoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtRazaoSocial = null;
    target.edtNomeFantasia = null;
    target.edtNumeroPedido = null;
    target.edtDataEmissao = null;
    target.edtDataEntrega = null;
    target.edtValorProduto = null;
    target.edtValorDesconto = null;
    target.edtValorTotal = null;
    target.edtNomeVendedor = null;
    target.btnConfirmar = null;
  }
}
