package com.example.rcksuporte05.rcksistemas.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityInfoApp extends AppCompatActivity {
    PackageInfo pInfo = null;
    String versaoApp = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_app);
        ButterKnife.bind(this);
     //   EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_sobre_app);
        toolbar.setTitle("Voltar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView txv_versao_app = findViewById(R.id.txv_versao__app) ;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versaoApp = pInfo.versionName.substring(0,7);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {

            String  sdkMin  = "21";
            txv_versao_app.setText( "Versão do APP/API: $version\nLinguagem utilizada(Java/Kotlin)" +
                    "\nVersão ANDROID compativel com o app >= ANDROID 5.1 Lolipop(SDK 21)");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}