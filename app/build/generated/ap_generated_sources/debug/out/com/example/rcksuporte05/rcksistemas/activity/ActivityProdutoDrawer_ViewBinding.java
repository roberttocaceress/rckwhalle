// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityProdutoDrawer_ViewBinding implements Unbinder {
  private ActivityProdutoDrawer target;

  private View view7f0a00ed;

  private View view7f0a041b;

  private View view7f0a00d6;

  @UiThread
  public ActivityProdutoDrawer_ViewBinding(ActivityProdutoDrawer target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProdutoDrawer_ViewBinding(final ActivityProdutoDrawer target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.listaProdutoRecycler, "field 'recyclerView'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tb_produto, "field 'toolbar'", Toolbar.class);
    target.edtTotalProdutos = Utils.findRequiredViewAsType(source, R.id.edtTotalProdutos, "field 'edtTotalProdutos'", TextView.class);
    target.buscaProduto = Utils.findRequiredViewAsType(source, R.id.buscaProduto, "field 'buscaProduto'", SearchView.class);
    target.compradoProduto = Utils.findRequiredViewAsType(source, R.id.compradoProduto, "field 'compradoProduto'", ImageView.class);
    target.helpProduto = Utils.findRequiredViewAsType(source, R.id.helpProduto, "field 'helpProduto'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.btnNovoPedido, "field 'btnNovoPedido' and method 'btnNovoPedido'");
    target.btnNovoPedido = Utils.castView(view, R.id.btnNovoPedido, "field 'btnNovoPedido'", Button.class);
    view7f0a00ed = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnNovoPedido();
      }
    });
    view = Utils.findRequiredView(source, R.id.lytNovoPedido, "field 'lytNovoPedido' and method 'novoPedido'");
    target.lytNovoPedido = Utils.castView(view, R.id.lytNovoPedido, "field 'lytNovoPedido'", LinearLayout.class);
    view7f0a041b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.novoPedido();
      }
    });
    target.cardFinalizarInclusao = Utils.findRequiredViewAsType(source, R.id.cardFinalizarInclusao, "field 'cardFinalizarInclusao'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btnFinalizarInclusao, "field 'btnFinalizarInclusao' and method 'btnFinalizarInclusao'");
    target.btnFinalizarInclusao = Utils.castView(view, R.id.btnFinalizarInclusao, "field 'btnFinalizarInclusao'", Button.class);
    view7f0a00d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnFinalizarInclusao();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProdutoDrawer target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.toolbar = null;
    target.edtTotalProdutos = null;
    target.buscaProduto = null;
    target.compradoProduto = null;
    target.helpProduto = null;
    target.btnNovoPedido = null;
    target.lytNovoPedido = null;
    target.cardFinalizarInclusao = null;
    target.btnFinalizarInclusao = null;

    view7f0a00ed.setOnClickListener(null);
    view7f0a00ed = null;
    view7f0a041b.setOnClickListener(null);
    view7f0a041b = null;
    view7f0a00d6.setOnClickListener(null);
    view7f0a00d6 = null;
  }
}
