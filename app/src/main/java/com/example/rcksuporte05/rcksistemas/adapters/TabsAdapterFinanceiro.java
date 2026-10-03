package com.example.rcksuporte05.rcksistemas.adapters;

import static com.example.rcksuporte05.rcksistemas.api.Api.context;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.fragment.FinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.fragment.FinanceiroVencer;
import com.example.rcksuporte05.rcksistemas.fragment.FinanceiroVencido;

public class TabsAdapterFinanceiro extends FragmentPagerAdapter {
    //private String[] titles = { "Titulos", "Resumo" };
    private String[] titles = { "Vencido", "A vencer", "Quitado" };
    int[] imageList={R.drawable.ic_pdf_pedido,R.drawable.ic_pdf_pedido,R.drawable.ic_pdf_pedido};
    public TabsAdapterFinanceiro(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new FinanceiroVencido();
                break;
            case 1:
                frag = new FinanceiroVencer();
                break;
            case 2:
                frag = new FinanceiroQuitado();
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
        return (titles[position]);
    }
    /*
    @Override
    public CharSequence getPageTitle(int position) {

        Drawable drawable= ContextCompat.getDrawable(context.getApplicationContext()
                ,imageList[position]);

        // set bound
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());

        // Initialize spannable image
        SpannableString spannableString=new SpannableString(""+ titles[position]);

        // Initialize image span
        ImageSpan imageSpan=new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);

        // Set span
        spannableString.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // return spannable string
        return spannableString;
        //return (titles[position]);
    }

     */
}
