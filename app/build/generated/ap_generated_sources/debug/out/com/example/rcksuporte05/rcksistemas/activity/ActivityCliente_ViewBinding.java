// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityCliente_ViewBinding implements Unbinder {
  private ActivityCliente target;

  private View view7f0a00e4;

  @UiThread
  public ActivityCliente_ViewBinding(ActivityCliente target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityCliente_ViewBinding(final ActivityCliente target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaRecycler, "field 'recyclerView'", RecyclerView.class);
    target.edtTotalClientes = Utils.findRequiredViewAsType(source, R.id.edtTotalClientes, "field 'edtTotalClientes'", EditText.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tb_cliente, "field 'toolbar'", Toolbar.class);
    target.rgFiltraCliente = Utils.findRequiredViewAsType(source, R.id.rgFiltraCliente, "field 'rgFiltraCliente'", RadioGroup.class);
    target.filtraTodosClientes = Utils.findRequiredViewAsType(source, R.id.filtraTodosClientes, "field 'filtraTodosClientes'", RadioButton.class);
    target.filtraClientesEfetivados = Utils.findRequiredViewAsType(source, R.id.filtraClientesEfetivados, "field 'filtraClientesEfetivados'", RadioButton.class);
    target.filtraClientesNaoEfetivados = Utils.findRequiredViewAsType(source, R.id.filtraClientesNaoEfetivados, "field 'filtraClientesNaoEfetivados'", RadioButton.class);
    view = Utils.findRequiredView(source, R.id.btnInserirCliente, "field 'btnInserirCliente' and method 'inserirCliente'");
    target.btnInserirCliente = Utils.castView(view, R.id.btnInserirCliente, "field 'btnInserirCliente'", Button.class);
    view7f0a00e4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.inserirCliente();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityCliente target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.edtTotalClientes = null;
    target.toolbar = null;
    target.rgFiltraCliente = null;
    target.filtraTodosClientes = null;
    target.filtraClientesEfetivados = null;
    target.filtraClientesNaoEfetivados = null;
    target.btnInserirCliente = null;

    view7f0a00e4.setOnClickListener(null);
    view7f0a00e4 = null;
  }
}
