package com.example.rcksuporte05.rcksistemas.activity;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.rcksuporte05.rcksistemas.DAO.AlertaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Alerta;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAlerta extends AppCompatActivity {
    @BindView(R.id.swit_emissao_doc)
    Switch swit_emissao_doc;
    @BindView(R.id.swit_vencimento_doc)
    Switch swit_vencimento_doc;
    @BindView(R.id.swit_novo_app)
    Switch swit_novo_app;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;
    @BindView(R.id.txvAlerta)
    TextView txvAlerta;
    @BindView(R.id.txvFirebaseKey)
    EditText txvFirebaseKey;
    @BindView(R.id.progress_ajuda)
    ProgressBar progress_bar;
    @BindView(R.id.progress_title)
    TextView progress_title;
    Alerta alerta;
    DBHelper db = new DBHelper(this);
    ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    AlertaDAO alertaDAO = new AlertaDAO(db);
    String url;
    PackageInfo pInfo = null;
    Utilitaria util = new Utilitaria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        ButterKnife.bind(this);
        txvAlerta.setText("Marque as opções para receber\n os alerta(s)/notificações");
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txvFirebaseKey.setText(UsuarioHelper.getUsuario().getAparelho_key_firebase());
        showDados();
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.btnConfirmar)
    public void addAlerta(){
        Alerta alerta = new Alerta();
        alerta.setAlerta_emissao_doc( swit_emissao_doc.isChecked() ? "S": "N" );
        alerta.setAlerta_vencimento_doc( swit_vencimento_doc.isChecked() ? "S": "N" );
        alerta.setAlerta_novo_app( swit_novo_app.isChecked() ? "S": "N" );
        if( alertaDAO.add(alerta) ){  //metodo retorno boolean
            showHideProgressBar(true);
            getConfiguracao();
        } else {
            showHideProgressBar(false);
            Toast.makeText(ActivityAlerta.this, " Falha ao atualizar os dados!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showDados(){
        alerta = alertaDAO.getAlerta("SELECT * FROM TBL_ALERTA");
        if ( !alerta.getId().isEmpty()) {
            swit_emissao_doc.setChecked( alerta.getAlerta_emissao_doc().equalsIgnoreCase("S") ? true : false );
            swit_vencimento_doc.setChecked( alerta.getAlerta_vencimento_doc().equalsIgnoreCase("S") ? true : false );
            swit_novo_app.setChecked( alerta.getAlerta_novo_app().equalsIgnoreCase("S") ? true : false );
        }
    }

    public void getConfiguracao() {
        if ( pInfo == null) {
            showHideProgressBar(false);
            return;
        }
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if ( !configuracao.getId().isEmpty()  )
            updateUsuarioAPI();
        else
            Toast.makeText( ActivityAlerta.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!",Toast.LENGTH_LONG).show();
    }

    private void showHideProgressBar(boolean isVisivel) {
        if( isVisivel)
            progress_bar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(ActivityAlerta.this, R.color.branco), PorterDuff.Mode.SRC_IN );
        progress_bar.setVisibility(isVisivel ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility(isVisivel ? View.VISIBLE : View.INVISIBLE);
    }

    private void updateUsuarioAPI() { //Método para atualizar na base de dados no servidor os parametros p a utilização de Alerts no app do usuario logado
        Rotas apiRotas = Api.buildRetrofit(false);
        Call<Usuario> call;
        call = apiRotas.updateUsuario( setUsuario(UsuarioHelper.getUsuario()) );
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                showHideProgressBar(false);
                switch (response.code()) {
                    case 200:
                        Toast.makeText(ActivityAlerta.this, "Atenção!\nDados atualizados com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        util.showMsgAlerta("Falha ao atualizar os dados! Tente novamente!\n("+ String.valueOf(response.code()) + ")", ActivityAlerta.this);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                showHideProgressBar(false);
                util.showMsgAlerta("Não foi possivel sincronizar com o servidor, por favor verifique sua conexão!!" +
                        "\n" + t.getMessage(), ActivityAlerta.this);
            }
        });
    }

    private Usuario setUsuario(Usuario usuario) {
        Usuario usuario1 = new Usuario();
        try {
            usuario1.setId_usuario(usuario.getId_usuario());
            usuario1.setAparelho_key_firebase(usuario.getAparelho_key_firebase());
            usuario1.setAparelho_versao_app(usuario.getAparelho_versao_app());
            usuario1.setAparelho_notifica_update_app(usuario.getAparelho_notifica_update_app());
            usuario1.setAparelho_notifica_status_ped(usuario.getAparelho_notifica_status_ped());
            usuario1.setAparelho_notifica_vencto_fin(usuario.getAparelho_notifica_vencto_fin());
            usuario.setAparelho_versao_app(pInfo.versionName.substring(0, 7));
        } catch ( NullPointerException e) {
        } catch ( Exception e) {
        }
        return  usuario1;
        //Método para atualizar na base de dados no servidor os parametros p a utilização de Alerts no app do usuario
    }
}
