// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroProspectFotoSalvar_ViewBinding implements Unbinder {
  private CadastroProspectFotoSalvar target;

  private View view7f0a0327;

  private View view7f0a0328;

  private View view7f0a00bf;

  @UiThread
  public CadastroProspectFotoSalvar_ViewBinding(final CadastroProspectFotoSalvar target,
      View source) {
    this.target = target;

    View view;
    target.edtDataRetorno = Utils.findRequiredViewAsType(source, R.id.edtDataRetorno, "field 'edtDataRetorno'", EditText.class);
    target.spTipoVisita = Utils.findRequiredViewAsType(source, R.id.spTipoVisita, "field 'spTipoVisita'", Spinner.class);
    target.edtTitulo = Utils.findRequiredViewAsType(source, R.id.edtTitulo, "field 'edtTitulo'", TextView.class);
    target.edtDescricaoAcao = Utils.findRequiredViewAsType(source, R.id.edtDescricaoAcao, "field 'edtDescricaoAcao'", EditText.class);
    view = Utils.findRequiredView(source, R.id.imagemProspect1, "field 'imagemProspect1' and method 'chamarGaleria'");
    target.imagemProspect1 = Utils.castView(view, R.id.imagemProspect1, "field 'imagemProspect1'", ImageButton.class);
    view7f0a0327 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chamarGaleria();
      }
    });
    view = Utils.findRequiredView(source, R.id.imagemProspect2, "field 'imagemProspect2' and method 'chamarGaleria2'");
    target.imagemProspect2 = Utils.castView(view, R.id.imagemProspect2, "field 'imagemProspect2'", ImageButton.class);
    view7f0a0328 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chamarGaleria2();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCheckinVisitaProspect, "field 'btnCheckinVisitaProspect' and method 'checkin'");
    target.btnCheckinVisitaProspect = Utils.castView(view, R.id.btnCheckinVisitaProspect, "field 'btnCheckinVisitaProspect'", Button.class);
    view7f0a00bf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.checkin();
      }
    });
    target.txtLatitudeProspect = Utils.findRequiredViewAsType(source, R.id.txtLatitudeProspect, "field 'txtLatitudeProspect'", TextView.class);
    target.txtLongitudeProspect = Utils.findRequiredViewAsType(source, R.id.txtLongitudeProspect, "field 'txtLongitudeProspect'", TextView.class);
    target.txtChekinEnderecoProspect = Utils.findRequiredViewAsType(source, R.id.txtChekinEnderecoProspect, "field 'txtChekinEnderecoProspect'", TextView.class);
    target.btnContinuar = Utils.findRequiredViewAsType(source, R.id.btnContinuar, "field 'btnContinuar'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroProspectFotoSalvar target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edtDataRetorno = null;
    target.spTipoVisita = null;
    target.edtTitulo = null;
    target.edtDescricaoAcao = null;
    target.imagemProspect1 = null;
    target.imagemProspect2 = null;
    target.btnCheckinVisitaProspect = null;
    target.txtLatitudeProspect = null;
    target.txtLongitudeProspect = null;
    target.txtChekinEnderecoProspect = null;
    target.btnContinuar = null;

    view7f0a0327.setOnClickListener(null);
    view7f0a0327 = null;
    view7f0a0328.setOnClickListener(null);
    view7f0a0328 = null;
    view7f0a00bf.setOnClickListener(null);
    view7f0a00bf = null;
  }
}
