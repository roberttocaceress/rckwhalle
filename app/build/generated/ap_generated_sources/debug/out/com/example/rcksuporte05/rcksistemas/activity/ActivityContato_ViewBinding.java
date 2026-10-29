// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityContato_ViewBinding implements Unbinder {
  private ActivityContato target;

  private View view7f0a03fb;

  private View view7f0a04fb;

  private View view7f0a00f9;

  @UiThread
  public ActivityContato_ViewBinding(ActivityContato target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityContato_ViewBinding(final ActivityContato target, View source) {
    this.target = target;

    View view;
    target.txtCategoria = Utils.findRequiredViewAsType(source, R.id.txtCategoria, "field 'txtCategoria'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.imFisicaJuridica = Utils.findRequiredViewAsType(source, R.id.imFisicaJuridica, "field 'imFisicaJuridica'", ImageView.class);
    target.txtRazaoSocial = Utils.findRequiredViewAsType(source, R.id.txtRazaoSocial, "field 'txtRazaoSocial'", TextView.class);
    target.txtNomeFantasia = Utils.findRequiredViewAsType(source, R.id.txtNomeFantasia, "field 'txtNomeFantasia'", TextView.class);
    target.txtCodigoCliente = Utils.findRequiredViewAsType(source, R.id.txtCodigoCliente, "field 'txtCodigoCliente'", TextView.class);
    target.txtTelefone = Utils.findRequiredViewAsType(source, R.id.txtTelefone, "field 'txtTelefone'", TextView.class);
    target.txtCpfCnpj = Utils.findRequiredViewAsType(source, R.id.txtCpfCnpj, "field 'txtCpfCnpj'", TextView.class);
    target.txtEmail = Utils.findRequiredViewAsType(source, R.id.txtEmail, "field 'txtEmail'", TextView.class);
    target.txtEndereco = Utils.findRequiredViewAsType(source, R.id.txtEndereco, "field 'txtEndereco'", TextView.class);
    target.lyDetalhes = Utils.findRequiredViewAsType(source, R.id.lyDetalhes, "field 'lyDetalhes'", RelativeLayout.class);
    target.lyChamada = Utils.findRequiredViewAsType(source, R.id.lyChamada, "field 'lyChamada'", RelativeLayout.class);
    target.lyEmail = Utils.findRequiredViewAsType(source, R.id.lyEmail, "field 'lyEmail'", RelativeLayout.class);
    target.lyGps = Utils.findRequiredViewAsType(source, R.id.lyGps, "field 'lyGps'", RelativeLayout.class);
    target.lyNomeCliente = Utils.findRequiredViewAsType(source, R.id.lyNomeCliente, "field 'lyNomeCliente'", RelativeLayout.class);
    target.lyFinanceiro = Utils.findRequiredViewAsType(source, R.id.lyFinanceiro, "field 'lyFinanceiro'", RelativeLayout.class);
    target.txtLimiteCredito = Utils.findRequiredViewAsType(source, R.id.txtLimiteCredito, "field 'txtLimiteCredito'", TextView.class);
    target.txtLimiteUtilizado = Utils.findRequiredViewAsType(source, R.id.txtLimiteUtilizado, "field 'txtLimiteUtilizado'", TextView.class);
    target.txtLimiteDisponivel = Utils.findRequiredViewAsType(source, R.id.txtLimiteDisponivel, "field 'txtLimiteDisponivel'", TextView.class);
    target.txtPendenciaFinanceira = Utils.findRequiredViewAsType(source, R.id.txtPendenciaFinanceira, "field 'txtPendenciaFinanceira'", TextView.class);
    target.txtClientePesquisaFinanceiro = Utils.findRequiredViewAsType(source, R.id.txtClientePesquisaFinanceiro, "field 'txtClientePesquisaFinanceiro'", TextView.class);
    target.txtPedidoPendente = Utils.findRequiredViewAsType(source, R.id.txtPedidoPendente, "field 'txtPedidoPendente'", TextView.class);
    target.txtPedidoAnalise = Utils.findRequiredViewAsType(source, R.id.txtPedidoAnalise, "field 'txtPedidoAnalise'", TextView.class);
    target.edtDataCadastro = Utils.findRequiredViewAsType(source, R.id.edtDataCadastro, "field 'edtDataCadastro'", TextView.class);
    target.txtPromocaoCliente = Utils.findRequiredViewAsType(source, R.id.txtPromocaoCliente, "field 'txtPromocaoCliente'", TextView.class);
    target.txtNomeVendedor = Utils.findRequiredViewAsType(source, R.id.txtNomeVendedor, "field 'txtNomeVendedor'", TextView.class);
    target.txtobservacoesFaturamento = Utils.findRequiredViewAsType(source, R.id.txtobservacoesFaturamento, "field 'txtobservacoesFaturamento'", TextView.class);
    target.txtobservacoesFinanceiro = Utils.findRequiredViewAsType(source, R.id.txtobservacoesFinanceiro, "field 'txtobservacoesFinanceiro'", TextView.class);
    view = Utils.findRequiredView(source, R.id.lyNomeVendedor, "method 'showActivityContatoVendedor'");
    view7f0a03fb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showActivityContatoVendedor();
      }
    });
    view = Utils.findRequiredView(source, R.id.novoPedido, "method 'novoPedido'");
    view7f0a04fb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.novoPedido();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnNovoPedido, "method 'btnNovoPedido'");
    view7f0a00f9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnNovoPedido();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityContato target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtCategoria = null;
    target.toolbar = null;
    target.imFisicaJuridica = null;
    target.txtRazaoSocial = null;
    target.txtNomeFantasia = null;
    target.txtCodigoCliente = null;
    target.txtTelefone = null;
    target.txtCpfCnpj = null;
    target.txtEmail = null;
    target.txtEndereco = null;
    target.lyDetalhes = null;
    target.lyChamada = null;
    target.lyEmail = null;
    target.lyGps = null;
    target.lyNomeCliente = null;
    target.lyFinanceiro = null;
    target.txtLimiteCredito = null;
    target.txtLimiteUtilizado = null;
    target.txtLimiteDisponivel = null;
    target.txtPendenciaFinanceira = null;
    target.txtClientePesquisaFinanceiro = null;
    target.txtPedidoPendente = null;
    target.txtPedidoAnalise = null;
    target.edtDataCadastro = null;
    target.txtPromocaoCliente = null;
    target.txtNomeVendedor = null;
    target.txtobservacoesFaturamento = null;
    target.txtobservacoesFinanceiro = null;

    view7f0a03fb.setOnClickListener(null);
    view7f0a03fb = null;
    view7f0a04fb.setOnClickListener(null);
    view7f0a04fb = null;
    view7f0a00f9.setOnClickListener(null);
    view7f0a00f9 = null;
  }
}
