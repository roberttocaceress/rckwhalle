package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.ClienteResumo;
import com.example.rcksuporte05.rcksistemas.model.CnpjConsulta;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Pais;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Mask;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.google.firebase.database.snapshot.Index;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCpfCnpjCliente extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtCpfCnpj)
    TextView txtCpfCnpj;

    @BindView(R.id.edtCpf)
    EditText edtCpf;

    @BindView(R.id.edtCnpj)
    EditText edtCnpj;

    @BindView(R.id.edtCnpjL)
    EditText edtCnpjL;



    @BindView(R.id.btnVerificar)
    Button btnVerificar;

    @BindView(R.id.chkCNPJ)
    CheckBox chkCNPJ;

    @BindView(R.id.chkCNPJL)
    CheckBox chkCNPJL;

    @BindView(R.id.chkCPF)
    CheckBox chkPF;

    private DBHelper db = new DBHelper(this);
    ;
    private ClienteDAO clienteDAO = new ClienteDAO(db);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private Utilitaria util = new Utilitaria();
    PackageInfo pInfo = null;
    Context context;

    private String getSelectedDocumento() {
        if (chkCNPJ.isChecked()) {
            return edtCnpj.getText().toString().trim();
        } else if (chkCNPJL.isChecked()) {
            return edtCnpjL.getText().toString().trim();
        } else {
            return edtCpf.getText().toString().trim();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpf_cnpj_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            return WindowInsetsCompat.CONSUMED;
        });
        context = this;
        ButterKnife.bind(this);
        //btnVerificar.setEnabled(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaCpfCnpj()) {
                    String documento = getSelectedDocumento();
                    Cliente cliente = verificaCpfCnpj(documento);
                    if (cliente != null) {
                        String cpfCnpj;
                        //switch (documento.replaceAll("[^0-9]", "").length()) {
                        switch (Mask.unmask(documento).length()) {
                            case 11:
                                cpfCnpj = "CPF";
                                break;
                            case 14:
                                cpfCnpj = "CNPJ";
                                break;
                            default:
                                cpfCnpj = "CNPJ/CPF";
                                break;
                        }
                        util.showMsgAlerta(cpfCnpj + " já cadastrado. \nCodigo: " + cliente.getId_cadastro() +
                                ".\nNome de Cadastro: " + cliente.getNome_cadastro() + "\n" +
                                "Nome Fantasia: " + cliente.getNome_fantasia(), ActivityCpfCnpjCliente.this);
                    } else {
                        //ClienteResumo clienteResumo = clienteDAO.getCliente("SELECT * FROM TBL_CADASTRO_RESUMO WHERE CPF_CNPJ = '" + documento.replaceAll("[^0-9]", "") + "'");
                        //if (clienteResumo != null) {
                        //    util.showMsgAlerta("CPF/CNPJ " + "já cadastrado!\nCliente: " + clienteResumo.getNome() +
                        //            "\nFavor verificar na empresa o cadastro, e após a correção/ajuste, efetuar uma nova sincronia de dados!", ActivityCpfCnpjCliente.this);
                        //} else {
                            if (verificaConexao()) {
                                //consultaCpfCnpjServidor(documento.replaceAll("[^0-9]", ""));
                                consultaCpfCnpjServidor(Mask.unmask(documento));
                            }else {
                                //if ( documento.replaceAll("[^0-9]", "").length() > 11) {
                                if ( Mask.unmask(documento).length() > 11) {
                                    //consultaCnpjSefaz(documento.replaceAll("[^0-9]", ""));
                                    consultaCnpjSefaz(Mask.unmask(documento));
                                } else {
                                    prosseguirCadastro(true);
                                }
                                //consultaCnpjSefaz(documento.replaceAll("[^0-9]", ""));
                                //prosseguirCadastro(false);
                            }
                        //}

                    }
                } else {
                    if (chkPF.isChecked()) {
                        edtCpf.setError("CPF Inválido");
                    } else {
                        edtCnpj.setError("CNPJ Inválido");
                    }
                }
            }
        });

        edtCnpj.addTextChangedListener(ActivityCpfCnpjCliente.MaskCnpj.insert(Mask.MaskType.CNPJ, edtCnpj,btnVerificar, context, chkCNPJ));
        edtCnpjL.addTextChangedListener(ActivityCpfCnpjCliente.MaskCnpj.insert(Mask.MaskType.CNPJ, edtCnpjL,btnVerificar, context, chkCNPJL));
        edtCpf.addTextChangedListener(ActivityCpfCnpjCliente.MaskCpf.insert(Mask.MaskType.CPF, edtCpf,btnVerificar, context, chkPF));


        chkCNPJ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chkPF.setChecked(false);
                    chkCNPJL.setChecked(false);

                    edtCpf.setEnabled(false);
                    edtCpf.setVisibility(View.INVISIBLE);
                    edtCpf.setText("");

                    edtCnpjL.setEnabled(false);
                    edtCnpjL.setVisibility(View.INVISIBLE);
                    edtCnpjL.setText("");

                    edtCnpj.setEnabled(true);
                    edtCnpj.setVisibility(View.VISIBLE);
                    edtCnpj.setText("");
                    edtCnpj.setHint("Informe o cnpj.");
                    edtCnpj.requestFocus();
                    imm.showSoftInput(edtCnpj, InputMethodManager.SHOW_IMPLICIT);

                    btnVerificar.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimaryTransparente20));
                    btnVerificar.setEnabled(false);
                } else {
                    if (!chkPF.isChecked() && !chkCNPJL.isChecked()) {
                        chkCNPJ.setChecked(true);
                    } else {
                        edtCnpj.setEnabled(false);
                        edtCnpj.setVisibility(View.INVISIBLE);
                        edtCnpj.setText("");
                    }
                }
            }
        });

        chkCNPJL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chkPF.setChecked(false);
                    chkCNPJ.setChecked(false);

                    edtCpf.setEnabled(false);
                    edtCpf.setVisibility(View.INVISIBLE);
                    edtCpf.setText("");

                    edtCnpj.setEnabled(false);
                    edtCnpj.setVisibility(View.INVISIBLE);
                    edtCnpj.setText("");

                    edtCnpjL.setEnabled(true);
                    edtCnpjL.setVisibility(View.VISIBLE);
                    edtCnpjL.setText("");
                    edtCnpjL.setHint("Informe o cnpj(N-L).");
                    edtCnpjL.requestFocus();
                    imm.showSoftInput(edtCnpjL, InputMethodManager.SHOW_IMPLICIT);

                    btnVerificar.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimaryTransparente20));
                    btnVerificar.setEnabled(false);
                } else {
                    if (!chkPF.isChecked() && !chkCNPJ.isChecked()) {
                        chkCNPJL.setChecked(true);
                    } else {
                        edtCnpjL.setEnabled(false);
                        edtCnpjL.setVisibility(View.INVISIBLE);
                        edtCnpjL.setText("");
                    }
                }
            }
        });

        chkPF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chkCNPJ.setChecked(false);
                    chkCNPJL.setChecked(false);

                    edtCnpj.setEnabled(false);
                    edtCnpj.setVisibility(View.INVISIBLE);
                    edtCnpj.setText("");

                    edtCnpjL.setEnabled(false);
                    edtCnpjL.setVisibility(View.INVISIBLE);
                    edtCnpjL.setText("");

                    edtCpf.setEnabled(true);
                    edtCpf.setVisibility(View.VISIBLE);
                    edtCpf.setText("");
                    edtCpf.requestFocus();
                    imm.showSoftInput(edtCpf, InputMethodManager.SHOW_IMPLICIT);

                    btnVerificar.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimaryTransparente20));
                    btnVerificar.setEnabled(false);
                } else {
                    if (!chkCNPJ.isChecked() && !chkCNPJL.isChecked()) {
                        chkPF.setChecked(true);
                    } else {
                        edtCpf.setEnabled(false);
                        edtCpf.setVisibility(View.INVISIBLE);
                        edtCpf.setText("");
                    }
                }
            }
        });

        btnVerificar.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimaryTransparente20));
        btnVerificar.setEnabled(false);

        //edtCpfCnpj.addTextChangedListener(ActivityCpfCnpjCliente.MaskCnpj.insert(Mask.MaskType.CNPJ, edtCpfCnpj,btnVerificar, context, chkCNPJ));
        /*edtCpfCnpj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (s.toString().replaceAll("[^0-9]", "").length()) {
                    case 11:
                    case 14:
                        // btnVerificar.setBackground(context.getResources().getColorStateList(R.color.colorButton));
                        //btnVerificar.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButton));
                        //btnVerificar.setEnabled(true);
                        break;
                    default:
                        //btnVerificar.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButtonTransparente50));
                        // btnVerificar.setEnabled(true);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //chkCNPJ.setChecked(true);
        edtCnpj.setHint("");
        edtCnpj.setEnabled(false);

    }

    private void prosseguirCadastro(boolean cadastroVerificado) {
        Intent intent = new Intent(ActivityCpfCnpjCliente.this, CadastroClienteMain.class);
        intent.putExtra("novo", 1);
        intent.putExtra("verificado", cadastroVerificado);
        startActivity(intent);
        String documento = getSelectedDocumento();
        //switch (documento.replaceAll("[^0-9]", "").length()) {
        switch (Mask.unmask(documento).length()) {
            case 11:
                ClienteHelper.getCliente().setPessoa_f_j("F");
                break;
            case 14:
                ClienteHelper.getCliente().setPessoa_f_j("J");
                break;
        }
        //ClienteHelper.getCliente().setCpf_cnpj(documento.replaceAll("[^0-9]", ""));
        ClienteHelper.getCliente().setCpf_cnpj(Mask.unmask(documento));
        finish();
    }

    private void prosseguirCadastroPreenchido(boolean cadastroVerificado, String razaoSocial, String nomeFantasia,
                                              String tipo_logradouro, String logradouro, String numero, String complemento, String bairro,
                                              String ddd1, String telefone1, String ddd2, String telefone2, String email, String cep,
                                              String codigo_pais, String nome_pais,
                                              String sigla_estado, String nome_estado,
                                              String codigo_cidade, String nome_cidade, String insc_estadual) {
        Intent intent = new Intent(ActivityCpfCnpjCliente.this, CadastroClienteMain.class);
        intent.putExtra("novo", 1);
        intent.putExtra("verificado", cadastroVerificado);
        intent.putExtra("razao_social", razaoSocial);
        intent.putExtra("nome_fantasia", nomeFantasia);
        intent.putExtra("tipo_logradouro", tipo_logradouro);
        intent.putExtra("logradouro", logradouro);
        intent.putExtra("numero", numero);
        intent.putExtra("complemento", complemento);
        intent.putExtra("bairro", bairro);
        intent.putExtra("ddd1", ddd1);
        intent.putExtra("telefone1", telefone1);
        intent.putExtra("ddd2", ddd2);
        intent.putExtra("telefone2", telefone2);
        intent.putExtra("email", email);

        intent.putExtra("cep", cep);
        intent.putExtra("codigo_pais", codigo_pais);
        intent.putExtra("nome_pias", nome_pais);

        intent.putExtra("sigla_estado", sigla_estado);
        intent.putExtra("nome_estado", nome_estado);

        intent.putExtra("codigo_cidade", codigo_cidade);
        intent.putExtra("nome_cidade", nome_cidade);
        intent.putExtra("insc_estadual", insc_estadual);


        startActivity(intent);
        String documento = getSelectedDocumento();
        //switch (documento.replaceAll("[^0-9]", "").length()) {
        switch (Mask.unmask(documento).length()) {
            case 11:
                ClienteHelper.getCliente().setPessoa_f_j("F");
                break;
            case 14:
                ClienteHelper.getCliente().setPessoa_f_j("J");
                break;
        }
        ClienteHelper.getCliente().setCpf_cnpj(Mask.unmask(documento)/*documento.replaceAll("[^0-9]", "")*/);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validaCpfCnpj() {
        String documento = getSelectedDocumento();
        //if (documento.replaceAll("[^0-9]", "").length() == 11) {
        if (Mask.unmask(documento).length() == 11) {
            return MascaraUtil.isValidCPF(Mask.unmask(documento)/*documento.replaceAll("[^0-9]", "")*/);
        }
        if (Mask.unmask(documento).length() == 14/*documento.toString().replaceAll("[^0-9]", "").length() == 14*/) {
            if (MascaraUtil.isAlphanumeric(Mask.unmask(documento)))
                return MascaraUtil.isValidCNPJAlfaNumerico(Mask.unmask(documento));
            else
                return MascaraUtil.isValidCNPJ(Mask.unmask(documento)) ;
        }
        return false;
    }

    public Cliente verificaCpfCnpj(String cpfCnpj) {
        try {
            if (clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE CPF_CNPJ = '" + Mask.unmask(cpfCnpj) + "' AND ID_CADASTRO <> " + ClienteHelper.getCliente().getId_cadastro() + " AND FINALIZADO = 'S';").size() > 0)
                return clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE CPF_CNPJ = '" + Mask.unmask(cpfCnpj) + "' AND ID_CADASTRO <> " + ClienteHelper.getCliente().getId_cadastro() + " AND FINALIZADO = 'S';").get(0);
        } catch (CursorIndexOutOfBoundsException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public void consultaCpfCnpjServidor(String cpfCnpj) {
        if (!usuarioIsLogado())
            return;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Falha ao definir a versão do aplicativo para acesso a conexão!!", Toast.LENGTH_LONG).show();
            return;
        }

        String versaoApp = pInfo.versionName.substring(0, 5);
        Toast toast = Toast.makeText(context, "Aguarde.... \nConsultando a base de dados no servidor.\nV-APP-API!!" + versaoApp, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            Call<Cliente> call = getRotasAPI(1, configuracao, versaoApp, cpfCnpj);
            if (call == null) {
                toast.cancel();
                return;
            }
            call.enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    switch (response.code()) {
                        case 210:
                            toast.cancel();
                            Cliente cliente = response.body();
                            if (cliente != null) {
                                String cpfCnpj = "";
                                if (cliente.getPessoa_f_j().equals("F"))
                                    cpfCnpj = "CPF";
                                else if (cliente.getPessoa_f_j().equals("J"))
                                    cpfCnpj = "CNPJ";
                                toast.cancel();
                                util.showMsgAlerta(cpfCnpj + " já cadastrado. \nCodigo: " + cliente.getId_cadastro() +
                                        ".\nNome de Cadastro: " + cliente.getNome_cadastro() + "\n" +
                                        "Nome Fantasia: " + cliente.getNome_fantasia(), ActivityCpfCnpjCliente.this);
                            } else {
                                if ( cpfCnpj.length() > 11) {
                                    consultaCnpjSefaz(cpfCnpj);
                                } else {
                                    prosseguirCadastro(true);
                                }
                                //consultaCnpjSefaz(cpfCnpj);
                            }
                            break;
                        case 220:
                            toast.cancel();
                            if ( cpfCnpj.length() > 11) {
                                consultaCnpjSefaz(cpfCnpj);
                            } else {
                                prosseguirCadastro(true);
                            }
                            //consultaCnpjSefaz(cpfCnpj);
                            //prosseguirCadastro(true);
                            //toast.cancel();
                            break;
                        case 500:
                            toast.cancel();
                            if ( cpfCnpj.length() > 11) {
                                consultaCnpjSefaz(cpfCnpj);
                            } else {
                                prosseguirCadastro(true);
                            }
                            //consultaCnpjSefaz(cpfCnpj);
                            Toast.makeText(ActivityCpfCnpjCliente.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            toast.cancel();
                            Toast.makeText(ActivityCpfCnpjCliente.this, "Erro não catalogado: " + response.code(), Toast.LENGTH_SHORT).show();
                            if ( cpfCnpj.length() > 11) {
                                consultaCnpjSefaz(cpfCnpj);
                            } else {
                                prosseguirCadastro(true);
                            }
                            //consultaCnpjSefaz(cpfCnpj);
                            //prosseguirCadastro(false);

                            //toast.cancel();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    toast.cancel();
                    try {
                        if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty()) {
                            consultaCpfCnpjServidor2(cpfCnpj);
                        } else {
                            if ( cpfCnpj.length() > 11) {
                                consultaCnpjSefaz(cpfCnpj);
                            } else {
                                prosseguirCadastro(true);
                            }
                            //consultaCnpjSefaz(cpfCnpj);
                            //prosseguirCadastro(false);
                        }
                    } catch (NullPointerException e) {
                        if ( cpfCnpj.length() > 11) {
                            consultaCnpjSefaz(cpfCnpj);
                        } else {
                            prosseguirCadastro(true);
                        }
                        //consultaCnpjSefaz(cpfCnpj);
                        //prosseguirCadastro(false);
                    }
                }
            });
        }
    }

    public void consultaCpfCnpjServidor2(String cpfCnpj) {
        if (!usuarioIsLogado())
            return;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Falha ao definir a versão do aplicativo para acesso a conexão!!", Toast.LENGTH_LONG).show();
            return;
        }

        String versaoApp = pInfo.versionName.substring(0, 5);
        Toast toast = Toast.makeText(context, "Aguarde.... \nreiniciando a consulta a base de dados no servidor.\nV-APP-API!!" + versaoApp, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            Call<Cliente> call = getRotasAPI(2, configuracao, versaoApp, cpfCnpj);
            if (call == null) {
                toast.cancel();
                return;
            }
            call.enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    switch (response.code()) {
                        case 210:
                            toast.cancel();
                            Cliente cliente = response.body();
                            if (cliente != null) {
                                String cpfCnpj = "";
                                if (cliente.getPessoa_f_j().equals("F"))
                                    cpfCnpj = "CPF";
                                else if (cliente.getPessoa_f_j().equals("J"))
                                    cpfCnpj = "CNPJ";
                                util.showMsgAlerta(cpfCnpj + " já cadastrado. \nCodigo: " + cliente.getId_cadastro() +
                                        ".\nNome de Cadastro: " + cliente.getNome_cadastro() + "\n" +
                                        "Nome Fantasia: " + cliente.getNome_fantasia(), ActivityCpfCnpjCliente.this);
                            } else {
                                if ( cpfCnpj.length() > 11) {
                                    consultaCnpjSefaz(cpfCnpj);
                                } else {
                                    prosseguirCadastro(true);
                                }
                            }
                            break;
                        case 220:
                            toast.cancel();
                            if ( cpfCnpj.length() > 11) {
                                consultaCnpjSefaz(cpfCnpj);
                            } else {
                                prosseguirCadastro(true);
                            }
                            break;
                        case 500:
                            toast.cancel();
                            if ( cpfCnpj.length() > 11) {
                                consultaCnpjSefaz(cpfCnpj);
                            } else {
                                prosseguirCadastro(true);
                            }
                            Toast.makeText(ActivityCpfCnpjCliente.this, "Erro : " + response.code(), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            toast.cancel();
                            Toast.makeText(ActivityCpfCnpjCliente.this, "Erro não catalogado: " + response.code(), Toast.LENGTH_SHORT).show();
                            if ( cpfCnpj.length() > 11) {
                                consultaCnpjSefaz(cpfCnpj);
                            } else {
                                toast.cancel();
                                prosseguirCadastro(true);
                            }
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    toast.cancel();
                    if ( cpfCnpj.length() > 11) {
                        consultaCnpjSefaz(cpfCnpj);
                    } else {
                        prosseguirCadastro(true);
                    }
                    //consultaCnpjSefaz(cpfCnpj);
                    //prosseguirCadastro(false);
                    t.printStackTrace();
                }
            });
        }
    }

    public void consultaCnpjSefaz(String cnpj) {
        Toast toast = Toast.makeText(context, "Aguarde.... \nConsultando cnpj " + MascaraUtil.mascaraCNPJ(cnpj) + " a sefaz.!!" , Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected()) {
            toast.cancel();
            Toast.makeText(context,"Você está offline...\nA consulta ao cnpj " + MascaraUtil.mascaraCNPJ(cnpj)  + " não sera efetuada!.", Toast.LENGTH_SHORT).show();
            prosseguirCadastro(false);
            return; // Interrompe o método
        }

            Rotas apiRotas3 = Api.buildRetrofitSefaz(true);
            Call<JsonObject> call;
            call = apiRotas3.consultarCnpjSefaz(cnpj.replace(".","").replace("/","").replace("-","").replace(" ", ""));
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if ( response.body() == null) {
                        toast.cancel();
                        prosseguirCadastro(true);
                    }  else {
                        Gson gson = new Gson();
                        Map<String, Object> mapa = gson.fromJson(response.body(), new TypeToken<Map<String, Object>>() {
                        }.getType());
                        Map<String , Object> estabelecimento = (Map<String, Object>) mapa.get("estabelecimento");
                        String razao_social = (String) mapa.get("razao_social");
                        String nome_fantasia = (String) estabelecimento.get("nome_fantasia");
                        String data_situacao_cadastral = (String) estabelecimento.get("data_situacao_cadastral");
                        String data_inicio_atividade = (String) estabelecimento.get("data_inicio_atividade");
                        String tipo_logradouro = (String) estabelecimento.get("tipo_logradouro");
                        String logradouro = (String) estabelecimento.get("logradouro");
                        String numero = (String) estabelecimento.get("numero");
                        String complemento = (String) estabelecimento.get("complemento");
                        String bairro = (String) estabelecimento.get("bairro");
                        String cep = (String) estabelecimento.get("cep");
                        String ddd1 = (String) estabelecimento.get("ddd1");
                        String telefone1 = (String) estabelecimento.get("telefone1");
                        String ddd2 = (String) estabelecimento.get("ddd2");
                        String telefone2 = (String) estabelecimento.get("telefone2");
                        String email = (String) estabelecimento.get("email");
                        Map<String, Object> pais      = (Map<String, Object>) estabelecimento.get("pais");
                        String codigo_pais   = (String) pais.get("id");
                        String nome_pais     = (String) pais.get("nome");
                        Map<String, Object> estado    = (Map<String, Object>) estabelecimento.get("estado");
                        String sigla_estado  = (String) estado.get("sigla");
                        String nome_estado   = (String) estado.get("nome");
                        Map<String, Object> cidade    = (Map<String, Object>) estabelecimento.get("cidade");
                        String codigo_cidade = String.valueOf(cidade.get("ibge_id"));
                        String nome_cidade   = (String) cidade.get("nome");
                        List<LinkedTreeMap> inscricoesEstaduais = (List<LinkedTreeMap>) estabelecimento.get("inscricoes_estaduais");
                        String insc_estadual = "";
                        if (inscricoesEstaduais != null)
                            for (LinkedTreeMap inscricao : inscricoesEstaduais)
                                if ((boolean) inscricao.get("ativo"))
                                    insc_estadual = (String) inscricao.get("inscricao_estadual");
                        toast.cancel();
                        if ( nome_cidade != null)
                            nome_cidade = nome_cidade.toUpperCase().trim();
                        if ( sigla_estado != null)
                            sigla_estado = sigla_estado.toUpperCase().trim();
                        prosseguirCadastroPreenchido(true, razao_social, nome_fantasia,
                                tipo_logradouro, logradouro,numero, complemento,bairro,
                                ddd1, telefone1, ddd2, telefone2, email,cep, codigo_pais, nome_pais,
                                sigla_estado, nome_estado, codigo_cidade, nome_cidade, insc_estadual
                        );
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    toast.cancel();
                    prosseguirCadastro(false);
                }
            });

    }
    private Call<Cliente> getRotasAPI(int idRota, Configuracao configuracao, String versaoApp, String cpfCnpj) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                if (versaoApp.substring(0, 3).equalsIgnoreCase("1.0"))
                    return apiRotas.analisaCpfCnpj(cpfCnpj, cabecalho);
                else
                    return apiRotas.verificaCpfCnpj(cpfCnpj, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + versaoApp + "/ws/";
                    Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();

                    Api.context = context;
                    apiRotas = Api.buildRetrofit(true);
                    return apiRotas.verificaCpfCnpj(cpfCnpj, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if (idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                return apiRotas2.verificaCpfCnpj(cpfCnpj, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();

                    Api.context = context;
                    apiRotas2 = Api.buildRetrofit2(true);
                    return apiRotas2.verificaCpfCnpj(cpfCnpj, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    public boolean verificaConexao() {
        //boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected())
            return true;
        return false;
    }

    private boolean usuarioIsLogado() {
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(context));
        try {
            if (UsuarioHelper.getUsuario() == null) {
                Toast.makeText(context, "Falha ao definir o usuário logado no aplicativo!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NullPointerException e) {
            Toast.makeText(context, "Falha ao definir o usuário logado no aplicativo!\n" + e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static class MaskCnpj {
        public enum MaskType {
            CNPJ("##.###.###/####-##"), NUMBER("##.##");
            String mask;

            MaskType(String s) {
                mask = s;
            }

            public String getMask() {
                return mask;
            }
        }

        public static String unmask(String s) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[ ]", "").replaceAll("[)]", "");
        }

        public static String unmaskNUmber(String s) {
            return s.replaceAll("[,]", ".").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[ ]", "").replaceAll("[)]", "");
        }

        public static String mask(Mask.MaskType type, String s) {
            String result = s;

            if (!s.contains(".") && !type.getMask().equalsIgnoreCase("##.##")) {
                String str = Mask.unmask(s.toString());
                result = "";

                int i = 0;
                for (char m : type.getMask().toCharArray()) {
                    if (m != '#') {
                        result += m;
                        continue;
                    }
                    try {
                        result += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
            } else {
                result = "";
                String str = Mask.unmaskNUmber(s.toString());
                int i = 0;
                for (char m : type.getMask().toCharArray()) {
                    if (m != '#') {
                        result += m;
                        continue;
                    }
                    try {
                        result += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
            }

            return result;
        }


        public static TextWatcher insert(final Mask.MaskType type, final EditText edtCnpj, final Button btn, final Context context, final CheckBox tipoPessoa) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {

                    if (isUpdating) {
                        isUpdating = false;
                        old = s.toString();
                        return;
                    }

                    if (!s.toString().isEmpty() && (s.toString().length() > old.length())) {
                        String str;
                        if (!type.getMask().equalsIgnoreCase("##.##")) {
                            str = Mask.unmask(s.toString());
                        } else {
                            str = Mask.unmask(s.toString());
                        }
                        String mask = "";

                        int i = 0;
                        for (char m : type.getMask().toCharArray()) {
                            if (m != '#') {
                                mask += m;
                                continue;
                            }
                            try {
                                mask += str.charAt(i);
                            } catch (Exception e) {
                                break;
                            }
                            i++;
                        }
                        isUpdating = true;
                        edtCnpj.setText(mask.toUpperCase());
                        try {
                            edtCnpj.setSelection(mask.length());
                        } catch ( IndexOutOfBoundsException e){
                            return;
                        }
                        //String x = s.toString().replaceAll("[^0-9]", "");
                        //if (s.toString().replaceAll("[^0-9]", "").length() == 14 && tipoPessoa.isChecked()) {
                        if (Mask.unmask(s.toString()).length() == 14 && tipoPessoa.isChecked()) {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            btn.setEnabled(true);
                        } else {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimaryTransparente20));
                            btn.setEnabled(false);
                        }

                    } else {
                        old = s.toString();
                        //if (s.toString().replaceAll("[^0-9]", "").length() == 14 && tipoPessoa.isChecked()) {
                        if (Mask.unmask(s.toString()).length() == 14 && tipoPessoa.isChecked()) {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            btn.setEnabled(true);
                        } else {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimaryTransparente20));
                            btn.setEnabled(false);
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            };
        }
    }

    private static class MaskCpf {
        public enum MaskType {
            CNPJ("###.###.###-##"), NUMBER("##.##");
            String mask;

            MaskType(String s) {
                mask = s;
            }

            public String getMask() {
                return mask;
            }
        }

        public static String unmask(String s) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[ ]", "").replaceAll("[)]", "");
        }

        public static String unmaskNUmber(String s) {
            return s.replaceAll("[,]", ".").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[ ]", "").replaceAll("[)]", "");
        }

        public static String mask(Mask.MaskType type, String s) {
            String result = s;

            if (!s.contains(".") && !type.getMask().equalsIgnoreCase("##.##")) {
                String str = Mask.unmask(s.toString());
                result = "";

                int i = 0;
                for (char m : type.getMask().toCharArray()) {
                    if (m != '#') {
                        result += m;
                        continue;
                    }
                    try {
                        result += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
            } else {
                result = "";
                String str = Mask.unmaskNUmber(s.toString());
                int i = 0;
                for (char m : type.getMask().toCharArray()) {
                    if (m != '#') {
                        result += m;
                        continue;
                    }
                    try {
                        result += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
            }

            return result;
        }


        public static TextWatcher insert(final Mask.MaskType type, final EditText edtCnpj, final Button btn, final Context context, final CheckBox tipoPessoa) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {

                    if (isUpdating) {
                        isUpdating = false;
                        old = s.toString();
                        return;
                    }

                    if (!s.toString().isEmpty() && (s.toString().length() > old.length())) {
                        String str;
                        if (!type.getMask().equalsIgnoreCase("##.##")) {
                            str = Mask.unmask(s.toString());
                        } else {
                            str = Mask.unmaskNUmber(s.toString());
                        }
                        String mask = "";

                        int i = 0;
                        for (char m : type.getMask().toCharArray()) {
                            if (m != '#') {
                                mask += m;
                                continue;
                            }
                            try {
                                mask += str.charAt(i);
                            } catch (Exception e) {
                                break;
                            }
                            i++;
                        }
                        isUpdating = true;
                        edtCnpj.setText(mask);
                        try {
                            edtCnpj.setSelection(mask.length());
                        } catch (IndexOutOfBoundsException e) {
                            return;
                        }
                        String x = s.toString().replaceAll("[^0-9]", "");
                        if (s.toString().replaceAll("[^0-9]", "").length() == 11 && tipoPessoa.isChecked()) {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            btn.setEnabled(true);
                        } else {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimaryTransparente20));
                            btn.setEnabled(false);
                        }

                    } else {
                        old = s.toString();
                        if (s.toString().replaceAll("[^0-9]", "").length() == 11 && tipoPessoa.isChecked()) {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            btn.setEnabled(true);
                        } else {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimaryTransparente20));
                            btn.setEnabled(false);
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            };
        }
    }
}
