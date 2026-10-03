// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotaFiscalViewHolder_ViewBinding implements Unbinder {
  private NotaFiscalViewHolder target;

  @UiThread
  public NotaFiscalViewHolder_ViewBinding(NotaFiscalViewHolder target, View source) {
    this.target = target;

    target.txvNomeOperacao = Utils.findRequiredViewAsType(source, R.id.txvNomeOperacao, "field 'txvNomeOperacao'", TextView.class);
    target.txvValorOperacao = Utils.findRequiredViewAsType(source, R.id.txvValorOperacao, "field 'txvValorOperacao'", TextView.class);
    target.txvTotalDocs = Utils.findRequiredViewAsType(source, R.id.txvTotalDocs, "field 'txvTotalDocs'", TextView.class);
    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NotaFiscalViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txvNomeOperacao = null;
    target.txvValorOperacao = null;
    target.txvTotalDocs = null;
    target.itemView = null;
  }
}
