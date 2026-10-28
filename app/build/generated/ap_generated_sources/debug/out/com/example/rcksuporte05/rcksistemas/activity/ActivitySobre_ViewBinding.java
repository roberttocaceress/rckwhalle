// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivitySobre_ViewBinding implements Unbinder {
  private ActivitySobre target;

  private View view7f0a03f3;

  private View view7f0a03fa;

  private View view7f0a03de;

  private View view7f0a041b;

  @UiThread
  public ActivitySobre_ViewBinding(ActivitySobre target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivitySobre_ViewBinding(final ActivitySobre target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lyGps, "field 'lyGps' and method 'gps'");
    target.lyGps = Utils.castView(view, R.id.lyGps, "field 'lyGps'", LinearLayout.class);
    view7f0a03f3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.gps();
      }
    });
    view = Utils.findRequiredView(source, R.id.lyMail, "field 'lyMail' and method 'email'");
    target.lyMail = Utils.castView(view, R.id.lyMail, "field 'lyMail'", LinearLayout.class);
    view7f0a03fa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.email();
      }
    });
    view = Utils.findRequiredView(source, R.id.lyChamada, "field 'lyChamada' and method 'chamada'");
    target.lyChamada = Utils.castView(view, R.id.lyChamada, "field 'lyChamada'", LinearLayout.class);
    view7f0a03de = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chamada();
      }
    });
    view = Utils.findRequiredView(source, R.id.lyWhats, "field 'lyWhats' and method 'whats'");
    target.lyWhats = Utils.castView(view, R.id.lyWhats, "field 'lyWhats'", LinearLayout.class);
    view7f0a041b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.whats();
      }
    });
    target.versaoAPP = Utils.findRequiredViewAsType(source, R.id.versaoAPP, "field 'versaoAPP'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivitySobre target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lyGps = null;
    target.lyMail = null;
    target.lyChamada = null;
    target.lyWhats = null;
    target.versaoAPP = null;
    target.toolbar = null;

    view7f0a03f3.setOnClickListener(null);
    view7f0a03f3 = null;
    view7f0a03fa.setOnClickListener(null);
    view7f0a03fa = null;
    view7f0a03de.setOnClickListener(null);
    view7f0a03de = null;
    view7f0a041b.setOnClickListener(null);
    view7f0a041b = null;
  }
}
