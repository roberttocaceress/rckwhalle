package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaEmprasaClienteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.imgAvatar)
    public ImageView imgAvatar;
    //@BindView(R.id.txvUsuario)
    //public TextView txvUsuario;

    @BindView(R.id.edtIPUrl)
    public TextInputEditText edtIPUrl;
    @BindView(R.id.edtPorta)
    public TextInputEditText edtPorta;

    @BindView(R.id.edtRazaoSocial)
    public TextInputEditText edtRazaoSocial;

   /* @BindView(R.id.edtVersaoAPI)
    public TextInputEditText edtVersaoAPI;*/
    @BindView(R.id.spVersaoAPI)
    public Spinner spVersaoAPI;

    @BindView(R.id.swtConexaoSegura)
    public Switch swtConexaoSegura;

    @BindView(R.id.btnConfirmar)
    public Button btnConfirmar;


    public ListaEmprasaClienteViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {
    }
}
