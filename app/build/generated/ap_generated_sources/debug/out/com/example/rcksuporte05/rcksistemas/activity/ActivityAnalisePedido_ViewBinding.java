// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
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

public class ActivityAnalisePedido_ViewBinding implements Unbinder {
  private ActivityAnalisePedido target;

  @UiThread
  public ActivityAnalisePedido_ViewBinding(ActivityAnalisePedido target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityAnalisePedido_ViewBinding(ActivityAnalisePedido target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tblAnalisePedido, "field 'toolbar'", Toolbar.class);
    target.edtMotivo = Utils.findRequiredViewAsType(source, R.id.edtMotivo, "field 'edtMotivo'", EditText.class);
    target.txvCredito = Utils.findRequiredViewAsType(source, R.id.txvCredito, "field 'txvCredito'", TextView.class);
    target.txvCarregamento = Utils.findRequiredViewAsType(source, R.id.txvCarregamento, "field 'txvCarregamento'", TextView.class);
    target.txvExpedicao = Utils.findRequiredViewAsType(source, R.id.txvExpedicao, "field 'txvExpedicao'", TextView.class);
    target.txvFaturamento = Utils.findRequiredViewAsType(source, R.id.txvFaturamento, "field 'txvFaturamento'", TextView.class);
    target.txvBoletoEmitido = Utils.findRequiredViewAsType(source, R.id.txvBoletoEmitido, "field 'txvBoletoEmitido'", TextView.class);
    target.txvTransportador = Utils.findRequiredViewAsType(source, R.id.txvTransportador, "field 'txvTransportador'", TextView.class);
    target.txvEspecie = Utils.findRequiredViewAsType(source, R.id.txvEspecie, "field 'txvEspecie'", TextView.class);
    target.txvVolume = Utils.findRequiredViewAsType(source, R.id.txvVolume, "field 'txvVolume'", TextView.class);
    target.txvDataImportacao = Utils.findRequiredViewAsType(source, R.id.txvDataImportacao, "field 'txvDataImportacao'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityAnalisePedido target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.edtMotivo = null;
    target.txvCredito = null;
    target.txvCarregamento = null;
    target.txvExpedicao = null;
    target.txvFaturamento = null;
    target.txvBoletoEmitido = null;
    target.txvTransportador = null;
    target.txvEspecie = null;
    target.txvVolume = null;
    target.txvDataImportacao = null;
  }
}
