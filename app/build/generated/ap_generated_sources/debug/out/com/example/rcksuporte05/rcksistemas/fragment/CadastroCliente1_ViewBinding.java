// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroCliente1_ViewBinding implements Unbinder {
  private CadastroCliente1 target;

  @UiThread
  public CadastroCliente1_ViewBinding(CadastroCliente1 target, View source) {
    this.target = target;

    target.edtData = Utils.findRequiredViewAsType(source, R.id.edtData, "field 'edtData'", EditText.class);
    target.edtNomeFantasia = Utils.findRequiredViewAsType(source, R.id.edtNomeFantasia, "field 'edtNomeFantasia'", EditText.class);
    target.edtCpfCnpj = Utils.findRequiredViewAsType(source, R.id.edtCpfCnpj, "field 'edtCpfCnpj'", EditText.class);
    target.edtTelefonePrincipal = Utils.findRequiredViewAsType(source, R.id.edtTelefonePrincipal, "field 'edtTelefonePrincipal'", EditText.class);
    target.edtTelefone1 = Utils.findRequiredViewAsType(source, R.id.edtTelefone1, "field 'edtTelefone1'", EditText.class);
    target.edtTelefone2 = Utils.findRequiredViewAsType(source, R.id.edtTelefone2, "field 'edtTelefone2'", EditText.class);
    target.edtPessoaContato = Utils.findRequiredViewAsType(source, R.id.edtPessoaContato, "field 'edtPessoaContato'", EditText.class);
    target.edtEmailPrincipal = Utils.findRequiredViewAsType(source, R.id.edtEmailPrincipal, "field 'edtEmailPrincipal'", EditText.class);
    target.edtNomeCliente = Utils.findRequiredViewAsType(source, R.id.edtNomeCliente, "field 'edtNomeCliente'", EditText.class);
    target.edtInscEstadual = Utils.findRequiredViewAsType(source, R.id.edtInscEstadual, "field 'edtInscEstadual'", EditText.class);
    target.rdFisica = Utils.findRequiredViewAsType(source, R.id.rdFisica, "field 'rdFisica'", RadioButton.class);
    target.rdJuridica = Utils.findRequiredViewAsType(source, R.id.rdJuridica, "field 'rdJuridica'", RadioButton.class);
    target.txtCpfCnpj = Utils.findRequiredViewAsType(source, R.id.txtCpfCnpj, "field 'txtCpfCnpj'", TextView.class);
    target.txtId = Utils.findRequiredViewAsType(source, R.id.txtId, "field 'txtId'", TextView.class);
    target.txtNomeCliente = Utils.findRequiredViewAsType(source, R.id.txtNomeCliente, "field 'txtNomeCliente'", TextView.class);
    target.spIe = Utils.findRequiredViewAsType(source, R.id.spIe, "field 'spIe'", Spinner.class);
    target.spCategoria = Utils.findRequiredViewAsType(source, R.id.spCategoria, "field 'spCategoria'", Spinner.class);
    target.txtCategoria = Utils.findRequiredViewAsType(source, R.id.txtCategoria, "field 'txtCategoria'", TextView.class);
    target.spSegmento = Utils.findRequiredViewAsType(source, R.id.spSegmento, "field 'spSegmento'", Spinner.class);
    target.txtSegmento = Utils.findRequiredViewAsType(source, R.id.txtSegmento, "field 'txtSegmento'", TextView.class);
    target.txtData = Utils.findRequiredViewAsType(source, R.id.txtData, "field 'txtData'", TextView.class);
    target.btnLigar1 = Utils.findRequiredViewAsType(source, R.id.btnLigar1, "field 'btnLigar1'", Button.class);
    target.btnLigar2 = Utils.findRequiredViewAsType(source, R.id.btnLigar2, "field 'btnLigar2'", Button.class);
    target.btnLigar3 = Utils.findRequiredViewAsType(source, R.id.btnLigar3, "field 'btnLigar3'", Button.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroCliente1 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtData = null;
    target.edtNomeFantasia = null;
    target.edtCpfCnpj = null;
    target.edtTelefonePrincipal = null;
    target.edtTelefone1 = null;
    target.edtTelefone2 = null;
    target.edtPessoaContato = null;
    target.edtEmailPrincipal = null;
    target.edtNomeCliente = null;
    target.edtInscEstadual = null;
    target.rdFisica = null;
    target.rdJuridica = null;
    target.txtCpfCnpj = null;
    target.txtId = null;
    target.txtNomeCliente = null;
    target.spIe = null;
    target.spCategoria = null;
    target.txtCategoria = null;
    target.spSegmento = null;
    target.txtSegmento = null;
    target.txtData = null;
    target.btnLigar1 = null;
    target.btnLigar2 = null;
    target.btnLigar3 = null;
    target.btnContinuar = null;
  }
}
