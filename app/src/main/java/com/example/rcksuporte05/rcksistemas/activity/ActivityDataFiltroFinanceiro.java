package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDataFiltroFinanceiro extends AppCompatActivity implements View.OnClickListener , CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.btnDiaAnterior)
    Button btnDiaAnterior;

    @BindView(R.id.btnDiaAtual)
    Button btnDiaAtual;

    @BindView(R.id.btnMesAtual)
    Button btnMesAtual;

    @BindView(R.id.btnMesAnterior)
    Button btnMesAnterior;

    @BindView(R.id.btnSemanaAtual)
    Button btnSemanaAtual;

    @BindView(R.id.btnSemanaAnterior)
    Button btnSemanaAnterior;

    @BindView(R.id.btnCalendario)
    Button btnCalendario;

    @BindView(R.id.btnFiltrar)
    Button btnFiltrar;

    @BindView(R.id.btnCancelar)
    Button btnCancelar;

    @BindView(R.id.opcaoEmissao)
    CheckBox opcaoEmissao;

    @BindView(R.id.opcaoCompetencia)
    CheckBox opcaoCompetencia;

    @BindView(R.id.opcaoVencimento)
    CheckBox opcaoVencimento;

    @BindView(R.id.opcaoQuitacao)
    CheckBox opcaoQuitacao;


    @BindView(R.id.txvDiaInicial)
    TextView txvDiaInicial;

    @BindView(R.id.txvDiaFinal)
    TextView txvDiaFinal;

    @BindView(R.id.txvAnoInicial)
    TextView txvAnoInicial;

    @BindView(R.id.txvAnoFinal)
    TextView txvAnoFinal;

    @BindView(R.id.txvMesInicial)
    TextView txvMesInicial;

    @BindView(R.id.txvMesFinal)
    TextView txvMesFinal;
    //
    private String dataIni, dataFin, periodo, debitoCredito;
    private Context context;
    private DBHelper db = new DBHelper(this);
    private String periodoSelecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_data_filtro_financeiro);
        ButterKnife.bind(this);

        btnDiaAnterior.setOnClickListener(this);
        btnDiaAtual.setOnClickListener(this);
        btnMesAtual.setOnClickListener(this);
        btnMesAnterior.setOnClickListener(this);
        btnSemanaAtual.setOnClickListener(this);
        btnSemanaAnterior.setOnClickListener(this);
        btnCalendario.setOnClickListener(this);
        btnFiltrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnDiaAtual.callOnClick();

        opcaoEmissao.setOnCheckedChangeListener(this);
        opcaoCompetencia.setOnCheckedChangeListener(this);
        opcaoVencimento.setOnCheckedChangeListener(this);
        opcaoQuitacao.setOnCheckedChangeListener(this);
        opcaoQuitacao.setVisibility(View.INVISIBLE);

        opcaoVencimento.setChecked(false);
        opcaoVencimento.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFiltrar:
                context.startActivity( new Intent(context, ActivityFinanceiroCobranca.class)
                        .putExtra("debitoCredito", "C")
                        .putExtra("dataInicial"  , dataIni )
                        .putExtra("dataFinal"    , dataFin)
                        .putExtra("periodoStatus", periodo)
                );

                break;
            case R.id.btnCancelar:
                setResult(RESULT_CANCELED, new Intent());
                finish();
                break;

            case R.id.btnDiaAtual:
                btnDiaAtual.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                btnDiaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnDiaAnterior.setBackgroundTintList(null);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnSemanaAnterior.setBackgroundTintList(null);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnSemanaAtual.setBackgroundTintList(null);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnMesAnterior.setBackgroundTintList(null);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnMesAtual.setBackgroundTintList(null);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnCalendario.setBackgroundTintList(null);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_text);
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                periodoSelecionado = "DE HOJE";

                setData( db.pegaDataInicial("SELECT date('now','localtime');"), db.pegaDataFinal("SELECT date('now','localtime');"));
                break;
            case R.id.btnDiaAnterior:
                btnDiaAnterior.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                btnDiaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnDiaAtual.setBackgroundTintList(null);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnSemanaAnterior.setBackgroundTintList(null);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnSemanaAtual.setBackgroundTintList(null);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnMesAnterior.setBackgroundTintList(null);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnMesAtual.setBackgroundTintList(null);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnCalendario.setBackgroundTintList(null);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_text);
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                periodoSelecionado = "DE ONTEM";

                setData( db.pegaDataInicial("SELECT date('now','-1 day','localtime');"), db.pegaDataFinal("SELECT date('now','-1 day','localtime');") );
                break;
            case R.id.btnSemanaAtual:
                btnSemanaAtual.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                btnSemanaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnSemanaAnterior.setBackgroundTintList(null);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnDiaAtual.setBackgroundTintList(null);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnDiaAnterior.setBackgroundTintList(null);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnMesAnterior.setBackgroundTintList(null);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnMesAtual.setBackgroundTintList(null);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnCalendario.setBackgroundTintList(null);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_text);
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                periodoSelecionado = "DESTA SEMANA";
                setData( db.pegaDataInicial("SELECT date('now','weekday 0', '-7 days');"), db.pegaDataFinal("SELECT date('now','weekday 0', '-1 days');") );
                break;
            case R.id.btnSemanaAnterior:
                btnSemanaAnterior.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                btnSemanaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnSemanaAtual.setBackgroundTintList(null);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnDiaAtual.setBackgroundTintList(null);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnDiaAnterior.setBackgroundTintList(null);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnMesAnterior.setBackgroundTintList(null);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnMesAtual.setBackgroundTintList(null);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnCalendario.setBackgroundTintList(null);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_text);
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                periodoSelecionado = "DA SEMANA PASSADA";
                setData( db.pegaDataInicial("SELECT date('now','weekday 0', '-14 days');"), db.pegaDataFinal("SELECT date('now','weekday 0', '-8 days');") );
                break;
            case R.id.btnMesAtual:
                btnMesAtual.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                btnMesAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnMesAnterior.setBackgroundTintList(null);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnDiaAtual.setBackgroundTintList(null);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnDiaAnterior.setBackgroundTintList(null);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnSemanaAnterior.setBackgroundTintList(null);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnSemanaAtual.setBackgroundTintList(null);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnCalendario.setBackgroundTintList(null);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_text);
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                periodoSelecionado = "DESTE MÊS";
                setData( db.pegaDataInicial("SELECT date('now','start of month');"), db.pegaDataFinal("SELECT date('now','start of month', '+1 month', '-1 days');") );
                break;
            case R.id.btnMesAnterior:
                btnMesAnterior.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnMesAtual.setBackgroundTintList(null);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnDiaAtual.setBackgroundTintList(null);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnDiaAnterior.setBackgroundTintList(null);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnSemanaAnterior.setBackgroundTintList(null);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnSemanaAtual.setBackgroundTintList(null);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnCalendario.setBackgroundTintList(null);
                btnCalendario.setBackgroundResource(R.drawable.borda_botao_text);
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                periodoSelecionado = "DO MÊS PASSADO";
                setData( db.pegaDataInicial("SELECT date('now','start of month', '-1 month');"), db.pegaDataFinal("SELECT date('now','start of month', '-1 day');") );
                break;
            case  R.id.btnCalendario:
                btnCalendario.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnMesAtual.setBackgroundTintList(null);
                btnMesAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnMesAnterior.setBackgroundTintList(null);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));


                btnDiaAtual.setBackgroundTintList(null);
                btnDiaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnDiaAnterior.setBackgroundTintList(null);
                btnDiaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnDiaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

                btnSemanaAnterior.setBackgroundTintList(null);
                btnSemanaAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                btnSemanaAtual.setBackgroundTintList(null);
                btnSemanaAtual.setBackgroundResource(R.drawable.borda_botao_text);
                btnSemanaAtual.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
                //activityResultLauncher.launch(new Intent(context,  CalendarioActivity.class));
                startActivityForResult(new Intent(ActivityDataFiltroFinanceiro.this,  CalendarioActivity.class), 100);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode >= 0 && resultCode == RESULT_OK) {
                if (requestCode == 100) {
                    String dataInicial = data.getStringExtra("dataInicial");
                    String dataFinal = data.getStringExtra("dataFinal");
                    setData(dataInicial, dataFinal);
                }

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.opcaoEmissao:
                if (isChecked) {
                    periodo = "E";
                    opcaoCompetencia.setChecked(!isChecked);
                    opcaoVencimento.setChecked(!isChecked);
                    opcaoQuitacao.setChecked(!isChecked);
                }
                break;

            case R.id.opcaoCompetencia:
                if (isChecked) {
                    periodo = "C";
                    opcaoEmissao.setChecked(!isChecked);
                    opcaoVencimento.setChecked(!isChecked);
                    opcaoQuitacao.setChecked(!isChecked);
                }
                break;
            case R.id.opcaoVencimento:
                if (isChecked) {
                    periodo = "V";
                    opcaoEmissao.setChecked(!isChecked);
                    opcaoCompetencia.setChecked(!isChecked);
                    opcaoQuitacao.setChecked(!isChecked);
                }
                break;
            case R.id.opcaoQuitacao:
                if (isChecked) {
                    periodo = "Q";
                    opcaoEmissao.setChecked(!isChecked);
                    opcaoCompetencia.setChecked(!isChecked);
                    opcaoVencimento.setChecked(!isChecked);
                }
                break;
        }
    }

    private void setData(String dataInicial, String dataFinal) {
        dataIni = dataInicial;
        dataFin = dataFinal;
        txvDiaInicial.setText( dataInicial.substring(8));
        txvDiaFinal.setText( dataFinal.substring(8));
        txvAnoInicial.setText( dataInicial.substring(0,4));
        txvAnoFinal.setText( dataFinal.substring(0,4));
        txvMesInicial.setText(dataInicial.substring(5,7));
        txvMesFinal.setText(dataFinal.substring(5,7));
        if ( dataInicial.substring(5,7).equalsIgnoreCase("01"))
            txvMesInicial.setText("JAN");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("02"))
            txvMesInicial.setText("FEV");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("03"))
            txvMesInicial.setText("MAR");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("04"))
            txvMesInicial.setText("ABR");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("05"))
            txvMesInicial.setText("MAI");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("06"))
            txvMesInicial.setText("JUN");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("07"))
            txvMesInicial.setText("JUL");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("08"))
            txvMesInicial.setText("AGO");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("09"))
            txvMesInicial.setText("SET");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("10"))
            txvMesInicial.setText("OUT");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("11"))
            txvMesInicial.setText("NOV");
        if ( dataInicial.substring(5,7).equalsIgnoreCase("12"))
            txvMesInicial.setText("DEZ");

        if ( dataFinal.substring(5,7).equalsIgnoreCase("01"))
            txvMesFinal.setText("JAN");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("02"))
            txvMesFinal.setText("FEV");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("03"))
            txvMesFinal.setText("MAR");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("04"))
            txvMesFinal.setText("ABR");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("05"))
            txvMesFinal.setText("MAI");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("06"))
            txvMesFinal.setText("JUN");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("07"))
            txvMesFinal.setText("JUL");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("08"))
            txvMesFinal.setText("AGO");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("09"))
            txvMesFinal.setText("SET");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("10"))
            txvMesFinal.setText("OUT");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("11"))
            txvMesFinal.setText("NOV");
        if ( dataFinal.substring(5,7).equalsIgnoreCase("12"))
            txvMesFinal.setText("DEZ");

    }


}