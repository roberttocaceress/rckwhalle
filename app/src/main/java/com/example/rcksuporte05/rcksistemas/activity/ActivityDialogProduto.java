package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDialogProduto extends Activity {
    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    @BindView(R.id.checkVisualizaLegenda)
    CheckBox checkVisualizaLegenda;


    private Configuracao configuracao;

    private DBHelper db = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_produto);
        ButterKnife.bind(this);
        /*btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        ButterKnife.bind(this);
        getConfiguracao();
        try {
            checkVisualizaLegenda.setChecked(configuracao.getVisualiza_legenda_produto().equalsIgnoreCase("N"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( checkVisualizaLegenda.isChecked()  ) {
                    db.update("UPDATE TBL_CONFIGURACAO SET VISUALIZA_LEGENDA_PRODUTO = 'N'");
                } else{
                    db.update("UPDATE TBL_CONFIGURACAO SET VISUALIZA_LEGENDA_PRODUTO = 'S'");
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