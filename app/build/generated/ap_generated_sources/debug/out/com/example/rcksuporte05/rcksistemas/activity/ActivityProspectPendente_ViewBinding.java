// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
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

public class ActivityProspectPendente_ViewBinding implements Unbinder {
  private ActivityProspectPendente target;

  private View view7f0a00a6;

  @UiThread
  public ActivityProspectPendente_ViewBinding(ActivityProspectPendente target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProspectPendente_ViewBinding(final ActivityProspectPendente target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.recycleProspect = Utils.findRequiredViewAsType(source, R.id.recycleProspect, "field 'recycleProspect'", RecyclerView.class);
    target.edtTotalProspect = Utils.findRequiredViewAsType(source, R.id.edtTotalProspect, "field 'edtTotalProspect'", EditText.class);
    target.rgFiltaProspect = Utils.findRequiredViewAsType(source, R.id.rgFiltaProspect, "field 'rgFiltaProspect'", RadioGroup.class);
    view = Utils.findRequiredView(source, R.id.btnAddProspect, "method 'novoProspect'");
    view7f0a00a6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.novoProspect();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProspectPendente target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recycleProspect = null;
    target.edtTotalProspect = null;
    target.rgFiltaProspect = null;

    view7f0a00a6.setOnClickListener(null);
    view7f0a00a6 = null;
  }
}
