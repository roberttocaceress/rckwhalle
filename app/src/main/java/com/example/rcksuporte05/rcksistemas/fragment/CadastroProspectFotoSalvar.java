package com.example.rcksuporte05.rcksistemas.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProspectDAO;
import com.example.rcksuporte05.rcksistemas.DAO.VisitaProspectDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityFoto;
import com.example.rcksuporte05.rcksistemas.api.ApiGeocoder;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.util.DatePickerUtil;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;
import com.example.rcksuporte05.rcksistemas.util.classesGeocoderUtil.RespostaGeocoder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCK 03 on 29/01/2018.
 */

public class CadastroProspectFotoSalvar extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static int REQUEST_CODE_IMAGEM_1 = 798;
    static int REQUEST_CODE_IMAGEM_2 = 987;

    @BindView(R.id.edtDataRetorno)
    public EditText edtDataRetorno;

    @BindView(R.id.spTipoVisita)
    Spinner spTipoVisita;

    @BindView(R.id.edtTitulo)
    TextView edtTitulo;

    @BindView(R.id.edtDescricaoAcao)
    EditText edtDescricaoAcao;

    @BindView(R.id.imagemProspect1)
    ImageButton imagemProspect1;

    @BindView(R.id.imagemProspect2)
    ImageButton imagemProspect2;

    @BindView(R.id.btnCheckinVisitaProspect)
    Button btnCheckinVisitaProspect;

    @BindView(R.id.txtLatitudeProspect)
    TextView txtLatitudeProspect;

    @BindView(R.id.txtLongitudeProspect)
    TextView txtLongitudeProspect;

    @BindView(R.id.txtChekinEnderecoProspect)
    TextView txtChekinEnderecoProspect;

    @BindView(R.id.btnContinuar)
    Button btnContinuar;
    Bitmap mImagem1;
    Bitmap mImagem2;
    RespostaGeocoder respostaGeocoder;
    Location mLocation;
    ProgressDialog progress;
    private String[] tipoVisita = {"Presencial", "Telefone"};
    private Uri uri;
    private DBHelper db;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean verificaObrigatorios;

    private Bitmap miniatura;
    private Bitmap bitmap;
    private File file ;
    Uri outputFileUri ;
    private ActionMode actionMode;
    private ProspectDAO prospectDAO;
    private VisitaProspectDAO visitaProspectDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_foto_salvar, container, false);
        ButterKnife.bind(this, view);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        db = new DBHelper(getContext());
        visitaProspectDAO = new VisitaProspectDAO(db);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, tipoVisita);
        spTipoVisita.setAdapter(adapter);
        imagemProspect1.setImageResource(R.mipmap.ic_add_imagem);
        imagemProspect2.setImageResource(R.mipmap.ic_add_imagem);
        txtLatitudeProspect.setVisibility(View.GONE);
        txtLongitudeProspect.setVisibility(View.GONE);
        txtChekinEnderecoProspect.setVisibility(View.GONE);

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            spTipoVisita.setEnabled(false);
            edtTitulo.setFocusable(false);
            edtDescricaoAcao.setFocusable(false);
            edtDataRetorno.setFocusable(false);
            imagemProspect1.setEnabled(false);
            imagemProspect2.setEnabled(false);
            btnCheckinVisitaProspect.setClickable(false);

        } else {
            edtDataRetorno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerUtil.mostraDatePickerDialog(getContext(), edtDataRetorno);
                    edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext);
                }
            });
        }

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insereDadosDaFrame();
                    boolean validado = true;
                    if (ProspectHelper.getProspect().getDataRetorno() == null || ProspectHelper.getProspect().getDataRetorno().trim().isEmpty()) {
                        edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext_erro);
                        Toast.makeText(getActivity(), "Informe a data de retorno", Toast.LENGTH_LONG).show();
                        validado = false;
                    } else {
                        Calendar dataAtual = new GregorianCalendar();
                        Calendar dataRetorno = new GregorianCalendar();
                        Date date = new Date();
                        dataAtual.setTime(date);
                        try {
                            dataRetorno.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ProspectHelper.getProspect().getDataRetorno()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (dataAtual.getTime().after(dataRetorno.getTime())) {
                            edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext_erro);
                            Toast.makeText(getActivity(), "A data deve ser posterior ao dia de hoje!", Toast.LENGTH_LONG).show();
                            validado = false;
                        }

                        if (ProspectHelper.getProspect().getLatitude() == null && ProspectHelper.getProspect().getLongitude() == null) {
                            Toast.makeText(getActivity(), "Fazer Check-in é obrigatório", Toast.LENGTH_LONG).show();
                            validado = false;
                        }
                    }

                    VisitaProspect visita;
                    if (VisitaHelper.getVisitaProspect() != null)
                        visita = VisitaHelper.getVisitaProspect();
                    else
                        visita = new VisitaProspect();
                    verificaObrigatorios = true;

                    visita.setProspect(VisitaHelper.getProspect());
                    visita.setDataVisita(db.pegaDataAtual());
                    visita.setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
                    visita.setTipoContato(tipoVisita[spTipoVisita.getSelectedItemPosition()]);

                    if (spTipoVisita.getSelectedItemPosition() == 0) {
                        String dataRetorno = dataRetorno();
                        if (dataRetorno != null)
                            visita.setDataRetorno(dataRetorno);
                        else {
                            edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext_erro);
                            Toast.makeText(getActivity(), "A data é obrigatoria", Toast.LENGTH_SHORT).show();
                        }

                        if (mLocation != null) {
                            visita.setLongitude(String.valueOf(mLocation.getLongitude()));
                            visita.setLatitude(String.valueOf(mLocation.getLatitude()));
                        }

                    } else {
                        String dataRetorno = dataRetorno();
                        if (dataRetorno != null)
                            visita.setDataRetorno(dataRetorno);
                    }

                    if (edtTitulo.getText() != null && !edtTitulo.getText().toString().trim().equals(""))
                        visita.setTitulo(edtTitulo.getText().toString());
                    else {
                        edtTitulo.setError("Campo obrigatorio");
                        edtTitulo.requestFocus();
                        verificaObrigatorios = false;
                    }

                    if (edtDescricaoAcao.getText() != null && !edtDescricaoAcao.getText().toString().trim().equals(""))
                        visita.setDescricaoVisita(edtDescricaoAcao.getText().toString());
                    else {
                        edtDescricaoAcao.setError("Campo obrigatorio");
                        edtDescricaoAcao.requestFocus();
                        verificaObrigatorios = false;
                    }

                    if (validado && verificaObrigatorios) {
                        if (ProspectHelper.getProspect().getId_prospect() != null && !ProspectHelper.getProspect().getId_prospect().trim().isEmpty()) {
                            //System.out.println("Esse prospect já tem id!");
                        } else {
                            ProspectHelper.getProspect().setId_prospect(String.valueOf(db.contagem("SELECT MAX(ID_PROSPECT) FROM TBL_PROSPECT;") + 1));
                        }
                        visita.setProspect(ProspectHelper.getProspect());
                        //visita = db.atualizaTBL_VISITA_PROSPECT(visita);
                        visita = visitaProspectDAO.addUpdate(visita);
                        if (visita != null)
                            ProspectHelper.getProspect().setIdPrimeiraVisita(Integer.parseInt(visita.getIdVisita()));
                        else
                            //System.out.println("Falha ao salvar");

                        if (ProspectHelper.getProspect().getFotoPrincipalBase64() != null) {
                            visita.setFotoPrincipalBase64(ProspectHelper.getProspect().getFotoPrincipalBase64());
                            visita.getFotoPrincipalBase64().setIdCadastro(Integer.parseInt(visita.getIdVisita()));
                            visita.getFotoPrincipalBase64().setPrincipal("S");
                            visita.getFotoPrincipalBase64().setIdEntidade(11);
                            visita.getFotoPrincipalBase64().setExcluido("N");
                        }

                        if (ProspectHelper.getProspect().getFotoSecundariaBase64() != null) {
                            visita.setFotoSecundariaBase64(ProspectHelper.getProspect().getFotoSecundariaBase64());
                            visita.getFotoSecundariaBase64().setIdCadastro(Integer.parseInt(visita.getIdVisita()));
                            visita.getFotoSecundariaBase64().setPrincipal("N");
                            visita.getFotoSecundariaBase64().setIdEntidade(11);
                            visita.getFotoSecundariaBase64().setExcluido("N");
                        }
                        visitaProspectDAO.addUpdate(visita);
                        //db.atualizaTBL_VISITA_PROSPECT(visita);

                        if (ProspectHelper.getProspect().getFotoPrincipalBase64() != null) {
                            ProspectHelper.getProspect().getFotoPrincipalBase64().setIdCadastro(Integer.parseInt(ProspectHelper.getProspect().getId_prospect()));
                            ProspectHelper.getProspect().getFotoPrincipalBase64().setPrincipal("S");
                            ProspectHelper.getProspect().getFotoPrincipalBase64().setIdEntidade(10);
                            ProspectHelper.getProspect().getFotoPrincipalBase64().setExcluido("N");
                        }

                        if (ProspectHelper.getProspect().getFotoSecundariaBase64() != null) {
                            ProspectHelper.getProspect().getFotoSecundariaBase64().setIdCadastro(Integer.parseInt(ProspectHelper.getProspect().getId_prospect()));
                            ProspectHelper.getProspect().getFotoSecundariaBase64().setPrincipal("N");
                            ProspectHelper.getProspect().getFotoSecundariaBase64().setIdEntidade(10);
                            ProspectHelper.getProspect().getFotoSecundariaBase64().setExcluido("N");
                        }

                        ProspectHelper.getProspect().setProspectSalvo("N");
                        ProspectHelper.getProspect().setIdEmpresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                        ProspectHelper.getProspect().setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
                        ProspectHelper.getProspect().setUsuario_nome(UsuarioHelper.getUsuario().getNome_usuario());
                        try {
                            ProspectHelper.getProspect().setUsuario_data(new SimpleDateFormat("dd/MM/yyyy")
                                    .format(new SimpleDateFormat("yyyy-MM-dd")
                                            .parse(db.pegaDataAtual())));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        prospectDAO = new ProspectDAO(db);
                        prospectDAO.addUpdate(ProspectHelper.getProspect());
                        //db.atualizarTBL_PROSPECT(ProspectHelper.getProspect());

                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Atenção");
                        alert.setMessage("Tem certeza que deseja salvar esse prospect PERMANENTEMENTE?");
                        alert.setNegativeButton("NÃO", null);
                        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProspectHelper.getProspect().setProspectSalvo("S");
                                ProspectHelper.getProspect().setIdEmpresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                ProspectHelper.getProspect().setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
                                ProspectHelper.getProspect().setUsuario_nome(UsuarioHelper.getUsuario().getNome_usuario());
                                ProspectHelper.getProspect().setUsuario_data(db.pegaDataAtual());
                                ProspectHelper.getProspect().setIdVendedor(Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor()));
                                prospectDAO.addUpdate(ProspectHelper.getProspect());
                                //db.atualizarTBL_PROSPECT(ProspectHelper.getProspect());
                                getActivity().finish();
                            }
                        });
                        alert.show();
                    }
                }
            });
        }
        insereDadosNaTela();
        ProspectHelper.setCadastroProspectFotoSalvar(this);
        return view;
    }

    public void insereDadosDaFrame() {
        try {
            ProspectHelper.getProspect().setDataRetorno(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(edtDataRetorno.getText().toString().trim())));
        } catch (ParseException e) {
        }

        try {
            ProspectHelper.setImagem1(mImagem1);
        } catch (Exception e) {
        }

        try {
            ProspectHelper.setImagem2(mImagem2);
        } catch (Exception e) {
        }

        if (mLocation != null) {
            ProspectHelper.getProspect().setLatitude(String.valueOf(mLocation.getLatitude()));
            ProspectHelper.getProspect().setLongitude(String.valueOf(mLocation.getLongitude()));
        }
    }

    public void insereDadosNaTela() {
        try {
            if (ProspectHelper.getProspect().getDataRetorno() != null && !ProspectHelper.getProspect().getDataRetorno().trim().isEmpty())
                edtDataRetorno.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(ProspectHelper.getProspect().getDataRetorno())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ProspectHelper.getImagem1() != null) {
            mImagem1 = ProspectHelper.getImagem1();
            imagemProspect1.setImageBitmap(ProspectHelper.getImagem1());
        } else {
            if ( ProspectHelper.getProspect().getFotoPrincipalBase64() != null ) {
                try {
                     byte[] data = Base64.decode(ProspectHelper.getProspect().getFotoPrincipalBase64().getAnexo(), Base64.NO_WRAP);
                     mImagem1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                     ProspectHelper.setImagem1(mImagem1);
                     imagemProspect1.setImageBitmap(Bitmap.createScaledBitmap(mImagem1, 220, 230, false));
                } catch (Exception e) {
                     imagemProspect1.setImageResource(R.drawable.ic_image_gallery);
                     Toast.makeText(getContext(), "Falha ao visualizar a imagem cadastrada!!  --" + e.toString() + "--)", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                imagemProspect1.setImageResource(R.drawable.ic_image_gallery);
                Toast.makeText(getContext(), "Imagem 1 ainda não cadastrada!!", Toast.LENGTH_LONG).show();
            }
        }

        if (ProspectHelper.getImagem2() != null) {
            mImagem2 = ProspectHelper.getImagem2();
            imagemProspect2.setImageBitmap(ProspectHelper.getImagem2());
        } else {
            if (ProspectHelper.getProspect().getFotoSecundariaBase64() != null)
                try {
                    byte[] data2 = Base64.decode(ProspectHelper.getProspect().getFotoSecundariaBase64().getAnexo(), Base64.NO_WRAP);
                    mImagem2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);
                    ProspectHelper.setImagem2(mImagem2);
                    imagemProspect2.setImageBitmap(Bitmap.createScaledBitmap(mImagem2, 220, 230, false));
                } catch (Exception e) {
                    imagemProspect2.setImageResource(R.drawable.ic_image_gallery);
                    Toast.makeText(getContext(), "Falha ao visualizar a imagem cadastrada!!  --" + e.toString() + "--)", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            else {
                imagemProspect2.setImageResource(R.drawable.ic_image_gallery);
                Toast.makeText(getContext(), "Imagem 2 ainda não cadastrada!!", Toast.LENGTH_LONG).show();
            }
        }

        if (ProspectHelper.getCheckin() != null) {
            txtChekinEnderecoProspect.setVisibility(View.VISIBLE);
            txtChekinEnderecoProspect.setText(ProspectHelper.getCheckin());
        } else if (ProspectHelper.getLocalizacao() != null) {
            txtLatitudeProspect.setVisibility(View.VISIBLE);
            txtLongitudeProspect.setVisibility(View.VISIBLE);
            mLocation = ProspectHelper.getLocalizacao();
            progress = new ProgressDialog(getContext());
            progress.setMessage("Requisitando Endereço");
            progress.setTitle("Aguarde");
            progress.show();
            getGeocoder();
        } else if (ProspectHelper.getProspect().getLatitude() != null && ProspectHelper.getProspect().getLongitude() != null) {
            progress = new ProgressDialog(getContext());
            progress.setMessage("Requisitando Endereço");
            progress.setTitle("Aguarde");
            progress.show();
            getGeocoder();
        }

        if (VisitaHelper.getVisitaProspect() != null) {
            edtTitulo.setText(VisitaHelper.getVisitaProspect().getTitulo());
            edtDescricaoAcao.setText(VisitaHelper.getVisitaProspect().getDescricaoVisita());

            if (VisitaHelper.getVisitaProspect().getTipoContato() != null && VisitaHelper.getVisitaProspect().getTipoContato().equals(tipoVisita[0]))
                spTipoVisita.setSelection(0);
            else
                spTipoVisita.setSelection(1);

        }
    }

    @OnClick(R.id.imagemProspect1)
    public void chamarGaleria() {
        //final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        if (ProspectHelper.getProspect().getFotoPrincipalBase64() != null)
            showMsgExcluirAltera(R.id.imagemProspect1, "Deseja alterar ou excluir a imagem selecionada?)");
        else
            showMsgLocalArquivo(R.id.imagemProspect1, "Selecione o local para captura da imagem/arquivo)");
    }

    @OnClick(R.id.imagemProspect2)
    public void chamarGaleria2() {
        //AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        if ( ProspectHelper.getProspect().getFotoSecundariaBase64() != null) {
            showMsgExcluirAltera(R.id.imagemProspect2, "Deseja alterar ou excluir a imagem selecionada?)");
        } else {
            showMsgLocalArquivo(R.id.imagemProspect2, "Selecione o local para captura da imagem/arquivo)");
        }
    }

    @OnClick(R.id.btnCheckinVisitaProspect)
    public void checkin() {
        progress = new ProgressDialog(getContext());
        progress.setMessage("Fazendo Check-in");
        progress.setTitle("Aguarde");
        progress.show();
        EnableGPSAutoMatically();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                getActivity().sendBroadcast(novaIntent);
                Intent intent = new Intent(getActivity(), ActivityFoto.class);
                try {
                    imagemProspect1.setImageBitmap(FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri)), uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        } else if (requestCode == 456) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                imagemProspect1.setImageBitmap(bitmap);
            }
        } else if (requestCode == REQUEST_CODE_IMAGEM_1 ) {
            if ( REQUEST_CODE_IMAGEM_1 == 799 /*Camera*/) {
                if (resultCode == Activity.RESULT_OK) {
                    progress = new ProgressDialog(getContext());
                    progress.setMessage("Processando Imagen!");
                    progress.setTitle("Aguarde");
                    progress.show();
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);
                        try {
                            imageBitmap = FotoUtil.rotateBitmap(imageBitmap, outputFileUri/*data.getData()*/);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        miniatura = Bitmap.createScaledBitmap(imageBitmap, 220, 230, false);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                        byte[] fotoBinario = outputStream.toByteArray();
                        if (ProspectHelper.getProspect().getFotoPrincipalBase64() == null)
                            ProspectHelper.getProspect().setFotoPrincipalBase64(new CadastroAnexo());
                        ProspectHelper.getProspect().getFotoPrincipalBase64().setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                        ProspectHelper.setImagem1(miniatura);

                        mImagem1 = imageBitmap;
                        imagemProspect1.setImageBitmap(miniatura); // ImageButton, seto a foto como imagem do botão
                        imagemProspect1.setBackgroundColor(Color.TRANSPARENT);
                        progress.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Falha ao obter imagem!!(--" + e.toString() + "--)", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        //fotoBinario = null;
                        miniatura = null;
                        imagemProspect1.setImageBitmap(null); // I
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getContext(), "Captura da imagem cancelada!!!", Toast.LENGTH_LONG).show();
                    //fotoBinario = null;
                    miniatura = null;
                    imagemProspect1.setImageBitmap(null); // I
                    getActivity().finish();
                }
            }else if ( REQUEST_CODE_IMAGEM_1 == 798 /*Galeria*/){
                if (data != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    /*Reduzindo a qualidade da imagem para preservar memoria.
                     * Aqui você pode testar a redução que melhor atende sua necessidade
                     * O codigo original não tinha o 'options'
                     */
                    options.inSampleSize = 2;
                    try {
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(data.getData()), null, options), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    miniatura = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                    byte[] fotoBinario = outputStream.toByteArray();

                    mImagem1 = bitmap;
                    if (ProspectHelper.getProspect().getFotoPrincipalBase64() == null)
                        ProspectHelper.getProspect().setFotoPrincipalBase64(new CadastroAnexo());
                    ProspectHelper.getProspect().getFotoPrincipalBase64().setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                    ProspectHelper.setImagem1(miniatura);

                    mImagem1 = bitmap;
                    imagemProspect1.setImageBitmap(miniatura);
                    imagemProspect1.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    Toast.makeText(getContext(), "Falha ao obter imagem!", Toast.LENGTH_LONG).show();
                    miniatura = null;
                    imagemProspect1.setImageBitmap(null); // I
                    getActivity().finish();
                }
            } else {
                Toast.makeText(getContext(), "Falha ao obter imagem!", Toast.LENGTH_LONG).show();
                miniatura = null;
                imagemProspect1.setImageBitmap(null); // I
                getActivity().finish();
            }

        } else if (requestCode == REQUEST_CODE_IMAGEM_2) {
            if ( REQUEST_CODE_IMAGEM_2 == 799 /*Camera*/) {
                if (resultCode == Activity.RESULT_OK) {
                    progress = new ProgressDialog(getContext());
                    progress.setMessage("Processando Imagen!");
                    progress.setTitle("Aguarde");
                    progress.show();
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);
                        try {
                            imageBitmap = FotoUtil.rotateBitmap(imageBitmap, outputFileUri/*data.getData()*/);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        miniatura = Bitmap.createScaledBitmap(imageBitmap, 220, 230, false);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                        byte[] fotoBinario = outputStream.toByteArray();
                        if (ProspectHelper.getProspect().getFotoSecundariaBase64() == null)
                            ProspectHelper.getProspect().setFotoSecundariaBase64(new CadastroAnexo());
                        ProspectHelper.getProspect().getFotoSecundariaBase64().setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                        ProspectHelper.setImagem1(miniatura);

                        mImagem1 = imageBitmap;
                        imagemProspect2.setImageBitmap(miniatura); // ImageButton, seto a foto como imagem do botão
                        imagemProspect2.setBackgroundColor(Color.TRANSPARENT);
                        progress.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Falha ao obter imagem!!(--" + e.toString() + "--)", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        //fotoBinario = null;
                        miniatura = null;
                        imagemProspect2.setImageBitmap(null); // I
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getContext(), "Captura da imagem cancelada!!!", Toast.LENGTH_LONG).show();
                    //fotoBinario = null;
                    miniatura = null;
                    imagemProspect2.setImageBitmap(null); // I
                    getActivity().finish();
                }
            }else if ( REQUEST_CODE_IMAGEM_2 == 798 /*Galeria*/){
                if (data != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    /*Reduzindo a qualidade da imagem para preservar memoria.
                     * Aqui você pode testar a redução que melhor atende sua necessidade
                     * O codigo original não tinha o 'options'
                     */
                    options.inSampleSize = 2;
                    try {
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(data.getData()), null, options), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    miniatura = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                    byte[] fotoBinario = outputStream.toByteArray();

                    if (ProspectHelper.getProspect().getFotoSecundariaBase64() == null)
                        ProspectHelper.getProspect().setFotoSecundariaBase64(new CadastroAnexo());
                    ProspectHelper.getProspect().getFotoSecundariaBase64().setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                    ProspectHelper.setImagem1(miniatura);

                    mImagem1 = bitmap;
                    imagemProspect2.setImageBitmap(miniatura);
                    imagemProspect2.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    Toast.makeText(getContext(), "Falha ao obter imagem!", Toast.LENGTH_LONG).show();
                    miniatura = null;
                    imagemProspect2.setImageBitmap(null); // I
                    getActivity().finish();
                }
            } else {
                Toast.makeText(getContext(), "Falha ao obter imagem!", Toast.LENGTH_LONG).show();
                miniatura = null;
                imagemProspect2.setImageBitmap(null); // I
                getActivity().finish();
            }
        } else if (requestCode == 1) {
            if (resultCode != 0)
                Toast.makeText(getContext(), "Tente Novamente", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getContext(), "Sem Localização ativa, recurso indisponível", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getContext(), "Ação indefinida", Toast.LENGTH_LONG).show();

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void pegarUltimaLocalizacao() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            //Tenho a última localização conhecida. Em algumas situações raras, isso pode ser nulo.
                            if (location != null) {
                                mLocation = location;
                                ProspectHelper.setLocalizacao(mLocation);
                                getGeocoder();
                            }
                        }
                    });
        } else {
            progress.dismiss();
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    public void getGeocoder() {
        Rotas rotas = ApiGeocoder.buildRetrofit();
        Call<RespostaGeocoder> call;

        if (mLocation != null)
            call = rotas.getGeocoder(String.valueOf(mLocation.getLatitude()) + "," + String.valueOf(mLocation.getLongitude()), true, "pt-BR");
        else
            call = rotas.getGeocoder(ProspectHelper.getProspect().getLatitude() + "," + ProspectHelper.getProspect().getLongitude(), true, "pt-BR");

        call.enqueue(new Callback<RespostaGeocoder>() {
            @Override
            public void onResponse(Call<RespostaGeocoder> call, Response<RespostaGeocoder> response) {
                respostaGeocoder = response.body();
                if (response.body().getResult().size() > 0) {
                    ProspectHelper.setCheckin(respostaGeocoder.getResult().get(1).getFormattedAddress());
                    txtChekinEnderecoProspect.setVisibility(View.VISIBLE);
                    txtChekinEnderecoProspect.setText(respostaGeocoder.getResult().get(1).getFormattedAddress());
                } else {
                    txtLatitudeProspect.setVisibility(View.VISIBLE);
                    txtLongitudeProspect.setVisibility(View.VISIBLE);
                    if (mLocation != null) {
                        txtLatitudeProspect.setText(String.valueOf(mLocation.getLatitude()));
                        txtLongitudeProspect.setText(String.valueOf(mLocation.getLongitude()));
                    } else {
                        txtLatitudeProspect.setText(ProspectHelper.getProspect().getLatitude());
                        txtLongitudeProspect.setText(ProspectHelper.getProspect().getLongitude());
                    }
                    Toast.makeText(getContext(), "Endereço não encontrado! somente latitude e longitude", Toast.LENGTH_LONG).show();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<RespostaGeocoder> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getContext(), "Falha ao requisitar", Toast.LENGTH_LONG).show();
                txtLatitudeProspect.setText(ProspectHelper.getProspect().getLatitude());
                txtLongitudeProspect.setText(ProspectHelper.getProspect().getLongitude());
            }
        });
    }

    private void EnableGPSAutoMatically() {
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
                                progress.dismiss();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                progress = new ProgressDialog(getContext());
                progress.setMessage("Fazendo Check-in");
                progress.setTitle("Aguarde");
                progress.show();
                pegarUltimaLocalizacao();
            } else
                Toast.makeText(getContext(), "Sem a permissão, função indisponivel!", Toast.LENGTH_LONG).show();
        }
    }





    public void finishActionMode() {
        if (actionMode != null) {
            actionMode.finish();
            //cadastroAnexoAdapter.clearSelection();
            actionMode = null;
        }
    }
    private String dataRetorno() {
        if (edtDataRetorno.getText() != null && !edtDataRetorno.getText().toString().trim().isEmpty()) {
            String dataCapturada = "";
            Calendar dataAtual = new GregorianCalendar();
            Calendar dataRetorno = new GregorianCalendar();
            Date date = new Date();
            dataAtual.setTime(date);
            //pegar data da tela
            try {
                dataCapturada = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy")
                        .parse(edtDataRetorno.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //converte no mesmo padrão da captura da atual
            try {
                dataRetorno.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataCapturada));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (dataAtual.after(dataRetorno)) {
                edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext_erro);
                Toast.makeText(getActivity(), "Data deve ser posterior a atual", Toast.LENGTH_SHORT).show();
                verificaObrigatorios = false;
            } else
                return dataCapturada;
        }
        return null;
    }

    public void showMsgLocalArquivo( int id, String mensagem) {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sim_nao_cancelar_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button btnCancelar = (Button) dialogView.findViewById(R.id.btnMensagemCancelar);
        btnCancelar.setText("CANCELAR");
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setText("GALERIA");
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if ( id == R.id.imagemProspect1) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    REQUEST_CODE_IMAGEM_1 = 798;
                    REQUEST_CODE_IMAGEM_2 = 000;
                    startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_CODE_IMAGEM_1);
                } else if ( id == R.id.imagemProspect2) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    REQUEST_CODE_IMAGEM_1 = 000;
                    REQUEST_CODE_IMAGEM_2 = 798;
                    startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_CODE_IMAGEM_2);
                }
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setText("CAMERA");
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if ( id == R.id.imagemProspect1) {
                    try {
                        if (Environment.getExternalStorageState() != null) {
                            file = new File(Environment.getExternalStorageDirectory() + "/arquivo.jpg");
                        } else {
                            file = new File(Environment.getDataDirectory() + "/arquivo.jpg");
                        }
                        outputFileUri = Uri.fromFile(file);
                    } catch (Exception exp) {
                        Toast.makeText(getContext(), "Falha ao iniciar captura de imagem pela camera!!!(--" + exp.toString() + "--)", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    try {
                        REQUEST_CODE_IMAGEM_2 = 000;
                        REQUEST_CODE_IMAGEM_1 = 799;
                        startActivityForResult(intent, REQUEST_CODE_IMAGEM_1);
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "Falha ao iniciar captura de imagem pela camera!!(--" + exc.toString() + "--)", Toast.LENGTH_LONG).show();
                    }
                } else if ( id == R.id.imagemProspect2) {
                    try {
                        if (Environment.getExternalStorageState() != null)
                            file = new File(Environment.getExternalStorageDirectory() + "/arquivo.jpg");
                        else
                            file = new File(Environment.getDataDirectory() + "/arquivo.jpg");
                        outputFileUri = Uri.fromFile(file);
                    } catch (Exception exp) {
                        Toast.makeText(getContext(), "Falha ao iniciar captura de imagem pela camera!!!(--" + exp.toString() + "--)", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    try {
                        REQUEST_CODE_IMAGEM_2 = 799;
                        REQUEST_CODE_IMAGEM_1 = 000;
                        startActivityForResult(intent, REQUEST_CODE_IMAGEM_2);
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "Falha ao iniciar captura de imagem pela camera!!(--" + exc.toString() + "--)", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        alertDialog.show();
    }


    public void showMsgExcluirAltera( int id, String mensagem) {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sim_nao_cancelar_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button btnCancelar = (Button) dialogView.findViewById(R.id.btnMensagemCancelar);
        btnCancelar.setText("CANCELAR");
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setText("EXCLUIR");
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if ( id == R.id.imagemProspect1) {
                    ProspectHelper.getProspect().getFotoPrincipalBase64().setAnexo(null);
                    ProspectHelper.getProspect().setFotoPrincipalBase64(null);
                    ProspectHelper.setImagem1(null);
                    imagemProspect1.setImageResource(R.drawable.ic_image_gallery);
                } else if ( id == R.id.imagemProspect2) {
                    ProspectHelper.getProspect().getFotoSecundariaBase64().setAnexo(null);
                    ProspectHelper.getProspect().setFotoSecundariaBase64(null);
                    ProspectHelper.setImagem2(null);
                    imagemProspect2.setImageResource(R.drawable.ic_image_gallery);
                }
            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setText("ALTERAR");
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if ( id == R.id.imagemProspect1)
                    showMsgLocalArquivo(R.id.imagemProspect1, "Selecione o local para captura da imagem/arquivo)");
                else if ( id == R.id.imagemProspect2)
                    showMsgLocalArquivo(R.id.imagemProspect2, "Selecione o local para captura da imagem/arquivo)");
            }
        });
        alertDialog.show();
    }
}
