package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PaisesDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.activity.CadastroClienteMain;
import com.example.rcksuporte05.rcksistemas.model.Pais;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroCliente3 extends Fragment implements  AdapterView.OnItemSelectedListener, View.OnClickListener {
    @BindView(R.id.edtPaisCobranca)
    Spinner spPaisCobranca;
    @BindView(R.id.edtUfCobranca)
    Spinner spUfCobranca;
    @BindView(R.id.edtMunicipioCobranca)
    Spinner spMunicipioCobranca;
    @BindView(R.id.edtLimiteCredito)
    EditText edtLimiteCredito;
    @BindView(R.id.edtContatoFinanceiro)
    EditText edtContatoFinanceiro;
    @BindView(R.id.edtEmailFinanceiro)
    EditText edtEmailFinanceiro;
    @BindView(R.id.edtEnderecoCobranca)
    EditText edtEnderecoCobranca;
    @BindView(R.id.edtNumero)
    EditText edtNumero;
    @BindView(R.id.edtBairro)
    EditText edtBairro;
    @BindView(R.id.edtComplemento)
    EditText edtComplemento;
    @BindView(R.id.edtCepCobranca)
    EditText edtCepCobranca;
    @BindView(R.id.btnHistoricoFinanceiro)
    Button btnHistoricoFinanceiro;
    @BindView(R.id.txtHistoricoFinanceiro)
    TextView txtHistoricoFinanceiro;
    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    private int[] listaUf = {R.array.AC,
            R.array.AL,
            R.array.AM,
            R.array.AP,
            R.array.BA,
            R.array.CE,
            R.array.DF,
            R.array.ES,
            R.array.EX,
            R.array.GO,
            R.array.MA,
            R.array.MG,
            R.array.MS,
            R.array.MT,
            R.array.PA,
            R.array.PB,
            R.array.PE,
            R.array.PI,
            R.array.PR,
            R.array.RJ,
            R.array.RN,
            R.array.RO,
            R.array.RR,
            R.array.RS,
            R.array.SC,
            R.array.SE,
            R.array.SP,
            R.array.TO};

    private ArrayAdapter municipioAdapter;
    private ArrayAdapter ufAdapter;
    private ArrayAdapter<Pais> paisAdapter;
    private List<Pais> listaPaises;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente3, container, false);
        ButterKnife.bind(this, view);

        btnHistoricoFinanceiro.setOnClickListener(this);
        txtHistoricoFinanceiro.setOnClickListener(this);
        db = new DBHelper(getActivity());
        PaisesDAO paisesDAO = new PaisesDAO(db);
        listaPaises = paisesDAO.getLista();
        paisAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, listaPaises);
        spPaisCobranca.setAdapter(paisAdapter);
        spPaisCobranca.setOnItemSelectedListener(this);

        ufAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
        spUfCobranca.setAdapter(ufAdapter);
        spUfCobranca.setOnItemSelectedListener(this);

        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[spUfCobranca.getSelectedItemPosition()]));
        spMunicipioCobranca.setAdapter(municipioAdapter);
        spMunicipioCobranca.setOnItemSelectedListener(this);;

        if (listaPaises.size() > 0)
            setPaisCobranca();
        setMunicipioCobranca();

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            setEditTextFocusable(false);
            if (ClienteHelper.getCliente().getLimite_credito() != null) {
                Float limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                edtLimiteCredito.setText("R$" + String.format("%.2f", limiteCredito));
            }
            if (ClienteHelper.getCliente().getCob_endereco_cep() != null) {
                String cep = ClienteHelper.getCliente().getCob_endereco_cep().trim().replaceAll("[^0-9]", "");
                edtCepCobranca.setText( cep.length() >= 8 ? (cep.substring(0, 5) + "-" + cep.substring(5)) : cep);
            }

        } else {
            btnHistoricoFinanceiro.setVisibility(View.GONE);
            txtHistoricoFinanceiro.setVisibility(View.GONE);

            if (ClienteHelper.getCliente().getLimite_credito() != null)
                edtLimiteCredito.setText(ClienteHelper.getCliente().getLimite_credito());

            if (ClienteHelper.getCliente().getCob_endereco_cep() != null)
                edtCepCobranca.setText(ClienteHelper.getCliente().getCob_endereco_cep());

            if (ClienteHelper.getCliente().getCob_endereco_id_pais() <= 0) {
                for (int i = 0; listaPaises.size() > i; i++)
                    if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                        spPaisCobranca.setSelection(i);
                        break;
                    }
            }


        }
        setDados();
        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(this);
        }
        System.gc();
        ClienteHelper.setCadastroCliente3(this);
        return view;
    }

    private void setDados() {
        edtContatoFinanceiro.setText( ClienteHelper.getCliente().getPessoa_contato_financeiro() != null ? ClienteHelper.getCliente().getPessoa_contato_financeiro() : null );
        edtEmailFinanceiro.setText( ClienteHelper.getCliente().getEmail_financeiro() != null ? ClienteHelper.getCliente().getEmail_financeiro(): null );
        edtEnderecoCobranca.setText( ClienteHelper.getCliente().getCob_endereco() != null ? ClienteHelper.getCliente().getCob_endereco() : null );
        edtNumero.setText( ClienteHelper.getCliente().getCob_endereco_numero() != null ? ClienteHelper.getCliente().getCob_endereco_numero() : null);
        edtBairro.setText( ClienteHelper.getCliente().getCob_endereco_bairro() != null ? ClienteHelper.getCliente().getCob_endereco_bairro() : null );
        edtComplemento.setText( ClienteHelper.getCliente().getCob_endereco_complemento() != null ? ClienteHelper.getCliente().getCob_endereco_complemento() : null );
    }

    private void setPaisCobranca() {

        if (ClienteHelper.getCliente().getCob_endereco_id_pais() > 0) {
            for (int i = 0; listaPaises.size() > i; i++)
                if (listaPaises.get(i).getId_pais().equals(String.valueOf(ClienteHelper.getCliente().getCob_endereco_id_pais()))) {
                    spPaisCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaPais(i);
                    break;
                }
        } else {
            for (int i = 0; listaPaises.size() > i; i++)
                if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                    spPaisCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaPais(i);
                    break;
                }
        }
    }

    private void setMunicipioCobranca() {
        if (ClienteHelper.getCliente().getCob_endereco_uf() != null && !ClienteHelper.getCliente().getCob_endereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++)
                if (ClienteHelper.getCliente().getCob_endereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    spUfCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    spMunicipioCobranca.setAdapter(municipioAdapter);
                    break;
                }
        } else if (ClienteHelper.getVendedor().getCob_endereco_uf() != null && !ClienteHelper.getVendedor().getCob_endereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++)
                if (ClienteHelper.getVendedor().getCob_endereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    spUfCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    spMunicipioCobranca.setAdapter(municipioAdapter);
                    break;
                }
        }

        if (ClienteHelper.getCliente().getNome_cob_municipio() != null && !ClienteHelper.getCliente().getNome_cob_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[spUfCobranca.getSelectedItemPosition()]).length > i; i++)
                if (ClienteHelper.getCliente().getNome_cob_municipio().equals(getResources().getStringArray(listaUf[spUfCobranca.getSelectedItemPosition()])[i])) {
                    spMunicipioCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaMunicipio(i);
                    break;
                }
        } else if (ClienteHelper.getVendedor().getNome_cob_municipio() != null && !ClienteHelper.getVendedor().getNome_cob_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[spUfCobranca.getSelectedItemPosition()]).length > i; i++)
                if (ClienteHelper.getVendedor().getNome_cob_municipio().equals(getResources().getStringArray(listaUf[spUfCobranca.getSelectedItemPosition()])[i])) {
                    spMunicipioCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaMunicipio(i);
                    break;
                }
        }

    }

    private void setEditTextFocusable(boolean b) {
        edtLimiteCredito.setFocusable(b);
        edtContatoFinanceiro.setFocusable(b);
        edtEmailFinanceiro.setFocusable(b);
        edtEnderecoCobranca.setFocusable(b);
        edtNumero.setFocusable(b);
        edtBairro.setFocusable(b);
        edtComplemento.setFocusable(b);
        edtCepCobranca.setFocusable(b);
        spMunicipioCobranca.setEnabled(b);
        spPaisCobranca.setEnabled(b);
        spUfCobranca.setEnabled(b);
    }

    public void addDadosDaFrame() {
        ClienteHelper.getCliente().setLimite_credito(!edtLimiteCredito.getText().toString().trim().isEmpty() ?
                edtLimiteCredito.getText().toString().replaceAll("[^0-9]", "") : null);
        ClienteHelper.getCliente().setPessoa_contato_financeiro(!edtContatoFinanceiro.getText().toString().trim().isEmpty() ?
                edtContatoFinanceiro.getText().toString().toUpperCase() : null);
        ClienteHelper.getCliente().setEmail_financeiro(!edtEmailFinanceiro.getText().toString().trim().isEmpty() ?
                edtEmailFinanceiro.getText().toString().toLowerCase() : null);
        ClienteHelper.getCliente().setCob_endereco(!edtEnderecoCobranca.getText().toString().trim().isEmpty() ?
                edtEnderecoCobranca.getText().toString().toUpperCase() : null);
        ClienteHelper.getCliente().setCob_endereco_numero(!edtNumero.getText().toString().trim().isEmpty() ?
                edtNumero.getText().toString() : null);
        ClienteHelper.getCliente().setCob_endereco_bairro(!edtBairro.getText().toString().trim().isEmpty() ?
                edtBairro.getText().toString().toUpperCase() : null);
        ClienteHelper.getCliente().setCob_endereco_complemento(!edtComplemento.getText().toString().trim().isEmpty() ?
                edtComplemento.getText().toString().toUpperCase() : null);
        ClienteHelper.getCliente().setCob_endereco_cep(!edtCepCobranca.getText().toString().trim().isEmpty() ?
                edtCepCobranca.getText().toString() : null);
        try {
            ClienteHelper.getCliente().setCob_endereco_id_pais(Integer.parseInt(listaPaises.get(spPaisCobranca.getSelectedItemPosition()).getId_pais()));
            ClienteHelper.getCliente().setCob_endereco_uf(ufAdapter.getItem(spUfCobranca.getSelectedItemPosition()).toString());
            ClienteHelper.getCliente().setNome_cob_municipio(municipioAdapter.getItem(spMunicipioCobranca.getSelectedItemPosition()).toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnHistoricoFinanceiro || view == txtHistoricoFinanceiro) {
            if (ClienteHelper.getCliente() != null && ClienteHelper.getCliente().getId_cadastro_servidor() > 0)
                try {
                    Intent intent = new Intent(getContext(), ActivityFinanceiroResumo.class);
                    HistoricoFinanceiroHelper.setCliente(ClienteHelper.getCliente());
                    System.gc();
                    getContext().startActivity(intent);
                    CadastroClienteMain cadastroClienteMain = new CadastroClienteMain();
                    cadastroClienteMain.finish();
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Financeiro não encontrado, por favor faça a sincronia e tente novamente!", Toast.LENGTH_LONG).show();
                }
            else
                Toast.makeText(getContext(), "Você precisa selecionar um cliente já efetivado para consultar seu historico financeiro", Toast.LENGTH_SHORT).show();

        } else if (view == btnContinuar) {
            addDadosDaFrame();
            if (ClienteHelper.getCliente().getFinalizado().equals("S"))
                ClienteHelper.getCliente().setAlterado("S");
            ClienteDAO clienteDAO = new ClienteDAO(db);
            clienteDAO.update(ClienteHelper.getCliente());
            ClienteHelper.moveTela(3);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch ( view.getId()) {
            case R.id.edtMunicipioCobranca:
                ClienteHelper.setPosicaoCobrancaMunicipio(position);
                break;
            case R.id.edtUfCobranca:
                if (ClienteHelper.getPosicaoCobrancaMunicipio() > -1 && spUfCobranca.getSelectedItemPosition() != ClienteHelper.getPosicaoCobrancaUf())
                    ClienteHelper.setPosicaoCobrancaMunicipio(0);
                if (paisAdapter.getItem(spPaisCobranca.getSelectedItemPosition()).getId_pais().equals("1058")) {
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[position]));
                    spMunicipioCobranca.setAdapter(municipioAdapter);
                }
                ClienteHelper.setPosicaoCobrancaUf(position);
                try {
                    spMunicipioCobranca.setSelection(ClienteHelper.getPosicaoCobrancaMunicipio());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.edtPaisCobranca:
                if (!paisAdapter.getItem(position).getId_pais().equals("1058")) {
                    ClienteHelper.setPosicaoCobrancaUf(0);
                    ClienteHelper.setPosicaoCobrancaMunicipio(0);
                    String[] uf = {"EX"};
                    ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, uf);
                    spUfCobranca.setAdapter(ufAdapter);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[8]));
                    spMunicipioCobranca.setAdapter(municipioAdapter);
                } else {
                    ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
                    spUfCobranca.setAdapter(ufAdapter);
                }
                ClienteHelper.setPosicaoCobrancaPais(position);
                try {
                    spUfCobranca.setSelection(ClienteHelper.getPosicaoCobrancaUf());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
