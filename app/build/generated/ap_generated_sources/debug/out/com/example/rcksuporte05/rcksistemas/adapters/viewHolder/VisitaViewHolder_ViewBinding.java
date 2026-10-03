// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VisitaViewHolder_ViewBinding implements Unbinder {
  private VisitaViewHolder target;

  @UiThread
  public VisitaViewHolder_ViewBinding(VisitaViewHolder target, View source) {
    this.target = target;

    target.rlItemVisita = Utils.findRequiredViewAsType(source, R.id.rlItemVisita, "field 'rlItemVisita'", RelativeLayout.class);
    target.txtDescricaoVisita = Utils.findRequiredViewAsType(source, R.id.txtDescricaoAcao, "field 'txtDescricaoVisita'", TextView.class);
    target.txtDataVisita = Utils.findRequiredViewAsType(source, R.id.txtDataVisita, "field 'txtDataVisita'", TextView.class);
    target.visitaSalvo = Utils.findRequiredViewAsType(source, R.id.visitaSalvo, "field 'visitaSalvo'", ImageView.class);
    target.idVisitaProspect = Utils.findRequiredViewAsType(source, R.id.idVisitaProspect, "field 'idVisitaProspect'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VisitaViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rlItemVisita = null;
    target.txtDescricaoVisita = null;
    target.txtDataVisita = null;
    target.visitaSalvo = null;
    target.idVisitaProspect = null;
  }
}
