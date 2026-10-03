package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDialogCliente extends Activity {
    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    @BindView(R.id.checkVisualizaLegenda)
    CheckBox checkVisualizaLegenda;


    private Configuracao configuracao;

    private DBHelper db = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dialog_cliente), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);

            //return  windowInsets;
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        getConfiguracao();
        try {
            checkVisualizaLegenda.setChecked(configuracao.getVisualiza_legenda_cliente().equalsIgnoreCase("N"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( checkVisualizaLegenda.isChecked()  ) {
                    db.update("UPDATE TBL_CONFIGURACAO SET VISUALIZA_LEGENDA_CLIENTE = 'N'");
                } else{
                    db.update("UPDATE TBL_CONFIGURACAO SET VISUALIZA_LEGENDA_CLIENTE = 'S'");
                }
                finish();
            }
        });
    }
    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
}