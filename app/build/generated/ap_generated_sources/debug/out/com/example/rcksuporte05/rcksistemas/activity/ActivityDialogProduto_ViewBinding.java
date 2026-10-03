// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityDialogProduto_ViewBinding implements Unbinder {
  private ActivityDialogProduto target;

  @UiThread
  public ActivityDialogProduto_ViewBinding(ActivityDialogProduto target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityDialogProduto_ViewBinding(ActivityDialogProduto target, View source) {
    this.target = target;

    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
    target.checkVisualizaLegenda = Utils.findRequiredViewAsType(source, R.id.checkVisualizaLegenda, "field 'checkVisualizaLegenda'", CheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityDialogProduto target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnConfirmar = null;
    target.checkVisualizaLegenda = null;
  }
}
