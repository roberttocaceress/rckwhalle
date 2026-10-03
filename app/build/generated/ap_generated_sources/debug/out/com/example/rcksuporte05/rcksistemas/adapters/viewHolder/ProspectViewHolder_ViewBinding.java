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

public class ProspectViewHolder_ViewBinding implements Unbinder {
  private ProspectViewHolder target;

  @UiThread
  public ProspectViewHolder_ViewBinding(ProspectViewHolder target, View source) {
    this.target = target;

    target.nomeProspect = Utils.findRequiredViewAsType(source, R.id.nomeProspect, "field 'nomeProspect'", TextView.class);
    target.txtDataRetorno = Utils.findRequiredViewAsType(source, R.id.txtDataRetorno, "field 'txtDataRetorno'", TextView.class);
    target.textViewNomeFantasia = Utils.findRequiredViewAsType(source, R.id.txtRazaoSocial, "field 'textViewNomeFantasia'", TextView.class);
    target.prospectSalvo = Utils.findRequiredViewAsType(source, R.id.prospectSalvo, "field 'prospectSalvo'", ImageView.class);
    target.txtIdProspect = Utils.findRequiredViewAsType(source, R.id.txtIdProspect, "field 'txtIdProspect'", TextView.class);
    target.itemListaProspect = Utils.findRequiredViewAsType(source, R.id.itemListaProspect, "field 'itemListaProspect'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProspectViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.nomeProspect = null;
    target.txtDataRetorno = null;
    target.textViewNomeFantasia = null;
    target.prospectSalvo = null;
    target.txtIdProspect = null;
    target.itemListaProspect = null;
  }
}
