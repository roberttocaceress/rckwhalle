// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityAnexoCadastro_ViewBinding implements Unbinder {
  private ActivityAnexoCadastro target;

  private View view7f0a031b;

  @UiThread
  public ActivityAnexoCadastro_ViewBinding(ActivityAnexoCadastro target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityAnexoCadastro_ViewBinding(final ActivityAnexoCadastro target, View source) {
    this.target = target;

    View view;
    target.tb_anexo = Utils.findRequiredViewAsType(source, R.id.tb_anexo, "field 'tb_anexo'", Toolbar.class);
    target.edtAnexo = Utils.findRequiredViewAsType(source, R.id.edtAnexo, "field 'edtAnexo'", EditText.class);
    view = Utils.findRequiredView(source, R.id.imAnexo, "field 'imgAnexo' and method 'showGaleria'");
    target.imgAnexo = Utils.castView(view, R.id.imAnexo, "field 'imgAnexo'", ImageView.class);
    view7f0a031b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showGaleria();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityAnexoCadastro target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tb_anexo = null;
    target.edtAnexo = null;
    target.imgAnexo = null;

    view7f0a031b.setOnClickListener(null);
    view7f0a031b = null;
  }
}
