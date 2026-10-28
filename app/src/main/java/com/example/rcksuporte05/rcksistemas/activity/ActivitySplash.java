package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.SQLException;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;

import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.LogSistemaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity_.ActivityLogin_;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;

import com.example.rcksuporte05.rcksistemas.model.LogSistema;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.AppCrashHandler;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import com.example.rcksuporte05.rcksistemas.util.Mask;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySplash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2050;
    private static int VALIDATE_TIME_OUT = 4050;
    Animation animZoomSplash;
    ImageView zoomSplash;
    ProgressBar progress_bar;
    TextView progress_title;
    private UsuarioBO usuarioBO = new UsuarioBO();
    private List<Usuario> usuarioList = new ArrayList<>();
    private DBHelper db = new DBHelper(ActivitySplash.this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private String url;
    PackageInfo pInfo = null;
    private Context context;
    FirebaseFirestore fdb;
    boolean isValidCnpjCod = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCrashHandler.init(this);
        super.onCreate(savedInstanceState);
        //if (android.os.Build.VERSION.SDK_INT >= 35) {
        //    androidx.activity.EdgeToEdge.enable(this);
        //}
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // Força a barra de status e navegação a terem a mesma cor da Toolbar
        getWindow().setStatusBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootx), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            View toolbar = findViewById(R.id.zoom_splash);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.progress_title);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        context = this;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        fdb = FirebaseFirestore.getInstance();

        try {
            db.onUpgrade(db.getWritableDatabase(), 27, 28);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        try {
            db.onUpgrade(db.getWritableDatabase(), 27, 27);
            db.onCreateIndex(db.getWritableDatabase());
            // NOVO: Criação do índice para otimização do JOIN de produtos e tabelas de preço
            db.getWritableDatabase().execSQL("CREATE INDEX IF NOT EXISTS IDX_TPI_PROD_TABELA ON TBL_TABELA_PRECO_ITENS(ID_PRODUTO, ID_TABELA)");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        animZoomSplash = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_splash);
        zoomSplash = (ImageView) findViewById(R.id.zoom_splash);
        zoomSplash.setVisibility(View.VISIBLE);
        zoomSplash.startAnimation(animZoomSplash);
        progress_bar = (ProgressBar) findViewById(R.id.progress_splash);
        progress_title = (TextView) findViewById(R.id.progress_title);

        // SINCRONIZA LOGS PENDENTES LOGO AO ABRIR O APP
        syncLogsToFirestoreGlobal();

        showSplash();
        //getDadosFirebase(fdb);
        validaDados();
    }
    private void showSplash() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                zoomSplash.setVisibility(View.INVISIBLE);
                showHideProgressBar(true);
            }
        }, SPLASH_TIME_OUT);
    }

    private void showHideProgressBar(boolean visivel) {
        if( visivel) {
            progress_title.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            progress_bar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.branco), PorterDuff.Mode.SRC_IN);
        }

        progress_bar.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
    }
    private void validaDados() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                if (Api.url.equalsIgnoreCase("")) {
                    final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                    if (!configuracao.getId().isEmpty())
                        try {
                            url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + pInfo.versionName.substring(0, 5) + "/ws/";
                            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                                    "http://" + url :
                                    "https://" + url;
                            Api.time_conn = configuracao.getTime_conn();
                            Api.time_read = configuracao.getTime_read();
                            Api.time_write = configuracao.getTime_write();
                            Api.context = context;
                            Rotas apiRotas = Api.buildRetrofit(true);
                            validaUsuario();
                        } catch (/*android.database.CursorIndexOutOfBoundsException*/ Exception e) {
                            if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN WHERE LOGADO = 'S'") != 0)
                                deleteLogin();
                            showMainActivity();
                        }
                    else {
                        showHideProgressBar(false);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        showCnpjPrompt("");
                        //startActivity(new Intent(ActivitySplash.this,ActivityCnpjCodigoAtivacao.class));
                        //finish();
                        //getDadosFirebase(fdb);
                    }
                        //startActivity(new Intent(ActivitySplash.this, ActivityConfiguracao.class));
                } else {
                    try {
                        validaUsuario();
                    } catch (/*android.database.CursorIndexOutOfBoundsException*/ Exception e) {
                        if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN WHERE LOGADO = 'S'") != 0)
                            deleteLogin();
                        showMainActivity();
                    }
                }
            }
        }, VALIDATE_TIME_OUT);
    }

    private void showCnpjPrompt(String cnpj) {
        final EditText txtCnpj = new EditText(ActivitySplash.this);
        txtCnpj.addTextChangedListener(Mask.insert(Mask.MaskType.CNPJ, txtCnpj));
        if ( cnpj.isEmpty())
             txtCnpj.setHint("INFORME O CNPJ");
        else
            txtCnpj.setText(cnpj.trim());

        txtCnpj.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtCnpj.setGravity(Gravity.CENTER_HORIZONTAL);

        if (Build.VERSION.SDK_INT < 23)
            txtCnpj.setTextAppearance(context, androidx.appcompat.R.style.TextAppearance_AppCompat_Medium);
        else
            txtCnpj.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Medium);
        txtCnpj.setFocusable(true);
        txtCnpj.requestFocus();

        new AlertDialog.Builder(ActivitySplash.this)
                .setTitle("Atenção!")
                .setMessage("Informe/confirme o CNPJ para cadastramento dos parametros de acesso!")
                .setView(txtCnpj)
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //showHideProgressBar(true);
                        if ( validaCnpj(txtCnpj.getText().toString().replaceAll("[^0-9]", "")))
                            showCodigoPrompt( txtCnpj.getText().toString().replaceAll("[^0-9]", "") );
                            //getDadosFirebase(fdb, txtCnpj.getText().toString().replaceAll("[^0-9]", ""));
                        else
                            showHideProgressBar(false);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //startActivity(new Intent(ActivitySplash.this, ActivityConfiguracao.class));
                        finishAffinity();
                        //finish();
                    }
                })
                .show();

    }

    private void showCodigoPrompt( String cnpj) {
        final EditText txt = new EditText(ActivitySplash.this);
        //txt.setInputType(InputType.TYPE_CLASS_NUMBER);
        txt.setGravity(Gravity.CENTER_HORIZONTAL);
        txt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        txt.setFocusable(true);
        txt.requestFocus();

        new AlertDialog.Builder(ActivitySplash.this)
                .setTitle("Atenção!")
                .setMessage("Informe o código de segurança para continuar!")
                .setView(txt)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        showHideProgressBar(true);
                        getDadosFirebase(fdb, cnpj, txt.getText().toString().trim());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //startActivity(new Intent(ActivitySplash.this, ActivityConfiguracao.class));
                        finishAffinity();
                        //finish();
                    }
                })
                .show();

    }

    public boolean validaCnpj(String cnpj) {
        if (cnpj.replaceAll("[^0-9]", "").length() == 14) {
            if (MascaraUtil.isValidCNPJ(cnpj.replaceAll("[^0-9]", ""))) {
                return true;
            } else if (cnpj.replaceAll("[^0-9]", "").equalsIgnoreCase("99999999999999")) {
                return true;
            }
        }
        Toast.makeText(ActivitySplash.this, "CNPJ informádo é invalido", Toast.LENGTH_SHORT).show();
        showCnpjPrompt(cnpj);
        return false;
    }

    private void getDadosFirebase(FirebaseFirestore fdb, String cnpj, String codigo) {
        try {
            fdb.collection("clientes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.get("cnpj").toString().equalsIgnoreCase(cnpj.trim()) && document.get("codigo").toString().equalsIgnoreCase(codigo)) {
                                        isValidCnpjCod = true;
                                        addConfiguracao(document);
                                        break;
                                    }

                                }
                                if (!isValidCnpjCod) {
                                    showHideProgressBar(false);
                                    showCnpjPrompt(cnpj);
                                    Toast.makeText(ActivitySplash.this, " CNPJ/Código de segurança não localizado(s), favor confirmar os dados informados!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                showHideProgressBar(false);
                                Toast.makeText(ActivitySplash.this, " Falha ao localizar os dados\nFavor cadastrar os dados manualmente", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(ActivitySplash.this, ActivityConfiguracao.class));
                            }
                        }
                    });
        }catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void addConfiguracao(QueryDocumentSnapshot document) {
        //String cnpj = document.get("cnpj").toString();
        //String ddns = document.get("ddns").toString();
        //String porta = document.get("porta").toString();
        //String segura = document.get("segura").toString();
        Configuracao configuracao = new Configuracao();
        configuracao.setUrl_ip_1(document.get("ddns").toString().toLowerCase());
        configuracao.setPorta_1(Integer.parseInt(document.get("porta").toString()));
        configuracao.setUrl_ip_desc_1(document.get("descricao").toString().toUpperCase());
        configuracao.setConexao_segura(document.get("segura").toString());
        configuracao.setPesquisa_produto_por_linha_grupo("G");
        configuracao.setTrabalha_com_campanhas("N");
        configuracao.setTrabalha_com_dscto_reais("N");
        configuracao.setTrabalha_com_limite_cred("N");
        configuracao.setTrabalha_com_pre_pedido("N");
        configuracao.setTrabalha_com_confirmacao_prazo("N");
        configuracao.setTrabalha_com_unidade_manut_estoque("N");
        configuracao.setTrabalha_com_cobranca("N");
        configuracao.setTrabalha_com_icms_st("N");
        configuracao.setTrabalha_com_paginacao_dados("N");
        configuracao.setMarca_produto_ja_comprado("S");
        try {
            configuracao.setTrabalha_com_cobranca(document.get("utilizacobranca").toString());
        } catch ( NullPointerException e) {
            configuracao.setTrabalha_com_cobranca("N");
        } catch ( Exception e) {
            configuracao.setTrabalha_com_cobranca("N");
        }

        try {
            configuracao.setSincronia_automatica_app(document.get("sincroniaautomatica").toString());
        } catch ( NullPointerException e) {
            configuracao.setSincronia_automatica_app("N");
        } catch ( Exception e) {
            configuracao.setSincronia_automatica_app("N");
        }
        showHideProgressBar(false);
        if (configuracaoDAO.add(configuracao)) {
            startActivity(new Intent(ActivitySplash.this, ActivityLogin_.class));
            finish();
        } else {
            Toast.makeText(ActivitySplash.this, " Falha ao atualizar os dados\nFavor cadastrar os dados manualmente", Toast.LENGTH_SHORT).show();
        }
    }

    private void validaUsuario() {
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(ActivitySplash.this));
        try {
            if (!UsuarioHelper.getUsuario().getId_usuario().isEmpty()) {
                UsuarioBO usuarioBO = new UsuarioBO();
                UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
            }
        } catch (NullPointerException e) {
            UsuarioBO usuarioBO = new UsuarioBO();
            UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(db));
        } catch (Exception e) {
        }
        if (!UsuarioHelper.getUsuario().getToken().isEmpty() && !UsuarioHelper.getUsuario().getToken().equals(null)) {
            //getListaUsuarios();
            showActivityPrincipal();
        } else {
            showMainActivity();
        }
    }

    private void deleteLogin() {
        db.deleteDados("TBL_LOGIN", "1", null);
        Toast.makeText(getApplicationContext(), "Usuario alterado", Toast.LENGTH_LONG).show();
    }

    private void showMainActivity() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //startActivity(new Intent(ActivitySplash.this, MainActivity.class));
        startActivity(new Intent(ActivitySplash.this, ActivityLogin_.class));
        finish();
    }

    private void showActivityPrincipal() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(new Intent(ActivitySplash.this, ActivityPrincipal.class));
        finish();
    }

    private void syncLogsToFirestoreGlobal() {
        new Thread(() -> {
            try {
                LogSistemaDAO logDAO = new LogSistemaDAO(db);
                List<LogSistema> logs = logDAO.getAllUnsentLogs();

                if (logs.isEmpty()) return;

                Configuracao config = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                String cnpj = config.getCnpj_empresa() != null ? config.getCnpj_empresa().trim() : "";
                if (cnpj.isEmpty()) return;

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                for (LogSistema log : logs) {
                    // FILTRO: Na Splash, enviamos APENAS os erros de CRASH
                    if (!"CRASH".equalsIgnoreCase(log.getTipoLog())) continue;

                    Map<String, Object> logMap = new HashMap<>();
                    for (Field field : log.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        try {
                            logMap.put(field.getName(), field.get(log));
                        } catch (Exception ignored) {}
                    }

                    String idVendedor = log.getIdVendedor() != null ? log.getIdVendedor() : "SEM_VENDEDOR";

                    // LOG TÉCNICO (CRASH): Centralizado para fácil localização do suporte
                    firestore.collection(cnpj)
                            .document("TBL_LOG_ERROS_TECNICOS")
                            .collection(idVendedor)
                            .document(log.getIdLog() + "_" + (log.getDataHora() != null ? log.getDataHora().replaceAll("[^0-9]", "") : ""))
                            .set(logMap)
                            .addOnSuccessListener(aVoid -> logDAO.markAsSent(log.getIdLog()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}

