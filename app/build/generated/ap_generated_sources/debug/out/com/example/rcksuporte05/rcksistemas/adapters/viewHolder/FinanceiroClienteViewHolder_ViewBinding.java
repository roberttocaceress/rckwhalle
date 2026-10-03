// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FinanceiroClienteViewHolder_ViewBinding implements Unbinder {
  private FinanceiroClienteViewHolder target;

  @UiThread
  public FinanceiroClienteViewHolder_ViewBinding(FinanceiroClienteViewHolder target, View source) {
    this.target = target;

    target.inputNumeroDocumento = Utils.findRequiredViewAsType(source, R.id.inputNumeroDocumento, "field 'inputNumeroDocumento'", TextInputLayout.class);
    target.numeroDocumento = Utils.findRequiredViewAsType(source, R.id.numeroDocumento, "field 'numeroDocumento'", TextView.class);
    target.inputNumeroParcela = Utils.findRequiredViewAsType(source, R.id.inputNumeroParcela, "field 'inputNumeroParcela'", TextInputLayout.class);
    target.numeroParcela = Utils.findRequiredViewAsType(source, R.id.numeroParcela, "field 'numeroParcela'", TextView.class);
    target.inputDataEmissao = Utils.findRequiredViewAsType(source, R.id.inputDataEmissao, "field 'inputDataEmissao'", TextInputLayout.class);
    target.dataEmissao = Utils.findRequiredViewAsType(source, R.id.dataEmissao, "field 'dataEmissao'", TextView.class);
    target.inputDataVencimento = Utils.findRequiredViewAsType(source, R.id.inputDataVencimento, "field 'inputDataVencimento'", TextInputLayout.class);
    target.dataVencimento = Utils.findRequiredViewAsType(source, R.id.dataVencimento, "field 'dataVencimento'", TextView.class);
    target.inputDataBaixa = Utils.findRequiredViewAsType(source, R.id.inputDataBaixa, "field 'inputDataBaixa'", TextInputLayout.class);
    target.dataBaixa = Utils.findRequiredViewAsType(source, R.id.dataBaixa, "field 'dataBaixa'", TextView.class);
    target.inputValorTotal = Utils.findRequiredViewAsType(source, R.id.inputValorTotal, "field 'inputValorTotal'", TextInputLayout.class);
    target.valorTotal = Utils.findRequiredViewAsType(source, R.id.valorTotal, "field 'valorTotal'", TextView.class);
    target.valorJuros = Utils.findRequiredViewAsType(source, R.id.valorJuros, "field 'valorJuros'", TextView.class);
    target.valorPago = Utils.findRequiredViewAsType(source, R.id.valorPago, "field 'valorPago'", TextView.class);
    target.valorSaldo = Utils.findRequiredViewAsType(source, R.id.valorSaldo, "field 'valorSaldo'", TextView.class);
    target.especieDocumento = Utils.findRequiredViewAsType(source, R.id.especieDocumento, "field 'especieDocumento'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FinanceiroClienteViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.inputNumeroDocumento = null;
    target.numeroDocumento = null;
    target.inputNumeroParcela = null;
    target.numeroParcela = null;
    target.inputDataEmissao = null;
    target.dataEmissao = null;
    target.inputDataVencimento = null;
    target.dataVencimento = null;
    target.inputDataBaixa = null;
    target.dataBaixa = null;
    target.inputValorTotal = null;
    target.valorTotal = null;
    target.valorJuros = null;
    target.valorPago = null;
    target.valorSaldo = null;
    target.especieDocumento = null;
  }
}
