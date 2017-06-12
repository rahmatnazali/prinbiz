package com.hiti.plugins.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.view.View;

public class QRCodeDrawer extends View implements AbstractDrawer {
    private static final int MinimumUnitPixel = 3;
    private Bitmap bitmap;
    private int bitmapH;
    private int bitmapW;
    private Paint paint;

    public QRCodeDrawer(Context context) {
        super(context);
        this.paint = new Paint();
    }

    public void setPic(String pic) {
        if (pic != null && pic.length() != 0) {
            try {
                this.bitmap = Media.getBitmap(getContext().getContentResolver(), Uri.parse(pic));
                this.bitmapH = this.bitmap.getHeight();
                this.bitmapW = this.bitmap.getWidth();
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onDraw(Canvas c) {
    }

    public int getBitmapHeight() {
        return 0;
    }

    public int getBitmapWidth() {
        return 0;
    }

    public void drawBitmap(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        canvas.clipRect(0, 0, getBitmapWidth(), getBitmapHeight());
        draw(canvas);
    }
}
