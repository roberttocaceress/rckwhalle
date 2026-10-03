// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NovidadeViewHolder_ViewBinding implements Unbinder {
  private NovidadeViewHolder target;

  @UiThread
  public NovidadeViewHolder_ViewBinding(NovidadeViewHolder target, View source) {
    this.target = target;

    target.txvTitulo = Utils.findRequiredViewAsType(source, R.id.txvTitulo, "field 'txvTitulo'", TextView.class);
    target.btnShow = Utils.findRequiredViewAsType(source, R.id.btnShow, "field 'btnShow'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NovidadeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txvTitulo = null;
    target.btnShow = null;
  }
}
