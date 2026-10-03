package com.example.rcksuporte05.rcksistemas.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.LoginDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityAjuda;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPrincipal;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utilitaria {
    public Utilitaria() {

    }

    public void showMsgAlerta(String mensagem, Activity activity) {
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_alerta_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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

    public void showMsgSucesso(String mensagem, Activity activity) {
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_sucesso_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                activity.finish();
            }
        });
        alertDialog.show();
    }

    public void showMsgSimNao( final int id , String mensagem, Context context, Activity activity) {
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                context.startActivity(new Intent( context, activity.getClass() ));
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                context.startActivity( new Intent());
            }

        });
        alertDialog.show();
    }

    public void showFeedbackDialog(String mensagem, boolean sucesso, boolean fimActivity, Integer iconId, Context context) {
        //if (isFinishing() || isDestroyed()) return;
        DialogHelper.showFeedbackDialog(
                context,
                sucesso ? "Sucesso" : "Atenção", // Título dinâmico
                mensagem,
                sucesso,
                iconId,
                null, // Passando a cor personalizada
                () -> {
                    System.gc();
                }
        );
    }

    public void getListaUsuariosAPIX(Context context, DBHelper db, String versaoApi, boolean isMostraMensagem) {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + versaoApi + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.time_conn = configuracao.getTime_conn();
            Api.time_read = configuracao.getTime_read();
            Api.time_write = configuracao.getTime_write();
            Rotas apiRotas = Api.buildRetrofit(true);

            Call<List<Usuario>> call = apiRotas.getListaUsuarios();
            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    switch (response.code()) {
                        case 200:
                            if (!(new UsuarioBO().add(response.body(), context))) {
                                if (isMostraMensagem)
                                    Toast.makeText(context, "Houve um erro ao atualizar a lista de usúario(s)!", Toast.LENGTH_LONG).show();
                            } else {
                                if (isMostraMensagem)
                                    Toast.makeText(context, "Lista de usúario(s) atualizada com sucesso!", Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    if (isMostraMensagem)
                        Toast.makeText(context, "Falha ao atualizar a lista de usúario(s)!" +"\n" + t.getMessage(), Toast.LENGTH_LONG).show();
                    else {
                        try {
                            Configuracao configuracao = new ConfiguracaoDAO(db).getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
                            if (!configuracao.getId().isEmpty() && !configuracao.getUrl_ip_2().isEmpty()) {
                                logarNaApi2(configuracao, context, db, versaoApi, true);
                            } else {
                                Toast.makeText(context, "Falha ao atualizar a lista de usúario(s)!!", Toast.LENGTH_LONG).show();
                            }
                        } catch ( NullPointerException e) {
                            Toast.makeText(context, "Falha ao atualizar a lista de usúario(s)!!!" + "\n" + e.getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!", Toast.LENGTH_LONG).show();
        }
    }

    public void getListaUsuariosAPICrypto(Context context, DBHelper db, String versaoApi, boolean isMostraMensagem) {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if (!configuracao.getId().isEmpty()) {
            String url = configuracao.getUrl_ip_1().trim().toString() + ":" + Integer.toString(configuracao.getPorta_1()).trim() + "/rckwhalleAPI" + versaoApi + "/ws/";
            Api.url = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.time_conn = configuracao.getTime_conn();
            Api.time_read = configuracao.getTime_read();
            Api.time_write = configuracao.getTime_write();
            Rotas apiRotas = Api.buildRetrofit(true);

            Call<List<String>> call = apiRotas.getListaUsuariosCryptografado();
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    switch (response.code()) {
                        case 200:

                            if (!(new UsuarioBO().addUsuario(response.body(), context))) {
                                if (isMostraMensagem)
                                    Toast.makeText(context, "Houve um erro ao atualizar a lista de usúario(s)!", Toast.LENGTH_LONG).show();
                            } else {
                                if (isMostraMensagem)
                                    Toast.makeText(context, "Lista de usúario(s) atualizada com sucesso!", Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao atualizar a lista de usúario(s)!!!" + "\n" + t.getMessage() , Toast.LENGTH_LONG).show();

                }
            });
        } else {
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!", Toast.LENGTH_LONG).show();
        }
    }



    public void logarNaApi2(Configuracao configuracao, Context context, DBHelper db, String versaoApi, Boolean isMostraMensagem) {

        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        if (!configuracao.getId().isEmpty()) {
            String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApi + "/ws/";
            Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                    "http://" + url :
                    "https://" + url;
            Api.time_conn = configuracao.getTime_conn();
            Api.time_read = configuracao.getTime_read();
            Api.time_write = configuracao.getTime_write();
            Rotas apiRotas2 = Api.buildRetrofit2(true);
            Api.context = context;

            Call<List<Usuario>> call = apiRotas2.getListaUsuarios();
            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    switch (response.code()) {
                        case 200:
                            if (!(new UsuarioBO().add(response.body(), context))) {
                                if (isMostraMensagem)
                                    Toast.makeText(context, "Houve um erro ao atualizar a lista de usúario(s)!", Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            if (isMostraMensagem)
                                Toast.makeText(context, "Houve um erro ao atualizar a lista de usúario(s)!!\n(" + String.valueOf(response.code()) + ")", Toast.LENGTH_LONG).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    if (isMostraMensagem)
                        Toast.makeText(context, "Falha ao atualizar a lista de usúario(s)!!!" + "\n" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar!!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isEmailValido(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
        String resposta, jsonEmString = "";
        while ((resposta = buffereReader.readLine()) != null) {
            jsonEmString += resposta;
        }
        return jsonEmString;
    }

    public static Call<List<Financeiro>> getRotasAPI(int idRota, String tipo, int idCliente, int dias_historico_financeiro, Configuracao configuracao, String versaoApp, Context context) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas.getFinanceiroTitulos(tipo, idCliente, dias_historico_financeiro, cabecalho);
                else
                    return apiRotas.getFinanceiro(tipo, idCliente, dias_historico_financeiro, cabecalho);
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
                        return apiRotas.getFinanceiroTitulos(tipo, idCliente, dias_historico_financeiro, cabecalho);

                    else
                        return apiRotas.getFinanceiro(tipo, idCliente, dias_historico_financeiro, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if(idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas2.getFinanceiroTitulos(tipo, idCliente, dias_historico_financeiro, cabecalho);

                else
                    return apiRotas2.getFinanceiro(tipo, idCliente, dias_historico_financeiro, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp.substring(0, 5) + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();
                    Api.context = context;
                    apiRotas2 = Api.buildRetrofit2(true);
                    if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                        return apiRotas2.getFinanceiroTitulos(tipo, idCliente, dias_historico_financeiro, cabecalho);
                    else
                        return apiRotas2.getFinanceiro(tipo, idCliente, dias_historico_financeiro, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }


    public static Call<List<Financeiro>> getRotasAPIPdf(int idRota, String tipo, int idCliente, int dias_historico_financeiro, Configuracao configuracao, String versaoApp, Context context) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        if (idRota == 1) {
            Rotas apiRotas = Api.buildRetrofit(false);
            try {
                if ( versaoApp.trim().equalsIgnoreCase("2.7.2")) {
                    return apiRotas.getFinanceiroPdf(UsuarioHelper.getUsuario().getId_usuario(), tipo, idCliente, dias_historico_financeiro, cabecalho);
                } else {
                    return apiRotas.getFinanceiroPdf2(UsuarioHelper.getUsuario().getId_usuario(), tipo, idCliente, dias_historico_financeiro, versaoApp, cabecalho);
                }
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
                        return apiRotas.getFinanceiroPdf(UsuarioHelper.getUsuario().getId_usuario(), tipo, idCliente, dias_historico_financeiro, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!\n" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }

        } else if(idRota == 2) {
            Rotas apiRotas2 = Api.buildRetrofit2(false);
            try {
                if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                    return apiRotas2.getFinanceiroTitulos(tipo, idCliente, dias_historico_financeiro, cabecalho);

                else
                    return apiRotas2.getFinanceiro(tipo, idCliente, dias_historico_financeiro, cabecalho);
            } catch (NullPointerException e) {
                try {
                    String url = configuracao.getUrl_ip_2().trim().toString() + ":" + Integer.toString(configuracao.getPorta_2()).trim() + "/rckwhalleAPI" + versaoApp.substring(0, 5) + "/ws/";
                    Api.url2 = (configuracao.getConexao_segura().isEmpty() || configuracao.getConexao_segura().equalsIgnoreCase("N")) ?
                            "http://" + url :
                            "https://" + url;
                    Api.time_conn = configuracao.getTime_conn();
                    Api.time_read = configuracao.getTime_read();
                    Api.time_write = configuracao.getTime_write();
                    Api.context = context;
                    apiRotas2 = Api.buildRetrofit2(true);
                    if ( versaoApp.substring(0,5).equalsIgnoreCase("1.0.0"))
                        return apiRotas2.getFinanceiroTitulos(tipo, idCliente, dias_historico_financeiro, cabecalho);
                    else
                        return apiRotas2.getFinanceiro(tipo, idCliente, dias_historico_financeiro, cabecalho);
                } catch (NullPointerException ex) {
                    Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão!" + ex.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Falha ao definir as rotas de acesso a conexão/api!\n" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }
}

