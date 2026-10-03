// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CadastroAnexoViewHolder_ViewBinding implements Unbinder {
  private CadastroAnexoViewHolder target;

  @UiThread
  public CadastroAnexoViewHolder_ViewBinding(CadastroAnexoViewHolder target, View source) {
    this.target = target;

    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", RelativeLayout.class);
    target.txtPosition = Utils.findRequiredViewAsType(source, R.id.txtPosition, "field 'txtPosition'", TextView.class);
    target.imAnexo = Utils.findRequiredViewAsType(source, R.id.imAnexo, "field 'imAnexo'", ImageView.class);
    target.txtNomeAnexo = Utils.findRequiredViewAsType(source, R.id.txtNomeAnexo, "field 'txtNomeAnexo'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CadastroAnexoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.itemView = null;
    target.txtPosition = null;
    target.imAnexo = null;
    target.txtNomeAnexo = null;
  }
}
