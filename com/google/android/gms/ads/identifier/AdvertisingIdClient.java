package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzcc;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.commons.net.ftp.FTPClient;

public class AdvertisingIdClient {
    private final Context mContext;
    com.google.android.gms.common.zza zzajb;
    zzcc zzajc;
    boolean zzajd;
    Object zzaje;
    zza zzajf;
    final long zzajg;

    public static final class Info {
        private final String zzajl;
        private final boolean zzajm;

        public Info(String str, boolean z) {
            this.zzajl = str;
            this.zzajm = z;
        }

        public String getId() {
            return this.zzajl;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzajm;
        }

        public String toString() {
            String str = this.zzajl;
            return new StringBuilder(String.valueOf(str).length() + 7).append("{").append(str).append("}").append(this.zzajm).toString();
        }
    }

    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzajh;
        private long zzaji;
        CountDownLatch zzajj;
        boolean zzajk;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzajh = new WeakReference(advertisingIdClient);
            this.zzaji = j;
            this.zzajj = new CountDownLatch(1);
            this.zzajk = false;
            start();
        }

        private void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.zzajh.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzajk = true;
            }
        }

        public void cancel() {
            this.zzajj.countDown();
        }

        public void run() {
            try {
                if (!this.zzajj.await(this.zzaji, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException e) {
                disconnect();
            }
        }

        public boolean zzdj() {
            return this.zzajk;
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000);
    }

    public AdvertisingIdClient(Context context, long j) {
        this.zzaje = new Object();
        zzab.zzy(context);
        this.mContext = context;
        this.zzajd = false;
        this.zzajg = j;
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1);
        try {
            advertisingIdClient.zze(false);
            Info info = advertisingIdClient.getInfo();
            return info;
        } finally {
            advertisingIdClient.finish();
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    static zzcc zza(Context context, com.google.android.gms.common.zza com_google_android_gms_common_zza) throws IOException {
        try {
            return com.google.android.gms.internal.zzcc.zza.zzf(com_google_android_gms_common_zza.zza(10000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            IOException iOException = new IOException(th);
        }
    }

    private void zzdi() {
        synchronized (this.zzaje) {
            if (this.zzajf != null) {
                this.zzajf.cancel();
                try {
                    this.zzajf.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzajg > 0) {
                this.zzajf = new zza(this, this.zzajg);
            }
        }
    }

    static com.google.android.gms.common.zza zzh(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            switch (zzc.zzang().isGooglePlayServicesAvailable(context)) {
                case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    ServiceConnection com_google_android_gms_common_zza = new com.google.android.gms.common.zza();
                    Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                    intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                    try {
                        if (zzb.zzaux().zza(context, intent, com_google_android_gms_common_zza, 1)) {
                            return com_google_android_gms_common_zza;
                        }
                        throw new IOException("Connection failure");
                    } catch (Throwable th) {
                        IOException iOException = new IOException(th);
                    }
                default:
                    throw new IOException("Google Play services not available");
            }
        } catch (NameNotFoundException e) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public void finish() {
        zzab.zzhj("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext == null || this.zzajb == null) {
                return;
            }
            try {
                if (this.zzajd) {
                    zzb.zzaux().zza(this.mContext, this.zzajb);
                }
            } catch (Throwable e) {
                Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", e);
            }
            this.zzajd = false;
            this.zzajc = null;
            this.zzajb = null;
        }
    }

    public Info getInfo() throws IOException {
        Info info;
        zzab.zzhj("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzajd) {
                synchronized (this.zzaje) {
                    if (this.zzajf == null || !this.zzajf.zzdj()) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    zze(false);
                    if (!this.zzajd) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (Throwable e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Throwable e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzab.zzy(this.zzajb);
            zzab.zzy(this.zzajc);
            info = new Info(this.zzajc.getId(), this.zzajc.zzf(true));
        }
        zzdi();
        return info;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zze(true);
    }

    protected void zze(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzab.zzhj("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzajd) {
                finish();
            }
            this.zzajb = zzh(this.mContext);
            this.zzajc = zza(this.mContext, this.zzajb);
            this.zzajd = true;
            if (z) {
                zzdi();
            }
        }
    }
}
