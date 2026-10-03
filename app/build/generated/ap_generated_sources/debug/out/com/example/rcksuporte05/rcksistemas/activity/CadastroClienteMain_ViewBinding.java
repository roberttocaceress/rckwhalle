// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroClienteMain_ViewBinding implements Unbinder {
  private CadastroClienteMain target;

  @UiThread
  public CadastroClienteMain_ViewBinding(CadastroClienteMain target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CadastroClienteMain_ViewBinding(CadastroClienteMain target, View source) {
    this.target = target;

    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.vp_tabs, "field 'mViewPager'", ViewPager.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbarFrags, "field 'toolbar'", Toolbar.class);
    target.mSlidingTabLayout = Utils.findRequiredViewAsType(source, R.id.stl_tabs, "field 'mSlidingTabLayout'", SlidingTabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroClienteMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.toolbar = null;
    target.mSlidingTabLayout = null;
  }
}
