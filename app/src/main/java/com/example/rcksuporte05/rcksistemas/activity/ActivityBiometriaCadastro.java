package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.LoginDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBiometriaCadastro extends AppCompatActivity {
    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;
    @BindView(R.id.edtLogin)
    EditText edtLogin;
    @BindView(R.id.edtSenha)
    EditText edtSenha;
    @BindView(R.id.cadastrar_biometria_toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_main)
    ProgressBar progress_main;
    @BindView(R.id.progress_main_title)
    TextView progress_main_title;
    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    PackageInfo pInfo = null;
    private String url;
    Utilitaria util = new Utilitaria();
    private UsuarioBO usuarioBO = new UsuarioBO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometria_cadastro);
        ButterKnife.bind(this);
        toolbar.setTitle("Cadastrar biometria");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        validaUsuario();
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                if ( !configuracao.getId().isEmpty()  ) {
                    if (edtLogin.getText().toString().isEmpty()) {
                        Toast.makeText( ActivityBiometriaCadastro.this, "Necessário informar o usuario/email antes de prosseguir!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ( edtSenha.getText().toString().isEmpty()) {
                        Toast.makeText( ActivityBiometriaCadastro.this, "Necessário informar a senha antes de prosseguir!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    sincronizaUsuarioAPI(configuracao);
                }else
                    Toast.makeText( ActivityBiometriaCadastro.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!",Toast.LENGTH_LONG).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void validaUsuario() {
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLoginBiometria(db));
        try {
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty())
                UsuarioHelper.setUsuario( new UsuarioBO().getUsuarioLoginBiometria(db));
        } catch (NullPointerException e) {
            UsuarioHelper.setUsuario( new UsuarioBO().getUsuarioLoginBiometria(db));
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (UsuarioHelper.getUsuario().getUsuario_permite_biometria().isEmpty() || UsuarioHelper.getUsuario().getUsuario_permite_biometria().equalsIgnoreCase("N"))
                toolbar.setTitle("Cadastrar biometria");
            else
                toolbar.setTitle("Remover biometria");
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        try {
            if (!UsuarioHelper.getUsuario().getToken().isEmpty() && !UsuarioHelper.getUsuario().getToken().equals(null))
                setEdtLoginSenha(UsuarioHelper.getUsuario().getLogin(),UsuarioHelper.getUsuario().getSenha());
            else
                setEdtLoginSenha("","");
        }catch ( NullPointerException e) {
            setEdtLoginSenha("","");
        }

    }
    private void setEdtLoginSenha(String login, String senha){
        edtLogin.setText(login);
        edtSenha.setText(senha);
    }
    private void sincronizaUsuarioAPI(Configuracao configuracao) { //Método para atualizar na base de dados no servidor os parametros p a utilização de Alerts no app do usuario
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName.substring(0,5);
        showHideProgressBar(true);
        url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + pInfo.versionName.substring(0, 5) + "/ws/";
        Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                "http://" + url :
                "https://" + url;
        Api.time_conn = configuracao.getTime_conn();
        Api.time_read = configuracao.getTime_read();
        Api.time_write = configuracao.getTime_write();

        Rotas apiRotas = Api.buildRetrofit(true);
        Call<Usuario> call;
        Usuario usuario1 = setUsuario(UsuarioHelper.getUsuario());
        if (isValidEmail(edtLogin.getText().toString().toLowerCase()))
            call = apiRotas.updateBiometria( "-", edtSenha.getText().toString(), edtLogin.getText().toString() );
        else
            call = apiRotas.updateBiometria( edtLogin.getText().toString(), edtSenha.getText().toString(), "-" );
        callExecCall1(call);

    }

    private void execCall(Call<Usuario> call) {
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario1 = response.body();
                switch (response.code()) {
                    case 200:
                        showHideProgressBar(false);
                        usuario1.setLogado("S");
                        if (usuario1.getIdEmpresaMultiDevice() != null && Integer.parseInt(usuario1.getIdEmpresaMultiDevice()) > 0) {
                            LoginDAO loginDAO = new LoginDAO(db);
                            if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") > 0)
                                loginDAO.update(usuario1);
                            else
                                loginDAO.add(usuario1);
                            db.close();
                            System.gc();
                            UsuarioHelper.setUsuario(usuario1);
                            startActivity(new Intent(ActivityBiometriaCadastro.this, ActivityPrincipal.class).putExtra("alterado", 0));
                            finish();
                        } else {
                            showHideProgressBar(false);
                            util.showMsgAlerta("O usuario em questão não tem o codigo da empresa informado em seu cadastro, é necessário a correção no cadastro deste usuário!\n    " +
                                    "Em caso de duvida, favor entrar em contato com a RCK sistemas!", ActivityBiometriaCadastro.this);
                           setEdtLoginSenha("", "");
                        }
                        break;
                    case 500:
                        showHideProgressBar(false);
                        edtSenha.setError("Senha incorreta");
                        edtSenha.setText("");
                        edtSenha.requestFocus();
                        break;
                    default:
                        util.showMsgAlerta("Não foi possivel sincronizar com o servidor, por favor verifique sua conexão!"
                                + "\n("+ String.valueOf(response.code()) + ")", ActivityBiometriaCadastro.this);
                        setEdtLoginSenha("", "");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                showHideProgressBar(false);
                util.showMsgAlerta("Não foi possivel sincronizar com o servidor, por favor verifique sua conexão!"
                        + "\n"+ t.getMessage(), ActivityBiometriaCadastro.this );
                edtSenha.setText("");
                edtSenha.requestFocus();
            }
        });
    }

    private void callExecCall1(Call<Usuario> call) {
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                switch (response.code()) {
                    case 200:
                        loginNaApi(response.body(), 0, "US");
                        getUsuarios(false);
                        break;
                    case 500:
                        showHideProgressBar(false);
                        util.showMsgAlerta("Usuário não localizado!\n("+ String.valueOf(response.code()) + ")", ActivityBiometriaCadastro.this);
                        break;
                    default:
                        showHideProgressBar(false);
                        util.showMsgAlerta("Falha ao atualizar os dados! Tente novamente!\n("+ String.valueOf(response.code()) + ")", ActivityBiometriaCadastro.this);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                showHideProgressBar(false);
                util.showMsgAlerta("Não foi possivel sincronizar com o servidor, por favor verifique sua conexão!!" +
                        "\n" + t.getMessage(), ActivityBiometriaCadastro.this);
            }
        });
    }

    public void showHideProgressBar(boolean isVisivel) {
        if (isVisivel)
            progress_main.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_IN);

        progress_main.setVisibility(isVisivel ? View.VISIBLE : View.INVISIBLE);
        progress_main_title.setVisibility(isVisivel ? View.VISIBLE : View.INVISIBLE);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private Usuario setUsuario(Usuario usuario) {
        Usuario usuario1 = new Usuario();
        try {
            usuario1.setId_usuario(usuario.getId_usuario());
            usuario1.setAparelho_key_firebase(usuario.getAparelho_key_firebase());
            usuario1.setAparelho_versao_app(usuario.getAparelho_versao_app());
            usuario1.setAparelho_notifica_update_app(usuario.getAparelho_notifica_update_app());
            usuario1.setAparelho_notifica_status_ped(usuario.getAparelho_notifica_status_ped());
            usuario1.setAparelho_notifica_vencto_fin(usuario.getAparelho_notifica_vencto_fin());
            usuario.setAparelho_versao_app(pInfo.versionName.substring(0, 7));
            try {
                if (usuario.getUsuario_permite_biometria().isEmpty() || usuario.getUsuario_permite_biometria().equalsIgnoreCase("N"))
                    usuario.setUsuario_permite_biometria("S");
                else
                    usuario.setUsuario_permite_biometria("N");
            }catch ( NullPointerException e) {
                usuario.setUsuario_permite_biometria("S");
            }
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        return  usuario1;
        //Método para atualizar na base de dados no servidor os parametros p a utilização de Alerts no app do usuario
    }

    public void loginNaApi(final Usuario usuario, final int alterado, final String tipoLogin) {
        String version = pInfo.versionName.substring(0,5);
        db = new DBHelper(ActivityBiometriaCadastro.this);
        configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if ( !configuracao.getId().isEmpty()  ) {
            url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + pInfo.versionName.substring(0, 5) + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.time_conn = configuracao.getTime_conn();
            Api.time_read = configuracao.getTime_read();
            Api.time_write = configuracao.getTime_write();

            Rotas apiRotas = Api.buildRetrofit(true);
            String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            Call<Usuario> call;
            if ( tipoLogin.equalsIgnoreCase("US")) {
                if (isValidEmail(edtLogin.getText().toString().toLowerCase())) {
                    call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario().trim(), "-", edtSenha.getText().toString(), edtLogin.getText().toString().toLowerCase());
                } else {
                    call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario(), edtLogin.getText().toString().toUpperCase(), edtSenha.getText().toString(), "-");
                }
            } else {
                call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario(), "-", "-", "-");
            }
            execCall(call);
        } else {
            Toast.makeText( ActivityBiometriaCadastro.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!",Toast.LENGTH_LONG).show();
        }
    }

     public void getUsuarios(final Boolean mostrar) {
        String version = pInfo.versionName.substring(0,5);
        configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if ( !configuracao.getId().isEmpty()  ) {
            url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + pInfo.versionName.substring(0, 5) + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.time_conn = configuracao.getTime_conn();
            Api.time_read = configuracao.getTime_read();
            Api.time_write = configuracao.getTime_write();

            Rotas apiRotas = Api.buildRetrofit(true);
            Call<List<Usuario>> call = apiRotas.getListaUsuarios();
            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    List<Usuario> usuarioList = response.body();
                    if (!usuarioBO.add(usuarioList, ActivityBiometriaCadastro.this)) {
                    }
                }
                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                }
            });
        }
    }


}