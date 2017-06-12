package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import com.google.android.gms.common.util.zzs;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetOption;

public final class zzra extends Drawable implements Callback {
    private int mFrom;
    private boolean wK;
    private int wQ;
    private int wR;
    private int wS;
    private int wT;
    private int wU;
    private boolean wV;
    private zzb wW;
    private Drawable wX;
    private Drawable wY;
    private boolean wZ;
    private boolean xa;
    private boolean xb;
    private int xc;
    private long zzczg;

    private static final class zza extends Drawable {
        private static final zza xd;
        private static final zza xe;

        private static final class zza extends ConstantState {
            private zza() {
            }

            public int getChangingConfigurations() {
                return 0;
            }

            public Drawable newDrawable() {
                return zza.xd;
            }
        }

        static {
            xd = new zza();
            xe = new zza();
        }

        private zza() {
        }

        public void draw(Canvas canvas) {
        }

        public ConstantState getConstantState() {
            return xe;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    static final class zzb extends ConstantState {
        int mChangingConfigurations;
        int xf;

        zzb(zzb com_google_android_gms_internal_zzra_zzb) {
            if (com_google_android_gms_internal_zzra_zzb != null) {
                this.mChangingConfigurations = com_google_android_gms_internal_zzra_zzb.mChangingConfigurations;
                this.xf = com_google_android_gms_internal_zzra_zzb.xf;
            }
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public Drawable newDrawable() {
            return new zzra(this);
        }
    }

    public zzra(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = zza.xd;
        }
        this.wX = drawable;
        drawable.setCallback(this);
        zzb com_google_android_gms_internal_zzra_zzb = this.wW;
        com_google_android_gms_internal_zzra_zzb.xf |= drawable.getChangingConfigurations();
        if (drawable2 == null) {
            drawable2 = zza.xd;
        }
        this.wY = drawable2;
        drawable2.setCallback(this);
        com_google_android_gms_internal_zzra_zzb = this.wW;
        com_google_android_gms_internal_zzra_zzb.xf |= drawable2.getChangingConfigurations();
    }

    zzra(zzb com_google_android_gms_internal_zzra_zzb) {
        this.wQ = 0;
        this.wS = TelnetOption.MAX_OPTION_VALUE;
        this.wU = 0;
        this.wK = true;
        this.wW = new zzb(com_google_android_gms_internal_zzra_zzb);
    }

    public boolean canConstantState() {
        if (!this.wZ) {
            boolean z = (this.wX.getConstantState() == null || this.wY.getConstantState() == null) ? false : true;
            this.xa = z;
            this.wZ = true;
        }
        return this.xa;
    }

    public void draw(Canvas canvas) {
        int i = 1;
        int i2 = 0;
        switch (this.wQ) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                this.zzczg = SystemClock.uptimeMillis();
                this.wQ = 2;
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                if (this.zzczg >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zzczg)) / ((float) this.wT);
                    if (uptimeMillis < 1.0f) {
                        i = 0;
                    }
                    if (i != 0) {
                        this.wQ = 0;
                    }
                    this.wU = (int) ((Math.min(uptimeMillis, 1.0f) * ((float) (this.wR + 0))) + 0.0f);
                    break;
                }
                break;
        }
        i2 = i;
        i = this.wU;
        boolean z = this.wK;
        Drawable drawable = this.wX;
        Drawable drawable2 = this.wY;
        if (i2 != 0) {
            if (!z || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.wS) {
                drawable2.setAlpha(this.wS);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z) {
            drawable.setAlpha(this.wS - i);
        }
        drawable.draw(canvas);
        if (z) {
            drawable.setAlpha(this.wS);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.wS);
        }
        invalidateSelf();
    }

    public int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.wW.mChangingConfigurations) | this.wW.xf;
    }

    public ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.wW.mChangingConfigurations = getChangingConfigurations();
        return this.wW;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.wX.getIntrinsicHeight(), this.wY.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.wX.getIntrinsicWidth(), this.wY.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.xb) {
            this.xc = Drawable.resolveOpacity(this.wX.getOpacity(), this.wY.getOpacity());
            this.xb = true;
        }
        return this.xc;
    }

    @TargetApi(11)
    public void invalidateDrawable(Drawable drawable) {
        if (zzs.zzavn()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public Drawable mutate() {
        if (!this.wV && super.mutate() == this) {
            if (canConstantState()) {
                this.wX.mutate();
                this.wY.mutate();
                this.wV = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        this.wX.setBounds(rect);
        this.wY.setBounds(rect);
    }

    @TargetApi(11)
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (zzs.zzavn()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.scheduleDrawable(this, runnable, j);
            }
        }
    }

    public void setAlpha(int i) {
        if (this.wU == this.wS) {
            this.wU = i;
        }
        this.wS = i;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.wX.setColorFilter(colorFilter);
        this.wY.setColorFilter(colorFilter);
    }

    public void startTransition(int i) {
        this.mFrom = 0;
        this.wR = this.wS;
        this.wU = 0;
        this.wT = i;
        this.wQ = 1;
        invalidateSelf();
    }

    @TargetApi(11)
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (zzs.zzavn()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.unscheduleDrawable(this, runnable);
            }
        }
    }

    public Drawable zzarq() {
        return this.wY;
    }
}
