package com.example.rcksuporte05.rcksistemas.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.ClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityAnexoCadastro;
import com.example.rcksuporte05.rcksistemas.activity.ActivityFoto;
import com.example.rcksuporte05.rcksistemas.adapters.CadastroAnexoAdapter;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroCliente8 extends Fragment implements CadastroAnexoAdapter.Listener, View.OnClickListener  {
   //Para uso na camera
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @BindView(R.id.recyclerAnexo)
    RecyclerView recyclerView;

    @BindView(R.id.edtTotalAnexos)
    TextView edtTotalAnexos;

    @BindView(R.id.btnAddAnexos)
    FloatingActionButton btnAddAnexos;

    @BindView(R.id.btnAddAnexCam)
    FloatingActionButton btnAddAnexCam;

    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    private CadastroAnexoAdapter cadastroAnexoAdapter;
    private ActionMode actionMode;

    @OnClick(R.id.btnAddAnexCam)

    public void addAnexoCam() {
        Intent intent = new Intent(getActivity(), ActivityFoto.class);
        startActivity(intent);
    }


    @OnClick(R.id.btnAddAnexos)
    public void addAnexo() {
        Intent intent = new Intent(getActivity(), ActivityAnexoCadastro.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente8_2026, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(this);
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            btnAddAnexos.setVisibility(View.VISIBLE);
            btnAddAnexCam.setVisibility(View.GONE);
        }
        ClienteHelper.setCadastroCliente8(this);
        return view;
    }

    @Override
    public void onResume() {
        setRecyclerView();
        super.onResume();
    }

    private void setRecyclerView() {
        cadastroAnexoAdapter = new CadastroAnexoAdapter(ClienteHelper.getListaCadastroAnexo(), this);
        recyclerView.setAdapter(cadastroAnexoAdapter);
        cadastroAnexoAdapter.notifyDataSetChanged();
        if (ClienteHelper.getListaCadastroAnexo() != null && ClienteHelper.getListaCadastroAnexo().size() > 0)
            edtTotalAnexos.setText("Anexos listados: " + ClienteHelper.getListaCadastroAnexo().size());
        else
            edtTotalAnexos.setText("Nenhum anexo registrado");
    }

    @Override
    public void onClickListener(int position) {
        if (cadastroAnexoAdapter.getSelectedItensCount() > 0)
            enableActionMode(position);
        else {
            Intent intent = new Intent(getActivity(), ActivityAnexoCadastro.class);
            intent.putExtra("Alteracao", position);
            if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1)
                intent.putExtra("vizualizacao", 1);
            startActivity(intent);
        }
    }

    @Override
    public void onLongClickListener(int position) {
        enableActionMode(position);
    }

    public void enableActionMode(final int position) {
        if (actionMode == null) {
            actionMode = getActivity().startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.cadastro_anexo_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_delete:
                            if (cadastroAnexoAdapter.getSelectedItensCount() > 1)
                                showMsgSimNao("Deseja mesmo excluir o(s) " + cadastroAnexoAdapter.getSelectedItensCount() + " anexo(s) selecionado(s)?");
                            else
                                showMsgSimNao("Deseja mesmo excluir o anexo selecionado?");
                            break;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    finishActionMode();
                }
            });
        }
        toggleSelection(position);
    }

    public void toggleSelection(int position) {
        cadastroAnexoAdapter.toggleSelection(position);
        if (cadastroAnexoAdapter.getSelectedItensCount() == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(cadastroAnexoAdapter.getSelectedItensCount()));
            actionMode.invalidate();
        }
    }

    public void finishActionMode() {
        if (actionMode != null) {
            actionMode.finish();
            cadastroAnexoAdapter.clearSelection();
            actionMode = null;
        }
    }

    public void showMsgSimNao( String mensagem) {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sim_nao_mensagem, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        TextView textView = (TextView) dialogView.findViewById(R.id.txvMensagemOk);
        textView.setText(mensagem);
        Button btnNao = (Button) dialogView.findViewById(R.id.btnMensagemNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        Button btnSim = (Button) dialogView.findViewById(R.id.btnMensagemSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                for (CadastroAnexo cadastroAnexo : cadastroAnexoAdapter.getItensSelecionados()) {
                    if (ClienteHelper.getListaCadastroAnexoExcluidos() != null) {
                        ClienteHelper.getListaCadastroAnexoExcluidos().add(cadastroAnexo);
                    } else {
                        List<CadastroAnexo> listaCadastroAnexoExcluidos = new ArrayList<>();
                        listaCadastroAnexoExcluidos.add(cadastroAnexo);
                        ClienteHelper.setListaCadastroAnexoExcluidos(listaCadastroAnexoExcluidos);
                    }
                    ClienteHelper.getListaCadastroAnexo().remove(cadastroAnexo);
                }
                finishActionMode();
                setRecyclerView();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if ( view == btnContinuar) {
            DBHelper db = new DBHelper(getActivity());
            CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
            if (ClienteHelper.getListaCadastroAnexo() != null && ClienteHelper.getListaCadastroAnexo().size() > 0)
                for (CadastroAnexo cadastroAnexo : ClienteHelper.getListaCadastroAnexo()) {
                    cadastroAnexo.setExcluido("N");
                    cadastroAnexo.setIdEntidade(1);
                    cadastroAnexo.setIdCadastro(ClienteHelper.getCliente().getId_cadastro());
                    cadastroAnexo.setIdCadastroServidor(ClienteHelper.getCliente().getId_cadastro_servidor());
                    cadastroAnexoDAO.addUpdate(cadastroAnexo);
                }
            if (ClienteHelper.getListaCadastroAnexoExcluidos() != null && ClienteHelper.getListaCadastroAnexoExcluidos().size() > 0)
                for (CadastroAnexo cadastroAnexoExcluido : ClienteHelper.getListaCadastroAnexoExcluidos()) {
                    cadastroAnexoExcluido.setExcluido("S");
                    cadastroAnexoDAO.addUpdate(cadastroAnexoExcluido);
                }
            if (ClienteHelper.getCliente().getFinalizado().equals("S"))
                ClienteHelper.getCliente().setAlterado("S");
            ClienteDAO clienteDAO = new ClienteDAO(db);
            clienteDAO.update(ClienteHelper.getCliente());
            ClienteHelper.moveTela(4);
        }
    }
}
