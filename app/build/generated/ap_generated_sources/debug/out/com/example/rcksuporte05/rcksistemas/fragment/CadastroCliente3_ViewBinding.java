// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroCliente3_ViewBinding implements Unbinder {
  private CadastroCliente3 target;

  @UiThread
  public CadastroCliente3_ViewBinding(CadastroCliente3 target, View source) {
    this.target = target;

    target.spPaisCobranca = Utils.findRequiredViewAsType(source, R.id.edtPaisCobranca, "field 'spPaisCobranca'", Spinner.class);
    target.spUfCobranca = Utils.findRequiredViewAsType(source, R.id.edtUfCobranca, "field 'spUfCobranca'", Spinner.class);
    target.spMunicipioCobranca = Utils.findRequiredViewAsType(source, R.id.edtMunicipioCobranca, "field 'spMunicipioCobranca'", Spinner.class);
    target.edtLimiteCredito = Utils.findRequiredViewAsType(source, R.id.edtLimiteCredito, "field 'edtLimiteCredito'", EditText.class);
    target.edtContatoFinanceiro = Utils.findRequiredViewAsType(source, R.id.edtContatoFinanceiro, "field 'edtContatoFinanceiro'", EditText.class);
    target.edtEmailFinanceiro = Utils.findRequiredViewAsType(source, R.id.edtEmailFinanceiro, "field 'edtEmailFinanceiro'", EditText.class);
    target.edtEnderecoCobranca = Utils.findRequiredViewAsType(source, R.id.edtEnderecoCobranca, "field 'edtEnderecoCobranca'", EditText.class);
    target.edtNumero = Utils.findRequiredViewAsType(source, R.id.edtNumero, "field 'edtNumero'", EditText.class);
    target.edtBairro = Utils.findRequiredViewAsType(source, R.id.edtBairro, "field 'edtBairro'", EditText.class);
    target.edtComplemento = Utils.findRequiredViewAsType(source, R.id.edtComplemento, "field 'edtComplemento'", EditText.class);
    target.edtCepCobranca = Utils.findRequiredViewAsType(source, R.id.edtCepCobranca, "field 'edtCepCobranca'", EditText.class);
    target.btnHistoricoFinanceiro = Utils.findRequiredViewAsType(source, R.id.btnHistoricoFinanceiro, "field 'btnHistoricoFinanceiro'", Button.class);
    target.txtHistoricoFinanceiro = Utils.findRequiredViewAsType(source, R.id.txtHistoricoFinanceiro, "field 'txtHistoricoFinanceiro'", TextView.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroCliente3 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spPaisCobranca = null;
    target.spUfCobranca = null;
    target.spMunicipioCobranca = null;
    target.edtLimiteCredito = null;
    target.edtContatoFinanceiro = null;
    target.edtEmailFinanceiro = null;
    target.edtEnderecoCobranca = null;
    target.edtNumero = null;
    target.edtBairro = null;
    target.edtComplemento = null;
    target.edtCepCobranca = null;
    target.btnHistoricoFinanceiro = null;
    target.txtHistoricoFinanceiro = null;
    target.btnContinuar = null;
  }
}
