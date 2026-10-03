package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaTabelaAnaliseAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaUsuarioAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.UfAdapter;
import com.example.rcksuporte05.rcksistemas.model.Generico;

import java.util.ArrayList;
import java.util.List;

public class ActivityTabelaAnalise extends AppCompatActivity {
    RecyclerView recyclerView;
    private ListaTabelaAnaliseAdapter adapter;
    ListaTabelaAnaliseAdapter.Listener listener;
    Toolbar toolbar;
    Context context;
    Button button;
    DBHelper db = new DBHelper(this);
    List<Generico> lista = new ArrayList<>();
    String nomeTabela;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabela_analise);
        context = this;
        nomeTabela = getIntent().getStringExtra("nomeTabela");
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        button = findViewById(R.id.button);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsgLimparApagar("Deseja limpar/excluir a tabela " + nomeTabela, nomeTabela);
            }
        });
        listener = new  ListaTabelaAnaliseAdapter.Listener() {
            @Override
            public void onClick(int position) {
                showMsgApagar("Deseja apagar o registro clicado/selecionado? ", nomeTabela, lista.get(position).getId());

            }
        };
        setLista();
    }

    @SuppressLint("Range")
    private void setLista() {
        Cursor cursor;
        if( nomeTabela.equalsIgnoreCase("TBL_WEB_RECEBER_CAB"))
            cursor = db.listaDados("SELECT  WRC.*, CAD.NOME_CADASTRO, CAD.NOME_FANTASIA " +
                    "FROM TBL_WEB_RECEBER_CAB AS WRC " +
                    "INNER JOIN TBL_CADASTRO AS CAD ON CAD.ID_CADASTRO_SERVIDOR = WRC.ID_CADASTRO" );
        else
            cursor = db.listaDados("SELECT * FROM " + nomeTabela);

        if( nomeTabela.equalsIgnoreCase("TBL_WEB_RECEBER_CAB")) {
            while (cursor.moveToNext()) {
                Generico generico = new Generico();
                generico.setId(cursor.getString(cursor.getColumnIndex("ID_WEB_RECEBER")));
                generico.setValor(cursor.getString(cursor.getColumnIndex("VALOR")));
                generico.setNome(cursor.getString(cursor.getColumnIndex("NOME_CADASTRO")));
                generico.setDatae(cursor.getString(cursor.getColumnIndex("DATA_VENCIMENTO")));
                generico.setDatap(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
                generico.setId_generico(cursor.getString(cursor.getColumnIndex("CHAVE_RECEBER_APP")));
                lista.add(generico);
            }
        }
        setRecyclerView(lista);
    }


    private void setRecyclerView(List<Generico> lista) {
        adapter = new ListaTabelaAnaliseAdapter(lista, listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void showMsgLimparApagar( String mensagem, String nomeTabela) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityTabelaAnalise.this).inflate(R.layout.dialog_sim_nao_cancelar_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTabelaAnalise.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button btnCancelar = (Button) dialogView.findViewById(R.id.btnMensagemCancelar);
        btnCancelar.setText("Cancelar a ação");
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }

        });

        Button btnLimpar = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnLimpar.setText("Limpar a tabela");
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( db.deleteDados(nomeTabela, "1", null) > 0) {
                    setLista();
                    Toast.makeText(context, "Dados da tabela apagados com sucesso!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Falha ao apagar os dados da tabela!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        Button btnExcluir = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnExcluir.setText("Excluir a tabela");
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( db.onDropTable(nomeTabela)) {
                    db.onCreate(db.getWritableDatabase());
                    Toast.makeText(context, "Tabela excluida/recriada com sucesso!", Toast.LENGTH_SHORT).show();
                    setLista();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Falha ao excluir a tabela!", Toast.LENGTH_SHORT).show();
                    setLista();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    public void showMsgApagar( String mensagem, String nomeTabela, String idRegistro) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityTabelaAnalise.this).inflate(R.layout.dialog_sim_nao_cancelar_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTabelaAnalise.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button btnCancelar = (Button) dialogView.findViewById(R.id.btnMensagemCancelar);
        btnCancelar.setText("Cancelar a ação");
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }

        });

        Button btnLimpar = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnLimpar.setText("");
        btnLimpar.setVisibility(View.INVISIBLE);
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Button btnExcluir = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnExcluir.setText("Excluir o registro");
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( db.deleteDados(nomeTabela, "ID_WEB_RECEBER = ?", new String[]{idRegistro}) > 0) {
                    Toast.makeText(context, "registro excluido com sucesso!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Falha ao excluir o registro!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
}