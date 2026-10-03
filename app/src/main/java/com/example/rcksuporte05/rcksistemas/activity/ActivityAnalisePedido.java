package com.example.rcksuporte05.rcksistemas.activity;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoAnalise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAnalisePedido extends AppCompatActivity {
    @BindView(R.id.tblAnalisePedido)
    Toolbar toolbar;
    @BindView(R.id.edtMotivo)
    EditText edtMotivo;
    @BindView(R.id.txvCredito)
    TextView txvCredito;
    @BindView(R.id.txvCarregamento)
    TextView txvCarregamento;
    @BindView(R.id.txvExpedicao)
    TextView txvExpedicao;
    @BindView(R.id.txvFaturamento)
    TextView txvFaturamento;
    @BindView(R.id.txvBoletoEmitido)
    TextView txvBoletoEmitido;
    @BindView(R.id.txvTransportador)
    TextView txvTransportador;
    @BindView(R.id.txvEspecie)
    TextView txvEspecie;
    @BindView(R.id.txvVolume)
    TextView txvVolume;

    @BindView(R.id.txvDataImportacao)
    TextView txvDataImportacao;
    public WebPedidoAnalise webPedidoAnalise;
    public WebPedido webPedido;
    private Context context;
    private DBHelper db = new DBHelper(this);
    private ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_pedido);
        ButterKnife.bind(this);
        context = this;
        webPedido = PedidoHelper.getWebPedido();
        toolbar.setTitle( "Pedido: " + webPedido.getId_web_pedido_servidor());
        WebPedidoAnalise webPedidoAnalise = new WebPedidoAnalise();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sincronizaAnalisePedidoAPI();
        toolbar.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        limparDados();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        limparDados();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                limparDados();
                super.onBackPressed();
                break;
        }
        return true;
    }


    public void limparDados() {
        try {
            PedidoHelper.setWebPedido(null);
            ClienteHelper.setCliente(null);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        webPedido = null;
        //System.gc();
    }

    private String  getIdPedido(WebPedido webPedido) {
        try {
            if (!webPedido.getId_pedido().isEmpty())
                if (!webPedido.getId_pedido().equalsIgnoreCase(webPedido.getId_web_pedido_servidor()))
                    return webPedido.getId_web_pedido_servidor();
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        return webPedido.getId_web_pedido_servidor();
    }

    private String getIdOrdemCarregamento(WebPedido webPedido) {
        try {
            if (Integer.parseInt(webPedido.getId_ordem_carregamento()) > 0)
                return webPedido.getId_ordem_carregamento();
        } catch (NullPointerException|NumberFormatException e) {
        } catch (Exception e){
        }
        return "0";
    }

    public void sincronizaAnalisePedidoAPI() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Falha ao definir a versão do aplicativo para acesso a conexão!!", Toast.LENGTH_LONG).show();
            return;
        }
        if ( !usuarioIsLogado() )
            return;
        String versaoApp = pInfo.versionName.substring(0, 5);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            Call<WebPedidoAnalise> call = getRotasAPI(1, getIdPedido(webPedido), getIdOrdemCarregamento(webPedido), configuracao, versaoApp);
            if ( call == null)
                return;
            call.enqueue(new Callback<WebPedidoAnalise>() {
                @Override
                public void onResponse(Call<WebPedidoAnalise> call, Response<WebPedidoAnalise> response) {
                    switch (response.code()) {
                        case 200:
                            updatePedidosAnalisados(response.body());
                            break;
                        default:
                            Toast.makeText(ActivityAnalisePedido.this, "Falha ao analisar o histórico do pedido!"
                                    + "\n(" + String.valueOf(response.code()) + ")", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<WebPedidoAnalise> call, Throwable t) {
                    try {
                        toolbar.setTitle("Pedido:" + webPedido.getId_web_pedido_servidor());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ActivityAnalisePedido.this, "Falha ao analisar o histórico do pedido!!"
                            + "\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText( context, "Parametros para conexão não cadastrados!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePedidosAnalisados(WebPedidoAnalise webPedidoAnalise) {
        WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
        webPedidoAnalise.setId_pedido_app(PedidoHelper.getWebPedido().getId_web_pedido());
        try {
            if( webPedidoAnalise.getExpedicao_status() == null && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S")) {
                try {
                    if (  webPedidoAnalise.getExcluido() != null && webPedidoAnalise.getExcluido().equalsIgnoreCase("N")) {
                        if ( webPedidoAnalise.getFaturado() == null && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S")){
                            webPedidoDAO.updatePedidoAnaliseCredito(webPedidoAnalise);
                        } else if( webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S") &&  webPedidoAnalise.getFaturado().equalsIgnoreCase("S") ) {
                            webPedidoDAO.updatePedidoFaturado(webPedidoAnalise);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if( webPedidoAnalise.getExpedicao_status() == null  && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("N")) {
                if ( webPedidoAnalise.getFaturado() == null && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("N")){
                    webPedidoDAO.updatePedidoAnaliseCredito(webPedidoAnalise);
                } else if( webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("N") &&  webPedidoAnalise.getFaturado().equalsIgnoreCase("S") ) {
                    webPedidoDAO.updatePedidoFaturado(webPedidoAnalise);
                }
            } else if( webPedidoAnalise.getExpedicao_status() != null  && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S")) {
                try {
                    if (  webPedidoAnalise.getExcluido() != null && webPedidoAnalise.getExcluido().equalsIgnoreCase("N")) {
                        if ( webPedidoAnalise.getFaturado() == null && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S")){
                            webPedidoDAO.updatePedidoAnaliseCredito(webPedidoAnalise);
                        } else if( webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S") &&  webPedidoAnalise.getFaturado().equalsIgnoreCase("S") ) {
                            webPedidoDAO.updatePedidoFaturado(webPedidoAnalise);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            if (webPedidoAnalise.getExcluido() != null && webPedidoAnalise.getExcluido().equalsIgnoreCase("S")) {
                webPedidoDAO.updatePedidCanceladoExcluido(webPedidoAnalise);
            }
            try {
                if (webPedidoAnalise.getExcluido() != null && webPedidoAnalise.getExcluido().equalsIgnoreCase("N")) {
                    if (webPedidoAnalise.getFaturado() == null && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S")) {
                        webPedidoDAO.updatePedidoAnaliseCredito(webPedidoAnalise);
                    } else if (webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S") && webPedidoAnalise.getFaturado().equalsIgnoreCase("S")) {
                        webPedidoDAO.updatePedidoFaturado(webPedidoAnalise);
                    } else if (webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S") && webPedidoAnalise.getFaturado().equalsIgnoreCase("N")) {
                        webPedidoDAO.updatePedidoAnaliseCredito(webPedidoAnalise);
                    }
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        if (webPedidoAnalise.getExcluido() != null && webPedidoAnalise.getExcluido().equalsIgnoreCase("S")) {
            webPedidoDAO.updatePedidCanceladoExcluido(webPedidoAnalise);
        } else if (webPedidoAnalise.getAnalise_credito() != null && webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("B")) {
            webPedidoDAO.updatePedidCanceladoExcluido(webPedidoAnalise);
        } else {
            try {
                if (Integer.parseInt(webPedidoAnalise.getId_nota_fiscal()) > 0) {
                    //holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_faturado)); //mudado para cor_pedido_autorizado-faturado
                    //updatePedidoAnalisado(listaParaAnalise.get(0));
                    //holder.txtStatus.setText("Faturado".toUpperCase());
                }
            } catch (NullPointerException e) {
                webPedidoDAO.updatePedidoImportado(webPedidoAnalise);
            } catch (NumberFormatException e) {
                webPedidoDAO.updatePedidoImportado(webPedidoAnalise);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        showDados(webPedidoAnalise);

    }

    public void showDados( WebPedidoAnalise webPedidoAnalise) {
        this.webPedidoAnalise = webPedidoAnalise;
        try {
            if (Integer.parseInt(webPedidoAnalise.getId_pedido()) > 0) {
                if ( webPedidoAnalise.getData_importacao().equalsIgnoreCase("")) {
                    Toast.makeText( ActivityAnalisePedido.this, "Pedido " + webPedidoAnalise.getId_pedido() + " de " + webPedido.getCadastro().getNome_cadastro() + " não importado!" , Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    edtMotivo.setText("");
                    txvDataImportacao.setText( new SimpleDateFormat("dd/MM/yy hh:mm").format(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(webPedidoAnalise.getData_importacao())));
                    if ( webPedidoAnalise.getExcluido().equalsIgnoreCase("S")) {
                        edtMotivo.setText(webPedidoAnalise.getJustificativa_exclusao().toUpperCase());
                        txvCredito.setText("Cancelado/Excluido".toUpperCase());
                        Toast.makeText( ActivityAnalisePedido.this, "Pedido " + webPedidoAnalise.getId_pedido() + " de " + webPedido.getCadastro().getNome_cadastro() + " foi cancelado/excluido do sistema ERP em "
                                +  new SimpleDateFormat("dd/MM/yy hh:mm").format(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(webPedidoAnalise.getExcluido_usuario_data())) , Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("N") || webPedidoAnalise.getAnalise_credito().isEmpty())
                        txvCredito.setText("Pendente".toUpperCase());
                    else if (webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("T"))
                        txvCredito.setText("Crédito revisado e bloqueado".toUpperCase());
                    else if (webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("S"))
                        txvCredito.setText("Crédito autorizado".toUpperCase());
                    else if (webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("B"))
                        txvCredito.setText("Crédito negado".toUpperCase());
                    try {
                        edtMotivo.setText(!webPedidoAnalise.getEvento_descricao().isEmpty() ? webPedidoAnalise.getEvento_descricao().toUpperCase() : "");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (Integer.parseInt(webPedidoAnalise.getId_ordem_carregamento()) > 0)
                            txvCarregamento.setText(webPedidoAnalise.getData_ordem_carregamento() + "  Nº " + webPedidoAnalise.getId_ordem_carregamento());
                        else {
                            if (webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("N") ||
                                    webPedidoAnalise.getAnalise_credito().isEmpty() ||
                                    webPedidoAnalise.getAnalise_credito().equalsIgnoreCase("B"))
                                txvCarregamento.setText("");
                            else
                                txvCarregamento.setText("Pendente".toUpperCase());
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        txvTransportador.setText(webPedidoAnalise.getNome_transportadora().toUpperCase());
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    }
                    try {
                        txvEspecie.setText(webPedidoAnalise.getEspecie());
                        txvVolume.setText(webPedidoAnalise.getVolumes());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (Integer.parseInt(webPedidoAnalise.getId_nota_fiscal()) > 0) {
                            try {
                                if (Integer.parseInt(webPedidoAnalise.getNumero_nota_fiscal()) > 0)
                                    txvFaturamento.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedidoAnalise.getData_nota_fiscal())) + " NF-e: " + webPedidoAnalise.getNumero_nota_fiscal());
                                else
                                    txvFaturamento.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedidoAnalise.getData_nota_fiscal())) + webPedidoAnalise.getNumero_nota_fiscal());
                                if (Integer.parseInt(webPedidoAnalise.getId_ordem_carregamento()) <= 0)
                                    txvCarregamento.setText("");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            } catch (Exception e ) {
                                txvFaturamento.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedidoAnalise.getData_nota_fiscal())) + webPedidoAnalise.getNumero_nota_fiscal());
                            }
                        }
                    } catch ( NullPointerException e) {
                        e.printStackTrace();
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                    if ( webPedidoAnalise.getBoleto_emitido().equalsIgnoreCase("S"))
                        txvBoletoEmitido.setText( "Emitido");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch ( NullPointerException e) {
            Toast.makeText( ActivityAnalisePedido.this, "Pedido " + webPedidoAnalise.getId_pedido() + " de " + webPedido.getCadastro().getNome_cadastro() + " não importado" , Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ActivityAnalisePedido.this, "Pedido " + webPedidoAnalise.getId_pedido() + " de " + webPedido.getCadastro().getNome_cadastro() + " não importado", Toast.LENGTH_SHORT).show();
        }
    }

    private Call<WebPedidoAnalise> getRotasAPI(int idRota, String idPedido, String idOrdem, Configuracao configuracao, String versaoApp) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                //return apiRotas.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas.getAnalisePedido( idPedido, idOrdem, cabecalho);
                else
                    return apiRotas.listaAnalise( idPedido, idOrdem, cabecalho);
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
                        return apiRotas.getAnalisePedido(  idPedido, idOrdem, cabecalho);
                    else
                        return apiRotas.listaAnalise(  idPedido, idOrdem, cabecalho);
                } catch (NullPointerException ex) {
                    //showHideProgressBar(false);
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if(idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas2.getAnalisePedido( idPedido, idOrdem, cabecalho);
                else
                    return apiRotas2.listaAnalise( idPedido, idOrdem, cabecalho);
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
                        return apiRotas2.getAnalisePedido( idPedido, idOrdem, cabecalho);
                    else
                        return apiRotas2.listaAnalise( idPedido, idOrdem, cabecalho);
                    //return apiRotas2.sincronizaCobrancaRecebimento(idCadastro, Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), sincronia, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\nSelecione o registro e tente novamente!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!\nSelecione o registro e tente novamente!\n"+ e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return null;
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
}
