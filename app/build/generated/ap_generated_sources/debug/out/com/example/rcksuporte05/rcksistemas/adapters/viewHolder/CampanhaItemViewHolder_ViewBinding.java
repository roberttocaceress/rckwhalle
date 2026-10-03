// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CampanhaItemViewHolder_ViewBinding implements Unbinder {
  private CampanhaItemViewHolder target;

  @UiThread
  public CampanhaItemViewHolder_ViewBinding(CampanhaItemViewHolder target, View source) {
    this.target = target;

    target.txtIdProdutoLinha = Utils.findRequiredViewAsType(source, R.id.txtIdProdutoLinha, "field 'txtIdProdutoLinha'", TextView.class);
    target.edtNomeProdutoLinha = Utils.findRequiredViewAsType(source, R.id.edtNomeProdutoLinha, "field 'edtNomeProdutoLinha'", TextView.class);
    target.edtQuantidadeLinha = Utils.findRequiredViewAsType(source, R.id.edtQuantidadeLinha, "field 'edtQuantidadeLinha'", TextView.class);
    target.txtIdProdutoBrinde = Utils.findRequiredViewAsType(source, R.id.txtIdProdutoBrinde, "field 'txtIdProdutoBrinde'", TextView.class);
    target.edtNomeProdutoBrinde = Utils.findRequiredViewAsType(source, R.id.edtNomeProdutoBrinde, "field 'edtNomeProdutoBrinde'", TextView.class);
    target.edtQuantidadeBrinde = Utils.findRequiredViewAsType(source, R.id.edtQuantidadeBrinde, "field 'edtQuantidadeBrinde'", TextView.class);
    target.txtTipoCampanha1 = Utils.findRequiredViewAsType(source, R.id.txtTipoCampanha1, "field 'txtTipoCampanha1'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CampanhaItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtIdProdutoLinha = null;
    target.edtNomeProdutoLinha = null;
    target.edtQuantidadeLinha = null;
    target.txtIdProdutoBrinde = null;
    target.edtNomeProdutoBrinde = null;
    target.edtQuantidadeBrinde = null;
    target.txtTipoCampanha1 = null;
  }
}
