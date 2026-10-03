// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroCliente7_ViewBinding implements Unbinder {
  private CadastroCliente7 target;

  @UiThread
  public CadastroCliente7_ViewBinding(CadastroCliente7 target, View source) {
    this.target = target;

    target.rdSim = Utils.findRequiredViewAsType(source, R.id.rdSim, "field 'rdSim'", RadioButton.class);
    target.rdNao = Utils.findRequiredViewAsType(source, R.id.rdNao, "field 'rdNao'", RadioButton.class);
    target.edtEmail4 = Utils.findRequiredViewAsType(source, R.id.edtEmail4, "field 'edtEmail4'", EditText.class);
    target.edtEmail1 = Utils.findRequiredViewAsType(source, R.id.edtEmail1, "field 'edtEmail1'", EditText.class);
    target.edtEmail5 = Utils.findRequiredViewAsType(source, R.id.edtEmail5, "field 'edtEmail5'", EditText.class);
    target.edtEmail3 = Utils.findRequiredViewAsType(source, R.id.edtEmail3, "field 'edtEmail3'", EditText.class);
    target.edtEmail2 = Utils.findRequiredViewAsType(source, R.id.edtEmail2, "field 'edtEmail2'", EditText.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroCliente7 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rdSim = null;
    target.rdNao = null;
    target.edtEmail4 = null;
    target.edtEmail1 = null;
    target.edtEmail5 = null;
    target.edtEmail3 = null;
    target.edtEmail2 = null;
    target.btnContinuar = null;
  }
}
