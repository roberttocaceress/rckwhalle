// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ActivityCobranca_ViewBinding implements Unbinder {
  private ActivityCobranca target;

  @UiThread
  public ActivityCobranca_ViewBinding(ActivityCobranca target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityCobranca_ViewBinding(ActivityCobranca target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tbl_cobranca, "field 'toolbar'", Toolbar.class);
    target.buscaCobranca = Utils.findRequiredViewAsType(source, R.id.buscaCobranca, "field 'buscaCobranca'", SearchView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycle_cobranca, "field 'recyclerView'", RecyclerView.class);
    target.card_view_menu = Utils.findRequiredViewAsType(source, R.id.card_view_menu, "field 'card_view_menu'", CardView.class);
    target.btn_view_avancar = Utils.findRequiredViewAsType(source, R.id.img_view_avancar, "field 'btn_view_avancar'", ImageView.class);
    target.btn_view_menu = Utils.findRequiredViewAsType(source, R.id.img_view_menu, "field 'btn_view_menu'", ImageView.class);
    target.id_opcao_emissao = Utils.findRequiredViewAsType(source, R.id.id_opcao_emissao, "field 'id_opcao_emissao'", CheckBox.class);
    target.id_opcao_vencimento = Utils.findRequiredViewAsType(source, R.id.id_opcao_vencimento, "field 'id_opcao_vencimento'", CheckBox.class);
    target.id_opcao_recebido = Utils.findRequiredViewAsType(source, R.id.id_opcao_recebido, "field 'id_opcao_recebido'", CheckBox.class);
    target.id_opcao_reagendado = Utils.findRequiredViewAsType(source, R.id.id_opcao_reagendado, "field 'id_opcao_reagendado'", CheckBox.class);
    target.sp_estado = Utils.findRequiredViewAsType(source, R.id.sp_estado, "field 'sp_estado'", Spinner.class);
    target.sp_cidade = Utils.findRequiredViewAsType(source, R.id.sp_cidade, "field 'sp_cidade'", Spinner.class);
    target.sp_bairro = Utils.findRequiredViewAsType(source, R.id.sp_bairro, "field 'sp_bairro'", Spinner.class);
    target.sp_regiao = Utils.findRequiredViewAsType(source, R.id.sp_regiao, "field 'sp_regiao'", Spinner.class);
    target.sp_conta_bancaria = Utils.findRequiredViewAsType(source, R.id.sp_conta_bancaria, "field 'sp_conta_bancaria'", Spinner.class);
    target.sp_moeda = Utils.findRequiredViewAsType(source, R.id.sp_moeda, "field 'sp_moeda'", Spinner.class);
    target.edt_data_final = Utils.findRequiredViewAsType(source, R.id.edt_data_final, "field 'edt_data_final'", EditText.class);
    target.edt_data_inicial = Utils.findRequiredViewAsType(source, R.id.edt_data_inicial, "field 'edt_data_inicial'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityCobranca target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.buscaCobranca = null;
    target.recyclerView = null;
    target.card_view_menu = null;
    target.btn_view_avancar = null;
    target.btn_view_menu = null;
    target.id_opcao_emissao = null;
    target.id_opcao_vencimento = null;
    target.id_opcao_recebido = null;
    target.id_opcao_reagendado = null;
    target.sp_estado = null;
    target.sp_cidade = null;
    target.sp_bairro = null;
    target.sp_regiao = null;
    target.sp_conta_bancaria = null;
    target.sp_moeda = null;
    target.edt_data_final = null;
    target.edt_data_inicial = null;
  }
}
