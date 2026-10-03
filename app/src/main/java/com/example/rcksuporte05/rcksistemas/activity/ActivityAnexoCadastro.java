package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityAnexoCadastro extends AppCompatActivity {
    @BindView(R.id.tb_anexo)
    Toolbar tb_anexo;
    @BindView(R.id.edtAnexo)
    EditText edtAnexo;
    @BindView(R.id.imAnexo)
    ImageView imgAnexo;
    private Bitmap fotoMiniatura;
    private byte[] fotoAnexo; //Arquivo binario
    private CadastroAnexo cadastroAnexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cadastro_anexo);
        ButterKnife.bind(this);
        tb_anexo.setTitle("Anexo");
        if (getIntent().getIntExtra("vizualizacao", 0) >= 1)
            edtAnexo.setFocusable(false);
        if (getIntent().getIntExtra("Alteracao", -1) <= -1) {
            cadastroAnexo = new CadastroAnexo();
            startActivityForResult(Intent.createChooser( new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), "Selecione uma imagem!"), 123);
        } else {
            cadastroAnexo = ClienteHelper.getListaCadastroAnexo().get(getIntent().getIntExtra("Alteracao", -1));
            edtAnexo.setText(cadastroAnexo.getNomeAnexo().replace(".jpg", ""));
            byte[] data = Base64.decode(cadastroAnexo.getAnexo(), Base64.NO_WRAP);
            imgAnexo.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
        }
        setSupportActionBar(tb_anexo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.imAnexo)
    public void showGaleria() {
        if (getIntent().getIntExtra("vizualizacao", 0) != 1)
            startActivityForResult(Intent.createChooser(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), "Selecione uma imagem!"), 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (requestCode == 123)
            if (resultCode == Activity.RESULT_OK) {
                if (data != null)
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        /*Reduzindo a qualidade da imagem para preservar memoria.
                         * Aqui você pode testar a redução que melhor atende sua necessidade
                         * O codigo original não tinha o 'options'
                         */
                        options.inSampleSize = 2;
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(data.getData()), null, options), data.getData());
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        fotoMiniatura = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                        fotoAnexo = outputStream.toByteArray();
                        imgAnexo.setImageBitmap(bitmap);
                        imgAnexo.setBackgroundColor(Color.TRANSPARENT);
                    } catch (FileNotFoundException | NullPointerException e) {
                    } catch (Exception e) {
                    }
                else
                    Toast.makeText(this, "Falha ao obter imagem!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Captura da imagem cancelada!!!", Toast.LENGTH_LONG).show();
                finish();
            }
        super.onActivityResult(requestCode, resultCode, data);
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
                cadastroAnexo.setIdEntidade(1);
                if (fotoAnexo == null && fotoMiniatura == null) {
                    Toast.makeText(this, "Falha ao obter imagem!!", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                }
                salvarAnexo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void salvarAnexo(){
        cadastroAnexo.setAnexo(Base64.encodeToString(fotoAnexo, Base64.NO_WRAP));
        cadastroAnexo.setMiniatura(fotoMiniatura);
        cadastroAnexo.setIdCadastro(ClienteHelper.getCliente().getId_cadastro());
        cadastroAnexo.setIdCadastroServidor(ClienteHelper.getCliente().getId_cadastro_servidor());
        if (edtAnexo.getText().toString() != null && !edtAnexo.getText().toString().trim().isEmpty()) {
            cadastroAnexo.setNomeAnexo( edtAnexo.getText().toString().contains(".jpg") ? edtAnexo.getText().toString() : edtAnexo.getText().toString() + ".jpg");
            if (getIntent().getIntExtra("Alteracao", -1) <= -1)
                if (ClienteHelper.getListaCadastroAnexo() != null)
                    ClienteHelper.getListaCadastroAnexo().add(cadastroAnexo);
                else
                    setListaAnexo(cadastroAnexo);
            else
                ClienteHelper.getListaCadastroAnexo().set(getIntent().getIntExtra("Alteracao", -1), cadastroAnexo);
            finish();
        } else {
            edtAnexo.setError("É necessário informar a descrição do anexo!");
            edtAnexo.requestFocus();
        }
    }

    private void setListaAnexo(CadastroAnexo cadastroAnexo) {
        List<CadastroAnexo> listaAnexo = new ArrayList<>();
        listaAnexo.add(cadastroAnexo);
        ClienteHelper.setListaCadastroAnexo(listaAnexo);
    }
}
