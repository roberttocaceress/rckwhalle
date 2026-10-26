// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityAlerta_ViewBinding implements Unbinder {
  private ActivityAlerta target;

  private View view7f0a00c3;

  @UiThread
  public ActivityAlerta_ViewBinding(ActivityAlerta target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityAlerta_ViewBinding(final ActivityAlerta target, View source) {
    this.target = target;

    View view;
    target.swit_emissao_doc = Utils.findRequiredViewAsType(source, R.id.swit_emissao_doc, "field 'swit_emissao_doc'", Switch.class);
    target.swit_vencimento_doc = Utils.findRequiredViewAsType(source, R.id.swit_vencimento_doc, "field 'swit_vencimento_doc'", Switch.class);
    target.swit_novo_app = Utils.findRequiredViewAsType(source, R.id.swit_novo_app, "field 'swit_novo_app'", Switch.class);
    target.btnCancelar = Utils.findRequiredViewAsType(source, R.id.btnCancelar, "field 'btnCancelar'", Button.class);
    view = Utils.findRequiredView(source, R.id.btnConfirmar, "field 'btnConfirmar' and method 'addAlerta'");
    target.btnConfirmar = Utils.castView(view, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
    view7f0a00c3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addAlerta();
      }
    });
    target.txvAlerta = Utils.findRequiredViewAsType(source, R.id.txvAlerta, "field 'txvAlerta'", TextView.class);
    target.txvFirebaseKey = Utils.findRequiredViewAsType(source, R.id.txvFirebaseKey, "field 'txvFirebaseKey'", EditText.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_ajuda, "field 'progress_bar'", ProgressBar.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityAlerta target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swit_emissao_doc = null;
    target.swit_vencimento_doc = null;
    target.swit_novo_app = null;
    target.btnCancelar = null;
    target.btnConfirmar = null;
    target.txvAlerta = null;
    target.txvFirebaseKey = null;
    target.progress_bar = null;
    target.progress_title = null;

    view7f0a00c3.setOnClickListener(null);
    view7f0a00c3 = null;
  }
}
