package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.FinanceiroDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaFinanceiroCobrancaAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaHistoricoFinanceiroTKSAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.FinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricoFinanceiroTKSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoFinanceiroTKSFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String idCliente;
    private String situacao;
    private Usuario usuario;
    private Configuracao configuracao;
    private Utilitaria util = new Utilitaria();
    private DBHelper db;
    private ListaHistoricoFinanceiroTKSAdapter adapter;
    private View view;
    private TextView txvPontualidade, txvDataVencimento;

    public HistoricoFinanceiroTKSFragment( String idCliente, String situacao) {
        this.idCliente     = idCliente;
        this.situacao      = situacao;
    }

    public static HistoricoFinanceiroTKSFragment newInstance( String idCliente, String situacao) {
        HistoricoFinanceiroTKSFragment fragment = new HistoricoFinanceiroTKSFragment(idCliente, situacao);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_historico_financeiro_tks, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        txvPontualidade   = view.findViewById(R.id.txvPontualidade);
        txvDataVencimento = view.findViewById(R.id.txvDataVencimento);
        if ( situacao.equalsIgnoreCase("Q")) {
            txvPontualidade.setText("Pont.");
            txvDataVencimento.setText("Pgto");
        }
        db = new DBHelper(getContext());
        getListaTitulos(idCliente, situacao);
        return view;
    }

    private void getListaTitulos(String idCliente, String situacao ) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");


        if (!configuracao.getId().isEmpty()) {
            final Rotas apiRotas = Api.buildRetrofit(false);
            Map<String, String> cabecalho = new HashMap<>();
            cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
            Call<List<FinanceiroResumo>> call = apiRotas.gethistoricoFinanceiroCliente(String.valueOf(UsuarioHelper.getUsuario().getId_usuario()), Integer.parseInt(idCliente), situacao, cabecalho);
            call.enqueue(new Callback<List<FinanceiroResumo>>() {
                @Override
                public void onResponse(Call<List<FinanceiroResumo>> call, Response<List<FinanceiroResumo>> response) {
                    if (response.code() == 200) {
                        List<FinanceiroResumo> lista = response.body();
                       setRecyclerView(lista);
                    }
                }

                @Override
                public void onFailure(Call<List<FinanceiroResumo>> call, Throwable t) {
                    Toast.makeText(getContext(), "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }



    }

    private void setRecyclerView(List<FinanceiroResumo> lista) {
        adapter = new ListaHistoricoFinanceiroTKSAdapter(lista,  getContext(), situacao);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}