// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ContatoViewHolder_ViewBinding implements Unbinder {
  private ContatoViewHolder target;

  @UiThread
  public ContatoViewHolder_ViewBinding(ContatoViewHolder target, View source) {
    this.target = target;

    target.txtNomeResponsavelProspectlist = Utils.findRequiredViewAsType(source, R.id.txtNomeResponsavelProspectlist, "field 'txtNomeResponsavelProspectlist'", TextView.class);
    target.txtFuncaoResponsalveProspectList = Utils.findRequiredViewAsType(source, R.id.txtFuncaoResponsalveProspectList, "field 'txtFuncaoResponsalveProspectList'", TextView.class);
    target.txtCelular1ProspectList = Utils.findRequiredViewAsType(source, R.id.txtCelular1ProspectList, "field 'txtCelular1ProspectList'", TextView.class);
    target.txtEmailProspectLista = Utils.findRequiredViewAsType(source, R.id.txtEmailProspectLista, "field 'txtEmailProspectLista'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContatoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtNomeResponsavelProspectlist = null;
    target.txtFuncaoResponsalveProspectList = null;
    target.txtCelular1ProspectList = null;
    target.txtEmailProspectLista = null;
  }
}
