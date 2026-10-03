// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroCliente8_ViewBinding implements Unbinder {
  private CadastroCliente8 target;

  private View view7f0a00a1;

  private View view7f0a00a0;

  @UiThread
  public CadastroCliente8_ViewBinding(final CadastroCliente8 target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerAnexo, "field 'recyclerView'", RecyclerView.class);
    target.edtTotalAnexos = Utils.findRequiredViewAsType(source, R.id.edtTotalAnexos, "field 'edtTotalAnexos'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnAddAnexos, "field 'btnAddAnexos' and method 'addAnexo'");
    target.btnAddAnexos = Utils.castView(view, R.id.btnAddAnexos, "field 'btnAddAnexos'", FloatingActionButton.class);
    view7f0a00a1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addAnexo();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAddAnexCam, "field 'btnAddAnexCam' and method 'addAnexoCam'");
    target.btnAddAnexCam = Utils.castView(view, R.id.btnAddAnexCam, "field 'btnAddAnexCam'", FloatingActionButton.class);
    view7f0a00a0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addAnexoCam();
      }
    });
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroCliente8 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.edtTotalAnexos = null;
    target.btnAddAnexos = null;
    target.btnAddAnexCam = null;
    target.btnContinuar = null;

    view7f0a00a1.setOnClickListener(null);
    view7f0a00a1 = null;
    view7f0a00a0.setOnClickListener(null);
    view7f0a00a0 = null;
  }
}
