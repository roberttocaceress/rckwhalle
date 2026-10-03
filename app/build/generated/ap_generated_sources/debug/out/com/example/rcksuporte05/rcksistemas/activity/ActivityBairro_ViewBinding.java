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

public class ActivityBairro_ViewBinding implements Unbinder {
  private ActivityBairro target;

  @UiThread
  public ActivityBairro_ViewBinding(ActivityBairro target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityBairro_ViewBinding(ActivityBairro target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaBairro, "field 'recyclerView'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbarBairro, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityBairro target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.toolbar = null;
  }
}
