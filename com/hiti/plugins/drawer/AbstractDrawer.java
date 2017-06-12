package com.hiti.plugins.drawer;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface AbstractDrawer {
    void draw(Canvas canvas);

    void drawBitmap(Bitmap bitmap);

    int getBitmapHeight();

    int getBitmapWidth();
}
