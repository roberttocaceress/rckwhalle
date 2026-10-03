// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListaEmprasaClienteViewHolder_ViewBinding implements Unbinder {
  private ListaEmprasaClienteViewHolder target;

  @UiThread
  public ListaEmprasaClienteViewHolder_ViewBinding(ListaEmprasaClienteViewHolder target,
      View source) {
    this.target = target;

    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", ImageView.class);
    target.edtIPUrl = Utils.findRequiredViewAsType(source, R.id.edtIPUrl, "field 'edtIPUrl'", TextInputEditText.class);
    target.edtPorta = Utils.findRequiredViewAsType(source, R.id.edtPorta, "field 'edtPorta'", TextInputEditText.class);
    target.edtRazaoSocial = Utils.findRequiredViewAsType(source, R.id.edtRazaoSocial, "field 'edtRazaoSocial'", TextInputEditText.class);
    target.spVersaoAPI = Utils.findRequiredViewAsType(source, R.id.spVersaoAPI, "field 'spVersaoAPI'", Spinner.class);
    target.swtConexaoSegura = Utils.findRequiredViewAsType(source, R.id.swtConexaoSegura, "field 'swtConexaoSegura'", Switch.class);
    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListaEmprasaClienteViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgAvatar = null;
    target.edtIPUrl = null;
    target.edtPorta = null;
    target.edtRazaoSocial = null;
    target.spVersaoAPI = null;
    target.swtConexaoSegura = null;
    target.btnConfirmar = null;
  }
}
