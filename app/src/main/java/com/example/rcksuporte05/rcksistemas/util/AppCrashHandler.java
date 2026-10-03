package com.example.rcksuporte05.rcksistemas.util;

import android.content.Context;
import android.util.Log;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.LogSistemaDAO;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.model.LogSistema;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private final Context context;
    private final Thread.UncaughtExceptionHandler defaultHandler;

    public AppCrashHandler(Context context) {
        this.context = context;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            // 1. Captura o StackTrace completo
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();

            // 2. Prepara o Log de Crash
            LogSistema log = new LogSistema();
            log.setTipoLog("CRASH");
            log.setEvento("FORCE_CLOSE");
            log.setClasse("Thread: " + thread.getName());
            log.setMetodo("GlobalExceptionHandler");
            log.setErroDetalhe(stackTrace);

            // Tenta pegar contexto de pedido se houver
            try {
                WebPedido pedidoAtual = PedidoHelper.getWebPedido();
                if (pedidoAtual != null) {
                    log.setIdPedido(pedidoAtual.getId_web_pedido());
                    log.setIdPedidoApp(pedidoAtual.getId_pedido_app());
                    log.setIdWebPedidoServidor(pedidoAtual.getId_web_pedido_servidor());
                    log.setIdVendedor(pedidoAtual.getId_vendedor());
                }
            } catch (Exception ignored) {}

            // 3. Grava no SQLite de forma direta (sincrona)
            DBHelper db = new DBHelper(context);
            new LogSistemaDAO(db).add(log);

            Log.e("CRASH_HANDLER", "CRASH CAPTURADO E SALVO NO SQLITE");

        } catch (Exception e) {
            Log.e("CRASH_HANDLER", "FALHA AO SALVAR LOG DE CRASH: " + e.getMessage());
        }

        // 4. Repassa para o Android fechar o app normalmente
        if (defaultHandler != null) {
            defaultHandler.uncaughtException(thread, ex);
        }
    }

    public static void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(context));
    }
}
