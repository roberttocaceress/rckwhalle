// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPreferencia_ViewBinding implements Unbinder {
  private ActivityPreferencia target;

  @UiThread
  public ActivityPreferencia_ViewBinding(ActivityPreferencia target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPreferencia_ViewBinding(ActivityPreferencia target, View source) {
    this.target = target;

    target.swit_venda_1 = Utils.findRequiredViewAsType(source, R.id.swit_venda_1, "field 'swit_venda_1'", Switch.class);
    target.swit_venda_2 = Utils.findRequiredViewAsType(source, R.id.swit_venda_2, "field 'swit_venda_2'", Switch.class);
    target.swit_venda_3 = Utils.findRequiredViewAsType(source, R.id.swit_venda_3, "field 'swit_venda_3'", Switch.class);
    target.swit_venda_4 = Utils.findRequiredViewAsType(source, R.id.swit_venda_4, "field 'swit_venda_4'", Switch.class);
    target.swit_venda_5 = Utils.findRequiredViewAsType(source, R.id.swit_venda_5, "field 'swit_venda_5'", Switch.class);
    target.swit_venda_6 = Utils.findRequiredViewAsType(source, R.id.swit_venda_6, "field 'swit_venda_6'", Switch.class);
    target.swit_fin_1 = Utils.findRequiredViewAsType(source, R.id.swit_fin_1, "field 'swit_fin_1'", Switch.class);
    target.swit_fin_2 = Utils.findRequiredViewAsType(source, R.id.swit_fin_2, "field 'swit_fin_2'", Switch.class);
    target.swit_fin_3 = Utils.findRequiredViewAsType(source, R.id.swit_fin_3, "field 'swit_fin_3'", Switch.class);
    target.swit_fin_4 = Utils.findRequiredViewAsType(source, R.id.swit_fin_4, "field 'swit_fin_4'", Switch.class);
    target.swit_fin_5 = Utils.findRequiredViewAsType(source, R.id.swit_fin_5, "field 'swit_fin_5'", Switch.class);
    target.swit_fin_6 = Utils.findRequiredViewAsType(source, R.id.swit_fin_6, "field 'swit_fin_6'", Switch.class);
    target.swit_ped_1 = Utils.findRequiredViewAsType(source, R.id.swit_ped_1, "field 'swit_ped_1'", Switch.class);
    target.swit_ped_2 = Utils.findRequiredViewAsType(source, R.id.swit_ped_2, "field 'swit_ped_2'", Switch.class);
    target.swit_ped_3 = Utils.findRequiredViewAsType(source, R.id.swit_ped_3, "field 'swit_ped_3'", Switch.class);
    target.swit_ped_4 = Utils.findRequiredViewAsType(source, R.id.swit_ped_4, "field 'swit_ped_4'", Switch.class);
    target.swit_ped_5 = Utils.findRequiredViewAsType(source, R.id.swit_ped_5, "field 'swit_ped_5'", Switch.class);
    target.swit_ped_6 = Utils.findRequiredViewAsType(source, R.id.swit_ped_6, "field 'swit_ped_6'", Switch.class);
    target.btnCancelar = Utils.findRequiredViewAsType(source, R.id.btnCancelar, "field 'btnCancelar'", Button.class);
    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPreferencia target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swit_venda_1 = null;
    target.swit_venda_2 = null;
    target.swit_venda_3 = null;
    target.swit_venda_4 = null;
    target.swit_venda_5 = null;
    target.swit_venda_6 = null;
    target.swit_fin_1 = null;
    target.swit_fin_2 = null;
    target.swit_fin_3 = null;
    target.swit_fin_4 = null;
    target.swit_fin_5 = null;
    target.swit_fin_6 = null;
    target.swit_ped_1 = null;
    target.swit_ped_2 = null;
    target.swit_ped_3 = null;
    target.swit_ped_4 = null;
    target.swit_ped_5 = null;
    target.swit_ped_6 = null;
    target.btnCancelar = null;
    target.btnConfirmar = null;
  }
}
