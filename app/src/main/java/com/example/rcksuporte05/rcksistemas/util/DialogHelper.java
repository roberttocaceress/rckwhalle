package com.example.rcksuporte05.rcksistemas.util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.rcksuporte05.rcksistemas.model.Generico;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.example.rcksuporte05.rcksistemas.R;

import java.util.List;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.DAO.UsuarioDAO;

public class DialogHelper {
    public interface OnConfirmListener {
        void onConfirm(View dialogView, AlertDialog dialog);
    }
    public static void showPermissionDialog(Context context, OnConfirmListener listener) {
        // SEMPRE use o contexto da Activity para inflar o layout do diálogo
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_alert_permissao, null);
        View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado, null);
        TextInputEditText edtCodigoAcesso = dialogView.findViewById(R.id.edtPwd); // Buscamos o ID aqui

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCustomTitle(headerView)
                //.setIcon(R.mipmap.ic_baseline_av_orion_color_primary_branco_new)
                .setMessage("Informe o código de acesso para a opção selecionada!")
                .setView(dialogView)
                .setPositiveButton("Confirmar", null) // Definimos null aqui para customizar o clique abaixo
                .setNegativeButton("Cancelar", (d, w) -> d.dismiss())
                .create();
        dialog.setOnShowListener(dialogInterface -> {
            edtCodigoAcesso.requestFocus();
            // 2. Força a abertura do teclado
            edtCodigoAcesso.postDelayed(() -> {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    // Usamos showSoftInput apontando para o campo específico, em vez de toggleSoftInput
                    imm.showSoftInput(edtCodigoAcesso, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 300);

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                if (listener != null) {
                    listener.onConfirm(dialogView, dialog);
                }
            });
        });
        dialog.show();

    }

