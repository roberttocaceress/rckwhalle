package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.TabelasApp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTabelasApp extends AppCompatActivity {

    @BindView(R.id.txvTabelas)
    TextView txvTabelas;

    @BindView(R.id.lstvTabelas)
    ListView lstvTabelas;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Context context;
    private DBHelper db = new DBHelper(this);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabelas_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.lstvTabelas);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView lstvTabelas = (ListView) findViewById(R.id.lstvTabelas);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTabelasApp());
        lstvTabelas.setAdapter(adapter);
        lstvTabelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(ActivityTabelasApp.this, ActivityTabelaAnalise.class).putExtra("nomeTabela", adapter.getItem(position) ));
                //showMsgLimparApagar("Deseja limpar ou excluir a tabela selecionada(" + adapter.getItem(position) + ")", adapter.getItem(position));
            }

        });

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
    private ArrayList<String> getTabelasApp() {
        ArrayList<String> lista = new ArrayList<>();
        Cursor cursor = db.listaDados("SELECT * FROM SQLITE_MASTER WHERE TYPE ='table' AND name NOT LIKE 'sqlite_%' ORDER BY NAME;");
        if (cursor.getCount() <= 0)
            return lista;
        cursor.moveToFirst();
        do {
            try {
                lista.add(cursor.getString(cursor.getColumnIndex("name")));
            } catch (CursorIndexOutOfBoundsException e) {
            }
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();
        return lista;
    }

    public void showMsgLimparApagar( String mensagem, String nomeTabela) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityTabelasApp.this).inflate(R.layout.dialog_sim_nao_cancelar_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTabelasApp.this);
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
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Falha ao excluir a tabela!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

}