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

public class BairroViewHolder_ViewBinding implements Unbinder {
  private BairroViewHolder target;

  @UiThread
  public BairroViewHolder_ViewBinding(BairroViewHolder target, View source) {
    this.target = target;

    target.txtBairro = Utils.findRequiredViewAsType(source, R.id.txtBairro, "field 'txtBairro'", TextView.class);
    target.rlItemBairros = Utils.findRequiredViewAsType(source, R.id.rlItemBairros, "field 'rlItemBairros'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BairroViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtBairro = null;
    target.rlItemBairros = null;
  }
}
