// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MotivoViewHolder_ViewBinding implements Unbinder {
  private MotivoViewHolder target;

  @UiThread
  public MotivoViewHolder_ViewBinding(MotivoViewHolder target, View source) {
    this.target = target;

    target.txtMotivo = Utils.findRequiredViewAsType(source, R.id.txtMotivo, "field 'txtMotivo'", TextView.class);
    target.rlItemMotivo = Utils.findRequiredViewAsType(source, R.id.rlItemMotivos, "field 'rlItemMotivo'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MotivoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtMotivo = null;
    target.rlItemMotivo = null;
  }
}
