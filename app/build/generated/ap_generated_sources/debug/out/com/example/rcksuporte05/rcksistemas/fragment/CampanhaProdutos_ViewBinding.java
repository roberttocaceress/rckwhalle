// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CampanhaProdutos_ViewBinding implements Unbinder {
  private CampanhaProdutos target;

  @UiThread
  public CampanhaProdutos_ViewBinding(CampanhaProdutos target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycleCampanhaProdutos, "field 'recyclerView'", RecyclerView.class);
    target.edtTotalProdutos = Utils.findRequiredViewAsType(source, R.id.edtTotalProdutos, "field 'edtTotalProdutos'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CampanhaProdutos target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.edtTotalProdutos = null;
  }
}
