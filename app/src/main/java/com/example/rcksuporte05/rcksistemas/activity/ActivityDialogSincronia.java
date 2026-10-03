package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.rcksuporte05.rcksistemas.BO.SincroniaBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.EmpresaParametroDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
//import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class ActivityDialogSincronia extends Activity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.id_opcao_pedidos)
    Switch id_opcao_pedidos;

    @BindView(R.id.id_opcao_cliente)
    Switch id_opcao_cliente;

    @BindView(R.id.id_opcao_produto)
    Switch id_opcao_produto;

    @BindView(R.id.id_opcao_pedidos_pendentes)
    Switch id_opcao_pedidos_pendentes;

    @BindView(R.id.id_opcao_prospect)
    Switch id_opcao_prospect;

    @BindView(R.id.id_opcao_prospect_enviados)
    Switch id_opcao_prospect_enviados;

    @BindView(R.id.id_opcao_visiatas_pendentes)
    Switch id_opcao_visiatas_pendentes;

    @BindView(R.id.id_opcao_imagem_produto)
    Switch id_opcao_imagem_produto;

    @BindView(R.id.id_opcao_cobrancas)
    Switch id_opcao_cobrancas;


    private SincroniaBO sincroniaBO = new SincroniaBO();
    PackageInfo pInfo = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sincronia);
        ButterKnife.bind(this);
        id_opcao_pedidos_pendentes.setOnCheckedChangeListener(this);
        id_opcao_pedidos.setOnCheckedChangeListener(this);

        try {
            EmpresaParametroDAO empresaParametroDAO = new EmpresaParametroDAO(new DBHelper(this));
            EmpresaParametro empresaParametro = empresaParametroDAO.getEmpresa("SELECT * FROM TBL_EMPRESA_PARAMETRO");
            if (empresaParametro.getUtiliza_cobranca_offline().equalsIgnoreCase("S") || empresaParametro.getUtiliza_cobranca_offline().equalsIgnoreCase("V"))
                id_opcao_cobrancas.setChecked(true);
        } catch ( NullPointerException|CursorIndexOutOfBoundsException e ) {
                e.printStackTrace();
        } catch ( Exception e) {
                e.printStackTrace();
        }
        try {
            Bundle extras = getIntent().getExtras();
            if( extras.getBoolean("autoSincroniza") ) {
                Configuracao configuracao = new Configuracao();
                ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(new DBHelper(this));
                try {
                    configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                    if (configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("S") || configuracao.getTrabalha_com_cobranca().equalsIgnoreCase("V") ) {
                        id_opcao_cobrancas.setChecked(true);
                    }
                } catch ( NullPointerException|CursorIndexOutOfBoundsException e) {
                } catch (Exception e) {
                }
                btnConfirmar();;
            }
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnConfirmar)
    public void btnConfirmar() {
        String versaoApp = "-";
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versaoApp = pInfo.versionName.substring(0,7);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //ActivityPrincipal.setSincronizando();
        Bundle extras = getIntent().getExtras();
        if( extras.getBoolean("autoSincroniza") == false) {
            ActivityPrincipal.setSincronizando();
        }
        Sincronia sincronia = new Sincronia(id_opcao_cliente.isChecked(),
                id_opcao_produto.isChecked(),
                id_opcao_pedidos.isChecked(),
                id_opcao_pedidos_pendentes.isChecked(),
                id_opcao_prospect.isChecked(),
                id_opcao_prospect_enviados.isChecked(),
                id_opcao_visiatas_pendentes.isChecked(),
                id_opcao_imagem_produto.isChecked(),
                id_opcao_cobrancas.isChecked());
        sincroniaBO.sincronizaApi( sincronia, versaoApp );
        finish();
    }

    @OnClick(R.id.btnCancelar)
    public void btnCancelar() {
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.id_opcao_pedidos_pendentes:
                if (b) {
                    DBHelper db = new DBHelper(ActivityDialogSincronia.this);
                    WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
                    try {
                        List<WebPedido> listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
                        for (WebPedido pedido : listaPedido)
                            if (pedido.getDescontoIndevido().equalsIgnoreCase("S")) {
                                Toast toast = Toast.makeText(this, "O pedido " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + " esta com desconto indevido!\nÉ aconselhavel a correção do mesmo, antes do envio/sincronização ao servidor!", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                id_opcao_pedidos_pendentes.setChecked(false);
                                break;
                            } else if (Integer.parseInt(pedido.getId_web_pedido_servidor()) > 0) {
                                Toast toast = Toast.makeText(this, "O pedido " + pedido.getId_web_pedido() + " de " + pedido.getNome_extenso() + " ja foi sincronizado!\nÉ aconselhavel a confirmação junto a empresa, antes do envio/sincronização ao servidor!", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                id_opcao_pedidos_pendentes.setChecked(false);
                                break;
                            }

                    } catch (NumberFormatException | CursorIndexOutOfBoundsException | NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            /*
                case R.id.id_opcao_pedidos:
                if(b) {
                    DBHelper db = new DBHelper(ActivityDialogSincronia.this);
                    WebPedidoDAO webPedidoDAO = new WebPedidoDAO(db);
                    try {
                        List<WebPedido> listaPedido = webPedidoDAO.getLista("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' AND ID_CADASTRO > 0 ORDER BY ID_WEB_PEDIDO DESC;");
                        if ( listaPedido.size() > 0) {
                            Toast toast = Toast.makeText(this, "Esta opção só pode ser executada quando não houver pedidos pendentes de sincronia! Caso deseje sincronizar novamente todos os pedidos ja enviados, favor transmitir primeiro todos os pedidos pendentes!!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            id_opcao_pedidos.setChecked(!b);
                        }
                    } catch (NumberFormatException | CursorIndexOutOfBoundsException | NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;

             */
        }
    }
}
