// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityAjuda_ViewBinding implements Unbinder {
  private ActivityAjuda target;

  private View view7f0a0117;

  private View view7f0a0119;

  private View view7f0a0118;

  private View view7f0a0116;

  private View view7f0a011d;

  private View view7f0a011c;

  private View view7f0a011b;

  private View view7f0a011a;

  @UiThread
  public ActivityAjuda_ViewBinding(ActivityAjuda target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityAjuda_ViewBinding(final ActivityAjuda target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.ajuda_toolbar, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.btn_ajuda_configuracao, "field 'btn_ajuda_configuracao' and method 'showActivityConfiguracao'");
    target.btn_ajuda_configuracao = Utils.castView(view, R.id.btn_ajuda_configuracao, "field 'btn_ajuda_configuracao'", Button.class);
    view7f0a0117 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showActivityConfiguracao();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ajuda_preferencia, "field 'btn_ajuda_preferencia' and method 'showActivityPreferencia'");
    target.btn_ajuda_preferencia = Utils.castView(view, R.id.btn_ajuda_preferencia, "field 'btn_ajuda_preferencia'", Button.class);
    view7f0a0119 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showActivityPreferencia();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ajuda_lista_usuario, "field 'btn_ajuda_lista_usuario' and method 'getListaUsuario'");
    target.btn_ajuda_lista_usuario = Utils.castView(view, R.id.btn_ajuda_lista_usuario, "field 'btn_ajuda_lista_usuario'", Button.class);
    view7f0a0118 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.getListaUsuario();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ajuda_atualiza_url_api, "field 'btn_ajuda_atualiza_url_api' and method 'updateURLAPI'");
    target.btn_ajuda_atualiza_url_api = Utils.castView(view, R.id.btn_ajuda_atualiza_url_api, "field 'btn_ajuda_atualiza_url_api'", Button.class);
    view7f0a0116 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.updateURLAPI();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ajuda_suporte_whats, "field 'btn_ajuda_suporte_whats' and method 'showWhatsApp'");
    target.btn_ajuda_suporte_whats = Utils.castView(view, R.id.btn_ajuda_suporte_whats, "field 'btn_ajuda_suporte_whats'", Button.class);
    view7f0a011d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showWhatsApp();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ajuda_suporte_telefone, "field 'btn_ajuda_suporte_telefone' and method 'showTelefoneApp'");
    target.btn_ajuda_suporte_telefone = Utils.castView(view, R.id.btn_ajuda_suporte_telefone, "field 'btn_ajuda_suporte_telefone'", Button.class);
    view7f0a011c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showTelefoneApp();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ajuda_suporte_email, "field 'btn_ajuda_suporte_email' and method 'showEmailApp'");
    target.btn_ajuda_suporte_email = Utils.castView(view, R.id.btn_ajuda_suporte_email, "field 'btn_ajuda_suporte_email'", Button.class);
    view7f0a011b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showEmailApp();
      }
    });
    target.txv_ajuda_informacoes = Utils.findRequiredViewAsType(source, R.id.txv_ajuda_informacoes, "field 'txv_ajuda_informacoes'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_ajuda_sobre, "field 'btn_ajuda_informacoes' and method 'showActivitySobre'");
    target.btn_ajuda_informacoes = Utils.castView(view, R.id.btn_ajuda_sobre, "field 'btn_ajuda_informacoes'", Button.class);
    view7f0a011a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showActivitySobre();
      }
    });
    target.progress_ajuda = Utils.findRequiredViewAsType(source, R.id.progress_ajuda, "field 'progress_ajuda'", ProgressBar.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityAjuda target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.btn_ajuda_configuracao = null;
    target.btn_ajuda_preferencia = null;
    target.btn_ajuda_lista_usuario = null;
    target.btn_ajuda_atualiza_url_api = null;
    target.btn_ajuda_suporte_whats = null;
    target.btn_ajuda_suporte_telefone = null;
    target.btn_ajuda_suporte_email = null;
    target.txv_ajuda_informacoes = null;
    target.btn_ajuda_informacoes = null;
    target.progress_ajuda = null;
    target.progress_title = null;

    view7f0a0117.setOnClickListener(null);
    view7f0a0117 = null;
    view7f0a0119.setOnClickListener(null);
    view7f0a0119 = null;
    view7f0a0118.setOnClickListener(null);
    view7f0a0118 = null;
    view7f0a0116.setOnClickListener(null);
    view7f0a0116 = null;
    view7f0a011d.setOnClickListener(null);
    view7f0a011d = null;
    view7f0a011c.setOnClickListener(null);
    view7f0a011c = null;
    view7f0a011b.setOnClickListener(null);
    view7f0a011b = null;
    view7f0a011a.setOnClickListener(null);
    view7f0a011a = null;
  }
}
