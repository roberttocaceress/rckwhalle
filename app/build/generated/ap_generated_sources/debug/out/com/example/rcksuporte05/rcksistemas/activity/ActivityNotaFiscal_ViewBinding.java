// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityNotaFiscal_ViewBinding implements Unbinder {
  private ActivityNotaFiscal target;

  @UiThread
  public ActivityNotaFiscal_ViewBinding(ActivityNotaFiscal target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityNotaFiscal_ViewBinding(ActivityNotaFiscal target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tbl_resumo_nota_fiscal, "field 'toolbar'", Toolbar.class);
    target.txvMes = Utils.findRequiredViewAsType(source, R.id.txvMes, "field 'txvMes'", TextView.class);
    target.txvAno = Utils.findRequiredViewAsType(source, R.id.txvAno, "field 'txvAno'", TextView.class);
    target.btnAnterior = Utils.findRequiredViewAsType(source, R.id.img_view_anterior, "field 'btnAnterior'", ImageView.class);
    target.btnProximo = Utils.findRequiredViewAsType(source, R.id.img_view_proximo, "field 'btnProximo'", ImageView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycle_nota_fiscal, "field 'recyclerView'", RecyclerView.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_splash, "field 'progress_bar'", ProgressBar.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
    target.card_view_menu = Utils.findRequiredViewAsType(source, R.id.card_view_menu, "field 'card_view_menu'", CardView.class);
    target.btn_view_menu = Utils.findRequiredViewAsType(source, R.id.img_view_menu, "field 'btn_view_menu'", ImageView.class);
    target.chk_opcao_mes = Utils.findRequiredViewAsType(source, R.id.id_opcao_mes, "field 'chk_opcao_mes'", Switch.class);
    target.chk_opcao_semana = Utils.findRequiredViewAsType(source, R.id.id_opcao_semana, "field 'chk_opcao_semana'", Switch.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityNotaFiscal target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txvMes = null;
    target.txvAno = null;
    target.btnAnterior = null;
    target.btnProximo = null;
    target.recyclerView = null;
    target.progress_bar = null;
    target.progress_title = null;
    target.card_view_menu = null;
    target.btn_view_menu = null;
    target.chk_opcao_mes = null;
    target.chk_opcao_semana = null;
  }
}
