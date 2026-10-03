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

public class ClientesViewHolder_ViewBinding implements Unbinder {
  private ClientesViewHolder target;

  @UiThread
  public ClientesViewHolder_ViewBinding(ClientesViewHolder target, View source) {
    this.target = target;

    target.textViewNome = Utils.findRequiredViewAsType(source, R.id.nomeListaCliente, "field 'textViewNome'", TextView.class);
    target.txtDataUltimaCompra = Utils.findRequiredViewAsType(source, R.id.txtDataUltimaCompra, "field 'txtDataUltimaCompra'", TextView.class);
    target.textViewNomeFantasia = Utils.findRequiredViewAsType(source, R.id.textViewNomeFantasia, "field 'textViewNomeFantasia'", TextView.class);
    target.telefoneCliente = Utils.findRequiredViewAsType(source, R.id.telefoneCliente, "field 'telefoneCliente'", TextView.class);
    target.cnpjCliente = Utils.findRequiredViewAsType(source, R.id.cnpjCliente, "field 'cnpjCliente'", TextView.class);
    target.cidadeCliente = Utils.findRequiredViewAsType(source, R.id.cidadeCliente, "field 'cidadeCliente'", TextView.class);
    target.txtClienteAguarda = Utils.findRequiredViewAsType(source, R.id.txtClienteAguarda, "field 'txtClienteAguarda'", TextView.class);
    target.idCliente = Utils.findRequiredViewAsType(source, R.id.idCliente, "field 'idCliente'", TextView.class);
    target.nomeTabelaPreco = Utils.findRequiredViewAsType(source, R.id.nomeTabelaPreco, "field 'nomeTabelaPreco'", TextView.class);
    target.txtCategoria = Utils.findRequiredViewAsType(source, R.id.txtCategoria, "field 'txtCategoria'", TextView.class);
    target.txtSegmento = Utils.findRequiredViewAsType(source, R.id.txtSegmento, "field 'txtSegmento'", TextView.class);
    target.imStatus = Utils.findRequiredViewAsType(source, R.id.imStatus, "field 'imStatus'", ImageView.class);
    target.imgFinanceiroVencendo = Utils.findRequiredViewAsType(source, R.id.imgFinanceiroVencendo, "field 'imgFinanceiroVencendo'", ImageView.class);
    target.imgFinanceiroVencido = Utils.findRequiredViewAsType(source, R.id.imgFinanceiroVencido, "field 'imgFinanceiroVencido'", ImageView.class);
    target.imgPedidoAnalise = Utils.findRequiredViewAsType(source, R.id.imgPedidoAnalise, "field 'imgPedidoAnalise'", ImageView.class);
    target.imgPromocaoCliente = Utils.findRequiredViewAsType(source, R.id.imgPromocaoCliente, "field 'imgPromocaoCliente'", ImageView.class);
    target.imgClienteBloqueado = Utils.findRequiredViewAsType(source, R.id.imgClienteBloqueado, "field 'imgClienteBloqueado'", ImageView.class);
    target.itemView = Utils.findRequiredViewAsType(source, R.id.lyCliente, "field 'itemView'", LinearLayout.class);
    target.lyBotoes = Utils.findRequiredViewAsType(source, R.id.botoes, "field 'lyBotoes'", LinearLayout.class);
    target.lyDownload = Utils.findRequiredViewAsType(source, R.id.lyDownload, "field 'lyDownload'", LinearLayout.class);
    target.lyChamada = Utils.findRequiredViewAsType(source, R.id.lyChamada, "field 'lyChamada'", LinearLayout.class);
    target.lyGps = Utils.findRequiredViewAsType(source, R.id.lyGps, "field 'lyGps'", LinearLayout.class);
    target.lyEmail = Utils.findOptionalViewAsType(source, R.id.lyEmail, "field 'lyEmail'", LinearLayout.class);
    target.lyFinanceiro = Utils.findOptionalViewAsType(source, R.id.lyFinanceiro, "field 'lyFinanceiro'", LinearLayout.class);
    target.lyNovoPedido = Utils.findRequiredViewAsType(source, R.id.lyNovoPedido, "field 'lyNovoPedido'", LinearLayout.class);
    target.btnDownload = Utils.findRequiredViewAsType(source, R.id.btnDownload, "field 'btnDownload'", Button.class);
    target.btnChamada = Utils.findRequiredViewAsType(source, R.id.btnChamada, "field 'btnChamada'", Button.class);
    target.btnGps = Utils.findRequiredViewAsType(source, R.id.btnGps, "field 'btnGps'", Button.class);
    target.btnEmail = Utils.findOptionalViewAsType(source, R.id.btnEmail, "field 'btnEmail'", Button.class);
    target.btnFinanceiro = Utils.findRequiredViewAsType(source, R.id.btnFinanceiro, "field 'btnFinanceiro'", Button.class);
    target.btnNovoPedido = Utils.findRequiredViewAsType(source, R.id.btnNovoPedido, "field 'btnNovoPedido'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ClientesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textViewNome = null;
    target.txtDataUltimaCompra = null;
    target.textViewNomeFantasia = null;
    target.telefoneCliente = null;
    target.cnpjCliente = null;
    target.cidadeCliente = null;
    target.txtClienteAguarda = null;
    target.idCliente = null;
    target.nomeTabelaPreco = null;
    target.txtCategoria = null;
    target.txtSegmento = null;
    target.imStatus = null;
    target.imgFinanceiroVencendo = null;
    target.imgFinanceiroVencido = null;
    target.imgPedidoAnalise = null;
    target.imgPromocaoCliente = null;
    target.imgClienteBloqueado = null;
    target.itemView = null;
    target.lyBotoes = null;
    target.lyDownload = null;
    target.lyChamada = null;
    target.lyGps = null;
    target.lyEmail = null;
    target.lyFinanceiro = null;
    target.lyNovoPedido = null;
    target.btnDownload = null;
    target.btnChamada = null;
    target.btnGps = null;
    target.btnEmail = null;
    target.btnFinanceiro = null;
    target.btnNovoPedido = null;
  }
}
