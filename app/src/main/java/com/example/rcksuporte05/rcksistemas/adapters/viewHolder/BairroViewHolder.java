package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.BairroAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.MunicipioAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BairroViewHolder  extends RecyclerView.ViewHolder {

    @BindView(R.id.txtBairro)
    public TextView txtBairro;

    @BindView(R.id.rlItemBairros)
    public RelativeLayout rlItemBairros;

    public BairroViewHolder(View itemView, final BairroAdapter.bairroListener listener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(getAdapterPosition());
            }
        });
        ButterKnife.bind(this, itemView);
    }
}
