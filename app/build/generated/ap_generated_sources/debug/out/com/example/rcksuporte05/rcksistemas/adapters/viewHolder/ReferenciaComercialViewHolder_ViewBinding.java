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

public class ReferenciaComercialViewHolder_ViewBinding implements Unbinder {
  private ReferenciaComercialViewHolder target;

  @UiThread
  public ReferenciaComercialViewHolder_ViewBinding(ReferenciaComercialViewHolder target,
      View source) {
    this.target = target;

    target.rlItemREferenciaComercial = Utils.findRequiredViewAsType(source, R.id.rlItemReferenciaComercial, "field 'rlItemREferenciaComercial'", RelativeLayout.class);
    target.txtNomeFornecedorProspectlist = Utils.findRequiredViewAsType(source, R.id.txtNomeFornecedorProspectlist, "field 'txtNomeFornecedorProspectlist'", TextView.class);
    target.txtTelefoneFornecedorProspectList = Utils.findRequiredViewAsType(source, R.id.txtTelefoneFornecedorProspectList, "field 'txtTelefoneFornecedorProspectList'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReferenciaComercialViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rlItemREferenciaComercial = null;
    target.txtNomeFornecedorProspectlist = null;
    target.txtTelefoneFornecedorProspectList = null;
  }
}
