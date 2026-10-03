package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityContatoVendedor extends AppCompatActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.txtNomeVendedor)
    public TextView txtNomeVendedor;
    @BindView(R.id.txtTelefone)
    public TextView txtTelefone;
    @BindView(R.id.txtEmail)
    public TextView txtEmail;
    @BindView(R.id.txtEndereco)
    public TextView txtEndereco;
    @BindView(R.id.lyChamada)
    public RelativeLayout lyChamada;
    @BindView(R.id.lyEmail)
    public RelativeLayout lyEmail;
    @BindView(R.id.lyGps)
    public RelativeLayout lyGps;
    @BindView(R.id.edtDataCadastro)
    public TextView edtDataCadastro;
    @BindView(R.id.txtMunicipio)
    public TextView txtMunicipio;
    @BindView(R.id.txtUf)
    public TextView txtUf;
    @BindView(R.id.txtDescricaoTelefone)
    TextView txtDescricaoTelefone;
    private DBHelper db = new DBHelper(this);
    private Cliente vendedor;
    private ClienteDAO clienteDAO = new ClienteDAO(db);
    Utilitaria util = new Utilitaria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato_vendedor);
        ButterKnife.bind(this);
        try {

            vendedor = clienteDAO.getLista("SELECT * FROM TBL_CADASTRO WHERE F_ID_VENDEDOR = " + ClienteHelper.getCliente().getId_vendedor() + ";").get(0);
            txtNomeVendedor.setText(vendedor.getNome_cadastro());
            if (vendedor.getTelefone_principal() != null && !vendedor.getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty() && vendedor.getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && vendedor.getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11)
                txtTelefone.setText(formataTelefone(vendedor.getTelefone_principal()));
             else if (vendedor.getTelefone_dois() != null && !vendedor.getTelefone_dois().replaceAll("[^0-9]", "").trim().isEmpty() && vendedor.getTelefone_dois().replaceAll("[^0-9]", "").length() >= 8 && vendedor.getTelefone_dois().replaceAll("[^0-9]", "").length() <= 11)
                txtTelefone.setText(formataTelefone(vendedor.getTelefone_dois()));
             else if (vendedor.getTelefone_tres() != null && !vendedor.getTelefone_tres().replaceAll("[^0-9]", "").trim().isEmpty() && vendedor.getTelefone_tres().replaceAll("[^0-9]", "").length() >= 8 && vendedor.getTelefone_tres().replaceAll("[^0-9]", "").length() <= 11)
                txtTelefone.setText(formataTelefone(vendedor.getTelefone_tres()));
             else
                txtTelefone.setText("Nenhum telefone válido informado!");
                txtDescricaoTelefone.setVisibility(View.INVISIBLE);

            if (vendedor.getEmail_principal() != null && !vendedor.getEmail_principal().trim().equals(""))
                txtEmail.setText(vendedor.getEmail_principal());
            else if (vendedor.getEmail_financeiro() != null && !vendedor.getEmail_financeiro().trim().equals(""))
                txtEmail.setText(vendedor.getEmail_financeiro());
            else
                txtEmail.setText("Nenhum email informado!");

            String endereco;
            if (vendedor.getEndereco() != null && !vendedor.getEndereco().trim().equals(""))
                endereco = vendedor.getEndereco().replace(",", "") + ", " + vendedor.getEndereco_numero() + " - " + vendedor.getEndereco_cep();
            else if (vendedor.getCob_endereco() != null && !vendedor.getCob_endereco().trim().equals(""))
                endereco = vendedor.getCob_endereco().replace(",", "") + ", " + vendedor.getCob_endereco_numero() + " - " + vendedor.getCob_endereco_cep();
            else
                endereco = "Nenhum endereço informado!";

            txtEndereco.setText(endereco.replace(", null", ""));

            lyChamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fazerChamada(txtTelefone.getText().toString(), vendedor.getNome_cadastro());
                }
            });

            lyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtEmail.getText().toString().equals("Nenhum email informado!")) {
                        util.showMsgAlerta("Nenhum e-mail informado!", ActivityContatoVendedor.this);
                    } else {
                        showMsgSimNao(v.getId(), "Deseja enviar um email a " + vendedor.getNome_cadastro() + "?");

                    }
                }
            });

            lyGps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtEndereco.getText().toString().equals("Nenhum endereço informado!"))
                        util.showMsgAlerta("Nenhum endereço informado!", ActivityContatoVendedor.this);
                    else
                        showMsgSimNao( v.getId(), "Deseja navegar para a localização para " + vendedor.getNome_cadastro() + " no GPS?" );
                }
            });

            try {
                edtDataCadastro.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(vendedor.getUsuario_data())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                txtMunicipio.setText(vendedor.getNome_municipio());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                txtUf.setText(vendedor.getEndereco_uf());
            } catch (Exception e) {
                e.printStackTrace();
            }

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ActivityContatoVendedor.this, "Erro ao carregar Vendedor", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public String formataTelefone(String telefone) {
        telefone = telefone.trim().replaceAll("[^0-9]", "");
        switch (telefone.length()) {
            case 10:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
            case 11:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
            case 9:
                return telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
            case 8:
                return telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
            default:
                return telefone;
        }
    }

    public void fazerChamada(final String telefone, final String nome) {
        try {
            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty()) {
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11) {
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(ActivityContatoVendedor.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContatoVendedor.this);
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                    textView.setText("Deseja ligar para " + nome + " usando o número " + telefone + " ?");
                    Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
                    btnNao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                        }
                    });
                    Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
                    btnSim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (telefone.replaceAll("[^0-9]", "").length() == 10)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else if (telefone.replaceAll("[^0-9]", "").length() == 11)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else
                                intent.setData(Uri.parse("tel:" + telefone));
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                } else
                    util.showMsgAlerta("Este numero de telefone não é válido!", ActivityContatoVendedor.this);
            } else
                util.showMsgAlerta("Nenhum numero de Telefone informado!", ActivityContatoVendedor.this);

        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.gc();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMsgSimNao( final int id , String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityContatoVendedor.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContatoVendedor.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if ( id == R.id.lyEmail)
                    startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: " + txtEmail.getText().toString())));
                else if ( id == R.id.lyGps)
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + txtEndereco.getText().toString())).setPackage("com.google.android.apps.maps"));
            }
        });
        alertDialog.show();
    }
}
