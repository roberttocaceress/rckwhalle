package com.example.rcksuporte05.rcksistemas.api;

import android.content.Context;
import android.security.keystore.KeyProperties;

import com.example.rcksuporte05.rcksistemas.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RCK 04 on 06/10/2017.
 */

public class Api {
    public static String url = "";
    public static String url2 = "";
    public static String urlTecnico = "";
    public static String time_conn = "";
    public static String time_read = "";
    public static String time_write = "";
    private static Rotas apiRotas;
    private static Rotas apiRotas2;

    private static  Rotas apiRotas3;
    private static Rotas apiRotasTecnico;
    public static  Context context;

    public static Rotas buildRetrofit(Boolean newApi) {
        try {
            if (apiRotas == null || newApi)
                apiRotas = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(interceptor().build())
                        .baseUrl(url)
                        .build().create(Rotas.class);
        } catch ( IllegalArgumentException e) {
            e.printStackTrace();
        }
        return apiRotas;
    }

    public static Rotas buildRetrofit2(Boolean newApi) {
        try {
            if (apiRotas2 == null || newApi)
                apiRotas2 = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(interceptor().build())
                        .baseUrl(url2)
                        .build().create(Rotas.class);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return apiRotas2;
    }

    public static Rotas buildRetrofitSefaz(Boolean newApi) {
        try {
            if (apiRotas3 == null || newApi)
                apiRotas3 = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(interceptor().build())
                        .baseUrl("https://publica.cnpj.ws/")
                        .build().create(Rotas.class);
        } catch ( IllegalArgumentException e) {
            e.printStackTrace();
        }
        return apiRotas3;
    }


    public static Rotas buildRetrofitTecnico(Boolean newApi) {
        if (apiRotasTecnico == null || newApi )
            apiRotasTecnico = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(interceptor().build())
                    .baseUrl(urlTecnico)
                    .build().create(Rotas.class);
        return apiRotasTecnico;
    }

    public static Rotas buildRetrofitHttps(Boolean newApi, Context contex) {

        if (apiRotasTecnico == null || newApi )
            apiRotasTecnico = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(interceptor().build())
                    .baseUrl(urlTecnico)
                    .build().create(Rotas.class);
        return apiRotasTecnico;
    }


    private static OkHttpClient.Builder interceptor() {
        //Este funcionou parcialmente,problema continuou no servidor
        /*
        InputStream in = context.getResources().openRawResource(R.raw.rckwhalle);
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(in,"1234".toCharArray());

        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KeyManagerFactory keyManagerFactory = null;
        try {
            keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, "1234".toCharArray());
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        */
        int conn = 30;
        int read = 30;
        int write = 30;
        try {
            if (time_conn != null && !time_conn.isEmpty()) conn = Integer.parseInt(time_conn);
            if (time_read != null && !time_read.isEmpty()) read = Integer.parseInt(time_read);
            if (time_write != null && !time_write.isEmpty()) write = Integer.parseInt(time_write);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return new OkHttpClient.Builder()
                .connectTimeout(conn, TimeUnit.SECONDS)
                .readTimeout(read, TimeUnit.MINUTES)
                .writeTimeout(write, TimeUnit.MINUTES)
                //.sslSocketFactory(sslContext.getSocketFactory())
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        HeaderInterceptor headerInterceptor = new HeaderInterceptor();
                        return headerInterceptor.intercept(chain);
                    }
                });
    }


}