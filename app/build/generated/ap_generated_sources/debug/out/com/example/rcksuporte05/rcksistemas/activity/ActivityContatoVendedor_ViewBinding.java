// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityContatoVendedor_ViewBinding implements Unbinder {
  private ActivityContatoVendedor target;

  @UiThread
  public ActivityContatoVendedor_ViewBinding(ActivityContatoVendedor target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityContatoVendedor_ViewBinding(ActivityContatoVendedor target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.txtNomeVendedor = Utils.findRequiredViewAsType(source, R.id.txtNomeVendedor, "field 'txtNomeVendedor'", TextView.class);
    target.txtTelefone = Utils.findRequiredViewAsType(source, R.id.txtTelefone, "field 'txtTelefone'", TextView.class);
    target.txtEmail = Utils.findRequiredViewAsType(source, R.id.txtEmail, "field 'txtEmail'", TextView.class);
    target.txtEndereco = Utils.findRequiredViewAsType(source, R.id.txtEndereco, "field 'txtEndereco'", TextView.class);
    target.lyChamada = Utils.findRequiredViewAsType(source, R.id.lyChamada, "field 'lyChamada'", RelativeLayout.class);
    target.lyEmail = Utils.findRequiredViewAsType(source, R.id.lyEmail, "field 'lyEmail'", RelativeLayout.class);
    target.lyGps = Utils.findRequiredViewAsType(source, R.id.lyGps, "field 'lyGps'", RelativeLayout.class);
    target.edtDataCadastro = Utils.findRequiredViewAsType(source, R.id.edtDataCadastro, "field 'edtDataCadastro'", TextView.class);
    target.txtMunicipio = Utils.findRequiredViewAsType(source, R.id.txtMunicipio, "field 'txtMunicipio'", TextView.class);
    target.txtUf = Utils.findRequiredViewAsType(source, R.id.txtUf, "field 'txtUf'", TextView.class);
    target.txtDescricaoTelefone = Utils.findRequiredViewAsType(source, R.id.txtDescricaoTelefone, "field 'txtDescricaoTelefone'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityContatoVendedor target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtNomeVendedor = null;
    target.txtTelefone = null;
    target.txtEmail = null;
    target.txtEndereco = null;
    target.lyChamada = null;
    target.lyEmail = null;
    target.lyGps = null;
    target.edtDataCadastro = null;
    target.txtMunicipio = null;
    target.txtUf = null;
    target.txtDescricaoTelefone = null;
  }
}
