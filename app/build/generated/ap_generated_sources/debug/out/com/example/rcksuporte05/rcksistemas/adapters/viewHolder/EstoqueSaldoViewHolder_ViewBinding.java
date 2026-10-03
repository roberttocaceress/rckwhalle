// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EstoqueSaldoViewHolder_ViewBinding implements Unbinder {
  private EstoqueSaldoViewHolder target;

  @UiThread
  public EstoqueSaldoViewHolder_ViewBinding(EstoqueSaldoViewHolder target, View source) {
    this.target = target;

    target.idProduto = Utils.findRequiredViewAsType(source, R.id.idProduto, "field 'idProduto'", TextView.class);
    target.txtCodigoBarra = Utils.findRequiredViewAsType(source, R.id.txtCodigoBarra, "field 'txtCodigoBarra'", TextView.class);
    target.nomeListaProduto = Utils.findRequiredViewAsType(source, R.id.nomeListaProduto, "field 'nomeListaProduto'", TextView.class);
    target.txtSaldoEstoque = Utils.findRequiredViewAsType(source, R.id.txtSaldoEstoque, "field 'txtSaldoEstoque'", TextView.class);
    target.txtSaldoReservado = Utils.findRequiredViewAsType(source, R.id.txtSaldoReservado, "field 'txtSaldoReservado'", TextView.class);
    target.textUN = Utils.findRequiredViewAsType(source, R.id.txtUnidadeListaProduto, "field 'textUN'", TextView.class);
    target.imgProdutoAtivoBloqueado = Utils.findRequiredViewAsType(source, R.id.imgProdutoAtivoBloqueado, "field 'imgProdutoAtivoBloqueado'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EstoqueSaldoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.idProduto = null;
    target.txtCodigoBarra = null;
    target.nomeListaProduto = null;
    target.txtSaldoEstoque = null;
    target.txtSaldoReservado = null;
    target.textUN = null;
    target.imgProdutoAtivoBloqueado = null;
  }
}
