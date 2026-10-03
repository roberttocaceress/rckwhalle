// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityBiometriaCadastro_ViewBinding implements Unbinder {
  private ActivityBiometriaCadastro target;

  @UiThread
  public ActivityBiometriaCadastro_ViewBinding(ActivityBiometriaCadastro target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityBiometriaCadastro_ViewBinding(ActivityBiometriaCadastro target, View source) {
    this.target = target;

    target.btnConfirmar = Utils.findRequiredViewAsType(source, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
    target.edtLogin = Utils.findRequiredViewAsType(source, R.id.edtLogin, "field 'edtLogin'", EditText.class);
    target.edtSenha = Utils.findRequiredViewAsType(source, R.id.edtSenha, "field 'edtSenha'", EditText.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.cadastrar_biometria_toolbar, "field 'toolbar'", Toolbar.class);
    target.progress_main = Utils.findRequiredViewAsType(source, R.id.progress_main, "field 'progress_main'", ProgressBar.class);
    target.progress_main_title = Utils.findRequiredViewAsType(source, R.id.progress_main_title, "field 'progress_main_title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityBiometriaCadastro target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnConfirmar = null;
    target.edtLogin = null;
    target.edtSenha = null;
    target.toolbar = null;
    target.progress_main = null;
    target.progress_main_title = null;
  }
}
