package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityExportaImagem extends AppCompatActivity {
    ProgressBar progress_bar;
    TextView progress_title;
    Button btnExportar;
    Button btnVoltar;
    Utilitaria util = new Utilitaria();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exporta_imagem);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar );
        progress_title = (TextView) findViewById(R.id.progress_title );
        btnExportar = (Button) findViewById(R.id.btnExportar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(ActivityExportaImagem.this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );
                progress_bar.setVisibility(View.VISIBLE);
                progress_title.setVisibility(View.VISIBLE);
                exportarFotos();

            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void exportarFotos(){
        DBHelper db = new DBHelper(ActivityExportaImagem.this);
        ProdutoDAO produtoDAO = new ProdutoDAO(db);
        String SQL ;
        SQL = "SELECT PF.*, PR.NOME_PRODUTO AS NOME_PRODUTO FROM TBL_PRODUTO_FOTO AS PF " +
                "INNER JOIN TBL_PRODUTO AS PR ON PR.ID_PRODUTO = PF.ID_PRODUTO " +
                "ORDER BY PF.ID_PRODUTO";
        List<ProdutoFoto> listaProdutoFoto = produtoDAO.getListaAnexos(SQL);

        final Rotas apiRotas = Api.buildRetrofit(false);
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<List<ProdutoFoto>> call = apiRotas.salvarProdutoFoto(listaProdutoFoto, cabecalho);
        call.enqueue(new Callback<List<ProdutoFoto>>() {
            @Override
            public void onResponse(Call<List<ProdutoFoto>> call, Response<List<ProdutoFoto>> response) {
                List<ProdutoFoto> listaProdutoFoto = response.body();
                progress_bar.setVisibility(View.INVISIBLE);
                progress_title.setVisibility(View.INVISIBLE);
                if (response.code() == 200)
                     util.showMsgAlerta("Arquivos exportados com sucesso!", ActivityExportaImagem.this);
                else
                    util.showMsgAlerta("Não foi possivel expotar os arquivos",ActivityExportaImagem.this);
            }
            @Override
            public void onFailure(Call<List<ProdutoFoto>> call, Throwable t) {
                progress_bar.setVisibility(View.INVISIBLE);
                progress_title.setVisibility(View.INVISIBLE);
                util.showMsgAlerta("Não foi possivel exportar os arquivos! Verifique sua conexão a rede/internet",ActivityExportaImagem.this);
            }
        });
    }
}
