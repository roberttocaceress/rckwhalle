// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityBIHub_ViewBinding implements Unbinder {
  private ActivityBIHub target;

  @UiThread
  public ActivityBIHub_ViewBinding(ActivityBIHub target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityBIHub_ViewBinding(ActivityBIHub target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.cardAnalitico = Utils.findRequiredViewAsType(source, R.id.cardAnalitico, "field 'cardAnalitico'", MaterialCardView.class);
    target.cardFinanceiro = Utils.findRequiredViewAsType(source, R.id.cardFinanceiro, "field 'cardFinanceiro'", MaterialCardView.class);
    target.cardPerformance = Utils.findRequiredViewAsType(source, R.id.cardPerformance, "field 'cardPerformance'", MaterialCardView.class);
    target.cardGestaoMetas = Utils.findRequiredViewAsType(source, R.id.cardGestaoMetas, "field 'cardGestaoMetas'", MaterialCardView.class);
    target.cardGestaoVisitas = Utils.findRequiredViewAsType(source, R.id.cardGestaoVisitas, "field 'cardGestaoVisitas'", MaterialCardView.class);
    target.cardRankingEquipe = Utils.findRequiredViewAsType(source, R.id.cardRankingEquipe, "field 'cardRankingEquipe'", MaterialCardView.class);
    target.cardExpansao = Utils.findRequiredViewAsType(source, R.id.cardExpansao, "field 'cardExpansao'", MaterialCardView.class);
    target.tvSaudacao = Utils.findRequiredViewAsType(source, R.id.tvSaudacao, "field 'tvSaudacao'", TextView.class);
    target.tvValorMesHub = Utils.findRequiredViewAsType(source, R.id.tvValorMesHub, "field 'tvValorMesHub'", TextView.class);
    target.tvPercPerformanceHub = Utils.findRequiredViewAsType(source, R.id.tvPercPerformanceHub, "field 'tvPercPerformanceHub'", TextView.class);
    target.tvNovosClientesHub = Utils.findRequiredViewAsType(source, R.id.tvNovosClientesHub, "field 'tvNovosClientesHub'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityBIHub target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.cardAnalitico = null;
    target.cardFinanceiro = null;
    target.cardPerformance = null;
    target.cardGestaoMetas = null;
    target.cardGestaoVisitas = null;
    target.cardRankingEquipe = null;
    target.cardExpansao = null;
    target.tvSaudacao = null;
    target.tvValorMesHub = null;
    target.tvPercPerformanceHub = null;
    target.tvNovosClientesHub = null;
  }
}
