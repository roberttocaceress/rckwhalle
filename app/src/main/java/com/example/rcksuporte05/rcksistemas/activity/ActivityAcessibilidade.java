package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityAcessibilidade extends AppCompatActivity implements View.OnClickListener {

    private DBHelper db = new DBHelper(this);

    private int tExtraFonte = 0;
    Context context ;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;

    @BindView(R.id.lytFontePadrao)
    LinearLayout lytFontePadrao;

    @BindView(R.id.lytFonteMedio)
    LinearLayout lytFonteMedio;

    @BindView(R.id.lytFonteGrande)
    LinearLayout lytFonteGrande;

    @BindView(R.id.imgTamanhoPadrao)
    ImageView imgTamanhoPadrao;

    @BindView(R.id.imgTamanhoMedio)
    ImageView imgTamanhoMedio;

    @BindView(R.id.imgTamanhoGrande)
    ImageView imgTamanhoGrande;

    @BindView(R.id.txvTamanhoPadrao)
    TextView txvTamanhoPadrao;

    @BindView(R.id.txvTamanhoMedio)
    TextView txvTamanhoMedio;

    @BindView(R.id.txvTamanhoGrande)
    TextView txvTamanhoGrande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acessibilidade);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.btnCancelar);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
        } catch (NullPointerException e) {
        }
        btnConfirmar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        lytFontePadrao.setOnClickListener(this);
        imgTamanhoPadrao.setOnClickListener(this);
        lytFonteMedio.setOnClickListener(this);
        imgTamanhoMedio.setOnClickListener(this);
        lytFonteGrande.setOnClickListener(this);
        imgTamanhoGrande.setOnClickListener(this);
        btnConfirmar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );
        btnCancelar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );
        if ( db.pegaTamanhoExtraFonte() == 0) {
            tExtraFonte = 0;
            txvTamanhoPadrao.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            imgTamanhoPadrao.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
        }
        if ( db.pegaTamanhoExtraFonte() == 2) {
            tExtraFonte = 2;
            txvTamanhoMedio.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            imgTamanhoMedio.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
        }
        if ( db.pegaTamanhoExtraFonte() == 4) {
            tExtraFonte = 4;
            txvTamanhoGrande.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            imgTamanhoGrande.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));

        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnConfirmar) {
            db.update("UPDATE TBL_ACESSIBILIDADE SET TAMANHO_EXTRA_FONTE = " + tExtraFonte);
            if ( db.pegaTamanhoExtraFonte() == tExtraFonte) {
                Toast.makeText(getApplicationContext(), "Tamanho da fonte alterada com sucesso!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), "Falha ao alterar o tamanho da fonte!!", Toast.LENGTH_LONG).show();
            }
        }

        if ( view == btnCancelar)
            onBackPressed();

        if ( view == lytFontePadrao || view == imgTamanhoPadrao) {
            tExtraFonte = 0;
            txvTamanhoPadrao.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            imgTamanhoPadrao.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            txvTamanhoMedio.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            imgTamanhoMedio.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            txvTamanhoGrande.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            imgTamanhoGrande.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            btnConfirmar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );
            btnCancelar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );
            //db.update("UPDATE TBL_ACESSIBILIDADE SET TAMANHO_EXTRA_FONTE = 0");
        }
        if ( view == lytFonteMedio || view == imgTamanhoMedio) {
            tExtraFonte = 2;
            txvTamanhoMedio.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            imgTamanhoMedio.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            txvTamanhoPadrao.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            imgTamanhoPadrao.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            txvTamanhoGrande.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            imgTamanhoGrande.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            btnConfirmar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );
            btnCancelar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );

            //db.update("UPDATE TBL_ACESSIBILIDADE SET TAMANHO_EXTRA_FONTE = 2");
        }
        if ( view == lytFonteGrande || view == imgTamanhoGrande) {
            tExtraFonte = 4;
            txvTamanhoGrande.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            imgTamanhoGrande.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            txvTamanhoMedio.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            imgTamanhoMedio.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            txvTamanhoPadrao.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            imgTamanhoPadrao.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.branco)));
            btnConfirmar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );
            btnCancelar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte );
            //db.update("UPDATE TBL_ACESSIBILIDADE SET TAMANHO_EXTRA_FONTE = 4");
        }
    }
}