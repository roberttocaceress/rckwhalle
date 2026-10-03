// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroCliente2_ViewBinding implements Unbinder {
  private CadastroCliente2 target;

  @UiThread
  public CadastroCliente2_ViewBinding(CadastroCliente2 target, View source) {
    this.target = target;

    target.edtNumero = Utils.findRequiredViewAsType(source, R.id.edtNumero, "field 'edtNumero'", EditText.class);
    target.edtBairro = Utils.findRequiredViewAsType(source, R.id.edtBairro, "field 'edtBairro'", EditText.class);
    target.edtCep = Utils.findRequiredViewAsType(source, R.id.edtCep, "field 'edtCep'", EditText.class);
    target.edtcHECKIN = Utils.findRequiredViewAsType(source, R.id.edtcHECKIN, "field 'edtcHECKIN'", EditText.class);
    target.edtEndereco = Utils.findRequiredViewAsType(source, R.id.edtEndereco, "field 'edtEndereco'", EditText.class);
    target.edtPais = Utils.findRequiredViewAsType(source, R.id.edtPais, "field 'edtPais'", Spinner.class);
    target.edtUf = Utils.findRequiredViewAsType(source, R.id.edtUf, "field 'edtUf'", Spinner.class);
    target.edtMunicipio = Utils.findRequiredViewAsType(source, R.id.edtMunicipio, "field 'edtMunicipio'", Spinner.class);
    target.edtComplemento = Utils.findRequiredViewAsType(source, R.id.edtComplemento, "field 'edtComplemento'", EditText.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_splash, "field 'progress_bar'", ProgressBar.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroCliente2 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtNumero = null;
    target.edtBairro = null;
    target.edtCep = null;
    target.edtcHECKIN = null;
    target.edtEndereco = null;
    target.edtPais = null;
    target.edtUf = null;
    target.edtMunicipio = null;
    target.edtComplemento = null;
    target.btnContinuar = null;
    target.progress_bar = null;
    target.progress_title = null;
  }
}
