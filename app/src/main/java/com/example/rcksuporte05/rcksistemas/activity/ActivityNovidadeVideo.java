package com.example.rcksuporte05.rcksistemas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Novidade;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityNovidadeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener {
    YouTubePlayerView youTubeView;
    YouTubePlayer youTubePlayer;
    Toolbar toolbar;
    String url;
    String videoDescricao;
    int position;
    //BottomNavigationView bottomNavView;
    CardView cardView;
    ImageView img_view_anterior, img_view_proximo;//, img_view_menu;
    List<Novidade> lista = new ArrayList<>();
    TextView txv_video_descricao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novidade_video);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbarNovidade);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cardView = (CardView) findViewById(R.id.card_view);
        txv_video_descricao = (TextView) findViewById(R.id.txv_video_descricao);
        img_view_anterior = (ImageView) findViewById(R.id.img_view_anterior);
        img_view_proximo = (ImageView) findViewById(R.id.img_view_proximo);
        //img_view_menu = (ImageView) findViewById(R.id.img_view_menu);

        img_view_anterior.setOnClickListener(this);
        img_view_proximo.setOnClickListener(this);
        //img_view_menu.setOnClickListener(this);

        youTubeView = (YouTubePlayerView)
                findViewById(R.id.videoView);
        this.url = getIntent().getExtras().getString("url");
        this.videoDescricao = getIntent().getExtras().getString("descricao");
        txv_video_descricao.setText(videoDescricao);
        //this.position = getIntent().getExtras().getInt("position");
        showVideo();
        lista = getListaNovidades();
        showHideImg(position);


    }
    private void showHideImg(int position) {
        if ( position == 0 ) {
            img_view_anterior.setImageResource(R.drawable.ic_action_up_arrow_back_disabled);
            if ( lista.size() <= 1)
                img_view_proximo.setImageResource(R.drawable.ic_action_arrow_forward_disabled);
            else
                img_view_proximo.setImageResource(R.drawable.ic_action_arrow_forward_white);
        } else {
            //img_view_anterior.setVisibility(View.VISIBLE);
            img_view_anterior.setImageResource(R.drawable.ic_action_up_arrow_back_white);
            if ( (position+1) < lista.size() )
                img_view_proximo.setImageResource(R.drawable.ic_action_arrow_forward_white);
            else
                img_view_proximo.setImageResource(R.drawable.ic_action_arrow_forward_disabled);

        }
    }

    private void showVideo(){
        if ( youTubePlayer != null) {
            youTubeView.destroyDrawingCache();
            youTubePlayer.pause();
            //youTubePlayer.cueVideo(url);
            youTubePlayer.loadVideo(url);
        } else {
            youTubeView.initialize(ActivityNovidadeVideo.DeveloperKey.DEVELOPER_KEY, ActivityNovidadeVideo.this);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        youTubePlayer = player;
        if (!wasRestored) {
            try {
                //youTubePlayer.cueVideo(url);
                youTubePlayer.loadVideo(url);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Algo deu errado com este vídeo!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


    }

    @Override
    public void onClick(View view) {
        switch ( view.getId()){
            case R.id.img_view_anterior:
                try {
                    if ( position <= lista.size()) {
                        position--;
                        url = lista.get(position).getArquivo();
                        showHideImg(position);
                        showVideo();
                    }
                } catch ( ArrayIndexOutOfBoundsException e) {
                } catch ( Exception e) {
                }
                break;
            case R.id.img_view_proximo:
                try {
                    if (  lista.size() > (position+1) ) {
                        position++;
                        url = getListaNovidades().get(position).getArquivo();
                        showHideImg(position);
                        showVideo();
                    }
                } catch ( ArrayIndexOutOfBoundsException e) {
                } catch ( Exception e) {
                }
                break;
        }
    }


    /*
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch ( item.getItemId() ) {
                case R.id.video_anterior:
                    try {
                        if ( position <= lista.size()) {
                            position--;
                            url = lista.get(position).getArquivo();
                            showVideo();
                        }
                    } catch ( ArrayIndexOutOfBoundsException e) {

                    } catch ( Exception e) {

                    }
                    break;
                case R.id.video_proximo:
                    try {
                        if (  lista.size() > (position+1) ) {
                            position++;
                            url = getListaNovidades().get(position).getArquivo();
                            showVideo();

                        }
                    } catch ( ArrayIndexOutOfBoundsException e) {

                    } catch ( Exception e) {

                    }
                    break;
            }
            return true;
        }
    */
    public class DeveloperKey {
        /**
         * Please replace this with a valid API key which is enabled for the
         * YouTube Data API v3 service. Go to the
         * <a href=”https://code.google.com/apis/console/“>Google APIs Console</a> to
         * register a new developer key.
         */
        public static final String DEVELOPER_KEY = "AIzaSyCyRK-mgZgqZ3rfL3acLhusdive6Zd4uGo";
    }

    private List<Novidade> getListaNovidades() {
        Novidade novidade;
        List<Novidade> lista = new ArrayList<>();
        novidade = new Novidade("1", "Versao 2.*.*-*", "Cadastro de novo cliente no aplicativo", "mjln5bYagno");
        lista.add(novidade);
        novidade = new Novidade("2", "Versao 2.*.*-*", "Sincronização/exportação do novo cliente no aplicativo", "3-3vRBonKZI");
        lista.add(novidade);
        //novidade = new Novidade("3", "Versao 2.*.*-*", "Sincronização/exportação do novo cliente no aplicativo", "3-3vRBonKZI");
        //lista.add(novidade);
        return  lista;
    }
}