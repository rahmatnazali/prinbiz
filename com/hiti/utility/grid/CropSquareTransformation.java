package com.hiti.utility.grid;

import android.graphics.Bitmap;
import com.squareup.picasso.Transformation;

public class CropSquareTransformation implements Transformation {
    public Bitmap transform(Bitmap sourceBmp) {
        int size = Math.min(sourceBmp.getWidth(), sourceBmp.getHeight());
        Bitmap result = Bitmap.createBitmap(sourceBmp, (sourceBmp.getWidth() - size) / 2, (sourceBmp.getHeight() - size) / 2, size, size);
        if (result != sourceBmp) {
            sourceBmp.recycle();
        }
        return result;
    }

    public String key() {
        return "square()";
    }
}
