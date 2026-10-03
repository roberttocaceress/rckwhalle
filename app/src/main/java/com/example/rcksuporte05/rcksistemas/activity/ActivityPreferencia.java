package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityPreferencia extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    @BindView(R.id.swit_venda_1)
    Switch swit_venda_1;

    @BindView(R.id.swit_venda_2)
    Switch swit_venda_2;

    @BindView(R.id.swit_venda_3)
    Switch swit_venda_3;

    @BindView(R.id.swit_venda_4)
    Switch swit_venda_4;

    @BindView(R.id.swit_venda_5)
    Switch swit_venda_5;

    @BindView(R.id.swit_venda_6)
    Switch swit_venda_6;

    @BindView(R.id.swit_fin_1)
    Switch swit_fin_1;

    @BindView(R.id.swit_fin_2)
    Switch swit_fin_2;

    @BindView(R.id.swit_fin_3)
    Switch swit_fin_3;

    @BindView(R.id.swit_fin_4)
    Switch swit_fin_4;

    @BindView(R.id.swit_fin_5)
    Switch swit_fin_5;

    @BindView(R.id.swit_fin_6)
    Switch swit_fin_6;

    @BindView(R.id.swit_ped_1)
    Switch swit_ped_1;

    @BindView(R.id.swit_ped_2)
    Switch swit_ped_2;

    @BindView(R.id.swit_ped_3)
    Switch swit_ped_3;

    @BindView(R.id.swit_ped_4)
    Switch swit_ped_4;

    @BindView(R.id.swit_ped_5)
    Switch swit_ped_5;

    @BindView(R.id.swit_ped_6)
    Switch swit_ped_6;

    @BindView(R.id.btnCancelar)
    Button btnCancelar;

    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    Preferencia preferencia;
    //private DBHelper db ;

    private int dias_hist_venda = 30, dias_hist_financeiro = 30, dias_hist_pedido = 0;
    private DBHelper db = new DBHelper(ActivityPreferencia.this);
    private PreferenciaDAO preferenciaDAO = new PreferenciaDAO(db);
    Utilitaria util = new Utilitaria();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Aplica o padding no topo da Toolbar (para não cobrir o relógio)
            View toolbar = findViewById(R.id.tbl_preferecnia);
            toolbar.setPadding(0, insets.top, 0, 0);
            // Aplica o padding no rodapé (para não cobrir os totais com a barra de gestos)
            View rodape = findViewById(R.id.linearLayout2);
            rodape.setPadding(0, 0, 0, insets.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
        if ( !preferencia.getId().isEmpty()) {
            switch(preferencia.getDias_hist_venda()) {
                case 30:
                    swit_venda_1.setChecked(true);
                    break;
                case 60:
                    swit_venda_2.setChecked(true);
                    break;
                case 90:
                    swit_venda_3.setChecked(true);
                    break;
                case 120:
                    swit_venda_4.setChecked(true);
                    break;
                case  150:
                    swit_venda_5.setChecked(true);
                    break;
                case  180:
                    swit_venda_6.setChecked(true);
                    break;
                default:
                    swit_venda_1.setChecked(true);
                    break;
            }
            switch(preferencia.getDias_hist_financeiro()) {
                case 30:
                    swit_fin_1.setChecked(true);
                    break;
                case 60:
                    swit_fin_2.setChecked(true);
                    break;
                case  90:
                    swit_fin_3.setChecked(true);
                    break;
                case  120:
                    swit_fin_4.setChecked(true);
                    break;
                case  150:
                    swit_fin_5.setChecked(true);
                    break;
                case  180:
                    swit_fin_6.setChecked(true);
                    break;
                default:
                    swit_fin_1.setChecked(true);
                    break;
            }
            switch( preferencia.getDias_hist_pedido()) {
                case 1:
                    swit_ped_1.setChecked(true);
                    break;
                case 7:
                    swit_ped_2.setChecked(true);
                    break;
                case 15:
                    swit_ped_3.setChecked(true);
                    break;
                case 30:
                    swit_ped_4.setChecked(true);
                    break;
                case 60:
                    swit_ped_5.setChecked(true);
                    break;
                case 90:
                    swit_ped_6.setChecked(true);
                    break;
                default:
                    break;

            }
        } else {
            swit_venda_1.setChecked(true);
            swit_fin_1.setChecked(true);
        }

        swit_venda_1.setOnCheckedChangeListener(this);
        swit_venda_2.setOnCheckedChangeListener(this);
        swit_venda_3.setOnCheckedChangeListener(this);
        swit_venda_4.setOnCheckedChangeListener(this);
        swit_venda_5.setOnCheckedChangeListener(this);
        swit_venda_6.setOnCheckedChangeListener(this);

        swit_fin_1.setOnCheckedChangeListener(this);
        swit_fin_2.setOnCheckedChangeListener(this);
        swit_fin_3.setOnCheckedChangeListener(this);
        swit_fin_4.setOnCheckedChangeListener(this);
        swit_fin_5.setOnCheckedChangeListener(this);
        swit_fin_6.setOnCheckedChangeListener(this);

        swit_ped_1.setOnCheckedChangeListener(this);
        swit_ped_2.setOnCheckedChangeListener(this);
        swit_ped_3.setOnCheckedChangeListener(this);
        swit_ped_4.setOnCheckedChangeListener(this);
        swit_ped_5.setOnCheckedChangeListener(this);
        swit_ped_6.setOnCheckedChangeListener(this);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencia preferencia = new Preferencia();
                preferencia.setDias_hist_venda(dias_hist_venda);
                preferencia.setDias_hist_financeiro(dias_hist_financeiro);
                preferencia.setDias_hist_pedido(dias_hist_pedido);
                if( preferenciaDAO.add(preferencia) ){
                    Toast.makeText(ActivityPreferencia.this, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    util.showMsgAlerta(" Falha ao atualizar os dados", ActivityPreferencia.this);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId() ) {
            case R.id.swit_venda_1:
                if ( b) {
                    dias_hist_venda = 30;
                    swit_venda_2.setChecked(!b);
                    swit_venda_3.setChecked(!b);
                    swit_venda_4.setChecked(!b);
                    swit_venda_5.setChecked(!b);
                    swit_venda_6.setChecked(!b);
                }
                break;
            case R.id.swit_venda_2:
                if ( b) {
                    dias_hist_venda = 60;
                    swit_venda_1.setChecked(!b);
                    swit_venda_3.setChecked(!b);
                    swit_venda_4.setChecked(!b);
                    swit_venda_5.setChecked(!b);
                    swit_venda_6.setChecked(!b);
                }
                break;
            case R.id.swit_venda_3:
                if ( b) {
                    dias_hist_venda = 90;
                    swit_venda_1.setChecked(!b);
                    swit_venda_2.setChecked(!b);
                    swit_venda_4.setChecked(!b);
                    swit_venda_5.setChecked(!b);
                    swit_venda_6.setChecked(!b);
                }
                break;
            case R.id.swit_venda_4:
                if ( b) {
                    dias_hist_venda = 120;
                    swit_venda_1.setChecked(!b);
                    swit_venda_2.setChecked(!b);
                    swit_venda_3.setChecked(!b);
                    swit_venda_5.setChecked(!b);
                    swit_venda_6.setChecked(!b);
                }
                break;
            case R.id.swit_venda_5:
                if ( b) {
                    dias_hist_venda = 150;
                    swit_venda_1.setChecked(!b);
                    swit_venda_2.setChecked(!b);
                    swit_venda_3.setChecked(!b);
                    swit_venda_4.setChecked(!b);
                    swit_venda_6.setChecked(!b);
                }
                break;
            case R.id.swit_venda_6:
                if ( b) {
                    dias_hist_venda = 180;
                    swit_venda_1.setChecked(!b);
                    swit_venda_2.setChecked(!b);
                    swit_venda_3.setChecked(!b);
                    swit_venda_4.setChecked(!b);
                    swit_venda_5.setChecked(!b);
                }
                break;

            case R.id.swit_fin_1:
                if ( b) {
                    dias_hist_financeiro = 30;
                    swit_fin_2.setChecked(!b);
                    swit_fin_3.setChecked(!b);
                    swit_fin_4.setChecked(!b);
                    swit_fin_5.setChecked(!b);
                    swit_fin_6.setChecked(!b);
                }
                break;
            case R.id.swit_fin_2:
                if ( b) {
                    dias_hist_financeiro = 60;
                    swit_fin_1.setChecked(!b);
                    swit_fin_3.setChecked(!b);
                    swit_fin_4.setChecked(!b);
                    swit_fin_5.setChecked(!b);
                    swit_fin_6.setChecked(!b);
                }
                break;
            case R.id.swit_fin_3:
                if ( b) {
                    dias_hist_financeiro = 90;
                    swit_fin_1.setChecked(!b);
                    swit_fin_2.setChecked(!b);
                    swit_fin_4.setChecked(!b);
                    swit_fin_5.setChecked(!b);
                    swit_fin_6.setChecked(!b);
                }
                break;
            case R.id.swit_fin_4:
                if ( b) {
                    dias_hist_financeiro = 120;
                    swit_fin_1.setChecked(!b);
                    swit_fin_2.setChecked(!b);
                    swit_fin_3.setChecked(!b);
                    swit_fin_5.setChecked(!b);
                    swit_fin_6.setChecked(!b);
                }
                break;
            case R.id.swit_fin_5:
                if ( b) {
                    dias_hist_financeiro = 150;
                    swit_fin_1.setChecked(!b);
                    swit_fin_2.setChecked(!b);
                    swit_fin_3.setChecked(!b);
                    swit_fin_4.setChecked(!b);
                    swit_fin_6.setChecked(!b);
                }
                break;
            case R.id.swit_fin_6:
                if ( b) {
                    dias_hist_financeiro = 180;
                    swit_fin_1.setChecked(!b);
                    swit_fin_2.setChecked(!b);
                    swit_fin_3.setChecked(!b);
                    swit_fin_4.setChecked(!b);
                    swit_fin_5.setChecked(!b);
                }
                break;

            case R.id.swit_ped_1:
                if ( b) {
                    dias_hist_pedido = 1;
                    swit_ped_2.setChecked(!b);
                    swit_ped_3.setChecked(!b);
                    swit_ped_4.setChecked(!b);
                    swit_ped_5.setChecked(!b);
                    swit_ped_6.setChecked(!b);
                }
                break;
            case R.id.swit_ped_2:
                if ( b) {
                    dias_hist_pedido = 7;
                    swit_ped_1.setChecked(!b);
                    swit_ped_3.setChecked(!b);
                    swit_ped_4.setChecked(!b);
                    swit_ped_5.setChecked(!b);
                    swit_ped_6.setChecked(!b);
                }
                break;
            case R.id.swit_ped_3:
                if ( b) {
                    dias_hist_pedido = 15;
                    swit_ped_1.setChecked(!b);
                    swit_ped_2.setChecked(!b);
                    swit_ped_4.setChecked(!b);
                    swit_ped_5.setChecked(!b);
                    swit_ped_6.setChecked(!b);
                }
                break;
            case R.id.swit_ped_4:
                if ( b) {
                    dias_hist_pedido = 30;
                    swit_ped_1.setChecked(!b);
                    swit_ped_2.setChecked(!b);
                    swit_ped_3.setChecked(!b);
                    swit_ped_5.setChecked(!b);
                    swit_ped_6.setChecked(!b);
                }
                break;
            case R.id.swit_ped_5:
                if ( b) {
                    dias_hist_pedido = 60;
                    swit_ped_1.setChecked(!b);
                    swit_ped_2.setChecked(!b);
                    swit_ped_3.setChecked(!b);
                    swit_ped_4.setChecked(!b);
                    swit_ped_6.setChecked(!b);
                }
                break;
            case R.id.swit_ped_6:
                if ( b) {
                    dias_hist_pedido = 90;
                    swit_ped_1.setChecked(!b);
                    swit_ped_2.setChecked(!b);
                    swit_ped_3.setChecked(!b);
                    swit_ped_4.setChecked(!b);
                    swit_ped_5.setChecked(!b);
                }
                break;
        }
    }

}

            /*
            if ( preferencia.getDias_hist_venda() <= 30) {
                swit_venda_1.setChecked(true);
                dias_hist_venda = 30;
            } else if( preferencia.getDias_hist_venda() == 60) {
                swit_venda_2.setChecked(true);
                dias_hist_venda = 60;
            }else if( preferencia.getDias_hist_venda() == 90) {
                    swit_venda_3.setChecked(true);
                dias_hist_venda = 90;
            }else if( preferencia.getDias_hist_venda() == 120) {
                swit_venda_4.setChecked(true);
                dias_hist_venda = 120;
            }else if( preferencia.getDias_hist_venda() == 150) {
                swit_venda_5.setChecked(true);
                dias_hist_venda = 150;
            }else if( preferencia.getDias_hist_venda() == 180) {
                swit_venda_6.setChecked(true);
                dias_hist_venda = 180;
            }

             */

            /*
            if (preferencia.getDias_hist_financeiro() <= 30) {
                swit_fin_1.setChecked(true);
                dias_hist_financeiro = 30;
            } else if(preferencia.getDias_hist_financeiro() == 60) {
                swit_fin_2.setChecked(true);
                dias_hist_financeiro = 60;
            } else if(preferencia.getDias_hist_financeiro() == 90) {
                swit_fin_3.setChecked(true);
                dias_hist_financeiro = 90;
            } else if(preferencia.getDias_hist_financeiro() == 120) {
                swit_fin_4.setChecked(true);
                dias_hist_financeiro = 120;
            } else if(preferencia.getDias_hist_financeiro() == 150) {
                swit_fin_5.setChecked(true);
                dias_hist_financeiro = 150;
            } else if(preferencia.getDias_hist_financeiro() == 180) {
                swit_fin_6.setChecked(true);
                dias_hist_financeiro = 180;
            }

             */
            /*

            if (preferencia.getDias_hist_pedido() == 1) {
                swit_ped_1.setChecked(true);
                dias_hist_pedido = 1;
            } else if(preferencia.getDias_hist_pedido() == 7) {
                swit_ped_2.setChecked(true);
                dias_hist_pedido = 7;
            } else if(preferencia.getDias_hist_pedido() == 15) {
                swit_ped_3.setChecked(true);
                dias_hist_pedido = 15;
            } else if(preferencia.getDias_hist_pedido() == 30) {
                swit_ped_4.setChecked(true);
                dias_hist_pedido = 30;
            } else if(preferencia.getDias_hist_pedido() == 60) {
                swit_ped_5.setChecked(true);
                dias_hist_pedido = 60;
            } else if(preferencia.getDias_hist_pedido() == 90) {
                swit_ped_6.setChecked(true);
                dias_hist_pedido = 90;
            }

             */

