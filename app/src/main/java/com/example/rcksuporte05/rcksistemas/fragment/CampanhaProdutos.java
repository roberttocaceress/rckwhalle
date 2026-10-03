package com.example.rcksuporte05.rcksistemas.fragment;


import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialItensDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityItemLinhaProduto;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCampanhaltensAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCampanhaltensAdapter.Listener;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaProdutos extends Fragment implements Listener {

    @BindView(R.id.recycleCampanhaProdutos)
    RecyclerView recyclerView;
    @BindView(R.id.edtTotalProdutos)
    EditText edtTotalProdutos;

    private View view;
    private DBHelper db;
    private ListaCampanhaltensAdapter adapter;
    CampanhaComercialItensDAO campanhaComercialItensDAO;
    private Configuracao configuracao = new Configuracao();
    private int offSet = 0;
    private int totalReg = 0;
    private int limitReg = 50;
    private String SQL;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_campanha_produtos, container, false);
        ButterKnife.bind(this, view);
        db = new DBHelper(getActivity());
        campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CampanhaProdutos.ScrollListener());
        getConfiguracao();

        showDados();
        return view;
    }

    @Override
    public void onClickListener(int position) {
    }

    @Override
    public View.OnClickListener onInfoClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityItemLinhaProduto.class);
                intent.putExtra("linha", adapter.getItem(position).getIdLinhaProduto());
                intent.putExtra("nomelinha", adapter.getItem(position).getNomeProdutoLinha());
                startActivity(intent);
            }
        };
    }

    private  void showDados() {
        try {
            SQL = "SELECT  COUNT(*) AS TOTALREG  FROM TBL_CAMPANHA_COMERCIAL_ITENS WHERE ID_CAMPANHA = " + CampanhaHelper.getCampanhaComercialCab().getIdCampanha() + ";";
            totalReg = campanhaComercialItensDAO.getTotalReg(SQL);
            List<CampanhaComercialItens> lista = campanhaComercialItensDAO.getLista(CampanhaHelper.getCampanhaComercialCab());
            edtTotalProdutos.setText(lista.size() + " Produto(s) ");
            adapter = new ListaCampanhaltensAdapter(lista, CampanhaProdutos.this);
            recyclerView.setAdapter(adapter);
            if (lista.size() > 0)
                edtTotalProdutos.setText("1/" + totalReg);
            else
                edtTotalProdutos.setText("0/" + totalReg);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            edtTotalProdutos.setText("0/0");
        }
    }
    private void getConfiguracao() {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(db);
        configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
    }
    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            edtTotalProdutos.setText( "(" + (getFirstItem() + 1) + " A " + (getLastItem() + 1) + ")/" + totalReg);
        }

    }
    private int getFirstItem(){
        return ((LinearLayoutManager)recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private int getLastItem() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastVisibleItemPosition();
    }
}
