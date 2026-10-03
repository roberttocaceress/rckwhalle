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

public class ActivityProspectCadastro_ViewBinding implements Unbinder {
  private ActivityProspectCadastro target;

  @UiThread
  public ActivityProspectCadastro_ViewBinding(ActivityProspectCadastro target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProspectCadastro_ViewBinding(ActivityProspectCadastro target, View source) {
    this.target = target;

    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.vp_tabs_prospect, "field 'mViewPager'", ViewPager.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbarFragsProspect, "field 'toolbar'", Toolbar.class);
    target.mSlidingTabLayout = Utils.findRequiredViewAsType(source, R.id.stl_tabs_Prospect, "field 'mSlidingTabLayout'", SlidingTabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProspectCadastro target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.toolbar = null;
    target.mSlidingTabLayout = null;
  }
}
