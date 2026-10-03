// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class ActivityEstoqueSaldo_ViewBinding implements Unbinder {
  private ActivityEstoqueSaldo target;

  @UiThread
  public ActivityEstoqueSaldo_ViewBinding(ActivityEstoqueSaldo target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityEstoqueSaldo_ViewBinding(ActivityEstoqueSaldo target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tb_estoque_saldo, "field 'toolbar'", Toolbar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaEstoqueRecyclerView, "field 'recyclerView'", RecyclerView.class);
    target.edt_nome_produto = Utils.findRequiredViewAsType(source, R.id.edt_nome_produto, "field 'edt_nome_produto'", EditText.class);
    target.edt_id_produto = Utils.findRequiredViewAsType(source, R.id.edt_codigo_produto, "field 'edt_id_produto'", EditText.class);
    target.edt_ean_produto = Utils.findRequiredViewAsType(source, R.id.edt_ean_produto, "field 'edt_ean_produto'", EditText.class);
    target.btn_view_avancar = Utils.findRequiredViewAsType(source, R.id.img_view_avancar, "field 'btn_view_avancar'", ImageView.class);
    target.btn_view_menu = Utils.findRequiredViewAsType(source, R.id.img_view_menu, "field 'btn_view_menu'", ImageView.class);
    target.card_view_menu = Utils.findRequiredViewAsType(source, R.id.card_view_menu, "field 'card_view_menu'", CardView.class);
    target.chk_status_todos = Utils.findRequiredViewAsType(source, R.id.chk_status_todos, "field 'chk_status_todos'", CheckBox.class);
    target.chk_status_ativo = Utils.findRequiredViewAsType(source, R.id.chk_status_ativo, "field 'chk_status_ativo'", CheckBox.class);
    target.chk_status_inativo = Utils.findRequiredViewAsType(source, R.id.chk_status_inativo, "field 'chk_status_inativo'", CheckBox.class);
    target.sp_deposito = Utils.findRequiredViewAsType(source, R.id.sp_deposito, "field 'sp_deposito'", Spinner.class);
    target.sp_grupo = Utils.findRequiredViewAsType(source, R.id.sp_grupo, "field 'sp_grupo'", Spinner.class);
    target.txv_grupo = Utils.findRequiredViewAsType(source, R.id.txv_grupo, "field 'txv_grupo'", TextView.class);
    target.helpEstoqueSaldo = Utils.findRequiredViewAsType(source, R.id.helpEstoqueSaldo, "field 'helpEstoqueSaldo'", ImageView.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_splash, "field 'progress_bar'", ProgressBar.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityEstoqueSaldo target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recyclerView = null;
    target.edt_nome_produto = null;
    target.edt_id_produto = null;
    target.edt_ean_produto = null;
    target.btn_view_avancar = null;
    target.btn_view_menu = null;
    target.card_view_menu = null;
    target.chk_status_todos = null;
    target.chk_status_ativo = null;
    target.chk_status_inativo = null;
    target.sp_deposito = null;
    target.sp_grupo = null;
    target.txv_grupo = null;
    target.helpEstoqueSaldo = null;
    target.progress_bar = null;
    target.progress_title = null;
  }
}
