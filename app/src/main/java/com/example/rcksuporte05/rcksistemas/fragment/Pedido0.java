package com.example.rcksuporte05.rcksistemas.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityClienteDrawer;
import com.example.rcksuporte05.rcksistemas.activity.ActivityContato;
import com.example.rcksuporte05.rcksistemas.activity.ActivityFinanceiroCobranca;
import com.example.rcksuporte05.rcksistemas.activity.ActivityHistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProdutoDrawer;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutoPedido;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.PromocaoRetorno;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Pedido0 extends Fragment  {

    public static List<WebPedidoItens> listaProdutoRemovido = new ArrayList<>();
    private static ListaAdapterProdutoPedido adapter;
    private DBHelper db = new DBHelper(PedidoHelper.getActivityPedidoMain());
    //private FloatingActionButton btnInserirProduto/*, btnAvancar*/;
    //private TextView textView15;
    private Bundle bundle;
    private int idCliente;
    private CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO;
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    private PromocaoRetorno promocaoRetorno;
    private Context context;
    private Configuracao configuracao = new Configuracao();
    private boolean exportado = false;

    private TextView txtRazaoSocial;
    private TextView txtNomeFantasia;
    private TextView txtTelefone, descricaoTelefone;

    private TextView txtEndereco;
    private TextView txtLimiteCredito, txtLimiteUtilizado, txtLimiteDisponivel, descricaoLimiteCredito, descricaoLimiteUsado, descricaoLimiteDisponivel;

    private TextView txtPendenciaFinanceira, txtPromocaoCliente, pendenciaFinanceira, promocaoCliente, txtClientePesquisaFinanceiro ;

    private TextView txtobservacoesFaturamento, txtobservacoesFinanceiro, observacoesFaturamento, observacoesFinanceiro ;
    private TextView txtCpfCnpj, cpfCnpj;

    private TextView txtTabelaPrecoCliente;
    private TextView lblTabelaPreco;
    private View dividerTabelaPreco;
    private View lyTabelaPrecoCliente;

    private TextView txtEmpresaPedido;
    private TextView btnAlterarEmpresa;

    private int tExtraFonte = 0;
    @BindView(R.id.btnCliente)
    Button btnCliente;


    RelativeLayout lyFinanceiro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido0_2025, container, false);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        final PedidoHelper pedidoHelper = new PedidoHelper(this);
        bundle = getArguments();

        btnCliente = view.findViewById(R.id.btnCliente);
        lyFinanceiro = view.findViewById(R.id.lyFinanceiro);

        txtRazaoSocial  = view.findViewById(R.id.txtRazaoSocial);
        txtNomeFantasia = view.findViewById(R.id.txtNomeFantasia);
        txtTelefone = view.findViewById(R.id.txtTelefone);
        descricaoTelefone = view.findViewById(R.id.descricaoTelefone);
        txtEndereco = view.findViewById(R.id.txtEndereco);

        txtLimiteCredito   = view.findViewById(R.id.txtLimiteCredito);
        txtLimiteUtilizado = view.findViewById(R.id.txtLimiteUtilizado);
        txtLimiteDisponivel = view.findViewById(R.id.txtLimiteDisponivel);
        descricaoLimiteCredito = view.findViewById(R.id.descricaoLimiteCredito);
        descricaoLimiteUsado = view.findViewById(R.id.descricaoLimiteUsado);
        descricaoLimiteDisponivel = view.findViewById(R.id.descricaoLimiteDisponivel);

        txtPendenciaFinanceira= view.findViewById(R.id.txtPendenciaFinanceira);
        pendenciaFinanceira= view.findViewById(R.id.pendenciaFinanceira);
        txtClientePesquisaFinanceiro = view.findViewById(R.id.txtClientePesquisaFinanceiro);
        txtPromocaoCliente= view.findViewById(R.id.txtPromocaoCliente);
        promocaoCliente= view.findViewById(R.id.promocaoCliente);
        txtobservacoesFaturamento= view.findViewById(R.id.txtobservacoesFaturamento);
        txtobservacoesFinanceiro= view.findViewById(R.id.txtobservacoesFinanceiro);
        observacoesFaturamento= view.findViewById(R.id.observacoesFaturamento);
        observacoesFinanceiro= view.findViewById(R.id.observacoesFinanceiro);

        txtCpfCnpj = view.findViewById(R.id.txtCpfCnpj);//txtCpfCnpj
        cpfCnpj  = view.findViewById(R.id.cpfCnpj);//txtCpfCnpj
        
        txtTabelaPrecoCliente = view.findViewById(R.id.txtTabelaPrecoCliente);
        lblTabelaPreco = view.findViewById(R.id.lblTabelaPreco);
        dividerTabelaPreco = view.findViewById(R.id.dividerTabelaPreco);
        lyTabelaPrecoCliente = view.findViewById(R.id.lyTabelaPrecoCliente);

        txtEmpresaPedido = view.findViewById(R.id.txtEmpresaPedido);
        btnAlterarEmpresa = view.findViewById(R.id.btnAlterarEmpresa);

        if (bundle.getInt("vizualizacao") == 1) {
            btnCliente.setEnabled(false);
            btnAlterarEmpresa.setEnabled(false);
            btnAlterarEmpresa.setVisibility(View.GONE);
        } else {
            verificarMultiplasEmpresas();
        }

        btnAlterarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherEmpresaPedido();
            }
        });

        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCliente();
            }
        });

        lyFinanceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                HistoricoFinanceiroHelper.setCliente(ClienteHelper.getCliente());
                String idCliente = String.valueOf(ClienteHelper.getCliente().getId_cadastro_servidor());
                try {
                    //System.gc();
                    if ( configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("V")) {
                        startActivity(
                                new Intent(getContext(), ActivityFinanceiroCobranca.class)
                                        .putExtra("idCliente", idCliente)
                                        .putExtra("debitoCredito", "C")
                                        .putExtra("tipoRelatorio", "OFF")  //relatorio off line
                        );
                    } else {
                        startActivity(
                                new Intent(getContext(), ActivityHistoricoFinanceiro.class));
                    }
                } catch (CursorIndexOutOfBoundsException e) {
                    Toast.makeText(getContext(), "Financeiro não encontrado, por favor faça a sincronia e tente novamente!", Toast.LENGTH_LONG).show();
                }

            }
        });

        if (bundle.getInt("vizualizacao") == 1) {
            exportado = true;
            btnCliente.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparente50)));
        }

        if (PedidoHelper.getIdPedido() > 0) {
            //final int pedido = PedidoHelper.getIdPedido();
            if (bundle.getInt("vizualizacao") == 1) {
                //btnInserirProduto.setVisibility(View.INVISIBLE);
                Configuracao configuracao = new Configuracao();
                ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);

                configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                try {
                    PedidoHelper.getWebPedido().setTrabalha_com_campanha(configuracao.getTrabalha_com_campanhas());
                } catch (NullPointerException e) {

                }
            }

        }

        //System.gc();
        try {
            tExtraFonte = db.pegaTamanhoExtraFonte();
        } catch (NullPointerException ignored) {
        }
        if ( tExtraFonte > 0) {
            txtRazaoSocial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtNomeFantasia.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            descricaoTelefone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtTelefone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            cpfCnpj.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtCpfCnpj.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);

            descricaoLimiteCredito.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtLimiteCredito.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            descricaoLimiteUsado.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtLimiteUtilizado.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            descricaoLimiteDisponivel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtLimiteDisponivel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);

            pendenciaFinanceira.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtPendenciaFinanceira.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            promocaoCliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtPromocaoCliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);

            if (lblTabelaPreco != null) {
                lblTabelaPreco.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            }
            if (txtTabelaPrecoCliente != null) {
                txtTabelaPrecoCliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            }

            observacoesFaturamento.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtobservacoesFaturamento.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            observacoesFinanceiro.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
            txtobservacoesFinanceiro.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 + tExtraFonte);
        }

        return (view);
    }



  /*  public List<WebPedidoItens> salvaPedidos() {
        for (WebPedidoItens webPedidoItens : listaProdutoPedido) {
            if ((PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("N") || webPedidoItens.getProduto_materia_prima().equals("S") || webPedidoItens.getProduto_tercerizacao().equals("S"))
                    && Float.parseFloat(webPedidoItens.getValor_desconto_real()) > 0.f) {
                if ( ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S") && ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")){
                    webPedidoItens.setDescontoIndevido(false);
                } else {
                    webPedidoItens.setDescontoIndevido(true);
                }
            }
            if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("N") && PedidoHelper.getCondicoesPagamento().getAceita_desconto().equals("S"))
                webPedidoItens.setDescontoIndevido(false);
        }
        try {
            adapter.notifyDataSetChanged();
            PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
            PedidoHelper.setListaWebPedidoItens(listaProdutoPedido);
        } catch ( NullPointerException e) {
           e.printStackTrace();
        }
        return listaProdutoPedido;
    }*/

    @Override
    public void onResume() {
        try {
            PedidoHelper.calculaValorPedido(PedidoHelper.getListaWebPedidoItens(), PedidoHelper.getActivityPedidoMain());
        } catch (NullPointerException e) {
            PedidoHelper.calculaValorPedido(null, PedidoHelper.getActivityPedidoMain());
        }
        if (ClienteHelper.getCliente() != null)
            verificaFinanceiro(); //Verifica situação financeira atras de pendencia
        try {
            if (PedidoHelper.getWebPedido().getCadastro() != null) {
                txtRazaoSocial.setText(PedidoHelper.getWebPedido().getCadastro().getNome_cadastro().trim());
                txtNomeFantasia.setText(PedidoHelper.getWebPedido().getCadastro().getNome_fantasia().trim());
                //Telefone
                if (PedidoHelper.getWebPedido().getCadastro().getTelefone_principal() != null && !PedidoHelper.getWebPedido().getCadastro().getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty() && PedidoHelper.getWebPedido().getCadastro().getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && PedidoHelper.getWebPedido().getCadastro().getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11)
                    txtTelefone.setText(formataTelefone(PedidoHelper.getWebPedido().getCadastro().getTelefone_principal()));
                else if (PedidoHelper.getWebPedido().getCadastro().getTelefone_dois() != null && !PedidoHelper.getWebPedido().getCadastro().getTelefone_dois().replaceAll("[^0-9]", "").trim().isEmpty() && PedidoHelper.getWebPedido().getCadastro().getTelefone_dois().replaceAll("[^0-9]", "").length() >= 8 && PedidoHelper.getWebPedido().getCadastro().getTelefone_dois().replaceAll("[^0-9]", "").length() <= 11)
                    txtTelefone.setText(formataTelefone(PedidoHelper.getWebPedido().getCadastro().getTelefone_dois()));
                else if (PedidoHelper.getWebPedido().getCadastro().getTelefone_tres() != null && !PedidoHelper.getWebPedido().getCadastro().getTelefone_tres().replaceAll("[^0-9]", "").trim().isEmpty() && PedidoHelper.getWebPedido().getCadastro().getTelefone_tres().replaceAll("[^0-9]", "").length() >= 8 && PedidoHelper.getWebPedido().getCadastro().getTelefone_tres().replaceAll("[^0-9]", "").length() <= 11)
                    txtTelefone.setText(formataTelefone(PedidoHelper.getWebPedido().getCadastro().getTelefone_tres()));
                else
                    txtTelefone.setText("Nenhum telefone informado!");

                try {
                    if (PedidoHelper.getWebPedido().getCadastro().getCpf_cnpj() == null || PedidoHelper.getWebPedido().getCadastro().getCpf_cnpj().isEmpty())
                        txtCpfCnpj.setText("--");
                    else {
                        if (PedidoHelper.getWebPedido().getCadastro().getPessoa_f_j().equalsIgnoreCase("J")) {
                            txtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(PedidoHelper.getWebPedido().getCadastro().getCpf_cnpj()));
                        } else if(PedidoHelper.getWebPedido().getCadastro().getPessoa_f_j().equalsIgnoreCase("F")) {
                            txtCpfCnpj.setText(MascaraUtil.mascaraCPF(PedidoHelper.getWebPedido().getCadastro().getCpf_cnpj()));
                        } else {
                            txtCpfCnpj.setText("--");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Endereço
                String endereco;
                if (PedidoHelper.getWebPedido().getCadastro().getEndereco() != null && !PedidoHelper.getWebPedido().getCadastro().getEndereco().trim().equals("")) {
                    endereco = PedidoHelper.getWebPedido().getCadastro().getEndereco().replace(",", "") + ", ";
                    if (PedidoHelper.getWebPedido().getCadastro().getEndereco_numero() != null && !PedidoHelper.getWebPedido().getCadastro().getEndereco_numero().isEmpty())
                        endereco += PedidoHelper.getWebPedido().getCadastro().getEndereco_numero();
                    if ( PedidoHelper.getWebPedido().getCadastro().getEndereco_complemento() != null && !PedidoHelper.getWebPedido().getCadastro().getEndereco_complemento().isEmpty())
                        endereco += " - " + PedidoHelper.getWebPedido().getCadastro().getEndereco_complemento();
                    if ( PedidoHelper.getWebPedido().getCadastro().getEndereco_bairro() != null && !PedidoHelper.getWebPedido().getCadastro().getEndereco_bairro().isEmpty())
                        endereco += " - " + PedidoHelper.getWebPedido().getCadastro().getEndereco_bairro();

                    try {
                        if (PedidoHelper.getWebPedido().getCadastro().getEndereco_cep() != null && !PedidoHelper.getWebPedido().getCadastro().getEndereco_cep().isEmpty()) {
                            endereco +=  MascaraUtil.formataCep(PedidoHelper.getWebPedido().getCadastro().getEndereco_cep() + " ");
                        }
                    } catch ( NullPointerException e) {

                    }

                    if ( PedidoHelper.getWebPedido().getCadastro().getNome_municipio() != null && !PedidoHelper.getWebPedido().getCadastro().getNome_municipio().isEmpty())
                        endereco += " - " + PedidoHelper.getWebPedido().getCadastro().getNome_municipio();
                    if (PedidoHelper.getWebPedido().getCadastro().getEndereco_uf() != null && !PedidoHelper.getWebPedido().getCadastro().getEndereco_uf().isEmpty())
                        endereco += " - " + PedidoHelper.getWebPedido().getCadastro().getEndereco_uf();

                }else if (PedidoHelper.getWebPedido().getCadastro().getCob_endereco() != null && !PedidoHelper.getWebPedido().getCadastro().getCob_endereco().trim().equals(""))
                    endereco = PedidoHelper.getWebPedido().getCadastro().getCob_endereco().replace(",", "") + ", " + PedidoHelper.getWebPedido().getCadastro().getCob_endereco_numero() + " - CEP" + PedidoHelper.getWebPedido().getCadastro().getCob_endereco_cep();
                else
                    endereco = "Nenhum endereço informado!";

                txtEndereco.setText(endereco.replace(", null", ""));

                //Limites de credito
                if (PedidoHelper.getWebPedido().getCadastro().getLimite_credito() != null && !PedidoHelper.getWebPedido().getCadastro().getLimite_credito().trim().isEmpty()) {
                    txtLimiteCredito.setText("(+) " + MascaraUtil.mascaraReal(PedidoHelper.getWebPedido().getCadastro().getLimite_credito()));
                    txtLimiteDisponivel.setText( "(=) " + MascaraUtil.mascaraReal(Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getLimite_credito()) - (Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencido()) + Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencendo())) ));
                    txtLimiteUtilizado.setText("(-) " + MascaraUtil.mascaraReal( Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencido()) + Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencendo())));
                    if ( Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getLimite_credito()) - (Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencido()) + Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencendo())) < 0.00f)
                        txtLimiteDisponivel.setTextColor(Color.RED);
                }

                //Pendencia financeira
                float vencido = Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencido());
                float vencendo = Float.parseFloat(PedidoHelper.getWebPedido().getCadastro().getFinanceiro_vencendo());
                float totalPendencia = vencido + vencendo;

                txtPendenciaFinanceira.setText("(-) " + MascaraUtil.mascaraReal(totalPendencia));
                
                if (totalPendencia > 0.0f) {
                    txtPendenciaFinanceira.setTextColor(Color.RED);
                    if (txtClientePesquisaFinanceiro != null) {
                        // Se houver valor VENCIDO, fica vermelho. Se for apenas VENCENDO, mantemos amarelo/alerta
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
                    if (Integer.parseInt(PedidoHelper.getWebPedido().getCadastro().getId_promocao()) > 0) {
                        PromocaoDAO promocaoDAO = new PromocaoDAO(db);
                        Promocao promocao = promocaoDAO.getPromocao( PedidoHelper.getWebPedido().getCadastro().getId_promocao());
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

                // NOVO: Lógica para exibir a Tabela de Preço se for diferente de zero
                try {
                    int idTabela = PedidoHelper.getWebPedido().getCadastro().getId_tabela_preco();
                    if (idTabela > 0) {
                        String nomeTabela = db.consultaDados("SELECT NOME_TABELA FROM TBL_TABELA_PRECO_CAB WHERE ID_TABELA = " + idTabela, "NOME_TABELA");
                        if (nomeTabela != null && !nomeTabela.isEmpty()) {
                            txtTabelaPrecoCliente.setText(idTabela + " - " + nomeTabela.toUpperCase());
                        } else {
                            txtTabelaPrecoCliente.setText(String.valueOf(idTabela));
                        }
                        lyTabelaPrecoCliente.setVisibility(View.VISIBLE);
                        dividerTabelaPreco.setVisibility(View.VISIBLE);
                    } else {
                        lyTabelaPrecoCliente.setVisibility(View.GONE);
                        dividerTabelaPreco.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    lyTabelaPrecoCliente.setVisibility(View.GONE);
                    dividerTabelaPreco.setVisibility(View.GONE);
                }

                try {
                    txtobservacoesFaturamento.setText(PedidoHelper.getWebPedido().getCadastro().getObservacoes_faturamento());
                } catch ( NullPointerException e) {
                    txtobservacoesFaturamento.setText("");
                }

                try {
                    txtobservacoesFinanceiro.setText(PedidoHelper.getWebPedido().getCadastro().getObservacoes_financeiro());
                } catch ( NullPointerException e) {
                    txtobservacoesFinanceiro.setText("");
                }
            }

            // Exibe a Razão Social da Empresa do Pedido (Movido para fora do bloco do Cadastro para garantir exibição na visualização)

        } catch (NullPointerException e) {

        }
        try {
            String idEmpresa = PedidoHelper.getWebPedido().getId_empresa();
            if (idEmpresa != null && !idEmpresa.isEmpty()) {
                String razaoSocial = db.consultaDados("SELECT RAZAO_SOCIAL FROM TBL_EMPRESA_PARAMETRO WHERE ID = '" + idEmpresa + "';", "RAZAO_SOCIAL");
                txtEmpresaPedido.setText(razaoSocial != null && !razaoSocial.isEmpty() ? razaoSocial.trim() : "Empresa (" + idEmpresa + ")");
            } else {
                txtEmpresaPedido.setText("Não informado");
            }
        } catch (Exception e) {
            txtEmpresaPedido.setText("Não informado");
        }
        /*if (listaProdutoPedido != null && listaProdutoPedido.size() > 0) {
            if ( (ClienteHelper.getCliente().getId_cadastro() != idCliente && idCliente > 0) && ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {
                //Enviar o cadastro da empresa para verificar os parametros
                try {
                    if (ClienteHelper.getCliente().getUtiliza_tabela_preco().equalsIgnoreCase("S")) {
                        if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("2")) {
                            if (ClienteHelper.getCliente().getIdCategoria() > 0) {
                                ViewGroup viewGroup = PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                                AlertDialog.Builder builder = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCancelable(false);

                                TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                                if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"))
                                    textView.setText("Você alterou o cliente. Deseja alterar o desconto do(s) produto(s) para a categoria do cliente " + ClienteHelper.getCliente().getNome_cadastro() + "?\nTodas as campanhas lançadas serão removidas, caso não sejam aplicaveis ao cliente alterado!");
                                else
                                    textView.setText("Você alterou o cliente. Deseja alterar o desconto do(s) produto(s) para a categoria do cliente " + ClienteHelper.getCliente().getNome_cadastro() + "?");
                                Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
                                btnNao.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"))
                                        verificaDescontoTabelaCampanha(true, true);
                                        //else
                                        //    verificaDescontoTabelaCampanha(true, false);
                                        alertDialog.dismiss();
                                    }
                                });
                                Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
                                btnSim.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"))
                                            verificaDescontoTabelaCampanha(true, true);
                                        else
                                            verificaDescontoTabelaCampanha(true, false);
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            } else {
                                showMsgAlerta("O cliente " + ClienteHelper.getCliente().getNome_cadastro() + " não tem uma categoria definida!");
                            }


                        } else if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("4")) {
                            if (ClienteHelper.getCliente().getIdCategoria() > 0) {
                                ViewGroup viewGroup =  PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from( PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                                AlertDialog.Builder builder = new AlertDialog.Builder( PedidoHelper.getActivityPedidoMain());
                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCancelable(false);

                                TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                                if ( PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"))
                                    textView.setText("Você alterou o cliente. Deseja alterar o desconto do(s) produto(s) para a categoria do cliente " + ClienteHelper.getCliente().getNome_cadastro() + "?\nTodas as campanhas lançadas serão removidas, caso não sejam aplicaveis ao cliente alterado!");
                                else
                                    textView.setText("Você alterou o cliente. Deseja alterar o desconto do(s) produto(s) para a categoria do cliente " + ClienteHelper.getCliente().getNome_cadastro() + "?");
                                Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
                                btnNao.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"))
                                            verificaDescontoTabelaCampanha(false, true);
                                        else
                                            verificaDescontoTabelaCampanha(true, false);
                                        alertDialog.dismiss();
                                    }
                                });
                                Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
                                btnSim.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (PedidoHelper.getWebPedido().getTrabalha_com_campanha().equalsIgnoreCase("S"))
                                            verificaDescontoTabelaCampanha(true, true);
                                        else
                                            verificaDescontoTabelaCampanha(true, false);
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            } else {
                                showMsgAlerta("O cliente " + ClienteHelper.getCliente().getNome_cadastro() + " não tem uma categoria definida!");
                            }
                        } else if (ClienteHelper.getCliente().getTipo_tabela_preco().equalsIgnoreCase("6")) {
                            if (ClienteHelper.getCliente().getId_tabela_preco() > 0) {
                                ViewGroup viewGroup =  PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from( PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                                AlertDialog.Builder builder = new AlertDialog.Builder( PedidoHelper.getActivityPedidoMain());
                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCancelable(false);

                                TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                                textView.setText("Você alterou o cliente. Deseja alterar o preço do(s) produto(s) para a tabela do cliente " + ClienteHelper.getCliente().getNome_cadastro() + "?");

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
                                        verificaDescontoTabela();
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            } else {
                                verificaDescontoSemTabela();
                                showMsgSucesso("O cliente " + ClienteHelper.getCliente().getNome_cadastro() + " não tem uma tabela de preço definida! O preço dos produtos foram redefinidos para a tabela padrão!");
                            }
                        }
                    }
                }catch ( NullPointerException e ) {
                }catch ( Exception e ) {
                }
            }
            idCliente = ClienteHelper.getCliente().getId_cadastro();

            setTabelaPrecoItem();

            PedidoBO pedidoBO = new PedidoBO();
            if (bundle.getInt("vizualizacao") != 1)
                verificaDescontoIndevido(pedidoBO);
            try {
                adapter.notifyDataSetChanged();
            } catch ( NullPointerException e) {
                e.printStackTrace();
            }
        }*/

        super.onResume();
    }

    @Override
    public void onDestroy() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.gc();
        super.onDestroy();
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

    public void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            PedidoHelper.getActivityPedidoMain().actionMode.finish();
        } else {
            PedidoHelper.getActivityPedidoMain().actionMode.setTitle(String.valueOf(count));
            PedidoHelper.getActivityPedidoMain().actionMode.invalidate();
        }
    }

    public void showMsgSucesso(String mensagem) {
        ViewGroup viewGroup = PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_sucesso_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = PedidoHelper.getActivityPedidoMain().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( PedidoHelper.getActivityPedidoMain()).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private boolean verificaCampanha(int idCliente, int idProduto, int idLinhaColecao, int idCampanha) {
        CampanhaComercialCabDAO campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        try {
            List<CampanhaComercialCab> listaCampanha = campanhaComercialCabDAO.getLista(idCliente, idProduto, idLinhaColecao);
            if (listaCampanha.size() > 0)
                for (CampanhaComercialCab campanha : listaCampanha)
                    if (campanha.getIdCampanha() == idCampanha)
                        return true;
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void verificaFinanceiro() {
        try {
            cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
            if (ClienteHelper.getCliente().getId_cadastro_servidor() > 0){
                HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(cadastroFinanceiroResumoDAO.getCadastro(ClienteHelper.getCliente().getId_cadastro_servidor()));
                if (HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroVencido() > 0) {
                    PedidoHelper.pintaTxtNomeCliente();
                    Toast.makeText(getActivity(), "Este cliente possui pendências financeiras!.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }


    private void showCliente() {
        //Toast.makeText(getApplication().getApplicationContext(),  UsuarioHelper.getUsuario().getUsuario_altera_cliente_ped(), Toast.LENGTH_SHORT).show();
        try {
            if (PedidoHelper.getWebPedido().getCadastro() == null || UsuarioHelper.getUsuario().getUsuario_altera_cliente_ped().equalsIgnoreCase("S")) {
                bundle = new Bundle();
                bundle.putInt("acao", 1);
                Intent intent = new Intent(getContext(), ActivityClienteDrawer.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            } else {
                Toast.makeText(getContext(), "Alteração do cliente não é permitida!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NullPointerException e) {
        } catch ( Exception e) {
        }
        bundle = new Bundle();
        bundle.putInt("acao", 1);
        Intent intent = new Intent(getContext(), ActivityClienteDrawer.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private void escolherEmpresaPedido() {
        List<EmpresaParametro> listaEmpresas = new EmpresaParametroDAO(db).getLista("SELECT * FROM TBL_EMPRESA_PARAMETRO");
        if (listaEmpresas != null && listaEmpresas.size() > 1) {
            String[] nomesEmpresas = new String[listaEmpresas.size()];
            for (int i = 0; i < listaEmpresas.size(); i++) {
                nomesEmpresas[i] = listaEmpresas.get(i).getRazao_social() != null ? listaEmpresas.get(i).getRazao_social() : listaEmpresas.get(i).getRazao_social();
            }

            new androidx.appcompat.app.AlertDialog.Builder(getContext())
                    .setTitle("Selecione a Empresa")
                    .setItems(nomesEmpresas, (dialog, which) -> {
                        String idNovaEmpresa = String.valueOf(listaEmpresas.get(which).getId_empresa());
                        if (PedidoHelper.getIdPedido() > 0) {
                            WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
                            webPedidoDAO.updateEmpresaPedido(PedidoHelper.getIdPedido(), idNovaEmpresa);
                        }
                        PedidoHelper.getWebPedido().setId_empresa(idNovaEmpresa);
                        onResume(); // Recarrega os dados na tela
                    })
                    .setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

    private void verificarMultiplasEmpresas() {
        int totalEmpresas = db.contagem("SELECT COUNT(*) FROM TBL_EMPRESA_PARAMETRO");
        if (totalEmpresas > 1 && bundle.getInt("vizualizacao") != 1) {
            btnAlterarEmpresa.setVisibility(View.VISIBLE);
        } else {
            btnAlterarEmpresa.setVisibility(View.GONE);
        }
    }

    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
}


