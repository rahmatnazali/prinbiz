package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzra;
import com.google.android.gms.internal.zzrb;
import com.google.android.gms.internal.zzrc;
import java.lang.ref.WeakReference;
import org.apache.commons.net.telnet.TelnetCommand;

public abstract class zza {
    final zza wG;
    protected int wH;
    protected int wI;
    protected boolean wJ;
    private boolean wK;
    private boolean wL;
    private boolean wM;

    static final class zza {
        public final Uri uri;

        public zza(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object obj) {
            return !(obj instanceof zza) ? false : this == obj ? true : zzaa.equal(((zza) obj).uri, this.uri);
        }

        public int hashCode() {
            return zzaa.hashCode(this.uri);
        }
    }

    public static final class zzb extends zza {
        private WeakReference<ImageView> wN;

        public zzb(ImageView imageView, int i) {
            super(null, i);
            com.google.android.gms.common.internal.zzb.zzu(imageView);
            this.wN = new WeakReference(imageView);
        }

        public zzb(ImageView imageView, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzb.zzu(imageView);
            this.wN = new WeakReference(imageView);
        }

        private void zza(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
            Object obj = (z2 || z3) ? null : 1;
            if (obj != null && (imageView instanceof zzrb)) {
                int zzars = ((zzrb) imageView).zzars();
                if (this.wI != 0 && zzars == this.wI) {
                    return;
                }
            }
            boolean zzc = zzc(z, z2);
            Drawable zza = zzc ? zza(imageView.getDrawable(), drawable) : drawable;
            imageView.setImageDrawable(zza);
            if (imageView instanceof zzrb) {
                zzrb com_google_android_gms_internal_zzrb = (zzrb) imageView;
                com_google_android_gms_internal_zzrb.zzp(z3 ? this.wG.uri : null);
                com_google_android_gms_internal_zzrb.zzga(obj != null ? this.wI : 0);
            }
            if (zzc) {
                ((zzra) zza).startTransition(TelnetCommand.SB);
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof zzb)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            ImageView imageView = (ImageView) this.wN.get();
            ImageView imageView2 = (ImageView) ((zzb) obj).wN.get();
            boolean z = (imageView2 == null || imageView == null || !zzaa.equal(imageView2, imageView)) ? false : true;
            return z;
        }

        public int hashCode() {
            return 0;
        }

        protected void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageView imageView = (ImageView) this.wN.get();
            if (imageView != null) {
                zza(imageView, drawable, z, z2, z3);
            }
        }
    }

    public static final class zzc extends zza {
        private WeakReference<OnImageLoadedListener> wO;

        public zzc(OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzb.zzu(onImageLoadedListener);
            this.wO = new WeakReference(onImageLoadedListener);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof zzc)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzc com_google_android_gms_common_images_zza_zzc = (zzc) obj;
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.wO.get();
            OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) com_google_android_gms_common_images_zza_zzc.wO.get();
            boolean z = onImageLoadedListener2 != null && onImageLoadedListener != null && zzaa.equal(onImageLoadedListener2, onImageLoadedListener) && zzaa.equal(com_google_android_gms_common_images_zza_zzc.wG, this.wG);
            return z;
        }

        public int hashCode() {
            return zzaa.hashCode(this.wG);
        }

        protected void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
            if (!z2) {
                OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.wO.get();
                if (onImageLoadedListener != null) {
                    onImageLoadedListener.onImageLoaded(this.wG.uri, drawable, z3);
                }
            }
        }
    }

    public zza(Uri uri, int i) {
        this.wH = 0;
        this.wI = 0;
        this.wJ = false;
        this.wK = true;
        this.wL = false;
        this.wM = true;
        this.wG = new zza(uri);
        this.wI = i;
    }

    private Drawable zza(Context context, zzrc com_google_android_gms_internal_zzrc, int i) {
        return context.getResources().getDrawable(i);
    }

    protected zzra zza(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof zzra) {
            drawable = ((zzra) drawable).zzarq();
        }
        return new zzra(drawable, drawable2);
    }

    void zza(Context context, Bitmap bitmap, boolean z) {
        com.google.android.gms.common.internal.zzb.zzu(bitmap);
        zza(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    void zza(Context context, zzrc com_google_android_gms_internal_zzrc) {
        if (this.wM) {
            zza(null, false, true, false);
        }
    }

    void zza(Context context, zzrc com_google_android_gms_internal_zzrc, boolean z) {
        Drawable drawable = null;
        if (this.wI != 0) {
            drawable = zza(context, com_google_android_gms_internal_zzrc, this.wI);
        }
        zza(drawable, z, false, false);
    }

    protected abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);

    protected boolean zzc(boolean z, boolean z2) {
        return (!this.wK || z2 || z) ? false : true;
    }

    public void zzfy(int i) {
        this.wI = i;
    }
}
