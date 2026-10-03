package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCobrancaContato extends AppCompatActivity {

    @BindView(R.id.txtCategoria)
    TextView txtCategoria;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imFisicaJuridica)
    ImageView imFisicaJuridica;
    @BindView(R.id.txtRazaoSocial)
    TextView txtRazaoSocial;
    @BindView(R.id.txtNomeFantasia)
    TextView txtNomeFantasia;
    @BindView(R.id.txtTelefone)
    TextView txtTelefone;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtEndereco)
    TextView txtEndereco;

    @BindView(R.id.lyChamada)
    RelativeLayout lyChamada;
    @BindView(R.id.lyEmail)
    RelativeLayout lyEmail;
    @BindView(R.id.lyGps)
    RelativeLayout lyGps;
    @BindView(R.id.lyNomeCliente)
    RelativeLayout lyNomeCliente;

    @BindView(R.id.edtDataCadastro)
    TextView edtDataCadastro;

    Utilitaria util = new Utilitaria();
    private DBHelper db = new DBHelper(this);
    private FinanceiroResumo financeiroResumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranca_contato);
        ButterKnife.bind(this);
        DBHelper db = new DBHelper(this);
        showDados();

        lyChamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerChamada(txtTelefone.getText().toString(), financeiroResumo.getNome_favorecido());
            }
        });

        lyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEmail.getText().toString().equals("Nenhum email informado!"))
                    util.showMsgAlerta("Nenhum e-mail informado!", ActivityCobrancaContato.this);
                else
                    showMsgSimNao(v.getId(), "Deseja enviar um email a ?");
            }
        });

        lyGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEndereco.getText().toString().equals("Nenhum endereço informado!"))
                    util.showMsgAlerta("Nenhum endereço informado!", ActivityCobrancaContato.this);
                else
                    showMsgSimNao(v.getId(), "Deseja navegar para a localização para " + financeiroResumo.getNome_favorecido() + " no GPS?");
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void showDados() {
        financeiroResumo = new FinanceiroResumo();
        FinanceiroDAO financeiroDAO = new FinanceiroDAO(db);
        try {
            financeiroResumo = financeiroDAO.getFinanceiro("SELECT * FROM TBL_FINANCEIRO_RESUMO WHERE ID_FAVORECIDO = '" + getIntent().getStringExtra("id_favorecido")  + "'");
            if ( !financeiroResumo.getId_favorecido().isEmpty()) {


                toolbar.setTitle("Contato");
                txtRazaoSocial.setText(financeiroResumo.getNome_favorecido().toUpperCase());
                try {
                    txtNomeFantasia.setText(financeiroResumo.getNome_fantasia().toUpperCase());
                } catch ( NullPointerException e) {
                    txtNomeFantasia.setText("");
                }


                imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_fisica);

                if (financeiroResumo.getTelefone_principal() != null && !financeiroResumo.getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty() && financeiroResumo.getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && financeiroResumo.getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11)
                    txtTelefone.setText(formataTelefone(financeiroResumo.getTelefone_principal()));
                else
                    txtTelefone.setText("Nenhum telefone válido informado!");

                txtEmail.setText("Nenhum email informado!");

                String endereco;
                if (financeiroResumo.getEndereco() != null && !financeiroResumo.getEndereco().trim().equals("")) {
                    endereco = financeiroResumo.getEndereco().replace(",", "") + ", " + financeiroResumo.getEndereco_numero() + " - " + financeiroResumo.getEndereco_bairro();
                    try {
                        if (!financeiroResumo.getEndereco_cep().isEmpty()) {
                            endereco += " - CEP" + financeiroResumo.getEndereco_cep();
                        }
                    } catch ( NullPointerException e) {

                    }
                    try {
                        if ( !financeiroResumo.getNome_municipio().isEmpty()) {
                            endereco += " - " + financeiroResumo.getNome_municipio().toUpperCase() + " " + financeiroResumo.getEndereco_uf().toUpperCase();
                        }
                    } catch ( NullPointerException e) {

                    }
                }else
                    endereco = "Nenhum endereço informado!";
                txtEndereco.setText(endereco.replace(", null", ""));
                edtDataCadastro.setText("");

            }

        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            Toast.makeText(ActivityCobrancaContato.this, "Erro ao carregar os dados do cliente!", Toast.LENGTH_LONG).show();
            finish();
        } catch ( Exception e) {
            Toast.makeText(ActivityCobrancaContato.this, "Erro ao carregar os dados do cliente!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public String formataTelefone(String telefone) {
        telefone = telefone.trim().replaceAll("[^0-9]", "");
        switch (telefone.length()) {
            case 10:
                return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
            case 11:
                return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
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
            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty())
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11) {
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(ActivityCobrancaContato.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCobrancaContato.this);
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
                            final Intent intent = new Intent(Intent.ACTION_DIAL);
                            if (telefone.replaceAll("[^0-9]", "").length() == 10)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else if (telefone.replaceAll("[^0-9]", "").length() == 11)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else
                                intent.setData(Uri.parse("tel:" + telefone));
                            alertDialog.dismiss();
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                } else
                    util.showMsgAlerta("Este numero de Telefone não é valido!", ActivityCobrancaContato.this);
            else
                util.showMsgAlerta("Nenhum numero de telefone informado!", ActivityCobrancaContato.this);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }



    @Override
    protected void onDestroy() {
        ClienteHelper.clear();
        super.onDestroy();
    }

    public void showMsgSimNao( final int id , String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityCobrancaContato.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCobrancaContato.this);
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
