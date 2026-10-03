package com.example.rcksuporte05.rcksistemas.activity;

import static java.security.AccessController.getContext;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDataFiltro extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnDiaAtual)
    Button btnDiaAtual;

    @BindView(R.id.btnDiaAnterior)
    Button btnDiaAnterior;

    @BindView(R.id.btnSemanaAnterior)
    Button btnSemanaAnterior;

    @BindView(R.id.btnSemanaAtual)
    Button btnSemanaAtual;

    @BindView(R.id.btnMesAnterior)
    Button btnMesAnterior;

    @BindView(R.id.btnMesAtual)
    Button btnMesAtual;

    @BindView(R.id.btnCalendario)
    Button btnCalendario;

    @BindView(R.id.btnFiltrar)
    Button btnFiltar;

    @BindView(R.id.btnCancelar)
    Button btnCancelar;

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


    @BindView(R.id.layoutVendedores)
    LinearLayout layoutVendedores;

    @BindView(R.id.spVendedores)
    Spinner spVendedores;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String dataIni, dataFin;
    private DBHelper db = new DBHelper(this);
    private String tipoRelatorio;
    private int tExtraFonte  = 0;
    private ArrayAdapter<Usuario> adapterVendedores;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_filtro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            View toolbar = findViewById(R.id.toolbar);
            toolbar.setPadding(0, insets.top, 0, 0);
            View rodape = findViewById(R.id.btnCancelar);
            rodape.setPadding(0, 0, 0, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btnDiaAtual.setOnClickListener(this);
        btnDiaAnterior.setOnClickListener(this);
        btnSemanaAtual.setOnClickListener(this);
        btnSemanaAnterior.setOnClickListener(this);
        btnMesAtual.setOnClickListener(this);
        btnMesAnterior.setOnClickListener(this);
        btnFiltar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnCalendario.setOnClickListener(this);
        //btnCalendario.setEnabled(false);
        setTextButton();
        btnDiaAtual.callOnClick();
        tipoRelatorio = getIntent().getStringExtra("tipoRelatorio");
        if (UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("G") || UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("A") ||
                UsuarioHelper.getUsuario().getUsuario_tipo().equalsIgnoreCase("D")) {
            List<Usuario> lista = new UsuarioDAO(db).getLista("SELECT * FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND ID_QUANDO_VENDEDOR IS NOT NULL " +
                    "AND USUARIO_TIPO <> 'D' AND NOME_USUARIO <> '' ORDER BY NOME_USUARIO");
            Usuario usuario = new Usuario();
            usuario.setId_usuario("999");
            usuario.setId_quando_vendedor("999");
            //usuario.setId_usuario("999");
            usuario.setNome_usuario("TODOS");
            //lista.add(usuario);
            List<Usuario> listaAdapter = new ArrayList<>();
            listaAdapter.add(usuario);
            for (Usuario usuario1 : lista) {
                listaAdapter.add(usuario1);
            }
            adapterVendedores = new ArrayAdapter<>( context,
                    tExtraFonte == 0 ?  R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g,
                    listaAdapter);
            spVendedores.setAdapter(adapterVendedores);

        } else {
            List<Usuario> lista = new ArrayList<>();
            lista.add(UsuarioHelper.getUsuario());
            adapterVendedores = new ArrayAdapter<>( context,
                    tExtraFonte == 0 ?  R.layout.simple_list_item_spinner_p : tExtraFonte == 2 ? R.layout.simple_list_item_spinner_m : R.layout.simple_list_item_spinner_g,
                    lista);
            spVendedores.setAdapter(adapterVendedores);
        }
    }

    private void setTextButton() {

        btnDiaAtual.setText(showDataMesAno(db.pegaDataInicial("SELECT date('now','start of month');")));
        btnDiaAnterior.setText(showDataMesAno(db.pegaDataInicial("SELECT date('now','start of month', '-1 month');")));
        btnSemanaAtual.setText(showDataMesAno(db.pegaDataInicial("SELECT date('now','start of month', '-2 month');")));
        btnSemanaAnterior.setText(showDataMesAno(db.pegaDataInicial("SELECT date('now','start of month', '-3 month');")));
        btnMesAtual.setText(showDataMesAno(db.pegaDataInicial("SELECT date('now','start of month', '-4 month');")));
        btnMesAnterior.setText(showDataMesAno(db.pegaDataInicial("SELECT date('now','start of month', '-5 month');")));

    }

    private String showDataMesAno(String data) {
        if (data.substring(5, 7).equalsIgnoreCase("01"))
            data =   "JAN" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("02"))
            data =   "FEV" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("03"))
            data =   "MAR" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("04"))
            data =   "ABR" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("05"))
            data =   "MAI" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("06"))
            data =   "JUN" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("07"))
            data =   "JUL" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("08"))
            data =   "AGO" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("09"))
            data =   "SET" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("10"))
            data =   "OUT" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("11"))
            data =   "NOV" + " - " + "" + data.substring(0,4);
        if ( data.substring(5,7).equalsIgnoreCase("12"))
            data =   "DEZ" + " - " + "" + data.substring(0,4);

        return data;
    }

    private String showDataAno(String anoInicial, String anoFinal) {
        if ( !anoFinal.isEmpty())
            return anoFinal.substring(0,4) + "  -  " + anoInicial.substring(0,4);
        else
            return anoInicial.substring(0,4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFiltrar:
                if ( tipoRelatorio == null) {
                    tipoRelatorio = getIntent().getStringExtra("tipoRelatorio");
                }
                startActivity(new Intent(ActivityDataFiltro.this, ActivityParticipacaoFaturamento.class)
                        .putExtra("dataInicial", dataIni)
                        .putExtra("dataFinal", dataFin)
                        .putExtra("tipoRelatorio", tipoRelatorio)
                        .putExtra("idVendedor", adapterVendedores.getItem(spVendedores.getSelectedItemPosition()).getId_usuario()));
                break;
            case R.id.btnCancelar:
                setResult(RESULT_CANCELED, new Intent());
                finish();
                break;

            case R.id.btnDiaAtual:
                btnDiaAtual.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
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
                setData(db.pegaDataInicial("SELECT date('now','start of month');"), db.pegaDataFinal("SELECT date('now', 'localtime');"));
                break;
            case R.id.btnDiaAnterior:
                btnDiaAnterior.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
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
                setData(db.pegaDataInicial("SELECT date('now','start of month', '-1 month');"), db.pegaDataFinal("SELECT date('now','start of month', '-1 day');"));
                break;
            case R.id.btnSemanaAtual:
                btnSemanaAtual.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
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
                setData(db.pegaDataInicial("SELECT date('now','start of month', '-2 month');"), db.pegaDataFinal("SELECT date('now','start of month', '-1 month', '-1 day');"));
                break;
            case R.id.btnSemanaAnterior:
                btnSemanaAnterior.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
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
                setData(db.pegaDataInicial("SELECT date('now','start of month', '-3 month');"), db.pegaDataFinal("SELECT date('now','start of month', '-2 month', '-1 day');"));
                break;
            case R.id.btnMesAtual:
                btnMesAtual.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
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
                setData(db.pegaDataInicial("SELECT date('now','start of month', '-4 month');"), db.pegaDataFinal("SELECT date('now','start of month', '-3 month', '-1 day');"));
                break;
            case R.id.btnMesAnterior:
                btnMesAnterior.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
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
                setData(db.pegaDataInicial("SELECT date('now','start of month', '-5 month');"), db.pegaDataFinal("SELECT date('now','start of month', '-4 month', '-1 day');"));
                break;
            case R.id.btnCalendario:
                btnCalendario.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
                btnCalendario.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.branco)));

                btnMesAnterior.setBackgroundTintList(null);
                btnMesAnterior.setBackgroundResource(R.drawable.borda_botao_text);
                btnMesAnterior.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));

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

                startActivityForResult(new Intent(getApplicationContext(),  CalendarioActivity.class), 100);
                break;
            default:
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode >= 0 && resultCode == RESULT_OK) {
                if (requestCode == 100) {
                    String dataInicial = data.getStringExtra("dataInicial");
                    String dataFinal = data.getStringExtra("dataFinal");
                    setData(dataInicial, dataFinal);
                } else {
                    Toast.makeText(getApplicationContext(), "Falha ao obter o periodo para p relatorio", Toast.LENGTH_SHORT).show();
                }

            }

        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}