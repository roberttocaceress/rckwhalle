package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaEmpresaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaUsuarioAdapter;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ActivityEmpresaCliente extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    ListaEmpresaClienteAdapter listaEmpresaClienteAdapter;
    ListaEmpresaClienteAdapter.Listener listener;
    List<EmpresaParametro> lista = new ArrayList<>();
    Context context;
    DBHelper db = new DBHelper(this);
    private int acao = 0;
    Button btnConfirmar;
    PackageInfo pInfo = null;
    String versaoApp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.recyclerView);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        context = this;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versaoApp = pInfo.versionName.substring(0,7);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Voltar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false));
        acao = getIntent().getIntExtra("acao", 0); //0 = listar usuarios   1 = listar pedidos exportados nos ultimos 5 dias

        listener = new ListaEmpresaClienteAdapter.Listener() {
            @Override
            public void onClickListener(int position) {
                String versaoAPI = lista.get(position).getVersaoAPI();
                if (acao == 0)
                    startActivity(new Intent(ActivityEmpresaCliente.this, ActivityUsuario.class)
                            .putExtra("razaoSocial", lista.get(position).getRazao_social())
                            .putExtra("url", lista.get(position).getIp_url())
                            .putExtra("porta", lista.get(position).getPorta())
                            .putExtra("versaoAPI", lista.get(position).getVersaoAPI())
                            .putExtra("conexaoSegura", lista.get(position).getConexao_segura().equalsIgnoreCase("S") ? "S" : "N")
                            .putExtra("acao", "lista"));
                else if (acao == 1)
                    startActivity(new Intent(ActivityEmpresaCliente.this, ActivityPedidoExportado.class)
                            .putExtra("razaoSocial", lista.get(position).getRazao_social())
                            .putExtra("url", lista.get(position).getIp_url())
                            .putExtra("porta", lista.get(position).getPorta())
                            .putExtra("versaoAPI", lista.get(position).getVersaoAPI())
                            .putExtra("conexaoSegura", lista.get(position).getConexao_segura().equalsIgnoreCase("S") ? "S" : "N"));
            }
        };
        if (!UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D") ) {
            Configuracao configuracao = new Configuracao();
            ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
            configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            lista.add(new EmpresaParametro(configuracao.getUrl_ip_desc_1(), configuracao.getUrl_ip_1(), configuracao.getPorta_1(), configuracao.getConexao_segura(), versaoApp));

        }else{
            lista.add(new EmpresaParametro("LOCALHOST", "192.168.100.152", 725, "N", "2.6.9"));
            lista.add(new EmpresaParametro("RCK SISTEMAS ESPECIFICOS", "rcksistemas.ddns.com.br", 735, "N", versaoApp));
            lista.add(new EmpresaParametro("JH ATACADO DE ALIMENTOS E PRODUTOS DE LIMPEZA LTDA", "jhdistribuidora.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("VICTORINO & CIA LTDA - ME-INATIVO", "victorino.ddns.net", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("VICTORINO & CIA LTDA - ME", "vpn3.cloud.azulti.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("NORTE NUTRI FOODS COMERCIO DE PRODUTOS", "nortenutri.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("BERTOLA COSMÉTICOS E ACESSÓRIOS EIRELI ME", "bertolacosmeticos.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("KAIOWAS DISTRIBUIDORA", "rckkaiowas.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("KR DISTRIBUIDORA", "krdistribuidora.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("SANTANAO ALIMENTOS", "santanaoalimentos.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("TKS", "tksimports.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("PIETRO FOODS", "rckpietrofoods.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("OLIVEIRA COMERCIO", "oliveiracomercio.ddns.com.br", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("DALLA", "187.108.195.101", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("KR DISTRIBUIDORA MT", "138.118.3.241", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("REI DAS EMBALAGENS", "170.81.87.178", 725, "N", versaoApp));
            lista.add(new EmpresaParametro("LR DISTRIBUIDORA DE EMBALAGENS", "lrdistribuidora.ddns.com.br", 725, "N", versaoApp));
        }
        setRecyclerView(lista);


    }
    private void setRecyclerView(List<EmpresaParametro> lista) {
        String btnTitulo;
        if ( acao == 0)
            btnTitulo = "LISTAR USUÁRIO(S)";
        else
            btnTitulo = "LISTAR PEDIDO(S)";
        listaEmpresaClienteAdapter = new ListaEmpresaClienteAdapter( context, lista, listener, btnTitulo);
        recyclerView.setAdapter(listaEmpresaClienteAdapter);
        listaEmpresaClienteAdapter.notifyDataSetChanged();
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
}