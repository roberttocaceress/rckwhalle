package com.example.rcksuporte05.rcksistemas.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Foto;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFoto extends AppCompatActivity {

    /*@BindView(R.id.toolbar)
    Toolbar toolbar;
    */
    @BindView(R.id.tb_anexo_foto)
    Toolbar tb_anexo_foto;

    @BindView(R.id.edtAnexoFoto)
    EditText edtAnexoFoto;

    @BindView(R.id.imAnexoFoto)
    ImageView imAnexoFoto;

    private Foto imagem = new Foto();
    private ProgressDialog progress;
    private Uri uri;
    private CadastroAnexo cadastroAnexo;
    private Context context;
    private byte[] fotoBinario;
    private Bitmap miniatura;
    private Bitmap bitmap;
    private File file;
    Uri outputFileUri;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        ButterKnife.bind(this);
        context = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        tb_anexo_foto.setTitle("Anexo(Foto)");
        if (ContextCompat.checkSelfPermission(ActivityFoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ActivityFoto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        if (ContextCompat.checkSelfPermission(ActivityFoto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ActivityFoto.this, new String[]{Manifest.permission.CAMERA}, 0);
        //else {
            cadastroAnexo = new CadastroAnexo();
            try {
                if (Environment.getExternalStorageState() != null)
                    file = new File(Environment.getExternalStorageDirectory() + "/arquivo.jpg");
                else
                    file = new File(Environment.getDataDirectory() + "/arquivo.jpg");
                //file = new File(Environment.getDataDirectory() + "/arquivo.jpg");

                file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + R.string.app_name + "/arquivo.jpg");

                String pathStorageDirectory = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    pathStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
                    //pathStorageDirectory =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                } else {
                    pathStorageDirectory =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                }
                File folder = new File(pathStorageDirectory);
                if ( !folder.exists())
                    if (!folder.mkdir())
                        Toast.makeText(context, "Falha ao criar o diretorio para download do arquivo! Tente novamente, caso o problema persista, entre em contato com o suporte técnico!", Toast.LENGTH_LONG).show();
                String nameFile = "fotopedido.jpg";
                file = new File(folder,  nameFile);

                outputFileUri = FileProvider.getUriForFile(context,
                        context.getApplicationContext().getPackageName() + ".provider", file, "ANEXO"); //Uri.fromFile(file);

                Uri uri = FileProvider.getUriForFile(context,
                        context.getApplicationContext().getPackageName() + ".provider", file, "ANEXO");

            } catch (Exception exp) {
                Toast.makeText(this, "Falha ao iniciar captura de imagem pela camera!!!(--" + exp.toString() + "--)", Toast.LENGTH_LONG).show();
                finish();
            }
            try {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE/*"android.media.action.IMAGE_CAPTURE"*/).putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri), 123);

                /*
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }

                 */
            } catch (Exception exc) {
                Toast.makeText(this, "Falha ao iniciar captura de imagem pela camera!!(--" + exc.toString() + "--)", Toast.LENGTH_LONG).show();
                finish();
            }
        //}
        setSupportActionBar(tb_anexo_foto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 123) {
                //if (resultCode == RESULT_OK) {
                    showProgressDialog();
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap imageBitmap;
                        String pathFile = file.getAbsolutePath();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                            imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        } else {
                            imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        }
                        //Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);
                        try {
                            imageBitmap = FotoUtil.rotateBitmap(imageBitmap, outputFileUri/*data.getData()*/);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        miniatura = Bitmap.createScaledBitmap(imageBitmap, 220, 230, false);
                        boolean validaCompressao = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                        fotoBinario = outputStream.toByteArray();
                        String encodedImage = Base64.encodeToString(fotoBinario, Base64.DEFAULT);
                        imAnexoFoto.setImageBitmap(imageBitmap); // ImageButton, seto a foto como imagem do botão
                        imAnexoFoto.setBackgroundColor(Color.TRANSPARENT);
                        boolean isImageTaken = true;
                        progress.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(this, "Falha ao obter imagem!!(--" + e.toString() + "--)", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        setNullImagem();
                        finish();
                    }
                //} else {
                //    Toast.makeText(this, "Captura da imagem cancelada!!!", Toast.LENGTH_LONG).show();
                //    setNullImagem();
                //    finishActivity(requestCode);
                //}
            } else {
                Toast.makeText(this, "Falha ao obter imagem!!", Toast.LENGTH_LONG).show();
                setNullImagem();
                finishActivity(requestCode);
            }
        } catch (Exception exc) {
            Toast.makeText(this, "Falha ao obter imagem!(--" + exc.toString() + "--)", Toast.LENGTH_LONG).show();
            setNullImagem();
            finishActivity(requestCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String BitmapTOBase64(Bitmap imagem) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap
        byte[] photo = baos.toByteArray();
        return Base64.encodeToString(photo, Base64.NO_WRAP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("vizualizacao", 0) != 1)
            getMenuInflater().inflate(R.menu.menu_produto_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_salvar_produto:
                Cliente cliente = ClienteHelper.getCliente();
                cadastroAnexo.setIdEntidade(1);
                if (fotoBinario != null)
                    cadastroAnexo.setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                if (miniatura != null)
                    cadastroAnexo.setMiniatura(miniatura);

                try {
                    cadastroAnexo.setIdCadastro(ClienteHelper.getCliente().getId_cadastro());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    cadastroAnexo.setIdCadastroServidor(ClienteHelper.getCliente().getId_cadastro_servidor());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                if (edtAnexoFoto.getText().toString() != null && !edtAnexoFoto.getText().toString().trim().isEmpty()) {
                    if (edtAnexoFoto.getText().toString().contains(".jpg"))
                        cadastroAnexo.setNomeAnexo(edtAnexoFoto.getText().toString());
                    else
                        cadastroAnexo.setNomeAnexo(edtAnexoFoto.getText().toString() + ".jpg");
                    if (getIntent().getIntExtra("Alteracao", -1) <= -1)
                        if (ClienteHelper.getListaCadastroAnexo() != null)
                            ClienteHelper.getListaCadastroAnexo().add(cadastroAnexo);
                        else {
                            List<CadastroAnexo> listaAnexo = new ArrayList<>();
                            listaAnexo.add(cadastroAnexo);
                            ClienteHelper.setListaCadastroAnexo(listaAnexo);
                        }
                    else
                        ClienteHelper.getListaCadastroAnexo().set(getIntent().getIntExtra("Alteracao", -1), cadastroAnexo);

                    finish();
                } else {
                    edtAnexoFoto.setError("É necessário informar a descrição do anexo");
                    edtAnexoFoto.requestFocus();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showProgressDialog(){
        progress = new ProgressDialog(ActivityFoto.this);
        progress.setMessage("Processando Imagen!");
        progress.setTitle("Aguarde");
        progress.show();
    }

    private void setNullImagem(){
        fotoBinario = null;
        miniatura = null;
        imAnexoFoto.setImageBitmap(null); // I
    }
}
/*
    public void salvarImagem(final Foto foto) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final ProgressDialog dialog = new ProgressDialog(FotoActivity.this);
                        dialog.setTitle("Atenção");
                        dialog.setMessage("Enviando a imagem");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });

                Rotas apiRotas = Api.buildRetrofit();
                Call<Foto> call = apiRotas.salvarImagem(foto);
                try {
                    Response<Foto> response = call.execute();
                    switch (response.code()) {
                        case 200:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();
                                    Toast.makeText(FotoActivity.this, "Imagem enviada com sucesso", Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                            break;
                        case 500:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();
                                    Toast.makeText(FotoActivity.this, "Problemas ao enviar a imagem", Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                            break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.dismiss();
                            Toast.makeText(FotoActivity.this, "Sem conexão com a rede!\n\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog.Builder alert = new AlertDialog.Builder(FotoActivity.this);
                    alert.setMessage(e.getMessage());
                    alert.setNeutralButton("OK", null);
                    alert.show();
                }
            }
        });

        thread.start();
    }
    */
