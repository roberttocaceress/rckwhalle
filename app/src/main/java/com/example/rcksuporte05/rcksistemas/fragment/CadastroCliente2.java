package com.example.rcksuporte05.rcksistemas.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BuildConfig;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PaisesDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityEstado;
import com.example.rcksuporte05.rcksistemas.activity.ActivityMunicipio;
import com.example.rcksuporte05.rcksistemas.api.ApiGeocoder;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Endereco;
import com.example.rcksuporte05.rcksistemas.model.Pais;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.example.rcksuporte05.rcksistemas.util.classesGeocoderUtil.RespostaGeocoder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroCliente2 extends Fragment implements  LocationListener {
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 50.00f;
    private static final long MIN_TIME_BW_UPDATES = 10000;
    @BindView(R.id.edtNumero)
    public EditText edtNumero;
    @BindView(R.id.edtBairro)
    public EditText edtBairro;
    @BindView(R.id.edtCep)
    public EditText edtCep;

    @BindView(R.id.edtcHECKIN)
    public EditText edtcHECKIN;

    @BindView(R.id.edtEndereco)
    public EditText edtEndereco;
    @BindView(R.id.edtPais)
    Spinner edtPais;
    @BindView(R.id.edtUf)
    Spinner edtUf;
    @BindView(R.id.edtMunicipio)
    Spinner edtMunicipio;
    @BindView(R.id.edtComplemento)
    EditText edtComplemento;
    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    @BindView(R.id.progress_splash)
    ProgressBar progress_bar;
    @BindView(R.id.progress_title)
    TextView progress_title;

    private int[] listaUf = {R.array.AC,
            R.array.AL,
            R.array.AM,
            R.array.AP,
            R.array.BA,
            R.array.CE,
            R.array.DF,
            R.array.ES,
            R.array.EX,
            R.array.GO,
            R.array.MA,
            R.array.MG,
            R.array.MS,
            R.array.MT,
            R.array.PA,
            R.array.PB,
            R.array.PE,
            R.array.PI,
            R.array.PR,
            R.array.RJ,
            R.array.RN,
            R.array.RO,
            R.array.RR,
            R.array.RS,
            R.array.SC,
            R.array.SE,
            R.array.SP,
            R.array.TO};

    private ArrayAdapter municipioAdapter;
    private ArrayAdapter ufAdapter;
    private ArrayAdapter<Pais> paisAdapter;
    private List<Pais> listaPaises = new ArrayList<>();
    private View view;
    private DBHelper db;
    private int indMunicipio = -1;

    private Location mLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private RespostaGeocoder respostaGeocoder;
    private CadastroCliente2 context;


    //GPSTracker gps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cadastro_cliente2_2026, container, false);
        ButterKnife.bind(this, view);
        context = this;
        db = new DBHelper(getActivity());
        Cliente xcliente = ClienteHelper.getCliente();

        try {
            PaisesDAO paisesDAO = new PaisesDAO(db);
            listaPaises = paisesDAO.getLista();
            paisAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, listaPaises);
            edtPais.setAdapter(paisAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ufAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
        edtUf.setAdapter(ufAdapter);
        edtUf.setClickable(false);
        edtUf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Intent intent = new Intent(getActivity(), ActivityEstado.class);
                    intent.putExtra("cliente", 1);
                    startActivity(intent);
                }
                return true;
            }
        });

        edtMunicipio.setClickable(false);
        edtMunicipio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Intent intent = new Intent(getActivity(), ActivityMunicipio.class);
                    intent.putExtra("cliente", 1);
                    startActivity(intent);
                }
                return true;
            }
        });

        edtCep.setClickable(true);
        edtCep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtCep.getRight() - edtCep.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showHideProgressBar(true);
                        pesquisaCep(edtCep.getText().toString().trim());
                        return true;
                    }
                }
                return false;
            }
        });

        edtCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edtCep.getText().toString().length() == 8) {
                        pesquisaCep(edtCep.getText().toString().trim());
                        return;
                    }
                    if (edtCep.getText().toString().length() >= 9)
                        Toast.makeText(getActivity(), "Atenção!!\nCEP informádo inválido", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });


        edtcHECKIN.setClickable(true);
        edtcHECKIN.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //EnableGPSAutoMatically();
                    showHideProgressBar(true);
                    getLocationWithCheckNetworkAndGPS();
                }
                return false;
            }
        });


        if (listaPaises.size() > 0) {
            if (ClienteHelper.getCliente().getId_pais() > 0) {
                for (int i = 0; listaPaises.size() > i; i++)
                    if (listaPaises.get(i).getId_pais().equals(String.valueOf(ClienteHelper.getCliente().getId_pais()))) {
                        edtPais.setSelection(i);
                        ClienteHelper.setPosicaoPais(i);
                        break;
                    }
            } else {
                for (int i = 0; listaPaises.size() > i; i++)
                    if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                        edtPais.setSelection(i);
                        ClienteHelper.setPosicaoPais(i);
                        break;
                    }
            }
        }

        if (ClienteHelper.getCliente().getEndereco_uf() != null && !ClienteHelper.getCliente().getEndereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++)
                if (ClienteHelper.getCliente().getEndereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    edtUf.setSelection(i);
                    ClienteHelper.setPosicaoUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    edtMunicipio.setAdapter(municipioAdapter);
                    break;
                }
        } else if (ClienteHelper.getVendedor().getEndereco_uf() != null && !ClienteHelper.getVendedor().getEndereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++)
                if (ClienteHelper.getVendedor().getEndereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    edtUf.setSelection(i);
                    ClienteHelper.setPosicaoUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    edtMunicipio.setAdapter(municipioAdapter);
                    break;
                }

        }

        if (ClienteHelper.getCliente().getNome_municipio() != null && !ClienteHelper.getCliente().getNome_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()]).length > i; i++)
                if (ClienteHelper.getCliente().getNome_municipio().equals(getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()])[i])) {
                    edtMunicipio.setSelection(i);
                    ClienteHelper.setPosicaoMunicipio(i);
                    break;
                }
        } else if (ClienteHelper.getVendedor().getNome_municipio() != null && !ClienteHelper.getVendedor().getNome_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()]).length > i; i++)
                if (ClienteHelper.getVendedor().getNome_municipio().equals(getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()])[i])) {
                    //edtMunicipio.setSelection(i);
                    //ClienteHelper.setPosicaoMunicipio(i);
                    break;
                }
        }

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addDadosFrame();
                    boolean validado = true;
                    if (ClienteHelper.getCliente().getEndereco() == null || ClienteHelper.getCliente().getEndereco().trim().isEmpty()) {
                        edtEndereco.requestFocus();
                        edtEndereco.setError("Campo Obrigatorio");
                        validado = false;
                    }

                    if (ClienteHelper.getCliente().getEndereco_numero() == null || ClienteHelper.getCliente().getEndereco_numero().trim().isEmpty()) {
                        edtNumero.requestFocus();
                        edtNumero.setError("Campo Obrigatorio");
                        validado = false;
                    }

                    if (ClienteHelper.getCliente().getEndereco_bairro() == null || ClienteHelper.getCliente().getEndereco_bairro().trim().isEmpty()) {
                        edtBairro.requestFocus();
                        edtBairro.setError("Campo Obrigatorio");
                        validado = false;
                    }

                    if (ClienteHelper.getCliente().getEndereco_cep() == null || ClienteHelper.getCliente().getEndereco_cep().trim().isEmpty()) {
                        edtCep.requestFocus();
                        edtCep.setError("Campo Obrigatorio");
                        validado = false;
                    } else {
                        if (ClienteHelper.getCliente().getEndereco_cep().replaceAll("^[0-9]", "").length() >= 8) {
                            edtCep.requestFocus();
                            edtCep.setError("Tamanho maximo é de 8 caracteres");
                            validado = false;
                        }
                    }

                    if (edtMunicipio.getSelectedItemPosition() <= 0) {
                        Toast toast = Toast.makeText(getActivity(), "Atenção!!\nSelecione o municipio", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        validado = false;

                    }

                    if (validado) {
                        if (ClienteHelper.getCliente().getFinalizado().equals("S"))
                            ClienteHelper.getCliente().setAlterado("S");
                        ClienteDAO clienteDAO = new ClienteDAO(db);
                        clienteDAO.update(ClienteHelper.getCliente());
                        ClienteHelper.moveTela(2);
                    }
                }
            });
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            edtNumero.setFocusable(false);
            edtBairro.setFocusable(false);
            edtCep.setFocusable(false);
            edtEndereco.setFocusable(false);
            edtPais.setEnabled(false);
            edtUf.setEnabled(false);
            edtMunicipio.setEnabled(false);
            edtComplemento.setFocusable(false);

            if (ClienteHelper.getCliente().getEndereco_cep() != null) {
                String cep = ClienteHelper.getCliente().getEndereco_cep().trim().replaceAll("[^0-9]", "");
                edtCep.setText(cep);
            }
        } else {
            if (ClienteHelper.getCliente().getId_pais() <= 0) {
                for (int i = 0; listaPaises.size() > i; i++)
                    if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                        edtPais.setSelection(i);
                        break;
                    }
            }

            edtMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    if (indMunicipio >= 1 && i <= 0) {
                        Toast toast = Toast.makeText(getActivity(), "Atenção!!\nSelecione o municipio", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        ClienteHelper.setPosicaoMunicipio(i);
                    }
                    indMunicipio = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edtUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (ClienteHelper.getPosicaoMunicipio() > -1 && edtUf.getSelectedItemPosition() != ClienteHelper.getPosicaoUf())
                        ClienteHelper.setPosicaoMunicipio(0);
                    if (paisAdapter != null)
                        if (paisAdapter.getItem(edtPais.getSelectedItemPosition()).getId_pais().equals("1058")) {
                            municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[position]));
                            edtMunicipio.setAdapter(municipioAdapter);
                        }
                    ClienteHelper.setPosicaoUf(position);
                    try {
                        edtMunicipio.setSelection(ClienteHelper.getPosicaoMunicipio());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edtPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!paisAdapter.getItem(position).getId_pais().equals("1058")) {
                        edtUf.setSelection(8);
                        ClienteHelper.setPosicaoUf(8);
                        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[8]));
                        ClienteHelper.setPosicaoMunicipio(0);
                        edtMunicipio.setAdapter(municipioAdapter);
                    } else {
                        ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
                        edtUf.setAdapter(ufAdapter);
                    }
                    ClienteHelper.setPosicaoPais(position);
                    try {
                        edtUf.setSelection(ClienteHelper.getPosicaoUf());
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (ClienteHelper.getCliente().getEndereco_uf() != null && !ClienteHelper.getCliente().getEndereco_uf().trim().isEmpty())
                            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++)
                                if (ClienteHelper.getCliente().getEndereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                                    edtUf.setSelection(i);
                                    ClienteHelper.setPosicaoUf(i);
                                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                                    edtMunicipio.setAdapter(municipioAdapter);
                                    break;
                                }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (ClienteHelper.getCliente().getEndereco_cep() != null)
                edtCep.setText(ClienteHelper.getCliente().getEndereco_cep());
        }

        if (ClienteHelper.getCliente().getEndereco() != null)
            edtEndereco.setText(ClienteHelper.getCliente().getEndereco());
        if (ClienteHelper.getCliente().getEndereco_numero() != null)
            edtNumero.setText(ClienteHelper.getCliente().getEndereco_numero());
        if (ClienteHelper.getCliente().getEndereco_bairro() != null)
            edtBairro.setText(ClienteHelper.getCliente().getEndereco_bairro());
        if (ClienteHelper.getCliente().getEndereco_complemento() != null)
            edtComplemento.setText(ClienteHelper.getCliente().getEndereco_complemento());
        try {
            if (!ClienteHelper.getCliente().getLatitude().isEmpty() && !ClienteHelper.getCliente().getLongitude().isEmpty()) {
                edtcHECKIN.setText("L " + ClienteHelper.getCliente().getLatitude() + " * L " + ClienteHelper.getCliente().getLongitude());
            }
        } catch (NullPointerException e) {
        }
        System.gc();
        ClienteHelper.setCadastroCliente2(this);
        edtCep.requestFocus();
        return view;
    }

    private void pesquisaCep(String cep) {
        if (cep.trim().length() == 8) {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            Utilitaria util = new Utilitaria();
            String webService = "https://viacep.com.br/ws/";
            int codigoSucesso = 200;
            String urlParaChamada = webService + cep + "/json";

            try {
                URL url = new URL(urlParaChamada);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                if (conexao.getResponseCode() != codigoSucesso) {
                    showHideProgressBar(false);
                    throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

                }
                BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
                String jsonEmString = util.converteJsonEmString(resposta);

                Gson gson = new Gson();
                Endereco endereco = gson.fromJson(jsonEmString, Endereco.class);
                edtEndereco.setText(endereco.getLogradouro().toUpperCase());
                edtEndereco.setError(null);
                edtBairro.setError(null);
                edtBairro.setText(endereco.getBairro().toUpperCase());
                for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++)
                    if (endereco.getUf().toUpperCase().equalsIgnoreCase(getResources().getStringArray(R.array.uf)[i])) {
                        edtUf.setSelection(i);
                        ClienteHelper.setPosicaoUf(i);
                        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                        edtMunicipio.setAdapter(municipioAdapter);
                        break;
                    }
                for (int i = 0; getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()]).length > i; i++)
                    if (endereco.getLocalidade().toUpperCase().equalsIgnoreCase(getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()])[i])) {
                        edtMunicipio.setSelection(i);
                        ClienteHelper.setPosicaoMunicipio(i);
                        break;
                    }
                showHideProgressBar(false);
            } catch (Exception e) {
                showHideProgressBar(false);
                Toast.makeText(getContext(), "Falha ao consultar o CEP informado" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            showHideProgressBar(false);
            Toast.makeText(getContext(), "Tamanho do cep informado incorreto!", Toast.LENGTH_SHORT).show();
        }


    }
    /*
    private void EnableGPSAutoMatically() {

        if (getActivity() != null) {
            if (getContext() != null) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                GoogleApiClient googleApiClient = null;
                if (googleApiClient == null) {
                    googleApiClient = new GoogleApiClient.Builder(getContext())
                            .addApi(LocationServices.API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this).build();

                    googleApiClient.connect();

                    LocationRequest locationRequest = LocationRequest.create();

                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(30 * 1000);
                    locationRequest.setFastestInterval(5 * 1000);

                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);

                    // **************************
                    builder.setAlwaysShow(true); // this is the key ingredient
                    // **************************

                    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                            .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            final LocationSettingsStates state = result
                                    .getLocationSettingsStates();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    pegarUltimaLocalizacao();
                                    // All location settings are satisfied. The client can
                                    // initialize location
                                    // requests here.
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied. But could be
                                    // fixed by showing the user
                                    // a dialog.
                                    try {
                                        // Show the dialog by calling
                                        // startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        status.startResolutionForResult(getActivity(), 1);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have
                                    // no way to fix the
                                    // settings so we won't show the dialog.
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }
    */

    @SuppressLint("NewApi")
    public void pegarUltimaLocalizacao() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            String autorizado = "S";
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            //Tenho a última localização conhecida. Em algumas situações raras, isso pode ser nulo.
                            if (location != null) {
                                Toast.makeText(getContext(), "LOCATION", Toast.LENGTH_SHORT).show();
                                mLocation = location;
                                getGeocoder();
                            } else {
                                Toast.makeText(getContext(), "LOCATION NULL", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            //progress.dismiss();
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    public void getGeocoder() {
        Rotas rotas = ApiGeocoder.buildRetrofit();
        Call<RespostaGeocoder> call = null;

        if (mLocation != null) {
            call = rotas.getGeocoder(String.valueOf(mLocation.getLatitude()) + "," + String.valueOf(mLocation.getLongitude()), true, "pt-BR");
        }

        call.enqueue(new Callback<RespostaGeocoder>() {
            @Override
            public void onResponse(Call<RespostaGeocoder> call, Response<RespostaGeocoder> response) {
                respostaGeocoder = response.body();
                if (response.body().getResult().size() > 0) {
                    //txtEndereco.setVisibility(View.VISIBLE);
                    //txtEndereco.setText(respostaGeocoder.getResult().get(1).getFormattedAddress());
                } else {
                    // txtEndereco.setVisibility(View.VISIBLE);
                    if (mLocation != null)
                        //txtEndereco.setText(String.valueOf(mLocation.getLatitude()) + "\n" + String.valueOf(mLocation.getLongitude()));
                        Toast.makeText(getActivity(), "Endereço não encontrado! somente latitude e longitude", Toast.LENGTH_LONG).show();
                }
                //progress.dismiss();
            }

            @Override
            public void onFailure(Call<RespostaGeocoder> call, Throwable t) {
                //progress.dismiss();
                Toast.makeText(getActivity(), "Falha ao requisitar", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onResume() {
        if (ClienteHelper.getPosicaoUf() > 0) {
            edtUf.setSelection(ClienteHelper.getPosicaoUf());
            municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[ClienteHelper.getPosicaoUf()]));
            edtMunicipio.setAdapter(municipioAdapter);
        }
        if (ClienteHelper.getPosicaoMunicipio() > 0)
            edtMunicipio.setSelection(ClienteHelper.getPosicaoMunicipio());
        try {
            if (edtEndereco.getText().toString().isEmpty())
                edtCep.requestFocus();
        } catch (NullPointerException e) {
            edtCep.requestFocus();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        edtCep.requestFocus();
        super.onResume();
    }

    @SuppressLint("ResourceType")
    public void addDadosFrame() {
        if (!edtNumero.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco_numero(edtNumero.getText().toString());
        else
            ClienteHelper.getCliente().setEndereco_numero(null);
        if (!edtBairro.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco_bairro(edtBairro.getText().toString().toUpperCase());
        else
            ClienteHelper.getCliente().setEndereco_bairro(null);
        if (!edtCep.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco_cep(edtCep.getText().toString());
        else
            ClienteHelper.getCliente().setEndereco_cep(null);
        if (!edtEndereco.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco(edtEndereco.getText().toString().toUpperCase());
        else
            ClienteHelper.getCliente().setEndereco(null);
        if (!edtComplemento.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco_complemento(edtComplemento.getText().toString().toUpperCase());
        try {
            ClienteHelper.getCliente().setId_pais(Integer.parseInt(listaPaises.get(edtPais.getSelectedItemPosition()).getId_pais()));
            ClienteHelper.getCliente().setEndereco_uf(ufAdapter.getItem(edtUf.getSelectedItemPosition()).toString());
            ClienteHelper.getCliente().setNome_municipio(municipioAdapter.getItem(edtMunicipio.getSelectedItemPosition()).toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //progress = new ProgressDialog(getContext());
                //progress.setMessage("Fazendo Check-in");
                //progress.setTitle("Aguarde");
                //progress.show();
                pegarUltimaLocalizacao();
            } else
                Toast.makeText(getContext(), "Sem a permissão, função indisponivel!", Toast.LENGTH_LONG).show();
        }
    }

    public Location getLocationWithCheckNetworkAndGPS() {
        LocationManager lm = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        boolean isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkLocationEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location networkLoacation = null, gpsLocation = null, finalLoc = null;
        if (isGpsEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    showHideProgressBar(false);
                    showAlertInfoApp();
                    return null;
                }
            }
        } else {
            showHideProgressBar(false);
            Toast.makeText( getContext(),
                    "GPS não habilitado!!",
                    Toast.LENGTH_SHORT).show();
            return finalLoc;
        }

        lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        Log.d("GPS Enabled", "GPS Enabled");



        gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (isNetworkLocationEnabled)
            networkLoacation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation != null && networkLoacation != null) {
            if (gpsLocation.getAccuracy() > networkLoacation.getAccuracy()) {
                ClienteHelper.getCliente().setLatitude(String.valueOf(networkLoacation.getLatitude()));
                ClienteHelper.getCliente().setLongitude(String.valueOf(networkLoacation.getLongitude()));
                edtcHECKIN.setText("L " + String.valueOf(networkLoacation.getLatitude()) + " * L " + String.valueOf(networkLoacation.getLongitude()));
                showHideProgressBar(false);
                return finalLoc = networkLoacation;
            } else {
                ClienteHelper.getCliente().setLatitude(String.valueOf(gpsLocation.getLatitude()));
                ClienteHelper.getCliente().setLongitude(String.valueOf(gpsLocation.getLongitude()));
                edtcHECKIN.setText("L " + String.valueOf(gpsLocation.getLatitude()) + " * L " + String.valueOf(gpsLocation.getLongitude()));
                showHideProgressBar(false);
                return finalLoc = gpsLocation;
            }
        } else {
            try {
                if (gpsLocation != null) {
                    ClienteHelper.getCliente().setLatitude(String.valueOf(gpsLocation.getLatitude()));
                    ClienteHelper.getCliente().setLongitude(String.valueOf(gpsLocation.getLongitude()));
                    edtcHECKIN.setText("L " + String.valueOf(gpsLocation.getLatitude()) + " * L " + String.valueOf(gpsLocation.getLongitude()));
                    showHideProgressBar(false);
                    return finalLoc = gpsLocation;
                } else if (networkLoacation != null) {
                    ClienteHelper.getCliente().setLatitude(String.valueOf(networkLoacation.getLatitude()));
                    ClienteHelper.getCliente().setLongitude(String.valueOf(networkLoacation.getLongitude()));
                    edtcHECKIN.setText("L " + String.valueOf(networkLoacation.getLatitude()).trim() + " * L " + String.valueOf(networkLoacation.getLongitude()).trim());
                    showHideProgressBar(false);
                    return finalLoc = networkLoacation;
                }
            } catch ( NullPointerException e) {
                e.printStackTrace();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
        showHideProgressBar(false);
        return finalLoc;
    }

    private void showAlertInfoApp() {
        new AlertDialog.Builder(getActivity())
                .setTitle("RCK SISTEMAS ESPECIFICOS!")
                .setMessage("Aplicativo sem permissão para acesso a localização!\n Gostaria de analisar/conceder permissão de localização para este aplicativo? ")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getActivity(), "Ação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


    private void showHideProgressBar(boolean visivel) {
        if( visivel)
            progress_bar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN );
        progress_bar.setVisibility( visivel ? View.VISIBLE : View.INVISIBLE);
        progress_title.setVisibility(visivel ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
