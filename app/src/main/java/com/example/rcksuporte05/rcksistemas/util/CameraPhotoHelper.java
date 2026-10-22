package com.example.rcksuporte05.rcksistemas.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraPhotoHelper {

    public static final int REQUEST_CAMERA_CODE = 9988;
    private static File currentPhotoFile;
    private static Uri currentPhotoUri;
    private static ImageView currentTargetImageView;

    /**
     * Inicia a intenção de câmera vinculando o ImageView que receberá a foto.
     */
    public static boolean dispatchTakePictureIntent(Activity activity, ImageView targetImageView) {
        currentTargetImageView = targetImageView;
        if (activity == null) return false;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            try {
                currentPhotoFile = createImageFile(activity);
                if (currentPhotoFile != null) {
                    currentPhotoUri = FileProvider.getUriForFile(
                            activity,
                            activity.getPackageName() + ".provider",
                            currentPhotoFile
                    );
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                activity.startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean dispatchTakePictureIntent(Activity activity) {
        return dispatchTakePictureIntent(activity, null);
    }

    /**
     * Cria o arquivo de foto com timestamp único na pasta de fotos do app.
     */
    private static File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "CHECKIN_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir == null) {
            storageDir = context.getCacheDir();
        }
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    /**
     * Processa o resultado da câmera no onActivityResult, corrige a rotação da foto e exibe na ImageView.
     */
    public static boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap capturedBitmap = null;

            // 1. Tenta recuperar bitmap do Intent data (thumb ou captura direta)
            if (data != null && data.getExtras() != null && data.getExtras().get("data") instanceof Bitmap) {
                capturedBitmap = (Bitmap) data.getExtras().get("data");
            }

            // 2. Se não veio no data, tenta carregar do arquivo FileProvider gravado
            if (capturedBitmap == null && currentPhotoFile != null && currentPhotoFile.exists() && currentPhotoFile.length() > 0) {
                try {
                    capturedBitmap = getOptimizedAndRotatedBitmap(currentPhotoFile.getAbsolutePath(), 800, 800);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 3. Exibe a foto capturada no ImageView
            if (capturedBitmap != null && currentTargetImageView != null) {
                try {
                    currentTargetImageView.setImageTintList(null); // REMOVE O TINT LIST DO XML
                    currentTargetImageView.setColorFilter(null); // Limpa o tint para exibir a foto em cores reais
                    currentTargetImageView.setPadding(0, 0, 0, 0); // Limpa o padding do ícone
                    currentTargetImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    currentTargetImageView.setImageBitmap(capturedBitmap);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean handleActivityResult(int requestCode, int resultCode) {
        return handleActivityResult(requestCode, resultCode, null);
    }

    public static File getCurrentPhotoFile() {
        return currentPhotoFile;
    }

    public static Uri getCurrentPhotoUri() {
        return currentPhotoUri;
    }

    /**
     * Redimensiona e ajusta a rotação EXIF da foto capturada.
     */
    private static Bitmap getOptimizedAndRotatedBitmap(String photoPath, int reqWidth, int reqHeight) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        if (bitmap == null) return null;

        ExifInterface ei = new ExifInterface(photoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;
            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }

        return rotatedBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap rotated = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        if (rotated != source) {
            source.recycle();
        }
        return rotated;
    }
}
