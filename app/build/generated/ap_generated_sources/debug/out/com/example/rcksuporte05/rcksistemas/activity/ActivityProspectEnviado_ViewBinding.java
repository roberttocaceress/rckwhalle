// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityProspectEnviado_ViewBinding implements Unbinder {
  private ActivityProspectEnviado target;

  @UiThread
  public ActivityProspectEnviado_ViewBinding(ActivityProspectEnviado target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProspectEnviado_ViewBinding(ActivityProspectEnviado target, View source) {
    this.target = target;

    target.recyclerProspectsEnviado = Utils.findRequiredViewAsType(source, R.id.recyclerProspectsEnviado, "field 'recyclerProspectsEnviado'", RecyclerView.class);
    target.toolbarProspectEnviado = Utils.findRequiredViewAsType(source, R.id.toolbarProspectEnviado, "field 'toolbarProspectEnviado'", Toolbar.class);
    target.edtTotalProspectEnviado = Utils.findRequiredViewAsType(source, R.id.edtTotalProspectEnviado, "field 'edtTotalProspectEnviado'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProspectEnviado target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerProspectsEnviado = null;
    target.toolbarProspectEnviado = null;
    target.edtTotalProspectEnviado = null;
  }
}
