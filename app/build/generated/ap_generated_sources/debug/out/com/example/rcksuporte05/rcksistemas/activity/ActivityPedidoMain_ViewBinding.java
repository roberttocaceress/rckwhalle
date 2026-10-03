// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityPedidoMain_ViewBinding implements Unbinder {
  private ActivityPedidoMain target;

  private View view7f0a00b3;

  private View view7f0a02f6;

  private View view7f0a00b4;

  private View view7f0a02f7;

  @UiThread
  public ActivityPedidoMain_ViewBinding(ActivityPedidoMain target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityPedidoMain_ViewBinding(final ActivityPedidoMain target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbarFragsPedido, "field 'toolbar'", Toolbar.class);
    target.txtNomeCliente = Utils.findRequiredViewAsType(source, R.id.txtNomeCliente, "field 'txtNomeCliente'", TextView.class);
    target.txtNomeFantasia = Utils.findRequiredViewAsType(source, R.id.txtNomeFantasia, "field 'txtNomeFantasia'", TextView.class);
    target.stl_tabsPedido = Utils.findRequiredViewAsType(source, R.id.stl_tabsPedido, "field 'stl_tabsPedido'", SlidingTabLayout.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.vp_tabsPedido, "field 'mViewPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.btnBuscaCliente, "field 'btnBuscaCliente' and method 'buscaCliente'");
    target.btnBuscaCliente = Utils.castView(view, R.id.btnBuscaCliente, "field 'btnBuscaCliente'", Button.class);
    view7f0a00b3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.buscaCliente();
      }
    });
    view = Utils.findRequiredView(source, R.id.ifCliente, "field 'ifCliente' and method 'nomeCliente'");
    target.ifCliente = Utils.castView(view, R.id.ifCliente, "field 'ifCliente'", LinearLayout.class);
    view7f0a02f6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.nomeCliente();
      }
    });
    target.lyHeaderCliente = Utils.findRequiredViewAsType(source, R.id.lyHeaderCliente, "field 'lyHeaderCliente'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.btnBuscaHistoricoFinanceiro, "field 'btnBuscaHistoricoFinanceiro' and method 'buscaHistoricoFinanceiro'");
    target.btnBuscaHistoricoFinanceiro = Utils.castView(view, R.id.btnBuscaHistoricoFinanceiro, "field 'btnBuscaHistoricoFinanceiro'", Button.class);
    view7f0a00b4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.buscaHistoricoFinanceiro();
      }
    });
    view = Utils.findRequiredView(source, R.id.ifHistoricoFinanceiro, "field 'ifHistoricoFinanceiro' and method 'financeiroCliente'");
    target.ifHistoricoFinanceiro = Utils.castView(view, R.id.ifHistoricoFinanceiro, "field 'ifHistoricoFinanceiro'", LinearLayout.class);
    view7f0a02f7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.financeiroCliente();
      }
    });
    target.lyhistoricoFinanceiro = Utils.findRequiredViewAsType(source, R.id.lyhistoricoFinanceiro, "field 'lyhistoricoFinanceiro'", RelativeLayout.class);
    target.lyvalorIcmsST = Utils.findRequiredViewAsType(source, R.id.lyvalorIcmsST, "field 'lyvalorIcmsST'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityPedidoMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtNomeCliente = null;
    target.txtNomeFantasia = null;
    target.stl_tabsPedido = null;
    target.mViewPager = null;
    target.btnBuscaCliente = null;
    target.ifCliente = null;
    target.lyHeaderCliente = null;
    target.btnBuscaHistoricoFinanceiro = null;
    target.ifHistoricoFinanceiro = null;
    target.lyhistoricoFinanceiro = null;
    target.lyvalorIcmsST = null;

    view7f0a00b3.setOnClickListener(null);
    view7f0a00b3 = null;
    view7f0a02f6.setOnClickListener(null);
    view7f0a02f6 = null;
    view7f0a00b4.setOnClickListener(null);
    view7f0a00b4 = null;
    view7f0a02f7.setOnClickListener(null);
    view7f0a02f7 = null;
  }
}
