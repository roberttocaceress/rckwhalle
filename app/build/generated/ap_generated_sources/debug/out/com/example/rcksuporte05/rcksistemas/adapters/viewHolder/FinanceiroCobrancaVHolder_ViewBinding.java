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

public class FinanceiroCobrancaVHolder_ViewBinding implements Unbinder {
  private FinanceiroCobrancaVHolder target;

  @UiThread
  public FinanceiroCobrancaVHolder_ViewBinding(FinanceiroCobrancaVHolder target, View source) {
    this.target = target;

    target.inputNomeFavorecido = Utils.findRequiredViewAsType(source, R.id.inputNomeFavorecido, "field 'inputNomeFavorecido'", TextInputLayout.class);
    target.nomeFavorecido = Utils.findRequiredViewAsType(source, R.id.nomeFavorecido, "field 'nomeFavorecido'", TextView.class);
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
    target.corx = Utils.findRequiredView(source, R.id.corx, "field 'corx'");
    target.inputValorTotal = Utils.findRequiredViewAsType(source, R.id.inputValorTotal, "field 'inputValorTotal'", TextInputLayout.class);
    target.valorTotal = Utils.findRequiredViewAsType(source, R.id.valorTotal, "field 'valorTotal'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FinanceiroCobrancaVHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.inputNomeFavorecido = null;
    target.nomeFavorecido = null;
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
    target.corx = null;
    target.inputValorTotal = null;
    target.valorTotal = null;
  }
}
