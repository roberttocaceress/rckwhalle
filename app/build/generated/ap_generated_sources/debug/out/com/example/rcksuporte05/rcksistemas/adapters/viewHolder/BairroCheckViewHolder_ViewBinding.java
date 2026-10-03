// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BairroCheckViewHolder_ViewBinding implements Unbinder {
  private BairroCheckViewHolder target;

  @UiThread
  public BairroCheckViewHolder_ViewBinding(BairroCheckViewHolder target, View source) {
    this.target = target;

    target.txtBairro = Utils.findRequiredViewAsType(source, R.id.txvBairro, "field 'txtBairro'", TextView.class);
    target.chkBairro = Utils.findRequiredViewAsType(source, R.id.chkBairro, "field 'chkBairro'", CheckBox.class);
    target.lytBairro = Utils.findRequiredViewAsType(source, R.id.lytBairro, "field 'lytBairro'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BairroCheckViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtBairro = null;
    target.chkBairro = null;
    target.lytBairro = null;
  }
}
