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

public class ReferenciaBancariaViewHolder_ViewBinding implements Unbinder {
  private ReferenciaBancariaViewHolder target;

  @UiThread
  public ReferenciaBancariaViewHolder_ViewBinding(ReferenciaBancariaViewHolder target,
      View source) {
    this.target = target;

    target.txtBancoProspectList = Utils.findRequiredViewAsType(source, R.id.txtBancoProspectList, "field 'txtBancoProspectList'", TextView.class);
    target.txtContaCorrenteList = Utils.findRequiredViewAsType(source, R.id.txtContaCorrenteList, "field 'txtContaCorrenteList'", TextView.class);
    target.txtAgenciaProspectList = Utils.findRequiredViewAsType(source, R.id.txtAgenciaProspectList, "field 'txtAgenciaProspectList'", TextView.class);
    target.rlItemReferenciaBancaria = Utils.findRequiredViewAsType(source, R.id.rlItemReferenciaBancaria, "field 'rlItemReferenciaBancaria'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReferenciaBancariaViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtBancoProspectList = null;
    target.txtContaCorrenteList = null;
    target.txtAgenciaProspectList = null;
    target.rlItemReferenciaBancaria = null;
  }
}
