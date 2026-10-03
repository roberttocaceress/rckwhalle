// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityDataFiltroCobranca_ViewBinding implements Unbinder {
  private ActivityDataFiltroCobranca target;

  @UiThread
  public ActivityDataFiltroCobranca_ViewBinding(ActivityDataFiltroCobranca target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityDataFiltroCobranca_ViewBinding(ActivityDataFiltroCobranca target, View source) {
    this.target = target;

    target.btnDiaAnterior = Utils.findRequiredViewAsType(source, R.id.btnDiaAnterior, "field 'btnDiaAnterior'", Button.class);
    target.btnDiaAtual = Utils.findRequiredViewAsType(source, R.id.btnDiaAtual, "field 'btnDiaAtual'", Button.class);
    target.btnMesAtual = Utils.findRequiredViewAsType(source, R.id.btnMesAtual, "field 'btnMesAtual'", Button.class);
    target.btnMesAnterior = Utils.findRequiredViewAsType(source, R.id.btnMesAnterior, "field 'btnMesAnterior'", Button.class);
    target.btnSemanaAtual = Utils.findRequiredViewAsType(source, R.id.btnSemanaAtual, "field 'btnSemanaAtual'", Button.class);
    target.btnSemanaAnterior = Utils.findRequiredViewAsType(source, R.id.btnSemanaAnterior, "field 'btnSemanaAnterior'", Button.class);
    target.btnCalendario = Utils.findRequiredViewAsType(source, R.id.btnCalendario, "field 'btnCalendario'", Button.class);
    target.btnFiltrar = Utils.findRequiredViewAsType(source, R.id.btnFiltrar, "field 'btnFiltrar'", Button.class);
    target.btnCancelar = Utils.findRequiredViewAsType(source, R.id.btnCancelar, "field 'btnCancelar'", Button.class);
    target.opcaoEmissao = Utils.findRequiredViewAsType(source, R.id.opcaoEmissao, "field 'opcaoEmissao'", CheckBox.class);
    target.opcaoCompetencia = Utils.findRequiredViewAsType(source, R.id.opcaoCompetencia, "field 'opcaoCompetencia'", CheckBox.class);
    target.opcaoVencimento = Utils.findRequiredViewAsType(source, R.id.opcaoVencimento, "field 'opcaoVencimento'", CheckBox.class);
    target.opcaoQuitacao = Utils.findRequiredViewAsType(source, R.id.opcaoQuitacao, "field 'opcaoQuitacao'", CheckBox.class);
    target.txvDiaInicial = Utils.findRequiredViewAsType(source, R.id.txvDiaInicial, "field 'txvDiaInicial'", TextView.class);
    target.txvDiaFinal = Utils.findRequiredViewAsType(source, R.id.txvDiaFinal, "field 'txvDiaFinal'", TextView.class);
    target.txvAnoInicial = Utils.findRequiredViewAsType(source, R.id.txvAnoInicial, "field 'txvAnoInicial'", TextView.class);
    target.txvAnoFinal = Utils.findRequiredViewAsType(source, R.id.txvAnoFinal, "field 'txvAnoFinal'", TextView.class);
    target.txvMesInicial = Utils.findRequiredViewAsType(source, R.id.txvMesInicial, "field 'txvMesInicial'", TextView.class);
    target.txvMesFinal = Utils.findRequiredViewAsType(source, R.id.txvMesFinal, "field 'txvMesFinal'", TextView.class);
    target.spCobrancaStatus = Utils.findRequiredViewAsType(source, R.id.spCobrancaStatus, "field 'spCobrancaStatus'", Spinner.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityDataFiltroCobranca target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnDiaAnterior = null;
    target.btnDiaAtual = null;
    target.btnMesAtual = null;
    target.btnMesAnterior = null;
    target.btnSemanaAtual = null;
    target.btnSemanaAnterior = null;
    target.btnCalendario = null;
    target.btnFiltrar = null;
    target.btnCancelar = null;
    target.opcaoEmissao = null;
    target.opcaoCompetencia = null;
    target.opcaoVencimento = null;
    target.opcaoQuitacao = null;
    target.txvDiaInicial = null;
    target.txvDiaFinal = null;
    target.txvAnoInicial = null;
    target.txvAnoFinal = null;
    target.txvMesInicial = null;
    target.txvMesFinal = null;
    target.spCobrancaStatus = null;
    target.toolbar = null;
  }
}
