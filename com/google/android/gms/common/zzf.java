package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzs;

public class zzf {
    private static zzf rx;
    private final Context mContext;

    private zzf(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private boolean zzb(PackageInfo packageInfo, boolean z) {
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return false;
        }
        zza com_google_android_gms_common_zzd_zzb = new zzb(packageInfo.signatures[0].toByteArray());
        for (zzs equals : z ? zzd.zzani() : zzd.zzanj()) {
            if (com_google_android_gms_common_zzd_zzb.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public static zzf zzbz(Context context) {
        zzab.zzy(context);
        synchronized (zzf.class) {
            if (rx == null) {
                zzd.zzbq(context);
                rx = new zzf(context);
            }
        }
        return rx;
    }

    zza zza(PackageInfo packageInfo, zza... com_google_android_gms_common_zzd_zzaArr) {
        int i = 0;
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzb com_google_android_gms_common_zzd_zzb = new zzb(packageInfo.signatures[0].toByteArray());
        while (i < com_google_android_gms_common_zzd_zzaArr.length) {
            if (com_google_android_gms_common_zzd_zzaArr[i].equals(com_google_android_gms_common_zzd_zzb)) {
                return com_google_android_gms_common_zzd_zzaArr[i];
            }
            i++;
        }
        return null;
    }

    public boolean zza(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            zza zza;
            if (z) {
                zza = zza(packageInfo, zzd.ro);
            } else {
                zza = zza(packageInfo, zzd.ro[0]);
            }
            if (zza != null) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zze.zzbu(this.mContext)) {
            return zzb(packageInfo, true);
        }
        boolean zzb = zzb(packageInfo, false);
        if (zzb || !zzb(packageInfo, true)) {
            return zzb;
        }
        Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        return zzb;
    }

    public boolean zzb(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (!zza(packageInfo, true)) {
            return false;
        }
        if (zze.zzbu(this.mContext)) {
            return true;
        }
        Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        return false;
    }

    public boolean zzb(PackageManager packageManager, String str) {
        try {
            return zza(packageManager, packageManager.getPackageInfo(str, 64));
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
