package com.example.rcksuporte05.rcksistemas.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.LoginDAO;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.Biometria;
import com.example.rcksuporte05.rcksistemas.util.CryptoManager;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.text.pdf.qrcode.ByteArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txvBemVindo;

    private EditText edtLogin;
    private TextInputEditText edtSenha;
    private Button btnEntrar, btnBiometria;
    private Button btnAjuda;
    private Button btnRecepurarSenha1, btnRecepurarSenha2, btnInformacoesApp;
   // private ProgressDialog progressBar;
    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioBO usuarioBO = new UsuarioBO();
    private DBHelper db = new DBHelper(MainActivity.this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private Biometria biometria = new Biometria();
    Animation animZoomIn, animZoomOut;
    Boolean execAnimation = false;
    private ProgressBar progressBar;
    TextView progressTitle;
    PackageInfo pInfo = null;
    private Context context;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private CryptoManager cryptoManager;

    private Boolean readyToEncrypt = false;
    private String secretKeyName;
    private byte[] ciperText;
    private ByteArray initializationVector;
    private String url;
    Utilitaria util = new Utilitaria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        /*
        int orientation = this.getResources().getConfiguration().orientation;
        int size = this.getResources().getConfiguration().screenLayout;
        if (size == Configuration.SCREENLAYOUT_SIZE_LARGE)
            Toast.makeText(context, "SCREENLAYOUT_SIZE_LARGE", Toast.LENGTH_LONG).show();
        if (size == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            Toast.makeText(context, "SCREENLAYOUT_SIZE_XLARGE", Toast.LENGTH_LONG).show();
        if (size == Configuration.SCREENLAYOUT_SIZE_MASK)
            Toast.makeText(context, "SCREENLAYOUT_SIZE_MASK", Toast.LENGTH_LONG).show();
        if (size == Configuration.SCREENLAYOUT_SIZE_NORMAL)
            Toast.makeText(context, "SCREENLAYOUT_SIZE_NORMAL", Toast.LENGTH_LONG).show();
        if (size == Configuration.SCREENLAYOUT_SIZE_SMALL)
            Toast.makeText(context, "SCREENLAYOUT_SIZE_SMALL", Toast.LENGTH_LONG).show();
        if (size == Configuration.SCREENLAYOUT_SIZE_UNDEFINED)
            Toast.makeText(context, "SCREENLAYOUT_SIZE_UNDEFINED", Toast.LENGTH_LONG).show();
        */
        context = this;
        btnRecepurarSenha1 = findViewById(R.id.btnRecuperarSenha1);
        btnRecepurarSenha2 = findViewById(R.id.btnRecuperarSenha2);
        btnInformacoesApp = findViewById(R.id.btnInformacoesApp);
        btnRecepurarSenha2.setText(R.string.underlined_text);
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        progressBar = findViewById(R.id.progressBar);
        progressTitle = findViewById(R.id.progressTitle);
        txvBemVindo = findViewById(R.id.txvBemVindo);
        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        //btnEntrar.setVisibility(View.INVISIBLE);

        btnBiometria = findViewById(R.id.btnBiometria);
        btnAjuda = findViewById(R.id.btnAjuda);

        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in_default);
        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out_default);

        btnEntrar.setOnClickListener(this);
        btnBiometria.setOnClickListener(this);
        btnAjuda.setOnClickListener(this);
        btnRecepurarSenha1.setOnClickListener(this);
        btnRecepurarSenha2.setOnClickListener(this);
        btnInformacoesApp.setOnClickListener(this);

        edtLogin.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() >= 2 && edtSenha.getText().toString().length() >= 2) {
                    if (!execAnimation) {
                        btnEntrar.setVisibility(View.VISIBLE);
                        btnEntrar.setEnabled(true);
                        btnEntrar.startAnimation(animZoomIn);
                        execAnimation = true;
                    }
                } else {
                    if (execAnimation) {
                        btnEntrar.startAnimation(animZoomOut);
                        execAnimation = false;
                        btnEntrar.setVisibility(View.INVISIBLE);
                        btnEntrar.setEnabled(false);
                    }
                }
            }
        });

        edtSenha.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() >= 2 && edtLogin.getText().toString().length() >= 2) {
                    if (!execAnimation) {
                        btnEntrar.setVisibility(View.VISIBLE);
                        btnEntrar.setEnabled(true);
                        btnEntrar.startAnimation(animZoomIn);
                        execAnimation = true;
                    }
                } else {
                    if (execAnimation) {
                        btnEntrar.startAnimation(animZoomOut);
                        execAnimation = false;
                        btnEntrar.setVisibility(View.INVISIBLE);
                        btnEntrar.setEnabled(false);
                    }
                }
            }
        });

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode != 13)
                    Toast.makeText(MainActivity.this, "Erro de autenticação " + errorCode + " - " + errString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                logarNoApp(0, "AB");  //ID DO APARELHO E BIOMETRIA = S
                //processData(result.getCryptoObject());
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                showMsgSimNao("Falha de autenticação!\n Digital não localizada/cadastrada no dispositivo. Deseja cadastrar uma nova digital no dispositivo?");

            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login biométrico")
                .setSubtitle("Faça login usando sua credencial biométrica")
                .setNegativeButtonText("Voltar")
                .build();
        cryptoManager = new CryptoManager();


        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&  biometria.isBiometricPromptEnabled() )
            showHideBiomatria(true);
        else
            showHideBiomatria(false);


    }
    //secretKeyName = getString(R.string.secret_key_name)

    /*
    private void processData(BiometricPrompt.CryptoObject cryptoObject) {
        //cryptoManager.decryptData(ciperText, cryptoObject.getCipher() );

    }
    */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view == btnEntrar) {
            if (isCamposValidos())
                logarNoApp(0, "US"); //tipoLogin -> Usuario e senha
        } else if (view == btnBiometria) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                showBiometria();
            else
                Toast.makeText(MainActivity.this, "Opção não ativa para esta versao do Android", Toast.LENGTH_SHORT).show();
        } else if (view == btnAjuda)
            startActivity(new Intent(MainActivity.this, ActivityAjuda.class));
        else if (view == btnRecepurarSenha1 || view == btnRecepurarSenha2)
            startActivity(new Intent(MainActivity.this, ActivityRecuperaSenha.class));
        else if (view == btnInformacoesApp)
            startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
    }



    private boolean isCamposValidos() {
        if (edtLogin.getText().toString().equalsIgnoreCase("")) {
            edtLogin.setError("Valor inválido!Por favor insira seu usuário");
            edtLogin.setText("");
            edtLogin.requestFocus();

        } else if (edtSenha.getText().toString().equalsIgnoreCase("")) {
            edtSenha.setError("Valor inválido! Por favor informe sua senha");
            edtSenha.setText("");
            edtSenha.requestFocus();
        } else
            return true;
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void logarNoApp(final int alterado, final String tipoLogin) {
        try {
            getListaUsuarios();
            showHideProgressBar(true);
            if (alterado != 1) {
                try {
                    String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    UsuarioDAO usuarioDAO = new UsuarioDAO(db);
                    if (tipoLogin.equalsIgnoreCase("US")) { //USUARIO E SENHA
                        List<Usuario> listaUsuario = usuarioDAO.getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ( LOGIN = '" + edtLogin.getText().toString().toUpperCase().trim() + "' OR EMAIL_VENDEDOR = '" + edtLogin.getText().toString().toLowerCase().trim() + "')");
                        if (listaUsuario.size() > 0)
                            logarNaApi(listaUsuario.get(0), alterado, "US");
                        else {
                            showLoginInvalido();
                        }
                    } else if (tipoLogin.equalsIgnoreCase("AB")) {  //ID DO APARELHO E BIOMETRIA AUTORIZADA
                        List<Usuario> listaUsuario = usuarioDAO.getLista("SELECT * FROM TBL_WEB_USUARIO WHERE APARELHO_ID = '" + idAndroit + "'");
                        if (listaUsuario.size() > 0) {
                            Usuario usuario;
                            boolean biometriaAutorizada = false;
                            for (int i = 0; i < listaUsuario.size(); i++) {
                                usuario = listaUsuario.get(i);
                                try {
                                    if (usuario.getUsuario_permite_biometria().equalsIgnoreCase("S")) {
                                        biometriaAutorizada = true;
                                        logarNaApi(usuario, alterado, "AB");
                                    }
                                } catch (NullPointerException e) {
                                    showHideProgressBar(false);
                                    startActivity(new Intent(MainActivity.this, ActivityBiometriaCadastro.class));
                                } catch (Exception e) {
                                    showHideProgressBar(false);
                                    startActivity(new Intent(MainActivity.this, ActivityBiometriaCadastro.class));
                                }
                            }
                            showHideProgressBar(false);
                            if (!biometriaAutorizada)
                                startActivity(new Intent(MainActivity.this, ActivityBiometriaCadastro.class));
                        } else {
                            showLoginInvalido();
                            edtLogin.setError("!");
                            Toast.makeText(MainActivity.this, "Usuário não autorizado/localizado", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (NullPointerException e) {
                    showLoginInvalido();
                } catch (Exception e) {
                    showLoginInvalido();
                }
            }

        } catch (IndexOutOfBoundsException e) {
            //shimmerLayout.hideShimmer();
            showLoginInvalido();
        }
    }

    private void showLoginInvalido() {
        showHideProgressBar(false);
        edtLogin.setError("Usuario/senha inexistente!");
        edtLogin.setText("");
        edtLogin.requestFocus();
        edtSenha.setText("");
    }

    private void showActivitySecuritySettings() {
        startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showBiometria() {
        if (biometria.isBiometricPromptEnabled())
            if (biometria.isSdkVersionSupported())
                if (biometria.isFingerprintAvailable(MainActivity.this))
                    if (biometria.isPermissionGranted(MainActivity.this))
                        biometricPrompt.authenticate(promptInfo);
    }


    public void getListaUsuarios() {
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }
        util.getListaUsuariosAPICrypto(context, db, pInfo.versionName.substring(0, 5), false);


    }

    public void logarNaApi(final Usuario usuario, final int alterado, final String tipoLogin) {
        String version = pInfo.versionName.substring(0, 5);
        db = new DBHelper(MainActivity.this);
        configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + version + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.context = context;
            Api.time_conn = configuracao.getTime_conn();
            Api.time_read = configuracao.getTime_read();
            Api.time_write = configuracao.getTime_write();
            Rotas apiRotas = Api.buildRetrofit(true);
            String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            Call<Usuario> call;
            if (tipoLogin.equalsIgnoreCase("US")) { // logar usando usuario e senha
                if (isEmailValido(edtLogin.getText().toString().toLowerCase()))
                    call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario().trim(), "-", edtSenha.getText().toString(), edtLogin.getText().toString().toLowerCase());
                else
                    call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario(), edtLogin.getText().toString().toUpperCase(), edtSenha.getText().toString(), "-");
            } else  //Logar usaundo a biometria
                call = apiRotas.logarAPI(idAndroit, usuario.getId_usuario(), "-", "-", "-");

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    Usuario usuario1 = response.body();
                    switch (response.code()) {
                        case 200:
                            usuario1.setLogado("S");
                            usuario1.setUsuario_lembrete_sincronia("N");
                            usuario1.setUsuario_lembrete_sincronia_data("");
                            if (usuario1.getIdEmpresaMultiDevice() != null && Integer.parseInt(usuario1.getIdEmpresaMultiDevice()) > 0) {
                                LoginDAO loginDAO = new LoginDAO(db);
                                if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") > 0)
                                    loginDAO.update(usuario1);
                                else
                                    loginDAO.add(usuario1);
                                db.close();
                                System.gc();
                                UsuarioHelper.setUsuario(usuario1);
                                startActivity(new Intent(MainActivity.this, ActivityPrincipal.class).putExtra("alterado", alterado));
                                finish();
                            } else {
                                showHideProgressBar(false);
                                util.showMsgAlerta("O usuario em questão não tem o codigo da empresa informado em seu cadastro, é necessário a correção no cadastro deste usuário!\n    " +
                                        "Em caso de duvida, favor entrar em contato com a RCK sistemas!", MainActivity.this);
                                edtLogin.setText("");
                                edtSenha.setText("");
                            }
                            break;
                        case 500:
                            showHideProgressBar(false);
                            edtSenha.setError("Senha incorreta");
                            edtSenha.setText("");
                            edtSenha.requestFocus();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                   logarNaApi2(usuario, alterado, tipoLogin);
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }
    }

    public void logarNaApi2(final Usuario usuario, final int alterado, final String tipoLogin) {
        String version = pInfo.versionName.substring(0, 5);
        db = new DBHelper(MainActivity.this);
        configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        try {
            if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty()) {
                url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + version + "/ws/";
                Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                        "http://" + url :
                        "https://" + url;
                Api.time_conn = configuracao.getTime_conn();
                Api.time_read = configuracao.getTime_read();
                Api.time_write = configuracao.getTime_write();
                Rotas apiRotas2 = Api.buildRetrofit2(true);
                String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                Call<Usuario> call;
                if (tipoLogin.equalsIgnoreCase("US")) { // logar usando usuario e senha
                    if (isEmailValido(edtLogin.getText().toString().toLowerCase()))
                        call = apiRotas2.logarAPI(idAndroit, usuario.getId_usuario().trim(), "-", edtSenha.getText().toString(), edtLogin.getText().toString().toLowerCase());
                    else
                        call = apiRotas2.logarAPI(idAndroit, usuario.getId_usuario(), edtLogin.getText().toString().toUpperCase(), edtSenha.getText().toString(), "-");
                } else  //Logar usaundo a biometria
                    call = apiRotas2.logarAPI(idAndroit, usuario.getId_usuario(), "-", "-", "-");

                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        Usuario usuario1 = response.body();
                        switch (response.code()) {
                            case 200:
                                usuario1.setLogado("S");
                                usuario1.setUsuario_lembrete_sincronia("N");
                                usuario1.setUsuario_lembrete_sincronia_data("");
                                if (usuario1.getIdEmpresaMultiDevice() != null && Integer.parseInt(usuario1.getIdEmpresaMultiDevice()) > 0) {
                                    LoginDAO loginDAO = new LoginDAO(db);
                                    if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") > 0)
                                        loginDAO.update(usuario1);
                                    else
                                        loginDAO.add(usuario1);
                                    db.close();
                                    System.gc();
                                    UsuarioHelper.setUsuario(usuario1);
                                    startActivity(new Intent(MainActivity.this, ActivityPrincipal.class).putExtra("alterado", alterado));
                                    finish();
                                } else {
                                    showHideProgressBar(false);
                                    util.showMsgAlerta("O usuario em questão não tem o codigo da empresa informado em seu cadastro, é necessário a correção no cadastro deste usuário!\n " +
                                            "Em caso de duvida, favor entrar em contato com a RCK sistemas!", MainActivity.this);
                                    edtLogin.setText("");
                                    edtSenha.setText("");
                                }
                                break;
                            case 500:
                                showHideProgressBar(false);
                                edtSenha.setError("Senha incorreta");
                                edtSenha.setText("");
                                edtSenha.requestFocus();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        showHideProgressBar(false);
                        util.showMsgAlerta("Você precisa estar conectado a internet/rede local para poder logar!\n(" + t.getMessage() + ")" , MainActivity.this);
                        edtSenha.setText("");
                        edtSenha.requestFocus();
                    }
                });
            } else {
                showHideProgressBar(false);
                Toast.makeText(MainActivity.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!", Toast.LENGTH_LONG).show();
            }
        } catch ( NullPointerException e) {
            showHideProgressBar(false);
            Toast.makeText(MainActivity.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!\n(" + e.getMessage() + ")", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        getListaUsuarios();
        super.onResume();
    }

    private void showHideBiomatria(boolean isVisivel) {
        if (!isVisivel) {
            ViewGroup.LayoutParams params = btnBiometria.getLayoutParams();
            params.height = 0;
            btnBiometria.setVisibility(View.INVISIBLE);
            btnBiometria.setLayoutParams(params);
        }

    }

    private void showHideProgressBar(boolean isVisivel) {
        if (isVisivel)
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.branco), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(isVisivel ? View.VISIBLE : View.INVISIBLE);
        progressTitle.setVisibility(isVisivel ? View.VISIBLE : View.INVISIBLE);
    }

    public boolean isEmailValido(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void showMsgSimNao(  String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
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
                startActivityForResult(new Intent(Settings.ACTION_SECURITY_SETTINGS), 0);
            }
        });
        alertDialog.show();
    }
}



