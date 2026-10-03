package com.example.rcksuporte05.rcksistemas.activity;



import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityItemLinhaProduto extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleLinhaProdutos)
    RecyclerView recyclerView;
    @BindView(R.id.edtTotalProdutos)
    EditText edtTotalProdutos;
    private ListaProdutoAdapter listaProdutoAdapter;
    private ProdutoDAO produtoDAO;
    private Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_linha_produto);
        ButterKnife.bind(this);
        try {
            toolbar.setTitle(getIntent().getStringExtra("nomelinha"));
            DBHelper db = new DBHelper(this);
            configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
            setRecyclerView();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(this, "Nenhum produto dessa linha esta disponivel para venda externa", Toast.LENGTH_LONG).show();
            finish();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setRecyclerView() {
        listaProdutoAdapter = new ListaProdutoAdapter( produtoDAO.getLista("SELECT PROD.* FROM TBL_PRODUTO PROD INNER JOIN TBL_PRODUTO_LINHA_COLECAO LINHA ON PROD.ID_LINHA_COLECAO = LINHA.ID_LINHA_COLECAO\n" +
                "WHERE PROD.ID_LINHA_COLECAO = " + getIntent().getIntExtra("linha", 0) + ";",""), null, null, ActivityItemLinhaProduto.this, 0, configuracao);        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(listaProdutoAdapter);
        edtTotalProdutos.setText(listaProdutoAdapter.getItemCount() + " produtos nesta linha");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
