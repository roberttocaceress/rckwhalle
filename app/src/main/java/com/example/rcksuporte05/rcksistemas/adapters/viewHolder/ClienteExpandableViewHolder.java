package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
//import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
//import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class ClienteExpandableViewHolder /*extends GroupViewHolder*/ {

    public TextView textView_parent, txv_nome_cliente;

    public ClienteExpandableViewHolder(View itemView) {
       // super(itemView);
        textView_parent = itemView.findViewById(R.id.txv_cliente_parent);
        txv_nome_cliente = itemView.findViewById(R.id.txv_nome_cliente);
    }

    /*
    @Override
    public void expand() {
        super.expand();
        textView_parent.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_up_arrow,0);
    }

     */

    /*
    @Override
    public void collapse() {
        super.collapse();
        textView_parent.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_down_arrow,0);
    }

     */

    /*
    public void setGroupName(ExpandableGroup groupName){
        txv_nome_cliente.setText(groupName.getTitle());
    }

     */


}