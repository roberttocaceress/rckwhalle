package com.example.rcksuporte05.rcksistemas.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.SegmentoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Segmento;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Mask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CadastroCliente1 extends Fragment {

    @BindView(R.id.edtData)
    public EditText edtData;
    @BindView(R.id.edtNomeFantasia)
    public EditText edtNomeFantasia;
    @BindView(R.id.edtCpfCnpj)
    public EditText edtCpfCnpj;
    @BindView(R.id.edtTelefonePrincipal)
    public EditText edtTelefonePrincipal;
    @BindView(R.id.edtTelefone1)
    public EditText edtTelefone1;
    @BindView(R.id.edtTelefone2)
    public EditText edtTelefone2;
    @BindView(R.id.edtPessoaContato)
    public EditText edtPessoaContato;
    @BindView(R.id.edtEmailPrincipal)
    public EditText edtEmailPrincipal;
    @BindView(R.id.edtNomeCliente)
    public EditText edtNomeCliente;
    @BindView(R.id.edtInscEstadual)
    public EditText edtInscEstadual;
    @BindView(R.id.rdFisica)
    public RadioButton rdFisica;
    @BindView(R.id.rdJuridica)
    public RadioButton rdJuridica;
    @BindView(R.id.txtCpfCnpj)
    public TextView txtCpfCnpj;
    @BindView(R.id.txtId)
    TextView txtId;
    @BindView(R.id.txtNomeCliente)
    TextView txtNomeCliente;
    @BindView(R.id.spIe)
    Spinner spIe;
    @BindView(R.id.spCategoria)
    Spinner spCategoria;
    @BindView(R.id.txtCategoria)
    TextView txtCategoria;


    @BindView(R.id.spSegmento)
    Spinner spSegmento;
    @BindView(R.id.txtSegmento)
    TextView txtSegmento;


    @BindView(R.id.txtData)
    TextView txtData;
    @BindView(R.id.btnLigar1)
    Button btnLigar1;
    @BindView(R.id.btnLigar2)
    Button btnLigar2;
    @BindView(R.id.btnLigar3)
    Button btnLigar3;
    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    private ArrayAdapter arrayIe;
    private ArrayAdapter arrayCategoria;
    private ArrayAdapter arraySegmento;
    private String[] contribuinte = {"Com inscrição estadual", "Isento de inscrição", "Sem inscrição estadual"};
    private List<Categoria> listaCategoria = new ArrayList<>();
    private List<Segmento> listaSegmento = new ArrayList<>();
    private View view;
    private DBHelper db;
    private int indCategoria = 0;
    private int indSegmento = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cadastro_cliente1_2026, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            btnContinuar.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this, view);
        db = new DBHelper(getActivity());
        CategoriaDAO categoriaDAO = new CategoriaDAO(db);
        ClienteDAO clienteDAO = new ClienteDAO(db);
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin( db ));
        try {
            try {
                if (ClienteHelper.getCliente().getId_cadastro_servidor() < 0)
                    listaCategoria = categoriaDAO.getListaNovo(true);
                else {
                    if (ClienteHelper.getCliente().getF_cliente().equalsIgnoreCase("S"))
                        listaCategoria = categoriaDAO.getLista(true);
                    else
                        listaCategoria = categoriaDAO.getListaNovo(true);
                }
            } catch ( Exception e ) {
                listaCategoria = categoriaDAO.getListaNovo(true);
            }
            arrayCategoria = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, listaCategoria);
            spCategoria.setAdapter(arrayCategoria);
            if (listaCategoria.size() == 2)
                spCategoria.setSelection(1);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            showMsgAlerta("Você precisa ter sincronizado pelo menos uma vez");
        }
        try {
            SegmentoDAO segmentoDAO = new SegmentoDAO(db);
            listaSegmento = segmentoDAO.getLista("SELECT * FROM TBL_CADASTRO_SETOR", true);
            arraySegmento = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, listaSegmento);
            spSegmento.setAdapter(arraySegmento);
            if (listaSegmento.size() == 2)
                spSegmento.setSelection(1);
        } catch (CursorIndexOutOfBoundsException| NullPointerException e) {
        }


        try {
            if (ClienteHelper.getCliente().getIdCategoria() > 0)
                for (int i = 0; listaCategoria.size() > i; i++)
                    if (listaCategoria.get(i).getIdCategoria() == ClienteHelper.getCliente().getIdCategoria()) {
                        spCategoria.setSelection(i);
                        indCategoria = i;
                    }


        } catch (NullPointerException e) {
        } catch (Exception e) {
        }

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ( indCategoria >= 1 && i <= 0) {
                    Toast toast = Toast.makeText(getActivity(), "Atenção!!\nSelecione uma cátegoria", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();;
                }
                ClienteHelper.getCliente().setIdCategoria(listaCategoria.get(spCategoria.getSelectedItemPosition()).getIdCategoria());
                indCategoria = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        try {
            String id = ClienteHelper.getCliente().getId_setor();
            if (!ClienteHelper.getCliente().getId_setor().isEmpty())
                for (int i = 0; listaSegmento.size() > i; i++)
                    if (listaSegmento.get(i).getIdSetor().equalsIgnoreCase(ClienteHelper.getCliente().getId_setor())) {
                        spSegmento.setSelection(i);
                        indSegmento = i;
                    }
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }

        spSegmento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ( indSegmento >= 1 && i <= 0) {
                    Toast toast = Toast.makeText(getActivity(), "Atenção!!\nSelecione um segmento/setor", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();;
                }
                ClienteHelper.getCliente().setId_setor(listaSegmento.get(spSegmento.getSelectedItemPosition()).getIdSetor());
                indSegmento = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            rdFisica.setClickable(false);
            rdJuridica.setClickable(false);
            edtCpfCnpj.setFocusable(false);
            if ( UsuarioHelper.getUsuario() == null ) {
                UsuarioHelper.setUsuario(new UsuarioBO().getUsuarioLogin(db));
            }
            try {
                ClienteHelper.setVendedor(clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE F_ID_VENDEDOR = " + UsuarioHelper.getUsuario().getId_quando_vendedor() + ";").get(0));
            } catch (CursorIndexOutOfBoundsException e) {
                Cliente clientev = new Cliente();
                EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
                EmpresaParametro empresa = empresaParametroDAO.getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice()).get(0);
                clientev.setEndereco_uf(empresa.getEndereco_uf());
                clientev.setId_pais(1058);
                ClienteHelper.setVendedor(clientev);
                e.printStackTrace();
            } catch ( Exception e ) {
                Cliente clientev = new Cliente();
                EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
                try {
                   EmpresaParametro empresa = empresaParametroDAO.getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice()).get(0);
                   clientev.setEndereco_uf(empresa.getEndereco_uf());
                   clientev.setId_pais(1058);
                   ClienteHelper.setVendedor(clientev);
                }catch (ArrayIndexOutOfBoundsException e1) {
                   clientev.setEndereco_uf("MS");
                   clientev.setId_pais(1058);
                   ClienteHelper.setVendedor(clientev);
                }
            }

            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addDadosFrame();
                    boolean validado = true;
                    if (ClienteHelper.getCliente().getPessoa_f_j() == null || ClienteHelper.getCliente().getPessoa_f_j().trim().isEmpty()) {
                        Toast.makeText(getContext(), "Escolher Pessoa Fisica ou Juridica é obrigatorio", Toast.LENGTH_LONG).show();
                        validado = false;
                    }

                    if (ClienteHelper.getCliente().getNome_cadastro() == null || ClienteHelper.getCliente().getNome_cadastro().trim().isEmpty()) {
                        edtNomeCliente.setError("Campo Obrigatorio");
                        edtNomeCliente.requestFocus();
                        validado = false;
                    }

                    if (ClienteHelper.getCliente().getNome_fantasia() == null || ClienteHelper.getCliente().getNome_fantasia().trim().isEmpty()) {
                        edtNomeFantasia.setError("Campo Obrigatorio");
                        edtNomeFantasia.requestFocus();
                        validado = false;
                    }

                    if (ClienteHelper.getCliente().getCpf_cnpj() == null || ClienteHelper.getCliente().getCpf_cnpj().trim().isEmpty()) {
                        edtCpfCnpj.setError("Campo Obrigatorio");
                        Toast.makeText(getActivity(), "Campo Obrigatorio", Toast.LENGTH_LONG).show();
                        edtCpfCnpj.requestFocus();
                        validado = false;
                    } else if (ClienteHelper.getCliente().getPessoa_f_j().equals("F")) {
                        if (!MascaraUtil.isValidCPF(ClienteHelper.getCliente().getCpf_cnpj())) {
                            edtCpfCnpj.setError("CPF Inválido");
                            Toast.makeText(getActivity(), "CPF Inválido", Toast.LENGTH_LONG).show();
                            edtCpfCnpj.requestFocus();
                            validado = false;
                        }
                    } else if (ClienteHelper.getCliente().getPessoa_f_j().equals("J")) {
                        if ( MascaraUtil.isAlphanumeric(Mask.unmask(ClienteHelper.getCliente().getCpf_cnpj()))) {
                            if (!MascaraUtil.isValidCNPJAlfaNumerico(Mask.unmask(ClienteHelper.getCliente().getCpf_cnpj()))) {
                                edtCpfCnpj.setError("CNPJ Inválido!");
                                Toast.makeText(getActivity(), "CNPJ Inválido!", Toast.LENGTH_LONG).show();
                                edtCpfCnpj.requestFocus();
                                validado = false;
                            }
                        } else {
                            if (!MascaraUtil.isValidCNPJ(Mask.unmask(ClienteHelper.getCliente().getCpf_cnpj()))) {
                                edtCpfCnpj.setError("CNPJ Inválido");
                                Toast.makeText(getActivity(), "CNPJ Inválido", Toast.LENGTH_LONG).show();
                                edtCpfCnpj.requestFocus();
                                validado = false;
                            }
                        }
                    }

                    if (!verificaCpfCnpj(ClienteHelper.getCliente().getCpf_cnpj())) {
                        edtCpfCnpj.setError("Já existe outro cliente com esse CPF/CNPJ");
                        Toast.makeText(getActivity(), "Já existe outro cliente com esse CPF/CNPJ", Toast.LENGTH_LONG).show();
                        edtCpfCnpj.requestFocus();
                        validado = false;
                    }

                    if (ClienteHelper.getCliente().getInd_da_ie_destinatario() != null && ClienteHelper.getCliente().getInd_da_ie_destinatario().equals("1")) {
                        if (Integer.parseInt(ClienteHelper.getCliente().getInd_da_ie_destinatario()) == 1) {
                            if (ClienteHelper.getCliente().getInscri_estadual() == null || ClienteHelper.getCliente().getInscri_estadual().trim().isEmpty()) {
                                edtInscEstadual.setError("Campo Obrigatorio");
                                edtInscEstadual.requestFocus();
                                validado = false;
                            }
                        }
                    }
                    try {
                        if (ClienteHelper.getCliente().getIdCategoria() <= 0 && listaCategoria.size() > 1) {
                            Toast.makeText(getActivity(), "Atenção!!\n Obrigátorio informar a categoria do cliente!", Toast.LENGTH_LONG).show();
                            validado = false;
                        }
                    } catch ( NullPointerException e) {
                        Toast.makeText(getActivity(), "Atenção!!\n Obrigátorio informar a categoria do cliente!", Toast.LENGTH_LONG).show();
                        validado = false;

                    }

                    try {
                        if (ClienteHelper.getCliente().getId_setor().equalsIgnoreCase("0")) {
                            Toast.makeText(getActivity(), "Atenção!!\n Obrigátorio informar o segmento/setor do cliente!", Toast.LENGTH_LONG).show();
                            validado = false;
                        }
                    }catch ( NullPointerException e) {
                        Toast.makeText(getActivity(), "Atenção!!\n Obrigátorio informar o segmento/setor do cliente!", Toast.LENGTH_LONG).show();
                        validado = false;

                    }


                    if (validado) {
                        EmpresaParametro empresaParametro = new EmpresaParametro();
                        EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(db);
                        try {
                            empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO WHERE ID = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                        } catch (  NullPointerException|IndexOutOfBoundsException e) {
                            showMsgAlerta("Falha ao cadastrar o cliente!!");
                            getActivity().finish();
                        } catch ( Exception e){
                            showMsgAlerta("Falha ao cadastrar o cliente!!");
                            getActivity().finish();
                        }
                        if (getActivity().getIntent().getIntExtra("prospect", 0) > 0)
                            db.deleteDados("TBL_CADASTRO", "FINALIZADO = ?", new String[]{"N"});
                        ClienteHelper.getCliente().setTipo_tabela_preco( empresaParametro.getTipo_tabela_preco());
                        ClienteHelper.getCliente().setUtiliza_tabela_preco(empresaParametro.getUtiliza_tabela_preco());
                        ClienteDAO clienteDAO = new ClienteDAO(db);
                        if ( listaCategoria.size() <= 1) {
                            ClienteHelper.getCliente().setIdCategoria(1);
                        }

                        if (ClienteHelper.getCliente().getId_cadastro() > 0) {
                            ClienteHelper.getCliente().setAtivo("S");
                            if (ClienteHelper.getCliente().getFinalizado().equals("S"))
                                ClienteHelper.getCliente().setAlterado("S");
                            clienteDAO.update(ClienteHelper.getCliente());
                        } else {
                            ClienteHelper.getCliente().setAtivo("S");
                            ClienteHelper.getCliente().setId_empresa(Integer.parseInt(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice()));
                            ClienteHelper.getCliente().setId_cadastro_servidor(-1);
                            ClienteHelper.getCliente().setId_prospect(0);
                            ClienteHelper.getCliente().setUsuario_data(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            ClienteHelper.getCliente().setFinalizado("N");
                            ClienteHelper.getCliente().setF_cliente("S");
                            ClienteHelper.getCliente().setF_fornecedor("N");
                            ClienteHelper.getCliente().setF_funcionario("N");
                            ClienteHelper.getCliente().setF_vendedor("N");
                            ClienteHelper.getCliente().setF_transportador("N");
                            ClienteHelper.getCliente().setId_entidade("1");
                            if ( ClienteHelper.getCliente().getNome_municipio() == null || ClienteHelper.getCliente().getNome_municipio().isEmpty()) {
                                ClienteHelper.getCliente().setEndereco_uf(ClienteHelper.getVendedor().getEndereco_uf());
                                ClienteHelper.getCliente().setNome_municipio(ClienteHelper.getVendedor().getNome_municipio());
                                ClienteHelper.getCliente().setId_pais(ClienteHelper.getVendedor().getId_pais());
                            }
                            int idCliente = (int) clienteDAO.add(ClienteHelper.getCliente());

                            if ( idCliente > 0) {
                                ClienteHelper.getCliente().setId_cadastro(idCliente/*db.contagem("SELECT MAX(ID_CADASTRO) FROM TBL_CADASTRO;"*/);
                                ClienteHelper.getCliente().setId_cliente_app_fdb(
                                        "V " +ClienteHelper.getCliente().getId_vendedor() + " DCAD " +db.pegaDataHoraAtual()
                                                +  " DBFSTORE " + "TP " + ClienteHelper.getCliente().getPessoa_f_j() + " "  + ClienteHelper.getCliente().getCpf_cnpj() );

                                //webCliente.setId_cliente_app_fdb("V " + webCliente.getId_vendedor()+ " DCAD " + db.pegaDataHoraAtual()
                                ///        +  " DBFSTORE " +  "TP " + webCliente.getPessoa_f_j() + " "  + webCliente.getCpf_cnpj() );
                                clienteDAO.updateIdFDB(ClienteHelper.getCliente(), String.valueOf(idCliente));
                                txtId.setText("ID: " + ClienteHelper.getCliente().getId_cadastro());
                            } else {
                                showMsgAlerta("Falha ao cadastrar o cliente!!");
                                getActivity().finish();
                            }
                        }
                        if (getActivity().getIntent().getIntExtra("prospect", 0) > 0) {
                            db.deleteDados("TBL_PROSPECT", "ID_PROSPECT = ?", new String[]{ String.valueOf(getActivity().getIntent().getIntExtra("prospect", 0))});
                            db.deleteDados("TBL_VISITA_PROSPECT", "ID_CADASTRO = ?", new String[]{ String.valueOf(getActivity().getIntent().getIntExtra("prospect", 0))});
                        }
                        ClienteHelper.moveTela(1);
                    }
                }
            });
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            btnLigar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fazerChamada(edtTelefonePrincipal.getText().toString(), ClienteHelper.getCliente().getNome_cadastro());
                }
            });

            btnLigar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fazerChamada(edtTelefone1.getText().toString(), ClienteHelper.getCliente().getNome_cadastro());
                }
            });

            btnLigar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fazerChamada(edtTelefone2.getText().toString(), ClienteHelper.getCliente().getNome_cadastro());
                }
            });

            rdFisica.setClickable(false);
            rdJuridica.setClickable(false);
            edtCpfCnpj.setFocusable(false);
            edtNomeFantasia.setFocusable(false);
            edtTelefonePrincipal.setFocusable(false);
            edtTelefone1.setFocusable(false);
            edtTelefone2.setFocusable(false);
            edtPessoaContato.setFocusable(false);
            edtEmailPrincipal.setFocusable(false);
            edtNomeCliente.setFocusable(false);
            edtInscEstadual.setFocusable(false);
            spIe.setEnabled(false);

            if (ClienteHelper.getCliente().getIdCategoria() <= 0) {
                spCategoria.setVisibility(View.INVISIBLE);
                txtCategoria.setText("CATEGORIA NÃO INFORMADA");
            } else
                spCategoria.setEnabled(false);

            txtId.setText("ID: " + ClienteHelper.getCliente().getId_cadastro_servidor());

            if (ClienteHelper.getCliente().getCpf_cnpj() != null && ClienteHelper.getCliente().getPessoa_f_j() != null)
                switch (ClienteHelper.getCliente().getPessoa_f_j()) {
                    case "J":
                        rdJuridica.setChecked(true);
                        try {
                            edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(ClienteHelper.getCliente().getCpf_cnpj()));
                        } catch (StringIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "F":
                        rdFisica.setChecked(true);
                        edtCpfCnpj.setText(MascaraUtil.mascaraCPF(ClienteHelper.getCliente().getCpf_cnpj()));
                        break;
                    default:
                        edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                        break;
                }

            if (ClienteHelper.getCliente().getTelefone_principal() != null) {
                String telefonePrincipal = ClienteHelper.getCliente().getTelefone_principal().trim().replaceAll("[^0-9]", "");
                if (telefonePrincipal.length() == 10)
                    edtTelefonePrincipal.setText("(" + telefonePrincipal.substring(0, 2) + ") " + telefonePrincipal.substring(2, 6) + "-" + telefonePrincipal.substring(6, 10));
                else if (telefonePrincipal.length() == 11)
                    edtTelefonePrincipal.setText("(" + telefonePrincipal.substring(0, 2) + ") " + telefonePrincipal.substring(2, 7) + "-" + telefonePrincipal.substring(7, 11));
                else if (telefonePrincipal.length() == 9 && !telefonePrincipal.contains("-"))
                    edtTelefonePrincipal.setText(telefonePrincipal.substring(0, 5) + "-" + telefonePrincipal.substring(5, 9));
                else if (telefonePrincipal.length() == 8)
                    edtTelefonePrincipal.setText(telefonePrincipal.substring(0, 4) + "-" + telefonePrincipal.substring(4, 8));
                else
                    edtTelefonePrincipal.setText(telefonePrincipal);
            }

            if (ClienteHelper.getCliente().getTelefone_dois() != null) {
                String telefone1 = ClienteHelper.getCliente().getTelefone_dois().trim().replaceAll("[^0-9]", "");
                if (telefone1.length() == 10)
                    edtTelefone1.setText("(" + telefone1.substring(0, 2) + ") " + telefone1.substring(2, 6) + "-" + telefone1.substring(6, 10));
                else if (telefone1.length() == 11)
                    edtTelefone1.setText("(" + telefone1.substring(0, 2) + ") " + telefone1.substring(2, 7) + "-" + telefone1.substring(7, 11));
                else if (telefone1.length() == 9 && !telefone1.contains("-"))
                    edtTelefone1.setText(telefone1.substring(0, 5) + "-" + telefone1.substring(5, 9));
                else if (telefone1.length() == 8)
                    edtTelefone1.setText(telefone1.substring(0, 4) + "-" + telefone1.substring(4, 8));
                else
                    edtTelefone1.setText(telefone1);
            }

            if (ClienteHelper.getCliente().getTelefone_tres() != null) {
                String telefone2 = ClienteHelper.getCliente().getTelefone_tres().trim().replaceAll("[^0-9]", "");
                if (telefone2.length() == 10)
                    edtTelefone2.setText("(" + telefone2.substring(0, 2) + ") " + telefone2.substring(2, 6) + "-" + telefone2.substring(6, 10));
                else if (telefone2.length() == 11)
                    edtTelefone2.setText("(" + telefone2.substring(0, 2) + ") " + telefone2.substring(2, 7) + "-" + telefone2.substring(7, 11));
                else if (telefone2.length() == 9 && !telefone2.contains("-"))
                    edtTelefone2.setText(telefone2.substring(0, 5) + "-" + telefone2.substring(5, 9));
                else if (telefone2.length() == 8)
                    edtTelefone2.setText(telefone2.substring(0, 4) + "-" + telefone2.substring(4, 8));
                else
                    edtTelefone2.setText(telefone2);
            }
        } else {
            txtId.setText("ID: " + ClienteHelper.getCliente().getId_cadastro());
            if (ClienteHelper.getCliente().getCpf_cnpj() != null && ClienteHelper.getCliente().getPessoa_f_j() != null) {
                switch (ClienteHelper.getCliente().getPessoa_f_j()) {
                    case "J":
                        rdJuridica.setChecked(true);
                        try {
                            edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(ClienteHelper.getCliente().getCpf_cnpj()));
                        } catch (StringIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "F":
                        rdFisica.setChecked(true);
                        edtCpfCnpj.setText(MascaraUtil.mascaraCPF(ClienteHelper.getCliente().getCpf_cnpj()));
                        break;
                    default:
                        edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                        break;
                }
            }

            btnLigar1.setVisibility(View.INVISIBLE);
            btnLigar2.setVisibility(View.INVISIBLE);
            btnLigar3.setVisibility(View.INVISIBLE);

            if (ClienteHelper.getCliente().getTelefone_principal() != null)
                edtTelefonePrincipal.setText((ClienteHelper.getCliente().getTelefone_principal()));
            if (ClienteHelper.getCliente().getTelefone_dois() != null)
                edtTelefone1.setText((ClienteHelper.getCliente().getTelefone_dois()));
            if (ClienteHelper.getCliente().getTelefone_tres() != null)
                edtTelefone2.setText((ClienteHelper.getCliente().getTelefone_tres()));

            edtData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraDatePickerDialog(getActivity(), edtData);
                }
            });

            edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        validaCpfCnpj();
                    else
                        edtCpfCnpj.setText(Mask.unmask(edtCpfCnpj.getText().toString()));//.replaceAll("[^0-9]", ""));
                }
            });

            spIe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        edtInscEstadual.setEnabled(false);
                        edtInscEstadual.setText("");
                    } else
                        edtInscEstadual.setEnabled(true);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        arrayIe = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, contribuinte);
        spIe.setAdapter(arrayIe);

        if (ClienteHelper.getCliente().getInd_da_ie_destinatario() != null) {
            try {
                spIe.setSelection(Integer.parseInt(ClienteHelper.getCliente().getInd_da_ie_destinatario()) - 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (ClienteHelper.getCliente().getNome_cadastro() != null)
            edtNomeCliente.setText(ClienteHelper.getCliente().getNome_cadastro());
        if (ClienteHelper.getCliente().getNome_fantasia() != null)
            edtNomeFantasia.setText(ClienteHelper.getCliente().getNome_fantasia());
        if (ClienteHelper.getCliente().getInscri_estadual() != null)
            edtInscEstadual.setText(ClienteHelper.getCliente().getInscri_estadual());
        if (ClienteHelper.getCliente().getPessoa_contato_principal() != null)
            edtPessoaContato.setText(ClienteHelper.getCliente().getPessoa_contato_principal());
        if (ClienteHelper.getCliente().getEmail_principal() != null)
            edtEmailPrincipal.setText(ClienteHelper.getCliente().getEmail_principal());
        if (ClienteHelper.getCliente().getData_aniversario() != null) {
            try {
                edtData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(ClienteHelper.getCliente().getData_aniversario())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (rdJuridica.isChecked())
            setTxtTipoPessoa("J");
        else if (rdFisica.isChecked())
            setTxtTipoPessoa("F");

        rdJuridica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    setTxtTipoPessoa("J");
            }
        });

        rdFisica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    setTxtTipoPessoa("F");
            }
        });
        System.gc();
        ClienteHelper.setCadastroCliente1(this);
        return (view);
    }


    @Override
    public void onResume() {
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //ClienteHelper.getCliente().setId_setor(null);
                //ClienteHelper.getCliente().setIdCategoria(-1);
                ClienteHelper.setPosicaoMunicipio(-1);

                return false;
            }


        });
        super.onResume();
    }

    private void preencheTela() {
        txtId.setText("ID: " + ClienteHelper.getCliente().getId_cadastro());

        if (ClienteHelper.getCliente().getCpf_cnpj() != null && ClienteHelper.getCliente().getPessoa_f_j() != null) {
            switch (ClienteHelper.getCliente().getPessoa_f_j()) {
                case "J":
                    rdJuridica.setChecked(true);
                    try {
                        edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                    } catch (StringIndexOutOfBoundsException | NullPointerException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "F":
                    rdFisica.setChecked(true);
                    try {
                        edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                    } catch (StringIndexOutOfBoundsException | NullPointerException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                    break;
            }
        }

        btnLigar1.setVisibility(View.INVISIBLE);
        btnLigar2.setVisibility(View.INVISIBLE);
        btnLigar3.setVisibility(View.INVISIBLE);

        if (ClienteHelper.getCliente().getTelefone_principal() != null)
            edtTelefonePrincipal.setText((ClienteHelper.getCliente().getTelefone_principal()));
        if (ClienteHelper.getCliente().getTelefone_dois() != null)
            edtTelefone1.setText((ClienteHelper.getCliente().getTelefone_dois()));
        if (ClienteHelper.getCliente().getTelefone_tres() != null)
            edtTelefone2.setText((ClienteHelper.getCliente().getTelefone_tres()));

        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraDatePickerDialog(getActivity(), edtData);
            }
        });

        edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    validaCpfCnpj();
                else
                    edtCpfCnpj.setText(Mask.unmask(edtCpfCnpj.getText().toString()));//.replaceAll("[^0-9]", ""));
            }
        });

        spIe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    edtInscEstadual.setEnabled(false);
                    edtInscEstadual.setText("");
                } else
                    edtInscEstadual.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayIe = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, contribuinte);
        spIe.setAdapter(arrayIe);

        if (ClienteHelper.getCliente().getInd_da_ie_destinatario() != null)
            try {
                spIe.setSelection(Integer.parseInt(ClienteHelper.getCliente().getInd_da_ie_destinatario()) - 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        if (ClienteHelper.getCliente().getNome_cadastro() != null)
            edtNomeCliente.setText(ClienteHelper.getCliente().getNome_cadastro());
        if (ClienteHelper.getCliente().getNome_fantasia() != null)
            edtNomeFantasia.setText(ClienteHelper.getCliente().getNome_fantasia());
        if (ClienteHelper.getCliente().getInscri_estadual() != null)
            edtInscEstadual.setText(ClienteHelper.getCliente().getInscri_estadual());
        if (ClienteHelper.getCliente().getPessoa_contato_principal() != null)
            edtPessoaContato.setText(ClienteHelper.getCliente().getPessoa_contato_principal());
        if (ClienteHelper.getCliente().getEmail_principal() != null)
            edtEmailPrincipal.setText(ClienteHelper.getCliente().getEmail_principal());
        if (ClienteHelper.getCliente().getData_aniversario() != null)
            try {
                edtData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(ClienteHelper.getCliente().getData_aniversario())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        if (rdJuridica.isChecked())
            setTxtTipoPessoa("J");
        else if (rdFisica.isChecked())
            setTxtTipoPessoa("F");

        rdJuridica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    setTxtTipoPessoa("J");
            }
        });

        rdFisica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    setTxtTipoPessoa("F");
            }
        });
    }
    private void setTxtTipoPessoa( String tipo) {
        if (tipo.equalsIgnoreCase("F")) {
            txtNomeCliente.setText("Nome");
            edtNomeCliente.setHint("Nome *");
            txtData.setText("Data nascimento");
            edtData.setHint("Data Nascimento *");
            txtCpfCnpj.setText("CPF");
            edtCpfCnpj.setHint("CPF *");
        } else {
            txtNomeCliente.setText("Razão social");
            edtNomeCliente.setHint("Razão Social *");
            txtData.setText("Data abertura");
            edtData.setHint("Data Abertura *");
            txtCpfCnpj.setText("CNPJ");
            edtCpfCnpj.setHint("CNPJ *");
        }
    }

    public void validaCpfCnpj() {
        if (rdFisica.isChecked()) {
            if (MascaraUtil.isValidCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")))
                edtCpfCnpj.setText(MascaraUtil.mascaraCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")));
            else {
                edtCpfCnpj.setError("CPF Inválido");
                Toast.makeText(getActivity(), "CPF Inválido", Toast.LENGTH_LONG).show();
            }
        } else if (rdJuridica.isChecked()) {
            if (MascaraUtil.isValidCNPJAlfaNumerico(Mask.unmask(edtCpfCnpj.getText().toString()))) {
                if (MascaraUtil.isValidCNPJAlfaNumerico(Mask.unmask(edtCpfCnpj.getText().toString())))
                    edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(Mask.unmask(edtCpfCnpj.getText().toString())));
                else {
                    edtCpfCnpj.setError("CNPJ Inválido");
                    Toast.makeText(getActivity(), "CNPJ Inválido", Toast.LENGTH_LONG).show();
                }

            } else {
                if (MascaraUtil.isValidCNPJ(Mask.unmask(edtCpfCnpj.getText().toString())))
                    edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(Mask.unmask(edtCpfCnpj.getText().toString())));
                else {
                    edtCpfCnpj.setError("CNPJ Inválido");
                    Toast.makeText(getActivity(), "CNPJ Inválido", Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Toast.makeText(getActivity(), "É necessário informar se é pessoa Física ou Jurídica", Toast.LENGTH_SHORT).show();
            rdFisica.requestFocus();
        }
    }

    @SuppressLint("ResourceType")
    public void addDadosFrame() {
        ClienteHelper.getCliente().setPessoa_f_j( rdFisica.isChecked() ? "F" : "J");

        if (!edtNomeCliente.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNome_cadastro(edtNomeCliente.getText().toString().toUpperCase());
        else
            ClienteHelper.getCliente().setNome_cadastro(null);
        if (!edtNomeFantasia.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNome_fantasia(edtNomeFantasia.getText().toString().toUpperCase());
        else
            ClienteHelper.getCliente().setNome_fantasia(null);

        if (!edtData.getText().toString().trim().isEmpty()) {
            try {
                ClienteHelper.getCliente().setData_aniversario(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(edtData.getText().toString())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else
            ClienteHelper.getCliente().setData_aniversario(null);

        if (!edtCpfCnpj.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setCpf_cnpj( Mask.unmask(edtCpfCnpj.getText().toString()));
                    //edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""));
        else
            ClienteHelper.getCliente().setCpf_cnpj(null);

        if (!edtTelefonePrincipal.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setTelefone_principal(edtTelefonePrincipal.getText().toString());
        else
            ClienteHelper.getCliente().setTelefone_principal(null);

        if (!edtTelefone1.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setTelefone_dois(edtTelefone1.getText().toString());
        else
            ClienteHelper.getCliente().setTelefone_dois(null);

        if (!edtTelefone2.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setTelefone_tres(edtTelefone2.getText().toString());
        else
            ClienteHelper.getCliente().setTelefone_tres(null);

        if (!edtPessoaContato.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setPessoa_contato_principal(edtPessoaContato.getText().toString().toUpperCase());
        else
            ClienteHelper.getCliente().setPessoa_contato_principal(null);

        if (!edtEmailPrincipal.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEmail_principal(edtEmailPrincipal.getText().toString().toLowerCase());
        else
            ClienteHelper.getCliente().setEmail_principal(null);

        if (!edtInscEstadual.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setInscri_estadual(edtInscEstadual.getText().toString());
        else
            ClienteHelper.getCliente().setInscri_estadual(null);

        ClienteHelper.getCliente().setId_vendedor(Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor()));

        ClienteHelper.getCliente().setInd_da_ie_destinatario(String.valueOf(spIe.getSelectedItemPosition() + 1));
        //ClienteHelper.getCliente().setId
        int x = listaCategoria.size();
        if (listaCategoria.size() > 0)
            try {
                //if (ClienteHelper.getCliente().getIdCategoria() >= 1)
                    ClienteHelper.getCliente().setIdCategoria(listaCategoria.get(spCategoria.getSelectedItemPosition()).getIdCategoria());
            } catch ( NullPointerException e) {
e.printStackTrace();
            } catch ( Exception e) {
e.printStackTrace();
            }
        if (listaSegmento.size() > 0)
            try {
                //if (!ClienteHelper.getCliente().getId_setor().isEmpty() || !ClienteHelper.getCliente().getId_setor().equalsIgnoreCase("0"))
                    ClienteHelper.getCliente().setId_setor(listaSegmento.get(spSegmento.getSelectedItemPosition()).getIdSetor());
            } catch ( NullPointerException e) {

            } catch ( Exception e) {

            }

    }

    public void fazerChamada(final String telefone, final String nome) {
        try {
            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty())
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11)
                    showMsgSimNao("Deseja ligar para " + nome + " usando o número " + telefone + " ?", telefone);
                else
                    showMsgAlerta("Este numero de telefone não é válido!");
            else
                showMsgAlerta("Nenhum numero de telefone informado!");
        } catch (Exception e) {
            showMsgAlerta("Nenhum numero de telefone informado/e ou numero inválido!");
        }
    }

    public void mostraDatePickerDialog(Context context, final EditText campoTexto) {
        final Calendar calendar;
        //Prepara data anterior caso ja tenha sido selecionada
        calendar = campoTexto.getTag() != null ? ((Calendar) campoTexto.getTag()) : Calendar.getInstance();
        //if (campoTexto.getTag() != null) {
            //calendar = ((Calendar) campoTexto.getTag());
        //} else {
            //calendar = Calendar.getInstance();
        //}

        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                campoTexto.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
                campoTexto.setTag(newDate);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public boolean verificaCpfCnpj(String cpfCnpj) {
        if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE CPF_CNPJ = '" + cpfCnpj + "' AND ID_CADASTRO <> " + ClienteHelper.getCliente().getId_cadastro() + " AND FINALIZADO = 'S';") > 0)
            return false;
        return true;
    }


    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( getActivity()).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                //getActivity().finish();
                //PedidoHelper.getActivityPedidoMain().finish();
            }
        });
        alertDialog.show();
    }

    public void showMsgSimNao( String mensagem, String telefone) {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();

            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                final Intent intent = new Intent(Intent.ACTION_DIAL);
                if (telefone.replaceAll("[^0-9]", "").length() == 10) {
                    intent.setData(Uri.parse("tel:" + "0" + telefone));
                } else if (telefone.replaceAll("[^0-9]", "").length() == 11) {
                    intent.setData(Uri.parse("tel:" + "0" + telefone));
                } else {
                    intent.setData(Uri.parse("tel:" + telefone));
                }
                startActivity(intent);
            }

        });
        alertDialog.show();
    }

}
