package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.LoginDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.TabelaRCK;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTecnico extends AppCompatActivity {
    @BindView(R.id.btn_tecnico_update_tabela_rck)
    Button btn_tecnico_update_tabela_rck;

    @BindView(R.id.btn_tecnico_update_tabela_app)
    Button btn_tecnico_update_tabela_app;

    @BindView(R.id.btn_tecnico_update_indice_app)
    Button btn_tecnico_update_indice_app;

    Toolbar toolbar;
    PackageInfo pInfo = null;
    Context context;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnico);
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
        context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.gc();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @OnClick(R.id.btn_tecnico_update_tabela_rck)
    public void updateTabelasRck() { //Atualiza a estrutura das tabelas do rck no servidor
        if (!UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D") ) {
            Toast.makeText(ActivityTecnico.this, "Usuario não tem permissão para acessar este módulo do menu!", Toast.LENGTH_LONG).show();
            return;
        }
        DBHelper db = new DBHelper(ActivityTecnico.this);
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

        boolean sucesso = false;
        if (!configuracao.getId().isEmpty()) {
            Rotas apiRotas = Api.buildRetrofit(false);
            Call<TabelaRCK> call;
            call = apiRotas.alterarTabelas();
            call.enqueue(new Callback<TabelaRCK>() {
                @Override
                public void onResponse(Call<TabelaRCK> call, Response<TabelaRCK> response) {
                    switch (response.code()) {
                        case 200:
                            TabelaRCK tabelaRCK = response.body();
                            if ( tabelaRCK.isSucesso())
                                Toast.makeText(context, "Tabelas atualizadas com sucesso!", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context, "Tabelas não atualizadas!!", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(context, "Falha ao atualizar as tabelas! (" + response.code() + ")", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(context, "Falha ao atualizar as tabelas! (" + response.code() + ")", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
                @Override
                public void onFailure(Call<TabelaRCK> call, Throwable t) {
                    Toast.makeText(context, "Erro  ao atualizar as tabelas!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(ActivityTecnico.this, "Parametros para conexão não cadastrados!", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btn_tecnico_update_tabela_app)
    public void updateTabelasApp(){ //Atualiza a estrutura das tabelas no banco de dados SQLITE do app
        DBHelper db = new DBHelper(context);
        db.onUpgrade(db.getWritableDatabase(), 27, 27);
        Toast.makeText(context, "Tabela(s) atualizada(s)!!", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_tecnico_lista_tabela_app)
    public void listaTabelasApp(){ //Atualiza a estrutura das tabelas no banco de dados SQLITE do app
        startActivity(new Intent(ActivityTecnico.this, ActivityTabelasApp.class));
    }

    @OnClick(R.id.btn_tecnico_update_indice_app)
    public void updateIndiceApp(){ //Apaga e cria novamente os indices no banco de dados SQLITE do app
        //if (!UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D") ) {
        //    Toast.makeText(ActivityTecnico.this, "Usuario não tem permissão para acessar este módulo do menu!", Toast.LENGTH_LONG).show();
        //    return;
        //}
        DBHelper db = new DBHelper(context);
        if (db.reconstruirTodosIndices()) {
            Toast.makeText(context, "Índices recriados e otimizados com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Falha ao recriar os índices do banco.", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btn_tecnico_lista_usuario_app)
    public void listaUsuarioApp(){
        if ( UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("A") ) {
            startActivity(new Intent(ActivityTecnico.this, ActivityEmpresaCliente.class).putExtra("acao", 0) ); //Lista usuarios
        } else {
            //startActivity(new Intent(ActivityTecnico.this, ActivityEmpresaCliente.class).putExtra("acao", 0) ); //Lista usuarios
            Toast.makeText(ActivityTecnico.this, "Usuario não tem permissão para acessar este módulo do menu!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @OnClick(R.id.btn_tecnico_lista_pedido_app)
    public void listaPedidoApp(){
        if ( UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("A") ) {
            startActivity(new Intent(ActivityTecnico.this, ActivityEmpresaCliente.class).putExtra("acao", 1)); //Lista pedidos exportados pelos usuarios(ultimos 5 dias)
        } else {
            Toast.makeText(ActivityTecnico.this, "Usuario não tem permissão para acessar este módulo do menu!", Toast.LENGTH_LONG).show();
            return;
        }
    }

}