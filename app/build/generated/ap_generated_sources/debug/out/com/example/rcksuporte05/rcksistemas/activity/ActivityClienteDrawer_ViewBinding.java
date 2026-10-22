// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityClienteDrawer_ViewBinding implements Unbinder {
  private ActivityClienteDrawer target;

  private View view7f0a00de;

  private View view7f0a00d7;

  @UiThread
  public ActivityClienteDrawer_ViewBinding(ActivityClienteDrawer target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityClienteDrawer_ViewBinding(final ActivityClienteDrawer target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaRecycler, "field 'recyclerView'", RecyclerView.class);
    target.edtTotalClientes = Utils.findRequiredViewAsType(source, R.id.edtTotalClientes, "field 'edtTotalClientes'", EditText.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tb_cliente, "field 'toolbar'", Toolbar.class);
    target.buscaCliente = Utils.findRequiredViewAsType(source, R.id.buscaCliente, "field 'buscaCliente'", SearchView.class);
    target.helpCliente = Utils.findRequiredViewAsType(source, R.id.helpCliente, "field 'helpCliente'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.btnInserirCliente, "field 'btnInserirCliente' and method 'inserirCliente'");
    target.btnInserirCliente = Utils.castView(view, R.id.btnInserirCliente, "field 'btnInserirCliente'", Button.class);
    view7f0a00de = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.inserirCliente();
      }
    });
    target.lytInserirCliente = Utils.findRequiredViewAsType(source, R.id.lytInserirCliente, "field 'lytInserirCliente'", LinearLayout.class);
    target.cardFinalizarInclusao = Utils.findRequiredViewAsType(source, R.id.cardFinalizarInclusao, "field 'cardFinalizarInclusao'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btnFinalizarInclusao, "field 'btnFinalizarInclusao' and method 'btnFinalizarInclusao'");
    target.btnFinalizarInclusao = Utils.castView(view, R.id.btnFinalizarInclusao, "field 'btnFinalizarInclusao'", Button.class);
    view7f0a00d7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnFinalizarInclusao();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityClienteDrawer target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.edtTotalClientes = null;
    target.toolbar = null;
    target.buscaCliente = null;
    target.helpCliente = null;
    target.btnInserirCliente = null;
    target.lytInserirCliente = null;
    target.cardFinalizarInclusao = null;
    target.btnFinalizarInclusao = null;

    view7f0a00de.setOnClickListener(null);
    view7f0a00de = null;
    view7f0a00d7.setOnClickListener(null);
    view7f0a00d7 = null;
  }
}
