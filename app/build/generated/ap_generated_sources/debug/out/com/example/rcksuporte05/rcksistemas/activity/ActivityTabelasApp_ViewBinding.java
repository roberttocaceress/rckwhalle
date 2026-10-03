// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityTabelasApp_ViewBinding implements Unbinder {
  private ActivityTabelasApp target;

  @UiThread
  public ActivityTabelasApp_ViewBinding(ActivityTabelasApp target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityTabelasApp_ViewBinding(ActivityTabelasApp target, View source) {
    this.target = target;

    target.txvTabelas = Utils.findRequiredViewAsType(source, R.id.txvTabelas, "field 'txvTabelas'", TextView.class);
    target.lstvTabelas = Utils.findRequiredViewAsType(source, R.id.lstvTabelas, "field 'lstvTabelas'", ListView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityTabelasApp target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txvTabelas = null;
    target.lstvTabelas = null;
    target.toolbar = null;
  }
}
