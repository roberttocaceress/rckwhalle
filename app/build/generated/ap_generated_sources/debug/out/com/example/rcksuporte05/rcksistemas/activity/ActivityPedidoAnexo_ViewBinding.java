// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPedidoAnexo_ViewBinding implements Unbinder {
  private ActivityPedidoAnexo target;

  private View view7f0a010f;

  private View view7f0a00a9;

  private View view7f0a00aa;

  private View view7f0a00e1;

  private View view7f0a031b;

  @UiThread
  public ActivityPedidoAnexo_ViewBinding(ActivityPedidoAnexo target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPedidoAnexo_ViewBinding(final ActivityPedidoAnexo target, View source) {
    this.target = target;

    View view;
    target.tb_anexo = Utils.findRequiredViewAsType(source, R.id.tb_anexo, "field 'tb_anexo'", Toolbar.class);
    target.edtAnexo = Utils.findRequiredViewAsType(source, R.id.edtAnexo, "field 'edtAnexo'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btnSalvarAnexo, "field 'btnSalvarAnexo' and method 'salvarAnexoGaleria'");
    target.btnSalvarAnexo = Utils.castView(view, R.id.btnSalvarAnexo, "field 'btnSalvarAnexo'", Button.class);
    view7f0a010f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.salvarAnexoGaleria();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAdicionarAnexo, "field 'btnAdicionarAnexo' and method 'addAnexoGaleria'");
    target.btnAdicionarAnexo = Utils.castView(view, R.id.btnAdicionarAnexo, "field 'btnAdicionarAnexo'", Button.class);
    view7f0a00a9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addAnexoGaleria();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAdicionarFoto, "field 'btnAdicionarFoto' and method 'addAnexoFoto'");
    target.btnAdicionarFoto = Utils.castView(view, R.id.btnAdicionarFoto, "field 'btnAdicionarFoto'", Button.class);
    view7f0a00aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addAnexoFoto();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnFinalizarInclusao, "field 'btnFinalizarInclusao' and method 'closeActivity'");
    target.btnFinalizarInclusao = Utils.castView(view, R.id.btnFinalizarInclusao, "field 'btnFinalizarInclusao'", Button.class);
    view7f0a00e1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.closeActivity();
      }
    });
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.imAnexo, "field 'imgAnexo' and method 'removerAnexoFoto'");
    target.imgAnexo = Utils.castView(view, R.id.imAnexo, "field 'imgAnexo'", ImageView.class);
    view7f0a031b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.removerAnexoFoto();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPedidoAnexo target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tb_anexo = null;
    target.edtAnexo = null;
    target.btnSalvarAnexo = null;
    target.btnAdicionarAnexo = null;
    target.btnAdicionarFoto = null;
    target.btnFinalizarInclusao = null;
    target.recyclerView = null;
    target.imgAnexo = null;

    view7f0a010f.setOnClickListener(null);
    view7f0a010f = null;
    view7f0a00a9.setOnClickListener(null);
    view7f0a00a9 = null;
    view7f0a00aa.setOnClickListener(null);
    view7f0a00aa = null;
    view7f0a00e1.setOnClickListener(null);
    view7f0a00e1 = null;
    view7f0a031b.setOnClickListener(null);
    view7f0a031b = null;
  }
}
