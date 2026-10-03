// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityProdutoImagemZoom_ViewBinding implements Unbinder {
  private ActivityProdutoImagemZoom target;

  @UiThread
  public ActivityProdutoImagemZoom_ViewBinding(ActivityProdutoImagemZoom target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProdutoImagemZoom_ViewBinding(ActivityProdutoImagemZoom target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_prod_imagem_zoom, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProdutoImagemZoom target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
  }
}
