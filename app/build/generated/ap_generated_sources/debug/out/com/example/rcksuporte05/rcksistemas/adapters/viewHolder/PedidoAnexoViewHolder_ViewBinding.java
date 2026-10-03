// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PedidoAnexoViewHolder_ViewBinding implements Unbinder {
  private PedidoAnexoViewHolder target;

  @UiThread
  public PedidoAnexoViewHolder_ViewBinding(PedidoAnexoViewHolder target, View source) {
    this.target = target;

    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", RelativeLayout.class);
    target.imAnexo = Utils.findRequiredViewAsType(source, R.id.imAnexo, "field 'imAnexo'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PedidoAnexoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.itemView = null;
    target.imAnexo = null;
  }
}
