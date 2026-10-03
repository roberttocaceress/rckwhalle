// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityEstado_ViewBinding implements Unbinder {
  private ActivityEstado target;

  @UiThread
  public ActivityEstado_ViewBinding(ActivityEstado target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityEstado_ViewBinding(ActivityEstado target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaUF, "field 'recyclerView'", RecyclerView.class);
    target.toolbarUf = Utils.findRequiredViewAsType(source, R.id.toolbarUf, "field 'toolbarUf'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityEstado target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.toolbarUf = null;
  }
}
