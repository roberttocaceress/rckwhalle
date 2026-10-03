package com.example.rcksuporte05.rcksistemas.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
//import com.google.firebase.messaging.FirebaseMessagingService;

import java.sql.SQLException;

import static android.content.ContentValues.TAG;

public class MyFirebaseInstanceIdService{

    //DBHelper db = new DBHelper(this);
    public void novoToken(final Usuario usuario) {
       /*
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        if ( !token.isEmpty()) {
                            if (!token.trim().equalsIgnoreCase(usuario.getAparelho_key_firebase())){
                                try {
                                    db.alterar("UPDATE TBL_WEB_USUARIO SET APARELHO_KEY_FIREBASE = '" + token + "' WHERE ID_USUARIO = '" + usuario.getId_usuario() + "'");
                                    UsuarioHelper.getUsuario().setAparelho_key_firebase(token);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

        */


    }
}

    //this method will be called
    //when the token is generated




    //@Override
    //public void onNewToken(String token) {

        // Get updated InstanceID token.
       /// String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //for now we are displaying the token in the log
        //copy it as this method is called only when the new token is generated
        //and usually new token is only generated when the app is reinstalled or the data is cleared

       // Log.d("Refreshed token: ", refreshedToken);


    //}



