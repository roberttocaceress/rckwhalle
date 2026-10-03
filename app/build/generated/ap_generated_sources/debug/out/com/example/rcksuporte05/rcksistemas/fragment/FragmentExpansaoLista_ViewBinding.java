// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentExpansaoLista_ViewBinding implements Unbinder {
  private FragmentExpansaoLista target;

  @UiThread
  public FragmentExpansaoLista_ViewBinding(FragmentExpansaoLista target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.rvExpansaoLista, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentExpansaoLista target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
  }
}
