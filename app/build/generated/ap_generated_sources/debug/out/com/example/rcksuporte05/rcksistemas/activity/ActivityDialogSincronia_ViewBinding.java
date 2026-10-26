// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Switch;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityDialogSincronia_ViewBinding implements Unbinder {
  private ActivityDialogSincronia target;

  private View view7f0a00c3;

  private View view7f0a00ba;

  @UiThread
  public ActivityDialogSincronia_ViewBinding(ActivityDialogSincronia target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityDialogSincronia_ViewBinding(final ActivityDialogSincronia target, View source) {
    this.target = target;

    View view;
    target.id_opcao_pedidos = Utils.findRequiredViewAsType(source, R.id.id_opcao_pedidos, "field 'id_opcao_pedidos'", Switch.class);
    target.id_opcao_cliente = Utils.findRequiredViewAsType(source, R.id.id_opcao_cliente, "field 'id_opcao_cliente'", Switch.class);
    target.id_opcao_produto = Utils.findRequiredViewAsType(source, R.id.id_opcao_produto, "field 'id_opcao_produto'", Switch.class);
    target.id_opcao_pedidos_pendentes = Utils.findRequiredViewAsType(source, R.id.id_opcao_pedidos_pendentes, "field 'id_opcao_pedidos_pendentes'", Switch.class);
    target.id_opcao_prospect = Utils.findRequiredViewAsType(source, R.id.id_opcao_prospect, "field 'id_opcao_prospect'", Switch.class);
    target.id_opcao_prospect_enviados = Utils.findRequiredViewAsType(source, R.id.id_opcao_prospect_enviados, "field 'id_opcao_prospect_enviados'", Switch.class);
    target.id_opcao_visiatas_pendentes = Utils.findRequiredViewAsType(source, R.id.id_opcao_visiatas_pendentes, "field 'id_opcao_visiatas_pendentes'", Switch.class);
    target.id_opcao_imagem_produto = Utils.findRequiredViewAsType(source, R.id.id_opcao_imagem_produto, "field 'id_opcao_imagem_produto'", Switch.class);
    target.id_opcao_cobrancas = Utils.findRequiredViewAsType(source, R.id.id_opcao_cobrancas, "field 'id_opcao_cobrancas'", Switch.class);
    view = Utils.findRequiredView(source, R.id.btnConfirmar, "method 'btnConfirmar'");
    view7f0a00c3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnConfirmar();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCancelar, "method 'btnCancelar'");
    view7f0a00ba = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnCancelar();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityDialogSincronia target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.id_opcao_pedidos = null;
    target.id_opcao_cliente = null;
    target.id_opcao_produto = null;
    target.id_opcao_pedidos_pendentes = null;
    target.id_opcao_prospect = null;
    target.id_opcao_prospect_enviados = null;
    target.id_opcao_visiatas_pendentes = null;
    target.id_opcao_imagem_produto = null;
    target.id_opcao_cobrancas = null;

    view7f0a00c3.setOnClickListener(null);
    view7f0a00c3 = null;
    view7f0a00ba.setOnClickListener(null);
    view7f0a00ba = null;
  }
}
