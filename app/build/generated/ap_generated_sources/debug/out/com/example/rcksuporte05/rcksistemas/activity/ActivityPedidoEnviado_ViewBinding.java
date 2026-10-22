// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPedidoEnviado_ViewBinding implements Unbinder {
  private ActivityPedidoEnviado target;

  private View view7f0a00ee;

  @UiThread
  public ActivityPedidoEnviado_ViewBinding(ActivityPedidoEnviado target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPedidoEnviado_ViewBinding(final ActivityPedidoEnviado target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaPedidoEnviados, "field 'recyclerView'", RecyclerView.class);
    target.buscapedido = Utils.findRequiredViewAsType(source, R.id.buscaPedido, "field 'buscapedido'", SearchView.class);
    target.helpPedido = Utils.findRequiredViewAsType(source, R.id.helpPedido, "field 'helpPedido'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.btnNovoPedido, "method 'btnNovoPedido'");
    view7f0a00ee = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnNovoPedido();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPedidoEnviado target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.buscapedido = null;
    target.helpPedido = null;

    view7f0a00ee.setOnClickListener(null);
    view7f0a00ee = null;
  }
}
