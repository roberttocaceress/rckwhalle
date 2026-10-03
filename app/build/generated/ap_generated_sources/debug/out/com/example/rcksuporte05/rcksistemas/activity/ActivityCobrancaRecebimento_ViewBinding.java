// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityCobrancaRecebimento_ViewBinding implements Unbinder {
  private ActivityCobrancaRecebimento target;

  @UiThread
  public ActivityCobrancaRecebimento_ViewBinding(ActivityCobrancaRecebimento target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityCobrancaRecebimento_ViewBinding(ActivityCobrancaRecebimento target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.txvRazaoSocial = Utils.findRequiredViewAsType(source, R.id.txvRazaoSocial, "field 'txvRazaoSocial'", TextView.class);
    target.edtTotalPendente = Utils.findRequiredViewAsType(source, R.id.edtTotalPendente, "field 'edtTotalPendente'", EditText.class);
    target.edtTotalRecebido = Utils.findRequiredViewAsType(source, R.id.edtTotalRecebido, "field 'edtTotalRecebido'", EditText.class);
    target.edtDataReagendamento = Utils.findRequiredViewAsType(source, R.id.edtDataReagendamento, "field 'edtDataReagendamento'", EditText.class);
    target.edtObservacao = Utils.findRequiredViewAsType(source, R.id.edtObservacao, "field 'edtObservacao'", EditText.class);
    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
    target.btnCancelar = Utils.findRequiredViewAsType(source, R.id.btnCancelar, "field 'btnCancelar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityCobrancaRecebimento target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txvRazaoSocial = null;
    target.edtTotalPendente = null;
    target.edtTotalRecebido = null;
    target.edtDataReagendamento = null;
    target.edtObservacao = null;
    target.btnConfirmar = null;
    target.btnCancelar = null;
  }
}
