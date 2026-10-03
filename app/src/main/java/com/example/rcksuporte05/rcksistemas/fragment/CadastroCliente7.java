package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroCliente7 extends Fragment implements View.OnClickListener{

    @BindView(R.id.rdSim)
    RadioButton rdSim;
    @BindView(R.id.rdNao)
    RadioButton rdNao;
    @BindView(R.id.edtEmail4)
    EditText edtEmail4;
    @BindView(R.id.edtEmail1)
    EditText edtEmail1;
    @BindView(R.id.edtEmail5)
    EditText edtEmail5;
    @BindView(R.id.edtEmail3)
    EditText edtEmail3;
    @BindView(R.id.edtEmail2)
    EditText edtEmail2;
    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente7_2026, container, false);
        ButterKnife.bind(this, view);

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(this);
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            setEditTextFocusable(false);
        }
        setDados();
        ClienteHelper.setCadastroCliente7(this);
        return view;
    }

    private void setDados() {
        if (ClienteHelper.getCliente().getNfe_email_enviar() != null && !ClienteHelper.getCliente().getNfe_email_enviar().trim().isEmpty()) {
            rdSim.setChecked(ClienteHelper.getCliente().getNfe_email_enviar().equals("S") ? true : false);
            rdNao.setChecked(ClienteHelper.getCliente().getNfe_email_enviar().equals("N") ? true : false);
        }

        if (ClienteHelper.getCliente().getNfe_email_um() != null && !ClienteHelper.getCliente().getNfe_email_um().trim().isEmpty())
            edtEmail1.setText(ClienteHelper.getCliente().getNfe_email_um());
        if (ClienteHelper.getCliente().getNfe_email_dois() != null && !ClienteHelper.getCliente().getNfe_email_dois().trim().isEmpty())
            edtEmail2.setText(ClienteHelper.getCliente().getNfe_email_dois());
        if (ClienteHelper.getCliente().getNfe_email_tres() != null && !ClienteHelper.getCliente().getNfe_email_tres().trim().isEmpty())
            edtEmail3.setText(ClienteHelper.getCliente().getNfe_email_tres());
        if (ClienteHelper.getCliente().getNfe_email_quatro() != null && !ClienteHelper.getCliente().getNfe_email_quatro().trim().isEmpty())
            edtEmail4.setText(ClienteHelper.getCliente().getNfe_email_quatro());
        if (ClienteHelper.getCliente().getNfe_email_cinco() != null && !ClienteHelper.getCliente().getNfe_email_cinco().trim().isEmpty())
            edtEmail5.setText(ClienteHelper.getCliente().getNfe_email_cinco());

    }
    private void setEditTextFocusable(boolean b) {
        rdSim.setClickable(b);
        rdNao.setClickable(b);
        edtEmail4.setFocusable(b);
        edtEmail1.setFocusable(b);
        edtEmail5.setFocusable(b);
        edtEmail3.setFocusable(b);
        edtEmail2.setFocusable(b);

    }
    public void addDadosDaFrame() {

        if (rdSim.isChecked())
            ClienteHelper.getCliente().setNfe_email_enviar("S");
        else if (rdNao.isChecked())
            ClienteHelper.getCliente().setNfe_email_enviar("N");

        ClienteHelper.getCliente().setNfe_email_quatro(!edtEmail4.getText().toString().trim().isEmpty() ? edtEmail4.getText().toString().toLowerCase() : null);
        ClienteHelper.getCliente().setNfe_email_um(!edtEmail1.getText().toString().trim().isEmpty() ? edtEmail1.getText().toString().toLowerCase() : null);
        ClienteHelper.getCliente().setNfe_email_cinco(!edtEmail5.getText().toString().trim().isEmpty() ? edtEmail5.getText().toString().toLowerCase() : null);
        ClienteHelper.getCliente().setNfe_email_tres(!edtEmail3.getText().toString().trim().isEmpty() ? edtEmail3.getText().toString().toLowerCase() : null);
        ClienteHelper.getCliente().setNfe_email_dois(!edtEmail2.getText().toString().trim().isEmpty() ? edtEmail2.getText().toString().toLowerCase() : null);
    }

    @Override
    public void onClick(View view) {
        if ( view == btnContinuar) {
            addDadosDaFrame();
            DBHelper db = new DBHelper(getActivity());
            if (ClienteHelper.getCliente().getFinalizado().equals("S"))
                ClienteHelper.getCliente().setAlterado("S");
            ClienteDAO clienteDAO = new ClienteDAO(db);
            clienteDAO.update(ClienteHelper.getCliente());
            ClienteHelper.moveTela(3);
        }
    }
}
