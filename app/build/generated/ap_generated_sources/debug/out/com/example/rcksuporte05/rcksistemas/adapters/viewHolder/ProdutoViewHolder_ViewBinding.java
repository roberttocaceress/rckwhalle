// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
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

public class ProdutoViewHolder_ViewBinding implements Unbinder {
  private ProdutoViewHolder target;

  @UiThread
  public ProdutoViewHolder_ViewBinding(ProdutoViewHolder target, View source) {
    this.target = target;

    target.txtCodigoBarra = Utils.findRequiredViewAsType(source, R.id.txtCodigoBarra, "field 'txtCodigoBarra'", TextView.class);
    target.nomeListaProduto = Utils.findRequiredViewAsType(source, R.id.nomeListaProduto, "field 'nomeListaProduto'", TextView.class);
    target.precoProduto = Utils.findRequiredViewAsType(source, R.id.precoProduto, "field 'precoProduto'", TextView.class);
    target.precoICMS = Utils.findRequiredViewAsType(source, R.id.precoICMS, "field 'precoICMS'", TextView.class);
    target.textUN = Utils.findRequiredViewAsType(source, R.id.txtUnidadeListaProduto, "field 'textUN'", TextView.class);
    target.idProduto = Utils.findRequiredViewAsType(source, R.id.idProduto, "field 'idProduto'", TextView.class);
    target.txtSaldoEstoque = Utils.findRequiredViewAsType(source, R.id.txtSaldoEstoque, "field 'txtSaldoEstoque'", TextView.class);
    target.txtNomeSubGrupo = Utils.findRequiredViewAsType(source, R.id.txtNomeSubGrupo, "field 'txtNomeSubGrupo'", TextView.class);
    target.txtVazio = Utils.findRequiredViewAsType(source, R.id.txtVazio, "field 'txtVazio'", TextView.class);
    target.txtNomeGrupo = Utils.findRequiredViewAsType(source, R.id.txtNomeGrupo, "field 'txtNomeGrupo'", TextView.class);
    target.nomeTabelaPreco = Utils.findRequiredViewAsType(source, R.id.nomeTabelaPreco, "field 'nomeTabelaPreco'", TextView.class);
    target.imageAnexo = Utils.findRequiredViewAsType(source, R.id.imageAnexo, "field 'imageAnexo'", ImageView.class);
    target.imagePromocaoVerde = Utils.findRequiredViewAsType(source, R.id.imagePromocaoVerde, "field 'imagePromocaoVerde'", ImageView.class);
    target.imageCampanhaVerde = Utils.findRequiredViewAsType(source, R.id.imageCampanhaVerde, "field 'imageCampanhaVerde'", ImageView.class);
    target.imageCarrinhoVerde = Utils.findRequiredViewAsType(source, R.id.imageCarrinhoVerde, "field 'imageCarrinhoVerde'", ImageView.class);
    target.imageCarrinhoAzul = Utils.findRequiredViewAsType(source, R.id.imageCarrinhoAzul, "field 'imageCarrinhoAzul'", ImageView.class);
    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", LinearLayout.class);
    target.lytTabelaPrecoICMS = Utils.findRequiredViewAsType(source, R.id.lytTabelaPrecoICMS, "field 'lytTabelaPrecoICMS'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProdutoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtCodigoBarra = null;
    target.nomeListaProduto = null;
    target.precoProduto = null;
    target.precoICMS = null;
    target.textUN = null;
    target.idProduto = null;
    target.txtSaldoEstoque = null;
    target.txtNomeSubGrupo = null;
    target.txtVazio = null;
    target.txtNomeGrupo = null;
    target.nomeTabelaPreco = null;
    target.imageAnexo = null;
    target.imagePromocaoVerde = null;
    target.imageCampanhaVerde = null;
    target.imageCarrinhoVerde = null;
    target.imageCarrinhoAzul = null;
    target.itemView = null;
    target.lytTabelaPrecoICMS = null;
  }
}
