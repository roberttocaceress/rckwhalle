// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityProdutoImagem_ViewBinding implements Unbinder {
  private ActivityProdutoImagem target;

  @UiThread
  public ActivityProdutoImagem_ViewBinding(ActivityProdutoImagem target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProdutoImagem_ViewBinding(ActivityProdutoImagem target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.tv_current_matrix = Utils.findRequiredViewAsType(source, R.id.tv_current_matrix, "field 'tv_current_matrix'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProdutoImagem target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tv_current_matrix = null;
  }
}
