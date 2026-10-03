package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaBairroCheckAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BairroCheckViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txvBairro)
    public TextView txtBairro;
    @BindView(R.id.chkBairro)
    public CheckBox chkBairro;
    @BindView(R.id.lytBairro)
    public LinearLayout lytBairro;


    public BairroCheckViewHolder(View itemView, final ListaBairroCheckAdapter.bairroListener listener) {
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
