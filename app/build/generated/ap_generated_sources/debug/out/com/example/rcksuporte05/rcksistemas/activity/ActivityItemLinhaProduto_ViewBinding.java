// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityItemLinhaProduto_ViewBinding implements Unbinder {
  private ActivityItemLinhaProduto target;

  @UiThread
  public ActivityItemLinhaProduto_ViewBinding(ActivityItemLinhaProduto target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityItemLinhaProduto_ViewBinding(ActivityItemLinhaProduto target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycleLinhaProdutos, "field 'recyclerView'", RecyclerView.class);
    target.edtTotalProdutos = Utils.findRequiredViewAsType(source, R.id.edtTotalProdutos, "field 'edtTotalProdutos'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityItemLinhaProduto target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recyclerView = null;
    target.edtTotalProdutos = null;
  }
}
