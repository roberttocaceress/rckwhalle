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

public class CobrancaViewHolder_ViewBinding implements Unbinder {
  private CobrancaViewHolder target;

  @UiThread
  public CobrancaViewHolder_ViewBinding(CobrancaViewHolder target, View source) {
    this.target = target;

    target.txtRazaoSocial = Utils.findRequiredViewAsType(source, R.id.txtRazaoSocial, "field 'txtRazaoSocial'", TextView.class);
    target.txtTelefone = Utils.findRequiredViewAsType(source, R.id.txtTelefone, "field 'txtTelefone'", TextView.class);
    target.txtEndereco = Utils.findRequiredViewAsType(source, R.id.txtEndereco, "field 'txtEndereco'", TextView.class);
    target.txtVencimento = Utils.findRequiredViewAsType(source, R.id.txtVencimento, "field 'txtVencimento'", TextView.class);
    target.txvPagamento = Utils.findRequiredViewAsType(source, R.id.txvPagamento, "field 'txvPagamento'", TextView.class);
    target.txvValorDebito = Utils.findRequiredViewAsType(source, R.id.txvValorDebito, "field 'txvValorDebito'", TextView.class);
    target.txvValorRecebido = Utils.findRequiredViewAsType(source, R.id.txvValorRecebido, "field 'txvValorRecebido'", TextView.class);
    target.txvValorSaldo = Utils.findRequiredViewAsType(source, R.id.txvValorSaldo, "field 'txvValorSaldo'", TextView.class);
    target.txvReagendamento = Utils.findRequiredViewAsType(source, R.id.txvReagendamento, "field 'txvReagendamento'", TextView.class);
    target.lyDadosCobranca = Utils.findRequiredViewAsType(source, R.id.lyDadosCobranca, "field 'lyDadosCobranca'", LinearLayout.class);
    target.itemView = Utils.findRequiredViewAsType(source, R.id.itemView, "field 'itemView'", LinearLayout.class);
    target.txvUltimaCompra = Utils.findRequiredViewAsType(source, R.id.txvUltimaCompra, "field 'txvUltimaCompra'", TextView.class);
    target.imgStatusCliente = Utils.findRequiredViewAsType(source, R.id.imgStatusCliente, "field 'imgStatusCliente'", ImageView.class);
    target.imgStatus = Utils.findRequiredViewAsType(source, R.id.imgStatus, "field 'imgStatus'", ImageView.class);
    target.imgCheck = Utils.findRequiredViewAsType(source, R.id.imgCheck, "field 'imgCheck'", ImageView.class);
    target.imgReagendado = Utils.findRequiredViewAsType(source, R.id.imgReagendado, "field 'imgReagendado'", ImageView.class);
    target.ivGps = Utils.findRequiredViewAsType(source, R.id.ivGps, "field 'ivGps'", ImageView.class);
    target.ivChamada = Utils.findRequiredViewAsType(source, R.id.ivChamada, "field 'ivChamada'", ImageView.class);
    target.descricaoEndereco = Utils.findRequiredViewAsType(source, R.id.descricaoEndereco, "field 'descricaoEndereco'", TextView.class);
    target.descricaoTelefone = Utils.findRequiredViewAsType(source, R.id.descricaoTelefone, "field 'descricaoTelefone'", TextView.class);
    target.descricaoBairro = Utils.findRequiredViewAsType(source, R.id.descricaoBairro, "field 'descricaoBairro'", TextView.class);
    target.txtBairro = Utils.findRequiredViewAsType(source, R.id.txtBairro, "field 'txtBairro'", TextView.class);
    target.btnNovoPedido = Utils.findRequiredViewAsType(source, R.id.btnNovoPedido, "field 'btnNovoPedido'", Button.class);
    target.txtNovoPedido = Utils.findRequiredViewAsType(source, R.id.txtNovoPedido, "field 'txtNovoPedido'", TextView.class);
    target.lytNovoPedido = Utils.findRequiredViewAsType(source, R.id.lytNovoPedido, "field 'lytNovoPedido'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CobrancaViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtRazaoSocial = null;
    target.txtTelefone = null;
    target.txtEndereco = null;
    target.txtVencimento = null;
    target.txvPagamento = null;
    target.txvValorDebito = null;
    target.txvValorRecebido = null;
    target.txvValorSaldo = null;
    target.txvReagendamento = null;
    target.lyDadosCobranca = null;
    target.itemView = null;
    target.txvUltimaCompra = null;
    target.imgStatusCliente = null;
    target.imgStatus = null;
    target.imgCheck = null;
    target.imgReagendado = null;
    target.ivGps = null;
    target.ivChamada = null;
    target.descricaoEndereco = null;
    target.descricaoTelefone = null;
    target.descricaoBairro = null;
    target.txtBairro = null;
    target.btnNovoPedido = null;
    target.txtNovoPedido = null;
    target.lytNovoPedido = null;
  }
}
