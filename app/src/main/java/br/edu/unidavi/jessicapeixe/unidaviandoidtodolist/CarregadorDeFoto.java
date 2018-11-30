package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import java.io.IOException;

public class CarregadorDeFoto {

    public static Bitmap carrega(String caminhoFoto, ImageView vwImagem) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(caminhoFoto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION))) {
            case ExifInterface.ORIENTATION_NORMAL:     return abreFotoERotaciona(caminhoFoto,   0, vwImagem);
            case ExifInterface.ORIENTATION_ROTATE_90:  return abreFotoERotaciona(caminhoFoto,  90, vwImagem);
            case ExifInterface.ORIENTATION_ROTATE_180: return abreFotoERotaciona(caminhoFoto, 180, vwImagem);
            case ExifInterface.ORIENTATION_ROTATE_270: return abreFotoERotaciona(caminhoFoto, 270, vwImagem);
        }

        return null;
    }

    private static Bitmap abreFotoERotaciona(String caminhoFoto, int angulo, ImageView vwImagem) {

        int width  = 140; //vwImagem.getWidth() > 0 ? vwImagem.getWidth() : 90;
        int height = 170; //vwImagem.getHeight() > 0 ? vwImagem.getHeight() : 110;

        BitmapFactory.Options facOptions = new BitmapFactory.Options();
        facOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(caminhoFoto, facOptions);

        int scaleFactor = Math.min(facOptions.outWidth/ width, facOptions.outHeight / height);

        facOptions.inJustDecodeBounds = false;
        facOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto, facOptions);

        Matrix matrix = new Matrix();
        matrix.postRotate(angulo);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}