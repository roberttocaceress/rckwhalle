// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivitySemCobranca_ViewBinding implements Unbinder {
  private ActivitySemCobranca target;

  @UiThread
  public ActivitySemCobranca_ViewBinding(ActivitySemCobranca target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivitySemCobranca_ViewBinding(ActivitySemCobranca target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tbl_cobranca, "field 'toolbar'", Toolbar.class);
    target.buscaCobranca = Utils.findRequiredViewAsType(source, R.id.buscaCobranca, "field 'buscaCobranca'", SearchView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycle_cobranca, "field 'recyclerView'", RecyclerView.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_splash, "field 'progress_bar'", ProgressBar.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
    target.card_view_menu = Utils.findRequiredViewAsType(source, R.id.card_view_menu, "field 'card_view_menu'", CardView.class);
    target.btn_view_avancar = Utils.findRequiredViewAsType(source, R.id.img_view_avancar, "field 'btn_view_avancar'", ImageView.class);
    target.btn_view_menu = Utils.findRequiredViewAsType(source, R.id.img_view_menu, "field 'btn_view_menu'", ImageView.class);
    target.sp_estado = Utils.findRequiredViewAsType(source, R.id.sp_estado, "field 'sp_estado'", Spinner.class);
    target.sp_cidade = Utils.findRequiredViewAsType(source, R.id.sp_cidade, "field 'sp_cidade'", Spinner.class);
    target.sp_bairro = Utils.findRequiredViewAsType(source, R.id.sp_bairro, "field 'sp_bairro'", Spinner.class);
    target.sp_regiao = Utils.findRequiredViewAsType(source, R.id.sp_regiao, "field 'sp_regiao'", Spinner.class);
    target.sp_conta_bancaria = Utils.findRequiredViewAsType(source, R.id.sp_conta_bancaria, "field 'sp_conta_bancaria'", Spinner.class);
    target.sp_moeda = Utils.findRequiredViewAsType(source, R.id.sp_moeda, "field 'sp_moeda'", Spinner.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivitySemCobranca target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.buscaCobranca = null;
    target.recyclerView = null;
    target.progress_bar = null;
    target.progress_title = null;
    target.card_view_menu = null;
    target.btn_view_avancar = null;
    target.btn_view_menu = null;
    target.sp_estado = null;
    target.sp_cidade = null;
    target.sp_bairro = null;
    target.sp_regiao = null;
    target.sp_conta_bancaria = null;
    target.sp_moeda = null;
  }
}
