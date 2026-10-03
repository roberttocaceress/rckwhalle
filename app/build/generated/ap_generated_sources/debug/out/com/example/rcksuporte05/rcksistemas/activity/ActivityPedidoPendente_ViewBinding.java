// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPedidoPendente_ViewBinding implements Unbinder {
  private ActivityPedidoPendente target;

  @UiThread
  public ActivityPedidoPendente_ViewBinding(ActivityPedidoPendente target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPedidoPendente_ViewBinding(ActivityPedidoPendente target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaPedidosPendentes, "field 'recyclerView'", RecyclerView.class);
    target.buscapedido = Utils.findRequiredViewAsType(source, R.id.buscaPedido, "field 'buscapedido'", SearchView.class);
    target.helpPedido = Utils.findRequiredViewAsType(source, R.id.helpPedido, "field 'helpPedido'", ImageView.class);
    target.btnNovoPedido = Utils.findRequiredViewAsType(source, R.id.btnNovoPedido, "field 'btnNovoPedido'", Button.class);
    target.lytInserirPedido = Utils.findRequiredViewAsType(source, R.id.lytInserirPedido, "field 'lytInserirPedido'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPedidoPendente target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.buscapedido = null;
    target.helpPedido = null;
    target.btnNovoPedido = null;
    target.lytInserirPedido = null;
  }
}
