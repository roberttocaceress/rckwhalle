package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rcksuporte05.rcksistemas.R;

public class ActivityNotificacao extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_pedido); //  nao usarei a activyty de analise do pedido
        String title     = getIntent().getExtras().getString("title");
        String subTitle  = getIntent().getExtras().getString("subTitle");
        String body      = getIntent().getExtras().getString("body");
        finish();

    }
}
