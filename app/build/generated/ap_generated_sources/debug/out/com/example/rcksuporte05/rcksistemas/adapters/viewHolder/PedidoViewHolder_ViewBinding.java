// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PedidoViewHolder_ViewBinding implements Unbinder {
  private PedidoViewHolder target;

  @UiThread
  public PedidoViewHolder_ViewBinding(PedidoViewHolder target, View source) {
    this.target = target;

    target.txtIdPedido = Utils.findRequiredViewAsType(source, R.id.edtNumeroPedido, "field 'txtIdPedido'", TextView.class);
    target.txtNomeCliente = Utils.findRequiredViewAsType(source, R.id.txtNomeCliente, "field 'txtNomeCliente'", TextView.class);
    target.txtEmpresaPedido = Utils.findRequiredViewAsType(source, R.id.txtEmpresaPedido, "field 'txtEmpresaPedido'", TextView.class);
    target.txtFantasiaCliente = Utils.findRequiredViewAsType(source, R.id.txtFantasiaCliente, "field 'txtFantasiaCliente'", TextView.class);
    target.txtPrecoPedido = Utils.findRequiredViewAsType(source, R.id.txtPrecoPedido, "field 'txtPrecoPedido'", TextView.class);
    target.txtDataEntrega = Utils.findRequiredViewAsType(source, R.id.txtDataEntrega, "field 'txtDataEntrega'", TextView.class);
    target.txtDataEmissaoPedido = Utils.findRequiredViewAsType(source, R.id.txtDataEmissaoPedido, "field 'txtDataEmissaoPedido'", TextView.class);
    target.itemListaPedido = Utils.findRequiredViewAsType(source, R.id.item_lista_pedido, "field 'itemListaPedido'", LinearLayout.class);
    target.cor = Utils.findRequiredView(source, R.id.cor, "field 'cor'");
    target.btnExcluir = Utils.findRequiredViewAsType(source, R.id.btnExcluir, "field 'btnExcluir'", Button.class);
    target.btnEnviar = Utils.findRequiredViewAsType(source, R.id.btnEnviar, "field 'btnEnviar'", Button.class);
    target.btnDuplic = Utils.findRequiredViewAsType(source, R.id.btnDuplic, "field 'btnDuplic'", Button.class);
    target.btnCompartilhar = Utils.findRequiredViewAsType(source, R.id.btnCompartilhar, "field 'btnCompartilhar'", Button.class);
    target.btnRastreio = Utils.findRequiredViewAsType(source, R.id.btnRastreio, "field 'btnRastreio'", Button.class);
    target.btnCopiaSegura = Utils.findRequiredViewAsType(source, R.id.btnCopiaSegura, "field 'btnCopiaSegura'", Button.class);
    target.txtCondicaoPagamento = Utils.findRequiredViewAsType(source, R.id.txtCondicaoPagamento, "field 'txtCondicaoPagamento'", TextView.class);
    target.txtOperacao = Utils.findRequiredViewAsType(source, R.id.txtOperacao, "field 'txtOperacao'", TextView.class);
    target.txtMoeda = Utils.findRequiredViewAsType(source, R.id.txtMoeda, "field 'txtMoeda'", TextView.class);
    target.txtStatus = Utils.findRequiredViewAsType(source, R.id.txtStatus, "field 'txtStatus'", TextView.class);
    target.img_data_emissao = Utils.findRequiredViewAsType(source, R.id.img_data_emissao, "field 'img_data_emissao'", ImageView.class);
    target.img_data_entrega = Utils.findRequiredViewAsType(source, R.id.img_data_entrega, "field 'img_data_entrega'", ImageView.class);
    target.abandonado = Utils.findRequiredViewAsType(source, R.id.abandonado, "field 'abandonado'", LinearLayout.class);
    target.txvAbandonado = Utils.findRequiredViewAsType(source, R.id.txvAbandonado, "field 'txvAbandonado'", TextView.class);
    target.lyEntrega = Utils.findRequiredViewAsType(source, R.id.lyEntrega, "field 'lyEntrega'", LinearLayout.class);
    target.btnLinhaTempo = Utils.findRequiredViewAsType(source, R.id.btnLinhaTempo, "field 'btnLinhaTempo'", Button.class);
    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PedidoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtIdPedido = null;
    target.txtNomeCliente = null;
    target.txtEmpresaPedido = null;
    target.txtFantasiaCliente = null;
    target.txtPrecoPedido = null;
    target.txtDataEntrega = null;
    target.txtDataEmissaoPedido = null;
    target.itemListaPedido = null;
    target.cor = null;
    target.btnExcluir = null;
    target.btnEnviar = null;
    target.btnDuplic = null;
    target.btnCompartilhar = null;
    target.btnRastreio = null;
    target.btnCopiaSegura = null;
    target.txtCondicaoPagamento = null;
    target.txtOperacao = null;
    target.txtMoeda = null;
    target.txtStatus = null;
    target.img_data_emissao = null;
    target.img_data_entrega = null;
    target.abandonado = null;
    target.txvAbandonado = null;
    target.lyEntrega = null;
    target.btnLinhaTempo = null;
    target.itemView = null;
  }
}
