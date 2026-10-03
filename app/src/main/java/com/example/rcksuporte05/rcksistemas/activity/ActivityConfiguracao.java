package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity_.ActivityLogin_;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;

import java.util.List;

public class ActivityConfiguracao extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private EditText edt_url_ip_1, edt_url_porta_1, edt_url_ip_1_descricao;

    private EditText edt_url_ip_2, edt_url_porta_2, edt_url_ip_2_descricao;
    private EditText edt_time_conn, edt_time_read, edt_time_write;
    private CheckBox chk_con_segura, chk_usa_campanha, chk_usa_dscto_reais, chk_usa_limite_cred,
            chk_usa_pre_pedido, chk_usa_confirma_prazo, chk_usa_unidade_manut_estoque,
            chk_usa_paginacao_dados, chk_usa_icms_st, chk_usa_desc_auto_app, chk_usa_sinc_auto_app,
            chk_usa_verba_pedido, chk_cadastra_cliente, chk_atualiza_lista_exportado,chk_confima_sincronia;
    private Switch swt_marca_produto_comprado, swt_pesquisa_por_grupo, swt_pesquisa_por_sub_grupo, swt_pesquisa_por_linha, swt_pesquisa_todos_pedidos;
    private Switch swt_gira_tela_cliente, swt_gira_tela_produto, swt_gira_tela_cobranca;
    private Switch swt_gira_tela_pedido, swt_gira_tela_ped_pendente, swt_gira_tela_ped_enviado;
    private Button btn_salvar, btn_sair;
    private TextView txvOrdemCliente;
    Configuracao configuracao;
    private DBHelper db = new DBHelper(this);
    ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private String ordem_lista_cliente = "N";
    private String atual_lista_ped_exportado = "N";
    private String confirma_sincronia_automatizada = "N";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.tbl_config);
            toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.linearLayout3);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        edt_url_ip_1 = (EditText) findViewById(R.id.edt_url_ip_1);
        edt_url_porta_1 = (EditText) findViewById(R.id.edt_url_porta_1);
        edt_url_ip_1_descricao = (EditText) findViewById(R.id.edt_url_ip_1_descricao);
        txvOrdemCliente        = (TextView) findViewById(R.id.txvOrdemCliente);

        edt_url_ip_2 = (EditText) findViewById(R.id.edt_url_ip_2);
        edt_url_porta_2 = (EditText) findViewById(R.id.edt_url_porta_2);
        edt_url_ip_2_descricao = (EditText) findViewById(R.id.edt_url_ip_2_descricao);

        edt_time_conn = (EditText) findViewById(R.id.edt_time_conn);
        edt_time_read = (EditText) findViewById(R.id.edt_time_read);
        edt_time_write = (EditText) findViewById(R.id.edt_time_write);

        chk_con_segura = (CheckBox) findViewById(R.id.chk_con_segura);
        chk_usa_campanha = (CheckBox) findViewById(R.id.chk_usa_campanha);
        chk_usa_dscto_reais = (CheckBox) findViewById(R.id.chk_usa_dscto_reais);
        chk_usa_limite_cred = (CheckBox) findViewById(R.id.chk_usa_limite_cred);
        chk_usa_pre_pedido = (CheckBox) findViewById(R.id.chk_usa_pre_pedido);
        chk_usa_confirma_prazo = (CheckBox) findViewById(R.id.chk_usa_confirma_prazo);
        chk_usa_unidade_manut_estoque  = (CheckBox) findViewById(R.id.chk_usa_unidade_manut_estoque);
        chk_usa_paginacao_dados = (CheckBox) findViewById(R.id.chk_usa_paginacao_dados);
        chk_usa_icms_st = (CheckBox) findViewById(R.id.chk_usa_icms_st);
        chk_usa_desc_auto_app = (CheckBox) findViewById(R.id.chk_usa_desc_auto_app);
        chk_usa_sinc_auto_app = (CheckBox) findViewById(R.id.chk_usa_sinc_auto_app);
        chk_usa_verba_pedido  = (CheckBox) findViewById(R.id.chk_usa_verba_pedido);
        chk_cadastra_cliente  = (CheckBox) findViewById(R.id.chk_cadastra_cliente);
        chk_atualiza_lista_exportado  = (CheckBox) findViewById(R.id.chk_atualiza_lista_ped_exprtado);
        chk_confima_sincronia         = (CheckBox) findViewById(R.id.chk_confima_sincronia) ;

        btn_salvar = (Button) findViewById(R.id.btnConfirmar);
        btn_sair = (Button) findViewById(R.id.btnCancelar);
        swt_marca_produto_comprado  = (Switch) findViewById(R.id.swtMarcaProdutoJaComprado);
        swt_pesquisa_por_grupo     = (Switch) findViewById(R.id.swtPesquisaGrupo);
        swt_pesquisa_por_grupo     = (Switch) findViewById(R.id.swtPesquisaGrupo);
        swt_pesquisa_por_linha     = (Switch) findViewById(R.id.swtPesquisaLinha);
        swt_pesquisa_por_sub_grupo = (Switch) findViewById(R.id.swtPesquisaSubGrupo);
        swt_pesquisa_todos_pedidos = (Switch) findViewById(R.id.swtPesquisaTodosPedidos);

        swt_gira_tela_cliente = (Switch) findViewById(R.id.swtGiraTelaCliente);
        swt_gira_tela_produto = (Switch) findViewById(R.id.swtGiraTelaProduto);
        swt_gira_tela_cobranca = (Switch) findViewById(R.id.swtGiraTelaCobranca);
        swt_gira_tela_pedido = (Switch) findViewById(R.id.swtGiraTelaPedido);
        swt_gira_tela_ped_pendente = (Switch) findViewById(R.id.swtGiraTelaPedPendente);
        swt_gira_tela_ped_enviado = (Switch) findViewById(R.id.swtGiraTelaPedEnviado);

        showDados();
        disableChecks();
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConfiguracao();
            }
        });

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        swt_marca_produto_comprado.setOnCheckedChangeListener(this);
        swt_pesquisa_por_grupo.setOnCheckedChangeListener(this);
        swt_pesquisa_por_sub_grupo.setOnCheckedChangeListener(this);
        swt_pesquisa_por_linha.setOnCheckedChangeListener(this);
        //swt_pesquisa_todos_pedidos.setO
        //swt_pesquisa_por_grupo.setChecked(true);

    }
    private void disableChecks(){
        chk_usa_campanha.setEnabled(false);
        chk_usa_dscto_reais.setEnabled(false);
        chk_usa_limite_cred.setEnabled(false);
        chk_usa_pre_pedido.setEnabled(false);
        chk_usa_confirma_prazo.setEnabled(false);
        chk_usa_unidade_manut_estoque.setEnabled(false);
        chk_usa_paginacao_dados.setEnabled(false);
        chk_usa_icms_st.setEnabled(false);
        chk_usa_desc_auto_app.setEnabled(false);
        chk_usa_sinc_auto_app.setEnabled(false);
        chk_usa_verba_pedido.setEnabled(false);
        chk_cadastra_cliente.setEnabled(false);
        chk_atualiza_lista_exportado.setEnabled(false);
        chk_confima_sincronia.setEnabled(false);
    }

    private void addConfiguracao(){
        if (edt_url_ip_1.getText().toString().isEmpty()) {
            edt_url_ip_1.setError("Informe a url/id para a conexão");
            return;
        }
        if (edt_url_porta_1.getText().toString().isEmpty()) {
            edt_url_porta_1.setError("Informe o numero da porta para a conexão");
            return;
        }
        if (edt_url_ip_1_descricao.getText().toString().isEmpty()) {
            edt_url_ip_1_descricao.setError("Informe a descrição/nome da conexão");
            return;
        }

        if (edt_time_conn.getText().toString().isEmpty()) {
            edt_time_conn.setError("Informe o tempo limite para conexão");
            return;
        }
        if (edt_time_read.getText().toString().isEmpty()) {
            edt_time_read.setError("Informe o tempo limite para leitura");
            return;
        }
        if (edt_time_write.getText().toString().isEmpty()) {
            edt_time_write.setError("Informe o tempo limite para gravação");
            return;
        }

        configuracao.setUrl_ip_1(edt_url_ip_1.getText().toString().toLowerCase());
        configuracao.setPorta_1(Integer.parseInt(edt_url_porta_1.getText().toString()));
        configuracao.setUrl_ip_desc_1(edt_url_ip_1_descricao.getText().toString().toUpperCase());

        configuracao.setConexao_segura(chk_con_segura.isChecked() ? "S" : "N");
        if ( swt_pesquisa_por_linha.isChecked())
            configuracao.setPesquisa_produto_por_linha_grupo("L");
        else if ( swt_pesquisa_por_sub_grupo.isChecked() )
           configuracao.setPesquisa_produto_por_linha_grupo("S");
        else
            configuracao.setPesquisa_produto_por_linha_grupo("G");
        //configuracao.setPesquisa_produto_por_linha_grupo( swt_pesquisa_por_grupo.isChecked() ? "G" : "L");
        if( swt_pesquisa_todos_pedidos.isChecked())
            configuracao.setLista_todos_pedidos("S");
        else
            configuracao.setLista_todos_pedidos("N");

        if ( swt_marca_produto_comprado.isChecked()) {
            configuracao.setMarca_produto_ja_comprado("S");
        } else {
            configuracao.setMarca_produto_ja_comprado("N");
        }

        configuracao.setTrabalha_com_campanhas(configuracao.getTrabalha_com_campanhas());
        configuracao.setTrabalha_com_dscto_reais(configuracao.getTrabalha_com_dscto_reais());
        configuracao.setTrabalha_com_limite_cred(configuracao.getTrabalha_com_limite_cred());
        configuracao.setTrabalha_com_pre_pedido(configuracao.getTrabalha_com_pre_pedido());
        configuracao.setTrabalha_com_confirmacao_prazo(configuracao.getTrabalha_com_confirmacao_prazo());
        configuracao.setTrabalha_com_unidade_manut_estoque(configuracao.getTrabalha_com_unidade_manut_estoque());
        configuracao.setTrabalha_com_paginacao_dados(configuracao.getTrabalha_com_paginacao_dados());
        configuracao.setId_con_pgto_default_app(configuracao.getId_con_pgto_default_app());
        configuracao.setTrabalha_com_icms_st(configuracao.getTrabalha_com_icms_st());
        configuracao.setTrabalha_com_desc_auto_app(configuracao.getTrabalha_com_desc_auto_app());
        configuracao.setSincronia_automatica_app(configuracao.getSincronia_automatica_app());
        configuracao.setTrabalha_com_verba_pedido(configuracao.getTrabalha_com_verba_pedido());
        configuracao.setCadastra_cliente_app(configuracao.getCadastra_cliente_app());
        try {
            if ( configuracao.getTrabalha_com_cobranca() != null && !configuracao.getTrabalha_com_cobranca().isEmpty())
                configuracao.setTrabalha_com_cobranca(configuracao.getTrabalha_com_cobranca());
            else
                configuracao.setTrabalha_com_cobranca("N");
        } catch ( NullPointerException e) {
            configuracao.setTrabalha_com_cobranca("N");
        } catch ( Exception e) {
            configuracao.setTrabalha_com_cobranca("N");
        }
        try{
            if ( configuracao.getLista_todos_pedidos() != null && !configuracao.getLista_todos_pedidos().isEmpty())
                configuracao.setLista_todos_pedidos(configuracao.getLista_todos_pedidos());
            else
                configuracao.setLista_todos_pedidos("N");
        } catch ( NullPointerException e) {
            configuracao.setLista_todos_pedidos("N");
        } catch ( Exception e) {
            configuracao.setLista_todos_pedidos("N");
        }
        try{
            if ( configuracao.getLayout_venda_erp() != null && !configuracao.getLayout_venda_erp().isEmpty())
                configuracao.setLayout_venda_erp(configuracao.getLayout_venda_erp());
            else
                configuracao.setLayout_venda_erp("0");
        } catch ( NullPointerException e) {
            configuracao.setLayout_venda_erp("0");
        } catch ( Exception e) {
            configuracao.setLayout_venda_erp("0");
        }

        configuracao.setGira_tela_cliente( swt_gira_tela_cliente.isChecked() ? "S" : "N");
        configuracao.setGira_tela_produto( swt_gira_tela_produto.isChecked() ? "S" : "N");
        configuracao.setGira_tela_cobranca( swt_gira_tela_cobranca.isChecked() ? "S" : "N");
        configuracao.setGira_tela_pedido( swt_gira_tela_pedido.isChecked() ? "S" : "N");
        configuracao.setGira_tela_ped_pendente( swt_gira_tela_ped_pendente.isChecked() ? "S" : "N");
        configuracao.setGira_tela_ped_enviado( swt_gira_tela_ped_enviado.isChecked() ? "S" : "N");

        configuracao.setTime_conn(edt_time_conn.getText().toString());
        configuracao.setTime_read(edt_time_read.getText().toString());
        configuracao.setTime_write(edt_time_write.getText().toString());

        try {
            configuracao.setUrl_ip_2(edt_url_ip_2.getText().toString().toLowerCase());
            configuracao.setPorta_2(Integer.parseInt(edt_url_porta_2.getText().toString()));
            configuracao.setUrl_ip_desc_2(edt_url_ip_2_descricao.getText().toString().toUpperCase());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        configuracao.setOrdem_lista_cliente(ordem_lista_cliente);
        configuracao.setAtual_lista_ped_exportado(atual_lista_ped_exportado);
        configuracao.setConfirma_sincronia_dados(confirma_sincronia_automatizada);


        if (configuracaoDAO.add(configuracao)) {
            startActivity(new Intent(ActivityConfiguracao.this, ActivityLogin_.class));
            finish();
        } else {
            Toast.makeText(ActivityConfiguracao.this, " Falha ao atualizar os dados", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDados(){
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        swt_pesquisa_por_linha.setChecked(false);
        swt_pesquisa_por_sub_grupo.setChecked(false);
        swt_pesquisa_por_grupo.setChecked(true);
        if (!configuracao.equals(null)) {
            try {
                edt_url_ip_1.setText(configuracao.getUrl_ip_1().toString().toLowerCase());
                edt_url_porta_1.setText(Integer.toString(configuracao.getPorta_1()));
                edt_url_ip_1_descricao.setText(configuracao.getUrl_ip_desc_1().toUpperCase());
                try {
                    chk_con_segura.setChecked(configuracao.getConexao_segura().equalsIgnoreCase("S") ? true : false);
                    chk_usa_campanha.setChecked(  configuracao.getTrabalha_com_campanhas().equalsIgnoreCase("S") ? true : false );
                    chk_usa_dscto_reais.setChecked(configuracao.getTrabalha_com_dscto_reais().equalsIgnoreCase("S") ? true : false);
                    chk_usa_limite_cred.setChecked(configuracao.getTrabalha_com_limite_cred().equalsIgnoreCase("S") ? true : false);
                    chk_usa_pre_pedido.setChecked(configuracao.getTrabalha_com_pre_pedido().equalsIgnoreCase("S") ? true : false);
                    chk_usa_confirma_prazo.setChecked(configuracao.getTrabalha_com_confirmacao_prazo().equalsIgnoreCase("S") ? true : false);
                    chk_usa_unidade_manut_estoque.setChecked(configuracao.getTrabalha_com_unidade_manut_estoque().equalsIgnoreCase("S") ? true : false);
                    chk_usa_paginacao_dados.setChecked(configuracao.getTrabalha_com_paginacao_dados().equalsIgnoreCase("S") ? true : false);
                    chk_cadastra_cliente.setChecked(configuracao.getCadastra_cliente_app().equalsIgnoreCase("S") ? true : false);
                    try {
                        chk_usa_icms_st.setChecked(configuracao.getTrabalha_com_icms_st().equalsIgnoreCase("S") ? true : false);
                    } catch (NullPointerException e) {
                    }
                    try {
                        chk_usa_desc_auto_app.setChecked(configuracao.getTrabalha_com_desc_auto_app().equalsIgnoreCase("S") ? true : false);
                    } catch (NullPointerException e) {
                    }
                    try {
                        chk_usa_sinc_auto_app.setChecked(configuracao.getSincronia_automatica_app().equalsIgnoreCase("S") ? true : false);
                    } catch (NullPointerException e) {
                    }
                    try {
                        chk_usa_verba_pedido.setChecked(configuracao.getTrabalha_com_verba_pedido().equalsIgnoreCase("S") ? true : false);
                    } catch (NullPointerException e) {
                    }
                    try {
                        swt_marca_produto_comprado.setChecked(configuracao.getMarca_produto_ja_comprado().equalsIgnoreCase("S") ? true : false);
                    } catch (NullPointerException e) {
                        swt_marca_produto_comprado.setChecked(false);
                    }
                    swt_pesquisa_por_grupo.setChecked(configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("G") ? true : false);
                    swt_pesquisa_por_sub_grupo.setChecked(configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("S") ? true : false);
                    swt_pesquisa_por_linha.setChecked(configuracao.getPesquisa_produto_por_linha_grupo().equalsIgnoreCase("L") ? true : false);
                    try {
                        swt_pesquisa_todos_pedidos.setChecked(configuracao.getLista_todos_pedidos().equalsIgnoreCase("S") ? true : false);
                    } catch ( NullPointerException e) {
                        swt_pesquisa_todos_pedidos.setChecked(false);
                    } catch ( Exception e) {
                        swt_pesquisa_todos_pedidos.setChecked(false);
                    }

                    swt_gira_tela_cliente.setChecked(configuracao.getGira_tela_cliente().equalsIgnoreCase("S") ? true : false);
                    swt_gira_tela_produto.setChecked(configuracao.getGira_tela_produto().equalsIgnoreCase("S") ? true : false);
                    swt_gira_tela_cobranca.setChecked(configuracao.getGira_tela_cobranca().equalsIgnoreCase("S") ? true : false);
                    swt_gira_tela_pedido.setChecked(configuracao.getGira_tela_pedido().equalsIgnoreCase("S") ? true : false);
                    swt_gira_tela_ped_pendente.setChecked(configuracao.getGira_tela_ped_pendente().equalsIgnoreCase("S") ? true : false);
                    swt_gira_tela_ped_enviado.setChecked(configuracao.getGira_tela_ped_enviado().equalsIgnoreCase("S") ? true : false);


                    edt_time_conn.setText(configuracao.getTime_conn());
                    edt_time_read.setText(configuracao.getTime_read());
                    edt_time_write.setText(configuracao.getTime_write());

                    if( configuracao.getOrdem_lista_cliente() == null || configuracao.getOrdem_lista_cliente().isEmpty()){
                        ordem_lista_cliente = "N";
                        txvOrdemCliente.setText("Ordem padrão(Nome/Razao Social)");
                    } else {
                        ordem_lista_cliente = configuracao.getOrdem_lista_cliente();
                        if(ordem_lista_cliente.equalsIgnoreCase("N")) {
                            txvOrdemCliente.setText("Ordem padrão(Nome/Razao Social)");
                        } else if(ordem_lista_cliente.equalsIgnoreCase("D")) {
                            txvOrdemCliente.setText("Ordem padrão(Devedor/Nome)");
                        } else{
                            txvOrdemCliente.setText("Ordem padrão(Não definido!)");
                        }
                    }
                    if( configuracao.getAtual_lista_ped_exportado() == null || configuracao.getAtual_lista_ped_exportado().isEmpty()){
                        atual_lista_ped_exportado = "N";
                    } else {
                        atual_lista_ped_exportado = configuracao.getAtual_lista_ped_exportado();
                    }
                    if( configuracao.getConfirma_sincronia_dados() == null || configuracao.getConfirma_sincronia_dados().isEmpty()){
                        confirma_sincronia_automatizada = "N";
                    } else {
                        confirma_sincronia_automatizada = configuracao.getConfirma_sincronia_dados();
                    }
                    chk_atualiza_lista_exportado.setChecked(atual_lista_ped_exportado.equalsIgnoreCase("S") ? true : false);
                    chk_confima_sincronia.setChecked(confirma_sincronia_automatizada.equalsIgnoreCase("S") ? true : false);
                    try {
                        edt_url_ip_2.setText(configuracao.getUrl_ip_2().toLowerCase());
                        edt_url_ip_2_descricao.setText(configuracao.getUrl_ip_desc_2().toUpperCase());
                        edt_url_porta_2.setText(Integer.toString(configuracao.getPorta_2()));
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }

                } catch ( NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch ( compoundButton.getId()) {
            case R.id.swtPesquisaGrupo:
                if (b) {
                    swt_pesquisa_por_linha.setChecked(!b);
                    swt_pesquisa_por_sub_grupo.setChecked(!b);
                }
                break;
            case R.id.swtPesquisaSubGrupo:
                if ( b) {
                    swt_pesquisa_por_linha.setChecked(!b);
                    swt_pesquisa_por_grupo.setChecked(!b);
                }
                break;
            case  R.id.swtPesquisaLinha:
                if ( b) {
                    swt_pesquisa_por_grupo.setChecked(!b);
                    swt_pesquisa_por_sub_grupo.setChecked(!b);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
