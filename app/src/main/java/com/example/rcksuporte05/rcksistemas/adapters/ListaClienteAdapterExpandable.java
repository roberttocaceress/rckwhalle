package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ClienteChildViewHolders;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ClienteExpandableViewHolder;
import com.example.rcksuporte05.rcksistemas.model.ClienteChildData;
import com.example.rcksuporte05.rcksistemas.model.ClienteData;
//import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
//import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListaClienteAdapterExpandable/*  extends ExpandableRecyclerViewAdapter<ClienteExpandableViewHolder, ClienteChildViewHolders>*/ {

    public Context context;

    //public ListaClienteAdapterExpandable(Context context, List<? extends ExpandableGroup> groups) {
    //    super(groups);
    //    this.context = context;
   // }

    /*
    @Override
    public ClienteExpandableViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cliente_parent,parent,false);

        return new ClienteExpandableViewHolder(view);
    }
*/
    /*

    @Override
    public ClienteChildViewHolders onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cliente_lista_expandable,parent,false);
        return new ClienteChildViewHolders(view);
    }

     */


    /*
    @Override
    public void onBindChildViewHolder(ClienteChildViewHolders holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ClienteChildData childData = ((ClienteData)group).getItems().get(childIndex);
        holder.setChildTextDataUltCompra(childData.getUltima_compra());
        if ( childData.getId_cadastro_servidor() > 0)
            holder.setChildTextIdCliente( Integer.toString(childData.getId_cadastro_servidor()));
        else
            holder.setChildTextIdCliente( childData.getId_cliente());
        holder.setChildTextFantasia(childData.getNome_fantasia());
        try {
            if (new SimpleDateFormat("yyyy-MM-dd").parse(childData.getUltima_compra()).before(new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01")))
                holder.setChildTextDataUltCompra("Sem compra");
            else
                holder.setChildTextDataUltCompra("Não compra à " + calcularDias(new SimpleDateFormat("yyyy-MM-dd").parse(childData.getUltima_compra()), new Date()) + " dias");
        } catch (Exception e) {
            holder.setChildTextDataUltCompra("Sem compra");
            e.printStackTrace();
        }


        holder.ly_lista_cliente_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Selected : " + childData.getNome_cliente(), Toast.LENGTH_SHORT).show();
            }
        });

    }

     */

    /*
    @Override
    public void onBindGroupViewHolder(ClienteExpandableViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }
    */

    private String calcularDias(Date data1, Date data2) {
        return String.valueOf((data2.getTime() - data1.getTime()) / 86400000L);
    }
}