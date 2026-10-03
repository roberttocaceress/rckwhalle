// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityDialogEstoqueSaldo_ViewBinding implements Unbinder {
  private ActivityDialogEstoqueSaldo target;

  @UiThread
  public ActivityDialogEstoqueSaldo_ViewBinding(ActivityDialogEstoqueSaldo target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityDialogEstoqueSaldo_ViewBinding(ActivityDialogEstoqueSaldo target, View source) {
    this.target = target;

    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityDialogEstoqueSaldo target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnConfirmar = null;
  }
}
