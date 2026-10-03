package com.example.rcksuporte05.rcksistemas.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rcksuporte05.rcksistemas.fragment.GraficoFaturamento1;

public class GraficoFaturamentoAdapter2 extends FragmentStateAdapter {

    public GraficoFaturamentoAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GraficoFaturamento1(0);
            case 1:
                return new GraficoFaturamento1(1);
            case 2:
                return new GraficoFaturamento1(2);
            default:
                return new GraficoFaturamento1(3);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
