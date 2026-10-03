package com.example.rcksuporte05.rcksistemas.activity;

import static com.example.rcksuporte05.rcksistemas.api.Api.context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ImagemProdutoAdapter;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DialogHelper;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class ActivityProdutoImagem extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_current_matrix)
    TextView tv_current_matrix;
    private DBHelper db = new DBHelper(this);
    private ViewPager mViewPager;
    private ImagemProdutoAdapter mViewPagerAdapter;
    private ArrayList<ProdutoFoto>  mContents;
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);

    private static  int currentPage = 0;
    private static  int numPage = 0;
    private ActionMode actionMode;
    String id_produto;
    String nome_produto;
    float saldo_estoque;
    Utilitaria util = new Utilitaria();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_produto_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.tv_current_matrix);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        id_produto     = getIntent().getExtras().getString("id_produto");
        nome_produto   = getIntent().getExtras().getString("nome_produto");
        saldo_estoque  = getIntent().getExtras().getFloat("saldo_estoque");
        int acao       = getIntent().getExtras().getInt("acao");
        int position   = getIntent().getExtras().getInt("position");
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mContents = new ArrayList<>();
        List<ProdutoFoto> listaProdutoFoto;

        listaProdutoFoto = produtoDAO.getListaFotos("SELECT FOTO_MINIATURA, FOTO_ARQUIVO, ID_FOTO FROM TBL_PRODUTO_FOTO WHERE ID_PRODUTO = '" + id_produto + "' AND ATIVO = 'S' ORDER BY FOTO_PRINCIPAL DESC");
        for ( int i = 0 ; i < listaProdutoFoto.size(); i++)
            mContents.add(new ProdutoFoto(listaProdutoFoto.get(i).getId_foto(), listaProdutoFoto.get(i).getImages()));


        mViewPagerAdapter = new ImagemProdutoAdapter( mContents, this);
        mViewPager.setPageTransformer(true, new ViewPagerStack());

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mViewPagerAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.circle_view_produto_img);
        indicator.setViewPager(mViewPager);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int PageScrolled = i1;
            }

            @Override
            public void onPageSelected(int i) {
                int PageSelec = i;
                mViewPagerAdapter.setImagemListada(i);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int PageScroll = state;
                if ( state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = mContents.size();//;images.length;
                }
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Voltar");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        if ( (acao) == 1 || (acao) == 2 ) {
            getSupportActionBar().hide();
            enableActionMode(position);
        }
        final Handler handler = new Handler();
    }

    private class ViewPagerStack implements ViewPager.PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {
            /*
            if ( position >= 0) {
                page.setScaleX( 0.7f - 0.05f * position);
                page.setScaleY(0.7f);
                page.setTranslationX( -page.getWidth() * position);
                page.setTranslationY(-48 * position);
            }

             */
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actionMode == null) {
            getMenuInflater().inflate(R.menu.menu_produto_view_imagem, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.gc();
                finish();
                return true;
            case R.id.action_share_whats:
                compartilharImagemWhats();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void enableActionMode(final int position) {
        if (actionMode == null) {
            actionMode = startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.menu_action_mode_produtos_pedido_imagem, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_share_whats:
                            compartilharImagemWhats();
                            return true;
                        case android.R.id.home:
                            System.gc();
                            //Intent intent = new Intent(ActivityViewProdutoImage.this, ActivityProduto.class);
                            startActivity(new Intent(ActivityProdutoImagem.this, ActivityProdutoDrawer.class));
                            finish();
                        case R.id.action_continua_pedido_imagem:
                            Boolean produtoRepetido = false;
                            Boolean controlaEstoque = false;

                            if (PedidoHelper.getListaWebPedidoItens() != null) {
                                for (final WebPedidoItens webPedidoItens : PedidoHelper.getListaWebPedidoItens())
                                    if (webPedidoItens.getId_produto().trim().equals(id_produto.trim())) {
                                        produtoRepetido = true;
                                        break;
                                    }

                            }

                            if ( !produtoRepetido ) {
                                String SQL = "";
                                if ( ClienteHelper.getCliente() != null)
                                    SQL = "SELECT P.*,  TI.VALOR_UNITARIO AS PRECO_TABELA_CLIENTE, TC.NOME_TABELA AS NOME_TABELA FROM TBL_PRODUTO AS P " +
                                            " LEFT JOIN  TBL_TABELA_PRECO_ITENS AS TI ON TI.ID_PRODUTO = P.ID_PRODUTO  AND TI.ID_TABELA = " + ClienteHelper.getCliente().getId_tabela_preco() +
                                            " LEFT JOIN TBL_TABELA_PRECO_CAB   AS TC ON TC.ID_TABELA = TI.ID_TABELA " +
                                            " WHERE P.ID_PRODUTO = '" + id_produto + "'";
                                else
                                    SQL = "SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + id_produto + "'";

                                List<Produto> listaProduto = produtoDAO.getLista(SQL,"");
                                if (listaProduto.size() > 0)
                                    try {
                                        if (listaProduto.get(0).getTravar_fat_sem_estoque().equalsIgnoreCase("S"))
                                            controlaEstoque = true;
                                        else
                                            controlaEstoque = false;
                                    } catch (Exception e) {
                                    }
                                if (controlaEstoque) {
                                    if (saldo_estoque > 0) {
                                        showActionDialog(0, position, "Deseja enviar o produto " + nome_produto + " para o pedido?", listaProduto.get(0));
                                        break;
                                        /*
                                        ViewGroup viewGroup = ActivityProdutoImagem.this.findViewById(android.R.id.content);
                                        View dialogView = LayoutInflater.from(ActivityProdutoImagem.this).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProdutoImagem.this);
                                        builder.setView(dialogView);
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.setCancelable(false);

                                        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
                                        textView.setText("Deseja enviar o produto " + nome_produto + " para o pedido?");

                                        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
                                        btnNao.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
                                        btnSim.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();
                                                PedidoHelper.setProduto(listaProduto.get(0));
                                                Intent intent = new Intent(ActivityProdutoImagem.this, ActivityProdutoPedido.class);
                                                startActivityForResult(intent, position);
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }
                                        });
                                        alertDialog.show();
                                        break;

                                         */
                                    } else {
                                        util.showFeedbackDialog("O produto " + nome_produto + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido", true, false, R.drawable.ic_alerta_mensagem, ActivityProdutoImagem.this);
                                        //util.showMsgAlerta("O produto " + nome_produto + " está com saldo em estoque zerado e por isso não pode ser lançado no pedido", ActivityProdutoImagem.this);
                                        break;
                                    }
                                } else {
                                    if (!produtoRepetido) {
                                        //List<Produto> listaProduto = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + id_produto + "' ");
                                        PedidoHelper.setProduto(listaProduto.get(0));
                                        Intent it = new Intent(ActivityProdutoImagem.this, ActivityProdutoPedido.class);
                                        startActivityForResult(it, position);
                                        setResult(RESULT_OK, it);
                                        finish();
                                        break;
                                    }
                                }
                            } else {
                                util.showFeedbackDialog("O produto " + nome_produto + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!", true, false, R.drawable.ic_alerta_mensagem, ActivityProdutoImagem.this);
                                //util.showMsgAlerta("O produto " + nome_produto + " já esta nesse pedido e não pode ser lançado novamente, somente alterado!", ActivityProdutoImagem.this);
                                break;
                            }
                    }
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    actionMode.finish();
                    actionMode = null;
                    finish();
                }
            });
        }
    }
    public void showActionDialog(final int posicao , int position, String mensagem, Produto produto/*, Intent intent*/) {
        DialogHelper.showActionDialog(
               this,
                mensagem,
                "NÂO", null,
                "SIM", null,

                null,  null,
                new DialogHelper.OnActionSelectedListener() {
                    @Override
                    public void onPositive() {

                    }
                    @Override
                    public void onNegative() {
                        PedidoHelper.setProduto(produto);
                        Intent intent = new Intent(ActivityProdutoImagem.this, ActivityProdutoPedido.class);
                        startActivityForResult(intent, position);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    @Override
                    public void onNeutral() {
                    }
                }
        );
    }

    private void compartilharImagemWhats() {
        try {
            int currentItem = mViewPager.getCurrentItem();
            if (mContents == null || mContents.isEmpty() || currentItem >= mContents.size()) {
                Toast.makeText(this, "Nenhuma imagem disponível para compartilhar", Toast.LENGTH_SHORT).show();
                return;
            }

            ProdutoFoto foto = mContents.get(currentItem);
            String base64Image = foto.getFbase64();

            if (base64Image == null || base64Image.isEmpty()) {
                Toast.makeText(this, "Erro ao carregar os dados da imagem", Toast.LENGTH_SHORT).show();
                return;
            }

            // Remove prefixo se houver (ex: "data:image/png;base64,")
            if (base64Image.contains(",")) {
                base64Image = base64Image.split(",")[1];
            }

            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            if (bitmap == null) {
                Toast.makeText(this, "Falha ao processar a imagem", Toast.LENGTH_SHORT).show();
                return;
            }

            // Salva em arquivo temporário na pasta de cache
            File cachePath = new File(getExternalCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "produto_compartilhado_" + System.currentTimeMillis() + ".png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setPackage("com.whatsapp");

                try {
                    startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Se não tiver WhatsApp, tenta o compartilhamento geral
                    shareIntent.setPackage(null);
                    startActivity(Intent.createChooser(shareIntent, "Compartilhar imagem via:"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao preparar arquivo para compartilhamento", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ocorreu um erro inesperado ao compartilhar", Toast.LENGTH_SHORT).show();
        }
    }
}