//
    public interface OnActionSelectedListener {
       void onPositive(); // Ex: Visualizar
       void onNegative(); // Ex: Enviar/Responder
       void onNeutral();  // Ex: Marcar como lida
    }

    public static void showSnackInfo(View view, String mensagem) {
        Snackbar.make(view, mensagem,
                        Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.colorPrimary))
                .show();
    }
    public static void showSnackInfo(View view, String mensagem, int iconResId) {
        // 1. Criar o Snackbar
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG);

        // 2. Acessar a View interna do Snackbar
        View snackbarView = snackbar.getView();

        // 3. Customizar cor de fundo (Opcional)
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(view.getContext(), R.color.colorPrimary)));
        snackbar.setDuration(6000);
        // 4. Acessar o TextView interno do Snackbar para colocar o ícone
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);

        if (textView != null) {
            // Carregar o ícone
            Drawable icon = ContextCompat.getDrawable(view.getContext(), iconResId);

            if (icon != null) {
                // Envolver e pintar o ícone de branco para dar contraste
                icon = DrawableCompat.wrap(icon).mutate();
                //DrawableCompat.setTint(icon, Color.WHITE );

                // Definir o tamanho do ícone (ajuste conforme necessário)
                icon.setBounds(0, 0, 60, 60);
                textView.setMaxLines(Integer.MAX_VALUE); // Remove o limite de linhas
                textView.setEllipsize(null);             // Remove as reticências (...)
                textView.setSingleLine(false);
                // Coloca o ícone à esquerda do texto (start)
                textView.setCompoundDrawables(icon, null, null, null);

                // Espaço entre o ícone e o texto
                textView.setCompoundDrawablePadding(24);

                // Centralizar ícone verticalmente com o texto
                textView.setGravity(Gravity.CENTER_VERTICAL);
            }
        }

        // 5. Ajuste para Android 15 (Edge-to-Edge)
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);

        snackbar.show();
    }

    public static void showSnack(View view, String mensagem, int iconResId) {
        // 1. Criar o Snackbar
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG);

        // 2. Acessar a View do Snackbar
        View snackbarView = snackbar.getView();
        Context context = view.getContext();

        // --- ESTILIZAÇÃO DO CONTAINER (O "BOX") ---
        // Criamos um background arredondado e flutuante
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(28f); // Bem arredondado (estilo pílula)
        background.setColor(ContextCompat.getColor(context, R.color.colorButton)); // Sua cor principal

        snackbarView.setBackground(background);
        // --- AJUSTE DE MARGENS (PARA ELE FLUTUAR) ---
        // Isso evita que ele fique colado nas bordas da tela
        if (snackbarView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snackbarView.getLayoutParams();
            int marginSide = (int) (16 * context.getResources().getDisplayMetrics().density);
            int marginBottom = (int) (24 * context.getResources().getDisplayMetrics().density); // Espaço extra para Android 15
            params.setMargins(marginSide, 0, marginSide, marginBottom);
            snackbarView.setLayoutParams(params);
        }
        // --- ESTILIZAÇÃO DO CONTEÚDO (TEXTO E ÍCONE) ---
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        if (textView != null) {
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL)); // Fonte mais legível
            // Configuração do Ícone
            Drawable icon = ContextCompat.getDrawable(context, iconResId);
            if (icon != null) {
                icon = DrawableCompat.wrap(icon).mutate();
                DrawableCompat.setTint(icon, Color.WHITE);
                // Definir tamanho do ícone (20dp)
                int iconSize = (int) (20 * context.getResources().getDisplayMetrics().density);
                icon.setBounds(0, 0, iconSize, iconSize);
                textView.setCompoundDrawables(icon, null, null, null);
                textView.setCompoundDrawablePadding((int) (12 * context.getResources().getDisplayMetrics().density));
                textView.setGravity(Gravity.CENTER_VERTICAL);
            }
        }
        // 3. Animação moderna (Slide)
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

    public static void showActionDialog(
            Context context,
            String mensagem,
            String textPositivo, Integer iconPositivo,
            String textNegativo, Integer iconNegativo,
            String textNeutro, Integer iconNeutro,
            OnActionSelectedListener listener) {
        showActionDialog(context, mensagem, textPositivo, iconPositivo, textNegativo, iconNegativo, textNeutro, iconNeutro, listener, null);
    }

    public static void showActionDialog(
            Context context,
            String mensagem,
            String textPositivo, Integer iconPositivo,
            String textNegativo, Integer iconNegativo,
            String textNeutro, Integer iconNeutro,
            OnActionSelectedListener listener,
            View bodyView) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        @SuppressLint("InflateParams") View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado, null);

        // Define o ícone no cabeçalho se algum ícone for passado
        if (headerView != null) {
            ImageView imgIcon = headerView.findViewById(R.id.dialog_icon);
            if (imgIcon != null) {
                int resId = 0;
                if (iconNegativo != null && iconNegativo != 0) {
                    resId = iconNegativo;
                } else if (iconPositivo != null && iconPositivo != 0) {
                    resId = iconPositivo;
                }

                if (resId != 0) {
                    imgIcon.setImageResource(resId);
                    imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
        }
        
        builder.setCustomTitle(headerView);
        
        if (bodyView != null) {
            builder.setView(bodyView);
        }

        builder.setMessage(mensagem);
        if (textPositivo != null)
            builder.setPositiveButton(textPositivo, (dialog, which) -> {
                if (listener != null) listener.onPositive();
            });

        if (textNegativo != null)
            builder.setNegativeButton(textNegativo, (dialog, which) -> {
                if (listener != null) listener.onNegative();
            });

        if (textNeutro != null)
            builder.setNeutralButton(textNeutro, (dialog, which) -> {
                if (listener != null) listener.onNeutral();
            });

        AlertDialog dialog = builder.create();
        dialog.show();

        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int maxDialogWidth = (int) (380 * context.getResources().getDisplayMetrics().density); // Máximo de 380dp

        // Se a tela for maior que 380dp (caso dos tablets), fixamos em 380dp.
        // Se for menor (smartphones), usamos 90% da largura da tela.
        int finalWidth = Math.min(maxDialogWidth, (int) (widthPixels * 0.9));

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(finalWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }



        //Ajustes para ficarem sempre na vertical
        Button btnPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button btnNeu = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        if (btnPos != null && btnPos.getParent() instanceof LinearLayout) {
            LinearLayout parent = (LinearLayout) btnPos.getParent();
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setGravity(Gravity.CENTER);
            parent.setPadding(0, 0, 0, 16); // Espaço extra no rodapé

            int corBotao = context.getColor(R.color.colorPrimary);

            // Gerencia botão POSITIVO
            if (textPositivo != null && btnPos != null) {
                btnPos.setVisibility(View.VISIBLE);
                styleVerticalButton(context, btnPos, corBotao, iconPositivo);
            } else if (btnPos != null) {
                btnPos.setVisibility(View.GONE);
            }

            // Gerencia botão NEGATIVO
            if (textNegativo != null && btnNeg != null) {
                btnNeg.setVisibility(View.VISIBLE);
                styleVerticalButton(context, btnNeg, corBotao, iconNegativo);
            } else if (btnNeg != null) {
                btnNeg.setVisibility(View.GONE);
            }

            // Gerencia botão NEUTRO
            if (textNeutro != null && btnNeu != null) {
                btnNeu.setVisibility(View.VISIBLE);
                styleVerticalButton(context, btnNeu, corBotao, iconNeutro);
            } else if (btnNeu != null) {
                btnNeu.setVisibility(View.GONE);
            }
        }
    }

    public static void showActionDialogUltPreco(
            Context context,
            String mensagem,
            String textPositivo, Integer iconPositivo,
            String textNegativo, Integer iconNegativo,
            String textNeutro, Integer iconNeutro,
            Generico generico,
            OnActionSelectedListener listener) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        @SuppressLint("InflateParams") View headerView =  LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.dialog_alert_ultimo_preco_2026, null);

        EditText edtDataCompra = headerView.findViewById(R.id.edtDataCompra);
        EditText edtQtdeCompra = headerView.findViewById(R.id.edtQtdeCompra);
        EditText edtUltimoPreco = headerView.findViewById(R.id.edtUltimoPreco);

        edtDataCompra.setText(MascaraUtil.formataData(generico.getDatae(), "/", 8));
        edtQtdeCompra.setText(generico.getQtde_produto());

        // Cálculo do desconto (Sua lógica)
        if (generico.getPerc_desconto() != null && Float.parseFloat(generico.getPerc_desconto()) > 0.00f) {
            float valorOriginal = Float.parseFloat(generico.getValor());
            float percDesconto = Float.parseFloat(generico.getPerc_desconto());
            generico.setValor(String.valueOf(valorOriginal - (percDesconto / 100 * valorOriginal)));
        }
        edtUltimoPreco.setText(MascaraUtil.mascaraRealSimbolo(generico.getValor()));


        builder.setCustomTitle(headerView);
        if (textPositivo != null)
            builder.setPositiveButton(textPositivo, (dialog, which) -> {
                if (listener != null) listener.onPositive();
            });

        if (textNegativo != null)
            builder.setNegativeButton(textNegativo, (dialog, which) -> {
                if (listener != null) listener.onNegative();
            });

        if (textNeutro != null)
            builder.setNeutralButton(textNeutro, (dialog, which) -> {
                if (listener != null) listener.onNeutral();
            });

        AlertDialog dialog = builder.create();
        dialog.show();

        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int maxDialogWidth = (int) (380 * context.getResources().getDisplayMetrics().density); // Máximo de 380dp

        // Se a tela for maior que 380dp (caso dos tablets), fixamos em 380dp.
        // Se for menor (smartphones), usamos 90% da largura da tela.
        int finalWidth = Math.min(maxDialogWidth, (int) (widthPixels * 0.9));

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(finalWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }



        //Ajustes para ficarem sempre na vertical
        Button btnPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button btnNeu = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        if (btnPos != null && btnPos.getParent() instanceof LinearLayout) {
            LinearLayout parent = (LinearLayout) btnPos.getParent();
            
            // Força o container de botões a ocupar toda a largura disponível para permitir a centralização
            ViewGroup.LayoutParams parentLp = parent.getLayoutParams();
            parentLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            parent.setLayoutParams(parentLp);

            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setGravity(Gravity.CENTER);
            parent.setPadding(0, 0, 0, 16); // Espaço extra no rodapé

            int corBotao = context.getColor(R.color.colorPrimary);

            // Gerencia botão POSITIVO
            if (textPositivo != null && btnPos != null) {
                btnPos.setVisibility(View.VISIBLE);
                styleVerticalButton(context, btnPos, corBotao, iconPositivo);
            } else if (btnPos != null) {
                btnPos.setVisibility(View.GONE);
            }

            // Gerencia botão NEGATIVO
            if (textNegativo != null && btnNeg != null) {
                btnNeg.setVisibility(View.VISIBLE);
                styleVerticalButton(context, btnNeg, corBotao, iconNegativo);
            } else if (btnNeg != null) {
                btnNeg.setVisibility(View.GONE);
            }

            // Gerencia botão NEUTRO
            if (textNeutro != null && btnNeu != null) {
                btnNeu.setVisibility(View.VISIBLE);
                styleVerticalButton(context, btnNeu, corBotao, iconNeutro);
            } else if (btnNeu != null) {
                btnNeu.setVisibility(View.GONE);
            }
        }
    }


    public static void showVendasResumoDialog(Context context, View bodyView) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        @SuppressLint("InflateParams") View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado, null);

        // Define o ícone no cabeçalho
        if (headerView != null) {
            ImageView imgIcon = headerView.findViewById(R.id.dialog_icon);
            if (imgIcon != null) {
                imgIcon.setImageResource(R.drawable.ic_baseline_check_circle_outline_24_color_primary);
                imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }

        builder.setCustomTitle(headerView);
        builder.setView(bodyView);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();

        // Configura o clique no botão que está dentro do bodyView (o XML customizado)
        Button btnEntendido = bodyView.findViewById(R.id.btnEntendidoResumo);
        if (btnEntendido != null) {
            btnEntendido.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();

        // Ajuste de largura padrão do sistema
        if (dialog.getWindow() != null) {
            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
            int maxDialogWidth = (int) (380 * context.getResources().getDisplayMetrics().density);
            int finalWidth = Math.min(maxDialogWidth, (int) (widthPixels * 0.9));
            dialog.getWindow().setLayout(finalWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }

    public static void showDebtorAlertaDialog(Context context, float valorVencido, float valorVencendo, String mensagemPersonalizada, Runnable onConfirm) {
        showDebtorAlertaDialog(context, valorVencido, valorVencendo, mensagemPersonalizada, null, onConfirm);
    }

    public static void showDebtorAlertaDialog(Context context, float valorVencido, float valorVencendo, String mensagemPersonalizada, String textBotaoConfirmar, Runnable onConfirm) {
        showDebtorAlertaDialog(context, valorVencido, valorVencendo, mensagemPersonalizada, textBotaoConfirmar, null, onConfirm, null);
    }

    public static void showDebtorAlertaDialog(Context context, float valorVencido, float valorVencendo, String mensagemPersonalizada, String textBotaoConfirmar, String textBotaoNegar, Runnable onConfirm, Runnable onCancel) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        View viewAlerta = LayoutInflater.from(context).inflate(R.layout.layout_dialog_alerta_devedor, null);

        TextView tvTitulo = viewAlerta.findViewById(R.id.tvTituloAlerta);
        ImageView ivIcone = viewAlerta.findViewById(R.id.ivIconeAlerta);
        TextView tvMensagem = viewAlerta.findViewById(R.id.tvMensagemRodape);
        
        View lyVencido = viewAlerta.findViewById(R.id.lyVencido);
        TextView tvSaldoVencido = viewAlerta.findViewById(R.id.tvSaldoVencido);
        
        View lyVencendo = viewAlerta.findViewById(R.id.lyVencendo);
        TextView tvSaldoVencendo = viewAlerta.findViewById(R.id.tvSaldoVencendo);
        
        Button btnSim = viewAlerta.findViewById(R.id.btnSimDevedor);
        Button btnNao = viewAlerta.findViewById(R.id.btnNaoDevedor);

        if (mensagemPersonalizada != null && !mensagemPersonalizada.isEmpty()) {
            tvMensagem.setText(mensagemPersonalizada);
        }

        if (textBotaoConfirmar != null && !textBotaoConfirmar.isEmpty()) {
            btnSim.setText(textBotaoConfirmar);
        }

        if (textBotaoNegar != null && !textBotaoNegar.isEmpty()) {
            btnNao.setText(textBotaoNegar);
        }

        // Lógica Dinâmica de Exibição
        if (valorVencido > 0) {
            lyVencido.setVisibility(View.VISIBLE);
            tvSaldoVencido.setText(MascaraUtil.mascaraRealSimbolo(valorVencido));
            tvTitulo.setText("ALERTA DE INADIMPLÊNCIA");
            tvTitulo.setTextColor(Color.parseColor("#C62828"));
            ivIcone.setImageResource(R.drawable.ic_check_alert_red);
        }

        if (valorVencendo > 0) {
            lyVencendo.setVisibility(View.VISIBLE);
            tvSaldoVencendo.setText(MascaraUtil.mascaraRealSimbolo(valorVencendo));
            // Se não tiver vencido, suaviza o título
            if (valorVencido <= 0) {
                tvTitulo.setText("INFORMATIVO FINANCEIRO");
                tvTitulo.setTextColor(Color.parseColor("#E67E22"));
                ivIcone.setImageResource(R.drawable.ic_alerta_mensagem);
                ivIcone.setColorFilter(Color.parseColor("#E67E22"));
                
                // Muda cor dos botões para o padrão de aviso (laranja)
                btnSim.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E67E22")));
            }
        }

        builder.setView(viewAlerta);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();

        if (btnSim != null) btnSim.setOnClickListener(v -> { 
            dialog.dismiss(); 
            if (onConfirm != null) onConfirm.run(); 
        });
        
        if (btnNao != null) btnNao.setOnClickListener(v -> {
            dialog.dismiss();
            if (onCancel != null) onCancel.run();
        });

        dialog.show();

        if (dialog.getWindow() != null) {
            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
            int maxDialogWidth = (int) (380 * context.getResources().getDisplayMetrics().density);
            int finalWidth = Math.min(maxDialogWidth, (int) (widthPixels * 0.9));
            dialog.getWindow().setLayout(finalWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }

    public static void showFeedbackDialog(
            Context context,
            String titulo,
            String mensagem,
            boolean isSuccess,
            Integer iconResId,
            Integer corBotao, // Pode ser null para usar a cor padrão
            Runnable onConfirm) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado_positive_button, null);
        ImageView imgIcon = headerView.findViewById(R.id.dialog_icon);
        if (iconResId != null && iconResId != 0)
            imgIcon.setImageResource(iconResId);
        else {
            if (isSuccess) imgIcon.setImageResource(R.drawable.ic_baseline_check_circle_outline_24_color_primary);
            else imgIcon.setImageResource(R.drawable.ic_baseline_error_outline_24_color_primary);

        }

        builder.setCustomTitle(headerView);
        builder.setMessage(mensagem);
        builder.setCancelable(false);

        builder.setPositiveButton(" OK ", (dialog, which) -> {
            if (onConfirm != null) onConfirm.run();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (btnPositive != null) {
            btnPositive.setBackgroundTintList(null); // Obrigatório para o fundo aparecer
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(4f * context.getResources().getDisplayMetrics().density);
            int corFinal = context.getColor(R.color.colorPrimary);
            shape.setColor(corFinal);
            btnPositive.setBackground(shape);
            btnPositive.setTextColor(context.getColor(R.color.branco));
            btnPositive.setTextSize(16);
            LinearLayout parent = (LinearLayout) btnPositive.getParent();
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof android.widget.Space)
                    child.setVisibility(View.GONE);
            }

            parent.setGravity(Gravity.CENTER);
            parent.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
            params.width = (int) (200 * context.getResources().getDisplayMetrics().density);
            params.height = (int) (40 * context.getResources().getDisplayMetrics().density);
            params.weight = 0;
            params.gravity = Gravity.CENTER;
            params.setMargins(0, 48, 0, 48);
            btnPositive.setLayoutParams(params);
            btnPositive.setGravity(Gravity.CENTER);
            parent.requestLayout();
        }
    }

    public static void showAnimationFeedbackDialog(
            Context context,
            String titulo,
            String mensagem,
            boolean isSuccess,
            Integer iconResId,
            Integer corBotao,
            Runnable onConfirm) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado_positive_button, null);
        ImageView imgIcon = headerView.findViewById(R.id.dialog_icon);

        if (iconResId != null && iconResId != 0)
            imgIcon.setImageResource(iconResId);
        else {
            if (isSuccess) imgIcon.setImageResource(R.drawable.ic_baseline_check_circle_outline_24_color_primary);
            else imgIcon.setImageResource(R.drawable.ic_baseline_error_outline_24_color_primary);
        }

        // ADICIONA ANIMAÇÃO DE PULSAR (EXCLUSIVA PARA ESTE MÉTODO)
        ScaleAnimation pulse = new ScaleAnimation(1.0f, 1.20f, 1.0f, 1.20f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        pulse.setDuration(900);
        pulse.setRepeatCount(Animation.INFINITE);
        pulse.setRepeatMode(Animation.REVERSE);
        imgIcon.startAnimation(pulse);

        builder.setCustomTitle(headerView);
        builder.setMessage(mensagem);
        builder.setCancelable(false);

        builder.setPositiveButton(" OK ", (dialog, which) -> {
            if (onConfirm != null) onConfirm.run();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (btnPositive != null) {
            btnPositive.setBackgroundTintList(null);
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(4f * context.getResources().getDisplayMetrics().density);
            int corFinal = context.getColor(R.color.colorPrimary);
            shape.setColor(corFinal);
            btnPositive.setBackground(shape);
            btnPositive.setTextColor(context.getColor(R.color.branco));
            btnPositive.setTextSize(16);
            LinearLayout parent = (LinearLayout) btnPositive.getParent();
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof android.widget.Space)
                    child.setVisibility(View.GONE);
            }

            parent.setGravity(Gravity.CENTER);
            parent.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
            params.width = (int) (200 * context.getResources().getDisplayMetrics().density);
            params.height = (int) (40 * context.getResources().getDisplayMetrics().density);
            params.weight = 0;
            params.gravity = Gravity.CENTER;
            params.setMargins(0, 48, 0, 48);
            btnPositive.setLayoutParams(params);
            btnPositive.setGravity(Gravity.CENTER);
            parent.requestLayout();
        }
    }

    public static void showActionFeedbackDialogOld(
            Context context,
            String titulo,
            String mensagem,
            boolean isSuccess,
            String textPositivo,
            String textNegativo,
            Integer corBotaoPositivo,
            OnActionSelectedListener listener) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado_positive_button, null);
        ImageView imgIcon = headerView.findViewById(R.id.dialog_icon);
        imgIcon.setImageResource(R.drawable.ic_help_new);
        builder.setCustomTitle(headerView);
        builder.setMessage(mensagem);
        builder.setCancelable(false);
        builder.setPositiveButton(textPositivo, (dialog, which) -> {
            if (listener != null) listener.onPositive();
        });

        builder.setNegativeButton(textNegativo, (dialog, which) -> {
            if (listener != null) listener.onNegative();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Button btnPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (btnPos != null && btnNeg != null) {
            LinearLayout parent = (LinearLayout) btnPos.getParent();
            parent.setGravity(Gravity.CENTER); // Centraliza o bloco de botões
            parent.setOrientation(LinearLayout.HORIZONTAL); // Garante que fiquem lado a lado
            parent.setPadding(0, 0, 0, 0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof android.widget.Space)
                    child.setVisibility(View.GONE);
            }

            int corPositiva = context.getColor(R.color.colorButton);
            int corNegativa = context.getColor(R.color.colorButton);
            styleActionButton(context, btnPos, corPositiva);
            styleActionButton(context, btnNeg, corNegativa);
            parent.requestLayout();
        }
    }

    public static void showActionFeedbackDialog(
            Context context,
            String titulo,
            String mensagem,
            boolean isSuccess,
            String textPositivo,
            String textNegativo,
            Integer corBotaoPositivo,
            OnActionSelectedListener listener) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado_positive_button, null);
        ImageView imgIcon = headerView.findViewById(R.id.dialog_icon);
        imgIcon.setImageResource(R.drawable.ic_help_color_primary_24_24);
        builder.setCustomTitle(headerView);
        builder.setMessage(mensagem);
        builder.setCancelable(false);
        builder.setPositiveButton(textPositivo, (dialog, which) -> {
            if (listener != null) listener.onPositive();
        });
        builder.setNegativeButton(textNegativo, (dialog, which) -> {
            if (listener != null) listener.onNegative();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Button btnPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (btnPos != null && btnNeg != null) {
            LinearLayout parent = (LinearLayout) btnPos.getParent();
            android.view.ViewGroup.LayoutParams lp = parent.getLayoutParams();
            lp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            parent.setLayoutParams(lp);
            parent.setGravity(Gravity.CENTER);
            parent.setPadding(0, 0, 0, 0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof android.widget.Space)
                    child.setVisibility(View.GONE);
            }
            int corPositiva = context.getColor(R.color.colorButton);
            int corNegativa = context.getColor(R.color.colorButton);
            styleActionButton(context, btnPos, corPositiva);
            styleActionButton(context, btnNeg, corNegativa);
            parent.requestLayout();
        }
    }
    private static void styleActionButton(Context context, Button button, int corFundo) {
        if (button == null) return;
        button.setBackgroundTintList(null);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(4f * context.getResources().getDisplayMetrics().density);
        shape.setColor(corFundo);
        button.setBackground(shape);
        button.setTextColor(context.getColor(R.color.branco));
        button.setTextSize(16);
        button.setAllCaps(true);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
        params.width = (int) (120 * context.getResources().getDisplayMetrics().density);
        params.height = (int) (40 * context.getResources().getDisplayMetrics().density);
        params.weight = 0;
        params.gravity = Gravity.CENTER;
        int marginH = (int) (6 * context.getResources().getDisplayMetrics().density);
        int marginV = (int) (12 * context.getResources().getDisplayMetrics().density);
        params.setMargins(marginH, marginV, marginH, marginV);
        button.setLayoutParams(params);
        button.setGravity(Gravity.CENTER);
    }

    public static void styleVerticalButton(Context context, Button button, int corFundo, Integer iconResId) {
        if (button == null) return;

        button.setBackgroundTintList(null);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(4f * context.getResources().getDisplayMetrics().density); // Visual arredondado original
        shape.setColor(corFundo);
        button.setBackground(shape);
        button.setTextColor(context.getColor(R.color.branco));
        button.setTextSize(16);
        button.setAllCaps(true);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) button.getLayoutParams();
        lp.width = (int) (240 * context.getResources().getDisplayMetrics().density);
        lp.height = (int) (40 * context.getResources().getDisplayMetrics().density);
        lp.gravity = Gravity.CENTER;
        
        int marginV = (int) (6 * context.getResources().getDisplayMetrics().density);
        lp.setMargins(0, marginV, 0, marginV);

        button.setLayoutParams(lp);
        button.setGravity(Gravity.CENTER);
    }

    public static void styleOutlinedButton(Context context, Button button, int corBorda, Integer iconResId) {
        if (button == null) return;

        button.setBackgroundTintList(null);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(8f * context.getResources().getDisplayMetrics().density);
        shape.setStroke((int) (1 * context.getResources().getDisplayMetrics().density), corBorda);
        shape.setColor(Color.TRANSPARENT);
        
        button.setBackground(shape);
        button.setTextColor(corBorda);
        button.setTextSize(13);
        button.setAllCaps(true);

        if (iconResId != null && iconResId != 0) {
            Drawable icon = ContextCompat.getDrawable(context, iconResId);
            if (icon != null) {
                icon = DrawableCompat.wrap(icon).mutate();
                DrawableCompat.setTint(icon, corBorda);
                icon.setBounds(0, 0, 40, 40);
                button.setCompoundDrawables(icon, null, null, null);
                button.setCompoundDrawablePadding(16);
            }
        }

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) button.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = (int) (44 * context.getResources().getDisplayMetrics().density);
        button.setLayoutParams(lp);
    }

    private static void applyBottomMarginToLastVisibleButton(LinearLayout container, Context context) {
        for (int i = container.getChildCount() - 1; i >= 0; i--) {
            View child = container.getChildAt(i);
            if (child instanceof Button && child.getVisibility() == View.VISIBLE) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                lp.bottomMargin = (int) (20 * context.getResources().getDisplayMetrics().density);
                child.setLayoutParams(lp);
                break;
            }
        }
    }

    public static void showRaioXClienteDialog(
            Context context,
            String nomeCliente,
            double totalFaturado,
            int totalPedidos,
            double ticketMedio,
            String dataUltimaCompra,
            String frequenciaMedia,
            String topProdutosTexto,
            Runnable onIniciarPedido) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded);
        View headerView = LayoutInflater.from(context).inflate(R.layout.dialog_header_centralizado_positive_button, null);
        ImageView imgIcon = headerView.findViewById(R.id.dialog_icon);
        if (imgIcon != null) {
            imgIcon.setImageResource(R.drawable.baseline_analytics_24);
        }

        String mensagem = nomeCliente + "\n\n" +
                "• Faturamento Acumulado: " + MascaraUtil.mascaraRealSimboloD(totalFaturado) + "\n" +
                "• Histórico: " + totalPedidos + " pedido(s) realizado(s)\n" +
                "• Ticket Médio: " + MascaraUtil.mascaraRealSimboloD(ticketMedio) + "\n" +
                "• Última Compra: " + dataUltimaCompra + "\n" +
                "• Frequência Média: " + frequenciaMedia + "\n\n" +
                "TOP 5 PRODUTOS MAIS COMPRADOS (CURVA A):\n" + topProdutosTexto;

        builder.setCustomTitle(headerView);
        builder.setTitle("RAIO-X DE COMPRAS DO CLIENTE");
        builder.setMessage(mensagem);
        builder.setCancelable(false);

        builder.setPositiveButton("INICIAR PEDIDO", (dialog, which) -> {
            if (onIniciarPedido != null) onIniciarPedido.run();
        });

        builder.setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (btnPos != null && btnNeg != null) {
            LinearLayout parent = (LinearLayout) btnPos.getParent();
            android.view.ViewGroup.LayoutParams lp = parent.getLayoutParams();
            lp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            parent.setLayoutParams(lp);
            parent.setGravity(Gravity.CENTER);
            parent.setPadding(0, 0, 0, 0);

            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof android.widget.Space)
                    child.setVisibility(View.GONE);
            }

            int corPositiva = context.getColor(R.color.colorPrimary);
            int corNegativa = context.getColor(R.color.colorButton);
            styleActionButton(context, btnPos, corPositiva);
            styleActionButton(context, btnNeg, corNegativa);
            parent.requestLayout();
        }
    }

    public interface OnCheckInConfirmedListener {
        void onCheckInConfirmed(GpsLocationHelper.LocationData locationData, byte[] fotoBytes);
    }

    public static void showCheckInClienteDialog(
            Context context,
            String razaoSocial,
            String nomeFantasia,
            String endereco,
            OnCheckInConfirmedListener onConfirmCheckIn) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_checkin_cliente, null);

        TextView tvRazao = dialogView.findViewById(R.id.tvCheckInRazaoSocial);
        TextView tvFantasia = dialogView.findViewById(R.id.tvCheckInNomeFantasia);
        TextView tvEnd = dialogView.findViewById(R.id.tvCheckInEndereco);

        TextView tvLat = dialogView.findViewById(R.id.tvCheckInLatitude);
        TextView tvLng = dialogView.findViewById(R.id.tvCheckInLongitude);
        TextView tvAltPrec = dialogView.findViewById(R.id.tvCheckInAltitudePrecisao);
        ImageView ivMap = dialogView.findViewById(R.id.ivPlaceholderMapa);

        if (tvRazao != null && razaoSocial != null && !razaoSocial.isEmpty()) {
            tvRazao.setText(razaoSocial);
        }
        if (tvFantasia != null && nomeFantasia != null && !nomeFantasia.isEmpty()) {
            tvFantasia.setText(nomeFantasia);
        }
        if (tvEnd != null && endereco != null && !endereco.isEmpty()) {
            tvEnd.setText(endereco);
        }

        Button btnConfirmarCheckIn = dialogView.findViewById(R.id.btnConfirmarCheckIn);
        if (btnConfirmarCheckIn != null) {
            btnConfirmarCheckIn.setEnabled(false);
            btnConfirmarCheckIn.setAlpha(0.5f);
        }

        final GpsLocationHelper.LocationData[] currentLoc = new GpsLocationHelper.LocationData[1];

        // BINDING DE LOCALIZAÇÃO GPS EM TEMPO REAL
        if (tvLat != null) tvLat.setText("Obtendo...");
        if (tvLng != null) tvLng.setText("Obtendo...");
        if (tvAltPrec != null) tvAltPrec.setText("Buscando GPS...");

        GpsLocationHelper.obterLocalizacaoAtual(context, new GpsLocationHelper.OnLocationResultListener() {
            @Override
            public void onLocationReceived(GpsLocationHelper.LocationData locationData) {
                currentLoc[0] = locationData;
                if (btnConfirmarCheckIn != null) {
                    btnConfirmarCheckIn.setEnabled(true);
                    btnConfirmarCheckIn.setAlpha(1.0f);
                }
                if (locationData != null && locationData.hasLocation) {
                    if (tvLat != null) tvLat.setText(String.format(java.util.Locale.US, "%.5f", locationData.latitude));
                    if (tvLng != null) tvLng.setText(String.format(java.util.Locale.US, "%.5f", locationData.longitude));
                    if (tvAltPrec != null) tvAltPrec.setText(String.format(java.util.Locale.US, "%.0fm / %.1fm", locationData.altitude, locationData.accuracy));

                    // CARREGA IMAGEM ESTÁTICA DO MAPA COM GLIDE E FALLBACK AUTOMÁTICO
                    if (ivMap != null) {
                        try {
                            ivMap.setColorFilter(null);
                            TextView tvTextoPlaceholder = dialogView.findViewById(R.id.tvTextoPlaceholderMapa);
                            if (tvTextoPlaceholder != null) {
                                tvTextoPlaceholder.setVisibility(View.GONE);
                            }
                            String urlGoogle = GpsLocationHelper.getGoogleStaticMapUrl(context, locationData.latitude, locationData.longitude, 600, 300);
                            String urlOsm = GpsLocationHelper.getOsmStaticMapUrl(locationData.latitude, locationData.longitude, 600, 300);

                            com.bumptech.glide.Glide.with(context)
                                    .load(urlGoogle)
                                    .error(com.bumptech.glide.Glide.with(context).load(urlOsm))
                                    .into(ivMap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onError(String message) {
                // TRAVA DE SEGURANÇA: Mantém o botão de confirmação BLOQUEADO se não houver coordenadas válidas
                if (btnConfirmarCheckIn != null) {
                    btnConfirmarCheckIn.setEnabled(false);
                    btnConfirmarCheckIn.setAlpha(0.4f);
                }
                if (tvLat != null) tvLat.setText("Indisponível");
                if (tvLng != null) tvLng.setText("Indisponível");
                if (tvAltPrec != null) {
                    tvAltPrec.setText(message);
                    tvAltPrec.setTextColor(context.getColor(R.color.vermelho));
                }
                TextView tvTextoPlaceholder = dialogView.findViewById(R.id.tvTextoPlaceholderMapa);
                if (tvTextoPlaceholder != null) {
                    tvTextoPlaceholder.setText("Mapa Indisponível (Sem Sinal GPS / Internet)");
                }
            }
        });

        ImageView ivCapturarFoto = dialogView.findViewById(R.id.ivCapturarFoto);
        if (ivCapturarFoto != null && context instanceof Activity) {
            ivCapturarFoto.setOnClickListener(v -> {
                CameraPhotoHelper.dispatchTakePictureIntent((Activity) context, ivCapturarFoto);
            });
        }

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        if (btnConfirmarCheckIn != null) {
            btnConfirmarCheckIn.setOnClickListener(v -> {
                dialog.dismiss();
                if (onConfirmCheckIn != null) {
                    byte[] fotoBytes = CameraPhotoHelper.getPhotoBytes(75);
                    onConfirmCheckIn.onCheckInConfirmed(currentLoc[0], fotoBytes);
                }
            });
        }

        Button btnCancelarCheckIn = dialogView.findViewById(R.id.btnCancelarCheckIn);
        if (btnCancelarCheckIn != null) {
            btnCancelarCheckIn.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    public interface OnMotivoSelectedListener {
        void onMotivoSelected(String idMotivo, String descricaoMotivo);
    }

    public static void showOpcoesPosCheckInDialog(
            Context context,
            Runnable onContinuarPedido,
            Runnable onNaoVenda) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_opcao_atendimento_venda, null);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        View cardPedido = dialogView.findViewById(R.id.cardOpcaoPedido);
        if (cardPedido != null) {
            cardPedido.setOnClickListener(v -> {
                dialog.dismiss();
                if (onContinuarPedido != null) onContinuarPedido.run();
            });
        }

        View cardNaoVenda = dialogView.findViewById(R.id.cardOpcaoNaoVenda);
        if (cardNaoVenda != null) {
            cardNaoVenda.setOnClickListener(v -> {
                dialog.dismiss();
                if (onNaoVenda != null) onNaoVenda.run();
            });
        }

        Button btnVoltar = dialogView.findViewById(R.id.btnVoltarAtendimento);
        if (btnVoltar != null) {
            btnVoltar.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    public interface OnNaoVendaConfirmedListener {
        void onNaoVendaConfirmed(List<String> motivosSelecionados, String observacao);
    }

    public static void showRegistroNaoVendaDialog(
            Context context,
            com.example.rcksuporte05.rcksistemas.DAO.DBHelper db,
            String razaoSocial,
            String nomeFantasia,
            String endereco,
            OnNaoVendaConfirmedListener listener) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_registro_nao_venda, null);

        TextView tvRazao = dialogView.findViewById(R.id.tvNaoVendaRazaoSocial);
        TextView tvFantasia = dialogView.findViewById(R.id.tvNaoVendaNomeFantasia);
        TextView tvEnd = dialogView.findViewById(R.id.tvNaoVendaEndereco);

        if (tvRazao != null && razaoSocial != null && !razaoSocial.isEmpty()) {
            tvRazao.setText(razaoSocial);
        }
        if (tvFantasia != null && nomeFantasia != null && !nomeFantasia.isEmpty()) {
            tvFantasia.setText(nomeFantasia);
        }
        if (tvEnd != null && endereco != null && !endereco.isEmpty()) {
            tvEnd.setText(endereco);
        }

        // CARREGA MOTIVOS E CRIA CHECKBOXES DINÂMICOS
        LinearLayout containerCheckboxes = dialogView.findViewById(R.id.containerCheckboxesMotivos);
        List<android.widget.CheckBox> checkBoxesList = new java.util.ArrayList<>();

        com.example.rcksuporte05.rcksistemas.DAO.CadastroMotivoDAO motivoDAO = new com.example.rcksuporte05.rcksistemas.DAO.CadastroMotivoDAO(db);
        List<com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento> listaMotivos = motivoDAO.getLista();

        List<String> titulosMotivos = new java.util.ArrayList<>();
        if (listaMotivos != null && !listaMotivos.isEmpty()) {
            for (com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento m : listaMotivos) {
                titulosMotivos.add(m.getMotivo());
            }
        } else {
            titulosMotivos.add("Estoque cheio / Sem necessidade no momento");
            titulosMotivos.add("Preço da concorrência mais atraente");
            titulosMotivos.add("Cliente ausente / Estabelecimento fechado");
            titulosMotivos.add("Cliente com pendência / problema financeiro");
            titulosMotivos.add("Comprador em reunião / não pôde atender");
            titulosMotivos.add("Outros motivos");
        }

        if (containerCheckboxes != null) {
            for (String itemMotivo : titulosMotivos) {
                android.widget.CheckBox cb = new android.widget.CheckBox(context);
                cb.setText(itemMotivo);
                cb.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 12);
                cb.setTextColor(context.getColor(R.color.preto));
                cb.setPadding(8, 4, 8, 4);
                containerCheckboxes.addView(cb);
                checkBoxesList.add(cb);
            }
        }

        EditText edtObservacao = dialogView.findViewById(R.id.edtObservacaoNaoVenda);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnConfirmar = dialogView.findViewById(R.id.btnConfirmarNaoVenda);
        if (btnConfirmar != null) {
            btnConfirmar.setOnClickListener(v -> {
                List<String> motivosSelecionados = new java.util.ArrayList<>();
                for (android.widget.CheckBox cb : checkBoxesList) {
                    if (cb.isChecked()) {
                        motivosSelecionados.add(cb.getText().toString());
                    }
                }

                String obs = edtObservacao != null ? edtObservacao.getText().toString().trim() : "";

                if (motivosSelecionados.isEmpty() && obs.isEmpty()) {
                    Toast.makeText(context, "Selecione ao menos um motivo ou digite uma observação!", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();
                if (listener != null) {
                    listener.onNaoVendaConfirmed(motivosSelecionados, obs);
                }
            });
        }

        Button btnCancelar = dialogView.findViewById(R.id.btnCancelarNaoVenda);
        if (btnCancelar != null) {
            btnCancelar.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }
}
