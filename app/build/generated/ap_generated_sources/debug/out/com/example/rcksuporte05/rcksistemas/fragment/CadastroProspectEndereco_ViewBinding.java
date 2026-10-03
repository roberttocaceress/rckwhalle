// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroProspectEndereco_ViewBinding implements Unbinder {
  private CadastroProspectEndereco target;

  @UiThread
  public CadastroProspectEndereco_ViewBinding(CadastroProspectEndereco target, View source) {
    this.target = target;

    target.edtEnderecoProspect = Utils.findRequiredViewAsType(source, R.id.edtEnderecoProspect, "field 'edtEnderecoProspect'", EditText.class);
    target.edtNumeroProspect = Utils.findRequiredViewAsType(source, R.id.edtNumeroProspect, "field 'edtNumeroProspect'", EditText.class);
    target.edtBairroProspect = Utils.findRequiredViewAsType(source, R.id.edtBairroProspect, "field 'edtBairroProspect'", EditText.class);
    target.edtCep = Utils.findRequiredViewAsType(source, R.id.edtCep, "field 'edtCep'", EditText.class);
    target.spMunicipioProspect = Utils.findRequiredViewAsType(source, R.id.spMunicipioProspect, "field 'spMunicipioProspect'", Spinner.class);
    target.spPaisProspect = Utils.findRequiredViewAsType(source, R.id.spPaisProspect, "field 'spPaisProspect'", Spinner.class);
    target.spUfProspect = Utils.findRequiredViewAsType(source, R.id.spUfProspect, "field 'spUfProspect'", Spinner.class);
    target.edtComplementoProspect = Utils.findRequiredViewAsType(source, R.id.edtComplementoProspect, "field 'edtComplementoProspect'", EditText.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroProspectEndereco target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtEnderecoProspect = null;
    target.edtNumeroProspect = null;
    target.edtBairroProspect = null;
    target.edtCep = null;
    target.spMunicipioProspect = null;
    target.spPaisProspect = null;
    target.spUfProspect = null;
    target.edtComplementoProspect = null;
    target.btnContinuar = null;
  }
}
