// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityProspectValida_ViewBinding implements Unbinder {
  private ActivityProspectValida target;

  private View view7f0a00f1;

  private View view7f0a0120;

  @UiThread
  public ActivityProspectValida_ViewBinding(ActivityProspectValida target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProspectValida_ViewBinding(final ActivityProspectValida target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.txtEndereco = Utils.findRequiredViewAsType(source, R.id.txtEndereco, "field 'txtEndereco'", TextView.class);
    target.edtCpfCnpj = Utils.findRequiredViewAsType(source, R.id.edtCpfCnpj, "field 'edtCpfCnpj'", EditText.class);
    target.edtTelefone = Utils.findRequiredViewAsType(source, R.id.edtTelefone, "field 'edtTelefone'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btnLocaliza, "method 'localiza'");
    view7f0a00f1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.localiza();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnVerificar, "method 'verificar'");
    view7f0a0120 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.verificar();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProspectValida target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtEndereco = null;
    target.edtCpfCnpj = null;
    target.edtTelefone = null;

    view7f0a00f1.setOnClickListener(null);
    view7f0a00f1 = null;
    view7f0a0120.setOnClickListener(null);
    view7f0a0120 = null;
  }
}
