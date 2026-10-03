package com.example.rcksuporte05.rcksistemas.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService /*extends FirebaseMessagingService */{
    PackageInfo pInfo = null;

  /*
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String versaoApp = "";
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versaoApp = pInfo.versionName.substring(0, 5);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = null;
        String title = "";
        String subTitle = "";
        String tipo = remoteMessage.getData().get("tipo");
        String body = "";
        //String body = remoteMessage.getNotification().getBody();

        String pedido;
        String emissao;
        String cliente;
        String observacao;
        String dataa;
        String documento;
        String versaoUpdate = "";
        if (tipo.equalsIgnoreCase("update")) {
            boolean notificado = false;
            try {
               notificado = UsuarioHelper.getUsuario().isAparelho_notificado();
                if (UsuarioHelper.getUsuario().isAparelho_notificado()) {
                    return;
                }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e){
                e.printStackTrace();
            }
            try {
                versaoUpdate = remoteMessage.getData().get("versaoUpdate");
                if (versaoUpdate.equalsIgnoreCase(versaoApp)) {
                    return;
                }
                body = remoteMessage.getNotification().getBody();
                title = remoteMessage.getNotification().getTitle();
                subTitle = remoteMessage.getData().get("subtitle");
                UsuarioHelper.getUsuario().setAparelho_notificado(true);
                showNotificacao( title, subTitle, body);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                jsonArray = new JSONArray(remoteMessage.getData().get("pedidos"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    body = "";
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    pedido = jsonObject.getString("pedido");
                    emissao = jsonObject.getString("emissao");
                    cliente = jsonObject.getString("cliente");

                    try {
                        observacao = jsonObject.getString("motivo");
                    } catch (JSONException e) {
                        observacao = "";
                    }
                    try {
                        dataa = jsonObject.getString("dataanalise");
                    } catch (JSONException e) {
                        dataa = "";
                    }

                    if (tipo.equalsIgnoreCase("bloqueado")) {
                        title = "Pedido bloqueado:  " + pedido;
                        subTitle = "Crédito bloqueado";
                        body += "Emissão: " + emissao + "   Bloqueado: " + dataa + "\n";
                        body += "Cliente: " + cliente + "\n";
                        body += "Motivo:  " + observacao;
                    } else if (tipo.equalsIgnoreCase("cancelado")) {
                        title = "Pedido cancelado:  " + pedido;
                        subTitle = "Pedido excluido/cancelado";
                        body += "Emissão: " + emissao + "   Cancelado: " + dataa + "\n";
                        body += "Cliente: " + cliente + "\n";
                        body += "Motivo:  " + observacao;

                    } else if (tipo.equalsIgnoreCase("faturado")) {
                        title = "Pedido faturado:  " + pedido;
                        subTitle = "Pedido faturado";
                        body += "Emissão: " + emissao + "   Faturado: " + dataa + "\n"
                                + "NF-e/doc: " + jsonObject.getString("documento")
                                + "   Valor R$  " + jsonObject.get("valor_total") + "\n";
                        body += "Cliente: " + cliente + "\n";
                        body += "Pagamento: " + observacao;
                    }

                    showNotificacao( title, subTitle, body);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();;
            }
        }

    }
    */




    private void showNotificacao( String title, String subTitle, String body) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //NotificationManager mNotificationManager =
            //        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           // int importance = NotificationManager.IMPORTANCE_HIGH;
           // NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
           // mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
           // mChannel.enableLights(true);
            //mChannel.setLightColor(Color.RED);
            //mChannel.enableVibration(true);
            //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //mNotificationManager.createNotificationChannel(mChannel);
        }
        //Displaying a notification locally
        //MyNotificationManager.getInstance(getApplicationContext()).displayNotification(title, subTitle, body);
    }

}