// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityFinanceiroDetalhe_ViewBinding implements Unbinder {
  private ActivityFinanceiroDetalhe target;

  @UiThread
  public ActivityFinanceiroDetalhe_ViewBinding(ActivityFinanceiroDetalhe target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityFinanceiroDetalhe_ViewBinding(ActivityFinanceiroDetalhe target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tbFinanceiro, "field 'toolbar'", Toolbar.class);
    target.trLinhaColunaQuitado = Utils.findRequiredViewAsType(source, R.id.trLinhaColunaQuitado, "field 'trLinhaColunaQuitado'", TableRow.class);
    target.trLinhaColunaPendente = Utils.findRequiredViewAsType(source, R.id.trLinhaColunaPendente, "field 'trLinhaColunaPendente'", TableRow.class);
    target.listaHistoricoFinanceiro = Utils.findRequiredViewAsType(source, R.id.lstHistoricoFinanceiro, "field 'listaHistoricoFinanceiro'", ListView.class);
    target.edtTotalTitulos = Utils.findRequiredViewAsType(source, R.id.edtTotalTitulos, "field 'edtTotalTitulos'", EditText.class);
    target.swipe = Utils.findRequiredViewAsType(source, R.id.swipe, "field 'swipe'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityFinanceiroDetalhe target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.trLinhaColunaQuitado = null;
    target.trLinhaColunaPendente = null;
    target.listaHistoricoFinanceiro = null;
    target.edtTotalTitulos = null;
    target.swipe = null;
  }
}
