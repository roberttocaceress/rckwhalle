package com.example.rcksuporte05.rcksistemas.adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rcksuporte05.rcksistemas.fragment.CampanhaClientes;
import com.example.rcksuporte05.rcksistemas.fragment.CampanhaProdutos;

public class TabsAdapterCampanha extends FragmentPagerAdapter {
    private String[] titles = { "Regulamento"};
    public TabsAdapterCampanha(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new CampanhaProdutos();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
