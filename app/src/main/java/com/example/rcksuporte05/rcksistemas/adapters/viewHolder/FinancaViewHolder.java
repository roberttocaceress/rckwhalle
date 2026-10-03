package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinancaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.txvNomeOperacao)
    public TextView txvNomeOperacao;

    @BindView(R.id.txvValorOperacao)
    public TextView txvValorOperacao;

    @BindView(R.id.txvTotalDocs)
    public  TextView txvTotalDocs;

    @BindView(R.id.swtStatus)
    public Switch swtStatus;

    @BindView(R.id.itemView)
    public LinearLayout itemView;

    public FinancaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    @Override
    public void onClick(View v) {

    }
}
