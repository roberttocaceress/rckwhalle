// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CampanhaViewHolder_ViewBinding implements Unbinder {
  private CampanhaViewHolder target;

  @UiThread
  public CampanhaViewHolder_ViewBinding(CampanhaViewHolder target, View source) {
    this.target = target;

    target.txtIdCampanha = Utils.findRequiredViewAsType(source, R.id.txtIdCampanha, "field 'txtIdCampanha'", TextView.class);
    target.txtNomeCampanha = Utils.findRequiredViewAsType(source, R.id.txtNomeCampanha, "field 'txtNomeCampanha'", TextView.class);
    target.txtPeriodo = Utils.findRequiredViewAsType(source, R.id.txtPeriodo, "field 'txtPeriodo'", TextView.class);
    target.txtDescricao = Utils.findRequiredViewAsType(source, R.id.txtDescricao, "field 'txtDescricao'", TextView.class);
    target.layoutCampanha = Utils.findRequiredViewAsType(source, R.id.layoutCampanha, "field 'layoutCampanha'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CampanhaViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtIdCampanha = null;
    target.txtNomeCampanha = null;
    target.txtPeriodo = null;
    target.txtDescricao = null;
    target.layoutCampanha = null;
  }
}
