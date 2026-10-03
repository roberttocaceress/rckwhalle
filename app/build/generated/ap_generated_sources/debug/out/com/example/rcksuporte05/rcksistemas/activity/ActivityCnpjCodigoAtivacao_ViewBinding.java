// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityCnpjCodigoAtivacao_ViewBinding implements Unbinder {
  private ActivityCnpjCodigoAtivacao target;

  @UiThread
  public ActivityCnpjCodigoAtivacao_ViewBinding(ActivityCnpjCodigoAtivacao target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityCnpjCodigoAtivacao_ViewBinding(ActivityCnpjCodigoAtivacao target, View source) {
    this.target = target;

    target.edtCnpjCpf = Utils.findRequiredViewAsType(source, R.id.edtCnpjCpf, "field 'edtCnpjCpf'", EditText.class);
    target.edtCodigo = Utils.findRequiredViewAsType(source, R.id.edtCodigo, "field 'edtCodigo'", EditText.class);
    target.btnValida = Utils.findRequiredViewAsType(source, R.id.btnValida, "field 'btnValida'", Button.class);
    target.btnCancela = Utils.findRequiredViewAsType(source, R.id.btnCancela, "field 'btnCancela'", Button.class);
    target.shimmerLayout = Utils.findRequiredViewAsType(source, R.id.shimmerLayout, "field 'shimmerLayout'", ShimmerFrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityCnpjCodigoAtivacao target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtCnpjCpf = null;
    target.edtCodigo = null;
    target.btnValida = null;
    target.btnCancela = null;
    target.shimmerLayout = null;
  }
}
