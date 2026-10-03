package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UpdateAppWhalleActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Context context;
    private DBHelper db;
    private Configuracao configuracao;
    private String versaoApp, linkApp;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView progressTitle, versionTitle;
    private Button btn_down_install_app;
    private Button btn_open_folder_down;
    private boolean install, downSucess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_app_whalle);
        context = this;
        db = new DBHelper(this);
        configuracao = getConfiguracao();
        versaoApp = getIntent().getStringExtra("versaoApp");
        linkApp   = getIntent().getStringExtra("linkApp");
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        progressTitle = findViewById(R.id.progressTitle);
        versionTitle = findViewById(R.id. versionTitle);
        setSupportActionBar(toolbar);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        btn_down_install_app = findViewById(R.id.btn_down_install_app);
        btn_open_folder_down = findViewById(R.id.btn_open_folder_down);
        btn_down_install_app.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                showHideProgressBar(true);
                install = true;
                downloadInstallApk();
            }
        });

        btn_open_folder_down.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                showHideProgressBar(true);
                install = false;
                downloadInstallApk();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        showHideProgressBar(false);
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionTitle.setText("Versão atual instalada:\n" + pInfo.versionName.substring(0,7) + "\n\n" + pInfo.versionName.substring(7));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
    }
    private void showHideProgressBar(boolean visivel) {
        if( visivel) {
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            btn_down_install_app.setClickable(false);
            btn_open_folder_down.setClickable(false);
            //progressBar.setProgress(0);
        }  else {
            btn_down_install_app.setClickable(true);
            btn_open_folder_down.setClickable(true);

        }
        progressBar.setVisibility( visivel ? View.VISIBLE : View.GONE);
        progressTitle.setVisibility( visivel ? View.VISIBLE : View.GONE);

    }

    private Configuracao getConfiguracao() {
        return new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    private void downloadInstallApk() {
        String pathStorageDirectory = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            pathStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        } else {
            pathStorageDirectory =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        }
        //String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        //String pubStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        //String intStorageDirectory = Environment.getDataDirectory().getAbsolutePath();//;.;(Environment.DIRECTORY_DOWNLOADS).toString();
        //PackageManager packageManager = context.getPackageManager();
        File folder = new File(pathStorageDirectory);
        if ( !folder.exists())
            if (!folder.mkdir())
                showMsgDialog("Falha ao criar o diretorio para download do arquivo! Tente novamente, caso o problema persista, entre em contato com o suporte técnico!", false);
        String nameFile = "rckwhalle-" + versaoApp.trim() + ".apk";
        File file = new File(folder,  nameFile);
        try {
            if ( file.exists())
                file.delete();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if ( file.createNewFile()) {
                PackageInfo pInfo = null;
                try {
                    pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String path = "http://" + configuracao.getUrl_ip_1() + ":"
                        + configuracao.getPorta_1() + "/rckwhalleAPI"
                        + pInfo.versionName.substring(0, 5) + "/apk/rckwhalle-"
                        + versaoApp.trim() + ".apk";
                DownloadApkTask downloadApkTask = new DownloadApkTask("http://" + configuracao.getUrl_ip_1() + ":"
                        + configuracao.getPorta_1() + "/rckwhalleAPI"
                        + pInfo.versionName.substring(0, 5) + "/apk/rckwhalle-"
                        + versaoApp.trim() + ".apk", file, install);
                downloadApkTask.execute();
            } else {
                showHideProgressBar(false);
                showMsgDialog("Falha ao criar o arquivo! Tente novamente, caso o problema persista, entre em contato com o suporte técnico!", false);
            }
        } catch (IOException e1) {
            showHideProgressBar(false);
            showMsgDialog("Falha ao criar o arquivo! " + e1.toString() + "\nTente novamente, caso o problema persista, entre em contato com o suporte técnico!", false);
        }

    }

    public  class DownloadApkTask extends AsyncTask<String, Void, Void> {
        String fileURL;
        File directory;
        public DownloadApkTask(String fileURL,File directory, boolean install) {
            this.fileURL = fileURL;
            this.directory = directory;
        }

        @SuppressLint("SetWorldWritable")
        @Override
        protected Void doInBackground(String... params) {
            try {
                FileOutputStream f = new FileOutputStream(directory);
                URL u = new URL(fileURL);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.connect();
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
                directory.setWritable(true,false);
                downSucess = true;
            } catch (Exception e) {
                e.printStackTrace();
                downSucess = false;
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Void stringReturn) {
            super.onPostExecute(stringReturn);
            try {
                showHideProgressBar(false);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            File file ;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                file = new   File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() +"/rckwhalle-" + versaoApp.trim() + ".apk");
            } else {
                file = new   File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +"/rckwhalle-" + versaoApp.trim() + ".apk");
            }
            //File file = new   File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +"/rckwhalle-" + versaoApp.trim() + ".apk");
            context.getApplicationContext().getFilesDir().getAbsolutePath();
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            if  ( !downSucess) {
                if ( install ) {
                    showMsgDialog("Falha ao efetuar o download do aplicativo! Tente novamente, caso o problema persista, entre em contato com o suporte técnico!", false);
                    return;
                }
            }
            if (install) {
                if (file.exists()) {
                    int sdk_v = Build.VERSION.SDK_INT;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri uri = FileProvider.getUriForFile(context,
                                context.getApplicationContext().getPackageName() + ".provider", file, "RCK-WHALLE-APP");
                        Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(uri, "application/vnd.android.package-archive");
                        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        for (ResolveInfo resolveInfo : resInfoList) {
                            context.grantUriPermission(contextWrapper.getApplicationContext().getPackageName() + ".provider", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        try {
                            if (!getPackageManager().canRequestPackageInstalls()) {
                                startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse(String.format("package:%s", getPackageName()))));
                            }
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            showMsgDialog("A atualização do aplicativo não pode ser executada!", false);
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        try {
                            if (!getPackageManager().canRequestPackageInstalls()) {
                                startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse(String.format("package:%s", getPackageName()))));
                            }
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            showMsgDialog("A atualização do aplicativo não pode ser executada!", false);
                        }
                    }
                } else {
                    showMsgDialog("O arquivo não foi localizado!", false);
                }
            } else {
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
               //     startActivity(new Intent(Intent.ACTION_OPEN_DOCUMENT).setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION));
              //  else
                    startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS).setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION));
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
            }
        }
    }


    public  void showMsgDialog(String mensagem, boolean sucesso) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(UpdateAppWhalleActivity.this).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAppWhalleActivity.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}