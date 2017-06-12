package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.net.Uri;
import android.widget.ImageView;

public final class zzrb extends ImageView {
    private Uri xg;
    private int xh;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public int zzars() {
        return this.xh;
    }

    public void zzga(int i) {
        this.xh = i;
    }

    public void zzp(Uri uri) {
        this.xg = uri;
    }
}
