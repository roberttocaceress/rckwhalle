package com.example.rcksuporte05.rcksistemas.util;

import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.MesAno;
import com.example.rcksuporte05.rcksistemas.model.MesAnoGrupoMarca;
import com.example.rcksuporte05.rcksistemas.model.MetaSemanal;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreConexao {

    public interface OnMetaRetorno {
        void onSucesso(MesAno mesAno);
        void onFalha(Exception e);
    }

    public interface OnMetaSemanalRetorno {
        void onSucesso(MetaSemanal metaSemanal);
        void onFalha(Exception e);
    }

    public interface OnListaMetasRetorno {
        void onSucesso(List<MesAnoGrupoMarca> lista);
        void onFalha(Exception e);
    }

    public interface OnListaEvolucaoRetorno {
        void onSucesso(List<EvolucaoFaturamento> lista);
        void onFalha(Exception e);
    }

    public interface OnSucessoFalha {
        void onSucesso();
        void onFalha(Exception e);
    }

    public void getMetaMesAno(String cnpj, String ano, String id, OnMetaRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        try {
            fdb.collection(cnpj)
                    .document("TBL_WEB_USARIO_META")
                    .collection(id)
                    .document("META-MES-ANO-" + ano)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists())
                            callback.onSucesso(documentSnapshot.toObject(MesAno.class));
                        else
                            callback.onSucesso(null);
                    })
                    .addOnFailureListener(callback::onFalha);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getMetaEmpresa(String cnpj, String ano, String idEmpresa, OnMetaRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        fdb.collection(cnpj)
                .document("TBL_WEB_EMPRESA_META")
                .collection(idEmpresa)
                .document("META-MES-ANO-" + ano)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        callback.onSucesso(documentSnapshot.toObject(MesAno.class));
                    else
                        callback.onSucesso(null);
                })
                .addOnFailureListener(callback::onFalha);
    }

    public void getMetaMesAnoSubOpcao(String cnpj, String ano, String idVendedor, String idSubOpcao, String tipoMeta, OnMetaRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        String colecao = tipoMeta.equals("G") ? "META-MES-ANO-" + ano + "-GRUPO" : "META-MES-ANO-" + ano + "-MARCA";

        fdb.collection(cnpj)
                .document("TBL_WEB_USARIO_META")
                .collection(idVendedor)
                .document(colecao)
                .collection("ITENS")
                .document(idSubOpcao)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        MesAnoGrupoMarca metaItem = documentSnapshot.toObject(MesAnoGrupoMarca.class);
                        callback.onSucesso(metaItem != null ? metaItem.getMesAno() : null);
                    } else {
                        callback.onSucesso(null);
                    }
                })
                .addOnFailureListener(callback::onFalha);
    }

    public void getTodasMetasSubOpcao(String cnpj, String ano, String idVendedor, String tipoMeta, OnListaMetasRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        String colecao = tipoMeta.equals("G") ? "META-MES-ANO-" + ano + "-GRUPO" : "META-MES-ANO-" + ano + "-MARCA";

        fdb.collection(cnpj)
                .document("TBL_WEB_USARIO_META")
                .collection(idVendedor)
                .document(colecao)
                .collection("ITENS")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<MesAnoGrupoMarca> lista = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        lista.add(doc.toObject(MesAnoGrupoMarca.class));
                    }
                    callback.onSucesso(lista);
                })
                .addOnFailureListener(callback::onFalha);
    }

    public void getMetaSemanal(String cnpj, String ano, String idVendedor, String idMes, OnMetaSemanalRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        String colecao = "META-MES-ANO-" + ano + "-SEMANA";

        fdb.collection(cnpj)
                .document("TBL_WEB_USARIO_META")
                .collection(idVendedor)
                .document(colecao)
                .collection(idMes)
                .document(idMes)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        callback.onSucesso(documentSnapshot.toObject(MetaSemanal.class));
                    else
                        callback.onSucesso(null);
                })
                .addOnFailureListener(callback::onFalha);
    }

    public void salvarMeta(String cnpj, String ano, String idVendedor, String idSubOpcao, String tipoMeta, Object metaObj, OnSucessoFalha callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        if (tipoMeta.equals("M")) {
            fdb.collection(cnpj)
                    .document("TBL_WEB_USARIO_META")
                    .collection(idVendedor)
                    .document("META-MES-ANO-" + ano)
                    .set(metaObj)
                    .addOnSuccessListener(aVoid -> callback.onSucesso())
                    .addOnFailureListener(callback::onFalha);
        } else if (tipoMeta.equals("E")) {
            fdb.collection(cnpj)
                    .document("TBL_WEB_EMPRESA_META")
                    .collection(idSubOpcao)
                    .document("META-MES-ANO-" + ano)
                    .set(metaObj)
                    .addOnSuccessListener(aVoid -> callback.onSucesso())
                    .addOnFailureListener(callback::onFalha);
        } else if (tipoMeta.equals("S")) {
            fdb.collection(cnpj)
                    .document("TBL_WEB_USARIO_META")
                    .collection(idVendedor)
                    .document("META-MES-ANO-" + ano + "-SEMANA")
                    .collection(idSubOpcao)
                    .document(idSubOpcao)
                    .set(metaObj)
                    .addOnSuccessListener(aVoid -> callback.onSucesso())
                    .addOnFailureListener(callback::onFalha);
        } else {
            String colecao = tipoMeta.equals("G") ? "META-MES-ANO-" + ano + "-GRUPO" : "META-MES-ANO-" + ano + "-MARCA";

            fdb.collection(cnpj)
                    .document("TBL_WEB_USARIO_META")
                    .collection(idVendedor)
                    .document(colecao)
                    .collection("ITENS")
                    .document(idSubOpcao)
                    .set(metaObj)
                    .addOnSuccessListener(aVoid -> callback.onSucesso())
                    .addOnFailureListener(callback::onFalha);
        }
    }

    public void salvarRealizado(String cnpj, String ano, String idVendedor, String idSubOpcao, String tipoMeta, Object realizadoObj, OnSucessoFalha callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        if (tipoMeta.equals("M")) {
            fdb.collection(cnpj)
                    .document("TBL_WEB_USARIO_REALIZADO")
                    .collection(idVendedor)
                    .document("REALIZADO-MES-ANO-" + ano)
                    .set(realizadoObj)
                    .addOnSuccessListener(aVoid -> { if (callback != null) callback.onSucesso(); })
                    .addOnFailureListener(e -> { if (callback != null) callback.onFalha(e); });
        } else {
            String prefixo = tipoMeta.equals("G") ? "REALIZADO-MES-ANO-" + ano + "-GRUPO" : "REALIZADO-MES-ANO-" + ano + "-MARCA";
            fdb.collection(cnpj)
                    .document("TBL_WEB_USARIO_REALIZADO")
                    .collection(idVendedor)
                    .document(prefixo)
                    .collection("ITENS")
                    .document(idSubOpcao)
                    .set(realizadoObj)
                    .addOnSuccessListener(aVoid -> { if (callback != null) callback.onSucesso(); })
                    .addOnFailureListener(e -> { if (callback != null) callback.onFalha(e); });
        }
    }

    public void getRealizadoMesAno(String cnpj, String ano, String idVendedor, OnMetaRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        fdb.collection(cnpj)
                .document("TBL_WEB_USARIO_REALIZADO")
                .collection(idVendedor)
                .document("REALIZADO-MES-ANO-" + ano)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        callback.onSucesso(documentSnapshot.toObject(MesAno.class));
                    else
                        callback.onSucesso(null);
                })
                .addOnFailureListener(callback::onFalha);
    }

    public void getRealizadoMesAnoSubOpcao(String cnpj, String ano, String idVendedor, String idSubOpcao, String tipoMeta, OnMetaRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        String colecao = tipoMeta.equals("G") ? "REALIZADO-MES-ANO-" + ano + "-GRUPO" : "REALIZADO-MES-ANO-" + ano + "-MARCA";

        fdb.collection(cnpj)
                .document("TBL_WEB_USARIO_REALIZADO")
                .collection(idVendedor)
                .document(colecao)
                .collection("ITENS")
                .document(idSubOpcao)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        MesAnoGrupoMarca metaItem = documentSnapshot.toObject(MesAnoGrupoMarca.class);
                        callback.onSucesso(metaItem != null ? metaItem.getMesAno() : null);
                    } else {
                        callback.onSucesso(null);
                    }
                })
                .addOnFailureListener(callback::onFalha);
    }

    public void salvarEvolucaoVendas(String cnpj, String idUsuario, List<EvolucaoFaturamento> lista, OnSucessoFalha callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("evolucao", lista);

        fdb.collection(cnpj)
                .document("TBL_WEB_USUARIO_EVOLUCAO")
                .collection(idUsuario)
                .document("DADOS-EVOLUCAO")
                .set(data)
                .addOnSuccessListener(aVoid -> { if (callback != null) callback.onSucesso(); })
                .addOnFailureListener(e -> { if (callback != null) callback.onFalha(e); });
    }

    public void getEvolucaoVendas(String cnpj, String idUsuario, OnListaEvolucaoRetorno callback) {
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        fdb.collection(cnpj)
                .document("TBL_WEB_USUARIO_EVOLUCAO")
                .collection(idUsuario)
                .document("DADOS-EVOLUCAO")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Map<String, Object>> mapList = (List<Map<String, Object>>) documentSnapshot.get("evolucao");
                        List<EvolucaoFaturamento> evolucaoList = new ArrayList<>();
                        if (mapList != null) {
                            for (Map<String, Object> map : mapList) {
                                EvolucaoFaturamento ev = new EvolucaoFaturamento();
                                ev.setMes(String.valueOf(map.get("mes")));
                                ev.setTotal_liquido(String.valueOf(map.get("total_liquido")));
                                evolucaoList.add(ev);
                            }
                        }
                        callback.onSucesso(evolucaoList);
                    } else {
                        callback.onSucesso(null);
                    }
                })
                .addOnFailureListener(callback::onFalha);
    }
}
