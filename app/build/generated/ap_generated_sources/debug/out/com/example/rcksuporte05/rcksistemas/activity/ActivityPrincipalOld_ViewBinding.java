// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPrincipalOld_ViewBinding implements Unbinder {
  private ActivityPrincipalOld target;

  @UiThread
  public ActivityPrincipalOld_ViewBinding(ActivityPrincipalOld target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPrincipalOld_ViewBinding(ActivityPrincipalOld target, View source) {
    this.target = target;

    target.btnCliente = Utils.findRequiredViewAsType(source, R.id.btnCliente, "field 'btnCliente'", Button.class);
    target.btnProduto = Utils.findRequiredViewAsType(source, R.id.btnProduto, "field 'btnProduto'", Button.class);
    target.btnPedidos = Utils.findRequiredViewAsType(source, R.id.btnPedido, "field 'btnPedidos'", Button.class);
    target.btnSincroniza = Utils.findRequiredViewAsType(source, R.id.btnSincroniza, "field 'btnSincroniza'", Button.class);
    target.btnPedidoFinalizado = Utils.findRequiredViewAsType(source, R.id.btnPedidoFinalizado, "field 'btnPedidoFinalizado'", Button.class);
    target.btnPedidoPendente = Utils.findRequiredViewAsType(source, R.id.btnPedidoPendente, "field 'btnPedidoPendente'", Button.class);
    target.btnProspectNovo = Utils.findRequiredViewAsType(source, R.id.btnProspectNovo, "field 'btnProspectNovo'", Button.class);
    target.btnCampanha = Utils.findRequiredViewAsType(source, R.id.btnCampanha, "field 'btnCampanha'", Button.class);
    target.ivInternet = Utils.findRequiredViewAsType(source, R.id.ivInternet, "field 'ivInternet'", ImageView.class);
    target.tb_principal = Utils.findRequiredViewAsType(source, R.id.tb_principal, "field 'tb_principal'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPrincipalOld target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnCliente = null;
    target.btnProduto = null;
    target.btnPedidos = null;
    target.btnSincroniza = null;
    target.btnPedidoFinalizado = null;
    target.btnPedidoPendente = null;
    target.btnProspectNovo = null;
    target.btnCampanha = null;
    target.ivInternet = null;
    target.tb_principal = null;
  }
}
