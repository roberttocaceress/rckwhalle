package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.ConfiguracaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PreferenciaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoPendente;
import com.example.rcksuporte05.rcksistemas.adapters.ListaHistoricoFinanceiroAdapter;
import com.example.rcksuporte05.rcksistemas.model.Configuracao;
import com.example.rcksuporte05.rcksistemas.model.Financeiro;
import com.example.rcksuporte05.rcksistemas.model.Preferencia;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
import com.example.rcksuporte05.rcksistemas.util.Utilitaria;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinanceiroVencido extends Fragment implements ListaHistoricoFinanceiroAdapter.FinanceiroAdapterListener {
    @BindView(R.id.listaFinanceiro)
    RecyclerView recycleFinanceiro;

    @BindView(R.id.edtTotalTitulos)
    TextView edtTotalTitulos;
    @BindView(R.id.edtTotalJuros)
    TextView edtTotalJuros;
    @BindView(R.id.edtTotalPago)
    TextView edtTotalPago;
    @BindView(R.id.edtTotalSaldo)
    TextView edtTotalSaldo;

    @BindView(R.id.layout_progress)
    LinearLayout layout_progress;

    @BindView(R.id.progress_ajuda)
    ProgressBar progress_ajuda;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerLayout;

    @BindView(R.id.progress_title)
    TextView progress_title;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    private View view;
    Context context;
    private static ListaHistoricoFinanceiroAdapter listaFinanceiroAdapter;
    private static List<Financeiro> listaFinanceiro = new ArrayList<>();
    PackageInfo pInfo = null;
    private DBHelper db;
    private ConfiguracaoDAO configuracaoDAO;
    private PreferenciaDAO preferenciaDAO;
    private LinearLayout.LayoutParams params;

    private Configuracao configuracao;
    private boolean install, downSucess;
    String nameFile;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_financeiro_2025, container, false);
        ButterKnife.bind(this, view);
        context = this.getContext();
        shimmerLayout.stopShimmer();
        shimmerLayout.hideShimmer();
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycleFinanceiro.setLayoutManager(layoutManager);
        try {

                runnable = () -> {
                    if (isAdded() && getActivity() != null && getView() != null) {
                        if (ClienteHelper.getCliente() == null)
                            return;
                        showProgress();
                        pesquisaTitulo("vc", ClienteHelper.getCliente().getId_cadastro_servidor(), false);
                    }
                };
                handler.postDelayed(runnable, 2500);

        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (listaFinanceiro.size() > 0) {
                        showShimmer();
                        Handler handler = new Handler();
                        Runnable runnable = () -> {
                            pesquisaTitulo("vc", ClienteHelper.getCliente().getId_cadastro_servidor(), true);
                        };
                        handler.postDelayed(runnable, 2500);

                    }
                } catch (NullPointerException e) {
                } catch (Exception e) {
                }
            }
        });
        return (view);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Isso remove o Runnable da fila se o usuário fechar a tela antes do tempo
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
    private void pesquisaTitulo(String tipo, int idCliente, boolean geraPdf) {  //Tipo vc = vencido , vn = a vencer  , qt = quitado
        String versaoApp = pInfo.versionName.substring(0, 5);
        int dias_historico_financeiro = 0;
        db = new DBHelper(context);
        preferenciaDAO = new PreferenciaDAO(db);
        try {
            Preferencia preferencia = preferenciaDAO.getPreferencia("SELECT * FROM TBL_PREFERENCIA");
            dias_historico_financeiro = preferencia.getDias_hist_financeiro();
        } catch ( NullPointerException | NumberFormatException e ) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        configuracaoDAO = new ConfiguracaoDAO(db);
        final Configuracao configuracao = configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");
        if ( !usuarioIsLogado() ) {
            if ( geraPdf) {
                stopShimmer();
            } else {
                hideProgress();
            }
            return;
        }
        if (!configuracao.getId().isEmpty()) {
            Call<List<Financeiro>> call;
            if (geraPdf)
                call = Utilitaria.getRotasAPIPdf(1, tipo, idCliente, dias_historico_financeiro, configuracao, versaoApp, getContext());
            else
                call = Utilitaria.getRotasAPI(1, tipo, idCliente, dias_historico_financeiro, configuracao, versaoApp, getContext());
            call.enqueue(new Callback<List<Financeiro>>() {
                @Override
                public void onResponse(Call<List<Financeiro>> call, Response<List<Financeiro>> response) {
                    if (response.code() == 200) {
                            listaFinanceiro = response.body();
                            if ( geraPdf ) {
                                stopShimmer();
                                if ( listaFinanceiro.size() > 0)
                                    downloadOpenPdf(listaFinanceiro.get(0).getNome_pdf());
                            }else
                                setRecyclerView(listaFinanceiro);
                    } else {
                        if ( geraPdf) {
                            stopShimmer();
                        } else {
                            hideProgress();
                        }
                        Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Financeiro>> call, Throwable t) {
                    if ( geraPdf) {
                        stopShimmer();
                    } else {
                        hideProgress();
                    }
                    Toast.makeText(context, "Não foi possivel sincronizar com o servidor! Por favor verifique sua conexão e tente novamente!!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            if ( geraPdf) {
                stopShimmer();
            } else {
                hideProgress();
            }
            Toast.makeText(context, "Parametros para conexão não cadastrados, por gentileza clique no botão ajuda antes de continuar", Toast.LENGTH_LONG).show();
        }

    }

    private void setRecyclerView(List<Financeiro> listaFinanceiro) {
        listaFinanceiroAdapter = new ListaHistoricoFinanceiroAdapter(listaFinanceiro);//, listenerFinanceiro);
        recycleFinanceiro.setAdapter(listaFinanceiroAdapter);
        listaFinanceiroAdapter.notifyDataSetChanged();
        float totalTitulos = 0.0f;
        float totalJuros = 0.0f;
        float totalPago = 0.0f;
        float totalSaldo = 0.0f;
        try {
            if (listaFinanceiro.size() > 0) {
                for (int i = 0; i < listaFinanceiro.size(); i++) {
                    totalTitulos += Float.parseFloat(listaFinanceiro.get(i).getValor_documento());
                    try {
                        totalJuros += Float.parseFloat(listaFinanceiro.get(i).getValor_juros());
                    } catch (Exception e) {

                    }
                    try {
                        totalPago += Float.parseFloat(listaFinanceiro.get(i).getValor_pago());
                    } catch (Exception e) {

                    }
                    try {
                        totalSaldo += Float.parseFloat(listaFinanceiro.get(i).getValor_saldo());
                    } catch (Exception e) {

                    }
                }
                edtTotalTitulos.setText(MascaraUtil.mascaraVirgula(totalTitulos));
                edtTotalJuros.setText(MascaraUtil.mascaraVirgula(totalJuros));
                edtTotalPago.setText(MascaraUtil.mascaraVirgula(totalPago));
                edtTotalSaldo.setText(MascaraUtil.mascaraVirgula(totalSaldo));
            } else
                Toast.makeText(context, "Não foram encontrados registros no histórico financeiro!", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            edtTotalTitulos.setText(MascaraUtil.mascaraVirgula(totalTitulos));
            edtTotalJuros.setText(MascaraUtil.mascaraVirgula(totalJuros));
            edtTotalPago.setText(MascaraUtil.mascaraVirgula(totalPago));
            edtTotalSaldo.setText(MascaraUtil.mascaraVirgula(totalSaldo));
        } catch (Exception e) {
            edtTotalTitulos.setText(MascaraUtil.mascaraVirgula(totalTitulos));
            edtTotalJuros.setText(MascaraUtil.mascaraVirgula(totalJuros));
            edtTotalPago.setText(MascaraUtil.mascaraVirgula(totalPago));
            edtTotalSaldo.setText(MascaraUtil.mascaraVirgula(totalSaldo));
        }
        hideProgress();
    }

    private void hideProgress() {
        try {
            progress_ajuda.setVisibility(View.INVISIBLE);
            progress_title.setVisibility(View.INVISIBLE);
            params.weight = 0.0f;
            params.height = 0;
            layout_progress.setLayoutParams(params);
        } catch ( NullPointerException e) {
        } catch ( Exception e){
        }
    }

    private void showProgress() {
        params = (LinearLayout.LayoutParams) layout_progress.getLayoutParams();
        params.weight = 0.2f;
        params.height = 40;
        layout_progress.setLayoutParams(params);
        progress_ajuda.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progress_ajuda.setVisibility(View.VISIBLE);
        progress_title.setVisibility(View.VISIBLE);
    }

    private void showShimmer() {
        shimmerLayout.startShimmer();
        shimmerLayout.showShimmer(true);
    }

    private void stopShimmer() {
        shimmerLayout.stopShimmer();
        shimmerLayout.hideShimmer();
    }

    @Override
    public void onClickListener(int position) {
    }

    @Override
    public void onLongClickListener(int position) {
    }

    private boolean usuarioIsLogado() {
        UsuarioBO usuarioBO = new UsuarioBO();
        UsuarioHelper.setUsuario(usuarioBO.getUsuarioLogin(context));
        try {
            if (UsuarioHelper.getUsuario() == null) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private void downloadOpenPdf(String nomePdf) {
        configuracao = getConfiguracao();
        String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File folder = new File(extStorageDirectory);
        folder.mkdirs();
        nameFile =  nomePdf + ".pdf";
        File file = new File(folder, nameFile);
        try {
            if ( file.exists())
                file.delete();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
        DownloadPdfTask downloadpdfTask = new DownloadPdfTask("http://" + configuracao.getUrl_ip_1() + ":"
                + configuracao.getPorta_1() + "/rckwhalleAPI"
                + pInfo.versionName.substring(0, 5) + "/pdf/" + nameFile, file, true);
        downloadpdfTask.execute();
    }

    public class DownloadPdfTask extends AsyncTask<String, Void, Void> {
        String fileURL;
        File directory;
        public DownloadPdfTask(String fileURL,File directory, boolean install) {
            this.fileURL = fileURL;
            this.directory = directory;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                FileOutputStream f = new FileOutputStream(directory);
                URL u = new URL(fileURL);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.connect();
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
                directory.setReadable(true,false);
                downSucess = true;
            } catch (Exception e) {
                e.printStackTrace();
                downSucess = false;
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Void stringReturn) {
            super.onPostExecute(stringReturn);
            File file = new   File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +"/" + nameFile);
            context.getApplicationContext().getFilesDir().getAbsolutePath();
            ContextWrapper contextWrapper = new ContextWrapper(context);

            if (downSucess) {
                if (file.exists()) {
                    int sdk_v = Build.VERSION.SDK_INT;
                    try {
                        startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                .setType("pdf/*")
                                .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file))
                                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION), "Compartilhar historico financeiro do cliente " + listaFinanceiro.get(0).getId_favorecido()));

                    } catch (NullPointerException e) {
                        Toast.makeText(context, "Aplicativo não pode criar/armazenar/compartilhar arquivos!\nFavor verifique as permissões concedidas!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(context, "O arquivo não foi localizado!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Falha ao efetuar o download do arquivo!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
            }
        }
    }


    private Configuracao getConfiguracao() {
        return configuracaoDAO.getConfiguracao("SELECT * FROM TBL_CONFIGURACAO");

    }

}
