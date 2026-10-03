// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityFoto_ViewBinding implements Unbinder {
  private ActivityFoto target;

  @UiThread
  public ActivityFoto_ViewBinding(ActivityFoto target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityFoto_ViewBinding(ActivityFoto target, View source) {
    this.target = target;

    target.tb_anexo_foto = Utils.findRequiredViewAsType(source, R.id.tb_anexo_foto, "field 'tb_anexo_foto'", Toolbar.class);
    target.edtAnexoFoto = Utils.findRequiredViewAsType(source, R.id.edtAnexoFoto, "field 'edtAnexoFoto'", EditText.class);
    target.imAnexoFoto = Utils.findRequiredViewAsType(source, R.id.imAnexoFoto, "field 'imAnexoFoto'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityFoto target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tb_anexo_foto = null;
    target.edtAnexoFoto = null;
    target.imAnexoFoto = null;
  }
}
