// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivitySincronia_ViewBinding implements Unbinder {
  private ActivitySincronia target;

  @UiThread
  public ActivitySincronia_ViewBinding(ActivitySincronia target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivitySincronia_ViewBinding(ActivitySincronia target, View source) {
    this.target = target;

    target.id_opcao_pedidos = Utils.findRequiredViewAsType(source, R.id.id_opcao_pedidos, "field 'id_opcao_pedidos'", Switch.class);
    target.id_opcao_cliente = Utils.findRequiredViewAsType(source, R.id.id_opcao_cliente, "field 'id_opcao_cliente'", Switch.class);
    target.id_opcao_produto = Utils.findRequiredViewAsType(source, R.id.id_opcao_produto, "field 'id_opcao_produto'", Switch.class);
    target.id_opcao_pedidos_pendentes = Utils.findRequiredViewAsType(source, R.id.id_opcao_pedidos_pendentes, "field 'id_opcao_pedidos_pendentes'", Switch.class);
    target.id_opcao_prospect = Utils.findRequiredViewAsType(source, R.id.id_opcao_prospect, "field 'id_opcao_prospect'", Switch.class);
    target.id_opcao_prospect_enviados = Utils.findRequiredViewAsType(source, R.id.id_opcao_prospect_enviados, "field 'id_opcao_prospect_enviados'", Switch.class);
    target.id_opcao_visitas_pendentes = Utils.findRequiredViewAsType(source, R.id.id_opcao_visitas_pendentes, "field 'id_opcao_visitas_pendentes'", Switch.class);
    target.id_opcao_imagem_produto = Utils.findRequiredViewAsType(source, R.id.id_opcao_imagem_produto, "field 'id_opcao_imagem_produto'", Switch.class);
    target.id_opcao_cobrancas = Utils.findRequiredViewAsType(source, R.id.id_opcao_cobrancas, "field 'id_opcao_cobrancas'", Switch.class);
    target.id_opcao_fazer_checkin = Utils.findRequiredViewAsType(source, R.id.id_opcao_fazer_checkin, "field 'id_opcao_fazer_checkin'", Switch.class);
    target.id_opcao_restaurar_pendentes = Utils.findRequiredViewAsType(source, R.id.id_opcao_restaurar_pendentes, "field 'id_opcao_restaurar_pendentes'", Switch.class);
    target.id_opcao_restaurar_clientes = Utils.findRequiredViewAsType(source, R.id.id_opcao_restaurar_clientes, "field 'id_opcao_restaurar_clientes'", Switch.class);
    target.lytEncerrarRepetir = Utils.findRequiredViewAsType(source, R.id.lytEncerrarRepetir, "field 'lytEncerrarRepetir'", LinearLayout.class);
    target.linearLayout6 = Utils.findRequiredViewAsType(source, R.id.linearLayout6, "field 'linearLayout6'", LinearLayout.class);
    target.txvMensagem = Utils.findRequiredViewAsType(source, R.id.txvMensagem, "field 'txvMensagem'", TextView.class);
    target.btnEncerrar = Utils.findRequiredViewAsType(source, R.id.btnEncerrar, "field 'btnEncerrar'", Button.class);
    target.btnRepetir = Utils.findRequiredViewAsType(source, R.id.btnRepetir, "field 'btnRepetir'", Button.class);
    target.btnCancelar = Utils.findRequiredViewAsType(source, R.id.btnCancelar, "field 'btnCancelar'", Button.class);
    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
    target.btnNovoApp = Utils.findRequiredViewAsType(source, R.id.btnNovoApp, "field 'btnNovoApp'", Button.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivitySincronia target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.id_opcao_pedidos = null;
    target.id_opcao_cliente = null;
    target.id_opcao_produto = null;
    target.id_opcao_pedidos_pendentes = null;
    target.id_opcao_prospect = null;
    target.id_opcao_prospect_enviados = null;
    target.id_opcao_visitas_pendentes = null;
    target.id_opcao_imagem_produto = null;
    target.id_opcao_cobrancas = null;
    target.id_opcao_fazer_checkin = null;
    target.id_opcao_restaurar_pendentes = null;
    target.id_opcao_restaurar_clientes = null;
    target.lytEncerrarRepetir = null;
    target.linearLayout6 = null;
    target.txvMensagem = null;
    target.btnEncerrar = null;
    target.btnRepetir = null;
    target.btnCancelar = null;
    target.btnConfirmar = null;
    target.btnNovoApp = null;
    target.toolbar = null;
  }
}
