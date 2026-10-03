package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProdutoImagemZoom;
import com.example.rcksuporte05.rcksistemas.model.ProdutoFoto;

import java.net.URL;
import java.util.List;
import java.io.*;

import static android.content.Context.ACTIVITY_SERVICE;

public class ImagemProdutoAdapter extends PagerAdapter {
    private List<ProdutoFoto> lista;
    private Context context;
    private boolean openImage = false;
    public ProdutoFoto produtoFoto;
    public int positionselected;
    public ImagemProdutoAdapter(List<ProdutoFoto> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public boolean isViewFromObject( View view,  Object object) {
        return view == (FrameLayout)object;
    }
    public void setImagemListada(int position) {
        positionselected = position;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_view_produto_image_new, container, false);
        container.addView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_produto_img_new);
        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory();
        if (!memoryInfo.lowMemory) {
            try {
                produtoFoto = lista.get(position);
                byte[] decodedString = Base64.decode(produtoFoto.getFbase64().getBytes(), 0);
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));//, 170, 180, false));
                openImage = true;
            } catch (OutOfMemoryError e) {
                System.gc();
                Toast.makeText(container.getContext(), "Não foi possivel visualizar a imagem!  ", Toast.LENGTH_SHORT).show();
                openImage = false;
            } catch (Exception e) {
                System.gc();
                openImage = false;
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (openImage)
                        //Intent intent = new Intent(v.getContext(), ActivityProdutoImagemZoom.class);
                        //intent.putExtra("id_foto", contents.get(position).getId_foto());
                        try {
                            v.getContext().startActivity(new Intent(v.getContext(), ActivityProdutoImagemZoom.class).putExtra("id_foto", lista.get(positionselected).getId_foto()));
                        } catch ( NullPointerException e) {

                        } catch ( Exception e) {

                        }
                    else
                        Toast.makeText(v.getContext(), "Não foi possivel aplicar o zoom na imagem!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            System.gc();
            Toast.makeText(context, "Pouca memoria disponivel para visualização da imagem!", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
    private ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
