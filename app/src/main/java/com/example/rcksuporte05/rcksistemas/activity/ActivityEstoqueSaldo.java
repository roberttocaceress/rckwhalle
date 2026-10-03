package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaEstoqueSaldoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Deposito;
import com.example.rcksuporte05.rcksistemas.model.EstoqueSaldo;
import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.example.rcksuporte05.rcksistemas.model.ProdutoGrupo;
import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;
import com.example.rcksuporte05.rcksistemas.model.ProdutoSubGrupo;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEstoqueSaldo extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    @BindView(R.id.tb_estoque_saldo)
    Toolbar toolbar;
    @BindView(R.id.listaEstoqueRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.edt_nome_produto)
    EditText edt_nome_produto;

    @BindView(R.id.edt_codigo_produto)
    EditText edt_id_produto;

    @BindView(R.id.edt_ean_produto)
    EditText edt_ean_produto;

    @BindView(R.id.img_view_avancar)
    ImageView btn_view_avancar;

    @BindView(R.id.img_view_menu)
    ImageView btn_view_menu;

    @BindView(R.id.card_view_menu)
    CardView card_view_menu;

    @BindView(R.id.chk_status_todos)
    CheckBox chk_status_todos;

    @BindView(R.id.chk_status_ativo)
    CheckBox chk_status_ativo;

    @BindView(R.id.chk_status_inativo)
    CheckBox chk_status_inativo;

    @BindView(R.id.sp_deposito)
    Spinner sp_deposito;

    @BindView(R.id.sp_grupo)
    Spinner sp_grupo;

    @BindView(R.id.txv_grupo)
    TextView txv_grupo;

    @BindView((R.id.helpEstoqueSaldo))
    ImageView helpEstoqueSaldo;

    //@BindView(R.id.buscaProduto)
    //SearchView buscaProduto;
    @BindView(R.id.progress_splash)
    ProgressBar progress_bar;
    @BindView(R.id.progress_title)
    TextView progress_title;

    ListaEstoqueSaldoAdapter listaEstoqueSaldoAdapter;
    private ListaEstoqueSaldoAdapter.Listener listener;
    PackageInfo pInfo = null;
    private Context context;
    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private boolean visivel;
    private String ativo = "T";
    private String tipo = "G";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo_estoque);
        ButterKnife.bind(this);
        context = this;
        toolbar.setTitle("Lista de Produtos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManagerProduto = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerProduto);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario( usuarioBO.getUsuarioLogin(db));
        showHideFiltros();
        //List<ProdutoGrupo> lista = new ArrayList<>();
        ArrayAdapter arrayAdapter = null;
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        List<Generico> lista = new ArrayList<>();
        Generico generico = new Generico();
        generico.setId("0");
        generico.setNome("Todos");
        generico.setAtivo("S");
        lista.add(generico);
        if ( configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("G")) { //Grupo de produto
            tipo = "G";
            txv_grupo.setText("Grupo");
            Cursor cursor = produtoDAO.getCursor("SELECT * FROM TBL_PRODUTO_GRUPO");
            if ( cursor.getCount() <= 0)
                return;
            cursor.moveToFirst();
            do {
                Generico produtoGrupo = new Generico();
                try {
                    produtoGrupo.setId(cursor.getString(cursor.getColumnIndex("ID")));
                    produtoGrupo.setNome(cursor.getString(cursor.getColumnIndex("NOME_GRUPO")));
                    produtoGrupo.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                    lista.add(produtoGrupo);
                } catch ( ArrayIndexOutOfBoundsException e) {
                }
            } while (cursor.moveToNext());
            cursor.close();
            sp_grupo.setAdapter(new ArrayAdapter( ActivityEstoqueSaldo.this, android.R.layout.simple_list_item_activated_1, lista));
        } else if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("S")) { // SubGrupo
            tipo = "S";
            txv_grupo.setText("Sub Grupo");
            Cursor cursor = produtoDAO.getCursor("SELECT * FROM TBL_PRODUTO_SUB_GRUPO");
            if (cursor.getCount() <= 0)
                return;
            cursor.moveToFirst();
            do {
                Generico produtoSubGrupo = new Generico();
                try {
                    produtoSubGrupo.setId(cursor.getString(cursor.getColumnIndex("ID")));
                    produtoSubGrupo.setNome(cursor.getString(cursor.getColumnIndex("NOME_SUB_GRUPO")));
                    produtoSubGrupo.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                    lista.add(produtoSubGrupo);
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            } while (cursor.moveToNext());
            cursor.close();
            sp_grupo.setAdapter(new ArrayAdapter(ActivityEstoqueSaldo.this, android.R.layout.simple_list_item_activated_1, lista));
        } else if (configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("L")) {
            tipo = "L";
            txv_grupo.setText("Linha");
            Cursor cursor = produtoDAO.getCursor("SELECT * FROM TBL_PRODUTO_LINHA_COLECAO");
            if (cursor.getCount() <= 0)
                return;
            cursor.moveToFirst();
            do {
                Generico produtoLinha = new Generico();
                try {
                    produtoLinha.setId(cursor.getString(cursor.getColumnIndex("ID_LINHA_COLECAO")));
                    produtoLinha.setNome(cursor.getString(cursor.getColumnIndex("NOME_DESCRICAO_LINHA")));
                    produtoLinha.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
                    lista.add(produtoLinha);
                } catch ( ArrayIndexOutOfBoundsException e) {
                }
            } while (cursor.moveToNext());
            cursor.close();
            sp_grupo.setAdapter(new ArrayAdapter(ActivityEstoqueSaldo.this, android.R.layout.simple_list_item_activated_1, lista));
        }

        List<Deposito> listaDeposito = new ArrayList<>();
        Deposito deposito = new Deposito();
        deposito.setId_deposito("0");
        deposito.setId_empresa("0");
        deposito.setNome_deposito("Todos");
        listaDeposito.add(deposito);

        Cursor cursor = produtoDAO.getCursor("SELECT * FROM TBL_DEPOSITO");
        if (cursor.getCount() >=  0) {
            cursor.moveToFirst();
            do {
                deposito = new Deposito();
                try {
                    deposito.setId_deposito(cursor.getString(cursor.getColumnIndex("ID_DEPOSITO")));
                    deposito.setNome_deposito(cursor.getString(cursor.getColumnIndex("NOME_DEPOSITO")));
                    deposito.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                    listaDeposito.add(deposito);
                } catch (ArrayIndexOutOfBoundsException|CursorIndexOutOfBoundsException e) {
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        sp_deposito.setAdapter(new ArrayAdapter( ActivityEstoqueSaldo.this, android.R.layout.simple_list_item_activated_1, listaDeposito));


        btn_view_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !chk_status_ativo.isChecked() && !chk_status_inativo.isChecked() && !chk_status_todos.isChecked())
                    ativo = "T";
                String nomeProduto  = edt_nome_produto.getText().toString().isEmpty() ? "-" : edt_nome_produto.getText().toString();
                String idProduto    = edt_id_produto.getText().toString().isEmpty() ? "-" : edt_id_produto.getText().toString();
                String codigoEAN    = edt_ean_produto.getText().toString().isEmpty() ? "-" : edt_ean_produto.getText().toString();
                visivel = true;
                showHideFiltros();
                getEstoqueSaldo( ativo, nomeProduto, idProduto, codigoEAN, listaDeposito.get(sp_deposito.getSelectedItemPosition()).getId_deposito(), lista.get( sp_grupo.getSelectedItemPosition()).getId(), tipo);
            }
        });

        btn_view_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideFiltros();
            }
        });

        helpEstoqueSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityEstoqueSaldo.this, ActivityDialogEstoqueSaldo.class));
            }
        });
    }


    private void showHideProgressBar(boolean visivel) {
        if (visivel)
            progress_bar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progress_bar.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
    }
    private void showHideFiltros() {
        ViewGroup.LayoutParams params = card_view_menu.getLayoutParams();
        if (!visivel) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            visivel = !visivel;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_down_white);
            //btn_view_avancar.setVisibility(View.VISIBLE);
        } else {
            params.height = 0;
            visivel = !visivel;
            btn_view_menu.setImageResource(R.drawable.ic_action_arrow_up_white);
            //btn_view_avancar.setVisibility(View.INVISIBLE);
        }
        card_view_menu.setLayoutParams(params);
        chk_status_todos.setOnCheckedChangeListener(this);
        chk_status_ativo.setOnCheckedChangeListener(this);
        chk_status_inativo.setOnCheckedChangeListener(this);


    }
    private void getEstoqueSaldo(String status, String nomeProduto, String idProduto, String codigoEAN, String idDeposito, String idGrupo, String tipo  ) { //Tipo se refere a grupo/subgrupo/linha
        showHideProgressBar(true);
        configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if ( !configuracao.getId().isEmpty()  ) {
            Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<EstoqueSaldo>> call;
            call = apiRotas.getEstoqueSaldo(status, nomeProduto, idProduto, codigoEAN, idDeposito, idGrupo, tipo, UsuarioHelper.getUsuario().getId_usuario(), cabecalho);

            call.enqueue(new Callback<List<EstoqueSaldo>>() {
                @Override
                public void onResponse(Call<List<EstoqueSaldo>> call, Response<List<EstoqueSaldo>> response) {
                    switch (response.code()) {
                        case 200:
                            setRecyclerView(response.body());
                            break;
                        case 500:
                            showHideProgressBar(false);
                            Toast.makeText(ActivityEstoqueSaldo.this, "Erro " + response.code(), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            showHideProgressBar(false);
                            Toast.makeText(ActivityEstoqueSaldo.this, "Erro " + response.code(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<EstoqueSaldo>> call, Throwable t) {
                    showHideProgressBar(false);
                    Toast.makeText(ActivityEstoqueSaldo.this, "Falha " + t.toString() , Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText( ActivityEstoqueSaldo.this, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar",Toast.LENGTH_LONG).show();
        }

    }
    public void setRecyclerView(List<EstoqueSaldo> lista) {
        listaEstoqueSaldoAdapter = new ListaEstoqueSaldoAdapter(lista, listener, ActivityEstoqueSaldo.this );
        recyclerView.setAdapter(listaEstoqueSaldoAdapter);
        listaEstoqueSaldoAdapter.notifyDataSetChanged();
        showHideProgressBar(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch ( compoundButton.getId()) {
            case R.id.chk_status_ativo:
                if ( isChecked) {
                    ativo = "S";
                    chk_status_inativo.setChecked(!isChecked);
                    chk_status_todos.setChecked(!isChecked);
                }
                break;
            case R.id.chk_status_inativo:
                if ( isChecked) {
                    ativo = "N";
                    chk_status_ativo.setChecked(!isChecked);
                    chk_status_todos.setChecked(!isChecked);
                }
                break;
            case R.id.chk_status_todos:
                if ( isChecked) {
                    ativo = "T";
                    chk_status_inativo.setChecked(!isChecked);
                    chk_status_ativo.setChecked(!isChecked);
                }
                break;
        }
    }
}