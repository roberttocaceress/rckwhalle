// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CampanhaClientes_ViewBinding implements Unbinder {
  private CampanhaClientes target;

  @UiThread
  public CampanhaClientes_ViewBinding(CampanhaClientes target, View source) {
    this.target = target;

    target.txtDataInicio = Utils.findRequiredViewAsType(source, R.id.txtDataInicio, "field 'txtDataInicio'", TextView.class);
    target.txtDataVencimento = Utils.findRequiredViewAsType(source, R.id.txtDataVencimento, "field 'txtDataVencimento'", TextView.class);
    target.edtDescCampanha = Utils.findRequiredViewAsType(source, R.id.edtDescCampanha, "field 'edtDescCampanha'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CampanhaClientes target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtDataInicio = null;
    target.txtDataVencimento = null;
    target.edtDescCampanha = null;
  }
}
