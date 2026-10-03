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

public class ActivityFinanca_ViewBinding implements Unbinder {
  private ActivityFinanca target;

  @UiThread
  public ActivityFinanca_ViewBinding(ActivityFinanca target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityFinanca_ViewBinding(ActivityFinanca target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tbl_resumo_financeiro, "field 'toolbar'", Toolbar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycle_financa, "field 'recyclerView'", RecyclerView.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_splash, "field 'progress_bar'", ProgressBar.class);
    target.progress_title = Utils.findRequiredViewAsType(source, R.id.progress_title, "field 'progress_title'", TextView.class);
    target.card_view_menu = Utils.findRequiredViewAsType(source, R.id.card_view_menu, "field 'card_view_menu'", CardView.class);
    target.btn_view_avancar = Utils.findRequiredViewAsType(source, R.id.img_view_avancar, "field 'btn_view_avancar'", ImageView.class);
    target.btn_view_menu = Utils.findRequiredViewAsType(source, R.id.img_view_menu, "field 'btn_view_menu'", ImageView.class);
    target.id_opcao_emissao = Utils.findRequiredViewAsType(source, R.id.id_opcao_emissao, "field 'id_opcao_emissao'", CheckBox.class);
    target.id_opcao_competencia = Utils.findRequiredViewAsType(source, R.id.id_opcao_competencia, "field 'id_opcao_competencia'", CheckBox.class);
    target.id_opcao_vencimento = Utils.findRequiredViewAsType(source, R.id.id_opcao_vencimento, "field 'id_opcao_vencimento'", CheckBox.class);
    target.id_opcao_quitacao = Utils.findRequiredViewAsType(source, R.id.id_opcao_quitacao, "field 'id_opcao_quitacao'", CheckBox.class);
    target.id_opcao_pendentes = Utils.findRequiredViewAsType(source, R.id.id_opcao_pendentes, "field 'id_opcao_pendentes'", CheckBox.class);
    target.id_opcao_quitadas = Utils.findRequiredViewAsType(source, R.id.id_opcao_quitadas, "field 'id_opcao_quitadas'", CheckBox.class);
    target.id_opcao_vencidas = Utils.findRequiredViewAsType(source, R.id.id_opcao_vencidas, "field 'id_opcao_vencidas'", CheckBox.class);
    target.id_opcao_avencer = Utils.findRequiredViewAsType(source, R.id.id_opcao_avencer, "field 'id_opcao_avencer'", CheckBox.class);
    target.edt_data_final = Utils.findRequiredViewAsType(source, R.id.edt_data_final, "field 'edt_data_final'", EditText.class);
    target.edt_data_inicial = Utils.findRequiredViewAsType(source, R.id.edt_data_inicial, "field 'edt_data_inicial'", EditText.class);
    target.sp_conta_bancaria = Utils.findRequiredViewAsType(source, R.id.sp_conta_bancaria, "field 'sp_conta_bancaria'", Spinner.class);
    target.sp_especie = Utils.findRequiredViewAsType(source, R.id.sp_especie, "field 'sp_especie'", Spinner.class);
    target.edt_nome_favorecido = Utils.findRequiredViewAsType(source, R.id.edt_nome_favorecido, "field 'edt_nome_favorecido'", EditText.class);
    target.edt_numero_documento = Utils.findRequiredViewAsType(source, R.id.edt_numero_documento, "field 'edt_numero_documento'", EditText.class);
    target.edt_numero_cheque = Utils.findRequiredViewAsType(source, R.id.edt_numero_cheque, "field 'edt_numero_cheque'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityFinanca target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recyclerView = null;
    target.progress_bar = null;
    target.progress_title = null;
    target.card_view_menu = null;
    target.btn_view_avancar = null;
    target.btn_view_menu = null;
    target.id_opcao_emissao = null;
    target.id_opcao_competencia = null;
    target.id_opcao_vencimento = null;
    target.id_opcao_quitacao = null;
    target.id_opcao_pendentes = null;
    target.id_opcao_quitadas = null;
    target.id_opcao_vencidas = null;
    target.id_opcao_avencer = null;
    target.edt_data_final = null;
    target.edt_data_inicial = null;
    target.sp_conta_bancaria = null;
    target.sp_especie = null;
    target.edt_nome_favorecido = null;
    target.edt_numero_documento = null;
    target.edt_numero_cheque = null;
  }
}
