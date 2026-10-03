// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroCliente9_ViewBinding implements Unbinder {
  private CadastroCliente9 target;

  @UiThread
  public CadastroCliente9_ViewBinding(CadastroCliente9 target, View source) {
    this.target = target;

    target.btnSalvar = Utils.findRequiredViewAsType(source, R.id.btnSalvar, "field 'btnSalvar'", Button.class);
    target.edtobsFat = Utils.findRequiredViewAsType(source, R.id.edtobsFat, "field 'edtobsFat'", EditText.class);
    target.edtObsFinancas = Utils.findRequiredViewAsType(source, R.id.edtObsFinancas, "field 'edtObsFinancas'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroCliente9 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnSalvar = null;
    target.edtobsFat = null;
    target.edtObsFinancas = null;
  }
}
