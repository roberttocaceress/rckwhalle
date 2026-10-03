package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.ButterKnife;

public class ActivityVideoAula extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout lyt_pesquisa_cliente, lyt_pesquisa_cliente_status, lyt_atualiza_cliente, lyt_financeiro_cliente;
    LinearLayout lyt_pedido_cliente, lyt_novo_cliente, lyt_edita_exclui_cliente;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_video_aulas);
        ButterKnife.bind(this);
        activity = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lyt_pesquisa_cliente = (LinearLayout) findViewById(R.id.lyt_pesquisa_cliente);
        lyt_pesquisa_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText( activity, "Pesquisa por nome/razão social/cnpj", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, ActivityNovidadeVideo.class)
                        .putExtra("url", "Iac3jx_QHKU")
                        .putExtra("descricao", "Toque no icone de pesquisa para digitar a razão social/cnpj que deseja pesquisar!\nPara limpar a pesquisa digitada toque no icone correspondente(X)"));
            }
        });

        lyt_pesquisa_cliente_status = (LinearLayout) findViewById(R.id.lyt_pesquisa_cliente_status);
        lyt_pesquisa_cliente_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ActivityNovidadeVideo.class)
                        .putExtra("url", "1cD_VewTHI0")
                        .putExtra("descricao", "Toque no menu superior a esquerda, para efetuar a pesquisa desejada!"));
            }
        });

        lyt_atualiza_cliente = (LinearLayout) findViewById(R.id.lyt_atualiza_cliente);
        lyt_atualiza_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText( activity, "Pesquisa por nome/razão social/cnpj", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, ActivityNovidadeVideo.class)
                        .putExtra("url", "Iac3jx_QHKU")
                        .putExtra("descricao", "Toque no icone primeiro icone a esquerda para efetuar a atualização/sincronização do cadastro do cliente!"));
            }
        });

        lyt_financeiro_cliente = (LinearLayout) findViewById(R.id.lyt_financeiro_cliente);
        lyt_financeiro_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ActivityNovidadeVideo.class)
                        .putExtra("url", "Uj_EIvc5Tl8")
                        .putExtra("descricao", "Toque no icone correspondente ao simbolo da moeda($), para efetuar a pesquisa ao(s) tittulo(s) vencido(s)/a vencer/pago(s) do cliente!"));
            }
        });

        lyt_pedido_cliente = (LinearLayout) findViewById(R.id.lyt_pedido_cliente);
        lyt_pedido_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ActivityNovidadeVideo.class)
                        .putExtra("url", "yWSo0GEeBmo")
                        .putExtra("descricao", "Toque no icone correspondente ao carrinho de compras/ou toque na lista e depois no botão novo pedido, para iniciar um pedido para o cliente!"));
            }
        });

        lyt_novo_cliente = (LinearLayout) findViewById(R.id.lyt_novo_cliente);
        lyt_novo_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ActivityNovidadeVideo.class)
                        .putExtra("url", "G2m0mae1oC4")
                        .putExtra("descricao", "Toque no icone com o sinal(+) para iniciar o cadastro de um novo cliente! Para sincronizar/enviar o cadastro ao servidor da empresa, assista ao terceiro video nesta lista!"));
            }
        });

        lyt_edita_exclui_cliente = (LinearLayout) findViewById(R.id.lyt_edita_exclui_cliente);
        lyt_edita_exclui_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ActivityNovidadeVideo.class)
                        .putExtra("url", "rH6w80ruqik")
                        .putExtra("descricao", "Para editar o cadastro toque no cliente desejado na lista, " +
                                "para excluir, efetue um clique longo no mesmo, e posteriormente clique no icone correspondente(lixeira) no menu superior!"));
            }
        });
    }
}