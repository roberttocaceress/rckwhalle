package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.URLEncoder;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by RCK 03 on 26/10/2017.
 */

public class ActivityAjuda extends AppCompatActivity {
    @BindView(R.id.ajuda_toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_ajuda_configuracao)
    Button btn_ajuda_configuracao;
    @BindView(R.id.btn_ajuda_preferencia)
    Button btn_ajuda_preferencia;
    @BindView(R.id.btn_ajuda_lista_usuario)
    Button btn_ajuda_lista_usuario;

    @BindView(R.id.btn_ajuda_atualiza_url_api)
    Button btn_ajuda_atualiza_url_api;
    @BindView(R.id.btn_ajuda_suporte_whats)
    Button btn_ajuda_suporte_whats;
    @BindView(R.id.btn_ajuda_suporte_telefone)
    Button btn_ajuda_suporte_telefone;
    @BindView(R.id.btn_ajuda_suporte_email)
    Button btn_ajuda_suporte_email;
    @BindView(R.id.txv_ajuda_informacoes)
    TextView txv_ajuda_informacoes;
    @BindView(R.id.btn_ajuda_sobre)
    Button btn_ajuda_informacoes;
    @BindView(R.id.progress_ajuda)
    ProgressBar progress_ajuda;
    @BindView(R.id.progress_title)
    TextView progress_title;
    Context context ;
    DBHelper db = new DBHelper(this);
    PackageInfo pInfo = null;
    String versaoApp;
    FirebaseFirestore fdb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.ajuda_toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.progress_title);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        toolbar.setTitle("Ajuda");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versaoApp = pInfo.versionName.substring(0, 5);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        fdb = FirebaseFirestore.getInstance();
    }

    @OnClick(R.id.btn_ajuda_sobre)
    public void showActivitySobre() {
        startActivity(new Intent(ActivityAjuda.this, ActivitySobre.class));
    }

    @OnClick(R.id.btn_ajuda_configuracao)
    public void showActivityConfiguracao() {
        startActivity(new Intent(ActivityAjuda.this, ActivityConfiguracao.class));
    }

    @OnClick(R.id.btn_ajuda_preferencia)
    public void showActivityPreferencia() {
        startActivity(new Intent(ActivityAjuda.this, ActivityPreferencia.class));
    }

    @OnClick(R.id.btn_ajuda_lista_usuario)
    public void getListaUsuario() { //Utiliza a conexão padrao(1)
        showHideProgressBar(true);
        Rotas apiRotas = Api.buildRetrofit(true);
        Call<List<Usuario>> call = apiRotas.getListaUsuarios();
        execCall(call, false);
    }

    @OnClick(R.id.btn_ajuda_atualiza_url_api)

    public void updateURLAPI() { //Utiliza a conexão padrao(1)
            ViewGroup viewGroup = ((Activity) context).findViewById(R.id.rootx);
            View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.dialog_alert_permissao, viewGroup, true);
            TextInputEditText edtCodigoAcesso = dialogView.findViewById(R.id.edtPwd);
            new AlertDialog.Builder(ActivityAjuda.this)
                    .setTitle("RCK WHALLE")
                    .setMessage("Informe o código para a opção selecionada!")
                    .setView(dialogView)
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (edtCodigoAcesso.getText().toString().isEmpty()) {
                                Toast.makeText(context, "Código de acesso inválido!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (edtCodigoAcesso.getText().toString().trim().length() < 4) {
                                Toast.makeText(context, "Código de acesso inválido!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            getDadosFirebase(fdb, edtCodigoAcesso.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(context, "Ação cancelada!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
            edtCodigoAcesso.requestFocus();
    }

    public void getListaUsuario(Configuracao configuracao) {
        String url2 = configuracao.getUrl_ip_2().trim().toString() + ":" +
                Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp + "/ws/";
        if(!configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("S") ) {
            url2 = configuracao.getUrl_ip_2().trim().toString() + "/rckwhalleAPI" + versaoApp + "/ws/";
        }

        Api.context = context;
        Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                "http://" + url2 :
                "https://" + url2;
        Api.time_conn = configuracao.getTime_conn();
        Api.time_read = configuracao.getTime_read();
        Api.time_write = configuracao.getTime_write();
        Rotas apiRotas = Api.buildRetrofit2(true);
        Call<List<Usuario>> call = apiRotas.getListaUsuarios();
        execCall(call, true);
    }

    private void getDadosFirebase(FirebaseFirestore fdb,  String codigo) {
        try {
            fdb.collection("clientes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            boolean isValidCnpjCod = false;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if ( document.get("codigo").toString().equalsIgnoreCase(codigo)) {
                                        isValidCnpjCod = !isValidCnpjCod;
                                        updateConfiguracao(document);

                                        break;
                                    }
                                }
                                if (!isValidCnpjCod) {
                                    Toast.makeText(ActivityAjuda.this, " Código de segurança não localizado(s), favor confirmar os dados informados!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(ActivityAjuda.this, " Falha ao localizar os dados.\nFavor tentar novamente, caso o problema persista, entre em contato com o suporte técnico!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void updateConfiguracao(QueryDocumentSnapshot document) {
        Configuracao configuracao = new Configuracao();
        configuracao.setUrl_ip_1(document.get("ddns").toString().toLowerCase());
        configuracao.setPorta_1(Integer.parseInt(document.get("porta").toString()));
        configuracao.setUrl_ip_desc_1(document.get("descricao").toString().toUpperCase());
        configuracao.setConexao_segura(document.get("segura").toString());
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        if (configuracaoDAO.update(configuracao) > 0) {
            Toast.makeText(ActivityAjuda.this, " Dados atualizados com sucesso!!", Toast.LENGTH_SHORT).show();
            onBackPressed();
            //finish();
        } else {
            Toast.makeText(ActivityAjuda.this, " Falha ao atualizar os dados\nTente novamente, caso o problema persista, entre em contato  com o suporte técnico!", Toast.LENGTH_SHORT).show();
        }
    }


    private void execCall( Call<List<Usuario>> call, boolean isConexao2) {
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                switch (response.code()) {
                    case 200:
                        showHideProgressBar(false);
                        UsuarioBO usuarioBO = new UsuarioBO();
                        List<Usuario> lista = response.body();
                        if (!usuarioBO.add(lista, ActivityAjuda.this))
                            Toast.makeText(ActivityAjuda.this, "Houve um erro ao atualizar a lista de usúario(s)!" +
                                    "\n(" + String.valueOf(response.code()) + ")", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(ActivityAjuda.this, "Lista atualizada com sucesso!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                        break;
                    default:
                        showHideProgressBar(false);
                        Toast.makeText(ActivityAjuda.this, "Houve um erro ao atualizar a lista de usúario(s)!!" +
                                "\n(" + String.valueOf(response.code()) + ")", Toast.LENGTH_LONG).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                if (!isConexao2)
                    try {
                        Configuracao configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                        //getSslContextForCertificateFile(context, "keystore.jks");
                        if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty())
                            getListaUsuario(configuracao);
                        else {
                            showHideProgressBar(false);
                            Toast.makeText(ActivityAjuda.this, "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão!" +
                                    "\n" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } catch (NullPointerException e) {
                        showHideProgressBar(false);
                        Toast.makeText(ActivityAjuda.this, "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão!!" +
                                "\n" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                else {
                    showHideProgressBar(false);
                    Toast.makeText(ActivityAjuda.this, "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão!!!" +
                            "\n" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showHideProgressBar(boolean visivel) {
        if ( visivel)
            progress_ajuda.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(ActivityAjuda.this, R.color.branco), PorterDuff.Mode.SRC_IN );
        progress_ajuda.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick(R.id.btn_ajuda_suporte_whats) public void showWhatsApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setPackage("com.whatsapp").setData(Uri.parse("https://api.whatsapp.com/send?phone=5567993235558&text=" + URLEncoder.encode("Ola! Tudo bem? ", "UTF-8")));
            if (intent.resolveActivity(context.getPackageManager()) != null)
                context.startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_ajuda_suporte_telefone) public void showTelefoneApp() {
        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:06733833878")));
    }

    @OnClick(R.id.btn_ajuda_suporte_email) public void showEmailApp() {
        startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: rcksistemassuporte@rcksistemas.com.br")));
    }

    public static SSLContext getSslContextForCertificateFile(Context context, String fileName) {
        try {
            KeyStore keyStore = getKeyStore(context, fileName);
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            String msg = "Error during creating SslContext for certificate from assets";
            e.printStackTrace();
            throw new RuntimeException(msg);
        }
    }

           public static KeyStore getKeyStore(Context context, String fileName) {
            KeyStore keyStore = null;
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
                try (android.content.res.AssetManager.AssetInputStream caInput = (android.content.res.AssetManager.AssetInputStream) context.getAssets().open(fileName)) {
                    Certificate ca = cf.generateCertificate(caInput);
                    Log.d("SslUtilsAndroid", "ca=" + ((X509Certificate) ca).getSubjectDN());

                    String keyStoreType = KeyStore.getDefaultType();
                    keyStore = KeyStore.getInstance(keyStoreType);
                    keyStore.load(null, null);
                    keyStore.setCertificateEntry("ca", ca);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |NoSuchProviderException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return keyStore;
        }



}


