// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityAcessibilidade_ViewBinding implements Unbinder {
  private ActivityAcessibilidade target;

  @UiThread
  public ActivityAcessibilidade_ViewBinding(ActivityAcessibilidade target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityAcessibilidade_ViewBinding(ActivityAcessibilidade target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
    target.btnCancelar = Utils.findRequiredViewAsType(source, R.id.btnCancelar, "field 'btnCancelar'", Button.class);
    target.lytFontePadrao = Utils.findRequiredViewAsType(source, R.id.lytFontePadrao, "field 'lytFontePadrao'", LinearLayout.class);
    target.lytFonteMedio = Utils.findRequiredViewAsType(source, R.id.lytFonteMedio, "field 'lytFonteMedio'", LinearLayout.class);
    target.lytFonteGrande = Utils.findRequiredViewAsType(source, R.id.lytFonteGrande, "field 'lytFonteGrande'", LinearLayout.class);
    target.imgTamanhoPadrao = Utils.findRequiredViewAsType(source, R.id.imgTamanhoPadrao, "field 'imgTamanhoPadrao'", ImageView.class);
    target.imgTamanhoMedio = Utils.findRequiredViewAsType(source, R.id.imgTamanhoMedio, "field 'imgTamanhoMedio'", ImageView.class);
    target.imgTamanhoGrande = Utils.findRequiredViewAsType(source, R.id.imgTamanhoGrande, "field 'imgTamanhoGrande'", ImageView.class);
    target.txvTamanhoPadrao = Utils.findRequiredViewAsType(source, R.id.txvTamanhoPadrao, "field 'txvTamanhoPadrao'", TextView.class);
    target.txvTamanhoMedio = Utils.findRequiredViewAsType(source, R.id.txvTamanhoMedio, "field 'txvTamanhoMedio'", TextView.class);
    target.txvTamanhoGrande = Utils.findRequiredViewAsType(source, R.id.txvTamanhoGrande, "field 'txvTamanhoGrande'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityAcessibilidade target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.btnConfirmar = null;
    target.btnCancelar = null;
    target.lytFontePadrao = null;
    target.lytFonteMedio = null;
    target.lytFonteGrande = null;
    target.imgTamanhoPadrao = null;
    target.imgTamanhoMedio = null;
    target.imgTamanhoGrande = null;
    target.txvTamanhoPadrao = null;
    target.txvTamanhoMedio = null;
    target.txvTamanhoGrande = null;
  }
}
