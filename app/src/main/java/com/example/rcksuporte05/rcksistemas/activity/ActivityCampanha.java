package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCampanhaAdapter;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCampanha extends AppCompatActivity implements ListaCampanhaAdapter.Listener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleCampanha)
    RecyclerView recyclerView;
    @BindView(R.id.edtTotalCampanhas)
    EditText edtTotalCampanhas;

    @BindView(R.id.txtDescricao)
    TextView txtDescricao;
    private ListaCampanhaAdapter listaCampanhaAdapter;
    private DBHelper db = new DBHelper(this);
    private CampanhaComercialCabDAO campanhaComercialCabDAO;
    private Configuracao configuracao = new Configuracao();
    private int offSet = 0;
    private int totalReg = 0;
    private int limitReg = 50;
    private String SQL;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String dataAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);
        ButterKnife.bind(this);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        toolbar.setTitle("Campanha(s) ativa(s)");
        getConfiguracao();
        if (getIntent().getIntExtra("pedido", 0) >= 1) {
            toolbar.setSubtitle(getIntent().getStringExtra("produto"));
            try {
                totalReg = CampanhaHelper.getListaCampanha().size();
            } catch ( NumberFormatException|NullPointerException e) {
            }
            setRecycleView(CampanhaHelper.getListaCampanha());
        } else
            try {
                dataAtual  = sdf.format(new Date());
                SQL = "SELECT  COUNT(*) AS TOTALREG FROM TBL_CAMPANHA_COMERCIAL_CAB WHERE (DATA_INICIO <= '" + dataAtual + "' AND  DATA_FIM >= '" + dataAtual + "') ORDER BY NOME_CAMPANHA;";
                totalReg = campanhaComercialCabDAO.getTotalReg(SQL);
                setRecycleView(campanhaComercialCabDAO.getLista());
            } catch (CursorIndexOutOfBoundsException e) {
                edtTotalCampanhas.setText("0/0");
            }
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.addOnScrollListener(new ActivityCampanha.ScrollListener());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setRecycleView(List<CampanhaComercialCab> lista) {
        listaCampanhaAdapter = new ListaCampanhaAdapter(lista, this);
        recyclerView.setAdapter(listaCampanhaAdapter);
        if (lista.size() > 0)
            edtTotalCampanhas.setText("1/" + totalReg);
        else
            edtTotalCampanhas.setText("0/" + totalReg);
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            edtTotalCampanhas.setText( "(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
        }

    }
    private int getFirstItem(){
        return ((LinearLayoutManager)recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private int getLastItem() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastVisibleItemPosition();
    }

    @Override
    public void onClickListener(int position) {
        if (getIntent().getIntExtra("pedido", 0) >= 1) {
            CampanhaHelper.setCampanhaComercialCab(listaCampanhaAdapter.getItem(position));
            PedidoHelper.getWebPedidoItem().setIdCampanha(listaCampanhaAdapter.getItem(position).getIdCampanha());
            PedidoHelper.getWebPedidoItem().setNomeCampanha(listaCampanhaAdapter.getItem(position).getNomeCampanha());
            finish();
        } else {
            CampanhaHelper.setCampanhaComercialCab(listaCampanhaAdapter.getItem(position));
            startActivity(new Intent(ActivityCampanha.this, ActivityCampanhaItem.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("pedido", 0) >= 1) {
            getMenuInflater().inflate(R.menu.menu_campanha, menu);
            txtDescricao.setText("TOQUE NA CAMPANHA PARA CONFIRMAR");
            txtDescricao.setTextSize(12);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.limpaCampanha:
                limpaCampanha();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void limpaCampanha(){
        CampanhaHelper.setCampanhaComercialCab(null);
        PedidoHelper.getWebPedidoItem().setIdCampanha(0);
        PedidoHelper.getWebPedidoItem().setNomeCampanha("");
        finish();
    }
}
