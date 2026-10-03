// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityTecnico_ViewBinding implements Unbinder {
  private ActivityTecnico target;

  private View view7f0a0125;

  private View view7f0a0124;

  private View view7f0a0123;

  private View view7f0a0121;

  private View view7f0a0122;

  private View view7f0a0120;

  @UiThread
  public ActivityTecnico_ViewBinding(ActivityTecnico target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityTecnico_ViewBinding(final ActivityTecnico target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_tecnico_update_tabela_rck, "field 'btn_tecnico_update_tabela_rck' and method 'updateTabelasRck'");
    target.btn_tecnico_update_tabela_rck = Utils.castView(view, R.id.btn_tecnico_update_tabela_rck, "field 'btn_tecnico_update_tabela_rck'", Button.class);
    view7f0a0125 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.updateTabelasRck();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_tecnico_update_tabela_app, "field 'btn_tecnico_update_tabela_app' and method 'updateTabelasApp'");
    target.btn_tecnico_update_tabela_app = Utils.castView(view, R.id.btn_tecnico_update_tabela_app, "field 'btn_tecnico_update_tabela_app'", Button.class);
    view7f0a0124 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.updateTabelasApp();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_tecnico_update_indice_app, "field 'btn_tecnico_update_indice_app' and method 'updateIndiceApp'");
    target.btn_tecnico_update_indice_app = Utils.castView(view, R.id.btn_tecnico_update_indice_app, "field 'btn_tecnico_update_indice_app'", Button.class);
    view7f0a0123 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.updateIndiceApp();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_tecnico_lista_tabela_app, "method 'listaTabelasApp'");
    view7f0a0121 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.listaTabelasApp();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_tecnico_lista_usuario_app, "method 'listaUsuarioApp'");
    view7f0a0122 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.listaUsuarioApp();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_tecnico_lista_pedido_app, "method 'listaPedidoApp'");
    view7f0a0120 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.listaPedidoApp();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityTecnico target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btn_tecnico_update_tabela_rck = null;
    target.btn_tecnico_update_tabela_app = null;
    target.btn_tecnico_update_indice_app = null;

    view7f0a0125.setOnClickListener(null);
    view7f0a0125 = null;
    view7f0a0124.setOnClickListener(null);
    view7f0a0124 = null;
    view7f0a0123.setOnClickListener(null);
    view7f0a0123 = null;
    view7f0a0121.setOnClickListener(null);
    view7f0a0121 = null;
    view7f0a0122.setOnClickListener(null);
    view7f0a0122 = null;
    view7f0a0120.setOnClickListener(null);
    view7f0a0120 = null;
  }
}
