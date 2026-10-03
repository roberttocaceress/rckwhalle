// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroProspectGeral_ViewBinding implements Unbinder {
  private CadastroProspectGeral target;

  @UiThread
  public CadastroProspectGeral_ViewBinding(CadastroProspectGeral target, View source) {
    this.target = target;

    target.edtNomeClienteProspect = Utils.findRequiredViewAsType(source, R.id.edtNomeClienteProspect, "field 'edtNomeClienteProspect'", EditText.class);
    target.edtNomeFantasiaProspect = Utils.findRequiredViewAsType(source, R.id.edtNomeFantasiaProspect, "field 'edtNomeFantasiaProspect'", EditText.class);
    target.edtCpfCnpjProspect = Utils.findRequiredViewAsType(source, R.id.edtCpfCnpjProspect, "field 'edtCpfCnpjProspect'", EditText.class);
    target.edtInscEstadualProspect = Utils.findRequiredViewAsType(source, R.id.edtInscEstadualProspect, "field 'edtInscEstadualProspect'", EditText.class);
    target.spIeProspect = Utils.findRequiredViewAsType(source, R.id.spIeProspect, "field 'spIeProspect'", Spinner.class);
    target.spCategoriaProspect = Utils.findRequiredViewAsType(source, R.id.spCategoriaProspect, "field 'spCategoriaProspect'", Spinner.class);
    target.txtCpfCnpjProspect = Utils.findRequiredViewAsType(source, R.id.txtCpfCnpjProspect, "field 'txtCpfCnpjProspect'", TextView.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
    target.rdPessoaProspect = Utils.findRequiredViewAsType(source, R.id.rdPessoaProspect, "field 'rdPessoaProspect'", RadioGroup.class);
    target.rdFisica = Utils.findRequiredViewAsType(source, R.id.rdFisica, "field 'rdFisica'", RadioButton.class);
    target.rdJuridica = Utils.findRequiredViewAsType(source, R.id.rdJuridica, "field 'rdJuridica'", RadioButton.class);
    target.edtTelefoneProspect = Utils.findRequiredViewAsType(source, R.id.edtTelefoneProspect, "field 'edtTelefoneProspect'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroProspectGeral target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtNomeClienteProspect = null;
    target.edtNomeFantasiaProspect = null;
    target.edtCpfCnpjProspect = null;
    target.edtInscEstadualProspect = null;
    target.spIeProspect = null;
    target.spCategoriaProspect = null;
    target.txtCpfCnpjProspect = null;
    target.btnContinuar = null;
    target.rdPessoaProspect = null;
    target.rdFisica = null;
    target.rdJuridica = null;
    target.edtTelefoneProspect = null;
  }
}
