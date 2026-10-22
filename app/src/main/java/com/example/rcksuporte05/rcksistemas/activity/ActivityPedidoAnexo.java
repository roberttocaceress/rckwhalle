package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PedidoAnexoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.CadastroAnexoAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.PedidoAnexoAdapter;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.PedidoAnexo;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityPedidoAnexo extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.tb_anexo)
    Toolbar tb_anexo;
    @BindView(R.id.edtAnexo)
    EditText edtAnexo;
    @BindView(R.id.btnSalvarAnexo)
    Button btnSalvarAnexo;

    @BindView(R.id.btnAdicionarAnexo)
    Button btnAdicionarAnexo;

    @BindView(R.id.btnAdicionarFoto)
    Button btnAdicionarFoto;

    @BindView(R.id.btnFinalizarInclusao)
    Button btnFinalizarInclusao;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.imAnexo)
    ImageView imgAnexo;
    private Bitmap fotoMiniatura;
    private byte[] fotoAnexo; //Arquivo binario
    private PedidoAnexo pedidoAnexo;

    private PedidoAnexoAdapter pedidoAnexoAdapter;

    private PedidoAnexoAdapter.Listener listener;

    private Context context;

    private Bitmap bitmap;

    private byte[] fotoBinario;
    private Bitmap miniatura;

    private File file;
    Uri outputFileUri;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_anexo_2025);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout11), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.tb_anexo);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.btnFinalizarInclusao);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);

        context = this;
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        tb_anexo.setTitle("Anexo/Foto");
        if (getIntent().getIntExtra("vizualizacao", 0) >= 1)
            edtAnexo.setFocusable(false);
        if (getIntent().getIntExtra("Alteracao", -1) <= -1) {
            try {
                if (PedidoHelper.getListaPedidoAnexo().size() == 3) {
                    return;
                }
            } catch (NullPointerException e) {
            }
        } else {
            pedidoAnexo = PedidoHelper.getListaPedidoAnexo().get(getIntent().getIntExtra("Alteracao", -1));
            edtAnexo.setText(pedidoAnexo.getNomeAnexo().replace(".jpg", ""));
            byte[] data = Base64.decode(pedidoAnexo.getAnexo(), Base64.NO_WRAP);
            imgAnexo.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
        }
        setSupportActionBar(tb_anexo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            int visualizacao = getIntent().getIntExtra("visualizacao", -1);
            if ( visualizacao == 1) {
                btnSalvarAnexo.setEnabled(false);
                btnAdicionarAnexo.setEnabled(false);
                btnAdicionarFoto.setEnabled(false);

                btnSalvarAnexo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparente50)));
                btnAdicionarAnexo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparente50)));
                btnAdicionarFoto.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparente50)));
                edtAnexo.setText("");
                edtAnexo.setEnabled(false);
                imgAnexo.setEnabled(false);
                btnFinalizarInclusao.setText("VOLTAR");
            } else {
                btnSalvarAnexo.setEnabled(false);
                btnAdicionarAnexo.setEnabled(true);
                btnAdicionarFoto.setEnabled(true);

            }
        } catch ( NullPointerException e) {
            btnSalvarAnexo.setEnabled(false);
            btnAdicionarAnexo.setEnabled(true);
            btnAdicionarFoto.setEnabled(true);
            e.printStackTrace();
        }
        if (ContextCompat.checkSelfPermission(ActivityPedidoAnexo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ActivityPedidoAnexo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        if (ContextCompat.checkSelfPermission(ActivityPedidoAnexo.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ActivityPedidoAnexo.this, new String[]{Manifest.permission.CAMERA}, 0);
    }

    @Override
    public void onResume() {
        WebPedido webPedido = PedidoHelper.getWebPedido();
        try {
            PedidoHelper.setListaPedidoAnexo(new PedidoAnexoDAO(new DBHelper(context)).getLista(Integer.parseInt(webPedido.getId_web_pedido()), 0));
        } catch (NullPointerException e) {
            PedidoHelper.setListaPedidoAnexo(new PedidoAnexoDAO(new DBHelper(context)).getLista(Integer.parseInt(webPedido.getId_web_pedido()), 0));
        }
        setListenerPedidoAnexo();
        setRecyclerView();
        super.onResume();
    }

    private void setRecyclerView() {
        pedidoAnexoAdapter = new PedidoAnexoAdapter(PedidoHelper.getListaPedidoAnexo(), listener);
        if ( PedidoHelper.getListaPedidoAnexo().size() == 2) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            int orientation = this.getResources().getConfiguration().orientation;
            recyclerView.setHasFixedSize(true);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {

                    if (pedidoAnexoAdapter != null) {
                        pedidoAnexoAdapter.setNumCols(2);
                        switch (pedidoAnexoAdapter.getItemViewType(i)) {
                            case 1:
                                return 1;
                            case 0:
                                return 2;
                            default:
                                return -1;
                        }
                    }else
                        return -1;
                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(pedidoAnexoAdapter);
            pedidoAnexoAdapter.notifyDataSetChanged();
            setListenerPedidoAnexo();
        } else if (PedidoHelper.getListaPedidoAnexo().size() == 3) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            int orientation = this.getResources().getConfiguration().orientation;
            recyclerView.setHasFixedSize(true);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {

                    if (pedidoAnexoAdapter != null) {
                        pedidoAnexoAdapter.setNumCols(3);
                        switch (pedidoAnexoAdapter.getItemViewType(i)) {
                            case 1:
                                return 1;
                            case 0:
                                return 3 ;
                            default:
                                return -1;
                        }
                    }else
                        return -1;
                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(pedidoAnexoAdapter);
            pedidoAnexoAdapter.notifyDataSetChanged();
            setListenerPedidoAnexo();
        } else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(pedidoAnexoAdapter);
            pedidoAnexoAdapter.notifyDataSetChanged();
            setListenerPedidoAnexo();
        }
        //if (PedidoHelper.getListaPedidoAnexo() != null && PedidoHelper.getListaPedidoAnexo().size() > 0)
        //edtTotalAnexos.setText("Anexos listados: " + ClienteHelper.getListaCadastroAnexo().size());
        //else
        //edtTotalAnexos.setText("Nenhum anexo registrado");
    }
    private void setListenerPedidoAnexo() {
        listener = new PedidoAnexoAdapter.Listener() {
            @Override
            public void onClickListener(int position) {
                try {
                    int visualizacao = getIntent().getIntExtra("visualizacao", -1);
                    if (visualizacao == 1) {
                        return;
                    }
                } catch ( NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showMsgDialog("Clique em uma das opções!!", position);
                //Toast.makeText(context, PedidoHelper.getListaPedidoAnexo().get(position).getIdAnexo(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onLongClickListener(int position) {

            }
        };
    }
    public void showMsgDialog(String mensagem, int position) {
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("RCK SISTEMAS ESPECIFICOS");
        alertDialog.setMessage(mensagem);
        alertDialog.setIcon(R.mipmap.ic_rck_logo_dialog_progressbar_2025);
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Excluir anexo/foto",
                (dialog, which) -> {
                    dialog.dismiss(); //<-- change it with ur code
                    alertDialog.dismiss();
                    int res = new PedidoAnexoDAO(new DBHelper(context)).delete(PedidoHelper.getListaPedidoAnexo().get(position));
                    if ( res > 0) {
                        PedidoHelper.getListaPedidoAnexo().remove(position);
                    } else {
                        Toast.makeText(context, "Falha ao excluir o anexo/foto!", Toast.LENGTH_LONG).show();
                    }
                    onResume();

                });
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } );
        alertDialog.show();
    }



    @OnClick(R.id.btnSalvarAnexo)
    public void salvarAnexoGaleria() {

        if (fotoAnexo == null && fotoMiniatura == null) {
            Toast.makeText(this, "Falha ao obter imagem!!", Toast.LENGTH_LONG).show();
            finish();
        }
        salvarAnexo();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnAdicionarAnexo)
    public void addAnexoGaleria() {
        try {
            if (PedidoHelper.getListaPedidoAnexo().size() == 3) {
                Toast.makeText(context, "Quantidade máxima de anexos permitido já informado!", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        imgAnexo.setImageBitmap(null);
        pedidoAnexo = new PedidoAnexo();
        startActivityForResult(Intent.createChooser( new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), "Selecione uma imagem!"), 123);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnAdicionarFoto)
    public void addAnexoFoto() {
        try {
            if (PedidoHelper.getListaPedidoAnexo().size() == 3) {
                Toast.makeText(context, "Quantidade máxima de anexos permitido já informado!", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (NullPointerException e) {
        }
        imgAnexo.setImageBitmap(null);
        pedidoAnexo = new PedidoAnexo();

        try {
            file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + R.string.app_name + "/arquivo.jpg");
            String pathStorageDirectory = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                pathStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
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
        } catch (Exception exp) {
            Toast.makeText(this, "Falha ao iniciar captura de imagem pela camera!!!(--" + exp.toString() + "--)", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE/*"android.media.action.IMAGE_CAPTURE"*/).putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri), 1234);
        } catch (Exception exc) {
            Toast.makeText(this, "Falha ao iniciar captura de imagem pela camera!!(--" + exc.toString() + "--)", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnFinalizarInclusao)
    public void closeActivity() {
        onBackPressed();
    }

    @OnClick(R.id.imAnexo)
    public void removerAnexoFoto() {

        if (btnSalvarAnexo.isEnabled()) {
            androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context).create();
            alertDialog.setTitle("RCK SISTEMAS ESPECIFICOS");
            alertDialog.setMessage("Clique em uma das opções!");
            alertDialog.setIcon(R.mipmap.ic_rck_logo_dialog_progressbar_2025);
            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Cancelar inclusão anexo/foto",
                    (dialog, which) -> {
                        dialog.dismiss(); //<-- change it with ur code
                        alertDialog.dismiss();
                        imgAnexo.setImageBitmap(null);
                       // bitmap = null;
                        btnSalvarAnexo.setEnabled(false);
                        btnAdicionarAnexo.setEnabled(true);
                        btnAdicionarFoto.setEnabled(true);
                        onResume();

                    });
            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Salvar anexo/foto",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            salvarAnexoGaleria();
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null)
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(data.getData()), null, options), data.getData());
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        fotoMiniatura = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                        fotoAnexo = outputStream.toByteArray();
                        imgAnexo.setImageBitmap(bitmap);
                        imgAnexo.setBackgroundColor(Color.TRANSPARENT);
                        btnSalvarAnexo.setEnabled(true);
                        btnAdicionarAnexo.setEnabled(false);
                        btnAdicionarFoto.setEnabled(false);
                        Toast.makeText(this, "Informe a descrição e clique em salvar anexo/foto!!", Toast.LENGTH_LONG).show();
                        return;
                    } catch (FileNotFoundException | NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else
                    Toast.makeText(this, "Falha ao obter imagem!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Captura da imagem cancelada!!!", Toast.LENGTH_LONG).show();
            }
            btnSalvarAnexo.setEnabled(false);
            btnAdicionarAnexo.setEnabled(true);
            btnAdicionarFoto.setEnabled(true);
        }
        if (requestCode == 1234) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    //Bitmap imageBitmap;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    } else {
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    }
                    try {
                        bitmap = FotoUtil.rotateBitmap(bitmap, outputFileUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    fotoMiniatura = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                    //boolean validaCompressao = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    //fotoBinario = outputStream.toByteArray();
                    //String encodedImage = Base64.encodeToString(fotoBinario, Base64.DEFAULT);
                    /*
                    fotoAnexo = outputStream.toByteArray();
                    imgAnexo.setImageBitmap(imageBitmap); // ImageButton, seto a foto como imagem do botão
                    imgAnexo.setBackgroundColor(Color.TRANSPARENT);
                    //boolean isImageTaken = true;
                    miniatura.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);

                     */
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                    fotoAnexo = outputStream.toByteArray();
                    imgAnexo.setImageBitmap(bitmap);
                    imgAnexo.setBackgroundColor(Color.TRANSPARENT);
                    btnSalvarAnexo.setEnabled(true);
                    btnAdicionarAnexo.setEnabled(false);
                    btnAdicionarFoto.setEnabled(false);
                    Toast.makeText(this, "Informe a descrição e clique em salvar anexo/foto!!", Toast.LENGTH_LONG).show();
                    return;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Captura da imagem/foto cancelada!!!", Toast.LENGTH_LONG).show();
            }
            btnSalvarAnexo.setEnabled(false);
            btnAdicionarAnexo.setEnabled(true);
            btnAdicionarFoto.setEnabled(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //if (getIntent().getIntExtra("vizualizacao", 0) != 1)
        //    getMenuInflater().inflate(R.menu.menu_produto_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_salvar_produto:
                Toast.makeText(context, "Opção em desenvolvimento", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarAnexo(){
        pedidoAnexo.setAnexo(Base64.encodeToString(fotoAnexo, Base64.NO_WRAP));
        pedidoAnexo.setMiniatura(fotoMiniatura);
        pedidoAnexo.setIdPedido(Integer.parseInt(PedidoHelper.getWebPedido().getId_web_pedido()));
        pedidoAnexo.setIdPedidoServidor(0);
        if (edtAnexo.getText().toString() != null && !edtAnexo.getText().toString().trim().isEmpty()) {
            pedidoAnexo.setNomeAnexo( edtAnexo.getText().toString().contains(".jpg") ? edtAnexo.getText().toString() : edtAnexo.getText().toString() + ".jpg");
            if (getIntent().getIntExtra("Alteracao", -1) <= -1)
                if (PedidoHelper.getListaPedidoAnexo() != null)
                    PedidoHelper.getListaPedidoAnexo().add(pedidoAnexo);
                else
                    setListaAnexo(pedidoAnexo);
            else
                PedidoHelper.getListaPedidoAnexo().set(getIntent().getIntExtra("Alteracao", -1), pedidoAnexo);
            //finish();
            new PedidoAnexoDAO(new DBHelper(context)).add(pedidoAnexo);
            imgAnexo.setImageBitmap(null);
            //bitmap = null;
            edtAnexo.setText("");
            onResume();
            btnSalvarAnexo.setEnabled(false);
            btnAdicionarAnexo.setEnabled(true);
            btnAdicionarFoto.setEnabled(true);

        } else {
            btnSalvarAnexo.setEnabled(true);
            btnAdicionarAnexo.setEnabled(false);
            btnAdicionarFoto.setEnabled(false);
            edtAnexo.setError("É necessário informar a descrição do anexo/foto!");
            edtAnexo.requestFocus();
        }
    }
    private void setListaAnexo(PedidoAnexo pedidoAnexo) {
        List<PedidoAnexo> listaAnexo = new ArrayList<>();
        listaAnexo.add(pedidoAnexo);
        PedidoHelper.setListaPedidoAnexo(listaAnexo);
    }

    private  void getListaAnexo(int idPedido) {
        List<PedidoAnexo> listaAnexo = new ArrayList<>();
        listaAnexo.add(pedidoAnexo);
        PedidoHelper.setListaPedidoAnexo(listaAnexo);
    }

    @Override
    public void onClick(View view) {

    }
}