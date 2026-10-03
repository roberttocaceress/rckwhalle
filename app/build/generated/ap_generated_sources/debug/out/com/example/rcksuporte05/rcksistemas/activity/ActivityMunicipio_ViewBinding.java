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

public class ActivityMunicipio_ViewBinding implements Unbinder {
  private ActivityMunicipio target;

  @UiThread
  public ActivityMunicipio_ViewBinding(ActivityMunicipio target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityMunicipio_ViewBinding(ActivityMunicipio target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaMunicipio, "field 'recyclerView'", RecyclerView.class);
    target.toolbarMunicipio = Utils.findRequiredViewAsType(source, R.id.toolbarMunicipio, "field 'toolbarMunicipio'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityMunicipio target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.toolbarMunicipio = null;
  }
}
