// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityHistoricoFinanceiroTKS_ViewBinding implements Unbinder {
  private ActivityHistoricoFinanceiroTKS target;

  @UiThread
  public ActivityHistoricoFinanceiroTKS_ViewBinding(ActivityHistoricoFinanceiroTKS target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityHistoricoFinanceiroTKS_ViewBinding(ActivityHistoricoFinanceiroTKS target,
      View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.btnFiltrar = Utils.findRequiredViewAsType(source, R.id.btnFiltrar, "field 'btnFiltrar'", Button.class);
    target.btnFiltrar2 = Utils.findRequiredViewAsType(source, R.id.btnFiltrar2, "field 'btnFiltrar2'", Button.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewPager, "field 'viewPager'", ViewPager2.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabLayout, "field 'tabLayout'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityHistoricoFinanceiroTKS target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.btnFiltrar = null;
    target.btnFiltrar2 = null;
    target.viewPager = null;
    target.tabLayout = null;
  }
}
