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

public class MunicipioViewHolder_ViewBinding implements Unbinder {
  private MunicipioViewHolder target;

  @UiThread
  public MunicipioViewHolder_ViewBinding(MunicipioViewHolder target, View source) {
    this.target = target;

    target.txtMunicipio = Utils.findRequiredViewAsType(source, R.id.txtMunicipio, "field 'txtMunicipio'", TextView.class);
    target.rlItemMunicipios = Utils.findRequiredViewAsType(source, R.id.rlItemMunicipios, "field 'rlItemMunicipios'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MunicipioViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtMunicipio = null;
    target.rlItemMunicipios = null;
  }
}
