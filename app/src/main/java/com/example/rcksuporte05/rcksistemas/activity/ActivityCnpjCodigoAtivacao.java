package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity_.ActivityLogin_;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Mask;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCnpjCodigoAtivacao extends AppCompatActivity {
    @BindView(R.id.edtCnpjCpf)
    EditText edtCnpjCpf;
    @BindView(R.id.edtCodigo)
    EditText edtCodigo;
    @BindView(R.id.btnValida)
    Button btnValida;
    @BindView(R.id.btnCancela)
    Button btnCancela;
    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerLayout;
    FirebaseFirestore fdb;
    boolean isValidCnpjCod = false;
    private DBHelper db = new DBHelper(this);
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnpj_codigo_ativacao);
        context = this;
        ButterKnife.bind(this);
        shimmerLayout.hideShimmer();
        btnValida.setEnabled(false);
        fdb = FirebaseFirestore.getInstance();
        edtCnpjCpf.setFocusable(true);
        edtCnpjCpf.addTextChangedListener(MaskCnpj.insert(Mask.MaskType.CNPJ, edtCnpjCpf, btnValida, context, edtCodigo));
        edtCodigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (s.toString().length()) {
                    case 4:
                        if ( validaCpfCnpj()) {
                            btnValida.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButton));
                            btnValida.setEnabled(true);
                        }
                        break;
                    default:
                        btnValida.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButtonTransparente20));
                        btnValida.setEnabled(false);
                        break;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finish();
                finishAffinity();
            }
        });
        btnValida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaCpfCnpj())
                    if (!edtCodigo.getText().toString().isEmpty() && edtCodigo.getText().length() >= 4)
                        showShimmerLayout(3000, fdb, edtCnpjCpf.getText().toString(), edtCodigo.getText().toString());
                    else
                        edtCodigo.setError("Codigo é inválido");
            }
        });
    }

    private void showShimmerLayout(int delay, FirebaseFirestore fdb, String cnpj, String codigo) {
        shimmerLayout.showShimmer(true);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               getDadosFirebase(fdb, cnpj.replaceAll("[^0-9]", ""), codigo);
            }
        };
        handler.postDelayed(runnable, delay);
    }

    public boolean validaCpfCnpj() {
        if (edtCnpjCpf.getText().toString().replaceAll("[^0-9]", "").length() == 14) {
            if (MascaraUtil.isValidCNPJ(edtCnpjCpf.getText().toString().replaceAll("[^0-9]", ""))) {
                edtCnpjCpf.setText(MascaraUtil.mascaraCNPJ(edtCnpjCpf.getText().toString().replaceAll("[^0-9]", "")));
                return true;
            }
        }
        edtCnpjCpf.setError("CNPJ é inválido");
        btnValida.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButtonTransparente20));
        return false;
    }

    private void getDadosFirebase(FirebaseFirestore fdb, String cnpj, String codigo) {
        shimmerLayout.showShimmer(true);
        try {
            fdb.collection("clientes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.get("cnpj").toString().equalsIgnoreCase(cnpj.trim()) && document.get("codigo").toString().equalsIgnoreCase(codigo)) {
                                        isValidCnpjCod = true;
                                        addConfiguracao(document);
                                        break;
                                    }
                                }
                                if (!isValidCnpjCod) {
                                    shimmerLayout.hideShimmer();
                                    Toast.makeText(ActivityCnpjCodigoAtivacao.this, " CNPJ/Código de segurança não localizado(s), favor confirmar os dados informados!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                shimmerLayout.hideShimmer();
                                Toast.makeText(ActivityCnpjCodigoAtivacao.this, " Falha ao localizar os dados.\nFavor tentar novamente, caso o problema persista, entre em contato com o suporte técnico!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }catch ( NullPointerException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void addConfiguracao(QueryDocumentSnapshot document) {
        Configuracao configuracao = new Configuracao();
        configuracao.setUrl_ip_1(document.get("ddns").toString().toLowerCase());
        configuracao.setPorta_1(Integer.parseInt(document.get("porta").toString()));
        configuracao.setUrl_ip_desc_1(document.get("descricao").toString().toUpperCase());
        configuracao.setConexao_segura(document.get("segura").toString());
        configuracao.setPesquisa_produto_por_linha_grupo("G");
        configuracao.setTrabalha_com_campanhas("N");
        configuracao.setTrabalha_com_dscto_reais("N");
        configuracao.setTrabalha_com_limite_cred("N");
        configuracao.setTrabalha_com_pre_pedido("N");
        configuracao.setTrabalha_com_confirmacao_prazo("N");
        configuracao.setTrabalha_com_unidade_manut_estoque("N");
        configuracao.setTrabalha_com_cobranca("N");
        configuracao.setTrabalha_com_icms_st("N");
        configuracao.setTrabalha_com_paginacao_dados("N");
        configuracao.setConfirma_sincronia_dados("N");

        try {
            configuracao.setTrabalha_com_cobranca(document.get("utilizacobranca").toString());
        } catch ( NullPointerException e) {
            configuracao.setTrabalha_com_cobranca("N");
        } catch ( Exception e) {
            configuracao.setTrabalha_com_cobranca("N");
        }

        try {
            configuracao.setSincronia_automatica_app(document.get("sincroniaautomatica").toString());
        } catch ( NullPointerException e) {
            configuracao.setSincronia_automatica_app("N");
        } catch ( Exception e) {
            configuracao.setSincronia_automatica_app("N");
        }
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        shimmerLayout.hideShimmer();
        if (configuracaoDAO.add(configuracao)) {
            //startActivity(new Intent(ActivityCnpjCodigoAtivacao.this, MainActivity.class));
            startActivity(new Intent(ActivityCnpjCodigoAtivacao.this, ActivityLogin_.class));
            finish();
        } else {
            Toast.makeText(ActivityCnpjCodigoAtivacao.this, " Falha ao atualizar os dados\nFavor cadastrar os dados manualmente", Toast.LENGTH_SHORT).show();
        }
    }

    private static class MaskCnpj{
        public enum MaskType {
            CNPJ("##.###.###/####-##"), NUMBER("##.##");
            String mask;

            MaskType(String s) {
                mask = s;
            }

            public String getMask() {
                return mask;
            }
        }
        public static String unmask(String s) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[ ]", "").replaceAll("[)]", "");
        }

        public static String unmaskNUmber(String s) {
            return s.replaceAll("[,]", ".").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[ ]", "").replaceAll("[)]", "");
        }
        public static String mask(Mask.MaskType type, String s) {
            String result = s;

            if (!s.contains(".") && !type.getMask().equalsIgnoreCase("##.##")) {
                String str = Mask.unmask(s.toString());
                result = "";

                int i = 0;
                for (char m : type.getMask().toCharArray()) {
                    if (m != '#') {
                        result += m;
                        continue;
                    }
                    try {
                        result += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
            } else {
                result = "";
                String str = Mask.unmaskNUmber(s.toString());
                int i = 0;
                for (char m : type.getMask().toCharArray()) {
                    if (m != '#') {
                        result += m;
                        continue;
                    }
                    try {
                        result += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
            }

            return result;
        }



        public static TextWatcher insert(final Mask.MaskType type, final EditText edtCnpj, final Button btn, final Context context, final EditText edtCodigo) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {

                    if (isUpdating){
                        isUpdating = false;
                        old = s.toString();
                        return;
                    }

                    if (!s.toString().isEmpty() && (s.toString().length() > old.length())) {
                        String str;
                        if ( !type.getMask().equalsIgnoreCase("##.##") ){
                            str = Mask.unmask(s.toString());
                        } else {
                            str = Mask.unmaskNUmber(s.toString());
                        }
                        String mask = "";

                        int i = 0;
                        for (char m : type.getMask().toCharArray()) {
                            if (m != '#') {
                                mask += m;
                                continue;
                            }
                            try {
                                mask += str.charAt(i);
                            } catch (Exception e) {
                                break;
                            }
                            i++;
                        }
                        isUpdating = true;
                        edtCnpj.setText(mask);
                        edtCnpj.setSelection(mask.length());
                        String  x = s.toString().replaceAll("[^0-9]", "");
                        if ( s.toString().replaceAll("[^0-9]", "").length() == 14 && edtCodigo.getText().length() == 4) {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButton));
                            btn.setEnabled(true);
                        }else {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButtonTransparente20));
                            btn.setEnabled(false);
                        }

                    }else{
                        old = s.toString();
                        if ( s.toString().replaceAll("[^0-9]", "").length() == 14 && edtCodigo.getText().length() == 4) {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButton));
                            btn.setEnabled(true);
                        }else {
                            btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorButtonTransparente20));
                            btn.setEnabled(false);
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            };
        }
    }
}