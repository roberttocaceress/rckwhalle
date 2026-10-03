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

public class UFViewHolder_ViewBinding implements Unbinder {
  private UFViewHolder target;

  @UiThread
  public UFViewHolder_ViewBinding(UFViewHolder target, View source) {
    this.target = target;

    target.txtUF = Utils.findRequiredViewAsType(source, R.id.txtUF, "field 'txtUF'", TextView.class);
    target.rlItemUFs = Utils.findRequiredViewAsType(source, R.id.rlItemUFs, "field 'rlItemUFs'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UFViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtUF = null;
    target.rlItemUFs = null;
  }
}
