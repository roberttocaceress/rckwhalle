package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
//import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
public class ClienteChildViewHolders /*extends ChildViewHolder*/{
    public RelativeLayout ly_lista_cliente_expand;
    public TextView txv_id_cliente, txv_nome_fantasia;

    public  TextView txv_data_ult_compra;
    public ImageView img_status_cliente;

    public ClienteChildViewHolders(View itemView) {
        //super(itemView);
        ly_lista_cliente_expand = itemView.findViewById(R.id.ly_lista_cliente_expand);
        txv_id_cliente = itemView.findViewById(R.id.txv_id_cliente);
        txv_nome_fantasia = itemView.findViewById(R.id.txv_nome_fantasia);
        txv_data_ult_compra = itemView.findViewById(R.id.txv_data_ult_compra);
        img_status_cliente = itemView.findViewById(R.id.img_status_cliente);
    }

    public void setChildTextFantasia(String nome_fantasia){
        try {
            txv_nome_fantasia.setText(nome_fantasia);
        } catch ( NullPointerException e) {
            txv_nome_fantasia.setText("");
        }
    }
    public void setChildTextIdCliente(String id_cliente){
        txv_id_cliente.setText(id_cliente);
    }

    public void setChildTextDataUltCompra(String data_ult_compra){
        try {
            txv_data_ult_compra.setText(data_ult_compra);
        } catch (NullPointerException e ) {
            txv_data_ult_compra.setText("");
        }
    }

    public void setImagemStatus( ){}


}

