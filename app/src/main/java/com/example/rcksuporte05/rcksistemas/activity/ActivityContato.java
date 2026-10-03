package com.example.rcksuporte05.rcksistemas.activity;

import static com.example.rcksuporte05.rcksistemas.BO.ActivitySincroniaBO.getActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityContato extends AppCompatActivity {

    @BindView(R.id.txtCategoria)
    TextView txtCategoria;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imFisicaJuridica)
    ImageView imFisicaJuridica;
    @BindView(R.id.txtRazaoSocial)
    TextView txtRazaoSocial;
    @BindView(R.id.txtNomeFantasia)
    TextView txtNomeFantasia;

    @BindView(R.id.txtCodigoCliente)
    TextView txtCodigoCliente;
    @BindView(R.id.txtTelefone)
    TextView txtTelefone;

    @BindView(R.id.txtCpfCnpj)
    TextView txtCpfCnpj;


    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtEndereco)
    TextView txtEndereco;
    @BindView(R.id.lyDetalhes)
    RelativeLayout lyDetalhes;
    @BindView(R.id.lyChamada)
    RelativeLayout lyChamada;
    @BindView(R.id.lyEmail)
    RelativeLayout lyEmail;
    @BindView(R.id.lyGps)
    RelativeLayout lyGps;
    @BindView(R.id.lyNomeCliente)
    RelativeLayout lyNomeCliente;
    @BindView(R.id.lyFinanceiro)
    RelativeLayout lyFinanceiro;

    @BindView(R.id.txtLimiteCredito)
    TextView txtLimiteCredito;
    @BindView(R.id.txtLimiteUtilizado)
    TextView txtLimiteUtilizado;
    @BindView(R.id.txtLimiteDisponivel)
    TextView txtLimiteDisponivel;
    @BindView(R.id.txtPendenciaFinanceira)
    TextView txtPendenciaFinanceira;
    @BindView(R.id.txtClientePesquisaFinanceiro)
    TextView txtClientePesquisaFinanceiro;
    @BindView(R.id.txtPedidoPendente)
    TextView txtPedidoPendente;
    @BindView(R.id.txtPedidoAnalise)
    TextView txtPedidoAnalise;



    @BindView(R.id.edtDataCadastro)
    TextView edtDataCadastro;


    @BindView(R.id.txtPromocaoCliente)
    TextView txtPromocaoCliente;
    @BindView(R.id.txtNomeVendedor)
    TextView txtNomeVendedor;

    @BindView(R.id.txtobservacoesFaturamento)
    TextView txtobservacoesFaturamento;

    @BindView(R.id.txtobservacoesFinanceiro)
    TextView txtobservacoesFinanceiro;


    private Cliente cliente;
    private CadastroFinanceiroResumo cadastroFinanceiroResumo;
    Utilitaria util = new Utilitaria();
    private DBHelper db = new DBHelper(this);
    CategoriaDAO categoriaDAO = new CategoriaDAO(db);
    ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private Configuracao configuracao = new Configuracao();
    private Context context;
    PackageInfo pInfo = null;
    @OnClick(R.id.lyNomeVendedor)
    public void showActivityContatoVendedor() {
        startActivity(new Intent(ActivityContato.this, ActivityContatoVendedor.class));
    }

    @OnClick(R.id.novoPedido)
    public void novoPedido() {
       escolherEmpresaPedido(0);


    }
    private void escolherEmpresaPedido(final int position) {
        List<EmpresaParametro> listaEmpresas = new EmpresaParametroDAO(db).getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        if (listaEmpresas != null && listaEmpresas.size() > 1) {
            String[] nomesEmpresas = new String[listaEmpresas.size()];
            for (int i = 0; i < listaEmpresas.size(); i++) {
                nomesEmpresas[i] = listaEmpresas.get(i).getRazao_social() != null ? listaEmpresas.get(i).getRazao_social() : listaEmpresas.get(i).getRazao_social();
            }

            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Selecione a Empresa")
                    .setItems(nomesEmpresas, (dialog, which) -> {
                        showActivityPedido(position, String.valueOf(listaEmpresas.get(which).getId_empresa()));
                    })
                    .setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            showActivityPedido(position, "");
        }
    }
    private void showActivityPedido(int position, String idEmpresa) {
        Cliente cliente = ClienteHelper.getCliente();
        //ClienteHelper.setCliente(cliente);
        try {

            if (cliente.getAtivo().equalsIgnoreCase("N")) {
                Toast.makeText(this, "Este cliente esta inativado/bloqueado!", Toast.LENGTH_SHORT).show();
                return;
            } else if (cliente.getIdCategoria() <= 0) {
                Utilitaria util = new Utilitaria();
                util.showFeedbackDialog("Este cliente não tem uma categoria definida", true, false, R.drawable.ic_alerta_mensagem_color_primary, ActivityContato.this);
                return;
            }

            // NOVO: Verificação de Débito com Informativo em Destaque (Ação 3 - Pelo Hub de Opções)
            if (verificarDebitoCliente(cliente, () -> continuarParaPedido(idEmpresa))) {
                return;
            }



        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        continuarParaPedido(idEmpresa);
    }
    private void continuarParaPedido(String idEmpresa) {
        /*
        ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
        Pedido2 pedido2 = new Pedido2();
        activityPedidoMain.pegaCliente(ClienteHelper.getCliente());
        pedido2.pegaCliente(ClienteHelper.getCliente());
        startActivity(new Intent(ActivityContato.this, ActivityPedidoMain.class));

         */
        new PedidoHelper().limparDados();

        // 2. Garante que o cliente selecionado esteja ativo no Helper
        Cliente cliente = ClienteHelper.getCliente();
        if (cliente != null) {
            ClienteHelper.setCliente(cliente);
        }

        // 3. Abre a tela principal do pedido limpa
        Intent intent = new Intent(ActivityContato.this, ActivityPedidoMain.class);
        intent.putExtra("id_empresa", idEmpresa);
        startActivity(intent);
    }

    /**
     * NOVO: Método centralizado para verificar o débito do cliente e exibir o alerta específico.
     */
    private boolean verificarDebitoCliente(Cliente cliente, Runnable onConfirm) {
        float vencido = 0.00f;
        float vencendo = 0.00f;
        try {
            vencido = Float.parseFloat(cliente.getFinanceiro_vencido() == null ? "0.00" : cliente.getFinanceiro_vencido());
            vencendo = Float.parseFloat(cliente.getFinanceiro_vencendo() == null ? "0.00" : cliente.getFinanceiro_vencendo());
        } catch (Exception ignored) {
        }

        // Se o cliente tem qualquer pendência (vencida ou a vencer), mostra o diálogo adaptado
        if (vencido > 0 || vencendo > 0) {
            DialogHelper.showDebtorAlertaDialog(context, vencido, vencendo,
                    "O cliente possui pendências financeiras registradas. Deseja prosseguir com a abertura do novo pedido mesmo assim?", onConfirm);
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnNovoPedido)
    public void btnNovoPedido() {
        novoPedido();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.novoPedido);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        context = this;
        DBHelper db = new DBHelper(this);
        getConfiguracao();
        try {
            if (configuracao.getGira_tela_cliente().isEmpty() || configuracao.getGira_tela_cliente().equalsIgnoreCase("N"))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch ( NullPointerException e) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        showDados();
        lyDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //visualiza os dados
                startActivity(new Intent(ActivityContato.this, CadastroClienteMain.class).putExtra("vizualizacao", 1));
            }
        });

        lyChamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtTelefone.getText().toString().replaceAll("[^0-9]", "").trim().isEmpty()) {
                    if (txtTelefone.getText().toString().replaceAll("[^0-9]", "").length() >= 8 && txtTelefone.getText().toString().replaceAll("[^0-9]", "").length() <= 11) {
                        showActionDialog(v.getId(), "Deseja ligar para o numero " + txtTelefone.getText().toString() + " ?", formataTelefone(txtTelefone.getText().toString()));
                        return;
                    }
                }
                util.showFeedbackDialog("Este numero de telefone não é válido!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityContato.this);

            }
        });

        lyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEmail.getText().toString().equals("Nenhum email informado!"))
                    util.showFeedbackDialog("Nenhum e-mail informado!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityContato.this);
                else
                    showActionDialog(v.getId(), "Deseja enviar um email a " + cliente.getNome_cadastro() + "?", null);
            }
        });

        lyGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEndereco.getText().toString().equals("Nenhum endereço informado!"))
                    util.showFeedbackDialog("Nenhum endereço informado!!", true, false, R.drawable.ic_alerta_mensagem_color_primary , ActivityContato.this);
                else
                    showActionDialog(v.getId(), "Deseja navegar para a localização de " + cliente.getNome_cadastro() + " no GPS?", null);
            }
        });

        lyNomeCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityContato.this, CadastroClienteMain.class).putExtra("vizualizacao", 1));
            }
        });

        lyFinanceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                HistoricoFinanceiroHelper.setCliente(ClienteHelper.getCliente());
                String idCliente = String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor());
                try {
                    System.gc();
                    if ( configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("V")) {
                        startActivity(
                                new Intent(ActivityContato.this, ActivityFinanceiroCobranca.class)
                                        .putExtra("idCliente", idCliente)
                                        .putExtra("debitoCredito", "C")
                                        .putExtra("tipoRelatorio", "OFF")  //relatorio off line
                        );
                    } else {
                        startActivity(
                                new Intent(ActivityContato.this, ActivityHistoricoFinanceiro.class));
                    }
               } catch (CursorIndexOutOfBoundsException e) {
                    Toast.makeText(ActivityContato.this, "Financeiro não encontrado, por favor faça a sincronia e tente novamente!", Toast.LENGTH_LONG).show();
                }

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void showDados() {
        try {
            cliente = ClienteHelper.getCliente();
            try {
                txtCategoria.setText(categoriaDAO.getCategoria(cliente.getIdCategoria()).getNomeCategoria());
                if ( txtCategoria.getText().toString().isEmpty())
                    txtCategoria.setText("Não informado");
            } catch (NullPointerException e) {
                txtCategoria.setText("Não definida!");
            }
            toolbar.setTitle("Contato");
            txtRazaoSocial.setText(cliente.getNome_cadastro());

            txtNomeFantasia.setText(cliente.getNome_fantasia());


            try {
                txtNomeVendedor.setText(db.consultaDados("SELECT NOME_CADASTRO FROM TBL_CADASTRO WHERE F_ID_VENDEDOR = " + cliente.getId_vendedor(), "NOME_CADASTRO"));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
                txtNomeVendedor.setText(String.valueOf(cliente.getId_vendedor()));
            }
            txtCodigoCliente.setText(String.valueOf(cliente.getId_cadastro_servidor()));
            try {
                txtobservacoesFaturamento.setText(cliente.getObservacoes_faturamento());
            } catch ( NullPointerException e) {
                txtobservacoesFaturamento.setText("");
            }

            try {
                txtobservacoesFinanceiro.setText(cliente.getObservacoes_financeiro());
            } catch ( NullPointerException e) {
                txtobservacoesFinanceiro.setText("");
            }



            if (cliente.getPessoa_f_j() != null)
                switch (cliente.getPessoa_f_j()) {
                    case "F":
                        imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_fisica);
                        break;
                    case "J":
                        imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_juridica);
                        break;
                    default:
                        imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);
                        break;
                }
            else
                imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);

            try {
                if (cliente.getCpf_cnpj() == null || cliente.getCpf_cnpj().isEmpty())
                    txtCpfCnpj.setText("--");
                else {
                    if (cliente.getPessoa_f_j().equalsIgnoreCase("F")) {
                        txtCpfCnpj.setText(MascaraUtil.mascaraCPF(cliente.getCpf_cnpj()));
                    } else if (cliente.getPessoa_f_j().equalsIgnoreCase("J")) {
                        txtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(cliente.getCpf_cnpj()));
                    } else {
                        txtCpfCnpj.setText("--");
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (cliente.getTelefone_principal() != null && !cliente.getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11)
                txtTelefone.setText(formataTelefone(cliente.getTelefone_principal()));
            else if (cliente.getTelefone_dois() != null && !cliente.getTelefone_dois().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() <= 11)
                txtTelefone.setText(formataTelefone(cliente.getTelefone_dois()));
            else if (cliente.getTelefone_tres() != null && !cliente.getTelefone_tres().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() <= 11)
                txtTelefone.setText(formataTelefone(cliente.getTelefone_tres()));
            else
                txtTelefone.setText("Não informado!");

            if (cliente.getEmail_principal() != null && !cliente.getEmail_principal().trim().equals(""))
                txtEmail.setText(cliente.getEmail_principal());
            else if (cliente.getEmail_financeiro() != null && !cliente.getEmail_financeiro().trim().equals(""))
                txtEmail.setText(cliente.getEmail_financeiro());
            else
                txtEmail.setText("Nenhum email informado!");

            String endereco;
            if (cliente.getEndereco() != null && !cliente.getEndereco().trim().equals("")) {
                endereco = cliente.getEndereco().replace(",", "") + ", ";
                if (cliente.getEndereco_numero() != null && !cliente.getEndereco_numero().isEmpty())
                    endereco += cliente.getEndereco_numero();
                if ( cliente.getEndereco_complemento() != null && !cliente.getEndereco_complemento().isEmpty())
                    endereco += " - " + cliente.getEndereco_complemento();
                if ( cliente.getEndereco_bairro() != null && !cliente.getEndereco_bairro().isEmpty())
                    endereco += " - " + cliente.getEndereco_bairro();

                try {
                    if (cliente.getEndereco_cep() != null && !cliente.getEndereco_cep().isEmpty()) {
                        endereco +=  MascaraUtil.formataCep(cliente.getEndereco_cep() + " ");
                    }
                } catch ( NullPointerException e) {

                }

                if ( cliente.getNome_municipio() != null && !cliente.getNome_municipio().isEmpty())
                    endereco += " - " + cliente.getNome_municipio();
                if (cliente.getEndereco_uf() != null && !cliente.getEndereco_uf().isEmpty())
                    endereco += " - " + cliente.getEndereco_uf();

            }else if (cliente.getCob_endereco() != null && !cliente.getCob_endereco().trim().equals(""))
                endereco = cliente.getCob_endereco().replace(",", "") + ", " + cliente.getCob_endereco_numero() + " - CEP" + cliente.getCob_endereco_cep();
            else
                endereco = "Nenhum endereço informado!";

            txtEndereco.setText(endereco.replace(", null", ""));

            if (cliente.getLimite_credito() != null && !cliente.getLimite_credito().trim().isEmpty()) {
                txtLimiteCredito.setText("(+) " + MascaraUtil.mascaraReal(cliente.getLimite_credito()));
                txtLimiteDisponivel.setText( "(=) " + MascaraUtil.mascaraReal(Float.parseFloat(cliente.getLimite_credito()) - (Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo())) ));
                txtLimiteUtilizado.setText("(-) " + MascaraUtil.mascaraReal( Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo())));
                if ( Float.parseFloat(cliente.getLimite_credito()) - (Float.parseFloat(cliente.getFinanceiro_vencido()) + Float.parseFloat(cliente.getFinanceiro_vencendo())) < 0.00f)
                        txtLimiteDisponivel.setTextColor(Color.RED);
            }

            try {
                String dataCadastro = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(cliente.getUsuario_data()));
                edtDataCadastro.setText(dataCadastro);
            } catch (Exception e) {
                e.printStackTrace();
            }
            txtPedidoAnalise.setText("(-) " + MascaraUtil.mascaraReal(cliente.getPedido_analise()));
            
            float vencido = 0;
            float vencendo = 0;
            try {
                vencido = Float.parseFloat(cliente.getFinanceiro_vencido());
                vencendo = Float.parseFloat(cliente.getFinanceiro_vencendo());
                txtPendenciaFinanceira.setText("(-) " + MascaraUtil.mascaraReal(vencido + vencendo));
            } catch (NullPointerException | NumberFormatException e) {
                txtPendenciaFinanceira.setText("(-) R$ 0,00" );
            }
            
            if ( (vencido + vencendo) > 0.0f) {
                txtPendenciaFinanceira.setTextColor(Color.RED);
                if (txtClientePesquisaFinanceiro != null) {
                    if (vencido > 0) {
                        txtClientePesquisaFinanceiro.setBackgroundResource(R.drawable.bg_badge_status_vermelho_forte);
                        txtClientePesquisaFinanceiro.setTextColor(Color.WHITE);
                    } else {
                        txtClientePesquisaFinanceiro.setBackgroundResource(R.drawable.bg_badge_status_amarelo);
                        txtClientePesquisaFinanceiro.setTextColor(Color.parseColor("#B91C1C"));
                    }
                }
            } else {
                if (txtClientePesquisaFinanceiro != null) {
                    txtClientePesquisaFinanceiro.setBackgroundResource(R.drawable.bg_badge_status_amarelo);
                    txtClientePesquisaFinanceiro.setTextColor(Color.parseColor("#B91C1C"));
                }
            }
            try {
                if (Integer.parseInt(cliente.getId_promocao()) > 0) {
                    PromocaoDAO promocaoDAO = new PromocaoDAO(db);
                        Promocao promocao = promocaoDAO.getPromocao( cliente.getId_promocao());
                        if ( promocao != null)
                            txtPromocaoCliente.setText("(" +MascaraUtil.duasCasaDecimal(promocao.getDescontoPerc()) + "%) " + promocao.getNomePromocao().toUpperCase());
                        else
                            txtPromocaoCliente.setText("");
                }else{
                    txtPromocaoCliente.setText("");
                }
            } catch ( NullPointerException|NumberFormatException e) {
            } catch ( Exception e ) {
            }
            sincronizaFinanceiroAPI(cliente.getId_cadastro_servidor());
            sincronizaPendenciaAPI(cliente.getId_cadastro_servidor());
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(ActivityContato.this, "Erro ao carregar Cliente", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public String formataTelefone(String telefone) {
        telefone = telefone.trim().replaceAll("[^0-9]", "");
        switch (telefone.length()) {
            case 10:
                return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
            case 11:
                return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
            case 9:
                return telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
            case 8:
                return telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
            default:
                return telefone;
        }
    }

    public void fazerChamada(final String telefone, final String nome) {
        try {
            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty())
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11) {
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(ActivityContato.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContato.this);
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);

                    TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                    textView.setText("Deseja ligar para " + nome + " usando o número " + telefone + " ?");

                    Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
                    btnNao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                        }
                    });
                    Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
                    btnSim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent intent = new Intent(Intent.ACTION_DIAL);
                            if (telefone.replaceAll("[^0-9]", "").length() == 10)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else if (telefone.replaceAll("[^0-9]", "").length() == 11)
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            else
                                intent.setData(Uri.parse("tel:" + telefone));
                            alertDialog.dismiss();
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                } else
                    util.showMsgAlerta("Este numero de Telefone não é valido!", ActivityContato.this);
            else
                util.showMsgAlerta("Nenhum numero de telefone informado!", ActivityContato.this);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }



    @Override
    protected void onDestroy() {
        //ClienteHelper.clear();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        ClienteHelper.clear();
        super.onBackPressed();
    }

    public void sincronizaFinanceiroAPI(int idCadastroServidor) {
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versaoApp = pInfo.versionName.substring(0, 5);
        if ( !usuarioIsLogado() )
            return;
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {

            Call<CadastroFinanceiroResumo> call = getRotasAPI(1, idCadastroServidor, configuracao, versaoApp);
            call.enqueue(new Callback<CadastroFinanceiroResumo>() {
                @Override
                public void onResponse(Call<CadastroFinanceiroResumo> call, Response<CadastroFinanceiroResumo> response) {
                    CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
                    cadastroFinanceiroResumoDAO.addUpdate(response.body());
                    HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(response.body());
                    atualizaDados();
                }

                @Override
                public void onFailure(Call<CadastroFinanceiroResumo> call, Throwable t)
                {
                    t.getMessage();
                    //Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(context, "Parametros para conexão não cadastrados!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean usuarioIsLogado() {
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(context));
        try {
            if (UsuarioHelper.getUsuario() == null) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private Call<CadastroFinanceiroResumo> getRotasAPI(int idRota, int idCadastroServidor, Configuracao configuracao, String versaoApp) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas.getFinanceiroResumo(idCadastroServidor, cabecalho);

                else
                    return  apiRotas.sincronizaFinanceiro(idCadastroServidor, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + versaoApp + "/ws/";
                    Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();

                    Api.context = context;
                    apiRotas = Api.buildRetrofit(true);
                    if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                        return apiRotas.getFinanceiroResumo(idCadastroServidor, cabecalho);

                    else
                        return  apiRotas.sincronizaFinanceiro(idCadastroServidor, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(ActivityContato.this, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(ActivityContato.this, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if(idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas2.getFinanceiroResumo(idCadastroServidor, cabecalho);

                else
                    return  apiRotas2.sincronizaFinanceiro(idCadastroServidor, cabecalho);
                //return apiRotas2.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp.substring(0, 5) + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();

                    apiRotas2 = Api.buildRetrofit2(true);
                    if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                        return apiRotas2.getFinanceiroResumo(idCadastroServidor, cabecalho);

                    else
                        return  apiRotas2.sincronizaFinanceiro(idCadastroServidor, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(ActivityContato.this, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(ActivityContato.this, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n"+ e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }



    public void atualizaDados() {
        if (HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito() != null && HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito() > 0)
            txtLimiteCredito.setText("(+)" + MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito()));
        else
            txtLimiteCredito.setText("(+)" + MascaraUtil.mascaraReal(0.f));
        String sql = "SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO " +
                "WHERE PEDIDO_ENVIADO = 'N' AND ID_CONDICAO_PAGAMENTO <> 1 " +
                "AND ID_WEB_PEDIDO <> " + PedidoHelper.getIdPedido() + " " +
                "AND ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro_servidor() + ";";

        Float limiteUltilizado = db.soma(sql) + cadastroFinanceiroResumo.getLimiteUtilizado();
        Float saldoRestante = cadastroFinanceiroResumo.getLimiteCredito() - limiteUltilizado - getIntent().getFloatExtra("valorPedido", 0) - cadastroFinanceiroResumo.getFinanceiroVencido();
        txtLimiteDisponivel.setText("(=)" + MascaraUtil.mascaraReal(saldoRestante));
        txtLimiteUtilizado.setText( limiteUltilizado != null && limiteUltilizado > 0 ? "(-)" + MascaraUtil.mascaraReal(limiteUltilizado) : "(-)" + MascaraUtil.mascaraReal(0.f));
        txtLimiteDisponivel.setTextColor( saldoRestante < 0 || cadastroFinanceiroResumo.getFinanceiroVencido() > 0 ? Color.RED: Color.parseColor("#0277BD") );
    }

    public void sincronizaPendenciaAPI(int idCadastroServidor) {
        Float total = 0.0f;
        Float pedidPendente = db.soma("SELECT SUM(VALOR_TOTAL) AS VALOR_TOTAL FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND FINALIZADO = 'S' AND EXCLUIDO = 'N' AND ID_CADASTRO = " + cliente.getId_cadastro_servidor() + ";");// + cadastroFinanceiroResumo.getLimiteUtilizado();
        try {
            if (pedidPendente > 0.0f)
                total += pedidPendente;
            txtPedidoPendente.setText("(-) " + MascaraUtil.mascaraReal(pedidPendente));
        } catch ( NullPointerException|NumberFormatException e) {

        }catch ( Exception e) {

        }
        Rotas apiRotas = Api.buildRetrofit(false);
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<List<CadastroFinanceiroResumo>> call = apiRotas.getFinanceiroPendente(idCadastroServidor, cabecalho);
        Float finalTotal = total;
        call.enqueue(new Callback<List<CadastroFinanceiroResumo>>() {
            @Override
            public void onResponse(Call<List<CadastroFinanceiroResumo>> call, Response<List<CadastroFinanceiroResumo>> response) {
                switch (response.code()) {
                    case 200:
                        try {
                            List<CadastroFinanceiroResumo> lista = response.body();
                            if (lista.size() > 0) {
                                if (cliente.getLimite_credito() != null && !cliente.getLimite_credito().trim().isEmpty()) {
                                    txtLimiteUtilizado.setText("(-) " + MascaraUtil.mascaraReal((lista.get(0).getLimiteUtilizado() )));
                                    txtLimiteDisponivel.setText("(=) " +  MascaraUtil.mascaraReal(Float.parseFloat(cliente.getLimite_credito()) - (lista.get(0).getLimiteUtilizado() /*+ (finalTotal + lista.get(0).getPedidosAnalise())*/ ) ));
                                    //txtPedidoAnalise.setText("(-) " + MascaraUtil.mascaraReal(lista.get(0).getPedidosAnalise()));
                                    if ( ( Float.parseFloat(cliente.getLimite_credito()) - (lista.get(0).getLimiteUtilizado() /*+ (finalTotal + lista.get(0).getPedidosAnalise())*/ ) ) < 0.00f )
                                        txtLimiteDisponivel.setTextColor(Color.RED);
                                }
                                txtPendenciaFinanceira.setText("(-) " + MascaraUtil.mascaraReal(lista.get(0).getFinanceiroPendente()));
                                if ( lista.get(0).getFinanceiroPendente() > 0.0f)
                                    txtPendenciaFinanceira.setTextColor(Color.RED);
                                txtPedidoAnalise.setText("(-) " + MascaraUtil.mascaraReal(lista.get(0).getPedidosAnalise()));
                            }
                        } catch ( NullPointerException | CursorIndexOutOfBoundsException e) {
                        } catch ( Exception e){
                        }
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<CadastroFinanceiroResumo>> call, Throwable t) {
                //progress.dismiss();
                //util.showMsgAlerta("Não foi possivel atualizar o financeiro deste cliente!\n        Verifique sua conexão com a internet/servidor!", ContatoActivity.this);
            }
        });
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }

    /*
    public void showMsgSimNao(final int id , String mensagem) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ActivityContato.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContato.this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if ( id == R.id.lyEmail)
                    startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: " + txtEmail.getText().toString())));
                else if ( id == R.id.lyGps) {
                    //https://maps.google.com/maps?source=s_d&saddr=ATL&daddr=LGA+to:LAX+to:AAP
                    //final String uri = "http://maps.google.com/maps?saddr=My+Location&daddr=My+Location+to:AVENIDA AFONSO PENA, 4909 - BAIRRO SANTA FÉ - CAMPO GRANDE -MS+to:ANTONIO MARIA COELHO, 300 - VILA PLANALTO - CAMPO GRANDE -MS";
                    //final Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    //if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    //     startActivity(intent);
                    //}
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse( uri)).setPackage("com.google.android.apps.maps"));
                    //Testes para tentar montar uma rota com varias paradas
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + txtEndereco.getText().toString())).setPackage("com.google.android.apps.maps"));
                }else if ( id == R.id.novoPedido) {
                    ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                    Pedido2 pedido2 = new Pedido2();
                    activityPedidoMain.pegaCliente(ClienteHelper.getCliente());
                    pedido2.pegaCliente(ClienteHelper.getCliente());
                    startActivity(new Intent(ActivityContato.this, ActivityPedidoMain.class));
                }
            }
        });
        alertDialog.show();
    }
    */
    public void showActionDialog(final int id ,  String mensagem, String telefone) {
        DialogHelper.showActionDialog(
                context,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {

                    }
                    @Override
                    public void onNegative() {
                        if ( id == R.id.lyEmail) {
                            startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto: " + txtEmail.getText().toString())));
                        }else if ( id == R.id.lyGps) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + txtEndereco.getText().toString())).setPackage("com.google.android.apps.maps"));
                        } else if ( id == R.id.lyChamada) {
                            try {
                                final Intent intent = new Intent(Intent.ACTION_DIAL);
                                if (telefone.replaceAll("[^0-9]", "").length() == 10)
                                    intent.setData(Uri.parse("tel:" + "0" + telefone));
                                else if (telefone.replaceAll("[^0-9]", "").length() == 11)
                                    intent.setData(Uri.parse("tel:" + "0" + telefone));
                                else
                                    intent.setData(Uri.parse("tel:" + telefone));
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(context, "Sem suporte para efetuar ligação!", Toast.LENGTH_LONG).show();
                            }
                        }else if ( id == R.id.novoPedido) {
                            ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                            Pedido2 pedido2 = new Pedido2();
                            activityPedidoMain.pegaCliente(ClienteHelper.getCliente());
                            pedido2.pegaCliente(ClienteHelper.getCliente());
                            startActivity(new Intent(ActivityContato.this, ActivityPedidoMain.class));
                        }
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }
}
