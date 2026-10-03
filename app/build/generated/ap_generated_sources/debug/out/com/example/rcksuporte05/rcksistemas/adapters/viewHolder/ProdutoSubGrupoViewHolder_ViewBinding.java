// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProdutoSubGrupoViewHolder_ViewBinding implements Unbinder {
  private ProdutoSubGrupoViewHolder target;

  @UiThread
  public ProdutoSubGrupoViewHolder_ViewBinding(ProdutoSubGrupoViewHolder target, View source) {
    this.target = target;

    target.cdv_produto_sub_grupo = Utils.findRequiredViewAsType(source, R.id.cdv_produto_sub_grupo, "field 'cdv_produto_sub_grupo'", CardView.class);
    target.img_produto_sub_grupo = Utils.findRequiredViewAsType(source, R.id.img_produto_sub_grupo, "field 'img_produto_sub_grupo'", ImageView.class);
    target.lyt_produto_sub_grupo = Utils.findRequiredViewAsType(source, R.id.lyt_produto_sub_grupo, "field 'lyt_produto_sub_grupo'", LinearLayout.class);
    target.txv_id_sub_grupo = Utils.findRequiredViewAsType(source, R.id.txv_id_sub_grupo, "field 'txv_id_sub_grupo'", TextView.class);
    target.txt_nome_sub_grupo = Utils.findRequiredViewAsType(source, R.id.txt_nome_sub_grupo, "field 'txt_nome_sub_grupo'", TextView.class);
    target.txt_total_produto = Utils.findRequiredViewAsType(source, R.id.txt_total_produto, "field 'txt_total_produto'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProdutoSubGrupoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cdv_produto_sub_grupo = null;
    target.img_produto_sub_grupo = null;
    target.lyt_produto_sub_grupo = null;
    target.txv_id_sub_grupo = null;
    target.txt_nome_sub_grupo = null;
    target.txt_total_produto = null;
  }
}
