package com.example.rcksuporte05.rcksistemas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRecuperaSenha extends AppCompatActivity {
    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    @BindView(R.id.edtEmailSenha)
    EditText edtEmailSenha;

    @BindView(R.id.recupera_senha_toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress_main)
    ProgressBar progress_main;

    @BindView(R.id.progress_main_title)
    TextView progress_main_title;

    private DBHelper   db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    PackageInfo pInfo = null;
    private String url;
    Utilitaria util = new Utilitaria();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_senha);
        ButterKnife.bind(this);
        toolbar.setTitle("Recuperação de senha");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEmailSenha.getText().toString().isEmpty()) {
                    if (isValidEmail(edtEmailSenha.getText().toString()))
                        recuperarSenha(edtEmailSenha.getText().toString());
                    else
                        Toast.makeText(ActivityRecuperaSenha.this, "e-mail inválido!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ActivityRecuperaSenha.this, "Informe seu e-mail antes de continuar!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void recuperarSenha(final String emailVendedor) {
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName.substring(0,5);
        progress_main.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_IN );
        progress_main.setVisibility(View.VISIBLE);
        progress_main_title.setVisibility(View.VISIBLE);

        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

        if ( ! configuracao.getId().isEmpty()  ) {
            url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + pInfo.versionName.substring(0, 5) + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.time_conn = configuracao.getTime_conn();
            Api.time_read = configuracao.getTime_read();
            Api.time_write = configuracao.getTime_write();
            Rotas apiRotas = Api.buildRetrofit(true);
            String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            Call<Usuario> call = apiRotas.recuperarSenha(idAndroit, emailVendedor);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    Usuario usuario = response.body();
                    progress_main.setVisibility(View.INVISIBLE);
                    progress_main_title.setVisibility(View.INVISIBLE);
                    switch (response.code()) {
                        case 200:
                            if ( usuario.getEmail_vendedor().equalsIgnoreCase(emailVendedor))
                                util.showMsgAlerta("Recuperação de senha.\n" +
                                        "Caso os dados informados sejam\n válidos, você recebera um e-mail\n contendo a sua senha.\nVerifique sua caixa de mensagem!", ActivityRecuperaSenha.this);
                            else
                                util.showMsgAlerta("e-mail informado não localizado!\nVerifique o e-mail e tente novamente", ActivityRecuperaSenha.this);
                            break;
                        case 400:
                            util.showMsgAlerta("e-mail informado não localizado!\nVerifique o e-mail e tente novamente", ActivityRecuperaSenha.this);
                            break;
                        case 500:
                            util.showMsgAlerta("Erro não catalogado!", ActivityRecuperaSenha.this);
                            break;
                    }
                }
                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    progress_main.setVisibility(View.INVISIBLE);
                    progress_main_title.setVisibility(View.INVISIBLE);
                    util.showMsgAlerta("Você precisa estar conectado a internet/rede local para poder solicitar a recuperação da senha!", ActivityRecuperaSenha.this);

                }
            });
        } else {
            Toast.makeText( ActivityRecuperaSenha.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda, na tela de login e informe os parametros de conexão!",Toast.LENGTH_LONG).show();
        }
    }
  }
