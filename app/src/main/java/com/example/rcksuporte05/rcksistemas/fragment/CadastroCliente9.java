package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityClienteDrawer;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoPendente;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroCliente9 extends Fragment implements View.OnClickListener {
    @BindView(R.id.btnSalvar)
    Button btnSalvar;
    @BindView(R.id.edtobsFat)
    EditText edtobsFat;
    @BindView(R.id.edtObsFinancas)
    EditText edtObsFinancas;
    private DBHelper db;
    private ProgressDialog progress;
    private Configuracao configuracao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente9_2026, container, false);
        ButterKnife.bind(this, view);
        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            edtobsFat.setFocusable(false);
            edtObsFinancas.setFocusable(false);
            btnSalvar.setVisibility(View.GONE);
        } else {
            btnSalvar.setOnClickListener(this);
            btnSalvar.setOnClickListener(this);
        }
        setDados();
        ClienteHelper.setCadastroCliente9(this);
        return view;
    }

    private void setDados() {
        if (ClienteHelper.getCliente().getObservacoes_faturamento() != null && !ClienteHelper.getCliente().getObservacoes_faturamento().trim().isEmpty())
            edtobsFat.setText(ClienteHelper.getCliente().getObservacoes_faturamento());
        if (ClienteHelper.getCliente().getObservacoes_financeiro() != null && !ClienteHelper.getCliente().getObservacoes_financeiro().trim().isEmpty())
            edtObsFinancas.setText(ClienteHelper.getCliente().getObservacoes_financeiro());

    }

    public void addDadosDaFrame() {
        ClienteHelper.getCliente().setObservacoes_faturamento( !edtobsFat.getText().toString().trim().isEmpty() ? edtobsFat.getText().toString() : null);
        ClienteHelper.getCliente().setObservacoes_financeiro( !edtObsFinancas.getText().toString().trim().isEmpty() ? edtObsFinancas.getText().toString() : null);
        ClienteHelper.getCliente().setAtivo("S");
        ClienteHelper.getCliente().setF_cliente("N");
        ClienteHelper.getCliente().setF_fornecedor("N");
        ClienteHelper.getCliente().setF_funcionario("N");
        ClienteHelper.getCliente().setF_transportador("N");
        ClienteHelper.getCliente().setF_vendedor("N");
        ClienteHelper.getCliente().setId_entidade("10");
        ClienteHelper.getCliente().setId_empresa(Integer.parseInt(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice()));
        ClienteHelper.getCliente().setUsuario_id(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()));
        ClienteHelper.getCliente().setUsuario_nome(UsuarioHelper.getUsuario().getNome_usuario());
    }

    @Override
    public void onClick(View view) {
        if ( view == btnSalvar) {
            try {
                db = new DBHelper(getActivity());
                configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                addDadosDaFrame();
                if (ClienteHelper.getCliente().getFinalizado().equals("N")) {
                    ClienteHelper.getCliente().setAlterado("N");
                } else {
                    if (ClienteHelper.getCliente().getId_cadastro_servidor() > 0)
                        ClienteHelper.getCliente().setAlterado("S");
                }
                if (ClienteHelper.getCliente().getFinalizado().equals("S"))
                    ClienteHelper.getCliente().setAlterado("S");
                ClienteHelper.getCliente().setFinalizado("S");
                ClienteDAO clienteDAO = new ClienteDAO(db);
                clienteDAO.update(ClienteHelper.getCliente());
                if (getActivity().getIntent().getIntExtra("prospect", 0) > 0)
                    db.deleteDados("TBL_PROSPECT", "ID_PROSPECT = ?", new String[]{ String.valueOf(getActivity().getIntent().getIntExtra("prospect", 0))});
                //showMsgAlerta("Cadastro salvo com sucesso! É aconselhavel enviar o cadastro a empresa antes de efetuar pedido ao cliente!");
                if ( ClienteHelper.getCliente().getId_cadastro_servidor() <= 0)
                    showMsgBackupCliente(configuracao.getCnpj_empresa(), ClienteHelper.getCliente());
                else {
                    showMsgAlerta("Cadastro salvo com  sucesso!");
                }
                //addClienteFirebase( configuracao.getCnpj_empresa(), ClienteHelper.getCliente() );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showMsgAlerta(String mensagem) {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( getActivity()).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);

        Button button = (Button) dialogView.findViewById(R.id.btnMensagemOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                getActivity().finish();
            }
        });
        alertDialog.show();
    }

    public void showMsgBackupCliente( String cnpj, Cliente webCliente) {
        ViewGroup viewGroup =  getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from( getActivity()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText("Cadastro salvo com sucesso! Gostaria de criar uma cópia de segurança?");
        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                getActivity().finish();
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                addClienteFirebase( cnpj, webCliente );
            }
        });
        alertDialog.show();
    }
    private void addClienteFirebase(String cnpj, Cliente webCliente) {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected()) {
            showMsgAlerta("Você está offline. A copia de segurança não sera efetuada!.");
            return; // Interrompe o método
        }
        progress = new ProgressDialog(getContext());
        progress.setMessage("Efetuando cópia de segurança!Aguarde...");
        progress.setTitle("Atenção!");
        progress.setCancelable(false);
        progress.show();
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        for (Field field : webCliente.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(webCliente));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String x = "0";
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if ( webCliente.getId_cliente_app_fdb() == null || webCliente.getId_cliente_app_fdb().isEmpty()) {
                webCliente.setId_cliente_app_fdb("V " + webCliente.getId_vendedor()+ " DCAD " + db.pegaDataHoraAtual()
                       +  " DBFSTORE " +  "TP " + webCliente.getPessoa_f_j() + " "  + webCliente.getCpf_cnpj() );
                new ClienteDAO(db).updateIdFDB(webCliente, String.valueOf(webCliente.getId_cadastro()));
            }
            fdb.collection(cnpj).document("TBL_WEB_CLIENTE").collection(UsuarioHelper.getUsuario().getId_quando_vendedor()).document(webCliente.getId_cliente_app_fdb()).set(map).addOnSuccessListener(documentReference -> {
                        progress.dismiss();
                        showMsgAlerta("Copia de segurança efetuada com sucesso!É aconselhavel o envio do cadastro a empresa antes de efetuar o pedido!Em caso de duvidas entre em contato com o suporte técnico!" );
                    }
            ).addOnFailureListener(e -> {
                progress.dismiss();
                showMsgAlerta("Falha ao efetuar a copia de segurança!!\n" + e);
            });

        }, 500);
    }
}
