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

public class ParticipacaoFaturamentoViewHolder_ViewBinding implements Unbinder {
  private ParticipacaoFaturamentoViewHolder target;

  @UiThread
  public ParticipacaoFaturamentoViewHolder_ViewBinding(ParticipacaoFaturamentoViewHolder target,
      View source) {
    this.target = target;

    target.txvNome = Utils.findRequiredViewAsType(source, R.id.txvNome, "field 'txvNome'", TextView.class);
    target.txvValorBruto = Utils.findRequiredViewAsType(source, R.id.txvValorBruto, "field 'txvValorBruto'", TextView.class);
    target.txvValorLiquido = Utils.findRequiredViewAsType(source, R.id.txvValorLiquido, "field 'txvValorLiquido'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ParticipacaoFaturamentoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txvNome = null;
    target.txvValorBruto = null;
    target.txvValorLiquido = null;
  }
}
