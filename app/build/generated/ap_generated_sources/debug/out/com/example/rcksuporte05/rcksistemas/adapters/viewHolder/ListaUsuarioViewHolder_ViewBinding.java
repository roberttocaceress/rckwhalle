// Generated code from Butter Knife. Do not modify!
package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListaUsuarioViewHolder_ViewBinding implements Unbinder {
  private ListaUsuarioViewHolder target;

  @UiThread
  public ListaUsuarioViewHolder_ViewBinding(ListaUsuarioViewHolder target, View source) {
    this.target = target;

    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", ImageView.class);
    target.edtLogin = Utils.findRequiredViewAsType(source, R.id.edtLogin, "field 'edtLogin'", TextInputEditText.class);
    target.edtSenha = Utils.findRequiredViewAsType(source, R.id.edtSenha, "field 'edtSenha'", TextInputEditText.class);
    target.edtEmail = Utils.findRequiredViewAsType(source, R.id.edtEmail, "field 'edtEmail'", TextInputEditText.class);
    target.edtSaldoVerba = Utils.findRequiredViewAsType(source, R.id.edtSaldoVerba, "field 'edtSaldoVerba'", TextInputEditText.class);
    target.edtDataVerba = Utils.findRequiredViewAsType(source, R.id.edtDataVerba, "field 'edtDataVerba'", TextInputEditText.class);
    target.swtAtivo = Utils.findRequiredViewAsType(source, R.id.swtAtivo, "field 'swtAtivo'", Switch.class);
    target.swtBiometria = Utils.findRequiredViewAsType(source, R.id.swtBiometria, "field 'swtBiometria'", Switch.class);
    target.txvSincCliente = Utils.findRequiredViewAsType(source, R.id.txvSincCliente, "field 'txvSincCliente'", TextView.class);
    target.txvSincProduto = Utils.findRequiredViewAsType(source, R.id.txvSincProduto, "field 'txvSincProduto'", TextView.class);
    target.txvSincImagem = Utils.findRequiredViewAsType(source, R.id.txvSincImagem, "field 'txvSincImagem'", TextView.class);
    target.txvSincCobranca = Utils.findRequiredViewAsType(source, R.id.txvSincCobranca, "field 'txvSincCobranca'", TextView.class);
    target.txvVersaoApp = Utils.findRequiredViewAsType(source, R.id.txvAparelhoVersaoApp, "field 'txvVersaoApp'", TextView.class);
    target.img_avalia_1 = Utils.findRequiredViewAsType(source, R.id.img_avalia_1, "field 'img_avalia_1'", ImageView.class);
    target.img_avalia_2 = Utils.findRequiredViewAsType(source, R.id.img_avalia_2, "field 'img_avalia_2'", ImageView.class);
    target.img_avalia_3 = Utils.findRequiredViewAsType(source, R.id.img_avalia_3, "field 'img_avalia_3'", ImageView.class);
    target.img_avalia_4 = Utils.findRequiredViewAsType(source, R.id.img_avalia_4, "field 'img_avalia_4'", ImageView.class);
    target.img_avalia_5 = Utils.findRequiredViewAsType(source, R.id.img_avalia_5, "field 'img_avalia_5'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListaUsuarioViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgAvatar = null;
    target.edtLogin = null;
    target.edtSenha = null;
    target.edtEmail = null;
    target.edtSaldoVerba = null;
    target.edtDataVerba = null;
    target.swtAtivo = null;
    target.swtBiometria = null;
    target.txvSincCliente = null;
    target.txvSincProduto = null;
    target.txvSincImagem = null;
    target.txvSincCobranca = null;
    target.txvVersaoApp = null;
    target.img_avalia_1 = null;
    target.img_avalia_2 = null;
    target.img_avalia_3 = null;
    target.img_avalia_4 = null;
    target.img_avalia_5 = null;
  }
}
