package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzrc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static final Object wr;
    private static HashSet<Uri> ws;
    private static ImageManager wt;
    private static ImageManager wu;
    private final Context mContext;
    private final Handler mHandler;
    private final Map<Uri, Long> wA;
    private final ExecutorService wv;
    private final zzb ww;
    private final zzrc wx;
    private final Map<zza, ImageReceiver> wy;
    private final Map<Uri, ImageReceiver> wz;

    @KeepName
    private final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<zza> wB;
        final /* synthetic */ ImageManager wC;

        ImageReceiver(ImageManager imageManager, Uri uri) {
            this.wC = imageManager;
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.wB = new ArrayList();
        }

        public void onReceiveResult(int i, Bundle bundle) {
            this.wC.wv.execute(new zzc(this.wC, this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public void zzarp() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            this.wC.mContext.sendBroadcast(intent);
        }

        public void zzb(zza com_google_android_gms_common_images_zza) {
            com.google.android.gms.common.internal.zzb.zzhi("ImageReceiver.addImageRequest() must be called in the main thread");
            this.wB.add(com_google_android_gms_common_images_zza);
        }

        public void zzc(zza com_google_android_gms_common_images_zza) {
            com.google.android.gms.common.internal.zzb.zzhi("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.wB.remove(com_google_android_gms_common_images_zza);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    @TargetApi(11)
    private static final class zza {
        static int zza(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    private final class zzc implements Runnable {
        private final Uri mUri;
        final /* synthetic */ ImageManager wC;
        private final ParcelFileDescriptor wD;

        public zzc(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.wC = imageManager;
            this.mUri = uri;
            this.wD = parcelFileDescriptor;
        }

        public void run() {
            com.google.android.gms.common.internal.zzb.zzhj("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z = false;
            Bitmap bitmap = null;
            if (this.wD != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.wD.getFileDescriptor());
                } catch (Throwable e) {
                    String valueOf = String.valueOf(this.mUri);
                    Log.e("ImageManager", new StringBuilder(String.valueOf(valueOf).length() + 34).append("OOM while loading bitmap for uri: ").append(valueOf).toString(), e);
                    z = true;
                }
                try {
                    this.wD.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.wC.mHandler.post(new zzf(this.wC, this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                String valueOf2 = String.valueOf(this.mUri);
                Log.w("ImageManager", new StringBuilder(String.valueOf(valueOf2).length() + 32).append("Latch interrupted while posting ").append(valueOf2).toString());
            }
        }
    }

    private final class zzd implements Runnable {
        final /* synthetic */ ImageManager wC;
        private final zza wE;

        public zzd(ImageManager imageManager, zza com_google_android_gms_common_images_zza) {
            this.wC = imageManager;
            this.wE = com_google_android_gms_common_images_zza;
        }

        public void run() {
            com.google.android.gms.common.internal.zzb.zzhi("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) this.wC.wy.get(this.wE);
            if (imageReceiver != null) {
                this.wC.wy.remove(this.wE);
                imageReceiver.zzc(this.wE);
            }
            zza com_google_android_gms_common_images_zza_zza = this.wE.wG;
            if (com_google_android_gms_common_images_zza_zza.uri == null) {
                this.wE.zza(this.wC.mContext, this.wC.wx, true);
                return;
            }
            Bitmap zza = this.wC.zza(com_google_android_gms_common_images_zza_zza);
            if (zza != null) {
                this.wE.zza(this.wC.mContext, zza, true);
                return;
            }
            Long l = (Long) this.wC.wA.get(com_google_android_gms_common_images_zza_zza.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.wE.zza(this.wC.mContext, this.wC.wx, true);
                    return;
                }
                this.wC.wA.remove(com_google_android_gms_common_images_zza_zza.uri);
            }
            this.wE.zza(this.wC.mContext, this.wC.wx);
            imageReceiver = (ImageReceiver) this.wC.wz.get(com_google_android_gms_common_images_zza_zza.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(this.wC, com_google_android_gms_common_images_zza_zza.uri);
                this.wC.wz.put(com_google_android_gms_common_images_zza_zza.uri, imageReceiver);
            }
            imageReceiver.zzb(this.wE);
            if (!(this.wE instanceof com.google.android.gms.common.images.zza.zzc)) {
                this.wC.wy.put(this.wE, imageReceiver);
            }
            synchronized (ImageManager.wr) {
                if (!ImageManager.ws.contains(com_google_android_gms_common_images_zza_zza.uri)) {
                    ImageManager.ws.add(com_google_android_gms_common_images_zza_zza.uri);
                    imageReceiver.zzarp();
                }
            }
        }
    }

    @TargetApi(14)
    private static final class zze implements ComponentCallbacks2 {
        private final zzb ww;

        public zze(zzb com_google_android_gms_common_images_ImageManager_zzb) {
            this.ww = com_google_android_gms_common_images_ImageManager_zzb;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
            this.ww.evictAll();
        }

        public void onTrimMemory(int i) {
            if (i >= 60) {
                this.ww.evictAll();
            } else if (i >= 20) {
                this.ww.trimToSize(this.ww.size() / 2);
            }
        }
    }

    private final class zzf implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        final /* synthetic */ ImageManager wC;
        private boolean wF;
        private final CountDownLatch zzale;

        public zzf(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.wC = imageManager;
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.wF = z;
            this.zzale = countDownLatch;
        }

        private void zza(ImageReceiver imageReceiver, boolean z) {
            ArrayList zza = imageReceiver.wB;
            int size = zza.size();
            for (int i = 0; i < size; i++) {
                zza com_google_android_gms_common_images_zza = (zza) zza.get(i);
                if (z) {
                    com_google_android_gms_common_images_zza.zza(this.wC.mContext, this.mBitmap, false);
                } else {
                    this.wC.wA.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                    com_google_android_gms_common_images_zza.zza(this.wC.mContext, this.wC.wx, false);
                }
                if (!(com_google_android_gms_common_images_zza instanceof com.google.android.gms.common.images.zza.zzc)) {
                    this.wC.wy.remove(com_google_android_gms_common_images_zza);
                }
            }
        }

        public void run() {
            com.google.android.gms.common.internal.zzb.zzhi("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (this.wC.ww != null) {
                if (this.wF) {
                    this.wC.ww.evictAll();
                    System.gc();
                    this.wF = false;
                    this.wC.mHandler.post(this);
                    return;
                } else if (z) {
                    this.wC.ww.put(new zza(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.wC.wz.remove(this.mUri);
            if (imageReceiver != null) {
                zza(imageReceiver, z);
            }
            this.zzale.countDown();
            synchronized (ImageManager.wr) {
                ImageManager.ws.remove(this.mUri);
            }
        }
    }

    private static final class zzb extends LruCache<zza, Bitmap> {
        public zzb(Context context) {
            super(zzcc(context));
        }

        @TargetApi(11)
        private static int zzcc(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int memoryClass = (((context.getApplicationInfo().flags & AccessibilityNodeInfoCompat.ACTION_DISMISS) != 0 ? 1 : null) == null || !zzs.zzavn()) ? activityManager.getMemoryClass() : zza.zza(activityManager);
            return (int) (((float) (memoryClass * AccessibilityNodeInfoCompat.ACTION_DISMISS)) * 0.33f);
        }

        protected /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            zza(z, (zza) obj, (Bitmap) obj2, (Bitmap) obj3);
        }

        protected /* synthetic */ int sizeOf(Object obj, Object obj2) {
            return zza((zza) obj, (Bitmap) obj2);
        }

        protected int zza(zza com_google_android_gms_common_images_zza_zza, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected void zza(boolean z, zza com_google_android_gms_common_images_zza_zza, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, com_google_android_gms_common_images_zza_zza, bitmap, bitmap2);
        }
    }

    static {
        wr = new Object();
        ws = new HashSet();
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.wv = Executors.newFixedThreadPool(4);
        if (z) {
            this.ww = new zzb(this.mContext);
            if (zzs.zzavq()) {
                zzarn();
            }
        } else {
            this.ww = null;
        }
        this.wx = new zzrc();
        this.wy = new HashMap();
        this.wz = new HashMap();
        this.wA = new HashMap();
    }

    public static ImageManager create(Context context) {
        return zzg(context, false);
    }

    private Bitmap zza(zza com_google_android_gms_common_images_zza_zza) {
        return this.ww == null ? null : (Bitmap) this.ww.get(com_google_android_gms_common_images_zza_zza);
    }

    @TargetApi(14)
    private void zzarn() {
        this.mContext.registerComponentCallbacks(new zze(this.ww));
    }

    public static ImageManager zzg(Context context, boolean z) {
        if (z) {
            if (wu == null) {
                wu = new ImageManager(context, true);
            }
            return wu;
        }
        if (wt == null) {
            wt = new ImageManager(context, false);
        }
        return wt;
    }

    public void loadImage(ImageView imageView, int i) {
        zza(new com.google.android.gms.common.images.zza.zzb(imageView, i));
    }

    public void loadImage(ImageView imageView, Uri uri) {
        zza(new com.google.android.gms.common.images.zza.zzb(imageView, uri));
    }

    public void loadImage(ImageView imageView, Uri uri, int i) {
        zza com_google_android_gms_common_images_zza_zzb = new com.google.android.gms.common.images.zza.zzb(imageView, uri);
        com_google_android_gms_common_images_zza_zzb.zzfy(i);
        zza(com_google_android_gms_common_images_zza_zzb);
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza(new com.google.android.gms.common.images.zza.zzc(onImageLoadedListener, uri));
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        zza com_google_android_gms_common_images_zza_zzc = new com.google.android.gms.common.images.zza.zzc(onImageLoadedListener, uri);
        com_google_android_gms_common_images_zza_zzc.zzfy(i);
        zza(com_google_android_gms_common_images_zza_zzc);
    }

    public void zza(zza com_google_android_gms_common_images_zza) {
        com.google.android.gms.common.internal.zzb.zzhi("ImageManager.loadImage() must be called in the main thread");
        new zzd(this, com_google_android_gms_common_images_zza).run();
    }
}
