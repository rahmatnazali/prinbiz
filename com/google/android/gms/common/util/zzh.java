package com.google.android.gms.common.util;

import android.os.SystemClock;

public final class zzh implements zze {
    private static zzh AW;

    public static synchronized zze zzavm() {
        zze com_google_android_gms_common_util_zze;
        synchronized (zzh.class) {
            if (AW == null) {
                AW = new zzh();
            }
            com_google_android_gms_common_util_zze = AW;
        }
        return com_google_android_gms_common_util_zze;
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public long nanoTime() {
        return System.nanoTime();
    }
}
