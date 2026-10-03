package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaUsuarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.imgAvatar)
    public ImageView imgAvatar;
    //@BindView(R.id.txvUsuario)
    //public TextView txvUsuario;

    @BindView(R.id.edtLogin)
    public TextInputEditText edtLogin;
    @BindView(R.id.edtSenha)
    public TextInputEditText edtSenha;
    @BindView(R.id.edtEmail)
    public TextInputEditText edtEmail;

    @BindView(R.id.edtSaldoVerba)
    public TextInputEditText edtSaldoVerba;

    @BindView(R.id.edtDataVerba)
    public TextInputEditText edtDataVerba;

    @BindView(R.id.swtAtivo)
    public Switch swtAtivo;
    @BindView(R.id.swtBiometria)
    public Switch swtBiometria;

    @BindView(R.id.txvSincCliente)
    public TextView txvSincCliente;
    @BindView(R.id.txvSincProduto)
    public TextView txvSincProduto;
    @BindView(R.id.txvSincImagem)
    public TextView txvSincImagem;

    @BindView(R.id.txvSincCobranca)
    public TextView txvSincCobranca;

    @BindView(R.id.txvAparelhoVersaoApp)
    public TextView txvVersaoApp;

    //@BindView(R.id.btnConfirmar)
    //public Button btnConfirmar;

    @BindView(R.id.img_avalia_1)
    public ImageView img_avalia_1;

    @BindView(R.id.img_avalia_2)
    public ImageView img_avalia_2;

    @BindView(R.id.img_avalia_3)
    public ImageView img_avalia_3;

    @BindView(R.id.img_avalia_4)
    public ImageView img_avalia_4;

    @BindView(R.id.img_avalia_5)
    public ImageView img_avalia_5;

    public ListaUsuarioViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {
    }
}
