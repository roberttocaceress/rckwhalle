// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
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

public class ActivityCobrancaContato_ViewBinding implements Unbinder {
  private ActivityCobrancaContato target;

  @UiThread
  public ActivityCobrancaContato_ViewBinding(ActivityCobrancaContato target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityCobrancaContato_ViewBinding(ActivityCobrancaContato target, View source) {
    this.target = target;

    target.txtCategoria = Utils.findRequiredViewAsType(source, R.id.txtCategoria, "field 'txtCategoria'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.imFisicaJuridica = Utils.findRequiredViewAsType(source, R.id.imFisicaJuridica, "field 'imFisicaJuridica'", ImageView.class);
    target.txtRazaoSocial = Utils.findRequiredViewAsType(source, R.id.txtRazaoSocial, "field 'txtRazaoSocial'", TextView.class);
    target.txtNomeFantasia = Utils.findRequiredViewAsType(source, R.id.txtNomeFantasia, "field 'txtNomeFantasia'", TextView.class);
    target.txtTelefone = Utils.findRequiredViewAsType(source, R.id.txtTelefone, "field 'txtTelefone'", TextView.class);
    target.txtEmail = Utils.findRequiredViewAsType(source, R.id.txtEmail, "field 'txtEmail'", TextView.class);
    target.txtEndereco = Utils.findRequiredViewAsType(source, R.id.txtEndereco, "field 'txtEndereco'", TextView.class);
    target.lyChamada = Utils.findRequiredViewAsType(source, R.id.lyChamada, "field 'lyChamada'", RelativeLayout.class);
    target.lyEmail = Utils.findRequiredViewAsType(source, R.id.lyEmail, "field 'lyEmail'", RelativeLayout.class);
    target.lyGps = Utils.findRequiredViewAsType(source, R.id.lyGps, "field 'lyGps'", RelativeLayout.class);
    target.lyNomeCliente = Utils.findRequiredViewAsType(source, R.id.lyNomeCliente, "field 'lyNomeCliente'", RelativeLayout.class);
    target.edtDataCadastro = Utils.findRequiredViewAsType(source, R.id.edtDataCadastro, "field 'edtDataCadastro'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityCobrancaContato target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtCategoria = null;
    target.toolbar = null;
    target.imFisicaJuridica = null;
    target.txtRazaoSocial = null;
    target.txtNomeFantasia = null;
    target.txtTelefone = null;
    target.txtEmail = null;
    target.txtEndereco = null;
    target.lyChamada = null;
    target.lyEmail = null;
    target.lyGps = null;
    target.lyNomeCliente = null;
    target.edtDataCadastro = null;
  }
}
