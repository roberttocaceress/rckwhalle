// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProdutoPedidoViewHolder_ViewBinding implements Unbinder {
  private ProdutoPedidoViewHolder target;

  @UiThread
  public ProdutoPedidoViewHolder_ViewBinding(ProdutoPedidoViewHolder target, View source) {
    this.target = target;

    target.imageAnexo = Utils.findRequiredViewAsType(source, R.id.imageAnexo, "field 'imageAnexo'", ImageView.class);
    target.idPosition = Utils.findRequiredViewAsType(source, R.id.idPosition, "field 'idPosition'", TextView.class);
    target.idProduto = Utils.findRequiredViewAsType(source, R.id.idProduto, "field 'idProduto'", TextView.class);
    target.txtCodigoBarra = Utils.findRequiredViewAsType(source, R.id.txtCodigoBarra, "field 'txtCodigoBarra'", TextView.class);
    target.nomeListaProduto = Utils.findRequiredViewAsType(source, R.id.nomeListaProduto, "field 'nomeListaProduto'", TextView.class);
    target.precoProduto = Utils.findRequiredViewAsType(source, R.id.precoProduto, "field 'precoProduto'", TextView.class);
    target.textViewUnidadeMedida = Utils.findRequiredViewAsType(source, R.id.textViewUnidadeMedida, "field 'textViewUnidadeMedida'", TextView.class);
    target.textViewCampanha = Utils.findRequiredViewAsType(source, R.id.textViewCampanha, "field 'textViewCampanha'", TextView.class);
    target.nomeCampanha = Utils.findRequiredViewAsType(source, R.id.nomeCampanha, "field 'nomeCampanha'", TextView.class);
    target.txtDesconto = Utils.findRequiredViewAsType(source, R.id.txtDesconto, "field 'txtDesconto'", TextView.class);
    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", LinearLayout.class);
    target.btnExcluirProduto = Utils.findRequiredViewAsType(source, R.id.btnExcluirProduto, "field 'btnExcluirProduto'", Button.class);
    target.btnAnaliseEstoque = Utils.findRequiredViewAsType(source, R.id.btnAnaliseEstoque, "field 'btnAnaliseEstoque'", Button.class);
    target.lytDesxctoPreco = Utils.findRequiredViewAsType(source, R.id.lytDesxctoPreco, "field 'lytDesxctoPreco'", LinearLayout.class);
    target.lytCampanha = Utils.findRequiredViewAsType(source, R.id.lytCampanha, "field 'lytCampanha'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProdutoPedidoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageAnexo = null;
    target.idPosition = null;
    target.idProduto = null;
    target.txtCodigoBarra = null;
    target.nomeListaProduto = null;
    target.precoProduto = null;
    target.textViewUnidadeMedida = null;
    target.textViewCampanha = null;
    target.nomeCampanha = null;
    target.txtDesconto = null;
    target.itemView = null;
    target.btnExcluirProduto = null;
    target.btnAnaliseEstoque = null;
    target.lytDesxctoPreco = null;
    target.lytCampanha = null;
  }
}
