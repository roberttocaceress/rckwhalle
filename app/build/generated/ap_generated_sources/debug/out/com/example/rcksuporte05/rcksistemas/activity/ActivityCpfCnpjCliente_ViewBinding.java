// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class ActivityCpfCnpjCliente_ViewBinding implements Unbinder {
  private ActivityCpfCnpjCliente target;

  @UiThread
  public ActivityCpfCnpjCliente_ViewBinding(ActivityCpfCnpjCliente target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityCpfCnpjCliente_ViewBinding(ActivityCpfCnpjCliente target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.txtCpfCnpj = Utils.findRequiredViewAsType(source, R.id.txtCpfCnpj, "field 'txtCpfCnpj'", TextView.class);
    target.edtCpf = Utils.findRequiredViewAsType(source, R.id.edtCpf, "field 'edtCpf'", EditText.class);
    target.edtCnpj = Utils.findRequiredViewAsType(source, R.id.edtCnpj, "field 'edtCnpj'", EditText.class);
    target.edtCnpjL = Utils.findRequiredViewAsType(source, R.id.edtCnpjL, "field 'edtCnpjL'", EditText.class);
    target.btnVerificar = Utils.findRequiredViewAsType(source, R.id.btnVerificar, "field 'btnVerificar'", Button.class);
    target.chkCNPJ = Utils.findRequiredViewAsType(source, R.id.chkCNPJ, "field 'chkCNPJ'", CheckBox.class);
    target.chkCNPJL = Utils.findRequiredViewAsType(source, R.id.chkCNPJL, "field 'chkCNPJL'", CheckBox.class);
    target.chkPF = Utils.findRequiredViewAsType(source, R.id.chkCPF, "field 'chkPF'", CheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityCpfCnpjCliente target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtCpfCnpj = null;
    target.edtCpf = null;
    target.edtCnpj = null;
    target.edtCnpjL = null;
    target.btnVerificar = null;
    target.chkCNPJ = null;
    target.chkCNPJL = null;
    target.chkPF = null;
  }
}
