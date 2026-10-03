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

public class ProspectEnviadoViewHolder_ViewBinding implements Unbinder {
  private ProspectEnviadoViewHolder target;

  @UiThread
  public ProspectEnviadoViewHolder_ViewBinding(ProspectEnviadoViewHolder target, View source) {
    this.target = target;

    target.nomeProspectEnviado = Utils.findRequiredViewAsType(source, R.id.nomeProspectEnviado, "field 'nomeProspectEnviado'", TextView.class);
    target.txtIdProspectEnviado = Utils.findRequiredViewAsType(source, R.id.txtIdProspectEnviado, "field 'txtIdProspectEnviado'", TextView.class);
    target.txtDataRetornoEnviado = Utils.findRequiredViewAsType(source, R.id.txtDataRetornoEnviado, "field 'txtDataRetornoEnviado'", TextView.class);
    target.rlItemListaProspectEnviado = Utils.findRequiredViewAsType(source, R.id.rlItemListaProspectEnviado, "field 'rlItemListaProspectEnviado'", RelativeLayout.class);
    target.imFisicaJuridica = Utils.findRequiredViewAsType(source, R.id.imFisicaJuridica, "field 'imFisicaJuridica'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProspectEnviadoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.nomeProspectEnviado = null;
    target.txtIdProspectEnviado = null;
    target.txtDataRetornoEnviado = null;
    target.rlItemListaProspectEnviado = null;
    target.imFisicaJuridica = null;
  }
}
