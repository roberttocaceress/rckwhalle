// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityHistoricoVisitaProspect_ViewBinding implements Unbinder {
  private ActivityHistoricoVisitaProspect target;

  private View view7f0a00aa;

  @UiThread
  public ActivityHistoricoVisitaProspect_ViewBinding(ActivityHistoricoVisitaProspect target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityHistoricoVisitaProspect_ViewBinding(final ActivityHistoricoVisitaProspect target,
      View source) {
    this.target = target;

    View view;
    target.recycleHistoricoVisita = Utils.findRequiredViewAsType(source, R.id.recycleHistoricoVisita, "field 'recycleHistoricoVisita'", RecyclerView.class);
    target.toolbarVisita = Utils.findRequiredViewAsType(source, R.id.toolbarVisita, "field 'toolbarVisita'", Toolbar.class);
    target.edtTotalVisita = Utils.findRequiredViewAsType(source, R.id.edtTotalVisita, "field 'edtTotalVisita'", TextView.class);
    target.imFisicaJuridica = Utils.findRequiredViewAsType(source, R.id.imFisicaJuridica, "field 'imFisicaJuridica'", ImageView.class);
    target.txtNomeProspectVisita = Utils.findRequiredViewAsType(source, R.id.txtDescricaoAcao, "field 'txtNomeProspectVisita'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnAddVisita, "method 'abrirTela'");
    view7f0a00aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.abrirTela();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityHistoricoVisitaProspect target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycleHistoricoVisita = null;
    target.toolbarVisita = null;
    target.edtTotalVisita = null;
    target.imFisicaJuridica = null;
    target.txtNomeProspectVisita = null;

    view7f0a00aa.setOnClickListener(null);
    view7f0a00aa = null;
  }
}
