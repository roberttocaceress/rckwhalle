package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.R;

import java.net.URLEncoder;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class ActivitySobre extends AppCompatActivity {

    @BindView(R.id.lyGps)
    LinearLayout lyGps;
    @BindView(R.id.lyMail)
    LinearLayout lyMail;
    @BindView(R.id.lyChamada)
    LinearLayout lyChamada;
    @BindView(R.id.lyWhats)
    LinearLayout lyWhats;
    @BindView(R.id.versaoAPP)
    TextView versaoAPP;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //
    private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dialog);
        ButterKnife.bind(this);
        context = this;
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            //View rodape = findViewById(R.id.linearLayout4);
            //rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        Drawable upArrow = ContextCompat.getDrawable(this, androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versaoAPP.setText("Versão do APP/API: " + version + "\nVersão ANDROID compativel com o app >= ANDROID 5.1 Lolipop");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.lyGps)
    public void gps() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q= Rua Senador Ponce, 257 Campo Grande - MS")).setPackage("com.google.android.apps.maps"));
    }

    @OnClick(R.id.lyMail)
    public void email() {
        startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: rcksistemassuporte@rcksistemas.com.br")));
    }

    @OnClick(R.id.lyChamada)
    public void chamada() {
        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:06733833878")));
    }

    @OnClick(R.id.lyWhats)
    public void whats() {
        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=5567993235558&text=" + URLEncoder.encode("Ola! Tudo bem? ", "UTF-8");
            i.setPackage("com.whatsapp").setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

