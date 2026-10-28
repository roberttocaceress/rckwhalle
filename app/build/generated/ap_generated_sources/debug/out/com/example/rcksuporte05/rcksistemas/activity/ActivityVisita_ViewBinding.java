// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class ActivityVisita_ViewBinding implements Unbinder {
  private ActivityVisita target;

  private View view7f0a00c3;

  private View view7f0a0114;

  private View view7f0a032f;

  private View view7f0a0330;

  @UiThread
  public ActivityVisita_ViewBinding(ActivityVisita target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityVisita_ViewBinding(final ActivityVisita target, View source) {
    this.target = target;

    View view;
    target.txtLatitude = Utils.findRequiredViewAsType(source, R.id.txtLatitude, "field 'txtLatitude'", TextView.class);
    target.txtLongitude = Utils.findRequiredViewAsType(source, R.id.txtLongitude, "field 'txtLongitude'", TextView.class);
    target.txtChekinEndereco = Utils.findRequiredViewAsType(source, R.id.txtChekinEndereco, "field 'txtChekinEndereco'", TextView.class);
    target.edtDataRetornoVisita = Utils.findRequiredViewAsType(source, R.id.edtDataRetornoVisita, "field 'edtDataRetornoVisita'", EditText.class);
    target.spTipoVisita = Utils.findRequiredViewAsType(source, R.id.spTipoVisita, "field 'spTipoVisita'", Spinner.class);
    target.edtTitulo = Utils.findRequiredViewAsType(source, R.id.edtTitulo, "field 'edtTitulo'", EditText.class);
    target.edtDescricaoVisita = Utils.findRequiredViewAsType(source, R.id.edtDescricaoAcao, "field 'edtDescricaoVisita'", EditText.class);
    target.tb_visita = Utils.findRequiredViewAsType(source, R.id.tb_visita, "field 'tb_visita'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.btnCheckinVisita, "field 'btnCheckinVisita' and method 'checkin'");
    target.btnCheckinVisita = Utils.castView(view, R.id.btnCheckinVisita, "field 'btnCheckinVisita'", Button.class);
    view7f0a00c3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.checkin();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSalvarVisita, "field 'btnSalvarVisita' and method 'salvar'");
    target.btnSalvarVisita = Utils.castView(view, R.id.btnSalvarVisita, "field 'btnSalvarVisita'", Button.class);
    view7f0a0114 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.salvar();
      }
    });
    view = Utils.findRequiredView(source, R.id.imagem1, "field 'imagem1' and method 'chamarGaleria'");
    target.imagem1 = Utils.castView(view, R.id.imagem1, "field 'imagem1'", ImageButton.class);
    view7f0a032f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chamarGaleria();
      }
    });
    view = Utils.findRequiredView(source, R.id.imagem2, "field 'imagem2' and method 'chamarGaleria2'");
    target.imagem2 = Utils.castView(view, R.id.imagem2, "field 'imagem2'", ImageButton.class);
    view7f0a0330 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chamarGaleria2();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityVisita target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtLatitude = null;
    target.txtLongitude = null;
    target.txtChekinEndereco = null;
    target.edtDataRetornoVisita = null;
    target.spTipoVisita = null;
    target.edtTitulo = null;
    target.edtDescricaoVisita = null;
    target.tb_visita = null;
    target.btnCheckinVisita = null;
    target.btnSalvarVisita = null;
    target.imagem1 = null;
    target.imagem2 = null;

    view7f0a00c3.setOnClickListener(null);
    view7f0a00c3 = null;
    view7f0a0114.setOnClickListener(null);
    view7f0a0114 = null;
    view7f0a032f.setOnClickListener(null);
    view7f0a032f = null;
    view7f0a0330.setOnClickListener(null);
    view7f0a0330 = null;
  }
}
