package com.hiti.ImageFilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;

public class ImageFilterPlus {
    Context m_context;

    public ImageFilterPlus(Context context) {
        this.m_context = null;
        this.m_context = context;
    }

    public int ProcessImage_RGB(Bitmap oriBmp, float fRed, float fGreen, float fBlue) {
        if (oriBmp == null) {
            return -1;
        }
        float[] rgb = GetRGBcolorMatrix((((float) 50) + fRed) / ((float) 50), (((float) 50) + fGreen) / ((float) 50), (((float) 50) + fBlue) / ((float) 50));
        new ColorMatrix().set(rgb);
        BitmapMonitorResult rgbBmr = BitmapMonitor.Copy(oriBmp, oriBmp.getConfig(), true);
        if (!rgbBmr.IsSuccess()) {
            return rgbBmr.GetResult();
        }
        oriBmp.eraseColor(0);
        Canvas canvas = new Canvas(oriBmp);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(rgb));
        canvas.drawBitmap(rgbBmr.GetBitmap(), 0.0f, 0.0f, paint);
        rgbBmr.GetBitmap().recycle();
        return 0;
    }

    private float[] GetRGBcolorMatrix(float R, float G, float B) {
        return new float[]{R, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, G, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, B, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    }
}
