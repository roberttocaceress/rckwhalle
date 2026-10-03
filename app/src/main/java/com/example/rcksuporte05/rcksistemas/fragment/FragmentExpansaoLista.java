package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.ExpansaoCarteira;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentExpansaoLista extends Fragment {

    @BindView(R.id.rvExpansaoLista)
    RecyclerView recyclerView;

    private static List<ExpansaoCarteira> dados;

    public static FragmentExpansaoLista newInstance(List<ExpansaoCarteira> lista) {
        dados = lista;
        return new FragmentExpansaoLista();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expansao_lista_2026, container, false);
        ButterKnife.bind(this, view);
        configurarLista();
        return view;
    }

    private void configurarLista() {
        if (dados == null || dados.isEmpty()) return;

        List<Object> itensLista = new ArrayList<>();
        
        // Se for vendedor, pega o primeiro item. Se for gestor, soma tudo ou permite filtro.
        // Para a lista premium, vamos consolidar os meses de todos os vendedores ou do selecionado.
        
        for (ExpansaoCarteira exp : dados) {
            if (exp.getMeses() != null) {
                for (ExpansaoCarteira.ExpansaoMensal mes : exp.getMeses()) {
                    itensLista.add(mes); // Header do Mês
                    if (mes.getClientes() != null) {
                        itensLista.addAll(mes.getClientes()); // Linhas de Clientes
                    }
                }
            }
        }

        ExpansaoListaAdapter adapter = new ExpansaoListaAdapter(itensLista);
        recyclerView.setAdapter(adapter);
    }

    // --- ADAPTER INTERNO PARA A LISTA AGRUPADA ---
    private class ExpansaoListaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_CLIENTE = 1;
        private List<Object> itens;

        public ExpansaoListaAdapter(List<Object> itens) {
            this.itens = itens;
        }

        @Override
        public int getItemViewType(int position) {
            return (itens.get(position) instanceof ExpansaoCarteira.ExpansaoMensal) ? TYPE_HEADER : TYPE_CLIENTE;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expansao_header_mes, parent, false);
                return new HeaderViewHolder(v);
            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expansao_cliente, parent, false);
                return new ClienteViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_HEADER) {
                ExpansaoCarteira.ExpansaoMensal mes = (ExpansaoCarteira.ExpansaoMensal) itens.get(position);
                HeaderViewHolder h = (HeaderViewHolder) holder;
                h.tvMes.setText(mes.getMesNome().toUpperCase());
                h.tvTotal.setText(mes.getTotalMes() + " NOVOS");
            } else {
                ExpansaoCarteira.ClienteExpansao cli = (ExpansaoCarteira.ClienteExpansao) itens.get(position);
                ClienteViewHolder h = (ClienteViewHolder) holder;
                h.tvData.setText(cli.getDataInclusao());
                h.tvRazao.setText(cli.getRazaoSocial());
                h.tvFantasia.setText(cli.getNomeFantasia());
            }
        }

        @Override
        public int getItemCount() {
            return itens.size();
        }

        class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView tvMes, tvTotal;
            HeaderViewHolder(View v) {
                super(v);
                tvMes = v.findViewById(R.id.tvHeaderMesNome);
                tvTotal = v.findViewById(R.id.tvHeaderMesTotal);
            }
        }

        class ClienteViewHolder extends RecyclerView.ViewHolder {
            TextView tvData, tvRazao, tvFantasia;
            ClienteViewHolder(View v) {
                super(v);
                tvData = v.findViewById(R.id.tvItemExpansaoData);
                tvRazao = v.findViewById(R.id.tvItemExpansaoRazao);
                tvFantasia = v.findViewById(R.id.tvItemExpansaoFantasia);
            }
        }
    }
}
