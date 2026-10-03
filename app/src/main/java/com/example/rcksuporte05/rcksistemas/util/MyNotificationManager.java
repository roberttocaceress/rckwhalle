package com.example.rcksuporte05.rcksistemas.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityNotificacao;


import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyNotificationManager {

    private Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification( String title, String subTitle, String body) {
        try {
            if (!UsuarioHelper.getUsuario().getAparelho_key_firebase().isEmpty()) {

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                .setSubText(subTitle)
                                .setContentTitle(title)

                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(body))
                                .setContentText(body)
                                .setContentInfo("Informátivo");

                Intent resultIntent = new Intent(mCtx, ActivityNotificacao.class);
                resultIntent.putExtra("title", title);
                resultIntent.putExtra("subTitle", subTitle);
                resultIntent.putExtra("body", body);

                //Intent resultIntent = new Intent();
                //PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
                //PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
                //                | PendingIntent.FLAG_ONE_SHOT);




                mBuilder.setContentIntent(pendingIntent);

                NotificationManager mNotifyMgr =
                        (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

                /*
                 * The first parameter is the notification id
                 * better don't give a literal here (right now we are giving a int literal)
                 * because using this id we can modify it later
                 * */
                if (mNotifyMgr != null) {
                    final int min = 100;
                    final int max = 1000;
                    int randon = ThreadLocalRandom.current().nextInt(min, max) ;
                    int id = (int) ((System.currentTimeMillis()  + randon ) ) ;// /*% Integer.MAX_VALUE*/);
                    mNotifyMgr.notify(id, mBuilder.build());

                    //int id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}