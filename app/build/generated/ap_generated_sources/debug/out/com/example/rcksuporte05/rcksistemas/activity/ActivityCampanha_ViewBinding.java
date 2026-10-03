// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityCampanha_ViewBinding implements Unbinder {
  private ActivityCampanha target;

  @UiThread
  public ActivityCampanha_ViewBinding(ActivityCampanha target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityCampanha_ViewBinding(ActivityCampanha target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycleCampanha, "field 'recyclerView'", RecyclerView.class);
    target.edtTotalCampanhas = Utils.findRequiredViewAsType(source, R.id.edtTotalCampanhas, "field 'edtTotalCampanhas'", EditText.class);
    target.txtDescricao = Utils.findRequiredViewAsType(source, R.id.txtDescricao, "field 'txtDescricao'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityCampanha target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recyclerView = null;
    target.edtTotalCampanhas = null;
    target.txtDescricao = null;
  }
}
