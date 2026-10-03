// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroProspectMotivos_ViewBinding implements Unbinder {
  private CadastroProspectMotivos target;

  @UiThread
  public CadastroProspectMotivos_ViewBinding(CadastroProspectMotivos target, View source) {
    this.target = target;

    target.edtOutrosMotivosProspect = Utils.findRequiredViewAsType(source, R.id.edtOutrosMotivosProspect, "field 'edtOutrosMotivosProspect'", EditText.class);
    target.recyclerMotivos = Utils.findRequiredViewAsType(source, R.id.recyclerMotivos, "field 'recyclerMotivos'", RecyclerView.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroProspectMotivos target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtOutrosMotivosProspect = null;
    target.recyclerMotivos = null;
    target.btnContinuar = null;
  }
}
