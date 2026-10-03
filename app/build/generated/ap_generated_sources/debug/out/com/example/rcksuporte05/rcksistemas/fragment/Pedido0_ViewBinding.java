// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Pedido0_ViewBinding implements Unbinder {
  private Pedido0 target;

  @UiThread
  public Pedido0_ViewBinding(Pedido0 target, View source) {
    this.target = target;

    target.btnCliente = Utils.findRequiredViewAsType(source, R.id.btnCliente, "field 'btnCliente'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Pedido0 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnCliente = null;
  }
}
