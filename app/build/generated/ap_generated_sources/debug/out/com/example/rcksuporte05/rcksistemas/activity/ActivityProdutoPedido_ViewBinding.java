// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.relex.circleindicator.CircleIndicator;

public class ActivityProdutoPedido_ViewBinding implements Unbinder {
  private ActivityProdutoPedido target;

  private View view7f0a010e;

  private View view7f0a010d;

  private View view7f0a00ba;

  private View view7f0a00c1;

  private View view7f0a00db;

  @UiThread
  public ActivityProdutoPedido_ViewBinding(ActivityProdutoPedido target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivityProdutoPedido_ViewBinding(final ActivityProdutoPedido target, View source) {
    this.target = target;

    View view;
    target.activity_produto_pedido = Utils.findRequiredViewAsType(source, R.id.activity_produto_pedido, "field 'activity_produto_pedido'", LinearLayout.class);
    target.lyProduto = Utils.findRequiredViewAsType(source, R.id.lytProduto, "field 'lyProduto'", RelativeLayout.class);
    target.rbPorcentagem = Utils.findRequiredViewAsType(source, R.id.porcentagem, "field 'rbPorcentagem'", RadioButton.class);
    target.rbReal = Utils.findRequiredViewAsType(source, R.id.real, "field 'rbReal'", RadioButton.class);
    target.edtNomeProduto = Utils.findRequiredViewAsType(source, R.id.edtNomeProduto, "field 'edtNomeProduto'", TextView.class);
    target.edtTabelaPreco = Utils.findRequiredViewAsType(source, R.id.edtTabelaPreco, "field 'edtTabelaPreco'", EditText.class);
    target.edtQuantidade = Utils.findRequiredViewAsType(source, R.id.edtQuantidade, "field 'edtQuantidade'", EditText.class);
    target.edtValorProdutos = Utils.findRequiredViewAsType(source, R.id.edtValorProdutos, "field 'edtValorProdutos'", EditText.class);
    target.edtValorPrecoPago = Utils.findRequiredViewAsType(source, R.id.edtValorPrecoPago, "field 'edtValorPrecoPago'", EditText.class);
    target.edtDesconto = Utils.findRequiredViewAsType(source, R.id.edtDesconto, "field 'edtDesconto'", EditText.class);
    target.edtDescontoReais = Utils.findRequiredViewAsType(source, R.id.edtDescontoReais, "field 'edtDescontoReais'", EditText.class);
    target.edtDescontoVerba = Utils.findRequiredViewAsType(source, R.id.edtDescontoVerba, "field 'edtDescontoVerba'", EditText.class);
    target.edtvalorIcmsST = Utils.findRequiredViewAsType(source, R.id.edtvalorIcmsST, "field 'edtvalorIcmsST'", EditText.class);
    target.edtTotal = Utils.findRequiredViewAsType(source, R.id.edtTotal, "field 'edtTotal'", EditText.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tb_produto_pedido, "field 'toolbar'", Toolbar.class);
    target.btnBuscaProduto = Utils.findRequiredViewAsType(source, R.id.btnBuscaProduto, "field 'btnBuscaProduto'", Button.class);
    target.cdPromocao = Utils.findRequiredViewAsType(source, R.id.cdPromocao, "field 'cdPromocao'", CardView.class);
    target.txtPromocao = Utils.findRequiredViewAsType(source, R.id.txtPromocao, "field 'txtPromocao'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnSubtraiQuantidade, "field 'btnSubtraiQuantidade' and method 'subtraiQuantidade'");
    target.btnSubtraiQuantidade = Utils.castView(view, R.id.btnSubtraiQuantidade, "field 'btnSubtraiQuantidade'", Button.class);
    view7f0a010e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.subtraiQuantidade();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSomaQuantidade, "field 'btnSomaQuantidade' and method 'somaQuantidade'");
    target.btnSomaQuantidade = Utils.castView(view, R.id.btnSomaQuantidade, "field 'btnSomaQuantidade'", Button.class);
    view7f0a010d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.somaQuantidade();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCancelar, "field 'btnCancelar' and method 'cancelar'");
    target.btnCancelar = Utils.castView(view, R.id.btnCancelar, "field 'btnCancelar'", Button.class);
    view7f0a00ba = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cancelar();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnConfirmar, "field 'btnConfirmar' and method 'confirmar'");
    target.btnConfirmar = Utils.castView(view, R.id.btnConfirmar, "field 'btnConfirmar'", Button.class);
    view7f0a00c1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.confirmar();
      }
    });
    target.lymainCampanha = Utils.findRequiredViewAsType(source, R.id.lymainCampanha, "field 'lymainCampanha'", LinearLayout.class);
    target.lyCampanha = Utils.findRequiredViewAsType(source, R.id.lyCampanha, "field 'lyCampanha'", LinearLayout.class);
    target.lydsctoReais = Utils.findRequiredViewAsType(source, R.id.lydsctoReais, "field 'lydsctoReais'", LinearLayout.class);
    target.lydsctoVerba = Utils.findRequiredViewAsType(source, R.id.lydsctoVerba, "field 'lydsctoVerba'", LinearLayout.class);
    target.lyvalorIcmsST = Utils.findRequiredViewAsType(source, R.id.lyvalorIcmsST, "field 'lyvalorIcmsST'", LinearLayout.class);
    target.edtNomeCampanha = Utils.findRequiredViewAsType(source, R.id.edtNomeCampanha, "field 'edtNomeCampanha'", EditText.class);
    target.btnBuscaCampanha = Utils.findRequiredViewAsType(source, R.id.btnBuscaCampanha, "field 'btnBuscaCampanha'", Button.class);
    view = Utils.findRequiredView(source, R.id.btnInfoCampanha, "field 'btnInfoCampanha' and method 'infoCampanha'");
    target.btnInfoCampanha = Utils.castView(view, R.id.btnInfoCampanha, "field 'btnInfoCampanha'", Button.class);
    view7f0a00db = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.infoCampanha();
      }
    });
    target.lymainSku = Utils.findRequiredViewAsType(source, R.id.lymainSku, "field 'lymainSku'", LinearLayout.class);
    target.lySku = Utils.findRequiredViewAsType(source, R.id.lySku, "field 'lySku'", LinearLayout.class);
    target.spnSku = Utils.findRequiredViewAsType(source, R.id.spnSku, "field 'spnSku'", Spinner.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.view_pager_produto_img, "field 'mViewPager'", ViewPager.class);
    target.indicator = Utils.findRequiredViewAsType(source, R.id.circle_view_produto_img, "field 'indicator'", CircleIndicator.class);
    target.txtPesquisaProduto = Utils.findRequiredViewAsType(source, R.id.txtPesquisaProduto, "field 'txtPesquisaProduto'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityProdutoPedido target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.activity_produto_pedido = null;
    target.lyProduto = null;
    target.rbPorcentagem = null;
    target.rbReal = null;
    target.edtNomeProduto = null;
    target.edtTabelaPreco = null;
    target.edtQuantidade = null;
    target.edtValorProdutos = null;
    target.edtValorPrecoPago = null;
    target.edtDesconto = null;
    target.edtDescontoReais = null;
    target.edtDescontoVerba = null;
    target.edtvalorIcmsST = null;
    target.edtTotal = null;
    target.toolbar = null;
    target.btnBuscaProduto = null;
    target.cdPromocao = null;
    target.txtPromocao = null;
    target.btnSubtraiQuantidade = null;
    target.btnSomaQuantidade = null;
    target.btnCancelar = null;
    target.btnConfirmar = null;
    target.lymainCampanha = null;
    target.lyCampanha = null;
    target.lydsctoReais = null;
    target.lydsctoVerba = null;
    target.lyvalorIcmsST = null;
    target.edtNomeCampanha = null;
    target.btnBuscaCampanha = null;
    target.btnInfoCampanha = null;
    target.lymainSku = null;
    target.lySku = null;
    target.spnSku = null;
    target.mViewPager = null;
    target.indicator = null;
    target.txtPesquisaProduto = null;

    view7f0a010e.setOnClickListener(null);
    view7f0a010e = null;
    view7f0a010d.setOnClickListener(null);
    view7f0a010d = null;
    view7f0a00ba.setOnClickListener(null);
    view7f0a00ba = null;
    view7f0a00c1.setOnClickListener(null);
    view7f0a00c1 = null;
    view7f0a00db.setOnClickListener(null);
    view7f0a00db = null;
  }
}
