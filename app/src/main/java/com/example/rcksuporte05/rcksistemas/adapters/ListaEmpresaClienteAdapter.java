package com.example.rcksuporte05.rcksistemas.adapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ListaEmprasaClienteViewHolder;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ListaUsuarioViewHolder;
import com.example.rcksuporte05.rcksistemas.model.EmpresaParametro;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.ArrayList;
import java.util.List;

public class ListaEmpresaClienteAdapter extends RecyclerView.Adapter<ListaEmprasaClienteViewHolder>{

    private Context context;
    private List<EmpresaParametro> lista;
    private ListaEmpresaClienteAdapter.Listener listener;
    private String btnTitulo;
    public EmpresaParametro empresaParametro;
    public ArrayAdapter<String> adapterVersaoAPI;
    public ArrayList<String> listaVersaoAPI = new ArrayList<>();


    public String versaoAPI;
    public ListaEmpresaClienteAdapter(Context context, List<EmpresaParametro> lista, ListaEmpresaClienteAdapter.Listener listener, String btnTitulo) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
        this.btnTitulo = btnTitulo;
    }
    @NonNull
    @Override
    public ListaEmprasaClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_empresa_cliente, viewGroup, false);
        return new ListaEmprasaClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaEmprasaClienteViewHolder holder, int position) {
        empresaParametro = lista.get(position);
        holder.edtRazaoSocial.setText(empresaParametro.getRazao_social().toUpperCase());
        holder.edtIPUrl.setText(empresaParametro.getIp_url());
        holder.edtPorta.setText(String.valueOf(empresaParametro.getPorta()));
        //holder.edtVersaoAPI.setText( empresaParametro.getVersaoAPI());
        holder.swtConexaoSegura.setChecked(empresaParametro.getConexao_segura().isEmpty() || empresaParametro.getConexao_segura().equalsIgnoreCase("N") ? false : true);
        listaVersaoAPI.clear();
        listaVersaoAPI.add("2.7.3");
        listaVersaoAPI.add("2.7.2");
        listaVersaoAPI.add("2.7.1");
        listaVersaoAPI.add("2.7.0");
        adapterVersaoAPI = new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, listaVersaoAPI);
        holder.spVersaoAPI.setAdapter(adapterVersaoAPI);
        holder.spVersaoAPI.setSelection(0);

        lista.get(position).setVersaoAPI(listaVersaoAPI.get(0));

        holder.btnConfirmar.setText(btnTitulo);
        holder.spVersaoAPI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lista.get(position).setVersaoAPI(listaVersaoAPI.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        applyCLickEnvents(holder, position);
        System.gc();
    }



    @Override
    public int getItemCount() {
        if ( lista.size() > 0)
            return lista.size();
        return 0;
    }

    private void applyCLickEnvents(final ListaEmprasaClienteViewHolder holder, final int position) {

        holder.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public interface Listener {
        void onClickListener(int position);
    }
}
