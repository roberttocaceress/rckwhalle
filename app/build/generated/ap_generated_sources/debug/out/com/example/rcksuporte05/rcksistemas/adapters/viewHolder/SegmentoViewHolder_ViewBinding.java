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

public class SegmentoViewHolder_ViewBinding implements Unbinder {
  private SegmentoViewHolder target;

  @UiThread
  public SegmentoViewHolder_ViewBinding(SegmentoViewHolder target, View source) {
    this.target = target;

    target.txtNomeSegmento = Utils.findRequiredViewAsType(source, R.id.txtNomeSegmento, "field 'txtNomeSegmento'", TextView.class);
    target.rlItemSegmentos = Utils.findRequiredViewAsType(source, R.id.rlItemSegmentos, "field 'rlItemSegmentos'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SegmentoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtNomeSegmento = null;
    target.rlItemSegmentos = null;
  }
}
