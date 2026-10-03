// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Pedido2_ViewBinding implements Unbinder {
  private Pedido2 target;

  @UiThread
  public Pedido2_ViewBinding(Pedido2 target, View source) {
    this.target = target;

    target.spPagamento = Utils.findRequiredViewAsType(source, R.id.spPagamento, "field 'spPagamento'", Spinner.class);
    target.spOperacao = Utils.findRequiredViewAsType(source, R.id.spOperacao, "field 'spOperacao'", Spinner.class);
    target.lytMoeda = Utils.findRequiredViewAsType(source, R.id.lytMoeda, "field 'lytMoeda'", LinearLayout.class);
    target.spMoeda = Utils.findRequiredViewAsType(source, R.id.spMoeda, "field 'spMoeda'", Spinner.class);
    target.edtDataEmissao = Utils.findRequiredViewAsType(source, R.id.txtDataEmissao, "field 'edtDataEmissao'", TextView.class);
    target.txtDataEmissao = Utils.findRequiredViewAsType(source, R.id.txtDaDataDeEmissao, "field 'txtDataEmissao'", TextView.class);
    target.txtDataEntrega = Utils.findRequiredViewAsType(source, R.id.txtDataEntrega, "field 'txtDataEntrega'", TextView.class);
    target.edtDataEntrega = Utils.findRequiredViewAsType(source, R.id.edtDataEntrega, "field 'edtDataEntrega'", EditText.class);
    target.edtValorRecibo = Utils.findRequiredViewAsType(source, R.id.edtValorRecibo, "field 'edtValorRecibo'", EditText.class);
    target.edtSaldoDevedor = Utils.findRequiredViewAsType(source, R.id.edtSaldoDevedor, "field 'edtSaldoDevedor'", EditText.class);
    target.edtObservacao = Utils.findRequiredViewAsType(source, R.id.edtObservacao, "field 'edtObservacao'", EditText.class);
    target.btnSalvarPedido = Utils.findRequiredViewAsType(source, R.id.btnSalvarPedido, "field 'btnSalvarPedido'", Button.class);
    target.btnPedidoAnexo = Utils.findRequiredViewAsType(source, R.id.btnPedidoAnexo, "field 'btnPedidoAnexo'", Button.class);
    target.lyPrazoPagamento = Utils.findRequiredViewAsType(source, R.id.lyPrazoPagamento, "field 'lyPrazoPagamento'", LinearLayout.class);
    target.lyOperacao = Utils.findRequiredViewAsType(source, R.id.lyOperacao, "field 'lyOperacao'", LinearLayout.class);
    target.lySpinner = Utils.findRequiredViewAsType(source, R.id.lySpinner, "field 'lySpinner'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Pedido2 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spPagamento = null;
    target.spOperacao = null;
    target.lytMoeda = null;
    target.spMoeda = null;
    target.edtDataEmissao = null;
    target.txtDataEmissao = null;
    target.txtDataEntrega = null;
    target.edtDataEntrega = null;
    target.edtValorRecibo = null;
    target.edtSaldoDevedor = null;
    target.edtObservacao = null;
    target.btnSalvarPedido = null;
    target.btnPedidoAnexo = null;
    target.lyPrazoPagamento = null;
    target.lyOperacao = null;
    target.lySpinner = null;
  }
}
