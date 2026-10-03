package com.example.rcksuporte05.rcksistemas.activity;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoDAO;
import com.example.rcksuporte05.rcksistemas.R;
import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityProdutoImagemZoom extends AppCompatActivity {
    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";
    static final String SCALE_TOAST_STRING = "Scaled to: %.2ff";
    static final String FLING_LOG_STRING = "Fling velocityX: %.2f, velocityY: %.2f";

    private PhotoView mPhotoView;
    private TextView mCurrMatrixTv;

    private Toast mCurrentToast;
    private Matrix mCurrentDisplayMatrix = null;

    @BindView(R.id.toolbar_prod_imagem_zoom)
    Toolbar toolbar;
    private DBHelper db = new DBHelper(this);
    private ProdutoDAO produtoDAO = new ProdutoDAO(db);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_imagem_zoom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            View toolbar = findViewById(R.id.appbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.tv_current_matrix);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.ic_action_up_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhotoView = findViewById(R.id.iv_photo);
        mCurrMatrixTv = findViewById(R.id.tv_current_matrix);

        Cursor cursor = produtoDAO.getListaFotoID("SELECT FOTO_ARQUIVO, FOTO_MINIATURA FROM TBL_PRODUTO_FOTO WHERE ID_FOTO = "
                + getIntent().getExtras().getString("id_foto"));
        String base64 = "";
        String base64m = "";
        if ( cursor.getCount() > 0)
            if (cursor.moveToNext()) {
                base64 = cursor.getString(cursor.getColumnIndex("FOTO_ARQUIVO"));
                base64m = cursor.getString(cursor.getColumnIndex("FOTO_MINIATURA"));
            }

        try {
            if (!base64.isEmpty()) {
                try {
                    byte[] decodedString = Base64.decode(base64.getBytes(), 0);
                    mPhotoView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch ( NullPointerException e) {
            if (!base64m.isEmpty()) {
                try {
                    byte[] decodedString = Base64.decode(base64m.getBytes(), 0);
                    mPhotoView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                } catch (OutOfMemoryError ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
        mPhotoView.setOnMatrixChangeListener(new MatrixChangeListener());
        mPhotoView.setOnPhotoTapListener(new PhotoTapListener());
        mPhotoView.setOnSingleFlingListener(new SingleFlingListener());

    }
    private class PhotoTapListener implements OnPhotoTapListener {
        @Override
        public void onPhotoTap(ImageView view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;
        }
    }

    private void showToast(CharSequence text) {
        if (mCurrentToast != null)
            mCurrentToast.cancel();

    }

    private class MatrixChangeListener implements OnMatrixChangedListener {
        @Override
        public void onMatrixChanged(RectF rect) {
            mCurrMatrixTv.setText("Toque duplo na imagem p/ ampliar/diminuir o zoom"/*rect.toString()*/);
        }
    }
    private class SingleFlingListener implements OnSingleFlingListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }
    }
}
