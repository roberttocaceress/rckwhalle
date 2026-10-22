package com.example.rcksuporte05.rcksistemas.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GpsLocationHelper {

    public interface OnLocationResultListener {
        void onLocationReceived(LocationData locationData);
        void onError(String message);
    }

    public static class LocationData {
        public double latitude;
        public double longitude;
        public double altitude;
        public float accuracy;
        public boolean hasLocation;

        public LocationData(double latitude, double longitude, double altitude, float accuracy) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
            this.accuracy = accuracy;
            this.hasLocation = true;
        }

        public LocationData() {
            this.hasLocation = false;
        }
    }

    public static boolean hasLocationPermission(Context context) {
        if (context == null) return false;
        int fineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        return fineLocation == PackageManager.PERMISSION_GRANTED || coarseLocation == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isInternetConnected(Context context) {
        if (context == null) return false;
        try {
            android.net.ConnectivityManager cm = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;
            android.net.NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isGpsEnabled(Context context) {
        if (context == null) return false;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm == null) return false;
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    public static void obterLocalizacaoAtual(Context context, OnLocationResultListener listener) {
        if (context == null || listener == null) return;

        if (!hasLocationPermission(context)) {
            listener.onError("Permissão de localização não concedida.");
            return;
        }

        boolean gpsAtivo = isGpsEnabled(context);
        boolean internetAtiva = isInternetConnected(context);

        // REGRA DE SEGURANÇA: Bloqueia se o aparelho não tem GPS ativo e está sem internet
        if (!gpsAtivo && !internetAtiva) {
            listener.onError("Sem sinal de GPS e sem conexão com a internet.");
            return;
        }

        if (!gpsAtivo) {
            listener.onError("O GPS do aparelho está desativado.");
            return;
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        // Valida se a localização em cache é recente (máximo 10 minutos) e precisa
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            long tempoDecorridoMs = location != null ? (System.currentTimeMillis() - location.getTime()) : Long.MAX_VALUE;
            boolean isRecente = tempoDecorridoMs <= (10 * 60 * 1000); // 10 minutos

            if (location != null && isRecente && location.hasAccuracy() && location.getAccuracy() <= 20.0f) {
                LocationData data = new LocationData(
                        location.getLatitude(),
                        location.getLongitude(),
                        location.getAltitude(),
                        location.getAccuracy()
                );
                listener.onLocationReceived(data);
            } else {
                solicitarAtualizacaoTempoReal(fusedLocationClient, listener);
            }
        }).addOnFailureListener(e -> solicitarAtualizacaoTempoReal(fusedLocationClient, listener));
    }

    @SuppressLint("MissingPermission")
    private static void solicitarAtualizacaoTempoReal(FusedLocationProviderClient fusedLocationClient, OnLocationResultListener listener) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setNumUpdates(1);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location loc = locationResult.getLastLocation();
                    float accuracy = loc.hasAccuracy() ? loc.getAccuracy() : 5.0f;
                    LocationData data = new LocationData(
                            loc.getLatitude(),
                            loc.getLongitude(),
                            loc.getAltitude(),
                            accuracy
                    );
                    listener.onLocationReceived(data);
                } else {
                    listener.onError("Não foi possível obter o sinal do GPS.");
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public static String getGoogleStaticMapUrl(Context context, double latitude, double longitude, int width, int height) {
        String apiKey = "";
        try {
            int resId = context.getResources().getIdentifier("google_maps_key", "string", context.getPackageName());
            if (resId != 0) {
                apiKey = context.getString(resId);
            }
        } catch (Exception ignored) {}

        if (apiKey != null && !apiKey.trim().isEmpty()) {
            return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude +
                    "&zoom=16&size=" + width + "x" + height +
                    "&markers=color:red%7C" + latitude + "," + longitude +
                    "&key=" + apiKey;
        }
        return getOsmStaticMapUrl(latitude, longitude, width, height);
    }

    public static String getOsmStaticMapUrl(double latitude, double longitude, int width, int height) {
        return "https://static-maps.yandex.ru/1.x/?lang=pt_BR&ll=" + longitude + "," + latitude +
                "&z=16&l=map&size=" + width + "," + height +
                "&pt=" + longitude + "," + latitude + ",pm2rdm";
    }
}
